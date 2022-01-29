<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms" %>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<%@page import="com.kp.cms.constants.CMSConstants"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="css/styles.css" rel="stylesheet" type="text/css">
<title>Certificate Course</title>
</head>
<script type="text/javascript">
function printMe()
{
	window.print();
}
function closeMe()
{
	window.close();
}
</script>
<body>
<html:form action="/StudentCertificateCourse">

<html:hidden property="method" styleId="method" value="initPrintChallen"/>
<html:hidden property="formName" value="studentCertificateCourseForm"/>
<html:hidden property="pageType" value="3"/>
<table width="100%" border="0" >
  <tr>
    <td valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0" >
      <tr>
        <td height="20" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news">
        
        <table width="98%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td height="25"  class="row-white" valign="top" style="width: 500px;">
					<div align="center">
						<img src='<%=CMSConstants.LOGO_URL%>' alt="Logo not available" width="210" height="100">
					</div>
				</td>	
			</tr>
			
			<tr>
				<td height="25" class="row-white" valign="top" style="width: 500px;">
				<div align="center">
					<b>APPLICATION FOR CERTIFICATE COURSES</b>
				</div>
			</td>	
			</tr>
		  </table>
	  	<table height="5%">
	 	</table>
	 <table width="100%" style="border:1px solid #000000">
	 <tr>
	 <td>
		  <table width="100%" border="0">
			  <tr>
			 	<td height="25" class="row-white" width="55%">
					Name of the applicant: <bean:write name="studentCertificateCourseForm" property = "studentTO.studentName" />
				</td>	
				
				<td height="25" class="row-white" width="45%">
					<bean:write name="studentCertificateCourseForm" property = "studentTO.personalData.gender" />
				</td>
								
			 </tr>
			 </table>
			 
		 <table width="100%" border="0">
			<tr><td>&nbsp;</td></tr>
			  <tr>
			 	<td class="row-white" width="35%">
					Register Number: <bean:write name="studentCertificateCourseForm" property = "studentTO.registerNo" />
				</td>	
				<td class="row-white"  width="20%">
					Class: <bean:write name="studentCertificateCourseForm" property = "studentTO.className" />
				</td>	
				<td  class="row-white"  width="45%">
					Combination: <bean:write name="studentCertificateCourseForm" property = "studentTO.courseName" /> 
				</td>
			 </tr>
			 <tr><td>&nbsp;</td></tr>
			 <tr>
			 	<td class="row-white">
					Date of Birth: <bean:write name="studentCertificateCourseForm" property = "studentTO.dob" />
				</td>	
			 </tr>
		 	</table>
		 	</td>
			 </tr>
			 </table>
		 	<table height="2%">
		 	</table>
		 	<table width="100%" style="border:1px solid #000000">
				 <tr>
					 <td class="row-white">
						Address: <bean:write name="studentCertificateCourseForm" property = "studentTO.personalData.permanentAddressLine1" /></td>
				 </tr>
					<logic:notEmpty name="studentCertificateCourseForm" property="studentTO.personalData.permanentAddressLine2">
					 <tr><td>&nbsp;</td></tr>
						 <tr>
						 <td class="row-white">
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<bean:write name="studentCertificateCourseForm" property = "studentTO.personalData.permanentAddressLine2" />
						</td>
						</tr>	
					</logic:notEmpty>
					<tr><td>&nbsp;</td></tr>
					 <tr>
						 <td class="row-white">
							 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							 <bean:write name="studentCertificateCourseForm" property = "studentTO.countryName" />, <bean:write name="studentCertificateCourseForm" property = "studentTO.stateName" />
						</td>
					</tr>
					<tr><td>&nbsp;</td></tr>
					<tr>
						 <td class="row-white" width="55%"> Telephone: <bean:write name="studentCertificateCourseForm" property = "studentTO.personalData.phNo1" />  
							 <bean:write name="studentCertificateCourseForm" property = "studentTO.personalData.phNo2" /> 
							 <bean:write name="studentCertificateCourseForm" property = "studentTO.personalData.phNo3" />
						 </td>
						  <td class="row-white" width="45%" > Mobile: <bean:write name="studentCertificateCourseForm" property = "studentTO.personalData.mobileNo1" /> 
							 <bean:write name="studentCertificateCourseForm" property = "studentTO.personalData.mobileNo2" />  
							 <bean:write name="studentCertificateCourseForm" property = "studentTO.personalData.mobileNo3" />
						 </td>
						 
					</tr>
					<tr><td>&nbsp;</td></tr>
				<tr>
					<td class="row-white" width="50%"> Email: <bean:write name="studentCertificateCourseForm" property = "studentTO.personalData.email" /> 
					</td>
				
				</tr>
        </table>
        <table height="2%">
		 </table>
		 <table width="100%"  style="border:1px solid #000000">
	 <tr>
	 <td>
        <table width="100%" border="0">
        	<tr>
	       	 	<td class="row-white" width="50%"> Name of the Course: <bean:write name="studentCertificateCourseForm" property = "certificateCourseName" /></td>
			</tr>
			
				<tr><td>&nbsp;</td></tr>
				<tr>
		       	 	<td class="row-white" width="50%"> Coordinator of Course: 
		       	 	<logic:notEmpty name = "studentCertificateCourseForm" property="certificateCourseTeacherTO">
		       	 		<bean:write name="studentCertificateCourseForm" property = "certificateCourseTeacherTO.teacherName" />
		       	 	</logic:notEmpty>
		       	 	</td>
				</tr>
			
				<tr><td>&nbsp;</td></tr>
				<tr>
		       	 	<td class="row-white" width="50%"> Department: 
		       	 		<bean:write name="studentCertificateCourseForm" property = "studentTO.courseName" />
		       	 	</td>
				</tr>
			
			 
        </table>
        </td>
        </tr>
        </table>
        <table width="100%" border="0" height="25%">
	        <tr>
	       	 	<td class="row-white" width="50%"> Date:</td>
	       	 	<td class="row-white" width="50%" align="right"> Signature of the student</td>
			</tr>
			 
        </table>
         <table width="100%" border="0" >
	        <tr>
	       	 	<td class="row-white" align="right"> REGISTRAR</td>
			</tr>
			 
        </table>
        
         <table width="100%" border="0" height="15%">
	        <tr>
	       	 	<td class="row-white" width="50%" align="center">FOR OFFICE USE ONLY</td>
			</tr>
			<tr>
	       	 	<td class="row-white" width="50%" >Admission Decision:(Admitted/Not Admitted) Fees Paid:....................&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Date:...................</td>
			</tr>
        </table>
        <table width="100%" border="0" >
       		 <tr>
	       	 	<td class="row-white" width="50%">&nbsp;</td>
			</tr>
			
			<tr>
	       	 	<td class="row-white" width="50%">&nbsp;</td>
			</tr>
			
	        <tr>
	       	 	<td class="row-white" width="50%">Receiving Authority</td>
			</tr>
			
        </table>
        </td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="20" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news">&nbsp;</td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      
    </table></td>
  </tr>
</table>

</html:form>
</body>
<script type="text/javascript">
	window.print();
</script>
