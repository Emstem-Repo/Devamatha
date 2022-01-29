<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<script language="JavaScript" src="js/calendar.js"></script>
<link href="css/styles.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
function getCourses(programTypeID) {
	resetOption("course");
	getCoursesByProgramType1("coursesMap", programTypeID, "course", updateCourses);
}
function updateCourses(req) {
	updateOptionsFromMap(req, "course", "- Select -");
}
	function selectAll(obj) {
		var start=0;
		var end=document.getElementById('countcheck').value;
	    var value = obj.checked;
	    var inputs = document.getElementsByTagName("input");
	    var inputObj;
	    var checkBoxselectedCount = 0;
	    for(var count1 = 0;count1<inputs.length;count1++) {
	          inputObj = inputs[count1];
	          var type = inputObj.getAttribute("type");
	            if (type == 'checkbox' && start<=end) {
	                  inputObj.checked = value;
	                  start++;
	            }
	    }
	}

	function selectAll1(obj) {
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
	function selectAll2(obj) {
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
	function selectAll3(obj) {
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
	function unCheckSelectAll1() {
	    var inputs = document.getElementsByTagName("input");
	    var inputObj;
	    var checkBoxOthersSelectedCount = 0;
	    var checkBoxOthersCount = 0;
	    for(var count1 = 0;count1<inputs.length;count1++) {
	          inputObj = inputs[count1];
	          var type = inputObj.getAttribute("type");
	            if (type == 'checkbox' && inputObj.id != "checkAll1") {
	                  checkBoxOthersCount++;
	                  if(inputObj.checked) {
	                        checkBoxOthersSelectedCount++;
	                  }     
	            }
	    }
	    if(checkBoxOthersCount != checkBoxOthersSelectedCount) {
	      document.getElementById("checkAll1").checked = false;
	    } else {
	      document.getElementById("checkAll1").checked = true;
	    }        
	}
	function unCheckSelectAll2() {
	    var inputs = document.getElementsByTagName("input");
	    var inputObj;
	    var checkBoxOthersSelectedCount = 0;
	    var checkBoxOthersCount = 0;
	    for(var count1 = 0;count1<inputs.length;count1++) {
	          inputObj = inputs[count1];
	          var type = inputObj.getAttribute("type");
	            if (type == 'checkbox' && inputObj.id != "checkAll2") {
	                  checkBoxOthersCount++;
	                  if(inputObj.checked) {
	                        checkBoxOthersSelectedCount++;
	                  }     
	            }
	    }
	    if(checkBoxOthersCount != checkBoxOthersSelectedCount) {
	      document.getElementById("checkAll2").checked = false;
	    } else {
	      document.getElementById("checkAll2").checked = true;
	    }        
	}
	function unCheckSelectAll3() {
	    var inputs = document.getElementsByTagName("input");
	    var inputObj;
	    var checkBoxOthersSelectedCount = 0;
	    var checkBoxOthersCount = 0;
	    for(var count1 = 0;count1<inputs.length;count1++) {
	          inputObj = inputs[count1];
	          var type = inputObj.getAttribute("type");
	            if (type == 'checkbox' && inputObj.id != "checkAll3") {
	                  checkBoxOthersCount++;
	                  if(inputObj.checked) {
	                        checkBoxOthersSelectedCount++;
	                  }     
	            }
	    }
	    if(checkBoxOthersCount != checkBoxOthersSelectedCount) {
	      document.getElementById("checkAll3").checked = false;
	    } else {
	      document.getElementById("checkAll3").checked = true;
	    }        
	}

	
	function getStudentDetails(){
		document.getElementById("method").value = "getStudentDetails";
		document.documentSubmissionScheduleForm.submit();
		}
	function submitShow(){
		document.getElementById("method").value = "submitShow";
		document.documentSubmissionScheduleForm.submit();
		}
	function AddDocumentToList(){
		document.getElementById("method").value = "AddDocumentToList";
		}
	function deleteDocumentList(id){
        	deleteConfirm = confirm("Are you sure to delete this entry?");
	if (deleteConfirm)
		document.location.href = "PhdDocumentSubmissionSchedule.do?method=deleteDocumentList&id="+id;
	}
	function submitStudentSubGroups(){
		document.getElementById("subjectGroupName").value = document
			.getElementById("subjectGroup").options[document
			.getElementById("subjectGroup").selectedIndex].text;
		document.getElementById("method").value = "addSubjectGroupDetails";
		document.assignSubjectGroupHistoryForm.submit();
	}
	function cancelPage(){
		document.location.href="PhdDocumentSubmissionSchedule.do?method=initPhdDocumentSubmissionSchedule";
	}
	function editStudentDetails(id){
		document.location.href="PhdDocumentSubmissionSchedule.do?method=editStudentDetails&id="+id;
	}
	function updateDocumentDetails(){
	document.getElementById("method").value = "updateDocumentDetails";
	document.assignSubjectGroupHistoryForm.submit();
	}
	function deletePhdDocumentDetails(id,tempDate) {
		deleteConfirm = confirm("Are you sure to delete this entry?");
		if (deleteConfirm)
		document.location.href="PhdDocumentSubmissionSchedule.do?method=deletePhdDocumentDetails&id="+id;
	}
	function getCurrentDate(count) {
		var currentDate = new Date();
		var day = currentDate.getDate();
		var month = currentDate.getMonth() + 1 ;
		var year = currentDate.getFullYear() ;
	    var date=(day + "/" + month + "/" + year);
		var check=document.getElementById("datePicker"+count).value;
		if(check!=null && check!=""){
		   document.getElementById("datePicker"+count).value=null;
		}else{
			document.getElementById("datePicker"+count).value=date;
		}
		
	}
</script>
<html:form action="/PhdDocumentSubmissionSchedule" method="post">
	<html:hidden property="method" styleId="method" value="" />
	<html:hidden property="formName" value="documentSubmissionScheduleForm" />
	<html:hidden property="countcheck" styleId="countcheck"/>
	<html:hidden property="pageType" value="1" />
	<table width="99%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message
				key="knowledgepro.phd" /> <span class="Bredcrumbs">&gt;&gt;
				Document Submission Schedule&gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="heading_white">Document Submission Schedule</td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="news">
					<div align="right"><span class='MandatoryMark'><bean:message
						key="knowledgepro.mandatoryfields" /> </span></div>
					<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
					<FONT color="green"><html:messages id="msg"
						property="messages" message="true">
						<c:out value="${msg}" escapeXml="false"></c:out>
						<br>
					</html:messages> </FONT></div>
					</td>
					<td valign="top" background="images/Tright_3_3.gif"></td>
				</tr>
				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top">
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
									 <td height="25" class="row-odd" width="30%">
									<div align="right"><span class="Mandatory">*</span>
										<bean:message key="knowledgepro.fee.appliedyear"/>:</div>
									</td>
									<td class="row-even" valign="top">
									<input type="hidden" id="tempyear" name="appliedYear"
										value="<bean:write name="documentSubmissionScheduleForm" property="year"/>" />
									<html:select property="year" styleId="year" styleClass="combo" >
										<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
										<cms:renderAcademicYear></cms:renderAcademicYear>
									</html:select></td>
								   </tr>
								<tr>
								<td height="25" class="row-odd"  width="30%"><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.admission.programtype"/>:</div></td>
			                	<td height="25" class="row-even" >
			                	<input type="hidden" id="currentProgramTypeId" value="<bean:write property="currentProgramType" name="documentSubmissionScheduleForm" />"/>
			                				<html:select property="programTypeId" styleId="programType" onchange="getCourses(this.value),setProgramTypeName()" styleClass="comboMediumBig">
												<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
												<html:optionsCollection name="documentSubmissionScheduleForm" property="programTypeList" label="programTypeName" value="programTypeId" />
											</html:select> 
			                				</td>
									</tr>
									<tr>
									<td  height="25" class="row-odd"  width="30%">
									<div align="right"><bean:message key="knowledgepro.admin.course" />:</div>
									</td>
									<td  height="25" class="row-even">
									<html:select property="selectedcourseId" styleId="course" styleClass="body" multiple="multiple" size="10" style="width:500px">
										<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
										<logic:notEmpty name="documentSubmissionScheduleForm" property="courseList">
												<html:optionsCollection name="documentSubmissionScheduleForm" property="courseList" label="name" value="id" />
										</logic:notEmpty>
									</html:select>
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
					<td valign="top" background="images/Tright_3_3.gif"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="heading">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td height="35" align="center"><html:submit property=""
								styleClass="formbutton" value="Search"
								onclick="getStudentDetails()"></html:submit></td>
						</tr>
					</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif"></td>
				</tr>
		 		<logic:present name="documentSubmissionScheduleForm" property="studentDetailsList">
					<tr>
						<td valign="top" background="images/Tright_03_03.gif"></td>
						<td valign="top">
						<div><FONT color="red" size="1">
						<bean:write name="documentSubmissionScheduleForm" property="displayMessage"/><br>
						<bean:write name="documentSubmissionScheduleForm" property="displayMsg"/>
						</FONT></div>
						<table width="100%" border="0" align="left" cellpadding="0"
							cellspacing="0">
							<tr>
								
								<td valign="top">
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
											<td width="10%" class="row-odd" align="center">
											 SelectAll<input type="checkbox" id="checkAll" onclick="selectAll(this)"/> 
											</td>
											<td width="25%" class="row-odd">
												<div align="center"><bean:message
													key="knowledgepro.admin.course" /></div>
												</td>
												<td width="10%" class="row-odd">
												<div align="center"><bean:message
													key="knowledgepro.attendanceentry.regno" /></div>
												</td>
												<td width="20%" class="row-odd">
												<div align="center"><bean:message
													key="knowledgepro.attendance.studentName" /></div>
												</td>
												<td width="15%" class="row-odd">
												<div align="center"><bean:message
													key="knowledgepro.phd.SynopsisDefense.feePaidDate" /></div>
												</td>
											<td width="10%" height="25" class="row-odd"><div align="center"><bean:message key="knowledgepro.admission.status" /></div></td>
											<td width="5%" height="25" class="row-odd"><div align="center"><bean:message key="knowledgepro.edit" /></div></td>
											<td width="5%" height="20" class="row-odd" align="center"><div align="center"><bean:message key="knowledgepro.delete" /></div></td>
											</tr>
											<tr>
											<nested:iterate id="studentList" property="studentDetailsList"
												name="documentSubmissionScheduleForm" indexId="count">
												<c:choose>
														<c:when test="${count%2 == 0}">
															<tr class="row-even">
														</c:when>
														<c:otherwise>
															<tr class="row-white">
														</c:otherwise>
					 								</c:choose>
													<td   align="center">
														<input type="hidden" name="studentDetailsList[<c:out value='${count}'/>].tempChecked"	id="hidden_<c:out value='${count}'/>"
																value="<nested:write name='studentList' property='tempChecked'/>" />
														
														<input type="checkbox" name="studentDetailsList[<c:out value='${count}'/>].checked" id="<c:out value='${count}'/>"  onclick="unCheckSelectAll()" />
																<script type="text/javascript">
																var studentId = document.getElementById("hidden_<c:out value='${count}'/>").value;
																if(studentId == "on") {
																document.getElementById("<c:out value='${count}'/>").checked = true;
																		}		
														</script>
													</td>
													<td >
														<div align="left"><bean:write property="courseName"
															name="studentList" /></div>
														</td>
													<td >
														<div align="center"><bean:write property="registerNo"
															name="studentList" /></div>
														</td>
														<td >
														<div align="left"><bean:write property="studentName"
															name="studentList" /></div>
														</td>
														<td >
														<div align="center"><bean:write property="feePaidDate"
															name="studentList" /></div>
														<input type="hidden" >
														</td>
														<td ><div align="center"><bean:write property="status" name="studentList" /></div></td>
														<c:choose>
														<c:when test="${studentList.editCheck == 'Yes'}">
														<td  align="center"><div align="center">
														<img src="images/edit_icon.gif" width="16" height="18" style="cursor: pointer" onclick="editStudentDetails('<bean:write name="studentList" property="id"/>')"></div>
														</td>
													    <td  align="center"><div align="center"><img src="images/delete_icon.gif" width="16" height="16"
														 style="cursor: pointer" onclick="deletePhdDocumentDetails('<bean:write name="studentList" property="id"/>')">
																	</div>

																	</td></c:when>
														<c:otherwise>
														<td ></td>
														<td ></td>
														</c:otherwise>
													</c:choose>
														</nested:iterate>
													  </tr> 
											</table>
										</td>
										<td width="5" background="images/right.gif"></td>
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
						<tr>
                         <td width="45%" height="25"></td>
                       </tr>
             		</table>
						</td><td valign="top" background="images/Tright_3_3.gif"></td>
						</tr>
				</logic:present>
				<tr>
						<td valign="top" background="images/Tright_03_03.gif"></td>
						<td valign="top">
						<table width="100%" border="0" align="left" cellpadding="0"
							cellspacing="0">
							<tr>
								<td valign="top">
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
										<logic:present name="documentSubmissionScheduleForm" property="studentDetailsList">
										<tr >
                                         <td width="20%" height="25" class="row-even"><div align="right"><bean:message key="knowledgepro.phd.document.name" /></div></td>
				                            <td width="20%" height="25" class="white" colspan="7"><span	class="star"> 
				                            <html:select property="documentId" styleClass="comboLarge" styleId="documentId"><html:option value=""><bean:message key="knowledgepro.admin.select" />
				                             </html:option><html:optionsCollection name="DocumentDetails" label="documentName" value="id" /></html:select></span>
				                             <html:submit property="" styleClass="formbutton" onclick="AddDocumentToList()">Add</html:submit></td>
                                             </tr>
                                             <tr >
                                            <td  height="25" class="white" colspan="8"></td>
				                            
                                             </tr>
                                             </logic:present>
                                             <logic:present name="documentSubmissionScheduleForm" property="documentList">
											<tr>
											<td width="15%" class="row-odd" align="center"><div align="center"><bean:message key="knowledgepro.phd.document.name" /></div></td>
											<td width="10%" class="row-odd" align="center"><div align="center"><bean:message key="knowledgepro.phd.Submission.Schedule.assigndate" /></div></td>
											<td width="15%" class="row-odd" align="center"><div align="center"><bean:message key="knowledgepro.phd.isreminder.mailrequired"/></div></td>
											<td width="15%" class="row-odd" align="center"><div align="center"><bean:message key="knowledgepro.phd.guide.feerequired"/></div></td>
											<td width="15%" class="row-odd" align="center"><div align="center"><bean:message key="knowledgepro.phd.can.submit.online"/></div></td>
											<td width="15%" class="row-odd" align="center"><div align="center"><bean:message key="knowledgepro.admin.ducuments.submitted" /></div></td>
											<td width="10%" class="row-odd" align="center"><div align="center"><bean:message key="knowledgepro.phd.Guide.submitted_date" /></div></td> 
											<td height="5%" class="row-odd" align="center"> <div align="center"><bean:message key="knowledgepro.delete" /></div></td>
										  </tr>
										
											<tr>
											<nested:iterate id="studentList" property="documentList"
												name="documentSubmissionScheduleForm" indexId="count">
													<%
										             String styleDate1 = "datePick" + count;
													 String styleDate2 = "datePicker" + count;
									                 %>
													<c:choose>
													<c:when test="${count%2 == 0}">
													<tr class="row-even">
													</c:when>
													<c:otherwise>
															<tr class="row-white">
														</c:otherwise>
					 								</c:choose>
											             <td >
														<div align="left"><bean:write property="documentName"
															name="studentList" /></div>
														</td>
								<td height="20" class="bodytext"><div align="center">									
								<nested:text styleId='<%=styleDate1%>'
								property="assignDate" size="10" maxlength="10" />
										<script language="JavaScript">
							 new tcal( {
								// form name
								'formname' :'documentSubmissionScheduleForm',
								// input name
								'controlname' :'<%=styleDate1%>'
							});
						</script></div>
										</td>
										        	<td  align="center">
														<input type="hidden" name="documentList[<c:out value='${count}'/>].tempChecked1"	id="hidden1_<c:out value='${count}'/>"
																value="<nested:write name='studentList' property='tempChecked1'/>" />
														<input type="checkbox" name="documentList[<c:out value='${count}'/>].checked1" id="hiden1_<c:out value='${count}'/>"  onclick="unCheckSelectAll1()" />
																<script type="text/javascript">
																var studentId = document.getElementById("hidden1_<c:out value='${count}'/>").value;
																if(studentId == "on") {
																document.getElementById("hiden1_<c:out value='${count}'/>").checked = true;
																		}		
														</script>
													</td>
													<td  align="center">
														<input type="hidden" name="documentList[<c:out value='${count}'/>].tempChecked2"	id="hidden2_<c:out value='${count}'/>"
																value="<nested:write name='studentList' property='tempChecked2'/>" />
														
														<input type="checkbox" name="documentList[<c:out value='${count}'/>].checked2" id="hiden2_<c:out value='${count}'/>"  onclick="unCheckSelectAll2()" />
																<script type="text/javascript">
																var studentId = document.getElementById("hidden2_<c:out value='${count}'/>").value;
																if(studentId == "on") {
																document.getElementById("hiden2_<c:out value='${count}'/>").checked = true;
																		}		
														</script>
													</td>
														<td  align="center">
														<input type="hidden" name="documentList[<c:out value='${count}'/>].tempChecked3"	id="hidden3_<c:out value='${count}'/>"
																value="<nested:write name='studentList' property='tempChecked3'/>" />
														
														<input type="checkbox" name="documentList[<c:out value='${count}'/>].checked3" id="hiden3_<c:out value='${count}'/>"  onclick="unCheckSelectAll3()" />
																<script type="text/javascript">
																var studentId = document.getElementById("hidden3_<c:out value='${count}'/>").value;
																if(studentId == "on") {
																document.getElementById("hiden3_<c:out value='${count}'/>").checked = true;
																		}		
														</script>
													</td>
														<td  align="center">
														<input type="hidden" name="documentList[<c:out value='${count}'/>].tempChecked4"	id="hidden4_<c:out value='${count}'/>"
																value="<nested:write name='studentList' property='tempChecked4'/>" />
														<% String docSubmit = "docSubmit"+count; %>
														<input type="checkbox" name="documentList[<c:out value='${count}'/>].checked4" id="hiden4_<c:out value='${count}'/>" onchange="getCurrentDate('<c:out value='${count}'/>')"/>
																<script type="text/javascript">
																var studentId = document.getElementById("hidden4_<c:out value='${count}'/>").value;
																if(studentId == "on") {
																document.getElementById("hiden4_<c:out value='${count}'/>").checked = true;
																		}	
														</script>
													</td>
													<td class="bodytext"><div align="center">	
							                        	<nested:text styleId='<%=styleDate2%>'
							                               	property="submittedDate" size="10" maxlength="10"/>
										                    <script language="JavaScript">
							                                 new tcal( {
								                               // form name
								                                'formname' :'documentSubmissionScheduleForm',
								                               // input name
							                                 	'controlname' :'<%=styleDate2%>'
						                                    	});
					                                        	</script></div>
									                 </td>
													<td  align="center"><div align="center">
													<img src="images/delete_icon.gif" width="16" height="16" style="cursor: pointer" onclick="deleteDocumentList('<bean:write name="studentList" property="id"/>')">
											      </div>
											      
											      </td>
														</nested:iterate>
										</tr> 
										</logic:present>
											</table>
										</td>
										<td width="5" background="images/right.gif"></td>
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
							<logic:present name="documentSubmissionScheduleForm" property="documentList">
							<tr>
                			<td width="45%" height="35"><div align="center"><c:choose>
							<c:when test="${DocumentDetailsList != null && DocumentDetailsList == 'edit'}">
							<html:submit property="" styleClass="formbutton" onclick="updateDocumentDetails()">
							<bean:message key="knowledgepro.update" /></html:submit>&nbsp;&nbsp;&nbsp;
							<html:submit property="" styleClass="formbutton" value="Cancel" onclick="getStudentDetails()"></html:submit>
							</c:when><c:otherwise>
							<html:submit property="" styleClass="formbutton" onclick="submitShow()">
							<bean:message key="knowledgepro.submit" /></html:submit>&nbsp;&nbsp;&nbsp;
							<html:button property="" styleClass="formbutton" value="Cancel" onclick="cancelPage()"></html:button>
										</c:otherwise>
									</c:choose></div>
									</td>
                       </tr>
                       </logic:present>
						</table>
						
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
<script type="text/javascript">
	var yearId = document.getElementById("tempyear").value;
	if (yearId != null && yearId.length != 0) {
		document.getElementById("year").value = yearId;
	}
	var programType = document.getElementById("currentProgramTypeId").value;
	if (programType != null && programType.length != 0) {
		document.getElementById("programType").value = programType;
	}
</script>