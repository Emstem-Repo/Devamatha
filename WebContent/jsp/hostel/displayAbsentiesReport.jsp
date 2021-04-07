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
	document.location.href = "hostelAbsentiesReport.do?method=initHostelDetails";
}
function printAction(){
	  var url = "hostelAbsentiesReport.do?method=getDetailsToPrint";
	 myRef = window.open(url,"hostelAbsentiesReport","left=20,top=20,width=800,height=500,toolbar=1,resizable=0,scrollbars=1");
	 }
</script>
<link rel="stylesheet" href="css/calendar.css">
</head>
<html:form action="/hostelAbsentiesReport" >
<html:hidden property="formName" value="hostelAbsentiesReportForm" />
<html:hidden property="pageType" value="2"/>

<table width="99%" border="0">
  
  <tr>
    <td><span class="heading"><bean:message key="knowledgepro.hostel"/><span class="Bredcrumbs">&gt;&gt;<bean:message key="knowledgepro.hostel.absentees.report"/> &gt;&gt;</span> </span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="heading_white" ><bean:message key="knowledgepro.hostel.absentees.report"/></td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="44" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" ><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td ><img src="images/01.gif" width="5" height="5"></td>
            <td width="914" background="images/02.gif"></td>
            <td><img src="images/03.gif" width="5" height="5"></td>
          </tr>
		 	<tr> </tr>
			<tr> </tr>
          <tr>
            <td width="5"  background="images/left.gif"></td>
            <td height="54" valign="top"><table width="100%" cellspacing="1" cellpadding="2">
              <tr >
                <td height="25" rowspan="2" class="row-odd" ><div align="center"><bean:message key="knowledgepro.hostel.absentees.slno"/></div></td>
                <td rowspan="2" class="row-odd" ><bean:message key="knowledgepro.hostel.daily.hostelName"/></td>
                <td rowspan="2" class="row-odd" ><div align="left"> <bean:message key="knowledgepro.hostel.absentees.regno"/></div></td>
                <td rowspan="2" class="row-odd" ><bean:message key="knowledgepro.hostel.absentees.studName"/></td>
                <td height="25" rowspan="2" class="row-odd"><div align="left"><bean:message key="knowledgepro.hostel.absentees.studClass"/></div></td>
                <td rowspan="2" class="row-odd"><bean:message key="knowledgepro.hostel.absentees.rno"/></td>
                <td colspan="3" class="row-odd"><div align="center"><bean:message key="knowledgepro.hostel.absentees.noOfDays"/></div></td>
                </tr>
              <tr >
                <td height="16" class="row-odd" ><div align="center"><bean:message key="knowledgepro.hostel"/></div></td>
              </tr>
              <c:set var="temp" value="0"/>
										<logic:notEmpty name="hostelAbsentiesReportForm" property="hostelAbsentiesReportToList">
											<logic:iterate id="absentReportDetails" name="hostelAbsentiesReportForm" property="hostelAbsentiesReportToList" indexId="count">
												<c:choose>
													<c:when test="${temp == 0}">
														<tr>
															<td width="5%" height="25" class="row-even" ><c:out value="${count + 1}"/></td>
													        <td width="17%" class="row-even" ><bean:write name="absentReportDetails" property="hostName" /></td>
													        <td width="20%" class="row-even" ><bean:write name="absentReportDetails" property="regNo" /></td>
															<td width="15%" class="row-even" ><bean:write name="absentReportDetails" property="studName" /></td>
															<td width="7%" class="row-even" ><bean:write name="absentReportDetails" property="className" /></td>
															<td width="10%" class="row-even" ><bean:write name="absentReportDetails" property="roomNo" /></td>
															<td class="row-even" ><bean:write name="absentReportDetails" property="count" /></td>
														</tr>
														<c:set var="temp" value="1" />
													</c:when>
													<c:otherwise>
														<tr>
															<td width="5%" height="25" class="row-white" ><c:out value="${count + 1}"/></td>
													        <td width="17%" class="row-white" ><bean:write name="absentReportDetails" property="hostName" /></td>
															<td width="20%" class="row-white" ><bean:write name="absentReportDetails" property="regNo" /></td>
															<td width="15%" class="row-white" ><bean:write name="absentReportDetails" property="studName" /></td>
															<td width="7%" class="row-white" ><bean:write name="absentReportDetails" property="className" /></td>
															<td width="10%" class="row-white" ><bean:write name="absentReportDetails" property="roomNo" /></td>
															<td  class="row-white" ><bean:write name="absentReportDetails" property="count" /></td>
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
            <td width="48%" height="35"><div align="right">
               <html:button property="print" onclick="printAction()" styleClass="formbutton"  styleId="printme" >
               			<bean:message key="knowledgepro.print" /></html:button>
            </div></td>
            <td width="3%"></td>
            <td width="49%"><input type="button" class="formbutton" value="Back" onclick="cancelAction()" /></td>
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
