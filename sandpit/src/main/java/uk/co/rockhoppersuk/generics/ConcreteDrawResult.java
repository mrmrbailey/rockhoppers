/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.rockhoppersuk.generics;

/**
 *
 * @author mxbailey
 */
public class ConcreteDrawResult extends DrawResult<ConcreteResult> {

    @Override
    public boolean isMatch(ConcreteResult result) {
        result.getNumber();
        return true;
    }
    
}
