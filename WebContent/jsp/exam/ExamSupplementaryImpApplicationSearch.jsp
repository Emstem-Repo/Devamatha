<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@page import="java.util.Map,java.util.HashMap"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>

<script language="JavaScript" src="js/ajax/AjaxUtil.js"></script>
<script language="JavaScript" src="js/ajax/Ajax.js"></script>
<script type="text/javascript">

function editRevApp(studentId) {
	var sup=document.getElementById("supplementaryImprovement").value;
	var courseId=document.getElementById("courseId").value;
	var schemeNo=document.getElementById("schemeNo").value;
	document.location.href = "ExamSupplementaryImpApp.do?method=editSupplementaryImpApp&studentId="+studentId+"&supplementaryImprovement="+sup+"&courseId="+courseId+"&schemeNo="+schemeNo;
}


function deleteRevApp(studentId,name) {
	deleteConfirm =confirm("Are you sure to delete "+name+" this entry?");
	if(deleteConfirm)
	{
		var courseId=document.getElementById("courseId").value;
		var schemeNo=document.getElementById("schemeNo").value;
	document.location.href = "ExamSupplementaryImpApp.do?method=deleteExamSupplementaryImpApp&studentId="+studentId+"&courseId="+courseId+"&schemeNo="+schemeNo;;
	}
}
function goToHomePage() {
	document.location.href = "ExamSupplementaryImpApp.do?method=initSupplementaryImpApplication";
}

</script>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html:form action="/ExamSupplementaryImpApp.do">
	<html:hidden property="formName" value="ExamSupplementaryImpAppForm" />
    <html:hidden property="pageType" value="2" />
    <html:hidden property="courseId" styleId="courseId"  />
	<html:hidden property="schemeNo" styleId="schemeNo"  />
	
	<html:hidden property="supplementaryImprovement" styleId="supplementaryImprovement"  />
<table width="100%" border="0">
  <tr>
    <td><span class="heading"><bean:message	key="knowledgepro.exam.exam" /> <span class="Bredcrumbs">&gt;&gt;  <bean:message key="knowledgepro.exam.supplementaryApplication" />&gt;&gt;</span></span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="body" ><strong class="boxheader"><bean:message key="knowledgepro.exam.supplementaryApplication" /></strong></td>
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
                <tr>
             <td  class="row-odd" height="28" ><div align="right"><bean:message key="knowledgepro.exam.supplementaryApplication.suppImp" /></div></td>
                 <td width="206" class="row-even" ><bean:write name="ExamSupplementaryImpAppForm" property="supplementaryImprovement" /></td>
               
                 <td  class="row-odd" height="28" ><div align="right"><bean:message key="knowledgepro.exam.revaluationApplication.examName" /> :</div></td>
                <td  class="row-even"><bean:write name="ExamSupplementaryImpAppForm" property="examName" /></td>
                </tr>
               
                <tr>
                <td height="26" class="row-odd"><div align="right" ><bean:message key="knowledgepro.exam.revaluationApplication.course" /> :</div></td>
                <td class="row-even" style="width:195px" ><bean:write name="ExamSupplementaryImpAppForm" property="courseName" /></td>
                   <td width="194" class="row-odd" ><div align="right"><bean:message key="knowledgepro.exam.revaluationApplication.scheme" />  :</div></td>
                <td width="222" class="row-even" ><bean:write name="ExamSupplementaryImpAppForm" property="schemeName" /></td>
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
        <td height="20" class="news"><div align="right" class="mandatoryfield"></div></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
         
        
        
        
        
        
         <tr>
							<td height="45" colspan="4">
							<table width="98%" border="0" align="center" cellpadding="0"
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
											<td class="row-odd">
											<div align="center"><bean:message
												key="knowledgepro.slno" /></div>
											</td>
											<td height="25" class="row-odd" align="left"><bean:message
												key="knowledgepro.exam.supplementaryImpApplication.class" /></td>
											<td width="15%" height="25" class="row-odd" align="left"><bean:message
												key="knowledgepro.exam.UpdateExcludeWithheld.rollNo" /></td>
											<td width="15%" height="25" class="row-odd" align="left"><bean:message
												key="knowledgepro.exam.UpdateExcludeWithheld.registerNo" /></td>
											
											<td width="30%" height="25" class="row-odd" align="left"><bean:message
												key="knowledgepro.exam.UpdateExcludeWithheld.studentName" /> </td>
											<td class="row-odd">
											<div align="center"><bean:message
												key="knowledgepro.edit" /></div>
											</td>
											<td class="row-odd">
											<div align="center"><bean:message
												key="knowledgepro.delete" /></div>
											</td>
											
										</tr>
										
										
										
										<c:set var="temp" value="0" />

										<logic:iterate name="ExamSupplementaryImpAppForm"
											property="listStudentName" id="listStudentName"
											type="com.kp.cms.to.exam.ExamSupplementaryImpApplicationTO"
											indexId="count">
											<c:choose>
												<c:when test="${temp == 0}">
													<tr>
														<td  height="25" class="row-even">
														<div align="center"><c:out value="${count+1}" /></div>
														</td>
														<td  height="25" class="row-even"
															align=left><bean:write name="listStudentName"
															property="className" /></td>
														<td height="25" class="row-even"
															align="left"><bean:write name="listStudentName"
															property="rollNumber" /></td>
														<td  height="25" class="row-even"
															align=left><bean:write name="listStudentName"
															property="regNumber" /></td>
														
														<td  height="25" class="row-even"
															align="left"><bean:write name="listStudentName"
															property="studentName" /></td>
														
														<td width="11%" height="25" class="row-even">
														<div align="center"><img src="images/edit_icon.gif"
															height="18" style="cursor: pointer"
															onclick="editRevApp('<bean:write name="listStudentName" property="studentId"/>')">
														</div>
														</td>
														<td height="25" class="row-even">
														<div align="center"><img
															src="images/delete_icon.gif" width="16" height="16"
															style="cursor: pointer"
															onclick="deleteRevApp('<bean:write name="listStudentName" property="studentId"/>','<bean:write name="listStudentName" property="studentName"/>')"></div>
														</td>
													
													<c:set var="temp" value="1" />
												</c:when>
												<c:otherwise>
													<tr>
														<td height="25" class="row-white">
														<div align="center"><c:out value="${count+1}" /></div>
														</td>
														<td  height="25" class="row-white"
															align="left"><bean:write name="listStudentName"
															property="className" /></td>
														<td  height="25" class="row-white"
															align="left"><bean:write name="listStudentName"
															property="rollNumber" /></td>	
														<td  height="25" class="row-white"
															align="left"><bean:write name="listStudentName"
															property="regNumber" /></td>
														
														<td  height="25" class="row-white"
															align="left"><bean:write name="listStudentName"
															property="studentName" /></td>
														<td height="25" class="row-white">
														<div align="center"><img src="images/edit_icon.gif"
															height="18" style="cursor: pointer"
															onclick="editRevApp('<bean:write name="listStudentName" property="studentId"/>')">
														</div>
														</td>
														<td height="25" class="row-white">
														<div align="center"><img
															src="images/delete_icon.gif" width="16" height="16"
															style="cursor: pointer"
															onclick="deleteRevApp('<bean:write name="listStudentName" property="studentId"/>','<bean:write name="listStudentName" property="studentName"/>')"></div>
														</td>
														
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
						<tr>
						<td>&nbsp;
						</td>
						</tr>
        				<tr>
				         	<td width="51%"><div align="center">
				            	<html:button property=""  styleClass="formbutton" value="Close" onclick="goToHomePage()"></html:button>
							</div></td>
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