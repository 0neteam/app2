package com.java.transp;

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

public class TranspModalDTO {
	private int quoNo;
	private int orderNo;
	private String departure;
	private String dstn;
	private String driverName;
	private String driverPhone;
	private String transpStatus;
	private int transpMailNo;
	private LocalDate transpDate;
}
