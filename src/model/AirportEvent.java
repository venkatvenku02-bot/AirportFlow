package model;

public class AirportEvent {

    private int eventId;
    private String message;

    public AirportEvent(int eventId, String message) {
        this.eventId = eventId;
        this.message = message;
    }

    public int getEventId() {
        return eventId;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return eventId + " | " + message;
    }
}