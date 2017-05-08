angular
    .module('app')
    .factory('CurrencyFactory', CurrencyFactory);

function CurrencyFactory($resource) {
    return $resource('api/currencies/:id', {id: '@id'}, {
        getAll: {
            method: 'GET',
            url: 'api/currencies/all',
            isArray: true
        },
        getByCode: {
            method: 'GET',
            url: 'api/currencies/code/:currencyCode'
        }
    });
}
