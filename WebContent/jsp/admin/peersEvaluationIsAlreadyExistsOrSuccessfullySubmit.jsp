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
		document.location.href = "LoginAction.do?method=initLoginAction";
	}
	</script>
	
</head>
<body>
<html:form action="/peersEvaluationFeedback" >
<html:hidden property="formName" value="peersFeedbackForm"/>

<table width="100%" border="0" cellpadding="0" cellspacing="0" align="left">
<tr>
			<td colspan="3"><span class="Bredcrumbs">Faculty Evaluation
			<span class="Bredcrumbs">&gt;&gt;
			Peers Evaluation Feedback
			 &gt;&gt;</span></span></td>
		</tr>
  
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader">Peers Evaluation Feedback</strong></td>

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
			<logic:equal value="true" name="peersFeedbackForm" property="exist">
        	 <td class="heading" align="center"> Peers Evaluation has been Already Submitted. </td>
        	</logic:equal>
        	<!--<logic:equal value="true" name="peersFeedbackForm" property="submitSuccessfully">
        	 <td class="heading" align="center"> Peers Evaluation Completed Successfully.</td>
        	</logic:equal>
          --></tr>
          <tr >
			                   <td colspan="3" height="50"><div align="center">
								 <html:button property="" value="Close" styleClass="formbutton" onclick="cancelAction()"></html:button>
								</div></td>
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