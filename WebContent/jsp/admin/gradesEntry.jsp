<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>

<script type="text/javascript">
	function editGrade(gradeId, grade, mark) {
		document.getElementById("method").value = "updateGrades";
		document.getElementById("id").value = gradeId;
		document.getElementById("grade").value = grade;
		document.getElementById("mark").value = mark;
		document.getElementById("submitbutton").value = "Update";
		document.getElementById("origGrade").value = grade;
		document.getElementById("origMark").value = mark;
		resetErrMsgs();
	}

	function deleteGrade(GId, grade) {
		deleteConfirm = confirm("Are you sure you want to delete this entry?")
		if (deleteConfirm) {
			document.location.href = "Grades.do?method=deleteGrade&id="
					+ GId + "&grade=" + grade;
		}
	}

	function resetValues() {
		document.getElementById("grade").value = "";
		document.getElementById("mark").value = "";
		if (document.getElementById("method").value == "updateGrades") {
			document.getElementById("grade").value = document
					.getElementById("origGrade").value;
			document.getElementById("mark").value = document
					.getElementById("origMark").value;
		}
		resetErrMsgs();
	}

	function checkNumber(field) {
		if (isNaN(field.value)) {
			field.value = "";
		}
	}	
</script>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html:form action="/Grades" focus="grade">
	<c:choose>
		<c:when
			test="${gradesOperation != null && gradesOperation == 'edit'}">
			<html:hidden property="method" styleId="method"
				value="updateGrades" />
		</c:when>
		<c:otherwise>
			<html:hidden property="method" styleId="method" value="addGrades" />
		</c:otherwise>
	</c:choose>
	<html:hidden property="id" styleId="id" />
	<html:hidden property="formName" value="gradesForm" />
	<html:hidden property="pageType" value="1" />
	<html:hidden property="origGrade" styleId="origGrade"/>
	<html:hidden property="origMark" styleId="origMark"/>

	<table width="100%" border="0">
		<tr>

			<td><span class="Bredcrumbs"><bean:message key="knowledgepro.admin" /><span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.admin.grade.entry"/> &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td valign="top">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> <bean:message key="knowledgepro.admin.grade.entry"/></strong></td>

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
									<div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.admin.grade.col"/></div>
									</td>
									<td width="19%" height="25" class="row-even">
										<html:text property="grade" styleId="grade" styleClass="TextBox"
											size="10" maxlength="5" /> <span class="star"></span>
									</td>

									<td width="22%" class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.admin.grade.per.mark.col"/></div>
									</td>
									<td width="16%" class="row-even"><span class="star">
									<html:text property="mark" styleId="mark" styleClass="TextBox"
										size="16" maxlength="3"  onkeypress="return isNumberKey(event)"
										onblur="checkNumber(this)"/> </span></td>
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
									test="${gradesOperation != null && gradesOperation == 'edit'}">
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
									<td height="25" class="row-odd" align="center"><bean:message key="knowledgepro.admin.grade.required"/></td>

									<td class="row-odd" align="center"><bean:message key="knowledgepro.admin.grade.per.mark"/></td>
									<td class="row-odd" align="center">
									<div align="center"><bean:message key="knowledgepro.edit" /></div>
									</td>
									<td class="row-odd" align="center">
									<div align="center"><bean:message
										key="knowledgepro.delete" /></div>
									</td>
								</tr>
								<logic:iterate id="gradesList" name="gradesList"
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
										name="gradesList" property="grade" /></td>

									<td width="37%" align="center"><bean:write
										name="gradesList" property="mark" /></td>
									<td width="11%" height="25" align="center">
									<div align="center"><img src="images/edit_icon.gif"
										width="16" height="18" style="cursor:pointer"
										onclick="editGrade('<bean:write name="gradesList" property="id"/>','<bean:write name="gradesList" property="grade"/>','<bean:write name="gradesList" property="mark"/>')">
									</div>
									</td>
									<td width="10%" height="25"  align="center">
									<div align="center"><img src="images/delete_icon.gif"
										width="16" height="16" style="cursor:pointer"
										onclick="deleteGrade('<bean:write name="gradesList" property="id"/>','<bean:write name="gradesList" property="grade"/>')">
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