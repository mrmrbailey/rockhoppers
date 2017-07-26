/**
 * Title:		Millionaire Monopolymillionaire
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


public class Monopolymillionaire implements TicketDataGenerator {
	/**
	 * This class defines the class for generating ticket data for Millionaire Monopolymillionaire instant
	 * tickets.  It requires implementation of a method that generates ticket data
	 * in the form of a String based on outcome data. The return string format is
	 * defined the getTicketData() method. The class also has a method for setting
	 * the prize parameters.
	 *
	 */
    private PrizeParameters prizeParams = null;

    private static final Integer[] tierMap = {1, 2, 2, 2, 3, 3, 4, 4, 4, 4, 5, 5, 5, 6, 6, 6, 6, 7, 7, 7, 7, 8, 8, 8, 8, 9, 9, 9, 10, 10, 10, 11, 11, 11, 12, 12, 13};
    private static final Integer[] gameTiers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};
    
    private boolean gameWin;
    private int myGameTier;
    private Integer[] prizes;
    private Integer[] winTiers;
    private Integer[] winRows;
    private ArrayList<Integer> gamesLeft;
    
    private static final Integer[] games = {1, 2, 3, 4};
    private static final Integer[] gameRows = {6, 3, 5, 1};
    private static final int topTier = 1;
    private static final int lowTier = 13;
    
    private static final String[][] prizeLevels = {
    		{"1000000", "1MIL"},
    		{"50000", "50K"},
    		{"10000", "10K"},
    		{"5000", "5K"},
    		{"1000", "1K"},
    		{"200", "200"},
    		{"100", "100"},
    		{"50", "50"},
    		{"30", "30"},
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
         prizes = new Integer[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
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
    	 	case 2:
    	 	case 3:
    	 	case 5:
    	 	case 7:
    	 	case 8:
    	 	case 9:
    	 	case 11:
    	 	case 12:
    	 	case 14:
    	 	case 15:
    	 	case 16:
    	 	case 18:
    	 	case 19:
    	 	case 20:
    	 	case 22:
    	 	case 23:
    	 	case 24:
    	 	case 26:
    	 	case 27:
    	 	case 29:
    	 	case 30:
    	 	case 32:
    	 	case 33:
    	 	case 35:
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
    	 	case 2:
    	 		// �0,000 x 5
    	 		numWins = 5;
    	 		winTiers = new Integer[]{3, 3, 3, 3, 3};
    	 		break;
    	 	case 3:
    	 		// �,000 x 10
    	 		numWins = 10;
    	 		winTiers = new Integer[]{4, 4, 4, 4, 4, 4, 4, 4, 4, 4};
    	 		break;
    	 	case 5:
    	 		// �,000 x 10
    	 		numWins = 10;
    	 		winTiers = new Integer[]{5, 5, 5, 5, 5, 5, 5, 5, 5, 5};
    	 		break;
    	 	case 7:
    	 		// (�,000 x 4) + (�00 x 3) + (�00 x 3) + �0 + �0 + (�0 x 3)
    	 		numWins = 15;
    	 		winTiers = new Integer[]{5, 5, 5, 5, 6, 6, 6, 7, 7, 7, 8, 10, 12, 12, 12};
    	 		break;
    	 	case 8:
    	 		// (�,000 x 3) + (�00 x 10)
    	 		numWins = 13;
    	 		winTiers = new Integer[]{5, 5, 5, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6};
    	 		break;
    	 	case 9:
    	 		// �,000 x 5
    	 		numWins = 5;
    	 		winTiers = new Integer[]{5, 5, 5, 5, 5};
    	 		break;
    	 	case 11:
    	 		// �00 x 5
    	 		numWins = 5;
    	 		winTiers = new Integer[]{6, 6, 6, 6, 6};
    	 		break;
    	 	case 12:
    	 		// (�00 x 9) + (�0 x 4) + (�0 x 2)
    	 		numWins = 15;
    	 		winTiers = new Integer[]{7, 7, 7, 7, 7, 7, 7, 7, 7, 10, 10, 10, 10, 12, 12};
    	 		break;
    	 	case 14:
    	 		// �00 + (�0 x 2)
    	 		numWins = 3;
    	 		winTiers = new Integer[]{7, 8, 8};
    	 		break;
    	 	case 15:
    	 		// �0 x 10
    	 		numWins = 10;
    	 		winTiers = new Integer[]{10, 10, 10, 10, 10, 10, 10, 10, 10, 10};
    	 		break;
    	 	case 16:
    	 		// �0 + �0 + (�0 x 13)
    	 		numWins = 15;
    	 		winTiers = new Integer[]{8, 10, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12};
    	 		break;
    	 	case 18:
    	 		// �0 + �0 + (�5 x 2)
    	 		numWins = 4;
    	 		winTiers = new Integer[]{8, 10, 11, 11};
    	 		break;
    	 	case 19:
    	 		// (�0 x 5) + (� x 10)
    	 		numWins = 15;
    	 		winTiers = new Integer[]{12, 12, 12, 12, 12, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13};
    	 		break;
    	 	case 20:
    	 		// �0 x 5
    	 		numWins = 5;
    	 		winTiers = new Integer[]{10, 10, 10, 10, 10};
    	 		break;
    	 	case 22:
    	 		// �0 + �0
    	 		numWins = 2;
    	 		winTiers = new Integer[]{9, 10};
    	 		break;
    	 	case 23:
    	 		// � x 10
    	 		numWins = 10;
    	 		winTiers = new Integer[]{13, 13, 13, 13, 13, 13, 13, 13, 13, 13};
    	 		break;
    	 	case 24:
    	 		// �0 x 5
    	 		numWins = 5;
    	 		winTiers = new Integer[]{12, 12, 12, 12, 12};
    	 		break;
    	 	case 26:
    	 		// �0 + (� x 4)
    	 		numWins = 5;
    	 		winTiers = new Integer[]{12, 13, 13, 13, 13};
    	 		break;
    	 	case 27:
    	 		// �5 x 2
    	 		numWins = 2;
    	 		winTiers = new Integer[]{11, 11};
    	 		break;
    	 	case 29:
    	 		// �0 x 2
    	 		numWins = 2;
    	 		winTiers = new Integer[]{12, 12};
    	 		break;
    	 	case 30:
    	 		// � x 4
    	 		numWins = 4;
    	 		winTiers = new Integer[]{13, 13, 13, 13};
    	 		break;
    	 	case 32:
    	 		// �0 + �
    	 		numWins = 2;
    	 		winTiers = new Integer[]{12, 13};
    	 		break;
    	 	case 33:
    	 		// � x 3
    	 		numWins = 3;
    	 		winTiers = new Integer[]{13, 13, 13};
    	 		break;
    	 	case 35:
    	 		// � x 2
    	 		numWins = 2;
    	 		winTiers = new Integer[]{13, 13};
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
    		 {
    			 recalcGamesLeft();
    		 }
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
     
     private static final String[] g1Symbols = {"s", "th", "d", "b", "m", "a", "i", "t", "w"};
     private static final Integer[] g1SymbolNums = {1, 2, 3, 4, 5, 6, 7, 8, 9};
     private int[] g1SymbolCount;
     private int[][] g1GameSlots;
     
     /**
      * Plays the first game
      * @param winRows The number of winning rows for this game
      */
     private void playGame1(int winRows)
     {
    	 g1GameSlots = new int[6][4]; // Note the 1st column will denote whether it's a win or lose row
    	 g1SymbolCount = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
    	 
    	 ArrayList<Integer> g1SymbolsLeft = new ArrayList<Integer>();
    	 Collections.addAll(g1SymbolsLeft, g1SymbolNums);
    	 ArrayList<Integer> possibleSymbols;
    	 
    	 int rSymbolIndex;
    	 int rSymbol;
    	 int rRow;
    	 
    	 // Check if winning game
    	 if (winRows > 0)
    	 {
    		 // For each winning row, pick symbols
    		 for (int i = 0; i < winRows; i++)
    		 {
    			 // Pick a random symbol
    			 rSymbolIndex = randomNumber(0, g1SymbolsLeft.size()-1);
    			 rSymbol = g1SymbolsLeft.get(rSymbolIndex);
    			 
    			 // Pick a random row and insert the symbol there
    			 rRow = randomNumber(0, g1GameSlots.length-1);
    			 g1InsertAtOrNext(rRow, new int[]{1, rSymbol, rSymbol, rSymbol}, g1GameSlots.length);
    			 
    			 // Remove random symbol from selection
    			 g1SymbolsLeft.remove(rSymbolIndex);
    		 }
    	 }
    	 else
    	 {
    		 // Losing game so sort "almost win" tiers
    		 possibleSymbols = new ArrayList<Integer>(g1SymbolsLeft);
    		 
    		 // Pick first two almost win tiers, symbol goes in slots 1 and 2 in row
    		 for (int i = 0; i < 2; i++)
    		 {
	    		 rSymbolIndex = randomNumber(0, possibleSymbols.size()-1);
	    		 rSymbol = possibleSymbols.get(rSymbolIndex);
	    		 rRow = randomNumber(0, g1GameSlots.length-1);
				 g1InsertAtOrNext(rRow, new int[]{0, rSymbol, rSymbol, -1}, g1GameSlots.length);
				 possibleSymbols.remove(rSymbolIndex); // Remove this temporarily so no 2 almosts are the same
				 g1SymbolCount[rSymbol-1]+= 2;
    		 }
			 
			 // Now 2 more almost tiers, symbol in slots 1 and 3 or 2 and 3
			 for (int j = 0; j < 2; j++)
			 {
	    		 rSymbolIndex = randomNumber(0, possibleSymbols.size()-1);
	    		 rSymbol = possibleSymbols.get(rSymbolIndex);
	    		 rRow = randomNumber(0, g1GameSlots.length-1);
	    		 if (randomNumber(1, 2) == 1)
	    			 g1InsertAtOrNext(rRow, new int[]{0, rSymbol, -1, rSymbol}, g1GameSlots.length);
	    		 else
	    			 g1InsertAtOrNext(rRow, new int[]{0, -1, rSymbol, rSymbol}, g1GameSlots.length);
				 possibleSymbols.remove(rSymbolIndex); // Remove this temporarily so no 2 almosts are the same
				 g1SymbolCount[rSymbol-1]+= 2;
			 }
    	 }
    	 
    	 // Reset possible symbols now so that it only has removed any winning symbols
    	 possibleSymbols = new ArrayList<Integer>(g1SymbolsLeft);
    	 
    	 int sAbove = 0;
    	 int sBelow = 0;
    	 int dupNum = 0;
    	 
    	 // Now fill remaining symbols - go through g1GameSlots and replace any 0s or -1s
    	 for (int row = 0; row < g1GameSlots.length; row++)
    	 {
    		 for (int column = 1; column < 4; column++) // Note - ignore column 0 as that should remain "0" to denote losing rows
    		 {
    			 if (g1GameSlots[row][column] < 1)
    			 {
    				 // Reset possible symbols for this row
    				 possibleSymbols = new ArrayList<Integer>(g1SymbolsLeft);
    	    		 
    				 // Remove symbols above and below in this column from possibilities to adhere
    				 // to game rule regarding no adjacent like symbols
    				 if (row > 0)
    					 sAbove = g1GameSlots[row-1][column];
    				 if (row < g1GameSlots.length - 1)
    					 sBelow = g1GameSlots[row+1][column];
    				 
    				 // If replacing a value in one of the almost columns, we need to make sure the value picked is not the same as the others
    				 if (column == 1 && (g1GameSlots[row][2] == g1GameSlots[row][3]))
    					 dupNum = g1GameSlots[row][2];
    				 if (column == 2 && (g1GameSlots[row][1] == g1GameSlots[row][3]))
    					 dupNum = g1GameSlots[row][1];
    				 if (column == 3 && (g1GameSlots[row][1] == g1GameSlots[row][2]))
    					 dupNum = g1GameSlots[row][1];
    				 
    				 if (dupNum > 0)
    				 {
    					 if (possibleSymbols.indexOf(dupNum) > -1)
    					 {
    						 possibleSymbols.remove(possibleSymbols.indexOf(dupNum));
    					 }
    				 }
    				 
    				 // Now remove these from possibilities this time
    				 if (possibleSymbols.indexOf(sAbove) > -1)
    					 possibleSymbols.remove(possibleSymbols.indexOf(sAbove));
    				 if (possibleSymbols.indexOf(sBelow) > -1)
    					 possibleSymbols.remove(possibleSymbols.indexOf(sBelow));
    				 
    				 rSymbolIndex = randomNumber(0, possibleSymbols.size()-1);
    	    		 rSymbol = possibleSymbols.get(rSymbolIndex);
    	    		 g1GameSlots[row][column] = rSymbol;
    	    		 g1SymbolCount[rSymbol-1]++;
    	    		 
    	    		 if (g1SymbolCount[rSymbol-1] >= 3)
    	    		 {
    	    			 g1SymbolsLeft.remove(g1SymbolsLeft.indexOf(rSymbol));
    	    		 }
    			 }
    		 }
    	 }
     }
     
     /**
      * Inserts a value into the g1GameSlots array at index; if it is filled finds the next empty slot
      * @param index  The index at which to insert the value into the column
      * @param values  The array of values to be inserted
      * @param maxLength  The maximum index to check (before looping back to start of the g1GameSlots array)
      * @return
      */
     private int g1InsertAtOrNext(int index, int[] values, int maxLength)
     {
    	if (g1GameSlots[index][1] == 0)
    	{
    		// Insert value at index
    		g1GameSlots[index] = values;
    		return index;
    	}
    	else
    	{
    		int count = 0;
    		// Loop through array until find a new value
    		for (int i = index; i <= maxLength; i++)
    		{
    			if (count <= 9)
    			{
    				if (i >= maxLength)
					{
	    				// If we reach end of the array (or maxlength), loop back to the start
						i = 0;
					}
	    			if (g1GameSlots[i][1] == 0)
	    			{
	    				g1GameSlots[i] = values;
	    				return i;
	    			}
    			}
    			count++;
    		}
    		return -1;
    	}
     }
     
     private static final Integer[] g2Symbols = {1, 2, 3, 4, 5, 6};
     private int[] g2SymbolCount;
     private int[][] g2GameSlots;
     
     /**
      * Plays the second game
      * @param winRows The number of winning rows for this game
      */
     private void playGame2(int winRows)
     {
    	 g2GameSlots = new int[3][3]; // Note the 1st column will denote whether it's a win or lose row
    	 g2SymbolCount = new int[]{0, 0, 0, 0, 0, 0};
    	 ArrayList<Integer> g2RowsLeft = new ArrayList<Integer>();
    	 Collections.addAll(g2RowsLeft, new Integer[]{0, 1, 2});
    	 
    	 ArrayList<Integer> g2SymbolsLeft = new ArrayList<Integer>();
    	 Collections.addAll(g2SymbolsLeft, g2Symbols);
    	 ArrayList<Integer> possibleSymbols;
    	 
    	 int rowsLeft = 3;
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
    	 
    	 possibleSymbols = new ArrayList<Integer>(g2SymbolsLeft);
    	 int sAbove = 0;
    	 int sBelow = 0;
    	 int sLast = 0;
    	 int maxTimes = 2;
    	 
    	 // If remaining losing rows, insert symbols into remaining places
    	 if (rowsLeft > 0)
    	 {
    		 for (int row = 0; row < g2GameSlots.length; row++)
        	 {
        		 for (int column = 1; column < 3; column++) // Note - ignore column 0 as that should remain "0" to denote losing rows
        		 {
        			 if (g2GameSlots[row][column] < 1)
        			 {
        				 // Remove symbols above and below in this column from possibilities to adhere
        				 // to game rule regarding no adjacent like symbols
        				 if (row > 0)
        					 sAbove = g2GameSlots[row-1][column];
        				 if (row < g2GameSlots.length - 1)
        					 sBelow = g2GameSlots[row+1][column];
        				 
        				 // Now remove these from possibilities this time
        				 if (possibleSymbols.indexOf(sAbove) > -1)
        					 possibleSymbols.remove(possibleSymbols.indexOf(sAbove));
        				 if (possibleSymbols.indexOf(sBelow) > -1)
        					 possibleSymbols.remove(possibleSymbols.indexOf(sBelow));
        				 if (possibleSymbols.indexOf(sLast) > -1)
        					 possibleSymbols.remove(possibleSymbols.indexOf(sLast));
        				 
        				 rSymbolIndex = randomNumber(0, possibleSymbols.size()-1);
        	    		 rSymbol = possibleSymbols.get(rSymbolIndex);
        	    		 g2GameSlots[row][column] = rSymbol;
        	    		 g2SymbolCount[rSymbol-1]++;
        	    		 sLast = rSymbol; // Remember this symbol so it won't be used next turn
        	    		 
        	    		 if (g2SymbolCount[rSymbol-1] >= maxTimes)
        	    		 {
        	    			 g2SymbolsLeft.remove(g2SymbolsLeft.indexOf(rSymbol));
        	    			 maxTimes = 1; // Only allow one symbol to appear twice, just to make it appear more random for losing games
        	    		 }
        	    		 // Reset possible symbols for next row
        	    		 possibleSymbols = new ArrayList<Integer>(g2SymbolsLeft);
        			 }
        		 }
        	 }
    	 }
     }
     
     private static final String[] g3Symbols = {"s", "th", "d", "b", "t", "c", "i", "w"};
     private static final Integer[] g3Numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9};
     private static final Integer[] g3AlmostTotals = {8, 9, 11, 12};
     private static final int winTotal = 10;
     private String[][] g3SymbolSlots;
     private int[][] g3GameSlots;
     private int[] g3SymbolCount;
     
     /**
      * Plays the third game
      * @param winRows The number of winning rows for this game
      */
     private void playGame3(int winRows)
     {
    	 g3SymbolSlots = new String[5][2];
    	 g3GameSlots = new int[5][4]; // Note the 1st column will denote whether it's a win or lose row
    	 g3SymbolCount = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    	 
    	 ArrayList<Integer> g3RowsLeft = new ArrayList<Integer>();
    	 Collections.addAll(g3RowsLeft, new Integer[]{0, 1, 2, 3, 4});
    	 
    	 ArrayList<Integer> g3AlmostLeft = new ArrayList<Integer>();
    	 Collections.addAll(g3AlmostLeft, g3AlmostTotals);
    	 ArrayList<Integer> g3NumbersLeft = new ArrayList<Integer>();
    	 Collections.addAll(g3NumbersLeft, g3Numbers);
    	 ArrayList<Integer> possibleSymbols;
    	 
    	 int rowsLeft = 5;
    	 int rNumberIndex;
    	 int rNumber;
    	 int sNumberIndex;
    	 int sNumber;
    	 int rRowIndex;
    	 int rRow;
    	 int rTotalIndex;
    	 int rTotal;
    	 
    	 // Check if winning game
    	 if (winRows > 0)
    	 {
    		 // For each winning row, pick first number then calculate second so it adds to winTotal
    		 for (int i = 0; i < winRows; i++)
    		 {
    			 // Pick a random number
    			 rNumberIndex = randomNumber(0, g3NumbersLeft.size()-1);
    			 rNumber = g3NumbersLeft.get(rNumberIndex);
    			 
    			 sNumber = winTotal - rNumber;
    			 
    			 // Pick a random row and insert the relevant numbers there
    			 rRowIndex = randomNumber(0, g3RowsLeft.size()-1);
    			 rRow = g3RowsLeft.get(rRowIndex);
    			 g3GameSlots[rRow][0] = 1; // Denotes a win
    			 g3GameSlots[rRow][1] = rNumber;
    			 g3GameSlots[rRow][2] = sNumber;
    			 g3GameSlots[rRow][3] = winTotal;
    			 
    			 g3SymbolCount[rNumber-1]++;
    			 g3SymbolCount[sNumber-1]++;
    			 
    			 // For a winning game, we remove the random symbol (first number in row) from selection again
    			 // to ensure there is no repetition if this game has multiple wins
    			 g3NumbersLeft.remove(rNumberIndex);
    			 g3RowsLeft.remove(rRowIndex);
    			 rowsLeft--;
    		 }
    	 }
    	 else
    	 {
    		 // Need to pick 1 or 2 "almost wins" rows
    		 possibleSymbols = new ArrayList<Integer>(g3NumbersLeft);
    		 
    		 int numAlmost = randomNumber(1, 2);
    		 
    		 // Pick the random total, then work the rest out 
    		 for (int i = 0; i < numAlmost; i++)
    		 {
    			 // Pick the random total
    			 rTotalIndex = randomNumber(0, g3AlmostLeft.size()-1);
    			 rTotal = g3AlmostLeft.get(rTotalIndex);
    			 
    			 int rMin = 0;
    			 int rMax = possibleSymbols.size()-1;
    			 // If the random total is 12, minimum possible value is 3 (12 - 3 = 9), also remove 6 as cannot duplicate numbers on row
    			 if (rTotal == 12)
    			 {
    				 if (possibleSymbols.indexOf(3) > -1)
    				 {
    					 rMin = possibleSymbols.indexOf(3);
    				 }
    				 else
    				 {
    					 rMin = possibleSymbols.indexOf(4); // If 3 has already been removed
    				 }
    				 
    				 if (possibleSymbols.indexOf(6) > -1)
    				 {
    					 possibleSymbols.remove(possibleSymbols.indexOf(6));
    					 rMax--;
    				 }
    			 }
    			 // If the random total is 11, minimum possible value is 2 (11 - 2 = 9)
    			 if (rTotal == 11)
    			 {
    				 if (possibleSymbols.indexOf(2) > -1)
    				 {
    					 rMin = possibleSymbols.indexOf(2);
    				 }
    				 else
    				 {
    					 rMin = possibleSymbols.indexOf(3); // If 2 has already been removed
    				 }
    			 }
    			 // If the random total is 9, maximum possible value is 8
    			 if (rTotal == 9)
    			 {
    				 if (possibleSymbols.indexOf(8) > -1)
    				 {
    					 rMax = possibleSymbols.indexOf(8);
    				 }
    				 else
    				 {
    					 rMax = possibleSymbols.indexOf(7); // If 8 has already been removed
    				 }
    			 }
    			 // If the random total is 8, maximum possible value is 7, also remove 4 as cannot duplicate numbers on row
    			 if (rTotal == 8)
    			 {
    				 if (possibleSymbols.indexOf(7) > -1)
    				 {
    					 rMax = possibleSymbols.indexOf(7);
    				 }
    				 else
    				 {
    					 rMax = possibleSymbols.indexOf(6); // If 7 has already been removed
    				 }
    				 
    				 if (possibleSymbols.indexOf(4) > -1)
    				 {
    					 possibleSymbols.remove(possibleSymbols.indexOf(4));
    					 rMax--;
    				 }
    			 }
    			 // Extra check - if in the occasional case both numbers in one of the above if statements have been removed,
    			 // one of these values could be -1 so ensure this is never the case
    			 if (rMin < 0)
    				 rMin = 0;
    			 if (rMax < 0)
    				 rMax = possibleSymbols.size()-1;
    			 
	    		 rNumberIndex = randomNumber(rMin, rMax);
	    		 rNumber = possibleSymbols.get(rNumberIndex);
	    		 
	    		 sNumber = rTotal - rNumber;
	    		 
	    		 // Pick a random row and insert the relevant numbers there
    			 rRowIndex = randomNumber(0, g3RowsLeft.size()-1);
    			 rRow = g3RowsLeft.get(rRowIndex);
    			 g3GameSlots[rRow][0] = 0; // Denotes a lose
    			 g3GameSlots[rRow][1] = rNumber;
    			 g3GameSlots[rRow][2] = sNumber;
    			 g3GameSlots[rRow][3] = rTotal;
    			 
    			 g3SymbolCount[rNumber-1]++;
    			 g3SymbolCount[sNumber-1]++;
    			 
    			 // Now remove this random symbol and total to ensure 2 almost totals aren't the same
    			 possibleSymbols.remove(rNumberIndex);
    			 if (possibleSymbols.indexOf(sNumber) > -1)
    			 {
    				 possibleSymbols.remove(possibleSymbols.indexOf(sNumber));
    			 }
    			 g3NumbersLeft.remove(rNumberIndex);
    			 if (g3NumbersLeft.indexOf(sNumber) > -1) // This ensures two rows can't have the same numbers later
    			 {
    				 g3NumbersLeft.remove(g3NumbersLeft.indexOf(sNumber));
    			 }
    			 g3AlmostLeft.remove(rTotalIndex);
    			 g3RowsLeft.remove(rRowIndex);
    			 rowsLeft--;
    		 }
    	 }
    	     	 
    	 // Reset possible symbols now so that it only has removed any winning symbols
    	 possibleSymbols = new ArrayList<Integer>(g3NumbersLeft);
    	 
    	 int maxCount = 0;
    	 int numMax = 2;
    	 
    	 // Now fill remaining rows
    	 for (int row = 0; row < g3GameSlots.length; row++)
    	 {
		 	 if (g3GameSlots[row][1] < 1) // If the first number column is 0, then this row is untouched
			 {
				 // Pick first column number
				 rNumberIndex = randomNumber(0, possibleSymbols.size()-1);
	    		 rNumber = possibleSymbols.get(rNumberIndex);
	    		 
	    		 // Now remove this symbol from remaining possibilities (no like symbols on same row rule)
	    		 possibleSymbols.remove(rNumberIndex);
	    		 // Pick second column number
	    		 sNumberIndex = randomNumber(0, possibleSymbols.size()-1);
	    		 sNumber = possibleSymbols.get(sNumberIndex);
	    		 
	    		 if (rNumber + sNumber == 10)
	    			 sNumber = (sNumber < 9) ? sNumber+1 : sNumber-1; 
	    		 
	    		 g3GameSlots[row][0] = 0; // Denotes a lose
    			 g3GameSlots[row][1] = rNumber;
    			 g3GameSlots[row][2] = sNumber;
    			 g3GameSlots[row][3] = rNumber + sNumber;
    			 
    			 g3SymbolCount[rNumber-1]++;
    			 g3SymbolCount[sNumber-1]++;
	    		 
    			 // This first number will always be in g3NumbersLeft, always remove this one so can never pick same number in column 1
    			 g3NumbersLeft.remove(g3NumbersLeft.indexOf(rNumber));
	    		 
	    		 if (g3SymbolCount[sNumber-1] >= numMax) // This symbol might have been changed, so first check if it is in array before removing
	    		 {
	    			 if (g3NumbersLeft.indexOf(sNumber) > -1)
	    				 g3NumbersLeft.remove(g3NumbersLeft.indexOf(sNumber));
	    			 maxCount++;
	    		 }
	    		 
	    		 // If we have reached the maximum number of appearances for a number once, reduce the maximum
	    		 // number of times a number can appear (eg. if one numbers appear twice, all other numbers can appear only once)
	    		 if (maxCount > 1)
	    			 numMax = 1;
	    		 
	    		 // Reset possible symbols for next row
	    		 possibleSymbols = new ArrayList<Integer>(g3NumbersLeft);
			 }
    	 }
    	 
    	 int rSymbolIndex;
    	 String rSymbol;
    	 
    	 // Assign symbols to slots
    	 ArrayList<String> g3SymbolsLeft = new ArrayList<String>();
    	 Collections.addAll(g3SymbolsLeft, g3Symbols);
    	 
    	 for (int row = 0; row < g3SymbolSlots.length; row++)
    	 {
    		 for (int column = 0; column < g3SymbolSlots[row].length; column++)
    		 {
	    		 rSymbolIndex = randomNumber(0, g3SymbolsLeft.size()-1);
	    		 rSymbol = g3SymbolsLeft.get(rSymbolIndex);
	    		 g3SymbolSlots[row][column] = rSymbol;
	    		 g3SymbolsLeft.remove(rSymbolIndex);
	    		 
	    		 if (g3SymbolsLeft.size() == 0)
	    			 Collections.addAll(g3SymbolsLeft, g3Symbols);
    		 }
    	 }
     }
     
     private static final String[] g4LoseSymbols = {"j", "e", "f", "p", "r", "t", "w"};
     private static final String g4WinSymbol = "m";     
     private String g4Symbol;
     private int g4Win;
     
     /**
      * Plays the fourth game
      * @param win 1 if this game is a win, 0 if a lose
      */
     private void playGame4(int win)
     {
    	 g4Win = win;
    	 
    	 if (g4Win == 1)
    	 {
    		 g4Symbol = g4WinSymbol;
    	 }
    	 else
    	 {
    		 int randomIndex = randomNumber(0, g4LoseSymbols.length - 1);
    		 g4Symbol = g4LoseSymbols[randomIndex];
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
    	 
    	 int turnsLeft = 15;
    	 
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
    			 winIndex.add(i+6);
    		 }
    	 }
    	 // Game 3
    	 for (int i = 0; i < g3GameSlots.length; i++)
    	 {
    		 if (g3GameSlots[i][0] == 1)
    		 {
    			 winIndex.add(i+9);
    		 }
    	 }
    	 // Game 4
    	 if (g4Win == 1)
    	 {
    		 winIndex.add(14); // Final go
    	 }
    	 
    	 int rIndex1;
    	 int rValue1;
    	 int rIndex2;
    	 int rValue2;
    	 
    	 if (gameWin)
    	 {
    		 int winSize = winIndex.size();
	    	 // Assign random prizes to the winning indexes
	    	 for (int j = 0; j < winSize; j++)
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
    			if (count <= 15)
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
     		xml.append("s1=\"" + g1Symbols[g1GameSlots[i][1]-1] + "\" ");
     		xml.append("s2=\"" + g1Symbols[g1GameSlots[i][2]-1] + "\" ");
     		xml.append("s3=\"" + g1Symbols[g1GameSlots[i][3]-1] + "\" ");
     		xml.append("p=\"" + prizeLevels[prizes[i]-1][1] + "\"/>");
     	}
     	// Game 2
     	for (int i = 0; i < g2GameSlots.length; i++)
     	{
     		xml.append("<turn name=\"go" + (i+6) + "\" g=\"2\" ");
     		xml.append("r1=\"" + g2GameSlots[i][1] + "\" ");
     		xml.append("r2=\"" + g2GameSlots[i][2] + "\" ");
     		xml.append("p=\"" + prizeLevels[prizes[i+6]-1][1] + "\"/>");
     	}
     	// Game 3
     	for (int i = 0; i < g3GameSlots.length; i++)
     	{
     		xml.append("<turn name=\"go" + (i+9) + "\" g=\"3\" ");
     		xml.append("s1=\"" + g3SymbolSlots[i][0] + "\" ");
     		xml.append("n1=\"" + g3GameSlots[i][1] + "\" ");
     		xml.append("s2=\"" + g3SymbolSlots[i][1] + "\" ");
     		xml.append("n2=\"" + g3GameSlots[i][2] + "\" ");
     		xml.append("p=\"" + prizeLevels[prizes[i+9]-1][1] + "\"/>");
     	}
     	// Game 4
     	xml.append("<turn name=\"go14\" g=\"4\" ");
 		xml.append("s=\"" + g4Symbol + "\" ");
 		xml.append("p=\"" + prizeLevels[prizes[14]-1][1] + "\"/>");
     	
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

