<#escape x as x?html>
<#import "../common/common.ftl" as common/>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en-gb" lang="en-gb" dir="ltr">
    <head>
        <title>Listings Hub</title>
        <@s.head />
    </head>
    <body>
        <@common.nav />
        <div id="main">
            <h1>Listings Hub page</h1>
            Common listings
            <ul>
                <li><a href="<@s.url action="viewDailyListing" listingDate="${today?date}" />">Today's Listings</a></li>
                <li><a href="<@s.url action="viewFutureListing"/>">Future</a></li>
                <li><a href="<@s.url action="viewListingsForTvShowHub"/>">Tv Shows Listings</a></li>
                <li><a href="<@s.url action="viewListingsForStatusHub"/>">Listings by State</a></li>
                <li><a href="<@s.url action="viewListingsForChannelHub"/>">Listings by Channel</a></li>
                <li><a href="<@s.url action="viewListingForUnwatchedShows"/>">Listings for Unwatched shows</a></li>
            </ul>

            All Listings
            <ul>
                <@s.iterator value="listingDates">
                    <li>
                        <a href="<@s.url action="viewDailyListing">
                                    <@s.param name="listingDate"><@s.property /></@s.param>
                                  </@s.url>"><@s.property />
                        </a>
                    </li>
                </@s.iterator>
            </ul>
        </div>
    </body>
</html>
</#escape>
