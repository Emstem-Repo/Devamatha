<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<script language="JavaScript" >
function getLeaveTaken(){
	var empId=document.getElementById("employeeId").value;
	var stDate=document.getElementById("startDate").value;
	if((empId!=null && empId!='') && (stDate!=null && stDate!='')){
		getLeavesTakenInaMonth(empId,stDate,updateLeaveTaken);
	}
}
function updateLeaveTaken(req) {
	var responseObj = req.responseXML.documentElement;
	var value = responseObj.getElementsByTagName("value");
	if(value!=null){
		for ( var I = 0; I < value.length; I++) {
			if(value[I].firstChild!=null){
			var temp = value[I].firstChild.nodeValue;
			document.getElementById("takenMonth").innerHTML =" No of Leaves Taken in this Month "+ temp;
			}
		}
	}
}

function resetMessages() {
	document.location.href="employeeOnlineLeave.do?method=initOnlineLeave";
}

function getDateObject(dateString,dateSeperator)
{
	//This function return a date object after accepting
	//a date string ans dateseparator as arguments
	var curValue=dateString;
	var sepChar=dateSeperator;
	var curPos=0;
	var cDate,cMonth,cYear;
	
	//extract day portion
	curPos=dateString.indexOf(sepChar);
	cDate=dateString.substring(0,curPos);
	
	//extract month portion
	endPos=dateString.indexOf(sepChar,curPos+1); cMonth=dateString.substring(curPos+1,endPos);
	
	//extract year portion
	curPos=endPos;
	endPos=curPos+5;
	cYear=curValue.substring(curPos+1,endPos);
	
	//Create Date Object
	dtObject=new Date(cYear,cMonth,cDate);
	return dtObject;
}
function checkDayDiff(){
	var st=document.getElementById("startDate").value;
	var en=document.getElementById("endDate").value;
	if(st!=null && st!='' && en!=null && en!=''){
		var startDate = getDateObject(st,"/");
		var endDate = getDateObject(en,"/");
		var oneDay=1000*60*60*24;
		var diff=Math.ceil((endDate.getTime()-startDate.getTime())/oneDay);
		if(diff == 0){
			document.getElementById("showHalfDay").style.display="block";
		}else{
			document.getElementById("showHalfDay").style.display="none";
			document.getElementById("showAm").style.display="none";
		}
	}
}
function showOrHide(value){
	if(value=='yes'){
		document.getElementById("showAm").style.display="block";
	}else{
		document.getElementById("showAm").style.display="none";
	}
}

</script>
<html:form action="/employeeOnlineLeave">
	<html:hidden property="method" styleId="method" value="applyLeave" />	
	<html:hidden property="pageType" value="1" />
	<html:hidden property="formName" value="employeeOnlineLeaveForm" />
	<html:hidden property="empTypeId" styleId="empTypeId" name="employeeOnlineLeaveForm" />
	<html:hidden property="employeeId" styleId="employeeId" name="employeeOnlineLeaveForm" />
	<html:hidden property="halfDayDisplay" styleId="halfDayDisplay" name="employeeOnlineLeaveForm" />
	<html:hidden property="focusValue" styleId="focusValue"/>
	<html:hidden property="amDisplay" styleId="amDisplay" name="employeeOnlineLeaveForm" />
	<table width="98%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message key="knowledgepro.admin.sec.EmployeeCategory" />
			<span class="Bredcrumbs">&gt;&gt; Online Apply Leave &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="10"><img src="images/Tright_03_01.gif"></td>
					<td width="100%" background="images/Tcenter.gif" class="body">
					<div align="left"><strong class="boxheader">Online Apply Leave</strong></div>
					</td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
						<tr>
							<td colspan="6" align="left">
							<div align="right"><span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields" /></span></div>
							<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
							<FONT color="green"><html:messages id="msg"
								property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
								<br>
							</html:messages></FONT></div>
							</td>
						</tr>
						<tr>
							<td><img src="images/01.gif" width="5" height="5" /></td>
							<td width="914" background="images/02.gif"></td>
							<td><img src="images/03.gif" width="5" height="5" /></td>
						</tr>
						<tr>
						
							<td width="5" background="images/left.gif"></td>
							<td width="100%" valign="top">
							<table width="100%" cellspacing="1" cellpadding="2">
								
								<tr>
									<td height="25" width="20%" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message key="knowledgepro.employee.holidays.startDate" />:</div>
									</td>
									<td class="row-even" width="25%">
									<html:text name="employeeOnlineLeaveForm" property="startDate" styleId="startDate" size="10" maxlength="16" onchange="checkDayDiff(),getLeaveTypeForEmployee(),getLeaveTaken()"/>
									<script
									language="JavaScript">
									new tcal( {
										// form name
										'formname' :'employeeOnlineLeaveForm',
										// input name
										'controlname' :'startDate'
									});
									</script>
									</td>
									<td height="25" class="row-odd" width="20%">
									<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message key="knowledgepro.employee.holidays.endDate" />:</div>
									</td>
									<td height="25" class="row-even" width="25%">
									<html:text property="endDate" styleId="endDate" size="10" maxlength="11"  onchange="checkDayDiff()"></html:text>
									<script language="JavaScript">
										new tcal( {
											// form name
											'formname' :'employeeOnlineLeaveForm',
											// input name
											'controlname' :'endDate'
										});
									</script>
									</td>
								</tr>
								<tr>
									<td class="row-even">
										<div id="showHalfDay">
											Is Half Day : &nbsp;&nbsp;&nbsp;
											<html:radio property="isHalfday" styleId="isHalfday_1" value="yes" onclick="showOrHide(this.value)">yes</html:radio>
											<html:radio property="isHalfday" styleId="isHalfday_2" value="no" onclick="showOrHide(this.value)">no</html:radio>
										</div>
									</td>
									<td class="row-even" >
										<div id="showAm">
											<html:radio property="isAm" styleId="isAM_1" value="am">am</html:radio>
											<html:radio property="isAm" styleId="isAM_2" value="pm">pm</html:radio>
										</div>
									</td>
									<td height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message
										key="knowledgepro.employee.leave.allotment.leaveType" />:</div>
									</td>
									<td height="25" class="row-even"><label>
									<html:select property="leaveTypeId" styleId="leaveTypeId" styleClass="combo">
										<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
										<logic:notEmpty name="leaveTypes" scope="session">
											<html:optionsCollection name="leaveTypes" label="value" value="key" />
										</logic:notEmpty>
									</html:select> 
									</label></td>
								</tr>
								<tr>
									<td class="row-even" align="right"><span class="Mandatory">*</span>
									Reason
									</td>
									<td class="row-even"align="left">
										<html:textarea property="reason" cols="25" rows="4" ></html:textarea>
									</td>
									<td class="row-even">
										<div id="takenMonth"></div>
									</td>
									<td class="row-even"> </td>
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
						<tr>
							<td width="46%" height="35">
							<div align="right"><html:submit styleClass="formbutton">
								<bean:message key="knowledgepro.submit" />
							</html:submit></div>
							</td>
							<td width="2%"></td>
							<td width="52%" align="left"><html:button property=""
								styleClass="formbutton" onclick="resetMessages()">
								<bean:message key="knowledgepro.admin.reset" />
								</html:button> 
							</td>
						</tr>
					</table>
					</div>
					<logic:notEmpty name="employeeOnlineLeaveForm" property="leaveInstructionsTo">
					<table>
					
					<tr>
					<td class="Bredcrumbs" align="left" style="font-size: 12px;" >Note:</td>
					</tr>
					<logic:iterate id="id" name="employeeOnlineLeaveForm" property="leaveInstructionsTo">
					<tr>
					<td></td>
					<td class="Bredcrumbs">&nbsp;&nbsp;
					<!--<bean:write name="id" property="description"/>
					--><c:out
						value='${id.description}' escapeXml='false' />
					</td>
					</tr>
					</logic:iterate>
					</table>
					</logic:notEmpty>
							<logic:notEmpty name="details" scope="request">
								<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
						              <tr>
						                <td ><img src="images/01.gif" width="5" height="5" /></td>
						                <td width="914" background="images/02.gif"></td>
						                <td><img src="images/03.gif" width="5" height="5" /></td>
						              </tr>
						              <tr>
						                <td width="5"  background="images/left.gif"></td>
						                <td valign="top">
						         			<table width="100%" cellpadding="1" cellspacing="2">
												
													<tr class="row-odd" height="25">
														<td> Leave Type</td>
														<td> Leaves Allocated</td>
														<td> Leaves Sanctioned</td>
														<td> Leaves Remaining</td>
													</tr>
													<logic:iterate id="to"name="details" scope="request">
														<tr class="row-even" height="25">
															<td><bean:write name="to" property="empLeaveTypeName"/> </td>
															<td><bean:write name="to" property="leavesAllocated"/> </td>
															<td><bean:write name="to" property="leavesSanctioned"/> </td>
															<td><bean:write name="to" property="leavesRemaining"/> </td>
														</tr>
													</logic:iterate>
												
										</table>
							            </td>
							               <td width="5" height="30"  background="images/right.gif"></td>
							              </tr>
							              <tr>
							                <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
							                <td background="images/05.gif"></td>
							                <td><img src="images/06.gif" /></td>
							              </tr>
						            </table>
						      </logic:notEmpty>		
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				<tr>
					<td><img src="images/Tright_03_05.gif" height="29" width="9"></td>
					<td background="images/TcenterD.gif" width="100%"></td>
					<td><img src="images/Tright_02.gif" height="29" width="9"></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</html:form>
<script type="text/javascript">

var showET = document.getElementById("halfDayDisplay").value;
if( showET =="true") {
	document.getElementById("showHalfDay").style.display = "block";
}else{
	document.getElementById("showHalfDay").style.display = "none";
	
}
var showAT = document.getElementById("amDisplay").value;
if(showAT.length != 0 && showAT == "true") {
	document.getElementById("showAm").style.display = "block";
}else{
	document.getElementById("showAm").style.display = "none";
}
</script>