<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>

<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<script type="text/javascript" language="javascript">
	// Functions for AJAX  first Method
	function editExamDetails(id) {

		document.location.href = "ExamInternalRetestApplication.do?method=edit&id="
				+ id;
	}
	function deleteExamDetails(id) {
		if (confirm("Are you sure to delete this Entry?"))
			document.location.href = "ExamInternalRetestApplication.do?method=delete&id="
					+ id;
	}
</script>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html:form action="/ExamInternalRetestApplication.do" method="POST"
	enctype="multipart/form-data">

	<html:hidden property="method" styleId="method" value="search" />
	<html:hidden property="formName"
		value="ExamInternalRetestApplicationForm" styleId="formName" />
	<html:hidden property="pageType" value="2" styleId="pageType" />
	<table width="100%" border="0">
		<tr>

			<td><span class="heading"><a href="main.html"
				class="Bredcrumbs">Exams</a> <span class="Bredcrumbs">&gt;&gt;
			Internal Retest Application &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> Internal Retest Application</strong></td>

					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td height="20" class="news">
					<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
					<FONT color="green"> <html:messages id="msg"
						property="messages" message="true">
						<c:out value="${msg}" escapeXml="false"></c:out>
						<br>
					</html:messages> </FONT></div>
					<div align="right" class="mandatoryfield">*Mandatory fields</div>
					</td>
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
									<td height="30" class="row-odd">
									<div align="right"><span class="mandatoryfield">*</span>Academic
									Year :</div>
									</td>
									<td height="30" class="row-even"><bean:write
										property="academicYear"
										name="ExamInternalRetestApplicationForm" /></td>
									<td width="25%" height="30" class="row-odd">
									<div align="right"><span class="mandatoryfield">*</span>Exam
									Name :</div>
									</td>
									<td width="25%" height="30" class="row-even"><bean:write
										property="examName" name="ExamInternalRetestApplicationForm" /></td>
								</tr>

								<tr>
									<td height="26" class="row-odd">
									<div align="right">Class Name :</div>
									</td>
									<td class="row-even" colspan="3"><bean:write
										property="className" name="ExamInternalRetestApplicationForm" /></td>

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
					<td height="10" valign="top" background="images/Tright_03_03.gif"></td>

					<td valign="top" class="news">&nbsp;</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td height="10" valign="top" background="images/Tright_03_03.gif"></td>
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
									<td height="25" align="center" class="row-odd">Sl No.</td>

									<td class="row-odd">Class</td>

									<td class="row-odd">Register No.</td>
									<td class="row-odd">Roll No.</td>
									<td class="row-odd">Name</td>
									<td width="13%" class="row-odd">Edit</td>
									<td width="13%" class="row-odd">Delete</td>
								</tr>
								<nested:iterate property="listClassDetails" indexId="count">
									<c:choose>
										<c:when test="${count%2 == 0}">
											<tr class="row-even">
										</c:when>
										<c:otherwise>
											<tr class="row-white">
										</c:otherwise>
									</c:choose>

									<td height="25" align="center"><c:out value="${count+1}"></c:out>
									</td>
									<td><nested:write property="className" /></td>
									<td><nested:write property="regNumber" /></td>
									<td><nested:write property="rollNumber" /></td>
									<td><nested:write property="studentName" /></td>

									<td align="center"><img src="images/edit_icon.gif"
										width="16" height="18" border="0"
										onclick="editExamDetails('<nested:write property="id"/>')"></td>
									<td align="center">
									<div align="center"><img src="images/delete_icon.gif"
										alt="" width="16" height="16"
										onclick="deleteExamDetails('<nested:write property="id"/>')"></div>
									</td>
									</tr>
								</nested:iterate>
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

					<td height="10" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">&nbsp;</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
					<td width="0" background="images/TcenterD.gif"></td>
					<td><img src="../images/Tright_02.gif" width="9" height="29"></td>
				</tr>

			</table>
			</td>
		</tr>
	</table>


</html:form>