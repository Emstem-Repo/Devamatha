<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@page import="com.kp.cms.constants.CMSConstants"%>
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
<script language="JavaScript" src="js/calendar_us.js"></script>
	<link rel="stylesheet" href="css/calendar.css">
<script language="JavaScript" src="js/admission/admissionform.js"></script>
<html:form action="/AdmissionStatus" method="POST">
	<html:hidden property="method" styleId="method" />
	<html:hidden property="formName" value="admissionStatusForm" />
	<input type="hidden" name="applicationId"
		value='<bean:write name="admissionStatusForm" property="applicantDetails.applicationId" />' />
	<input type="hidden" name="courseId" value='<bean:write	name="admissionStatusForm" property="applicantDetails.course.id" />' />	
	<table  width="600" border="0"  cellpadding="3" cellspacing="0" bordercolor="#E0DFDB">		
	 <tr>
		<td align="center">
		  <img width="800" height="150"  src='<%= request.getRequestURL().toString().replace(request.getRequestURI().substring(0), request.getContextPath()).concat("/LogoServlet") %>' alt="Logo not available">
		</td>
	</tr>
	<tr>
	  <td>
	    <table width="100%">
	      <tr>
	        <td width="50%">
	          <table >
	           <tr>
	            <td>Reg. No</td><td>: </td><td><bean:write name="admissionStatusForm" property="applicantDetails.applnNo"/></td>
	           </tr>
	           <tr>
	            <td>Challan No</td><td>: </td><td><bean:write name="admissionStatusForm" property="applicantDetails.journalNo"/></td>
	           </tr>
	           <tr>
	            <td>Date</td><td>: </td><td><bean:write name="admissionStatusForm" property="applicantDetails.challanDate"/></td>
	           </tr>
	          </table>
	        </td>
	        
	        <td width="50%" >
	        <b>FOR OFFICE USE ONLY</b>
	          <table width="100%" border="1" cellpadding="3" class="mytable">
	            <tr><td width="30%">Admission No.</td><td width="70%"></td></tr>
	            <tr><td width="30%">Programme</td><td width="70%"></td></tr>
	            <tr><td width="30%">Class No.</td><td width="70%"></td></tr>
	          </table>
	        </td>
	      </tr>
	    </table>
	  </td>
	</tr>
	
	<logic:equal value="1" name="admissionStatusForm" property="applicantDetails.programType">
	<tr>
	  <td class="title" align="center"><br></br>APPLICATION FOR ADMISSION TO UNDERGRADUATE PROGRAMME - 2015<br></br></td>
	</tr>
	</logic:equal>	

	<logic:equal value="2" name="admissionStatusForm" property="applicantDetails.programType">
	<tr>
	  <td class="title" align="center"><br></br>APPLICATION FOR ADMISSION TO POSTGRADUATE PROGRAMME - 2015<br></br></td>
	</tr>
	</logic:equal>	
	
	<tr><td><p></p></td></tr>
	      <tr>
	      <td>
	        <table border="0" style="line-height: 20px;">
	        <tr>
	     <td>1.</td><td class="algn">Name</td><td>: </td><td><nested:write property="applicantDetails.personalData.firstName"></nested:write>&nbsp;
				                    <nested:write property="applicantDetails.personalData.middleName"></nested:write>&nbsp;
				                    <nested:write property="applicantDetails.personalData.lastName"></nested:write></td><td></td><td></td><td align="right" rowspan="6"><img src='<%= request.getRequestURL().toString().replace(request.getRequestURI().substring(0), request.getContextPath()).concat("/PhotoServlet") %>' height="150px" width="150px"/></td>
	   </tr>
	   
	   <tr>
	     <td>2.</td><td class="algn">Gender</td><td>: </td><td><logic:equal name="admissionStatusForm" property="applicantDetails.personalData.gender" value="MALE"><bean:message key="admissionForm.studentinfo.sex.male.text"/></logic:equal>
											<logic:equal name="admissionStatusForm" property="applicantDetails.personalData.gender" value="FEMALE"><bean:message key="admissionForm.studentinfo.sex.female.text"/></logic:equal></td>
	   </tr>
	   
	   <tr>
	     <td>3.</td><td class="algn">Date of Birth</td><td>: </td><td><nested:write property="applicantDetails.personalData.dob"></nested:write></td>
	   </tr>
	   
	   <tr>
	     <td>4.</td><td class="algn">Nationality</td><td>: </td><td><bean:write	name="admissionStatusForm" property="applicantDetails.personalData.citizenship" /></td>
	   </tr>
	   
	   <tr>
	     <td>5.</td><td class="algn">Domicile State</td><td>: </td><td><bean:write
												name="admissionStatusForm"
												property="applicantDetails.personalData.residentCategoryName" /></td>
	   </tr>
	   
	   <tr>
	     <td>6.</td><td class="algn">Religion</td><td>: </td><td><bean:write
												name="admissionStatusForm"
												property="applicantDetails.personalData.religionName" /></td>
	   </tr>
	   <tr>
	     <td>7.</td><td class="algn">Caste</td><td>: </td><td><bean:write
												name="admissionStatusForm"
												property="applicantDetails.personalData.casteCategory" /></td>
	   </tr>
	   
	   
	   <tr>
	     <td>8.</td><td class="algn">Category</td><td>: </td><td><bean:write
												name="admissionStatusForm"
												property="applicantDetails.personalData.subregligionName" /></td>
	   </tr>
	   
	   
	   <tr>
	     <td>9.</td><td class="algn">Name of Father</td><td>: </td><td><nested:write property="applicantDetails.titleOfFather"/>. 
											<nested:write property="applicantDetails.personalData.fatherName"></nested:write></td><td class="algn">Occupation</td><td>: </td><td><bean:write name="admissionStatusForm" property="applicantDetails.personalData.fatherOccupation" /></td>
	   </tr>
	   <tr>
	     <td>10.</td><td class="algn">Name of Mother</td><td>: </td><td><nested:write property="applicantDetails.titleOfMother"/>. 
											<nested:write property="applicantDetails.personalData.motherName"></nested:write></td><td class="algn">Occupation</td><td>: </td><td><bean:write
												                                                                                                    name="admissionStatusForm"
												                                                             property="applicantDetails.personalData.motherOccupation" /></td>
	   </tr>
	   
	   <tr>
	     <td>11.</td><td class="algn">Permanent Address</td><td>: </td><td><nested:write property="applicantDetails.personalData.permanentAddressLine1"></nested:write></td></tr>
	   <tr>
	   <td></td><td></td><td> </td><td class="algn"><nested:write property="applicantDetails.personalData.permanentAddressLine2"></nested:write>,<nested:write property="applicantDetails.personalData.permanentCityName"></nested:write>
										</td>
	   </tr>
	   <tr>
	     <td></td><td></td><td></td><td class="algn">
										<bean:write
												name="admissionStatusForm"
												property="applicantDetails.personalData.permanentStateName" />,
										<bean:write
												name="admissionStatusForm"
												property="applicantDetails.personalData.permanentCountryName" />,
										<nested:write property="applicantDetails.personalData.permanentAddressZipCode"></nested:write></td>
	   </tr>
	   
	   <tr>
	     <td>12.</td><td class="algn">Present Address</td><td>: </td><td><nested:write property="applicantDetails.personalData.currentAddressLine1"></nested:write></td></tr>
	   <tr>
	   <td></td><td></td><td> </td><td class="algn"><nested:write property="applicantDetails.personalData.currentAddressLine2"></nested:write>,
										<nested:write property="applicantDetails.personalData.currentCityName"></nested:write></td></tr>
										
	   
	    <tr>
	     <td></td><td></td><td></td><td class="algn"><bean:write
												name="admissionStatusForm"
												property="applicantDetails.personalData.currentStateName" />,
										<bean:write
												name="admissionStatusForm"
												property="applicantDetails.personalData.currentCountryName" />,
										<nested:write property="applicantDetails.personalData.currentAddressZipCode"></nested:write></td>
	   </tr>
	   
	   <tr>
	     <td>13.</td><td class="algn">Phone Land</td><td>: </td><td><bean:write name="admissionStatusForm" property="applicantDetails.personalData.landlineNo"/></td>
	   </tr>
	   
	   <tr>
	     <td></td><td class="algn">Mobile</td><td>: </td><td><bean:write name="admissionStatusForm" property="applicantDetails.personalData.mobileNo"/></td>
	   </tr>
	   
	   <tr>
	     <td>14.</td><td class="algn">E - Mail</td><td>: </td><td><nested:write property="applicantDetails.personalData.email"></nested:write></td>
	   </tr>
	   
	   <tr>
	     <td valign="top">15.</td><td class="algn">Eligibility for Bonus Marks</td><td>: </td><td>
	                                                                 <logic:equal value="true" name="admissionStatusForm" property="applicantDetails.personalData.nsscertificate">NSS,  </logic:equal>
	                                                                 <logic:equal value="true" name="admissionStatusForm" property="applicantDetails.personalData.ncccertificate">NCC,  </logic:equal> 
	                                                                 <logic:equal value="true" name="admissionStatusForm" property="applicantDetails.personalData.exservice">Ex-Service  </logic:equal>
	                                                               </td>
	   </tr>
	   
	   <tr>
	     <td>16.</td><td class="algn">Claim for Reservation</td><td>: </td><td>
	                                                           <logic:equal value="true" name="admissionStatusForm" property="applicantDetails.personalData.sportsPerson">Sports/Cultural, </logic:equal>

	                                                           <logic:equal value="true" name="admissionStatusForm" property="applicantDetails.personalData.handicapped"> Physically Challenged, </logic:equal> 
	                                                           
	                                                          </td>
	   </tr>
	   
	   </table>
	   </td>
	  
	</tr>
	<tr>
	  <td>
	    <table border='1' width="800" height='100' cellpadding="3" class="mytable">
           <tr class="title2">
             <td align="center" rowspan='2'>Exam</td>
             <td align="center" rowspan='2'>Reg. No.</td>
             <td align="center" rowspan='2'>Year</td>
             <td align="center" rowspan='2'>Name of Board</td>
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
          
          <nested:iterate property="applicantDetails.ednQualificationList" id="ednQualList" indexId="count">
          
          <tr>
             <td  align="center">               <logic:equal value="Class 12" name="ednQualList" property="docName">Plus Two
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
             <td  align="center"><bean:write name="ednQualList" property="previousRegNo" /></td>
             <td  align="center"><bean:write name="ednQualList" property="yearPassing" /></td>
             <td  align="center"><bean:write name="ednQualList"property="universityName" /></td>
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
	
	<logic:equal value="1" name="admissionStatusForm" property="applicantDetails.programType">
	<tr>
	  <td align="center" class="title2"><br></br>Statement of Plus Two Marks</td>
	</tr>
	</logic:equal>	
	
	<logic:equal value="2" name="admissionStatusForm" property="applicantDetails.programType">
	<tr>
	  <td align="center" class="title2"><br></br>Statement of Degree Marks</td>
	</tr>
	</logic:equal>	
	
	<tr>
	  <td>
	    <table cellpadding="3" align="center" width="80%" border="1" class="mytable">
	      <tr class="title2">
	        <td align="center">Name of Subject</td><td align="center">Marks Secured</td><td align="center">Maximum Marks</td>
	       </tr>
	      <logic:iterate id="submrks" property="applicantDetails.pucsubjectmarkto" name="admissionStatusForm">
	      <tr>
	          <td align="left"><bean:write name="submrks" property="subjectName"/></td>
	          <td  align="center"><bean:write name="submrks" property="obtainedmark" /></td>
	          <td  align="center"><bean:write name="submrks" property="maxmark" /></td>
	        </tr>
	      </logic:iterate>
	      <tr>
	        <td class="title2" align="right">Total Marks</td><td><%=obtained%></td><td><%=total%></td>
	      </tr>
	    </table>
	  </td>
	</tr>
	<tr>
	 <td>
	   <table>
	   
	   	<logic:equal value="1" name="admissionStatusForm" property="applicantDetails.programType">
	     <tr>
	       <td>17.</td><td>No. of chances taken to pass plus two</td><td>: </td><td><%=noofattempt %></td>
	     </tr>
	     </logic:equal>
	     
	   	<logic:equal value="2" name="admissionStatusForm" property="applicantDetails.programType">
	     <tr>
	       <td>17.</td><td>No. of chances taken to pass degree</td><td>: </td><td><%=noofattempt %></td>
	     </tr>
	     </logic:equal>

	     <tr><td><br></br></td></tr>
	     <tr>
	       <td>18.</td><td>Preference Details</td>
	    </tr>
	   </table>
	    </td>
    </tr>
    <tr>
      <td>
        <table cellpadding="3" width="60%" align="center" border="1" class="mytable">
           <tr class="title2">
             <td align="center">Preference No.</td><td align="center">Programme</td>
           </tr>
           <logic:iterate id="preflist" name="admissionStatusForm" property="applicantDetails.preflist">
               <tr>
                 <td  align="center"><bean:write name="preflist" property="prefNo"/></td>
                  <td><bean:write name="preflist" property="coursName"/></td>
               </tr>
           
           </logic:iterate>
        </table>
      </td>
    </tr>
	   <tr>
	  <td align="center" class="title"><font style="text-decoration: underline"><br></br>DECLARATION<br></br></font></td>
	</tr>
	
	<tr>
	  <td align="left"><p>If I am admitted to the College, I agree to abide by the rules and regulations of the College and I promise not to take part in</p><p>strikes and other agitations against the College and University.</p><br></br></td>
	</tr>
	<tr><td><p> </p></td></tr>
	      <tr>
	        <td class="title2">
	         Place:<br></br><br></br>
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
	    
	   <hr style="size:1px;background-color: black" noshade="noshade"  align="center"></hr>
	   <table>	
	 <tr>
	  <td align="center" class="title"><font style="text-decoration: underline">FOR OFFICE USE ONLY<br></br></font></td>
	</tr>
	 <tr>
	  <td align="left">The applicant may be admitted to the first semester .............................................................................................programme.</td>
	</tr>
	<tr>
	  <td>
	    <table width="100%">
	        <tr><td width="20%">Core</td><td>: </td><td>..................................................................................................</td> </tr>
	        <tr><td>Complementary</td><td>: </td><td>..................................................................................................</td></tr>
	        <tr><td></td><td>: </td><td>..................................................................................................</td></tr> 
	        <tr><td></td><td>: </td><td>..................................................................................................</td></tr>  
	        <tr><td></td><td>: </td><td>..................................................................................................</td></tr>  
	        <tr><td>Second Language</td><td>: </td><td>..................................................................................................</td></tr> 
	    </table>
	  </td>
	 </tr>
	 <tr><td><br></br><br></br></td></tr>
	  <tr>
	    <td>
	      <table width="100%">
	        <tr class="title2">
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