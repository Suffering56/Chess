app.factory("initService", function ($http, $location, $window) {
    const GAME_PREFIX = "/game/";
    const POSITION_PREFIX = "/position/";

    var result = {};
    var path = $location.path();
    var onGameStarted;
    var params;

    result.checkPathAndInit = function (_params, _onGameStarted) {
        onGameStarted = _onGameStarted;
        params = _params;
        var isNewGame = path.indexOf(GAME_PREFIX) == -1;

        if (isNewGame == true) {
            initNewGamePath();
        } else {
            initContinueGamePath();
        }
    };

    function initNewGamePath() {
        $http({
            method: "GET",
            url: "/api/init"
        }).then(function (response) {
            var responseGame = response.data;
            params.game.id = responseGame.id;
            params.game.position = responseGame.position;
            $location.path(GAME_PREFIX + params.game.id + POSITION_PREFIX + params.game.position);
        });
    }

    function initContinueGamePath() {
        var pathParts = path.split("/");

        params.game.id = pathParts[2];
        if (path.indexOf(POSITION_PREFIX) != -1) {
            params.game.position = pathParts[4];
        } else {
            $location.path(GAME_PREFIX + params.game.id + POSITION_PREFIX + params.game.position);
        }

        checkPlayerSide();
    }

    function checkPlayerSide() {
        $http({
            method: "GET",
            url: "/api/init/" + params.game.id + "/side"
        }).then(function (response) {
            var playerParams = response.data;

            if (playerParams) {
                params.player.isWhite = playerParams.isWhite;
                params.player.isViewer = playerParams.isViewer;

                if (playerParams.isViewer == true) {
                    alert("player-params not found: you can only view this game");
                    call(loadStartArrangement());
                } else if (playerParams.isWhite != null) {
                    call(loadStartArrangement());
                }
            } else {
                alert("game not found");
                $window.location.href = "/";
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
            call(loadStartArrangement());
        });
    };

    function loadStartArrangement() {
        $http({
            method: "GET",
            url: "/api/init/" + params.game.id + "/arrangement"
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