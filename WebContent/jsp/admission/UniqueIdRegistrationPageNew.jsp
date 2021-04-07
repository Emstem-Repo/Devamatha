<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
 
<!-- for cache controling with html code-->
<meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />
 
 <!-- for cache controling with jsp code-->
<% 
response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
response.setHeader("Expires", "0"); // Proxies.
%>



<%--
<LINK REL=StyleSheet HREF= "css/admission/AdmissionResponsiveTabs.css" TYPE="text/css">
<link rel="stylesheet" href="css/admission/OnlineApplicationFormNew.css"/>
--%>


<link rel="stylesheet" href="css/admission/css/stylesheet-pure-css.css"/>  

<script src="jquery/development-bundle/jquery-1.7.1.js"></script>
<script src="js/AC_RunActiveContent.js" type="text/javascript"></script>
<link type="text/css" href="jquery/css/ui-lightness/jquery-ui-1.8.18.custom.css" rel="stylesheet" />
<script src="jquery/js/jquery-ui-1.10.0.custom.min.js" type="text/javascript"></script>
<script type="text/javascript" src="js/ajax/Ajax.js"></script>
<script type="text/javascript" src="js/ajax/AjaxUtil.js"></script>
<script type='text/javascript' src="jquery/Zebra/javascript/zebra_dialog.js"></script>
<link rel="stylesheet" href="jquery/Zebra/css/default/zebra_dialog.css"  />
<link rel="stylesheet" type="text/css" href="jquery.confirm/jquery.confirm/jquery.confirm.css" />
<script src="jquery.confirm/jquery.confirm/jquery.confirm.js"></script>
<style type="text/css">
	.ui-datepicker {
        font-family:Garamond;
        font-size: 14px;
        margin-left:10px
     }
    
</style>

 
<style type="text/css">
	
		.tooltip{
   			display: inline;
    		position: relative;
		}
		
		.tooltip:hover:after{
    		background: #333;
    		background: rgba(0,0,0,.8);
    		border-radius: 5px;
    		bottom: 26px;
    		color: #fff;
    		content: attr(title);
    		left: 20%;
    		padding: 5px 15px;
    		position: absolute;
    		z-index: 98;
    		width: 220px;
		}
		
		.tooltip:hover:before{
    		border: solid;
    		border-color: #333 transparent;
    		border-width: 6px 6px 0 6px;
    		bottom: 20px;
    		content: "";
    		left: 50%;
    		position: absolute;
    		z-index: 99;
		}
	
	
	.text-label{
	color:#cdcdcd;
	font-weight:bold;
	}
	
	</style>
	
 <style type="text/css">
 input[type="radio"] {
    -webkit-appearance: checkbox;
    -moz-appearance: checkbox;
    -ms-appearance: checkbox;     /* not currently supported */
    -o-appearance: checkbox;      /* not currently supported */
     transform:scale(1.3, 1.3);
     color: white;
     background: white; 
     background-color: white;
  
}
</style>

<script type="text/javascript">

function getMobileNo(){
	var residentId = document.getElementById("residentCategory").value;
	var nativeCountry = document.getElementById("nativeCountry").value;
	var  nativeCountrys = nativeCountry.split(",");
	var indian=false;
	for ( var i = 0; i < nativeCountrys.length; i++) {
		if(nativeCountrys[i]==residentId){
			indian = true;
		}
	}
	if(indian){
		document.getElementById("mobilecode").readOnly = true;
		document.getElementById("mobilecode").value="91";
	}else{
		document.getElementById("mobilecode").value="";
		document.getElementById("mobilecode").readOnly = false;
	}			
}

 function IsAlpha(e) {
	 var inputValue = e.which;
	 if(!(inputValue >= 65 && inputValue <= 120) && (inputValue != 32 && inputValue != 0) && (inputValue != 8 && inputValue != 0) && (inputValue != 127 && inputValue != 0)) {
		  e.preventDefault(); 
		  }
}

 function setCommunity(val) {
	 document.getElementById("comuno").value=val;
	
}
 function shoCommunity() {
	var ptype=document.getElementById("programTypeId").value;
	console.log(ptype);
	if (ptype=="1") {
		document.getElementById("comunlBlock").style.display="none";
		document.getElementById("comunBlock").style.display="none";
	}
	if (ptype=="2") {
		document.getElementById("comunlBlock").style.display="none";
		document.getElementById("comunBlock").style.display="none";
	}
}
</script>

<title>Online Application Form</title>
</head>


<html:form action="/uniqueIdRegistration" method="post" styleId="register">
<html:hidden property="method" styleId="method" value="" />
	<html:hidden property="formName" value="uniqueIdRegistrationForm" />
	<html:hidden property="mode" name="uniqueIdRegistrationForm" styleId="mode" />
	<html:hidden property="pageType" name="uniqueIdRegistrationForm" styleId="pageType" value="1" />
	<html:hidden property="offlinePage" styleId="offlinePage" name="uniqueIdRegistrationForm"/>
	<html:hidden property="serverDownMessage" styleId="serverDownMessage" name="uniqueIdRegistrationForm" />
	<html:hidden property="nativeCountry" styleId="nativeCountry" name="uniqueIdRegistrationForm"/>
	<html:hidden property="isCommuQuota" value="No" styleId="comuno"></html:hidden>
	
<body >	

	<table width="80%" style="background-color: #F0F8FF" align="center">

	<tr >
	 <td height="20px">
	
	 <!-- errors display -->
    <div align="center" class="subheading">
		<div id="errorMessage" align="center">
			<FONT color="red"><html:errors /></FONT>
		</div>
	<div id="errorMessage1" style="font-size: 11px; color: red"></div>
	</div>
	
	</td>
	</tr>
	
   <tr><td align="center" class="subheading">REGISTRATION DETAILS</td></tr>
   <tr > <td height="20px"></td></tr>
  
      <tr>
        <td>
        <table width="100%" border="0" cellpadding="3" height="50%" align="center" class="subtable1 w"  >
         
         
		 
          <tr>
            <td width="55%" align="right" height="30%"><span class="Mandatory">*</span><bean:message key="knowledgepro.applicationform.candidateName"/> </td>
            <td >
             <html:text  property="applicantName" styleClass="textbox" styleId="applicantName" size="30" maxlength="90" name="uniqueIdRegistrationForm" errorStyleClass="error" onkeypress="IsAlphaDot(event)"></html:text>
          <a href="#" title="Enter name based on 10th class records" class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
        	
            </td>
          </tr>
          
           <tr>
            <td align="right" height="50%"> <span class="Mandatory">*</span>
				<bean:message key="admissionForm.studentinfo.dob.label"/>
				<bean:message key="admissionForm.application.dateformat.label"/>
                <bean:message key="knowledgepro.applicationform.dob.format"/>:
          </td>
            <td height="50%">
             <html:text  property="registerDateOfBirth" styleClass="textbox"  name="uniqueIdRegistrationForm" styleId="registerDateOfBirth" 
		 				 size="10" maxlength="10" />
						 <script language="JavaScript">
										$(function(){
											//var dateRange1=document.getElementById("dateRange").value;
											var d = new Date();
											var year = d.getFullYear() - 15 ;
											  $("#registerDateOfBirth").datepicker({dateFormat:"dd/mm/yy",
												changeMonth: true,
												changeYear: true,
												    yearRange: '1940:' + year, 
												   defaultDate: new Date(year, 0, 1),
												//yearRange: dateRange1,
												reverseYearRange: true
												  });
											  
											});
						</script>
    
            <a href="#" title="select date of birth based on 10th class records" class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
        	
            </td>
          </tr>
          
            <tr>
            <td align="right" height="50%"><span class="Mandatory">*</span><bean:message key="admissionForm.studentinfo.sex.label"/></td>
            
            <td >
           
            <fieldset style="border: 0px">
            
             <html:radio  property="gender" styleId="MALE"  value="MALE">
             </html:radio>
             <label for="MALE"><span><span></span></span>MALE</label> 
			
			
			
			 <html:radio property="gender"  styleId="FEMALE" value="FEMALE">
			 
			 </html:radio>
			 <label for="FEMALE"><span><span></span></span>FEMALE</label> 
			 <a href="#" title="select gender" class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
        	
			</fieldset>	
			<!--<fieldset style="border: 0px">	
			<html:radio property="gender"  styleId="Trans Gender" value="Trans Gender">
			 
			 </html:radio>
			 <label for="Trans Gender"><span><span></span></span>TRANSGENDER</label> 
			
			 </fieldset>	
            --></td>
          </tr>
		  <tr>
            <td align="right" height="30%"> <span class="Mandatory">*</span>
            <bean:message key="admissionForm.studentinfo.residentcatg.label2"/>
            </td>
            <td >
             <html:select property="residentCategoryId" name="uniqueIdRegistrationForm" styleClass="dropdown" styleId="residentCategory"  errorStyleClass="error" onchange="getMobileNo();">
			<option value=""><bean:message key="knowledgepro.admin.select"/></option>
			<html:optionsCollection name="uniqueIdRegistrationForm" property="residentTypes" label="name" value="id"/>
			</html:select>
            <a href="#" title="select residential category" class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
        	
            </td>
          </tr>
           <tr>
            <td align="right" width="50%" height="30%"><span class="Mandatory">*</span><bean:message key="knowledgepro.admission.programtype"/>:
            </td>
            <td width="50%">
             <html:select styleId="programTypeId" onchange="shoCommunity()"  property="programTypeId" name="uniqueIdRegistrationForm" styleClass="dropdown"   errorStyleClass="error"  >
									<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
									
									<cms:programTypesOnlineOpen></cms:programTypesOnlineOpen>
			</html:select>
           <a href="#" title="For BCom,BSc,BA,BBA,BCA Programs Select UG ProgramType and For MCom,MSc,MA,MBA,MCA Programs Select PG ProgramType" class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
        		
            </td>
            
		 </tr>
		 
			  
            
		   <tr>
            <td align="right" height="50%">
            <span class="Mandatory">*</span>
				<bean:message key="admissionForm.studentinfo.email.label"/>
				<br/>(e.g. name@yahoo.com)
        	 </td>
            <td >
             <html:text  property="emailId" name="uniqueIdRegistrationForm" styleClass="textbox" styleId="email" size="30" onblur="checkMail(this.value)" onchange="checkMail(this.value)"/>
            <a href="#" title="enter mail id" class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
        	
            </td>
            
          </tr>
          
          <tr>
            <td align="right" height="50%">
            <span class="Mandatory">*</span>
				<bean:message key="admissionForm.studentinfo.confirmemail.label"/>
				<br/>(e.g. name@yahoo.com)
        	 </td>
            <td >
             <html:text  property="confirmEmailId" name="uniqueIdRegistrationForm" styleClass="textbox" styleId="confirmEmailId" size="30" onblur="checkMail(this.value)" onchange="checkMail(this.value)"  onmousedown="noCopyMouse(event)" onkeydown= "noCopyKey(event)" onkeyup="noCopyKey(event)"/>
         	  <a href="#" title="enter confirmmail id, mail and confirmmail should same." class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
        	
            </td>
          </tr>
          
           
		  <tr>
            <td align="right" height="30%"><span class="Mandatory">*</span>Country Mobile Code &amp; Mobile No:
            <br/>(e.g. India code is 91)
             </td>
            <td >
            <html:text  readonly="false" property="mobileCode" styleClass="textboxsmall" styleId="mobilecode" size="30" name="uniqueIdRegistrationForm" onkeypress="return isNumberKey(event)" maxlength="5"/>
            <script type="text/javascript">
				var inputBox = document.getElementById('mobilecode');	/* to avoid occurance of non numeric entries while pasting*/
				inputBox.onchange = function(){
				    inputBox.value = inputBox.value.replace(/[^0-9]/g, '');
				};
			</script>
             &nbsp;
             <html:text  property="mobileNo" styleClass="textboxmedium" styleId="mobile" size="30" name="uniqueIdRegistrationForm" onkeypress="return isNumberKey(event)" maxlength="10"/>
             <a href="#" title="enter mobile code, e.g. India 91 and enter mobile number" class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
        	
            </td>
          </tr>
          <%-- <tr>
            <td align="right" height="50%"><span class="Mandatory">*</span><bean:message key="admissionForm.studentinfo.community.label"/>:</td>
            
            <td >
           
            <fieldset style="border: 0px">
            
             <html:radio  property="isCommuQuota" value="Yes" onclick="showAlert()">
             </html:radio>
             <label for="MALE"><span><span></span></span>Yes</label> 
			
			
			
			 <html:radio property="isCommuQuota" value="No" styleId="comuno">
			 
			 </html:radio>
			 <label for="FEMALE"><span><span></span></span>No</label> 
			 <a href="#" title="Whether you seeking Admission under Communnity Quota or Not" class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
        	
			</fieldset>	
          </tr> --%>
          <tr>
            <td align="right" height="50%"><div id="comunlBlock"><span class="Mandatory">*</span><bean:message key="admissionForm.studentinfo.community.label"/>:</div></td>
            
            <td >
           <div id="comunBlock">
            <fieldset style="border: 0px">
            
             <input type="radio" name="comun" onclick="showAlert();setCommunity('Yes')"/>
             <label for="MALE"><span><span></span></span>Yes</label> 
			
			
			
			 <input type="radio" name="comun" onclick="setCommunity('No')">
			 
			 <label for="FEMALE"><span><span></span></span>No</label> 
			 <a href="#" title="Whether you seeking Admission under Communnity Quota or Not" class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
        	
			</fieldset>	
			</div>
			</td>
          </tr>
           
         <tr>		   				
			<td align="right" height="50%"><span class="Mandatory">*</span>Apply for Management Quota:</td>
              <td >
           	<fieldset style="border: 0px">	
             <html:radio  property="mngQuota" value="1">
             </html:radio>
             <label for="MALE"><span><span></span></span>Yes</label> 
			
			 <html:radio property="mngQuota" value="0">
			 <label for="FEMALE"><span><span></span></span>No</label> 
			 <a href="#" title="Whether you seeking Admission under Management Quota or Not" class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
			 </html:radio>		
			</fieldset>				
											
			</td>
		</tr>
		
        </table>
        </td>
      </tr>
      
  <tr height="20px"><td></td></tr>
  <tr>
  <td  width="100%" align="center">
 <div align="center" id="buttons" style="display: block;">
  <html:submit value="Register" styleClass="formbutton" styleId="register_validate" /> 
  &nbsp; <html:button property="" value="Clear" styleClass="formbutton" onclick="resetForm('register');" /> 
  &nbsp;<html:button value="Cancel" property="" styleClass="formbutton" onclick="loginPage()" />	
  </div>    
  </td>
  </tr>
   
 
   <tr height="40px"><td></td></tr>
  
</table>
	
	
	
		
		
	<!--End login form -->
	<!--End of body -->
	
	</body>
<script src="js/admission/UniqueIdRegistrationPage.js" type="text/javascript"></script>	

 <script type="text/javascript"><!--

 $("#registerDateOfBirth").attr("placeholder","dd/mm/yyyy");
 document.getElementById("buttons").style.display="block";
 function showAlert() {
	    alert ("Applicable only for RCSC Candidates applying for Aided Programmes. No Community Quota for Self Financing Programmes.");
	  }
 /*
 $.confirm({
		'message'	: 'Aided and Unaided courses have to register separate Application Forms.',
		'buttons'	: {
			'Ok'	: {
				'class'	: 'blue',
				'action': function(){
					
					$.confirm.hide();
				}
			}
		}
	});*/
 </script>

</html:form>
</html>
