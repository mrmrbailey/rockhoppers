/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.rockhoppersuk.bean;

/**
 *
 * @author mxbailey
 */
public class Bean {

    private int Id;
    private String value;
    private String nonPublicThing;

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getNonPublicThing() {
        return nonPublicThing;
    }

    public void setNonPublicThing(String nonPublicThing) {
        this.nonPublicThing = nonPublicThing;
    }
}
