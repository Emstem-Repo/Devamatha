<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>

<%@page import="com.kp.cms.constants.CMSConstants"%><SCRIPT type="text/javascript">
	
	function printMe()
	{
		window.print();
	}
	function closeMe()
	{
		window.close();
	}
</SCRIPT>

<script language="JavaScript" src="js/calendar_us.js"></script>
	<link rel="stylesheet" href="css/calendar.css">
<script language="JavaScript" src="js/admission/admissionform.js"></script>
<html:form action="/admissionFormSubmit" method="POST">
	<html:hidden property="method" value="" />
	<html:hidden property="formName" value="admissionFormForm" />
	<html:hidden property="pageType" value="16" />
	
	<table width="98%" border="0">
		
		<tr>
			<td>
			<table width="100%" border="1" cellpadding="0" cellspacing="0" bordercolor="black">
				
				<tr>
					
					<td valign="top" class="news">
					<div align="center">
					<table width="98%"  border="0" cellpadding="1"
						cellspacing="2" bordercolor="black">
						
						
						<tr>
							<td class="heading">
								<div id="errorMessage">
									<FONT color="red"><html:errors /></FONT>
									<FONT color="green"><html:messages id="msg"
										property="messages" message="true">
										<c:out value="${msg}" escapeXml="false"></c:out>
									</html:messages></FONT>
								</div>
							</td>
						</tr>
						

						<tr>
							<td align="center">
							<table width="98%" border="0" cellspacing="0" cellpadding="0" bordercolor="black">
								<tr>
									<td width="48%" height="21">
									
									</td>
									<td width="1%"><div align="center"></div></td>
									<td width="51%" height="45" align="left"><html:button
										property="" styleClass="formbutton"
										onclick="closeMe()">
										<bean:message key="knowledgepro.close" />
									</html:button></td>
								</tr>
							</table>
							</td>
						</tr>
						
					</table>
					</div>
					</td>
				
				</tr>
				
			</table>
			</td>
		</tr>
	</table>
</html:form>