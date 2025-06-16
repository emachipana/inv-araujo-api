package com.inversionesaraujo.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.inversionesaraujo.api.business.payload.MessageResponse;
import com.inversionesaraujo.api.business.request.DataEmbedRequest;
import com.inversionesaraujo.api.business.request.QuestionRequest;
import com.inversionesaraujo.api.business.service.IEmbedding;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/chatbot")
public class ChatBotController {
	@Autowired
	private IEmbedding embeddingService;

	@PostMapping("/question")
	public ResponseEntity<MessageResponse> answer(@RequestBody @Valid QuestionRequest request) throws JsonProcessingException {
		String answer = embeddingService.answer(request.getQuestion());

		return ResponseEntity.ok().body(MessageResponse
			.builder()
			.message("La respuesta se genero con éxito")
			.data(answer)
			.build());
	}

	@PostMapping("/add-data")
	public ResponseEntity<MessageResponse> addData(@RequestBody @Valid DataEmbedRequest request) throws JsonProcessingException {
		embeddingService.saveText(request.getText());

		return ResponseEntity.ok().body(MessageResponse
			.builder()
			.message("El texto se guardo con éxito")
			.build());
	}
}
