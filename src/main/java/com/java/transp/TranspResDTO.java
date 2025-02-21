package com.java.transp;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TranspResDTO {

    private boolean status;
    private String msg;
    private Object result;
    
}
