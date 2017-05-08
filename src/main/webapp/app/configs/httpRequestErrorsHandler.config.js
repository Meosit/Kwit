angular
    .module('app')
    .config(HttpRequestErrorsHandler);

function HttpRequestErrorsHandler($httpProvider) {
    $httpProvider.interceptors.push(interceptor);

    function interceptor($q, $rootScope) {
        return {
            responseError: function (response) {
                var errors = [];
                if (response.status !== 404) {
                    if (!!response.data.errors) {
                        errors = errors.concat(response.data.errors);
                    } else if (!!response.data.error) {
                        errors.push({title: 'Auth', message: response.data.error_description})
                    }
                }
                $rootScope.$broadcast('httpError', errors);
                return $q.resolve(response);
            }
        };
    }
}