<#escape x as x?html>
<#import "../../common/common.ftl" as common/>
<#import "../../navigation/showNav.ftl" as leftNav/>
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
        </div>
    </body>
</html>
</#escape>
