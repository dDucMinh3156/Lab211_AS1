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
public class SortQuantity extends Products implements Comparator<Products> {

    //-- sort data following Quantity product: descending
    @Override
    public int compare(Products q1, Products q2) {
        if (q1.getQuantity() == q2.getQuantity()) {
            return 0;
        } else if (q1.getQuantity() > q2.getQuantity()) {
            return -1;
        } else {
            return 1;
        }
    }
}
