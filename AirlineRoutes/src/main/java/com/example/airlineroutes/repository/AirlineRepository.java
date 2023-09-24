package com.example.airlineroutes.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.airlineroutes.Airline;

@Repository
public interface AirlineRepository extends JpaRepository<Airline, Long> {
    // You can define custom query methods if needed
	
	Airline findByCode(String code);
	
}
