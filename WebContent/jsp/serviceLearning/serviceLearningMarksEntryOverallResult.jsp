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
function resetFormFields(){	
	document.getElementById("studentId").value = null;
	
}

function printServiceLearning(studentId) {
	
	var url = "serviceLearningMarksEntry.do?method=printOverallMarkByStudentId&studentId="
				+ studentId;
	myRef = window
	.open(url, "MarksCard",
			"left=20,top=20,width=900,height=900,toolbar=1,resizable=0,scrollbars=1");

}

function getCourses(programname) {
	getCoursesByProgramTypes("courseMap", programname, "courseId", updateProgrammes);
}
function updateProgrammes(req) {
	updateOptionsFromMap(req, "courseId", "- Select -");
}
function submitForm(grivNo) {	
	//alert("grivNo is  "+grivNo);
	var url="serviceLearningMarksEntry.do?method=showOverallMarkDetails&studentId="+grivNo;
	myRef = window
	.open(url, "MarksCard",
			"left=20,top=20,width=900,height=900,toolbar=1,resizable=0,scrollbars=1");
}



</script>
<html:form action="/serviceLearningMarksEntry">	

	<html:hidden property="formName" value="serviceLearningMarksEntryForm" />
	
	

		<html:hidden property="method" styleId="method" value="getOverallMarkByStudentId" />
		
		
	
	<table width="100%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message key="knowledgepro.admin"/>
			<span class="Bredcrumbs">&gt;&gt;
		Service Learning
			 &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td valign="top">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> Service Learning Overall Mark</strong></td>

					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="20" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
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
							<td width="16%" height="25" class="row-odd">
							<div align="right"><span class="Mandatory">*</span>Applied Year:</div>
							</td>
							<td width="16%" class="row-even" valign="top">
									<input
										type="hidden" id="tempyear" name="appliedYear"
										value="<bean:write name="serviceLearningMarksEntryForm" property="year"/>" />
									<html:select 
										property="year" styleId="year"
										styleClass="combo" >
										<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
										<cms:renderAcademicYear></cms:renderAcademicYear>
									</html:select></td>
									<td width="20%" height="25" class="row-odd">
							<div align="right"><span class="Mandatory">*</span><bean:message
								key="knowledgepro.admin.program.Type" /></div>
							</td>
							<td width="26%" height="25" class="row-even"><span
								class="star"> <html:select property="programTypeId"
								styleClass="comboLarge" styleId="programTypeId" onchange="getCourses(this.value)">
								<html:option value="">
									<bean:message key="knowledgepro.admin.select" />
								</html:option>
								<html:optionsCollection property="programTypeList" name="serviceLearningMarksEntryForm"
									label="programTypeName" value="programTypeId" />
							</html:select></span></td>
								<td width="20%" height="25" class="row-odd">
							<div align="right"><span class="Mandatory">*</span>Course: </div>
							</td>	
								<td width="26%" height="25" class="row-even"><span
								class="star"> <html:select property="courseId"
								styleClass="comboLarge" styleId="courseId">
								<html:option value="">
									<bean:message key="knowledgepro.admin.select" />
								</html:option>
								<logic:notEmpty property="courseMap" name="loginform">
								<html:optionsCollection property="courseMap" name="serviceLearningMarksEntryForm"
									label="name" value="id" />
									</logic:notEmpty>
							</html:select></span></td>
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
							<td width="45%" height="35">
							<div align="right">
							
								<html:submit property="" styleClass="formbutton" value="Submit"
										styleId="submitbutton">
									</html:submit>
							
								</div>
							</td>
							<td width="2%"></td>
							<td width="53%">
							<html:button property=""
							styleClass="formbutton" value="Reset"
							onclick="resetFormFields()"></html:button>
							</td>
							
						</tr>
					</table>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				<logic:notEmpty property="serviceLearningmarksOverallEntryToList" name="serviceLearningMarksEntryForm"> 
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
							<td valign="top" height="25">
							<table width="100%" cellspacing="1" cellpadding="0">
								<tr height="25">

									<td class="row-odd" height="25">
									<div align="center">Sl.No</div>
									</td>
									<td class="row-odd" height="25">Name</td>
									<td  class="row-odd" height="25">Register No</td>
									<td class="row-odd" height="25">Total Credit</td>
									<td class="row-odd" height="25">Details</td>
									
									
								</tr>
								
								<logic:iterate id="sList" property="serviceLearningmarksOverallEntryToList" name="serviceLearningMarksEntryForm"
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
											<td height="25" width="10%">
											<div align="center"><c:out value="${count + 1}" /></div>
											</td>
							<td width="13%" height="39" >&nbsp;<bean:write name="sList" property="studentName" /></td>
                            <td width="19%" >&nbsp;<bean:write property="registerNo" name="sList"/></td>
                            <td width="8%" >&nbsp;<bean:write property="overallMark" name="sList"/></td>
                           <td width="12%"  >&nbsp;
                            
                      <img src="images/detail_icon.jpg"
													width="100" height="30"
													onclick="submitForm('<bean:write name="sList" property="studentId" />')" alt="img not found" />
													
													
													</td>
                          
								
											</tr>
											</logic:iterate>
											
							
					
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
					<td height="35" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="45%" height="35">
							<div align="center">
							
								<input type="button" class="formbutton" value="Print"
										 onclick="printServiceLearning('<bean:write name="serviceLearningMarksEntryForm" property="studentId" />')"/ >
									
							
								</div>
							</td>
							
						</tr>
					</table>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				
				</logic:notEmpty>
				
						
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
