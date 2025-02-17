package com.java.quo;

import java.time.LocalDate;

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
public class QuoOrderDTO {
    
    private int quoNo;
    private int orderNo;
    private int bizNo;
    private String departure;
    private String dstn;
	private LocalDate deliDate;
	private LocalDate orderDate;

}
