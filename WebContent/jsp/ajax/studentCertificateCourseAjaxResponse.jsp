<%@ page contentType="text/xml;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@page import="java.util.ArrayList"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<response>
	<c:if test="${msg==null}">
	<schemeId><bean:write name="SchemeNo"/></schemeId>
		<logic:iterate id="map" name="SchemeMap">
	            <option>
	          	 	  <optionvalue><bean:write name="map" property="key"/></optionvalue>
	          		  <optionlabel><bean:write name="map" property="value"/></optionlabel>
	            </option>
		</logic:iterate>
	</c:if>
	<c:if test="${msg!=null}">
		<value><bean:write name="msg"/> </value>
	</c:if>
</response>