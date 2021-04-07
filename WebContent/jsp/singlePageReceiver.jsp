<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<html:html>
<head>
<title>:: CMS ::</title>
</head>
<body>
<html:form action="admissionFormSubmit" method="post">
<%
String reqPath=request.getContextPath();
pageContext.setAttribute("reqPath",reqPath);
%>
<html:hidden property="method" value="initOutsideSinglePageAccess"/>
<html:hidden property="formName" value="admissionFormForm"/>
</html:form>
</body>
<script type="text/javascript">
	document.admissionFormForm.submit();
	//document.location.href="<c:out value='${reqPath}'/>/admissionFormSubmit.do?method=initOutsideSinglePageAccess";
</script>
</html:html>