/*
 * (c) Mark Bailey - www.rockhoppersuk.co.uk - 2010
 */
package uk.co.rockhoppersuk.tvApp.show;

import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;

/**
 * A <code>TvShow</code> is an enum defining the different TV shows
 * that the system uses.
 *
 * @author mbailey
 * @version 1.0
 */
public enum TvShow {

    /**
     * TvShow: Bones.
     */
    BattlestarGalactica("Battlestar Galactica"),
    /**
     * TvShow: Bones.
     */
    Bones("Bones"),
    /**
     * TvShow: Bones.
     */
    Castle("Castle"),
    /**
     * TvShow: Criminal Mind.
     */
    CriminalMinds("Criminal Minds"),
    /**
     * TvShow: CSIVegas.
     */
    CSIVegas("CSIVegas"),
    /**
     * TvShow: CSIMiami.
     */
    CSIMiami("CSIMiami"),
    /**
     * TvShow: CSINY.
     */
    CSINY("CSINY"),
    /**
     * TvShow: Dexter.
     */
    Dexter("Dexter"),
    /**
     * TvShow: Doctor Who.
     */
    DoctorWho("Doctor Who"),
    /**
     * TvShow: Dollhouse.
     */
    Dollhouse("Dollhouse"),
    /**
     * TvShow: Firefly.
     */
    Firefly("Firefly"),
    /**
     * TvShow: Flash Forward.
     */
    FlashForward("Flash Forward"),
    /**
     * TvShow: Fringe.
     */
    Fringe("Fringe"),
    /**
     * TvShow: Heroes.
     */
    Haven("Haven"),
    /**
     * TvShow: Heroes.
     */
    Heroes("Heroes"),
    /**
     * TvShow: House.
     */
    House("House"),
    /**
     * TvShow: Journeyman.
     */
    Journeyman("Journeyman"),
    /**
     * TvShow: Lost.
     */
    Lost("Lost"),
    /**
     * TvShow: Numb3rs.
     */
    Numb3rs("Numb3rs"),
    /**
     * TvShow: The IT Crowd.
     */
    TheEvent("The Event"),
    /**
     * TvShow: The IT Crowd.
     */
    TheITCrowd("The IT Crowd"),
    /**
     * TvShow: The Walking Dead.
     */
    TheWalkingDead("The Walking Dead"),
    /**
     * TvShow: Torchwood.
     */
    Torchwood("Torchwood"),
    /**
     * TvShow: Without a Trace.
     */
    WithoutATrace("Without a Trace"),
    /**
     * TvShow: 24.
     */
    TwentyFour("24"),
    /**
     * TvShow: Unknown - used for any unknown shows.
     */
    Unknown("Unknown");
    /**
     * The presentation name of the <code>TvShow</code>.
     */
    private String showName;
    /**
     * the name of the <code>TvShow</code> returned if it is unrecognised.
     */
    public static final String UNKNOWN_SHOW = "Unknown";
    /**
     * log4j static variable.
     */
    private static Logger logger = Logger.getLogger(TvShow.class);
    /**
     * Internal Map representing the <code>TvShow</code> and the presentation name.
     */
    private static Map<String, TvShow> tvShowMap = new HashMap<String, TvShow>();

    /**
     * Static to populate showMap.
     */
    static {
        for (TvShow shows : TvShow.values()) {
            tvShowMap.put(shows.getTvShowName(), shows);
        }
    }

    /**
     * Constructor for the enum.
     * @param showName presentation name of the <code>TvShow</code>.
     */
    TvShow(final String showName) {
        this.showName = showName;
    }

    /**
     * getter for presentation name of the <code>TvShow</code>.
     * @return presentation name of the <code>TvShow</code>.
     */
    public String getTvShowName() {
        return showName;
    }

    /**
     * getter for the <code>TvShow</code>.
     * @param showName presentation name of the <code>TvShow</code>.
     * @return the <code>TvShow</code> requested or Unknown if not recognised.
     */
    public static TvShow getTvShow(final String showName) {

        String enumKeyShowName = TvShowProperties.getInstance().getTvShowName(showName);
        if (!tvShowMap.containsKey(enumKeyShowName)) {
            logger.warn("Show is not recognised :" + showName
                    + " enumKeyShowName :" + enumKeyShowName);
            return Unknown;
        }
        return tvShowMap.get(enumKeyShowName);
    }
}
