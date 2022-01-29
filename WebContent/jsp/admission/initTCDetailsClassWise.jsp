<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<script type="text/javascript">
	function getClasses(year) {
		getClassesByYear("classMap", year, "class", updateClasses);
		resetOption("subject");
	}
	function updateClasses(req) {
		updateOptionsFromMap(req, "class", "- Select -");
	}
	function getPracticalBatch() {
		document.getElementById("className").value = document
				.getElementById("class").options[document
				.getElementById("class").selectedIndex].text;
		document.getElementById("method").value = "getCandidates";
		document.tcDetailsClassWiseForm.submit();
	}
</script>
<html:form action="/tcDetailsClassWise" method="post">
	<html:hidden property="method" styleId="method" value="getCandidates" />
	<html:hidden property="formName" value="tcDetailsClassWiseForm" />
	<html:hidden property="pageType" value="1" />
	<html:hidden property="className" styleId="className" />
	<table width="99%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message
				key="knowledgepro.admission" /> <span class="Bredcrumbs">&gt;&gt;<bean:message
				key="knowledgepro.admission.tc.details.label" />&gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="heading_white"><bean:message
						key="knowledgepro.admission.tc.details.label" /></td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="news">
					<div align="right"><span class='MandatoryMark'><bean:message
						key="knowledgepro.mandatoryfields" /> </span></div>
					<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
					<FONT color="green"><html:messages id="msg"
						property="messages" message="true">
						<c:out value="${msg}" escapeXml="false"></c:out>
						<br>
					</html:messages> </FONT></div>
					</td>
					<td valign="top" background="images/Tright_3_3.gif"></td>
				</tr>
				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top">
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
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.interview.Year" /></div>
									</td>
									<td  class="row-even"><input type="hidden"
										id="yr" name="yr"
										value='<bean:write name="tcDetailsClassWiseForm" property="academicYear"/>' />
									<html:select property="academicYear" styleClass="combo" styleId="academicYear"
										onchange="getClasses(this.value)">
										<html:option value="">
											<bean:message key="knowledgepro.select" />-</html:option>
										<cms:renderAcademicYear></cms:renderAcademicYear>
									</html:select></td>
									<td  height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.attendance.class.col" /></div>
									</td>
									<td   class="row-even"><input type="hidden"
										name="c1" id="c1"
										value='<bean:write name="tcDetailsClassWiseForm" property="classId"/>' />
									<html:select property="classId" styleClass="comboLarge"
										styleId="class">
										<html:option value="">
											<bean:message key="knowledgepro.select" />-</html:option>
										<c:choose>
											<c:when test="${classMap != null}">
												<html:optionsCollection name="classMap" label="value"
													value="key" />
											</c:when>
											<c:otherwise>
												<c:if
													test="${tcDetailsClassWiseForm.classId != null && tcDetailsClassWiseForm.classId != ''}">
													<c:set var="classMap"
														value="${baseActionForm.collectionMap['classMap']}" />
													<c:if test="${classMap != null}">
														<html:optionsCollection name="classMap" label="value"
															value="key" />
													</c:if>
												</c:if>
											</c:otherwise>
										</c:choose>
									</html:select></td>
								</tr>
								<tr>
									<td height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.admin.academicyear"/></div>
									</td>
									<td  class="row-even">
									<input type="hidden"
										id="yr1" name="yr1"
										value='<bean:write name="tcDetailsClassWiseForm" property="year"/>' />
									<nested:select property="year" styleId="year" styleClass="comboSmall">
										<html:option value="">Select</html:option>
						              	<cms:renderYear normalYear="true"></cms:renderYear>
									</nested:select></td>
									<td  height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.exam.examDefinition.month" /></div>
									</td>
									<td   class="row-even">
									<nested:select property="month" styleId="month" styleClass="comboSmall">
										<html:option value="">Select</html:option>
										<html:option value="JAN">JAN</html:option>
						              	<html:option value="FEB">FEB</html:option>
										<html:option value="MAR">MAR</html:option>
										<html:option value="APR">APR</html:option>
										<html:option value="MAY">MAY</html:option>
										<html:option value="JUN">JUN</html:option>
										<html:option value="JUL">JUL</html:option>
										<html:option value="AUG">AUG</html:option>
										<html:option value="SEPT">SEPT</html:option>
										<html:option value="OCT">OCT</html:option>
										<html:option value="NOV">NOV</html:option>
										<html:option value="DEC">DEC</html:option>
									</nested:select>
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
					<td valign="top" background="images/Tright_3_3.gif"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="heading">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td height="35" align="center"><html:button property=""
								styleClass="formbutton" value="Next"
								onclick="getPracticalBatch()"></html:button></td>
						</tr>
					</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif"></td>
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
	var yearId = document.getElementById("yr").value;
	if (yearId != null && yearId.length != 0) {
		document.getElementById("academicYear").value = yearId;
	}
	var yearId1 = document.getElementById("yr1").value;
	if (yearId1 != null && yearId1.length != 0) {
		document.getElementById("year").value = yearId1;
	}
	var classId = document.getElementById("c1").value;
	if (classId != null && classId.length != 0) {
		document.getElementById("class").value = classId;
	}
</script>