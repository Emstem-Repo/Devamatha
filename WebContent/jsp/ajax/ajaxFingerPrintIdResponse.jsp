<%@ page contentType="text/xml;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested" %>
<%@page import="java.util.ArrayList" %>
<response>
   <logic:iterate id="fingerPrint" name="fingerPrints">
       <option>
     	 	  <optionvalue><nested:write name="fingerPrint"/></optionvalue>
       </option>
   </logic:iterate>
</response>