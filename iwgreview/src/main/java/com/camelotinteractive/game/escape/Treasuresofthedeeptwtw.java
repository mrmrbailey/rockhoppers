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
import java.util.*;

/**
 * PluckADuck ticket generation class.
 *
 **/
public class Treasuresofthedeeptwtw implements TicketDataGenerator {

    /**
     * The PrizeParameters object. This is set by the setPrizeParameters method.
     */
    private PrizeParameters prizeParams = null;

    // metric to count generation loop iterations
    private int generateCount;

    private final static String VERSION = "b";

    // Total number of clicks in the game
    private final static int NUM_GAME_ROWS = 6;
    
    // Number of columns in each click result row
    private final static int NUM_GAME_COLS = 2;

    // Tier definitions indicating win status
    private final static double[][] TIER_PRIZES = {
        {}, // Filler instead of manipulating the tier number for zero starting arrays
        
        {8000.0, 8000.0},
        {500.0, 500.0},
        {100.0, 100},        
        {50.0, 50.0},
        {20.0, 20.0},
        {10.0, 10.0},
        {5.0, 5.0},
        {2.0, 2.0},
        {1.0, 1.0},
        {0.0}
    };

    private final static int TOP_PRIZE = 8000;
    private final static Double[] HIGH_PRIZES = {8000.0, 500.0, 100.0};
    private final static Double[] MEDIUM_PRIZES = { 50.0, 20.0, 10.0 };
    private final static Double[] LOW_PRIZES = { 5.0, 2.0, 1.0 };

    private final static Double[] ALL_PRIZES = { 1.0, 2.0, 5.0, 10.0, 20.0, 50.0, 100.0, 500.0, 8000.0 };  

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
        ArrayList<Double> prizes = new ArrayList<Double>(Arrays.asList(ALL_PRIZES));
        // <editor-fold defaultstate="expanded" desc="Build the ticket using a String Builder">
        xml.append("<?xml version='1.0' encoding='UTF-8' ?><ticket><outcome amount=\"");
        xml.append(formatDecimal(prizeParams.getAmount(tier).doubleValue()));
        xml.append("\" prizeTier=\"");
        xml.append(tier);
        xml.append("\" />");
        xml.append("<params wT=\"");
        xml.append(win);
        xml.append("\" v=\"");
        xml.append(VERSION);
        xml.append("\"/>\n");
        Double[] clickResult;
        for (int i = 0; i < NUM_GAME_ROWS; i++) {
            clickResult = this.gameRows.get(i);
            xml.append("<turn n=\"");
            xml.append(i);
            xml.append("\" p=\"");
            xml.append(clickResult[PRIZE_INDEX].intValue());
            xml.append("\" w=\"");            
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
        ArrayList<ArrayList<Double>> prizeTiers = new ArrayList<ArrayList<Double>>(); // This is an array of arrays, basically. It's going to hold a copy of all the Prize Tiers, so the application can pick and choose from them.
        ArrayList<Double> allPrizes = new ArrayList<Double>(Arrays.asList(ALL_PRIZES));
        Collections.shuffle(allPrizes);
        double[] tierData = TIER_PRIZES[tier];    
        Double[] row = null; // Reusable variable to store row data.
        int rand; // Used for generating random integers.
        int required = NUM_GAME_ROWS; // for manipulation of the number of game rows (incase of not winning)
        
        // Populate prizeTiers with the tiers.
        prizeTiers.add(new ArrayList<Double>(Arrays.asList(HIGH_PRIZES)));
        prizeTiers.add(new ArrayList<Double>(Arrays.asList(MEDIUM_PRIZES)));
        prizeTiers.add(new ArrayList<Double>(Arrays.asList(LOW_PRIZES)));
        
        
        if(isWinner) { // If the game is a winning game, add in three game rows of the winning tier.
            for(int i = 0; i < WINNING_COUNT; ++i)
                addGameRow(tierData[1], WINNER);
            for(ArrayList prizeTier : prizeTiers) { // Loop through all the prizeTiers and remove the winning tier.
                if(prizeTier.contains(tierData[1])) {
                    prizeTier.remove(tierData[1]);
                    break; // No need to have eronious loops.
                }
            }
            for(int i = 0; i < 3; ++i) { // Add 3 more prizes
                ArrayList prizeTier;
                Double prize;
                rand = randomBetween(0, 2);
                prizeTier = prizeTiers.get(rand); // Get a random tier.
                
                rand = randomBetween(0,prizeTier.size()-1); // Get a random prize within the tier.
                prize = (Double)prizeTier.get(rand);
                for(Double[] gameRow : this.gameRows) { // Check if the prize is already in the Game Rows. If it is, remove it from the Prize Tiers so it's not put in more than three times.
                    if(gameRow[0].doubleValue() == prize) {
                        prizeTiers.get(prizeTiers.indexOf(prizeTier)).remove(prize);
                        break; // No need to loop any more.
                    }
                }
                addGameRow(prize, LOSER);
                Collections.shuffle(this.gameRows); // Shuffle it.
            }
        } else { // If it's not a winner...
            for(ArrayList prizeTier : prizeTiers) { // Loop through all the prize tiers to ensure that each prize tier is represented in a losing ticket
                rand = randomBetween(0, prizeTier.size()-1);
                addGameRow((Double)prizeTier.get(rand),LOSER);
                prizeTier.remove(prizeTier.get(rand));
            }
            rand = randomBetween(1,100); // Determine randomly how many misses there are
            if(rand <= 25) { // 3 misses
                this.gameRows.addAll(gameRows); // Three misses? Just double all the values.
                Collections.shuffle(this.gameRows); 
            } else if(rand <= 50) { // 2 misses
                ArrayList<Double> prizeQueue = new ArrayList<Double>(); // Prizes to be added need to be queued up, so no three-ofs exist.
                
                for(int i = 0; i < 2; ++i) { // Add in two prizes that already exist in Game Row to fabricate two misses.
                    Collections.shuffle(this.gameRows); // Shuffle first, ensure randomness.
                    rand = randomBetween(0, this.gameRows.size()-1);
                    prizeQueue.add(this.gameRows.get(rand)[PRIZE_INDEX]); // Add the prize to the queue, then remove it from the Game Rows. This is to ensure that it won't get the same Game Row again.
                    this.gameRows.remove(this.gameRows.get(rand)); // remove from game rows so that we don't pick it again
                }
                for(Double prize : prizeQueue) {
                    for(int i = 0; i < 2; ++i) { // Add each prize twice as to create the illusion of near misses.
                        addGameRow(prize, LOSER);
                    }
                }                
                Collections.shuffle(this.gameRows);
                addGameRow(getRandomPrize(prizeTiers),LOSER); // Add one random row as the last go
            } else { // 1 miss
                
                // Add in one more random prize bringing total to 4
                    rand = randomBetween(0,2);
                    ArrayList prizeTier = prizeTiers.get(rand);
                    rand = randomBetween(0,prizeTier.size()-1);
                    addGameRow((Double)prizeTier.get(rand),LOSER);
                    prizeTiers.get(prizeTiers.indexOf(prizeTier)).remove(rand);
                
                // Now choose one of the populated prizes to add again to create the near miss
                Collections.shuffle(this.gameRows); // Shuffle, then add in one existing prize
                rand = randomBetween(0, this.gameRows.size() -1);
                addGameRow(this.gameRows.get(rand)[PRIZE_INDEX], LOSER);
                
                // Finally add in the last prize that is not a near miss
                addGameRow(getRandomPrize(prizeTiers),LOSER);
                
            }
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
     * Adds a row to the GameRows.
     * @param prize double
     * @param winner double
     */
    private void addGameRow(double prize, double winner) {
        Double[] row = new Double[NUM_GAME_COLS];
        row[PRIZE_INDEX] = prize;
        row[WINNER_INDEX] = winner;
        this.gameRows.add(row);
    }
    
    private Double getRandomPrize(ArrayList prizeTiers) {
        int rand = randomBetween(0,2);
        ArrayList prizeTier = (ArrayList)prizeTiers.get(rand); // Get a random tier.
        rand = randomBetween(0,prizeTier.size()-1);
        return (Double)prizeTier.get(rand);
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
