app.controller("common", function ($scope, $http, initService) {
    $scope.horizontalLabels = ["h", "g", "f", "e", "d", "c", "b", "a"];
    $scope.verticalLabels = ["1", "2", "3", "4", "5", "6", "7", "8"];

    $scope.params = {
        gameStarted: false,
        game: {
            id: null,
            position: 0
        },
        player: {
            isWhite: null,
            isViewer: false
        }
    };

    var params = $scope.params;
    var game = params.game;
    var player = params.player;

    initService.checkPathAndInit(params, function (response) {
        $scope.piecesMatrix = response.data.cells;
        params.gameStarted = true;
    });

    $scope.sideClick = initService.sideClick;


    var prevSelectedCell;
    var prevAvailablePoints;

    $scope.doClick = function (cell) {
        if (player.isViewer == true || cell.selected == true) {
            return;
        }
        var expectedSide = (game.position % 2 == 0) ? "white" : "black";

        if (cell.side && cell.side == expectedSide) {
            if (prevSelectedCell) {
                prevSelectedCell.selected = false;
            }
            cell.selected = true;
            prevSelectedCell = cell;

            getAvailableMoves(cell);
        }
    };

    function getAvailableMoves(cell) {
        $http({
            method: "POST",
            url: "/api/game/" + game.id + "/move",
            data: {
                rowIndex: cell.rowIndex,
                columnIndex: cell.columnIndex
            }
        }).then(function (response) {
            handleAvailableMoves(response.data);
        });
    }

    var handleAvailableMoves = function (points) {
        if (prevAvailablePoints) {
            prevAvailablePoints.map(function (point) {
                getCellByPoint(point).available = false;
            });
        }
        points.map(function (point) {
            getCellByPoint(point).available = true;
        });
        prevAvailablePoints = points;
    };

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

    function getCell(rowIndex, columnIndex) {
        return $scope.piecesMatrix[rowIndex][columnIndex];
    }

    function getCellByPoint(point) {
        return getCell(point.rowIndex, point.columnIndex);
    }

});