<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<link type="text/css"
	href="jquery/css/ui-lightness/jquery-ui-1.8.18.custom.css" rel="stylesheet" />
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
</style>
<html:html>
<script type="text/javascript">
function getDetails() {
	document.getElementById("method").value="getHostelLeaveApprovalDetails";
}
function selectAll(obj) {
    var value = obj.checked;
    var inputs = document.getElementsByTagName("input");
    var inputObj;
    var checkBoxselectedCount = 0;
    for(var count1 = 0;count1<inputs.length;count1++) {
          inputObj = inputs[count1];
          var type = inputObj.getAttribute("type");
            if (type == 'checkbox') {
                  inputObj.checked = value;
                  inputObj.value="on";
            }
    }
}

function unCheckSelectAll() {
    var inputs = document.getElementsByTagName("input");
    var inputObj;
    var checkBoxOthersSelectedCount = 0;
    var checkBoxOthersCount = 0;
    for(var count1 = 0;count1<inputs.length;count1++) {
          inputObj = inputs[count1];
          var type = inputObj.getAttribute("type");
            if (type == 'checkbox' && inputObj.id != "checkAll") {
                  checkBoxOthersCount++;
                  if(inputObj.checked) {
                        checkBoxOthersSelectedCount++;
                        inputObj.value="on";
                  }else{
                	  inputObj.value="off";	
                      }   
            }
    }
    if(checkBoxOthersCount != checkBoxOthersSelectedCount) {
      document.getElementById("checkAll").checked = false;
    } else {
      document.getElementById("checkAll").checked = true;
    }
}
function leaveApplication(registerNo,studentName,className){
	var url = "hostelLeaveApproval.do?method=getDetailsList&regNo="+registerNo+"&propertyName=NoofApplication&studentName="+studentName+"&className="+className;
	myRef = window .open(url, "ViewLeaveApproval", "left=20,top=20,width=900,height=900,toolbar=1,resizable=0,scrollbars=1");
}
function leaveApproval(registerNo,studentName,className){
	var url = "hostelLeaveApproval.do?method=getDetailsList&regNo="+registerNo+"&propertyName=Approved&studentName="+studentName+"&className="+className;
	myRef = window .open(url, "ViewLeaveApproval", "left=20,top=20,width=900,height=900,toolbar=1,resizable=0,scrollbars=1");
}
function leaveRejected(registerNo,studentName,className){
	var url = "hostelLeaveApproval.do?method=getDetailsList&regNo="+registerNo+"&propertyName=Rejected&studentName="+studentName+"&className="+className;
	myRef = window .open(url, "ViewLeaveApproval", "left=20,top=20,width=900,height=900,toolbar=1,resizable=0,scrollbars=1");
}
function leaveCancelled(registerNo,studentName,className){
	var url = "hostelLeaveApproval.do?method=getDetailsList&regNo="+registerNo+"&propertyName=Cancelled&studentName="+studentName+"&className="+className;
	myRef = window .open(url, "ViewLeaveApproval", "left=20,top=20,width=900,height=900,toolbar=1,resizable=0,scrollbars=1");
}
function approveDetails(){
	document.getElementById("method").value="submitApproveLeaveDetails";
}
function rejectDetails(){
	document.getElementById("method").value="submitRejectLeaveDetails";
}
function resetFields1() {
	resetFieldAndErrMsgs();
	var today = new Date();
    var dd = today.getDate();
    var mm = today.getMonth()+1; //January is 0!

    var yyyy = today.getFullYear();
    if(dd<10){dd='0'+dd} if(mm<10){mm='0'+mm} var today = dd+'/'+mm+'/'+yyyy;
	document.getElementById("fromDate").value = today;
	document.getElementById("toDate").value = today;
}
function cancel() {
	document.location.href = "hostelLeaveApproval.do?method=initHostelLeaveApproval";
}
function displayRejectButton(){
	document.getElementById("displayReasonButton").style.display = "block";
	document.getElementById("displayReasonButton1").style.display = "block";
}
function Cancel1(){
	document.getElementById("rejectReason").value = "";
	document.getElementById("displayReasonButton").style.display = "none";
	document.getElementById("displayReasonButton1").style.display = "none";
}
function imposeMaxLength1(field, size) {
    if (field.value.length > size) {
        field.value = field.value.substring(0, size);
    }
}
	
// to display the text areas length 
function len_display(Object,MaxLen,element){
    var len_remain = MaxLen+Object.value.length;
   if(len_remain <=250){
    document.getElementById(element).value=len_remain; }
}
function getBlock(hostelId){
	getBlockByHostel("blockMap",hostelId,"blockId",updateBlock);
	resetOption("unitId");
	}
function updateBlock(req) {
	updateOptionsFromMap(req,"blockId","- Select -");
}
function getUnit(blockId){
	getUnitByBlock("unitMap",blockId,"unitId",updateUnit);
	}
function updateUnit(req) {
	updateOptionsFromMap(req,"unitId","- Select -");
}
function cancelAction(){
	resetFieldAndErrMsgs();
	document.location.href = "LoginAction.do?method=loginAction";
}
</script>
<html:form action="/hostelLeaveApproval" method="post">
<html:hidden property="method" styleId="method" value=" "/>	
<html:hidden property="pageType" value="1" />
<html:hidden property="formName" value="hostelLeaveApprovalForm" />
<html:hidden property="flag" styleId="flag" name="hostelLeaveApprovalForm" />

<table width="100%" border="0">
  <tr>
    <td><span class="Bredcrumbs"><bean:message key="knowledgepro.hostel" />
			<span class="Bredcrumbs">&gt;&gt; Hostel Leave Approval &gt;&gt;</span></span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="body" ><strong class="boxheader">Hostel Leave Approval</strong></td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td height="20" class="news"><div align="right" class="mandatoryfield">*Mandatory fields</div>
        <div id="errorMessage"><FONT color="red"><html:errors /></FONT>
							<FONT color="green"><html:messages id="msg"
								property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
								<br>
							</html:messages></FONT></div>
        
        </td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td ><img src="images/01.gif" width="5" height="5" /></td>
            <td width="100%" background="images/02.gif"></td>
            <td><img src="images/03.gif" width="5" height="5" /></td>
          </tr>
          <tr>
            <td width="5"  background="images/left.gif"></td>
            <td valign="top">
            <table width="100%" cellspacing="1" cellpadding="2">
			<tr>
		        <td width="25%" class="row-odd" ><div align="right">
		        <bean:message key="knowledgepro.hostel.hostel.entry.name" /></div></td>
		        <td width="25%" class="row-even" >
		        <label>
					<html:select property="hostelId" styleId="hostelId" onchange="getBlock(this.value)">
								 	<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
								   		<html:optionsCollection property="hostelMap" label="value" value="key" />
								   	 </html:select>
					</label></td>
				<td class="row-odd" width="25%"><div align="right"><bean:message key="knowledgepro.employee.Status" />:</div></td>
				 <td  height="25" class="row-even" width="25%">
	               <div align="left">
	               <input type="hidden" id="tempStatus"  value="<bean:write name="hostelLeaveApprovalForm" property="status"/>"/>
                      <html:select property="status"  styleId="status"  name="hostelLeaveApprovalForm" >
                      <option value="Approved">Approved</option>
                      <option value="Pending">Pending</option>
                       <option value=" Rejected">Rejected</option>
                         <option value="Cancelled">Cancelled</option>
                	</html:select> </div>
                	</td>
	       </tr>
	        <tr>
			<td width="25%" class="row-odd"><div align="right">Block</div></td>
			<td width="25%" class="row-even"><input type="hidden" id="tempBlockId"  value="<bean:write name="hostelLeaveApprovalForm" property="blockId"/>"/>
			<html:select property="blockId" styleId="blockId" styleClass="combo" onchange="getUnit(this.value)">
			<html:option value=" "><bean:message key="knowledgepro.admin.select"/></html:option>
					<c:choose>
             		<c:when test="${blockMap != null}">
             		<html:optionsCollection name="blockMap" label="value" value="key" />
					</c:when>
					</c:choose>
					</html:select>
					</td>
			<td width="25%" class="row-odd" ><div align="right">
			Unit</div></td>
			<td width="25%" class="row-even" >
			<label>
			<input type="hidden" id="tempUnitId"  value="<bean:write name="hostelLeaveApprovalForm" property="unitId"/>"/>
			<html:select property="unitId" styleId="unitId" >
			<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
			<c:choose>
             <c:when test="${unitMap != null}">
             <html:optionsCollection name="unitMap" label="value" value="key" />
			</c:when>
			</c:choose></html:select></label></td>
			</tr>
           <tr>
             <td width="25%"  class="row-odd" align="right">
					 <span class="Mandatory">*</span>Leave From:</td>
		        <td width="25%" class="row-even">
						<html:text name="hostelLeaveApprovalForm" property="fromDate" styleId="fromDate" size="11" maxlength="11" />
							<script language="JavaScript">
							$(function(){
								 var pickerOpts = {
										 	            dateFormat:"dd/mm/yy"
										         };  
								  $.datepicker.setDefaults(
								    $.extend($.datepicker.regional[""])
								  );
								  $("#fromDate").datepicker(pickerOpts);
								});
							</script>
				</td>
               <td width="25%" class="row-odd">
					<div align="right"><span class="Mandatory">*</span>
					Leave Till:</div>
				</td>
				<td class="row-even" width="25%"> 
						<html:text name="hostelLeaveApprovalForm" property="toDate" styleId="toDate" size="11" maxlength="11" />
							<script language="JavaScript">
							$(function(){
								 var pickerOpts = {
										 	            dateFormat:"dd/mm/yy"
										         };  
								  $.datepicker.setDefaults(
								    $.extend($.datepicker.regional[""])
								  );
								  $("#toDate").datepicker(pickerOpts);
								});
							</script>
				</td>
              </tr>
              <tr>
              <td width="25%"  class="row-odd" >
				 	<div align="right"><bean:message key="knowledgepro.admin.course" />:</div>
				</td>
				 <td width="25%"  class="row-even" align="right">
		        <label>
					<html:select property="courseId" styleId="courseId" >
								 	<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
								   		<html:optionsCollection property="courseMap" label="value" value="key" />
								   	 </html:select>
					</label></td>
				<td height="25" class="row-odd" width="25%">
					<div align="right"><bean:message key="knowledgepro.certificate.course.Semester" />:</div>
				</td>
				<td class="row-even" width="25%">
					<html:text name="hostelLeaveApprovalForm" property="semesterNo" styleId="semesterNo" />
				</td>
			
              </tr>
            </table>
            
            </td>
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
        <td height="10" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="45%" height="35">
							<div align="right">
									<html:submit property="" styleClass="formbutton" onclick="getDetails()" value="Search"></html:submit>
										
							</div>
							</td>
							<td width="2%"></td>
							<td width="5%">
							<div align="left">
									<html:button property="" styleClass="formbutton" value="Reset"
										onclick="resetFields1()"></html:button>
							</div>
							</td>
							<td width="44%" ><html:button property="" styleClass="formbutton" value="Cancel" onclick="cancelAction()">
													</html:button>
							</td>
						</tr>
			</table>
		</td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      
     <logic:notEmpty property="hostelLeaveApprovalTo" name="hostelLeaveApprovalForm">
      <tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
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
									<td width="5%" height="25" class="row-odd">
									<div align="center"><bean:message key="knowledgepro.slno" /></div>
									</td>
								 	<td width="5%" align="center" height="25" class="row-odd">
									<input type="checkbox" id="checkAll" onclick="selectAll(this)"/> select
									</td>
									<td width="10%" align="center" height="25" class="row-odd">
									<div align="center">Register No</div>
									</td>
									<td width="25%" align="center" height="25" class="row-odd">
									<div align="center">Name</div>
									</td>
									<td width="8%" class="row-odd">
									<div align="center">Class</div>
									</td>
									<td width="12%" class="row-odd">
									<div align="center">Date and Time From</div>
									</td>
									<td width="12%" class="row-odd">
									<div align="center">Date and Time To</div>
									</td>
									<td width="6%" class="row-odd">
									<div align="center">No.of Applications Submitted</div>
									</td>
									<td width="6%" class="row-odd">
									<div align="center">No.of Leaves Approved</div>
									</td>
									<td width="6%" class="row-odd">
									<div align="center">No.of Leaves Rejected</div>
									</td>
									<td width="6%" class="row-odd">
									<div align="center">No.of Leaves Cancelled </div>
									</td>
								</tr>
								
									
									<nested:iterate id="to" property="hostelLeaveApprovalTo" name="hostelLeaveApprovalForm" indexId="count">
										<c:choose>
											<c:when test="${count%2 == 0}">
												<tr class="row-even">
											</c:when>
											<c:otherwise>
												<tr class="row-white">
											</c:otherwise>
										</c:choose>
										<td width="4%" height="25">
										<div align="center"><c:out value="${count + 1}" /></div>
										</td>
										 <td width="4%" height="25">
										<div align="center">
										<nested:checkbox property="checked" onclick="unCheckSelectAll()"> </nested:checkbox>
										</div>
										</td>
										<td align="center" height="25"><nested:write
											 property="registerNo" /></td>
										<td align="center" height="25"><nested:write
											 property="name" /></td>
										<td align="center" height="25"><nested:write
											 property="className" /></td>
										<td align="center" height="25"><nested:write
											 property="dateAndTimeFrom" /></td>
										<td align="center" height="25"><nested:write
											 property="dateAndTimeTo" /></td>
										<td align="center" height="25">
										<logic:notEqual property="noOfLeaveApplications" value="0" name="to">
												<a href="javascript:void(0)" onclick="leaveApplication('<bean:write name="to" property="registerNo"/>','<bean:write name="to" property="name"/>','<bean:write name="to" property="className"/>')">
												    <nested:write property="noOfLeaveApplications" />
										        </a>
										</logic:notEqual>
										<logic:equal property="noOfLeaveApplications" value="0" name="to">
													<nested:write property="noOfLeaveApplications" />
										</logic:equal>
										</td>
										<td align="center" height="25">
										<logic:notEqual property="noOfLeaveApproval" value="0" name="to">
												<a href="javascript:void(0)" onclick="leaveApproval('<bean:write name="to" property="registerNo"/>','<bean:write name="to" property="name"/>','<bean:write name="to" property="className"/>')">
												    <nested:write property="noOfLeaveApproval" />
										        </a>
										</logic:notEqual>
										<logic:equal property="noOfLeaveApproval" value="0" name="to">
												<nested:write property="noOfLeaveApproval" />
										</logic:equal>
										</td>
										<td align="center" height="25">
										<logic:notEqual property="noOfLeaveRejected" value="0" name="to">
												<a href="javascript:void(0)" onclick="leaveRejected('<bean:write name="to" property="registerNo"/>','<bean:write name="to" property="name"/>','<bean:write name="to" property="className"/>')">
												    <nested:write property="noOfLeaveRejected" />
										        </a>
										</logic:notEqual>
										<logic:equal property="noOfLeaveRejected" value="0" name="to">
												<nested:write property="noOfLeaveRejected" />
										</logic:equal>
										</td>
										<td align="center" height="25">
										<logic:notEqual property="noOfLeaveCancelled" value="0" name="to">
												<a href="javascript:void(0)" onclick="leaveCancelled('<bean:write name="to" property="registerNo"/>','<bean:write name="to" property="name"/>','<bean:write name="to" property="className"/>')">
												    <nested:write property="noOfLeaveCancelled" />
										        </a>
										</logic:notEqual>
										<logic:equal property="noOfLeaveCancelled" value="0" name="to">
												<nested:write property="noOfLeaveCancelled" />
										</logic:equal>
										</td>
									</nested:iterate>
								
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
					<td height="34" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="45%" height="35">
								<div align="right">
								 <logic:notEmpty property="hostelLeaveApprovalTo" name="hostelLeaveApprovalForm">
									<html:submit property="" styleClass="formbutton" onclick="approveDetails()" value="Approve"></html:submit>
								</logic:notEmpty>
								</div>
								</td>
								<td width="2%"></td>
								<td width="53%">
								 <logic:notEmpty property="hostelLeaveApprovalTo" name="hostelLeaveApprovalForm">
									<div align="left">
										<html:button property="" styleClass="formbutton" onclick="displayRejectButton()" value="Reject"></html:button> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<%--<html:button property="" styleClass="formbutton" onclick="Cancel()" value="Close"></html:button> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;--%>
									</div>
								</logic:notEmpty>
							</td>
						</tr>
						<tr>
							<td colspan="3">
								<div id="displayReasonButton">
									<table  cellpadding="0" cellspacing="0" width="100%"  >
										 <tr>
											<td width="35%" class="row-odd" colspan="3" align="right" ><span class="Mandatory">*</span>Reason: </td>
											 <td width="65%" class="row-even"  colspan="4" ><html:textarea property="rejectReason" style="width: 50%" styleId="rejectReason" cols="15" rows="2" onkeypress="return imposeMaxLength1(this, 249);" onkeyup="len_display(this,0,'long_len2')"></html:textarea>
											 <input type="text" id="long_len2" value="0" class="len2" size="2" readonly="readonly" style="border: none; background-color: #E3EBE5; text-align: right; font-size: 11px; font-family: Verdana, Arial, Helvetica, sans-serif">/250 Characters</td>
										 </tr>
									 </table>
								</div>
						   </td>
						</tr>
						<tr >
							<td colspan="3" height="30">
							<div id="displayReasonButton1" align="center">
										
							<html:submit property="" styleClass="formbutton" onclick="rejectDetails()" value="Submit"></html:submit> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<html:button property="" styleClass="formbutton" onclick="Cancel1()" value="Cancel"></html:button> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							</div>
							</td>
						 </tr>
						</table>
					</td>
					<td valign="top" class="news"></td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
      
      <tr>
        <td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
        <td width="0" background="images/TcenterD.gif"></td>
        <td><img src="images/Tright_02.gif" width="9" height="29"></td>
      </tr>
    </table></td>
  </tr>
</table>
</html:form>
<script type="text/javascript">
	var flag = document.getElementById("flag").value;
	if(flag == "true"){
		document.getElementById("displayReasonButton").style.display = "block";
		document.getElementById("displayReasonButton1").style.display = "block";
	}else{
		document.getElementById("displayReasonButton").style.display = "none";
		document.getElementById("displayReasonButton1").style.display = "none";
	}
	var status = document.getElementById("tempStatus").value;
	if (status != null && status.length != 0) {
		document.getElementById("status").value = status;
	}
	var blockId = document.getElementById("tempBlockId").value;
	if (blockId != null && blockId.length != 0) {
		document.getElementById("blockId").value = blockId;
	}
	var unitId = document.getElementById("tempUnitId").value;
	if (unitId != null && unitId.length != 0) {
		document.getElementById("unitId").value = unitId;
	}
</script>

</html:html>
