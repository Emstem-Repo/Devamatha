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
	document.location.href = "hostelReqReport.do?method=initHostelDetails";
}
</script>
<link rel="stylesheet" href="css/calendar.css">
</head>
<body>
<html:form action="/hostelReqReport">
<table width="99%" border="0">
  <tr>
    <td><span class="heading"><bean:message key="knowledgepro.hostel"/> <span class="Bredcrumbs">&gt;&gt; <bean:message key="knowledgepro.hostel.req.report"/> &gt;&gt;</span> </span></td>
  </tr>
  <tr>
    <td valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="heading_white" > <bean:message key="knowledgepro.hostel.req.report"/></td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="26" valign="top" background="images/Tright_03_03.gif"></td>
        <td class="news">&nbsp;</td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="26" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td ><img src="images/01.gif" width="5" height="5" /></td>
            <td width="914" background="images/02.gif"></td>
            <td><img src="images/03.gif" width="5" height="5" /></td>
          </tr>
          <tr>
            <td width="5"  background="images/left.gif"></td>
            <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
                <tr >
                  <td class="row-odd" ><bean:message key="knowledgepro.hostel.student.name"/></td>
                  <td height="25" class="row-odd" ><bean:message key="knowledgepro.hostel.appno.regno.staffid"/></td>
                  <td class="row-odd" ><bean:message key="knowledgepro.hostel.req.for"/></td>
                 </tr>
                 <tr>
                         			<c:set var="temp" value="0" />
										<logic:notEmpty name="hostelReqReportForm" property="hostelReqReportToList">
											<logic:iterate id="reqReportDetails" name="hostelReqReportForm" property="hostelReqReportToList" indexId="count">
												<c:choose>
													<c:when test="${temp == 0}">
														<tr>
															 <td width="12%" height="25" class="row-even" ><bean:write name="reqReportDetails" property="applicantName" /></td>
											                 <td width="21%" class="row-even" ><bean:write name="reqReportDetails" property="applnNo" /></td>
											                 <td width="28%" class="row-even" ><bean:write name="reqReportDetails" property="roomTypeName" /></td>
														</tr>
														<c:set var="temp" value="1" />
													</c:when>
													<c:otherwise>
														<tr>
															<td height="25" class="row-white" ><bean:write name="reqReportDetails" property="applicantName" /></td>
											                  <td class="row-white" ><bean:write name="reqReportDetails" property="applnNo" /></td>
											                  <td class="row-white" ><bean:write name="reqReportDetails" property="roomTypeName" /></td>
														</tr>
														<c:set var="temp" value="0" />
													</c:otherwise>
												</c:choose>
											</logic:iterate>
										</logic:notEmpty>
                    				</tr>
            </table></td>
            <td width="5" height="30"  background="images/right.gif"></td>
          </tr>
          <tr>
            <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
            <td background="images/05.gif"></td>
            <td><img src="images/06.gif" /></td>
          </tr>
        </table></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="26" valign="top" background="images/Tright_03_03.gif"></td>
        <td class="heading"><bean:message key="knowledgepro.hostel.availability"/></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="26" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="50%" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td ><img src="images/01.gif" width="5" height="5" /></td>
            <td width="914" background="images/02.gif"></td>
            <td><img src="images/03.gif" width="5" height="5" /></td>
          </tr>
          <tr>
            <td width="5"  background="images/left.gif"></td>
            <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
               			 <tr>
							<logic:notEmpty name="hostelReqReportForm" property="hostelRoomTypeToList">
								<logic:iterate id="roomTypeDetails" name="hostelReqReportForm" property="hostelRoomTypeToList" indexId="count">
									<tr >
										<td width="50%" class="row-odd" ><div align="right"><bean:write name="roomTypeDetails" property="roomTypeName" />  </div></td>
										<td width="50%" class="row-even" ><bean:write name="roomTypeDetails" property="noOfRooms" /></td>
									</tr>
								</logic:iterate>
							</logic:notEmpty>
                  		 </tr>
            </table></td>
            <td width="5" height="30"  background="images/right.gif"></td>
          </tr>
          <tr>
            <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
            <td background="images/05.gif"></td>
            <td><img src="images/06.gif" /></td>
          </tr>
        </table></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
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
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
        <td width="0" background="images/TcenterD.gif"></td>
        <td><img src="images/Tright_02.gif" width="9" height="29"></td>
      </tr>
    </table></td>
  </tr>
</table>
</html:form>
</body>
</html>
