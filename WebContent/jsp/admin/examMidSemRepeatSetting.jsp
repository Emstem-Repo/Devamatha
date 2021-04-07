<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@page import="java.util.Map,java.util.HashMap"%>
<link type="text/css"
	href="jquery/css/ui-lightness/jquery-ui-1.8.18.custom.css"
	rel="stylesheet" />
<script src="jquery/js/jquery-ui-1.10.0.custom.min.js"
	type="text/javascript"></script>
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

.ui-timepicker {
	background: #236b8e;
	border: 1px solid #555;
	color: #ffffff;
	font-family: Arial;
	font-size: 11px;
	margin-left: 10px
}
</style>
<script type="text/javascript">
$(document).ready(function(){
    $("#applicationOpenFrom").datepicker({
    	 dateFormat:"dd/mm/yy",
        onSelect: function(selected) {
          $("#applicationOpenTill").datepicker("option","minDate", selected);
        }
    });
    $("#applicationOpenTill").datepicker({
    	dateFormat:"dd/mm/yy", 
        onSelect: function(selected) {
           $("#applicationOpenFrom").datepicker("option","maxDate", selected);
           $("#feePaymentTill").datepicker("option","minDate", selected);
        }
    });  
    $("#feePaymentTill").datepicker({
    	dateFormat:"dd/mm/yy",
        onSelect: function(selected) {
          $("#applicationOpenTill").datepicker("option","maxDate", selected);
        }
    });
    $("#applicationOpenFrom").change(function(){
        if($("#applicationOpenFrom").val()==''){
        	$("#applicationOpenTill").datepicker("option","minDate", null);
        }
    });
    $("#applicationOpenTill").change(function(){
        if($("#applicationOpenTill").val()==''){
        	$("#applicationOpenFrom").datepicker("option","maxDate", null);
            $("#feePaymentTill").datepicker("option","minDate", null); 
        }
    });
    $("#feePaymentTill").change(function(){
        if($("#feePaymentTill").val()==''){
        	 $("#applicationOpenTill").datepicker("option","maxDate", null);
        }
    });
});

$(document).ready(function() {
  $('#Submit').click(function(){
       var academicYear = $('#academicYear').val();
       var examType = $('#examType').val();
       var examName = $('#examName').val();
       var applicationOpenFrom = $('#applicationOpenFrom').val();
       var applicationOpenTill = $('#applicationOpenTill').val();
       var feePaymentTill = $('#feePaymentTill').val();
       var feesPerSubject = $('#feesPerSubject').val();
       if(academicYear== '' && examType=='' && examName=='' && applicationOpenFrom==''
           && applicationOpenTill=='' && feePaymentTill=='' && feesPerSubject==''){
		    	 $('#errorMessage').slideDown().html("<span>Academic Year is Required <br>Exam Type is Required <br>Exam Name is Required <br>Application Start Date is Required.<br>"+
		    	    	       	    	   "Application End Date is Required<br>Fee Payment End Date is Required<br>Fees Per Subject is Required.</span>");
		         return false;
        }else  if(examName=='' && applicationOpenFrom==''
            && applicationOpenTill=='' && feePaymentTill=='' && feesPerSubject==''){
	     	   $('#errorMessage').slideDown().html("<span>Exam Name is Required <br>Application Start Date is Required.<br>"+
	     	    	       	    	   "Application End Date is Required<br>Fee Payment End Date is Required<br>Fees Per Subject is Required.</span>");
	           return false;
         }else if(academicYear== ''){
	           $('#errorMessage').slideDown().html("<span>Please Select Academic Year.</span>");
	           return false;
         }else if(examType== ''){
        	   $('#errorMessage').slideDown().html("<span>Please Select Exam Type.</span>");
               return false;
         }else if(examName== ''){
	       	   $('#errorMessage').slideDown().html("<span>Please Select Exam Name.</span>");
	           return false;
     	 }else if(applicationOpenFrom== ''){
	       	   $('#errorMessage').slideDown().html("<span>Please Enter Application Start Date.</span>");
	           return false;
     	 }else if(validateDate(applicationOpenFrom)==false){
        	   $('#errorMessage').slideDown().html("<span>Please Enter Valid Application Start Date.</span>");
        	   return false;
         }else if(applicationOpenTill== ''){
        	   $('#errorMessage').slideDown().html("<span>Please Enter Application End Date.</span>");
               return false;
         }else if(validateDate(applicationOpenTill)==false){
      	       $('#errorMessage').slideDown().html("<span>Please Enter Valid Application End Date.</span>");
    	       return false;
         }else if(feePaymentTill== ''){
      	       $('#errorMessage').slideDown().html("<span>Please Enter Fees Payment End Date.</span>");
               return false;
         }else if(validateDate(feePaymentTill)==false){
  	           $('#errorMessage').slideDown().html("<span>Please Enter Valid Fees Payment End Date.</span>");
	           return false;
         }else if(feesPerSubject== ''){
      	       $('#errorMessage').slideDown().html("<span>Please Enter Fees Fees Per Subject.</span>");
               return false;
         }else{
   		      document.examMidSemRepeatSettingForm.submit();
         }
      });
});	
	function editMidSemRepeatSetting(id) {
		document.location.href = "examMidSemRepeatSetting.do?method=editMidSemRepeatSetting&repeatSettingId="+id;
	}
    function resetMidSemRepeatSettings() {
    	document.getElementById("academicYear").value = "";
		document.getElementById("examType").value = "";
		document.getElementById("examName").value = "";
		document.getElementById("errorMessage").value = "";
		document.getElementById("applicationOpenFrom").value = "";
		document.getElementById("applicationOpenTill").value = "";
		document.getElementById("feePaymentTill").value = "";
		document.getElementById("feesPerSubject").value = "";
		$("#applicationOpenFrom").datepicker("option","maxDate", null);
		$("#applicationOpenTill").datepicker("option","minDate", null);
		$("#feePaymentTill").datepicker("option","minDate", null);
		if(document.getElementById("method").value=="updateMidSemRepeatSetting"){
			document.getElementById("academicYear").value = document.getElementById("origAcademicYear").value;
			document.getElementById("examType").value = document.getElementById("origExamType").value;
			document.getElementById("examName").value = document.getElementById("origExamName").value;
			document.getElementById("applicationOpenFrom").value = document.getElementById("origApplicationStartDate").value;
			document.getElementById("applicationOpenTill").value =document.getElementById("origApplicationEndDate").value;
			document.getElementById("feePaymentTill").value = document.getElementById("origFeePaymentEndDate").value;
			document.getElementById("feesPerSubject").value = document.getElementById("origFeePerSubject").value;
		}
		resetErrMsgs();
	}
	function deleteMidSemRepeatSetting(id) {
		deleteConfirm = confirm("Are you sure you want to delete this entry?");
		if (deleteConfirm) {
			document.location.href = "examMidSemRepeatSetting.do?method=deleteMidSemRepeatSetting&repeatSettingId="+id;
		}
	}
	function validateDate(testdate) {
	    var date_regex = /^(0?[1-9]|[12][0-9]|3[01])[\/\-](0?[1-9]|1[012])[\/\-]\d{4}$/ ;
	    if(!date_regex.test(testdate)){
	    	return false;
	    }else{
		return true;
	    }    
	}
	function getExamsByExamTypeAndYear() {
		var examType=document.getElementById("examType").value;
		var year=document.getElementById("academicYear").value;
		getExamNameByExamTypeAndYear("examMap", examType,year, "examName", updateExamName);
	}
	function updateExamName(req) {
		updateOptionsFromMap(req, "examName", "- Select -");
		updateCurrentExam(req, "examName");
	}
</script>

<html:form action="/examMidSemRepeatSetting" method="post">
	<html:hidden property="formName" value="examMidSemRepeatSettingForm" />
	<html:hidden property="pageType" value="1" />
	<html:hidden property="disableFields" name="examMidSemRepeatSettingForm" styleId="disableFields"/>
	<html:hidden property="origAcademicYear" name="examMidSemRepeatSettingForm" styleId="origAcademicYear"/>
	<html:hidden property="origApplicationEndDate" name="examMidSemRepeatSettingForm" styleId="origApplicationEndDate"/>
	<html:hidden property="origApplicationStartDate" name="examMidSemRepeatSettingForm" styleId="origApplicationStartDate"/>
	<html:hidden property="origExamName" name="examMidSemRepeatSettingForm" styleId="origExamName"/>
	<html:hidden property="origExamType" name="examMidSemRepeatSettingForm" styleId="origExamType"/>
	<html:hidden property="origFeePaymentEndDate" name="examMidSemRepeatSettingForm" styleId="origFeePaymentEndDate"/>
	<html:hidden property="origFeePerSubject" name="examMidSemRepeatSettingForm" styleId="origFeePerSubject"/>
	
	<c:choose>
		<c:when test="${operation == 'edit'}">
			<html:hidden property="method" styleId="method"
				value="updateMidSemRepeatSetting" />
		</c:when>
		<c:otherwise>
			<html:hidden property="method" styleId="method"
				value="saveMidSemRepeatSetting" />
		</c:otherwise>
	</c:choose>

	<table width="100%" border="0">
		<tr>

			<td><span class="Bredcrumbs"> <bean:message
				key="knowledgepro.admin" /> <span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.admin.mid.sem.repeat.setting" /> &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> <bean:message
						key="knowledgepro.admin.mid.sem.repeat.setting" /></strong></td>

					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" cellspacing="1" cellpadding="2">
						<tr bgcolor="#FFFFFF">
							<td height="20" colspan="4">
							<div align="right"><FONT color="red"> <span
								class='MandatoryMark'><bean:message
								key="knowledgepro.mandatoryfields" /></span></FONT></div>
							<font color="red" size="2">
							<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
							<FONT color="green"> <html:messages id="msg"
								property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
								<br>
							</html:messages> </FONT></div>
							</font></td>
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
											<td width="25%" height="25" class="row-odd">
											<div align="right"><span class="Mandatory">*</span><bean:message
												key="knowledgepro.admin.year" />:</div>
											</td>
											<td width="25%" height="25" class="row-even">
											<div align="left"><input type="hidden" id="tempyear"
												name="appliedYear"
												value="<bean:write name="examMidSemRepeatSettingForm" property="academicYear"/>" />
											<html:select property="academicYear" styleId="academicYear"
												styleClass="combo" onchange="getExamsByExamTypeAndYear()">
												<html:option value="">
													<bean:message key="knowledgepro.admin.select" />
												</html:option>
												<cms:renderAcademicYear></cms:renderAcademicYear>
											</html:select></div>
											</td>
											<td width="25%" height="25" class="row-odd">
											<div align="right"><span class="Mandatory">*</span><bean:message
												key="knowledgepro.exam.ExamMarksEntry.Students.classes.Exam.Type" />:</div>
											</td>
											<td width="25%" height="25" class="row-even"><html:select
												property="examType" styleClass="combo" styleId="examType"
												onchange="getExamsByExamTypeAndYear()">
												<html:option value="">
													<bean:message key="knowledgepro.admin.select" />
												</html:option>
												<logic:notEmpty name="examMidSemRepeatSettingForm"
													property="examTypeMap">
													<html:optionsCollection property="examTypeMap"
														name="examMidSemRepeatSettingForm" label="value" value="key" />
												</logic:notEmpty>
											</html:select></td>
										</tr>
										<tr>
											<td height="25" class="row-odd" width="25%">
											<div align="right"><span class="Mandatory">*</span><bean:message
												key="knowledgepro.exam.ExamName" /></div>
											</td>
											<td height="25" class="row-even"><html:select
												name="examMidSemRepeatSettingForm" property="examName"
												styleId="examName" styleClass="comboLarge"
												onchange="getList1()">
												<html:option value="">
													<bean:message key="knowledgepro.admin.select" />
												</html:option>
												<logic:notEmpty name="examMidSemRepeatSettingForm"
													property="examNameMap">
													<html:optionsCollection property="examNameMap"
														name="examMidSemRepeatSettingForm" label="value" value="key" />
												</logic:notEmpty>
											</html:select></td>
											<td width="25%" height="25" class="row-odd">
											<div align="right"><span class="Mandatory">*</span><bean:message
												key="knowledgepro.admin.mid.sem.repeat.setting.application.open.from" />:</div>
											</td>
											<td width="25%" height="25" class="row-even"><html:text
												name="examMidSemRepeatSettingForm"
												property="applicationOpenFrom" styleId="applicationOpenFrom"
												size="10" maxlength="10" /> </td>
										</tr>
										<tr>
											<td width="25%" height="25" class="row-odd">
											<div align="right"><span class="Mandatory">*</span><bean:message
												key="knowledgepro.admin.mid.sem.repeat.setting.application.open.till" />:</div>
											</td>
											<td width="25%" height="25" class="row-even"><html:text
												name="examMidSemRepeatSettingForm"
												property="applicationOpenTill" styleId="applicationOpenTill"
												size="10" maxlength="10" /> </td>
											<td width="25%" height="25" class="row-odd">
											<div align="right"><span class="Mandatory">*</span><bean:message
												key="knowledgepro.admin.mid.sem.repeat.setting.fee.payment.til.date" />:</div>
											</td>
											<td width="25%" height="25" class="row-even"><html:text
												name="examMidSemRepeatSettingForm" property="feePaymentTill"
												styleId="feePaymentTill" size="10" maxlength="10" /> </td>
										</tr>
										<tr>
											<td width="25%" height="25" class="row-odd">
											<div align="right"><span class="Mandatory">*</span><bean:message
												key="knowledgepro.admin.mid.sem.repeat.setting.fee.per.subject" />:</div>
											</td>
											<td width="25%" height="25" class="row-even" colspan="3">
											<html:text name="examMidSemRepeatSettingForm"
												property="feesPerSubject" styleId="feesPerSubject" size="10"
												maxlength="10" onkeypress="return isNumberKey(event)" /></td>
										</tr>
									</table>
									</td>
									<td width="5" height="29" background="images/right.gif"></td>
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
								<tr><td style="height: 10px" align="center" colspan="3"></td></tr>
								<tr>
									<td width="45%" height="35">
									<div align="right"><c:choose>
										<c:when test="${operation == 'edit'}">
											<html:submit property="" styleClass="formbutton"
												value="Update" styleId="Submit"></html:submit>
										</c:when>
										<c:otherwise>
											<html:submit property="" styleClass="formbutton"
												value="Submit" styleId="Submit"></html:submit>
										</c:otherwise>
									</c:choose></div>
									</td>
									<td width="2%"></td>
									<td width="53%"><html:button property=""
										styleClass="formbutton" value="Reset"
										onclick="resetMidSemRepeatSettings()" styleId="reset"></html:button></td>
								</tr>								
								<tr><td style="height: 10px" align="center" colspan="3"></td></tr>								
							</table>
							</td>
						</tr>
						<!--here wil start-->
						<logic:notEmpty property="repeatSettingMap" name="examMidSemRepeatSettingForm">
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
									<td valign="top" class="news">
								<table width="100%" cellspacing="1" cellpadding="2">
								 <tr>
								    <td width="5%" class="row-odd" align="center">Sl No</td>
									<td width="20%" height="25" class="row-odd" align="center"><bean:message
										key="knowledgepro.exam.blockUnblock.examName" /></td>
									<td width="15%" class="row-odd" align="center"><bean:message
										key="knowledgepro.admin.mid.sem.repeat.setting.application.open.from"/></td>
									<td width="15%" class="row-odd" align="center">
										<div align="center"><bean:message
											key="knowledgepro.admin.mid.sem.repeat.setting.application.open.till" /></div>
									</td>
									<td width="14%" height="25" class="row-odd" align="center">
										<div align="center"><bean:message key="knowledgepro.admin.mid.sem.repeat.setting.fee.payment.til.date" /></div>
									</td>
									<td width="12%" height="25" class="row-odd" align="center">
										<div align="center"><bean:message key="knowledgepro.admin.mid.sem.repeat.setting.fee.per.subject" /></div>
									</td>
									<td width="9%" class="row-odd">
										<div align="center">Edit</div>
									</td>
									<td width="9%" class="row-odd">
										<div align="center">Delete</div>
									</td>
								</tr>
									<logic:iterate id="repeatMap" property="repeatSettingMap" name="examMidSemRepeatSettingForm" indexId="count">
									  <tr>
									  <td class="row-even" align="center">
											<c:out value="${count+1}"/></td>
									<td class="row-even" align="left"><bean:write name="repeatMap" property="value.examName"/> </td>
									<td class="row-even" align="center"><bean:write name="repeatMap" property="value.applicationStartDate"/> </td>
									<td class="row-even" align="center"><bean:write name="repeatMap" property="value.applicationEndDate"/></td>
									<td class="row-even" align="center"><bean:write name="repeatMap" property="value.feePaymentEndDate"/></td>
									<td class="row-even" align="center"><bean:write name="repeatMap" property="value.feesPerSubject"/></td>
									<td class="row-even" align="center">
								     <div align="center"><img src="images/edit_icon.gif"
										width="16" height="18"  style="cursor:pointer" 
										onclick="editMidSemRepeatSetting('<bean:write name="repeatMap" property="key"/>')">
									</div>
									</td>
									<td class="row-even" align="center">
									<div align="center"><img src="images/delete_icon.gif"
										width="16" height="16" style="cursor:pointer" 
										onclick="deleteMidSemRepeatSetting('<bean:write name="repeatMap" property="key"/>')">
									</div>
									</td>
									  </tr>
									</logic:iterate>
									</table>
									</td>
									<td width="5" height="29" background="images/right.gif"></td>
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
<script type="text/javascript">
var disable=document.getElementById("disableFields").value;
if(disable=="Yes"){
	document.getElementById("academicYear").disabled = true;
	document.getElementById("examType").disabled = true;
	document.getElementById("examName").disabled = true;
}else{
	document.getElementById("academicYear").disabled = false;
	document.getElementById("examType").disabled = false;
	document.getElementById("examName").disabled = false;
}
var year=document.getElementById("tempyear").value;
if(year!=null && year!=""){
	document.getElementById("academicYear").value=year;
}
</script>