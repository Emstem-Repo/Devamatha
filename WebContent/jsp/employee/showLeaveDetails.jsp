<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-tld/displaytag.tld" prefix="display"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<head>
	<link rel="stylesheet" type="text/css" href="css/styles.css"/>
</head>
<html:form action="/employeeApplyLeave">
	<html:hidden property="method" styleId="method" value="applyLeave" />	
	<html:hidden property="pageType" value="1" />
	<html:hidden property="formName" value="employeeApplyLeaveForm" />
	<table width="98%" border="0">
		<tr>
			<td class="heading">&gt;&gt;
			<span class="Bredcrumbs">Employee<span
				class="Bredcrumbs">&gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td valign="top">
			<table width="98%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="10"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td width="100%" background="images/Tcenter.gif" class="body">
					<div align="left"><strong class="boxheader">Details</strong></div>
					</td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="8" valign="top" background="images/Tright_03_03.gif"></td>
					<td height="8" valign="top"></td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td height="8" valign="top" background="images/Tright_03_03.gif"></td>
					<td height="8" valign="top">
					<table width="100%" border="0" align="center" cellpadding="0"
						cellspacing="0">
						<tr>
							<td><img src="images/01.gif" width="5" height="5" /></td>
							<td width="914" background="images/02.gif"></td>
							<td><img src="images/03.gif" width="5" height="5" /></td>
						</tr>
						<tr>
							<td width="5" background="images/left.gif"></td>
							<td valign="top">
								<table width="100%" cellpadding="1" cellspacing="2">
										<logic:empty name="details" scope="request">
											<tr>
												<td>
													No Record Found
												</td>
											</tr>
										</logic:empty>
										<logic:notEmpty name="details" scope="request">
											<tr class="row-odd" height="25">
												<td> Date</td>
												<td> Period</td>
												<td> Leave Type</td>
											</tr>
											<logic:iterate id="to" name="details" scope="request">
												<tr class="row-even" height="25">
													<td><bean:write name="to" property="date"/> </td>
													<td><bean:write name="to" property="period"/> </td>
													<td><bean:write name="to" property="empLeaveTypeName"/> </td>
												</tr>
											</logic:iterate>
										</logic:notEmpty>		
								</table>
							
							</td>
							<td width="5" height="30" background="images/right.gif"></td>
						</tr>
						<tr>
							<td height="5"><img src="images/04.gif" width="5" height="5" /></td>
							<td background="images/05.gif"></td>
							<td><img src="images/06.gif" /></td>
						</tr>
					</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
					<td background="images/TcenterD.gif"></td>
					<td><img src="images/Tright_02.gif" width="9" height="29"></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</html:form>