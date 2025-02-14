package com.java.common;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.java.user.MyUserDTO;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class UniFunc {
	
	private final JavaMailSender mailSender;

	public int getUserNo() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth != null) {
			MyUserDTO userDTO = (MyUserDTO) auth.getPrincipal();
			return userDTO.getNo();
		}
		return 0;
	}
	
	public boolean sendMail(MailDTO mailDTO) {
		MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(mailDTO.getEmailFrom());
            helper.setTo(mailDTO.getEmailTo());
            helper.setSubject(mailDTO.getEmailSubject());
            helper.setText(mailDTO.getEmailBody(), mailDTO.isEmailHtmlEnable());
            mailSender.send(message);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
	}
	
}

