package com.altemetrik.OnlineBusReservationSystem.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.altemetrik.OnlineBusReservationSystem.Entity.User;

@Repository
public interface IUserRepository extends CrudRepository<User, Integer>{

}
