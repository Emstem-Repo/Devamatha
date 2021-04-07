<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>

<script type="text/javascript">

function addScLostCorrection() {
	document.getElementById("method").value = "addScLostCorrection";
	document.ScLostCorrectionForm.submit();
}

function goBack(){
	document.location.href = "ScLostCorrection.do?method=initScLostCorrection";
}

function imposeMaxLength(evt, Object) {
	var keynum = (evt.which) ? evt.which : event.keyCode;
	if (keynum == 8 || keynum == 37 || keynum == 39 || keynum == 9) {
		return true;
	}
	var MaxLen = 250;
	return (Object.value.length < MaxLen);
}
</script>

<html:form action="/ScLostCorrection" method="post">
<html:hidden property="formName" value="ScLostCorrectionForm" />
<html:hidden property="pageType" value="2"/>
<html:hidden property="method" styleId="method" value="addScLostCorrection"/>
<html:hidden property="id" />

<table width="100%" border="0">
		<tr>
		<td><span class="Bredcrumbs"><bean:message
				key="knowledgepro.smartcard" /> <span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.smartcard.lostcorrection" />&gt;&gt;</span></span></td>
		</tr>
		<tr>
		<td valign="top">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> <bean:message
						key="knowledgepro.smartcard.lostcorrection" /></strong></td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="30" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<div align="right"><span class='MandatoryMark'> <bean:message key="knowledgepro.mandatoryfields" /></span></div>
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
							<logic:equal name="ScLostCorrectionForm" property="isEmployee" value="Student">
								<tr>
									<td height="1" colspan="2" class="heading" align="left">Student Details</td>
								</tr>
								<tr>
									<td width="20%" height="25" class="row-odd"><div align="right"><bean:message key="knowledgepro.hostel.reservation.registerNo"/></div></td>
                                    <td width="30%" class="row-even">&nbsp;<bean:write name="ScLostCorrectionForm" property="regNo" /></td>
                                    <td width="20%" height="25" class="row-odd"></td>
                                    <td width="30%" class="row-even"></td>
                                 </tr>
                                 <tr>
                                	<td width="20%" height="25" class="row-odd"><div align="right"><bean:message key="knowledgepro.admin.name"/>:</div></td>
                                    <td width="30%" class="row-even">&nbsp;<bean:write name="ScLostCorrectionForm" property="name" /></td>
                                    <td width="20%" height="25" class="row-odd"><div align="right"><bean:message key="knowledgepro.attendance.classname"/>:</div></td>
                                    <td width="30%" class="row-even">&nbsp;<bean:write name="ScLostCorrectionForm" property="className" /></td>
                                    
                                 </tr>
                                 <tr>
                                    <td width="20%" height="25" class="row-odd"><div align="right"><bean:message key="knowledgepro.inventory.vendor.smart.card.no"/>.:</div></td>
                                    <td width="30%" class="row-even">&nbsp;<bean:write name="ScLostCorrectionForm" property="oldSmartCardNo" /></td>
                                    <td width="20%" height="25" class="row-odd"><div align="right"><bean:message key="knowledgepro.petticash.AccountNo"/>.:</div></td>
                                    <td width="30%" class="row-even">&nbsp;<bean:write name="ScLostCorrectionForm" property="accountNo" /></td>
                                 </tr>
                                 <tr>
                                 	<td width="20%" height="25" class="row-odd"><div align="right">Course Valid Till:</div></td>
                                    <td width="30%" class="row-even">&nbsp;<bean:write name="ScLostCorrectionForm" property="studentCourseDuration" /></td>
                                 	<td width="20%" height="25" class="row-odd"><div align="right"><bean:message key="admissionForm.studentinfo.currAddr.label"/>:</div></td>
                                    <td width="30%" class="row-even">&nbsp;<bean:write name="ScLostCorrectionForm" property="currentAddress" /></td>
                                  </tr>
                              </logic:equal>
                              <logic:equal name="ScLostCorrectionForm" property="isEmployee" value="Employee">
                              <tr>
									<td height="1" colspan="2" class="heading" align="left">Employee Details</td>
								</tr>
								<tr>
									<td width="20%" height="25" class="row-odd"><div align="right"><bean:message key="knowledgepro.hostel.checkin.fingerPrintId"/></div></td>
                                    <td width="30%" class="row-even">&nbsp;<bean:write name="ScLostCorrectionForm" property="empFingerPrintId" /></td>
                                    <td width="20%" height="25" class="row-odd"></td>
                                    <td width="30%" class="row-even"></td>
                                 </tr>
                                 <tr>
                                	<td width="20%" height="25" class="row-odd"><div align="right"><bean:message key="knowledgepro.admin.name"/>:</div></td>
                                    <td width="30%" class="row-even">&nbsp;<bean:write name="ScLostCorrectionForm" property="name" /></td>
                                    <td width="20%" height="25" class="row-odd"><div align="right"><bean:message key="knowledgepro.admin.sec.Department"/>:</div></td>
                                    <td width="30%" class="row-even">&nbsp;<bean:write name="ScLostCorrectionForm" property="empDepartment" /></td>
                                    
                                 </tr>
                                 <tr>
                                	<td width="20%" height="25" class="row-odd"><div align="right"><bean:message key="knowledgepro.admin.sec.Designation"/>:</div></td>
                                    <td width="30%" class="row-even">&nbsp;<bean:write name="ScLostCorrectionForm" property="empDesignation" /></td>
                                    <td width="20%" height="25" class="row-odd"><div align="right"><bean:message key="knowledgepro.inventory.vendor.smart.card.no"/>.:</div></td>
                                    <td width="30%" class="row-even">&nbsp;<bean:write name="ScLostCorrectionForm" property="oldSmartCardNo" /></td>
                                 </tr>
                                 <tr>
                                 	<td width="20%" height="25" class="row-odd"><div align="right"><bean:message key="knowledgepro.petticash.AccountNo"/>.:</div></td>
                                    <td width="30%" class="row-even">&nbsp;<bean:write name="ScLostCorrectionForm" property="accountNo" /></td>
                                 	<td width="20%" height="25" class="row-odd"><div align="right"><bean:message key="admissionForm.studentinfo.currAddr.label"/>:</div></td>
                                    <td width="30%" class="row-even">&nbsp;<bean:write name="ScLostCorrectionForm" property="currentAddress" /></td>
                                  </tr>
                              </logic:equal>
                              </table>
                                  
									<table width="100%" height="22" border="0" cellpadding="0">
										<tr class="row-white">

											<td width="50%" height="101" align="center" class="row-even"><img
												src='<%=request.getContextPath()%>/DisplinaryPhotoServlet'
												height="150Px" width="150Px" /></td>
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
				
				
				
				<logic:notEmpty name="ScLostCorrectionForm" property="displayHistory">
				<tr>
					<td height="10" valign="top" background="images/Tright_03_03.gif">
					<div id="errorMessage"></div>
					</td>
					<td valign="top" class="news" height="10">
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
									<td height="1" colspan="2" class="heading" align="left">History
									</td>
								</tr>
								<tr height="30">
							<td height="25" class="row-odd" ><div align="center"><bean:message key="knowledgepro.slno"/></div></td>
							<td class="row-odd" align="center">Type</td>
							<td class="row-odd" align="center">Applied Date</td>
							<td class="row-odd" align="center">Status</td>
							<td class="row-odd" align="center">Issued Date</td>
							<td class="row-odd" align="center">Cancelled Date</td>
							<td class="row-odd" align="center">Old Smart Card Number</td>
							<td class="row-odd" align="center">Remarks</td>
						</tr>
								<logic:notEmpty name="ScLostCorrectionForm" property="scHistory">
				       <logic:iterate id="to" name="ScLostCorrectionForm" property="scHistory" indexId="count">
				        <tr>
				                   		<td width="5%" height="25" class="row-even" ><div align="center"><c:out value="${count + 1}"/></div></td>
				                   		<td width="10%" height="25" class="row-even" align="center" ><bean:write name="to" property="cardType"/></td>
				                   		<td width="10%" height="25" class="row-even" align="center" ><bean:write name="to" property="appliedOn"/></td>
				                   		<td width="10%" height="25" class="row-even" align="center" ><bean:write name="to" property="status"/></td>
				                   		<td width="10%" height="25" class="row-even" align="center" ><bean:write name="to" property="issuedDate"/></td>
				                   		<td width="10%" height="25" class="row-even" align="center" ><bean:write name="to" property="cancelledDate"/></td>
				                   		<td width="20%" height="25" class="row-even" align="center" ><bean:write name="to" property="oldSmartCardNo"/></td>
				                   		<td width="25%" height="25" class="row-even" align="left" ><bean:write name="to" property="remarks"/></td>
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
				</logic:notEmpty>
				
				<tr>
				<td height="10" valign="top" background="images/Tright_03_03.gif"></td>
				<td><div></div></td>
				<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				
				
				<tr>
					<td height="10" valign="top" background="images/Tright_03_03.gif">
					<div id="errorMessage"></div>
					</td>
					<td valign="top" class="news" height="10">
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
									<td height="1" colspan="2" class="heading" align="left">Smart Card Details
									</td>
								</tr>
								<tr>
								
									<td height="25" colspan="2" class="row-even">
									<div align="center">
									<html:radio property="cardType"	styleId="lost" value="Lost" ></html:radio>
									Lost Card&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<html:radio property="cardType" value="Correction" styleId="correction" ></html:radio>
									Correction&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<html:radio property="cardType" value="New" styleId="new" ></html:radio>
									New Card</div>
									</td>
								</tr>
								
								<tr>
									
									<td width="50%" height="25" class="row-odd">
									<div align="right"><bean:message key="knowledgepro.hostel.adminmessage.remarks"/>:</div>
									</td>
									<td width="50%" class="row-even">
									<html:textarea property="remarks" styleClass="TextBox" styleId="remarks" 
									cols="30" onkeypress="return imposeMaxLength(event,this)"></html:textarea>
							
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
							<td width="49%" height="35">
							<div align="right">
							<html:button property="" styleClass="formbutton" value="Submit"
										onclick="addScLostCorrection()"></html:button>
							</div>
							</td>
							<td width="2%"></td>
							<td width="49%">			
							<html:button property="" styleClass="formbutton" value="Cancel"
										onclick="goBack()"></html:button>
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