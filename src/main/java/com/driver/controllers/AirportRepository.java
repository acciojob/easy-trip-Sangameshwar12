package com.driver.controllers;

import com.driver.model.Airport;
import com.driver.model.City;
import com.driver.model.Flight;
import com.driver.model.Passenger;

import java.util.*;

public class AirportRepository {

    public HashMap<String, Airport> AirportDb = new HashMap<>();
    public HashMap<Integer, Flight> flightDb = new HashMap<>();

    public HashMap<Integer, Passenger> passengerDb = new HashMap<>();

    public HashMap<Integer, List<Integer>> flightToPassengerDb = new HashMap<>();



    // function for adding airport details

    public String addAirport(Airport airport){

        String key = airport.getAirportName();

        AirportDb.put(key, airport);

        return "SUCCESS";
    }

    // function for finding largest airportName

    public String getLargestAirportName() {

        String largestAirPort = "";

        int greatesTerminal = 0;

        for(Airport airport : AirportDb.values()){
            if(airport.getNoOfTerminals() > greatesTerminal){
                largestAirPort = airport.getAirportName();
                greatesTerminal = airport.getNoOfTerminals();
            }
            else if(airport.getNoOfTerminals()==greatesTerminal){
                if(airport.getAirportName().compareTo(largestAirPort)<0){
                    largestAirPort = airport.getAirportName();
                }
            }
        }


        return largestAirPort;
    }

    public double shortestFlight(City fromCity, City toCity){
        double duration = 1000000000;

        for(Flight flight : flightDb.values()){
            if(flight.getFromCity().equals(fromCity) && flight.getToCity().equals(toCity)){
                duration = Math.min(duration, flight.getDuration());
            }
        }
        if(duration == 1000000000){
            return -1;
        }
        return duration;
    }


    public String addFlight(Flight flight){

        int flightId = flight.getFlightId();

        flightDb.put(flightId, flight);

        return "SUCCESS";
    }

    public String addPassenger(Passenger passenger){
        int pssngrId = passenger.getPassengerId();

        passengerDb.put(pssngrId, passenger);
        return "SUCCESS";
    }

    public int noOfPeopleOnThatDate(Date date, String airportName){
        Airport airport = AirportDb.get(airportName);
        if(Objects.isNull(airport)){
            return 0;
        }
        City city = airport.getCity();
        int count = 0;
        for(Flight flight:flightDb.values()){
            if(date.equals(flight.getFlightDate()))
                if(flight.getToCity().equals(city)||flight.getFromCity().equals(city)){

                    int flightId = flight.getFlightId();
                    count = count + flightToPassengerDb.get(flightId).size();
                }
        }
        return count;
    }

    public int totalFare(int flightId){

        int totalPeople = flightToPassengerDb.get(flightId).size();

        return totalPeople*50 + 3000;
    }

    public String bookATicket(int flightId, int passengerId){

        if(Objects.nonNull(flightToPassengerDb.get(flightId)) &&(flightToPassengerDb.get(flightId).size()<flightDb.get(flightId).getMaxCapacity())){


            List<Integer> passengers =  flightToPassengerDb.get(flightId);

            if(passengers.contains(passengerId)){
                return "FAILURE";
            }

            passengers.add(passengerId);
            flightToPassengerDb.put(flightId,passengers);
            return "SUCCESS";
        }
        else if(Objects.isNull(flightToPassengerDb.get(flightId))){
            flightToPassengerDb.put(flightId,new ArrayList<>());
            List<Integer> passengers =  flightToPassengerDb.get(flightId);

            if(passengers.contains(passengerId)){
                return "FAILURE";
            }

            passengers.add(passengerId);
            flightToPassengerDb.put(flightId,passengers);
            return "SUCCESS";

        }
        return "FAILURE";
    }

    public String canceTicket(int flightId, Integer passengerId){

        List<Integer> passengers = flightToPassengerDb.get(flightId);

        if(passengers == null){
            return "FAILURE";
        }

        if(passengers.contains(passengerId)){
            passengers.remove(passengerId);
        }

        return "FAILURE";
    }

    public int countOfBookings(int passengerId){
        HashMap<Integer,List<Integer>> passengerToFlightDb = new HashMap<>();
        //We have a list from passenger To flights database:-
        int count = 0;
        for(Map.Entry<Integer,List<Integer>> entry: flightToPassengerDb.entrySet()){

            List<Integer> passengers  = entry.getValue();
            for(Integer passenger : passengers){
                if(passenger==passengerId){
                    count++;
                }
            }
        }
        return count;
    }

    public String nameOfAirById(int flightId){

        if(flightToPassengerDb.containsKey(flightId)){
            City city = flightDb.get(flightId).getFromCity();

            for(Airport airport : AirportDb.values()){
                if(airport.getCity().equals(city)){
                    return airport.getAirportName();
                }
            }
        }
        return null;
    }

    public int calculateRevenue(int flightId){
        int noOfPeopleBooked = flightToPassengerDb.get(flightId).size();
        int variableFare = (noOfPeopleBooked*(noOfPeopleBooked+1))*25;
        int fixedFare = 3000*noOfPeopleBooked;
        int totalFare = variableFare + fixedFare;

        return totalFare;
    }

}
