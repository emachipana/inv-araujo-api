package com.inversionesaraujo.api.config.db;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.inversionesaraujo.api.business.service.IEmbedding;

@Component
public class EmbeddingSeder implements CommandLineRunner {
    @Autowired
    private IEmbedding embeddingService;
    
    @Override
    public void run(String... args) throws Exception {
        if(embeddingService.count() > 0) {
            System.out.println("Embeddings ya cargados");
            return;
        }

        List<String> texts = List.of(
            // Ubicación
            "Estamos ubicados en Huancayo, distrito de Sapallanga, exactamente en Jr. San Bernardo.",
            "Nuestra tienda/almacén/local se encuentra a 2 cuadras del cementerio de Sapallanga.",
            "Pueden visitarnos en nuestro punto de venta en Sapallanga o coordinar envíos a domicilio.",
            
            // Horarios
            "Horario de atención: Lunes a Viernes de 9:00 AM a 6:00 PM.",
            "Sábados de 9:00 AM a 12:00 PM. Domingos no hay atención al público.",
            
            // Productos
            "Somos una empresa agrícola que vende insumos agrícolas en general.",
            "Producimos plántulas in vitro de papa y olluco, bajo pedido.",
            "Trabajamos con una gran variedad de papa nativa y mejorada.",
            "Ofrecemos semillas, fertilizantes, abonos y herramientas para el campo.",
            "Especialistas en cultivos andinos y productos para la agricultura.",
            
            // Plántulas
            "Las plántulas/plantines/plantas in vitro se entregan en tapers especiales con gel nutritivo.",
            "Cada taper contiene 40 unidades de brotes/plántulas de alta calidad.",
            "Precio por plántula in vitro: S/ 0.80. Precios especiales por volumen.",
            
            // Pedidos y pagos
            "Para iniciar tu pedido se requiere un adelanto del 50%.",
            "Tiempo de producción varía según la cantidad solicitada.",
            "Aceptamos pagos con tarjeta. También Yape.",
            
            // Envíos y delivery
            "¿Hacen delivery? No ofrecemos delivery directo, pero puedes recoger en tienda.",
            "Recojo en nuestro almacén/tienda/local en Sapallanga, Huancayo.",
            "¿Hacen envíos? Sí, enviamos a provincias a través de Shalom.",
            "Los envíos son contra entrega, el cliente paga al recibir el pedido.",
            "El traslado desde nuestro almacén a la agencia de transporte no tiene costo adicional.",
            
            // Garantías y condiciones
            "Tomamos fotos como evidencia del estado de los productos antes del envío.",
            "No nos hacemos responsables por daños ocurridos durante el transporte.",
            "Los clientes pueden revisar su pedido antes de retirarlo de nuestro local.",
            
            // Contacto
            "WhatsApp: 990 849 360 | Correo: inversionesaraujojl@gmail.com",
            "Contáctanos para consultas sobre disponibilidad y precios especiales.",
            
            // Preguntas frecuentes
            "¿Dónde están ubicados? En Jr. San Bernardo, Sapallanga, Huancayo.",
            "¿Qué métodos de pago aceptan? Tarjeta de crédito/débito.",
            "¿Hacen envíos a provincia? Sí, a través de Shalom.",
            "¿Cuál es el horario de atención? L-V 9AM-6PM, Sáb 9AM-12PM.",
            "¿Venden al por mayor? Sí, contáctanos para precios por volumen.",
            "¿Qué productos ofrecen? Semillas, fertilizantes, herramientas y plántulas in vitro.",
            "¿Las plántulas tienen garantía? Se entregan en óptimas condiciones, verificables al momento de la entrega.",
            "¿Tienen ofertas? Sí, consulta por nuestras promociones estacionales.",
            "¿Qué variedades de papa manejan? Trabajamos con variedades nativas y mejoradas, consulta disponibilidad."
        );

        for (String text : texts) {
            embeddingService.saveText(text);
        }

        System.out.println("Data de la empresa cargada");
    }
}
