package com.inversionesaraujo.api.business.service;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface IEmbedding {
	void saveText(String text) throws JsonProcessingException;

	String answer(String question) throws JsonProcessingException;

	Integer count();
}
