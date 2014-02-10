/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.rockhoppersuk.feeds.messages;

import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * Outbound message containing User Id and Postcode.
 */
//@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class UsersPostcodeMsg {
    int userId;
    String postcode;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }
}
