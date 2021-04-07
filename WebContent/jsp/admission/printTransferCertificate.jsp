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
<html:form action="/transferCertificate">

<html:hidden property="formName" value="transferCertificateForm" />
<html:hidden property="method" styleId="method" value="initTransferCertificate"/>
<html:hidden property="pageType" value="3"/>
<logic:notEmpty name="transferCertificateForm" property="studentList">
<table width="100%" border="0" >
	<tr>
    	<td valign="top">
    		<table width="100%" border="0" cellpadding="0" cellspacing="0" >
      			<tr>
        			<td valign="top" class="news">
        				
							<logic:iterate id="studentList" name="transferCertificateForm" property="studentList" indexId="count">
		        		        <table width="100%" border="0" cellpadding="0" cellspacing="0">
		          					<c:choose>
								<c:when test="${count== 0}">
									<tr height="198px">
									<logic:equal value="true" property="reprint" name="transferCertificateForm">
										<td valign="middle">
											<logic:equal value="Discontinued" property="tcType" name="studentList">
												<font class="heading4">Discontinued</font>
											</logic:equal>
										</td>
									</logic:equal>
									<logic:equal value="false" property="reprint" name="transferCertificateForm">
									<td valign="middle">
											<logic:equal value="yes" property="duplicate" name="transferCertificateForm">
												<font class="heading4">Duplicate</font>
											</logic:equal>
											<logic:equal value="Discontinued" property="tcType" name="transferCertificateForm">
												<font class="heading4">Discontinued</font>
											</logic:equal>
											<logic:equal value="Duplicate(Discontinued)" property="tcType" name="transferCertificateForm">
												<font class="heading4">(Discontinued)</font>
											</logic:equal>
										</td>
									</logic:equal>
		          					</tr>
								</c:when>
								<c:otherwise>
									<tr height="205px">
									<logic:equal value="true" property="reprint" name="transferCertificateForm">
										<td valign="middle">
											<logic:equal value="Discontinued" property="tcType" name="studentList">
												<font class="heading4">Discontinued</font>
											</logic:equal>
										</td>
									</logic:equal>
										<logic:equal value="false" property="reprint" name="transferCertificateForm">
									<td valign="middle">
											<logic:equal value="yes" property="duplicate" name="transferCertificateForm">
												<font class="heading4">Duplicate</font>
											</logic:equal>
											<logic:equal value="Discontinued" property="tcType" name="transferCertificateForm">
												<font class="heading4">Discontinued</font>
											</logic:equal>
											<logic:equal value="Duplicate(Discontinued)" property="tcType" name="transferCertificateForm">
												<font class="heading4">(Discontinued)</font>
											</logic:equal>
										</td>
									</logic:equal>
		          					</tr>
								</c:otherwise>
							</c:choose>
		          					
										
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
		          													<bean:write name="studentList" property="slNo"/></b>
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
		          													<bean:write name="studentList" property="tcNo"/></b>
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
		          										<bean:write name="studentList" property="studentName"/>
		          									</td>
		          								</tr>
		          								<tr>
		          									<td width="50%" align="left">
		          										&nbsp;
		          									</td>
		          									<td width="50%" class="fontClass">
		          										<bean:write name="studentList" property="studentNo"/>
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
		          										<bean:write name="studentList" property="dobFigures"/>
		          									</td>
		          								</tr>
		          								<tr>
		          									<td width="50%" align="left">
		          										&nbsp;
		          									</td>
		          									<td width="50%" class="fontClass">
		          										<bean:write name="studentList" property="dobWords"/>
		          									</td>
		          								</tr>
		          								<tr height="2px">
		          								</tr>
		          								<tr>
		          									<td width="50%" align="left">
		          										&nbsp;
		          									</td>
		          									<td width="50%" class="fontClass">
		          										<bean:write name="studentList" property="sex"/>
		          									</td>
		          								</tr>
		          								<tr>
		          									<td width="50%" align="left">
		          										&nbsp;
		          									</td>
		          									<td width="50%" class="fontClass">
		          										<bean:write name="studentList" property="religion"/>
		          									</td>
		          								</tr>
		          								<tr>
		          									<td width="50%" align="left">
		          										&nbsp;
		          									</td>
		          									<td width="50%" class="fontClass">
		          										<bean:write name="studentList" property="nationality"/>
		          									</td>
		          								</tr>
		          								<tr>
		          									<td width="50%" align="left">
		          										&nbsp;
		          									</td>
		          									<td width="50%" class="fontClass">
		          										<bean:write name="studentList" property="fatherName"/>
		          									</td>
		          								</tr>
		          								<tr>
		          									<td width="50%" align="left">
		          										&nbsp;
		          									</td>
		          									<td width="50%" class="fontClass">
		          										<bean:write name="studentList" property="motherName"/>
		          									</td>
		          								</tr>
		          								<tr>
		          									<td width="50%" align="left">
														&nbsp;	          										
		          									</td>
		          									<td width="50%" class="fontClass">
		          										<bean:write name="studentList" property="dateOfAdmission"/>
		          										
		          										
		          									</td>
		          								</tr>
		          								<tr>
		          									<td width="50%" align="left">
														&nbsp;	          										
		          									</td>
		          									<td width="50%" class="fontClass">
		          										<bean:write name="studentList" property="dateOfLeaving"/>
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
		          									<logic:empty name="studentList" property="classOfLeaving">
		          										<bean:write name="studentList" property="className"/>
		          										</logic:empty>
		          									<bean:write name="studentList" property="classOfLeaving"/>
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
	          											<bean:write name="studentList" property="subjectsPart1"/>
		          									</td>
		          									
		          								</tr>
		          								<tr>
		          									<td width="50%" align="left">
		          										&nbsp;
		          									</td>
		          									<td width="50%" style="font-size: 8pt" valign="middle" class="fontClass">
	          											<bean:write name="studentList" property="subjectsPart2"/>
		          									</td>
		          									
		          								</tr>
		          								<tr height="30px">
		          								</tr>
		          								<tr>
		          									<td width="50%" align="left">
		          										&nbsp;
		          									</td>
		          									<td width="50%" class="fontClass">
		          										<bean:write name="studentList" property="passed"/>
		          									</td>
		          								</tr>
		          								<tr height="2px">
		          								</tr>
		          								<tr>
		          									<td width="50%" align="left">
		          										&nbsp;
		          									</td>
		          									<td width="50%" class="fontClass">
		          										<bean:write name="studentList" property="publicExamName"/>
		          									</td>
		          								</tr>
		          								<tr>
		          									<td width="50%" align="left">
		          										&nbsp;
		          									</td>
		          									<logic:notEqual value="Discontinued" property="tcType" name="studentList">
		          									<td width="50%" class="fontClass">
		          									<bean:write name="studentList" property="regMonthYear"/>
		          									</td>
		          									 </logic:notEqual>
		          								</tr>
		          								<tr>
		          									<td width="50%" align="left">
		          										&nbsp;
		          									</td>
		          									<td width="50%" class="fontClass">
		          										<bean:write name="studentList" property="caste"/>
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
		          										<bean:write name="studentList" property="scholarship"/>
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
		          										<bean:write name="studentList" property="feePaid"/>
		          									</td>
		          								</tr>
		          								
		          								<tr>
		          									<td width="50%" align="left">
		          										&nbsp;
		          									</td>
		          									<td width="50%" class="fontClass">
		          										<bean:write name="studentList" property="conduct"/>
		          									</td>
		          								</tr>
		          								<tr>
		          									<td width="50%" align="left">
		          										&nbsp;
		          									</td>
		          									<td width="50%" class="fontClass">
		          										<bean:write name="studentList" property="dateOfApplication"/>
		          									</td>
		          								</tr>
		          								<tr>
		          									<td width="50%" align="left">
		          										&nbsp;
		          									</td>
		          									<td width="50%" class="fontClass">
		          										<bean:write name="studentList" property="dateOfIssue"/>
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
						       
							</logic:iterate>
				       
                	</td>
      			</tr>
		    </table>
		</td>
  </tr>
</table>
<script type="text/javascript">
	window.print();
</script>
</logic:notEmpty>
<logic:empty name="transferCertificateForm" property="studentList">
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
