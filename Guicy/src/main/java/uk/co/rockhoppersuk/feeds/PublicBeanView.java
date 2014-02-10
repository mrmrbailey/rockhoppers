/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.rockhoppersuk.feeds;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 *
 * @author mxbailey
 */
@JsonIgnoreProperties({"nonPublicThing"})
public interface PublicBeanView {
    
    @JsonProperty("publicId")
    String getId();    
    
}
