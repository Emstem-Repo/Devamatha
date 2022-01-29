<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<link rel="stylesheet" href="jquery/css/ui-lightness/jquery-ui-1.8.18.custom.css">
<link rel="stylesheet" type="text/css " href="css/custom-button.css">
<script type="text/javascript" src="js/AC_RunActiveContent.js"></script>
<script type="text/javascript" src="js/sdmenu/sdmenu.js"></script>
<script type="text/javascript" src="js/leftSwitchMenu.js"></script>
<script language="JavaScript" src="js/calendar_us.js"></script>
<script src="jquery/js/jquery-ui-1.10.0.custom.min.js" type="text/javascript"></script>
<link rel="stylesheet" href="css/calendar.css">
<style type="text/css">
body {
	margin-left: 0px;
	margin-top: 5px;
	margin-right: 0px;
	margin-bottom: 0px;
}
</style>
<style type="text/css">
.ui-datepicker {
	background: #6B8E23;
	border: 1px solid #555;
	color: #6B8E23;
	font-family: Arial;
	font-size: 11px;
	margin-left: 10px
}
</style>
<script type="text/javascript">
function cancelAction(){
	document.location.href="examRegistrationDetails.do?method=initExamRegistrationDetails";
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
					document.examRegDetailsForm.submit();
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
<html:form action="/examRegistrationDetails" method="post">
	<html:hidden property="method" styleId="method" value="verifySmartCardDetails" />
	<html:hidden property="formName" value="examRegDetailsForm" />
	<table width="98%" border="0">
		<tr>
			<td></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
					<td width="9"><img src="images/st_Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/st_Tcenter.gif" class="body"><strong
						class="boxheader">SAP Exam Registration </strong></td>

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
							Contact Us: support@christuniversity.in
							</div>
							<div align="right">or</div>
							<div align="right">
							Office of Admissions: Ground Floor,Central Block
							</div>
							<div align="right">
							(9.00am to 5.00pm)
							</div>
							</td>
						</tr>
						<tr>
							<td colspan="6">&nbsp; </td>
						</tr>
						<tr>
							<td colspan="6" class="heading">Welcome to Christ University Payment Gateway Portal </td>
						</tr>
						<tr>
							<td colspan="6">&nbsp; </td>
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
							<td width="100%" valign="top" >
							<table width="100%" cellspacing="1" cellpadding="2" align="center">
								
									<tr height="20">
											<td class="studentrow-odd" align="right" width="50%" height="25" style="text-align: right;pointer-events: none; cursor: default;color: #003399;">
											 &nbsp;Name of the Candidate:
											</td>
											<td class="studentrow-even"  width="50%" style="text-align: left;pointer-events: none; cursor: default;">
												<bean:write name="examRegDetailsForm" property="nameOfStudent"/>
											</td>
									</tr>
									<tr >
										<td class="studentrow-odd" align="right" width="50%" height="25" style="text-align: right;pointer-events: none; cursor: default;color: #003399;">
										  &nbsp;Register No:
										</td>
										<td class="studentrow-even" style="text-align: left;pointer-events: none; cursor: default;">
											<bean:write name="examRegDetailsForm" property="regNo"/>
										</td>
									</tr>
									<tr >
											<td class="studentrow-odd" align="right" width="50%" height="25" style="text-align: right;pointer-events: none; cursor: default;color: #003399;">
											 &nbsp; Class Name:
											</td>
											<td class="studentrow-even" style="text-align: left;pointer-events: none; cursor: default;">
										<bean:write name="examRegDetailsForm" property="className"/>
										</td>
									</tr>
									<tr >
											<td class="studentrow-odd" align="right" width="50%" height="25" style="text-align: right;pointer-events: none; cursor: default;color: #003399;">
											&nbsp;Campus :
											</td>
											<td class="studentrow-even" width="50%" style="text-align: left;pointer-events: none; cursor: default;">
									<bean:write name="examRegDetailsForm" property="workLocationName"/>
									</td>
									</tr>
								<tr >
											<td class="studentrow-odd" align="right" width="50%" height="25" style="text-align: right;pointer-events: none; cursor: default;color: #003399;">
											&nbsp;Venue :
											</td>
											<td class="studentrow-even" width="50%" style="text-align: left;pointer-events: none; cursor: default;">
									<bean:write name="examRegDetailsForm" property="venueName"/>
									</td>
									</tr>
									<tr >
											<td class="studentrow-odd" align="right" width="50%" height="25" style="text-align: right;pointer-events: none; cursor: default;color: #003399;">
											&nbsp;Date & Time :
											</td>
											<td class="studentrow-even" width="50%" style="text-align: left;pointer-events: none; cursor: default;">
									<bean:write name="examRegDetailsForm" property="examDate"/>&nbsp;&nbsp;(
									<bean:write name="examRegDetailsForm" property="sessionName"/>)
									</td>
									</tr>
								<tr >
											<td class="studentrow-odd" align="right" width="50%" height="25" style="text-align: right;pointer-events: none; cursor: default;color: #003399;">
											&nbsp;Fee Amount:
											</td>
											<td class="studentrow-even" width="50%" style="text-align: left;pointer-events: none; cursor: default;">
									<bean:write name="examRegDetailsForm" property="feeAmount"/>
									</td>
									</tr>
								<tr height="25%">
									<td class="studentrow-odd" align="right" width="50%" height="25" style="text-align: right;pointer-events: none; cursor: default;color: #003399;">
										<span class="Mandatory">*</span> &nbsp; Date Of Birth:
									</td>
									<td class="studentrow-even" width="50%" style="text-align: left;">
									<html:text name="examRegDetailsForm" property="dob" styleId="dob" size="10" maxlength="16"/>
									<script language="JavaScript">
									$(function(){
										 var pickerOpts = {
												 	            dateFormat:"dd/mm/yy"
												         };  
										  $.datepicker.setDefaults(
										    $.extend($.datepicker.regional[""])
										  );
										  $("#dob").datepicker(pickerOpts);
										});
									</script>
									</td>
								</tr>
								<tr height="25%">
									<td class="studentrow-odd" align="right" width="50%" height="25" style="text-align: right;pointer-events: none; cursor: default;color: #003399;">
										<span class="Mandatory">*</span> &nbsp; Kindly enter the last 5 digits of your smart card number:
									</td>
									<td class="studentrow-even" width="50%" style="text-align: left;">XXXXXXXXXXXXXX
										<html:text name="examRegDetailsForm" property="smartCardNo" styleId="smartCardNo" size="5" maxlength="5"></html:text>									
									</td>
								</tr>
								<tr height="25%">
									<td class="studentrow-odd" align="right" width="50%" height="25" style="text-align: right;pointer-events: none; cursor: default;color: #003399;">
										<span class="Mandatory">*</span> &nbsp; Valid Thru of your smart card:
									</td>
									<td class="studentrow-even" width="50%" style="text-align: left;">
										<html:text name="examRegDetailsForm" property="validThruMonth" styleId="validThruMonth" size="2" maxlength="2" onkeypress="return isNumberKey(event)"></html:text> /
										<html:text name="examRegDetailsForm" property="validThruYear" styleId="validThruYear" size="2" maxlength="2" onkeypress="return isNumberKey(event)"></html:text>
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
					<table width="100%" border="0" cellspacing="0" cellpadding="0" height="20">
					<tr>
							<td height="15"><img src="images/04.gif" width="5" height="5" /></td>
							<td background="images/05.gif"></td>
							<td><img src="images/06.gif" /></td>
						</tr>
					<tr class="row-white" height="20">
                   <td colspan="2"><div align="center">
					<html:button property=""
								styleClass="buttom" value="Proceed"
								onclick="saveCertCourse()"></html:button>	&nbsp; <html:button property=""
								styleClass="buttom" value="Cancel"
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
</html:form>
<script type="text/javascript">
$(".display-info").fadeOut(800).fadeIn(800).fadeOut(400).fadeIn(400).fadeOut(400).fadeIn(400);
</script>