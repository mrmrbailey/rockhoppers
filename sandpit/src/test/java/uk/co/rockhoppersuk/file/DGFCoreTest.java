/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.rockhoppersuk.file;

import java.net.URL;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import uk.co.rockhoppersuk.dgf.CISDGFUtil;

/**
 *
 * @author mxbailey
 */
public class DGFCoreTest {

    public DGFCoreTest() {
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
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //

    @Test
    public void hello() {
        URL[] urls = CISDGFUtil.getULRS();

        for (URL url : urls) {
            System.out.println(url.getFile());
        }


    }
}
