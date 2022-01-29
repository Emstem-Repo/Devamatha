<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>    
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>

<head>
<script type="text/javascript">
function redirectControl() {
	document.location.href = "evaluateFacultyFeedback.do?method=initEvaluateFacultyFeedback";
}
function getClasses(year) {
	getClassesForEvaluateFacultyFeedback("classMap", year, "class", updateClasses);
}
function updateClasses(req) {
	updateOptionsFromMap(req, "class", "- Select -");
}
function validate()
{
	var error="";
	if(document.getElementById("academicYear").value=="")
		error="Academic Year required <br>";
	if(document.getElementById("class").value=="")
		error+="Class is required<br>";		
	if(error=="")
	{
		return true;
	}
	else
	{
		document.getElementById("errorMessage").innerHTML=error;
		return false;
	}	
}
</script>
</head>
<html:form action="/evaluateFacultyFeedback" method="POST">
<html:hidden property="method" styleId="method" value="evaluateFacultyFeedback" />
<html:hidden property="formName" value="evaluateFacultyFeedbackForm" />
<html:hidden property="pageType" value="1" />
<table width="99%" border="0">  
  <tr>
    <td><span class="Bredcrumbs"><bean:message key="knowledgepro.facultyEvaluation"/> 
    <span class="Bredcrumbs">&gt;&gt; 
    <bean:message key="knowledgepro.evaluateFacultyFeedback"/> &gt;&gt;</span> </span></td>
  </tr>  
  <tr>
	<td valign="top">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
				<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> <bean:message key="knowledgepro.evaluateFacultyFeedback"/></strong></td>

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
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top">
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
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.interview.Year" /></div>
									</td>
									<td  class="row-even"><input type="hidden"
										id="yr" name="yr"
										value='<bean:write name="evaluateFacultyFeedbackForm" property="academicYear"/>' />
									<html:select property="academicYear" styleClass="combo" styleId="academicYear"
										onchange="getClasses(this.value)">
										<html:option value="">
											<bean:message key="knowledgepro.select" />-</html:option>
										<cms:renderAcademicYear></cms:renderAcademicYear>
									</html:select></td>
									<td  height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.evaluateFacultyFeedback.class" /></div>
									</td>
									<td   class="row-even"><input type="hidden"
										name="c1" id="c1"
										value='<bean:write name="evaluateFacultyFeedbackForm" property="classId"/>' />
									<html:select property="classId" styleClass="comboLarge"
										styleId="class">
										<html:option value="">
											<bean:message key="knowledgepro.select" />-</html:option>	
											<c:choose>
											<c:when test="${classMap != null}">
												<html:optionsCollection name="classMap" label="value"
													value="key" />
											</c:when>
											<c:otherwise>
												<c:if
													test="${evaluateFacultyFeedbackForm.classId != null && evaluateFacultyFeedbackForm.classId != ''}">
													<c:set var="classMap"
														value="${baseActionForm.collectionMap['classMap']}" />
													<c:if test="${classMap != null}">
														<html:optionsCollection name="classMap" label="value"
															value="key" />
													</c:if>
												</c:if>
											</c:otherwise>
										</c:choose>									
									</html:select></td>									
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
					<td valign="top" background="images/Tright_3_3.gif"></td>
				</tr>
				
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>

					<td valign="top" class="news">&nbsp;</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="heading">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td height="35" align="right">
								<html:submit property="" styleClass="formbutton" value="Evaluate"
										styleId="submitbutton" onclick="validate()">
									</html:submit></td><td>&nbsp;</td>
								<td width="53%"><html:button property="" styleClass="formbutton" value="Reset"
										onclick="redirectControl()" /></td>
						</tr>
						<tr><td height="35" align="center">&nbsp;</td></tr>
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