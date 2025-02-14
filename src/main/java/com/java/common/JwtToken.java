package com.java.common;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.MacAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtToken {

    private MacAlgorithm ALGORITHM = Jwts.SIG.HS256;
    private String jwtSecretKey = "c2hlbGxmb2xkZXIxMjM0NTY3ODlEZXZKV1QxMjM0NTY3ODk=";
    private int interval = 1000;
    private String type = "bearer";
    private final KeyCrypt keyCrypt;

    private Map<String, String> getHeader() {
		Map<String, String> header = new HashMap<>();
		header.put("alg", "HS256");
		header.put("typ", "JWT");
		return header;
	}

    private SecretKey getKey() {
	    return Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(jwtSecretKey));
	}

    private Map<String, ?> setClaims(String bizNo) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("issuer", "MFR");
		claims.put("subject", "IMG");
		claims.put("audience", keyCrypt.encodeContent(bizNo));
		return claims;
	}

    private Date getDate() {
		Calendar date = Calendar.getInstance();
		date.add(Calendar.MINUTE, interval);
		return date.getTime();
	}

    public String getTokenFromHeader(String token) {
	    String[] strArray = token.split(" ");
	    log.info("Token Array : {}", strArray);
	    if(type.equals(strArray[0])) {
	    	log.info("Token : {}", strArray[1]);
	    	return strArray[1];
	    }
	    return null;
	}

    public String setToken(String bizNo) {
		return type + " " + Jwts.builder()
				.header().add(getHeader()).and()
				.claims(setClaims(bizNo))
				.expiration(getDate())
				.issuedAt(Calendar.getInstance().getTime())
				.signWith(getKey(), ALGORITHM)
				.compact();
	}

    public Claims getToken(String token) {
		Claims claims = Jwts.parser()
				.verifyWith(getKey())
				.build()
				.parseSignedClaims(token)
				.getPayload();
		return claims;
	}

    public String getBizNo(String token) {
		return keyCrypt.decodeContent( getToken(token).get("audience", String.class) );
	}

}
