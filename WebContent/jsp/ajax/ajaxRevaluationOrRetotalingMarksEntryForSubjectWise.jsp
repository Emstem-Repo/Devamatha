<%@ page contentType="text/xml;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@page import="java.util.ArrayList"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<response>
	<c:if test="${msg==null}">
	<logic:iterate id="RevaluationOrRetotalingMarksEntryForSubjectWiseTo" name="list">
		<fields>
		<detailId><bean:write name="RevaluationOrRetotalingMarksEntryForSubjectWiseTo" property="id"/></detailId>
		<studentId><bean:write name="RevaluationOrRetotalingMarksEntryForSubjectWiseTo" property="studentId"/></studentId>
		<classId><bean:write name="RevaluationOrRetotalingMarksEntryForSubjectWiseTo" property="classId"/></classId>
		<courseId><bean:write name="RevaluationOrRetotalingMarksEntryForSubjectWiseTo" property="courseId"/></courseId>
		<schemeNo><bean:write name="RevaluationOrRetotalingMarksEntryForSubjectWiseTo" property="schemeNo"/></schemeNo>
		<year><bean:write name="RevaluationOrRetotalingMarksEntryForSubjectWiseTo" property="year"/></year>
		</fields>
		</logic:iterate>
	</c:if>
	<c:if test="${msg!=null}">
		<value><bean:write name="msg"/> </value>
	</c:if>
</response>