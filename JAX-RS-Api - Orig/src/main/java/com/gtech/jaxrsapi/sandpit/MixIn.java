/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtech.jaxrsapi.sandpit;

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 *
 * @author mxbailey
 */
public interface MixIn {
    
    @JsonIgnore long getBar();
}
