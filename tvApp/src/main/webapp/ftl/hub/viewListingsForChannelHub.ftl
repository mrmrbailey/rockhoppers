<#escape x as x?html>
<#import "../common/common.ftl" as common/>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en-gb" lang="en-gb" dir="ltr">
    <head>
        <title>Listings By Channel Hub</title>
        <@s.head />
    </head>
    <body>
        <@common.nav />

        <div id="main">
            <h1>Channel listings required for:</h1>
            <ul>
                <@s.iterator value="channels">
                    <li>
                        <a href="
                           <@s.url action="viewListingForChannel">
                               <@s.param name="channel"><@s.property /></@s.param>
                           </@s.url>
                           "><@s.property /></a>
                    </li>
                </@s.iterator>
            </ul>
        </div>
    </body>
</html>
</#escape>
