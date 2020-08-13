package com.altemetrik.OnlineBusReservationSystem.Entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Route {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int routeId;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "busNo")
	private Bus bus;

	@OneToOne(cascade = CascadeType.ALL)
	private RouteDetail routeDetail;

	public int getRouteId() {
		return routeId;
	}

	public void setRouteId(int routeId) {
		this.routeId = routeId;
	}

	public RouteDetail getRouteDetail() {
		return routeDetail;
	}

	public void setRouteDetail(RouteDetail routeDetail) {
		this.routeDetail = routeDetail;
		if (routeDetail != null)
			routeDetail.setRoute(this);
	}

	public Bus getBus() {
		return bus;
	}

	public void setBus(Bus bus) {
		this.bus = bus;
	}

}
