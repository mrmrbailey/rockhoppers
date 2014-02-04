/*
 * (c) Mark Bailey - www.rockhoppersuk.co.uk - 2011
 */
package uk.co.rockhoppersuk.tvApp.persistence.url;

import uk.co.rockhoppersuk.tvApp.persistence.Reader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import org.apache.log4j.Logger;

/**
 * A class to deal with the reading of urls.
 *
 * @author mbailey
 * @version 1.0
 */
public class UrlReader implements Reader {

    /**
     * log4j static variable.
     */
    private static Logger logger = Logger.getLogger(UrlReader.class);

    /**
     * Opens the supplied URL and adds it to a reader to enable it to be read.
     * @param urlString The address of the url.
     * @return a <code>BufferedReader</code> containing the urlString.
     */
    public BufferedReader getReader(final String urlString) {
        System.setProperty("java.net.useSystemProxies", "true");
        URL url;
        logger.info("Atempting to open: " + urlString);
        try {
            url = new URL(urlString);
            return new BufferedReader(new InputStreamReader(url.openStream()));
        } catch (MalformedURLException ex) {
            logger.error("Unable to resolve url: " + urlString);
            throw new IllegalArgumentException("Unable to resolve url: " + urlString, ex);
        } catch (IOException ex) {
            logger.error("Unable to read url: " + urlString);
            throw new IllegalArgumentException("Unable to read url: " + urlString, ex);
        }
    }
}
