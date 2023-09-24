package com.example.airlineroutes.service;

import java.util.List;

import com.example.airlineroutes.AirlineRoute;
import com.example.airlineroutes.controller.GetDetailedItineraryResponse;

public interface AirlineRouteService {
	public List<AirlineRoute> getAllAirlineRoutes();
	public List<AirlineRoute> getAllRoutes();
//	List<AirlineRoute>getBestRecommendations(AirlineRoute request);
	public List<GetDetailedItineraryResponse> getDetailedItinerary(List<Integer> routeIds);
	
	}
