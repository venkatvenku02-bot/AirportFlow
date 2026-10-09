package model;


public class Flight {

    private String flightNumber;
    private String source;
    private String destination;
    private String gate;
    private String status;

    public Flight(String flightNumber, String source,
                  String destination, String gate,
                  String status) {

        this.flightNumber = flightNumber;
        this.source = source;
        this.destination = destination;
        this.gate = gate;
        this.status = status;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public String getSource() {
        return source;
    }

    public String getDestination() {
        return destination;
    }

    public String getGate() {
        return gate;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return flightNumber + " | "
                + source + " → "
                + destination + " | Gate: "
                + gate + " | " + status;
    }
}