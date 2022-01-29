<%@taglib prefix="pd4ml" uri="http://pd4ml.com/tlds/pd4ml/2.6"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="com.kp.cms.constants.CMSConstants"%>
<pd4ml:transform 
      inline="false"
	  fileName="application.pdf"
      screenWidth="815"
      pageFormat="A4"
      pageOrientation="portrait"
      pageInsets="10,10,10,10,points">
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
	border:1px solid #0000;
	text-align:
	
}
.algn{
   height:25px;

}
</style>
<SCRIPT type="text/javascript">
	function downloadFile(documentId) {
		document.location.href = "DocumentDownloadAction.do?documentId="
				+ documentId;
	}
	function getSemesterMarkDetails(qualId) {
		var url = "admissionFormSubmit.do?method=viewSemesterMarkPage&editcountID="
				+ qualId;
		myRef = window
				.open(url, "ViewSemesterMarkDetails",
						"left=20,top=20,width=800,height=300,toolbar=1,resizable=0,scrollbars=1");
	}
	function getDetailsMark(qualId) {
		var url = "admissionFormSubmit.do?method=viewDetailMarkPage&editcountID="
				+ qualId;
		myRef = window
				.open(url, "ViewDetailsMark",
						"left=20,top=20,width=800,height=600,toolbar=1,resizable=0,scrollbars=1");
	}
	function detailLateralSubmit() {
		var url = "admissionFormSubmit.do?method=viewLateralEntryPage";
		myRef = window
				.open(url, "ViewLateralDetails",
						"left=20,top=20,width=800,height=600,toolbar=1,resizable=0,scrollbars=1");
	}
	function detailTransferSubmit() {
		var url = "admissionFormSubmit.do?method=viewTransferEntryPage";
		myRef = window
				.open(url, "ViewTransferDetails",
						"left=20,top=20,width=800,height=600,toolbar=1,resizable=0,scrollbars=1");
	}
	function forwardQualifySeparate() {
		var url = "admissionFormSubmit.do?method=forwardQualifyExamPrint";
		myRef = window
				.open(url, "ViewTransferDetails",
						"left=20,top=20,width=800,height=600,toolbar=1,resizable=0,scrollbars=1");
	}
	function printMe() {
		window.print();
	}
	function closeMe() {
		window.close();
	}
</SCRIPT>



<script language="JavaScript" src="js/calendar_us.js"></script>
	<link rel="stylesheet" href="css/calendar.css">
<script language="JavaScript" src="js/admission/admissionform.js"></script>
<html:form action="/uniqueIdRegistration" method="POST">
	<html:hidden property="method" styleId="method" />
	<html:hidden property="formName" value="uniqueIdRegistrationForm" />
	<input type="hidden" name="applicationId"
		value='<bean:write name="uniqueIdRegistrationForm" property="applicantDetails.applicationId" />' />
	<input type="hidden" name="courseId" value='<bean:write	name="uniqueIdRegistrationForm" property="applicantDetails.course.id" />' />	
	<table  width="100%" border="0"  cellpadding="3" cellspacing="0" bordercolor="#E0DFDB">
	
	<tr>
	 	<td align="right">
	 		<table border="1" style="border:1px solid black; border-collapse: collapse">
	 			<tr>
	 				<td style="font-weight :normal; font-size: xx-small;border:1px solid black; border-collapse: collapse;">GENERAL</td>
		 			<td style="font-weight :normal; font-size: xx-small;border:1px solid black; border-collapse: collapse;">SC</td>
		 			<td style="font-weight :normal; font-size: xx-small;border:1px solid black; border-collapse: collapse;">ST</td>
		 			<td style="font-weight :normal; font-size: xx-small;border:1px solid black; border-collapse: collapse;">PWD</td>
		 			<td style="font-weight :normal; font-size: xx-small;border:1px solid black; border-collapse: collapse;">SPORTS</td>
		 			<td style="font-weight :normal; font-size: xx-small;border:1px solid black; border-collapse: collapse;">CULTURAL</td>
		 			<td style="font-weight :normal; font-size: xx-small;border:1px solid black; border-collapse: collapse;">COMMUNITY</td>
		 			<td style="font-weight :normal; font-size: xx-small;border:1px solid black; border-collapse: collapse;">OBC</td>
		 			<td style="font-weight :normal; font-size: xx-small;border:1px solid black; border-collapse: collapse;">OEC</td>
	 			</tr>
	 		</table>	
	 	</td>
	 </tr>
			
	 <tr>
	 	
		<td align="center">
		<%--<img width="800" height="150"  src='<%= request.getRequestURL().toString().replace(request.getRequestURI().substring(0), request.getContextPath()).concat("/LogoServlet") %>' alt="Logo not available">
		 --%>
		<img src="images/admission/images/headerlogo.png" alt="noimg" width="600" height="150" align="top"/>
		  </td>
	</tr>
	<tr>
	  <td>
	    <table width="100%">
	      <tr>
	        <td width="50%">
	          <table >
	           <tr>
	            <td>Appl. No</td><td>: </td><td><bean:write name="uniqueIdRegistrationForm" property="applicantDetails.applnNo"/></td>
	           </tr>
	           <tr>
	            <td>ID No</td><td>: </td><td><bean:write name="uniqueIdRegistrationForm" property="applicantDetails.journalNo"/></td>
	           </tr>
	           <tr>
	            <td>Date</td><td>: </td><td><bean:write name="uniqueIdRegistrationForm" property="applicantDetails.challanDate"/></td>
	           </tr>
	          </table>
	        </td>
	        
	        <td width="50%">
	        <b>FOR OFFICE USE ONLY</b>
	          <table width="100%" border="1" cellpadding="3" style="border:1px solid black; border-collapse: collapse">
	            <tr><td width="30%" style="border:1px solid black; border-collapse: collapse">Admission No.</td> <td width="70%" style="border:1px solid black; border-collapse: collapse">&nbsp;</td></tr>
	            <tr><td width="30%" style="border:1px solid black; border-collapse: collapse">Programme</td> <td width="70%" style="border:1px solid black; border-collapse: collapse"> &nbsp;</td></tr>
	            <tr><td width="30%" style="border:1px solid black; border-collapse: collapse">Class No.</td> <td width="70%" style="border:1px solid black; border-collapse: collapse"> &nbsp;</td></tr>
	          </table>
	         
	        </td>
	      </tr>
	    </table>
	                      
	  </td>
	</tr>
	
	<logic:equal value="1" name="uniqueIdRegistrationForm" property="applicantDetails.programType">
	<tr>
	  <td style="font-size:medium; font-weight: bold" align="center"><br></br>APPLICATION FOR ADMISSION TO UNDERGRADUATE PROGRAMME - <bean:write name="uniqueIdRegistrationForm" property="applicantDetails.appliedYear"/><br></br></td>
	</tr>
	</logic:equal>
	
	<logic:equal value="2" name="uniqueIdRegistrationForm" property="applicantDetails.programType">
	<tr>
	  <td style="font-size:medium; font-weight: bold" align="center"><br></br>APPLICATION FOR ADMISSION TO POSTGRADUATE PROGRAMME - <bean:write name="uniqueIdRegistrationForm" property="applicantDetails.appliedYear"/><br></br></td>
	</tr>
	</logic:equal>   
	   <tr><td><p></p></td></tr>
	
	      <tr>
	      <td>
	        <table border="0" style="line-height: 20px;">
	        <tr>
	     <td>1.</td><td height="25px">Name</td><td>: </td><td><nested:write name="uniqueIdRegistrationForm" property="applicantDetails.personalData.firstName"></nested:write>&nbsp;
				                    <nested:write name="uniqueIdRegistrationForm" property="applicantDetails.personalData.middleName"></nested:write>&nbsp;
				                    <nested:write name="uniqueIdRegistrationForm" property="applicantDetails.personalData.lastName"></nested:write></td><td></td><td></td><td align="right" rowspan="6"><img src='<%= request.getRequestURL().toString().replace(request.getRequestURI().substring(0), request.getContextPath()).concat("/PhotoServlet") %>' height="150px" width="150px"/></td>
	   </tr>
	   
	   <tr>
	     <td>2.</td><td height="25px">Gender</td><td>: </td><td><logic:equal name="uniqueIdRegistrationForm" property="applicantDetails.personalData.gender" value="MALE"><bean:message key="admissionForm.studentinfo.sex.male.text"/></logic:equal>
											<logic:equal name="uniqueIdRegistrationForm" property="applicantDetails.personalData.gender" value="FEMALE"><bean:message key="admissionForm.studentinfo.sex.female.text"/></logic:equal></td>
	   </tr>
	   
	   <tr>
	     <td>3.</td><td height="25px">Date of Birth</td><td>: </td><td><nested:write name="uniqueIdRegistrationForm" property="applicantDetails.personalData.dob"></nested:write></td>
	   </tr>
	   
	   <tr>
	     <td>4.</td><td height="25px">Nationality</td><td>: </td><td><bean:write name="uniqueIdRegistrationForm" property="applicantDetails.personalData.citizenship"/></td>
	   </tr>
	   
	   <tr>
	     <td>5.</td><td height="25px">Domicile State</td><td>: </td><td><bean:write
												name="uniqueIdRegistrationForm"
												property="applicantDetails.personalData.residentCategoryName" /></td>
	   </tr>
	   
	   <tr>
	     <td>6.</td><td height="25px">Religion</td><td>: </td><td><bean:write
												name="uniqueIdRegistrationForm"
												property="applicantDetails.personalData.religionName" /></td>
	   </tr>
	   <tr>
	     <td>7.</td><td height="25px">Caste</td><td>: </td><td><bean:write
												name="uniqueIdRegistrationForm"
												property="applicantDetails.personalData.casteCategory" /></td>
	   </tr>
	   
	   <tr>
	     <td>8.</td><td class="algn">Category</td><td>: </td><td><bean:write
												name="uniqueIdRegistrationForm"
												property="applicantDetails.personalData.subregligionName" /></td>
	   </tr>
	   
	   
	   <tr>
	     <td>9.</td><td height="25px">Name of Father</td><td>: </td><td><nested:write name="uniqueIdRegistrationForm" property="applicantDetails.titleOfFather"/>. 
											<nested:write name="uniqueIdRegistrationForm" property="applicantDetails.personalData.fatherName"></nested:write></td><td height="25px">Occupation</td><td>: </td><td><bean:write name="uniqueIdRegistrationForm" property="applicantDetails.personalData.fatherOccupation" /></td>
	   </tr>
	   <tr>
	     <td>10.</td><td height="25px">Name of Mother</td><td>: </td><td><nested:write name="uniqueIdRegistrationForm" property="applicantDetails.titleOfMother"/>. 
											<nested:write name="uniqueIdRegistrationForm" property="applicantDetails.personalData.motherName"></nested:write></td><td height="25px">Occupation</td><td>: </td><td><bean:write
												                                                                                                    name="uniqueIdRegistrationForm"
												                                                             property="applicantDetails.personalData.motherOccupation" /></td>
	   </tr>
	   
	   <tr>
	     <td>11.</td><td height="25px">Permanent Address</td><td>: </td><td><nested:write name="uniqueIdRegistrationForm" property="applicantDetails.personalData.permanentAddressLine1"></nested:write></td></tr>
	   <tr>
	   <td></td><td></td><td> </td><td height="25px"><nested:write name="uniqueIdRegistrationForm" property="applicantDetails.personalData.permanentAddressLine2"></nested:write>,<nested:write name="uniqueIdRegistrationForm" property="applicantDetails.personalData.permanentCityName"></nested:write>
										</td>
	   </tr>
	   <tr>
	     <td></td><td></td><td></td><td height="25px">
										<bean:write
												name="uniqueIdRegistrationForm"
												property="applicantDetails.personalData.permanentStateName" />,
										<bean:write
												name="uniqueIdRegistrationForm"
												property="applicantDetails.personalData.permanentCountryName" />,
										<nested:write name="uniqueIdRegistrationForm" property="applicantDetails.personalData.permanentAddressZipCode"></nested:write></td>
	   </tr>
	   
	   <tr>
	     <td>12.</td><td height="25px">Present Address</td><td>: </td><td><nested:write name="uniqueIdRegistrationForm" property="applicantDetails.personalData.currentAddressLine1"></nested:write></td></tr>
	   <tr>
	   <td></td><td></td><td> </td><td height="25px"><nested:write name="uniqueIdRegistrationForm" property="applicantDetails.personalData.currentAddressLine2"></nested:write>,
										<nested:write name="uniqueIdRegistrationForm" property="applicantDetails.personalData.currentCityName"></nested:write></td></tr>
										
	   
	    <tr>
	     <td></td><td></td><td></td><td height="25px"><bean:write
												name="uniqueIdRegistrationForm"
												property="applicantDetails.personalData.currentStateName" />,
										<bean:write
												name="uniqueIdRegistrationForm"
												property="applicantDetails.personalData.currentCountryName" />,
										<nested:write name="uniqueIdRegistrationForm" property="applicantDetails.personalData.currentAddressZipCode"></nested:write></td>
	   </tr>
	   
	   <tr>
	     <td>13.</td><td height="25px">Land Phone</td><td>: </td><td><bean:write name="uniqueIdRegistrationForm" property="applicantDetails.personalData.landlineNo"/></td>
	   </tr>
	   
	   <tr>
	     <td></td><td height="25px">Mobile</td><td>: </td><td><bean:write name="uniqueIdRegistrationForm" property="applicantDetails.personalData.mobileNo"/></td>
	   </tr>
	   
	   <tr>
	     <td>14.</td><td height="25px">E - Mail</td><td>: </td><td><nested:write name="uniqueIdRegistrationForm" property="applicantDetails.personalData.email"></nested:write></td>
	   </tr>
	   
	   <tr>
	     <td valign="top">15.</td><td height="25px">Eligibility for Bonus Marks</td><td>: </td><td>
	                                                                 <logic:equal value="true" name="uniqueIdRegistrationForm" property="applicantDetails.personalData.nsscertificate">NSS, </logic:equal>
	                                                                 <logic:equal value="true" name="uniqueIdRegistrationForm" property="applicantDetails.personalData.ncccertificate">NCC, </logic:equal> 
	                                                                 <logic:equal value="true" name="uniqueIdRegistrationForm" property="applicantDetails.personalData.exservice">Ex-Service </logic:equal>
	                                                               </td>
	   </tr>
	   
	   <tr>
	     <td>16.</td><td class="algn">Claim for Reservation</td><td>: </td><td>
	                                                           <logic:equal value="true" name="uniqueIdRegistrationForm" property="applicantDetails.personalData.sportsPerson">Sports/Cultural, </logic:equal>

	                                                           <logic:equal value="true" name="uniqueIdRegistrationForm" property="applicantDetails.personalData.handicapped"> Physically Challenged, </logic:equal> 
	                                                           
	                                                          </td>
	   </tr>
	   
	   </table>
	   </td>
	  
	</tr>
	<tr>
	  <td>
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
	
	
	</table>
	
	
	
	<p style="page-break-after:always;"> </p>
	
	<table style="line-height: 15px">
	
	<logic:equal value="1" name="uniqueIdRegistrationForm" property="applicantDetails.programType">
	<tr>
	  <td align="center" style="font-size:small; font-weight: bold"><br></br>Statement of Plus Two Marks</td>
	</tr>
	</logic:equal>
	
	<logic:equal value="2" name="uniqueIdRegistrationForm" property="applicantDetails.programType">
	<tr>
	  <td align="center" style="font-size:small; font-weight: bold"><br></br>Statement of Degree Marks</td>
	</tr>
	</logic:equal>
	<tr>
	  <td>
	    <table cellpadding="3" align="center" width="80%" border="1" style="border:1px solid black; border-collapse: collapse ">
	      <tr style="font-size:small; font-weight: bold">
	        <td align="center" style="border:1px solid black; border-collapse: collapse ">Name of Subject</td>
	        <td align="center" style="border:1px solid black; border-collapse: collapse ">Marks Secured</td>
	        <td align="center" style="border:1px solid black; border-collapse: collapse ">Maximum Marks</td>
	       </tr>
	      <logic:iterate id="submrks" property="applicantDetails.pucsubjectmarkto" name="uniqueIdRegistrationForm">
	      <tr>
	          <td align="left" style="border:1px solid black; border-collapse: collapse "><bean:write name="submrks" property="subjectName"/></td>
	          <td  align="center" style="border:1px solid black; border-collapse: collapse "><bean:write name="submrks" property="obtainedmark" /></td>
	          <td  align="center" style="border:1px solid black; border-collapse: collapse "><bean:write name="submrks" property="maxmark" /></td>
	        </tr>
	      </logic:iterate>
	     <tr>
	        <td class="title2" align="right">Total Marks</td><td><center><%=obtained%></center></td><td><center><%=total%></center></td>
	      </tr>
	    </table>
	  </td>
	</tr>
	<tr>
	 <td>
	   <table>
	   
	   	 <logic:equal value="1" name="uniqueIdRegistrationForm" property="applicantDetails.programType">
	     <tr>
	       <td>17.</td><td>No. of chances taken to pass plus two</td><td>: </td><td><%=noofattempt %></td>
	     </tr>
	     </logic:equal>

	   	 <logic:equal value="2" name="uniqueIdRegistrationForm" property="applicantDetails.programType">
	     <tr>
	       <td>17.</td><td>No. of chances taken to pass degree</td><td>: </td><td><%=noofattempt %></td>
	     </tr>
	     </logic:equal>
	     
	     
	     
	     
	     <tr>
	       <td>18.</td><td>Preference Details</td>
	    </tr>
	   </table>
	    </td>
    </tr>
    
    <tr>
      <td>
        <table cellpadding="3" width="60%" align="center" border="1" style="border:1px solid black; border-collapse: collapse ">
           <tr style="font-size:small; font-weight: bold">
             <td align="center" style="border:1px solid black; border-collapse: collapse ">Preference No.</td><td align="center" style="border:1px solid black; border-collapse: collapse ">Programme</td>
           </tr>
           <logic:iterate id="preflist" name="uniqueIdRegistrationForm" property="applicantDetails.preflist">
               <tr>
                 <td  align="center" style="border:1px solid black; border-collapse: collapse "><bean:write name="preflist" property="prefNo"/></td>
                  <td style="border:1px solid black; border-collapse: collapse "><bean:write name="preflist" property="coursName"/></td>
               </tr>
           
           </logic:iterate>
        </table>
      </td>
    </tr>
    <tr><td><table width="100%"> 
    
	 <tr>
	     <td>19.</td><td class="algn">whether physically challenged(If yes attach the copy of certificate):</td><td>: </td><td>
	     <logic:equal value="true" name="uniqueIdRegistrationForm" property="applicantDetails.personalData.handicapped">
	    	Yes
	     </logic:equal>
	     <logic:equal value="false" name="uniqueIdRegistrationForm" property="applicantDetails.personalData.handicapped">
	    	No
	     </logic:equal>
	     </td>
	 </tr>
	 <tr>
	     <td>20.</td><td class="algn">Residence during the programme :</td><td>: </td><td><bean:write name="uniqueIdRegistrationForm" property="applicantDetails.personalData.currentCityName"/>,<bean:write name="uniqueIdRegistrationForm" property="applicantDetails.personalData.currentDistricName"/></td>
	 </tr>
	 <logic:equal value="1" name="uniqueIdRegistrationForm" property="applicantDetails.programType">
	 <tr>
	     <td>21.</td><td class="algn">Second Language :</td><td>: </td><td><bean:write name="uniqueIdRegistrationForm" property="applicantDetails.personalData.secondLanguage"/></td>
	 </tr>
	 </logic:equal>
	  <logic:notEmpty name="uniqueIdRegistrationForm" property="applicantDetails.personalData.dioceseOthers">
	 <tr>
	     <td>22.</td><td class="algn">Diocese :</td><td>: </td><td><bean:write name="uniqueIdRegistrationForm" property="applicantDetails.personalData.dioceseOthers"/></td>
	 </tr>
	 </logic:notEmpty>
	 <logic:notEmpty name="uniqueIdRegistrationForm" property="applicantDetails.personalData.parishOthers">
	 <tr>
	     <td>23.</td><td class="algn">Parish:</td><td>: </td><td><bean:write name="uniqueIdRegistrationForm" property="applicantDetails.personalData.parishOthers"/></td>
	 </tr>
	 </logic:notEmpty>
     <logic:notEmpty name="uniqueIdRegistrationForm" property="applicantDetails.personalData.recommendDeatails">
    <tr>
	     <td>24.</td><td class="algn">Name, Designation and Mobile Number of the Referee:</td><td>: </td><td><bean:write name="uniqueIdRegistrationForm" property="applicantDetails.personalData.recommendDeatails"/></td>
	 </tr>
	   </logic:notEmpty>
    <logic:notEmpty name="uniqueIdRegistrationForm" property="applicantDetails.personalData.recommentedBy">
    <tr>
	     <td>25.</td><td class="algn">Recommended By</td><td>: </td><td><bean:write name="uniqueIdRegistrationForm" property="applicantDetails.personalData.recommentedBy"/></td>
	 </tr>
	   </logic:notEmpty>
	    <logic:notEmpty name="uniqueIdRegistrationForm" property="applicantDetails.personalData.recommentedPersonDesignation">
	   <tr>
	     <td> 26.</td><td class="algn">Recommended Person Designation</td><td>: </td><td><bean:write name="uniqueIdRegistrationForm" property="applicantDetails.personalData.recommentedPersonDesignation"/></td>
	   </tr>
	   </logic:notEmpty>
	   <logic:notEmpty name="uniqueIdRegistrationForm" property="applicantDetails.personalData.recommentedPersonMobile">
	   <tr>
	    <td> 21.</td><td class="algn">Recommended Person MobileNo</td><td>: </td><td><bean:write name="uniqueIdRegistrationForm" property="applicantDetails.personalData.recommentedPersonMobile"/></td>
	   </tr>
	   </logic:notEmpty>
	   <logic:notEmpty name="uniqueIdRegistrationForm" property="applicantDetails.personalData.nccgrades">
	   <tr>
	    <td> A.</td><td class="algn">NCC Grades</td><td>: </td><td width="10%">NCC ,</td><td><bean:write name="uniqueIdRegistrationForm" property="applicantDetails.personalData.nccgrades"/></td>
	   </tr>
	   </logic:notEmpty >
	   <logic:notEmpty name="uniqueIdRegistrationForm" property="applicantDetails.personalData.nssgrades">
	   <tr>
	    <td> B.</td><td class="algn">NSS Grades</td><td>: </td><td width="10%">NSS ,</td><td><bean:write name="uniqueIdRegistrationForm" property="applicantDetails.personalData.nssgrades"/></td>
	   </tr>
	   </logic:notEmpty >
	    <logic:notEmpty name="uniqueIdRegistrationForm" property="applicantDetails.recommedationApplicationNo">
    <tr>
	     <td>C.</td><td class="algn">Recommedation Application No</td><td>: </td><td><bean:write name="uniqueIdRegistrationForm" property="applicantDetails.recommedationApplicationNo"/></td>
	 </tr>
	   </logic:notEmpty>
	   </table></td></tr>
	   <tr>
	  <td align="center" style="font-size:medium; font-weight: bold"><font style="text-decoration: underline"><br></br>DECLARATION</font></td>
	</tr>
	
<tr>
	  <td align="left"><p>I <nested:write name="uniqueIdRegistrationForm" property="applicantDetails.personalData.firstName"></nested:write>&nbsp;
	  								<nested:notEmpty name="uniqueIdRegistrationForm" property="applicantDetails.personalData.middleName">
				                    <nested:write name="uniqueIdRegistrationForm" property="applicantDetails.personalData.middleName"></nested:write>&nbsp;</nested:notEmpty>
				                    <nested:notEmpty name="uniqueIdRegistrationForm" property="applicantDetails.personalData.lastName">
				                    <nested:write name="uniqueIdRegistrationForm" property="applicantDetails.personalData.lastName"></nested:write> </nested:notEmpty>
				                    hereby declare that the information furnished above is true to the best of my knowledge. If granted admission, I agree to abide by the rules and regulations of the College.</p><br></br></td>
	</tr>
	     <tr><td><p></p><p></p><p></p></td></tr>
	      <tr>
	        <td style="font-size:small; font-weight: bold">
	         Place:
	       </td>
	     </tr>
	       <tr><td><p></p><p></p><p></p><p></p></td></tr>
	       <tr><td><p></p><p></p><p></p><p></p></td></tr>
	       <tr><td><p></p><p></p><p></p><p></p></td></tr>
	     <tr><td>
	     <table width="100%">
	     <tr style="font-size:small; font-weight: bold">
	       <td align="left">Date: </td><td align="center">Signature of Parent/Guardian</td><td align="right">Signature of the Candidate</td>
	    </tr>
	    </table>
	    </td></tr>
	    </table>
	    <pd4ml:page_break/>
	   <hr style="size:1px;background-color: black" noshade="noshade"  align="center"></hr>
	   <table width="100%">
	        <tr class="title2">
	         <td colspan="2" align="center"><u>പ്രതിജ്ഞ</u></td>
	        </tr>
	        <tr class="title2">
	         <td colspan="2" align="left"><p>എന്നെ ഈ കോളേജിൽ ചേർക്കുന്ന പക്ഷം കോളേജിലെ നിയമങ്ങളും കോളേജ് അധികാരികളുടെ അപ്പഴപ്പോഴുള്ള നിർദേശങ്ങളും പാലിച്ചു കൊള്ളാമെന്നും കോളേജ്  നടത്തിപ്പിന് തടസ്സം വരുത്തുന്ന യാതൊരു
	          പരിപാടികളിലും കോളേജിന് അകത്തോ പുറത്തോ വച്ച് ഉൾപ്പെടുകയില്ലെന്നും ചെയ്യുന്നു.കോളേജിൽ രാഷ്ട്രീയം നിരോധിച്ചിരിക്കുന്നു എന്ന് ഞാൻ മനസ്സിലാക്കുന്നു.യാതൊരു രാഷ്ട്രീയമോ അല്ലാത്തതോ ആയ സമരങ്ങൾക്കും ഞാൻ പങ്കു  ചേരുന്നതല്ല.
	          </p></td>
	        </tr>
	        <tr class="title2">
	         <td align="left"><p>സ്ഥലം:......................</p></td>
	        </tr>
	        <tr class="title2">
	         <td align="left"><p>തിയ്യതി:......................</p></td><td align="right"><p>വിദ്യാർത്ഥിയുടെ ഒപ്പ്.</p></td>
	        </tr>
	        <tr class="title2">
	         <td colspan="2" align="left"><p>എൻ്റെ രക്ഷകർതൃത്തിലുള്ള ഈ വിദ്യാർത്ഥിയെ കോളേജിൽ ചേർക്കുന്ന പക്ഷം വിദ്യാർത്ഥിയുടെ മേൽ പറഞ്ഞ പ്രതിജ്ഞകൾ കർശനമായി പാലിക്കുന്നതിനും സ്വഭാവവും പെരുമാറ്റവും 
	         നന്നായിരിക്കുന്നതിനും വിദ്യാർത്ഥി കോളേജിന് എന്തെങ്കിലും നഷ്ടം വരുത്തുന്ന പക്ഷം അത്  പരിഹരിക്കാനുള്ള ഉത്തരവാദിത്തംവിദ്യാര്ഥിയുടെ രക്ഷകർത്താവായ ഞാൻ ............................................................ഏറ്റെടുക്കുന്നു.
	         </p></td>
	        </tr>
	        <tr class="title2">
	         <td align="left"><p>സ്ഥലം:......................</p></td>
	        </tr>
	        <tr class="title2">
	         <td align="left"><p>തിയ്യതി:......................</p></td><td align="right"><p>രക്ഷകർത്താവിന്റെ  ഒപ്പ്.</p></td>
	        </tr>
	 </table>

	<hr style="size:1px;background-color: black" noshade="noshade"  align="center"></hr>
	<table width="100%" class="smallcheck">
	        <tr class="title2">
	         <td>NB<p>Anyone,who secures admission on the basis of false information supplied will forfeit his/her 
	         seat and will be summarily dismissed from the college,as soon as the matter come to the notice of the principal.<p></td>
	        </tr>
	        <tr class="title2">
	         <td>Note<p>
	          1.A copy of the conduct certificate obtained from the head of the institution last studied should be enclosed with the application.<br>
	          2.SC/ST/OBC/OEC and converted students are to produce their community and income certificate at the time of admission.<br>
	          3.these eligible for concessions under KPCR  are to produce their family income certificates at the time of admission.<br>
	          4.applicants are to keep with them sufficient number of attested true copies of their degree mark list for their use.original submitted at the time of admission will not be returned during the time of the course.
	         <p></td>
	        </tr>
	 </table>
	    
	   <hr style="size:1px;background-color: black" noshade="noshade"  align="center"></hr>
	   <table>	
	 <tr>
	  <td align="center" class="title"><font style="text-decoration: underline">FOR OFFICE USE ONLY<br></br></font></td>
	</tr>
	 <tr>
	  <td align="left">The applicant may be admitted to the first semester ................................................................................................programme.</td>
	</tr>
	 <tr><td><br></br></td></tr>
	  <tr>
	    <td>
	      <table width="100%">
	        <tr class="title2">
	         <td align="left">Date: </td><td align="center">Principal</td><td align="right">Manager</td>
	        </tr>
	      </table>
	    </td>
	  </tr>  
	
	</table>
	
	

	
	
</html:form>

<script type="text/javascript">
	window.print();
</script>
</pd4ml:transform>