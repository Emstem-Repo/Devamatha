<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@page import="java.util.Map,java.util.HashMap"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>

<script type="text/javascript">
function editGD(id) {
	document.location.href = "ExamSGPADefinition.do?method=editGCD&id="+id;
	document.getElementById("submit").value="Update";
	
}
function deleteGD(id,name) {
	deleteConfirm =confirm("Are you sure to delete "+ name +" this entry?");
	if(deleteConfirm)
	{
		document.location.href = "ExamSGPADefinition.do?method=deleteGCD&id="+id;
	}
}


function resetMessages() {
    document.getElementById("id").selectedIndex =-1;
	resetErrMsgs();
}
</script>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html:form action="/ExamSGPADefinition.do">
	<html:hidden property="formName" value="ExamSGPADefinitionForm" />
    <html:hidden property="pageType" value="1" />
	<c:choose>
		<c:when
			test="${examGDOperation != null && examGDOperation == 'edit'}">
			<html:hidden property="method" styleId="method" value="updateGD" />
		</c:when>
		<c:otherwise>
			<html:hidden property="method" styleId="method" value="addGD" />
		</c:otherwise>
	</c:choose>
<table width="100%" border="0">
  <tr>
    <td><span class="heading"><bean:message key="knowledgepro.exam.exam" /> <span class="Bredcrumbs">&gt;&gt; SGPA Grade Definition &gt;&gt;</span></span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="body" ><strong class="boxheader"> SGPA Grade Definition </strong></td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td height="20" class="news">
        <div id="errorMessage"><FONT color="red"><html:errors /></FONT>
					<FONT color="green"> <html:messages id="msg"
						property="messages" message="true">
						<c:out value="${msg}" escapeXml="false"></c:out>
						<br>
					</html:messages> </FONT></div>
        
        
        <div align="right" class="mandatoryfield"><bean:message key="knowledgepro.mandatoryfields" /></div></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
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
                <td width="25%" height="25" class="row-odd" ><div align="right"><span class="Mandatory">*</span> <bean:message key="knowledgepro.exam.gradeDefinition.course" />  :</div></td>
                <td  height="5" class="row-even"><nested:select
										property="courseName" styleClass="body"
										multiple="multiple" size="8" styleId="id" style="width:500px">
										
										<nested:optionsCollection name="ExamSGPADefinitionForm"
											property="listCourseName" label="display" value="id" />
									</nested:select></td>
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
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="50%" height="35"><div align="right">
                 &nbsp;
                 <input name="button2" type="submit" class="formbutton" value="Add Definition" />
            </div></td>
            <td width="2%"></td>
            <td width="48%"><input type="Reset" class="formbutton" value="Reset" onclick="resetMessages()"/></td>
          </tr>
        </table> </td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
       
       
       
       
       
       
       
       <tr>
			<td height="45" colspan="4">
				<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
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
								<td height="25" class="row-odd"><div align="center"><bean:message key="knowledgepro.slno" /></div></td>
								<td height="25" class="row-odd" align="center"><bean:message key="knowledgepro.exam.gradeDefinition.course" /></td>
								<td class="row-odd"><div align="center"><bean:message key="knowledgepro.edit" /></div></td>
								<td class="row-odd"><div align="center"><bean:message key="knowledgepro.delete" /></div></td>
							</tr>
							
							<c:set var="temp" value="0" />
								<logic:iterate name="ExamSGPADefinitionForm" property="listExamCourseGroup" id="listExamCourseGroup" type="com.kp.cms.to.exam.KeyValueTO"	indexId="count">
								<c:choose>
	    							<c:when test="${temp == 0}">
									<tr>
										<td width="6%" height="25" class="row-even"><div align="center"><c:out value="${count + 1}" /></div></td>
										<td width="41%" height="25" class="row-even" align="left"><bean:write	name="listExamCourseGroup" property="display" /></td>
										<td width="6%" height="25" class="row-even"><div align="center"><img src="images/edit_icon.gif"	width="16" height="18" style="cursor: pointer" onclick="editGD('<bean:write name="listExamCourseGroup" property="id"/>')"></div></td>
										<td width="6%" height="25" class="row-even"><div align="center"><img src="images/delete_icon.gif" width="16" height="16" style="cursor: pointer" onclick="deleteGD('<bean:write name="listExamCourseGroup" property="id"/>','<bean:write name="listExamCourseGroup" property="display"/>')"></div></td>
									</tr>
								<c:set var="temp" value="1" />
									</c:when>
										<c:otherwise>
									<tr>
										<td width="6%" height="25" class="row-white"><div align="center"><c:out value="${count + 1}" /></div></td>
										<td width="41%" height="25" class="row-white" align="left"><bean:write name="listExamCourseGroup" property="display" /></td>
										<td width="6%" height="25" class="row-white"><div align="center"><img src="images/edit_icon.gif" width="16" height="18" style="cursor: pointer"	onclick="editGD('<bean:write name="listExamCourseGroup" property="id"/>')"></div></td>
										<td width="6%" height="25" class="row-white"><div align="center"><img src="images/delete_icon.gif" width="16" height="16" style="cursor: pointer" onclick="deleteGD('<bean:write name="listExamCourseGroup" property="id"/>','<bean:write name="listExamCourseGroup" property="display"/>')"></div></td>
									</tr>
								  <c:set var="temp" value="0" />
							      </c:otherwise>
					      </c:choose>
				       </logic:iterate>

									</table>
									</td>
									<td width="5" height="30" background="images/right.gif"></td>
								</tr>
								<tr>
									<td height="5"><img src="images/04.gif" width="5"
										height="5" /></td>
									<td background="images/05.gif"></td>
									<td><img src="images/06.gif" /></td>
								</tr>
							</table>
							</td>
						</tr>
       
       
       
       
       
       
       
       
       
       
       
       
       
       
         
        </table></td>
        <td width="10" valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="10" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news">&nbsp;</td>
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