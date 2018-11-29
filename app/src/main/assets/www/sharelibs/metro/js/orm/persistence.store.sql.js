function config(e,t){function n(t,n,i,a){if(a=a||"",t.trackedObjects[i[a+"id"]])return t.trackedObjects[i[a+"id"]];var r=e.typeMapper,o=e.getMeta(n),s=e.define(n);if(!i[a+"id"])return null;var l=new s(t,(void 0),(!0));l.id=r.dbValToEntityVal(i[a+"id"],r.idType),l._new=!1;for(var c in i)if(i.hasOwnProperty(c)&&c.substring(0,a.length)===a){var u=c.substring(a.length);"id"!=u&&(l._data[u]=r.dbValToEntityVal(i[c],o.fields[u]||r.idType))}return l}function i(t,n,i){var a=e.getMeta(t._type),o=e.typeMapper,s=[],l=[],c=[],u=[];if(t._new)for(var h in a.fields)a.fields.hasOwnProperty(h)&&(t._dirtyProperties[h]=!0);for(var h in t._dirtyProperties)if(t._dirtyProperties.hasOwnProperty(h)){s.push("`"+h+"`");var p=a.fields[h]||o.idType;l.push(o.entityValToDbVal(t._data[h],p)),c.push(o.outVar("?",p)),u.push("`"+h+"` = "+o.outVar("?",p))}var d=[];if(a&&a.hasMany)for(var h in a.hasMany)a.hasMany.hasOwnProperty(h)&&(d=d.concat(e.get(t,h).persistQueries()));r(n,d,function(){if(!t._new&&0===s.length)return void(i&&i());if(t._dirtyProperties={},t._new){s.push("id"),l.push(o.entityIdToDbId(t.id)),c.push(o.outIdVar("?"));var e="INSERT INTO `"+t._type+"` ("+s.join(", ")+") VALUES ("+c.join(", ")+")";t._new=!1,n.executeSql(e,l,i,i)}else{var e="UPDATE `"+t._type+"` SET "+u.join(",")+" WHERE id = "+o.outId(t.id);n.executeSql(e,l,i,i)}})}function a(t,n,i){var a=e.getMeta(t._type),o=e.typeMapper,s=[["DELETE FROM `"+t._type+"` WHERE id = "+o.outId(t.id),null]];for(var l in a.hasMany)if(a.hasMany.hasOwnProperty(l)&&a.hasMany[l].manyToMany){var c=a.hasMany[l].tableName;s.push(["DELETE FROM `"+c+"` WHERE `"+a.name+"_"+l+"` = "+o.outId(t.id),null])}r(n,s,i)}function r(t,n,i){for(var a=[],r=3;r<arguments.length;r++)a.push(arguments[r]);e.asyncForEach(n,function(e,n){t.executeSql(e[0],e[1],n,function(e,t){console.log(t.message),n(e,t)})},function(e,t){return t&&i?void i(e,t):void(i&&i.apply(null,a))})}var o=e.argspec;e.typeMapper=t.typeMapper||defaultTypeMapper,e.generatedTables={},e.schemaSync=function(n,i,a){var s=o.getArgs(arguments,[{name:"tx",optional:!0,check:e.isTransaction,defaultValue:null},{name:"callback",optional:!0,check:o.isCallback(),defaultValue:function(){}},{name:"emulate",optional:!0,check:o.hasType("boolean")}]);if(n=s.tx,i=s.callback,a=s.emulate,!n){var l=this;return void this.transaction(function(e){l.schemaSync(e,i,a)})}var c,u,h,p,d=[],y=e.typeMapper,f=e.getEntityMeta();for(var v in f)if(f.hasOwnProperty(v)){if(c=f[v],!c.isMixin){u=[];for(var m in c.fields)c.fields.hasOwnProperty(m)&&u.push([m,c.fields[m]]);for(var _ in c.hasOne)c.hasOne.hasOwnProperty(_)&&(h=c.hasOne[_].type.meta,u.push([_,y.idType]),d.push([t.createIndex(c.name,[_]),null]));for(var T=0;T<c.indexes.length;T++)d.push([t.createIndex(c.name,c.indexes[T].columns,c.indexes[T]),null])}for(var _ in c.hasMany)if(c.hasMany.hasOwnProperty(_)&&c.hasMany[_].manyToMany&&(p=c.hasMany[_].tableName,!e.generatedTables[p])){var h=c.hasMany[_].type.meta,g=c.hasMany[_].inverseProperty;if(h.hasMany[g].type.meta!=c)continue;var M=c.name+"_"+_,b=h.name+"_"+g;d.push([t.createIndex(p,[M]),null]),d.push([t.createIndex(p,[b]),null]);var S=[[M,y.idType],[b,y.idType]];c.isMixin&&S.push([M+"_class",y.classNameType]),h.isMixin&&S.push([b+"_class",y.classNameType]),d.push([t.createTable(p,S),null]),e.generatedTables[p]=!0}c.isMixin||(u.push(["id",y.idType,"PRIMARY KEY"]),e.generatedTables[c.name]=!0,d.push([t.createTable(c.name,u),null]))}for(var E=e.schemaSyncHooks,T=0;T<E.length;T++)E[T](n);a?i(n):r(n,d,function(e,t){i(n,t)})},e.flush=function(t,n){var r=o.getArgs(arguments,[{name:"tx",optional:!0,check:e.isTransaction},{name:"callback",optional:!0,check:o.isCallback(),defaultValue:null}]);t=r.tx,n=r.callback;var s=this;if(!t)return void this.transaction(function(e){s.flush(e,n)});var l=e.flushHooks;e.asyncForEach(l,function(e,n){e(s,t,n)},function(){var r=[];for(var o in s.trackedObjects)s.trackedObjects.hasOwnProperty(o)&&r.push(s.trackedObjects[o]);var l=[];for(var o in s.objectsToRemove)s.objectsToRemove.hasOwnProperty(o)&&(l.push(s.objectsToRemove[o]),delete s.trackedObjects[o]);if(s.objectsToRemove={},n)e.asyncParForEach(l,function(e,n){a(e,t,n)},function(a,o){return o?n(a,o):void e.asyncParForEach(r,function(e,n){i(e,t,n)},n)});else{for(var c=0;c<r.length;c++)i(r[c],t);for(var c=0;c<l.length;c++)a(l[c],t)}})},e.reset=function(t,n){var i=o.getArgs(arguments,[{name:"tx",optional:!0,check:e.isTransaction,defaultValue:null},{name:"callback",optional:!0,check:o.isCallback(),defaultValue:function(){}}]);t=i.tx,n=i.callback;var a=this;return t?void this.schemaSync(t,function(){function i(){var e=o.pop();t.executeSql("DROP TABLE IF EXISTS `"+e+"`",null,function(){o.length>0?i():r()},r)}function r(t,i){a.clean(),e.generatedTables={},n&&n(t,i)}var o=[];for(var s in e.generatedTables)e.generatedTables.hasOwnProperty(s)&&o.push(s);o.length>0?i():r()},!0):void a.transaction(function(e){a.reset(e,n)})},e.save=i,e.executeQueriesSeq=r,e.QueryCollection.prototype.persistQueries=function(){return[]};var s=e.QueryCollection.prototype.clone;e.QueryCollection.prototype.clone=function(e){var t=s.call(this,e);return t._additionalJoinSqls=this._additionalJoinSqls.slice(0),t._additionalWhereSqls=this._additionalWhereSqls.slice(0),t._additionalGroupSqls=this._additionalGroupSqls.slice(0),t._manyToManyFetch=this._manyToManyFetch,t};var l=e.QueryCollection.prototype.init;e.QueryCollection.prototype.init=function(e,t,n){l.call(this,e,t,n),this._manyToManyFetch=null,this._additionalJoinSqls=[],this._additionalWhereSqls=[],this._additionalGroupSqls=[]};var c=e.QueryCollection.prototype.toUniqueString;e.QueryCollection.prototype.toUniqueString=function(){var e=c.call(this);e+="|JoinSQLs:";for(var t=0;t<this._additionalJoinSqls.length;t++)e+=this._additionalJoinSqls[t];e+="|WhereSQLs:";for(var t=0;t<this._additionalWhereSqls.length;t++)e+=this._additionalWhereSqls[t];e+="|GroupSQLs:";for(var t=0;t<this._additionalGroupSqls.length;t++)e+=this._additionalGroupSqls[t];return this._manyToManyFetch&&(e+="|ManyToManyFetch:",e+=JSON.stringify(this._manyToManyFetch)),e},e.NullFilter.prototype.sql=function(e,t,n){return"1=1"},e.AndFilter.prototype.sql=function(e,t,n){return"("+this.left.sql(e,t,n)+" AND "+this.right.sql(e,t,n)+")"},e.OrFilter.prototype.sql=function(e,t,n){return"("+this.left.sql(e,t,n)+" OR "+this.right.sql(e,t,n)+")"},e.PropertyFilter.prototype.sql=function(t,n,i){var a=e.typeMapper,r=n?"`"+n+"`.":"",o=t.fields[this.property]||a.idType;if("="===this.operator&&null===this.value)return r+"`"+this.property+"` IS NULL";if("!="===this.operator&&null===this.value)return r+"`"+this.property+"` IS NOT NULL";if("in"===this.operator){for(var s=this.value,l=[],c=0;c<s.length;c++)l.push("?"),i.push(a.entityValToDbVal(s[c],o));return 0===s.length?"1 = 0":r+"`"+this.property+"` IN ("+l.join(", ")+")"}if("not in"===this.operator){for(var s=this.value,l=[],c=0;c<s.length;c++)l.push("?"),i.push(a.entityValToDbVal(s[c],o));return 0===s.length?"1 = 1":r+"`"+this.property+"` NOT IN ("+l.join(", ")+")"}var u=this.value;return u!==!0&&u!==!1||(u=u?1:0),i.push(a.entityValToDbVal(u,o)),r+"`"+this.property+"` "+this.operator+" "+a.outVar("?",o)},e.DbQueryCollection.prototype.list=function(t,i){function a(e,t,n){var i=[h.inIdVar("`"+t+"`.id")+" AS "+n+"id"];for(var a in e.fields)e.fields.hasOwnProperty(a)&&i.push(h.inVar("`"+t+"`.`"+a+"`",e.fields[a])+" AS `"+n+a+"`");for(var a in e.hasOne)e.hasOne.hasOwnProperty(a)&&i.push(h.inIdVar("`"+t+"`.`"+a+"`")+" AS `"+n+a+"`");return i}var r=o.getArgs(arguments,[{name:"tx",optional:!0,check:e.isTransaction,defaultValue:null},{name:"callback",optional:!1,check:o.isCallback()}]);t=r.tx,i=r.callback;var s=this,l=this._session;if(!t)return void l.transaction(function(e){s.list(e,i)});var c=this._entityName,u=e.getMeta(c),h=e.typeMapper;if(u.isMixin){var p=[];return void e.asyncForEach(u.mixedIns,function(e,n){var i=s.clone();i._entityName=e.name,i.list(t,function(e){p=p.concat(e),n()})},function(){var t=new e.LocalQueryCollection(p);t._orderColumns=s._orderColumns,t._reverse=s._reverse,t.list(null,i)})}var r=[],d=c+"_",y="root",f=a(u,y,d),v="",m=this._additionalWhereSqls.slice(0),_=this._manyToManyFetch;_&&(v+="LEFT JOIN `"+_.table+"` AS mtm ON mtm.`"+_.inverseProp+"` = `root`.`id` ",m.push("mtm.`"+_.prop+"` = "+h.outId(_.id))),v+=this._additionalJoinSqls.join(" ");for(var T=0;T<this._prefetchFields.length;T++){var g=this._prefetchFields[T].split("."),M=g[0],b=c;g.length>1&&(M=g[1],b=g[0]);var S=e.getMeta(b),E=S.hasOne[M].type.meta;if(E.isMixin)throw new Error("cannot prefetch a mixin");var O=E.name+"_"+M+"_tbl",I=y;g.length>1&&(I=b+"_"+b+"_tbl"),f=f.concat(a(E,O,M+"_")),v+="LEFT JOIN `"+E.name+"` AS `"+O+"` ON `"+O+"`.`id` = `"+I+"`.`"+M+"` "}var x="WHERE "+[this._filter.sql(u,y,r)].concat(m).join(" AND "),N="SELECT "+f.join(", ")+" FROM `"+c+"` AS `"+y+"` "+v+" "+x;this._additionalGroupSqls.length>0&&(N+=this._additionalGroupSqls.join(" ")),this._orderColumns.length>0&&(N+=" ORDER BY "+this._orderColumns.map(function(e){return(e[2]?"`":"LOWER(`")+d+e[0]+(e[2]?"` ":"`) ")+(e[1]?"ASC":"DESC")}).join(", ")),this._limit>=0&&(N+=" LIMIT "+this._limit),this._skip>0&&(N+=" OFFSET "+this._skip),l.flush(t,function(){t.executeSql(N,r,function(t){var a=[];s._reverse&&t.reverse();for(var r=0;r<t.length;r++){for(var o=t[r],u=n(l,c,o,d),h=0;h<s._prefetchFields.length;h++){var p=s._prefetchFields[h].split("."),y=p[0],f=c;p.length>1&&(y=p[1],f=p[0]);var v=e.getMeta(f),m=v.hasOne[y].type.meta;u._data_obj[y]=n(l,m.name,o,y+"_"),l.add(u._data_obj[y])}a.push(u),l.add(u)}i(a),s.triggerEvent("list",s,a)})})},e.DbQueryCollection.prototype.destroyAll=function(t,n){var i=o.getArgs(arguments,[{name:"tx",optional:!0,check:e.isTransaction,defaultValue:null},{name:"callback",optional:!0,check:o.isCallback(),defaultValue:function(){}}]);t=i.tx,n=i.callback;var a=this,r=this._session;if(!t)return void r.transaction(function(e){a.destroyAll(e,n)});var s=this._entityName,l=e.getMeta(s),c=e.typeMapper;if(l.isMixin)return void e.asyncForEach(l.mixedIns,function(e,i){var r=a.clone();r._entityName=e.name,r.destroyAll(t,n)},n);var u="",h=this._additionalWhereSqls.slice(0),p=this._manyToManyFetch;p&&(u+="LEFT JOIN `"+p.table+"` AS mtm ON mtm.`"+p.inverseProp+"` = `root`.`id` ",h.push("mtm.`"+p.prop+"` = "+c.outId(p.id))),u+=this._additionalJoinSqls.join(" ");var i=[],d="WHERE "+[this._filter.sql(l,null,i)].concat(h).join(" AND "),y="SELECT id FROM `"+s+"` "+u+" "+d,f="DELETE FROM `"+s+"` "+u+" "+d,v=i.slice(0);r.flush(t,function(){t.executeSql(y,i,function(e){for(var i=0;i<e.length;i++)delete r.trackedObjects[e[i].id],r.objectsRemoved.push({id:e[i].id,entity:s});a.triggerEvent("change",a),t.executeSql(f,v,n,n)},n)})},e.DbQueryCollection.prototype.count=function(t,n){var i=o.getArgs(arguments,[{name:"tx",optional:!0,check:e.isTransaction,defaultValue:null},{name:"callback",optional:!1,check:o.isCallback()}]);t=i.tx,n=i.callback;var a=this,r=this._session;if(t&&!t.executeSql&&(n=t,t=null),!t)return void r.transaction(function(e){a.count(e,n)});var s=this._entityName,l=e.getMeta(s),c=e.typeMapper;if(l.isMixin){var u=0;return void e.asyncForEach(l.mixedIns,function(e,n){var i=a.clone();i._entityName=e.name,i.count(t,function(e){u+=e,n()})},function(){n(u)})}var h="",p=this._additionalWhereSqls.slice(0),d=this._manyToManyFetch;d&&(h+="LEFT JOIN `"+d.table+"` AS mtm ON mtm.`"+d.inverseProp+"` = `root`.`id` ",p.push("mtm.`"+d.prop+"` = "+c.outId(d.id))),h+=this._additionalJoinSqls.join(" ");var i=[],y="WHERE "+[this._filter.sql(l,"root",i)].concat(p).join(" AND "),f="SELECT COUNT(*) AS cnt FROM `"+s+"` AS `root` "+h+" "+y;r.flush(t,function(){t.executeSql(f,i,function(e){n(parseInt(e[0].cnt,10))})})},e.ManyToManyDbQueryCollection.prototype.persistQueries=function(){for(var t=[],n=e.getMeta(this._obj._type),i=n.hasMany[this._coll].type.meta,a=e.typeMapper,r=n.hasMany[this._coll],o=i.hasMany[r.inverseProperty],s=r.mixin?r.mixin.meta.name:n.name,l=o.mixin?o.mixin.meta.name:i.name,c=0;c<this._localAdded.length;c++){var u=[s+"_"+this._coll,l+"_"+r.inverseProperty],h=[a.outIdVar("?"),a.outIdVar("?")],p=[a.entityIdToDbId(this._obj.id),a.entityIdToDbId(this._localAdded[c].id)];r.mixin&&(u.push(s+"_"+this._coll+"_class"),h.push("?"),p.push(n.name)),o.mixin&&(u.push(l+"_"+r.inverseProperty+"_class"),h.push("?"),p.push(i.name)),t.push(["INSERT INTO "+r.tableName+" (`"+u.join("`, `")+"`) VALUES ("+h.join(",")+")",p])}this._localAdded=[];for(var c=0;c<this._localRemoved.length;c++)t.push(["DELETE FROM  "+r.tableName+" WHERE `"+s+"_"+this._coll+"` = "+a.outIdVar("?")+" AND `"+l+"_"+r.inverseProperty+"` = "+a.outIdVar("?"),[a.entityIdToDbId(this._obj.id),a.entityIdToDbId(this._localRemoved[c].id)]]);return this._localRemoved=[],t}}var defaultTypeMapper={idType:"VARCHAR(32)",classNameType:"TEXT",columnType:function(e){switch(e){case"JSON":return"TEXT";case"BOOL":return"INT";case"DATE":return"INT";default:return e}},inVar:function(e,t){return e},outVar:function(e,t){return e},outId:function(e){return"'"+e+"'"},dbValToEntityVal:function(e,t){if(null===e||void 0===e)return e;switch(t){case"DATE":return e>1e12?new Date(parseInt(e,10)):new Date(1e3*parseInt(e,10));case"BOOL":return 1===e||"1"===e;case"INT":return+e;case"BIGINT":return+e;case"JSON":return e?JSON.parse(e):e;default:return e}},entityValToDbVal:function(e,t){return void 0===e||null===e?null:"JSON"===t&&e?JSON.stringify(e):e.id?e.id:"BOOL"===t?"false"===e?0:e?1:0:"DATE"===t||e.getTime?(e=new Date(e),Math.round(e.getTime()/1e3)):"VARCHAR(32)"===t?e.toString():e},inIdVar:function(e){return this.inVar(e,this.idType)},outIdVar:function(e){return this.outVar(e,this.idType)},entityIdToDbId:function(e){return this.entityValToDbVal(e,this.idType)}};"undefined"!=typeof exports?(exports.defaultTypeMapper=defaultTypeMapper,exports.config=config):(window=window||{},window.persistence=window.persistence||persistence||{},window.persistence.store=window.persistence.store||{},window.persistence.store.sql={defaultTypeMapper:defaultTypeMapper,config:config});