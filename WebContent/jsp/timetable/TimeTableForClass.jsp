
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
	<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@page import="java.util.Map,java.util.HashMap"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<script src="jquery/development-bundle/jquery-1.7.1.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<link type="text/css"
	href="jquery/css/ui-lightness/jquery-ui-1.8.18.custom.css"
	rel="stylesheet" />
<script src="jquery/js/jquery-ui-1.10.0.custom.min.js"
	type="text/javascript"></script>
<link rel="stylesheet" type="text/css"
	href="jquery.confirm/jquery.confirm/jquery.confirm.css" />
<script src="jquery.confirm/jquery.confirm/jquery.confirm.js"></script>
<script src="js/ajax/Ajax.js"></script>
<script src="js/ajax/AjaxUtil.js"></script>
<style>
.btnc {
	background-color: #008CBA;
	border: none;
	color: white;
	padding: 10px 18px;
	text-align: center;
	text-decoration: none;
	display: inline-block;
	font-size: 14px;
	border-radius: 12px;
}

.btnc:hover {
	cursor: pointer;
}
</style>
<script type="text/javascript">
	function disableDown() {
		if (document.getElementById("display") != null) {
			document.getElementById("display").style.display = "none";
		}
	}
	function getClasses() {
		var year = document.getElementById("academicYear").value;
		var evenOrOdd = "";
		if (document.getElementById("odd").checked == true) {
			evenOrOdd = document.getElementById("odd").value;
		} else {
			evenOrOdd = "even";
		}
		getClassesForAttendanceByYear("classMap", year, evenOrOdd, "class",
				updateClasses);
	}
	function updateClasses(req) {
		updateOptionsFromMap(req, "class", "- Select -");
	}
	function updateTimeTable(finalApprove) {
		if (finalApprove != "" && finalApprove == 'on') {
			var args = "method=updateFlagForTimeTable&finalApprove="
					+ finalApprove;
			var url = "timeTableForClass.do";
			requestOperation(url, args, timeTableUpdate);
			document.getElementById("finalApprove_2").disabled = true;
		}
	}
	function timeTableUpdate(req) {
		var responseObj = req.responseXML.documentElement;
		var value = responseObj.getElementsByTagName("value");
		if (value != null) {
			for (var I = 0; I < value.length; I++) {
				if (value[I].firstChild != null) {
					var temp = value[I].firstChild.nodeValue;
					document.getElementById("errorMessage").innerHTML = temp;
				}
			}
		}
	}
	function getPeriodDetails(position, count) {
		document.location.href = "timeTableForClass.do?method=getPeriodDetail&position="
				+ position + "&count=" + count;
	}
	function getTimeTableHistory() {
		document.location.href = "timeTableForClass.do?method=getTimeTableHistory";
	}
	function copyPeriods() {
		document.getElementById("method").value = "copyPeriodDetails";
		document.timeTableForClassForm.submit();
	}
	function copy() {
		document.location.href = "timeTableForClass.do?method=copyPeriods";
	}
	function resetFieldsAndErrors() {
		document.getElementById("academicYear").value = "";
		document.getElementById("class").value = "";
		resetErrMsgs();
	}

	function getcourses() {
		var year = document.getElementById("academicYear").value;
		getSubjectByYear("subjectMap", year, "course", updateCourses);
	}
	function updateCourses(req) {
		updateOptionsFromMap(req, "course", "- Select -");
	}
	function declareType(val) {
		if (val == 'classWise') {
			document.getElementById("courseWise").style.display = "none";
			document.getElementById("courseWisemsg").style.display = "none";
			document.getElementById("classWise").style.display = "block";
			document.getElementById("classWisemsg").style.display = "block"; 
			var myEle = document.getElementById("crsWiseEntry");
			if(myEle  != null){
			document.getElementById("crsWiseEntry").style.display = "none";
			}
		} else if (val == 'courseWise') {
			document.getElementById("courseWise").style.display = "block";
			document.getElementById("classWise").style.display = "none";
			document.getElementById("courseWisemsg").style.display = "block";
			document.getElementById("classWisemsg").style.display = "none";
			var myEle = document.getElementById("crsWiseEntry");
			if(myEle  != null){
			document.getElementById("crsWiseEntry").style.display = "block";
			}
		}
		console.log(val);

	}
	function enterCourseWise() {
		document.getElementById("method").value="updatePeriodCourseWise";
		document.timeTableForClassForm.submit();
	}
	function resetFields() {
		document.getElementById("classes").value = "";
		document.getElementById("periods").value = "";
		document.getElementById("teachers").value = "";
		document.getElementById("weeks").value = "";
		resetErrMsgs();
	}
	
	function getAttType() {
		var subId=document.getElementById("course").value;
		getAtType(subId,updateatattype);
	}
	function updateatattype(req) {
		var result = req.responseText;
			document.getElementById("atttype").value=result
	}
	 function getSubjectsfor() {
		var year = document.getElementById("academicYear");
		var evenOrOdd = "";
		if (document.getElementById("odd").checked == true) {
			evenOrOdd = document.getElementById("odd").value;
		} else {
			evenOrOdd = "even";
		}
		var year = document.getElementById("academicYear").value;
		
		var destination2 = document.getElementById("course");
		for (x1=destination2.options.length-1; x1>=0; x1--) {
			destination2.options[x1]=null;
		}
		
		 
		/* if(classes.selectedIndex != -1) {
			destination2.options[0]=new Option("- Loading -","");
			
			var selectedClasses = new Array();
			var count = 0;
			for (var i=0; i<classes.options.length; i++) {
			    if (classes.options[i].selected) {
			    	selectedClasses[count] = classes.options[i].value;
			      count++;
			    }
			 }	 */
			 getSubjectsForTeacher(evenOrOdd,year,updateSubjcets);
		/* }  */
	}
	function updateSubjcets(req) {
		var responseObj = req.responseXML.documentElement;
		
		
		var destination2 = document.getElementById("course");
		
		var items2 = responseObj.getElementsByTagName("subject");
		if(items2.length == 0) {
			var destination5 = document.getElementById("course");
			for (x1=destination5.options.length-1; x1>=0; x1--) {
				destination5.options[x1]=null;
			}
		}	
		for (var k = 0 ; k < items2.length ; k++) {
	        label = items2[k].getElementsByTagName("optionlabel")[0].firstChild.nodeValue;
		     value = items2[k].getElementsByTagName("optionvalue")[0].firstChild.nodeValue;
		     destination2.options[k] = new Option(label,value);
		 }
	} 
	function getTeachers() {
		var subele=document.getElementById("course");		
		var year = document.getElementById("academicYear").value;
		var subId=subele.options[subele.selectedIndex].value;
		
		var destination1 = document.getElementById("teachers");
		for (x1=destination1.options.length-1; x1>=0; x1--) {
			destination1.options[x1]=null;
		}
			getTeachersForSubjects(subId,year,updateTeachers);
			getClassesForSubjects();
	}
		function updateTeachers(req) {
			var responseObj = req.responseXML.documentElement;
			var destination1 = document.getElementById("teachers");
			
			var items1 = responseObj.getElementsByTagName("option");
			if(items1.length == 0) {
				var destination4 = document.getElementById("teachers");
				for (x1=destination4.options.length-1; x1>=0; x1--) {
					destination4.options[x1]=null;
				}
			}	
			for (var k = 0 ; k < items1.length ; k++) {
		        label = items1[k].getElementsByTagName("optionlabel")[0].firstChild.nodeValue;
			     value = items1[k].getElementsByTagName("optionvalue")[0].firstChild.nodeValue;
			     destination1.options[k] = new Option(label,value);
			 }
		} 
		function getClassesForSubjects(subId,year,updateTeachers) {
			var mode = document.getElementById("roleId").value;
			if (mode=="131") {
				var evenOrOdd = "";
				if (document.getElementById("odd").checked == true) {
					evenOrOdd = document.getElementById("odd").value;
				} else {
					evenOrOdd = "even";
				}
			var subele=document.getElementById("course");		
			var year = document.getElementById("academicYear").value;
			var userId = document.getElementById("userId").value;
			var subId=subele.options[subele.selectedIndex].value;
			var destination1 = document.getElementById("classes");
			for (x1=destination1.options.length-1; x1>=0; x1--) {
				destination1.options[x1]=null;
			}
			getClassesForSubjectsandUser(evenOrOdd,userId,subId,year,updateClassesBysub);
			}
		}
		function updateClassesBysub(req) {
			var responseObj = req.responseXML.documentElement;
			var destination1 = document.getElementById("classes");
			
			var items1 = responseObj.getElementsByTagName("option");
			if(items1.length == 0) {
				var destination4 = document.getElementById("classes");
				for (x1=destination4.options.length-1; x1>=0; x1--) {
					destination4.options[x1]=null;
				}
			}	
			for (var k = 0 ; k < items1.length ; k++) {
		        label = items1[k].getElementsByTagName("optionlabel")[0].firstChild.nodeValue;
			     value = items1[k].getElementsByTagName("optionvalue")[0].firstChild.nodeValue;
			     destination1.options[k] = new Option(label,value);
			 }
			
		}
		function getClassesForSubjectsAcademic(subId,year,updateTeachers) {
			var mode = document.getElementById("roleId").value;
			if (mode=="131") {
				var evenOrOdd = "";
				if (document.getElementById("odd").checked == true) {
					evenOrOdd = document.getElementById("odd").value;
				} else {
					evenOrOdd = "even";
				}
			var year = document.getElementById("academicYear").value;
			var userId = document.getElementById("userId").value;
			var subId="";
			var destination1 = document.getElementById("class");
			for (x1=destination1.options.length-1; x1>=0; x1--) {
				destination1.options[x1]=null;
			}
			getClassesForSubjectsandUser(evenOrOdd,userId,subId,year,updateClassesBysubAcademic);
			}
		}
		function updateClassesBysubAcademic(req) {
			var responseObj = req.responseXML.documentElement;
			var destination1 = document.getElementById("class");
			
			var items1 = responseObj.getElementsByTagName("option");
			if(items1.length == 0) {
				var destination4 = document.getElementById("class");
				for (x1=destination4.options.length-1; x1>=0; x1--) {
					destination4.options[x1]=null;
				}
			}	
			for (var k = 0 ; k < items1.length ; k++) {
		        label = items1[k].getElementsByTagName("optionlabel")[0].firstChild.nodeValue;
			     value = items1[k].getElementsByTagName("optionvalue")[0].firstChild.nodeValue;
			     destination1.options[k] = new Option(label,value);
			 }
			
		}
</script>
<html:form action="/timeTableForClass" method="post">
	<html:hidden property="method" styleId="method" value="getPeriods" />
	<html:hidden property="formName" value="timeTableForClassForm" />
	<html:hidden property="pageType" value="1" />
	<html:hidden name="timeTableForClassForm" property="attType"  styleId="atttype"/>
	<html:hidden name="timeTableForClassForm" property="roleId"  styleId="roleId"/>
	<html:hidden name="timeTableForClassForm" property="userId"  styleId="userId"/>
	<div class="panel-body">
		<div class="form-group">
			<table width="100%" border="0">
				<tr>
					<td><span class="Bredcrumbs"><bean:message
								key="knowledgepro.timetable.module" /> <span class="Bredcrumbs">&gt;&gt;
								<bean:message key="knowledgepro.timetable.for.class" />
								&gt;&gt;
						</span></span></td>
				</tr>

				<tr>
					<td><table width="100%" border="0" cellpadding="0"
							cellspacing="0">
							<tr>
								<td width="9"><img src="images/Tright_03_01.gif" width="9"
									height="29"></td>
								<td background="images/Tcenter.gif" class="heading_white"><bean:message
										key="knowledgepro.timetable.for.class" /></td>
								<td width="10"><img src="images/Tright_1_01.gif" width="9"
									height="29"></td>
							</tr>

							<%-- 
	      <logic:equal value="true" name="blink" scope="request">
	      <tr>
	
	        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
	        <td valign="top" class="news">
					<div id="info">
						Please Approve The Time Table After Correction
					</div> 
			</td>
	        <td valign="top" background="images/Tright_3_3.gif" ></td>
	      </tr>
	      </logic:equal>
	      --%>

							<tr>

								<td height="19" valign="top"
									background="images/Tright_03_03.gif"></td>
								<td valign="top" class="news">
									<div align="right">
										<FONT color="red"> <span class='MandatoryMark'><bean:message
													key="knowledgepro.mandatoryfields" /></span></FONT>
									</div>
									<div id="errorMessage">
										<FONT color="red"><html:errors /></FONT><Font color="red"><bean:write
												name="timeTableForClassForm" property="errormsg"></bean:write></Font>
										<FONT color="green"> <html:messages id="msg"
												property="messages" message="true">
												<c:out value="${msg}" escapeXml="false"></c:out>
												<br>
											</html:messages>
										</FONT>
									</div>
								</td>
								<td valign="top" background="images/Tright_3_3.gif"></td>
							</tr>
							<tr>
								<td valign="top" background="images/Tright_03_03.gif"></td>
								<td class="heading"><table width="100%" border="0"
										cellspacing="0" cellpadding="0">
										<tr>
											<td align="center"><table width="100%" border="0"
													align="center" cellpadding="0" cellspacing="0">

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
																		<div align="right">
																			<span class="Mandatory">*</span><font
																				style="font-size: 11px"><bean:message
																					key="knowledgepro.interview.Year" /></font>
																		</div>
																	</td>
																	<td class="row-even"><input type="hidden" id="yr"
																		name="yr"
																		value='<bean:write name="timeTableForClassForm" property="academicYear"/>' />
																		<html:select property="year" styleClass="combo"
																			styleId="academicYear"
																			onchange="getClassesForSubjectsAcademic();getSubjectsfor()">
																			<html:option value="">
																				<bean:message key="knowledgepro.select" />-</html:option>
																			<cms:renderAcademicYear></cms:renderAcademicYear>
																		</html:select></td>
																	<td height="25" class="row-odd">
																		<div align="right">
																			<span class="Mandatory">*</span><font
																				style="font-size: 11px">Semester</font>
																		</div>
																	</td>
																	<td class="row-even"><html:radio styleId="odd"
																			property="evenOrOdd" value='odd'
																			onclick="getClasses();getSubjectsfor()" /> Odd <html:radio
																			styleId="even" property="evenOrOdd" value='even'
																			onclick="getClasses();getSubjectsfor()" /> Even</td>
																</tr>


																<tr>
																	<td height="25" class="row-odd">
																		<div align="right">
																			<span class="Mandatory">*</span><font
																				style="font-size: 11px">Entry Type</font>
																		</div>
																	</td>
																	<td class="row-even"><input type="radio"
																		value="classWise" name="clsOrcrs" id="clsWise"
																		onclick="declareType('classWise')"> Class wise
																		<input type="radio" value="courseWise" name="clsOrcrs"
																		id="crsWise" onclick="getSubjectsfor();declareType('courseWise');">
																		Course wise</td>

																	<td height="25" class="row-odd">
																		<div id="classWisemsg">
																			<div align="right">
																				<span class="Mandatory">*</span><font
																					style="font-size: 11px"><bean:message
																						key="knowledgepro.attendance.class.col" /></font>
																			</div>
																		</div>

																		<div id="courseWisemsg">
																			<div align="right">
																				<span class="Mandatory">*</span><font
																					style="font-size: 11px"><bean:message
																						key="knowledgepro.attendance.course.col" /></font>
																			</div>
																		</div>
																	</td>
																	<td class="row-even">
																		<div id="classWise">
																			<nested:select property="classId"
																				styleClass="comboLarge" styleId="class"
																				onchange="disableDown()">
																				<html:option value="">
																					<bean:message key="knowledgepro.select" />-</html:option>
																				<logic:notEmpty name="timeTableForClassForm"
																					property="classMap">
																					<html:optionsCollection
																						name="timeTableForClassForm" property="classMap"
																						label="value" value="key" />
																				</logic:notEmpty>
																			</nested:select>
																		</div>
																		<div id="courseWise">
																			<nested:select style="width:405px"
																				property="subjectId" styleId="course"
																				styleClass="combo" onchange="getAttType();getTeachers();">
																				<html:option value="">select</html:option>
																				<html:optionsCollection name="timeTableForClassForm"
																					property="subjectMap" label="value" value="key" />
																			</nested:select>
																		</div>
																	</td>

																</tr>

																<tr>
																	<td style="height: 10px;" align="center" colspan="4">


																	</td>
																</tr>
																<tr>
																	<td width="50%" height="35" colspan="2"><div
																			align="right">
																			<html:submit value="Submit" styleClass="formbutton"></html:submit>
																			&nbsp;
																		</div></td>

																	<td width="50%" colspan="2">&nbsp; <html:button
																			property="" styleClass="formbutton" value="Reset"
																			onclick="resetFieldsAndErrors()"></html:button>
																	</td>
																</tr>

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
												</table></td>
										</tr>


									</table></td>
								<td valign="top" background="images/Tright_3_3.gif"></td>
							</tr>
							<tr>
								<td height="19" valign="top"
									background="images/Tright_03_03.gif"></td>
								<td class="heading"></td>
								<td valign="top" background="images/Tright_3_3.gif"></td>
							</tr>
							<logic:notEmpty name="timeTableForClassForm" property="classTos">
								<tr>
									<td height="19" valign="top"
										background="images/Tright_03_03.gif"></td>
									<td class="heading">
										<table width="100%" border="0" align="center" cellpadding="0"
											cellspacing="0" id="display">
											<tr>
												<td><img src="images/01.gif" width="5" height="5"></td>
												<td width="914" background="images/02.gif"></td>
												<td><img src="images/03.gif" width="5" height="5"></td>
											</tr>
											<%-- 
	          <c:if test="${timeTableForClassForm.ttClassId>0}">
	          <tr>
	
	            <td width="5"  background="images/left.gif"></td>
	            <td height="54" valign="top">
	            <table width="100%" cellspacing="1" cellpadding="2">
					<tr>
						<td width="100%" align="center" class="row-even">
						<input type="hidden"
										id="fa" name="fa"
										value='<bean:write name="timeTableForClassForm" property="finalApprove"/>' />
						Final Approve:
							<nested:radio property="finalApprove" styleId="finalApprove_1" value="on" onclick="updateTimeTable(this.value)"> yes</nested:radio>
							<nested:radio property="finalApprove" styleId="finalApprove_2" value="off" onclick="updateTimeTable(this.value)"> no</nested:radio>
						<logic:equal value="true" name="timeTableForClassForm" property="ttClassHistoryExists">
						<div align="right"><a href="#" onclick="getTimeTableHistory()">View History</a></div>
						</logic:equal>
						</td>
						
					</tr>            
	            </table>
	            
	            </td>  
	             <td  background="images/right.gif" width="5" height="54"></td>
	        <!--  <td valign="top" background="images/Tright_3_3.gif" ></td>-->
	      </tr></c:if>
	      
	      --%>


											<c:if test="${timeTableForClassForm.ttClassId>0}">
												<tr>

													<td width="5" background="images/left.gif"></td>
													<td height="54" valign="top">
														<table width="100%" cellspacing="1" cellpadding="2">
															<tr>
																<td width="100%" align="center" class="row-even"><logic:equal
																		value="true" name="timeTableForClassForm"
																		property="ttClassHistoryExists">
																		<div align="right">
																			<a href="#" onclick="getTimeTableHistory()">View
																				History</a>
																		</div>
																	</logic:equal></td>

															</tr>
														</table>

													</td>
													<td background="images/right.gif" width="5" height="54"></td>
													<!--  <td valign="top" background="images/Tright_3_3.gif" ></td>-->
												</tr>
											</c:if>



											<tr>

												<td width="5" background="images/left.gif"></td>
												<td height="54" valign="top">
													<table width="100%" cellspacing="1" cellpadding="2">
														<tr class="row-odd">
															<td>&nbsp;&nbsp;Day</td>
															<logic:notEmpty name="timeTableForClassForm"
																property="periodList">
																<logic:iterate id="bo" name="timeTableForClassForm"
																	property="periodList">
																	<td><bean:write name="bo" property="periodName" />
																		<%-- (<bean:write name="bo" property="startTime"/> - <bean:write name="bo" property="endTime"/>) --%>
																	</td>
																</logic:iterate>
															</logic:notEmpty>
														</tr>
														<logic:iterate id="to" name="timeTableForClassForm"
															property="classTos">
															<tr class="row-even" height="50px">
																<td>
																<logic:equal value="Monday" name="to" property="week">
																	&nbsp;Day I
																</logic:equal>
																<logic:equal value="Tuesday" name="to" property="week">
																	&nbsp;Day II
																</logic:equal>
																<logic:equal value="Wednesday" name="to" property="week">
																	&nbsp;Day III
																</logic:equal>
																<logic:equal value="Thursday" name="to" property="week">
																	&nbsp;Day IV
																</logic:equal>
																<logic:equal value="Friday" name="to" property="week">
																	&nbsp;DayV
																</logic:equal>
																<logic:equal value="Saturday" name="to" property="week">
																	&nbsp;Day VI
																</logic:equal>
																
																
																</td>
																<logic:notEmpty name="to" property="timeTablePeriodTos">
																	<logic:iterate id="pto" name="to"
																		property="timeTablePeriodTos">
																		<td><a href="#"
																			onclick="getPeriodDetails('<bean:write name="to" property="position" />','<bean:write name="pto" property="count" />')">
																				<bean:write name="pto" property="periodName" />
																				
																		</a></td>
																	</logic:iterate>
																</logic:notEmpty>
															</tr>
														</logic:iterate>


													</table>
												</td>

												<td background="images/right.gif" width="5" height="54"></td>
											</tr>
											<tr>
												<td width="5" background="images/left.gif"></td>
												<td height="54" valign="top">


													<table width="100%" cellspacing="1" cellpadding="3">
														<tr>
															<td>
																<table width="100%" cellspacing="0" cellpadding="0">
																	<tr>
																		<td style="height: 10px;" align="center"></td>
																	</tr>
																	<tr>
																		<td width="70%" height="35"><div align="center">
																				<html:button property="" value="Copy Period"
																					styleClass="formbutton" onclick="copy()"></html:button>
																			</div>
																	</tr>
																</table>
															</td>
														</tr>
														<tr>
															<td>
																<table width="100%" cellspacing="1" cellpadding="3">
																	<logic:equal value="true" name="timeTableForClassForm"
																		property="flag">
																		<tr>

																			<td width="12%" class="row-odd">
																				<div align="right">
																					<span class="Mandatory">*</span><font
																						style="font-size: 11px"><bean:message
																							key="knowledgepro.from.day" /></font>
																				</div>
																			</td>
																			<td width="12%" class="row-even"><nested:select
																					property="fromDay" styleId="day" styleClass="combo">
																					<html:option value="">select</html:option>
																					<logic:notEmpty name="timeTableForClassForm"
																						property="classTos">
																						<html:optionsCollection
																							name="timeTableForClassForm" property="classTos"
																							label="week" value="position" />
																					</logic:notEmpty>
																				</nested:select></td>
																			<td height="25" width="12%" class="row-odd">
																				<div align="right">
																					<span class="Mandatory">*</span><font
																						style="font-size: 11px"><bean:message
																							key="knowledgepro.from.period" /></font>
																				</div>
																			</td>
																			<td width="12%" class="row-even"><nested:select
																					property="fromPeriodId" styleId="period"
																					styleClass="combo">
																					<html:option value="">select</html:option>
																					<logic:notEmpty name="timeTableForClassForm"
																						property="periodList">
																						<html:optionsCollection
																							name="timeTableForClassForm"
																							property="periodList" label="periodName"
																							value="id" />
																					</logic:notEmpty>
																				</nested:select></td>
																			<td height="25" width="12%" class="row-odd">
																				<div align="right">
																					<span class="Mandatory">*</span><font
																						style="font-size: 11px"><bean:message
																							key="knowledgepro.to.day" /></font>
																				</div>
																			</td>
																			<td width="12%" class="row-even"><nested:select
																					property="toDay" styleId="day" styleClass="combo">
																					<html:option value="">select</html:option>
																					<logic:notEmpty name="timeTableForClassForm"
																						property="classTos">
																						<html:optionsCollection
																							name="timeTableForClassForm" property="classTos"
																							label="week" value="position" />
																					</logic:notEmpty>
																				</nested:select></td>
																			<td height="25" width="12%" class="row-odd">
																				<div align="right">
																					<span class="Mandatory">*</span><font
																						style="font-size: 11px"><bean:message
																							key="knowledgepro.to.period" /></font>
																				</div>
																			</td>
																			<td width="12%" class="row-even"><nested:select
																					property="toPeriodId" styleId="period"
																					styleClass="combo">
																					<html:option value="">select</html:option>
																					<logic:notEmpty name="timeTableForClassForm"
																						property="periodList">
																						<html:optionsCollection
																							name="timeTableForClassForm"
																							property="periodList" label="periodName"
																							value="id" />
																					</logic:notEmpty>
																				</nested:select></td>
																		</tr>
																	</logic:equal>
																</table>
															</td>
														</tr>
														<logic:equal value="true" name="timeTableForClassForm"
															property="flag">
															<tr>
																<td>
																	<table width="100%" border="0" cellspacing="0"
																		cellpadding="0">
																		<tr>
																			<td style="height: 10px;" align="center"></td>
																		</tr>
																		<tr>
																			<td width="70%" height="35"><div align="center">
																					<html:button property="" value="Submit"
																						styleClass="formbutton" onclick="copyPeriods()"
																						styleId="copyPeriod_1"></html:button>
																				</div></td>
																		</tr>
																	</table>
																</td>
															</tr>
														</logic:equal>
													</table>
												</td>
												<td background="images/right.gif" width="5" height="54"></td>
											</tr>


											<tr>
												<td height="5"><img src="images/04.gif" width="5"
													height="5"></td>
												<td background="images/05.gif"></td>
												<td><img src="images/06.gif"></td>
											</tr>

										</table>
									</td>
									<td valign="top" background="images/Tright_3_3.gif"></td>
								</tr>
							</logic:notEmpty>



							<tr>

								<td height="19" valign="top"
									background="images/Tright_03_03.gif"></td>
								<td valign="top">
								
								<logic:equal value="t" name="timeTableForClassForm" property="toggler">
								<div id="crsWiseEntry">
										<table width="100%">


											<tr>
												<td height="25" class="row-odd" width="20%">
													<div align="right">
														<span class="Mandatory">*</span><font
															style="font-size: 11px">Teachers</font>
													</div>
												</td>
												<td class="row-even" width="30%"><html:select
														name="timeTableForClassForm" size="5" styleId="teachers"
														property="teachers" style="width: 300px;" multiple="multiple"
														styleClass="form-control">
														<html:optionsCollection name="timeTableForClassForm"
															property="teachersMap" label="value" value="key" />
													</html:select></td>
													<td height="25" class="row-odd">
													<div align="right">
														<span class="Mandatory">*</span><font
															style="font-size: 11px">Class</font>
													</div>
												</td>
												<td class="row-even"><html:select
														name="timeTableForClassForm" styleId="classes"
														property="classes" size="5" multiple="multiple"
														styleClass="form-control" onclick="">
														<html:optionsCollection name="timeTableForClassForm"
															property="classMap" label="value" value="key" />
													</html:select></td>
												

											</tr>
											
											<tr>
												
												<td height="25" class="row-odd" width="20%">
													<div align="right">
														<span class="Mandatory">*</span><font
															style="font-size: 11px">Weeks</font>
													</div>
												</td>
												<td class="row-even" width="30%"><html:select
														name="timeTableForClassForm" styleId="weeks"
														property="weeks" size="5" multiple="multiple"
														styleClass="form-control" style="  width: 200px;">
														<html:option value="1">Day I</html:option>
														<html:option value="2">Day II</html:option>
														<html:option value="3">Day III</html:option>
														<html:option value="4">Day IV</html:option>
														<html:option value="5">Day V</html:option>
														<html:option value="6">Day VI </html:option>
													</html:select></td>
												<td height="25" class="row-odd">
													<div align="right">
														<span class="Mandatory">*</span><font
															style="font-size: 11px">Periods</font>
													</div>
												</td>
												<td class="row-even"><html:select
														name="timeTableForClassForm" styleId="periods"
														property="periods" size="5" multiple="multiple"
														styleClass="form-control" style="  width: 200px;">
														<%-- <logic:notEmpty name="timeTableForClassForm"
																					property="periodMap">
														<html:optionsCollection name="timeTableForClassForm" property="periodMap" label="value" value="key"/>
														</logic:notEmpty> --%>
														<html:option value="1">Hour 1</html:option>
														<html:option value="2">Hour 2</html:option>
														<html:option value="3">Hour 3</html:option>
														<html:option value="4">Hour 4</html:option>
														<html:option value="5">Hour 5</html:option>
													</html:select></td>

											</tr>
											
											<tr>
												<td colspan="4" class="row-even" width="30%" align="center"><input type="button"
													value="submit" class="btnc" onclick="enterCourseWise()">
													<input type="button"
													value="Reset" class="btnc" onclick="resetFields()">
												</td>
											</tr>

										</table>
										</div>
									</logic:equal></td>
								<td valign="top" background="images/Tright_3_3.gif"></td>
							</tr>


							<tr>
								<td height="19" valign="top"
									background="images/Tright_03_03.gif"></td>
								<td class="heading"><table width="100%" border="0"
										cellspacing="0" cellpadding="0">
									</table></td>
								<td valign="top" background="images/Tright_3_3.gif"></td>
							</tr>
							<tr>
								<td><img src="images/Tright_03_05.gif" width="9"
									height="29"></td>
								<td width="0" background="images/TcenterD.gif"></td>
								<td><img src="images/Tright_02.gif" width="9" height="29"></td>
							</tr>
						</table></td>
				</tr>

			</table>
			<script>
				if ('<bean:write name="timeTableForClassForm" property="clsOrcrs"/>' != ''
						&& '<bean:write name="timeTableForClassForm" property="clsOrcrs"/>' == 'courseWise') {
					document.getElementById("clsWise").checked = false;
					document.getElementById("crsWise").checked = true;
				} else {
					document.getElementById("clsWise").checked = true;
					document.getElementById("crsWise").checked = false;
				}
			</script>
		</div>
		</div>
</html:form>
<script type="text/javascript">
	var yearId = document.getElementById("yr").value;
	if (yearId != null && yearId.length != 0) {
		document.getElementById("academicYear").value = yearId;
	}
	if (document.getElementById("fa") != null) {
		var fa = document.getElementById("fa").value;
		if (fa != null && fa.length != 0 && fa == 'on') {
			document.getElementById("finalApprove_2").disabled = true;
		}
	}
	if (document.getElementById("info") != null) {
		$("#finalApprove_1").click(function() {
			$("#info").animate({
				left : "+=10px"
			}).animate({
				left : "-5000px"
			});
		});
		function blink() {
			$("#info").fadeOut(800).fadeIn(800).fadeOut(400).fadeIn(400)
					.fadeOut(400).fadeIn(400);
		}
		blink();
	}
	if (document.getElementById("info") != null) {
		$("#copyPeriod_1").click(function() {
			$("#info").animate({
				left : "+=10px"
			}).animate({
				left : "-5000px"
			});
		});
		function blink() {
			$("#info").fadeOut(800).fadeIn(800).fadeOut(400).fadeIn(400)
					.fadeOut(400).fadeIn(400);
		}
		blink();
	}

	if (document.getElementById("clsWise").checked) {
		document.getElementById("courseWise").style.display = "none";
		document.getElementById("courseWisemsg").style.display = "none";
		document.getElementById("classWise").style.display = "block";
		document.getElementById("classWisemsg").style.display = "block";
	}
	if (document.getElementById("crsWise").checked) {
		document.getElementById("courseWise").style.display = "block";
		document.getElementById("classWise").style.display = "none";
		document.getElementById("courseWisemsg").style.display = "block";
		document.getElementById("classWisemsg").style.display = "none";
	}
</script>
