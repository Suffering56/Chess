var app = angular.module("app", []);

app.config(["$locationProvider", function ($locationProvider) {
    $locationProvider.html5Mode({
        enabled: true,
        requireBase: false
    });
}]);

app.filter('chessboardReverse', function() {
    return function(items, isWhite) {
        if (isWhite) {
            return items.slice().reverse();
        } else {
            return items;
        }
    };
});
