<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@page import="java.util.Map,java.util.HashMap"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<script type="text/javascript">
	
	function getExamName(examType) {
		var year = document.getElementById("year").value;
		getExamNameByExamTypeYearWise("examMap", examType,year, "examName", updateExamName);
		resetOption("subject");
	}
	function updateExamName(req) {
		updateOptionsFromMap(req, "examName", "- Select -");
		updateCurrentExam(req, "examName");
		if(document.getElementById("examName").value != null && document.getElementById("examName").value != ""){
			getCourse(document.getElementById("examName").value);
		}
	}
	function getExamNameByYear(year) {
		var examType = "";
		if(document.getElementById("regular").checked){
			examType = document.getElementById("regular").value;
		}
		if(document.getElementById("sup").checked){
			examType = document.getElementById("sup").value;
		}
		if(document.getElementById("int").checked){
			examType = document.getElementById("int").value;
		}
		getExamNameByExamTypeYearWise("examMap", examType,year, "examName", updateExamName);
		resetOption("subject");
	}
	
	function cancelAction() {
		document.location.href = "valuationStatus.do?method=initValuationStatus";
	}
	function getCourse(examName) {
		getCourseByExamName("schemeMap", examName, "course", updateToCourse);

	}
	function updateToCourse(req) {
		updateOptionsFromMap(req, "course", "- Select -");

	}
	function resetFields() {
		document.getElementById("method").value="resetForValuationStatus";
		document.examValuationStatusForm.submit();
	}
</SCRIPT>



<html:form action="/valuationStatus">
	<html:hidden property="formName" value="examValuationStatusForm" styleId="formName" />
	<html:hidden property="method" styleId="method" value="displayValuationStatus" />
	<html:hidden property="pageType" value="1" styleId="pageType" />
	<input type="hidden" id="typeExam" name="type" value='<bean:write name="examValuationStatusForm" property="examType"/>' />
	<table width="99%" border="0">
		<tr>
			<td><span class="heading"><span class="Bredcrumbs">Exam
			&gt;&gt; Exam Valuation Status &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="heading_white">Exam Valuation Status</td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td height="20" class="news">

					<div align="right"><FONT color="red"> <span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields"/></span></FONT></div>
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
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="heading">&nbsp;</td>
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

									<td height="25" colspan="4" class="row-even">
									<div align="Center"><html:radio property="examType"
										styleId="regular" value="Regular"
										onclick="getExamName(this.value)"></html:radio>
									Regular&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<html:radio property="examType" value="Supplementary"
										styleId="sup"
										onclick="getExamName(this.value)"></html:radio>
									Supplementary&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<html:radio property="examType" value="Internal" styleId="int"
										onclick="getExamName(this.value)"></html:radio>
									Internal</div>
									</td>
								</tr>
								<tr>
									<td class="row-odd"><div align="right"><bean:message key="knowledgepro.fee.academicyear"/>:</div></td>
		                            <td class="row-even" align="left">
		                            <input
										type="hidden" id="tempyear" name="appliedYear"
										value="<bean:write name="examValuationStatusForm" property="academicYear"/>" />
		                            <html:select property="academicYear" styleId="year" styleClass="combo" onchange="getExamNameByYear(this.value)">
	                     	   					 <html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
	                     	   					 <cms:renderAcademicYear></cms:renderAcademicYear>
	                     			   		</html:select>
		                            </td>
									<td class="row-odd" >
									<div align="right"><span class="Mandatory">*</span>
									Exam Name :</div>
									</td>

									<td class="row-even"><html:select
										name="examValuationStatusForm" property="examId"
										styleId="examName" styleClass="comboExtraLarge" onchange="getCourse(this.value)">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
											<logic:notEmpty name="examValuationStatusForm"
												property="examNameList">
												<html:optionsCollection property="examNameList"
													name="examValuationStatusForm" label="value" value="key" />
											</logic:notEmpty>
									</html:select></td>
								</tr>
								<tr>
									<td class="row-odd">
									<div align="right"><span class="Mandatory">*</span>&nbsp; Semester</div>
									</td>
									<td class="row-even">
									<html:select property="termNumber" styleId="termNumber">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<html:optionsCollection name="TermNumberMap" label="value" value="key" />
									</html:select>
									</td>
									<td height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message
										key="knowledgepro.admin.course.with.col" /></div>
									</td>
									<td height="25" class="row-even">
										<html:select property="courseId" styleClass="comboExtraLarge" styleId="course"
														name="examValuationStatusForm"	style="width:200px">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<logic:notEmpty name="examValuationStatusForm" property="courceList">
											<html:optionsCollection property="courceList"
												name="examValuationStatusForm" label="value" value="key" />
										</logic:notEmpty>
									</html:select></td>
									<!--<td class="row-odd">
									
									</td>
									<td class="row-even"></td>
								--></tr>
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
						<tr>
							<td width="20%" height="35" align="center">
							
							<html:submit value="Submit" styleClass="formbutton"></html:submit>
										&nbsp;&nbsp;
							<html:button property="" styleClass="formbutton" value="Reset" onclick="resetFields()" ></html:button>
							
							</td>
						</tr>
						
					</table>
					</td>
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
<script>
var year = document.getElementById("tempyear").value;
if(year.length != 0) {
 	document.getElementById("year").value=year;
}
</script>

