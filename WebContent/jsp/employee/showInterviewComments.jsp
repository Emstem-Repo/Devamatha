
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 

"http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@page import="com.kp.cms.constants.CMSConstants"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="css/styles.css" rel="stylesheet" type="text/css">
<style type="text/css">
input {
	border: 0;
	size: 1;
}
</style>
<script type="text/javascript" src="js/jquery.js">
		</script>
<script type="text/javascript">
	
	function printPass() {	
		window.print();
	}
	
	$(document).ready(function(){
		 
	    //iterate through each textboxes and add keyup
	    //handler to trigger sum event
	    $(".txt1").each(function() {
	
	        $(this).keyup(function(){
	            calculateSum1();
	        });
	        calculateSum1();
	    });
	
	});
	
	function calculateSum1() {
	
	    var sum1 = 0;
	    //iterate through each textboxes and add the values
	    $(".txt1").each(function() {
	
	        //add only if the value is number
	        if(!isNaN(this.value) && this.value.length!=0) {
		       
	            sum1 += parseFloat(this.value);
	        }
	
	    });
	    //.toFixed() method will roundoff the final sum to 2 decimal places
	    if(sum1!=null && sum1!=0){
			$("#sum1").html(sum1.toFixed());
	   }else{
	   		$("#sum1").html(" ");
	   }
	}
	//2.
	$(document).ready(function(){
	 
	//iterate through each textboxes and add keyup
	//handler to trigger sum event
	$(".txt2").each(function() {
	
	    $(this).keyup(function(){
	        calculateSum2();
	    });
	    calculateSum2();
	});
	
	});
	
	function calculateSum2() {
	
	var sum2 = 0;
	//iterate through each textboxes and add the values
	$(".txt2").each(function() {
	
	    //add only if the value is number
	    if(!isNaN(this.value) && this.value.length!=0) {
	        sum2 += parseFloat(this.value);
	    }
	
	});
	//.toFixed() method will roundoff the final sum to 2 decimal places
	if(sum2!=null && sum2!=0){
		$("#sum2").html(sum2.toFixed());
   }else{
   		$("#sum2").html(" ");
   }
	}
	
	//3.
	//
	$(document).ready(function(){
	 
	//iterate through each textboxes and add keyup
	//handler to trigger sum event
	$(".txt3").each(function() {
	
	    $(this).keyup(function(){
	        calculateSum3();
	    });
	    calculateSum3();
	});
	
	});
	
	function calculateSum3() {
	
	var sum3 = 0;
	//iterate through each textboxes and add the values
	$(".txt3").each(function() {
	
	    //add only if the value is number
	    if(!isNaN(this.value) && this.value.length!=0) {
	        sum3 += parseFloat(this.value);
	    }
	
	});
	//.toFixed() method will roundoff the final sum to 2 decimal places
	if(sum3!=null && sum3!=0){
		$("#sum3").html(sum3.toFixed());
   }else{
   		$("#sum3").html(" ");
   }
	}
	
	//4.
	$(document).ready(function(){
	 
	//iterate through each textboxes and add keyup
	//handler to trigger sum event
	$(".txt4").each(function() {
	
	    $(this).keyup(function(){
	        calculateSum4();
	    });
	    calculateSum4();
	});
	
	});
	
	function calculateSum4() {
	
	var sum4 = 0;
	//iterate through each textboxes and add the values
	$(".txt4").each(function() {
	
	    //add only if the value is number
	    if(!isNaN(this.value) && this.value.length!=0) {
	        sum4 += parseFloat(this.value);
	    }
	
	});
	//.toFixed() method will roundoff the final sum to 2 decimal places
	if(sum4!=null && sum4!=0){
		$("#sum4").html(sum4.toFixed());
   }else{
   		$("#sum4").html(" ");
   }
	}
	
	//5.
	//
	$(document).ready(function(){
	 
	//iterate through each textboxes and add keyup
	//handler to trigger sum event
	$(".txt5").each(function() {
	
	    $(this).keyup(function(){
	        calculateSum5();
	    });
	    calculateSum5();
	});
	
	});
	
	function calculateSum5() {
	
	var sum5 = 0;
	//iterate through each textboxes and add the values
	$(".txt5").each(function() {
	
	    //add only if the value is number
	    if(!isNaN(this.value) && this.value.length!=0) {
	        sum5 += parseFloat(this.value);
	    }
	
	});
	//.toFixed() method will roundoff the final sum to 2 decimal places
	if(sum5!=null && sum5!=0){
		$("#sum5").html(sum5.toFixed());
   }else{
   		$("#sum5").html(" ");
   }
	}


	//7.
	//
	$(document).ready(function(){
	 
	//iterate through each textboxes and add keyup
	//handler to trigger sum event
	$(".txt6").each(function() {
	
	    $(this).keyup(function(){
	        calculateSum6();
	    });
	    calculateSum6();
	});
	
	});
	
	function calculateSum6() {
	
	var sum6 = 0;
	//iterate through each textboxes and add the values
	$(".txt6").each(function() {
	
	    //add only if the value is number
	    if(!isNaN(this.value) && this.value.length!=0) {
	        sum6 += parseFloat(this.value);
	    }
	
	});
	//.toFixed() method will roundoff the final sum to 2 decimal places
	if(sum6!=null && sum6!=0){
		$("#sum6").html(sum6.toFixed());
   }else{
   		$("#sum6").html(" ");
   }
	}


	//7.
	//
	$(document).ready(function(){
	 
	//iterate through each textboxes and add keyup
	//handler to trigger sum event
	$(".txt7").each(function() {
	
	    $(this).keyup(function(){
	        calculateSum7();
	    });
	    calculateSum7();
	});
	
	});
	
	function calculateSum7() {
	
	var sum7 = 0;
	//iterate through each textboxes and add the values
	$(".txt7").each(function() {
	
	    //add only if the value is number
	    if(!isNaN(this.value) && this.value.length!=0) {
	        sum7 +=parseFloat(this.value);
	    }
	
	});
	//.toFixed() method will roundoff the final sum to 2 decimal places
	if(sum7!=null && sum7!=0){
		$("#sum7").html(sum7.toFixed());
   }else{
   		$("#sum7").html(" ");
   }
	}

	//8.
	//
	$(document).ready(function(){
	 
	//iterate through each textboxes and add keyup
	//handler to trigger sum event
	$(".txt8").each(function() {
	
	    $(this).keyup(function(){
	        calculateSum8();
	    });
	    calculateSum8();
	});
	
	});
	
	function calculateSum8() {
	
	var sum8 = 0;
	//iterate through each textboxes and add the values
	$(".txt8").each(function() {
	
	    //add only if the value is number
	    if(!isNaN(this.value) && this.value.length!=0) {
	        sum8 += parseFloat(this.value);
	    }
	
	});
	//.toFixed() method will roundoff the final sum to 2 decimal places
	if(sum8!=null && sum8!=0){
		$("#sum8").html(sum8.toFixed());
   }else{
   		$("#sum8").html(" ");
   }
	}
	
	//9.  txt9b
	//
	$(document).ready(function(){
	 
	//iterate through each textboxes and add keyup
	//handler to trigger sum event
	$(".txt9").each(function() {
	
	    $(this).keyup(function(){
	        calculateSum9();
	    });
	    calculateSum9();
	});
	
	});
	
	function calculateSum9() {
	
	var sum9 = 0;
	//iterate through each textboxes and add the values
	$(".txt9").each(function() {
	
	    //add only if the value is number
	    if(!isNaN(this.value) && this.value.length!=0) {
	        sum9 += parseFloat(this.value);
	    }
	
	});
	//.toFixed() method will roundoff the final sum to 2 decimal places
	if(sum9!=null && sum9!=0){
		$("#sum9").html(sum9.toFixed());
   }else{
   		$("#sum9").html(" ");
   }
	}

	//10. txt10
	//
	$(document).ready(function(){
	 
	//iterate through each textboxes and add keyup
	//handler to trigger sum event
	$(".txt10").each(function() {
	
	    $(this).keyup(function(){
	        calculateSum10();
	    });
	    calculateSum10();
	});
	
	});
	
	function calculateSum10() {
	
	var sum10 = 0;
	//iterate through each textboxes and add the values
	$(".txt10").each(function() {
	
	    //add only if the value is number
	    if(!isNaN(this.value) && this.value.length!=0) {
	        sum10 += parseFloat(this.value);
	    }
	
	});
	//.toFixed() method will roundoff the final sum to 2 decimal places
	if(sum10!=null && sum10!=0){
		$("#sum10").html(sum10.toFixed());
   }else{
   		$("#sum10").html(" ");
   }
	}

	//10. txt11
	//
	$(document).ready(function(){
	 
	//iterate through each textboxes and add keyup
	//handler to trigger sum event
	$(".txt11").each(function() {
	
	    $(this).keyup(function(){
	        calculateSum11();
	    });
	    calculateSum11();
	});
	
	});
	
	function calculateSum11() {
	
	var sum11 = 0;
	//iterate through each textboxes and add the values
	$(".txt11").each(function() {
	
	    //add only if the value is number
	    if(!isNaN(this.value) && this.value.length!=0) {
	        sum11 += parseFloat(this.value);
	    }
	
	});
	//.toFixed() method will roundoff the final sum to 2 decimal places
	if(sum11!=null && sum11!=0){
		$("#sum11").html(sum11.toFixed());
   }else{
   		$("#sum11").html(" ");
   }
	}
	//12. txt12
	//
	$(document).ready(function(){
	 
	//iterate through each textboxes and add keyup
	//handler to trigger sum event
	$(".txt12").each(function() {
	
	    $(this).keyup(function(){
	        calculateSum12();
	    });
	    calculateSum12();
	});
	
	});
	
	function calculateSum12() {
	
	var sum12 = 0;
	//iterate through each textboxes and add the values
	$(".txt12").each(function() {
	
	    //add only if the value is number
	    if(!isNaN(this.value) && this.value.length!=0) {
	        sum12 += parseFloat(this.value);
	    }
	
	});
	//.toFixed() method will roundoff the final sum to 2 decimal places
	if(sum12!=null && sum12!=0){
		$("#sum12").html(sum12.toFixed());
   }else{
   		$("#sum12").html(" ");
   }
	}
	//13. txt13
	//
	$(document).ready(function(){
	 
	//iterate through each textboxes and add keyup
	//handler to trigger sum event
	$(".txt13").each(function() {
	
	    $(this).keyup(function(){
	        calculateSum13();
	    });
	    calculateSum13();
	});
	
	});
	
	function calculateSum13() {
	
	var sum13 = 0;
	//iterate through each textboxes and add the values
	$(".txt13").each(function() {
	
	    //add only if the value is number
	    if(!isNaN(this.value) && this.value.length!=0) {
	        sum13 += parseFloat(this.value);
	    }
	
	});
	//.toFixed() method will roundoff the final sum to 2 decimal places
	if(sum13!=null && sum13!=0){
		$("#sum13").html(sum13.toFixed());
   }else{
   		$("#sum13").html(" ");
   }
	}
	//14. txt14
	//
	$(document).ready(function(){
	 
	//iterate through each textboxes and add keyup
	//handler to trigger sum event
	$(".txt14").each(function() {
	
	    $(this).keyup(function(){
	        calculateSum14();
	    });
	    calculateSum14();
	});
	
	});
	
	function calculateSum14() {
	
	var sum14 = 0;
	//iterate through each textboxes and add the values
	$(".txt14").each(function() {
	
	    //add only if the value is number
	    if(!isNaN(this.value) && this.value.length!=0) {
	        sum14 += parseFloat(this.value);
	    }
	
	});
	//.toFixed() method will roundoff the final sum to 2 decimal places
	if(sum14!=null && sum14!=0){
		$("#sum14").html(sum14.toFixed());
   }else{
   		$("#sum14").html(" ");
   }
	}
	function calc(count){ 
		var count1=0;
		if(document.getElementById("internalInterviewer1_"+count)!=null){
		 count1 +=document.getElementById("internalInterviewer1_"+count).value*1;
		}
		if(document.getElementById("internalInterviewer2_"+count)!=null){
			count1 +=document.getElementById("internalInterviewer2_"+count).value*1;
		}
		if(document.getElementById("internalInterviewer3_"+count)!=null){
			count1 +=document.getElementById("internalInterviewer3_"+count).value*1;
		}
		if(document.getElementById("internalInterviewer4_"+count)!=null){
			count1 +=document.getElementById("internalInterviewer4_"+count).value*1;
		}
		if(document.getElementById("internalInterviewer5_"+count)!=null){
			count1 +=document.getElementById("internalInterviewer5_"+count).value*1;
		}
		if(document.getElementById("internalInterviewer6_"+count)!=null){
			count1 +=document.getElementById("internalInterviewer6_"+count).value*1;
		}
		if(document.getElementById("externalInterviewer1_"+count)!=null){
			count1 +=document.getElementById("externalInterviewer1_"+count).value*1;
		}
		if(document.getElementById("externalInterviewer2_"+count)!=null){
			count1 +=document.getElementById("externalInterviewer2_"+count).value*1;
		}
		if(document.getElementById("externalInterviewer3_"+count)!=null){
			count1 +=document.getElementById("externalInterviewer3_"+count).value*1;
		}
		if(document.getElementById("externalInterviewer4_"+count)!=null){
			count1 +=document.getElementById("externalInterviewer4_"+count).value*1;
		}
		if(document.getElementById("externalInterviewer5_"+count)!=null){
			count1 +=document.getElementById("externalInterviewer5_"+count).value*1;
		}
		if(document.getElementById("externalInterviewer6_"+count)!=null){
			count1 +=document.getElementById("externalInterviewer6_"+count).value*1;
		}
		 var total= 0;
		 if(document.getElementById("internalInterviewer1_"+count)!=null){
			 if(document.getElementById("internalInterviewer1_"+count).value!=''){
				 total=total+1;
			 }
		 }
		 if(document.getElementById("internalInterviewer2_"+count)!=null){
			 if(document.getElementById("internalInterviewer2_"+count).value!=''){
				 total=total+1;
			 }
		 }
		 if(document.getElementById("internalInterviewer3_"+count)!=null){
			 if(document.getElementById("internalInterviewer3_"+count).value!=''){
				 total=total+1;
			 }
		 }
		 if(document.getElementById("internalInterviewer4_"+count)!=null){
			 if(document.getElementById("internalInterviewer4_"+count).value!=''){
				 total=total+1;
			 }
		 }
		 if(document.getElementById("internalInterviewer5_"+count)!=null){
			 if(document.getElementById("internalInterviewer5_"+count).value!=''){
				 total=total+1;
			 }
		 }
		 if(document.getElementById("internalInterviewer6_"+count)!=null){
			 if(document.getElementById("internalInterviewer6_"+count).value!=''){
				 total=total+1;
			 }
		 }
		 if(document.getElementById("externalInterviewer1_"+count)!=null){
			 if(document.getElementById("externalInterviewer1_"+count).value!=''){
				 total=total+1;
			 }
		 }
		 if(document.getElementById("externalInterviewer2_"+count)!=null){
			 if(document.getElementById("externalInterviewer2_"+count).value!=''){
				 total=total+1;
			 }
		 }
		 if(document.getElementById("externalInterviewer3_"+count)!=null){
			 if(document.getElementById("externalInterviewer3_"+count).value!=''){
				 total=total+1;
			 }
		 }
		 if(document.getElementById("externalInterviewer4_"+count)!=null){
			 if(document.getElementById("externalInterviewer4_"+count).value!=''){
				 total=total+1;
			 }
		 }
		 if(document.getElementById("externalInterviewer5_"+count)!=null){
			 if(document.getElementById("externalInterviewer5_"+count).value!=''){
				 total=total+1;
			 }
		 }
		 if(document.getElementById("externalInterviewer6_"+count)!=null){
			 if(document.getElementById("externalInterviewer6_"+count).value!=''){
				 total=total+1;
			 }
		 }
		 
			 var avg=avg=count1/total;
			var dec=2;
			var result=Math.round(avg*Math.pow(10,dec))/Math.pow(10,dec);
			if(!isNaN(result)){
			document.getElementById("avg_"+count).value=result;
			}
	}
	
			
	function isNumberKey(evt) {
		
		var charCode = (evt.which) ? evt.which : event.keyCode;
				
		
		if(charCode == 8){
			
			return false;
		}
			
		if (charCode > 31 && (charCode > 48 || charCode < 57))
			
			return false;
		
		return true;
	}
	function CalAge() {

	    var now = new Date();
	   
	    var Dob=document.getElementById("dateOfBirth").value;
	   
	    bD = Dob.split('/');
	   	    if (bD.length == 3) {
	        	   born = new Date(bD[2], bD[1] * 1 - 1, bD[0]);
	                 years = Math.floor((now.getTime() - born.getTime()) / (365.25 * 

24 * 60 * 60 * 1000));
	              document.getElementById("age").value=years;
	              alert(years);
	      	    }
	}
	</script>
</head>
<body>
<html:form action="/newInterviewComments">
	<html:hidden property="method" styleId="method" value="printPassword" />
	<html:hidden property="formName" value="newInterviewCommentsForm" />
	<html:hidden property="pageType" value="3" />
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td colspan="6" height="10" class="row-white">
			<div align="center"><img
				src='<%=CMSConstants.LOGO_URL%>'
				alt="Logo not available"></div>
			<br></br>
			</td>
		</tr>
		<tr>
			<td valign="top">

			<table width="100%" cellspacing="1" cellpadding="1" border="0"
				style="height: 50%">
				<logic:notEmpty name="newInterviewCommentsForm"
					property="empOnlineResumeList">
					<nested:iterate name="newInterviewCommentsForm"
						property="empOnlineResumeList">
						<tr>
							<td align="left" height="1"><font size="2">Name </font></td> <td><font size="2">:</font></td>
							<td align="left" width="35%"><font size="2"><STRONG>
							<nested:write property="name" /></STRONG></font></td>
							<td align="left" height="1" ><font size="2">Gender </font></td>  <td><font size="2">:</font></td>
							<td align="left" width="20%"><font size="2"><STRONG>
							<nested:write property="gender" /></STRONG></font></td>	
							<td align="left" height="1" ><font size="2">Department </font></td>  <td><font size="2">:</font></td>
							<td align="left" width="40%" colspan="4"><font size="2"><STRONG>
							<nested:write property="department" /></STRONG></font></td>
							<td></td>
							<td></td>
							<td></td>
						</tr>
						<tr>
							<td align="left" height="1"><font size="2">Age </font></td>  <td><font size="2">:</font></td>
							<td align="left" width="25%"><font size="2"><STRONG>
							<nested:write property="age1" /></STRONG></font></td>
							<td align="left" height="1"><font size="2">DOB </font></td>  <td><font size="2">:</font></td>
							<td align="left" width="20%"><font size="2"><STRONG>
							<nested:write property="dateofBirth" /></STRONG></font></td>
							<td align="left" height="1"><font size="2">Appln&nbsp;No </font></td> <td><font size="2">:</font></td>
							<td align="left" width="20%"><font size="2"><STRONG>
							<nested:write property="applnNo" /></STRONG></font></td>
							<td align="left" height="1"><font size="2">Applied&nbsp;Date</font></td>
							<td align="left">:</td>
							<td><font size="2"> <strong>
							<nested:write property="appliedDate" /></strong>
					 		</font></td>
							
						</tr>
					</nested:iterate>
				</logic:notEmpty>
			</table>
			
			</td>
		</tr>
		
						
		
		<tr>
			<td><font size="2">
			Academic Performance:
			<table style='border: 1px solid #000000' rules='all' width="100%">

				<tr>
					<td align="left" width="25%">Degree</td>
					<td align="left" width="10%">Year of passing</td>
					<td align="left" width="15%">% of Marks/Grade</td>
					<td align="left" width="50%">University</td>
				</tr>
				<logic:notEmpty name="newInterviewCommentsForm"
					property="educationalDetailsList">
					<nested:iterate name="newInterviewCommentsForm"
						property="educationalDetailsList" id="educationalDetails">
						<tr>
							<td align="left" width="25%"><STRONG><nested:write
								property="degree" name="educationalDetails"></nested:write></STRONG></td>
							<td align="left" width="10%"><STRONG><nested:write
								property="yearOfPassing" name="educationalDetails" /></STRONG></td>
							<td align="left" width="15%"><STRONG><nested:write
								property="marks" name="educationalDetails" /></STRONG></td>
							<td align="left" width="50%"><STRONG><nested:write
								property="university" name="educationalDetails" /></STRONG></td>
						</tr>
					</nested:iterate>
				</logic:notEmpty>
			</table>
			</font>
			</td>
		</tr>
<tr>
		<td >
			<table>
				<logic:notEmpty name="newInterviewCommentsForm" property="empOnlineResumeList">
					<nested:iterate  id="id" name="newInterviewCommentsForm" property="empOnlineResumeList" type="com.kp.cms.to.employee.EmpOnlineResumeTO">
					<% if(id.getEligibilityTest()!=null && !id.getEligibilityTest().isEmpty()){ %>
					<tr>
					<td align="left" height="5"><font size="2">Eligibility Test &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</font></td> <td>:</td>
					<td><font size="2"> 
							<strong><nested:write property="eligibilityTest" /></strong>
					 </font></td>
				 </tr>
					<%} %>
						</nested:iterate>
						</logic:notEmpty>
						</table>
						</td>
		</tr>
		<tr>
			<td><font size="2">
			Grades awarded by interviewers(SSSC - 1):
			<table width="100%" style='border: 1px solid #000000' rules='all'>
				<tr>
					<%
						int internal = (Integer) session.getAttribute("Internal");
						int external = (Integer) session.getAttribute("External");
					%>
					<td align="center"><strong>Item</strong></td>
					<% if (internal != 0) { %>
					<td align="center" colspan='<%=internal%>'><strong>
					Internal Interviewers </strong><br />
					</td>
					<%} %>
					<% if (external != 0) { %>
					<td align="center" colspan='<%=external%>'><strong>
					External Interviewers </strong><br />
					</td>
					<%} %>
					<td align="center"><label><strong>Average</strong></label></td>
					<td align="center"><label><strong>Max</strong></label></td>
					<td align="center"><label><strong>Remarks</strong></label></td>
				</tr>
				  <tr>
					<td  align="center"></td>
					<% if (internal == 1) { %>
					<td  align="center">&nbsp;&nbsp;&nbsp;&nbsp;<span class="int1" style='text-align: right;'>1</span></td>
					<%} %>
					<% if (internal == 2) { %>
					<td  align="center">&nbsp;&nbsp;&nbsp;&nbsp;<span class="int1" style='text-align: right;'>1</span></td>
					<td  align="center">&nbsp;&nbsp;&nbsp;&nbsp;<span class="int2" style='text-align: right;'>2</span></td>
					<%} %>
					<% if (internal == 3) { %>
					<td  align="center">&nbsp;&nbsp;&nbsp;&nbsp;<span class="int1" style='text-align: right;'>1</span></td>
					<td  align="center">&nbsp;&nbsp;&nbsp;&nbsp;<span class="int2" style='text-align: right;'>2</span></td>
					<td  align="center">&nbsp;&nbsp;&nbsp;&nbsp;<span class="int3" style='text-align: right;'>3</span></td>
					<%} %>
					<% if (internal == 4) { %>
					<td  align="center">&nbsp;&nbsp;&nbsp;&nbsp;<span class="int1" style='text-align: right;'>1</span></td>
					<td  align="center">&nbsp;&nbsp;&nbsp;&nbsp;<span class="int2" style='text-align: right;'>2</span></td>
					<td  align="center">&nbsp;&nbsp;&nbsp;&nbsp;<span class="int3" style='text-align: right;'>3</span></td>
					<td  align="center">&nbsp;&nbsp;&nbsp;&nbsp;<span class="int7" style='text-align: right;'>4</span></td>
					<%} %>
					<% if (internal == 5) { %>
					<td  align="center">&nbsp;&nbsp;&nbsp;&nbsp;<span class="int1" style='text-align: right;'>1</span></td>
					<td  align="center">&nbsp;&nbsp;&nbsp;&nbsp;<span class="int2" style='text-align: right;'>2</span></td>
					<td  align="center">&nbsp;&nbsp;&nbsp;&nbsp;<span class="int3" style='text-align: right;'>3</span></td>
					<td  align="center">&nbsp;&nbsp;&nbsp;&nbsp;<span class="int7" style='text-align: right;'>4</span></td>
					<td  align="center">&nbsp;&nbsp;&nbsp;&nbsp;<span class="int8" style='text-align: right;'>5</span></td>
					<%} %>
					<% if (internal == 6) { %>
					<td  align="center">&nbsp;&nbsp;&nbsp;&nbsp;<span class="int1" style='text-align: right;'>1</span></td>
					<td  align="center">&nbsp;&nbsp;&nbsp;&nbsp;<span class="int2" style='text-align: right;'>2</span></td>
					<td  align="center">&nbsp;&nbsp;&nbsp;&nbsp;<span class="int3" style='text-align: right;'>3</span></td>
					<td  align="center">&nbsp;&nbsp;&nbsp;&nbsp;<span class="int7" style='text-align: right;'>4</span></td>
					<td  align="center">&nbsp;&nbsp;&nbsp;&nbsp;<span class="int8" style='text-align: right;'>5</span></td>
					<td  align="center">&nbsp;&nbsp;&nbsp;&nbsp;<span class="int9" style='text-align: right;'>6</span></td>
					<%} %>
					<% if (external == 1) { %>
					<td  align="center">&nbsp;&nbsp;&nbsp;&nbsp;<span class="int4" style='text-align: right;'>1</span></td>
					<%} %>
					<% if (external == 2) { %>
					<td  align="center">&nbsp;&nbsp;&nbsp;&nbsp;<span class="int4" style='text-align: right;'>1</span></td>
					<td  align="center">&nbsp;&nbsp;&nbsp;&nbsp;<span class="int5" style='text-align: right;'>2</span></td>
					<%} %>
					<% if (external == 3) { %>
					<td  align="center">&nbsp;&nbsp;&nbsp;&nbsp;<span class="int4" style='text-align: right;'>1</span></td>
					<td  align="center">&nbsp;&nbsp;&nbsp;&nbsp;<span class="int5" style='text-align: right;'>2</span></td>
					<td  align="center">&nbsp;&nbsp;&nbsp;&nbsp;<span class="int6" style='text-align: right;'>3</span></td>
					<%} %>
					<% if (external == 4) { %>
					<td  align="center">&nbsp;&nbsp;&nbsp;&nbsp;<span class="int4" style='text-align: right;'>1</span></td>
					<td  align="center">&nbsp;&nbsp;&nbsp;&nbsp;<span class="int5" style='text-align: right;'>2</span></td>
					<td  align="center">&nbsp;&nbsp;&nbsp;&nbsp;<span class="int6" style='text-align: right;'>3</span></td>
					<td  align="center">&nbsp;&nbsp;&nbsp;&nbsp;<span class="int10" style='text-align: right;'>4</span></td>
					<%} %>
					<% if (external == 5) { %>
					<td  align="center">&nbsp;&nbsp;&nbsp;&nbsp;<span class="int4" style='text-align: right;'>1</span></td>
					<td  align="center">&nbsp;&nbsp;&nbsp;&nbsp;<span class="int5" style='text-align: right;'>2</span></td>
					<td  align="center">&nbsp;&nbsp;&nbsp;&nbsp;<span class="int6" style='text-align: right;'>3</span></td>
					<td  align="center">&nbsp;&nbsp;&nbsp;&nbsp;<span class="int10" style='text-align: right;'>4</span></td>
					<td  align="center">&nbsp;&nbsp;&nbsp;&nbsp;<span class="int11" style='text-align: right;'>5</span></td>
					<%} %>
					<% if (external == 6) { %>
					<td  align="center">&nbsp;&nbsp;&nbsp;&nbsp;<span class="int4" style='text-align: right;'>1</span></td>
					<td  align="center">&nbsp;&nbsp;&nbsp;&nbsp;<span class="int5" style='text-align: right;'>2</span></td>
					<td  align="center">&nbsp;&nbsp;&nbsp;&nbsp;<span class="int6" style='text-align: right;'>3</span></td>
					<td  align="center">&nbsp;&nbsp;&nbsp;&nbsp;<span class="int10" style='text-align: right;'>4</span></td>
					<td  align="center">&nbsp;&nbsp;&nbsp;&nbsp;<span class="int11" style='text-align: right;'>5</span></td>
					<td  align="center">&nbsp;&nbsp;&nbsp;&nbsp;<span class="int12" style='text-align: right;'>6</span></td>
					<%} %>
					<td  align="center" style='text-align: right;'></td> 
					<td  align="center" style='text-align: right;'></td>
					<td  align="center" style='text-align: right;'></td>
				</tr> 
				<tr>
					<logic:notEmpty name="newInterviewCommentsForm"
						property="newInterviewCommentsDetailsList">
						<nested:iterate name="newInterviewCommentsForm"
							property="newInterviewCommentsDetailsList" indexId="count"
							id="list"
							type="com.kp.cms.to.employee.NewInterviewCommentsDetailsTo">
							<%
								String internalInterviewer1 = "internalInterviewer1_" + count;
								String internalInterviewer2 = "internalInterviewer2_" + count;
								String internalInterviewer3 = "internalInterviewer3_" + count;
								String internalInterviewer4 = "internalInterviewer4_" + count;
								String internalInterviewer5 = "internalInterviewer5_" + count;
								String internalInterviewer6 = "internalInterviewer6_" + count;
								String externalInterviewer1 = "externalInterviewer1_" + count;
								String externalInterviewer2 = "externalInterviewer2_" + count;
								String externalInterviewer3 = "externalInterviewer3_" + count;
								String externalInterviewer4 = "externalInterviewer4_" + count;
								String externalInterviewer5 = "externalInterviewer5_" + count;
								String externalInterviewer6 = "externalInterviewer6_" + count;
								String avgMethod = "calc(" + count + ")";
								String avgId = "avg_" + count;
							%>
							<tr>
								<td align="left"><FONT size="2"><nested:write
									property="ratingFactor" /></FONT></td>
								<logic:equal value="true" name="newInterviewCommentsForm" property="isInternal1">
									<td align="center"> <b><nested:text
										property="internalInterviewer1" style='font-size: 0.875em;font-weight: bold;text-align: right;'
										size="1" styleClass="txt1" styleId='<%=internalInterviewer1 %>'
										onkeypress="return isNumberKey (event)"
										onchange="<%=avgMethod %>" readonly="true"></nested:text></b> </td>
								</logic:equal>
								<logic:equal value="true" name="newInterviewCommentsForm"
									property="isInternal2">
									<td align="center"><b> <nested:text  
										property="internalInterviewer2" style='font-size: 0.875em;font-weight: bold;text-align: right;'
										size="1" styleClass="txt2"
										styleId='<%=internalInterviewer2 %>'
										onkeypress="return isNumberKey (event)"
										onchange="<%=avgMethod %>" readonly="true"></nested:text> </b></td>
								</logic:equal>
								<logic:equal value="true" name="newInterviewCommentsForm"
									property="isInternal3">
									<td align="center"><b><nested:text
										property="internalInterviewer3" style='font-size: 0.875em;font-weight: bold;text-align: right;'
										size="1" styleClass="txt3"
										styleId='<%=internalInterviewer3 %>'
										onkeypress="return isNumberKey (event)"
										onchange="<%=avgMethod %>" readonly="true"></nested:text> </b></td>
								</logic:equal>
								<logic:equal value="true" name="newInterviewCommentsForm"
									property="isInternal4">
									<td align="center"><b> <nested:text
										property="internalInterviewer4" style='font-size: 0.875em;font-weight: bold;text-align: right;'
										size="1" styleClass="txt4"
										styleId='<%=internalInterviewer4 %>'
										onkeypress="return isNumberKey (event)"
										onchange="<%=avgMethod %>" readonly="true"></nested:text> </b></td>
								</logic:equal>
								<logic:equal value="true" name="newInterviewCommentsForm"
									property="isInternal5">
									<td align="center"><b> <nested:text
										property="internalInterviewer5" style='font-size: 0.875em;font-weight: bold;text-align: right;'
										size="1" styleClass="txt5"
										styleId='<%=internalInterviewer5 %>'
										onkeypress="return isNumberKey (event)"
										onchange="<%=avgMethod %>" readonly="true"></nested:text> </b></td>
								</logic:equal>
								<logic:equal value="true" name="newInterviewCommentsForm"
									property="isInternal6">
									<td align="center"><b><nested:text
										property="internalInterviewer6" style='font-size: 0.875em;font-weight: bold;text-align: right;'
										size="1" styleClass="txt6"
										styleId='<%=internalInterviewer6 %>'
										onkeypress="return isNumberKey (event)"
										onchange="<%=avgMethod %>" readonly="true"></nested:text> </b></td>
								</logic:equal>
								<logic:equal value="true" name="newInterviewCommentsForm"
									property="isExternal1">
									<td align="center"><b> <nested:text
										property="externalInterviewer1" style='font-size: 0.875em;font-weight: bold;text-align: right;'
										size="1" styleClass="txt7"
										styleId='<%=externalInterviewer1 %>'
										onkeypress="return isNumberKey (event)"
										onchange="<%=avgMethod %>" readonly="true"></nested:text> </b></td>
								</logic:equal>
								<logic:equal value="true" name="newInterviewCommentsForm"
									property="isExternal2">
									<td align="center"><b> <nested:text
										property="externalInterviewer2" style='font-size: 0.875em;font-weight: bold;text-align: right;'
										size="1" styleClass="txt8"
										styleId='<%=externalInterviewer2 %>'
										onkeypress="return isNumberKey (event)"
										onchange="<%=avgMethod %>" readonly="true"></nested:text> </b></td>
								</logic:equal>
								<logic:equal value="true" name="newInterviewCommentsForm"
									property="isExternal3">
									<td align="center"><b> <nested:text
										property="externalInterviewer3" style='font-size: 0.875em;font-weight: bold;text-align: right;'
										size="1" styleClass="txt9"
										styleId='<%=externalInterviewer3 %>'
										onkeypress="return isNumberKey (event)"
										onchange="<%=avgMethod %>" readonly="true"></nested:text> </b></td>
								</logic:equal>
								<logic:equal value="true" name="newInterviewCommentsForm"
									property="isExternal4">
									<td align="center"><b> <nested:text
										property="externalInterviewer4" style='font-size: 0.875em;font-weight: bold;text-align: right;'
										size="1" styleClass="txt10"
										styleId='<%=externalInterviewer4 %>' onkeypress="return isNumberKey (event)"
										onchange="<%=avgMethod %>" readonly="true"></nested:text> </b></td>
								</logic:equal>
								<logic:equal value="true" name="newInterviewCommentsForm"
									property="isExternal5">
									<td align="center"><b> <nested:text
										property="externalInterviewer5" style='font-size: 0.875em;font-weight: bold;text-align: right;'
										size="1" styleClass="txt11"
										styleId='<%=externalInterviewer5 %>' onkeypress="return isNumberKey (event)"
										onchange="<%=avgMethod %>" readonly="true"></nested:text> </b></td>
								</logic:equal>
								<logic:equal value="true" name="newInterviewCommentsForm"
									property="isExternal6">
									<td align="center"><b><nested:text
										property="externalInterviewer6" style='font-size: 0.875em;font-weight: bold;text-align: right;'
										size="1" styleClass="txt12"
										styleId='<%=externalInterviewer6 %>' onkeypress="return isNumberKey (event)"
										onchange="<%=avgMethod %>" readonly="true"></nested:text> </b></td>
								</logic:equal>
								<td align="center"><input type="text" id="<%=avgId%>"
									value="0" class="txt13" size="1" style='font-size: 0.875em;font-weight: bold;text-align: right;' />
								<script type="text/javascript">
      								calc(<%=count%>);
      							</script>&nbsp;&nbsp;</td>
								<td align="center"><nested:text property="maxScore"
									style='font-size: 0.875em;text-align: right;' size="1" styleClass="txt14" /></td>
								<td align="center" style='font- size: 0.875em;'></td>
							</tr>
						</nested:iterate>
					</logic:notEmpty>
				</tr>
				<tr>
					<td align="center"><b>Total</b></td>
					<% if (internal == 1) {
					%>
					<td id="summation" align="center">&nbsp;&nbsp;&nbsp;&nbsp;<b><span id="sum1" style='text-align: right;'></span></b></td>
					<% } %>
					<% if (internal == 2) {
					%>
					<td id="summation" align="center">&nbsp;&nbsp;&nbsp;&nbsp;<b><span id="sum1" style='text-align: right;'></span></b></td>
					<td id="summation" align="center">&nbsp;&nbsp;&nbsp;&nbsp;<b><span id="sum2" style='text-align: right;'></span></b></td>
					<% } %>
					<% if (internal == 3) {
					%>
					<td id="summation" align="center">&nbsp;&nbsp;&nbsp;&nbsp;<b><span id="sum1" style='text-align: right;'></span></b></td>
					<td id="summation" align="center">&nbsp;&nbsp;&nbsp;&nbsp;<b><span id="sum2" style='text-align: right;'></span></b></td>
					<td id="summation" align="center">&nbsp;&nbsp;&nbsp;&nbsp;<b><span id="sum3" style='text-align: right;'></span></b></td>
					<% } %>
					<% if (internal == 4) {
					%>
					<td id="summation" align="center">&nbsp;&nbsp;&nbsp;&nbsp;<b><span id="sum1" style='text-align: right;'></span></b></td>
					<td id="summation" align="center">&nbsp;&nbsp;&nbsp;&nbsp;<b><span id="sum2" style='text-align: right;'></span></b></td>
					<td id="summation" align="center">&nbsp;&nbsp;&nbsp;&nbsp;<b><span id="sum3" style='text-align: right;'></span></b></td>
					<td id="summation" align="center">&nbsp;&nbsp;&nbsp;&nbsp;<b><span id="sum4" style='text-align: right;'></span></b></td>
					<% } %>
					<% if (internal == 5) {
					%>
					<td id="summation" align="center">&nbsp;&nbsp;&nbsp;&nbsp;<b><span id="sum1" style='text-align: right;'></span></b></td>
					<td id="summation" align="center">&nbsp;&nbsp;&nbsp;&nbsp;<b><span id="sum2" style='text-align: right;'></span></b></td>
					<td id="summation" align="center">&nbsp;&nbsp;&nbsp;&nbsp;<b><span id="sum3" style='text-align: right;'></span></b></td>
					<td id="summation" align="center">&nbsp;&nbsp;&nbsp;&nbsp;<b><span id="sum4" style='text-align: right;'></span></b></td>
					<td id="summation" align="center">&nbsp;&nbsp;&nbsp;&nbsp;<b><span id="sum5" style='text-align: right;'></span></b></td>
					<% } %>
					<% if (internal == 6) {
					%>
					<td id="summation" align="center">&nbsp;&nbsp;&nbsp;&nbsp;<b><span id="sum1" style='text-align: right;'></span></b></td>
					<td id="summation" align="center">&nbsp;&nbsp;&nbsp;&nbsp;<b><span id="sum2" style='text-align: right;'></span></b></td>
					<td id="summation" align="center">&nbsp;&nbsp;&nbsp;&nbsp;<b><span id="sum3" style='text-align: right;'></span></b></td>
					<td id="summation" align="center">&nbsp;&nbsp;&nbsp;&nbsp;<b><span id="sum4" style='text-align: right;'></span></b></td>
					<td id="summation" align="center">&nbsp;&nbsp;&nbsp;&nbsp;<b><span id="sum5" style='text-align: right;'></span></b></td>
					<td id="summation" align="center">&nbsp;&nbsp;&nbsp;&nbsp;<b><span id="sum6" style='text-align: right;'></span></b></td>
					<% } %>
					<% if (external == 1) { %>
					<td id="summation" align="center">&nbsp;&nbsp;&nbsp;&nbsp;<b><span id="sum7" style='text-align: right;'></span></b></td>
					<% } %>
					<% if (external == 2) { %>
					<td id="summation" align="center">&nbsp;&nbsp;&nbsp;&nbsp;<b><span id="sum7" style='text-align: right;'></span></b></td>
					<td id="summation" align="center">&nbsp;&nbsp;&nbsp;&nbsp;<b><span id="sum8" style='text-align: right;'></span></b></td>
					<% } %>
					<% if (external == 3) { %>
					<td id="summation" align="center">&nbsp;&nbsp;&nbsp;&nbsp;<b><span id="sum7" style='text-align: right;'></span></b></td>
					<td id="summation" align="center">&nbsp;&nbsp;&nbsp;&nbsp;<b><span id="sum8" style='text-align: right;'></span></b></td>
					<td id="summation" align="center">&nbsp;&nbsp;&nbsp;&nbsp;<b><span id="sum9" style='text-align: right;'></span></b></td>
					<% } %>
					 <% if (external == 4) { %>
					<td id="summation" align="center">&nbsp;&nbsp;&nbsp;&nbsp;<b><span id="sum7" style='text-align: right;'></span></b></td>
					<td id="summation" align="center">&nbsp;&nbsp;&nbsp;&nbsp;<b><span id="sum8" style='text-align: right;'></span></b></td>
					<td id="summation" align="center">&nbsp;&nbsp;&nbsp;&nbsp;<b><span id="sum9" style='text-align: right;'></span></b></td>
					<td id="summation" align="center">&nbsp;&nbsp;&nbsp;&nbsp;<b><span id="sum10" style='text-align: right;'></span></b></td>
					<% } %>
					<% if (external == 5) { %>
					<td id="summation" align="center">&nbsp;&nbsp;&nbsp;&nbsp;<b><span id="sum7" style='text-align: right;'></span></b></td>
					<td id="summation" align="center">&nbsp;&nbsp;&nbsp;&nbsp;<b><span id="sum8" style='text-align: right;'></span></b></td>
					<td id="summation" align="center">&nbsp;&nbsp;&nbsp;&nbsp;<b><span id="sum9" style='text-align: right;'></span></b></td>
					<td id="summation" align="center">&nbsp;&nbsp;&nbsp;&nbsp;<b><span id="sum10" style='text-align: right;'></span></b></td>
					<td id="summation" align="center">&nbsp;&nbsp;&nbsp;&nbsp;<b><span id="sum11" style='text-align: right;'></span></b></td>
					<% } %>
					<% if (external == 6) { %>
					<td id="summation" align="center">&nbsp;&nbsp;&nbsp;&nbsp;<b><span id="sum7" style='text-align: right;'></span></b></td>
					<td id="summation" align="center">&nbsp;&nbsp;&nbsp;&nbsp;<b><span id="sum8" style='text-align: right;'></span></b></td>
					<td id="summation" align="center">&nbsp;&nbsp;&nbsp;&nbsp;<b><span id="sum9" style='text-align: right;'></span></b></td>
					<td id="summation" align="center">&nbsp;&nbsp;&nbsp;&nbsp;<b><span id="sum10" style='text-align: right;'></span></b></td>
					<td id="summation" align="center">&nbsp;&nbsp;&nbsp;&nbsp;<b><span id="sum11" style='text-align: right;'></span></b></td>
					<td id="summation" align="center">&nbsp;&nbsp;&nbsp;&nbsp;<b><span id="sum12" style='text-align: right;'></span></b></td>
					<% } %>
					<td id="summation" align="center">&nbsp;&nbsp;<b><span id="sum13" style='text-align: right;'></span></b></td>
					<td id="summation" align="center">&nbsp;&nbsp;<span id="sum14" style='text-align: right;'></span></td>
					<td></td>
				</tr>
			</table>
			</font></td>
		</tr>
		<tr>
			<td>
			<table>
				<tr>
					<td align="left" height="5"><font size="2">Joining time
					required , if selected</font></td>
					<td>:</td>
					<td><font size="2"> <logic:notEmpty
						name="newInterviewCommentsForm"
						property="newInterviewCommentsList">
						<nested:iterate name="newInterviewCommentsForm"
							property="newInterviewCommentsList">
							<strong><nested:write property="joiningTime" /></strong>
						</nested:iterate>
					</logic:notEmpty> </font></td>
				 </tr>
				<tr>
					<td align="left" height="5"><font size="2">Expected
					Salary Per Month</font></td>
					<td height="5">:</td>
					<td ><font size="2"> <logic:notEmpty
						name="newInterviewCommentsForm" property="empOnlineResumeList">
						<nested:iterate name="newInterviewCommentsForm"
							property="empOnlineResumeList"><div align="justify">
							<strong><img src="images/rupee.jpg" height="16px" width="12px" align="top" style="align:center;">&nbsp;<nested:write
								property="expectedSalaryPerMonth"  />/-</strong></div>
						</nested:iterate>
					</logic:notEmpty> </font></td>
				 </tr>
				<tr>
					<td align="left" ><font size="2">Comments
					</font></td>
					<td height="5">:</td>
					<td><font size="2"> <logic:notEmpty
						name="newInterviewCommentsForm" property="newInterviewCommentsList">
						<nested:iterate name="newInterviewCommentsForm"
							property="newInterviewCommentsList">
							<strong> <nested:write
								property="comments" /></strong>
						</nested:iterate>
					</logic:notEmpty> </font></td>
				</tr>
			</table>
			<hr></hr>
			</td>
		</tr>
		<tr>
			<logic:notEmpty name="newInterviewCommentsForm"
				property="newInterviewCommentsList">
				<nested:iterate name="newInterviewCommentsForm"
					property="newInterviewCommentsList" id="id"
					type="com.kp.cms.to.employee.NewInterviewCommentsTO">
					<td><font size="2">
					<table width="100%" cellspacing="1" cellpadding="2" border="0"
						style="height: 100%">
						<tr>
							<td>Name of the interviewers(Internal):</td>
							<td>Name of the interviewers(External):</td>
						</tr>
						<tr>
							<% if (id.getNameOfInternalInterviewer1() != null && ! id.getNameOfInternalInterviewer1().isEmpty()) { %>
							<td>1.<STRONG><nested:write property="nameOfInternalInterviewer1" /></STRONG></td>
							<% } else { %>
							<td></td><%} %>
							<% if (id.getNameOfExternalInterviewer1() != null && !id.getNameOfExternalInterviewer1().isEmpty()) { %>
							<td>1.<STRONG><nested:write property="nameOfExternalInterviewer1" /></STRONG></td>
							<% } %>
						</tr>
						<tr>
							<% if (id.getNameOfInternalInterviewer2() != null && ! id.getNameOfInternalInterviewer2().isEmpty()) { %>
							<td>2.<STRONG><nested:write property="nameOfInternalInterviewer2" /></STRONG></td>
							<% } else { %>
							<td></td><%} %>
							<% if (id.getNameOfExternalInterviewer2() != null && ! id.getNameOfExternalInterviewer2().isEmpty()) { %>
							<td>2.<STRONG><nested:write
								property="nameOfExternalInterviewer2" /></STRONG></td>
							<% } %>
						</tr>
						<tr>
							<% if (id.getNameOfInternalInterviewer3() != null && ! id.getNameOfInternalInterviewer3().isEmpty()) { %>
							<td>3.<STRONG><nested:write property="nameOfInternalInterviewer3" /></STRONG></td>
							<% } else { %>
							<td></td><%} %>
							<% if (id.getNameOfExternalInterviewer3() != null && !id.getNameOfExternalInterviewer3().isEmpty()) { %>
							<td>3.<STRONG><nested:write property="nameOfExternalInterviewer3" /></STRONG></td>
							<% } %>
						</tr>
						<tr>
							<% if (id.getNameOfInternalInterviewer4() != null && ! id.getNameOfInternalInterviewer4().isEmpty()) { %>
							<td>4.<STRONG><nested:write property="nameOfInternalInterviewer4" /></STRONG></td> 
							<% } else { %>
							<td></td><%} %>
							<% if (id.getNameOfExternalInterviewer4() != null && !id.getNameOfExternalInterviewer4().isEmpty()) { %>
							<td>4.<STRONG><nested:write property="nameOfExternalInterviewer4" /></STRONG></td>
							<% } %>
						</tr>
						<tr>
							<% if (id.getNameOfInternalInterviewer5() != null && ! id.getNameOfInternalInterviewer5().isEmpty()) { %>
							<td>5.<STRONG><nested:write property="nameOfInternalInterviewer5" /></STRONG></td>
							<% } else { %>
							<td></td><%} %>
							<% if (id.getNameOfExternalInterviewer5() != null && !id.getNameOfExternalInterviewer5().isEmpty()) { %>
							<td>5.<STRONG><nested:write property="nameOfExternalInterviewer5" /></STRONG></td>
							<% } %>
						</tr>
						<tr>
							<% if (id.getNameOfInternalInterviewer6() != null && ! id.getNameOfInternalInterviewer6().isEmpty()) { %>
							<td>6.<STRONG><nested:write property="nameOfInternalInterviewer6" /></STRONG></td>
							<% } else { %>
							<td></td><%} %>
							<% if (id.getNameOfExternalInterviewer6() != null && !id.getNameOfExternalInterviewer6().isEmpty()) { %>
							<td>6.<STRONG><nested:write property="nameOfExternalInterviewer6" /></STRONG></td>
							<% } %>
						</tr>
					</table>
					</font>
					<p></p></td>
				</nested:iterate>
			</logic:notEmpty>

			<logic:empty name="newInterviewCommentsForm"
				property="newInterviewCommentsList">
				<td>
				<table width="100%" cellspacing="1" cellpadding="2" border="0"
					style="height: 100%">

					<tr>
						<td><font size="2">Name of the interviewers (Internal):</font></td>
						<td><font size="2">Name of the interviewers (External):</font></td>
					</tr>
					<tr>
						<td><font size="2">1.</font></td>
						<td><font size="2">1.</font></td>
						
					</tr>

				</table>
				
				</td>
			</logic:empty>

		</tr>
		<tr>
			<td>
			<font size="2">SSSC - 2</font>

			<table style='border: 1px solid #000000' rules="cols" width="100%">
				<tr>
				<logic:equal value="true" property="isTeachingStaff" name="newInterviewCommentsForm">
					<td width="50%"><font size="2">&nbsp;&nbsp;Dean:</font></td>
				</logic:equal>
				<logic:equal value="false" property="isTeachingStaff" name="newInterviewCommentsForm">
					<td width="50%"><font size="2">&nbsp;&nbsp;Director:</font></td>
					</logic:equal>
				<td width="50%"><font size="2">&nbsp;&nbsp;Personnel Officer:</font></td>
				</tr>
				<tr>
					<td><br></br>
					</td>
				</tr>
				<!--<tr>
					<td><br></br>
					</td>
				</tr>
				
				--><logic:equal value="true" property="isTeachingStaff" name="newInterviewCommentsForm">
				<tr>
					
				
					<td width="50%" height="25"><font size="2">&nbsp;&nbsp;Deanery Representative:</font></td>
					<td height="25"><font size="2">&nbsp;&nbsp;Designation:........................................................................................</font></td></tr>
					<tr>
					<td></td>
					<td height="25"><font size="2">&nbsp;&nbsp;Scale of Pay:........................................................................................</font></td></tr>
					<tr>
					<td></td>
					<td height="25"><font size="2">&nbsp;&nbsp;Basic:........................................Gross:................................................</font></td></tr>
					<tr>
					<td></td>
					<td height="25"><font size="2">&nbsp;&nbsp;Total Experience:.................... Recognized Experience:.....................</font></td></tr>
					<tr><td height="30"></td></tr>
					<tr>
					<td width="50%"><font size="2">&nbsp;&nbsp;VC's Nominee:</font></td>
					<td width="50%"><font size="2">&nbsp;&nbsp;Approval by Vice Chancellor:</font></td>
					
					</tr>
					</logic:equal>
					<logic:equal value="false" property="isTeachingStaff" name="newInterviewCommentsForm">
				<tr>
					
				
					<td width="50%" height="25"></td>
					<td height="25"><font size="2">&nbsp;&nbsp;Designation:........................................................................................</font></td></tr>
					<tr>
					<td></td>
					<td height="25"><font size="2">&nbsp;&nbsp;Scale of Pay:........................................................................................</font></td></tr>
					<tr>
					<td width="50%"><font size="2">&nbsp;&nbsp;CFO:</font></td>
					<td height="25"><font size="2">&nbsp;&nbsp;Basic:........................................Gross:................................................</font></td></tr>
					<tr>
					<td></td>
					<td height="25"><font size="2">&nbsp;&nbsp;Total Experience:.................... Recognized Experience:.....................</font></td></tr>
					<tr><td height="30"></td></tr>
					<tr>
					<td></td>
					<td width="50%"><font size="2">&nbsp;&nbsp;Approval by Vice Chancellor:</font></td>
					
					</tr>
					</logic:equal>
				<tr>
					<td><br></br>
					</td>
				</tr>
			</table>
			</td>
		</tr>
		<!--<tr>
			<td>
			<font size="2">SSSC - 3</font>

			<table style='border: 1px solid #000000' rules="cols" width="100%">
				<tr>
					<td width="50%"><font size="2">&nbsp;&nbsp;Recommended /not recommended:</font></td>
					<td width="50%"><font size="2">&nbsp;&nbsp;Approval by Vice Chancellor:</font></td>
				</tr>
				<tr>
					<td height="25"><font size="2">&nbsp;&nbsp;1. .................................................................................</font></td>
				</tr>
				<tr>
					<td height="25"><font size="2">&nbsp;&nbsp;2. .................................................................................</font></td>
				</tr>
				<tr></tr>
				<tr></tr>
			</table>
			</td>
		</tr>
	--></table>
</html:form>
<script type="text/javascript">printPass();</script>

</body>
</html>
