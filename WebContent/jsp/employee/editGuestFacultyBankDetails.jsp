<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<script type="text/javascript">
function searchGuestFaculty(){
	var guestId=document.getElementById("guestId").value;
	document.location.href = "GuestFaculty.do?method=getGuestFacultyInformation&guestId="+guestId;
}
function updateEditedDetails(){
	document.getElementById("method").value="saveEditedGuestFacultyBankDetails";
	document.GuestFacultyInfoForm.submit();
}
function resetTextFields(){
		document.getElementById("bankIfscCode").value=document.getElementById("originalIfscCode").value;
		document.getElementById("bankBranch").value=document.getElementById("originalBankBranch").value;
		document.getElementById("bankAccNo").value=document.getElementById("originalBankAccountNo").value;
		document.getElementById("panno").value=document.getElementById("originalPanNo").value;
	resetErrMsgs();
}
function caps(element)
{
element.value = element.value.toUpperCase();
}
function openURL(){
	var url = "http://www.ifsc4bank.com";
	myRef = window.open(url);
}
</script>

<html:form action="/GuestFaculty">

	<html:hidden property="method" styleId="method" value="" />
	<html:hidden property="formName" value="GuestFacultyInfoForm" />
	<html:hidden property="originalBankAccountNo" styleId="originalBankAccountNo"/>
	<html:hidden property="originalBankBranch" styleId="originalBankBranch"/>
	<html:hidden property="originalIfscCode" styleId="originalIfscCode"/>
	<html:hidden property="originalPanNo" styleId="originalPanNo"/>
	<html:hidden property="pageType" value="2" />

	<table width="100%" border="0">
		<tr>
			<td class="heading"><html:link href="AdminHome.do"
					styleClass="Bredcrumbs">
					<bean:message key="knowledgepro.admin.sec.EmployeeCategory" />
				</html:link> <span class="Bredcrumbs">&gt;&gt; <bean:message
						key="knowledgepro.employee.guestfaculty.bankdetails.edit" /> &gt;&gt;
			</span></td>
		</tr>
		<tr>
			<td><table width="100%" border="0" cellpadding="0"
					cellspacing="0">
					<tr>
						<td width="9"><img src="images/Tright_03_01.gif" width="9"
							height="29"></td>
						<td background="images/Tcenter.gif" class="body"><strong
							class="boxheader"><bean:message
									key="knowledgepro.employee.guestfaculty.bankdetails.edit" /></strong></td>
						<td width="10"><img src="images/Tright_1_01.gif" width="9"
							height="29"></td>
					</tr>
					<tr>
						<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
						<td valign="top" class="news">
							<div id="errorMessage">
								<FONT color="red"><html:errors /></FONT> <FONT color="green">
									<html:messages id="msg" property="messages" message="true">
										<c:out value="${msg}" escapeXml="false"></c:out>
										<br>
									</html:messages>
								</FONT>
							</div>
						</td>
						<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
					</tr>
					<tr>
						<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
						<td valign="top" class="news"><table width="100%" border="0"
								align="center" cellpadding="0" cellspacing="0">
								<tr>
									<td><img src="images/01.gif" width="5" height="5" /></td>
									<td width="914" background="images/02.gif"></td>
									<td><img src="images/03.gif" width="5" height="5" /></td>
								</tr>
								<tr>
									<td width="5" background="images/left.gif"></td>
									<td valign="top"><table width="100%" cellspacing="1"
											cellpadding="2">
											<tr>
												<td width="45%" height="25" class="row-odd"><div
														align="right">
														<bean:message key="knowledgepro.employee.guestfaculty" />
													</div></td>
												<td width="55%" class="row-even"><span class="star">
														<html:select property="guestId" styleClass="combo" styleId="guestId" name="GuestFacultyInfoForm"
												style="width:200px">
												<html:option value="">
													<bean:message key="knowledgepro.admin.select" />
												</html:option>
												<logic:notEmpty name="GuestFacultyInfoForm"
													property="guestFacultyMap">
													<html:optionsCollection property="guestFacultyMap"
														name="GuestFacultyInfoForm" label="value" value="key" />
												</logic:notEmpty>
											</html:select>
												</span></td>
											</tr>
										</table></td>
									<td width="5" height="30" background="images/right.gif"></td>
								</tr>
								<tr>
									<td height="5"><img src="images/04.gif" width="5"
										height="5" /></td>
									<td background="images/05.gif"></td>
									<td><img src="images/06.gif" /></td>
								</tr>
							</table></td>
						<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
					</tr>
					<tr>
						<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
						<td valign="top" class="news"><table width="100%" border="0"
								cellspacing="0" cellpadding="0">
								<tr>
									<td width="45%" height="35"><div align="center">
											<html:button styleClass="formbutton" value="Search" onclick="searchGuestFaculty()" property=""></html:button>
										</div></td>
								</tr>
							</table></td>
						<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
					</tr>
					<c:if test="${GuestFacultyInfoForm.guestFacultyTo!=null}">
					<tr>
						<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
						<td valign="top" class="news"><table width="100%" border="0"
								align="center" cellpadding="0" cellspacing="0">
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
											    <td width="25%" height="25" class="row-odd"><div
														align="right">
														<bean:message key="knowledgepro.employee.guestfaculty.name" />:
												</div></td>
												<td width="25%" height="25" class="row-odd"><div
														align="left">
														<c:out value="${GuestFacultyInfoForm.guestFacultyTo.name}"></c:out>
												</div></td>
												<td width="25%" height="25" class="row-odd"><div
														align="right">
														<bean:message key="admissionFormForm.mobile" />:
												</div></td>
												<td width="25%" height="25" class="row-odd"><div
														align="left">
														<c:out value="${GuestFacultyInfoForm.guestFacultyTo.mobile}"></c:out>
												</div></td>
											
											</tr>
											<tr>
											    <td width="25%" height="25" class="row-odd"><div
														align="right">
														<bean:message key="admissionForm.studentinfo.email.label" />
												</div></td>
												<td width="25%" height="25" class="row-odd"><div
														align="left">
														<c:out value="${GuestFacultyInfoForm.guestFacultyTo.email}"></c:out>
												</div></td>
												<td width="25%" height="25" class="row-odd"><div
														align="right">
														<bean:message key="admissionForm.parentinfo.address.label" />:
												</div></td>
												<td width="25%" height="25" class="row-odd"><div
														align="left">
														<c:out value="${GuestFacultyInfoForm.guestFacultyTo.communicationAddress}"></c:out>
												</div></td>
											</tr>
											
											<tr>
											    <td width="25%" height="25" class="row-even"><div
														align="right">
														<bean:message key="employee.info.personal.SNNO" />
												</div></td>
												<td width="25%" height="25" class="row-even"><div
														align="left">
														<input type="text" id="panno" name="panno" value="<c:out value="${GuestFacultyInfoForm.guestFacultyTo.panNo}"></c:out>">
												</div></td>
												<td width="25%" height="25" class="row-even"><div
														align="right">
														<bean:message key="knowledgepro.pettycash.accheads.bankAccNo" />:
												</div></td>
												<td width="25%" height="25" class="row-even"><div
														align="left">
														<input type="text" id="bankAccNo" name="bankAccNo" value="<c:out value="${GuestFacultyInfoForm.guestFacultyTo.bankAccNo}"></c:out>">
												</div></td>
											</tr>
											<tr>
											    <td width="25%" height="25" class="row-even"><div
														align="right">
														<bean:message key="knowledgepro.employee.bankbranch" />:
												</div></td>
												<td width="25%" height="25" class="row-even"><div
														align="left">
														<input type="text" id="bankBranch" name="bankBranch" value="<c:out value="${GuestFacultyInfoForm.guestFacultyTo.bankBranch}"></c:out>">
												</div></td>
												<td width="25%" height="25" class="row-even"><div
														align="right">
														<bean:message key="knowledgepro.employee.bank.ifsc" />:
												</div></td>
												<td width="25%" height="25" class="row-even"><div
														align="left">
														<input type="text" id="bankIfscCode" name="bankIfscCode" onkeyup="caps(this)" value="<c:out value="${GuestFacultyInfoForm.guestFacultyTo.bankIfscCode}" ></c:out>">
												<img align="top" src="images/questionMark.jpg" style="cursor:pointer" width="20" height="20"
								 onclick="openURL()" title="Click here for the IFSC code list" />
												</div></td>
											</tr>
											<tr>
												<td height="25" colspan="2">
												<div align="right">
												<html:button styleClass="formbutton" value="Update" onclick="updateEditedDetails()" property=""></html:button>
												</div>
												</td>
												<td height="25" colspan="2">
												<div align="left">
												<html:button styleClass="formbutton" value="Reset" onclick="resetTextFields()" property=""></html:button>
												</div>
												</td>
											</tr>
										</table>
									</td>
									<td width="5" height="30" background="images/right.gif"></td>
								</tr>
								<tr>
									<td height="5"><img src="images/04.gif" width="5"
										height="5" /></td>
									<td background="images/05.gif"></td>
									<td><img src="images/06.gif" /></td>
								</tr>
							</table></td>
						<td width="10" valign="top" background="images/Tright_3_3.gif"
							class="news"></td>
					</tr></c:if>
					<tr>
						<td height="10" valign="top" background="images/Tright_03_03.gif"></td>
						<td valign="top" class="news">&nbsp;</td>
						<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
					</tr>
					<tr>
						<td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
						<td width="100%" background="images/TcenterD.gif"></td>
						<td><img src="images/Tright_02.gif" width="9" height="29"></td>
					</tr>
				</table></td>
		</tr>
	</table>

</html:form>