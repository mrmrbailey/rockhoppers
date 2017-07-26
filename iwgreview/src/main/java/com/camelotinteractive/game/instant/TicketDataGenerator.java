package com.camelotinteractive.game.instant;

import com.gtech.game.PrizeParameters;

public interface TicketDataGenerator {

    /**
     * Method for generating ticket data
     *
     * @param outcome                  game outcome object
     * @return                         String - representation of the ticket
     *      data
     * @exception TicketDataException
     */
    String getTicketData(GameOutcome outcome)
        throws TicketDataException;


    /**
     * Method for setting prize parameters. This method should be called prior
     * to the getTicketData method to enable prize information to be sent as
     * part of the ticket data
     *
     * @param prizeParams  The new PrizeParameters value
     */
    void setPrizeParameters(PrizeParameters prizeParams);


    /**
     * Method for setting custom configuration parameters. This method is called
     * during initialization of the object. The map parameters are
     * game-specific. If there are no custom configuration parameters, this
     * method should be simply stubbed out.
     *
     * @param customConfig  custom configuration parameters
     */
    void setCustomConfig(java.util.Map customConfig);

}
