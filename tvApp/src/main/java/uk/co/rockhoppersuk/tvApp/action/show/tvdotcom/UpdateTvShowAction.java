/*
 * (c) Mark Bailey - www.rockhoppersuk.co.uk - 2010
 */
package uk.co.rockhoppersuk.tvApp.action.show.tvdotcom;

import com.opensymphony.xwork2.ActionSupport;
import java.util.List;
import org.apache.log4j.Logger;
import uk.co.rockhoppersuk.tvApp.show.TvShow;
import uk.co.rockhoppersuk.tvApp.show.episode.Episode;
import uk.co.rockhoppersuk.tvApp.show.episode.EpisodeCatalogue;
import uk.co.rockhoppersuk.tvApp.show.episode.EpisodeFactory;
import uk.co.rockhoppersuk.tvApp.show.tvdotcom.TvDotComCatalogue;
import uk.co.rockhoppersuk.tvApp.show.tvdotcom.TvDotComEpisode;
import uk.co.rockhoppersuk.tvApp.show.tvdotcom.TvDotComFactory;

/**
 * This action responsible for presenting all <code>Episode</code> for a given
 * <code>TvShow</code> from TV.com to the front end.
 *
 * @author mbailey
 * @version 1.0
 */
public class UpdateTvShowAction extends ActionSupport {

    /**
     * serialVersionId for keeping track of changes.
     */
    public static final long serialVersionUID = 42L;
    /**
     * log4j static variable.
     */
    private static Logger logger = Logger.getLogger(UpdateTvShowAction.class);
    /**
     * The name of the <code>TvShow</code> of the <code>Episode</code> to be presented.
     */
    private String tvShowName;
    /**
     * The List of <code>Episode</code> to be presented.
     */
    private List<Episode> episodes;
    /**
     * The List of <code>Episode</code> to be presented.
     */
    private List<TvDotComEpisode> tvDotComEpisodes;
    /**
     * To enable unit testing.
     */
    private EpisodeCatalogue episodeCatalogue;
    /**
     * To enable unit testing.
     */
    private TvDotComCatalogue tvDotComCatalogue;

    /**
     * Action to set the <code>Episode</code>.
     * @return SUCCESS if successful.
     * @throws Exception if something bad happens
     */
    @Override
    public String execute() throws Exception {
        logger.info("entering execute method. TvShow: " + tvShowName);
        
        episodes = getEpisodeCatalogue().getTvShowEpisodes(TvShow.getTvShow(tvShowName));
        tvDotComEpisodes = getTvDotComCatalogue().getTvShowEpisodes(TvShow.getTvShow(tvShowName));

        logger.info("exiting execute method: episdoes size :" + episodes.size() + 
                "tvDotComEpisodes.size: " + tvDotComEpisodes.size());
        return SUCCESS;
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
     * The list of <code>Episode</code> requested.
     * @return the list of <code>Episode</code> requested.
     */
    public List<Episode> getEpisodes() {
        return episodes;
    }

        /**
     * The list of <code>TvDotComEpisode</code> requested.
     * @return the list of <code>TvDotComEpisode</code> requested.
     */
    public List<TvDotComEpisode> getTvDotComEpisodes() {
        return tvDotComEpisodes;
    }

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
     * @param mockTvDotComCatalogue mock TvDotEpisodeCatalogue
     */
    protected void setTvDotComCatalogue(final TvDotComCatalogue mockTvDotComCatalogue) {
        this.tvDotComCatalogue = mockTvDotComCatalogue;
    }

    /**
     * getter for the TvDotComCatalogue.  Used to enable catalogue to be mocked out.
     * @return the required TvDotComCatalogue implementation.
     */
    private TvDotComCatalogue getTvDotComCatalogue() {
        if (tvDotComCatalogue == null) {
            tvDotComCatalogue = TvDotComFactory.getTvDotComCatalogueInstance();
        }
        return tvDotComCatalogue;
    }        
}
