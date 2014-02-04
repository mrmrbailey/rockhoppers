package com.camelotinteractive.game.publiccreative;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Map;
import java.text.DecimalFormat;
import java.util.Collections;
import com.camelotinteractive.game.instant.GameOutcome;
import com.camelotinteractive.game.instant.TicketDataException;
import com.camelotinteractive.game.instant.TicketDataGenerator;
import com.gtech.game.PrizeParameters;


public class Carsncashtwel implements TicketDataGenerator {

	private final static int[][] PRIZE_TIERS = {{},
												{1},
												{2},
												{10,10,10,10,7,7,7},
												{4},
												{10, 10},
												{18,18,18,18,14,14,14},
												{7},
												{18,18,18,18,18},
												{26,22,22,18,18,14},
												{10},
												{22,22,22,22,22},
												{26,22,22,18},
												{18,18},
												{14},
												{26,26,26,26,26},
												{31,31,30,26,26,26,26},
												{26,22,22},
												{18},
												{34,33,33,31,31,30,30},
												{30,30,30,30},
												{31,31,31,31,31,31,30},
												{22},
												{34,34,34,34,34,31,31},
												{34,33,33,31,31},
												{30,30},
												{26},
												{34,34,34,33},
												{31,31},
												{34,33,33},
												{30},
												{31},
												{34,34},
												{33},
												{34},
												{35}
												};
												
												
	private final static int[] PURE_PRIZE_TIERS = {1,2,4,7,10,14,18,22,26,30,31,33,34};
	//H, M, L
	
	private final static int LOSING_TIER = 35;
	
	private PrizeParameters prizeParams = null;
	
	private boolean isWin = false;
	private int tier;


	private int counter;

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
		
		counter = 0;
		 tier = gameOutcome.getTierNumber();
		
		String prz = prizeParams.getAmount(tier).toString();
		
		String gameOneString = "";
		String gameTwoString = "";
		
		ArrayList gameOnePrizes = new ArrayList();
		ArrayList gameTwoPrizes = new ArrayList();
		ArrayList possiblePrizes = new ArrayList();
		ArrayList pickable = new ArrayList();
		
		ArrayList winningAmounts = new ArrayList();
		ArrayList losingAmounts = new ArrayList();
		ArrayList game1Amounts = new ArrayList();
		ArrayList game1LosingAmounts = new ArrayList();
		ArrayList game2Amounts = new ArrayList();
		ArrayList game2LosingAmounts = new ArrayList();
		
		int i = 0;
		int j = 0;
		
		for(i = 0; i < PRIZE_TIERS[tier].length; i++) {
			if(tier != LOSING_TIER){
				winningAmounts.add(prizeParams.getAmount(PRIZE_TIERS[tier][i]).toString());		
			}
		}
		
		for(i = 0; i < PURE_PRIZE_TIERS.length; i++) {
			boolean isLosingAmount = true;
			for(j = 0; j < PRIZE_TIERS[tier].length; j++) {
				if(PURE_PRIZE_TIERS[i] == PRIZE_TIERS[tier][j]) {
					isLosingAmount = false;					
				}
			}
			if(isLosingAmount) {
				losingAmounts.add(prizeParams.getAmount(PURE_PRIZE_TIERS[i]).toString());
			}
		}
		
		Collections.shuffle(winningAmounts);
		Collections.shuffle(losingAmounts);
		
		for(i = 0; i < winningAmounts.size(); i++) {
			if(randomBetween(0,1) == 1){
				if(game1Amounts.size() < 3){
					game1Amounts.add(winningAmounts.get(i));
				}else{
					game2Amounts.add(winningAmounts.get(i));
				}
			}else{
				if(game2Amounts.size() < 4){
					game2Amounts.add(winningAmounts.get(i));
				}else{
					game1Amounts.add(winningAmounts.get(i));
				}
			}
		}
		
		for(i = game1Amounts.size(); i < 3; i++){
			game1LosingAmounts.add(losingAmounts.get(0));
			losingAmounts = moveToBack(losingAmounts);
		}
		
		for(i = game2Amounts.size(); i < 4; i++){
			game2LosingAmounts.add(losingAmounts.get(0));
			losingAmounts = moveToBack(losingAmounts);
		}
		
		
			gameOneString = createGameOne(game1Amounts, game1LosingAmounts);
			gameTwoString = createGame4(game2Amounts, game2LosingAmounts,gameOutcome);
		//	gameTwoString = createGameTwo(false, gameTwoPrizes);
		
		return appendXML(tier, gameOneString, gameTwoString, gameOutcome);
	}
	


	private String createGameOne(ArrayList game1Amounts, ArrayList game1LosingAmounts) {
		ArrayList symbols = new ArrayList();
		int i = 0;
		for(i = 0; i < 12; i++){
			symbols.add(Integer.toString(i));
		}
		int winSymbol = randomBetween(0,(symbols.size()-1));
		String returnString = "game1Symbol=\""+symbols.get(winSymbol)+"\" game1=\"";
		symbols.remove(winSymbol);
		Collections.shuffle(symbols);
		if(game1Amounts.size() == 3){
			for(i = 0; i < 3; i++){
				returnString += winSymbol+"|"+game1Amounts.get(i)+",";
			}
		}else if(game1Amounts.isEmpty()){
			for(i = 0; i < 3; i++){
				returnString += symbols.get(i)+"|"+game1LosingAmounts.get(i)+",";
			}
		}else{
			returnString += symbols.get(0)+"|"+game1LosingAmounts.get(0)+",";
			if(game1Amounts.size() == 2){
				returnString += winSymbol+"|"+game1Amounts.get(0)+",";
				returnString += winSymbol+"|"+game1Amounts.get(1)+",";
			}else{
				if(randomBetween(0, 1) == 0){
					returnString += winSymbol+"|"+game1Amounts.get(0)+",";
					returnString += symbols.get(1)+"|"+game1LosingAmounts.get(1)+",";
				}else{
					returnString += symbols.get(1)+"|"+game1LosingAmounts.get(1)+",";
					returnString += winSymbol+"|"+game1Amounts.get(0)+",";	
				}
			}
		}
		returnString = returnString.substring(0, returnString.length()-1);
		returnString += "\"";
		return returnString;
	}
	
	private String createGameTwo(boolean isWin, ArrayList gameTwoPrizes) {
		ArrayList theirSpeed = new ArrayList();
		ArrayList yourSpeed = new ArrayList();
		ArrayList usedSpeeds = new ArrayList();
		ArrayList allSpeeds = new ArrayList();
		ArrayList order = new ArrayList();
		for(int i = 0; i < 4; i++){
			order.add(Integer.toString(i));
		}
		Collections.shuffle(order);
		String[][] grid = new String[4][2];
		String returnString = "";
		int theirInt = 0;
		int yourInt = 0;
		int diff = 0;
		int winCheck = -1;
		for(int i = 30; i < 66; i++){
			yourSpeed.add(Integer.toString(i));
			allSpeeds.add(Integer.toString(i));
		}
		for(int i = 30; i < 65; i++){
			theirSpeed.add(Integer.toString(i));
		}
		if(isWin){
			theirInt = randomBetween(0, (theirSpeed.size()-6));
			yourInt = randomBetween(theirInt+5, (yourSpeed.size()-1));
			diff = 10;
		}else{
			theirInt = randomBetween(9, (theirSpeed.size()-1));
			yourInt = randomBetween((theirInt-9), (theirInt-1));
			diff = 4;
			winCheck = 2;
		}
		grid[0][0] = (String)yourSpeed.get(yourInt);
		grid[0][1] = (String)theirSpeed.get(theirInt);
		usedSpeeds.add(yourSpeed.get(yourInt));
		usedSpeeds.add(theirSpeed.get(theirInt));
		for(int i = 0; i < 3; i++){
			theirInt = getValidSpeeds(allSpeeds, usedSpeeds);
			usedSpeeds.add(Integer.toString(theirInt));
			if(i == winCheck){
				yourInt = getLosingSpeed(usedSpeeds, theirInt, true, diff);
			}else{
				yourInt = getLosingSpeed(usedSpeeds, theirInt, false, diff);
			}
			usedSpeeds.add(Integer.toString(yourInt));
			grid[i+1][0] = Integer.toString(yourInt);
			grid[i+1][1] = Integer.toString(theirInt);
		}
		/*
		System.out.println("==========");
		for(int i = 0; i < grid.length; i++){
			System.out.println(grid[i][0]+" / "+grid[i][1]);
		}
		System.out.println("==========");
		*/
		returnString = "game2=\"";
		int k = 1;
		int j = 0;
		for(int i = 0; i < 4; i++){
			j = Integer.parseInt((String)order.get(i));
			if(j == 0){
				returnString += grid[j][0]+"|"+grid[j][1]+"|"+gameTwoPrizes.get(0)+",";
			}else{
				returnString += grid[j][0]+"|"+grid[j][1]+"|"+gameTwoPrizes.get(k)+",";
				k++;
			}
		}
		returnString = returnString.substring(0, returnString.length()-1);
		returnString += "\"";
		//System.out.println(returnString);
		return returnString;
	}
	

	private int getValidSpeeds(ArrayList allSpeeds, ArrayList usedSpeeds) {
		ArrayList valid = new ArrayList();
		for(int i= 0; i < allSpeeds.size(); i++){
			Object ind = allSpeeds.get(i);
			Object ind1;
			Object ind2;
			if((i-1) >=0){
				 ind1 = allSpeeds.get((i-1));
			}else{
				ind1  = new Object();
			}
			if((i - 2) >= 0){
				 ind2 = allSpeeds.get((i-1));
			}else{
				ind2  = new Object();
			}
			if(usedSpeeds.indexOf(ind) == -1){
				if( ( (usedSpeeds.indexOf(ind1) == -1)&&(allSpeeds.indexOf(ind1) != -1) ) ||  ( (usedSpeeds.indexOf(ind2) == -1)&&(allSpeeds.indexOf(ind2) != -1) ) ){
					valid.add(ind);
				}
			}
		}
		Collections.shuffle(valid);
		return Integer.parseInt((String)valid.get(0));
	}

	private int getLosingSpeed(ArrayList usedSpeeds, int theirInt, boolean nearWin, int diff ) {
		ArrayList validSpeeds = new ArrayList();
		boolean isUsed = false;
		for(int i = 30; i < 65; i++) {
			isUsed = false;
			 for(int j = 0; j < usedSpeeds.size(); j++) {
					if(Integer.toString(i).equals(usedSpeeds.get(j))) {
						isUsed = true;
					}
				}
			 if(nearWin){
				 if( ((theirInt - i) <= 2) && ((theirInt - i) > 0)){
					 //valid
				 }else{
					 isUsed = true;
				 }
			 }else{
				 if( ((theirInt - i) <= diff) && ((theirInt - i) > 0)){
					 //valid
				 }else{
					 isUsed = true;
				 }
			 }
			 if(!isUsed){
				 validSpeeds.add(Integer.toString(i));
			 }
		}
		//System.out.println(validSpeeds);
		Collections.shuffle(validSpeeds);
		return Integer.parseInt((String)validSpeeds.get(0));
	}
	
	
	
	
	
	
	
	
	private String createGame4(ArrayList winningAmounts, ArrayList losingAmounts, GameOutcome outcome) {
		ArrayList game = new ArrayList();
		ArrayList gameSymbols = new ArrayList();
		
		ArrayList usedSymbols = new ArrayList();
		
		boolean losingGame = false;
		int nearWin = 0;
		if(winningAmounts.isEmpty()) {
			losingGame = true;
			nearWin = randomBetween(0, 3);
		}
	
		String gameOutcome = "game2=\"";
		int i = 0;
		for(i = 0; i < 4; i++) {
			game.add(Integer.toString(i));
		}
		Collections.shuffle(game);
		Collections.shuffle(gameSymbols);		
		
		for(i = 0; i < game.size(); i++) {
			int index = Integer.parseInt((String) game.get(i));
			int yourPresent;
			if(losingGame && i == nearWin) {
				yourPresent = getValidYourPresent(usedSymbols, true, false);
			}else if (losingGame){
				yourPresent = getValidYourPresent(usedSymbols, false, false);
			}else{
				yourPresent = getValidYourPresent(usedSymbols, false, true);
			}
			usedSymbols.add(Integer.toString(yourPresent));
			int theirPresent = 0;
			
			if(index < winningAmounts.size()) {
				theirPresent = getWinningTheirPresent(usedSymbols, yourPresent);
				usedSymbols.add(Integer.toString(theirPresent));
				gameOutcome += yourPresent + "|" + theirPresent + "|" + winningAmounts.get(index);
			}else {
				if(losingGame && nearWin == i) {
					theirPresent = getLosingTheirPresent(usedSymbols, yourPresent, true);
				}else {
					theirPresent = getLosingTheirPresent(usedSymbols, yourPresent, false);
				}
				usedSymbols.add(Integer.toString(theirPresent));
				gameOutcome += yourPresent + "|" + theirPresent + "|" + losingAmounts.get(index - winningAmounts.size());
			}
			if(i != game.size() -1) {
				gameOutcome += ",";
			}else {
				gameOutcome += "\"";
			}
		}
		//System.out.println(gameOutcome);
		return gameOutcome;
		
	}
	
	
	//COMPUTER WIN
	private int getLosingTheirPresent(ArrayList usedSymbols, int yourPresent, boolean nearWin) {
		ArrayList validPresents = new ArrayList();
		for(int i = yourPresent+1; i <= 64; i++) {
			boolean used = false;
			for(int j = 0; j < usedSymbols.size(); j++) {
				if(Integer.toString(i).equals(usedSymbols.get(j))) {
					used = true;
				}
			}
			if(nearWin) {
				if( ((i - yourPresent) <= 2)  && ((i - yourPresent) > 0)) {
					//valid present
				}else {
					used = true;
				}
			}else{
				if ( (tier == LOSING_TIER) && (counter < 2) ){
					if( ((i - yourPresent) < 4)  && ((i - yourPresent) > 0)) {
						//valid present
						
					}else {
						used = true;
					}
				}else{
					if( ((i - yourPresent) <= 9)  && ((i - yourPresent) > 0)) {
						//valid present
					}else {
						used = true;
					}
				}
			}
			if(!used) {
				validPresents.add(Integer.toString(i));				
			}
		}
		if (tier == LOSING_TIER){
			counter++;
		}
		Collections.shuffle(validPresents);
		return Integer.parseInt((String) validPresents.get(0));
	}

	//PLAYER WIN
	private int getWinningTheirPresent(ArrayList usedSymbols, int yourPresent) {
		ArrayList validPresents = new ArrayList();
			for(int i = 30; i < yourPresent; i++) {
				boolean used = false;
				for(int j = 0; j < usedSymbols.size(); j++) {
					if(Integer.toString(i).equals(usedSymbols.get(j))) {
						used = true;
					}
				}
				if( ((yourPresent - i) >= 5) && ((yourPresent - i) > 0)){
					//valid
				}else{
					used = true;
				}
				if(!used) {
					validPresents.add(Integer.toString(i));				
				}
			}
			Collections.shuffle(validPresents);
			System.out.println(yourPresent+" / "+validPresents+" / "+usedSymbols);
		return Integer.parseInt((String) validPresents.get(0));
	}

	private int getValidYourPresent(ArrayList usedSymbols, boolean nearWin, boolean win) {
		int num = 30;
		if(win){
			num = 35;
		}
		ArrayList validPresents = new ArrayList();
		for(int i = num; i <= 65; i++) {
			boolean used = false;
			boolean availableBelow = false;
			boolean availableAbove = false;
			for(int j = 0; j < usedSymbols.size(); j++) {
				if(Integer.toString(i).equals(usedSymbols.get(j))) {
					used = true;
				}
			}
			if(!used) {
				//checking to see if there are available symbols below your present
				for(int j = 30; j <= i; j++) {
					if(win){
						if(j < (i - 5)) {
							boolean isAvailableBelow = true;
							for(int k = 0; k < usedSymbols.size(); k++) {
								if(Integer.toString(j).equals(usedSymbols.get(k))) {
									isAvailableBelow = false;
								}
							}
							if(isAvailableBelow) {
								availableBelow = true;
							}
						}
					}else{
						if(j < (i - 2)) {
							boolean isAvailableBelow = true;
							for(int k = 0; k < usedSymbols.size(); k++) {
								if(Integer.toString(j).equals(usedSymbols.get(k))) {
									isAvailableBelow = false;
								}
							}
							if(isAvailableBelow) {
								availableBelow = true;
							}
						}
					}
				}
				for(int j = i; j <= 64; j++) {
					if(j > i) {
						boolean isAvailableAbove = true;
						for(int k = 0; k < usedSymbols.size(); k++) {
							if(Integer.toString(j).equals(usedSymbols.get(k))) {
								isAvailableAbove = false;
							}
						}
						if(isAvailableAbove) {
							if(nearWin) {
								if((j - 2) <= i) {
									availableAbove = true;
								}
							}else {
								availableAbove = true;
							}
						}
					}
				}
				if(!used && availableBelow && availableAbove) {
					validPresents.add(Integer.toString(i));				
				}
			}
		}
		Collections.shuffle(validPresents);
		return Integer.parseInt((String) validPresents.get(0));
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	private String appendXML(int tier, String game1, String game2, GameOutcome gameOutcome) {
		String win = gameOutcome.isWinner()?"1":"0";
		StringBuffer xml = new StringBuffer();
		xml.append("<?xml version='1.0' encoding='UTF-8' ?>");
	    xml.append("<ticket>");
	    xml.append("<outcome prizeTier=\""+tier+"\" amount=\""+formatDecimal(prizeParams.getAmount(tier))+ "\"/>");
	    xml.append("<params ");
	    xml.append("wT=\""+win+"\" " + game1 + " " + game2  + " />");
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
	
	private String tierToString(Object tier) {
		return formatDecimal(prizeParams.getAmount(Integer.parseInt((String) tier)));
	}
	
	private ArrayList moveToBack(ArrayList arrayList) {
		String amount = (String) arrayList.get(0);
		arrayList.remove(0);
		arrayList.add(amount);
		return arrayList;
	}
}