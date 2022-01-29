<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms" %>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<%@page import="com.kp.cms.constants.CMSConstants"%>
<head>
<link href="css/styles.css" rel="stylesheet" type="text/css">
<title><bean:message key="knowledgepro.admission.transferCertificate"/></title>
<style type="text/css">
	.fontClass
	{
		font-size: 8pt;
		font-weight: normal;
	}
	.heading1
	{
		font-weight: bold;
		font-family: sans-serif;
		font-family: 16pt;
	}
	.heading2
	{
		font-weight: bold;
		font-family: sans-serif;
		font-size: 8pt;
	}
	.heading3
	{
		font-weight: bold;
		font-family: sans-serif;
		font-size: 6pt;
	}
	.heading4
	{
		font-weight: bold;
		font-family: sans-serif;
		font-size: 10pt;
	}
	.body2
	{
		font-size: 8pt
	}
</style>
</head>
<body>
<html:form action="/transferCertificate">

<html:hidden property="formName" value="transferCertificateForm" />
<html:hidden property="method" styleId="method" value="initTransferCertificate"/>
<html:hidden property="pageType" value="3"/>
<logic:notEmpty name="transferCertificateForm" property="studentList">
	<logic:iterate id="studentList" name="transferCertificateForm" property="studentList" indexId="count">
		<table width="95%" border="2" style="margin: 25px;margin-right: 25px"  rules="none" cellpadding="0" cellspacing="0" >
      		<tr>
        		<td valign="top" class="news">
		        	<table width="85%" border="0" rules="none" cellpadding="3" cellspacing="3" align="center">
						<tr>
							<td colspan="2" width="100%" valign="middle">
								<table width="100%">
									<tr>
										<td width="30%" rowspan="2">
											<img src='<%=CMSConstants.LOGO_URL%>' alt='Logo not available'>
										</td>
										<td valign="middle" align="right" colspan="2">
											<logic:equal value="Yes" property="duplicate" name="transferCertificateForm">
												<font class="heading4">Duplicate</font>
											</logic:equal>													
										</td>
									</tr>
									<tr>
										<td valign="middle" width="35%"  align="center">
											<font class="heading1"> Transfer Certificate </font>
										</td>
										<td align="left">
											<font class="heading2">T.C No</font>
												:&nbsp;
											<font class="fontClass"><bean:write name="studentList" property="tcNo"/></font>
										</td>
									</tr>
								</table>
         					</td>
         				</tr>
         				<tr height="18px" >
       						<td colspan="2">
       							&nbsp;
       						</td>
       					</tr>
    					<tr>
    						<td width="35%" align="left">
    							<font class="heading2">Name Of The Student</font>
    						</td>
    						<td width="50%" class="fontClass" align="left">
    							:&nbsp;&nbsp; <bean:write name="studentList" property="studentName"/>
    						</td>
    					</tr>
    					<tr height="25px" >
       						<td colspan="2">
       							&nbsp;
       						</td>
       					</tr>
    					<tr>
    						<td width="35%" align="left">
    							<font class="heading2">Register No.,Month and Year of Pass</font>
    						</td>
    						<td width="50%" class="fontClass">
    							:&nbsp;&nbsp; <bean:write name="studentList" property="regMonthYear"/>
    						</td>
    					</tr>
    					<tr height="25px" >
       						<td colspan="2">
       							&nbsp;
       						</td>
       					</tr>
    					<tr height="25px">
    						<td width="35%" align="left">
    							<font class="heading2">Date of Birth&nbsp;&nbsp;(In figures)</font>
    						</td>
    						<td width="50%" class="fontClass">
    							:&nbsp;&nbsp; <bean:write name="studentList" property="dobFigures"/>
    						</td>
    					</tr>
    					<tr height="25px">
    						<td width="35%" align="left">
    							<font class="heading3">(As entered in the admission register)</font>&nbsp;&nbsp;<font class="heading2">(In words)</font>
    						</td>
    						<td width="50%" class="fontClass">
    							:&nbsp;&nbsp; <bean:write name="studentList" property="dobWords"/>
    						</td>
    					</tr>
    					<tr height="25px" >
       						<td colspan="2">
       							&nbsp;
       						</td>
       					</tr>
    					<tr height="25px">
    						<td width="35%" align="left">
								<font class="heading2">Date and Class of Admission</font>      										
    						</td>
    						<td width="50%" class="fontClass">
    							:&nbsp;&nbsp; <bean:write name="studentList" property="dateOfAdmission"/>
    							&nbsp;
    							and 
    							&nbsp;
    							<bean:write name="studentList" property="admissionClass"/>	
    						</td>
    					</tr>
    					<tr height="25px">
    						<td width="35%" align="left">
    							<font class="heading2">Subject Studied</font>
    						</td>
    						<td width="50%" class="fontClass">
    							:&nbsp;&nbsp; <bean:write name="studentList" property="subjectsStudied"/>
    						</td>
    					</tr>
    					<tr height="25px">
    						<td width="35%" align="left">
    							<font class="heading2">Date and Class of Leaving</font>
    						</td>
    						<td width="50%" class="fontClass">
    							:&nbsp;&nbsp; <bean:write name="studentList" property="dateOfLeaving"/>
    							&nbsp;
    							and 
    							&nbsp;
    							<bean:write name="studentList" property="className"/>	 
    						</td>
    					</tr>
    					<tr height="25px" >
       						<td colspan="2">
       							&nbsp;
       						</td>
       					</tr>
    					<tr height="25px">
    						<td width="35%" align="left">
    							<font class="heading2">Reason of Transfer</font>
    						</td>
    						<td width="50%" class="fontClass" rowspan="1">
    							:&nbsp;&nbsp; <bean:write name="studentList" property="reason"/>
    						</td>	
    					</tr>
    					<tr height="25px">
							<td width="35%" align="left">
								<font class="heading2">Eligible for Promotion</font>
							</td>
							<td width="50%" class="fontClass" rowspan="1">
								:&nbsp;&nbsp; <bean:write name="studentList" property="eligible"/>
							</td>
						</tr>
						<tr height="25px" >
							<td colspan="2">
								&nbsp;
							</td>
 						</tr>
						<tr height="25px">
							<td width="35%" align="left">
								<font class="heading2">Character and Conduct</font>
							</td>
							<td width="50%" class="fontClass">
								:&nbsp;&nbsp; <bean:write name="studentList" property="conduct"/>
							</td>
						</tr>
						<tr height="25px" >
							<td colspan="2">
								&nbsp;
							</td>
 						</tr>
 						<tr height="25px">
 							<td colspan="2">
 								<table width="100%">
 									<tr>
 										<td width="70%">
 											<font class="heading2">Bangalore</font>
 										</td>
 										<td>
 											<font class="heading2">Registrar</font>
 										</td>
 									</tr>	
 								</table>
 							</td>
 						</tr>
						<tr height="25px">
							<td width="35%" align="left">
								<table width="100%">
									<tr>
										<td width="20%">
											<font class="heading2">Date</font>
										</td>
										<td class="fontClass">
											<bean:write name="studentList" property="tcDate"/>	
										</td>
									</tr>
								</table>
							</td>
							<td width="50%" class="fontClass">
								&nbsp;
							</td>
						</tr>
						<tr>
							<td colspan="2" width="60%">
								<hr></hr>
							</td>
						</tr>
						<tr>
							<td colspan="2" width="100%" valign="middle">
								<table width="100%">
									<tr>
										<td width="30%">
											<img src='<%=CMSConstants.LOGO_URL%>' alt='Logo not available'>
										</td>
										<td valign="middle" width="35%"  align="center">
											<font class="heading1"> Migration Certificate </font>
										</td>
										<td align="left">
											<font class="heading2">M.C No</font>
												:&nbsp;
											<font class="fontClass"><bean:write name="studentList" property="mcNo"/></font>
										</td>
									</tr>
								</table>
         					</td>
         				</tr>
    					<tr>
    						<td align="left" width="90%"  colspan="2" >
    							<table width="100%" cellpadding="3" cellspacing="5">
    								<tr>
    									<td style="font-size: 8pt">
			    							It is hereby stated that        									
			    							<font class="heading2" style="text-decoration: underline">&nbsp;&nbsp;&nbsp;<bean:write name="studentList" property="studentName"/>&nbsp;&nbsp;&nbsp;</font>
			    							with the register number <font class="heading2" style="text-decoration: underline">&nbsp;&nbsp;&nbsp;<bean:write name="studentList" property="regNo"/>&nbsp;&nbsp;&nbsp;</font> 
			    							
			    						</td>
			    					</tr>
			    					<tr>
			    						<td style="font-size: 8pt">		  
			     							completed/discontinued  his/her studies in 
			    							<font class="heading2" style="text-decoration: underline">&nbsp;&nbsp;&nbsp;<bean:write name="studentList" property="course"/>&nbsp;&nbsp;&nbsp;</font>
			     							during the academic year
			     							<font class="heading2" style="text-decoration: underline">&nbsp;&nbsp;&nbsp;<bean:write name="studentList" property="leavingYear"/>&nbsp;&nbsp;&nbsp;</font> .
			     								 
       									</td>
       								</tr>
       								<tr>
			    						<td style="font-size: 8pt">
						    				Christ University has no record of  him/her found involve in any act of ragging or related acts 
			    						</td>
			    					</tr>
			    					<tr>
			    						<td style="font-size: 8pt">
			    							directly or indirectly. Christ University has no objection to continue his/her  studies in
			    						</td>
			    					</tr>
			    					<tr>
			    						<td style="font-size: 8pt">
			    							 any other University.
			    						</td>
			    					</tr>
       							</table>
       						</td>
       					</tr>
       					<tr height="25px">
       						<td colspan="2">
       							<table width="100%">
       								<tr>
       									<td width="33%" align="left">
       										<font class="heading2">Bangalore</font>
       									</td>
       									<td width="33%" align="center">
       										<font class="heading2">Verified</font>
       									</td>
       									<td width="34%" align="center">
       										<font class="heading2">Registrar</font>
       									</td>
       								</tr>	
       							</table>
       						</td>
       					</tr>
    					<tr height="25px">
    						<td width="35%" align="left">
    							<table width="100%">
    								<tr>
    									<td width="20%">
    										<font class="heading2">Date</font>
    									</td>
    									<td class="fontClass">
    										<bean:write name="studentList" property="tcDate"/>	
    									</td>
    								</tr>
    							</table>
    						</td>
    						<td width="50%" class="fontClass">
    							&nbsp;
    						</td>
    					</tr>						
         			</table>
         		</td>
         	</tr>
        </table>			
	<p style="page-break-after:always;"> </p>
	</logic:iterate>
		
<script type="text/javascript">
	window.print();
</script>
</logic:notEmpty>
<logic:empty name="transferCertificateForm" property="studentList">
	<table width="100%" height="435px">
		<tr>
			<td align="center" valign="middle">
				No Records Found
			</td>
		</tr>
	</table>
</logic:empty>
</html:form>
</body>
