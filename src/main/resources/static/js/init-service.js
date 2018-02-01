app.factory("initService", function ($http, $location) {
    var result = {};

    result.initParams = function (scope, callback) {
        var params = {
            gameId: null,
            position: 0,
            showBoard: false
        };
        scope.params = params;

        const GAME_PREFIX = "/game/";
        const POSITION_PREFIX = "/position/";

        var path = $location.path();
        var isNewGame = path.indexOf(GAME_PREFIX) == -1;
        var pathParts = path.split("/");

        if (isNewGame != true) {
            params.gameId = pathParts[2];
            if (path.indexOf(POSITION_PREFIX) != -1) {
                params.position = pathParts[4];
            } else {
                $location.path(GAME_PREFIX + params.gameId + POSITION_PREFIX + params.position, false);
            }

            callback(params);
        } else {
            $http({
                method: "GET",
                url: "/api/game/start"
            }).then(function (response) {
                var game = response.data;
                params.gameId = game.id;
                $location.path(GAME_PREFIX + params.gameId + POSITION_PREFIX + params.position, false);

                callback(params);
            });
        }
    };

    return result;
});