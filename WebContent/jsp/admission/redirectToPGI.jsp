<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.kp.cms.constants.CMSConstants"%><html>
<body>
  	<!-- <form method="post" name="payuForm" action="https://secure.payu.in/_payment"> -->
    
 	<%-- <form method="post" name="payuForm" action="https://test.payu.in/_payment">
    <form method="post" name="payuForm" action="https://test.payu.in/_payment">--%>
  <!--  <form id="AWLPGPost" action="https://cgt.in.worldline.com/ipg/doMEPayRequest" method="POST" >--> 
      <form id="AWLPGPost" action="https://ipg.in.worldline.com/doMEPayRequest" method="POST" > 
   
   <%--  <input type="hidden" name="msg" value="<%= request.getAttribute("pgiMsg") %>" />
     
	<input type="hidden" name="hash" value="<%= request.getAttribute("hash") %>"/>
    <input type="hidden" name="txnid" value="<%= request.getAttribute("txnid") %>" />
    <input type="hidden" name="key" value=<%= request.getAttribute("key") %> />
	<input type="hidden" name="service_provider" value="payu_paisa" />
    <input type="hidden" name="productinfo" value="<%= request.getAttribute("productinfo") %>" />
    <input type="hidden" name="amount" value="<%= request.getAttribute("amount") %>"/>
    <input type="hidden" name="email" value="<%= request.getAttribute("email") %>" />
    <input type="hidden" name="firstname" value="<%= request.getAttribute("firstname") %>" />
	<input type="hidden" name="phone" value="<%= request.getAttribute("phone") %>" />
	<input type="hidden" name="surl" value="<%= request.getAttribute("surl") %>" />
    <input type="hidden" name="furl" value="<%= request.getAttribute("furl") %>" /> --%>
	<%--<input type="hidden" name="test" value="<%= request.getAttribute("test") %>" />
	--%>
    <input type="hidden" name="merchantRequest"  value="<%= request.getAttribute("merchantRequest") %>" />
     <input type="hidden" name="MID" value="<%=CMSConstants.PGI_WLS_MERCHANT_ID %>"/> 
   <input type="submit" name="checkout" value="Click here to Pay Online">
</form>
</body>
</html>