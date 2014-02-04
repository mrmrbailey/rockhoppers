/*
 * (c) Mark Bailey - www.rockhoppersuk.co.uk - 2011
 */
package uk.co.rockhoppersuk.tvApp.properties;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.apache.log4j.Logger;

/**
 * Base class for loading properties file.
 *
 * @author mbailey
 * @version 1.0
 */
public abstract class AbstractProperties {

    /**
     * The Properties file.
     */
    private Properties props;
    /**
     * log4j static variable.
     */
    private static Logger logger;
    /**
     * The path of the properties file.
     */
    private static String propertyFilename;

    /**
     * loads the properties file from the file system.
     */
    protected void loadProperties() {
        logger.info("entering loadProperties method.");
        InputStream in = null;
        try {
            props = new Properties();
            in = this.getClass().getClassLoader().getResourceAsStream(propertyFilename);
            props.load(in);
            in.close();
            logger.info("exiting loadProperties method. props.size: " + props.size());
        } catch (IOException ex) {
            logger.error(ex);
        } finally {
            try {
                in.close();
            } catch (IOException ex) {
                logger.error(ex);
            }
        }
    }

    /**
     * getter for the logger.
     * @return the logger.
     */
    public static Logger getLogger() {
        return logger;
    }

    /**
     * Setter for the logger.
     * @param logger The logger.
     */
    public static void setLogger(final Logger logger) {
        AbstractProperties.logger = logger;
    }

    /**
     * Setter for the name of the properties file.
     * @param propertyFilename The name of the properties file.
     */
    public static void setPropertyFilename(final String propertyFilename) {
        AbstractProperties.propertyFilename = propertyFilename;
    }

    /**
     * getter for the properties file.  If not already populated this will populate the file.
     * @return The properties file.
     */
    public Properties getProps() {
        if (props == null) {
            loadProperties();
        }
        return props;
    }
}
