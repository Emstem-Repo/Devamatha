<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@page import="java.util.Map,java.util.HashMap"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<script src="jquery/js/jquery-1.9.0.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="jquery.confirm/jquery.confirm/jquery.confirm.css" />
    <script src="jquery.confirm/jquery.confirm/jquery.confirm.js"></script>
<script type="text/javascript">
$(document).ready(function() {
	  $('#Submit').click(function(){
	       var contactNo = $('#emergencyContactNo').val();
	       var vehicleNo = $('#vehicleNo').val();
	       var modelVehicle = $('#modelOfVehicle').val();
	       if(contactNo== '' && vehicleNo=='' && modelVehicle==''){
	    	   $('#errorMessage').slideDown().html("<span>Please enter Model of Vehicle <br> Vehicle No required <br> Emergency Contact Number Required </span>");
	          return false;
	        }
	       else if(contactNo== ''){
	        	 $('#errorMessage').slideDown().html("<span>Emergency Contact number is Required.</span>");
	           return false;
	        }

	       else if(vehicleNo== ''){
	        	 $('#errorMessage').slideDown().html("<span>Vehicle Number is Required.</span>");
	            return false;
	        }
	       else if(modelVehicle== ''){
	        	 $('#errorMessage').slideDown().html("<span>Model of Vehicle is Required.</span>");
	            return false;
	        }
	       else{
	    	   var checkDisabledtext = $('#checkDisableText').val();
		       if(checkDisabledtext=='true'){
		    	   var contactNo=document.getElementById('emergencyContactNo').value;
		   	       var vehicleNo=document.getElementById('vehicleNo').value;
		   	       var modelVehicle=document.getElementById('modelOfVehicle').value;
		   	       document.location.href="studentCarPass.do?method=saveStudentCarDetailsAndPrint&emergencyContactNo="+contactNo+"&vehicleNo="+vehicleNo+"&modelOfVehicle="+modelVehicle+"&checkDisableText="+checkDisabledtext;
		       }else{
		    	   $.confirm({
						'message'	: 'Please Verify the Details.<br> The vehicles details cannot be edited after submission.<br> Click <b>OK</b> to Proceed with the submission.<br> <b>Cancel</b> to modify the Vehicle details. ',
						'buttons'	: {
							'Ok'	: {
								'class'	: 'blue',
								'action': function(){
									$.confirm.hide();
									   var contactNo=document.getElementById('emergencyContactNo').value;
							   	       var vehicleNo=document.getElementById('vehicleNo').value;
							   	       var modelVehicle=document.getElementById('modelOfVehicle').value;
							   	       document.location.href="studentCarPass.do?method=saveStudentCarDetailsAndPrint&emergencyContactNo="+contactNo+"&vehicleNo="+vehicleNo+"&modelOfVehicle="+modelVehicle;
								}
							},
		    	       'Cancel'	:  {
								'class'	: 'gray',
								'action': function(){
									$.confirm.hide();
								}
							}
						}
					});
			       }
	       }
	      });
	});	
function goToHomePage() {
	document.location.href = "StudentLoginAction.do?method=returnHomePage";
}

</script>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html:form action="/studentCarPass">
	<html:hidden property="formName" value="studentCarPassForm" />
	<html:hidden property="pageType" value="1" />
	<html:hidden property="method" styleId="method" value="" />
	<html:hidden property="checkDisableText" styleId="checkDisableText" name="studentCarPassForm" />
	<table width="98%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message
				key="knowledgepro.admin.studentlogin" /> <span class="Bredcrumbs">&gt;&gt;
			Car Pass &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="10"><img src="images/st_Tright_03_01.gif"></td>
					<td width="100%" background="images/st_Tcenter.gif" class="body">
					<div align="left"><strong class="boxheader">Car Pass</strong></div>
					</td>
					<td width="10"><img src="images/st_Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
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
							<td width="100%" valign="top">
							<table width="100%" cellspacing="1" cellpadding="2"
								class="row-white">
								<tr>
									<td align="left">
									<div id="err"
										style="color: red; font-family: arial; font-size: 11px;"></div>
									<div id="errorMessage"
										style="color: red; font-family: arial; font-size: 11px;">
									<p><span id="err"><html:errors /></span></p>
									<FONT color="green"> <html:messages id="msg"
										property="messages" message="true">
										<c:out value="${msg}" escapeXml="false"></c:out>
										<br>
									</html:messages> </FONT></div>
									</td>
								</tr>
							</table>
							</td>
							<td width="5" height="30" background="images/st_right.gif"></td>
						</tr>

						<tr>
							<td width="5" background="images/st_left.gif"></td>
							<td width="100%" valign="top">
							<table width="100%" cellspacing="1" cellpadding="2"
								class="row-white">
								<tr>
									<td colspan="2">
									<table width="100%" cellpadding="2" cellspacing="1">

										<tr height="21px">
											<td height="20" width="50%" class="studentrow-odd"
												align="left"><span class="Mandatory">*</span>Model of
											Vehicle</td>
											<td height="20" width="50%" class="studentrow-even"
												align="left"><html:text
												property="modelOfVehicle" name="studentCarPassForm"
												size="30" maxlength="50" styleId="modelOfVehicle">
											</html:text></td>

										</tr>
										<tr>
											<td height="20" class="studentrow-odd" width="50%"
												align="left">
											<span class="Mandatory">*</span>Vehicle
											No
											</td>
											<td height="25" class="studentrow-even" width="50%"><html:text
												property="vehicleNo" name="studentCarPassForm" size="30"
												maxlength="50" styleId="vehicleNo">
											</html:text></td>
										</tr>
										<tr>
											<td height="20" class="studentrow-odd" width="50%">
											<div align="left"><span class="Mandatory">*</span>Emergency
											Contact No.</div>
											</td>
											<td height="25" class="studentrow-even" width="50%"><html:text
												property="emergencyContactNo" name="studentCarPassForm"
												size="30" maxlength="10"
												onkeypress="return isNumberKey(event)" styleId="emergencyContactNo">
											</html:text></td>
										</tr>
									</table>
									<table width="100%">
									    <tr height="5"></tr>
									    <tr><td width="100%"><font color="red" size="2"><b>** Only Day Scholars are eligible to apply for the Passes**</b></font></td></tr>
									    <tr height="5"></tr>
										<tr><td width="100%" colspan="2" class="heading">N.B :</td></tr>
										<tr><td width="100%" colspan="2" class="heading">1) Only Vehicles registered with University  will be allowed to park in the Campus.</td></tr>
										<tr><td width="100%" colspan="2" class="heading">2) Car Pass validity is from June 2013 to March 2014 only. </td></tr>
										<tr><td width="100%" colspan="2" class="heading">3) Amount for the pass is Rs. 7500/-.</td></tr>
										<tr><td width="100%" colspan="2" class="heading">4) Kindly print this form and submit to IPM with the required signature while collecting the pass.</td></tr>
										<tr><td width="100%" colspan="2" class="heading">5) Passes can be collected within one working day after the online submission of the form.</td></tr>
										<tr><td width="100%" colspan="2" class="heading">6) The amount can be remitted while collecting the pass.</td></tr>
									</table>
									</td>
								</tr>
								<tr>
								</tr>
							</table>

							</td>
							<td width="5" height="30" background="images/st_right.gif"></td>
						</tr>
						<tr>
							<td height="5"><img src="images/st_04.gif" width="5"
								height="5" /></td>
							<td background="images/st_05.gif"></td>
							<td><img src="images/st_06.gif" /></td>
						</tr>
					</table>
					<div align="center">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="46%" height="35">
							<div align="right"></div>
							</td>
							<td width="2%"></td>
							<td width="52%" align="left"><html:button property=""
								styleClass="btnbg" value="Submit and Print"
								onclick="saveStduentCarDetails()" styleId="Submit"></html:button> <html:button
								property="" styleClass="btnbg" value="Close"
								onclick="goToHomePage()"></html:button></td>
						</tr>
					</table>
					</div>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				<tr>
					<td><img src="images/st_Tright_03_05.gif" height="29"
						width="9"></td>
					<td background="images/st_TcenterD.gif" width="100%"></td>
					<td><img src="images/st_Tright_02.gif" height="29" width="9"></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</html:form>
<script>
var modelVehicle=document.getElementById('modelOfVehicle').value;
if(modelVehicle!=null && modelVehicle!=''){
	document.getElementById('modelOfVehicle').disabled = true;
}else{
	document.getElementById('modelOfVehicle').disabled = false;
}
var contactNo=document.getElementById('emergencyContactNo').value;
if(contactNo!=null && contactNo!=''){
	document.getElementById('emergencyContactNo').disabled = true;
}else{
	document.getElementById('emergencyContactNo').disabled = false;
}
var vehicleNo=document.getElementById('vehicleNo').value;
if(vehicleNo!=null && vehicleNo!=''){
	document.getElementById('vehicleNo').disabled = true;
}else{
	document.getElementById('vehicleNo').disabled = false;
}
</script>