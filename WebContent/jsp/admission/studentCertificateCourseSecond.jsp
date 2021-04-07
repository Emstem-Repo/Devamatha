<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>

<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<script type="text/javascript" src="js/AC_RunActiveContent.js"></script>
<script type="text/javascript" src="js/sdmenu/sdmenu.js"></script>
<script type="text/javascript" src="js/leftSwitchMenu.js"></script>
<script>

	function showMandatoryCourse(id) {
		document.getElementById("mandatory").style.display = "block";
	}

	function hideMandatoryCourse(id) {
		if (document.getElementById(id).checked) {
			document.getElementById("mandatory").style.display = "none";
		} else {
			document.getElementById("mandatory").style.display = "block";
		}
	}
	function showOptionalCourse(id) {
		document.getElementById("optionalId").style.display = "block";
	}

	function hideOptionalCourse(id) {
		var len = parseInt(document.getElementById("mandatoryLen").value);

		if (document.getElementById(id).checked) {
			for ( var count = 0; count <= len - 1; count++) {
				var styleId = "mandatory_check_" + count;
				if (id != styleId) {
					document.getElementById(styleId).disabled = true;
				}
			}
			;
			document.getElementById("optionalId").style.display = "none";
			document.getElementById("searchItem").style.display = "none";
		} else {
			document.getElementById("optionalId").style.display = "block";
			document.getElementById("searchItem").style.display = "block";
			for ( var count = 0; count <= len - 1; count++) {
				var styleId = "mandatory_check_" + count;
				document.getElementById(styleId).disabled = false;
			}
			;
		}
	}
	function viewDetails(id) {
		var url = "StudentCertificateCourse.do?method=showTeacherDetails&certificateCourseId="
				+ id;
		myRef = window
				.open(url, "viewFeeDetails",
						"left=20,top=20,width=500,height=200,toolbar=1,resizable=0,scrollbars=1");
	}
	function printCertCourse() {
		document.getElementById("printCourse").value ="true";
		document.studentCertificateCourseForm.submit();
	}
	function saveCertCourse() {
		document.studentCertificateCourseForm.submit();
	}
	function cancel() {
		document.location.href = "StudentCertificateCourse.do?method=initStudentCertificate";
	}
	function itemSearch(searchValue){
		var sda = document.getElementById("optionalId");
		var len = sda.length;
		var searchValueLen = searchValue.length;
		for(var m =0; m<len; m++){
			sda.options[m].selected = false;		
		}
		for(var j=0; j<len; j++)
		{
			for(var i=0; i<len; i++){
			if(((sda.options[i].text).substring(0, searchValueLen).toUpperCase())  == (searchValue.toUpperCase())){
				sda.options[i].selected = true;
				break;
			}
			}
		}
		document.getElementById("certificateCourseName").value = document.getElementById("optionalId").options[document.getElementById("optionalId").selectedIndex].text;
	}
</script>
<html:form action="/StudentCertificateCourse">
	<html:hidden property="method" styleId="method" value="saveCertificateCourse" />
	<html:hidden property="formName" value="studentCertificateCourseForm" />
	<html:hidden property="pageType" value="1" />

	<html:hidden property="certificateCourseName" styleId="certificateCourseName" value="" />
	<html:hidden property="printCourse" styleId="printCourse" value="false" />
	<table width="98%" border="0">
		<tr>
			<td></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="10"><img src="images/Tright_03_01.gif"></td>
					<td width="100%" background="images/Tcenter.gif" class="body">
					<div align="left"><strong class="boxheader"><bean:message
						key="knowledgepro.admin.certificate.course" /></strong></div>
					</td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" align="center" cellpadding="0"
						cellspacing="0">
						<tr>
							<td colspan="6" align="left">
							<div align="right"><span class='MandatoryMark'><bean:message
								key="knowledgepro.mandatoryfields" /></span></div>
							<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
							<FONT color="green"><html:messages id="msg"
								property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
								<br>
							</html:messages></FONT></div>
							</td>
						</tr>
						<tr>
							<td><img src="images/01.gif" width="5" height="5" /></td>
							<td width="914" background="images/02.gif"></td>
							<td><img src="images/03.gif" width="5" height="5" /></td>
						</tr>
						<tr>
							<td width="5" background="images/left.gif"></td>
							<td width="100%" valign="top">

							<table width="100%" cellspacing="1" cellpadding="2">
								<tr>
									<td>
									<div id="mandatory">
									<table width="99%" border="0" align="center" cellpadding="0"
										cellspacing="0">
										<logic:notEmpty name="studentCertificateCourseForm"
											property="mandatorycourseList">
											<tr>
												<td align="center" class="heading">Mandatory</td>
											</tr>

											<tr>
												<td>&nbsp;</td>
											</tr>
											<c:set var="manLen" value="0" />
											<nested:iterate name="studentCertificateCourseForm"
												property="mandatorycourseList" id="mandatorycourseList"
												indexId="count">
												<c:choose>
													<c:when test="${count%2 == 0}">
														<tr class="row-even">
													</c:when>
													<c:otherwise>
														<tr class="row-white">
													</c:otherwise>
												</c:choose>
												<%
													String s1 = "mandatory_check_" + count;
																String s2 = "hidden_" + count;
												%>
												<td width="55%"><nested:checkbox property="courseCheck"
													styleId="<%=s1%>" onclick="hideOptionalCourse(this.id)" />&nbsp;&nbsp;&nbsp;
												<nested:write name="mandatorycourseList"
													property="courseName" /></td>
												<td width="20%"><b>
												<nested:write name="mandatorycourseList"
													property="maxIntake" /></b>
												</td>	
												<td height="25" align="center">
												<div align="center"><a href="#"
													onclick="viewDetails('<nested:write name="mandatorycourseList" property="id"/>')">Details</a></div>
												</td>
												<c:set var="manLen" value="${manLen+1}" />
											</nested:iterate>
										</logic:notEmpty>
									</table>
									</div>
									</td>
								</tr>
								<tr>
									<td><input type="hidden" name="mandatoryLen"
										id="mandatoryLen" value="<c:out value="${manLen}"/>" />&nbsp;</td>
								</tr>
								<logic:notEmpty name="studentCertificateCourseForm"
									property="optionalCourseList">
									<tr>
										<td>
										<div id="optional">
										<table width="99%" border="0" align="center" cellpadding="0"
											cellspacing="0">
											<tr>
												<td align="center" class="heading">Optional</td>
											</tr>
											<tr>
												<td>&nbsp;</td>
											</tr>
											<tr>
											<td>
												<table width="100%" cellpadding="0" cellspacing="0">
													<tr>
														<td class="row-odd">
															Certificate Course Name
														</td>
														<td class="row-even">
															<html:text property="searchItem" name="studentCertificateCourseForm" styleId="searchItem" size="20" onkeyup="itemSearch(this.value)"/>
														</td>
													</tr>
													<tr>
													<td class="row-odd"></td>
													<td class="row-even">
													<nested:select property="optionalCourseId" styleId="optionalId" styleClass="body" style="width:300px;">
														<html:option value="">Select </html:option>
														<logic:notEmpty name="studentCertificateCourseForm" property="optionalCourseList">
														<nested:optionsCollection name="studentCertificateCourseForm" property="optionalCourseList" label="courseName" value="id" styleClass="comboBig"/>
														</logic:notEmpty>
													</nested:select>
													</td>
													</tr>
												</table>
											</td>
											</tr>
										</table>
										</div>
										</td>
									</tr>
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
					<div align="center">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="30%" height="35">
							<div align="right"><html:button property=""
								styleClass="formbutton" value="save&print"
								onclick="printCertCourse()"></html:button></div>
							</td>
							<td width="15%" height="35">
							<div align="right"><html:button property=""
								styleClass="formbutton" value="save"
								onclick="saveCertCourse()"></html:button></div>
							</td>
							<td width="45%" height="35">
							<div><html:button property="" styleClass="formbutton"
								value="Cancel" onclick="cancel()"></html:button></div>
							</td>
						</tr>
					</table>
					</div>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				<tr>
					<td><img src="images/Tright_03_05.gif" height="29" width="9"></td>
					<td background="images/TcenterD.gif" width="100%"></td>
					<td><img src="images/Tright_02.gif" height="29" width="9"></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</html:form>
