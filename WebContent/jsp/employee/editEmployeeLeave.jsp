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
function getLeaveTypeDetails(){
	var empId=document.getElementById("employeeId").value;
	var stDate=document.getElementById("startDate").value;
	var empTypeId=document.getElementById("empTypeId").value;
	if(empId==null || empId=='' || empId=="0"){
			alert("Please Enter Emp Code Or Employee Id");
	}
	if(stDate==null || stDate==''){
		alert("Please Enter Start Date");
	}
	if((empId!=null && empId!='') && (stDate!=null && stDate!='') && (empTypeId!=null && empTypeId!='' && empTypeId!='0')){
		var url = "employeeApplyLeave.do?method=getDetails&employeeId="+empId+"&startDate="+stDate;
		myRef = window
				.open(url, "viewPrivileges",
						"left=20,top=20,width=900,height=600,toolbar=1,resizable=0,scrollbars=1");
	}
}
function getLeaveTypeForEmployee(){
	resetOption("leaveTypeId");
	var empId=document.getElementById("employeeId").value;
	var empTypeId=document.getElementById("empTypeId").value;
	var stDate=document.getElementById("startDate").value;
	var exemption="";
	if(document.getElementById("isExemption_1").checked){
		exemption=document.getElementById("isExemption_1").value;
	}else{
		exemption=document.getElementById("isExemption_2").value;
	}
	if((empId!=null && empId!='') && (empTypeId!=null && empTypeId!='' && empTypeId!='0') && (stDate!=null && stDate!='') && (exemption!=null && exemption!=''))
		getLeaveTypesForEmployee(empId,empTypeId,stDate,exemption,"leaveTypeId",updateLeaveTypeForEmployee);
}
function updateLeaveTypeForEmployee(req) {
	updateOptionsFromMap(req,"leaveTypeId","- Select -");
}
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

function getEmpDetails() {
	resetOption("leaveTypeId");
	document.getElementById("empName").innerHTML = "";
    document.getElementById("depName").innerHTML = "";
    document.getElementById("desName").innerHTML = "";
    document.getElementById("notValid").innerHTML = "";
	var empCode=document.getElementById("empCode").value;
	var fingerPrintId=document.getElementById("fingerPrintId").value;
	if((empCode!=null && empCode!='') || (fingerPrintId!=null && fingerPrintId!=''))
		getEmployeeDetails(empCode,fingerPrintId,updateEmpDetails);		
}

function updateEmpDetails(req) {
	var responseObj = req.responseXML.documentElement;
	var value = responseObj.getElementsByTagName("value");
	var isMsg=false;
	if(value!=null){
		for ( var I = 0; I < value.length; I++) {
			if(value[I].firstChild!=null){
			var temp = value[I].firstChild.nodeValue;
			document.getElementById("notValid").innerHTML = temp;
			document.getElementById("employeeId").value = "0";
			document.getElementById("empTypeId").value = "0";
			isMsg=true;
			}
		}
		}
			if(isMsg!=true){
				var items = responseObj.getElementsByTagName("empDetails");
				for (var i = 0 ; i < items.length ; i++) {
					if(items[i]!=null){
				         var name = items[i].getElementsByTagName("name")[0].firstChild.nodeValue;
					     var department= items[i].getElementsByTagName("departmentName")[0].firstChild.nodeValue;
					     var designationName = items[i].getElementsByTagName("designationName")[0].firstChild.nodeValue;
					     var empId = items[i].getElementsByTagName("empId")[0].firstChild.nodeValue;
					     var empTypeId = items[i].getElementsByTagName("empTypeId")[0].firstChild.nodeValue;
					     document.getElementById("employeeId").value = empId;
					     document.getElementById("empTypeId").value = empTypeId;
					     document.getElementById("empName").innerHTML = name;
					     document.getElementById("depName").innerHTML = department;
					     document.getElementById("desName").innerHTML = designationName;
					}
			   }
		}
			getLeaveTypeForEmployee();
			getLeaveTaken();	
}
function cancelPage() {
	document.location.href="ModifyEmployeeLeave.do?method=viewEmployeeLeaveDetails";
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
<html:form action="/ModifyEmployeeLeave">
	<html:hidden property="method" styleId="method" value="updateLeave" />	
	<html:hidden property="pageType" value="1" />
	<html:hidden property="formName" value="ModifyEmployeeLeaveForm" />
	<html:hidden property="empTypeId" styleId="empTypeId" name="ModifyEmployeeLeaveForm" />
	<html:hidden property="employeeId" styleId="employeeId" name="ModifyEmployeeLeaveForm" />
	<html:hidden property="halfDayDisplay" styleId="halfDayDisplay" name="ModifyEmployeeLeaveForm" />
	<html:hidden property="amDisplay" styleId="amDisplay" name="ModifyEmployeeLeaveForm" />
	<table width="98%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message key="knowledgepro.admin.sec.EmployeeCategory" />
			<span class="Bredcrumbs">&gt;&gt; Modify Employee  Leave &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="10"><img src="images/Tright_03_01.gif"></td>
					<td width="100%" background="images/Tcenter.gif" class="body">
					<div align="left"><strong class="boxheader">Modify Employee  Leave</strong></div>
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
								<tr height="25">
									<td colspan="3" class="row-odd" align="right">
										<bean:message key="knowledgepro.employee.leave.isExemption"/>:
									</td>
									<td colspan="3" class="row-even" align="left">
										<html:radio property="isExemption" styleId="isExemption_1" value="yes" onclick="getLeaveTypeForEmployee()">Yes</html:radio>
										<html:radio property="isExemption" styleId="isExemption_2" value="no" onclick="getLeaveTypeForEmployee()">No</html:radio>
									</td>
								</tr>
								<tr>
		 							<td height="25" class="row-odd"><div align="right"><bean:message key="employee.info.code"/>:</div></td>
		                             <td height="25" class="row-even" align="left">
		                             <span class="star">
		                               <html:text property="empCode" styleId="empCode" name="ModifyEmployeeLeaveForm" maxlength="9"/>
		                             </span></td>							
									<td width="5%" height="25" class="row-even" align="center">OR</td>
		                             <td height="25" class="row-odd"><div align="right" id="regLabel"><bean:message key="knowledgepro.employee.leave.employeeId"/>:</div></td>
		                             <td height="25" class="row-even" align="left"><span class="star">
		                               <html:text property="fingerPrintId" styleId="fingerPrintId" name="ModifyEmployeeLeaveForm" maxlength="10"/>
		                             </span></td>
		                             <td class="row-even">
		                             	<div id="notValid"></div>
		                             </td>
	                            </tr>
							  	<tr height="25">
							  		<td class="row-odd" align="right">
							  			Employee Name:
							  		</td>
							  		<td class="row-even" align="left">
							  			<div id="empName">
							  				<bean:write name="ModifyEmployeeLeaveForm" property="employeeName"/>
							  			</div>
							  		</td>
							  		<td class="row-odd" align="right">
							  			Department Name:
							  		</td>
							  		<td class="row-even" align="left">
							  			<div id="depName">
							  				<bean:write name="ModifyEmployeeLeaveForm" property="departmentName"/>
							  			</div>
							  		</td>
							  		<td class="row-odd"  align="right">
							  			Designation:
							  		</td>
							  		<td class="row-even" align="left">
							  			<div id="desName">
							  				<bean:write name="ModifyEmployeeLeaveForm" property="designationName"/>
							  			</div>
							  		</td>
							  	</tr>
								<tr>
									<td height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message key="knowledgepro.employee.holidays.startDate" />:</div>
									</td>
									<td class="row-even">
									<html:text name="ModifyEmployeeLeaveForm" property="startDate" styleId="startDate" size="10" maxlength="16" onchange="checkDayDiff(),getLeaveTypeForEmployee(),getLeaveTaken()"/>
									<script
									language="JavaScript">
									new tcal( {
										// form name
										'formname' :'ModifyEmployeeLeaveForm',
										// input name
										'controlname' :'startDate'
									});
									</script>
									</td>
									<td height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message key="knowledgepro.employee.holidays.endDate" />:</div>
									</td>
									<td height="25" class="row-even">
									<html:text property="endDate" styleId="endDate" size="10" maxlength="11"  onchange="checkDayDiff()"></html:text>
									<script language="JavaScript">
										new tcal( {
											// form name
											'formname' :'ModifyEmployeeLeaveForm',
											// input name
											'controlname' :'endDate'
										});
									</script>
									</td>
									<td height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message
										key="knowledgepro.employee.leave.allotment.leaveType" />:</div>
									</td>
									<td height="25" class="row-even"><label>
									<html:select property="leaveTypeId" styleId="leaveTypeId" styleClass="combo">
										<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
										<logic:notEmpty name="ModifyEmployeeLeaveForm" property="leaveTypes">
											<html:optionsCollection property="leaveTypes" label="name" value="id" />
										</logic:notEmpty>
									</html:select> 
									</label></td>
								</tr>
								<tr>
									<td class="row-even" colspan="3">
										<div id="showHalfDay">
											Is Half Day : &nbsp;&nbsp;&nbsp;
											<html:radio property="isHalfday" styleId="isHalfday_1" value="yes" onclick="showOrHide(this.value)">yes</html:radio>
											<html:radio property="isHalfday" styleId="isHalfday_2" value="no" onclick="showOrHide(this.value)">no</html:radio>
										</div>
									</td>
									<td class="row-even" colspan="3">
										<div id="showAm">
											<html:radio property="isAm" styleId="isAM_1" value="am">am</html:radio>
											<html:radio property="isAm" styleId="isAM_2" value="pm">pm</html:radio>
										</div>
									</td>
								</tr>
								<tr>
									<td colspan="2" class="row-even">
										<div id="takenMonth"></div>
									</td>
									<td class="row-even" align="right">
									Reason
									</td>
									<td class="row-even" colspan="2" align="left">
										<html:textarea property="reason" cols="25" rows="4" ></html:textarea>
									
									</td>
									<td class="row-even"></td>
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
								styleClass="formbutton" onclick="cancelPage()">
								<bean:message key="knowledgepro.admin.cancel" />
								</html:button> 
							</td>
						</tr>
					</table>
					</div>
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