package com.camelotinteractive.game.publicreative;

import java.awt.List;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Map;
import java.text.DecimalFormat;
import java.util.Collections;
import com.camelotinteractive.game.instant.GameOutcome;
import com.camelotinteractive.game.instant.TicketDataException;
import com.camelotinteractive.game.instant.TicketDataGenerator;
import com.gtech.game.PrizeParameters;


public class Fivehundredkblack implements TicketDataGenerator {

	private final static int[][] PRIZE_TIERS = {{},
												{1},
												{2},
												{7,10,10,10,10,14,18,18,20},
												{4},
												{10,10,10,14,14,14,14},
												{10,10,10,10,14,14},
												{7},
												{18,18,18,20,20,20,23,23},
												{14,18,20,20,23,23},
												{10},
												{20,20,20,22,23,24,24,24},
												{20,20,20,20,20},
												{18,20,20,23,23},
												{14},
												{22,23,24,24,24},
												{23,23,23,23},
												{20,23,23},
												{18},
												{23,23},
												{20},
												{24,24},
												{22},
												{23},
												{24}
												};
	
	

	private final static int LOSING_TIER = 25;
												
	private final static int[] PURE_PRIZE_TIERS = {1,2,4,7,10,14,18,20,22,23,24};



	private static final int GAME2_MAX_TURNS = 4;
	
	
	
	//H, M, L
	
	private PrizeParameters prizeParams = null;
	
	private int tier;

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
		
		
		/*
		loseList.add(formatDecimal(prizeParams.getAmount(1)));
		loseList.add(formatDecimal(prizeParams.getAmount(24)));
		
		
		System.out.println(loseList);
		System.out.println("=======");
		*/
		ArrayList<String> prizeList = new ArrayList<String>();
		for(int i = 0; i < 9; i++){
			prizeList.add("0");
		}
		
		
		
		boolean isLose = true;
		tier = gameOutcome.getTierNumber();
		int prizeSize = 0;
		 if(tier != LOSING_TIER){
			 isLose = false;
			 for(int j = 0; j < PRIZE_TIERS[tier].length; j++) {
				 prizeList.set(j, formatDecimal(prizeParams.getAmount((PRIZE_TIERS[tier][j]))));
				 prizeSize++;
			 }
		 }
		Collections.shuffle(prizeList);
		ArrayList<String>gameOne = generateGameOne(prizeList);
		ArrayList<String>gameTwo = generateGameTwo(prizeList, isLose);
		ArrayList<String>gameThree = generateGameThree(prizeList);
		
		ArrayList<String> temp = new ArrayList<String>();
		int losers = 10 - prizeSize;
		//System.out.println("losers A: "+losers);
		if(tier != 1){
			temp.add(formatDecimal(prizeParams.getAmount(PURE_PRIZE_TIERS[0])));
			losers--;
		}
		if(tier != 24){
			temp.add(formatDecimal(prizeParams.getAmount(PURE_PRIZE_TIERS[10])));
			losers--;
		}
		for(int i = 1; i <losers; i++){
			temp.add(formatDecimal(prizeParams.getAmount(PURE_PRIZE_TIERS[i])));
		}
		//System.out.println("losers B: "+losers);
		Collections.shuffle(temp);
		ArrayList<String> loseList = new ArrayList<String>();
		for(int i = 0; i <temp.size(); i++){
			loseList.add(temp.get(i));
		//	System.out.println("i: "+i+" / "+temp.get(i));
		}   
		
		int k = 0;
		for(int i = 0; i < prizeList.size(); i++){
			if(prizeList.get(i) == "0"){
				//System.out.println("i: "+i+" k: "+k+" / "+loseList.get(k));
				prizeList.set(i, loseList.get(k));
				k++;
			}
		}
	//	System.out.println(tier);
	//	System.out.println("=========");
		return appendXML(tier, gameOutcome, gameOne, gameTwo, gameThree, prizeList);
	}


	private String appendXML(int tier2, GameOutcome gameOutcome,ArrayList<String> gameOne, ArrayList<String> gameTwo,ArrayList<String> gameThree, ArrayList<String> prizeList) {
		String win = gameOutcome.isWinner()?"1":"0";
		StringBuilder xml = new StringBuilder();
		xml.append("<?xml version='1.0' encoding='UTF-8' ?>");
	    xml.append("<ticket>");
	    xml.append("<outcome prizeTier=\""+tier+"\" amount=\""+formatDecimal(prizeParams.getAmount(tier))+ "\"/>");
	    xml.append("<params ");
	    xml.append("wT=\""+win+"\" prizeList=\"");
	    for(int i = 0; i < prizeList.size(); i++){
	    	xml.append(prizeList.get(i));
	    	if(i < (prizeList.size()-1)){
			    xml.append(",");
	    	}
	    }
	    xml.append("\"/>");
	    for(int i = 0; i < gameOne.size(); i++){
	    	xml.append("<gameOne turn=\""+i+"\" "+gameOne.get(i)+" />");
	    }	 
	    for(int i = 0; i < gameTwo.size(); i++){
	    	xml.append("<gameTwo turn=\""+i+"\" "+gameTwo.get(i)+" />");
	    }	
	    for(int i = 0; i < gameThree.size(); i++){
	    	xml.append("<gameThree turn=\""+i+"\" "+gameThree.get(i)+" />");
	    }	
	    xml.append("</ticket>");
		return xml.toString();
	}

	
	
	private ArrayList<String> generateGameTwo(ArrayList<String> prizeList, boolean isLose) {
		ArrayList usedTotals = new ArrayList();
		ArrayList colTotals = new ArrayList();
		colTotals.add(Integer.toString(0));
		colTotals.add(Integer.toString(0));
		ArrayList numberList = new ArrayList();
		ArrayList<String>game = new ArrayList<String>();
		int[] losingTotalHolder = {0};
		int losingTurnCounter = 0;
		for(int i = 0; i < 4; i++){
			if(prizeList.get(i) == "0"){
				losingTurnCounter++;
			}
		}
		for(int i = 4; i < 8; i++){
			game.add( getGame2Go(colTotals, losingTotalHolder, i, prizeList, usedTotals, numberList, losingTurnCounter));        
		//	getGame2Go(colTotals, losingTotalHolder, i, prizeList, usedTotals, numberList, losingTurnCounter);        

		}
		
		return game;
	}


	private String getGame2Go(ArrayList colTotals, int[] losingTotal,int goesHad, ArrayList<String> prizeList, ArrayList usedTotals, ArrayList numberList, int losingTurnCounter) {
		int[] numbers = new int[2];
		
		StringBuffer xml = new StringBuffer();
		int tSym1 = 0;
		int tSym2 = 0;
		String symbols1;
		String symbols2;
		String sum;
		
		symbols1 = "num1=\"";
		symbols2 = "num2=\"";
		sum = "sum=\"";
		
		boolean lastGo = false;
		if(goesHad == 3){
			lastGo = true;
		}
		if(prizeList.get(goesHad) != "0"){
			int[] returnNumbers = getRowNumbers(Integer.parseInt((String) colTotals.get(0)), Integer.parseInt((String) colTotals.get(1)), 10, true, numberList, lastGo);
			tSym1 = returnNumbers[0];
			tSym2 = returnNumbers[1];
			symbols1 += (tSym1 + "\"");
			symbols2 += (tSym2 + "\"");
			sum += 10 + "\"";
			numbers[0] = tSym1;
			numbers[1] = tSym2;
		}else {
			
			ArrayList excluding = new ArrayList();
			excluding.add(Integer.toString(10));
			
			int flag = 0;
			
			
			if(losingTotal[0] < 2) {
				
				if(((GAME2_MAX_TURNS - (goesHad-4)) > 2 - losingTotal[0] )&&(2 < losingTurnCounter)){
					flag = randomBetween(0,1);
					if(flag == 1){
						losingTotal[0]++;
						addExcludingElements(excluding);
					}
				}else {
					losingTotal[0]++;
					addExcludingElements(excluding);
				}
				
			}
			
			int[] excludingArray = new int[excluding.size()];
			for(int i = 0; i < excluding.size(); i++){
				excludingArray[i] = Integer.parseInt(excluding.get(i).toString());
			}
			
			int[] returnNumbers = getNumbersExcluding(Integer.parseInt((String) colTotals.get(0)), Integer.parseInt((String) colTotals.get(1)),
					excludingArray, usedTotals, numberList, goesHad);
			
			
			
			tSym1 = returnNumbers[0];
			tSym2 = returnNumbers[1];
		
			symbols1 += (tSym1 + "\"");
			symbols2 += (tSym2 + "\"");
			int sumTotal = tSym1 + tSym2;
			sum += sumTotal + "\"";
			
			numbers[0] = tSym1;
			numbers[1] = tSym2;
			
			
		}
		
		int col1Total = Integer.parseInt((String) colTotals.get(0));
		int col2Total = Integer.parseInt((String) colTotals.get(1));
		
		
		col1Total += tSym1;
		col2Total += tSym2;
		
		
		colTotals.set(0, Integer.toString(col1Total));
		colTotals.set(1, Integer.toString(col2Total));
		
		
		xml.append(symbols1 + " " + symbols2 + " " + sum);
		
		
		numberList.add(numbers);
		//System.out.println(xml.toString());
		return xml.toString();
	}
	
	
	private void addExcludingElements(ArrayList excluding){
		excluding.add(Integer.toString(5));
		excluding.add(Integer.toString(6));
		excluding.add(Integer.toString(7));
		excluding.add(Integer.toString(13));
		excluding.add(Integer.toString(14));
		excluding.add(Integer.toString(15));
	}
	
	private int[] getNumbersExcluding(int col1Total, int col2Total, int[] excluding, ArrayList usedTotals, ArrayList numberList, int goesHad){
		
		ArrayList totals = new ArrayList();
		
		for(int i = 5; i < 16; i++){
			boolean flag = false;
			for(int j = 0; j < excluding.length; j++){
				
				if(excluding[j] == i){
					flag = true;
				}				
			}
			if(flag == false){
				for(int k = 0; k < usedTotals.size(); k++){
					if(Integer.parseInt(usedTotals.get(k).toString()) == i) {
						flag = true;
					}					
				}
			}
			if(goesHad == 7 && i == 5){
				flag = true;
			}
			if(flag == false){
				
				totals.add(Integer.toString(i));
			}
		}
		Collections.shuffle(totals);
		
		boolean lastGo = false;
		if(goesHad == 7){
			lastGo = true;
		}
		int[] workingRow = new int[2];
		String workingTotal = "";
		
		for(int i = 0; i < totals.size(); i++){
			
			int[] rowNumbers = getRowNumbers(col1Total, col2Total, Integer.parseInt(totals.get(i).toString()), false, numberList, lastGo);
			if(rowNumbers[0] != -1){
				workingTotal = (String) totals.get(i);
				workingRow[0] = rowNumbers[0];
				workingRow[1] = rowNumbers[1];
				i = totals.size();
			}
		}
		
		usedTotals.add(workingTotal);
		
		
		return workingRow;
	}

private int[] getRowNumbers(int col1Total, int col2Total, int total, boolean canBeSame, ArrayList numberList, boolean lastGo){
	
	int tSym1 = 0;
	int tSym2 = 0;
	int numberCeiling = 0;
	
	int numberFloor = 1;
	
	if(total < 10){
		numberCeiling = total -1;
	}else {
		numberCeiling = 9;
		numberFloor = total - 9;
	}
	
	//check if the total can be made of 2 equal numbers and expand array if so
	int totalCounter = 0;
	if(canBeSame == false){
		if(total%2 == 0){
			totalCounter = 1;
		}
	}
	
	
	int[] tSymExclude = {10 - col1Total, total - (10-col2Total)};
	if(!lastGo){
		tSymExclude[0] = 0;
		tSymExclude[1] = 0;
	}
	//add existing numbers so no duplicates can be created
	int[] excludeDuplicates = new int[tSymExclude.length + totalCounter];
	

	for(int i = 0; i < tSymExclude.length; i++){
		if(i < tSymExclude.length){
			excludeDuplicates[i] = tSymExclude[i];
		}
		
	}
	if(totalCounter == 1){
		excludeDuplicates[excludeDuplicates.length-1] = total/2;
	}

	
	tSym1 = randomBetween(numberFloor, numberCeiling, excludeDuplicates, numberList, total);
	tSym2 = total - tSym1;
	
	int[] returnNumbers = {-1,-1};
	if(tSym1 == -1){
		
	}else {
		returnNumbers[0] = tSym1;
		returnNumbers[1] = tSym2;
	}
	return returnNumbers;
}


	private ArrayList<String> generateGameThree(ArrayList<String> prizeList) {
		int theirs;
		int yours;
		ArrayList<String>game = new ArrayList<String>();
		if(prizeList.get(prizeList.size()-1) == "0"){
			theirs =  randomBetween(13, 29);
			if(randomBetween(0, 5) < 3){
				int min = theirs - 5;
				if(min < 12){
					min = 12;
				}
				yours = randomBetween(min, (theirs-1));
			}else{
				yours = randomBetween(12, (theirs-1));
			}
			game.add("yours = \""+yours+"\" theirs = \""+theirs+"\"");
		}else{
			yours = randomBetween(12, 31);
			theirs = randomBetween(10,(yours-1));
			game.add("yours = \""+yours+"\" theirs = \""+theirs+"\"");
		}
		
		return game;
	}


	

	private ArrayList<String> generateGameOne(ArrayList<String> prizeList) {
		ArrayList<String>game = new ArrayList<String>();
		ArrayList<Integer>col1 = new ArrayList<Integer>();
		ArrayList<Integer>col2 = new ArrayList<Integer>();
		for(int i = 0; i < 12; i++){
			col1.add(i);
		}
		for(int i = 0; i < 4; i++){
			int s1;
			int s2;
			Collections.shuffle(col1);
			if(prizeList.get(i) == "0" ){
				s1 = col1.get(0);
				col1.remove(0);
				s2 = col1.get(0);
				col1.remove(0);
			}else{
				s1 = col1.get(0);
				s2 = col1.get(0);
				col1.remove(0);
			}
			game.add("s1 =\""+s1+"\" s2 = \""+s2+"\"");
		}
		return game;
	}


	
	private String formatDecimal(BigDecimal bd) {
		final String format = "0.00#";
	    DecimalFormat df = new DecimalFormat(format);
	    return df.format(bd.doubleValue());
	}
	private int randomBetween(int low, int high) {
        return (int)(Math.floor(Math.random()*((high+1)-low))+low);
    }
	private int randomBetween(int low, int high, int[] excluding, ArrayList numberList, int total){
		ArrayList randomNumbers = new ArrayList();
		randomNumbers.clear();
		for(int i = low; i < high+1; i++){
			boolean flag = false;
			for(int j = 0; j < excluding.length; j++){
				if(excluding[j] == i){
					flag = true;
				}
				if(flag == false){
					for(int k = 0; k < numberList.size(); k++){
						if((total - i) == ((int[])numberList.get(k))[1]) {
							flag = true;
						}
						if(i == ((int[])numberList.get(k))[0]){
							flag = true;
						}
					}
				}
			}
			if(flag == false){
				randomNumbers.add(Integer.toString(i));
			}
		}
		Collections.shuffle(randomNumbers);

		int returnedValue = 0;
		if(randomNumbers.size() == 0){
			returnedValue = -1;
		}else {
			returnedValue = Integer.parseInt((String) randomNumbers.get(0));
		}
		return returnedValue;
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
}