/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.MenuFlight;
import Controller.FlightList;
import Controller.PassengerList;
import Controller.SeatList;
import Controller.Utils;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Ta Minh Duc
 */
public class ViewManagementSystem {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter file want to load: ");

        //--load file flight
        System.out.println("Enter file flight: ");
        FlightList flightList = new FlightList();
//        String fileFlight = sc.nextLine();

//        if (!flightList.loadFromFileFlight(fileFlight)) {
//            System.out.println("*** Can not load file flight! ***");
//            return;
//        } else {
//            System.out.println("*** File flight is loaded successfully ***");
//        }

        //-- load file passenger
        System.out.println("File passenger: ");
        PassengerList passengerList = new PassengerList();

        //-- load file seat
        System.out.println("File seat: ");
        SeatList seatList = new SeatList();

        //-- ArrayList options
        ArrayList<String> options = new ArrayList<>();
        options.add("Flight schedule management");
        options.add("Passenger reservation and booking");
        options.add("Passenger check-in and seat allocation");
        options.add("Crew management and assignments");
        options.add("Administrator access for system management");
        options.add("Data storage for flight details, reservations, and assignments");
        options.add("");

        //-- create Menu Flight
        MenuFlight menu = new MenuFlight();
        int choice = 0;
        do {
            System.out.println("---------------MENU MANAGEMENT---------------");
            choice = menu.int_getChoice(options);
            switch (choice) {
                case 1:
                    flightList.addFlight();
                    break;
                case 2:
                    //-- miniMenu: search flight, booking
                    System.out.println("1. Search Flight.\n"
                            + "2. Booking flight.\n"
                            + "Ohter. Exit.");
                    int mChoice = sc.nextInt();

                    if (mChoice == 1) {
                        System.out.println("Search for available flights based on departure and arrival locations.");
                        flightList.searchFlight();
                    } else if (mChoice == 2) {
                        passengerList.addPassengerBooking();
                    }
                    break;
                case 3:
                    passengerList.showInfoPassenBooking();
                    seatList.choiceAvaiSeat();
                    break;
                case 4:
                    break;
                case 5:
                    boolean ask;
                    do {
                        do {
                            System.out.println("Choice file want to save:\n"
                                    + "1. List all flight.\n"
                                    + "2. List all passenger.\n"
                                    + "3. List all crew.\n"
                                    + "Other. Exit.");
                            mChoice = sc.nextInt();
                            switch (mChoice) {
                                case 1:
                                    System.out.println("File flight is saving...");
                                    String saveFileF = "flight.dat";
                                    flightList.saveToFileFlight(saveFileF);
                                    break;
                                case 2:
                                    System.out.println("File passenger is saving...");
                                    String saveFileP = "passenger.dat";
                                    passengerList.saveToFileFlight(saveFileP);
                                    break;
                                case 3:
                                    break;
                                default:
                                    break;
                            }
                        } while (mChoice > 0 && mChoice < 4);
                        ask = Utils.confirmYesNo("Do you want to continue save file? [Y:yes/N:no]");
                    } while (ask);
                    break;
                case 6:
                    System.out.println("Choice list:\n"
                            + "1. List all flight.\n"
                            + "2. List all booking.\n"
                            + "3. List all assignment.\n"
                            + "Other. Exit.");
                    mChoice = sc.nextInt();
                    if (mChoice == 1) {
                        flightList.showAllFlight();
                    } else if (mChoice == 2) {
                        passengerList.showAllPassenger();
                    } else if (mChoice == 3) {

                    } else {
                        System.out.println("Exit.");
                    }
                    break;
                default:
                    System.out.println("--------EXIT PROGRAM--------");
                    break;
            }

        } while (choice > 0 && choice < options.size());

    }
}
