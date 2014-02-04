/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtech.jaxrsapi.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *
 * @author mxbailey
 */
public class DrawResult implements Serializable {

    private Date drawDate;
    private char resultConfirmedInd;
    private Date nextDrawDate;
    private BigDecimal nextEstJackpot;
    private short numRollovers;
    private String operatorInsert;
    private String operatorVerify;
    private Date insertTs;
    private Date updateTs;
    private String updateBy;
    private String machineName;
    private String ballset;
    private List<Prize> prizes;
    private Date lastClaimDate;
    private boolean pariMutuelJackpot;
    private List<PrizeType> prizeTypes;

    public Date getLastClaimDate() {
        return lastClaimDate;
    }

    public void setLastClaimDate(Date lastClaimDate) {
        this.lastClaimDate = lastClaimDate;
    }

    public boolean isPariMutuelJackpot() {
        return pariMutuelJackpot;
    }

    public void setPariMutuelJackpot(boolean pariMutuelJackpot) {
        this.pariMutuelJackpot = pariMutuelJackpot;
    }

    public List<PrizeType> getPrizeTypes() {
        return prizeTypes;
    }

    public void setPrizeTypes(List<PrizeType> prizeTypes) {
        this.prizeTypes = prizeTypes;
    }

    public String getBallset() {
        return ballset;
    }

    public void setBallset(String ballset) {
        this.ballset = ballset;
    }

    public Date getDrawDate() {
        return drawDate;
    }

    public void setDrawDate(Date drawDate) {
        this.drawDate = drawDate;
    }

    public Date getInsertTs() {
        return insertTs;
    }

    public void setInsertTs(Date insertTs) {
        this.insertTs = insertTs;
    }

    public String getMachineName() {
        return machineName;
    }

    public void setMachineName(String machineName) {
        this.machineName = machineName;
    }

    public Date getNextDrawDate() {
        return nextDrawDate;
    }

    public void setNextDrawDate(Date nextDrawDate) {
        this.nextDrawDate = nextDrawDate;
    }

    public BigDecimal getNextEstJackpot() {
        return nextEstJackpot;
    }

    public void setNextEstJackpot(BigDecimal nextEstJackpot) {
        this.nextEstJackpot = nextEstJackpot;
    }

    public short getNumRollovers() {
        return numRollovers;
    }

    public void setNumRollovers(short numRollovers) {
        this.numRollovers = numRollovers;
    }

    public String getOperatorInsert() {
        return operatorInsert;
    }

    public void setOperatorInsert(String operatorInsert) {
        this.operatorInsert = operatorInsert;
    }

    public String getOperatorVerify() {
        return operatorVerify;
    }

    public void setOperatorVerify(String operatorVerify) {
        this.operatorVerify = operatorVerify;
    }

    public List<Prize> getPrizes() {
        return prizes;
    }

    public void setPrizes(List<Prize> prizes) {
        this.prizes = prizes;
    }

    public char getResultConfirmedInd() {
        return resultConfirmedInd;
    }

    public void setResultConfirmedInd(char resultConfirmedInd) {
        this.resultConfirmedInd = resultConfirmedInd;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateTs() {
        return updateTs;
    }

    public void setUpdateTs(Date updateTs) {
        this.updateTs = updateTs;
    }
}
