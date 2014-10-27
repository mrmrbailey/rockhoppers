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
public interface TypeInterface<T> {

    <U> void addBox(U u, List<Box<U>> boxes);
    <U> void outputBoxes(List<Box<U>> boxes);
}
