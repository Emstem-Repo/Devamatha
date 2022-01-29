<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>

<%@page import="java.util.Map,java.util.HashMap"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<link href="styles.css" rel="stylesheet" type="text/css"/>
<script src="js/autocomplete/jquery1.7.2.js" type="text/javascript"></script>
<script src="js/autocomplete/jquery-ui.min1.8.js" type="text/javascript"></script>
<link href="js/autocomplete/jquery-ui1.8.css" rel="stylesheet" type="text/css" />

<style type="text/css">
.ui-autocomplete {
    position: absolute;
    cursor: default;
    height: 200px;
    width:100px;
    overflow-y: scroll;
    overflow-x: hidden;
    }
    body { font-family: sans-serif; font-size: 14px; margin: 0; padding: 0;}
    .ui-widget{ border: 1px solid #999; background: #FFF; cursor: default; overflow: auto; -webkit-box-shadow: 1px 4px 3px rgba(50, 50, 50, 0.64); -moz-box-shadow: 1px 4px 3px rgba(50, 50, 50, 0.64); box-shadow: 1px 4px 3px rgba(50, 50, 50, 0.64); }
</STYLE>
<SCRIPT type="text/javascript">


$(function(){
	var subjectCodeArray=$('#subjectCodeList').val().split(",");
	var data = Array();
	for(var i = 0; i < subjectCodeArray.length; i++)
	{
	  data.push(subjectCodeArray[i].trim());
	}
   $('#code').autocomplete({
	   source: function(req, response) { 
	    var re = $.ui.autocomplete.escapeRegex(req.term); 
	    var matcher = new RegExp( "^" + re, "i" ); 
	    response($.grep(data, function(value){return matcher.test(value); }) ); 
	    },
     minLength: 1
  });
   var subjectNameArray=$('#subjectNameList').val().split(",");
   var data1 = Array();
	for(var j = 0; j < subjectNameArray.length; j++)
	{
	  data1.push(subjectNameArray[j].trim());
	}
   $('#name').autocomplete({
	   source: function(req, response) { 
	    var re = $.ui.autocomplete.escapeRegex(req.term); 
	    var matcher = new RegExp( "^" + re, "i" ); 
	    response($.grep(data1, function(value){return matcher.test(value); }) ); 
	    },
    minLength: 1
   });

});

	function editSubject(id) {
		document.location.href = "SubjectEntry.do?method=editSubject&id="+id;
	}
	function deleteSubject(id) {
		deleteConfirm = confirm("Are you sure you want to delete this entry?");
		if (deleteConfirm) {
			document.location.href = "SubjectEntry.do?method=deleteSubject&id="
					+ id ;
		}
		function activateRadioButtons()
		{ 
			if(document.getElementById("optional2").value!=null)
			document.getElementById("optional2").checked = true;
			document.getElementById("secondlanguage2").checked = true;
		}
	}

	function reActivate(){
		var code = document.getElementById("code").value;
		document.location.href = "SubjectEntry.do?method=reActivateSubjectEntry&code="+code;
	}
	function clearMessages(){
		document.location.href = "SubjectEntry.do?method=initSubjectEntry";
	}
	
	function shows(obj,msg){
		
		document.getElementById("messageBox1").style.top=obj.offsetTop;
		document.getElementById("messageBox1").style.left=obj.offsetLeft+obj.offsetWidth+5;
		document.getElementById("contents").innerHTML=msg;
		document.getElementById("messageBox1").style.display="block";
	}function showss(obj,msgs){
		document.getElementById("messageBox").style.top=obj.offsetTop;
		document.getElementById("messageBox").style.right=obj.offsetRight+obj.offsetWidth+5;
		document.getElementById("content").innerHTML=msgs;
		document.getElementById("messageBox").style.display="block";
		}
	function hidess(){
		document.getElementById("messageBox").style.display="none";
	}function hides(){
		document.getElementById("messageBox1").style.display="none";
	}
	function getSubBySchNo(schemeNo){
		//document.getElementById("method").value="getSubjectEntry";
		//document.subjectEntryForm.submit();
		document.location.href = "SubjectEntry.do?method=getSubjectEntry&schemeNo="+schemeNo;
	}
</SCRIPT>

<html:form action="/SubjectEntry">
	<html:hidden property="formName" value="subjectEntryForm" />
	<html:hidden property="pageType" value="1" />
	<html:hidden property="subjectCodes" name="subjectEntryForm" styleId="subjectCodeList"/>
	<html:hidden property="subjectNames" name="subjectEntryForm" styleId="subjectNameList"/>
	<c:choose>
		<c:when
			test="${Update == null || Update == 'valid' || Update == 'add'}">
			<html:hidden property="method" styleId="method" value="addSubject" />
			
		</c:when>
		<c:otherwise>
			<html:hidden property="method" styleId="method" value="updateSubject" />
			<html:hidden property="editedcode" styleId="editedcode" />
			<html:hidden property="editedname" styleId="editedname" />
			<html:hidden property="editTheoryOrPractical" styleId="editTheoryOrPractical" />
			<html:hidden property="id" styleId="id" />
		</c:otherwise>
	</c:choose>
	<table width="100%" border="0">
		<tr>
			<td><span class="heading"> <bean:message
				key="knowledgepro.admin" /> <span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.admin.subject" /> &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> <bean:message
						key="knowledgepro.admin.subject" /></strong></td>
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
							</html:messages> </FONT></div>
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
                                    <td width="25%" height="25" class="row-odd">
										<div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.fee.semister" />:</div>
									</td>
									<td height="25" class="row-even" width="30%"><span class="star">
										<html:select property="schemeNo" styleId="schemeNo" styleClass="combo" onchange="getSubBySchNo(this.value)">
										<html:option value="0">ALL</html:option>
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
									</span>
									</td>
									<td width="25%" height="25" class="row-odd"></td>
									<td height="25" class="row-even" width="20%"></td>
                                   </tr>
										<tr>
											<td height="25" class="row-odd" width="20%">
											<div align="right"><span class="Mandatory">*</span> <bean:message
												key="knowledgepro.admin.subject.subject.code.disp" /></div>
											</td>
											<td height="25" class="row-even" width="30%"><span class="star">
											<html:text property="code" styleId="code"
												styleClass="TextBox"  size="16" maxlength="45" /> </span><span
												class="star"></span></td>
											<td class="row-odd" width="20%">
											<div align="right"><span class="Mandatory">*</span><bean:message
												key="knowledgepro.admin.subject.subject.name.disp" /></div>
											</td>
											<td class="row-even" width="30%"><span class="star"> <html:text
												property="name" styleId="name" styleClass="TextBox"
												size="50" maxlength="150" /> </span></td>
										</tr>

										<tr>

											<td height="25" rowspan="2" class="row-odd" width="20%">
											<div align="right"><span class="Mandatory">*</span><bean:message
												key="knowledgepro.exam.subjectsDefinition.subjectType" /> :</div>
											</td>
											<td class="row-even" width="30%"><html:select
												property="subjectTypeId" styleClass="combo"
												styleId="subjectTypeId" name="subjectEntryForm"
												style="width:200px">
												<html:option value="">
													<bean:message key="knowledgepro.admin.select" />
												</html:option>
												<logic:notEmpty name="subjectEntryForm"
													property="listSubjectType">
													<html:optionsCollection property="listSubjectType"
														name="subjectEntryForm" label="display" value="id" />
												</logic:notEmpty>
											</html:select></td>

											<td width="20%" rowspan="2" class="row-odd" width="20%">
											<div align="right"><span class="Mandatory">*</span><bean:message
												key="knowledgepro.exam.subjectsDefinition.theoryPractical" />
											:</div>
											</td>
											<td width="28%" rowspan="2" class="row-even" width="30%"><html:select
												property="theoryPractical" styleId="theoryPractical">
												<html:option value="">Select</html:option>
												<html:option value="T">Theory</html:option>
												<html:option value="P">Practical</html:option>
												<html:option value="B">Both</html:option>
											</html:select></td>
										</tr>

										<tr>

										</tr>
										<tr>
											<td class="row-odd" width="20%">
											<div align="right"><bean:message
												key="knowledgepro.exam.subjectsDefinition.cMCSName" /> :</div>
											</td>
											<td class="row-even" width="30%"><html:text property="consMCSName"
												styleId="consMCSName" styleClass="TextBox" size="16"
												maxlength="50" /></td>


											<td height="25" class="row-odd" width="20%">
											<div align="right"><bean:message key="knowledgepro.exam.subjectsDefinition.subjectNameprefix" />:</div>
											</td>
											<td class="row-even" width="30%"><html:text
												property="subjectNameprefix" styleId="subjectNameprefix"
												styleClass="TextBox" size="16" maxlength="50" /></td>
										</tr>

										<tr>
											<td class="row-odd" width="20%">
											<div align="right"><span class="Mandatory">*</span><bean:message
												key="knowledgepro.admin.subject.is.optional.subject?.disp" />
											? :</div>
											</td>
											<td class="row-even" width="30%"><html:radio property="optional"
												styleId="optional1" value="Yes" /> <bean:message
												key="knowledgepro.yes" /><html:radio property="optional"
												value="No" styleId="optional2" /> <bean:message
												key="knowledgepro.no" /></td>                          
											<td height="25" class="row-odd" width="20%">
											<div align="right"><span class="Mandatory">*</span><bean:message
												key="knowledgepro.admin.subject.is.second.language.?.disp" />
											? :</div>
											</td>
											<td height="25" class="row-even" width="30%"><html:radio
												property="secondlanguage" value="Yes"
												styleId="secondlanguage1" /><bean:message
												key="knowledgepro.yes" /> <html:radio
												property="secondlanguage" value="No"
												styleId="secondlanguage2" /><bean:message
												key="knowledgepro.no" /></td>

										</tr>
										
										<tr>
											<td width="20%" class="row-odd" height="25" onmouseover="shows(this,'additional subject attendance will not be considered as core subject attendance')" onmouseout="hides()">
											<div align="right"><bean:message
												key="knowledgepro.admin.subjectEntry.additionalSubject"/></div>
											</td>
											<td class="row-even" width="30%" ><input type="hidden" id="isAdditional" name="isAdditional" value="<bean:write name="subjectEntryForm" property="isAdditionalSubject"/>"/>
											<html:radio
												property="isAdditionalSubject" value="Yes"
												styleId="isAdditionalSubject1" name="subjectEntryForm" onmouseover="shows(this,'additional subject attendance will not be considered as core subject attendance')" onmouseout="hides()"/><bean:message
												key="knowledgepro.yes" /> <html:radio
												property="isAdditionalSubject" value="No"
												styleId="isAdditionalSubject2" name="subjectEntryForm" onmouseover="shows(this,'additional subject attendance will not be considered as core subject attendance')" onmouseout="hides()"/><bean:message
												key="knowledgepro.no" />
												<div id="messageBox1">
	                                        <div id="contents"></div>
	                                        </div>
												</td>
												
												
											<td height="25" class="row-odd" width="20%">
											<div align="right"><span class="Mandatory">*</span>Is Certificate Course ? :</div>
											</td>
											<td height="25" class="row-even" width="20%">
											<input type="hidden" id="isCertificate" name="isCertificate" value="<bean:write name="subjectEntryForm" property="isCertificateCourse"/>"/>
											<html:radio
												property="isCertificateCourse" value="Yes"
												styleId="isCertificateCourse1" name="subjectEntryForm"/><bean:message
												key="knowledgepro.yes" /> <html:radio
												property="isCertificateCourse" value="No"
												styleId="isCertificateCourse2" name="subjectEntryForm"/><bean:message
												key="knowledgepro.no" /></td>
											
										</tr>
										
										<tr >
										<td class="row-odd" height="25" width="20%">
											<div align="right"><!--<bean:message
												key="knowledgepro.exam.subjectsDefinition.majorDepartmentCode" /></div>
											</td>
											<td class="row-even" width="30%"><html:select
												property="majorDepartmentCodeId" styleClass="combo"
												styleId="majorDepartmentCodeId" name="subjectEntryForm"
												style="width:200px">
												<html:option value="">
													<bean:message key="knowledgepro.admin.select" />
												</html:option>
												<logic:notEmpty name="subjectEntryForm"
													property="listMajorDepartmentCode">
													<html:optionsCollection property="listMajorDepartmentCode"
														name="subjectEntryForm" label="display" value="id" />
												</logic:notEmpty>
											</html:select>-->
											<span class="Mandatory">*</span>Parent Department</div></td>
               								 <td width="30%" class="row-even">
                								<html:select property="departmentId" styleId="departmentId" styleClass="comboMediumBig">
												  <html:option value=""><bean:message key="knowledgepro.select"/></html:option>
													<logic:notEmpty property="departmentMap" name="subjectEntryForm">
														<html:optionsCollection property="departmentMap" label="value" value="key"/>
					 								</logic:notEmpty>
												</html:select>
											</td>
												<td height="25" class="row-odd">
											<div align="right"><span class="Mandatory">*</span>Question Bank is Required</div>
											</td>
											<td height="25" class="row-even">
											<input type="hidden" id="questionrequired" name="questionrequired" value="<bean:write name="subjectEntryForm" property="questionbyrequired"/>"/>
											<html:radio
												property="questionbyrequired" value="Yes"
												styleId="questionbyrequired1" name="subjectEntryForm"/><bean:message
												key="knowledgepro.yes" /> <html:radio
												property="questionbyrequired" value="No"
												styleId="questionbyrequired2" name="subjectEntryForm"/><bean:message
												key="knowledgepro.no" /></td>
                                   </tr>
                                   <tr>
                                     <td height="25" class="row-odd" width="20%">
									<div align="right"><bean:message key="knowledgepro.exam.subjectsDefinition.TeachingHourPerSemester" />:</div>
									</td>
									<td height="25" class="row-even" width="30%"><span
										class="star"> <html:text property="hourpersem"
										styleClass="TextBox" styleId="TeachingHour" size="30"
										maxlength="75" onkeypress="return isNumberKey(event)"/> </span></td>
										
									 <td width="20%" class="row-odd" height="25" onmouseover="showss(this,'The co curricular subject will not be visible in marks card. It will be only available for attendance entry.')" onmouseout="hidess()">
											<div align="right"><bean:message
												key="knowledgepro.admin.subjectEntry.coCurricularSubject"/></div>
											</td>
									 	<td class="row-even" width="30%" ><input type="hidden" id="coCurricular" name="coCurricular" value="<bean:write name="subjectEntryForm" property="coCurricularSubject"/>"/>
											<html:radio
												property="coCurricularSubject" value="Yes"
												styleId="coCurricularSubject1" name="subjectEntryForm" onmouseover="showss(this,'The co curricular subject will not be visible in marks card. It will be only available for attendance entry.')" onmouseout="hidess()"/><bean:message
												key="knowledgepro.yes" /> <html:radio
												property="coCurricularSubject" value="No"
												styleId="coCurricularSubject2" name="subjectEntryForm" onmouseover="showss(this,'The co curricular subject will not be visible in marks card. It will be only available for attendance entry.')" onmouseout="hidess()"/><bean:message
												key="knowledgepro.no" />
												<div id="messageBox">
	                                        <div id="content"></div>
	                                        </div>
												</td>
								   </tr>
								   <tr>
								   		<td height="25" class="row-odd" width="20%">
											<div align="right">Eligible For Honours Course:</div>
										</td>
										<td width="30%" class="row-even">
                								<html:select property="eligibleCourseId" styleId="eligibleCourseId" styleClass="comboMediumBig">
												  <html:option value=""><bean:message key="knowledgepro.select"/></html:option>
													<logic:notEmpty property="courseMap" name="subjectEntryForm">
														<html:optionsCollection property="courseMap" label="value" value="key"/>
					 								</logic:notEmpty>
												</html:select>
										</td>
										<td height="25" class="row-odd" width="20%">
											<div align="right">Subject Code Group:</div>
										</td>
										<td width="30%" class="row-even">
                								<html:select property="subjectCodeGroup" styleId="subjectCodeGroup" styleClass="comboMediumBig">
												  <html:option value=""><bean:message key="knowledgepro.select"/></html:option>
													<logic:notEmpty property="subjectCodeGroupMap" name="subjectEntryForm">
														<html:optionsCollection property="subjectCodeGroupMap" label="value" value="key"/>
					 								</logic:notEmpty>
												</html:select>
										</td>
								   </tr>
								   <tr>
								   		<td align="right" height="25" class="row-odd" width="20%">
											<span class="Mandatory">*</span>
											<bean:message key="knowledgepro.consolidatedSubjectStream"/>
										</td>
								   		<td width="30%" class="row-even">
								   			<html:select property="consolidatedSubjectStreamId">
								   				<html:option value=""><bean:message key="knowledgepro.select"/></html:option>
								   				<logic:notEmpty property="consolidatedSubjectStreams" name="subjectEntryForm">
								   					<html:optionsCollection property="consolidatedSubjectStreams" label="value" value="key"/>
								   				</logic:notEmpty>
								   			</html:select>
								   		</td>
								   		
								   						<!--<td width="20%" class="row-odd" height="25" >
											<div align="right">Subject Type Based on Marks</div>
											</td>
											<td class="row-even" width="30%" ><input type="hidden" id="subjectTypeBasedOnMarks" name="subjectTypeBasedOnMarks" value="<bean:write name="subjectEntryForm" property="subjectTypeBasedOnMarks"/>"/>
											External:<html:radio
												property="subjectTypeBasedOnMarks" value="external"
												styleId="subjectTypeBasedOnMarks1" name="subjectEntryForm" /> Internal:<html:radio
												property="subjectTypeBasedOnMarks" value="internal"
												styleId="subjectTypeBasedOnMarks2" name="subjectEntryForm" />
												
										
												</td>-->
												
												
														<td height="25" class="row-odd" width="20%">
											<div align="right">Is external or internal subject ?</div>
											</td>
											<td height="25" class="row-even" width="20%">
											<input type="hidden" id="subjectType11" name="subjectType1" value="<bean:write name="subjectEntryForm" property="subjectTypeBasedOnMarks"/>"/>
											<html:radio
												property="subjectTypeBasedOnMarks" value="external"
												styleId="subjectTypeBasedOnMarks1" name="subjectEntryForm"/>External <html:radio
												property="subjectTypeBasedOnMarks" value="internal"
												styleId="subjectTypeBasedOnMarks2" name="subjectEntryForm"/>Internal</td>
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
								<tr>
									<td width="45%" height="35">
									<div align="right"><c:choose>
										<c:when test="${Update != null && Update == 'Update'}">
											<html:submit property="" styleClass="formbutton"
												value="Update" styleId="submitbutton">
											</html:submit>
										</c:when>
										<c:otherwise>
											<html:submit property="" styleClass="formbutton"
												value="Submit" styleId="submitbutton">
											</html:submit>
										</c:otherwise>
									</c:choose></div>
									</td>
									<td width="2%"></td>

									<td width="53%"><c:choose>
										<c:when test="${Update != null && Update == 'Update'}">
											<html:cancel styleClass="formbutton" value="Reset"
												onclick="clearMessages()"></html:cancel>
										</c:when>
										<c:otherwise>
											<input type="button" class="formbutton" value="Cancel" onclick="clearMessages()" />
										</c:otherwise>
									</c:choose></td>
								</tr>
							</table>
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
											<td height="25" colspan="3">
											<table width="100%" cellspacing="1" cellpadding="2">
												<tr>
													<td height="25" class="row-odd" width="4%">
													<div align="center"><bean:message
														key="knowledgepro.admin.subject.subject.s1no" /></div>
													</td>
													<td height="25" class="row-odd" align="center"  width="7"><bean:message
														key="knowledgepro.admin.subject.subject.code" /></td>
													<td class="row-odd" align="center"  width="45%"><bean:message
														key="knowledgepro.admin.subject.subject.name" /></td>

													<td class="row-odd" align="center"  width="7%"><bean:message
														key="knowledgepro.exam.subjectsDefinition.subjectType" /></td>


													<td class="row-odd" align="center"  width="5%"><bean:message
														key="knowledgepro.admin.subject.is.second.language.?" />
													?</td>

													<td class="row-odd" align="center"  width="5%"><bean:message
														key="knowledgepro.exam.subjectsDefinition.theoryPractical" /></td>

													<td class="row-odd" align="center"  width="4%"><bean:message
														key="knowledgepro.admin.subject.is.optional.subject?" />
													?</td>
													<td class="row-odd" align="center"  width="4%">Parent Department</td>
									                <td class="row-odd" align="center"  width="4%">Scheme No</td>
									                <td class="row-odd" align="center"  width="4%">Question Bank Is Required</td>
													<td class="row-odd"  width="4%">
													<div align="center"><bean:message
														key="knowledgepro.edit" /></div>
													</td>
													<td class="row-odd"  width="4%">
													<div align="center"><bean:message
														key="knowledgepro.delete" /></div>
													</td>
												</tr>
												<logic:iterate name="subjectEntryForm"
													property="subjectList" id="sList" indexId="count">
													<c:choose>
														<c:when test="${temp == 0}">
															<tr>
																<td height="25" class="row-even" width="4%">
																<div align="center"><c:out value="${count+1}" /></div>
																</td>
																<td height="25" class="row-even" align="center" width="7%"><bean:write
																	name="sList" property="code" /></td>
																<td class="row-even" align="center" width="45%"><bean:write
																	name="sList" property="name" /></td>
																<td class="row-even" align="center" width="5%"><bean:write
																	name="sList" property="subjectType" /></td>
																<td class="row-even" align="center" width="4%"><bean:write
																	name="sList" property="secondlanguage" /></td>
																<td class="row-even" align="center"><bean:write
																	name="sList" property="theoryPractical" /></td>
																<td class="row-even" align="center"><bean:write
																	name="sList" property="optional" /></td>
																<td class="row-even" align="center"><bean:write
																	name="sList" property="departmentName" /></td>
																<td class="row-even" align="center"><bean:write
																	name="sList" property="schemeNo" /></td>
																<td class="row-even" align="center"><bean:write
																	name="sList" property="questionbyrequired" /></td>
																<td height="25" class="row-even">
																<div align="center"><img
																	src="images/edit_icon.gif" width="16" height="18"
																	style="cursor: pointer"
																	onclick="editSubject('<bean:write name="sList" property="id" />')" /></div>
																</td>
																<td width="4%" height="25" class="row-even">
																<div align="center"><img
																	src="images/delete_icon.gif" width="16" height="16"
																	style="cursor: pointer"
																	onclick="deleteSubject('<bean:write name="sList" property="id" />')" /></div>
																</td>
															</tr>
															<c:set var="temp" value="1" />
														</c:when>
														<c:otherwise>
															<tr>
																<td height="25" class="row-white" width="5%">
																<div align="center"><c:out value="${count+1}" /></div>
																</td>
																<td height="25" class="row-white" align="center" width="4%"><bean:write
																	name="sList" property="code" /></td>
																<td class="row-white" align="center" width="45%"><bean:write
																	name="sList" property="name" /></td>
																<td class="row-white" align="center" width="5%"><bean:write
																	name="sList" property="subjectType" /></td>
																<td class="row-white" align="center" width="5%"><bean:write
																	name="sList" property="secondlanguage" /></td>
																<td class="row-white" align="center"><bean:write
																	name="sList" property="theoryPractical" /></td>
																<td class="row-white" align="center"><bean:write
																	name="sList" property="optional" /></td>
																<td class="row-white" align="center"><bean:write
																	name="sList" property="departmentName" /></td>
																<td class="row-white" align="center"><bean:write
																	name="sList" property="schemeNo" /></td>
																<td class="row-white" align="center"><bean:write
																	name="sList" property="questionbyrequired" /></td>	
																<td height="25" class="row-white">
																<div align="center"><img
																	src="images/edit_icon.gif" width="16" height="18"
																	style="cursor: pointer"
																	onclick="editSubject('<bean:write name="sList" property="id" />')" /></div>
																</td>
																<td height="25" class="row-white">
																<div align="center"><img
																	src="images/delete_icon.gif" width="16" height="16"
																	style="cursor: pointer"
																	onclick="deleteSubject('<bean:write name="sList" property="id" />')" /></div>
																</td>
															</tr>
															<c:set var="temp" value="0" />
														</c:otherwise>
													</c:choose>
												</logic:iterate>
											</table>
											</td>
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
<c:if test="${Update == null || Update == 'add'}">
	<script type="text/javascript" language="javascript">
	document.getElementById("optional2").checked = true;
	document.getElementById("secondlanguage2").checked = true;
	document.getElementById("isCertificateCourse2").checked = true;
	document.getElementById("questionbyrequired1").checked = true;
	document.getElementById("isAdditionalSubject2").checked = true;
	document.getElementById("coCurricularSubject2").checked = true;

</script>
</c:if>