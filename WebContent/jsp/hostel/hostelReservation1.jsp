<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<script type="text/javascript">
function resetFields()	{
	document.getElementById("hostelName").selectedIndex = 0;
	document.getElementById("requisitionNo").value ="";
	resetFieldAndErrMsgs();
}
</script>
<body>
<html:form action="HostelReservation" method="post">
	<html:hidden property="method" styleId="method"
		value="getApplicantDetails" />
	<html:hidden property="formName" value="hostelReservationForm" />
	<html:hidden property="pageType" value="1" />
	<table width="99%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message
				key="knowledgepro.hostel" /></span> <span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.hostel.reservation" /> &gt;&gt;</span></td>
		</tr>
		<tr>
			<td valign="top">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif"></td>
					<td colspan="2" background="images/Tcenter.gif" class="body">
					<div align="left" class="heading_white"><bean:message
						key="knowledgepro.hostel.reservation" /></div>
					</td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="20" valign="top" background="images/Tright_03_03.gif"></td>
					<td height="20" colspan="2" class="news">
					<div align="right"><FONT color="red"> <span
						class='MandatoryMark'>* Mandatory fields</span></FONT></div>
					<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
					<FONT color="green"> <html:messages id="msg"
						property="messages" message="true">
						<c:out value="${msg}" escapeXml="false"></c:out>
						<br>
					</html:messages> </FONT></div>
					</td>
					<td height="20" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>

				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td colspan="2" valign="top" class="heading">
					<table width="100%" border="0" align="center" cellpadding="0"
						cellspacing="0">
						<tr>
							<td><img src="images/01.gif" width="5" height="5" /></td>
							<td width="914" background="images/02.gif"></td>
							<td><img src="images/03.gif" width="5" height="5" /></td>
						</tr>


						<tr>
							<td width="5" background="images/left.gif"></td>
							<td valign="top">
							<table width="100%" cellspacing="1" cellpadding="2">
								<tr>
									<td width="25%" class="row-odd">
									<div align="right"><span class="Mandatory">* </span><bean:message
										key="knowledgepro.hostel.name.col" /></div>
									</td>
									<td width="25%" class="row-even"><html:select
										property="hostelId" styleClass="combo" styleId="hostelName">
										<html:option value=" ">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<html:optionsCollection name="hostelList" label="name"
											value="id" />
									</html:select></td>
									<td width="25%" class="row-odd">
									<div align="right"><span class="Mandatory">* </span><bean:message
										key="knowledgepro.hostel.reqno.view.status" /> :</div>
									</td>
									<td width="25%" class="row-even"><html:text styleId="requisitionNo"
										property="requisitionNo"></html:text></td>

								</tr>
							</table>
							</td>
							<td width="5" height="30" background="images/right.gif"></td>
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
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td colspan="2" valign="top" height="15" class="news"></td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>

				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td colspan="2" valign="top" class="news">
					<div align="center">
					<table width="100%" height="27" border="0" cellpadding="1"
						cellspacing="2">
						<tr>
							<td colspan="3">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td width="45%">
									<div align="right"><html:submit styleClass="formbutton">
										<bean:message key="knowledgepro.continue" />
									</html:submit></div>
									</td>
									<td width="2%" />
									<td width="53"><html:button property=""
										styleClass="formbutton" value="Reset" onclick="resetFields()"></html:button></td>
								</tr>
							</table>
							</td>
						</tr>
					</table>
					</div>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>

				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td colspan="2" valign="top" height="15" class="news"></td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				<tr>
					<td><img src="images/Tright_03_05.gif"></td>
					<td colspan="2" background="images/TcenterD.gif"></td>
					<td><img src="images/Tright_02.gif" width="9" height="29"></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</html:form>
</body>
<script type="text/javascript">
var print = "<c:out value='${hostelReservationForm.print}'/>";
if(print.length != 0 && print == "true") {
	var url ="HostelReservation.do?method=displayPage";
	myRef = window.open(url,"hostel_reservation_entry","left=20,top=20,width=800,height=300,toolbar=1,resizable=0,scrollbars=1");
}
</script>
