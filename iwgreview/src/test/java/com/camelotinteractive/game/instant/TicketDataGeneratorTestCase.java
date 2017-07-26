package com.camelotinteractive.game.instant;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import com.gtech.game.PrizeParameters;

public class TicketDataGeneratorTestCase {

	private GameOutcome mockGameOutcome;
	private PrizeParameters mockPrizeParameters;
	private TicketDataGenerator tdg;


	/**
	 * Set up a mock GameOutcome and Prize Parameter; 
	 * create a TicketDataGenerator for the required game.
	 * 
	 * @param tdg - the TicketDataGenerator bean for the game under test.
	 * 
	 * @throws java.lang.Exception
	 */
	protected void setUpMocks(TicketDataGenerator tdg) throws Exception {
		int tier = 1;
		int tierCount = 14;
		boolean winner = true;
		
		mockGameOutcome = mock(GameOutcome.class);
		when(mockGameOutcome.isWinner()).thenReturn(new Boolean(winner));
		when(mockGameOutcome.getTierNumber()).thenReturn(new Integer(tier));

		mockPrizeParameters = mock(PrizeParameters.class);
		for(int i=0;i<tierCount;i++){
			when(mockPrizeParameters.getAmount(i)).thenReturn(new BigDecimal(i));
		}
		when(mockPrizeParameters.getTierCount()).thenReturn(tierCount);

		this.tdg = tdg;
		tdg.setPrizeParameters(mockPrizeParameters);
	}
	
	protected String getTicketData() throws TicketDataException {
		return tdg.getTicketData(mockGameOutcome);
	}

}
