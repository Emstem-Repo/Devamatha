\<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
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
	getGroupTemplateList();
}

function getCourses(programId) {
	getCoursesByProgram("courseMap", programId, "course", updateCourses);
	getGroupTemplateList();
}
function updateCourses(req) {
	updateOptionsFromMap(req, "course", "- Select -");
	
}
function editTemplate(id) {
	document.getElementById("templateId").value = id;
	document.getElementById("method").value = "editGroupTemplate";
	document.templateForm.submit();
}

function updateTemplate() {
	document.getElementById("method").value = "updateGroupTemplate";
	document.templateForm.submit();
}

function addTemplate() {
	document.getElementById("method").value = "createGroupTemplate";
	document.templateForm.submit();
}
function viewTemplate(id) {
	var url = "CreateTemplate.do?method=viewTemplateDescription&templateId=" + id;
	myRef = window.open(url, "ViewTemplateDescription", "left=20,top=20,width=600,height=500,toolbar=1,resizable=0,scrollbars=1");
}
function deleteTemplate(id) {
	deleteConfirm = confirm("Are you sure you want to delete this entry?")
	if (deleteConfirm == true) {
		document.location.href = "CreateTemplate.do?method=deleteGroupTemplate&templateId=" + id;
	}
}
function openHtml() {
	var url = "CreateTemplate.do?method=helpMenu";
	win2 = window.open(url, "Help", "width=800,height=800,scrollbars=yes"); 
}

function getGroupTemplateList(){
	var programId="";
	var templateName="";
	var programTypeId="";
	 programId=document.getElementById("program").value;
	 templateName=document.getElementById("templateNameId").value;
	 programTypeId=document.getElementById("programType").value;
 getGroupTemplate(programTypeId,programId,templateName,updateGroupTemplateList);
}
function updateGroupTemplateList(req){
	var responseObj = req.responseXML.documentElement;
	var items = responseObj.getElementsByTagName("option");
	
	var htm="<table width='100%' cellspacing='1' cellpadding='2'>  <tr height='25px' class='row-odd'>";
	htm=htm+"<td>"+"Sl No"+"</td>"+"<td>"+"Program Type"+"</td>"+"<td>"+"Program"+"</td>"+"<td>"+"Course"+"</td>"+"<td>"+"Template Name"+"</td>"+"<td>"+"View"+"</td>"+"<td>"+"Edit"+"</td><td>"+"Delete"+"</td></tr>";

		if(items != null ){
			 var slNo = 1;
			for ( var i = 0; i < items.length; i++) {
				 var id = items[i].getElementsByTagName("id")[0].firstChild.nodeValue;
			     var programTypeId = items[i].getElementsByTagName("programTypeId")[0].firstChild.nodeValue;
			     var programId = items[i].getElementsByTagName("programId")[0].firstChild.nodeValue;
			     var courseId = items[i].getElementsByTagName("courseId")[0].firstChild.nodeValue;
			     var programTypeName = items[i].getElementsByTagName("programType")[0].firstChild.nodeValue;
			     var programName = items[i].getElementsByTagName("program")[0].firstChild.nodeValue;
			     var courseName = items[i].getElementsByTagName("course")[0].firstChild.nodeValue;
			     var templateName = items[i].getElementsByTagName("templateName")[0].firstChild.nodeValue;
				 if(slNo%2==0){
				     htm = htm + "<tr class='row-white'> ";
				 }else{
					 htm = htm + "<tr class='row-even'> ";
				 }

			     htm=htm + "<td width='10%' height='25'>"+slNo+ "</td>"+"<td width='15%'>"+programTypeName+ "</td>"+"<td width='15%'>"+programName+ "</td>"+"<td width='15%'>"+courseName+ "</td>"+"<td width='15%'>"+templateName+ "</td>";
			     htm = htm + "<td width='10%'> <div align='center'> <img src='images/View_icon.gif' width='16' height='18' style='cursor: pointer' onclick='viewTemplate("+id+")'/></div></td>";
			     htm = htm + "<td width='10%'> <div align='center'> <img src='images/edit_icon.gif' width='16' height='18' style='cursor: pointer' onclick='editTemplate("+id+")'/></div></td>";
			     htm = htm + "<td width='10%'> <div align='center'> <img src='images/delete_icon.gif' width='16' height='16' style='cursor: pointer' onclick='deleteTemplate("+id+")'/></div></td>";
			     htm=htm+"</tr>";
			     slNo++;
			}
		}
		htm = htm + "</table>";
		document.getElementById("display_Details").innerHTML = htm;
}
</script>
<html:form action="/CreateTemplate" method="post">
<html:hidden property="method" styleId="method"	value="createGroupTemplate" />
<html:hidden property="templateId" styleId="templateId" />
<html:hidden property="formName" value="templateForm" />
<html:hidden property="pageType" value="1" />
<table width="98%" border="0">
  <tr>
    <td><span class="heading"><bean:message key="knowledgepro.admin"/><span class="Bredcrumbs">&gt;&gt;Group Template&gt;&gt;</span></span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="10"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="body" ><div align="left"><strong class="boxheader">Group Template</strong></div></td>
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
		                 			    	<c:if test="${templateForm.programTypeId != null && templateForm.programTypeId != ''}">
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
												<c:if test="${templateForm.programId != null && templateForm.programId != ''}">
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
                            	<!-- <input type="hidden" id="templatenameId" name="template" value='<bean:write name="templateForm" property="templateName"/>' /> -->	
                            	<html:select property="templateName" styleClass="comboMediumLarge" styleId="templateNameId" onchange="getGroupTemplateList();">
                            		<html:option value=" "><bean:message key="knowledgepro.admin.select"/></html:option>
                            		<html:option value="Address Certificate">Address Certificate</html:option>
                            		<html:option value="Address And Date Of Birth">Address And Date Of Birth</html:option>
                            		<html:option value="Admission Card">Admission Card</html:option>
                            		<html:option value="Admission Mail">Admission Mail</html:option>
                            		<html:option value="Application Status Update Mail">Application Status Update Mail</html:option>
                            		<html:option value="Admit Card">Admit Card</html:option>
                            		<html:option value="AMC/Warranty Expiry">AMC/Warranty Expiry Notification</html:option>
                            		<html:option value="Application Instruction">Application Instruction</html:option>
                            		<html:option value="Application Print">Application Print</html:option>
                            		<html:option value="Attendance SHORTAGE TEMPLATE">Attendance SHORTAGE TEMPLATE</html:option>
                            		<html:option value="Attempt Certificate">Attempt Certificate</html:option>
                            		<html:option value="Character Certificate">Character Certificate</html:option>
									<html:option value="Date Of Birth">Date Of Birth</html:option>
									<html:option value="Fee Details">Fee Details</html:option>
									<html:option value="Foreign NOC">Foreign NOC</html:option>
                            		<html:option value="Guidelines">Guidelines</html:option>
                            		<html:option value="Guidelines For NRI">Guidelines - International</html:option>
                            		<html:option value="Interview Rejected">Interview Rejected</html:option>
                            		<html:option value="MARK TRANSCRIPT- I PU">MARK TRANSCRIPT- I PU</html:option>
                            		<html:option value="MARK TRANSCRIPT - II PU">MARK TRANSCRIPT - II PU</html:option>
                            		<html:option value="MARK TRANSCRIPT - I and II PU">MARK TRANSCRIPT - I and II PU</html:option>
                            		<html:option value="Medium Of Instruction">Medium Of Instruction</html:option>
                            		<html:option value="NCC">NCC</html:option>
                            		<html:option value="Mother Tongue">Mother Tongue</html:option>
									<html:option value="No Due">No Due</html:option>
                            		<html:option value="Password">Password</html:option>
                            		<html:option value="Pending Document">Pending Document</html:option>
                            		<html:option value="Progress Report">Progress Report</html:option>
                            		<html:option value="Scholarship Details">Scholarship Details</html:option>
									<html:option value="Sports Template">Sports Template</html:option>
                            		<html:option value="Selection Process Mail">Selection Process Mail</html:option>
                            		<html:option value="Submission Mail">Submission Mail</html:option>
                            		<html:option value="Terms And Condition">Terms And Condition</html:option>
                            		<html:option value="Tuition Fee">Tuition Fee</html:option>
									<html:option value="Visa Letter">Visa Letter</html:option>
									<html:option value="Application Received Mail">Application Received Mail</html:option>
									<html:option value="Edit Selection Process Mail">Edit Selection Process Mail</html:option>
									<html:option value="Submission Mail-Offline">Submission Mail-Offline</html:option>
									<html:option value="Revaluation / Re-totaling Mail">Revaluation / Re-totaling Mail</html:option>
									<html:option value="Revaluation / Re-totaling Status Update Mail">Revaluation / Re-totaling Status Update Mail</html:option>
									<html:option value="Employee Application Submission Mail_Applicant">Employee Application Submission Mail_Applicant</html:option>
									<html:option value="Employee Application Submission Mail_Admin">Employee Application Submission Mail_Admin</html:option>
									<html:option value="Employee-Online Leave Application">Employee-Online Leave Application</html:option>
									<html:option value="Employee-Approve Leave">Employee-Approve Leave</html:option> 
									<html:option value="Employee-Return/Clarify Leave">Employee-Return/Clarify Leave</html:option>
									<html:option value="Employee-Authorization Leave">Employee-Authorization Leave</html:option>
									<html:option value="Employee-Return Leave">Employee-Authorization Return Leave</html:option>
									<html:option value="Employee-Request Doc Leave">Employee-Authorization Request Doc Leave</html:option>
									<html:option value="Online payment Receipts">Online payment Receipts</html:option>
									<html:option value="Research and Publication Mail For Approver">Research and Publication Mail For Approver</html:option>
									<html:option value="Research and Publication Mail For Employee">Research and Publication Mail For Employee</html:option>
									<html:option value="Research and Publication Approved Mail For Employee">Research and Publication Approved Mail For Employee</html:option>
									<html:option value="Online payment Acknowledgement Slip">Online payment Acknowledgement Slip</html:option>
									<html:option value="Certificate Request Submit Success Mail for student">Certificate Request Submit Success Mail for student</html:option>											
									<html:option value="Certificate Request Completed Mail for student">Certificate Request Completed Mail for student</html:option>	
									<html:option value="Certificate Request Rejected Mail for student">Certificate Request Rejected Mail for student</html:option>																	
									<html:option value="Research and Publication Rejected Mail For Employee">Research and Publication Rejected Mail For Employee</html:option>
									<html:option value="Online Application Remainder Mail">Online Application Remainder Mail</html:option>		
									<html:option value="Phd- Generate Guides Remuneration Advice">Phd- Generate Guides Remuneration Advice</html:option>
									<html:option value="Phd- Document pending Reminder mails">Phd- Document pending Reminder mails</html:option>	
									<html:option value="Phd- Document Pending Due mails">Phd- Document Pending Due mails</html:option>	
									<html:option value="Phd- Fee payment Due">Phd- Fee payment Due</html:option>																																																	
									<html:option value="Holistic/Indian Constitution Repeat Exam Application">Holistic/Indian Constitution Repeat Exam Application</html:option>
									<html:option value="Hostel waiting list intimation">Hostel waiting list intimation</html:option>	
									<html:option value="Valuation Schedule Remainder">Valuation Schedule Remainder</html:option>
									<html:option value="Auditorium Booking Events">Auditorium Booking Events</html:option>
									<html:option value="Auditorium Approving Events">Auditorium Approving Events</html:option>	
									<html:option value="Hostel Leave - Student">Hostel Leave - Student</html:option>
                            		<html:option value="Hostel Leave Reject">Hostel Leave Reject</html:option>																																			
									<html:option value="News and Events Submitted">News and Events Submitted</html:option>
									<html:option value="News and Events Admin Notification mail">News and Events Admin Notification mail</html:option>
									<html:option value="Hostel Absent Mail For Student">Hostel Absent Mail For Student </html:option>
									<html:option value="Hostel Absent Mail For Parent">Hostel Absent Mail For Parent </html:option>
									<html:option value="News and Events Sender Notification mail">News and Events Sender Notification mail</html:option>
									<html:option value="Smart Card Status">Smart Card Lost/Correction Mail</html:option>
									<html:option value="Exam-Question Paper Checking Alert Mail To Teachers">Exam-Question Paper Checking Alert Mail To Teachers</html:option>	
									<html:option value="SAP Registration Admin Notification mail">SAP Registration Admin Notification mail</html:option>
									<html:option value="SAP Keys For Student mail">SAP Keys For Student mail</html:option>
									<html:option value="Support Request Status Mail For Staff">Support Request Status Mail For Staff</html:option>
									<html:option value="Support Request Status Mail For Student">Support Request Status Mail For Student</html:option>
									<html:option value="Support Request Escalation2">Support Request Escalation2</html:option>
									<html:option value="Support Request Submission Email">Support Request Submission Email</html:option>
									<html:option value="Support Request Escalation1">Support Request Escalation1</html:option>
									<html:option value="Online payment confirmation-Billdesk">Online payment confirmation-Billdesk</html:option>
								    <html:option value="Mail For Repeat Mid Sem Exam Application Submit">Mail For Repeat Mid Sem Exam Application Submit</html:option>
								    <html:option value="Mail For Repeat Mid Sem Exam Application Approved">Mail For Repeat Mid Sem Exam Application Approved</html:option>
								    <html:option value="Reminder Mail For Mid Semester Repeat Exam Fee Payment">Reminder Mail For Mid Semester Repeat Exam Fee Payment</html:option>
								    <html:option value="Mail For Mid Semester Repeat Exam Fee Exemption">Mail For Mid Semester Repeat Exam Fee Exemption</html:option>
									<html:option value="Mail For Repeat Mid Sem Exam Application Rejected">Mail For Repeat Mid Sem Exam Application Rejected</html:option>
									<html:option value="Photo Upload Student Login-Approval">Photo Upload from Student Login-Approval</html:option>
									<html:option value="Photo Upload Student Login-Rejection">Photo Upload from Student Login-Rejection</html:option>
									<html:option value="Employee Add Notification">Employee Add Notification</html:option>
									<html:option value="Hostel Re-Admission Selection">Hostel Re-Admission Selection</html:option>
								</html:select>
								<img src="images/questionMark.jpg" style="cursor:pointer" width="20" height="20"
								 onclick="openHtml()" title="Help">
								 </div>
							</td>
							<td class="row-odd">&nbsp;</td>
                            <td class="row-even">&nbsp;</td>
							<td class="row-odd">&nbsp;</td>
                            <td class="row-even">&nbsp;</td>
                           </tr>
                           <tr>
                             <td width="50%" height="25" class="row-odd" colspan="6">
								<FCK:editor instanceName="EditorDefault"  toolbarSet="Default">
									<jsp:attribute name="value">
										<c:out value="${templateForm.templateDescription}" escapeXml="false"></c:out>
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
                       <div id="display_Details">
                       <logic:notEmpty name="templateForm" property="templateList">
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
                           <logic:iterate id="template" name="templateForm" property="templateList" indexId="count">
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
                            </tr>
                          </logic:iterate>
                      </table></logic:notEmpty></div>
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
<!-- <script type="text/javascript">
	var templateName = document.getElementById("templatenameId").value;
	if (templateName.length != 0) {
		document.getElementById("templateNameId").value = templateName;
	}
</script> -->