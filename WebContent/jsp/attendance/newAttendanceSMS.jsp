<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">

<script type="text/javascript"><!--
function getClasses(year) {
	getClassesByYear("classMap", year, "classes", updateClasses);
}
function updateClasses(req) {
	updateOptionsFromMap(req, "classes", " - Select -");
}

	function resetValues() {
		document.getElementById("classes").value = "";				
		resetErrMsgs();
	}
	//function validateDate()
	//{
		//var date=document.getElementById("attendancedate").value;
		//if(date!=null && date!="")
		//{
			//var selectedDate=new Date(date);
			//var currDate=new Date();
			//if(selectedDate>currDate)
			//{
			//	alert("Invalid Date");
			//}			
		//}
		//else
		//{
			//alert("Select Valid Date");
		//}
		
	//}
</script>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html:form action="/NewAttendanceSMS">
	
	<html:hidden property="method" styleId="method" value="getAbsentees" />
	<html:hidden property="formName" value="newAttendanceSmsForm" />
	<html:hidden property="pageType" value="1" />

	<table width="100%" border="0">
		<tr>
			<td class="Bredcrumbs">
				<bean:message key="knowledgepro.admin" />
			 <span class="Bredcrumbs">&gt;&gt; Send SMS &gt;&gt;</span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> Send SMS</strong></td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="122" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" cellspacing="0" cellpadding="0">
						<tr bgcolor="#FFFFFF">
							<td height="20" colspan="6">
							<div align="right"><FONT color="red"> <span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields"/></span></FONT></div>
							<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
							<FONT color="green"> <html:messages id="msg"
								property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
								<br>
							</html:messages> </FONT></div>
							</td>
						</tr>

						<tr>
							<td valign="top" class="news">
							<table width="100%" border="0" align="center" cellpadding="0"
								cellspacing="0">
								<tr>
									<td><img src="images/01.gif" width="5" height="5" /></td>
									<td width="914" background="images/02.gif"></td>
									<td><img src="images/03.gif" width="5" height="5" /></td>
								</tr>
								<tr>
									<td width="5" background="images/left.gif"></td>
									<td valign="top">
									<table width="100%" cellspacing="1" cellpadding="2">

										<tr>
											<td height="25" class="row-odd">
											<div align="right"><span class="Mandatory">*</span>&nbsp;Date:</div>
											</td>
											  <td width="60" class="row-even">
							                      <html:text name="newAttendanceSmsForm" styleId="attendancedate" property="attendancedate" styleClass="TextBox" onchange="validateDate()"/>
							                   </td>
							                     <td width="40" class="row-even"><script language="JavaScript">
														new tcal ({
															// form name
															'formname': 'newAttendanceSmsForm',
															// input name
															'controlname': 'attendancedate'
												 });</script></td>											
											
											<td height="25" class="row-odd">
											<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message
												key="knowledgepro.admin.year" />:</div>
											</td>
											<td align="left" height="25" class="row-even"><input type="hidden"
												id="tempyear" name="tempyear"
												value="<bean:write name="newAttendanceSmsForm" property="year"/>" />
											<html:select property="year" styleId="year" onchange="getClasses(this.value),validateDate()"
												styleClass="combo">
												<html:option value=" "><bean:message key="knowledgepro.admin.select"/></html:option>
												<cms:renderAcademicYear></cms:renderAcademicYear>
											</html:select></td>
											
											<td height="25" class="row-odd">
											<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message
												key="knowledgepro.attendance.activityattendence.class" />:</div>
											</td>
											<td align="left" width="30%" class="row-even">
											<html:select
												property="classes" multiple="multiple" styleClass="body" size="10"  style="width:300px"
												styleId="classes" onchange="getPeriodFrom(this.value)">												
												<c:choose>
													<c:when test="${classMap != null}">
														<html:optionsCollection name="classMap" label="value"
															value="key" />
													</c:when>
													<c:otherwise>
														<c:set var="classMap"
															value="${baseActionForm.collectionMap['classMap']}" />
														<c:if test="${classMap != null}">
															<html:optionsCollection name="classMap" label="value"
																value="key" />
														</c:if>
													</c:otherwise>
												</c:choose>
											</html:select></td>
										
										</tr>

									</table>
									</td>
									<td width="5" height="29" background="images/right.gif"></td>
								</tr>
			
								<tr>
									<td height="5"><img src="images/04.gif" width="5" height="5" /></td>
									<td background="images/05.gif"></td>
									<td><img src="images/06.gif" /></td>
								</tr>
							</table>
							</td>
							<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
						</tr>

						<tr>
							<td height="25" colspan="4">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td width="45%" height="35">
									<div align="right"><c:choose>
										<c:when
											test="${stateOperation != null && stateOperation == 'edit'}">
											<html:submit property="" styleClass="formbutton"
												value="Update" styleId="submitbutton">
											</html:submit>
										</c:when>
										<c:otherwise>
											<html:submit property="" styleClass="formbutton"
												value="Submit" styleId="submitbutton">
											</html:submit>
										</c:otherwise>
									</c:choose></div>
									</td>
									<td width="2%"></td>
									<td width="53%"><html:button property=""
										styleClass="formbutton" value="Reset" onclick="resetValues()"></html:button></td>
								</tr>
							</table>
							</td>
						</tr>

				
					</table>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				<tr>
              <td  valign="top" background="images/Tright_03_03.gif"></td>
              <td height="20" valign="top" ></td>
              <td width="13" valign="top" background="images/Tright_3_3.gif" class="news"></td>
            </tr>
            <tr>
              <td><img src="images/Tright_03_05.gif" width="9" height="29" /></td>
              <td width="100%" background="images/TcenterD.gif"></td>
              <td><img src="images/Tright_02.gif" width="9" height="29" /></td>
            </tr>
			</table>
			</td>
		</tr>
	</table>
	<script type="text/javascript">
		document.getElementById("year").value="";
		document.getElementById("tempyear").value="";
	</script>
	
</html:form>

