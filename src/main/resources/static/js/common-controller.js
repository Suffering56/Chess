app.controller("common", function ($scope, $http, initService) {
    $scope.horizontalLabels = ["h", "g", "f", "e", "d", "c", "b", "a"];
    $scope.verticalLabels = ["1", "2", "3", "4", "5", "6", "7", "8"];

    $scope.params = {
        gameId: null,
        position: 0,
        gameStarted: false,
        player: {
            isWhite: null,
            isViewer: true
        }
    };

    var params = $scope.params;
    var player = params.player;

    initService.checkPath($scope, loadStartArrangement);

    $scope.sideClick = function (isWhite) {
        $http({
            method: "POST",
            url: "/api/game/" + params.gameId + "/player/side",
            data: {
                isWhite: isWhite
            }
        }).then(function () {
            player.isWhite = isWhite;
            loadStartArrangement();
        });
    };

    function loadStartArrangement() {
        $http({
            method: "GET",
            url: "/api/game/" + params.gameId + "/start/arrangement"
        }).then(function (response) {
            $scope.piecesMatrix = response.data.cells;
            params.gameStarted = true;
        });
    }

    var previousSelectedCell;

    $scope.doClick = function (cell) {
        if (player.isViewer == true) {
            return;
        }
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
        var result = [];

        if (cell.piece) {
            result.push('piece');
            result.push(cell.piece + "-" + cell.side);
        }

        if (cell.selected == true) {
            result.push('selected');
        } else if (cell.available == true) {
            result.push('available');
        }

        return result;
    };

});