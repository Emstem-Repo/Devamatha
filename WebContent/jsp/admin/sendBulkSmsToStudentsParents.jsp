<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<html:html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<script language="JavaScript" src="js/admission/admissionform.js"></script>
<link href="css/styles.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
	function submitMe(method) {
		document.sendBulkSmsToStudentsParentsForm.method.value = method;
		document.sendBulkSmsToStudentsParentsForm.submit();
	}


	function getCourses(programId) {
		getCoursesByProgram("coursesMap",programId,"course",updateCourses);
		
	}

	function updateCourses(req) {
		updateOptionsFromMap(req,"course","- Select -");
	}
	function IsMobileNumber() {
		var mob = /^[789]{1}[0-9]{9}$/;
		var txtMobile = document.getElementById("mobileNo");
		if (mob.test(txtMobile.value) == false) {
		    alert("Please enter valid mobile number.");
		    txtMobile.focus();
		    return false;
		}
	
			
		return true;
	}
	function showProgramType(){
		document.getElementById("showProgramType").style.display = "block";
		document.getElementById("programTypeId").disabled = true;
		document.getElementById("progPref1").disabled = true;
		document.getElementById("year").disabled = true;
		document.getElementById("course").disabled = true;
		
		
		
	}
	function hideProgramType(){
		document.getElementById("programTypeId").disabled = false;
		document.getElementById("progPref1").disabled = false;
		document.getElementById("showProgramType").style.display = "none";
		document.getElementById("year").disabled = false;
		document.getElementById("course").disabled = false;
	}
	
</script>
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
</head>

<body>
<html:form action="/sendBulkSmsToStudentsParents">
	<html:hidden property="method" value="SendingBulkSms" />
	<html:hidden property="pageType" value="1" />
	<html:hidden property="formName" value="sendBulkSmsToStudentsParentsForm" />

	<table width="98%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message
				key="knowledgepro.admin" /><span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.sendSms.label" /> &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif"></td>
					<td width="935" colspan="2" background="images/Tcenter.gif"
						class="body">
					<div align="left"><strong class="boxheader"> <bean:message
						key="knowledgepro.sendSms.label" /></strong></div>
					</td>
					<td width="9"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td height="20" colspan="2" class="heading">
					<div align="right"><span class='MandatoryMark'><bean:message
						key="knowledgepro.mandatoryfields" /></span></div>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>

				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td colspan="2">

					<div id="errorMessage"><html:errors /><FONT color="green">
					<html:messages id="msg" property="messages" message="true">
						<c:out value="${msg}" escapeXml="false"></c:out>
						<br>
					</html:messages> </FONT></div>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>

				<tr>
					<td height="52" valign="top" background="images/Tright_03_03.gif"></td>
					<td colspan="2" valign="top" class="news">
					<table width="99%" border="0" align="center" cellpadding="0"
						cellspacing="0">
						<tr>
							<td><img src="images/01.gif" width="5" height="5"></td>
							<td width="914" background="images/02.gif"></td>
							<td><img src="images/03.gif" width="5" height="5"></td>
						</tr>
						
											
						<tr>
							<td width="5" background="images/left.gif"></td>
							<td height="30" valign="top">
							<table width="100%" height="30" border="0" cellpadding="0"
								cellspacing="1">
								
								<tr>
							
						   	      <td width="16%" height="25" class="row-odd">
							            <div align="right"><span class="Mandatory">*</span>Sending SMS to all students ?:</div> </td>
							    	<td width="8%" class="row-even"><html:radio styleId="sendingSmsToAllStudents1"
															property="sendingSmsToAllStudents" value="yes" onclick="showProgramType();">Yes</html:radio>											
									</td>									
									<td width="8%" class="row-even"><html:radio styleId="sendingSmsToAllStudents2"
															property="sendingSmsToAllStudents" value="no" onclick="hideProgramType();">No</html:radio>								
									</td>
								</tr>
				           		<tr id="showProgramType" style="display: none">
							
						   	     			<td width="16%" height="20" class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.admission.programtype" />:</div>
									</td>
									<td width="16%" height="20" class="row-even"><input
										type="hidden" id="programType" name="programType"
										value='<bean:write name="sendBulkSmsToStudentsParentsForm" property="programTypeId1"/>' />

									<html:select styleId="programTypeId1" property="programTypeId1"
										styleClass="combo"
										onchange="getPrograms(this.value,'progPref1')">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<cms:renderProgramTypes></cms:renderProgramTypes>
									</html:select></td>
								</tr>
								
							
							</table>
							
							</td>
							
							<td background="images/right.gif" width="5" height="5"></td>
						</tr> 
		
						
						<tr>
							<td width="5" background="images/left.gif"></td>
							<td height="30" valign="top">
							<table width="100%" height="30" border="0" cellpadding="0"
								cellspacing="1">
								
			<tr class="row-white">
									<td width="16%" height="20" class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.admission.programtype" />:</div>
									</td>
									<td width="16%" height="20" class="row-even"><input
										type="hidden" id="programType" name="programType"
										value='<bean:write name="sendBulkSmsToStudentsParentsForm" property="programTypeId"/>' />

									<html:select styleId="programTypeId" property="programTypeId"
										styleClass="combo"
										onchange="getPrograms(this.value,'progPref1')">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<cms:renderProgramTypes></cms:renderProgramTypes>
									</html:select></td>
									<td width="16%" height="20" class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.admission.program" />:</div>
									</td>
									
									<td width="16%" height="20" class="row-even"><html:select
										property="programId" styleClass="combo" styleId="progPref1" onchange="getCourses(this.value)">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<c:choose>
											<c:when test="${operation == 'load'}">
												<html:optionsCollection name="programMap" label="value"
													value="key" />
											</c:when>
											<c:otherwise>
												<c:if
													test="${sendBulkSmsToStudentsParentsForm.programTypeId != null && sendBulkSmsToStudentsParentsForm.programTypeId != ''}">
													<c:set var="programMap"
														value="${baseActionForm.collectionMap['programMap']}" />
													<c:if test="${programMap != null}">
														<html:optionsCollection name="programMap" label="value"
															value="key" />
													</c:if>
												</c:if>
											</c:otherwise>
										</c:choose>
									</html:select></td>
									</tr>
									<tr>
									<td width="16%" height="20" class="row-odd">
									<input
										type="hidden" id="tempyear" name="appliedYear"
										value="<bean:write name="sendBulkSmsToStudentsParentsForm" property="year"/>" />
								
									
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.fee.appliedyear" /></div>
									</td>
									<td width="16%" height="20" class="row-even"><html:select
										property="year" styleId="year" styleClass="combo">
										<html:option value=" ">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<cms:renderYear></cms:renderYear>
									</html:select></td>
									 <td width="25%" height="25" class="row-odd" ><div align="right"> <bean:message key="knowledgepro.exam.gradeDefinition.course" /><span class="Mandatory">*</span>  :</div></td>
                                  <td  height="5" class="row-even"><nested:select
										property="courseIds" styleClass="combo" styleId="course" >
										<c:if test="${sendBulkSmsToStudentsParentsForm.programId != null && sendBulkSmsToStudentsParentsForm.programId != ''}">
             						<c:set var="courseMap" value="${baseActionForm.collectionMap['coursesMap']}"/>
             		    			<c:if test="${courseMap != null}">
             		    				<html:optionsCollection name="courseMap" label="value" value="key"/>
             		    			</c:if>	 
             		   			</c:if>
             		   			<%-- 
										<nested:optionsCollection name="sendBulkSmsToStudentsParentsForm"
											property="listCourseName" label="display" value="id" />
								--%>			
									</nested:select>
									</td>
								</tr>
								
							
							</table>
							
							</td>
							
							<td background="images/right.gif" width="5" height="5"></td>
						</tr>
						
						
						
			
						
						<tr>
							<td width="5" background="images/left.gif"></td>
							<td height="30" valign="top">
							<table width="100%" height="30" border="0" cellpadding="0"
								cellspacing="1">

								<tr>
										<td width="16%" height="25" class="row-odd">
											<div align="right"><span class="Mandatory">*</span>office Number:</div>
										</td>
										<td width="16%" height="25" class="row-even"><html:text
											property="mobileNo" styleId="mobileNo" onchange="IsMobileNumber();" /><span class="star"></span></td>
	
	
										<td width="16%" height="25" class="row-odd">
											<div align="right"><span class="Mandatory">*</span><bean:message
												key="knowledgepro.admin.sendSms.message" /></div>
										</td>
										<td width="16%" height="25" class="row-even"><html:textarea
											property="message" cols="40" rows="5" styleId="message"
											styleClass="TextBox" /><span class="star"></span></td>
								</tr>

								<tr>

									<td height="25" colspan="8" class="row-even">
									<div align="Center"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.admin.sendSms.message.send" />
									<html:radio  property="isStudent"  value="true" >
													<bean:message key="knowledgepro.admin.sendSms.student" /></html:radio>
									<html:radio  property="isStudent"  value="false" >
													<bean:message key="knowledgepro.admin.sendSms.parent" /></html:radio>
								</div>
									</td>

								</tr>
								
							
							</table>
							
							</td>
							
							<td background="images/right.gif" width="5" height="5"></td>
						</tr>
	
						<tr>
							<td height="5"><img src="images/04.gif" width="5" height="5"></td>
							<td background="images/05.gif"></td>
							<td><img src="images/06.gif"></td>
						</tr>
					</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>


				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td colspan="2" valign="top" class="news">
					<div align="center">
					<table width="100%" height="27" border="0" cellpadding="1"
						cellspacing="2">
						<tr>
							<td>
							<table width="100%" border="0" cellspacing="0" cellpadding="10">
								<tr>
									<td width="25%" height="21">
									<div align="center">
									<input name="button2" type="submit"
								class="formbutton" value="Submit" />
										</div>
									</td>
									
								</tr>
							</table>
							</td>
						</tr>
					</table>
					</div>
					</td>
					<td width="9" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td colspan="2" valign="top" class="news">&nbsp;</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td><img src="images/Tright_03_05.gif" height="29" width="9"></td>
					<td colspan="2" background="images/TcenterD.gif" width="100%"></td>
					<td><img src="images/Tright_02.gif" height="29" width="9"></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</html:form>
</body>

</html:html>

<script type="text/javascript">

var year = document.getElementById("tempyear").value;
if(year.length != 0) {
 	document.getElementById("year").value=year;
}
	if (document.getElementById("programType") != null
			&& document.getElementById("programTypeId") != null) {
		var programTypeId = document.getElementById("programType").value;
		if (programTypeId != null && programTypeId.length != 0) {
			document.getElementById("programTypeId").value = programTypeId;
		}
	}
</script>