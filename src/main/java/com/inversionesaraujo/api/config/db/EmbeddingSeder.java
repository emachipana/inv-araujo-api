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
            "Ubicación: Jr. San Bernardo, Sapallanga, Huancayo",
            "¿Donde estan ubicados? En Sapallanga, Huancayo, a 2 cuadras del cementerio",
            "¿Donde esta ubicada la tienda? En Sapallanga, Huancayo, a 2 cuadras del cementerio",

            // Horarios
            "Horario de atención: Lunes a Viernes de 9:00 AM a 6:00 PM",
            "Horario de atención los Sábados: 9:00 AM a 12:00 PM",
            "Domingos no hay atención en la tienda",
            
            // Productos
            "Venta de insumos agrícolas en general",
            "Plántulas in vitro de papa y olluco bajo pedido",
            "Semillas de papa nativa y variedades mejoradas",
            "Fertilizantes y abonos para cultivos",
            "Herramientas y equipos para agricultura",
            "¿Que variedades de plantulas invitro ofrecen? Todas las variedades en nativas y mejoradas",
            "¿Que variedades de semillas ofrecen? Todas las variedades en nativas y mejoradas",
            
            // Plántulas
            "Plántulas in vitro en tapers con gel nutritivo",
            "Venta por taper con 40 unidades de plántulas",
            "Precio de plántula in vitro: S/ 0.80 c/u",
            
            // Pagos
            "Adelanto del 50% para confirmar pedidos de plántulas",
            "Metodos de pago: tarjeta de crédito/débito y Yape",
            
            // Envíos
            "Recojo de pedidos en tienda en Sapallanga",
            "Envíos a provincias mediante agencia Shalom",
            "Método de pago: contra entrega",
            
            // Contacto
            "Contacto por WhatsApp: 990 849 369",
            "Correo electrónico: inversionesaraujojl@gmail.com",
            "Número de contacto: 990 949 369",
            "Teléfono de contacto: 990 949 369"
        );

        for (String text : texts) {
            embeddingService.saveText(text);
        }

        System.out.println("Data de la empresa cargada");
    }
}
