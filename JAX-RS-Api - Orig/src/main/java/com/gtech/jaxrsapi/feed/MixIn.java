/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtech.jaxrsapi.feed;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 *
 * @author mxbailey
 */
public interface MixIn {
    
    @JsonProperty("x") int x();
    @JsonProperty("y") int y();
    @JsonIgnore int getArea();
    
}
