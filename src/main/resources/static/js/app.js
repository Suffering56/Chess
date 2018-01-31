var app = angular.module('app', []);

app.config(['$locationProvider', function ($locationProvider) {
  $locationProvider.html5Mode({
    enabled: true,
    requireBase: false
  });
}]);

app.controller('common', function ($scope, $http, $location) {

  console.log('init path', $location.path());
  console.log('init path.length', $location.path().split("/").length);

  //if gameId == null then {game is new -> create new game} else {get latest move}

  $scope.showPieces = false;
  $scope.params = {
    gameId: null
  };
  var previousSelectedCell;

  $http({
    method: 'GET',
    url: '/api/game/start'
  }).then(function (response) {
    $scope.piecesMatrix = response.data;
    $scope.showPieces = true;
  });

  var counter = 0;

  $scope.doClick = function (cell, rowIndex, columnIndex) {
    $location.path('/game/' + counter, true);
    counter++;
    var length = $location.path().split("/").length;
    var gameId = $location.path().split("/")[2];
    console.log('path', $location.path());
    console.log('length',length);
    console.log('gameId', gameId);

    if (cell) {
      if (previousSelectedCell) {
        previousSelectedCell.selected = false;
      }
      cell.selected = true;
      previousSelectedCell = cell;

      update(rowIndex, columnIndex);
    }
  };

  function update(rowIndex, columnIndex) {
    $http({
      method: 'GET',
      url: '/api/game/moves/' + rowIndex + '/' + columnIndex
    }).then(function (response) {
      $scope.piecesMatrix = response.data;
    });
  }

  $scope.getCellClass = function (cell, rowIndex, columnIndex) {
    if ((rowIndex + columnIndex) % 2 == 0) {
      return 'white';
    } else {
      return 'black';
    }
  };

  $scope.getInnerCellClass = function (cell) {
    var result = ['', ''];

    if (cell) {
      if (cell.piece) {
        result[0] = cell.piece + '-' + cell.side;
      }

      if (cell.selected == true) {
        result[1] = 'selected';
      } else if (cell.available == true) {
        result[1] = 'available';
      }
    }

    return result;
  };

});