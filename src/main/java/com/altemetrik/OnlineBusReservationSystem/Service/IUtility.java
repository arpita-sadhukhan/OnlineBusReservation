package com.altemetrik.OnlineBusReservationSystem.Service;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

import org.modelmapper.ModelMapper;

import com.altemetrik.OnlineBusReservationSystem.DTO.RouteDTO;
import com.altemetrik.OnlineBusReservationSystem.DTO.RouteDetailsDTO;
import com.altemetrik.OnlineBusReservationSystem.Entity.Route;
import com.altemetrik.OnlineBusReservationSystem.Entity.RouteDetail;

public interface IUtility {

	public default Route computeTravelTime(RouteDTO dto, ModelMapper mapper) {

		Route route = new Route();
		RouteDetailsDTO detailDto = dto.getRouteDetail();
		
		RouteDetail detail = mapper.map(detailDto, RouteDetail.class);
		
		LocalDate date = LocalDate.parse(detailDto.getDateOfTravel());

		if(detailDto.getTimeOfOrigin() != null) {
			Time departureTime = Time.valueOf(detailDto.getTimeOfOrigin());
			detail.setTimeOfOrigin(departureTime);
			
			LocalTime depTimeAsLocalTime = LocalTime.parse(detailDto.getTimeOfOrigin());
			LocalTime arrTimeAsLocalTime = depTimeAsLocalTime.plusHours(detailDto.getDuration());
			LocalTime midNight = LocalTime.of(0, 00, 00);
			
			
			
			if(arrTimeAsLocalTime.isAfter(midNight)) {
				date = date.plusDays(1);
			}
			detail.setDateOfArrival(Date.valueOf(date));
			detail.setTimeOfArrival(Time.valueOf(arrTimeAsLocalTime));
		}

		detail.setTravelTime(detailDto.getDuration());
		if(detailDto.getDateOfTravel() != null) {
			Date dateOfOrigin = Date.valueOf(detailDto.getDateOfTravel());
			detail.setDateOfOrigin(dateOfOrigin);
		}

		route.setRouteDetail(detail);
		return route;
	}
	
	public default RouteDetailsDTO populateDetailDto(RouteDetail detail, ModelMapper mapper) {
		
		RouteDetailsDTO detailDto = mapper.map(detail, RouteDetailsDTO.class);
		detailDto.setDateOfTravel(detail.getDateOfOrigin().toString());
		detailDto.setTimeOfOrigin(detail.getTimeOfOrigin().toString());
		detailDto.setTimeOfArrival(detail.getTimeOfArrival().toString());
		detailDto.setDuration(detail.getTravelTime());
		
		return detailDto;
	}
}
