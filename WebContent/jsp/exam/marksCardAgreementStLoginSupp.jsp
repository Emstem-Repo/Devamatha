<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
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
	<script>
	function goToHomePage() {
		document.location.href = "StudentLoginAction.do?method=initMarksCard";
	}
	function openReport(){
		var v=<%=session.getAttribute("birtUg") %>;
		if(v==false){
		document.location.href = "birtFeeReport.do?method=pgMarksCardForStudent&reportName=PGMarksCardReport&ExamName=<%= session.getAttribute("examIDForMCard")%>&Exam Class=<%= session.getAttribute("marksCardClassId")%>&Semester=<%= session.getAttribute("semesterYearNo")%>&Start RegisterNo=<%= session.getAttribute("stuRegNo")%>&End RegisterNo=<%= session.getAttribute("stuRegNo")%>";
		}else{
			document.location.href = "birtFeeReport.do?method=pgMarksCardForStudent&reportName=UGMarksCardReport&ExamName=<%= session.getAttribute("examIDForMCard")%>&Exam Class=<%= session.getAttribute("marksCardClassId")%>&Semester=<%= session.getAttribute("semesterYearNo")%>&Start RegisterNo=<%= session.getAttribute("stuRegNo")%>&End RegisterNo=<%= session.getAttribute("stuRegNo")%>";
			}
	}
	</script>
<html:form action="/StudentLoginAction" >
<html:hidden property="method" value="SupplementaryMarksCard"/>
<html:hidden property="formName" value="loginform"/>
<html:hidden property="pageType" value="1"/>

<table width="930" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td width="172">&nbsp;</td>
    <td width="37">&nbsp;</td>
    <td width="269">&nbsp;</td>
    <td width="30">&nbsp;</td>
    <td width="422">&nbsp;</td>
  </tr>
  <tr>
    <td valign="top"><table width="100" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td>&nbsp;</td>
        </tr>
        <tr>
          <td>&nbsp;</td>
        </tr>
        <tr>
          <td>&nbsp;</td>
        </tr>
        <tr>
          <td>&nbsp;</td>
        </tr>
        <tr>
          <td>&nbsp;</td>
        </tr>
        <tr>
          <td>&nbsp;</td>
        </tr>
        <tr>
          <td height="30" valign="bottom"><p>&nbsp;</p>
          <p>&nbsp;</p>
          <p><img src="bullet_imge.gif" width="170" height="8" /></p></td>
        </tr>
      </table></td>
    <td width="37"><p>&nbsp;</p>
    <p>&nbsp;</p>
    <p>&nbsp;</p></td>
    <td colspan="3" valign="top">
    <table width="100%" border="0">
		<tr>
			<td valign="top">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/st_Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/st_Tcenter.gif" class="body"><strong
						class="boxheader">Agreement</strong></td>

					<td width="10"><img src="images/st_Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="20" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<div align="right"><FONT color="red"> </FONT></div>
					<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
					<FONT color="green"> <html:messages id="msg"
						property="messages" message="true">
						<c:out value="${msg}" escapeXml="false"></c:out>
						<br>
					</html:messages> </FONT></div>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				
				
				<tr>
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
									
									<td width="10%" height="25" align="center"  colspan="5">
									 <table width="100%" cellspacing="0" cellpadding="0" border="0" >
										 <tr>
										<td  valign="top" align="left">
										<FONT face="Verdana" size="1"><c:out value="${marksCardAgreement}" escapeXml="false"></c:out></FONT>
										</td>
										</tr>
									</table>
									</td>
									
									
								</tr>
								<tr>
						        	<td width="48%" height="35"><div align="right">
						           		<html:submit property=""  styleClass="formbutton" value="Accept"></html:submit>
						            </div></td>
						         	<td width="51%"><div align="left">
						            	<html:button property=""  styleClass="formbutton" value="Close" onclick="goToHomePage()"></html:button>
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
			</td>

		</tr>
	</table>
    <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" background="bg_img.gif">
  <tr>
    <td height="32" align="center" class="copyright">Copyrights @ 2009 Knowledge Pro All rights reserved. </td>
  </tr>
</table>
</td>
</tr>
</table>

</html:form>
