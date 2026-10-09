package app;

import model.Flight;

import java.util.ArrayList;
import java.util.List;


import model.Gate;
import model.Passenger;
import model.AirportEvent;

public class AirportData {

    public static Flight[] flights = {

        new Flight("AI101", "Delhi", "Hyderabad",
                "G1", "Boarding"),

        new Flight("AI102", "Mumbai", "Delhi",
                "G2", "Scheduled"),

        new Flight("6E201", "Hyderabad", "Bangalore",
                "G3", "Delayed"),

        new Flight("EK508", "Dubai", "Hyderabad",
                "G4", "Boarding"),

        new Flight("SG401", "Chennai", "Delhi",
                "G5", "Scheduled")
    };


    public static Gate[] gates = {

        new Gate("G1", "Terminal 1", false),
        new Gate("G2", "Terminal 1", false),
        new Gate("G3", "Terminal 2", true),
        new Gate("G4", "Terminal 2", false),
        new Gate("G5", "Terminal 1", true)
    };


    public static Passenger[] passengers = {

        new Passenger("P001", "Rahul",
                "AI101", "BG1001"),

        new Passenger("P002", "Arjun",
                "AI102", "BG1002"),

        new Passenger("P003", "Priya",
                "6E201", "BG1003"),

        new Passenger("P004", "Anjali",
                "EK508", "BG1004")
    };


    public static List<AirportEvent> events =
            new ArrayList<>();

    static {

        events.add(
                new AirportEvent(
                        1,
                        "Flight AI101 is now boarding"));

        events.add(
                new AirportEvent(
                        2,
                        "Flight 6E201 is delayed"));

        events.add(
                new AirportEvent(
                        3,
                        "RUNWAY CLOSED due to maintenance"));

        events.add(
                new AirportEvent(
                        4,
                        "Flight EK508 boarding started"));

        events.add(
                new AirportEvent(
                        5,
                        "Security check completed"));
    }
}