package com.inversionesaraujo.api.business.service.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.inversionesaraujo.api.business.service.ICohere;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CohereImpl implements ICohere {
    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public List<Double> embed(String text) {
        try {
            String apiKey = System.getenv("COHERE_API_KEY");
            String apiUrl = "https://api.cohere.ai/embed";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(apiKey);

            Map<String, Object> body = new HashMap<>();
            body.put("texts", List.of(text));
            body.put("model", "embed-multilingual-v3.0");
            body.put("input_type", "search_document");

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

            ResponseEntity<Map> response = restTemplate.postForEntity(apiUrl, request, Map.class);
            List<List<Double>> embeddings = (List<List<Double>>) response.getBody().get("embeddings");

            return embeddings.get(0);
        } catch (Exception e) {
            log.error("Error generando el embed: {}", e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    @Override
    public String generate(String prompt) {
        String apiKey = System.getenv("COHERE_API_KEY");
        String apiUrl = "https://api.cohere.ai/generate";
    
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);
        headers.set("Cohere-Version", "2022-12-06");

        Map<String, Object> body = new HashMap<>();
        body.put("model", "command-r-plus");
        body.put("prompt", prompt);
        body.put("max_tokens", 100);
        body.put("temperature", 0.5);
        body.put("stop_sequences", List.of("Usuario:"));
    
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
    
        ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, request, String.class);
    
        System.out.println("Response: " + response.getBody());

        try {
            JSONObject json = new JSONObject(response.getBody());
            return json.getJSONArray("generations").getJSONObject(0).getString("text").trim();
        } catch (Exception e) {
            return "Lo siento, hubo un error al generar la respuesta.";
        }
    }
    
}
