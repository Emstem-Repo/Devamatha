<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>

<html:html>
<head>
<title><bean:message key="knowledgepro.title"/> </title>
<script language="JavaScript" src="js/calendar_us.js"></script>
<script language="JavaScript" src="js/admission/interviewprocess.js"></script>
<link href="css/styles.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="css/calendar.css">

<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 5px;
	margin-right: 0px;
	margin-bottom: 0px;
}
-->
</style>
</head>
<script type="text/javascript">
function getPrograms(programTypeId) {
	getProgramsByType("programMap",programTypeId,"program",updatePrograms);
}

function updatePrograms(req) {
	updateOptionsFromMap(req,"program","- Select -");
}

function getCourses(programId) {
	getCoursesByProgram("coursesMap",programId,"course",updateCourses);
	
}

function updateCourses(req) {
	updateOptionsFromMap(req,"course","- Select -");
}


function resetFeeReport()	{
	
	document.getElementById("programtype").selectedIndex = 0;
	resetOption("program");
	resetOption("course");
	
	resetErrMsgs();
}



function getStudentsDetails(){
	document.getElementById("method").value = "getStudentsChallanStatusOnCourseForExam";
	document.dDStatusForm.submit();
}


function cancelPage(){
	document.location.href="ddStatus.do?method=initChallanStatusOnCourseForExam";
}



function updateChallanStatusOnCourse() {
	document.getElementById("method").value = "updateChallanStatusOnCourseForExam";
	document.dDStatusForm.submit();

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
    var ij=0;
    for(var count1 = 0;count1<inputs.length;count1++) {
          inputObj = inputs[count1];
          var type = inputObj.getAttribute("type");
            if (type == 'checkbox' && inputObj.id != "checkAll") {
                  checkBoxOthersCount++;
                  if(inputObj.checked) {
                        checkBoxOthersSelectedCount++;
                        
                        document.getElementById(ij).checked = true;
                        document.getElementById(ij).value="on";
                        document.getElementById('hidden1_'+ij).value="on";
                  }else{
                	  
                	  document.getElementById(ij).checked = false;
                	  document.getElementById(ij).value="off";
                	  document.getElementById('hidden1_'+ij).value="off";
                  } 
                 // alert(document.getElementById(ij).value+'----------'+document.getElementById(ij).checked);
                  ij++;    
            }
           
    }
    if(checkBoxOthersCount != checkBoxOthersSelectedCount) {
      document.getElementById("checkAll").checked = false;
    } else {
      document.getElementById("checkAll").checked = true;
    }        
}
function getClassesMap(){
	var examName= document.getElementById("examId").value;
		getClassCodeByExamName("classMap",examName,"classes", updateClasses);
	}
function updateClasses(req){
	updateOptionsFromMap(req, "classes", "- Select -");
	}
	
function getExamName() {
	if(document.getElementById("examType").checked==true)
		var examType=document.getElementById("examType").value;
		else if(document.getElementById("sup").checked==true)
			var examType=document.getElementById("sup").value;
		
	var year=document.getElementById("academicYear").value;
	getExamNameByExamTypeAndYear("examMap", examType,year, "examId", updateExamName);
//	getExamNameByExamType("examMap", examType, "examId", updateExamName);

}
function updateExamName(req) {
	updateOptionsFromMap(req, "examId", "- Select -");
	updateCurrentExam(req, "examId");
}


</script>

<body>
<html:form action="ddStatus" method="post">
<html:hidden property="method" styleId="method" value="initChallanStatusOnCourse"/>
<html:hidden property="formName" value="dDStatusForm" />
<html:hidden property="pageType" value="4"/>




<table width="100%" border="0">
		<tr>
			<td><span class="heading"> <bean:message
				key="knowledgepro.admin" /> <span class="Bredcrumbs">&gt;&gt;
			Challan Status On Student Wise For Exam &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> Challan Status On Student Wise For Exam</strong></td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="122" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" cellspacing="1" cellpadding="2">
						<tr bgcolor="#FFFFFF">
							<td height="20" colspan="4">
							<div align="right"><FONT color="red"> <span
								class='MandatoryMark'><bean:message
								key="knowledgepro.mandatoryfields" /></span></FONT></div>
							<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
							<FONT color="green"> <html:messages id="msg"
								property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
							</html:messages> </FONT>
							</div>
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
			 
            <td width="5"  background="images/left.gif"></td>
            <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
              <tr >
              
               <td height="25" class="row-odd" ><div align="right"><span class="Mandatory">*</span><bean:message key="admissionForm.studentedit.admyear.label" /></div></td>
				<td height="25" class="row-even" >
					<input type="hidden" id="tempyear" name="tempyear" value="<bean:write name="dDStatusForm" property="academicYear"/>"/>
                 <html:select property="academicYear" styleId="academicYear" styleClass="combo" onchange="getExamName()" >
  	   				 <html:option value="">- Select -</html:option>
  	   				<cms:renderAcademicYear></cms:renderAcademicYear>
   			   </html:select>
				</td>
				<td height="25" colspan="2" class="row-even">
									<div align="Center">
									<html:radio property="examType"
										styleId="examType" value="Regular"
										onclick="getExamName()" ></html:radio>
									Regular&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<html:radio property="examType" value="Supplementary"
										styleId="sup"
										onclick="getExamName()"  ></html:radio>
									Supplementary									
									</div>
									</td>
                
											
                </tr>
              <tr >
              <td height="25" class="row-odd" width="25%">
												<div align="right">
													<DIV align="right"><span class="Mandatory">*</span>Exam
													Name :</DIV>
													</div>
											</td>
											
											<td height="25" class="row-even" width="14%"><html:select
											property="examid" styleClass="body" styleId="examId" onchange="getClassesMap()" style="width:200px">
											<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
											<logic:notEmpty name="dDStatusForm" property="examMap">
												<html:optionsCollection property="examMap" name="dDStatusForm" label="value" value="key" />
											</logic:notEmpty>
											</html:select></td>
              <td width="25%" height="25" class="row-odd" ><div id ="classes1" align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.attendanceentry.class" /> </div></td>
                             <td width="25%" height="25" class="row-even" align="left" >
                              <html:select
										property="classes" styleClass="combo"
										styleId="classes" name="dDStatusForm">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<c:choose>
											<c:when test="${classMap != null}">
												<html:optionsCollection property="classMap" label="value"
													value="key" />
											</c:when>
										</c:choose>
									</html:select></td>
              
											<!--<div align="right"><span class="Mandatory">*</span>&nbsp;Date:</div>
											-->
											
												<!--<html:text name="dDStatusForm" property="recievedChallanDate" styleId="recievedChallanDate" size="10" maxlength="16"/>
												<script
												language="JavaScript">
												new tcal( {
													// form name
													'formname' :'dDStatusForm',
													// input name
													'controlname' :'recievedChallanDate'
												});
											</script>
											-->
                
              </tr>

										<tr bordercolor="">

											<td valign="top" colspan="2">
											<table width="100%" cellspacing="1" cellpadding="2">
												<tr>

													<td height="25" colspan="1" class="row-odd" align="right">
													Is Revaluation ?:</td>


													<td height="25" colspan="1" class="row-even">
													<div align="Center"><html:radio
														property="flagType" styleId="isRevaluation"
														value="isRevaluation">Yes</html:radio>

											</div>
													</td>
												</tr>
												
														<tr>

													<td height="25" colspan="1" class="row-odd" align="right">
													Is Scrutiny ?:</td>


													<td height="25" colspan="1" class="row-even">
													<div align="Center"><html:radio
														property="flagType" styleId="isScrutiny"
														value="isScrutiny">Yes</html:radio>

													
											</div>
													</td>
												</tr>
												
														<tr>

													<td height="25" colspan="1" class="row-odd" align="right">
													Is Challenge Valuation ?:</td>


													<td height="25" colspan="1" class="row-even">
													<div align="Center"><html:radio
														property="flagType" styleId="isChallengeValuation"
														value="isChallengeValuation">Yes</html:radio>
													
										</div>
													</td>
												</tr>


											</table>
											</td>
										</tr>



									</table></td>
            <td width="5" height="30"  background="images/right.gif"></td>
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
							<td height="25" colspan="4">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td  height="35">
									<div align="center">
									<html:submit styleClass="formbutton" value="Continue" onclick="getStudentsDetails()" ></html:submit>
									</div>
									</td>
									
								</tr>
							</table>
							</td>
						</tr>
						
						
						
						
						
						
						
						
						
						
						
						<tr>
						
						</tr>
						<logic:notEmpty name="dDStatusForm" property="ddStatusList">
						<tr>
							<td valign="top" class="news">
							
							<table width="100%" cellspacing="1" cellpadding="2">
										<tr>
										<!--<td align="center" class="row-odd">SI.No</td>
										<td align="center" class="row-odd">Application No</td>
										<td align="center" class="row-odd">Course</td>
										
										-->
										<td align="center" class="row-odd">Student Name</td>
										<td align="center" class="row-odd">Category</td>
										<td align="center" class="row-odd">Journal No</td>
										<td align="center" class="row-odd"><input type="checkbox" id="checkAll" onclick="selectAll(this)"/> Select</td>
										
										</tr>
											<nested:iterate id="to" property="ddStatusList"	name="dDStatusForm" indexId="count">
												<tr>
													<!--<td align="center" class="row-even">
														<c:out value="${count + 1}" />
													</td>
													<td align="center" class="row-even">
														<nested:write property="applnNo" />
													</td>
													
													<td align="center" class="row-even">
							                              <nested:write property="courseName" />  	
							                        </td>
													-->
													
							                        
													<td align="center" class="row-even">
														<nested:write property="studentName" />
													</td>
													<td align="center" class="row-even">
														<nested:write property="casteName" />
													</td>
													
													<td align="center" class="row-even">
							                              <nested:write property="enterdChallanNo" />  	
							                        </td>
							                        
													<td align="center" class="row-even">
							                                	
							                                 <input type="hidden" name="ddStatusList[<c:out value='${count}'/>].tempChecked1"	id="hidden1_<c:out value='${count}'/>"
																value="<nested:write property='tempChecked1'/>" />
																	
							                                <input type="hidden" name="ddStatusList[<c:out value='${count}'/>].tempChecked"	id="hidden_<c:out value='${count}'/>"
																value="<nested:write property='tempChecked'/>" />
																
															<input type="checkbox" name="ddStatusList[<c:out value='${count}'/>].checked" id="<c:out value='${count}'/>"  onclick="unCheckSelectAll()" />
														<script type="text/javascript">

																var studentId1 = document.getElementById("hidden1_<c:out value='${count}'/>").value;
																if(studentId1 == "on") {
																document.getElementById("<c:out value='${count}'/>").checked = true;
																		}	
																
																var studentId = document.getElementById("hidden_<c:out value='${count}'/>").value;
																if(studentId == "on") {
																document.getElementById("<c:out value='${count}'/>").checked = true;
																		}	
																	//document.write('1');
														</script>
													</td>
																
													
																
												</tr>
											</nested:iterate>
										
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
									<html:submit styleClass="formbutton" value="update" onclick="updateChallanStatusOnCourse()" ></html:submit>
									</div>
									</td>
									<td width="2%"></td>
									<td width="53%">
									<input type="Reset" class="formbutton" value="Cancel" onclick="cancelPage()" />
									</td>
								</tr>
							</table>
							</td>
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
</body>
</html:html>

<script type="text/javascript">
var year = document.getElementById("tempyear").value;
if(year.length != 0) {
 	document.getElementById("academicYear").value=year;
}
</script>
