<%@ page contentType="text/xml;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean" %>
<%@page import="java.util.ArrayList" %>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<response>
   <c:if test="${ErrorOccured==null}">
    <logic:iterate id="map" name="subjectOptionMap">
                <subject>
              	 	  <optionvalue><bean:write name="map" property="key"/></optionvalue>
              		  <optionlabel><bean:write name="map" property="value"/></optionlabel>
                </subject>
   	</logic:iterate>
   	<logic:iterate id="map" name="periodOptionMap">
                <period>
              	 	  <optionvalue><bean:write name="map" property="key"/></optionvalue>
              		  <optionlabel><bean:write name="map" property="value"/></optionlabel>
                </period>
   	</logic:iterate>
   	<logic:equal value="true" name="timeTable">
   	<classId><bean:write name="classId" scope="request"/></classId>
   	<subjectId><bean:write name="subjectId" scope="request"/></subjectId>
   	<attendanceTypeId><bean:write name="attendanceTypeId" scope="request"/></attendanceTypeId>
   	<batchId><bean:write name="batchId" scope="request"/></batchId>
   	<activityId><bean:write name="activityId" scope="request"/></activityId>
   	<logic:iterate id="map" name="batchMap">
                <batch>
              	 	  <optionvalue><bean:write name="map" property="key"/></optionvalue>
              		  <optionlabel><bean:write name="map" property="value"/></optionlabel>
                </batch>
   	</logic:iterate>
   	<logic:iterate id="map" name="activityMap">
                <activity>
              	 	  <optionvalue><bean:write name="map" property="key"/></optionvalue>
              		  <optionlabel><bean:write name="map" property="value"/></optionlabel>
                </activity>
   	</logic:iterate>
   	</logic:equal>
   	</c:if>
	<c:if test="${ErrorOccured!=null}">
	<ErrorOccured><bean:write name="ErrorOccured" scope="request"/></ErrorOccured>
	</c:if>
</response>