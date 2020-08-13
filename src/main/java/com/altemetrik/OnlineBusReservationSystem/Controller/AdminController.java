package com.altemetrik.OnlineBusReservationSystem.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.altemetrik.OnlineBusReservationSystem.DTO.BusDTO;
import com.altemetrik.OnlineBusReservationSystem.DTO.RouteDTO;
import com.altemetrik.OnlineBusReservationSystem.Service.IAdminService;

@RestController
@RequestMapping("/admin/add")
public class AdminController {

	@Autowired
	private IAdminService service;
	
	@PostMapping("/bus")
	public ResponseEntity<BusDTO> addNewBus(@RequestBody BusDTO dto){
		
		if(dto != null) {
			BusDTO newBusDetails = service.addNewBus(dto);
			if(newBusDetails != null) {
				return ResponseEntity.status(HttpStatus.CREATED).body(newBusDetails);
			}
		}
		return null;
	}
	
	@PostMapping("/routeDetails")
	public ResponseEntity<RouteDTO> addBusRoutes(@RequestBody RouteDTO dto) throws Exception{
		
		if(dto != null) {
			RouteDTO routeDto = service.addRouteDetails(dto);
			if(routeDto != null) {
				return ResponseEntity.status(HttpStatus.CREATED).body(routeDto);
			}
		}
		return null;
	}
}
