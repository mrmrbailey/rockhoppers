<#escape x as x?html>
<#import "../common/common.ftl" as common/>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en-gb" lang="en-gb" dir="ltr">
    <head>
        <title>TV App</title>
        <@s.head />
    </head>
    <body>
        <@common.nav />
        <div id="main">
            <h1><@s.property value="welcomeMessage"/></h1>

            <ul>
                <li><a href="<@s.url action="viewTvShowHub"/>">View TV Shows</a></li>
                <li><a href="<@s.url action="viewListingsHub"/>">View Listings</a></li>
            </ul>
        </div>
    </body>
</html>
</#escape>
