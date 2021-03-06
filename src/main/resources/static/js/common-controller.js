app.controller("common", function ($scope, $http, $window, initService) {
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
        },
        checkWhiteKing: false,
        checkBlackKing: false
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
        var url = "/api/game/" + game.id + "/move";
        $http({
            method: "POST",
            url: url,
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
        }, function (reason) {
            alert("You have already opened game, please refresh current page if you want continue game!");
        });
    }

    function handleApplyMove(paramsDTO) {
        game.position = paramsDTO.game.position;
        $scope.piecesMatrix = paramsDTO.piecesMatrix;
        initService.updatePath();
    }

    function selectCell(cell) {

        clearAvailablePoints();
        if (selectedCell) {
            selectedCell.selected = false;
        }

        if (cell.side && cell.side == getExpectedSide()) {
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
        points.map(function (point) {
            getCellByPoint(point).available = true;
        });
        availablePoints = points;
    };

    function clearAvailablePoints() {
        if (availablePoints) {
            availablePoints.map(function (point) {
                getCellByPoint(point).available = false;
            });
        }
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
            result.push("piece");
            result.push(cell.piece + "-" + cell.side);
            if (cell.piece == "king") {
                if ((params.checkWhiteKing == true && cell.side == "white") || (params.checkBlackKing == true && cell.side == "black")) {
                    result.push("check");
                }
            }
        }

        if (cell.selected == true) {
            result.push('selected');
        } else if (cell.available == true) {
            result.push('available');
            if (cell.side == getEnemySide()){
                result.push('capture');
            }
        }

        return result;
    };

    function getCell(rowIndex, columnIndex) {
        return $scope.piecesMatrix[rowIndex][columnIndex];
    }

    function getCellByPoint(point) {
        return getCell(point.rowIndex, point.columnIndex);
    }

    function getExpectedSide() {
        return (game.position % 2 == 0) ? "white" : "black";
    }

    function getEnemySide() {
        return (game.position % 2 == 0) ? "black" : "white";
    }

});