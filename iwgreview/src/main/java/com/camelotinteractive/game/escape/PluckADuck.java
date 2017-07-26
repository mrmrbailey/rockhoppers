package com.camelotinteractive.game.escape;

import java.util.Map;
import java.util.ArrayList;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.Arrays;

/**
 * Standard imports for creation of Java Ticket Generator
 **/
import com.camelotinteractive.game.instant.GameOutcome;
import com.camelotinteractive.game.instant.TicketDataException;
import com.camelotinteractive.game.instant.TicketDataGenerator;
import com.gtech.game.PrizeParameters;
import java.text.DecimalFormatSymbols;
import java.util.Iterator;

/**
 * PluckADuck ticket generation class.
 *
 **/
public class PluckADuck implements TicketDataGenerator {

    /**
     * The PrizeParameters object. This is set by the setPrizeParameters method.
     */
    private PrizeParameters prizeParams = null;

    // metric to count generation loop iterations
    private int generateCount;

    private final static String VERSION = "b";

    // Total number of clicks in the game
    private final static int NUM_GAME_ROWS = 8;
    
    // Number of columns in each click result row
    private final static int NUM_GAME_COLS = 2;

    // Tier definitions indicating win status
    private final static double[][] TIER_PRIZES = {
        {}, // Filler instead of manipulating the tier number for zero starting arrays
        
        {500, 500},
        {20, 20},
        {5, 5},        
        {1, 1},
        {0.25, 0.25},
        {0}
    };

    private final static int TOP_PRIZE = 500;
    private final static Double[] MEDIUM_PRIZES = { 500.0, 20.0 };
    private final static Double[] LOW_PRIZES = { 5.0, 1.0, 0.25 };

    private final static Double[] ALL_PRIZES = { 0.25, 1.0, 5.0, 20.0 ,500.0 };    

    private ArrayList<Double[]> gameRows;	// final holder for click data
      
    private final static double WINNER = 1;
    private final static double LOSER = 0;
    private final static int WINNING_COUNT = 3;
    private final static int NEAR_COUNT = 2;

    private final static int PRIZE_INDEX = 0;
    private final static int WINNER_INDEX = 1;
    
    /****************************************************************************************
     * Interface Definition
     ****************************************************************************************/
    /**
     * getTicketData
     * Returns actual XML ticket data for a particular game given a GameOutcome instance
     * Not sure what GameOutcome is yet as we do not have the classes
     *
     * @param gameOutcome - a GameOutcome object
     * @throws TicketDataException
     * @return String
     **/
    public String getTicketData(GameOutcome gameOutcome) throws com.camelotinteractive.game.instant.TicketDataException {

        int tier = gameOutcome.getTierNumber();

        // Check if instant win is required (Numbers are the two tier numbers that have instant wins)


        // Check if the game is a winner
        String win = gameOutcome.isWinner() ? "1" : "0";

        // Generate the game data


        this.generateGame(tier, gameOutcome.isWinner());

        // Generate the ticket xml
        // This is actually quite an inefficient way of doing things, but it is the cleanest from a code
        // readablility and simplicity point of view.
        
        
        StringBuilder xml = new StringBuilder();
        // <editor-fold defaultstate="expanded" desc="Build the ticket using a String Builder">
        xml.append("<?xml version='1.0' encoding='UTF-8' ?><ticket><outcome amount=\"");
        xml.append(formatDecimal(prizeParams.getAmount(tier)));
        xml.append("\" prizeTier=\"");
        xml.append(tier);
        xml.append("\" />");
        xml.append("<params wT=\"");
        xml.append(win);
        xml.append("\" pzArray=\"");
        ArrayList<Double> prizes = new ArrayList<Double>(Arrays.asList(ALL_PRIZES));
        for( Iterator<Double> it = prizes.iterator() ; it.hasNext();) {
            xml.append(this.formatDecimal(it.next()));
            if (it.hasNext()) {
                xml.append(",");
            }
        }
        xml.append("\" v=\"");
        xml.append(VERSION);
        xml.append("\"/>");
        Double[] clickResult;
        for (int i = 0; i < NUM_GAME_ROWS; i++) {
            clickResult = this.gameRows.get(i);
            xml.append("<turn name=\"d");
            xml.append(i);
            xml.append("\" pz=\"");
            xml.append(prizes.indexOf(clickResult[PRIZE_INDEX]));
            xml.append("\" wS=\"");            
            xml.append(clickResult[WINNER_INDEX].intValue());
            xml.append("\"/>");
        }
        xml.append("</ticket>");
        // </editor-fold>
        return xml.toString();
    }

    /**
     * setPrizeParameters
     * A required data member setter for a PrizeParameters object
     *
     * @param prizeParams - a PrizeParameters object
     **/
    public void setPrizeParameters(PrizeParameters prizeParams) {
        this.prizeParams = prizeParams;
    }

    /**
     * setCustomConfig
     * A required method for the TicketDataGenerator interface.
     * Takes a Map object as its only parameter.
     *
     * @param map - a Map object of configuration items
     **/
    public void setCustomConfig(Map parm1) {
    }

    /****************************************************************************************
     * Ticket Generation
     ****************************************************************************************/
    /**
     * Primary method for the generation of game content. It uses the tier information to determine
     * the type of ticket to make and sets up the internal data members ready for output of the ticket.
     * The execution of this method is limited by an iteration counter so that an infinite loop is never
     * fallen into.
     *
     * @throws TicketDataException
     **/
    private void generateGame(int tier, boolean isWinner) throws TicketDataException {

        this.gameRows = new ArrayList<Double[]>();
        ArrayList<Double> allPrizes = new ArrayList<Double>(Arrays.asList(ALL_PRIZES));
        Collections.shuffle(allPrizes);
        
        double[] tierData = TIER_PRIZES[tier];                

        Double[] row = null;

        if (isWinner) {
            // Place all of the winning turns across the board
            for(int i = 0; i < WINNING_COUNT; i++) {
                row = new Double[NUM_GAME_COLS];
                row[PRIZE_INDEX] = tierData[1];
                row[WINNER_INDEX] = WINNER;
                this.gameRows.add(row);
            }
            
            // Remove the prize so that there are no more than the required entries
            // to win
            allPrizes.remove(allPrizes.indexOf(tierData[1]));
            
        } else {
            
            Double prize = allPrizes.remove(0);
            for(int i = 0; i < NEAR_COUNT; i++) {
                row = new Double[NUM_GAME_COLS];
                row[PRIZE_INDEX] = prize;
                row[WINNER_INDEX] = LOSER;
                this.gameRows.add(row);
            }
            
        }
        
        // Double up allPrizes so that we can just pick from the list and know
        // there is no chance of a win occuring, but that near wins are possible
        
        int size = allPrizes.size();
        for (int i = 0; i < size ; i++) {
            allPrizes.add(allPrizes.get(i));
        }
        Collections.shuffle(allPrizes);
        
        // Now fill out the rest of the turns apart from the last. This makes sure
        int required = NUM_GAME_ROWS;
        if (!isWinner) {
            // if this is a loser we add all but one turn, then shuffle and add
            // the final turn so that the near win always happens before the last
            // turn.
            required -= 1;
        }
        while(this.gameRows.size() < required) {
            row = new Double[NUM_GAME_COLS];
            row[PRIZE_INDEX] = allPrizes.remove(0);
            row[WINNER_INDEX] = LOSER;
            this.gameRows.add(row);
        }
        Collections.shuffle(this.gameRows);
        if (!isWinner) {
            row = new Double[NUM_GAME_COLS];
            row[PRIZE_INDEX] = allPrizes.remove(0);
            row[WINNER_INDEX] = LOSER;
            this.gameRows.add(row);
        }
        
    }    
    
    /**
     * Generate random number between (and inclusively) first and second parameters
     *
     * @param low int
     * @param high int
     * @return int
     */
    private int randomBetween(int low, int high) {
        return (int) (Math.floor(Math.random() * ((high + 1) - low)) + low);
    }

    /**
     * formatDecimal
     * Helper method to spit out correctly formated numbers
     *
     * @param bd - Number the number to format
     * @return String
     **/
    private String formatDecimal(Number bd) {
        String format = "#,##0.00#";
        if (bd.floatValue() >= 10000) {
            format = "#,###";
        }
        DecimalFormat df = new DecimalFormat(format);
        if (bd.floatValue() >= 10000) {
            DecimalFormatSymbols dfs = new DecimalFormatSymbols();
            dfs.setGroupingSeparator(',');
            df.setDecimalFormatSymbols(dfs);
        }
        return df.format(bd.doubleValue());
    }

}
