<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<link rel="stylesheet" href="jquery/css/ui-lightness/jquery-ui-1.8.18.custom.css">
<script type="text/javascript" src="js/AC_RunActiveContent.js"></script>
<script type="text/javascript" src="js/sdmenu/sdmenu.js"></script>
<script type="text/javascript" src="js/leftSwitchMenu.js"></script>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">

<script type="text/javascript">
function cancelAction(){
	document.location.href="certificateRequest.do?method=initCertificateRequest";
}
	function saveCertCourse(){
		var smartCard=document.getElementById("smartCardNo").value;
		var  validThruYear=document.getElementById("validThruYear").value;
		var  validThruMonth=document.getElementById("validThruMonth").value;
		var msg="";
		if(validThruYear==''){
			msg=msg+"<br/>"+"Please enter Valid Thru Year";
			} else{
				if(validThruYear<=11)
					msg=msg+"<br/>"+"Please enter proper Valid Thru Year";
			}
		
		if(validThruMonth==''){
			msg=msg+"<br/>"+"Please enter Valid Thru Month";
			}else{
				if(validThruMonth>12 || validThruMonth==0)
					msg=msg+"<br/>"+"Please enter Proper Valid Thru Month";					
				}
			if(msg==''){
			if(smartCard.length==5){
				var deleteConfirm = confirm("Are you sure you want to Proceed?");
				if (deleteConfirm == true) {
					document.certificateRequestOnlineForm.submit();
				}
				
			}
			else{
				document.getElementById("errorMessages").style.display="block";
				document.getElementById("err").innerHTML="Please enter the last 5 digits of your smart card number";
			}
			}else{
				document.getElementById("errorMessages").style.display="block";
				document.getElementById("err").innerHTML=msg;
				}
	}
</script>
<html:form action="/certificateRequest">
	<html:hidden property="method" styleId="method" value="verifyStudentSmartCardForStudentLogin" />
	<html:hidden property="formName" value="certificateRequestOnlineForm" />
	<html:hidden property="pageType" value="4" />
	<table width="98%" border="0">
		<tr>
			<td></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="10"><img src="images/st_Tright_03_01.gif"></td>
					<td width="100%" background="images/st_Tcenter.gif" class="body">
					<div align="left"><strong class="boxheader">Certificate Application </strong></div>
					</td>
					<td width="10"><img src="images/st_Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="70%" border="0" align="center" cellpadding="0"
						cellspacing="0">
						<tr>
							<td colspan="6" align="left">
							<div align="right"><span class='MandatoryMark'><bean:message
								key="knowledgepro.mandatoryfields" /></span></div>
							<div id="errorMessages" class="ui-widget">
							<div class="ui-state-error ui-corner-all" style="padding: 0 .7em;">
							<p>
							<span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
							<strong>Alert:</strong>
							<span id="err"><html:errors/></span>
							</p>
							</div>
							</div>

							<div id="messages"><div class="display-info">
							<span id="msg"><html:messages id="message" property="messages" message="true"><c:out value="${message}" escapeXml="false"></c:out><br></html:messages></span>
							</div>
							</div>
							<script type="text/javascript">
								if(document.getElementById("msg")==null ||  document.getElementById("msg").innerHTML==''){
									document.getElementById("messages").style.display="none";
									}
								if(document.getElementById("err").innerHTML==''){
									document.getElementById("errorMessages").style.display="none";
									}
							</script>
							
							</td>
						</tr>
						<tr>
							<td colspan="6" align="left">
							<div align="right">
							Contact Us: 
							</div>
							</td>
						</tr>
						<tr>
							<td colspan="6">&nbsp; </td>
						</tr>
						<tr>
							<td colspan="6" class="heading">Welcome to Mount Carmel Payment Gateway Portal </td>
						</tr>
						<tr>
							<td colspan="6">&nbsp; </td>
						</tr>
						
						
						
						
						<!-- first -->
												<tr>
							<td><img src="images/01.gif" width="5" height="5" /></td>
							<td width="914" background="images/02.gif"></td>
							<td><img src="images/03.gif" width="5" height="5" /></td>
						</tr>
						
						<tr>
							<td width="5" background="images/left.gif"></td>
							<td width="100%" valign="top">
							<table width="100%" cellspacing="1" cellpadding="2" align="center">
							<tr>
									<td class="studentrow-odd" align="right"  width="50%">
										Name of the Candidate:
									</td>
									<td class="studentrow-even"  width="50%">
									<bean:write name="certificateRequestOnlineForm" property="nameOfStudent"/>
									</td>
								</tr>
								<tr>
									<td class="studentrow-odd" align="right">
										Register No:
									</td>
									<td class="studentrow-even">
									<bean:write name="certificateRequestOnlineForm" property="regNo"/>
									</td>
								</tr>
								<tr>
								<td class="studentrow-odd" align="right">
										Class Name:
									</td>
									<td class="studentrow-even">
									<bean:write name="certificateRequestOnlineForm" property="className"/>
									</td>
								</tr>
								<tr>
									<td class="studentrow-odd" align="right" width="50%" >
										Total Amount:
									</td>
									<td class="studentrow-even" width="50%" >
									<bean:write name="certificateRequestOnlineForm" property="totalFees"/>
									</td>
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
						<tr>
							<td colspan="3"> &nbsp; </td>
						</tr>
						
						<tr>
							<td><img src="images/01.gif" width="5" height="5" /></td>
							<td width="914" background="images/02.gif"></td>
							<td><img src="images/03.gif" width="5" height="5" /></td>
						</tr>
						
						<tr>
							<td width="5" background="images/left.gif"></td>
							<td width="100%" valign="top">
							<table width="100%" cellspacing="1" cellpadding="2" align="center">
								
								<tr>
									<td class="studentrow-odd" align="right" width="50%">
										<span class="Mandatory">*</span> &nbsp; Date Of Birth:
									</td>
									<td class="studentrow-even" width="50%">
									<html:text name="certificateRequestOnlineForm" property="dob" styleId="dob" size="10" maxlength="16"/>
									<script language="JavaScript">
									new tcal( {
										// form name
										'formname' :'certificateRequestOnlineForm',
										// input name
										'controlname' :'dob'
									});
									</script>
									</td>
								</tr>
								<tr>
									<td class="studentrow-odd" align="right" width="50%">
										<span class="Mandatory">*</span> &nbsp; Kindly enter the last 5 digits of your smart card number:
									</td>
									<td class="studentrow-even" width="50%">XXXXXXXXXXXXXX
										<html:text name="certificateRequestOnlineForm" property="smartCardNo" styleId="smartCardNo" size="5" maxlength="5"></html:text>									
									</td>
								</tr>
								<tr>
									<td class="studentrow-odd" align="right" width="50%">
										<span class="Mandatory">*</span> &nbsp; Valid Thru of your smart card:
									</td>
									<td class="studentrow-even" width="50%">
										<html:text name="certificateRequestOnlineForm" property="validThruMonth" styleId="validThruMonth" size="2" maxlength="2" onkeypress="return isNumberKey(event)"></html:text> /
										<html:text name="certificateRequestOnlineForm" property="validThruYear" styleId="validThruYear" size="2" maxlength="2" onkeypress="return isNumberKey(event)"></html:text>
										&nbsp;&nbsp; mm/yy
									</td>
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
					<div align="center">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr class="row-white">
                   <td colspan="2"><div align="center">
					<html:button property=""
								styleClass="btnbg" value="Proceed"
								onclick="saveCertCourse()"></html:button>	&nbsp; <html:button property=""
								styleClass="btnbg" value="Cancel"
								onclick="cancelAction()"></html:button>
					</div></td>
                 </tr>
					</table>
					</div>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					&nbsp;
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				<tr>
					<td><img src="images/st_Tright_03_05.gif" height="29" width="9"></td>
					<td background="images/st_TcenterD.gif" width="100%"></td>
					<td><img src="images/st_Tright_02.gif" height="29" width="9"></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</html:form>
<script type="text/javascript">
$(".display-info").fadeOut(800).fadeIn(800).fadeOut(400).fadeIn(400).fadeOut(400).fadeIn(400);
</script>