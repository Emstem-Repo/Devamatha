<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@page import="java.util.Map,java.util.HashMap"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>

<script type="text/javascript" language="javascript">
	function getExamNames(processType) {

		
		if (processType == "5") {
			document.getElementById("academicYear").disabled = false;
			document.getElementById("examFeePaidCheck").disabled = false;
			document.getElementById("promotionCritariaCheck").disabled = false;
			document.getElementById("examId").disabled = true;
		} else {
			document.getElementById("academicYear").disabled = true;
			document.getElementById("examId").disabled = false;
			if (processType == "4") {
				document.getElementById("academicYear").disabled = false;
				document.getElementById("examId").disabled = true;
			}
			document.getElementById("examFeePaidCheck").checked = false;
			document.getElementById("promotionCritariaCheck").checked = false;
			document.getElementById("examFeePaidCheck").disabled = true;
			document.getElementById("promotionCritariaCheck").disabled = true;

		}
	
		// Functions for AJAX 
		getExamNameByProcessType("examMap", processType, "examId",
					updateExamName);
		}
		function updateExamName(req) {
			updateOptionsFromMap(req, "examId", "- Select -");
		}



	function resetValues() {
		document.location.href = "ExamUpdateProcess.do?method=initExamUpdateProcess";
	}
</script>
<html:form action="/ExamUpdateProcess.do">

	<html:hidden property="formName" value="ExamUpdateProcessForm"
		styleId="formName" />
	<html:hidden property="pageType" value="1" styleId="pageType" />



	<html:hidden property="method" styleId="method" value="fetchDetails" />
	<table width="100%" border="0">
		<tr>
			<td><span class="heading">Exams<span class="Bredcrumbs">&gt;&gt;
			Update Process &gt;&gt;</span></span></td>
		</tr>

		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="heading_white">Exam
					Update Process</td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>

				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td height="20" class="news">
					<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
					<FONT color="green"> <html:messages id="msg"
						property="messages" message="true">
						<c:out value="${msg}" escapeXml="false"></c:out>
						<br>
					</html:messages> </FONT></div>
					<div align="right" class="mandatoryfield">*Mandatory fields</div>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news">
					</td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
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

									<td class="row-odd" width="25%">
									<div align="right"><span class="mandatoryfield">*</span>Process:</div>
									</td>
									<td class="row-even" width="25%"><html:select property="process"
										styleId="process" onchange="getExamNames(this.value)">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<html:option value="1">Calculate Overall regular Exam marks</html:option>
										<html:option value="2">Calculate Overall Internal marks</html:option>
										<html:option value="3">Supplementary data creation</html:option>
										<html:option value="4">Update Detention</html:option>
									</html:select></td>


									<td height="25" class="row-odd" width="25%">
									<div align="right">
									<DIV align="right"><span class="Mandatory">*</span>Exam
									Name :</DIV>
									</div>
									</td>
									<td height="25" class="row-even" width="50%"><html:select
										property="examId" styleClass="body" styleId="examId"
										disabled="true">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
<c:if
													test="${ExamUpdateProcessForm.process != null && ExamUpdateProcessForm.process != ''}">
													<c:set var="examMap"
														value="${baseActionForm.collectionMap['examMap']}" />
													<c:if test="${examMap != null}">
														<html:optionsCollection name="examMap" label="value"
															value="key" />
													</c:if>
												</c:if>

										

									</html:select></td>
								</tr>
								<tr>
									<td class="row-odd">
									<div align="right">Academic Year:</div>
									</td>

									<td class="row-even" ><html:select
										property="academicYear" styleClass="body"
										styleId="academicYear" disabled="true">
										<cms:renderYear></cms:renderYear>

									</html:select></td>
									
									<td width="19%" height="25" class="row-even">
										<input type="radio" name="isPreviousExam" id="isPreviousExam_1" value="true"/> Previous
			                    		<input type="radio" name="isPreviousExam" id="isPreviousExam_2" value="false" checked="checked"/> Current
			    						<script type="text/javascript">
											var isPreviousExam = "<bean:write name='ExamUpdateProcessForm' property='isPreviousExam'/>";
											if(isPreviousExam == "true") {
							                        document.getElementById("isPreviousExam_1").checked = true;
											}	
										</script>
		    						</td>
		    						<td class="row-even">
									<div align="right">&nbsp;</div>
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
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td height="20" class="news">
					<div align="right" class="mandatoryfield"></div>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>

					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
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
									<td width="36%" class="row-odd">
									<div align="right">Check Promotional Criteria:</div>
									</td>
									<td width="14%" class="row-even"><html:checkbox
										property="promotionCritariaCheck" disabled="true"
										styleId="promotionCritariaCheck" /></td>
									<td width="32%" height="25" class="row-odd">
									<div align="right">
									<DIV align="right">Check Exam Fee Paid :</DIV>
									</div>
									</td>

									<td width="18%" height="25" class="row-even"><html:checkbox
										property="examFeePaidCheck" disabled="true"
										styleId="examFeePaidCheck" /><html:hidden styleId="process"
										property="process" /></td>
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
						<tr>
							<td width="45%" height="35" align="right"><input
								name="Submit2" type="submit" class="formbutton" value="Submit" /></td>

							<td width="2%" height="35" align="center">&nbsp;</td>
							<td width="45%" align="left"><input name="Submit3"
								type="button" class="formbutton" value="Cancel"
								onclick="resetValues()" /></td>
						</tr>
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
	<script>
	var processType = document.getElementById("process").value;
	if (processType == "5") {
		document.getElementById("academicYear").disabled = false;
		document.getElementById("examFeePaidCheck").disabled = false;
		document.getElementById("promotionCritariaCheck").disabled = false;
		document.getElementById("examId").disabled = true;
	} else {
		document.getElementById("academicYear").disabled = true;
		document.getElementById("examId").disabled = false;
		if (processType == "4") {
			document.getElementById("academicYear").disabled = false;
			document.getElementById("examId").disabled = true;
		}
		document.getElementById("examFeePaidCheck").checked = false;
		document.getElementById("promotionCritariaCheck").checked = false;
		document.getElementById("examFeePaidCheck").disabled = true;
		document.getElementById("promotionCritariaCheck").disabled = true;

	}
</script>

</html:form>