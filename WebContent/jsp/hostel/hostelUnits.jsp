<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>

<script type="text/javascript">
function getBlocks(hostal){
	getBlocksByHostel(hostal, "blockId", updateBlocks);
}
function updateBlocks(req){
	updateOptionsFromMap(req, "blockId", "- Select -");
}
function addHostelUnits() {
	document.getElementById("method").value = "addHostelUnits";
	document.hostelUnitsForm.submit();
}
function deleteHostelUnits(id){
	deleteConfirm = confirm("Are you sure you want to delete this entry?");
	if(deleteConfirm){
		document.location.href = "HostelUnits.do?method=deleteHostelUnits&id="+ id;
	}
}
function editHostelUnits(id) {
	document.location.href = "HostelUnits.do?method=editHostelUnits&id="+ id;
}
function updateHostelUnits() {
	document.getElementById("method").value = "updateHostelUnits";
	document.hostelUnitsForm.submit();
}
function reActivate() {
	var name = document.getElementById("name").value;
	document.location.href = "HostelUnits.do?method=reActivateHostelUnits&name="+ name;
}
function resetMessages(){
	resetFieldAndErrMsgs();
	document.getElementById("onlineLeave1").checked = true;
	document.getElementById("smsForParents1").checked = true;
	document.getElementById("smsForPrimaryCon1").checked = true;
	document.getElementById("smsForSecondCon1").checked = true;
	document.getElementById("smsOnMorning1").checked = true;
	document.getElementById("smsOnEvening1").checked = true;
	document.getElementById("punchExepSundaySession1").checked = true;
	
}
function resetField(){
	document.getElementById("primaryContactName").value = document.getElementById("tempprimaryContactName").value ;
	document.getElementById("primaryContactDesignation").value = document.getElementById("primaryContactDesignation").value;
	document.getElementById("primaryContactPhone").value = document.getElementById("primaryContactPhone").value;
	document.getElementById("primaryContactMobile").value = document.getElementById("primaryContactMobile").value;
	document.getElementById("primaryContactEmail").value = document.getElementById("primaryContactEmail").value;
	document.getElementById("secContactName").value = document.getElementById("secContactName").value;
	document.getElementById("secContactDesignation").value = document.getElementById("secContactDesignation").value;
	document.getElementById("secContactPhone").value = document.getElementById("secContactPhone").value;
	document.getElementById("secContactMobile").value = document.getElementById("secContactMobile").value;
	document.getElementById("secContactEmail").value = document.getElementById("secContactEmail").value;
	document.getElementById("leaveBeforeNoOfDays").value = document.getElementById("templeaveBeforeNoOfDays").value;
	document.getElementById("shtime").value = document.getElementById("tempinHours").value;
	document.getElementById("smtime").value = document.getElementById("tempinMins").value;
	document.getElementById("shtime1").value = document.getElementById("tempoutHours").value;
	document.getElementById("smtime1").value = document.getElementById("tempoutMins").value;
	document.getElementById("intervalMails").value = document.getElementById("tempintervalMails").value ;
	var leave=document.getElementById("tempOnlineLeave").value; 
	var smsForParent=document.getElementById("tempSmsForParents").value;
	var smsForPrimaryCon1=document.getElementById("tempsmsForPrimaryCon").value;
	var smsForSecondCon1=document.getElementById("tempsmsForSecondCon").value;
	var smsOnMorning1=document.getElementById("tempsmsOnMorning").value;
	var smsOnEvening1=document.getElementById("tempsmsOnEvening").value;
	var punchExepSunday=document.getElementById("tempPunchExepSundaySession").value;
	if(leave=="yes"){
		document.getElementById("onlineLeave").checked = true;
	}else{
		document.getElementById("onlineLeave1").checked = true;
		}
	if(smsForParent=="yes"){
		document.getElementById("smsForParents").checked = true;
	}else{
		document.getElementById("smsForParents1").checked = true;
		}
	if(smsForPrimaryCon1== "yes"){
		document.getElementById("smsForPrimaryCon").checked = true;
	}else{
		document.getElementById("smsForPrimaryCon1").checked = true;
		}
	if(smsForSecondCon1== "yes"){
		document.getElementById("smsForSecondCon").checked = true;
	}else{
		document.getElementById("smsForSecondCon1").checked = true;
		}
	if(smsOnMorning1 == "yes"){
		document.getElementById("smsOnMorning").checked = true;
	}else{
		document.getElementById("smsOnMorning1").checked = true;
		}
	if(smsOnEvening1=="yes"){
		document.getElementById("smsOnEvening").checked = true;
	}else{
		document.getElementById("smsOnEvening1").checked = true;
		}
	if(punchExepSunday=="yes"){
		document.getElementById("punchExepSundaySession").checked = true;
	}else{
		document.getElementById("punchExepSundaySession1").checked = true;
		}
	resetErrMsgs();
}
function clearField(field){
	if(field.value == "00")
		field.value = "";
}
function checkForEmpty(field){
	if(field.value.length == 0){
		field.value="00";
	}
}
function checkNumber(field){
	if(isNaN(field.value)){
		field.value = "00";
	}
}
function mail(sms){
	if(sms == "yes"){
		document.getElementById("intervalMails").disabled=false;
		}else if(sms == "no"){
			document.getElementById("intervalMails").disabled=true;
			}
}
function cancel(){
	document.location.href = "LoginAction.do?method=loginAction";
}
function home(){
	document.location.href = "HostelUnits.do?method=initHostelUnits";
}
</script>

<html:form action="HostelUnits" method="post" enctype="multipart/form-data">
	<html:hidden property="formName" value="hostelUnitsForm" />
	<html:hidden property="pageType" value="1" />
	<html:hidden property="id" styleId="id"/>
	<c:choose>
		<c:when test="${unitsOperation == 'edit'}">
			<html:hidden property="method" styleId="method" value="editHostelUnits" />
		</c:when>
		<c:otherwise>
			<html:hidden property="method" styleId="method" value="addHostelUnits" />
		</c:otherwise>
	</c:choose>

	<table width="100%" border="0">
	  <tr>
		<td><span class="Bredcrumbs"> <bean:message key="knowledgepro.hostel"/><span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.hostel.units.hostelunits"/> &gt;&gt;</span></span></td>	  
	  </tr>
	  <tr>
	    <td valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
	      <tr>
	        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
	        <td colspan="2" background="images/Tcenter.gif" class="heading_white" ><bean:message key="knowledgepro.hostel.units.details"/></td>
	        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
	      </tr>
	      <tr>
	        <td height="30" valign="top" background="images/Tright_03_03.gif"></td>
	        <td colspan="2" class="news">
			<div align="right">
				<FONT color="red"> <span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields"/></span></FONT></div>
				<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
				<FONT color="green"> <html:messages id="msg"
				property="messages" message="true">
				<c:out value="${msg}" escapeXml="false"></c:out>
				<br>
				</html:messages> </FONT></div></td>
	        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
	
	      </tr>
	      <tr>
	        <td height="30" valign="top" background="images/Tright_03_03.gif"></td>
	        <td colspan="2" valign="top" class="news"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
	            <tr>
	              <td ><img src="images/01.gif" width="5" height="5" /></td>
	              <td width="914" background="images/02.gif"></td>
	              <td><img src="images/03.gif" width="5" height="5" /></td>
	            </tr>
	            <tr>
	              <td width="5"  background="images/left.gif"></td>
	              <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
	                <tr>
	                  <td width="30%" height="25" class="row-odd"><div align="right"><span class="Mandatory">*</span> <bean:message key="knowledgepro.hostel.entry.hostel.name"/></div></td>
	                  <td width="20%" class="row-even">
									<html:select property="hostelId" styleClass="combo" styleId="hostelId" onchange="getBlocks(this.value)">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<logic:notEmpty name="hostelUnitsForm" property="hostelList">
											<html:optionsCollection name="hostelUnitsForm" property="hostelList" label="name" value="id" />
										</logic:notEmpty>
									</html:select>
									</td>
	                  <td width="30%" class="row-odd"><div align="right"><span class="Mandatory">*</span>&nbsp;
									<bean:message key="knowledgepro.hostel.blocks" />:</div></td>
	
	                  <td width="20%" class="row-even">
							<html:select name="hostelUnitsForm" property="blockId" styleClass="combo" styleId="blockId">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<logic:notEmpty name="hostelUnitsForm" property="blockMap">
											<html:optionsCollection name="hostelUnitsForm" property="blockMap" label="value" value="key" />
										</logic:notEmpty>
									</html:select>
					</td>
	                </tr>
	                <tr>
	                	<td width="30%" height="25" class="row-odd"><div align="right"><span class="Mandatory">*</span>
	                	<bean:message key="knowledgepro.hostel.units"/></div></td>
	                 	<td width="20%" height="25" class="row-even"><html:text property="name" styleClass="TextBox"
									styleId="name" size="20" maxlength="50" name="hostelUnitsForm" /></td>
						<td width="30%" height="25" class="row-odd"><div align="right"><span class="Mandatory">*</span>
						<bean:message key="knowledgepro.hostel.entry.no.of.floors"/></div></td>
						<td width="20%" height="25" class="row-even"><html:text property="noOfFloors" styleClass="TextBox"
									styleId="noOfFloors" size="20" maxlength="50" name="hostelUnitsForm" onkeypress="return isNumberKey(event)"/></td>
						 
	                </tr>
	              </table></td>
	              <td width="5" height="30"  background="images/right.gif"></td>
	            </tr>
	            <tr>
							<td height="5"><img src="images/04.gif" width="5" height="5" /></td>
							<td background="images/05.gif"></td>
							<td><img src="images/06.gif" /></td>
						</tr>
	        </table></td>
	        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
	      </tr>
	      <tr>
	        <td height="13" valign="top" background="images/Tright_03_03.gif"></td>
	        <td height="20" class="heading"><bean:message key="knowledgepro.hostel.entry.primary.contact"/></td>
	        <td height="20" class="heading"><bean:message key="knowledgepro.hostel.entry.secondary.contact"/></td>
	        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
	      </tr>
	      <tr>
	        <td height="13" valign="top" background="images/Tright_03_03.gif"></td>
	        <td width="470" height="20" valign="top" class="news"><table width="98%" border="0" cellpadding="0" cellspacing="0">
	            <tr>
	
	              <td ><img src="images/01.gif" width="5" height="5"></td>
	              <td width="410" background="images/02.gif"></td>
	              <td><img src="images/03.gif" width="5" height="5"></td>
	            </tr>
	            <tr>
	              <td width="5"  background="images/left.gif"></td>
	              <td height="133" valign="top">
	              <table width="100%" cellspacing="1" cellpadding="2">
	                  <tr >
	                    <td width="60%" height="25" class="row-odd" ><div align="right"><bean:message key="admissionForm.studentedit.name.label"/></div></td>
	                    <td width="40%" height="25" class="row-even">
	                    <label>
	                     <input type="hidden" name="hostelUnitsForm"	id="tempprimaryContactName" value="<bean:write name='hostelUnitsForm' property='primaryContactName'/>" />
	                      <html:text property="primaryContactName" styleClass="TextBox"
									styleId="primaryContactName" size="20" maxlength="50" name="hostelUnitsForm" />
	                    </label></td>
	                  </tr>
	                  <tr >
	                    <td height="25" class="row-odd" ><div align="right"><bean:message key="knowledgepro.hostel.entry.Designation"/></div></td>
	                    <td height="25" class="row-even" >
	                   		<input type="hidden" name="hostelUnitsForm"	id="tempprimaryContactDesignation" value="<bean:write name='hostelUnitsForm' property='primaryContactDesignation'/>" />
	                    	<html:text property="primaryContactDesignation" styleClass="TextBox"
									styleId="primaryContactDesignation" size="20" maxlength="50" name="hostelUnitsForm" /></td>
	                  </tr>
	
	                  <tr >
	                    <td height="25" class="row-odd"><div align="right"><bean:message key="admissionForm.studentinfo.phone.label"/></div></td>
	                    <td height="25" class="row-even">
	                    	<input type="hidden" name="hostelUnitsForm"	id="tempprimaryContactPhone" value="<bean:write name='hostelUnitsForm' property='primaryContactPhone'/>" />
	                    	<html:text property="primaryContactPhone" styleClass="TextBox"
									styleId="primaryContactPhone" size="20" maxlength="10" name="hostelUnitsForm" onkeypress="return isNumberKey(event)"/></td>
	                  </tr>
	                  <tr >
	                    <td height="25" class="row-odd"><div align="right"><bean:message key="admissionForm.studentinfo.mobile.label"/></div></td>
	                    <td height="25" class="row-even">
	                    	<input type="hidden" name="hostelUnitsForm"	id="tempprimaryContactMobile" value="<bean:write name='hostelUnitsForm' property='primaryContactMobile'/>" />
	                    	<html:text property="primaryContactMobile" styleClass="TextBox"
									styleId="primaryContactMobile" size="20" maxlength="10" name="hostelUnitsForm" onkeypress="return isNumberKey(event)"/></td>
	                  </tr>
	
	                  
	                  <tr >
	                    <td height="25" class="row-odd"><div align="right"><bean:message key="admissionForm.studentinfo.email.label"/></div></td>
	                    <td height="25" class="row-even">
	                    	<input type="hidden" name="hostelUnitsForm"	id="tempprimaryContactEmail" value="<bean:write name='hostelUnitsForm' property='primaryContactEmail'/>" />
	                    	<html:text property="primaryContactEmail" styleClass="TextBox"
									styleId="primaryContactEmail" size="20" maxlength="50" name="hostelUnitsForm" /></td>
	                  </tr>
	              </table></td>
	              <td background="images/right.gif" width="5" height="133"></td>
	            </tr>
	            <tr>
	              <td height="5"><img src="images/04.gif" width="5" height="5"></td>
	              <td background="images/05.gif"></td>
	              <td><img src="images/06.gif" ></td>
	            </tr>
	        </table></td>
	        <td width="497" valign="top" class="news"><div align="left">
	            <table width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
	              <tr>
	                <td ><img src="images/01.gif" width="5" height="5"></td>
	
	                <td width="404" background="images/02.gif"></td>
	                <td><img src="images/03.gif" width="5" height="5"></td>
	              </tr>
	              <tr>
	                <td width="5"  background="images/left.gif"></td>
	                <td height="133" valign="top"><table width="100%" cellspacing="1" cellpadding="2">
	                    <tr >
	                      <td width="60%" height="25" class="row-odd" ><div align="right"><bean:message key="admissionForm.studentedit.name.label"/></div></td>
	                      <td width="40%" height="25" class="row-even" ><label>
	                      	<input type="hidden" name="hostelUnitsForm"	id="tempsecContactName" value="<bean:write name='hostelUnitsForm' property='secContactName'/>" />
	                      	<html:text property="secContactName" styleClass="TextBox"
									styleId="secContactName" size="20" maxlength="50" name="hostelUnitsForm" />
	                      </label></td>
	                    </tr>
	                    <tr >
	                      <td height="25" class="row-odd" ><div align="right"><bean:message key="knowledgepro.hostel.entry.Designation"/></div></td>
	                      <td height="25" class="row-even" >
	                      	<input type="hidden" name="hostelUnitsForm"	id="tempsecContactDesignation" value="<bean:write name='hostelUnitsForm' property='secContactDesignation'/>" />
	                      	<html:text property="secContactDesignation" styleClass="TextBox"
									styleId="secContactDesignation" size="20" maxlength="50" name="hostelUnitsForm" /></td>
	                    </tr>
	
	                    <tr >
	                      <td height="25" class="row-odd"><div align="right"><bean:message key="admissionForm.studentinfo.phone.label"/></div></td>
	                      <td height="25" class="row-even">
	                      	<input type="hidden" name="hostelUnitsForm"	id="tempsecContactPhone" value="<bean:write name='hostelUnitsForm' property='secContactPhone'/>" />
	                      	<html:text property="secContactPhone" styleClass="TextBox"
									styleId="secContactPhone" size="20" maxlength="10" name="hostelUnitsForm" onkeypress="return isNumberKey(event)"/></td>
	                    </tr>
	                    <tr >
	                      <td height="25" class="row-odd"><div align="right"><bean:message key="admissionForm.studentinfo.mobile.label"/></div></td>
	                      <td height="25" class="row-even">
	                      	<input type="hidden" name="hostelUnitsForm"	id="tempsecContactMobile" value="<bean:write name='hostelUnitsForm' property='secContactMobile'/>" />
	                      	<html:text property="secContactMobile" styleClass="TextBox"
									styleId="secContactMobile" size="20" maxlength="10" name="hostelUnitsForm" onkeypress="return isNumberKey(event)"/></td>
	                    </tr>
	
	                    
	                    <tr >
	                      <td height="25" class="row-odd"><div align="right"><bean:message key="admissionForm.studentinfo.email.label"/></div></td>
	                      <td height="25" class="row-even">
	                      	<input type="hidden" name="hostelUnitsForm"	id="tempsecContactEmail" value="<bean:write name='hostelUnitsForm' property='secContactEmail'/>" />
	                      	<html:text property="secContactEmail" styleClass="TextBox"
									styleId="secContactEmail" size="20" maxlength="50" name="hostelUnitsForm" /></td>
	                    </tr>
	                </table></td>
	                <td  background="images/right.gif" width="5" height="133"></td>
	              </tr>
	              <tr>
	
	                <td height="5"><img src="images/04.gif" width="5" height="5"></td>
	                <td background="images/05.gif"></td>
	                <td><img src="images/06.gif" ></td>
	              </tr>
	            </table>
	        </div></td>
	        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
	      </tr>
	      <tr>
	        <td height="13" valign="top" background="images/Tright_03_03.gif"></td>
	        <td height="20" ></td>
	        <td height="20" ></td>
	        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
	      </tr>
	      <tr>
	        <td height="30" valign="top" background="images/Tright_03_03.gif"></td>
	        <td colspan="2" valign="top" class="news"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
	            <tr>
	              <td ><img src="images/01.gif" width="5" height="5" /></td>
	              <td width="914" background="images/02.gif"></td>
	              <td><img src="images/03.gif" width="5" height="5" /></td>
	            </tr>
	            <tr>
	              <td width="5"  background="images/left.gif"></td>
	              <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
	                <tr>
	                  <td width="30%" height="25" class="row-odd"><div align="right"><span class="Mandatory"></span> <bean:message key="knowledgepro.hostel.entry.online.leave.required"/></div></td>
	                  <td width="20%" class="row-even">	
	                 	 <input type="hidden" name="hostelUnitsForm"	id="tempOnlineLeave" value="<bean:write name='hostelUnitsForm' property='onlineLeave'/>" />
	                  		<input type="radio" name="onlineLeave" id="onlineLeave" value="yes" /> <bean:message key="knowledgepro.yes"/>
                    		<input type="radio" name="onlineLeave" id="onlineLeave1" value="no" checked="checked"/> <bean:message key="knowledgepro.no"/>
                    			<script type="text/javascript">
									var leave = document.getElementById("tempOnlineLeave").value;
									if(leave == "yes") {
										document.getElementById("onlineLeave").checked = true;
									}else{
										document.getElementById("onlineLeave1").checked = true;
									}		
								</script>
					  </td>
	                  <td width="30%" class="row-odd"><div align="right"><span class="Mandatory"></span>&nbsp;<bean:message key="knowledgepro.hostel.leave.apply.before.days" />:</div></td>
	                  <td width="20%" class="row-even">
	                  	<input type="hidden" name="hostelUnitsForm"	id="templeaveBeforeNoOfDays" value="<bean:write name='hostelUnitsForm' property='leaveBeforeNoOfDays'/>" />
	                  	<html:text property="leaveBeforeNoOfDays" styleId="leaveBeforeNoOfDays" onkeypress="return isNumberKey(event)" size="2" maxlength="2"></html:text>
					  </td>
	                </tr>
	                <tr>
	                  <td width="30%" height="25" class="row-odd"><div align="right"><span class="Mandatory"></span> <bean:message key="knowledgepro.hostel.entry.online.before.time"/></div></td>
	                  <td width="20%" class="row-even" align="left" >
	                  	<input type="hidden" name="hostelUnitsForm"	id="tempinHours" value="<bean:write name='hostelUnitsForm' property='applyBeforeHours'/>" />
	                    <input type="hidden" name="hostelUnitsForm"	id="tempinMins" value="<bean:write name='hostelUnitsForm' property='applyBeforeMin'/>" />
	                  	<html:text name="hostelUnitsForm" property="applyBeforeHours" styleClass="Timings" styleId="shtime" size="2" maxlength="2" onfocus="clearField(this)" onblur="checkForEmpty(this), checkNumber(this)" onkeypress="return isNumberKey(event)"/>:
 						<html:text  property="applyBeforeMin" styleClass="Timings" styleId="smtime" size="2" maxlength="2" onfocus="clearField(this)" onblur="checkForEmpty(this), checkNumber(this)" onkeypress="return isNumberKey(event)"/>
 					  </td>
	                  <td width="30%" class="row-odd"><div align="right"><span class="Mandatory"></span>&nbsp;<bean:message key="knowledgepro.hostel.leave.apply.nextday.time" />:</div></td>
	                  <td width="20%" class="row-even" align="left" >
	                  <input type="hidden" name="hostelUnitsForm"	id="tempoutHours" value="<bean:write name='hostelUnitsForm' property='applyBeforeNextDayHours'/>" />
	                    <input type="hidden" name="hostelUnitsForm"	id="tempoutMins" value="<bean:write name='hostelUnitsForm' property='applyBeforeNextDayMin'/>" />
	                  	<html:text name="hostelUnitsForm" property="applyBeforeNextDayHours" styleClass="Timings" styleId="shtime1" size="2" maxlength="2" onfocus="clearField(this)" onblur="checkForEmpty(this), checkNumber(this)" onkeypress="return isNumberKey(event)"/>:
 						<html:text  property="applyBeforeNextDayMin" styleClass="Timings" styleId="smtime1" size="2" maxlength="2" onfocus="clearField(this)" onblur="checkForEmpty(this), checkNumber(this)" onkeypress="return isNumberKey(event)"/>
 					  </td>
	                </tr>
	                <tr>
	                  	<td width="30%" height="25" class="row-odd"><div align="right"><span class="Mandatory"></span> <bean:message key="knowledgepro.hostel.entry.online.sms.required"/></div></td>
	                  	<td width="20%" class="row-even">
	                  	 <input type="hidden" name="hostelUnitsForm"	id="tempSmsForParents" value="<bean:write name='hostelUnitsForm' property='smsForParents'/>" />	
	                  		<input type="radio" name="smsForParents" id="smsForParents" value="yes" onchange="mail(this.value)"/> <bean:message key="knowledgepro.yes"/>
                    		<input type="radio" name="smsForParents" id="smsForParents1" value="no" onchange="mail(this.value)" checked="checked"/> <bean:message key="knowledgepro.no"/>
                    			<script type="text/javascript">
									var leave = document.getElementById("tempSmsForParents").value;
									if(leave == "yes") {
										document.getElementById("smsForParents").checked = true;
									}else{
										document.getElementById("smsForParents1").checked = true;
									}		
								</script>
					  	</td>
					   	<td width="30%" class="row-odd"><div align="right"><span class="Mandatory"></span>&nbsp;<bean:message key="knowledgepro.hostel.leave.apply.intervel.mail" />:</div></td>
	                  	<td width="20%" class="row-even">
	                  		<input type="hidden" name="hostelUnitsForm"	id="tempintervalMails" value="<bean:write name='hostelUnitsForm' property='intervalMails'/>" />
	                  		<html:text property="intervalMails" styleId="intervalMails" onkeypress="return isNumberKey(event)" size="2" maxlength="2" disabled="true"></html:text>
	                  		<script type="text/javascript">
									var leave = document.getElementById("tempSmsForParents").value;
									if(leave == "yes") {
										document.getElementById("intervalMails").disabled = false;
									}else{
										document.getElementById("intervalMails").disabled = true;
									}		
								</script>
					  	</td>
	                </tr>
	                <tr>
	                  <td width="30%" height="25" class="row-odd"><div align="right"><span class="Mandatory"></span> <bean:message key="knowledgepro.hostel.entry.online.sms.forprimaryContact"/></div></td>
	                  <td width="20%" class="row-even">	
	                  		<input type="hidden" name="hostelUnitsForm"	id="tempsmsForPrimaryCon" value="<bean:write name='hostelUnitsForm' property='smsForPrimaryCon'/>" />	
	                  		<input type="radio" name="smsForPrimaryCon" id="smsForPrimaryCon" value="yes" /> <bean:message key="knowledgepro.yes"/>
                    		<input type="radio" name="smsForPrimaryCon" id="smsForPrimaryCon1" value="no" checked="checked"/> <bean:message key="knowledgepro.no"/>
                    		<script type="text/javascript">
									var leave = document.getElementById("tempsmsForPrimaryCon").value;
									if(leave == "yes") {
										document.getElementById("smsForPrimaryCon").checked = true;
									}else{
										document.getElementById("smsForPrimaryCon1").checked = true;
									}		
								</script>
					  </td>
	                  <td width="30%" class="row-odd"><div align="right"><span class="Mandatory"></span>&nbsp;<bean:message key="knowledgepro.hostel.entry.online.sms.forsecondContact" />:</div></td>
	                  <td width="20%" class="row-even">	
	                  		<input type="hidden" name="hostelUnitsForm"	id="tempsmsForSecondCon" value="<bean:write name='hostelUnitsForm' property='smsForSecondCon'/>" />	
	                  		<input type="radio" name="smsForSecondCon" id="smsForSecondCon" value="yes" /> <bean:message key="knowledgepro.yes"/>
                    		<input type="radio" name="smsForSecondCon" id="smsForSecondCon1" value="no" checked="checked"/> <bean:message key="knowledgepro.no"/>
                    		<script type="text/javascript">
									var leave = document.getElementById("tempsmsForSecondCon").value;
									if(leave == "yes") {
										document.getElementById("smsForSecondCon").checked = true;
									}else{
										document.getElementById("smsForSecondCon1").checked = true;
									}		
								</script>
					  </td>
	                </tr>
	                <tr>
	                  <td width="30%" height="25" class="row-odd"><div align="right"><span class="Mandatory"></span> <bean:message key="knowledgepro.hostel.entry.online.sms.morning"/></div></td>
	                  <td width="20%" class="row-even">	
	                  		<input type="hidden" name="hostelUnitsForm"	id="tempsmsOnMorning" value="<bean:write name='hostelUnitsForm' property='smsOnMorning'/>" />	
	                  		<input type="radio" name="smsOnMorning" id="smsOnMorning" value="yes" /> <bean:message key="knowledgepro.yes"/>
                    		<input type="radio" name="smsOnMorning" id="smsOnMorning1" value="no" checked="checked"/> <bean:message key="knowledgepro.no"/>
                    		<script type="text/javascript">
									var leave = document.getElementById("tempsmsOnMorning").value;
									if(leave == "yes") {
										document.getElementById("smsOnMorning").checked = true;
									}else{
										document.getElementById("smsOnMorning1").checked = true;
									}		
								</script>
					  </td>
	                  <td width="30%" class="row-odd"><div align="right"><span class="Mandatory"></span>&nbsp;<bean:message key="knowledgepro.hostel.entry.online.sms.evening" />:</div></td>
	                  <td width="20%" class="row-even">
	                  		<input type="hidden" name="hostelUnitsForm"	id="tempsmsOnEvening" value="<bean:write name='hostelUnitsForm' property='smsOnEvening'/>" />		
	                  		<input type="radio" name="smsOnEvening" id="smsOnEvening" value="yes" /> <bean:message key="knowledgepro.yes"/>
                    		<input type="radio" name="smsOnEvening" id="smsOnEvening1" value="no" checked="checked"/> <bean:message key="knowledgepro.no"/>
                    		<script type="text/javascript">
									var leave = document.getElementById("tempsmsOnEvening").value;
									if(leave == "yes") {
										document.getElementById("smsOnEvening").checked = true;
									}else{
										document.getElementById("smsOnEvening1").checked = true;
									}		
								</script>
					  </td>
	                </tr>
	                <tr>
	                  <td width="30%" height="25" class="row-odd"><div align="right"><span class="Mandatory"></span> <bean:message key="knowledgepro.hostel.unit.punching.exemption.sunday.session"/></div></td>
	                  <td width="20%" class="row-even">	
	                  		<input type="hidden" name="hostelUnitsForm"	id="tempPunchExepSundaySession" value="<bean:write name='hostelUnitsForm' property='punchExepSundaySession'/>" />	
	                  		<input type="radio" name="punchExepSundaySession" id="punchExepSundaySession" value="yes" /> <bean:message key="knowledgepro.yes"/>
                    		<input type="radio" name="punchExepSundaySession" id="punchExepSundaySession1" value="no" checked="checked"/> <bean:message key="knowledgepro.no"/>
                    		<script type="text/javascript">
									var leave = document.getElementById("tempPunchExepSundaySession").value;
									if(leave == "yes") {
										document.getElementById("punchExepSundaySession").checked = true;
									}else{
										document.getElementById("punchExepSundaySession1").checked = true;
									}		
								</script>
					  </td>
	                  <td width="30%" class="row-odd"></td>
	                  <td width="20%" class="row-even"> </td>
	                </tr>
	              </table></td>
	              <td width="5" height="30"  background="images/right.gif"></td>
	            </tr>
	            <tr>
							<td height="5"><img src="images/04.gif" width="5" height="5" /></td>
							<td background="images/05.gif"></td>
							<td><img src="images/06.gif" /></td>
						</tr>
	        </table></td>
	        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
	      </tr>
	      <tr>
	
	        <td height="36" valign="top" background="images/Tright_03_03.gif"></td>
	        <td colspan="2" valign="top" class="news"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
	          <tr>
	            <td width="45%" height="35"><div align="right">
	                <c:choose>
					<c:when
						test="${unitsOperation != null && unitsOperation == 'edit'}">
						<html:button property="" styleClass="formbutton" value="Update"
										onclick="updateHostelUnits()"></html:button>
					</c:when>
					<c:otherwise>
						<html:button property="" styleClass="formbutton" value="Submit"
										onclick="addHostelUnits()"></html:button>
					</c:otherwise>
				</c:choose>
	            </div></td>
	            <td width="2%"></td>
	            <td width="5%">
	            <c:choose>
					<c:when test="${unitsOperation == 'edit'}">
						<html:button property="" styleClass="formbutton" value="Reset" onclick="resetField()"></html:button>
					</c:when>
					<c:otherwise>
						<html:button property="" styleClass="formbutton" value="Reset"
										onclick="resetMessages()"></html:button>
					</c:otherwise>
				</c:choose></td>
				 <td width="2%"></td>
				 <td width="45%" align="left">
	            <c:choose>
					<c:when test="${unitsOperation == 'edit'}">
						<html:button property="" styleClass="formbutton" value="Cancel" onclick="cancel()"></html:button>
					</c:when>
					<c:otherwise>
						<html:button property="" styleClass="formbutton" value="Cancel"
										onclick="home()"></html:button>
					</c:otherwise>
				</c:choose></td>
	          </tr>
	
	        </table></td>
	        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
	      </tr>
	      <tr>
	        <td height="94" valign="top" background="images/Tright_03_03.gif"></td>
	        <td colspan="2" valign="top" class="news"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
	          <tr>
	            <td ><img src="images/01.gif" width="5" height="5" /></td>
	            <td width="914" background="images/02.gif"></td>
	
	            <td><img src="images/03.gif" width="5" height="5" /></td>
	          </tr>
	          <tr>
	            <td width="5"  background="images/left.gif"></td>
	            <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
	                <tr >
	                  <td height="25" class="row-odd" ><div align="center"><bean:message key="knowledgepro.slno"/> </div></td>
	                  <td height="25" class="row-odd" ><div align="center"><bean:message key="knowledgepro.hostel"/></div></td>
	                  <td height="25" class="row-odd" ><div align="center"><bean:message key="knowledgepro.hostel.blocks"/></div></td>
	                  <td height="25" class="row-odd"><div align="center"><bean:message key="knowledgepro.hostel.units" /></div></td>
	                  <td height="25" class="row-odd"><div align="center"><bean:message key="knowledgepro.hostel.units.nooffloors" /></div></td>	
	                  <td class="row-odd"><div align="center"><bean:message key="knowledgepro.edit"/> </div></td>
	                  <td class="row-odd"><div align="center"><bean:message key="knowledgepro.delete"/></div></td>
	                </tr>
					<logic:notEmpty name="hostelUnitsForm"	property="unitsList">
					<logic:iterate id="unt" name="hostelUnitsForm"
										property="unitsList" indexId="count">
						<c:choose>
							<c:when test="${count%2 == 0}">
								<tr class="row-even">
							</c:when>
							<c:otherwise>
								<tr class="row-white">
							</c:otherwise>
						</c:choose>
		                  <td width="5%" height="25" ><div align="center"><c:out value="${count + 1}" /></div></td>
		                  <td width="20%" height="25"><div align="center"><bean:write name="unt" property="hostelName" /></div></td>
		                 <td width="20%" height="25"><div align="center"><bean:write name="unt" property="blockName" /></div></td>
		                 <td width="20%" height="25"><div align="center"><bean:write name="unt" property="name" /></div></td>
		               	 <td width="20%" height="25"><div align="center"><bean:write name="unt" property="noOfFloors" /></div></td>		                  
		                  <td width="5%" height="25"><div align="center"><img src="images/edit_icon.gif" 
		                  width="16" height="18" style="cursor:pointer"
							onclick="editHostelUnits('<bean:write name="unt" property="id" />')" /></div></td>		
		                  <td width="5%" height="25"><div align="center"><img src="images/delete_icon.gif" width="16" height="16"
							style="cursor:pointer" onclick="deleteHostelUnits('<bean:write name="unt" property="id"/>')"></div></td>
							</logic:iterate>
							</logic:notEmpty>
	            </table></td>
	            <td width="5" height="30"  background="images/right.gif"></td>
	          </tr>
	          <tr>
	            <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
	            <td background="images/05.gif"></td>
	            <td><img src="images/06.gif" /></td>
	          </tr>
	        </table></td>
	
	        <td width="10" valign="top" background="images/Tright_3_3.gif" class="news"></td>
	      </tr>
	      <tr>
	        <td height="26" valign="top" background="images/Tright_03_03.gif"></td>
	        <td colspan="2" valign="top" class="news"></td>
	        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
	      </tr>
	      <tr>
	        <td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
	
	        <td colspan="2" background="images/TcenterD.gif"></td>
	        <td><img src="images/Tright_02.gif" width="9" height="29"></td>
	      </tr>
	    </table>
	    </td>
	    </tr>
	    </table>
</html:form>