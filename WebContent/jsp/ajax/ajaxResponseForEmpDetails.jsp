<%@ page contentType="text/xml;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@page import="java.util.ArrayList"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<response>
	<c:if test="${msg==null}">
		<empDetails>
			<name><bean:write name="EmpName"/></name>
			<departmentName><bean:write name="DepartmentName"/></departmentName>
			<designationName><bean:write name="DesignationName"/></designationName>
			<empId><bean:write name="empId"/></empId>
			<empTypeId><bean:write name="empTypeId"/></empTypeId>
		</empDetails>
	</c:if>
	<c:if test="${msg!=null}">
		<value><bean:write name="msg"/> </value>
	</c:if>
</response>