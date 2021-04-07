<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<script type="text/javascript"><!--
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

</script>
<html:form action="/AttendanceEntryForStaff" method="post">

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
	       <div id="errorMessage" style="font-family: verdana;font-size: 10px">
	                       <FONT color="red"><html:errors/></FONT>
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
                <tr >
                  <td height="25" class="row-odd" width="110">
                  <div align="right"><bean:message key="knowledgepro.attn.teacherClass.numeric.code"/>:</div>
                  </td>
                  <td class="row-even" >
                 <html:text property="numericCode" styleId="numericCode" onchange="submitForm()"></html:text>
                  </td>
                  <td class="row-odd" width="75"><div align="right"><span class='MandatoryMark'>*</span><bean:message key="knowledgepro.attendanceentry.date"/>:</div></td>
                  <td class="row-even" width="100">
                  <table width="82" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="60">
                      <html:text name="attendanceEntryForm" styleId="attendancedate" property="attendancedate" styleClass="TextBox"/>
                      </td>
                      <td width="40"><script language="JavaScript">
							new tcal ({
								// form name
								'formname': 'attendanceEntryForm',
								// input name
								'controlname': 'attendancedate'
							});</script></td>
                    </tr>
                  </table></td>
                  <td class="row-odd" width="100"><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.fee.academicyear"/>:</div></td>
		          <td class="row-even" align="left" width="100">
		                           <input type="hidden" id="tempyear" name="tempyear" value="<bean:write name="attendanceEntryForm" property="year"/>"/>
		                           <html:select property="year" styleId="academicYear" name="attendanceEntryForm" styleClass="combo" onchange="getClassByTeacherAndYear(this.value)">
                       	   				 <html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
                       	   				<cms:renderAcademicYear></cms:renderAcademicYear>
                       			   </html:select>
		        	</td>
                </tr>
                <tr >
                  <td class="row-odd" >
                  <c:choose>
                  	<c:when test="${attendanceEntryForm.teacherMandatory == 'yes'}">
                  		<div id="teacherdiv" align="right"><span class='MandatoryMark'>*</span><bean:message key="knowledgepro.attendanceentry.teacher"/>:</div>
                  	</c:when>
                  	<c:otherwise>
                  		<div id="teacherdiv" align="right"><bean:message key="knowledgepro.attendanceentry.teacher"/>:</div>
                  	</c:otherwise>
                  </c:choose>
                  </td>
                  <td class="row-even" >
                  <c:choose>
                  	<c:when test="${attendanceEntryForm.isStaff == 'true'}">
                 		  <html:select name="attendanceEntryForm" styleId="teachers" property="teachers" style="width:200px;height:80px" multiple="multiple"  onchange="getClassForTeacher(this.value)" disabled="true">
                    	<html:optionsCollection name="attendanceEntryForm" property="teachersMap" label="value" value="key"/>
                  </html:select>
                 	 </c:when>
                 	 <c:otherwise>
                 	 	  <html:select name="attendanceEntryForm" styleId="teachers" property="teachers" style="width:200px;height:80px" multiple="multiple"  onchange="getClassForTeacher(this.value)">
                    	<html:optionsCollection name="attendanceEntryForm" property="teachersMap" label="value" value="key"/>
                  </html:select>
                 	 </c:otherwise>
                  </c:choose>
                 
                  </td>
                  <td class="row-odd" >
                  <c:choose>
                  	<c:when test="${attendanceEntryForm.classMandatry == 'yes'}">
                 		 <div id="classsdiv" align="right"><span class='MandatoryMark'>*</span><bean:message key="knowledgepro.attendanceentry.class"/>:</div>
                 	 </c:when>
                 	 <c:otherwise>
                 	 	 <div id="classsdiv" align="right"><bean:message key="knowledgepro.attendanceentry.class"/>:</div>
                 	 </c:otherwise>
                  </c:choose>
                  </td>
                  <td  class="row-even" >
                  <html:select  name="attendanceEntryForm" styleId="classes" property="classes" size="5" style="width:200px" multiple="multiple" onchange="getSubjectsPeriodsBatchForTeacherAndClass(),getBatchesForClassesActivity()">
       		    		<html:optionsCollection name="attendanceEntryForm" property="classMap" label="value" value="key"/>
                  </html:select>
                  </td>
                  <td  class="row-odd" >
                  <c:choose>
                  	<c:when test="${attendanceEntryForm.periodMandatory == 'yes'}">
                  		<div id="perioddiv" align="right"><span class='MandatoryMark'>*</span><bean:message key="knowledgepro.attendanceentry.period"/>:</div>
                  	</c:when>
                  	<c:otherwise>
                  	    <div id="perioddiv" align="right"><bean:message key="knowledgepro.attendanceentry.period"/>:</div>
                  	</c:otherwise>
                  </c:choose>
                  </td>
                  <td  class="row-even" >
                   <html:select name="attendanceEntryForm" styleId="periods" property="periods" size="5" style="width:120px" multiple="multiple">
                   <html:optionsCollection name="attendanceEntryForm" property="periodMap" label="value" value="key"/>
                  </html:select>
                  </td>
                </tr>
                <tr >
                <td  height="25" class="row-odd" >
                  <c:choose>
                  	<c:when test="${attendanceEntryForm.subjectMandatory == 'yes'}">
                  		<div id="subjectdiv" align="right"><span class='MandatoryMark'>*</span><bean:message key="knowledgepro.attendanceentry.subject"/>:</div>
                    </c:when>
                    <c:otherwise>
                    	<div id="subjectdiv" align="right"><bean:message key="knowledgepro.attendanceentry.subject"/>:</div>
                    </c:otherwise>
                  </c:choose>
                  </td>
                  <td  class="row-even" ><label>
                    <html:select style="width:275px" property="subjectId" styleId="subjectId" styleClass="comboMediumLarge" onchange="getBatches(this.value)">
                      <html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
                      <html:optionsCollection name="attendanceEntryForm" property="subjectMap" label="value" value="key"/>
                    </html:select>
                  </label></td>
                  <td height="25" class="row-odd" ><div align="right"><span class='MandatoryMark'>*</span><bean:message key="knowledgepro.attendanceentry.hoursheld"/>:</div></td>
                  <td class="row-even" >
                 <html:text name="attendanceEntryForm" styleId="hoursHeld" property="hoursHeld" styleClass="TextBox" onkeypress="return isNumberKey(event)" maxlength="4" onblur="validNumber(this)" />
                  </td>
                  <td class="row-odd" >
                   <c:choose>
                  	<c:when test="${attendanceEntryForm.batchMandatory == 'yes'}">
                  		<div id="batchdiv" align="right"><span class='MandatoryMark'>*</span><bean:message key="knowledgepro.attendanceentry.batchname"/>:</div>
                    </c:when>
                    <c:otherwise>
                    	<div id="batchdiv" align="right"><bean:message key="knowledgepro.attendanceentry.batchname"/>:</div>
                    </c:otherwise>
                   </c:choose>
                  </td>
                  <td class="row-even" >
                  <html:select name="attendanceEntryForm" property="batchId" styleId="batchId" styleClass="combo">
                    <html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
                    <html:optionsCollection name="attendanceEntryForm" property="batchMap" label="value" value="key"/>
                  </html:select></td>
                </tr>
                <tr>
                <td class="row-odd" >
                  <c:choose>
                  	<c:when test="${attendanceEntryForm.activityTypeMandatory == 'yes'}">
                  		<div id="activitydiv" align="right"><span class='MandatoryMark'>*</span><bean:message key="knowledgepro.attendanceentry.activitytype"/>:</div>
                    </c:when>
                    <c:otherwise>
                    	<div id="activitydiv" align="right"><bean:message key="knowledgepro.attendanceentry.activitytype"/>:</div>
                    </c:otherwise>
                   </c:choose>
                  </td>
                  <td class="row-even" >
                  <html:select style="width:275px" name="attendanceEntryForm" property="activityId" styleId="activityId" styleClass="comboMediumLarge" onchange="getBatchesForClassesActivity()">
                    <html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
                    		<html:optionsCollection name="attendanceEntryForm" property="activityMap" label="value" value="key"/>
                    </html:select></td>
                   <td height="25" class="row-odd" width="110">
                  <div align="right"><span class='MandatoryMark'>*</span><bean:message key="knowledgepro.attendanceentry.type"/>:</div>
                  </td>
                  <td class="row-even" >
                  <html:select style="width:275px" property="attendanceTypeId" styleId="attendanceTypeId" name="attendanceEntryForm" styleClass="combo" onchange="getMandatory(this.value)">
                  	<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
                  	<html:optionsCollection name="attendanceEntryForm" property="attendanceTypes" label="value" value="key"/>
                  </html:select>
                  </td>
                  <logic:equal name="attendanceEntryForm" property="displayRequired" value="true">
                   <td class="row-odd" >Is SMS Required
                   </td>
                   <td class="row-even" > <html:radio property="isSMSRequired" value="Yes"></html:radio>Yes &nbsp; <html:radio property="isSMSRequired" value="No"></html:radio>No
                   </td>
                  </logic:equal>
                  <!--   <td class="row-odd" >
                   
                  		<div id="slipdiv" align="right"><span class='MandatoryMark'></span><bean:message key="knowledgepro.attendanceentry.slipNo"/>:</div>
                  
                   </td>
                   <td class="row-even" >
                   <html:text name="attendanceEntryForm" styleId="slipNo" property="slipNo" styleClass="TextBox"  />
                   </td> -->
                  
                </tr>
                
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
           		<html:button value="Next" styleClass="formbutton" property="" onclick="submitData()"></html:button>
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
	var year = document.getElementById("tempyear").value;
	if (year.length != 0) {
		document.getElementById("academicYear").value = year;
	}
</script>