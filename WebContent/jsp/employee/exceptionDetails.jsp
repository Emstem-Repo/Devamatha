<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<script language="JavaScript" >
function addExceptionDetails() {
	document.getElementById("method").value = "addExceptionDetails";
}
function editException(id) {
	document.location.href = "exceptionDetails.do?method=editExceptionDetails&id="
			+ id;
}
function updateExceptionDetails() {
		document.getElementById("method").value = "updateExceptionDetails";
}
function reActivate(id) {
	document.location.href = "exceptionDetails.do?method=reActivateExceptionDetails&id="+id;
}
function deleteException(id) {
	deleteConfirm = confirm("Are you sure want to delete this entry?");
	if (deleteConfirm) {
		document.location.href = "exceptionDetails.do?method=deleteExceptionDetails&id=" 
				+ id;
	}
}
function resetMessages() {
	document.getElementById("toAM").checked =false;
	document.getElementById("fromAM").checked =false;
	document.getElementById("toPM").checked =false;
	document.getElementById("fromPM").checked =false;
	resetFieldAndErrMsgs();
	document.getElementById("eCodeName_1").checked = true;
}

function getECodeName(eCodeName){

	getEmployeeCodeName("employeeMap",eCodeName,"employeeId",updateToEmployee);
	
}

function updateToEmployee(req){
		updateOptionsFromMap(req, "employeeId", "- Select -");
}
</script>
<html:form action="/exceptionDetails">	
	<html:hidden property="method" styleId="method"  />
	<html:hidden property="formName" value="ExceptionDetailseForm" />
	<html:hidden property="pageType" value="1" />
	<html:hidden property="id" styleId="id" />
	<c:choose>
	<c:when test="${operation == 'edit'}">
			<html:hidden property="method" styleId="method"
				value="updateExceptionDetails" />
		</c:when>
		<c:otherwise>
			<html:hidden property="method" styleId="method"
				value="addExceptionDetails" />
		</c:otherwise>
		</c:choose> 
	
	
	
	
	
	<table width="98%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message key="knowledgepro.admin.sec.EmployeeCategory" />
			<span class="Bredcrumbs">&gt;&gt; <bean:message key="knowledgepro.emp.exceptionDetails.display" /> &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="10"><img src="images/Tright_03_01.gif"></td>
					<td width="100%" background="images/Tcenter.gif" class="body">
					<div align="left"><strong class="boxheader"><bean:message key="knowledgepro.emp.exceptionDetails.display" /></strong></div>
					</td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
						<tr>
							<td colspan="6" align="left">
							<div align="right"><span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields" /></span></div>
							<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
							<FONT color="green"><html:messages id="msg"
								property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
								<br>
							</html:messages></FONT></div>
							</td>
						</tr>
						<tr>
							<td><img src="images/01.gif" width="5" height="5" /></td>
							<td width="914" background="images/02.gif"></td>
							<td><img src="images/03.gif" width="5" height="5" /></td>
						</tr>
						<tr>
							<td width="5" background="images/left.gif"></td>
							<td width="100%" valign="top">
							<table width="100%" cellspacing="1" cellpadding="2">
							
							<tr>

									<td height="25" colspan="3" class="row-even">
									<div align="center">
									<input type="radio" name="eCodeName" id="eCodeName_1" value="eCode" checked="checked" onclick="getECodeName(this.value)" />
									
									Employee Code&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<input type="radio" name="eCodeName" id="eCodeName_2" value="eName" onclick="getECodeName(this.value)"/> 
										Employee
									Name&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									</div>
									</td>


								</tr>
							
			<tr>
                <td class="row-odd" ><div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message key="knowledgepro.hostel.applicationByAdmin.employeeName"/>:</div></td>
                <td class="row-even" colspan="3">
                <html:select name="ExceptionDetailseForm" property="employeeId" styleId="employeeId" styleClass="combo">
					<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
					<html:optionsCollection name="ExceptionDetailseForm" property="employeeMap" label="value" value="key"/>
				</html:select> 
                
                </td><td>&nbsp;</td>
              </tr>				
           
            <tr>
                <td class="row-odd" ><div align="right"><span class="Mandatory">*</span>&nbsp;Exception Types:</div></td>
                <td class="row-even" colspan="3">
                <html:select name="ExceptionDetailseForm" property="exceptionTypeId" styleId="exceptionTypeId" styleClass="combo">
					<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
					<html:optionsCollection name="ExceptionDetailseForm" property="exceptionTypeMap" label="value" value="key"/>
				</html:select> 
                
                </td><td>&nbsp;</td>
              </tr>
              
              
              <tr>
                <td   class="row-odd" ><div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message key="knowledgepro.attendance.leavemodify.fromdate"/>:</div></td>
                <td  height="25" class="row-even" ><table width="189" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="60">
                      <html:text name="ExceptionDetailseForm" property="fromDate" styleId="fromDate" size="10" maxlength="16"/> 
                      </td>
                      <td width="2"><script language="JavaScript">
						new tcal ({
							// form name
							'formname': 'ExceptionDetailseForm',
							// input name
							'controlname': 'fromDate'
						});</script>
					</td>
					<td width="126">
					<html:radio name="ExceptionDetailseForm" property="fromAM" styleId="fromAM" value="AM"><font size="-3">AM</font></html:radio>
					<html:radio name="ExceptionDetailseForm" property="fromAM" styleId="fromPM" value="PM"><font size="-3">PM</font></html:radio>
					</td>
                    </tr>
                  </table>
                  </td>
                  </tr>
                <tr>
                <td  class="row-odd"><div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message key="knowledgepro.attendance.leavemodify.todate"/>:</div></td>
                <td class="row-even"><table width="188" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td ><html:text name="ExceptionDetailseForm" property="toDate" styleId="toDate" size="10" maxlength="16"/> </td>
                    <td ><script language="JavaScript">
					new tcal ({
						// form name
						'formname': 'ExceptionDetailseForm',
						// input name
						'controlname': 'toDate'
					});</script>
					</td>
					<td >
					<html:radio name="ExceptionDetailseForm" property="toAM" styleId="toAM" value="AM"><font size="-3">AM</font></html:radio>
					<html:radio name="ExceptionDetailseForm" property="toAM" styleId="toPM" value="PM"><font size="-3">PM</font></html:radio>
					</td>
                  </tr>
                </table></td>
              </tr>
              <tr>
                <td class="row-odd" ><div align="right"><span class="Mandatory">*</span>&nbsp;Remarks:</div></td>
                <td class="row-even" colspan="3"><span class="row-white">
                <html:textarea property="remarks" name="ExceptionDetailseForm" cols="28" rows="4" ></html:textarea>
                </span></td><td>&nbsp;</td>
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
					<div align="center">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="46%" height="35">
								<div align="right">
									<c:choose>
										<c:when test="${operation == 'edit'}">
											<html:submit styleClass="formbutton" onclick="updateExceptionDetails()">
												<bean:message key="knowledgepro.emp.exceptionDetails.update" />
											</html:submit>
										</c:when>
										<c:otherwise>
											<html:submit styleClass="formbutton" onclick="addExceptionDetails()">
												<bean:message key="knowledgepro.emp.exceptionDetails" />
											</html:submit>
										</c:otherwise>
									 </c:choose>
							 	</div>
							</td>
							<td width="2%"></td>
							<td width="52%" align="left">
								<c:choose>
									<c:when test="${operation == 'edit'}">
										<html:cancel styleClass="formbutton" onclick="updateExceptionDetails()">
											Reset
										</html:cancel>
									</c:when>
									<c:otherwise>
										<html:button property=""
											styleClass="formbutton" onclick="resetMessages()" styleId="reset">
											<bean:message key="knowledgepro.emp.exceptionDetails.reset" />
										</html:button>
									</c:otherwise>
								</c:choose>
							 
							</td>
						</tr>
					</table>
					</div>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
							
							<table width="100%" border="0" align="center" cellpadding="0"
								cellspacing="0">
								<tr>
									<td><img src="images/01.gif" width="5" height="5"></td>
									<td width="914" background="images/02.gif"></td>
									<td><img src="images/03.gif" width="5" height="5"></td>
								</tr>
								<tr>
									<td width="5" background="images/left.gif"></td>
									<td valign="top">
									<table width="100%" cellspacing="1" cellpadding="2">

										<tr>
											<td width="4%" height="25" class="row-odd">
											<div align="center"><bean:message key="knowledgepro.slno" /></div>
											</td>
											<td width="15%" class="row-odd">
											<div align="center"><bean:message
											key="knowledgepro.hostel.applicationByAdmin.employeeName" /></div>
											</td>
											<td width="15%" class="row-odd">
											<div align="center"><bean:message
											key="knowledgepro.emp.exceptionType" /></div>
											</td>
											<td width="10%" class="row-odd">
											<div align="center"><bean:message
											key="knowledgepro.emp.exceptionDetails.fromDate" /></div>
											</td>
											<td width="10%" class="row-odd">
											<div align="center"><bean:message
											key="knowledgepro.emp.exceptionDetails.toDate" /></div>
											</td>
											<td width="9%" class="row-odd">
											<div align="center"><bean:message key="knowledgepro.edit" /></div>
											</td>
											<td width="8%" class="row-odd">
											<div align="center"><bean:message key="knowledgepro.delete" /></div>
											</td>
										</tr>
										<logic:notEmpty name="ExceptionDetailseForm" property="exceptionList">
										<logic:iterate id="eList" name="ExceptionDetailseForm" property="exceptionList"
											indexId="count">
											<tr>
											<c:choose>
												<c:when test="${count%2 == 0}">
													<tr class="row-even">
												</c:when>
												<c:otherwise>
													<tr class="row-white">
												</c:otherwise>
											</c:choose>
											<td height="25">
											<div align="center"><c:out value="${count + 1}" /></div>
											</td>
											<td align="center"><bean:write name="eList"
												property="employeeName" /></td>
											<td align="center"><bean:write name="eList"
												property="exceptionType" /></td>
											<td align="center"><bean:write name="eList"
												property="staffStartDate" /></td>
											<td align="center"><bean:write name="eList"
												property="staffEndDate" /></td>	
											<td height="25" align="center">
											<div align="center"><img src="images/edit_icon.gif"
													width="16" height="18" style="cursor:pointer"
													onclick="editException('<bean:write name="eList" property="id" />')" /></div>
											</td>
											<td height="25">
											<div align="center"><img src="images/delete_icon.gif"
													width="16" height="16" style="cursor:pointer"
													onclick="deleteException('<bean:write name="eList" property="id" />')" /></div>
											</td>
											</tr>
										</logic:iterate>
										</logic:notEmpty>
									</table>
									</td>
									<td width="5" height="30" background="images/right.gif"></td>
								</tr>
								<tr>
									<td height="5"><img src="images/04.gif" width="5"
										height="5"></td>
									<td background="images/05.gif"></td>
									<td><img src="images/06.gif"></td>
								</tr>
							</table>
							
						</td>
							<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
						</tr>
						
						
						
					<tr>
					<td height="26" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news"></td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>	
				<tr>
					<td><img src="images/Tright_03_05.gif" height="29" width="9"></td>
					<td background="images/TcenterD.gif" width="100%"></td>
					<td><img src="images/Tright_02.gif" height="29" width="9"></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</html:form>