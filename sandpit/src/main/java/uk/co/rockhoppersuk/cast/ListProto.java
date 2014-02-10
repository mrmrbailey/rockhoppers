/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.rockhoppersuk.cast;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mxbailey
 */
public class ListProto {

    public void hook() {
//        List<AnObject> theList = getObjects();
    }

    public List<Object> getObjects() {
        AnObject anObject = new AnObject();
        anObject.setId("mark");

        List<Object> aList = new ArrayList<Object>();
        aList.add(anObject);
        return aList;
    }

}
