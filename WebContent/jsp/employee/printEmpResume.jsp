	<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	    pageEncoding="ISO-8859-1"%>
	<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
	<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
	<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic" %>
	<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
	<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean" %>
	<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms" %>
	<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
	<%@page import="com.kp.cms.constants.CMSConstants"%>
	<html>
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link href="css/styles.css" rel="stylesheet" type="text/css">
	<style type="text/css">
			
			</style>
	<script type="text/javascript" src="js/jquery.js">
		</script>
	<script type="text/javascript">
	
	function printPass() {	
		document.getElementById("hideButton").innerHTML="<td></td>";
		window.print();
	}
	
	</script>
	</head>
	<body>
	<html:form action="/DownLoadEmployeeResume">
	<html:hidden property="method" styleId="method" value=""/>
	<html:hidden property="formName" value="downloadEmployeeResumeForm"/>
	<html:hidden property="pageType" value="3"/>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
				<td colspan="3"  height="25" class="row-white"><div align="center">
				<img src='<%=CMSConstants.LOGO_URL%>' alt="Logo not available" align="middle"/>
				<img src='<%=request.getContextPath()%>/PhotoServlet' height="150" width="150" align="right"/> </div></td>
		</tr>
		<tr>
				<td align="left" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<strong>Application Form For 
				<logic:notEmpty name="downloadEmployeeResumeForm" property="tos">
				<nested:iterate name="downloadEmployeeResumeForm" property="tos">
				<nested:write property="postAppliedFor"/></nested:iterate></logic:notEmpty> Staff
				<div align="right">Application No:<nested:write name="downloadEmployeeResumeForm" property="applicationNo"/></div></strong></td>
				</tr>
		<tr>
			<td valign="top">
			
				<table style='border:1px solid #000000' rules='all' width="100%"  >
				<logic:notEmpty name="downloadEmployeeResumeForm" property="tos">
				<nested:iterate name="downloadEmployeeResumeForm" property="tos" id="to">
				<tr>
	                <td width="43%">Post Applied For</td><td align="left"><nested:write property="postAppliedFor"/></td>
	           </tr>
	           <tr>
	           		 <c:choose>
						<c:when test="${!downloadEmployeeResumeForm.isCjc}">
	           		        <td>Department</td><td align="left"><nested:write property="departmentName"/></td>
	           		    </c:when>
	           		    <c:otherwise>
	           		        <td>Subject</td><td align="left"><nested:write property="empSubject"/></td>
	           		    </c:otherwise>
	           		 </c:choose>
	           </tr>
	           <tr>
	                <td>Job Code</td><td align="left"> <nested:write property="jobCode"/></td>
	           </tr>
	           <tr>
					<td>Name & Current Address:</td><td align="left"><nested:write property="employeeName"/><BR/>
					 <logic:notEmpty name="to" property="currentAddress1"><nested:write property="currentAddress1"/><BR/></logic:notEmpty>
				<logic:notEmpty name="to" property="currentAddress2"><nested:write property="currentAddress2"/><BR/></logic:notEmpty>
				<logic:notEmpty name="to" property="currentAddress3"><nested:write property="currentAddress3"/></logic:notEmpty>
					 </td>
				</tr>
				 <!--<tr>
	                <td>Current Location</td><td align="left"><nested:write property="currentLocation"/></td>
				</tr>
				--><tr>
	                <td>Nationality</td><td align="left"><nested:write property="nationality"/></td>
				</tr>
				<tr>
				<td  >Gender</td><td align="left"><nested:write property="gender"/></td>
				</tr>
				<tr>
				<td >Marital Status</td><td align="left"><nested:write property="marital"/></td>
				</tr>
				<tr>
				<td >Date of Birth</td><td align="left"><nested:write property="dateOfBirth"/></td>
				</tr>
				<tr>
				<td >Religion</td><td align="left"><nested:write property="religion"/></td>
				</tr>
				<tr>
				<td >Blood Group</td><td align="left"><nested:write property="bloodGroup"/></td>
				</tr>
				<tr>
				<td >Email</td><td align="left"><nested:write property="email"/></td>
				</tr>
				<tr>
				<td >Reservation Category</td><td align="left"><nested:write property="reservationCategory"/></td>
				</tr>
				<tr>
				<td >Contact Number</td><td align="left"><nested:write property="contactNumber"/></td>
				</tr>
				<tr>
				<td >Mobile Number</td><td align="left"><nested:write property="mobileNumber"/></td>
				</tr>
				<tr>
				<td >Currently Working</td><td align="left"><nested:write property="workStatus"/></td>
				</tr>
				<tr>
				<td  >Designation</td><td align="left"><nested:write property="designationName"/></td>
				</tr>
				<!--<tr>
				<td  >Organization</td><td align="left"><nested:write property="designationName"/></td>
				</tr>
				--><tr>
				<td >Permanent Address:</td><td align="left">
				<logic:notEmpty name="to" property="addressLine1"><nested:write property="addressLine1"/><BR/></logic:notEmpty> 
					 <logic:notEmpty name="to" property="addressLine2"><nested:write property="addressLine2"/><BR/></logic:notEmpty> 
					 <logic:notEmpty  name="to" property="addressLine3"><nested:write property="addressLine3"/></logic:notEmpty></td>
				</tr>
				<tr>
				<td>Date of Application</td><td align="left"><nested:write property="dateOfApplication"/></td>
				</tr>
				</nested:iterate>
				
				</logic:notEmpty>
				</table>
				
			</td>
		</tr>
		<tr height="25px"> <td><STRONG><p>Experience Details</p></STRONG></td></tr>
		<tr>
			<td>
				<table style='border:1px solid #000000' rules='all' width="100%">
				<tr> 
					<td colspan="4"><u><b>1) Teaching Experience Details</b></u></td>
					
				</tr>
				<tr> 
					<td width="25%"> Teaching Experience</td>
					<logic:notEmpty name="downloadEmployeeResumeForm" property="tos"> 
						<nested:iterate name="downloadEmployeeResumeForm" property="tos" id="exp"> 
							<td><nested:write property="totalTeachingExperience" name="exp"></nested:write></td>
							
						</nested:iterate>
						<logic:notEmpty name="downloadEmployeeResumeForm" property="teachingExperience">
						<td></td>
							<td></td>
							<td></td>
							<td></td>
							</logic:notEmpty>
					</logic:notEmpty>
				</tr>
				<tr>
					<td width="25%">Functional Area</td>
					<logic:notEmpty name="downloadEmployeeResumeForm" property="tos"> 
						<nested:iterate name="downloadEmployeeResumeForm" property="tos" id="TeachingfuncArea"> 
							<td><nested:write property="subjectArea" name="TeachingfuncArea"></nested:write></td>
							
						</nested:iterate>
						<logic:notEmpty name="downloadEmployeeResumeForm" property="teachingExperience">
						<td></td>
							<td></td>
							<td></td>
							<td></td>
							</logic:notEmpty>
					</logic:notEmpty>
				</tr>
				<logic:notEmpty name="downloadEmployeeResumeForm" property="teachingExperience">
				
				
				<tr>
				<td width="20%" align="center"><STRONG>Designation </STRONG></td>
				<td width="20%" align="center"><STRONG>Institution</STRONG></td>
				<td width="15%" align="center"><STRONG>From Date</STRONG></td>
				<td width="15%" align="center"><STRONG>To Date</STRONG></td>
				<td width="15%" align="center"><STRONG>Experience Year(s)</STRONG></td>
				<td width="15%" align="center"><STRONG>Experience Month(s)</STRONG></td>
				</tr>
			
				
				<nested:iterate name="downloadEmployeeResumeForm" property="teachingExperience" id="teaching">
					<tr>
						<td align="center"><nested:write property="empDesignation" name="teaching"></nested:write></td>
						<td align="center"><nested:write property="empOrganization" name="teaching"/></td>
						<td align="center"><nested:write property="fromDate" name="teaching"/></td>
						<td align="center"><nested:write property="toDate" name="teaching"/></td>
						<td align="center"><nested:write property="teachingExpYears" name="teaching"/>&nbsp;Year(s)</td>
						<td align="center"><nested:write property="teachingExpMonths" name="teaching"/>&nbsp;Month(s)</td>
					</tr>
				</nested:iterate>
				</logic:notEmpty>
				
				
				</table>
			</td>
		</tr>
		<tr height="5px"></tr>
		<tr>
			<td>
				<table style='border:1px solid #000000' rules='all' width="100%">
				<tr> 
					<td colspan="4"><u><b>2) Industry Experience Details</b></u></td>
					
				</tr>
				<tr> 
					<td width="25%"> Industry Experience</td>
					<logic:notEmpty name="downloadEmployeeResumeForm" property="tos"> 
						<nested:iterate name="downloadEmployeeResumeForm" property="tos" id="exp"> 
							<td><nested:write property="industryExperience" name="exp"></nested:write></td>
							
						</nested:iterate>
						<logic:notEmpty name="downloadEmployeeResumeForm" property="industryExperience">
						<td></td>
							<td></td>
							<td></td>
							<td></td>
							</logic:notEmpty>
					</logic:notEmpty>
				</tr>
				<tr> 
					<td width="25%">Functional Area</td>
					<logic:notEmpty name="downloadEmployeeResumeForm" property="tos"> 
						<nested:iterate name="downloadEmployeeResumeForm" property="tos" id="indFuncArea"> 
							<td><nested:write property="industryFunctionalArea" name="indFuncArea"></nested:write></td>
							
						</nested:iterate>
						<logic:notEmpty name="downloadEmployeeResumeForm" property="industryExperience">
						<td></td>
							<td></td>
							<td></td>
							<td></td>
							</logic:notEmpty>
					</logic:notEmpty>
				</tr>
				<logic:notEmpty name="downloadEmployeeResumeForm" property="industryExperience">
				
				
				<tr>
				<td width="20%" align="center"><STRONG>Designation</STRONG> </td>
				<td width="20%" align="center"><STRONG>Institution</STRONG></td>
				<td width="15%" align="center"><STRONG>From Date</STRONG></td>
				<td width="15%" align="center"><STRONG>To Date</STRONG></td>
				<td width="15%" align="center"><STRONG>Experience Year(s)</STRONG></td>
				<td width="15%" align="center"><STRONG>Experience Month(s)</STRONG></td>
				</tr>
				
				
				<nested:iterate name="downloadEmployeeResumeForm" property="industryExperience" id="industry">
				<tr>
					<td align="center"><nested:write property="designation" name="industry"></nested:write></td>
					<td align="center"><nested:write property="organization" name="industry"/></td>
					<td align="center"><nested:write property="fromDate" name="industry"/></td>
					<td align="center"><nested:write property="toDate" name="industry"/></td>
					<td align="center"><nested:write property="industryExpYears" name="industry"></nested:write>&nbsp;Year(s)</td>
					<td align="center"><nested:write property="industryExpMonths" name="industry"></nested:write>&nbsp;Month(s)</td>
				</tr>
				</nested:iterate>
				</logic:notEmpty>
				
				</table>
			</td>
		</tr>
		<tr height="5px"></tr>
		<tr>
			<td>
				<table style='border:1px solid #000000' rules='all' width="100%">
				
				<logic:notEmpty name="downloadEmployeeResumeForm" property="tos">
				<nested:iterate name="downloadEmployeeResumeForm" property="tos" id="to">
				<tr> 
					<td width="43%"> Total Experience</td>
					<td><nested:write property="totalExp" name="to"></nested:write></td>
				</tr>
				<tr> 
					<td>Qualification Level</td>
					<td><nested:write property="qualificationLevel" name="to"/></td>
				</tr>
				<tr>
					<td>Job Type </td>
					<td><nested:write property="jobType" name="to"/></td>
				</tr>
				<tr>
					<td >Employment Status</td>
					<td><nested:write property="empStatus" name="to"></nested:write></td>
				</tr>
				<tr>
					<td>Expected Salary</td>
					<td><nested:write property="expectedSalary" name="to"/></td>
				</tr>
				<tr>
					<td>Eligibility Test</td>
					<td><nested:write property="eligibilityTest" name="to"/></td>
				</tr>
			
				</nested:iterate>
				</logic:notEmpty>
				
				
				</table>
			</td>
		</tr>
				 <tr>
			<td><STRONG><p>Educational Qualification</p></STRONG>
				<table style='border:1px solid #000000' rules='all' width="100%">
				
				<tr>
				<td ><STRONG>Course</STRONG></td>
				<td><STRONG>Specialization</STRONG></td>
				<td><STRONG>Year Of Completion</STRONG></td>
				<td><STRONG>Grade %</STRONG></td>
				<td><STRONG>Institute/University</STRONG></td>
				</tr>
				
				<logic:notEmpty name="downloadEmployeeResumeForm" property="empEducationalDetails">
				<nested:iterate name="downloadEmployeeResumeForm" property="empEducationalDetails" id="educationalDetails">
				<tr>
				<td><nested:write property="course" name="educationalDetails"></nested:write></td>
				<td><nested:write property="specialization" name="educationalDetails"/></td>
				<td><nested:write property="yearOfCompletion" name="educationalDetails"/></td>
				<td><nested:write property="grade" name="educationalDetails"/></td>
				<td><nested:write property="institute" name="educationalDetails"/></td>
				</tr>
				</nested:iterate>
				</logic:notEmpty>
				
				
				</table>
			</td>
		</tr>
		 <tr>
		 	<logic:notEmpty name="downloadEmployeeResumeForm" property="additionalQualification">
			<td><STRONG><p>Additional Qualification</p></STRONG>
				<table style='border:1px solid #000000' rules='all' width="100%">
				
				<tr>
				<td ><STRONG>Course</STRONG></td>
				<td><STRONG>Specialization</STRONG></td>
				<td><STRONG>Year Of Completion</STRONG></td>
				<td><STRONG>Grade %</STRONG></td>
				<td><STRONG>Institute/University</STRONG></td>
				</tr>
				
				<nested:iterate name="downloadEmployeeResumeForm" property="additionalQualification" id="educationalDetails">
				<tr>
				<td><nested:write property="course" name="educationalDetails"></nested:write></td>
				<td><nested:write property="specialization" name="educationalDetails"/></td>
				<td><nested:write property="yearOfCompletion" name="educationalDetails"/></td>
				<td><nested:write property="grade" name="educationalDetails"/></td>
				<td><nested:write property="institute" name="educationalDetails"/></td>
				</tr>
				</nested:iterate>
				</table>
			</td></logic:notEmpty>
		</tr>
		
		<%--  <tr>
		 <logic:equal value="true" name="downloadEmployeeResumeForm" property="publicationDetailsPresent">
		 <logic:notEmpty name="downloadEmployeeResumeForm" property="tos">
			<nested:iterate name="downloadEmployeeResumeForm" property="tos" id="publications">
			<td><STRONG><p>No. of Publications</p></STRONG>
				<table style='border:1px solid #000000' rules='all' width="100%">
				
				<tr>
				<td>Refereed</td>
				<td>Non-Refereed</td>
				<td>Books</td>
				</tr>
				<tr>
				
				<td><nested:write property="noOfPublicationsRefered" name="publications"></nested:write></td>
				<td><nested:write property="noOfPublicationsNotRefered" name="publications"/></td>
				<td><nested:write property="books" name="publications"/></td>
				</tr>
				</table>
			</td>
			</nested:iterate>
			</logic:notEmpty>
			</logic:equal>
		</tr> --%>
		<tr>
		<td height="15"></td>
		</tr>
		</table>
		<logic:equal value="false" name="downloadEmployeeResumeForm" property="isCjc">
		<logic:notEmpty name="downloadEmployeeResumeForm" property="tos">
			<nested:iterate name="downloadEmployeeResumeForm" property="tos" id="research">
			<STRONG>Research Contribution</STRONG><br><br>
		<strong><font size="2">Research Papers(Journals)</font></strong><br>
				Refereed: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				  <input type="text" value="<nested:write property="researchPapersRefereed" name="research"></nested:write>" name="downloadEmployeeResumeForm" style="height: 20px;width: 50px; color: black;text-align: center;border:1px solid black;" disabled="disabled"/>
				  &nbsp;&nbsp;&nbsp;
				Non-refereed:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				  <input type="text" value="<nested:write property="researchPapersNonRefereed" name="research"/>" name="downloadEmployeeResumeForm" style="height: 20px;width: 50px;color: black;text-align: center;border:1px solid black;" disabled="disabled"/>
				  &nbsp;&nbsp;&nbsp;
				  Proceedings:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				  <input type="text" value="<nested:write property="researchPapersProceedings" name="research"/>" name="downloadEmployeeResumeForm" style="height: 20px;width: 50px;color: black;text-align: center;border:1px solid black;" disabled="disabled"/>
				  
				  <br><br>
				  <strong><font size="2">Book Publications</font></strong><br>
				
				  International:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				  <input type="text" value="<nested:write property="internationalBookPublications" name="research"/>" name="downloadEmployeeResumeForm" style="height: 20px;width: 50px;color: black;text-align: center;border:1px solid black;" disabled="disabled"/>
				  &nbsp;&nbsp;&nbsp;
				  National:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				  <input type="text" value="<nested:write property="nationalBookPublications" name="research"/>" name="downloadEmployeeResumeForm" style="height: 20px;width: 50px;color: black;text-align: center;border:1px solid black;" disabled="disabled"/>
				  &nbsp;&nbsp;&nbsp;
				  Local:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				  <input type="text" value="<nested:write property="localBookPublications" name="research"/>" name="downloadEmployeeResumeForm" style="height: 20px;width: 50px;color: black;text-align: center;border:1px solid black;" disabled="disabled" align="top"/>
				  <br><br>
				 <strong><font size="2">Chapters in edited books</font></strong><br>
				International:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				  <input type="text" value="<nested:write property="chaptersEditedBooksInternational" name="research"/>" name="downloadEmployeeResumeForm" style="height: 20px;width: 50px;color: black;text-align: center;border:1px solid black;" disabled="disabled"/>
				  &nbsp;&nbsp;&nbsp;
				  National:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				  <input type="text" value="<nested:write property="chaptersEditedBooksNational" name="research"/>" name="downloadEmployeeResumeForm" style="height: 20px;width: 50px;color: black;text-align: center;border:1px solid black;" disabled="disabled"/>
				  <br><br>
				  <strong><font size="2">Sponsored projects(completed)</font></strong><br>
				Major (>5L):&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				   <input type="text" value="<nested:write property="majorSponseredProjects" name="research"/>" name="downloadEmployeeResumeForm" style="height: 20px;width: 50px;color: black;text-align: center;border:1px solid black;" disabled="disabled"/>
				   &nbsp;&nbsp;&nbsp;
				   Minor (<5L):&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				   <input type="text" value="<nested:write property="minorSponseredProjects" name="research"/>" name="downloadEmployeeResumeForm" style="height: 20px;width: 50px;color: black;text-align: center;border:1px solid black;" disabled="disabled"/>
				   &nbsp;&nbsp;&nbsp;
				    Consultancy (>10L):&nbsp;&nbsp;&nbsp;&nbsp;
				    <input type="text" value="<nested:write property="consultancy1SponseredProjects" name="research"></nested:write>" name="downloadEmployeeResumeForm" style="height: 20px;width: 50px;color: black;text-align: center;border:1px solid black;" disabled="disabled"/>
				    &nbsp;&nbsp;&nbsp;   <br><br>
				     Consultancy (>2L):&nbsp;
				     <input type="text" value="<nested:write property="consultancy2SponseredProjects" name="research"/>" name="downloadEmployeeResumeForm" style="height: 20px;width: 50px;color: black;text-align: center;border:1px solid black;" disabled="disabled"/>
				     <br><br>
				     <strong><font size="2">Research Guidance(Awarded)</font></strong><br>
				PhD:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				
				  <input type="text" value="<nested:write property="phdResearchGuidance" name="research"/>" name="downloadEmployeeResumeForm" style="height: 20px;width: 50px;color: black;text-align: center;border:1px solid black;" disabled="disabled"/>
				  &nbsp;&nbsp;&nbsp;
				  Mphil:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				  <input type="text" value="<nested:write property="mphilResearchGuidance" name="research"/>" name="downloadEmployeeResumeForm" style="height: 20px;width: 50px;color: black;text-align: center;border:1px solid black;" disabled="disabled"/>
				  <br><br>
				  <strong><font size="2">Training attended</font></strong><br>
				FDP (>2 weeks):&nbsp;&nbsp;&nbsp;&nbsp;
				  <input type="text" value="<nested:write property="trainingAttendedFdp2Weeks" name="research"/>" name="downloadEmployeeResumeForm" style="height: 20px;width: 50px;color: black;text-align: center;border:1px solid black;" disabled="disabled"/>
				  &nbsp;&nbsp;&nbsp;
				  FDP (>1 week):&nbsp;&nbsp;&nbsp;&nbsp;
				  <input type="text" value="<nested:write property="trainingAttendedFdp1Weeks" name="research"/>" name="downloadEmployeeResumeForm" style="height: 20px;width: 50px;color: black;text-align: center;border:1px solid black;" disabled="disabled"/>
				  <br><br>
				  <strong><font size="2">Conference presentation</font></strong><br>
				International:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				  <input type="text" value="<nested:write property="internationalConferencePresentaion" name="research"/>" name="downloadEmployeeResumeForm" style="height: 20px;width: 50px;color: black;text-align: center;border:1px solid black;" disabled="disabled"/>
				  &nbsp;&nbsp;&nbsp;
				 National:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				   <input type="text" value="<nested:write property="nationalConferencePresentaion" name="research"/>" name="downloadEmployeeResumeForm" style="height: 20px;width: 50px;color: black;text-align: center;border:1px solid black;" disabled="disabled"/>
				   &nbsp;&nbsp;&nbsp;
				 Local:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				  <input type="text" value="<nested:write property="localConferencePresentaion" name="research"/>" name="downloadEmployeeResumeForm" style="height: 20px;width: 50px;color: black;text-align: center;border:1px solid black;" disabled="disabled"/>
				  &nbsp;&nbsp;&nbsp; <br><br>
				  Regional:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				  <input type="text" value="<nested:write property="regionalConferencePresentaion" name="research"/>" name="downloadEmployeeResumeForm" style="height: 20px;width: 50px;color: black;text-align: center;border:1px solid black;" disabled="disabled"/>
			</nested:iterate></logic:notEmpty></logic:equal>
			<table  width="100%">
			<tr height="5px">
		
		</tr>
		<tr><logic:notEmpty name="downloadEmployeeResumeForm" property="tos">
			<nested:iterate name="downloadEmployeeResumeForm" property="tos" id="publications">
			<logic:notEmpty name="publications" property="otherInfo">
			<td><STRONG><p>Other Information</p></STRONG>
				<table style='border:1px solid #000000' rules='all' width="100%">
				<tr>
				<td><p><nested:write property="otherInfo" name="publications"></nested:write></p>
				</td>
				</tr>
				</table>
			</td></logic:notEmpty>
		</nested:iterate>
		</logic:notEmpty>
		</tr></table>
		<table  width="100%">
			<tr height="25">
			<td>
				
			</td>
			</tr>
			<tr height="25">
			<td align="center"><div id="hideButton">
				<html:button property="" styleClass="formbutton" value="Print" onclick="printPass()"></html:button></div>
			</td>
			</tr>
		</table>
	</html:form>
	
	
	</body>
</html>
	