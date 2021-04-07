<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<html:html>
<head>
<title>:: CMS ::</title></head>
<body>
<html:form action="applicationFormRegistration" method="post">
<html:hidden property="method" value="updatePGIResponseProfessor"/>
<html:hidden property="formName" value="applicationRegistrationForm"/>


<html:hidden property="status" styleId="status"/>

<html:hidden property="txnid" styleId="txnid"/>
<html:hidden property="amount" styleId="amount"/>

<html:hidden property="hash" styleId="hash"/>

</html:form>
</body>
<script type="text/javascript">

	
	document.getElementById("status").value="<%= request.getAttribute("status").toString()%>";
	document.getElementById("txnid").value="<%= request.getAttribute("txnid").toString()%>";
	document.getElementById("amount").value="<%= request.getAttribute("amount").toString()%>";
	document.getElementById("hash").value="<%= request.getAttribute("hash").toString()%>";
	
	document.applicationRegistrationForm.submit();
</script>
</html:html>