package com.inversionesaraujo.api.business.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailRequest {
	public String destination;
	public String subject;
	public String content;
}
