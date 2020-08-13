package com.altemetrik.OnlineBusReservationSystem.Entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.springframework.lang.NonNull;

@Entity
public class Bus {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int busNo;

	@NonNull
	private String operatorName;

	@NonNull
	private int noOfSeats;
	
	@OneToMany
	private List<Route> routes;

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

	public List<Route> getRoutes() {
		return routes;
	}

	public void addRoutes(Route route) {
		if(routes == null) {
			routes = new ArrayList<>();
		}
		routes.add(route);
		route.setBus(this);
	}

}
