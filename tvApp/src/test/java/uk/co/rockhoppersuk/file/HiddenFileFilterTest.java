/*
 * (c) Mark Bailey - www.rockhoppersuk.co.uk - 2010
 */
package uk.co.rockhoppersuk.file;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * A test for Hidden FileUtils.
 *
 * @author mbailey
 * @version 1.0
 */
public class HiddenFileFilterTest {

    private final static String TEST_FILE_PATH = "src/test/resources/file/";
    private static final String MAC_HIDDEN_FILENAME = ".DS_Store";
    private static final String TEST_FILENAME = "test.txt";

    public HiddenFileFilterTest() {
    }

    /**
     * Creates the hidden file.
     */
    @Before
    public void setUp() {
        File hiddenFile = new File(TEST_FILE_PATH + MAC_HIDDEN_FILENAME);
        File file = new File(TEST_FILE_PATH + TEST_FILENAME);
        try {
            hiddenFile.createNewFile();
            file.createNewFile();
        } catch (IOException ex) {
            Logger.getLogger(FileUtilsTest.class.getName()).log(Level.ERROR, null, ex);
            fail("Error creating test file");
        }

    }

    /**
     * removes the hidden file.
     */
    @After
    public void tearDown() {
        File hiddenFile = new File(TEST_FILE_PATH + MAC_HIDDEN_FILENAME);
        hiddenFile.delete();
        File file = new File(TEST_FILE_PATH + TEST_FILENAME);
        file.delete();
    }

    /**
     * Test of accept method, of class HiddenFileFilter.
     */
    @Test
    public void testAccept() {
        FilenameFilter filter = new HiddenFileFilter();
        String[] dirListingsNoFilter = new File(TEST_FILE_PATH).list();
        String[] dirListingsFilter = new File(TEST_FILE_PATH).list(filter);
        assertFalse("lists are different sizes",
                dirListingsFilter.length == dirListingsNoFilter.length);

        for (int i = 0; i < dirListingsFilter.length; i++) {
            if (dirListingsFilter[i].equals(MAC_HIDDEN_FILENAME)){
                fail("Listings should not contain " + MAC_HIDDEN_FILENAME);
            }
        }
        boolean found = false;
        for (int i = 0; i < dirListingsNoFilter.length; i++) {
            if (dirListingsNoFilter[i].equals(MAC_HIDDEN_FILENAME)){
                found = true;
            }
        }
        assertTrue("Non filtered listings should contain" + MAC_HIDDEN_FILENAME ,found);
    }

}