/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.co.rockhoppersuk.generics;

/**
 *
 * @author mxbailey
 */
public abstract class AAbstractMarkerInterfaceClass implements MarkerInterface<Integer>{

    @Override
    public Integer getT() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public abstract int blob();
    
}
