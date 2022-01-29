<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<link rel="stylesheet" type="text/css" href="css/styles.css"/>
	<link rel="stylesheet" type="text/css" href="css/sdmenu.css"/>
    <script type="text/javascript" src="js/AC_RunActiveContent.js"></script>
    <script type="text/javascript" src="js/sdmenu/sdmenu.js"></script>
    <script type="text/javascript" src="js/ajax/Ajax.js"></script>
    <script type="text/javascript" src="js/ajax/AjaxUtil.js"></script>
    <script type="text/javascript" src="js/leftSwitchMenu.js"></script>
	<script type="text/javascript" src="js/common.js"></script>
	
	
	
	<script type="text/javascript">
	function cancelAction()
	{
		document.location.href = "StudentLoginAction.do?method=returnHomePage";
	}
	</script>
<html:form action="/ExtraCocurricularLeaveEntry" >

<html:hidden property="formName" value="extraCocurricularLeaveEntryForm"/>
<html:hidden property="pageType" value="2"/>
<html:hidden property="method" styleId="method" value="initExtraCocurricularLeaveEntry"/>

<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    
  </tr>
  <tr>
    <td valign="top"></td>
   
    
    <td colspan="3" valign="top">
    <table width="100%" border="0">
		<tr>
			<td valign="top">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/st_Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/st_Tcenter.gif" class="body"><strong
						class="boxheader"><bean:message key="knowledgepro.attendance.studentLogin.extra.curricular.leave.entry"/></strong></td>

					<td width="10"><img src="images/st_Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
			
				
				
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" align="center" cellpadding="0"
						cellspacing="0">
					
						<tr>
							
							<td valign="top">
							
							
							
							
							
							<table width="100%" align="center" cellpadding="0" cellspacing="0">
	                     <tr>
	                       <td ><img src="images/st_01.gif" width="5" height="5" /></td>
	                       <td width="914" background="images/st_02.gif"></td>
	                       <td><img src="images/st_03.gif" width="5" height="5" /></td>
	                     </tr>
	                     <tr>
	                       <td width="5"  background="images/st_left.gif"></td>
	                       <td valign="top">
	                      <table width="100%">
							<tr>
							<td><div align="center">
							
								<div id="errorMessage" align="center">
	                       			<FONT color="green" size="3">
									<html:messages id="msg" property="messages" message="true">
									<c:out value="${msg}" escapeXml="false"></c:out><br>
									</html:messages>
						  			</FONT></div>
							
							</div></td>
							
							</tr>
							</table>
	                       </td>
	                       <td width="5" height="30"  background="images/st_right.gif"></td>
	                     </tr>
                     <tr>
                       <td height="5"><img src="images/st_04.gif" width="5" height="5" /></td>
                       <td background="images/st_05.gif"></td>
                       <td><img src="images/st_06.gif" /></td>
                     </tr>
                     
                   </table>
							
							
						
							<table align="center" width="100%">
									<tr class="row-white">
										<td ><div align="right" >
										<html:submit property="" value="Go Back" styleClass="btnbg" ></html:submit>
										</div></td>
                   						<td ><div align="left">
										<html:button property="" value="Home" styleClass="btnbg" onclick="cancelAction()"></html:button>
										</div></td>
                 					</tr>
							</table>
							</td>
							
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
			</td>

		</tr>
	</table>
   
</td>
</tr>
</table>

</html:form>
