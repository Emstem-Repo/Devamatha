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
	function cancelAction() {
		document.location.href = "StudentLoginAction.do?method=returnHomePage";
	}
	function payOnline(){
		document.getElementById("method").value = "redirectingToPGI";
		document.loginform.submit();
	}
	function getFeeReciept(){
		var url = "StudentLoginAction.do?method=getPaymentRecieptStudent";
		myRef = window
		.open(url, "",
				"left=20,top=20,width=900,height=900,toolbar=1,resizable=0,scrollbars=1");
		
	}
	</script>
<html:form action="/StudentLoginAction" >
<html:hidden property="formName" value="loginform"/>
<html:hidden property="pageType" value="1"/>
<html:hidden property="method" styleId="method"/>

<table width="100%" border="0">
		<tr>
			<td valign="top">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/st_Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/st_Tcenter.gif" class="body"><strong
						class="boxheader">Fee Payment</strong></td>

					<td width="10"><img src="images/st_Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr height="10%">
					<td height="20" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" height="10%">
					
					<FONT color="black" size="2px">
					 <html:messages id="msg"
						property="messages" message="true">
						<c:out value="${msg}" escapeXml="false"></c:out>
						<br>
					</html:messages> </FONT>
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
							<table width="100%" cellspacing="1" cellpadding="1" border="0" >
									 <logic:empty name="loginform" property="paymentListStudent">
									 	No Records
									 </logic:empty>
									 <logic:notEmpty name="loginform" property="paymentListStudent">
										 	<tr>
											<td height="30" align="center" width="20%" class="studentrow-odd">
											<div align="center">Payment Details Of :  <bean:write name="loginform" property="studentName"/> </div>
											 </td>
											
											</tr>
												</logic:notEmpty>
									</table>
									<table width="100%" cellspacing="1" cellpadding="1" border="0" >
											<logic:iterate  name="loginform" property="paymentListStudent" id="paymentListStudent">
												<tr>
												<td height="25" width="10%" class="studentrow-even">
												<div align="center">
												<b><font color="red"><bean:write name="paymentListStudent" property="categoryName"/></b></font>
												</div>
												</td>
												</tr>
												<tr>
												<td height="25" width="10%" class="studentrow-even">
												<div align="center">
													<bean:write name="paymentListStudent" property="headingName"/>
												</div>
												</td>
												<td height="25" width="20%" class="studentrow-even">
												<div align="left">Amount :<bean:write name="paymentListStudent" property="assignedAmount"/>
													
												</div>
												</td>
												<td height="25" width="20%" class="studentrow-even">
												<div align="left">Amount Paid :<bean:write name="paymentListStudent" property="totalPaidAmt"/>
													
												</div>
												</td>
<!--												<td height="25" width="20%" class="studentrow-even">-->
<!--												<div align="left">Balance Amount :<bean:write name="paymentListStudent" property="totalBalanceAmt"/>-->
<!--													-->
<!--												</div>-->
<!--												</td>-->
												</tr>
											</logic:iterate>
										 	<tr>
										 	<td height="25" width="10%" class="studentrow-even">
												<div align="center">
													Amount to Pay:
												</div>
												</td>
											<td height="30" align="center" width="20%" class="studentrow-odd">
											<logic:equal value="true" property="isDisplayText" name="loginform">
											<div ><html:text readonly="true" property="amount" styleId="amount" name="loginform" disabled="true"/> </div>
											</logic:equal>
											<logic:equal value="false" property="isDisplayText" name="loginform">
											<div ><html:text readonly="true" property="amount" styleId="amount" name="loginform" disabled="false"/> </div>
											</logic:equal>
											 </td>
											
											</tr>
												
									
											
										</table>
									
							<table>
								<tr class="row-white">
								<td width="40%"></td><td width="25"></td>
								<logic:equal value="false" property="isSuccess" name="loginform">
                   						<td colspan="2">
                   						<div align="center">
										<html:button property="" value="Pay Online" styleClass="btnbg"  onclick="payOnline()"></html:button>
										</div></td>
										</logic:equal>
										<logic:equal value="true" property="isSuccess" name="loginform">
										<td width="8%"></td><td width="25"></td>
                   						<td colspan="2"><div align="center">
										<html:button property="" value="Fee Receipt" styleClass="btnbg"  onclick="getFeeReciept()"></html:button>
										</div></td>
										</logic:equal>
										<td width="8%"></td><td width="25"></td>
                   						<td colspan="2"><div align="center">
										<html:button property="" value="Cancel" styleClass="btnbg" onclick="cancelAction()"></html:button>
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
	<script>
	document.getElementById("amount").checked = true;
	</script>
</html:form>