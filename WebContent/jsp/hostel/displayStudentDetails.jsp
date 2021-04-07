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
	document.location.href = "studentExtract.do?method=initStudentExtractDetails";
}
</script>
<link rel="stylesheet" href="css/calendar.css">
</head>
<html:form action="/studentExtract" >
<html:hidden property="formName" value="hostelStudentExtractForm" />
<html:hidden property="method" styleId="method" value="getStudentExtractDetails"/>
<html:hidden property="pageType" value="2"/>
<table width="99%" border="0">
	 <tr>
	    <td><span class="heading"><bean:message key="knowledgepro.hostel"/><span class="Bredcrumbs">&gt;&gt; <bean:message key="knowledgepro.hostel.student.extraction"/> &gt;&gt;</span> </span></td>
	 </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="heading_white" ><bean:message key="knowledgepro.hostel.student.extraction"/> </td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" >&nbsp;</td>
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
              <tr >
                <td class="row-odd"> <bean:message key="knowledgepro.hostel.student.regNo"/> </td>
                <td class="row-odd"><div align="left"> <bean:message key="knowledgepro.hostel.student.name"/> </div></td>
                <td class="row-odd"> <bean:message key="knowledgepro.hostel.student.class"/></td>
                <td height="25" class="row-odd"><div align="left"> <bean:message key="knowledgepro.hostel.student.rno"/> </div></td>
                <td class="row-odd"><bean:message key="knowledgepro.hostel.student.leavetype"/></td>
                <td class="row-odd"> <bean:message key="knowledgepro.hostel.student.period"/> </td>
                </tr>
	               <tr>
                         			<c:set var="temp" value="0" />
										<logic:notEmpty name="hostelStudentExtractForm" property="hostelStudExtractToList">
											<logic:iterate id="stExtractDetails" name="hostelStudentExtractForm" property="hostelStudExtractToList" indexId="count">
												<c:choose>
													<c:when test="${temp == 0}">
														<tr>
															 <td  width="21%"  class="row-even" ><bean:write name="stExtractDetails" property="regNo" /></td>
											                 <td  width="24%" class="row-even" ><bean:write name="stExtractDetails" property="applicantName" /></td>
											                 <td width="10%" class="row-even" ><bean:write name="stExtractDetails" property="className" /></td>
																<td  width="7%" height="25" class="row-even" ><bean:write name="stExtractDetails" property="roomNo" /></td>
																<td  width="8%" class="row-even" ><bean:write name="stExtractDetails" property="leaveType" /></td>
																<td  width="30%" class="row-even" ><bean:write name="stExtractDetails" property="diffDates" /></td>
														</tr>
														<c:set var="temp" value="1" />
													</c:when>
													<c:otherwise>
														<tr>
															<td height="25" class="row-white" ><bean:write name="stExtractDetails" property="regNo" /></td>
											                 <td  class="row-white" ><bean:write name="stExtractDetails" property="applicantName" /></td>
											                 <td  class="row-white" ><bean:write name="stExtractDetails" property="className" /></td>
																<td height="25" class="row-white" ><bean:write name="stExtractDetails" property="roomNo" /></td>
																<td  class="row-white" ><bean:write name="stExtractDetails" property="leaveType" /></td>
																<td  class="row-white" ><bean:write name="stExtractDetails" property="diffDates" /></td>
														</tr>
														<c:set var="temp" value="0" />
													</c:otherwise>
												</c:choose>
											</logic:iterate>
										</logic:notEmpty>
                    				</tr>
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
