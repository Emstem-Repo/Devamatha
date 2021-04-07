<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms" %>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<head>
<link href="css/styles.css" rel="stylesheet" type="text/css">
<title><bean:message key="knowledgepro.admission.transferCertificate"/></title>
<style type="text/css">
	.fontClass
	{
		font-size: 8pt;
		font-family: sans-serif;
		font-weight: bold;
	}
	.heading4
	{
		font-weight: bold;
		font-family: sans-serif;
		font-size: 10pt;
	}
</style>
</head>
<body>
<html:form action="/TcDetailsOldStudents">


<html:hidden property="formName" value="tcDetailsOldStudentsForm" />
<!--<html:hidden property="method" styleId="method" value="initTransferCertificate"/>-->
<html:hidden property="pageType" value="4"/>
<table width="100%" border="0" >
	<tr>
    	<td valign="top">
    		<table width="100%" border="0" cellpadding="0" cellspacing="0" >
      			<tr>
        			<td valign="top" class="news">
        				
		        		        <table width="100%" border="0" cellpadding="0" cellspacing="0">
									<tr height="198px">
										<td valign="middle">
											<bean:write name="tcDetailsOldStudentsForm" property="tempTcType"/>
										</td>
		          					</tr>
		          					
										
		          					<tr>
		          						<td width="100%">
		          							<table width="100%" border="0"  cellpadding="1" rules="none" cellspacing="2.5">
		          								<tr height="10px">
		          								<td></td>
		          								</tr>
		          								<tr>
		          									<td width="50%" align="left">
		          										<table width="100%">
		          											<tr>
		          												<td width="38%">
		          													&nbsp;
		          												</td>
		          												<td ><b>
		          													<bean:write name="tcDetailsOldStudentsForm" property="tcNumber"/></b>
		          												</td>
		          											</tr>
		          										</table>
		          									</td>
		          									<td width="50%" class="fontClass">
		          										<table width="100%">
		          											<tr>
		          												<td width="48%">
		          													&nbsp;
		          												</td>
		          												<td ><b>
		          													<bean:write name="tcDetailsOldStudentsForm" property="registerNo"/></b>
		          												</td>
		          											</tr>
		          										</table>
		          									</td>
		          								</tr>
		          								<tr height="10px">
		          								<td></td>
		          								</tr>
		          								<tr>
		          									<td width="50%" align="left">
		          										&nbsp;
		          									</td>
		          									<td width="50%" class="fontClass">
		          										<bean:write name="tcDetailsOldStudentsForm" property="name"/>
		          									</td>
		          								</tr>
		          								<tr>
		          									<td width="50%" align="left">
		          										&nbsp;
		          									</td>
		          									<td width="50%" class="fontClass">
		          										<bean:write name="tcDetailsOldStudentsForm" property="studentNo"/>
		          									</td>
		          								</tr>
		          								<tr>
		          									<td colspan="2">
		          										&nbsp;
		          									</td>
		          								</tr>
		          								<tr>
		          									<td width="50%" align="left">
		          										&nbsp;
		          									</td>
		          									<td width="50%" class="fontClass">
		          										<bean:write name="tcDetailsOldStudentsForm" property="dateOfBirth"/>
		          									</td>
		          								</tr>
		          								<tr>
		          									<td width="50%" align="left">
		          										&nbsp;
		          									</td>
		          									<td width="50%" class="fontClass">
		          										<bean:write name="tcDetailsOldStudentsForm" property="dobInWords"/>
		          									</td>
		          								</tr>
		          								<tr height="2px">
		          								</tr>
		          								<tr>
		          									<td width="50%" align="left">
		          										&nbsp;
		          									</td>
		          									<td width="50%" class="fontClass">
		          										<bean:write name="tcDetailsOldStudentsForm" property="gender"/>
		          									</td>
		          								</tr>
		          								<tr>
		          									<td width="50%" align="left">
		          										&nbsp;
		          									</td>
		          									<td width="50%" class="fontClass">
		          										<bean:write name="tcDetailsOldStudentsForm" property="religionName"/>
		          									</td>
		          								</tr>
		          								<tr>
		          									<td width="50%" align="left">
		          										&nbsp;
		          									</td>
		          									<td width="50%" class="fontClass">
		          										<bean:write name="tcDetailsOldStudentsForm" property="nationalityName"/>
		          									</td>
		          								</tr>
		          								<tr>
		          									<td width="50%" align="left">
		          										&nbsp;
		          									</td>
		          									<td width="50%" class="fontClass">
		          										<bean:write name="tcDetailsOldStudentsForm" property="fatherName"/>
		          									</td>
		          								</tr>
		          								<tr>
		          									<td width="50%" align="left">
		          										&nbsp;
		          									</td>
		          									<td width="50%" class="fontClass">
		          										<bean:write name="tcDetailsOldStudentsForm" property="motherName"/>
		          									</td>
		          								</tr>
		          								<tr>
		          									<td width="50%" align="left">
														&nbsp;	          										
		          									</td>
		          									<td width="50%" class="fontClass">
		          										<bean:write name="tcDetailsOldStudentsForm" property="admissionDate"/>
		          										
		          										
		          									</td>
		          								</tr>
		          								<tr>
		          									<td width="50%" align="left">
														&nbsp;	          										
		          									</td>
		          									<td width="50%" class="fontClass">
		          										<bean:write name="tcDetailsOldStudentsForm" property="dateOfLeaving"/>
		          									</td>
		          								</tr>
		          								<tr>
		          									<td colspan="2">
														&nbsp;	          										
		          									</td>
		          								</tr>
		          								<tr>
		          									<td width="50%">
		          										&nbsp;
		          									</td>
		          									<td width="50%" class="fontClass">
		          										<bean:write name="tcDetailsOldStudentsForm" property="className"/>
		          									</td>
		          								</tr>
		          								<tr>
		          									<td colspan="2">
		          										&nbsp;
		          									</td>
		          								</tr>
		          								
		          								<tr>
		          									<td width="50%" align="left">
		          										&nbsp;
		          									</td>
		          									<td width="50%" style="font-size: 8pt" valign="middle" class="fontClass">
	          											<bean:write name="tcDetailsOldStudentsForm" property="part1Subjects"/>
		          									</td>
		          									
		          								</tr>
		          								<tr>
		          									<td width="50%" align="left">
		          										&nbsp;
		          									</td>
		          									<td width="50%" style="font-size: 8pt" valign="middle" class="fontClass">
	          											<bean:write name="tcDetailsOldStudentsForm" property="part2Subjects"/>
		          									</td>
		          									
		          								</tr>
		          								<tr height="30px">
		          								</tr>
		          								<tr>
		          									<td width="50%" align="left">
		          										&nbsp;
		          									</td>
		          									<td width="50%" class="fontClass">
		          										<bean:write name="tcDetailsOldStudentsForm" property="passDetails"/>
		          									</td>
		          								</tr>
		          								<tr height="2px">
		          								</tr>
		          								<tr>
		          									<td width="50%" align="left">
		          										&nbsp;
		          									</td>
		          									<td width="50%" class="fontClass">
		          									<logic:notEmpty name="tcDetailsOldStudentsForm" property="publicExamName">
		          										<bean:write name="tcDetailsOldStudentsForm" property="publicExamName"/>
		          										</logic:notEmpty>
		          									<logic:empty name="tcDetailsOldStudentsForm" property="publicExamName">
		          													---
		          									</logic:empty>
		          									</td>
		          								</tr>
		          								<tr>
		          									<td width="50%" align="left">
		          										&nbsp;
		          									</td>
		          									<td width="50%" class="fontClass">
		          									<logic:notEmpty name="tcDetailsOldStudentsForm" property="examRegisterNo">
		          										<bean:write name="tcDetailsOldStudentsForm" property="examRegisterNo"/>,&nbsp;
		          									</logic:notEmpty>
		          										<logic:notEmpty name="tcDetailsOldStudentsForm" property="month1">
		          										<bean:write name="tcDetailsOldStudentsForm" property="month1"/>&nbsp;
		          									</logic:notEmpty>
		          									<logic:notEmpty name="tcDetailsOldStudentsForm" property="yr">
		          										<bean:write name="tcDetailsOldStudentsForm" property="yr"/>
		          									</logic:notEmpty>
		          									</td>
		          								</tr>
		          								<tr>
		          									<td width="50%" align="left">
		          										&nbsp;
		          									</td>
		          									<td width="50%" class="fontClass">
		          										<bean:write name="tcDetailsOldStudentsForm" property="casteCategoryName"/>
		          									</td>
		          								</tr>
		          								<tr>
		          									<td colspan="2">
		          										&nbsp;
		          									</td>
		          									
		          								</tr>
		          								<tr>
		          									<td width="50%" align="left">
		          										&nbsp;
		          									</td>
		          									<td width="50%" class="fontClass">
		          										<bean:write name="tcDetailsOldStudentsForm" property="scolorship"/>
		          									</td>
		          								</tr>
		          								<tr>
		          									<td colspan="2">
		          										&nbsp;
		          									</td>
		          									
		          								</tr>
		          								<tr>
		          									<td width="50%" align="left">
		          										&nbsp;
		          									</td>
		          									<td width="50%" class="fontClass" >
		          										<bean:write name="tcDetailsOldStudentsForm" property="feePaid"/>
		          									</td>
		          								</tr>
		          								
		          								<tr>
		          									<td width="50%" align="left">
		          										&nbsp;
		          									</td>
		          									<td width="50%" class="fontClass">
		          										<bean:write name="tcDetailsOldStudentsForm" property="characterAndConduct"/>
		          									</td>
		          								</tr>
		          								<tr>
		          									<td width="50%" align="left">
		          										&nbsp;
		          									</td>
		          									<td width="50%" class="fontClass">
		          										<bean:write name="tcDetailsOldStudentsForm" property="dateOfApplication"/>
		          									</td>
		          								</tr>
		          								<tr>
		          									<td width="50%" align="left">
		          										&nbsp;
		          									</td>
		          									<td width="50%" class="fontClass">
		          										<bean:write name="tcDetailsOldStudentsForm" property="dateOfIssue"/>
		          									</td>
		          								</tr>
		          								<tr>
		          									<td width="50%" align="left">
		          										&nbsp;
		          									</td>
		          									<td width="50%" class="fontClass">
		          										<%=session.getAttribute("username").toString().toUpperCase()%>
		          									</td>
		          								</tr>
		          								
		          							</table>
		          						</td>
		          					</tr>
						        </table>
                	</td>
      			</tr>
		    </table>
		</td>
  </tr>
</table>
<script type="text/javascript">
	window.print();
</script>
<logic:empty name="tcDetailsOldStudentsForm" property="registerNo">
	<table width="100%" height="350px">
		<tr>
			<td align="center" valign="middle">
				No Records Found
			</td>
		</tr>
	</table>
</logic:empty>
</html:form>
</body>
