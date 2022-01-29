<%@ page contentType="text/xml;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@page import="java.util.ArrayList"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<response>
	
	<c:if test="${allotmentDetails!=null}">			
	<logic:iterate id="details" name="allotmentDetails">
		
			<fields>
			<allotmentToDetails>
					<Room> <bean:write name="details" property="room"/></Room> 
					<Type><bean:write name="details" property="type"/></Type>
					<ExamDate><bean:write name="details" property="examDate"/></ExamDate>
					<Session><bean:write name="details" property="session"/></Session>
			</allotmentToDetails>			
				</fields>
			</logic:iterate>
			</c:if>
</response>