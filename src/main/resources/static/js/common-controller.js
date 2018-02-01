app.controller("common", function ($scope, $http, initService) {

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
            method: "POST",
            url: "/api/game/" + $scope.params.gameId + "/move",
            data: {
                position: $scope.params.position,
                selectedRow: rowIndex,
                selectedColumn: columnIndex
            }
        }).then(function (response) {
            $scope.piecesMatrix = response.data.cells;
        });
    }

    $scope.getCellClass = function (cell, rowIndex, columnIndex) {
        if ((rowIndex + columnIndex) % 2 == 0) {
            return "white";
        } else {
            return "black";
        }
    };

    $scope.getInnerCellClass = function (cell) {
        var result = ["", ""];

        if (cell) {
            if (cell.piece) {
                result[0] = cell.piece + "-" + cell.side;
            }

            if (cell.selected == true) {
                result[1] = "selected";
            } else if (cell.available == true) {
                result[1] = "available";
            }
        }

        return result;
    };


});