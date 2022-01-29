<%@ page contentType="text/xml;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean" %>
<%@page import="java.util.ArrayList" %>
<response>
	   <logic:iterate id="map" name="optionMap" indexId="count">
					<option>
							<id><bean:write name="map" property="id"/></id>
							<programTypeId> <bean:write name="map" property="programTypeId"/></programTypeId> 
							<programId> <bean:write name="map" property="programId"/></programId> 
							<courseId> <bean:write name="map" property="courseId"/></courseId> 
							<programType> <bean:write name="map" property="programTypeName"/></programType> 
							<program><bean:write name="map" property="programName"/></program>
							<course><bean:write name="map" property="courseName"/></course>
							<templateName><bean:write name="map" property="templateName"/></templateName>
					</option>
	      
	   </logic:iterate>
</response>