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
	document.location.href = "ScViewStudentDetails.do?method=initScViewStudentDetails";
}

</script>

<html:form action="/ScViewStudentDetails" method="post">
<html:hidden property="formName" value="ScLostCorrectionForm" />
<html:hidden property="pageType" value="2"/>
<html:hidden property="method" styleId="method" value="addScLostCorrection"/>
<html:hidden property="id" />
<table width="100%" border="0">
		<tr>
		<td><span class="Bredcrumbs"><bean:message key="knowledgepro.inventory.vendor.bank" />
		<span class="Bredcrumbs">&gt;&gt;View Student Details</span></span></td>
		</tr>
		<tr>
		<td valign="top">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader">View Student Details</strong></td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="30" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<div align="right"></div>
					<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
					<FONT color="green"><html:messages id="msg" property="messages" message="true">
						<c:out value="${msg}" escapeXml="false"></c:out>
						<br>
					</html:messages> </FONT></div>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td height="20" valign="top" background="images/Tright_03_03.gif">
					<div id="errorMessage"></div>
					</td>
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
							<table width="100%" cellspacing="1" cellpadding="2">
							<tr>
									<td height="1" colspan="2" class="heading" align="left">Student Details
									</td>
								</tr>
								<tr>
								<td align="center" colspan="4">
								<table width="100%" height="22" border="0" cellpadding="0">
										<tr class="row-white">

											<td width="50%" height="101" align="center" class="row-even"><img
												src='<%=request.getContextPath()%>/DisplinaryPhotoServlet'
												height="150Px" width="150Px" /></td>
										</tr>
								</table>
								</td>
								</tr>
								<tr>
									<td width="20%" height="30" class="row-odd"><div align="right"><bean:message key="knowledgepro.hostel.reservation.registerNo"/></div></td>
                                    <td width="30%" class="row-even"><bean:write name="ScLostCorrectionForm" property="regNo" /></td>
                                    <td width="20%" height="25" class="row-odd"></td>
                                    <td width="30%" class="row-even"></td>
                                 </tr>
                                 <tr>
                                	<td width="20%" height="30" class="row-odd"><div align="right"><bean:message key="knowledgepro.fee.studentname"/>:</div></td>
                                    <td width="30%" class="row-even"><bean:write name="ScLostCorrectionForm" property="name" /></td>
                                    <td width="20%" height="25" class="row-odd"><div align="right"><bean:message key="knowledgepro.attendance.classname"/>:</div></td>
                                    <td width="30%" class="row-even"><bean:write name="ScLostCorrectionForm" property="className" /></td>
                                    
                                 </tr>
                                 <tr>
                                    <td width="20%" height="30" class="row-odd"><div align="right"><bean:message key="knowledgepro.inventory.vendor.smart.card.no"/>.:</div></td>
                                    <td width="30%" class="row-even"><bean:write name="ScLostCorrectionForm" property="oldSmartCardNo" /></td>
                                    <td width="20%" height="25" class="row-odd"><div align="right"><bean:message key="knowledgepro.petticash.AccountNo"/>.:</div></td>
                                    <td width="30%" class="row-even"><bean:write name="ScLostCorrectionForm" property="accountNo" /></td>
                                 </tr>
                                 <tr>
                                 	<td width="20%" height="30" class="row-odd"><div align="right">Course Valid Till:</div></td>
                                    <td width="30%" class="row-even"><bean:write name="ScLostCorrectionForm" property="studentCourseDuration" /></td>
                                 	<td width="20%" height="25" class="row-odd"><div align="right"><bean:message key="admissionForm.studentinfo.currAddr.label"/>:</div></td>
                                    <td width="30%" class="row-even"><bean:write name="ScLostCorrectionForm" property="currentAddress" /></td>
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
							<html:button property="" styleClass="formbutton" value="Back"
										onclick="goBack()"></html:button>
						</td>
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