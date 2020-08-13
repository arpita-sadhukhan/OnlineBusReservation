package com.altemetrik.OnlineBusReservationSystem.Service;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.altemetrik.OnlineBusReservationSystem.DTO.BookingDTO;
import com.altemetrik.OnlineBusReservationSystem.DTO.BusListDTO;
import com.altemetrik.OnlineBusReservationSystem.DTO.RouteDTO;
import com.altemetrik.OnlineBusReservationSystem.Exception.CustomException;

public interface IService {

	List<RouteDTO> fetchBusByCityAndDateDetails(String sourceCity, String destinationCity, String traveldate,
			String sort_by) throws CustomException;

	BusListDTO getBusDetailsByDate(@NotNull String travelDate) throws CustomException;

	BookingDTO makeABooking(BookingDTO dto) throws CustomException;

}
