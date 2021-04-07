<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<html:html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href="css/styles.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 5px;
	margin-right: 0px;
	margin-bottom: 0px;
}
-->
</style>
</head>
<body>
<table width="98%" border="0">
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" cellspacing="1" cellpadding="2">
						<tr>
							<td colspan="2" class="heading">
							<table width="99%" border="0" align="center" cellpadding="0"
								cellspacing="0">
								<tr>
									<td><img src="images/01.gif" width="5" height="5"></td>
									<td width="914" background="images/02.gif"></td>
									<td><img src="images/03.gif" width="5" height="5"></td>
								</tr>
								<tr>
									<td width="5" background="images/left.gif"></td>
									<td valign="top">
									<table width="100%" border="0" cellpadding="0" cellspacing="1">
										<tr>
			                				<td height="25" class="row-odd"><bean:message key="knowledgepro.emailtemplate.templatename"/></td>
			                				<td height="25" class="row-odd">Supported Tags</td>
			              				</tr>
			                			<tr class="row-even">
			                				<td height="25" class="row-odd">Employee Attendance Remainder SMS</td>
			                				<td height="25" >
	                							<table>
	                								<tr>
	                									<td><bean:message key="knowledgepro.employee.template.name"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.employee.template.fingerPrintId"/></td>
	                								</tr>
	                							</table>
			                				</td>
			                			</tr>
			                			<tr class="row-even">
			                				<td height="25" class="row-odd">Student Marks Correction Password</td>
			                				<td height="25" >
	                							<table>
	                								<tr>
	                									<td><bean:message key="knowledgepro.sms.template.AdditionalSecurity.password"/></td>
	                								</tr>
	                								
	                							</table>
			                				</td>
			                			</tr>
			                				<tr class="row-even">
			                				<td height="25" class="row-odd">Online Application Submission</td>
			                				<td height="25" >
	                							<table>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.applicationNo"/></td>
	                								</tr>
	                							</table>
			                				</td>
			                			</tr>
			                			<tr class="row-even">
			                				<td height="25" class="row-odd">Interview Status for not selected</td>
			                				<td height="25" >
	                							<table>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admission.interview.status.upload.template"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.applicationNo"/></td>
	                								</tr>
	                							</table>
			                				</td>
			                			</tr>
			                			<tr class="row-even">
			                				<td height="25" class="row-odd">Final Merit List Upload Not Selected</td>
			                				<td height="25" >
	                							<table>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admission.interview.status.upload.template"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.applicationNo"/></td>
	                								</tr>
	                							</table>
			                				</td>
			                			</tr>
			                				<tr class="row-even">
			                				<td height="25" class="row-odd">Admission Confirmation</td>
			                				<td height="25" >
	                							<table>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.course"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.applicationNo"/></td>
	                								</tr>
	                							</table>
			                				</td>
			                			</tr>
			                				<tr class="row-even">
			                				<td height="25" class="row-odd">Admission Cancellation</td>
			                				<td height="25" >
	                							<table>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.course"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.applicationNo"/></td>
	                								</tr>
	                							</table>
			                				</td>
			                			</tr>
			                			<tr>
			                			    <td height="25" class="row-odd" >Certificate Request Completed SMS</td>
			                			    <td height="25" class="row-even">
			                			       <table>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.template.certificateName"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.template.campus"/></td>
        											</tr>
        								       </table>
			                			    </td>
			                			</tr>
			                			<tr>
			                			    <td height="25" class="row-odd" >Certificate Request Rejected SMS</td>
			                			    <td height="25" class="row-even">
			                			       <table>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.template.certificateName"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.template.campus"/></td>
        											</tr>
        								       </table>
			                			    </td>
			                			</tr>
			                				<tr>
			                			    <td height="25" class="row-odd" >Hostel waiting list intimation</td>
			                			    <td height="25" class="row-even">
			                			       <table>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.template.applicationNo"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.hostel.template.hostel.application.number"/></td>
        											</tr>
        								       </table>
			                			    </td>
			                			</tr>
			                			<tr>
			                			    <td height="25" class="row-odd" >Valuation Schedule Remainder</td>
			                			    <td height="25" class="row-even">
			                			       <table>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.template.exam.date"/></td>
        											</tr>
        											
        										</table>
			                			    </td>
			                			</tr>
			                			<tr>
			                			    <td height="25" class="row-odd" >Hostel Leave - Parent</td>
			                			    <td height="25" class="row-even">
			                			       <table>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.hostel.leave.regno"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.hostel.leave.name"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.hostel.fromDate"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.hostel.toDate"/></td>
        											</tr>
        								       </table>
			                			    </td>
			                			</tr>
			                			<tr>
			                			    <td height="25" class="row-odd" >Hostel Absentees</td>
			                			    <td height="25" class="row-even">
			                			       <table>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.hostel.absentees.hostel"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.hostel.absentees.unit"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.hostel.absentees.block"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.hostel.absentees.count"/></td>
        											</tr>
        								       </table>
			                			    </td>
			                			</tr><tr>
			                			   <td height="25" class="row-odd" >Auditorium Booking Status SMS</td>
			                			    <td height="25" class="row-even">
			                			       <table>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.template.date"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.template.username"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.template.auditorium.venue"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.admission.final.merit.list.upload.template"/></td>
        											</tr>
        											
        										</table>
			                			    </td>
			                			</tr>
			                			<tr>
			                			    <td height="25" class="row-odd" >Hostel Leave - Student</td>
			                			    <td height="25" class="row-even">
			                			       <table>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.hostel.leave.name"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.hostel.leave.regno"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.hostel.fromDate"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.hostel.toDate"/></td>
        											</tr>
        								       </table>
			                			    </td>
			                			</tr>
			                			<tr>
			                			    <td height="25" class="row-odd" >Hostel Leave Reject</td>
			                			    <td height="25" class="row-even">
			                			       <table>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.hostel.leave.name"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.hostel.leave.regno"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.hostel.fromDate"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.hostel.toDate"/></td>
        											</tr>
        								       </table>
			                			    </td>
			                			</tr>
			                			<tr>
			                			    <td height="25" class="row-odd" >Hostel Absent Sms For Student </td>
			                			    <td height="25" class="row-even">
			                			       <table>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.sms.template.registerNo"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.template.studentname"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.template.date"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.template.session"/></td>
        											</tr>
        										</table>
			                			    </td>
			                			</tr>
			                			<tr>
			                			    <td height="25" class="row-odd" >Hostel Absent Sms For Parent </td>
			                			    <td height="25" class="row-even">
			                			       <table>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.sms.template.registerNo"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.template.studentname"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.template.date"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.template.session"/></td>
        											</tr>
        										</table>
			                			    </td>
			                			</tr>
			                			<tr>
			                			    <td height="25" class="row-odd" >Smart Card Lost or Correction SMS</td>
			                			    <td height="25" class="row-even">
			                			       <table>
			                			       		<tr>
        											   <td><bean:message key="knowledgepro.admin.template.name"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.template.number"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.sms.template.date"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.smartcard.lostcorrection.processed.template"/></td>
        											</tr>
        								       </table>
			                			    </td>
			                			</tr>
			                			<tr>
			                			    <td height="25" class="row-odd" >Student Attendance Regular Absent SMS For Parent</td>
			                			    <td height="25" class="row-even">
			                			       <table>
			                			       		<tr>
        											   <td><bean:message key="knowledgepro.admin.template.studentname"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.sms.template.registerNo"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.template.subjectcodes"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.template.subjectnames"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.template.Exam.Date"/></td>
        											</tr>
        								       </table>
			                			    </td>
			                			</tr>
			                			<tr>
			                			    <td height="25" class="row-odd" >Student Attendance Internal Absent SMS For Parent</td>
			                			    <td height="25" class="row-even">
			                			       <table>
			                			       		<tr>
        											   <td><bean:message key="knowledgepro.admin.template.studentname"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.sms.template.registerNo"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.template.subjectcodes"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.template.subjectnames"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.template.Exam.Date"/></td>
        											</tr>
        								       </table>
			                			    </td>
			                			</tr>
			                			<tr>
			                			    <td height="25" class="row-odd" >Exam-Question Paper Checking Alert Sms To Teachers</td>
			                			    <td height="25" class="row-even">
			                			       <table>
			                			          <tr>
	                									<td><bean:message key="knowledgepro.exam.teacher.name"/></td>
	                								</tr>
        										   <tr>
	                									<td><bean:message key="knowledgepro.exam.subject.code"/></td>
	                								</tr>
        											<tr>
	                									<td><bean:message key="knowledgepro.exam.subject.name"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.exam.exam.date"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.exam.exam.session"/></td>
	                								</tr>
	                								</table>
			                			    </td>
			                			</tr>
			                			<tr>
			                			    <td height="25" class="row-odd" >SAP Registration Student Sms</td>
			                			    <td height="25" class="row-even">
			                			       <table>
			                			       		
        								       </table>
			                			    </td>
			                			</tr>
			                			<tr>
			                			    <td height="25" class="row-odd" >SAP Exam - Reminder for student</td>
			                			    <td height="25" class="row-even">
			                			       <table>
			                			          <tr>
	                									<td><bean:message key="knowledgepro.sap.exam.date"/></td>
	                								</tr>
        										   <tr>
	                									<td><bean:message key="knowledgepro.sap.exam.session"/></td>
	                								</tr>
        											<tr>
	                									<td><bean:message key="knowledgepro.sap.exam.venue"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.sap.exam.registerNo"/></td>
	                								</tr>
	                								</table>
			                			    </td>
			                			</tr>
			                			<tr>
			                			    <td height="25" class="row-odd" >SAP Exam - Reminder for Invigilators</td>
			                			    <td height="25" class="row-even">
			                			       <table>
			                			          <tr>
	                									<td><bean:message key="knowledgepro.sap.exam.date"/></td>
	                								</tr>
        										   <tr>
	                									<td><bean:message key="knowledgepro.sap.exam.session"/></td>
	                								</tr>
        											<tr>
	                									<td><bean:message key="knowledgepro.sap.exam.venue"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.sap.exam.staff.name"/></td>
	                								</tr>
	                								</table>
			                			    </td>
			                			</tr>
			                			<tr>
			                			    <td height="25" class="row-odd" >Support Request Status SMS For Staff</td>
			                			    <td height="25" class="row-even">
			                			       <table>
        											<tr>
        											   <td><bean:message key="knowledgepro.template.support.request.employee.id"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.template.phd.SubmissionDate"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.template.support.request.category"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.admission.interview.status.upload.template"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.admission.interview.requestId.upload.template"/></td>
        											</tr>
        										</table>
			                			    </td>
			                			</tr>
			                			<tr>
			                			    <td height="25" class="row-odd" >Support Request Status SMS For Student</td>
			                			    <td height="25" class="row-even">
			                			       <table>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.sms.template.registerNo"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.template.phd.SubmissionDate"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.template.support.request.category"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.admission.interview.status.upload.template"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.admission.interview.requestId.upload.template"/></td>
        											</tr>
        										</table>
			                			    </td>
			                			</tr>
			                			<tr>
			                			    <td height="25" class="row-odd" >Valuation Remuneration Payment SMS</td>
			                			    <td height="25" class="row-even">
			                			       <table>
        											<tr>
        											   <td><bean:message key="knowledgepro.template.phd.GeneratedNo"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.template.phd.totalAmount"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.exam.remuniration.payment.vocher.date"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.remuniration.payment.mode"/></td>
        											</tr>
        										</table>
			                			    </td>
			                			</tr>
			                			<tr class="row-even">
			                				<td height="25" class="row-odd">Application Received</td>
			                				<td height="25" >
	                							<table>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.applicationNo"/></td>
	                								</tr>
	                							</table>
			                				</td>
			                			</tr>
			                			<tr class="row-even">
			                				<td height="25" class="row-odd">E Admit Card</td>
			                				<td height="25" >
	                							<table>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.applicationNo"/></td>
	                								</tr>
	                							</table>
			                				</td>
			                			</tr>
			                			<tr class="row-even">
			                				<td height="25" class="row-odd">E Admission Card</td>
			                				<td height="25" >
	                							<table>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.applicationNo"/></td>
	                								</tr>
	                							</table>
			                				</td>
			                			</tr>
			                			<tr class="row-even">
			                				<td height="25" class="row-odd">Edit E Admit Card</td>
			                				<td height="25" >
	                							<table>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.applicationNo"/></td>
	                								</tr>
	                							</table>
			                				</td>
			                			</tr>
			                			<tr class="row-even">
			                				<td height="25" class="row-odd">Offline Application Acknowledgement SMS</td>
			                				<td height="25" >
	                							<table>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.applicationNo"/></td>
	                								</tr>
	                							</table>
			                				</td>
			                			</tr>
			                			<tr class="row-even">
			                				<td height="25" class="row-odd">Interview Card Date Modification SMS</td>
			                				<td height="25" >
	                							<table>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.applicationNo"/></td>
	                									<td><bean:message key="knowledgepro.admin.template.SelectionDate"/></td>
	                								</tr>
	                							</table>
			                				</td>
			                			</tr>
			                			<tr>
			                			    <td height="25" class="row-odd" >SMS For Repeat Mid Sem Exam Application Submit</td>
			                			    <td height="25" class="row-even">
			                			       <table>
        											<tr>
        											   <td><bean:message key="knowledgepro.template.dob.registerno"/></td>
        											</tr>
        									  </table>
			                			    </td>
			                			</tr>
			                			 <tr>
			                			    <td height="25" class="row-odd" >SMS For Repeat Mid Sem Exam Application Approved</td>
			                			    <td height="25" class="row-even">
			                			       <table>
        											<tr>
        											   <td><bean:message key="knowledgepro.template.dob.registerno"/></td>
        											</tr>
        									  </table>
			                			    </td>
			                			</tr>
			                			<tr>
			                			    <td height="25" class="row-odd" >Reminder Sms For Mid Semester Repeat Exam Fee Payment</td>
			                			    <td height="25" class="row-even">
			                			       <table>
        											<tr>
        											   <td><bean:message key="knowledgepro.template.dob.registerno"/></td>
        											</tr>
        											<tr>
	                									<td><bean:message key="knowledgepro.admin.template.feesdate"/></td>
	                								</tr>
        									  </table>
			                			    </td>
			                			</tr>
			                			<tr>
			                			    <td height="25" class="row-odd" >SMS For Mid Semester Repeat Exam Fee Exemption</td>
			                			    <td height="25" class="row-even">
			                			       <table>
        											<tr>
        											   <td><bean:message key="knowledgepro.template.dob.registerno"/></td>
        											</tr>
        									  </table>
			                			    </td>
			                			</tr>
			                			<tr>
			                			    <td height="25" class="row-odd" >SMS For Repeat Mid Sem Exam Application Rejected</td>
			                			    <td height="25" class="row-even">
			                			       <table>
        											<tr>
        											   <td><bean:message key="knowledgepro.template.dob.registerno"/></td>
        											</tr>
        									  </table>
			                			    </td>
			                			</tr>
			                			<tr>
			                			    <td height="25" class="row-odd" >Photo Upload from Student Login-Approval</td>
			                			    <td height="25" class="row-even">
			                			       <table>
        											<tr>
        											   <td><bean:message key="knowledgepro.template.dob.registerno"/></td>
        											</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.studentname"/></td>
	                								</tr>
        										</table>
			                			    </td>
			                			</tr>
			                			<tr>
			                			    <td height="25" class="row-odd" >Photo Upload from Student Login-Rejection</td>
			                			    <td height="25" class="row-even">
			                			       <table>
        											<tr>
        											   <td><bean:message key="knowledgepro.template.dob.registerno"/></td>
        											</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.studentname"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.employee.reject.reason"/></td>
	                								</tr>
        										</table>
			                			    </td>
			                			</tr>	
			                			<tr>
			                			    <td height="25" class="row-odd" >Hostel Re-Admission Selection</td>
			                			    <td height="25" class="row-even">
			                			       <table>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.sms.template.registerNo"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.template.studentname"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.hostel.template.hostelname"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.hostel.template.roomtype"/></td>
        											</tr>
        								       </table>
			                			    </td>
			                			</tr>
			                			
			                			<tr>
											<td valign="bottom" colspan="2">
												<div align="center">
													<html:button property="" styleClass="formbutton" onclick="javascript:self.close();">
														<bean:message key="knowledgepro.close" />
													</html:button>
												</div>
											</td>
										</tr>		
			                		</table>
			                		</td>
									<td background="images/right.gif" width="5" height="22"></td>
								</tr>
								<tr>
									<td height="5"><img src="images/04.gif" width="5"
										height="5"></td>
									<td background="images/05.gif"></td>
									<td><img src="images/06.gif"></td>
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
					<td><img src="images/Tright_03_05.gif"></td>
					<td background="images/TcenterD.gif"></td>
					<td><img src="images/Tright_02.gif" width="9" height="29"></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</body>
</html:html>