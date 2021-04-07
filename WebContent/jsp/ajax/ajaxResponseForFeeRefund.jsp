<%@ page contentType="text/xml;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@page import="java.util.ArrayList"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<response>
	<c:if test="${msg==null}">
	<feeRefund>
		<refundAmount><bean:write name="refundAmount"/> </refundAmount>
		<refundMode><bean:write name="refundMode"/></refundMode>
		<refundDate><bean:write name="refundDate"/> </refundDate>
		<refundId><bean:write name="refundId"/> </refundId>
	</feeRefund>
	</c:if>
	<c:if test="${msg!=null}">
		<value><bean:write name="msg"/> </value>
	</c:if>
</response>