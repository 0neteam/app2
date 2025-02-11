package com.java.quo;

import java.util.Map;

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
public class QuoSearchDTO {
    
    private String category;
    private String search;

    public static QuoSearchDTO setDTO(Map<String, String> paramMap){
        if(paramMap.isEmpty()) {
            return QuoSearchDTO.builder().build();
        } else {
            return QuoSearchDTO.builder().category(paramMap.get("category")).search(paramMap.get("search")).build();
        }
    }

}
