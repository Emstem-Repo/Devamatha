<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<html:html>

<head>
<title><bean:message key="knowledgepro.title" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

<link rel="stylesheet" href="css/calendar.css">
<link href="css/styles.css" rel="stylesheet" type="text/css">

<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 5px;
	margin-right: 0px;
	margin-bottom: 0px;
}
-->
</style>
<script type="text/javascript">
	function cancel() {
		document.location.href = "examValidation.do?method=initExamValidationEntry&updatedScripts=ScriptUpdated";
	}
	function deleteValidationDetails(id){
		deleteConfirm =confirm("Are you sure to delete this entry?");
		if(deleteConfirm)
		document.location.href = "examValidation.do?method=deleteValidationDetails&deleteId="+id;
	}
	function updateScripts(){
		document.getElementById("method").value = "updateNumberOfScriptAndValuator";
     	document.examValidationDetailsForm.submit();
		}
</script>
</head>
<body>
<html:form action="/examValidation" method="POST">
	<html:hidden property="method" styleId="method" value="updateNumberOfScriptAndValuator"/>
	<html:hidden property="formName" value="examValidationDetailsForm" styleId="formName" />
	<html:hidden property="pageType" value="5" />

	<table width="99%" border="0">

		<tr>
			<td><span class="Bredcrumbs">&gt;&gt;Exams
			&gt;&gt;Exam Valuation</span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="heading_white">Valuation Details</td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top">
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
								<tr height="25">
									<td class="row-odd" align="center" colspan="2">Valuator Name: &nbsp;&nbsp;&nbsp;
										
										 &nbsp;&nbsp;&nbsp; <bean:write name="examValidationDetailsForm" property="valuatorName"/>
									</td>
									<td class="row-odd" align="center" colspan="2">Total Number Of Answer Scripts: &nbsp;&nbsp;&nbsp;
										
										 &nbsp;&nbsp;&nbsp; <bean:write name="examValidationDetailsForm" property="totalAnswerScripts"/>
									</td>
								</tr>
								<tr height="25">
									<td class="row-odd" align="center">Issue Date
									</td>
									<td class="row-odd" align="center">Valuator OR Reviewer
									</td>
									<td class="row-odd" align="center">Number Of Answer Scripts
									</td>
								</tr><tr>
								       <logic:notEmpty property="answerScripts" name="examValidationDetailsForm">
				                   		<td width="20%" height="25" class="row-even" align="center"><bean:write name="examValidationDetailsForm" property="answerScripts.issueDate"/></td>
				                   		<td width="12%" height="25" class="row-even" ><html:select property="answerScripts.valuator"  styleId="valuator">
										<html:option value="Valuator1">Valuator1</html:option>
										<html:option value="Valuator2">Valuator2</html:option>
										<html:option value="Reviewer">Reviewer</html:option>
										<html:option value="Project Major">Project Evaluation Major</html:option>
										<html:option value="Project Minor">Project Evaluation Minor</html:option>
										<html:option value="Re-Valuator">Re-Valuator</html:option>
										</html:select></td>
				                   		<td width="17%" height="25" class="row-even" align="center"><html:text property="answerScripts.answerScripts"  styleId="numberOfScripts"></html:text></td>
				                   		</logic:notEmpty>
				                </tr>
							</table>
							</td>
							<td width="5" background="images/right.gif"></td>
						</tr>
						<tr>
							<td height="5"><img src="images/04.gif" width="5" height="5" /></td>
							<td background="images/05.gif"></td>
							<td><img src="images/06.gif" /></td>
						</tr>
					</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif"></td>
				</tr>


				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="heading">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="33%" height="35" align="right">&nbsp;</td>
							<td width="9%" height="35" align="center">
								<input name="Submit1" type="button" class="formbutton" value="Update" onclick="updateScripts()" />
								</td>
							<td width="58%" height="35" align="left"><input
								name="Submit2" type="button" class="formbutton" value="Cancel"
								onclick="cancel()" /></td>
						</tr>
					</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif"></td>
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
</body>

</html:html>