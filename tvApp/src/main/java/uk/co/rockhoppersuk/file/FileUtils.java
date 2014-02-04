/*
 * (c) Mark Bailey - www.rockhoppersuk.co.uk - 2010
 */
package uk.co.rockhoppersuk.file;

import java.io.File;
import org.apache.log4j.Logger;

/**
 * Utility Class handling any file utilities.
 *
 * @author mbailey
 * @version 1.0
 */
public final class FileUtils {

    /**
     * log4j static variable.
     */
    private static Logger logger = Logger.getLogger(FileUtils.class);

    /**
     * Utility class should not be instantiated.
     */
    private FileUtils() {
        logger.error("Attempting to instantiate Utility Class");
        throw new UnsupportedOperationException(
                "Utility Class should not be instantiated");
    }

    /**
     * Check if the File Exists, is a file and can be read.
     * @param fileName Fully qualified filename to be checked
     * @return True if file exists else false
     */
    public static boolean isFileExist(final String fileName) {
        File inputFile = new File(fileName);
        return (inputFile.exists()
                && inputFile.isFile()
                && inputFile.canRead());
    }

    /**
     * Utility method to create a directory if one does not already exist.
     * @param path Fully qualified path to be created.
     * @return true if the directory is created or already exists, otherwise false.
     */
    public static boolean createDir(final String path) {
        File dir = new File(path);
        if (!dir.exists()) {
            return dir.mkdir();
        } else {
            return true;
        }
    }
}
