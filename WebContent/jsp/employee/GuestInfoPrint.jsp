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
			input {
			border:0;
			}
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
	<html:form action="/GuestFaculty">
	<html:hidden property="method" styleId="method" value=""/>
	<html:hidden property="formName" value="GuestFacultyInfoForm"/>
	<html:hidden property="pageType" value="3"/>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
				<td colspan="3" class="row-white"><div align="center">
				<img src='<%=CMSConstants.LOGO_URL%>' alt="Logo not available" align="middle"/>
				<img src='<%=request.getContextPath()%>/PhotoServlet' height="186" width="144" align="right"/> </div></td>
		</tr>
		<tr>
				<td align="left" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<strong>GUEST FACULTY PROFILE & APPLICATION</strong>
				</td>
				</tr>
		<tr>
			<td valign="top">
			
				<table style='border:1px solid #000000' rules='all' width="100%"  >
				<logic:notEmpty name="GuestFacultyInfoForm" property="tos">
				<nested:iterate name="GuestFacultyInfoForm" property="tos" id="to">
				
	           <tr>
	                <td>Name</td><td align="left"> <nested:write property="firstName"/></td>
	           </tr>
	           <tr>
					<td>Current Address:</td><td >Permanent Address:</td>
				</tr>
				<tr>
				<td align="left">
			     <logic:notEmpty name="to" property="communicationAddressLine1"><nested:write property="communicationAddressLine1"/><BR/></logic:notEmpty>
				<logic:notEmpty name="to" property="communicationAddressLine2"><nested:write property="communicationAddressLine2"/><BR/></logic:notEmpty>
				<logic:notEmpty  name="to" property="communicationAddressCity"><nested:write property="communicationAddressCity"/></logic:notEmpty>,
				<logic:notEmpty  name="to" property="communicationStateName"><nested:write property="communicationStateName"/></logic:notEmpty>,
				<logic:notEmpty  name="to" property="communicationCountryName"><nested:write property="communicationCountryName"/></logic:notEmpty>.
				
				
				</td>
					
				<td align="left">
				<logic:notEmpty name="to" property="permanentAddressLine1"><nested:write property="permanentAddressLine1"/><BR/></logic:notEmpty> 
			    <logic:notEmpty name="to" property="permanentAddressLine2"><nested:write property="permanentAddressLine2"/><BR/></logic:notEmpty> 
			    <logic:notEmpty  name="to" property="permanentAddressCity"><nested:write property="permanentAddressCity"/></logic:notEmpty>,
			    <logic:notEmpty  name="to" property="permanentStateName"><nested:write property="permanentStateName"/></logic:notEmpty>,
			    <logic:notEmpty  name="to" property="permanentCountryName"><nested:write property="permanentCountryName"/></logic:notEmpty>.
			   
			    </td>
				</tr>
				
				<tr>
				<td >PAN Number</td><td align="left"><nested:write property="panNo"/></td>
				</tr>
				<tr>
	                <td>Nationality</td><td align="left"><nested:write property="nationalityName"/></td>
				</tr>
				<tr>
				<td  >Gender</td><td align="left"><nested:write property="gender"/></td>
				</tr>
				<tr>
				<td >Marital Status</td><td align="left"><nested:write property="maritalStatus"/></td>
				</tr>
				<tr>
				<td >Date of Birth</td><td align="left"><nested:write property="dob"/></td>
				</tr>
				<tr>
				<td >Religion</td><td align="left"><nested:write property="religion"/></td>
				</tr>
				<!-- <tr>
				<td >Blood Group</td><td align="left"><nested:write property="bloodGroup"/></td>
				</tr>-->
				<tr>
				<td >Email</td><td align="left"><nested:write property="email"/></td>
				</tr>
				<tr>
				<td >Reservation Category</td><td align="left"><nested:write property="reservationCatagory"/></td>
				</tr>
				<tr>
				<td >Contact Number</td><td align="left"><nested:write property="currentAddressHomeTelephone3"/></td>
				</tr>
				<tr>
				<td >Mobile Number</td><td align="left"><nested:write property="currentAddressMobile1"/></td>
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
					<td width="25%"><b>Teaching Experience</b></td>
					<logic:notEmpty name="GuestFacultyInfoForm" property="tos"> 
						<nested:iterate name="GuestFacultyInfoForm" property="tos" id="exp"> 
							<td width="25%"><nested:write property="totalTeachingExperience" name="exp"></nested:write></td>
							
						</nested:iterate>
					</logic:notEmpty>
				
					<td width="25%">Functional Area</td>
					<logic:notEmpty name="GuestFacultyInfoForm" property="tos"> 
						<nested:iterate name="GuestFacultyInfoForm" property="tos" id="TeachingfuncArea"> 
							<td width="25%"><nested:write property="subjectArea" name="TeachingfuncArea"></nested:write></td>
							</nested:iterate>
					</logic:notEmpty>
				</tr>
				<logic:notEmpty name="GuestFacultyInfoForm" property="teachingExperience">
				
				
				<tr>
				<td width="25%" align="center"><STRONG>Designation </STRONG></td>
				<td width="25%" align="center"><STRONG>Institution</STRONG></td>
				<td width="25%" align="center"><STRONG>Experience Year(s)</STRONG></td>
				<td width="25%" align="center"><STRONG>Experience Month(s)</STRONG></td>
				</tr>
			
				
				<nested:iterate name="GuestFacultyInfoForm" property="teachingExperience" id="teaching">
					<tr>
						<td align="center"><nested:write property="currentTeachnigDesignation" name="teaching"></nested:write></td>
						<td align="center"><nested:write property="currentOrganisation" name="teaching"/></td>
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
				<logic:notEmpty name="GuestFacultyInfoForm" property="tos"> 
				<tr> 
					<td width="25%"><b> Industry Experience</b></td>
						<nested:iterate name="GuestFacultyInfoForm" property="tos" id="exp"> 
							<td width="25%"><nested:write property="industryExperience" name="exp"></nested:write></td>
							
						</nested:iterate>
					
				
					<td width="25%">Functional Area</td>
						<nested:iterate name="GuestFacultyInfoForm" property="tos" id="indFuncArea"> 
							<td width="25%"><nested:write property="industryFunctionalArea" name="indFuncArea"></nested:write></td>
						</nested:iterate>
					
				</tr>
				</logic:notEmpty>
				
				<logic:notEmpty name="GuestFacultyInfoForm" property="industryExperience">
				
				<tr>
				<td width="25%" align="center"><STRONG>Designation</STRONG> </td>
				<td width="25%" align="center"><STRONG>Institution</STRONG></td>
				<td width="25%" align="center"><STRONG>Experience Year(s)</STRONG></td>
				<td width="25%" align="center"><STRONG>Experience Month(s)</STRONG></td>
				</tr>
				
				
				<nested:iterate name="GuestFacultyInfoForm" property="industryExperience" id="industry">
				<tr>
					<td align="center"><nested:write property="empDesignation" name="industry"></nested:write></td>
					<td align="center"><nested:write property="empOrganization" name="industry"/></td>
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
				
				<logic:notEmpty name="GuestFacultyInfoForm" property="tos">
				<nested:iterate name="GuestFacultyInfoForm" property="tos" id="to">
				<tr> 
					<td width="43%"> Total Experience</td>
					<td><nested:write property="totalExp" name="to"></nested:write></td>
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
				
				<logic:notEmpty name="GuestFacultyInfoForm" property="empEducationalDetails">
				<nested:iterate name="GuestFacultyInfoForm" property="empEducationalDetails" id="educationalDetails">
				<tr>
				<td><nested:write property="course" name="educationalDetails"></nested:write></td>
				<td><nested:write property="specialization" name="educationalDetails"/></td>
				<td><nested:write property="yearOfComp" name="educationalDetails"/></td>
				<td><nested:write property="grade" name="educationalDetails"/></td>
				<td><nested:write property="institute" name="educationalDetails"/></td>
				</tr>
				</nested:iterate>
				</logic:notEmpty>
				
				
				</table>
			</td>
		</tr>
		 <tr>
		 	<logic:notEmpty name="GuestFacultyInfoForm" property="additionalQualification">
			<td><STRONG><p>Additional Qualification</p></STRONG>
				<table style='border:1px solid #000000' rules='all' width="100%">
				
				<tr>
				<td ><STRONG>Course</STRONG></td>
				<td><STRONG>Specialization</STRONG></td>
				<td><STRONG>Year Of Completion</STRONG></td>
				<td><STRONG>Grade %</STRONG></td>
				<td><STRONG>Institute/University</STRONG></td>
				</tr>
				
				<nested:iterate name="GuestFacultyInfoForm" property="additionalQualification" id="educationalDetails">
				<tr>
				<td><nested:write property="course" name="educationalDetails"></nested:write></td>
				<td><nested:write property="specialization" name="educationalDetails"/></td>
				<td><nested:write property="yearOfComp" name="educationalDetails"/></td>
				<td><nested:write property="grade" name="educationalDetails"/></td>
				<td><nested:write property="institute" name="educationalDetails"/></td>
				</tr>
				</nested:iterate>
				</table>
			</td></logic:notEmpty>
		</tr>
			
		<tr>
			<td valign="top">
			
		<table style='border:1px solid #000000' rules='all' width="100%"  >
		<logic:notEmpty name="GuestFacultyInfoForm" property="tos">
				<nested:iterate name="GuestFacultyInfoForm" property="tos" id="to">
		<tr>
			<td colspan="4"><p>
			
		      I  am willing to be a Guest Faculty at Christ University in the Department of <nested:write property="departmentName"/>
              for the period  starting from <nested:write property="startDate"/> to <nested:write property="endDate"/> for the 
              Subject/specialization of <nested:write property="subjectSpecilization"/> . I will abide by and adhere 
              to the Rules and Regulations of the University during my association as Guest/Adjunct Faculty. 
              I will not have any objection from my Employers (if employed) and
              I will ensure that I will complete my full responsibility as a Guest Faculty including related evaluations.
              I am attaching my updated Resume along with copies of testimonials.
            
			</p>
              
			</td>
		</tr>
		<tr>
		<td width="25%">Date:</td><td width="25%"> &nbsp;</td>
		<td width="25%">Signature:</td><td width="25%"> &nbsp;</td>
		</tr>
		<tr>
		<td width="25%">Working Hours per Week:</td><td width="25%"><nested:write property="workingHoursPerWeek"/></td>
		<td width="25%">Honorarium per Hour:</td><td width="25%"><nested:write property="honorariumPerHours"/></td>
		</tr>
				
		<tr>
				<td ><STRONG>Referred By:</STRONG></td>
				<td><STRONG>Reviewed and Approved-HOD</STRONG></td>
				<td><STRONG>Approved-Director/Dean</STRONG></td>
				<td><STRONG>Personnel Officer</STRONG></td>
				
		</tr>
		<tr>
				<td><nested:write property="referredBy"/></td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				
		</tr>
		 </nested:iterate>
         </logic:notEmpty>
		</table>
		</td>
		</tr>
</table>
		
	
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
	