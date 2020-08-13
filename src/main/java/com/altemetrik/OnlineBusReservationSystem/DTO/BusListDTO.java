package com.altemetrik.OnlineBusReservationSystem.DTO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class BusListDTO extends ExceptionMessage {

	private int noOfResults;
	private List<RouteDTO> routeLists;

	public int getNoOfResults() {
		return noOfResults;
	}

	public void setNoOfResults(int noOfResults) {
		this.noOfResults = noOfResults;
	}

	public List<RouteDTO> getRouteLists() {
		
		Collections.sort(routeLists, new Comparator<RouteDTO>() {
			@Override
			public int compare(RouteDTO d1, RouteDTO d2) {
				return (int) (d1.getRouteDetail().getPrice() - d2.getRouteDetail().getPrice());
			}
		});
		
		return routeLists;
	}

	public void adddRouteLists(RouteDTO route) {
		if(routeLists == null) {
			routeLists = new ArrayList<>();
		}
		routeLists.add(route);
	}

}
