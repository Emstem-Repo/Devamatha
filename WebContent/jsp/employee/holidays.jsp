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
function addEmpHolidays() {
	document.getElementById("method").value = "addEmpHolidays";
}
function editHolidays(id) {
	document.location.href = "holidaysDetails.do?method=editEmpHolidays&id="+ id;
}
function updateHolidays() {
		document.getElementById("method").value = "updateEmpHolidays";
}
function reActivate(id) {
	document.location.href = "holidaysDetails.do?method=activateHolidays&id="+ id;
}
function deleteHolidays(id) {
	deleteConfirm = confirm("Are you sure want to delete this entry?");
	if (deleteConfirm) {
		document.location.href = "holidaysDetails.do?method=deleteHolidays&id=" 
				+ id;
	}
}
function resetMessages() {
	resetFieldAndErrMsgs();
}
</script>
<html:form action="/holidaysDetails">	
	<html:hidden property="method" styleId="method"  value=""/>
	<html:hidden property="formName" value="holidaysForm" />
	<html:hidden property="pageType" value="1" />
	<html:hidden property="id" styleId="id" />
	<c:choose>
	<c:when test="${operation == 'edit'}">
			<html:hidden property="method" styleId="method"
				value="updateEmpHolidays" />
		</c:when>
		<c:otherwise>
			<html:hidden property="method" styleId="method"
				value="addEmpHolidays" />
		</c:otherwise>
		</c:choose> 
	
	<table width="98%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message key="knowledgepro.admin.sec.EmployeeCategory" />
			<span class="Bredcrumbs">&gt;&gt; <bean:message key="knowledgepro.emp.holidaysDetails.display" /> &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="10"><img src="images/Tright_03_01.gif"></td>
					<td width="100%" background="images/Tcenter.gif" class="body">
					<div align="left"><strong class="boxheader"><bean:message key="knowledgepro.emp.holidaysDetails.display" /></strong></div>
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
                <td class="row-odd" ><div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message key="employee.info.job.emptype"/></div></td>
                <td class="row-even" colspan="1">
                <html:select name="holidaysForm" property="empTypeId" styleId="empTypeId" styleClass="combo">
					<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
					<html:optionsCollection name="holidaysForm" property="empTypeList" label="empTypeName" value="empTypeId"/>
				</html:select> 
                </td>
				<td   class="row-odd" ><div align="right"> <bean:message key="knowledgepro.exam.examDefinition.year"/>:</div></td>
                <td  class="row-even">
					<input type="hidden" id="tempYear" name="tempYear" value="<bean:write name="holidaysForm" property="academicYear"/>"/>
                    <html:select name="holidaysForm" property="academicYear" styleId="academicYear" styleClass="combo" > 
                    	<html:option value=" "><bean:message key="knowledgepro.admin.select" /></html:option>
                     	<cms:renderFutureYear normalYear="true"></cms:renderFutureYear>
					</html:select>
				</td>                
			</tr>
            <tr>
            	<td   class="row-odd" ><div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message key="knowledgepro.feepays.startdate"/>:</div></td>
                <td  height="25" class="row-even" >
                	<table width="189" border="0" cellspacing="0" cellpadding="0">
                    	<tr>
                      		<td width="60">
                      			<html:text name="holidaysForm" property="startDate" styleId="startDate" size="10" maxlength="16"/> 
                      		</td>
                      		<td width="2">
                      			<script language="JavaScript">
									new tcal ({
										// form name
										'formname': 'holidaysForm',
										// input name
										'controlname': 'startDate'
									});
								</script>
							</td>
                    	</tr>
                	</table>
                </td>
			    <td  class="row-odd"><div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message key="knowledgepro.feepays.enddate"/>:</div></td>
                <td class="row-even">
                	<table width="188" border="0" cellspacing="0" cellpadding="0">
                  		<tr>
                    		<td ><html:text name="holidaysForm" property="endDate" styleId="endDate" size="10" maxlength="16"/> </td>
                    		<td >
                    			<script language="JavaScript">
									new tcal ({
									// form name
									'formname': 'holidaysForm',
									// input name
									'controlname': 'endDate'
									});
								</script>
							</td>
                  		</tr>
                	</table>
                </td>
			</tr>
            <tr>
                <td class="row-odd" ><div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message key="knowledgepro.emp.holidaysDetails.display.reason"/>:</div></td>
                <td class="row-even" colspan="3">
                	<span class="row-white">
                		<html:textarea property="holiday" name="holidaysForm" cols="28" rows="4" ></html:textarea>
                	</span>
                </td>
                <td>&nbsp;</td>
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
											<html:submit styleClass="formbutton" onclick="updateHolidays()">
												<bean:message key="knowledgepro.emp.exceptionDetails.update" />
											</html:submit>
										</c:when>
										<c:otherwise>
											<html:submit styleClass="formbutton" onclick="addEmpHolidays()">
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
										<html:cancel styleClass="formbutton" onclick="updateHolidays()">
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
											key="knowledgepro.emp.employeeType" /></div>
											</td>
											<td width="15%" class="row-odd">
											<div align="center"><bean:message
											key="knowledgepro.admin.academicyear" /></div>
											</td>
											<td width="10%" class="row-odd">
											<div align="center"><bean:message
											key="knowledgepro.feepays.startdate" /></div>
											</td>
											<td width="10%" class="row-odd">
											<div align="center"><bean:message
											key="knowledgepro.feepays.enddate" /></div>
											</td>
											<td width="9%" class="row-odd">
											<div align="center"><bean:message key="knowledgepro.edit" /></div>
											</td>
											<td width="8%" class="row-odd">
											<div align="center"><bean:message key="knowledgepro.delete" /></div>
											</td>
										</tr>
										<logic:notEmpty name="holidaysForm" property="holidaysTOsList">
										<logic:iterate id="hList" name="holidaysForm" property="holidaysTOsList"
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
											<td align="center"><bean:write name="hList"
												property="empTypeName" /></td>
											<td align="center"><bean:write name="hList"
												property="academicYear" /></td>
											<td align="center"><bean:write name="hList"
												property="startDate" /></td>
											<td align="center"><bean:write name="hList"
												property="endDate" /></td>	
											<td height="25" align="center">
											<div align="center"><img src="images/edit_icon.gif"
													width="16" height="18" style="cursor:pointer"
													onclick="editHolidays('<bean:write name="hList" property="id" />')" /></div>
											</td>
											<td height="25">
											<div align="center"><img src="images/delete_icon.gif"
													width="16" height="16" style="cursor:pointer"
													onclick="deleteHolidays('<bean:write name="hList" property="id" />')" /></div>
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
<script type="text/javascript">
	var year = document.getElementById("tempyear").value;
	if (year.length != 0) {
		document.getElementById("academicYear").value = year;
	}
</script>