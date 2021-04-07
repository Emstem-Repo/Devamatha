<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<script type="text/javascript">
function goBack() {
	document.location.href="ProgressCard.do?method=initProgressCard";
}
function editFee(regNo, studId)
{
	document.getElementById("registrationNo").value = regNo;
	document.getElementById("studentId").value = studId;
	var url = "ProgressCard.do?method=getCandidates&studentId="+studId;
	myRef = window .open(url, "Progres Card", "left=20,top=20,width=900,height=900,toolbar=1,resizable=0,scrollbars=1");
	//document.adminMarksCardForm.submit();
}

</script>

<html:form action="/ProgressCard">
	<html:hidden property="method" styleId="method"
		value="getCandidates" />
	<html:hidden property="formName" value="adminMarksCardForm" />
	<html:hidden property="pageType" value="4" />
	<%-- <html:hidden property="semeterSelectedIndex"
		styleId="semeterSelectedIndex" />--%>
	<html:hidden property="registrationNo" styleId="registrationNo" /> 
	<html:hidden property="studentId" styleId="studentId" />

	<table width="98%" border="0">
		<tr>
			<td><span class="heading"><bean:message
				key="knowledgepro.fee" /><span class="Bredcrumbs">&gt;&gt; <bean:message
				key="knowledgepro.fee.feepayment" /> &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="10"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body">
					<div align="left"><strong class="boxheader"><bean:message
						key="knowledgepro.fee.feepayment" /></strong></div>
					</td>
					<td width="13"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="122" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<div align="center">
					<table width="100%" height="263" border="0" cellpadding="0"
						cellspacing="0">
						<tr>
							<td height="20" colspan="6" align="left">
							<div align="right" style="color: red"><span
								class='MandatoryMark'> <bean:message
								key="knowledgepro.mandatoryfields" /></span></div>
							<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
							<FONT color="green"> <html:messages id="msg"
								property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
								<br>
							</html:messages> </FONT></div>
							</td>
						</tr>
						<tr>
							<td height="30" colspan="6" class="body">
							<table width="100%" border="0" align="center" cellpadding="0"
								cellspacing="0">
								<tr>
									<td><img src="images/01.gif" width="5" height="5" /></td>
									<td width="100%" background="images/02.gif"></td>
									<td><img src="images/03.gif" width="5" height="5" /></td>
								</tr>
								<tr>
									<td width="5" background="images/left.gif"></td>
									<td valign="top">
									<table width="100%" cellspacing="1" cellpadding="1">

										<tr>
											<td class="row-odd" align="right"><bean:message
												key="knowledgepro.interview.Year" /></td>
											<td class="row-white" align="left"><bean:write
												property="year" name="adminMarksCardForm" /></td>
											<td class="row-odd" align="right"><bean:message
												key="knowledgepro.admission.className" />:</td>
											<td class="row-white" align="left"><bean:write
												property="className" name="adminMarksCardForm" /></td>
										</tr>

										
									</table>
									</td>
									<td width="5" height="30" background="images/right.gif"></td>
								</tr>
								<tr>
									<td height="5"><img src="images/04.gif" width="5"
										height="5" /></td>
									<td background="images/05.gif"></td>
									<td><img src="images/06.gif" /></td>
								</tr>
							</table>
							</td>
						</tr>
						<tr><td>&nbsp;</td></tr>
						<tr>
							<td height="30" colspan="6" class="body">
							<table width="100%" border="0" align="center" cellpadding="0"
								cellspacing="0">
								<tr>
									<td><img src="images/01.gif" width="5" height="5" /></td>
									<td width="100%" background="images/02.gif"></td>
									<td><img src="images/03.gif" width="5" height="5" /></td>
								</tr>
								<tr>
									<td width="5" background="images/left.gif"></td>
									<td valign="top">
									<table width="100%" cellspacing="1" cellpadding="1">


										<logic:notEmpty property="candidateList"
											name="adminMarksCardForm">

											<tr height="30px">
												<td class="row-odd" align="center"><bean:message
													key="knowledgepro.exam.UpdateExcludeWithheld.registerNo" /></td>
												<td class="row-odd" align="center"><bean:message
													key="knowledgepro.exam.UpdateExcludeWithheld.studentName" /></td>
												<td class="row-odd" align="center">Print</td>
											</tr>

											<logic:iterate id="list" property="candidateList"
												name="adminMarksCardForm">
												<tr height="30px">
													<td class="row-even" align="center"><bean:write
														property="regNo" name="list" /></td>
													<td class="row-even" align="center"><bean:write
														property="studentName" name="list" /></td>
													<td class="row-even" align="center">
													<div align="center"><img src="images/print-icon.png"
														width="16" height="18"
														onclick="editFee('<bean:write name="list" property="regNo" />','<bean:write name="list" property="studentId" />')" />
													</div>
													</td>
												</tr>
											</logic:iterate>
										</logic:notEmpty>




									</table>
									</td>
									<td width="5" height="30" background="images/right.gif"></td>
								</tr>
								<tr>
									<td height="5"><img src="images/04.gif" width="5"
										height="5" /></td>
									<td background="images/05.gif"></td>
									<td><img src="images/06.gif" /></td>
								</tr>
							</table>
							</td>
						</tr>
						<tr><td>&nbsp;</td></tr>
						<tr>
							<td height="27">
							<div align="center">
							<html:button property=""
								styleClass="formbutton" value="Cancel" onclick="goBack()"></html:button>
							</div>
							</td>
						</tr>
						<tr>
							<td height="10" colspan="6" class="body"></td>
						</tr>
					</table>
					</div>
					</td>
					<td width="13" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				<tr>
					<td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
					<td width="100%" background="images/TcenterD.gif"></td>
					<td><img src="images/Tright_02.gif" width="9" height="29"></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</html:form>
