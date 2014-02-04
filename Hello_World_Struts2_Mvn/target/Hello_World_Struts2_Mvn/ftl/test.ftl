<#escape x as x?html>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en-gb" lang="en-gb" dir="ltr">
    <head>
        <title>Hello Workd</title>
        <@s.head />
    </head>
    <body>
        <div id="main">
            <h1><@s.property value="messageStore.message"/></h1>
<h2>maybe this is it ${messageStore.message}</h2>

            <ul>
                <li><a href="<@s.url action="hello"/>">Hello</a></li>
                <li><a href="<@s.url action="draw"/>">draws</a></li>
<li><a href="<@s.url action="register"/>">register</a></li>
register
            </ul>
        </div>
    </body>
</html>
</#escape>
