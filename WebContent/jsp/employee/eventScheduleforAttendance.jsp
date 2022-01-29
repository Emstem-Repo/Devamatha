<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@page import="java.util.Map,java.util.HashMap"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<head>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<SCRIPT>
	function cancelAction() {
		document.location.href = "eventSchedule.do?method=initEventScheduleForAttendance";
	}
	function moveoutid() {
		var mapFrom = document.getElementById('mapClass');
		var len = mapFrom.length;
		var mapTo = document.getElementById('selsubMap');

		if (mapTo.length == 0) {
			document.getElementById("moveIn").disabled = false;
		}
		for ( var j = 0; j < len; j++) {
			if (mapFrom[j].selected) {

				//listClasses.push(mapFrom[j].value);
				var tmp = mapFrom.options[j].text;
				var tmp1 = mapFrom.options[j].value;
				mapFrom.remove(j);
				len--;
				j--;
				if (j < 0) {
					document.getElementById("moveOut").disabled = true;
					document.getElementById("moveIn").disabled = false;
				}
				if (mapFrom.length <= 0)
					document.getElementById("moveOut").disabled = true;
				else
					document.getElementById("moveOut").disabled = false;
				var y = document.createElement('option');

				y.text = tmp;
				y.value = tmp1;
				y.setAttribute("class", "comboBig");
				try {
					mapTo.add(y, null);
				} catch (ex) {
					mapTo.add(y);
				}
			}
		}
	}

	function moveinid() {
		var mapFrom = document.getElementById('mapClass');
		var mapTo = document.getElementById('selsubMap');
		var len = mapTo.length;

		for ( var j = 0; j < len; j++) {
			if (mapTo[j].selected) {
				
				var tmp = mapTo.options[j].text;
				var tmp1 = mapTo.options[j].value;
				mapTo.remove(j);
				len--;
				j--;
				if (j < 0) {
					document.getElementById("moveIn").disabled = true;
					document.getElementById("moveOut").disabled = false;
				}
				if (mapTo.length != 0) {
					document.getElementById("moveOut").disabled = false;
					document.getElementById("moveIn").disabled = false;
				} else
					document.getElementById("moveOut").disabled = false;
				var y = document.createElement('option');
				y.setAttribute("class", "comboBig");
				y.text = tmp;
				y.value = tmp1;
				try {
					mapFrom.add(y, null);
				} catch (ex) {
					mapFrom.add(y);
				}
			}
		}

	}
	
	function moveoutidForDept() {
		var mapFrom = document.getElementById('deptMap');
		var len = mapFrom.length;
		var mapTo = document.getElementById('deptsubMap');

		if (mapTo.length == 0) {
			document.getElementById("moveInForDept").disabled = false;
		}
		for ( var j = 0; j < len; j++) {
			if (mapFrom[j].selected) {

				//listClasses.push(mapFrom[j].value);
				var tmp = mapFrom.options[j].text;
				var tmp1 = mapFrom.options[j].value;
				mapFrom.remove(j);
				len--;
				j--;
				if (j < 0) {
					document.getElementById("moveOutForDept").disabled = true;
					document.getElementById("moveInForDept").disabled = false;
				}
				if (mapFrom.length <= 0)
					document.getElementById("moveOutForDept").disabled = true;
				else
					document.getElementById("moveOutForDept").disabled = false;
				var y = document.createElement('option');

				y.text = tmp;
				y.value = tmp1;
				y.setAttribute("class", "comboBig");
				try {
					mapTo.add(y, null);
				} catch (ex) {
					mapTo.add(y);
				}
			}
		}
	}

	function moveinidForDept() {
		var mapFrom = document.getElementById('deptMap');
		var mapTo = document.getElementById('deptsubMap');
		var len = mapTo.length;

		for ( var j = 0; j < len; j++) {
			if (mapTo[j].selected) {
				
				var tmp = mapTo.options[j].text;
				var tmp1 = mapTo.options[j].value;
				mapTo.remove(j);
				len--;
				j--;
				if (j < 0) {
					document.getElementById("moveInForDept").disabled = true;
					document.getElementById("moveOutForDept").disabled = false;
				}
				if (mapTo.length != 0) {
					document.getElementById("moveOutForDept").disabled = false;
					document.getElementById("moveInForDept").disabled = false;
				} else
					document.getElementById("moveOutForDept").disabled = false;
				var y = document.createElement('option');
				y.setAttribute("class", "comboBig");
				y.text = tmp;
				y.value = tmp1;
				try {
					mapFrom.add(y, null);
				} catch (ex) {
					mapFrom.add(y);
				}
			}
		}

	}
	function getDeptNames() {
		if(document.getElementById("staff").checked==true){
			var id=1;
			getDepartments("classMap",id,"deptMap", updateClass);
		}
		
		if(document.getElementById("staff").checked==true)
		var type=document.getElementById("staff").value;
		
		if(document.getElementById("student").checked==true)
			var type=document.getElementById("student").value;
		
		if (type == "Staff") {
			document.getElementById("showStDetails").style.display = "none";
			document.getElementById("showDeptDetails").style.display = "block";
			document.getElementById("showStudentDetails").style.display = "none";
			document.getElementById("showstaffDetails").style.display = "block";
		}else if(type == "Student"){
			document.getElementById("showDeptDetails").style.display = "none";
			document.getElementById("showStDetails").style.display = "block";
			document.getElementById("showstaffDetails").style.display = "none";
			document.getElementById("showStudentDetails").style.display = "block";
		}
	}
	function updateClass(req){
		updateOptionsFromMapMultiselect(req, "deptMap", "");
	}
	
	function getPeriods() {
		if(document.getElementById("no").checked==true)
		var type=document.getElementById("no").value;
		if(document.getElementById("yes").checked==true)
			var type=document.getElementById("yes").value;
		if (type == "No") {
			document.getElementById("showperiods").style.display = "none";
		}else if(type == "Yes"){
			document.getElementById("showperiods").style.display = "block";
		}
		resetOption("subject");
	}
	function getclassesByYear(){
			var year=document.getElementById("year").value;
			getClassesByYear("classMap", year, "mapClass", updateClass1);
		}
	function updateClass1(req){
		updateOptionsFromMapMultiselect(req, "mapClass", "");
	}
	function getClassValues(){
		var listClasses=new Array(); 
		if(document.getElementById("student").checked==true){
			var mapTo1 = document.getElementById('selsubMap');
			var len1 = mapTo1.length;
			for(var k=0; k<len1; k++)
			{
				listClasses.push(mapTo1[k].value);
			}
		}
		if(document.getElementById("staff").checked==true){
			var mapTo1 = document.getElementById('deptsubMap');
			var len1 = mapTo1.length;
			for(var k=0; k<len1; k++)
			{
				listClasses.push(mapTo1[k].value);
			}
		}
		document.getElementById("classValues").value=listClasses;
		
	}
	function getPeriodsByClasses(){
		var fromMapClasses = document.getElementById('mapClass');
		var tomapClasses = document.getElementById('selsubMap');

		var destination1 = document.getElementById("fromPeriod");
		for (x1=destination1.options.length-1; x1>=0; x1--) {
			destination1.options[x1]=null;
		}

		var destination2 = document.getElementById("toPeriod");
		for (x1=destination2.options.length-1; x1>=0; x1--) {
			destination2.options[x1]=null;
		}

		
			destination1.options[0]=new Option("- Loading -","");
			destination2.options[0]=new Option("- Loading -","");
			var selectedClasses1 = new Array();
				
			
				var len = tomapClasses.length;
				for(var k=0; k<len; k++)
				{
					selectedClasses1.push(tomapClasses[k].value);
				}
				getPeriodsByClassesvalues(selectedClasses1,updateSubjcetBatchPeriod);
		}
		
		
	function updateSubjcetBatchPeriod(req) {
		var responseObj = req.responseXML.documentElement;
		var destination3 = document.getElementById("toPeriod");
		var destination4 = document.getElementById("fromPeriod");
		destination4.options[0]=new Option("- Select -","");
		
		destination3.options[0]=new Option("- Select -","");
		var items1 = responseObj.getElementsByTagName("period");
		for (var j = 0 ; j < items1.length ; j++) {
	        label = items1[j].getElementsByTagName("optionlabel")[0].firstChild.nodeValue;
		     value = items1[j].getElementsByTagName("optionvalue")[0].firstChild.nodeValue;
		     destination3.options[j+1] = new Option(label,value);
		 }
		for (var k = 0 ; k < items1.length ; k++) {
	        label = items1[k].getElementsByTagName("optionlabel")[0].firstChild.nodeValue;
		     value = items1[k].getElementsByTagName("optionvalue")[0].firstChild.nodeValue;
		     destination4.options[k+1] = new Option(label,value);
		 }
	}


	function editEventSchedule(id){
		if(document.getElementById("staff").checked==true){
			var type=document.getElementById("staff").value;
		}else if(document.getElementById("student").checked==true){
			var type=document.getElementById("student").value;
		}
		document.location.href = "eventSchedule.do?method=editEventSchedule&id="+id+"&type="+type;
	}
	function deleteEventSchedule(id){
		if(document.getElementById("staff").checked==true){
			var type=document.getElementById("staff").value;
		}else if(document.getElementById("student").checked==true){
			var type=document.getElementById("student").value;
		}
		document.location.href = "eventSchedule.do?method=deleteEventSchedule&id="+id+"&type="+type;
	}
</SCRIPT>
</head>
<html:form action="/eventSchedule" method="POST" enctype="multipart/form-data">
	<html:hidden property="formName" value="eventScheduleForAttendanceForm" styleId="formName" />
	<html:hidden property="pageType" value="1" styleId="pageType" />
	<html:hidden property="classValues" styleId="classValues" />
	<c:choose>
		<c:when test="${Operation == 'edit'}">
			<html:hidden property="method" styleId="method"
				value="updateStudentOrStaffDetails" />
		</c:when>
		<c:otherwise>
			<html:hidden property="method" styleId="method"
				value="addStudentOrStaffDetails" />
		</c:otherwise>
	</c:choose>
	<table width="99%" border="0">
		<tr>
			<td><span class="heading"><span class="Bredcrumbs">Employee
			&gt;&gt; Event Schedule For Attendance&gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="heading_white">Event Schedule For Attendance</td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td height="20" class="news">

					<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
					<FONT color="green"> <html:messages id="msg"
						property="messages" message="true">
						<c:out value="${msg}" escapeXml="false"></c:out>
						<br>
					</html:messages> </FONT></div>
					<div align="right" class="mandatoryfield">*Mandatory fields</div>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				
				<tr>
				<logic:equal value="true" name="eventScheduleForAttendanceForm" property="flag">
					 <c:if test="${eventScheduleForAttendanceForm.dupClasses != null && eventScheduleForAttendanceForm.dupClasses  != ''}">
					<FONT color="red" size="1"><bean:write name="eventScheduleForAttendanceForm" property="dupClasses"/><br/>
					</FONT><br/></c:if>
					</logic:equal>
				</tr>
				<tr>
				<logic:equal value="true" name="eventScheduleForAttendanceForm" property="flag1">
					 <c:if test="${eventScheduleForAttendanceForm.dupDepartments != null && eventScheduleForAttendanceForm.dupDepartments != ''}">
					<FONT color="red" size="1"><bean:write name="eventScheduleForAttendanceForm" property="dupDepartments"/><br/>
					</FONT><br/></c:if>
					</logic:equal>
				</tr>
				
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="heading">
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

									<td height="25" colspan="4" class="row-even">
									<div align="Center">
									<html:radio property="type" value="Student"
										styleId="student" onclick="getDeptNames()"></html:radio>
									Student&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<html:radio property="type"
										styleId="staff" value="Staff" onclick="getDeptNames()"></html:radio>
									Staff
									</div>
									</td>
								</tr>
								<tr>
								<td height="25" class="row-odd" width="25%">
											<div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.employee.event.desc" /> :</div>
											</td>
											<td class="row-even" width="25%"><html:text name="eventScheduleForAttendanceForm"
												property="eventDesc" styleId="eventDesc"
												styleClass="comboMediumLarge" size="16" maxlength="50"/>
												</td>
									<td height="25" class="row-odd" width="25%">
												<div align="right"><span class="Mandatory">*</span><bean:message
												key="employee.info.job.eventLocationName" /> :</div>
												</td>
												<td height="25" class="row-even" width="25%">
												<html:select styleId="eventLocation"  property="eventLocation" name="eventScheduleForAttendanceForm" styleClass="combo">
													<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
													<logic:notEmpty  name="eventScheduleForAttendanceForm" property="eventLocationMap">
													<html:optionsCollection name="eventScheduleForAttendanceForm" property="eventLocationMap" label="value" value="key"/></logic:notEmpty>	
												</html:select></td>
								</tr>
								<tr>
								<td width="25%" height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message key="knowledgepro.employee.event.date" />:</div>
									</td>
									<td width="75%" class="row-even" colspan="3">
									<table width="82" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td width="60"><html:text
												name="eventScheduleForAttendanceForm" property="eventDate"
												styleId="eventDate" maxlength="10"
												styleClass="TextBox" size="10" /></td>
											<td width="40"><script language="JavaScript">
												new tcal( {
													// form name
													'formname' :'eventScheduleForAttendanceForm',
													// input name
													'controlname' :'eventDate'
												});
											</script></td>
										</tr>
									</table>
								</td>
								</tr>
								<tr>
									<td class="row-odd" width="25%"><div align="right"><span class='MandatoryMark'>*</span><bean:message key="knowledgepro.auditorium.time.timeFrom"/>:</div></td>
									<td class="row-even" width="25%" >
										<html:text property="timeFrom"  styleClass="Timings"  styleId="timeFrom" size="2" maxlength="2"  onfocus="clearField(this)" onblur="checkForEmpty(this)" onkeypress="return isNumberKey(event)"/>:
										<html:text property="timeFromMin"  styleClass="Timings"  styleId="timeFromMin" size="2" maxlength="2"  onfocus="clearField(this)" onblur="checkForEmpty(this)" onkeypress="return isNumberKey(event)"/>
										
									</td>
									
									<td class="row-odd" width="25%"><div align="right"><span class='MandatoryMark'>*</span><bean:message key="knowledgepro.auditorium.time.timeTo"/>:</div></td>
									<td class="row-even" width="25%" >
										<html:text property="timeTo" styleClass="Timings"  styleId="timeTo" size="2" maxlength="2"  onfocus="clearField(this)" onblur="checkForEmpty(this)" onkeypress="return isNumberKey(event)"/>:
										<html:text property="timeToMIn" styleClass="Timings"  styleId="timeToMIn" size="2" maxlength="2"  onfocus="clearField(this)" onblur="checkForEmpty(this)" onkeypress="return isNumberKey(event)"/>
										
									</td>
								</tr>
							</table>
							</td>
							<td width="5" background="images/right.gif"></td>
						</tr>
						
						
						
						<tr >
						<td width="5" background="images/left.gif"></td>
						
						
								<td id = "showStDetails" >
								<table width="100%" cellspacing="1" cellpadding="2" border="0">
								<tr>
								<td height="25" class="row-odd"><div align="right"><span class="Mandatory">*</span> <bean:message
										key="knowledgepro.admin.year" /> :</div></td>
									<td width="16%" class="row-even" valign="top" colspan="3">
									<input
										type="hidden" id="tempyear" name="appliedYear"
										value="<bean:write name="eventScheduleForAttendanceForm" property="year"/>" />
									<html:select 
										property="year" styleId="year"
										styleClass="combo" onchange="getclassesByYear()">
										<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
										<cms:renderAcademicYear></cms:renderAcademicYear>
									</html:select></td>
								
								
								</tr>
								<tr>
								<td width="22%" height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span> <bean:message
										key="knowledgepro.employee.event.is.attendance.required" />
									:</div>
									</td>
									<td height="25"  class="row-even" colspan="3">
									<input type="hidden" name="eventScheduleForAttendanceForm" id="isPeriodSelected"
																		value="<nested:write name='eventScheduleForAttendanceForm' property='periodSelected'/>" />	
									<div align="Left">
									<html:radio property="isAttendanceRequired"
										styleId="no" value="No" onclick="getPeriods()"></html:radio>
									No&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<html:radio property="isAttendanceRequired" value="Yes"
										styleId="yes" onclick="getPeriods()"></html:radio>
									Yes
									</div>
									<script type="text/javascript">
										var selected = document.getElementById("isPeriodSelected").value;
												if(selected == "true") {
													document.getElementById("yes").checked = true;
												}		
									</script>
									</td>
								
								
								
								
								</tr>
						<tr>
			                <td width="25%" height="25" valign="top"  class="row-odd" >
			                  <div align="right"><span class="Mandatory">*</span>
			                <bean:message key="knowledgepro.admin.assignClasses.class" /> :</div></td>
			                <td width="75%" colspan="3"  height="25"   class="row-even">
			                  <table width="440" border="0">
			                  <tr><td width="118">
			                 <label>
					       <nested:select
							property="classIdsFrom" styleClass="body"
							multiple="multiple" size="8" styleId="mapClass"
							style="width:200px">
										<logic:notEmpty name="eventScheduleForAttendanceForm"
										property="mapClass">
										<nested:optionsCollection name="eventScheduleForAttendanceForm"
										property="mapClass" label="value" value="key"
										styleClass="comboBig" />
										</logic:notEmpty>
							</nested:select>
			                  </label>
			                  </td>
			                  <td width="52">
			                  <table border="0">
			                  <tr><td>
			                  <input type="button" onClick=" moveoutid(),getPeriodsByClasses()" 
																	id="moveOut" value=">>"></td></tr>
			                  <tr><td><input type="button" value="<<" 
																id="moveIn" onclick="moveinid(),getPeriodsByClasses()"></td></tr></table></td>
			                  <td width="256">
			                    <label>
			                      <nested:select
									property="classIdsTo" styleId="selsubMap" styleClass="body"
									multiple="multiple" size="8" style="width:200px;">
										<logic:notEmpty name="eventScheduleForAttendanceForm"
										property="mapSelectedClass">
										<nested:optionsCollection
											name="eventScheduleForAttendanceForm"
											property="mapSelectedClass" label="value" value="key"
											styleClass="comboBig" />
											</logic:notEmpty>
								</nested:select> 
			                    </label>
			                    </td>
			                    </tr>
			                    </table>
                    </td>
                </tr>
								</table>
								</td>
						
						<td width="5" background="images/right.gif"></td>
						</tr>
		 <logic:equal name="eventScheduleForAttendanceForm" property="type" value="Student">				
			<tr >
			<td width="5" background="images/left.gif"></td>
			<td id="showperiods" >
			<table width="100%" cellspacing="1" cellpadding="2" border="0">
			<tr>
                <td height="25" class="row-odd" width="25%" colspan="1">
					<div align="right"><span class="Mandatory">*</span><bean:message
					key="knowledgepro.from.period" /> :</div>
					</td>
					<td height="25" class="row-even" width="25%" colspan="1">
					<html:select styleId="fromPeriod"  property="fromPeriod" name="eventScheduleForAttendanceForm" styleClass="combo">
						<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
						<logic:notEmpty  name="eventScheduleForAttendanceForm" property="periodMap">
						<html:optionsCollection name="eventScheduleForAttendanceForm" property="periodMap" label="value" value="key"/></logic:notEmpty>	
					</html:select></td>
					<td height="25" class="row-odd" width="25%" colspan="1">
					<div align="right"><span class="Mandatory">*</span><bean:message
					key="knowledgepro.to.period" /> :</div>
					</td>
					<td height="25" class="row-even" width="25%" colspan="1">
					<html:select styleId="toPeriod"  property="toPeriod" name="eventScheduleForAttendanceForm" styleClass="combo">
						<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
						<logic:notEmpty  name="eventScheduleForAttendanceForm" property="periodMap">
						<html:optionsCollection name="eventScheduleForAttendanceForm" property="periodMap" label="value" value="key"/></logic:notEmpty>	
					</html:select></td>
					</tr>
					</table>
					</td>
					<td width="5" background="images/right.gif"></td>
				</tr>
						
					</logic:equal>	
						
						
						
						<tr >
							<td width="5" background="images/left.gif"></td>
							
							
								<td id = "showDeptDetails">
								<table width="100%" cellspacing="1" cellpadding="2" >
								<tr>
								<td height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span> <bean:message
										key="knowledgepro.hostel.applicationByAdmin.employee.department" />
									:</div>
									</td>
									<td  height="25" class="row-even" colspan="3">

									<table >
										<tr>
											<td ><label>
											
											 <nested:select
												property="deptSelectedFrom" styleClass="body"
												multiple="multiple" size="8" styleId="deptMap"
												style="width:200px">
												<logic:notEmpty name="eventScheduleForAttendanceForm"
														property="mapDept">
														<nested:optionsCollection name="eventScheduleForAttendanceForm"
															property="mapDept" label="value" value="key"
															styleClass="comboBig" />
													</logic:notEmpty>
											</nested:select> </label></td>
											<td >
													<table border="0">
														<tr>
															<td><input type="button" onClick="moveoutidForDept()"
																id="moveOutForDept" value="&gt;&gt;">&nbsp;&nbsp;&nbsp;</td>
														</tr>
														<tr>
															<td><input type="button"  value="<<" id="moveInForDept" onclick="moveinidForDept()" ></td>
														</tr>
													</table>
											</td>
											<td ><label> <nested:select
												property="deptSelectedTo" styleId="deptsubMap"
												styleClass="body" multiple="multiple" size="8"
												style="width:200px;">
												<logic:notEmpty name="eventScheduleForAttendanceForm"
													property="mapSelectedDept">
													<nested:optionsCollection name="eventScheduleForAttendanceForm"
														property="mapSelectedDept" label="value" value="key"
														styleClass="comboBig" />
												</logic:notEmpty>
											</nested:select> </label></td>
										</tr>
									</table>
									</td>
									</tr>
									</table>
									</td>
							<td width="5" background="images/right.gif"></td>
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
								<td width="45%" height="35">
									<div align="right"><c:choose>
										<c:when test="${Operation == 'edit'}">
											<html:submit property="" styleClass="formbutton"
												value="Update" styleId="submitbutton" onclick="getClassValues()">
											</html:submit>
											
										</c:when>
										<c:otherwise>
											<html:submit property="" styleClass="formbutton"
												value="Submit" styleId="submitbutton" onclick="getClassValues()">
											</html:submit>
										</c:otherwise>
									</c:choose></div></td>
							<td width="2%" height="35" align="center">&nbsp;</td>
							<td width="49%" height="35" align="left"><input
								type="button" class="formbutton" value="Cancel"
								onclick="cancelAction()" /></td>
						</tr>
					</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif"></td>
				</tr>
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
											<td height="25" >
										<table width="100%" cellspacing="1" cellpadding="2">	
												
												
						<tr>
						<td id = "showStudentDetails">
							
								<table width="100%" cellspacing="1" cellpadding="2">
								<tr>
													<td height="25" align="center" class="row-odd" width="5%" >
													<bean:message key="knowledgepro.admin.subject.subject.s1no" />
													</td>
													<td height="25" class="row-odd" align="center" width="20%"><bean:message
														key="knowledgepro.employee.event.desc" /></td>
													<td class="row-odd" align="center" width="20%"><bean:message
														key="employee.info.job.eventLocationName" /></td>
													<td class="row-odd" align="center" width="10%"><bean:message
														key="knowledgepro.employee.event.date" /></td>	
													<td class="row-odd" align="center" width="10%"><bean:message
													key="knowledgepro.auditorium.time.timeFrom" /></td>
													<td class="row-odd" align="center" width="10%"><bean:message
													key="knowledgepro.auditorium.time.timeTo" /></td>
													<td class="row-odd" width="15%">
													<div align="center"><bean:message
														key="knowledgepro.edit" /></div>
													</td>
													<td class="row-odd" width="15%">
													<div align="center"><bean:message
														key="knowledgepro.delete" /></div>
													</td>
												</tr>
												<logic:notEmpty name="eventScheduleForAttendanceForm" property="studentList">	
												<logic:iterate name="eventScheduleForAttendanceForm"
													property="studentList" id="student" indexId="count">
															<tr >
																<td height="25" class="row-even" width="5%">
																<div align="center"><c:out value="${count+1}" /></div>
																</td>
																<td height="25" class="row-even" align="center" width="20%"><bean:write
																	name="student" property="eventDescription" /></td>
																<td class="row-even" align="center" width="20%"><bean:write
																	name="student" property="eventLocation" /></td>
																<td class="row-even" align="center" width="10%"><bean:write
																	name="student" property="eventDate" /></td>
																<td class="row-even" align="center" width="10%"><bean:write
																	name="student" property="eventTimeFrom" /></td>
																<td class="row-even" align="center" width="10%"><bean:write
																	name="student" property="eventTimeTo" /></td>
																<td height="25" class="row-even" width="15%">
																<div align="center"><img
																	src="images/edit_icon.gif" width="10%" height="20"
																	style="cursor: pointer"
																	onclick="editEventSchedule('<bean:write name="student" property="id" />')" /></div>
																</td>
																<td width="15%" height="25" class="row-even">
																<div align="center"><img
																	src="images/delete_icon.gif" width="12%" height="20"
																	style="cursor: pointer"
																	onclick="deleteEventSchedule('<bean:write name="student" property="id" />')" /></div>
																</td>
															</tr>
												</logic:iterate>
												</logic:notEmpty>
												</table>
												
												</td>
												</tr>
							<tr>
						<td id = "showstaffDetails">
						<table width="100%" cellspacing="1" cellpadding="2">
						<tr>
													<td height="25" align="center" class="row-odd" width="5%" >
													<bean:message key="knowledgepro.admin.subject.subject.s1no" />
													</td>
													<td height="25" class="row-odd" align="center" width="20%"><bean:message
														key="knowledgepro.employee.event.desc" /></td>
													<td class="row-odd" align="center" width="20%"><bean:message
														key="employee.info.job.eventLocationName" /></td>
													<td class="row-odd" align="center" width="10%"><bean:message
														key="knowledgepro.employee.event.date" /></td>	
													<td class="row-odd" align="center" width="10%"><bean:message
													key="knowledgepro.auditorium.time.timeFrom" /></td>
													<td class="row-odd" align="center" width="10%"><bean:message
													key="knowledgepro.auditorium.time.timeTo" /></td>
													<td class="row-odd" width="15%">
													<div align="center"><bean:message
														key="knowledgepro.edit" /></div>
													</td>
													<td class="row-odd" width="15%">
													<div align="center"><bean:message
														key="knowledgepro.delete" /></div>
													</td>
												</tr>
							<logic:notEmpty name="eventScheduleForAttendanceForm" property="staffList">
									<logic:iterate name="eventScheduleForAttendanceForm"
													property="staffList" id="staff" indexId="count">
															<tr>
																<td height="25" class="row-even" width="4%">
																<div align="center"><c:out value="${count+1}" /></div>
																</td>
																<td height="25" class="row-even" align="center" width="20%"><bean:write
																	name="staff" property="eventDescription" /></td>
																<td class="row-even" align="center" width="20%"><bean:write
																	name="staff" property="eventLocation" /></td>
																<td class="row-even" align="center" width="10%"><bean:write
																	name="staff" property="eventDate" /></td>
																<td class="row-even" align="center" width="10%"><bean:write
																	name="staff" property="eventTimeFrom" /></td>
																<td class="row-even" align="center" width="10%"><bean:write
																	name="staff" property="eventTimeTo" /></td>
																<td height="25" class="row-even" width="15%">
																<div align="center"><img
																	src="images/edit_icon.gif" width="10%" height="20"
																	style="cursor: pointer"
																	onclick="editEventSchedule('<bean:write name="staff" property="id" />')" /></div>
																</td>
																<td width="15%" height="25" class="row-even">
																<div align="center"><img
																	src="images/delete_icon.gif" width="12%" height="20"
																	style="cursor: pointer"
																	onclick="deleteEventSchedule('<bean:write name="staff" property="id" />')" /></div>
																</td>
																</tr>
												</logic:iterate>
												</logic:notEmpty>
												</table>	
												</td>
												</tr>
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
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="heading"></td>
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
if(document.getElementById("staff").checked==true)
	var type=document.getElementById("staff").value;
	if(document.getElementById("student").checked==true)
		var type=document.getElementById("student").value;
	if (type == "Staff") {
		document.getElementById("showStDetails").style.display = "none";
		document.getElementById("showDeptDetails").style.display = "block";
		document.getElementById("showStudentDetails").style.display = "none";
		document.getElementById("showstaffDetails").style.display = "block";
	}else if(type == "Student"){
		document.getElementById("showDeptDetails").style.display = "none";
		document.getElementById("showStDetails").style.display = "block";
		document.getElementById("showstaffDetails").style.display = "none";
		document.getElementById("showStudentDetails").style.display = "block";
	}

	if(document.getElementById("student").checked==true){
		if(document.getElementById("no").checked==true)
			var type=document.getElementById("no").value;
			if(document.getElementById("yes").checked==true)
				var type=document.getElementById("yes").value;
			if (type == "No") {
				document.getElementById("showperiods").style.display = "none";
			}else if(type == "Yes"){
				document.getElementById("showperiods").style.display = "block";
			}
		}
	var year = document.getElementById("tempyear").value;
	if(year.length != 0) {
	 	document.getElementById("year").value=year;
	}
</script>
