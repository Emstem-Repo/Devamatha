<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
	
<script type="text/javascript">

	function addFacultyGrades() {
		document.getElementById("method").value = "addFacultyGrades";
		document.facultyGradesForm.submit();
	}
	function deleteFacultyGrades(id){
		deleteConfirm = confirm("Are you sure you want to delete this entry?");
		if(deleteConfirm){
			document.location.href = "FacultyGrades.do?method=deleteFacultyGrades&id="+ id;
		}
	}
	function editFacultyGrades(id) {
		document.location.href = "FacultyGrades.do?method=editFacultyGrades&id="+ id;
	}
	function updateFacultyGrades() {
		document.getElementById("method").value = "updateFacultyGrades";
		document.facultyGradesForm.submit();
	}
	function reActivate() {
//		var id = document.getElementById("id").value;
		var grade = document.getElementById("grade").value;
		document.getElementById("method").value = "reActivateFacultyGrades";
		document.facultyGradesForm.submit();
//		document.location.href = "FacultyGrades.do?method=reActivateFacultyGrades&grade="+ grade;
	}
	function resetMessages(){
		document.getElementById("grade").value = "";
		document.getElementById("scaleFrom").value = "";
		document.getElementById("scaleTo").value = "";
		resetErrMsgs();
	}
	function caps(element)
	{
	element.value = element.value.toUpperCase();
	}
</script>
<html:form action="/FacultyGrades" method="post">
	<c:choose>
		<c:when test="${facultyOperation == 'edit'}">
			<html:hidden property="method" styleId="method" value="editFacultyGrades" />
		</c:when>
		<c:otherwise>
			<html:hidden property="method" styleId="method" value="addFacultyGrades" />
		</c:otherwise>
	</c:choose>
	<html:hidden property="formName" value="facultyGradesForm" />
	<html:hidden property="pageType" value="1" />
	<html:hidden property="id" />
	<table width="100%" border="0">
		<tr>
		<td><span class="heading"><bean:message
				key="knowledgepro.exam" /> <span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.studentfeedback.facultygrades" />&gt;&gt;</span></span></td>
		</tr>
		<tr>
		<td valign="top">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> <bean:message
						key="knowledgepro.studentfeedback.facultygrades" /></strong></td>
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
									<td width="20%" height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>&nbsp;
									<bean:message key="knowledgepro.employee.grade" />:</div>
									</td>
									<td width="25%" class="row-even"><span class="star">
									<input type="hidden" name="gr" id="gr"
										value='<bean:write name="facultyGradesForm" property="grade"/>' />
									<html:text property="grade" styleClass="TextBox" styleId="grade"
										size="20" maxlength="10" onkeyup="caps(this)"></html:text> </span></td>
									<td width="20%" height="25" class="row-odd">
									<div align="right"></div>
									</td>
									<td width="25%" height="25" class="row-even">
									<div align="right"></div>
									</td>									
							</tr>
								<tr>
									<td width="20%" height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>&nbsp;
									<bean:message key="knowledgepro.studentfeedback.scalefrom" />:</div>
									</td>
									<td height="25" class="row-even"><span class="star">
									<input type="hidden" name="sf" id="sf"
										value='<bean:write name="facultyGradesForm" property="scaleFrom"/>' />
									<html:text property="scaleFrom" styleClass="TextBox"
										styleId="scaleFrom" size="20" ></html:text> </span></td>
									<td width="20%" height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>&nbsp;
									<bean:message key="knowledgepro.studentfeedback.scaleto" />:</div>
									</td>
									<td class="row-even"><span class="star">
									<input type="hidden" name="st" id="st"
										value='<bean:write name="facultyGradesForm" property="scaleTo"/>' />
									<html:text property="scaleTo" styleClass="TextBox"
										styleId="scaleTo" size="20" ></html:text> </span></td>
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
								<c:when test="${facultyOperation == 'edit'}">
									<html:button property="" styleClass="formbutton" value="Update"
										onclick="updateFacultyGrades()"></html:button>
								</c:when>
								<c:otherwise>
									<html:button property="" styleClass="formbutton" value="Submit"
										onclick="addFacultyGrades()"></html:button>
								</c:otherwise>
							</c:choose></div>
							</td>
							<td width="2%"></td>
							<td width="53%"><c:choose>
								<c:when test="${facultyOperation == 'edit'}">
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
									<td align="center" class="row-odd"><bean:message key="knowledgepro.employee.grade" /></td>
									<td align="center" class="row-odd"><bean:message key="knowledgepro.studentfeedback.scalefrom" /></td>
									<td align="center" class="row-odd"><bean:message key="knowledgepro.studentfeedback.scaleto" /></td>
									<td class="row-odd">
									<div align="center"><bean:message key="knowledgepro.edit" />
									</div>
									</td>
									<td class="row-odd">
									<div align="center"><bean:message key="knowledgepro.delete" /></div>
									</td>
								</tr>
								<c:set var="temp" value="0" />
								<logic:notEmpty name="facultyGradesForm" property="facultyList">
									<logic:iterate id="fac" name="facultyGradesForm" property="facultyList"
										type="com.kp.cms.to.studentfeedback.FacultyGradesTO" indexId="count">
										<c:choose>
											<c:when test="${temp == 0}">
										<tr>
											<td width="5%" height="25" class="row-even">
											<div align="center"><c:out value="${count + 1}" /></div>
											</td>
											<td align="center" width="20%" height="25" class="row-even">
											<bean:write name="fac" property="grade" /></td>
											
											<td align="center" width="15%" height="25" class="row-even">
											<bean:write name="fac" property="scaleFrom" /></td>
											<td align="center" width="15%" height="25" class="row-even">
											<bean:write name="fac" property="scaleTo" /></td>	
											<td align="center" width="5%" height="25" class="row-even">
											<div align="center"><img src="images/edit_icon.gif"
												width="16" height="18" style="cursor: pointer"
												onclick="editFacultyGrades('<bean:write name="fac" property="id" />')" /></div>
											</td>
											<td align="center" width="5%" height="25" class="row-even">
											<div align="center"><img src="images/delete_icon.gif"
												width="16" height="16" style="cursor: pointer"
												onclick="deleteFacultyGrades('<bean:write name="fac" property="id"/>')"></div>
											</td>
										</tr>
										<c:set var="temp" value="1" />
										</c:when>
											<c:otherwise>
										<tr>
											<td width="5%" height="25" class="row-white">
											<div align="center"><c:out value="${count + 1}" /></div>
											</td>
											<td align="center" width="20%" height="25" class="row-white">
											<bean:write name="fac" property="grade" /></td>
											
											<td align="center" width="15%" height="25" class="row-white">
											<bean:write name="fac" property="scaleFrom" /></td>
											<td align="center" width="15%" height="25" class="row-white">
											<bean:write name="fac" property="scaleTo" /></td>	
											<td align="center" width="5%" height="25" class="row-white">
											<div align="center"><img src="images/edit_icon.gif"
												width="16" height="18" style="cursor: pointer"
												onclick="editFacultyGrades('<bean:write name="fac" property="id" />')" /></div>
											</td>
											<td align="center" width="5%" height="25" class="row-white">
											<div align="center"><img src="images/delete_icon.gif"
												width="16" height="16" style="cursor: pointer"
												onclick="deleteFacultyGrades('<bean:write name="fac" property="id"/>')"></div>
											</td>
										</tr>
										<c:set var="temp" value="0" />
											</c:otherwise>
										</c:choose>
									</logic:iterate>
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