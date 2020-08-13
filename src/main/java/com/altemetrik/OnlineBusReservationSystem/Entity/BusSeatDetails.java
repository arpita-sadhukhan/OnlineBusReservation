package com.altemetrik.OnlineBusReservationSystem.Entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
public class BusSeatDetails {

	@EmbeddedId
	private BusNoTravelDateEmbeddedIdClass id;
	private int seatsBooked;
	private int[] seats;


	public BusNoTravelDateEmbeddedIdClass getId() {
		return id;
	}

	public void setId(BusNoTravelDateEmbeddedIdClass id) {
		this.id = id;
	}

	public int getSeatsBooked() {
		return seatsBooked;
	}

	public void setSeatsBooked(int seatsBooked) {
		this.seatsBooked = seatsBooked;
	}

	public int[] getSeats() {
		return seats;
	}

	public void setSeats(int[] seats) {
		this.seats = seats;
	}

}
