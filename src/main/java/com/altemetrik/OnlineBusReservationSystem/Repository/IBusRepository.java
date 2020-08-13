package com.altemetrik.OnlineBusReservationSystem.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.altemetrik.OnlineBusReservationSystem.Entity.Bus;

@Repository
public interface IBusRepository extends CrudRepository<Bus, Integer> {

}
