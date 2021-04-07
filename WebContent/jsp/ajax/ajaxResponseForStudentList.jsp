<%@ page contentType="text/xml;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean" %>
<%@page import="java.util.ArrayList" %>
<response>
	   <logic:iterate id="map" name="optionMap" indexId="count">
	      
					<option>
							<studentName> <bean:write name="map" property="studentName"/></studentName> 
							<secondLanguage> <bean:write name="map" property="secondLanguage"/></secondLanguage> 
							<percentage> <bean:write name="map" property="percentage"/></percentage> 
							<gender> <bean:write name="map" property="gender"/></gender> 
							<ClassName><bean:write name="map" property="className"/></ClassName>
							<applNo><bean:write name="map" property="applNo"/></applNo>
							<regNo><bean:write name="map" property="regNo"/></regNo>
					</option>
	      
	   </logic:iterate>
</response>