var app = angular.module('app', []);

app.controller('common', function ($scope, $http) {

  $scope.showPieces = false;
  var previousSelectedCell;

  $http({
    method: 'GET',
    url: '/api/piece/arrangement/start'
  }).then(function (response) {
    $scope.piecesMatrix = response.data;
    $scope.showPieces = true;
  });

  $scope.doClick = function (cell, rowIndex, columnIndex) {
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
      url: '/api/piece/moves/' + rowIndex + '/' + columnIndex
    }).then(function (response) {
      $scope.piecesMatrix = response.data;
    });
  }

  $scope.getCellClass = function (cell, rowIndex, columnIndex) {
    var result = ['', ''];

    if ((rowIndex + columnIndex) % 2 == 0) {
      result[0] = 'white';
    } else {
      result[0] = 'black';
    }

    if (cell.available == true) {
      result[1] = 'available';
    }

    return result;
  };

  $scope.getInnerCellClass = function (cell) {
    var result = ['', ''];

    if (cell) {
      if (cell.piece) {
        result[0] = cell.piece + '-' + cell.side;
      }

      if (cell.selected == true) {
        result[1] = 'selected';
      }
    }

    return result;
  };

});