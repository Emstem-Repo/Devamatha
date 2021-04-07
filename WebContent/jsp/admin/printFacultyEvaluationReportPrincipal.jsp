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
<style type="text/css">
	.table
	{
		font-size: 9pt;
	}
		
</style>
</head>
<body>
<%@ page import="java.util.*"%>
<%@ page import="java.text.*"%>
<html:form action="/facultyEvaluationReport">
<html:hidden property="formName" value="facultyEvaluationReportPrincipalForm" />
<html:hidden property="method" styleId="method" value="initFacultyEvaluationReport"/>
<html:hidden property="pageType" value="2"/>
	<logic:notEmpty name="facultyEvaluationReportPrincipalForm" property="teacherList"><table>
		<tr><td>
		<table width="100%">
			<tr>
			<td align="center" colspan="2">
		  		<img width="350" height="70"  src='<%= request.getRequestURL().toString().replace(request.getRequestURI().substring(0), request.getContextPath()).concat("/LogoServlet") %>' alt="Logo not available">
			</td>
			</tr>				
			<tr><td>&nbsp;</td></tr>
			<tr>
			<td align="left"><FONT size="2"><b>FACULTY EVALUATION REPORT OF <bean:write name="facultyEvaluationReportPrincipalForm" property="schemeNo"/>  SEMESTER</b></FONT></td>
			<td align="right"><FONT size="2"><b>STUDENTS OF DEPARTMENT OF <bean:write name="facultyEvaluationReportPrincipalForm" property="departmentName"></bean:write></b></FONT></td>
			</tr>	
		</table>
		</td></tr>
		
		<tr><td>
			<table width="100%" border="1" style="border-collapse: collapse" class="table">	
			<tr>
			<th><bean:message key="knowledgepro.no"></bean:message></th>
			<th><bean:message key="knowledgepro.admin.template.candidate.name"></bean:message></th>
			<!--<th><bean:message key="knowledgepro.admin.selectedSubjects"></bean:message></th>	
			<th><bean:message key="knowledgepro.exam.blockUnblock.class"></bean:message></th>		
			--><logic:iterate name="facultyEvaluationReportPrincipalForm" property="questions" id="queList">
			<th><bean:write name="queList" property="question"/></th>
			</logic:iterate>
			<th>Total Average</th>
			<th>Faculty Average Difference</th>
			<th>Total Entries</th>			
			</tr>	
			<logic:iterate id="teacherList" name="facultyEvaluationReportPrincipalForm" property="teacherList" indexId="count">
			<tr align="center"><%count = count +1; %>
			<td><%= count %></td>
			<td><bean:write name="teacherList" property="teacherName"/></td>
			<!--<td><bean:write name="teacherList" property="subject"/></td>
			<td><bean:write name="teacherList" property="className"/></td>
			--><logic:iterate id="detailsList" name="teacherList" property="resultList">
			<td><bean:write name="detailsList" property="scoreForEachQue"/></td></logic:iterate>	
			<td><bean:write name="teacherList" property="totalAverage"/></td>			
			<bean:define id="facultyAvgDiff" name="teacherList" property="totalAverage"></bean:define>	
			<% 
			Double facultyAvg = Double.parseDouble(request.getAttribute("facultyAverage").toString());
			String faculty1=facultyAvgDiff.toString();
			Double Avg = facultyAvg-Double.parseDouble(faculty1);
			DecimalFormat d=new DecimalFormat("#.##");
			%>
			<td><%=d.format(Avg) %></td>	
			<td><bean:write name="teacherList" property="totalEntries"/></td>
			</tr>
			</logic:iterate>				
		</table>
		</td></tr>
		<tr>
	<td align="center"><b><bean:message key="knowledgepro.facultyEvaluation.facultyAverage"></bean:message>
	<bean:write name="facultyEvaluationReportPrincipalForm" property="totalFacultyAverage"/></b></td>
	</tr>
	<tr><td>&nbsp;</td></tr>		
	<tr><td><h4><b>Remarks given by students</b></h4></td></tr>		
		<tr><td><div align="left">
			<table width="100%" border="1" style="border-collapse: collapse" align="left">
			<logic:iterate id="teacherList" name="facultyEvaluationReportPrincipalForm" property="teacherList" indexId="count">
			<logic:notEmpty name="teacherList" property="remarks">
			<tr align="center"><%count = count +1; %>		
			<td valign="top" width="10%"><h4><bean:write name="teacherList" property="teacherName"/></h4></td>
			<td><div align="left"><table>
			<logic:iterate id="remarks" name="teacherList" property="remarks">
			<tr><td align="left"><div align="left"><bean:write name="remarks" property="value"/></div></td></tr>
			</logic:iterate>
			</table></div>
			</td>
			</tr>
			</logic:notEmpty>			
			</logic:iterate>				
		</table>
		</div>
		</td></tr>
	</table>
	</logic:notEmpty>	
	<logic:empty name="facultyEvaluationReportPrincipalForm" property="teacherList">
	<table width="100%" height="435px">
		<tr>
			<td align="center" valign="middle">
				No Records Found
			</td>
		</tr>
	</table></logic:empty>
<script type="text/javascript">
	window.print();
</script>
</html:form>
</body>
