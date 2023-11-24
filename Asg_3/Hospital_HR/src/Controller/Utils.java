/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.util.Calendar;
import java.util.Locale;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Hospital HR
 *
 * @author Ta Minh Duc
 */
public class Utils {

    public static String getString(String welcome) {
        Scanner sc = new Scanner(System.in);
        boolean check = true;
        String result = "";
        do {
            System.out.print(welcome);
            result = sc.nextLine().trim();
            if (result.isEmpty()) {
                System.out.println("Please input text!");
            } else {
                check = false;
            }
        } while (check);
        return result;
    }

    //== GET STRING ID ===========================================================================================================
    public static String getStringID(String welcome) {
        Scanner sc = new Scanner(System.in);
        boolean check = true;
        String resultID = "";
        do {
            System.out.print(welcome);
            resultID = sc.nextLine().trim().toUpperCase();
            if (resultID.isEmpty()) {
                System.out.println("Please input text!");
            } else if (!Pattern.matches("^EM\\d{4}$", resultID)) {
                System.out.println("ID wrong format [EMxxxx]! Please enter again.");
            } else {
                check = false;
            }
        } while (check);
        return resultID;
    }

    //-- CONFIRM YES/ NO ASK =====================================================================================================
    public static boolean confirmYesNo(String welcome) {
        boolean result = false;
        String confirm = Utils.getString(welcome);
        if ("Y".equalsIgnoreCase(confirm)) {
            result = true;
        }
        return result;
    }

    //== FORMAT BIRTH/ WORKING AGE ======================================================================================================
    public static String formatWorkingAge(String welcome) {
        System.out.println(welcome);
        Locale locale = new Locale("en", "VN");
        Calendar calendar = Calendar.getInstance(locale);
        Scanner sc = new Scanner(System.in);
        /*
        Do tuoi lao dong cua Viet Nam tu 15 tuoi den 60 tuoi (doi voi nam),
        tu 15 tuoi den 56 tuoi (doi voi nu).
        -> tuoi lao dong hop phap tinh tu nam 2023 la sinh truoc nam 2008
        Tuổi nhân viên phải trong độ tuổi lao động (bDate), tuổi lúc nhận việc hợp đồng 
        phải nằm trong độ tuổi lao động, tuổi tại thời điểm nhận việc (hiredDate) 
        + thời gian làm việc theo hợp đồng (contractTime) phải nhỏ hơn 
        tuổi lao động tối đa (<58 tuổi), resign date phải trong khoản thời gian từ lúc ngày 
        nhận việc đến lúc hết thời hạn hợp đồng
         */
        //------- LIMIT YEAR BIRTH/ WORKING ---------------------
        System.out.print("Enter year birth [yyyy]: ");
        int year = sc.nextInt();
        //-- current year: 2023
        //-- limit year: 2008 (MIN)
        int yearLimitMin = calendar.get(Calendar.YEAR) - 15;
        //-- limit year: 1965 (MAX)
        int yearLimitMax = calendar.get(Calendar.YEAR) - 58;
        //-- limit year to working
        while (year > yearLimitMin || year < yearLimitMax) {
            if (year > yearLimitMin) {
                System.out.println("Under working age, must be at least "
                        + "15 years old! Please enter again.");
                year = sc.nextInt();
            } else {
                System.out.println("Above working age, must be at most "
                        + "58 years old! Please enter again.");
                year = sc.nextInt();
            }
        }
        //------- LIMIT MONTH BIRTH/ WORKING ---------------------
        System.out.print("Enter month birth [mm]: ");
        int month = sc.nextInt();
        //-- current month: ...
        int monthCurrent = calendar.get(Calendar.MONTH) + 1;
        //-- limit month to working
        //-- CUNG nam voi nam gioi han tuoi lao dong (MIN)----------------------
        if (year == yearLimitMin) {
            while (month > monthCurrent || month < 1) {
                if (month > monthCurrent) {
                    System.out.println("Under working age, must be at least "
                            + "15 years old! Please enter again.");
                    month = sc.nextInt();
                } else {
                    System.out.println("Invalid month! Please enter again.");
                    month = sc.nextInt();
                }
            }
            //-- CUNG nam voi nam gioi han tuoi lao dong (MAX)------------------
        } else if (year == yearLimitMax) {
            while (month < monthCurrent || month > 12) {
                if (month < monthCurrent) {
                    System.out.println("Above working age, must be at most "
                            + "58 years old! Please enter again.");
                    month = sc.nextInt();
                } else {
                    System.out.println("Invalid month! Please enter again.");
                    month = sc.nextInt();
                }
            }

            //-- KHAC nam vs nam gioi han tuoi lao dong (MAX, MIN)--------------
        } else if (year < yearLimitMin && year > yearLimitMax) {
            while (month < 1 || month > 12) {
                System.out.println("Invalid month! Please enter again.");
                month = sc.nextInt();
            }
        }

        //------- LIMIT DAY BIRTH/ WORKING ---------------------
        //-- current day: ...
        int dayCurrent = calendar.get(Calendar.DATE);
        //-- Check YEAR enter to see if it IS in a leap year or NOT
        boolean checkLeap = checkLeapYear(year);
        System.out.print("Enter day birth [dd]: ");
        int day = sc.nextInt();
        //----------------------------------------------------------------------
        //-- cùng năm, CÙNG tháng so vs thời gian hiện tại (MIN)------------
        if (year == yearLimitMin && month == monthCurrent) {
            while (day < 1 || day > dayCurrent) {
                if (day > dayCurrent) {
                    System.out.println("Under working age, must be at least "
                            + "15 years old! Please enter again.");
                    day = sc.nextInt();
                } else {
                    System.out.println("Invalid day! Please enter again.");
                    day = sc.nextInt();
                }
            }
            //-- cùng năm, KHÁC tháng so vs thời gian hiện tại (MIN)--------
        } else if (year == yearLimitMin && month < monthCurrent) {
            //+++++++++++++ la thang 2 +++++++++++++
            if (month == 2) {
                if (checkLeap == true) {    //-- It is leap year: February have 29 day
                    while (day < 1 || day > 29) {
                        System.out.println("Invalid day, Feb just have 29 day! Please try again.");
                        day = sc.nextInt();
                    }
                } else {                //-- It is NOT leap year: February have 28 day
                    while (day < 1 || day > 28) {
                        System.out.println("Invalid day, Feb just have 28 day! Please try again.");
                        day = sc.nextInt();
                    }
                }
                //+++++++++++++ thang co 31 ngay +++++++++++++
            } else if (month == 1 || month == 3 || month == 5 || month == 7
                    || month == 8 || month == 10 || month == 12) {
                while (day < 1 || day > 31) {
                    System.out.println("Invalid day! Please try again.");
                    day = sc.nextInt();
                }
                //+++++++++++++ thang co 30 ngay +++++++++++++
            } else if (month == 4 || month == 6 || month == 9 || month == 11) {
                while (day < 1 || day > 30) {
                    System.out.println("Invalid day! Please try again.");
                    day = sc.nextInt();
                }
            }

            //-- cùng năm, CÙNG tháng so vs thời gian hiện tại (MAX)------------
        } else if (year == yearLimitMax && month == monthCurrent) {
            //+++++++++++++ la thang 2 +++++++++++++
            if (month == 2) {
                if (checkLeap == true) {    //-- It is leap year: February have 29 day
                    while (day < dayCurrent || day > 29) {
                        if (day < dayCurrent) {
                            System.out.println("Above working age, must be at most "
                                    + "58 years old! Please enter again.");
                            day = sc.nextInt();
                        } else {
                            System.out.println("Invalid day, Feb just have 29 day! Please enter again.");
                            day = sc.nextInt();
                        }
                    }
                } else {                //-- It is NOT leap year: February have 28 day
                    while (day < dayCurrent || day > 28) {
                        if (day < dayCurrent) {
                            System.out.println("Above working age, must be at most "
                                    + "58 years old! Please enter again.");
                            day = sc.nextInt();
                        } else {
                            System.out.println("Invalid day, Feb just have 28 day! Please enter again.");
                            day = sc.nextInt();
                        }
                    }
                }
                //+++++++++++++ thang co 31 ngay +++++++++++++
            } else if (month == 1 || month == 3 || month == 5 || month == 7
                    || month == 8 || month == 10 || month == 12) {
                while (day < dayCurrent || day > 31) {
                    if (day < dayCurrent) {
                        System.out.println("Above working age, must be at most "
                                + "58 years old! Please enter again.");
                        day = sc.nextInt();
                    } else {
                        System.out.println("Invalid day! Please try again.");
                        day = sc.nextInt();
                    }
                }
                //+++++++++++++ thang co 30 ngay +++++++++++++
            } else if (month == 4 || month == 6 || month == 9 || month == 11) {
                while (day < dayCurrent || day > 30) {
                    if (day < dayCurrent) {
                        System.out.println("Above working age, must be at most "
                                + "58 years old! Please enter again.");
                        day = sc.nextInt();
                    } else {
                        System.out.println("Invalid day! Please try again.");
                        day = sc.nextInt();
                    }
                }
            }

            //-- cùng năm, KHÁC tháng so vs thời gian hiện tại (MAX)------------
        } else if (year == yearLimitMax && month > monthCurrent) {
            //+++++++++++++ la thang 2 +++++++++++++
            if (month == 2) {
                if (checkLeap == true) {    //-- It is leap year: February have 29 day
                    while (day < 1 || day > 29) {
                        System.out.println("Invalid day, Feb just have 29 day! Please try again.");
                        day = sc.nextInt();
                    }
                } else {                //-- It is NOT leap year: February have 28 day
                    while (day < 1 || day > 28) {
                        System.out.println("Invalid day, Feb just have 28 day! Please try again.");
                        day = sc.nextInt();
                    }
                }
                //+++++++++++++ thang co 31 ngay +++++++++++++
            } else if (month == 1 || month == 3 || month == 5 || month == 7
                    || month == 8 || month == 10 || month == 12) {
                while (day < 1 || day > 31) {
                    System.out.println("Invalid day! Please try again.");
                    day = sc.nextInt();
                }
                //+++++++++++++ thang co 30 ngay +++++++++++++
            } else if (month == 4 || month == 6 || month == 9 || month == 11) {
                while (day < 1 || day > 30) {
                    System.out.println("Invalid day! Please try again.");
                    day = sc.nextInt();
                }
            }

            //-- KHAC nam so vs thoi gian hien tai (MIN, MAX)-------------------
        } else if (year < yearLimitMin && year > yearLimitMax) {
            //+++++++++++++ la thang 2 +++++++++++++
            if (month == 2) {
                if (checkLeap == true) {    //-- It is leap year: February have 29 day
                    while (day < 1 || day > 29) {
                        System.out.println("Invalid day, Feb just have 29 day! Please try again.");
                        day = sc.nextInt();
                    }
                } else {                //-- It is NOT leap year: February have 28 day
                    while (day < 1 || day > 28) {
                        System.out.println("Invalid day, Feb just have 28 day! Please try again.");
                        day = sc.nextInt();
                    }
                }
                //+++++++++++++ thang co 31 ngay +++++++++++++
            } else if (month == 1 || month == 3 || month == 5 || month == 7
                    || month == 8 || month == 10 || month == 12) {
                while (day < 1 || day > 31) {
                    System.out.println("Invalid day! Please try again.");
                    day = sc.nextInt();
                }
                //+++++++++++++ thang co 30 ngay +++++++++++++
            } else if (month == 4 || month == 6 || month == 9 || month == 11) {
                while (day < 1 || day > 30) {
                    System.out.println("Invalid day! Please try again.");
                    day = sc.nextInt();
                }
            }
        }

        //------- FORMAT BIRTH DATE ---------------------
        String strDay = String.valueOf(day);
        String strMonth = String.valueOf(month);
        String strYear = String.valueOf(year);
        //-- convert value [dd-mm-yyyy] from INTEGER to STRING
        String formatBiDate = strDay + "-" + strMonth + "-" + strYear;
        return formatBiDate;
    }

    //== FORMAT HIRED DATE =======================================================================================================
    public static String formatHiredDate(String welcome, String calRetireDate, String birthDate) {
        System.out.println(welcome);
        Scanner sc = new Scanner(System.in);
        //-- COMPARE HIRED DATE WITH BIRTH DATE ---------------------
        String lineBirthDate = birthDate;
        String[] part1 = lineBirthDate.split("-");
        int birthDay = Integer.parseInt(part1[0].trim());
        int birthMonth = Integer.parseInt(part1[1].trim());
        int birthYear = Integer.parseInt(part1[2].trim()) + 15;
        //-- COMPARE HIRED DATE WITH RETIRE DATE ---------------------
        String lineRetireDate = calRetireDate;
        String[] part2 = lineRetireDate.split("-");
        int retireDay = Integer.parseInt(part2[0].trim());
        int retireMonth = Integer.parseInt(part2[1].trim());
        int retireYear = Integer.parseInt(part2[2].trim());
        //------- LIMIT YEAR HIRED ---------------------
        System.out.print("Enter year hired [yyyy]: ");
        int hiredYear = sc.nextInt();
        while (hiredYear > retireYear || hiredYear < birthYear) {
            if (hiredYear > retireYear) {
                System.out.println("Over the employee's working age! Please enter again.");
                hiredYear = sc.nextInt();
            } else {
                System.out.println("Below the employee's working age! Please enter again.");
                hiredYear = sc.nextInt();
            }
        }

        //------- LIMIT MONTH HIRED ---------------------
        System.out.print("Enter month hired [mm]: ");
        int hiredMonth = sc.nextInt();
        //-- CUNG nam so vs nam ve huu (MAX)------------------------------------
        if (hiredYear == retireYear) {
            while (hiredMonth > retireMonth || hiredMonth < 1) {
                if (hiredMonth > retireMonth) {
                    System.out.println("Over the employee's working age! Please enter again.");
                    hiredMonth = sc.nextInt();
                } else {
                    System.out.println("Invalid month! Please enter again.");
                    hiredMonth = sc.nextInt();
                }
            }
            //-- CUNG nam so vs nam bat dau lao dong (MIN)----------------------
        } else if (hiredYear == birthYear) {
            while (hiredMonth < birthMonth || hiredMonth > 12) {
                if (hiredMonth < birthMonth) {
                    System.out.println("Below the employee's working age! Please enter again.");
                    hiredMonth = sc.nextInt();
                } else {
                    System.out.println("Invalid month! Please enter again.");
                    hiredMonth = sc.nextInt();
                }
            }
            //-- KHAC nam so vs nam ve huu va bat dau lao dong (MIN, MAX)-------
        } else if (hiredYear < retireYear && hiredYear > birthYear) {
            while (hiredMonth < 1 || hiredMonth > 12) {
                System.out.println("Invalid month! Please enter again.");
                hiredMonth = sc.nextInt();
            }
        }
        //------- LIMIT DAY HIRED ---------------------
        System.out.print("Enter day hired [dd]: ");
        int hiredDay = sc.nextInt();
        boolean checkLeap = checkLeapYear(hiredYear);

        //-- cung nam, CUNG thang so vs thang ve huu (MAX)
        if (hiredYear == retireYear && hiredMonth == retireMonth) {
            while (hiredDay < 1 || hiredDay > retireDay) {
                if (hiredDay > retireDay) {
                    System.out.println("Over the employee's working age! Please enter again.");
                    hiredDay = sc.nextInt();
                } else {
                    System.out.println("Invalid day! Please enter again.");
                    hiredDay = sc.nextInt();
                }
            }
            //-- cung nam, KHAC thang so vs thang ve huu (MAX)------------------
        } else if (hiredYear == retireYear && hiredMonth < retireMonth) {
            //+++++++++++++ la thang 2 +++++++++++++
            if (hiredMonth == 2) {
                if (checkLeap == true) {    //-- It is leap year: February have 29 day
                    while (hiredDay < 1 || hiredDay > 29) {
                        System.out.println("Invalid day, Feb just have 29 day! Please enter again.");
                        hiredDay = sc.nextInt();
                    }
                } else {                //-- It is NOT leap year: February have 28 day
                    while (hiredDay < 1 || hiredDay > 28) {
                        System.out.println("Invalid day, Feb just have 28 day! Please enter again.");
                        hiredDay = sc.nextInt();
                    }
                }
                //+++++++++++++ thang co 31 ngay +++++++++++++
            } else if (hiredMonth == 1 || hiredMonth == 3 || hiredMonth == 5 || hiredMonth == 7
                    || hiredMonth == 8 || hiredMonth == 10 || hiredMonth == 12) {
                while (hiredDay < 1 || hiredDay > 31) {
                    System.out.println("Invalid day! Please enter again.");
                    hiredDay = sc.nextInt();
                }
                //+++++++++++++ thang co 30 ngay +++++++++++++
            } else if (hiredMonth == 4 || hiredMonth == 6 || hiredMonth == 9 || hiredMonth == 11) {
                while (hiredDay < 1 || hiredDay > 30) {
                    System.out.println("Invalid day! Please enter again.");
                    hiredDay = sc.nextInt();
                }
            }
            //-- cung nam, CUNG thang so vs thang bat dau lao dong (MIN)--------
        } else if (hiredYear == birthYear && hiredMonth == birthMonth) {
            //+++++++++++++ la thang 2 +++++++++++++
            if (hiredMonth == 2) {
                if (checkLeap == true) {    //-- It is leap year: February have 29 day
                    while (hiredDay < birthDay || hiredDay > 29) {
                        if (hiredDay < birthDay) {
                            System.out.println("Below the employee's working age! Please enter again.");
                            hiredDay = sc.nextInt();
                        } else {
                            System.out.println("Invalid day, Feb just have 29 day! Please enter again.");
                            hiredDay = sc.nextInt();
                        }
                    }
                } else {                //-- It is NOT leap year: February have 28 day
                    while (hiredDay < birthDay || hiredDay > 28) {
                        if (hiredDay < birthDay) {
                            System.out.println("Below the employee's working age! Please enter again.");
                            hiredDay = sc.nextInt();
                        } else {
                            System.out.println("Invalid day, Feb just have 28 day! Please enter again.");
                            hiredDay = sc.nextInt();
                        }
                    }
                }
                //+++++++++++++ thang co 31 ngay +++++++++++++
            } else if (hiredMonth == 1 || hiredMonth == 3 || hiredMonth == 5 || hiredMonth == 7
                    || hiredMonth == 8 || hiredMonth == 10 || hiredMonth == 12) {
                while (hiredDay < birthDay || hiredDay > 31) {
                    if (hiredDay < birthDay) {
                        System.out.println("Below the employee's working age! Please enter again.");
                        hiredDay = sc.nextInt();
                    } else {
                        System.out.println("Invalid day! Please enter again.");
                        hiredDay = sc.nextInt();
                    }
                }
                //+++++++++++++ thang co 30 ngay +++++++++++++
            } else if (hiredMonth == 4 || hiredMonth == 6 || hiredMonth == 9 || hiredMonth == 11) {
                while (hiredDay < birthDay || hiredDay > 30) {
                    if (hiredDay < birthDay) {
                        System.out.println("Below the employee's working age! Please enter again.");
                        hiredDay = sc.nextInt();
                    } else {
                        System.out.println("Invalid day! Please enter again.");
                        hiredDay = sc.nextInt();
                    }
                }
            }
            //-- cung nam, KHAC thang so vs thang bat dau lao dong (MIN)--------
        } else if (hiredYear == birthYear && hiredMonth > birthMonth) {
            //+++++++++++++ la thang 2 +++++++++++++
            if (hiredMonth == 2) {
                if (checkLeap == true) {    //-- It is leap year: February have 29 day
                    while (hiredDay < 1 || hiredDay > 29) {
                        System.out.println("Invalid day, Feb just have 29 day! Please enter again.");
                        hiredDay = sc.nextInt();
                    }
                } else {                //-- It is NOT leap year: February have 28 day
                    while (hiredDay < 1 || hiredDay > 28) {
                        System.out.println("Invalid day, Feb just have 28 day! Please enter again.");
                        hiredDay = sc.nextInt();
                    }
                }
                //+++++++++++++ thang co 31 ngay +++++++++++++
            } else if (hiredMonth == 1 || hiredMonth == 3 || hiredMonth == 5 || hiredMonth == 7
                    || hiredMonth == 8 || hiredMonth == 10 || hiredMonth == 12) {
                while (hiredDay < 1 || hiredDay > 31) {
                    System.out.println("Invalid day! Please enter again.");
                    hiredDay = sc.nextInt();
                }
                //+++++++++++++ thang co 30 ngay +++++++++++++
            } else if (hiredMonth == 4 || hiredMonth == 6 || hiredMonth == 9 || hiredMonth == 11) {
                while (hiredDay < 1 || hiredDay > 30) {
                    System.out.println("Invalid day! Please enter again.");
                    hiredDay = sc.nextInt();
                }
            }
            //-- KHAC nam so vs nam ve huu va nam bat dau lao dong (MIN, MAX)---
        } else if (hiredYear < retireYear && hiredYear > birthYear) {
            //+++++++++++++ la thang 2 +++++++++++++
            if (hiredMonth == 2) {
                if (checkLeap == true) {    //-- It is leap year: February have 29 day
                    while (hiredDay < 1 || hiredDay > 29) {
                        System.out.println("Invalid day, Feb just have 29 day! Please enter again.");
                        hiredDay = sc.nextInt();
                    }
                } else {                //-- It is NOT leap year: February have 28 day
                    while (hiredDay < 1 || hiredDay > 28) {
                        System.out.println("Invalid day, Feb just have 28 day! Please enter again.");
                        hiredDay = sc.nextInt();
                    }
                }
                //+++++++++++++ thang co 31 ngay +++++++++++++
            } else if (hiredMonth == 1 || hiredMonth == 3 || hiredMonth == 5 || hiredMonth == 7
                    || hiredMonth == 8 || hiredMonth == 10 || hiredMonth == 12) {
                while (hiredDay < 1 || hiredDay > 31) {
                    System.out.println("Invalid day! Please enter again.");
                    hiredDay = sc.nextInt();
                }
                //+++++++++++++ thang co 30 ngay +++++++++++++
            } else if (hiredMonth == 4 || hiredMonth == 6 || hiredMonth == 9 || hiredMonth == 11) {
                while (hiredDay < 1 || hiredDay > 30) {
                    System.out.println("Invalid day! Please enter again.");
                    hiredDay = sc.nextInt();
                }
            }
        }

        //------- FORMAT HIRED DATE ---------------------
        String strHiredDay = String.valueOf(hiredDay);
        String strHiredMonth = String.valueOf(hiredMonth);
        String strHiredYear = String.valueOf(hiredYear);
        //-- convert value [dd-mm-yyyy] from INTEGER to STRING
        String formatHiDate = strHiredDay + "-" + strHiredMonth + "-" + strHiredYear;
        return formatHiDate;
    }

    //== FORMAT RESIGN DATE ======================================================================================================
    public static String formatResignDate(String welcome, String ExConTime, String hiredDate) {
        System.out.println(welcome);
        Scanner sc = new Scanner(System.in);
        //-- COMPARE RESIGN DATE WITH HIRED DATE (MIN) -------------------------
        String lineHDate = hiredDate;
        String[] part1 = lineHDate.split("-");
        int hireDay = Integer.parseInt(part1[0].trim());
        int hireMonth = Integer.parseInt(part1[1].trim());
        int hireYear = Integer.parseInt(part1[2].trim());

        //-- COMPARE RESIGN DATE WITH EXPIRE CONTRACT TIME (MAX) ---------------
        String lineECTime = ExConTime;
        String[] part2 = lineECTime.split("-");
        int contractDay = Integer.parseInt(part2[0].trim());
        int contractMonth = Integer.parseInt(part2[1].trim());
        int contractYear = Integer.parseInt(part2[2].trim());
        //------- LIMIT YEAR RESIGN ---------------------
        System.out.print("Enter year resign [yyyy]: ");
        int resignYear = sc.nextInt();
        while (resignYear > contractYear || resignYear < hireYear) {
            if (resignYear > contractYear) {
                System.out.println("The resign time cannot exceed the employment contract time! Please enter again.");
                resignYear = sc.nextInt();
            } else {
                System.out.println("The resign time cannot be less than the employment contract time! Please enter again.");
                resignYear = sc.nextInt();
            }
        }
        //------- LIMIT MONTH RESIGN ---------------------
        System.out.print("Enter month resign [mm]: ");
        int resignMonth = sc.nextInt();
        //-- CUNG nam so vs nam gioi han hop dong lao dong (MAX)
        if (resignYear == contractYear) {
            while (resignMonth > contractMonth || resignMonth < 1) {
                if (resignMonth > contractMonth) {
                    System.out.println("The resign time cannot exceed the employment contract time! Please enter again.");
                    resignMonth = sc.nextInt();
                } else {
                    System.out.println("Invalid month! Please enter again.");
                    resignMonth = sc.nextInt();
                }
            }
            //-- CUNG nam so vs nam gioi han hop dong lao dong (MIN)
        } else if (resignYear == hireYear) {
            while (resignMonth < hireMonth || resignMonth > 12) {
                if (resignMonth < hireMonth) {
                    System.out.println("The resign time cannot be less than the employment contract time! Please enter again.");
                    resignMonth = sc.nextInt();
                } else {
                    System.out.println("Invalid month! Please enter again.");
                    resignMonth = sc.nextInt();
                }
            }

            //-- KHAC nam so vs nam gioi han hop dong lao dong (MAX, MIN)
        } else if (resignYear > hireYear && resignYear < contractYear) {
            while (resignMonth < 1 || resignMonth > 12) {
                System.out.println("Invalid month! Please enter again.");
                resignMonth = sc.nextInt();
            }
        }
        //------- LIMIT DAY RESIGN ---------------------
        /*
        hop dong lao dong co 2 gioi han DAU va CUOI thoi gian hop dong
        gioi han DAU: thoi diem bat dau lam viec
        gioi han CUOI: thoi diem het hop dong (gia han them neu co) lam viec
         */
        System.out.print("Enter day resign [dd]: ");
        int resignDay = sc.nextInt();
        boolean checkLeap = checkLeapYear(resignYear);
        //-- cung nam, CUNG thang so vs thang gioi han CUOI hop dong lao dong (MAX)
        if (resignYear == contractYear && resignMonth == contractMonth) {
            while (resignDay < 1 || resignDay > contractDay) {
                if (resignDay > contractDay) {
                    System.out.println("The resign time cannot exceed the employment contract time! Please enter again.");
                    resignDay = sc.nextInt();
                } else {
                    System.out.println("Invalid day! Please enter again.");
                    resignDay = sc.nextInt();
                }
            }
            //-- cung nam, KHAC thang so vs thang gioi han CUOI hop dong lao dong (MAX)
        } else if (resignYear == contractYear && resignMonth < contractMonth) {
            //+++++++++++++ la thang 2 +++++++++++++
            if (resignMonth == 2) {
                if (checkLeap == true) {    //-- It is leap year: February have 29 day
                    while (resignDay < 1 || resignDay > 29) {
                        System.out.println("Invalid day, Feb just have 29 day! Please enter again.");
                        resignDay = sc.nextInt();
                    }
                } else {                //-- It is NOT leap year: February have 28 day
                    while (resignDay < 1 || resignDay > 28) {
                        System.out.println("Invalid day, Feb just have 28 day! Please enter again.");
                        resignDay = sc.nextInt();
                    }
                }
                //+++++++++++++ thang co 31 ngay +++++++++++++
            } else if (resignMonth == 1 || resignMonth == 3 || resignMonth == 5 || resignMonth == 7
                    || resignMonth == 8 || resignMonth == 10 || resignMonth == 12) {
                while (resignDay < 1 || resignDay > 31) {
                    System.out.println("Invalid day! Please try again.");
                    resignDay = sc.nextInt();
                }
                //+++++++++++++ thang co 30 ngay +++++++++++++
            } else if (resignMonth == 4 || resignMonth == 6 || resignMonth == 9 || resignMonth == 11) {
                while (resignDay < 1 || resignDay > 30) {
                    System.out.println("Invalid day! Please try again.");
                    resignDay = sc.nextInt();
                }
            }
            //-- cung nam, CUNG thang so vs thang gioi han DAU hop dong lao dong (MIN)
        } else if (resignYear == hireYear && resignMonth == hireMonth) {

            while (resignDay < hireDay || resignDay > 29) {
                if (resignDay < hireDay) {
                    System.out.println("The resign time cannot be less than the employment contract time! Please enter again.");
                    resignDay = sc.nextInt();
                } else {
                    System.out.println("Invalid day! Please enter again.");
                    resignDay = sc.nextInt();
                }
            }
            //-- cung nam, KHAC thang so vs thang gioi han DAU hop dong lao dong (MIN)
        } else if (resignYear == hireYear && resignMonth > hireMonth) {
            //+++++++++++++ la thang 2 +++++++++++++
            if (resignMonth == 2) {
                if (checkLeap == true) {    //-- It is leap year: February have 29 day
                    while (resignDay < 1 || resignDay > 29) {
                        System.out.println("Invalid day, Feb just have 29 day! Please enter again.");
                        resignDay = sc.nextInt();
                    }
                } else {                //-- It is NOT leap year: February have 28 day
                    while (resignDay < 1 || resignDay > 28) {
                        System.out.println("Invalid day, Feb just have 28 day! Please enter again.");
                        resignDay = sc.nextInt();
                    }
                }
                //+++++++++++++ thang co 31 ngay +++++++++++++
            } else if (resignMonth == 1 || resignMonth == 3 || resignMonth == 5 || resignMonth == 7
                    || resignMonth == 8 || resignMonth == 10 || resignMonth == 12) {
                while (resignDay < 1 || resignDay > 31) {
                    System.out.println("Invalid day! Please try again.");
                    resignDay = sc.nextInt();
                }
                //+++++++++++++ thang co 30 ngay +++++++++++++
            } else if (resignMonth == 4 || resignMonth == 6 || resignMonth == 9 || resignMonth == 11) {
                while (resignDay < 1 || resignDay > 30) {
                    System.out.println("Invalid day! Please try again.");
                    resignDay = sc.nextInt();
                }
            }

            //-- KHAC nam so vs thoi gian DAU/ CUOI gioi han hop dong lao dong (MIN, MAX)
        } else if (resignYear < contractYear && resignYear > hireYear) {
            //+++++++++++++ la thang 2 +++++++++++++
            if (resignMonth == 2) {
                if (checkLeap == true) {    //-- It is leap year: February have 29 day
                    while (resignDay < 1 || resignDay > 29) {
                        System.out.println("Invalid day, Feb just have 29 day! Please enter again.");
                        resignDay = sc.nextInt();
                    }
                } else {                //-- It is NOT leap year: February have 28 day
                    while (resignDay < 1 || resignDay > 28) {
                        System.out.println("Invalid day, Feb just have 28 day! Please enter again.");
                        resignDay = sc.nextInt();
                    }
                }
                //+++++++++++++ thang co 31 ngay +++++++++++++
            } else if (resignMonth == 1 || resignMonth == 3 || resignMonth == 5 || resignMonth == 7
                    || resignMonth == 8 || resignMonth == 10 || resignMonth == 12) {
                while (resignDay < 1 || resignDay > 31) {
                    System.out.println("Invalid day! Please try again.");
                    resignDay = sc.nextInt();
                }
                //+++++++++++++ thang co 30 ngay +++++++++++++
            } else if (resignMonth == 4 || resignMonth == 6 || resignMonth == 9 || resignMonth == 11) {
                while (resignDay < 1 || resignDay > 30) {
                    System.out.println("Invalid day! Please try again.");
                    resignDay = sc.nextInt();
                }
            }
        }

        //------- FORMAT RESIGN DATE ---------------------
        String strResignDay = String.valueOf(resignDay);
        String strResignMonth = String.valueOf(resignMonth);
        String strResignYear = String.valueOf(resignYear);
        //-- convert value [dd-mm-yyyy] from INTEGER to STRING
        String formatReDate = strResignDay + "-" + strResignMonth + "-" + strResignYear;
        return formatReDate;
    }

    //== CALCULATE EXPIRE CONTRACT TIME ==========================================================================================
    public static String calExpireContractTime(int time, String formatHiredDate) {
        /*
        thoi gian het han hop dong (expire contract time)
        = ngay ky hop dong (hired date) + thoi gian hop dong (contract time)
         */
        int contractTime = time;

        //-- split format hired date
        String lineHDate = formatHiredDate;
        String[] part = lineHDate.split("-");
        int contractTimeDay = Integer.parseInt(part[0].trim());
        int contractTimeMonth = Integer.parseInt(part[1].trim());
        int contractTimeYear = Integer.parseInt(part[2].trim()) + contractTime;

        boolean checkLeap = checkLeapYear(contractTimeYear);
        if (contractTimeMonth == 2 && checkLeap == false) {
            contractTimeDay = 1;
            contractTimeMonth = 3;
        }

        //------- FORMAT EXPIRED CONTRACT TIME DATE ---------------------
        String strContractTimeDay = String.valueOf(contractTimeDay);
        String strContractTimeMonth = String.valueOf(contractTimeMonth);
        String strContractTimeYear = String.valueOf(contractTimeYear);
        //-- convert value [dd-mm-yyyy] from INTEGER to STRING
        String formatExpireContractTime = strContractTimeDay + "-" + strContractTimeMonth + "-" + strContractTimeYear;
        return formatExpireContractTime;
    }

    //=== RETIREMENT DATE ========================================================================================================
    public static String calRetireDate(String formatWorkingAge) {

        String lineBDate = formatWorkingAge;
        String[] part = lineBDate.split("-");
        int retireDay = Integer.parseInt(part[0].trim());
        int retireMonth = Integer.parseInt(part[1].trim());
        //-- Retire Date = Birth Date + 58
        int retireYear = Integer.parseInt(part[2].trim()) + 58;

        boolean checkLeap = checkLeapYear(retireYear);
        if (retireMonth == 2) {
            if (checkLeap == false) {        //-- It is leap year: February have 29 day
                if (retireDay == 29) {
                    retireMonth = 3;    //- tu thang 2 -> thang 3
                    retireDay = 1;      //- tu ngay 29 -> ngay 1
                }
            }
        }

        //------- FORMAT RETIREMENT DATE ---------------------
        String strRetireDay = String.valueOf(retireDay);
        String strRetireMonth = String.valueOf(retireMonth);
        String strRetireYear = String.valueOf(retireYear);
        //-- convert value [dd-mm-yyyy] from INTEGER to STRING
        String formatRetireDate = strRetireDay + "-" + strRetireMonth + "-" + strRetireYear;
        return formatRetireDate;
    }

    //== CHECK LEAP YEAR =========================================================================================================
    public static boolean checkLeapYear(int welcome) {
        int year = welcome;
        boolean isLeapYear = false;
        boolean result = false;

        if (year % 4 == 0) {
            if (year % 100 == 0) {
                if (year % 400 == 0) {
                    isLeapYear = true;
                } else {
                    isLeapYear = false;
                }
            } else {
                isLeapYear = true;
            }
        } else {
            isLeapYear = false;
        }

        if (isLeapYear) {
            return true;    //-- this is leap year
        } else {
            return result;  //-- this is not leap year
        }
    }

    //== GET INT =================================================================================================================
    public static int getInt(String welcome, int min, int max) {
        Scanner sc = new Scanner(System.in);
        boolean check = true;
        int number = 0;
        do {
            try {
                System.out.print(welcome);
                number = sc.nextInt();
                if (number < min || number > max) {
                    System.out.println("Need from " + min + " to " + max + ".");
                } else {
                    check = false;
                }
            } catch (Exception e) {
                System.out.println("Please input integer number!");
            }
        } while (check || number > max || number < min);
        return number;
    }

}
