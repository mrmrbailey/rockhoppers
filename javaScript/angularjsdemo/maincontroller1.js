app.controller("MainController", function($scope){
  $scope.selectedPerson=0;
  $scope.selectedGenre=null;
  $scope.people=[
    {
      id:0
      name:'Leon',
      music:[
        'Rock',
        'Metal'
      ]
    },
    {
      id:1
      name:'Chris',
      music:[
        'Rock',
        'Indie'
      ]
    },
    {
      id:2
      name:'Harry',
      music:[
        'Rock'
      ]
    }
  ];
});