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
public class TranspDTO {

	private int transpNo;
	private int orderNo;
	private String departure;
	private String dstn;
	private String driverName;
	private String transpStatus;
	private String driverPhone;
	private LocalDate transpDate;
}
