package com.altemetrik.OnlineBusReservationSystem.Repository;

import org.springframework.data.repository.CrudRepository;

import com.altemetrik.OnlineBusReservationSystem.Entity.Booking;

public interface IBookingRepository extends CrudRepository<Booking, Integer> {

}
