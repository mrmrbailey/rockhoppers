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


public class Onehundredkolympic implements TicketDataGenerator {

	
	private final static int[] PURE_PRIZE_TIERS = {1,2,3,4,5,6,7,9,10,11};
											//H, M, L
	
	private final static int LOSING_TIER = 12;
	
	private final static int AUTO_TIER = 8;
	
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
		boolean isAuto = false;
		int turnWin = -1;
		BigDecimal winAmount = new BigDecimal(0.00);
		if(tier != LOSING_TIER){
			if(tier < 9){
				winAmount = prizeParams.getAmount(PURE_PRIZE_TIERS[tier-1]);
			}else{
				winAmount = prizeParams.getAmount(PURE_PRIZE_TIERS[tier-2]);
			}
			if(tier == AUTO_TIER){
				isAuto = true;
				cashAmounts = generateAutoWin();
				turnWin = cashAmounts.indexOf("AUTO");
				winAmount = prizeParams.getAmount(AUTO_TIER);
			}else{
				 cashAmounts = generateWin(tier);
				 turnWin = cashAmounts.lastIndexOf(winAmount.toString());
			}
		}else{
			cashAmounts = generateLose();
		}
		return appendXML(tier, cashAmounts, gameOutcome, isAuto, turnWin, winAmount);
	}
	

	private ArrayList<String> generateWin(int tier) {
		ArrayList<BigDecimal> ca = new ArrayList<BigDecimal>();
		ArrayList<String> cam = new ArrayList<String>();
		for(int i = 0; i < PURE_PRIZE_TIERS.length; i++){
			ca.add(prizeParams.getAmount(PURE_PRIZE_TIERS[i]));
		}		
		if(tier < 9){
			tier--;
		}else{
			tier -= 2;
		}
		String winAmount = ca.get(tier).toString();
		cam.add(winAmount);
		cam.add(winAmount);
		ca.remove(tier);
		int j = ca.size();
		for(int i = 0; i < j; i ++){
			ca.add(ca.get(i));
		}
		int extras = 6;
		if(tier != 0){
			int ran = randomBetween(0, 5);
			if(ran < 5){
				cam.add(ca.get(0).toString());
				ca.remove(0);  
				extras = 5;
			}
		}
		for(int i = 0; i < extras; i++){
			Collections.shuffle(ca);
			cam.add(ca.get(0).toString());
			ca.remove(0);  
		}
		Collections.shuffle(cam);
		int p = randomBetween(4,9);
		if(p == 9){
			cam.add(winAmount);
		}else{
			cam.add(p, winAmount);
		}
		return cam;
	}

	private ArrayList<String> generateLose() {
		ArrayList<BigDecimal> ca = new ArrayList<BigDecimal>();
		ArrayList<String> cam = new ArrayList<String>();
		for(int i = 0; i < PURE_PRIZE_TIERS.length; i++){
			ca.add(prizeParams.getAmount(PURE_PRIZE_TIERS[i]));
		}
		int ran = randomBetween(0, 5);
		boolean twc = false;
		int extras = 5;
		if(ran < 5){
			if(randomBetween(0, 1) == 0){
				cam.add(ca.get(0).toString());
				ca.remove(0);
				extras = 4;
			}else{
				cam.add(ca.get(0).toString());
				cam.add(ca.get(0).toString());
				twc = true;
				ca.remove(0);
			}
		}
		if(!twc){
			Collections.shuffle(ca);
			cam.add(ca.get(0).toString());
			cam.add(ca.get(0).toString());
			ca.remove(0);
		}
		Collections.shuffle(ca);
		cam.add(ca.get(0).toString());
		cam.add(ca.get(0).toString());
		ca.remove(0);
		int j = ca.size();
		for(int i = 0; i < j; i ++){
			ca.add(ca.get(i));
		}
		for(int i = 0; i < extras; i++){
			Collections.shuffle(ca);
			cam.add(ca.get(0).toString());
			ca.remove(0);
		}
		Collections.shuffle(cam);
		return cam;
	}

	private ArrayList<String> generateAutoWin() {
		ArrayList<BigDecimal> ca = new ArrayList<BigDecimal>();
		ArrayList<String> cam = new ArrayList<String>();
		for(int i = 0; i < PURE_PRIZE_TIERS.length; i++){
			ca.add(prizeParams.getAmount(PURE_PRIZE_TIERS[i]));
			ca.add(prizeParams.getAmount(PURE_PRIZE_TIERS[i]));
		}
		int extras = 8;
		int ran = randomBetween(0, 5);
		if(ran < 5){
			cam.add(ca.get(0).toString());
			ca.remove(0);  
			ca.remove(1);  
			extras = 7;
		}
		for(int i = 0; i < extras; i++){
			Collections.shuffle(ca);
			cam.add(ca.get(0).toString());
			ca.remove(0);
		}
		int j = randomBetween(4,8);
		cam.add(j, "AUTO");
		//cam.remove(0);
		return cam;
	}

	private String appendXML(int tier, ArrayList<String> winningSymbols, GameOutcome gameOutcome, boolean isAuto, int turnWin, BigDecimal winAmount) {
	    String win = gameOutcome.isWinner()?"1":"0";
	    int i = 0;
	    StringBuilder xml = new StringBuilder();
	    xml.append("<?xml version='1.0' encoding='UTF-8' ?>");
	    xml.append("<ticket>");
	    xml.append("<outcome prizeTier=\""+tier+"\" amount=\""+formatDecimal(winAmount)+ "\"/>");
	    xml.append("<params ");
		xml.append("wT=\""+win+"\" />");
		for(i = 0; i < winningSymbols.size(); i++){
			xml.append("<turn name=\"t"+i+"\" amount=\"" + winningSymbols.get(i) + "\"");
			if(i == turnWin) {
				xml.append(" win=\"1\"");
			}else {
				xml.append(" win=\"0\"");
			}
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