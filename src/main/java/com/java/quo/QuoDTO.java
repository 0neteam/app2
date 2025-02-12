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
	private String bizName;
	private String bizNum;
	private String dstn;
	private String adr;
	private LocalDate quoDate;
	private LocalDate deliDate;
	private String quoStatus;
	private String category;
}