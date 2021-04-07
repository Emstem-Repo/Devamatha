<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="com.kp.cms.constants.CMSConstants"%><html>

<body>
<form method="post" name="merchantForm" action="https://epay.federalbank.co.in/FedPaymentsV1/Payments.ashx">
   
	<input type="hidden" name="user_code" value="<%= request.getAttribute("user_code") %>"/>
    <input type="hidden" name="pass_code" value="<%= request.getAttribute("pass_code") %>" />
    <input type="hidden" name="tran_id" value="<%= request.getAttribute("tran_id") %>" />
	<input type="hidden" name="amount" value="<%= request.getAttribute("amount") %>" />
    <input type="hidden" name="charge_code" value="<%= request.getAttribute("charge_code") %>" />
    <input type="hidden" name="hash_value" 	value="<%= request.getAttribute("hash_value") %>"/>
    <input type="hidden" name="reserve10" 	value="<%= request.getAttribute("response_url") %>"/>
    
</form>
<script type="text/javascript">
	document.merchantForm.submit();
</script>
</body>
</html>