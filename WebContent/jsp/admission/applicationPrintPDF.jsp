<%@taglib prefix="pd4ml" uri="http://pd4ml.com/tlds/pd4ml/2.6"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>

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
<html:form action="/AdmissionStatus" method="POST">
	<html:hidden property="method" styleId="method" />
	<html:hidden property="formName" value="admissionFormForm" />
	<input type="hidden" name="applicationId"
		value='<bean:write name="admissionFormForm" property="applicantDetails.applicationId" />' />
	<input type="hidden" name="courseId" value='<bean:write	name="admissionFormForm" property="applicantDetails.course.id" />' />	
	<table  width="600" border="0"  cellpadding="3" cellspacing="0" bordercolor="#E0DFDB">		
	 <tr>
		<td align="center">
		 <img width="120" height="120"  src='<%= request.getRequestURL().toString().replace(request.getRequestURI().substring(0), request.getContextPath()).concat("/LogoServlet") %>' alt="Logo not available">
		 
		
		
		</td>
	</tr>
	<tr>
	  <td>
	    <table width="100%">
	      <tr>
	        <td width="50%">
	          <table >
	           <tr>
	            <td>Appl. No</td><td>: </td><td><bean:write name="admissionFormForm" property="applicantDetails.applnNo"/></td>
	           </tr>
	           <tr>
	            <td>Challan No</td><td>: </td><td><bean:write name="admissionFormForm" property="applicantDetails.journalNo"/></td>
	           </tr>
	           <tr>
	            <td>Date</td><td>: </td><td><bean:write name="admissionFormForm" property="applicantDetails.challanDate"/></td>
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
	
	<logic:equal value="1" name="admissionFormForm" property="applicantDetails.programType">
	<tr>
	  <td style="font-size:medium; font-weight: bold" align="center"><br></br>APPLICATION FOR ADMISSION TO UNDERGRADUATE PROGRAMME - <bean:write name="admissionFormForm" property="applicantDetails.appliedYear"/><br></br></td>
	</tr>
	</logic:equal>
	
	<logic:equal value="2" name="admissionFormForm" property="applicantDetails.programType">
	<tr>
	  <td style="font-size:medium; font-weight: bold" align="center"><br></br>APPLICATION FOR ADMISSION TO POSTGRADUATE PROGRAMME - <bean:write name="admissionFormForm" property="applicantDetails.appliedYear"/><br></br></td>
	</tr>
	</logic:equal>   
	   <tr><td><p></p></td></tr>
	
	      <tr>
	      <td>
	        <table border="0" style="line-height: 20px;">
	        <tr>
	     <td>1.</td><td height="25px">Name</td><td>: </td><td><nested:write name="admissionFormForm" property="applicantDetails.personalData.firstName"></nested:write>&nbsp;
				                    <nested:write name="admissionFormForm" property="applicantDetails.personalData.middleName"></nested:write>&nbsp;
				                    <nested:write name="admissionFormForm" property="applicantDetails.personalData.lastName"></nested:write></td><td></td><td></td><td align="right" rowspan="6"><img src='<%= request.getRequestURL().toString().replace(request.getRequestURI().substring(0), request.getContextPath()).concat("/PhotoServlet") %>' height="150px" width="150px"/></td>
	   </tr>
	   
	   <tr>
	     <td>2.</td><td height="25px">Gender</td><td>: </td><td><logic:equal name="admissionFormForm" property="applicantDetails.personalData.gender" value="MALE"><bean:message key="admissionForm.studentinfo.sex.male.text"/></logic:equal>
											<logic:equal name="admissionFormForm" property="applicantDetails.personalData.gender" value="FEMALE"><bean:message key="admissionForm.studentinfo.sex.female.text"/></logic:equal></td>
	   </tr>
	   
	   <tr>
	     <td>3.</td><td height="25px">Date of Birth</td><td>: </td><td><nested:write name="admissionFormForm" property="applicantDetails.personalData.dob"></nested:write></td>
	   </tr>
	   
	   <tr>
	     <td>4.</td><td height="25px">Nationality</td><td>: </td><td><bean:write name="admissionFormForm" property="applicantDetails.personalData.citizenship"/></td>
	   </tr>
	   
	   <tr>
	     <td>5.</td><td height="25px">Domicile State</td><td>: </td><td><bean:write
												name="admissionFormForm"
												property="applicantDetails.personalData.residentCategoryName" /></td>
	   </tr>
	   
	   <tr>
	     <td>6.</td><td height="25px">Religion</td><td>: </td><td><bean:write
												name="admissionFormForm"
												property="applicantDetails.personalData.religionName" /></td>
	   </tr>
	   <tr>
	     <td>7.</td><td height="25px">Caste</td><td>: </td><td><bean:write
												name="admissionFormForm"
												property="applicantDetails.personalData.casteCategory" /></td>
	   </tr>
	   
	   <tr>
	     <td>8.</td><td class="algn">Category</td><td>: </td><td><bean:write
												name="admissionFormForm"
												property="applicantDetails.personalData.subregligionName" /></td>
	   </tr>
	   
	   
	   <tr>
	     <td>9.</td><td height="25px">Name of Father</td><td>: </td><td><nested:write name="admissionFormForm" property="applicantDetails.titleOfFather"/>. 
											<nested:write name="admissionFormForm" property="applicantDetails.personalData.fatherName"></nested:write></td><td height="25px">Occupation</td><td>: </td><td><bean:write name="admissionFormForm" property="applicantDetails.personalData.fatherOccupation" /></td>
	   </tr>
	   <tr>
	     <td>10.</td><td height="25px">Name of Mother</td><td>: </td><td><nested:write name="admissionFormForm" property="applicantDetails.titleOfMother"/>. 
											<nested:write name="admissionFormForm" property="applicantDetails.personalData.motherName"></nested:write></td><td height="25px">Occupation</td><td>: </td><td><bean:write
												                                                                                                    name="admissionFormForm"
												                                                             property="applicantDetails.personalData.motherOccupation" /></td>
	   </tr>
	   
	   <tr>
	     <td>11.</td><td height="25px">Permanent Address</td><td>: </td><td><nested:write name="admissionFormForm" property="applicantDetails.personalData.permanentAddressLine1"></nested:write></td></tr>
	   <tr>
	   <td></td><td></td><td> </td><td height="25px"><nested:write name="admissionFormForm" property="applicantDetails.personalData.permanentAddressLine2"></nested:write>,<nested:write name="admissionFormForm" property="applicantDetails.personalData.permanentCityName"></nested:write>
										</td>
	   </tr>
	   <tr>
	     <td></td><td></td><td></td><td height="25px">
										<bean:write
												name="admissionFormForm"
												property="applicantDetails.personalData.permanentStateName" />,
										<bean:write
												name="admissionFormForm"
												property="applicantDetails.personalData.permanentCountryName" />,
										<nested:write name="admissionFormForm" property="applicantDetails.personalData.permanentAddressZipCode"></nested:write></td>
	   </tr>
	   
	   <tr>
	     <td>12.</td><td height="25px">Present Address</td><td>: </td><td><nested:write name="admissionFormForm" property="applicantDetails.personalData.currentAddressLine1"></nested:write></td></tr>
	   <tr>
	   <td></td><td></td><td> </td><td height="25px"><nested:write name="admissionFormForm" property="applicantDetails.personalData.currentAddressLine2"></nested:write>,
										<nested:write name="admissionFormForm" property="applicantDetails.personalData.currentCityName"></nested:write></td></tr>
										
	   
	    <tr>
	     <td></td><td></td><td></td><td height="25px"><bean:write
												name="admissionFormForm"
												property="applicantDetails.personalData.currentStateName" />,
										<bean:write
												name="admissionFormForm"
												property="applicantDetails.personalData.currentCountryName" />,
										<nested:write name="admissionFormForm" property="applicantDetails.personalData.currentAddressZipCode"></nested:write></td>
	   </tr>
	   
	   <tr>
	     <td>13.</td><td height="25px">Land Phone</td><td>: </td><td><bean:write name="admissionFormForm" property="applicantDetails.personalData.landlineNo"/></td>
	   </tr>
	   
	   <tr>
	     <td></td><td height="25px">Mobile</td><td>: </td><td><bean:write name="admissionFormForm" property="applicantDetails.personalData.mobileNo"/></td>
	   </tr>
	   
	   <tr>
	     <td>14.</td><td height="25px">E - Mail</td><td>: </td><td><nested:write name="admissionFormForm" property="applicantDetails.personalData.email"></nested:write></td>
	   </tr>
	   
	   <tr>
	     <td valign="top">15.</td><td height="25px">Eligibility for Bonus Marks</td><td>: </td><td>
	                                                                 <logic:equal value="true" name="admissionFormForm" property="applicantDetails.personalData.nsscertificate">NSS, </logic:equal>
	                                                                 <logic:equal value="true" name="admissionFormForm" property="applicantDetails.personalData.ncccertificate">NCC, </logic:equal> 
	                                                                 <logic:equal value="true" name="admissionFormForm" property="applicantDetails.personalData.exservice">Ex-Service </logic:equal>
	                                                               </td>
	   </tr>
	   
	   <tr>
	     <td>16.</td><td class="algn">Claim for Reservation</td><td>: </td><td>
	                                                           <logic:equal value="true" name="admissionFormForm" property="applicantDetails.personalData.sportsPerson">Sports/Cultural, </logic:equal>

	                                                           <logic:equal value="true" name="admissionFormForm" property="applicantDetails.personalData.handicapped"> Physically Challenged, </logic:equal> 
	                                                           
	                                                          </td>
	   </tr>
	   
	   </table>
	   </td>
	  
	</tr>
	<tr>
	  <td>
	    <table border='1' width="800" height='100' cellpadding="3" style="border-collapse: collapse; border: 1px solid #0000;">
           <tr style="font-size:small; font-weight: bold">
             <td align="center" rowspan='2' style="border:1px solid black; border-collapse: collapse ">Exam</td>
             <td align="center" rowspan='2' style="border:1px solid black; border-collapse: collapse ">Reg. No.</td>
             <td align="center" rowspan='2' style="border:1px solid black; border-collapse: collapse ">Year</td>
             <td align="center" rowspan='2' style="border:1px solid black; border-collapse: collapse ">Name of Board</td>
             <td align="center" colspan='3' style="border:1px solid black; border-collapse: collapse ">Marks</td>
           </tr>
           <tr style="font-size:small; font-weight: bold">
             <td align="center" style="border:1px solid black; border-collapse: collapse ">%</td>
             <td align="center" style="border:1px solid black; border-collapse: collapse ">Secured</td>
             <td align="center" style="border:1px solid black; border-collapse: collapse ">Total</td>
          </tr>
          <%String noofattempt = ""; 
            String total = "";
            String obtained = "";
          %>
          <nested:iterate name="admissionFormForm" property="applicantDetails.ednQualificationList" id="ednQualList" indexId="count">
          
          <tr>
             <td  align="center" style="border:1px solid black; border-collapse: collapse ">
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
											    
												<logic:equal value="Class 10" name="ednQualList" property="docName">X</logic:equal>
											</td>
             <td  align="center" style="border:1px solid black; border-collapse: collapse "><bean:write name="ednQualList" property="previousRegNo" /></td>
             <td  align="center" style="border:1px solid black; border-collapse: collapse "><bean:write name="ednQualList" property="yearPassing" /></td>
             <td  align="center" style="border:1px solid black; border-collapse: collapse "><bean:write name="ednQualList"property="universityName" /></td>
             <td  align="center" style="border:1px solid black; border-collapse: collapse "><bean:write name="ednQualList" property="percentage" /></td>
             <td  align="center" style="border:1px solid black; border-collapse: collapse "><bean:write name="ednQualList" property="marksObtained" /></td>
             <td  align="center" style="border:1px solid black; border-collapse: collapse "><bean:write name="ednQualList" property="totalMarks" /></td>
             
         </tr>
         </nested:iterate>
         
        </table>
	  </td>
	</tr>
	
	
	</table>
	
	
	
	<p style="page-break-after:always;"> </p>
	
	<table style="line-height: 15px">
	
	<logic:equal value="1" name="admissionFormForm" property="applicantDetails.programType">
	<tr>
	  <td align="center" style="font-size:small; font-weight: bold"><br></br>Statement of Plus Two Marks</td>
	</tr>
	</logic:equal>
	
	<logic:equal value="2" name="admissionFormForm" property="applicantDetails.programType">
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
	      <logic:iterate id="submrks" property="applicantDetails.pucsubjectmarkto" name="admissionFormForm">
	      <tr>
	          <td align="left" style="border:1px solid black; border-collapse: collapse "><bean:write name="submrks" property="subjectName"/></td>
	          <td  align="center" style="border:1px solid black; border-collapse: collapse "><bean:write name="submrks" property="obtainedmark" /></td>
	          <td  align="center" style="border:1px solid black; border-collapse: collapse "><bean:write name="submrks" property="maxmark" /></td>
	        </tr>
	      </logic:iterate>
	      <tr>
	        <td style="font-size:small; font-weight: bold" align="right" style="border:1px solid black; border-collapse: collapse ">Total Marks</td><td style="border:1px solid black; border-collapse: collapse "><%=obtained%></td><td style="border:1px solid black; border-collapse: collapse "><%=total%></td>
	      </tr>
	    </table>
	  </td>
	</tr>
	<tr>
	 <td>
	   <table>
	   
	   	 <logic:equal value="1" name="admissionFormForm" property="applicantDetails.programType">
	     <tr>
	       <td>17.</td><td>No. of chances taken to pass plus two</td><td>: </td><td><%=noofattempt %></td>
	     </tr>
	     </logic:equal>

	   	 <logic:equal value="2" name="admissionFormForm" property="applicantDetails.programType">
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
           <logic:iterate id="preflist" name="admissionFormForm" property="applicantDetails.preflist">
               <tr>
                 <td  align="center" style="border:1px solid black; border-collapse: collapse "><bean:write name="preflist" property="prefNo"/></td>
                  <td style="border:1px solid black; border-collapse: collapse "><bean:write name="preflist" property="coursName"/></td>
               </tr>
           
           </logic:iterate>
        </table>
      </td>
    </tr>
	   <tr>
	  <td align="center" style="font-size:medium; font-weight: bold"><font style="text-decoration: underline"><br></br>DECLARATION</font></td>
	</tr>
	
<tr>
	  <td align="left"><p>I hereby declare that the information furnished above is true to the best of my knowledge. If granted admission, I agree to abide by the rules and regulations of the College.</p><br></br></td>
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
	    
	   <hr style="size:1px;background-color: black" noshade="noshade"  align="center"></hr>
	   <table>	
	 <tr>
	  <td align="center" style="font-size:medium; font-weight: bold"><font style="text-decoration: underline">FOR OFFICE USE ONLY<br></br></font></td>
	</tr>
	<tr><td><p></p></td></tr>
	 <tr>
	  <td align="left">The applicant may be admitted to the first semester .............................................................................................programme.</td>
	</tr>
	<tr>
	  <td>
	    <table width="100%">
	       <%--  <tr><td width="20%">Core</td><td>: </td><td>..................................................................................................</td> </tr>
	        <tr><td>Complementary</td><td>: </td><td>..................................................................................................</td></tr>
	        <tr><td></td><td>: </td><td>..................................................................................................</td></tr> 
	        <tr><td></td><td>: </td><td>..................................................................................................</td></tr>  
	        <tr><td></td><td>: </td><td>..................................................................................................</td></tr>  
	       --%> <tr><td>Second Language</td><td>: </td><td>..................................................................................................</td></tr> 
	    </table>
	  </td>
	 </tr>
	
	<tr><td><p></p><p></p><p></p><p></p></td></tr>
	<tr><td><p></p><p></p><p></p><p></p></td></tr>
	<tr><td><p></p><p></p><p></p><p></p></td></tr>
	
	  <tr>
	    <td>
	      <table width="100%">
	        <tr style="font-size:small; font-weight: bold">
	         <td align="left">Date: </td><td align="center">Head of the Department</td><td align="right">Principal</td>
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