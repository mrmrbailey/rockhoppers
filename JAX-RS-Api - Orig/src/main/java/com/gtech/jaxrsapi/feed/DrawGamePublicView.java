/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtech.jaxrsapi.feed;

import java.util.List;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 *
 * @author mxbailey
 */

@JsonIgnoreProperties({"anotherList"})
public interface DrawGamePublicView {
    
    @JsonProperty("cost")
    int getPrice();
    
    @JsonSerialize(include= JsonSerialize.Inclusion.NON_DEFAULT)
    List<String> getDrawDays();
}
