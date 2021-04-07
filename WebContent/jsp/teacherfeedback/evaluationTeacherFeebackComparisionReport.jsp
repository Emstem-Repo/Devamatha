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
			<bean:define id="totalQuestions" name="evaluationTeacherFeedbackForm" property="totalQuestions"/>
			<td colspan="<%=Integer.parseInt(totalQuestions.toString())+1 %>" align="center"><h3>Student Indices</h3></td>
			<td colspan="5" align="center"><h3>Teacher Evaluated</h3></td>
		</tr>
		<tr>
			<th></th>
			<th height="50px"><bean:message key="knowledgepro.fee.studentname"/></th>
			<logic:iterate id="queList" name="evaluationTeacherFeedbackForm" property="evaTeacherQuestionsToList" indexId="totStu">
			<th width="15%"><bean:write name="queList" property="question"/></th>
			</logic:iterate>			
			<th>Index Points</th>		
			<th>Internal</th>		
			<th>Avg(Index Points+Internal)</th>		
			<th>Points scored by teacher</th>		
			<th>Weighted Points</th>		
		</tr>
		<logic:iterate id="stuMap" name="evaluationTeacherFeedbackForm" property="studentsFeedbackMap">
			<tr height="30px">
				<td></td>
				<td><bean:write name="stuMap" property="value.studentName"/></td>
				<logic:iterate id="ansList" name="stuMap" property="value.questionTosList">
				<td align="center"><bean:write name="ansList" property="answer"/></td>
				</logic:iterate>
				<td align="center"><bean:write name="stuMap" property="value.indexPoints"/></td>
				<td align="center"><bean:write name="stuMap" property="value.internal"/></td>
				<td align="center"><bean:write name="stuMap" property="value.average"/></td>
				<td align="center"><bean:write name="stuMap" property="value.pointsScoreByTeacher"/></td>
				<td align="center"><bean:write name="stuMap" property="value.weightedPoints"/></td>
			</tr>
		</logic:iterate>
		<tr height="30px">
			<td><h3>Total</h3></td>			
			<td align="center"><h3><bean:write name="evaluationTeacherFeedbackForm" property="totalStudents"/></h3></td>
			<logic:iterate id="queList" name="evaluationTeacherFeedbackForm" property="evaTeacherQuestionsToList" indexId="totStu">
			<td></td>
			</logic:iterate>	
			<td></td>
			<td></td>
			<td align="center"><h3><bean:write name="evaluationTeacherFeedbackForm" property="totalAverage"/></h3></td>
			<td align="center"><h3><bean:write name="evaluationTeacherFeedbackForm" property="totalPointsScoredByTeacher"/></h3></td>
			<td align="center"><h3><bean:write name="evaluationTeacherFeedbackForm" property="totalWeightedPoints"/></h3></td>
		</tr>
		<tr height="50px"><td colspan="12" align="center"><h3><bean:write name="evaluationTeacherFeedbackForm" property="totalWeightedPoints"/>/<bean:write name="stuMap" property="value.indexPoints"/>=
		<bean:write name="evaluationTeacherFeedbackForm" property="totalWeightsAndIndexPoints"/></h3></td></tr>
	</logic:notEmpty>
			</table>
		</td>
	</tr>			
	
</table>
</html:form>