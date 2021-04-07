	<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
	<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
	<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
	<%@page import="com.kp.cms.constants.CMSConstants"%>
	<html:html>
	<head>
	<title>:: CMS ::</title></head>
	<body>
	<html:form action="/admissionFormSubmit" method="post">
	
	<%
	String reqPath=request.getContextPath();
	pageContext.setAttribute("reqPath",reqPath);
	%>
	<html:hidden property="method" value=""/>
	<html:hidden property="formName" value="admissionFormForm" />
	<% String path = "";
	path = path + CMSConstants.birtLink;
	path = path + "/birtFeeReport.do?method=initBirtViewPeerFeedback&reportName=peerevaluation-teacherlogin";
	path = path + "&userId=";
	path = path + session.getAttribute("userId").toString();
	%>
	<iframe src="<%=path %>" width="100%" height="700" frameborder="0">
	  <p>Your browser does not support iframes.</p>
	</iframe>
	
	</html:form>
	</body>
	</html:html>