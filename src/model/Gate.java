package model;

public class Gate {

    private String gateId;
    private String terminal;
    private boolean available;

    public Gate(String gateId, String terminal, boolean available) {
        this.gateId = gateId;
        this.terminal = terminal;
        this.available = available;
    }

    public String getGateId() {
        return gateId;
    }

    public String getTerminal() {
        return terminal;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return gateId + " | Terminal: "
                + terminal + " | Available: "
                + available;
    }
}