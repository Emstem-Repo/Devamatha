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
			              				<tr>
			                				<td height="25" class="row-odd" >Submission Mail</td>
			                				<td height="25" class="row-even">
	                							<table>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.applicationNo"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.applicantName"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.dob"/></td>
	                								</tr>
	                							</table>
			                				</td>
			                			</tr>
			                			<tr>
			                			    <td height="25" class="row-odd" >Application Status Update Mail</td>
			                			    <td height="25" class="row-even">
			                			       <table>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.template.program"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.template.course"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.template.applicationNo"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.template.applicantName"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.template.dob"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="group.template.helper.application.status"/></td>
        											</tr>			                			       
			                			       </table>
			                			    </td>
			                			</tr>
			              				<tr>
			                				<td height="25" class="row-odd" >Admit Card/Admission Card</td>
			                				<td height="25" class="row-even">
	                							<table>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.program"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.course"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.selectedcourse"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.applicationNo"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.interviewType"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.applicantName"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.dob"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.pob"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.email"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.nationality"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.residentcategory"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.gender"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.barcode"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.subreligion"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.religion"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.caste"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.interviewDate"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.interviewTime"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.interviewVenue"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.photo"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.logo"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.academicYear"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.studentCurrentAddress"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.studentPermanentAddress"/></td>
	                								</tr>
	                								
	                								<tr>
	                									<td><bean:message key="group.template.help.exam.center.name"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="group.template.help.prefix"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="group.template.help.seat.no"/></td>
	                								</tr>
													<tr>
	                									<td><bean:message key="group.template.help.address"/></td>
	                								</tr>	                								
	                								<tr>
	                									<td><bean:message key="knowledgepro.admission.approve.final.merit.list.date"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.mode"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.admission.scheduled.date"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.admission.scheduled.time"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.admission.specialization.prefered"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.admission.scourse.commencement"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.admission.interview.scheduled.count"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.admission.interviewcard.created.date"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.admission.interviewcard.created.time"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.admission.interview.scheduled.history"/></td>
	                								</tr>
	                							</table>
			                				</td>
			                			</tr>
			                			<tr>
			                				<td height="25" class="row-odd">Admission Mail</td>
			                				<td height="25" class="row-white">
	                							<table>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.program"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.course"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.selectedcourse"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.applicationNo"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.applicantName"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.dob"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.pob"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.email"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.nationality"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.gender"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.subreligion"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.religion"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.caste"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.academicYear"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.studentCurrentAddress"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.studentPermanentAddress"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.logo"/></td>
	                								</tr>
	                							</table>
			                				</td>
			                			</tr>
			                			<tr>
			                				<td height="25" class="row-odd">Selection Process Mail / Edit Selection Process Mail</td>
			                				<td height="25" class="row-even">
	                							<table>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.program"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.course"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.applicationNo"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.applicantName"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.dob"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.pob"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.email"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.nationality"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.gender"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.subreligion"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.religion"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.caste"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.academicYear"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.studentCurrentAddress"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.studentPermanentAddress"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.interviewDate"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.interviewTime"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.interviewVenue"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.interviewType"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.logo"/></td>
	                								</tr>
	                							</table>
			                				</td>
			                			</tr>
			                			<tr>
			                				<td height="25" class="row-odd"><bean:message key="knowledgepro.admin.password"/></td>
			                				<td height="25" class="row-white">
	                							<table>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.fatherName"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.address"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.pincode"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.username"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.password"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.date"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.logo"/></td>
	                									
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.logo1"/></td>
	                   								</tr>
	                   								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.email"/></td>
	                   								</tr>
	                   								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.character.class"/></td>
	                   								</tr>
	                							</table>
			                				</td>
			                			</tr>
			                			<tr>
			                				<td height="25" class="row-odd"><bean:message key="KnowledgePro.template.applnInstr.name"/></td>
			                				<td height="25" class="row-even">
	                							<table>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.applicationNo"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.applicantName"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.email"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.program"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.course"/></td>
	                								</tr>
	                							</table>
			                				</td>
			                			</tr>
			                			<tr>
			                				<td height="25" class="row-odd"><bean:message key="knowledgepro.admin.guidelines.report"/></td>
			                				<td height="25" class="row-white">
	                							<table>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.printChallan"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.logo"/></td>
	                								</tr>
	                							</table>
			                				</td>
			                			</tr>
			                			<tr>
			                				<td height="25" class="row-odd"><bean:message key="knowledgepro.admin.guidelines.report.nri"/></td>
			                				<td height="25" class="row-white">
	                							<table>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.printChallan"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.logo"/></td>
	                								</tr>
	                							</table>
			                				</td>
			                			</tr>
			                			<tr>
			                				<td height="25" class="row-odd">Terms And Conditions</td>
			                				<td height="25" class="row-white">
	                							<table>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.logo"/></td>
	                								</tr>
	                							</table>
			                				</td>
			                			</tr>
			                			<tr>
			                				<td height="25" class="row-odd">AMC/Warranty Expiry Notification</td>
			                				<td height="25" class="row-white">
	                							<table>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.itemNo"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.itemName"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.itemInvLocation"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.itemDOE"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.itemVendorName"/></td>
	                								</tr>
	                							</table>
			                				</td>
			                			</tr>
			                			<tr>
			                				<td height="25" class="row-odd">Interview Rejected</td>
			                				<td height="25" class="row-white">
	                							<table>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.applicationNo"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.applicantName"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.interviewType"/></td>
	                								</tr>
	                							</table>
			                				</td>
			                			</tr>
			                			
			                			<tr>
			                				<td height="25" class="row-odd">Pending Documents</td>
			                				<td height="25" class="row-even">
	                							<table>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.logo"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.studentPhoto"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.name"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.applicationNo"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.courseCode"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.barcode"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.courseName"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.pendingDocuemntsNames"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.submittedDocumentsname"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.submissiondate"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.currentDate"/></td>
	                								</tr>
	                							</table>
			                				</td>
			                			</tr>
			                			
			                			<tr>
			                				<td height="25" class="row-odd">Progress Report</td>
			                				<td height="25" class="row-even">
	                							<table>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.logo"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.studentCurrentAddress"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.studentPermanentAddress"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.institutionaddress"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.institutionname"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.courseName"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.parentsaddress"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.guardianaddress"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.studentname"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.classname"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.rollno"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.registrationnumber"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.schemeno"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.internalexaminationtype"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.subjectcodes"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.subjectnames"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.classheldpersubject"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.classattendedpersubject"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.attendance.percentagepersubject"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.totalaveragepercentage"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.totalclassheld"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.totalclassattended"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.minimummarkpersubject"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.maximummarkspersubject"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.markobtained.eachsubject"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.statuspersubject"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.totalmarkobtained"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.totalofminimum"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.totalofmaximum"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.totalstatus"/></td>
	                								</tr>
	                							</table>
			                				</td>
			                			</tr>
			                			<tr>
			                				<td height="25" class="row-odd">Attendance SHORTAGE TEMPLATE1</td>
			                				<td height="25" class="row-even">
	                							<table>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.fatherName"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.name"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.courseName"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.studentPermanentAddress"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.pincode"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.username"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.password"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.template.registerno"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.template.percentage"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.date"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.logo"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.signature"/></td>
	                								</tr>
	                							</table>
			                				</td>
			                			</tr>
			                			<tr>
			                				<td height="25" class="row-odd">Address Certificate</td>
			                				<td height="25" class="row-even">
	                							<table>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.date"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.name"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.studentPermanentAddress"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.studentCurrentAddress"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.address.academicYear"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.character.class"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.character.course"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.character.stream"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.template.dob.registerno"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.template.examregisterno"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.gender.he.small"/></td>
	                								</tr>
	                							</table>
			                				</td>
			                			</tr>
			                			<tr>
			                				<td height="25" class="row-odd">Date Of Birth</td>
			                				<td height="25" class="row-even">
	                							<table>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.date"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.name"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.academicYear"/></td>
	                								</tr>
	                								
	                								<tr>
	                									<td><bean:message key="knowledgepro.template.dob.registerno"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.template.examregisterno"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.dob"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.gender"/></td>
	                								</tr>
	                							</table>
			                				</td>
			                			</tr>
			                			<tr>
			                				<td height="25" class="row-odd">Character Certificate</td>
			                				<td height="25" class="row-even">
	                							<table>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.date"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.name"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.character.stream"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.character.class"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.character.course"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.character.sem"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.character.semyear"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.dob.firstyear"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.dob.lastyear"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.template.dob.registerno"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.template.examregisterno"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.character"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.gender.her"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.gender2"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.gender.he"/></td>
	                								</tr>
	                							</table>
			                				</td>
			                			</tr>
			                			<tr>
			                				<td height="25" class="row-odd">No Due</td>
			                				<td height="25" class="row-even">
	                							<table>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.date"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.name"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.fatherName"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.sonof"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.character.stream"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.character.class"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.character.course"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.character.semyear"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.template.dob.registerno"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.template.examregisterno"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.gender"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.gender2"/></td>
	                								</tr>
	                							</table>
			                				</td>
			                			</tr>
			                			<tr>
			                				<td height="25" class="row-odd">Visa Letter</td>
			                				<td height="25" class="row-even">
	                							<table>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.date"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.name"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.template.visa.passportno"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.gender"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.courseCode"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.studentPermanentAddress"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.studentCurrentAddress"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.applicationNo"/></td>
	                								</tr>
	                							</table>
			                				</td>
			                			</tr>
			                			<tr>
			                				<td height="25" class="row-odd">Attempt Certificate</td>
			                				<td height="25" class="row-even">
	                							<table>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.date"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.name"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.dob.firstyear"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.dob.lastyear"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.character.sem"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.template.dob.registerno"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.gender"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.template.examregisterno"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.template.examination.year"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.template.number.attempts"/></td>
	                								</tr>
	                							</table>
			                				</td>
			                			</tr>
			                			<tr>
			                				<td height="25" class="row-odd">Scholarship Details</td>
			                				<td height="25" class="row-even">
	                							<table>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.date"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.name"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.fatherName"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.sonof"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.character.stream"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.character.class"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.character.course"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.address.academicYear"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.template.dob.registerno"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.template.examregisterno"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.gender"/></td>
	                								</tr>
	                							</table>
			                				</td>
			                			</tr>
			                			<tr>
			                				<td height="25" class="row-odd">Medium Of Instruction</td>
			                				<td height="25" class="row-even">
	                							<table>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.date"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.name"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.sonof"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.fatherName"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.character.stream"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.character.class"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.address.academicYear"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.template.dob.registerno"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.template.examregisterno"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.gender"/></td>
	                								</tr>
	                							</table>
			                				</td>
			                			</tr>
			                			<tr>
			                				<td height="25" class="row-odd">Sports Template</td>
			                				<td height="25" class="row-even">
	                							<table>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.date"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.name"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.fatherName"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.sonof"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.academicYear"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.gender"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.sports.sportsnames"/></td>
	                								</tr>
	                							</table>
			                				</td>
			                			</tr>
			                			<tr>
			                				<td height="25" class="row-odd">Foreign NOC</td>
			                				<td height="25" class="row-even">
	                							<table>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.date"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.name"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.fatherName"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.sonof"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.start.date"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.end.date"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.reopen.date"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.gender2"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.gender1"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.gender.her"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.template.dob.registerno"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.template.examregisterno"/></td>
	                								</tr>
	                							</table>
			                				</td>
			                			</tr>
			                			<tr>
			                				<td height="25" class="row-odd">Tuition Fee</td>
			                				<td height="25" class="row-even">
	                							<table>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.date"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.name"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.character.stream"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.tuition.tuitionfee"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.tuition.fee.inwords"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.address.academicYear"/></td>
	                								</tr>
	                							</table>
			                				</td>
			                			</tr>
			                			<tr>
			                				<td height="25" class="row-odd">MARK TRANSCRIPT- I PU</td>
			                				<td height="25" class="row-even">
	                							<table>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.date"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.name"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.fatherName"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.character.class"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.template.dob.registerno"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.template.examregisterno"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.template.examination.year"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.course"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.marksheet"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.marksresult"/></td>
	                								</tr>
	                							</table>
			                				</td>
			                			</tr>
			                			<tr>
			                				<td height="25" class="row-odd">Address And Date Of Birth</td>
			                				<td height="25" class="row-even">
	                							<table>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.date"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.name"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.dob"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.studentPermanentAddress"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.studentCurrentAddress"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.address.academicYear"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.character.class"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.character.course"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.character.stream"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.template.dob.registerno"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.template.examregisterno"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.gender.her"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.gender2"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.gender.he"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.gender.he.small"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.fatherName"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.sonof"/></td>
	                								</tr>
	                							</table>
			                				</td>
			                			</tr>
			                			<tr>
			                				<td height="25" class="row-odd">MARK TRANSCRIPT - II PU</td>
			                				<td height="25" class="row-even">
	                							<table>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.date"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.name"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.fatherName"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.character.class"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.template.dob.registerno"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.template.examregisterno"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.template.examination.year"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.course"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.marksheet"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.marksresult"/></td>
	                								</tr>
	                							</table>
			                				</td>
			                			</tr>
			                			<tr>
			                				<td height="25" class="row-odd">MARK TRANSCRIPT - I and II PU</td>
			                				<td height="25" class="row-even">
	                							<table>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.date"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.name"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.fatherName"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.character.class"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.template.dob.registerno"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.template.examregisterno"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.template.examination.year"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.course"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.marksheet"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.marksresult"/></td>
	                								</tr>
	                							</table>
			                				</td>
			                			</tr>
			                			<tr>
			                				<td height="25" class="row-odd">Fee Details</td>
			                				<td height="25" class="row-even">
	                							<table>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.date"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.name"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.fatherName"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.sonof"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.gender1"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.character.stream"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.character.class"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.character.semyear"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.address.academicYear"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.total.fee.paid"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.fee.receiptno"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.fee.date"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.fee.group"/></td>
	                								</tr>
	                							</table>
			                				</td>
			                			</tr>
			                			<tr>
			                				<td height="25" class="row-odd">NCC</td>
			                				<td height="25" class="row-even">
	                							<table>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.date"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.name"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.fatherName"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.sonof"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.academicYear"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.gender1"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.ncc.ncccamps"/></td>
	                								</tr>
	                							</table>
			                				</td>
			                			</tr>
			                			<tr>
			                				<td height="25" class="row-odd">Mother Tongue</td>
			                				<td height="25" class="row-even">
	                							<table>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.date"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.name"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.fatherName"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.sonof"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.mothertongue.admittedclass"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.mothertongue.currentclass"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.mothertongue.admittedyear"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.mothertongue.currentyear"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.mothertongue.name"/></td>
	                								</tr>
	                							</table>
			                				</td>
			                			</tr>
			                			
			                			<tr>
			                				<td height="25" class="row-odd">Employee Application Submission Mail_Applicant</td>
			                				<td height="25" class="row-even">
	                							<table>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.applicantName"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.applicationNo"/></td>
	                								</tr>
	                							</table>
			                				</td>
			                			</tr>
			                			
			                			<tr>
			                				<td height="25" class="row-odd">Employee Application Submission Mail_Admin</td>
			                				<td height="25" class="row-even">
	                							<table>
	                								<tr>
	                									<td><bean:message key="knowledgepro.employee.department.name"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.applicationNo"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.employee.post.appl"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.employee.qualification.level.tag"/></td>
	                								</tr>
	                							</table>
			                				</td>
			                			</tr>
			                			<tr>
			                			    <td height="25" class="row-odd" >Application Received Mail</td>
			                			    <td height="25" class="row-even">
			                			       <table>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.template.course"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.template.applicationNo"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.template.applicantName"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.template.dob"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="group.template.helper.application.status"/></td>
        											</tr>			                			       
			                			       </table>
			                			    </td>
			                			</tr>
			                			<tr>
			                			    <td height="25" class="row-odd" >Submission Mail-Offline</td>
			                			    <td height="25" class="row-even">
			                			       <table>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.template.applicationNo"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.template.applicantName"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.template.dob"/></td>
        											</tr>
			                			       </table>
			                			    </td>
			                			</tr>
			                			<tr>
			                			    <td height="25" class="row-odd" >Revaluation / Re-totaling Mail</td>
			                			    <td height="25" class="row-even">
			                			       <table>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.template.course"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.template.registerno"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.template.studentname"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.template.sem.type"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.template.term.no"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.template.dd.no"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.template.dd.date"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.template.bank"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.template.branch"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.template.month.year"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.template.amount"/></td>
        											</tr>
			                			       </table>
			                			    </td>
			                			</tr>
			                			
			                			<tr>
			                			    <td height="25" class="row-odd" >Research and Publication Mail For Approver</td>
			                			    <td height="25" class="row-even">
			                			       <table>
        											<tr>
        											   <td><bean:message key="Knowledgepro.employee.template.name"/></td>
        											</tr>
        											
			                			       </table>
			                			    </td>
			                			</tr>
			                			
			                			<tr>
			                			    <td height="25" class="row-odd" >Research and Publication Mail For Employee</td>
			                			    <td height="25" class="row-even">
			                			       <table>
        											<tr>
        											   <td><bean:message key="Knowledgepro.employee.template.name"/></td>
        											</tr>
        											
			                			       </table>
			                			    </td>
			                			</tr>
			                			<tr>
			                			    <td height="25" class="row-odd" >Research and Publication Approved Mail For Employee</td>
			                			</tr>
			                			<tr>
			                			    <td height="25" class="row-odd" >Employee-Online Leave Application</td>
			                			    <td height="25" class="row-even">
			                			       <table>
        									   <tr>
        											   <td><bean:message key="Knowledgepro.employee.template.name"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="Knowledgepro.employee.template.employeeid"/></td>
        											</tr>
        											 <tr>
        											   <td><bean:message key="Knowledgepro.employee.template.employee.leaveType"/></td>
        									        </tr>
        											
			                			       </table>
			                			    </td>
			                			</tr>
			                			<tr>
			                			    <td height="25" class="row-odd" >Revaluation / Re-totaling Status Update Mail</td>
			                			    <td height="25" class="row-even">
			                			       <table>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.template.course"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.template.registerno"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.template.studentname"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.template.sem.type"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.template.term.no"/></td>
        											</tr>
			                			       </table>
			                			    </td>
			                			</tr>
			                			<tr>
			                			    <td height="25" class="row-odd" >Employee-Approve Leave</td>
			                			    <td height="25" class="row-even">
			                			       <table>
        											<tr>
        											   <td><bean:message key="Knowledgepro.employee.template.name"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="Knowledgepro.employee.template.employeeid"/></td>
        											</tr>
			                			       </table>
			                			    </td>
			                			</tr>
			                			<tr>
			                			    <td height="25" class="row-odd" >Employee-Return/Clarify Leave</td>
			                			    <td height="25" class="row-even">
			                			       <table>
        											<tr>
        											   <td><bean:message key="knowledgepro.employee.template.reason"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="Knowledgepro.employee.template.name"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="Knowledgepro.employee.template.employeeid"/></td>
        											</tr>
			                			       </table>
			                			    </td>
			                			</tr>
			                			<tr>
			                			    <td height="25" class="row-odd" >Employee-Authorization Leave</td>
			                			    <td height="25" class="row-even">
			                			       <table>
        											<tr>
        											   <td><bean:message key="Knowledgepro.employee.template.name"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="Knowledgepro.employee.template.employeeid"/></td>
        											</tr>
			                			       </table>
			                			    </td>
			                			</tr>
			                			<tr>
			                			    <td height="25" class="row-odd" >Employee-Authorization Return Leave</td>
			                			    <td height="25" class="row-even">
			                			       <table>
        											<tr>
        											   <td><bean:message key="knowledgepro.employee.template.reason"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="Knowledgepro.employee.template.name"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="Knowledgepro.employee.template.employeeid"/></td>
        											</tr>
			                			       </table>
			                			    </td>
			                			</tr>
			                			<tr>
			                			    <td height="25" class="row-odd" >Employee-Authorization Request Doc Leave</td>
			                			    <td height="25" class="row-even">
			                			       <table>
        											<tr>
        											   <td><bean:message key="knowledgepro.employee.template.reason"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="Knowledgepro.employee.template.name"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="Knowledgepro.employee.template.employeeid"/></td>
        											</tr>
			                			       </table>
			                			    </td>
			                			</tr>
			                			<tr>
			                			    <td height="25" class="row-odd" >Online payment Receipts</td>
			                			    <td height="25" class="row-even">
			                			       <table>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.template.studentname"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.template.registerno"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.template.date"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.template.fee.receiptno"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.template.amount"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.template.amount.inwords"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.template.logo"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.template.logo1"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.template.applicationFor"/></td>
        											</tr>
			                			       </table>
			                			    </td>
			                			</tr>
			                			<tr>
			                			    <td height="25" class="row-odd" >Online payment Acknowledgement Slip</td>
			                			    <td height="25" class="row-even">
			                			       <table>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.template.applicant.name"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.template.applicationNo"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.template.course"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.template.reference.number"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.template.bank.ref.number"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.template.bank.id"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.template.candidate.reference.number"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.template.email"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.template.transaction.date"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.template.transaction.amount"/></td>
        											</tr>
			                			       </table>
			                			    </td>
			                			</tr>
			                			<tr>
			                			    <td height="25" class="row-odd" >Certificate Request Rejected Mail for student</td>
			                			    <td height="25" class="row-even">
			                			       <table>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.template.certificateName"/></td>
        											</tr>
        											
        										</table>
			                			    </td>
			                			</tr>
			                			<tr>
			                			    <td height="25" class="row-odd" >Certificate Request Completed Mail for student</td>
			                			    <td height="25" class="row-even">
			                			       <table>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.template.certificateName"/></td>
        											</tr>
        											
        										</table>
			                			    </td>
			                			</tr>
			                			<tr>
			                			    <td height="25" class="row-odd" >Research and Publication Rejected Mail For Employee</td>
			                			    <td height="25" class="row-even">
			                			       <table>
        											<tr>
        											   <td><bean:message key="knowledgepro.employee.reject.reason"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="Knowledgepro.employee.template.name"/></td>
        											</tr>
        											
        										</table>
			                			    </td>
			                			</tr>
			                			<tr>
			                			    <td height="25" class="row-odd" >Online Application Remainder Mail</td>
			                			    <td height="25" class="row-even">
			                			       <table>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.template.applicant.name"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.template.dob"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.template.course"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.template.transaction.date"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.template.candidate.reference.number"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.template.category"/></td>
        											</tr>
			                			       </table>
			                			    </td>
			                			</tr>
			                			<tr>
			                			    <td height="25" class="row-odd" >Phd- Generate Guides Remuneration Advice</td>
			                			    <td height="25" class="row-even">
			                			       <table>
        										   <tr>
	                									<td><bean:message key="knowledgepro.template.registerno"/></td>
	                								</tr>
        											<tr>
	                									<td><bean:message key="knowledgepro.admin.template.name"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.courseName"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.template.phd.Guid"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.template.phd.totalAmount"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.template.phd.GeneratedNo"/></td>
	                								</tr>
	                								<tr>
        											   <td><bean:message key="knowledgepro.phd.template.generated.date"/></td>
        											</tr>
        										</table>
			                			    </td>
			                			</tr>
			                			<tr>
			                			    <td height="25" class="row-odd" >Phd- Document pending Reminder mails</td>
			                			    <td height="25" class="row-even">
			                			       <table>
			                			          <tr>
	                									<td><bean:message key="knowledgepro.admin.template.certificateName"/></td>
	                								</tr>
        										   <tr>
	                									<td><bean:message key="knowledgepro.template.registerno"/></td>
	                								</tr>
        											<tr>
	                									<td><bean:message key="knowledgepro.admin.template.name"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.batch"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.courseName"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.template.phd.SubmissionDate"/></td>
	                								</tr>
        										</table>
			                			    </td>
			                			</tr>
			                			<tr>
			                			    <td height="25" class="row-odd" >Phd- Document Pending Due mails</td>
			                			    <td height="25" class="row-even">
			                			       <table>
			                			          <tr>
	                									<td><bean:message key="knowledgepro.admin.template.certificateName"/></td>
	                								</tr>
        										   <tr>
	                									<td><bean:message key="knowledgepro.template.registerno"/></td>
	                								</tr>
        											<tr>
	                									<td><bean:message key="knowledgepro.admin.template.name"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.batch"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.courseName"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.template.phd.SubmissionDate"/></td>
	                								</tr>
        										</table>
			                			    </td>
			                			</tr>
			                			<tr>
			                			 <td height="25" class="row-odd" >Phd- Fee payment Due</td>
			                			    <td height="25" class="row-even">
			                			       <table>
        										   <tr>
	                									<td><bean:message key="knowledgepro.template.registerno"/></td>
	                								</tr>
        											<tr>
	                									<td><bean:message key="knowledgepro.admin.template.name"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.courseName"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.template.phd.Feepayment.for.AcademicYear"/></td>
	                								</tr>
        										</table>
			                			    </td>
			                			</tr>
			                			<tr>
			                			    <td height="25" class="row-odd" >Certificate Request Submit Success Mail for student</td>
			                			    <td height="25" class="row-even">
			                			       <table>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.template.certificateName"/></td>
        											</tr>
        											
        										</table>
			                			    </td>
			                			</tr>
			                			
			                			
			                			<tr>
			                			    <td height="25" class="row-odd" >Holistic/Indian Constitution Repeat Exam Application</td>
			                			    <td height="25" class="row-even">
			                			       <table>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.template.studentname"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.template.registerno"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.template.date"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.template.fee.receiptno"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.template.amount"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.template.amount.inwords"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.template.logo"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.template.logo1"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.template.applicationFor"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.template.Venue"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.template.Time"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.template.Exam.Date"/></td>
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
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.template.studentname"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.hostel.template.hostelname"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.hostel.template.roomtype"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.hostel.template.priority.number"/></td>
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
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.template.subjectdetails"/></td>
        											</tr>
        											
        										</table>
			                			    </td>
			                			</tr>
			                			<tr>
			                			   <td height="25" class="row-odd" >Auditorium Booking Events</td>
			                			    <td height="25" class="row-even">
			                			       <table>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.template.date"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.template.username"/></td>
        											</tr>
                                                     <tr>
        											   <td><bean:message key="knowledgepro.admin.template.auditorium.block"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.template.auditorium.venue"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.template.auditorium.fromTime"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.template.auditorium.toTime"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.template.mode"/></td>
        											</tr>
        										</table>
			                			    </td>
			                			</tr>
			                			<tr>
			                			    <td height="25" class="row-odd" >Auditorium Approving Events</td>
			                			    <td height="25" class="row-even">
			                			       <table>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.template.date"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.template.username"/></td>
        											</tr>
                                                     <tr>
        											   <td><bean:message key="knowledgepro.admin.template.auditorium.block"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.template.auditorium.venue"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.template.auditorium.fromTime"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.template.auditorium.toTime"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.admission.final.merit.list.upload.template"/></td>
        											</tr>
        											
        										</table>
			                			    </td>
			                			</tr>
			                			<tr>
			                			    <td height="25" class="row-odd" >News and Events Submitted</td>
			                			    <td height="25" class="row-even">
			                			       <table>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.template.date"/></td>
        											</tr>
        										</table>
			                			    </td>
			                			</tr>
			                			<tr>
			                			    <td height="25" class="row-odd" >News and Events Admin Notification mail</td>
			                			    <td height="25" class="row-even">
			                			       <table>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.template.date"/></td>
        											</tr>
        										</table>
			                			    </td>
			                			</tr>
			                			<tr>
			                			    <td height="25" class="row-odd" >Hostel Absent Mail For Student </td>
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
        											   <td><bean:message key="knowledgepro.admin.template.auditorium.block"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.hostel.absentees.unit"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.hostel.template.room"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.hostel.template.bed"/></td>
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
			                			    <td height="25" class="row-odd" >Hostel Absent Mail For Parent </td>
			                			    <td height="25" class="row-even">
			                			       <table>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.sms.template.registerNo"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.template.parentname"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.template.studentname"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.hostel.template.hostelname"/></td>
        											</tr>
                                                     <tr>
        											   <td><bean:message key="knowledgepro.admin.template.auditorium.block"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.hostel.absentees.unit"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.hostel.template.room"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.hostel.template.bed"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.hostel.template.absent.details"/></td>
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
			                			    <td height="25" class="row-odd" >News and Events Sender Notification mail</td>
			                			    <td height="25" class="row-even">
			                			       <table>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.template.date"/></td>
        											</tr>
        										</table>
			                			    </td>
			                			</tr>
			                			<tr>
			                			    <td height="25" class="row-odd" >Smart Card Lost/Correction Mail</td>
			                			    <td height="25" class="row-even">
			                			       <table>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.template.name"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.template.number"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.template.date"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.smartcard.lostcorrection.processed.template"/></td>
        											</tr>
        										</table>
			                			    </td>
			                			</tr>
			                			<tr>
			                			    <td height="25" class="row-odd" >Exam-Question Paper Checking Alert Mail To Teachers</td>
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
			                			    <td height="25" class="row-odd" >SAP Registration Admin Notification mail</td>
			                			    <td height="25" class="row-even">
			                			       <table>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.hostel.leave.name"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.sms.template.registerNo"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.template.character.class"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.student.university.email"/></td>
        											</tr>
        										</table>
			                			    </td>
			                			</tr>
			                			<tr>
			                			    <td height="25" class="row-odd" >SAP Keys For Student mail</td>
			                			    <td height="25" class="row-even">
			                			       <table>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.sms.template.registerNo"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.template.studentname"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.admin.sap.template.key"/></td>
        											</tr>
        										</table>
			                			    </td>
			                			</tr>
			                			<tr>
			                			    <td height="25" class="row-odd" >Support Request Status Mail For Staff</td>
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
			                			    <td height="25" class="row-odd" >Support Request Status Mail For Student</td>
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
			                			    <td height="25" class="row-odd" >Support Request Escalation2</td>
			                			    <td height="25" class="row-even">
			                			       <table>
        											<tr>
        											   <td><bean:message key="knowledgepro.template.support.request.NoOfOpen.Issue"/></td>
        											</tr>
        										</table>
			                			    </td>
			                			</tr>
			                			<tr>
			                			    <td height="25" class="row-odd" >Support Request Submission Email</td>
			                			    <td height="25" class="row-even">
			                			       <table>
        											<tr>
        											   <td><bean:message key="knowledgepro.template.phd.SubmissionDate"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.admission.interview.requestId.upload.template"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.supportrequest.mail.description"/></td>
        											</tr>
        										</table>
			                			    </td>
			                			</tr>
			                			<tr>
			                			    <td height="25" class="row-odd" >Support Request Escalation1</td>
			                			    <td height="25" class="row-even">
			                			       <table>
        											<tr>
        											   <td><bean:message key="knowledgepro.template.support.request.NoOfOpen.Issue"/></td>
        											</tr>
        										</table>
			                			    </td>
			                			</tr>
			                			<tr>
			                			    <td height="25" class="row-odd" >Online payment confirmation-Billdesk</td>
			                			    <td height="25" class="row-even">
			                			       <table>
        											<tr>
        											   <td><bean:message key="knowledgepro.template.online.payment.conformation.cadateName"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.template.online.payment.conformation.appliedcourse"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.template.online.payment.conformation.dob"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.template.online.payment.conformation.category"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.template.online.payment.conformation.mbileNo"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.template.online.payment.conformation.email"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.template.online.payment.candidate.ref.number"/></td>
        											</tr>
        											<tr>
        											   <td><bean:message key="knowledgepro.template.online.payment.tnx.ref.number"/></td>
        											</tr>
        										</table>
			                			    </td>
			                			</tr>
			                			<tr>
			                			    <td height="25" class="row-odd" >Mail For Repeat Exam Application Submit</td>
			                			    <td height="25" class="row-even">
			                			       <table>
        											<tr>
        											   <td><bean:message key="knowledgepro.template.dob.registerno"/></td>
        											</tr>
        											<tr>
	                									<td><bean:message key="knowledgepro.admin.template.subjectnamess"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.studentname"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.character.class"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.feesdate"/></td>
	                								</tr>
        										</table>
			                			    </td>
			                			</tr>
			                			<tr>
			                			    <td height="25" class="row-odd" >Mail For Repeat Mid Sem Exam Application Approved</td>
			                			    <td height="25" class="row-even">
			                			       <table>
        											<tr>
        											   <td><bean:message key="knowledgepro.template.dob.registerno"/></td>
        											</tr>
        											<tr>
	                									<td><bean:message key="knowledgepro.admin.template.subjectnamess"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.studentname"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.character.class"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.feesdate"/></td>
	                								</tr>
        										</table>
			                			    </td>
			                			</tr>
			                				<tr>
			                			    <td height="25" class="row-odd" >Reminder Mail For Mid Semester Repeat Exam Fee Payment</td>
			                			    <td height="25" class="row-even">
			                			       <table>
        											<tr>
        											   <td><bean:message key="knowledgepro.template.dob.registerno"/></td>
        											</tr>
        											<tr>
	                									<td><bean:message key="knowledgepro.admin.template.subjectnamess"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.studentname"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.character.class"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.feesdate"/></td>
	                								</tr>
        										</table>
			                			    </td>
			                			</tr>
			                			<tr>
			                			    <td height="25" class="row-odd" >Mail For Mid Semester Repeat Exam Fee Exemption</td>
			                			    <td height="25" class="row-even">
			                			       <table>
        											<tr>
        											   <td><bean:message key="knowledgepro.template.dob.registerno"/></td>
        											</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.subjectnamess"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.studentname"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.character.class"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.feesdate"/></td>
	                								</tr>
	                								
        										</table>
			                			    </td>
			                			</tr>
			                			<tr>
			                			    <td height="25" class="row-odd" >Mail For Repeat Mid Sem Exam Application Rejected</td>
			                			    <td height="25" class="row-even">
			                			       <table>
        											<tr>
        											   <td><bean:message key="knowledgepro.template.dob.registerno"/></td>
        											</tr>
        											<tr>
	                									<td><bean:message key="knowledgepro.admin.template.subjectnamess"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.studentname"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.character.class"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.admin.template.feesdate"/></td>
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
			                			    <td height="25" class="row-odd" >Employee Add Notification</td>
			                			    <td height="25" class="row-even">
			                			       <table>
        											<tr>
        											   <td><bean:message key="knowledgepro.employee.notification.add.type"/></td>
        											</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.employee.notification.add.name"/></td>
	                								</tr>
	                								<tr>
	                									<td><bean:message key="knowledgepro.employee.notification.add.department"/></td>
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
        											   <td><bean:message key="knowledgepro.admin.hostel.template.room.type"/></td>
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