package com.altemetrik.OnlineBusReservationSystem.Entity;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Embeddable;

@Embeddable
public class BusNoTravelDateEmbeddedIdClass implements Serializable {

	private static final long serialVersionUID = -975273891519659412L;
	private int busNo;
	private Date travelDate;

	public BusNoTravelDateEmbeddedIdClass() {}
	
	public BusNoTravelDateEmbeddedIdClass(int busNo, Date travelDate) {
		super();
		this.busNo = busNo;
		this.travelDate = travelDate;
	}

	public int getBusNo() {
		return busNo;
	}

	public void setBusNo(int busNo) {
		this.busNo = busNo;
	}

	public Date getTravelDate() {
		return travelDate;
	}

	public void setTravelDate(Date travelDate) {
		this.travelDate = travelDate;
	}

}
