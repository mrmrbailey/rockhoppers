/*
 * (c) Mark Bailey - www.rockhoppersuk.co.uk - 2010
 */
package uk.co.rockhoppersuk.tvApp.channel;

import java.util.HashMap;
import java.util.Map;

/**
 * A <code>Channel</code> is an enum defining the different TV Channels
 * that the system uses.
 *
 * @author mbailey
 * @version 1.0
 */
public enum Channel {

    /**
     * TV Channel: BBC1 London & South East.
     */
    BBC1("BBC1 London & South East"),
    /**
     * TV Channel: BBC1 London & South East.
     */
    BBC1London("BBC1 London"),
    /**
     * TV Channel: BBC2 London & South East.
     */
    BBC2("BBC2 London & South East"),
    /**
     * TV Channel: BBC3.
     */
    BBC3("BBC3"),
    /**
     * TV Channel: BBC HD.
     */
    BBCHD("BBC HD"),
    /**
     * TV Channel: Channel 4.
     */
    Channel4("Channel 4"),
    /**
     * TV Channel: Channel 4 HD.
     */
    Channel4HD("Channel 4 HD"),
    /**
     * TV Channel: Channel 5.
     */
    Channel5("Channel 5"),
    /**
     * TV Channel: Channel 5 HD.
     */
    Channel5HD("Channel 5 HD"),
    /**
     * TV Channel: Channel Five.
     */
    ChannelFive("Channel Five"),
    /**
     * TV Channel: Channel One.
     */
    ChannelOne("Channel One"),
    /**
     * TV Channel: E4.
     */
    E4("E4"),
    /**
     * TV Channel: E4 HD.
     */
    E4HD("E4 HD"),
    /**
     * TV Channel: Five.
     */
    Five("Five"),
    /**
     * TV Channel: Five US.
     */
    FiveUS("Five US"),
    /**
     * TV Channel: Five USA.
     */
    FiveUSA("Five USA"),
    /**
     * TV Channel: 5USA.
     */
    FUSA("5USA"),
    /**
     * TV Channel: Fiver.
     */
    Fiver("Fiver"),
    /**
     * TV Channel: FX.
     */
    FX("FX"),
    /**
     * TV Channel: FX HD.
     */
    FXHD("FX HD"),
    /**
     * TV Channel: Hallmark.
     */
    Hallmark("Hallmark"),
    /**
     * TV Channel: Horror Channel.
     */
    HorrorChannel("Horror Channel"),
    /**
     * TV Channel: The Horror Channel.
     */
    TheHorrorChannel("The Horror Channel"),
    /**
     * TV Channel: ITV1 London.
     */
    ITV1("ITV1 London"),
    /**
     * TV Channel: ITV3.
     */
    ITV3("ITV3"),
    /**
     * TV Channel: ITV4.
     */
    ITV4("ITV4"),
    /**
     * TV Channel: Living.
     */
    Living("Living"),
    /**
     * TV Channel: Living2.
     */
    Living2("Living2"),
    /**
     * TV Channel: More4.
     */
    More4("More4"),
    /**
     * TV Channel: S4C.
     */
    S4C("S4C"),
    /**
     * TV Channel: Sci-Fi.
     */
    SciFi("Sci-Fi"),
    /**
     * TV Channel: Sky Atlantic HD.
     */
    SkyAtlantic("Sky Atlantic HD"),
    /**
     * TV Channel: Sky Living.
     */
    SkyLiving("Sky Living"),
    /**
     * TV Channel: Sky Living HD.
     */
    SkyLivingHD("Sky Living HD"),
    /**
     * TV Channel: Sky One.
     */
    SkyOne("Sky One"),
    /**
     * TV Channel: Sky Two.
     */
    SkyTwo("Sky Two"),
    /**
     * TV Channel: Sky1.
     */
    Sky1("Sky1"),
    /**
     * TV Channel: Sky1HD.
     */
    Sky1HD("Sky1 HD"),
    /**
     * TV Channel: Sky2.
     */
    Sky2("Sky2"),
    /**
     * TV Channel: Sky3.
     */
    Sky3("Sky3"),
    /**
     * TV Channel: STV (was Grampian/Scottish).
     */
    STV("STV (was Grampian/Scottish)"),
    /**
     * TV Channel: Syfy.
     */
    Syfy("Syfy"),
    /**
     * TV Channel: SyfyHD.
     */
    SyfyHD("Syfy HD"),
    /**
     * TV Channel: UKTV Drama.
     */
    UKTVDram("UKTV Drama"),
    /**
     * TV Channel: UKTV Gold.
     */
    UKTVGold("UKTV Gold"),
    /**
     * TV Channel: Ulster.
     */
    Ulster("Ulster"),
    /**
     * TV Channel: Universal.
     */
    Universal("Universal"),
    /**
     * TV Channel: Universal HD.
     */
    UniversalHD("Universal HD"),
    /**
     * TV Channel: UTV.
     */
    UTV("UTV"),
    /**
     * TV Channel: Virgin 1.
     */
    Virgin1("Virgin 1"),
    /**
     * TV Channel: Watch.
     */
    Watch("Watch"),
    /**
     * TV Channel: Unknown.
     */
    Unknown("Unknown");
    /**
     * The Channel name presentation value.
     */
    private String channelName;
    /**
     * the name of the channel returned if it is unrecognised.
     */
    public static final String UNKNOWN_CHANNEL = "Unknown";
    /**
     * Internal Map representing the <code>Channel</code> and the presentation name.
     */
    private static Map<String, Channel> tvChannelMap = new HashMap<String, Channel>();

    /**
     * Static to populate tvChannelMap.
     */
    static {
        for (Channel channel : Channel.values()) {
            tvChannelMap.put(channel.getChannelName(), channel);
        }
    }

    /**
     * Constructor for the enum.
     * @param channelName Presentation name for the Channel
     */
    Channel(final String channelName) {
        this.channelName = channelName;
    }

    /**
     * getter for the channel name.
     * @return presentation name for the Channel
     */
    public String getChannelName() {
        return channelName;
    }

    /**
     * getter for the channel.
     * @param channelName The presentation name of the channel
     * @return the channel requested or Unknown if not recognised
     */
    public static Channel getChannel(final String channelName) {
        if (tvChannelMap.get(channelName) == null) {
            return Unknown;
        }
        return tvChannelMap.get(channelName);
    }
}
