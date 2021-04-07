<%@ page contentType="text/xml;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@page import="java.util.ArrayList"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<response>
	
	<c:if test="${empList!=null}">			
	<logic:iterate id="details" name="empList">
		
			<fields>
					<TeachingExpYears> <bean:write name="details" property="TeachingExpYears"/></TeachingExpYears> 
					<TeachingExpMonths><bean:write name="details" property="TeachingExpMonths"/></TeachingExpMonths>
					<CurrentTeachnigDesignation><bean:write name="details" property="CurrentTeachnigDesignation"/></CurrentTeachnigDesignation>
					<CurrentTeachingOrganisation><bean:write name="details" property="CurrentTeachingOrganisation"/></CurrentTeachingOrganisation>
							
				</fields>
			</logic:iterate>
			</c:if>
</response>