/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.co.rockhoppersuk.generics;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 * @author mxbailey
 */
public class TypeInferenceDemoTest {

    @Test
    public void testBoxInt() {

        List<Box<Integer>> boxes = new ArrayList();

        TypeInferenceDemo.<Integer>addBox(1, boxes);
        TypeInferenceDemo.<Integer>addBox(2, boxes);

        TypeInferenceDemo.<Integer>outputBoxes(boxes);
    }


    @Test
    public void testBoxString() {

        List<Box<String>> boxes = new ArrayList();

        TypeInferenceDemo.<String>addBox("hello", boxes);
        TypeInferenceDemo.<String>addBox("Mark", boxes);

        TypeInferenceDemo.<String>outputBoxes(boxes);
    }

}
