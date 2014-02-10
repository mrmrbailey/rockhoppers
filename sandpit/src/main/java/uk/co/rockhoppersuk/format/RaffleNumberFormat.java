/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.rockhoppersuk.format;

/**
 *
 * @author mxbailey
 */
public class RaffleNumberFormat {

    private String raffleNumber;

    public String getRaffleNumber() {
        return raffleNumber;
    }

    public void setRaffleNumber(String raffleNumber) {
        this.raffleNumber = raffleNumber;
    }

    public String getFormatedRaffleNumber() {

        StringBuilder formatedRaffleNumber = new StringBuilder();
        formatedRaffleNumber.append(raffleNumber.substring(0, 4));
        formatedRaffleNumber.append(" ");
        formatedRaffleNumber.append(raffleNumber.substring(4, 8));
        formatedRaffleNumber.append(" ");
        formatedRaffleNumber.append(raffleNumber.substring(8, 12));
        return formatedRaffleNumber.toString();
    }

    public String getFormatedRaffleNumberStringBuilder() {


        StringBuilder formatedRaffleNumber = new StringBuilder(raffleNumber);
        formatedRaffleNumber.insert(8, " ");
        formatedRaffleNumber.insert(4, " ");
        return formatedRaffleNumber.toString();
    }

}
