/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Ta Minh Duc
 */
public class ManageSeat {
    private String seatID;
    private String seatStatus;

    public ManageSeat(String seatID, String seatStatus) {
        this.seatID = seatID;
        this.seatStatus = seatStatus;
    }

    public String getSeatID() {
        return seatID;
    }

    public String getSeatStatus() {
        return seatStatus;
    }

    public void setSeatStatus(String seatStatus) {
        this.seatStatus = seatStatus;
    }
    
    @Override
    public String toString(){
        return seatID + " - " + seatStatus;
    }
}
