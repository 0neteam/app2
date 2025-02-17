package com.java.transp;

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
public class TranspQuoDTO {

	private int quoNo;
    private int orderNo;
    private int quoItemNo;
    private int itemCode;
    private int qty;
    private String departure;
    private String dstn;

}
