app.controller("common", function ($scope, $http, initService) {
    $scope.player = {
        isWhite: true
    };

    initService.initParams($scope, function (params) {
        $http({
            method: "GET",
            url: "/api/game/" + params.gameId + "/start"
        }).then(function (response) {
            $scope.piecesMatrix = response.data.cells;
            $scope.params.showBoard = true;
        });
    });

    var previousSelectedCell;

    $scope.doClick = function (cell) {
        if (previousSelectedCell) {
            previousSelectedCell.selected = false;
        }
        cell.selected = true;
        previousSelectedCell = cell;

        update(cell);
    };

    function update(cell) {
        $http({
            method: "POST",
            url: "/api/game/" + $scope.params.gameId + "/move",
            data: {
                position: $scope.params.position,
                selectedRow: cell.rowIndex,
                selectedColumn: cell.columnIndex
            }
        }).then(function (response) {
            $scope.piecesMatrix = response.data.cells;
        });
    }

    $scope.getCellClass = function (cell) {
        if ((cell.rowIndex + cell.columnIndex) % 2 == 0) {
            return "white";
        } else {
            return "black";
        }
    };

    $scope.getInnerCellClass = function (cell) {
        var result = ["", ""];

        if (cell.piece) {
            result[0] = cell.piece + "-" + cell.side;
        }

        if (cell.selected == true) {
            result[1] = "selected";
        } else if (cell.available == true) {
            result[1] = "available";
        }

        return result;
    };


});