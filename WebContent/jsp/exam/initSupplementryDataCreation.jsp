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
	function getExamNamesByAcademicYear() {
		
		var academicYear=document.getElementById("academicYear").value;
		// Functions for AJAX 
		var args ="method=getSupplementryExamNamesByAcademicYear&academicYear="+academicYear;
		var url ="AjaxRequest.do";
		requestOperation(url,args,updateExamName);
		}
		function updateExamName(req) {
			updateOptionsFromMap(req, "examId", "- Select -");
		}



	function resetValues() {
		document.location.href = "supplementryDataCreation.do?method=initSupplementaryUpdateProcess";
	}
</script>
<html:form action="/supplementryDataCreation">
	<html:hidden property="formName" value="supplementaryDataCreationForm" styleId="formName" />
	<html:hidden property="pageType" value="1" styleId="pageType" />
	<html:hidden property="method" styleId="method" value="getClasses" />
	<table width="100%" border="0">
		<tr>
			<td><span class="heading">Exams<span class="Bredcrumbs">&gt;&gt;
			Update Process &gt;&gt;</span></span></td>
		</tr>

		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="heading_white">Supplementry Data Creation
					</td> <td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>

				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td height="20" class="news">
					<div id="errorMessage"><FONT color="red" size="14px"><html:errors />
					</FONT>
					<FONT color="red" size="1px"><bean:write name="supplementaryDataCreationForm" property="errorMessage"/></FONT>
					<FONT color="green"> <html:messages id="msg"
						property="messages" message="true">
						<c:out value="${msg}" escapeXml="false"></c:out>
						<br>
					</html:messages> </FONT></div>
					<div align="right" class="mandatoryfield">*Mandatory fields</div>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news">
					</td>
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
					<td class="row-odd" width="25%">
									<div align="right">Academic Year:</div>
									</td>
									<td class="row-even" width="25%">
									<input
										type="hidden" id="tempyear" name="appliedYear"
										value="<bean:write name="supplementaryDataCreationForm" property="academicYear"/>" />
									<html:select
										property="academicYear" styleClass="body"
										styleId="academicYear" onchange="getExamNamesByAcademicYear()">
										<cms:renderAcademicYear></cms:renderAcademicYear>

									</html:select></td>
									<td height="25" class="row-odd" width="25%">
									<DIV align="right"><span class="Mandatory">*</span>Exam
									Name :</DIV>
									</td>
									<td height="25" class="row-even" width="50%"><html:select
										property="examId" styleClass="comboLarge" styleId="examId">
										<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
										<logic:notEmpty name="supplementaryDataCreationForm" property="suppExamNameMap">
											<html:optionsCollection property="suppExamNameMap" name="supplementaryDataCreationForm" label="value" value="key"  />
										</logic:notEmpty>
									</html:select></td>
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
					<td height="20" class="news">
					<div align="right" class="mandatoryfield"></div>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td height="10" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="45%" height="35" align="right"><input
								name="Submit2" type="submit" class="formbutton" value="Submit" /></td>

							<td width="2%" height="35" align="center">&nbsp;</td>
							<td width="45%" align="left"><input name="Submit3"
								type="button" class="formbutton" value="Cancel"
								onclick="resetValues()" /></td>
						</tr>
					</table>
					</td>
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
	<script>
	var year = document.getElementById("tempyear").value;
	if(year.length != 0) {
	 	document.getElementById("year").value=year;
	}
</script>

</html:form>