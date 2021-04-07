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

<%--<html:hidden property="responseMsg" styleId="responseMsg"/>--%>

<html:hidden property="mihpayid" styleId="mihpayid"/>
<html:hidden property="mode1" styleId="mode1"/>
<html:hidden property="status" styleId="status"/>
<html:hidden property="key" styleId="key"/>
<html:hidden property="txnid" styleId="txnid"/>
<html:hidden property="amount" styleId="amount"/>
<html:hidden property="productinfo" styleId="productinfo"/>
<html:hidden property="hash" styleId="hash"/>
<html:hidden property="error1" styleId="Error"/>
<html:hidden property="PG_TYPE" styleId="PG_TYPE"/>
<html:hidden property="bank_ref_num" styleId="bank_ref_num"/>
<html:hidden property="unmappedstatus" styleId="unmappedstatus"/>
<html:hidden property="payuMoneyId" styleId="payuMoneyId"/>
<html:hidden property="applicantName" styleId="firstname"/>
<html:hidden property="additionalCharges" styleId="additionalCharges"/>
<html:hidden property="paymentMail" styleId="paymentMail"/>

</html:form>
</body>
<script type="text/javascript">

	document.getElementById("mihpayid").value="<%= request.getAttribute("mihpayid").toString()%>";
	document.getElementById("mode1").value="<%= request.getAttribute("mode").toString()%>";
	document.getElementById("status").value="<%= request.getAttribute("status").toString()%>";
	document.getElementById("key").value="<%= request.getAttribute("key").toString()%>";
	document.getElementById("txnid").value="<%= request.getAttribute("txnid").toString()%>";
	document.getElementById("amount").value="<%= request.getAttribute("amount").toString()%>";
	document.getElementById("hash").value="<%= request.getAttribute("hash").toString()%>";
	document.getElementById("Error").value="<%= request.getAttribute("Error").toString()%>";
	document.getElementById("PG_TYPE").value="<%= request.getAttribute("PG_TYPE").toString()%>";
	document.getElementById("bank_ref_num").value="<%= request.getAttribute("bank_ref_num").toString()%>";
	document.getElementById("unmappedstatus").value="<%= request.getAttribute("unmappedstatus").toString()%>";
	document.getElementById("payuMoneyId").value="<%= request.getAttribute("payuMoneyId").toString()%>";
	document.getElementById("productinfo").value="<%= request.getAttribute("productinfo").toString()%>";
	document.getElementById("firstname").value="<%= request.getAttribute("firstname").toString()%>";
	document.getElementById("additionalCharges").value="<%= request.getAttribute("additionalCharges").toString()%>";
	document.getElementById("paymentMail").value="<%= request.getAttribute("email").toString()%>";
	
	document.onlineApplicationForm.submit();
</script>
</html:html>