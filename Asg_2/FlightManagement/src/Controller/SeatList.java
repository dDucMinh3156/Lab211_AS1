/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author Ta Minh Duc
 */
public class SeatList {

    private static HashMap<String, String> listSeat;

    public SeatList() {
        listSeat = new HashMap<String, String>();
    }

    public static void createNewFileSeat(String newFilename) {
        String strName = "";
        try {
//            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            strName = newFilename;
//            strPath = "H:\\LAB211\\Asg_2\\FlightManagement";
            File file = new File(strName + ".dat");
            file.createNewFile();
            BufferedWriter bw= new BufferedWriter(new FileWriter(file));
            bw.write("A1 - Available\n");     //-- seat A
            bw.write("A2 - Available\n");
            bw.write("A3 - Available\n");
            bw.write("A4 - Available\n");
            bw.write("A5 - Available\n");
            bw.write("B1 - Available\n");     //-- seat B
            bw.write("B2 - Available\n");
            bw.write("B3 - Available\n");
            bw.write("B4 - Available\n");
            bw.write("B5 - Available\n");
            bw.write("C1 - Available\n");     //-- seat C
            bw.write("C2 - Available\n");
            bw.write("C3 - Available\n");
            bw.write("C4 - Available\n");
            bw.write("C5 - Available\n");
            bw.write("D1 - Available\n");     //-- seat D
            bw.write("D2 - Available\n");
            bw.write("D3 - Available\n");
            bw.write("D4 - Available\n");
            bw.write("D5 - Available");
            
            bw.close();
            System.out.println("New file is created.");
        } catch (Exception e) {
            System.out.println("Can not create new file!");
        }

    }

    public boolean loadFromFileSeat(String filename) {
        File file = new File(filename);
        try {
            if (!file.exists()) {
                System.out.println("File is not existed!");
                return false;
            } else {
                BufferedReader bfr = new BufferedReader(new FileReader(file));
                String line = null;
                while ((line = bfr.readLine()) != null) {
                    String[] parts = line.split(" - ");
                    String seatID = parts[0].trim();
                    String seatStatus = parts[1].trim();

                    if (!seatID.equals("") && !seatStatus.equals("")) {
                        listSeat.put(seatID, seatStatus);
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

    public boolean saveToFileSeat(String filename) {
        File file = new File(filename);
        try {
            BufferedWriter bfw = new BufferedWriter(new FileWriter(file));

            for (Map.Entry<String, String> listS : listSeat.entrySet()) {
                bfw.write(listS.getKey() + " - " + listS.getValue());
                bfw.newLine();
            }
            bfw.flush();
            bfw.close();
            System.out.println("Data seats are update successfully.");
            return true;
        } catch (IOException e) {
            System.out.println("Data seats have not been updated!!!");
            e.printStackTrace();
            return false;
        }
    }

    public void choiceAvaiSeat() {
        Scanner sc = new Scanner(System.in);
        //-- show available seat
        
        System.out.println("-------List Available Seat-------");
        for (String listS : listSeat.keySet()) {
            if (listSeat.get(listS).equals("Available")) {  //-- display seat have status available
                System.out.print(listS + " ");
            }
        }
        System.out.println("---------------***---------------");
        //-- let passenger choice seat available
        boolean choiceAgain;
        String choiceSeat;
        do {
            System.out.println("Choice number seat you want: ");
            choiceSeat = sc.nextLine().trim().toUpperCase();
            //-- check seat valid
            while (!listSeat.containsKey(choiceSeat)) {
                System.out.println("Position seat not existed! Try again.");
                choiceSeat = sc.nextLine().trim().toUpperCase();
            }

            choiceAgain = Utils.confirmYesNo("Do you want to choice again? [Y:yes/N:no]");
        } while (choiceAgain);

        listSeat.replace(choiceSeat, "Not Available");

    }

    public static int countAvaiSeat() {
        int count = 0;
        for (String listS : listSeat.keySet()) {
            if (listSeat.get(listS).equals("Available")) {
                count++;
            }
        }
        return -1;
    }
}

//    public static String getStatus(String welcome) {
//        String result = "";
//        int count = 0;
//        System.out.println(welcome);
//        for (String listS : listSeat.keySet()) {
//            if (listSeat.get(listS).equals("Available")) {
//                count++;
//            }
//        }
//        if (count >= 1) {
//            result = "Available Seat";
//        } else {
//            result = "Not Available Seat";
//        }
//        return result;
//    }
