<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@ taglib uri="/WEB-INF/struts-tld/displaytag.tld" prefix="display" %>
<link href="css/styles.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
function winOpen(attendanceId,periodDate,periodName,subjectName) {
	var url = "attendTeacherSummaryReport.do?method=getPreviousAbsentStudents&attendanceId="+attendanceId+"&attendanceDate="+periodDate+"&periodName="+periodName+"&subjectName="+subjectName;
	myRef = window
			.open(url, "viewPrivileges",
					"left=20,top=20,width=900,height=600,toolbar=1,resizable=0,scrollbars=1");
}
</script>

<html:form action="/attendTeacherSummaryReport.do">
<html:hidden property="method" styleId="method" value = ""/>
<html:hidden property="formName" value="attendanceTeacherReportForm"/>
<table width="99%" border="0">  
  <tr>
    <td><span class="heading"><span class="Bredcrumbs"><bean:message key="knowledgepro.attendance"/>  &gt;&gt;Period Details &gt;&gt;</span></span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td colspan="2" background="images/Tcenter.gif" class="heading_white" >Period Details</td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td colspan="2" class="heading"><div align="right"> </div>       
        <div id="errorMessage"><FONT color="red"><html:errors /></FONT>
					<FONT color="green"><html:messages id="msg"
						property="messages" message="true">
						<c:out value="${msg}" escapeXml="false"></c:out>
						<br>
					</html:messages> </FONT></div>       
        </td>
        <td valign="top" background="images/Tright_3_3.gif" ></td>
      </tr>
      <tr>
        <td valign="top" background="images/Tright_03_03.gif"></td>
        <td colspan="2" valign="top" ><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td ><img src="images/01.gif" width="5" height="5" /></td>
            <td width="914" background="images/02.gif"></td>
            <td><img src="images/03.gif" width="5" height="5" /></td>
          </tr>
          <tr>
            <td width="5"  background="images/left.gif"></td>
            <td valign="top">
	   	<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td ><img src="images/01.gif" width="5" height="5" /></td>
            <td width="914" background="images/02.gif"></td>
            <td><img src="images/03.gif" width="5" height="5" /></td>
          </tr> 
           
          <tr>
            <td width="5" background="images/left.gif"></td>
            <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
            
              <tr >
                <td height="25"  width="25%" class="row-odd" ><bean:message key="knowledgepro.date"/> </td>
                <td height="25"  width="30%"class="row-odd" ><bean:message key="knowledgepro.cancelattendance.periods"/> </td>
                <td height="25"  width="20%"class="row-odd" ><bean:message key="knowledgepro.attendanceentry.hoursheld"/> </td>
                 <td height="25"  width="25%"class="row-odd" ><bean:message key="knowledgepro.attendance.type"/> </td>
                </tr>
				
				<logic:notEmpty name="attendanceTeacherReportForm" property="periodList" > 
					<logic:iterate id="period" name="attendanceTeacherReportForm" property="periodList" indexId="count">
                <tr >
	                <td width="25%" height="25" class="row-even"><bean:write name="period" property="date" ></bean:write> </td>
	                <td width="30%" height="25" class="row-even" >
	               
	                <bean:write name="period" property="periodNames" ></bean:write>&nbsp;&nbsp; </td>
	                <td width="20%" height="25" class="row-even" >
	                <A HREF="javascript:winOpen('<bean:write name="period" property="attendanceId" />','<bean:write name="period" property="date" />','<bean:write name="period" property="periodNames" />','<bean:write name="period" property="subjectName" />' );"><bean:write name="period" property="hoursTaken" /></A> 
	                </td>
	                <td width="25%" height="25" class="row-even"><bean:write name="period" property="attendanceType" ></bean:write> </td>
                </tr>
					</logic:iterate>	
				</logic:notEmpty>				
            </table></td>
            <td width="5" height="30" background="images/right.gif"></td>
          </tr>
          <tr>
            <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
            <td background="images/05.gif"></td>
            <td><img src="images/06.gif" /></td>
          </tr>
        </table>
        <td width="5" height="30"  background="images/right.gif"></td>
          </tr>
          <tr>
            <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
            <td background="images/05.gif"></td>
            <td><img src="images/06.gif" /></td>
          </tr>
        </table></td>
        <td valign="top" background="images/Tright_3_3.gif" ></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td colspan="2" class="heading"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="49%" height="35" align="right">&nbsp;</td>
            <td width="2%" height="35" align="center"><html:button property="" styleClass="formbutton" value="Close" onclick="window.close()"/></td>
            <td width="49%" height="35" align="left"></td>
          </tr>
        </table></td>
        <td valign="top" background="images/Tright_3_3.gif" ></td>
      </tr>
      <tr>
        <td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
        <td width="0" colspan="2" background="images/TcenterD.gif"></td>
        <td><img src="images/Tright_02.gif" width="9" height="29"></td>
      </tr>
    </table></td>
  </tr>
</table>
</html:form>