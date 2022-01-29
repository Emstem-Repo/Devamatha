<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
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
	function addExternalEvaluator() {
		document.getElementById("method").value = "addExternalEvaluator";
		document.externalEvaluatorForm.submit();
	}
	function deleteExternalEvaluator(id){
		deleteConfirm = confirm("Are you sure you want to delete this entry?");
		if(deleteConfirm){
			document.location.href = "ExternalEvaluator.do?method=deleteExternalEvaluator&id="+ id;
		}
	}
	function editExternalEvaluator(id) {
		document.location.href = "ExternalEvaluator.do?method=editExternalEvaluator&id="+ id;
	}
	function updateExternalEvaluator() {
		document.getElementById("method").value = "updateExternalEvaluator";
		document.externalEvaluatorForm.submit();
	}
	function reActivate() {
		var name = document.getElementById("name").value;
		document.location.href = "ExternalEvaluator.do?method=reActivateExternalEvaluator&name="+ name;
	}
	function resetMessages(){
		document.getElementById("name").value = "";
		document.getElementById("addressLine1").value = "";
		document.getElementById("addressLine2").value = "";
		document.getElementById("city").value = "";
		document.getElementById("pin").value="";
		document.getElementById("email").value="";
		document.getElementById("mobile").value="";
		document.getElementById("pan").value="";
		document.getElementById("department").value ="";
		document.getElementById("countryId").selectedIndex = 0;
		document.getElementById("state").selectedIndex = 0;
		document.getElementById("bankAccNo").value = "";
		document.getElementById("bankName").value = "";
		document.getElementById("bankBranch").value = "";
		resetOption("state");
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
<html:form action="/ExternalEvaluator" method="post">
	<c:choose>
		<c:when test="${externalOperation == 'edit'}">
			<html:hidden property="method" styleId="method" value="editExternalEvaluator" />
		</c:when>
		<c:otherwise>
			<html:hidden property="method" styleId="method" value="addExternalEvaluator" />
		</c:otherwise>
	</c:choose>
	<html:hidden property="formName" value="externalEvaluatorForm" />
	<html:hidden property="pageType" value="1" />
	<html:hidden property="id" />
	<table width="100%" border="0">
		<tr>
		<td><span class="heading"><bean:message
				key="knowledgepro.exam" /> <span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.exam.externalevaluator" />&gt;&gt;</span></span></td>
		</tr>
		<tr>
		<td valign="top">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> <bean:message
						key="knowledgepro.exam.external.evaluators.details" /></strong></td>
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
							<tr>
									<td height="1" colspan="2" class="heading" align="left">
										<bean:message key="knowledgepro.employee.contactdetails"/>
									</td>
								</tr>
							<tr>
									<td width="20%" height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>&nbsp;
									<bean:message key="knowledgepro.exam.name" />:</div>
									</td>
									<td width="25%" class="row-even"><span class="star">
									<input type="hidden" name="nm" id="nm"
										value='<bean:write name="externalEvaluatorForm" property="name"/>' />
									<html:text property="name" styleClass="TextBox" styleId="name"
										size="25" maxlength="50" onkeyup="caps(this)"></html:text> </span></td>
									<td width="20%" height="25" class="row-odd">
									<div align="right"></div>
									</td>
									<td width="25%" height="25" class="row-even">
									<div align="right"></div>
									</td>									
							</tr>
								<tr>
									<td height="25" class="row-odd">
									<div align="right">&nbsp;
									<bean:message key="admissionForm.studentinfo.addrs1.label" /></div>
									</td>
									<td height="25" class="row-even"><span class="star">
									<input type="hidden" name="a1" id="a1"
										value='<bean:write name="externalEvaluatorForm" property="addressLine1"/>' />
									<html:text property="addressLine1" styleClass="TextBox"
										styleId="addressLine1" size="25" maxlength="100"></html:text> </span></td>
									<td class="row-odd">
									<div align="right">&nbsp;
									<bean:message key="admissionForm.studentinfo.addrs2.label" /></div>
									</td>
									<td class="row-even"><span class="star"> <input
										type="hidden" name="a2" id="a2"
										value='<bean:write name="externalEvaluatorForm" property="addressLine2"/>' />
									<html:text property="addressLine2" styleClass="TextBox"
										styleId="addressLine2" size="25" maxlength="100"></html:text> </span></td>
								</tr>
								<tr>
									<td height="25" class="row-odd">
									<div align="right">&nbsp;
									<bean:message
										key="admissionForm.studentinfo.addrs1.country.label" /></div>
									</td>
									<td height="25" class="row-even"><input type="hidden"
										id="cid" name="cid"
										value='<bean:write name="externalEvaluatorForm" property="countryId"/>' />

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
									<div align="right">&nbsp;<bean:message
										key="admissionForm.studentinfo.addrs1.state.label" /></div>
									</td>
									<td class="row-even"><input type="hidden" id="sid"
										name="sid"
										value='<bean:write name="externalEvaluatorForm" property="stateId"/>' />
									<html:select property="stateId" styleClass="comboMediumLarge"
										styleId="state">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
											<c:choose>
												<c:when test="${externalOperation == 'add'}">
												<c:if test="${stateMap!=null && stateMap != ''}">
													<html:optionsCollection name="stateMap" label="value"
														value="key" />
														</c:if>
												</c:when>
											</c:choose>
											<c:choose>
												<c:when test="${externalOperation == 'edit'}">
												<c:if test="${stateMap!=null && stateMap != ''}">
													<html:optionsCollection name="stateMap" label="value"
														value="key" />
														</c:if>
												</c:when>
												<c:otherwise>
													<c:if
														test="${externalEvaluatorForm.countryId != null && externalEvaluatorForm.countryId!= ''}">
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
									<div align="right">&nbsp;<bean:message
										key="knowledgepro.exam.city" />:</div>
									</td>
									<td height="25" class="row-even"><input type="hidden"
										name="c1" id="c1"
										value='<bean:write name="externalEvaluatorForm" property="city"/>' />
									<html:text property="city" styleClass="TextBox" styleId="city"
										size="25" maxlength="50"></html:text></td>
									<td class="row-odd">
									<div align="right">&nbsp;<bean:message
										key="knowledgepro.exam.pin" />:</div>
									</td>
									<td class="row-even"><span class="star"> <input
										type="hidden" name="pn" id="pn"
										value='<bean:write name="externalEvaluatorForm" property="pin"/>' />
									<html:text property="pin" styleId="pin"
										styleClass="TextBox" size="25" maxlength="10" onkeypress="return isNumberKey(event)"></html:text> </span></td>
								</tr>
								<tr>
									<td height="25" class="row-odd">
									<div align="right">&nbsp;<bean:message
										key="knowledgepro.exam.email" />:</div>
									</td>
									<td height="25" class="row-even"><input type="hidden"
										name="em" id="em"
										value='<bean:write name="externalEvaluatorForm" property="email"/>' />
									<html:text property="email" styleClass="TextBox" styleId="email"
										size="25" maxlength="50"></html:text></td>
									<td class="row-odd">
									<div align="right">&nbsp;<bean:message
										key="knowledgepro.exam.mobile" />:</div>
									</td>
									<td class="row-even"><span class="star"> <input
										type="hidden" name="mb" id="mb"
										value='<bean:write name="externalEvaluatorForm" property="mobile"/>' />
									<html:text property="mobile" styleId="mobile"
										styleClass="TextBox" size="25" maxlength="10" onkeypress="return isNumberKey(event)"></html:text> </span></td>
								</tr>
								<tr>
									<td height="25" class="row-odd">
									<div align="right">&nbsp;<bean:message
										key="knowledgepro.exam.pan" />:</div>
									</td>
									<td height="25" class="row-even"><input type="hidden"
										name="pa" id="pa"
										value='<bean:write name="externalEvaluatorForm" property="pan"/>' />
									<html:text property="pan" styleClass="TextBox" styleId="pan"
										size="25" maxlength="10" onkeyup="caps(this)"></html:text></td>
									<td class="row-odd">
									<div align="right">&nbsp;<bean:message
										key="knowledgepro.exam.department" />:</div>
									</td>
									<td class="row-even"><span class="star"> <input
										type="hidden" name="dp" id="dp"
										value='<bean:write name="externalEvaluatorForm" property="department"/>' />
									<html:text property="department" styleId="department"
										styleClass="TextBox" size="25" maxlength="50"></html:text> </span></td>
									</tr>
										
								<tr>
									<td height="15" colspan="2" class="heading" align="left">
										<bean:message key="knowledgepro.exam.external.bankinfo.label"/>
									</td>
								</tr>
								<tr>
								<td height="25" class="row-odd">
								<div align="right">&nbsp;<bean:message key="knowledgepro.employee.bankAccNo" /></div>
								</td>
								<td height="25" class="row-even"><input type="hidden" name="ba" id="ba"
										value='<bean:write name="externalEvaluatorForm" property="bankAccNo"/>' />
									<html:text property="bankAccNo" styleClass="TextBox" styleId="bankAccNo"
										size="25" maxlength="30"></html:text></td>
								<td height="25" class="row-odd">
								<div align="right">Bank IFSC Code:</div>
								</td>
								<td height="25" class="row-even"><input type="hidden" name="if" id="if"
										value='<bean:write name="externalEvaluatorForm" property="bankIfscCode"/>' />
									<html:text property="bankIfscCode" styleClass="TextBox" styleId="bankIfscCode"
										size="25" maxlength="11" onkeyup="caps(this)"></html:text>
										<img align="top" src="images/questionMark.jpg" style="cursor:pointer" width="20" height="20"
								 onclick="openURL()" title="Click here for the IFSC code list" />
										</td>
								</tr>
								<tr>
								<td height="25" class="row-odd">
								<div align="right">&nbsp;<bean:message key="knowledgepro.admin.bankName" /></div>
								</td>
								<td height="25" class="row-even"><input type="hidden" name="bn" id="bn"
										value='<bean:write name="externalEvaluatorForm" property="bankName"/>' />
									<html:text property="bankName" styleClass="TextBox" styleId="bankName"
										size="25" maxlength="50"></html:text></td>
								
								<td height="25" class="row-odd">
								<div align="right">&nbsp;<bean:message key="knowledgepro.hostel.reservation.branchName" /></div>
								</td>
								<td height="25" class="row-even"><input type="hidden" name="br" id="br"
										value='<bean:write name="externalEvaluatorForm" property="bankBranch"/>' />
									<html:text property="bankBranch" styleClass="TextBox" styleId="bankBranch"
										size="25" maxlength="50"></html:text></td>										
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
								<c:when test="${externalOperation == 'edit'}">
									<html:button property="" styleClass="formbutton" value="Update"
										onclick="updateExternalEvaluator()"></html:button>
								</c:when>
								<c:otherwise>
									<html:button property="" styleClass="formbutton" value="Submit"
										onclick="addExternalEvaluator()"></html:button>
								</c:otherwise>
							</c:choose></div>
							</td>
							<td width="2%"></td>
							<td width="53%"><c:choose>
								<c:when test="${externalOperation == 'edit'}">
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
									<td align="center" class="row-odd"><bean:message
										key="knowledgepro.exam.name" /></td>
									<td align="center" class="row-odd"><bean:message
										key="knowledgepro.exam.email" /></td>
									<td align="center" class="row-odd"><bean:message
										key="knowledgepro.exam.mobile" /></td>
									<td align="center" class="row-odd"><bean:message
										key="knowledgepro.exam.department" /></td>
									<td class="row-odd">
									<div align="center"><bean:message key="knowledgepro.edit" />
									</div>
									</td>
									<td class="row-odd">
									<div align="center"><bean:message key="knowledgepro.delete" /></div>
									</td>
								</tr>
								<c:set var="temp" value="0" />
								<logic:notEmpty name="externalEvaluatorForm"
									property="externalList">
									<nested:iterate id="rec" name="externalEvaluatorForm"
										property="externalList"
										type="com.kp.cms.to.exam.ExternalEvaluatorTO" indexId="count">
										<c:choose>
											<c:when test="${temp == 0}">
											<tr>
											<td width="5%" height="25" class="row-even">
											<div align="center"><c:out value="${count + 1}" /></div>
											</td>
											<td align="center" width="20%" class="row-even"><nested:write
												name="rec" property="name" /></td>
											<td align="center" width="10%" class="row-even"><nested:write
												name="rec" property="email" /></td>
											<td align="center" width="10%" class="row-even"><nested:write
												name="rec" property="mobile" /></td>
											<td align="center" width="10%" class="row-even"><nested:write
												name="rec" property="department" /></td>	
											<td align="center" width="5%" height="25" class="row-even">
											<div align="center"><img src="images/edit_icon.gif"
												width="16" height="18" style="cursor: pointer"
												onclick="editExternalEvaluator('<nested:write name="rec" property="id" />')" /></div>
											</td>
											<td width="5%" height="25" class="row-even">
											<div align="center"><img src="images/delete_icon.gif"
												width="16" height="16" style="cursor: pointer"
												onclick="deleteExternalEvaluator('<bean:write name="rec" property="id"/>')"></div>
											</td>
										</tr>
										<c:set var="temp" value="1" />
										</c:when>
											<c:otherwise>
												<tr>
											<td width="5%" height="25" class="row-white">
												<div align="center"><c:out value="${count + 1}" /></div>
											</td>
											<td align="center" width="20%" class="row-white"><nested:write
												name="rec" property="name" /></td>
											<td align="center" width="10%" class="row-white"><nested:write
												name="rec" property="email" /></td>
											<td align="center" width="10%" class="row-white"><nested:write
												name="rec" property="mobile" /></td>
											<td align="center" width="10%" class="row-white"><nested:write
												name="rec" property="department" /></td>
											<td align="center" width="5%" height="25" class="row-white">
											<div align="center"><img src="images/edit_icon.gif"
												width="16" height="18" style="cursor: pointer"
												onclick="editExternalEvaluator('<nested:write name="rec" property="id" />')" /></div>
											</td>
											<td width="5%" height="25" class="row-white">
											<div align="center"><img src="images/delete_icon.gif"
												width="16" height="16" style="cursor: pointer"
												onclick="deleteExternalEvaluator('<bean:write name="rec" property="id"/>')"></div>
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