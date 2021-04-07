<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@page import="java.util.Map,java.util.HashMap"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<script language="JavaScript" src="js/ajax/AjaxUtil.js"></script>

<script type="text/javascript" language="javascript">
	function getExamName() {
		if(document.getElementById("examType").checked==true)
			var examType=document.getElementById("examType").value;
			else if(document.getElementById("sup").checked==true)
				var examType=document.getElementById("sup").value;
			
		var year=document.getElementById("year").value;
		getExamNameByExamTypeAndYear("examMap", examType,year, "examId", updateExamName);
	}
	function updateExamName(req) {
		updateOptionsFromMap(req, "examId", "- Select -");
		updateCurrentExam(req, "examId");
	}
</script>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html:form action="/examRevaluationOfflineApp">
	<html:hidden property="formName" value="examRevaluationOfflineAppForm" />
	<html:hidden property="method" styleId="method" value="getSubjectsForStudent" />
	<html:hidden property="pageType" value="1" />
	<table width="100%" border="0">
		<tr>
			<td><span class="heading"><bean:message
				key="knowledgepro.exam.exam" /> <span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.exam.revaluation.offline.app"/>&gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> <bean:message key="knowledgepro.exam.revaluation.offline.app"/></strong></td>

					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" align="center" cellpadding="0"
						cellspacing="0">
						<tr>
							<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
							<FONT color="green"> <html:messages id="msg"
								property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
								<br>
							</html:messages> </FONT></div>
							<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
							<td height="20" class="news">
							<div align="right" class="mandatoryfield"><bean:message
								key="knowledgepro.mandatoryfields" /></div>
							</td>
							<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
						</tr>
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
									<input type="hidden" id="tempyear" name="appliedYear"
										value="<bean:write name="examRevaluationOfflineAppForm" property="year"/>" />
									<html:select property="year" styleId="year"	styleClass="combo" onchange="getExamName()">
										<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
										<cms:renderAcademicYear></cms:renderAcademicYear>
									</html:select></td>
									<td height="25" colspan="6" class="row-even">
									<div align="Center">
									<html:radio property="examType"
										styleId="examType" value="Regular"
										onclick="getExamName()" ></html:radio>
									Regular&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<html:radio property="examType" value="Supplementary"
										styleId="sup"
										onclick="getExamName()" ></html:radio>
									Supplementary									
									</div>
									</td>

								</tr>
								<tr>
									<td width="24%" class="row-odd">
									<div align="right">
									<DIV align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.exam.examDefinition.examName" /> :</DIV>
									</div>
									</td>
									<td width="21%"  class="row-even">
									<html:select property="examId" styleClass="combo" styleId="examId" name="examRevaluationOfflineAppForm" style="width:200px" >
									<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
										<logic:notEmpty name="examRevaluationOfflineAppForm" property="examNameMap">
											<html:optionsCollection property="examNameMap" name="examRevaluationOfflineAppForm" label="value" value="key" />
										</logic:notEmpty>
									</html:select></td>
									<td width="22%" height="15" class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.hostel.reservation.registerNo" /></div>
									</td>
									<td width="36%" class="row-even"><html:text property="registerNo" size="10"></html:text>
									</td>
								</tr>
								<tr>
								<td width="24%" class="row-odd">
									<div align="right">
									<span class="Mandatory">*</span><bean:message
										key="knowledgepro.exam.ExamSpecializationSubjectgroup.SchemeNo" /> :
									</div>
									</td>
									<td class="row-even">
											<nested:select property="schemeNo" styleId="scheme1" styleClass="combo">
												<html:option value=" "><bean:message key="knowledgepro.admin.select" /></html:option>
												<html:option value="1">1</html:option>
												<html:option value="2">2</html:option>
												<html:option value="3">3</html:option>
												<html:option value="4">4</html:option>
												<html:option value="5">5</html:option>
												<html:option value="6">6</html:option>
												<html:option value="7">7</html:option>
												<html:option value="8">8</html:option>
												<html:option value="9">9</html:option>
												<html:option value="10">10</html:option>
											</nested:select>
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
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td height="10" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr><td style="height: 10px" align="center" colspan="3"></td></tr>
						<tr>
							<td width="45%" height="35" align="right"><html:submit value="Submit" styleClass="formbutton"></html:submit> </td>
							<td width="2%" height="35" align="center">&nbsp;</td>
							<td width="45%" align="left"><html:button property="" value="Reset" onclick="resetFieldAndErrMsgs()" styleClass="formbutton"></html:button> &nbsp; 
								</td>
						</tr>						
						<tr><td style="height: 10px" align="center" colspan="3"></td></tr>						
					</table>
					</td>
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
<script language="JavaScript" >
	var year = document.getElementById("tempyear").value;
	if(year.length != 0) {
	 	document.getElementById("year").value=year;
	}
</script>