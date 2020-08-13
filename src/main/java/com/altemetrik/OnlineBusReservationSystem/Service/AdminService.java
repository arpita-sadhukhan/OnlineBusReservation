package com.altemetrik.OnlineBusReservationSystem.Service;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.altemetrik.OnlineBusReservationSystem.DTO.BusDTO;
import com.altemetrik.OnlineBusReservationSystem.DTO.RouteDTO;
import com.altemetrik.OnlineBusReservationSystem.DTO.RouteDetailsDTO;
import com.altemetrik.OnlineBusReservationSystem.Entity.Bus;
import com.altemetrik.OnlineBusReservationSystem.Entity.Route;
import com.altemetrik.OnlineBusReservationSystem.Entity.RouteDetail;
import com.altemetrik.OnlineBusReservationSystem.Repository.IBusRepository;
import com.altemetrik.OnlineBusReservationSystem.Repository.IRouteDetailRepository;
import com.altemetrik.OnlineBusReservationSystem.Repository.IRouteRepository;

@Service
public class AdminService implements IAdminService, IUtility {

	@Autowired
	ModelMapper mapper;
	
	@Autowired
	IBusRepository busRepo;
	
	@Autowired
	IRouteRepository routeRepo;
	
	@Override
	public BusDTO addNewBus(BusDTO dto) {

		Bus bus = mapper.map(dto, Bus.class);
		Bus savedBus = busRepo.save(bus);
		
		BusDTO newBusDto = mapper.map(savedBus, BusDTO.class);
		return newBusDto;
	}

	@Override
	@Transactional
	public RouteDTO addRouteDetails(RouteDTO dto) throws Exception {

		Route route = computeTravelTime(dto, mapper);
		Optional<Bus> bus = busRepo.findById(dto.getBus().getBusNo());
		bus.orElseThrow(Exception::new);
		route.setBus(bus.get());
		
		Route savedRoute = routeRepo.save(route);
		RouteDTO newRoute = mapper.map(savedRoute, RouteDTO.class);
		RouteDetail routeDetail = savedRoute.getRouteDetail();
		
		RouteDetailsDTO detailsList = mapRouteDetails(routeDetail);
		newRoute.setRouteDetail(detailsList);
		return newRoute;
	}

	private RouteDetailsDTO mapRouteDetails(RouteDetail routeDetail) {

			RouteDetailsDTO detailsDTO = mapper.map(routeDetail, RouteDetailsDTO.class);
			detailsDTO.setDateOfTravel(routeDetail.getDateOfOrigin().toString());
			detailsDTO.setTimeOfOrigin(routeDetail.getTimeOfOrigin().toString());
			detailsDTO.setTimeOfArrival(routeDetail.getTimeOfArrival().toString());
			detailsDTO.setDuration(routeDetail.getTravelTime());
			
		return detailsDTO;
	}

}
