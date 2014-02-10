/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtech.jaxrsapi.feed;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 *
 * @author mxbailey
 */
@JsonIgnoreProperties({"drawDays"})
public interface MatrixDrawGamePublicView extends DrawGamePublicView {
    
}
