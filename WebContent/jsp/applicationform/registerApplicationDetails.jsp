<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@ taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<html lang="en">

<head>
<meta charset="UTF-8">
<title>Knowledge Pro</title>

<script type="text/javascript" src="js/ajax/AjaxUtil.js"></script>
<script type="text/javascript" src="js/ajax/Ajax.js"></script>

<script src="https://ajax.aspnetcdn.com/ajax/jQuery/jquery-3.4.1.min.js"
	type="text/javascript"></script>

<link
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css"
	rel="stylesheet" type="text/css" />
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.3.0/css/datepicker.css"
	rel="stylesheet" type="text/css" />

<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.3.0/js/bootstrap-datepicker.js"></script>


<script
	src='http://cdnjs.cloudflare.com/ajax/libs/bootstrap-validator/0.4.5/js/bootstrapvalidator.min.js'></script>
<script type="text/javascript"
	src="js/admission/UniqueIdRegistrationPage.js"></script>


 <!-- for cache controling with html code-->
<meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />
 
 <!-- for cache controling with jsp code-->
<% 
response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
response.setHeader("Expires", "0"); // Proxies.
%>



<script type="text/javascript">
function getCaste(religionId) {
		
	getSubCasteByReligion("casteMap", religionId, "casteId",
			updateCaste);

}
function updateCaste(req) {
	updateOptionsFromMapMultiselect(req, "casteId", "- Select -");
}

function getMphil(mphil) {

	if(mphil=='true'){
		document.getElementById("isMphilTopic").style.display = "block";
		document.getElementById("isMphilUniversity").style.display = "block";
	}else if(mphil=='false'){
		document.getElementById("isMphilTopic").style.display = "none";
		document.getElementById("isMphilUniversity").style.display = "none";
	}

	
	
} 

function getPhd(phd){

	if(phd=='true'){
		document.getElementById("isPhdTopic").style.display = "block";
		document.getElementById("isPhdUniversity").style.display = "block";
	}else if(phd=='false'){
		document.getElementById("isPhdTopic").style.display = "none";
		document.getElementById("isPhdUniversity").style.display = "none";
	}
}


function getNetQualification(net){

	if(net=='true'){
		document.getElementById("isNetDetails").style.display = "block";
	}else if(net=='false'){
		document.getElementById("isNetDetails").style.display = "none";
	}
	
}

function getJrfQualification(jrf){
	if(jrf=='true'){
		document.getElementById("isJrfDetails").style.display = "block";
	}else if(jrf=='false'){
		document.getElementById("isJrfDetails").style.display = "none";
	}
}

function submitBooksAdd(method,mode){
	document.getElementById("method").value=method;
	document.getElementById("mode").value=mode;
	document.applicationRegistrationForm.submit();
}

function register(method,mode){
	document.getElementById("method").value=method;
	document.getElementById("mode").value=mode;
	document.applicationRegistrationForm.submit();
}

function isRCSyrian(valu){
	var religionId=document.getElementById("religionId").value;
	if(religionId==3 && valu==160){
		document.getElementById("subCaste").style.display = "block";
	}else{
		document.getElementById("subCaste").style.display = "none";
	}
		
}



</script>

<style type="text/css">
.style1{
	border-top: 1px solid #8c8b8b;
}

</style>



</head>

<body>
<html:form
	styleClass="well form-horizontal" action="/applicationFormRegistration"
	method="post">
	<html:hidden property="formName" value="applicationRegistrationForm" styleId="applicationRegistrationForm" />
	<html:hidden property="pageType" value="1" />
	<html:hidden property="method" value="" styleId="method" />
	<html:hidden property="mode" styleId="mode" value="" />
	<html:hidden property="isMphil" styleId="isMphil"
		name="applicationRegistrationForm" />
<div class="container">
	<fieldset><legend>
	<center>
	<h2><b>Bharata Mata College, Thrikkakara, Kochi-682021</b></h2>
	</center>
	</legend><br>
	
	<legend>
	<center>
	<h4><b>Application for Appointment as Assistant Professor</b></h4>
	</center>
	</legend><br>
	

							
	<div class="alert alert-danger" id="error_message">
	Fields marked with <span style="color: red;"><sup>*</sup></span> are
	mandatory.<br></br>
	<html:errors /></div>

	<div class="form-group"><label class="col-md-2 control-label">Name<span
		style="color: red;"><sup>*</sup></span></label>
	<div class="col-md-4 inputGroupContainer">
	<div class="input-group"><span class="input-group-addon"><i
		class="glyphicon glyphicon-user"></i></span> <html:text property="firstName"
		styleId="firstName" name="applicationRegistrationForm"
		styleClass="form-control"></html:text></div>
	</div>

	<label class="col-md-2 control-label">Department<span
		style="color: red;"><sup>*</sup></span></label>
	<div class="col-md-4 selectContainer">
	<div class="input-group"><span class="input-group-addon"></span>
	<html:select property="departmentId" name="applicationRegistrationForm"
		styleId="departmentId" styleClass="form-control selectpicker">
		<html:option value="">
			<bean:message key="knowledgepro.admin.select" />
		</html:option>
		<logic:notEmpty name="applicationRegistrationForm"
			property="departmentMap">
			<html:optionsCollection property="departmentMap"
				name="applicationRegistrationForm" label="value" value="key" />
		</logic:notEmpty>

	</html:select></div>

	</div>
	</div>

	<div class="form-group"><label class="col-md-2 control-label">Email
	address<span style="color: red;"><sup>*</sup></span></label>
	<div class="col-md-4 inputGroupContainer">
	<div class="input-group"><span class="input-group-addon"><i
		class="glyphicon glyphicon-envelope"></i></span> <html:text
		property="emailId" styleId="emailId"
		name="applicationRegistrationForm" styleClass="form-control"></html:text>
	</div>
	</div>

	<label class="col-md-2 control-label">Mobile number<span
		style="color: red;"><sup>*</sup></span></label>
	<div class="col-md-4 inputGroupContainer">
	<div class="input-group"><span class="input-group-addon"><i
		class="glyphicon glyphicon-phone"></i></span> <html:text property="mobileNo"
		styleId="mobileNo" name="applicationRegistrationForm"
		styleClass="form-control" onkeypress="return isNumberKey(event)"
		maxlength="12"></html:text></div>
	</div>
	</div>


	<div class="form-group"><label class="col-md-2 control-label">Date
	of Birth<span style="color: red;"><sup>*</sup></span></label>
	<div class="col-md-4 inputGroupContainer">
	<div id="datepicker" class="input-group date"><span
		class="input-group-addon"><i
		class="glyphicon glyphicon-calendar"></i></span> <html:text
		name="applicationRegistrationForm"
		styleClass="form-control datepicker" styleId="dateOfBirth"
		property="dateOfBirth"></html:text> <script type="text/javascript">
                     		         $('.datepicker').datepicker({format: "dd/mm/yyyy",size: 'large'}); 
                     		</script></div>
	</div>




	<label class="col-md-2 control-label">Age<span
		style="color: red;"><sup>*</sup></span></label>
	<div class="col-md-4 inputGroupContainer">
	<div class="input-group"><span class="input-group-addon"><i
		class="glyphicon glyphicon-pencil"></i></span> <html:text property="age"
		styleId="age" name="applicationRegistrationForm"
		styleClass="form-control" onkeypress="return isNumberKey(event)"
		maxlength="2"></html:text></div>
	</div>
	</div>



	<div class="form-group"><label class="col-md-2 control-label">Religion<span
		style="color: red;"><sup>*</sup></span></label>
	<div class="col-md-4 inputGroupContainer">
	<div class="input-group"><span class="input-group-addon"></span><html:select
		property="religionId" name="applicationRegistrationForm"
		onchange="getCaste(this.value)" styleClass="form-control selectpicker"
		styleId="religionId">
		<html:option value="">
			<bean:message key="knowledgepro.admin.select" />
		</html:option>
		<html:optionsCollection name="applicationRegistrationForm"
			property="religionList" label="religionName" value="religionId" />
	</html:select></div>
	</div>

	<label class="col-md-2 control-label">Caste<span
		style="color: red;"><sup>*</sup></span></label>
	<div class="col-md-4 inputGroupContainer">
	<div class="input-group"><span class="input-group-addon"></span>
	<html:select property="casteId" name="applicationRegistrationForm"
		styleClass="form-control selectpicker" styleId="casteId" onchange="isRCSyrian(this.value)">
		<html:option value="">
			<bean:message key="knowledgepro.admin.select" />
		</html:option>
		<!--<c:if test="${casteMap != null}">
			<html:optionsCollection name="applicationRegistrationForm"
				property="casteMap" label="value" value="key" />
		</c:if>
		
		--><logic:notEmpty name="applicationRegistrationForm"
											property="casteMap">
											<html:optionsCollection property="casteMap"
												name="applicationRegistrationForm" label="value" value="key" />
										</logic:notEmpty>
	</html:select></div>
	</div>
	</div>
	
          <div class="form-group" id="subCaste"><label class="col-md-6 control-label">Diocese<span
		style="color: red;"><sup>*</sup></span></label>
			<div class="col-md-6 input-group text-left"><div class="input-group"><nested:select property="diocese"
				styleClass="form-control selectpicker" styleId="diocese" name="applicationRegistrationForm">
				<html:option value="">- Select -</html:option>
				<html:option value="Ernakulam-Angamaly">Ernakulam-Angamaly</html:option>
				<html:option value="OTHERS">OTHERS</html:option>
			</nested:select></div></div>

			</div>
			
			
			<div class="form-group"><label class="col-md-2 control-label">Address<span
		style="color: red;"><sup>*</sup></span></label></div>



	<div class="form-group">
	<div class="col-md-2"></div>
	<div class="col-md-10 text-right"><html:textarea
		property="address" styleId="address"
		name="applicationRegistrationForm" styleClass="form-control"></html:textarea></div>

	</div>
	
		<div class="form-group"><label class="col-md-2 control-label">Marital
	Status<span style="color: red;"><sup>*</sup></span></label>
	<div class="col-md-6 inputGroupContainer">
	<div class="input-group control-label"><html:radio
		property="maritalStatus" value="single"
		name="applicationRegistrationForm"></html:radio>&nbsp;&nbsp;&nbsp;Single&nbsp;
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <html:radio
		property="maritalStatus" value="married"
		name="applicationRegistrationForm"></html:radio>&nbsp;&nbsp;&nbsp;Married
	</div>
	</div>
	</div>
	
	
		<div class="form-group"><label class="col-md-2 control-label">Category<span style="color: red;"><sup>*</sup></span></label>
	<div class="col-md-6 inputGroupContainer">
	<div class="input-group control-label"><html:radio
		property="category" value="Open Merit"
		name="applicationRegistrationForm"></html:radio>&nbsp;&nbsp;&nbsp;Open Merit&nbsp;
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <html:radio
		property="category" value="Community"
		name="applicationRegistrationForm"></html:radio>&nbsp;&nbsp;&nbsp;Community&nbsp;
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<html:radio
		property="category" value="Both"
		name="applicationRegistrationForm"></html:radio>&nbsp;&nbsp;&nbsp;Both&nbsp;
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	</div>
	</div>
	</div>
	



<div class="style1"></div>




	<div class="form-group"><label class="col-md-2 control-label">Academic&nbsp;Qualifications<span
		style="color: red;"><sup>*</sup></span></label></div>

	<nested:iterate name="applicationRegistrationForm" id="educationList"
		property="educationalDetails" indexId="count">
		<logic:equal value="1" name="educationList" property="qualificationId">
			<div class="form-group"><label class="col-md-4 control-label">Under
			Graduation :</label></div>

			<div class="form-group"><label class="col-md-4 control-label">Subject</label>
			<div class="col-md-4 input-group text-left"><nested:text
				property="subjectName" styleId="ugSubject" styleClass="form-control"
				></nested:text></div>

			</div>
			<div class="form-group"><label
				class="col-md-4 control-label inputGroupContainer">College</label>
			<div class="col-md-4 input-group text-left"><nested:text
				property="collegeName" styleId="ugCollege" styleClass="form-control"
				></nested:text></div>
			</div>
			<div class="form-group"><label
				class="col-md-4 control-label inputGroupContainer">University</label>
			<div class="col-md-4 input-group text-left"><nested:text
				property="universityName" styleId="ugUniversity"
				styleClass="form-control"></nested:text></div>
			</div>
			<div class="form-group"><label
				class="col-md-4 control-label inputGroupContainer">Percentage of Marks/Grade</label>
			<div class="col-md-4 input-group text-left"><nested:text
				property="percentage" styleId="ugPercentage"
				styleClass="form-control"></nested:text></div>
			</div>
		</logic:equal>
		<logic:equal value="2" name="educationList" property="qualificationId">
			<div class="form-group"><label class="col-md-4 control-label">Post
			Graduation :</label></div>

			<div class="form-group"><label class="col-md-4 control-label">Subject</label>
			<div class="col-md-4 input-group text-left"><nested:text
				property="subjectName" styleId="pgSubject" styleClass="form-control"
				></nested:text></div>

			</div>
			<div class="form-group"><label
				class="col-md-4 control-label inputGroupContainer">College</label>
			<div class="col-md-4 input-group text-left"><nested:text
				property="collegeName" styleId="pgCollege" styleClass="form-control"
				></nested:text></div>
			</div>
			<div class="form-group"><label
				class="col-md-4 control-label inputGroupContainer">University</label>
			<div class="col-md-4 input-group text-left"><nested:text
				property="universityName" styleId="pgUniversity"
				styleClass="form-control"></nested:text></div>
			</div>
			<div class="form-group"><label
				class="col-md-4 control-label inputGroupContainer">Percentage of Marks/Grade</label>
			<div class="col-md-4 input-group text-left"><nested:text
				property="percentage" styleId="pgPercentage"
				styleClass="form-control"></nested:text></div>
			</div>
		</logic:equal>
		
		<logic:equal value="3" name="educationList" property="qualificationId">
		<div class="form-group"><label class="col-md-4 control-label inputGroupContainer">Rank Position</label>
			
			<div class="col-md-4 input-group text-left"><nested:select property="rankPosition"
				styleClass="form-control selectpicker" styleId="rankPosition">
				<html:option value="">- Select -</html:option>
				<html:option value="1">I</html:option>
				<html:option value="2">II</html:option>
				<html:option value="3">III</html:option>
			</nested:select></div>
			
			</div>
		</logic:equal>
		
		<logic:equal value="4" name="educationList" property="qualificationId">
			<div class="form-group"><label class="col-md-4 control-label">M.Phil
			:</label>
			<div class="col-md-4 inputGroupContainer">
			<div class="input-group control-label"><nested:radio
				property="isMphil" value="true" styleId="isMphil"
				onclick="getMphil(this.value)"></nested:radio>&nbsp;&nbsp;&nbsp;Yes&nbsp;
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <nested:radio
				property="isMphil" value="false" styleId="isMphil"
				onclick="getMphil(this.value)"></nested:radio>&nbsp;&nbsp;&nbsp;No</div>
			</div>
			</div>

			<div class="form-group" id="isMphilTopic"><label
				class="col-md-4 control-label inputGroupContainer">Topic</label>
			<div class="col-md-4 input-group text-left "><nested:text
				property="subjectName" styleId="mphilTopic"
				styleClass="form-control"></nested:text></div>
			</div>
			<div class="form-group" id="isMphilUniversity"><label
				class="col-md-4 control-label inputGroupContainer">University</label>
			<div class="col-md-4 input-group text-left"><nested:text
				property="universityName" styleId="mphilUniversity"
				styleClass="form-control"></nested:text></div>
			</div>
		</logic:equal>
		<logic:equal value="5" name="educationList" property="qualificationId">
			<div class="form-group"><label class="col-md-4 control-label">Ph.D
			:</label>
			<div class="col-md-4 inputGroupContainer">
			<div class="input-group control-label"><nested:radio
				property="isPhd" value="true" styleId="isPhd"
				onclick="getPhd(this.value)"></nested:radio>&nbsp;&nbsp;&nbsp;Yes&nbsp;
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <nested:radio
				property="isPhd" value="false" styleId="isPhd"
				onclick="getPhd(this.value)"></nested:radio>&nbsp;&nbsp;&nbsp;No</div>
			</div>
			</div>

			<div class="form-group" id="isPhdTopic"><label
				class="col-md-4 control-label inputGroupContainer">Topic</label>
			<div class="col-md-4 input-group text-left "><nested:text
				property="subjectName" styleId="phdTopic" styleClass="form-control"
				>
			</nested:text></div>
			</div>
			<div class="form-group" id="isPhdUniversity"><label
				class="col-md-4 control-label inputGroupContainer">University</label>
			<div class="col-md-4 input-group text-left"><nested:text
				property="universityName" styleId="phdUniversity"
				styleClass="form-control"></nested:text></div>
			</div>
		</logic:equal>

	</nested:iterate>
	
	
	
	<div class="style1"></div>


	<div class="form-group"><label class="col-md-2 control-label">NET
	Qualification<span style="color: red;"><sup>*</sup></span></label>
	<div class="col-md-6 inputGroupContainer">
	<div class="input-group control-label"><html:radio
		property="isNetQualification" value="true"
		name="applicationRegistrationForm"
		onclick="getNetQualification(this.value)" styleId="isNetQualification"></html:radio>&nbsp;&nbsp;&nbsp;Yes&nbsp;
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <html:radio
		property="isNetQualification" value="false"
		name="applicationRegistrationForm"
		onclick="getNetQualification(this.value)" styleId="isNetQualification"></html:radio>&nbsp;&nbsp;&nbsp;No
	</div>
	</div>
	</div>

	<div class="form-group" id="isNetDetails"><label
		class="col-md-4 control-label inputGroupContainer">Details :</label>
	<div class="col-md-4 input-group text-left"><html:text
		property="netDeatils" styleId="netDeatils"
		name="applicationRegistrationForm" styleClass="form-control"></html:text></div>
	</div>

	<div class="form-group"><label class="col-md-2 control-label">JRF
	Qualification<span style="color: red;"><sup>*</sup></span></label>
	<div class="col-md-6 inputGroupContainer">
	<div class="input-group control-label"><html:radio
		property="isJrfQualification" value="true"
		name="applicationRegistrationForm"
		onclick="getJrfQualification(this.value)" styleId="isJrfQualification"></html:radio>&nbsp;&nbsp;&nbsp;Yes&nbsp;
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <html:radio
		property="isJrfQualification" value="false"
		name="applicationRegistrationForm"
		onclick="getJrfQualification(this.value)" styleId="isJrfQualification"></html:radio>&nbsp;&nbsp;&nbsp;No
	</div>
	</div>
	</div>

	<div class="form-group" id="isJrfDetails"><label
		class="col-md-4 control-label inputGroupContainer">Details :</label>
	<div class="col-md-4 input-group text-left"><html:text
		property="jrfDeatils" styleId="jrfDeatils"
		name="applicationRegistrationForm" styleClass="form-control"></html:text></div>
	</div>
	
	<div class="style1"></div>

 
	<div class="form-group"><label class="col-md-2 control-label">Publications(Relevent)</label></div>
	<div class="form-group"><logic:notEmpty
		name="applicationRegistrationForm" property="booksAndArticles">
		<nested:iterate property="booksAndArticles"
			name="applicationRegistrationForm" id="booksAndArticlesList"
			indexId="count">
			<label class="col-md-2 control-label">Books :</label>
			<div class="col-md-4 inputGroupContainer">
			<div class="input-group"><span class="input-group-addon"><i
				class="glyphicon glyphicon-pencil"></i></span> <nested:text
				property="books" styleId="books" styleClass="form-control"></nested:text></div>
			</div>

			<label class="col-md-2 control-label">Articles :</label>
			<div class="col-md-4 inputGroupContainer">
			<div class="input-group"><nested:select property="articles"
				styleClass="form-control selectpicker" styleId="articles">
				<html:option value="">- Select -</html:option>
				<html:option value="Regional">Regional</html:option>
				<html:option value="National">National</html:option>
				<html:option value="International">International</html:option>
			</nested:select></div>
			</div>
		</nested:iterate>
	</logic:notEmpty></div>
	
	<div class="form-group">
	
         <div class="col-md-12 center-block">
         <div class="col-md-6 text-right">
         <html:submit property=""
		styleClass="formbutton" value="Add more" styleId="addMore"
		onclick="submitBooksAdd('resetBooksInfo','ExpAddMore'); return false;"></html:submit></div>
		<div class="col-md-6 text-left">
	<c:if test="${applicationRegistrationForm.bookSize>0}">
		<html:submit property="" styleClass="formbutton"
			value="Remove" styleId="addMore"
			onclick="submitBooksAdd('removeBooksInfo','ExpAddMore'); return false;"></html:submit>
	</c:if></div>


	</div></div>
	
		<div class="form-group"><label class="col-md-2 control-label">Research Publications(In UGC Approved journal)<span
		style="color: red;"><sup>*</sup></span></label></div>



	<div class="form-group">
	<div class="col-md-2"></div>
	<div class="col-md-10 text-right"><html:textarea
		property="ugcApprovedInformation" styleId="ugcApprovedInformation"
		name="applicationRegistrationForm" styleClass="form-control"></html:textarea></div>

	</div>
	
	
	
	
	
	<div class="style1"></div>

	<div class="form-group"><label class="col-md-2 control-label">Post&nbsp;Doctoral&nbsp;Experience</label></div>
	<div class="form-group"><logic:notEmpty
		name="applicationRegistrationForm" property="postDocExp">
		<nested:iterate property="postDocExp"
			name="applicationRegistrationForm" id="postDocExp" indexId="count">

			<label class="col-md-2 control-label">From&nbsp;Date :</label>
			<div class="col-md-2 inputGroupContainer">
			<div id="docFromDate" class="input-group"><span
				class="input-group-addon"><i
				class="glyphicon glyphicon-calendar"></i></span> <nested:text
				property="fromDate" styleId="postDocFromDate"
				styleClass="form-control docFromDate"></nested:text> <script
				type="text/javascript">
                     		         $('.docFromDate').datepicker({format: "dd/mm/yyyy",size: 'large'}); 
                     		</script></div>
			</div>

			<label class="col-md-2 control-label">To Date :</label>
			<div class="col-md-2 inputGroupContainer">
			<div id="docToDate" class="input-group date"><span
				class="input-group-addon"><i
				class="glyphicon glyphicon-calendar"></i></span> <nested:text
				property="toDate" styleId="postDocToDate"
				styleClass="form-control docToDate"></nested:text> <script
				type="text/javascript">
                     		         $('.docToDate').datepicker({format: "dd/mm/yyyy",size: 'large'}); 
                     		</script></div>
			</div>


			<label class="col-md-2 control-label">College :</label>
			<div class="col-md-2 inputGroupContainer">
			<div class="input-group"><span class="input-group-addon"><i
				class="glyphicon glyphicon-pencil"></i></span> <nested:text
				property="collageName" styleId="postDocCollageName"
				styleClass="form-control"></nested:text></div>
			</div>


		</nested:iterate>
	</logic:notEmpty></div>

	<div class="form-group">
	<div class="col-md-12 center-block">
	
	<div class="col-md-6 text-right">
	<html:submit property=""
		styleClass="formbutton" value="Add more" styleId="addMore"
		onclick="submitBooksAdd('resetPostDocInfo','ExpAddMore'); return false;"></html:submit></div>
		
		
		<div class="col-md-6 text-left">
	<c:if test="${applicationRegistrationForm.postDocSize>0}">
		<html:submit property="" styleClass="formbutton"
			value="Remove" styleId="addMore"
			onclick="submitBooksAdd('removePostDocInfo','ExpAddMore'); return false;"></html:submit>
	</c:if></div></div>
	</div>

	
	<div class="form-group"><label class="col-md-2 control-label">Teaching&nbsp;Experience</label></div>
	<div class="form-group"><logic:notEmpty
		name="applicationRegistrationForm" property="teachingExp">
		<nested:iterate property="teachingExp"
			name="applicationRegistrationForm" id="teachingExp" indexId="count">

			<label class="col-md-2 control-label">From&nbsp;Date :</label>
			<div class="col-md-2 inputGroupContainer">
			<div id="docFromDate" class="input-group"><span
				class="input-group-addon"><i
				class="glyphicon glyphicon-calendar"></i></span> <nested:text
				property="fromDate" styleId="teachingFromDate"
				styleClass="form-control docFromDate"></nested:text> <script
				type="text/javascript">
                     		         $('.docFromDate').datepicker({format: "dd/mm/yyyy",size: 'large'}); 
                     		</script></div>
			</div>

			<label class="col-md-2 control-label">To Date :</label>
			<div class="col-md-2 inputGroupContainer">
			<div id="docToDate" class="input-group date"><span
				class="input-group-addon"><i
				class="glyphicon glyphicon-calendar"></i></span> <nested:text
				property="toDate" styleId="teachingToDate"
				styleClass="form-control docToDate"></nested:text> <script
				type="text/javascript">
                     		         $('.docToDate').datepicker({format: "dd/mm/yyyy",size: 'large'}); 
                     		</script></div>
			</div>


			<label class="col-md-2 control-label">College :</label>
			<div class="col-md-2 inputGroupContainer">
			<div class="input-group"><span class="input-group-addon"><i
				class="glyphicon glyphicon-pencil"></i></span> <nested:text
				property="collageName" styleId="collageName"
				styleClass="form-control"></nested:text></div>
			</div>


		</nested:iterate>
	</logic:notEmpty></div>

	<div class="form-group">
	<div class="col-md-12 center-block">
	
	<div class="col-md-6 text-right">
	<html:submit property=""
		styleClass="formbutton" value="Add more" styleId="addMore"
		onclick="submitBooksAdd('resetTeachInfo','ExpAddMore'); return false;"></html:submit></div>
	
	<div class="col-md-6 text-left">
	<c:if test="${applicationRegistrationForm.teachingExpSize>0}">
		<html:submit property="" styleClass="formbutton"
			value="Remove" styleId="addMore"
			onclick="submitBooksAdd('removeTeachInfo','ExpAddMore'); return false;"></html:submit>
	</c:if></div>
	</div></div>
		
	<div class="style1"></div>

<div class="form-group"><label class="col-md-2 control-label">Additional&nbsp;Information-Regarding&nbsp;Academic&nbsp;Contributions<span
		style="color: red;"><sup>*</sup></span></label></div>



	<div class="form-group">
	<div class="col-md-12 input-group text-left"><html:textarea
		property="additionalInformation" styleId="additionalInformation"
		name="applicationRegistrationForm" styleClass="form-control"></html:textarea></div>

	</div><!--	
		
		
		
		
	
		<div class="form-group"><label class="col-md-4 control-label">Upload photo<span
		style="color: red;"><sup>*</sup></span></label>
			<div class="col-md-4  control-label"><html:file property="editDocument" styleId="editDoc" name="applicationRegistrationForm"></html:file>
			</div>

			</div>
	
			
          					
		 
	
	
    --><div class="form-group">
	<div class="col-md-12"><html:submit property=""
		styleClass="col-md-12 formbutton btn btn-primary" value="Submit & Pay Online"
		styleId="submit"
		onclick="register('registerProfeesor','register'); return false;"></html:submit>

	</div>
	</div>



	</fieldset></div>
</html:form>


<script type="text/javascript">
	if(document.getElementById("isMphil").checked == true) {
		document.getElementById("isMphilTopic").style.display = "block";
		document.getElementById("isMphilUniversity").style.display = "block";
	}
	else {
		var show = "<c:out value='${applicationRegistrationForm.isMphil}'/>";
		if(show=='true'){
			document.getElementById("isMphilTopic").style.display = "block";
			document.getElementById("isMphilUniversity").style.display = "block";
		}else {
			document.getElementById("isMphilTopic").style.display = "none";
			document.getElementById("isMphilUniversity").style.display = "none";
		}
	}

	if(document.getElementById("isPhd").checked == true) {
		document.getElementById("isPhdTopic").style.display = "block";
		document.getElementById("isPhdUniversity").style.display = "block";
	}
	else {
		var show = "<c:out value='${applicationRegistrationForm.isPhd}'/>";
		if(show=='true'){
			document.getElementById("isPhdTopic").style.display = "block";
			document.getElementById("isPhdUniversity").style.display = "block";
		}else {
			document.getElementById("isPhdTopic").style.display = "none";
			document.getElementById("isPhdUniversity").style.display = "none";
		}
	}

	if(document.getElementById("isNetQualification").checked == true) {
		document.getElementById("isNetDetails").style.display = "block";
	}
	else {
		var show = "<c:out value='${applicationRegistrationForm.isNetQualification}'/>";
		if(show=='true'){
			document.getElementById("isNetDetails").style.display = "block";
		}else {
			document.getElementById("isNetDetails").style.display = "none";
		}
	}

	if(document.getElementById("isJrfQualification").checked == true) {
		document.getElementById("isJrfDetails").style.display = "block";
	}
	else {
		var show = "<c:out value='${applicationRegistrationForm.isJrfQualification}'/>";
		if(show=='true'){
			document.getElementById("isJrfDetails").style.display = "block";
		}else {
			document.getElementById("isJrfDetails").style.display = "none";
		}
	}

	
		var religonId = "<c:out value='${applicationRegistrationForm.religionId}'/>";
		var casteId = "<c:out value='${applicationRegistrationForm.casteId}'/>";
		if(religonId==3 && casteId==160){
			document.getElementById("subCaste").style.display = "block";
		}else {
			document.getElementById("subCaste").style.display = "none";
		}
	</script>

</body>


</html>