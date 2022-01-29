<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@page import="java.util.Map,java.util.HashMap"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<script>
	function fun() {

		document.location.href = "ExamInternalRetestApplication.do?method=initInternalRetestApplication";

	}
</script>
<html:form action="/ExamInternalRetestApplication.do" method="POST"
	enctype="multipart/form-data">

	<html:hidden property="method" styleId="method" value="update" />
	<html:hidden property="formName"
		value="ExamInternalRetestApplicationForm" styleId="formName" />
	<html:hidden property="pageType" value="3" styleId="pageType" />
	<html:hidden property="internalRetestTo.id" />
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
						class="boxheader">Internal Retest Application</strong></td>

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
									<td class="row-odd" height="28">
									<div align="right">Exam Name</div>
									</td>
									<td class="row-even"><html:hidden
										property="internalRetestTo.studentId"
										name="ExamInternalRetestApplicationForm" /> <bean:write
										property="examName" name="ExamInternalRetestApplicationForm" /></td>
									<td class="row-odd">
									<div align="right">Chance</div>
									</td>
									<td class="row-even"><bean:write
										property="internalRetestTo.chance"
										name="ExamInternalRetestApplicationForm" /></td>
								</tr>

								<tr>
									<td height="26" class="row-odd">
									<div align="right">Class Name:</div>
									</td>
									<td class="row-even"><bean:write
										property="internalRetestTo.className"
										name="ExamInternalRetestApplicationForm" /></td>
									<td height="26" class="row-odd">
									<div align="right">Student Name :</div>
									</td>
									<td class="row-even"><bean:write
										property="internalRetestTo.studentName"
										name="ExamInternalRetestApplicationForm" /></td>
								</tr>
								<tr>

									<td width="30%" height="30" class="row-odd">
									<div align="right">Register No. :</div>
									</td>
									<td width="19%" height="30" class="row-even"><bean:write
										property="internalRetestTo.regNumber"
										name="ExamInternalRetestApplicationForm" /></td>
									<td width="21%" height="30" class="row-odd">
									<div align="right">Roll No. :</div>
									</td>
									<td width="24%" height="30" class="row-even"><bean:write
										property="internalRetestTo.rollNumber"
										name="ExamInternalRetestApplicationForm" /></td>
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
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="47%" height="20">
							<div align="center"></div>
							</td>
						</tr>
					</table>
					</td>
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
									<td height="25" rowspan="2" align="center" class="row-odd">Sl
									No.</td>
									<td rowspan="2" class="row-odd">Subject Code</td>
									<td rowspan="2" class="row-odd">Subject Name</td>

									<td rowspan="2" class="row-odd">Fees</td>
									<td colspan="2" class="row-odd">
									<div align="center">Appeared</div>
									</td>
								</tr>
								<tr>
									<td class="row-odd">Theory</td>
									<td class="row-odd">Practical</td>
								</tr>
								<nested:iterate property="internalRetestTo.subjectList"
									indexId="count">
									<c:choose>
										<c:when test="${count%2 == 0}">
											<tr class="row-even">
										</c:when>
										<c:otherwise>
											<tr class="row-white">
										</c:otherwise>
									</c:choose>

									<td width="9%" height="25" align="center"><c:out
										value="${count+1}"></c:out> <nested:hidden
										property="subjectId" /></td>
									<td width="17%"><nested:write property="subjectCode" /></td>
									<td width="20%"><nested:write property="subjectName" /></td>
									<td width="13%"><nested:text property="fees"
										maxlength="49" /></td>
									<td width="10%">

									<div align="center">
									<%
										String s2 = "hidden_" + count;
													String s1 = "check_" + count;
									%> <nested:hidden styleId='<%=s2%>' property="isCheckedDummy" />
									<nested:checkbox styleId='<%=s1%>' property="theoryAppeared" />
									<script type="text/javascript">
	var v = document.getElementById("hidden_<c:out value='${count}'/>").value;
	if (v == "true") {
		document.getElementById("check_<c:out value='${count}'/>").checked = true;
	}
</script></div>
									</td>
									<td width="10%">


									<div align="center">
									<%
										String s3 = "hidden1_" + count;
													String s4 = "check1_" + count;
									%> <nested:hidden styleId='<%=s3%>'
										property="isCheckedDummyPractical" /> <nested:checkbox
										styleId='<%=s4%>' property="practicalAppeared" /> <script
										type="text/javascript">
	var v1 = document.getElementById("hidden1_<c:out value='${count}'/>").value;
	if (v1 == "true") {
		document.getElementById("check1_<c:out value='${count}'/>").checked = true;
	}
</script>
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
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr><td style="height: 10px" align="center" colspan="3"></td></tr>
						<tr>
							<td width="47%" height="35">
							<div align="right"><input name="button2" type="submit"
								class="formbutton" value="Submit" /></div>
							</td>
							<td width="1%"></td>

							<td width="46%"><input type="button" class="formbutton"
								value="Reset" onclick="fun()" /></td>
						</tr>						
						<tr><td style="height: 10px" align="center" colspan="3"></td></tr>						
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

					<td><img src="images/Tright_02.gif" width="9" height="29"></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>


</html:form>