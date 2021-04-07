<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<link rel="stylesheet" type="text/css" href="css/styles.css"/>
	<link rel="stylesheet" type="text/css" href="css/sdmenu.css"/>
    <script type="text/javascript" src="js/AC_RunActiveContent.js"></script>
    <script type="text/javascript" src="js/sdmenu/sdmenu.js"></script>
    <script type="text/javascript" src="js/ajax/Ajax.js"></script>
    <script type="text/javascript" src="js/ajax/AjaxUtil.js"></script>
    <script type="text/javascript" src="js/leftSwitchMenu.js"></script>
	<script type="text/javascript" src="js/common.js"></script>
<link type="text/css"
	href="jquery/css/ui-lightness/jquery-ui-1.8.18.custom.css" rel="stylesheet" />
<script src="jquery/js/jquery-ui-1.10.0.custom.min.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="jquery.confirm/jquery.confirm/jquery.confirm.css" />
<script src="jquery.confirm/jquery.confirm/jquery.confirm.js"></script>
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
	background: #65A6C5;
	border: 1px solid #555;
	color: #0EB4EE;
	font-family: Arial;
	font-size: 11px;
	margin-left: 10px
}
</style>
	<script>
	$(document).ready(function() {
		$('#leaveFromId').bind("cut copy paste",function(e){
			 e.preventDefault();
		});
		$('#leaveToId').bind("cut copy paste",function(e){
			 e.preventDefault();
		});  

		 $('#buttonId').click(function(e){
			 var leaveFromId=  $('#leaveFromId').val();
			 var leaveToId=  $('#leaveToId').val();
			 if(leaveFromId=="" && leaveToId=="")
			 {
				 $('#errorMessage').slideDown().html("<span>Please Select From Date and To Date.</span>");
				 return false;
			 }
			 else if(leaveFromId=="")
			  {
			  $('#errorMessage').slideDown().html("<span>Please Select From Date.</span>");
			  return false;
			  }
			 else if(leaveToId=="")
			  {
			  $('#errorMessage').slideDown().html('<span>Please Select To Date.</span>');
			  return false;
			  }else{
				   e.preventDefault();
					document.getElementById("method").value="saveApplyLeave";
					document.hostelLeaveForm.submit();
				  
			  }
		 });
		});
	function cancelAction() {
		document.location.href = "StudentLoginAction.do?method=returnHomePage";
	}
	function checkNumeric(event) {
		return false;
		}
	
	function validateDate(testdate) {
	    var date_regex = /^(0?[1-9]|[12][0-9]|3[01])[\/\-](0?[1-9]|1[012])[\/\-]\d{4}$/ ;
	    if(!date_regex.test(testdate)){
	    	$.confirm({
				'message'	: '<b>Please Enter Valid Date.</b>',
				'buttons'	: {
					'Ok'	: {
						'class'	: 'blue',
						'action': function(){
							$.confirm.hide();
						}
					}
				}
			});
	    }
	}
	
	</script>
<html:form action="/hostelLeave" >
	<html:hidden property="method" styleId="method" value=" " />
	<html:hidden property="formName" value="hostelLeaveForm" />
	<html:hidden property="pageType" value="4" />
	
	<table width="90%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/st_Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/st_Tcenter.gif" class="body"><strong
						class="boxheader">Hostel Leave Application</strong></td>

					<td width="10"><img src="images/st_Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr height="10%">
					<td height="20" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" height="10%">
					
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				
				
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" align="center" cellpadding="0"
						cellspacing="0">
						<tr><td colspan="2">
							<font color="red" size="2"><div id="errorMessage"><FONT color="red"><html:errors /></FONT>
							<FONT color="green"><html:messages id="msg"
								property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
								<br>
							</html:messages></FONT></div></font>
             </td>
						</tr>
						<tr>
							<td><img src="images/st_01.gif" width="5" height="5" /></td>
							<td width="914" background="images/st_02.gif"></td>

							<td><img src="images/st_03.gif" width="5" height="5" /></td>
						</tr>
						<tr>
							<td width="5" background="images/st_left.gif"></td>
							<td valign="top">
							<logic:notEmpty name="hostelLeaveForm" property="noRecordFound">
							<div class="boxheader" align="center"><FONT color="red">	
							<bean:write name="hostelLeaveForm" property="noRecordFound"/></FONT></div>
							</logic:notEmpty>
							<logic:empty name="hostelLeaveForm" property="noRecordFound">
							<table width="100%" cellspacing="1" cellpadding="2">
							<tr>
									<td width="20%" height="25" class="studentrow-odd"><div align="right">Hostel:&nbsp;</div></td>
									<td width="30%" height="25" class="studentrow-even" align="left"><bean:write name="hostelLeaveForm" property="hostelName"/></td>
									<td width="20%" height="25" class="studentrow-odd"><div align="right">Block:&nbsp;</div></td>
									<td width="30%" height="25" class="studentrow-even" align="left"><bean:write name="hostelLeaveForm" property="blockName"/></td>
							</tr>	
							<tr>
									<td width="20%" height="25" class="studentrow-odd" ><div align="right">Unit:&nbsp;</div></td>
									<td width="30%" height="25" class="studentrow-even" align="left"><bean:write name="hostelLeaveForm" property="unitName"/></td>
									<td width="20%" height="25" class="studentrow-odd" align="right"></td>
									<td width="30%" height="25" class="studentrow-even" align="left"></td>
							</tr>	
								<tr>
									<td width="20%" height="25" class="studentrow-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.hostel.leave.from" />:&nbsp;</div>
									</td>
									<td width="30%" height="25" class="studentrow-even"><html:text
										name="hostelLeaveForm" property="startDate"
										styleId="leaveFromId" size="10" maxlength="10" onchange="validateDate(this.value)" onkeypress="return checkNumeric(event)"/> <script
										language="JavaScript">

										$(function(){
											 var pickerOpts = {
													 	            dateFormat:"dd/mm/yy"
													         };  
											  $.datepicker.setDefaults(
											    $.extend($.datepicker.regional[""])
											  );
											  $("#leaveFromId").datepicker(pickerOpts);
											});

		
										</script> 
										<nested:radio property="leaveFromSession" styleId="leaveFromSessionMor" value="Morning">
											<bean:message key="knowledgepro.hostel.leave.morning" />
										</nested:radio> 
										<nested:radio property="leaveFromSession" styleId="leaveFromSessionEve" value="Evening">
											<bean:message key="knowledgepro.hostel.leave.evening" />
										</nested:radio></td>
									<td width="20%" height="25" class="studentrow-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.hostel.leave.to" />:&nbsp;</div>
									</td>
									<td width="30%" height="25" class="studentrow-even"><html:text
										name="hostelLeaveForm" property="endDate" styleId="leaveToId"
										size="10" maxlength="10" onchange="validateDate(this.value)" onkeypress="return checkNumeric(event)"/> <script language="JavaScript">
										$(function(){
											 var pickerOpts = {
										 	            dateFormat:"dd/mm/yy"
										         };  
											  $.datepicker.setDefaults(
											    $.extend($.datepicker.regional[""])
											  );
											  $("#leaveToId").datepicker(pickerOpts);
											});
										
										</script> 
										<nested:radio property="leaveToSession" styleId="leaveToSessionMor"	value="Morning">
										<bean:message key="knowledgepro.hostel.leave.morning" />
									</nested:radio> <nested:radio property="leaveToSession"
										styleId="leaveToSessionEve" value="Evening">
										<bean:message key="knowledgepro.hostel.leave.evening" />
									</nested:radio></td>

								</tr>
                  <tr class="row-white">
                 		<td width="20%" height="25" valign="middle" class="studentrow-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.admin.sec.LeaveType" />:&nbsp;</div>
									</td>
									<td width="30%" height="25" class="studentrow-even"><html:select
										name="hostelLeaveForm" property="leaveType" styleClass="combo"
										styleId="rId">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<logic:notEmpty name="hostelLeaveForm"
											property="leaveTypeList">
											<html:optionsCollection name="hostelLeaveForm"
												property="leaveTypeList" label="name" value="id" />
										</logic:notEmpty>
									</html:select></td>
									<td width="20%" height="25" valign="top" class="studentrow-odd">
									<div align="right"><span class="Mandatory">*</span> <bean:message
										key="knowledgepro.feepays.Reason" />:&nbsp;</div>
									</td>
									<td width="30%" height="25" class="studentrow-even"><html:textarea
										property="reasons" styleId="reasons" cols="20" rows="2" /></td>
                 			</tr>
                  <tr class="row-white">
                   <td colspan="4"><div align="center">
					<html:submit value="Submit" styleClass="btnbg" styleId="buttonId"></html:submit>	&nbsp; <html:button property="" value="Cancel" styleClass="btnbg" onclick="cancelAction()"></html:button>
					</div></td>
                 </tr>
                 </table>
                 </logic:empty>
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
</html:form>
<script type="text/javascript">
	document.getElementById("relationShip").style.display = "none";
	var pass=document.getElementById("pass1").value;
	var passAvailable=document.getElementById("passAvailable").value;
	var onepassAvailbale=document.getElementById("onepassAvailbale").value;
	if(pass=='true' && document.getElementById("pass1").checked && onepassAvailbale=='true'){
		document.getElementById("relationShip").style.display = "block";
		document.getElementById("relationShip").innerHTML = "<table width='100%'> <tr height='25'> <td class='studentrow-odd' align='right' width='50%'>select the Pass:</td> <td class='studentrow-odd' align='left'> <input type='radio' value='1' name='passes'> 1 Pass</td> </tr></table>";
		document.getElementById("buttonId").value="proceed With Online Payment";
	}else if(pass=='true' && document.getElementById("pass1").checked && passAvailable=='true'){
		document.getElementById("relationShip").style.display = "block";
		document.getElementById("buttonId").value="proceed With Online Payment";
	}else if(pass=='true' && document.getElementById("pass1").checked && passAvailable=='false'){
		document.getElementById("buttonId").value="Submit";
		document.getElementById("relationShip").style.display = "block";
		document.getElementById("relationShip").innerHTML=" <table width='100%'> <tr tr height='25'><td width='100%' colspan='2' class='studentrow-odd' align='center'>Passes are not available</td> </tr></table>";
		document.getElementById("pass2").checked = true;
	}else{
		document.getElementById("relationShip").style.display = "none";
		document.getElementById("buttonId").value="Submit";
	}
</script>