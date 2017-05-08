angular
    .module('app')
    .config(AppStates);

function AppStates($stateProvider, $urlRouterProvider) {
    [
        {
            name: 'root',
            url: '/',
            controller: 'RootController'
        },
        {
            name: 'signIn',
            url: '/sign_in',
            controller: 'SignInController as self',
            templateUrl: 'templates/sign_in.html'
        },
        {
            name: 'signUp',
            url: '/sign_up',
            controller: 'SignUpController as self',
            templateUrl: 'templates/sign_up.html'
        },

        {
            name: 'transactions',
            url: '/transactions',
            controller: 'TransactionListController as self',
            templateUrl: 'templates/transactions/index.html'
        },
        {
            name: 'wallets',
            url: '/wallets',
            controller: 'WalletListController as self',
            templateUrl: 'templates/wallets/index.html'
        },
        {
            name: 'categories',
            url: '/categories',
            views: {
                '': {templateUrl: 'templates/categories/categories.income.outgo.html'},
                'outgo@categories': {
                    controller: 'CategoryOutgoListController as self',
                    templateUrl: 'templates/categories/index.html'
                },
                'income@categories': {
                    controller: 'CategoryIncomeListController as self',
                    templateUrl: 'templates/categories/index.html'
                }
            }
        },
        {
            name: 'profile',
            url: '/profile',
            controller: 'ProfileController as self',
            templateUrl: 'templates/users/profile.html'
        },
        {name: 'otherwise', url: '/otherwise', templateUrl: '404.html'}
    ].forEach(function (state) {
        $stateProvider.state(state)
    });

    $urlRouterProvider.when('', '/');
    $urlRouterProvider.otherwise('/otherwise');
}
