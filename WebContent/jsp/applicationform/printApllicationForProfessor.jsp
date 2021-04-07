<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@ taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<html lang="en">

<head>
<meta charset="UTF-8">
<title>Knowledge Pro</title>

<script type="text/javascript" src="js/ajax/AjaxUtil.js"></script>
<script type="text/javascript" src="js/ajax/Ajax.js"></script>

<script src="https://ajax.aspnetcdn.com/ajax/jQuery/jquery-3.4.1.min.js"
	type="text/javascript"></script>

<link
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css"
	rel="stylesheet" type="text/css" />
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.3.0/css/datepicker.css"
	rel="stylesheet" type="text/css" />

<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.3.0/js/bootstrap-datepicker.js"></script>


<script
	src='http://cdnjs.cloudflare.com/ajax/libs/bootstrap-validator/0.4.5/js/bootstrapvalidator.min.js'></script>
<script type="text/javascript"
	src="js/admission/UniqueIdRegistrationPage.js"></script>


<style type="text/css">
.divider {
  height: 1px;
  width:100%;
  display:block;
  margin: 9px 0;
  overflow: hidden;
  background-color: #e5e5e5;
}

.style1{
	border-top: 1px solid #8c8b8b;
}

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
 	padding:25px;	
	
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

@media print {
    .pagebreak { page-break-before: always; } /* page-break-after works, as well */
}

</style>

</head>

<body>


<html:form action="applicationFormRegistration" method="post">
<html:hidden property="formName" value="applicationRegistrationForm"/>

<table width="100%" border="0" cellpadding="3" cellspacing="0" bordercolor="#E0DFDB">


		<tr>
			<td class="title" align="center">
			<h3><b>Bharata Mata College, Thrikkakara, Kochi-682021</b></h3>
			</td>
		</tr>
		<tr class="divider"></tr>
		

		<tr>
			<td align="center" class="mytabledata">
			<h4><b>Application for Appointment as Assistant Professor</b><br></br></h4>
			</td>
		</tr>
		<tr class="divider"></tr>

		<tr>
			<td width="100%">
			<table width="100%" border="0">
				<tr>
					<td class="mytabledata">Name</td>
					<td>:</td>
					<td class="mytabledata"><bean:write
						name="applicationRegistrationForm" property="firstName" /></td>

					<td class="mytabledata">Department</td>
					<td>:</td>
					<td class="mytabledata"><bean:write
						name="applicationRegistrationForm" property="departmentName" />
					</td>

				</tr>
				
				<tr>
					<td class="mytabledata">Email address</td>
					<td>:</td>
					<td class="mytabledata"><bean:write
						name="applicationRegistrationForm" property="emailId" /></td>

					<td class="mytabledata">Mobile number</td>
					<td>:</td>
					<td class="mytabledata"><bean:write
						name="applicationRegistrationForm" property="mobileNo" />
					</td>

				</tr>
				
				<tr>
					<td class="mytabledata">Date of Birth</td>
					<td>:</td>
					<td class="mytabledata"><bean:write
						name="applicationRegistrationForm" property="dateOfBirth" /></td>

					<td class="mytabledata">Age</td>
					<td>:</td>
					<td class="mytabledata"><bean:write
						name="applicationRegistrationForm" property="age" />
					</td>

				</tr>
				
				<tr>
					<td class="mytabledata">Religion</td>
					<td>:</td>
					<td class="mytabledata"><div id="religionId"><bean:write
						name="applicationRegistrationForm" property="religionName" /></div></td>

					<td class="mytabledata">Caste</td>
					<td>:</td>
					<td class="mytabledata"><div id="casteId"><bean:write
						name="applicationRegistrationForm" property="casteName" /></div>
					</td>

				</tr>
				
				<tr id="subCaste">
					<td class="mytabledata">Diocese</td>
					<td>:</td>
					<td class="mytabledata"><bean:write
						name="applicationRegistrationForm" property="diocese" /></td>
				</tr>
				
				<tr>
					<td class="mytabledata">Address</td>
					<td>:</td>
					<td class="mytabledata"><bean:write
						name="applicationRegistrationForm" property="address" /></td>
				</tr>
				
				
				<tr>
					<td class="mytabledata">Marital Status</td>
					<td>:</td>
					<td class="mytabledata"><bean:write
						name="applicationRegistrationForm" property="maritalStatus" /></td>
				</tr>
				
				<tr>
					<td class="mytabledata">Category</td>
					<td>:</td>
					<td class="mytabledata"><bean:write
						name="applicationRegistrationForm" property="category" /></td>
				</tr>

			</table>

			</td>
		</tr>
		
		<tr class="divider"></tr> 
       <tr>
       <td width="100%">
       <table  width="100%" border="0">

				<tr>
					<td>
					<h5><b>Academic Qualifications</b></h5>
					</td>

				</tr>

				<nested:iterate name="applicationRegistrationForm" id="educationList" property="educationalDetails" indexId="count">
					<logic:equal value="1" name="educationList" property="qualificationId">
						<tr>
							<td class="mytabledata" style="text-indent: 5em;"><b>Under Graduation</b></td>
						</tr>
						

						<tr>
							<td style="padding-left:160px" class="mytabledata">Subject</td>
							<td>:</td>
							<td class="mytabledata"><bean:write
								name="educationList" property="subjectName" /></td>
						</tr>
						
						<tr>
							<td style="padding-left:160px" class="mytabledata">College</td>
							<td>:</td>
							<td class="mytabledata"><bean:write
								name="educationList" property="collegeName" /></td>
						</tr>
						
						<tr>
							<td style="padding-left:160px" class="mytabledata">University</td>
							<td>:</td>
							<td class="mytabledata"><bean:write
								name="educationList" property="universityName" /></td>
						</tr>
						
						<tr>
							<td style="padding-left:160px" class="mytabledata">Percentage</td>
							<td>:</td>
							<td class="mytabledata"><bean:write
								name="educationList" property="percentage" /></td>
						</tr>
					</logic:equal>
					
					<logic:equal value="2" name="educationList" property="qualificationId">
						<tr align="left">
							<td class="mytabledata" style="text-indent: 5em;"><b>Post Graduation</b></td>
						</tr>

						<tr>
							<td style="padding-left:160px" class="mytabledata">Subject</td>
							<td>:</td>
							<td class="mytabledata"><bean:write
								name="educationList" property="subjectName" /></td>
						</tr>
						
						<tr >
							<td style="padding-left:160px" class="mytabledata">College</td>
							<td>:</td>
							<td class="mytabledata"><bean:write
								name="educationList" property="collegeName" /></td>
						</tr>
						
						<tr>
							<td style="padding-left:160px" class="mytabledata">University</td>
							<td>:</td>
							<td class="mytabledata"><bean:write
								name="educationList" property="universityName" /></td>
						</tr>
						
						<tr>
							<td style="padding-left:160px" class="mytabledata">Percentage</td>
							<td>:</td>
							<td class="mytabledata"><bean:write
								name="educationList" property="percentage" /></td>
						</tr>
					</logic:equal>
					
					
					<logic:equal value="3" name="educationList" property="qualificationId">
						<tr align="left">
							<td class="mytabledata" style="text-indent: 5em;"><b>Rank Position</b></td>
							<td>:</td>
							<td class="mytabledata"><bean:write
								name="educationList" property="rankPosition" /></td>
						</tr>
					</logic:equal>
					
					
					
					
					
					<logic:equal value="4" name="educationList" property="qualificationId">
					<logic:equal value="true" name="educationList" property="isMphil">
						<tr align="left">
							<td class="mytabledata" style="text-indent: 5em;"><b>M.Phil</b></td>
						</tr>

						<tr >
							<td width="20" style="padding-left:160px" class="mytabledata">Topic</td>
							<td>:</td>
							<td class="mytabledata"><bean:write
								name="educationList" property="subjectName" /></td>
						</tr>
						
						<tr >
							<td style="padding-left:160px" class="mytabledata">University</td>
							<td>:</td>
							<td class="mytabledata"><bean:write
								name="educationList" property="universityName" /></td>
						</tr>
					</logic:equal>
					</logic:equal>
					
					
					<logic:equal value="5" name="educationList" property="qualificationId">
					<logic:equal value="true" name="educationList" property="isPhd">
						<tr align="left">
							<td class="mytabledata" style="text-indent: 5em;"><b>Ph.D</b></td>
						</tr>

						<tr >
							<td style="padding-left:160px" class="mytabledata">Topic</td>
							<td>:</td>
							<td class="mytabledata"><bean:write
								name="educationList" property="subjectName" /></td>
						</tr>
						
						<tr >
							<td style="padding-left:160px" class="mytabledata">University</td>
							<td>:</td>
							<td class="mytabledata"><bean:write
								name="educationList" property="universityName" /></td>
						</tr>
					</logic:equal>
					</logic:equal>
				</nested:iterate>
       </table>
       
      
      
       
       </td>
       
       
       </tr>
       
       <tr>
       </tr>
       
       
       
       
  
       
        <logic:equal value="true" name="applicationRegistrationForm" property="isNetQualification">
                <tr>
                <td>
                <table>
                <tr>
                
                	<td class="mytabledata">NET Qualification</td>
                <td>:</td>
					<td class="mytabledata"><bean:write
						name="applicationRegistrationForm" property="netDeatils" /></td>
                
                </tr>
                
                
                </table>
                
                
                </td>
					
				</tr>
       
       </logic:equal>
       
       <logic:equal value="true" name="applicationRegistrationForm" property="isJrfQualification">
			<tr>
				<td class="mytabledata">JRF Qualification</td>
				<td>:</td>
				<td class="mytabledata"><bean:write
					name="applicationRegistrationForm" property="jrfDeatils" /></td>
			</tr>
		</logic:equal>
		  <logic:equal value="true" name="applicationRegistrationForm" property="isNetQualification">
		   <logic:equal value="true" name="applicationRegistrationForm" property="isJrfQualification">
		   <tr class="divider"></tr>
		   
		   </logic:equal>
		  
		  </logic:equal>
		  
		  <logic:equal value="true" name="applicationRegistrationForm" property="isNetQualification">
		  <logic:equal value="false" name="applicationRegistrationForm" property="isJrfQualification">
		  
		  <tr class="divider"></tr>
		  </logic:equal>
		  </logic:equal>
		  
		    <logic:equal value="false" name="applicationRegistrationForm" property="isNetQualification">
		  <logic:equal value="true" name="applicationRegistrationForm" property="isJrfQualification">
		  
		  <tr class="divider"></tr>
		  </logic:equal>
		  </logic:equal>
		
		
		<logic:notEqual value="0" name="applicationRegistrationForm" property="bookSize">


			<tr>
				<td>
				
				<table width="100%" border="0">
				<tr>
				<td>
				<h5><b>Publications(Relevent)</b></h5>
				
				</td>
				<nested:iterate property="booksAndArticles" name="applicationRegistrationForm" id="booksAndArticlesList" indexId="count">
			<tr>
					<td width="30" class="mytabledata">Books </td>
					<td>:</td>
					<td class="mytabledata"><bean:write
						name="booksAndArticlesList" property="books" /></td>

					<td class="mytabledata">Articles </td>
					<td>:</td>
					<td class="mytabledata"><bean:write
						name="booksAndArticlesList" property="articles" />
					</td>

				</tr>
			
			
			</nested:iterate>
				
				</tr>

				
				</table>
				

			</tr>
			
			 <tr class="divider"></tr>
			<tr>

			<td>
			<h5><b>Research Publications(In UGC Approved journal)</b></h5>
			</td>
		</tr>
		
		<tr>
					<td class="mytabledata" style="text-indent: 15em;"><bean:write
						name="applicationRegistrationForm" property="ugcApprovedInformation" /></td>
		</tr>
			
           <tr class="divider"></tr>
		</logic:notEqual>
		
	<logic:notEqual value="0" name="applicationRegistrationForm" property="postDocSize">
			<tr>
			
				<td>
				
				<table class="pagebreak" width="100%" border="0">
				<tr>
				<td>
				<h5><b>Post Doctoral Experience</b></h5>
				</td>
				
				</tr>
				
					<nested:iterate property="postDocExp" name="applicationRegistrationForm" id="postDocExp" indexId="count">
			
			<tr>
					<td width="30" class="mytabledata">From Date </td>
					<td>:</td>
					<td class="mytabledata"><bean:write
						name="postDocExp" property="fromDate" /></td>

					<td class="mytabledata">To Date </td>
					<td>:</td>
					<td class="mytabledata"><bean:write
						name="postDocExp" property="toDate" />
					</td>
					
					<td class="mytabledata">College </td>
					<td>:</td>
					<td class="mytabledata"><bean:write
						name="postDocExp" property="collageName" />
					</td>

				</tr>
			
			
			
			</nested:iterate>
				
				
				
				</table>
				
</td>
			</tr>
			
		


		</logic:notEqual>
		
		
			<logic:notEqual value="0" name="applicationRegistrationForm" property="teachingExpSize">
			<tr>
				<td>
				<table width="100%" border="0">
				<tr>
				<td>
				<h5><b>Teaching Experience</b></h5>
				
				
				</td>
				
				<nested:iterate property="teachingExp" name="applicationRegistrationForm" id="teachingExp" indexId="count">
			
			<tr>
					<td width="30" class="mytabledata">From Date </td>
					<td>:</td>
					<td class="mytabledata"><bean:write
						name="teachingExp" property="fromDate" /></td>

					<td class="mytabledata">To Date </td>
					<td>:</td>
					<td class="mytabledata"><bean:write
						name="teachingExp" property="toDate" />
					</td>
					
					<td class="mytabledata">College </td>
					<td>:</td>
					<td class="mytabledata"><bean:write
						name="teachingExp" property="collageName" />
					</td>

				</tr>
			
			
			
			</nested:iterate>
				</table>
				
				
				</td>

			</tr>
			
			

		</logic:notEqual>
<logic:notEqual value="0" name="applicationRegistrationForm" property="teachingExpSize">
<logic:notEqual value="0" name="applicationRegistrationForm" property="postDocSize">

 <tr class="divider"></tr>
</logic:notEqual>
</logic:notEqual>

<logic:equal value="0" name="applicationRegistrationForm" property="teachingExpSize">
<logic:notEqual value="0" name="applicationRegistrationForm" property="postDocSize">

 <tr class="divider"></tr>
</logic:notEqual>
</logic:equal>

<logic:notEqual value="0" name="applicationRegistrationForm" property="teachingExpSize">
<logic:equal value="0" name="applicationRegistrationForm" property="postDocSize">

 <tr class="divider"></tr>
</logic:equal>
</logic:notEqual>



		<tr>

			<td>
			<h5><b>Additional Information-Regarding Academic Contributions</b></h5>
			</td>
		</tr>
		
		<tr>
					<td class="mytabledata" style="text-indent: 15em;"><bean:write
						name="applicationRegistrationForm" property="additionalInformation" /></td>
		</tr>
		 <tr class="divider"></tr>
		
		<tr>

			<td>
			<h5><b>Declaration-</b></h5>
			</td>
		</tr>
		
		<tr>

			<td class="mytabledata" style="text-indent: 10em;">
			<p>I Shri/Smt&nbsp; <bean:write
						name="applicationRegistrationForm" property="firstName" />&nbsp; certify that all the above
			details are true to the best of my knowledge & belief. I am
			personally liable for my wrong Information</p>

			</td>
		</tr>


		<tr>

			<td class="mytabledata">Place :</td>

		</tr>

		<tr>

			<td class="mytabledata">Date :</td>

		</tr>

		<tr height=90>
			<td class="mytabledata" align="right">Signature of the applicant</td>
		</tr>

		<tr>
			<td class="mytabledata" align="center"><strong>Note:- </strong>Supporting documents to be attached in respect of all the claims.</td>
		</tr>
		<tr>
			<td class="mytabledata" align="center">List of enclouses-</td>
		</tr>
         <tr class="mytabledata"></tr>
         <tr class="mytabledata"></tr>
         <tr class="mytabledata"></tr>
         <tr class="mytabledata"></tr>

 <tr class="divider"></tr>

		<tr>
			<td class="mytabledata"><b>For office use.</b></td>
		</tr>

		<tr>
			<td class="mytabledata">Date of Recepit :</td>
		</tr>

		<tr>
			<td class="mytabledata">Date of Verification :</td>
		</tr>


		<tr>
			<td width=30 height="80" class="mytabledata">
			
			       <span style="margin-left:50%"><b>HOD</b></span>
			       &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			        
			       <span><b>Principal</b></span>
						
			</td>
		</tr>

	</table>






</html:form>


</body>
<script type="text/javascript">

var religionId=document.getElementById("religionId").innerHTML;
var casteId=document.getElementById("casteId").innerHTML;
if(religionId=='Christianity' && casteId=='RC Syrian Catholic'){
	document.getElementById("subCaste").style.display = "block";
}else{
	document.getElementById("subCaste").style.display = "none";
}


	window.print();
</script>

</html>