<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<script language="JavaScript" src="js/calendar.js"></script>

<script type="text/javascript">
function getRoomTypes(hostelId){
	var args = "method=getRoomTypeByHostelBYstudent&hostelId="+hostelId;
	var url ="AjaxRequest.do";
	requestOperation(url,args,updateRoomType);
}
function updateRoomType(req) {
	updateOptionsFromMap(req, "roomTypeName", "- Select -");
}
function Cancel(){
	document.location.href = "LoginAction.do?method=initLoginAction";
}
</script>
<html:form action="/hostelWaitingListView" method="post">
<html:hidden property="formName" value="hlAdmissionForm" />
<html:hidden property="method" value="searchStudentsInHostel"/>
<html:hidden property="pageType" value="3"/>

<table width="100%" border="0">
		<tr>
			<td><span class="Bredcrumbs"> <bean:message key="knowledgepro.hostel.adminmessage.hostel" />
			 <span class="Bredcrumbs">&gt;&gt;<bean:message key="knowledgepro.hostel.waitinglistview" /> &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong class="boxheader"> <bean:message key="knowledgepro.hostel.waitinglistview" /></strong></td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9" 	height="29"></td>
				</tr>
				<tr>
					<td height="122" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" cellspacing="1" cellpadding="2">
						<tr bgcolor="#FFFFFF">
							<td height="20" colspan="2">
							<div align="right"><FONT color="red"> <span
								class='MandatoryMark'><bean:message
								key="knowledgepro.mandatoryfields" /></span></FONT></div>
							<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
							<FONT color="green" size="2"> <html:messages id="msg"
								property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
								<br>
							</html:messages> </FONT></div>
							</td>
						</tr>


						<tr>
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
									<td valign="top" class="news">
									<table width="100%" cellspacing="1" cellpadding="2">
                                 
										<tr>
											 <td width="16%" height="25" class="row-odd" >
									            <div align="right"><span class="Mandatory">*</span>
										           <bean:message key="knowledgepro.petticash.academicYear"/>:</div>
									            </td>
									         <td width="17%" height="25" class="row-even" >
									        <input type="hidden" id="tempyear" name="appliedYear"
										     value="<bean:write name="hlAdmissionForm" property="year"/>" />
									       <html:select property="year" styleId="year" styleClass="combo" >
										<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
										<cms:renderAcademicYearForAdmission></cms:renderAcademicYearForAdmission>
									</html:select></td>
									<td width="16%" height="25" class="row-odd">
											<div align="right"><span class="Mandatory">*</span><bean:message
												key="knowledgepro.hostel.hostel.entry.name" /></div>
											</td>
											<td width="16%" height="25" class="row-even"><span
												class="star"> <input type="hidden" id="temphostelName" name="appliedYear"
										     value="<bean:write name="hlAdmissionForm" property="hostelId"/>" /> 
										     <html:select property="hostelName"
												styleClass="comboLarge" styleId="hostelName" onchange="getRoomTypes(this.value)">
												<html:option value="">
													<bean:message key="knowledgepro.admin.select" />
												</html:option>
											<c:if test="${hostelmap != null}">
												<html:optionsCollection name="hostelmap" label="value" value="key" />
							                   </c:if>
											</html:select></span></td>
									<td width="16%" height="25" class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.roomtype" /></div>
											</td>
											<td width="18%" height="25" class="row-even"><span
												class="star"> <input type="hidden" id="temproomTypeName" 
										     value="<bean:write name="hlAdmissionForm" property="hostelroomTypeId"/>" /> 
										     <html:select property="roomTypeName"
												styleClass="comboLarge" styleId="roomTypeName" onchange="getNumberOfSeats()">
												<html:option value="">
													<bean:message key="knowledgepro.admin.select" />
												</html:option>
												<c:if test="${roomTypeMap != null}">
												<html:optionsCollection name="roomTypeMap" label="value" value="key" />
							                   </c:if>
											</html:select></span></td>
										</tr>
									</table>
									</td>
									<td width="5" height="29" background="images/right.gif"></td>
								</tr>
								<tr>
									<td height="5"><img src="images/04.gif" width="5"
										height="5" /></td>
									<td background="images/05.gif"></td>
									<td><img src="images/06.gif" /></td>
								</tr>
							</table>
							</td>
							<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
						</tr>
						      <tr>
							<td height="25">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td height="35" colspan="6" align="center">
									<div align="center">
											<html:submit property="" styleClass="formbutton">
												<bean:message key="knowledgepro.admin.search" /></html:submit>&nbsp;&nbsp;&nbsp;&nbsp;
												<html:button property="" styleClass="formbutton"  onclick="Cancel()">
												<bean:message key="knowledgepro.cancel" /></html:button>
												
										
									</div>
									</td>
								</tr>
							</table>
							</td>
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
<script type="text/javascript">
	
</script>