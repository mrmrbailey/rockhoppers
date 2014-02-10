/*
 * (c) Mark Bailey - www.rockhoppersuk.co.uk - 2010
 */
package uk.co.rockhoppersuk.tvApp.action.episode;

import com.opensymphony.xwork2.ActionSupport;
import java.util.List;
import org.apache.log4j.Logger;
import uk.co.rockhoppersuk.tvApp.listing.Listing;
import uk.co.rockhoppersuk.tvApp.listing.ListingCatalogue;
import uk.co.rockhoppersuk.tvApp.listing.ListingFactory;
import uk.co.rockhoppersuk.tvApp.show.TvShow;
import uk.co.rockhoppersuk.tvApp.show.episode.Episode;
import uk.co.rockhoppersuk.tvApp.show.episode.EpisodeCatalogue;
import uk.co.rockhoppersuk.tvApp.show.episode.EpisodeFactory;

/**
 * This action responsible for presenting an <code>Episode</code> for a given
 * <code>TvShow</code> and Title to the front end.
 *
 * @author mbailey
 * @version 1.0
 */
public class ViewEpisodeAction extends ActionSupport {

    /**
     * serialVersionId for keeping track of changes.
     */
    public static final long serialVersionUID = 42L;
    /**
     * log4j static variable.
     */
    private static Logger logger = Logger.getLogger(ViewEpisodeAction.class);
    /**
     * The <code>Episode</code> to be presented.
     */
    private Episode episode;
    /**
     * The title of the <code>Episode</code> to be presented.
     */
    private String episodeTitle;
    /**
     * The name of the <code>TvShow</code> of the <code>Episode</code> to be presented.
     */
    private String tvShowName;
    /**
     * The dates in which this Episode has been aired.
     */
    private List<Listing> episodeListings;
    /**
     * To enable unit testing.
     */
    private EpisodeCatalogue episodeCatalogue;
    /**
     * To enable unit testing.
     */
    private ListingCatalogue listingCatalogue;

    /**
     * To enable unit testing.
     * @param mockEpisodeCatalogue mock EpisodeCatalogue
     */
    protected void setEpisodeCatalogue(final EpisodeCatalogue mockEpisodeCatalogue) {
        this.episodeCatalogue = mockEpisodeCatalogue;
    }

    /**
     * getter for the EpisodeCatalogue.  Used to enable catalogue to be mocked out.
     * @return the required EpisodeCatalogue implementation.
     */
    private EpisodeCatalogue getEpisodeCatalogue() {
        if (episodeCatalogue == null) {
            episodeCatalogue = EpisodeFactory.getEpisodeCatalogueInstance();
        }
        return episodeCatalogue;
    }

    /**
     * To enable unit testing.
     * @param mockListingCatalogue mock ListingCatalogue
     */
    protected void setListingCatalogue(final ListingCatalogue mockListingCatalogue) {
        this.listingCatalogue = mockListingCatalogue;
    }

    /**
     * getter for the ListingCatalogue.  Used to enable catalogue to be mocked out.
     * @return the required ListingCatalogue implementation.
     */
    private ListingCatalogue getListingCatalogue() {
        if (listingCatalogue == null) {
            listingCatalogue = ListingFactory.getListingCatalogueInstance();
        }
        return listingCatalogue;
    }

    /**
     * Action to set the <code>Episode</code>.
     * @return SUCCESS if successful.
     * @throws Exception if something bad happens
     */
    @Override
    public String execute() throws Exception {
        logger.info("entering execute method. TvShow: " + tvShowName
                + ", title: " + episodeTitle);

        episode = getEpisodeCatalogue().
                getEpisode(TvShow.getTvShow(tvShowName), episodeTitle);

        episodeListings = getListingCatalogue().
                getListingsForEpisode(TvShow.getTvShow(tvShowName), episodeTitle);

        logger.info("exiting execute method: Episode: " + episode.getEpisodeTitle());
        return SUCCESS;
    }

    /**
     * The <code>Episode</code> requested.
     * @return the <code>Episode</code> requested.
     */
    public Episode getEpisode() {
        return episode;
    }

    /**
     * getter for the name of the <code>TvShow</code>.
     * @return the name of the <code>TvShow</code>.
     */
    public String getTvShowName() {
        return tvShowName;
    }

    /**
     * setter for the name of the <code>TvShow</code>.
     * @param tvShowName the name of the <code>TvShow</code>.
     */
    public void setTvShowName(final String tvShowName) {
        this.tvShowName = tvShowName;
    }

    /**
     * getter for the title of the <code>Episode</code>.
     * @return the title of the <code>Episode</code>.
     */
    public String getEpisodeTitle() {
        return episodeTitle;
    }

    /**
     * setter for the title of the <code>Episode</code>.
     * @param episodeTitle the title of the <code>Episode</code>.
     */
    public void setEpisodeTitle(final String episodeTitle) {
        this.episodeTitle = episodeTitle;
    }

    /**
     * getter for the List of episode listing.
     * @return the list of episode listings.
     */
    public List<Listing> getEpisodeListings() {
        return episodeListings;
    }
}
