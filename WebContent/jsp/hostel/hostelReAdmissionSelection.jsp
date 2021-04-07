<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<script type="text/javascript"><!--
$(document).ready(function() {
	  $('#Search').click(function(){
		       var academicYear = $('#academicYear').val();
		       var hostelId = $('#hostelId').val();
	       if(academicYear== '' && hostelId==''){
	    	   $('#errorMessage').slideDown().html("<span>Academic Year is Required <br>Hostel. is Required.</span>");
	          return false;
	        }
	       else if(academicYear== ''){
	        	 $('#errorMessage').slideDown().html("<span>Please Select Academic Year.</span>");
	           return false;
	        }
	       else if(hostelId== ''){
	        	 $('#errorMessage').slideDown().html("<span>Please Select Hostel.</span>");
	            return false;
	        }else{
	   		   document.feeRefundForm.submit();
	       }
	      });
	  $('#close').click(function(){
		  document.location.href = "hostelReAdmission.do?method=initHostelReAdmission";
	  });
	  $('#reset').click(function(){
			  document.getElementById("errorMessage").innerHTML = "";
			  document.getElementById("changeRoomType").value = "";
	  });
	});

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
                  }     
            }
    }
    if(checkBoxOthersCount != checkBoxOthersSelectedCount) {
      document.getElementById("checkAll").checked = false;
    } else {
      document.getElementById("checkAll").checked = true;
    }        
}
function viewStudentDetails(regNo,studentName,applNo) {
	 var url = "disciplinaryDetails.do?method=viewStudentDetailsForHostelReAdmission&tempRegRollNo="+regNo+"&tempFirstName="+studentName+"&tempApplicationNo="+applNo;
	 myRef = window.open(url, "Marks Entry",
		"left=20,top=20,width=900,height=900,toolbar=1,resizable=0,scrollbars=1");
}
</script>
<style>
 #hyper2{
    color: #9c0455;
    -webkit-transition: 0.5s;
    -moz-transition: 0.5s;
    -o-transition: 0.5s;
    -ms-transition: 0.5s;
    transition: 0.5s;
    text-decoration: none;
    font-weight: bolder;
    font-size: 11px;
 }
 
 #hyper2:hover {
    -webkit-transform: scale(1.3,1.3);
    -moz-transform: scale(1.3,1.3);
    -o-transform: scale(1.3,1.3);
    -ms-transform: scale(1.3,1.3);
    transform: scale(1.3,1.3);
       color:#559c04;
    text-decoration:blink;   
    font-weight: bolder;
    font-size: 15px;
 }
</style>
<html:form action="/hostelReAdmission" method="post">
<html:hidden property="formName" value="hostelReAdmissionForm" />
<html:hidden property="pageType" value="1"/>
<c:choose>
		<c:when test="${operation == 'continueToUpdate'}">
			<html:hidden property="method" styleId="method" value="updateHostelOnlineApplication" />
		</c:when>
		<c:otherwise>
			<html:hidden property="method" styleId="method" value="searchStudentByHostelIdAndYear" />
		</c:otherwise>
	</c:choose>
<table width="100%" border="0">
		<tr>
			<td><span class="Bredcrumbs"> <bean:message key="knowledgepro.hostel.adminmessage.hostel" />
			 <span class="Bredcrumbs">&gt;&gt;<bean:message key="knowledgepro.hostel.readmission.selection" /> &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong class="boxheader"> <bean:message key="knowledgepro.hostel.readmission.selection" /></strong></td>
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
							<font color="red" size="2"><div id="errorMessage"><FONT color="red"><html:errors /></FONT>
							<!--<font color="red" size="2"><div id="updateRefundMsg"></div></font>-->
							<FONT color="green" size="2"> <html:messages id="msg"
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
									<td valign="top" class="news">
									<table width="100%" cellspacing="1" cellpadding="2">
                                 
										<tr>
											 <td width="25%%" height="25" class="row-odd" >
									            <div align="right"><span class="Mandatory">*</span>
										           <bean:message key="knowledgepro.petticash.academicYear"/>:</div>
									            </td>
									         <td width="25%" height="25" class="row-even" >
									           <input type="hidden" id="tempyear" name="appliedYear"
										     value="<bean:write name="hostelReAdmissionForm" property="academicYear"/>" />
									       <html:select property="academicYear" styleId="academicYear" styleClass="combo" name="hostelReAdmissionForm">
										<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
										<cms:renderAcademicYearForAdmission></cms:renderAcademicYearForAdmission>
									</html:select></td>
									<td width="20%" height="25" class="row-odd">
											<div align="right"><span class="Mandatory">*</span><bean:message
												key="knowledgepro.hostel.adminmessage.hostel" /></div>
											</td>
											<td width="30%" height="25" class="row-even">
											 <html:select property="hostelIdForReAdm" styleId="hostelId" styleClass="comboLarge" name="hostelReAdmissionForm">
										       <html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
										      <html:optionsCollection property="hostelMap" name="hostelReAdmissionForm" label="value" value="key" />
									        </html:select>
											</td>
										</tr>
										<tr>
										 <td width="25%" height="25" class="row-odd" >
								            <div align="right"><span class="Mandatory">*</span>
									           <bean:message key="knowledgepro.admin.search"/>:</div>
								          </td>
									         <td width="25%" height="25" class="row-even" colspan="3">
									         
												<div align="left">
												<html:radio property="hlAdmSelection" value="NotSelected"
													styleId="notSelect" ></html:radio>
												Not Selected&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
												<html:radio property="hlAdmSelection"
													styleId="select" value="Selected" ></html:radio>
												Selected&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
												<html:radio property="hlAdmSelection"
													styleId="all" value="All" ></html:radio>
												All
												</div>
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
						  <logic:notEmpty property="reAdmissionToList" name="hostelReAdmissionForm">
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
								    <td width="5%" class="row-odd">
												SelectAll<input type="checkbox" id="checkAll" onclick="selectAll(this)"/> 
									</td>
									<td width="25%" height="25" class="row-odd" align="center"><bean:message
										key="knowledgepro.attendance.studentName" /></td>
									<td width="17%" class="row-odd" align="center"><bean:message
										key="knowledgepro.admission.regno"/></td>
									<td width="10%" class="row-odd" align="center">
										<div align="center"><bean:message
											key="knowledgepro.exam.blockUnblock.class" /></div>
									</td>
									<td width="10%" height="25" class="row-odd" align="center">
										<div align="center"><bean:message key="knowledgepro.hostel.readmission.selection.applied.room.type" /></div>
									</td>
									<td width="15%" class="row-odd">
										<div align="center">Change Room Type</div>
									</td>
									<td width="20%" class="row-odd">
										<div align="center">View Student Details</div>
									</td>
								</tr>
									<nested:iterate id="admissionTo" property="reAdmissionToList" name="hostelReAdmissionForm" indexId="count"> 
									  <tr>
									  <td class="row-even" align="center">
											<input type="hidden" name="reAdmissionToList[<c:out value='${count}'/>].tempChecked"	id="hidden_<c:out value='${count}'/>"
												value="<nested:write name='admissionTo' property='tempChecked'/>" />
											<input type="checkbox" name="reAdmissionToList[<c:out value='${count}'/>].checked" id="<c:out value='${count}'/>"  onclick="unCheckSelectAll()" />
											<script type="text/javascript">
												var studentId = document.getElementById("hidden_<c:out value='${count}'/>").value;
												if(studentId == "on") {
												document.getElementById("<c:out value='${count}'/>").checked = true;
												}		
											</script>
									</td>
									<td class="row-even" align="left"><bean:write name="admissionTo" property="studentName"/> </td>
									<td class="row-even" align="center"><bean:write name="admissionTo" property="registerNo"/> </td>
									<td class="row-even" align="center"><bean:write name="admissionTo" property="studentClassName"/></td>
									<td class="row-even" align="center"><bean:write name="admissionTo" property="appliedRoomType"/></td>
									<td class="row-even" align="center">
									 <nested:select property="changeRoomType" styleId="changeRoomType" styleClass="combo" >
										       <html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
										      <html:optionsCollection property="roomTypeMap" name="hostelReAdmissionForm" label="value" value="key" />
									        </nested:select>
									</td>
									<td class="row-even" align="center"><a id="hyper2" href="#" onclick="viewStudentDetails('<bean:write name="admissionTo" property="registerNo"/>','<bean:write name="admissionTo" property="studentName"/>','<bean:write name="admissionTo" property="studentApplicationNo"/>');">
									<span>View Details</span></a></td>
									  </tr>
									</nested:iterate>	
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
						      <tr>
							<td height="25">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td width="45%" height="35">
									<div align="right"><c:choose>
										<c:when test="${operation == 'continueToUpdate'}">
										   <html:submit property="" styleClass="formbutton" styleId="Update" value="Update"></html:submit>
										</c:when>
										<c:otherwise>
											<html:submit property="" styleClass="formbutton"
												value="Search"  styleId="Search"></html:submit>
										</c:otherwise>
									</c:choose></div>
									</td>
									<td width="2%"></td>
									<td width="53%">
									  <c:choose>
										<c:when test="${operation == 'continueToUpdate'}">
										 <html:button property="" styleClass="formbutton"  styleId="close" value="Close" >
							            </html:button>
										</c:when>
										<c:otherwise>
										<html:button property="" styleClass="formbutton"  onclick="resetFieldAndErrMsgs()" value="Reset" >
							            </html:button>
							            </c:otherwise>
							            </c:choose>
								   </td>
								</tr>
							</table>
							</td>
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
<script type="text/javascript">
var methodName=document.getElementById("method").value;
if(methodName=="updateHostelOnlineApplication"){
	document.getElementById("academicYear").disabled = true;
	document.getElementById("hostelId").disabled = true;
	document.getElementById("notSelect").disabled = true;
	document.getElementById("select").disabled = true;
	document.getElementById("all").disabled = true;
}else{
	document.getElementById("academicYear").disabled = false;
	document.getElementById("hostelId").disabled = false;
	document.getElementById("notSelect").disabled = false;
	document.getElementById("select").disabled = false;
	document.getElementById("all").disabled = false;
}
var year=document.getElementById("tempyear").value;
if(year!=null && year!=""){
	document.getElementById("academicYear").value=year;
}
</script>