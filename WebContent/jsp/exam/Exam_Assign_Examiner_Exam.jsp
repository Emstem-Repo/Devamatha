<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<script language="JavaScript" src="js/ajax/AjaxUtil.js"></script>
<script language="JavaScript" src="js/ajax/Ajax.js"></script>
<script type="text/javascript"> 
function getExamsByExamTypeAndYear() {
	var examType=document.getElementById("examType").value;
	var year=document.getElementById("year").value;
	getExamNameByExamTypeAndYear("examMap", examType,year, "examName", updateExamName);
}
function updateExamName(req) {
	updateOptionsFromMap(req, "examName", "- Select -");
	updateCurrentExam(req, "examName");
}
</script>
<html:form action="AssignExaminerToExam.do" method="POST"
	enctype="multipart/form-data">
	<html:hidden property="formName" value="ExamAssignExaminerToExamForm"
		styleId="formName" />
	<html:hidden property="pageType" value="1"  />
	
	
			<html:hidden property="method" styleId="method"
				value="assignExaminer" />
		


	<table width="100%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message
				key="knowledgepro.exam" /> <span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.exam.assignExaminerToExam" /> &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td valign="top" class="news"></td>
		</tr>
		<tr>
			<td valign="top">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="10"><img src="images/Tright_03_01.gif" width="9"
						height="29" /></td>
					<td background="images/Tcenter.gif" class="body">
					<div align="left"><strong class="boxheader"> <bean:message
						key="knowledgepro.exam.assignExaminerToExam" /></strong></div>
					</td>
					<td width="13"><img src="images/Tright_1_01.gif" width="9"
						height="29" /></td>
				</tr>
				<tr>
					<td height="122" valign="top" background="images/Tright_03_03.gif"></td>
					<td width="100%" valign="top" class="news">
					<table width="100%" height="211" border="0" cellpadding="0"
						cellspacing="0">
						<tr>
							<td height="25" colspan="6" class="mandatoryfield"></td>
						</tr>
						<tr>
							<td></td>
							<td>
							<div align="right"><FONT color="red"> <span
								class='MandatoryMark'>* Mandatory fields</span> </FONT></div>
							<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
							<FONT color="green"> <html:messages id="msg"
								property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
								<br>
							</html:messages> </FONT></div>
							</td>
						</tr>
						<tr>
							<td height="35" colspan="6" valign="top" class="body">
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
										key="knowledgepro.admin.year" /> :</div></td>
									<td width="16%" class="row-even" valign="top">
									<input
										type="hidden" id="tempyear" name="appliedYear"
										value="<bean:write name="ExamAssignExaminerToExamForm" property="year"/>" />
									<html:select 
										property="year" styleId="year"
										styleClass="combo" onchange="getExamsByExamTypeAndYear()">
										<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
										<cms:renderAcademicYear></cms:renderAcademicYear>
									</html:select></td>
							<td height="25" class="row-odd"><div align="right"><span class="Mandatory">*</span> <bean:message
										key="knowledgepro.exam.examDefinition.examType" /> :</div></td>
								<td height="25" class="row-even"><html:select
										property="examType" styleClass="combo"  styleId="examType" onchange="getExamsByExamTypeAndYear()">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<logic:notEmpty property="examTypeList"
											name="ExamAssignExaminerToExamForm">
											<html:optionsCollection property="examTypeList"
												name="ExamAssignExaminerToExamForm" label="value" value="key" />
										</logic:notEmpty>
									</html:select></td>
									</tr>
										<tr>
											<td  height="25" class="row-odd">
											<div align="right"><span class="Mandatory">*</span> <bean:message
												key="knowledgepro.exam.examDefinition.examName" />
												 <span class="star"></span>:</div>
											</td>
											<td  height="25" class="row-even"><span
												class="star"> 
											<html:select property="examName" styleClass="combo"
												styleId="examName" name="ExamAssignExaminerToExamForm"
												style="width:200px">
												<html:option value="">
													<bean:message key="knowledgepro.admin.select" />
												</html:option>
												<logic:notEmpty name="ExamAssignExaminerToExamForm" property="examNameMap">
											<html:optionsCollection name="ExamAssignExaminerToExamForm" property="examNameMap" label="value"
													value="key" /></logic:notEmpty>
											</html:select>
											</span></td>
											<td class="row-odd"></td>
											<td class="row-even"></td>
										</tr>
									
									</table>
									</td>
									<td width="5" height="30" background="images/right.gif"></td>
								</tr>
								<tr>
									<td height="5"><img src="images/04.gif" width="5"
										height="5" /></td>
									<td background="images/05.gif"></td>
									<td><img src="images/06.gif" /></td>
								</tr>
							</table>
							</td>
						</tr>
						<tr>
							<td height="35" colspan="6" valign="top" class="body">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr><td style="height: 10px" align="center" colspan="1"></td></tr>
								<tr> 
									<td width="47%" height="35 ">
									<div align="center">
											<html:submit styleClass="formbutton" styleId="button">
												<bean:message key="knowledgepro.continue" />
											</html:submit>
										</div>
									</td>
									
								</tr>								
								<tr><td style="height: 10px" align="center" colspan="1"></td></tr>								
							</table>
							</td>
						</tr>
						
						
					</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td height="20" valign="top"></td>
					<td width="13" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
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
</html:form>
<script type="text/javascript">
var year = document.getElementById("tempyear").value;
if(year.length != 0) {
 	document.getElementById("year").value=year;
}
</script>