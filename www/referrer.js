module.exports = {
    get: function (onSuccess, onError) {
        cordova.exec(onSuccess, onError, 'Referrer', 'get');
    }
};