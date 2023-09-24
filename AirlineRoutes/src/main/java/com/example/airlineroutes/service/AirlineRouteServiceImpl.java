package com.example.airlineroutes.service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.airlineroutes.Airline;
import com.example.airlineroutes.AirlineRoute;
import com.example.airlineroutes.Airport;
import com.example.airlineroutes.controller.GetDetailedItineraryResponse;
import com.example.airlineroutes.repository.AirlineRepository;
import com.example.airlineroutes.repository.AirlineRouteRepo;
import com.example.airlineroutes.repository.AirportRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
	
@Service



public class AirlineRouteServiceImpl implements AirlineRouteService {

 

	@Autowired
	AirlineRouteRepo airlineRouteRepo;
	
	@Autowired
	RestTemplate airportConfig;
	
	@Autowired
	private AirlineRepository ar;
	
	@Autowired
	private AirportRepository airportRepo;

	public List<AirlineRoute> getAllAirlineRoutes() {
		List<AirlineRoute> dataList = new ArrayList();
		ObjectMapper objectMapper = new ObjectMapper();
	try {
			// Specify the path to your JSON file
			File jsonFile = new File("src/main/resources/json/flightsDB.routes_v2.json");
			// Read JSON data into a List of MyJsonObject
			dataList = objectMapper.readValue(jsonFile, new TypeReference<List<AirlineRoute>>() {
			});

		// Now you can work with the 'dataList' which contains a list of JSON objects.
			for (AirlineRoute obj : dataList) {
				airlineRouteRepo.save(obj);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return dataList;

 

}

 

	public List<AirlineRoute> getAllRoutes() {
		return airlineRouteRepo.findAll();
	}

 

	
//	public List<AirlineRoute> getBestRecommendations(AirlineRoute request) {
//		// TODO Auto-generated method stub
//		return null;
//	}

 

	


//	public List<AirlineRoute> getBestRecommendations(AirlineRoute request) {
//		String iataFrom =request.getIataFrom();
//		String iataTo=request.getIataTo();
//		boolean classBusiness=request.isClassBusiness();
//		boolean classEconomy=request.isClassEconomy();
//		boolean classFirst=request.isClassFirst();
//		List<AirlineRoute>recommendations=airlineRouteRepo.findBestRoutes(iataFrom, iataTo, classBusiness, classEconomy, classFirst,);
//		return recommendations;
//	}

 @Override
		public List<GetDetailedItineraryResponse> getDetailedItinerary(List<Integer> routeId) {
			List<GetDetailedItineraryResponse> itineraryList = new ArrayList<>();
			for (int id : routeId) {

	 

				AirlineRoute getRoute = airlineRouteRepo.findById(id).get();
				String airlineIata = getRoute.getAirLineIata();
				String airportToIata = getRoute.getIataTo();
				String airportFromIata = getRoute.getIataFrom();
				Airline getAirline = airportConfig
						.getForObject("http://localhost:8300/api/routes/getAirline/" + airlineIata, Airline.class);
				Airport getAirportTo = airportConfig
						.getForObject("http://localhost:8300/api/routes/getAirport/" + airportToIata, Airport.class);
				Airport getAirportFrom = airportConfig
						.getForObject("http://localhost:8300/api/routes/getAirport/" + airportFromIata, Airport.class);
				GetDetailedItineraryResponse itineraryResponse = new GetDetailedItineraryResponse(id, getAirportFrom,
						getAirportTo, getAirline);
				itineraryList.add(itineraryResponse);
			}

	 

			return itineraryList;
		}

 
 
 //AirlineService
 
 public String readJsonFromFileSystem(String filePath) throws IOException {
     byte[] bytes = Files.readAllBytes(Paths.get(filePath));
     String jsonContent= new String(bytes, StandardCharsets.UTF_8);
     ObjectMapper objectMapper = new ObjectMapper(); // Assuming you have Jackson ObjectMapper
     Airline[] airline=objectMapper.readValue(jsonContent,Airline[].class);
     List<Airline> airlines=new ArrayList();
     for(Airline am:airline) {
     	airlines.add(am);
     }

     ar.saveAll(airlines);

     return  new String(bytes, StandardCharsets.UTF_8);
//     ObjectMapper objectMapper = new ObjectMapper();
 }


 public List<Airline> getAllAirlines() {
     return ar.findAll();
 }
 public Airline getAirlineByCode(String code) {
     return ar.findByCode(code); // Assuming you have a method findByAirlineCode in your AirlineRepository
 }
 
 //AirportService
 

public String readJsonFromFileSystems(String filePath) throws IOException {
    byte[] bytes = Files.readAllBytes(Paths.get(filePath));
    String jsonContent= new String(bytes, StandardCharsets.UTF_8);
    ObjectMapper objectMapper = new ObjectMapper(); // Assuming you have Jackson ObjectMapper
    Airport[] airline=objectMapper.readValue(jsonContent,Airport[].class);
    List<Airport> airlines=new ArrayList();
    for(Airport am:airline) {
    	airlines.add(am);
    }
for(Airport am:airline) {
	try {
    airportRepo.save(am);
	}
	catch (Exception e) {
		// TODO: handle exception
		
		System.out.println(am.getState());
		
	}
}

    return  new String(bytes, StandardCharsets.UTF_8);
//    ObjectMapper objectMapper = new ObjectMapper();
}
public List<Airport> getAllAirports() {
    return airportRepo.findAll();
}
public Airport getAirportByCode(String code) {
    return airportRepo.findByCode(code); // Assuming you have a method findByAirlineCode in your AirlineRepository
}   

}

 