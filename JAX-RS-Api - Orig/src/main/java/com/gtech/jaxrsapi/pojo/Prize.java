/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtech.jaxrsapi.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author mxbailey
 */
public class Prize implements Serializable{
    
    private BigDecimal prizeFund;
    private BigDecimal prizeFundIntl;
    private int numWinners;    
    private int numWinnersIntl;
    private Date insertTS;    
    private Date updateTS;    
    private String updateBy;

    public Date getInsertTS() {
        return insertTS;
    }

    public void setInsertTS(Date insertTS) {
        this.insertTS = insertTS;
    }

    public int getNumWinners() {
        return numWinners;
    }

    public void setNumWinners(int numWinners) {
        this.numWinners = numWinners;
    }

    public int getNumWinnersIntl() {
        return numWinnersIntl;
    }

    public void setNumWinnersIntl(int numWinnersIntl) {
        this.numWinnersIntl = numWinnersIntl;
    }

    public BigDecimal getPrizeFund() {
        return prizeFund;
    }

    public void setPrizeFund(BigDecimal prizeFund) {
        this.prizeFund = prizeFund;
    }

    public BigDecimal getPrizeFundIntl() {
        return prizeFundIntl;
    }

    public void setPrizeFundIntl(BigDecimal prizeFundIntl) {
        this.prizeFundIntl = prizeFundIntl;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateTS() {
        return updateTS;
    }

    public void setUpdateTS(Date updateTS) {
        this.updateTS = updateTS;
    }
    
    
}
