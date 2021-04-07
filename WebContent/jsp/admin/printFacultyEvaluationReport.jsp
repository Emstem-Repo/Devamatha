<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms" %>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<%@page import="com.kp.cms.constants.CMSConstants"%>
<head>
<link href="css/styles.css" rel="stylesheet" type="text/css">
<title><bean:message key="knowledgepro.facultyEvaluation.facultyEvaluationReport"/></title>
</head>
<body>
<%@ page import="java.util.*"%>
<%@ page import="java.text.*"%>
<html:form action="/facultyEvaluationReport">
<html:hidden property="formName" value="facultyEvaluationReportForm" />
<html:hidden property="method" styleId="method" value="initFacultyEvaluationReport"/>
<html:hidden property="pageType" value="2"/>
<logic:equal value="true" property="dataExist" name="facultyEvaluationReportForm">
	<table width="100%" border="0">
	<tr>
	<td align="center">
		<table width="100%" border="0">
			<tr>
			<td align="center" colspan="3">
		  		<img width="350" height="70"  src='<%= request.getRequestURL().toString().replace(request.getRequestURI().substring(0), request.getContextPath()).concat("/LogoServlet") %>' alt="Logo not available">
			</td>
			</tr>					
		</table>
	</td>
	</tr>	
	<tr><td>&nbsp;</td></tr>
	<tr>
	<td align="center">
		<table width="100%" border="1" style="border-collapse: collapse">		
			<tr>
			<th align="center" colspan="3"><bean:message key="knowledgepro.facultyEvaluation.evaluationReport"/></th>			
			</tr>
			<tr><td><table width="100%">
			<tr>
			<td align="left" width="20%"><bean:message key="knowledgepro.facultyEvaluation.academicyear"/></td>
			<td width="2%">:</td>	
			<td><bean:write name="facultyEvaluationReportForm" property="academicYear"/></td>			
			</tr>	
			<tr>
			<td align="left"  width="20%"><bean:message key="knowledgepro.facultyEvaluation.semester"/></td>
			<td width="2%">:</td>	
			<td><bean:write name="facultyEvaluationReportForm" property="to.semester"/></td>			
			</tr>
			<tr>
			<td align="left"  width="20%"><bean:message key="knowledgepro.facultyEvaluation.programme"/></td>
			<td width="2%">:</td>	
			<td><bean:write name="facultyEvaluationReportForm" property="to.programme"/></td>			
			</tr>
			<tr>
			<td align="left" width="20%"><bean:message key="knowledgepro.facultyEvaluation.teacher"/></td>
			<td width="2%">:</td>	
			<td><bean:write name="facultyEvaluationReportForm" property="to.teacherName"/></td>			
			</tr>
			<tr>
			<td align="left" width="20%"><bean:message key="knowledgepro.facultyEvaluation.course"/></td>
			<td width="2%">:</td>	
			<td><bean:write name="facultyEvaluationReportForm" property="to.courseName"/></td>			
			</tr>
			<tr>
			<td align="left" width="20%"><bean:message key="knowledgepro.facultyEvaluation.class"/></td>
			<td width="2%">:</td>	
			<td><bean:write name="facultyEvaluationReportForm" property="to.className"/></td>			
			</tr>
			<tr>
			<td align="left" width="20%"><bean:message key="knowledgepro.facultyEvaluation.subject"/></td>
			<td width="2%">:</td>	
			<td><bean:write name="facultyEvaluationReportForm" property="to.subject"/></td>			
			</tr>
			</table></td></tr>							
		</table>
	</td>
	</tr>
	<tr><td></td></tr>
	<tr>
	<td>
	<logic:notEmpty name="facultyEvaluationReportForm" property="questions">
		<table border="1" style="border-collapse: collapse">
		<tr>
		<th align="center" width="2%">Q.no</th>
		<th align="center" width="15%">Criteria of Students Assessment</th>
		<th align="center" width="2%">1</th>
		<th align="center" width="2%">2</th>
		<th align="center" width="2%">3</th>
		<th align="center" width="2%">4</th>
		<th align="center" width="2%">5</th>
		<th align="center" width="2%">Total</th>
		<th align="center" width="5%">Score*<br>1-5 Scale</th>
		</tr>		
		<logic:iterate id="queList" name="facultyEvaluationReportForm" property="questions" indexId="slNo">
		<% slNo = slNo +1; %>
			<tr>
			<td align="center"><%=slNo %></td>
			<td><bean:write name="queList" property="question"/></td>
			<logic:iterate id="ansList" name="queList" property="answers">
			<td align="center"><bean:write name="ansList" property="countOfStudents"/></td>
			</logic:iterate>
			<td align="center"><bean:write name="queList" property="totalStudentsForEachQue"/></td>						
			<td align="center"><bean:write name="queList" property="totalScoreForEachQue"/></td>						
			</tr>
		</logic:iterate>
		<tr>
		<th colspan="2" align="center">Overall Average</th>
		<th colspan="7" align="right"><bean:write name="facultyEvaluationReportForm" property="overallScore"/></th>
		</tr>
		<tr>
		<th colspan="2" align="center">Class Average</th>
		<th colspan="7" align="right"><bean:write name="facultyEvaluationReportForm" property="classAverage"/></th>
		</tr>	
		<tr>
		<th colspan="2" align="center">Department Average</th>
		<th colspan="7" align="right"><bean:write name="facultyEvaluationReportForm" property="deptAverage"/></th>
		</tr>
		<tr>
		<th colspan="9" align="center">* 1= V Poor  2= Poor  3= Satisfactory  4 = Good  5= V Good</th>
		
		</tr>		
		</table>
	</logic:notEmpty>
	</td></tr>
	<logic:notEmpty name="facultyEvaluationReportForm" property="remarks">
	<tr><td>&nbsp;</td></tr>
					
	<tr><td><h4><b>Remarks given by students</b></h4></td></tr>
	<logic:iterate id="remarks" name="facultyEvaluationReportForm" property="remarks" indexId="count">
	<% count = count +1; %>	
	<tr><td><bean:write name="remarks" property="key"/>. <bean:write name="remarks" property="value"/></td>
	</tr>
	</logic:iterate>
	</logic:notEmpty>	</table>	
	</logic:equal>	
	<logic:notEqual value="true" property="dataExist" name="facultyEvaluationReportForm">
	<table width="100%" height="435px">
		<tr>
			<td align="center" valign="middle">
				No Records Found
			</td>
		</tr>
	</table>
	</logic:notEqual>
<script type="text/javascript">
	window.print();
</script>
</html:form>
</body>
