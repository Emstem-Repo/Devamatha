<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@page import="java.util.Map,java.util.HashMap"%>
<script src="jquery/jquery.ui.timepicker.js" type="text/javascript"></script>
<link type="text/css" href="jquery/css/ui-lightness/jquery-ui-1.8.18.custom.css" rel="stylesheet" />
<link type="text/css" href="jquery/jquery.ui.timepicker.css" rel="stylesheet" />
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
    background: #6B8E23;
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
	       var serviceId = $('#servicesId').val();
	       var date = $('#date').val();
	       var downFrom = $('#downFrom').val();
	       var downTill = $('#downTill').val();
	       var remarks = $('#remarks').val();
	       if(serviceId=='' && downFrom=='' && downTill=='' && remarks==''){
	    	   $('#errorMessage').slideDown().html("<span style='color:red; font-size: 8pt'>Service is Required <br>Down From is Required <br>Down Till is Required <br>Remarks is Required</span>");
	          return false;
	        }
	        else if(serviceId== ''){
		       $('#errorMessage').slideDown().html("<span style='color:red; font-size: 8pt'>Please Select Service Name.</span>");
	           return false;
	        }
	        else if(date== ''){
	        	$('#errorMessage').slideDown().html("<span style='color:red; font-size: 8pt'>Please Enter Date.</span>");
	            return false;
	        }
	        else if(validateDate(date)==false){
	        	$('#errorMessage').slideDown().html("<span style='color:red; font-size: 8pt'>Please Enter Valid Date.</span>");
	        }
	        else if(validateTodayDate(date)==false){
	        	$('#errorMessage').slideDown().html("<span style='color:red; font-size: 8pt'>Date Cannot be Future Date.</span>");
	        	return false;
	        }
	       	else if(downFrom== ''){
	        	 $('#errorMessage').slideDown().html("<span style='color:red; font-size: 8pt'>Please Enter Down From Time.</span>");
	            return false;
	        }else if(downTill== ''){
	       	 $('#errorMessage').slideDown().html("<span style='color:red; font-size: 8pt'>Please Enter Down Till Time.</span>");
	         return false;
	        }
	        else if(remarks== ''){
		       	 $('#errorMessage').slideDown().html("<span style='color:red; font-size: 8pt'>Please Enter Remarks.</span>");
		         return false;
		    }
	       	else{
	           var lessThan=0;
	    	   var fromTime = downFrom.split(' ');
	    	   var toTime = downTill.split(' ');
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
	   		   document.servicesDownTrackerForm.submit();
	       	  }else{
	       		 $('#errorMessage').slideDown().html("<span style='color:red'><b>Down From Time</b> should be less than <b>Down Till Time</b> .</span>");
	                return false;
	           	  }
	       }
	      });
	});

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

function validateTodayDate(testdate) {
	var day = testdate.substr(0, 2);
	var month = testdate.substr(3, 2);
	var year = testdate.substr(6, 10);
	var testdateNew = month+"/"+day+"/"+year;
	var date_format = new Date(testdateNew);
    var today_date = new Date();    
    
    if(date_format > today_date){
    	return false;
    }else{
		return true;
    }    
}

function imposeMaxLength(evt, Object) {
	var keynum = (evt.which) ? evt.which : event.keyCode;
	if (keynum == 8 || keynum == 37 || keynum == 39 || keynum == 9) {
		return true;
	}
	var MaxLen = 250;
	return (Object.value.length < MaxLen);
}

function cancellAdd(){
	document.location.href = "LoginAction.do?method=loginAction";
}

function cancellEdit() {
	document.location.href = "servicesDownTracker.do?method=initServicesDownTracker";
}

function resetMessages() {
	document.getElementById("servicesId").selectedIndex = "";
	$("#date").datepicker({dateFormat:"dd/mm/yy"}).datepicker("setDate",new Date());
	document.getElementById("downFrom").value = "";
	document.getElementById("downTill").value = "";
	document.getElementById("remarks").value = "";
	document.getElementById("errorMessage").value = "";

	resetErrMsgs();
}

function editServicesDownTracker(id) {
	document.location.href = "servicesDownTracker.do?method=editServicesDownTracker&id="+id;
}

function deleteServicesDownTracker(id) {
	deleteConfirm = confirm("Are you sure you want to delete this entry?")
	if (deleteConfirm) {
		document.location.href = "servicesDownTracker.do?method=deleteServicesDownTracker&id="+id;
	}
}

function displayAccServices(id) {
	document.location.href = "servicesDownTracker.do?method=displayAccServices&servicesId="+id;
}

</script>
<html:form action="/servicesDownTracker" method="post">
<html:hidden property="formName" value="servicesDownTrackerForm" />
<html:hidden property="pageType" value="1" />
<html:hidden property="id" />


	<c:choose>
		<c:when test="${trackerOperation == 'edit'}">
			<html:hidden property="method" styleId="method" value="updateServicesDownTracker" />
		</c:when>
		<c:otherwise>
			<html:hidden property="method" styleId="method" value="addServicesDownTracker" />
		</c:otherwise>
	</c:choose>
	
	<table width="100%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message
				key="knowledgepro.news.events.sendmail.admin.to.name" /> <span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.services.down.tracker" /></span></span></td>
		</tr>
		<tr>
			<td valign="top">
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
						<td background="images/Tcenter.gif" class="heading_white">
							<bean:message key="knowledgepro.services.down.tracker" />
						</td>
						<td width="10">
							<img src="images/Tright_1_01.gif" width="9" height="29">
						</td>
					</tr>				
					<tr>
						<td height="30" valign="top" background="images/Tright_03_03.gif"></td>
						<td valign="top" class="news">
							<div align="right">
								<FONT color="red">
									<span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields"/></span>
								</FONT>
							</div>
							<div id="errorMessage">
								<FONT color="red"><html:errors /></FONT>
								<FONT color="green">
									<html:messages id="msg"	property="messages" message="true">
										<c:out value="${msg}" escapeXml="false"></c:out><br>
									</html:messages>
								</FONT>
							</div>
						</td>
						<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
					</tr>					

					<tr>
						<td height="30" valign="top" background="images/Tright_03_03.gif"></td>
						<td valign="top" class="news">
							<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
								<tr>
									<td><img src="images/01.gif" width="5" height="5" /></td>
									<td width="100%" background="images/02.gif"></td>
									<td><img src="images/03.gif" width="5" height="5" /></td>
								</tr>
								<tr>
									<td width="5" background="images/left.gif"></td>
									<td valign="top">
										<table width="100%" cellspacing="1" cellpadding="2">							
											<tr>
											<td  width="20%" class="row-odd" align="right"><span class="Mandatory">*</span>&nbsp;<bean:message key="knowledgepro.services" />:</td>
											<td width="30%" height="25" class="row-even">
												<div align="left">
												
													<html:select property="servicesId" styleClass="comboLarge" styleId="servicesId" name="servicesDownTrackerForm" onchange="displayAccServices(this.value)">
													<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
													<c:if test="${servicesMap!=null && servicesMap!=''}">
														<html:optionsCollection name="servicesMap" label="value" value="key" />
													</c:if>
													</html:select>
												
												</div>
											</td>
											<td width="20%" height="25" class="row-odd"><div align="right"><span class="Mandatory">*</span>&nbsp;
												<bean:message key="knowledgepro.admission.date"/></div></td>
												<td width="30%" class="row-even">
												<input type="hidden" id="dt" name="dt" value="<bean:write name="servicesDownTrackerForm" property="date"/>" />
												<html:text  property="date" styleId="date" size="20" maxlength="16"/>
												<c:choose>
													<c:when test="${trackerOperation != 'edit'}">
	                                    				<script language="JavaScript">
		                                    				$("#date").datepicker({dateFormat:"dd/mm/yy"}).datepicker("setDate",new Date());
	                                     				</script>
	                                     			</c:when>
	                                     			<c:otherwise>
	                                     				<script language="JavaScript">
		                                    				$("#date").datepicker({dateFormat:"dd/mm/yy"}).datepicker();
	                                     				</script>
	                                     			</c:otherwise>
                                     			</c:choose>
                                     			
												</td>
											</tr>
											<tr>
												<td  width="20%" class="row-odd"><div align="right"><span class="Mandatory">*</span>&nbsp;
												Down From:</div></td>
												<td  width="30%" class="row-even">
													<html:text name="servicesDownTrackerForm" property="downFrom" styleId="downFrom" onkeypress="return checkNumeric(event)"
													size="20" maxlength="10" />
													<script>
													  $(function() {
														$("#downFrom").timepicker({
															showPeriod: true,
														    showLeadingZero: true
															});
													  });
													</script>
												</td>
												<td width="20%" class="row-odd" ><div align="right"><span class="Mandatory">*</span>&nbsp;
												Down Till:</div></td>
												<td   class="row-even" width="30%" align="left">
													<html:text name="servicesDownTrackerForm" property="downTill" styleId="downTill" onkeypress="return checkNumeric(event)"
													size="20" maxlength="10" />
													<script>
													  $(function() {
														$("#downTill").timepicker({
															showPeriod: true,
														    showLeadingZero: true
															});
													  });
													</script>
												</td>
											</tr>
											
											<tr>
									
												<td width="50%" height="25" class="row-odd" colspan="2">
												<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message key="knowledgepro.hostel.adminmessage.remarks"/>:</div>
												</td>
												<td width="50%" height="25" class="row-even" colspan="2">
												<html:textarea property="remarks" styleClass="TextBox" styleId="remarks" 
												cols="30" onkeypress="return imposeMaxLength(event,this)"></html:textarea>
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
						</td>
						<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
					</tr>
					
					<tr>
						<td height="35" valign="top" background="images/Tright_03_03.gif"></td>
						<td valign="top" class="news">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									
							<c:choose>
								<c:when test="${trackerOperation == 'edit'}">
								<td width="48%" height="35">
									<div align="right">
										<html:submit property="" styleClass="formbutton" value="Update" styleId="Submit"></html:submit>
									</div>
								</td>
								
								<td width="2%"></td>
								<td width="50%">									
									<div align="left">
										<html:button property="" styleClass="formbutton" onclick="cancellEdit()">
												<bean:message key="knowledgepro.cancel" /></html:button>
									</div>
								</td>
								</c:when>
								<c:otherwise>
								<td width="48%" height="35">
									<div align="right">
										<html:submit property="" styleClass="formbutton" value="Submit"  styleId="Submit"></html:submit>
									</div>
								</td>
								<td width="2%"></td>
								<td>									
									<div align="left">
										<html:button property="" styleClass="formbutton" onclick="resetMessages()">
												<bean:message key="knowledgepro.reset" /></html:button>
									</div>
								</td>
								<td width="2%"></td>
								<td width="48%">									
									<div align="left">
										<html:button property="" styleClass="formbutton" onclick="cancellAdd()">
												<bean:message key="knowledgepro.cancel" /></html:button>
									</div>
								</td>
								</c:otherwise>

							</c:choose>
										
							</tr>
							</table>
						</td>
						<td width="10" valign="top" background="images/Tright_3_3.gif" class="news"></td>
					</tr>
					
					<logic:notEmpty name="servicesDownTrackerForm" property="servicesDownTrackerTOList">
					<tr>
					<td height="20" valign="top" background="images/Tright_03_03.gif"></td>
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
									<td height="25" class="row-odd">
									<div align="center"><bean:message key="knowledgepro.slno" /></div>
									</td>
									<td align="center" class="row-odd"><bean:message key="knowledgepro.services" /></td>
									<td align="center" class="row-odd">Date</td>
									<td align="center" class="row-odd">Down From</td>
									<td align="center" class="row-odd">Down Till</td>
									<td align="center" class="row-odd"><bean:message key="knowledgepro.hostel.adminmessage.remarks" /></td>
									<td class="row-odd">
									<div align="center"><bean:message key="knowledgepro.edit" />
									</div>
									</td>
									<td class="row-odd">
									<div align="center"><bean:message key="knowledgepro.delete" /></div>
									</td>
								
								</tr>
								<c:set var="temp" value="0" />
									<logic:iterate id="tra" name="servicesDownTrackerForm"
										property="servicesDownTrackerTOList"
										type="com.kp.cms.to.admin.ServicesDownTrackerTO" indexId="count">
										<c:choose>
											<c:when test="${temp == 0}">
											<tr>
											<td width="5%" height="25" class="row-even">
											<div align="center"><c:out value="${count + 1}" /></div>
											</td>
											<td align="center" width="25%" class="row-even">
												<bean:write	name="tra" property="serviceName" />
											</td>
											<td align="center" width="12%" class="row-even">
												<bean:write name="tra" property="date" />
											</td>
											<td align="center" width="14%" class="row-even">
												<bean:write name="tra" property="downFrom" />
											</td>
											<td align="center" width="14%" class="row-even">
												<bean:write name="tra" property="downTill" />
											</td>
											<td align="center" width="25%" class="row-even">
												<bean:write name="tra" property="remarks" />
											</td>
											<td align="center" width="5%" height="25" class="row-even">
											<div align="center"><img src="images/edit_icon.gif"
												width="16" height="18" style="cursor: pointer"
												onclick="editServicesDownTracker('<bean:write name="tra" property="id" />')" /></div>
											</td>
											<td width="5%" height="25" class="row-even">
											<div align="center"><img src="images/delete_icon.gif"
												width="16" height="16" style="cursor: pointer"
												onclick="deleteServicesDownTracker('<bean:write name="tra" property="id"/>')"></div>
											</td>
										
										</tr>
										<c:set var="temp" value="1" />
										</c:when>
											<c:otherwise>
												<tr>
											<td width="5%" height="25" class="row-white">
												<div align="center"><c:out value="${count + 1}" /></div>
											</td>
											<td align="center" width="25%" class="row-white">
												<bean:write	name="tra" property="serviceName" />
											</td>
											<td align="center" width="12%" class="row-white">
												<bean:write name="tra" property="date" />
											</td>
											<td align="center" width="14%" class="row-white">
												<bean:write name="tra" property="downFrom" />
											</td>
											<td align="center" width="14%" class="row-white">
												<bean:write name="tra" property="downTill" />
											</td>
											<td align="center" width="25%" class="row-white">
												<bean:write name="tra" property="remarks" />
											</td>
											<td align="center" width="5%" height="25" class="row-white">
											<div align="center"><img src="images/edit_icon.gif"
												width="16" height="18" style="cursor: pointer"
												onclick="editServicesDownTracker('<bean:write name="tra" property="id" />')" /></div>
											</td>
											<td width="5%" height="25" class="row-white">
											<div align="center"><img src="images/delete_icon.gif"
												width="16" height="16" style="cursor: pointer"
												onclick="deleteServicesDownTracker('<bean:write name="tra" property="id"/>')"></div>
											</td>
										
										</tr>
										<c:set var="temp" value="0" />
											</c:otherwise>
										</c:choose>
									</logic:iterate>
								
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
				</logic:notEmpty>
				
					<tr>
						<td height="3" valign="top" background="images/Tright_03_03.gif"></td>
						<td valign="top" class="news">&nbsp;</td>
						<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
					</tr>
					<tr>
						<td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
						<td width="0" background="images/TcenterD.gif"></td>
						<td><img src="images/Tright_02.gif" width="9" height="29"></td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</html:form>
