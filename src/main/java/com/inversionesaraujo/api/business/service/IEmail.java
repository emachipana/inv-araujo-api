package com.inversionesaraujo.api.business.service;

import com.inversionesaraujo.api.business.request.EmailRequest;

public interface IEmail {
	public boolean sendEmail(EmailRequest body);

	public boolean sendEmailWithAttachment(EmailRequest request, byte[] attachment, String filename);
}
