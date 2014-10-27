/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.rockhoppersuk.generics;

import java.util.List;

/**
 *
 * @author mxbailey
 */
public class TypeInferenceDemo {

    public static <U> void addBox(U u, List<Box<U>> boxes) {
        Box<U> box = new Box();
        box.set(u);
        boxes.add(box);
    }

    public static <U> void outputBoxes(List<Box<U>> boxes) {
        for (Box<U> box : boxes) {
            U contents = box.get();
            System.out.println("Box contains : " + contents.toString());
        }
    }

}
