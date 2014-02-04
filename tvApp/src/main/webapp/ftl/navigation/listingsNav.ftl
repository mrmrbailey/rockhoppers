<#macro leftNav showDates=true>
    <div id="leftnav">
        <ul>
            <#if showDates>
                <li><a href="<@s.url action="viewDailyListing" listingDate="${today?date}" />">Today's Listings</a></li>
            </#if>
            <li><a href="<@s.url action="viewFutureListing"/>">Future</a></li>
            <li><a href="<@s.url action="viewListingsForTvShowHub"/>">Tv Shows Listings</a></li>
            <li><a href="<@s.url action="viewListingsForStatusHub"/>">Listings by State</a></li>
            <li><a href="<@s.url action="viewListingsForChannelHub"/>">Listings by Channel</a></li>
            <li><a href="<@s.url action="viewListingForUnwatchedShows"/>">Listings for Unwatched shows</a></li>
            <#if showDates>
                <li><a href="<@s.url action="viewDailyListing" listingDate="${nextListingDate?date}" />">Next Days Listing Listing</a></li>
                <li><a href="<@s.url action="viewDailyListing" listingDate="${previousListingDate?date}" />">Previous Days Listing</a></li>
            </#if>
        </ul>
    </div>
</#macro>
