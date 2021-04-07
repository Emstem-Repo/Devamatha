<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>

<%@page import="java.util.Map,java.util.HashMap"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<link href="styles.css" rel="stylesheet" type="text/css"/>
<SCRIPT type="text/javascript">
function loginPage(){
	document.location.href = "employeeOnlineLeave.do?method=initApproveLeave";
}

</SCRIPT>

<html:form action="/employeeOnlineLeave">
	<html:hidden property="method" styleId="method" value="sendEmployeeLeave"/>
	<html:hidden property="formName" value="employeeOnlineLeaveForm" />
	<table width="100%" border="0">
		<tr>
			<td><span class="heading"> <bean:message
				key="knowledgepro.employee" /> <span class="Bredcrumbs">&gt;&gt;
			Pending Approvals&gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> Pending Approvals</strong></td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				
				<tr>
					<td height="122" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" cellspacing="1" cellpadding="2">
					<tr>
					<td colspan="4" class="heading" align="left">&nbsp;<bean:message
								key="employee.info.job.leave.title" /></td>
					</tr>
						<tr>
							<td valign="top" class="news">
							<table width="100%" border="0" align="center" cellpadding="0"
								cellspacing="0">
								<tr>
									<td><img src="images/01.gif" width="5" height="5" /></td>
									<td width="1310" background="images/02.gif"></td>
									<td><img src="images/03.gif" width="5" height="5" /></td>
								</tr>
								<tr>
									<td width="5" background="images/left.gif"></td>
									
									<td height="25" colspan="4">
											<table width="100%" cellspacing="1" cellpadding="2">
												<tr>
											 		<td height="25" class="row-odd" width="25%"><div align="right">Employee</div></td>
											        <td height="25" class="row-even">
											        	<html:select property="employeeId" styleClass="comboLarge" styleId="employeeId">
															<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
															<html:optionsCollection name="employeeMap" label="value" value="key" />
														</html:select>
											        </td>						
										       </tr>
										       <% String message="Dear Sir \n\nPlease find the attached online applications of () candidates for your department \n\nRegards \nPersonnel Office \n\nPh: 40129087"; %>
										       <tr>
											 		<td height="25" class="row-odd" width="25%"><div align="right">Enter Mail Message:</div></td>
											        <td height="25" class="row-even">
											        	<label>
															<html:textarea name="employeeOnlineLeaveForm" property="mailBody"  style="width: 500px; height: 140px"/>
														</label></td>						
										       </tr>
											</table>
											</td>
									<td width="5" height="29" background="images/right.gif"></td>
								</tr>
								<tr>
									<td height="5"><img src="images/04.gif" width="5"
										height="5" /></td>
									<td background="images/05.gif" ></td>
									<td><img src="images/06.gif" /></td>
								</tr>
							</table>
							</td>
							<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
						</tr>
						
						<tr>
							<td valign="top" class="news">
							
								<div align="center">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
							<td height="35" align="right">
								<html:submit  value="Send" styleClass="formbutton"  property=""></html:submit>
							</td>
							<td height="35" align="left">
								&nbsp;&nbsp;&nbsp;<html:button  value="Cancel" styleClass="formbutton" onclick="loginPage()" property=""></html:button>
							</td>
				          </tr>
						</table>
					</div>
							</td>
							<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
						</tr>

					</table>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td height="20" valign="top"></td>
					<td width="13" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
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
