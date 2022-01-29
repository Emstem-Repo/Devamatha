<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<script language="javascript" type="text/javascript"
	src="js/datetimepicker.js"></script>
<script language="javascript" type="text/javascript"
	src="js/calendar.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<script language="JavaScript" src="js/admission/admissionform.js"></script>
<script language="javaScript" type="text/javascript">
	function addOfflineDetails() {
		document.getElementById("method").value = "addOfflineDetails";
		document.offlineDetailsForm.submit();
	}
	function updateOfflineDetails(){
		document.getElementById("method").value = "updateOfflineDetails";
		document.offlineDetailsForm.submit();
	}
	function deleteOfflineDetails(id)
	{
		deleteConfirm = confirm("Are you sure want to delete this entry?")
		if (deleteConfirm == true) {
			document.location.href = "OfflineDetails.do?method=deleteOfflineDetails&id="
					+ id;
		}
	}	
		function editOfflineDetails(id)
		{
		document.location.href = "OfflineDetails.do?method=editOfflineDetails&id="+ id;			
		}
		function resetFields(){
			document.getElementById("course").selectedIndex = 0;
			document.getElementById("appno").value = "";
			document.getElementById("amount").value = "";
			document.getElementById("date").value = "";
			document.getElementById("year").value = resetYear();
			resetErrMsgs();
		}
</script>
<html:form action="/OfflineDetails" method="post">
	<html:hidden property="method" styleId="method"/>
	<html:hidden property="formName" value="offlineDetailsForm" />
	<html:hidden property="id" />
	<html:hidden property="pageType" value="1" />
	<table width="100%" border="0">
		<tr>
			<td><span class="heading"><bean:message
				key="knowledgepro.admission" /><span class="Bredcrumbs">&gt;&gt;<bean:message
				key="knowledgepro.admission.offlinedetails" /> &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="99%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body">
					<div align="left"><strong class="boxheader"><bean:message
						key="knowledgepro.admission.offlinedetails" /></strong></div>
					</td>
					<td width="16"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="122" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" cellspacing="1" cellpadding="2">
						<tr bgcolor="#FFFFFF">
							<td height="20" colspan="6">
							<div align="right"><span class='MandatoryMark'> <bean:message
							key="knowledgepro.mandatoryfields" /></span></div>
							<div id="errorMessage"><FONT color="green"><html:errors /></FONT>
							<FONT color="green"><html:messages id="msg"
								property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
								<br>
							</html:messages> </FONT></div>
							</td>
						</tr>
						<tr>
							<td width="17%" class="row-odd">
							<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message
								key="knowledgepro.admin.course.with.col" /></div>
							</td>
							<td width="15%" class="row-even"><input type="hidden"
								name="cId" id="cId"
								value='<bean:write name="offlineDetailsForm" property="courseId"/>' />
							<html:select property="courseId" styleClass="comboLarge"
								styleId="course">
								<html:option value=""><bean:message
								key="knowledgepro.admin.select" /></html:option>
								<logic:notEmpty name="offlineDetailsForm" property="courseList">
								<html:optionsCollection name="offlineDetailsForm"
									property="courseList" label="name" value="id" />
									</logic:notEmpty>
							</html:select>
							</td>
							<td height="25" class="row-odd">
							<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message
								key="knowledgepro.interview.ApplicationNo.col" /></div>
							</td>
							<td height="25" class="row-even"><label> <html:text
								property="applicationNo" size="9" maxlength="9"
								styleId="appno"></html:text> </label></td>
							<td height="25" class="row-odd">
							<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message
								key="knowledgepro.admission.receiptno.col" /></div>
							</td>
							<td height="25" class="row-even"><label> <html:text
								property="receiptNo" readonly="true" size="10" maxlength="10">
								<logic:notEmpty name="offlineDetailsForm" property="receiptNo">
									<bean:write name="offlineDetailsForm" property="receiptNo" />
								</logic:notEmpty>
							</html:text> </label></td>
						</tr>
						<tr>
							<td class="row-odd">
							<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message
								key="knowledgepro.fee.academicyear.col" /></div>
							</td>
							<td class="row-even"><span class="star"> <input
								type="hidden" id="yr" name="yr"
								value='<bean:write name="offlineDetailsForm" property="academicYear"/>' />
							<html:select property="academicYear" styleClass="combo"
								styleId="year">
								<html:option value="">
								<bean:message key="knowledgepro.admin.select" />
								</html:option>
						<cms:renderAcademicYearForAdmission></cms:renderAcademicYearForAdmission>
							</html:select> </span></td>
							<td height="25" class="row-odd">
							<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message
								key="knowledgepro.admission.amt.col" /></div>
							</td>
							<td height="25" class="row-even"><html:text
								property="amount" size="8" maxlength="8" styleId="amount"></html:text></td>							
							<td height="25" class="row-odd">
							<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message
								key="knowlegepro.admission.date.col" /></div>
							</td>
							<td class="row-even"><html:text property="date"
								readonly="true" size="10" maxlength="10" styleId="date"></html:text>
							<script language="JavaScript">
						new tcal ({
					// form name
					'formname': 'offlineDetailsForm',
					// input name
					'controlname': 'date'
					});
					</script></td>
						</tr>
						<tr>
							<td height="25" colspan="6">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td width="48%" height="35">
									<div align="right"><c:choose>
										<c:when test="${operation == 'edit'}">
											<html:button property="" styleClass="formbutton"
												value="Update" onclick="updateOfflineDetails()"></html:button>
										</c:when>
										<c:otherwise>
											<html:button property="" styleClass="formbutton"
												value="Submit" onclick="addOfflineDetails()"></html:button>
										</c:otherwise>
									</c:choose></div>
									</td>
									<td width="1%"></td>
									<td width="49%"><c:choose>
										<c:when test="${operation == 'edit'}">
											<html:cancel value="Reset" styleClass="formbutton"></html:cancel>
										</c:when>
										<c:otherwise>
											<html:button property="" styleClass="formbutton"
												value="Reset" onclick="resetFields()"></html:button>
										</c:otherwise>
									</c:choose></td>
								</tr>
							</table>
						</tr>
					</table>
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
									<td height="25" class="row-odd">
									<div align="center"><bean:message key="knowledgepro.slno" /></div>
									</td>
									<td height="25" class="row-odd">
									<div align="center"><bean:message
										key="knowledgepro.admin.course" /></div>
									</td>
									<td height="25" align="center" class="row-odd"><bean:message
										key="knowledgepro.interview.ApplicationNo" /></td>
									<td height="25" align="center" class="row-odd"><bean:message
										key="knowledgepro.admission.receiptno" /></td>
									<td align="center" class="row-odd"><bean:message
										key="knowledgepro.fee.academicyear" /></td>
									<td height="25" align="center" class="row-odd"><bean:message
										key="knowledgepro.admission.amt" /></td>
									<td height="25" align="center" class="row-odd"><bean:message
										key="knowlegepro.admission.date" /></td>
									<td class="row-odd">
									<div align="center"><bean:message key="knowledgepro.edit" /></div>
									</td>
									<td class="row-odd">
									<div align="center"><bean:message
										key="knowledgepro.delete" /></div>
									</td>
								</tr>
								<c:set var="temp" value="0" />
								<logic:notEmpty name="offlineDetailsForm"
									property="offlineDetailsList">
									<nested:iterate id="off" name="offlineDetailsForm"
										property="offlineDetailsList" indexId="count">
										<bean:define id="year1" property="academicYear" name="off"
											type="java.lang.Integer"></bean:define>
										<% year1= year1.intValue(); %>
										<c:choose>
											<c:when test="${temp == 0}">
												<tr>
													<td width="6%" height="25" class="row-even">
													<div align="center"><c:out value="${count + 1}" /></div>
													</td>
													<td align="center" class="row-even"><nested:write
														name="off" property="courseName" /></td>
													<td align="center" class="row-even"><nested:write
														name="off" property="applicationNo" /></td>
													<td align="center" class="row-even"><nested:write
														name="off" property="receiptNo" /></td>
													<td align="center" class="row-even"><nested:write
														name="off" property="academicYear" />-<%=year1+1 %></td>
													<td align="center" class="row-even"><nested:write
														name="off" property="amount" /></td>
													<td align="center" class="row-even"><nested:write
														name="off" property="date" /></td>
													<td height="25" class="row-even">
													<div align="center"><img src="images/edit_icon.gif"
														width="16" height="18" style="cursor: pointer"
														onclick="editOfflineDetails('<bean:write name="off" property="id"/>')" /></div>
													</td>
													<td width="6%" class="row-even">
													<div align="center">
													<div align="center"><img src="images/delete_icon.gif"
														width="16" height="16" style="cursor: pointer"
														onclick="deleteOfflineDetails('<bean:write name="off" property="id"/>')" /></div>
													</div>
													</td>
												</tr>
												<c:set var="temp" value="1" />
											</c:when>
											<c:otherwise>
												<tr>
													<td height="25" class="row-white">
													<div align="center"><c:out value="${count + 1}" /></div>
													</td>
													<td align="center" class="row-white"><nested:write
														name="off" property="courseName" /></td>
													<td align="center" class="row-white"><nested:write
														name="off" property="applicationNo" /></td>
													<td align="center" class="row-white"><nested:write
														name="off" property="receiptNo" /></td>
													<td align="center" class="row-white"><nested:write
														name="off" property="academicYear" />-<%=year1+1 %></td>
													<td align="center" class="row-white"><nested:write
														name="off" property="amount" /></td>
													<td align="center" class="row-white"><nested:write
														name="off" property="date" /></td>
													<td align="center" height="25" class="row-white">
													<div align="center"><img src="images/edit_icon.gif"
														width="16" height="18" style="cursor: pointer"
														onclick="editOfflineDetails('<bean:write name="off" property="id"/>')" /></div>
													</td>
													<td align="center" width="6%" class="row-white">
													<div align="center">
													<div align="center"><img src="images/delete_icon.gif"
														width="16" height="16" style="cursor: pointer"
														onclick="deleteOfflineDetails('<bean:write name="off" property="id"/>')" /></div>
													</div>
													</td>
												</tr>
												<c:set var="temp" value="0" />
											</c:otherwise>
										</c:choose>
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
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				<tr>
					<td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
					<td width="100%" background="images/TcenterD.gif"></td>
					<td><img src="images/Tright_02.gif" width="9" height="29"></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>

</html:form>

<script type="text/javascript">
	var courseId = document.getElementById("cId").value;
	if (courseId.length != 0) {
		document.getElementById("course").value = courseId;
	}
	var yearId = document.getElementById("yr").value;
	if (yearId != null && yearId.length != 0) {
		document.getElementById("year").value = yearId;
	}
</script>