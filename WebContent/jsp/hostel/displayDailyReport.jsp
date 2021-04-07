<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>

<head>
<link href="css/styles.css" rel="stylesheet" type="text/css">
<script language="JavaScript" src="js/calendar_us.js"></script>
<script type="text/javascript" language="JavaScript">
function cancelAction() {
	document.location.href = "hostelDailyReport.do?method=initHostelDetails";
}
</script>
<link rel="stylesheet" href="css/calendar.css">
</head>
<html:form action="/hostelDailyReport" >
<html:hidden property="formName" value="hostelDailyReportForm" />
<html:hidden property="pageType" value="2"/>

<table width="99%" border="0">
  
  <tr>
    <td><span class="heading"><bean:message key="knowledgepro.hostel"/><span class="Bredcrumbs">&gt;&gt;  <bean:message key="knowledgepro.hostel.daily.report"/> &gt;&gt;</span> </span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="heading_white" ><bean:message key="knowledgepro.hostel.daily.report"/> </td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td valign="top" background="images/Tright_03_03.gif"></td>
       <td valign="top" class="news">
								<div align="right"><span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields" /></span></div>
								<div id="errorMessage">
									<FONT color="red"><html:errors /></FONT>
									<FONT color="green">
										<html:messages id="msg" property="messages" message="true">
											<c:out value="${msg}" escapeXml="false"></c:out><br>
										</html:messages>
									</FONT>
								</div>
			</td>
        <td valign="top" background="images/Tright_3_3.gif" ></td>
      </tr>
      <tr>
        <td height="44" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" ><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td ><img src="images/01.gif" width="5" height="5"></td>
            <td width="914" background="images/02.gif"></td>
            <td><img src="images/03.gif" width="5" height="5"></td>
          </tr>
          <tr>
            <td width="5"  background="images/left.gif"></td>
            <td height="54" valign="top"><table width="100%" cellspacing="1" cellpadding="2">
              <tr>
				<td class="row-odd" width="12%">  <bean:message key="knowledgepro.hostel.daily.hostelName"/>  </td>
                <td class="row-odd" width="21%">  <bean:message key="knowledgepro.hostel.daily.studId"/>  </td>
                <td class="row-odd" width="12%"><div align="left"> <bean:message key="knowledgepro.hostel.daily.studName"/> </div></td>
                <td class="row-odd" width="12%"> <bean:message key="knowledgepro.hostel.daily.studClass"/> </td>
                <td height="25" class="row-odd" width="12%"><div align="left"> <bean:message key="knowledgepro.hostel.daily.rno"/> </div></td>
                <td class="row-odd" width="20%"> <bean:message key="knowledgepro.hostel.daily.mobileno"/></td>
                <td class="row-odd" width="20%"> <bean:message key="knowledgepro.hostel.daily.guardian"/> </td>
                </tr>
              
              <c:set var="temp" value="0"/>
										<logic:notEmpty name="hostelDailyReportForm" property="hostelDailyReportToList">
											<logic:iterate id="dailyReportDetails" name="hostelDailyReportForm" property="hostelDailyReportToList" indexId="count">
												<c:choose>
													<c:when test="${temp == 0}">
														<tr>
															<c:choose>
																<c:when test="${dailyReportDetails.isStaff == false}">
																	 <td width="12%" height="25" class="row-even" ><bean:write name="dailyReportDetails" property="hostelName" /></td>
													                 <td width="21%" class="row-even" ><bean:write name="dailyReportDetails" property="stId" /></td>
													                 <td width="12%" class="row-even" ><bean:write name="dailyReportDetails" property="studName" /></td>
																		<td width="12%" class="row-even" ><bean:write name="dailyReportDetails" property="className" /></td>
																		<td width="12%" class="row-even" ><bean:write name="dailyReportDetails" property="roomName" /></td>
																		<td width="28%" class="row-even" ><bean:write name="dailyReportDetails" property="stMobileNo" /></td>
																		<c:choose>
																			<c:when test="${hostelDailyReportToList[count].gdMobileNo != null}">
																				<td width="20%" class="row-even" ><bean:write name="dailyReportDetails" property="gdMobileNo" /></td>
																			</c:when>
																			<c:otherwise>
																				<td width="20%" class="row-even" ><bean:write name="dailyReportDetails" property="pmobileNo" /></td>
																			</c:otherwise>
																		</c:choose>
																</c:when>
																<c:otherwise>
																		<td width="12%" height="25" class="row-even" ><bean:write name="dailyReportDetails" property="hostelName" /></td>
													                	 <td width="21%" class="row-even" ><bean:write name="dailyReportDetails" property="empId" /></td>
													                 	<td width="12%" class="row-even" ><bean:write name="dailyReportDetails" property="empName" /></td>
																		<td width="12%" class="row-even" ><bean:write name="dailyReportDetails" property="className" /></td>
																		<td width="12%" class="row-even" ><bean:write name="dailyReportDetails" property="roomName" /></td>
																		<td width="20%" class="row-even" ><bean:write name="dailyReportDetails" property="emobileNo" /></td>
																		<td width="20%" class="row-even" ><bean:write name="dailyReportDetails" property="eemergencyMobile" /></td>
																</c:otherwise>
															</c:choose>
														</tr>
														<c:set var="temp" value="1" />
													</c:when>
													<c:otherwise>
														<tr>
																<c:choose>
																	<c:when test="${dailyReportDetails.isStaff == false}">
																			 <td width="12%" height="25" class="row-white" ><bean:write name="dailyReportDetails" property="hostelName" /></td>
														                	 <td width="21%" class="row-white" ><bean:write name="dailyReportDetails" property="stId" /></td>
														                	 <td width="12%" class="row-white" ><bean:write name="dailyReportDetails" property="studName" /></td>
																			<td width="12%" class="row-white" ><bean:write name="dailyReportDetails" property="className" /></td>
																			<td width="12%" class="row-white" ><bean:write name="dailyReportDetails" property="roomName" /></td>
																			<td width="28%" class="row-white" ><bean:write name="dailyReportDetails" property="stMobileNo" /></td>
																			<c:choose>
																			<c:when test="${hostelDailyReportToList[count].gdMobileNo != null}">
																				<td width="20%" class="row-even" ><bean:write name="dailyReportDetails" property="gdMobileNo" /></td>
																			</c:when>
																			<c:otherwise>
																				<td width="20%" class="row-even" ><bean:write name="dailyReportDetails" property="pmobileNo" /></td>
																			</c:otherwise>
																		</c:choose>
																	</c:when>
																	<c:otherwise>
																			<td width="12%" height="25" class="row-white" ><bean:write name="dailyReportDetails" property="hostelName" /></td>
														                 	<td width="21%" class="row-white" ><bean:write name="dailyReportDetails" property="empId" /></td>
														                 	<td width="12%" class="row-white" ><bean:write name="dailyReportDetails" property="empName" /></td>
																			<td width="12%" class="row-white" ><bean:write name="dailyReportDetails" property="className" /></td>
																			<td width="12%" class="row-white" ><bean:write name="dailyReportDetails" property="roomName" /></td>
																			<td width="20%" class="row-white" ><bean:write name="dailyReportDetails" property="emobileNo" /></td>
																			<td width="20%" class="row-white" ><bean:write name="dailyReportDetails" property="eemergencyMobile" /></td>
																	</c:otherwise>
																</c:choose>
															</tr>
														<c:set var="temp" value="0" />
													</c:otherwise>
												</c:choose>
											</logic:iterate>
										</logic:notEmpty>
            </table></td>
            <td  background="images/right.gif" width="5" height="54"></td>
          </tr>
          <tr>
            <td height="5"><img src="images/04.gif" width="5" height="5"></td>
            <td background="images/05.gif"></td>
            <td><img src="images/06.gif" ></td>
          </tr>
        </table></td>
        <td valign="top" background="images/Tright_3_3.gif" ></td>
      </tr>
      
      <tr>
        <td height="26" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" ><table width="100%"  border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="48%" height="35">&nbsp;</td>
            <td width="3%"><input type="button" class="formbutton" value="Back" onclick="cancelAction()" /></td>
            <td width="49%">&nbsp;</td>
          </tr>
        </table></td>
        <td valign="top" background="images/Tright_3_3.gif" ></td>
      </tr>
      <tr>
        <td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
        <td width="0" background="images/TcenterD.gif"></td>
        <td><img src="images/Tright_02.gif" width="9" height="29"></td>
      </tr>
    </table></td>
  </tr>
</table></html:form>
</body>
</html>
