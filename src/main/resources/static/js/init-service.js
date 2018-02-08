app.factory("initService", function ($http, $location, $window) {
    const GAME_PREFIX = "/game/";
    const POSITION_PREFIX = "/position/";

    var result = {};
    var path = $location.path();
    var onGameStarted;
    var params;

    result.init = function (_params, _onGameStarted) {
        onGameStarted = _onGameStarted;
        params = _params;
        var isNewGame = path.indexOf(GAME_PREFIX) == -1;

        if (isNewGame == true) {
            createNewGame();
        } else {
            extractGameParamsByPath();
            checkGame();
        }
    };

    result.updatePath = function () {
        $location.path(GAME_PREFIX + params.game.id + POSITION_PREFIX + params.game.position);
    };

    function extractGameParamsByPath() {
        var pathParts = path.split("/");
        params.game.id = pathParts[2];
        if (path.indexOf(POSITION_PREFIX) != -1) {
            params.game.position = pathParts[4];
        }
    }

    function createNewGame() {
        $http({
            method: "GET",
            url: "/api/init"
        }).then(function (response) {
            var game = response.data;
            params.game.id = game.id;
            params.game.position = game.position;
        });
    }

    function checkGame() {
        $http({
            method: "GET",
            url: "/api/init/" + params.game.id
        }).then(function (response) {
            var game = response.data;
            if (!game) {
                alert("game not found. Starting a new game...");
                $window.location.href = "/";
            } else {
                params.game.id = game.id;

                if (params.game.position) {
                    if (params.game.position > game.position) {
                        params.game.position = game.position;
                    }
                } else {
                    params.game.position = game.position;
                }

                checkPlayerSide();
            }
        });
    }

    function checkPlayerSide() {
        $http({
            method: "GET",
            url: "/api/init/" + params.game.id + "/side"
        }).then(function (response) {
            var paramsPlayerDTO = response.data;

            params.player.isWhite = paramsPlayerDTO.isWhite;
            params.player.isViewer = paramsPlayerDTO.isViewer;

            if (paramsPlayerDTO.isViewer == true) {
                alert("all gaming places are occupied - you can only view this game");
                call(getArrangementByPosition());
            } else if (paramsPlayerDTO.isWhite != null) {
                call(getArrangementByPosition());
            }
        });
    }

    result.sideClick = function (isWhite) {
        $http({
            method: "POST",
            url: "/api/init/" + params.game.id + "/side",
            data: {
                isWhite: isWhite
            }
        }).then(function () {
            params.player.isWhite = isWhite;
            call(getArrangementByPosition());
        });
    };

    function getArrangementByPosition() {
        result.updatePath();

        $http({
            method: "GET",
            url: "/api/init/" + params.game.id + "/arrangement/" + params.game.position
        }).then(function (response) {
            call(onGameStarted(response));
        });
    }

    function call(callback) {
        if (callback) {
            callback();
        }
    }

    return result;
});