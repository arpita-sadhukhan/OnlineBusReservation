package com.altemetrik.OnlineBusReservationSystem.DTO;

import javax.validation.constraints.NotNull;

public class BusDTO {

	private int busNo;
	
	@NotNull(message = "Operator name cannot be null")
	private String operatorName;
	
	@NotNull(message = "No Of Seats cannot be left blank")
	private int noOfSeats;

	public int getBusNo() {
		return busNo;
	}

	public void setBusNo(int busNo) {
		this.busNo = busNo;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public int getNoOfSeats() {
		return noOfSeats;
	}

	public void setNoOfSeats(int noOfSeats) {
		this.noOfSeats = noOfSeats;
	}

}
