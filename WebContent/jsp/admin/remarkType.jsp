<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<script type="text/javascript">
	function editRemarkTye(id, occurrences, color, type) {
		document.getElementById("method").value = "updateRemarkType";
		document.getElementById("id").value = id;
		document.getElementById("remarkType").value = type;
		document.getElementById("color").value = color;
		document.getElementById("maxOccurrences").value = occurrences;
		document.getElementById("origRemarkType").value = type;
		document.getElementById("origColor").value = color;
		document.getElementById("origMaxOccurrences").value = occurrences;
		document.getElementById("submitbutton").value = "Update";
		resetErrMsgs();
	}
	
	function deleteRemarkTye(id) {
		deleteConfirm = confirm("Are you sure you want to delete this entry?")
		if (deleteConfirm) {
			document.location.href = "RemarkType.do?method=deleteRemarkType&id=" + id 
		}
	}
	function resetValues() {
		document.getElementById("remarkType").value = "";
		document.getElementById("color").value = "";
		document.getElementById("maxOccurrences").value = "";
		if (document.getElementById("method").value == "updateRemarkType") {
			document.getElementById("remarkType").value = document
					.getElementById("origRemarkType").value;
			document.getElementById("color").value = document
					.getElementById("origColor").value;
			document.getElementById("maxOccurrences").value = document
			.getElementById("origMaxOccurrences").value;
		}
		resetErrMsgs();
	}

	function reActivate() {
		var id = document.getElementById("id").value;
		document.location.href = "RemarkType.do?method=activateRemarkType&id="
				+ id;
	}
	function checkNumber(field) {
		if (isNaN(field.value)) {
			field.value = "";
		}
	}
	
</script>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html:form action="/RemarkType" >
	<c:choose>
		<c:when
			test="${remarkTypeOperation != null && remarkTypeOperation == 'edit'}">
			<html:hidden property="method" styleId="method"
				value="updateRemarkType" />
		</c:when>
		<c:otherwise>
			<html:hidden property="method" styleId="method" value="addRemarkType" />
		</c:otherwise>
	</c:choose>
	<html:hidden property="id" styleId="id" />
	<html:hidden property="formName" value="remarkTypeForm" />
	<html:hidden property="pageType" value="1" />
	<html:hidden property="origRemarkType" styleId="origRemarkType"/>
	<html:hidden property="origColor" styleId="origColor"/>
	<html:hidden property="origMaxOccurrences" styleId="origMaxOccurrences"/>

	<table width="100%" border="0">
		<tr>

			<td><span class="Bredcrumbs"><bean:message key="knowledgepro.admin" /><span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.admin.remarktype.remark.type.required"/> &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td valign="top">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> <bean:message key="knowledgepro.admin.remark.type.entry"/></strong></td>

					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="20" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<div align="right"><FONT color="red"> <span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields"/></span></FONT></div>
					<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
					<FONT color="green"> <html:messages id="msg"
						property="messages" message="true">
						<c:out value="${msg}" escapeXml="false"></c:out>
						<br>
					</html:messages> </FONT></div>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td height="41" valign="top" background="images/Tright_03_03.gif"></td>

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
									<td width="13%" height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.userinfo.remark.type"/></div>
									</td>
									<td width="19%" height="25" class="row-even">
										<html:text property="remarkType" styleId="remarkType" styleClass="TextBox"
											size="10" maxlength="30" /> <span class="star"></span>
									</td>

									<td width="22%" class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.userinfo.remark.type.max.occurances"/></div>
									</td>
									<td width="16%" class="row-even"><span class="star">
									<html:text property="maxOccurrences" styleId="maxOccurrences" styleClass="TextBox"
										size="16" maxlength="3" onkeypress="return isNumberKey(event)" onblur="checkNumber(this)"/> </span></td>

									<td width="22%" class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.userinfo.remark.type.color"/></div>
									</td>
									<td width="16%" class="row-even"><span class="star">
									<input
										type="hidden" id="col" name="col"
										value='<bean:write name="remarkTypeForm" property="color"/>' />
									<html:select property="color"
								styleClass="combo" styleId="color" onchange="this.style.backgroundColor=this.options[this.selectedIndex].style.backgroundColor"  >
								<option value=""><bean:message key="knowledgepro.select" /></option>
								<option value="#FF0000" style="background-color: Red;">Red</option>
								<option value="#A52A2A" style="background-color: Brown;">Brown</option>
								<option value="#0000FF" style="background-color: Blue;">Blue</option>
								<option value="#000080" style="background-color: Navy;color: #FFFFFF;">Navy</option>
								<option value="#006400" style="background-color: DarkGreen;color: #FFFFFF;">DarkGreen</option>
								<option value="#008000" style="background-color: Green;color: #FFFFFF;">Green</option>
								<option value="#9ACD32" style="background-color: YellowGreen;">YellowGreen</option>
								<option value="#FFFF00" style="background-color: Yellow;">Yellow</option>
								<option value="#FFA500" style="background-color: Orange;">Orange</option>
								<option value="#FF1493" style="background-color: DeepPink;">DeepPink</option>
								<option value="#EE82EE" style="background-color: Violet;">Violet</option>
								<option value="#800080" style="background-color: Purple;color: #FFFFFF;">Purple</option>
								<option value="#FFC0CB" style="background-color: Pink;">Pink</option>
								<option value="#7FFFD4" style="background-color: Aquamarine;">Aquamarine</option>
								<option value="#000000" style="background-color: Black;color: #FFFFFF;">Black</option>
								<option value="#808080" style="background-color: Gray;">Gray</option>
								<option value="#DEB887" style="background-color: BurlyWood;">BurlyWood</option>

								</html:select>
								</span></td>

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
					<td height="35" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="45%" height="35">
							<div align="right"><c:choose>
								<c:when
									test="${remarkTypeOperation != null && remarkTypeOperation == 'edit'}">
									<html:submit property="" styleClass="formbutton" value="Update"
										styleId="submitbutton">
									</html:submit>
								</c:when>
								<c:otherwise>
									<html:submit property="" styleClass="formbutton" value="Submit"
										styleId="submitbutton">
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
									<td height="25" class="row-odd" align="center">
									<div align="center"><bean:message key="knowledgepro.slno" /></div>
									</td>
									<td height="25" class="row-odd" align="center"><bean:message key="knowledgepro.admin.remark.type.report"/></td>

									<td class="row-odd" align="center"><bean:message key="knowledgepro.admin.remarktype.max.occurrences.required"/></td>
									<td class="row-odd" align="center">
									<div align="center"><bean:message key="knowledgepro.edit" /></div>
									</td>
									<td class="row-odd" align="center">
									<div align="center"><bean:message
										key="knowledgepro.delete" /></div>
									</td>
								</tr>
								<logic:iterate id="remarkTypeList" name="remarkTypeList"
									indexId="count">
									<c:choose>
										<c:when test="${count%2 == 0}">
											<tr class="row-even">
										</c:when>
										<c:otherwise>
											<tr class="row-white">
										</c:otherwise>
									</c:choose>
									<td width="8%" height="25" align="center">
									<div align="center"><c:out value="${count + 1}" /></div>
									</td>
									<td width="34%" height="25" align="center"><bean:write
										name="remarkTypeList" property="remarkType" /></td>

									<td width="37%" align="center"><bean:write
										name="remarkTypeList" property="maxOccurrences" /></td>
									<td width="11%" height="25" align="center">
									<div align="center"><img src="images/edit_icon.gif"
										width="16" height="18" style="cursor:pointer"
										onclick="editRemarkTye('<bean:write name="remarkTypeList" property="id"/>','<bean:write name="remarkTypeList" property="maxOccurrences"/>','<bean:write name="remarkTypeList" property="color"/>','<bean:write name="remarkTypeList" property="remarkType"/>')">
									</div>
									</td>
									<td width="10%" height="25"  align="center">
									<div align="center"><img src="images/delete_icon.gif"
										width="16" height="16" style="cursor:pointer"
									    onclick="deleteRemarkTye('<bean:write name="remarkTypeList" property="id"/>')">
									</div>
									</td>
									</tr>
								</logic:iterate>
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
	var colorId = document.getElementById("col").value;
	if (colorId.length != 0 && colorId != null) {
		document.getElementById("color").value = colorId;
	}
	</script>