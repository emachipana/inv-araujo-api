package com.inversionesaraujo.api.business.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class EmailRequest {
	public String destination;
	public String subject;
	public String content;
}
