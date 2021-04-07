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
	if(document.getElementById("isCheckIn").checked){
		document.getElementById("method").value ="updateHostelAdmission";
		document.HlStudentChInEditForm.submit();
	}else{
		document.getElementById("checkInError").innerHTML="Please select Check-In as Yes";
		return false;
	}
}

function editHlAdmission(id) {
	document.location.href = "HostelStudentCheckIn.do?method=editHostelStudentCheckIn&id="+ id;
}

function updateSubCategory() {
	document.getElementById("method").value = "updateHostelStudentCheckIn";
}
function HlAdmissionSearch(){
	document.getElementById("method").value="HostelStudentCheckInSearchNew";
}
function deleteHlAdmission(id) {
	document.location.href = "HostelStudentCheckIn.do?method=getCancelReason&id="+ id;
}


function getStudentName(){
	var regNo=document.getElementById("regNo").value;
	var appNo= document.getElementById("applNo").value;
	var academicYear=document.getElementById("year").value;
	var hostelAppNo=document.getElementById("hlApplicationNo").value;
	if((regNo!=null && regNo!='') || (appNo!=null && appNo!='') || (hostelAppNo!=null && hostelAppNo!='') ||(academicYear!=null && academicYear!=''))
	{
		document.getElementById("method").value ="HostelAdmissionSearchNew";
		document.HlStudentChInEditForm.submit();
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

function getRoom(){
	var hostelId=document.getElementById("hostelId").value;
	var roomTypeId= document.getElementById("roomTypeName").value;
	var academicYear= document.getElementById("year").value;
	var block= document.getElementById("block").value;
	var unit=  document.getElementById("unit").value;
	var floor=  document.getElementById("floorNo").value;
	var args ="method=getRooms&hostelId="+hostelId+"&hostelroomType="+roomTypeId+"&academicYear="+academicYear+"&blockId="+block+"&unitId="+unit+"&floorNo="+floor+"&studentId="+document.getElementById("studentId").value;
	var url ="HostelStudentCheckIn.do";
	requestOperation(url,args,updateRooms);	
}

function updateRooms(req) {
	updateOptionsFromMap(req, "roomName", "- Select -");
}

function getBedByRoomId(roomId){
	var RoomId=  document.getElementById("roomName").value;
	var args ="method=getBedsAvailable&roomId="+RoomId;
	var url ="HostelStudentCheckIn.do";
	requestOperation(url,args,updateBeds);	
	
}
function updateBeds(req) {
	updateOptionsFromMap(req, "bedNo", "- Select -");
}

function setRoomByRoomType(roomTypeId){
	getRoomByRoomTypeId1("roomMap",roomTypeId,"roomName",updateRooms);
}

function getNumberOfSeats(seats){
	var showSeats=seats;	
	var hostelId=document.getElementById("hostelName").value;
	var roomTypeId= document.getElementById("roomTypeName").value;
	var academicYear= document.getElementById("year").value;
		var args ="method=getNumberOfSeatsAvailable&hostelId="+hostelId+"&hostelroomTypeId="+roomTypeId+"&academicYear="+academicYear;
		var url ="AjaxRequest.do";
		requestOperation(url,args,updateNoOfSeats);	
	
}

function Cancel(){
	document.location.href = "HostelStudentCheckIn.do?method=initHlAdmissionNew";
}

function backToform(){
	document.location.href = "HostelStudentCheckIn.do?method=initHlAdmissionNew";
	
	
}

//-------------------new Scripts-----------------------
function getBlocks(hostal){
		getBlocksByHostel(hostal, "block", updateBlocks);
}
	function updateBlocks(req){
		updateOptionsFromMap(req, "block", "- Select -");
	}
	function getUnits(blockId){
		getUnitsByBlocks(blockId, "unit", updateUnits);
	}
	function updateUnits(req){
		updateOptionsFromMap(req, "unit", "- Select -");
	}

function getFloors(unitId) {
		getFloorsByHostel("floorMap", unitId, "floorNo", updateFloors);
	}
	
	function updateFloors(req) {
		updateOptionsFromMap(req, "floorNo", "- Select -");
	}


	function unCheckSelectAll() {
//		document.getElementsByTagName("facilityhidden_"+count).value=true;
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

	function handleKeyPress(e){
		 var key=e.keyCode || e.which;
		  if (key==13){
			  getStudentName();
		  }
		}

	function updateHlAdmission(){
		if(document.getElementById("isCheckIn").checked){
			document.getElementById("method").value="updateHostelStudentCheckIn";
		}else{
			document.getElementById("checkInError").innerHTML="Please select Check-In as Yes";
			return false;
		}
	}
</script>
<html:form action="/HostelStudentCheckIn" method="post">
<html:hidden property="formName" value="HlStudentChInEditForm" />
<html:hidden property="pageType" value="1"/>
<html:hidden property="checkAdmission" styleId="checkAdmission"/>
<html:hidden property="tempSeatAvailable" styleId="tempSeatAvailable"/>
<html:hidden property="tempStudentName" styleId="tempStudentName"/>
<html:hidden property="tempStudentClass" styleId="tempStudentClass"/>
<html:hidden property="tempGender" styleId="tempGender"/>
<html:hidden property="hostelName" styleId="hostelName"/>
<html:hidden property="unitName" styleId="unitName"/>
<html:hidden property="blockName" styleId="blockName"/>
<html:hidden property="studentId" styleId="studentId"/>
<html:hidden property="method" styleId="method" value="updateHostelStudentCheckIn"/>
		
<table width="100%" border="0">
		<tr>
			<td><span class="Bredcrumbs"> <bean:message key="knowledgepro.hostel.adminmessage.hostel" />
			 <span class="Bredcrumbs">&gt;&gt;Check-In &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong class="boxheader">Check-In</strong></td>
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
							<FONT color="green"> <html:messages id="msg"
								property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
								<br>
							</html:messages> </FONT></div>
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
                           <tr>
                                     <td width="16%" height="25" class="row-odd" ><div align="right"><bean:message key="knowledgepro.exam.blockUnblock.regNo"/>:</div></td>
                                      <td width="16%" height="25" class="row-even" ><span class="star">
                                      <c:choose>
                                      <c:when test="${hlAdmission != null && hlAdmission == 'edit'}">
                                      <html:text property="regNo" styleId="regNo" size="27"  disabled="true"/>
                                      </c:when>
                                      <c:otherwise>
                                      <html:text property="regNo" styleId="regNo" size="27" onkeypress="handleKeyPress(event)"/>
                                      </c:otherwise>
                                      </c:choose>
                                      </span></td>
                                  <td width="16%" height="25" class="row-odd"><div align="right"><bean:message key="knowledgepro.admission.applicationnumber"/>:</div></td>
                                   <td width="17%" class="row-even"><div align="left"> <span class="star">
                                              <c:choose>
                                             <c:when test="${hlAdmission != null && hlAdmission == 'edit'}">
                                                <html:text property="applNo" styleId="applNo" size="20"  disabled="true"/>
                                               </c:when>
                                              <c:otherwise> 
                                              <html:text property="applNo" styleId="applNo" size="20" onkeypress="handleKeyPress(event)"/>
                                                </c:otherwise>
                                               </c:choose>
                                    </span></div></td>
                           </tr>
                           <tr>
                                          <td width="16%" height="25" class="row-odd"><div align="right"><bean:message key="knowledgepro.hostel.applicationNo"/>.:</div></td>
                                          <td width="18%" class="row-even"><div align="left"> <span class="star">
                                                <html:text property="hlApplicationNo" styleId="hlApplicationNo"  size="25" style="text-transform:uppercase;" onkeypress="handleKeyPress(event)"/>
                                    		</span></div></td>
                                    		
                                    	 <td width="16%" height="25" class="row-odd" >
									            <div align="right">
										           <bean:message key="knowledgepro.petticash.academicYear"/>:</div>
									            </td>
									     <td width="17%" height="25" class="row-even" >
									        <input type="hidden" id="tempyear" name="appliedYear"
										     value="<bean:write name="HlStudentChInEditForm" property="year"/>" />
									       <html:select property="year" styleId="year" styleClass="combo" >
											<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
											<cms:renderAcademicYearForAdmission></cms:renderAcademicYearForAdmission>
										   </html:select>
										   </td>
                             </tr>
                         <logic:equal name="HlStudentChInEditForm" property="dataAvailable" value="false">     
                            <tr>
                             <td align="center" colspan="4">
                              <html:button property="" styleClass="formbutton" onclick="getStudentName()">
                              <bean:message key="knowledgepro.admin.search" /></html:button>
							</td>
                           </tr>     
                        </logic:equal>                
                                   
                       <logic:equal name="HlStudentChInEditForm" property="dataAvailable" value="true">         
                                   
                                   <tr>
                                   		
                                        <td width="16%" height="25" class="row-odd"><div align="right"><bean:message key="knowledgepro.fee.studentname"/>:</div></td>
                                        <td width="18%" class="row-even"><b><bean:write name="HlStudentChInEditForm" property="studentName" /></b></td>
                                         <td width="16%" height="25" class="row-odd"><div align="right">Course:</div></td>
                                        <td width="18%" class="row-even"><b><bean:write name="HlStudentChInEditForm" property="courseName" /></b></td>     
							     </tr>
							     <tr>
										<td width="16%" height="25" class="row-odd"><div align="right"><bean:message key="admissionFormForm.admissionDate"/>:</div></td>
                  		            	<td height="16%" height="25" class="row-even"><html:text name="HlStudentChInEditForm" property="admittedDate" styleId="admittedDate" size="10" maxlength="10"/>
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
											//  $('#admittedDate').datepicker('setDate', 'today');
											});
		
								</script>
							        		 
					            		</td>
							       		<td width="25%" height="25" class="row-odd"><div align="right"><span class="Mandatory">*</span> <bean:message key="knowledgepro.hostel.entry.hostel.name"/></div></td>
	                    						<td width="25%" height="25" class="row-even">
	                    							<html:select property="hostelId" styleClass="comboLarge" styleId="hostelId" onchange="getBlocks(this.value);getRoomTypes(this.value)">
														<html:option value="">
															<bean:message key="knowledgepro.admin.select" />
														</html:option>
														<logic:notEmpty name="HlStudentChInEditForm" property="hostelList">
															<html:optionsCollection  property="hostelList" label="name" value="id" />
														</logic:notEmpty>
													</html:select>
												</td>
							</tr>
							<tr>
												
												<td width="16%" height="25" class="row-odd"><div align="right"><span class="Mandatory">* </span><bean:message key="knowledgepro.roomtype" />:</div>
												</td>
											<td width="18%" height="25" class="row-even"><span class="star"> <html:select property="roomTypeName"
												styleClass="comboLarge" styleId="roomTypeName" onchange="getRoom()">
												<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
												<logic:notEmpty name="HlStudentChInEditForm" property="roomTypeMap">
												<html:optionsCollection property="roomTypeMap" label="value" value="key" />
							            		</logic:notEmpty>
								    			</html:select></span>
								    		</td>
									
                          		 
											<td width="25%" class="row-odd"><div align="right"><span class="Mandatory">* </span><bean:message key="knowledgepro.block" />:</div></td>
	                    						<td width="25%" class="row-even">
								                    <html:select name="HlStudentChInEditForm" property="block" styleId="block" styleClass="combo" onchange="getUnits(this.value)">
														<html:option value="">- Select -</html:option>
														<logic:notEmpty name="HlStudentChInEditForm" property="blockMap">
															<html:optionsCollection  property="blockMap" label="value" value="key" />
														</logic:notEmpty>
													</html:select>
												</td>
									</tr>
									<tr>
	                  							<td width="25%" class="row-odd"><div align="right"><span class="Mandatory">* </span><bean:message key="knowledgepro.unit" />:</div></td>
	                    						<td width="25%" class="row-even">
								                    <html:select name="HlStudentChInEditForm" property="unit" styleId="unit" styleClass="combo" onchange="getFloors(this.value)">
														<html:option value="">- Select -</html:option>
														<logic:notEmpty name="HlStudentChInEditForm" property="unitMap">
															<html:optionsCollection  property="unitMap" label="value" value="key" />
														</logic:notEmpty>
													</html:select>
												</td>
							                   <td width="25%" class="row-odd"><div align="right"><span class="Mandatory">* </span><bean:message key="knowledgepro.hostel.room.floor.no"/></div></td>
	                    						<td width="25%" class="row-even">
								                    <html:select name="HlStudentChInEditForm" property="floorNo" styleId="floorNo" styleClass="combo" onchange="getRoom()">
														<html:option value="">- Select -</html:option>
														<logic:notEmpty name="HlStudentChInEditForm" property="floorMap">
															<html:optionsCollection  property="floorMap" label="value" value="key" />
														</logic:notEmpty>
													</html:select>
												</td>
						 </tr>
                         <tr>
								    
					  				 <td width="16%" height="25" class="row-odd" ><div align="right"><span class="Mandatory">* </span><bean:message key="knowledgepro.hostel.roomno"/>.:</div></td>
                      				<td width="17%" height="25" class="row-even" ><html:select property="roomName" styleId="roomName" styleClass="combo" onchange="getBedByRoomId(this.value)">
									<html:option value="">- Select -</html:option>
										<logic:notEmpty name="HlStudentChInEditForm" property="roomMap">
										<html:optionsCollection name="HlStudentChInEditForm" property="roomMap" label="value" value="key"/>
										</logic:notEmpty>
									</html:select>
									</td>
					
				  					<td width="16%" height="25" class="row-odd"><div align="right"><span class="Mandatory">* </span><bean:message key="knowledgepro.hostel.bedno"/>.:</div></td>
                  					<td width="18%" height="25" class="row-even"><html:select property="bedNo" styleId="bedNo" styleClass="combo">
									<html:option value="">- Select -</html:option>
									<logic:notEmpty name="HlStudentChInEditForm" property="bedMap">
									<html:optionsCollection property="bedMap" label="value" value="key"/>
									</logic:notEmpty>
									</html:select></td>
				 </tr>	
				  <tr>
									<td width="16%" height="25" class="row-odd"><div align="right">Biometric Id:</div></td>
                                              <td width="18%" class="row-even"><div align="left"> <span class="star">
                                                <html:text property="biometricId" styleId="biometricId" size="25" maxlength="25" />
                                    </span></div></td>
				 	
				 	 				<td width="16%" height="25" class="row-odd"><div align="right">Check-In date:</div></td>
                                              <td width="18%" class="row-even"><div align="left"> <span class="star">
                                                <html:text property="checkInDate" styleId="checkInDate" size="15" maxlength="25" name="HlStudentChInEditForm"/>
                                    </span></div>
                                    	<script
												language="JavaScript">

										$(function(){
											 var pickerOpts = {
													 	            dateFormat:"dd/mm/yy"
													         };  
											  $.datepicker.setDefaults(
											    $.extend($.datepicker.regional[""])
											  );
											  $("#checkInDate").datepicker(pickerOpts);
											 // $('#checkInDate').datepicker('setDate', 'today');
											});

		
</script>
                                    </td>
                 </tr>	
				 <tr>
                                    <td height="25" class="row-odd"><div align="right" >Check-In</div></td>
                					<td class="row-even"><span class="star">
                 					<html:radio property="isCheckIn" styleId="isCheckIn" value="1"/>Yes&nbsp; 
				 					<html:radio property="isCheckIn" styleId="isCheckIn1" value="0"/>No
                 					</span></td>
                 					<td height="25" class="row-even" colspan="2"><div align="left" id="checkInError" style="color: red;"></div></td>
				 	 
				 	</tr>
				 	 </logic:equal>
								
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
						
				<logic:equal name="HlStudentChInEditForm" property="dataAvailable" value="true">    
						<tr bgcolor="#FFFFFF">
						<td height="20" colspan="2">
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
								
								<logic:notEmpty name="HlStudentChInEditForm" property="facilityList">
							<tr class="row-odd">
									<td colspan="3" align="left" height="30"><b>Facility Allottement for Room</b></td>
							</tr>
								<tr>
										
										<td width="6%" height="25" class="row-odd">Select</td>
										<td width="40%" class="row-odd" align="left">Name</td>	
										<td width="54%" height="25" class="row-odd" align="left">Description</td>
								</tr>
							
							<tr>
								<nested:iterate id="facility" name="HlStudentChInEditForm" property="facilityList" indexId="count">
									<c:choose>
										<c:when test="${count%2 == 0}">
											<tr class="row-even">
										</c:when>
										<c:otherwise>
											<tr class="row-white">
										</c:otherwise>
									</c:choose>
										
										<td width="6%" height="25">
										<div align="left">	
										<input type="hidden" name="facilityList[<c:out value='${count}'/>].dummySelected" id="facilityhidden_<c:out value='${count}'/>"
													value="<nested:write name='facility' property='dummySelected'/>" />				
										<input type="checkbox" name="facilityList[<c:out value='${count}'/>].checked" id="<c:out value='${count}'/>" onclick="unCheckSelectAll()"/>
										</div>												
										<script type="text/javascript">
										var facility = document.getElementById("facilityhidden_<c:out value='${count}'/>").value;
										if(facility == "true") {
										document.getElementById("<c:out value='${count}'/>").checked = true;
										}		
										</script>														
										</td>
										<td width="40%"  align="left"><nested:write  property="name"/></td>	
										<td height="25"  align="left">
										<nested:text property="description" size="100" maxlength="200"></nested:text>
										</td>
													
									</nested:iterate>
									</logic:notEmpty>
								</table>
								</td>
								<td width="5" height="30" background="images/right.gif"></td>
							</tr>
							<tr>
								<td height="5"><img src="images/04.gif" width="5"
									height="5" /></td>
								<td background="images/05.gif"></td>
								<td><img src="images/06.gif" /></td>
							</tr>
						</table>
						</td>
					</tr>
					</logic:equal>
						 <logic:equal name="HlStudentChInEditForm" property="dataAvailable" value="true">    
						<tr>
							<td height="25">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td height="35" colspan="6" align="center">
									<div align="center"><c:choose>
									<c:when test="${hlAdmission != null && hlAdmission == 'edit'}">
									<html:submit property="" styleClass="formbutton" onclick="updateHlAdmission()">Check-In</html:submit>&nbsp;&nbsp;&nbsp;&nbsp;
										<html:button property="" styleClass="formbutton" value="Reset" onclick="Cancel()">
										<bean:message key="knowledgepro.cancel" /></html:button>
										</c:when>
										<c:otherwise>
									<html:button property="" styleClass="formbutton" onclick="addHlAdmission()">Check-In</html:button>&nbsp;&nbsp;&nbsp;&nbsp;
												<logic:notEmpty name="HlStudentChInEditForm" property="checkAdmission">
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
						</logic:equal>   
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
    <logic:notEmpty name="HlStudentChInEditForm" property="print">
	<SCRIPT type="text/javascript">	
	var url ="HostelStudentCheckIn.do?method=printHostelStudentCheckIn";
	myRef = window.open(url,"collectionLedger","left=20,top=20,width=800,height=500,toolbar=1,resizable=0,scrollbars=1");
</SCRIPT>
	</logic:notEmpty>
</html:form>
<script type="text/javascript">

document.getElementById("studentName").innerHTML=document.getElementById("tempStudentName").value ;

	var yearId = document.getElementById("tempyear").value;
	if (yearId != null && yearId.length != 0) {
		document.getElementById("year").value = yearId;
	}

</script>