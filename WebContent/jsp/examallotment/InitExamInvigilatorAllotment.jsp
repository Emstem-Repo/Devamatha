<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-tiles.tld" prefix="tiles" %>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms" %>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<%@page import="com.kp.cms.utilities.CommonUtil"%>
<%@page import="com.kp.cms.constants.CMSConstants"%>
<%@page import="java.util.Date"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>Invigilator Allotment</title>
<LINK REL=StyleSheet HREF= "css/styles.css" TYPE="text/css">
<script language="JavaScript" src="js/calendar_us.js"></script>
<script type="text/javascript" src="js/jquery.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<script src="jquery/js/jquery-1.9.0.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="jquery.confirm/jquery.confirm/teacherAllotment.css" />
    <script src="jquery.confirm/jquery.confirm/jquery.confirm.js"></script>
<link type="text/css"
	href="jquery/css/ui-lightness/jquery-ui-1.8.18.custom.css" rel="stylesheet" />
<script src="jquery/js/jquery-ui-1.10.0.custom.min.js" type="text/javascript"></script>
<style type="text/css">
body {
	margin-left: 0px;
	margin-top: 5px;
	margin-right: 0px;
	margin-bottom: 0px;
}
</style>
<style type="text/css">
.ui-datepicker {
	background: #6B8E23;
	border: 1px solid #555;
	color: #6B8E23;
	font-family: Arial;
	font-size: 11px;
	margin-left: 10px
}
</style>
<script type="text/javascript">
function maxlength(field, size) {
    if (field.value.length > size) {
        field.value = field.value.substring(0, size);
    }
}
function cancel(){
	document.location.href = "LoginAction.do?method=loginAction";
	
}
function getExamNames(academicYear)
{
	getExamNameByAcademicYear("listExamName",academicYear,"examId",updateExamNames );
}
function updateExamNames(req)
{
	updateOptionsFromMap(req, "examId", "--Select--");
}


/*function getDatewiseExemptionList(){
	document.getElementById("method").value="InvigilatorAllotmentExemptedList";
	document.examInviligatorAllotmentForm.submit();

}*/
function getTeacherAvailableList(){
	document.getElementById("method").value="InvigilatorAllotmentAvailableList";
	document.examInviligatorAllotmentForm.submit();

}
function getDatewiseExemptionList(){
	$.confirm({
		'message'	: 'Before running this process please make sure that Exam Room Allotment for single session is done if there are any special allotments for a date and session. Proceed with teacher allotment ?',
		'buttons'	: {
			'Ok'	: {
				'class'	: 'blue',
				'action': function(){
					$.confirm.hide();
					document.getElementById("method").value="InvigilatorAllotmentExemptedList";
					document.examInviligatorAllotmentForm.submit();
				}
			},
	  'Cancel'	:  {
				'class'	: 'gray',
				'action': function(){
					$.confirm.hide();
				}
			}
		}
	});

	
}




function Submit(){

	$.confirm({
		'message'	: 'The Process will assign Invigilators /Relivers to the rooms to which Students have been allotted for Exam. All the existing records for the Exam will be deleted and fresh Data will be created. Press Ok if u want to run the process, else press Cancel',
		'buttons'	: {
			'Ok'	: {
				'class'	: 'blue',
				'action': function(){
					$.confirm.hide();
					document.getElementById("method").value="InvigilatorAllotment";
					document.examInviligatorAllotmentForm.submit();
				}
			},
	  'Cancel'	:  {
				'class'	: 'gray',
				'action': function(){
					$.confirm.hide();
				}
			}
		}
	});
	
}

</script>
</head>
<body>
<table width="100%" border="0">
<html:form action="/InviligatorAllotment" enctype="multipart/form-data">
	<html:hidden property="pageType" value="1" />
	<html:hidden property="method" value="" styleId="method" />
	<html:hidden property="formName" value="examInviligatorAllotmentForm" />
	
       <tr>
			<td><span class="Bredcrumbs"><bean:message key="knowledgepro.exam.allotment" />
			<span class="Bredcrumbs">&gt;&gt; <bean:message key="knowledgepro.exam.allotment.invigilator" /> &gt;&gt;</span></span></td>
		</tr>
   <tr>
    <td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="10"><img src="images/Tright_03_01.gif" width="9" height="29"/></td>
        <td width="1271" background="images/Tcenter.gif" class="body" >
			<div align="left">
				<strong class="boxheader"><bean:message key="knowledgepro.exam.allotment.invigilator"/></strong>
			</div>
		</td>
       <td width="15" >
	   <img src="images/Tright_1_01.gif" width="9" height="29"/>	   </td>
      </tr>
      <tr>
        <td height="122" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news">
              <table width="100%" height="50"  border="0" cellpadding="0" cellspacing="0">
	                 <tr>
	               	    <td height="20" colspan="6" align="left">
	               	    <div align="right" style="color:red"> <span class='MandatoryMark'>* Mandatory fields</span></div>
	               	    <div id="err" style="color:red;font-family:arial;font-size:11px;"></div>
	               	    <div id="errorMessage">
	                       <FONT color="red"><html:errors/></FONT>
	                       <FONT color="green">
								<html:messages id="msg" property="messages" message="true">
										<c:out value="${msg}" escapeXml="false"></c:out><br>
								</html:messages>
						  </FONT>
						  </div>
	               	    </td>
	                 </tr>
					   	<tr>
						<td valign="top" class="news" colspan="3">
						<table width="100%" border="0" align="center" cellpadding="0"
							cellspacing="0">
							<tr>
								<td><img src="images/01.gif" width="5" height="5" /></td>
								<td width="100%" background="images/02.gif"></td>
								<td><img src="images/03.gif" width="5" height="5" /></td>
							</tr>
							<tr>
								<td width="5" background="images/left.gif"></td>
								<td valign="top">
								<table width="100%" cellspacing="1" cellpadding="2">
								    <tr>
						              <td width="20%" height="25" class="row-odd" ><div align="left"><span class="Mandatory">*</span><bean:message key="knowledgepro.fee.academicyear"/>:</div></td>
						              <td width="30%" height="25" class="row-even" colspan="3">
										 <input type="hidden" id="tempyear" name="tempyear" value="<bean:write name="examInviligatorAllotmentForm" property="academicYear"/>" />
						                	 <html:select property="academicYear" styleId="academicYear" styleClass="combo" onchange="getExamNames(this.value)">
						  	   				 	<html:option value=""> Select</html:option>
						  	   					<cms:renderYear></cms:renderYear>
						   			   		</html:select>
										</td>
								    </tr>
								
									<tr>
										<td width="14%" class="row-odd"><div align="left" ><span class="Mandatory">*</span><bean:message key="knowledgepro.employee.worklocation"/></div></td>
							               <td height="36" class="row-even" ><span class="star">
							                	<html:select property="workLocationId" styleId="workLocationId" styleClass="comboMediumBig">
												  <html:option value="">Select</html:option>
													<logic:notEmpty property="workLocationMap" name="examInviligatorAllotmentForm">
														<html:optionsCollection property="workLocationMap" label="value" value="key"/>
												 	</logic:notEmpty>
												</html:select>
							                </span>
							              </td>
							              <td height="25" class="row-odd"><div align="left"><span class="Mandatory">*</span> <bean:message key="knowledgepro.exam.examDefinition.examName" /> :</div></td>
											<td height="25" class="row-even"><span class="star">
											  <html:select property="examId" styleClass="combo" styleId="examId">
										       <html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
										       <logic:notEmpty name="examInviligatorAllotmentForm" property="examMap">
										           <html:optionsCollection name="examInviligatorAllotmentForm" property="examMap" label="value" value="key" /></logic:notEmpty>
									      	</html:select>
									      	</span>
									      </td>
						</tr>
			<tr >
                <td width="16%" height="25" class="row-odd" ><div align="left"><bean:message key="knowledgepro.admin.mobNewsEventsDetailsDateFrom"/>:</div></td>
                <td width="32%" height="25" class="row-even" ><label></label>
                    <span class="star">
                    <html:text property="fromDate" styleId="fromDate" size="20" maxlength="20"/>
                  </span>
                  <script language="JavaScript">
                  					$(function(){
											var pickerOpts = {
													 	        dateFormat:"dd/mm/yy"
													         };  
										  $("#fromDate").datepicker(pickerOpts);
										});
                  </script>
                  </td>
                  <td width="16%" height="25" class="row-odd" ><div align="left"><bean:message key="knowledgepro.admin.mobNewsEventsDetailsDateTo"/>:</div></td>
                <td width="32%" height="25" class="row-even" >
                    <span class="star">
                    <html:text property="toDate" styleId="toDate" size="20" maxlength="20"/>
                  </span>
                  <script language="JavaScript">
                  					$(function(){
											var pickerOpts = {
													 	        dateFormat:"dd/mm/yy"
													         };  
										  $("#toDate").datepicker(pickerOpts);
										});
                  </script>
                  </td>
              </tr>
							</table>			
								</td>
								<td width="5" height="30" background="images/right.gif"></td>
							</tr>
							<tr>
								<td height="5"><img src="images/04.gif" width="5" height="5" /></td>
								<td background="images/05.gif"></td>
								<td><img src="images/06.gif" /></td>
							</tr>
						</table>
						</td>
					</tr>
					<tr>
					<td height="10" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="45%" height="35" align="right">
							<html:button styleClass="formbutton" property ="" value="Submit" onclick="getDatewiseExemptionList()"></html:button></td>

							<td width="2%" height="35" align="center">&nbsp;</td>
							<td width="45%" align="left"><input name="Submit3"
								type="button" class="formbutton" value="Cancel"
								onclick="cancel()" /></td>
						</tr>
					</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
					<tr>
						<td valign="top" class="news" colspan="3">
						<!-- <table width="100%" border="0" align="center" cellpadding="0"
							cellspacing="0">
							<tr>
								<td><img src="images/01.gif" width="5" height="5" /></td>
								<td width="914" background="images/02.gif"></td>
								<td><img src="images/03.gif" width="5" height="5" /></td>
							</tr>
							
	<tr>
					<td height="19" valign="top" background="images/separater.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
					
					</table>
					</td>
					<td height="19" valign="top" background="images/separater.gif"></td>
			</tr>		
		<tr>
			        <td><img src="images/Tright_03_05.gif" width="9" height="29"/></td>
			        <td width="100%" background="images/TcenterD.gif"></td>
			        <td><img src="images/Tright_02.gif" width="9" height="29"/></td>
	 </tr>
	</table>-->	
	</table>
	</td>
	</tr>	 
	</html:form>
</table>
<script type="text/javascript">
	document.getElementById("academicYear").value=document.getElementById("tempyear").value;
	</script>
</body>
</html>
			
		
			
	


