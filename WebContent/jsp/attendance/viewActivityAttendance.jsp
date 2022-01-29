<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<script language="JavaScript">
	function editActivityAttendance(id) {
		document.location.href = "activityAttendance.do?method=getActivityAttendanceByIdForView&id=" + id;
	}
	function resetFields() {
		document.getElementById("fromDate").value = "";
		document.getElementById("toDate").value = "";
		resetErrMsgs();
	}
</script>
<html:form action="/activityAttendance">
	<html:hidden property="formName" value="approveleaveForm" />
	<html:hidden property="pageType" value="2" />
	<html:hidden property="method" styleId="method" value="initViewActivityAttendence"/>
	<table width="100%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message key="knowledgepro.attendance" /> <span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.attendance.viewactivityattendence" /> &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td valign="top">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"><bean:message key="knowledgepro.attendance.viewactivityattendence" /></strong></td>

					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="20" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<div align="right"><FONT color="red"> <span class='MandatoryMark'> <bean:message key="knowledgepro.mandatoryfields" /></span></FONT></div>
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
									<td width="13%" height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.admission.fromdate" /></div>
									</td>
									<td width="19%" height="25" class="row-even">
									<html:text property="fromDate" styleId="fromDate" size="11" maxlength="11"></html:text>
									<script language="JavaScript">
										new tcal( {
											// form name
											'formname' :'approveleaveForm',
											// input name
											'controlname' :'fromDate'
										});
									</script>
									</td>

									<td width="22%" class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.admission.todate" /></div>
									</td>
									<td width="16%" class="row-even">
									<html:text property="toDate" styleId="toDate" size="11" maxlength="11"></html:text>
									<script language="JavaScript">
										new tcal( {
											// form name
											'formname' :'approveleaveForm',
											// input name
											'controlname' :'toDate'
										});
									</script>
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
						<tr><td style="height: 10px" align="center" colspan="3"></td></tr>
						<tr>
							<td width="45%" height="35">
							<div align="right">
								<html:submit property="" styleClass="formbutton" value="Search" styleId="submitbutton"></html:submit>
							</div>
							</td>
							<td width="2%"></td>
							<td width="53%"><html:button property="" styleClass="formbutton" value="Reset" onclick="resetFields()"></html:button></td>
						</tr>
						<tr><td style="height: 10px" align="center" colspan="3"></td></tr>						
					</table>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				  <tr>
                   <td height="35" colspan="6" >
				          <logic:notEmpty name="approveleaveForm" property="leaveList">
					<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                     <tr>
                       <td ><img src="images/01.gif" width="5" height="5" /></td>
                       <td width="914" background="images/02.gif"></td>
                       <td><img src="images/03.gif" width="5" height="5" /></td>
                     </tr>
                     <tr>
                       <td width="5"  background="images/left.gif"></td>
                       <td valign="top">
                       
	                   <table width="100%" cellspacing="1" cellpadding="2">
	                       <tr >
	                           <td width="5%" height="25" class="row-odd"><div align="center"><bean:message key="admissionForm.detailmark.slno.label"/>.</div></td>
		                       <td width="10%" class="row-odd" ><div align="center"><bean:message key="knowledgepro.attendance.activityattendence.leavetype"/></div></td>
		                       <td width="10%" class="row-odd" ><div align="center"><bean:message key="knowledgepro.attendance.leavemodify.fromdate"/></div></td>
		                       <td width="10%" class="row-odd" ><div align="center"><bean:message key="knowledgepro.attendance.leavemodify.todate"/></div></td>
		                       <td width="10%" class="row-odd" ><div align="center"><bean:message key="knowledgepro.attendance.activityattendence.fromperiod"/></div></td>
		                       <td width="10%" class="row-odd"><div align="center"><bean:message key="knowledgepro.attendance.activityattendence.toperiod"/></div></td>
		                       <td width="10%" class="row-odd"><div align="center"><bean:message key="knowledgepro.attendance.activityattendence.class"/></div></td>
		                       <td width="10%" class="row-odd"><div align="center"><bean:message key="knowledgepro.attendance.regno/rollno"/></div></td>
		                       <td width="10%" class="row-odd"><div align="center"><bean:message key="knowledgepro.view"/></div></td>
	                       </tr>
	                       <c:set var="temp" value="0"/>
	                       <logic:iterate id="studentLeave" name="approveleaveForm" property="leaveList" type="com.kp.cms.to.attendance.StudentLeaveTO" indexId="count">
		                       <c:choose>
	                           	 <c:when test="${temp == 0}">
	                           		<tr>
									   <td height="25" class="row-even" ><div align="center"><c:out value="${count + 1}"/></div></td>
				                       <td class="row-even"><div align="center"><bean:write name="studentLeave" property="leaveType"/></div></td>
				                       <td class="row-even"><div align="center"><bean:write name="studentLeave"  property="startDate"/></div></td>
				                       <td class="row-even"><div align="center"><bean:write name="studentLeave" property="endDate"/></div></td>
				                       <td class="row-even"><div align="center"><bean:write name="studentLeave" property="startPeriod"/></div></td>
				                       <td class="row-even"><div align="center"><bean:write name="studentLeave" property="endPeriod"/></div></td>
				                       <td class="row-even"><div align="center"><bean:write name="studentLeave" property="className"/></div></td>
				                       <td class="row-even"><div align="center"><bean:write name="studentLeave" property="rollOrRegNos"/></div></td>
				                       <td class="row-even"><div align="center"><img src="images/View_icon.gif" width="16" style="cursor:pointer" height="18" onclick="editActivityAttendance('<bean:write name="studentLeave" property="id"/>')"></div></td>
	                               </tr>
	                      		   <c:set var="temp" value="1"/>
	                   		 	</c:when>
	                    	    <c:otherwise>
			                    <tr>
			                    	   <td height="25" class="row-white" ><div align="center"><c:out value="${count + 1}"/></div></td>
			             			   <td class="row-white"><div align="center"><bean:write name="studentLeave" property="leaveType"/></div></td>
				                       <td class="row-white"><div align="center"><bean:write name="studentLeave" property="startDate"/></div></td>
				                       <td class="row-white"><div align="center"><bean:write name="studentLeave" property="endDate"/></div></td>
				                       <td class="row-white"><div align="center"><bean:write name="studentLeave" property="startPeriod"/></div></td>
				                       <td class="row-white"><div align="center"><bean:write name="studentLeave" property="endPeriod"/></div></td>
				                       <td class="row-white"><div align="center"><bean:write name="studentLeave" property="className"/></div></td>
				                       <td class="row-white"><div align="center"><bean:write name="studentLeave" property="rollOrRegNos"/></div></td>
				                       <td class="row-white"><div align="center"><img src="images/View_icon.gif" width="16" style="cursor:pointer" height="18" onclick="editActivityAttendance('<bean:write name="studentLeave" property="id"/>')"></div></td>
				                </tr>       
	                    		 <c:set var="temp" value="0"/>
					  	       </c:otherwise>
	                        </c:choose>
	                      </logic:iterate>
	                      <tr>
						   <td height="2" class="row-even" ><div align="center"></div></td>
	                       <td class="row-even"><div align="center"></div></td>
	                       <td class="row-even" ><div align="center"></div></td>
	                       <td class="row-even" ><div align="center"></div></td>
	                       <td class="row-even" ><div align="center"></div></td>
	                       <td class="row-even" ><div align="center"></div></td>
	                       <td class="row-even" ><div align="center"></div></td>
	                       <td class="row-even" ><div align="center"></div></td>
	                     </tr>
	                   </table>
	                  
                   </td>
                       <td width="5" height="30"  background="images/right.gif"></td>
                     </tr>
                     <tr>
                       <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
                       <td background="images/05.gif"></td>
                       <td><img src="images/06.gif" /></td>
                     </tr>
                   </table>
                    </logic:notEmpty>
				</td>
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