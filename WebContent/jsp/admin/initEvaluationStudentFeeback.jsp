<!DOCTYPE html> 
<html> 
        <head> 
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<meta name="viewport" content="width=device-width, initial-scale=1"> 
<link rel="stylesheet" type="text/css" href="css/styles.css"/>
	<link rel="stylesheet" type="text/css" href="css/sdmenu.css"/>
	<link rel="stylesheet"  href="css/jquery.mobile-1.0.min.css" />
    <script type="text/javascript" src="js/AC_RunActiveContent.js"></script>
    <script type="text/javascript" src="js/sdmenu/sdmenu.js"></script>
    <script type="text/javascript" src="js/ajax/Ajax.js"></script>
    <script type="text/javascript" src="js/ajax/AjaxUtil.js"></script>
    <script type="text/javascript" src="js/leftSwitchMenu.js"></script>
	<script type="text/javascript" src="js/common.js"></script>
	
	<!--<script type="text/javascript" src="jquery/js/jquery.mobile.themeswitcher.js"></script>
	<script type="text/javascript" src="jquery/js/jquery.mobile-1.0.min.js"></script>
	--><script>
	function cancelAction() {
		document.location.href = "StudentLoginAction.do?method=returnHomePage";
	}
	</script>
	</head>
<html:form action="/EvaluationStudentFeedback" >
<html:hidden property="method" styleId="method" value="getTeachersAndSubjectDetails" />
<html:hidden property="formName" value="evaluationStudentFeedbackForm"/>
<html:hidden property="pageType" value="1"/>

<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/st_Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/st_Tcenter.gif" class="body"><strong
						class="boxheader">Faculty Evaluation</strong></td>

					<td width="10"><img src="images/st_Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr height="10%">
					<td height="20" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" height="10%">
					
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr >
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" align="center" cellpadding="0"
						cellspacing="0">
						<tr>
							<td><img src="images/st_01.gif" width="5" height="5" /></td>
							<td width="914" background="images/st_02.gif"></td>

							<td><img src="images/st_03.gif" width="5" height="5" /></td>
						</tr>
						<tr>
							<td width="5" background="images/st_left.gif"></td>
							<td valign="top">
							<table width="100%" cellspacing="1" cellpadding="2">
							<tr>
								<td class="heading" align="center" style="font-size: 14px;" height="50" colspan="2" >Instructions</td>
								</tr>
								<tr><td  align="left" height="15" colspan="2" style="font-size: 12px;" ><i>Dear Student,</i></td></tr>
								<logic:iterate id="id" name="evaluationStudentFeedbackForm" property="instructionsList">
								<tr></tr>
								<tr><td ></td>
								<td  height="15" colspan="1" style="font-size: 12px;">
								<c:out value='${id.description}' escapeXml='false' />
								</td>
								</tr>
								</logic:iterate>
			                 <tr class="row-white" >
			                   <td colspan="2" height="50"><div align="center">
								<html:submit value="Continue" styleClass="classname" ></html:submit>	&nbsp; <html:button property="" value="Close" styleClass="classname" onclick="cancelAction()"></html:button>
								</div></td>
			                 </tr>
							</table>
							</td>
							<td width="5" height="30" background="images/st_right.gif"></td>
						</tr>
						<tr>

							<td height="5"><img src="images/st_04.gif" width="5" height="5" /></td>
							<td background="images/st_05.gif"></td>
							<td><img src="images/st_06.gif" /></td>
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
					<td><img src="images/st_Tright_03_05.gif" width="9" height="29"></td>
					<td width="0" background="images/st_TcenterD.gif"></td>
					<td><img src="images/st_Tright_02.gif" width="9" height="29"></td>
				</tr>
			</table>

</html:form>
</html>