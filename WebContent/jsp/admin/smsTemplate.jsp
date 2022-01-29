 <%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="http://java.fckeditor.net" prefix="FCK" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<script type="text/javascript">
function getPrograms(ProgramTypeId) {
	getProgramsByType("programMap", ProgramTypeId, "program", updatePrograms);
}

function updatePrograms(req) {
	updateOptionsFromMap(req, "program", "- Select -");
}

function getCourses(programId) {
	getCoursesByProgram("courseMap", programId, "course", updateCourses);
}
function updateCourses(req) {
	updateOptionsFromMap(req, "course", "- Select -");
}
function editTemplate(id) {
	document.getElementById("templateId").value = id;
	document.getElementById("method").value = "editSMSTemplate";
	document.smsTemplateForm.submit();
}

function updateTemplate() {
	document.getElementById("method").value = "updateSMSTemplate";
	document.smsTemplateForm.submit();
}

function addTemplate() {
	document.getElementById("method").value = "createSMSTemplate";
	document.smsTemplateForm.submit();
}
function viewTemplate(id) {
	var url = "SMSTemplate.do?method=viewTemplateDescription&templateId=" + id;
	myRef = window.open(url, "ViewTemplateDescription", "left=20,top=20,width=600,height=500,toolbar=1,resizable=0,scrollbars=1");
}
function deleteTemplate(id) {
	deleteConfirm = confirm("Are you sure you want to delete this entry?");
	if (deleteConfirm == true) {
		document.location.href = "SMSTemplate.do?method=deleteSMSTemplate&templateId=" + id;
	}
}
function openHtml() {
	var url = "SMSTemplate.do?method=helpMenu";
	win2 = window.open(url, "Help", "width=800,height=800,scrollbars=yes"); 
}
function imposeMaxLength(field, size) {
    if (field.value.length > size) {
        field.value = field.value.substring(159, size);
    }
}
function len_display(Object,MaxLen,element){
	document.getElementById("charCount").style.display="block";
    var len_remain = MaxLen-Object.value.length;
   if(len_remain <=160){
    document.getElementById(element).value=len_remain;
     }
}
function charCountDisplay(){
	document.getElementById("charCount").style.display="none";
}
</script>
<!--<body onload="charCountDisplay()"></body>
--><html:form action="/SMSTemplate" method="post">
<html:hidden property="method" styleId="method"	value="createSMSTemplate" />
<html:hidden property="templateId" styleId="templateId" />
<html:hidden property="formName" value="smsTemplateForm" />
<html:hidden property="pageType" value="1" />
<table width="98%" border="0">
  <tr>
    <td><span class="heading"><bean:message key="knowledgepro.admin"/><span class="Bredcrumbs">&gt;&gt;SMS Template&gt;&gt;</span></span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="10"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="body" ><div align="left"><strong class="boxheader">SMS Template</strong></div></td>
        <td width="13" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="122" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><div align="center">
              <table width="100%" height="50"  border="0" cellpadding="0" cellspacing="0">
                 <tr>
               	    <td height="20" colspan="6" align="left">
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
                   <td height="49" colspan="6" class="body" ><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
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
								<td width="17%" height="25" class="row-odd">
								<div align="right"><bean:message key="knowledgepro.admin.programtype" />:</div>
								</td>
								<td width="23%" height="25" class="row-even">
									<html:select property="programTypeId" styleClass="combo" styleId="programType" onchange="getPrograms(this.value)" >
										<html:option value=" "><bean:message key="knowledgepro.admin.select"/></html:option>
										<html:optionsCollection property="programTypeList" label="programTypeName" value="programTypeId"/>
									</html:select>
								</td>
								<td width="13%" class="row-odd">
								<div align="right"><bean:message key="knowledgepro.admin.program" />:</div>
								</td>
								<td width="17%" class="row-even">
								<html:select property="programId" styleClass="combo" styleId="program" onchange="getCourses(this.value)" >
									<html:option value=" "><bean:message key="knowledgepro.select" /></html:option>
									<c:choose>
										<c:when test="${operation == 'edit'}">
											<c:if test="${programMap != null}">
												<html:optionsCollection name="programMap" label="value" value="key" />
											</c:if>
										</c:when>
										<c:otherwise>
		                 			    	<c:if test="${smsTemplateForm.programTypeId != null && smsTemplateForm.programTypeId != ''}">
		                     					<c:set var="programMap" value="${baseActionForm.collectionMap['programMap']}"/>
		                     		    	 	<c:if test="${programMap != null}">
		                     		    	 		<html:optionsCollection name="programMap" label="value" value="key"/>
		                     		    	 	</c:if>	 
		                     		   		</c:if>
		                     		   	</c:otherwise>
				                   	</c:choose>	
								</html:select>
								</td>
								<td width="13%" class="row-odd">
								<div align="right"><bean:message key="knowledgepro.admin.course" />:</div>
								</td>
								<td width="17%" class="row-even">
								<html:select property="courseId" styleClass="combo" styleId="course">
									<html:option value=" "><bean:message key="knowledgepro.select" /></html:option>
										<c:choose>
											<c:when test="${operation == 'edit'}">
												<c:if test="${courseMap != null}">
													<html:optionsCollection name="courseMap" label="value" value="key" />
												</c:if>	
											</c:when>
											<c:otherwise>
												<c:if test="${smsTemplateForm.programId != null && smsTemplateForm.programId != ''}">
													<c:set var="coursesMap" value="${baseActionForm.collectionMap['courseMap']}" />
													<c:if test="${coursesMap != null}">
														<html:optionsCollection name="coursesMap" label="value" value="key" />
													</c:if>
												</c:if>
											</c:otherwise>
										</c:choose>
								</html:select>
								</td>
							</tr>
                           <tr>
                             <td class="row-odd" ><div align="right">
                             	<span class="Mandatory">*</span><bean:message key="knowledgepro.emailtemplate.templatename"/>:</div>
                             </td>
                             <td class="row-even">
                            	<div align="left">
                            	<!-- <input type="hidden" id="templatenameId" name="template" value='<bean:write name="smsTemplateForm" property="templateName"/>' /> -->	
                            	<html:select property="templateName" styleClass="comboMediumLarge" styleId="templateNameId">
                            		<html:option value="">Select</html:option>
                            		<html:option value="Employee Attendance Remainder SMS">Employee Attendance Remainder SMS</html:option>
                            		<html:option value="Student Marks Correction Password">Student Marks Correction Password</html:option>
                            		<html:option value="Online Application Submission">Online Application Submission</html:option>
                            		<html:option value="Missing Documents">Missing Documents</html:option>
                            		<html:option value="Application Received">Application Received</html:option>
                            		<html:option value="E Admit Card">E Admit Card</html:option>
                            		<html:option value="Interview Status for not selected">Interview Status for not selected</html:option>
                            		<html:option value="E Admission Card">E Admission Card</html:option>
                            		<html:option value="Final Merit List Upload Not Selected">Final Merit List Upload Not Selected</html:option>
                            		<html:option value="Admission Confirmation">Admission Confirmation</html:option>
                            		<html:option value="Admission Cancellation">Admission Cancellation</html:option>
                            		<html:option value="Edit E Admit Card">Edit E Admit Card</html:option>
                            		<html:option value="Revaluation / Re-totaling SMS">Revaluation / Re-totaling SMS</html:option>
                            		<html:option value="Revaluation / Re-totaling Status Update SMS">Revaluation / Re-totaling Status Update SMS</html:option>
                            		<html:option value="Student Certificate Course Submission">Student Certificate Course Submission</html:option>
                            		<html:option value="Supplementary Application Submission">Supplementary Application Submission</html:option>
                            		<html:option value="Certificate Request Completed SMS">Certificate Request Completed SMS</html:option>
                            		<html:option value="Offline Application Acknowledgement SMS">Offline Application Acknowledgement SMS</html:option>
                            		<html:option value="Certificate Request Rejected SMS">Certificate Request Rejected SMS</html:option>
                            		<html:option value="Hostel waiting list intimation">Hostel waiting list intimation</html:option>
                            		<html:option value="Valuation Schedule Remainder">Valuation Schedule Remainder</html:option>
                            		<html:option value="Hostel Leave - Parent">Hostel Leave - Parent</html:option>
                            		<html:option value="Hostel Absentees">Hostel Absentees</html:option>
                            		<html:option value="Auditorium Booking Status SMS">Auditorium Booking Status SMS</html:option>
                            		<html:option value="Hostel Leave - Student">Hostel Leave - Student</html:option>
                            		<html:option value="Hostel Leave Reject">Hostel Leave Reject</html:option>
                            		<html:option value="Hostel Absent Sms For Student">Hostel Absent Sms For Student </html:option>
                            		<html:option value="Hostel Absent Sms For Parent">Hostel Absent Sms For Parent </html:option>
                            		<html:option value="Smart Card Sms">Smart Card Lost/Correction SMS</html:option>
                            		<html:option value="Student Attendance Regular Absent Sms For Parent">Student Attendance Regular Absent SMS for Parent </html:option>
                            		<html:option value="Student Attendance Internal Absent Sms For Parent">Student Attendance Internal Absent SMS for Parent </html:option>
									<html:option value="Exam-Question Paper Checking Alert Sms To Teachers">Exam-Question Paper Checking Alert Sms To Teachers</html:option>
									<html:option value="SAP Registration Student Sms">SAP Registration Student Sms</html:option>
									<html:option value="SAP Exam - Reminder for student">SAP Exam - Reminder for student</html:option>
									<html:option value="SAP Exam - Reminder for Invigilators">SAP Exam - Reminder for Invigilators</html:option>
									<html:option value="Support Request Status SMS For Staff">Support Request Status SMS For Staff</html:option>
									<html:option value="Support Request Status SMS For Student">Support Request Status SMS For Student</html:option>
									<html:option value="Valuation Remuneration Payment SMS">Valuation Remuneration Payment SMS</html:option>
									<html:option value="Interview Card Date Modification SMS">Interview Card Date Modification SMS</html:option>
									<html:option value="Publish Regular Exam Hall Ticket">Publish Regular Exam Hall Ticket</html:option>
									<html:option value="Publish Regular Exam Marks card">Publish Regular Exam Marks card</html:option>
									<html:option value="Publish Supplementary Exam Marks card">Publish Supplementary Exam Marks card</html:option>
									<html:option value="SMS For Repeat Mid Sem Exam Application Submit">SMS For Repeat Mid Sem Exam Application Submit</html:option>
									<html:option value="SMS For Repeat Mid Sem Exam Application Approved">SMS For Repeat Mid Sem Exam Application Approved</html:option>
									<html:option value="Reminder Sms For Mid Semester Repeat Exam Fee Payment">Reminder Sms For Mid Semester Repeat Exam Fee Payment</html:option>
									<html:option value="SMS For Mid Semester Repeat Exam Fee Exemption">SMS For Mid Semester Repeat Exam Fee Exemption</html:option>
									<html:option value="SMS For Repeat Mid Sem Exam Application Rejected">SMS For Repeat Mid Sem Exam Application Rejected</html:option>
									<html:option value="Photo Upload Student Login-Approval">Photo Upload from Student Login-Approval</html:option>
									<html:option value="Photo Upload Student Login-Rejection">Photo Upload from Student Login-Rejection</html:option>
									<html:option value="Hostel Re-Admission Selection">Hostel Re-Admission Selection</html:option>
								</html:select>
								<img src="images/questionMark.jpg" style="cursor:pointer" width="20" height="20"
								 onclick="openHtml()" title="Help">
								 </div>
							</td>
							<td class="row-odd"><div align="right">Template Description:</div></td>
                            <td class="row-even">
<html:textarea property="templateDescription" onclick="len_display(this,160,'long_len')" onkeypress="return imposeMaxLength(this, 0);" onkeyup="len_display(this,160,'long_len')"></html:textarea>
<div align="right" id="charCount"><input type="text" id="long_len" value="160" class="len" size="2" readonly="readonly" style="border: none; background-color: #eaeccc; text-align: right; font-size: 11px; font-family: Verdana, Arial, Helvetica, sans-serif"> Characters Remaining</div>
</td>
							<td class="row-odd"></td>
                            <td class="row-even"></td>
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
                   <td height="20" colspan="6" valign="top" class="body" >
                   <table width="100%" cellspacing="1" cellpadding="2"><tr ></tr>
                     <tr>
                       <td height="20" colspan="2" >   
		                <table width="100%"  border="0" cellspacing="0" cellpadding="0">
		                  <tr>
		                    <td height="20"><div align="center">
		                    
		                     <c:choose>
		                     	<c:when test="${operation == 'edit'}">
		                     	 	<html:button property="" styleClass="formbutton" value="Update" onclick="updateTemplate()"></html:button>
		                      	</c:when>
		                      	<c:otherwise>
		                      		<html:button property="" styleClass="formbutton" value="Save" onclick="addTemplate()"></html:button>
		                      	</c:otherwise>
		                     </c:choose>	
		                    </div></td>
		                    </tr>
		                </table>
                        </td>
                     </tr>
                   </table></td>
                </tr>
                 <tr>
                   <td height="10" colspan="6" class="body" ></td>
                 </tr>
              </table>
            </div></td>
        <td width="13" valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <logic:notEmpty name="smsTemplateForm" property="templateList">
      <tr>
        <td height="122" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><div align="center">
              <table width="100%" height="50"  border="0" cellpadding="0" cellspacing="0">
                 <tr>
                   <td height="49" colspan="6" class="body" ><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
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
                             <td width="10%" height="25" class="row-odd"><div align="center">
                             	<bean:message key="knowledgepro.slno"/></div>
                             </td>
                             <td width="15%" height="25" class="row-odd"><div align="center">
                             	<bean:message key="knowledgepro.admin.programtype" /></div>
                             </td>
                             <td width="15%" height="25" class="row-odd"><div align="center">
                             	<bean:message key="knowledgepro.admin.program" /></div>
                             </td>
                             <td width="15%" height="25" class="row-odd"><div align="center">
                             	<bean:message key="knowledgepro.admin.course" /></div>
                             </td>
                             <td width="15%" height="25" class="row-odd" align="center">
	                             <span class="star">
	                             	<bean:message key="knowledgepro.emailtemplate.templatename"/>
	                             </span>
	                         </td>
                             <td width="10%" height="25" class="row-odd" >
                             	<div align="center">
                             		<bean:message key="knowledgepro.view" />
                             	</div>
                             </td>
                             <td width="10%" height="25" class="row-odd" >
                             	<div align="center">
                             		<bean:message key="knowledgepro.edit" />
                             	</div>
                             </td>
                             <td width="10%" height="25" class="row-odd" >
                             	<div align="center">
                             		<bean:message key="knowledgepro.delete" />
                             	</div>
                             </td>
                           </tr>
                           <logic:iterate id="template" name="smsTemplateForm" property="templateList" indexId="count">
							<c:choose>
								<c:when test="${count%2 == 0}">
									<tr class="row-even">
								</c:when>
								<c:otherwise>
									<tr class="row-white">
								</c:otherwise>
							</c:choose>
                           	<td height="25"><div align="center">
                             	<c:out value="${count + 1}"/> </div>
                             </td>
                             <td height="25"><div align="center">
	                             <bean:write name="template" property="programTypeName" />
	                           </div>
	                         </td>
	                         <td height="25"><div align="center">
	                             <bean:write name="template" property="programName"/>
	                           </div>
	                         </td>
	                         <td height="25"><div align="center">
	                             <bean:write name="template" property="courseName"/>
	                           </div>
	                         </td>
                             <td height="25"><div align="center">
	                             <bean:write name="template" property="templateName"/>
	                           </div>
	                         </td>
	                         <td height="25">
								<div align="center"><img src="images/View_icon.gif" style="cursor:pointer"
									width="16" height="18" onclick="viewTemplate('<bean:write name="template" property="id"/>')"></div>
							 </td>
							 <td height="25">
								<div align="center"><img src="images/edit_icon.gif" style="cursor:pointer"
									width="16" height="18" onclick="editTemplate('<bean:write name="template" property="id"/>')"></div>
							 </td>
							 <td height="25">
								<div align="center"><img src="images/delete_icon.gif" style="cursor:pointer"
								 width="16" height="16" onclick="deleteTemplate('<bean:write name="template" property="id"/>')"></div>
							 </td>
                          </logic:iterate>
                      </table>
                      </td>
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
                  <td height="10" colspan="6" class="body" ></td>
                </tr>
             </table>
           </div></td>
       <td width="13" valign="top" background="images/Tright_3_3.gif" class="news"></td>
     </tr>
     </logic:notEmpty>
     <tr>
       <td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
       <td width="100%" background="images/TcenterD.gif"></td>
       <td><img src="images/Tright_02.gif" width="9" height="29"></td>
     </tr>
   </table>
   </td>
 </tr>
</table>
<script type="text/javascript">
charCountDisplay();
</script>
</html:form>
<!-- <script type="text/javascript">
	var templateName = document.getElementById("templatenameId").value;
	if (templateName.length != 0) {
		document.getElementById("templateNameId").value = templateName;
	}
</script> -->