<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@page import="java.util.Map,java.util.HashMap"%>
<script src="jquery/jquery.ui.timepicker.js" type="text/javascript"></script>
<link type="text/css"
	href="jquery/css/ui-lightness/jquery-ui-1.8.18.custom.css" rel="stylesheet" />
<link type="text/css"href="jquery/jquery.ui.timepicker.css" rel="stylesheet" />
<script src="jquery/js/jquery-ui-1.10.0.custom.min.js" type="text/javascript"></script>
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
.ui-timepicker{
    background: #236b8e;
	border: 1px solid #555;
	color: #ffffff;
	font-family: Arial;
	font-size: 11px;
	margin-left: 10px
}
</style>
<script type="text/javascript">
$(document).ready(function() {
  $('#Submit').click(function(){
       var maintenanceMessage = $('#maintenanceMessage').val();
       var maintenanceDate = $('#maintenanceDate').val();
       var maintenanceFromTime = $('#maintenanceFromTime').val();
       var maintenanceToTime = $('#maintenanceToTime').val();
       if(maintenanceMessage== '' && maintenanceDate=='' && maintenanceFromTime=='' && maintenanceToTime==''){
    	   $('#errorMessage').slideDown().html("<span>Maintenance Message is Required <br>Date is Required <br>From Time is Required <br>To Time is Required.</span>");
          return false;
        }
       else if(maintenanceMessage== ''){
        	 $('#errorMessage').slideDown().html("<span>Please Enter Maintenance Message.</span>");
           return false;
        }
       else if(maintenanceDate== ''){
        	 $('#errorMessage').slideDown().html("<span>Please Enter Maintenance Date.</span>");
            return false;
        }else if(validateDate(maintenanceDate)==false){
        	$('#errorMessage').slideDown().html("<span>Please Enter Valid Maintenance Date.</span>");
            }
       else if(maintenanceFromTime== ''){
        	 $('#errorMessage').slideDown().html("<span>Please Enter Maintenance From Time.</span>");
            return false;
        }else if(maintenanceToTime== ''){
       	 $('#errorMessage').slideDown().html("<span>Please Enter Maintenance To Time.</span>");
         return false;
     }
       else{
           var lessThan=0;
    	   var fromTime = maintenanceFromTime.split(' ');
    	   var toTime = maintenanceToTime.split(' ');
    	   if(fromTime[1].toLowerCase()=="pm" && toTime[1].toLowerCase()=="pm"){
                 var toTimeMin=toTime[0].split(':');
                 var fromTimeMin=fromTime[0].split(':');
                 if(toTimeMin[0].toLowerCase()=="12"){
                	 lessThan=1;
                 }else{
                     if(fromTimeMin[0].toLowerCase()!="12"){
                     var totalToMin=parseInt(toTimeMin[0])*60+parseInt(toTimeMin[1]);
                     var totalFromMin=parseInt(fromTimeMin[0])*60+parseInt(fromTimeMin[1]);
                     if(parseInt(totalFromMin)>=parseInt(totalToMin)){
                    	 lessThan=1;
                     }
                     }
                 }
        	 }else if(fromTime[1].toLowerCase()=="am" && toTime[1].toLowerCase()=="am"){
                 var toTimeMin=toTime[0].split(':');
                 var fromTimeMin=fromTime[0].split(':');
                 if(toTimeMin[0].toLowerCase()=="12"){
                	 lessThan=1;
                 }else{
                	 if(fromTimeMin[0].toLowerCase()!="12"){
                     var totalToMin=parseInt(toTimeMin[0])*60+parseInt(toTimeMin[1]);
                     var totalFromMin=parseInt(fromTimeMin[0])*60+parseInt(fromTimeMin[1]);
                     if(parseInt(totalFromMin)>=parseInt(totalToMin)){
                    	 lessThan=1;
                     }
                	 }
                 }
        	}else if(fromTime[1].toLowerCase()=="pm" && toTime[1].toLowerCase()=="am"){
        		    lessThan=1;
            }
       	  if(lessThan==0){ 
   		   document.maintenanceAlertForm.submit();
       	  }else{
       		 $('#errorMessage').slideDown().html("<span><b>From Time</b> should be less than <b>To Time</b> .</span>");
                return false;
           	  }
       }
      });
});	
	function editMaintenanceAlert(id) {
		document.location.href = "maintenanceAlert.do?method=editMaintenanceAlert&id="+id;
	}
	function deleteMaintenance(id) {
		deleteConfirm = confirm("Are you sure you want to delete this entry?")
		if (deleteConfirm) {
			document.location.href = "maintenanceAlert.do?method=deleteMaintenance&id="+id;
		}
	}
	function resetMaintenance() {
		document.getElementById("maintenanceMessage").value = "";
		document.getElementById("maintenanceDate").value = "";
		document.getElementById("maintenanceFromTime").value = "";
		document.getElementById("errorMessage").value = "";
		document.getElementById("maintenanceToTime").value = "";
		if(document.getElementById("method").value=="updateMaintenanceAlert"){
			 document.getElementById("maintenanceMessage").value=document.getElementById("origMaintenanceMessage").value;
			 document.getElementById("maintenanceDate").value=document.getElementById("origMaintenanceDate").value;
			 document.getElementById("maintenanceFromTime").value=document.getElementById("origMaintenanceFromTime").value;
			 document.getElementById("maintenanceToTime").value=document.getElementById("origMaintenanceToTime").value;
		}
		resetErrMsgs();

	}
	function checkNumeric(event) {
		return false;
		}

	function validateDate(testdate) {
	    var date_regex = /^(0?[1-9]|[12][0-9]|3[01])[\/\-](0?[1-9]|1[012])[\/\-]\d{4}$/ ;
	    if(!date_regex.test(testdate)){
	    	return false;
	    }else{
		return true;
	    }    
	}
</script>

<html:form action="/maintenanceAlert" method="post" >
	<html:hidden property="formName" value="maintenanceAlertForm" />
	<html:hidden property="pageType" value="1"/>
	<html:hidden property="origMaintenanceMessage" name="maintenanceAlertForm" styleId="origMaintenanceMessage"/>
	<html:hidden property="origMaintenanceDate" name="maintenanceAlertForm" styleId="origMaintenanceDate"/>
	<html:hidden property="origMaintenanceFromTime" name="maintenanceAlertForm" styleId="origMaintenanceFromTime"/>
	<html:hidden property="origMaintenanceToTime" name="maintenanceAlertForm" styleId="origMaintenanceToTime"/>
	<c:choose>
		<c:when test="${operation == 'edit'}">
			<html:hidden property="method" styleId="method" value="updateMaintenanceAlert" />
		</c:when>
		<c:otherwise>
			<html:hidden property="method" styleId="method" value="addMaintenanceAlert" />
		</c:otherwise>
	</c:choose>

	<table width="100%" border="0">
		<tr>

			<td><span class="Bredcrumbs"> <bean:message
				key="knowledgepro.admin" /> <span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.admin.maintenance.alert" /> &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> <bean:message
						key="knowledgepro.admin.maintenance.alert" /></strong></td>

					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" cellspacing="1" cellpadding="2">
						<tr bgcolor="#FFFFFF">
							<td height="20" colspan="4">
							<div align="right"><FONT color="red"> <span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields"/></span></FONT></div>
							<font color="red" size="2"><div id="errorMessage"><FONT color="red"><html:errors /></FONT>
							<FONT color="green"> <html:messages id="msg"
								property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
								<br>
							</html:messages> </FONT></div></font>
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

						<tr>
                            <td width="25%" height="25" class="row-odd">
                            <div align="right"><span class="Mandatory">*</span><bean:message
								key="knowledgepro.admin.maintenance.message" />:</div>
							</td>
							<td width="25%" height="25" class="row-even">
                            <div align="left">
                            <html:text property="maintenanceMessage" name="maintenanceAlertForm" styleClass="TextBox" styleId="maintenanceMessage" size="60"></html:text>
                            </div>
							</td>
							<td width="25%" height="25" class="row-odd">
							<div align="right"><span class="Mandatory">*</span><bean:message
								key="knowledgepro.admin.template.Date" />:</div>
							</td>
							<td width="25%" height="25" class="row-even"><html:text
										name="maintenanceAlertForm" property="maintenanceDate" styleId="maintenanceDate"
										size="10" maxlength="10"  /> <script language="JavaScript">
										$(function(){
											 var pickerOpts = {
										 	            dateFormat:"dd/mm/yy"
										         };  
											  $.datepicker.setDefaults(
											    $.extend($.datepicker.regional[""])
											  );
											  $("#maintenanceDate").datepicker(pickerOpts);
											});
                              </script> </td>
						</tr>
						<tr>
							<td height="25" class="row-odd" width="25%">
							<div align="right"><span class="Mandatory">*</span><bean:message
								key="knowledgepro.admin.maintenance.from.time" /></div>
							</td>
							<td height="25" class="row-even"><span class="star">
							<html:text property="maintenanceFromTime" styleClass="TextBox"
								styleId="maintenanceFromTime" name="maintenanceAlertForm" onkeypress="return checkNumeric(event)"/>
							</span>
												<script>
							  $(function() {
								$("#maintenanceFromTime").timepicker({
									showPeriod: true,
								    showLeadingZero: true
									});
								
							  });
							</script>
							</td>
							 <td width="25%" height="25" class="row-odd">
                            <div align="right"><span class="Mandatory">*</span><bean:message
								key="knowledgepro.admin.maintenance.to.time" />:</div>
							</td>
							<td width="25%" height="25" class="row-even">
                            <div align="left">
                            <html:text property="maintenanceToTime" name="maintenanceAlertForm" styleClass="TextBox" styleId="maintenanceToTime" onkeypress="return checkNumeric(event)">
                            </html:text>
                            </div>
                            	<script>
							  $(function() {
								$("#maintenanceToTime").timepicker({
									showPeriod: true,
								    showLeadingZero: true
									});
							  });
							</script>
							</td>
						</tr>
							</table>
									</td>
									<td width="5" height="29" background="images/right.gif"></td>
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
							<td height="25" colspan="4">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td width="45%" height="35">
									<div align="right"><c:choose>
										<c:when test="${operation == 'edit'}">
											<html:submit property="" styleClass="formbutton"
												value="Update" styleId="Submit"></html:submit>
										</c:when>
										<c:otherwise>
											<html:submit property="" styleClass="formbutton"
												value="Submit"  styleId="Submit"></html:submit>
										</c:otherwise>
									</c:choose></div>
									</td>
									<td width="2%"></td>
									<td width="53%">
							              <html:button property="" styleClass="formbutton"
												value="Reset" onclick="resetMaintenance()" styleId="reset"></html:button>
										</td>
								</tr>
							</table>
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

						<tr>
							<td height="25" colspan="4">
							<table width="100%" cellspacing="1" cellpadding="2">
								<tr>
									<td width="28%" height="25" class="row-odd" align="center"><bean:message
										key="knowledgepro.admin.maintenance.message" /></td>
									<td width="18%" class="row-odd" align="center"><bean:message
										key="knowledgepro.admin.template.Date"/></td>
									<td width="10%" class="row-odd" align="center">
									<div align="center"><bean:message
										key="knowledgepro.admin.maintenance.from.time" /></div>
									</td>
									<td width="10%" height="25" class="row-odd" align="center">
									<div align="center"><bean:message key="knowledgepro.admin.maintenance.to.time" /></div>
									</td>
									<td width="18%" class="row-odd">
									<div align="center">Edit</div>
									</td>
									<td width="18%" class="row-odd">
									<div align="center">Delete</div>
									</td>
								</tr>
									<tr class="row-even">
									<logic:notEmpty name="maintenanceAlertForm" property="maintenanceTo" >
									<bean:define id="to" name="maintenanceAlertForm" property="maintenanceTo"></bean:define>
									<td width="9%" height="25" align="center"><bean:write
										name="to" property="maintenanceMessage"/> </td>
									<td width="9%" height="25" align="center"><bean:write
										name="to" property="maintenanceDate"/> </td>
									<td width="9%" height="25" align="center"><bean:write
										name="to" property="maintenanceFromTime"/> </td>
									<td width="5%" height="25" align="center">
									<bean:write name="to" property="maintenanceToTime"/>
									</td>	
									<td width="5%" height="25" align="center">
									<div align="center"><img src="images/edit_icon.gif"
										width="16" height="18"  style="cursor:pointer" 
										onclick="editMaintenanceAlert('<bean:write name="to" property="id"/>')">
									</div>
									</td>
									<td width="5%" height="25" align="center">
									<div align="center"><img src="images/delete_icon.gif"
										width="16" height="16" style="cursor:pointer" 
										onclick="deleteMaintenance('<bean:write name="to" property="id"/>')">
									</div>
									</td>
									</logic:notEmpty>
								</tr>
							</table>
							</td>
						</tr>
						</table>
							</td>
							<td width="5" height="29" background="images/right.gif"></td>
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