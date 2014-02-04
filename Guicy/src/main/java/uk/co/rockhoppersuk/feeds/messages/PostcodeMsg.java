/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.rockhoppersuk.feeds.messages;

import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 *
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class PostcodeMsg {

    String userId;
    String postcode;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }
}
