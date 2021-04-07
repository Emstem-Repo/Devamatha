<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<script type="text/javascript" language="javascript">

function addLeaveAppInstructions() {
	document.getElementById("method").value="addTeacherFeedbackSession";
}

function resetErrorMsgs(){
	document.getElementById("name1").value = "";
	document.getElementById("year").value = "";
	document.getElementById("academicYear").value = "";
	document.getElementById("month").value = "";
	if(document.getElementById("method").value == "updateEvaluationTeacherFeedbackSession"){
		document.getElementById("name1").value = document.getElementById("sessionId").value;
		document.getElementById("year").value = document.getElementById("yr").value;
		document.getElementById("academicYear").value= document.getElementById("tempyear").value;
		document.getElementById("month").value = document.getElementById("month1").value;
	}
	resetErrMsgs();
}
function deleteSession(id) {
	deleteConfirm =confirm("Are you sure to delete this entry?");
	document.getElementById("NWID").value=id;
	if(deleteConfirm)
		document.location.href = "evaTeacherFeedbackSession.do?method=deleteTeacherFeedbackSession&id="+id;
}

function editSession(id){
	
	document.getElementById("NWID").value=id;
	document.location.href = "evaTeacherFeedbackSession.do?method=editTeacherFeedbackSession&id="+id;
}
</script>
<html:form action="/evaTeacherFeedbackSession" method="POST">
<html:hidden property="id" styleId="NWID"/>
<html:hidden property="formName" value="evaluationTeacherFeedbackSessionForm" />
<html:hidden property="pageType" value="1" />
<c:choose>
		<c:when test=""></c:when>
	</c:choose>
	<c:choose>
		<c:when test="${sessionList != null && sessionList == 'edit'}">
			<html:hidden property="method" styleId="method" value="updateEvaluationTeacherFeedbackSession" />
		</c:when>
		<c:otherwise>
			<html:hidden property="method" styleId="method" value="addTeacherFeedbackSession" />
		</c:otherwise>
	</c:choose>
<table width="99%" border="0">
  
  <tr>
    <td><span class="Bredcrumbs"><bean:message key="knowledgepro.teacherFeedBack"/> <span class="Bredcrumbs">&gt;&gt; Teacher Feedback Session &gt;&gt;</span> </span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="heading_white" >Teacher Feedback Session</td>
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
            <td valign="top"><table width="100%" cellspacing="1" cellpadding="3">
              
			<tr>
			<td height="25" class="row-odd"><div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message key="knowledgepro.admin.year" />:</div></td>
              <td align="left" height="25" class="row-even"><input type="hidden" id="tempyear" name="tempyear" 	value="<bean:write name="evaluationTeacherFeedbackSessionForm" property="academicYear"/>" />
			  
			  <html:select property="academicYear"  styleId="academicYear" styleClass="combo"  >
			  <html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
			  <cms:renderAcademicYear></cms:renderAcademicYear>
			  </html:select></td>
				
				<td width="21%" height="25" class="row-odd">
				<div align="right"><span class="Mandatory">*</span>&nbsp;Session:</div>
				</td>
				<td height="25" class="row-even" ><label></label><input type="hidden" id="sessionId" name="sessionId" value='<bean:write name="evaluationTeacherFeedbackSessionForm" property="name"/>'>  
									<span class="star"> <html:text property="name" name="evaluationTeacherFeedbackSessionForm"
										styleId="name1" size="45" maxlength="50" /> </span></td>
				
			</tr>
			<tr>
									<td height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message
										key="knowledgepro.exam.examDefinition.month" />:</div>
									</td>
									<td height="25" class="row-even"><input type="hidden"
										id="month1" name="month1"
										value='<bean:write name="evaluationTeacherFeedbackSessionForm" property="month"/>' /><html:select
										property="month" styleId="month" styleClass="combo">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<cms:renderMonths normalMonth="true"></cms:renderMonths>
									</html:select></td>

									<td height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message
										key="knowledgepro.exam.examDefinition.year" />:</div>
									</td>
									<td height="25" class="row-even"><input type="hidden"
										id="yr" name="yr"
										value='<bean:write name="evaluationTeacherFeedbackSessionForm" property="year"/>' /><html:select
										property="year" styleId="year" styleClass="combo">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<cms:renderFutureYear normalYear="true"></cms:renderFutureYear>
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
            <td width="100%" height="20" colspan="4"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="45%" height="35"><div align="right">
                <c:choose>
								<c:when
									test="${sessionList != null && sessionList == 'edit'}">
									<html:submit property="" styleClass="formbutton"
										onclick="updateLeaveAppInstructions()">
										<bean:message key="knowledgepro.update" />
									</html:submit>
								</c:when>
								<c:otherwise>
									<html:submit property="" styleClass="formbutton"
										onclick="addLeaveAppInstructions()">
										<bean:message key="knowledgepro.submit" />
									</html:submit>
								</c:otherwise>
							</c:choose>
                </div></td>
                <td width="2%"></td>
                <td width="53%">
						<html:button property="" styleClass="formbutton" value="Reset" onclick="resetErrorMsgs()"></html:button>
				</td>
              </tr>
            </table></td>
          </tr>
          
          <tr>
            <td valign="top" class="news">
			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
              <tr>
                <td ><img src="images/01.gif" width="5" height="5" /></td>
                <td width="914" background="images/02.gif"></td>
                <td><img src="images/03.gif" width="5" height="5" /></td>
              </tr>
              <tr>
                <td width="5"  background="images/left.gif"></td>
                <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
                  <tr >
                    <td height="25" class="row-odd" ><div align="center"><bean:message key="knowledgepro.slno"/></div></td>
                    <td height="25" class="row-odd" align="center" >Academic Year</td>
                    <td height="25" class="row-odd" align="center" >Session</td>
                    <td height="25" class="row-odd" align="center" >Month</td>
                    <td height="25" class="row-odd" align="center" >Year</td>
                    <!--<td height="25" class="row-odd" align="center" >StartDate</td>
                    <td height="25" class="row-odd" align="center" >EndDate</td>
                    --><td height="25" class="row-odd" align="center" ><bean:message key="knowledgepro.edit"/></td>
                    <td class="row-odd"><div align="center"><bean:message key="knowledgepro.delete"/></div></td>
                  </tr>
                <c:set var="temp" value="0"/>
                <logic:notEmpty name="evaluationTeacherFeedbackSessionForm" property="sessionList">
                <logic:iterate id="INS" name="evaluationTeacherFeedbackSessionForm" property="sessionList" indexId="count">
                <c:choose>
                   <c:when test="${temp == 0}">
                   	<tr>
                   		<td width="6%" height="25" class="row-even" ><div align="center"><c:out value="${count + 1}"/></div></td>
                   		<td width="35%" class="row-even" align="center"><bean:write name="INS" property="academicYear"/></td>
                   		<td width="35%" class="row-even" align="center"><bean:write name="INS" property="name"/></td>
                   		<td width="10%" class="row-even" align="center"><bean:write name="INS" property="month"/></td>
                   		<td width="10%" class="row-even" align="center"><bean:write name="INS" property="year"/></td>
                   		<td width="10%" height="25" class="row-even" align="center"> <div align="center"><img src="images/edit_icon.gif"
						width="16" height="18" style="cursor:pointer" onclick="editSession('<bean:write name="INS" property="id"/>')"> </div> </td>
                   		<td width="6%" height="25" class="row-even" ><div align="center">
                   			<img src="images/delete_icon.gif" width="16" height="16" style="cursor:pointer" onclick="deleteSession('<bean:write name="INS" property="id"/>')"></div></td>
					</tr>
                    <c:set var="temp" value="1"/>
                   	</c:when>
                    <c:otherwise>
		            <tr>
               			<td width="6%" height="25" class="row-white"><div align="center"><c:out value="${count + 1}"/></div></td>
               			<td width="35%" class="row-white" align="center"><bean:write name="INS" property="academicYear"/></td>
               			<td width="35%" class="row-white" align="center"><bean:write name="INS" property="name"/></td>
                   		<td width="10%" class="row-white" align="center"><bean:write name="INS" property="month"/></td>
                   		<td width="10%" class="row-white" align="center"><bean:write name="INS" property="year"/></td>
               			<td width="10%" height="25" class="row-white" align="center"> <div align="center"><img src="images/edit_icon.gif"
						width="16" height="18" style="cursor:pointer" onclick="editSession('<bean:write name="INS" property="id"/>')"> </div> </td>
               			<td width="6%" height="25" class="row-white" ><div align="center">
               				<img src="images/delete_icon.gif" width="16" height="16" style="cursor:pointer" onclick="deleteSession('<bean:write name="INS" property="id"/>')"></div></td>
               		</tr>
                    <c:set var="temp" value="0"/>
				  	</c:otherwise>
                  </c:choose>
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
        	
          </tr>
          
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
<script type="text/javascript">
	var yearId = document.getElementById("yr").value;
	if (yearId != null && yearId.length != 0) {
		document.getElementById("year").value = yearId;
	}
	var monthId = document.getElementById("month1").value;
	if (monthId != null && monthId.length != 0) {
		document.getElementById("month").value = monthId;
	}
	var yearId = document.getElementById("tempyear").value;
	if (yearId != null && yearId.length != 0) {
		document.getElementById("academicYear").value = yearId;
	}
</script>
</html:form>	
