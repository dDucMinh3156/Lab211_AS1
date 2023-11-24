/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.Menu;
import Controller.EmpList;
import java.util.ArrayList;

/**
 * Hospital HR
 *
 * @author Ta Minh Duc
 */
public class Hospital_HR_Mng {

    public static void main(String[] args) {
        //-- load file employee
        EmpList empList = new EmpList();
        String empListFile = "employee.txt";
        if (!empList.loadFromFile(empListFile)) {
            System.out.println("*** Can not load file [" + empListFile + "]! ***");
            return;
        } else {
            System.out.println("*** File [" + empListFile + "] is loaded. ***");
        }

        //-- ArrayList options
        ArrayList<String> option = new ArrayList<>();
        option.add("Add new employee");
        option.add("Search employee");
        option.add("Remove employee");
        option.add("Update employee");
        option.add("View all list");
        option.add("View take-on list");
        option.add("View resign list");
        option.add("View docter list");
        option.add("View nurse list");
        option.add("view technician list");
        option.add("Write list to file");
        option.add("Quit the program");

        //-- create menu
        Menu menu = new Menu();
        int choice = 0;
        do {
            System.out.println("\n------- HOSPITAL HR PROGRAM -------");
            choice = menu.int_getChocie(option);
            switch (choice) {
                case 1:
                    empList.addEmployee();
                    break;
                case 2:
                    empList.searchEmployee();
                    break;
                case 3:
                    empList.removeEmployee();
                    break;
                case 4:
                    empList.updateEmployee();
                    break;
                case 5:
                    empList.viewAllList();
                    break;
                case 6:
                    empList.viewTakeOnList();
                    break;
                case 7:
                    empList.viewResignList();
                    break;
                case 8:
                    empList.viewDoctorList();
                    break;
                case 9:
                    empList.viewNurseList();
                    break;
                case 10:
                    empList.viewTechnicianList();
                    break;
                case 11:
                    empList.saveToFile(empListFile);
                    break;
                case 12:
                    System.out.println("----------- END PROGRAM -----------");
                    break;
                default:
                    break;
            }

        } while (choice > 0 && choice < option.size());

    }
}
