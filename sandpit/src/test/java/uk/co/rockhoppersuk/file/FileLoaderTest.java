/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.rockhoppersuk.file;

import java.net.URL;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author mxbailey
 */
public class FileLoaderTest {

    public FileLoaderTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getGameBundleURLs method, of class FileLoader.
     */
    @Test
    public void testGetGameBundleURLs() {
        System.out.println("getGameBundleURLs");
        String bundleFilepath = "C:\\mark\\tmp\\bundles";
        URL[] result = FileLoader.getGameBundleURLs(bundleFilepath);
        for (URL url : result) {
            System.out.println(url.getPath());
        }
        
    }
}
