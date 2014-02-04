/**
2 * Title:		Triple Lucky Sevens
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

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;



public class Tripleluckyseventwel_Original implements TicketDataGenerator {
	/**
	 * This class defines the class for generating ticket data for Pirate Payout instant
	 * tickets.  It requires implementation of a method that generates ticket data
	 * in the form of a String based on outcome data. The return string format is
	 * defined the getTicketData() method. The class also has a method for setting
	 * the prize parameters.
	 *
	 */
    private PrizeParameters prizeParams = null;

    private int[] prizeTier;
    private int[] column1;
    private int[] column2;
    private int[] column3;
    private int[] colour1;
    private int[] colour2;
    private int[] colour3;
    private int[] timesUsed;
    private int[] prizeTiers;
    private String[] prizeAmount;
    
    private static final Integer[] tierMap = {1, 2, 2, 3, 3, 4, 4, 4, 4, 5, 5, 5, 5, 6, 6, 6, 6, 7, 7, 7, 7, 8, 8, 8, 9, 9, 9, 10, 11};
    private static final Integer[] gameTiers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
    private static final Integer[] numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9};
    private static final int winSymbol = 7;
    
    private ArrayList tiersLeft;
    private ArrayList numbersLeft;
    
    private int winTier;
    
    private static final Integer[] tierLevels = {
    	0,	// Index/Tier 0 not used
    	1,	// Tier 1 - High
    	1,	// Tier 2 - High
    	1,	// Tier 3 - High
    	2,	// Tier 4 - Medium
    	2,	// Tier 5 - Medium
    	2,	// Tier 6 - Medium
    	2,	// Tier 7 - Medium
    	3,	// Tier 8 - Low
    	3,  // Tier 9 - Low
    	4,  // Tier 10 - Lowest
    	4   // Tier 11 - Lowest
    };

    private static final String[] prizeLevels = {
    		"70000",
    		"7000",
    		"700",
    		"200",
    		"70",
    		"40",
    		"20",
    		"10",
    		"6",
    		"3",
    		"2",
    		"0"
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
         
         prizeTier = new int[]{0, 0, 0, 0};
         column1 = new int[]{0, 0, 0, 0, 0, 0, 0};
         column2 = new int[]{0, 0, 0, 0, 0, 0, 0};
         column3 = new int[]{0, 0, 0, 0, 0, 0, 0};
         colour1 = new int[]{0, 0, 0, 0, 0, 0, 0};
         colour2 = new int[]{0, 0, 0, 0, 0, 0, 0};
         colour3 = new int[]{0, 0, 0, 0, 0, 0, 0};
         timesUsed = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
         prizeTiers = new int[]{0, 0, 0, 0, 0, 0, 0};
         prizeAmount = new String[]{"", "", "", "", "", "", ""};
         
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
    	 boolean gameWin = gameOutcome.isWinner() ? true : false; 
    	 int gameTier = gameOutcome.getTierNumber();
    	 
    	 switch (gameTier)
    	 {
    	 	case 2:
    	 	case 4:
    	 	case 6:
    	 	case 7:
    	 	case 8:
    	 	case 10:
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
    	 	case 25:
    	 	case 26:
    	 		processSpecialTier(gameTier);
    	 		break;
    	 	default:
    	 		processNormalTier(gameTier, gameWin);
    	 }
    	 
    	 return generateXml(gameOutcome);
     }
     
     /**
      * Processes a single win or losing outcome
      * @param gameTier The game tier
      * @param gameWin True if the game is a win, false if lose
      */
     private void processNormalTier(int gameTier, boolean gameWin)
     {
    	 tiersLeft = new ArrayList(Arrays.asList(gameTiers));
    	 numbersLeft = new ArrayList(Arrays.asList(numbers));
    	 int randomIndex;
    	 int randomValue;
    	 int randomRow;
    	 int thisRow;
    	 int rowsLeft = 7;
    	 int randomColour;
    	 
    	 if (gameWin)
    	 {
    		 winTier = gameTiers[tierMap[gameTier - 1] - 1];
    		 // Remove win tier from possibilities
    		 tiersLeft.remove(winTier - 1);
    		 // Pick winning row
    		 randomRow = randomNumber(0, 6);
    		 // Insert winSymbol into winning row
    		 // Winning colour in this case is black (0), so value already set
    		 column1[randomRow] = winSymbol;
    		 column2[randomRow] = winSymbol;
    		 column3[randomRow] = winSymbol;
    		 
    		 prizeTiers[randomRow] = winTier;
    		 
    		 // Note that 1 row is filled
    		 rowsLeft = 6;
       	 }
    	 else
    	 {
    		 winTier = 12; // For a lose, the prize lookup amount of 0 is in last position in array
    	 }
    	 
		 // Assign the value of the 7s as per rules for losing games
    	 // Also used to fill remaining rows on a single row win
		 // First rule for at least 3 rows - 2 7s (black or green) - For single row win apply to 2 rows
		 for (int i = 0; i < rowsLeft - 4; i++)
		 {
			 // Pick colour - black should appear 60% of time, green 40%
			 randomColour = (randomNumber(1, 5) > 3) ? 1 : 0;
			 randomRow = randomNumber(0, 6);
			 thisRow = insertAtOrNext(randomRow, winSymbol, column1.length);
			 colour1[thisRow] = randomColour;
			 
			 if (randomNumber(0, 1) == 0)
			 {
				 column2[thisRow] = winSymbol;
				 colour2[thisRow] = randomColour;
			 }
			 else
			 {
				 column3[thisRow] = winSymbol;
				 colour3[thisRow] = randomColour;
			 }
		 }
		 // Next rule for at least 2 rows - 1 black 7 and one green 7
		 for (int i = 0; i < 2; i++)
		 {
			 ArrayList<Integer> values = new ArrayList<Integer>();
			 values.add(winSymbol); // Represents black 7
			 values.add(winSymbol * 2); // Represents green 7
			 values.add(0); // Represents 3rd symbol we will randomly insert later
			 
			 randomRow = randomNumber(0, 6);
			 randomIndex = randomNumber(0, 2);
			 randomValue = (Integer)values.get(randomIndex);
			 // Insert the first random value into column1
			 thisRow = insertAtOrNext(randomRow, randomValue, column1.length);
			 if (randomValue == winSymbol * 2)
			 {
				 column1[thisRow] = winSymbol;
				 colour1[thisRow] = 1;
			 }
			 values.remove(randomIndex);
			 // Insert next random value into column2
			 randomIndex = randomNumber(0,1);
			 randomValue = (Integer)values.get(randomIndex);
			 if (randomValue == winSymbol * 2)
			 {
				 column2[thisRow] = winSymbol;
				 colour2[thisRow] = 1;
			 }
			 else
			 {
				 column2[thisRow] = randomValue;
			 }
			 values.remove(randomIndex);
			 // Insert final random value into column3
			 randomValue = (Integer)values.get(0);
			 if (randomValue == winSymbol * 2)
			 {
				 column3[thisRow] = winSymbol;
				 colour3[thisRow] = 1;
			 }
			 else
			 {
				 column3[thisRow] = randomValue;
			 }
		 }
		 // Final rule for at least 2 rows - 1 black or 1 green 7
		 for (int i = 0; i < 2; i++)
		 {
			 ArrayList<Integer> values = new ArrayList<Integer>();
			 values.add(winSymbol);
			 values.add(0); // Represents symbols to be randomly inserted later
			 values.add(0);
			 
			 randomColour = (randomNumber(1, 5) > 3) ? 1 : 0;
			 randomRow = randomNumber(0, 6);
			 randomIndex = randomNumber(0, 2);
			 randomValue = (Integer)values.get(randomIndex);
			 // Insert the first random value into column1
			 thisRow = insertAtOrNext(randomRow, randomValue, column1.length);
			 if (randomValue == winSymbol) colour1[thisRow] = randomColour;
			 values.remove(randomIndex);
			 // Insert next random value into column2
			 randomIndex = randomNumber(0,1);
			 randomValue = (Integer)values.get(randomIndex);
			 column2[thisRow] = randomValue;
			 if (randomValue == winSymbol) colour2[thisRow] = randomColour;
			 values.remove(randomIndex);
			 // Insert final random value into column3
			 randomValue = (Integer)values.get(0);
			 column3[thisRow] = randomValue;
			 if (randomValue == winSymbol) colour3[thisRow] = randomColour;
		 }
		 
		 // Remove winning symbol if it's still in array
		 int winIndex = (Integer)numbersLeft.indexOf(winSymbol);
    	 if (winIndex > -1) 
    		 numbersLeft.remove(winIndex);
		 
    	 // Go through each column and replace any 0 values with a random value and colour
		 for (int i = 0; i < column1.length; i++)
		 {
			 if (column1[i] == 0)
				 generateLosingValue(i, 1);
		 }
		 for (int i = 0; i < column2.length; i++)
		 {
			 if (column2[i] == 0)
				 generateLosingValue(i, 2);
		 }
		 for (int i = 0; i < column3.length; i++)
		 {
			 if (column3[i] == 0)
				 generateLosingValue(i, 3);
		 }
		 
		// Now fill prize values
    	 fillPrizeValues();
     }
     
     /**
      * Generates a random losing value for a given column in a row  
      * @param position The row to insert the value in
      * @param column The column to insert the value in
      */
     private void generateLosingValue(int position, int column)
     {
    	 int randomIndex;
    	 int randomValue;
    	 int randomColour;
    	 
    	 // Pick random number from remaining, and random colour
    	 randomIndex = randomNumber(0, numbersLeft.size() - 1);
    	 randomValue = (Integer)numbersLeft.get(randomIndex);
    	 randomColour = (randomNumber(1, 5) > 3) ? 1 : 0;
    	 
    	 switch (column)
    	 {
    	 	case 1:
    	 		column1[position] = randomValue;
    	 		colour1[position] = randomColour;
    	 		break;
    	 	case 2:
    	 		column2[position] = randomValue;
    	 		colour2[position] = randomColour;
    	 		break;
    	 	case 3:
    	 		column3[position] = randomValue;
    	 		colour3[position] = randomColour;
    	 		break;
    	 }
    	 
    	 // If number used twice already remove from possibilities
    	 timesUsed[randomValue - 1]++;
    	 if (timesUsed[randomValue - 1] > 1)
    		 numbersLeft.remove(randomIndex);
     }
     
     /**
      * Generates a losing row and inserts all the values into the correct column
      * @param position The positon of the row to be inserted
      */
     private void generateLosingRow(int position)
     {
    	 int randomIndex;
    	 int randomValue;
    	 int randomColour;
    	 int winCount = 0;
    	 
    	 // Pick random number from remaining, and random colour
    	 // COLUMN 1
    	 randomIndex = randomNumber(0, numbersLeft.size() - 1);
    	 randomValue = (Integer)numbersLeft.get(randomIndex);
    	 randomColour = (randomNumber(1, 5) > 3) ? 1 : 0;
    	 
    	 column1[position] = randomValue;
    	 colour1[position] = randomColour;
    	 
    	 if (randomValue == winSymbol)
    	 {
    		 winCount++;
    	 }
    	 else
    	 {
	    	 // If number used twice already remove from possibilities
	    	 timesUsed[randomValue - 1]++;
	    	 if (timesUsed[randomValue - 1] > 2)
	    		 numbersLeft.remove(randomIndex);
    	 }
    	 
    	 // COLUMN 2
    	 randomIndex = randomNumber(0, numbersLeft.size() - 1);
    	 randomValue = (Integer)numbersLeft.get(randomIndex);
    	 randomColour = (randomNumber(1, 5) > 3) ? 1 : 0;
    	 
    	 column2[position] = randomValue;
    	 colour2[position] = randomColour;
    	 
    	 if (randomValue == winSymbol)
    	 {
    		 winCount++;
    	 }
    	 else
    	 {
	    	 // If number used twice already remove from possibilities
	    	 timesUsed[randomValue - 1]++;
	    	 if (timesUsed[randomValue - 1] > 2)
	    		 numbersLeft.remove(randomIndex);
    	 }
    	 
    	 // COLUMN 3
    	 randomIndex = randomNumber(0, numbersLeft.size() - 1);
    	 randomValue = (Integer)numbersLeft.get(randomIndex);
    	 
    	 if (winCount == 2 && randomValue == winSymbol)
    	 {
    		 if (randomIndex > 0) randomIndex--;
    		 else randomIndex++;
    		 randomValue = (Integer)numbersLeft.get(randomIndex);
    	 }
    	 
    	 randomColour = (randomNumber(1, 5) > 3) ? 1 : 0;
    	 
    	 column3[position] = randomValue;
    	 colour3[position] = randomColour;
    	 
    	 if (randomValue != winSymbol)
    	 {
    		 // If number used twice already remove from possibilities
	    	 timesUsed[randomValue - 1]++;
	    	 if (timesUsed[randomValue - 1] > 2)
	    		 numbersLeft.remove(randomIndex);
    	 }
     }
     
     /**
      * Processes a tier that has a fixed outcome
      * @param gameTier The game tier
      */
     private void processSpecialTier(int gameTier)
     {
    	 tiersLeft = new ArrayList(Arrays.asList(gameTiers));
    	 numbersLeft = new ArrayList(Arrays.asList(numbers));
    	 
    	 // Determine the fixed outcomes
    	 switch (gameTier)
    	 {
	    	 case 2: // �00 + (700 triple x3)
	    		 insertComboRow(0, "prize700");
	    		 insertComboRow(1, "prize700x3");
	    		 insertComboRow(1, "prize700x3");
	    		 insertComboRow(1, "prize700x3");
	    		 removeLevel(1);
	    		 break;
	    	 case 4: // �0 triple + �0 + (�0 triple x5)
	    		 insertComboRow(1, "prize20x3");
	    		 insertComboRow(0, "prize40");
	    		 insertComboRow(1, "prize40x3");
	    		 insertComboRow(1, "prize40x3");
	    		 insertComboRow(1, "prize40x3");
	    		 insertComboRow(1, "prize40x3");
	    		 insertComboRow(1, "prize40x3");
	    		 break;
	    	 case 6: // (�0 triple x6) + �0
	    		 insertComboRow(1, "prize10x3");
	    		 insertComboRow(1, "prize10x3");
	    		 insertComboRow(1, "prize10x3");
	    		 insertComboRow(1, "prize10x3");
	    		 insertComboRow(1, "prize10x3");
	    		 insertComboRow(1, "prize10x3");
	    		 insertComboRow(0, "prize20");
	    		 break;
	    	 case 7: // � + � triple + �0 triple + �0 + �0 triple + (�0 x2)
	    		 insertComboRow(0, "prize2");
	    		 insertComboRow(1, "prize6x3");
	    		 insertComboRow(1, "prize10x3");
	    		 insertComboRow(0, "prize10");
	    		 insertComboRow(1, "prize20x3");
	    		 insertComboRow(0, "prize40");
	    		 insertComboRow(0, "prize40");
	    		 break;
	    	 case 8: // �0 + �0 triple + �0
	    		 insertComboRow(0, "prize10");
	    		 insertComboRow(1, "prize40x3");
	    		 insertComboRow(0, "prize70");
	    		 removeLevel(2);
	    		 break;
	    	 case 10: // �0 x7
	    		 insertComboRow(0, "prize10");
	    		 insertComboRow(0, "prize10");
	    		 insertComboRow(0, "prize10");
	    		 insertComboRow(0, "prize10");
	    		 insertComboRow(0, "prize10");
	    		 insertComboRow(0, "prize10");
	    		 insertComboRow(0, "prize10");
	    		 break;
	    	 case 11: // (� x2) + � + � triple + � triple + � triple + �0 triple
	    		 insertComboRow(0, "prize2");
	    		 insertComboRow(0, "prize2");
	    		 insertComboRow(0, "prize3");
	    		 insertComboRow(1, "prize2x3");
	    		 insertComboRow(1, "prize3x3");
	    		 insertComboRow(1, "prize6x3");
	    		 insertComboRow(1, "prize10x3");
	    		 break;
	    	 case 12: // �0 triple + �0
	    		 insertComboRow(1, "prize20x3");
	    		 insertComboRow(0, "prize10");
	    		 removeLevel(3);
	    		 break;
	    	 case 14: // (� x2) + (� x4) + �0
	    		 insertComboRow(0, "prize3");
	    		 insertComboRow(0, "prize3");
	    		 insertComboRow(0, "prize6");
	    		 insertComboRow(0, "prize6");
	    		 insertComboRow(0, "prize6");
	    		 insertComboRow(0, "prize6");
	    		 insertComboRow(0, "prize10");
	    		 break;
	    	 case 15: // (� x2)+ � + � triple + � triple + � triple
	    		 insertComboRow(0, "prize2");
	    		 insertComboRow(0, "prize2");
	    		 insertComboRow(0, "prize3");
	    		 insertComboRow(1, "prize2x3");
	    		 insertComboRow(1, "prize3x3");
	    		 insertComboRow(1, "prize6x3");
	    		 removeLevel(4);
	    		 break;
	    	 case 16: // �0 triple + �0
	    		 insertComboRow(1, "prize10x3");
	    		 insertComboRow(0, "prize10");
	    		 removeLevel(3);
	    		 break;
	    	 case 18: // � + � triple + (� x 4)
	    		 insertComboRow(0, "prize2");
	    		 insertComboRow(1, "prize2x3");
	    		 insertComboRow(0, "prize3");
	    		 insertComboRow(0, "prize3");
	    		 insertComboRow(0, "prize3");
	    		 insertComboRow(0, "prize3");
	    		 removeLevel(4);
	    		 break;
	    	 case 19: // � triple + �
	    		 insertComboRow(1, "prize6x3");
	    		 insertComboRow(0, "prize2");
	    		 break;
	    	 case 20: // � triple x2 + �
	    		 insertComboRow(1, "prize3x3");
	    		 insertComboRow(1, "prize3x3");
	    		 insertComboRow(0, "prize2");
	    		 removeLevel(4);
	    		 break;
	    	 case 22: // (� x2) + �
	    		 insertComboRow(0, "prize2");
	    		 insertComboRow(0, "prize2");
	    		 insertComboRow(0, "prize6");
	    		 removeLevel(4);
	    		 break;
	    	 case 23: // � triple + (� x2)
	    		 insertComboRow(1, "prize2x3");
	    		 insertComboRow(0, "prize2");
	    		 insertComboRow(0, "prize2");
	    		 removeLevel(4);
	    		 break;
	    	 case 25: // � triple
	    		 insertComboRow(1, "prize2x3");
	    		 break;
	    	 case 26: // � x2
	    		 insertComboRow(0, "prize3");
	    		 insertComboRow(0, "prize3");
	    		 removeLevel(4);
	    		 break;
    	 }
    	 
    	 // Now fill the remaining rows with random values
    	 for (int i = 0; i < column1.length; i++)
    	 {
    		 if (column1[i] == 0)
    		 {
    			 generateLosingRow(i);
    		 }
    	 }
    	 
    	 // Now fill prize values
    	 fillPrizeValues();
     }
     
     /**
      * Inserts a combination win row based on the parameters passed to it from processSpecialTier method
      * @param colour The colour the row should be
      * @param prize The prize label of the row for the xml
      */
     private void insertComboRow(int colour, String prize)
     {
    	 int randomRow;
    	 int thisRow;
    	 
    	 randomRow = randomNumber(0, 6);
    	 thisRow = insertAtOrNext(randomRow, winSymbol, column1.length);
    	 column2[thisRow] = winSymbol;
    	 column3[thisRow] = winSymbol;
    	 colour1[thisRow] = colour;
    	 colour2[thisRow] = colour;
    	 colour3[thisRow] = colour;
    	 prizeAmount[thisRow] = prize;
     }
     
     /**
      * Generates the prize values for each row in the game (where it has not previously been set)
      */
     private void fillPrizeValues()
     {
    	 int randomIndex = 0;
    	 int randomTier;
    	 boolean topLevel = false;
    	 
    	 // Check for top level prize, if doesn't appear must in 5/6 games
    	 int topIndex = tiersLeft.indexOf(1);
    	 if (topIndex >= 0)
    	 {
    		 topLevel = randomNumber(1, 6) == 6 ? true : false;
    	 }
    	 
    	 for (int i = 0; i < prizeTiers.length; i++)
    	 {
    		 if (prizeTiers[i] == 0)
    		 {
    			 if (topLevel)
    			 {
    				 randomTier = 1;
    				 topLevel = false;
    				 tiersLeft.remove(topIndex);
    			 }
    			 else
    			 {
    				 randomIndex = randomNumber(0, tiersLeft.size() - 1);
	    			 randomTier = (Integer)tiersLeft.get(randomIndex);
	    			 tiersLeft.remove(randomIndex);
    			 }
    			 prizeTiers[i] = randomTier;
    			 
    			 removeRepeatLevels();
    		 }
    	 }
    	 
    	 for (int i = 0; i < prizeAmount.length; i++)
    	 {
    		 if (prizeAmount[i].equals(""))
    		 {
    			 prizeAmount[i] = "prize" + prizeLevels[prizeTiers[i] - 1];
    		 }
    	 }
     }
     
     /**
      * Inserts a value into the column1 array at index; if it is filled finds the next empty slot
      * @param index  The index at which to insert the value into the column
      * @param value  The value to be inserted
      * @param maxLength  The maximum index to check (before looping back to start of the column1 array)
      * @return
      */
     private int insertAtOrNext(int index, int value, int maxLength)
     {
    	if (column1[index] == 0)
    	{
    		// Insert value at index
    		column1[index] = value;
    		return index;
    	}
    	else
    	{
    		int count = 0;
    		
    		// Loop through array until find a new value
    		for (int i = index + 1; i <= maxLength; i++)
    		{
    			if (count <= 9)
    			{
	    			if (i >= maxLength)
					{
	    				// If we reach end of the array (or maxlength), loop back to the start
						i = 0;
					}
	    			if (column1[i] == 0)
	    			{
	    				column1[i] = value;
	    				return i;
	    			}
    			}
    			count++;
    		}
    		return -1;
    	}
     }
     
     private void removeRepeatLevels()
     {
    	 removeRepeatLevels(2, 2, 2, 2);
     }
     
     /**
      * Counts the number of times each tier level appears in the gameSlots array, and removes any tiers that appear
      * more than the specified number of times.
      */
     private void removeRepeatLevels(int hMax, int mMax, int lMax, int lsMax)
     {
    	 int numH = 0;
    	 int numM = 0;
    	 int numL = 0;
    	 int numLs = 0;

    	 for (int i = 0; i < prizeTiers.length; i++)
    	 {
    		 switch (tierLevels[prizeTiers[i]])
    		 {
	    		 case 1:
	    			 numH++;
	    			 break;
	    		 case 2:
	    			 numM++;
	    			 break;
	    		 case 3:
	    			 numL++;
	    			 break;
	    		 case 4:
	    			 numLs++;
	    			 break;
    		 }
    	 }
    	 if (numH > hMax)
    		 removeLevel(1);
    	 if (numM > mMax)
    		 removeLevel(2);
    	 if (numL > lMax)
    		 removeLevel(3);
    	 if (numLs > lsMax)
    		 removeLevel(4);
     }
     
     /**
      * Removes a tier level from the array of possible remaining tiers to select from
      * @param level  The level to remove (1 = high, 2 = medium, 3 = low)
      */
     private void removeLevel(int level)
     {
    	 // First get the indexes to remove
    	 List removeList = new ArrayList();

    	 for (int i = 0; i < tiersLeft.size(); i++)
    	 {
    		if (tierLevels[(Integer)tiersLeft.get(i)] == level)
    			removeList.add((Integer)tiersLeft.get(i));
    	 }

    	 // Then remove
    	 for (int c = 0; c < removeList.size(); c++)
    	 {
    		 tiersLeft.remove(removeList.get(c));
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
    	amount = gameOutcome.isWinner() ? prizeLevels[gameTiers[tierMap[tier - 1] - 1] - 1] : "0";

     	StringBuilder xml = new StringBuilder();
     	xml.append("<?xml version='1.0' encoding='UTF-8' ?>");
     	xml.append("<ticket>");
     	xml.append("<outcome prizeTier=\"" + tier + "\" amount=\""
    			+ amount + ".00\" />");
     	xml.append("<params wT=\"" + win + "\" wAmount=\""
     			+ amount + ".00\" />");

     	String thisColour;
     	
     	for (int j = 0; j < column1.length; j++)
     	{
     		xml.append("<turn name=\"go" + j + "\" ");
     		xml.append("p1=\"" + column1[j] + "\" ");
     		thisColour = colour1[j] == 1 ? "g" : "b";
     		xml.append("c1=\"" + thisColour + "\" ");
     		xml.append("p2=\"" + column2[j] + "\" ");
     		thisColour = colour2[j] == 1 ? "g" : "b";
     		xml.append("c2=\"" + thisColour + "\" ");
     		xml.append("p3=\"" + column3[j] + "\" ");
     		thisColour = colour3[j] == 1 ? "g" : "b";
     		xml.append("c3=\"" + thisColour + "\" ");     		
     		xml.append("pz=\"" + prizeAmount[j] + "\" />");
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

