<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<script type="text/javascript">
function cancelAction() {
	document.location.href = "ResidentStudentInfo.do?method=initResidentStudent";
}
function viewStudent(applicationNumber,appliedYear,courseId){
	var url  = "ApplicantDetails.do?method=getApplicantDetails&applicationNumber="+applicationNumber+"&applicationYear="+appliedYear+"&courseId="+courseId;
	myRef = window.open(url,"ViewApplicantDetails","left=20,top=20,width=800,height=600,toolbar=1,resizable=0,scrollbars=1");
}
</script>
<html>
<body>
<html:form action="/ResidentStudentInfo">	
	<html:hidden property="formName" value="residentStudentInfoForm" />
<table width="99%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message key="knowledgepro.hostel"/>
			<span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.hostel.residentStudentInfo"/>
			 &gt;&gt;</span></span></td>
		</tr>
  <tr>
    <td valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="heading_white" ><bean:message key="knowledgepro.hostel.residentStudentInfo"/></td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="25" valign="top" background="images/Tright_03_03.gif"></td>
        <td class="heading"><bean:message key="knowledgepro.hostel.details"/></td>
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
                  <td width="25%" height="25" align="right" class="row-odd" ><bean:message key="knowledgepro.hostel.hostel.entry.name"/>:</td>
                  <td width="25%" height="25" class="row-even" >
                  <bean:write name="residentStudentInfoForm" property="rto.hostelType"/>
                  </td>
                  <td width="25%" align="right" class="row-odd" >
                  <bean:message key="knowledgepro.roomtype"/>:
                  </td>
                  <td width="25%" class="row-even" >
                  <bean:write name="residentStudentInfoForm" property="rto.roomType"/>
                  </td>
                </tr>
                <tr >
                  <td width="25%" height="25" align="right" class="row-odd" >Student Name:</td>
                  <td width="25%" height="25" class="row-even" >
                  <bean:write name="residentStudentInfoForm" property="rto.studentName"/>
                  </td>
                  <td width="25%" align="right" class="row-odd" >
                  Appln No/Reg no/Roll No:
                  </td>
                  <td width="25%" class="row-even" >
                  <bean:write name="residentStudentInfoForm" property="rto.applNo"/>
                  </td>
                </tr>
                <tr >
                  <td height="25" colspan="4" align="right" class="row-white"><input name="Submit2" type="button" class="formbutton" value="View Application Details" onclick="viewStudent('<bean:write name="residentStudentInfoForm" property="rto.applNo1"/>','<bean:write name="residentStudentInfoForm" property="rto.year"/>','<bean:write name="residentStudentInfoForm" property="rto.courseId"/>')"/></td>
                  </tr>

            </table></td>
            <td width="5"  background="images/right.gif"></td>
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
        <td height="25" valign="top" background="images/Tright_03_03.gif"></td>
        <td class="heading"><bean:message key="knowledgepro.hostel.disciplinary.details"/></td>
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
                  <td height="25" class="row-odd" >Application No</td>
                  <td class="row-odd" ><bean:message key="knowledgepro.fee.studentname"/></td>
                  <td class="row-odd" ><bean:message key="knowledgepro.hostel.disciplinary.type"/></td>
                  <td class="row-odd" ><bean:message key="knowledgepro.inventory.salvageitem.date.label"/></td>
                  </tr>
               	<logic:notEmpty name="residentStudentInfoForm" property="rto.disciplinaryToList">
               	<logic:iterate id="dto" name="residentStudentInfoForm" property="rto.disciplinaryToList">
               	 <tr >
                  <td width="21%" height="25" class="row-even" ><bean:write name="dto" property="applNo"/></td>
                  <td width="28%" class="row-even" ><bean:write name="dto" property="studentName"/></td>
                  <td width="16%" class="row-even" ><bean:write name="dto" property="name"/></td>
                  <td width="11%" class="row-even" ><bean:write name="dto" property="date"/></td>
                  </tr>
                  </logic:iterate>
               </logic:notEmpty>
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
        <td height="25" valign="top" background="images/Tright_03_03.gif"></td>
        <td class="heading"><bean:message key="employee.info.job.leave.title"/></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="26" valign="top" background="images/Tright_03_03.gif"></td>
        <td class="news"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td ><img src="images/01.gif" width="5" height="5" /></td>
            <td width="914" background="images/02.gif"></td>
            <td><img src="images/03.gif" width="5" height="5" /></td>
          </tr>
          <tr>
            <td width="5"  background="images/left.gif"></td>
            <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
                <tr >
                  <td height="25" class="row-odd" ><bean:message key="knowledgepro.fee.studentname"/></td>
                  <td class="row-odd" ><bean:message key="knowledgepro.admin.sec.LeaveType"/></td>
                  <td class="row-odd" ><bean:message key="knowledgepro.feepays.Reason"/></td>
                  <td class="row-odd" ><bean:message key="knowledgepro.admission.startDate"/></td>
                  <td class="row-odd" ><bean:message key="knowledgepro.admission.endDate"/></td>
                  </tr>
                  <logic:notEmpty name="residentStudentInfoForm" property="rto.leaveList">
                  <logic:iterate id="lto" name="residentStudentInfoForm" property="rto.leaveList">
                  <tr >
                  <td width="28%" height="25" class="row-even" ><bean:write name="lto" property="studentName"/></td>
                  <td width="16%" class="row-even" ><bean:write name="lto" property="name"/></td>
                  <td width="18%" class="row-even" ><bean:write name="lto" property="reasons"/></td>
                  <td width="13%" class="row-even" ><bean:write name="lto" property="startDate"/></td>
                  <td width="13%" class="row-even" ><bean:write name="lto" property="endDate"/></td>
                  </tr>
                  </logic:iterate>
                  </logic:notEmpty>
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
        <td height="25" valign="top" background="images/Tright_03_03.gif"></td>
        <td class="heading"><bean:message key="knowledgepro.attendanceentry.attendance"/></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="26" valign="top" background="images/Tright_03_03.gif"></td>
        <td class="news"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td ><img src="images/01.gif" width="5" height="5" /></td>
            <td width="914" background="images/02.gif"></td>
            <td><img src="images/03.gif" width="5" height="5" /></td>
          </tr>
          <tr>
            <td width="5"  background="images/left.gif"></td>
            <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
                <tr >
                  <td height="25" class="row-odd" ><bean:message key="knowledgepro.hostel.resident.monthYear"/></td>
                  <td class="row-odd" ><bean:message key="knowledgepro.hostel.resident.noOfDaysAbsent"/></td>
                  </tr>
                  <logic:notEmpty name="residentStudentInfoForm" property="rto.list">
                  <logic:iterate id="ato" name="residentStudentInfoForm" property="rto.list">
                <tr >
                  <td width="39%" height="25" class="row-even" ><bean:write name="ato" property="monthYear"/></td>
                  <td width="39%" class="row-even" ><bean:write name="ato" property="noOfAbsance"/></td>
                  </tr>
                  </logic:iterate>
                </logic:notEmpty>
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
        <td valign="top" class="news"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="48%" height="35" align="center"><html:button property=""	styleClass="formbutton" value="Cancel" onclick="cancelAction()"></html:button></td>
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
