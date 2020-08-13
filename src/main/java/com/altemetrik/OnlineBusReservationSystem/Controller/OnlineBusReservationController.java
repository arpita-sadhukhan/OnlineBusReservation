package com.altemetrik.OnlineBusReservationSystem.Controller;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.altemetrik.OnlineBusReservationSystem.DTO.BookingDTO;
import com.altemetrik.OnlineBusReservationSystem.DTO.BusDetailsDTO;
import com.altemetrik.OnlineBusReservationSystem.DTO.BusListDTO;
import com.altemetrik.OnlineBusReservationSystem.DTO.RouteDTO;
import com.altemetrik.OnlineBusReservationSystem.Exception.CustomException;
import com.altemetrik.OnlineBusReservationSystem.Service.IService;

@RestController
public class OnlineBusReservationController {

	@Autowired
	IService service;

	@GetMapping("/bus")
	public ResponseEntity<BusDetailsDTO> fetchBusDetails(@RequestParam String sourceCity,
			@RequestParam String destinationCity, @RequestParam String traveldate, @RequestParam String returnDate,
			@RequestParam String sort_by) {

		BusDetailsDTO busDetailDto = new BusDetailsDTO();
		HttpStatus status = HttpStatus.OK;
		try {
			if (sourceCity != null && destinationCity != null && traveldate != null) {
				List<RouteDTO> busList = service.fetchBusByCityAndDateDetails(sourceCity, destinationCity, traveldate,
						sort_by);
				busDetailDto.setOneWayBusList(busList);

				if (returnDate != null && !returnDate.isEmpty()) {
					List<RouteDTO> returnBusList = service.fetchBusByCityAndDateDetails(destinationCity, sourceCity,
							returnDate, sort_by);

					busDetailDto.setReturnBusList(returnBusList);
				}
			}
		} catch (CustomException e) {
			busDetailDto = new BusDetailsDTO();
			busDetailDto.setErrorMessage(e.getMessage());
			busDetailDto.setErrorCode(HttpStatus.BAD_REQUEST.value());

			status = HttpStatus.BAD_REQUEST;
		}
		return ResponseEntity.status(status).body(busDetailDto);
	}

	@GetMapping("/bus/date")
	public ResponseEntity<BusListDTO> getBusDetailsByDate(@NotNull @RequestParam String travelDate) {

		BusListDTO busListDto = null;
		HttpStatus status = HttpStatus.OK;
		try {
			busListDto = service.getBusDetailsByDate(travelDate);
		} catch (Exception e) {
			status = HttpStatus.BAD_REQUEST;
			e.printStackTrace();
		}

		return ResponseEntity.status(status).body(busListDto);
	}

	@PostMapping("/booking")
	public ResponseEntity<BookingDTO> bookABus(@RequestBody BookingDTO dto) {

		HttpStatus status = HttpStatus.CREATED;
		BookingDTO bookedDto = null;
		try {
			if (dto != null && dto.getUser() != null && dto.getRoute() != null) {
				bookedDto = service.makeABooking(dto);

				if (bookedDto == null) {
					status = HttpStatus.BAD_REQUEST;
				}
			}
		} catch (CustomException e) {
			bookedDto = new BookingDTO();
			bookedDto.setErrorMessage(e.getMessage());
			bookedDto.setErrorCode(HttpStatus.BAD_REQUEST.value());
			status = HttpStatus.BAD_REQUEST;
		}
		return ResponseEntity.status(status).body(bookedDto);
	}
}
