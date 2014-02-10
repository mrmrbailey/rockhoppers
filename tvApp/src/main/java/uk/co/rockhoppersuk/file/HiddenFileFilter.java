/*
 * (c) Mark Bailey - www.rockhoppersuk.co.uk - 2010
 */
package uk.co.rockhoppersuk.file;

import java.io.File;
import java.io.FilenameFilter;

/**
 * A <code>java.io.FilenameFilter</code> to filter out Mac OS's .DS_Store file.
 *
 * @author mbailey
 * @version 1.0
 */
public class HiddenFileFilter implements FilenameFilter {

    /**
     * Name of the hidden Mac file.
     */
    private static final String MAC_HIDDEN_FILENAME = ".DS_Store";

    /**
     * Tests if a specified file should be included in a file list.
     *
     * @param   dir    the directory in which the file was found.
     * @param   name   the name of the file.
     * @return  <code>true</code> if and only if the name should be
     * included in the file list; <code>false</code> otherwise.
     */
    public boolean accept(final File dir, final String name) {
        return !(name.equals(MAC_HIDDEN_FILENAME));
    }
}
