<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<script type="text/javascript">
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

function modifyAttendanceFirstPage() {
	document.getElementById("method").value ="loadModifyFirstPage";
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

</script>

<html:form action="/AttendanceEntryForStaff" method="post">

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

<table width="99%" border="0">
  
  <tr>
    <td><span class="heading"><bean:message key="knowledgepro.attendanceentry.attendance"/> <span class="Bredcrumbs">&gt;&gt; <bean:message key="knowledgepro.attendanceentry.modifyattendance"/></span></span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="heading_white" ><bean:message key="knowledgepro.attendanceentry.modifyattendance"/></td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td>
        	<div align="right" style="color:red" class="heading"> <span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields"/></span></div>
	        <div id="errorMessage">
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
	                  <td height="25" class="row-odd" >
	                  <div align="right"><span class='MandatoryMark'>*</span><bean:message key="knowledgepro.attendanceentry.type"/>:</div>
	                  </td>
	                  <td class="row-even" >
	                  <html:select property="attendanceTypeId" styleId="attendanceTypeId" name="attendanceEntryForm" styleClass="combo" onchange="getMandatory(this.value)">
	                  	<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
	                  	<html:optionsCollection name="attendanceEntryForm" property="attendanceTypes" label="value" value="key"/>
	                  </html:select>
	                  </td>
	                  <td class="row-odd" ><div align="right"><span class='MandatoryMark'>*</span><bean:message key="knowledgepro.attendanceentry.date"/>:</div></td>
	                  <td class="row-even" >
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
	                  <td class="row-odd"><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.fee.academicyear"/>:</div></td>
			          <td class="row-even" align="left">
			                           <input type="hidden" id="tempyear" name="tempyear" value="<bean:write name="attendanceEntryForm" property="year"/>"/>
			                           <html:select property="year" styleId="academicYear" name="attendanceEntryForm" styleClass="combo" onchange="getClasses(this.value)">
	                       	   				 <html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
	                       	   				<cms:renderAcademicYear></cms:renderAcademicYear>
	                       			   </html:select>
			        	</td>
	                </tr>
	                <tr >
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
	                  <td class="row-even" >
	                  <html:select name="attendanceEntryForm" styleId="classes" property="classes" size="5" style="width:120px" multiple="multiple" onchange="getSubjectsPeriodsBatchForClass(),getBatches()" disabled="true">
	       		    		<html:optionsCollection name="attendanceEntryForm" property="classMap" label="value" value="key"/>
	                  </html:select></td>
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
	                  <td  class="row-even" >
	                  <html:select name="attendanceEntryForm" styleId="teachers" property="teachers" size="5" style="width:120px" multiple="multiple">
	                    	<html:optionsCollection name="attendanceEntryForm" property="teachersMap" label="value" value="key"/>
	                  </html:select></td>
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
	                    <html:select name="attendanceEntryForm" property="subjectId" styleId="subjectId" styleClass="combo" onchange="getBatches(this.value)">
	                      <html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
	                      <html:optionsCollection name="attendanceEntryForm" property="subjectMap" label="value" value="key"/>
	                    </html:select>
	                  </label></td>
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
	                  <html:select name="attendanceEntryForm" property="activityId" styleId="activityId" styleClass="combo" onchange="getBatches()">
	                    <html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
	                    		<html:optionsCollection name="attendanceEntryForm" property="activityMap" label="value" value="key"/>
	                    </html:select></td>
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
	               <td height="25" class="row-odd" ><div align="right"><span class='MandatoryMark'>*</span><bean:message key="knowledgepro.attendanceentry.hoursheld"/>:</div></td>
	               <td class="row-even" >
	               <html:text name="attendanceEntryForm" styleId="hoursHeld" property="hoursHeld" styleClass="TextBox" onkeypress="return isNumberKey(event)" maxlength="4" onblur="validNumber(this)" />
	               </td>
                   <td class="row-odd" >&nbsp;
                   </td>
                   <td class="row-even" >&nbsp;
                   </td>
                   <td class="row-odd" >&nbsp;
                   </td>
                   <td class="row-even" >&nbsp;
                   </td>
                  
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
        <td class="heading">&nbsp;</td>
        <td valign="top" background="images/Tright_3_3.gif" ></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td class="heading"><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td><table width="99%" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                  <td ><img src="images/01.gif" width="5" height="5"></td>
                  <td width="914" background="images/02.gif"></td>
                  <td><img src="images/03.gif" width="5" height="5"></td>
                </tr>
                <tr>
                  <td width="5"  background="images/left.gif"></td>
                  <td height="54" valign="top">
                  
                  <table width="100%" cellspacing="1" cellpadding="2">
                      <tr >
                        <td width="160" class="row-odd" align="center">
                        <c:choose>
                        	<c:when test="${attendanceEntryForm.regNoDisplay == true}">
                        		<bean:message key="knowledgepro.attendanceentry.regno"/>
                        	</c:when>
                        	<c:otherwise>
                        		<bean:message key="knowledgepro.attendanceentry.rollno"/>
                        	</c:otherwise>
                        </c:choose>
                        </td>
                        <td width="135" class="row-odd" align="center"><bean:message key="knowledgepro.attendance.studentname"/></td>
                        <td width="40" height="25" class="row-odd" align="center"><bean:message key="knowledgepro.attendance.markabsent"/></td>
                      </tr>
                      <nested:iterate id="student" property="studentList" name="attendanceEntryForm" indexId="count">
                      
                      <c:if test="${count < attendanceEntryForm.halfLength}">
					   <c:choose>
								<c:when test="${count%2 == 0}">
									<tr class="row-even">
								</c:when>
									<c:otherwise>
									<tr class="row-white">
								</c:otherwise>
					 </c:choose>
                        <td width="193" align="center">
                        <c:choose>
                        	<c:when test="${attendanceEntryForm.regNoDisplay == true}">
                        		<nested:write name="student" property="registerNo"/>
                        	</c:when>
                        	<c:otherwise>
                        		<nested:write name="student" property="rollNo"/>
                        	</c:otherwise>
                        </c:choose>
                        </td>
                        
                        <td width="212" align="center"><nested:write name="student" property="studentName"/></td>
                        <td height="25" align="center" align="center">
                        
                        <input
							type="hidden"
							name="studentList[<c:out value='${count}'/>].tempChecked"
							id="hidden_<c:out value='${count}'/>"
							value="<nested:write name='student' property='tempChecked'/>" />
                        
                        <c:choose>
                        <c:when test="${student.coCurricularLeavePresent == true || student.studentLeave == true}">
                        <input
							type="checkbox"
							name="studentList[<c:out value='${count}'/>].checked"
							id="<c:out value='${count}'/>" disabled="disabled"/>
                        </c:when>
                        <c:otherwise>
                        	<input
							type="checkbox"
							name="studentList[<c:out value='${count}'/>].checked"
							id="<c:out value='${count}'/>" />
                        </c:otherwise>
                        </c:choose>
                        
                        
						<script type="text/javascript">
							var studentId = document.getElementById("hidden_<c:out value='${count}'/>").value;
							if(studentId == "true") {
								document.getElementById("<c:out value='${count}'/>").checked = true;
							}		
						</script>
					   </td>
                      </tr>
                      </c:if>
                      </nested:iterate>
                  </table>
                  
                  </td>
                  <td  background="images/right.gif" width="5" height="54"></td>
                </tr>
                <tr>
                  <td height="5"><img src="images/04.gif" width="5" height="5"></td>
                  <td background="images/05.gif"></td>
                  <td><img src="images/06.gif" ></td>
                </tr>
            </table></td>
            <td><table width="99%" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                  <td ><img src="images/01.gif" width="5" height="5"></td>
                  <td width="914" background="images/02.gif"></td>
                  <td><img src="images/03.gif" width="5" height="5"></td>
                </tr>
                <tr>
                  <td width="5"  background="images/left.gif"></td>
                  <td height="54" valign="top">
              
                  <table width="100%" cellspacing="1" cellpadding="2">
                      <tr >
                        <td width="160" class="row-odd" align="center">
                        <c:choose>
                        	<c:when test="${attendanceEntryForm.regNoDisplay == true}">
                        		<bean:message key="knowledgepro.attendanceentry.regno"/>
                        	</c:when>
                        	<c:otherwise>
                        		<bean:message key="knowledgepro.attendanceentry.rollno"/>
                        	</c:otherwise>
                        </c:choose>
                        </td>
                        <td width="135" class="row-odd" align="center"><bean:message key="knowledgepro.attendance.studentname"/></td>
                        <td width="40" height="25" class="row-odd" align="center"><bean:message key="knowledgepro.attendance.markabsent"/></td>
                      </tr>
                      <c:set var="c" value="0"/>
                      <nested:iterate id="student" property="studentList" name="attendanceEntryForm" indexId="count">
                      <c:set var="c" value="${c + 1}"/>
                      <c:if test="${count >= attendanceEntryForm.halfLength}">
                      <c:choose>
								<c:when test="${count%2 == 0}">
									<tr class="row-even">
								</c:when>
									<c:otherwise>
									<tr class="row-white">
								</c:otherwise>
					 </c:choose>
                        <td width="193" align="center">
                        <c:choose>
                        	<c:when test="${attendanceEntryForm.regNoDisplay == true}">
                        		<nested:write name="student" property="registerNo"/>
                        	</c:when>
                        	<c:otherwise>
                        		<nested:write name="student" property="rollNo"/>
                        	</c:otherwise>
                        </c:choose>
                        </td>
                        <td width="212" align="center"><nested:write name="student" property="studentName"/></td>
                        <td height="25" align="center">
                        
                        <input
							type="hidden"
							name="studentList[<c:out value='${count}'/>].tempChecked"
							id="hidden_<c:out value='${count}'/>"
							value="<nested:write name='student' property='tempChecked'/>" />
                        
                        <c:choose>
                        <c:when test="${student.coCurricularLeavePresent == true || student.studentLeave == true}">
                        <input
							type="checkbox"
							name="studentList[<c:out value='${count}'/>].checked"
							id="<c:out value='${count}'/>" disabled="disabled"/>
                        </c:when>
                        <c:otherwise>
                        	<input
							type="checkbox"
							name="studentList[<c:out value='${count}'/>].checked"
							id="<c:out value='${count}'/>" />
                        </c:otherwise>
                        </c:choose>
                        
						<script type="text/javascript">
							var studentId1 = document.getElementById("hidden_<c:out value='${count}'/>").value;
							if(studentId1 == "true") {
								document.getElementById("<c:out value='${count}'/>").checked = true;
							}		
						</script>
					   </td>
                      </tr>
                      </c:if>
                      </nested:iterate>
                      <c:if test="${(c % 2) != 0}" >
                      <tr  class="row-white">
                        <td width="193"  >&nbsp;</td>
                        <td width="212" >&nbsp;</td>
                        <td height="25" align="center" >&nbsp;
                      </td>
                      </tr>
                      </c:if>
                  </table>
                  
                  </td>
                  <td  background="images/right.gif" width="5" height="54"></td>
                </tr>
                <tr>
                  <td height="5"><img src="images/04.gif" width="5" height="5"></td>
                  <td background="images/05.gif"></td>
                  <td><img src="images/06.gif" ></td>
                </tr>
            </table></td>
          </tr>
        </table></td>
        <td valign="top" background="images/Tright_3_3.gif" ></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td class="heading"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
          <tr><td style="height: 10px" align="center" colspan="1"></td></tr>
          <tr>
            <td height="35" align="center">
            <html:button property="" styleClass="formbutton" value="Update" onclick="updateData()"></html:button>&nbsp;&nbsp;&nbsp;&nbsp;
            <html:button property="" styleClass="formbutton" value="Cancel" onclick="modifyAttendanceFirstPage()"></html:button>
            </td>			
          </tr>
          <tr><td style="height: 10px" align="center" colspan="1"></td></tr>          
        </table>
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