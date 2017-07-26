package com.camelotinteractive.game.publicreative;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Map;
import java.text.DecimalFormat;
import java.util.Collections;
import com.camelotinteractive.game.instant.GameOutcome;
import com.camelotinteractive.game.instant.TicketDataException;
import com.camelotinteractive.game.instant.TicketDataGenerator;
import com.gtech.game.PrizeParameters;


public class Hilospinner implements TicketDataGenerator {
	
	private final static int HI_LO_GUESSES = 0;
	private final static int ODD_EVEN_GUESSES = 1;
	private final static int RED = 2;
	private final static int BLUE = 3;
	private final static int GREEN = 4;
	private final static int YELLOW = 5;
	private final static int TIER_WIN = 6;
	private final static int LOSING_TIER = 20;
	
	private final static int[][][] TIER_PRIZES = {
		{},//tier0
		{{HI_LO_GUESSES, 7}},				//tier1
		{{ODD_EVEN_GUESSES, 7}},			//tier2
		{{RED, 3}},							//tier3
		{{TIER_WIN, 5},{TIER_WIN, 8}},		//tier4
		{{HI_LO_GUESSES, 6}},				//tier5
		{{TIER_WIN, 7},{TIER_WIN, 10}},		//tier6
		{{ODD_EVEN_GUESSES, 6}},			//tier7
		{{BLUE, 3}},						//tier8
		{{TIER_WIN, 10},{TIER_WIN,13}},		//tier9
		{{HI_LO_GUESSES, 5}},				//tier10
		{{TIER_WIN,13},{TIER_WIN,16}},		//tier11
		{{GREEN, 3}},						//tier12
		{{ODD_EVEN_GUESSES, 5}},			//tier13
		{{TIER_WIN, 16},{TIER_WIN,18}},		//tier14
		{{TIER_WIN, 16},{TIER_WIN, 19}},	//tier15
		{{HI_LO_GUESSES, 4}},				//tier16
		{{TIER_WIN, 18},{TIER_WIN, 19}},	//tier17
		{{YELLOW, 3}},						//tier18
		{{ODD_EVEN_GUESSES, 4}},			//tier19
		{{LOSING_TIER, 0}}					//tier20 �.00
	};
												
	private PrizeParameters prizeParams = null;

	public String getTicketData(GameOutcome gameOutcome)
		throws com.camelotinteractive.game.instant.TicketDataException {
	
	    // Check for null objects and throw errors if necessary
	    if (gameOutcome == null) {
	        throw new TicketDataException("Null GameOutcome object");
	    }
	
	    if (prizeParams == null) {
	        throw new TicketDataException("Null PrizeParameters object");
	    }		
	    
		return setVariables(gameOutcome);
	}
	
	private String setVariables(GameOutcome gameOutcome) {
		
		int tier = gameOutcome.getTierNumber();
		boolean losingTier = !gameOutcome.isWinner();
		int SPINS = 7;
		
		int hi_lo_guesses = 0;
		int odd_even_guesses = 0;
		int reds = 0;
		int blues = 0;
		int greens = 0;
		int yellows = 0;
		
		for (int[] tierPrize : TIER_PRIZES[tier]) {
			//If TIER_PRIZES[tier] is a TIER_WIN, then change tierPrize to that tier prize data.
			tierPrize = (int[]) ((tierPrize[0] == TIER_WIN) ? TIER_PRIZES[tierPrize[1]][0] : tierPrize);
			switch(tierPrize[0]) {
				case HI_LO_GUESSES:
					hi_lo_guesses += tierPrize[1];
				break;
				case ODD_EVEN_GUESSES:
					odd_even_guesses += tierPrize[1];
				break;
				case RED:
					reds += tierPrize[1];
				break;
				case BLUE:
					blues += tierPrize[1];
				break;
				case GREEN:
					greens += tierPrize[1];
				break;
				case YELLOW:
					yellows += tierPrize[1];
				break;
			}
		}

		ArrayList<Boolean> correctHilospinner = new ArrayList<Boolean>();
		ArrayList<Boolean> correctOddEven = new ArrayList<Boolean>();
		ArrayList<Integer> colours = new ArrayList<Integer>();
		
		int i = 0;
		for(i = 0; i < hi_lo_guesses; i++) {
			correctHilospinner.add(true);
		}
		for(i = 0; i < odd_even_guesses; i++) {
			correctOddEven.add(true);
		}
		if(losingTier) {
			//Add near win guesses
			int oneAwaySplit = randomBetween(0, 2);
			boolean oneAwayHilospinner = false; //Flag used to setup near-wins before penultimate go.
			boolean oneAwayOddEven = false; //Flag used to setup near-wins before penultimate go.
			
			switch(oneAwaySplit) {
			case 0:	//Add three hi-lo correct guesses and 0-2 correct odd-evens
				correctHilospinner.add(true);
				correctHilospinner.add(true);
				correctHilospinner.add(true);
				for(i = 0; i < randomBetween(1, 2); i++) {
					correctOddEven.add(true);
				}
				oneAwayHilospinner = true;
				break;
			case 1: //Add three odd-evens correct guesses and 0-2 correct hi-los
				correctOddEven.add(true);
				correctOddEven.add(true);
				correctOddEven.add(true);
				for(i = 0; i < randomBetween(1, 2); i++) {
					correctHilospinner.add(true);
				}
				oneAwayOddEven = true;
				break;
			case 2: //Add three correct guesses and 3 correct hi-los
				correctHilospinner.add(true);
				correctHilospinner.add(true);
				correctHilospinner.add(true);
				correctOddEven.add(true);
				correctOddEven.add(true);
				correctOddEven.add(true);
				oneAwayHilospinner = true;
				oneAwayOddEven = true;
				break;
			}
			
			//Add additional losing guesses to both array lists to total 7, shuffle, then add a losing go to create one-away near-wins.
			
			
			for(i = correctHilospinner.size(); i < SPINS; i++){
				if(i < 6) {
					correctHilospinner.add(false);
				}else if(!oneAwayHilospinner) {
					correctHilospinner.add(false);
				}
			}
			for(i = correctOddEven.size(); i < SPINS; i++){
				if(i < 6) {
					correctOddEven.add(false);
				}else if(!oneAwayOddEven) {
					correctOddEven.add(false);
				}				
			}
			
			Collections.shuffle(correctHilospinner);
			Collections.shuffle(correctOddEven);
			
			if(oneAwayHilospinner) {
				correctHilospinner.add(false);
			}
			if(oneAwayOddEven) {
				correctOddEven.add(false);
			}
			
			// Add two of each colour to a temporary array, shuffle and take the first 7 to generate the 7 losing colours.
			ArrayList<Integer> tempColours = new ArrayList<Integer>();
			for(i = RED; i <= YELLOW; i++) {
				tempColours.add(i);
				tempColours.add(i);
			}
			Collections.shuffle(tempColours);
			for(i = 0; i < SPINS; i++) {
				colours.add(tempColours.get(i));
			}
			
			//End result is an arraylist of correct/incorrect hilo, even, and colour based turns with near wins on penultimate goes.
		}else {
			
			//Add some extra random correct guesses if wins aren't already decided.
			if(correctHilospinner.size() == 0) {
				for(i = 0; i < randomBetween(0, 3); i++) {
					correctHilospinner.add(true);
				}			
			}
			if(correctOddEven.size() == 0) {
				for(i = 0; i < randomBetween(0, 3); i++) {
					correctOddEven.add(true);
				}			
			}
			//Add additional losing elements to each list
			for(i = correctHilospinner.size(); i < SPINS; i++){
				correctHilospinner.add(false);
			}
			for(i = correctOddEven.size(); i < SPINS; i++){
				correctOddEven.add(false);			
			}
			
			Collections.shuffle(correctHilospinner);
			Collections.shuffle(correctOddEven);
			
			ArrayList<Integer> existingColours = new ArrayList<Integer>();
			existingColours.add(reds);
			existingColours.add(blues);
			existingColours.add(greens);
			existingColours.add(yellows);
			ArrayList<Integer> tempColours = new ArrayList<Integer>();
			for(i = 0; i < existingColours.size(); i++) {
				// add two of each colour that doesn't have an associated win to a tempColour array to generate losing colours
				if(existingColours.get(i) == 0) {
					// the +2 is the offset from the integer colour ID of RED starting at 2 not 0
					tempColours.add(i + 2);
					tempColours.add(i + 2);
				}else {
					// add the winning colours to the colour arraylist
					for(int j = 0; j < existingColours.get(i); j++) {
						colours.add(i + 2);
					}
				}
			}
			Collections.shuffle(tempColours);
			
			for(i = colours.size(); i < SPINS; i++) {
				colours.add(tempColours.get(0));
				tempColours.remove(0);
			}
			
			Collections.shuffle(colours);
			
		}
		return generateMoves(correctHilospinner, correctOddEven, colours, gameOutcome);
	}
	private String generateMoves(ArrayList<Boolean> correctHilospinner,
			ArrayList<Boolean> correctOddEven, ArrayList<Integer> colours, GameOutcome gameOutcome) {
		
		int SPINS = 7;
		int i = 0;
		
		//Starting position between 16 and 20 to allow for some variation.
		int startingPosition = randomBetween(16, 20);
		//Lowest and highest will track the last low and high point in the movelist
		int lowest = startingPosition;
		int highest = startingPosition;
		
		ArrayList<int[]> moves = new ArrayList<int[]>();
		
		for(i = 0; i < SPINS; i++) {
			//Create 4 numbers, hieven, hiodd, loeven, loodd distributed away from last number so that hi will always be higher and lo will
			//always be lowest
			//Move:	hiNumber, loNumber, oddNumber, evenNumber, colour
			int[] move = new int[]{0,0,0,0,0};
			
			int maxOffset = (SPINS-(i+1)) * 2;
			ArrayList<Integer> highestPossibleMoves;
			ArrayList<Integer> lowestPossibleMoves;
			//Retrieve the available moves between the current highest position and the maximum turn/number offset from 36
			//which will still permit remaining moves
			if(highest % 2 == 0) {
				highestPossibleMoves = availableNumbers(highest+1, 36-maxOffset, false);
			}else {
				highestPossibleMoves = availableNumbers(highest+1, 36-maxOffset, true);
			}
			//Replicate but for the lowest moves.
			if(lowest % 2 == 0) {
				lowestPossibleMoves = availableNumbers(1+maxOffset, lowest, false);
			}else {
				lowestPossibleMoves = availableNumbers(1+maxOffset, lowest, true);
			}
			
			Collections.shuffle(highestPossibleMoves);
			Collections.shuffle(lowestPossibleMoves);
			//Choose a random available move.
			highest = highestPossibleMoves.get(0);
			lowest = lowestPossibleMoves.get(0);
			
			if(highest % 2 == 0) {
				move[0] = highest;
				move[1] = highest-1;
			}else {
				move[0] = highest-1;
				move[1] = highest;
			}
			if(lowest % 2 == 0) {
				move[2] = lowest;
				move[3] = lowest + 1;
			}else {
				move[2] = lowest + 1;
				move[3] = lowest;

			}
			moves.add(move);
		}

		return appendXML(startingPosition, moves, correctHilospinner, correctOddEven, colours, gameOutcome);
	}
	private ArrayList<Integer> availableNumbers(int low, int high, Boolean odd){
		ArrayList<Integer> numbers = new ArrayList<Integer>();
		for(int i = low; i < high; i++) {
			if(i % 2 == 0) {
				if(!odd) {
					numbers.add(i);
				}
			}else {
				if(odd) {
					numbers.add(i);
				}
			}
		}
		return numbers;
	}
	
	private String appendXML(int startingPosition, ArrayList<int[]> moves, ArrayList<Boolean> correctHilospinner, ArrayList<Boolean> correctOddEven, ArrayList<Integer> colours, GameOutcome gameOutcome) {
	    String win = gameOutcome.isWinner()?"1":"0";
	    int i = 0;
	    StringBuffer xml = new StringBuffer();
	    xml.append("<?xml version='1.0' encoding='UTF-8' ?>");
	    xml.append("<ticket>");
	    xml.append("<outcome prizeTier=\""+gameOutcome.getTierNumber()+"\" amount=\""+formatDecimal(prizeParams.getAmount(gameOutcome.getTierNumber()))+ "\"/>");
	    xml.append("<params ");
		xml.append("wT=\""+win+"\" red=\"0\" blue=\"1\" green=\"2\" yellow=\"3\" startingPosition=\"" + startingPosition + "\" startingColour=\"" + randomBetween(0,3) + "\"");		
		xml.append("/>");
		
		for(i = 0; i < moves.size(); i++){
			int hiEven;
			int hiOdd;
			int loEven;
			int loOdd;
			// Move [hiEven, hiOdd, loEven, loOdd];
			int[] move = moves.get(i);
			if(correctHilospinner.get(i)) {
				if(correctOddEven.get(i)) {
					hiEven = move[0];
					hiOdd = move[1];
					loEven = move[2];
					loOdd = move[3];
				}else {
					hiEven = move[1];
					hiOdd = move[0];
					loEven = move[3];
					loOdd = move[2];
				}
			}else {
				if(correctOddEven.get(i)) {
					hiEven = move[2];  //2
					hiOdd = move[3];	//3
					loEven = move[0];	//0
					loOdd = move[1];	//1
				}else {
					hiEven = move[3];	//3
					hiOdd = move[2];	//2
					loEven = move[1];	//1
					loOdd = move[0];	//0
				}
			}
			xml.append("<turn name=\"t"+i+"\" hilo=\"" + correctHilospinner.get(i) + "\" oddeven=\"" + correctOddEven.get(i) + "\" hiEven=\"" + hiEven + "\" hiOdd=\"" + hiOdd + "\" loEven=\"" + loEven + "\" loOdd=\"" + loOdd + "\" colour=\"" + (colours.get(i)-2) + "\"");
			
			xml.append("/>");
		}
	    xml.append("</ticket>");
	    return xml.toString();
	}

	private String formatDecimal(BigDecimal bd) {
		final String format = "0.00#";
	    DecimalFormat df = new DecimalFormat(format);
	    return df.format(bd.doubleValue());
	}
	private int randomBetween(int low, int high) {
        return (int)(Math.floor(Math.random()*((high+1)-low))+low);
    }
	public void setCustomConfig(Map map) {
		// TODO Auto-generated method stub
	}
	public void setPrizeParameters(PrizeParameters prizeParams) {
	      this.prizeParams = prizeParams;
	}

}