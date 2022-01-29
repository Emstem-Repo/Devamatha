
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@page import="java.util.Map,java.util.HashMap"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<script language="JavaScript" src="js/ajax/AjaxUtil.js"></script>
<script language="JavaScript" src="js/ajax/Ajax.js"></script>
<title>:: CMS ::</title>
<script type="text/javascript">
function getDetailForDownload(formatNamejsp) {
	document.downloadFormatsForm.method.value="getStreamInfo";
	document.downloadFormatsForm.formatName.value=formatNamejsp;
	document.downloadFormatsForm.submit();
	myRef = window.open(url, "Download Formats", "left=20,top=20,width=900,height=900,toolbar=1,resizable=0,scrollbars=1");
}
</script>

<html:form action="/printDownloadFormat">

	<html:hidden property="method" styleId="method"	value="" />
	<html:hidden property="formName" value="downloadFormatsForm"  styleId="formName"/>
	<html:hidden property="formatName" value="" />
	<html:hidden property="pageType" value="1" />


<table width="100%" border="0">
  <tr>
    <td><span class="heading"><bean:message	key="knowledgepro.admission" /> <span class="Bredcrumbs">&gt;&gt; <bean:message key="knowledgepro.admission.downloadFormats" /> &gt;&gt;</span></span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="body" ><strong class="boxheader"> <bean:message key="knowledgepro.admission.downloadFormats" /></strong></td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="10" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
         
          
          
          
          <tr>
							<td height="45" colspan="4">
							<table width="98%" border="0" align="center" cellpadding="0"
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
									<td width="20%" height="25" class="row-odd"><div align="center"><bean:message key="knowledgepro.admission"/></div></td>
									<td width="40%" height="25" class="row-odd" align="left"></td>
									<td width="20%" height="25" class="row-odd" align="left"></td>
									</tr>
										<tr>
											<td width="20%" height="25" class="row-odd"><div align="center"><bean:message
												key="knowledgepro.admission.downloadFormats.formatName" /></div></td>
											<td width="40%" height="25" class="row-odd" align="left"><bean:message
												key="knowledgepro.admission.downloadFormats.formatDescription" /></td>
											<td width="20%" height="25" class="row-odd" align="left"><bean:message
												key="knowledgepro.admission.downloadFormats.downloadLink" /> </td>
										</tr>
										
													<tr>
														<td  height="25" class="row-even"
															align=left>Application Status Update</td>
														<td height="25" class="row-even"
															align="left">To upload the status of the online applications for which any document is missing
															 when we received the application form via post/courier</td>
														<td  height="25" class="row-even"
															align="left"><a href="#" onclick="getDetailForDownload('ApplicationStatusUpdate')"><bean:message key="knowledgepro.reports.submit" /></a></td>
													</tr>
													
														<tr>
														<td  height="25" class="row-white"
															align=left>Upload entrance Result</td>
														<td height="25" class="row-white"
															align="left">To upload the entrance result in percentage. 
															This can be used for any interview rounds which has % of mark instead of grade. Only one result can be uploaded per student for an interview round/sub round</td>
														<td  height="25" class="row-white"
															align="left"><a href="#" onclick="getDetailForDownload('UploadEntranceResult')"><bean:message key="knowledgepro.reports.submit" /></a></td>
													</tr>
														<tr>
														<td  height="25" class="row-even"
															align="left">Upload interview Status</td>
														<td  height="25" class="row-even"
													 		align="left">To upload the status of the interview round. Eg: Once the GD_MP is completed the status to be uploaded. 
															Student with 'Selected' status will be moved to the next round of interview and if date,time and venue is mentioned in the sheet, the students E-Admit Card will also be generated.
															Please note that Date should be in the format of dd/MM/yyyy and time should be in hh:mm in text format cells. Date, time and venue is not mandatory. If next round selection process to be scheduled along with the upload, the date, time and Venue are mandatory.
															 Against not selected / wait listed students any comment can be entered.</td>
														<td  height="25" class="row-even"
															align="left"><a href="#" onclick="getDetailForDownload('UploadInterviewStatus')"><bean:message key="knowledgepro.reports.submit" /></a></td>
													</tr>
															<tr>
														<td  height="25" class="row-white"
															align=left>Upload final merit list</td>
														<td height="25" class="row-white"
															align="left">To upload the final merit list. The course code should have the course code for which they got selected. The Admission Scheduled Date should be in the form of dd/mm/yyyy and the admission scheduled time column (hh:mm:ss) should not exceed 20 characters. 
															For Not Selected students the status should be Not Selected and for students in waiting list the status should be Waitlisted
                                                            In both the cases the remarks is mandatory. The remarks will be sent as SMS and also will appear in online status.
                                                            For selected students the status should be Selected and remarks is not required.</td>
														<td  height="25" class="row-white"
															align="left"><a href="#" onclick="getDetailForDownload('UploadFinalMeritList')"><bean:message key="knowledgepro.reports.submit" /></a></td>
													</tr>
														<tr>
														<td  height="25" class="row-even"
															align="left">Register No Upload</td>
														<td  height="25" class="row-even"
															align="left">To upload the register no and class for each student</td>
														<td  height="25" class="row-even"
															align="left"><a href="#" onclick="getDetailForDownload('RegisterNoUpload')"><bean:message key="knowledgepro.reports.submit" /></a></td>
													</tr>
													<tr>
														<td  height="25" class="row-even"
															align="left">Unversity Register No Upload</td>
														<td  height="25" class="row-even"
															align="left">To upload the University register no  for each student</td>
														<td  height="25" class="row-even"
															align="left"><a href="#" onclick="getDetailForDownload('UniversityRegisterNo')"><bean:message key="knowledgepro.reports.submit" /></a></td>
													</tr>
													
															<tr>
														<td  height="25" class="row-white"
															align=left>Smart card Number Upload</td>
														<td height="25" class="row-white"
															align="left">To upload the smart card no and the account no received from the bank</td>
														<td  height="25" class="row-white"
															align="left"><a href="#" onclick="getDetailForDownload('SmartCardNumberUpload')"><bean:message key="knowledgepro.reports.submit" /></a></td>
													</tr>
															<tr>
														<td  height="25" class="row-even"
															align=left>Mobile Number Upload</td>
														<td height="25" class="row-even"
															align="left">To upload the mobile numbers of students if not available in the software </td>
														<td  height="25" class="row-even"
															align="left"><a href="#" onclick="getDetailForDownload('MobileNumberUpload')"><bean:message key="knowledgepro.reports.submit" /></a></td>
													</tr>
															<tr>
														<td  height="25" class="row-white"
															align=left>Second Language Upload</td>
														<td height="25" class="row-white"
															align="left">To upload the second languages of students </td>
														<td  height="25" class="row-white"
															align="left"><a href="#" onclick="getDetailForDownload('SecondLanguageUpload')"><bean:message key="knowledgepro.reports.submit" /></a></td>
													</tr>
													<tr>
														<td  height="25" class="row-even"
															align="left">Upload interview Result</td>
														<td  height="25" class="row-even"
															align="left">To upload the interview result grades for any interview round/sub round. 
															There should be as many grade columns as the no of interview panelists</td>
														<td  height="25" class="row-even"
															align="left">To download the format of upload interview result, please use the menu link "Download Interview Result Format in Admission Module" </td>
													</tr>
														<tr>
														<td  height="25" class="row-white"
															align=left>Student University Email Upload</td>
														<td height="25" class="row-white"
															align="left">To upload the university email of students.
													 </td>
														<td  height="25" class="row-white"
															align="left"><a href="#" onclick="getDetailForDownload('UniversityEmailUpload')"><bean:message key="knowledgepro.reports.submit" /></a></td>
													</tr>
														<tr>
														<td  height="25" class="row-even"
															align="left">OMR Data Upload</td>
														<td  height="25" class="row-even"
															align="left">To upload the OMR data of students. 
														</td>
														<td  height="25" class="row-even"
															align="left"><a href="#" onclick="getDetailForDownload('OMRDataUpload')"><bean:message key="knowledgepro.reports.submit" /></a></td>
													</tr>
									<tr>
									<td width="20%" height="25" class="row-odd"><div align="center"><bean:message key="knowledgepro.admin.sec.EmployeeCategory"/></div></td>
									<td width="40%" height="25" class="row-odd" align="left"></td>
									<td width="20%" height="25" class="row-odd" align="left"></td>
									</tr>
													<tr>
														<td  height="25" class="row-even"
															align=left>Employee Attendance Upload</td>
														<td height="25" class="row-even"
															align="left">To upload the attendance of employees. Note: The "Date" column should be filled with dd/mm/yyyy format only.
													 </td>
														<td  height="25" class="row-even"
															align="left"><a href="#" onclick="getDetailForDownload('EmployeeAttendanceUpload')"><bean:message key="knowledgepro.reports.submit" /></a></td>
													</tr>
													<tr>
														<td  height="25" class="row-white"
															align=left>Employee Leave Upload</td>
														<td height="25" class="row-white"
															align="left">To upload the leave of employees. Note: The date columns should be filled with dd/mm/yyyy format only.
													 </td>
														<td  height="25" class="row-white"
															align="left"><a href="#" onclick="getDetailForDownload('EmployeeLeaveUpload')"><bean:message key="knowledgepro.reports.submit" /></a></td>
													</tr>
													<tr>
														<td  height="25" class="row-even"
															align=left>Employee Smart Card Number Upload</td>
														<td height="25" class="row-even"
															align="left">To upload the smart card no and the account no received from the bank.
													 </td>
														<td  height="25" class="row-even"
															align="left"><a href="#" onclick="getDetailForDownload('EmployeeSmartCardNumberUpload')"><bean:message key="knowledgepro.reports.submit" /></a></td>
													</tr>
															<tr>
														<td  height="25" class="row-white"
															align=left>Employee Pay Scale and Grade Upload</td>
														<td height="25" class="row-white"
															align="left">To upload the pay scale, grade details and allowance values of employees.Note: Please add remaining Allowance 
															names, as columns if any is missed. Also make sure the Grade column values are same as the ones entered in the master entry screen "PayScaleDetails"
													 </td>
														<td  height="25" class="row-white"
															align="left"><a href="#" onclick="getDetailForDownload('EmployeePayScaleGradeUpload')"><bean:message key="knowledgepro.reports.submit" /></a></td>
													</tr>
									<tr>
									<td width="20%" height="25" class="row-odd"><div align="center">Exam</div></td>
									<td width="40%" height="25" class="row-odd" align="left"></td>
									<td width="20%" height="25" class="row-odd" align="left"></td>
									</tr>
													<tr>
														<td  height="25" class="row-even"
															align=left>Upload Marks</td>
														<td height="25" class="row-even"
															align="left">To upload the Marks. Note: In case of 2 Evaluators add a new column as Marks2. Marks2 should have 2nd Evaluator Marks
													 </td>
														<td  height="25" class="row-even"
															align="left"><a href="#" onclick="getDetailForDownload('UploadMarks')"><bean:message key="knowledgepro.reports.submit" /></a></td>
													</tr>
													<tr>
														<td  height="25" class="row-white"
															align=left>Upload Block Hallticket/Marks Card</td>
														<td height="25" class="row-white"
															align="left">To upload the blocked register numbers with the reason.Please note that reason is mandatory and no duplicate register numbers allowed in same sheet
													 </td>
														<td  height="25" class="row-white"
															align="left"><a href="#" onclick="getDetailForDownload('UploadBlockHallticketOrMarks')"><bean:message key="knowledgepro.reports.submit" /></a></td>
													</tr>
													<tr>
									<td width="20%" height="25" class="row-odd"><div align="center">Hostel</div></td>
									<td width="40%" height="25" class="row-odd" align="left"></td>
									<td width="20%" height="25" class="row-odd" align="left"></td>
									</tr>
												<tr>
													<td  height="25" class="row-even" align=left>Upload Room Details</td>
													<td height="25" class="row-even"
															align="left">To upload Room Details enter the details as mention the sheet
													 </td>
													<td  height="25" class="row-even"
															align="left"><a href="#" onclick="getDetailForDownload('UploadRoomDetails')"><bean:message key="knowledgepro.reports.submit" /></a></td>
												</tr>
												
												<tr>
													<td  height="25" class="row-even" align=left>Upload Re-admission application</td>
													<td height="25" class="row-even"
															align="left">To Upload Offline Application received for Re-admission
													 </td>
													<td  height="25" class="row-even"
															align="left"><a href="#" onclick="getDetailForDownload('UploadOfflineApplicationDetails')"><bean:message key="knowledgepro.reports.submit" /></a></td>
												</tr>
												<tr>
													<td  height="25" class="row-even" align=left>Upload Re-admission Selection</td>
													<td height="25" class="row-even"
															align="left">To Upload the selected students with room type selected for Re-admission 
													 </td>
													<td  height="25" class="row-even"
															align="left"><a href="#" onclick="getDetailForDownload('ReadmissionSelection')"><bean:message key="knowledgepro.reports.submit" /></a></td>
												</tr>
												
												<tr>
									<td width="20%" height="25" class="row-odd"><div align="center">SAP Exam</div></td>
									<td width="40%" height="25" class="row-odd" align="left"></td>
									<td width="20%" height="25" class="row-odd" align="left"></td>
									</tr>
												<tr>
													<td  height="25" class="row-even" align=left>SAP Marks Upload</td>
													<td height="25" class="row-even"
															align="left">To upload the exam results of SAP. 
													 </td>
													<td  height="25" class="row-even"
															align="left"><a href="#" onclick="getDetailForDownload('SAPMarksUploadDetails')"><bean:message key="knowledgepro.reports.submit" /></a></td>
												</tr>
												
									</table>
									</td>
									<td width="5" height="30" background="images/right.gif"></td>
								</tr>
								<tr>
									<td height="5"><img src="images/04.gif" width="5"
										height="5" /></td>
									<td background="images/05.gif"></td>
									<td><img src="images/06.gif" /></td>
								</tr>
							</table>
							</td>
						</tr>
          
        </table></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
          <tr>
            
            <td width="1%"></td>
          
            <td width="1%"></td>
            
          </tr>
        </table> </td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      
      <tr>
        <td height="10" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news">&nbsp;</td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
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

