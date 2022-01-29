<%@ page contentType="text/xml;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@page import="java.util.ArrayList"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<response>
	<c:if test="${msg==null}">
	<logic:iterate id="StudentMarksTo" name="list">
		<fields>
		<detailId><bean:write name="StudentMarksTo" property="id"/></detailId>
		<studentId><bean:write name="StudentMarksTo" property="studentId"/></studentId>
		<marksAbscentCode><bean:write name="StudentMarksTo" property="marksAbscent"/></marksAbscentCode>
		<retest><bean:write name="StudentMarksTo" property="retest"/></retest>
		<classId><bean:write name="StudentMarksTo" property="classId"/></classId>
		<courseId><bean:write name="StudentMarksTo" property="courseId"/></courseId>
		<schemeNo><bean:write name="StudentMarksTo" property="schemeNo"/></schemeNo>
		<year><bean:write name="StudentMarksTo" property="year"/></year>
		</fields>
		</logic:iterate>
	</c:if>
	<c:if test="${msg!=null}">
		<value><bean:write name="msg"/> </value>
	</c:if>
</response>