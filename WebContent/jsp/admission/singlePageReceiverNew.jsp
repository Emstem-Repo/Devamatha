<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<html:html>
<head>
<title>:: CMS ::</title>
</head>

<body>
<html:form action="uniqueIdRegistration" method="post">
<html:hidden property="method" value="initOnlineApplicationLogin"/>
<html:hidden property="formName" value="uniqueIdRegistrationForm"/>

</html:form>
</body>

</html:html>
<script type="text/javascript">
		document.uniqueIdRegistrationForm.submit();
</script>