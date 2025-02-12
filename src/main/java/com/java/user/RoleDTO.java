package com.java.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.io.Serializable;  // Serializable을 임포트해야 합니다.

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleDTO implements Serializable {  // Serializable 미구현시 : org.springframework.data.redis.serializer.SerializationException: Cannot serialize

    private static final long serialVersionUID = 1L;  // 버전 관리용 serialVersionUID 추가

    private int roleNo;
    private String name;
}
