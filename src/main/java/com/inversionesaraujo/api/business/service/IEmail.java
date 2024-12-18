package com.inversionesaraujo.api.business.service;

import com.inversionesaraujo.api.business.dto.request.EmailRequest;

public interface IEmail {
	public boolean sendEmail(EmailRequest body);
}
