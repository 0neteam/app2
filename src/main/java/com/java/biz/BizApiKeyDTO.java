package com.java.biz;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BizApiKeyDTO {
    
    private int apiNo;
    private int bizNo;
    private String type;
    private String url;
    private String key;
    private String useYN;
    private LocalDateTime regDate;

}
