<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Guicy</title>
        <script src="http://code.jquery.com/jquery-latest.js"></script>
    </head>
<body>
    <h1>a feed example</h1>
  

    <div class='notGuicyBeans'>
        <h2>not Guicy - Beans</h2>
    </div>
     
    <div class='notGuicyBean'>
        <h2>not Guicy single bean</h2>
    </div>

    <div class='guicyBeans'>
        <h2>Guicy - Beans</h2>
    </div>
     
    <div class='guicyBean'>
        <h2>Guicy single bean</h2>
    </div>
    <script>
        
        $.getJSON('http://localhost:8090/Guicy-1.0-SNAPSHOT/feed/notGuicy/beans.json',
            function(data) {
                var items = [];
                $.each(data, function(key, val){
                    items.push('<li id="' + key + '">id:' + val.publicId + ', value:' + val.value + ',nonPublicThing:' + val.nonPublicThing + '</li>');
                });
                $('<ul/>bob', {
                    'id': 'beans',
                    html: items.join('')
                }).appendTo('.notGuicyBeans');    
            });
            

        $.getJSON('http://localhost:8090/Guicy-1.0-SNAPSHOT/feed/notGuicy/bean_1.json',
            function(data) {
                var items = [];
                items.push('<li id="1">id:' + data.publicId + ', value:' + data.value + ',nonPublicThing:' + data.nonPublicThing + '</li>');
                $('<ul/>', {
                    'id': 'bean',
                    html: items.join('')
                }).appendTo('.notGuicyBean');    
            });

        $.getJSON('http://localhost:8090/Guicy-1.0-SNAPSHOT/feed/guicy/beans.json',
            function(data) {
                var items = [];
                $.each(data, function(key, val){
                    items.push('<li id="' + key + '">id:' + val.publicId + ', value:' + val.value + ',nonPublicThing:' + val.nonPublicThing + '</li>');
                });
                $('<ul/>bob', {
                    'id': 'beans',
                    html: items.join('')
                }).appendTo('.guicyBeans');    
            });
            
        $.getJSON('http://localhost:8090/Guicy-1.0-SNAPSHOT/feed/guicy/bean_1.json',
            function(data) {
                var items = [];
                items.push('<li id="1">id:' + data.publicId + ', value:' + data.value + ',nonPublicThing:' + data.nonPublicThing + '</li>');
                $('<ul/>', {
                    'id': 'bean',
                    html: items.join('')
                }).appendTo('.guicyBean');    
            });
    </script>    

</body>
</html>