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
@JsonSerialize
public class InMsg {


    private List<String> userIds;

    public List<String> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<String> userIds) {
        this.userIds = userIds;
    }
}
