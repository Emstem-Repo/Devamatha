<%@ page contentType="text/xml;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@page import="java.util.ArrayList"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<response>
    <c:if test="${ToList!=null}">	
    <logic:iterate id="list" name="ToList">		
			<SupplyImprove>
						<examName><bean:write name="list" property="examName"/></examName> 
						<className><bean:write name="list" property="className"/></className>
						<startDate><bean:write name="list" property="startDate"/></startDate>
						<endDate><bean:write name="list" property="endDate"/></endDate>
						<tId><bean:write name="list" property="id"/></tId>
			</SupplyImprove>
	</logic:iterate>
	</c:if>
</response>