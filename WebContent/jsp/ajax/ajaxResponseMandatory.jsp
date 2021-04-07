<%@ page contentType="text/xml;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean" %>
<%@page import="java.util.ArrayList" %>
<response>
	<logic:iterate id="attendanceMandatory" name="lists">
	            <fields>
	          	 	  <field><bean:write name="attendanceMandatory" property="name"/></field>
	          		  <mandatory><bean:write name="attendanceMandatory" property="isMandatory"/></mandatory>
	            </fields>
	</logic:iterate>
	<logic:iterate id="map" name="optionMap">
	            <option>
	          	 	  <optionvalue><bean:write name="map" property="key"/></optionvalue>
	          		  <optionlabel><bean:write name="map" property="value"/></optionlabel>
	            </option>
	</logic:iterate>
</response>