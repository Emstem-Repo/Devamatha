<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>

<title>Knowledge Pro</title>
<head>
	<link rel="stylesheet" type="text/css" href="css/styles.css"/>
	<link rel="stylesheet" type="text/css" href="css/sdmenu.css"/>
	<link rel="stylesheet" type="text/css" href="css/calstyle.css">
	<link rel="stylesheet" type="text/css" href="css/displaytag.css">
	<link rel="stylesheet" href="css/calendar.css">
    <script type="text/javascript" src="js/ajax/Ajax.js"></script>
    <script type="text/javascript" src="js/ajax/AjaxUtil.js"></script>
    <script type="text/javascript" src="js/sdmenu/sdmenu.js"></script>
    <script type="text/javascript" src="js/leftSwitchMenu.js"></script>
    <script type="text/javascript" src="js/basiccalendar.js"></script>
    <script type="text/javascript" src="js/clockh.js"></script>
 	<script type="text/javascript" src="js/clockp.js"></script>
	<script type="text/javascript" src="js/common.js"></script>
	<script language="JavaScript" src="js/calendar_us.js"></script>
	<script src="jquery/js/jquery-1.9.0.js" type="text/javascript"></script>
</head>
<script type="text/javascript">
$(document).keydown(function(event) {
   var keycode = event.keyCode;
   if(keycode == '116') {
      return false;
   }
});
window.onbeforeunload = confirmExit;
 function confirmExit()
 {
	if(hook){
			var deleteConfirm = confirm("You have attempted to leave this page.  If you continue, the current session will be inactive?");
			if (deleteConfirm == true) {
				document.location.href = "LogoutAction.do?method=logout";
				return true;	
			}else{
				return false;
			}
		}else{
			hook =true;
		}
 }
function getMandatory(value) {
	var destination3 = document.getElementById("batchId");
	for (x1=destination3.options.length-1; x1>0; x1--) {
		destination3.options[x1]=null;
	}
	if(value.length == 0) {
		document.getElementById("classMandatry").value = "no"; 
		document.getElementById("subjectMandatory").value ="no"; 
		document.getElementById("periodMandatory").value = "no"; 
		document.getElementById("teacherMandatory").value = "no"; 
		document.getElementById("batchMandatory").value = "no"; 
		document.getElementById("activityTypeMandatory").value = "no"; 
		document.getElementById("classsdiv").innerHTML = "Class:";
		document.getElementById("subjectdiv").innerHTML = "Subject:";
		document.getElementById("perioddiv").innerHTML = "Period:";
		document.getElementById("teacherdiv").innerHTML = "Teacher:";
		document.getElementById("batchdiv").innerHTML = "Batch Name:";
		document.getElementById("activitydiv").innerHTML = "Activity Type:";
	} else {	
	getMandatoryFieldsByAttendanceType("activityMap",value,updateMandatory);
	}
}

function updateMandatory(req) {
	var responseObj = req.responseXML.documentElement;
	var items = responseObj.getElementsByTagName("fields");
	for (var i = 0 ; i < items.length ; i++) {
         var label = items[i].getElementsByTagName("field")[0].firstChild.nodeValue;
	     var value = items[i].getElementsByTagName("mandatory")[0].firstChild.nodeValue;
	     if(label == "Class") {
	    	 document.getElementById("classMandatry").value = value; 
			if(value == "yes") {
				document.getElementById("classsdiv").innerHTML = "<span class='MandatoryMark'>*</span>Class:";	
			} else {
				document.getElementById("classsdiv").innerHTML = "Class:";
			}		
	     } else if(label == "Subject") {
	    	 document.getElementById("subjectMandatory").value = value;
	    	 if(value == 'yes') {
					document.getElementById("subjectdiv").innerHTML = "<span class='MandatoryMark'>*</span>Subject:";	
				} else {
					document.getElementById("subjectdiv").innerHTML = "Subject:";
				}
	     } else if(label == "Period") {
	    	 document.getElementById("periodMandatory").value = value;
	    	 if(value == "yes") {
					document.getElementById("perioddiv").innerHTML = "<span class='MandatoryMark'>*</span>Period:";	
				} else {
					document.getElementById("perioddiv").innerHTML = "Period:";
				}
	     } else if(label == "Teacher") {
	    	 document.getElementById("teacherMandatory").value = value;
	    	 if(value == "yes") {
					document.getElementById("teacherdiv").innerHTML = "<span class='MandatoryMark'>*</span>Teacher:";	
				} else {
					document.getElementById("teacherdiv").innerHTML = "Teacher:";
				}
	     } else if(label == "Batch name") {
	    	 document.getElementById("batchMandatory").value = value;
	    	 if(value == "yes") {
					document.getElementById("batchdiv").innerHTML = "<span class='MandatoryMark'>*</span>Batch Name:";	
				} else {
					document.getElementById("batchdiv").innerHTML = "Batch Name:";
				}
	     } else if(label == "Activity Type") {
	    	 document.getElementById("activityTypeMandatory").value = value;
	    	 if(value == "yes") {
					document.getElementById("activitydiv").innerHTML = "<span class='MandatoryMark'>*</span>Activity Type:";	
				} else {
					document.getElementById("activitydiv").innerHTML = "Activity Type:";
				}
	     }       
	 }
	var destination = document.getElementById("activityId");
	for (x1=destination.options.length-1; x1>0; x1--) {
		destination.options[x1]=null;
	}
	destination.options[0]=new Option("- Select -","");
	var items1 = responseObj.getElementsByTagName("option");
	for (var j = 0 ; j < items1.length ; j++) {
        label = items1[j].getElementsByTagName("optionlabel")[0].firstChild.nodeValue;
	     value = items1[j].getElementsByTagName("optionvalue")[0].firstChild.nodeValue;
	     destination.options[j+1] = new Option(label,value);
	 }
 
}


function getClassForTeacher(teacher){
	var teachers =  document.getElementById("teachers");	
	var destination1 = document.getElementById("subjectId");
	for (x1=destination1.options.length-1; x1>0; x1--) {
		destination1.options[x1]=null;
	}
	
	var destination2 = document.getElementById("periods");
	for (x1=destination2.options.length-1; x1>=0; x1--) {
		destination2.options[x1]=null;
	}

	var destination3 = document.getElementById("batchId");
	for (x1=destination3.options.length-1; x1>0; x1--) {
		destination3.options[x1]=null;
	}
	destination1.options[0]=new Option("- Select -","");
	destination3.options[0]=new Option("- Select -","");

	if(teachers.selectedIndex != -1) {
		//alert("testing.......");
		//destination1.options[0]=new Option("- Loading -","");
		//destination2.options[0]=new Option("- Loading -","");
		//var year = document.getElementById("academicYear").value;
		//var selectedClasses = new Array();
		//var count = 0;
		//for (var i=0; i<classes.options.length; i++) {
		    //if (classes.options[i].selected) {
		    	//selectedClasses[count] = classes.options[i].value;
		      //count++;
		    //}
		 //}	
		// getSubjectsPeriodsBatchForClassSendRequest(selectedClasses,year,updateSubjcetBatchPeriod);
	//	getSubjectsPeriodsBatchForTeacherAndClassSendRequest(selectedClasses,year, selectedTeacherId, updateSubjcetBatchPeriod);

		getClassesByTeacherInMuliSelect("classMap", teacher, "classes", updateClasses);
	} 
	
}

function getClasses(year) {
	var destination1 = document.getElementById("subjectId");
	for (x1=destination1.options.length-1; x1>0; x1--) {
		destination1.options[x1]=null;
	}
	
	var destination2 = document.getElementById("periods");
	for (x1=destination2.options.length-1; x1>=0; x1--) {
		destination2.options[x1]=null;
	}

	var destination3 = document.getElementById("batchId");
	for (x1=destination3.options.length-1; x1>0; x1--) {
		destination3.options[x1]=null;
	}
	destination1.options[0]=new Option("- Select -","");
	destination3.options[0]=new Option("- Select -","");
	
	getClassesByYear("classMap", year, "classes", updateClasses);
}
function updateClasses(req) {
	var responseObj = req.responseXML.documentElement;
	var destination = document.getElementById("classes");
	for (x1=destination.options.length-1; x1>=0; x1--) {
		destination.options[x1]=null;
	}
	var items1 = responseObj.getElementsByTagName("option");
	for (var j = 0 ; j < items1.length ; j++) {
        label = items1[j].getElementsByTagName("optionlabel")[0].firstChild.nodeValue;
	     value = items1[j].getElementsByTagName("optionvalue")[0].firstChild.nodeValue;
	     destination.options[j] = new Option(label,value);
	 }
}
function getSubjectsPeriodsBatchForTeacherAndClass() {

	var classes =  document.getElementById("classes");
	
	var destination1 = document.getElementById("subjectId");
	for (x1=destination1.options.length-1; x1>0; x1--) {
		destination1.options[x1]=null;
	}
		var destination2 = document.getElementById("periods");
	for (x1=destination2.options.length-1; x1>=0; x1--) {
		destination2.options[x1]=null;
	}
	var destination3 = document.getElementById("batchId");
	for (x1=destination3.options.length-1; x1>0; x1--) {
		destination3.options[x1]=null;
	}
	var teachers = document.getElementById("teachers");
	var selectedTeacherId = teachers.options[teachers.selectedIndex].value;
	 
	if(classes.selectedIndex != -1) {
		destination1.options[0]=new Option("- Loading -","");
		destination2.options[0]=new Option("- Loading -","");
		var year = document.getElementById("academicYear").value;
		var selectedClasses = new Array();
		var count = 0;
		for (var i=0; i<classes.options.length; i++) {
		    if (classes.options[i].selected) {
		    	selectedClasses[count] = classes.options[i].value;
		      count++;
		    }
		 }	
		// getSubjectsPeriodsBatchForClassSendRequest(selectedClasses,year,updateSubjcetBatchPeriod);
		getSubjectsPeriodsBatchForTeacherAndClassSendRequest(selectedClasses,year, selectedTeacherId, updateSubjcetBatchPeriod);
	} 
}

function updateSubjcetBatchPeriod(req) {
	var responseObj = req.responseXML.documentElement;
	var destination1 = document.getElementById("subjectId");
	
	destination1.options[0]=new Option("- Select -","");
	var items1 = responseObj.getElementsByTagName("subject");
	for (var j = 0 ; j < items1.length ; j++) {
        label = items1[j].getElementsByTagName("optionlabel")[0].firstChild.nodeValue;
	     value = items1[j].getElementsByTagName("optionvalue")[0].firstChild.nodeValue;
	     destination1.options[j+1] = new Option(label,value);
	 }

	var destination2 = document.getElementById("periods");
	
	var items2 = responseObj.getElementsByTagName("period");
	if(items2.length == 0) {
		var destination5 = document.getElementById("periods");
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
function submitData() {
	var obj1= document.getElementById("classes").selectedIndex;
	var obj2= document.getElementById("teachers").selectedIndex;
	var obj3= document.getElementById("periods").selectedIndex;
	document.getElementById("classSelectedIndex").value=obj1;
	document.getElementById("teacherSelectedIndex").value=obj2;
	document.getElementById("periodSelectedIndex").value=obj3;

	document.getElementById("attenType").value = document.getElementById("attendanceTypeId").options[document.getElementById("attendanceTypeId").selectedIndex].text;
	document.getElementById("acaYear").value = document.getElementById("academicYear").options[document.getElementById("academicYear").selectedIndex].text;
	if(document.getElementById("subjectId").selectedIndex != 0) {
		document.getElementById("attendanceSubject").value = document.getElementById("subjectId").options[document.getElementById("subjectId").selectedIndex].text;
	}else {
		document.getElementById("attendanceSubject").value = "";
	}
	if(document.getElementById("batchId").selectedIndex != 0) {
		document.getElementById("attendanceBatchName").value = document.getElementById("batchId").options[document.getElementById("batchId").selectedIndex].text;
	} else {
		document.getElementById("attendanceBatchName").value = "";
	}
	if(document.getElementById("activityId").selectedIndex != 0) {
		document.getElementById("attendanceActivity").value = document.getElementById("activityId").options[document.getElementById("activityId").selectedIndex].text;
	} else {
		document.getElementById("attendanceActivity").value = "";
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

	var periodString="";
	var periods = document.getElementById("periods");
	for (var k=0; k<periods.options.length; k++) {
	    if (periods.options[k].selected) {
	    	periodString = periodString + periods.options[k].text+", ";
	    }
	 }
	classesString =  classesString.substr(0,(classesString.length - 2));
	teacherString = teacherString.substr(0,(teacherString.length - 2));
	periodString = periodString.substr(0,(periodString.length - 2));
	document.getElementById("attendanceClass").value = classesString;
	document.getElementById("attendanceTeacher").value = teacherString;
	document.getElementById("attendancePeriod").value = periodString;

	
	document.attendanceEntryForm.submit();
}
function validNumber(field) {
	if(isNaN(field.value)) {
		field.value="";
	}
}


function getClassByTeacherAndYear(year){
	document.getElementById("numericCode").value='';
	var teachers =  document.getElementById("teachers").value;
	var destination1 = document.getElementById("subjectId");
	for (x1=destination1.options.length-1; x1>0; x1--) {
		destination1.options[x1]=null;
	}
	var destination2 = document.getElementById("periods");
	for (x1=destination2.options.length-1; x1>=0; x1--) {
		destination2.options[x1]=null;
	}
	var destination3 = document.getElementById("batchId");
	for (x1=destination3.options.length-1; x1>0; x1--) {
		destination3.options[x1]=null;
	}
	destination1.options[0]=new Option("- Select -","");
	destination3.options[0]=new Option("- Select -","");
	if(year!=null && year!='') {
		getClassesByTeacherAndYear("classMap", year, teachers,"classes", updateClasses);
	} 
}
function submitForm(){
	var numericCode=document.getElementById("numericCode").value;
	document.getElementById("method").value = "setDataForNumericCode";
	document.attendanceEntryForm.submit();
}
function getBatchesForClassesActivity(){
	var classes =  document.getElementById("classes");
	var selectedClasses = new Array();
			var count = 0;
			for (var i=0; i<classes.options.length; i++) {
			    if (classes.options[i].selected) {
			    	selectedClasses[count] = classes.options[i].value;
			      count++;
			    }
			 }
	var activity=  document.getElementById("activityId").value;
	if(classes.selectedIndex != -1 && activity!=null && activity!=""){
		getBatchesForActivityAndClass(selectedClasses,activity);
		}
}
function getBatchesForActivityAndClass(selectedClasses,activity){
	getBatchesByActivity("subjectMap",activity,selectedClasses,"batchId",updateBatches);
}

function searchDeptWise(deptId){
	getTearchersByDepartmentWise(deptId,updateTeachersMap);
}
function updateTeachersMap(req){
	updateTeachersFromMap(req,"teachers");
}

</script>
<html:form action="/AttendanceEntry" method="post">

	<html:hidden property="formName" value="attendanceEntryForm" />
	<html:hidden property="method" styleId="method" value="initAttendanceEntrySecondPage"/>
	<html:hidden property="pageType" value="1"/>
	<html:hidden  property="classMandatry" styleId="classMandatry" name="attendanceEntryForm"/>
	<html:hidden  property="subjectMandatory" styleId="subjectMandatory" name="attendanceEntryForm" />
	<html:hidden  property="periodMandatory" styleId="periodMandatory" name="attendanceEntryForm" />
	<html:hidden  property="teacherMandatory" styleId="teacherMandatory" name="attendanceEntryForm" />
	<html:hidden  property="batchMandatory" styleId="batchMandatory" name="attendanceEntryForm" />
	<html:hidden  property="activityTypeMandatory" styleId="activityTypeMandatory" name="attendanceEntryForm" />
	<html:hidden property="classSelectedIndex" styleId="classSelectedIndex"/>
	<html:hidden property="teacherSelectedIndex" styleId="teacherSelectedIndex"/>
	<html:hidden property="periodSelectedIndex" styleId="periodSelectedIndex"/>
	
	<html:hidden property="attenType" styleId="attenType"/>
	<html:hidden property="acaYear" styleId="acaYear"/>
	<html:hidden property="attendanceClass" styleId="attendanceClass"/>
	<html:hidden property="attendanceSubject" styleId="attendanceSubject"/>
	<html:hidden property="attendanceTeacher" styleId="attendanceTeacher"/>
	<html:hidden property="attendancePeriod" styleId="attendancePeriod"/>
	<html:hidden property="attendanceBatchName" styleId="attendanceBatchName"/>
	<html:hidden property="attendanceActivity" styleId="attendanceActivity"/>
	<html:hidden property="classIds" styleId="classIds" name="attendanceEntryForm"/>
	<html:hidden property="classId" styleId="classId" name="attendanceEntryForm"/>
	<html:hidden property="checked" styleId="checked" name="attendanceEntryForm"/>
	<html:hidden property="subjectId" styleId="subjectId"/>
	<html:hidden property="isTheoryPractical" styleId="theoryPractical"/>
	<table width="99%" border="0">
  
  <tr>
    <td><span class="Bredcrumbs"><bean:message key="knowledgepro.attendanceentry.attendance"/> <span class="Bredcrumbs">&gt;&gt; <bean:message key="knowledgepro.attendanceentry.entry"/>&gt;&gt;</span></span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="heading_white" ><bean:message key="knowledgepro.attendanceentry.entry"/></td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td >
	       <div align="right" style="color:red" class="heading"> <span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields"/></span></div>
	       <div id="errorMessage" style="font-family: verdana;font-size: 14px">
	                       <FONT color="red" style="font-family: verdana;font-size: 14px"><html:errors/></FONT>
	                       <FONT color="green">
								<html:messages id="msg" property="messages" message="true">
										<c:out value="${msg}" escapeXml="false"></c:out><br>
								</html:messages>
						  </FONT>
	  	   </div>
        
        </td>
        <td valign="top" background="images/Tright_3_3.gif" ></td>
      </tr>
      <tr>
        <td valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" >
        
        
        <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td ><img src="images/01.gif" width="5" height="5" /></td>
            <td width="914" background="images/02.gif"></td>
            <td><img src="images/03.gif" width="5" height="5" /></td>
          </tr>
          <tr>
            <td width="5"  background="images/left.gif"></td>
            <td valign="top">
            <table width="100%" cellspacing="1" cellpadding="2">
                <tr>
					<td class="atten-odd" width="35%"><div align="right">Teacher: </div></td>
					<td class="atten-even" width="60%"> <bean:write name="attendanceEntryForm" property="teacherName"/> </td>
                </tr>
                <tr>
                 	<td class="atten-odd" >
							<c:choose>
		                  	<c:when test="${attendanceEntryForm.classMandatry == 'yes'}">
		                 		 <div id="classsdiv" align="right"><span class='MandatoryMark'>*</span><bean:message key="knowledgepro.attendanceentry.class"/>:</div>
		                 	 </c:when>
		                 	 <c:otherwise>
		                 	 	 <div id="classsdiv" align="right"><bean:message key="knowledgepro.attendanceentry.class"/>:</div>
		                 	 </c:otherwise>
		                  </c:choose>
					</td>
                    <td class="atten-even" ></td>
                </tr>
                <logic:notEmpty property="classList" name="attendanceEntryForm">
                	<nested:iterate id="classes1" property="classList" name="attendanceEntryForm" indexId="count" type="com.kp.cms.to.attendance.ClassesTO">
                		
						<tr class="atten-even">
							
	                		<td align="right">
		               				<%if(classes1.getClassName() != null && !classes1.getClassName().isEmpty()) {%> 
									<input	type="hidden"	name="classList[<c:out value='${count}'/>].tempChecked"	id="hidden_<c:out value='${count}'/>"
																	value="<nested:write name='classes1' property='checked'/>" />
										<span id="s1_<c:out value='${count}'/>">
										<input type="checkbox"	name="attendanceEntryForm.checked"	id="classList1_<c:out value='${count}'/>" onclick="getPeriodDetails(<c:out value='${count}'/>,'<bean:write name="classes1" property="id" />')"/>
										</span>
										<script type="text/javascript">
																		var studentId = document.getElementById("hidden_<c:out value='${count}'/>").value;
																		if(studentId == "on") {
																			document.getElementById("classList1_<c:out value='${count}'/>").checked = true;
																			document.getElementById("s1_<c:out value='${count}'/>").style.background="red";
																		}		
																	</script>
										<%} %>
									 </td>
		               				<td align="left"> <bean:write name="classes1" property="className" /></td>
		               		</tr>
                	</nested:iterate>
                </logic:notEmpty>
                <tr>
               		<td class="atten-odd" >
               				<c:choose>
		                  	<c:when test="${attendanceEntryForm.periodMandatory == 'yes'}">
		                  		<div id="perioddiv" align="right"><span class='MandatoryMark'>*</span><bean:message key="knowledgepro.attendanceentry.period"/>:</div>
		                  	</c:when>
		                  	<c:otherwise>
		                  	    <div id="perioddiv" align="right"><bean:message key="knowledgepro.attendanceentry.period"/>:</div>
		                  	</c:otherwise>
		                  </c:choose>
               		</td>
                    <td class="atten-even" ><bean:write name="attendanceEntryForm" property="currentPeriodName"/> </td>
                </tr>
                <tr>
                	<td  height="25" class="atten-odd" >
                  		<c:choose>
		                  	<c:when test="${attendanceEntryForm.subjectMandatory == 'yes'}">
		                  		<div id="subjectdiv" align="right"><span class='MandatoryMark'>*</span><bean:message key="knowledgepro.attendanceentry.subject"/>:</div>
		                    </c:when>
		                    <c:otherwise>
		                    	<div id="subjectdiv" align="right"><bean:message key="knowledgepro.attendanceentry.subject"/>:</div>
		                    </c:otherwise>
		                  </c:choose>
                  	</td>
                  	 <td class="atten-even" ></td>
                </tr>
                 <logic:notEmpty property="subjectList" name="attendanceEntryForm">
                	<nested:iterate id="subject" property="subjectList" name="attendanceEntryForm" indexId="count" type="com.kp.cms.to.admin.SubjectTO">
                		<tr class="atten-even">
							
	                		<td class="atten-even" align="right">
										<%if(subject.getName() != null && !subject.getName().isEmpty()){ %>
										<span id="ss_<c:out value='${count}'/>">
										<input type="radio" name="subjectId" id="subjectList_<c:out value='${count}'/>" 
																				  value="<nested:write  property='subjectID' />" onclick="getAditionalDetails(<c:out value='${count}'/>,'<bean:write name="subject" property="subjectID" />','<bean:write name="subject" property="name" />','<bean:write name="subject" property="theoryPractical" />')"/>
										</span>
										<%} %>
									</td>
               						<td class="atten-even" align="left"> <bean:write name="subject" property="name" /></td>
					</tr>
							<script type="text/javascript">
								var subType = document.getElementById("subjectList_<c:out value='${count}'/>").value;
								var subValue = "<c:out value='${attendanceEntryForm.subjectId}'/>";
								if(subType == subValue) {
										document.getElementById("subjectList_<c:out value='${count}'/>").checked = true;
										document.getElementById("ss_<c:out value='${count}'/>").style.background="red";
								}	
							</script>
                	</nested:iterate>
                </logic:notEmpty>
                <logic:notEmpty property="teachersList" name="attendanceEntryForm">
		              		 <tr>
			                	<td  height="25" class="atten-odd">
				                  		<div id="" align="right"><span class='MandatoryMark'></span>Additional Teachers:</div>
			                  	</td>
			                  	 <td class="atten-even" ></td>
		                	</tr>    	
		                <nested:iterate id="teac" property="teachersList" name="attendanceEntryForm" indexId="count">
		                		<tr class="atten-even">
									
			                		<td class="atten-even"  align="right">
												<span id="teacher_<c:out value='${count}'/>">
												<input	type="hidden"	name="teachersList[<c:out value='${count}'/>].tempChecked"	id="hiddent_<c:out value='${count}'/>"
																	value="<nested:write name='teac' property='checked'/>" />
												<span id="teacher_<c:out value='${count}'/>">
											 	<input type="checkbox" name="teachersList[<c:out value='${count}'/>].checked" id="teachersList_<c:out value='${count}'/>" onclick="redColourCheckBox(<c:out value='${count}'/>)"/>
											 	</span>
											 	<script type="text/javascript">
																		var studentId = document.getElementById("hiddent_<c:out value='${count}'/>").value;
																		if(studentId == "on") {
																			document.getElementById("teachersList_<c:out value='${count}'/>").checked = true;
																			document.getElementById("teacher_<c:out value='${count}'/>").style.background="red";
																		}		
																	</script>
												</span>
											 </td>
		               						<td class="atten-even" align="left"> <bean:write name="teac" property="teacherName" /></td>
		               			</tr>
		               </nested:iterate>
		         	</logic:notEmpty>
		         	 <logic:notEmpty property="periodList" name="attendanceEntryForm">
		         	 		<tr>
			                	<td  height="25" class="atten-odd">
				                  		<div id="" align="right"><span class='MandatoryMark'></span>Additional Period(s):</div>
			                  	</td>
			                  	 <td class="atten-even" ></td>
		                	</tr>    	
		         	 
                	<nested:iterate id="period" property="periodList" name="attendanceEntryForm" indexId="count" type="com.kp.cms.to.attendance.PeriodTO">
	                	<tr class="atten-even">
							
	                		<td class="atten-even" align="right">
										<%if(period.getName() != null && !period.getName().isEmpty()) {%>
											<input	type="hidden"	name="periodList[<c:out value='${count}'/>].tempChecked"	id="hiddenp_<c:out value='${count}'/>"
																	value="<nested:write name='period' property='checked'/>" />
											<span id="sp_<c:out value='${count}'/>">
										 	<input type="checkbox" name="periodList[<c:out value='${count}'/>].checked" id="periodList_<c:out value='${count}'/>" onclick="setColourForPeriods(<c:out value='${count}'/>)"/>
										 	</span>
										 	<script type="text/javascript">
																		var studentId = document.getElementById("hiddenp_<c:out value='${count}'/>").value;
																		if(studentId == "on") {
																			document.getElementById("periodList_<c:out value='${count}'/>").checked = true;
																			document.getElementById("sp_<c:out value='${count}'/>").style.background="red";
																		}		
												</script>
										 <%} %>
									</td>
               						<td class="atten-even" align="left"> <bean:write name="period" property="name" /></td>
               				</tr>
                	</nested:iterate>
                </logic:notEmpty>
		         	
                <tr>
                	 <td height="25" class="atten-odd" ><div align="right"><span class='MandatoryMark'>*</span><bean:message key="knowledgepro.attendanceentry.hoursheld"/>:</div></td>
	                  <td class="atten-even" >
	                 <html:text name="attendanceEntryForm" styleId="hoursHeld" property="hoursHeld" styleClass="TextBox" onkeypress="return checkNumeric(event)" maxlength="4" onblur="validNumber(this)" />
	                  </td>
                </tr>
                <tr>
                	<td class="atten-odd" >
	                   <c:choose>
	                  	<c:when test="${attendanceEntryForm.batchMandatory == 'yes'}">
	                  		<div id="batchdiv" align="right"><span class='MandatoryMark'>*</span><bean:message key="knowledgepro.attendanceentry.batchname"/>:</div>
	                    </c:when>
	                    <c:otherwise>
	                    	<div id="batchdiv" align="right"><bean:message key="knowledgepro.attendanceentry.batchname"/>:</div>
	                    </c:otherwise>
	                   </c:choose>
	                  </td>
	                  <td class="atten-even" >
	                   <html:select name="attendanceEntryForm" styleId="batchId" property="batchIdsArray" size="5" style="width:250px" multiple="multiple">
                    <html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
                    <html:optionsCollection name="attendanceEntryForm" property="batchMap" label="value" value="key"/>
                  </html:select>
	                  </td>
                </tr>
                <tr>
                	<td  height="25" class="atten-odd" >
                  		<div align="right"><span class='MandatoryMark'>*</span><bean:message key="knowledgepro.attendanceentry.type"/>:</div>
                  	</td>
                  	 <td class="atten-even" ></td>
                </tr>
                <logic:notEmpty property="attendanceTypeList" name="attendanceEntryForm">
                	<nested:iterate id="type" property="attendanceTypeList" name="attendanceEntryForm" indexId="count" type="com.kp.cms.to.attendance.AttendanceTypeTO">
                		<tr class="atten-even">
								       <td class="atten-even" align="right">
										<%if(type.getAttendanceTypeName() != null && !type.getAttendanceTypeName().isEmpty()) {%>
										<span id="type_<c:out value='${count}'/>">
										<input type="radio" name="attendanceTypeId" id="attendanceTypeList_<c:out value='${count}'/>" 
																					value="<nested:write name='type' property='id' />" 
																					onclick="checkMandatoryFields(<c:out value='${count}'/>,'<bean:write name="type" property="id" />','<bean:write name="type" property="attendanceTypeName" />')"/>  
										</span>
									<%} %>
									</td>
               						<td class="atten-even" align="left"> <bean:write name="type" property="attendanceTypeName" /></td>
               			</tr>
							<script type="text/javascript">
									var attType = document.getElementById("attendanceTypeList_<c:out value='${count}'/>").value;
									var formValue = "<c:out value='${attendanceEntryForm.attendanceTypeId}'/>";
									if(attType == formValue) {
											document.getElementById("attendanceTypeList_<c:out value='${count}'/>").checked = true;
											document.getElementById("type_<c:out value='${count}'/>").style.background="red";
									}		
							</script>
                	</nested:iterate>
                </logic:notEmpty>
                
                 <tr>
                	<td  height="25" class="atten-odd" >
                  		<c:choose>
	                  	<c:when test="${attendanceEntryForm.activityTypeMandatory == 'yes'}">
	                  		<div id="activitydiv" align="right"><span class='MandatoryMark'>*</span><bean:message key="knowledgepro.attendanceentry.activitytype"/>:</div>
	                    </c:when>
	                    <c:otherwise>
	                    	<div id="activitydiv" align="right"><bean:message key="knowledgepro.attendanceentry.activitytype"/>:</div>
	                    </c:otherwise>
	                   </c:choose>
                  	</td>
                  	 <td class="atten-even"> 
                  	 	<span id="deSelectStyle">
                  	 	<input type="checkbox" id="deSelectId" onclick="unSelectActivity()"/></span> &nbsp;&nbsp; Deselect</td>
                </tr>
                
                  <logic:notEmpty property="activityList" name="attendanceEntryForm">
		                	<nested:iterate id="activity" property="activityList" name="attendanceEntryForm" indexId="count" type="com.kp.cms.to.attendance.ActivityTO">
		                		
		                	<tr class="atten-even">
			                		<td class="atten-even" align="right">
												<%if(activity.getName() != null && !activity.getName().isEmpty()){ %>
												<span id="act_<c:out value='${count}'/>">
												<input type="radio" name="activityId" id="activityListId_<c:out value='${count}'/>" value="<nested:write name='activity' property='id' />" onclick="getBatchesForClassesActivity1(<c:out value='${count}'/>,this.value,'<bean:write name="activity" property="name" />')"/> 
											 	</span>
											 	<%} %>
											 </td>
		               						<td class="atten-even" align="left"> <bean:write name="activity" property="name" /></td>
		               			</tr>
		                	</nested:iterate>
		                </logic:notEmpty>
              </table>
              </td>
            <td width="5" height="30"  background="images/right.gif"></td>
          </tr>
          <tr>
            <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
            <td background="images/05.gif"></td>
            <td><img src="images/06.gif" /></td>
          </tr>
        </table>
        
        
        </td>
        <td valign="top" background="images/Tright_3_3.gif" ></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td class="heading"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
        <tr><td style="height: 10px" align="center" colspan="1"></td></tr>
          <tr>
            <td height="35" align="center">
            	<input type="button" class="formbutton" onclick="nextPage()" value="             Next            "/>
            	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            	<input type="button" class="formbutton" onclick="cancelPage()" value="           Cancel            "/>
            </td>
          </tr>
         <tr><td style="height: 10px" align="center" colspan="1"></td></tr>          
        </table></td>
        <td valign="top" background="images/Tright_3_3.gif" ></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td class="heading"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
        </table></td>
        <td valign="top" background="images/Tright_3_3.gif" ></td>
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
	function nextPage(){
		var hrsHeld =document.getElementById("hoursHeld").value;
		document.getElementById("hoursHeld").value=hrsHeld;		
		document.getElementById("method").value="initAttendanceEntrySecondPageNew";
		document.attendanceEntryForm.submit();
	}
	function getPeriodDetails(checkValue,classId){
		document.getElementById("s1_"+checkValue).style.background="red";
		var check = document.getElementById("classList1_"+checkValue).checked;
		if(check){
			document.getElementById("checked").value="on";
			document.getElementById("classId").value=classId;
			document.getElementById("method").value="getPeriodDetails";
			document.attendanceEntryForm.submit();
		}else{
			document.getElementById("checked").value="off";
			document.getElementById("classId").value=classId;
			document.getElementById("method").value="getPeriodDetails";
			document.attendanceEntryForm.submit();
		}
	}
	function checkMandatoryFields(countValu,value,attenTypeName){
		document.getElementById("type_"+countValu).style.background="red";
		if(value != null && value == "3"){
			document.getElementById("method").value="getActivityType";
			document.attendanceEntryForm.submit();
		}			
	}
	function checkMandatory(value,attenTypeName){
		if(attenTypeName=="Practical"){
			document.getElementById("method").value="getMultiTeachersForPractical";
			document.attendanceEntryForm.submit();
		}else{
			if(value.length == 0) {
				document.getElementById("classMandatry").value = "no"; 
				document.getElementById("subjectMandatory").value ="no"; 
				document.getElementById("periodMandatory").value = "no"; 
				document.getElementById("batchMandatory").value = "no"; 
				document.getElementById("activityTypeMandatory").value = "no"; 
				document.getElementById("classsdiv").innerHTML = "Class:";
				document.getElementById("subjectdiv").innerHTML = "Subject:";
				document.getElementById("perioddiv").innerHTML = "Period:";
				document.getElementById("batchdiv").innerHTML = "Batch Name:";
				document.getElementById("activitydiv").innerHTML = "Activity Type:";
			} else {	
				getMandatoryFieldsByAttendanceType("activityMap",value,updateMandatory);
			}
		}
	}

	function updateMandatory(req) {
		var responseObj = req.responseXML.documentElement;
		var items = responseObj.getElementsByTagName("fields");
		for (var i = 0 ; i < items.length ; i++) {
	         var label = items[i].getElementsByTagName("field")[0].firstChild.nodeValue;
		     var value = items[i].getElementsByTagName("mandatory")[0].firstChild.nodeValue;
		     if(label == "Class") {
		    	 document.getElementById("classMandatry").value = value; 
				if(value == "yes") {
					document.getElementById("classsdiv").innerHTML = "<span class='MandatoryMark'>*</span>Class:";	
				} else {
					document.getElementById("classsdiv").innerHTML = "Class:";
				}		
		     } else if(label == "Subject") {
		    	 document.getElementById("subjectMandatory").value = value;
		    	 if(value == 'yes') {
						document.getElementById("subjectdiv").innerHTML = "<span class='MandatoryMark'>*</span>Subject:";	
					} else {
						document.getElementById("subjectdiv").innerHTML = "Subject:";
					}
		     } else if(label == "Period") {
		    	 document.getElementById("periodMandatory").value = value;
		    	 if(value == "yes") {
						document.getElementById("perioddiv").innerHTML = "<span class='MandatoryMark'>*</span>Period:";	
					} else {
						document.getElementById("perioddiv").innerHTML = "Period:";
					}
		     }else if(label == "Batch name") {
		    	 document.getElementById("batchMandatory").value = value;
		    	 if(value == "yes") {
						document.getElementById("batchdiv").innerHTML = "<span class='MandatoryMark'>*</span>Batch Name:";	
					} else {
						document.getElementById("batchdiv").innerHTML = "Batch Name:";
					}
		     } else if(label == "Activity Type") {
		    	 document.getElementById("activityTypeMandatory").value = value;
		    	 if(value == "yes") {
						document.getElementById("activitydiv").innerHTML = "<span class='MandatoryMark'>*</span>Activity Type:";	
					} else {
						document.getElementById("activitydiv").innerHTML = "Activity Type:";
					}
		     }       
		 }
		var htm="<table width='100%' cellspacing='1' cellpadding='2'>";
		var items1 = responseObj.getElementsByTagName("option");
		var count1=0;
		for (var j = 0 ; j < items1.length ; j++) {
	        label = items1[j].getElementsByTagName("optionlabel")[0].firstChild.nodeValue;
		     value = items1[j].getElementsByTagName("optionvalue")[0].firstChild.nodeValue;
		     if(count1%2==0){
				htm = htm+"<tr height='25px' class='atten-even'>";
			 }
		     if(count1%2==0){
				htm = htm+"<td class='atten-even' width='60' align='right'>"+"<input type='radio' name='activityId' value='"+value+"'/>"; 
				htm = htm+"</td> <td class='atten-even' width='125' align='left'>"+label +"</td> ";
		     }else{
		    	 htm = htm+"<td class='atten-even' width='80' align='right'>"+"<input type='radio' name='activityId' value='"+value+"'/>"; 
				 htm = htm+"</td> <td class='atten-even' width='100' align='left'>"+label +"</td>";
			 }
			count1=count1+1;
		 }
		 htm = htm+"</table>";
		document.getElementById("_display").innerHTML = htm;
	}
	function getBatchesForClassesActivity1(countV,activity,activetyName){
		document.getElementById("attendanceActivity").value=activetyName;
		var selectedClasses =  document.getElementById("classIds").value;;
		if(selectedClasses!=null && selectedClasses!="" && activity!=null && activity!=""){
			getBatchesForActivityAndClass1(selectedClasses,activity);
			}
		for(var i =0;i<10;i++){
			if(countV==i){
				document.getElementById("act_"+countV).style.background="red";
			}else{
				document.getElementById("act_"+i).style.background="";
			}
		}
	}
	function getBatchesForActivityAndClass1(selectedClasses,activity){
		getBatchesByActivity1("subjectMap",activity,selectedClasses,"batchId",updateBatches);
	}

	function updateBatches(req) {
		updateOptionsFromMap(req,"batchId","- Select -");
	}
	
	function getBatches1(countval,subjectId,subjectName) {
		document.getElementById("ss_"+countval).style.background="red";
		document.getElementById("attendanceSubject").value=subjectName;
		var selectedClasses =  document.getElementById("classIds").value;
		getBatchesBySubject1("subjectMap",subjectId,selectedClasses,"batchId",updateBatches);
	}
	function redColourCheckBox(periodValue){
		var isCheck = document.getElementById("teachersList_"+periodValue).checked;
		if(isCheck){
			document.getElementById("teacher_"+periodValue).style.background="red";
		}else{
			document.getElementById("teacher_"+periodValue).style.background="white";
		}
	}
	function cancelPage(){
		document.location.href = "LoginAction.do?method=loginAction";
	}
	function unSelectActivity(){
		document.getElementById("deSelectStyle").style.background="red";
		document.getElementById("deSelectId").checked=false;
		document.getElementById("deSelectStyle").style.background="white";
		for(var i=0;i<=50;i++){
			var actCheck = document.getElementById("activityListId_"+i).checked;
			if(actCheck==true){
				document.getElementById("activityListId_"+i).checked=false;
				document.getElementById("act_"+i).style.background="white";
			}
		}
	}
	function getAditionalDetails(countval,subjectId,subjectName,isTheoryPractical) {
		document.getElementById("ss_"+countval).style.background="red";
		document.getElementById("attendanceSubject").value=subjectName;
		document.getElementById("subjectId").value=subjectId;
		document.getElementById("theoryPractical").value=isTheoryPractical;
		document.getElementById("method").value="getAditionalPeriods";
		document.attendanceEntryForm.submit();
	}
	function setColourForPeriods(inpuValue){
		
		var isCheck = document.getElementById("periodList_"+inpuValue).checked;
		if(isCheck){
			var hoursHeld = document.getElementById("hoursHeld").value;
			
			document.getElementById("hoursHeld").value = parseInt(hoursHeld)+1;
			document.getElementById("periodList_"+inpuValue).value="on";
			document.getElementById("hiddenp_"+inpuValue).value="on";
			document.getElementById("sp_"+inpuValue).style.background="red";
		}else{
			var hoursHeld = document.getElementById("hoursHeld").value;
			document.getElementById("hoursHeld").value = parseInt(hoursHeld)-1;
			document.getElementById("sp_"+inpuValue).style.background="white";
			document.getElementById("hiddenp_"+inpuValue).value="off";
			document.getElementById("periodList_"+inpuValue).value="off";
		}
	}
	function checkNumeric(event) {
		return false;
		}
	document.getElementById("hoursHeld").disabled = false;
</script>