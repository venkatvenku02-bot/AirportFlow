package model;

public class Passenger {

    private String passengerId;
    private String name;
    private String flightNumber;
    private String baggageCode;

    public Passenger(String passengerId, String name,
                     String flightNumber, String baggageCode) {

        this.passengerId = passengerId;
        this.name = name;
        this.flightNumber = flightNumber;
        this.baggageCode = baggageCode;
    }

    public String getPassengerId() {
        return passengerId;
    }

    public String getName() {
        return name;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public String getBaggageCode() {
        return baggageCode;
    }

    @Override
    public String toString() {
        return passengerId + " | "
                + name + " | Flight: "
                + flightNumber + " | Baggage: "
                + baggageCode;
    }
}