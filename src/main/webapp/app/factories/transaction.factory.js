angular
    .module('app')
    .factory('TransactionFactory', TransactionFactory);

function TransactionFactory($resource) {
    return $resource('api/transactions/:id', {id: '@id'}, {
        update: {method: 'PUT'},
        delete: {method: 'DELETE'}
    });
}