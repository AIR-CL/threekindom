(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-d441e11c"],{"1a51":function(e,t,c){},"2c12":function(e,t,c){"use strict";c("1a51")},"48b8":function(e,t,c){"use strict";c.d(t,"a",(function(){return n})),c.d(t,"b",(function(){return a})),c.d(t,"c",(function(){return u}));c("b64b"),c("4d63"),c("ac1f"),c("25f0"),c("466d"),c("1276");function n(e,t){if(e===t)return t;for(var c=Object.keys(t),n=0,a=c;n<a.length;n++){var u=a[n];void 0!==e[u]&&(t[u]=e[u])}return t}function a(e){return"undefined"===typeof e||null==e||""==e}function u(e,t){var c=arguments.length>2&&void 0!==arguments[2]&&arguments[2];e=a(e)?"/":e.split("?")[0],"/"!==e&&t&&"{}"!=JSON.stringify(t)||(c?window.open(e):window.location.href=e);var n=e.charAt(e.length-1);"/"==n&&(e=e.substring(0,e.length-1)),e+="?";for(var u=Object.keys(t),r=0,i=u;r<i.length;r++){var o=i[r];e+=o+"="+t[o]+"&"}e=e.substring(0,e.length-1),c?window.open(e):window.location.href=e}},"63db":function(e,t,c){},"646c":function(e,t,c){"use strict";c.d(t,"d",(function(){return b})),c.d(t,"c",(function(){return d})),c.d(t,"a",(function(){return j})),c.d(t,"b",(function(){return s}));c("a9e3"),c("25eb"),c("380f");var n=c("f64c"),a=c("7a23"),u=c("6c02"),r=c("e644"),i=c("48b8"),o=c("bcf4"),b=Object(a["E"])({uid:-1,username:"------",avatar:"",email:void 0,phone:void 0,level:0,createTime:"1999-01-01",signature:"",roleName:"",hcodeSize:0,topicStat:{likes:0,views:0,favNum:0}}),s=Object(a["F"])(""),f=function(){b.uid<=0||o["b"].uid<=0||(s.value=b.uid===o["b"].uid)};function d(){var e=Object(u["c"])().params.uid;if("string"===typeof e)try{e=Number.parseInt(e,10)}catch(t){return}r["b"].spaceInfo(e).then((function(e){var t=e.data.data;Object(i["a"])(t||b,b)})).catch((function(e){n["a"].error(e.message)}))}function j(e){switch(e){case"站长":return"red";case"临时用户":return"gray";case"注册会员":return"";case"正式会员":return"blue";default:}return"black"}Object(a["P"])((function(){return o["b"].uid}),f),Object(a["P"])((function(){return b.uid}),f)},"71fb":function(e,t,c){"use strict";c.r(t);var n=c("7a23"),a=Object(n["S"])("data-v-308ba341");Object(n["D"])("data-v-308ba341");var u={class:"uspace-container"},r=Object(n["l"])(" 不存在此用户 ");Object(n["B"])();var i=a((function(e,t,c,i,o,b){var s=Object(n["H"])("user-info"),f=Object(n["H"])("a-col"),d=Object(n["H"])("main-tabs"),j=Object(n["H"])("a-row"),O=Object(n["H"])("a-spin"),l=Object(n["H"])("default-layout");return Object(n["A"])(),Object(n["j"])(l,null,{default:a((function(){return[Object(n["m"])("div",u,[0!==e.spaceUserInfo.uid?(Object(n["A"])(),Object(n["j"])(O,{key:0,tip:"加载中...",spinning:-1===e.spaceUserInfo.uid},{default:a((function(){return[Object(n["m"])(j,{gutter:16},{default:a((function(){return[Object(n["m"])(f,{span:7,class:"user-info"},{default:a((function(){return[Object(n["m"])(s)]})),_:1}),Object(n["m"])(f,{span:17,class:"user-stream-detail"},{default:a((function(){return[Object(n["m"])(d,{"tab-key":e.spaceTabKey},null,8,["tab-key"])]})),_:1})]})),_:1})]})),_:1},8,["spinning"])):(Object(n["A"])(),Object(n["j"])(j,{key:1},{default:a((function(){return[r]})),_:1}))])]})),_:1})})),o=(c("ac1f"),c("5319"),Object(n["S"])("data-v-df33d5ac"));Object(n["D"])("data-v-df33d5ac");var b={class:"uname"},s={class:"signature"},f={style:{display:"flex","justify-content":"space-around"}},d={class:"topic-stat"},j=Object(n["m"])("p",null,"阅读总数",-1),O={class:"topic-stat"},l=Object(n["m"])("p",null,"点赞总数",-1),m={class:"topic-stat"},p=Object(n["m"])("p",null,"收藏总数",-1),v=Object(n["l"])("贡献了特殊码 "),g=Object(n["l"])(" 个"),y={class:"uid"},_={class:"create-time"};Object(n["B"])();var h=o((function(e,t,c,a,u,r){var i=Object(n["H"])("UserOutlined"),h=Object(n["H"])("a-avatar"),k=Object(n["H"])("a-col"),w=Object(n["H"])("a-tag"),H=Object(n["H"])("a-space"),S=Object(n["H"])("a-row"),I=Object(n["H"])("a-card"),N=Object(n["H"])("a-divider");return Object(n["A"])(),Object(n["j"])(n["b"],null,[Object(n["m"])(I,{class:"uinfo card"},{default:o((function(){return[Object(n["m"])(S,{gutter:16},{default:o((function(){return[Object(n["m"])(k,{span:8},{default:o((function(){return[e.uinfo.uid<=0?(Object(n["A"])(),Object(n["j"])(h,{key:0,size:86},{icon:o((function(){return[Object(n["m"])(i)]})),_:1})):(Object(n["A"])(),Object(n["j"])(h,{key:1,size:86,src:e.ANNEX_PREFIX+e.uinfo.avatar||1,class:e.isThis?"avatar-mask":"",onClick:t[1]||(t[1]=function(t){return e.isThis?e.openAvatarChangePage():void 0})},null,8,["src","class"]))]})),_:1}),Object(n["m"])(k,{span:16},{default:o((function(){return[Object(n["m"])("h2",b,Object(n["J"])(e.uinfo.username),1),Object(n["m"])(H,{size:8},{default:o((function(){return[Object(n["m"])(w,{color:e.roleTagColor},{default:o((function(){return[Object(n["l"])(Object(n["J"])(e.uinfo.roleName.replace("会员","用户")),1)]})),_:1},8,["color"])]})),_:1})]})),_:1})]})),_:1}),Object(n["m"])("p",s,Object(n["J"])(e.uinfo.signature),1)]})),_:1}),Object(n["m"])(I,{class:"creator-info card"},{default:o((function(){return[Object(n["m"])("div",f,[Object(n["m"])("div",d,[Object(n["m"])("h3",null,Object(n["J"])(e.uinfo.topicStat.views),1),j]),Object(n["m"])("div",O,[Object(n["m"])("h3",null,Object(n["J"])(e.uinfo.topicStat.likes),1),l]),Object(n["m"])("div",m,[Object(n["m"])("h3",null,Object(n["J"])(e.uinfo.topicStat.favNum),1),p])]),Object(n["m"])(N),Object(n["m"])("p",null,[v,Object(n["m"])("span",null,Object(n["J"])(e.uinfo.hcodeSize),1),g])]})),_:1}),Object(n["m"])(I,{class:"meta-info card"},{default:o((function(){return[Object(n["m"])(H,{size:80},{default:o((function(){return[Object(n["m"])("div",y," UID: "+Object(n["J"])(e.uinfo.uid),1),Object(n["m"])("div",_," 加入时间: "+Object(n["J"])(e.uinfo.createTime),1)]})),_:1})]})),_:1})],64)})),k=(c("99af"),c("edc4")),w=c("646c"),H=c("dee4"),S=c("48b8"),I=Object(n["n"])({name:"UserInfo",components:{UserOutlined:k["a"]},setup:function(e){var t=Object(n["F"])(Object(w["a"])(w["d"].roleName));Object(n["P"])((function(){return w["d"].roleName}),(function(e){t.value=Object(w["a"])(e)}));var c=function(){Object(S["c"])("".concat(H["b"],"/space/").concat(w["d"].uid,"/avatar"),{},!0)};return{uinfo:w["d"],ANNEX_PREFIX:H["a"],roleTagColor:t,isThis:w["b"],openAvatarChangePage:c}}});c("2c12");I.render=h,I.__scopeId="data-v-df33d5ac";var N=I,T=Object(n["S"])("data-v-cbb9c798");Object(n["D"])("data-v-cbb9c798");var A=Object(n["l"])(" 动态 "),J=Object(n["l"])(" 手记 "),K=Object(n["l"])(" 设置 ");Object(n["B"])();var P=T((function(e,t,c,a,u,r){var i=Object(n["H"])("router-link"),o=Object(n["H"])("a-menu-item"),b=Object(n["H"])("a-menu"),s=Object(n["H"])("router-view"),f=Object(n["H"])("a-card");return Object(n["A"])(),Object(n["j"])(f,{class:"stream-warp card","body-style":{padding:"6px 20px 20px 20px"}},{default:T((function(){return[Object(n["m"])(b,{selectedKeys:e.spaceTabKey,"onUpdate:selectedKeys":t[1]||(t[1]=function(t){return e.spaceTabKey=t}),mode:"horizontal"},{default:T((function(){return[Object(n["m"])(o,{key:"main"},{default:T((function(){return[Object(n["m"])(i,{to:"/space/".concat(e.uid)},{default:T((function(){return[A]})),_:1},8,["to"])]})),_:1}),Object(n["m"])(o,{key:"notes"},{default:T((function(){return[Object(n["m"])(i,{to:"/space/".concat(e.uid,"/notes")},{default:T((function(){return[J]})),_:1},8,["to"])]})),_:1}),e.userInfo.uid==e.uid?(Object(n["A"])(),Object(n["j"])(o,{key:"setting"},{default:T((function(){return[Object(n["m"])(i,{to:"/space/".concat(e.uid,"/setting")},{default:T((function(){return[K]})),_:1},8,["to"])]})),_:1})):Object(n["k"])("",!0)]})),_:1},8,["selectedKeys"]),Object(n["m"])(s)]})),_:1})})),U=(c("b0c0"),c("6c02")),z=c("bcf4"),C=Object(n["n"])({name:"MainTabs",props:["tabKey"],setup:function(e){var t=Object(n["F"])(["main"]);switch(Object(U["c"])().name){case"SpaceCreator":t.value=["creator"];break;case"SpaceNotes":t.value=["notes"];break;case"SpaceSetting":t.value=["setting"];break;default:}return Object(n["P"])((function(){return e.tabKey}),(function(e){t.value=e})),{spaceTabKey:t,userInfo:z["b"],uid:Object(U["c"])().params.uid}}});c("e6e5");C.render=P,C.__scopeId="data-v-cbb9c798";var F=C,x=Object(n["n"])({name:"SpaceHome",components:{UserInfo:N,MainTabs:F},setup:function(){Object(n["y"])((function(){Object(w["c"])()}));var e=Object(n["F"])(["main"]),t=Object(U["c"])(),c=Object(U["d"])();return Object(n["P"])((function(){return w["d"].roleName}),(function(n){"临时用户"===n&&(e.value=["setting"],c.push({name:"SpaceSetting",params:{uid:t.params.uid}}))})),{spaceUserInfo:w["d"],spaceTabKey:e}}});c("fca9");x.render=i,x.__scopeId="data-v-308ba341";t["default"]=x},aead:function(e,t,c){},e6e5:function(e,t,c){"use strict";c("63db")},fca9:function(e,t,c){"use strict";c("aead")}}]);