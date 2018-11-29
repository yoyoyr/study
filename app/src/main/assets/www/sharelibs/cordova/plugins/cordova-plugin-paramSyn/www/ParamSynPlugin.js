cordova.define("cordova-plugin-paramSyn.ParamSynPlugin", function (o, n, t) {
    var c = function () {
    };
    c.prototype.paramSyn = function (o, n) {
        var t = function (o) {
            n(!1, o)
        }, c = function (o) {
            n(!0, o)
        };
        cordova.exec(t, c, "ParamSynPlugin", "paramSyn", o)
    }, t.exports = new c
});