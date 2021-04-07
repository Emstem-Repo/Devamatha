<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<html:html>
<head>
<title>:: CMS ::</title>
</head>
<body>
<html:form action="AttendanceEntry" method="post">
<c:choose>
<c:when test="${view=='AutomaticAttendanceView'}">
<html:hidden property="method" styleId="method" value="initAttendanceEntryByTimeTableForEmployeeLogin"/>
</c:when>
<c:otherwise>
<html:hidden property="method" styleId="method" value="newAttendanceEntry"/>
</c:otherwise>
</c:choose>
<%
String reqPath=request.getContextPath();
pageContext.setAttribute("reqPath",reqPath);
%>

</html:form>
</body>
<script type="text/javascript">
	document.location.href="<c:out value='${reqPath}'/>/AttendanceEntry.do?method="+document.getElementById("method").value;
</script>
</html:html>