/**
 * Title:		Rich For Life
 * Description: Camelot Instant Win Game
 * Company:		Piri Ltd.
 * author:		Dani Moure
 * email: 		dani@piriltd.com
 */

package com.camelotinteractive.game.piriinteractive;

import com.camelotinteractive.game.instant.GameOutcome;

import com.camelotinteractive.game.instant.TicketDataException;
import com.camelotinteractive.game.instant.TicketDataGenerator;
import com.gtech.game.PrizeParameters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;


public class Richforlifetwtw implements TicketDataGenerator {
	/**
	 * This class defines the class for generating ticket data for Millionaire Monopoly instant
	 * tickets.  It requires implementation of a method that generates ticket data
	 * in the form of a String based on outcome data. The return string format is
	 * defined the getTicketData() method. The class also has a method for setting
	 * the prize parameters.
	 *
	 */
    private PrizeParameters prizeParams = null;

    private static final Integer[] tierMap = {1, 2, 2, 3, 3, 4, 4, 4, 5, 5, 5, 5, 5, 6, 6, 6, 6, 6, 7, 7, 7, 7, 7, 8, 8, 8, 9, 9, 9, 10, 10, 11};
    private static final Integer[] gameTiers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
    
    private boolean gameWin;
    private int myGameTier;
    private Integer[] prizes;
    private Integer[] winTiers;
    private Integer[] winRows;
    private ArrayList<Integer> gamesLeft;
    
    private static final Integer[] games = {1, 2, 3, 4};
    private static final Integer[] gameRows = {4, 4, 3, 3};
    private static final int topTier = 1;
    private static final int lowTier = 11;
    
    private static final String[][] prizeLevels = {
    		{"1852949", "1852949"},
    		{"50000", "50000"},
    		{"5000", "5000"},
    		{"1000", "1000"},
    		{"200", "200"},
    		{"100", "100"},
    		{"40", "40"},
    		{"20", "20"},
    		{"15", "15"},
    		{"10", "10"},
    		{"5", "5"},
    		{"0", "0"}
    };    
    
     /**
     * Principal method used by GameProducer to generate ticket
     * @param  gameOutcome 	Winning ticket object instantiated by Camelot IGS
     * @return String	XML formatted ticket string to be read by Flash instance
     */
     public String getTicketData(GameOutcome gameOutcome)
     	throws com.camelotinteractive.game.instant.TicketDataException
     {
         if (gameOutcome == null)
         {
             throw new TicketDataException("Null GameOutcome object");
         }
         if (prizeParams == null)
         {
             throw new TicketDataException("Null PrizeParameters object");
         }

         String xml = "";
         
         winRows = new Integer[]{0, 0, 0, 0};
         prizes = new Integer[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
         gamesLeft = new ArrayList<Integer>();
         Collections.addAll(gamesLeft, games);
         
         xml = processOutcome(gameOutcome);
         
         return xml;
     }

     /**
      * Process the win outcome and returns the generated XML ticket
      * @param gameOutcome
      * @return String  XML formatted ticket string to be read by Flash instance
      */
     private String processOutcome(GameOutcome gameOutcome)
     {
    	 gameWin = gameOutcome.isWinner() ? true : false; 
    	 int gameTier = gameOutcome.getTierNumber();
    	 if (gameWin)
    		 myGameTier = tierMap[gameTier - 1];
    	 else
    		 myGameTier = gameTier;
    	 
    	 switch (gameTier)
    	 {
    	 	case 3:
    	 	case 5:
    	 	case 7:
    	 	case 8:
    	 	case 10:
    	 	case 11:
    	 	case 12:
    	 	case 13:
    	 	case 15:
    	 	case 16:
    	 	case 17:
    	 	case 18:
    	 	case 20:
    	 	case 21:
    	 	case 22:
    	 	case 23:
    	 	case 25:
    	 	case 26:
    	 	case 28:
    	 	case 29:
    	 	case 31:
    	 		processComboTier(gameTier);
    	 		break;
    	 	default:
    	 		processNormalTier(gameTier);
    	 }
    	 
    	 playGame1(winRows[0]);
    	 playGame2(winRows[1]);
    	 playGame3(winRows[2]);
    	 playGame4(winRows[3]);
    	 fillPrizeValues();
    	 
    	 return generateXml(gameOutcome);
     }
     
     /**
      * Processes a single win or losing outcome
      * @param gameTier The game tier
      */
     private void processNormalTier(int gameTier)
     {
    	 if (gameWin)
    	 {
	    	 // Single game win - Pick winning game
	    	 int winGame = gamesLeft.get(randomNumber(0, 3));
    		 
	    	 winRows[winGame - 1]++;
    	 
    		 winTiers = new Integer[]{gameTiers[tierMap[gameTier - 1] - 1]};
    	 }
    	 else
    	 {
    		 // Losing game
    		 winTiers = new Integer[]{};
    	 }
     }
     
     /**
      * Processes a "combo" tier (a tier made up of multiple winning turns)
      * @param gameTier The game tier
      */
     private void processComboTier(int gameTier)
     {
    	 int numWins = 0;
    	 
    	 switch (gameTier)
    	 {
    	 	case 3:
    	 		// �,000 x 10
    	 		numWins = 10;
    	 		winTiers = new Integer[]{3, 3, 3, 3, 3, 3, 3, 3, 3, 3};
    	 		break;
    	 	case 5:
    	 		// (�00 x 10) + (�000 x 3)
    	 		numWins = 13;
    	 		winTiers = new Integer[]{5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 4, 4, 4};
    	 		break;
    	 	case 7:
    	 		// �00 x 5
    	 		numWins = 5;
    	 		winTiers = new Integer[]{5, 5, 5, 5, 5};
    	 		break;
    	 	case 8:
    	 		// �00 x 10
    	 		numWins = 10;
    	 		winTiers = new Integer[]{6, 6, 6, 6, 6, 6, 6, 6, 6, 6};
    	 		break;
    	 	case 10:
    	 		// (� x 4) + (�0 x 6) + (�0 x 2) + (�0 x 2)
    	 		numWins = 14;
    	 		winTiers = new Integer[]{11, 11, 11, 11, 10, 10, 10, 10, 10, 10, 8, 8, 7, 7};
    	 		break;
    	 	case 11:
    	 		// �00 x 3
    	 		numWins = 2;
    	 		winTiers = new Integer[]{6, 6};
    	 		break;
    	 	case 12:
    	 		// �0 x 10
    	 		numWins = 10;
    	 		winTiers = new Integer[]{8, 8, 8, 8, 8, 8, 8, 8, 8, 8};
    	 		break;
    	 	case 13:
    	 		// (�0 x 10) + (�0 x 3) + �0
    	 		numWins = 14;
    	 		winTiers = new Integer[]{10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 8, 8, 8, 7};
    	 		break;
    	 	case 15:
    	 		// � + (�0 x 2) + �5 + �0 + �0
    	 		numWins = 6;
    	 		winTiers = new Integer[]{11, 10, 10, 9, 8, 7};
    	 		break;
    	 	case 16:
    	 		// �0 x 10
    	 		numWins = 10;
    	 		winTiers = new Integer[]{10, 10, 10, 10, 10, 10, 10, 10, 10, 10};
    	 		break;
    	 	case 17:
    	 		// �0 x 5
    	 		numWins = 5;
    	 		winTiers = new Integer[]{8, 8, 8, 8, 8};
    	 		break;
    	 	case 18:
    	 		// (� x 10) + (�0 x 3) + �0
    	 		numWins = 14;
    	 		winTiers = new Integer[]{11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 10, 10, 10, 8};
    	 		break;
    	 	case 20:
    	 		// � + �5 + �0
    	 		numWins = 3;
    	 		winTiers = new Integer[]{11, 9, 8};
    	 		break;
    	 	case 21:
    	 		// (� x 4) + (�0 x 2)
    	 		numWins = 6;
    	 		winTiers = new Integer[]{11, 11, 11, 11, 10, 10};
    	 		break;
    	 	case 22:
    	 		// �0 x 4
    	 		numWins = 4;
    	 		winTiers = new Integer[]{10, 10, 10, 10};
    	 		break;
    	 	case 23:
    	 		// � x 8
    	 		numWins = 8;
    	 		winTiers = new Integer[]{11, 11, 11, 11, 11, 11, 11, 11};
    	 		break;
    	 	case 25:
    	 		// �0 x 2
    	 		numWins = 2;
    	 		winTiers = new Integer[]{10, 10};
    	 		break;
    	 	case 26:
    	 		// � x 4
    	 		numWins = 4;
    	 		winTiers = new Integer[]{11, 11, 11, 11};
    	 		break;
    	 	case 28:
    	 		// � + 10
    	 		numWins = 2;
    	 		winTiers = new Integer[]{11, 10};
    	 		break;
    	 	case 29:
    	 		// � x 3
    	 		numWins = 3;
    	 		winTiers = new Integer[]{11, 11, 11};
    	 		break;
    	 	case 31:
    	 		// � x 2
    	 		numWins = 2;
    	 		winTiers = new Integer[]{11, 11};
    	 		break;
    	 }
    	 
    	 int randomIndex;
    	 int randomGame;
 	 	
    	 // Now work out which games win
    	 for (int i = 0; i < numWins; i++)
    	 {
    		 randomIndex = randomNumber(0, gamesLeft.size()-1);
    		 randomGame = gamesLeft.get(randomIndex);
    		 winRows[randomGame - 1]++;
    		 gamesLeft.remove(randomIndex);
    		 
    		 if (gamesLeft.size() == 0)
    			 recalcGamesLeft();
    	 }
     }
     
     /**
      * Recalculates which games have turns available for winning rows
      */
     private void recalcGamesLeft()
     {
    	 gamesLeft = new ArrayList<Integer>();
    	 
    	 for (int i = 0; i < games.length; i++)
    	 {
    		 if (winRows[i] < gameRows[i])
    		 {
    			 gamesLeft.add(i+1);
    		 }
    	 }
     }
     
     
     private static final Integer[] g1Yours = {12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30};
     private static final int g1TheirsMin = 10;
     private static final int g1TheirsMax = 28;
     private static final Integer[] g1YoursAlmost = {12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27};
     private int[][] g1GameSlots;
     
     /**
      * Plays the first game - your weight is more than theirs to win
      * @param winRows The number of winning rows for this game
      */
     private void playGame1(int winRows)
     {
    	 g1GameSlots = new int[4][3]; // Note the 1st column will denote whether it's a win or lose row
    	 
    	 ArrayList<Integer> g1RowsLeft = new ArrayList<Integer>();
    	 Collections.addAll(g1RowsLeft, new Integer[]{0, 1, 2, 3});
    	 
    	 ArrayList<Integer> g1YoursLeft = new ArrayList<Integer>();
    	 Collections.addAll(g1YoursLeft, g1Yours);
    	 ArrayList<Integer> possibleYoursAlmost = new ArrayList<Integer>();
    	 Collections.addAll(possibleYoursAlmost, g1YoursAlmost);
    	 ArrayList<Integer> g1TheirsUsed = new ArrayList<Integer>();
    	 ArrayList<Integer> possibleTheirs = new ArrayList<Integer>();
    	 
    	 int rowsLeft = 4;
    	 int yNumberIndex;
    	 int yNumber;
    	 int tNumberIndex;
    	 int tNumber;
    	 int rRowIndex;
    	 int rRow;
    	 
    	 int loseRows = rowsLeft - winRows;
    	 for (int row = 0; row < loseRows; row++)
    	 {
    		 // Pick "your" number
			 yNumberIndex = randomNumber(0, possibleYoursAlmost.size()-1);
			 yNumber = possibleYoursAlmost.get(yNumberIndex);
			 
			 // Work out possible "theirs" ("yours" no more than four less than "theirs")
			 for (int t = yNumber+1; t <= yNumber+4; t++)
			 {
				 // Make sure t is not greater than highest "theirs" value
				 if (t <= g1TheirsMax)
				 {
					 // If the value is not already used as "theirs", add it to possibilities
					 if (g1TheirsUsed.indexOf(t) == -1)
						 possibleTheirs.add(t);
				 }
			 }
			 
			 // Now pick "their" number from possibilities
			 tNumberIndex = randomNumber(0, possibleTheirs.size()-1);
			 tNumber = possibleTheirs.get(tNumberIndex);
			 
			 // Now remove your number and add theirs to used array
			 g1TheirsUsed.add(tNumber);
			 g1TheirsUsed.add(yNumber);
			 possibleYoursAlmost.remove(yNumberIndex);
			 if (possibleYoursAlmost.indexOf(tNumber) > -1)
				 possibleYoursAlmost.remove(possibleYoursAlmost.indexOf(tNumber));
			 
			 // Also remove the "yours" within 4 less than "theirs" and "yours" values within 2 of picked one
			 // to ensure we don't run out of values for theirs later and a more even spread of numbers
			 for (int i = tNumber-4; i < tNumber; i++)
			 {
				 if (possibleYoursAlmost.indexOf(i) > -1)
					 possibleYoursAlmost.remove(possibleYoursAlmost.indexOf(i));
			 }
			 for (int j = yNumber; j <= yNumber+2; j++)
			 {
				 if (g1YoursLeft.indexOf(j) > -1)
					 g1YoursLeft.remove(g1YoursLeft.indexOf(j));
			 }
			 
			 if (g1YoursLeft.indexOf(yNumber) > -1)
				 g1YoursLeft.remove(g1YoursLeft.indexOf(yNumber));
			 if (g1YoursLeft.indexOf(tNumber) > -1)
				 g1YoursLeft.remove(g1YoursLeft.indexOf(tNumber));
			 
			 // Pick a random row and insert the relevant numbers there
			 rRowIndex = randomNumber(0, g1RowsLeft.size()-1);
			 rRow = g1RowsLeft.get(rRowIndex);
			 g1GameSlots[rRow][0] = 0; // Denotes a lose
			 g1GameSlots[rRow][1] = yNumber;
			 g1GameSlots[rRow][2] = tNumber;
			 g1RowsLeft.remove(rRowIndex);
			 rowsLeft--;
			 
			 // Reset possible "theirs"
			 possibleTheirs = new ArrayList<Integer>();
    	 }
    	 
    	 if (winRows > 0)
    	 {
    		 // For each winning row, first pick your number then a lower their number
    		 for (int i = 0; i < winRows; i++)
    		 {
    			 // Pick your number
    			 yNumberIndex = randomNumber(0, g1YoursLeft.size()-1);
    			 yNumber = g1YoursLeft.get(yNumberIndex);
    			 
    			 int theirsMax;
    			 if (yNumber == 30) theirsMax = 28;
    			 else theirsMax = yNumber-1;
    			 
    			 // Work out possible "theirs"
    			 for (int t = g1TheirsMin; t <= theirsMax; t++)
    			 {
    				 // If the value is not already used as "theirs", add it to possibilities
    				 if (g1TheirsUsed.indexOf(t) == -1)
    				 	 possibleTheirs.add(t);
    			 }
    			 
    			 tNumberIndex = randomNumber(0, possibleTheirs.size()-1);
    			 tNumber = possibleTheirs.get(tNumberIndex);
    			 
    			 // Now remove "your" number and add "yours" and "theirs" to used array
    			 g1YoursLeft.remove(yNumberIndex);
    			 g1TheirsUsed.add(tNumber);
    			 g1TheirsUsed.add(yNumber);
    			 
    			 // Also remove any "yours" within 2 greater than picked "your" number, and "yours" within 2 greater than "theirs"
    			 // to ensure we don't run out of values for "theirs" later and more even spread of numbers
    			 for (int j = tNumber; j <= tNumber+2; j++)
    			 {
    				 if (g1YoursLeft.indexOf(j) > -1)
    					 g1YoursLeft.remove(g1YoursLeft.indexOf(j));
    			 }
    			 for (int j = yNumber; j <= yNumber+2; j++)
    			 {
    				 if (g1YoursLeft.indexOf(j) > -1)
    					 g1YoursLeft.remove(g1YoursLeft.indexOf(j));
    			 }
    			 
    			 // Pick a random row and insert the relevant numbers there
    			 rRowIndex = randomNumber(0, g1RowsLeft.size()-1);
    			 rRow = g1RowsLeft.get(rRowIndex);
    			 g1GameSlots[rRow][0] = 1; // Denotes a win
    			 g1GameSlots[rRow][1] = yNumber;
    			 g1GameSlots[rRow][2] = tNumber;
    			 g1RowsLeft.remove(rRowIndex);
    			 rowsLeft--;
    			 
    			 // Reset possible theirs
    			 possibleTheirs = new ArrayList<Integer>();
    		 }
    	 }
     }
     
     
     private static final String[] g2Symbols = {"y", "gb", "gm", "gr", "s", "b", "tc", "n", "w", "p", "hn", "bc"};
     private static final Integer[] g2SymbolNums = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
     private int[][] g2GameSlots;
     
     /**
      * Plays the second game - match 2 winning symbols
      * @param winRows The number of winning rows for this game
      */
     private void playGame2(int winRows)
     {
    	 g2GameSlots = new int[4][3]; // Note the 1st column will denote whether it's a win or lose row
    	 
    	 ArrayList<Integer> g2RowsLeft = new ArrayList<Integer>();
    	 Collections.addAll(g2RowsLeft, new Integer[]{0, 1, 2, 3});
    	 
    	 ArrayList<Integer> g2SymbolsLeft = new ArrayList<Integer>();
    	 Collections.addAll(g2SymbolsLeft, g2SymbolNums);
    	 
    	 int rowsLeft = 4;
    	 int rSymbolIndex;
    	 int rSymbol;
    	 int rRowIndex;
    	 int rRow;
    	 
    	 // Check if winning game
    	 if (winRows > 0)
    	 {
    		 // For each winning row, pick symbols
    		 for (int i = 0; i < winRows; i++)
    		 {
    			 // Pick a random symbol
    			 rSymbolIndex = randomNumber(0, g2SymbolsLeft.size()-1);
    			 rSymbol = g2SymbolsLeft.get(rSymbolIndex);
    			 
    			 // Pick a random row and insert the symbol there
    			 rRowIndex = randomNumber(0, g2RowsLeft.size()-1);
    			 rRow = g2RowsLeft.get(rRowIndex);
    			 g2GameSlots[rRow][0] = 1; // Denotes a win
    			 g2GameSlots[rRow][1] = rSymbol;
    			 g2GameSlots[rRow][2] = rSymbol;
    			 
    			 // Remove random symbol from selection
    			 g2SymbolsLeft.remove(rSymbolIndex);
    			 g2RowsLeft.remove(rRowIndex);
    			 rowsLeft--;
    		 }
    	 }
    	 
    	 // Now fill remaining symbols - go through g2GameSlots and replace any 0s
    	 for (int row = 0; row < g2GameSlots.length; row++)
    	 {
    		 for (int column = 1; column < 3; column++) // Note - ignore column 0 as that should remain "0" to denote losing rows
    		 {
    			 if (g2GameSlots[row][column] < 1)
    			 {
    				 // Pick random symbol, only use each once on lose to ensure maximum spread of symbols
    				 rSymbolIndex = randomNumber(0, g2SymbolsLeft.size()-1);
    	    		 rSymbol = g2SymbolsLeft.get(rSymbolIndex);
    	    		 g2GameSlots[row][column] = rSymbol;
    	    		 g2SymbolsLeft.remove(rSymbolIndex);
    			 }
    		 }
    	 }
     }
     
     
     private static final Integer[] g3Numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9};
     private static final Integer[] g3AlmostTotals1 = {8, 9};
     private static final Integer[] g3AlmostTotals2 = {11, 12};
     private static final int winTotal = 10;
     private int[][] g3GameSlots;
     
     /**
      * Plays the third game - Numbers add up to 10
      * @param winRows The number of winning rows for this game
      */
     private void playGame3(int winRows)
     {
    	 g3GameSlots = new int[3][4]; // Note the 1st column will denote whether it's a win or lose row
    	 
    	 ArrayList<Integer> g3RowsLeft = new ArrayList<Integer>();
    	 Collections.addAll(g3RowsLeft, new Integer[]{0, 1, 2});
    	 
    	 ArrayList<Integer> g3AlmostLeft = new ArrayList<Integer>();
    	 Collections.addAll(g3AlmostLeft, g3AlmostTotals1);
    	 ArrayList<Integer> g3NumbersLeft = new ArrayList<Integer>();
    	 Collections.addAll(g3NumbersLeft, g3Numbers);
    	 ArrayList<Integer> possibleSymbols;
    	 
    	 ArrayList<Integer> g3NumbersUsed = new ArrayList<Integer>();
    	 
    	 int rowsLeft = 3;
    	 int rNumberIndex;
    	 int rNumber;
    	 int sNumberIndex;
    	 int sNumber;
    	 int rRowIndex;
    	 int rRow;
    	 int rTotalIndex;
    	 int rTotal;
    	 
    	 // First decide which rows will win/lose
    	 if (winRows > 0)
    	 {
    		 for (int i = 0; i < winRows; i++)
    		 {
    			 rRowIndex = randomNumber(0, g3RowsLeft.size()-1);
    			 rRow = g3RowsLeft.get(rRowIndex);
    			 g3GameSlots[rRow][0] = 1; // Denotes a win
    			 g3RowsLeft.remove(rRowIndex);
    			 rowsLeft--;
    		 }
    	 }
    	 
    	 // Then pick potential "almost" (near) win rows
    	 int numAlmost = randomNumber(1, 2);
    	 for (int i = 0; i < numAlmost; i++)
		 {
    		 if (rowsLeft > 0)
    		 {
				 rRowIndex = randomNumber(0, g3RowsLeft.size()-1);
				 rRow = g3RowsLeft.get(rRowIndex);
				 g3GameSlots[rRow][0] = 2; // Denotes an almost win (temporarily)
				 g3RowsLeft.remove(rRowIndex);
				 rowsLeft--;
    		 }
		 }
    	 
    	 // Now go through each row and play
    	 for (int row = 0; row < g3GameSlots.length; row++)
    	 {
    		 boolean lastRow = (row == g3GameSlots.length-1) ? true : false;
    		 
    		 // Set the row totals for win rows and pick totals for almost win rows
    		 if (g3GameSlots[row][0] == 1)
    		 {
	    		 rTotal = winTotal;
    		 }
    		 else if (g3GameSlots[row][0] == 2)
    		 {
    			 rTotalIndex = randomNumber(0, g3AlmostLeft.size()-1);
    			 rTotal = g3AlmostLeft.get(rTotalIndex);
    			 g3AlmostLeft.remove(rTotalIndex);
    			 
    			 g3AlmostLeft = new ArrayList<Integer>();
    	    	 Collections.addAll(g3AlmostLeft, g3AlmostTotals2);
    		 }
    		 else
    		 {
    			 rTotal = 0;
    		 }
    		 
    		 possibleSymbols = new ArrayList<Integer>();
    		 
    		 if (rTotal > 0)
    		 {
    			 // For win rows and almost wins, create the array of possible values
    			 int rMin = 1;
        		 int rMax = 9;
        		 
        		 switch (rTotal) 
    			 {
	    			 case 12:
	    				 rMin = 3; // (12 - 3 = 9)
	    				 break;
	    			 case 11:
	    				 rMin = 2; // (11 - 2 = 9)
	    				 break;
	    			 case 9:
	    				 rMax = 8; // (9 - 8 = 1)
	    				 break;
	    			 case 8:
	    				 rMax = 7; // (8 - 7 = 1)
	    				 break;
    			 }
        		 
	    		 for (int num = rMin; num <= rMax; num++)
	    		 {
	    			 if (!g3NumbersUsed.contains(num))
	    			 {
	    				 // Need to make sure doubles not used if row total is 12 or 8
		    			 if (!(rTotal == 12 && num == 6) && !(rTotal == 8 && num == 4))
						 {
							 if (num >= rMin && num <= rMax)
								 possibleSymbols.add(num);
						 }
	    			 }
	    		 }
    		 }
    		 else
    		 {
    			 // For losing rows, use the g3NumbersLeft array which just has the values already used in a slot removed
    			 possibleSymbols = new ArrayList<Integer>(g3NumbersLeft);
    		 }
    		 
    		 // If it's the last row, we need to make sure the columns don't add up to 10
    		 if (lastRow)
    		 {
    			 if (possibleSymbols.size() < 3)
    			 {
    				 // There is a chance we may reach a situation where all the possible numbers would be removed
    				 // If this is the case, reset the possibleSymbols array and just remove 2 numbers already picked
    				 // (instead of all 4)
    				 possibleSymbols = new ArrayList<Integer>();
    				 Collections.addAll(possibleSymbols, g3Numbers);
    				 if (possibleSymbols.indexOf(g3GameSlots[row-2][1]) > -1)
        				 possibleSymbols.remove(possibleSymbols.indexOf(g3GameSlots[row-2][1]));
    				 if (possibleSymbols.indexOf(g3GameSlots[row-1][1]) > -1)
        				 possibleSymbols.remove(possibleSymbols.indexOf(g3GameSlots[row-1][1]));
    				 // For totals of 12 or 8 we also need to ensure no doubles picked
    				 if (rTotal == 12)
    					 if (possibleSymbols.indexOf(6) > -1) possibleSymbols.remove(possibleSymbols.indexOf(6));
    				 if (rTotal == 8)
    					 if (possibleSymbols.indexOf(4) > -1) possibleSymbols.remove(possibleSymbols.indexOf(4));
    			 }
    			 
    			 // Remove all the values that would potentially make the column add up to 10
    			 int c1 = winTotal - g3GameSlots[0][1] - g3GameSlots[1][1];
    			 int c2 = winTotal - g3GameSlots[0][2] - g3GameSlots[1][2];
    			 int c3 = rTotal - c2;
    			 if (possibleSymbols.indexOf(c1) > -1)
    				 possibleSymbols.remove(possibleSymbols.indexOf(c1));
    			 if (possibleSymbols.indexOf(c2) > -1)
    				 possibleSymbols.remove(possibleSymbols.indexOf(c2));
    			 if (possibleSymbols.indexOf(c3) > -1)
    				 possibleSymbols.remove(possibleSymbols.indexOf(c3));
    		 }
    		 
    		 // Pick a random number
			 rNumberIndex = randomNumber(0, possibleSymbols.size()-1);
			 rNumber = possibleSymbols.get(rNumberIndex);
			 
			 // Remove this picked number so will not pick a duplicate on a losing row
			 possibleSymbols.remove(rNumberIndex);
			 
			 if (rTotal > 0)
			 {
				 // If win row or almost win row, calculate the 2nd number
				 sNumber = rTotal - rNumber;
			 }
			 else
			 {
				 // If losing row, pick the 2nd number from possibilities
				 sNumberIndex = randomNumber(0, possibleSymbols.size()-1);
	    		 sNumber = possibleSymbols.get(sNumberIndex);
	    		 
	    		 // Make sure number picked does not make row add up to 10
	    		 if (rNumber + sNumber == winTotal)
	    			 rNumber = (rNumber < 9) ? rNumber+1 : rNumber-1;
			 }
			 
			 // Assign values
			 if (g3GameSlots[row][0] == 2)
				 g3GameSlots[row][0] = 0; // Set almost win row as losing row for later
			 
			 g3GameSlots[row][1] = rNumber;
			 g3GameSlots[row][2] = sNumber;
			 g3GameSlots[row][3] = rNumber + sNumber;
			 
			 // Remove picked numbers from numbers left, and add to numbers used
			 if (g3NumbersLeft.indexOf(rNumber) > -1)
				 g3NumbersLeft.remove(g3NumbersLeft.indexOf(rNumber));
			 if (g3NumbersLeft.indexOf(sNumber) > -1)
				 g3NumbersLeft.remove(g3NumbersLeft.indexOf(sNumber));
			 
			 g3NumbersUsed.add(rNumber);
			 g3NumbersUsed.add(sNumber);
    	 }
    	     	 
    	 // Check no column adds up to 10, there are a few instances where it could 
    	 if ((g3GameSlots[0][1] + g3GameSlots[1][1] + g3GameSlots[2][1] == winTotal) || 
    			 (g3GameSlots[0][2] + g3GameSlots[1][2] + g3GameSlots[2][2] == winTotal))
    	 {
    		 // Transpose the values in the last row, unless last row contains two 5s or first 2 columns
    		 // add up to same number in which case swap 1st row
    		 int val1 = g3GameSlots[2][1];
    		 int val2 = g3GameSlots[2][2];
    		 
    		 if (!(val1 == 5 && val2 == 5) && !((g3GameSlots[0][1] + g3GameSlots[1][1]) == (g3GameSlots[0][2] + g3GameSlots[1][2])))
    		 {
	    		 g3GameSlots[2][1] = val2;
	    		 g3GameSlots[2][2] = val1;
    		 }
    		 else
    		 {
    		 	val1 = g3GameSlots[0][1];
    		 	val2 = g3GameSlots[0][2];
    		 	g3GameSlots[0][1] = val2;
    		 	g3GameSlots[0][2] = val1;
    		 }
    	 }
     }
     
     
     private static final String[] g4Symbols = {"dr", "lt", "k", "c", "mb", "p", "j", "gw", "gc", "sc"};
     private static final Integer[] g4SymbolNums = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
     private int[][] g4GameSlots;
     
     /**
      * Plays the fourth game - match the winning symbol
      * @param win 1 if this game is a win, 0 if a lose
      */
     private void playGame4(int winRows)
     {
    	 g4GameSlots = new int[4][2]; // Note the 1st column will denote whether it's a win or lose row (1 or 0), 1st row is "your symbol"
    	 
    	 ArrayList<Integer> g4RowsLeft = new ArrayList<Integer>();
    	 Collections.addAll(g4RowsLeft, new Integer[]{1, 2, 3}); // Row 0 will always be winning row
    	 ArrayList<Integer> g4SymbolsLeft = new ArrayList<Integer>();
    	 Collections.addAll(g4SymbolsLeft, g4SymbolNums);
    	 
    	 int rSymbolIndex;
    	 int rSymbol;
    	 int rRowIndex;
    	 int rRow;
    	 
    	 // Pick winning symbol
    	 int winSymbolIndex = randomNumber(0, g4SymbolsLeft.size()-1);
    	 int winSymbol = g4SymbolsLeft.get(winSymbolIndex);
    	 g4GameSlots[0][0] = 2; // Denotes winning symbol
    	 g4GameSlots[0][1] = winSymbol;
    	 
    	 // Remove winning symbol from selection
		 g4SymbolsLeft.remove(winSymbolIndex);
    	 
		 // Check if winning game
    	 if (winRows > 0)
    	 {
    		 // For each winning row, insert winning symbol
    		 for (int i = 0; i < winRows; i++)
    		 {
    			 rRowIndex = randomNumber(0, g4RowsLeft.size()-1);
    			 rRow = g4RowsLeft.get(rRowIndex);
    			 
    			 g4GameSlots[rRow][0] = 1; // Denotes a win
    			 g4GameSlots[rRow][1] = winSymbol;
    			 
    			 g4RowsLeft.remove(rRowIndex);
    		 }
    	 }
    	 
    	 // Now fill remaining rows
    	 for (int row = 0; row < g4GameSlots.length; row++)
    	 {
    		 if (g4GameSlots[row][0] < 1) // If the first column is 0, this is a losing row
			 {
    			 rSymbolIndex = randomNumber(0, g4SymbolsLeft.size()-1);
    			 rSymbol = g4SymbolsLeft.get(rSymbolIndex);
    			 g4GameSlots[row][1] = rSymbol;
    			 
    			 // Remove the symbol so can't be picked again
    			 g4SymbolsLeft.remove(rSymbolIndex);
			 }
    	 }
     }
     
     
     /**
      * Generates the prize values for each row in the game (where it has not previously been set)
      */
     private void fillPrizeValues()
     {
    	 ArrayList<Integer> winIndex = new ArrayList<Integer>();
    	 ArrayList<Integer> winValues = new ArrayList<Integer>();
    	 Collections.addAll(winValues, winTiers);
    	 ArrayList<Integer> prizesLeft = new ArrayList<Integer>();
    	 Collections.addAll(prizesLeft, gameTiers);
    	 
    	 int turnsLeft = 14;
    	 
    	 // Loop through game slots to see which turns are winning ones
    	 // Game1
    	 for (int i = 0; i < g1GameSlots.length; i++)
    	 {
    		 if (g1GameSlots[i][0] == 1)
    		 {
    			 winIndex.add(i);
    		 }
    	 }
    	 // Game 2
    	 for (int i = 0; i < g2GameSlots.length; i++)
    	 {
    		 if (g2GameSlots[i][0] == 1)
    		 {
    			 winIndex.add(i+4);
    		 }
    	 }
    	 // Game 3
    	 for (int i = 0; i < g3GameSlots.length; i++)
    	 {
    		 if (g3GameSlots[i][0] == 1)
    		 {
    			 winIndex.add(i+8);
    		 }
    	 }
    	 for (int i = 1; i < g4GameSlots.length; i++) // Skip the 1st row in this one as it's the winning symbol
    	 {
    		 if (g4GameSlots[i][0] == 1)
    		 {
    			 winIndex.add(i+10);
    		 }
    	 }
    	 
    	 int rIndex1;
    	 int rValue1;
    	 int rIndex2;
    	 int rValue2;
    	 
    	 if (gameWin)
    	 {
    		 int wins = winIndex.size();
    		 // Assign random prizes to the winning indexes
	    	 for (int j = 0; j < wins; j++)
	    	 {
	    		 rIndex1 = randomNumber(0, winIndex.size()-1);
	    		 rValue1 = winIndex.get(rIndex1);
	    		 
	    		 rIndex2 = randomNumber(0, winValues.size()-1);
	    		 rValue2 = winValues.get(rIndex2);
	    		 
	    		 prizes[rValue1] = rValue2;
	    		 
	    		 winIndex.remove(rIndex1);
	    		 winValues.remove(rIndex2);
	    		 
	    		 // Remove prize values from possibilities
	    		 if (prizesLeft.indexOf(rValue2) > -1)
	    			 prizesLeft.remove(prizesLeft.indexOf(rValue2));
	    		 
	    		 turnsLeft--;
	    	 }
    	 }
    	 
    	 if (turnsLeft > 0)
    	 {
    		 // Now make sure top tier and stakeback prize appear
    		 if (myGameTier != topTier)
    		 {
    			 rIndex1 = randomNumber(0, prizes.length-1);
    			 prizeInsertAtOrNext(rIndex1, topTier, prizes.length);
    			 if (prizesLeft.indexOf(topTier) > -1)
        			 prizesLeft.remove(prizesLeft.indexOf(topTier));
    			 turnsLeft--;
    		 }
    		 if (myGameTier != lowTier)
    		 {
    			 rIndex2 = randomNumber(0, prizes.length-1);
    			 prizeInsertAtOrNext(rIndex2, lowTier, prizes.length);
    			 if (prizesLeft.indexOf(lowTier) > -1)
        			 prizesLeft.remove(prizesLeft.indexOf(lowTier));
    			 turnsLeft--;
    		 }
    	 }
    	 
    	 // If there are still prizes to fill, fill them
    	 if (turnsLeft > 0)
    	 {
    		 for (int i = 0; i < prizes.length; i++)
    		 {
    			 if (prizes[i] == 0)
    			 {
    				 // If all prizes are used once, reset the prizes so can use them again
    	        	 if (prizesLeft.size() == 0)
    	        		 Collections.addAll(prizesLeft, gameTiers);
    	        	 
    	        	 // Pick the prize
    				 rIndex1 = randomNumber(0, prizesLeft.size()-1);
    				 rValue1 = prizesLeft.get(rIndex1);
    				 
    				 prizes[i] = rValue1;
    				 prizesLeft.remove(rIndex1); // Remove this prize from possibilities
    			 }
    		 }
    	 }
     }
     
     /**
      * Inserts a value into the prizes array at index; if it is filled finds the next empty slot
      * @param index  The index at which to insert the value into the column
      * @param values  The value to be inserted
      * @param maxLength  The maximum index to check (before looping back to start of the prizes array)
      * @return
      */
     private int prizeInsertAtOrNext(int index, int value, int maxLength)
     {
    	if (prizes[index] == 0)
    	{
    		// Insert value at index
    		prizes[index] = value;
    		return index;
    	}
    	else
    	{
    		int count = 0;
    		
    		// Loop through array until find a new value
    		for (int i = index + 1; i <= maxLength; i++)
    		{
    			if (count <= 14)
    			{
	    			if (i >= maxLength)
					{
	    				// If we reach end of the array (or maxlength), loop back to the start
						i = 0;
					}
	    			if (prizes[i] == 0)
	    			{
	    				prizes[i] = value;
	    				return i;
	    			}
    			}
    			count++;
    		}
    		return -1;
    	}
     }
     
     /**
      * randomNumber method to generate a random integer between 2 values.
      *
      * @param min Minimum value of range, inclusive
      * @param max Maximum value of range, inclusive
      * @return A random integer
      */
     private int randomNumber(int low, int high) {
         return (int)(Math.floor(Math.random()*((high+1)-low))+low);
     }

     /**
      * generateTicket method to generate XML for Ticket
      *
      * @param gameOutcome Object containing information about ticket
      * @return string XML representing ticket
      */
     private String generateXml(GameOutcome gameOutcome)
     {
    	String win = gameOutcome.isWinner() ? "1" : "0";
    	int tier = gameOutcome.getTierNumber();

    	String amount;
    	amount = gameOutcome.isWinner() ? prizeLevels[gameTiers[tierMap[tier - 1] - 1] - 1][0] : "0";

     	StringBuilder xml = new StringBuilder();
     	xml.append("<?xml version='1.0' encoding='UTF-8' ?>");
     	xml.append("<ticket>");
     	xml.append("<outcome prizeTier=\"" + tier + "\" amount=\""
    			+ amount + ".00\" />");
     	xml.append("<params wT=\"" + win + "\" wAmount=\""
     			+ amount + ".00\" />");

     	// Game 1
     	for (int i = 0; i < g1GameSlots.length; i++)
     	{
     		xml.append("<turn name=\"go" + i + "\" g=\"1\" ");
     		xml.append("y=\"" + g1GameSlots[i][1] + "\" ");
     		xml.append("t=\"" + g1GameSlots[i][2] + "\" ");
     		xml.append("p=\"" + prizeLevels[prizes[i]-1][0] + "\" ");
     		xml.append("w=\"" + g1GameSlots[i][0] + "\"/>");
     	}
     	
     	// Game 2
     	for (int i = 0; i < g2GameSlots.length; i++)
     	{
     		xml.append("<turn name=\"go" + (i+4) + "\" g=\"2\" ");
     		xml.append("s1=\"" + g2Symbols[g2GameSlots[i][1]-1] + "\" ");
     		xml.append("s2=\"" + g2Symbols[g2GameSlots[i][2]-1] + "\" ");
     		xml.append("p=\"" + prizeLevels[prizes[i+4]-1][0] + "\" ");
     		xml.append("w=\"" + g2GameSlots[i][0] + "\"/>");
     	}
     	
     	// Game 3
     	for (int i = 0; i < g3GameSlots.length; i++)
     	{
     		xml.append("<turn name=\"go" + (i+8) + "\" g=\"3\" ");
     		xml.append("n1=\"" + g3GameSlots[i][1] + "\" ");
     		xml.append("n2=\"" + g3GameSlots[i][2] + "\" ");
     		xml.append("p=\"" + prizeLevels[prizes[i+8]-1][1] + "\" ");
     		xml.append("w=\"" + g3GameSlots[i][0] + "\"/>");
     	}
     	
     	// Game 4
     	xml.append("<turn name=\"go11\" g=\"4\" ");
 		xml.append("ws=\"" + g4Symbols[g4GameSlots[0][1]-1] + "\"/>");
 		
 		for (int i = 1; i < g4GameSlots.length; i++)
     	{
     		xml.append("<turn name=\"go" + (i+11) + "\" g=\"4\" ");
     		xml.append("s=\"" + g4Symbols[g4GameSlots[i][1]-1] + "\" ");
     		xml.append("p=\"" + prizeLevels[prizes[i+10]-1][1] + "\" "); // Note this array is one shorter than total goes
     		xml.append("w=\"" + g4GameSlots[i][0] + "\"/>");
     	}
     	
     	xml.append("</ticket>");

     	return xml.toString();
     }

    /**
     * The PrizeParameters provides access to prize definition for
     * a given game
     *
     * @param PrizeParameters Interface
     */
    public void setPrizeParameters(PrizeParameters parm1) {
        prizeParams = parm1;
    }

    /**
     * The setCustomConfig is a mechanism to providers additional
     * configuration parameters
     *
     * @param Map Map of configuration parameters.
     */
    public void setCustomConfig(Map parm1) {
        //not used
    }

}

