package com.altemetrik.OnlineBusReservationSystem.DTO;

public class RouteDetailsDTO {

	private int detailId;
	private String sourceCity;
	private String destinationCity;
	private String dateOfTravel;
	private String timeOfOrigin;
	private String timeOfArrival;
	private long duration;
	private double price;

	public int getDetailId() {
		return detailId;
	}

	public void setDetailId(int detailId) {
		this.detailId = detailId;
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

	public String getTimeOfOrigin() {
		return timeOfOrigin;
	}

	public void setTimeOfOrigin(String timeOfOrigin) {
		this.timeOfOrigin = timeOfOrigin;
	}

	public String getTimeOfArrival() {
		return timeOfArrival;
	}

	public void setTimeOfArrival(String timeOfArrival) {
		this.timeOfArrival = timeOfArrival;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	public String getDateOfTravel() {
		return dateOfTravel;
	}

	public void setDateOfTravel(String dateOfTravel) {
		this.dateOfTravel = dateOfTravel;
	}

}
