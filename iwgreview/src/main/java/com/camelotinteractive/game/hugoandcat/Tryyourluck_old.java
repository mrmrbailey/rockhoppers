/**
 * 
 */
package com.camelotinteractive.game.hugoandcat;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.camelotinteractive.game.instant.GameOutcome;
import com.camelotinteractive.game.instant.TicketDataException;
import com.camelotinteractive.game.instant.TicketDataGenerator;
import com.gtech.game.PrizeParameters;

/**
 * This class generates XML tickets for the Try Your Luck game
 * complying with Camelot business logic and implementing the
 * TicketDataGenerator interface.
 * 
 * @author Ben Shimmin <bshimmin@hugoandcat.com>
 */
public class Tryyourluck_old implements TicketDataGenerator {
	
	private Boolean isWinner;
	private int tier;
	private PrizeParameters prizeparameters;
	private GameOutcome gameOutcome;
	
	/**
	 * All the prize tiers as comma separated lists of values.
	 */
	private List<String> prizeTiers = new ArrayList<String>();
	/**
	 * The end rows that we generate. The first three elements of
	 * each array are symbol numbers, and the fourth is the row's prize value.
	 */
	private List<String[]> rows = new ArrayList<String[]>(4);

	final int HIGH = 1;
	final int MID_HIGH = 2;
	final int MID = 3;
	final int LOW = 4;

	final private DecimalFormat fmt = getFormat();

	/**
	 * Get the XML for a ticket based on the GameOutcome.
	 * @return A String representing the XML of a ticket.
	 * @param GameOutcome gameOutcome
	 * @throws TicketDataException
	 */
	public String getTicketData(GameOutcome gameOutcome)
			throws com.camelotinteractive.game.instant.TicketDataException {
		
		if (gameOutcome == null) {
			throw new TicketDataException("Null GameOutcome object");
		}
		
		if (prizeparameters == null) {
			throw new TicketDataException("Null PrizeParameters object");
		}
		
		// high
		prizeTiers.add("6000");
		// medium
		prizeTiers.add("200,200,50,50");
		prizeTiers.add("500");
		prizeTiers.add("100,50,40,10");
		prizeTiers.add("200");
		prizeTiers.add("40,40,10,10");
		prizeTiers.add("100");
		prizeTiers.add("10,10,10,10");
		prizeTiers.add("20,20");
		prizeTiers.add("40");
		prizeTiers.add("5,5,5,5");
		prizeTiers.add("20");
		// low
		prizeTiers.add("5,5");
		prizeTiers.add("10");
		prizeTiers.add("2,2,1");
		prizeTiers.add("5");
		prizeTiers.add("1,1");
		prizeTiers.add("2");
		prizeTiers.add("1");
		// losing tier
		prizeTiers.add("0");
				
		isWinner = gameOutcome.isWinner();
		tier = gameOutcome.getTierNumber();
		
		this.gameOutcome = gameOutcome;
		
		if (isWinner) {
		//	winOutcome();
		} else {
		//	loseOutcome();
		}
		
		return getXML();
	}

	/**
	 * Create symbols and values for rows appropriate for a winning ticket.
	 */
	private void winOutcome() {
		String prizeTier = prizeTiers.get(tier - 1);
		
		List<Integer> skip = new ArrayList<Integer>();
		List<Integer> usedPrizes = new ArrayList<Integer>();

		if (isSingleWinTier(prizeTier)) {		
			// make one row of matches
			String[] winRow = new String[4];
			
			int num = randomNumber(0, 8);
			skip.add(num);

			winRow[0] = Integer.toString(num);
			winRow[1] = Integer.toString(num);
			winRow[2] = Integer.toString(num);
			winRow[3] = getPrizeTierTotal(prizeTier);
			
			rows.add(winRow);
			
			// and three tiers of non-matches with random prizes
			for (int i = 1; i < 4; i++) {
				String[] row = new String[4];
				
				int prizeBand = 0;
				
				// get a unique random prize from the applicable tiers that
				// doesn't match the winning prize
				while (row[3] == null || row[3].intern() == getPrizeTierTotal(prizeTier).intern()) {
					prizeBand = randomNumber(1, 4, usedPrizes);
					row[3] = getTotalFromTierBands(prizeBand);
				}
				
				usedPrizes.add(prizeBand);
				
				// generate the three rows at random, but make sure
				// each row is different
				int symbol = randomNumber(0, 8, skip);
				
				row[0] = Integer.toString(symbol);
				skip.add(symbol);
				
				row[1] = Integer.toString(randomNumber(0, 8, skip));
				row[2] = Integer.toString(randomNumber(0, 8, skip));
				
				rows.add(row);
			}
		} else {
			for (int i = 0; i < 4; i++) {
				String[] row = new String[4];
				
				switch (i) {
				case 0:
					row[3] = getPrizeByPosition(prizeTier, 0);
					break;
				case 1:
					row[3] = getPrizeByPosition(prizeTier, 1);
					break;
				case 2:
					row[3] = getPrizeByPosition(prizeTier, 2);
					break;
				case 3:
					row[3] = getPrizeByPosition(prizeTier, 3);
				}
				
				// if we have a prize on this row, create three matching symbols
				if (row[3].intern() != "0.00") {				
					int symbol = randomNumber(0, 8, skip);
					row[0] = Integer.toString(symbol);
					row[1] = Integer.toString(symbol);
					row[2] = Integer.toString(symbol);
					
					// never use this symbol again
					skip.add(symbol);
				} else {
				// create a non-matching row with a random prize from the higher tiers
				// (multiple row winners conveniently always have quite low prizes)
					int prizeBand = randomNumber(1, 2, usedPrizes);
					row[3] = getTotalFromTierBands(prizeBand);
					usedPrizes.add(prizeBand);
					
					int symbol = randomNumber(0, 8, skip);
					row[0] = Integer.toString(symbol);
					
					// make sure symbol2 and symbol3 are not the same as symbol
					// so we don't have a match
					skip.add(symbol);
					row[1] = Integer.toString(randomNumber(0, 8, skip));
					row[2] = Integer.toString(randomNumber(0, 8, skip));
				}
				rows.add(row);
			}	
		}
		
		Collections.shuffle(rows);
	}
	
	/**
	 * Create symbols and values for rows appropriate for a losing ticket.
	 */
	private void loseOutcome() {
		List<Integer> skip = new ArrayList<Integer>();
		
		// either 1 or 2 rows is a near miss
		for (int i = 0; i < randomNumber(1, 2); i++) {
			String[] row = new String[4];
						
			// first and second symbols are the same
			int symbol = randomNumber(0, 8, skip);
			row[0] = Integer.toString(symbol);
			row[1] = Integer.toString(symbol);
			
			// third symbol must be different
			int symbol3 = randomNumber(0, 8, symbol);
			row[2] = Integer.toString(symbol3);

			// never use these symbols again
			skip.add(symbol);
			skip.add(symbol3);

			rows.add(row);
		}

		// we now either have 2 or 3 rows left to fill
		for (int i = rows.size(); i < 4; i++) {
			String[] row = new String[4];
			
			// each number is now random, but skip any of the near miss pairs from above;
			// remove symbol and symbol2 after each go, but leave in symbol3 or else
			// we will run out of numbers
			int symbol = randomNumber(0, 8, skip);
			row[0] = Integer.toString(symbol);
			skip.add(symbol);
			
			int symbol2 = randomNumber(0, 8, skip);
			row[1] = Integer.toString(symbol2);
			skip.add(symbol2);
			
			int symbol3 = randomNumber(0, 8, skip);
			row[2] = Integer.toString(symbol3);
						
			rows.add(row);
		}
		
		// randomly assign prizes from the single prize tiers
		rows.get(0)[3] = getTotalFromTierBands(HIGH);
		rows.get(1)[3] = getTotalFromTierBands(MID_HIGH);
		rows.get(2)[3] = getTotalFromTierBands(MID);
		rows.get(3)[3] = getTotalFromTierBands(LOW);
		
		// shuffle so that the near misses aren't always the first 1 or 2 rows
		Collections.shuffle(rows);

		// the only possible problem we may have is 3 of symbol3 in a column (when we have
		// only one near miss); fix this simply by moving the near miss down one.  (This is
		// very unlikely to happen because of the shuffle, but not impossible.)
		while (rows.get(1)[2].intern() == rows.get(2)[2].intern() &&
				rows.get(2)[2].intern() == rows.get(3)[2].intern()) {
			Collections.swap(rows, 0, 1);
		}	
	}

	/**
	 * Write the XML and yield a String representation of it.
	 * @return String of the XML for the ticket
	 */
	private String getXML() {
		StringBuffer xml = new StringBuffer();
		xml.append("<?xml version='1.0' encoding='UTF-8' ?>\n");
		xml.append("<ticket>\n");
		xml.append("<outcome prizeTier=\"" + Integer.toString(tier)
				+ "\" amount=\"" + getPrizeTierTotal(prizeTiers.get(tier - 1))
				+ "\" isWinner=\"" + Boolean.toString(isWinner)
				+ "\" />\n");
		
		xml.append("\nIS WINNER: " + gameOutcome.isWinner());
		xml.append("\nPRIZE TYPE: " + gameOutcome.getPrizeType());
		xml.append("\nTIER NUMBER: " + gameOutcome.getTierNumber());
		xml.append("\nEXTRA PARAMS: " + gameOutcome.getExtraParameters());

		
		int i = 0;
		for (String[] row : rows) {
			xml.append("<row");
			xml.append(" rI=\"" + Integer.toString(i++) + "\"");
			xml.append(" rS=\"" + getRowSymbols(row) + "\"");
			xml.append(" rV=\"" + row[3] + "\"");
			xml.append(" rW=\"" + Boolean.toString(isRowWinner(row)) + "\"/>\n");
		}
		xml.append("</ticket>");
		
		return xml.toString();
	}
	
	/**
	 * Determine whether a tier has a single win value.
	 * @param tier The tier one wishes to assess.
	 * @return true if the tier is single win, otherwise false.
	 */
	private boolean isSingleWinTier(String tier) {
		return tier.indexOf(',') == -1;
	}

	/**
	 * Get a formatted total for a given prize tier.
	 * @param tier The tier one wishes to assess.
	 * @return A formatted String of the prize total.
	 */
	private String getPrizeTierTotal(String tier) {
		String[] prizes = tier.split(",");		
		int sum = 0;

		for (String prize : prizes) {
			sum += Integer.parseInt(prize, 10);
		}
		
		return fmt.format(sum);
	}
	
	/**
	 * Get a prize in a particular position in a given prize tier.
	 * @param tier The tier one wishes to assess.
	 * @param position The position of the prize.
	 * @return A formatted String of the prize.
	 */
	private String getPrizeByPosition(String tier, int position) {
		String[] prizes = tier.split(",");
		
		if (position > prizes.length - 1) return fmt.format(0);
				
		return fmt.format(Integer.parseInt(prizes[position], 10));
	}

	/**
	 * Get a random total from a given band of single prize tiers.
	 * @param band An int representing a prize tier band: one of HIGH, MID_HIGH, MID, or LOW.
	 * @return String of the total.
	 */
	private String getTotalFromTierBands(int band) {
		// indices of appropriate single win tiers
		int[] highTiers = {0, 1};
		int[] midHighTiers = {4, 6};
		int[] midTiers = {9, 11, 13};
		int[] lowTiers = {15, 17, 18};
		
		int[] tier;
		
		switch (band) {
		case HIGH:
			tier = highTiers;
			break;
		case MID_HIGH:
			tier = midHighTiers;
			break;
		case MID:
			tier = midTiers;
			break;
		default:
			tier = lowTiers;
		}
		
		int rand = randomNumber(0, tier.length - 1);
		
		return getPrizeTierTotal(prizeTiers.get(tier[rand]));
	}
	
	/**
	 * Condense three row symbol strings into a comma-separated list.
	 * @param row An array of Strings with (minimally) 3 values
	 * @return a String representing 
	 */
	private String getRowSymbols(String[] row) {
		return row[0] + "," + row[1] + "," + row[2];
	}
	
	/**
	 * Determine if a row is a winner.
	 * @param row An array of Strings with (minimally) 3 values
	 * @return true if the row is a winner, false if not.
	 */
	private Boolean isRowWinner(String[] row) {
		return row[0].intern() == row[1].intern() && row[1].intern() == row[2].intern();
	}
	
	/**
	 * Get a DecimalFormat to format a value string as a currency.
	 * @return a DecimalFormat
	 */
	private DecimalFormat getFormat() {
		DecimalFormat currFormat = (DecimalFormat) NumberFormat.getInstance();
		currFormat.applyPattern("#,##0.00");
		return currFormat;
	}
	
	/**
	 * Generate a random number between two integers.
	 * 
	 * @param min The minimum number in the range.
	 * @param max The maximum number in the range.
	 * @return An integer between a minimum and maximum.
	 */
	private int randomNumber(int min, int max) {
		return new Random().nextInt((max + 1) - min) + min;
	}
	
	/**
	 * Generate a random number (which isn't skip) between two integers.
	 * 
	 * @param min The minimum number in the range, inclusive.
	 * @param max The maximum number in the range, inclusive.
	 * @param skip A number to avoid.
	 * @return An integer between a minimum and maximum.
	 */
	private int randomNumber(int min, int max, int skip) {
		int num = randomNumber(min, max);
		
		return num != skip ? num : randomNumber(min, max, skip);
	}

	/**
	 * Generate a random number (which isn't one of skip) between two integers.
	 * 
	 * @param min The minimum number in the range, inclusive.
	 * @param max The maximum number in the range, inclusive.
	 * @param skip A List of numbers to avoid.
	 * @return An integer between a minimum and maximum.
	 */
	private int randomNumber(int min, int max, List<Integer> skip) {
		int num = randomNumber(min, max);
						
		return (skip.indexOf(num) == -1) ? num : randomNumber(min, max, skip);
	}

	/**
	 * The PrizeParameters provides access to prize definition for a given game
	 * 
	 * @param PrizeParameters
	 *            Interface
	 */
	public void setPrizeParameters(PrizeParameters parm1) {
		prizeparameters = parm1;
	}

	/**
	 * The setCustomConfig is a mechanism to providers additional configuration
	 * parameters
	 * 
	 * @param Map
	 *            Map of configuration parameters.
	 */
	public void setCustomConfig(Map parm1) {
		// not used
	}
	
}
