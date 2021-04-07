<%@ page contentType="text/xml;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@page import="java.util.ArrayList"%>
<response> <value><bean:write name="currentExam" /></value>
<dateTime><bean:write name="dateTimeValue" /></dateTime>
<logic:iterate id="map" name="optionMap">
	            <option>
	          	 	  <optionvalue><bean:write name="map" property="key"/></optionvalue>
	          		  <optionlabel><bean:write name="map" property="value"/></optionlabel>
	            </option>
	</logic:iterate>
</response>