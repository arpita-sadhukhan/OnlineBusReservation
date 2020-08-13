package com.altemetrik.OnlineBusReservationSystem.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.altemetrik.OnlineBusReservationSystem.Entity.Bus;
import com.altemetrik.OnlineBusReservationSystem.Entity.Route;
import com.altemetrik.OnlineBusReservationSystem.Entity.RouteDetail;

public interface IRouteRepository extends CrudRepository<Route, Integer> {

	Route findRouteByRouteDetail(RouteDetail routeDetail);
	Optional<List<Route>> findAllRouteByRouteDetail(RouteDetail routeDetail);
	
	Optional<List<Route>> findByRouteDetailDetailsIdIn(List<Integer> idList);
	
	Route findByRouteDetailAndBus(RouteDetail routeDetail, Bus bus);
	Route findByBusBusNoAndRouteDetailDetailsId(int busNo, int detailsid);
}
