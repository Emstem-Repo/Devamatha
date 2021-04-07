<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@page import="java.util.Map,java.util.HashMap"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<html>
<head>
<title>:: CMS ::</title>
<script type="text/javascript">
function getCourses(academicYear) {

	getCoursesByAcademicYear("coursesMap", academicYear, "course",
			updateCourses);
}
function updateCourses(req) {
	updateOptionsFromMap(req, "course", "- Select -");
}
</SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="js/selectbox.js"></SCRIPT>
<link href="css/styles.css" rel="stylesheet" type="text/css">

</head>

<body>
<html:form action="/durationAllocation.do">
	<html:hidden property="pageType" styleId="pageType" value="1" />
	<html:hidden property="formName" value="DurationAllocationForm" />
	<html:hidden property="method" styleId="method" value="fetchData" />


	<table width="99%" border="0">

		<tr>
			<td><span class="heading"><span class="Bredcrumbs">&gt;&gt;
			Duration Allocation &gt;&gt;</span></span></td>
		</tr>

		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="heading_white">Duration
					Allocation</td>
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
					<div align="right" class="mandatoryfield">*Mandatory fields</div>
					</td>
					<td valign="top" background="images/Tright_3_3.gif"></td>
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
									<td width="17%" height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>Academic Year :</div>
									</td>

									<td width="20%" class="row-even"><html:select
										property="academicYr" onchange="getCourses(this.value)">
										<cms:renderYear></cms:renderYear>
									</html:select></td>
									<td width="20%" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>Course :</div>
									</td>
									<td width="20%" class="row-even"><html:select
										property="course" styleId="course" style="width:200px">
										<html:option value="">Select</html:option>
										<c:if
											test="${DurationAllocationForm.academicYr != null && DurationAllocationForm.academicYr != ''}">
											<c:set var="courseMap"
												value="${baseActionForm.collectionMap['coursesMap']}" />
											<c:if test="${courseMap != null}">
												<html:optionsCollection name="courseMap" label="value"
													value="key" />
											</c:if>
										</c:if>
										<logic:notEmpty property="courseList" name="DurationAllocationForm">
										<html:optionsCollection property="courseList" value="key" label="value"/>
										</logic:notEmpty>
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
					<td class="heading">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="49%" align="right"><input name="Submit2"
								type="submit" class="formbutton" value="Continue" /></td>
							<td width="2%" height="35" align="center">&nbsp;</td>
							<td width="49%" height="35" align="left"><input
								name="Submit2" type="reset" class="formbutton" value="Reset"
								 /></td>
						</tr>
					</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif"></td>

				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="heading">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
					</table>
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
</body>
</html>

