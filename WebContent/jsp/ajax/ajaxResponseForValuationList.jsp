<%@ page contentType="text/xml;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean" %>
<%@page import="java.util.ArrayList" %>
<response>
	   <logic:iterate id="map" name="optionMap" indexId="count">
					<option>
							<id><bean:write name="map" property="id"/></id>
							<examName> <bean:write name="map" property="examName"/></examName> 
							<employeeName> <bean:write name="map" property="employeeName"/></employeeName> 
							<subjectName> <bean:write name="map" property="subjectName"/></subjectName> 
							<valuator> <bean:write name="map" property="valuator"/></valuator> 
					</option>
	      
	   </logic:iterate>
</response>
