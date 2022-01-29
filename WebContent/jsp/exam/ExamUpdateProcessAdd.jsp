<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@page import="java.util.Map,java.util.HashMap"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>

<script type="text/javascript" language="javascript">
	function resetValues() {
		document.location.href = "ExamUpdateProcess.do?method=initExamUpdateProcess";
	}
	function checkAll() {

		val = document.getElementById("checkallBoxes").checked;
		count = document.getElementById("listCount").value;
		for ( var i = 0; i < count; i++) {
			if (val == true) {
				document.getElementById("check_" + i).checked = true;
			} else {
				document.getElementById("check_" + i).checked = false;
			}
		}
	}
</script>
<html:form action="/ExamUpdateProcess.do">

	<html:hidden property="formName" value="ExamUpdateProcessForm"
		styleId="formName" />
	<html:hidden property="pageType" value="2" styleId="pageType" />



	<html:hidden property="method" styleId="method" value="updateProcess" />
	<table width="100%" border="0">
		<tr>
			<td><span class="heading"><a href="main.html"
				class="Bredcrumbs">Exams</a> <span class="Bredcrumbs">&gt;&gt;
			Update Process&gt;&gt;</span></span></td>
		</tr>

		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="heading_white">Exam
					Update Process</td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				
				<tr>

					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td height="20" class="news">
					<div align="right" class="mandatoryfield">*Mandatory fields</div>
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
									<td width="26%" class="row-odd">
									<div align="right">Process:</div>
									</td>

									<td width="27%" class="row-even"><bean:write
										property="processName" name="ExamUpdateProcessForm" /></td>


									<td width="19%" height="25" class="row-odd">
									<div align="right">
									<DIV align="right">Exam Name :</DIV>
									</div>
									</td>
									<td width="28%" height="25" class="row-even"><bean:write
										property="examName" name="ExamUpdateProcessForm" /></td>
								</tr>
								<tr>

									<td class="row-odd" height="28">
									<div align="right">Academic Year:</div>
									</td>
									<td class="row-even" colspan="3"><bean:write
										property="academicYear" name="ExamUpdateProcessForm" /></td>

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
							<table width="100%" cellspacing="1" cellpadding="0">
								<tr>
									<td height="25" align="center" class="row-odd">Select<br>
									<input type="checkbox" name="checkbox2" id="checkallBoxes"
										onclick="checkAll()"></td>
									<td class="row-odd">Joining Batch</td>

									<td class="row-odd">Class</td>
								</tr>
								<nested:iterate property="listClasses" indexId="count">
									<c:choose>
										<c:when test="${count%2 == 0}">
											<tr class="row-even">
										</c:when>
										<c:otherwise>
											<tr class="row-white">
										</c:otherwise>
									</c:choose>
									<%
										String s1 = "check_" + count;
													String s2 = "hidden_" + count;
									%><nested:hidden styleId='<%=s2%>' property="batchDummyCheck" />
									<td width="7%" height="25" align="center"><nested:checkbox
										property="batchCheck" styleId="<%=s1%>" /> <script
										type="text/javascript">
	var v = document.getElementById("hidden_<c:out value='${count}'/>").value;
	if (v == "true") {
		document.getElementById("check_<c:out value='${count}'/>").checked = true;
	}
</script></td>
									<td width="19%"><nested:write property="joiningBatch" /></td>
									<td width="19%"><nested:write property="className" /></td>
									<nested:hidden property="classId" />
									</tr>
								</nested:iterate>
								<nested:hidden property="listCount" styleId="listCount" />
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
					<td valign="top" class="news">
					<table width="94%" border="0" align="center" cellpadding="0"
						cellspacing="0">

						<tr>
							<td width="20%" height="35" align="center">&nbsp;</td>
							<td width="20%" height="35" align="center">&nbsp;</td>
							<td width="20%" align="center"><input name="Submit"
								type="submit" class="formbutton" value="Update " /></td>
							<td width="20%" align="center"><input name="Submit2"
								type="button" class="formbutton" value="Cancel"
								onclick="resetValues()" /></td>
							<td width="20%" align="center">&nbsp;</td>
							<td width="20%" align="center">&nbsp;</td>
						</tr>
						<tr>

							<td height="35" align="center">&nbsp;</td>
							<td height="35" align="center">&nbsp;</td>
							<td align="center">&nbsp;</td>
							<td align="center">&nbsp;</td>
							<td align="center">&nbsp;</td>
							<td align="center">&nbsp;</td>
						</tr>
					</table>
					</td>
					<td valign="top" background="../images/Tright_3_3.gif" class="news"></td>

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