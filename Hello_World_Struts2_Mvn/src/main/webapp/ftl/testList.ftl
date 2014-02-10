<#escape x as x?html>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en-gb" lang="en-gb" dir="ltr">
    <head>
        <title>Hello Workd</title>
        <@s.head />
    </head>
    <body>
        <div id="main">
 <@s.iterator value="draws">
<@s.property/>
</@s.iterator>
        </div>
    </body>
</html>
</#escape>
