angular
    .module('app')
    .factory('TransactionFactory', TransactionFactory);

function TransactionFactory($resource) {
    return $resource('api/transactions/:id', {id: '@id'}, {
        get: {
            method: 'GET',
            url: 'api/transactions',
            params: {
                page: '@page'
            }
        },
        update: {method: 'PUT'},
        delete: {method: 'DELETE'}
    });
}