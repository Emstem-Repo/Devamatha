<%@ page contentType="text/xml;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@page import="java.util.ArrayList"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<response>
	<c:if test="${msg==null}">
	<studentDetails>
			<name><bean:write name="name"/></name>
			<clas><bean:write name="clas"/></clas>
			<date><bean:write name="date"/></date>
			<session><bean:write name="session"/></session>
			<venue><bean:write name="venue"/></venue>
	</studentDetails>
	</c:if>
	<c:if test="${msg!=null}">
		<value><bean:write name="msg"/> </value>
	</c:if>
</response>