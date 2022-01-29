<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<script type="text/javascript">
	function getMandatory(value) {
		if (value.length == 0) {
			document.getElementById("classMandatry").value = "no";
			document.getElementById("subjectMandatory").value = "no";
			document.getElementById("teacherMandatory").value = "no";
			document.getElementById("batchMandatory").value = "no";
			document.getElementById("activityTypeMandatory").value = "no";
			document.getElementById("batchMandatory").value = "no"; 
			document.getElementById("classsdiv").innerHTML = "Class:";
			document.getElementById("subjectdiv").innerHTML = "Subject:";
			document.getElementById("teacherdiv").innerHTML = "Teacher:";
			document.getElementById("activitydiv").innerHTML = "Activity Type:";
		} else {
			getMandatoryFieldsByAttendanceType("activityMap", value,
					updateMandatory);
		}
	}

	function updateMandatory(req) {
		var responseObj = req.responseXML.documentElement;
		var items = responseObj.getElementsByTagName("fields");
		for ( var i = 0; i < items.length; i++) {
			var label = items[i].getElementsByTagName("field")[0].firstChild.nodeValue;
			var value = items[i].getElementsByTagName("mandatory")[0].firstChild.nodeValue;
			if (label == "Class") {
				document.getElementById("classMandatry").value = value;
				if (value == "yes") {
					document.getElementById("classsdiv").innerHTML = "<span class='MandatoryMark'>*</span>Class:";
				} else {
					document.getElementById("classsdiv").innerHTML = "Class:";
				}
			} else if (label == "Subject") {
				document.getElementById("subjectMandatory").value = value;
				if (value == 'yes') {
					document.getElementById("subjectdiv").innerHTML = "<span class='MandatoryMark'>*</span>Subject:";
				} else {
					document.getElementById("subjectdiv").innerHTML = "Subject:";
				}
			} else if (label == "Teacher") {
				document.getElementById("teacherMandatory").value = value;
				if (value == "yes") {
					document.getElementById("teacherdiv").innerHTML = "<span class='MandatoryMark'>*</span>Teacher:";
				} else {
					document.getElementById("teacherdiv").innerHTML = "Teacher:";
				}
			} else if (label == "Activity Type") {
				document.getElementById("activityTypeMandatory").value = value;
				if (value == "yes") {
					document.getElementById("activitydiv").innerHTML = "<span class='MandatoryMark'>*</span>Activity Type:";
				} else {
					document.getElementById("activitydiv").innerHTML = "Activity Type:";
				}
			}else if(label == "Batch name") {
		    	 document.getElementById("batchMandatory").value = value;
		    	 if(value == "yes") {
						document.getElementById("batchdiv").innerHTML = "<span class='MandatoryMark'>*</span>Batch Name:";	
					} else {
						document.getElementById("batchdiv").innerHTML = "Batch Name:";
					}
		     }
		}
		var destination = document.getElementById("activityId");
		for (x1 = destination.options.length - 1; x1 > 0; x1--) {
			destination.options[x1] = null;
		}
		destination.options[0] = new Option("- Select -", "");
		var items1 = responseObj.getElementsByTagName("option");
		for ( var j = 0; j < items1.length; j++) {
			label = items1[j].getElementsByTagName("optionlabel")[0].firstChild.nodeValue;
			value = items1[j].getElementsByTagName("optionvalue")[0].firstChild.nodeValue;
			destination.options[j + 1] = new Option(label, value);
		}

	}
	function getAttYears(){
		var year = document.getElementById("academicYear").value;
		var year1=parseInt(year)+1;
		var destination = document.getElementById("year1");
		for (x1 = destination.options.length - 1; x1 > 0; x1--) {
			destination.options[x1] = null;
		}
		destination.options[0] = new Option("- Select -", "");
		if(year!=null){
			destination.options[1] = new Option(year, year);
			destination.options[2] = new Option(year1,year1);	
		}
	}
	function getClasses(year) {
		var destination1 = document.getElementById("subjectId");
		for (x1 = destination1.options.length - 1; x1 > 0; x1--) {
			destination1.options[x1] = null;
		}

		var destination3 = document.getElementById("batchId");
		for (x1=destination3.options.length-1; x1>0; x1--) {
			destination3.options[x1]=null;
		}
		
		destination1.options[0] = new Option("- Select -", "");
		destination3.options[0]=new Option("- Select -","");
		
		getClassesByYear("classMap", year, "classes", updateClasses);
		getAttYears();
	}
	function updateClasses(req) {
		var responseObj = req.responseXML.documentElement;
		var destination = document.getElementById("classes");
		for (x1 = destination.options.length - 1; x1 >= 0; x1--) {
			destination.options[x1] = null;
		}
		var items1 = responseObj.getElementsByTagName("option");
		for ( var j = 0; j < items1.length; j++) {
			label = items1[j].getElementsByTagName("optionlabel")[0].firstChild.nodeValue;
			value = items1[j].getElementsByTagName("optionvalue")[0].firstChild.nodeValue;
			destination.options[j] = new Option(label, value);
		}
	}

	function getSubjectsPeriodsBatchForClass() {

		var classes = document.getElementById("classes");

		var destination1 = document.getElementById("subjectId");
		for (x1 = destination1.options.length - 1; x1 > 0; x1--) {
			destination1.options[x1] = null;
		}
		
		if (classes.selectedIndex != -1) {
			destination1.options[0] = new Option("- Loading -", "");
			var year = document.getElementById("academicYear").value;
			var selectedClasses = new Array();
			var count = 0;
			for ( var i = 0; i < classes.options.length; i++) {
				if (classes.options[i].selected) {
					selectedClasses[count] = classes.options[i].value;
					count++;
				}
			}
			getSubjectsPeriodsBatchForClassSendRequest(selectedClasses, year,
					updateSubjcetBatchPeriod);

		}
	}

	function updateSubjcetBatchPeriod(req) {
		var responseObj = req.responseXML.documentElement;
		var destination1 = document.getElementById("subjectId");

		destination1.options[0] = new Option("- Select -", "");
		var items1 = responseObj.getElementsByTagName("subject");
		for ( var j = 0; j < items1.length; j++) {
			label = items1[j].getElementsByTagName("optionlabel")[0].firstChild.nodeValue;
			value = items1[j].getElementsByTagName("optionvalue")[0].firstChild.nodeValue;
			destination1.options[j + 1] = new Option(label, value);
		}
	}

	function submitData() {
		var obj1= document.getElementById("classes").selectedIndex;
		var obj2= document.getElementById("teachers").selectedIndex;
		document.getElementById("classSelectedIndex").value=obj1;
		document.getElementById("teacherSelectedIndex").value=obj2;
		document.getElementById("attenType").value = document.getElementById("attendanceTypeId").options[document.getElementById("attendanceTypeId").selectedIndex].text;
		document.getElementById("acaYear").value = document.getElementById("month").options[document.getElementById("month").selectedIndex].text;



		if(document.getElementById("subjectId").selectedIndex != 0) {
			document.getElementById("attendanceSubject").value = document.getElementById("subjectId").options[document.getElementById("subjectId").selectedIndex].text;
		}
		var classesString ="";
		var classes = document.getElementById("classes");
		for (var i=0; i<classes.options.length; i++) {
		    if (classes.options[i].selected) {
		    	classesString = classesString + classes.options[i].text+", ";
		    }
		 }

		var teacherString="";
		var teachers = document.getElementById("teachers");
		for (var j=0; j<teachers.options.length; j++) {
		    if (teachers.options[j].selected) {
		    	teacherString = teacherString + teachers.options[j].text+", ";
		    }
		 }
		classesString =  classesString.substr(0,(classesString.length - 2));
		teacherString = teacherString.substr(0,(teacherString.length - 2));
	
		document.getElementById("attendanceClass").value = classesString;
		if(document.getElementById("batchId").selectedIndex != 0) {
			document.getElementById("batchName").value = document.getElementById("batchId").options[document.getElementById("batchId").selectedIndex].text;
			
		}
		document.getElementById("attendanceTeacher").value = teacherString;
		document.monthlyAttendanceEntryForm.submit();
	}

	function getBatches(subjectId) {
		var classes =  document.getElementById("classes");
		
		var selectedClasses = new Array();
		var count = 0;
		for (var i=0; i<classes.options.length; i++) {
		    if (classes.options[i].selected) {
		    	selectedClasses[count] = classes.options[i].value;
		      count++;
		    }
		 }	

		getBatchesBySubject("subjectMap",subjectId,selectedClasses,"batchId",updateBatches);
	}

	function updateBatches(req) {
		updateOptionsFromMap(req,"batchId","- Select -");
	}

	
	function validNumber(field) {
		if (isNaN(field.value)) {
			field.value = "";
		}
	}
</script>
<html:form action="/MonthlyAttendanceEntry" method="post">

	<html:hidden property="formName" value="monthlyAttendanceEntryForm" />
	<html:hidden property="method" styleId="method"
		value="getMonthlyAttendanceList" />
	<html:hidden property="pageType" value="1" />
	<html:hidden property="classMandatry" styleId="classMandatry"
		name="monthlyAttendanceEntryForm" />
	<html:hidden property="subjectMandatory" styleId="subjectMandatory"
		name="monthlyAttendanceEntryForm" />
	<html:hidden property="teacherMandatory" styleId="teacherMandatory"
		name="monthlyAttendanceEntryForm" />
	<html:hidden property="activityTypeMandatory"
		styleId="activityTypeMandatory" name="monthlyAttendanceEntryForm" />
	<html:hidden property="classSelectedIndex" styleId="classSelectedIndex" />
	<html:hidden property="teacherSelectedIndex"
		styleId="teacherSelectedIndex" />
			<html:hidden property="attenType" styleId="attenType"/>
	<html:hidden property="acaYear" styleId="acaYear"/>
	<html:hidden property="attendanceClass" styleId="attendanceClass"/>
	<html:hidden property="batchName" styleId="batchName"/>
	<html:hidden property="attendanceSubject" styleId="attendanceSubject"/>
	<html:hidden property="attendanceTeacher" styleId="attendanceTeacher"/>
<html:hidden  property="batchMandatory" styleId="batchMandatory" name="monthlyAttendanceEntryForm" />

	<table width="99%" border="0">

		<tr>
			<td><span class="heading"><bean:message
				key="knowledgepro.attendanceentry.attendance" /> <span
				class="Bredcrumbs">&gt;&gt; 
				<logic:empty name="monthlyAttendanceEntryForm" property="operationMode">
				<bean:message
				key="knowledgepro.attendanceentry.monthlyattendance" />
				</logic:empty>
				<logic:equal name="monthlyAttendanceEntryForm" property="operationMode" value="editAttendance">
				<bean:message
				key="knowledgepro.attendanceentry.updatemonthlyattendance" />
				</logic:equal>
				<logic:equal name="monthlyAttendanceEntryForm" property="operationMode" value="cancelAttendance">
				<bean:message
				key="knowledgepro.attendanceentry.cancelmonthlyattendance" />
				</logic:equal>
				&gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="heading_white">
					
					
					<logic:empty name="monthlyAttendanceEntryForm" property="operationMode">
				<bean:message
				key="knowledgepro.attendanceentry.monthlyattendance" />
				</logic:empty>
				<logic:equal name="monthlyAttendanceEntryForm" property="operationMode" value="editAttendance">
				<bean:message
				key="knowledgepro.attendanceentry.updatemonthlyattendance" />
				</logic:equal>
				<logic:equal name="monthlyAttendanceEntryForm" property="operationMode" value="cancelAttendance">
				<bean:message
				key="knowledgepro.attendanceentry.cancelmonthlyattendance" />
				</logic:equal></td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td>
					<div align="right" style="color: red" class="heading"><span
						class='MandatoryMark'>* Mandatory fields</span></div>
					 <div id="errorMessage" style="font-family: verdana;font-size: 10px">
	                       <FONT color="red"><html:errors/></FONT>
	                       <FONT color="green">
								<html:messages id="msg" property="messages" message="true">
										<c:out value="${msg}" escapeXml="false"></c:out><br>
								</html:messages>
						  </FONT>
	  	   </div>

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
									<td height="25" class="row-odd" width="90" >
									<div align="right"><span class='MandatoryMark'>*</span><bean:message
										key="knowledgepro.attendanceentry.type" />:</div>
									</td>
									<td class="row-even" ><html:select style="width: 275px" 
										property="attendanceTypeId" styleId="attendanceTypeId"
										name="monthlyAttendanceEntryForm" styleClass="combo"
										onchange="getMandatory(this.value)">
										<html:option value="">- Select -</html:option>
										<html:optionsCollection name="monthlyAttendanceEntryForm"
											property="attendanceTypes" label="value" value="key" />
									</html:select></td>
									<td class="row-odd" width="70">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.fee.academicyear" />:</div>
									</td>
									<td class="row-even" align="left">
									<input type="hidden"
										id="yearcheck" name="yearcheck"
										value="<bean:write name="monthlyAttendanceEntryForm" property="academicYear"/>" />

									<html:select property="academicYear"  style="width: 175px"  styleId="academicYear"
										name="monthlyAttendanceEntryForm" styleClass="combo"
										onchange="getClasses(this.value)">
										<html:option value="">- Select -</html:option>
										<cms:renderAcademicYear></cms:renderAcademicYear>
									</html:select></td>
									<td class="row-odd" width="70">
									<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message
										key="knowledgepro.admin.month" />&nbsp; & &nbsp; <bean:message
										key="knowledgepro.admin.academicyear" />:</div>
									</td>
									<td class="row-even">
									<table width="82" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td width="17%" height="25" class="row-even">
											<input type="hidden"
										id="tempyear" name="tempyear"
										value="<bean:write name="monthlyAttendanceEntryForm" property="month"/>" />
											<html:select style="width: 175px" 
												property="month" styleId="month" styleClass="combo">
												<html:option value="">
													<bean:message key="knowledgepro.admin.select" />
												</html:option>

												<cms:renderMonths></cms:renderMonths>
											</html:select>
											<html:select style="width: 175px" 
												property="year1" styleId="year1" styleClass="combo">
												
												
												
												
												
												
											</html:select>
											
											
											</td>
										</tr>
									</table>
									</td>
									
								</tr>
								<tr>
									<td class="row-odd"><c:choose>
										<c:when
											test="${monthlyAttendanceEntryForm.classMandatry == 'yes'}">
											<div id="classsdiv" align="right"><span
												class='MandatoryMark'>*</span><bean:message
												key="knowledgepro.attendanceentry.class" />:</div>
										</c:when>
										<c:otherwise>
											<div id="classsdiv" align="right"><bean:message
												key="knowledgepro.attendanceentry.class" />:</div>
										</c:otherwise>
									</c:choose></td>
									<td class="row-even"><html:select 
										name="monthlyAttendanceEntryForm" styleId="classes"
										property="classes" size="5" style="width:275px"
										multiple="multiple"
										onchange="getSubjectsPeriodsBatchForClass()">
										<html:optionsCollection name="monthlyAttendanceEntryForm"
											property="classMap" label="value" value="key" />
									</html:select></td>
									<td class="row-odd"><c:choose>
										<c:when
											test="${monthlyAttendanceEntryForm.teacherMandatory == 'yes'}">
											<div id="teacherdiv" align="right"><span
												class='MandatoryMark'>*</span><bean:message
												key="knowledgepro.attendanceentry.teacher" />:</div>
										</c:when>
										<c:otherwise>
											<div id="teacherdiv" align="right"><bean:message
												key="knowledgepro.attendanceentry.teacher" />:</div>
										</c:otherwise>
									</c:choose></td>
									<td class="row-even"><html:select 
										name="monthlyAttendanceEntryForm" styleId="teachers"
										property="teachers" size="5" style="width:175px"
										multiple="multiple">
										<html:optionsCollection name="monthlyAttendanceEntryForm"
											property="teachersMap" label="value" value="key" />
									</html:select></td>
									<td class="row-odd"></td>
									<td class="row-even"></td>
								</tr>
								<tr>
									<td height="25" class="row-odd"><c:choose>
										<c:when
											test="${monthlyAttendanceEntryForm.subjectMandatory == 'yes'}">
											<div id="subjectdiv" align="right"><span
												class='MandatoryMark'>*</span><bean:message
												key="knowledgepro.attendanceentry.subject" />:</div>
										</c:when>
										<c:otherwise>
											<div id="subjectdiv" align="right"><bean:message
												key="knowledgepro.attendanceentry.subject" />:</div>
										</c:otherwise>
									</c:choose></td>
									
									<td class="row-even"><label> <html:select style="width:275px"
										property="subjectId" styleId="subjectId" styleClass="combo"  onchange="getBatches(this.value)">
										<html:option value="">- Select -</html:option>
										<html:optionsCollection name="monthlyAttendanceEntryForm"
											property="subjectMap" label="value" value="key" />
									</html:select> </label></td>
									
									<td class="row-odd">
									<div align="right"><c:choose>
										<c:when
											test="${monthlyAttendanceEntryForm.activityTypeMandatory == 'yes'}">
											<div id="activitydiv" align="right"><span
												class='MandatoryMark'>*</span><bean:message
												key="knowledgepro.attendanceentry.activitytype" />:</div>
										</c:when>
										<c:otherwise>
											<div id="activitydiv" align="right"><bean:message
												key="knowledgepro.attendanceentry.activitytype" />:</div>
										</c:otherwise>
									</c:choose></div>
									</td>
									<td class="row-even"><html:select style="width:175px"
										name="monthlyAttendanceEntryForm" property="activityId"
										styleId="activityId" styleClass="combo">
										<html:option value="">Select</html:option>
										<html:optionsCollection name="monthlyAttendanceEntryForm"
											property="activityMap" label="value" value="key" />
									</html:select></td>

									<td class="row-odd"><c:choose>
										<c:when test="${monthlyAttendanceEntryForm.batchMandatory == 'yes'}">
											<div id="batchdiv" align="right"><span
												class='MandatoryMark'>*</span><bean:message
												key="knowledgepro.attendanceentry.batchname" />:</div>
										</c:when>
										<c:otherwise>
											<div id="batchdiv" align="right"><bean:message
												key="knowledgepro.attendanceentry.batchname" />:</div>
										</c:otherwise>
									</c:choose></td>
									<td class="row-even"><html:select style="width:175px"
										name="monthlyAttendanceEntryForm" property="batchId"
										styleId="batchId" styleClass="combo">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<html:optionsCollection name="monthlyAttendanceEntryForm"
											property="batchMap" label="value" value="key" />
									</html:select></td>



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
							<td height="35" align="center">
							<html:button value="Next" styleClass="formbutton" property="" onclick="submitData()"></html:button>
							</td>
						</tr>
					</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="heading">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
					</table>
					<td valign="top" background="images/Tright_3_3.gif"></td>
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
	var year = document.getElementById("tempyear").value;
	if (year.length != 0) {
		document.getElementById("month").value = year;
	}
	var yearcheck = document.getElementById("yearcheck").value;
	if (yearcheck.length != 0) {
		document.getElementById("academicYear").value = yearcheck;
	}	
	var year2 = document.getElementById("academicYear").value;
	var year1=parseInt(year2)+1;
	var destination = document.getElementById("year1");
	for (x1 = destination.options.length - 1; x1 > 0; x1--) {
		destination.options[x1] = null;
	}
	destination.options[0] = new Option("- Select -", "");
	if(year!=null){
		destination.options[1] = new Option(year2, year2);
		destination.options[2] = new Option(year1,year1);	
	}
	document.getElementById("year1").value = resetYear();
</script>
