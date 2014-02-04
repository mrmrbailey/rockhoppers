<#escape x as x?html>
<#import "../common/common.ftl" as common/>
<#import "../navigation/showNav.ftl" as leftNav/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en-gb" lang="en-gb" dir="ltr">
    <head>
        <title><@s.property value="tvShowName" /> Episode Details</title>
        <@s.head />
    </head>
    <body>
        <@common.nav />
        <@leftNav.leftNav false/>
        <div id="main">
            <h1><@s.property value="tvShowName" /> Episodes</h1>
            <table>
                <tr>
                    <th>Title</th>
                    <th>State</th>
                    <th>Season</th>
                    <th>Date</th>
                    <th>Link</th>
                </tr>
                <@s.iterator value="episodes">
                    <tr>
                        <td><@s.property value="episodeTitle" /></td>
                        <td><@s.property value="status" /></td>
                        <td><@s.property value="episodeNumber" /></td>
                        <td><@s.date name="airDate" format="dd/MM/yyyy"/></td>
                        <td>
                            <a href="
                               <@s.url action="viewEpisode">
                                   <@s.param name="tvShowName"><@s.property value="tvShowName"/></@s.param>
                                   <@s.param name="episodeTitle"><@s.property value="episodeTitle"/></@s.param>
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
