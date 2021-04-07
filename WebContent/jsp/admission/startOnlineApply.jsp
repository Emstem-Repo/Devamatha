<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<html:html>
<head>
<title><bean:message key="knowledgepro.title"/> </title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

<script language="JavaScript" src="js/calendar_us.js"></script>
<script language="JavaScript" src="js/admission/admissionform.js"></script>
<script src="jquery/development-bundle/jquery-1.7.1.js"></script>
<script type='text/javascript' src="jquery/Zebra/javascript/zebra_dialog.js"></script>
<link rel="stylesheet" href="jquery/Zebra/css/default/zebra_dialog.css"  />
 <script src="http://code.jquery.com/jquery-latest.js"></script>
<link rel="stylesheet" href="css/calendar.css">


<link href="css/styles.css" rel="stylesheet" type="text/css">

<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 5px;
	margin-right: 0px;
	margin-bottom: 0px;
}
-->
</style>
<script type="text/javascript">
var jq=$.noConflict();

function noCopyMouse(e) {
    var isRight = (e.button) ? (e.button == 2) : (e.which == 3);
    if(isRight) {
    	alert('Please write the re confimation mail');
		document.getElementById("confirmEmailId").value="";
        return false;
    }
    return true;
}

function noCopyKey(e) {
    var isCtrl;
isCtrl = e.ctrlKey
if(isCtrl) {
		document.getElementById("confirmEmailId").value="";
    		return false;
    		
      	}
	
	if(e.keyCode == 17){
		document.getElementById("confirmEmailId").value="";
	return false;
	}

	//noCopyMouse(e);
    	return true;
}

jq(document).ready(function(){
	
	if(document.getElementById("paymentSuccess").value==true || document.getElementById("paymentSuccess").value=="true"){
		 jq(".mainDisplay :input").attr( 'disabled', 'disabled'); 
		 jq("#mainButton").hide();
	}
	else{
		jq("#mainButton").show();
		jq(".mainDisplay :input").removeAttr('disabled');
	}
	});
function resetData(){
	resetFieldAndErrMsgs();
}

</script>
<script type="text/javascript">
function submitAddMorePreferences(method,mode){
	
	document.getElementById("method").value=method;
	document.getElementById("mode").value=mode;
	document.admissionFormForm.submit();
}
var blinkLength = 3000;
function blink1(){
	document.getElementById('div3').style.display = "block";
	t1=setTimeout("blink2()",blinkLength);
}
function blink2(){
	document.getElementById('div3').style.display = "none";
	t2=setTimeout("blink1()",500);
}
function validateEditCourse(){
	
	 var programTypeId=document.getElementById("programTypeId").value;
	 var courseId=document.getElementById("coursePref1").value;
	alert(courseId);
	document.location.href = "admissionFormSubmit.do?method=getPreferences&programTypeId="+programTypeId+"&courseId="+courseId;
	
	
}
function getCoursesByProgramType(ProgramTypeId) {
	//alert('000000000000000000');
	getCoursesByProgramTypes("courseMap", ProgramTypeId, "coursePref1",
			updateCourses1);
		
}
function updateCourses1(req) {
	updateOptionsFromMap(req,"coursePref1","- Select -");
}
function getCoursesByProgramType1(ProgramTypeId) {
	//alert('000000000000000000');
	getCoursesByProgramTypes("courseMap", ProgramTypeId, "coursePreference1",
			updateCourses11);
		
}
function updateCourses11(req) {
	updateOptionsFromMap(req,"coursePreference1","- Select -");
}

</script>

</head>

<body onload="blink1()">
<html:form action="/admissionFormSubmit">
<html:hidden property="method" value="" styleId="method"/>
<html:hidden property="pageType" value="26"/>
<html:hidden property="formName" value="admissionFormForm"/>
<html:hidden property="onlineApply" value="true"/>
<html:hidden property="paymentSuccess" styleId="paymentSuccess"/>
<html:hidden property="nativeCountry" styleId="nativeCountry" name="admissionFormForm"/>
<html:hidden property="serverDownMessage" styleId="serverDownMessage" name="admissionFormForm" />
<%
	String submitjsmethod=null;
%>
<logic:notEmpty name="transactionstatus" scope="request">
	<%
		submitjsmethod="#";
	%>
</logic:notEmpty>
<logic:empty name="transactionstatus" scope="request">
	<%
		submitjsmethod="submitAdmissionForm('submitOnlineApply')";
	%>
</logic:empty>
	<table width="100%" border="0">
  <tr>
    <td><span class="Bredcrumbs"><bean:message key="knowledgepro.admission"/><span class="Bredcrumbs">&gt;&gt; <bean:message key="knowledgepro.applicationform.online.label"/> &gt;&gt;</span></span></td>
  </tr>
  <tr>
    <td valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0" >
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td width="954" background="images/Tcenter.gif" class="body" ><strong class="boxheader"> <bean:message key="knowledgepro.applicationform.online.label"/></strong></td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
		
		
        

      <tr>
        <td height="20" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><div align="center"> <span class='MandatoryMark'><font size="3">All Fields Marked in Red Star are Mandatory</font></span></div>
<div id="errorMessage">
      						<html:errors/><FONT color="green">
							<html:messages id="msg" property="messages" message="true">
									<c:out value="${msg}" escapeXml="false"></c:out><br>
							</html:messages>
					  </FONT>
					</div></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      
      <tr>
        <td height="20" valign="top" background="images/Tright_03_03.gif"></td>
        <td width="100%" valign="top" class="news"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
            <tr>
              <td ><img src="images/01.gif" width="5" height="5" /></td>
              <td width="914" background="images/02.gif"></td>
              <td><img src="images/03.gif" width="5" height="5" /></td>
            </tr>
            <tr>
              <td width="5"  background="images/left.gif"></td>
              <td width="100%" valign="top"><table width="100%" height="30" border="0" cellpadding="0" cellspacing="1">
              <tr><td><font size="2">Personal Details</font></td></tr>
                <tr class="row-white">
                    <td width="161" height="25" class="row-odd"><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.applicationform.candidateName"/> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div></td>
                    <td width="164" class="row-even"><div class="mainDisplay"><html:text property="applicantName" size="30" maxlength="90"></html:text> </div>
                    </td></tr>
                    <tr>
                    <td width="140" class="row-odd"><div align="right" class="row-odd"><span class="Mandatory">*</span>
					<bean:message key="admissionForm.studentinfo.dob.label"/><bean:message key="admissionForm.application.dateformat.label"/>
                          <bean:message key="knowledgepro.applicationform.dob.format"/>:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					</div></td>
                    <td width="167" class="row-even"><div class="mainDisplay"><html:text property="applicantDob" styleId="dateOfBirth" size="11" maxlength="11" ></html:text>
                    <script language="JavaScript">
												new tcal( {
													// form name
													'formname' :'admissionFormForm',
													// input name
													'controlname' :'dateOfBirth'
												});
											</script></div>
                    </td></tr>
                    <tr>
                    <td width="140" class="row-odd"><div align="right"><span class="Mandatory">*</span>
                    <bean:message key="admissionForm.studentinfo.residentcatg.label"/><bean:message key="admissionForm.studentinfo.residentcatg.label2"/>:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div></td>
                    <td width="160" class="row-even"><div class="mainDisplay">                
                    <html:select property="residentCategoryForOnlineAppln" styleClass="comboLarge" styleId="residentCategory" onchange="getMobileNo()">
						<option value=""><bean:message key="knowledgepro.admin.select"/></option>
						<html:optionsCollection property="residentTypes" label="name" value="id"/>
													
					</html:select></div>
                    </td>
                  </tr>
                 <tr class="row-white">
                    <td width="161" height="25" class="row-odd" >
                    
                    <table width="100%" border="0" cellpadding="0" cellspacing="0">
			                              	  <tr><td valign="top"><div align="right">
			                              	  <span class="Mandatory">*</span><bean:message key="admissionForm.studentinfo.mobile.label"/>
			                              	  <bean:message key="admissionForm.phone.cntcode.label"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div></td></tr>
			                              	  <tr><td height="10"></td></tr>
			                                  <tr><td valign="bottom"><div align="right"> <bean:message key="admissionForm.mob.no.label"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div> </td></tr>
											</table> 
                   </td>
                    <td width="164" class="row-even"><table width="100%" border="0" cellpadding="0" cellspacing="0">
			                              	  <tr><td><div class="mainDisplay"><nested:text property="mobileNo1" styleId="mobileNo1" size="4" maxlength="4" onkeypress="return isNumberKey(event)"></nested:text></div></td></tr>
			                                  <tr><td><div class="mainDisplay"><nested:text property="mobileNo2" size="20" maxlength="10" onkeypress="return isNumberKey(event)"></nested:text></div></td></tr>
											</table>
                    </td>
                    </tr>
                    <tr>
                    <td width="140" class="row-odd"><div align="right" class="row-odd"><span class="Mandatory">*</span>
					<bean:message key="admissionForm.studentinfo.email.label"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					</div></td>
                    <td width="167" class="row-even"><table width="100%" border="0" cellpadding="0" cellspacing="0">
												 <tr><td><div class="mainDisplay"><nested:text property="email" size="30" maxlength="50"></nested:text></div> </td></tr>
												<tr><td>(e.g. name@yahoo.com)</td></tr>
                                  				</table>
                    </td></tr>
                    <tr>
                    <td width="140" class="row-odd"><div align="right"><span class="Mandatory">*</span><bean:message key="admissionForm.studentinfo.confirmemail.label"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    </div>
                   </td>
                    <td width="160" class="row-even"><div class="mainDisplay">
                    <p>
                        <nested:text property="confirmEmail" styleId="confirmEmailId" size="30" maxlength="50" onmousedown="noCopyMouse(event)" onkeydown= "noCopyKey(event)" onkeyup="noCopyKey(event)" ></nested:text>
                    <br />
			       </p></div>
                    </td>
                  </tr> 
                 <tr><td><font size="2">Preference Details</font></td></tr>
                  <tr class="row-white">
                   
                    <!--<td width="161" height="25" class="row-odd"><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.admission.programtype"/>:</div></td>
                    <td width="164" class="row-even"> <input type="hidden" id="programType" name="programType" value='<bean:write name="admissionFormForm" property="programTypeId"/>'/><div class="mainDisplay">
                <html:select styleId="programTypeId"  property="programTypeId" styleClass="comboLarge" onchange="getApplicationPrograms(this.value,'progPref1')">
									<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
									<cms:programTypesOnlineOpen></cms:programTypesOnlineOpen>
				</html:select></div></td> -->
				<td width="161" height="25" class="row-odd"><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.admission.programtype"/>:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div></td>
                    <td width="164" class="row-even"> <input type="hidden" id="programType" name="programType" value='<bean:write name="admissionFormForm" property="programTypeId"/>'/><div class="mainDisplay">
                <html:select styleId="programTypeId"  property="programTypeId" styleClass="comboLarge" onchange=" getCoursesByProgramType(this.value); getCoursesByProgramType1(this.value)">
									<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
									<cms:programTypesOnlineOpen></cms:programTypesOnlineOpen>
				</html:select></div></td></tr>
                   <!--<td width="140" class="row-odd"><div align="right" class="row-odd"><span class="Mandatory">*</span><bean:message key="knowledgepro.admission.program"/>:</div></td>
                    <td width="167" class="row-even"><div class="mainDisplay"><html:select property="programId" styleClass="comboLarge" styleId="progPref1" onchange="getCourseForOnline(this.value,'coursePref1')">
									<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
									<logic:notEmpty name="admissionFormForm" property="programMap">
									<html:optionsCollection name="admissionFormForm" property="programMap" label="value" value="key"/>
									</logic:notEmpty>
							</html:select></div></td>  -->
							<tr> <td width="140" class="row-odd"><div align="right"><span class="Mandatory">*</span><bean:message key="admissionForm.edit.firstpref.label"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div></td>
                    <td width="160" class="row-even"><div class="mainDisplay"><html:select property="courseId" styleClass="comboLarge" styleId="coursePref1">
									<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
									<logic:notEmpty name="admissionFormForm" property="courseMap">
									<html:optionsCollection name="admissionFormForm" property="courseMap" label="value" value="key"/>
									</logic:notEmpty>
							</html:select></div></td></tr>
							
							<logic:notEmpty name="admissionFormForm" property="prefcourses">
								<nested:iterate id="admissionpreference" name="admissionFormForm" property="prefcourses" indexId="count">
									<tr>
										<nested:notEmpty name="admissionpreference" property="courseMap">
                     						<td width="140" class="row-odd"><div align="right"><span class="Mandatory">*</span><bean:write name="admissionpreference" property="prefName"></bean:write>:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div></td>
                    						<td width="160" class="row-even"><div class="mainDisplay">
                    							<nested:select property="id" styleClass="comboLarge"  styleId="coursePreference1">
													<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
													<nested:optionsCollection  property="courseMap" label="value" value="key"/>
												</nested:select>
											</div></td>
										</nested:notEmpty>
               			 			</tr>
               			 		</nested:iterate>
							</logic:notEmpty>
               			 
                 <tr>
							<!--<td width="140" class="row-odd"><div align="right">&nbsp;<font size="3" color="red">*</font><font size="2" color="blue"> To add more </font></div></td>
							<td width="160" class="row-odd"><font size="2" color="blue" >&nbsp;preferences <a href="#"  onclick="validateEditCourse()"><blink>Click here</blink></a></font></td></tr>
                  
                  -->
                   <td width="140" class="row-odd"><div align="right">&nbsp;<font size="3" color="red">*</font><font size="2" color="blue"> (To add more preferences click here) </font></div></td>
					
                 <td width="140" class="row-even"><div align="left">
			                         <html:submit property="" styleClass="formbutton" value="Add more"  onclick="submitAddMorePreferences('addMorePreference','ExpAddMore'); return false;"></html:submit></div>
									</td> 
									
                 	</tr>
                
              </table></td>
              <td width="5" height="30"  background="images/right.gif"></td>
            </tr>
            <tr>
              <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
              <td background="images/05.gif"></td>
              <td><img src="images/06.gif" /></td>
            </tr>
        </table></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr >
        <td height="52" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table  width="100%"  border="0" cellspacing="0" cellpadding="0">
          <tr id="mainButton" >
            <td width="49%" height="35"><div align="right">
               <html:button property="" onclick='<%=submitjsmethod %>' styleClass="formbutton" value="Continue"></html:button>
            </div></td>
            <td width="1%"></td>
            <td width="51%"><div align="left"><html:button property=""  styleClass="formbutton" value="Reset" onclick="resetData()"></html:button></div></td>
          </tr>
        </table>
        <table width="100%"  border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="20%" height="35"></td>
            <td width="80%" align="left"></td>
          </tr>
        </table>
        </td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <logic:equal value="true" name="admissionFormForm" property="paymentSuccess">
      <tr>
        <td height="20" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
            <tr>
              <td ><img src="images/01.gif" width="5" height="5" /></td>
              <td width="914" background="images/02.gif"></td>
              <td><img src="images/03.gif" width="5" height="5" /></td>
            </tr>
            <tr>
              <td width="5"  background="images/left.gif"></td>
              <td width="100%" valign="top"><table width="100%" height="30" border="0" cellpadding="0" cellspacing="0">
                  <tr class="row-white">
                  	<td class="row-odd" height="25" width="15%" ></td>
                    <td width="70%" height="25" class="row-odd" align="center">
                    You have already made a payment through online for the course selected. Please continue to fill the application form!!
                    </td>
                    <td class="row-odd" height="25" width="15%" ></td>
                  </tr>
                  <tr>
                  <td class="row-odd" height="25" width="15%" ></td>
                  <td height="25" class="row-odd" align="center"><html:button property=""  styleClass="formbutton" value="Proceed to Fill Application" onclick="submitAdmissionForm('initGuidelinesPage')"></html:button></td>
                  <td class="row-odd" height="25" width="15%" ></td>
                  </tr>
              </table></td>
              <td width="5" height="30"  background="images/right.gif"></td>
            </tr>
            <tr>
              <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
              <td background="images/05.gif"></td>
              <td><img src="images/06.gif" /></td>
            </tr>
        </table></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      </logic:equal>
      <tr>
        <td height="52" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table  width="100%"  border="0" cellspacing="0" cellpadding="0">
          <tr>
				      <td>
				      <table width="100%">
				      <tr>
				                  <td colspan="6" align="center" style='color:red; font-size: 14px;'  >
				                  Demand Draft for  Rs. 200/-(UG) drawn in favour of Secretary, SH.
								
								
								</td>
								</tr>
				                  
				                  <tr><td height="30"></td>
				                  </tr>
				       <tr>
						<td colspan="3" class="heading" >
							<div align="center">This application is best viewed in Mozilla firefox.
							<a href="http://www.mozilla.org">Click Here to download Mozilla</a>
							</div>
						</td>					
					</tr>
					<tr height="5"></tr>
					
				      <tr>
						<td colspan="3">
							<div id=message>
							</div>
						</td>					
					</tr>
					 <tr height="15">
				      <td></td>
				      </tr> 
				   
					
					<tr>
						<td width="34%"></td>
						<td width="36%" class="heading">
							
						</td>
						<td width="30%"></td>
					</tr>
					
					</table>
					</td></tr>
        </table>
        
        </td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      
      <tr>
        <td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
        <td background="images/TcenterD.gif"></td>
        <td><img src="images/Tright_02.gif" width="9" height="29"></td>
      </tr>
      <tr height="10">
      <td colspan="3"></td>
      </tr>
    
  
	

    </table></td>
  </tr>
</table>
</html:form>
</html:html>
<script type="text/javascript">

var programTypeId = document.getElementById("programType").value;
if(programTypeId != null && programTypeId.length != 0) {
	document.getElementById("programTypeId").value = programTypeId;
}

var browserName=navigator.appName; 
 if (browserName=="Microsoft Internet Explorer")
 {
	 document.getElementById("message").innerHTML="<b style='color:red'>Note:</b> Use Mozilla Firefox for better Performance and view  or Please Change the Settings : Go to Tools -> Internet Options -> Privacy -> Advanced ->  Check the Override automatic cookie handling.";
	 alert(" If Your using InternetExplorer Please Change the Settings : Go to Tools -> Internet Options -> Privacy -> Advanced ->  Check the Override automatic cookie handling  or Use Mozilla FireFox");
 }

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
			document.getElementById("mobileNo1").value="91";
		}else{
			document.getElementById("mobileNo1").value="";
		}			
}
 var serverDownMessage=$('#serverDownMessage').val();
 if(serverDownMessage!=null && serverDownMessage!=""){
 	 $(".ZebraDialog_Title a.ZebraDialog_Close").hide();
 	$.Zebra_Dialog('<strong>'+serverDownMessage+'</strong>',{
 		'title':    'Alert',
 		'buttons':  ['Hide'],
 		'keyboard':false,
 		'overlay_close':false,
 		'show_close_button':false
 	});
 }
</SCRIPT>