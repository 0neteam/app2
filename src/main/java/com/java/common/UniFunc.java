package com.java.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;

import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.java.user.MyUserDTO;


import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@RequiredArgsConstructor
@Component
public class UniFunc {
	
	//메일 발송
	private final JavaMailSender mailSender;
	
	// 영문 대소문자 + 숫자 조합
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";    
    private static final SecureRandom random = new SecureRandom();
    
    // redis 인증코드 만료시간 설정
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    //로그인 UserNo 구하기
	public int getUserNo() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth != null) {
			MyUserDTO userDTO = (MyUserDTO) auth.getPrincipal();
			return userDTO.getNo();
		}
		return 0;
	}
	
	// 메일 발송
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
	
    // 템플릿을 이용한 이메일 내용 생성
    public String generateEmailContent(String authCode, String tempatePath) {
        // 이메일 템플릿에서 ${authCode} 부분을 실제 인증 코드로 치환
        String emailContent = loadEmailTemplate(tempatePath);
        emailContent = emailContent.replace("${authCode}", authCode);
        return emailContent;
    }
	
	
	// 리소스 디렉토리에서 이메일 템플릿을 읽어오기
    private String loadEmailTemplate(String tempatePath) {
        try {
            // `resources/templates/emailTemplate.html` 파일을 로드
            Resource resource = new ClassPathResource(tempatePath);
            // 파일 내용을 문자열로 읽어오기
            return new String(Files.readAllBytes(Paths.get(resource.getURI())));
        } catch (IOException e) {
            e.printStackTrace();
            return "이메일 템플릿 로드를 확인해주세요.";
        }
    }
    
	
	// 입력한 길이의 영문 대소문자 인증코드 생성
	public static String generateRandomCode(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(index));
        }
        return sb.toString();
    }
	
	//Redis 초 간격 인증코드 저장
	public void saveVerificationCode(String code, int ValidSecondTime) {
        stringRedisTemplate.opsForValue().set("verification_code:" + code, code, ValidSecondTime, TimeUnit.SECONDS);
    }
	
	public boolean verifyCode(String inputCode) {
        // Redis에서 저장된 인증 코드 조회
        String storedCode = stringRedisTemplate.opsForValue().get("verification_code:" + inputCode);

        // 입력한 인증 코드와 저장된 인증 코드 비교
        if (storedCode != null && storedCode.equals(inputCode)) {
            return true;  // 인증 코드 일치
        } else {
            return false; // 인증 코드 불일치 또는 만료됨
        }
    }
	
}

