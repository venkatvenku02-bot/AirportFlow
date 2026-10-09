package app;

import java.util.Scanner;

import model.Flight;
import model.AirportEvent;

import module2.KMP;
import module2.AhoCorasick;

import module3.Levenshtein;
import module3.GateAssignment;

import module4.DinicMaxFlow;

public class AirportFlow {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("====================================");
        System.out.println("           AIRPORTFLOW");
        System.out.println("    Airport Operations System");
        System.out.println("====================================");

        while (true) {

        	System.out.println("\n===== AIRPORTFLOW MENU =====");

        	System.out.println("\n1. Search Flights");
        	System.out.println("2. Search Airport Alerts");
        	System.out.println("3. Passenger Information");
        	System.out.println("4. Gate Assignment");
        	System.out.println("5. Passenger Flow");
        	System.out.println("6. Airport Event Management");
        	System.out.println("7. Flight Status & Delays");
        	System.out.println("8. Algorithm Demonstrations");
        	System.out.println("9. Exit");

            System.out.print("\nEnter choice: ");

            int choice = sc.nextInt();

            switch (choice) {

            // ==========================================
            // 1. FLIGHT SEARCH - KMP + LEVENSHTEIN
            // ==========================================

            case 1:

                System.out.println("\n===== AVAILABLE FLIGHTS =====");

                for (Flight flight : AirportData.flights) {
                    System.out.println(flight);
                }

                System.out.print(
                        "\nEnter flight number to search: ");

                String searchFlight = sc.next();

                boolean found = false;

                System.out.println(
                        "\nSearching using KMP...\n");

                // Exact search using KMP

                for (Flight flight : AirportData.flights) {

                    if (KMP.search(
                            flight.getFlightNumber(),
                            searchFlight)) {

                        System.out.println(flight);

                        found = true;
                    }
                }

                // If exact flight is not found,
                // use Levenshtein for typo detection

                if (!found) {

                    System.out.println(
                            "Flight not found.");

                    System.out.println(
                            "\nChecking for possible typo using Levenshtein...");

                    int bestDistance = Integer.MAX_VALUE;

                    Flight suggestedFlight = null;

                    for (Flight flight : AirportData.flights) {

                        int distance =
                                Levenshtein.distance(
                                        searchFlight.toUpperCase(),
                                        flight.getFlightNumber()
                                                .toUpperCase());

                        if (distance < bestDistance) {

                            bestDistance = distance;
                            suggestedFlight = flight;
                        }
                    }

                    // Suggest only if the typo is small

                    if (bestDistance <= 2
                            && suggestedFlight != null) {

                        System.out.println(
                                "\nDid you mean:");

                        System.out.println(
                                suggestedFlight);

                        System.out.println(
                                "Edit Distance: "
                                + bestDistance);

                    } else {

                        System.out.println(
                                "No similar flight number found.");
                    }
                }

                break;


            // ==========================================
            // 2. AIRPORT ALERTS - AHO-CORASICK
            // ==========================================

            case 2:

                System.out.println(
                        "\n===== AIRPORT ALERTS =====");

                for (AirportEvent event :
                        AirportData.events) {

                    System.out.println(event);
                }

                System.out.print(
                        "\nEnter alert keyword to search: ");

                sc.nextLine();

                String alertSearch = sc.nextLine();

                AhoCorasick ac = new AhoCorasick();

                ac.addWord(alertSearch);

                ac.build();

                boolean alertFound = false;

                System.out.println(
                        "\nSearching using Aho-Corasick...\n");

                for (AirportEvent event :
                        AirportData.events) {

                    if (!ac.search(
                            event.getMessage()).isEmpty()) {

                        System.out.println(event);

                        alertFound = true;
                    }
                }

                if (!alertFound) {

                    System.out.println(
                            "No matching alert found.");
                }

                break;


            // ==========================================
            // 3. PASSENGER INFORMATION
            // ==========================================

            case 3:

                System.out.println(
                        "\n===== PASSENGER INFORMATION =====");

                for (model.Passenger passenger :
                        AirportData.passengers) {

                    System.out.println(passenger);
                }

                System.out.print(
                        "\nEnter Passenger ID or Flight Number: ");

                String passengerSearch = sc.next();

                boolean passengerFound = false;

                for (model.Passenger passenger :
                        AirportData.passengers) {

                    if (passenger.getPassengerId()
                            .equalsIgnoreCase(passengerSearch)

                            || passenger.getFlightNumber()
                            .equalsIgnoreCase(passengerSearch)) {

                        System.out.println(
                                "\nPassenger Found:");

                        System.out.println(passenger);

                        passengerFound = true;
                    }
                }

                if (!passengerFound) {

                    System.out.println(
                            "Passenger not found.");
                }

                break;


            // ==========================================
            // 4. GATE ASSIGNMENT - BITMASK DP
            // ==========================================

            case 4:

                System.out.println(
                        "\n===== GATE ASSIGNMENT =====");

                GateAssignment.assignGates(
                        AirportData.flights,
                        AirportData.gates);

                break;


            // ==========================================
            // 5. PASSENGER FLOW - DINIC MAX FLOW
            // ==========================================

            case 5:

                DinicMaxFlow.demonstratePassengerFlow();

                break;


            // ==========================================
            // 6. FLIGHT STATUS & DELAYS
            // ==========================================

             // ==========================================
             // 6. AIRPORT EVENT MANAGEMENT
             // ==========================================

             case 6:

                 while (true) {

                     System.out.println(
                             "\n===== AIRPORT EVENT MANAGEMENT =====");

                     System.out.println("1. View Events");
                     System.out.println("2. Search Events");
                     System.out.println("3. Add Event");
                     System.out.println("4. Back");

                     System.out.print("\nEnter choice: ");

                     int eventChoice = sc.nextInt();

                     switch (eventChoice) {

                     case 1:

                         System.out.println(
                                 "\n===== ALL AIRPORT EVENTS =====");

                         for (AirportEvent event :
                                 AirportData.events) {

                             System.out.println(event);
                         }

                         break;

                     case 2:

                         System.out.print(
                                 "\nEnter event keyword: ");

                         sc.nextLine();

                         String eventKeyword =
                                 sc.nextLine();

                         AhoCorasick eventSearch =
                                 new AhoCorasick();

                         eventSearch.addWord(eventKeyword);
                         eventSearch.build();

                         boolean eventFound = false;

                         System.out.println(
                                 "\nSearching events using Aho-Corasick...\n");

                         for (AirportEvent event :
                                 AirportData.events) {

                             if (!eventSearch.search(
                                     event.getMessage()).isEmpty()) {

                                 System.out.println(event);

                                 eventFound = true;
                             }
                         }

                         if (!eventFound) {

                             System.out.println(
                                     "No matching event found.");
                         }

                         break;

                     case 3:

                    	    System.out.print(
                    	            "\nEnter Event ID: ");

                    	    int eventId = sc.nextInt();

                    	    sc.nextLine();

                    	    System.out.print(
                    	            "Enter Event Message: ");

                    	    String eventMessage =
                    	            sc.nextLine();

                    	    AirportData.events.add(
                    	            new AirportEvent(
                    	                    eventId,
                    	                    eventMessage));

                    	    System.out.println(
                    	            "\nEvent added successfully!");

                    	    break;

                     case 4:

                         break;

                     default:

                         System.out.println(
                                 "Invalid choice.");
                     }

                     if (eventChoice == 4) {
                         break;
                     }
                 }

                 break;
                 
              // ==========================================
              // 7. FLIGHT STATUS & DELAYS
              // ==========================================

              case 7:

                  System.out.println(
                          "\n===== FLIGHT STATUS & DELAYS =====");

                  boolean delayedFound = false;

                  for (Flight flight :
                          AirportData.flights) {

                      if (flight.getStatus()
                              .equalsIgnoreCase("Delayed")) {

                          System.out.println(
                                  "DELAYED: " + flight);

                          delayedFound = true;
                      }
                  }

                  if (!delayedFound) {

                      System.out.println(
                              "No delayed flights.");
                  }

                  System.out.println(
                          "\n===== ALL FLIGHT STATUS =====");

                  for (Flight flight :
                          AirportData.flights) {

                      System.out.println(
                              flight.getFlightNumber()
                              + " → "
                              + flight.getStatus());
                  }

                  break;


            // ==========================================
            // 8. ALGORITHM DEMONSTRATIONS
            // ==========================================

            case 8:

                System.out.println(
                        "\n===== ALGORITHM DEMONSTRATIONS =====");

                System.out.println("1. KMP");
                System.out.println("2. Aho-Corasick");
                System.out.println("3. Levenshtein Distance");
                System.out.println(
                        "4. Bitmask DP - Gate Assignment");
                System.out.println(
                        "5. Dinic Max Flow");

                System.out.print(
                        "\nEnter choice: ");

                int algorithmChoice = sc.nextInt();

                switch (algorithmChoice) {

                case 1:

                    System.out.println(
                            "\nKMP Algorithm");

                    System.out.println(
                            "Used for searching a specific flight number.");

                    break;


                case 2:

                    System.out.println(
                            "\nAho-Corasick Algorithm");

                    System.out.println(
                            "Used for detecting airport alert keywords.");

                    break;


                case 3:

                    System.out.println(
                            "\n===== LEVENSHTEIN TEST =====");

                    String entered = "AI10I";

                    String actual = "AI101";

                    int distance =
                            Levenshtein.distance(
                                    entered,
                                    actual);

                    System.out.println(
                            "Entered: " + entered);

                    System.out.println(
                            "Actual:  " + actual);

                    System.out.println(
                            "Edit Distance: " + distance);

                    break;


                case 4:

                    System.out.println(
                            "\n===== BITMASK DP - GATE ASSIGNMENT =====");

                    GateAssignment.assignGates(
                            AirportData.flights,
                            AirportData.gates);

                    break;


                case 5:

                    DinicMaxFlow.demonstratePassengerFlow();

                    break;


                default:

                    System.out.println(
                            "Invalid algorithm choice.");
                }

                break;


            // ==========================================
            // 9. EXIT
            // ==========================================

            case 9:

                System.out.println(
                        "\nThank you for using AirportFlow!");

                sc.close();

                return;


            default:

                System.out.println(
                        "\nInvalid choice.");
            }
        }
    }
}