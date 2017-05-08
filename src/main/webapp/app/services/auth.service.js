angular
    .module('app')
    .service('AuthService', AuthService);

function AuthService($q, $http, $state, $cookies, UserFactory) {

    var self = {
        prepareAuthInfo: prepareAuthInfo,
        tryAuthorize: tryAuthorize,
        unauthorize: unauthorize,
        signIn: signIn,
        signUp: signUp,
        signOut: signOut,
        isAuthorized: false
    };

    return self;

    function prepareAuthInfo() {
        return $q(function (resolve) {
            if (!self.isAuthorized) {
                tryAuthorize().then(resolve);
            } else {
                resolve();
            }
        })
    }

    function tryAuthorize() {
        return $q(function (resolve) {
            var accessToken = $cookies.get('kwit-access-token');
            if (!!accessToken) {
                injectAccessTokenToOutgoingHttpRequests(accessToken);
                self.isAuthorized = true;
            }
            resolve();
        });
    }

    function unauthorize() {
        $cookies.remove('kwit-access-token');
        rejectAccessTokenToOutgoingHttpRequests();
        self.isAuthorized = false;
        $state.go('signIn');
    }

    function signIn(email, password, needToRemember) {
        var requestParams = {
            username: email,
            password: password,
            grant_type: 'password'
        };

        $http(oauthRequestConfig('/Kwit/oauth/token', requestParams))
            .then(function (response) {
                handleAccessTokenRequest(response, needToRemember)
            });
    }

    function signUp(newUser) {
        $http.post('/Kwit/api/users/register', newUser).then(function (response) {
            if (response.status === 200) {
                signIn(newUser.email, newUser.password, true);
            }
        });
    }

    function signOut() {
        return makeLogoutRequest().then(self.unauthorize);
    }

    function handleAccessTokenRequest(response, needToRemember) {
        var accessToken = response.data.access_token;
        var tokenExpiresIn = response.data.expires_in;

        if (!!accessToken) {
            injectAccessTokenToOutgoingHttpRequests(accessToken);
            if (needToRemember) {
                rememberAccessToken(accessToken, tokenExpiresIn);
            }
            $state.go('root');
        }
    }

    function rememberAccessToken(accessToken, tokenExpiresIn) {
        var expirationDate = new Date();
        expirationDate.setSeconds(expirationDate.getSeconds() + tokenExpiresIn);
        $cookies.put('kwit-access-token', accessToken, {expires: expirationDate});
    }

    function injectAccessTokenToOutgoingHttpRequests(token) {
        $http.defaults.headers.common['Authorization'] = 'Bearer ' + token;
    }

    function rejectAccessTokenToOutgoingHttpRequests() {
        $http.defaults.headers.common['Authorization'] = undefined;
    }

    function makeLogoutRequest() {
        return $http.post('/Kwit/api/users/logout');
    }

    function oauthRequestConfig(url, params) {
        return {
            url: url,
            method: 'POST',
            headers: {
                'Authorization': 'Basic d2ViX2NsaWVudDprd2l0X3dlYl9jbGllbnRfc2VjcmV0=',
                'Accept': 'application/json'
            },
            params: params
        };
    }

}
