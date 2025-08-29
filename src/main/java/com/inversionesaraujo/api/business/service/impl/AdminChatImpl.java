package com.inversionesaraujo.api.business.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inversionesaraujo.api.business.service.IAdminChat;

@Service
public class AdminChatImpl implements IAdminChat {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private GptImpl gptService;
    @Autowired
    private ProductImpl productService;

    @Transactional(readOnly = true)
    @Override
    public String answer(String question) {
        String parsed = question.toLowerCase();

        if(
            parsed.contains("predice") || parsed.contains("prediccion") || 
            parsed.contains("predecir") || parsed.contains("pronostico") ||
            parsed.contains("venderé") || parsed.contains("vendere")
        ) {
            String productName = extractProductName(question);
            System.out.println(productName);

            Boolean productExists = productService.existsByName(productName);

            if(!productExists) {
                return """
                    {
                        "type": "text",
                        "content": "Lo siento, no encontré el producto que buscas."
                    }
                """;
            }

            String sql = generateProductHistorySQL(productName);
            System.out.println(sql);

            try {
                List<Map<String, Object>> results = jdbcTemplate.queryForList(sql);
                int months = results.size();
                System.out.println(sql);
                System.out.println(results);
                System.out.println(months);

                if(months < 3) {
                    return """
                        {
                            "type": "text",
                            "content": "No tenemos suficientes datos para realizar la predicción"
                        }
                    """;
                }

                return formatResponse(question, results);
            }catch(Exception e) {
                System.out.println(sql);
                System.out.println(e);
                return """
                    {
                        "type": "text",
                        "content": "Lo siento no encontré la información que buscabas."
                    }
                """;
            }
        }

        String sql = generateSQL(question);
        
        try {

            List<Map<String, Object>> results = jdbcTemplate.queryForList(sql);
            System.out.println(sql);
            System.out.println(results);
            return formatResponse(question, results);
        }catch(Exception e) {
            System.out.println(sql);
            System.out.println(e);
            return """
                {
                    "type": "text",
                    "content": "Lo siento no encontré la información que buscabas."
                }
            """;
        }
    }

    public String formatResponse(String question, List<Map<String, Object>> results) {
        String prompt = """
            Eres un asistente para un administrador.
    
            Devuelve SIEMPRE un objeto JSON con la forma:
            {
              "type": "text" | "chart",
              "content": string | {
                "label": string,
                "data": number[],
                "labels": string[]
              }
            }
    
            Reglas principales:
            1. Si la pregunta NO pide predicción:
               - Siempre responde con type "text".
               - Si hay un solo valor → muestra el valor directamente.
               - Si hay varios valores → muestra un resumen en texto.
               - Si no hay datos → explica que no se encontraron resultados.
            
            2. Si la pregunta pide PREDICCIÓN:
               - Usa solo hasta 3 meses de datos históricos.
               - Predicción máxima = 3 meses al futuro.
               - Para predicciones de 1 mes (ejm. el proximo mes) → haz la predicción usando los datos históricos disponibles y responde en texto.
               - Para predicciones de 2 o 3 meses → genera un gráfico mostrando solo la predicción (chart), haz la predicción usando los datos históricos disponibles máximo 3 meses al futuro.
               - Si pide más de 3 meses → responde en texto explicando que solo se pueden hasta 3 meses para mantener precisión.
               - Si pide semanas u otra granularidad → responde en texto explicando amablemente que solo se hacen predicciones mensuales.
               - Si no hay datos → responde en texto explicando que no se puede predecir por falta de datos.
    
            Datos de la DB:
            %s
    
            Pregunta:
            "%s"
        """.formatted(results.toString(), question);
    
        return gptService.ask(prompt);
    }

    private String generateProductHistorySQL(String productName) {
        return """
            SELECT gs.month::date AS mes, 
                COALESCE(SUM(op.quantity),0) AS total_vendido
            FROM generate_series(
                date_trunc('month', CURRENT_DATE) - INTERVAL '2 months',
                date_trunc('month', CURRENT_DATE),
                '1 month'
            ) gs(month)
            LEFT JOIN orders o ON date_trunc('month', o.date) = gs.month
            LEFT JOIN order_products op ON o.id = op.order_id
            LEFT JOIN products p ON op.product_id = p.id
            WHERE p.name ILIKE '%%' || '%s' || '%%'
            GROUP BY gs.month
            ORDER BY gs.month;
        """.formatted(productName);
    }

    private String generateSQL(String question) {
        String sqlPrompt = """
            Genera SOLO una consulta SQL válida para PostgreSQL y coherente.

            Reglas:
            - Usa ILIKE para búsquedas por nombre %%nombre%%.
            - Producto con descuento → price_discount > 0
            - Resumen de ingresos/gastos/ganancias → profits
            - Ventas de productos → orders + order_products + products
            - Para filtrar ganancias con la tabla profits usa rangos de fechas
            - Si vas a mostrar precios o dinero usa S/.
            - El año actual es 2025

            Tablas:
            - products(id, name, price, category_id, stock, price_discount)
            - categories(id, name)
            - profits(id, date, totalExpenses, income, profit)
            - orders(id, total, date)
            - order_products(id, order_id, product_id, quantity, subTotal, price)

            Pregunta: "%s"
            Devuelve solo la consulta SQL.
        """.formatted(question);
    
        return gptService.ask(sqlPrompt);
    }

    private String extractProductName(String question) {
        String prompt = """
            Extrae solo el nombre del producto de esta frase, sin ninguna explicación ni adornos:
            "%s"
            Devuelve solo el nombre del producto en texto plano.
        """.formatted(question);

        return gptService.ask(prompt).trim();
    }
}
