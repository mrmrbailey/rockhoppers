/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.rockhoppersuk.generics;

/**
 *
 * @author mxbailey
 */
public class AnotherConcreteDrawResult extends DrawResult<AnotherConcreteResult> {

    @Override
    public boolean isMatch(AnotherConcreteResult result) {
        result.getRaffle();
        return true;
    }
    
    
    
}
