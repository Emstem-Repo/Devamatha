<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<%@ taglib uri="/WEB-INF/struts-tld/displaytag.tld" prefix="display" %>
<%@ page buffer = "500kb" %>
<link href="css/displaytag.css" rel="stylesheet" type="text/css">
<html:html>
<head>

</head>
<script type="text/javascript">
function cancelAction() {
	closeConfirm = confirm("Are you sure you want to close the Teacher Evaluation?");
	if (closeConfirm == true) {
	document.location.href = "EvaluationTeacherFeedback.do?method=initEvaluationTeacherFeedback";
}
}
</script>


<html:form action="/EvaluationTeacherFeedback" method="post">
<html:hidden property="method" styleId="method" value="startTeachersEvaluation"/>	
<html:hidden property="pageType" value="3" />
<html:hidden property="formName" value="evaluationTeacherFeedbackForm" />



<table width="99%" border="0">
  
  <tr>
    <td><span class="Bredcrumbs">Faculty Evaluation <span class="Bredcrumbs">&gt;&gt;Teacher Evaluation Feedback </span> </span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="heading_white" > Teacher Evaluation </td>
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
      </tr> <logic:notEmpty name="evaluationTeacherFeedbackForm" property="studentList" >
             <tr>
             <td height="122" valign="top" background="images/Tright_03_03.gif"></td>
            <td valign="top" class="news"><table width="100%" cellspacing="1" cellpadding="2">
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
                    <td height="30%" class="row-odd" align="center"><bean:message key="knowledgepro.slno"/></td>
                    <td height="30%" class="row-odd" align="center" >Student Name</td>
             		 <td height="30%" class="row-odd" align="center" >Register Number</td>
             		  <td height="30%" class="row-odd" align="center" ></td>
                 </tr>
                <c:set var="temp" value="0"/>
                
               
                <logic:iterate id="to" name="evaluationTeacherFeedbackForm" property="studentList" indexId="count">
               <logic:equal  name="to" property="done" value="true">
                   	<tr>
                   		<td width="5%" height="25" class="row-even" ><div align="center"><c:out value="${count + 1}"/></div></td>
                   		<td width="25%" height="25" class="row-even" align="center"><bean:write name="to" property="studentName"/></td>
                   		<td width="40%" height="25" class="row-even" align="center"><bean:write name="to" property="registerNo"/></td>
                   		<td width="20%" height="25" class="row-even" align="center"><img src="images/check.png" width="16" height="18" /></td>
                   	</tr>
                  </logic:equal>
                </logic:iterate>
                <logic:iterate id="to" name="evaluationTeacherFeedbackForm" property="studentList" indexId="count">
		          <logic:equal  name="to" property="done" value="false">
		            <tr>
                   		<td width="5%" height="25" class="row-even" ><div align="center"><c:out value="${count + 1}"/></div></td>
                   		<td width="25%" height="25" class="row-even" align="center"><bean:write name="to" property="studentName"/></td>
                   		<td width="40%" height="25" class="row-even" align="center"><bean:write name="to" property="registerNo"/></td>
                   		<td width="20%" height="25" class="row-even" align="center"><img src="images/cross.png" width="16" height="18" /></td>
               		</tr>
               		</logic:equal>
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
          <tr>
			<td>
			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
				<tr>
				 <td colspan="3" height="60"><div align="center">
								<html:submit property="" styleClass="formbutton" value="Start"></html:submit>&nbsp; <html:button property="" styleClass="formbutton" value="Close"  onclick="cancelAction()"></html:button>
								</div></td>
			</tr>
				</table>
			</td>
	          </tr>
          
          
          </table></td>
        <td width="10" valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
       </logic:notEmpty>
       <logic:empty name="evaluationTeacherFeedbackForm" property="studentList">
       		<tr><td height="26" valign="top" background="images/Tright_03_03.gif"></td>
       			<td align="center">No Records Found</td>
       			<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
       		</tr>
       		
				<tr>
				<td height="26" valign="top" background="images/Tright_03_03.gif"></td>
				 <td  height="60"><div align="center">
								 <html:button property="" styleClass="formbutton" value="Close"  onclick="cancelAction()"></html:button>
								</div></td>
								<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
			</tr>
       </logic:empty>
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
</html:html>