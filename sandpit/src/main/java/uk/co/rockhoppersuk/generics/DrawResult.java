/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.rockhoppersuk.generics;

/**
 *
 * @author mxbailey
 */
public abstract class DrawResult<R extends AbstractResult> {

    public abstract boolean isMatch(R result);
}
