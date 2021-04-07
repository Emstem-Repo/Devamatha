<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>

<script type="text/javascript">

function goBack(){
	document.location.href = "ScLostCorrectionCancel.do?method=initScLostCorrectionCancel";
}
function cancelScLostCorrection(id){
	document.location.href = "ScLostCorrectionCancel.do?method=cancelScLostCorrection&id="+ id;
}
</script>

<html:form action="/ScLostCorrectionCancel" method="post">
<html:hidden property="formName" value="ScLostCorrectionForm" />
<html:hidden property="pageType" value="2"/>
<html:hidden property="method" styleId="method" value="cancelScLostCorrection"/>
<html:hidden property="id" />

<table width="100%" border="0">
		<tr>
		<td><span class="Bredcrumbs"><bean:message key="knowledgepro.smartcard"/><span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.smartcard.lostcorrection"/> Cancellation</span></span></td>
		</tr>
		<tr>
		<td valign="top">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"><bean:message key="knowledgepro.smartcard.lostcorrection"/> Cancellation</strong></td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="30" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<div align="right"><span class='MandatoryMark'></span></div>
					<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
					<FONT color="green"><html:messages id="msg" property="messages" message="true">
						<c:out value="${msg}" escapeXml="false"></c:out>
						<br>
					</html:messages> </FONT></div>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td height="30" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
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
							<table width="100%" cellspacing="1" cellpadding="1">
						<tr>
							<td width="16%" height="30" class="row-odd" align="center">Name</td>
							<logic:equal name="ScLostCorrectionForm" property="isEmployee" value="Student">
								<td width="10%" height="30" class="row-odd" align="center">Register No.</td>
							</logic:equal>
							<logic:equal name="ScLostCorrectionForm" property="isEmployee" value="Employee">
								<td width="10%" height="30" class="row-odd" align="center">Employee ID</td>
							</logic:equal>
							<logic:equal name="ScLostCorrectionForm" property="isEmployee" value="Student">
								<td width="10%" height="30" class="row-odd" align="center">Class</td>
							</logic:equal>
							<logic:equal name="ScLostCorrectionForm" property="isEmployee" value="Employee">
								<td width="10%" height="30" class="row-odd" align="center">Department</td>
							</logic:equal>
							<td width="14%" height="30" class="row-odd" align="center">Smart Card Type Applied</td>
							<td width="10%" height="30" class="row-odd" align="center">Applied Date</td>
							<td width="15%" height="30" class="row-odd" align="center">Remarks</td>
							<td width="15%" height="30" class="row-odd" align="center">Status</td>
							<td width="10%" height="30" class="row-odd" align="center">Cancel Request</td>
						</tr>
						
						<tr>
									<td align="left" width="16%" class="row-even">
									<bean:write name="ScLostCorrectionForm" property="name" /></td>
									<logic:equal name="ScLostCorrectionForm" property="isEmployee" value="Student">
										<td align="center" width="10%" class="row-even">
										<bean:write name="ScLostCorrectionForm" property="regNo" /></td>
									</logic:equal>
									<logic:equal name="ScLostCorrectionForm" property="isEmployee" value="Employee">
										<td align="center" width="10%" class="row-even">
										<bean:write name="ScLostCorrectionForm" property="empId" /></td>
									</logic:equal>
									<logic:equal name="ScLostCorrectionForm" property="isEmployee" value="Student">
										<td align="center" width="10%" class="row-even">
										<bean:write name="ScLostCorrectionForm" property="className" /></td>
									</logic:equal>
									<logic:equal name="ScLostCorrectionForm" property="isEmployee" value="Employee">
										<td align="center" width="10%" class="row-even">
										<bean:write name="ScLostCorrectionForm" property="empDepartment" /></td>
									</logic:equal>
									<td align="center" width="14%" class="row-even" >
									<bean:write name="ScLostCorrectionForm" property="cardType" /></td>
									<td align="center" width="10%" class="row-even">
									<bean:write name="ScLostCorrectionForm" property="appliedDate" /></td>
									<td align="left" width="15%" class="row-even">
									<bean:write name="ScLostCorrectionForm" property="remarks" /></td>
									<td align="center" width="15%" class="row-even">
									<bean:write name="ScLostCorrectionForm" property="status" /></td>
									
									<td width="10%" height="25" class="row-even">
											<div align="center"><img src="images/delete_icon.gif"
												width="16" height="16" style="cursor: pointer"
												onclick="cancelScLostCorrection('<bean:write name="ScLostCorrectionForm" property="id"/>')"></div>
									</td>
						</tr>
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
				<td height="10" valign="top" background="images/Tright_03_03.gif"></td>
				<td><div></div></td>
				<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td height="20" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							
							<td align="center">
							<html:button property="" styleClass="formbutton" value="Close" onclick="goBack()"></html:button></td>
						</tr>
					</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">&nbsp;</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
					<td width="0" background="images/TcenterD.gif"></td>
					<td><img src="images/Tright_02.gif" width="9" height="29"></td>
				</tr>
			</table>
		</td>
	</tr>
</table>
</html:form>
				