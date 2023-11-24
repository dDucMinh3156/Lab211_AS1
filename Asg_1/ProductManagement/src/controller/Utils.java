/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.Scanner;
import java.util.regex.Pattern;

/**
 *
 * @author Ta Minh Duc
 */
public class Utils {

    //-- case do not enter text
    public static String getString(String welcome) {
        boolean check = true;
        String result = "";
        do {
            Scanner sc = new Scanner(System.in);
            System.out.println(welcome);
            result = sc.nextLine().trim();
            if (result.isEmpty()) {
                System.out.println("Please input text!!!");
            } else {
                check = false;
            }
        } while (check);
        return result;
    }

    //-- In this case, return when entering wrong format of quantity
    public static int getInt(String welcome, int min, int max) {
        boolean check = true;
        int number = 0;
        do {
            try {
                Scanner sc = new Scanner(System.in);
                System.out.println(welcome);
                number = sc.nextInt();
                if (number < min || number > max) {
                    System.out.println("Need from 0 to 1,000.");
                } else {
                    check = false;
                }
            } catch (Exception e) {
                System.out.println("Please input integer number!");
                e.printStackTrace();
            }
        } while (check || number > max || number < min);
        return number;
    }

    //-- In this case, return when entering wrong format of unit price
    public static float getFloat(String welcome, float min, float max) {
        boolean check = true;
        float number = 0;
        do {
            try {
                Scanner sc = new Scanner(System.in);
                System.out.println(welcome);
                number = sc.nextFloat();
                if (number < min || number > max) {
                    System.out.println("Need from 0 to 10,000.");
                } else {
                    check = false;
                }
            } catch (Exception e) {
                System.out.println("Please input real number!");
                e.printStackTrace();
            }
        } while (check || number > max || number < min);
        return number;
    }

    //-- Ask to continue or return to the main menu
    public static boolean confirmYesNo(String welcome) {
        boolean result = false;
        String confirm = Utils.getString(welcome);
        if ("Y".equalsIgnoreCase(confirm)) {
            result = true;
        }
        return result;
    }
    //-- ask update 

    public static boolean confirmYesNoUpdate(String welcome) {
        boolean result = false;
        String confirm = Utils.getString(welcome);
        if ("Y".equalsIgnoreCase(confirm)) {
            result = true;
        }
        return result;
    }

    //-- In this case, skip updating unit price when pressing 'enter' or leave it blank
    public static float getFloatUpdate(String welcome, float min, float max) {
        boolean check = true;
        float number = 0;
        String skipUpdate;
        do {
            try {
                Scanner sc = new Scanner(System.in);
                System.out.println(welcome);
                skipUpdate = sc.nextLine().trim();
                if (!Pattern.matches("^.{0,0}$", skipUpdate)) {
                    number = Float.parseFloat(skipUpdate);
                    if (number < min || number > max) {
                        System.out.println("Need from 0 to 10,000.");
                    } else {
                        check = false;
                    }
                } else {
                    check = false;
                }

            } catch (Exception e) {
                System.out.println("Please input real number!");
                e.printStackTrace();
            }
        } while (check || number > max || number < min);
        return number;
    }

    public static int getIntUpdate(String welcome, int min, int max) {
        boolean check = true;
        int number = 0;
        String skipUpdate;
        do {
            try {
                Scanner sc = new Scanner(System.in);
                System.out.println(welcome);
                skipUpdate = sc.nextLine().trim();
                if (!Pattern.matches("^.{0,0}$", skipUpdate)) {
                    number = Integer.parseInt(skipUpdate);
                    if (number < min || number > max) {
                        System.out.println("Need from 0 to 1,000.");
                    } else {
                        check = false;
                    }
                } else {
                    check = false;
                }

            } catch (Exception e) {
                System.out.println("Please input integer number!");
                e.printStackTrace();
            }
        } while (check || number > max || number < min);
        return number;
    }

}
