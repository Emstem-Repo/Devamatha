<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>

<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<SCRIPT type="text/javascript">
	function cancelAction() {
		document.location.href = "WeightageDefenition.do?method=cancelWeightageEntry";
	}

	function calculateTotalValue() {
		var totalEducationalWeightValue = 0.0;
		for ( var i = 0; i < document.forms[0].elements.length; i++) {
			var e = document.forms[0].elements[i];
			if ((e.type == 'text')) {
				if (e.name != "totalEducationWeightage") {
					checkForEmpty(e);
					isValidNumber(e);
					totalEducationalWeightValue = totalEducationalWeightValue
							+ parseFloat(e.value);
				}
			}
		}
		document.getElementById("totalEducationWeightage").value = totalEducationalWeightValue;
	}

	function isValidNumber(field) {
		if (isNaN(field.value)) {
			field.value = "0";
		}
	}
</SCRIPT>
<html:form action="/WeightageDefenition" focus="programType">
	<html:hidden property="method" styleId="method"
		value="submitEducationWeightageDefenition" />
	<html:hidden property="pageType" value="2" />
	<html:hidden property="formName" value="weightageDefenitionForm" />
	<table width="98%" border="0">

		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif"></td>
					<td colspan="2" background="images/Tcenter.gif" class="body">
					<div align="left"><span class="Bredcrumbs"><a
						href="Admission_Intro.html" class="boxheader"><bean:message
						key="knowledgepro.admission.weightagedefenition.educationalweightage" /></a></span></div>
					</td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td height="46" colspan="2" class="heading"><img
						src="images/Weighage_Educational_Tab.jpg" border="0" usemap="#Map">
					<map name="Map">
						<area shape="rect" coords="6,4,150,29"
							href="WeightageDefenition.do?method=initWeightageDefenition">
						<area shape="rect" coords="156,6,263,30"
							href="WeightageDefenition.do?method=initPrerequisiteWeightageDefenition">
						<area shape="rect" coords="392,6,501,30"
							href="WeightageDefenition.do?method=initInterviewWeightageDefenition">
						<area shape="rect" coords="512,5,619,30"
							href="WeightageDefenition.do?method=initGeneralWeightageDefenition">

					</map></td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>

				<tr>
					<td height="52" valign="top" background="images/Tright_03_03.gif"></td>
					<td colspan="2" valign="top" class="news">
					<table width="99%" border="0" align="center" cellpadding="0"
						cellspacing="0">
						<tr>
							<td height="20" colspan="6" class="body" align="left">
							<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
							<FONT color="green"> <html:messages id="msg"
								property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
								<br>
							</html:messages> </FONT></div>
							</td>
						</tr>
						<tr>
							<td><img src="images/01.gif" width="5" height="5"></td>
							<td width="914" background="images/02.gif"></td>
							<td><img src="images/03.gif" width="5" height="5"></td>
						</tr>
						<tr>
							<td width="5" background="images/left.gif"></td>
							<td height="91" valign="top">
							<table width="100%" cellspacing="1" cellpadding="2">
								<tr>
									<td width="42%" height="25" class="row-odd">
									<div align="center"><bean:message
										key="knowledgepro.admission.documenttype" /></div>
									</td>
									<td width="51%" height="25" class="row-odd"><bean:message
										key="knowledgepro.admin.weightage" />
									<div align="left"></div>
									</td>
								</tr>
								<c:set var="temp" value="0" />

								<nested:iterate name="weightageDefenitionForm"
									property="educationWeightageList" indexId="count">
									<%
										String abc = "weightageentry" + count;
									%>

									<c:choose>
										<c:when test="${temp == 0}">

											<tr>
												<td height="25" class="row-even">
												<div align="center"><nested:write
													property="documentName" /></div>
												</td>
												<td height="25" class="row-even"><span class="bodytext">
												<nested:text property="weightagePercentage"
													styleId="weightagePercentage_<c:out value='${count}'/>"
													styleClass="TextBox" size="17" maxlength="6"
													onkeypress="return isDecimalNumberKey(this.value,event)"
													onblur="calculateTotalValue()"></nested:text> </span></td>
											</tr>

										</c:when>
										<c:otherwise>
											<tr>
												<td height="25" class="row-odd">
												<div align="center"><nested:message key="documentName" />
												</div>
												</td>
												<td height="25" class="row-even"><span class="bodytext">
												<nested:write property='weightagePercentage' /> <nested:text
													property="weightagePercentage"
													styleId="weightagePercentage_<c:out value='${count}'/>"
													styleClass="TextBox" size="17" maxlength="6"
													onkeypress="return isDecimalNumberKey(this.value,event)"
													onblur="calculateTotalValue()"></nested:text> </span></td>
											</tr>
										</c:otherwise>
									</c:choose>
								</nested:iterate>


								<tr>
									<td height="25" class="row-white">
									<div align="center" class="heading">
									<div align="right"><bean:message
										key="knowledgepro.admin.noof.total" />:</div>
									</div>
									</td>
									<td height="25" class="row-white">
									<div align="left" class="heading"><span class="bodytext">
									<html:text property="totalEducationWeightage" readonly="true"
										styleClass="TextBox" styleId="totalEducationWeightage"
										size="6" maxlength="6" /> </span></div>
									</td>
								</tr>
							</table>
							</td>
							<td background="images/right.gif" width="5" height="5"></td>
						</tr>
						<tr>
							<td height="5"><img src="images/04.gif" width="5" height="5"></td>
							<td background="images/05.gif"></td>
							<td><img src="images/06.gif"></td>
						</tr>
					</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>


				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td colspan="2" valign="top" class="news">
					<div align="center">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="45%" height="35">
							<div align="right"><html:submit styleClass="formbutton">
								<bean:message key="knowledgepro.submit" />
							</html:submit></div>
							</td>
							<td width="2%"></td>
							<td width="53"><html:button property=""
								styleClass="formbutton" value="Cancel" onclick="cancelAction()"></html:button></td>
						</tr>
					</table>
					</div>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td colspan="2" valign="top" class="news">&nbsp;</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td><img src="images/Tright_03_05.gif"></td>
					<td colspan="2" background="images/TcenterD.gif"></td>
					<td><img src="images/Tright_02.gif" width="9" height="29"></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</html:form>
<SCRIPT type="text/javascript">
   hook=false;
	calculateTotalValue();
</SCRIPT>