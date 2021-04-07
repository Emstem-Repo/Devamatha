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
.mytabledata
{
	font-size: 12px;
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
	<table width="100%" border="0"  cellpadding="3" cellspacing="0" bordercolor="#E0DFDB">		
	 <tr>
		<td align="center">
		<%-- 
		  <img width="600" height="150"  src='<%= request.getRequestURL().toString().replace(request.getRequestURI().substring(0), request.getContextPath()).concat("/LogoServlet") %>' alt="Logo not available">
		  --%> <img width="600" height="150"  src="images/admission/images/headerlogo.png" alt="Logo not available">
		</td>
	</tr>	
	<tr><td><br></br></td></tr>
	<tr>
	  <td class="title" align="center">ADMISSION MEMO</td>
	</tr>
	<tr>
	  <td align="center" class="mytabledata">Memo to be Produced Before the Principal for Admission<br></br></td>
	</tr>
	<tr><td width="100%"><table width="100%">
	
	<tr>
	     <td align="left" class="mytabledata">Appln. No :  <bean:write name="admissionStatusForm" property="applicantDetails.applnNo"/></td>
	     <td align="center" class="mytabledata"></td>
	     <td align="right" class="mytabledata"></td>
	   </tr>
	
	</table></td></tr>	
	<tr>
	 <td>
	 <table cellpadding="3" width="100%" border="0">
	   
	   <tr>
	     <td class="mytabledata">Name</td><td>: </td><td class="mytabledata"><nested:write property="applicantDetails.personalData.firstName"></nested:write>&nbsp;
				                    <nested:write property="applicantDetails.personalData.middleName"></nested:write>&nbsp;
				                    <nested:write property="applicantDetails.personalData.lastName"></nested:write></td><td></td><td></td><td rowspan="6"><img src='<%= request.getRequestURL().toString().replace(request.getRequestURI().substring(0), request.getContextPath()).concat("/PhotoServlet") %>'  height="150px" width="150px" /></td>
	   </tr>
	   
	   <tr>
	     <td class="mytabledata">Gender</td><td class="mytabledata">: </td><td class="mytabledata"><logic:equal name="admissionStatusForm" property="applicantDetails.personalData.gender" value="MALE"><bean:message key="admissionForm.studentinfo.sex.male.text"/></logic:equal>
											<logic:equal name="admissionStatusForm" property="applicantDetails.personalData.gender" value="FEMALE"><bean:message key="admissionForm.studentinfo.sex.female.text"/></logic:equal></td><td></td><td></td>
	   </tr>
	   
	   <tr>
	     <td class="mytabledata">Date of Birth</td><td class="mytabledata">: </td><td class="mytabledata"><nested:write property="applicantDetails.personalData.dob"></nested:write></td><td></td><td></td>
	   </tr>
	   
	   <tr>
	     <td class="mytabledata">Nationality</td><td class="mytabledata">: </td><td class="mytabledata"><bean:write	name="admissionStatusForm" property="applicantDetails.personalData.citizenship" /></td><td></td><td></td>
	   </tr>
	   
	   <tr>
	     <td class="mytabledata">Domicile State</td><td class="mytabledata">: </td><td class="mytabledata"><bean:write
												name="admissionStatusForm"
												property="applicantDetails.personalData.residentCategoryName" /></td><td></td><td></td>
	   </tr>
	   
	   <tr>
	     <td class="mytabledata">Religion</td><td class="mytabledata">: </td><td class="mytabledata"><bean:write
												name="admissionStatusForm"
												property="applicantDetails.personalData.religionName" /></td><td></td><td></td><td></td>
	   </tr>
	   
	   <tr>
	     <td class="mytabledata">Caste</td><td class="mytabledata">: </td><td class="mytabledata"><bean:write
												name="admissionStatusForm"
												property="applicantDetails.personalData.casteCategory" /></td><td></td><td></td><td></td>
	   </tr>
	   
	   <tr>
	   	<td class="mytabledata">Category</td><td class="mytabledata">: </td><td class="mytabledata"><bean:write
												name="admissionStatusForm"
												property="applicantDetails.personalData.subregligionName" /></td><td></td><td></td><td></td>
	   </tr>
	   
	   <tr>
	     <td class="mytabledata">Mobile</td><td class="mytabledata">: </td><td class="mytabledata"><bean:write name="admissionStatusForm" property="applicantDetails.personalData.mobileNo"/></td><td class="mytabledata"></td><td class="mytabledata"></td><td class="mytabledata"></td>
	   </tr>
	   
	   
	   <tr>
	     <td class="mytabledata">E - Mail</td><td class="mytabledata">: </td><td class="mytabledata"><nested:write property="applicantDetails.personalData.email"></nested:write></td><td></td><td></td><td></td>
	   </tr>
	  
	 </table>
	 </td>
	</tr>
	
	
          <%String noofattempt = ""; 
            String total = "";
            String obtained = "";
            String regno = "";
            String board = "";
            String year = "";
          %>
          <nested:iterate property="applicantDetails.ednQualificationList" id="ednQualList" indexId="count">
          
                                       
											    <logic:equal value="Class 12" name="ednQualList" property="docName">
											    <bean:define id="noofAtt" name="ednQualList" property="noOfAttempts"></bean:define>
											    <bean:define id="totalm" name="ednQualList" property="totalMarks"></bean:define>
											    <bean:define id="obtainedm" name="ednQualList" property="marksObtained"></bean:define>
											    <bean:define id="previousRegNom" name="ednQualList" property="previousRegNo"></bean:define>
											    <bean:define id="universityNamem" name="ednQualList" property="universityName"></bean:define>
											    <bean:define id="yearPassingm" name="ednQualList" property="yearPassing"></bean:define>
											    <%
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
											    if(previousRegNom!=null)
											    {
											    	regno =previousRegNom.toString(); 
											    }
											    if(universityNamem!=null)
											    {
											    	board =universityNamem.toString(); 
											    }
											    if(yearPassingm!=null)
											    {
											    	year =yearPassingm.toString(); 
											    }
											    %>
											    </logic:equal>
											    
											    <logic:equal value="DEG" name="ednQualList" property="docName">
											    <bean:define id="noofAtt" name="ednQualList" property="noOfAttempts"></bean:define>
											    <bean:define id="totalm" name="ednQualList" property="totalMarks"></bean:define>
											    <bean:define id="obtainedm" name="ednQualList" property="marksObtained"></bean:define>
											    <bean:define id="previousRegNom" name="ednQualList" property="previousRegNo"></bean:define>
											    <bean:define id="universityNamem" name="ednQualList" property="universityName"></bean:define>
											    <bean:define id="yearPassingm" name="ednQualList" property="yearPassing"></bean:define>
											    <%
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
											    if(previousRegNom!=null)
											    {
											    	regno =previousRegNom.toString(); 
											    }
											    if(universityNamem!=null)
											    {
											    	board =universityNamem.toString(); 
											    }
											    if(yearPassingm!=null)
											    {
											    	year =yearPassingm.toString(); 
											    }
											    %>
											    </logic:equal>
											    
											
	    </nested:iterate>
	    
	<tr><td>
	  <table>
	   	<tr><td><p></p></td></tr>	   
	        <tr>
	 <td class="mytabledata">Board of Qualifying Examination</td><td class="mytabledata">:</td><td class="mytabledata"><%=board %></td>
	</tr>
	<tr>
	 <td class="mytabledata">Year of Passing and Reg. No</td><td class="mytabledata">:</td><td class="mytabledata"><%=year %> , <%=regno %></td>
	</tr>
	<tr><td class="mytabledata">Bonus Mark</td><td class="mytabledata">:</td>
	<td class="mytabledata"><bean:write name="admissionStatusForm" property="applicantDetails.bonsmark"/></td></tr>
	<tr><td class="mytabledata">Handicap Mark</td><td class="mytabledata">:</td>
	<td class="mytabledata"><bean:write name="admissionStatusForm" property="applicantDetails.handicapmark"/></td></tr> 
	  
	  </table>	  
	</td></tr>   
	</table>	
	
	<table width="100%" border="0"  cellpadding="3" cellspacing="0" bordercolor="#E0DFDB">
	
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
	    <table cellpadding="3" align="center" width="100%" height="200px" border="1" class="mytable">
	      <tr class="title2">
	        <td align="center" height="2px">Name of Subject</td><td align="center" height="2px">Marks Secured</td><td align="center" height="2px">Maximum Marks</td>
	       </tr>
	      <logic:iterate id="submrks" property="applicantDetails.pucsubjectmarkto" name="admissionStatusForm">
	      <tr>
	          <td align="left" class="mytabledata"><bean:write name="submrks" property="subjectName"/></td>
	          <td align="center" class="mytabledata"><bean:write name="submrks" property="obtainedmark" /></td>
	          <td align="center" class="mytabledata"><bean:write name="submrks" property="maxmark" /></td>

	        </tr>
	      </logic:iterate>
	      <tr>
	        <td class="title2" align="right">Total Marks</td><td align="center" class="mytabledata"><%=obtained%></td><td align="center" class="mytabledata"><%=total%></td>
	      </tr>
	    </table>
	  </td>
	 </tr>  
</table>  
<p style="page-break-after:always;"> </p>
<table width="100%" border="0"  cellpadding="3" cellspacing="0" bordercolor="#E0DFDB">  
	<tr>
	  <td align="center" class="title2">ADMISSION DETAILS</td>
	</tr>
    <tr>
      <td>
        <table cellpadding="3" width="100%" align="center" border="1" class="mytable">
           <tr class="title2">
             <td align="center">Preference No.</td><td align="center">Programme</td><td align="center">Category</td>
        <%--     <td align="center">Status</td><td align="center">Fees</td><td align="center">Date and Time</td>   --%> 
           </tr>
  			<tr>
            	<td align="center" class="mytabledata"><bean:write name="admissionStatusForm" property="admissionStatusTO.pref"/></td><td align="center" class="mytabledata"><bean:write name="admissionStatusForm" property="admissionStatusTO.currentcourse"/></td><td align="center" class="mytabledata"><bean:write name="admissionStatusForm" property="admissionStatusTO.altmntcategory"/></td>
        <%--    	<td align="center" class="mytabledata">-</td>
            	<td align="center" class="mytabledata">-</td>
            	<td align="center" class="mytabledata">-</td>--%> 
          </tr>           
 <%--         <logic:notEmpty  name="admissionStatusForm" property="admissionStatusTO.memoList">
           <logic:iterate id="memo" name="admissionStatusForm" property="admissionStatusTO.memoList">
           <tr>
            <td align="center" class="mytabledata"><bean:write name="memo" property="prefNo"/></td><td align="center" class="mytabledata"><bean:write name="memo" property="coursName"/></td><td align="center" class="mytabledata"><bean:write name="memo" property="category"/></td><td align="center" class="mytabledata"><bean:write name="memo" property="status"/></td><td align="center" class="mytabledata"><bean:write name="memo" property="fees"/></td><td align="center" class="mytabledata"><bean:write name="memo" property="dateAndTime"/></td>
          </tr> 
           </logic:iterate>
           </logic:notEmpty>
 --%>                    

	
        </table>
      </td>
    </tr>
   
	 <tr>
	  <td align="left" class="mytabledata">The admission secured by you is based on the details furnished online. The original documents relating to the above must be submitted before the Principal at the time of admission. If discrepancies are detected at the time of admission or course period, your admission will be cancelled.<br></br></td>
	</tr>
	<tr>
	  <td>
	    <table width="100%">
	        <tr class="title2">
	       <td align="left">Signature of Parent/Guardian</td><td align="right">Signature of the Candidate</td>
	    </tr>
	    <tr><td><br></br></td></tr>
	     <tr class="title2">
	       <td align="left" width="73%">Verified by</td><td align="left">Date:</td>
	    </tr>
	    
	    
	    </table>
	    
	    
	    
	  
	  </td>
	</tr>
</table>
</html:form>
<script type="text/javascript">
	window.print();
</script>