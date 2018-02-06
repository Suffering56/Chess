app.factory("initService", function ($http, $location, $window) {
    const GAME_PREFIX = "/game/";
    const POSITION_PREFIX = "/position/";

    var result = {};
    var onGameContinue;
    var path = $location.path();

    result.checkPath = function (scope, _onGameContinue) {
        onGameContinue = _onGameContinue;

        var isNewGame = path.indexOf(GAME_PREFIX) == -1;

        if (isNewGame == true) {
            initNewGamePath(scope.params);
        } else {
            initContinueGamePath(scope.params);
        }
    };

    function initNewGamePath(params) {
        $http({
            method: "GET",
            url: "/api/game/start"
        }).then(function (response) {
            var game = response.data;
            params.gameId = game.id;
            $location.path(GAME_PREFIX + params.gameId + POSITION_PREFIX + params.position, false);
        });
    }

    function initContinueGamePath(params) {
        var pathParts = path.split("/");

        params.gameId = pathParts[2];
        if (path.indexOf(POSITION_PREFIX) != -1) {
            params.position = pathParts[4];
        } else {
            $location.path(GAME_PREFIX + params.gameId + POSITION_PREFIX + params.position, false);
        }

        checkPlayerSide(params);
    }

    function checkPlayerSide(params) {
        $http({
            method: "GET",
            url: "/api/game/" + params.gameId + "/player/side"
        }).then(function (response) {
            var playerParams = response.data;
            if (playerParams) {
                params.player.isWhite = playerParams.isWhite;
                params.player.isViewer = playerParams.isViewer;
                if (playerParams.isViewer == true) {
                    alert("player-params not found: you can only view this game");
                    continueGame(onGameContinue);
                } else if (playerParams.isWhite != null) {
                    continueGame(onGameContinue);
                }
            } else {
                alert("game not found");
                $window.location.href = "/";
            }
        });
    }

    function continueGame(callback) {
        if (callback) {
            callback();
        }
    }

    return result;
});