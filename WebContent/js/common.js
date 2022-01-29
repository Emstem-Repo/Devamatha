 
/* start details of code when they click button,image,anchor,checkbox,select something then it will not invalidate session*/

$(function() {
	 $("a,.formbutton,img,input:checkbox,.comboLarge,.combo,.comboMediumLarge,.comboExtraLarge,.comboBig,.comboMediumBig,.comboSmall,.comboMedium,*:text,:button").each(function(){
		var method = $(this).attr("onclick"); 
		var method1 = $(this).attr("onmousedown");
		var method2 = $(this).attr("onchange");
		var method3 = $(this).attr("onblur");
		if(method != undefined){
			method ="appendMethodOnBrowserClose(),"+method;
			$(this).attr("onclick",method);
		}else if(method1!=undefined){
			method1 ="appendMethodOnBrowserClose(),"+method1;
			$(this).attr("onmousedown",method1);
		}
		else if(method2!=undefined)
		{
			method2 ="appendMethodOnBrowserClose(),"+method2;
			$(this).attr("onchange",method2);
		}
		else if(method3!=undefined)
		{
			method3 ="appendMethodOnBrowserClose(),"+method3;
			$(this).attr("onblur",method3);
		}else{
			method ="appendMethodOnBrowserClose()";
			$(this).attr("onclick",method);
		}
	 });
	});

function appendMethodOnBrowserClose(){
	hook = false;
}
/* end details of code when they click button,image,anchor,checkbox,select something then it will not invalidate session*/
function resetErrMsgs() {
	document.getElementById("errorMessage").innerHTML = "";
	if(document.getElementById("err") != null ) {
		document.getElementById("err").innerHTML = "";
	}	
}

function resetFieldAndErrMsgs()	{

    var selects = document.getElementsByTagName("select");
    var select;
    // iterating all the selects
    if(selects!=null){
    for(var count=0;count<selects.length;count++) {
        selectObj = selects[count];
        // finding multiple is true
        
        if(selectObj.getAttribute("multiple") == true || selectObj.getAttribute("multiple") == 'multiple') {
           // if true setting index to -1 for reset.
        	selectObj.selectedIndex = -1;  
        } else  {
        	selectObj.selectedIndex = 0;
        }
    }        
    }
    var inputs = document.getElementsByTagName("input");
    var inputObj;
    for(var count1 = 0;count1<inputs.length;count1++) {
	    inputObj = inputs[count1];
	    var type = inputObj.getAttribute("type");
	   	if(type == 'text') {
	   		inputObj.value = "";
		} else if (type == 'checkbox') {
			inputObj.checked = false;
		} else if (type == 'radio') {
			inputObj.checked = "";
		} else if (type == 'file') {
			inputObj.value = "";
		} else if (type == 'password') {
			inputObj.value = "";
		}				
        
    }    
    
    var textAreas = document.getElementsByTagName("textarea");
    var areaObj;
    for(var count2 = 0;count2<textAreas.length;count2++) {
    	areaObj = textAreas[count2];
	   
	   		areaObj.value = "";
		
    }  
	resetErrMsgs();
}

function checkForEmpty(field){
	if(field.value.length == 0)
		field.value="0";
}

function onlyDecimalNumber(val, e) {
	var keynum;
	var keychar;
	var numcheck;

	if (window.event) // IE
	{
		keynum = e.keyCode;
	} else if (e.which) // Netscape/Firefox/Opera
	{
		keynum = e.which;
	}
	if (keynum == 8 || keynum == 37 || keynum == 39 || keynum == 9) {
		return true;
	}
	if (keynum == 190) {
		var contain = val.indexOf('.');
		if (contain == -1) {
			return true;
		}
	}
	keychar = String.fromCharCode(keynum);
	numcheck = /\d/;
	return numcheck.test(keychar);
}

function hideButton(button) {
	button.style.display = "none";
}

function resetAcademicYear(academicYear) {
	var destinationOption = document.getElementById(academicYear);
	destinationOption.selectedIndex = 0;
}


function isNumberKey(evt)
{  var charCode = (evt.which) ? evt.which : evt.keyCode;
      
   if (charCode > 31 && (charCode < 48 || charCode > 57))
      return false;

   return true;
}

function isDecimalNumberKey(val,evt)
{  var charCode = (evt.which) ? evt.which : evt.keyCode;
   if(charCode == 46){
	   var contain = val.indexOf('.');
	   if(contain == -1) { return true;}
	}
   
   if (charCode > 31 && (charCode < 48 || charCode > 57))
      return false;

   return true;
}
function onlyTwoFractions(obj,evt) {
   	
    var val = obj.value;
	var position = val.indexOf('.');
	var length = val.length;
	 
	if(position != -1) {
        var temp = parseInt(length - (position + 1));
		if(temp >= 3) {
			obj.value = val.substring(0,parseInt(position+3));
		}		
	}
	
}

function isAlphaNumberKey(evt)
{  var charCode = (evt.which) ? evt.which : evt.keyCode;
   if(((charCode >= 97) && (charCode <= 122)) ||((charCode >= 65) && (charCode <= 90))) {
      return true;
   }	      
   if (charCode > 31 && (charCode < 48 || charCode > 57))
      return false;

   return true;
}
function isNegativeDecimalNumberKey(val,evt)
{  var charCode = (evt.which) ? evt.which : evt.keyCode;
   if(charCode == 46){
	   var contain = val.indexOf('.');
	   if(contain == -1) { return true;}
	}
   if(charCode == 43 || charCode == 45) {
	   return true;
   }	   
   
   if (charCode > 31 && (charCode < 48 || charCode > 57))
      return false;

   return true;
}
function trim(str) {     
if (str != null) 
{       
	var i;         
	for (i=0; i<str.length; i++) 
	{            
		if (str.charAt(i)!=" ") 
		{                
			str=str.substring(i,str.length);                 
			break;            
		}         
	}             
	for (i=str.length-1; i>=0; i--) 
	{            
		if (str.charAt(i)!=" ") 
		{                
			str=str.substring(0,i+1);                 
			break;            
		}
	}                 
	if (str.charAt(0)==" ") 
	{            
		return "";         
	} else 
	{            
		return str;         
	}    
}
return null;
}
function resetYear(){
	var d = new Date();
	var currentYear = d.getFullYear();
	return currentYear;
}
function resetMonth(){
	var d = new Date();
	var month = d.getMonth();
	return month;
}

function onlyFourFractions(obj,evt) {
   	
    var val = obj.value;
	var position = val.indexOf('.');
	var length = val.length;
	 
	if(position != -1) {
        var temp = parseInt(length - (position + 1));
		if(temp >= 5) {
			obj.value = val.substring(0,parseInt(position+5));
		}		
	}
	
}

function getMaxLength(obj)
{
	obj.value=obj.value.substring(0,99);
}


var one_day=1000*60*60*24;
var one_month=1000*60*60*24*30;
var one_year=1000*60*60*24*30*12;

function displayage(yr, mon, day, unit, decimal, round){
	today=new Date();
	var pastdate=new Date(yr, mon-1, day);
	
	var countunit=unit;
	var decimals=decimal;
	var rounding=round;
	
	finalunit=(countunit=="days")? one_day : (countunit=="months")? one_month : one_year;
	decimals=(decimals<=0)? 1 : decimals*10;
	
	if (unit!="years"){
	if (rounding=="rounddown")
	document.write(Math.floor((today.getTime()-pastdate.getTime())/(finalunit)*decimals)/decimals+" "+countunit);
	else
	document.write(Math.ceil((today.getTime()-pastdate.getTime())/(finalunit)*decimals)/decimals+" "+countunit);
	}
	else{
	yearspast=today.getFullYear()-yr-1;
	tail=(today.getMonth()>mon-1 || today.getMonth()==mon-1 && today.getDate()>=day)? 1 : 0;
	pastdate.setFullYear(today.getFullYear());
	pastdate2=new Date(today.getFullYear()-1, mon-1, day);
	tail=(tail==1)? tail+Math.floor((today.getTime()-pastdate.getTime())/(finalunit)*decimals)/decimals : Math.floor((today.getTime()-pastdate2.getTime())/(finalunit)*decimals)/decimals;
	document.getElementById("age").value=yearspast+tail;
	}
}

function addNewMaster(form,value,mainPage,destinationMasterName,destinationMethodName){
	if(value=='AddNew' && (mainPage=="quotationSubmit" || mainPage=="purchaseOrderSubmit")){
	form.elements.method.value="addNewMaster";
	form.elements.superAddNewType.value=destinationMasterName;
	form.elements.superMainPage.value=mainPage;
	form.elements.destinationMethod.value=destinationMethodName;
	form.submit();
	}
	else if(value=='AddNew'){
		form.elements.method.value="addNewMaster";
		form.elements.addNewType.value=destinationMasterName;
		form.elements.mainPage.value=mainPage;
		form.elements.destinationMethod.value=destinationMethodName;
		form.submit();
		}
}

function goToMainPage(mainPage){
	document.location.href=mainPage+".do?method=goToMainPage";
}