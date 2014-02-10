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
public class PlayersPostcodeMsg {

    List<PostcodeMsg> userIdPostcodes;

    public List<PostcodeMsg> getUserIdPostcodes() {
        return userIdPostcodes;
    }

    public void setUserIdPostcodes(List<PostcodeMsg> userIdPostcodes) {
        this.userIdPostcodes = userIdPostcodes;
    }
}
