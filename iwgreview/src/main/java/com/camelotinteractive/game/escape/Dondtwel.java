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
import java.math.BigDecimal;
import java.util.Iterator;

/**
 * Dondtwel ticket generation class.
 *
 * $Id$
 *
 **/
public class Dondtwel implements TicketDataGenerator {

    /**
     * The PrizeParameters object. This is set by the setPrizeParameters method.
     */
    private PrizeParameters prizeParams = null;

    // metric to count generation loop iterations
    private int generateCount;

    private final static String VERSION = "d";

    // Total number of clicks in the game
    private final static int PHASE_ONE_ROWS = 14;
    private final static int PHASE_TWO_ROWS = 8;
    

    // Tier definitions indicating win status
    private final static int[][] TIER_PRIZES = {
        {}, // Filler instead of manipulating the tier number for zero starting arrays
        {250000},
        {10000},
        {1000},
        {500},
        {200},
        {100},
        {40},
        {20},
        {10},
        {5},
        {4},
        {2},
        {0}
    };

    private final static Integer[] HIGH_PRIZES = { 250000, 10000, 1000 };
    private final static Integer[] MEDIUM_PRIZES = { 500, 200, 100, 40, 20 };
    private final static Integer[] LOW_PRIZES = { 10, 5, 4, 2 };

    private final static Integer[] ALL_PRIZES = { 2, 4, 5, 10, 20, 40, 100, 200, 500, 1000, 10000, 250000 };
    private final static Integer[] LEFT_PRIZES = {2, 4, 5, 10, 20, 40};
    private final static Integer[] RIGHT_PRIZES = {100, 200, 500, 1000, 10000, 250000};

    private ArrayList<Integer[]> gameOneRows;	// final holder for click data
    private ArrayList<Integer[]> gameTwoRows;	// final holder for click data
    private ArrayList<Integer> miniGameMatch; // Values for mini-game clicks
    private ArrayList<Integer> miniGameBoxes; // Values for boxes in mini-game
    
    private ArrayList<Integer[]> phaseOneBucketOne;
    private ArrayList<Integer[]> phaseOneBucketTwo;
    private ArrayList<Integer[]> phaseOneBucketThree;
    private ArrayList<Integer[]> phaseOneBucketFour;
    
    private ArrayList<Integer[]> phaseTwoBucketOne;
    private ArrayList<Integer[]> phaseTwoBucketTwo;
    private ArrayList<Integer[]> phaseTwoBucketThree;

    private final static int WINNER = 1;
    private final static int LOSER = 0;

    private final static int WINNER_INDEX = 1;
    private final static int PRIZE_INDEX = 0;


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
        xml.append("\" pArray=\"");
        ArrayList<Integer> prizes = new ArrayList<Integer>(Arrays.asList(ALL_PRIZES));
        for( Iterator<Integer> it = prizes.iterator() ; it.hasNext();) {
            xml.append(it.next());
            if (it.hasNext()) {
                xml.append(",");
            }
        }
        xml.append("\" v=\"");
        xml.append(VERSION);
        xml.append("\" mGm=\"");
        for(int i = 0; i < 3; i++) {
            xml.append(prizes.indexOf(this.miniGameMatch.get(i)));
            if (i != 2) {
                xml.append(",");
            }
        }
        xml.append("\" mGb=\"");
        for(int i = 0; i < 3; i++) {
            xml.append(prizes.indexOf(this.miniGameBoxes.get(i)));
            if (i != 2) {
                xml.append(",");
            }
        }
        xml.append("\"/><g1>");
        
        // Setup test for too many same colour in a row
        boolean low = false;
        Integer[] clickResult = this.gameOneRows.get(0);        
        if (prizes.indexOf(clickResult[PRIZE_INDEX]) < 6) {
            low = true;
        } else {
            low = false;
        }
                
        for (int i = 0; i < PHASE_ONE_ROWS; i++) {
            clickResult = this.gameOneRows.get(i);
            xml.append("<turn n=\"");
            xml.append(i);            
            xml.append("\" p=\"");
            xml.append(prizes.indexOf(clickResult[PRIZE_INDEX]));
            xml.append("\" w=\"");
            xml.append(clickResult[WINNER_INDEX]);
            xml.append("\"/>");
        }
        xml.append("</g1><g2>");        
        for (int i = 0; i < PHASE_TWO_ROWS; i++) {
            clickResult = this.gameTwoRows.get(i);
            xml.append("<turn n=\"");
            xml.append(i);
            xml.append("\" p=\"");
            xml.append(prizes.indexOf(clickResult[PRIZE_INDEX]));
            xml.append("\" w=\"");
            xml.append(clickResult[WINNER_INDEX]);
            xml.append("\"/>");
        }

        xml.append("</g2></ticket>");
        // </editor-fold>
        return xml.toString();
    }

    /**
     * Returns the number of tries used to generate a ticket if called after,
     * getTicketData has been called. This is used for debugging only.
     *
     * @return number of generations
     **/
    /*public int getGenerateCount() {
        return this.generateCount;
    }*/

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

        //this.debug = new StringBuilder();
        int[] tierData = TIER_PRIZES[tier];
        int prize = tierData[0];

        // Initialise the unique prize array. This array will have used entries removed
        // so that there are no duplicate losing prize amounts
        ArrayList<Integer> lowPrizes = new ArrayList<Integer>(Arrays.asList(LOW_PRIZES));
        ArrayList<Integer> mediumPrizes = new ArrayList<Integer>(Arrays.asList(MEDIUM_PRIZES));
        ArrayList<Integer> highPrizes = new ArrayList<Integer>(Arrays.asList(HIGH_PRIZES));

        Collections.shuffle(lowPrizes);
        Collections.shuffle(mediumPrizes);
        Collections.shuffle(highPrizes);

        // Minigame prize pool
        ArrayList<Integer> minigamePrizes = new ArrayList<Integer>();
        minigamePrizes.addAll(highPrizes);
        minigamePrizes.addAll(mediumPrizes);
        Collections.shuffle(minigamePrizes);

        // Build the pool of prizes to work with for left and right sides
        ArrayList<Integer> leftPrizes = new ArrayList<Integer>(Arrays.asList(LEFT_PRIZES));
        ArrayList<Integer> rightPrizes = new ArrayList<Integer>(Arrays.asList(RIGHT_PRIZES));
        Collections.shuffle(leftPrizes);
        Collections.shuffle(rightPrizes);
        ArrayList<Integer> unusedLeftPrizes = new ArrayList<Integer>();
        ArrayList<Integer> unusedRightPrizes = new ArrayList<Integer>();
        
        // Remove a few so that we are more likely to get near wins
        for(int i = 0; i < 2; i++) {
            unusedLeftPrizes.add(leftPrizes.remove(0));
            unusedRightPrizes.add(rightPrizes.remove(0));
        }

        // If this is a winner take out the winning prize as this will be placed
        // manually
        if (isWinner) {
            if (unusedLeftPrizes.contains(prize)) {                
                unusedLeftPrizes.remove(unusedLeftPrizes.indexOf(prize));
            }
            if (leftPrizes.contains(prize)) {
                leftPrizes.remove(leftPrizes.indexOf(prize));
            }
            if (unusedRightPrizes.contains(prize)) {
                unusedRightPrizes.remove(unusedRightPrizes.indexOf(prize));
            }
            if (rightPrizes.contains(prize)) {
                rightPrizes.remove(rightPrizes.indexOf(prize));
            }
        }

        // Now pad out the left and right prize arrays so that there are enough of them
        // to get a near win for each prize
        int size = leftPrizes.size();
        int tPrize = 0;
        for (int i = 0; i < size; i++) {
            tPrize = leftPrizes.get(i);
            leftPrizes.add(tPrize);
        }
        Collections.shuffle(leftPrizes);
        size = unusedLeftPrizes.size();
        for (int i = 0; i < size; i++) {
            tPrize = unusedLeftPrizes.get(i);
            unusedLeftPrizes.add(tPrize);
        }
        Collections.shuffle(unusedLeftPrizes);

        size = rightPrizes.size();
        for (int i = 0; i < size; i++) {
            tPrize = rightPrizes.get(i);
            rightPrizes.add(tPrize);
            rightPrizes.add(tPrize);
        }
        Collections.shuffle(rightPrizes);
        size = unusedRightPrizes.size();
        for (int i = 0; i < size; i++) {
            tPrize = unusedRightPrizes.get(i);
            unusedRightPrizes.add(tPrize);
            unusedRightPrizes.add(tPrize);
        }
        Collections.shuffle(unusedRightPrizes);
        
        ArrayList<Integer> allPrizes = new ArrayList<Integer>();

        // Initialise the bucket and click arrays
        this.phaseOneBucketOne = new ArrayList<Integer[]>();
        this.phaseOneBucketTwo = new ArrayList<Integer[]>();
        this.phaseOneBucketThree = new ArrayList<Integer[]>();
        this.phaseOneBucketFour = new ArrayList<Integer[]>();
        this.phaseTwoBucketOne = new ArrayList<Integer[]>();
        this.phaseTwoBucketTwo = new ArrayList<Integer[]>();
        this.phaseTwoBucketThree = new ArrayList<Integer[]>();
        this.gameOneRows = new ArrayList<Integer[]>();
        this.gameTwoRows = new ArrayList<Integer[]>();
        
        this.miniGameBoxes = new ArrayList<Integer>();
        this.miniGameMatch = new ArrayList<Integer>();
        
        int placedInPhaseOne = 0;
        int placedInPhaseTwo = 0;


        if (isWinner) {
            int placeInPhaseOne = 0;
            int placeInPhaseTwo = 0;
            // Work out how many winners to placeInPhaseOne in phase one
            if (prize > 40) {
                placeInPhaseOne = this.randomBetween(2, 3);
                placeInPhaseTwo = 4 - placeInPhaseOne; // 4 is the required win count for a red prize
                this.placeWinnerInPhaseTwo(prize, placeInPhaseTwo);
                placedInPhaseTwo += placeInPhaseTwo;
            } else {
                placeInPhaseOne = this.randomBetween(1, 2);
                placeInPhaseTwo = 3 - placeInPhaseOne; // 3 is the required win count for a blue prize
                this.placeWinnerInPhaseTwo(prize, placeInPhaseTwo);
                placedInPhaseTwo += placeInPhaseTwo;
            }
            for(int i = 0; i < placeInPhaseOne; i++) {
                this.placeWinnerInPhaseOne(prize);
                placedInPhaseOne++;
            }

            // Now just fill out the rest of the clicks            
            // Populate a losing ticket
            while(placedInPhaseOne < PHASE_ONE_ROWS) {
                if (placedInPhaseOne % 2 == 0) {                    
                    if (leftPrizes.size() > 0) {
                        this.placeInPhaseOne(leftPrizes.remove(0));
                        placedInPhaseOne++;
                    } else {
                        if (rightPrizes.size() > 0) {
                            this.placeInPhaseOne(rightPrizes.remove(0));
                            placedInPhaseOne++;
                        } else {
                            throw new TicketDataException("Run out of phase one prizes");
                        }
                    }
                } else {
                    if (rightPrizes.size() > 0) {
                        this.placeInPhaseOne(rightPrizes.remove(0));
                        placedInPhaseOne++;
                    } else {
                        if (leftPrizes.size() > 0) {
                            this.placeInPhaseOne(leftPrizes.remove(0));
                            placedInPhaseOne++;
                        } else {
                            throw new TicketDataException("Run out of phase one prizes");
                        }
                    }
                }
            }
            
            allPrizes.addAll(leftPrizes);
            allPrizes.addAll(rightPrizes);

            
            allPrizes.addAll(unusedLeftPrizes);
            allPrizes.addAll(unusedRightPrizes);
            Collections.shuffle(allPrizes);
            while (placedInPhaseTwo < PHASE_TWO_ROWS) {
                this.placeInPhaseTwo(allPrizes.remove(0));
                placedInPhaseTwo++;
            }

            // Sort out the mini game for the banker
            if (minigamePrizes.contains(prize)) {
                minigamePrizes.remove(minigamePrizes.indexOf(prize));
            }
            this.miniGameBoxes.add(prize);
            this.miniGameMatch.add(prize);
            if (!lowPrizes.contains(prize)) {
                this.miniGameBoxes.add(lowPrizes.remove(0));
                this.miniGameMatch.add(lowPrizes.remove(0));
                this.miniGameBoxes.add(minigamePrizes.remove(0));
                this.miniGameMatch.add(minigamePrizes.remove(0));
            } else {
                this.miniGameBoxes.add(minigamePrizes.remove(0));
                this.miniGameMatch.add(minigamePrizes.remove(0));
                this.miniGameBoxes.add(minigamePrizes.remove(0));
                this.miniGameMatch.add(minigamePrizes.remove(0));
            }

        } else {

            // Populate a losing ticket
            while(placedInPhaseOne < PHASE_ONE_ROWS) {
                if (placedInPhaseOne % 2 == 0) {
                    if (leftPrizes.size() > 0) {
                        this.placeInPhaseOne(leftPrizes.remove(0));
                        placedInPhaseOne++;
                    } else {
                        if (rightPrizes.size() > 0) {
                            this.placeInPhaseOne(rightPrizes.remove(0));
                            placedInPhaseOne++;
                        } else {
                            throw new TicketDataException("Run out of phase one prizes");
                        }
                    }
                } else {
                    if (rightPrizes.size() > 0) {
                        this.placeInPhaseOne(rightPrizes.remove(0));
                        placedInPhaseOne++;
                    } else {
                        if (leftPrizes.size() > 0) {
                            this.placeInPhaseOne(leftPrizes.remove(0));
                            placedInPhaseOne++;
                        } else {
                            throw new TicketDataException("Run out of phase one prizes");
                        }
                    }
                }
            }

            // add back the missing prizes

            allPrizes.addAll(leftPrizes);
            allPrizes.addAll(rightPrizes);
            allPrizes.addAll(unusedLeftPrizes);
            allPrizes.addAll(unusedRightPrizes);
            Collections.shuffle(allPrizes);
            while (placedInPhaseTwo < PHASE_TWO_ROWS) {
                this.placeInPhaseTwo(allPrizes.remove(0));
                placedInPhaseTwo++;
            }

            // Sort out the mini game for the banker
            this.miniGameBoxes.add(lowPrizes.remove(0));
            this.miniGameMatch.add(lowPrizes.remove(0));
            this.miniGameBoxes.add(minigamePrizes.remove(0));
            this.miniGameMatch.add(minigamePrizes.remove(0));
            this.miniGameBoxes.add(minigamePrizes.remove(0));
            this.miniGameMatch.add(minigamePrizes.remove(0));

        }

        // Shuffle the mini-game clicks
        Collections.shuffle(this.miniGameBoxes);
        
        // Now we need to build the buckets into the click arrays
        Collections.shuffle(this.phaseOneBucketOne);
        this.gameOneRows.addAll(this.phaseOneBucketOne);
        Collections.shuffle(this.phaseOneBucketTwo);
        this.gameOneRows.addAll(this.phaseOneBucketTwo);
        Collections.shuffle(this.phaseOneBucketThree);
        this.gameOneRows.addAll(this.phaseOneBucketThree);
        Collections.shuffle(this.phaseOneBucketFour);
        this.gameOneRows.addAll(this.phaseOneBucketFour);
        

        Collections.shuffle(this.phaseTwoBucketOne);
        this.gameTwoRows.addAll(this.phaseTwoBucketOne);
        Collections.shuffle(this.phaseTwoBucketTwo);
        this.gameTwoRows.addAll(this.phaseTwoBucketTwo);
        
        this.gameTwoRows.addAll(this.phaseTwoBucketThree); 
        
        if (this.gameOneRows.size() < PHASE_ONE_ROWS || this.gameTwoRows.size() < PHASE_TWO_ROWS) {
            throw new TicketDataException("Ticket has not been completely filled");
        }
        if (this.gameOneRows.size() > PHASE_ONE_ROWS || this.gameTwoRows.size() > PHASE_TWO_ROWS) {
            throw new TicketDataException("Ticket has been over filled");
        }
        
    }

    /**
     * Place a prize in a random bucket in phase one
     *
     * @param low int
     * @param high int
     * @return int
     */
    private void placeWinnerInPhaseOne(int prize) throws TicketDataException {
        int pos = this.randomBetween(1, 4);
        boolean placed = false;
        int count = 0;
        Integer[] row = new Integer[2];
        row[0] = prize;
        row[1] = LOSER;
        while (!placed) {
            switch(pos) {
                case 1:
                    // Try and place in bucket 1
                    if (this.phaseOneBucketOne.isEmpty()) {
                        this.phaseOneBucketOne.add(row);
                        placed = true;
                    } else {
                        pos++;
                    }
                    break;
                case 2:
                    // Try and place in bucket 2
                    if (this.phaseOneBucketTwo.isEmpty()) {
                        this.phaseOneBucketTwo.add(row);
                        placed = true;
                    } else {
                        pos++;
                    }
                    break;
                case 3:
                    // Try and place in bucket 3
                    if (this.phaseOneBucketThree.isEmpty()) {
                        this.phaseOneBucketThree.add(row);
                        placed = true;
                    } else {
                        pos++;
                    }
                    break;
                case 4:
                    // Try and place in bucket 4
                    if (this.phaseOneBucketFour.isEmpty()) {
                        this.phaseOneBucketFour.add(row);
                        placed = true;
                    } else {
                        pos = 1;
                    }
                    break;
            }
            count++;
            if (count > 5) {
                throw new TicketDataException("Unable to place prize in phase one");
            }
        }
    }

    /**
     * Place a prize in the first available bucket position
     *
     * @param low int
     * @param high int
     * @return int
     */
    private void placeInPhaseOne(int prize) throws TicketDataException {
        int pos = 1;
        boolean placed = false;
        int count = 0;
        Integer[] row = new Integer[2];
        row[0] = prize;
        row[1] = LOSER;
        while (!placed) {
            switch(pos) {
                case 1:
                    // Try and place in bucket 1
                    if (this.phaseOneBucketOne.size() < 4) {
                        this.phaseOneBucketOne.add(row);
                        placed = true;
                    } else {
                        pos++;
                    }
                    break;
                case 2:
                    // Try and place in bucket 2
                    if (this.phaseOneBucketTwo.size() < 4) {
                        this.phaseOneBucketTwo.add(row);
                        placed = true;
                    } else {
                        pos++;
                    }
                    break;
                case 3:
                    // Try and place in bucket 3
                    if (this.phaseOneBucketThree.size() < 4) {
                        this.phaseOneBucketThree.add(row);
                        placed = true;
                    } else {
                        pos++;
                    }
                    break;
                case 4:
                    // Try and place in bucket 4
                    if (this.phaseOneBucketFour.size() < 2) {
                        this.phaseOneBucketFour.add(row);
                        placed = true;
                    } else {
                        pos = 1;
                    }
                    break;
            }
            count++;
            if (count > 5) {
                /*System.out.println(Arrays.deepToString(this.phaseOneBucketOne.toArray()));
                System.out.println(Arrays.deepToString(this.phaseOneBucketTwo.toArray()));
                System.out.println(Arrays.deepToString(this.phaseOneBucketThree.toArray()));
                System.out.println(Arrays.deepToString(this.phaseOneBucketFour.toArray()));*/
                throw new TicketDataException("Unable to place prize in phase one");
            }
        }
    }

    /**
     * Place a prize in a random bucket in phase two
     *
     * @param low int
     * @param high int
     * @return int
     */
    private void placeInPhaseTwo(int prize) throws TicketDataException {
        int pos = this.randomBetween(1, 3);
        boolean placed = false;
        int count = 0;
        Integer[] row = new Integer[2];
        row[0] = prize;
        row[1] = LOSER;
        while (!placed) {
            switch(pos) {
                case 1:
                    // Try and place in bucket 1
                    if (this.phaseTwoBucketOne.size() < 3) {
                        this.phaseTwoBucketOne.add(row);
                        placed = true;
                    } else {
                        pos++;
                    }
                    break;
                case 2:
                    // Try and place in bucket 2
                    if (this.phaseTwoBucketTwo.size() < 3) {
                        this.phaseTwoBucketTwo.add(row);
                        placed = true;
                    } else {
                        pos++;
                    }
                    break;
                case 3:
                    // Try and place in bucket 3
                    if (this.phaseTwoBucketThree.size() < 2) {
                        this.phaseTwoBucketThree.add(row);
                        placed = true;
                    } else {
                        pos = 1;
                    }
                    break;
            }       
            count++;
            if (count > 4) {
                throw new TicketDataException("Unable to place prize in phase Two");
            }
        }
    }

    /**
     * Place a wining prize in a random bucket in phase two
     *
     * @param low int
     * @param high int
     * @return int
     */
    private void placeWinnerInPhaseTwo(int prize, int number) throws TicketDataException {
        Integer[] row = null;
        if (number > 1) {
            row = new Integer[2];
            row[0] = prize;        
            row[1] = LOSER;
                        
            int pos = this.randomBetween(1, 2);
            boolean placed = false;
            int count = 0;

            while (!placed) {
                switch(pos) {
                    case 1:
                        // Try and place in bucket 1
                        if (this.phaseTwoBucketOne.size() < 3) {
                            this.phaseTwoBucketOne.add(row);
                            placed = true;
                        } else {
                            pos++;
                        }
                        break;
                    case 2:
                        // Try and place in bucket 2
                        if (this.phaseTwoBucketTwo.size() < 3) {
                            this.phaseTwoBucketTwo.add(row);
                            placed = true;
                        } else {
                            pos++;
                        }
                        break;
                }       
                count++;
                if (count > 3) {
                    throw new TicketDataException("Unable to place prize in phase Two");
                }
            }
                        
            row = new Integer[2];
            row[0] = prize;
            row[1] = WINNER;
            this.phaseTwoBucketThree.add(row);
            
        } else {
            row = new Integer[2];
            row[0] = prize;
            row[1] = WINNER;
            this.phaseTwoBucketThree.add(row);
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
    private String formatDecimal(BigDecimal bd) {
        String format = bd.compareTo(new BigDecimal(1000)) < 0 ?"#,##0.00" : "#,##0"; 
        DecimalFormat df = new DecimalFormat(format);
        return df.format(bd);
    }

}
