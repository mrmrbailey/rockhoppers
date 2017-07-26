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


public class Diamondriches implements TicketDataGenerator {

	private final static int[] PURE_PRIZE_TIERS = {1,2,3,5,7,10,13,17,20,21,23,24};
	
	private final static int[][] PRIZE_TIERS = {{},
												{1},
												{2},
												{3},
												{7,10,10,13,17,17,17},
												{5},
												{13,13,13,13,17,20,20},
												{7},
												{13,20,20,20,20,20,21,21},
												{13,13,21,21,21,21},
												{10},
												{20,21,21,21,21,23,23,24},
												{17,21,21,23,23,24},
												{13},
												{21,21,23,23,24},
												{20,23,23,24},
												{21,21,21,21},
												{17},
												{23,23,24},
												{24,24,24,24,24},
												{20},
												{21},
												{24,24},
												{23},
												{24}
		};
	
	private final static int LOSING_TIER = 25;
	
	
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
		ArrayList<String> prizeList = new ArrayList<String>();
		ArrayList<String> allPrizes = new ArrayList<String>();
		ArrayList<String> extraPrizes = new ArrayList<String>();
		for(int i = 0; i < PURE_PRIZE_TIERS.length; i++){
			allPrizes.add(formatDecimal(prizeParams.getAmount(PURE_PRIZE_TIERS[i])));
		}
		for(int i = 0; i < 8; i++){
			prizeList.add("0");
		}

		boolean isLose = true;
		boolean gameOneWin = false;
		tier = gameOutcome.getTierNumber();
		int prizeSize = 8;
		 if(tier != LOSING_TIER){
			 isLose = false;
			 for(int j = 0; j < PRIZE_TIERS[tier].length; j++) {
				 prizeList.set(j, formatDecimal(prizeParams.getAmount((PRIZE_TIERS[tier][j]))));
				 prizeSize--;
			 }
		 }
		Collections.shuffle(prizeList);
		String gameThree = generateGameThree(prizeList);	
		ArrayList<String> gameTwo = generateGameTwo(prizeList, isLose);	
		ArrayList<String> gameOne = generateGameOne(prizeList, gameOneWin);
		if(tier != 1){
			extraPrizes.add(prizeParams.getAmount(PURE_PRIZE_TIERS[0]).toString());
			prizeSize--;
		}
		
		if(tier != 24){
			extraPrizes.add(prizeParams.getAmount(PURE_PRIZE_TIERS[11]).toString());
			prizeSize--;
		}
		Collections.shuffle(allPrizes);
		for(int i = 0; i < prizeSize; i++){
			extraPrizes.add(allPrizes.get(i));
		}
		Collections.shuffle(extraPrizes);
		int k = 0;
		for(int i = 0; i < prizeList.size(); i++){
			if(prizeList.get(i).equals("0")){
				prizeList.set(i, extraPrizes.get(k));
				k++;
			}
		}
		ArrayList<String> onePrizes = new ArrayList<String>();
		int g1WinTurn = gameOne.lastIndexOf("1");
		
		ArrayList<String> onePrizesFinal = new ArrayList<String>();
		for(int i = 0; i < PURE_PRIZE_TIERS.length; i++){
			if(!prizeList.get(7).equals(prizeParams.getAmount(PURE_PRIZE_TIERS[i]).toString())){
				onePrizes.add(formatDecimal(prizeParams.getAmount(PURE_PRIZE_TIERS[i])));
			}
		}
		Collections.shuffle(onePrizes);
		if(g1WinTurn == -1){
			onePrizes.add(0, prizeList.get(7));
		}
		int m = 0;
		for(int i = 0; i < gameOne.size(); i++){
			if(gameOne.get(i).equals("1")){
				onePrizesFinal.add(prizeList.get(7));
			}else{
				onePrizesFinal.add(onePrizes.get(m));
				m++;
			}
		}
		
		return appendXML(tier, gameOutcome, gameOne, gameTwo, gameThree, onePrizesFinal, prizeList, g1WinTurn);
	}
	

	
	private ArrayList<String> generateGameTwo(ArrayList<String> prizeList, boolean isLose) {
		ArrayList<String>game = new ArrayList<String>();
		ArrayList<String> colTotals = new ArrayList<String>();
		ArrayList<String> totalSums = new ArrayList<String>();
		colTotals.add(Integer.toString(0));
		colTotals.add(Integer.toString(0));
		colTotals.add(Integer.toString(0));
		int total = 7;
		int max = 6;
		int min = 1;
		boolean needNear = true;
		boolean lastGo = false;
		for(int i = 4; i < 7; i++){
			if(i == 6 ){
				lastGo = true;
			}
			if(prizeList.get(i) == "0"){
				game.add(createLoseAdd(needNear, colTotals, total, max, min, lastGo, totalSums));
				needNear = false;
			}else{
				game.add(createWinAdd(colTotals, total, max, min, lastGo));
			}
		}
		return game;
	}

	private String createWinAdd(ArrayList<String> colTotals, int total, int max, int min, boolean lastGo) {
		int num1;
		int num2;
		num1 = getNumOne(min, max, total, total, lastGo, colTotals);
		num2 = total - num1;
		 int col1Tot = Integer.parseInt(colTotals.get(0));
		 int col2Tot = Integer.parseInt(colTotals.get(1));
		 int col3Tot = Integer.parseInt(colTotals.get(2));
		 col1Tot += num1;
		 col2Tot += num2;
		 col3Tot += total;
		 colTotals.set(0,Integer.toString(col1Tot));
		colTotals.set(1, Integer.toString(col2Tot));
		colTotals.set(2, Integer.toString(col3Tot));
		String gameString = num1+","+num2+","+total;
		return gameString;
	}

	private String createLoseAdd(boolean needNear, ArrayList<String> colTotals, int total, int max, int min, boolean lastGo, ArrayList<String> totalSums) {
		int totalNeeded;
		int num1;
		int num2;
		int i;
		ArrayList<Number> posTotals = new ArrayList<Number>();
		if(needNear){
			for(i = (total - 2); i <= (total + 2); i++){
				if((i != total) && (totalSums.indexOf(Integer.toString(i)) == -1)){
					if(lastGo){
					 int col1Chk = Integer.parseInt(colTotals.get(2));
						if( (col1Chk + i) != total){
							posTotals.add(i);
						}
					}else{
						posTotals.add(i);
					}
				}
			}
		}else{
			int modifier = 2;
			
			if(lastGo){
					modifier = 5;
			}
			
			for(i = (min + (min * modifier)); i <= (max + (max - min)); i++){
				if((i != total) && (totalSums.indexOf(Integer.toString(i)) == -1)){
					if(lastGo){
					 int col1Chk = Integer.parseInt(colTotals.get(2));
						if( (col1Chk + i) != total){
							posTotals.add(i);
						}
					}else{
						posTotals.add(i);
					}
				}
			}
		}
		Collections.shuffle(posTotals);
		totalNeeded = (Integer)posTotals.get(0);
		totalSums.add(Integer.toString(totalNeeded));
		if(totalNeeded <= max){
			num1 = getNumOne(min, (totalNeeded - 1), totalNeeded, total, lastGo, colTotals);
		}else{
			int localMin = totalNeeded - max;
			num1 = getNumOne(localMin, (max), totalNeeded, total, lastGo, colTotals);
		}
		num2 = totalNeeded - num1;
		 int col1Tot = Integer.parseInt(colTotals.get(0));
		 int col2Tot = Integer.parseInt(colTotals.get(1));
		 int col3Tot = Integer.parseInt(colTotals.get(2));
		 col1Tot += num1;
		 col2Tot += num2;
		 col3Tot += totalNeeded;
		 colTotals.set(0,Integer.toString(col1Tot));
		colTotals.set(1, Integer.toString(col2Tot));
		colTotals.set(2, Integer.toString(col3Tot));
		String gameString = num1+","+num2+","+totalNeeded;
		return gameString;
	}

	private int getNumOne(int min, int max, int totalNeeded, int total, boolean lastGo, ArrayList<String> colTotals) {
		ArrayList<Number> posTotals = new ArrayList<Number>();
		double checker = (double)totalNeeded;
		for(int i = min; i <= max; i++){
			if((checker / i) != 2){
				if(lastGo){
					 int col1Chk = Integer.parseInt(colTotals.get(0));
					 int col2Chk = Integer.parseInt(colTotals.get(1));
						if( (col1Chk + i) != total){
							if( ((totalNeeded - i) +  col2Chk) != total){
								posTotals.add(i);
							}
						}
				}else{
					posTotals.add(i);
				}
			}
		}
		Collections.shuffle(posTotals);
		if(posTotals.size() == 0){
			System.out.println(min+" / "+max+" / "+totalNeeded);
			System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		}
		return (Integer)posTotals.get(0);
	}

	private String generateGameThree(ArrayList<String> prizeList) {
		ArrayList<Integer> syms = new ArrayList<Integer>();
		for(int i = 1; i < 13; i++){
			syms.add(i);
		}
		Collections.shuffle(syms);
		int winSymbol = syms.get(0);
		String game3 = Integer.toString(winSymbol)+",";
		syms.remove(0);
		for(int i = 0; i < 4; i++){
			if(prizeList.get(i) == "0" ){
				game3 += syms.get(0).toString();
				syms.remove(0);
			}else{
				game3 += Integer.toString(winSymbol);
			}
			if(i < 3){
				game3 += ",";
			}
		}
		return game3;
	}

	

	private ArrayList<String> generateGameOne(ArrayList<String> prizeList, boolean gameOneWin) {
		boolean winner = false;
		ArrayList<String> pos = new ArrayList<String>();
		for(int i = 0; i < 3; i++){
			pos.add("0");
		}
		for(int i = 7; i < 8; i++){
			if(prizeList.get(i) != "0"){
				winner = true;
				gameOneWin = true;
			}
		}
		if(winner){
			for(int i = 0; i < 3; i++){
				pos.add("1");
			}
		}else{
			for(int i = 0; i < 3; i++){
				pos.add("0");
			}
		}
		Collections.shuffle(pos);
		return pos;
	}

	

	
	private String tierToString(Object tier) {
		return formatDecimal(prizeParams.getAmount(Integer.parseInt((String) tier)));
	}
	
	private String appendXML(int tier, GameOutcome gameOutcome, ArrayList<String> gameOne, ArrayList<String> gameTwo, String gameThree, ArrayList<String> onePrizesFinal, ArrayList<String> prizeList, int g1WinTurn) {
	    String win = gameOutcome.isWinner()?"1":"0";
	    int i = 0;
	    String[] threeSplit = gameThree.split(",");
	    StringBuilder xml = new StringBuilder();
	    xml.append("<?xml version='1.0' encoding='UTF-8' ?>");
	    xml.append("<ticket>");
	    xml.append("<outcome prizeTier=\""+tier+"\" amount=\""+formatDecimal(prizeParams.getAmount(tier))+ "\" isWinner=\""+win+"\"/>");
	    xml.append("<params gammeThreeSymbol=\""+threeSplit[0]+"\" />");
	    xml.append("<prizeList a=\""); 
	    ArrayList<String> searchablePrizes = new  ArrayList<String>();
	    for(i = 0; i < PURE_PRIZE_TIERS.length; i++){
	    	xml.append(formatDecimal(prizeParams.getAmount(PURE_PRIZE_TIERS[i])));
	    	searchablePrizes.add(formatDecimal(prizeParams.getAmount(PURE_PRIZE_TIERS[i])));
	    	if(i < (PURE_PRIZE_TIERS.length-1)){
	    		xml.append(",");
	    	}
	    }
	    xml.append("\" />");
	    xml.append("<games>");
	    xml.append("<game name=\"matchThree\" >");
	    int pID  = -1;
	    for(i = 0; i < onePrizesFinal.size(); i++){
	    	if(i == g1WinTurn){
	    		pID = searchablePrizes.indexOf(onePrizesFinal.get(i));
	    	}else{
	    		pID = -1;
	    	}
			xml.append("<turn name=\"t"+i+"\" value=\"" + onePrizesFinal.get(i) + "\" prizeID=\""+pID+"\" />");
		}
	    xml.append("</game>");
	    int k = 4;
	    xml.append("<game name=\"equalsSeven\" >");
	    for(i = 0; i < 3; i++){
	    	 if(gameTwo.get(i).charAt(gameTwo.get(i).length()-1) == "7".charAt(0)){
		    		pID = searchablePrizes.indexOf(prizeList.get(k));
		    	}else{
		    		pID = -1;
		    	}
			xml.append("<turn name=\"t"+i+"\" value=\"" + gameTwo.get(i) + "-"+prizeList.get(k)+"\" prizeID=\""+pID+"\" />");
			k++;
	    }
	    xml.append("</game>");
	    xml.append("<game name=\"matchOne\" >");
	    int m = 1;
	    k = 0;
	    for(i = 0; i < 4; i++){
	    	 if(threeSplit[m].equals(threeSplit[0])){
		    		pID = searchablePrizes.indexOf(prizeList.get(k));
		    	}else{
		    		pID = -1;
		    	}
			xml.append("<turn name=\"t"+i+"\" value=\"" + threeSplit[m] + "-"+prizeList.get(k)+ "\" prizeID=\""+pID+"\" />");
			k++;
			m++;
	    }
	    xml.append("</game>");
	    xml.append("</games>");
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

}