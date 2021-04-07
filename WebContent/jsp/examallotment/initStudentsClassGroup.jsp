<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
   <script type="text/javascript" src="js/auditorium/jquery-1.9.1.min.js"></script>
<link rel="stylesheet" type="text/css" href="jquery.confirm/jquery.confirm/jquery.confirm.css" />
    <script src="jquery.confirm/jquery.confirm/jquery.confirm.js"></script>
<script type="text/javascript">
	function moveoutid()
	{
		var sda = document.getElementById('classValues');
		var len = sda.length;
		var sda1 = document.getElementById('unSelectesClasses');
		if(sda1.length == 0) {
			document.getElementById("moveOut").disabled = false;
		}
		for(var j=0; j<len; j++)
		{
			if(sda[j].selected)
			{
				var tmp = sda.options[j].text;
				var tmp1 = sda.options[j].value;
				sda.remove(j);
				len--;
				j--;
				if(j<0){
					document.getElementById("moveIn").disabled = true;
					document.getElementById("moveOut").disabled = false;
				}
				if(sda.length <= 0)
					document.getElementById("moveIn").disabled = true;
				else
					document.getElementById("moveIn").disabled = false;
				var y=document.createElement('option');
				y.text=tmp;
				y.value = tmp1;
				y.setAttribute("class","comboBig");
				try
				{
					sda1.add(y,null);
				}
				catch(ex)
				{
					sda1.add(y);
				}
			}
		}
	}
	function moveinid()
	{
		var sda = document.getElementById('classValues');
		var sda1 = document.getElementById('unSelectesClasses');
		var len = sda1.length;
		  var array = new Array();
		for(var j=0; j<len; j++)
		{
			if(sda1[j].selected)
			{
				var tmp = sda1.options[j].text;
				var tmp1 = sda1.options[j].value;
				array[j]=tmp1;
				sda1.remove(j);
				len--;
				j--;
				if(j<0){
					document.getElementById("moveOut").disabled = true;
					document.getElementById("moveIn").disabled = false;
				}
				if(sda1.length != 0) {
					document.getElementById("moveIn").disabled = false;
					document.getElementById("moveOut").disabled = false;
				}
				else
					document.getElementById("moveIn").disabled = false;
				var y=document.createElement('option');
				y.setAttribute("class","comboBig");
				y.text=tmp;
				y.value = tmp1;
				try
				{
				sda.add(y,null);
				}
				catch(ex){
				sda.add(y);	
				}
			}
		}	
	}
	function updateClassMap(req){
		updateOptionsFromMapMultiselect(req, "unSelectesClasses", "- Select -");
	}
	function getClassValues(){
		var listClasses=new Array(); 
			var mapTo1 = document.getElementById('classValues');
			var len1 = mapTo1.length;
			for(var k=0; k<len1; k++)
			{
				listClasses.push(mapTo1[k].value);
		}
		document.getElementById("selectedClasses").value=listClasses;
	}

function cancelStudentClassGroup(){
	document.location.href = "LoginAction.do?method=loginAction";
}
function getClassesByYearAndLocationAndScheme(){
	var year=document.getElementById("academicYear").value;
	var campusName=document.getElementById("campusName").value;
	var schemeNo=document.getElementById("schemeNo").value;
	if(year!=""){
		if(schemeNo!=""){
			document.location.href = "StudentsClassGroup.do?method=getClassesByYearAndLocation&academicYear="+year+"&campusName="+campusName+"&schemeNo="+schemeNo;
		}else{
			document.location.href = "StudentsClassGroup.do?method=getClassesByYearAndLocation&academicYear="+year+"&campusName="+campusName;
			}
	}
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
function cancelPage(){
	document.location.href="StudentsClassGroup.do?method=initStudentsClassGroup";
}
function addClassGroupDetails(){
	document.getElementById("method").value = "addClassGroup";
	document.studentsClassGroupForm.submit();
}
function editStudentClassGroup(groupId){
	document.location.href="StudentsClassGroup.do?method=editStudentsClassGroup&groupId="+groupId;
}
function updateGroupClassWithStudents(){
	getClassValues();
	document.getElementById("method").value = "updateGroupClassWithStudents";
	document.studentsClassGroupForm.submit();
}
function deleteStudentClassGroup(groupId){
	 $.confirm({
			'message'	: 'Are you sure you want to delete this Group?',
			'buttons'	: {
				'Ok'	: {
					'class'	: 'blue',
					'action': function(){
						$.confirm.hide();
						document.location.href="StudentsClassGroup.do?method=deleteStudentsClassGroup&groupId="+groupId;
					}
				},
     'Cancel'	:  {
					'class'	: 'gray',
					'action': function(){
						$.confirm.hide();
					}
				}
			}
		});
}
function getGroupClassByYear(year){
	var campusName=document.getElementById("campusName").value;
	var schemeNo=document.getElementById("schemeNo").value;
	if(campusName!="" && schemeNo!=""){
		document.location.href = "StudentsClassGroup.do?method=getClassesByYearAndLocation&academicYear="+year+"&campusName="+campusName+"&schemeNo="+schemeNo;
	}else{
		document.location.href = "StudentsClassGroup.do?method=getClassGroupDetailsByYear&academicYear="+year;
		}
}
</script>

<html:form action="/StudentsClassGroup" method="post">
	<html:hidden property="formName" value="studentsClassGroupForm" />
	<html:hidden property="pageType" value="1"/>
    <html:hidden property="selectedClasses" styleId="selectedClasses"/>	
    <html:hidden property="method" styleId="method" value="getStudentDetailsByClasses" />
	<table width="100%" border="0">
		<tr>
			<td><span class="Bredcrumbs"> <bean:message
				key="knowledgepro.exam.allotment" /> <span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.exam.allotment.class.group.student" /> &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> <bean:message
						key="knowledgepro.exam.allotment.class.group.student" /></strong></td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="452" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" cellspacing="1" cellpadding="2">
						<tr bgcolor="#FFFFFF">
							<td height="20" colspan="4">
							<div align="right"><FONT color="red"> <span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields"/></span></FONT></div>
							<font color="red" size="2"><div id="errorMessage"><FONT color="red"><html:errors /></FONT>
							<FONT color="green"> <html:messages id="msg"
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
									<td valign="top">
									<table width="100%" cellspacing="1" cellpadding="2">
						<tr>
						<% boolean disable=false; %>
						<logic:equal value="true" property="disable" name="studentsClassGroupForm">
						<%disable=true ; %>
						</logic:equal>
						    <td width="15%" height="25" class="row-odd">
									<div align="right"><span class="Mandatory">* </span> <bean:message
										key="knowledgepro.admin.year" /></div>
									</td>
									<td width="20%" height="25" class="row-even">
									<input type="hidden" id="tempAcademicYear" name="tempAcademicYear" value="<bean:write name="studentsClassGroupForm" property="academicYear"/>"/>
									<html:select
										property="academicYear" styleClass="combo"
										styleId="academicYear" name="studentsClassGroupForm" onchange="getGroupClassByYear(this.value)" disabled="<%=disable %>">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<cms:renderAcademicYearForAdmission></cms:renderAcademicYearForAdmission>
									</html:select>
									</td>
							<td width="15%" height="25" class="row-odd">
                            <div align="right"><span class="Mandatory">*</span><bean:message
								key="knowledgepro.ExamAllotment.Students.Class.group.Campus" />:</div>
							</td>
							<td width="50%" height="25" class="row-even">
                            <div align="left">
                            <html:select property="campusName" name="studentsClassGroupForm"  styleId="campusName" styleClass="combo" onchange="getClassesByYearAndLocationAndScheme()" disabled="<%=disable %>" >
							<html:option value="">--Select--</html:option>
							<c:if test="${studentsClassGroupForm.locationMap != null}">
							<html:optionsCollection name="studentsClassGroupForm" property="locationMap" label="value" value="key" />
							</c:if>
						    </html:select>
                            </div>
							</td>		
						</tr>
						<tr>
						 <td width="15%" height="25" class="row-odd">
                            <div align="right"><span class="Mandatory">*</span><bean:message
								key="knowledgepro.fee.semister" />:</div>
							</td>
							<td width="20%" height="25" class="row-even">
                            <div align="left">
                            <html:select property="schemeNo" name="studentsClassGroupForm" styleId="schemeNo" styleClass="combo"  onchange="getClassesByYearAndLocationAndScheme()" disabled="<%=disable %>">
                            <html:option value="">--Select--</html:option>
                            <html:option value="1">1</html:option>
                            <html:option value="2">2</html:option>
                            <html:option value="3">3</html:option>
                            <html:option value="4">4</html:option>
                            <html:option value="5">5</html:option>
                            <html:option value="6">6</html:option>
                            <html:option value="7">7</html:option>
                            <html:option value="8">8</html:option>
                            <html:option value="9">9</html:option>
                            <html:option value="10">10</html:option>
                            </html:select>
                            </div>
							</td>
							<td width="15%" height="25" class="row-odd">
                            <div align="right"><span class="Mandatory">*</span><bean:message
								key="knowledgepro.admin.class.entry.report" />:</div>
							</td>
							<td width="50%" class="row-even" align="left">
							<table border="0"><tr>
						    <td width="112" height="15">
						    <nested:select property="unSelectesClasses" name="studentsClassGroupForm"  styleId="unSelectesClasses"  multiple="multiple" size="8" style="width:200px" styleClass="body">
						    <c:if test="${studentsClassGroupForm.classMap != null}">
						    <nested:optionsCollection property="classMap" label="value" value="key" styleClass="comboBig" name="studentsClassGroupForm"/>
						    </c:if>
						    </nested:select>
						    </td>
						    <td width="49">
						    <table border="0">
						    <tr><td>
						    <input type="button" align="right" value="&gt&gt;" id="moveOut" onclick="moveinid()"/>
						    </td></tr><tr>
						    <td>
						    <input type="button" align="right" value="&lt&lt;" id="moveIn" onclick="moveoutid()" />
						    </td>
						    </tr>
						    </table>
						    </td>
						    <td width="120" height="15">
					<nested:select property="classValues" name="studentsClassGroupForm" styleId="classValues"  multiple="multiple" size="8" style="width:200px" styleClass="body">
							<c:if test="${studentsClassGroupForm.selectedClassMap != null}">
							<nested:optionsCollection property="selectedClassMap" label="value" value="key" styleClass="comboBig" name="studentsClassGroupForm"/>
					            </c:if>	
						</nested:select>
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
									<td height="5"><img src="images/04.gif" width="5" height="5" /></td>
									<td background="images/05.gif"></td>
									<td><img src="images/06.gif" /></td>
								</tr>
							</table>
							</td>
							<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
						</tr>
						<tr>
							<td height="25" colspan="4">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td width="45%" height="35">
									<div align="right">
											<html:submit property="" styleClass="formbutton"
												value="Submit"  styleId="Submit" onclick="getClassValues()"></html:submit>
									</div>
									</td>
									<!--<logic:equal value="false" property="flag" name="studentsClassGroupForm">
									<td width="2%"></td>
									<td width="2%">
							              <html:button property="" styleClass="formbutton"
												value="Reset" onclick="resetRoomPoolWise()" styleId="reset"></html:button>
										</td></logic:equal>-->
										<td width="2%"></td>
									<td width="53%">
							              <html:button property="" styleClass="formbutton"
												value="Cancel" onclick="cancelStudentClassGroup()" styleId="cancel"></html:button>
										</td>	
								</tr>
							</table>
							</td>
						</tr>
						<logic:notEmpty property="classGroupToList" name="studentsClassGroupForm">
						<tr>
						<td valign="top" class="news">
						<div><FONT color="red" size="1">
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
												<td width="5%" class="row-odd">
												<div align="center"><bean:message
													key="knowledgepro.attendanceentry.regno" /></div>
												</td>
												<td width="15%" class="row-odd">
												<div align="center"><bean:message
													key="knowledgepro.attendance.studentName" /></div>
												</td>
												<td width="15%" class="row-odd">
												<div align="center">Class </div>
												</td>
												<td width="5%" class="row-odd">
												SelectAll<input type="checkbox" id="checkAll" onclick="selectAll(this)"/> 
												
												</td>
											</tr>
											<tr>
											<nested:iterate id="studentList" property="classGroupToList"
												name="studentsClassGroupForm" indexId="count">
												
												<!-- <tr>-->
													<c:if test="${count < studentsClassGroupForm.halfLength}">
													 <c:choose>
														<c:when test="${count%2 == 0}">
															<tr class="row-even">
														</c:when>
														<c:otherwise>
															<tr class="row-white">
														</c:otherwise>
					 								</c:choose>
													
													<td class="row-even">
														<div align="left"><bean:write property="registerNo"
															name="studentList" /></div>
														</td>
														<td class="row-even">
														<div align="left"><bean:write property="studentName"
															name="studentList" /></div>
														</td>
														<td class="row-even">
														<div align="left"><bean:write property="studentClass"
															name="studentList" /></div>
														</td>
														<td class="row-even">
														<input type="hidden" name="classGroupToList[<c:out value='${count}'/>].tempChecked"	id="hidden_<c:out value='${count}'/>"
																value="<nested:write name='studentList' property='tempChecked'/>" />
														
														<input type="checkbox" name="classGroupToList[<c:out value='${count}'/>].checked" id="<c:out value='${count}'/>"  onclick="unCheckSelectAll()" />
																<script type="text/javascript">
																var studentId = document.getElementById("hidden_<c:out value='${count}'/>").value;
																if(studentId == "on") {
																document.getElementById("<c:out value='${count}'/>").checked = true;
																		}		
																</script>
																</td></c:if></nested:iterate>
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
								<td valign="top" background="images/Tright_03_03.gif"></td>
								<td valign="top">
								<table width="100%" border="0" align="left" cellpadding="0"
									cellspacing="0">
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
												<td width="5%" class="row-odd">
														<div align="center"><bean:message
															key="knowledgepro.attendanceentry.regno" /></div>
														</td>
														<td width="15%" class="row-odd">
														<div align="center"><bean:message
															key="knowledgepro.attendance.studentName" /></div>
														</td>
														<td width="15%" class="row-odd">
														<div align="center">Class</div>
														</td>
														 <td width="5%" class="row-odd">
															Select
														</td>
													</tr>
												
										<c:set var="c" value="0"/>
											<nested:iterate id="studentList" property="classGroupToList"
														name="studentsClassGroupForm" indexId="count">		
												<c:set var="c" value="${c + 1}"/>											
											    
													<c:if test="${count >= studentsClassGroupForm.halfLength}">
													    <c:choose>
														<c:when test="${count%2 == 0}">
															<tr class="row-even">
															</c:when>
														<c:otherwise>
															<tr class="row-white">
															</c:otherwise>
					 										</c:choose>
																<td class="row-even">
																<div align="left"><bean:write
																property="registerNo" name="studentList" /></div>
																</td>
																<td class="row-even">
																<div align="left"><bean:write
																	property="studentName" name="studentList" /></div>
																</td>
																<td class="row-even">
																<div align="left"><bean:write property="studentClass"
																	name="studentList" /></div>
																</td>
																<td class="row-even">
																<input type="hidden" name="classGroupToList[<c:out value='${count}'/>].tempChecked"	id="hidden_<c:out value='${count}'/>"
																value="<nested:write name='studentList' property='tempChecked'/>" />
																<input type="checkbox" name="classGroupToList[<c:out value='${count}'/>].checked" id="<c:out value='${count}'/>" onclick="unCheckSelectAll()" />
																<script type="text/javascript">
																
																var studentId = document.getElementById("hidden_<c:out value='${count}'/>").value;
																if(studentId == "on") {
																document.getElementById("<c:out value='${count}'/>").checked = true;
																		}		
																</script>	
																</td>
															</c:if>
													</nested:iterate>
												</table>
												</td>
												<td width="5" background="images/right.gif"></td>
											</tr>
											<tr>
												<td height="5"><img src="images/04.gif" width="5"
													height="5" /></td>
												<td background="images/05.gif"></td>
												 <td> <img src="images/06.gif" /></td>
											</tr>
										</table>
										</td>
									</tr>
									</table>
								</td>
							</tr>
						</table>
						</td><td valign="top" background="images/Tright_3_3.gif"></td>
						</tr>
						</logic:notEmpty>
						<logic:equal value="true" property="flag" name="studentsClassGroupForm">
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
									    <td width="30%" class="row-even"> </td>
										<td width="20%" height="25" class="row-odd">
										<div align="right"><span class="Mandatory">*</span><bean:message
											key="knowledgepro.ExamAllotment.Students.Class.group" /></div>
										</td>
										<td width="50%" class="row-even">
										<% boolean disable1=false;%>
										<logic:equal value="true" name="studentsClassGroupForm" property="disable2">
										<% disable1=true;%>
										</logic:equal>
										<input type="hidden"
											name="s1" id="s1"
											value='<bean:write name="studentsClassGroupForm" property="classGroup"/>' />
										     <html:select property="classGroup" name="studentsClassGroupForm"  styleId="classGroup" styleClass="combo" disabled="<%=disable1 %>">
							<html:option value="">--Select--</html:option>
							<c:if test="${studentsClassGroupForm.classGroupMap != null}">
							<html:optionsCollection name="studentsClassGroupForm" property="classGroupMap" label="value" value="key" />
							</c:if>
						    </html:select></td>
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
						</table>
						</td>
						<td valign="top" background="images/Tright_3_3.gif"></td>
					</tr>
					<tr>
						<td class="heading">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							
							<tr>
							<td width="45%" height="35">
							<div align="right">
							<c:choose>
								<c:when test="${studentsClassGroupOperation == 'edit'}">
									<html:button property="" styleClass="formbutton" value="Update"
										onclick="updateGroupClassWithStudents()"></html:button>
								</c:when>
								<c:otherwise>
									<html:button property="" styleClass="formbutton" value="Submit"
										onclick="addClassGroupDetails()"></html:button>
								</c:otherwise>
							</c:choose>
								</div>
							</td>
							<td width="2%"></td>
							<td width="53">
									<html:button	property="" styleClass="formbutton"	onclick="cancelPage()">
									<bean:message key="knowledgepro.close" />
								</html:button>
							</td>
							</tr>
						
						</table>
						
						</td>
						<td valign="top" background="images/Tright_3_3.gif"></td>
					</tr>
					</logic:equal>	
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
							<td height="25" colspan="4">
							<table width="100%" cellspacing="1" cellpadding="2">
								<tr>
									<td width="10%" height="25" class="row-odd" align="center">
									<div align="center"><bean:message key="knowledgepro.slno" /></div>
									</td>
									<td width="30%" height="25" class="row-odd" align="center"><bean:message
										key="knowledgepro.ExamAllotment.Students.Class.group" /></td>
									<td width="30%" class="row-odd">
									<div align="center">Edit</div>
									</td>
									<td width="30%" class="row-odd">
									<div align="center">Delete</div>
									</td>
								</tr>
								<logic:notEmpty property="groupClassesList" name="studentsClassGroupForm">
								<logic:iterate id="groupList" property="groupClassesList" name="studentsClassGroupForm" indexId="count">
								<c:choose>
										<c:when test="${count%2 == 0}">
											<tr class="row-even">
										</c:when>
										<c:otherwise>
											<tr class="row-white">
										</c:otherwise>
									</c:choose>
								<td width="10%" height="25" align="center">
									<div align="center"><c:out value="${count+1}" /></div>
									</td>
									<td width="30%" height="25" align="center"><bean:write
										name="groupList" property="groupName"/> </td>
									<td width="30%" height="25" align="center">
									<div align="center"><img src="images/edit_icon.gif"
										width="16" height="18"  style="cursor:pointer" 
										onclick="editStudentClassGroup('<bean:write name="groupList" property="groupId"/>')">
									</div>
									</td>
									<td width="30%" height="25" align="center">
									<div align="center"><img src="images/delete_icon.gif"
										width="16" height="18"  style="cursor:pointer" 
										onclick="deleteStudentClassGroup('<bean:write name="groupList" property="groupId"/>')">
									</div>
									</td>
								</logic:iterate>
								</logic:notEmpty>
							</table>
							</td>
						</tr>
						</table>
							</td>
							<td width="5" height="29" background="images/right.gif"></td>
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
var year = document.getElementById("tempAcademicYear").value;
if(year.length != 0) {
 	document.getElementById("academicYear").value=year;
}
</script>