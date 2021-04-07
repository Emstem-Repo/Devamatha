<%@ page contentType="text/xml;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean" %>
<%@page import="java.util.ArrayList" %>
<response>
   <logic:iterate id="map" name="optionMap">
       <option>
     	 	  <optionvalue><bean:write name="map" property="key"/></optionvalue>
     		  <optionlabel><bean:write name="map" property="value"/></optionlabel>
       </option>
   </logic:iterate>
   <logic:notEmpty name="periodId" scope="request">
   		<periodId><bean:write name="periodId" scope="request"/></periodId>
   </logic:notEmpty>
  
</response>