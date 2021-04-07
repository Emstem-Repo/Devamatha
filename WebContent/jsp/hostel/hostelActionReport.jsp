<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css" />

<%@page import="com.kp.cms.constants.CMSConstants"%>
<script type="text/javascript">


function resetMessages() {
	document.getElementById("hostelNamesId").selectedIndex = 0;
	document.getElementById("aNamesId").selectedIndex = 0;
	document.getElementById("startDateId").value = "";
	document.getElementById("endDateId").value = "";	
	resetErrMsgs();
}
function searchHostelActionReportDetails(){
	//var hostName=document.getElementById("hostel").options[document.getElementById("hostel").selectedIndex].text;
	//document.getElementById("hostelNameId").value=hostName;
	document.getElementById("method").value = "getHostelActionReportDetailsPage";
	document.hostelActionReportForm.submit();
}
</script>
<html:form action="/hostelActionReport" method="post">
	<html:hidden property="method" styleId="method" />	
	<html:hidden property="formName" value="hostelActionReportForm" />
	<html:hidden property="pageType" value="1" />
	
	
	<table width="99%" border="0">
		<tr>
			<td><span class="heading"><bean:message
				key="knowledgepro.hostel" /> <span class="Bredcrumbs">&gt;&gt;<bean:message
				key="knowledgepro.hostel.actionReport" /> &gt;&gt;</span> </span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="heading_white"><bean:message
						key="knowledgepro.hostel.actionReport" /></td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="heading"><div id="errorMessage"><FONT color="red"><html:errors /></FONT>
							<FONT color="green"><html:messages id="msg"
							property="messages" message="true">
							<c:out value="${msg}" escapeXml="false"></c:out>
							<br>
						</html:messages> </FONT></div></td>
					<td valign="top" background="images/Tright_3_3.gif" class="news">
					</td>
				</tr>
				<tr>
					<td height="43" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" align="center" cellpadding="0"
						cellspacing="0">
						<tr>
							<td><img src="images/01.gif" width="5" height="5" /></td>
							<td width="914" background="images/02.gif">
							
							</td>
							<td><img src="images/03.gif" width="5" height="5" /></td>
						</tr>
						<tr>
							<td width="5" background="images/left.gif"></td>
							<td valign="top">
							<table width="100%" cellspacing="1" cellpadding="2">
								<tr>
									<td height="25" class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.hostel.entry.hostel.name" /></div>
									</td>
									<td height="25" class="row-even">
										<html:select name="hostelActionReportForm" property="hostelId" styleId="hostelNamesId" styleClass="combo">
											<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
											<logic:notEmpty name="hostelActionReportForm" property="hostelList">																												
											<html:optionsCollection name="hostelActionReportForm" property="hostelList" label="name" value="id"/>
											</logic:notEmpty>																												
										</html:select>
									</td>
									<td width="24%" height="25" class="row-even"></td>
									<td width="26%" height="25" class="row-even"></td>
								</tr>
								<tr>
									<td width="27%" height="25" class="row-odd">
										<div align="right"><bean:message key="knowledgepro.feepays.startdate.col" /></div>
									</td>
									<td width="23%" height="25" class="row-even">																		
										<html:text name="hostelActionReportForm" property="startDate" styleId="startDateId" size="10" />
	                        					<script language="JavaScript">
													new tcal ({
														// form name
														'formname': 'hostelActionReportForm',
														// input name
														'controlname': 'startDate'
													});
												</script>
									</td>
									<td width="27%" height="25" class="row-odd">
										<div align="right"><bean:message key="employee.info.education.endDt" /></div>
									</td>
									<td width="23%" height="25" class="row-even">																		
										<html:text name="hostelActionReportForm" property="endDate" styleId="endDateId" size="10" />
	                        					<script language="JavaScript">
													new tcal ({
														// form name
														'formname': 'hostelActionReportForm',
														// input name
														'controlname': 'endDate'
													});
												</script>
									</td>
								</tr>
								<tr>
									<td width="27%" height="25" class="row-odd">
										<div align="right"><bean:message key="knowledgepro.hostel.actionReport.type.col" /></div>
									</td>
									<td width="23%" height="25" class="row-even">
										<html:select name="hostelActionReportForm" property="actionId" styleId="aNamesId" styleClass="combo">
											<html:option value="0"><bean:message key="knowledgepro.hostel.leave" /></html:option>
											<html:option value="1"><bean:message key="knowledgepro.hostel.disciplinary" /></html:option>																																					
										</html:select>
									</td>
									<td width="27%" height="25" class="row-even"></td>
									<td width="23%" height="25" class="row-even"></td>
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
					<td height="37" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="49%" height="35">
							<div align="right">
							<html:button property="" styleClass="formbutton" value="Search"
										onclick="searchHostelActionReportDetails()"></html:button>
							</div>
							</td>
							<td width="2%"></td>
							<td width="49%">
							<div align="left"><html:button property="" styleClass="formbutton" value="Cancel"
										onclick="resetMessages()"></html:button></div>
							</td>
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
</html:form>