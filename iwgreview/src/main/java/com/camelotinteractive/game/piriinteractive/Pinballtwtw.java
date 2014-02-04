/**
2 * Title:		Pinball Payout
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
import java.util.Map;



public class Pinballtwtw implements TicketDataGenerator {
	/**
	 * This class defines the class for generating ticket data for Pinball Payout instant
	 * tickets.  It requires implementation of a method that generates ticket data
	 * in the form of a String based on outcome data. The return string format is
	 * defined the getTicketData() method. The class also has a method for setting
	 * the prize parameters.
	 *
	 */
    private PrizeParameters prizeParams = null;

    private int[] gameSlots;
    private int[] gameRoutes;
    private int[] pockets;
    private int[] timesUsed;
    private StringBuilder prizes;

    private static final Integer[] gameTiers = {1, 2, 3, 4, 5, 6, 7};
    private static final Integer[] tierMap = {1, 2, 3, 4, 6, 7};
    private static final Integer[] routes = {0, 1, 2, 3, 4, 5};
    private static final Integer[] pocketsIndex = {0, 1, 2, 3, 4, 5};  
    
    private ArrayList tiersLeft;

    private static final String[] prizeLevels = {
    		"6000",
    		"500",
    		"25",
    		"10",
    		"3",
    		"2",
    		"1",
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
         
         gameSlots = new int[]{0, 0, 0, 0, 0, 0};
         gameRoutes = new int[]{0, 0, 0, 0, 0, 0};
         pockets = new int[]{0, 0, 0, 0, 0, 0};
                
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
    	 tiersLeft = new ArrayList(Arrays.asList(gameTiers));
    	 int ballsLeft = 6;
    	 
    	 if (winTier == 5)
    	 {
	    	 // Fill pockets for � & �
    		 pockets[5] = 2;
    		 pockets[4] = 2;
    		 ballsLeft = 2;
    		 
    		 // Remove used tiers including combo tier
    		 tiersLeft.remove(4);
    		 tiersLeft.remove(4); // remove index 4 three times, as it will keep shifting down with each remove
    		 tiersLeft.remove(4);
    	 }
    	 else
    	 {
	    	 // Fill the pockets
	    	 if (winTier > 5)
	    	 {
	    		 pockets[winTier - 2] = 2;
	    		 ballsLeft = 4;
	    	 }
	    	 else
	    	 {
	    		 pockets[winTier - 1] = (winTier == 4) ? 2 : 3;
	    		 ballsLeft = (winTier == 4) ? 4 : 3;
	    	 }
	    	 
	    	 // Remove combo tier and winTier from tiers left
	    	 tiersLeft.remove(4); // combo tier
	    	 if (winTier > 5)
	    		 tiersLeft.remove(winTier - 2);
	    	 else
	    		 tiersLeft.remove(winTier - 1);
    	 }
	    	
    	 // Pick random tiers to fill remaining goes
    	 int randomIndex;
    	 int randomTier;
    	 
    	 // Now pick random tiers from remaining possibilities, if one remaining tier is picked too much, remove it
    	 for (int i = 0; i < ballsLeft; i++)
    	 {
    		 randomIndex = randomNumber(0, tiersLeft.size() - 1);
    		 randomTier = (Integer)tiersLeft.get(randomIndex);
    		 if (randomTier < 5)
    		 {
    			 pockets[randomTier - 1]++;
    		 }
    		 else
    		 {
    			 pockets[randomTier - 2]++;
    		 }
    		 timesUsed[randomTier - 1]++;
    		 if (randomTier < 4)
    		 {
    		 	if (timesUsed[randomTier - 1] == 2)
    		 		tiersLeft.remove(randomIndex);
    		 }
    		 else
    		 {
	    		 if (timesUsed[randomTier - 1] == 1)
	    		 	tiersLeft.remove(randomIndex);
    		 }
    	 }
    	 
    	 processGame();
    	 
    	 return generateXml(gameOutcome);
     }
     
     /**
      * Process the losing outcome and returns the generated XML ticket
      * @param gameOutcome
      * @return String  XML formatted ticket string to be read by Flash instance
      */
     private String processLoseOutcome(GameOutcome gameOutcome)
     {
    	 tiersLeft = new ArrayList(Arrays.asList(gameTiers));
    	 int ballsLeft;
    	 
    	 // Remove auto-win tier (5) for losing games
    	 tiersLeft.remove(4);
    	 
    	 // Pick 1 almost win tier
    	 int random = randomNumber(0, tiersLeft.size() - 1);
    	 int almost = (Integer)tiersLeft.get(random);
    	 if (almost > 5)
    	 {
    		 pockets[almost - 2] = 1;
    		 ballsLeft = 5;
    		 timesUsed[almost - 1]++;
    	 }
    	 else
    	 {
    		 pockets[almost - 1] = (almost == 4) ? 1 : 2;
    		 ballsLeft = (almost == 4) ? 5 : 4;
    		 timesUsed[almost - 1] = (almost == 4) ? 1 : 2;
    	 }
    	 tiersLeft.remove(random);
    	 
    	 int randomIndex;
    	 int randomTier;
    	 
    	 // Place random values in each remaining pocket, ensuring that there is a reasonable spread of tier levels
    	 for (int i = 0; i < ballsLeft; i++)
    	 {
    		 randomIndex = randomNumber(0, tiersLeft.size() - 1);
    		 randomTier = (Integer)tiersLeft.get(randomIndex);
    		 if (randomTier < 5)
    		 {
    			 pockets[randomTier - 1]++;
    		 }
    		 else
    		 {
    			 pockets[randomTier - 2]++;
    		 }
    		 timesUsed[randomTier - 1]++;
    		 if (randomTier < 4)
    		 {
    		 	if (timesUsed[randomTier - 1] == 2)
    		 		tiersLeft.remove(randomIndex);
    		 }
    		 else
    		 {
	    		 if (timesUsed[randomTier - 1] == 1)
	    		 	tiersLeft.remove(randomIndex);
    		 }
    	 }
    	 
    	 processGame();

    	 return generateXml(gameOutcome);
     }
     
     /**
      * Takes the pockets used and inserts winning balls into random gameSlots to generate the game turns in a random order.
      */
     private void processGame()
     {
    	 ArrayList routesLeft = new ArrayList(Arrays.asList(routes));
    	 ArrayList pocketsLeft = new ArrayList(Arrays.asList(pocketsIndex));
    	 prizes = new StringBuilder();
    	 int randomIndex;
    	 int randomPocket;
    	 int randomIndex2;
    	 int randomRoute;
    	 int slotIndex;
    	 int balls;
    	 
    	 for (int i = 0; i < pockets.length; i++)
    	 {
    		 randomIndex = randomNumber(0, pocketsLeft.size() - 1);
    		 randomPocket = (Integer)pocketsLeft.get(randomIndex);
    		 prizes.append(prizeLevels[tierMap[randomPocket] - 1] + ",");
    		
    		 balls = pockets[randomPocket];
    		 if (balls > 0)
    		 {
    			 for (int j = 0; j < balls; j++)
    			 {
    				 slotIndex = insertAtOrNext(i, i + 1, gameSlots.length);
    				 randomIndex2 = randomNumber(0, routesLeft.size() - 1);
    	    		 randomRoute = (Integer)routesLeft.get(randomIndex2);
    	    		 gameRoutes[slotIndex] = randomRoute;
    	    		 routesLeft.remove(randomIndex2);
    			 }
    		 }
    		 pocketsLeft.remove(randomIndex);
    	 }
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
    	// Remove trailing comma from prizes
    	String pz = prizes.toString();
    	pz = pz.substring(0, pz.length() - 1);
    	
    	StringBuilder xml = new StringBuilder();
     	xml.append("<?xml version='1.0' encoding='UTF-8' ?>");
     	xml.append("<ticket>");
     	xml.append("<outcome wT=\"" + win + "\" prizeTier=\"" + tier + "\" amount=\""
    			+ amount +".00\" />");
     	xml.append("<params wT=\"" + win + "\" wAmount=\"" + amount + 
     			".00\" pz=\"" + pz + "\" />");

     	for (int j = 0; j < gameSlots.length; j++)
     	{
     		xml.append("<turn name=\"go" + j + "\" ");
     		xml.append("dropPocket=\"" + (gameSlots[j]) + "\" ");
     		xml.append("route=\"" + gameRoutes[j] + "\" />");
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

