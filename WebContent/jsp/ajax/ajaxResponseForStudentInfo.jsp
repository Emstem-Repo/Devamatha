<%@ page contentType="text/xml;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@page import="java.util.ArrayList"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<response>
<c:if test="${msg==null}">
	<studentDetails>
			<studentName><bean:write name="studentName"/></studentName>
			<studentCourse><bean:write name="studentCourse"/></studentCourse>
			<studentRoom><bean:write name="studentRoomNo"/></studentRoom>
			<studentBed><bean:write name="studentBedNo"/></studentBed>
			<studentBlock><bean:write name="studentBlock"/></studentBlock>
			<studentUnit><bean:write name="studentUnit"/></studentUnit>
			<studentHostel><bean:write name="studentHostel"/></studentHostel>
	</studentDetails>
	</c:if>
	<c:if test="${msg!=null}">
		<value><bean:write name="msg"/> </value>
	</c:if>
</response>