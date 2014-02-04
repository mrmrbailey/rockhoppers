package com.camelotinteractive.game.escape;

import com.camelotinteractive.game.instant.GameOutcome;
import com.camelotinteractive.game.instant.TicketDataException;
import com.camelotinteractive.game.instant.TicketDataGenerator;
import com.gtech.game.PrizeParameters;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;

/**
 * Tin Can Cash ticket generation class.
 *
 *
 */
public class Astrominer implements TicketDataGenerator {

    
    /**
     * The PrizeParameters object. This is set by the setPrizeParameters method.
     */
    private PrizeParameters prizeParams = null;
    private final static String VERSION = "a";
    // Total number of clicks in the game
    private final static int NUM_GAME_ROWS = 13;
    // Tier definitions indicating win status
    private final static int[][] TIER_PRIZES = {
        {}, // Filler instead of manipulating the tier number for zero starting arrays
        {5000,0,0},         // 1
        {500,1,0},          // 2
        {100,2,0},          // 3
        {40,3,0},           // 4
        {20,4,1},           // 5
        {10,5,0},           // 6
        {5,6,0},            // 7
        {2,7,1},            // 8
        {1,8,0},            // 9
        {0,9,0}             // 10
    };
    
    private final static int TIER_PRIZE_AMOUNT = 0;
    private final static int TIER_PRIZE_TOKEN = 1;
    private final static int TIER_PRIZE_INSTANT = 2;
    
    private final static String[] TOKEN_NAMES = {
        "6",
        "5",
        "4",
        "3",
        "IW20",
        "2",
        "1",
        "IW2",
        "0",
        "-1"
    };
    
    private final static int[][] WINNING_COMBINATIONS = {
        {3, 3, 2, 1, 1},
        {3, 3, 3, 1, 1},
        {3, 2, 2, 2, 1},
        {3, 3, 2, 2, 1},
        {3, 3, 3, 2, 1},
        {3, 3, 3, 3, 1},
        {2, 2, 2, 2, 2},
        {3, 2, 2, 2, 2},
        {3, 3, 2, 2, 2},
        {3, 3, 3, 2, 2},
        {3, 3, 3, 3, 2},
    };
    
    private final static int[][] LOSING_COMBINATIONS = {
        {3, 3, 1, 1, 1},
        {3, 2, 2, 1, 1},
        {3, 3, 2, 1, 1},
        {3, 3, 3, 1, 1},
        {2, 2, 2, 2, 1},
        {3, 2, 2, 2, 1},
        {3, 3, 2, 2, 1},
        {3, 3, 3, 2, 1},
        {3, 3, 3, 3, 1},
        {2, 2, 2, 2, 2},
        {3, 2, 2, 2, 2},
        {3, 3, 2, 2, 2},
        {3, 3, 3, 2, 2},
    };
   
    
    private final static Integer REQUIRED_FOR_WIN = 3;
    private final static Integer[] HIGH_PRIZES = {1};
    private final static Integer[] MEDIUM_PRIZES = {2,3,4,5};
    private final static Integer[] LOW_PRIZES = {6,7,8,9};  
    
    
    private final static int TURNS = 5;
    private final static double WINNER = 1;
    private final static double LOSER = 0;
    private final static int PRIZE_INDEX = 0;
    private final static int WINNER_INDEX = 1;
    
    private final static int FIRST_ELEMENT = 0; // Magic number for the first element of an array.
    
    private ArrayList<String[]> gameRows;
    
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
        double prizeAmount = TIER_PRIZES[tier][TIER_PRIZE_AMOUNT];
        this.generateGame(tier, gameOutcome.isWinner());
        
        
        // Check if the game is a winner
        String win = gameOutcome.isWinner() ? "1" : "0";

        // Generate the game data
        this.generateGame(tier, gameOutcome.isWinner());

        
        int[] token_totals = new int[10];
        for(String[] gameRow : gameRows) {
            String[] tokens = gameRow[PRIZE_INDEX].split(",");
            for(String token : tokens) {
                token_totals[Integer.parseInt(token)]++;
            }
        }
        
        // Generate the ticket xml
        StringBuilder xml = new StringBuilder();
        xml.append("<?xml version='1.0' encoding='UTF-8' ?>");
        xml.append("<ticket>");
        xml.append("<outcome");
        xml.append(" amount=\"").append(formatDecimal(prizeAmount)).append("\"");
        xml.append(" tier=\"").append(new Integer(tier).toString()).append("\"");
        xml.append(" isWinner=\"").append(win).append("\"");
        xml.append("/>");
        
        xml.append("<prizeList");
        xml.append(" a=\"");
        for(int i = 1; i < 10; i++) {
            xml.append((Integer)TIER_PRIZES[i][TIER_PRIZE_AMOUNT]);
            xml.append(".00");
            if(i < 9) xml.append(",");
        }
        xml.append("\"");
        xml.append("/>");
        
        xml.append("<params");
        xml.append(" tList=\"");
        for(int i = 0; i < token_totals.length; i++) {
            xml.append(token_totals[i]);
            if(i < token_totals.length-1) xml.append(",");
        }
        xml.append("\"");
        xml.append("/>");
        
        xml.append("<g1>");
        Integer go = 0;
        for(String[] gameRow : gameRows) {
            go++;
            xml.append("<turn");
            xml.append(" name=\"");
            xml.append("go");
            xml.append(go.toString());
            xml.append("\"");
            
            StringBuilder prizes = new StringBuilder();
            String[] str_tokens = gameRow[PRIZE_INDEX].split(",");
            for(int i = 0; i < str_tokens.length; i++) {
                prizes.append(
                        TOKEN_NAMES[Integer.parseInt(str_tokens[i])]
                );
                if(i < str_tokens.length-1) prizes.append(",");
            }
            xml.append(" t=\"").append(prizes.toString()).append("\"");
            
            xml.append(" prizeId=\"");
            String prizeId = gameRow[WINNER_INDEX].contentEquals("1") ? ((Integer)(tier-1)).toString() : "-1";
            xml.append(prizeId);
            xml.append("\"");
            
            xml.append(" w=\"").append(gameRow[WINNER_INDEX]).append("\"");
            xml.append("/>");
        }
        xml.append("</g1>");
        xml.append("</ticket>");
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
     * Primary method for the generation of game content. It uses the tier
     * information to determine the type of ticket to make and sets up the
     * internal data members ready for output of the ticket. The execution of
     * this method is limited by an iteration counter so that an infinite loop
     * is never fallen into.
     *
     * @param tier - An integer that defines the prize tier of the ticket
     * @param isWinner - A boolean whether the game is a winner or not
     * @throws TicketDataException
     *
     */
    private void generateGame(int tier, boolean isWinner) throws TicketDataException {
        this.gameRows = new ArrayList<String[]>();
        int[] prize_tier = TIER_PRIZES[tier];
        ArrayList<Integer> token_pool = new ArrayList<Integer>();
        int[] turn_structure = isWinner ? WINNING_COMBINATIONS[randomBetween(0,WINNING_COMBINATIONS.length-1)] : LOSING_COMBINATIONS[randomBetween(0,LOSING_COMBINATIONS.length-1)];
        int win_turn = isWinner ? randomBetween(3,5) : -1;
        
        // Create an array list that contains two of each token
        ArrayList<Integer> all_tokens = new ArrayList<Integer>();
        for(int i = 0; i < 2; i++) { // 
            for(int j = 1; j < TIER_PRIZES.length-1; j++) { 
                int[] token_row = TIER_PRIZES[j];
                if(isWinner) {
                    if(token_row == prize_tier) continue; 
                }
                if(token_row[TIER_PRIZE_INSTANT] == 0)
                    all_tokens.add(TIER_PRIZES[j][TIER_PRIZE_TOKEN]);
            }
        }
        
        int number_of_turns = 0;
        for(int turns : turn_structure) number_of_turns += turns;
        
        if(isWinner) {
            if(prize_tier[TIER_PRIZE_INSTANT] == 0) {
                for(int i = 0; i < number_of_turns - 3; i++) {
                    Integer token = all_tokens.get(randomBetween(0,all_tokens.size()-1));
                    all_tokens.remove(token);
                    token_pool.add(token);
                }
                for(int i = 0; i < 2; i++) token_pool.add(prize_tier[TIER_PRIZE_TOKEN]);
            } else {
                for(int i = 0; i < number_of_turns - 1; i++) {
                    Integer token = all_tokens.get(randomBetween(0,all_tokens.size()-1));
                    all_tokens.remove(token);
                    token_pool.add(token);
                }
            }
        } else { 
            for(int i = 0; i < number_of_turns; i++) {
                Integer token = all_tokens.get(randomBetween(0,all_tokens.size()-1));
                all_tokens.remove(token);
                token_pool.add(token);
            }
        }
        
        Collections.shuffle(token_pool);
        
        int turn = 0;
        int wins = 0;
        if(prize_tier[TIER_PRIZE_INSTANT] == 1) wins = 2;
        for(int tokens : turn_structure) {
            turn++;
            Integer winner = 0;
            ArrayList<Integer> row_tokens = new ArrayList<Integer>();
            for(int i = 0; i < tokens; i++) {
                Integer token;
                if(turn == win_turn) {
                    token = prize_tier[TIER_PRIZE_TOKEN];
                    win_turn = -1;
                } else {
                    Collections.shuffle(token_pool);
                    token = token_pool.get(0);
                    token_pool.remove(0);
                }
                if(token == prize_tier[TIER_PRIZE_TOKEN]) {
                    wins++;
                    if(wins == REQUIRED_FOR_WIN)
                        winner = 1;
                }
                row_tokens.add(token);
            }
            gameRows.add(constructGameRow(row_tokens, winner));
        }
    }

    private String[] constructGameRow(ArrayList<Integer> row_tokens, Integer winner) {
        String[] row = new String[2];
        StringBuilder tokens = new StringBuilder();
        Collections.sort(row_tokens);
        Iterator<Integer> it = row_tokens.iterator();
        while(it.hasNext()) {
            Integer token = it.next();
            tokens.append(token.toString());
            if(it.hasNext()) tokens.append(",");
        }
        row[PRIZE_INDEX] = tokens.toString();
        row[WINNER_INDEX] = winner.toString();
        return row;
    }
    private ArrayList<Integer> getTiers(Integer[] prizes, Integer count) {
        ArrayList<Integer> output = new ArrayList<Integer>();
        for(int i = FIRST_ELEMENT; i < count; i++) {
            int rand = randomBetween(0, prizes.length-1);
            output.add(prizes[rand]);
        }
        return output;
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
     * formatDecimal Helper method to spit out correctly formated numbers
     *
     * @param bd - Number the number to format
     * @return String
     *
     */
    private String formatDecimal(Number bd) {
        String format = "###0.00#";
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