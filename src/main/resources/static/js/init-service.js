app.factory('initService', function ($http, $location) {
    var result = {};

    result.initParams = function() {
        var params = {
            gameId: 0,
            moveIndex: 0,
            showBoard: false
        };

        var path = $location.path();
        result.isNewGame = path.indexOf('/game/') == -1;
        var pathParts = path.split('/');

        if (result.isNewGame != true) {
            params.gameId = pathParts[2];
            if (path.indexOf('/move/') != -1) {
                params.moveIndex = pathParts[4];
            } else {
                $location.path('/game/' + params.gameId + '/move/' + params.moveIndex, false);
            }
        } else {
            $location.path('/game/' + params.gameId + '/move/' + params.moveIndex, false);
        }

        return params;
    };

    return result;
});