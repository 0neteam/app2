package com.java.quo;

import java.time.LocalDate;
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
	private LocalDate quoDate;
	private String quoStatus;
	private String useYN;
	private String departure;
	private String dstn;
	private LocalDate deliDate;
	private LocalDate orderDate;
	
	private String bizName;
	private String bizNum;
	private String adr;
	private String category;

}