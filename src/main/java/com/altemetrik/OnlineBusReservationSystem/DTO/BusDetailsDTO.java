package com.altemetrik.OnlineBusReservationSystem.DTO;

import java.util.List;

public class BusDetailsDTO extends ExceptionMessage {

	private List<RouteDTO> oneWayBusList;
	private List<RouteDTO> returnBusList;

	public List<RouteDTO> getOneWayBusList() {
		return oneWayBusList;
	}

	public void setOneWayBusList(List<RouteDTO> oneWayBusList) {
		this.oneWayBusList = oneWayBusList;
	}

	public List<RouteDTO> getReturnBusList() {
		return returnBusList;
	}

	public void setReturnBusList(List<RouteDTO> returnBusList) {
		this.returnBusList = returnBusList;
	}

}
