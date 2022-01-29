	<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	    pageEncoding="ISO-8859-1"%>
	<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
	<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
	<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic" %>
	<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
	<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean" %>
	<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms" %>
	<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
	<%@page import="com.kp.cms.constants.CMSConstants"%>
	<html>
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link href="css/styles.css" rel="stylesheet" type="text/css">
	<style type="text/css">
			input {
			border:0;
			}
			</style>
	<script type="text/javascript" src="js/jquery.js">
		</script>
	<script type="text/javascript">
		window.print();
	</script>
	</head>
	<body>
	<html:form action="/tempHallTicket">
	<html:hidden property="method" styleId="method" value=""/>
	<html:hidden property="formName" value="tempHallTicketOrIDCardForm"/>
	<html:hidden property="pageType" value="3"/>
		<table  border="0"  align="center">
			<tr>
				<td align="center" width="80%" >
					<img src='<%=CMSConstants.LOGO_URL%>' alt="Logo not available" align="middle"/>
				</td>
				 
			</tr>
			<tr>
				<td style=" line-height: 350%">
					<table align="center">
						<tr >
							<td ><font style="bold" size="5"><u>TEMPORARY HALL TICKET</u></font></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		
		<table  width="100%">
		<tr>
			<td align="left" style="padding-left: 20px; padding-right: 20px;">
      			<table >
      				<tr>
      					<td align="left" style=" line-height: 250%"><font style="bold" >This is a temporary HALL TICKET slip issued to:&nbsp;</font><bean:write name="tempHallTicketOrIDCardForm" property="nameOfStudent"/></td>
					</tr>
					<tr>	
						<td align="left" style=" line-height: 250%">Register No:&nbsp;<bean:write name="tempHallTicketOrIDCardForm" property="regNo"/></td>
					</tr>
					<tr>	
						<td align="left" style=" line-height: 250%">Valid till:&nbsp;<bean:write name="tempHallTicketOrIDCardForm" property="date"/> </td>
					</tr>
					<tr>
						<td align="left" style=" line-height: 250%">Class:&nbsp;<bean:write name="tempHallTicketOrIDCardForm" property="className"/></td>
      				</tr>
      			</table>
			</td>
			<td align="right" style="padding-left:20px; padding-right: 20px; "> 
      			<table >
      				<tr>
      					<td>
							<img src='<%=request.getContextPath()%>/PhotoServlet' height="150" width="150" align="right"/>
						</td>
      				</tr>
      			</table>
			</td>
		</tr>
			
		
		</table>
		<table style=" line-height: 1200%; padding-left: 500px;">
		
		<tr>
				<td align="right" >Issuing Authority</td>
		</tr>
		 
		</table>
		<table  width="100%">
			<tr height="25">
			<td>
				
			</td>
			</tr>
			<tr height="25">
			<!--<td align="center"><div id="hideButton">
				<html:button property="" styleClass="formbutton" value="Print" onclick="printPass()"></html:button></div>
			</td>
			--></tr>
		</table>
	</html:form>
	
	
	</body>
</html>
	