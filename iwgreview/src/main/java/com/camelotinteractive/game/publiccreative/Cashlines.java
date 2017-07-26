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


public class Cashlines implements TicketDataGenerator {

	private final static int[] PURE_PRIZE_TIERS = {1, 2, 3, 4, 5, 6, 7, 8, 10, 11, 12, 13};
											//H, M, L
	
	private final static int CORNERS_TIER = 9;
	private final static int LOSING_TIER = 14;
	
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
		
		//Choose a winning line, 0 to 11
		//Create grid
		
		//Create symbols to use
		int[] symbols = new int[9];
		int extraSymbols = 9;
		int i = 0;
		int j = 0;
		int tier = gameOutcome.getTierNumber();
			
		boolean losingGame = false;
		boolean cornersGame = false;
		
		if(tier == LOSING_TIER) {
			losingGame = true;
		}
		if(tier == CORNERS_TIER) {
			cornersGame = true;
		}
		
		//Create random 36 symbols used in game
		ArrayList shuffledSymbols = new ArrayList();
		for(i = 0; i < 9; i++) {
			symbols[i] = 3;
			shuffledSymbols.add(Integer.toString(i));
		}
		for(i = 0; i < extraSymbols; i++) {
			Collections.shuffle(shuffledSymbols);
			symbols[Integer.parseInt((String) shuffledSymbols.get(0))]++;
			if(symbols[Integer.parseInt((String) shuffledSymbols.get(0))] == 5){
				shuffledSymbols.remove(0);
			}
		}
		
		int winningLine = randomBetween(0,11);
		int numWinningSymbols = randomBetween(3, 6);

		ArrayList revealedSymbols = new ArrayList();
		ArrayList unrevealedSymbols = new ArrayList();
		
		int[] usedSymbols = new int[6];
		
		ArrayList winningLineSymbols = new ArrayList(6);
		
		ArrayList gameSymbols = new ArrayList(9);
		ArrayList winningSymbols = new ArrayList();
		
		for(i = 0; i < 9; i++) {
			gameSymbols.add(Integer.toString(i));
		}
		
		Collections.shuffle(gameSymbols);
		
		for(i = 0; i < 9; i++) {
			if(!losingGame) {
				if(!cornersGame) {
					if(i < numWinningSymbols) {
						winningSymbols.add(gameSymbols.get(0));
						if(i < 6 - numWinningSymbols) {
							winningLineSymbols.add(gameSymbols.get(0));
							winningLineSymbols.add(gameSymbols.get(0));
							symbols[Integer.parseInt((String) gameSymbols.get(0))] -= 2;
						}else {
							winningLineSymbols.add(gameSymbols.get(0));
							symbols[Integer.parseInt((String) gameSymbols.get(0))] --;
						}
					}
				}
			}
			if(i < 6) {
				//revealedSymbols[i] = Integer.parseInt((String) gameSymbols.get(0));
				for(j = 0; j < symbols[Integer.parseInt((String) gameSymbols.get(0))]; j++) {
					revealedSymbols.add(gameSymbols.get(0));
				}
				usedSymbols[i] = Integer.parseInt((String) gameSymbols.get(0));
			}else {
				//unrevealedSymbols[i - 6] = Integer.parseInt((String) gameSymbols.get(0));
				for(j = 0; j < symbols[Integer.parseInt((String) gameSymbols.get(0))]; j++) {
					unrevealedSymbols.add(gameSymbols.get(0));
				}
			}
			gameSymbols.remove(0);
		}
		
		int[][] grid = new int[6][6];
		int[][] winLoseGrid = new int[6][6];
		int[][] untouchableObjects = new int[6][6];
		
		for(i = 0; i < grid.length; i++) {
			for(j = 0; j < grid[i].length; j++) {
				grid[i][j] = -1;
				winLoseGrid[i][j] = -1;
			}
		}
		
		if(!losingGame && !cornersGame) {
			for(i = 0; i < winningLineSymbols.size(); i++) {
				symbols[Integer.parseInt((String) winningLineSymbols.get(i))] --;
			}

			Collections.shuffle(winningLineSymbols);
			
			if(winningLine < 6) {
				//Horizontal win
				for(i = 0; i < 6; i++) {
					grid[winningLine][i] = Integer.parseInt((String) winningLineSymbols.get(i));
					untouchableObjects[winningLine][i] = 1;
					winLoseGrid[winningLine][i] = 1;
				}
			}else {
				//Vertical win
				for(i = 0; i < 6; i++) {
					grid[i][winningLine-6] = Integer.parseInt((String) winningLineSymbols.get(i));
					untouchableObjects[i][winningLine-6] = 1;
					winLoseGrid[i][winningLine-6] = 1;
				}
			}
		}else if(losingGame) {
			ArrayList nearWinSymbols = new ArrayList();
			
			nearWinSymbols.add(Integer.toString(usedSymbols[0]));
			nearWinSymbols.add(Integer.toString(usedSymbols[0]));
			nearWinSymbols.add(Integer.toString(usedSymbols[1]));
			nearWinSymbols.add(Integer.toString(usedSymbols[1]));
			nearWinSymbols.add(Integer.toString(usedSymbols[2]));
			
			Collections.shuffle(nearWinSymbols);
			
			int randomLosingPosition = randomBetween(0,5);
			
			nearWinSymbols.add(randomLosingPosition, unrevealedSymbols.get(0));
			
			if(winningLine < 6) {
				//Horizontal win
				for(i = 0; i < 6; i++) {
					grid[winningLine][i] = Integer.parseInt((String) nearWinSymbols.get(i));
					untouchableObjects[winningLine][i] = 1;
					symbols[Integer.parseInt((String) nearWinSymbols.get(i))] --;
					if(i == randomLosingPosition) {
						winLoseGrid[winningLine][i] = 0;
					}else {
						winLoseGrid[winningLine][i] = 1;
					}
				}
			}else {
				//Vertical win
				for(i = 0; i < 6; i++) {			
					grid[i][winningLine-6] = Integer.parseInt((String) nearWinSymbols.get(i));
					untouchableObjects[i][winningLine-6] = 1;
					symbols[Integer.parseInt((String) nearWinSymbols.get(i))] --;
					if(i == randomLosingPosition) {
						winLoseGrid[i][winningLine-6] = 0;
					}else {
						winLoseGrid[i][winningLine-6] = 1;
					}
				}
				
			}
			
			for(i = 0; i < nearWinSymbols.size(); i++) {
				boolean removed = false;
				for(j = 0; j < revealedSymbols.size(); j++) {
					if(nearWinSymbols.get(i).equals(revealedSymbols.get(j)) && removed == false){
						removed = true;
						revealedSymbols.remove(j);
						j--;
					}
				}
				removed = false;
				for(j = 0; j < unrevealedSymbols.size(); j++) {
					if(nearWinSymbols.get(i).equals(unrevealedSymbols.get(j)) && removed == false){
						removed = true;
						unrevealedSymbols.remove(j);
						j--;
					}
				}
				
			}
		}
			
		return createGame(gameOutcome, grid, winLoseGrid, revealedSymbols, unrevealedSymbols, winningLine, usedSymbols, losingGame, cornersGame, untouchableObjects);
	}

	private String createGame(GameOutcome gameOutcome, int[][] grid, int[][] winLoseGrid,
			ArrayList revealedSymbols, ArrayList unrevealedSymbols, int winningLine, int[] usedSymbols, boolean losingGame, boolean cornersGame, int[][] untouchableObjects) {
		// TODO Auto-generated method stub
		
		int i = 0;
		int j = 0;
		
		int corners = 0;
		
		boolean horizontalWin = false;
		
		Collections.shuffle(revealedSymbols);
		Collections.shuffle(unrevealedSymbols);
		
		
		untouchableObjects[0][0] = untouchableObjects[5][5] = untouchableObjects[0][5] = untouchableObjects[5][0] = 1;
		
		/*
		 * Revealed symbols are symbols that will be revealed through user interaction
		 * Unrevealed symbols are symbols that will never be revealed
		 */
				
		int tier = gameOutcome.getTierNumber();
		
		/*
		 * Create an arraylist that contains all the prize values, remove the winning prize
		 * amount and insert it into the arraylist at the winning line position
		 */
		ArrayList prizes = new ArrayList();
		for(i = 0; i < PURE_PRIZE_TIERS.length; i++){
			prizes.add(prizeParams.getAmount(PURE_PRIZE_TIERS[i]).toString());
			System.out.println(prizes.get(i));
		}
		
		
		System.out.println("bob");
		prizes.clear();
		
		for(int k=0; k<prizeParams.getTierCount(); k++) {
			prizes.add(prizeParams.getAmount(k).toString());
			System.out.println(prizes.get(k));
		}
		
		Collections.shuffle(prizes);
		if(!losingGame && !cornersGame) {		
			for(i = 0; i < prizes.size(); i++) {
				if(prizes.get(i).toString().equals(prizeParams.getAmount(tier).toString())) {
					prizes.remove(i);
				}
			}
			prizes.add(winningLine, prizeParams.getAmount(tier).toString());
		}
		
		/*
		 * Add revealed symbols into the corners if it's a corner win game
		 */
		if(!losingGame && cornersGame) {
			winLoseGrid[0][0] = 1;
			winLoseGrid[0][5] = 1;
			winLoseGrid[5][0] = 1;
			winLoseGrid[5][5] = 1;

			grid[0][0] = Integer.parseInt((String) revealedSymbols.get(0));
			revealedSymbols.remove(0);
			grid[0][5] = Integer.parseInt((String) revealedSymbols.get(0));
			revealedSymbols.remove(0);
			grid[5][0] = Integer.parseInt((String) revealedSymbols.get(0));
			revealedSymbols.remove(0);
			grid[5][5] = Integer.parseInt((String) revealedSymbols.get(0));
			revealedSymbols.remove(0);
			
			winningLine = -1;
		}
		
		if(!cornersGame) {
			if(winningLine < 6) {
				horizontalWin = true;
			}else {
				winningLine -= 6;
			}
		}
		
		//Winning tiles
		//Losing tiles
		
		//Run through grid horizontally and make all non-winning lines non-winners
		//IF HORIZONTAL
		ArrayList positions = new ArrayList();
		
		if(cornersGame) {
			for(i = 1; i < 5; i++) {
				positions.add(Integer.toString(i));
			}
			Collections.shuffle(positions);
			
			positions.add(randomBetween(1, 2), "0");
			positions.add(randomBetween(1, 2), "5");
			
		}else {
			for(i = 0; i < 6; i++) {
				if(i != winningLine) {
					positions.add(Integer.toString(i));
				}
			}
			Collections.shuffle(positions);
		}
		
		j = 0;
		for(i = 0; i < 6; i++) {
			
			if(i != winningLine) {
				if(horizontalWin) {
					winLoseGrid[Integer.parseInt((String) positions.get(j))][i] = 0;
					//CHANGE TO A NON-REVEALED PRIZE!
					grid[Integer.parseInt((String) positions.get(j))][i] = Integer.parseInt((String) unrevealedSymbols.get(0));
					untouchableObjects[Integer.parseInt((String) positions.get(j))][i] = 1;
					unrevealedSymbols.remove(0);
				}else {
					winLoseGrid[i][Integer.parseInt((String) positions.get(j))] = 0;
					//CHANGE TO A NON-REVEALED PRIZE!
					grid[i][Integer.parseInt((String) positions.get(j))] = Integer.parseInt((String) unrevealedSymbols.get(0));
					untouchableObjects[i][Integer.parseInt((String) positions.get(j))] = 1;
					unrevealedSymbols.remove(0);
				}
				j++;
			}else {
				if(horizontalWin) {
					winLoseGrid[Integer.parseInt((String) positions.get(0))][i] = 0;
					//CHANGE TO A NON-REVEALED PRIZE!
					grid[Integer.parseInt((String) positions.get(0))][i] = Integer.parseInt((String) unrevealedSymbols.get(0));
					untouchableObjects[Integer.parseInt((String) positions.get(0))][i] = 1;
					unrevealedSymbols.remove(0);
				}else {
					winLoseGrid[i][Integer.parseInt((String) positions.get(0))] = 0;
					//CHANGE TO A NON-REVEALED PRIZE!
					grid[i][Integer.parseInt((String) positions.get(0))] = Integer.parseInt((String) unrevealedSymbols.get(0));
					untouchableObjects[i][Integer.parseInt((String) positions.get(0))] = 1;
					unrevealedSymbols.remove(0);
				}
			}
		}
		if(!cornersGame) {
			//Now we have a grid with a winning line and all other winning lines broken.
			//Adjust for corners
			ArrayList clearCorners = new ArrayList();
			boolean possibleCornerWin = true;
			
			if(winLoseGrid[0][0] == 1) {
				corners++;
			}else if(winLoseGrid[0][0] == -1){
				clearCorners.add("0");
			}else {
				possibleCornerWin = false;
			}
			if(winLoseGrid[5][0] == 1) {
				corners++;
			}else if(winLoseGrid[5][0] == -1){
				clearCorners.add("1");
			}else {
				possibleCornerWin = false;
			}
			if(winLoseGrid[0][5] == 1) {
				corners++;
			}else if(winLoseGrid[0][5] == -1){
				clearCorners.add("2");
			}else {
				possibleCornerWin = false;
			}
			if(winLoseGrid[5][5] == 1) {
				corners++;
			}else if(winLoseGrid[5][5] == -1){
				clearCorners.add("3");
			}else {
				possibleCornerWin = false;
			}
			
			Collections.shuffle(clearCorners);
			
			if(corners == 0) {			
				switch(Integer.parseInt(clearCorners.get(0).toString())) {
					case 0:
						winLoseGrid[0][0] = 1;
						grid[0][0] = Integer.parseInt((String) revealedSymbols.get(0));
						revealedSymbols.remove(0);
					break;
					case 1:
						winLoseGrid[5][0] = 1;
						grid[5][0] = Integer.parseInt((String) revealedSymbols.get(0));
						revealedSymbols.remove(0);
					break;
					case 2:
						winLoseGrid[0][5] = 1;
						grid[0][5] = Integer.parseInt((String) revealedSymbols.get(0));
						revealedSymbols.remove(0);
					break;
					case 3:
						winLoseGrid[5][5] = 1;
						grid[5][5] = Integer.parseInt((String) revealedSymbols.get(0));
						revealedSymbols.remove(0);
					break;
				}	
				clearCorners.remove(0);
			}
			
			if(possibleCornerWin) {
				//Add an empty corner so as not to create a win
				switch(Integer.parseInt(clearCorners.get(0).toString())) {
					case 0:
						winLoseGrid[0][0] = 0;
						grid[0][0] = Integer.parseInt((String) unrevealedSymbols.get(0));
						unrevealedSymbols.remove(0);
					break;
					case 1:
						winLoseGrid[5][0] = 0;
						grid[5][0] = Integer.parseInt((String) unrevealedSymbols.get(0));
						unrevealedSymbols.remove(0);
					break;
					case 2:
						winLoseGrid[0][5] = 0;
						grid[0][5] = Integer.parseInt((String) unrevealedSymbols.get(0));
						unrevealedSymbols.remove(0);
					break;
					case 3:
						winLoseGrid[5][5] = 0;
						grid[5][5] = Integer.parseInt((String) unrevealedSymbols.get(0));
						unrevealedSymbols.remove(0);
					break;
				}	
			}
			//Corners adjusted
		}

		//Adjust for diagonals
		if(checkCorners(winLoseGrid[0][0], winLoseGrid[1][1], winLoseGrid[2][2], winLoseGrid[3][3], winLoseGrid[4][4], winLoseGrid[5][5])) {
			ArrayList availableTiles = getAvailableTiles(winLoseGrid[0][0], winLoseGrid[1][1], winLoseGrid[2][2], winLoseGrid[3][3], winLoseGrid[4][4], winLoseGrid[5][5]);
			Collections.shuffle(availableTiles);
			winLoseGrid[Integer.parseInt((String) availableTiles.get(0))][Integer.parseInt((String) availableTiles.get(0))] = 0;
			grid[Integer.parseInt((String) availableTiles.get(0))][Integer.parseInt((String) availableTiles.get(0))] = Integer.parseInt((String) unrevealedSymbols.get(0));
			untouchableObjects[Integer.parseInt((String) availableTiles.get(0))][Integer.parseInt((String) availableTiles.get(0))] = 1;
			unrevealedSymbols.remove(0);
		}
		if(checkCorners(winLoseGrid[0][5], winLoseGrid[1][4], winLoseGrid[2][3], winLoseGrid[3][2], winLoseGrid[4][1], winLoseGrid[5][0])) {
			ArrayList availableTiles = getAvailableTiles(winLoseGrid[0][5], winLoseGrid[1][4], winLoseGrid[2][3], winLoseGrid[3][2], winLoseGrid[4][1], winLoseGrid[5][0]);
			Collections.shuffle(availableTiles);
			winLoseGrid[Integer.parseInt((String) availableTiles.get(0))][5 - Integer.parseInt((String) availableTiles.get(0))] = 0;
			grid[Integer.parseInt((String) availableTiles.get(0))][5 - Integer.parseInt((String) availableTiles.get(0))] = Integer.parseInt((String) unrevealedSymbols.get(0));
			untouchableObjects[Integer.parseInt((String) availableTiles.get(0))][5 - Integer.parseInt((String) availableTiles.get(0))] = 1;
			unrevealedSymbols.remove(0);
		}
		
		//Put in winning, put in losing.
		ArrayList availableLocations = new ArrayList();
		for(i = 0; i < 6; i++) {
			for(j = 0; j < 6; j++) {
				if(winLoseGrid[i][j] == -1) {
					String position = i + ":" + j;
					availableLocations.add(position);
				}
			}			
		}
		
		Collections.shuffle(availableLocations);
		
		for(i = 0; i < revealedSymbols.size(); i++) {
			String position = (String) availableLocations.get(0);
			int posX = Integer.parseInt(position.substring(0, 1));
			int posY = Integer.parseInt(position.substring(2, 3));
			grid[posX][posY] = Integer.parseInt((String) revealedSymbols.get(i));
			winLoseGrid[posX][posY] = 1;
			revealedSymbols.remove(0);
			availableLocations.remove(0);
			i--;
		}
		for(i = 0; i < unrevealedSymbols.size(); i++) {
			String position = (String) availableLocations.get(0);
			int posX = Integer.parseInt(position.substring(0, 1));
			int posY = Integer.parseInt(position.substring(2, 3));
			grid[posX][posY] = Integer.parseInt((String) unrevealedSymbols.get(i));
			winLoseGrid[posX][posY] = 0;
			unrevealedSymbols.remove(0);
			availableLocations.remove(0);
			i--;
		}
		/*
		 * Check for 4 or more symbols in a row/col and swap to create
		 * rows/cols of 3 or less
		 */
		grid = checkForMultipleSymbols(grid, winLoseGrid, untouchableObjects);
		
		return appendXML(usedSymbols, grid, losingGame, cornersGame, gameOutcome, prizes);
	}
	
	private int[][] checkForMultipleSymbols(int[][] grid, int[][] winLoseGrid, int[][] untouchableObjects) {
		int i = 0;
		int j = 0;
		int k = 0;
		
		boolean swapMade = false;
		//Check rows
		for(i = 0; i < grid.length; i++) {
			int[] contents = {0,0,0,0,0,0,0,0,0};
			for(j = 0; j < grid[i].length; j++) {
				contents[grid[i][j]]++;
			}
			for(j = 0; j < contents.length; j++) {
				if(contents[j] > 3) {
					//Greater than 4 of i in this row
					//4 in a row, need to swap one in this row for one with less than 3 in.
					
					for(k = 0; k < 6; k++) {
						if(grid[i][k] == j && untouchableObjects[i][k] == 0 && !swapMade) {
							//An offending object has been found, find a different symbol to swap it with
							grid = getSwappableNumbers(grid, untouchableObjects, j, i, k, contents);
							swapMade = true;
						}
					}
				}
			}
		}
		//Check cols
		for(i = 0; i < grid.length; i++) {
			int[] contents = {0,0,0,0,0,0,0,0,0};
			for(j = 0; j < grid[i].length; j++) {
				contents[grid[j][i]]++;				
			}
			for(j = 0; j < contents.length; j++) {
				if(contents[j] > 3) {
					//Greater than 4 of i in this row
					//4 in a row, need to swap one in this row for one with less than 3 in.
					//Get the offending symbols in the column that can be swapped (untouchableObjects[][] = 0)
					for(k = 0; k < 6; k++) {
						if(grid[k][i] == j && untouchableObjects[k][i] == 0 && !swapMade) {
							//An offending object has been found, find a different symbol to swap it with
							grid = getSwappableNumbers(grid, untouchableObjects, j, k, i, contents);
							swapMade = true;
						}
					}
				}
			}
		}
		if(swapMade){
			//If a swap has been made this loop, check to see if it's created another row/col of 4
			grid = checkForMultipleSymbols(grid, winLoseGrid, untouchableObjects);
		}
		return grid;
	}
	private int[][] getSwappableNumbers(int[][] grid, int[][] untouchableObjects, int numberToSwap, int row, int col, int[] contents) {
		
		int[][] gridClone = new int[6][6];
		
		int x = 0;
		int y = 0;
		
		for(x = 0; x < grid.length; x++) {
			for(y = 0; y < grid[x].length; y++) {
				gridClone[x][y] = grid[x][y];
			}
		}
		//Add all swapable numbers to an arraylist for shuffling
		ArrayList swapables = new ArrayList();
		for(x = 0; x < 6; x++) {
			for(y = 0; y < 6; y++) {
				if(x != row && y != col) {
					if(gridClone[x][y] != numberToSwap && untouchableObjects[x][y] == 0){
						int[] gridPosition = new int[2];
						gridPosition[0] = x;
						gridPosition[1] = y;
						swapables.add(gridPosition);
					}
				}
			}
			
		}
		Collections.shuffle(swapables);
		
		x = ((int[])swapables.get(0))[0];
		y = ((int[])swapables.get(0))[1];
		
		//Swap the random swapable number with the offending grid number
		int gridXY = gridClone[x][y];
		gridClone[row][col] = gridXY;
		gridClone[x][y] = numberToSwap;
		
		return gridClone;
	}

	private ArrayList getAvailableTiles(int i, int j, int k, int l, int m, int n) {
		// TODO Auto-generated method stub
		ArrayList availableTiles = new ArrayList();
		int[] tiles = {i, j, k, l, m, n};
		for(int o = 0; o < tiles.length; o++) {
			if(tiles[o] == -1) {
				availableTiles.add(Integer.toString(o));
			}
		}
		return availableTiles;
	}

	private boolean checkCorners(int i, int j, int k, int l, int m, int n) {
		if(i == 0 || j == 0 || k == 0 || l == 0 || m == 0 || n == 0) {
			return false;
		}else {
			return true;
		}		
	}
	private String appendXML(int[] usedSymbols, int[][] grid,
			boolean losingGame, boolean cornersGame, GameOutcome gameOutcome, ArrayList prizes) {
		
		int tier = gameOutcome.getTierNumber();
	    String win = gameOutcome.isWinner()?"1":"0";
	    int i = 0;
	    int j = 0;
	   	    
	    StringBuffer xml = new StringBuffer();
	    xml.append("<?xml version='1.0' encoding='UTF-8' ?>");
	    xml.append("<ticket>");
	    xml.append("<outcome prizeTier=\""+tier+"\" amount=\""+formatDecimal(prizeParams.getAmount(tier))+ "\"/>");
	    xml.append("<params ");
		xml.append("wT=\""+win+"\" grid=\"");
		for(i = 0; i < 6; i++) {
			for(j = 0; j < 6; j++){
				xml.append(grid[i][j]);
				if(j != 5 || i != 5){
					xml.append(",");
				}
			}
		}
		xml.append("\" pArray=\"");
		for(i = 0; i < prizes.size(); i++){
			xml.append( prizes.get(i));
			if(i != prizes.size() -1){
				xml.append(",");
			}
		}
		
		xml.append("\"/>");
		
		boolean cornerWin = false;

		for(i = 0; i < usedSymbols.length; i++){
			xml.append("<turn name=\"t"+i+"\" symbol=\"" + usedSymbols[i] + "\"");
			
			for(int k = 0; k < grid.length; k++) {
				for(j = 0; j < grid[k].length; j++) {
					if(grid[k][j] == usedSymbols[i]) {
						grid[k][j] = 9;
						if(checkRowWin(grid[k])) {
							xml.append(" rowWin=\"" + k + "\"");
						}
						if(checkColumnWin(grid, j)) {
							xml.append(" columnWin=\"" + j + "\"");
						}
						
						
					}
				}
			}

			if(!cornerWin) {
				if(checkCornerWin(grid)) {
					cornerWin = true;
					xml.append(" cornerWin=\"1\"");
				}
			}
			xml.append("/>");

		}
		
	    xml.append("</ticket>");
   
	    return xml.toString();
	}

	private boolean checkCornerWin(int[][] grid) {
	
		boolean win = false;
		if(grid[0][0] == 9 && grid[0][5] == 9 && grid[5][0] == 9 && grid[5][5] == 9) {
			win = true;
		}
		return win;
	}

	private boolean checkColumnWin(int[][] grid, int columnNumber) {
		boolean win = true;
		for(int i = 0; i < grid[columnNumber].length; i++) {
			if(grid[i][columnNumber] != 9) {
				win = false;
			}
		}
		return win;
	}

	private boolean checkRowWin(int[] row) {
		boolean win = true;
		for(int i = 0; i < row.length; i++) {
			if(row[i] != 9) {
				win = false;
			}
		}
		return win;
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