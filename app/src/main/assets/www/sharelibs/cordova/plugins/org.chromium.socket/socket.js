cordova.define("org.chromium.socket.Socket",function(e,o,t){var n=cordova.require("cordova/platform"),r=cordova.require("cordova/exec"),c=e("org.chromium.system.network.system.network");o.create=function(e,o,t){"function"==typeof o&&(t=o,o={});var n=t&&function(e){var o={socketId:e};t(o)};r(n,null,"ChromeSocket","create",[e])},o.destroy=function(e){r(null,null,"ChromeSocket","destroy",[e])},o.connect=function(e,o,t,n){var c=n&&function(){n(0)},i=n&&function(){n(-1e3)};r(c,i,"ChromeSocket","connect",[e,o,t])},o.bind=function(e,o,t,n){var c=n&&function(){n(0)},i=n&&function(){n(-1e3)};r(c,i,"ChromeSocket","bind",[e,o,t])},o.disconnect=function(e){r(null,null,"ChromeSocket","disconnect",[e])},o.read=function(e,o,t){"function"==typeof o&&(t=o,o=0),o=o||0;var n=t&&function(e){var o={resultCode:e.byteLength||1,data:e};t(o)},c=t&&function(){var e={resultCode:0};t(e)};r(n,c,"ChromeSocket","read",[e,o])},o.write=function(e,o,t){var n=Object.prototype.toString.call(o).slice(8,-1);if("ArrayBuffer"!=n)throw new Error("chrome.socket.write - data is not an ArrayBuffer! (Got: "+n+")");var c=t&&function(e){var o={bytesWritten:e};t(o)},i=t&&function(){var e={bytesWritten:0};t(e)};r(c,i,"ChromeSocket","write",[e,o])},o.recvFrom=function(e,o,t){"function"==typeof o&&(t=o,o=0),o=o||0;var c;c="android"==n.id?t&&function(){var e,o=0;return function(n){if(0===o)e=n,o++;else{var r={resultCode:e.byteLength||1,data:e,address:n.address,port:n.port};t(r)}}}():t&&function(e,o,n){var r={resultCode:e.byteLength||1,data:e,address:o,port:n};t(r)};var i=t&&function(){var e={resultCode:0};t(e)};r(c,i,"ChromeSocket","recvFrom",[e,o])},o.sendTo=function(e,o,t,n,c){var i=Object.prototype.toString.call(o).slice(8,-1);if("ArrayBuffer"!=i)throw new Error("chrome.socket.write - data is not an ArrayBuffer! (Got: "+i+")");var u=c&&function(e){var o={bytesWritten:e};c(o)},a=c&&function(){var e={bytesWritten:0};c(e)};r(u,a,"ChromeSocket","sendTo",[{socketId:e,address:t,port:n},o])},o.listen=function(e,o,t,n,c){"function"==typeof n&&(c=n,n=0);var i=c&&function(){c(0)},u=c&&function(){c(-1e3)};r(i,u,"ChromeSocket","listen",[e,o,t,n])},o.accept=function(e,o){var t=o&&function(e){var t={resultCode:0,socketId:e};o(t)},n=o&&function(){var e={resultCode:-1e3};o(e)};r(t,n,"ChromeSocket","accept",[e])},o.setKeepAlive=function(){console.warn("chrome.socket.setKeepAlive not implemented yet")},o.setNoDelay=function(){console.warn("chrome.socket.setNoDelay not implemented yet")},o.getInfo=function(e,o){var t=o&&function(e){e.connected=!!e.connected,o(e)};r(t,null,"ChromeSocket","getInfo",[e])},o.getNetworkList=function(e){c.getNetworkInterfaces(e)},o.joinGroup=function(e,o,t){var n=t&&function(){t(0)},c=t&&function(){t(-1e3)};r(n,c,"ChromeSocket","joinGroup",[e,o])},o.leaveGroup=function(e,o,t){var n=t&&function(){t(0)},c=t&&function(){t(-1e3)};r(n,c,"ChromeSocket","leaveGroup",[e,o])},o.setMulticastTimeToLive=function(e,o,t){return"android"!==n.id?void console.warn("chrome.socket.setMulticastTimeToLive not implemented yet"):void r(t,null,"ChromeSocket","setMulticastTimeToLive",[e,o])},o.setMulticastLoopbackMode=function(e,o,t){return"android"!==n.id?void console.warn("chrome.socket.setMulticastLoopbackMode not implemented yet"):void r(t,null,"ChromeSocket","setMulticastLoopbackMode",[e,o])},o.getJoinedGroups=function(e,o){var t=o,n=o&&function(){o(-1e3)};r(t,n,"ChromeSocket","getJoinedGroups",[e])}});