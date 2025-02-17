package com.java.quo;

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
public class QuoQtyDiffDTO {
    
    private int itemCode;
    private String name;
    private int mfrQty;
    private int stgQty;
    private int diff;

}
