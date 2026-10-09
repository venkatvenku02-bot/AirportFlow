package module3;

import model.Flight;
import model.Gate;

public class GateAssignment {

    public static void assignGates(Flight[] flights, Gate[] gates) {

        // Find available gates
        int gateCount = 0;

        for (Gate gate : gates) {
            if (gate.isAvailable()) {
                gateCount++;
            }
        }

        if (gateCount == 0) {
            System.out.println("\nNo gates are currently available.");
            return;
        }

        // Store available gates
        Gate[] availableGates = new Gate[gateCount];

        int g = 0;

        for (Gate gate : gates) {
            if (gate.isAvailable()) {
                availableGates[g] = gate;
                g++;
            }
        }

        // Select flights that are not delayed
        int flightCount = 0;

        for (Flight flight : flights) {
            if (!flight.getStatus().equalsIgnoreCase("Delayed")) {
                flightCount++;
            }
        }

        // We cannot assign more flights than available gates
        int assignCount = Math.min(flightCount, gateCount);

        if (assignCount == 0) {
            System.out.println("\nNo suitable flights for gate assignment.");
            return;
        }

        // Store selected flights
        Flight[] selectedFlights = new Flight[assignCount];

        int f = 0;

        for (Flight flight : flights) {

            if (!flight.getStatus().equalsIgnoreCase("Delayed")
                    && f < assignCount) {

                selectedFlights[f] = flight;
                f++;
            }
        }

        // Create cost matrix
        int[][] cost = new int[assignCount][gateCount];

        for (int i = 0; i < assignCount; i++) {

            for (int j = 0; j < gateCount; j++) {

                cost[i][j] =
                        calculateCost(
                                selectedFlights[i],
                                availableGates[j]);
            }
        }

        // Run Bitmask DP
        int[] result = solveDP(cost);

        int minimumCost = result[0];
        int[] assignment = new int[assignCount];

        for (int i = 0; i < assignCount; i++) {
            assignment[i] = result[i + 1];
        }

        // Display result
        System.out.println("\n===== BITMASK DP GATE ASSIGNMENT =====");

        System.out.println(
                "Minimum Assignment Cost: " + minimumCost);

        System.out.println("\nAssignments:");

        for (int i = 0; i < assignCount; i++) {

            int gateIndex = assignment[i];

            System.out.println(
                    selectedFlights[i].getFlightNumber()
                    + " → "
                    + availableGates[gateIndex].getGateId()
                    + " | "
                    + availableGates[gateIndex].getTerminal()
                    + " | Cost: "
                    + cost[i][gateIndex]);
        }
    }


    // Calculate assignment cost
    private static int calculateCost(
            Flight flight,
            Gate gate) {

        int cost = 10;

        // Boarding flights get higher priority
        if (flight.getStatus().equalsIgnoreCase("Boarding")) {
            cost -= 4;
        }

        // Scheduled flights
        if (flight.getStatus().equalsIgnoreCase("Scheduled")) {
            cost -= 2;
        }

        // Hyderabad flights prefer Terminal 2
        if (flight.getSource().equalsIgnoreCase("Hyderabad")
                && gate.getTerminal().equalsIgnoreCase("Terminal 2")) {

            cost -= 3;
        }

        return Math.max(cost, 1);
    }


    // ==========================================
    // BITMASK DYNAMIC PROGRAMMING
    // ==========================================

    private static int[] solveDP(int[][] cost) {

        int flights = cost.length;
        int gates = cost[0].length;

        int totalMasks = 1 << gates;

        int[] dp = new int[totalMasks];

        int[] parentMask = new int[totalMasks];

        int[] parentGate = new int[totalMasks];

        for (int i = 0; i < totalMasks; i++) {

            dp[i] = Integer.MAX_VALUE;
            parentMask[i] = -1;
            parentGate[i] = -1;
        }

        dp[0] = 0;

        for (int mask = 0; mask < totalMasks; mask++) {

            if (dp[mask] == Integer.MAX_VALUE) {
                continue;
            }

            int flightIndex = Integer.bitCount(mask);

            if (flightIndex >= flights) {
                continue;
            }

            for (int gate = 0; gate < gates; gate++) {

                // Check whether gate is already used
                if ((mask & (1 << gate)) == 0) {

                    int newMask =
                            mask | (1 << gate);

                    int newCost =
                            dp[mask]
                            + cost[flightIndex][gate];

                    if (newCost < dp[newMask]) {

                        dp[newMask] = newCost;

                        parentMask[newMask] = mask;

                        parentGate[newMask] = gate;
                    }
                }
            }
        }

        // Find the best final mask
        int bestMask = -1;
        int bestCost = Integer.MAX_VALUE;

        for (int mask = 0; mask < totalMasks; mask++) {

            if (Integer.bitCount(mask) == flights
                    && dp[mask] < bestCost) {

                bestCost = dp[mask];
                bestMask = mask;
            }
        }

        int[] result = new int[flights + 1];

        result[0] = bestCost;

        // Reconstruct assignments
        int currentMask = bestMask;

        for (int i = flights - 1; i >= 0; i--) {

            result[i + 1] =
                    parentGate[currentMask];

            currentMask =
                    parentMask[currentMask];
        }

        return result;
    }
}

