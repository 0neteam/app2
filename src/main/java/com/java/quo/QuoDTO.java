package com.java.quo;

import java.util.Date;

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

public class QuoDTO {
	
	private int quoNo;
	private int orderNo;
	private int bizNo;
	private Date quoDate;
	private String quoStatus;
	private int qty;
	private String departure;
}