
function submitEmployeeInfoAdd(method,mode){
	document.employeeInfoForm.method.value=method;
	document.employeeInfoForm.mode.value=mode;
	document.employeeInfoForm.submit();
}
function submitEmployeeInfo(method){
	document.employeeInfoForm.method.value=method;
	document.employeeInfoForm.submit();
}


function submitEmployeePersonalInfo(){
	document.employeeInfoForm.method.value="saveEmployeePersonalInfo";
	document.employeeInfoForm.pageType.value=2;
	document.employeeInfoForm.submit();
}

function updateGrossPay(size){
	 var basicpay=0.0;
	 var grosspay=0.0;
	 var allowpay=0.0;
	var basic=document.getElementById("basicPay").value;

	document.getElementById("grosspay").Value="";
	if(basic!=null && basic.length>0 && IsNumeric(basic))
	{
		
		basicpay=parseFloat(basic);
	}
	
	
	var i=0;

	while(i< size){
		var id="Allowance"+i;

		var allowance=document.getElementById(id).value;
		if(allowance!=null && allowance.length >0 && IsNumeric(allowance))
		{
			var tempallow=0.0;
			tempallow=parseFloat(allowance);
			allowpay=allowpay + tempallow;
		}
		i++;
	}
	grosspay=basicpay+allowpay;
	

if(document.getElementById('grosspay')!=null){

	document.getElementById('grosspay').value=grosspay;  
}	
	
}

function IsNumeric(sText)
{
   var ValidChars = "0123456789.";
   var IsNumber=true;
   var Char;
   for (i = 0; i < sText.length && IsNumber == true; i++) 
      { 
      Char = sText.charAt(i); 
      if (ValidChars.indexOf(Char) == -1) 
         {
         IsNumber = false;
         }
      }
   return IsNumber;
   
   }