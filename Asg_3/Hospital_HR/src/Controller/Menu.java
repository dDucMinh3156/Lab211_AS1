/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Hospital HR
 *
 * @author Ta Minh Duc
 */
public class Menu {

    //-- create main menu
    public int int_getChocie(ArrayList options) {
        int response;
        int N = options.size();
        for (int i = 0; i <= N - 1; i++) {
            System.out.println((i + 1) + ". " + options.get(i));
        }

        System.out.println("---------------***---------------");
        System.out.print("Please choose an option 1.." + N + ": ");
        Scanner sc = new Scanner(System.in);
        response = sc.nextInt();
        return response;
    }

    //-- get response from menu
    public <E> E ref_getChoice(ArrayList<E> options) {
        int response;
        int N = options.size();
        do {
            response = int_getChocie(options);
        } while (response < 0 || response > N);
        return options.get(response - 1);
    }

}
