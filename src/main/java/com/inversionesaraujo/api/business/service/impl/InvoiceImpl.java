package com.inversionesaraujo.api.business.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.storage.Bucket;
import com.google.firebase.cloud.StorageClient;
import com.inversionesaraujo.api.business.dto.InvoiceAddressDTO;
import com.inversionesaraujo.api.business.dto.InvoiceBodyDTO;
import com.inversionesaraujo.api.business.dto.InvoiceClientDTO;
import com.inversionesaraujo.api.business.dto.InvoiceCompanyDTO;
import com.inversionesaraujo.api.business.dto.InvoiceDTO;
import com.inversionesaraujo.api.business.dto.InvoiceDetailDTO;
import com.inversionesaraujo.api.business.dto.InvoicePayDTO;
import com.inversionesaraujo.api.business.payload.FileResponse;
import com.inversionesaraujo.api.business.service.I_Invoice;
import com.inversionesaraujo.api.business.spec.InvoiceSpecifications;
import com.inversionesaraujo.api.model.DocumentType;
import com.inversionesaraujo.api.model.Invoice;
import com.inversionesaraujo.api.model.InvoiceItem;
import com.inversionesaraujo.api.model.InvoiceType;
import com.inversionesaraujo.api.model.SortDirection;
import com.inversionesaraujo.api.repository.InvoiceItemRepository;
import com.inversionesaraujo.api.repository.InvoiceRepository;

@Service
public class InvoiceImpl implements I_Invoice {
    @Autowired
    private InvoiceRepository invoiceRepo;
    @Autowired
    private InvoiceItemRepository itemRepo;

    @Transactional(readOnly = true)
    @Override
    public Page<InvoiceDTO> listAll(InvoiceType type, Integer page, Integer size, SortDirection direction) {
        Specification<Invoice> spec = Specification.where(InvoiceSpecifications.findByInvoiceType(type));
        Sort sort = Sort.by(Sort.Direction.fromString(direction.toString()), "issueDate");
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Invoice> invoices = invoiceRepo.findAll(spec, pageable);

        return InvoiceDTO.toPageableDTO(invoices);
    }

    @Transactional
    @Override
    public InvoiceDTO save(InvoiceDTO invoice) {
        Invoice invoiceSaved = invoiceRepo.save(InvoiceDTO.toEntity(invoice));

        return InvoiceDTO.toDTO(invoiceSaved);
    }

    @Transactional(readOnly = true)
    @Override
    public InvoiceDTO findById(Long id) {
        Invoice invoice = invoiceRepo.findById(id).orElseThrow(() -> new DataAccessException("El comprobante no existe") {});

        return InvoiceDTO.toDTO(invoice);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        invoiceRepo.deleteById(id);
    }

    @Override
    public FileResponse getAndUploadDoc(InvoiceDTO invoice) {
        InvoicePayDTO formaPago = InvoicePayDTO
            .builder()
            .moneda("PEN")
            .tipo("Contado")
            .build();

        InvoiceAddressDTO clientAddress = InvoiceAddressDTO
            .builder()
            .direccion(invoice.getAddress())
            .provincia("HUANCAYO")
            .departamento("JUNIN")
            .distrito("HUANCAYO")
            .ubigueo("120133")
            .build();

        InvoiceAddressDTO companyAddress = InvoiceAddressDTO
            .builder()
            .direccion("JR. SAN BERNARDO NRO. S/N JUNIN - HUANCAYO - SAPALLANGA")
            .provincia("HUANCAYO")
            .departamento("JUNIN")
            .distrito("SAPALLANGA")
            .ubigueo("120133")
            .build();

        InvoiceClientDTO client = InvoiceClientDTO
            .builder()
            .tipoDoc(invoice.getDocumentType() == DocumentType.DNI ? "1" : "6")
            .numDoc(Long.parseLong(invoice.getDocument()))
            .rznSocial(invoice.getRsocial())
            .address(clientAddress)
            .build();
        
        InvoiceCompanyDTO company = InvoiceCompanyDTO
            .builder()
            .ruc(20600964471L)
            .razonSocial("INVERSIONES ARAUJO JL E.I.R.L.")
            .address(companyAddress)
            .build();

        Double total = invoice.getTotal();
        Double subtotal = BigDecimal.valueOf(total / 1.18).setScale(2, RoundingMode.HALF_UP).doubleValue();
        Double igv = BigDecimal.valueOf(total - subtotal).setScale(2, RoundingMode.HALF_UP).doubleValue();

        List<InvoiceItem> items = itemRepo.findByInvoiceId(invoice.getId());

        List<InvoiceDetailDTO> details = items
            .stream()
            .map(item -> {
                Double priceItem = item.getPrice();
                Double priceSub = BigDecimal.valueOf(priceItem / 1.18).setScale(2, RoundingMode.HALF_UP).doubleValue();
                Double totalSub = priceSub * item.getQuantity();
                Double igvItem = BigDecimal.valueOf(totalSub * 0.18).setScale(2, RoundingMode.HALF_UP).doubleValue();

                String codProducto = String.format("%03d", items.indexOf(item) + 1);
                return InvoiceDetailDTO
                    .builder()
                    .codProducto(codProducto)
                    .unidad(item.getUnit())
                    .descripcion(item.getName())
                    .cantidad(item.getQuantity())
                    .mtoValorUnitario(priceSub)
                    .mtoValorVenta(totalSub)
                    .mtoBaseIgv(totalSub)
                    .porcentajeIgv(18)
                    .igv(igvItem)
                    .tipAfeIgv(10)
                    .totalImpuestos(igvItem)
                    .mtoPrecioUnitario(priceItem)
                    .build();
            })
            .toList();

        LocalDateTime localDateTime = invoice.getIssueDate();
        ZoneOffset offset = ZoneOffset.of("-05:00");
        OffsetDateTime offsetDateTime = localDateTime.atOffset(offset);

        String issueDate = offsetDateTime.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);

        InvoiceBodyDTO payload = InvoiceBodyDTO
            .builder()
            .ublVersion("2.1")
            .tipoOperacion("01")
            .tipoDoc(invoice.getInvoiceType() == InvoiceType.FACTURA ? "01" : "03")
            .serie(invoice.getSerie())
            .correlativo(invoice.getId().toString())
            .fechaEmision(issueDate)
            .formaPago(formaPago)
            .tipoMoneda("PEN")
            .client(client)
            .company(company)
            .mtoOperGravadas(subtotal)
            .mtoIGV(igv)
            .valorVenta(subtotal)
            .totalImpuestos(igv)
            .subTotal(total)
            .mtoImpVenta(total)
            .details(details)
            .build();

        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonDebug = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(payload);
            System.out.println("JSON enviado a la API externa:\n" + jsonDebug);
        } catch (Exception e) {
            System.out.println("No se pudo imprimir el JSON: " + e.getMessage());
        }

        String apiUrl = "";
        String apiToken = "Bearer";

        byte[] pdfBytes;
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", apiToken);
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<InvoiceBodyDTO> requestEntity = new HttpEntity<>(payload, headers);

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<byte[]> response = restTemplate.postForEntity(apiUrl, requestEntity, byte[].class);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                pdfBytes = response.getBody();
            } else {
                throw new RuntimeException("Error: " + response.getStatusCode());
            }
        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            System.out.println("Error");
            System.out.println("Codigo: " + ex.getStatusCode());
            System.out.println("Response: " + ex.getResponseBodyAsString());
            throw new RuntimeException(ex);
        } catch (Exception e) {
            System.out.println("Error");
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        Bucket bucket = StorageClient.getInstance().bucket();
        String type = invoice.getInvoiceType().toString();
        String fileName = type.toLowerCase() + "-" + invoice.getId() + "-" + invoice.getRsocial().toLowerCase().replaceAll("\\s+", "") + ".pdf";
        bucket.create(fileName, pdfBytes, "application/pdf");

        String pdfUrl = String.format(
                "https://firebasestorage.googleapis.com/v0/b/%s/o/%s?alt=media",
                bucket.getName(),
                URLEncoder.encode(fileName, StandardCharsets.UTF_8)
        );

        // 4. Retornar el FileResponse
        return FileResponse
                .builder()
                .fileUrl(pdfUrl)
                .fileName(fileName)
                .build();
    }

    @Transactional(readOnly = true)
    @Override
    public Page<InvoiceDTO> search(String rsocial, String document, Integer page) {
        Pageable pageable = PageRequest.of(page, 10);

        Page<Invoice> invoices = invoiceRepo.findByrsocialContainingIgnoreCaseOrDocumentContainingIgnoreCase(rsocial, document, pageable);

        return InvoiceDTO.toPageableDTO(invoices);
    }
}
