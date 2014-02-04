/**
2 * Title:		Top Cat
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



public class TopCat implements TicketDataGenerator {
	/**
	 * This class defines the class for generating ticket data for Top Cat instant
	 * tickets.  It requires implementation of a method that generates ticket data
	 * in the form of a String based on outcome data. The return string format is
	 * defined the getTicketData() method. The class also has a method for setting
	 * the prize parameters.
	 *
	 */
    private PrizeParameters prizeParams = null;

    private int[] gameSlots;
    
    private int[] timesUsed;

    private static final Integer[] gameTiers = {1, 2, 3, 4, 5, 6, 7, 8};
    private static final Integer[] charMap = {6, 5, 4, 3, 2, 7, 1, 0};
    private static final int topTier = 1;

    private List<Integer> tiersLeft;

    private static final Integer[] tierLevels = {
    	0,	// Index/Tier 0 not used
    	1,	// Tier 1 - High
    	2,	// Tier 2 - Medium
    	2,	// Tier 3 - Medium
    	2,	// Tier 4 - Medium
    	3,	// Tier 5 - Low
    	3,	// Tier 6 - Low
    	3,	// Tier 7 - Low
    	3	// Tier 8 - Low
    };

    private static final String[] prizeLevels = {
    		"5000",
    		"100",
    		"40",
    		"20",
    		"10",
    		"5",
    		"2",
    		"1",
    		"0"
    };
    
    private static final String[] prizeLabels = {
    	"topCat_5000",
    	"benny_100",
    	"choo_40",
    	"brain_20",
    	"fancy_10",
    	"fish_5",
    	"spook_2",
    	"dibble_1"
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
         
         gameSlots = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
         
         timesUsed = new int[]{0, 0, 0, 0, 0, 0, 0, 0};

         if (gameOutcome.isWinner() && gameOutcome.getTierNumber() != prizeParams.getTierCount())
         {
        	 xml = processWinOutcome(gameOutcome);
         }
         else
         {
        	 xml = processLoseOutcome(gameOutcome);
         }

         return xml;
     }

     /**
      * Process the win outcome and returns the generated XML ticket
      * @param gameOutcome
      * @return String  XML formatted ticket string to be read by Flash instance
      */
     private String processWinOutcome(GameOutcome gameOutcome)
     {
    	 int winTier = gameOutcome.getTierNumber();
    	 
    	 if (winTier == 6)
    	 {
	    	 processInstantWin(winTier);
    	 }
    	 else
    	 {
	    	 List<Integer> tiers = Arrays.asList(gameTiers);
	    	 
	    	 // Insert winTier randomly twice in slots 1-6, once in slot 7-9
	    	 insertAtOrNext(randomNumber(0,5), winTier, gameSlots.length);
	    	 insertAtOrNext(randomNumber(0,5), winTier, gameSlots.length);
	    	 insertAtOrNext(randomNumber(6,8), winTier, gameSlots.length);
	
	    	 // Remove auto-win tier (6) for losing games
	    	 tiers.remove(5);
	    	 
	    	 // Remove winning tier from possible remaining tiers
	    	 if (winTier < 6)
	    		 tiers.remove(winTier - 1);
	    	 else
	    		 tiers.remove(winTier - 2); // As tiers have shifted from removing auto-win tier
	
	    	 int randomIndex;
	    	 int randomTier;
	    	 int count = 6; // number of remaining slots
	    	 
	    	 // Ensure 5 out of 6 games show top tier
	    	 if (winTier != 1)
	    	 {
	    		 int prob = randomNumber(0, 5);
	    		 if (prob < 5)
	    		 {
	    			 insertAtOrNext(randomNumber(0,8), topTier, gameSlots.length);
	    			 timesUsed[topTier - 1]++;
	    			 count--;
	    		 }
	    	 }
	    	 
	    	 // Now pick 6 random tiers from remaining possibilities, if one remaining tier is picked twice, remove it
	    	 for (int i = 0; i < count; i++)
	    	 {
	    		 randomIndex = randomNumber(0, tiers.size() - 1);
	    		 randomTier = (Integer)tiers.get(randomIndex);
	    		 insertAtOrNext(0, randomTier, gameSlots.length);
	    		 timesUsed[randomTier - 1]++;
	    		 if (timesUsed[randomTier - 1] == 2)
	    		 {
	    			 tiers.remove(randomIndex);
	    		 }
	    	 }
    	 }
    	 
    	 return generateXml(gameOutcome);
     }
     
     private void processInstantWin(int winTier)
     {
    	 tiersLeft = Arrays.asList(gameTiers);
    	 
    	 // Insert winTier randomly once in slot 7-9
    	 insertAtOrNext(randomNumber(6,8), winTier, gameSlots.length);
    	 
    	 // Remove win tier
    	 tiersLeft.remove(winTier - 1);
    	 
    	 int randomIndex;
    	 int randomTier;
    	 int count = 8; // number of remaining slots
    	 
    	 // Ensure 5 out of 6 games show top tier
    	 if (winTier != 1)
    	 {
    		 int prob = randomNumber(0, 5);
    		 if (prob < 5)
    		 {
    			 insertAtOrNext(randomNumber(0,8), topTier, gameSlots.length);
    			 timesUsed[topTier - 1]++;
    			 count--;
    		 }
    	 }
    	 
    	 // Now pick 8 random tiers from remaining possibilities, if one remaining tier is picked twice, remove it
    	 for (int i = 0; i < count; i++)
    	 {
    		 randomIndex = randomNumber(0, tiersLeft.size() - 1);
    		 randomTier = (Integer)tiersLeft.get(randomIndex);
    		 insertAtOrNext(0, randomTier, gameSlots.length);
    		 timesUsed[randomTier - 1]++;
    		 if (timesUsed[randomTier - 1] == 2)
    		 {
    			 tiersLeft.remove(randomIndex);
    		 }
    	 }
     }

     /**
      * Process the losing outcome and returns the generated XML ticket
      * @param gameOutcome
      * @return String  XML formatted ticket string to be read by Flash instance
      */
     private String processLoseOutcome(GameOutcome gameOutcome)
     {
    	 tiersLeft = Arrays.asList(gameTiers);
    	 
    	 // Remove auto-win tier (6) for losing games
    	 tiersLeft.remove(5);
    	 
    	 // Pick 2 almost win tiers
    	 int random1 = randomNumber(0, tiersLeft.size() - 1);
    	 int almost1 = (Integer)tiersLeft.get(random1);
    	 // Insert first almost tier twice randomly in slots 1 - 8
    	 insertAtOrNext(randomNumber(0,7), almost1, gameSlots.length - 1);
    	 insertAtOrNext(randomNumber(0,7), almost1, gameSlots.length - 1);
    	 tiersLeft.remove(random1);

	 	 int random2 = randomNumber(0, tiersLeft.size() - 1);
    	 int almost2 = (Integer)tiersLeft.get(random2);
    	 // Insert almost tier twice randomly in slots 1 - 8
    	 insertAtOrNext(randomNumber(0,7), almost2, gameSlots.length - 1);
    	 insertAtOrNext(randomNumber(0,7), almost2, gameSlots.length - 1);
    	 tiersLeft.remove(random2);
    	 
    	 timesUsed[almost1 - 1] = 2;
    	 timesUsed[almost2 - 1] = 2;
    	 
    	 removeRepeatLevels();

    	 int randomIndex;
    	 int randomTier;
    	 int count = 5; // number of remaining slots
    	 
    	 // Ensure 5 out of 6 games show top tier
    	 if (almost1 != 1 && almost2 != 1)
    	 {
    		 int prob = randomNumber(0, 5);
    		 if (prob < 5)
    		 {
    			 insertAtOrNext(randomNumber(0,8), topTier, gameSlots.length);
    			 timesUsed[topTier - 1]++;
    			 count--;
    		 }
    	 }

    	 // Place random values in each remaining slot, ensuring that there is a reasonable spread of tier levels
    	 for (int i = 0; i < count; i++)
    	 {
    		 randomIndex = randomNumber(0, tiersLeft.size() - 1);
    		 randomTier = (Integer)tiersLeft.get(randomIndex);
    		 insertAtOrNext(0, randomTier, gameSlots.length);
    		 timesUsed[randomTier - 1]++;
    		 if (timesUsed[randomTier - 1] == 2)
    		 {
    			 tiersLeft.remove(randomIndex);
    		 }
    		 removeRepeatLevels();
    	 }

    	 return generateXml(gameOutcome);
     }

     /**
      * Inserts a value into the game slot at index; if it is filled finds the next empty slot
      * @param index  The index at which to insert the value into the game slot
      * @param value  The value to be inserted
      * @param maxLength  The maximum index to check (before looping back to start of the gameSlots array)
      * @return
      */
     private int insertAtOrNext(int index, int value, int maxLength)
     {
    	if (gameSlots[index] == 0)
    	{
    		// Insert value at index
    		gameSlots[index] = value;
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
	    			if (gameSlots[i] == 0)
	    			{
	    				gameSlots[i] = value;
	    				return i;
	    			}
    			}
    			count++;
    		}
    		return -1;
    	}
     }

     /**
      * Counts the number of times each tier level appears in the gameSlots array, and removes any tiers that appear
      * more than twice.
      */
     private void removeRepeatLevels()
     {
    	 int numH = 0;
    	 int numM = 0;
    	 int numL = 0;

    	 for (int i = 0; i < gameSlots.length; i++)
    	 {
    		 switch (tierLevels[gameSlots[i]])
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
    		 }
    	 }
    	 if (numH > 2)
    		 removeLevel(1);
    	 if (numM > 3)
    		 removeLevel(2);
    	 if (numL > 3)
    		 removeLevel(3);
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
    	String amount = prizeLevels[tier - 1];
    	String type = "";
    	if (win.equals("1"))
    	{
    		if (tier == 6)
    			type = "IWG";
    		else
    			type = "3Bins";
    	}
    	else
    	{
    		type = "lose";
    	}
    	
    	StringBuilder xml = new StringBuilder();
     	xml.append("<?xml version='1.0' encoding='UTF-8' ?>");
     	xml.append("<ticket>");
     	xml.append("<outcome prizeTier=\"" + tier + "\" amount=\""
    			+ amount +".00\" />");
     	xml.append("<params wT=\"" + win + "\" wAmount=\"" + amount + 
     			".00\" wType=\"" + type + "\" />");

     	for (int j = 0; j < gameSlots.length; j++)
     	{
     		xml.append("<turn name=\"go" + j + "\" ");
     		xml.append("prizeLabel=\"" + prizeLabels[gameSlots[j] - 1] + "\" ");
     		xml.append("charNum=\"" + charMap[gameSlots[j] - 1] + "\" />");
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

