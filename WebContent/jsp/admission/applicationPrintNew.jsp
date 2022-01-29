<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@page import="com.kp.cms.constants.CMSConstants"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<style>
 .title{
 font-weight: bold;
 font-size: large;
 
 }
 .title2{
 font-weight: bold;
 font-size: medium;	
 }
 body{
 	color:black;
 	font-family: Arial;
	
 }
 
.mytable{
	border-collapse: collapse;
	
}
.algn{
   height:25px;

}
.smallCheck {
	font-weight: normal;
 	font-size: small;
}
p{
	font-weight: normal;
 	font-size: small;
 	text-justify: inter-word;
 	 text-align: justify;
}
.office{
	font-size: medium;
    line-height: 35px;
    font-weight: bold;
}
table tr{
	vertical-align: top;
}
</style>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<script language="JavaScript" src="js/admission/admissionform.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"></meta>
<html:form action="/uniqueIdRegistration" method="POST">
	<html:hidden property="method" styleId="method" />
	<html:hidden property="formName" value="uniqueIdRegistrationForm" />
	<input type="hidden" name="applicationId"
		value='<bean:write name="uniqueIdRegistrationForm" property="applicantDetails.applicationId" />' />
	<input type="hidden" name="courseId" value='<bean:write	name="uniqueIdRegistrationForm" property="applicantDetails.course.id" />' />	
	<%int i=1; %>
	<table  width="100%" border="0"  cellpadding="3" cellspacing="0" bordercolor="#E0DFDB">
	 
	<!--  <tr>
	 	<td align="right">
	 		<table border="1" class="mytable">
	 			<tr>
	 				<td class="smallCheck">GENERAL</td>
		 			<td class="smallCheck">SC</td>
		 			<td class="smallCheck">ST</td>
		 			<td class="smallCheck">PWD</td>
		 			<td class="smallCheck">SPORTS</td>
		 			<td class="smallCheck">CULTURAL</td>
		 			<td class="smallCheck">COMMUNITY</td>
		 			<td class="smallCheck">OBC</td>
		 			<td class="smallCheck">OEC</td>
	 			</tr>
	 		</table>	
	 	</td>
	 </tr> -->
	 
	 <tr>
	 	
		<td align="center">
		<%--<img width="800" height="150"  src='<%= request.getRequestURL().toString().replace(request.getRequestURI().substring(0), request.getContextPath()).concat("/LogoServlet") %>' alt="Logo not available">
		 --%>
		 <img width="800" height="150"  src='<%= request.getRequestURL().toString().replace(request.getRequestURI().substring(0), request.getContextPath()).concat("/LogoServlet") %>' alt="Logo not available">
		<!-- <img src="images/admission/images/headerlogo.png" width="600" height="150" align="top"/> -->
		  </td>
	</tr>
	<tr>
	  <td>
	    <table width="100%">
	      <tr>
	        <td width="50%">
	          <table >
	          <tr>
	            <td>Applied for</td><td>: </td><td>
	            <logic:equal value="1" name="uniqueIdRegistrationForm" property="applicantDetails.isCommuQuota">
	            Community Quota
	            </logic:equal>
	            <logic:equal value="1" name="uniqueIdRegistrationForm" property="applicantDetails.isCommuQuota">
	            <logic:equal value="1" name="uniqueIdRegistrationForm" property="applicantDetails.ismngQuota">
	            &
	            </logic:equal>
	            </logic:equal>
	            <logic:equal value="1" name="uniqueIdRegistrationForm" property="applicantDetails.ismngQuota">
	            Management Quota
	            </logic:equal>
	            </td>
	           </tr>
	           <tr>
	            <td>Appl. No</td><td>: </td><td><bean:write name="uniqueIdRegistrationForm" property="applicantDetails.applnNo"/></td>
	           </tr>
	           <tr>
	            <td>Id No</td><td>: </td><td><bean:write name="uniqueIdRegistrationForm" property="applicantDetails.journalNo"/></td>
	           </tr>
	           <tr>
	            <td>Date</td><td>: </td><td><bean:write name="uniqueIdRegistrationForm" property="applicantDetails.challanDate"/></td>
	           </tr>
	           <tr>
	            <td>Mode of payment</td><td>: </td><td>
	            <logic:equal value="Challan" name="uniqueIdRegistrationForm" property="applicantDetails.mode">
	     			<bean:write name="uniqueIdRegistrationForm" property="applicantDetails.mode" /></logic:equal>
	     
	    		 <logic:equal value="Online" name="uniqueIdRegistrationForm" property="applicantDetails.mode">
	    			 <logic:equal value="success" name="uniqueIdRegistrationForm" property="applicantDetails.onlinPaymentStatus">
	     				Online
	    			 </logic:equal>
	    		 </logic:equal>
	            </td>
	           </tr>
	           <tr>
	            <td>Index Mark</td><td>: </td><td></td>
	           </tr>
	          </table>
	        </td>
	        
	        <td width="50%">
	           <b>FOR OFFICE USE ONLY</b> 
	          <table width="100%" border="1" cellpadding="3" align="left" class="mytable">
	            <tr><td width="30%">Admission No.</td><td width="70%"></td></tr>
	            <tr><td width="30%">Programme</td><td width="70%"></td></tr>
	            <tr><td width="30%">Class No.</td><td width="70%"></td></tr>
	          </table>
	        </td>
	      </tr>
	    </table>
	  </td>
	</tr>
	
	<logic:equal value="1" name="uniqueIdRegistrationForm" property="applicantDetails.programType">
	<tr>
	  <td class="title" align="center">MANAGEMENT QUOTA/COMMUNITY QUOTA APPLICATION FOR ADMISSION TO UNDERGRADUATE PROGRAMME - <bean:write name="uniqueIdRegistrationForm" property="applicantDetails.applyYear"/><br></br></td>
	</tr>	
	</logic:equal>

	<logic:equal value="2" name="uniqueIdRegistrationForm" property="applicantDetails.programType">
	<tr>
	  <td class="title" align="center">MANAGEMENT QUOTA/COMMUNITY QUOTA APPLICATION FOR ADMISSION TO POSTGRADUATE PROGRAMME - <bean:write name="uniqueIdRegistrationForm" property="applicantDetails.applyYear"/><br></br></td>
	</tr>
	</logic:equal>
	
	    <tr><td><p></p></td></tr>
	
	      <tr>
	      <td>
	        <table border="0" style="line-height: 20px;">
	    <logic:equal value="2" name="uniqueIdRegistrationForm" property="applicantDetails.programType">    
	    <tr>
	     <td><%=i++ %>.</td><td class="algn">Programme applied for</td><td>: </td><td><logic:iterate id="preflist" name="uniqueIdRegistrationForm" property="applicantDetails.preflist"><bean:write name="preflist" property="coursName"/></logic:iterate></td>
	   </tr>
	   </logic:equal>   
	    
	   <tr>
	     <td><%=i++ %>.</td><td class="algn">Name</td><td>: </td><td><nested:write name="uniqueIdRegistrationForm" property="applicantDetails.personalData.firstName"></nested:write>&nbsp;
				                    <nested:write name="uniqueIdRegistrationForm" property="applicantDetails.personalData.middleName"></nested:write>&nbsp;
				                    <nested:write name="uniqueIdRegistrationForm" property="applicantDetails.personalData.lastName"></nested:write></td><td></td><td></td><td align="right" rowspan="6"><img src='<%= request.getRequestURL().toString().replace(request.getRequestURI().substring(0), request.getContextPath()).concat("/PhotoServlet") %>' height="150px" width="150px"/></td>
	   </tr>
	   
	   <tr>
	     <td><%=i++ %>.</td><td class="algn">Gender</td><td>: </td><td><logic:equal name="uniqueIdRegistrationForm" property="applicantDetails.personalData.gender" value="MALE"><bean:message key="admissionForm.studentinfo.sex.male.text"/></logic:equal>
											<logic:equal name="uniqueIdRegistrationForm" property="applicantDetails.personalData.gender" value="FEMALE"><bean:message key="admissionForm.studentinfo.sex.female.text"/></logic:equal></td>
	   </tr>
	   
	   <tr>
	     <td><%=i++ %>.</td><td class="algn">Date of Birth</td><td>: </td><td><nested:write name="uniqueIdRegistrationForm" property="applicantDetails.personalData.dob"></nested:write></td>
	   </tr>
	   
	   <tr>
	     <td><%=i++ %>.</td><td class="algn">Nationality</td><td>: </td><td><bean:write	name="uniqueIdRegistrationForm" property="applicantDetails.personalData.citizenship" /></td>
	   </tr>
	   
	   <tr>
	     <td><%=i++ %>.</td><td class="algn">Domicile State</td><td>: </td><td><bean:write
												name="uniqueIdRegistrationForm"
												property="applicantDetails.personalData.residentCategoryName" /></td>
	   </tr>
	   
	   <tr>
	     <td><%=i++ %>.</td><td class="algn">Category</td><td>: </td><td><bean:write
												name="uniqueIdRegistrationForm"
												property="applicantDetails.personalData.subregligionName" /></td>
	   </tr>
	   
	   <tr>
	     <td><%=i++ %>.</td><td class="algn">Religion</td><td>: </td><td><bean:write
												name="uniqueIdRegistrationForm"
												property="applicantDetails.personalData.religionName" /></td>
	   </tr>
	   <tr>
	     <td><%=i++ %>.</td><td class="algn">Caste</td><td>: </td><td><bean:write
												name="uniqueIdRegistrationForm"
												property="applicantDetails.personalData.casteCategory" /></td>
	   </tr>
	   
	   
	   <tr>
	     <td><%=i++ %>.</td><td class="algn">Name of Father</td><td>: </td><td><nested:write name="uniqueIdRegistrationForm" property="applicantDetails.titleOfFather"/>. 
											<nested:write name="uniqueIdRegistrationForm" property="applicantDetails.personalData.fatherName"></nested:write></td><td class="algn">Occupation</td><td>: </td><td><bean:write name="uniqueIdRegistrationForm" property="applicantDetails.personalData.fatherOccupation" /></td>
	   </tr>
	   <tr>
	     <td><%=i++ %>.</td><td class="algn">Name of Mother</td><td>: </td><td><nested:write name="uniqueIdRegistrationForm" property="applicantDetails.titleOfMother"/>. 
											<nested:write name="uniqueIdRegistrationForm" property="applicantDetails.personalData.motherName"></nested:write></td><td class="algn">Occupation</td><td>: </td><td><bean:write
												                                                                                                    name="uniqueIdRegistrationForm"
												                                                             property="applicantDetails.personalData.motherOccupation" /></td>
	   </tr>
	   <logic:notEmpty name="uniqueIdRegistrationForm" property="applicantDetails.personalData.fatherMobile">
	   <tr>
	     <td><%=i++ %></td><td class="algn">Mobile Number of Father</td><td>: </td><td>+91 <bean:write name="uniqueIdRegistrationForm" property="applicantDetails.personalData.fatherMobile"/></td>
	   </tr>
	   </logic:notEmpty>
	   <logic:notEmpty name="uniqueIdRegistrationForm" property="applicantDetails.personalData.motherMobile">
	   <tr>
	     <td><%=i++ %>.</td><td class="algn">Mobile Number of Mother</td><td>: </td><td>+91 <bean:write name="uniqueIdRegistrationForm" property="applicantDetails.personalData.motherMobile"/></td>
	   </tr>
	   </logic:notEmpty>
	   <tr>
	     <td><%=i++ %>.</td><td class="algn">Permanent Address</td><td>: </td><td><nested:write name="uniqueIdRegistrationForm" property="applicantDetails.personalData.permanentAddressLine1"></nested:write></td></tr>
	   <tr>
	   <td></td><td></td><td> </td><td class="algn"><nested:write name="uniqueIdRegistrationForm" property="applicantDetails.personalData.permanentAddressLine2"></nested:write>,<nested:write name="uniqueIdRegistrationForm" property="applicantDetails.personalData.permanentCityName"></nested:write>
										</td>
	   </tr>
	   <tr>
	     <td></td><td></td><td></td><td class="algn">
										<bean:write
												name="uniqueIdRegistrationForm"
												property="applicantDetails.personalData.permanentStateName" />,
										<bean:write
												name="uniqueIdRegistrationForm"
												property="applicantDetails.personalData.permanentCountryName" />,
										<nested:write name="uniqueIdRegistrationForm" property="applicantDetails.personalData.permanentAddressZipCode"></nested:write></td>
	   </tr>
	   
	   <tr>
	     <td><%=i++ %>.</td><td class="algn">Present Address</td><td>: </td><td><nested:write name="uniqueIdRegistrationForm" property="applicantDetails.personalData.currentAddressLine1"></nested:write></td></tr>
	   <tr>
	   <td></td><td></td><td> </td><td class="algn"><nested:write name="uniqueIdRegistrationForm" property="applicantDetails.personalData.currentAddressLine2"></nested:write>,
										<nested:write name="uniqueIdRegistrationForm" property="applicantDetails.personalData.currentCityName"></nested:write></td></tr>
										
	   
	    <tr>
	     <td></td><td></td><td></td><td class="algn"><bean:write
												name="uniqueIdRegistrationForm"
												property="applicantDetails.personalData.currentStateName" />,
										<bean:write
												name="uniqueIdRegistrationForm"
												property="applicantDetails.personalData.currentCountryName" />,
										<nested:write name="uniqueIdRegistrationForm" property="applicantDetails.personalData.currentAddressZipCode"></nested:write></td>
	   </tr>
	   
	  <%--  <tr>
	     <td>13.</td><td class="algn">Land Phone</td><td>: </td><td><bean:write name="uniqueIdRegistrationForm" property="applicantDetails.personalData.landlineNo"/></td>
	   </tr> --%>
	   
	   <tr>
	     <td><%=i++ %></td><td class="algn">Mobile Number of Applicant</td><td>: </td><td>+<bean:write name="uniqueIdRegistrationForm" property="applicantDetails.personalData.mobileNo"/></td>
	   </tr>
	   
	   <tr>
	     <td><%=i++ %>.</td><td class="algn">E - Mail</td><td>: </td><td><nested:write name="uniqueIdRegistrationForm" property="applicantDetails.personalData.email"></nested:write></td>
	   </tr>
	   
	  <%--  <tr>
	     <td valign="top"><%=i++ %>.</td><td class="algn">Eligibility for Bonus Marks</td><td>: </td><td>
	                                                                 <logic:equal value="true" name="uniqueIdRegistrationForm" property="applicantDetails.personalData.nsscertificate">NSS, </logic:equal>
	                                                                 <logic:equal value="true" name="uniqueIdRegistrationForm" property="applicantDetails.personalData.ncccertificate">NCC, </logic:equal> 
	                                                                 <logic:equal value="true" name="uniqueIdRegistrationForm" property="applicantDetails.personalData.exservice">Ex-Service </logic:equal>
	                                                               </td>
	   </tr>
	   
	   <tr>
	     <td><%=i++ %>.</td><td class="algn">Claim for Reservation</td><td>: </td><td>
	                                                           <logic:equal value="true" name="uniqueIdRegistrationForm" property="applicantDetails.personalData.sportsPerson">Sports/Cultural, </logic:equal>

	                                                           <logic:equal value="true" name="uniqueIdRegistrationForm" property="applicantDetails.personalData.handicapped"> Physically Challenged, </logic:equal> 
	                                                           
	                                                          </td>
	   </tr> --%>
	   
	   </table>
	   </td>
	  
	</tr>
	</table>
	<table style="line-height: 15px">
	<tr>
	  <td >
	    <table border='1' width="90%" align="center" height='100' cellpadding="3" class="mytable">
           <tr class="title2">
             <td align="center" rowspan='2'>Exam</td>
             <td align="center" rowspan='2'>Reg. No.</td>
             <td align="center" rowspan='2'>Year</td>
             <td align="center" rowspan='2'>Name of Board</td>
             <td align="center" rowspan='2'>Name of School/College</td>
             <td align="center" colspan='3'>Marks</td>
           </tr>
           <tr class="title2">
             <td align="center">%</td>
             <td align="center">Secured</td>
             <td align="center">Total</td>
          </tr>
          <%String noofattempt = ""; 
            String total = "";
            String obtained = "";
          %>
          <nested:iterate name="uniqueIdRegistrationForm" property="applicantDetails.ednQualificationList" id="ednQualList" indexId="count">
          
          <tr>
             <td  align="center">    
											    <logic:equal value="Class 12" name="ednQualList" property="docName">Plus Two
											    <bean:define id="noofAtt" name="ednQualList" property="noOfAttempts"></bean:define>
											    <bean:define id="totalm" name="ednQualList" property="totalMarks"></bean:define>
											    <bean:define id="obtainedm" name="ednQualList" property="marksObtained"></bean:define>
											    <%
											    System.out.println(noofAtt+"==========="+totalm+"=============="+obtainedm);
											    if(noofAtt!=null)
											    {
											    noofattempt =noofAtt.toString(); 
											    }
											    if(totalm!=null)
											    {
											    	total =totalm.toString(); 
											    }
											    if(obtainedm!=null)
											    {
											    	obtained =obtainedm.toString(); 
											    }
											    %>
											    </logic:equal>
											    <logic:equal value="DEG" name="ednQualList" property="docName">DEGREE
											    <bean:define id="noofAtt" name="ednQualList" property="noOfAttempts"></bean:define>
											    <bean:define id="totalm" name="ednQualList" property="totalMarks"></bean:define>
											    <bean:define id="obtainedm" name="ednQualList" property="marksObtained"></bean:define>
											    <%
											    System.out.println(noofAtt+"==========="+totalm+"=============="+obtainedm);
											    if(noofAtt!=null)
											    {
											    noofattempt =noofAtt.toString(); 
											    }
											    if(totalm!=null)
											    {
											    	total =totalm.toString(); 
											    }
											    if(obtainedm!=null)
											    {
											    	obtained =obtainedm.toString(); 
											    }
											    %>
											    </logic:equal>
											    
												<logic:equal value="Class X / SSLC" name="ednQualList" property="docName">SSLC/10th</logic:equal>
											</td>
             <td  align="center"><bean:write name="ednQualList" property="previousRegNo" /></td>
             <td  align="center"><bean:write name="ednQualList" property="yearPassing" /></td>
             <td  align="center"><bean:write name="ednQualList" property="universityName" /></td>
             <td  align="center"><bean:write name="ednQualList" property="institutionName" /></td>
             <td  align="center"><bean:write name="ednQualList" property="percentage" /></td>
             <td  align="center"><bean:write name="ednQualList" property="marksObtained" /></td>
             <td  align="center"><bean:write name="ednQualList" property="totalMarks" /></td>
             
         </tr>
         </nested:iterate>
         
        </table>
	  </td>
	</tr>
		
	
	<logic:equal value="1" name="uniqueIdRegistrationForm" property="applicantDetails.programType">
	<tr>
	  <td align="center" class="title2"><p style="page-break-after:always;"> </p>
	<br></br>Statement of Plus Two Marks</td>
	</tr>
	</logic:equal>	

	<logic:equal value="2" name="uniqueIdRegistrationForm" property="applicantDetails.programType">
	<tr>
	  <td align="center" class="title2"><p style="page-break-after:always;"> </p><br></br>Statement of Degree Marks</td>
	</tr>
	</logic:equal>	

	
	<tr>
	  <td>
	    <table cellpadding="3" align="center" width="80%" border="1" class="mytable">
	      <tr class="title2">
	        <td align="center">Name of Subject</td><td align="center">Marks Secured</td><td align="center">Maximum Marks</td>
	       </tr>
	       <logic:equal value="2" name="uniqueIdRegistrationForm" property="applicantDetails.programType">
	      <logic:iterate id="submrks" property="applicantDetails.pucsubjectmarkto" name="uniqueIdRegistrationForm">
	      <logic:equal value="Core" name="submrks" property="groupname">
	      <tr>
	          <td align="left"><bean:write name="submrks" property="subjectName"/></td>
	          <td  align="center"><bean:write name="submrks" property="obtainedmark" /></td>
	          <td  align="center"><bean:write name="submrks" property="maxmark" /></td>
	        </tr>
	        </logic:equal>
	      </logic:iterate>
	      <logic:iterate id="submrks" property="applicantDetails.pucsubjectmarkto" name="uniqueIdRegistrationForm">
	      <logic:equal value="Complimentary" name="submrks" property="groupname">
	      <tr>
	          <td align="left"><bean:write name="submrks" property="subjectName"/></td>
	          <td  align="center"><bean:write name="submrks" property="obtainedmark" /></td>
	          <td  align="center"><bean:write name="submrks" property="maxmark" /></td>
	        </tr>
	        </logic:equal>
	      </logic:iterate>
	      <logic:iterate id="submrks" property="applicantDetails.pucsubjectmarkto" name="uniqueIdRegistrationForm">
	      <logic:equal value="Common" name="submrks" property="groupname">
	      <tr>
	          <td align="left"><bean:write name="submrks" property="subjectName"/></td>
	          <td  align="center"><bean:write name="submrks" property="obtainedmark" /></td>
	          <td  align="center"><bean:write name="submrks" property="maxmark" /></td>
	        </tr>
	        </logic:equal>
	      </logic:iterate>
	      <logic:iterate id="submrks" property="applicantDetails.pucsubjectmarkto" name="uniqueIdRegistrationForm">
	      <logic:equal value="Open" name="submrks" property="groupname">
	      <tr>
	          <td align="left"><bean:write name="submrks" property="subjectName"/></td>
	          <td  align="center"><bean:write name="submrks" property="obtainedmark" /></td>
	          <td  align="center"><bean:write name="submrks" property="maxmark" /></td>
	        </tr>
	        </logic:equal>
	      </logic:iterate>
	      </logic:equal>
	      <logic:equal value="1" name="uniqueIdRegistrationForm" property="applicantDetails.programType">
	      <logic:iterate id="submrks" property="applicantDetails.pucsubjectmarkto" name="uniqueIdRegistrationForm">
	      <logic:equal value="English" name="submrks" property="subjectName">
	      <tr>
	          <td align="left"><bean:write name="submrks" property="subjectName"/></td>
	          <td  align="center"><bean:write name="submrks" property="obtainedmark" /></td>
	          <td  align="center"><bean:write name="submrks" property="maxmark" /></td>
	        </tr>
	        </logic:equal>
	        </logic:iterate>
	        <logic:iterate id="submrks" property="applicantDetails.pucsubjectmarkto" name="uniqueIdRegistrationForm">
	        <logic:notEqual value="English" name="submrks" property="subjectName">
	        <logic:equal value="Language" name="submrks" property="groupname">
	      <tr>
	          <td align="left"><bean:write name="submrks" property="subjectName"/></td>
	          <td  align="center"><bean:write name="submrks" property="obtainedmark" /></td>
	          <td  align="center"><bean:write name="submrks" property="maxmark" /></td>
	        </tr>
	        </logic:equal>
	        </logic:notEqual>
	        </logic:iterate>
	        <logic:iterate id="submrks" property="applicantDetails.pucsubjectmarkto" name="uniqueIdRegistrationForm">
	        <logic:notEqual value="English" name="submrks" property="subjectName">
	        <logic:notEqual value="Language" name="submrks" property="groupname">
	      <tr>
	          <td align="left"><bean:write name="submrks" property="subjectName"/></td>
	          <td  align="center"><bean:write name="submrks" property="obtainedmark" /></td>
	          <td  align="center"><bean:write name="submrks" property="maxmark" /></td>
	        </tr>
	        </logic:notEqual>
	        </logic:notEqual>
	        </logic:iterate>
	        </logic:equal>
	      <tr>
	        <td class="title2" align="right">Total Marks</td><td><center><%=obtained%></center></td><td><center><%=total%></center></td>
	      </tr>
	    </table>
	  </td>
	</tr>
	<tr>
	 <td>
	   <table >
	   
	   	 <logic:equal value="1" name="uniqueIdRegistrationForm" property="applicantDetails.programType">
	     <tr><td style="height: 15px"></td></tr>
	     <tr>
	       <td><%=i++ %>.</td><td>No. of chances taken to pass Plus Two</td><td>: </td><td><%=noofattempt %></td>
	     </tr>
	     </logic:equal>
	     
	   	 

	      <logic:equal value="1" name="uniqueIdRegistrationForm" property="applicantDetails.programType">
	     <tr>
	       <td><%=i++ %>.</td><td>Preference Details</td>
	    </tr>
	    </logic:equal>
	   </table>
	    </td>
    </tr>
    <logic:equal value="1" name="uniqueIdRegistrationForm" property="applicantDetails.programType">
	<tr><td style="height: 15px"></td></tr>
    <tr>
      <td>
        <table cellpadding="3" width="60%" align="center" border="1" class="mytable">
           <tr class="title2">
             <td align="center">Preference No.</td><td align="center">Programme</td>
           </tr>
           <logic:iterate id="preflist" name="uniqueIdRegistrationForm" property="applicantDetails.preflist">
               <tr>
                 <td  align="center"><bean:write name="preflist" property="prefNo"/></td>
                  <td><bean:write name="preflist" property="coursName"/></td>
               </tr>
           
           </logic:iterate>
        </table>
      </td>
    </tr>
    </logic:equal>
    <tr><td><table width="70%"> 
    <logic:equal value="1" name="uniqueIdRegistrationForm" property="applicantDetails.programType">
    <tr><td style="height: 15px"></td></tr>
    </logic:equal>
    <logic:equal value="2" name="uniqueIdRegistrationForm" property="applicantDetails.programType">
	   	 <tr><td style="height: 15px"></td></tr>
	     <tr>
	       <td><%=i++ %>.</td><td>No. of chances taken to pass Degree</td><td>: </td><td><%=noofattempt %></td>
	     </tr>
	     </logic:equal>
	 <tr>
	     <td><%=i++ %>.</td><td class="algn">Whether physically challenged<br>(If yes, attach the copy of certificate)</td><td>: </td><td>
	     <logic:equal value="true" name="uniqueIdRegistrationForm" property="applicantDetails.personalData.handicapped">
	    	Yes
	     </logic:equal>
	     <logic:equal value="false" name="uniqueIdRegistrationForm" property="applicantDetails.personalData.handicapped">
	    	No
	     </logic:equal>
	     </td>
	 </tr>
	 <tr>
	     <td><%=i++ %>.</td><td class="algn">Residence during the programme </td><td>: </td><td><bean:write name="uniqueIdRegistrationForm" property="applicantDetails.personalData.currentCityName"/>,<bean:write name="uniqueIdRegistrationForm" property="applicantDetails.personalData.currentDistricName"/></td>
	 </tr>
	 <logic:equal value="1" name="uniqueIdRegistrationForm" property="applicantDetails.programType">
	 <tr>
	     <td><%=i++ %>.</td><td class="algn">Second Language opting for Degree </td><td>: </td><td><bean:write name="uniqueIdRegistrationForm" property="applicantDetails.personalData.secondLanguage"/></td>
	 </tr>
	 </logic:equal>
	 <logic:notEmpty name="uniqueIdRegistrationForm" property="applicantDetails.personalData.nccgrades">
	   <tr>
	    <td> <%=i++ %>.</td><td class="algn">NCC Grades</td><td>: </td><td width="10%">NCC ,&nbsp;<bean:write name="uniqueIdRegistrationForm" property="applicantDetails.personalData.nccgrades"/></td>
	   </tr>
	   </logic:notEmpty >
	   <logic:notEmpty name="uniqueIdRegistrationForm" property="applicantDetails.personalData.nssgrades">
	   <tr>
	    <td> <%=i++ %>.</td><td class="algn">NSS Grades</td><td>: </td><td width="10%">NSS ,&nbsp;<bean:write name="uniqueIdRegistrationForm" property="applicantDetails.personalData.nssgrades"/></td>
	   </tr>
	   </logic:notEmpty >
	   <logic:notEmpty name="uniqueIdRegistrationForm" property="applicantDetails.personalData.preferenceNoCAP">
	 <tr>
	     <td><%=i++ %>.</td><td class="algn">CAP Register Number </td><td>: </td><td><bean:write name="uniqueIdRegistrationForm" property="applicantDetails.personalData.preferenceNoCAP"/></td>
	 </tr>
	 </logic:notEmpty>
	  <logic:notEmpty name="uniqueIdRegistrationForm" property="applicantDetails.personalData.dioceseOthers">
	 <tr>
	     <td><%=i++ %>.</td><td class="algn">Diocese </td><td>: </td><td><bean:write name="uniqueIdRegistrationForm" property="applicantDetails.personalData.dioceseOthers"/></td>
	 </tr>
	 </logic:notEmpty>
	 <logic:notEmpty name="uniqueIdRegistrationForm" property="applicantDetails.personalData.parishOthers">
	 <tr>
	     <td><%=i++ %>.</td><td class="algn">Parish</td><td>: </td><td><bean:write name="uniqueIdRegistrationForm" property="applicantDetails.personalData.parishOthers"/></td>
	 </tr>
	 </logic:notEmpty>
    <tr>
	     <td><%=i++ %>.</td><td class="algn">Recommendation of the Parish Priest with Name,Seal and Signature </td><td>: </td><td><%-- <bean:write name="uniqueIdRegistrationForm" property="applicantDetails.personalData.recommendDeatails"/> --%></td>
	 </tr>
	    <tr height="100px"><td colspan="3"></td></tr>
    <logic:notEmpty name="uniqueIdRegistrationForm" property="applicantDetails.personalData.recommentedBy">
    <tr>
	     <td><%=i++ %>.</td><td class="algn">Recommended By</td><td>: </td><td><bean:write name="uniqueIdRegistrationForm" property="applicantDetails.personalData.recommentedBy"/></td>
	 </tr>
	   </logic:notEmpty>
	    <logic:notEmpty name="uniqueIdRegistrationForm" property="applicantDetails.personalData.recommentedPersonDesignation">
	   <tr>
	     <td> <%=i++ %>.</td><td class="algn">Recommended Person Designation</td><td>: </td><td><bean:write name="uniqueIdRegistrationForm" property="applicantDetails.personalData.recommentedPersonDesignation"/></td>
	   </tr>
	   </logic:notEmpty>
	   <logic:notEmpty name="uniqueIdRegistrationForm" property="applicantDetails.personalData.recommentedPersonMobile">
	   <tr>
	    <td> <%=i++ %>.</td><td class="algn">Recommended Person MobileNo</td><td>: </td><td><bean:write name="uniqueIdRegistrationForm" property="applicantDetails.personalData.recommentedPersonMobile"/></td>
	   </tr>
	   </logic:notEmpty>
	   
    <%-- <tr>
	     <td> <%=i++ %>.</td><td class="algn">Recommedation Application No</td><td>: </td><td><bean:write name="uniqueIdRegistrationForm" property="applicantDetails.recommedationApplicationNo"/></td>
	 </tr> --%>
	   </table></td></tr>
	   <tr>
	  <td align="center" class="title"><font style="text-decoration: underline"><br></br>DECLARATION<br></br></font></td>
	</tr>
	
	 <tr>
	  <td align="left"><p>I, <nested:write name="uniqueIdRegistrationForm" property="applicantDetails.personalData.firstName"></nested:write>&nbsp;
	  								<nested:notEmpty name="uniqueIdRegistrationForm" property="applicantDetails.personalData.middleName">
				                    <nested:write name="uniqueIdRegistrationForm" property="applicantDetails.personalData.middleName"></nested:write>&nbsp;</nested:notEmpty>
				                    <nested:notEmpty name="uniqueIdRegistrationForm" property="applicantDetails.personalData.lastName">
				                    <nested:write name="uniqueIdRegistrationForm" property="applicantDetails.personalData.lastName"></nested:write> </nested:notEmpty>
				                    hereby declare that the information furnished above is true to the best of my knowledge. If granted admission, I agree to abide by the rules and regulations of the College.</p><br></br></td>
	</tr>
	<tr><td><p> </p></td></tr>
	      <tr>
	        <td class="title2">
	         Place:<br></br>
	       </td>
	     </tr>
	     <tr><td>
	     <table width="100%">
	     <tr class="title2">
	       <td align="left">Date: </td><td align="center">Signature of Parent/Guardian</td><td align="right">Signature of the Candidate</td>
	    </tr>
	    </table>
	    </td></tr>
	    </table>
	    <p style="page-break-after:always;"> </p>
	<table width="100%">
	        <tr class="title2">
	         <td colspan="2" align="center"><u>പ്രതിജ്ഞ</u></td>
	        </tr>
<tr><td style="height: 15px"></td></tr>

	        <tr class="title2">
	         <td colspan="2" align="left"  style="line-height: 25px;"><p>എന്നെ ഈ കോളേജിൽ ചേർക്കുന്ന പക്ഷം കോളേജിലെ നിയമങ്ങളും കോളേജ് അധികാരികളുടെ അപ്പഴപ്പോഴുള്ള നിർദേശങ്ങളും പാലിച്ചു കൊള്ളാമെന്നും കോളേജ്  നടത്തിപ്പിന് തടസ്സം വരുത്തുന്ന യാതൊരു
	          പരിപാടികളിലും കോളേജിന് അകത്തോ പുറത്തോ വച്ച് ഉൾപ്പെടുകയില്ലെന്നും കോളേജിൻ്റെ വസ്തുവകകൾ കേടു വരുത്തുകയോ നശിപ്പിക്കുകയോ ചെയ്യുകയില്ലെന്നും എൻ്റെ രക്ഷകർത്താവിനെ സാക്ഷിയാക്കി ഞാൻ പ്രതിജ്ഞ ചെയ്യുന്നു. കോളേജിൽ രാഷ്ട്രീയം നിരോധിച്ചിരിക്കുന്നു എന്ന് ഞാൻ മനസ്സിലാക്കുന്നു. യാതൊരുവിധ രാഷ്ട്രീയമോ അല്ലാത്തതോ ആയ സമരങ്ങൾക്കും ഞാൻ പങ്കു  ചേരുന്നതല്ല.
	          </p></td>
	        </tr>
	         <tr height="15px"><td colspan="2"></td></tr>
	        <tr>
	         <td align="left">സ്ഥലം :......................</td>
	        </tr>
	         <tr height="15px"><td colspan="2"></td></tr>
	        <tr>
	         <td align="left">തീയതി :......................</td><td align="right" style="padding-right: 2%">വിദ്യാർത്ഥിയുടെ ഒപ്പ് :......................</td>
	        </tr>
	         <tr height="15px"><td colspan="2"></td></tr>
	        <tr class="title2">
	         <td colspan="2" align="left"><p style="line-height: 25px;">എൻ്റെ രക്ഷാകർതൃത്വത്തിലുള്ള ഈ വിദ്യാർത്ഥിയെ കോളേജിൽ ചേർക്കുന്ന പക്ഷം വിദ്യാർത്ഥിയുടെ മേൽ പറഞ്ഞ പ്രതിജ്ഞകൾ കർശനമായി പാലിക്കുന്നതിനും സ്വഭാവവും പെരുമാറ്റവും 
	         നന്നായിരിക്കുന്നതിനും വിദ്യാർത്ഥി കോളേജിന് എന്തെങ്കിലും നഷ്ടം വരുത്തുന്ന പക്ഷം അത്  പരിഹരിക്കാനുള്ള ഉത്തരവാദിത്വം വിദ്യാർത്ഥിയുടെ രക്ഷകർത്താവായ ഞാൻ  ഏറ്റെടുക്കുന്നു.
	         </p></td>
	        </tr>
	        <tr height="15px"><td colspan="2"></td></tr>
	        <tr >
	         <td align="left">സ്ഥലം :......................</td><td align="right" style="padding-right: 2%">രക്ഷകർത്താവിൻ്റെ പേര് : .............................................</td>
	        </tr>
	        <tr height="15px"><td colspan="2"></td></tr>
	        <tr>
	         <td align="left">തീയതി :......................</td><td align="right" style="padding-right: 2%">രക്ഷകർത്താവിൻ്റെ  ഒപ്പ് : ...............................................</td>
	        </tr>
	 </table>
<tr><td style="height: 15px"></td></tr>
<tr><td style="height: 15px"></td></tr>



	<hr style="size:1px;background-color: black" noshade="noshade"  align="center"></hr>
	<table width="100%" class="smallcheck">
	        <tr class="title2">
	         <td style="height: 25px">NB<p>Anyone,who secures admission on the basis of false information supplied 
	         will be summarily dismissed from the college, as soon as the matter come to the notice of the Principal.<p></td>
	        </tr>
<tr><td style="height: 15px"></td></tr>

	        <tr class="title2">
	         <td style="line-height: 25px;">Note<p>
	          1.A copy of the conduct certificate obtained from the head of the institution last attended should be enclosed with the application.<br>
	          2.SC/ST/OEC/OBC-H students are to produce their Community and Income certificates at the time of admission.<br>
	          3.Applicants are to keep with them sufficient number of attested true copies of their SSLC/PlusTwo<logic:equal value="2" name="uniqueIdRegistrationForm" property="applicantDetails.programType">/Degree</logic:equal> mark list for their use. Originals submitted at the time of admission will not be returned during the time of the Programme.
	         <p></td>
	        </tr>
	 </table>
<tr><td style="height: 15px"></td></tr>
<tr><td style="height: 15px"></td></tr>

	   <hr style="size:1px;background-color: black" noshade="noshade"  align="center"></hr>
	   <table width="100%">	
<tr><td></td></tr>

	 <tr>
	  <td align="center" class="title"><font style="text-decoration: underline">FOR OFFICE USE ONLY<br></br></font></td>
	</tr>
	 <tr>
	  <td align="left"><p class="office">The applicant may be admitted to the first semester ...............................................................programme.
	  </p></td>
	</tr>
	 <tr><td><br></br></td></tr>
	 
	  <tr><td style="height: 50px"></td></tr>
	  <tr>
	    <td>
	      <table width="100%">
	         <tr class="title2">
	         <td colspan="2" align="left">Head of the Department </td><td align="right">Admission Convener</td>
	        </tr>
	        <tr><td colspan="3" style="height: 90px"></td></tr>
	        <tr class="title2">
	         <td align="left"  width="30%">Date: </td><td  width="35%" align="center">Principal</td><td  width="35%" align="right">Manager</td></tr>
	      </table>
	    </td>
	  </tr>
	    
	
	</table>
	
	
</html:form>
<script type="text/javascript">
	window.print();
</script>