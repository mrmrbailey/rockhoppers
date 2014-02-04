package com.camelotinteractive.game.instant;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class GameOutcome implements Cloneable {

    /**
     * The prize amount for this outcome
     */
    private BigDecimal prizeAmount;

    /**
     * Tier Number for this outcome
     */
    private int tierNumber;

    /**
     * WinStatus for this outcome. Winner if true. Loser if false.
     */
    private boolean winStatus;

    /**
     * isWinStatusSet is false for a new object. Once the winStatus is set it is
     * set to true.
     */
    private boolean isWinStatusSet;

    /**
     * The prize type for this outcome
     */
    private String prizeType;

    /**
     * The extra parameter Map
     */
    private Map extraParams;

    /**
     * The default prize amount which is obviously zero
     */
    private final static BigDecimal defaultPrizeAmount = BigDecimal.valueOf(0);

    /**
     * The Scenario name to be set by the GTH - defualt of null
     */
    private int scenarioId = -1;

    /**
     * Simple constructor which sets the object to default values. Defaults are
     * as follows: <br>
     *
     * <ul>
     *   <li> prize amount = 0.00
     *   <li> tier number = 0
     *   <li> win status = false
     *   <li> prize type = null
     * </ul>
     *
     */
    public GameOutcome() {
        super();
        this.prizeAmount = GameOutcome.defaultPrizeAmount;
        this.tierNumber = 0;
        this.winStatus = false;
        this.prizeType = null;
        this.isWinStatusSet = false;
        this.scenarioId = -1;
    }


    /**
     * getter for prize amount
     *
     * @return   BigDecimal - prize amount
     */
    public BigDecimal getPrizeAmount() {
        return this.prizeAmount;
    }


    /**
     * getter for tier number
     *
     * @return   int - result prize tier
     */
    public int getTierNumber() {
        return this.tierNumber;
    }


    /**
     * returns win status.
     *
     * @return   true - winner
     * @return   false - loser
     */
    public boolean isWinner() {
        if (this.isWinStatusSet) {
            return this.winStatus;
        } else {
            if (this.prizeAmount.compareTo(GameOutcome.defaultPrizeAmount) > 0) {
                return true;
            } else {
                return false;
            }
        }
    }


    /**
     * getter for the prize type value
     *
     * @return   String - prize type
     */
    public String getPrizeType() {
        return this.prizeType;
    }


    /**
     * getter for the extra game parameters
     *
     * @return   Map - contains extra game parameters <br>
     *      The Map contains the following parameters: <br>
     *      Name: "playType"; Object: String - Possible values as follows:
     *      "FREE$", "TRIAL", "WAGER"<br>
     *      Name: "ticketID"; Object: String - alphanumeric value <br>
     *
     */
    public Map getExtraParameters() {
        return this.extraParams;
    }

    /**
     * Getter for the ScenarioName.
     */
    public int getScenarioId() {
        return scenarioId;
    }

    /**
     * Setter for the extra game parameters
     *
     * @param newExtraParams  The new ExtraParameters value
     */
    protected void setExtraParameters(Map newExtraParams) {
        this.extraParams = newExtraParams;
    }


    /**
     * protected method to set the prize amount
     *
     * @param newPrizeAmount  The new PrizeAmount value
     */
    protected void setPrizeAmount(BigDecimal newPrizeAmount) {
        this.prizeAmount = newPrizeAmount;
    }


    /**
     * protected method to set the result tier
     *
     * @param newTierNumber  The new TierNumber value
     */
    protected void setTierNumber(int newTierNumber) {
        this.tierNumber = newTierNumber;
    }


    /**
     * protected method to set the win status to false
     */
    protected void setToWinner() {
        this.winStatus = true;
        this.isWinStatusSet = true;
    }


    /**
     * protected method to set the win status to true
     */
    protected void setToLoser() {
        this.winStatus = false;
        this.isWinStatusSet = true;
    }


    /**
     * protected method to set the prize type
     *
     * @param newPrizeType  The new PrizeType value
     */
    protected void setPrizeType(String newPrizeType) {
        this.prizeType = newPrizeType;
    }

    /**
     * Setter for the ScenarioName.
     */
    protected void setScenarioId(int i) {
        scenarioId = i;
    }

    /**
     * Method to clone the GameOutcome
     *
     * @return                                The Cloned GameoutCome object
     *      Value GameOutcome
     * @exception CloneNotSupportedException  Exception thrown if Cloning is not
     *      supported by implementing Cloneable interface
     */
    protected Object clone()
        throws CloneNotSupportedException {
        GameOutcome cloneGO = (GameOutcome) super.clone();
        cloneGO.setPrizeAmount(new BigDecimal(prizeAmount.unscaledValue(), prizeAmount.scale()));
        cloneGO.setPrizeType(this.prizeType);
        HashMap clonemap = new HashMap();
        clonemap.putAll(extraParams);
        cloneGO.setExtraParameters(clonemap);
        return cloneGO;
    }

}
