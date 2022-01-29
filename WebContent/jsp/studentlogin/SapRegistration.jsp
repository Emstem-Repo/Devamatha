<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<link rel="stylesheet" type="text/css" href="css/styles.css"/>
	<link rel="stylesheet" type="text/css" href="css/sdmenu.css"/>
    <script type="text/javascript" src="js/AC_RunActiveContent.js"></script>
    <script type="text/javascript" src="js/sdmenu/sdmenu.js"></script>
    <script type="text/javascript" src="js/ajax/Ajax.js"></script>
    <script type="text/javascript" src="js/ajax/AjaxUtil.js"></script>
    <script type="text/javascript" src="js/leftSwitchMenu.js"></script>
	<script type="text/javascript" src="js/common.js"></script>
	<script>
	function cancelAction() {
		document.location.href = "StudentLoginAction.do?method=returnHomePage";
	}
	
	</script>
<html:form action="/StudentLoginAction">
	<html:hidden property="formName" value="loginform" />
	<html:hidden property="pageType" value="20" />
	<html:hidden property="method" styleId="method" value="saveSapRegistration" />
	
	<table width="90%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/st_Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/st_Tcenter.gif" class="body"><strong
						class="boxheader">SAP-e Academy Course</strong></td>

					<td width="10"><img src="images/st_Tright_1_01.gif" width="9"
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
						<tr><td colspan="2">
							<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
							<FONT color="green" style="font-family: Verdana, Arial, Helvetica, sans-serif;font-size: 12px;font-weight: bold;"><B><html:messages id="msg"
								property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
								<br>
							</html:messages></B></FONT></div>
             </td>
						</tr>
						<tr>
							<td><img src="images/st_01.gif" width="5" height="5" /></td>
							<td width="914" background="images/st_02.gif"></td>

							<td><img src="images/st_03.gif" width="5" height="5" /></td>
						</tr>
						<tr>
							<td width="5" background="images/st_left.gif"></td>
							<td valign="top">
							<logic:equal value="true" property="sapRegExist" name="loginform">
								<table width="100%" cellspacing="1" cellpadding="2">
								<tr>
									<td width="100%" class="boxheader" align="center">
									<FONT color="red">You have Already Registered.</FONT>
									</td>
								</tr>
								<tr class="row-white">
			                  		 <td colspan="2"><div align="center">
										<html:button property="" value="Close" styleClass="btnbg" onclick="cancelAction()"></html:button>
									</div></td>
			                 	</tr>
								</table>								
							</logic:equal>
							<logic:equal value="false" property="sapRegExist" name="loginform">
							
							<table width="100%" cellspacing="1" cellpadding="2">
								<tr>
									<td width="100%" colspan="4">
									<table>
										<tr> 
										<td width="10%"></td>
										<td width="90%" class="heading">
										<BR/>
										<BR/>
										    To proceed with registration , you must have a laptop which is configured in Christ University Network
										<BR/>
										
										<BR/>
										
										</td></tr>
										
										
									</table>
									</td>
								</tr>
								
								 
                  	<tr class="row-white">
                  		 <td colspan="2"><div align="center">
							<html:submit value="Register" styleClass="btnbg" styleId="buttonId"></html:submit>&nbsp; <html:button property="" value="Cancel" styleClass="btnbg" onclick="cancelAction()"></html:button>
						</div></td>
                 	</tr>
                 </table>
                 </logic:equal>
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
