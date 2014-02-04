package com.gtech.jaxrsapi.feed;

import com.gtech.jaxrsapi.pojo.DrawGame;
import com.gtech.jaxrsapi.pojo.Point;
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
@Path("/draw-game")
public class DrawGameFeed {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/game.json")
    public DrawGame getDrawGame() {
        DrawGame game = createDrawGame();
        return game;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/point.json")
    public Point getPoint() {
        Point point = new Point(1, 2);
        return point;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/gameNull.json")
    public DrawGame getDrawGameNull() {
        DrawGame game = createDrawGame();
        game.setDrawDays(null);
        return game;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/gameEmpty.json")
    public DrawGame getDrawGameEmpty() {
        DrawGame game = new DrawGame();
        game.setGameName("aName");
        game.setPrice(BigDecimal.TEN);
        return game;
    }

    private DrawGame createDrawGame() {
        DrawGame game = new DrawGame();
        game.setGameName("aName");
        game.setPrice(BigDecimal.TEN);

        List<String> drawDays = new ArrayList<String>();
        drawDays.add("Wed");
        drawDays.add("Sat");
        game.setDrawDays(drawDays);
        
        game.setAnotherList(drawDays);

        return game;
    }
}
