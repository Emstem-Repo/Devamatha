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
function cancel() {
	document.location.href = "LoginAction.do?method=loginAction";
}
function SearchTeacher() {
	document.getElementById("method").value="searchTeacherByDepartmentWise";
}
function resetErrorMsgs() {
	resetErrMsgs();
	document.getElementById("departmentId").value = "";
}
function hideTeacher(userId) {
	var deptId = document.getElementById("departmentId").value;
	document.location.href = "hideTeachersPeersEvaluation.do?method=hideTeacherForPeersEvaluation&teacherId="+userId+"&departmentId="+deptId;
	}
function deleteTeacher(teacherId,deptId){
	
	document.location.href = "hideTeachersPeersEvaluation.do?method=deleteHiddenTeachers&teacherId="+teacherId+"&departmentId="+deptId;
}
</script>
</head>
<html:form action="/hideTeachersPeersEvaluation" method="POST">
<html:hidden property="formName" value="hideTeachersForPeersEvaluationForm" />
<html:hidden property="pageType" value="1" />
<html:hidden property="method" styleId="method" value="searchTeacherByDepartmentWise"/>
<table width="99%" border="0">
  
  <tr>
    <td><span class="Bredcrumbs">Faculty Evaluation <span class="Bredcrumbs">&gt;&gt; Hide Teachers For Peers Evaluation</span> </span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="heading_white" >Hide Teachers For Peers Evaluation</td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><div align="right"><span class='MandatoryMark'><bean:message
					key="knowledgepro.mandatoryfields" /></span></div>
			<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
				<FONT color="green"><html:messages id="msg" property="messages" message="true">
					<c:out value="${msg}" escapeXml="false"></c:out><br>
					</html:messages> </FONT></div>
					</td>
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
              <td height="25" class="row-odd"><div align="right"><span class="Mandatory">*</span>&nbsp;Department:</div></td>       
              <td  align="left" class="row-even" colspan="4">
               <input type="hidden" id="deptId" name="deptId" value="<bean:write name="hideTeachersForPeersEvaluationForm" property="departmentId"/>" />
              <html:select property="departmentId" styleId="departmentId"  styleClass="comboBig" onchange="getFaculty(this.value)" style="width: 350px;" >
			 <html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
			<html:optionsCollection name="hideTeachersForPeersEvaluationForm" property="departmentList" label="name" value="id"  />
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
		   <html:submit  styleClass="formbutton"  onclick="SearchTeacher()"><bean:message key="knowledgepro.submit"/> </html:submit>&nbsp;&nbsp;&nbsp;
		   <html:button property="" styleClass="formbutton" value="Reset" onclick="resetErrorMsgs()"></html:button>&nbsp;&nbsp;&nbsp;
		   <html:button property="" styleClass="formbutton" value="Cancel" onclick="cancel()"></html:button></td>
		   </tr>
           </table></td>
          </tr>
          <logic:notEmpty name="hideTeachersForPeersEvaluationForm" property="teachersMap" >
            <tr>
            <td>Teachers of Department&nbsp;<bean:write name="hideTeachersForPeersEvaluationForm" property="deptartmentName"/></td>
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
                    <td height="30%" class="row-odd" align="center" >Teacher Name</td>
                    <td height="10%" class="row-odd" align="center"><bean:message key="knowledgepro.hide"/></td>
                 </tr>
                <c:set var="temp" value="0"/>
                <logic:iterate id="CME" name="hideTeachersForPeersEvaluationForm" property="teachersMap" indexId="count"  >
                <c:choose>
                   <c:when test="${temp == 0}">
                   	<tr>
                   		<td width="5%" height="25" class="row-even" ><div align="center"><c:out value="${count + 1}"/></div></td>
                   		<td width="30%" height="25" class="row-even" align="center"><bean:write name="CME" property="value"/></td>
                   		<td width="10%" height="25" class="row-even" ><div align="center">
			            <img src="images/cancel_icon.gif" width="17" height="13" style="cursor:pointer" onclick="hideTeacher('<bean:write name="CME" property="key"/>')"></div></td>
                   	</tr>
                    <c:set var="temp" value="1"/>
                   	</c:when>
                    <c:otherwise>
		            <tr>
               			<td width="5%" height="25" class="row-white"><div align="center"><c:out value="${count + 1}"/></div></td>
               			<td width="30%" height="25" class="row-white" align="center"><bean:write name="CME" property="value"/></td>
               			<td width="10%" height="25" class="row-white" ><div align="center">
			            <img src="images/cancel_icon.gif" width="17" height="13" style="cursor:pointer" onclick="hideTeacher('<bean:write name="CME" property="key"/>')"></div></td>
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
          
          <logic:notEmpty name="hideTeachersForPeersEvaluationForm" property="hiddenTeachersList">
           <tr>
           <td>Hidden Teachers List for &nbsp;<bean:write name="hideTeachersForPeersEvaluationForm" property="deptartmentName"/></td>
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
                    <td height="30%" class="row-odd" align="center" >Teacher Name</td>
                    <td height="10%" class="row-odd" align="center"><bean:message key="knowledgepro.delete"/></td>
                 </tr>
                <c:set var="temp" value="0"/>
                <logic:iterate id="teacher" name="hideTeachersForPeersEvaluationForm" property="hiddenTeachersList" indexId="count">
                <c:choose>
                   <c:when test="${temp == 0}">
                   	<tr>
                   		<td width="5%" height="25" class="row-even" ><div align="center"><c:out value="${count + 1}"/></div></td>
                   		<td width="30%" height="25" class="row-even" align="center"><bean:write name="teacher" property="teacherName"/></td>
                   		<td width="10%" height="25" class="row-even" ><div align="center">
			            <img src="images/delete_icon.gif" width="20" height="16" style="cursor:pointer" onclick="deleteTeacher('<bean:write name="teacher" property="teacherId"/>','<bean:write name="teacher" property="departmentId"/>')"></div></td>
                   	</tr>
                    <c:set var="temp" value="1"/>
                   	</c:when>
                    <c:otherwise>
		            <tr>
               			<td width="5%" height="25" class="row-white"><div align="center"><c:out value="${count + 1}"/></div></td>
               			<td width="30%" height="25" class="row-white" align="center"><bean:write name="teacher" property="teacherName"/></td>
               			<td width="10%" height="25" class="row-white" ><div align="center">
			            <img src="images/delete_icon.gif" width="20" height="16" style="cursor:pointer" onclick="deleteTeacher('<bean:write name="teacher" property="teacherId"/>','<bean:write name="teacher" property="departmentId"/>')"></div></td>
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
