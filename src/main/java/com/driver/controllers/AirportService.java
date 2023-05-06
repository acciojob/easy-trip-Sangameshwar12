package com.driver.controllers;

import com.driver.model.Airport;
import com.driver.model.City;
import com.driver.model.Flight;
import com.driver.model.Passenger;

import java.util.Date;
import java.util.List;

public class AirportService {

    AirportRepository airportRepository = new AirportRepository();

    public String addAirport(Airport airport){

        return airportRepository.addAirport(airport);
    }

    public String airportName(){
        return airportRepository.getLargestAirportName();
    }

    public String addflight(Flight flight){
        return airportRepository.addFlight(flight);
    }

    public String addPasseenger(Passenger passenger){
        return airportRepository.addPassenger(passenger);
    }

    public double shortDuration(City fromCity, City toCity){
        return airportRepository.shortestFlight(fromCity, toCity);
    }

    public int noOfPeople(Date date, String airportName){
        return airportRepository.noOfPeopleOnThatDate(date, airportName);
    }

    public int total(int flightId){
        return airportRepository.totalFare(flightId);
    }

    public String canceTicket(int flightId, Integer passengerId){

        return airportRepository.canceTicket(flightId, passengerId);
    }

    public int countOfBookings(int passengerId){
        return airportRepository.countOfBookings(passengerId);
    }

    public String getNameOfAir(int flightId){
        return airportRepository.nameOfAirById(flightId);
    }

    public int revenue(int flightId){
        return airportRepository.calculateRevenue(flightId);
    }
}
