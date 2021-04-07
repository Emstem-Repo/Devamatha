<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<head>
<script type="text/javascript">
function hideTeacherSubject(id) {
	document.location.href = "HiddenSubjectTeacher.do?method=hideTeacherSubject&id="+id;
	}
function showTeacherSubject(id) {
	document.location.href = "HiddenSubjectTeacher.do?method=showTeacherSubject&id="+id;
	}
function SearchTeacherSubject() {
		document.getElementById("method").value="searchTeacherSubject";
	
}
function getClasses(year) {
	
	getClassesByYear("classMap", year, "classesId", updateClasses);
}
function updateClasses(req) {
	updateOptionsFromMap(req, "classesId", "- Select -");
}
function resetErrorMsgs() {
	resetErrMsgs();
	document.getElementById("classesId").value = "";
	document.getElementById("year").value = "";
}
function cancel() {
	document.location.href = "LoginAction.do?method=loginAction";
}
</script>
</head>
<html:form action="/HiddenSubjectTeacher" method="POST">
<html:hidden property="method" styleId="method" value="searchTeacherSubject"/>
<html:hidden property="formName" value="evaHiddenSubjectTeacherForm" />
<html:hidden property="pageType" value="1" />
<table width="99%" border="0">
  
  <tr>
    <td><span class="Bredcrumbs"><bean:message key="knowledgepro.studentFeedBack"/> <span class="Bredcrumbs">&gt;&gt; <bean:message key="knowledgepro.hideteacher.subject"/> &gt;&gt;</span> </span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="heading_white" ><bean:message key="knowledgepro.hideteacher.subject"/> </td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><div align="right"><span class='MandatoryMark'><bean:message
					key="knowledgepro.mandatoryfields" /></span></div>
			<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
				<FONT color="green"><html:messages id="msg" property="messages" message="true">
					<c:out value="${msg}" escapeXml="false"></c:out><br>
					</html:messages> </FONT></div></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="44" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
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
              <td height="25" class="row-odd"><div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message key="knowledgepro.admin.year" />:</div></td>
              <td align="left" height="25" class="row-even"><input type="hidden" id="tempyear" name="tempyear" 	value="<bean:write name="evaHiddenSubjectTeacherForm" property="year"/>" />
			  <html:select property="year"  styleId="year" onchange="getClasses(this.value)" styleClass="combo">
			  <html:option value=" "><bean:message key="knowledgepro.admin.select" /></html:option>
			  <cms:renderAcademicYear></cms:renderAcademicYear>
			  </html:select></td>
              <td height="25" class="row-odd"><div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message key="knowledgepro.attendance.activityattendence.class" />:</div></td>       
              <td align="left" width="30%" class="row-even"><html:select property="classesId" styleClass="combo" styleId="classesId">
			  <html:option value=""><bean:message key="knowledgepro.select" />-</html:option><html:optionsCollection name="evaHiddenSubjectTeacherForm" property="classMap" label="value" value="key" />
			  </html:select></td>
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
             <td height="122" valign="top" background="images/Tright_03_03.gif"></td>
            <td valign="top" class="news"><table width="100%" cellspacing="1" cellpadding="2">
           <tr bgcolor="#FFFFFF">
            <td width="100%" height="20" colspan="4">
            <table width="100%"  border="0" cellspacing="0" cellpadding="0">
           <tr>
		   <td height="35" align="center">
		   <html:submit styleClass="formbutton"  onclick="SearchTeacherSubject"><bean:message key="knowledgepro.submit"/> </html:submit>&nbsp;&nbsp;&nbsp;
		   <html:button property="" styleClass="formbutton" value="Reset" onclick="resetErrorMsgs()"></html:button>&nbsp;&nbsp;&nbsp;
		   <html:button property="" styleClass="formbutton" value="Cancel" onclick="cancel()"></html:button></td>
		   </tr>
           </table></td>
          </tr>
          <logic:notEmpty name="evaHiddenSubjectTeacherForm" property="teacherClassSubjectList">
            <tr>
            <td><bean:message key="knowledgepro.hideteacherFor.subject"/> &nbsp;<bean:write name="evaHiddenSubjectTeacherForm" property="className" /></td>
            </tr>
          <tr>
            <td height="45" colspan="4" ><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
              <tr>
                <td ><img src="images/01.gif" width="5" height="5" /></td>
                <td width="914" background="images/02.gif"></td>
                <td><img src="images/03.gif" width="5" height="5" /></td>
              </tr>
              <tr>
                <td width="5"  background="images/left.gif"></td>
                <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
                  <tr >
                    <td height="5%" class="row-odd" ><div align="center"><bean:message key="knowledgepro.slno"/></div></td>
                    <td height="30%" class="row-odd" align="center" ><bean:message key="knowledgepro.attendanceentry.teacher"/></td>
                    <td height="55%" class="row-odd" align="center"><bean:message key="knowledgepro.admin.eligibility.criteria.subjects"/></td>
                    <td height="10%" class="row-odd" align="center"><bean:message key="knowledgepro.hide"/></td>
                 </tr>
                <c:set var="temp" value="0"/>
                <logic:iterate id="CME" name="evaHiddenSubjectTeacherForm" property="teacherClassSubjectList" indexId="count">
                <c:choose>
                   <c:when test="${temp == 0}">
                   	<tr>
                   		<td width="5%" height="25" class="row-even" ><div align="center"><c:out value="${count + 1}"/></div></td>
                   		<td width="30%" height="25" class="row-even" align="center"><bean:write name="CME" property="teacherName"/></td>
                   		<td width="55%" height="25" class="row-even" align="left"><bean:write name="CME" property="subjectName"/></td>
                   		<td width="10%" height="25" class="row-even" ><div align="center">
			            <img src="images/cancel_icon.gif" width="17" height="13" style="cursor:pointer" onclick="hideTeacherSubject('<bean:write name="CME" property="id"/>')"></div></td>
                   	</tr>
                    <c:set var="temp" value="1"/>
                   	</c:when>
                    <c:otherwise>
		            <tr>
               			<td width="5%" height="25" class="row-white"><div align="center"><c:out value="${count + 1}"/></div></td>
               			<td width="30%" height="25" class="row-white" align="center"><bean:write name="CME" property="teacherName"/></td>
               			<td width="55%" height="25" class="row-white" align="left"><bean:write name="CME" property="subjectName"/></td>
               			<td width="10%" height="25" class="row-white" ><div align="center">
			            <img src="images/cancel_icon.gif" width="17" height="13" style="cursor:pointer" onclick="hideTeacherSubject('<bean:write name="CME" property="id"/>')"></div></td>
               		</tr>
                    <c:set var="temp" value="0"/>
				  	</c:otherwise>
                  </c:choose>
                </logic:iterate>
                
                </table></td>
                <td width="5" height="30"  background="images/right.gif"></td>
              </tr>
              <tr>
                <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
                <td background="images/05.gif"></td>
                <td><img src="images/06.gif" /></td>
              </tr>
            </table></td>
          </tr>
          </logic:notEmpty>
          
         <tr>
         <td height="26"></td>
         </tr>
         <tr>
         <td height="26"></td>
         </tr>
          
          <logic:notEmpty name="evaHiddenSubjectTeacherForm" property="hiddenTeacherList">
           <tr>
           <td height="30" background="images/Tcenter.gif" class="heading_white" ><bean:message key="knowledgepro.viewteacher.subject"/>&nbsp;<bean:write name="evaHiddenSubjectTeacherForm" property="className" /> </td>
           </tr>
           <tr>
           <td height="10"></td>
          </tr>
          <tr>
            <td height="45" colspan="4" ><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
              <tr>
                <td ><img src="images/01.gif" width="5" height="5" /></td>
                <td width="914" background="images/02.gif"></td>
                <td><img src="images/03.gif" width="5" height="5" /></td>
              </tr>
              <tr>
                <td width="5"  background="images/left.gif"></td>
                <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
                  <tr >
                    <td height="5%" class="row-odd" ><div align="center"><bean:message key="knowledgepro.slno"/></div></td>
                    <td height="30%" class="row-odd" align="center" ><bean:message key="knowledgepro.attendanceentry.teacher"/></td>
                    <td height="55%" class="row-odd" align="center"><bean:message key="knowledgepro.admin.eligibility.criteria.subjects"/></td>
                    <td height="10%" class="row-odd" align="center"><bean:message key="knowledgepro.delete"/></td>
                 </tr>
                <c:set var="temp" value="0"/>
                <logic:iterate id="CME" name="evaHiddenSubjectTeacherForm" property="hiddenTeacherList" indexId="count">
                <c:choose>
                   <c:when test="${temp == 0}">
                   	<tr>
                   		<td width="5%" height="25" class="row-even" ><div align="center"><c:out value="${count + 1}"/></div></td>
                   		<td width="30%" height="25" class="row-even" align="center"><bean:write name="CME" property="teacherName"/></td>
                   		<td width="55%" height="25" class="row-even" align="left"><bean:write name="CME" property="subjectName"/></td>
                   		<td width="10%" height="25" class="row-even" ><div align="center">
			            <img src="images/delete_icon.gif" width="20" height="16" style="cursor:pointer" onclick="showTeacherSubject('<bean:write name="CME" property="id"/>')"></div></td>
                   	</tr>
                    <c:set var="temp" value="1"/>
                   	</c:when>
                    <c:otherwise>
		            <tr>
               			<td width="5%" height="25" class="row-white"><div align="center"><c:out value="${count + 1}"/></div></td>
               			<td width="30%" height="25" class="row-white" align="center"><bean:write name="CME" property="teacherName"/></td>
               			<td width="55%" height="25" class="row-white" align="left"><bean:write name="CME" property="subjectName"/></td>
               			<td width="10%" height="25" class="row-white" ><div align="center">
			            <img src="images/delete_icon.gif" width="20" height="16" style="cursor:pointer" onclick="showTeacherSubject('<bean:write name="CME" property="id"/>')"></div></td>
               		</tr>
                    <c:set var="temp" value="0"/>
				  	</c:otherwise>
                  </c:choose>
                </logic:iterate>
                
                </table></td>
                <td width="5" height="30"  background="images/right.gif"></td>
              </tr>
              <tr>
                <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
                <td background="images/05.gif"></td>
                <td><img src="images/06.gif" /></td>
              </tr>
            </table></td>
          </tr>
          </logic:notEmpty>
        </table></td>
        <td width="10" valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="26" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"></td>
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