/*
 * (c) Mark Bailey - www.rockhoppersuk.co.uk - 2011
 */
package uk.co.rockhoppersuk.tvApp.persistence;

import java.io.BufferedReader;

/**
 * Interface to open a supplied item be it a URL or a File.
 *
 * @author mbailey
 * @version 1.0
 */
public interface Reader {

    /**
     * Opens the supplied itemToBeRead and adds it to a reader to enable it to be read.
     * @param itemToBeRead The address / location of the Item to be read.
     * @return a <code>BufferedReader</code> containing the itemToBeRead.
     */
    BufferedReader getReader(final String itemToBeRead);
}
