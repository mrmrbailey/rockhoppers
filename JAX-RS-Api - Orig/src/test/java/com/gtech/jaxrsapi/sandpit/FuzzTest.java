/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtech.jaxrsapi.sandpit;

import java.io.IOException;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;

/**
 *
 * @author mxbailey
 */
public class FuzzTest {
    

    /**
     * Test of getBiz method, of class Fuzz.
     */
    @Test
    public void testWriteOutMixIn() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
//        mapper.getSerializationConfig().addMixInAnnotations(Foo.class, MixIn.class);
        
        Fuzz fuzz = new Fuzz();
        System.out.println(mapper.writeValueAsString(fuzz));
                
    }
}
