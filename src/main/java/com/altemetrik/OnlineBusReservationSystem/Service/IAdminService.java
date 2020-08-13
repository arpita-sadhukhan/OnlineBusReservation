package com.altemetrik.OnlineBusReservationSystem.Service;

import com.altemetrik.OnlineBusReservationSystem.DTO.BusDTO;
import com.altemetrik.OnlineBusReservationSystem.DTO.RouteDTO;

public interface IAdminService {

	BusDTO addNewBus(BusDTO dto);

	RouteDTO addRouteDetails(RouteDTO dto) throws Exception;

}
