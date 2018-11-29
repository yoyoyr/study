cordova.define("cordova-plugin-dns.CommPlugin", function (b, a, c) {
	var d = function () {};
	d.prototype.dnsresolve = function (g, h) {
		var f = function (i) {
			h(false, i)
		};
		var e = function (i) {
			h(true, i)
		};
		cordova.exec(f, e, "CommPlugin", "dnsresolve", g)
	};
	d.prototype.getPublicNetState = function (g) {
		var f = function (h) {
			g(false, h)
		};
		var e = function (h) {
			g(true, h)
		};
		cordova.exec(f, e, "CommPlugin", "getPublicNetState", [{}])
	};
	d.prototype.getLbsEnable = function (g) {
		var f = function (h) {
			g(false, h)
		};
		var e = function (h) {
			g(true, h)
		};
		cordova.exec(f, e, "CommPlugin", "getLbsEnable", [{}])
	};
	d.prototype.startAcitvity = function(arg,callback){
		var success = function(data){
			callback(true,data);
		}
		var error = function(data){
			callback(false,data);
		}
		cordova.exec(success, error, "CommPlugin", "startAcitvity", [arg]);
	}
	d.prototype.detectApp = function (g, h) {
         var f = function (i) {
             h(false, i)
         };
         var e = function (i) {
             h(true, i)
         };
         cordova.exec(f, e, "CommPlugin", "detectApp", [g]);
    };
	d.prototype.getPowerOnTime = function(callback){
	    var success = function(data){
        	callback(true,Number(data));
        }
        var error = function(data){
        	callback(false,Number(data));
        }
        cordova.exec(success, error, "CommPlugin", "getPowerOnTime", [{}]);
	}
	d.prototype.getLastPowerOnTime = function(callback){
	    var success = function(data){
            callback(true,Number(data));
        }
        var error = function(data){
            callback(false,Number(data));
        }
        cordova.exec(success, error, "CommPlugin", "getLastPowerOnTime", [{}]);
	}
	d.prototype.getDirectorPassword = function(callback){
	    var success = function(data){
            callback(true,Number(data));
        }
        var error = function(data){
            callback(false,Number(data));
        }
        cordova.exec(success, error, "CommPlugin", "getDirectorPassword", [{}]);
	}

	c.exports = new d()
});
