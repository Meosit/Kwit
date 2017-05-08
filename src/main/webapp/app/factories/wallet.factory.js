angular
    .module('app')
    .factory('WalletFactory', WalletFactory);

function WalletFactory($resource) {
    return $resource('api/wallets/:id', {id: '@id'}, {
        update: {method: 'PUT'},
        delete: {method: 'DELETE'},
        softDelete: {
            method: 'DELETE',
            url: 'api/wallets/:id?newWallet=:newWallet',
            params: {
                newWallet: '@newWallet'
            }
        },
        getAll: {
            method: 'GET',
            url: 'api/wallets/all',
            isArray: true
        },
        forecast: {
            method: 'GET',
            url: 'api/wallets/forecast'
        }
    });
}