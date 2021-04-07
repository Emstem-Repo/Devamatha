<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<script type="text/javascript">
function cancelButton() {
	document.location.href = "HostelStudentInfo.do?method=initHostelStudentInfo";
	}
function imposeMaxLengthName(evt, Object) {
	var keynum = (evt.which) ? evt.which : event.keyCode;
	if (keynum == 8 || keynum == 37 || keynum == 39 || keynum == 9 || keynum == 46) {
		return true;
	}
	var MaxLen = 100;
	return (Object.value.length < MaxLen);
}
</script>
<html:form action="/HostelStudentInfo">
	<html:hidden property="formName" value="hostelStudentDetailsForm" />
	<html:hidden property="pageType" value="1" />
	<html:hidden property="method" styleId="method" value="getSearchedHostelStudents" />
	<table width="100%" border="0">
		<tr>
			<td><span class="heading"><bean:message
				key="knowledgepro.hostel" /> <span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.admission.studentDetailView" />&gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> <bean:message
						key="knowledgepro.admission.studentDetailView" /></strong></td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td height="20" class="news">
					<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
					<FONT color="green"> <html:messages id="msg"
						property="messages" message="true">
						<c:out value="${msg}" escapeXml="false"></c:out>
						<br>
					</html:messages> </FONT></div>
					<div align="right" class="mandatoryfield"><bean:message
						key="knowledgepro.mandatoryfields" /></div>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
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
								 <td width="25%" height="25" class="row-odd" >
									            <div align="right">
										           <span class="Mandatory">*</span>&nbsp;<bean:message key="knowledgepro.petticash.academicYear"/>:</div>
									            </td>
								   <td width="25%" height="25" class="row-even" >
									        <input type="hidden" id="tempyear" name="appliedYear"
										     value="<bean:write name="hostelStudentDetailsForm" property="year"/>" />
									       <html:select property="year" styleId="year" styleClass="combo" >
											<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
											<cms:renderAcademicYearForAdmission></cms:renderAcademicYearForAdmission>
										   </html:select>
								  </td>
										<td width="25%" height="25" class="row-odd">
													<div align="right">
														<span class="Mandatory">*</span>&nbsp;<bean:message key="knowledgepro.hostel.name.col"/>
													</div>
										</td>
										<td width="25%" height="25" class="row-even">
													<div align="left" width="30%">
														<html:select property="hostelId" styleId="hostelId" name="hostelStudentDetailsForm">
															<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>																													
															<html:optionsCollection name="hostelList" label="name" value="id" />																													
														</html:select>
													</div>
									</td> 
										   
										   
								</tr>
								<tr>
									<td width="25%" height="25" class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.admission.disciplinaryDetails.regRollNo" /></div>
									</td>
									<td width="25%" height="25" class="row-even"><html:text
										property="regNoOrRollNo" styleId="regNoOrRollNo" maxlength="50"
										 size="20" /></td>
										
									 <td width="25%" height="25" class="row-odd">
									 <div align="right"><bean:message key="knowledgepro.admin.name"/>:</div></td>
                   					 <td width="25%" height="25" class="row-even">
                   					 <html:text property="studentName" styleId="studentName" onkeypress="return imposeMaxLengthName(event,this)"/></td>
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
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="48%" height="35">
							<div align="right"><input type="submit" class="formbutton"
								value="Search"  /></div>
							</td>
							<td width="2%"></td>
							<td width="50%" align="left"><input type="Reset" class="formbutton"
								value="Reset" onclick="cancelButton()"/></td>
						</tr>
					</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>

				<tr>
					<td height="10" valign="top" background="images/Tright_03_03.gif"></td>
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
y	var yearId = document.getElementById("tempyear").value;
	if (yearId != null && yearId.length != 0) {
		document.getElementById("year").value = yearId;
	}
</script>