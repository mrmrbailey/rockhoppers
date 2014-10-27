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
public class TypeInteferenceDemo2Test {

    @Test
    public void testBoxString() {

        List<Box<String>> boxes = new ArrayList();

        TypeInterface<String> x = new TypeInteferenceDemo2();
        TypeInterface y = new TypeInteferenceDemo2();

        x.addBox("hello", boxes);
        x.addBox("Mark", boxes);
        x.outputBoxes(boxes);

        y.addBox("Bye", boxes);
        y.addBox("Mark", boxes);
        y.outputBoxes(boxes);

    }
}
