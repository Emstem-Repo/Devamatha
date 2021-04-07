<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>

<script type="text/javascript">

function getAdmission() {
	document.location.href = "dataMigration.do?method=dataMigrationReport";
}
function getAttendance() {
	document.location.href = "attnDataMigrationReport.do?method=initAttnDataMigrationReport";
}
function getPettyCash(){
	document.location.href = "pettyCashAccHeads.do?method=initPettyCash";
}
function getFee() {
	document.location.href = "feeMainDetailsReport.do?method=initFeeMainDetails";
}
function getExam(){
	document.location.href = "promotemarks.do?method=initExam";
}
  </script>
<html:form action="/dataMigration" method="post">
<html:hidden property="formName" value="dataMigrationForm" />
<table width="99%" border="0"> 
  <tr>
    <td><span class="heading">Data Migration&gt;&gt;<span class="Bredcrumbs">Data Migration Report&gt;&gt;</span> </span></td>

  </tr>
 
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="heading_white" >Data Migration Report</td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td class="heading">&nbsp;</td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="99" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="100%">
        	<tr bgcolor="#FFFFFF">
					<td height="20" colspan="4">
					<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
					<FONT color="green"> <html:messages id="msg" property="messages" message="true">
						<c:out value="${msg}" escapeXml="false"></c:out>
					</html:messages> </FONT></div>
					</td>
			</tr>
			<tr>
							<td colspan="2" class="heading">
							<table width="99%" border="0" align="center" cellpadding="0"
								cellspacing="0">
								<tr>
									<td><img src="images/01.gif" width="5" height="5"></td>
									<td width="914" background="images/02.gif"></td>
									<td><img src="images/03.gif" width="5" height="5"></td>
								</tr>
								<tr>
									<td width="5" background="images/left.gif"></td>
									<td valign="top">
									<table width="100%" border="0" cellpadding="0" cellspacing="1">
									<tr>
											<td align="center" class="row-even"><p><a href="#"  onclick="getAdmission()"><b>Admission</b></a></p></td>
									</tr>
									<tr>
											<td align="center" class="row-odd"><p><a href="#"  onclick="getAttendance()"><b>Attendance</b></a></p></td>
									</tr>
									<tr>
									<td align="center" class="row-even"><p><a href="#"  onclick="getPettyCash()"><b>PettyCash</b></a></p></td></tr>
									<tr>
											<td align="center" class="row-odd"><p><a href="#"  onclick="getFee()"><b>Fee</b></a></p></td>
									</tr>
									<tr>
											<td align="center" class="row-odd"><p><a href="#"  onclick="getExam()"><b>Promotional Exam</b></a></p></td>
									</tr>
									</table>
									</td>
									<td background="images/right.gif" width="5" height="22"></td>
								</tr>
								<tr>
									<td height="5"><img src="images/04.gif" width="5"
										height="5"></td>
									<td background="images/05.gif"></td>
									<td><img src="images/06.gif"></td>
								</tr>
							</table>
							</td>
						</tr>
			<tr>
					
			 </tr>
        </table>
		</td>
        <td width="10" valign="top" background="images/Tright_3_3.gif" class="news"></td>
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
