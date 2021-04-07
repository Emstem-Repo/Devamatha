<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@page import="com.kp.cms.constants.CMSConstants"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
	<link rel="stylesheet" href="css/calendar.css">
<script language="JavaScript" src="js/admission/admissionform.js"></script>
<html:form action="/AdmissionStatus" method="POST">
	<html:hidden property="method" styleId="method" />
	<table width="600" border="1" cellpadding="0" cellspacing="0" bordercolor="#E0DFDB">
			<tr>
				<td>
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td valign="top">
							<table width="100%" cellspacing="1" cellpadding="2" border="0" style="height:100%"  >
							<tr>
								<td colspan="2">
									<logic:notEmpty property="messageList" name = "admissionStatusForm">
															<table width="100%" cellspacing="1" cellpadding="2">
																<logic:iterate id="message" property="messageList" name= "admissionStatusForm">
																	<tr><td><c:out value="${message}" escapeXml="false"></c:out></td></tr>
																</logic:iterate>
															</table>
														</logic:notEmpty>
								</td>
							</tr>
							
							</table>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>				
</html:form>
<script type="text/javascript">
	window.print();
</script>