<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<html:html>
<head>
<title>:: CMS ::</title>
</head>

<body>

<html:form action="AdmissionStatus" method="post">
<%
String reqPath=request.getContextPath();
pageContext.setAttribute("reqPath",reqPath);
%>
<html:hidden property="method" value="initOutsideAccessAdmissionStatusOfStudent"/>
<html:hidden property="formName" value="admissionStatusForm"/>
</html:form>

</body>

</html:html>

<script type="text/javascript">
		document.admissionStatusForm.submit();
	//document.location.href="<c:out value='${reqPath}'/>/AdmissionStatus.do?method=initOutsideAccessAdmissionStatusOfStudent";
</script>