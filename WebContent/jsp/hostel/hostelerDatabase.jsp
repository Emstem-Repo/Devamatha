<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>

<%@page import="com.kp.cms.constants.CMSConstants"%>
<script type="text/javascript">
function resetMessages() {
	document.getElementById("textSearch").value = "";
	document.getElementById("hostel").selectedIndex = 0;
	document.getElementById("searchBy").selectedIndex = 0;
	resetErrMsgs();
}
function submitHostelerDatabase(){
	document.getElementById("method").value = "submitHostelerDatabase";
	document.hostelerDatabaseForm.submit();
}
</script>
<html:form action="/HostelerDatabase" method="post">
	<html:hidden property="method" styleId="method" />
	<html:hidden property="formName" value="hostelerDatabaseForm" />
	<html:hidden property="pageType" value="1" />
	<table width="99%" border="0">
		<tr>
			<td><span class="heading"><bean:message
				key="knowledgepro.hostel" /> <span class="Bredcrumbs">&gt;&gt;<bean:message
				key="knowledgepro.hostel.hosteler.database" /> &gt;&gt;</span> </span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="heading_white"><bean:message
						key="knowledgepro.hostel.hosteler.database" /></td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="heading"><div id="errorMessage"><FONT color="red"><html:errors /></FONT>
							<FONT color="green"><html:messages id="msg"
							property="messages" message="true">
							<c:out value="${msg}" escapeXml="false"></c:out>
							<br>
						</html:messages> </FONT></div></td>
					<td valign="top" background="images/Tright_3_3.gif" class="news">
					</td>
				</tr>
				<tr>
					<td height="43" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" align="center" cellpadding="0"
						cellspacing="0">
						<tr>
							<td><img src="images/01.gif" width="5" height="5" /></td>
							<td width="914" background="images/02.gif">
							
							</td>
							<td><img src="images/03.gif" width="5" height="5" /></td>
						</tr>
						<tr>
							<td width="5" background="images/left.gif"></td>
							<td valign="top">
							<table width="100%" cellspacing="1" cellpadding="2">
								<tr>
									<td height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.hostel.entry.hostel.name" /></div>
									</td>
									<td height="25" class="row-even"><html:select
										property="hostelId" styleClass="combo" styleId="hostel">
										<html:option value="">
										<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<c:if test="${hostelList!=null && hostelList != ''}">									
										<html:optionsCollection name="hostelList" label="name" value="id"/>
										</c:if>
									</html:select></td>
									<td width="24%" height="25" class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.hostel.serachby.col" /></div>
									</td>
									<td width="26%" height="25" class="row-even"><html:select
										property="searchBy" styleClass="combo" styleId="searchBy">
										<html:option value=" "><bean:message key="knowledgepro.admin.select"/></html:option>
										<html:option value='<%=CMSConstants.HOSTEL_HOSTLER_DATABASE_REGNO %>'><bean:message key="knowledgepro.hostel.hostelerdatabase.regno"/></html:option>
										<html:option value='<%=CMSConstants.HOSTEL_HOSTLER_DATABASE_ROOMNO %>'><bean:message key="knowledgepro.hostel.hostelerdatabase.roomno"/></html:option>
										<html:option value='<%=CMSConstants.HOSTEL_HOSTLER_DATABASE_STAFFID %>'><bean:message key="knowledgepro.hostel.hostelerdatabase.staffid"/></html:option>
										<html:option value='<%=CMSConstants.HOSTEL_HOSTLER_DATABASE_ROOMTYPE %>'><bean:message key="knowledgepro.hostel.hostelerdatabase.roomtype"/></html:option>
									</html:select></td>
								</tr>
								<tr>
									<td width="27%" height="25" class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.hostel.text.to.search" /></div>
									</td>
									<td width="23%" height="25" class="row-even">
									<html:text property="textToSearch" styleClass="TextBox" styleId="textSearch" size="20" maxlength="20">
									</html:text>
									</td>
									<td colspan="2" class="row-even">&nbsp;</td>
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
					<td height="37" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="49%" height="35">
							<div align="right">
							<html:button property="" styleClass="formbutton" value="Submit"
										onclick="submitHostelerDatabase()"></html:button>
							</div>
							</td>
							<td width="2%"></td>
							<td width="49%">
							<div align="left"><html:button property="" styleClass="formbutton" value="Reset"
										onclick="resetMessages()"></html:button></div>
							</td>
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
</html:form>