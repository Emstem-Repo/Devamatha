<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<%@page import="java.util.Map,java.util.HashMap"%>
<html>
<head>
<link rel="stylesheet" type="text/css" href="css/styles.css"/>
	<script>
	function cancelAction() {
		document.location.href = "EvaluationTeacherFeedback.do?method=initEvaluationTeacherFeedback";
	}
	</script>
	
</head>
<body>
<html:form action="/EvaluationTeacherFeedback" >
<html:hidden property="formName" value="evaluationTeacherFeedbackForm"/>
<html:hidden property="method" styleId="method" value="getStudentsForFeedback" />
<html:hidden property="pageType" value="2"/>

<table width="100%" border="0" cellpadding="0" cellspacing="0" align="left">
<tr>
  </tr>
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader">Teacher Evaluation</strong></td>

					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr height="10%">
					<td height="20" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" height="10%">
					
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				
				
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" align="center" cellpadding="0"
						cellspacing="0">
						<tr>
						
            <td valign="top" class="news">
			</td>
          </tr>
         <tr>
							<td width="5" background="images/st_left.gif"></td>
							<td valign="top">
							<table width="100%" cellspacing="1" cellpadding="2">
							<tr>
								<td class="heading" align="center" style="font-size: 14px;" height="50" colspan="2" >Instructions</td>
								</tr>
								<tr><td  align="left" height="15" colspan="2" style="font-size: 12px;" ><i>Dear Faculty,</i></td></tr>
								<logic:notEmpty name="evaluationTeacherFeedbackForm" property="instructionsTOsList">
								<logic:iterate id="id" name="evaluationTeacherFeedbackForm" property="instructionsTOsList">
								<tr></tr>
								<tr><td ></td>
								<td  height="15" colspan="1" style="font-size: 12px;">
								<c:out value='${id.description}' escapeXml='false' />
								</td>
								</tr>
								</logic:iterate>
								</logic:notEmpty>
			                 <tr  >
			                   <td colspan="2" height="50"><div align="center">
								<html:submit value="Continue" styleClass="formbutton"></html:submit>	&nbsp; <html:button property="" value="Close" styleClass="formbutton" onclick="cancelAction()"></html:button>
								</div></td>
			                 </tr>
							</table>
							</td>
							<td width="5" height="30" background="images/right.gif"></td>
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
					<td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
					<td width="0" background="images/TcenterD.gif"></td>
					<td><img src="images/Tright_02.gif" width="9" height="29"></td>
				</tr>
			</table>

</html:form>

</body>
</html>