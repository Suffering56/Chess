app.factory('initService', function ($http, $location) {
    var result = {};

    result.initParams = function (callback) {
        var params = {
            gameId: null,
            position: 0,
            showBoard: false
        };

        var path = $location.path();
        result.isNewGame = path.indexOf('/game/') == -1;
        var pathParts = path.split('/');

        if (result.isNewGame != true) {
            params.gameId = pathParts[2];
            if (path.indexOf('/move/') != -1) {
                params.position = pathParts[4];
            } else {
                $location.path('/game/' + params.gameId + '/move/' + params.position, false);
            }

            callback(params);
        } else {
            $http({
                method: 'GET',
                url: '/api/game/start'
            }).then(function (response) {
                var game = response.data;
                params.gameId = game.id;
                $location.path('/game/' + params.gameId + '/move/' + params.position, false);

                callback(params);
            });
        }

        return null;
    };

    return result;
});