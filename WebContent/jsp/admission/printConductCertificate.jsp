<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms" %>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<%@page import="com.kp.cms.constants.CMSConstants"%>
<head>
<link href="css/styles.css" rel="stylesheet" type="text/css">
<title><bean:message key="knowledgepro.admission.ccCertificate"/></title>
<style type="text/css">
	.heading1
	{
		font-weight: bold;	
		font-family: Arial,Helvetica,sans-serif;
		font-family: 9.5pt;
	}
	.heading2
	{
		font-family: Arial,Helvetica,sans-serif;
		font-size: 11.5px;
	}
	
	.heading3
	{
		font-weight: bold;
		font-family: Arial,Helvetica,sans-serif;
		font-size: 9.5pt;
	}
	.heading4
	{
		font-weight: bold;
		font-family: Arial,Helvetica,sans-serif;
		font-size: 9pt;
	}
	.body2
	{
		font-size: 8pt
	}
</style>
</head>
<body>
<%@ page import="java.util.*"%>
<%@ page import="java.text.*"%>
<html:form action="/conductCertificate">

<html:hidden property="formName" value="conductCertificateForm" />
<html:hidden property="method" styleId="method" value="initConductCertificate"/>
<html:hidden property="pageType" value="3"/>
<logic:notEmpty name="conductCertificateForm" property="studentList">

<%int i=1; %>
	<logic:iterate id="studentList" name="conductCertificateForm" property="studentList" indexId="count">
	<table width="100%"  style="border-collapse: collapse" >
	<tr>
		<td width="100%" align="center">
			<table border="0">
				<tr>
				<td>
				<table width="100%" height="20%" border="0">
								<tr>
				<td align="center" colspan="3">
		  			<img width="500" height="80"  src='<%= request.getRequestURL().toString().replace(request.getRequestURI().substring(0), request.getContextPath()).concat("/LogoServlet") %>' alt="Logo not available">
				</td>
				</tr>
				<tr><td>&nbsp;</td></tr>
				<tr>
				<td width="5%"></td>				
				<td width="5%"></td>
				</tr>
				
				<tr>
					<td width="70%" class="heading3" align="left">
						<bean:message key="knowledgepro.admission.slno"/> : 
						<bean:write name="studentList" property="ccNo"/>/<bean:write name="studentList" property="duration"/>
					</td>	
					<td width="70%" class="heading3" align="right">
					<bean:message key="knowledgepro.admission.cdate"/> :<bean:write name="studentList" property="curDate"/>
					</td>				
															
				</tr>
				<tr>
					<td width="70%" class="heading3" align="left">
						<bean:message key="knowledgepro.admission.no"/> : 
						<bean:write name="studentList" property="admissionnumber"/>
					</td>	
				</tr>
				<tr>
					<td width="70%" class="heading3" align="left">
						<bean:message key="knowledgepro.cl.no"/> : 
						<bean:write name="studentList" property="rollNo"/>
					</td>	
				</tr>
				<tr><td>&nbsp;</td></tr>				
				
				<tr>
					<td align="center" class="heading3" colspan="3">
						<h3><b><u style="font-size: 22px;"><bean:message key="knowledgepro.admission.conductCertificate"/></u></b></h3>
					</td>
				</tr>
											
				</table></td></tr>
			<%-- 	<tr><td>
 
 				<p style="font-style: italic">This is to certify that Miss <b><bean:write name="studentList" property="studentName"/></b> was a student <br>
 				of this college for the Degree/Post Graduate Course in <b><bean:write name="studentList" property="course"/></b><br>
				from <b><bean:write name="studentList" property="dateOfAdmission"/></b> Her character and conduct were <b><bean:write name="studentList" property="conduct"/></b>
 				</p>
 				</td></tr>--%>
 				<tr><td>
				<div style="width: 600px;font-size: 18px";>	
 				<p style="font-style: normal; line-height: 25px; text-align: justify;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;This is to certify that Ms/Mr <b><bean:write name="studentList" property="studentName"/></b>
 				 was a student of this  college in the <b><bean:write name="studentList" property="course"/></b>  programme during <b><bean:write name="studentList" property="duration"/></b>  
			     and his/her conduct and character have been ...............
 				</p>
 				</div>
 				</td></tr>
 				<tr>
 				<td>
 				<table width="100%">
 				<tr><td>&nbsp;</td></tr>
 				<tr><td>&nbsp;</td></tr> 				
 				<tr><td></td></tr><tr height="20px">
 				    <td width="15%"></td> 
 				    <td width="15%"></td> 
 				   				     	
    				<td align="right" class="heading3" width="40%">
					 <h3><bean:message key="knowledgepro.admission.principal"/></h3>
							 <%--<h3><bean:message key="knowledgepro.admission.principalviceprincipal"/></h3>--%>													
    				</td>
    			</tr>
 				
 				</table></td></tr>
		</table>
		</td>		
	</tr>
	</table>
	<p style="page-break-after: always;"></p>
	<%i++; %>
	</logic:iterate>		
<script type="text/javascript">
	window.print();
</script>
</logic:notEmpty>
<logic:empty name="conductCertificateForm" property="studentList">
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
