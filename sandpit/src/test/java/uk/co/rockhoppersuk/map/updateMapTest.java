/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.rockhoppersuk.map;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
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
public class updateMapTest {

    private static final String PARAM_NOT_LOADED_BUNDLES_NO = "test";
    private static Map<String, Object> hostOutputMap = Collections.synchronizedMap(new HashMap<String, Object>());
    private static Map<String, Integer> aMap = new HashMap<String, Integer>();

    @Before
    public void setUp() {
    }

    @Test
    public void testValues() {
        if (!hostOutputMap.containsKey(PARAM_NOT_LOADED_BUNDLES_NO)) {
            hostOutputMap.put(PARAM_NOT_LOADED_BUNDLES_NO, new TreeSet<String>());
        }
        Set<String> notLoadedBundles = ((Set<String>) hostOutputMap.get(PARAM_NOT_LOADED_BUNDLES_NO));
        System.out.println(notLoadedBundles.size());
        System.out.println(hostOutputMap.get(PARAM_NOT_LOADED_BUNDLES_NO));
        
        String allHostProductStr = "1 2 3 4 5 6";
        List<String> allHostProductList = Arrays.asList(allHostProductStr.split(" "));

        notLoadedBundles.addAll(allHostProductList);
        System.out.println(notLoadedBundles.size());
        System.out.println(hostOutputMap.get(PARAM_NOT_LOADED_BUNDLES_NO));

        notLoadedBundles.remove("5");
        System.out.println(notLoadedBundles.size());
        System.out.println(hostOutputMap.get(PARAM_NOT_LOADED_BUNDLES_NO));

    }
    

    @Test
    public void sanityTest() {    
        int x = 1;
        int y = x;
        
        y =3;
        
        System.out.println(y);
        System.out.println(x);
        
        Integer aInt = Integer.parseInt("1");
        Integer bInt = aInt;
        bInt = Integer.parseInt("33");
        
        System.out.println(aInt);
        System.out.println(bInt);
        
//        Map<String, Integer> aMap = Collections.synchronizedMap(new HashMap<String, Integer>());
        aMap.put("aInt", aInt);
        Integer cInt = aMap.get("aInt");
        
        cInt = Integer.parseInt("33");
        
        System.out.println(aMap.get("aInt"));
        System.out.println(cInt);
        
        Map<String, Object> aMapOfMaps = new HashMap<String, Object>();
        aMapOfMaps.put("mapA", aMap);
        
        Map<String, Integer> bMap = (Map<String, Integer>) aMapOfMaps.get("mapA");
        bMap.put("mapB", cInt);
        
        System.out.println(aMapOfMaps);
        
    }
    
    
    @Test
    public void sanityTestClass() {   
        
        Map<String, Object> aMap = new HashMap<String, Object>();
        Map<String, myObject> myObjectMap = new HashMap<String, myObject>();
        myObjectMap.put("a", new myObject(1));
        aMap.put("myObject", myObjectMap);
        
        Map<String, myObject> anotherObject= (Map<String, myObject>) aMap.get("myObject");
        anotherObject.put("b", new myObject(2));
        
        System.out.println(aMap);
        
        
    }
    
    private class myObject {
        int a;
        
        public myObject(int a) {
            this.a = a;
        }

        public int getA() {
            return a;
        }

        public void setA(int a) {
            this.a = a;
        }
    }
    
    
}
