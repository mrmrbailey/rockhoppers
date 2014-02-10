/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.rockhoppersuk.feeds.messages;

import java.util.List;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 *
 * @author mxbailey
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class PostCodeData {

    List<String> postCodes;

    public List<String> getPostCodes() {
        return postCodes;
    }

    public void setPostCodes(List<String> postCodes) {
        this.postCodes = postCodes;
    }
}
