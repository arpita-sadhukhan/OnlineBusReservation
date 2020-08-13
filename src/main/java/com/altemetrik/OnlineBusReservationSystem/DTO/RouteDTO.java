package com.altemetrik.OnlineBusReservationSystem.DTO;

import javax.validation.constraints.NotNull;

public class RouteDTO extends ExceptionMessage{

	private int routeId;

	@NotNull(message = "Bus details cant be null")
	private BusDTO bus;

	@NotNull(message = "Route details are left blank")
	private RouteDetailsDTO routeDetail;

	public int getRouteId() {
		return routeId;
	}

	public void setRouteId(int routeId) {
		this.routeId = routeId;
	}

	public BusDTO getBus() {
		return bus;
	}

	public void setBus(BusDTO bus) {
		this.bus = bus;
	}

	public RouteDetailsDTO getRouteDetail() {
		return routeDetail;
	}

	public void setRouteDetail(RouteDetailsDTO routeDetail) {
		this.routeDetail = routeDetail;
	}

}
