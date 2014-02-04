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
import uk.co.rockhoppersuk.feeds.messages.PlayerDetails;
import uk.co.rockhoppersuk.feeds.messages.PlayersPostcodeMsg;
import uk.co.rockhoppersuk.feeds.messages.PostCodeData;
import uk.co.rockhoppersuk.feeds.messages.PostcodeMsg;
import uk.co.rockhoppersuk.feeds.messages.UserIdMsgOLD;
import uk.co.rockhoppersuk.feeds.messages.UsersIdMsg;
import uk.co.rockhoppersuk.feeds.messages.UsersPostcodeMsg;

/**
 *
 * @author mxbailey
 */
@Path("/feed/promo")
public class Promotion {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/postcodes.json")
    public PostCodeData getPostCodes() {
        PostCodeData outMessage = new PostCodeData();

        List<String> postCodes = new ArrayList<String>();
        String postCode = "AL6";
        postCodes.add(postCode);

        postCode = "WD18";
        postCodes.add(postCode);
        outMessage.setPostCodes(postCodes);

        return outMessage;

    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("postPostCodes.json")
    public PostCodeData postPostCodes(InMsg inMessage) {
        PostCodeData outMessage = new PostCodeData();

        List<String> postCodes = new ArrayList<String>();
        String postCode = "AL6";
        postCodes.add(postCode);

        postCode = "WD18";
        postCodes.add(postCode);
        outMessage.setPostCodes(postCodes);

        return outMessage;
    }

    @POST
    @Path("/postcode")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<UsersPostcodeMsg> getPostcodesForUsers(UserIdMsgOLD inMsg) {

        if (inMsg == null) {
            throw new WebApplicationException(); //probably need to flesh this one out
        }

        List<UsersPostcodeMsg> outMsg = new ArrayList<UsersPostcodeMsg>();

        for (int userId : inMsg.getUserIds()) {
            UsersPostcodeMsg playerDetails = new UsersPostcodeMsg();
            playerDetails.setUserId(userId);
            playerDetails.setPostcode("ADBC");
            outMsg.add(playerDetails);
        }


        return outMsg;
    }

    @POST
    @Path("/postcodeArray")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public UsersPostcodeMsg[] getPostcodeArrayForUsers(UserIdMsgOLD inMsg) {

        if (inMsg == null) {
            throw new WebApplicationException(); //probably need to flesh this one out
        }

        UsersPostcodeMsg[] userIdPostcodes = new UsersPostcodeMsg[inMsg.getUserIds().size()];

        for (int i=0; i< inMsg.getUserIds().size(); i++) {
            UsersPostcodeMsg playerDetails = new UsersPostcodeMsg();
            playerDetails.setUserId(inMsg.getUserIds().get(i));
            playerDetails.setPostcode("AA1 2BB");
            userIdPostcodes[i] = playerDetails;
        }


        return userIdPostcodes;
    }

    @POST
    @Path("/postcodeArrayAgain")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public OutMsg getPostcodeArrayAgainForUsers(UserIdMsgOLD inMsg) {

        if (inMsg == null) {
            throw new WebApplicationException(); //probably need to flesh this one out
        }

        UsersPostcodeMsg[] userIdPostcodes = new UsersPostcodeMsg[inMsg.getUserIds().size()];

        for (int i=0; i< inMsg.getUserIds().size(); i++) {
            UsersPostcodeMsg playerDetails = new UsersPostcodeMsg();
            playerDetails.setUserId(inMsg.getUserIds().get(i));
            playerDetails.setPostcode("AA1 2BB");
            userIdPostcodes[i] = playerDetails;
        }

        OutMsg outMsg = new OutMsg();
        outMsg.setUserIdPostcodes(userIdPostcodes);

        return outMsg;
    }

    @POST
    @Path("/postcodeList")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public AnotherOutMsg getPostcodesListForUsers(UserIdMsgOLD inMsg) {

        if (inMsg == null) {
            throw new WebApplicationException(); //probably need to flesh this one out
        }

        List<UsersPostcodeMsg> listPostCodes = new ArrayList<UsersPostcodeMsg>();

        for (int userId : inMsg.getUserIds()) {
            UsersPostcodeMsg playerDetails = new UsersPostcodeMsg();
            playerDetails.setUserId(userId);
            playerDetails.setPostcode("ADBC");
            listPostCodes.add(playerDetails);
        }
        AnotherOutMsg outMsg = new AnotherOutMsg();
        outMsg.setUserIdPostcodes(listPostCodes);

        return outMsg;
    }

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
}
