<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<script type="text/javascript">
	function resetMessages() {
		document.getElementById("invalidRegNo").innerHTML ="";
		 resetFieldAndErrMsgs();
	}	
	function cancelAction(){
		resetFieldAndErrMsgs();
		document.location.href = "LoginAction.do?method=loginAction";
	}
	function checkRegNo(){
		var registerNo=document.getElementById("registerNO").value;
		if(registerNo!=""){
			checkDupilcateOfRegNo(registerNo,updateregNo);
		}else{
			alert("Please Enter Register No ");
			}
	}
	function updateregNo(req){
		var responseObj = req.responseXML.documentElement;
		var value = responseObj.getElementsByTagName("value");
		if(value!=null){

			for ( var I = 0; I < value.length; I++) {
				if(value[I].firstChild!=null){
				var temp = value[I].firstChild.nodeValue;
				document.getElementById("invalidRegNo").innerHTML = temp;
				}
			}
		}
	}
	function printHallTicket(){
		var regNo=document.getElementById("registerNO").value;
		document.getElementById("method").value="printHallTicket";
		document.tempHallTicketOrIDCardForm.submit();
	}
	function printIDCard(){
		var regNo=document.getElementById("registerNO").value;
		document.getElementById("method").value="printIDCard";
		document.tempHallTicketOrIDCardForm.submit();
	}
</script>
<html:form action="/tempHallTicket" method="post" >
	<html:hidden property="formName" styleId="formName" value="tempHallTicketOrIDCardForm" />
	<html:hidden property="method" styleId="method" value="initTempHallTicketOrIDCard"/>
	<html:hidden property="pageType" value="1" />
	<table width="100%" border="0">
		<tr>
			<td><span class="heading"> <bean:message
				key="knowledgepro.exam" /> <span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.exam.print.hallticketOrIdCard" /> &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> <bean:message
						key="knowledgepro.exam.print.hallticketOrIdCard" /></strong></td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="122" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" cellspacing="1" cellpadding="2">
						<tr bgcolor="#FFFFFF">
							<td height="20" colspan="4">
							<div align="right"><FONT color="red"> <span
								class='MandatoryMark'><bean:message
								key="knowledgepro.mandatoryfields" /></span></FONT></div>
							<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
							<FONT color="green"> <html:messages id="msg"
								property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
							</html:messages> </FONT></div>
							<font color="red"><div id="invalidRegNo"></div></font>
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
									<table width="100%" cellspacing="1" cellpadding="2">
										<tr >
											<td class="row-odd"  width="20%"><div align="right"><span class="Mandatory">* </span><bean:message key="knowledgepro.exam.reJoin.registerNo"/></div></td>
                  							<td class="row-even"  width="20%">
                  								<html:text property="registerNO" styleId="registerNO" />
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
						<tr>
							<td height="25" colspan="6">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr><td style="height: 10px" align="center" colspan="5"></td></tr>
								<tr>
            						<td width="38%" height="35">&nbsp;</td>
            						<td width="12%"><html:submit property="" styleClass="formbutton" styleId="hallTicket" value="Print Hall Ticket" onclick="printHallTicket()"><!--
												Print Hall Ticket
											--></html:submit>
									</td>
									<td>&nbsp;&nbsp;</td>
            						<td width="10%"><html:button property="" styleClass="formbutton" styleId="hallTicket" value="Print ID Card" onclick="printIDCard()"><!--
												Print ID Card
											--></html:button>
									</td>
									<td>&nbsp;&nbsp;</td>									
									<td width="7%"><html:button property="" styleClass="formbutton" onclick="resetMessages()">
												<bean:message key="knowledgepro.admin.reset"/>
											</html:button>
									</td>
            						<td width="60%" ><html:button property="" styleClass="formbutton" onclick="cancelAction()">
												<bean:message key="knowledgepro.cancel"/>
											</html:button>
									</td>
          						</tr>								
          						<tr><td style="height: 10px" align="center" colspan="5"></td></tr>          						
							</table>
							</td>
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
var print = "<c:out value='${tempHallTicketOrIDCardForm.printHallTicketPage}'/>";
if(print.length != 0 && print == "true"){
	var url = "tempHallTicket.do?method=printHallTicketDetails";
		myRef = window
				.open(url, "printHallTicket",
						"left=20,top=20,width=900,height=900,toolbar=1,resizable=0,scrollbars=1");	
}
var print = "<c:out value='${tempHallTicketOrIDCardForm.printIDCardPage}'/>";
if(print.length != 0 && print == "true"){
	var url = "tempHallTicket.do?method=printIDCardDetails";
		myRef = window
				.open(url, "printHallTicket",
						"left=20,top=20,width=900,height=900,toolbar=1,resizable=0,scrollbars=1");	
}
</script>