<%@ page contentType="text/xml;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean" %>
<%@page import="java.util.ArrayList" %>
<response>
    <logic:iterate id="map" name="optionMap">
                <leave>
              	 	  <leaveType><bean:write name="map" property="leaveType"/></leaveType>
              		  <allottedLeave><bean:write name="map" property="allottedLeave"/></allottedLeave>
              		  <sanctionedLeave><bean:write name="map" property="sanctionedLeave"/></sanctionedLeave>
              		  <remainingLeave><bean:write name="map" property="remainingLeave"/></remainingLeave>
                </leave>
   	</logic:iterate>
   
</response>