<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<html:form action="/EvaluationTeacherFeedback" method="post">
<html:hidden property="formName" value="evaluationTeacherFeedbackForm" />
<table  width="100%" border="0">
	<tr>
		<td align="center" colspan="3">
		<img width="350" height="100"  src='<%= request.getRequestURL().toString().replace(request.getRequestURI().substring(0), request.getContextPath()).concat("/LogoServlet") %>' alt="Logo not available">
		</td>
	</tr>
	<tr><td colspan="3">&nbsp;</td></tr>	
	<tr><td align="center" colspan="3"><h3>Student Evaluation Report</h3></td></tr>
	<tr><td colspan="3">&nbsp;</td></tr>	
	<tr>
		<th align="left" width="9%">Course</th><th width="1%">:</th>
		<th align="left" width="90%"><bean:write name="evaluationTeacherFeedbackForm" property="courseName"/></th>
	</tr>
	<tr>
		<th align="left">Semester</th><th>:</th>
		<th align="left"><bean:write name="evaluationTeacherFeedbackForm" property="semister"/></th>
	</tr>
	<tr>
		<th align="left">Subject</th><th>:</th>
		<th align="left"><bean:write name="evaluationTeacherFeedbackForm" property="subjectName"/></th>
	</tr>
	<tr><td colspan="3">&nbsp;</td></tr>	
	<tr>
		<td colspan="3">
			<table border="1" width="100%" style="border-collapse: collapse;">			
				<logic:notEmpty name="evaluationTeacherFeedbackForm" property="studentsFeedbackMap">
		<tr>
			<th height="50px"><bean:message key="knowledgepro.fee.studentname"/></th>
			<logic:iterate id="queList" name="evaluationTeacherFeedbackForm" property="evaTeacherQuestionsToList">
			<th width="15%"><bean:write name="queList" property="question"/></th>
			</logic:iterate>
		</tr>
		<logic:iterate id="stuMap" name="evaluationTeacherFeedbackForm" property="studentsFeedbackMap">
			<tr height="30px">
				<td><bean:write name="stuMap" property="value.studentName"/></td>
				<logic:iterate id="ansList" name="stuMap" property="value.questionTosList">
				<td align="center"><bean:write name="ansList" property="answer"/></td>
				</logic:iterate>
			</tr>
		</logic:iterate>
	</logic:notEmpty>
			</table>
		</td>
	</tr>			
	
</table>
</html:form>