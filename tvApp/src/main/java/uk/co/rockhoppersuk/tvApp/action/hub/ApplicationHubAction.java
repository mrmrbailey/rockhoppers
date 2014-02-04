/*
 * (c) Mark Bailey - www.rockhoppersuk.co.uk - 2010
 */
package uk.co.rockhoppersuk.tvApp.action.hub;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.log4j.Logger;

/**
 * Entry point into the application.  This action supplies a Welcome message
 * and not a great deal else.
 *
 * @author mbailey
 * @version 1.0
 */
public class ApplicationHubAction extends ActionSupport {

    /**
     * serialVersionId for keeping track of changes.
     */
    public static final long serialVersionUID = 42L;
    /**
     * Provide default value for Message property.
     */
    protected static final String WELCOME_MESSAGE = "welcome.message";
    /**
     * log4j static variable.
     */
    private static Logger logger = Logger.getLogger(ApplicationHubAction.class);
    /**
     * String holding the welcome message to be displayed to the user.
     */
    private String welcomeMessage;

    /**
     * Action to set the welcome message.
     * @return SUCCESS if message set.
     * @throws Exception if something bad happens
     */
    @Override
    public String execute() throws Exception {

        logger.info("entering execute method.");
        setWelcomeMessage(getText(WELCOME_MESSAGE));
        logger.info("exiting execute method.  welcomeMessage: " + welcomeMessage);

        return SUCCESS;
    }

    /**
     * Getter for the Welcome message.
     * @return welcomeMessage the welcome message
     */
    public String getWelcomeMessage() {
        return welcomeMessage;
    }

    /**
     * Setter for the Welcome message.
     * @param welcomeMessage the welcome message
     */
    public void setWelcomeMessage(final String welcomeMessage) {
        this.welcomeMessage = welcomeMessage;
    }
}
