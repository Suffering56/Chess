var app = angular.module('app', []);

app.controller('common', function ($scope) {
  $scope.url = {
    board: 'chessboard.html'
  };
  $scope.greeting = {id: 'xxx', content: 'Hello World!'};

  $scope.rows = [1, 2, 3, 4, 5, 6, 7, 8];
  $scope.columns = [1, 2, 3, 4, 5, 6, 7, 8];


  $scope.getCellClass = function x(rowIndex, columnIndex) {
    console.log("rowIndex: " + rowIndex + ", columnIndex: " + columnIndex)
    if (rowIndex == 4 && columnIndex == 4) {
      return "pawn";
    } else {
      return "";
    }
  }

});