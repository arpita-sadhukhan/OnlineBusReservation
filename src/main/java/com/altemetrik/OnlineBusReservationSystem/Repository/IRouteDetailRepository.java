package com.altemetrik.OnlineBusReservationSystem.Repository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.altemetrik.OnlineBusReservationSystem.Entity.Route;
import com.altemetrik.OnlineBusReservationSystem.Entity.RouteDetail;

public interface IRouteDetailRepository extends JpaRepository<RouteDetail, Integer> {

	Optional<List<RouteDetail>> findBySourceCityAndDestinationCityAndDateOfOrigin(String sourceCity, String destinationCity, Date dateOfOrigin, Sort sort);
	
	Optional<List<RouteDetail>> findByDateOfOrigin(Date dateOfOrigin);
}
