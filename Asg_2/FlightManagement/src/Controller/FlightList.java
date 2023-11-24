/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;
import model.InfoFlight;

/**
 *
 * @author Ta Minh Duc
 */
public class FlightList {

    private HashMap<String, InfoFlight> listFlight;

    public FlightList() {
        listFlight = new HashMap<String, InfoFlight>();
    }

    //-- load file of information Flights
//    public void loadFromFileFlight(String filename) {
//        HashMap<String, InfoFlight> listFlight = new HashMap<String, InfoFlight>();
//        try {
//            File file = new File(filename);
//            if (!file.exists()) {
//                System.out.println("File is not existed!");
//            } else {
//                BufferedReader bfr = new BufferedReader(new FileReader(file));
//                String line = null;
//                while ((line = bfr.readLine()) != null) {
//                    String[] parts1 = line.split(" _ ");
//                    String keyFilght = parts1[0].trim();
//                    String flightNumber = parts1[1].trim();
//
//                    String[] parts2 = line.split(", ");
//                    String departureCity = parts2[0].trim();
//                    String destinationCity = parts2[1].trim();
//                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm");
//                    Date departureTime = sdf.parse(parts2[2]);
//                    Date arrivalTime = sdf.parse(parts2[3]);
//                    String availableSeats = parts2[4].trim();
//                    InfoFlight flight = new InfoFlight(flightNumber, departureCity, destinationCity, departureTime, arrivalTime, availableSeats);
//
//                    if (!keyFilght.equals("") && !flight.equals("")) {
//                        listFlight.put(keyFilght, flight);
//                    }
//                    System.out.println("*** Load file Flight successfully ***");
//                    bfr.close();
//                }
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return listFlight;
//    }

    public boolean saveToFileFlight(String filename) {
        File file = new File(filename);
        try {
            BufferedWriter bfw = new BufferedWriter(new FileWriter(file));

            for (Map.Entry<String, InfoFlight> listF : listFlight.entrySet()) {
                bfw.write(listF.getKey() + " _ " + listF.getValue());
                bfw.newLine();
            }
            bfw.flush();
            bfw.close();
            System.out.println("Data flights are save successfully.");
            return true;
        } catch (IOException e) {
            System.out.println("Data flights have not been saved!!!");
            e.printStackTrace();
            return false;
        }
    }

    //-- OPTION 1: add new flight
    public void addFlight() {
        boolean ask;
        do {
            try {
                //-- enter flightNumber, departureCity, destinationCity, departureTime, arrivalTime, availableSeats
                //-- flightNumber
                String aFlightNumber = Utils.getStringID("Enter flight numbers: [Fxxxx] ").trim();

                //-- departureCity
                String aDepartureCity = Utils.getString("Enter departure city: ").trim();

                //-- destinationCity
                String aDestinationCity = Utils.getString("Enter destination city: ");

                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm");
                //-- departureTime
                System.out.println("Enter departure time: [dd-mm-yyyy hh:mm]");
                Date aDepartureTime = sdf.parse(Utils.formatDate());

                //-- arrivalTime
                System.out.println("Enter arrival time: [dd-mm-yyyy hh:mm]");
                Date aArrivalTime = sdf.parse(Utils.formatDate());

                //-- availableSeats
                String aAvailableSeats = "Available";

                //-- put new data to Hashmap
                InfoFlight aFlight = new InfoFlight(aFlightNumber, aDepartureCity, aDestinationCity, aDepartureTime, aArrivalTime, aAvailableSeats);

                //-- key --> aFlightNumber
                listFlight.put(aFlightNumber, aFlight);
                System.out.println("Data new flight is added successfully.");
                SeatList.createNewFileSeat(aFlightNumber);

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Data new flight has not been added!");
            }

            ask = Utils.confirmYesNo("Do you want to continue add new? [Y:Yes/ N:No]");
        } while (ask);
    }

    //-- search flight with departure, arrival, date
    public void searchFlight() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter departure: ");
        String enterDeparture = sc.nextLine().trim().toLowerCase();
        System.out.println("Enter arrival");
        String enterArrival = sc.nextLine().trim().toLowerCase();
//        String enterDate = sc.nextLine();
        try {
            for (String listF : listFlight.keySet()) {
//                if (listFlight.containsValue(enterDeparture) && listFlight.containsValue(enterArrival)) {
//                    System.out.println(listF);
//                }
                if (listFlight.get(listF.toLowerCase()).equals(enterDeparture) && listFlight.get(listF.toLowerCase()).equals(enterArrival)) {
                    System.out.println(listF);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //-- OPTION 6
    public void showAllFlight() {

        System.out.println("|-------------------- LIST FLIGHTS --------------------|");
        for (String listF : listFlight.keySet()) {
//            System.out.println(listFlight.get(listF));
            System.out.printf("| %-6s | %-15s | %-15s | %-18d | %-18d | %-20s |",
                    listFlight.get(listF).getFlightNumber(), listFlight.get(listF).getDepartureCity(),
                    listFlight.get(listF).getDestinationCity(), 
                    Utils.formatDateToolString(listFlight.get(listF).getDepartureTime(), "dd-MM-yyyy hh:mm"),
                    Utils.formatDateToolString(listFlight.get(listF).getArrivalTime(), "dd-MM-yyyy hh:mm"), 
                    listFlight.get(listF).getAvailableSeats());
        }
        System.out.println("|------------------------------------------------------|");
    }

}
