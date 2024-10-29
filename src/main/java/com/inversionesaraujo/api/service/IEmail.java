package com.inversionesaraujo.api.service;

import com.inversionesaraujo.api.model.request.EmailRequest;

public interface IEmail {
	public boolean sendEmail(EmailRequest body);
}
