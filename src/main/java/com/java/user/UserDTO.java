package com.java.user;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.io.Serializable;  // Serializable을 임포트해야 합니다.
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO implements Serializable {  // Serializable 인터페이스 구현

    private static final long serialVersionUID = 1L;  // 버전 관리용 serialVersionUID 추가

    private int userNo;
    private int deptNo;
    private int bizNo;
    private String name;
    private String pwd;
    private String email;
    private String phone;
    private boolean useYN;
    
    @JsonSerialize(using = LocalDateTimeSerializer.class)  // LocalDateTime 직렬화 설정
    private LocalDateTime regDate;

    private String roleName;
    private String deptName;
    private String selectRole;
    
    private String zipcode;
    private String adr;
    private String detail_adr;
    
    private String authCode;
    
    private int bizSelect;
}
