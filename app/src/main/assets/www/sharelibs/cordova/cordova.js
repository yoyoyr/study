!function(){var PLATFORM_VERSION_BUILD_LABEL="4.1.1",require,define;!function(){function e(e){var n=e.factory,o=function(n){var o=n;return"."===n.charAt(0)&&(o=e.id.slice(0,e.id.lastIndexOf(t))+t+n.slice(2)),require(o)};return e.exports={},delete e.factory,n(o,e.exports,e),e.exports}var n={},o=[],r={},t=".";require=function(t){if(!n[t])throw"module "+t+" not found";if(t in r){var i=o.slice(r[t]).join("->")+"->"+t;throw"Cycle in require graph: "+i}if(n[t].factory)try{return r[t]=o.length,o.push(t),e(n[t])}finally{delete r[t],o.pop()}return n[t].exports},define=function(e,o){if(n[e])throw"module "+e+" already defined";n[e]={id:e,factory:o}},define.remove=function(e){delete n[e]},define.moduleMap=n}(),"object"==typeof module&&"function"==typeof require&&(module.exports.require=require,module.exports.define=define),define("cordova",function(e,n,o){function r(e,n){var o=document.createEvent("Events");if(o.initEvent(e,!1,!1),n)for(var r in n)n.hasOwnProperty(r)&&(o[r]=n[r]);return o}if(window.cordova)throw new Error("cordova already defined");var t=e("cordova/channel"),i=e("cordova/platform"),a=document.addEventListener,c=document.removeEventListener,d=window.addEventListener,s=window.removeEventListener,u={},l={};document.addEventListener=function(e,n,o){var r=e.toLowerCase();"undefined"!=typeof u[r]?u[r].subscribe(n):a.call(document,e,n,o)},window.addEventListener=function(e,n,o){var r=e.toLowerCase();"undefined"!=typeof l[r]?l[r].subscribe(n):d.call(window,e,n,o)},document.removeEventListener=function(e,n,o){var r=e.toLowerCase();"undefined"!=typeof u[r]?u[r].unsubscribe(n):c.call(document,e,n,o)},window.removeEventListener=function(e,n,o){var r=e.toLowerCase();"undefined"!=typeof l[r]?l[r].unsubscribe(n):s.call(window,e,n,o)};var f={define:define,require:e,version:PLATFORM_VERSION_BUILD_LABEL,platformVersion:PLATFORM_VERSION_BUILD_LABEL,platformId:i.id,addWindowEventHandler:function(e){return l[e]=t.create(e)},addStickyDocumentEventHandler:function(e){return u[e]=t.createSticky(e)},addDocumentEventHandler:function(e){return u[e]=t.create(e)},removeWindowEventHandler:function(e){delete l[e]},removeDocumentEventHandler:function(e){delete u[e]},getOriginalHandlers:function(){return{document:{addEventListener:a,removeEventListener:c},window:{addEventListener:d,removeEventListener:s}}},fireDocumentEvent:function(e,n,o){var t=r(e,n);"undefined"!=typeof u[e]?o?u[e].fire(t):setTimeout(function(){"deviceready"==e&&document.dispatchEvent(t),u[e].fire(t)},0):document.dispatchEvent(t)},fireWindowEvent:function(e,n){var o=r(e,n);"undefined"!=typeof l[e]?setTimeout(function(){l[e].fire(o)},0):window.dispatchEvent(o)},callbackId:Math.floor(2e9*Math.random()),callbacks:{},callbackStatus:{NO_RESULT:0,OK:1,CLASS_NOT_FOUND_EXCEPTION:2,ILLEGAL_ACCESS_EXCEPTION:3,INSTANTIATION_EXCEPTION:4,MALFORMED_URL_EXCEPTION:5,IO_EXCEPTION:6,INVALID_ACTION:7,JSON_EXCEPTION:8,ERROR:9},callbackSuccess:function(e,n){f.callbackFromNative(e,!0,n.status,[n.message],n.keepCallback)},callbackError:function(e,n){f.callbackFromNative(e,!1,n.status,[n.message],n.keepCallback)},callbackFromNative:function(e,n,o,r,t){try{var i=f.callbacks[e];i&&(n&&o==f.callbackStatus.OK?i.success&&i.success.apply(null,r):n||i.fail&&i.fail.apply(null,r),t||delete f.callbacks[e])}catch(a){var c="Error in "+(n?"Success":"Error")+" callbackId: "+e+" : "+a;throw console&&console.log&&console.log(c),f.fireWindowEvent("cordovacallbackerror",{message:c}),a}},addConstructor:function(e){t.onCordovaReady.subscribe(function(){try{e()}catch(n){console.log("Failed to run constructor: "+n)}})}};o.exports=f}),define("cordova/android/nativeapiprovider",function(e,n,o){var r=this._cordovaNative||e("cordova/android/promptbasednativeapi"),t=r;o.exports={get:function(){return t},setPreferPrompt:function(n){t=n?e("cordova/android/promptbasednativeapi"):r},set:function(e){t=e}}}),define("cordova/android/promptbasednativeapi",function(e,n,o){o.exports={exec:function(e,n,o,r,t){return prompt(t,"gap:"+JSON.stringify([e,n,o,r]))},setNativeToJsBridgeMode:function(e,n){prompt(n,"gap_bridge_mode:"+e)},retrieveJsMessages:function(e,n){return prompt(+n,"gap_poll:"+e)}}}),define("cordova/argscheck",function(e,n,o){function r(e,n){return/.*?\((.*?)\)/.exec(e)[1].split(", ")[n]}function t(e,n,o,t){if(c.enableChecks){for(var i,s=null,u=0;u<e.length;++u){var l=e.charAt(u),f=l.toUpperCase(),v=o[u];if("*"!=l&&(i=a.typeName(v),(null!==v&&void 0!==v||l!=f)&&i!=d[f])){s="Expected "+d[f];break}}if(s)throw s+=", but got "+i+".",s='Wrong type for parameter "'+r(t||o.callee,u)+'" of '+n+": "+s,"undefined"==typeof jasmine&&console.error(s),TypeError(s)}}function i(e,n){return void 0===e?n:e}var a=e("cordova/utils"),c=o.exports,d={A:"Array",D:"Date",N:"Number",S:"String",F:"Function",O:"Object"};c.checkArgs=t,c.getValue=i,c.enableChecks=!0}),define("cordova/base64",function(e,n,o){function r(e){for(var n,o=e.byteLength,r="",t=c(),i=0;i<o-2;i+=3)n=(e[i]<<16)+(e[i+1]<<8)+e[i+2],r+=t[n>>12],r+=t[4095&n];return o-i==2?(n=(e[i]<<16)+(e[i+1]<<8),r+=t[n>>12],r+=a[(4095&n)>>6],r+="="):o-i==1&&(n=e[i]<<16,r+=t[n>>12],r+="=="),r}var t=n;t.fromArrayBuffer=function(e){var n=new Uint8Array(e);return r(n)},t.toArrayBuffer=function(e){for(var n="undefined"!=typeof atob?atob(e):new Buffer(e,"base64").toString("binary"),o=new ArrayBuffer(n.length),r=new Uint8Array(o),t=0,i=n.length;t<i;t++)r[t]=n.charCodeAt(t);return o};var i,a="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/",c=function(){i=[];for(var e=0;e<64;e++)for(var n=0;n<64;n++)i[64*e+n]=a[e]+a[n];return c=function(){return i},i}}),define("cordova/builder",function(e,n,o){function r(e,n,o){for(var r in e)e.hasOwnProperty(r)&&n.apply(o,[e[r],r])}function t(e,o,r){n.replaceHookForTesting(e,o);var t=!1;try{e[o]=r}catch(i){t=!0}(t||e[o]!==r)&&d.defineGetter(e,o,function(){return r})}function i(e,n,o,r){r?d.defineGetter(e,n,function(){return console.log(r),delete e[n],t(e,n,o),o}):t(e,n,o)}function a(n,o,t,s){r(o,function(o,r){try{var u=o.path?e(o.path):{};t?("undefined"==typeof n[r]?i(n,r,u,o.deprecated):"undefined"!=typeof o.path&&(s?c(n[r],u):i(n,r,u,o.deprecated)),u=n[r]):"undefined"==typeof n[r]?i(n,r,u,o.deprecated):u=n[r],o.children&&a(u,o.children,t,s)}catch(l){d.alert("Exception building Cordova JS globals: "+l+' for key "'+r+'"')}})}function c(e,n){for(var o in n)n.hasOwnProperty(o)&&(e.prototype&&e.prototype.constructor===e?t(e.prototype,o,n[o]):"object"==typeof n[o]&&"object"==typeof e[o]?c(e[o],n[o]):t(e,o,n[o]))}var d=e("cordova/utils");n.buildIntoButDoNotClobber=function(e,n){a(n,e,!1,!1)},n.buildIntoAndClobber=function(e,n){a(n,e,!0,!1)},n.buildIntoAndMerge=function(e,n){a(n,e,!0,!0)},n.recursiveMerge=c,n.assignOrWrapInDeprecateGetter=i,n.replaceHookForTesting=function(){}}),define("cordova/channel",function(e,n,o){function r(e){if("function"!=typeof e)throw"Function required as first argument!"}var t=e("cordova/utils"),i=1,a=function(e,n){this.type=e,this.handlers={},this.state=n?1:0,this.fireArgs=null,this.numHandlers=0,this.onHasSubscribersChange=null},c={join:function(e,n){for(var o=n.length,r=o,t=function(){--r||e()},i=0;i<o;i++){if(0===n[i].state)throw Error("Can only use join with sticky channels.");n[i].subscribe(t)}o||e()},create:function(e){return c[e]=new a(e,(!1))},createSticky:function(e){return c[e]=new a(e,(!0))},deviceReadyChannelsArray:[],deviceReadyChannelsMap:{},waitForInitialization:function(e){if(e){var n=c[e]||this.createSticky(e);this.deviceReadyChannelsMap[e]=n,this.deviceReadyChannelsArray.push(n)}},initializationComplete:function(e){var n=this.deviceReadyChannelsMap[e];n&&n.fire()}};a.prototype.subscribe=function(e,n){if(r(e),2==this.state)return void e.apply(n||this,this.fireArgs);var o=e,a=e.observer_guid;"object"==typeof n&&(o=t.close(n,e)),a||(a=""+i++),o.observer_guid=a,e.observer_guid=a,this.handlers[a]||(this.handlers[a]=o,this.numHandlers++,1==this.numHandlers&&this.onHasSubscribersChange&&this.onHasSubscribersChange())},a.prototype.unsubscribe=function(e){r(e);var n=e.observer_guid,o=this.handlers[n];o&&(delete this.handlers[n],this.numHandlers--,0===this.numHandlers&&this.onHasSubscribersChange&&this.onHasSubscribersChange())},a.prototype.fire=function(e){var n=Array.prototype.slice.call(arguments);if(1==this.state&&(this.state=2,this.fireArgs=n),this.numHandlers){var o=[];for(var r in this.handlers)o.push(this.handlers[r]);for(var t=0;t<o.length;++t)o[t].apply(this,n);2==this.state&&this.numHandlers&&(this.numHandlers=0,this.handlers={},this.onHasSubscribersChange&&this.onHasSubscribersChange())}},c.createSticky("onDOMContentLoaded"),c.createSticky("onNativeReady"),c.createSticky("onCordovaReady"),c.createSticky("onPluginsReady"),c.createSticky("onDeviceReady"),c.create("onResume"),c.create("onPause"),c.waitForInitialization("onCordovaReady"),c.waitForInitialization("onDOMContentLoaded"),o.exports=c}),define("cordova/exec",function(require,exports,module){function androidExec(e,n,o,r,t){if(bridgeSecret<0)throw new Error("exec() called without bridgeSecret");void 0===jsToNativeBridgeMode&&androidExec.setJsToNativeBridgeMode(jsToNativeModes.JS_OBJECT);for(var i=0;i<t.length;i++)"ArrayBuffer"==utils.typeName(t[i])&&(t[i]=base64.fromArrayBuffer(t[i]));var a=o+cordova.callbackId++,c=JSON.stringify(t);(e||n)&&(cordova.callbacks[a]={success:e,fail:n});var d=nativeApiProvider.get().exec(bridgeSecret,o,r,a,c);jsToNativeBridgeMode==jsToNativeModes.JS_OBJECT&&"@Null arguments."===d?(androidExec.setJsToNativeBridgeMode(jsToNativeModes.PROMPT),androidExec(e,n,o,r,t),androidExec.setJsToNativeBridgeMode(jsToNativeModes.JS_OBJECT)):d&&(messagesFromNative.push(d),nextTick(processMessages))}function pollOnceFromOnlineEvent(){pollOnce(!0)}function pollOnce(e){if(!(bridgeSecret<0)){var n=nativeApiProvider.get().retrieveJsMessages(bridgeSecret,!!e);n&&(messagesFromNative.push(n),processMessages())}}function pollingTimerFunc(){pollEnabled&&(pollOnce(),setTimeout(pollingTimerFunc,50))}function hookOnlineApis(){function e(e){cordova.fireWindowEvent(e.type)}window.addEventListener("online",pollOnceFromOnlineEvent,!1),window.addEventListener("offline",pollOnceFromOnlineEvent,!1),cordova.addWindowEventHandler("online"),cordova.addWindowEventHandler("offline"),document.addEventListener("online",e,!1),document.addEventListener("offline",e,!1)}function buildPayload(e,n){var o=n.charAt(0);if("s"==o)e.push(n.slice(1));else if("t"==o)e.push(!0);else if("f"==o)e.push(!1);else if("N"==o)e.push(null);else if("n"==o)e.push(+n.slice(1));else if("A"==o){var r=n.slice(1);e.push(base64.toArrayBuffer(r))}else if("S"==o)e.push(window.atob(n.slice(1)));else if("M"==o)for(var t=n.slice(1);""!==t;){var i=t.indexOf(" "),a=+t.slice(0,i),c=t.substr(i+1,a);t=t.slice(i+a+1),buildPayload(e,c)}else e.push(JSON.parse(n))}function processMessage(message){var firstChar=message.charAt(0);if("J"==firstChar)eval(message.slice(1));else if("S"==firstChar||"F"==firstChar){var success="S"==firstChar,keepCallback="1"==message.charAt(1),spaceIdx=message.indexOf(" ",2),status=+message.slice(2,spaceIdx),nextSpaceIdx=message.indexOf(" ",spaceIdx+1),callbackId=message.slice(spaceIdx+1,nextSpaceIdx),payloadMessage=message.slice(nextSpaceIdx+1),payload=[];buildPayload(payload,payloadMessage),cordova.callbackFromNative(callbackId,success,status,payload,keepCallback)}else console.log("processMessage failed: invalid message: "+JSON.stringify(message))}function processMessages(){if(!isProcessing&&0!==messagesFromNative.length){isProcessing=!0;try{var e=popMessageFromQueue();if("*"==e&&0===messagesFromNative.length)return void nextTick(pollOnce);processMessage(e)}finally{isProcessing=!1,messagesFromNative.length>0&&nextTick(processMessages)}}}function popMessageFromQueue(){var e=messagesFromNative.shift();if("*"==e)return"*";var n=e.indexOf(" "),o=+e.slice(0,n),r=e.substr(n+1,o);return e=e.slice(n+o+1),e&&messagesFromNative.unshift(e),r}var cordova=require("cordova"),nativeApiProvider=require("cordova/android/nativeapiprovider"),utils=require("cordova/utils"),base64=require("cordova/base64"),channel=require("cordova/channel"),jsToNativeModes={PROMPT:0,JS_OBJECT:1},nativeToJsModes={POLLING:0,LOAD_URL:1,ONLINE_EVENT:2},jsToNativeBridgeMode,nativeToJsBridgeMode=nativeToJsModes.ONLINE_EVENT,pollEnabled=!1,bridgeSecret=-1,messagesFromNative=[],isProcessing=!1,resolvedPromise="undefined"==typeof Promise?null:Promise.resolve(),nextTick=resolvedPromise?function(e){resolvedPromise.then(e)}:function(e){setTimeout(e)};androidExec.init=function(){bridgeSecret=+prompt("","gap_init:"+nativeToJsBridgeMode),channel.onNativeReady.fire()},hookOnlineApis(),androidExec.jsToNativeModes=jsToNativeModes,androidExec.nativeToJsModes=nativeToJsModes,androidExec.setJsToNativeBridgeMode=function(e){e!=jsToNativeModes.JS_OBJECT||window._cordovaNative||(e=jsToNativeModes.PROMPT),nativeApiProvider.setPreferPrompt(e==jsToNativeModes.PROMPT),jsToNativeBridgeMode=e},androidExec.setNativeToJsBridgeMode=function(e){e!=nativeToJsBridgeMode&&(nativeToJsBridgeMode==nativeToJsModes.POLLING&&(pollEnabled=!1),nativeToJsBridgeMode=e,bridgeSecret>=0&&nativeApiProvider.get().setNativeToJsBridgeMode(bridgeSecret,e),e==nativeToJsModes.POLLING&&(pollEnabled=!0,setTimeout(pollingTimerFunc,1)))},module.exports=androidExec}),define("cordova/exec/proxy",function(e,n,o){var r={};o.exports={add:function(e,n){return console.log("adding proxy for "+e),r[e]=n,n},remove:function(e){var n=r[e];return delete r[e],r[e]=null,n},get:function(e,n){return r[e]?r[e][n]:null}}}),define("cordova/init",function(e,n,o){function r(e){for(var n=0;n<e.length;++n)2!=e[n].state&&console.log("Channel not fired: "+e[n].type)}function t(e){var n=function(){};n.prototype=e;var o=new n;if(n.bind)for(var r in e)"function"==typeof e[r]?o[r]=e[r].bind(e):!function(n){u.defineGetterSetter(o,r,function(){return e[n]})}(r);return o}var i=e("cordova/channel"),a=e("cordova"),c=e("cordova/modulemapper"),d=e("cordova/platform"),s=e("cordova/pluginloader"),u=e("cordova/utils"),l=[i.onNativeReady,i.onPluginsReady];window.setTimeout(function(){2!=i.onDeviceReady.state&&(console.log("deviceready has not fired after 5 seconds."),r(l),r(i.deviceReadyChannelsArray))},5e3),window.navigator&&(window.navigator=t(window.navigator)),window.console||(window.console={log:function(){}}),window.console.warn||(window.console.warn=function(e){this.log("warn: "+e)}),i.onPause=a.addDocumentEventHandler("pause"),i.onResume=a.addDocumentEventHandler("resume"),i.onActivated=a.addDocumentEventHandler("activated"),i.onDeviceReady=a.addStickyDocumentEventHandler("deviceready"),"complete"==document.readyState||"interactive"==document.readyState?i.onDOMContentLoaded.fire():document.addEventListener("DOMContentLoaded",function(){i.onDOMContentLoaded.fire()},!1),window._nativeReady&&i.onNativeReady.fire(),c.clobbers("cordova","cordova"),c.clobbers("cordova/exec","cordova.exec"),c.clobbers("cordova/exec","Cordova.exec"),d.bootstrap&&d.bootstrap(),setTimeout(function(){s.load(function(){i.onPluginsReady.fire()})},0),i.join(function(){c.mapModules(window),d.initialize&&d.initialize(),i.onCordovaReady.fire(),i.join(function(){e("cordova").fireDocumentEvent("deviceready")},i.deviceReadyChannelsArray)},l)}),define("cordova/init_b",function(e,n,o){function r(e){for(var n=0;n<e.length;++n)2!=e[n].state&&console.log("Channel not fired: "+e[n].type)}function t(e){var n=function(){};n.prototype=e;var o=new n;if(n.bind)for(var r in e)"function"==typeof e[r]?o[r]=e[r].bind(e):!function(n){d.defineGetterSetter(o,r,function(){return e[n]})}(r);return o}var i=e("cordova/channel"),a=e("cordova"),c=e("cordova/platform"),d=e("cordova/utils"),s=[i.onDOMContentLoaded,i.onNativeReady];a.exec=e("cordova/exec"),window.setTimeout(function(){2!=i.onDeviceReady.state&&(console.log("deviceready has not fired after 5 seconds."),r(s),r(i.deviceReadyChannelsArray))},5e3),window.navigator&&(window.navigator=t(window.navigator)),window.console||(window.console={log:function(){}}),window.console.warn||(window.console.warn=function(e){this.log("warn: "+e)}),i.onPause=a.addDocumentEventHandler("pause"),i.onResume=a.addDocumentEventHandler("resume"),i.onActivated=a.addDocumentEventHandler("activated"),i.onDeviceReady=a.addStickyDocumentEventHandler("deviceready"),"complete"==document.readyState||"interactive"==document.readyState?i.onDOMContentLoaded.fire():document.addEventListener("DOMContentLoaded",function(){i.onDOMContentLoaded.fire()},!1),window._nativeReady&&i.onNativeReady.fire(),c.bootstrap&&c.bootstrap(),i.join(function(){c.initialize&&c.initialize(),i.onCordovaReady.fire(),i.join(function(){e("cordova").fireDocumentEvent("deviceready")},i.deviceReadyChannelsArray)},s)}),define("cordova/modulemapper",function(e,n,o){function r(e,n,o,r){if(!(n in d))throw new Error("Module "+n+" does not exist.");i.push(e,n,o),r&&(a[o]=r)}function t(e,n){if(!e)return n;for(var o,r=e.split("."),t=n,i=0;o=r[i];++i)t=t[o]=t[o]||{};return t}var i,a,c=e("cordova/builder"),d=define.moduleMap;n.reset=function(){i=[],a={}},n.clobbers=function(e,n,o){r("c",e,n,o)},n.merges=function(e,n,o){r("m",e,n,o)},n.defaults=function(e,n,o){r("d",e,n,o)},n.runs=function(e){r("r",e,null)},n.mapModules=function(n){var o={};n.CDV_origSymbols=o;for(var r=0,d=i.length;r<d;r+=3){var s=i[r],u=i[r+1],l=e(u);if("r"!=s){var f=i[r+2],v=f.lastIndexOf("."),p=f.substr(0,v),m=f.substr(v+1),g=f in a?"Access made to deprecated symbol: "+f+". "+g:null,h=t(p,n),b=h[m];"m"==s&&b?c.recursiveMerge(b,l):("d"==s&&!b||"d"!=s)&&(f in o||(o[f]=b),c.assignOrWrapInDeprecateGetter(h,m,l,g))}}},n.getOriginalSymbol=function(e,n){var o=e.CDV_origSymbols;if(o&&n in o)return o[n];for(var r=n.split("."),t=e,i=0;i<r.length;++i)t=t&&t[r[i]];return t},n.reset()}),define("cordova/platform",function(e,n,o){function r(n){var o=e("cordova"),r=n.action;switch(r){case"backbutton":case"menubutton":case"searchbutton":case"pause":case"resume":case"volumedownbutton":case"volumeupbutton":o.fireDocumentEvent(r);break;default:throw new Error("Unknown event action "+r)}}o.exports={id:"android",bootstrap:function(){function n(e){var n=t.addDocumentEventHandler(e+"button");n.onHasSubscribersChange=function(){i(null,null,c,"overrideButton",[e,1==this.numHandlers])}}var o=e("cordova/channel"),t=e("cordova"),i=e("cordova/exec"),a=e("cordova/modulemapper");i.init(),a.clobbers("cordova/plugin/android/app","navigator.app");var c=Number(t.platformVersion.split(".")[0])>=4?"CoreAndroid":"App",d=t.addDocumentEventHandler("backbutton");d.onHasSubscribersChange=function(){i(null,null,c,"overrideBackbutton",[1==this.numHandlers])},t.addDocumentEventHandler("menubutton"),t.addDocumentEventHandler("searchbutton"),n("volumeup"),n("volumedown"),o.onCordovaReady.subscribe(function(){i(r,null,c,"messageChannel",[]),i(null,null,c,"show",[])})}}}),define("cordova/plugin/android/app",function(e,n,o){var r=e("cordova/exec"),t=Number(e("cordova").platformVersion.split(".")[0])>=4?"CoreAndroid":"App";o.exports={clearCache:function(){r(null,null,t,"clearCache",[])},loadUrl:function(e,n){r(null,null,t,"loadUrl",[e,n])},cancelLoadUrl:function(){r(null,null,t,"cancelLoadUrl",[])},clearHistory:function(){r(null,null,t,"clearHistory",[])},backHistory:function(){r(null,null,t,"backHistory",[])},overrideBackbutton:function(e){r(null,null,t,"overrideBackbutton",[e])},overrideButton:function(e,n){r(null,null,t,"overrideButton",[e,n])},exitApp:function(){return r(null,null,t,"exitApp",[])}}}),define("cordova/pluginloader",function(e,n,o){function r(e,o,r,t){t=t||r,e in define.moduleMap?r():n.injectScript(o,function(){e in define.moduleMap?r():t()},t)}function t(e,n){for(var o,r=0;o=e[r];r++){if(o.clobbers&&o.clobbers.length)for(var t=0;t<o.clobbers.length;t++)c.clobbers(o.id,o.clobbers[t]);if(o.merges&&o.merges.length)for(var i=0;i<o.merges.length;i++)c.merges(o.id,o.merges[i]);o.runs&&c.runs(o.id)}n()}function i(e,n,o){function i(){--a||t(n,o)}var a=n.length;if(!a)return void o();for(var c=0;c<n.length;c++)r(n[c].id,e+n[c].file,i)}function a(){for(var e=null,n=document.getElementsByTagName("script"),o="/cordova.js",r=n.length-1;r>-1;r--){var t=n[r].src.replace(/\?.*$/,"");if(t.indexOf(o)==t.length-o.length){e=t.substring(0,t.length-o.length)+"/";break}}return e}var c=e("cordova/modulemapper");e("cordova/urlutil");n.injectScript=function(e,n,o){var r=document.createElement("script");r.onload=n,r.onerror=o,r.src=e,document.head.appendChild(r)},n.load=function(n){var o=a();null===o&&(console.log("Could not find cordova.js script tag. Plugin loading may fail."),o=""),r("cordova/plugin_list",o+"cordova_plugins.js",function(){var r=e("cordova/plugin_list");i(o,r,n)},n)}}),define("cordova/urlutil",function(e,n,o){n.makeAbsolute=function(e){var n=document.createElement("a");return n.href=e,n.href}}),define("cordova/utils",function(e,n,o){function r(e){for(var n="",o=0;o<e;o++){var r=parseInt(256*Math.random(),10).toString(16);1==r.length&&(r="0"+r),n+=r}return n}var t=n;t.defineGetterSetter=function(e,n,o,r){if(Object.defineProperty){var t={get:o,configurable:!0};r&&(t.set=r),Object.defineProperty(e,n,t)}else e.__defineGetter__(n,o),r&&e.__defineSetter__(n,r)},t.defineGetter=t.defineGetterSetter,t.arrayIndexOf=function(e,n){if(e.indexOf)return e.indexOf(n);for(var o=e.length,r=0;r<o;++r)if(e[r]==n)return r;return-1},t.arrayRemove=function(e,n){var o=t.arrayIndexOf(e,n);return o!=-1&&e.splice(o,1),o!=-1},t.typeName=function(e){return Object.prototype.toString.call(e).slice(8,-1)},t.isArray=Array.isArray||function(e){return"Array"==t.typeName(e)},t.isDate=function(e){return e instanceof Date},t.clone=function(e){if(!e||"function"==typeof e||t.isDate(e)||"object"!=typeof e)return e;var n,o;if(t.isArray(e)){for(n=[],o=0;o<e.length;++o)n.push(t.clone(e[o]));return n}n={};for(o in e)o in n&&n[o]==e[o]||(n[o]=t.clone(e[o]));return n},t.close=function(e,n,o){return function(){var r=o||arguments;return n.apply(e,r)}},t.createUUID=function(){return r(4)+"-"+r(2)+"-"+r(2)+"-"+r(2)+"-"+r(6)},t.extend=function(){var e=function(){};return function(n,o){e.prototype=o.prototype,n.prototype=new e,n.__super__=o.prototype,n.prototype.constructor=n}}(),t.alert=function(e){window.alert?window.alert(e):console&&console.log&&console.log(e)}}),navigator.platform.toLowerCase().match("win")||(window.cordova=require("cordova"),require("cordova/init"))}();