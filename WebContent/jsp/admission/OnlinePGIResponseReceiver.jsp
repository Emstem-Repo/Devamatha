<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<html:html>
<head>
<title>:: CMS ::</title></head>
<body>
<html:form action="onlineApplicationSubmit" method="post">
<html:hidden property="method" value="updatePGIResponse"/>
<html:hidden property="formName" value="onlineApplicationForm"/>
<html:hidden property="responseMsg" styleId="responseMsg"/>
</html:form>
</body>
<script type="text/javascript">
	document.getElementById("responseMsg").value="<%= request.getAttribute("responseMsg").toString()%>";
	document.onlineApplicationForm.submit();
</script>
</html:html>
