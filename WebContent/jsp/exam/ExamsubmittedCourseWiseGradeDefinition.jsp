<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>

<link href="../css/styles.css" rel="stylesheet" type="text/css">
<script type="text/javascript">

function isValidNumber(field) 
{
	if (isNaN(field.value)) {
		field.value = "";
	}
}

function resetErrorMsgs()
{
	
	document.getElementById("msg").value=" ";
	document.getElementById("errorMessage").value=" ";
}

function editGD(id)
{
	var courseId = document.getElementById("courseId").value;
	document.location.href = "ExamSubjectDefinitionCourseWise.do?method=editGCD&id="+id+ "&courseId=" + courseId;
	document.getElementById("submit").value="Update";
}
function deleteGD(id,subject_Name)
{
	 var subjectCode = document.getElementById("subjectCode").value;
	 var subjectName = document.getElementById("subjectName").value;
		var subId = document.getElementById("subId").value;
		var courseId = document.getElementById("courseId").value;
	
	deleteConfirm =confirm("Are you sure to delete  this entry?");
	if(deleteConfirm)
	{
	 		
		document.location.href = "ExamSubjectDefinitionCourseWise.do?method=deleteGDAdd&subjectName="
				+ subjectName
				+ "&subjectCode="
				+ subjectCode
				+ "&subId="
				+ subId + "&id=" + id+ "&courseId=" + courseId;

	  
	}
}
function resetMessages() {
	
	document.getElementById("startPercentage").value =document.getElementById("orgStartPercentage").value ;
	document.getElementById("endPercentage").value =document.getElementById("orgEndPercentage").value;
	document.getElementById("grade").value =document.getElementById("orgGrade").value ;
	document.getElementById("interpretation").value =document.getElementById("orgInterpretation").value;
	document.getElementById("resultClass").value =document.getElementById("orgResultClass").value;
	document.getElementById("gradePoint").value =document.getElementById("orgGradePoint").value;

	
	
	document.getElementById("selectedCourse").selectedIndex =name;
	resetErrMsgs();
	
	
}

function cancelAction(){
	document.location.href = "ExamSubjectDefinitionCourseWise.do?method=editSubjectInfo";
}

</script>

<html:form action="/ExamSubjectDefinitionCourseWise.do" method="POST">
<html:hidden property="formName" value="ExamSubjectDefCourseForm" />
<html:hidden property="pageType" value="3" />
<html:hidden property="subjectCode"  styleId="subjectCode" /> 
<html:hidden property="subjectName" styleId="subjectName" />
<html:hidden property="subId" styleId="subId" name="ExamSubjectDefCourseForm"/> 
<html:hidden property="courseId" styleId="courseId" />
<html:hidden property="subId" /> 

<c:choose>
		<c:when
			test="${examGDOperation != null && examGDOperation == 'edit'}">
			<html:hidden property="method" styleId="method" value="updateGDAdd" />
			
			<html:hidden property="id" styleId="id" />
             <html:hidden property="subjectCode"  styleId="subjectCode" /> 
           <html:hidden property="subjectName" styleId="subjectName" />
            <html:hidden property="subId" styleId="subId" name="ExamSubjectDefCourseForm"/>
			
			<html:hidden property="orgSubid" styleId="orgSubid" />
			<html:hidden property="orgStartPercentage" styleId="orgStartPercentage" />
			<html:hidden property="orgEndPercentage" styleId="orgEndPercentage" />
			<html:hidden property="orgGrade" styleId="orgGrade" />
			<html:hidden property="orgInterpretation" styleId="orgInterpretation" />
			<html:hidden property="orgResultClass" styleId="orgResultClass" />
			<html:hidden property="orgGradePoint" styleId="orgGradePoint" />
			
		
		</c:when>
		<c:otherwise>
             <html:hidden property="subjectCode"  styleId="subjectCode" /> 
           <html:hidden property="subjectName" styleId="subjectName" />
            <html:hidden property="subId" styleId="subId" name="ExamSubjectDefCourseForm"/> 
           
			<html:hidden property="method" styleId="method" value="addGDAdd" />
		</c:otherwise>
	</c:choose>
<table width="100%" border="0">
  <tr>
    <td><span class="Bredcrumbs"><bean:message
				key="knowledgepro.exam" /> <span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.exam.subjectDefinitionCourseWise" /> &gt;&gt;</span></span></td>
	</tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="body" ><strong class="boxheader"><bean:message key="knowledgepro.exam.subjectDefinitionCourseWise"/></strong></td>
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
					<div align="right" class="mandatoryfield">*Mandatory fields</div>
					</td>
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
                <td width="23%" height="25" class="row-odd" ><div align="right">Subject Code </div></td>
                <td height="25"  class="row-even" ><bean:write name="ExamSubjectDefCourseForm" property="subjectCode"/></td>
                 <td width="23%" height="25" class="row-odd" ><div align="right">Subject Name </div></td>
                <td height="25"  class="row-even" ><bean:write name="ExamSubjectDefCourseForm" property="subjectName" /></td>
                </tr>
                <tr >
                <td height="25" class="row-odd" ><div align="right"><span class="Mandatory">*</span> Start %:</div></td>
                <td height="25" class="row-even" ><html:text property="startPercentage" styleId="startPercentage" size="20" maxlength="6" onblur="isValidNumber(this)"/></td>
                <td class="row-odd"><div align="right"><span class="Mandatory">*</span> End %:</div></td>
                <td class="row-even"><html:text property="endPercentage" styleId="endPercentage" size="20" maxlength="6" onblur="isValidNumber(this)"/></td>
              </tr>
              <tr >
                <td height="25" class="row-odd" ><div align="right"><span class="Mandatory">*</span> Grade :</div></td>
                <td width="23%" height="25" class="row-even" ><html:text property="grade" styleId="grade" size="20" maxlength="3"/></td>
                <td width="28%" class="row-odd"><div align="right" >Interpretation:</div></td>
                <td width="26%" class="row-even"><html:text property="interpretation" styleId="interpretation" size="20" styleClass="TextBox"/></td>
              </tr>
              <tr >
                <td height="25" class="row-odd" ><div align="right"><span class="Mandatory">*</span> Result Class :</div></td>
                <td height="25" class="row-even" ><html:text property="resultClass" styleId="resultClass" size="20"/></td>
                <td class="row-odd"><div align="right"><span class="Mandatory">*</span> Grade Point:</div>
                </td>
                <td class="row-even"><html:text property="gradePoint" styleId="gradePoint" size="20"/></td>
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
        <td valign="top" class="news">
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr><td style="height: 10px" align="center" colspan="3"></td></tr>
						<tr>
							<td height="35" align="right">
							<c:choose>
								<c:when
									test="${examGDOperation != null && examGDOperation == 'edit'}">

									<input name="submit" type="submit" class="formbutton"
										value="Update" />
								</c:when>
								<c:otherwise>
									<input name="submit" type="submit" class="formbutton"
										value="Add" />

								</c:otherwise>
							</c:choose></td>
							<td  align="center">&nbsp;</td>
							<td align="left">
							
							
							<c:choose>
								<c:when
									test="${examGDOperation != null && examGDOperation == 'edit'}">

									<input type="button"
								class="formbutton" value="Reset" onclick="resetMessages()"/>
								</c:when>
								<c:otherwise>
									<input type="Reset"
								class="formbutton" value="Reset" onclick="resetErrMsgs()"/>

								</c:otherwise>
							</c:choose>
							<input type="button"
								class="formbutton" value="Cancel" onclick="cancelAction()"/>
							</td>
						</tr>						
						<tr><td style="height: 10px" align="center" colspan="3"></td></tr>						
					</table>
     </td>
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
                <td height="25" class="row-odd" ><div align="center">Sl.No </div></td>
                <td width="27%" height="25" class="row-odd" >Subject Name</td>
                <td class="row-odd" >Start %</td>
                <td class="row-odd" >End%</td>
                <td class="row-odd" >Grade</td>
                <td class="row-odd" >Interpretation</td>
                <td class="row-odd" >Result Class</td>
                <td class="row-odd" >Grade Point</td>
                
                <td class="row-odd"><div align="center">Edit </div></td>
                <td class="row-odd"><div align="center">Delete</div></td>
              </tr>
           
            <c:set var="temp" value="0" />
								<logic:iterate name="ExamSubjectDefCourseForm" property="listGradeDefinition" id="listExamSubCourseGrade" type="com.kp.cms.to.exam.ExamSubCoursewiseGradeDefnTO" indexId="count">
								<c:choose>
							<c:when test="${temp == 0}">
									<tr>
										<td width="6%" height="25" class="row-even"><div align="center"><c:out value="${count + 1}" /></div></td>
										<td width="41%" height="25" class="row-even" align="left"><bean:write name="listExamSubCourseGrade" property="subjectName" /></td>
										<td width="6%" height="25" class="row-even" align="left"><bean:write name="listExamSubCourseGrade" property="startPrcntgGrade" /></td>
										<td width="6%" height="25" class="row-even" align="left"><bean:write name="listExamSubCourseGrade" property="endPrcntgGrade" /></td>
										<td width="6%" height="25" class="row-even" align="left"><bean:write name="listExamSubCourseGrade" property="grade" /></td>
										<td width="35%" height="25" class="row-even" align="left"><bean:write name="listExamSubCourseGrade" property="gradeInterpretation" /></td>
										<td width="41%" height="25" class="row-even" align="left"><bean:write name="listExamSubCourseGrade" property="resultClass" /></td>
										<td width="41%" height="25" class="row-even" align="left"><bean:write name="listExamSubCourseGrade" property="gradePoint" /></td>
										<td width="10%" height="25" class="row-even"><div align="center"><img src="images/edit_icon.gif"	width="16" height="18" style="cursor: pointer" onclick="editGD('<bean:write name="listExamSubCourseGrade" property="id"/>')"></div></td>
										<td width="10%" height="25" class="row-even"><div align="center"><img src="images/delete_icon.gif" width="16" height="16" style="cursor: pointer" onclick="deleteGD('<bean:write name="listExamSubCourseGrade" property="id"/>','<bean:write name="listExamSubCourseGrade" property="subjectName"/>')"></div></td>
									</tr>
								<c:set var="temp" value="1" />
									</c:when>
										<c:otherwise>
									<tr>
										<td width="6%" height="25" class="row-white"><div align="center"><c:out value="${count + 1}" /></div></td>
										<td width="41%" height="25" class="row-white" align="left"><bean:write name="listExamSubCourseGrade" property="subjectName" /></td>
										<td width="6%" height="25" class="row-white" align="left"><bean:write name="listExamSubCourseGrade" property="startPrcntgGrade" /></td>
										<td width="6%" height="25" class="row-white" align="left"><bean:write name="listExamSubCourseGrade" property="endPrcntgGrade" /></td>
										<td width="6%" height="25" class="row-white" align="left"><bean:write name="listExamSubCourseGrade" property="grade" /></td>
										<td width="35%" height="25" class="row-white" align="left"><bean:write name="listExamSubCourseGrade" property="gradeInterpretation" /></td>
										<td width="41%" height="25" class="row-white" align="left"><bean:write name="listExamSubCourseGrade" property="resultClass" /></td>
										<td width="41%" height="25" class="row-white" align="left"><bean:write name="listExamSubCourseGrade" property="gradePoint" /></td>
										<td width="10%" height="25" class="row-white"><div align="center"><img src="images/edit_icon.gif"	width="16" height="18" style="cursor: pointer" onclick="editGD('<bean:write name="listExamSubCourseGrade" property="id"/>')"></div></td>
										<td width="10%" height="25" class="row-white"><div align="center"><img src="images/delete_icon.gif" width="16" height="16" style="cursor: pointer" onclick="deleteGD('<bean:write name="listExamSubCourseGrade" property="id"/>','<bean:write name="listExamSubCourseGrade" property="subjectName"/>')"></div></td>
									</tr>
								  <c:set var="temp" value="0" />
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
