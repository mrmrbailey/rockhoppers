<#escape x as x?html>
<#import "../common/common.ftl" as common/>
<#import "../navigation/listingsNav.ftl" as leftNav/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en-gb" lang="en-gb" dir="ltr">
    <head>
        <title>Listings for <@s.date name="listingDate" format="dd MMM"/></title>
        <@s.head />
    </head>
    <body>
        <@common.nav />
        <@leftNav.leftNav />
        <div id="main">
            <h1>Listings for all future dates</h1>

            <table>
                <tr>
                    <th>Date</th>
                    <th>Time</th>
                    <th>Show</th>
                    <th>Title</th>
                    <th>Status</th>
                    <th>Channel</th>
                    <th>Season</th>
                    <th>Details</th>
                </tr>
                <@s.iterator value="listings">
                    <tr>
                        <td><@s.date name="listingDateTime" format="EEE dd MMM"/></td>
                        <td><@s.date name="listingDateTime" format="HH:mm"/></td>
                        <td><@s.property value="episode.tvShow.tvShowName" /></td>
                        <td><@s.property value="episode.episodeTitle"/></td>
                        <td><@s.property value="episode.status" /></td>
                        <td><@s.property value="channel.channelName"/></td>
                        <td><@s.property value="episode.episodeNumber"/></td>
                        <td>
                            <a href="
                               <@s.url action="viewEpisode">
                                   <@s.param name="tvShowName"><@s.property value="episode.tvShow.tvShowName"/></@s.param>
                                   <@s.param name="episodeTitle"><@s.property value="episode.episodeTitle"/></@s.param>
                               </@s.url>
                               ">Details</a>
                        </td>
                    </tr>
                </@s.iterator>
            </table>
        </div>
    </body>
</html>
</#escape>
