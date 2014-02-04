/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.rockhoppersuk.feeds;

import com.google.inject.Guice;
import com.google.inject.Injector;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import uk.co.rockhoppersuk.bean.Bean;
import uk.co.rockhoppersuk.modules.Module;

/**
 *
 * @author mxbailey
 */
@Path("/feed/guicy")
public class GuicyFeed {
    
    Injector injector;

    public GuicyFeed() {
        injector = Guice.createInjector(new Module());
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/beans.json")
    public List<Bean> getBeans() {
        InjectableFeed feed = injector.getInstance(InjectableFeed.class);
        return feed.getBeans();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/bean_{id}.json")
    public Bean getBean(@PathParam("id") String id) {
        int parseId = Integer.parseInt(id);
        InjectableFeed feed = injector.getInstance(InjectableFeed.class);
        return feed.getBean(parseId);
    }
}
