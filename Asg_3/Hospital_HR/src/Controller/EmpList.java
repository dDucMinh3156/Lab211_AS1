/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Employee;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Hospital HR
 *
 * @author Ta Minh Duc
 */
public class EmpList {

    private ArrayList<Employee> listEmployee;
    Scanner sc = new Scanner(System.in);

    public EmpList() {
        listEmployee = new ArrayList<>();
    }

    //-- set up display table
    private final String header = "|--------+----------------+-------------+--------------+--------------+--------------+--------+---------------+--------------|\n"
            + "|   ID   |      NAME      |    PHONE    |  BIRTH DATE  |     ROLE     |  HIRED DATE  | SALARY | CONTRACT TIME |  RESIGN DATE |\n"
            + "|--------+----------------+-------------+--------------+--------------+--------------+--------+---------------+--------------|";
    private final String footer = "|--------+----------------+-------------+--------------+--------------+--------------+--------+---------------+--------------|";

    //== READ/ LOAD FILE EMPLOYEE ================================================================================================
    public boolean loadFromFile(String filename) {
        File file = new File(filename);
        try {
            if (!file.exists()) {
                System.out.println("*** File does not existed! ***");
                return false;
            } else {
                BufferedReader bufferRead = new BufferedReader(new FileReader(file));
                String line = bufferRead.readLine();
                //-- employees.txt
                //-- seperate information follow format
                while (line != null) {
                    String[] part = line.split(", ");
                    if (part.length <= 1) {
                        break;
                    }
                    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                    String ID = part[0].trim();
                    String name = part[1].trim();
                    String phone = part[2].trim();
                    Date bDate = formatter.parse(part[3].trim());
                    String role = part[4].trim();
                    Date hiredDate = formatter.parse(part[5].trim());
                    int salary = Integer.parseInt(part[6].trim());
                    int contractTime = Integer.parseInt(part[7].trim());
                    String strContractTime = part[8].trim();
                    Date resignDate;
                    if (strContractTime.equals("null")) {
                        resignDate = null;
                    } else {
                        resignDate = formatter.parse(strContractTime);
                    }

                    Employee loadEmployee = new Employee(ID, name, phone, bDate,
                            role, hiredDate, salary, contractTime, resignDate);
                    listEmployee.add(loadEmployee);
                    line = bufferRead.readLine();

                }
                bufferRead.close();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //== OPTION 11: WRITE/ SAVE FILE EMPLOYEE ====================================================================================
    public boolean saveToFile(String filename) {
        try {
            PrintWriter write = new PrintWriter(filename);
            for (Employee allEmp : listEmployee) {
                write.println(allEmp.saveFormat());
            }
            write.flush();
            write.close();
            System.out.println("*** Data employee is saved in file [" + filename + "] successfully. ***");
            return true;
        } catch (Exception e) {
            System.out.println("*** Data employee has not been saved! ***");
            e.printStackTrace();
            return false;
        }
    }

    //== OPTION 1: ADD NEW EMPLOYEE ==============================================================================================
    public void addEmployee() {
        boolean ask;
        do {
            //-- ID, name, phone, bDate, role, hiredDate, salary, contractTime, resignDate
            try {
                //--> GET ID ---------------------
                System.out.println("-------");
                String aID = Utils.getStringID("Enter new ID of employee [EMxxxx]: ").trim().toUpperCase();
//                while (!Pattern.matches("^EM\\d{4}$", aID)) {
//                    System.out.println("ID wrong format: [EMxxxx]! Please enter again.");
//                    aID = Utils.getStringID("Enter new ID of employee [EMxxxx]: ").trim().toUpperCase();
//                }
                int pos = searchID(aID);
                while (pos > -1) {
                    System.out.println("ID has been existed! Please enter again.");
                    aID = Utils.getStringID("Enter new ID of employee [EMxxxx]: ").trim().toUpperCase();
                    pos = searchID(aID);
                }

                //--> GET NAME ---------------------
                System.out.println("-------");
                String aName = Utils.getString("Enter new name of employee: ").trim().toUpperCase();

                //--> GET PHONE NUMBER ---------------------
                System.out.println("-------");
                String aPhone = Utils.getString("Enter new phone of employee [9-11 (numbers)]: ").trim();
                while (!Pattern.matches("^\\d{9,11}$", aPhone)) {
                    System.out.println("Phone must have 9-11 numbers! Please enter again.");
                    aPhone = Utils.getString("Enter new phone of employee [9-11 (numbers)]: ").trim();
                }

                //--> GET BDATE ---------------------
                System.out.println("-------");
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                String birthDate = Utils.formatWorkingAge("--- Date of birth ---");
                Date aBDate = sdf.parse(birthDate);
                System.out.println("-> Birthdate [dd-mm-yyyy]: " + FormatDate.formatDateTool(aBDate, "dd-MM-yyyy"));

                //--> GET ROLE ---------------------
                System.out.println("-------");
                String aRole = "";
                System.out.println("--- Role of employee ---\n"
                        + "1- Doctor.\n"
                        + "2- Nurse.\n"
                        + "3- Technician.");
                int choose = 0;
                do {
                    System.out.print("Choice role 1..3: ");
                    choose = sc.nextInt();
                    switch (choose) {
                        case 1:
                            aRole = "Doctor";
                            break;
                        case 2:
                            aRole = "Nurse";
                            break;
                        case 3:
                            aRole = "Technician";
                            break;
                        default:
                            System.out.println("Invalid choice! Please enter again.");
                            break;
                    }
                } while (choose < 1 || choose > 3);

                //--> GET HIREDDATE ---------------------
                System.out.println("-------");
                String RetireDate = Utils.calRetireDate(birthDate);
                Date dRetireDate = sdf.parse(RetireDate);
                System.out.println("-> Retirement date: " + FormatDate.formatDateTool(dRetireDate, "dd-MM-yyyy"));

                String HiredDate = Utils.formatHiredDate("--- Date of hired ---", RetireDate, birthDate);
                Date aHiredDate = sdf.parse(HiredDate);
                System.out.println("-> Hired date [dd-mm-yyyy]: " + FormatDate.formatDateTool(aHiredDate, "dd-MM-yyyy"));

                //--> GET SALARY ---------------------
                System.out.println("-------");
                int aSalary = Utils.getInt("Enter salary [100-8,000 ($)]: ", 100, 8000);

                //--> GET CONTRACT TIME ---------------------
                System.out.println("-------");

                int enterContractTime = Utils.getInt("Enter contract time [1-30 (years)]: ", 1, 30);
                String ExpireContractTime = Utils.calExpireContractTime(enterContractTime, HiredDate);
                //-- split format Expire Contract Time 
                String lineECT = ExpireContractTime;
                String[] partECT = lineECT.split("-");
//                int ECTDay = Integer.parseInt(partECT[0].trim());
//                int ECTMonth = Integer.parseInt(partECT[1].trim());
                int ECTYear = Integer.parseInt(partECT[2].trim());
                //-- split format Retire Date
                String lineReDate = RetireDate;
                String[] partRD = lineReDate.split("-");
//                int RDDay = Integer.parseInt(partRD[0].trim());
//                int RDMonth = Integer.parseInt(partRD[1].trim());
                int RDYear = Integer.parseInt(partRD[2].trim());

                while (ECTYear > RDYear) {
                    System.out.println("Expire contract time cannot exceed the retire date! Please enter again.");
                    enterContractTime = Utils.getInt("Enter contract time [1-30 (years)]: ", 1, 30);
                    ExpireContractTime = Utils.calExpireContractTime(enterContractTime, HiredDate);
                    //-- split format Expire Contract Time
                    lineECT = ExpireContractTime;
                    partECT = lineECT.split("-");
                    ECTYear = Integer.parseInt(partECT[2].trim());
                    //-- split format Retire Date
                    lineReDate = RetireDate;
                    partRD = lineReDate.split("-");
                    RDYear = Integer.parseInt(partRD[2].trim());
                }

                int aContractTime = enterContractTime;

                //--> GET RESIGN DATE ---------------------
                System.out.println("-------");
                String askResign = Utils.getString("Do you want enter date of resign? [Y:yes/ N:no] -> ").trim().toLowerCase();
                Date aResignDate;
                if (askResign.contains("y")) {
                    ExpireContractTime = Utils.calExpireContractTime(aContractTime, HiredDate);
                    Date dExpireContractTime = sdf.parse(ExpireContractTime);
                    System.out.println("-> Expire contract time [dd-mm-yyyy]: " + FormatDate.formatDateTool(dExpireContractTime, "dd-MM-yyyy"));
                    String ResignDate = Utils.formatResignDate("--- Date of resign ---", ExpireContractTime, HiredDate);
                    aResignDate = sdf.parse(ResignDate);
                    System.out.println("-> Resign date [dd-mm-yyyy]: " + FormatDate.formatDateTool(aResignDate, "dd-MM-yyyy"));

                } else {
                    aResignDate = null;
                }

                //-- Push information to list employee
                Employee aEmployee = new Employee(aID, aName, aPhone, aBDate, aRole, aHiredDate, aSalary, aContractTime, aResignDate);
                listEmployee.add(aEmployee);
                System.out.println("*** Date employee is added successfully. ***");

            } catch (Exception e) {
                System.out.println("*** Data employee has not been added! ***");
            }

            ask = Utils.confirmYesNo("Do you want continue add? [Y:yes/ N:no]  ");
        } while (ask);

    }

    //== OPTION 2: SEARCH EMPLOYEE ===============================================================================================
    public void searchEmployee() {
        Scanner sc = new Scanner(System.in);
        boolean ask;

        do {
            try {
                if (listEmployee.isEmpty()) {
                    System.out.println("Empty list.");
                } else {
                    String searchID = Utils.getStringID("Enter ID employee want to search: ");
                    int pos = searchID(searchID);
                    if (pos < 0) {
                        System.out.println("Not found information of employee.");
                    } else {
                        System.out.println("--> Employee found.");
                        System.out.println("|------------------------------------------------- Information of employee --------------------------------------------------|");
                        System.out.println(header);
                        System.out.print(listEmployee.get(pos).displayFormat());
                        System.out.println(footer);

                        System.out.println("-------");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            ask = Utils.confirmYesNo("Do you want to continue search? [Y:yes/ N:no] -> ");
        } while (ask);
    }

    //== OPTION 3: REMOVE EMPLOYEE ===============================================================================================
    public void removeEmployee() {
        Scanner sc = new Scanner(System.in);
        boolean ask;
        do {

            try {
                if (listEmployee.isEmpty()) {
                    System.out.println("Empty list.");
                } else {
                    String removeID = Utils.getStringID("Enter ID employee want to remove: ").toUpperCase();
                    int pos = searchID(removeID);
                    if (pos < 0) {
                        System.out.println("Not found information of employee.");
                    } else {
                        System.out.println("|---------------------------------------------------- List of employees -----------------------------------------------------|");
                        System.out.println(header);
                        System.out.print(listEmployee.get(pos).displayFormat());
                        System.out.println(footer);

                        System.out.println("-------");
                        boolean askRemove;
                        Employee rEmploy = listEmployee.get(pos);
                        Date rResignDate = new Date();

                        askRemove = Utils.confirmYesNo("Are you sure want to DELETE? [Y:yes/ N:no] -> ");
                        if (askRemove == true) {
                            rEmploy.setResignDate(rResignDate);
                            System.out.println("*** Remove data product successfully. ***");
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("*** Remove data employee failure! ***");
            }

            ask = Utils.confirmYesNo("Do you want continue delete? [Y:yes/ N:no] -> ");
        } while (ask);
    }

    //== OPTION 4: UPDATE INFORMATION EMPLOYEE ===================================================================================
    public void updateEmployee() {
        Scanner sc = new Scanner(System.in);
        boolean ask;
        do {
            try {
                //-- Check exist of employee ---------------------
                System.out.println("-------");
                String uID = Utils.getStringID("Enter ID employee want to update: ").trim();
                int pos = searchID(uID);
                while (pos < 0) {
                    System.out.println("*** Employee does not exist! ***");
                    uID = Utils.getStringID("Enter ID employee want to update: ").trim();
                    pos = searchID(uID);
                }
                if (listEmployee.get(pos).getResignDate() == null) {
                    //-- Show old information ---------------------
                    System.out.println("-------");
                    System.out.println("Information employee: ");
                    System.out.println("|------------------------------------------------ Information before update -------------------------------------------------|");
                    System.out.println(header);
                    System.out.print(listEmployee.get(pos).displayFormat());
                    System.out.println(footer);
                    //-- Set new data to list employee
                    Employee uEmployee = listEmployee.get(pos);

                    //-- Update new information employee ---------------------
                    //-- create mini menu to choice 
                    int choiceMini = 0;
                    do {
                        System.out.println("---- UPDATE EMPLOYEE ----");
                        System.out.print("1- Name.\n"
                                + "2- Phone.\n"
                                + "3- Birthdate.\n"
                                + "4- Role.\n"
                                + "5- Contract time.\n"
                                + "6- Exit.\n"
                                + "Choose update 1..6: ");
                        choiceMini = sc.nextInt();
                        switch (choiceMini) {
                            case 1:
                                //--> UPDATE NAME ---------------------
                                System.out.println("-------");
                                String uName = Utils.getString("Enter new name of employee: ").toUpperCase().trim();
                                uEmployee.setName(uName);
                                break;
                            case 2:
                                //--> UPDATE PHONE NUMBER ---------------------
                                System.out.println("-------");
                                String uPhone = Utils.getString("Enter new phone of employee [9-11 (numbers)]: ").trim();
                                while (!Pattern.matches("^\\d{9,11}$", uPhone)) {
                                    System.out.println("Phone must have 9-11 numbers! Please enter again.");
                                    uPhone = uPhone = Utils.getString("Enter new phone of employee [9-11 (numbers)]: ").trim();
                                }
                                uEmployee.setPhone(uPhone);
                                break;
                            case 3:
                                //--> UPDATE BDATE ---------------------
                                System.out.println("-------");
                                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                                String birthDate = Utils.formatWorkingAge("Enter new date of birth [dd-mm-yyyy]: ").trim();
                                Date uBDate = sdf.parse(birthDate);
                                uEmployee.setbDate(uBDate);
                                break;
                            case 4:
                                //--> UPDATE ROLE ---------------------
                                System.out.println("-------");
                                String uRole = "";
                                System.out.println("--- Role of employee ---\n"
                                        + "1- Doctor.\n"
                                        + "2- Nurse.\n"
                                        + "3- Technician.");
                                int choose = 0;
                                do {
                                    System.out.print("Choice role 1..3: ");
                                    choose = sc.nextInt();
                                    switch (choose) {
                                        case 1:
                                            uRole = "Doctor";
                                            break;
                                        case 2:
                                            uRole = "Nurse";
                                            break;
                                        case 3:
                                            uRole = "Technician";
                                            break;
                                        default:
                                            System.out.println("Invalid choice! Please enter again.");
                                            break;
                                    }
                                } while (choose < 1 || choose > 3);
                                uEmployee.setRole(uRole);
                                break;
                            case 5:
                                //--> UPDATE CONTRACT TIME ---------------------
                                System.out.println("-------");
                                int uContractTime = Utils.getInt("Enter new contract time [1-30 (year)]: ", 1, 30);
                                uEmployee.setContractTime(uContractTime);
                                break;
                            default:
                                System.out.println("--- Exit update. ---");
                                break;
                        }
                    } while (choiceMini > 0 && choiceMini < 6);

                    System.out.println("|------------------------------------------------ Information after update --------------------------------------------------|");
                    System.out.println(header);
                    System.out.print(listEmployee.get(pos).displayFormat());
                    System.out.println(footer);

                    System.out.println("*** Data employee is updated successfully. ***");
                } else {
                    System.out.println("*** Employee has resigned, cannot be updated. ***");
                }
            } catch (Exception e) {
                System.out.println("*** Data employee has not been update! ***");
            }

            ask = Utils.confirmYesNo("Do you want continue update information of employee? [Y:yes/ N:no] -> ");
        } while (ask);
    }

    //== OPTION 5: VIEW ALL LIST =================================================================================================
    public void viewAllList() {
        boolean ask;
        do {
            if (listEmployee.isEmpty()) {
                System.out.println("Empty list.");
            } else {
                try {
                    System.out.println("|---------------------------------------------------- List of employees -----------------------------------------------------|");
                    System.out.println(header);
                    Collections.sort(listEmployee, new Employee());
                    for (Employee allEmp : listEmployee) {
                        System.out.print(allEmp.displayFormat());
                    }
                    System.out.println(footer);
                    System.out.println("-------");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            ask = Utils.confirmYesNo("Do you want continue view all list employee? [Y:yes/ N:no] -> ");
        } while (ask);
    }

    //== OPTION 6: VIEW TAKE-ON LIST =============================================================================================
    public void viewTakeOnList() {
        boolean ask;
        do {
            if (listEmployee.isEmpty()) {
                System.out.println("Empty list.");
            } else {
                try {
                    System.out.println("|------------------------------------------------ List of employees Take-on -------------------------------------------------|");
                    System.out.println(header);
                    Collections.sort(listEmployee, new Employee());
                    for (Employee allEmp : listEmployee) {
                        if (allEmp.getResignDate() == null) {
                            System.out.print(allEmp.displayFormat());
                        }
                    }
                    System.out.println(footer);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            ask = Utils.confirmYesNo("Do you want continue view take-on list employee? [Y:yes/ N:no] -> ");
        } while (ask);
    }

    //== OPTION 7: VIEW RESIGN LIST ==============================================================================================
    public void viewResignList() {
        boolean ask;
        do {
            if (listEmployee.isEmpty()) {
                System.out.println("Empty list.");
            } else {
                try {
                    System.out.println("|------------------------------------------------ List of employees Resign --------------------------------------------------|");
                    System.out.println(header);
                    Collections.sort(listEmployee, new Employee());
                    for (Employee allEmp : listEmployee) {
                        if (allEmp.getResignDate() != null) {
                            System.out.print(allEmp.displayFormat());
                        }
                    }
                    System.out.println(footer);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            ask = Utils.confirmYesNo("Do you want continue view resign list employee? [Y:yes/ N:no] -> ");
        } while (ask);
    }

    //== OPTION 8: VIEW DOCTOR LIST ==============================================================================================
    public void viewDoctorList() {
        boolean ask;
        do {
            if (listEmployee.isEmpty()) {
                System.out.println("Empty list.");
            } else {
                try {
                    System.out.println("|------------------------------------------------ List of employees Doctor --------------------------------------------------|");
                    System.out.println(header);
                    Collections.sort(listEmployee, new Employee());
                    for (Employee allEmp : listEmployee) {
                        if (allEmp.getRole().equals("Doctor")) {
                            System.out.print(allEmp.displayFormat());
                        }
                    }
                    System.out.println(footer);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            ask = Utils.confirmYesNo("Do you want continue view doctor list employee? [Y:yes/ N:no] -> ");
        } while (ask);
    }

    //== OPTION 9: VIEW NURSE LIST ===============================================================================================
    public void viewNurseList() {
        boolean ask;
        do {
            if (listEmployee.isEmpty()) {
                System.out.println("Empty list.");
            } else {
                try {
                    System.out.println("|------------------------------------------------- List of employees Nurse --------------------------------------------------|");
                    System.out.println(header);
                    Collections.sort(listEmployee, new Employee());
                    for (Employee allEmp : listEmployee) {
                        if (allEmp.getRole().equals("Nurse")) {
                            System.out.print(allEmp.displayFormat());
                        }
                    }
                    System.out.println(footer);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            ask = Utils.confirmYesNo("Do you want continue view nurse list employee? [Y:yes/ N:no] -> ");
        } while (ask);
    }

    //== OPTION 10: VIEW TECHNICIAN LIST =========================================================================================
    public void viewTechnicianList() {
        boolean ask;
        do {
            if (listEmployee.isEmpty()) {
                System.out.println("Empty list.");
            } else {
                int count = 0;
                for (Employee allEmp : listEmployee) {
                        if (allEmp.getRole().equals("Technician")) {
                            count++;
                        }
                    }
                if (count > 0) {
                    try {
                        System.out.println("|---------------------------------------------- List of employees Technician ------------------------------------------------|");
                        System.out.println(header);
                        Collections.sort(listEmployee, new Employee());
                        for (Employee allEmp : listEmployee) {
                            if (allEmp.getRole().equals("Technician")) {
                                System.out.print(allEmp.displayFormat());
                            }
                        }
                        System.out.println(footer);
                        
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("Do not have technician employee.");
                }
            }

            ask = Utils.confirmYesNo("Do you want continue view technician list employee? [Y:yes/ N:no] -> ");
        } while (ask);
    }

    //== SEARCH ID EMPLOYEE ======================================================================================================
    public int searchID(String empID) {
        int N = listEmployee.size();
        for (int i = 0; i <= N - 1; i++) {
            if (listEmployee.get(i).getId().equals(empID)) {
                return i;
            }
        }
        return -1;
    }
}
