<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<html>
<head>
<SCRIPT type="text/javascript">
    function exportExcel(blockId) {  
    	document.location.href = "StudentLoginAction.do?method=hallTicketClearanceCertificate&blockId="+blockId;
    }
</SCRIPT>
</head>
<body>
<%
	String reason = ""; 
	if(session.getAttribute("hallTicketBlockReason")!= null){
		reason = session.getAttribute("hallTicketBlockReason").toString();
	}
	
	String id="";
	if(session.getAttribute("blockHallTicketId")!= null){
		id = session.getAttribute("blockHallTicketId").toString();
	}
	boolean isLessPercentage=false;
	if(session.getAttribute("isLessPercentage")!= null){
		isLessPercentage = Boolean.getBoolean(session.getAttribute("isLessPercentage").toString());
	}
	
%>
<font color="red"> Your Hall Ticket is blocked due to <%=reason%></font>
<br/>

<%-- 
<c:if test="${isLessPercentage==true}">
<a href="#" onclick="exportExcel(<%=id %>)" >Click Here to Get Clearance Certificate</a>
</c:if>
--%>

</body>
</html>