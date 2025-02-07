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
	private int quoNo;
	private int driverNo;
	private LocalDate transpDate;
	private String transpStatus;
	
}
