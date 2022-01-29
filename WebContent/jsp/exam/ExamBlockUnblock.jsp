<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@page import="java.util.Map,java.util.HashMap"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<script language="JavaScript" src="js/ajax/AjaxUtil.js"></script>

<script type="text/javascript" language="javascript">
	// Functions for AJAX 
	function getClasses(examName) {
		
		getClasesByExamName("classMap", examName, "classes", updateClasses);
	}
	function updateClasses(req) {
		updateOptionsFromMapMultiselect(req, "classes", "- Select -");
	}
	function submitEdit(){
		var errors = false;
		if(document.getElementById("blockType").value == null || document.getElementById("blockType").value==""){
			errors=true;
			document.getElementById("errorMessage").innerHTML="<FONT color='red'>Please select  Mandatory Fields </FONT>"; 
		}
		if(document.getElementById("typeId").value == null || document.getElementById("typeId").value==""){
			if(errors==false){
				errors=true;
				document.getElementById("errorMessage").innerHTML="<FONT color='red'>Please select  Mandatory Fields </FONT>";
			}
		}
		if(errors==false){
			if(document.getElementById("examName").value != null && document.getElementById("examName").value!="" 
				&& document.getElementById("classes").value != null && document.getElementById("classes").value!=""){
				if(document.getElementById("blockType").value=="EditReason"){
					document.ExamBlockUnblockForm.method.value="editReason";
					document.ExamBlockUnblockForm.submit();
				}else{
					document.getElementById("method").value="blockUnBlockExam";
					document.ExamBlockUnblockForm.submit();
				}
			}else{
				document.ExamBlockUnblockForm.submit();
			}
		}
	}
	function getExamsByExamTypeAndYear() {
		var examType=document.getElementById("examType").value;
		var year=document.getElementById("year").value;
		getExamNameByExamTypeAndYear("examMap", examType,year, "examName", updateExamName);
	}
	function updateExamName(req) {
		updateOptionsFromMap(req, "examName", "- Select -");
		updateCurrentExam(req, "examName");
	}
	function changeMandatoryOption(blockType){
		if(blockType=="Block"){
			document.getElementById("examDiv").innerHTML="<span class='Mandatory'>*</span>Exam Name :";
			document.getElementById("classDiv").innerHTML="<span class='Mandatory'>*</span>Class :";
			document.getElementById("method").value="blockUnBlockExam";
		}else if(blockType=="Unblock"){
			document.getElementById("method").value="getStudentList";
			document.getElementById("examDiv").innerHTML="Exam Name :";
			document.getElementById("classDiv").innerHTML="Class :";
		}else{
			document.getElementById("method").value="getStudentList";
			document.getElementById("examDiv").innerHTML="Exam Name :";
			document.getElementById("classDiv").innerHTML="Class :";
		}
	}
</script>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html:form action="/ExamBlockUnblock.do">
	<html:hidden property="formName" value="ExamBlockUnblockForm" />
	<html:hidden property="method" styleId="method"
		value="blockUnBlockExam" />
	<html:hidden property="pageType" value="1" />
	<html:hidden property="className" styleId="className" />
	<table width="100%" border="0">
		<tr>
			<td><span class="heading"><bean:message
				key="knowledgepro.exam.exam" /> <span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.exam.blockUnblock" /> &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> <bean:message
						key="knowledgepro.exam.blockUnblock" /></strong></td>

					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" align="center" cellpadding="0"
						cellspacing="0">
						<tr>
							<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
							<FONT color="green"> <html:messages id="msg"
								property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
								<br>
							</html:messages> </FONT></div>
							<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
							<td height="20" class="news">
							<div align="right" class="mandatoryfield"><bean:message
								key="knowledgepro.mandatoryfields" /></div>
							</td>
							<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
						</tr>
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
									<td height="25" class="row-odd"><div align="right"><span class="Mandatory">*</span> <bean:message
										key="knowledgepro.admin.year" /> :</div></td>
									<td width="16%" class="row-even" valign="top">
									<input
										type="hidden" id="tempyear" name="appliedYear"
										value="<bean:write name="ExamBlockUnblockForm" property="year"/>" />
									<html:select 
										property="year" styleId="year"
										styleClass="combo" onchange="getExamsByExamTypeAndYear()">
										<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
										<cms:renderAcademicYear></cms:renderAcademicYear>
									</html:select></td>
							<td height="25" class="row-odd"><div align="right"><span class="Mandatory">*</span> <bean:message
										key="knowledgepro.exam.examDefinition.examType" /> :</div></td>
								<td height="25" colspan="3" class="row-even"><html:select
										property="examType" styleClass="combo"  styleId="examType" onchange="getExamsByExamTypeAndYear()">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<logic:notEmpty property="examTypeList"
											name="ExamBlockUnblockForm">
											<html:optionsCollection property="examTypeList"
												name="ExamBlockUnblockForm" label="value" value="key" />
										</logic:notEmpty>
									</html:select></td>
									</tr>
									
								<tr>
								
									<td class="row-odd" height="28">
									<div align="right"><span class="Mandatory">*</span>Block/Unblock/Edit Reason :</div>
									</td>
									<td class="row-even"><html:select
										property="blockType" styleId="blockType" name="ExamBlockUnblockForm" onchange="changeMandatoryOption(this.value)">
										<option value="">Select</option>
										<option value="Block">Block</option>
										<option value="Unblock">Unblock</option>
										<option value="EditReason">Edit Reason</option>
									</html:select></td>
									<td class="row-odd" height="28">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.exam.blockUnblock.type" /> :</div>
									</td>
									<td class="row-even"><html:select
										property="typeId" styleId="typeId">
										<html:option value="">Select</html:option>
										<html:option value="1">Hall Ticket</html:option>
										<html:option value="2">Marks Card</html:option>
										<html:option value="3">Regular Application</html:option>
									</html:select></td>
								</tr>
								<tr>
									<td width="24%" height="128" class="row-odd">
									<div align="right" id="examDiv">
									<span class="Mandatory" >*</span><bean:message
										key="knowledgepro.exam.blockUnblock.examName" /> :
									</div>
									</td>
									<td width="21%" height="128"  class="row-even"><html:select
										property="examName" styleClass="combo" styleId="examName"
										name="ExamBlockUnblockForm" style="width:200px"
										onchange="getClasses(this.value)">
											<html:option value="">
												<bean:message key="knowledgepro.admin.select" />
											</html:option>
										<logic:notEmpty name="ExamBlockUnblockForm"
											property="examNameMap">
											<html:optionsCollection property="examNameMap"
												name="ExamBlockUnblockForm" label="value" value="key" />
										</logic:notEmpty>
									</html:select></td>



									<td width="22%" height="15" class="row-odd">
									<div align="right" id="classDiv"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.exam.blockUnblock.class" />:</div>
									</td>
									<td width="36%" height="15" colspan="3" class="row-even">
									<nested:select property="classValue" styleClass="body"
										multiple="multiple" size="6" styleId="classes"
										style="width:45%;height:15">

										<c:if
											test="${ExamBlockUnblockForm.examName != null && ExamBlockUnblockForm.examName != ''}">
											<c:set var="classMap"
												value="${baseActionForm.collectionMap['classMap']}" />
											<c:if test="${classMap != null}">
												<html:optionsCollection name="classMap" label="value"
													value="key" />
											</c:if>

										</c:if>
										<logic:notEmpty property="mapClass"
											name="ExamBlockUnblockForm">

											<html:optionsCollection name="ExamBlockUnblockForm"
												property="mapClass" label="value" value="key" />
										</logic:notEmpty>

									</nested:select></td>
								</tr>
								<tr>
									<td width="13%" height="25" class="row-odd">
										<div align="right">Exam</div>
										</td>
										<td width="19%" height="25" class="row-even">
										<input type="radio" name="isPreviousExam" id="isPreviousExam_1" value="true"/> Previous
			                    		<input type="radio" name="isPreviousExam" id="isPreviousExam_2" value="false" /> Current
			    						<script type="text/javascript">
											var isPreviousExam = "<bean:write name='ExamBlockUnblockForm' property='isPreviousExam'/>";
											if(isPreviousExam == "true") {
							                        document.getElementById("isPreviousExam_1").checked = true;
											}else{
													document.getElementById("isPreviousExam_2").checked = true;
											}
										</script>
		    						</td>
		    						<td class="row-odd" height="28"></td>
		    						<td class="row-even">&nbsp;
		    						</td>

								</tr>
							</table>
							</td>
							<td width="5" height="30" background="images/right.gif"></td>
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
					<td height="10" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="45%" height="35" align="right">
								<html:button property="" value="Submit" onclick="submitEdit()" styleClass="formbutton"> </html:button>
							</td>
							<td width="2%" height="35" align="center">&nbsp;</td>
							<td width="45%" align="left">
								<html:button property="" value="Reset" onclick="resetFeilds()" styleClass="formbutton"> </html:button>  
							</td>
						</tr>
					</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
					<td width="0" background="images/TcenterD.gif"></td>
					<td><img src="images/Tright_02.gif" width="9" height="29"></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>


</html:form>
<script type="text/javascript">
var year = document.getElementById("tempyear").value;
if(year.length != 0) {
 	document.getElementById("year").value=year;
}
</script>