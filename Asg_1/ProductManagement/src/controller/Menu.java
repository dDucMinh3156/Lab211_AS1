/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Ta Minh Duc
 */
public class Menu {

    //-- create menu
    public int int_getChoice(ArrayList options) {
        int response;
        int N = options.size();
        for (int i = 0; i <= N - 2; i++) {
            System.out.println((i + 1) + ". " + options.get(i));
        }
        System.out.println("Other- Quit.");

        System.out.println("--------------***---------------");
        System.out.print("Please choose an option 1.." + (N - 1) + ": ");

        Scanner sc = new Scanner(System.in);
        response = sc.nextInt();
        return response;
    }

    public <E> E ref_getChoice(ArrayList<E> options) {
        int response;
        int N = options.size();
        do {
            response = int_getChoice(options);
        } while (response < 0 || response > N);
        return options.get(response - 1);
    }

}
