<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
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
function getExamsByExamTypeAndYear() {
	var examType=document.getElementById("examType").value;
	var year=document.getElementById("year").value;
	getExamNameByExamTypeAndYear("examMap", examType,year, "examId", updateExamName);
}
function getExamName() {
	if(document.getElementById("examType").checked==true)
		var examType=document.getElementById("examType").value;
	else if(document.getElementById("sup").checked==true)
		var examType=document.getElementById("sup").value;
	else if(document.getElementById("intRedo").checked==true)
		var examType=document.getElementById("intRedo").value;	
	var year=document.getElementById("year").value;
	getExamNameByExamTypeAndYear("examMap", examType,year, "examId", updateExamName);
}
function updateExamName(req) {
	updateOptionsFromMap(req, "examId", "- Select -");
	updateCurrentExam(req, "examId");
}

function resetMessages() {
	 resetFieldAndErrMsgs();
}
function cancel(){
	document.location.href = "LoginAction.do?method=loginAction";
}
</script>
<html:form action="/extendSupplyImprApplDate" >	
	<html:hidden property="method" styleId="method" value="getExamsToExtend" />
	<html:hidden property="formName" value="extendSupplyImprovApplDateForm" />
	<html:hidden property="pageType" value="1" />
	<table width="100%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message key="knowledgepro.exam"/>
			<span class="Bredcrumbs">&gt;&gt;
			 Extend Supplementary Improvement Application Date
			 &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td valign="top">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader">  Extend Supplementary Improvement Application Date</strong></td>

					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="20" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<div align="right"><FONT color="red"> <span
						class='MandatoryMark'><bean:message
						key="knowledgepro.mandatoryfields" /></span></FONT></div>
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
						<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
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
											<td align="center" height="25" colspan="4" class="row-even">
												<table>
													<tr>
														<td align="right" width="40%">
															<html:radio property="examType"
																		styleId="examType" 
																		value="Regular"
																		onclick="getExamName()" ></html:radio>
															Regular
														</td>
														<td align="center" width="20%">
															<html:radio property="examType" 
																		value="Supplementary"
																		styleId="sup"
																		onclick="getExamName()"  ></html:radio>
															Supplementary
														</td>
														<td width="40%">
															<html:radio property="examType" 
																		value="5"
																		styleId="intRedo"
																		onclick="getExamName()"  ></html:radio>
															Internal Redo
														</td>
													</tr>
												</table>							
											</td>							
										</tr>
										<tr>
											<td width="25%" height="25" class="row-odd"><div align="right"><span class="Mandatory">*</span> <bean:message
														key="knowledgepro.admin.year" /> :</div>
											</td>
											<td width="25%" class="row-even" valign="top">
												<html:select property="year" styleId="year"	styleClass="combo" onchange="getExamName()">
													<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
													<cms:renderAcademicYear></cms:renderAcademicYear>
												</html:select>
											</td>
											<td width="25%" class="row-odd">
												<div align="right"><span class="Mandatory">*</span> Exam Name :</div>
											</td>
											<td width="25%" class="row-even">
												<html:select property="examId"	styleClass="combo" styleId="examId" name="extendSupplyImprovApplDateForm" style="width:200px">
													<html:option value="">
														<bean:message key="knowledgepro.admin.select" />
													</html:option>
													<logic:notEmpty name="extendSupplyImprovApplDateForm" property="examMap">
														<html:optionsCollection property="examMap" name="extendSupplyImprovApplDateForm" label="value" value="key" />
													</logic:notEmpty>
												</html:select>
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
						<tr><td style="height: 10px" align="center" colspan="4"></td></tr>
						<tr>
							<td  height="35" align="right" width="50%">
									<html:submit property="" styleClass="formbutton" value="Submit"
										styleId="submitbutton">
									</html:submit>
								
							</td>
							<td   align="left" width="1%"></td>
							<td   align="left" width="5%">
									<html:button property="" styleClass="formbutton" value="Reset" onclick="resetMessages()"></html:button>
							</td>
							<td   align="left" width="1%"></td>							
							<td  height="35" align="left" width="40%">
									<html:button property="" styleClass="formbutton" value="Close" onclick="cancel()"> </html:button>
							</td>
						</tr>						
						<tr><td style="height: 10px" align="center" colspan="4"></td></tr>
					</table>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
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
