/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.rockhoppersuk.feeds.messages;

/**
 *
 * @author mxbailey
 */
public class OutMsg {

    UsersPostcodeMsg[] userIdPostcodes;

    public UsersPostcodeMsg[] getUserIdPostcodes() {
        return userIdPostcodes;
    }

    public void setUserIdPostcodes(UsersPostcodeMsg[] userIdPostcodes) {
        this.userIdPostcodes = userIdPostcodes;
    }
}
