<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@page import="com.kp.cms.constants.CMSConstants"%>
   <script type="text/javascript" src="js/auditorium/jquery-1.9.1.min.js"></script>
<link type="text/css" href="jquery/css/ui-lightness/jquery-ui-1.8.18.custom.css" rel="stylesheet" />
<script src="jquery/js/jquery-ui-1.10.0.custom.min.js" type="text/javascript"></script>
<style type="text/css">
body {
	margin-left: 0px;
	margin-top: 5px;
	margin-right: 0px;
	margin-bottom: 0px;
}
</style>
<style type="text/css">
.ui-datepicker {
	background: #6B8E23;
	border: 1px solid #555;
	color: #6B8E23;
	font-family: Arial;
	font-size: 11px;
	margin-left: 10px
}
</style>   
    
<script type="text/javascript">
	
	function getRegisterNos(noOfStudents,columnName){
		var examType =  document.getElementById("examType").value;
		var campusId = document.getElementById("campusId").value;
		var examId = document.getElementById("examid").value;
		var campusId = document.getElementById("campusId").value;
		document.getElementById("displyColumn").value= columnName;
		var classId;
		var subjectId;
		var oddOrEven;
		if(columnName == 'col1'){
			 classId = document.getElementById("classId").value;
			 oddOrEven='odd';
		}else if(columnName == 'col2'){
			 classId = document.getElementById("classId1").value;
			 oddOrEven='even';
		}else if(columnName == 'subject1'){
			 subjectId = document.getElementById("subjectId").value;
			 oddOrEven='odd';
		}else if(columnName == 'subject2'){
			 subjectId = document.getElementById("subjectId1").value;
			 oddOrEven='even';
		}
		var msg ="";
		if(parseInt(noOfStudents)>0){
			
		var availableSeats ;
		if(classId == ''){
			msg = msg + " Class is Required.";
			document.getElementById("errorMessages").style.display="block";
			document.getElementById("errorMessages").innerHTML=msg;
			return;
		}else if(subjectId == ''){
			msg = msg + " Subject is Required.";
			document.getElementById("errorMessages").style.display="block";
			document.getElementById("errorMessages").innerHTML=msg;
			return;
		}
		var remainingStuCount ;
		if(columnName == 'col1' || columnName == 'col2'){
			if(oddOrEven == 'odd'){
				 remainingStuCount = parseInt(document.getElementById("oddClassOrSubjectCount").value); 
				if(parseInt(noOfStudents)<=remainingStuCount){
					availableSeats =parseInt(document.getElementById("colOneAvailableSeats").value);
					if(document.getElementById("allotAllCol").value== "off"){
						if(parseInt(noOfStudents)>availableSeats){
							msg = msg + " No. of Students  are  exceeded the Capacity of the Column 1.";
							document.getElementById("fromRegNo").value ='';
							document.getElementById("toRegNo").value='';
						}
					}else if(document.getElementById("allotAllCol").value== "on"){
						var total = parseInt(document.getElementById("tempAvailableSeats").value);
						if(parseInt(noOfStudents)>total){
							msg = msg + " No. of Students  are  exceeded the Capacity of the Column 1.";
							document.getElementById("fromRegNo").value='';
							document.getElementById("toRegNo").value='';
						}
					}
				}else {
						msg = msg + "No.of Studens should not be greater than Remaining Students of Class of the column 1";
				}
			}else if(oddOrEven == 'even'){
				remainingStuCount = parseInt(document.getElementById("evenClassOrSubjectCount").value); 
				if(parseInt(noOfStudents)<=remainingStuCount){
					availableSeats =parseInt(document.getElementById("colTwoAvailableSeats").value);
					if(parseInt(noOfStudents)>availableSeats){
						msg = msg + " No. of Students  are  exceeded the Capacity of the Column 2.";
						document.getElementById("fromRegNo1").value='';
						document.getElementById("toRegNo1").value='';
					}
				}else {
						msg = msg + "No.of Studens should not be greater than Remaining Students of Class of the column 2";
				}
			}
			subjectId="";
		}else if(columnName == 'subject1' || columnName == 'subject2'){
			if(oddOrEven == 'odd'){
				remainingStuCount = parseInt(document.getElementById("oddClassOrSubjectCount").value); 
				if(parseInt(noOfStudents)<=remainingStuCount){
					availableSeats =parseInt(document.getElementById("colOneAvailableSeats").value);
					if(document.getElementById("allotAllCol").value== "off"){
						if(parseInt(noOfStudents)>availableSeats){
							msg = msg + " No. of Students  are  exceeded the Capacity of the Column 1.";
							document.getElementById("fromRegNo2").value='';
							document.getElementById("toRegNo2").value='';
						}
					}else if(document.getElementById("allotAllCol").value== "on"){
							var total = parseInt(document.getElementById("tempAvailableSeats").value);
							if(parseInt(noOfStudents)>total){
								msg = msg + " No. of Students  are  exceeded the Capacity of the Column 1.";
								document.getElementById("fromRegNo2").value='';
								document.getElementById("toRegNo2").value='';
							}
					}
				}else {
						msg = msg + "No.of Studens should not be greater than Remaining Students of Subject of the column 1";
				}
			}else if(oddOrEven == 'even'){
				remainingStuCount = parseInt(document.getElementById("evenClassOrSubjectCount").value); 
				if(parseInt(noOfStudents)<=remainingStuCount){
					availableSeats =parseInt(document.getElementById("colTwoAvailableSeats").value);
					if(parseInt(noOfStudents)>availableSeats){
						msg = msg + " No. of Students  are  exceeded the Capacity of the Column 2.";
						document.getElementById("fromRegNo3").value='';
						document.getElementById("toRegNo3").value='';
					}
				}else {
						msg = msg + "No.of Studens should not be greater than Remaining Students of Subject of the column 2";
				}
			}
			classId = "";
		}
		}else{
					msg = msg + "No. of Students should be greater than Zero";
		}
		if(msg == ''){
			document.getElementById("errorMessages").style.display="none";
			var args =  "method=getRegisterNosRange&noOfStudents="+noOfStudents+"&propertyName="+columnName+"&propertyName1="+oddOrEven+"&examType="+examType+"&campusId="+campusId+"&examid="+examId+"&classId="+classId+"&subjectId="+subjectId+"&campusId="+campusId;
			var url = "examRoomAllotmentCjc.do";
			requestOperationProgram(url, args, displayName);
		}else {
			document.getElementById("errorMessages").style.display="block";
			document.getElementById("errorMessages").innerHTML=msg;
		}
	}
	function displayName(req) {
		var responseObj = req.responseXML.documentElement;
		var childNodes = responseObj.childNodes;
		var items = responseObj.getElementsByTagName("option");
		var label, value;
		var displyColumn = document.getElementById("displyColumn").value;
		var msg ="";
		for ( var i = 0; i < items.length; i++) {
			if(items[i].getElementsByTagName("optionlabel") [0].firstChild!= null ){
			label = items[i].getElementsByTagName("optionlabel") [0].firstChild.nodeValue;
			value = items[i].getElementsByTagName("optionvalue") [0].firstChild.nodeValue;
			if(displyColumn == 'col1'){
					document.getElementById("fromRegNo").value= value;
					document.getElementById("toRegNo").value=label;
			}else if(displyColumn == 'col2'){
					document.getElementById("fromRegNo1").value= value;
					document.getElementById("toRegNo1").value=label;
			}else if(displyColumn == 'subject1'){
					document.getElementById("fromRegNo2").value= value;
					document.getElementById("toRegNo2").value=label;
			}else if(displyColumn == 'subject2'){
					document.getElementById("fromRegNo3").value= value;
					document.getElementById("toRegNo3").value=label;
			}
		}	else if(displyColumn == 'col1' || displyColumn == 'col2'){
			msg = "Alloted has been Completed for Selected Class.";
			alert(msg);
		}  else if(displyColumn == 'subject1' || displyColumn == 'subject2'){
			msg = "Alloted has been Completed for Selected Subject.";
			alert(msg);
		}
		}
	}
	function getAlloted(oddOrEven,value){
		var classOrSubject;
		var noOfStudents;
		var fromRegNo;
		var toRegNo;
		var msg="";
		if(oddOrEven=='odd'){
			if(value=='Subject'){
				classOrSubject = document.getElementById("subjectId").value ;
				noOfStudents = document.getElementById("noOfStudents2").value ;
				fromRegNo= document.getElementById("fromRegNo2").value ;
				toRegNo= document.getElementById("toRegNo2").value ;
			}else if(value=='Classes'){
				classOrSubject = document.getElementById("classId").value ;
				noOfStudents = document.getElementById("noOfStudents").value ;
				fromRegNo= document.getElementById("fromRegNo").value ;
				toRegNo= document.getElementById("toRegNo").value ;
			}
		}else if(oddOrEven == 'even'){
			if(value=='Subject'){
				classOrSubject = document.getElementById("subjectId1").value ;
				noOfStudents = document.getElementById("noOfStudents3").value ;
				fromRegNo= document.getElementById("fromRegNo3").value ;
				toRegNo= document.getElementById("toRegNo3").value ;
			}else if(value=='Classes'){
				classOrSubject = document.getElementById("classId1").value ;
				noOfStudents = document.getElementById("noOfStudents1").value ;
				fromRegNo= document.getElementById("fromRegNo1").value ;
				toRegNo= document.getElementById("toRegNo1").value ;
			}
		}
		if(classOrSubject ==''){
			msg = msg + " Subject is Required"+"<br/>";
		}
		if(noOfStudents == ''){
			msg = msg + " No. of Students is Required"+"<br/>";
		}
		if(fromRegNo == '' ){
			msg = msg + " From Register No. is Required"+"<br/>";
		}
		if(toRegNo == '' ){
			msg = msg + " To Register No. is Required"+"<br/>";
		}
		if(msg != ''){
			document.getElementById("errorMessages").style.display="block";
			document.getElementById("errorMessages").innerHTML=msg;
		}else {
			document.getElementById("propertyName").value = oddOrEven;
			document.getElementById("tempField").value = value;
			document.getElementById("method").value = "studentsAllotment";
			document.examRoomAllotmentForCJCForm.submit();
		}
		
	}
	function clearAllotedList(oddOrEven,value){
		document.getElementById("propertyName").value = oddOrEven;
		document.getElementById("tempField").value = value;
		document.getElementById("method").value = "clearStudentsAllotment";
	}
	function submitAllotmentDetails(){
		document.getElementById("method").value = "submitStudentsAllotment";
	}
	function goTOFirstPage(){
		document.location.href = "examRoomAllotmentCjc.do?method=initExamRoomAllotmentForCJC";
	}
	function resetFields(oddOrEven,value){
		var examType =  document.getElementById("examType").value;
		var campusId = document.getElementById("campusId").value;
		var examId = document.getElementById("examid").value;
		document.getElementById("displyColumn").value= oddOrEven;
		document.getElementById("displyColumn1").value= value;
		var classId = "";
		var subjectId= "";
		if(oddOrEven=='odd'){
			if(value=='Subject'){
				document.getElementById("noOfStudents2").value='' ;
				document.getElementById("fromRegNo2").value='' ;
				document.getElementById("toRegNo2").value='' ;
				subjectId = document.getElementById("subjectId").value ;
			}else if(value=='Classes'){
				 document.getElementById("noOfStudents").value ='' ;
				 document.getElementById("fromRegNo").value ='' ;
				 document.getElementById("toRegNo").value='' ;
				 classId =document.getElementById("classId").value ;
			}
		}else if(oddOrEven == 'even'){
			if(value=='Subject'){
				document.getElementById("noOfStudents3").value='' ;
				document.getElementById("fromRegNo3").value='' ;
				document.getElementById("toRegNo3").value='' ;
				subjectId = document.getElementById("subjectId1").value ;
			}else if(value=='Classes'){
				document.getElementById("noOfStudents1").value='' ;
				document.getElementById("fromRegNo1").value='' ;
				document.getElementById("toRegNo1").value='' ;
				classId =document.getElementById("classId1").value ;
			}
		}
		var args =  "method=getRemainingCount&propertyName="+value+"&examType="+examType+"&campusId="+campusId+"&examid="+examId+"&classId="+classId+"&subjectId="+subjectId+"&campusId="+campusId;
		var url = "examRoomAllotmentCjc.do";
		requestOperationProgram(url, args, remainingCount);
	}
	function remainingCount(req) {
		var responseObj = req.responseXML.documentElement;
		var childNodes = responseObj.childNodes;
		var items = responseObj.getElementsByTagName("option");
		var label, value;
		var oddOrEven = document.getElementById("displyColumn").value;
		var tempField = document.getElementById("displyColumn1").value;
		var msg ="";
		for ( var i = 0; i < items.length; i++) {
			if(items[i].getElementsByTagName("optionlabel") [0].firstChild!= null ){
			label = items[i].getElementsByTagName("optionlabel") [0].firstChild.nodeValue;
			value = items[i].getElementsByTagName("optionvalue") [0].firstChild.nodeValue;
			if(oddOrEven=='odd'){
				if(tempField=='Subject'){
					document.getElementById("oddClassOrSubjectCount").value= label;
				}else if(tempField=='Classes'){
					 document.getElementById("oddClassOrSubjectCount").value =label ;
				}
			}else if(oddOrEven == 'even'){
				if(tempField=='Subject'){
					document.getElementById("evenClassOrSubjectCount").value=label;
				}else if(tempField=='Classes'){
					document.getElementById("evenClassOrSubjectCount").value=label;
				}
			}
		}	
		}
	}
	function checkNumeric(event) {
		return false;
		}
	function isNumberKey(evt) {
		
		var charCode = (evt.which) ? evt.which : event.keyCode
				if((evt.ctrlKey || evt.keyCode == 86)  ){
					return true;
				}
	    if ((charCode > 45 && charCode < 58) || charCode == 8){ 
	        return true; 
	    } 
	    return false;  
	}
	function allotmentAll(obj){
		var isDateWise =document.getElementById("isDateWise").value;
		var msg = "";
		if(obj.checked){
			var colOneAllotedSeats = parseInt(document.getElementById("colOneAllotedSeats").value);
			var colTwoAllotedSeats = parseInt(document.getElementById("colTwoAllotedSeats").value);
			var totalAllotedSeats = colOneAllotedSeats + colTwoAllotedSeats;
			if(totalAllotedSeats>0){
				msg = msg + "Please Clear the Current Allotment before going to Check the Allot All Columns. ";
				document.getElementById("errorMessages").style.display="block";
				document.getElementById("errorMessages").innerHTML=msg;
				obj.checked = false;
				document.getElementById("allotAllCol").value= "off";
				return;
			}
			document.getElementById("allotAllCol").value= "on";
			if(isDateWise == 'Yes'){
				document.getElementById("subjectId1").disabled=true;
				document.getElementById("noOfStudents3").disabled=true;
				document.getElementById("fromRegNo3").disabled=true;
				document.getElementById("toRegNo3").disabled=true;
				document.getElementById("secondSubjectCol").style.display="none";
				document.getElementById("tempSecondColumn").style.display="block";
			}else if(isDateWise == 'No'){
				document.getElementById("classId1").disabled=true;
				document.getElementById("noOfStudents1").disabled=true;
				document.getElementById("fromRegNo1").disabled=true;
				document.getElementById("toRegNo1").disabled=true;
				document.getElementById("secondSubjectCol").style.display="none";
				document.getElementById("tempSecondColumn").style.display="block";
			}
		}else{
			var allotSeats = parseInt(document.getElementById("tempAllotedSeats").value);
			if(allotSeats>0){
				msg = msg + "Please Clear the Current Allotment before going to unCheck the Allot All Columns. ";
				document.getElementById("errorMessages").style.display="block";
				document.getElementById("errorMessages").innerHTML=msg;
				obj.checked = true;
				document.getElementById("allotAllCol").value= "on";
				return;
			}else{
				document.getElementById("allotAllCol").value= "off";
				if(isDateWise == 'Yes'){
					document.getElementById("subjectId1").disabled=false;
					document.getElementById("noOfStudents3").disabled=false;
					document.getElementById("fromRegNo3").disabled=false;
					document.getElementById("toRegNo3").disabled=false;
					document.getElementById("secondSubjectCol").style.display="block";
					document.getElementById("tempSecondColumn").style.display="none";
				}else if(isDateWise == 'No'){
					document.getElementById("classId1").disabled=false;
					document.getElementById("noOfStudents1").disabled=false;
					document.getElementById("fromRegNo1").disabled=false;
					document.getElementById("toRegNo1").disabled=false;
					document.getElementById("secondSubjectCol").style.display="block";
					document.getElementById("tempSecondColumn").style.display="none";
				}
			}
		}
	}
	function shows(obj,msg){
		document.getElementById("messageBox").style.top=obj.offsetTop;
		document.getElementById("messageBox").style.left=obj.offsetLeft+obj.offsetWidth+5;
		document.getElementById("contents").innerHTML=msg;
		document.getElementById("messageBox").style.display="block";
		}
	function hides(){
		document.getElementById("messageBox").style.display="none";
	}
</script>
<style type="text/css">
.CSSTableGenerator {
	margin:0px;padding:0px;
	width:100%;
	box-shadow: 10px 10px 5px #888888;
	border:1px solid #c6c652;
	
	-moz-border-radius-bottomleft:0px;
	-webkit-border-bottom-left-radius:0px;
	border-bottom-left-radius:0px;
	
	-moz-border-radius-bottomright:0px;
	-webkit-border-bottom-right-radius:0px;
	border-bottom-right-radius:0px;
	
	-moz-border-radius-topright:0px;
	-webkit-border-top-right-radius:0px;
	border-top-right-radius:0px;
	
	-moz-border-radius-topleft:0px;
	-webkit-border-top-left-radius:0px;
	border-top-left-radius:0px;
}.CSSTableGenerator table{
	width:100%;
	height:100%;
	margin:0px;padding:0px;
}
.CSSTableGenerator td{
	vertical-align:middle;
	border:1px solid #c6c652;
	border-width:0px 1px 1px 0px;
	text-align:left;
	padding:7px;
	font-size:10px;
	font-family:Arial;
	font-weight:normal;
	color:#000000;
}
.CSSTableGenerator tr:first-child td{
		background:-o-linear-gradient(bottom, #acac39 5%, #c6c652 100%);	background:-webkit-gradient( linear, left top, left bottom, color-stop(0.05, #acac39), color-stop(1, #c6c652) );
	background:-moz-linear-gradient( center top, #acac39 5%, #c6c652 100% );
	filter:progid:DXImageTransform.Microsoft.gradient(startColorstr="#acac39", endColorstr="#c6c652");	background: -o-linear-gradient(top,#acac39,c6c652);

	background-color:#acac39;
	border:0px solid #c6c652;
	text-align:center;
	border-width:0px 0px 1px 1px;
	font-size:10px;
	font-family:Arial;
	font-weight:bold;
	color:#333333;
}
.CSSTableGenerator tr:first-child:hover td{
	background:-o-linear-gradient(bottom, #acac39 5%, #c6c652 100%);	background:-webkit-gradient( linear, left top, left bottom, color-stop(0.05, #acac39), color-stop(1, #c6c652) );
	background:-moz-linear-gradient( center top, #acac39 5%, #c6c652 100% );
	filter:progid:DXImageTransform.Microsoft.gradient(startColorstr="#acac39", endColorstr="#c6c652");	background: -o-linear-gradient(top,#acac39,c6c652);
	background-color:#acac39;
}
</style>
<html:form action="/examRoomAllotmentCjc" method="post">
	<html:hidden property="formName" value="examRoomAllotmentForCJCForm" />
	<html:hidden property="pageType" value="1"/>
    <html:hidden property="method" styleId="method" value=""/>
    <html:hidden property="propertyName" styleId="propertyName" value="examRoomAllotmentForCJCForm"/>
    <input type="hidden" id="displyColumn" name="examRoomAllotmentForCJCForm"/>
    <input type="hidden" id="displyColumn1" name="examRoomAllotmentForCJCForm"/>
    <html:hidden property="academicYear" styleId="academicYear" name="examRoomAllotmentForCJCForm"/>
    <html:hidden property="examType" styleId="examType" name="examRoomAllotmentForCJCForm"/>
    <html:hidden property="examid" styleId="examid" name="examRoomAllotmentForCJCForm"/>
    <html:hidden property="campusId" styleId="campusId" name="examRoomAllotmentForCJCForm"/>
    <html:hidden property="roomId" styleId="roomId" name="examRoomAllotmentForCJCForm"/>
    <html:hidden property="campusId" styleId="campusId" name="examRoomAllotmentForCJCForm"/>
    <html:hidden property="isDateWise" styleId="isDateWise" name="examRoomAllotmentForCJCForm"/>
    <html:hidden property="allottedDate" styleId="allottedDate" name="examRoomAllotmentForCJCForm"/>
    <html:hidden property="sessionId" styleId="sessionId" name="examRoomAllotmentForCJCForm"/>
    <html:hidden property="cycleId" styleId="cycleId" name="examRoomAllotmentForCJCForm"/>
    <html:hidden property="tempField" styleId="tempField" name="examRoomAllotmentForCJCForm"/>
    <html:hidden property="colOneAvailableSeats" styleId="colOneAvailableSeats" name="examRoomAllotmentForCJCForm"/>
    <html:hidden property="colTwoAvailableSeats" styleId="colTwoAvailableSeats" name="examRoomAllotmentForCJCForm"/>
    <html:hidden property="allotAllCol" styleId="allotAllCol" name="examRoomAllotmentForCJCForm"/>
    <html:hidden property="columnOneTotalSeats" styleId="columnOneTotalSeats" name="examRoomAllotmentForCJCForm"/>
    <html:hidden property="colOneAllotedSeats" styleId="colOneAllotedSeats" name="examRoomAllotmentForCJCForm"/>
    <html:hidden property="columnTwoTotalSeats" styleId="columnTwoTotalSeats" name="examRoomAllotmentForCJCForm"/>
    <html:hidden property="colTwoAllotedSeats" styleId="colTwoAllotedSeats" name="examRoomAllotmentForCJCForm"/>
    
     <html:hidden property="tempTotalSeats" styleId="tempTotalSeats" name="examRoomAllotmentForCJCForm"/>
    <html:hidden property="tempAllotedSeats" styleId="tempAllotedSeats" name="examRoomAllotmentForCJCForm"/>
    <html:hidden property="tempAvailableSeats" styleId="tempAvailableSeats" name="examRoomAllotmentForCJCForm"/>
	<table width="100%" border="0">
		<tr>
			<td><span class="Bredcrumbs"> <bean:message
				key="knowledgepro.exam.allotment" /> <span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.exam.allotment.room.allotment.status" /> &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> <bean:message
						key="knowledgepro.exam.allotment.room.allotment.status" /></strong></td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="200" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" cellspacing="1" cellpadding="2">
						<tr bgcolor="#FFFFFF">
							<td height="20" colspan="4">
							<div align="right"><FONT color="red"> <span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields"/></span></FONT></div>
							<font color="red" size="2"><div id="errorMessages"><FONT color="red"><html:errors /></FONT>
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
									<td>
									<table width="100%" cellspacing="1" cellpadding="2">
						<tr>
						    <td width="25%" height="25" class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.admin.year" /></div>
									</td>
									<td width="25%" height="25" class="row-even">
									<bean:write name="examRoomAllotmentForCJCForm" property="academicYear"/>
									</td>
							<td width="25%" height="25" class="row-odd">
                            	<div align="right"><bean:message
								key="knowledgepro.exam.examDefinition.examType" />:</div>
							</td>
							<td width="25%" class="row-even" align="left" colspan="2">
							<logic:equal value="M" property="examType" name="examRoomAllotmentForCJCForm">
								Mid Sem
							</logic:equal>
							<logic:equal value="E" property="examType" name="examRoomAllotmentForCJCForm">
								End Sem
							</logic:equal>
					        </td>
						</tr>
						<tr>
						 <td width="25%" height="25" class="row-odd">
                            <div align="right"><bean:message
								key="knowledgepro.exam.exam" />:</div>
							</td>
							<td width="25%" height="25" class="row-even">
                            <div align="left">
                            <bean:write name="examRoomAllotmentForCJCForm" property="examName"/>
                            </div>
							</td>
						<td width="25%" height="25" class="row-odd" >
                            <div align="right"><bean:message
								key="knowledgepro.ExamAllotment.Students.Class.group.Campus" />:</div>
							</td>
							<td width="25%" height="25" class="row-even" colspan="2">
                            <div align="left">
                            <bean:write name="examRoomAllotmentForCJCForm" property="campusName"/>
                            </div>
						</td>
						</tr>
						
						<tr>
							<td width="25%" height="25" class="row-odd">
                            <div align="right"><bean:message
								key="knowledgepro.hostel.roomno" />:</div>
							</td>
							<td width="25%" class="row-even" align="left" colspan="3">
							<bean:write name="examRoomAllotmentForCJCForm" property="roomName"/>
					        </td>
							
						</tr>
						<tr>
						<logic:equal value="Yes" property="isDateWise" name="examRoomAllotmentForCJCForm">
							<td width="25%" height="25" class="row-odd">
                            <div align="right">Date:</div>
							</td>
							<td width="25%" class="row-even" align="left" >
							<bean:write name="examRoomAllotmentForCJCForm" property="allottedDate"/>
					        </td>
					         <td width="25%" height="25" class="row-odd">
                            <div align="right">Session Name:</div>
							</td>
							<td width="25%" class="row-even" align="left" >
							<bean:write name="examRoomAllotmentForCJCForm" property="sessionName"/>
					        </td>
							</logic:equal>
						<logic:equal value="No" property="isDateWise" name="examRoomAllotmentForCJCForm">
							<td width="25%" height="25" class="row-odd">
                            <div align="right">Cycle:</div>
							</td>
							<td width="25%" class="row-even" align="left" colspan="3">
							<bean:write name="examRoomAllotmentForCJCForm" property="cycleName"/>
					        </td>
						</logic:equal>
						
						</tr>
						<% if(CMSConstants.LINK_FOR_CJC){ %>
						<tr>
							<td width="25%" height="25" class="row-odd">
                            <div align="right">Allot All Columns:</div>
							</td>
							<td width="25%" class="row-even" align="left" colspan="3">
							<html:checkbox property="allotAllCol" styleId="allotAllCol" name="examRoomAllotmentForCJCForm" onclick="allotmentAll(this)"
							onmouseover="shows(this,'If checked, the register numbers will be allotted for all columns which is displayed down')" onmouseout="hides()"></html:checkbox>
					       <div id="messageBox">
	                       <div id="contents"></div></div>
					        </td>
							
						</tr>
						<%} %>
							</table>
									</td>
									<td width="5" height="29" background="images/right.gif"></td>
								</tr>
								<tr>
									<td width="5" background="images/left.gif"></td>
									<td valign="top">
									<table width="100%" cellspacing="1" cellpadding="2">
						
						<logic:notEmpty name="examRoomAllotmentForCJCForm" property="roomAllotmentStatusTo" >
						<logic:equal value="Yes" property="isDateWise" name="examRoomAllotmentForCJCForm">
						<tr>
						 	<td width="17%" height="25" class="row-odd">
                            	<div align="center"><span class="Mandatory">*</span>Subject</div>
							</td>
							<td width="17%" height="25" class="row-odd">
                            	<div align="center"><span class="Mandatory"></span>Remaining Students of Subject</div>
							</td>
							<td width="17%" height="25" class="row-odd">
                            	<div align="center"><span class="Mandatory">*</span>No. of Students</div>
							</td>
							<td width="17%" height="25" class="row-odd">
                            	<div align="center"><span class="Mandatory">*</span>From Register No.</div>
							</td>
							<td width="17%" height="25" class="row-odd">
                            	<div align="center"><span class="Mandatory">*</span>To Register No.</div>
							</td>
							<td width="17%" height="25" class="row-odd">
							</td>
						</tr>
						<tr>
							<td width="17%" height="25" class="row-even">
                            <div align="center">
                            <html:select property="subjectId" name="examRoomAllotmentForCJCForm" styleId="subjectId" styleClass="comboMediumBig" onchange="resetFields('odd','Subject')">
                            <html:option value="">--Select--</html:option>
                            <c:if test="${examRoomAllotmentForCJCForm.subjectMap != null}">
							<html:optionsCollection name="examRoomAllotmentForCJCForm" property="subjectMap" label="value" value="key" />
							</c:if>
                            </html:select>
                            </div>
							</td>
							<td width="17%" height="25" class="row-even">
                            <div align="center">
                            <html:text property="oddClassOrSubjectCount" size="5" styleId="oddClassOrSubjectCount" name="examRoomAllotmentForCJCForm" onkeypress="return checkNumeric(event)"></html:text>
                            </div>
							</td>
							<td width="17%" height="25" class="row-even">
                            <div align="center">
                            <html:text property="noOfStudents2" styleId="noOfStudents2" name="examRoomAllotmentForCJCForm" onchange="getRegisterNos(this.value,'subject1')" onkeypress="return isNumberKey(event)"></html:text>
                            </div>
							</td>
							<td width="17%" height="25" class="row-even">
                            <div align="center">
                            <html:text property="fromRegNo2" styleId="fromRegNo2" name="examRoomAllotmentForCJCForm" onkeypress="return checkNumeric(event)"></html:text>
                            </div>
							</td>
							<td width="17%" height="25" class="row-even">
                            <div align="center">
                            <html:text property="toRegNo2" styleId="toRegNo2" name="examRoomAllotmentForCJCForm" onkeypress="return checkNumeric(event)"></html:text>
                            </div>
							</td>
							<td width="17%" height="25" class="row-even" align="center">
							<html:button property="" styleClass="formbutton"
												value="Allot"  styleId="Submit" onclick="getAlloted('odd','Subject')"></html:button> &nbsp;&nbsp;&nbsp;&nbsp;
							              <html:submit property="" styleClass="formbutton"
												value="Clear" onclick="clearAllotedList('odd','Subject')" styleId="reset"></html:submit>
							</td>
						</tr>
						<tr>
							<td width="17%" height="25" class="row-even">
                            <div align="center" >
                            <html:select property="subjectId1" name="examRoomAllotmentForCJCForm" styleId="subjectId1" styleClass="comboMediumBig" onchange="resetFields('even','Subject')" >
                            <html:option value="">--Select--</html:option>
                            <c:if test="${examRoomAllotmentForCJCForm.subjectMap != null}">
							<html:optionsCollection name="examRoomAllotmentForCJCForm" property="subjectMap" label="value" value="key" />
							</c:if>
                            </html:select>
                            </div>
							</td>
							<td width="17%" height="25" class="row-even" >
                            <div align="center">
                            <html:text property="evenClassOrSubjectCount" styleId="evenClassOrSubjectCount" name="examRoomAllotmentForCJCForm" size="5" onkeypress="return checkNumeric(event)"></html:text>
                            </div>
							</td>
							<td width="17%" height="25" class="row-even" >
                            <div align="center">
                            <html:text property="noOfStudents3" styleId="noOfStudents3" name="examRoomAllotmentForCJCForm" onchange="getRegisterNos(this.value,'subject2')" onkeypress="return isNumberKey(event)"></html:text>
                            </div>
							</td>
							<td width="17%" height="25" class="row-even" >
                            <div align="center">
                            <html:text property="fromRegNo3" styleId="fromRegNo3" name="examRoomAllotmentForCJCForm" onkeypress="return checkNumeric(event)"></html:text>
                            </div>
							</td>
							<td width="17%" height="25" class="row-even" >
                            <div align="center">
                            <html:text property="toRegNo3" styleId="toRegNo3" name="examRoomAllotmentForCJCForm" onkeypress="return checkNumeric(event)"></html:text>
                            </div>
							</td>
							<td width="17%" height="25" class="row-even" align="center" >
							<html:button property="" styleClass="formbutton" value="Allot"  styleId="Submit" onclick="getAlloted('even','Subject')"></html:button> &nbsp;&nbsp;&nbsp;&nbsp;
							              <html:submit property="" styleClass="formbutton"
												value="Clear" onclick="clearAllotedList('even','Subject')" styleId="reset"></html:submit>
							</td>
						</tr>
						</logic:equal>
						<logic:equal value="No" property="isDateWise" name="examRoomAllotmentForCJCForm">
						<tr>
						 	<td width="17%" height="25" class="row-odd">
                            	<div align="center"><span class="Mandatory">*</span>Classes</div>
							</td>
							<td width="17%" height="25" class="row-odd">
                            	<div align="center"><span class="Mandatory"></span>Remaining Student of Class</div>
							</td>
							<td width="17%" height="25" class="row-odd">
                            	<div align="center"><span class="Mandatory">*</span>No. of Students</div>
							</td>
							<td width="17%" height="25" class="row-odd">
                            	<div align="center"><span class="Mandatory">*</span>From Register No.</div>
							</td>
							<td width="17%" height="25" class="row-odd">
                            	<div align="center"><span class="Mandatory">*</span>To Register No.</div>
							</td>
							<td width="17%" height="25" class="row-odd">
							</td>
						</tr>
						<tr>
							<td width="20%" height="25" class="row-even">
                            <div align="center">
                            <html:select property="classId" name="examRoomAllotmentForCJCForm" styleId="classId" styleClass="comboMediumBig" onchange="resetFields('odd','Classes')">
                            <html:option value="">--Select--</html:option>
                            <c:if test="${examRoomAllotmentForCJCForm.classMap != null}">
							<html:optionsCollection name="examRoomAllotmentForCJCForm" property="classMap" label="value" value="key" />
							</c:if>
                            </html:select>
                            </div>
							</td>
							<td width="17%" height="25" class="row-even">
                            <div align="center" >
                            <html:text property="oddClassOrSubjectCount" styleId="oddClassOrSubjectCount" size="5" name="examRoomAllotmentForCJCForm" onkeypress="return checkNumeric(event)"></html:text>
                            </div>
							</td>
							<td width="17%" height="25" class="row-even">
                            <div align="center">
                            <html:text property="noOfStudents" styleId="noOfStudents" name="examRoomAllotmentForCJCForm" onchange="getRegisterNos(this.value,'col1')" onkeypress="return isNumberKey(event)"></html:text>
                            </div>
							</td>
							
							<td width="17%" height="25" class="row-even">
                            <div align="center">
                            <html:text property="fromRegNo" styleId="fromRegNo" name="examRoomAllotmentForCJCForm" onkeypress="return checkNumeric(event)"></html:text>
                            </div>
							</td>
							<td width="17%" height="25" class="row-even">
                            <div align="center">
                            <html:text property="toRegNo" styleId="toRegNo" name="examRoomAllotmentForCJCForm" onkeypress="return checkNumeric(event)"></html:text>
                            </div>
							</td>
							<td width="17%" height="25" class="row-even" align="center">
							<html:button property="" styleClass="formbutton" value="Allot"  styleId="Submit" onclick="getAlloted('odd','Classes')"></html:button> &nbsp;&nbsp;&nbsp;&nbsp;
							              <html:submit property="" styleClass="formbutton"
												value="Clear" onclick="clearAllotedList('odd','Classes')" styleId="reset"></html:submit>
							</td>
						</tr>
						<tr>
							<td width="17%" height="25" class="row-even">
                            <div align="center">
                            <html:select property="classId1" name="examRoomAllotmentForCJCForm" styleId="classId1" styleClass="comboMediumBig" onchange="resetFields('even','Classes')">
                            <html:option value="">--Select--</html:option>
                            <c:if test="${examRoomAllotmentForCJCForm.classMap1 != null}">
							<html:optionsCollection name="examRoomAllotmentForCJCForm" property="classMap1" label="value" value="key" />
							</c:if>
                            </html:select>
                            </div>
							</td>
							<td width="17%" height="25" class="row-even">
                            <div align="center" >
                            <html:text property="evenClassOrSubjectCount" styleId="evenClassOrSubjectCount" name="examRoomAllotmentForCJCForm" size="5" onkeypress="return checkNumeric(event)"></html:text>
                            </div>
							</td>
							<td width="17%" height="25" class="row-even">
                            <div align="center">
                            <html:text property="noOfStudents1" styleId="noOfStudents1" name="examRoomAllotmentForCJCForm" onchange="getRegisterNos(this.value,'col2')" onkeypress="return isNumberKey(event)"></html:text>
                            </div>
							</td>
							<td width="17%" height="25" class="row-even">
                            <div align="center">
                            <html:text property="fromRegNo1" styleId="fromRegNo1" name="examRoomAllotmentForCJCForm" onkeypress="return checkNumeric(event)"></html:text>
                            </div>
							</td>
							<td width="17%" height="25"  class="row-even">
                            <div align="center">
                            <html:text property="toRegNo1" styleId="toRegNo1" name="examRoomAllotmentForCJCForm" onkeypress="return checkNumeric(event)"></html:text>
                            </div>
							</td>
							<td height="17%" align="center" class="row-even">
											<html:button property="" styleClass="formbutton"
												value="Allot"  styleId="Submit" onclick="getAlloted('even','Classes')"></html:button> &nbsp;&nbsp;&nbsp;&nbsp;
							              <html:submit property="" styleClass="formbutton"
												value="Clear" onclick="clearAllotedList('even','Classes')" styleId="reset"></html:submit>
							</td>
						</tr>
						</logic:equal>
						<tr >
						<td colspan="6">
						<div id="secondSubjectCol">
						<table width="100%">
						<tr>
						 	<td width="17%" height="25" class="row-odd">
                            	
							</td>
							<td width="17%" height="25" class="row-odd">
                            	<div align="center">Total Seats</div>
							</td>
							<td width="17%" height="25" class="row-odd">
                            	<div align="center">Alloted Seats</div>
							</td>
							<td width="17%" height="25" class="row-odd">
							<div align="center">Available Seats</div>
							</td>
							<td width="17%" height="25" class="row-odd" colspan="2">
							</td>
						</tr>
						<tr>
						 	<td width="17%" height="25" class="row-even">
                            	<div align="center">Column 1 </div>
							</td>
							<td width="17%" height="25" class="row-even">
                            	<div align="center"><bean:write property="columnOneTotalSeats" name="examRoomAllotmentForCJCForm"/></div>
							</td>
							<td width="17%" height="25" class="row-even">
                            	<div align="center"><bean:write property="colOneAllotedSeats" name="examRoomAllotmentForCJCForm"/></div>
							</td>
							<td width="17%" height="25" class="row-even">
                            	<div align="center"><bean:write property="colOneAvailableSeats" name="examRoomAllotmentForCJCForm"/></div>
							</td>
							<td width="17%" height="25" class="row-even" colspan="2">
							</td>
						</tr>
						<tr >
						 	<td width="17%" height="25" class="row-even">
                            	<div align="center">Column 2 </div>
							</td>
							<td width="17%" height="25" class="row-even">
                            	<div align="center"><bean:write property="columnTwoTotalSeats" name="examRoomAllotmentForCJCForm"/></div>
							</td>
							<td width="17%" height="25" class="row-even">
                            	<div align="center"><bean:write property="colTwoAllotedSeats" name="examRoomAllotmentForCJCForm"/></div>
							</td>
							<td width="17%" height="25" class="row-even" >
                            	<div align="center"><bean:write property="colTwoAvailableSeats" name="examRoomAllotmentForCJCForm"/></div>
							</td>
							<td width="17%" height="25" class="row-even" colspan="2">
							</td>
						</tr>
						</table>
						</div>
						</td>
						</tr>
						<tr >
						<td colspan="6">
						<div id="tempSecondColumn">
						<table width="100%">
						<tr>
						 	<td width="17%" height="25" class="row-odd">
                            	
							</td>
							<td width="17%" height="25" class="row-odd">
                            	<div align="center">Total Seats</div>
							</td>
							<td width="17%" height="25" class="row-odd">
                            	<div align="center">Alloted Seats</div>
							</td>
							<td width="17%" height="25" class="row-odd">
							<div align="center">Available Seats</div>
							</td>
							<td width="17%" height="25" class="row-odd" colspan="2">
							</td>
						</tr>
						<tr>
						 	<td width="17%" height="25" class="row-even">
                            	<div align="center">Column 1 </div>
							</td>
							<td width="17%" height="25" class="row-even">
                            	<div align="center"><bean:write property="tempTotalSeats" name="examRoomAllotmentForCJCForm"/></div>
							</td>
							<td width="17%" height="25" class="row-even">
                            	<div align="center"><bean:write property="tempAllotedSeats" name="examRoomAllotmentForCJCForm"/></div>
							</td>
							<td width="17%" height="25" class="row-even">
                            	<div align="center"><bean:write property="tempAvailableSeats" name="examRoomAllotmentForCJCForm"/></div>
							</td>
							<td width="17%" height="25" class="row-even" colspan="2">
							</td>
						</tr>
						
						</table>
						</div>
						</td>
						</tr>
						<td valign="top" class="news" colspan="6">
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
							<td height="25" colspan="6">
							<table width="100%" cellspacing="1" cellpadding="2">
								<nested:iterate id="rowsList" name="examRoomAllotmentForCJCForm" property="roomAllotmentStatusTo" >
								<tr>
								<%int temp1 =0; %>
								<%int count=0; %>
								<%int position=0; %>
								<nested:iterate id="studentsList"  property="allotmentStatusDetailsToList" type="com.kp.cms.to.examallotment.RoomAllotmentStatusDetailsTo" indexId="temp">
								<%if(!studentsList.isTempCheck()){%>
									<%if(count!=studentsList.getColumnNO() && count!=0){%>
										<td width="10%"></td>
									<% }%>
									<%if(position==studentsList.getSeatingPosition() && position!=0){%>
										<td width="10%"></td>
									<% }%>
									
									<td width="10%">
									<div class="CSSTableGenerator">
									<table width="80%" height="50">
									<tr>
									<td width="50%" align="center" style="border: none;" height="10">
									<%if(studentsList.getClassName()!=null && !studentsList.getClassName().isEmpty()){ %>
										<bean:write name="studentsList" property="className"/>
									<%}else if(studentsList.getSubjectName()!=null && !studentsList.getSubjectName().isEmpty()){ %>
										<bean:write name="studentsList" property="subjectName"/>
									<%} %>
									
									</td>
									</tr>
									<tr align="center">
									<td width="50%" align="center" style="border: none;" >
									<nested:text  property="registerNo" size="10"></nested:text>
									</td>
									</tr>
									<%count=studentsList.getColumnNO();%>
									<%position=studentsList.getSeatingPosition(); %>
									</table>
									</div>
									</td>
									<%}else{%>
									<%if(temp1 == 0){ %>
									<td width="10%"></td>
									
									<%} %>
									<%temp1++; %>
									<td width="10%"></td>
									<%count=studentsList.getColumnNO()+1;%>
									<%} %>
									
								</nested:iterate>
								</tr>
								</nested:iterate>
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
				</logic:notEmpty>
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
												value="Submit"  styleId="Submit" onclick="submitAllotmentDetails()"></html:submit>
									</div>
									</td>
									<td width="2%"></td>
									<td width="53%">
							              <html:button property="" styleClass="formbutton"
												value="Close" onclick="goTOFirstPage()" styleId="close"></html:button>
										</td>	
								</tr>
							</table>
							</td>
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
var isDateWise =document.getElementById("isDateWise").value;
 var allotAll = document.getElementById("allotAllCol").value;

if(allotAll == "on"){
	if(isDateWise == 'Yes'){
		document.getElementById("subjectId1").disabled=true;
		document.getElementById("noOfStudents3").disabled=true;
		document.getElementById("fromRegNo3").disabled=true;
		document.getElementById("toRegNo3").disabled=true;
	}else if(isDateWise == 'No'){
		document.getElementById("classId1").disabled=true;
		document.getElementById("noOfStudents1").disabled=true;
		document.getElementById("fromRegNo1").disabled=true;
		document.getElementById("toRegNo1").disabled=true;
	}
	document.getElementById("tempSecondColumn").style.display="block";
	document.getElementById("secondSubjectCol").style.display="none";
}else{
	if(isDateWise == 'Yes'){
		document.getElementById("subjectId1").disabled=false;
		document.getElementById("noOfStudents3").disabled=false;
		document.getElementById("fromRegNo3").disabled=false;
		document.getElementById("toRegNo3").disabled=false;
	}else if(isDateWise == 'No'){
		document.getElementById("classId1").disabled=false;
		document.getElementById("noOfStudents1").disabled=false;
		document.getElementById("fromRegNo1").disabled=false;
		document.getElementById("toRegNo1").disabled=false;
	}
	document.getElementById("tempSecondColumn").style.display="none";
	document.getElementById("secondSubjectCol").style.display="block";
}
</script>