(this["webpackJsonpweather-history"]=this["webpackJsonpweather-history"]||[]).push([[0],{22:function(e,t,a){e.exports=a(50)},27:function(e,t,a){},28:function(e,t,a){},50:function(e,t,a){"use strict";a.r(t);var n=a(0),r=a.n(n),c=a(4),l=a.n(c),o=(a(27),a(7)),i=a(6),u=(a(28),a(17)),s=a.n(u);var m=function(e){return r.a.createElement("tr",null,r.a.createElement("td",null,e.dateTime),r.a.createElement("td",null,e.temperature," \xb0C"))},d=a(65);var h=function(){var e=Object(n.useState)({weather:[]}),t=Object(i.a)(e,2),a=t[0],c=t[1],l=Object(n.useState)({currentPage:1,offset:0,cardsPerPage:20}),u=Object(i.a)(l,2),h=u[0],f=u[1],g=Math.ceil(a.weather.length/h.cardsPerPage),p=a.weather.sort((function(e,t){return e.dateTime<t.dateTime?1:e.dateTime>t.dateTime?-1:0})).slice(h.offset,h.currentPage*h.cardsPerPage);return Object(n.useEffect)((function(){s.a.get("/api/weather").then((function(e){c({weather:e.data})}))}),[]),r.a.createElement("div",null,r.a.createElement("h1",null,"Weather history in Vilnius"),r.a.createElement("table",null,r.a.createElement("tbody",null,r.a.createElement("tr",null,r.a.createElement("th",null,"Time"),r.a.createElement("th",null,"Temperature")),p.map((function(e){return r.a.createElement(m,{key:e.id,temperature:e.temperature,dateTime:new Date(e.dateTime).toLocaleString("lt-LT")})})))),r.a.createElement("div",null,r.a.createElement(d.a,{style:{display:"flex",alignItems:"center",justifyContent:"center",paddingTop:"2%"},count:g,siblingCount:1,onChange:function(e,t){e.preventDefault(),f(Object(o.a)(Object(o.a)({},h),{},{currentPage:t,offset:(t-1)*h.cardsPerPage}))},page:h.currentPage,size:"large"})))};Boolean("localhost"===window.location.hostname||"[::1]"===window.location.hostname||window.location.hostname.match(/^127(?:\.(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)){3}$/));l.a.render(r.a.createElement(r.a.StrictMode,null,r.a.createElement(h,null)),document.getElementById("root")),"serviceWorker"in navigator&&navigator.serviceWorker.ready.then((function(e){e.unregister()})).catch((function(e){console.error(e.message)}))}},[[22,1,2]]]);
//# sourceMappingURL=main.c87e1c96.chunk.js.map