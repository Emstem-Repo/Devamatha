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
	<html:hidden property="method" styleId="method" value="" />
	<html:hidden property="formName" value="viewTeacherWiseTimeTableForm" />
	<html:hidden property="pageType" value="1" />
	<html:hidden property="userId" styleId="userId"
		name="viewTeacherWiseTimeTableForm" />
	<table width="100%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message
				key="knowledgepro.timetable.module" /> <span class="Bredcrumbs">&gt;&gt;
			Class Time Table&gt;&gt;</span></span></td>
		</tr>

		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="heading_white">Class
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
										<!--
	                <tr>
	        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
	        <td class="heading"></td>      
	        <td valign="top" background="images/Tright_3_3.gif" ></td>
	      </tr>
	                    -->
										<tr>
											<td height="25" class="row-odd">
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
											</html:select></td>
											<td height="25" class="row-odd">
											<div align="right"><span class="Mandatory">*</span><bean:message
												key="knowledgepro.attendance.class.col" /></div>
											</td>
											<td class="row-even"><nested:select
												property="classSchemewiseId" styleClass="comboLarge"
												styleId="class">
												<html:option value="">
													<bean:message key="knowledgepro.select" />-</html:option>
												<logic:notEmpty name="viewTeacherWiseTimeTableForm"
													property="classMap">
													<html:optionsCollection name="viewTeacherWiseTimeTableForm"
														property="classMap" label="value" value="key" />
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
									<td width="45%" height="35">
									<div align="right"><html:submit value="Submit"
										styleClass="formbutton" styleId="SubmitData"></html:submit></div>
									</td>
									<td width="2%"></td>
									<td width="53%"><html:button property=""
										styleClass="formbutton" value="Reset"
										onclick="resetFieldsAndErrors()"></html:button></td>
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
					<td class="heading"></td>
					<td valign="top" background="images/Tright_3_3.gif"></td>
				</tr>
				<!--
	      <tr>
	        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
	        <td class="heading"></td>      
	        <td valign="top" background="images/Tright_3_3.gif" ></td>
	      </tr>
	-->
				<logic:notEmpty name="viewTeacherWiseTimeTableForm"
					property="timeTableList">
					<tr>
						<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
						<td class="heading">
						<table width="100%" border="0" align="center" cellpadding="0"
							cellspacing="0">
							<tr>
								<td><img src="images/01.gif" width="5" height="5"></td>
								<td width="914" background="images/02.gif"></td>
								<td><img src="images/03.gif" width="5" height="5"></td>
							</tr>
							<tr>

								<td width="5" background="images/left.gif"></td>
								<td height="54" valign="top">
								<table width="100%" cellspacing="1" cellpadding="2">
									<tr class="row-odd" height="25px">
										<td width="10%" align="center">Day</td>
										<logic:notEmpty name="viewTeacherWiseTimeTableForm"
											property="periodList">
											<logic:iterate id="bo" name="viewTeacherWiseTimeTableForm"
												property="periodList">
												<td width="10%" height="10%"><bean:write name="bo"
													property="periodName" />&nbsp;&nbsp;(<bean:write name="bo"
													property="startTime" /> - <bean:write name="bo"
													property="endTime" />)</td>
											</logic:iterate>
										</logic:notEmpty>
									</tr>
									<logic:iterate id="to" name="viewTeacherWiseTimeTableForm"
										property="timeTableList">
										<tr height="20px">
											<td class="row-odd" width="10%" align="center"><bean:write
												name="to" property="week" /></td>
											<logic:notEmpty name="to" property="timeTablePeriodTos">
												<logic:iterate id="pto" name="to"
													property="timeTablePeriodTos">
													<td width="10%" class="row-even" align="left">
													<table cellpadding="0" cellspacing="0" width="100%">
														<tr height="1%">
															<td><font color="brown"><strong><bean:write
																name="pto" property="roomNo" /></strong></font></td>
														</tr>
														<tr height="1%">
															<td class="display"
																title="Subject Name : <bean:write name="pto" property="subjectNames"/>"><b><bean:write
																name="pto" property="subjectCode" /> </b>
															<div id="messageBox_" +<c:out value="${count}"/>></div>
															</td>
														</tr>
														<tr height="1%">
															<td class="heading"><bean:write name="pto"
																property="teacherName" /></td>
														</tr>
													</table>
													</td>
												</logic:iterate>
											</logic:notEmpty>
										</tr>
									</logic:iterate>


								</table>
								</td>

								<td background="images/right.gif" width="5" height="54"></td>
							</tr>
							<tr>
								<td height="5"><img src="images/04.gif" width="5"
									height="5"></td>
								<td background="images/05.gif"></td>
								<td><img src="images/06.gif"></td>
							</tr>
							<tr>

								<td height="54" valign="top" colspan="3">


								<table width="100%" cellspacing="1" cellpadding="3">
									<tr>
										<td>
										<table width="100%" cellspacing="0" cellpadding="0">
											<tr>
												<td width="70%" height="35">
												<div align="center"><html:button property=""
													value="Cancel" styleClass="formbutton" onclick="cancel()"></html:button>
												</div>
											</tr>
										</table>
										</td>
									</tr>
								</table>


								</td>

							</tr>
						</table>
						</td>
						<td valign="top" background="images/Tright_3_3.gif"></td>
					</tr>
				</logic:notEmpty>
				<logic:equal value="true" name="viewTeacherWiseTimeTableForm"
					property="flag">
					<logic:empty name="viewTeacherWiseTimeTableForm"
						property="timeTableList">
						<tr>
							<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
							<td align="center">No TimeTable Found</td>
							<td valign="top" background="images/Tright_3_3.gif"></td>
						</tr>
					</logic:empty>
				</logic:equal>
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