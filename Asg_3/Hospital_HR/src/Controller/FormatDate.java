/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Hospital HR
 *
 * @author Ta Minh Duc
 */
public class FormatDate {

    public static String formatDateTool(Date date, String... Date_Format) {
        String format = Date_Format.length >= 1 ? Date_Format[0] : "dd-MM-yyyy";
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        String temp = null;
        try {
            temp = date != null ? formatter.format(date) : null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return temp;
    }

    public static Date formatDateTool(String date, String... Date_Format) {
        String format = Date_Format.length >= 1 ? Date_Format[0] : "dd-MM-yyyy";
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
