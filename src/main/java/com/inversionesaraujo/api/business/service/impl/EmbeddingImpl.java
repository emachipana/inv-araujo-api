package com.inversionesaraujo.api.business.service.impl;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inversionesaraujo.api.business.service.IEmbedding;
import com.inversionesaraujo.api.model.Embedding;
import com.inversionesaraujo.api.repository.EmbeddingRepository;
import com.inversionesaraujo.api.utils.Similarity;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EmbeddingImpl implements IEmbedding {
    @Autowired
	private CohereImpl cohereService;
	@Autowired
	private EmbeddingRepository embeddingRepository;
	@Autowired
	private ObjectMapper objectMapper;
    
	@Transactional
    @Override
    public void saveText(String text) throws JsonProcessingException {
        List<Double> vector = cohereService.embed(text);
		String vectorJson = objectMapper.writeValueAsString(vector);
		Embedding embedding = Embedding
			.builder()
			.text(text)
			.vector(vectorJson)
			.build();

		embeddingRepository.save(embedding);
    }

	@Transactional(readOnly = true)
	@Override
	public String answer(String question) throws JsonProcessingException {
		String betterEmbedding = betterAnswer(question);

		String prompt = "Rol: Asistente virtual, Empresa: Inversiones Araujo, Ocupación: Agricultura, Ubicación: Sapallanga, Contacto: 990849369, Acción: responde solo preguntas de la empresa servicialmente, Dato embedding: " + betterEmbedding + "\n\n" + "Pregunta: " + question;

		return cohereService.generate(prompt);
	}

	private String betterAnswer(String question) throws JsonProcessingException {
		if (question == null || question.trim().isEmpty()) {
			return "Por favor, proporciona una pregunta válida.";
		}

		List<Double> questionVector = cohereService.embed(question);
		if (questionVector == null || questionVector.isEmpty()) {
			return "No pude procesar la pregunta. Por favor, inténtalo de nuevo.";
		}

		List<Embedding> embeddings = embeddingRepository.findAll();
		if (embeddings.isEmpty()) {
			return "No hay información disponible para responder a tu pregunta.";
		}

		double maxSimilarity = 0.0;
		String result = "No se encontró, responde con sentido";
		
		for (Embedding embedding : embeddings) {
			try {
				List<Double> vector = objectMapper.readValue(embedding.getVector(), new TypeReference<List<Double>>() {});

				if (vector == null || vector.isEmpty() || vector.size() != questionVector.size()) {
					log.warn("Vector inválido o de tamaño incorrecto para el embedding ID: {}", embedding.getId());
					continue;
				}

				double similarity = Similarity.cosineSimilarity(questionVector, vector);
				System.out.println("Similaridad: " + similarity + " - " + embedding.getText());

				if (similarity > 0.7 && similarity > maxSimilarity) {
					maxSimilarity = similarity;
					result = embedding.getText();
				}
			} catch (Exception e) {
				log.error("Error procesando el embedding ID {}: {}", embedding.getId(), e.getMessage(), e);
			}
		}

		return result;
	}

	@Override
	public Integer count() {
		return (int) embeddingRepository.count();
	}
}
