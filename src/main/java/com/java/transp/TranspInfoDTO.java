package com.java.transp;

import java.time.LocalDate;

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
public class TranspInfoDTO {

	private int transpMailNo;
	private int quoNo;
	private int bizNo;
	private int transpNo;
	private String driverName;
	private String driverPhone;
	private char userYN;
	private LocalDate regDate;
	
}
