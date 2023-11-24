/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Comparator;

/**
 *
 * @author Ta Minh Duc
 */
public class SortUnitPrice extends Products implements Comparator<Products> {

    //-- sort data following Unit Price product: ascending
    @Override
    public int compare(Products up1, Products up2) {
        if (up1.getUnitPrice() == up2.getUnitPrice()) {
            return 0;
        } else if (up1.getUnitPrice() > up2.getUnitPrice()) {
            return 1;
        } else {
            return -1;
        }
    }
}
