/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtech.jaxrsapi.sandpit.merge;

import java.util.ArrayList;
import java.util.List;
import org.codehaus.jackson.annotate.JsonAutoDetect;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.codehaus.jackson.map.introspect.VisibilityChecker;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author mxbailey
 */
public class JacksonFoo {

    Baz baz;
    Zab zab;
    List<Bar> bars;
    ObjectMapper mapper;
    Foo foo;

    @Before
    public void setUp() {
        baz = new Baz("BAZ", 42);
        zab = new Zab("ZAB", true);
        bars = new ArrayList<Bar>();
        bars.add(baz);
        bars.add(zab);
        
        foo = new Foo(bars);

        mapper = new ObjectMapper();
       


    }

    @Test
    public void testOutputBars() throws Exception {
        String barsValueAsString = mapper.writeValueAsString(bars);
        System.out.println(barsValueAsString);
    }
    
    @Test
    public void testOutputFoo() throws Exception {
        String fooValueAsString = mapper.writeValueAsString(foo);
        System.out.println(fooValueAsString);
    }    
}
