/*
 * (c) Mark Bailey - www.rockhoppersuk.co.uk - 2010
 */
package uk.co.rockhoppersuk.file;

import java.io.File;
import java.io.IOException;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * A test for FileUtils.
 *
 * @author mbailey
 * @version 1.0
 */
public class FileUtilsTest {

    private final static String TEST_FILE_PATH = "src/test/resources/file/";
    private final static String TEST_FILENAME = TEST_FILE_PATH + "fileExists.txt";
    private final static String TEST_FILENAME_NOTEXISTS = TEST_FILE_PATH + "fileNotExists.txt";

    /**
     * Creates a test file.
     */
    @Before
    public void setUp() {
        File file = new File(TEST_FILENAME);
        try {
            file.createNewFile();
        } catch (IOException ex) {
            Logger.getLogger(FileUtilsTest.class.getName()).log(Level.ERROR, null, ex);
            fail("Error creating test file");
        }

        //Ensure no file for the not exists test
        File notFile = new File(TEST_FILENAME_NOTEXISTS);
        notFile.delete();
    }

    /**
     * Removes test file.
     */
    @After
    public void tearDown() {
        File file = new File(TEST_FILENAME);
        file.delete();

    }

    /**
     * Tests the isFileExist with a valid file
     */
    @Test
    public void testIsFileExistValidFile() {
        assertTrue("testFileExist: File Exists", FileUtils.isFileExist(TEST_FILENAME));
    }

    /**
     * Tests the isFileExist with a directory,
     * should be false as a directory is not a file.
     */
    @Test
    public void testIsFileExistValidDirectory() {
        assertFalse("testNotFileExistDirectory", FileUtils.isFileExist(TEST_FILE_PATH));
    }

    /**
     * Tests the isFileExist with an invalid file, should false
     */
    @Test
    public void testNotFileExistInvalidFile() {
        assertFalse("testNotFileExistNoFile",
                FileUtils.isFileExist(TEST_FILENAME_NOTEXISTS));
    }

    /**
     * Test of createDir method, of class FileUtils.
     */
    @Test
    public void testCreateDir() {
        final String testDir = "/test";
        File dir = new File(TEST_FILE_PATH + testDir);
        dir.deleteOnExit();

        assertFalse(dir.exists());
        assertTrue(FileUtils.createDir(TEST_FILE_PATH + testDir));
        assertTrue(dir.exists());

        assertTrue(FileUtils.createDir(TEST_FILE_PATH + testDir));
        assertTrue(dir.exists());
    }
}
