package com.inversionesaraujo.api.config.db.seeders;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.inversionesaraujo.api.business.service.IEmbedding;

@Component
public class EmbeddingSeeder {
    @Autowired
    private IEmbedding embeddingService;
    
    public void seed() throws JsonProcessingException {
        if(embeddingService.count() > 0) {
            System.out.println("Embeddings ya cargados");
            return;
        }
        
        List<String> texts = List.of(
            // Ubicación
            "Ubicacion: Jr. San Bernardo, Sapallanga, Huancayo",
            "¿Donde estan ubicados? En Sapallanga, Huancayo, a 2 cuadras del cementerio",
            "¿Donde esta ubicada la tienda? En Sapallanga, Huancayo, a 2 cuadras del cementerio",

            // Horarios
            "Horario de atencion: Lunes a Viernes de 9 AM a 6 PM",
            "Horario de atencion los Sábados: 9 AM a 12 PM",
            "Domingos no hay atencion en la tienda",
            
            // Productos
            "Venta de insumos agricolas en general",
            "Plantulas in vitro de papa y olluco bajo pedido",
            "Semillas de papa nativa y variedades mejoradas",
            "Fertilizantes y abonos para cultivos",
            "Herramientas y equipos para agricultura",
            "¿Que variedades de plantulas invitro ofrecen? Todas las variedades de papa en nativas y mejoradas",
            "¿Que variedades de semillas ofrecen? Todas las variedades de papa en nativas y mejoradas",
            
            // Plantulas
            "Plantulas in vitro en tapers con gel nutritivo",
            "Venta por taper con 40 unidades de plantulas",
            "Precio de plantula in vitro: S/ 0.80 c/u",
            
            // Pagos
            "Adelanto del 50% para confirmar pedidos de plantulas",
            "Metodos de pago: tarjeta de debito y Yape",
            
            // Envios
            "Recojo de pedidos en tienda en Sapallanga",
            "Envios a provincias mediante agencia Shalom",
            "¿Hacen envios a otros departamentos? Si, mediante agencia Shalom",
            "Metodo de pago: contra entrega",
            
            // Contacto
            "Contacto por WhatsApp: 990849369",
            "Correo electronico: inversionesaraujojl@gmail.com",
            "Numero de contacto: 990949369",
            "Telefono de contacto: 990949369"
        );

        for (String text : texts) {
            embeddingService.saveText(text);
        }

        System.out.println("Data de la empresa cargada");
    }
}
