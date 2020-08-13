package com.altemetrik.OnlineBusReservationSystem.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.StringTokenizer;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.validation.constraints.NotNull;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.altemetrik.OnlineBusReservationSystem.DTO.BookingDTO;
import com.altemetrik.OnlineBusReservationSystem.DTO.BusDTO;
import com.altemetrik.OnlineBusReservationSystem.DTO.BusListDTO;
import com.altemetrik.OnlineBusReservationSystem.DTO.RouteDTO;
import com.altemetrik.OnlineBusReservationSystem.DTO.RouteDetailsDTO;
import com.altemetrik.OnlineBusReservationSystem.DTO.UserDTO;
import com.altemetrik.OnlineBusReservationSystem.Entity.Booking;
import com.altemetrik.OnlineBusReservationSystem.Entity.Bus;
import com.altemetrik.OnlineBusReservationSystem.Entity.BusNoTravelDateEmbeddedIdClass;
import com.altemetrik.OnlineBusReservationSystem.Entity.BusSeatDetails;
import com.altemetrik.OnlineBusReservationSystem.Entity.Route;
import com.altemetrik.OnlineBusReservationSystem.Entity.RouteDetail;
import com.altemetrik.OnlineBusReservationSystem.Entity.User;
import com.altemetrik.OnlineBusReservationSystem.Exception.CustomException;
import com.altemetrik.OnlineBusReservationSystem.Repository.IBookingRepository;
import com.altemetrik.OnlineBusReservationSystem.Repository.IBusRepository;
import com.altemetrik.OnlineBusReservationSystem.Repository.IBusSeatDetailRepository;
import com.altemetrik.OnlineBusReservationSystem.Repository.IRouteDetailRepository;
import com.altemetrik.OnlineBusReservationSystem.Repository.IRouteRepository;
import com.altemetrik.OnlineBusReservationSystem.Repository.IUserRepository;

@Service
public class ServiceImpl implements IService, IUtility {

	@Autowired
	IRouteDetailRepository routeDetailRepo;
	
	@Autowired
	IRouteRepository routeRepo;
	
	@Autowired
	ModelMapper mapper;
	
	@Autowired
	IUserRepository userRepo;
	
	@Autowired
	IBusRepository busRepo;
	
	@Autowired
	IBusSeatDetailRepository busSeatRepo;
	
	@Autowired
	IBookingRepository bookingRepo;
	
	@Override
	@Transactional
	public List<RouteDTO> fetchBusByCityAndDateDetails(String sourceCity, String destinationCity,
			String traveldate, String sort_by) throws CustomException {

		List<RouteDTO> routeDtoList = null;
		
		Sort sort = orderBy(sort_by);
		Optional<List<RouteDetail>> routeListOpt = routeDetailRepo.findBySourceCityAndDestinationCityAndDateOfOrigin(sourceCity, destinationCity, Date.valueOf(traveldate), sort);

		if(routeListOpt.isPresent()) {
			routeDtoList = new ArrayList<>();
			List<RouteDetail> routeList = routeListOpt.get();
			for(RouteDetail routeDetail : routeList) {

				Route route = routeRepo.findRouteByRouteDetail(routeDetail);
				RouteDTO routeDto = mapper.map(route, RouteDTO.class);
				Bus bus = route.getBus();
				BusDTO busDTO = mapper.map(bus, BusDTO.class);
				routeDto.setBus(busDTO);
				
				RouteDetailsDTO routeDetailsDTO = populateDetailDto(routeDetail, mapper);
				routeDto.setRouteDetail(routeDetailsDTO);
				
				routeDtoList.add(routeDto);
			}
		}else {
			throw new CustomException("No Route list found for the search Criteria");
		}
		return routeDtoList;
	}

	private Sort orderBy(String sort_by) {
		return Sort.by(sort_by);
	}

	@Override
	public BusListDTO getBusDetailsByDate(@NotNull String travelDate) throws CustomException {

		BusListDTO busListDto = new BusListDTO();
		
		RouteDetail detail = new RouteDetail();
		detail.setDateOfOrigin(Date.valueOf(travelDate));
		
		Optional<List<RouteDetail>> routeDetOpt = routeDetailRepo.findByDateOfOrigin(Date.valueOf(travelDate));
		routeDetOpt.orElseThrow(() -> new CustomException("No buses available on the given date"));
		
		List<RouteDetail> routeDetList = routeDetOpt.get();
		List<Integer> detIdList = routeDetList.stream().map(det -> det.getDetailsId()).collect(Collectors.toList());
		
		Optional<List<Route>> routeOpt = routeRepo.findByRouteDetailDetailsIdIn(detIdList);
		
		routeOpt.orElseThrow(() -> new CustomException("Internal server error"));
		
		for(Route route : routeOpt.get()) {
			
			RouteDetail routeDetail = route.getRouteDetail();
			RouteDetailsDTO routeDetailsDTO = populateDetailDto(routeDetail, mapper);
			
			RouteDTO routeDTO = mapper.map(route, RouteDTO.class);
			routeDTO.setRouteDetail(routeDetailsDTO);
			
			busListDto.adddRouteLists(routeDTO);
		}
		busListDto.setNoOfResults(routeOpt.get().size());
		return busListDto;
	}

	@Override
	@Transactional
	public BookingDTO makeABooking(BookingDTO dto) throws CustomException {

		User user = fetchUser(dto);
		Route route = fetchRoute(dto);
		
		BookingDTO bookingDto = null;
		
		boolean seatsAvailable = checkSeatAvailability(dto, route);
		if(seatsAvailable) {
			Booking booking = createBooking(user, route.getBus(), dto);
			bookingDto = mapper.map(booking, BookingDTO.class);
			
			bookingDto.setUser(mapper.map(user, UserDTO.class));
			RouteDetailsDTO routeDetDto = populateDetailDto(route.getRouteDetail(), mapper);
			RouteDTO routeDto = mapper.map(route, RouteDTO.class);
			routeDto.setRouteDetail(routeDetDto);
			bookingDto.setRoute(routeDto);
		}
		return bookingDto;
	}

	private Booking createBooking(User user, Bus bus, BookingDTO dto) {

		Booking booking = new Booking();
		booking.setBus(bus);
		booking.setUser(user);
		booking.setNoOfSeats(dto.getNoOfSeats());
		booking.setTravelDate(Date.valueOf(dto.getRoute().getRouteDetail().getDateOfTravel()));
		
		Booking newBooking = bookingRepo.save(booking);
		
		return newBooking;
	}

	private boolean checkSeatAvailability(BookingDTO dto, Route route) throws CustomException {

		boolean seatAvailability = false;

		int busNo = route.getBus().getBusNo();
		Date travelDate = route.getRouteDetail().getDateOfOrigin();

		BusNoTravelDateEmbeddedIdClass idCls = new BusNoTravelDateEmbeddedIdClass(busNo, travelDate);

		Optional<BusSeatDetails> seatDetOpt = busSeatRepo.findById(idCls);
		BusSeatDetails seatDetails = null;
		
		if (!seatDetOpt.isPresent()) {
			seatDetails = new BusSeatDetails();
			seatDetails.setId(idCls);
		} else {
			seatDetails = seatDetOpt.get();
		}
		int requiredSeats = dto.getNoOfSeats();
		int totalCapacity = route.getBus().getNoOfSeats();

		if (totalCapacity - seatDetails.getSeatsBooked() >= requiredSeats) {

			int[] bookedSeats = seatDetails.getSeats();
			List<Integer> bookingSeatList = new ArrayList<>();
			StringTokenizer tokens = new StringTokenizer(dto.getSeats(), ",");

			while (tokens.hasMoreTokens()) {
				bookingSeatList.add(Integer.parseInt(tokens.nextToken()));
			}

			List<Integer> asList = Arrays.stream(bookedSeats).boxed().collect(Collectors.toList());
			if (bookedSeats != null && asList != null) {
				
				for(Integer i : asList) {
					if(bookingSeatList.contains(i))
						throw new CustomException("One or more among the seats you are trying to book, is already booked");
				}
			} 
				int length = bookedSeats != null ? bookedSeats.length : 0;
				int totalSeatsAfterCurrentBooking = length + bookingSeatList.size();
				int[] revisedBookedSeats = new int[totalSeatsAfterCurrentBooking];
				
				if(bookedSeats != null) {
					for(int i = 0; i<bookedSeats.length; i++) {
						revisedBookedSeats[i] = bookedSeats[i];
					}
				}
				
				int index = length;
				for (int i : bookingSeatList) {
					revisedBookedSeats[index++] = i;
				}
				seatDetails.setSeats(revisedBookedSeats);
				seatDetails.setSeatsBooked(totalSeatsAfterCurrentBooking);

				busSeatRepo.save(seatDetails);
				seatAvailability = true;
			
		}

		return seatAvailability;
	}

	private Route fetchRoute(BookingDTO dto) throws CustomException {

		Route route = null;
		
		if(dto.getRoute() != null && dto.getRoute().getBus() != null && dto.getRoute().getRouteDetail() != null) {
			
			BusDTO busDTO = dto.getRoute().getBus();
			Bus bus = mapper.map(busDTO, Bus.class);
			RouteDetail routeDetail = computeTravelTime(dto.getRoute(), mapper).getRouteDetail();
			Optional<List<RouteDetail>> routeDetList = routeDetailRepo
					.findBySourceCityAndDestinationCityAndDateOfOrigin(routeDetail.getSourceCity(), routeDetail.getDestinationCity(), routeDetail.getDateOfOrigin(), Sort.by("price"));
			
			routeDetList.orElseThrow(() -> new CustomException("Route not Found for source city - "+routeDetail.getSourceCity() + "destination city - "+routeDetail.getDestinationCity() + "and travel date - "+routeDetail.getDateOfOrigin()));
			for(RouteDetail det : routeDetList.get()) {
				route = det.getRoute();
				if(route.getBus() != null && route.getBus().getBusNo() == busDTO.getBusNo())
					break;
			}
		}
		return route;
	}

	private User fetchUser(BookingDTO dto) throws CustomException {
		
		User user = null;
		if(dto.getUser() != null) {
			UserDTO userDto = dto.getUser();
			Optional<User> userOpt = userRepo.findById(userDto.getUserId());
			
			userOpt.orElseThrow(() -> new CustomException("User not Found"));
			
			user = mapper.map(userOpt.get(), User.class);
		}
		return user;
	}

	
}
