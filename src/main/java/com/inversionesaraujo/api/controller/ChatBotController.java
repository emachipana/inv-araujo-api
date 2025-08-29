package com.inversionesaraujo.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inversionesaraujo.api.business.service.IAdminChat;
import com.inversionesaraujo.api.business.service.IClientChat;
import com.inversionesaraujo.api.business.payload.MessageResponse;
import com.inversionesaraujo.api.business.request.ChatRequest;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/chatbot")
public class ChatBotController {
	@Autowired
	private IClientChat clientChat;
	@Autowired
	private IAdminChat adminChat;

	@PostMapping("/client")
	public ResponseEntity<MessageResponse> answerClient(@Valid @RequestBody ChatRequest request) {
		String answer = clientChat.answer(request.getQuestion());

		return ResponseEntity.ok().body(
			MessageResponse
				.builder()
				.data(answer)
				.message("Respuesta del chatbot")
				.build()
			);
	}

	@PostMapping("/admin")
	public ResponseEntity<String> answerAdmin(@Valid @RequestBody ChatRequest request) {
		return ResponseEntity.ok().body(adminChat.answer(request.getQuestion()));
	}
}
