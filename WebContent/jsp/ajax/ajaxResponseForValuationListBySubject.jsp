<%@ page contentType="text/xml;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean" %>
<%@page import="java.util.ArrayList" %>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<response>
	 <logic:iterate id="examValid" name="examValidationList" indexId="count">
					<option>
							<id><bean:write name="examValid" property="id"/></id>
							<issueDate> <bean:write name="examValid" property="issueDate"/></issueDate> 
							<employeeName> <bean:write name="examValid" property="employeeName"/></employeeName> 
							<numberOfAnswereScript> <bean:write name="examValid" property="answerScripts"/></numberOfAnswereScript> 
							<valuator> <bean:write name="examValid" property="valuator"/></valuator>
							<otherEmployee> <bean:write name="examValid" property="otherEmployee"/></otherEmployee> 
					</option>
	      
	   </logic:iterate>
</response>
