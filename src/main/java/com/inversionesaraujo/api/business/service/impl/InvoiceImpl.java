package com.inversionesaraujo.api.business.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
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
import com.inversionesaraujo.api.business.dto.InvoiceSunatDTO;
import com.inversionesaraujo.api.business.dto.InvoiceDTO;
import com.inversionesaraujo.api.business.dto.InvoiceDetailSunatDTO;
import com.inversionesaraujo.api.business.payload.ApiSunatResponse;
import com.inversionesaraujo.api.business.service.I_Invoice;
import com.inversionesaraujo.api.business.spec.InvoiceSpecifications;
import com.inversionesaraujo.api.model.DocumentType;
import com.inversionesaraujo.api.model.Invoice;
import com.inversionesaraujo.api.model.InvoiceItem;
import com.inversionesaraujo.api.model.InvoiceType;
import com.inversionesaraujo.api.model.SortDirection;
import com.inversionesaraujo.api.model.SortBy;
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
    public Page<InvoiceDTO> listAll(InvoiceType type, Integer page, Integer size, SortDirection direction, SortBy sortby) {
        Specification<Invoice> spec = Specification.where(InvoiceSpecifications.findByInvoiceType(type));
        Pageable pageable;
        if(sortby != null) {
            Sort sort = Sort.by(Sort.Direction.fromString(direction.toString()), sortby.toString());
            pageable = PageRequest.of(page, size, sort);
        }else {
            pageable = PageRequest.of(page, size);
        }

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
    public ApiSunatResponse sendInvoiceToSunat(InvoiceDTO invoice) {
        Double total = invoice.getTotal();
        Double subtotal = BigDecimal.valueOf(total / 1.18).setScale(2, RoundingMode.HALF_UP).doubleValue();
        Double igv = BigDecimal.valueOf(total - subtotal).setScale(2, RoundingMode.HALF_UP).doubleValue();

        List<InvoiceItem> items = itemRepo.findByInvoiceId(invoice.getId());

        List<InvoiceDetailSunatDTO> details = items
            .stream()
            .map(item -> {
                Double priceItem = item.getPrice();
                Double priceSub = BigDecimal.valueOf(priceItem / 1.18).setScale(2, RoundingMode.HALF_UP).doubleValue();
                Double totalSub = priceSub * item.getQuantity();
                Double totalItem = item.getPrice() * item.getQuantity();
                Double igvItem = BigDecimal.valueOf(totalItem - totalSub).setScale(2, RoundingMode.HALF_UP).doubleValue();

                String codProducto = String.format("%03d", items.indexOf(item) + 1);
                return InvoiceDetailSunatDTO
                    .builder()
                    .unidad_de_medida(item.getUnit())
                    .codigo(codProducto)
                    .descripcion(item.getName())
                    .cantidad(item.getQuantity())
                    .valor_unitario(priceSub)
                    .precio_unitario(priceItem)
                    .subtotal(totalSub)
                    .total(totalItem)
                    .igv(igvItem)
                    .tipo_de_igv(1)
                    .anticipo_regularizacion(false)
                    .build();
            })
            .toList();

        LocalDateTime localDateTime = invoice.getIssueDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String issueDate = localDateTime.format(formatter);

        Boolean isFirstType = invoice.getInvoiceType() == InvoiceType.FACTURA;

        InvoiceSunatDTO payload = InvoiceSunatDTO
            .builder()
            .operacion("generar_comprobante")
            .tipo_de_comprobante(isFirstType ? 1 : 2)
            .serie(invoice.getSerie())
            .numero(invoice.getId().intValue())
            .sunat_transaction(1)
            .cliente_tipo_de_documento(invoice.getDocumentType() == DocumentType.RUC ? 6 : 1)
            .cliente_numero_de_documento(invoice.getDocument())
            .cliente_denominacion(invoice.getRsocial())
            .cliente_direccion(invoice.getAddress())
            .fecha_de_emision(issueDate)
            .moneda(1)
            .porcentaje_de_igv(18.0)
            .total_gravada(subtotal)
            .total_igv(igv)
            .total(total)
            .detraccion(false)
            .enviar_automaticamente_a_la_sunat(true)
            .enviar_automaticamente_al_cliente(false)
            .items(details)
            .build();

        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonDebug = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(payload);
            System.out.println("JSON enviado a la API externa:\n" + jsonDebug);
        } catch (Exception e) {
            System.out.println("No se pudo imprimir el JSON: " + e.getMessage());
        }

        String apiUrl = System.getenv("SUNAT_URL");
        String apiToken = System.getenv("SUNAT_TOKEN");

        ApiSunatResponse apiResponse;
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", apiToken);
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<InvoiceSunatDTO> requestEntity = new HttpEntity<>(payload, headers);

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, requestEntity, String.class);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                // Debug: imprime el JSON de respuesta
                System.out.println("Respuesta de la API Sunat:\n" + response.getBody());

                ObjectMapper mapper = new ObjectMapper();
                apiResponse = mapper.readValue(response.getBody(), ApiSunatResponse.class);
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

        return apiResponse;
    }

    @Transactional(readOnly = true)
    @Override
    public Page<InvoiceDTO> search(String rsocial, String document, Integer page) {
        Pageable pageable = PageRequest.of(page, 10);

        Page<Invoice> invoices = invoiceRepo.findByrsocialContainingIgnoreCaseOrDocumentContainingIgnoreCase(rsocial, document, pageable);

        return InvoiceDTO.toPageableDTO(invoices);
    }
}
