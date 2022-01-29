<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms" %>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<link href="css/styles.css" rel="stylesheet" type="text/css">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="css/styles.css" rel="stylesheet" type="text/css">
</head>
<body>
<html:form action="/EmployeeApproveLeave">
<html:hidden property="method" styleId="method" value=""/>
<html:hidden property="formName" value="employeeApproveLeaveForm"/>
<html:hidden property="pageType" value="1"/>
<table width="100%" border="0">
	<tr>
		<td>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td width="9"><img src="images/Tright_03_01.gif" width="9"
					height="29"></td>
				<td background="images/Tcenter.gif" class="body"><strong
					class="boxheader">Available Leave</strong></td>
				<td width="10"><img src="images/Tright_1_01.gif" width="9"
					height="29"></td>
			</tr>
			<tr>
				<td height="122" valign="top" background="images/Tright_03_03.gif"></td>

				<td height="10" class="body"><br />
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
						<table width="100%" cellspacing="1" cellpadding="2" border="0" style="height:100%"  >
							<logic:notEmpty property="availableLeaveList" name = "employeeApproveLeaveForm">
							
										<tr>
											<td class="row-odd">Leave Type</td>
											<td class="row-odd">Allocated</td>
											<td class="row-odd">Sanctioned</td>
											<td class="row-odd">Remaining</td>
										</tr>
									<logic:iterate id="leave" property="availableLeaveList" name= "employeeApproveLeaveForm">
										<tr>
											<td class="row-even"><bean:write name="leave" property="empLeaveTypeName"/></td>
											<td class="row-even"><bean:write name="leave" property="leavesAllocated"/></td>
											<td class="row-even"><bean:write name="leave" property="leavesSanctioned"/></td>
											<td class="row-even"><bean:write name="leave" property="leavesRemaining"/></td>
										</tr>
									</logic:iterate>

							</logic:notEmpty>
						</table>
						<br>
						</td>
						<td width="5" height="30" background="images/right.gif"></td>
					</tr>
				<tr>
                   <td width="5"  background="images/left.gif"></td>
                   <td height="35" valign="top" class="body" >
                   <table width="100%" height="27"  border="0" cellpadding="0" cellspacing="0">
                     <tr>
                       <td colspan="3" align="center"><div align="center">
                       <input type="button" class="formbutton" value="Close" onclick="javascript:self.close();"/>
                       
                       </div></td>
                      
                     </tr>
                   </table>
                   </td>
                   <td width="5" align="right" background="images/right.gif"></td>
                 </tr>
					<tr>
						<td height="5"><img src="images/04.gif" width="5" height="5" /></td>
						<td background="images/05.gif"></td>
						<td><img src="images/06.gif" /></td>
					</tr>
				</table>
				<br />
				</td>
				<td height="122" width="13" valign="top"
					background="images/Tright_3_3.gif" class="news"></td>
			</tr>
			<tr>
				<td><img src="images/Tright_03_05.gif" width="9" height="29" /></td>
				<td width="100%" background="images/TcenterD.gif"></td>
				<td><img src="images/Tright_02.gif" width="9" height="29" /></td>
			</tr>
		</table>
		</td>
	</tr>
</table>
</html:form>
</body>
</html>