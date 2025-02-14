package com.java.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MailDTO {

	private String emailFrom;
	private String emailTo;
	private String emailSubject;
	private String emailBody;
	private boolean emailHtmlEnable;
	
}
