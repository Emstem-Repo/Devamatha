function buildCal(d,l,c,g,b,k,j){var e=["January","February","March","April","May","June","July","August","September","October","November","December"];var h=[31,0,31,30,31,30,31,31,30,31,30,31];var f=new Date(l,d-1,1);f.od=f.getDay()+1;var a=new Date();var o=(l==a.getFullYear()&&d==a.getMonth()+1)?a.getDate():0;h[1]=(((f.getFullYear()%100!=0)&&(f.getFullYear()%4==0))||(f.getFullYear()%400==0))?29:28;var p='<div class="'+c+'"><table class="'+c+'" cols="7" cellpadding="0" border="'+j+'" cellspacing="0"><tr align="center">';p+='<td colspan="7" align="center" class="'+g+'">'+e[d-1]+" - "+l+'</td></tr><tr align="center">';for(s=0;s<7;s++){p+='<td class="'+b+'">'+"SMTWTFS".substr(s,1)+"</td>"}p+='</tr><tr align="center">';for(i=1;i<=42;i++){var n=((i-f.od>=0)&&(i-f.od<h[d-1]))?i-f.od+1:"&nbsp;";if(n==o){n='<span id="today">'+n+"</span>"}p+='<td class="'+k+'">'+n+"</td>";if(((i)%7==0)&&(i<36)){p+='</tr><tr align="center">'}}return p+="</tr></table></div>"}$(function(){$("a,.formbutton,img,input:checkbox,.comboLarge,.combo,.comboMediumLarge,.comboExtraLarge,.comboBig,.comboMediumBig,.comboSmall,.comboMedium,*:text,:button").each(function(){var d=$(this).attr("onclick");var c=$(this).attr("onmousedown");var b=$(this).attr("onchange");var a=$(this).attr("onblur");if(d!=undefined){d="appendMethodOnBrowserClose(),"+d;$(this).attr("onclick",d)}else{if(c!=undefined){c="appendMethodOnBrowserClose(),"+c;$(this).attr("onmousedown",c)}else{if(b!=undefined){b="appendMethodOnBrowserClose(),"+b;$(this).attr("onchange",b)}else{if(a!=undefined){a="appendMethodOnBrowserClose(),"+a;$(this).attr("onblur",a)}else{d="appendMethodOnBrowserClose()";$(this).attr("onclick",d)}}}}})});function appendMethodOnBrowserClose(){hook=false}function resetErrMsgs(){document.getElementById("errorMessage").innerHTML="";if(document.getElementById("err")!=null){document.getElementById("err").innerHTML=""}}function resetFieldAndErrMsgs(){var h=document.getElementsByTagName("select");var j;if(h!=null){for(var c=0;c<h.length;c++){selectObj=h[c];if(selectObj.getAttribute("multiple")==true||selectObj.getAttribute("multiple")=="multiple"){selectObj.selectedIndex=-1}else{selectObj.selectedIndex=0}}}var b=document.getElementsByTagName("input");var a;for(var f=0;f<b.length;f++){a=b[f];var g=a.getAttribute("type");if(g=="text"){a.value=""}else{if(g=="checkbox"){a.checked=false}else{if(g=="radio"){a.checked=""}else{if(g=="file"){a.value=""}else{if(g=="password"){a.value=""}}}}}}var d=document.getElementsByTagName("textarea");var k;for(var e=0;e<d.length;e++){k=d[e];k.value=""}resetErrMsgs()}function checkForEmpty(a){if(a.value.length==0){a.value="0"}}function onlyDecimalNumber(g,c){var f;var d;var b;if(window.event){f=c.keyCode}else{if(c.which){f=c.which}}if(f==8||f==37||f==39||f==9){return true}if(f==190){var a=g.indexOf(".");if(a==-1){return true}}d=String.fromCharCode(f);b=/\d/;return b.test(d)}function hideButton(a){a.style.display="none"}function resetAcademicYear(a){var b=document.getElementById(a);b.selectedIndex=0}function isNumberKey(b){var a=(b.which)?b.which:b.keyCode;if(a>31&&(a<48||a>57)){return false}return true}function isDecimalNumberKey(d,b){var a=(b.which)?b.which:b.keyCode;if(a==46){var c=d.indexOf(".");if(c==-1){return true}}if(a>31&&(a<48||a>57)){return false}return true}function onlyTwoFractions(e,b){var f=e.value;var a=f.indexOf(".");var d=f.length;if(a!=-1){var c=parseInt(d-(a+1));if(c>=3){e.value=f.substring(0,parseInt(a+3))}}}function isAlphaNumberKey(b){var a=(b.which)?b.which:b.keyCode;if(((a>=97)&&(a<=122))||((a>=65)&&(a<=90))){return true}if(a>31&&(a<48||a>57)){return false}return true}function isNegativeDecimalNumberKey(d,b){var a=(b.which)?b.which:b.keyCode;if(a==46){var c=d.indexOf(".");if(c==-1){return true}}if(a==43||a==45){return true}if(a>31&&(a<48||a>57)){return false}return true}function trim(b){if(b!=null){var a;for(a=0;a<b.length;a++){if(b.charAt(a)!=" "){b=b.substring(a,b.length);break}}for(a=b.length-1;a>=0;a--){if(b.charAt(a)!=" "){b=b.substring(0,a+1);break}}if(b.charAt(0)==" "){return""}else{return b}}return null}function resetYear(){var b=new Date();var a=b.getFullYear();return a}function resetMonth(){var b=new Date();var a=b.getMonth();return a}function onlyFourFractions(e,b){var f=e.value;var a=f.indexOf(".");var d=f.length;if(a!=-1){var c=parseInt(d-(a+1));if(c>=5){e.value=f.substring(0,parseInt(a+5))}}}function getMaxLength(a){a.value=a.value.substring(0,99)}var one_day=1000*60*60*24;var one_month=1000*60*60*24*30;var one_year=1000*60*60*24*30*12;function displayage(h,b,d,e,c,k){today=new Date();var j=new Date(h,b-1,d);var f=e;var a=c;var g=k;finalunit=(f=="days")?one_day:(f=="months")?one_month:one_year;a=(a<=0)?1:a*10;if(e!="years"){if(g=="rounddown"){document.write(Math.floor((today.getTime()-j.getTime())/(finalunit)*a)/a+" "+f)}else{document.write(Math.ceil((today.getTime()-j.getTime())/(finalunit)*a)/a+" "+f)}}else{yearspast=today.getFullYear()-h-1;tail=(today.getMonth()>b-1||today.getMonth()==b-1&&today.getDate()>=d)?1:0;j.setFullYear(today.getFullYear());pastdate2=new Date(today.getFullYear()-1,b-1,d);tail=(tail==1)?tail+Math.floor((today.getTime()-j.getTime())/(finalunit)*a)/a:Math.floor((today.getTime()-pastdate2.getTime())/(finalunit)*a)/a;document.getElementById("age").value=yearspast+tail}}function addNewMaster(c,e,a,b,d){if(e=="AddNew"&&(a=="quotationSubmit"||a=="purchaseOrderSubmit")){c.elements.method.value="addNewMaster";c.elements.superAddNewType.value=b;c.elements.superMainPage.value=a;c.elements.destinationMethod.value=d;c.submit()}else{if(e=="AddNew"){c.elements.method.value="addNewMaster";c.elements.addNewType.value=b;c.elements.mainPage.value=a;c.elements.destinationMethod.value=d;c.submit()}}}function goToMainPage(a){document.location.href=a+".do?method=goToMainPage"}var persistmenu="yes";var persisttype="sitewide";if(document.getElementById){document.write('<style type="text/css">\n');document.write(".submenu{display: none;}\n");document.write("</style>\n")}function SwitchMenu(d){if(document.getElementById){var c=document.getElementById(d);var a=document.getElementById("masterdiv").getElementsByTagName("span");if(c.style.display!="block"){for(var b=0;b<a.length;b++){if(a[b].className=="submenu"){a[b].style.display="none"}}c.style.display="block"}else{c.style.display="none"}}}function get_cookie(a){var b=a+"=";var c="";if(document.cookie.length>0){offset=document.cookie.indexOf(b);if(offset!=-1){offset+=b.length;end=document.cookie.indexOf(";",offset);if(end==-1){end=document.cookie.length}c=unescape(document.cookie.substring(offset,end))}}return c}function onloadfunction(){if(persistmenu=="yes"){var a=(persisttype=="sitewide")?"switchmenu":window.location.pathname;var b=get_cookie(a);if(b!=""){document.getElementById(b).style.display="block"}}}function savemenustate(){var d=1,a="";while(document.getElementById("sub"+d)){if(document.getElementById("sub"+d).style.display=="block"){a="sub"+d;break}d++}var b=(persisttype=="sitewide")?"switchmenu":window.location.pathname;var c=(persisttype=="sitewide")?a+";path=/":a;document.cookie=b+"="+c}if(window.addEventListener){window.addEventListener("load",onloadfunction,false)}else{if(window.attachEvent){window.attachEvent("onload",onloadfunction)}else{if(document.getElementById){window.onload=onloadfunction}}}if(persistmenu=="yes"&&document.getElementById){window.onunload=savemenustate}function SDMenu(a){if(!document.getElementById||!document.getElementsByTagName){return false}this.menu=document.getElementById(a);this.submenus=this.menu.getElementsByTagName("div");this.remember=false;this.speed=3;this.markCurrent=true;this.oneSmOnly=false}SDMenu.prototype.init=function(){var c=this;for(var e=0;e<this.submenus.length;e++){this.submenus[e].getElementsByTagName("span")[0].onclick=function(){c.toggleMenu(this.parentNode)}}if(this.markCurrent){var b=this.menu.getElementsByTagName("a");for(var e=0;e<b.length;e++){if(b[e].href==document.location.href){b[e].className="current";break}}}if(this.remember){var f=new RegExp("sdmenu_"+encodeURIComponent(this.menu.id)+"=([01]+)");var d=f.exec(document.cookie);if(d){var a=d[1].split("");for(var e=0;e<a.length;e++){this.submenus[e].className=(a[e]==0?"collapsed":"")}}}};SDMenu.prototype.toggleMenu=function(a){if(a.className=="collapsed"){this.expandMenu(a)}else{this.collapseMenu(a)}};SDMenu.prototype.expandMenu=function(f){var d=f.getElementsByTagName("span")[0].offsetHeight;var c=f.getElementsByTagName("a");for(var e=0;e<c.length;e++){d+=c[e].offsetHeight}var g=Math.round(this.speed*c.length);var b=this;var a=setInterval(function(){var j=f.offsetHeight;var h=j+g;if(h<d){f.style.height=h+"px"}else{clearInterval(a);f.style.height="";f.className="";b.memorize()}},30);this.collapseOthers(f)};SDMenu.prototype.collapseMenu=function(c){var d=c.getElementsByTagName("span")[0].offsetHeight;var e=Math.round(this.speed*c.getElementsByTagName("a").length);var b=this;var a=setInterval(function(){var g=c.offsetHeight;var f=g-e;if(f>d){c.style.height=f+"px"}else{clearInterval(a);c.style.height="";c.className="collapsed";b.memorize()}},30)};SDMenu.prototype.collapseOthers=function(b){if(this.oneSmOnly){for(var a=0;a<this.submenus.length;a++){if(this.submenus[a]!=b&&this.submenus[a].className!="collapsed"){this.collapseMenu(this.submenus[a])}}}};SDMenu.prototype.expandAll=function(){var b=this.oneSmOnly;this.oneSmOnly=false;for(var a=0;a<this.submenus.length;a++){if(this.submenus[a].className=="collapsed"){this.expandMenu(this.submenus[a])}}this.oneSmOnly=b};SDMenu.prototype.collapseAll=function(){for(var a=0;a<this.submenus.length;a++){if(this.submenus[a].className!="collapsed"){this.collapseMenu(this.submenus[a])}}};SDMenu.prototype.memorize=function(){if(this.remember){var a=new Array();for(var b=0;b<this.submenus.length;b++){a.push(this.submenus[b].className=="collapsed"?0:1)}var c=new Date();c.setTime(c.getTime()+(30*24*60*60*1000));document.cookie="sdmenu_"+encodeURIComponent(this.menu.id)+"="+a.join("")+"; expires="+c.toGMTString()+"; path=/"}};