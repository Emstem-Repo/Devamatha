<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>

<script type="text/javascript">
	var stateId;
	function getStates(countryId) {
		if (countryId.length != 0) {
			var args = "method=getStatesByCountry&countryId=" + countryId;
			var url = "AjaxRequest.do";
			// make an request to server passing URL need to be invoked and arguments.
			requestOperation(url, args, updateDistrict);
		} else {
			var state = document.getElementById("state");
			for (x1 = state.options.length - 1; x1 > 0; x1--) {
				state.options[x1] = null;
			}
		}
	}
	function updateDistrict(req) {
		updateOptionsFromMap(req, "state", " Select ");
		if (stateId != null && stateId.length != 0) {
			document.getElementById("stateId").value = stateId;
		}
	}
	function addRecommendedBy() {
		document.getElementById("method").value = "addRecommendedBy";
		document.recommendedByForm.submit();
	}

	function deleteRecommendedBy(id) {
		deleteConfirm = confirm("Are you sure want to delete this entry?")
		if (deleteConfirm) {
			document.location.href = "RecommendedBy.do?method=deleteRecommendedBy&id="
					+ id;
		}
	}
	function editRecommendedBy(id) {
		document.location.href = "RecommendedBy.do?method=editRecommendedBy&id="
				+ id;
	}
	function updateRecommendedBy() {
		document.getElementById("method").value = "updateRecommendedBy";
		document.recommendedByForm.submit();
	}

	function reActivate() {
		var code = document.getElementById("code").value;
		document.location.href = "RecommendedBy.do?method=reActivateRecommendedBy&code="
				+ code;
	}
	function resetMessages() {
		document.getElementById("code").value = "";
		document.getElementById("name").value = "";
		document.getElementById("addressLine1").value = "";
		document.getElementById("addressLine2").value = "";
		document.getElementById("countryId").selectedIndex = 0;
		document.getElementById("state").selectedIndex = 0;
		document.getElementById("city").value = "";
		document.getElementById("phone").value = "";
		document.getElementById("comments").value = "";
		resetOption("state");
		resetErrMsgs();
	}	
</script>
<html:form action="/RecommendedBy" method="post">
	<c:choose>
		<c:when test="${recommendedOperation == 'edit'}">
			<html:hidden property="method" styleId="method"
				value="editRecommendedBy" />
		</c:when>
		<c:otherwise>
			<html:hidden property="method" styleId="method"
				value="addRecommendedBy" />
		</c:otherwise>
	</c:choose>
	<html:hidden property="formName" value="recommendedByForm" />
	<html:hidden property="pageType" value="1" />
	<html:hidden property="id" />
	<table width="100%" border="0">
		<tr>
			<td><span class="heading"><bean:message
				key="knowledgepro.admin" /> <span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.admission.recommendedby" />&gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td valign="top">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> <bean:message
						key="knowledgepro.admission.recommendedby" /></strong></td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="20" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<div align="right"><span class='MandatoryMark'> <bean:message
					key="knowledgepro.mandatoryfields" /></span></div>
					<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
					<FONT color="green"><html:messages id="msg"
						property="messages" message="true">
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
									<td width="19%" height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message
										key="knowledgepro.admin.code.colon" /></div>
									</td>
									<td width="31%" height="25" class="row-even"><span
										class="star"> <input type="hidden" name="cd" id="cd"
										value='<bean:write name="recommendedByForm" property="code"/>' />
									<html:text property="code" styleClass="TextBox" styleId="code"
										size="16" maxlength="20"></html:text> </span><span class="star"></span></td>
									<td width="22%" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message
										key="knowledgepro.admin.name.colon" /></div>
									</td>
									<td width="28%" class="row-even"><span class="star">
									<input type="hidden" name="nm" id="nm"
										value='<bean:write name="recommendedByForm" property="name"/>' />
									<html:text property="name" styleClass="TextBox" styleId="name"
										size="16" maxlength="50"></html:text> </span></td>
								</tr>
								<tr>
									<td height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message
										key="admissionForm.studentinfo.addrs1.label" /></div>
									</td>
									<td height="25" class="row-even"><span class="star">
									<input type="hidden" name="a1" id="a1"
										value='<bean:write name="recommendedByForm" property="addressLine1"/>' />
									<html:text property="addressLine1" styleClass="TextBox"
										styleId="addressLine1" size="16" maxlength="100"></html:text> </span></td>
									<td class="row-odd">
									<div align="right"><bean:message
										key="admissionForm.studentinfo.addrs2.label" /></div>
									</td>
									<td class="row-even"><span class="star"> <input
										type="hidden" name="a2" id="a2"
										value='<bean:write name="recommendedByForm" property="addressLine2"/>' />
									<html:text property="addressLine2" styleClass="TextBox"
										styleId="addressLine2" size="16" maxlength="100"></html:text> </span></td>
								</tr>
								<tr>
									<td height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>&nbsp;
									<bean:message
										key="admissionForm.studentinfo.addrs1.country.label" /></div>
									</td>
									<td height="25" class="row-even"><input type="hidden"
										id="cid" name="cid"
										value='<bean:write name="recommendedByForm" property="countryId"/>' />

									<html:select property="countryId" styleClass="combo"
										styleId="countryId" onchange="getStates(this.value)">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>	
										<c:if test="${countriesMap!=null && countriesMap != ''}">								
										<html:optionsCollection name="countriesMap" label="value"
											value="key" />
										</c:if>	
									</html:select></td>
									<td class="row-odd">
									<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message
										key="admissionForm.studentinfo.addrs1.state.label" /></div>
									</td>
									<td class="row-even"><input type="hidden" id="sid"
										name="sid"
										value='<bean:write name="recommendedByForm" property="stateId"/>' />
									<html:select property="stateId" styleClass="comboMediumLarge"
										styleId="state">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
											<c:choose>
												<c:when test="${recommendedOperation == 'add'}">
												<c:if test="${stateMap!=null && stateMap != ''}">
													<html:optionsCollection name="stateMap" label="value"
														value="key" />
														</c:if>
												</c:when>
											</c:choose>
											<c:choose>
												<c:when test="${recommendedOperation == 'edit'}">
												<c:if test="${stateMap!=null && stateMap != ''}">
													<html:optionsCollection name="stateMap" label="value"
														value="key" />
														</c:if>
												</c:when>
												<c:otherwise>
													<c:if
														test="${recommendedByForm.countryId != null && recommendedByForm.countryId!= ''}">
														<c:set var="stateMap"
															value="${baseActionForm.collectionMap['stateMap']}" />
														<c:if test="${stateMap != null && stateMap != ''}">													
														<html:optionsCollection name="stateMap" label="value"
																value="key" />
														</c:if>
													</c:if>
												</c:otherwise>
											</c:choose>
										</html:option>
									</html:select></td>
								</tr>
								<tr>
									<td height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message
										key="knowledgepro.admin.city.colon" /></div>
									</td>
									<td height="25" class="row-even"><input type="hidden"
										name="c1" id="c1"
										value='<bean:write name="recommendedByForm" property="city"/>' />
									<html:text property="city" styleClass="TextBox" styleId="city"
										size="16" maxlength="30"></html:text></td>
									<td class="row-odd">
									<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message
										key="admissionForm.studentinfo.phone.label" /></div>
									</td>
									<td class="row-even"><span class="star"> <input
										type="hidden" name="ph" id="ph"
										value='<bean:write name="recommendedByForm" property="phone"/>' />

									<html:text property="phone" styleId="phone"
										styleClass="TextBox" size="16" maxlength="15"></html:text> </span></td>
								</tr>
								<tr>
									<td height="25" class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.admin.comments.colon" /></div>
									</td>
									<td height="25" class="row-even"><span class="star">
									<input type="hidden" name="com" id="com"
										value='<bean:write name="recommendedByForm" property="comments"/>' />
									<html:text property="comments" styleId="comments"
										styleClass="TextBox" size="16" maxlength="100"></html:text> </span></td>
									<td class="row-odd">
									<div align="right"></div>
									</td>
									<td class="row-even">&nbsp;</td>
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
					<td height="20" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="49%" height="35">
							<div align="right"><c:choose>
								<c:when test="${recommendedOperation == 'edit'}">
									<html:button property="" styleClass="formbutton" value="Update"
										onclick="updateRecommendedBy()"></html:button>
								</c:when>
								<c:otherwise>
									<html:button property="" styleClass="formbutton" value="Submit"
										onclick="addRecommendedBy()"></html:button>
								</c:otherwise>
							</c:choose></div>
							</td>
							<td width="2%"></td>
							<td width="53%"><c:choose>
								<c:when test="${recommendedOperation == 'edit'}">
									<html:cancel value="Reset" styleClass="formbutton"></html:cancel>
								</c:when>
								<c:otherwise>
									<html:button property="" styleClass="formbutton" value="Reset"
										onclick="resetMessages()"></html:button>
								</c:otherwise>
							</c:choose>
						</tr>
					</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td height="20" valign="top" background="images/Tright_03_03.gif"></td>
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
										key="knowledgepro.admin.code" /></td>
									<td align="center" class="row-odd"><bean:message
										key="knowledgepro.admin.name" /></td>
									<td align="center" class="row-odd"><bean:message
										key="admissionForm.parentinfo.address.label" /></td>
									<td align="center" class="row-odd"><bean:message
										key="knowledgepro.admin.city" /></td>
									<td align="center" class="row-odd"><bean:message
										key="knowledgepro.admin.statename" /></td>
									<td align="center" class="row-odd"><bean:message
										key="knowledgepro.admin.countryname" /></td>
									<td align="center" class="row-odd"><bean:message
										key="admissionFormForm.phone" /></td>
									<td align="center" class="row-odd"><bean:message
										key="admissionFormForm.comments.required" /></td>
									<td class="row-odd">
									<div align="center"><bean:message key="knowledgepro.edit" />
									</div>
									</td>
									<td class="row-odd">
									<div align="center"><bean:message
										key="knowledgepro.delete" /></div>
									</td>
								</tr>
								<c:set var="temp" value="0" />
								<logic:notEmpty name="recommendedByForm"
									property="recommendedList">
									<nested:iterate id="rec" name="recommendedByForm"
										property="recommendedList"
										type="com.kp.cms.to.admin.RecommendedByTO" indexId="count">
										<c:choose>
											<c:when test="${temp == 0}">
										<tr>
											<td width="4%" height="25" class="row-even">
											<div align="center"><c:out value="${count + 1}" /></div>
											</td>
											<td align="center" width="8%" height="25" class="row-even"><nested:write
												name="rec" property="code" /></td>
											<td align="center" width="10%" class="row-even"><nested:write
												name="rec" property="name" /></td>
											<td width="10%" align="center" class="row-even"><nested:write
												name="rec" property="addressLine1" /><br>
											<nested:write name="rec" property="addressLine2" /><br>
											</td>
											<td align="center" width="8%" class="row-even"><nested:write
												name="rec" property="city" /></td>
											<td align="center" width="8%" class="row-even"><nested:write
												name="rec" property="stateTO.name" /></td>
											<td align="center" width="8%" class="row-even"><nested:write
												name="rec" property="countryTO.name" /></td>
											<td align="center" width="8%" class="row-even"><nested:write
												name="rec" property="phone" /></td>
											<td align="center" width="12%" class="row-even"><nested:write
												name="rec" property="comments" /></td>
											<td align="center" width="7%" height="25" class="row-even">
											<div align="center"><img src="images/edit_icon.gif"
												width="16" height="18" style="cursor: pointer"
												onclick="editRecommendedBy('<nested:write name="rec" property="id" />')" /></div>
											</td>
											<td width="7%" height="25" class="row-even">
											<div align="center"><img src="images/delete_icon.gif"
												width="16" height="16" style="cursor: pointer"
												onclick="deleteRecommendedBy('<bean:write name="rec" property="id"/>')"></div>
											</td>
										</tr>
										<c:set var="temp" value="1" />
											</c:when>
											<c:otherwise>
												<tr>
													<td height="25" class="row-white">
													<div align="center"><c:out value="${count + 1}" /></div>
													</td>
											<td align="center" width="8%" height="25" class="row-white"><nested:write
												name="rec" property="code" /></td>
											<td align="center" width="10%" class="row-white"><nested:write
												name="rec" property="name" /></td>
											<td width="10%" align="center" class="row-white"><nested:write
												name="rec" property="addressLine1" /><br>
											<nested:write name="rec" property="addressLine2" /><br>
											</td>
											<td align="center" width="8%" class="row-white"><nested:write
												name="rec" property="city" /></td>
											<td align="center" width="8%" class="row-white"><nested:write
												name="rec" property="stateTO.name" /></td>
											<td align="center" width="8%" class="row-white"><nested:write
												name="rec" property="countryTO.name" /></td>
											<td align="center" width="8%" class="row-white"><nested:write
												name="rec" property="phone" /></td>
											<td align="center" width="12%" class="row-white"><nested:write
												name="rec" property="comments" /></td>
											<td align="center" width="7%" height="25" class="row-white">
											<div align="center"><img src="images/edit_icon.gif"
												width="16" height="18" style="cursor: pointer"
												onclick="editRecommendedBy('<nested:write name="rec" property="id" />')" /></div>
											</td>
											<td width="7%" height="25" class="row-white">
											<div align="center"><img src="images/delete_icon.gif"
												width="16" height="16" style="cursor: pointer"
												onclick="deleteRecommendedBy('<bean:write name="rec" property="id"/>')"></div>
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
					<td height="20" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">&nbsp;</td>
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
	var conId = document.getElementById("cid").value;
	if (conId != null && conId.length != 0) {
		document.getElementById("countryId").value = conId;
	}
</script>