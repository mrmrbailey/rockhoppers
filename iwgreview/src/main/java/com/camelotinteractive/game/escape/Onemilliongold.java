package com.camelotinteractive.game.escape;

import com.camelotinteractive.game.instant.GameOutcome;
import com.camelotinteractive.game.instant.TicketDataException;
import com.camelotinteractive.game.instant.TicketDataGenerator;
import com.gtech.game.PrizeParameters;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.*;

/**
 * Tin Can Cash ticket generation class.
 *
 *
 */
public class Onemilliongold implements TicketDataGenerator {

    
    /**
     * The PrizeParameters object. This is set by the setPrizeParameters method.
     */
    private PrizeParameters prizeParams = null;
    private final static String VERSION = "a";
    // Total number of clicks in the game
    private final static int NUM_GAME_ROWS = 13;
    // Tier definitions indicating win status
    private final static double[][] TIER_PRIZES = {
        {}, // Filler instead of manipulating the tier number for zero starting arrays
        {1000000, 0},                                               // 1
        {50000, 1},                                                 // 2
        {5000, 3, 3, 3, 3, 4, 4, 4, 4, 12, 5, 8, 8, 11, 11},        // 3
        {5000, 3, 3, 3, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4},              // 4
        {5000, 2},                                                  // 5
        {1000, 5, 5, 5, 5, 5, 5, 5, 6, 6, 6, 6, 6, 6},              // 6
        {1000, 4, 4, 5, 5, 5, 5, 6, 12, 7, 8, 8, 10, 10, 10},       // 7
        {1000, 3},                                                  // 8
        {200, 12, 7,10, 10, 10, 10, 10, 10,10,10,10,10,10,10},      // 9
        {200, 4},                                                   // 10
        {100, 8, 8, 10, 10, 10, 10, 10, 11, 11},                    // 11
        {100, 12, 10, 10, 10, 10,10},                               // 12
        {100, 5},                                                   // 13
        {50, 9, 9, 11, 11, 11, 11},                                 // 14
        {50, 10, 10, 10, 10, 10},                                   // 15
        {50, 12},                                                   // 16
        {50, 6},                                                    // 17
        {30, 11, 11, 11, 11, 11, 11},                               // 18
        {30, 10, 10, 10},                                           // 19
        {30, 8, 10},                                                // 20
        {30, 7},                                                    // 21
        {20, 11, 11, 11, 11},                                       // 22
        {20, 10, 10},                                               // 23
        {20, 8},                                                    // 24
        {15, 10, 11},                                               // 25
        {15, 9},                                                    // 26
        {10, 11, 11},                                               // 27
        {10, 10},                                                   // 28
        {5, 11},                                                    // 29
        {0}                                                         // 30
    };
    private final static Integer[] ALL_PRIZES = {
        1000000,    // 0
        50000,      // 1
        5000,       // 2
        1000,       // 3
        200,        // 4
        100,        // 5
        50,         // 6
        30,         // 7
        20,         // 8
        15,         // 9
        10,         // 10
        5,          // 11
        50          // 12 (This is Fast 50)
    }; 
    
    private final static Integer REQUIRED_PRIZE = 0;
    private final static Integer[] HIGH_PRIZES = {1,2,3};
    private final static Integer[] MEDIUM_PRIZES = {4,5,6,7,8,9};
    private final static Integer[] LOW_PRIZES = {10,11};  
    
    private final static int FAST_FIFTY = 12;
    
    private final static Integer[] TOKENS = {
        1, 
        2, 
        3, 
        4,
        5,
        6,
        7,
        8,
        9
    };
    
    private final static Integer[] GAME_LENGTHS = {0, 3, 3, 6, 1, 1};
    
    private ArrayList<String> games;	// final holder for click data
    private final static double WINNER = 1;
    private final static double LOSER = 0;
    private final static int PRIZE_INDEX = 0;
    private final static int WINNER_INDEX = 1;
    private final static int FIRST_ELEMENT = 0; // Magic number for the first element of an array.
    private final static int FIRST_NON_PRIZE_ELEMENT = 1; // Magic number that allows for skipping over the total prize amount in the PRIZE_TIER array and right into the prize indices. 
    
    
    private final static int HIGH_COUNT = 2;
    private final static int MEDIUM_COUNT = 9;
    private final static int LOW_COUNT = 3;
    
    private final static int GONE_THEIRS_LOWER = 1;
    private final static int GONE_YOURS_LOWER = 2; 
    private final static int GONE_THEIRS_UPPER = 18;
    private final static int GONE_YOURS_UPPER = 20;
    
    private final static int[][] GTWO_WINNING_COMBINATIONS = {{5,5},{6,4}};
    private final static int[][] GTWO_NEAR_WIN_COMBINATIONS = {{5,3},{6,2},{5,4},{6,3},{6,5}};
    private final static int[][] GTWO_LOSING_COMBINATIONS = {{1,6},{1,5},{1,4},{1,3},{1,2},{2,5},{2,4},{2,3},{3,4}};
    
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
        double prizeAmount = TIER_PRIZES[tier][0];
        this.generateGame(tier, gameOutcome.isWinner());
        
        // Check if the game is a winner
        String win = gameOutcome.isWinner() ? "1" : "0";

        // Generate the game data
        this.generateGame(tier, gameOutcome.isWinner());
        
        ArrayList<Integer> prizeArray = new ArrayList<Integer>(Arrays.asList(ALL_PRIZES));
        prizeArray.remove(FAST_FIFTY);

        // Generate the ticket xml

        StringBuilder xml = new StringBuilder();
        xml.append("<?xml version='1.0' encoding='UTF-8' ?>");
        xml.append("<ticket>");
        xml.append("<outcome");
        xml.append(" amount=\"").append(formatDecimal(prizeAmount)).append("\"");
        xml.append(" tier=\"").append(new Integer(tier).toString()).append("\"");
        xml.append("/>");
        xml.append("<params");
        xml.append(" wT=\"").append(win).append("\"");
        xml.append(" pList=\"");
        for(double prizeTier : prizeArray) {
            xml.append((new Double(prizeTier).intValue()));
            if(prizeArray.indexOf(new Double(prizeTier).intValue()) != prizeArray.size()-1) {
                xml.append(",");
            }
        }
        xml.append("\"");
        xml.append("/>");
        for(String game : this.games)
            xml.append(game);
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
        // Initialize variables
        this.games = new ArrayList<String>(); // Initalize gameRow, holds strings of each gamme. 
        boolean gThreeTokenSwitch = false;
        
        // Create a prize pool at random
        ArrayList<Integer> prizePool = new ArrayList<Integer>();
        prizePool.addAll(getTiers(HIGH_PRIZES, HIGH_COUNT));
        prizePool.addAll(getTiers(MEDIUM_PRIZES, MEDIUM_COUNT));
        prizePool.addAll(getTiers(LOW_PRIZES, LOW_COUNT));
        
        // Grab tokens to be used as prize tokens
        ArrayList<Integer> tokenList = new ArrayList<Integer>();
        tokenList.addAll(Arrays.asList(TOKENS));
        
        Collections.shuffle(tokenList);
        Integer diamondOne, diamondTwo;
        diamondOne = tokenList.get(0);
        tokenList.remove(0);
        diamondTwo = tokenList.get(0);
        tokenList.remove(0);
        Collections.shuffle(tokenList);
        
        int gOneNearWins = randomBetween(2,3);
        ArrayList<Integer> yourTokens = new ArrayList<Integer>();
        ArrayList<Integer> theirTokens = new ArrayList<Integer>();
        ArrayList<Integer> theirWinningTokens = new ArrayList<Integer>();
        
        for(int i = GONE_THEIRS_LOWER; i <= GONE_THEIRS_UPPER; i++) theirTokens.add((Integer)i);
        for(int i = GONE_YOURS_LOWER; i <= GONE_YOURS_UPPER; i++) yourTokens.add((Integer)i);
        for(int i = GONE_YOURS_LOWER+3; i <= GONE_THEIRS_UPPER; i++) theirWinningTokens.add((Integer)i);

        int gTwoNearWins = randomBetween(2,3);
        ArrayList<int[]> winningCombinations = new ArrayList<int[]>();
        ArrayList<int[]> nearWinCombinations = new ArrayList<int[]>();
        ArrayList<int[]> losingCombinations = new ArrayList<int[]>();
        winningCombinations.addAll( Arrays.asList(GTWO_WINNING_COMBINATIONS));
        nearWinCombinations.addAll(Arrays.asList(GTWO_NEAR_WIN_COMBINATIONS));
        losingCombinations.addAll(   Arrays.asList(GTWO_LOSING_COMBINATIONS));
        
        
        // Create the games that will store the turns.
        StringBuilder gOne =    new StringBuilder("<g1>");
        StringBuilder gTwo =    new StringBuilder("<g2>");
        StringBuilder gThree =  new StringBuilder("<g3" ).append(" s0=\"").append(diamondOne.toString()).append("\"").append(" s1=\"").append(diamondTwo.toString()).append("\"").append(">");
        StringBuilder gFour =   new StringBuilder("<g4>");
        StringBuilder gFive =   new StringBuilder("<g5>");
        
        // and make a counter for them to tick up.
        Integer gOneLength = 0;
        Integer gTwoLength = 0;
        Integer gThreeLength = 0;
        Integer gFourLength = 0;
        Integer gFiveLength = 0;
        
        // Get Tier rows
        int addOne = 0;
        boolean addedRequired = false;
        double[] tierRow = TIER_PRIZES[tier];
        ArrayList<Integer[]> prizes = new ArrayList<Integer[]>();
        for(int i = FIRST_NON_PRIZE_ELEMENT; i < tierRow.length; i++) {
            Integer[] prize = {(new Double(tierRow[i]).intValue()),1};
            if(prize[PRIZE_INDEX] == FAST_FIFTY) addOne = 1;
            prizes.add(prize);
        }
        if(prizes.size() < (NUM_GAME_ROWS+addOne)) {
            if(!addedRequired) { 
                Integer[] row = {REQUIRED_PRIZE, 0};
                prizes.add(row);
            }
            int loops = (NUM_GAME_ROWS-prizes.size()+addOne);
            for(int i = FIRST_ELEMENT; i < loops; i++) {
                int rand = randomBetween(0,prizePool.size()-1);
                Integer[] row = {prizePool.get(rand), 0};
                prizePool.remove(rand);
                prizes.add(row);
            }
        }
        
        Collections.shuffle(prizes); 
        
        // First, look through the array for FAST FIFTY. If it's there, add a win to the gameFour, else add a loss to gameFour. Remove afterwards.
        
        for(int i = FIRST_ELEMENT; i < prizes.size(); i++) {
            Integer[] prize = prizes.get(i);
            if(prize[PRIZE_INDEX] == FAST_FIFTY) {
                prize[PRIZE_INDEX] = 5;
                gFourLength += 1;
                gFour.append("<go");
                gFour.append(addAttribute("p",prize[PRIZE_INDEX].toString()));
                gFour.append(addAttribute("w",prize[WINNER_INDEX].toString()));
                gFour.append(" />");
                prizes.remove(prize);
            }
        }
        
        if(!isFull(4, gFourLength)) {
            int prizeNum = randomBetween(1,9);
            if(prizeNum == 5) {
                int rand  = randomBetween(1,100);
                prizeNum = (rand < 50 ? prizeNum - randomBetween(1,4) : prizeNum + randomBetween(1,4));
            }
            
            Integer[] prize = {prizeNum,0};
            gFourLength += 1;
            gFour.append("<go");
            gFour.append(addAttribute("p",prize[PRIZE_INDEX].toString()));
            gFour.append(addAttribute("w",prize[WINNER_INDEX].toString()));
            gFour.append(" />");
        }
        
        for(int i = FIRST_ELEMENT; i < prizes.size(); i++) {
           Integer[] prize = prizes.get(i);
           if(!isFull(1, gOneLength)) {
               boolean isNearWin = gOneNearWins > 0;
               Integer theirs, yours;
               int token;
               ArrayList<Integer> greaterThanTokens = new ArrayList<Integer>();
               ArrayList<Integer> lessThanTokens = new ArrayList<Integer>();
               if(prize[WINNER_INDEX] == WINNER) {
                   for(int j = 1; j < yourTokens.size(); j++)
                       if(yourTokens.get(j) > theirTokens.get(FIRST_ELEMENT))
                           greaterThanTokens.add(yourTokens.get(j));
                   
                  token = randomBetween(FIRST_ELEMENT, greaterThanTokens.size()-1); 
                  yours = greaterThanTokens.get(token);
                  for(int j = FIRST_ELEMENT; j < theirTokens.size(); j++) {
                      if(theirTokens.get(j) < yours) {
                          lessThanTokens.add(theirTokens.get(j));
                      } else break;
                  }
                  token = randomBetween(FIRST_ELEMENT, lessThanTokens.size()-1);
                  theirs = lessThanTokens.get(token);
                  
                  yourTokens.remove(yours);
                  theirTokens.remove(theirs);
                  theirWinningTokens.remove(theirs);
               } else {
                  token = randomBetween(FIRST_ELEMENT, theirWinningTokens.size()-1);
                  theirs = theirWinningTokens.get(token);
                  for(int j = FIRST_ELEMENT; j < yourTokens.size(); j++) {
                      if(yourTokens.get(j) < theirs) {
                          if(isNearWin) {
                              if(yourTokens.get(j) > (theirs-4)) {
                                  lessThanTokens.add(yourTokens.get(j));
                              } else continue;
                          } else {
                              lessThanTokens.add(yourTokens.get(j));
                          }
                      } else break;
                  }
                  token = randomBetween(FIRST_ELEMENT, lessThanTokens.size()-1);
                  yours = lessThanTokens.get(token);
                  
                  yourTokens.remove(yours);
                  theirTokens.remove(theirs);
                  theirWinningTokens.remove(theirs);
                  if(isNearWin) gOneNearWins--;
               }
               gOneLength += 1;
               gOne.append("<go");
               gOne.append(addAttribute("y",yours.toString()));
               gOne.append(addAttribute("t",theirs.toString()));
               gOne.append(addAttribute("p",prize[PRIZE_INDEX].toString()));
               gOne.append(addAttribute("w",prize[WINNER_INDEX].toString()));
               gOne.append(" />");
               continue;
           }
           if(!isFull(2, gTwoLength)) {
               Integer bZero, bOne;
               int[] row;
               boolean first = randomBetween(1,100) > 50 ? true : false;
               if(prize[WINNER_INDEX] == WINNER) {
                   Collections.shuffle(winningCombinations);
                   row = winningCombinations.get(0);
                   if(first) {
                       bZero = row[0];
                       bOne = row[1];
                   } else {
                       bZero = row[1];
                       bOne = row[0];
                   }
               } else { 
                   if(gTwoNearWins > 0) {
                       Collections.shuffle(nearWinCombinations);
                       row = nearWinCombinations.get(0);
                       if(first) {
                           bZero = row[0];
                           bOne = row[1];
                       } else {
                           bZero = row[1];
                           bOne = row[0];
                       }
                       nearWinCombinations.remove(0);
                   } else {
                       Collections.shuffle(losingCombinations);
                       row = losingCombinations.get(0);
                       if(first) {
                           bZero = row[0];
                           bOne = row[1];
                       } else {
                           bZero = row[1];
                           bOne = row[0];
                       }
                       losingCombinations.remove(0);
                   }
                   gTwoNearWins--;
               }
               
               gTwoLength += 1;
               gTwo.append("<go");
               gTwo.append(addAttribute("b0",bZero.toString()));
               gTwo.append(addAttribute("b1",bOne.toString()));
               gTwo.append(addAttribute("t",new Integer(bZero+bOne).toString()));
               gTwo.append(addAttribute("p",prize[PRIZE_INDEX].toString()));
               gTwo.append(addAttribute("w",prize[WINNER_INDEX].toString()));
               gTwo.append(" />");
               continue;
           }
           if(!isFull(3, gThreeLength)) {
               Integer token;
               if(prize[WINNER_INDEX] == WINNER) {
                   int rand = randomBetween(1,100);
                   if(rand < 50 || gThreeTokenSwitch) {
                       gThreeTokenSwitch = false;
                       token = diamondOne;
                   } else {
                       gThreeTokenSwitch = true;
                       token = diamondTwo;
                   }
               } else {
                   Collections.shuffle(tokenList);
                   token = tokenList.get(0);
                   tokenList.remove(0);
               }
               
               gThreeLength += 1;
               gThree.append("<go");
               gThree.append(addAttribute("s",token.toString()));
               gThree.append(addAttribute("p",prize[PRIZE_INDEX].toString()));
               gThree.append(addAttribute("w",prize[WINNER_INDEX].toString()));
               gThree.append(" />");
               continue;
           }
           if(!isFull(5, gFiveLength)) {
               Collections.shuffle(tokenList);
               
               gFiveLength += 1;
               gFive.append("<go");
               gFive.append(addAttribute("n",gFiveLength.toString()));
               if(prize[WINNER_INDEX] == WINNER)
                   gFive.append(addAttribute("t","10"));
               else
                   gFive.append(addAttribute("t", tokenList.get(0).toString()));
               gFive.append(addAttribute("p",prize[PRIZE_INDEX].toString()));
               gFive.append(addAttribute("w",prize[WINNER_INDEX].toString()));
               gFive.append(" />");
               continue;
           }
       }
        
        // Add all Games to the games array
        
          gOne.append("</g1>");
          gTwo.append("</g2>");
        gThree.append("</g3>");
         gFour.append("</g4>");
         gFive.append("</g5>");
        
        this.games.add(gOne.toString());
        this.games.add(gTwo.toString());
        this.games.add(gThree.toString());
        this.games.add(gFour.toString());
        this.games.add(gFive.toString());
    }

    private ArrayList<Integer> getTiers(Integer[] prizes, Integer count) {
        ArrayList<Integer> output = new ArrayList<Integer>();
        for(int i = FIRST_ELEMENT; i < count; i++) {
            int rand = randomBetween(0, prizes.length-1);
            output.add(prizes[rand]);
        }
        return output;
    }
    
    private String addAttribute(String name, String value) {
        StringBuilder string = new StringBuilder(" ");
        string.append(name);
        string.append("=\"");
        string.append(value);
        string.append("\"");
        return string.toString();
    }
    
    private boolean isFull(Integer index, Integer length) {
        return !(GAME_LENGTHS[index] > length);
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