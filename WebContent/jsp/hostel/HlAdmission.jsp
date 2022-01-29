<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<script language="JavaScript" src="js/calendar.js"></script>
<link type="text/css"
	href="jquery/css/ui-lightness/jquery-ui-1.8.18.custom.css" rel="stylesheet" />
<script src="jquery/js/jquery-ui-1.10.0.custom.min.js" type="text/javascript"></script>
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

function addHlAdmission() {
	if(document.getElementById("reAdmId").checked==true){
		document.getElementById("method").value ="addHostelAdmission";
	}else{
		document.getElementById("method").value ="newHostelAdmission";
	}
}

function editHlAdmission(id,regno,applno) {
	document.location.href = "HostelAdmission.do?method=editHostelAdmission&id="+id+"&tRegNo="+regno+"&tApplNo="+applno;
}



function updateSubCategory() {
	document.getElementById("method").value = "updateHostelAdmission";
}
function HlAdmissionSearch(){
	document.getElementById("method").value="HostelAdmissionSearch";
}
function deleteHlAdmission(id) {
	document.location.href = "HostelAdmission.do?method=getCancelReason&id="+ id;
}

function printICard(id){
	var url ="HostelAdmission.do?method=printHlAdmission&id="+id;
	myRef = window.open(url,"collectionLedger","left=20,top=20,width=800,height=500,toolbar=1,resizable=0,scrollbars=1");
}
function getStudentName(){
	var regNo=document.getElementById("regNo").value;
	var appNo= document.getElementById("applNo").value;
	document.getElementById("seatAvailable").innerHTML ="";
	document.getElementById("studentName").innerHTML="";
	
	if((regNo!=null && regNo!='') || (appNo!=null && appNo!=''))
	{
		var args ="method=getStudentNameInHostel&regNo="+regNo+"&appNo="+appNo;
		var url ="AjaxRequest.do";
		requestOperation(url,args,updateStudentName);	
	}
}
function updateStudentName(req){
	var responseObj = req.responseXML.documentElement;
	 var value= responseObj.getElementsByTagName("name");
	 var temp="";
		if(value!=null){
			for ( var I = 0; I < value.length; I++) {
				if(value[I].firstChild!=null){
					temp = value[I].firstChild.nodeValue;
					document.getElementById("studentName").innerHTML =temp;
				    document.getElementById("tempStudentName").value =temp;
				}
			}
		}
		if(temp==null || temp==''){
			document.getElementById("errorMessage").innerHTML ="Student Is Not Admitted";
			document.getElementById("tempStudentName").value ='';
			document.getElementById("studentName").innerHTML ='';
		}else{
			document.getElementById("errorMessage").innerHTML ='';
		}
		var gender= responseObj.getElementsByTagName("gender");
		if(gender!=null){
			if(document.getElementById("reAdmId").checked){
				var admType=document.getElementById("reAdmId").value;
				document.location.href="HostelAdmission.do?method=checkStudentApplication&regNo="+document.getElementById("regNo").value+"&appNo="+document.getElementById("applNo").value+"&year="+document.getElementById("year").value+"&admissionType="+admType+"&studentName="+document.getElementById("tempStudentName").value;
			}else{
				var temp="";
				for ( var I = 0; I < value.length; I++) {
					if(gender[I].firstChild!=null){
						temp = gender[I].firstChild.nodeValue;
					}
				}
				document.getElementById("tempGender").value =temp;
				getHostelNameBygender(temp);
			}
		}
}
function checkStudentApplication(regNo,appNo){
	
	if(document.getElementById("reAdmId").checked){
		var args ="method=checkStudentApplication&regNo="+regNo+"&appNo="+appNo+"&year="+document.getElementById("year").value;
		var url ="AjaxRequest.do";
		requestOperation(url,args,updateHostelNames);
	}
}
function updateHostelNames(req){
	var responseObj = req.responseXML.documentElement;

	var applnNO= responseObj.getElementsByTagName("applnNO");
	if(applnNO!=null){
		var temp2="";
		for ( var I = 0; I < applnNO.length; I++) {
			if(applnNO[I].firstChild!=null){
				temp2 = applnNO[I].firstChild.nodeValue;
			}
		}
		document.getElementById("hlApplicationNo").value =temp2;
	}
	
	var hosteId= responseObj.getElementsByTagName("hostelId");
	if(hosteId!=null){
		var temp1="";
		for ( var I = 0; I < hosteId.length; I++) {
			if(hosteId[I].firstChild!=null){
				temp1 = hosteId[I].firstChild.nodeValue;
			}
		}
		document.getElementById("hostelName").value =temp1;
		document.getElementById("tempHostelId").value =temp1;
	}
	if(temp1 !=null && temp1 !=""){
		updateRoomType(req);
		var roomType= responseObj.getElementsByTagName("roomTypeId");
		if(roomType != null){
			for ( var I = 0; I < roomType.length; I++) {
				if(roomType[I].firstChild!=null){
					document.getElementById("roomTypeName").value = roomType[I].firstChild.nodeValue;
				}
			}
		}
	}else{
		document.getElementById("errmsg").innerHTML="<font style='font-weight: bold; color: red;'>This student is not selected for hostel admission.</font>";
	}	
}
function getHostelNameBygender(gender){
	var args = "method=getHostelBygender&hostelGender="+gender;
	var url ="AjaxRequest.do";
	requestOperation(url,args,updateHostelByGender);	
}
function updateHostelByGender(req) {
	updateOptionsFromMap(req, "hostelName", "- Select -");
}

function getRoomTypes(hostelId){
	var args = "method=getRoomTypeByHostelBYstudent&hostelId="+hostelId;
	var url ="AjaxRequest.do";
	requestOperation(url,args,updateRoomType);
}
function updateRoomType(req) {
	updateOptionsFromMap(req, "roomTypeName", "- Select -");
}

function setRoomByRoomType(roomTypeId){
	getRoomByRoomTypeId("roomMap",roomTypeId,"roomName",updateRooms);
}

function updateRooms(req) {
	updateOptionsFromMap(req, "roomName", "- Select -");
}

function getBedByRoomId(roomId){
	getBedByRoomId("bedMap",roomId,"bedNo",updateBeds);
}

function updateBeds(req) {
	updateOptionsFromMap(req, "bedNo", "- Select -");
}

function getNumberOfSeats(){
	var hostelId=document.getElementById("hostelName").value;
	var roomTypeId= document.getElementById("roomTypeName").value;
	var academicYear= document.getElementById("year").value;
	if(hostelId != null && hostelId !="" && roomTypeId != null && roomTypeId!=""){
		var args ="method=getNumberOfSeatsAvailable&hostelId="+hostelId+"&hostelroomTypeId="+roomTypeId+"&academicYear="+academicYear;
		var url ="AjaxRequest.do";
		requestOperation(url,args,updateNoOfSeats);	
	}
	
}
function updateNoOfSeats(req){
	var responseObj = req.responseXML.documentElement;
	 var value= responseObj.getElementsByTagName("name");
	 var temp; 
		if(value!=null){
			for ( var I = 0; I < value.length; I++) {
				if(value[I].firstChild!=null){
			 temp = value[I].firstChild.nodeValue;
				document.getElementById("seatAvailable").innerHTML =temp;
				document.getElementById("tempSeatAvailable").value =temp;
				}
			}
		}
		if(temp==0)
		{
			getWaitingListPriorityNo();
		}
		else{
             getStudentInWaitingListWithPriorityNo();
			document.getElementById("waitingPriorityNo").style.display = "none";
		}
}
function Cancel(){
	var check=document.getElementById("checkAdmission").value;
	if(check!=null && check!=''){
	document.location.href = "HostelAdmission.do?method=initHlAdmission";
	}else{
	document.location.href = "HostelAdmission.do?method=initHlAdmissionFromAdmission";
	}
	
}

function backToform(){
	var check=document.getElementById("checkAdmission").value;
	if(check!=null && check!=''){
	document.location.href = "HostelAdmission.do?method=initHlAdmission";
	}else{
	document.location.href = "admissionFormSubmit.do?method=initApplicationEdit";
	}
	
}
function bookWaitingList() {
	document.getElementById("method").value = "addHostelAdmissionInWaitingList";
}
function getWaitingListPriorityNo()
{
	var hostelId=document.getElementById("hostelName").value;
	var roomTypeId= document.getElementById("roomTypeName").value;
	var academicYear= document.getElementById("year").value;
	var regNo= document.getElementById("regNo").value;	
	var tempSeatAvailable= document.getElementById("tempSeatAvailable").value;
	var tempStudentName= document.getElementById("tempStudentName").value;
	var applNo= document.getElementById("applNo").value;
	hook=false;	
	document.location.href="HostelAdmission.do?method=getWaitingListPriorityNo&hostelId="+hostelId+"&hostelroomTypeId="+roomTypeId+"&academicYear="+academicYear+"&regNo="+regNo+"&tempStudentName="+tempStudentName+"&tempSeatAvailable="+tempSeatAvailable+"&applNo="+applNo;
}
function initAdmission()
{
	document.getElementById("method").value = "initHlAdmission";
}
function getStudentInWaitingListWithPriorityNo()
{
	var hostelId=document.getElementById("hostelName").value;
	var roomTypeId= document.getElementById("roomTypeName").value;
	var academicYear= document.getElementById("year").value;
	var regNo= document.getElementById("regNo").value;	
	var tempSeatAvailable= document.getElementById("tempSeatAvailable").value;
	var tempStudentName= document.getElementById("tempStudentName").value;
	var applNo= document.getElementById("applNo").value;
	var admType="";
	if(document.getElementById("admId").checked){
		admType = document.getElementById("admId").value;
	}else{
		admType = document.getElementById("reAdmId").value;
	}
	if((regNo!=null && regNo!="")||(applNo!=null && applNo!=""))
	{
		document.location.href="HostelAdmission.do?method=getStudentInWaitingListWithPriorityNo&hostelId="+hostelId+"&hostelroomTypeId="+roomTypeId+"&academicYear="+academicYear+"&regNo="+regNo+"&tempStudentName="+tempStudentName+"&tempSeatAvailable="+tempSeatAvailable+"&applNo="+applNo+"&admissionType="+admType;
	}	
}
function studentInWaitingListToAdmission()
{
	document.getElementById("method").value = "studentInWaitingListToHostelAdmission";
}
function showApplnNO(){
	if(document.getElementById("reAdmId").checked==true){
		document.getElementById("applnNO").style.display = "block";
		document.getElementById("applnNO1").style.display = "block";
	}else{
		document.getElementById("applnNO").style.display = "none";
		document.getElementById("applnNO1").style.display = "none";
	}
}
</script>
<html:form action="/HostelAdmission" method="post">
<html:hidden property="formName" value="hlAdmissionForm" />
<html:hidden property="pageType" value="1"/>
<html:hidden property="checkAdmission" styleId="checkAdmission"/>
<html:hidden property="tempSeatAvailable" styleId="tempSeatAvailable"/>
<html:hidden property="tempStudentName" styleId="tempStudentName"/>
<html:hidden property="tempGender" styleId="tempGender"/>
<c:choose>
<c:when test="${hlAdmission == 'edit'}">
<html:hidden property="method" styleId="method" value="updateHostelAdmission"/>
</c:when>
<c:otherwise>
<html:hidden property="method" styleId="method" value="addHostelAdmission"/>					
</c:otherwise>
</c:choose>
		
<table width="100%" border="0">
		<tr>
			<td><span class="Bredcrumbs"> <bean:message key="knowledgepro.hostel.adminmessage.hostel" />
			 <span class="Bredcrumbs">&gt;&gt;<bean:message key="knowledgepro.hostel.admission" /> &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong class="boxheader"> <bean:message key="knowledgepro.hostel.admission" /></strong></td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9" 	height="29"></td>
				</tr>
				<tr>
					<td height="122" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" cellspacing="1" cellpadding="2">
						<tr bgcolor="#FFFFFF">
							<td height="20" colspan="2">
							<div align="right"><FONT color="red"> <span
								class='MandatoryMark'><bean:message
								key="knowledgepro.mandatoryfields" /></span></FONT></div>
							<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
							<FONT color="green" size="2"> <html:messages id="msg"
								property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
								<br>
							</html:messages> </FONT></div>
							<div id="errmsg"></div>
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
									<td valign="top" class="news">
									<table width="100%" cellspacing="1" cellpadding="2">
									<tr height="25px">
										<td class="row-odd" colspan="3" align="right">
											<html:radio property="admissionType" name="hlAdmissionForm" value="New" styleId="admId" onclick="showApplnNO()"></html:radio>&nbsp;&nbsp;
											New Admission &nbsp;&nbsp;&nbsp;
										</td>
										<td class="row-odd" colspan="3">
											&nbsp;&nbsp;&nbsp;<html:radio property="admissionType" name="hlAdmissionForm" value="Re" styleId="reAdmId" onclick="showApplnNO()"></html:radio>&nbsp;&nbsp;
											Re-Admission 
										</td>
									</tr>
                                  <tr >
                                     <td width="16%" height="25" class="row-odd" ><div align="right"><bean:message key="knowledgepro.exam.blockUnblock.regNo"/></div></td>
                                      <td width="16%" height="25" class="row-even" ><span class="star">
                                      <c:choose>
                                      <c:when test="${hlAdmission != null && hlAdmission == 'edit'}">
                                         <input type="hidden" id="tempregNo" name="appliedYear"
										     value="<bean:write name="hlAdmissionForm" property="regNo"/>" />
                                      <html:text property="regNo" styleId="regNo" size="27" onchange="getStudentName()" disabled="true"/>
                                      </c:when>
                                      <c:otherwise>
                                       <input type="hidden" id="tempregNo" name="appliedYear"
										     value="<bean:write name="hlAdmissionForm" property="regNo"/>" />
                                      <html:text property="regNo" styleId="regNo" size="27" onchange="getStudentName()"/>
                                      </c:otherwise>
                                      </c:choose>
                                      </span></td>
                                            <td width="16%" height="25" class="row-odd"><div align="right"><bean:message key="knowledgepro.admission.applicationnumber"/>:</div></td>
                                              <td width="17%" class="row-even"><div align="left"> <span class="star">
                                              <c:choose>
                                             <c:when test="${hlAdmission != null && hlAdmission == 'edit'}">
                                               <input type="hidden" id="tempapplNo" name="appliedYear"
										     value="<bean:write name="hlAdmissionForm" property="applNo"/>" />
                                                <html:text property="applNo" styleId="applNo" size="20" onchange="getStudentName()" disabled="true"/>
                                               </c:when>
                                              <c:otherwise> 
                                               <input type="hidden" id="tempapplNo" name="appliedYear"
										     value="<bean:write name="hlAdmissionForm" property="applNo"/>" />
                                              <html:text property="applNo" styleId="applNo" size="20" onchange="getStudentName()" />
                                                </c:otherwise>
                                               </c:choose>
                                                </span></div></td>
                                        <td width="16%" height="25" class="row-odd"><div align="right"><bean:message key="knowledgepro.fee.studentname"/>:</div></td>
                                              <td width="18%" class="row-even"><div align="left" id="studentName"> <span class="star"> <bean:write name="hlAdmissionForm" property="studentName"/>
                                                </span></div></td>
                                                </tr>
										<tr>
											 <td width="16%" height="25" class="row-odd" >
									            <div align="right"><span class="Mandatory">*</span>
										           <bean:message key="knowledgepro.petticash.academicYear"/>:</div>
									            </td>
									         <td width="17%" height="25" class="row-even" >
									          <input type="hidden" id="tempyear" name="appliedYear"
										     value="<bean:write name="hlAdmissionForm" property="year"/>" />
										     <c:choose>
										     <c:when test="${hlAdmission != null && hlAdmission == 'edit'}">
									          <html:select property="year" styleId="year" styleClass="combo" disabled="true">
										      <html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
										      <cms:renderAcademicYearForAdmission></cms:renderAcademicYearForAdmission>
									         </html:select>
									          </c:when>
									          <c:otherwise> 
									           <html:select property="year" styleId="year" styleClass="combo">
										      <html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
										      <cms:renderAcademicYearForAdmission></cms:renderAcademicYearForAdmission>
									         </html:select>
									           </c:otherwise>
									         </c:choose>
								       	     </td>
									<td width="16%" height="25" class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.hostel.hostel.entry.name" /></div>
											</td>
											<td width="16%" height="25" class="row-even"><span
												class="star"> <input type="hidden" id="temphostelName" name="appliedYear"
										     value="<bean:write name="hlAdmissionForm" property="hostelId"/>" /> 
										     <html:select property="hostelName"
												styleClass="comboLarge" styleId="hostelName" onchange="getRoomTypes(this.value)">
												<html:option value="">
													<bean:message key="knowledgepro.admin.select" />
												</html:option>
											<c:if test="${hostelmap != null}">
												<html:optionsCollection name="hostelmap" label="value" value="key" />
							                   </c:if>
											</html:select></span></td>
									<td width="16%" height="25" class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.roomtype" /></div>
											</td>
											<td width="18%" height="25" class="row-even"><span
												class="star"> <input type="hidden" id="temproomTypeName" 
										     value="<bean:write name="hlAdmissionForm" property="hostelroomTypeId"/>" /> 
										     <html:select property="roomTypeName"
												styleClass="comboLarge" styleId="roomTypeName" onchange="getNumberOfSeats()">
												<html:option value="">
													<bean:message key="knowledgepro.admin.select" />
												</html:option>
												<c:if test="${roomTypeMap != null}">
												<html:optionsCollection name="roomTypeMap" label="value" value="key" />
							                   </c:if>
											</html:select></span></td>
										</tr>
							<tr>
									<td width="16%" height="25" class="row-odd"><div align="right"><span class="Mandatory">* </span><bean:message key="admissionFormForm.admissionDate"/>:</div></td>
                  		            <td height="16%" height="25" class="row-even">
                  		            <input type="hidden" id="tempadmittedDate" name="appliedYear"
										     value="<bean:write name="hlAdmissionForm" property="admittedDate"/>" />
                  		            <html:text name="hlAdmissionForm" property="admittedDate" styleId="admittedDate" size="10" maxlength="10"/>
	                                  <script
										language="JavaScript">

										$(function(){
											 var pickerOpts = {
													 	            dateFormat:"dd/mm/yy"
													         };  
											  $.datepicker.setDefaults(
											    $.extend($.datepicker.regional[""])
											  );
											  $("#admittedDate").datepicker(pickerOpts);
											  
											  var tempDate=document.getElementById("admittedDate").value;
											  if(tempDate==null || tempDate=='')
											  {
											  $('#admittedDate').datepicker('setDate', 'today');
											  }else{
												  $('#admittedDate').datepicker('setDate', tempDate);
											  }
											});

		
</script>
					            	</td>
					            	<td width="16%" height="25" class="row-odd"><div align="right"><bean:message key="knowledgepro.Hostel.Available.seat"/>:</div></td>
                                     <td width="5%" class="row-even" align="left"><b><div id="seatAvailable" ></b></div></td>
                                     
                                   <td width="16%" height="25" class="row-odd"><div align="right" id="applnNO1"><span class="Mandatory">* </span><bean:message key="knowledgepro.hostel.applicationNo"/>:</div></td>
                                              <td width="18%" class="row-even"><div align="left" id="applnNO"> <span class="star">
                                               <input type="hidden" id="temphlApplicationNo" name="appliedYear"
										     value="<bean:write name="hlAdmissionForm" property="hlApplicationNo"/>" />
                                                <html:text property="hlApplicationNo" styleId="hlApplicationNo" size="25" style="text-transform:uppercase;"/>
                                    </span></div></td>
					   <!--<td width="16%" height="25" class="row-odd" ><div align="right"><bean:message key="knowledgepro.hostel.roomno"/>:</div></td>
                      <td width="17%" height="25" class="row-even" ><html:select property="roomName" styleId="roomName" styleClass="combo" onchange="getBedByRoomId(this.value)">
						<html:option value="">- Select -</html:option>
						<c:if test="${roomMap != null}">
						<html:optionsCollection name="roomMap" label="value" value="key"/>
						</c:if>
					</html:select>
					</td>
					
				  <td width="16%" height="25" class="row-odd"><div align="right"><bean:message key="knowledgepro.hostel.bedno"/>:</div></td>
                   <td width="18%" height="25" class="row-even"><html:select property="bedNo" styleId="bedNo" styleClass="combo">
						<html:option value="">- Select -</html:option>
									<c:if test="${bedMap != null}">
						<html:optionsCollection name="bedMap" label="value" value="key"/>
						</c:if>
					</html:select></td>
										
						--></tr>
									</table>
									</td>
									<td width="5" height="29" background="images/right.gif"></td>
								</tr>

								<tr>
									<td height="5"><img src="images/04.gif" width="5"
										height="5" /></td>
									<td background="images/05.gif"></td>
									<td><img src="images/06.gif" /></td>
								</tr>
							</table>
							</td>
							<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
						</tr>
						<logic:equal value="true" name="hlAdmissionForm" property="waitingList">
		<tr id="waitingPriorityNo"><td height="25" align="center"><blink><font color="red" size="2px"> <bean:message key="knowledgepro.hostel.waitingListPriorityNo"/><font size="2px" color="black">:</font> <bean:write name="hlAdmissionForm" property="watingPriorityNo"></bean:write></font></blink></td></tr>
		</logic:equal>
		<logic:equal value="true" name="hlAdmissionForm" property="studentInWaitingList">
		<tr id="waitingPriorityNo"><td height="25" align="center"><blink><font color="red" size="2px"> <bean:message key="knowledgepro.hostel.student.waitingListPriorityNo"/><font size="2px" color="black">:</font> <bean:write name="hlAdmissionForm" property="watingPriorityNo"></bean:write></font></blink></td></tr>
		</logic:equal>
						

						<tr>
							<td height="25">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td height="35" colspan="6" align="center">
									<div align="center"><c:choose>
										<c:when test="${hlAdmission != null && hlAdmission == 'edit'}">
											<html:submit property="" styleClass="formbutton" onclick="updateHlAdmission()">
												<bean:message key="knowledgepro.update" /></html:submit>&nbsp;&nbsp;&nbsp;&nbsp;
												<html:button property="" styleClass="formbutton" value="Reset" onclick="Cancel()">
												<bean:message key="knowledgepro.cancel" /></html:button>
												
										</c:when>
										<c:otherwise>
										   <c:choose>
										   <c:when test="${hlAdmission == null && hlAdmission != 'edit'}">
											<html:submit property="" styleClass="formbutton" onclick="HlAdmissionSearch()"><bean:message key="knowledgepro.admin.search" />
											</html:submit>
										</c:when>
										 </c:choose>&nbsp;&nbsp;&nbsp;&nbsp;
											<html:submit property="" styleClass="formbutton" onclick="addHlAdmission()">
												<bean:message key="knowledgepro.hostel.assign.hostel" /></html:submit>&nbsp;&nbsp;&nbsp;&nbsp;
												<logic:notEmpty name="hlAdmissionForm" property="checkAdmission">
												<html:button property="" styleClass="formbutton" value="Reset" onclick="Cancel()"></html:button>&nbsp;&nbsp;&nbsp;&nbsp;
												</logic:notEmpty>
												<html:button property="" onclick="backToform()" styleClass="formbutton" value="Back"></html:button>
										</c:otherwise>
									</c:choose></div>
									</td>
								</tr>
							</table>
							</td>
						</tr>
						
						<logic:notEmpty name="hlAdmissionForm" 	property="hlAdmissionList">
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
											<td height="25" colspan="2">
											<table width="100%" cellspacing="1" cellpadding="2">
												<tr>
													<td width="5%" height="25" class="row-odd" align="center">
													<div align="center"><bean:message key="knowledgepro.slno" /></div></td>
													<td width="14%" height="25" class="row-odd" align="center">
													<div align="center"><bean:message key="knowledgepro.admin.course" /></div></td>
													<td width="8%" height="25" class="row-odd" align="center">
													<div align="center"><bean:message key="knowledgepro.exam.blockUnblock.regNo" /></div></td>
													<td width="9%" height="25" class="row-odd" align="center">
													<div align="center"><bean:message key="knowledgepro.admission.applicationnumber" /></div></td>
													<td width="16%" height="25" class="row-odd" align="center">
													<div align="center"><bean:message key="knowledgepro.fee.studentname" /></div></td>
													<td width="12%" height="25" class="row-odd" align="center">
													<div align="center"><bean:message key="knowledgepro.hostel.hostel.entry.name" /></div></td>
													<td width="10%" height="25" class="row-odd" align="center">
													<div align="center"><bean:message key="knowledgepro.roomtype" /></div></td>
													<td width="6%" height="25" class="row-odd" align="center">
													<div align="center"><bean:message key="knowledgepro.hostel.applicationNo" /></div></td>
													<td width="5%" height="25" class="row-odd" align="center">
													<div align="center"><bean:message key="knowledgepro.admin.year" /></div></td>
											    	<td width="5%" height="20" class="row-odd" align="center">
													<div align="center"><bean:message
														key="knowledgepro.edit" /></div>
													</td>
													<td width="5%" height="20" class="row-odd" align="center">
													<div align="center"><bean:message
														key="knowledgepro.print" /></div>
													</td>
													<td width="5%"  height="20" class="row-odd" align="center">
													<div align="center"><bean:message
														key="knowledgepro.admin.cancel" /></div>
													</td>
												</tr>

												<c:set var="temp" value="0" />
													<logic:iterate name="hlAdmissionForm" id="admission" property="hlAdmissionList" indexId="count">
														<c:choose>
															<c:when test="${temp == 0}">
																<tr>
																	<td height="25" class="row-even" align="center"><div align="center"><c:out value="${count+1}" /></div></td>
																	<td height="25" class="row-even" align="center"><bean:write name="admission" property="courseName" /></td>
																	<td height="25" class="row-even" align="center"><bean:write name="admission" property="regNo" /></td>
																	<td height="25" class="row-even" align="center"><bean:write name="admission" property="applNo" /></td>
																	<td height="25" class="row-even" align="center"><bean:write name="admission" property="studentName" /></td>
																	<td height="25" class="row-even" align="center"><bean:write name="admission" property="hostelName" /></td>
																	<td height="25" class="row-even" align="center"><bean:write name="admission" property="roomTypeName" /></td>
																	<td height="25" class="row-even" align="center"><bean:write name="admission" property="hlApplicationNo" /></td>
																	<td height="25" class="row-even" align="center"><bean:write name="admission" property="year" /></td>
																	<c:choose>
																   <c:when test="${not empty admission.hostelName}">
																	<td height="20" class="row-even" align="center"><div align="center"><img src="images/edit_icon.gif" width="16" height="18"
																		style="cursor: pointer" onclick="editHlAdmission('<bean:write name="admission" property="id"/>','<bean:write name="admission" property="regNo"/>','<bean:write name="admission" property="applNo"/>')"></div></td>
																	<td height="20" class="row-even" align="center"><div align="center"><img src="images/print-icon.png" width="16" height="18"
																		style="cursor: pointer" onclick="printICard('<bean:write name="admission" property="id"/>')"></div></td>
																	<td height="20" class="row-even" align="center"><div align="center"><img src="images/cancel_icon.gif" width="14" height="14"
																		style="cursor: pointer"	onclick="deleteHlAdmission('<bean:write name="admission" property="id"/>')"></div></td>
																	</c:when>
																	<c:otherwise>
																	<td class="row-even"></td>
														            <td class="row-even"></td>
														            <td class="row-even"></td>
														            </c:otherwise>
														            </c:choose>
																</tr>
																<c:set var="temp" value="1" />
															</c:when>
															<c:otherwise>
																<tr>
																	<td height="25" class="row-white" align="center"><div align="center"><c:out value="${count+1}" /></div></td>
																	<td height="20" class="row-white" align="center"><bean:write name="admission" property="courseName" /></td>
																	<td height="25" class="row-white" align="center"><bean:write name="admission" property="regNo" /></td>
																	<td height="25" class="row-white" align="center"><bean:write name="admission" property="applNo" /></td>
																	<td height="25" class="row-white" align="center"><bean:write name="admission" property="studentName" /></td>
																	<td height="25" class="row-white" align="center"><bean:write name="admission" property="hostelName" /></td>
																	<td height="25" class="row-white" align="center"><bean:write name="admission" property="roomTypeName" /></td>
																	<td height="25" class="row-white" align="center"><bean:write name="admission" property="hlApplicationNo" /></td>
																	<td height="25" class="row-white" align="center"><bean:write name="admission" property="year" /></td>
																	<c:choose>
																   <c:when test="${not empty admission.hostelName}">
																	<td height="20" class="row-white" align="center"><div align="center"><img src="images/edit_icon.gif" width="16" height="18"
																		style="cursor: pointer" onclick="editHlAdmission('<bean:write name="admission" property="id"/>','<bean:write name="admission" property="regNo"/>','<bean:write name="admission" property="applNo"/>')")"></div></td>
																	<td height="20" class="row-white" align="center"><div align="center"><img src="images/print-icon.png" width="16" height="18"
																		style="cursor: pointer" onclick="printICard('<bean:write name="admission" property="id"/>')"></div></td>
																	<td height="20" class="row-white" align="center"><div align="center"><img src="images/cancel_icon.gif" width="14" height="14"
																		style="cursor: pointer" onclick="deleteHlAdmission('<bean:write name="admission" property="id"/>')"></div></td>
																		</c:when>
																	<c:otherwise>
																	<td class="row-even"></td>
														            <td class="row-even"></td>
														            <td class="row-even"></td>
														            </c:otherwise>
														            </c:choose>
																</tr>
																<c:set var="temp" value="0" />
															</c:otherwise>
														</c:choose>
													</logic:iterate>
											</table>
											</td>
										</tr>
									</table>
									</td>
									<td width="5" height="29" background="images/right.gif"></td>
								</tr>
                               
								<tr>
									<td height="5"><img src="images/04.gif" width="5"
										height="5" /></td>
									<td background="images/05.gif"></td>
									<td><img src="images/06.gif" /></td>
								</tr>
							</table>
							</td>
							<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
						</tr>
                 </logic:notEmpty>

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
<script type="text/javascript">
	if(document.getElementById("reAdmId").checked==true){
		document.getElementById("applnNO").style.display = "block";
		document.getElementById("applnNO1").style.display = "block";
	}else{
		document.getElementById("applnNO").style.display = "none";
		document.getElementById("applnNO1").style.display = "none";
	}
	var yearId = document.getElementById("tempyear").value;
	if (yearId != null && yearId.length != 0) {
		document.getElementById("year").value = yearId;
	}
	var regId = document.getElementById("tempregNo").value;
	if (regId != null && regId.length != 0) {
		document.getElementById("regNo").value = regId;
	}
	var appId = document.getElementById("tempapplNo").value;
	if (appId != null && appId.length != 0) {
		document.getElementById("applNo").value = appId;
	}
	var hostelNameId = document.getElementById("temphostelName").value;
	if (hostelNameId != null && hostelNameId.length != 0) {
		document.getElementById("hostelName").value = hostelNameId;
	}
	var roomTypeNameId = document.getElementById("temproomTypeName").value;
	if (roomTypeNameId != null && roomTypeNameId.length != 0) {
		document.getElementById("roomTypeName").value = roomTypeNameId;
	}
	var hlApplicationNoId = document.getElementById("temphlApplicationNo").value;
	if (hlApplicationNoId != null && hlApplicationNoId.length != 0) {
		document.getElementById("hlApplicationNo").value = hlApplicationNoId;
	}
	var admittedDate = document.getElementById("tempadmittedDate").value;
	if (admittedDate != null && admittedDate.length != 0) {
		document.getElementById("admittedDate").value = admittedDate;
	}
	var tempStudentName = document.getElementById("tempStudentName").value;
	if (tempStudentName != null && tempStudentName.length != 0) {
		document.getElementById("studentName").innerHTML = tempStudentName;
	}
	var tempSeatAvailable = document.getElementById("tempSeatAvailable").value;
	if (tempSeatAvailable != null && tempSeatAvailable.length != 0) {
		document.getElementById("seatAvailable").innerHTML = tempSeatAvailable;
	}else{
		document.getElementById("seatAvailable").innerHTML = "";
	}
	var print = "<c:out value='${hlAdmissionForm.print}'/>";
	if(print.length != 0 && print == "true"){
		var url ="HostelAdmission.do?method=printHlAdmission";
		myRef = window.open(url,"collectionLedger","left=20,top=20,width=800,height=500,toolbar=1,resizable=0,scrollbars=1");
	}
</script>