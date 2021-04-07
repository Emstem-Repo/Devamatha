<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<script type="text/javascript">
function editDocExam(id){
	document.location.href = "examEndDate.do?method=editExamEndDateId&id="+ id;
}
function deleteDocExam(id){
	deleteConfirm = confirm("Are you sure you want to delete this entry?");
	if (deleteConfirm) {
		document.location.href = "examEndDate.do?method=deleteOrReactivateExamEndDate&mode=delete&id="+ id;
	}
}
function reActivate(){
	document.getElementById("method").value="deleteOrReactivateExamEndDate";
	document.examEndDateForm.submit();
}
function resetFormFields(){
	resetFieldAndErrMsgs();
}
function getExamsByYear(year) {
	getExamNameByAcademicYear("optionMap",year, "examNameId", updateExamName);
}
function updateExamName(req) {
	updateOptionsFromMap(req, "examNameId", "- Select -");
}
</script>
<html:form action="/examEndDate.do">	
	<c:choose>
		<c:when test="${operation == 'edit'}">
		<html:hidden property="mode" styleId="mode" value="update" />
		</c:when>
		<c:otherwise>
		<html:hidden property="mode" styleId="mode" value="add" />
		</c:otherwise>
	</c:choose>
	<html:hidden property="method" styleId="method" value="addOrUpdatePublish" />
	<html:hidden property="formName" value="examEndDateForm" />
	<html:hidden property="pageType" value="1" />
	<table width="100%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message key="knowledgepro.exam"/>
			<span class="Bredcrumbs">&gt;&gt;
			Exam End Dates Entry
			 &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td valign="top">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> Exam End Dates Entry</strong></td>

					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="20" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<div align="right"><FONT color="red"> <span class='MandatoryMark'>Exam End Dates Application</span></FONT></div>
					<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
					<FONT color="green"> <html:messages id="msg"
						property="messages" message="true">
						<c:out value="${msg}" escapeXml="false"></c:out>
						<br>
					</html:messages> </FONT></div>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td height="41" valign="top" background="images/Tright_03_03.gif"></td>

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
							<td height="25" class="row-odd"><div align="right"><span class="Mandatory">*</span> <bean:message
										key="knowledgepro.admin.select.year" /> :</div></td>
									<td width="16%" class="row-even" valign="top">
									<input
										type="hidden" id="tempyear" name="appliedYear"
										value="<bean:write name="examEndDateForm" property="year"/>" />
									<html:select 
										property="year" styleId="year"
										styleClass="combo" onchange="getExamsByYear(this.value)">
										<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
										<cms:renderAcademicYear></cms:renderAcademicYear>
									</html:select></td>
									<td class="row-odd">
									<div align="right"><span class="Mandatory">*</span> Exam
									Name :</div>
									</td>

									<td class="row-even"><html:select property="examId"
										styleClass="combo" styleId="examNameId" name="examEndDateForm" style="width:200px">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<c:choose>
										<c:when test="${optionMap!=null}">
										<html:optionsCollection 
												name="optionMap" label="value" value="key" />
										</c:when>
										<c:otherwise>
										<logic:notEmpty name="examEndDateForm" property="examNameList">
											<html:optionsCollection property="examNameList"
												name="examEndDateForm" label="value" value="key" />
										</logic:notEmpty>
										</c:otherwise>
										</c:choose>
									</html:select></td>
							</tr>
								<tr>
									<td height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message key="knowledgepro.employee.last.date" />:</div>
									</td>
									<td class="row-even">
									<html:text name="examEndDateForm" property="endDate" styleId="endDate" size="10" maxlength="16"/>
							<script
							language="JavaScript">
							new tcal( {
								// form name
								'formname' :'examEndDateForm',
								// input name
								'controlname' :'endDate'
							});
						</script>
									</td>
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
					<td height="35" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
									<c:choose>
							<c:when test="${operation == 'edit'}">
							<td width="45%" height="35">
							<div align="right">
									<html:submit property="" styleClass="formbutton" value="Update"
										styleId="submitbutton">
									</html:submit>
								</div>
							</td>
							<td width="2%"></td>
							<td width="53%"><html:cancel value="Reset" styleClass="formbutton" ></html:cancel></td>
							</c:when>
							<c:otherwise>
								<td width="45%" height="35">
							<div align="right">
									<html:submit property="" styleClass="formbutton" value="Submit"
										styleId="submitbutton">
									</html:submit>
								</div>
							</td>
							<td width="2%"></td>
							<td width="53%"><html:button property=""
							styleClass="formbutton" value="Reset"
							onclick="resetFormFields()"></html:button></td>
							</c:otherwise>
							</c:choose>
						</tr>
					</table>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>	
				<tr>
							<td height="25" colspan="6">
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
											<td width="5%" height="25" class="row-odd">
											<div align="center"><bean:message key="knowledgepro.slno" /></div>
											</td>
											<td width="25%" class="row-odd">
											<div align="center"><bean:message
											key="knowledgepro.exam.examDefinition.examName" /></div>
											</td>
											<td width="25%" class="row-odd">
											<div align="center"><bean:message
											key="knowledgepro.employee.last.date" /></div>
											</td>
											<td  width="8%" class="row-odd">
											<div align="center"><bean:message key="knowledgepro.edit" /></div>
											</td>
											<td width="8%" class="row-odd">
											<div align="center"><bean:message
										key="knowledgepro.delete" /></div>
											</td>
										</tr>
										<logic:notEmpty name="examEndDateForm" property="toList">
										<logic:iterate id="dList" name="examEndDateForm" property="toList" indexId="count">
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
											<td align="center"><bean:write name="dList" property="examName" /></td>
											<td align="center"><bean:write name="dList" property="endDate" /></td>
											<td height="25" align="center">
											<div align="center"><img src="images/edit_icon.gif"
													width="16" height="18" onclick="editDocExam('<bean:write name="dList" property="id" />')" /></div>
											</td>
											<td height="25">
											<div align="center"><img src="images/delete_icon.gif"
													width="16" height="16" onclick="deleteDocExam('<bean:write name="dList" property="id" />')" /></div>
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
						</tr>			
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>

					<td valign="top" class="news">&nbsp;</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
					<td width="0" background="images/TcenterD.gif"></td>
					<td><img src="images/Tright_02.gif" width="9" height="29"></td>
				</tr>
			</table>
			</td>

		</tr>
	</table>
</html:form>
<script type="text/javascript">
var year = document.getElementById("").value;
if(year.length != 0) {
 	document.getElementById("year").value=year;
}
</script>