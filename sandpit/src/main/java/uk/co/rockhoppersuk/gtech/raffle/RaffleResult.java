/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.rockhoppersuk.gtech.raffle;

import java.util.Date;

/**
 *
 * @author mxbailey
 */
public class RaffleResult {

    private int drawNumber;
    private int drawSequence;
    private int drawTypeId;
    private String raffleNumber;
    private String formattedRaffleNumber;
    private Date insertTs;
    private short tierId;

    public int getDrawNumber() {
        return drawNumber;
    }

    public void setDrawNumber(int drawNumber) {
        this.drawNumber = drawNumber;
    }

    public int getDrawSequence() {
        return drawSequence;
    }

    public void setDrawSequence(int drawSequence) {
        this.drawSequence = drawSequence;
    }

    public int getDrawTypeId() {
        return drawTypeId;
    }

    public void setDrawTypeId(int drawTypeId) {
        this.drawTypeId = drawTypeId;
    }

    public String getRaffleNumber() {
        return raffleNumber;
    }

    public void setRaffleNumber(String raffleNumber) {
        this.raffleNumber = raffleNumber;
    }

    public String getFormattedRaffleNumber() {
        return formattedRaffleNumber;
    }

    public void setFormattedRaffleNumber(String formattedRaffleNumber) {
        this.formattedRaffleNumber = formattedRaffleNumber;
    }

    public Date getInsertTs() {
        return insertTs;
    }

    public void setInsertTs(Date insertTs) {
        this.insertTs = insertTs;
    }

    public short getTierId() {
        return tierId;
    }

    public void setTierId(short tierId) {
        this.tierId = tierId;
    }


    

}
