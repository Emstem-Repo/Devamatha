<%@ page contentType="text/xml;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@page import="java.util.ArrayList"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<response>
	<c:if test="${ToList!=null}">
		<logic:iterate id="list" name="ToList">
			<TList>
				<subName><bean:write name="list" property="subjectName"/></subName>
				<status><bean:write name="list" property="status"/></status>
				<subId><bean:write name="list" property="subjId"/> </subId>
			</TList>
		</logic:iterate>
	</c:if>
</response>