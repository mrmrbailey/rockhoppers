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


public class Onehundredkrainbowtwtw implements TicketDataGenerator {

	private final static int[] PURE_PRIZE_TIERS = {1,2,3,4,5,6,7,8,9,10,11};
	
	private final static int LOSING_TIER = 12;
	
	
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
		ArrayList<String> cashAmounts = new ArrayList<String>();
		int tier = gameOutcome.getTierNumber();
		int turnWin = -1;
		BigDecimal winAmount = BigDecimal.ZERO;
		if(tier != LOSING_TIER){
			winAmount = prizeParams.getAmount(PURE_PRIZE_TIERS[tier-1]);
			 cashAmounts = generateWin(tier);
			 turnWin = cashAmounts.lastIndexOf(winAmount.toString());
		}else{
			cashAmounts = generateLose();
		}
		return appendXML(tier, cashAmounts, gameOutcome, turnWin, winAmount);
	}
	

	
	private ArrayList<String> generateWin(int currentTier) {
		ArrayList<BigDecimal> tempNumberList = new ArrayList<BigDecimal>();
		ArrayList<String> finalNumberList = new ArrayList<String>();
		for(int i = 0; i < PURE_PRIZE_TIERS.length; i++){
			tempNumberList.add(prizeParams.getAmount(PURE_PRIZE_TIERS[i]));
		}		
		currentTier--;
		String winAmount = tempNumberList.get(currentTier).toString();
		BigDecimal winner = tempNumberList.get(currentTier);
		tempNumberList.remove(currentTier);
		int j = tempNumberList.size();
		for(int i = 0; i < j; i ++){
			tempNumberList.add(tempNumberList.get(i));
		}
		int extras = 6;
		if(currentTier != 0){
			if(randomBetween(0,5) <= 4){
				finalNumberList.add(tempNumberList.get(0).toString());
				tempNumberList.remove(0);  
				extras--;
				int idx = tempNumberList.indexOf(winner);
				if(idx != -1){
					if(randomBetween(0,1) == 1){
						finalNumberList.add(tempNumberList.get(idx).toString());
						tempNumberList.remove(idx);  
						extras--;
					}else{
						tempNumberList.remove(idx);  
					}
				}
			}else{
				tempNumberList.remove(0); 
				int idx = tempNumberList.indexOf(winner);
				if(idx != -1){
					tempNumberList.remove(idx);  
				}
			}
		}
		for(int i = 0; i < extras; i++){
			Collections.shuffle(tempNumberList);
			finalNumberList.add(tempNumberList.get(0).toString());
			tempNumberList.remove(0);  
		}
		Collections.shuffle(finalNumberList);
		int p = randomBetween(1,100);
		if(p <= 5){
			//First 3
			finalNumberList.add(0, winAmount);
			finalNumberList.add(1, winAmount);
			finalNumberList.add(2, winAmount);
		}else if(p <= 25){
			//Last
			finalNumberList.add(randomBetween(0,6), winAmount);
			finalNumberList.add(randomBetween(0,7), winAmount);
			finalNumberList.add(winAmount);
		}else if(p <= 50){
			//4-6
			finalNumberList.add(randomBetween(0,2), winAmount);
			finalNumberList.add(randomBetween(0,2), winAmount);
			finalNumberList.add(randomBetween(3,5), winAmount);
		}else{
			//7-8
			finalNumberList.add(randomBetween(0,5), winAmount);
			finalNumberList.add(randomBetween(0,5), winAmount);
			finalNumberList.add(randomBetween(6,7), winAmount);
		}
		return finalNumberList;
	}

	private ArrayList<String> generateLose() {
		ArrayList<BigDecimal> ca = new ArrayList<BigDecimal>();
		ArrayList<String> cam = new ArrayList<String>();
		for(int i = 0; i < PURE_PRIZE_TIERS.length; i++){
			ca.add(prizeParams.getAmount(PURE_PRIZE_TIERS[i]));
		}
		Collections.shuffle(ca);
		int ran = randomBetween(1, 10);
		int pairs;
		if(ran <= 5){
			pairs = 3;
		}else if(ran <= 8){
			pairs = 4;
		}else{
			pairs = 2;
		}
		int extras = 9;
		if(randomBetween(0,5) <= 4){
			cam.add(ca.get(0).toString());
			extras--;
			if(randomBetween(0,1) == 1){
				cam.add(ca.get(0).toString());
				ca.remove(0);  
				extras--;
				pairs--;
			}else{
				ca.remove(0);  
			}
		}
		for(int i = 0; i < pairs; i++){
			cam.add(ca.get(i).toString());
			cam.add(ca.get(i).toString());
			ca.remove(i);
		}
		int others = extras - (pairs * 2);
		Collections.shuffle(ca);
		for(int i = 0; i < others; i++){
			cam.add(ca.get(i).toString());
		}
		Collections.shuffle(cam);
		return cam;
	}
	
	private String appendXML(int tier, ArrayList<String> winningSymbols, GameOutcome gameOutcome,  int turnWin, BigDecimal winAmount) {
	    String win = gameOutcome.isWinner()?"1":"0";
	    int i = 0;
	    StringBuilder xml = new StringBuilder();
	    xml.append("<?xml version='1.0' encoding='UTF-8' ?>");
	    xml.append("<ticket>");
	    xml.append("<outcome prizeTier=\""+tier+"\" amount=\""+formatDecimal(winAmount)+ "\" isWinner=\""+win+"\" />");
	    xml.append("<params  />");
	    xml.append("<prizeList  a=\""); 
	    ArrayList<BigDecimal> prizeList = new ArrayList<BigDecimal>();
	    for(i = 0; i < PURE_PRIZE_TIERS.length; i++){
			 prizeList.add(prizeParams.getAmount(PURE_PRIZE_TIERS[i]));
		 }
	    for(i = 0; i < prizeList.size(); i++){
	    	xml.append(prizeList.get(i));
	    	if(i < (prizeList.size()-1)){
	    		xml.append(",");
	    	}
	    }
	    xml.append("\" />");
	    xml.append("<games>");
	    xml.append("<game name=\"matchThree\" >");
	    for(i = 0; i < winningSymbols.size(); i++){
			xml.append("<turn name=\"t"+i+"\" value=\"" + winningSymbols.get(i) + "\"");
			if(i == turnWin) {
				xml.append(" prizeID=\""+tier+"\"");
			}else {
				xml.append(" prizeID=\"-1\"");
			}
			xml.append("/>");
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

}