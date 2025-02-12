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

public class QuoModalDTO {
	private int quoNo;
	private LocalDate quoDate;
	private int itemCode;
	private int price;
	private String name;
	private int qty;
	private LocalDate deliDate;
	private String dstn;

}
