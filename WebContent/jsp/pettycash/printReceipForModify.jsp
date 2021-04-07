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
<html:form action="/modifyCashCollection">

<html:hidden property="formName" value="modifycashCollectionForm"/>
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
							<tr>
							<td colspan="6" height="25" class="row-white" ><div><img src='<%=CMSConstants.LOGO_URL%>' alt="Logo not available"></div></td>
							</tr>
							<tr>
								<td  height="25" align="right"><bean:message
									key="knowledgepro.petticash.recieptNo" />:</td>
								<td  height="25" align="left"><bean:write
									name="modifycashCollectionForm" property="recNoResult" /></td>
								<td  align="right">Date:</td>
								<td  align="left"><bean:write
									name="modifycashCollectionForm" property="paidDate" /></td>
								<td  height="25" align="right">Time:</td>
								<td align="left"><bean:write
									name="modifycashCollectionForm" property="time" /></td>
							</tr>
							<tr>
								<td height="25" align="right">No:</td>
								<td height="25" align="left"><bean:write
									name="modifycashCollectionForm" property="refNo" /></td>
								<td height="25" align="right">Name:</td>
								<td height="25"  colspan="3" align="left"><bean:write
									name="modifycashCollectionForm" property="nameOfStudent" /></td>
							</tr>
							<tr>
							<td colspan="6" align="center"><font size="3"><b>CASH BILL</b></font> </td>
							</tr>
							<tr>
							<td colspan="6" valign="top">
								<table width="100%" style="border:1px solid #000000">
									<tr>
									<td height="25" align="center" style="border:1px solid #000000"><b>Sl.No</b></td>
									<td height="25" align="center" style="border:1px solid #000000"><b>Particulars</b></td>
									<td height="25" align="center" style="border:1px solid #000000"><b>Details</b></td>
									<td height="25" align="center" style="border:1px solid #000000"><b>Amount</b></td>
									</tr>
									<logic:notEmpty name="modifycashCollectionForm" property="accountList">
										<logic:iterate id="details" name="modifycashCollectionForm" property="accountList" indexId="count">
											<tr>
												<td height="25" align="center"><c:out value="${count + 1}" /></td>
												<td height="25" align="left"><bean:write	name="details" property="accName" /></td>
												<td height="25" align="left"><bean:write	name="details" property="details" /></td>
												<td height="25" align="right"><bean:write name="details" property="amount" /></td>
											</tr>
										</logic:iterate>
									</logic:notEmpty>
									<tr>
										<td width="20%" height="25"></td>
										<td width="60%" height="25" align="right"><b>TOTAL Rs:</b></td>
										<td width="20%" height="25" align="right">
										<bean:write name="modifycashCollectionForm" property="total" />
										</td>
									</tr>
								</table>
							
							</td>
							</tr>
							<tr> 
							<td colspan="2" align="left"><bean:write name="username" /></td>
							<td colspan="2" align="left">Rupees:&nbsp;<bean:write name="modifycashCollectionForm" property="rupeesInWords" /> ONLY</td>
							<td colspan="2"></td>
							</tr>
						</table>
						
					</td>
					<td background="images/right.gif" width="11" height="3"></td>
				</tr>
				<tr>
					<td height="5"><img src="images/04.gif" width="5" height="5"></td>
					<td background="images/05.gif"></td>
					<td><img src="images/06.gif"></td>
				</tr>
			</table>
		</td>
	</tr>
</table>
			
</html:form>
</body>
<script type="text/javascript">
	window.print();
</script>