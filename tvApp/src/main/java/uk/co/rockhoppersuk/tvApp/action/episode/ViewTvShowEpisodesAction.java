/*
 * (c) Mark Bailey - www.rockhoppersuk.co.uk - 2010
 */
package uk.co.rockhoppersuk.tvApp.action.episode;

import com.opensymphony.xwork2.ActionSupport;
import java.util.List;
import org.apache.log4j.Logger;
import uk.co.rockhoppersuk.tvApp.show.TvShow;
import uk.co.rockhoppersuk.tvApp.show.episode.Episode;
import uk.co.rockhoppersuk.tvApp.show.episode.EpisodeCatalogue;
import uk.co.rockhoppersuk.tvApp.show.episode.EpisodeFactory;

/**
 * This action responsible for presenting an list of <code>Episode</code> for a given
 * <code>TvShow</code> to the front end.
 *
 * @author mbailey
 * @version 1.0
 */
public class ViewTvShowEpisodesAction extends ActionSupport {

    /**
     * serialVersionId for keeping track of changes.
     */
    public static final long serialVersionUID = 42L;
    /**
     * log4j static variable.
     */
    private static Logger logger = Logger.getLogger(ViewTvShowEpisodesAction.class);
    /**
     * The List of <code>Episode</code> to be presented.
     */
    private List<Episode> episodes;
    /**
     * The name of the <code>TvShow</code> of the <code>Episode</code> to be presented.
     */
    private String tvShowName;
    /**
     * To enable unit testing.
     */
    private EpisodeCatalogue episodeCatalogue;

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
     * Action to set the <code>Episode</code>.
     * @return SUCCESS if successful.
     * @throws Exception if something bad happens
     */
    @Override
    public String execute() throws Exception {
        logger.info("entering execute method.  TvShow: " + tvShowName);

        episodes = getEpisodeCatalogue().getTvShowEpisodes(TvShow.getTvShow(tvShowName));

        logger.info("exiting execute method: Episodes size :" + episodes.size());
        return SUCCESS;
    }

    /**
     * The list of <code>Episode</code> requested.
     * @return the list of <code>Episode</code> requested.
     */
    public List<Episode> getEpisodes() {
        return episodes;
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
}
