<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@page import="java.util.Map,java.util.HashMap"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<SCRIPT>
	function goToFirstPage() {
		document.location.href = "moderationMarksEntry.do?method=initModeration";
	}
	function getExamName() {
		var year=document.getElementById("year").value;
		var examType=document.getElementById("examType").value;
		getExamNameByExamTypeAndYear("examMap", examType,year, "examName", updateExamName);
	}
	function updateExamName(req) {
		updateOptionsFromMap(req, "examName", "- Select -");
	}	
</SCRIPT>


<html:form action="/moderationMarksEntry" method="POST" enctype="multipart/form-data">

	<html:hidden property="method" styleId="method" value="initializeData" />
	<html:hidden property="formName" value="moderationMarksEntryForm" styleId="formName" />
	<html:hidden property="pageType" value="1" styleId="pageType" />
	<table width="99%" border="0">
		<tr>
			<td><span class="heading"><span class="Bredcrumbs">Exam
			&gt;&gt; Student Moderation/Revaluation &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="heading_white">Moderation/Revaluation</td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="heading">
					<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
					<FONT color="green"> <html:messages id="msg"
						property="messages" message="true">
						<c:out value="${msg}" escapeXml="false"></c:out>
						<br>
					</html:messages> </FONT></div>
					<div align="right" class="mandatoryfield"><bean:message
						key="knowledgepro.mandatoryfields" /></div>
					</td>
					<td valign="top" background="images/Tright_3_3.gif"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="heading">
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
									<div align="right"><span class="Mandatory">*</span>
										<bean:message key="knowledgepro.fee.academicyear.col"/>:</div>
									</td>
									<td class="row-even" valign="top">
									<input
										type="hidden" id="tempyear" name="appliedYear"
										value="<bean:write name="moderationMarksEntryForm" property="academicYear"/>" />
									<html:select
										property="academicYear" styleId="year"
										styleClass="combo" onchange="getExamName()">
										<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
										<cms:renderAcademicYear></cms:renderAcademicYear>
									</html:select></td>
							 <td height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>&nbsp;Entry Type</div>
									</td>

									<td height="25" class="row-even">
								     <html:select property="entryType" styleId="entrytype" styleClass="combo" >
												<html:option value=" "><bean:message key="knowledgepro.admin.select" /></html:option>
												<html:option value="R">REVALUATION</html:option>
												<html:option value="M">MODERATION</html:option>
												
											</html:select>
									</td>
							 
							</tr>
								<tr>
									<td class="row-odd">
									<div align="right"><span class="Mandatory">*</span>Exam
									Type :</div>
									</td>
									<td class="row-even">
							        <html:select property="examType"
										styleId="examType"
										onchange="getExamName()"
										style="width:200px">
										<html:option value="">Select</html:option>
										<html:option value="Regular">Regular</html:option>
										<html:option value="Supplementary">Supplementary</html:option>
										<%-- <html:option value="Internal">Internal</html:option>--%>
									</html:select></td>
									<td class="row-odd">
									<div align="right"><span class="Mandatory">*</span> Exam
									Name :</div>
									</td>
									<td class="row-even"><html:select property="examId"
										styleClass="comboLarge" styleId="examName"
										name="moderationMarksEntryForm"  style="width:200px">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<logic:notEmpty name="moderationMarksEntryForm" property="examMap">
											<html:optionsCollection property="examMap" name="moderationMarksEntryForm" label="value" value="key"/>
										</logic:notEmpty>
									</html:select></td>
								</tr>
								<tr>
								<td width="28%" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>
									Scheme:</div>
									</td>
						                <td class="row-even">
											<html:select name="moderationMarksEntryForm" property="schemeNo" styleId="scheme1" styleClass="combo">
												<html:option value=" "><bean:message key="knowledgepro.admin.select" /></html:option>
												<html:option value="1">1</html:option>
												<html:option value="2">2</html:option>
												<html:option value="3">3</html:option>
												<html:option value="4">4</html:option>
												<html:option value="5">5</html:option>
												<html:option value="6">6</html:option>
											</html:select>
										</td>
								 <%-- 
								 <td width="28%" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>
									Class:</div>
									</td>
						                <td class="row-even">
											<html:select name="" property="classId" styleId="class" styleClass="combo" onchange="getClass(this.value)">
												<html:option value=" "><bean:message key="knowledgepro.admin.select" /></html:option>
											<logic:notEmpty name="moderationMarksEntryForm" property="classMap">
											   <html:optionsCollection property="classMap" name="moderationMarksEntryForm" label="value" value="key"/>
										    </logic:notEmpty>
											</html:select>
										</td>
                                 --%>

								</tr>
                            </table>
							</td>
							<td width="5" background="images/right.gif"></td>
						</tr>
						<tr>
							<td height="5"><img src="images/04.gif" width="5" height="5" /></td>
							<td background="images/05.gif"></td>
							<td><img src="images/06.gif" /></td>
						</tr>
					</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="heading">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr><td style="height: 10px" align="center" colspan="3"></td></tr>
						<tr>
							<td width="49%" height="35" align="right"><input
								name="Submit7" type="submit" class="formbutton" value="Submit" /></td>
							<td width="2%" height="35" align="center">&nbsp;</td>
							<td width="49%" height="35" align="left"><input
								name="Submit8" type="button" class="formbutton" value="Cancel"
								onclick="goToFirstPage()" /></td>
						</tr>						
						<tr><td style="height: 10px" align="center" colspan="3"></td></tr>						
					</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="heading"></td>
					<td valign="top" background="images/Tright_3_3.gif"></td>
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