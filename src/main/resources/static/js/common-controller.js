app.controller("common", function ($scope, $http, initService) {
    $scope.horizontalLabels = ["h", "g", "f", "e", "d", "c", "b", "a"];
    $scope.verticalLabels = ["1", "2", "3", "4", "5", "6", "7", "8"];

    $scope.params = {
        gameStarted: false,
        game: {
            id: null,
            position: null
        },
        player: {
            isWhite: null,
            isViewer: false
        }
    };

    var params = $scope.params;
    var game = params.game;
    var player = params.player;

    var onGameStarted = function (response) {
        $scope.piecesMatrix = response.data.piecesMatrix;
        params.gameStarted = true;
    };

    initService.init(params, onGameStarted);

    $scope.sideClick = initService.sideClick;


    var selectedCell;
    var availablePoints;

    $scope.doClick = function (cell) {
        if (player.isViewer == true || cell.selected == true) {
            return;
        }

        if (cell.available == true) {
            applyMove(cell);
        } else {
            selectCell(cell);
        }
    };

    function applyMove(cell) {
        $http({
            method: "POST",
            url: "/api/game/" + game.id + "/move",
            data: {
                from: {
                    rowIndex: selectedCell.rowIndex,
                    columnIndex: selectedCell.columnIndex
                },
                to: {
                    rowIndex: cell.rowIndex,
                    columnIndex: cell.columnIndex
                }
            }
        }).then(function (response) {
            handleApplyMove(response.data);
        });
    }

    function handleApplyMove(paramsDTO) {
        game.position = paramsDTO.game.position;
        $scope.piecesMatrix = paramsDTO.piecesMatrix;
        initService.updatePath();
    }

    function selectCell(cell) {
        var expectedSide = (game.position % 2 == 0) ? "white" : "black";

        if (cell.side && cell.side == expectedSide) {
            if (selectedCell) {
                selectedCell.selected = false;
            }
            cell.selected = true;
            selectedCell = cell;

            getAvailableMoves(cell);
        }
    }

    function getAvailableMoves(cell) {
        $http({
            method: "GET",
            url: "/api/game/" + game.id + "/move",
            params: {
                rowIndex: cell.rowIndex,
                columnIndex: cell.columnIndex
            }
        }).then(function (response) {
            handleAvailableMoves(response.data);
        });
    }

    var handleAvailableMoves = function (points) {
        if (availablePoints) {
            availablePoints.map(function (point) {
                getCellByPoint(point).available = false;
            });
        }
        points.map(function (point) {
            getCellByPoint(point).available = true;
        });
        availablePoints = points;
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