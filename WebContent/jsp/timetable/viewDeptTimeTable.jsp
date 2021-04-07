<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<script type="text/javascript" src="js/auditorium/jquery-1.9.1.min.js"></script>
<link rel="stylesheet" type="text/css" href="css/tooltipster.css" />
<script type="text/javascript" src="js/jquery.tooltipster.js"></script>
<style>
.tooltipster-default {
	left: auto;
	text-align: left;
}
</style>
<html:form action="/viewMyTimeTable" method="post">
	<html:hidden property="method" styleId="method" value="viewDeptTimeTable" />
	<html:hidden property="formName" value="viewTeacherWiseTimeTableForm" />
	<html:hidden property="pageType" value="1" />
	<html:hidden property="userId" styleId="userId"
		name="viewTeacherWiseTimeTableForm" />
	<table width="100%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message
				key="knowledgepro.timetable.module" /> <span class="Bredcrumbs">&gt;&gt;
			Department Time Table&gt;&gt;</span></span></td>
		</tr>

		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="heading_white">Department
					Time Table</td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="heading"></td>
					<td valign="top" background="images/Tright_3_3.gif"></td>
				</tr>
				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td class="heading">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td>
							<div id="errorMessage" style="font-size: 12px; color: red;"></div>
							</td>
						</tr>
						<tr>
							<td align="center">
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
											<%-- <td height="25" class="row-odd">
											<div align="right"><span class="Mandatory">*</span><bean:message
												key="knowledgepro.interview.Year" /></div>
											</td>
											<td class="row-even"><input type="hidden" id="yr"
												name="yr"
												value='<bean:write name="viewTeacherWiseTimeTableForm" property="year"/>' />
											<html:select property="year" styleClass="combo"
												styleId="academicYear" onchange="getClasses(this.value)">
												<html:option value="">
													<bean:message key="knowledgepro.select" />-</html:option>
												<cms:renderAcademicYear></cms:renderAcademicYear>
											</html:select></td> --%>
											<td height="25" class="row-odd">
											<div align="right"><span class="Mandatory">*</span>Department</div>
											</td>
											<td class="row-even"><nested:select
												property="departmentId" styleClass="comboLarge"
												styleId="class">
												<html:option value="">
													<bean:message key="knowledgepro.select" />-</html:option>
												<logic:notEmpty name="viewTeacherWiseTimeTableForm"
													property="departmentMap">
													<html:optionsCollection name="viewTeacherWiseTimeTableForm"
														property="departmentMap" label="value" value="key" />
												</logic:notEmpty>
											</nested:select></td>
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
							<td>
							<table width="100%" border="0" align="center" cellpadding="0"
								cellspacing="0">
								<tr>
									<td width="100%" height="35">
									<div align="center"><html:submit value="Submit"
										styleClass="formbutton"></html:submit></div>
									</td>
								</tr>
							</table>
							</td>
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
<script src="js/timetable/viewClassTimeTable.js" type="text/javascript"></script>