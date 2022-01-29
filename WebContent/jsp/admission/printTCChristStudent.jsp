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
	.heading1
	{
		font-weight: bold;	
		font-family: Arial,Helvetica,sans-serif;
		font-family: 10pt;
	}
	.heading2
	{
		font-family: Arial,Helvetica,sans-serif;
		font-size: 14px;
		height: 32px;
		vertical-align: top;
	}
	
	.heading3
	{
		font-weight: bold;
		font-family: Arial,Helvetica,sans-serif;
		font-size: 11pt;
	}
	.heading4
	{
		font-weight: bold;
		font-family: Arial,Helvetica,sans-serif;
		font-size: 12pt;
	}
	.body2
	{
		font-size: 8.5pt
	}
</style>
<style type="text/css" media="print">
  	 @media print {
	  	 @page { size: landscape; } 
	 	 * { margin: 0 2 0px 5px !important; padding: 0 !important; }
	  	 #controls, .footer, .footerarea{ display: none; }
	  	 html, body {
	    	/*changing width to 100% causes huge overflow and wrap*/    	 
		    overflow: hidden;
		    background: #FFF; 
		    font-size: 9.5pt;
	  	 }
	  	.template { width: auto; left:0; top:0; }
	  	li { margin: 0 2 0px 37.8px !important;}
	}
</style>
</head>
<body>
<%@ page import="java.util.*"%>
<%@ page import="java.text.*"%>
<html:form action="/tcDetails">

<html:hidden property="formName" value="tCDetailsForm" />
<html:hidden property="method" styleId="method" value=""/>
<html:hidden property="pageType" value="3"/>
<logic:notEmpty name="tCDetailsForm" property="studentList">
	<logic:iterate id="studentList" name="tCDetailsForm" property="studentList" indexId="count">
		<table width="100%" border="0">
			<tr>
				<td width="49%">
					<table border="0">
						<tr>						
							<td>
								<table width="100%" height="20%" border="0">
									<tr>
										<td align="center" colspan="3">
								  			<img width="400" height="75"  src='<%= request.getRequestURL().toString().replace(request.getRequestURI().substring(0), request.getContextPath()).concat("/LogoServlet") %>' alt="Logo not available">
										</td>
									</tr>				
									<tr>
										<td width="5%"></td>
										<logic:equal value="true" name="tCDetailsForm" property="reprint">
											<td width="90%" align="right" style="font-family: sans-serif"><h4>DUPLICATE</h4></td>
										</logic:equal>		
										<td width="5%"></td>
									</tr>
									<tr>
										<td align="center" class="heading4" colspan="3">
											<h3><b><u><bean:message key="knowledgepro.admission.tcconductcertificate"/></u></b></h3>
										</td>
									</tr> 								
								</table>
							</td>
						</tr>
						<tr>
							<td>
								<table width="100%" style="table-layout: fixed">
									<tr height="20px">
										<td class="heading3" width="44%">
											<bean:message key="knowledgepro.admission.tcno"/>:<bean:write name="studentList" property="tcNo"/>
										</td>
										<% 
						  					DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
						    				Date date = new Date();
						    				String date1=dateFormat.format(date);
					    				%>
					    				<td width="1%"></td>
					    				<td align="right" class="heading3" width="55%">
					    				Date:
					    					<logic:notEmpty name="studentList" property="dateOfIssue">
												<bean:write name="studentList" property="dateOfIssue"/>
											</logic:notEmpty>
											<logic:empty name="studentList" property="dateOfIssue">
												<%=date1%>
											</logic:empty>											
					    				</td> 	
									</tr>
					       			<tr height="20px">		
					       				<td class="heading2"><bean:message key="knowledgepro.admission.studentname"/></td>
					       				<td valign="top">:</td>
					    				<td class="heading2"><b><bean:write name="studentList" property="studentName"/></b></td>
					    			</tr>
					    			<tr height="20px">
					    				<td class="heading2"><bean:message key="knowledgepro.admission.admissionno"/></td>
					    				<td valign="top">:</td>   
										<td class="heading2"> <bean:write name="studentList" property="admissionnumber"/></td>   										
					    			</tr>
					    			<tr>
					    				<td class="heading2"><bean:message key="knowledgepro.admission.dob"/></td>
					    				<td valign="top">:</td>
					    				<td class="heading2"><bean:write name="studentList" property="dobFigures"/><br><font style="font-size: 12px;font-family: sans-serif;">(<bean:write name="studentList" property="dobWords"/>)</font></td>
					    			</tr>						    			
					    			<tr height="20px">
					    				<td class="heading2"><bean:message key="knowledgepro.admission.admissiondate"/></td>
					    				<td valign="top">:</td>  
										<td class="heading2"> <bean:write name="studentList" property="dateOfAdmission"/></td>
					    			</tr>
					    			<tr height="20px">
					    				<td class="heading2"><bean:message key="knowledgepro.admission.leavingdate"/></td>
					    				<td valign="top">:</td>
					    				<td class="heading2"> <bean:write name="studentList" property="dateOfLeaving"/></td>
					    			</tr>					    			
					     			<tr height="20px">
					    				<td class="heading2"><bean:message key="knowledgepro.admission.class&subjectwhileleaving"/></td>
					    				<td valign="top">:</td>
										<td class="heading2"><bean:write name="studentList" property="classOfLeaving"/></td>
					    			</tr>    	
					    			<tr height="10px">
					    				<td colspan="3"></td>
					    			</tr>									    			
					    			<tr height="20px">
					    				<td class="heading2"><bean:message key="knowledgepro.admission.subjectsstudied"/></td>
					    				<td valign="top">:</td>
					    				<td class="heading2" style="word-wrap: break-word;"><bean:write name="studentList" property="subjectsStudied"/></td>
					    			</tr>					    			
					    			<tr height="20px">
					    				<td class="heading2"><bean:message key="knowledgepro.admission.higherclasspromotion"/></td>
					    				<td valign="top">:</td>
										<td class="heading2"><bean:write name="studentList" property="promotionToNextClass"/></td>
									</tr>						    				
					    			<tr height="20px">
										<td class="heading2"><bean:message key="knowledgepro.admission.feedues"/></td>
										<td valign="top">:</td>
										<td class="heading2"> <bean:write name="studentList" property="feePaid"/></td>
									</tr>									
									<tr height="20px">
					    				<td class="heading2"><bean:message key="knowledgepro.admission.university"/></td>
					    				<td valign="top">:</td>
					    				<td class="heading2"> Mahatma Gandhi University, Kottayam Kerala</td>
					    			</tr>					    			
					    			<tr height="20px">
										<td class="heading2"><bean:message key="knowledgepro.admission.examname&university"/>&nbsp;last registered</td>
										<td valign="top">:</td>
										<td class="heading2"> <bean:write name="studentList" property="publicExamName"/></td>
									</tr>		    							    			
					    			<tr height="20px">
					    				<td class="heading2"><bean:message key="knowledgepro.admission.permanentregno"/></td>
					    				<td valign="top">:</td>
					    				<td class="heading2"> <bean:write name="studentList" property="regMonthYear"/></td>
						    		</tr>					    								    			
									<tr height="20px">
					    				<td class="heading2"><bean:message key="knowledgepro.admission.reasonforleaving"/></td>
					    				<td valign="top">:</td>
					    				<td class="heading2"> <bean:write name="studentList" property="reason"/></td>
						    		</tr>												
				 				</table>
				 			</td>
				 		</tr>	
		 				<tr>
			 				<td>
				 				<table width="100%">
					 				<tr height="30px"></tr>				
					 				<tr height="15px">
					 				    <td width="25%"></td> 
					 				    <td width="25%"></td> 				     	
					    				<td align="left" class="heading3" width="25%">
					    				
											<h3><bean:message key="knowledgepro.admission.principal"/></h3>													
					    				</td>
					    			</tr>			 				
				 				</table>
				 			</td>
		 				</tr>
					</table>
				</td>
							
				<td width="2%" style="border-right: thin;border-right: dashed;"></td>
				
				<td width="49%">
					<table border="0">
						<tr>
							<td>
								<table width="100%" height="20%" border="0">
									<tr>
										<td align="center" colspan="3">
								  			<img width="400" height="75"  src='<%= request.getRequestURL().toString().replace(request.getRequestURI().substring(0), request.getContextPath()).concat("/LogoServlet") %>' alt="Logo not available">
										</td>
									</tr>
									<tr>
										<td width="5%"></td>
										<logic:equal value="true" name="tCDetailsForm" property="reprint">
											<td width="90%" align="right" style="font-family: sans-serif"><h4>DUPLICATE</h4></td>
										</logic:equal>		
										<td width="5%"></td>
									</tr>
									
									<tr>
										<td align="center" class="heading4" colspan="3">
											<h3><b><u><bean:message key="knowledgepro.admission.tcconductcertificate"/></u></b></h3>
										</td>
									</tr> 								
								</table>
							</td>
						</tr>
						<tr>
							<td>
								<table width="100%" style="table-layout: fixed">
									<tr height="20px">
										<td class="heading3" width="44%">
											<bean:message key="knowledgepro.admission.tcno"/>.:<bean:write name="studentList" property="tcNo"/>
										</td>
										<% 
						  					DateFormat dateFormat1 = new SimpleDateFormat("dd-MM-yyyy");
						    				Date date2 = new Date();
						    				String date3=dateFormat1.format(date2);
					    				%>
					    				<td width="1%"></td>
					    				<td align="right" class="heading3" width="55%">
											Date :
											<logic:notEmpty name="studentList" property="dateOfIssue">
												<bean:write name="studentList" property="dateOfIssue"/>
											</logic:notEmpty>
											<logic:empty name="studentList" property="dateOfIssue">
												<%=date2%>
											</logic:empty>						
					    				</td> 	
									</tr>
					       			<tr height="20px">		
					       				<td class="heading2"><bean:message key="knowledgepro.admission.studentname"/></td>
					       				<td valign="top">:</td>
					    				<td class="heading2"><b><bean:write name="studentList" property="studentName"/></b></td>
					    			</tr>				
					    			<tr height="20px">
					    				<td class="heading2"><bean:message key="knowledgepro.admission.admissionno"/></td>
					    				<td valign="top">:</td>   
										<td class="heading2"> <bean:write name="studentList" property="admissionnumber"/></td>   										
					    			</tr>
					    			<tr height="20px">
					    				<td class="heading2"><bean:message key="knowledgepro.admission.dob"/></td>
					    				<td valign="top">:</td>
					    				<td class="heading2"> <bean:write name="studentList" property="dobFigures"/><br><font style="font-size: 12px;font-family: sans-serif;">(<bean:write name="studentList" property="dobWords"/>)</font></td>
					    			</tr>						    			
					    			<tr height="20px">
					    				<td class="heading2"><bean:message key="knowledgepro.admission.admissiondate"/></td>
					    				<td valign="top">:</td>  
										<td class="heading2"> <bean:write name="studentList" property="dateOfAdmission"/></td>
					    			</tr>
					    			<tr height="20px">
					    				<td class="heading2"><bean:message key="knowledgepro.admission.leavingdate"/></td>
					    				<td valign="top">:</td>
					    				<td class="heading2"> <bean:write name="studentList" property="dateOfLeaving"/></td>
					    			</tr>					    			
					     			<tr height="20px">
					    				<td class="heading2"><bean:message key="knowledgepro.admission.class&subjectwhileleaving"/></td>
					    				<td valign="top">:</td>
										<td class="heading2"><bean:write name="studentList" property="classOfLeaving"/></td>
					    			</tr>    		
					    			<tr height="10px">
					    				<td colspan="3"></td>
					    			</tr>							    			
					    			<tr height="20px">
					    				<td class="heading2"><bean:message key="knowledgepro.admission.subjectsstudied"/></td>
					    				<td valign="top">:</td>
					    				<td class="heading2" style="word-wrap: break-word;"><bean:write name="studentList" property="subjectsStudied"/></td>
					    			</tr>					    			
					    			<tr height="20px">
					    				<td class="heading2"><bean:message key="knowledgepro.admission.higherclasspromotion"/></td>
					    				<td valign="top">:</td>
										<td class="heading2"><bean:write name="studentList" property="promotionToNextClass"/></td>
									</tr>						    				
					    			<tr height="20px">
										<td class="heading2"><bean:message key="knowledgepro.admission.feedues"/></td>
										<td valign="top">:</td>
										<td class="heading2"> <bean:write name="studentList" property="feePaid"/></td>
									</tr>									
									<tr height="20px">
					    				<td class="heading2"><bean:message key="knowledgepro.admission.university"/></td>
					    				<td valign="top">:</td>
					    				<td class="heading2"> Mahatma Gandhi University, Kottayam, Kerala </td>
					    			</tr>					    			
					    			<tr height="20px">
										<td class="heading2"><bean:message key="knowledgepro.admission.examname&university"/>&nbsp;last registered</td>
										<td valign="top">:</td>
										<td class="heading2"> <bean:write name="studentList" property="publicExamName"/></td>
									</tr>		    							    			
					    			<tr height="20px">
					    				<td class="heading2"><bean:message key="knowledgepro.admission.permanentregno"/></td>
					    				<td valign="top">:</td>
					    				<td class="heading2"> <bean:write name="studentList" property="regMonthYear"/></td>
						    		</tr>					    								    			
									<tr height="20px">
					    				<td class="heading2"><bean:message key="knowledgepro.admission.reasonforleaving"/></td>
					    				<td valign="top">:</td>
					    				<td class="heading2"> <bean:write name="studentList" property="reason"/></td>
						    		</tr>													
				 				</table>
				 			</td>
				 		</tr>
		 				<tr>
			 				<td>
				 				<table width="100%">
					 				<tr height="30px"></tr>
					  							
					 				<tr height="15px">
					 				    <td width="25%"></td> 
					 				    <td width="25%"></td> 				     	
					    				<td align="left" class="heading3" width="25%">
					    				
											<h3><bean:message key="knowledgepro.admission.principal"/></h3>													
					    				</td>
					    			</tr>				 				
				 				</table>
			 				</td>
			 			</tr>
					</table>
				</td>				
			</tr>
		</table>
		<p style="page-break-after: always;"></p>
	</logic:iterate>		
	<script type="text/javascript">
		window.print();
	</script>
</logic:notEmpty>
<logic:empty name="tCDetailsForm" property="studentList">
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
