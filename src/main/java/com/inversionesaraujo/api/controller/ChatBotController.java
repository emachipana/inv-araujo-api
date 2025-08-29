package com.inversionesaraujo.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inversionesaraujo.api.business.service.IClientChat;
import com.inversionesaraujo.api.business.request.ClientChatRequest;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/chatbot")
public class ChatBotController {
	@Autowired
	private IClientChat clientChat;

	@PostMapping("/client")
	public ResponseEntity<String> answer(@Valid @RequestBody ClientChatRequest request) {
		return ResponseEntity.ok().body(clientChat.answer(request.getQuestion()));
	}
}
	