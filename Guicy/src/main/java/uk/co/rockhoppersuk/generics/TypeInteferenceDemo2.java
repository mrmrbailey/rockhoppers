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
public class TypeInteferenceDemo2 implements TypeInterface<String> {

    @Override
    public <String> void addBox(String u, List<Box<String>> boxes) {
        Box<String> box = new Box();
        box.set(u);
        boxes.add(box);
    }

    @Override
    public <String> void outputBoxes(List<Box<String>> boxes) {
        for (Box<String> box : boxes) {
            String contents = box.get();
            System.out.println("Box contains : " + contents.toString());
        }
    }

}
