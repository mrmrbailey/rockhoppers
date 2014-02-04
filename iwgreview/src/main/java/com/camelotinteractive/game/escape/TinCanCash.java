package com.camelotinteractive.game.escape;

import com.camelotinteractive.game.instant.GameOutcome;
import com.camelotinteractive.game.instant.TicketDataException;
import com.camelotinteractive.game.instant.TicketDataGenerator;
import com.gtech.game.PrizeParameters;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

/**
 * Tin Can Cash ticket generation class.
 *
 *
 */
public class TinCanCash implements TicketDataGenerator {

    /**
     * The PrizeParameters object. This is set by the setPrizeParameters method.
     */
    private PrizeParameters prizeParams = null;
    private final static String VERSION = "a";
    // Total number of clicks in the game
    private final static int NUM_GAME_ROWS = 4;
    // Number of columns in each click result row
    private final static int NUM_GAME_COLS = 2;
    // Tier definitions indicating win status
    private final static double[][] TIER_PRIZES = {
        {}, // Filler instead of manipulating the tier number for zero starting arrays
        {4.0, 1000.0},
        {3.0, 100.0},
        {2.0, 20.0},
        {1.0, 2.0},
        {0.0, 0.5},
        {-1.0, 0.0}
    };
    private final static int[][] UNIQUE_FALLING_COMBINATIONS = { // The combinations that you can have when the cans fall over.
        {3, 2, 2, 1},
        {3, 3, 1, 1},
        {3, 3, 2, 0},
        {3, 2, 1, 1},
        {3, 3, 1, 0},
        {2, 2, 1, 1},
        {3, 3, 2, 1}
    };
    private final static Integer[] ALL_COLOURS = {0, 1, 2, 3, 4};
    private ArrayList<String[]> gameRows;	// final holder for click data
    private final static double WINNER = 1;
    private final static double LOSER = 0;
    private final static int WINNING_COUNT = 3;
    private final static int ARRAY_INDEX = 0;
    private final static int WINNER_INDEX = 1;
    private final static int FIRST_ELEMENT = 0; // Magic number for the first element of an array.
    private final static int[] ONE_NEAR_MISS = {50, 1};
    private final static int[] TWO_NEAR_MISS = {75, 2};
    private final static int[] THREE_NEAR_MISS = {100, 3};

    /**
     * **************************************************************************************
     * Interface Definition
     * **************************************************************************************
     */
    /**
     * getTicketData Returns actual XML ticket data for a particular game given
     * a GameOutcome instance Not sure what GameOutcome is yet as we do not have
     * the classes
     *
     * @param gameOutcome - a GameOutcome object
     * @throws TicketDataException
     * @return String
     *
     */
    public String getTicketData(GameOutcome gameOutcome) throws TicketDataException {

        int tier = gameOutcome.getTierNumber();

        // Check if the game is a winner
        String win = gameOutcome.isWinner() ? "1" : "0";

        // Generate the game data
        this.generateGame(tier, gameOutcome.isWinner());

        // Generate the ticket xml
        // This is actually quite an inefficient way of doing things, but it is the cleanest from a code
        // readablility and simplicity point of view.

        StringBuilder xml = new StringBuilder();
        // <editor-fold defaultstate="expanded" desc="Build the ticket using a String Builder">
        xml.append("<?xml version='1.0' encoding='UTF-8' ?>");
        xml.append("<ticket>");
        xml.append("\t<outcome amount=\"");
        xml.append(formatDecimal(prizeParams.getAmount(tier).floatValue()));
        xml.append("\" ");
        xml.append("prizeTier=\"");
        xml.append(tier);
        xml.append("\" />");
        xml.append("<params wT=\"");
        xml.append(win);
        xml.append("\" v=\"");
        xml.append(VERSION);
        xml.append("\"/>");
        String[] clickResult;
        for (int i = 0; i < NUM_GAME_ROWS; i++) {
            clickResult = this.gameRows.get(i);
            xml.append("<turn n=\"").append(i).append("\"");
            if (!clickResult[ARRAY_INDEX].isEmpty())
                xml.append(" t=\"").append(clickResult[ARRAY_INDEX]).append("\"");
            xml.append(" w=\"").append(clickResult[WINNER_INDEX]).append("\"/>");
        }
        xml.append("</ticket>");
        // </editor-fold>
        return xml.toString();
    }

    /**
     * setPrizeParameters A required data member setter for a PrizeParameters
     * object
     *
     * @param prizeParams - a PrizeParameters object
     *
     */
    public void setPrizeParameters(PrizeParameters prizeParams) {
        this.prizeParams = prizeParams;
    }

    /**
     * setCustomConfig A required method for the TicketDataGenerator interface.
     * Takes a Map object as its only parameter.
     *
     * @param map - a Map object of configuration items
     *
     */
    public void setCustomConfig(Map parm1) {
    }

    /**
     * **************************************************************************************
     * Ticket Generation
     * **************************************************************************************
     */
    /**
     * Primary method for the generation of game content. It uses the tier
     * information to determine the type of ticket to make and sets up the
     * internal data members ready for output of the ticket. The execution of
     * this method is limited by an iteration counter so that an infinite loop
     * is never fallen into.
     *
     * @throws TicketDataException
     *
     */
    private void generateGame(int tier, boolean isWinner) throws TicketDataException {
        this.gameRows = new ArrayList<String[]>(); // Iniitalize gameRows.
        ArrayList<Integer> canColours = new ArrayList<Integer>(); // This will hold all the cans (or colours) that will be put into the ticket.
        ArrayList<int[]> fallingCombinations = new ArrayList<int[]>(); // This will store the falling combinations. There are seven falling combinations across the ticket in the rules and this will hold the combinations for the amount of cans in the ticket.
        ArrayList<ArrayList<Integer>> fallingArrays = new ArrayList<ArrayList<Integer>>(); // This will hold arrays of cans for the final ticket. It is an extension of fallingCombinations in that fallingCombinations is used to determine the structure of fallingArrays.
        Integer winningCan = ((Double) TIER_PRIZES[tier][0]).intValue(); // The winning can. If it's -1, then there is no winning can.
        int[] fallingCombination; // Holds a random falling combination.
        int rand; // Used for holding random integers.
        
        // canColours needs to contain all the colours
        Collections.addAll(canColours, ALL_COLOURS);

        rand = randomBetween(0, 100); // Generate a number between 1 and 100.
        if (rand <= ONE_NEAR_MISS[0]) { // Determine the number of near misses randomly, based on the specifications given. 50% of times is one near miss, 25% of times is two near misses and the rest are 3 near misses.
            canColours = addNearMisses(canColours, ONE_NEAR_MISS[1]);
        } else if (rand <= TWO_NEAR_MISS[0]) {
            canColours = addNearMisses(canColours, TWO_NEAR_MISS[1]);
        } else if (rand <= THREE_NEAR_MISS[0]) {
            canColours = addNearMisses(canColours, THREE_NEAR_MISS[1]);
        }

        if (isWinner) { // Only run if the ticket is a winner. This section will add in the correct number of cans for a winning ticket.s
            int canRemove = WINNING_COUNT; // An integer that holds that number of cans that need to be removed from the can array to keep the same size when the winning cans are added.

            for (int i = canColours.size() - 1; i >= 0; --i) { // Loop through the array. If the can is the winning can, remove it and decrement canRemove.
                if (canColours.get(i) == winningCan) {
                    canColours.remove(i);
                    canRemove--;
                }
            }

            if (rand % 2 == 0) { // Half the time, the win will have one extra can. This means that a win can have 6-9 cans, while a loss can only ever have 6-8.
                canRemove--;
            }

            for (int i = 0; i < canRemove; i++) { // Since the can array no longer has any winning cans, we can safely remove a random can from the array to make room for the winning cans.
                rand = randomBetween(0, canColours.size() - 1);
                canColours.remove(rand);
            }

            for (int i = 0; i < WINNING_COUNT; i++) { // Finally, add in all the winning cans.
                canColours.add(winningCan);
            }
        }
        Collections.shuffle(canColours); // Shuffle the cans, so that they're in a random order.

        for (int[] combination : UNIQUE_FALLING_COMBINATIONS) { // For each combination of cans that can fall, determine which combinations fit the number of cans that are falling.
            int combinationTotal = 0; // Used to store the total number of cans in the combination.
            for (int num : combination) { // Loop through each throw of the combination, add the number of cans in that throw.
                combinationTotal += num;
            }
            if (combinationTotal == canColours.size()) { // If the number of cans in the combination is equal to the number of cans in the game, add it to the array that holds all combinations.
                fallingCombinations.add(combination);
            }
        }

        Collections.shuffle(fallingCombinations); // Shuffle up the combinations
        fallingCombination = fallingCombinations.get(FIRST_ELEMENT); // And take the first one as our combination.

        for (int fall : fallingCombination) { // For each throw, add the number of cans that need to be knocked over to a falling array.
            ArrayList<Integer> fallingArray = new ArrayList<Integer>(); // Make the fallingArray. It's reused over and over.
            for (int i = 0; i < fall; i++) { // Add the cans to the falling array. Randomize the cans, then get the first can. Shuffle again afterwards for more randomization.
                Collections.shuffle(canColours);
                fallingArray.add(canColours.get(FIRST_ELEMENT));
                canColours.remove(FIRST_ELEMENT);
                Collections.shuffle(canColours);
            }
            fallingArrays.add(fallingArray); // Finally, add the fallingArray as an array to the fallingArrays list.
        }
        Collections.shuffle(fallingArrays); // Shuffle up the arrays, so that you have a random distribution of ways that the cans get knocked over.
        int winningCount = 0; // Tracks the number of winning cans.
        for (ArrayList<Integer> fallingArray : fallingArrays) { // Loop through all the cans, create the string used for the ticket of cans that fall over.
            String arrayString = "";
            Collections.sort(fallingArray); // Sort the array, so that the array is in the proper order.
            for (Integer can : fallingArray) { // Loop through each can, if it's the winning can, add one to the winning count.
                if (can == winningCan) {
                    winningCount++;
                }
                if (!arrayString.isEmpty()) {
                    arrayString += ",";
                }
                arrayString += can;
            }

            if (winningCount >= WINNING_COUNT) { // If the winning count has been reached, then this ticket is a winner! Then reset the winning count.
                addGameRow(arrayString, WINNER);
                winningCount = 0;
            } else { // Else just add a loser game row.
                addGameRow(arrayString, LOSER);
            }
        }
    }

    /**
     * Adds in "near-misses" to the ArrayList.
     *
     * @param cans The array list of cans
     * @param nearMisses How many near misses to add
     *
     */
    private ArrayList<Integer> addNearMisses(ArrayList<Integer> cans, Integer nearMisses) {
        ArrayList<Integer> nearMissCans = new ArrayList<Integer>(); // Create a temporary ArrayList to store all the colours for adding into the cans array.
        Collections.addAll(nearMissCans, ALL_COLOURS); // Add in all the colours. 
        for (int i = 0; i < nearMisses; i++) { // For each near miss...
            Integer can = randomBetween(0, nearMissCans.size() - 1); // Select a random can to be added to cans
            cans.add(nearMissCans.get(can)); // Add the can to the cans array
            nearMissCans.remove(can); // Remove the can colour from the nearMissCans array, so it can't be picked again. This ensures that there are no wins generated in this function.
        }
        return cans; // Return the new array with near misses.
    }
    

    /**
     * Generate random number between (and inclusively) first and second
     * parameters
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
     *
     * @param prize double
     * @param winner double
     */
    private void addGameRow(String prize, Double winner) {
        String[] row = new String[NUM_GAME_COLS];
        row[ARRAY_INDEX] = prize;
        row[WINNER_INDEX] = ((Integer) winner.intValue()).toString();
        this.gameRows.add(row);
    }

    /**
     * formatDecimal Helper method to spit out correctly formated numbers
     *
     * @param bd - Number the number to format
     * @return String
     *
     */
    private String formatDecimal(Number bd) {
        String format = "#,##0.00#";
        if (bd.floatValue() >= 10000) {
            format = "#,###";
        }
        if (bd.floatValue() < 1.0) {
            format = "#.##";
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