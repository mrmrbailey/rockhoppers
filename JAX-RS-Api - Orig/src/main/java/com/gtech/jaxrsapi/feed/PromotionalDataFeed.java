/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtech.jaxrsapi.feed;

/**
 *
 * @author mxbailey
 */
import com.gtech.jaxrsapi.message.PostCodeMessage;
import com.gtech.jaxrsapi.pojo.DrawGame;
import com.gtech.jaxrsapi.pojo.Point;
import java.awt.Stroke;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * JAX-RS resource class for draw game feed
 */
@Path("/promo")
public class PromotionalDataFeed {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/postcodes.json")
    public PostCodeMessage getPostCodes() {
        PostCodeMessage outMessage = new PostCodeMessage();

        List<String> postCodes = new ArrayList<String>();
        String postCode = "AL6";
        postCodes.add(postCode);

        outMessage.setPostCodes(postCodes);

        return outMessage;

    }


}
