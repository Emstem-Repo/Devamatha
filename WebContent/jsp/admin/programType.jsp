<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<SCRIPT type="text/javascript">
	function editProgramType(programTypeId, programTypeName, ageFrom, ageTo, isOpen) 
	{
		document.getElementById("id").value = programTypeId;
		document.getElementById("programTypeName").value = programTypeName;
		document.getElementById("editedField").value = programTypeName;
		document.getElementById("ageFrom").value = ageFrom;
		document.getElementById("ageTo").value = ageTo;
		document.getElementById("origAgeFrom").value = ageFrom;
		document.getElementById("origAgeTo").value = ageTo;
		document.getElementById("origisOpen").value=isOpen;
		if(isOpen == "true") {
            document.getElementById("isOpen_1").checked = true;
            document.getElementById("isOpen_2").checked = false;
		}else{
			 document.getElementById("isOpen_1").checked = false;
			 document.getElementById("isOpen_2").checked = true;
		}
		
//		document.getElementById("programTypeName").select();
		document.getElementById("method").value = "editProgramType";
		document.getElementById("submitbutton").value = "Update";
		resetErrMsgs();
	}
	function deleteProgramType(programTypeId,programTypeName) 
	{
		deleteConfirm = confirm("Are you sure you want to delete this entry?")
		if (deleteConfirm) {
			document.location.href = "ProgramType.do?method=deleteProgramType&programTypeId="
					+ programTypeId+"&programTypeName="+programTypeName;
		}
	}
	function resetValues()
	{
		document.getElementById("programTypeName").value ="";
		document.getElementById("programTypeName").value ="";
		document.getElementById("ageFrom").value = "";
		document.getElementById("ageTo").value = "";
		 document.getElementById("isOpen_1").value = true;
		if(document.getElementById("method").value == "editProgramType")
		{
			document.getElementById("programTypeName").value =document.getElementById("editedField").value;
			document.getElementById("ageFrom").value = document.getElementById("origAgeFrom").value;
			document.getElementById("ageTo").value = document.getElementById("origAgeTo").value;
			var isOpen=document.getElementById("origisOpen").value;
			if(isOpen == "true") {
	            document.getElementById("isOpen_1").checked = true;
	            document.getElementById("isOpen_2").checked = false;
			}else{
				 document.getElementById("isOpen_1").checked = false;
				 document.getElementById("isOpen_2").checked = true;
			}
		}
		resetErrMsgs();
	}
	function checkNumber(field) {
		if (isNaN(field.value)) {
			field.value = "";
		}
	}	
	function reActivate() {
		var id = document.getElementById("id").value;
		document.location.href = "ProgramType.do?method=activateProgramType&id="
				+ id;
	}
	
</SCRIPT>
<html:form action="/ProgramType">
	<c:choose>
		<c:when
			test="${operation != null && operation == 'edit'}">
			<html:hidden property="method" styleId="method"
				value="editProgramType" />
		</c:when>
		<c:otherwise>
			<html:hidden property="method" styleId="method" value="addProgramType" />
		</c:otherwise>
	</c:choose>
	<html:hidden property="id" styleId="id" />
	<html:hidden property="formName" value="programTypeForm" />
	<html:hidden property="editedField" styleId="editedField" />
	<html:hidden property="origAgeFrom" styleId="origAgeFrom" />
	<html:hidden property="origAgeTo" styleId="origAgeTo" />
	<html:hidden property="pageType" value="1" />
	<html:hidden property="editedField" styleId="editedField" />
	<html:hidden property="origisOpen" styleId="origisOpen" />
	<table width="100%" border="0">
		<tr>
			<td><span class="heading"><a href="main.html"
				class="Bredcrumbs"><bean:message key="knowledgepro.admin" /></a> <span
				class="Bredcrumbs">&gt;&gt; <bean:message key="knowledgepro.admin.programtype"/> &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> <bean:message key="knowledgepro.admin.programtype"/></strong></td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="122" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" cellspacing="1" cellpadding="2">
						<tr bgcolor="#FFFFFF">
							<td height="20" colspan="4">
							<div align="right"><FONT color="red"> <span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields"/></span></FONT></div>
							<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
							<FONT color="green"> <html:messages id="msg"
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
									<td valign="top">
									<table width="100%" cellspacing="1" cellpadding="2">
			
						<tr>
							<td width="20%" height="25" class="row-odd">
							<div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.admin.programtype"/>:</div>
							</td>
							<td width="30%" height="25" class="row-even"><span
								class="star"><html:text property="programTypeName"
								styleId="programTypeName" styleClass="TextBox" size="16"
								maxlength="20" /></span></td>
								
							<td width="20%" height="25" class="row-odd">
							<div align="right">Is Online Application Open</div>
							</td>
							<td width="30%" height="25" class="row-even">
							<input type="radio" name="isOpen" id="isOpen_1" value="true"/> <bean:message key="knowledgepro.yes"/>
                    		<input type="radio" name="isOpen" id="isOpen_2" value="false" checked="checked"/> <bean:message key="knowledgepro.no"/>
                   			<script type="text/javascript">
								var isOpen = "<bean:write name='programTypeForm' property='isOpen'/>";
								if(isOpen == "true") {
				                        document.getElementById("isOpen_1").checked = true;
								}	
							</script>
							</td>
							
						</tr>
						<tr>
							<td width="20%" height="25" class="row-odd">
							<div align="right"><bean:message key="knowledgepro.admin.program.age.from"/></div>
							</td>
							<td width="30%" height="25" class="row-even"><span
								class="star"><html:text property="ageFrom"
								styleId="ageFrom" styleClass="TextBox" size="16"
								maxlength="2" onkeypress="return isNumberKey(event)"
								onblur="checkNumber(this)"/></span></td>
							<td width="20%" height="25" class="row-odd">
							<div align="right"><bean:message key="knowledgepro.admin.program.age.to"/></div>
							</td>
							<td width="30%" height="25" class="row-even"><span
								class="star"><html:text property="ageTo"
								styleId="ageTo" styleClass="TextBox" size="16"
								maxlength="2" onkeypress="return isNumberKey(event)"
								onblur="checkNumber(this)"/></span></td>
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
					<td height="25" colspan="2">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="45%" height="35">
							<div align="right"><c:choose>
								<c:when
									test="${operation != null && operation == 'edit'}">
									<html:submit property="" styleClass="formbutton"
										value="Update" styleId="submitbutton">
									</html:submit>
								</c:when>
								<c:otherwise>
									<html:submit property="" styleClass="formbutton"
										value="Submit" styleId="submitbutton">
									</html:submit>
								</c:otherwise>
							</c:choose></div>
							</td>
							<td width="2%"></td>
							<td width="53%"><html:button property=""
								styleClass="formbutton" value="Reset" onclick="resetValues()"></html:button></td>
						</tr>
					</table>
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
							<td valign="top">
							<table width="100%" cellspacing="1" cellpadding="2">
						<tr>
							<td height="25" colspan="6">
							<table width="100%" cellspacing="1" cellpadding="2">
								<tr>
									<td height="25" class="row-odd">
									<div align="center"><bean:message key="knowledgepro.slno"/></div>
									</td>
									<td height="25" class="row-odd" align="center"><bean:message key="knowledgepro.admin.programtype"/></td>
									<td height="25" class="row-odd" align="center">Is Online Application Open</td>
									<td height="25" class="row-odd" align="center"><bean:message key="knowledgepro.admin.programType.age.from"/></td>
									<td height="25" class="row-odd" align="center"><bean:message key="knowledgepro.admin.programType.age.to"/></td>
									<td class="row-odd">
									<div align="center"><bean:message key="knowledgepro.edit"/></div>
									</td>
									<td class="row-odd">
									<div align="center"><bean:message key="knowledgepro.delete"/></div>
									</td>
								</tr>
								<c:set var="temp" value="0" />
								<logic:iterate name="programTypeList" id = "ptList" indexId="count">
									<c:choose>
										<c:when test="${temp == 0}">
											<tr>
												<td width="8%" height="25" class="row-even">
												<div align="center"><c:out value="${count+1}" /></div>
												</td>
												<td width="55%" height="25" class="row-even" align="center"><bean:write
													name="ptList" property="programTypeName" /></td>
													<td width="10%" height="25" class="row-even" align="center"><bean:write
													name="ptList" property="isOpen" /></td>
												<td width="10%" height="25" class="row-even" align="center"><bean:write
													name="ptList" property="ageFrom" /></td>
												<td width="10%" height="25" class="row-even" align="center"><bean:write
													name="ptList" property="ageTo" /></td>
												<td width="9%" height="25" class="row-even">
												<div align="center"><img src="images/edit_icon.gif"
													width="16" height="18"  style="cursor:pointer" 
													onclick="editProgramType('<bean:write name="ptList" property="programTypeId" />','<bean:write name="ptList" property="programTypeName" />', '<bean:write name="ptList" property="ageFrom" />', '<bean:write name="ptList" property="ageTo" />', '<bean:write name="ptList" property="isOpen" />')" />
												</div>
												</td>
												<td width="8%" height="25" class="row-even">
												<div align="center"><img src="images/delete_icon.gif"
													width="16" height="16"  style="cursor:pointer" 
													onclick="deleteProgramType('<bean:write name="ptList" property="programTypeId" />','<bean:write name="ptList" property="programTypeName" />')" /></div>
												</td>
											</tr>
											<c:set var="temp" value="1" />
										</c:when>
										<c:otherwise>
											<tr>
												<td height="25" class="row-white">
												<div align="center"><c:out value="${count+1}" /></div>
												</td>
												<td width = "55%" height="25" class="row-white" align="center"><bean:write
													name="ptList" property="programTypeName" /></td>
													<td width="10%" height="25" class="row-white" align="center"><bean:write
													name="ptList" property="isOpen" /></td>
												<td width="10%" height="25" class="row-white" align="center"><bean:write
													name="ptList" property="ageFrom" /></td>
												<td width="10%" height="25" class="row-white" align="center"><bean:write
													name="ptList" property="ageTo" /></td>
												<td height="25" class="row-white">
												<div align="center"><img src="images/edit_icon.gif"
													width="16" height="18"  style="cursor:pointer" 
													onclick="editProgramType('<bean:write name="ptList" property="programTypeId" />','<bean:write name="ptList" property="programTypeName" />', '<bean:write name="ptList" property="ageFrom" />', '<bean:write name="ptList" property="ageTo" />', '<bean:write name="ptList" property="isOpen" />')" /></div>
												</td>
												<td height="25" class="row-white">
												<div align="center"><img src="images/delete_icon.gif"
													width="16" height="16"  style="cursor:pointer" 
													onclick="deleteProgramType('<bean:write name="ptList" property="programTypeId" />','<bean:write name="ptList" property="programTypeName" />')" /></div>
												</td>
											</tr>
											<c:set var="temp" value="0" />
										</c:otherwise>
									</c:choose>
								</logic:iterate>
							</table>
							</td>
						</tr>

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

					</table>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>

				<tr>
					<td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
					<td width="0" background="images/Tcenter.gif"></td>
					<td><img src="images/Tright_02.gif" width="9" height="29"></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</html:form>