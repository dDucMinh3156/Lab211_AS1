package Controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import model.InfoFlight;
import model.InfoPassenger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Ta Minh Duc
 */
public class PassengerList {

    private HashMap<String, InfoPassenger> listPassenger;

    public PassengerList() {
        listPassenger = new HashMap<>();
    }

    //-- load file of information passengers booking/ reservation
    public boolean loadFromFilePassenger(String filename) {
        try {
            File file = new File(filename);
            if (!file.exists()) {
                System.out.println("File is not existed!");
                return false;
            } else {
                BufferedReader bfr = new BufferedReader(new FileReader(file));
                String line = null;
                while ((line = bfr.readLine()) != null) {
                    String[] parts1 = line.split(" _ ");
                    String keyPassenger = parts1[0].trim();
                    String namePassenger = parts1[1].trim();

                    String[] parts2 = line.split(", ");
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                    Date birthday = sdf.parse(parts2[0].trim());
                    String gender = parts2[1].trim();
                    String phoneNumber = parts2[2].trim();
                    String email = parts2[3].trim();
                    String uniqueID = parts2[4].trim();
                    InfoPassenger passenger = new InfoPassenger(namePassenger, birthday, gender, phoneNumber, email, uniqueID);

                    if (!keyPassenger.equals("") && !passenger.equals("")) {
                        listPassenger.put(keyPassenger, passenger);
                    }
                    bfr.close();
                    return true;
                }

            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean saveToFileFlight(String filename) {
        File file = new File(filename);
        try {
            BufferedWriter bfw = new BufferedWriter(new FileWriter(file));

            for (Map.Entry<String, InfoPassenger> listP : listPassenger.entrySet()) {
                bfw.write(listP.getKey() + " _ " + listP.getValue());
                bfw.newLine();
            }
            bfw.flush();
            bfw.close();
            System.out.println("Data passengers are save successfully.");
            return true;
        } catch (IOException e) {
            System.out.println("Data flights have not been saved!!!");
            e.printStackTrace();
            return false;
        }
    }

    public void addPassengerBooking() {
        Scanner sc = new Scanner(System.in);
        boolean ask;
        do {
            try {
                //-- enter name passenger, birthday, gender, phone number, email
                //-- name passenger
                String aNamePassenger = Utils.getString("Enter name of passenger: ");
                //-- birthday passenger
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                System.out.println("Enter birthday: [dd-mm-yyyy]");
                String strBirthday = sc.nextLine().trim();
                Date aBirthday = sdf.parse(strBirthday);
                //-- gender passenger
                String aGender = Utils.getString("Enter gender: ");

                //-- phone number passenger
                String aPhoneNumber = Utils.getString("Enter phone number: ");

                //-- email passenger
                String aEmail = Utils.getStringEmail("Enter your email: ");

                //-- generate auto unique ID passenger
                boolean check;
                String aUniqueID = null;
                do {
                    aUniqueID = getAlphaNumericString();
                    if (checkDuplicatedID(aUniqueID) > 0) { //-- ID is duplicated
                        check = true;
                    } else {
                        check = false;
                    }
                } while (check);

                //-- put new data to Hashmap
                InfoPassenger aPassenger = new InfoPassenger(aNamePassenger, aBirthday, aGender, aPhoneNumber, aEmail, aUniqueID);
                //-- key --> aPhoneNumber
                listPassenger.put(aUniqueID, aPassenger);
                System.out.println("Data new passenger is added successfully.");

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Data new passenger has not been added!");
            }

            ask = Utils.confirmYesNo("Do you want to continue add new? [Y:Yes/ N:No]");
        } while (ask);

    }

    //-- check duplicated uniqueID
    public int checkDuplicatedID(String uniqID) {
        //int N= listPassenger.size();
        for (String passengerID : listPassenger.keySet()) {
            if (listPassenger.containsKey(uniqID)) {
                return 1; //-- ID existed
            }
        }
        return -1; //-- ID not existed
    }

    //-- generate automatic unique ID string
    public String getAlphaNumericString() {
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";
        //-- create unique ID width 8 character
        StringBuilder sb = new StringBuilder(7);

        for (int i = 0; i < 8; i++) {
            int index = (int) (AlphaNumericString.length() * Math.random());
            sb.append(AlphaNumericString.charAt(index));
        }
        return sb.toString();
    }

    //-- boarding passes include info flight and info passenger
    public void createBoardingPasses() {

    }

    public void showInfoPassenBooking(){
        Scanner sc= new Scanner(System.in);
        System.out.println("Enter your ID booking: ");
        String uniqueID= sc.nextLine().trim();
        for (String infoBooking : listPassenger.keySet()) {
            if (listPassenger.containsKey(uniqueID)) {
                System.out.println(infoBooking);
            }
        }
        
    }
    
    public void showAllPassenger() {

        for (String listP : listPassenger.keySet()) {
            System.out.println(listP + " - " + listPassenger.get(listP));
        }
    }
}
