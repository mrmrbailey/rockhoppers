/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.rockhoppersuk.feeds;

import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import uk.co.rockhoppersuk.feeds.messages.AnotherOutMsg;
import uk.co.rockhoppersuk.feeds.messages.InMsg;
import uk.co.rockhoppersuk.feeds.messages.OutMsg;
import uk.co.rockhoppersuk.feeds.messages.PlayersPostcodeMsg;
import uk.co.rockhoppersuk.feeds.messages.PostCodeData;
import uk.co.rockhoppersuk.feeds.messages.PostcodeMsg;
import uk.co.rockhoppersuk.feeds.messages.UsersIdMsg;
import uk.co.rockhoppersuk.feeds.messages.UsersPostcodeMsg;

/**
 *
 * @author mxbailey
 */
@Path("/feed/users")
public class PlayerDetails {

    @POST
    @Path("/postcode")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public PlayersPostcodeMsg getPostcodesForUsers(UsersIdMsg inMsg) {

        if (inMsg == null) {
            throw new WebApplicationException(); //probably need to flesh this one out
        }

        List<PostcodeMsg> listPostCodes = new ArrayList<PostcodeMsg>();

        for (String userId : inMsg.getUserId()) {
            PostcodeMsg playerDetails = new PostcodeMsg();
            playerDetails.setUserId(userId);
            playerDetails.setPostcode("AA1 2DD");
            listPostCodes.add(playerDetails);
        }
        PlayersPostcodeMsg outMsg = new PlayersPostcodeMsg();
        outMsg.setUserIdPostcodes(listPostCodes);

        return outMsg;
    }

    @GET
    @Path("/getPostcode")
    @Produces(MediaType.APPLICATION_JSON)
    public PlayersPostcodeMsg getPostcodesForUsers() {

        List<PostcodeMsg> listPostCodes = new ArrayList<PostcodeMsg>();

        PostcodeMsg playerDetails = new PostcodeMsg();
        playerDetails.setUserId("123");
        playerDetails.setPostcode("AA1 2DD");
        listPostCodes.add(playerDetails);

        PlayersPostcodeMsg outMsg = new PlayersPostcodeMsg();
        outMsg.setUserIdPostcodes(listPostCodes);

        return outMsg;
    }

}
