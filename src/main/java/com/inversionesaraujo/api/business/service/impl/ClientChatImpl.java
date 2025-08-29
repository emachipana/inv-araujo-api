package com.inversionesaraujo.api.business.service.impl;

import com.inversionesaraujo.api.business.service.IClientChat;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClientChatImpl implements IClientChat {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private GptImpl gptService;

    @Transactional(readOnly = true)
    @Override
    public String answer(String question) {
        if(isProductQuery(question)) {
            String sql = generateSQL(question);

            try {
                List<Map<String, Object>> results = jdbcTemplate.queryForList(sql);
                System.out.println(sql);
                System.out.println(results);
    
                return generateNaturalAnswer(question, results);
            }catch(Exception e) {
                return "Lo siento no encontré la información que buscabas.";
            }

        }

        return handleStaticQuestion(question);
    }

    private Boolean isProductQuery(String question) {
        String parsed = question.toLowerCase();

        return parsed.contains("precio") || parsed.contains("producto") || 
            parsed.contains("tienen") || parsed.contains("categoria") || 
            parsed.contains("cantidad") || parsed.contains("stock") || 
            parsed.contains("descuento") || parsed.contains("cuantas") || 
            parsed.contains("cuantos");
    }

    private String generateNaturalAnswer(String question, List<Map<String, Object>> results) {
        String prompt = """
            Reglas:
            - Si vas a mencionar precios usa S/.
            - Si el campo "price_discount" es mayor a 0 significa que tiene descuento, solo ahí menciona el precio original y el descuento.
            - Si los datos de la DB están vacios respondes corto y amable.
            - Genera una respuesta corta y amigable.
        
            El usuario preguntó: "%s"

            Estos son los resultados de la DB: %s
        """.formatted(question, results.toString());

        return gptService.ask(prompt);
    }

    private String generateSQL(String question) {
        String prompt = """
            Eres un asistente que genera SQL válido para PostgreSQL y coherente.
            Reglas:
            - Para buscar productos por nombre usa "ILIKE" para no depender de mayusculas o minusculas
            - El producto tiene descuento solo si el campo "price_discount" es mayor a 0

            Tablas:
            - products(id, name, price, category_id, stock, price_discount)
            - categories(id, name)

            Pregunta: "%s"
            Devuelve solo la consulta SQL.
        """.formatted(question);

        return gptService.ask(prompt);
    }

    private String handleStaticQuestion(String question) {
        String prompt = """
            Rol: asistente virtual (servicial y amable) de Inversiones Araujo.
            
            Reglas:
            - Si la pregunta está relacionada a la empresa, responde con los datos disponibles.
            - Usa únicamente los datos proporcionados abajo.
            - Responde de forma breve, clara y directa, sin repetir información innecesaria.
            - Si la respuesta ya está cubierta con un dato, no agregues el resto.
            - Si no existe respuesta en los datos, responde de manera servicial
            
            Datos disponibles:
            - Empresa del rubro agrícola dedicada a la venta de productos y plántulas in vitro de papa.
            - Ubicación: Sapallanga, Huancayo, a 2 cuadras del cementerio.
            - Horario: Lunes a Sábado, de 8AM a 6PM.
            - Envíos: mediante la agencia Shalom.
            - Contacto: 990849369.
            
            Pregunta del cliente: "%s"
        """.formatted(question);
    
        return gptService.ask(prompt);
    }
}
