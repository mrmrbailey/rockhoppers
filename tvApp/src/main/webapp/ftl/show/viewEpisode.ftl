<#escape x as x?html>
<#import "../common/common.ftl" as common/>
<#import "../navigation/showNav.ftl" as leftNav/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en-gb" lang="en-gb" dir="ltr">
    <head>
        <title><@s.property value="tvShowName" />, <@s.property value="episodeTitle"/> Details</title>
        <@s.head />
    </head>
    <body>
        <@common.nav />
        <@leftNav.leftNav />

        <div id="main">
            <h1><@s.property value="tvShowName" /></h1>
            <h2><@s.property value="episodeTitle" /></h2>
            <ul>
                <li><@s.property value="episode.status" /></li>
                <li><@s.property value="episode.episodeNumber" /></li>
                <li><@s.date name="episode.airDate" format="dd/MM/yyyy"/></td>
                <li><@s.property value="episode.synopsis" /></li>
            </ul>
            <table>
                <tr>
                    <th>Date</th>
                    <th>Channel</th>
                    <th>Link</th>
                </tr>
                <@s.iterator value="episodeListings">
                    <tr>
                        <td><@s.date name="listingDateTime" format="dd/MM/yyyy HH:mm"/></td>
                        <td><@s.property value="channel.channelName"/></td>
                        <td><a href="<@s.url action="viewDailyListing" listingDate="${listingDateTime?date}" />">Listings</a></td>
                    </tr>
                </@s.iterator> 
            </table>
        </div>
        <a href="<@s.url action="viewTvShowEpisode">
            <@s.param name="tvShowName"><@s.property value="tvShowName" /></@s.param>
        </@s.url>">Back to <@s.property value="tvShowName" /> Episodes</a>
    </body>
</html>
</#escape>
