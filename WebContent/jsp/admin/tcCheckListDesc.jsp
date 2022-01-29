<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<script type = "text/javascript">
	function addTermsAndConditions() {
		document.getElementById("method").value = "saveDetailedTC";
		document.termsConditionForm.submit();
	}
	function resetValues() {
		var size = parseInt(document.getElementById("length").value);
		for ( var count = 0; count <= size - 1; count++) {
			document.getElementById("termsConditionList[" + count
					+ "].checklistDescription").value = "";
			document.getElementById("termsConditionList[" + count
					+ "].mandatory").checked = true;

		}
		resetErrMsgs();
	}
	function imposeMaxLength(evt, Object) {
		var keynum = (evt.which) ? evt.which : event.keyCode;
		if (keynum == 8 || keynum == 37 || keynum == 39 || keynum == 9) {
			return true;
		}
		var MaxLen = 200;
		return (Object.value.length < MaxLen);
	}
		
</script>
<html:form action="/TermsCondition" method="post">
	<html:hidden property="method" styleId="method" value="saveDetailedTC" />
	<html:hidden property="formName" value="termsConditionForm" />
	<html:hidden property="id" styleId="id" />
	<html:hidden property="courseName" styleId="courseName" value="" />
	<html:hidden property="pageType" value="1" />
	<html:hidden property="origYear" styleId="origYear" />
	<table width="100%" border="0">
		<tr>

			<td><span class="Bredcrumbs"><bean:message key="knowledgepro.admin" /><span
				class="Bredcrumbs">&gt;&gt; <bean:message key="knowledgepro.admin.terms.conditions.check.list"/>
			&gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>

					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader">  <bean:message key="knowledgepro.admin.terms.conditions.check.list"/> </strong></td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="122" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" cellspacing="1" cellpadding="2">
						<tr bgcolor="#FFFFFF">
							<td height="20" colspan="4">
							<div align="right"><FONT color="red"> <span class='MandatoryMark'>* Mandatory fields</span></FONT></div>
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
								<td height="25" class="row-odd" align="center"><bean:message key="knowledgepro.admin.desc"/></td>

								<td class="row-odd" align="center"><bean:message key="knowledgepro.attendance.mandatory"/></td>
							</tr>						
							<tr>
							<c:set var="temp" value="0" />
							<logic:notEmpty name= "termsConditionForm" property="termsConditionList" >
								<logic:iterate name="termsConditionForm" property="termsConditionList"
									id="termsConditionList"
									type="com.kp.cms.to.admin.TermsConditionChecklistTO" indexId="adm">
									<tr class="row-even">
									<td width="5%" height="25">
	
									<div align="center"><c:out value="${adm+1}" /></div>
									</td>
									<td width="50%" height="25" align="center">

									<input type="text"
										name="termsConditionList[<c:out value="${adm}"/>].checklistDescription"
										id="termsConditionList[<c:out value="${adm}"/>].checklistDescription"
										value="<bean:write name='termsConditionList' property="checklistDescription"/>"
										size="75" maxlength="200">
									
	
									<td width="45%" align="center">
									<input type="radio"
										name="termsConditionList[<c:out value="${adm}"/>].mandatory"
										id="termsConditionList[<c:out value="${adm}"/>].mandatory"
										value="Yes" checked="checked"><bean:message key="knowledgepro.yes" />
										<input type="radio"
										name="termsConditionList[<c:out value="${adm}"/>].mandatory"
										id="termsConditionList[<c:out value="${adm}"/>].mandatory1"
										value="No"><bean:message key="knowledgepro.no" />
										</td>
									</tr>
									<c:set var="temp" value="${temp+1}" />
								</logic:iterate>
							</logic:notEmpty>
							<input type="hidden" name="length" id="length"
										value="<c:out value="${temp}"/>" /> 
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
							<td height="25" colspan="4">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td width="45%" height="35">
									<div align="right">
											<html:button property="" styleClass="formbutton"
												value="Submit" onclick="addTermsAndConditions()"></html:button>
									</div>
									</td>
									<td width="2%"></td>
									<td width="53%">
										<html:button property="" styleClass="formbutton"
										value="Reset" onclick="resetValues()"></html:button>
									</td>
								</tr>
							</table>
							</td>
						</tr>

					</table>
					<div align="center"></div>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td height="20" valign="top"></td>
					<td width="13" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				<tr>
					<td><img src="images/Tright_03_05.gif" width="9" height="29" /></td>
					<td width="100%" background="images/TcenterD.gif"></td>
					<td><img src="images/Tright_02.gif" width="9" height="29" /></td>
				</tr>
			</table>
			</td>

		</tr>
	</table>
</html:form>

<script type="text/javascript">
	var year = document.getElementById("tempyear").value;
	if (year.length != 0) {
		document.getElementById("year").value = year;
	}
</script>