package com.altemetrik.OnlineBusReservationSystem.Entity;

import java.sql.Date;
import java.sql.Time;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;


@Entity
public class RouteDetail {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int detailsId;
	
	@OneToOne(cascade = CascadeType.ALL)
	private Route route;

	@NotNull
	private String sourceCity;

	@NotNull
	private String destinationCity;

	@NotNull
	private Time timeOfOrigin;
	
	@NotNull
	private Date dateOfOrigin;
	
	@NotNull
	private Time timeOfArrival;
	
	@NotNull
	private Date dateOfArrival;

	@NotNull
	private long travelTime;

	@NotNull
	private double price;

	public int getDetailsId() {
		return detailsId;
	}

	public void setDetailsId(int detailsId) {
		this.detailsId = detailsId;
	}

	public String getSourceCity() {
		return sourceCity;
	}

	public void setSourceCity(String sourceCity) {
		this.sourceCity = sourceCity;
	}

	public String getDestinationCity() {
		return destinationCity;
	}

	public void setDestinationCity(String destinationCity) {
		this.destinationCity = destinationCity;
	}

	public Time getTimeOfOrigin() {
		return timeOfOrigin;
	}

	public void setTimeOfOrigin(Time timeOfOrigin) {
		this.timeOfOrigin = timeOfOrigin;
	}

	public long getTravelTime() {
		return travelTime;
	}

	public void setTravelTime(long travelTime) {
		this.travelTime = travelTime;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}


	public Time getTimeOfArrival() {
		return timeOfArrival;
	}

	public void setTimeOfArrival(Time timeOfArrival) {
		this.timeOfArrival = timeOfArrival;
	}

	public Date getDateOfOrigin() {
		return dateOfOrigin;
	}

	public void setDateOfOrigin(Date dateOfOrigin) {
		this.dateOfOrigin = dateOfOrigin;
	}

	public Date getDateOfArrival() {
		return dateOfArrival;
	}

	public void setDateOfArrival(Date dateOfArrival) {
		this.dateOfArrival = dateOfArrival;
	}

	public Route getRoute() {
		return route;
	}

	public void setRoute(Route route) {
		this.route = route;
	}

}
