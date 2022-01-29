<%@ page contentType="text/xml;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean" %>
<%@page import="java.util.ArrayList" %>
<response>
	   <logic:iterate id="map" name="optionMap" indexId="count">
	      
					<option>
							<id> <bean:write name="map" property="id"/></id> 
							<program> <bean:write name="map" property="program"/></program> 
							<programType> <bean:write name="map" property="programType"/></programType> 
							<examDefId_progId> <bean:write name="map" property="examDefId_progId"/></examDefId_progId> 
							<academicYear> <bean:write name="map" property="academicYear"/></academicYear> 
							<examName><bean:write name="map" property="examName"/></examName>
							<examCode><bean:write name="map" property="examCode"/></examCode>
							<examType><bean:write name="map" property="examType"/></examType>
							<isCurrent><bean:write name="map" property="isCurrent"/></isCurrent>
							<month><bean:write name="map" property="month"/></month>
							<year><bean:write name="map" property="year"/></year>
							<internalExamNameList><bean:write name="map" property="internalExamNameList"/></internalExamNameList>
					</option>
	   	   </logic:iterate>
</response>