/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.rockhoppersuk.map;

import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author mxbailey
 */
public class mapTest {

    private Map<String, Integer> aMap;

    @Before
    public void setUp() {
        aMap = new HashMap<String, Integer>();
        aMap.put("String1", Integer.parseInt("1"));
        aMap.put("String2", Integer.parseInt("2"));
        aMap.put("String3", Integer.parseInt("3"));
    }

    @Test
    public void testKeySet() {
        for (String a : aMap.keySet()) {
            System.out.println(a);
        }
    }

    @Test
    public void testEntrySet() {
        for (Map.Entry<String, Integer> a : aMap.entrySet()) {
            System.out.println(a);
        }
    }

    @Test
    public void testEntrySetAllGames() {
        for (Map.Entry<String, DrawGame> a : getAllDrawGames().entrySet()) {
            System.out.println(a);
        }
    }

    @Test
    public void testKeySetAllGames() {
        for (String a : getAllDrawGames().keySet()) {
            System.out.println(a);
        }
    }

    private Map<String, DrawGame> getAllDrawGames() {
        Map<String, DrawGame> allDrawGames = new HashMap<String, DrawGame>();
        allDrawGames.put("game1", new DrawGame());
        allDrawGames.put("game2", new DrawGame());
        allDrawGames.put("game3", new DrawGame());
        return allDrawGames;
    }

    private class DrawGame {
    }

    @Test
    public void testISSameObjectTwiceInHash() {
        assertEquals(3, aMap.size());
        aMap.put("String1", Integer.parseInt("1"));
        assertEquals(3, aMap.size());
    }
}
