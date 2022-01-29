<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<!--<link rel="stylesheet" href="css/messi.min.css" />
<script src="js/messi.min.js"></script>
--><script type="text/javascript">
function updateData() {
	document.getElementById("method").value ="updateAttendance";
	document.attendanceEntryForm.submit();
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

function getClasses(year) {
	var destination1 = document.getElementById("subjectId");
	for (x1=destination1.options.length-1; x1>0; x1--) {
		destination1.options[x1]=null;
	}
	
	var destination2 = document.getElementById("period");
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

function getSubjectsPeriodsBatchForClass() {

	var classes =  document.getElementById("classes");
	
	var destination1 = document.getElementById("subjectId");
	for (x1=destination1.options.length-1; x1>0; x1--) {
		destination1.options[x1]=null;
	}
	
	var destination2 = document.getElementById("periods");
	for (x1=destination2.options.length-1; x1>=0; x1--) {
		destination2.options[x1]=null;
	}

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
		getSubjectsPeriodsBatchForClassSendRequest(selectedClasses,year,updateSubjcetBatchPeriod);
		
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
	}
	if(document.getElementById("batchId").selectedIndex != 0) {
		document.getElementById("attendanceBatchName").value = document.getElementById("batchId").options[document.getElementById("batchId").selectedIndex].text;
	}
	if(document.getElementById("activityId").selectedIndex != 0) {
		document.getElementById("attendanceActivity").value = document.getElementById("activityId").options[document.getElementById("activityId").selectedIndex].text;
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

function viewAttendanceFirstPage() {
	document.getElementById("method").value ="loadViewAttendancePage";
	document.attendanceEntryForm.submit();
}

function getBatches(){
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


function showInfo(teachername,className,subjectName){

	var tab1 = '<p>Class &nbsp; &nbsp;:&nbsp;'+className+'<br>Teacher:&nbsp;'+teachername+'<br>Subject&nbsp;:&nbsp;'+subjectName+'</p>';
	new Messi(''+tab1+'', {title: '', modal: true, titleClass: 'success', buttons: [{id: 0, label: 'Close', val: 'X'}]});

}
</script>
<style> 
label input {
  display: none;/* <-- hide the default checkbox */
}
label span {/* <-- style the artificial checkbox */
  height: 10px;
  width: 10px;
  border: 1px solid grey;
  display: inline-block;
  
}
.table-striped > tbody > tr:nth-child(2n+1) > td {
    background-color: #03a9f440;
}

</style>
<html:form action="/AttendanceEntry" method="post">

<html:hidden property="formName" value="attendanceEntryForm" />
<html:hidden property="method" styleId="method" value=""/>
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
<head>
 <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Bootstrap DatePicker</title>
    <script src="https://code.jquery.com/jquery-3.3.1.min.js"></script><!--
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    --><script src="https://unpkg.com/gijgo@1.9.11/js/gijgo.min.js" type="text/javascript"></script>
    <link href="https://unpkg.com/gijgo@1.9.11/css/gijgo.min.css" rel="stylesheet" type="text/css" />


</head>
<div class="panel-body">
                        <div class="panel panel-primary">
                         <div class="panel-heading">
                         <h1 class="panel-title">View Attendance</h1>
                         
                         </div>
     
                         </div>
                         
                         
  <div class="form-group">
                 <div class="col-sm-10">
        	<div align="right" style="color:red;" > <span class='MandatoryMark'><b><bean:message key="knowledgepro.mandatoryfields"/></b></span></div>
	        <div class="col-sm-12" style="color:blue;">
	        <div id="errorMessage">
	                       <FONT color="red"><html:errors/></FONT>
	                       <FONT color="green">
								<html:messages id="msg" property="messages" message="true">
										<c:out value="${msg}" escapeXml="false"></c:out><br>
								</html:messages>
						  </FONT>
	  	     </div>
        </div>
     </div>
    </div>
        
          <div class="form-group">
                 <div class="col-sm-12">
        <div class="col-sm-12"  style="border-style: solid;border-color: #29953b">
               <div class="form-group">
                 <div class="col-sm-12">
                  		 <div class="col-sm-2">
	                  <span class='MandatoryMark'style="color:red;">*</span><b><bean:message key="knowledgepro.attendanceentry.type"/></b>:</div>                 
	                 <div class="col-sm-2">
	                  <html:select property="attendanceTypeId" styleId="attendanceTypeId" name="attendanceEntryForm" styleClass="form-control" onchange="getMandatory(this.value)" disabled="true">
	                  	<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
	                  	<html:optionsCollection name="attendanceEntryForm" property="attendanceTypes" label="value" value="key" />
	                  </html:select>
	                 </div>
	                  <div class="col-sm-2"><span class='MandatoryMark' style="color:red;">*</span><b><bean:message key="knowledgepro.attendanceentry.date"/></b>:</div><!--
	                <div class="col-sm-2">
	                
	                      <html:text name="attendanceEntryForm" styleId="attendancedate" property="attendancedate" styleClass="form-control"/>
	                      </div>
	                      <div class="col-sm-1"><script language="JavaScript">
								new tcal ({
									// form name
									'formname': 'attendanceEntryForm',
									// input name
									'controlname': 'attendancedate'
								});</script></div>
	                 -->
	                  <div class="col-sm-2">                    
                   <html:text  size="15" name="attendanceEntryForm" styleId="attendancedate" property="attendancedate" styleClass="form-control" />                
                  </div>
                   <script>
        $('#attendancedate').datepicker({
         format: 'dd/mm/yyyy'
        	
        });
    </script>
	                 <div class="col-sm-2"><span class="Mandatory" style="color:red;">*</span><b><bean:message key="knowledgepro.fee.academicyear"/></b>:</div>
			         <div class="col-sm-2">
			                           <input type="hidden" id="tempyear" name="tempyear" value="<bean:write name="attendanceEntryForm" property="year"/>"/>
			                           <html:select property="year" styleId="academicYear" name="attendanceEntryForm" styleClass="form-control" onchange="getClasses(this.value)" disabled="true">
	                       	   				 <html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
	                       	   				<cms:renderAcademicYear></cms:renderAcademicYear>
	                       			   </html:select>
			        	</div>
			        	</div>
			        	</div>
			        	 <div class="form-group">
                 <div class="col-sm-12">
	               <div class="col-sm-2">
	                  <c:choose>
	                  	<c:when test="${attendanceEntryForm.classMandatry == 'yes'}">
	                 		 <div id="classsdiv" ><span class='MandatoryMark'style="color:red;">*</span><b><bean:message key="knowledgepro.attendanceentry.class"/></b>:</div>
	                 	 </c:when>
	                 	 <c:otherwise>
	                 	 	 <div id="classsdiv" ><b><bean:message key="knowledgepro.attendanceentry.class"/></b>:</div>
	                 	 </c:otherwise>
	                  </c:choose>
	               </div>
	                 <div class="col-sm-2">
	                  <html:select name="attendanceEntryForm" styleId="classes" property="classes" size="5"  multiple="multiple" styleClass="form-control" onchange="getSubjectsPeriodsBatchForClass(),getBatches()" disabled="true">
	       		    		<html:optionsCollection name="attendanceEntryForm" property="classMap" label="value" value="key"/>
	                  </html:select>
	                </div>
	                 <div class="col-sm-2">
	                  <c:choose>
	                  	<c:when test="${attendanceEntryForm.teacherMandatory == 'yes'}">
	                  		<div id="teacherdiv"><span class='MandatoryMark' style="color:red;">*</span><b><bean:message key="knowledgepro.attendanceentry.teacher"/></b>:</div>
	                  	</c:when>
	                  	<c:otherwise>
	                  		<div id="teacherdiv" ><b><bean:message key="knowledgepro.attendanceentry.teacher"/></b>:</div>
	                  	</c:otherwise>
	                  </c:choose>	
	                  </div>
	                  <div class="col-sm-2">
	                  <html:select name="attendanceEntryForm" styleId="teachers" property="teachers" size="5"  multiple="multiple" styleClass="form-control" disabled="true">
	                    	<html:optionsCollection name="attendanceEntryForm" property="teachersMap" label="value" value="key"/>
	                  </html:select></div>
	                <div class="col-sm-2">
	                  <c:choose>
	                  	<c:when test="${attendanceEntryForm.periodMandatory == 'yes'}">
	                  		<div id="perioddiv"><span class='MandatoryMark'style="color:red;">*</span><b><bean:message key="knowledgepro.attendanceentry.period"/></b>:</div>
	                  	</c:when>
	                  	<c:otherwise>
	                  	    <div id="perioddiv"><b><bean:message key="knowledgepro.attendanceentry.period"/></b>:</div>
	                  	</c:otherwise>
	                  </c:choose>
	                  </div>
	                   <div class="col-sm-2">
	                   <html:select name="attendanceEntryForm" styleId="periods" property="periods" size="5"  multiple="multiple" styleClass="form-control" disabled="true">
	                   <html:optionsCollection name="attendanceEntryForm" property="periodMap" label="value" value="key"/>
	                  </html:select>
	                  </div>
	               </div>
	               </div>
	            <div class="form-group">
                 <div class="col-sm-12">
                    <div class="col-sm-2">
	                  <c:choose>
	                  	<c:when test="${attendanceEntryForm.subjectMandatory == 'yes'}">
	                  		<div id="subjectdiv" ><span class='MandatoryMark'style="color:red;">*</span><b><bean:message key="knowledgepro.attendanceentry.subject"/></b>:</div>
	                    </c:when>
	                    <c:otherwise>
	                    	<div id="subjectdiv" ><b><bean:message key="knowledgepro.attendanceentry.subject"/></b>:</div>
	                    </c:otherwise>
	                  </c:choose>
	                 </div>
	                   <div class="col-sm-4"><label>
	                    <html:select name="attendanceEntryForm" property="subjectId" styleId="subjectId"  styleClass="form-control" onchange="getBatches(this.value)" disabled="true">
	                      <html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
	                      <html:optionsCollection name="attendanceEntryForm" property="subjectMap" label="value" value="key"/>
	                    </html:select>
	                  </label>
	                  </div>
	                    <div style="opacity: 0" class="col-sm-4"><span class='MandatoryMark'style="color:red;">*</span><b><bean:message key="knowledgepro.attendanceentry.hoursheld"/></b>:</div>
	          <div class="col-sm-2">
	               <html:text style="opacity: 0" name="attendanceEntryForm" styleId="hoursHeld" property="hoursHeld" styleClass="form-control" onkeypress="return isNumberKey(event)" maxlength="4" onblur="validNumber(this)"  disabled="true"/>
	               </div>
	               
	                  </div>
	                  </div>
	                   <div class="form-group">
                 <div class="col-sm-12">
                  <div class="col-sm-2">
	                   <c:choose>
	                  	<c:when test="${attendanceEntryForm.batchMandatory == 'yes'}">
	                  		<div id="batchdiv" ><%-- <span class='MandatoryMark'style="color:red;">*</span><b><bean:message key="knowledgepro.attendanceentry.batchname"/></b>: --%></div>
	                    </c:when>
	                    <c:otherwise>
	                    	<div id="batchdiv"> <%-- <b><bean:message key="knowledgepro.attendanceentry.batchname"/></b>: --%></div>
	                    </c:otherwise>
	                   </c:choose>
	                 </div>
	                  <div class="col-sm-2">
	                  <html:select style="opacity: 0" name="attendanceEntryForm" property="batchId" styleId="batchId" styleClass="form-control" disabled="true">
	                    <html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
	                    <html:optionsCollection name="attendanceEntryForm" property="batchMap" label="value" value="key"/>
	                  </html:select>
                   </div>
	                <div class="col-sm-2" style="display: none;">
	              <div class="col-sm-2">
	                  <c:choose>
	                  	<c:when test="${attendanceEntryForm.activityTypeMandatory == 'yes'}">
	                  		<div id="activitydiv"><span class='MandatoryMark'>*</span><b><bean:message key="knowledgepro.attendanceentry.activitytype"/></b>:</div>
	                    </c:when>
	                    <c:otherwise>
	                    	<div id="activitydiv"><b><bean:message key="knowledgepro.attendanceentry.activitytype"/></b>:</div>
	                    </c:otherwise>
	                   </c:choose>
	                  </div>
	                 <div class="col-sm-2">
	                  <html:select name="attendanceEntryForm" property="activityId" styleId="activityId" styleClass="form-control" onchange="getBatches()" disabled="true">
	                    <html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
	                    		<html:optionsCollection name="attendanceEntryForm" property="activityMap" label="value" value="key"/>
	                    </html:select>
	                   </div>
	                  </div>
	                  </div>
	                </div>
	                </div>
	                </div>  
               </div>
             
     
        <div class="form-group">
                 <div class="col-sm-12"> 
            <div class="col-sm-8" style=" color:blue;">
            <b>Click on the cell to view details</b></div>
        <div class="col-sm-4">
         <label ><span style="background-color: green;"></span><input type='checkbox'></label> <b>is for Present</b>
        &nbsp; &nbsp;
        <label ><span style="background-color: red;"></span><input type='checkbox'></label><b> is for Absent</b>
       &nbsp; &nbsp;
       <label ><span style="background-color: #EEE8AA;"></span><input type='checkbox' disabled="disabled"></label> is not Marked
       	&nbsp; &nbsp;
        <br/>
       </div>
       </div>
       </div>
       <div class="col-sm-12"  style="border-style: solid;border-color: #29953b;">
     <div class="col-sm-12">
                   <div class="table-responsive">
                  <table class="table table-bordered " width="100%" >
                      <tr  style="background-color: #618685; color:white;">   
                      <td align="center" style="width: 196px;">
						<div align="center" ><b><bean:message key="knowledgepro.slno" /></b>
						</div>
					 </td>
                        <td align="center"  style="width: 635px;"><b><bean:message key="knowledgepro.attendance.studentname"/></b></td>
                        <!-- 
                         <td align="center" width="150">
                        		<b>Reg No</b>
                        </td> -->
                        <td  align="center" width="150">
                        		<b>Roll No</b>
                        </td>
                     </tr>
                      <tr>
                      <td colspan="3">
                    <div class="table-responsive">
                  <table class="table table-bordered table-striped" width="100%" >               
                      <nested:iterate id="student" property="studentList" name="attendanceEntryForm" indexId="count2">
					   <c:choose>
								<c:when test="${count2%2 == 0}">
									<tr>
								</c:when>
									<c:otherwise>
									<tr >
								</c:otherwise>
					 </c:choose>
					 <td align="center"  style="width:233px;" ><c:out value="${count2+1}"/></td>
                        <td style=" width: 801px;" height="50"  align="center"><nested:write name="student" property="studentName"/>
                        <div>	
                        <br>
                       				<c:forEach items="${student.periodTOs}" var="per">
					            			<bean:write name="per" property="periodName"/>
					            			<c:choose>
              									<c:when test="${per.hoursTaken == 'true' }">
						                       		<c:choose>
						                       			<c:when test="${per.tempChecked == 'true'}">
						                      					<label ><span style="background-color: green;"></span><input type='checkbox' disabled="disabled"></label>
						                        		</c:when>
						                        		<c:otherwise>
						                       					<label ><span style="background-color: red;"></span><input type='checkbox' disabled="disabled"></label>
						                        		</c:otherwise>
						                        	</c:choose>
												 </c:when>
												<c:otherwise>
													<label ><span style="background-color: #EEE8AA"></span><input type='checkbox' disabled="disabled"></label>
												</c:otherwise>
											</c:choose>
            						</c:forEach>
                       			</div>
                        </td>
                        <%-- <td height="25" align="center" style="width:171px;">
                        		<nested:write name="student" property="registerNo"/>
                        </td> --%>
                        <td  align="center"style="width: 171px;">
                        		<nested:write name="student" property="rollNo"/>
                        </td>
                     </nested:iterate>                     
                       </table>
                      </div>
                      </td>
                      </tr>
                  </table>
                </div>
              </div>
              </div>
              </div>     
          <div class="form-group">
            <div class="col-sm-12 text-center"> 
            <html:button property="" styleClass="btn btn-danger" value="Cancel" onclick="viewAttendanceFirstPage()"></html:button>
        </div>
        </div>
</html:form>