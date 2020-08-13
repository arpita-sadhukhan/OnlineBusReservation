package com.altemetrik.OnlineBusReservationSystem.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.altemetrik.OnlineBusReservationSystem.Entity.BusNoTravelDateEmbeddedIdClass;
import com.altemetrik.OnlineBusReservationSystem.Entity.BusSeatDetails;

@Repository
public interface IBusSeatDetailRepository extends CrudRepository<BusSeatDetails, BusNoTravelDateEmbeddedIdClass> {

}
