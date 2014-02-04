<#escape x as x?html>
<#import "../common/common.ftl" as common/>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en-gb" lang="en-gb" dir="ltr">
    <head>
        <title>Listings By Status Hub</title>
        <@s.head />
    </head>
    <body>
        <@common.nav />

        <div id="main">
            <h1>Status listings required for:</h1>
            <ul>
                <@s.iterator value="episodeStatuses">
                    <li>
                        <a href="
                           <@s.url action="viewListingForStatus">
                               <@s.param name="episodeStatus"><@s.property /></@s.param>
                           </@s.url>
                           "><@s.property /></a>
                    </li>
                </@s.iterator>
            </ul>
        </div>
    </body>
</html>
</#escape>
