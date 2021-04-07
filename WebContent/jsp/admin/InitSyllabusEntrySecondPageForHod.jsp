<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="http://java.fckeditor.net" prefix="FCK" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@page import="com.itextpdf.text.log.SysoLogger"%>
<script src="jquery/development-bundle/jquery-1.7.1.js"></script>
<script src="tinymce/js/tinymce/tinymce.min.js"></script>
<link rel="stylesheet" type="text/css" href="css/tooltipster.css" />
 <script type="text/javascript" src="js/jquery.tooltipster.js"></script>
<link rel="stylesheet" type="text/css" href="jquery.confirm/jquery.confirm/teacherAllotment.css" />
<link type="text/css"
	href="jquery/css/ui-lightness/jquery-ui-1.8.18.custom.css" rel="stylesheet" />
<link rel="stylesheet" type="text/css" href="css/styles.css"/>
	<link rel="stylesheet" type="text/css" href="css/sdmenu.css"/>
    <link rel='stylesheet' type='text/css' href="css/auditorium/start/jquery-ui-supportRequest.css" />
	<script type='text/javascript' src="js/auditorium/jquery-ui.min.js"></script>
	<link rel="stylesheet" type="text/css" href="jquery.confirm/jquery.confirm/jquery.confirm.css" />
    <script src="jquery.confirm/jquery.confirm/jquery.confirm.js"></script>
<style type="text/css">
body {
	margin-left: 0px;
	margin-top: 5px;
	margin-right: 0px;
	margin-bottom: 0px;
}
</style>
<style type="text/css">
.black_overlay{
    display: none;
    position: absolute;
    top: 0%;
    left: 0%;
    width: 100%;
    height: 100%;
    background-color: black;
    z-index:1001;
    -moz-opacity: 0.8;
    opacity:.80;
    filter: alpha(opacity=80);
}
.white_content {
    display: none;
    position: absolute;
    top: 25%;
    left: 25%;
    width: 50%;
    height: 50%;
    padding: 16px;
    border: 16px solid orange;
    background-color: white;
    z-index:1002;
    overflow: auto;
}
body { font-size: 62.5%; }
</style>
<style type="text/css">
 .btn {
  background: #A0F24E;
  background-image: -webkit-linear-gradient(top, #F7FBDA, #DBE87A);
  background-image: -moz-linear-gradient(top, #F7FBDA, #DBE87A);
  background-image: -ms-linear-gradient(top, #F7FBDA, #DBE87A);
  background-image: -o-linear-gradient(top, #F7FBDA, #DBE87A);
  -webkit-border-radius: 15;
  -moz-border-radius: 15;
  border-radius: 15px;
  font-family: Arial;
  font-weight:bold;
  color: #171919;
  font-size: 12px;
  padding: 5px 10px 5px 10px;
  text-decoration: none;
}

.btn:hover {
  background: #D5DC98;
  background-image: -webkit-linear-gradient(top, #D5DC98, #F7FBDA);
  background-image: -moz-linear-gradient(top, #D5DC98, #F7FBDA);
  background-image: -ms-linear-gradient(top, #D5DC98, #F7FBDA);
  background-image: -o-linear-gradient(top, #D5DC98, #F7FBDA);
  text-decoration: none;
}
.tdUnits {
  background: #ffffff;
  background-image: -webkit-linear-gradient(top, #ffffff, #a3a338);
  background-image: -moz-linear-gradient(top, #ffffff, #a3a338);
  background-image: -ms-linear-gradient(top, #ffffff, #a3a338);
  background-image: -o-linear-gradient(top, #ffffff, #a3a338);
  background-image: linear-gradient(to bottom, #ffffff, #a3a338);
  -webkit-border-radius: 0;
  -moz-border-radius: 0;
  border-radius: 0px;
  font-family: Arial;
  color: #050505;
  font-size: 14px;
  padding: 2px 17px 2px 20px;
  text-decoration: none;
}
.tdHeadings {
   background: #ffffff;
  background-image: -webkit-linear-gradient(top, #ffffff, #d4d44c);
  background-image: -moz-linear-gradient(top, #ffffff, #d4d44c);
  background-image: -ms-linear-gradient(top, #ffffff, #d4d44c);
  background-image: -o-linear-gradient(top, #ffffff, #d4d44c);
  background-image: linear-gradient(to bottom, #ffffff, #d4d44c);
  -webkit-border-radius: 0;
  -moz-border-radius: 0;
  border-radius: 0px;
  font-family: Arial;
  color: #050505;
  font-size: 12px;
  padding: 3px 17px 3px 20px;
  text-decoration: none;
}
 </style>
<script type="text/javascript">
	 tinymce.init({selector: "textarea.editme"});
</script>
<script type="text/javascript">
$(document).ready(function() {
    // Tooltip only Text
     $('#openDialog').hide();
    $('.syllabus').tooltipster();
    $('.dropt').tooltipster();
   
});
function firstPage(){
	document.getElementById("method").value = "initSyllabusEntryHodApprovalFirstPage";
	document.syllabusEntryHodApprovalForm.submit();
}
function sendForApproval(){
	$.confirm({
		'message'	: 'You are about to send the syllabus for approval. Once sent, it cannot be	modified',
		'buttons'	: {
			'Ok'	: {
				'class'	: 'blue',
				'action': function(){
					$.confirm.hide();
					document.getElementById("method").value="SaveByHod";
					document.syllabusEntryHodApprovalForm.submit();
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
function saveDraft(){
	document.getElementById("method").value = "saveDraftByHod";
	document.syllabusEntryHodApprovalForm.submit();
}
function editTemplate(id){
	document.location.href = "syllabusEntry.do?method=edit&id="+id;
}
function addMoreHeadingsAndDesc(position){
	document.getElementById("method").value = "addMoreHeadingsAndDescription";
	document.getElementById("num").value = position;
	document.syllabusEntryHodApprovalForm.submit();
}
function removeMoreHeadingsAndDesc(position){
	document.getElementById("method").value = "removeMoreHeadingsAndDescription";
	document.getElementById("num").value = position;
	document.syllabusEntryHodApprovalForm.submit();
}
function addMoreUnitsAndHrs(){
	document.getElementById("method").value = "addMoreUnitsAndHours";
	document.syllabusEntryHodApprovalForm.submit();
}
function removeMoreUnitsAndHrs(){
	document.getElementById("method").value = "removeMoreUnitsAndHours";
	document.syllabusEntryHodApprovalForm.submit();
}
function hideEditors(){
	$("#aid").hide();
    $("#uid").hide();
    $("#cid").show();
    $("#luid").removeClass("current");
    $("#laid").removeClass("current");
    $("#lcid").addClass("current");
}
function changeEditors(id){
		if(id=='cid'){
			        $("#luid").removeClass("current");
		            $("#laid").removeClass("current");
				    $("#uid").hide();
				    $("#aid").hide();
				    
				    $("#lcid").addClass("current");
				    $("#cid").show();
				    
			}else if(id=='aid'){
						$("#lcid").removeClass("current");
			            $("#luid").removeClass("current");
			            $("#laid").addClass("current");
					    $("#cid").hide();
					    $("#uid").hide();
					    $("#aid").show();
				}else if(id=='uid'){
							$("#lcid").removeClass("current");
				            $("#laid").removeClass("current");
				            $("#luid").addClass("current");
						    $("#cid").hide();
						    $("#aid").hide();
						    $("#uid").show();
					}
}
function openUnits(){
	$("#lcid").removeClass("current");
    $("#laid").removeClass("current");
    $("#luid").addClass("current");
	$("#cid").hide();
    $("#aid").hide();
    $("#uid").show();
}
function openUnitsAndHeadings(id,id1){
	$("#lcid").removeClass("current");
    $("#laid").removeClass("current");
    $("#luid").addClass("current");
	$("#cid").hide();
    $("#aid").hide();
    $("#uid").show();
	document.getElementById(id).style.display = '';
	document.getElementById(id1).style.display = '';
}
function previousData(){
	var deptId=document.getElementById("departmentId").value;
	var year=document.getElementById("batchYear").value;
	getYearMapByDept("yearMap", deptId,year, "previousYear", updateYearMap);
}
function updateYearMap(req){
	updateOptionsFromMap(req,"previousYear","- Select -");
	 $("#openDialog").dialog({
	        resizable: false,
	        modal: true,
	        height: 200,
	        width: 300,
	        close: function() {
	    	$("#openDialog").dialog("destroy");
	    	$("#openDialog").hide();
   },
   buttons: {
 	  Import : function() {
		 	  var year=document.getElementById("previousYear").value;
		 	  var subject=document.getElementById("previousYearSubjectId").value;
			  if(year!=null && year!='' && subject!=null && subject!=''){
					  document.getElementById("method").value = "previousSyllabusEntryBySubjectAndYearForHod";
					  document.getElementById("pyear").value= year;
					  document.getElementById("psubjectId").value= subject;
					  document.syllabusEntryHodApprovalForm.submit();
		              $("#openDialog").dialog("close");
		              $("#openDialog").hide();
				  }else{
					  $.confirm({
            				'message'	: 'Year and Subject is required',
            				'buttons'	: {
            					'Ok'	: {
            						'class'	: 'blue',
            						'action': function(){
            							$.confirm.hide();
            						}
            					}
            				}
            			});
					  }
         	
     },
    Cancel : function() {
         $("#openDialog").dialog("close");
         $("#openDialog").hide();
      }
	               
   }
	    });
}
function getSubjects(year){
	var deptId=document.getElementById("departmentId").value;
	getsubjectMapByDeptAndYear("subjMap", deptId,year, "previousYearSubjectId", updatesubjectMap);
}
function updatesubjectMap(req){
	updateOptionsFromMap(req,"previousYearSubjectId","- Select -");
	}
function changeSyllabus(val){
	if(val=='yes'){
		document.getElementById("tempChangeInSyllabus").value=val;
		 document.getElementById("change").style.display = '';
		 document.getElementById("change1").style.display = '';
		 document.getElementById("change2").style.display = '';
		 document.getElementById("change3").style.display = '';
		}else{
			document.getElementById("change").style.display = 'none';
			document.getElementById("change1").style.display = 'none';
			document.getElementById("change2").style.display = 'none';
			document.getElementById("change3").style.display = 'none';
			document.getElementById("tempChangeInSyllabus").value=val;
			}
}
function toggle(id) {
	 if( document.getElementById("U_"+id).style.display=='none' ){
	   document.getElementById("U_"+id).style.display = '';
	   document.getElementById("P_"+id).style.display = 'none';
	   document.getElementById("M_"+id).style.display = '';
	 }else{
	   document.getElementById("U_"+id).style.display = 'none';
	   document.getElementById("M_"+id).style.display = 'none';
		 document.getElementById("P_"+id).style.display = '';
	 }
	}
function toggle1(id,id1){
	 if( document.getElementById("head_"+id+"_"+id1).style.display=='none' ){
		   document.getElementById("head_"+id+"_"+id1).style.display = '';
		   document.getElementById("HP_"+id+"_"+id1).style.display = 'none';
		   document.getElementById("HM_"+id+"_"+id1).style.display = '';
		 }else{
		   document.getElementById("head_"+id+"_"+id1).style.display = 'none';
		   document.getElementById("HM_"+id+"_"+id1).style.display = 'none';
			document.getElementById("HP_"+id+"_"+id1).style.display = '';
		 }
}
function preview(){
	var status=document.getElementById("status").value;
	if(status=='Pending'){
		 $.confirm({
				'message'	: "Syllabus Entry status is pending",
				'buttons'	: {
	     		'Ok'	:  {
						'class'	: 'blue',
						'action': function(){
							$.confirm.hide();
						}
					}
				}
			});
		}else{
			var year=document.getElementById("batchYear").value;
			var subId=document.getElementById("subjectId").value;
			url = "syllabusEntryHodApproval.do?method=preview&subjectId="+subId+"&batchYear="+year;
			myRef = window.open(url,"collectionLedger","left=20,top=20,width=800,height=500,toolbar=1,resizable=0,scrollbars=1");
	}
	
}
</script>
<html:form action="/syllabusEntryHodApproval" method="post">
<html:hidden property="formName" value="syllabusEntryHodApprovalForm" />
<html:hidden property="pageType" value="2" />
<html:hidden property="num" value="0" styleId="num"/>
<html:hidden property="method" value="SaveByHod" styleId="method"/>
<html:hidden property="unitsFocus" styleId="unitsFocus" />
<html:hidden property="headingsFocus" styleId="headingsFocus" />
<html:hidden property="unitOrHead" styleId="unitOrHead" />
<html:hidden property="departmentId" styleId="departmentId"/>
<html:hidden property="batchYear" styleId="batchYear" />
<html:hidden property="previousYear" styleId="pyear" />
<html:hidden property="previousYearSubjectId" styleId="psubjectId" />
<html:hidden property="position" styleId="position" />
<html:hidden property="tempHeadingsFocus" styleId="tempHeadingsFocus" />
<html:hidden property="status" styleId="status" />
<html:hidden property="subjectId" styleId="subjectId" />
<table width="98%" border="0">
  <tr>
    <td><span class="heading"><bean:message key="knowledgepro.admin"/><span class="Bredcrumbs">&gt;&gt;Syllabus Entry&gt;&gt;</span></span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="10"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="body" ><div align="left"><strong class="boxheader">Syllabus Entry</strong></div></td>
        <td width="13" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="122" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><div align="center">
              <table width="100%" height="50"  border="0" cellpadding="0" cellspacing="0">
                 <tr>
               	    <td height="20" colspan="6" align="left" width="100%">
               	    <div align="right" style="color:red"> <span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields"/></span></div>
               	    <div id="errorMessage"> &nbsp;
                       <FONT color="red"><html:errors/></FONT>
                       <FONT color="green">
							<html:messages id="msg" property="messages" message="true">
									<c:out value="${msg}" escapeXml="false"></c:out><br>
							</html:messages>
					  </FONT>
					  </div>
               	    </td>
                 </tr>
                 <tr>
                   <td height="49" colspan="6" class="body" width="100%"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
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
                             <td width="20%" class="row-odd" ><div align="right">
                             	<bean:message key="knowledgepro.cancelattendance.batches"/>:</div>
                             </td>
                             <td width="25%" height="25" class="row-even">
                           		<bean:write property="batchYear" name="syllabusEntryHodApprovalForm"/>
                             </td>
                            <td width="15%" class="row-odd" ><div align="right">
                             	<bean:message key="knowledgepro.admin.code"/>/<bean:message key="knowledgepro.studentlogin.name"/>:</div>
                             </td>
                             <td width="40%" height="25" class="row-even">
                           		<bean:write property="subjectCode" name="syllabusEntryHodApprovalForm"/>/<bean:write property="subjectName" name="syllabusEntryHodApprovalForm"/>
                             </td>
                           </tr>
                           <tr>
                           	<td width="20%" class="row-odd" ><div align="right">
                             	<bean:message key="knowledgepro.usermanagement.userinfo.department"/>:</div>
                             </td>
                             <td width="25%" height="25" class="row-even">
                           		<bean:write property="parentDepartment" name="syllabusEntryHodApprovalForm"/>
                             </td>
                             <td width="15%" class="row-odd" ><div align="right">
                             	Theory/Practical:</div>
                             </td>
                             <td width="40%" height="25" class="row-even">
                           		<bean:write property="theoryOrPractical" name="syllabusEntryHodApprovalForm"/>
                             </td>
                           </tr>
                            <tr>
                             <td width="20%" class="row-odd"><div align="right">
                             	Question Bank Required:</div>
                             </td>
                             <td width="25%" height="25" class="row-even">
                           		<bean:write property="questionBankRequired" name="syllabusEntryHodApprovalForm"/>
                             </td>
                           
                            <td  height="25" class="row-odd" colspan="2">
                             	<a href="#" onclick="previousData()"><b><font size="2" color="red">Import Syllabus From Other Year</font></b></a>
                           		<div id="openDialog">
										<div align="center">
											<table width="100%">
												<tr>
							                   <td height="49"  class="body"><table width="99%" border="0"  cellpadding="0" cellspacing="0">
							                     <tr>
							                       <td ><img src="images/01.gif" width="5" height="5" /></td>
							                       <td width="914" background="images/02.gif"></td>
							                       <td><img src="images/03.gif" width="5" height="5" /></td>
							                     </tr>
							                     <tr>
							                       <td width="5"  background="images/left.gif"></td>
							                       <td valign="top">
							                       <table width="99%" cellspacing="1" cellpadding="2">
										                 <tr>
															<td class="row-odd" height="25" width="25%"><span class="Mandatory">*</span>Batch</td>
															<td class="row-even" height="25" width="70%">
																<html:select property="previousYear" styleClass="comboMedium" styleId="previousYear"  onchange="getSubjects(this.value)">
						                    						<c:choose>
										             					<c:when test="${yearMap != null}">
										             					<html:optionsCollection name="yearMap" label="value" value="key" />
																		</c:when>
												   					</c:choose>
												   				</html:select>
															</td>
														</tr>
														<tr>
															<td class="row-odd" height="25" width="25%"><span class="Mandatory">*</span>Subject</td>
															<td class="row-even" height="25" width="70%">
																<html:select property="previousYearSubjectId" styleId="previousYearSubjectId" styleClass="comboLarge">
						                    						<c:choose>
										             					<c:when test="${subjMap != null}">
										             					<html:optionsCollection name="subjMap" label="value" value="key" />
																		</c:when>
												   					</c:choose>
												   				</html:select>
															</td>
														</tr>
							                       </table></td>
							                       <td width="5" height="30"  background="images/right.gif"></td>
							                     </tr>
							                     <tr>
							                       <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
							                       <td background="images/05.gif"></td>
							                       <td><img src="images/06.gif" /></td>
							                     </tr>
							                   </table></td>
								           </tr>
											</table>
										</div>
								</div>
                             </td>
                           </tr>
                       </table></td>
                       <td width="5" height="30"  background="images/right.gif"></td>
                     </tr>
                     <tr>
                       <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
                       <td background="images/05.gif"></td>
                       <td><img src="images/06.gif" /></td>
                     </tr>
                   </table></td>
                 </tr>
                 <tr><td height="10" width="100%"></td></tr>
                 <logic:notEmpty name="syllabusEntryHodApprovalForm" property="rejectReason">
                 <tr>
	                   <td height="49"  class="body" width="100%"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
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
		                             <td class="row-odd"  width="100%"><div align="left">
		                             	<span class="Mandatory"></span>Reject Reason:</div>
		                             </td>
		                         </tr>
	                          	 <tr>
		                             <td width="100%" height="25" class="row-even">
		                             <bean:write property="rejectReason" name="syllabusEntryHodApprovalForm"/>
		                             </td>
		                           </tr>
		                            
	                       </table></td>
	                       <td width="5" height="30"  background="images/right.gif"></td>
	                     </tr>
	                     <tr>
	                       <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
	                       <td background="images/05.gif"></td>
	                       <td><img src="images/06.gif" /></td>
	                     </tr>
	                   </table></td>
		           </tr>
                 	<tr><td height="10" width="100%"></td></tr>
                 </logic:notEmpty>
                   <tr>
	                   <td height="49" colspan="4" class="body" width="100%"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
	                     <tr>
	                       <td ><img src="images/01.gif" width="5" height="5" /></td>
	                       <td width="914" background="images/02.gif"></td>
	                       <td><img src="images/03.gif" width="5" height="5" /></td>
	                     </tr>
	                     <tr>
	                       <td width="5"  background="images/left.gif"></td>
	                       <td valign="top">
	                       <table width="100%" cellspacing="1" cellpadding="2">
				                 <tr style="width: 100%">
		                           <td  width="100%" colspan="4">
		                           	<ul class="glossymenu" id="nav">
										<li id="lcid"><a href="#" onclick="changeEditors('cid')"><b><span class="Mandatory">*</span><FONT size="2">Course Details</FONT></b></a></li>
										<li id="luid"><a href="#" onclick="changeEditors('uid')"><b><span class="Mandatory">*</span><FONT size="2">Unit Details</FONT></b></a></li>
										<li id="laid"><a href="#" onclick="changeEditors('aid')"><b><span class="Mandatory">*</span><FONT size="2">Additional Information</FONT></b></a></li>	
									</ul>
									</td>
		                          </tr> 
	                             <tr id="cid" style="width: 100%">
		                             <td width="100%" height="25" colspan="4">
		                             	<table width="100%">
		                             		<tr>
			                   				<td height="49" class="body" >
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
								                             <td width="30%" class="row-odd" ><div align="right">
								                             	<span class="Mandatory">*</span><bean:message key="knowledgepro.admin.syllabus.entry.totalteachinghours"/>:</div>
								                             </td>
								                             <td width="20%" height="25" class="row-even" >
								                           		<html:text property="totTeachHrsPerSem" styleId="totTeachHrsPerSem" size="3" maxlength="3" onkeypress="return isNumberKey(event)"></html:text>
								                             </td>
								                             <td width="30%" class="row-odd" ><div align="right">
								                             	<span class="Mandatory">*</span><bean:message key="knowledgepro.admin.syllabus.entry.NoOflectureHoursPerWeek"/>:</div>
								                             </td>
								                             <td width="20%" height="25" class="row-even">
								                           		<html:text property="noOfLectureHrsPerWeek" styleId="noOfLectureHrsPerWeek" size="3" maxlength="3" onkeypress="return isNumberKey(event)"></html:text>
								                             </td>
								                           </tr>
								                           <tr>
								                             <td width="30%" class="row-odd" ><div align="right">
								                             	<span class="Mandatory">*</span><bean:message key="knowledgepro.admin.syllabus.entry.credits"/>:</div>
								                             </td>
								                             <td width="20%" height="25" class="row-even" >
								                           		<html:text property="credits" styleId="credits" maxlength="20" size="3"></html:text>
								                             </td>
								                             <td width="30%" class="row-odd" ><div align="right">
								                             	<span class="Mandatory">*</span><bean:message key="knowledgepro.admission.maxmarks"/>:</div>
								                             </td>
								                             <td width="20%" height="25" class="row-even">
								                           		<html:text property="maxMarks" styleId="maxMarks" size="3" maxlength="3" onkeypress="return isNumberKey(event)"></html:text>
								                             </td>
								                           </tr>
							                       </table></td>
							                       <td width="5" height="30"  background="images/right.gif"></td>
							                     </tr>
							                     <tr>
							                       <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
							                       <td background="images/05.gif"></td>
							                       <td><img src="images/06.gif" /></td>
							                     </tr>
							                   </table>
			                   				</td>
				           					</tr>
		                             		 <tr>
							                   <td height="49"  class="body" width="100%"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
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
								                             <td class="row-odd"  width="100%"><div align="left">
								                             	<span class="Mandatory">*</span><bean:message key="knowledgepro.admin.syllabus.entry.course.objective"/>:</div>
								                             </td>
								                         </tr>
							                          	 <tr>
								                             <td width="100%" height="25" class="row-even">
								                             <html:textarea property="courseObjective" styleClass="editme"  style="width:100%" ></html:textarea>
								                             </td>
								                           </tr>
								                            
							                       </table></td>
							                       <td width="5" height="30"  background="images/right.gif"></td>
							                     </tr>
							                     <tr>
							                       <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
							                       <td background="images/05.gif"></td>
							                       <td><img src="images/06.gif" /></td>
							                     </tr>
							                   </table></td>
								           </tr>
				           
								           <tr>
							                   <td height="49"  class="body" width="100%"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
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
								                             <td width="100%" class="row-odd" ><div align="left">
								                             	<span class="Mandatory">*</span><bean:message key="knowledgepro.admin.syllabus.entry.course.learningOutcome"/>:</div>
								                             </td>
								                           </tr>
								                           <tr>
								                             <td width="100%" height="25" class="row-even" >
								                            	 <html:textarea property="lerningOutcome" styleClass="editme" style="width:100%"></html:textarea>
								                             </td>
								                           </tr>
							                       </table></td>
							                       <td width="5" height="30"  background="images/right.gif"></td>
							                     </tr>
							                     <tr>
							                       <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
							                       <td background="images/05.gif"></td>
							                       <td><img src="images/06.gif" /></td>
							                     </tr>
							                   </table></td>
								           </tr>
				           
								            <tr>
							                   <td height="49" class="body" width="100%"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
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
							                             <td width="100%" class="row-odd" ><div align="left">
							                             	<span class="Mandatory"></span><bean:message key="knowledgepro.admin.syllabus.entry.course.txtbooksandrefBooks"/>:</div>
							                             </td>
							                           </tr>
							                           <tr>
							                             <td width="100%" height="25" class="row-even" >
							                             	 <html:textarea property="textBooksAndRefBooks" styleClass="editme" style="width:100%"></html:textarea>
							                             </td>
							                           </tr>
							                       </table></td>
							                       <td width="5" height="30"  background="images/right.gif"></td>
							                     </tr>
							                     <tr>
							                       <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
							                       <td background="images/05.gif"></td>
							                       <td><img src="images/06.gif" /></td>
							                     </tr>
							                   </table></td>
								           </tr>
		                             		<tr>
							                   <td height="49" class="body" width="100%"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
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
							                             <td width="100%" class="row-odd" ><div align="left">
							                             	<span class="Mandatory"></span><bean:message key="knowledgepro.admin.syllabus.entry.course.recommended.reading"/>:</div>
							                             </td>
							                           </tr>
							                           <tr>
							                             <td width="100%" height="25" class="row-even" >
							                             	 <html:textarea property="recommendedReading" styleClass="editme" style="width:100%"></html:textarea>
							                             </td>
							                           </tr>
							                       </table></td>
							                       <td width="5" height="30"  background="images/right.gif"></td>
							                     </tr>
							                     <tr>
							                       <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
							                       <td background="images/05.gif"></td>
							                       <td><img src="images/06.gif" /></td>
							                     </tr>
							                   </table></td>
								           </tr>
		                             	
		                             	
		                             	</table>
		                             </td>
		                          </tr>
		                          <tr id="aid" style="width: 100%">
		                             <td width="100%" height="25" colspan="4">
		                             	<table width="100%">
		                             		<tr>
							                   <td height="49"  class="body" ><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
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
							                             <td width="100%" class="row-odd" ><div align="left">
							                             	<span class="Mandatory"></span><bean:message key="knowledgepro.admin.syllabus.entry.course.freetext"/> (This will appear end of each subject):</div>
							                             </td>
							                           </tr>
							                           <tr>
							                             <td width="100%" height="25" class="row-even" >
															<FCK:editor instanceName="freeText"  toolbarSet="Default">
																<jsp:attribute name="value">
																	<c:out value="${syllabusEntryHodApprovalForm.freeText}" escapeXml="false"></c:out>
																</jsp:attribute>
															</FCK:editor>
							                             </td>
							                           </tr>
							                       </table></td>
							                       <td width="5" height="30"  background="images/right.gif"></td>
							                     </tr>
							                     <tr>
							                       <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
							                       <td background="images/05.gif"></td>
							                       <td><img src="images/06.gif" /></td>
							                     </tr>
							                   </table></td>
								           </tr>
								           <tr>
							                   <td height="49"  class="body" ><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
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
							                             <td width="100%" class="row-odd" colspan="2"><div align="center">
							                             <html:hidden property="tempChangeInSyllabus" styleId="tempChangeInSyllabus"/>
										                     <span class="Mandatory">*</span><bean:message key="knowledgepro.admin.syllabus.entry.change.syllabus.entry"/>:<input type="radio" name="changeInSyllabus" id="changeSyllabus1" value="yes" onclick="changeSyllabus(this.value)"/>Yes &nbsp;&nbsp;<input type="radio" name="changeInSyllabus" id="changeSyllabus2" value="no"  onclick="changeSyllabus(this.value)"/>No</div>
										                </td>
							                           </tr>
							                           <tr id="change" style="display: none;">
							                             <td width="25%" class="row-odd"><div align="right">
										                     <span class="Mandatory">*</span>Brief Details About Existing Syllabus:</div>
										                </td>
										                <td width="75%" height="25" class="row-even" >
							                           		<html:textarea property="briefDetailsExistingSyllabus" style="width: 100%" cols="80" rows="5"></html:textarea>
							                             </td>
							                           </tr>
							                           <tr id="change1" style="display: none;">
							                             <td width="25%" class="row-odd" ><div align="right">
										                     <span class="Mandatory">*</span>Brief Details About The Change:</div>
										                </td>
										                <td width="75%" height="25" class="row-even">
							                           		<html:textarea property="briefDetalsAboutChange" style="width: 100%" cols="80" rows="5"></html:textarea>
							                             </td>
							                           </tr>
							                           <tr id="change2" style="display: none;">
							                             <td width="25%" class="row-odd"><div align="right">
										                     <span class="Mandatory">*</span>Reason For Change:</div>
										                </td>
										                <td width="75%" height="25" class="row-even">
							                           		<html:textarea property="changeReason" style="width: 100%" cols="80" rows="5"></html:textarea>
							                             </td>
							                           </tr>
							                           <tr id="change3" style="display: none;">
							                             <td width="25%" class="row-odd"><div align="right">
										                     <span class="Mandatory">*</span><bean:message key="knowledgepro.hostel.adminmessage.remarks"/>:</div>
										                </td>
										                <td width="75%" height="25" class="row-even">
							                           		<html:textarea property="remarks" style="width: 100%" cols="80" rows="5"></html:textarea>
							                             </td>
							                           </tr>
							                       </table></td>
							                       <td width="5" height="30"  background="images/right.gif"></td>
							                     </tr>
							                     <tr>
							                       <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
							                       <td background="images/05.gif"></td>
							                       <td><img src="images/06.gif" /></td>
							                     </tr>
							                   </table></td>
								           </tr>
		                             	</table>
		                             </td>
		                          </tr>
		                          <!--
		                          		start for units
	                      			 -->
	                       			<tr id="uid" style="width: 100%">
		                             <td width="100%" height="25" colspan="4">
		                             	<table width="100%">
								                    <tr>
								                   <td height="49" class="body" width="100%"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
								                     <tr>
								                       <td ><img src="images/01.gif" width="5" height="5" /></td>
								                       <td width="914" background="images/02.gif"></td>
								                       <td><img src="images/03.gif" width="5" height="5" /></td>
								                     </tr>
								                     <tr>
								                       <td width="5"  background="images/left.gif"></td>
								                       <td valign="top">
								                       <table width="100%" cellspacing="1" cellpadding="2" id="report">
								                          	 	 <logic:notEmpty name="syllabusEntryHodApprovalForm" property="syllabusEntryUnitsHoursTos">
													                   <nested:iterate id="CME" name="syllabusEntryHodApprovalForm" property="syllabusEntryUnitsHoursTos" indexId="count" type="com.kp.cms.to.admin.SyllabusEntryUnitsHoursTo">
													                   	<%
													                   		String method = "addMoreHeadingsAndDesc("+CME.getPosition()+")";
													                   		String method1 = "removeMoreHeadingsAndDesc("+CME.getPosition()+")";
													                   		String focus="unit_"+count;
													                   		String uId="U_"+(count+1);
													                   		String method4="toggle("+(count+1)+")";
													                   		String plus="P_"+(count+1);
													                   		String minus="M_"+(count+1);
													                   	%>
									                          	 	 <tr>
									                          	 	 	<td  height="25" class="row-even" width="2%"><div align="center">
																			<nested:checkbox property="checked" onclick="unCheckSelectAll()"></nested:checkbox></div>
																		</td>
								                          	 	 	 	<td width="15%" class="tdUnits" onClick="<%=method4 %>;">
								                          	 	 	 		<table>
								                          	 	 	 			<tr>
								                          	 	 	 				<td width="5%" align="right" style="" id="<%=plus %>"><img  src="images/syllabusEntry_collapse.png" height="20" width="20"/></td>
								                          	 	 	 				<td width="5%" align="right" style="display: none;" id="<%=minus %>"><img  src="images/syllabusEntry_expand.png" height="20" width="20"/></td>
								                          	 	 	 				<td width="95%" align="left">
								                          	 	 	 					<span class="dropt" title="Click here for headings."><bean:write name="CME" property="units"/></span>
								                          	 	 	 				</td>
								                          	 	 	 			</tr>
								                          	 	 	 		</table>
																		 
																	   	</td>
																	    <td width="25%" class="row-odd" ><div align="right">
										                             	<span class="Mandatory">*</span><bean:write name="CME" property="teachingHoursTemplate"/>:</div>
											                             </td>
											                             <td width="25%" height="25" class="row-even">
											                           		<nested:text property="teachingHours" size="3" maxlength="3" styleId="<%=focus %>" onkeypress="return isNumberKey(event)"></nested:text>
											                             </td>
								                          	 		 </tr>
								                          	 		  <!-- 	start headings and description   -->
								                          	 		   <tr  id="<%=uId %>" style="display: none;">
													                  	<td width="100%" colspan="4">
													                  		<table width="100%">
													                  					<tr>
																							<td>
															                             		<table width="100%">
															                             				<tr>
																						                   <td height="1" class="body" width="100%" colspan="4"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
																						                     <tr>
																						                       <td ><img src="images/01.gif" width="5" height="5" /></td>
																						                       <td width="914" background="images/02.gif"></td>
																						                       <td><img src="images/03.gif" width="5" height="5" /></td>
																						                     </tr>
																						                     <tr>
																						                       <td width="5"  background="images/left.gif"></td>
																						                       <td valign="top">
																						                       <table width="100%" cellspacing="1" cellpadding="2" >
																						                          	  <logic:notEmpty name="CME" property="syllabusEntryHeadingDescTos">
																									                   <nested:iterate id="CME1" property="syllabusEntryHeadingDescTos" indexId="cnt">
																									                   	<%
																									                   		String hId="headings_"+CME.getPosition()+"_"+cnt;
																									                   		String hId1="head_"+CME.getPosition()+"_"+cnt;
																									                   		String method2="toggle1("+CME.getPosition()+","+cnt+")";
																									                   		String headPlus="HP_"+CME.getPosition()+"_"+cnt;
																									                   		String headMinus="HM_"+CME.getPosition()+"_"+cnt;
																									                   	%>
																						                          	 <tr  height="10">
																														<td width="5%"></td>
																														<td width="95%" style="" class="tdHeadings" onclick="<%=method2 %>">
																															<table width="100%">
																				                          	 	 	 			<tr>
																				                          	 	 	 				<td width="5%" align="right" style="" id="<%=headPlus %>"><img  src="images/syllabusEntry_collapse.png" height="20" width="20"/></td>
								                          	 	 	 																<td width="5%" align="right" style="display: none;" id="<%=headMinus %>"><img  src="images/syllabusEntry_expand.png" height="20" width="20"/></td>
																				                          	 	 	 				<td width="95%" align="left">
																				                          	 	 	 				<logic:notEmpty name="CME1" property="tempHead">
																				                          	 	 	 					<span class="dropt" title="Click here."><bean:write name="CME1" property="tempHead"/></span>
																				                          	 	 	 				</logic:notEmpty>
																				                          	 	 	 				<logic:empty name="CME1" property="tempHead">
																				                          	 	 	 					<span class="dropt" title="Click here."><bean:write name="CME1" property="headingTemplate"/>-<c:out value="${cnt + 1}"/></span>
																				                          	 	 	 				</logic:empty>
																				                          	 	 	 				</td>
																				                          	 	 	 			</tr>
																				                          	 	 	 		</table>
																						                             	</td>
																						                          	 </tr>
																						                          	 <tr id="<%=hId1 %>" style="display: none;">
																						                          	 	<td width="5%"></td>
																						                          	 	<td width="95%">
																						                          	 		<table width="100%">
																						                          	 			<tr>
																																	<td width="15%" class="row-odd" ><div align="right">
																									                             	<span class="Mandatory"></span>Heading:</div>
																									                             	</td>
																										                             <td width="85%" height="25" class="row-even" colspan="3">
																										                           		<nested:text property="heading"  size="90" styleId="<%=hId %>" maxlength="250"></nested:text>
																										                             </td>
																									                          	 </tr>
																									                          	  <tr>
																																	<td width="15%" class="row-odd" ><div align="right">
																									                             	<span class="Mandatory">*</span><bean:write name="CME1" property="descriptionTemplate"/>:</div>
																									                             	</td>
																										                             <td width="85%" height="25" class="row-even" colspan="3">
																										                           		<nested:textarea property="description" style="width: 100%" cols="80" rows="5"></nested:textarea>
																										                             </td>
																									                          	 </tr>
																						                          	 		</table>
																						                          	 	</td>
																						                          	 </tr>
																						                          	 </nested:iterate>
											                  													</logic:notEmpty>
											                  													<tr height="10">
																													<td  height="25" align="right" colspan="4">
																														<table width="100%">
																															<tr>
																																<td align="right" width="45%">
																																	<html:button property="" styleClass="btn" value="Add More Headings" onclick="<%=method%>"></html:button>
																																</td>
																																<td width="0%"></td>
																							                   					<logic:equal value="true" name="CME" property="headingsFlag">
																								                   					<td  align="left" width="95%">
																								                   						<html:button property="" styleClass="btn" value="Remove More Headings" onclick="<%=method1 %>"></html:button>
																								                   					</td>
																					                   							</logic:equal>
																															</tr>
																														</table>
																								               		</td>
																								               	</tr>
																						                       </table></td>
																						                       <td width="5" height="30"  background="images/right.gif"></td>
																						                     </tr>
																						                     <tr>
																						                       <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
																						                       <td background="images/05.gif"></td>
																						                       <td><img src="images/06.gif" /></td>
																						                     </tr>
																						                   </table></td>
																						                 </tr>
															                             		</table>
															                             	</td>
															                          	 </tr>
													                  		</table>
													                  	</td>
											               				
											            			  </tr>
											            			  <tr><td height="0"></td> </tr>
								                			 		  <!--   end headings and description    -->
								                          	 		</nested:iterate>
						                 						</logic:notEmpty>
								                       </table></td>
								                       <td width="5" height="30"  background="images/right.gif"></td>
								                     </tr>
								                     <tr>
								                       <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
								                       <td background="images/05.gif"></td>
								                       <td><img src="images/06.gif" /></td>
								                     </tr>
								                   </table></td>
								                 </tr>
								                 <tr>
													<td  height="25" align="left" width="100%" colspan="3">
														<table width="100%">
															<tr>
																<td  align="right" width="45%">
																	<html:button property="" styleClass="btn" value="Add More Units" onclick="addMoreUnitsAndHrs()"></html:button>
																</td>
																<td width="0%"></td>
								                   					<logic:equal value="true" property="unitsFlag" name="syllabusEntryHodApprovalForm">
									                   					<td  align="left" width="95%">
									                   						<html:button property="" styleClass="btn" value="Remove More Units" onclick="removeMoreUnitsAndHrs()"></html:button>
									                   					</td>
								                 					</logic:equal>
															</tr>
														</table>
								               		</td>
								               	</tr>
								                  <tr><td height="1" width="100%"></td></tr>
								                 <!--end units and hours
								                 -->
		                             	</table>
		                             </td>
		                          </tr>
			                       <!--
			                       end for units
			                       -->
	                       </table></td>
	                       <td width="5" height="30"  background="images/right.gif"></td>
	                     </tr>
	                     <tr>
	                       <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
	                       <td background="images/05.gif"></td>
	                       <td><img src="images/06.gif" /></td>
	                     </tr>
	                   </table></td>
		           </tr>
		            <tr><td height="10" width="100%"></td></tr>
                 <tr>
                   <td height="20"  valign="top" class="body" width="100%" colspan="4">
                   <table width="100%" cellspacing="1" cellpadding="2"><tr ></tr>
                     <tr>
                       <td height="20"  >   
		                <table width="100%"  border="0" cellspacing="0" cellpadding="0">
		                  <tr>
		                    <td height="20"><div align="center">
		                    		<html:button property="" styleClass="formbutton" value="Save Draft" onclick="saveDraft()"></html:button>
		                      		<html:button property="" styleClass="formbutton" value="Approve" onclick="sendForApproval()"></html:button>
									<html:button property="" styleClass="formbutton" value="Close" onclick="firstPage()"></html:button>
									<html:button property="" styleClass="formbutton" value="Preview" onclick="preview()"></html:button>
		                    </div></td>
		                    </tr>
		                </table>
                        </td>
                     </tr>
                   </table></td>
                </tr>
                 <tr>
                   <td height="10"  class="body" ></td>
                 </tr>
              </table>
            </div></td>
        <td width="13" valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
     <tr>
       <td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
       <td width="100%" background="images/TcenterD.gif"></td>
       <td><img src="images/Tright_02.gif" width="9" height="29"></td>
     </tr>
   </table>
   </td>
 </tr>
</table>
</html:form>
<script type="text/javascript">
var val= document.getElementById("tempChangeInSyllabus").value;
	if(val=='yes'){
	document.getElementById("changeSyllabus1").checked = true;
	document.getElementById("changeSyllabus2").checked = false;
	document.getElementById("change").style.display = '';
	document.getElementById("change1").style.display = '';
	document.getElementById("change2").style.display = '';
	document.getElementById("change3").style.display = '';
 	}else{
 		 document.getElementById("changeSyllabus2").checked = true;
 		document.getElementById("changeSyllabus1").checked = false;
     	}
	hideEditors();
	var unitOrHead=document.getElementById("unitOrHead").value;
	if(unitOrHead=='unit'){
		openUnits();
		var focusField=document.getElementById("unitsFocus").value;
		   if(focusField != 'null'){  
		    if(document.getElementById(focusField)!=null)      
		           document.getElementById(focusField).focus();
		}
	}else if(unitOrHead=='head'){
		var focusField1=document.getElementById("headingsFocus").value;
		var temp=document.getElementById("tempHeadingsFocus").value;
		var position=document.getElementById("position").value;
		openUnitsAndHeadings(position,temp);
		   if(focusField1 != 'null'){  
		    if(document.getElementById(focusField1)!=null)
		           document.getElementById(focusField1).focus();
		}
	}
	</script>