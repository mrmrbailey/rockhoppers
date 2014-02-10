/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.rockhoppersuk.feeds.messages;

import java.util.List;

/**
 *
 * @author mxbailey
 */
public class TheOutMsg {

        List<UsersPostcodeMsg> userIdPostcodes;

    public List<UsersPostcodeMsg> getUserIdPostcodes() {
        return userIdPostcodes;
    }

    public void setUserIdPostcodes(List<UsersPostcodeMsg> userIdPostcodes) {
        this.userIdPostcodes = userIdPostcodes;
    }

}
