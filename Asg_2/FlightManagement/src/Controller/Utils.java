/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import static Controller.SeatList.countAvaiSeat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * FlightManager
 *
 * @author Ta Minh Duc
 */
public class Utils {

    //-- check case blank string
    public static String getString(String welcome) {
        boolean check = true;
        String result = "";
        do {
            Scanner sc = new Scanner(System.in);
            System.out.println(welcome);

            result = sc.nextLine().trim();
            if (result.isEmpty()) {
                System.out.println("Please enter text!!!");
            } else {
                check = false;
            }
        } while (check);
        return result;
    }

    //-- check case wrong format Fxyzt (xyzt is number)
    public static String getStringID(String welcome) {
        boolean check = true;
        String result = "";
        do {
            Scanner sc = new Scanner(System.in);
            System.out.println(welcome);
            result = sc.nextLine().trim();
            if (result.isEmpty()) {
                System.out.println("Please enter text!");
            } else if (!Pattern.matches("^F+\\d{4}$", result)) {
                System.out.println("Wrong format! Please enter again.");
            } else {
                check = false;
            }
        } while (check);
        return result;
    }

    public static String getStatus(String welcome) {
        String result = "";
        int count = countAvaiSeat();
        System.out.println(welcome);

        if (count > 0) {
            result = "Available Seat";
        } else {
            result = "Not Available Seat";
        }
        return result;
    }

    public static String getStringDate(String welcome) {
        boolean check = true;
        String result = "";

        do {
            Scanner sc = new Scanner(System.in);
            System.out.println(welcome);
            result = sc.nextLine().trim();
            if (result.isEmpty()) {
                System.out.println("Please enter time!");
            } else if (!Pattern.matches("^\\d{2}+[-|\\/]+\\d{2}+[-|\\/]+\\d{4}+\\ +\\d{2}+\\:+\\d{2}$", result)) {
                System.out.println("Wrong format! Please enter again.");
            } else {
                check = false;
            }

        } while (check);
        return result;
    }

    //-- check email follow format xxx@gmail.com 
    public static String getStringEmail(String welcome) {
        boolean check = true;
        String result = "";
        try {
            do {
                Scanner sc = new Scanner(System.in);
                System.out.println(welcome);
                result = sc.nextLine().trim();
                if (result.isEmpty()) {
                    System.out.println("Please enter email!");
                } else if (!Pattern.matches("^[\\w^\\W]+@+(([\\w]{2,}+.[\\w]{2,}+.[\\w]{2,})|([\\w]{2,}+.[\\w]{2,}))$", result)) {
                    System.out.println("Wrong format! Please enter again.");
                } else {
                    check = false;
                }
            } while (check);
            return result;
        } catch (Exception e) {
            return result;
        }
    }

    public static boolean confirmYesNo(String welcome) {
        boolean result = false;
        String confirm = Utils.getString(welcome);
        if ("Y".equalsIgnoreCase(confirm)) {
            result = true;
        }
        return result;
    }

    public static boolean confirmYesNoUpdate(String welcome) {
        boolean result = false;
        String confirm = Utils.getString(welcome);
        if ("Y".equalsIgnoreCase(confirm)) {
            result = true;
        }
        return result;
    }

    //-- check format time of flight day, month, year, hour, minute
    public static String formatDate() {
        Locale locale = new Locale("en", "VN");
        Calendar calendar = Calendar.getInstance(locale);
        Scanner sc = new Scanner(System.in);

        //-- khoang thoi gian hien tai den nam 2024, 2024 la nam nhuan
        //-- LIMIT YEAR TO SET FLIGHT
        System.out.println("Enter year: [yyyy]");
        int year = sc.nextInt();
        //-- current year
        int yearCurrent = calendar.get(Calendar.YEAR);
//        System.out.println("current year: " + yearCurrent);
        //-- next year
        int yearNext = calendar.get(Calendar.YEAR) + 1;
//        System.out.println("next year: " + yearNext);

        //-- limit year to set flight
        while (year < yearCurrent || year > yearNext) {
            System.out.println("The year cannot be past or more than 1 year from now! Try again.");
            year = sc.nextInt();
        }

        //-- LIMIT MONTH TO SET FLIGHT
        System.out.println("Enter month: [MM]");
        int month = sc.nextInt();
        //-- current month
        int monthCurrent = calendar.get(Calendar.MONTH) + 1;
//        System.out.println("current month: " + monthCurrent);

        //-- limit next month
        int monthNext = calendar.get(Calendar.MONTH);
//        System.out.println("next month: " + monthNext);

        if (year == yearCurrent) {
            while (month < monthCurrent || month > 12) {
                System.out.println("The month cannot be past or more than December! Try again.");
                month = sc.nextInt();
            }
        } else if (year == yearNext) {
            while (month < 1 || month > monthNext) {
                System.out.println("The month cannot be less than January "
                        + "or more than 12 months away from the current month! Try again.");
                month = sc.nextInt();
            }
        }

        //-- LIMIT DAY TO SET FLIGHT
        System.out.println("Enter day: [dd]");
        int day = sc.nextInt();
        //-- current day
        int dayCurrent = calendar.get(Calendar.DATE);
//        System.out.println("current day: " + dayCurrent);

        if (month == 2 && year == 2024) {       //-- thang 2 cua nam nhuan
            while (day <= 0 || day >= 30) {
                System.out.println("Day can not less than 1 or over 29! Try again.");
                day = sc.nextInt();
            }
        } else if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
            if (month == monthCurrent) {  //-- day in year 2023
                while (day < dayCurrent || day >= 32) {
                    System.out.println("Day can not in past or over 31! Try again.");
                    day = sc.nextInt();
                }
            } else {            //-- day in year 2024
                while (day <= 0 || day >= 32) {
                    System.out.println("Day can not less than 1 or over 31! Try again.");
                    day = sc.nextInt();
                }
            }

        } else if (month == 4 || month == 6 || month == 9 || month == 11) {
            if (month == monthCurrent) {         //-- day in year 2023
                while (day < dayCurrent || day >= 31) {
                    System.out.println("Day can not in past or over 30! Try again.");
                    day = sc.nextInt();
                }
            } else {        //-- day in year 2024
                while (day <= 0 || day >= 32) {
                    System.out.println("Day can not less than 1 or over 31! Try again.");
                    day = sc.nextInt();
                }
            }

        } else {
            System.out.println("Invalid month.");
        }

        //-- LIMIT HOUR TO SET FLIGHT
        System.out.println("Enter hour: [hh]");
        int hour = sc.nextInt();
        //-- current hour
        int hourCurrent = calendar.get(Calendar.HOUR);
//        System.out.println("current hour: " + hourCurrent);

        if (day == dayCurrent) {
            while (hour < hourCurrent || hour >= 24) {
                System.out.println("Hour can not in past or over 24 hour! Try again.");
                hour = sc.nextInt();
            }
        } else {
            while (hour >= 24) {
                System.out.println("Hour can not over 24 hour! Try again.");
                hour = sc.nextInt();
            }
        }

        //-- LIMIT MINUTE TO SET FLIGHT
        System.out.println("Enter minute: [mm]");
        int minute = sc.nextInt();
        //-- current minute
        int minuteCurrent = calendar.get(Calendar.MINUTE);
//        System.out.println("current minute: " + minuteCurrent);

        if (day == dayCurrent) {
            if (hour == hourCurrent) {
                while (minute < minuteCurrent || minute >= 60) {
                    System.out.println("Minute can not in past or over 60 minute! Try again.");
                    minute = sc.nextInt();
                }
            } else {
                while (minute >= 60) {
                    System.out.println("Minute can not over 60 minute! Try again.");
                    minute = sc.nextInt();
                }
            }

        } else {
            while (minute >= 60) {
                System.out.println("Minute can not over 60 minute! Try again.");
                minute = sc.nextInt();
            }
        }
        //-- FORTMAT -> [dd-MM-yyyy hh:mm]
//        SimpleDateFormat formatTime = new SimpleDateFormat("dd-MM-yyyy hh:mm", locale);
        String formatStrDate = day + "-" + month + "-" + year + " " + hour + ":" + minute;
//        Date date = formatTime.parse(formatStrDate);
//        calendar.setTime(formatTime.parse(formatStrDate));
        return formatStrDate;
    }

    public static String formatDateToolString(Date date, String... Date_Format) {
        String format = Date_Format.length >= 1 ? Date_Format[0] : "dd-MM-yyyy hh:mm";
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        String temp = null;
        try {
            temp = date != null ? formatter.format(date) : null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return temp;
    }

    public static Date formatDateToolDate(String date, String... Date_Format) {
        String format = Date_Format.length >= 1 ? Date_Format[0] : "dd-MM-yyyy hh:mm";
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        Date temp = null;
        try {
            temp = !date.equals("null") ? formatter.parse(date) : null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return temp;
    }

}
