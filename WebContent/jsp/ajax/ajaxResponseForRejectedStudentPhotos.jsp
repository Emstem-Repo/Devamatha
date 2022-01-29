<%@ page contentType="text/xml;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@page import="java.util.ArrayList"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<response>
   <c:if test="${msg==null}">
	<c:if test="${rejectedPhotoList!=null}">
		<logic:iterate id="rejected" name="rejectedPhotoList">
			<rejectedList>
				<registerNo><bean:write name="rejected" property="regNo"/></registerNo>
				<imageName><bean:write name="rejected" property="fileName"/></imageName>
				<studentName><bean:write name="rejected" property="studentName"/></studentName>
				<studentClass><bean:write name="rejected" property="studentClass"/></studentClass>
				<rejectedReason><bean:write name="rejected" property="rejectedReason"/></rejectedReason>
			</rejectedList>
		</logic:iterate>
	</c:if>
   </c:if>
	<c:if test="${msg!=null}">
		<value><bean:write name="msg"/> </value>
	</c:if>
</response>