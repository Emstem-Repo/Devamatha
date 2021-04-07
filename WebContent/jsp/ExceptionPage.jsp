<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@page import="com.kp.cms.constants.CMSConstants"%>
<html:html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<html:form action="forwardException">
<%
String formName = request.getParameter(CMSConstants.FORMNAME);
if(formName==null || formName=="")
{
	if(request.getSession(false)!=null && request.getSession(false).getAttribute(CMSConstants.FORMNAME)!=null){
		formName =(String) request.getSession(false).getAttribute(CMSConstants.FORMNAME);
	}
}
%>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="10"><img src="images/Tright_03_01.gif" ></td>
        <td width="100%" background="images/Tcenter.gif" class="body" >
        <strong class="boxheader">Error Message:</strong>
        </td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
    <tr>
		<td valign="top" background="images/Tright_03_03.gif"></td>
		<td class="row-even"><strong class="boxheader"><font color="black"><br><bean:write  name="<%=formName %>" property="errorMessage" /><br>Error Details:<br>
			<bean:write  name="<%=formName %>" property="errorStack" /></font> </strong>
		</td>
		<td width="10" valign="top" background="images/Tright_3_3.gif" class="news"></td>
	</tr>
 	<tr>
        <td><img src="images/Tright_03_05.gif" ></td>
        <td  background="images/Tcenter.gif"></td>
        <td><img src="images/Tright_02.gif" width="9" height="29"></td>
      </tr>
</table>
</html:form>
</body>
</html:html>