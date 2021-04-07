<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>

<script type="text/javascript">

function Cancel(){
	document.location.href = "HostelTransaction.do?method=initHostelTransaction";
}
function Transaction(){
	document.getElementById("method").value="transactionImages";
	document.revaluationMarksUpdateForm.submit();
}
function leaveApply(){
	var reg=document.getElementById("studentRegNo").value;
	var year=document.getElementById("year").value;
	document.location.href = "hostelLeave.do?method=initHostelLeave&rgNoFromHlTransaction="+reg+"&isHlTransaction=true&academicYear="+year;
}
function disciplinary(){
	var reg=document.getElementById("studentRegNo").value;
	var year=document.getElementById("year").value;
	document.location.href = "hostelDisciplinaryDetails.do?method=initDisciplinaryDetails&rgNoFromHlTransaction="+reg+"&isHlTransaction=true&year="+year;
}	
function fineEntry(){
	var reg=document.getElementById("studentRegNo").value;
	var year=document.getElementById("year").value;
	document.location.href = "fineEntry.do?method=initFineEntry&rgNoFromHlTransaction="+reg+"&isHlTransaction=true&year="+year;
}
function visitorsInfo(){
	var reg=document.getElementById("studentRegNo").value;
	var year=document.getElementById("year").value;
	document.location.href = "hostelVisitors.do?method=initHostelVisitorsInformation&rgNoFromHlTransaction="+reg+"&isHlTransaction=true&year="+year;
}
function cancel() {
	document.location.href = "HostelTransaction.do?method=initHostelTransaction";
}
</script>
<html:form action="/HostelTransaction" method="post">
<html:hidden property="formName" value="hostelTransactionForm" />
<html:hidden property="method" styleId="method" value="getStudentDetails" />
<html:hidden property="pageType" value="1"/>
<html:hidden property="studentRegNo"	styleId="studentRegNo" name="hostelTransactionForm"/>
<html:hidden property="year"	styleId="year" name="hostelTransactionForm"/>
<table width="100%" border="0">
		<tr>
			<td><span class="Bredcrumbs"> <bean:message key="knowledgepro.hostel.adminmessage.hostel" />
			 <span class="Bredcrumbs">&gt;&gt;Hostel Transaction &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong class="boxheader"> Hostel Transaction</strong></td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9" 	height="29"></td>
				</tr>
				<tr>
					<td height="122" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" cellspacing="1" cellpadding="2">
						<tr bgcolor="#FFFFFF">
							<td height="20" colspan="2">
							<div align="right"><FONT color="red"> <span
								class='MandatoryMark'><bean:message
								key="knowledgepro.mandatoryfields" /></span></FONT></div>
							<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
							<FONT color="green"> <html:messages id="msg"
								property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
								<br>
							</html:messages> </FONT></div>
							</td>
						</tr>


						<tr>
							<td valign="top" class="news">
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
									<table width="100%" cellspacing="0" cellpadding="0" >
									<tr>
									<td valign="top" class="news">
										<table width="100%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													 <td width="16%" height="25" align="center">
									            <div  align="center"><p style="font-weight:bold;">
										           <bean:message key="knowledgepro.Hostel.transaction.leave"/></p></div>
								            </td>
											<td width="16%" height="25" align="center">
												<div  align="center"><p style="font-weight:bold;">
												<bean:message key="knowledgepro.Hostel.transaction.Remarks" /></p></div>
											</td>
											</tr>
										</table>
									</td>
									</tr>
									<tr>
									<td valign="top">
									<table width="100%" cellspacing="0" cellpadding="0" >
									<tr>
									<td width="30%" height="25" align="center" >
										<div align="center"><img src="images/leaveApply.jpg"
											width=160" height="60" style="cursor:pointer" 
											onclick="leaveApply()" /></div>
									</td>
									<!--<td width="30%" height="25" align="center" >
										<div align="center"><img src="images/disciplinaryImg2.jpg"
											width="80" height="80" style="cursor:pointer" 
											onclick="disciplinary()" /></div>
									</td>
									-->
									<td width="30%" height="25" align="center" >
										<div align="center"><img src="images/RemarksImages.jpg"
											width=140" height="80" style="cursor:pointer" 
											onclick="remsrks()" /></div>
									</td>
									</tr>
										</table>
									</td>
									</tr>
									<tr>
									<td>&nbsp;&nbsp;
									</td>
									<td>&nbsp;&nbsp;
									</td>
									</tr>
									<tr>
									<td>&nbsp;&nbsp;
									</td>
									<td>&nbsp;&nbsp;
									</td>
									</tr>
									<tr>
									<td>&nbsp;&nbsp;
									</td>
									<td>&nbsp;&nbsp;
									</td>
									</tr>
									<tr>
									<td valign="top" class="news">
										<table width="100%" border="0" cellspacing="0" cellpadding="0">
												<tr>
									
											<td width="16%" height="20" align="center">
												<div  align="center"  ><p style="font-weight:bold;">
												<bean:message key="knowledgepro.Hostel.transaction.Fine" /></p></div>
											</td>
											<td width="16%" height="20" align="center">
												<div align="center"><p style="font-weight:bold;">
												<bean:message key="knowledgepro.Hostel.transaction.VisitorsInfo" /></p></div>
											</td>
											</tr>
										</table>
									</td>
									</tr>
									<tr>
									<td valign="top">
									<table width="100%" cellspacing="0" cellpadding="0" >
									<tr>
									<td width="30%" height="20" align="center"  >
										<div align="center"><img src="images/fineMoney.jpg"
											width="80" height="100" style="cursor:pointer" 
											onclick="fineEntry()" /></div>
									</td>
									<td width="30%" height="20" align="center" >
										<div align="center"><img src="images/visitorInformation1.jpg"
											width="170" height="70" style="cursor:pointer" 
											onclick="visitorsInfo()" /></div>
									</td>
									</tr>
										</table>
									</td>
									</tr>
									<tr>
									<td>
									<div align="center">
									<html:button property=""
										styleClass="formbutton" value="Close" onclick="cancel()">
										</html:button>
									</div>
									</td>
									</tr>
									</table>
									</td>
									<td width="5" height="29" background="images/right.gif"></td>
								</tr>
								<tr>
									<td height="5"><img src="images/04.gif" width="5"
										height="5" /></td>
									<td background="images/05.gif"></td>
									<td><img src="images/06.gif" /></td>
								</tr>
							</table>
							</td>
							<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
						</tr>
					</table>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td height="20" valign="top"></td>
					<td width="13" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				<tr>
					<td><img src="images/Tright_03_05.gif" width="9" height="29" /></td>
					<td width="100%" background="images/TcenterD.gif"></td>
					<td><img src="images/Tright_02.gif" width="9" height="29" /></td>
				</tr>

			</table>
			</td>
		</tr>
	</table>
</html:form>
<script type="text/javascript">
	var yearId = document.getElementById("tempyear").value;
	if (yearId != null && yearId.length != 0) {
		document.getElementById("year").value = yearId;
	}
</script>