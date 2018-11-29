cordova.define("cordova-plugin-takephoto.takephoto", function (require, exports, module) {

    var exec = require('cordova/exec');

    function TakePhoto() {
    }

    TakePhoto.prototype = {
        takePhoto: function (config, callback) {
            var successCallback = function (message) {
                callback.err = null;
                callback.fileData = message;
            };

            var errorCallback = function (message) {
                callback.err = message;
                callback.fileData = null;
            };

            exec(successCallback, errorCallback, "TakePhotoPlugin", "takePhoto", [config]);
        }
    }

    module.exports = new TakePhoto();
});