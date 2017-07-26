/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.rockhoppersuk.inheritance;

import java.util.regex.Pattern;

/**
 *
 * @author mxbailey
 */
public abstract class AbstractClass {

    private final static String X = "x";

    private final String XBoard = X + getBoard();

    public abstract String getBoard();

    public void parse(String y) {
        System.out.println(XBoard);
        Pattern.compile(XBoard);
    }


}
