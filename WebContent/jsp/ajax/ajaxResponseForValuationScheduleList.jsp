<%@ page contentType="text/xml;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean" %>
<%@page import="java.util.ArrayList" %>
<response>
	   <logic:iterate id="map" name="optionMap" indexId="count">
					<option>
							<id> <bean:write name="map" property="id"/></id>
							<subjectName> <bean:write name="map" property="subjectName"/></subjectName> 
							<employeeName> <bean:write name="map" property="employeeName"/></employeeName> 
							<valuator> <bean:write name="map" property="valuator"/></valuator> 
							<boardValuationDate> <bean:write name="map" property="boardValuationDate"/></boardValuationDate> 
							<valuationFrom> <bean:write name="map" property="valuationFrom"/></valuationFrom>
							<valuationTo> <bean:write name="map" property="valuationTo"/></valuationTo>
					</option>
	      
	   </logic:iterate>
</response>