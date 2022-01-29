<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<script type="text/javascript">
	function saveOrganizationDetails() {
		document.getElementById("method").value = "saveOrganizationDetails";
		document.organizationForm.submit();
	}
	function deleteOrganization(id) {
		deleteConfirm = confirm("Are you sure want to delete this entry?")
		if (deleteConfirm) {
			document.location.href = "Organization.do?method=deleteOrganizationDetails&id="
					+ id;
		}
	}
	function editOrganization(id) {
			document.location.href = "Organization.do?method=editOrganizationDetails&id="
					+ id;
	}
	function updateOrganizationDetails(id) {
		document.getElementById("method").value = "updateOrganizationDetails";
		document.organizationForm.submit();
	}
	function downloadLogo(id, value) {
		document.location.href = "organizationDownloadAction.do?id=" + id
				+ "&value=" + value;
	}
	function downloadTopBar(id, value) {
		document.location.href = "organizationDownloadAction.do?id=" + id
				+ "&value=" + value;
	}
	function disableChangePassword() {
			document.getElementById("changePassword1").checked = false;
			document.getElementById("changePassword2").checked = true;
			document.getElementById("changePassword1").disabled = true;
			document.getElementById("changePassword2").disabled = true;
	}	
	function enableChangePassword() {
			document.getElementById("changePassword1").disabled = false;
			document.getElementById("changePassword2").disabled = false;
			document.getElementById("changePassword1").checked = true;
			document.getElementById("changePassword2").checked = false;
	}
	function onLoadDisable() {
		var same1= document.getElementById("sameUsertidPassword1").checked;
		var same2= document.getElementById("sameUsertidPassword2").checked;
		if(same1 == true && same2 == false){
			disableChangePassword();
		}
		if(same1 == false && same2 == true){
			enableChangePassword();
		}	
	}
	function resetFields() {
		document.location.href = "Organization.do?method=initOrganizationDetails";
	}	
</script>
<html:form action="/Organization" enctype="multipart/form-data"
	method="post">
	<html:hidden property="method" styleId="method" />
	<html:hidden property="formName" value="organizationForm" />
	<html:hidden property="pageType" value="1" />
	<table width="100%" border="0">
		<tr>
			<td><span class="heading"><bean:message
				key="knowledgepro.admin" /> <span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.admin.organization" /> &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td valign="top">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> <bean:message
						key="knowledgepro.admin.organization" /></strong></td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="20" valign="top" background="images/Tright_03_03.gif"></td>
					<td height="20" class="news">
					<div align="right" class="mandatoryfield"></div>
					</td>
					<td valign="top" background="images/Tright_3_3.gif"></td>
				</tr>
				<tr>
					<td height="41" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<div align="right"><span class='MandatoryMark'><bean:message
					key="knowledgepro.mandatoryfields" /></span></div>
					<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
					<FONT color="green"><html:messages id="msg"
						property="messages" message="true">
						<c:out value="${msg}" escapeXml="false"></c:out>
						<br>
					</html:messages> </FONT></div>

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
									<td width="21%" height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message
										key="knowledgepro.admin.organizationdetails.organizationname.colon" /></div>
									</td>
									<td height="25" class="row-even"><html:text
										property="organizationName" styleClass="TextBox" size="20"
										maxlength="100"></html:text></td>
									<td height="25" width="30%" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message
										key="knowledgepro.admin.organizationdetails.approval" /></div>
									</td>
									<td height="25" width="27%" class="row-even"><html:radio
										property="needApproval" styleId="need1" value="true">
										<bean:message key="knowledgepro.yes" />
									</html:radio> <html:radio property="needApproval" styleId="need2"
										value="false">
										<bean:message key="knowledgepro.no" />
									</html:radio></td>
								</tr>
								
								<tr>
									<td height="25" class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.admin.organizationdetails.organizationlogo.colon" /></div>
									</td>
									<td width="29%" height="25" class="row-even"><nested:file
										property="organizationLogo" styleClass="TextBox"></nested:file></td>
									<td width="23%" class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.admin.organizationdetails.topbar.colon" /></div>
									</td>
									<td width="27%" class="row-even"><nested:file
										property="organizationtopBar" styleClass="TextBox">
									</nested:file></td>
								</tr>
								<tr>
									<td height="25" class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.admin.organizationdetails.organizationlogo.colon" />1</div>
									</td>
									<td width="29%" height="25" class="row-even"><nested:file
										property="organizationLogo1" styleClass="TextBox"></nested:file></td>
									<td width="23%" class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.admin.organizationdetails.topbar.colon" />1</div>
									</td>
									<td width="27%" class="row-even"><nested:file
										property="organizationtopBar1" styleClass="TextBox">
									</nested:file></td>
								</tr>
								<tr>
									<td height="25" width="30%" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>&nbsp; Final Merit List Approval: </div>
									</td>
									<td height="25" width="27%" class="row-even"><html:radio
										property="finalMeritListApproval" styleId="final1" value="true">
										Enable
									</html:radio> <html:radio property="finalMeritListApproval" styleId="final2"
										value="false">
										Disable
									</html:radio></td>
									<td class="row-odd" align="right">  Time Limit For Automatic Attendance Entry:</td>
									<td class="row-even"> <html:text property="timeLimit" styleClass="TextBox" size="10" maxlength="2" onkeypress="return isNumberKey(event)"></html:text> (Minutes)</td>
								</tr>
								
								<tr>
									<td height="25" width="30%" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>&nbsp; Open Honours Course Application: </div>
									</td>
									<td height="25" width="27%" class="row-even"><html:radio
										property="openHonoursCourseLink" styleId="hono1" value="true">
										<bean:message key="knowledgepro.yes" />
									</html:radio> <html:radio property="openHonoursCourseLink" styleId="hono2"
										value="false">
										<bean:message key="knowledgepro.no" />
									</html:radio></td>
									<td height="25" width="30%" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>&nbsp; Convocation Registration: </div>
									</td>
									<td height="25" width="27%" class="row-even"><html:radio
										property="convocationRegistration" styleId="convocation1" value="true">
										Enable
									</html:radio> <html:radio property="convocationRegistration" styleId="convocation2"
										value="false">
										Disable
									</html:radio></td>
								</tr>
								<tr>
									<td height="25" width="30%" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>&nbsp; Student Photo Upload: </div>
									</td>
									<td height="25" width="27%" class="row-even" colspan="3"><html:radio
										property="studentPhotoUpload" styleId="studentPhotoUpload1" value="true">
										Enable
									</html:radio> <html:radio property="studentPhotoUpload" styleId="studentPhotoUpload2"
										value="false">
										Disable
									</html:radio></td>
								</tr>
							</table>
							</td>
							<td width="5" height="29" background="images/right.gif"></td>
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
					<td height="20" valign="top" background="images/Tright_03_03.gif"></td>
					<td height="15" class="heading"><bean:message
						key="admissionForm.parentinfo.address.label" /></td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" align="center" cellpadding="0"
						cellspacing="0">
						<tr>
							<td><img src="images/01.gif" width="5" height="5"></td>
							<td width="914" background="images/02.gif"></td>
							<td><img src="images/03.gif" width="5" height="5"></td>
						</tr>
						<tr>
							<td width="5" background="images/left.gif"></td>
							<td valign="top">
							<table width="100%" border="0" cellpadding="0" cellspacing="1">
								<tr class="row-white">
									<td width="13%" height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message
										key="admissionForm.studentinfo.addrs1.label" /></div>
									</td>
									<td width="18%" height="25" class="row-even"><html:text
										property="addressLine1" styleClass="TextBox"
										styleId="addressLine1" size="16" maxlength="50"></html:text></td>
									<td width="13%" height="25" class="row-odd">
									<div align="right"><bean:message
										key="admissionForm.studentinfo.addrs2.label" /></div>
									</td>
									<td width="18%" height="25" class="row-even"><html:text
										property="addressLine2" styleClass="TextBox"
										styleId="addressLine2" size="16" maxlength="50"></html:text></td>
									<td width="15%" class="row-odd">
									<div align="right"><bean:message
										key="admissionForm.studentinfo.addrs3.label" /></div>
									</td>
									<td width="23%" class="row-even"><html:text
										property="addressLine3" styleClass="TextBox"
										styleId="addressLine3" size="16" maxlength="50"></html:text></td>
								</tr>
							</table>
							</td>
							<td background="images/right.gif" width="5" height="29"></td>
						</tr>
						<tr>
							<td height="5"><img src="images/04.gif" width="5" height="5"></td>
							<td background="images/05.gif"></td>
							<td><img src="images/06.gif"></td>
						</tr>
					</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				
				<tr>
					<td height="20" valign="top" background="images/Tright_03_03.gif"></td>
					<td height="15" class="heading"><bean:message key="knowledgepro.admin.organizationdetails.userconfiguration"/> </td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" align="center" cellpadding="0"
						cellspacing="0">
						<tr>
							<td><img src="images/01.gif" width="5" height="5"></td>
							<td width="914" background="images/02.gif"></td>
							<td><img src="images/03.gif" width="5" height="5"></td>
						</tr>
						<tr>
							<td width="5" background="images/left.gif"></td>
							<td valign="top">
							<table width="100%" border="0" cellpadding="0" cellspacing="1">
								<tr class="row-white">
									<td width="35%" height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>&nbsp;
									<bean:message key="knowledgepro.admin.organizationdetails.sameuserid"/> </div>
									</td>
									<td width="15%" height="25" class="row-even">
									
									<html:radio
										property="sameUseridPassword" styleId="sameUsertidPassword1" value="true" onclick="disableChangePassword()">
										<bean:message key="knowledgepro.yes"/>
									</html:radio> <html:radio property="sameUseridPassword" styleId="sameUsertidPassword2"
										value="false" onclick="enableChangePassword()">
										<bean:message key="knowledgepro.no" />
									</html:radio>									
									</td>
									<td width="35%" height="25" class="row-odd">
									<div align="right"><bean:message key="knowledgepro.admin.organizationdetails.chengepassword" /></div>
									</td>
									<td width="15%" height="25" class="row-even">
									<html:radio
										property="changePassword" styleId="changePassword1" value="true">
										<bean:message key="knowledgepro.yes" />
									</html:radio> <html:radio property="changePassword" styleId="changePassword2"
										value="false">
										<bean:message key="knowledgepro.no" />
									</html:radio>
									</td>
								</tr>
							</table>
							</td>
							<td background="images/right.gif" width="5" height="29"></td>
						</tr>
						<tr>
							<td height="5"><img src="images/04.gif" width="5" height="5"></td>
							<td background="images/05.gif"></td>
							<td><img src="images/06.gif"></td>
						</tr>
					</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td height="35" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>						
							<td width="45%" height="35">
							<div align="right">
							<c:choose>
							<c:when test="${operation == 'edit'}">							
							<html:button property=""
								styleClass="formbutton" value="Update"
								onclick="updateOrganizationDetails()"></html:button>
								</c:when>
								<c:otherwise>
								<html:button property=""
								styleClass="formbutton" value="Submit"
								onclick="saveOrganizationDetails()"></html:button>
								</c:otherwise>
								</c:choose>
								</div>
							</td>
							<td width="2%"></td>
							<td width="53%">
							<c:choose>
							<c:when test="${operation == 'edit'}">		
							<html:cancel value="Reset" styleClass="formbutton"></html:cancel>
								</c:when>
								<c:otherwise>
								<html:button property=""
								styleClass="formbutton" value="Reset"
								onclick="resetFields()"></html:button>
								</c:otherwise>
								</c:choose>
								</td>
								</tr>
					</table>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
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
									<td height="25" class="row-odd">
									<div align="center"><bean:message key="knowledgepro.slno" /></div>
									</td>
									<td align="center" height="25" class="row-odd"><bean:message
										key="knowledgepro.admin.organizationdetails.organizationname" /></td>
									<td align="center" class="row-odd"><bean:message
										key="knowledgepro.admin.organizationdetails.organizationlogo" /></td>
									<td class="row-odd">
									<div align="center"><bean:message
										key="knowledgepro.admin.organizationdetails.topbar" /></div>
									</td>
									<td align="center" class="row-odd"><bean:message
										key="knowledgepro.admin.organizationdetails.organizationlogo" />1</td>
									<td class="row-odd">
									<div align="center"><bean:message
										key="knowledgepro.admin.organizationdetails.topbar" />1</div>
									</td>
									<td align="center" class="row-odd"><bean:message
										key="admissionForm.parentinfo.address.label" /></td>
									<td align="center" class="row-odd"><bean:message
									key="knowledgepro.admin.organizationdetails.needapproval" /></td>
									<td align="center" class="row-odd"><bean:message
									key="knowledgepro.admin.organizationdetails.uid&password" /></td>
									<td align="center" class="row-odd"><bean:message
									key="knowledgepro.admin.organizationdetails.chengepassword" /></td>
									<td align="center" class="row-odd">Final Merit List Approval</td>
									<td class="row-odd">
									<div align="center"><bean:message
										key="knowledgepro.edit" /></div>
									</td>
								</tr>
								<logic:notEmpty name="organizationForm"
									property="organizationList">
									<nested:iterate id="org" name="organizationForm"
										property="organizationList"
										type="com.kp.cms.to.admin.OrganizationTO" indexId="count">
												<tr>
													<td width="5%" height="25" class="row-even">
													<div align="center"><c:out value="${count + 1}" /></div>
													</td>
													<td align="center" width="20%" height="25" class="row-even"><nested:write
														name="org" property="organizationName" /></td>
													<td align="center" width="21%" class="row-even"><nested:write
														name="org" property="organizationLogoName" /> <nested:equal
														value="true" property="logoPresent">
														<a href="#"
															onclick="downloadLogo('<bean:write name="org" property="id"/>', '1')"><bean:message
															key="knowledgepro.view" /></a>
													</nested:equal></td>
													<td align="center" width="25%" height="25" class="row-even">
													<div align="center"></div>
													<nested:write name="org" property="organizationTopBarName" />
													<nested:equal value="true" property="topbarPresent">
														<a href="#"
															onclick="downloadTopBar('<bean:write name="org" property="id"/>', '2')"><bean:message
															key="knowledgepro.view" /></a>
													</nested:equal></td>
													<td align="center" width="21%" class="row-even"><nested:write
														name="org" property="organizationLogoName1" /> <nested:equal
														value="true" property="logoPresent1">
														<a href="#"
															onclick="downloadLogo('<bean:write name="org" property="id"/>', '3')"><bean:message
															key="knowledgepro.view" /></a>
													</nested:equal></td>
													<td align="center" width="25%" height="25" class="row-even">
													<div align="center"></div>
													<nested:write name="org" property="organizationTopBarName1" />
													<nested:equal value="true" property="topbarPresent1">
														<a href="#"
															onclick="downloadTopBar('<bean:write name="org" property="id"/>', '4')"><bean:message
															key="knowledgepro.view" /></a>
													</nested:equal></td>
													<td align="center" width="22%" class="row-even"><nested:write
														name="org" property="addressLine1" /><nested:write
														name="org" property="addressLine2" /><nested:write
														name="org" property="addressLine3" /></td>
														<td align="center" width="22%" class="row-even"><nested:write
														name="org" property="needFinalApprival" /></td>
														<td align="center" width="22%" class="row-even"><nested:write
														name="org" property="useridPassword" /></td>
														<td align="center" width="22%" class="row-even"><nested:write
														name="org" property="enableChangePassword" /></td>
														<td align="center" width="22%" class="row-even"><nested:write
														name="org" property="dispFinalMeritApproval" /></td>
													<td width="7%" height="25" class="row-even">
													<div align="center"><img src="images/edit_icon.gif"
														width="16" height="16" style="cursor: pointer"
														onclick="editOrganization('<bean:write name="org" property="id"/>')"></div>
													</td>
												</tr>
									</nested:iterate>
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
<script type="text/javascript">
onLoadDisable();
</script>