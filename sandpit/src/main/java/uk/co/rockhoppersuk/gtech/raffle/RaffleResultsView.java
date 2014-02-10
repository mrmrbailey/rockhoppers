/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.rockhoppersuk.gtech.raffle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author mxbailey
 */
public class RaffleResultsView {

    /**
     * Map keyed on colour and tier eg BLUE1, and value of a list of raffle
     * results for that colour tier combo.
     */
    private Map<String, List<RaffleResult>> rafflesByColour;

    public RaffleResultsView(List<RaffleResult> allRafflesForDraw) {
        rafflesByColour = new HashMap<String, List<RaffleResult>>();
        for (RaffleResult raffle : allRafflesForDraw) {
            String key = raffle.getRaffleNumber().substring(0, 4) + Short.toString(raffle.getTierId());
            List<RaffleResult> raffles;
            if (rafflesByColour.containsKey(key)) {
                raffles = rafflesByColour.get(key);
            } else {
                raffles = new ArrayList<RaffleResult>();
            }
            raffles.add(raffle);
            rafflesByColour.put(key, raffles);
        }
    }

    public int getNumberOfPrizes(short tier, String colour) {
        String key = colour + Short.toString(tier);
        if (rafflesByColour.containsKey(key)) {
            return rafflesByColour.get(key).size();
        } else {
            return 0;
        }
    }

    public List<RaffleResult> getRaffles(short tier, String colour) {
        String key = colour + Short.toString(tier);
        if (rafflesByColour.containsKey(key)) {
            return rafflesByColour.get(key);
        } else {
            return null;
        }
    }
}
