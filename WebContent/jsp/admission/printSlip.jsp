<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms" %>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<%@page import="com.kp.cms.constants.CMSConstants"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="css/styles.css" rel="stylesheet" type="text/css">
</head>
<script type="text/javascript">
function printMe()
{
	window.print();
}
function closeMe()
{
	window.close();
}
</script>
<body>
<html:form action="/appAcknowledgement">

<html:hidden property="formName" value="applicationAcknowledgeForm"/>
<html:hidden property="pageType" value="2"/>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
						
	<tr>
		<td height="20" valign="top" background="images/Tright_03_03.gif"></td>
		<td valign="top">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td><img src="images/01.gif" width="5" height="5"></td>
					<td width="100%" background="images/02.gif"></td>
					<td width="11"><img src="images/03.gif" width="5"
						height="5"></td>
				</tr>
				<tr>
					<td width="10" background="images/left.gif"></td>
					<td valign="top">
						<table width="100%">
						   
						    <tr><td colspan="2" height="25" class="row-white" ><div align="center"><img src='<%=CMSConstants.LOGO_URL%>' alt="Logo not available"></div></td></tr>
							<tr>
							<td colspan="2" align="center"><font size="5"><b>APPLICATION ACKNOWLEDGMENT CARD</b></font>
							 </td>
							 </tr>
							 <tr>
							 <td height="25" class="row-white"><div align='left'><b><bean:message key="knowledgepro.admin.course" />: <bean:write name="applicationAcknowledgeForm" property="courseName" /></b></div>
							 </td>
							 <td height="25" class="row-white"><div align='right'><b><bean:message key="knowledgepro.attendanceentry.slipNo" />: <bean:write name="applicationAcknowledgeForm" property="slipNo" /></b></div>
							 </td>
							 </tr>
							</table>
							<table width="100%" border="1">
							<tr>
							     <td  height="25" align="right">
							     <div align='center'>
							     <bean:message
									key="knowledgepro.admission.applicationAcknowledge.date" /></div></td>
									<td  height="25" align="right"><div align='center'><bean:message
									key="knowledgepro.fee.applicationno" /></div></td>
									<td  height="25" align="right"><div align='center'><bean:message
									key="knowledgepro.admin.name" /></div></td>
								
							</tr>
							<tr>
							<td align="center">
							<bean:write
									name="applicationAcknowledgeForm" property="receivedDate"/></td>
							<td  height="25" align="center"><bean:write	name="applicationAcknowledgeForm" property="appNo" />
								</td>
								<td  height="25" align="center">
								<bean:write	name="applicationAcknowledgeForm" property="name" /></td>
							</tr>
							</table>
					</td>
					<td background="images/right.gif" width="11" height="3"></td>
				</tr>
				
				<tr>
				<td width="10" background="images/left.gif"></td>
				    <td valign="top">
				        <table width="100%">
				        <tr><td></td></tr>
				        <tr height="25">
				        	<td> Kindly Check the application status link for selection process date and time</td>
				        </tr>
				         <tr><td></td></tr>
							<tr> 
							     <td align="right"><div align='right'>
	                                  Signature	&nbsp;&nbsp;&nbsp;&nbsp;</div>					     
							     </td>
							</tr>
							<tr><td></td></tr>
							<tr><td align="right">.....................</td></tr>
						
						</table>
				    </td>
				    <td background="images/right.gif" width="11" height="3"></td>
				</tr>
				<tr>
					<td height="5"><img src="images/04.gif" width="5" height="5"></td>
					<td background="images/05.gif"></td>
					<td><img src="images/06.gif"></td>
				</tr>
				<tr></tr>
				<tr></tr>
			</table>
		</td>
	</tr>
</table>
			
</html:form>
</body>
<script type="text/javascript">
	window.print();
</script>
