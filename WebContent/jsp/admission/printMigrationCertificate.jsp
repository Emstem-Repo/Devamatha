<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms" %>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<%@page import="com.kp.cms.constants.CMSConstants"%>
<head>
<link href="css/styles.css" rel="stylesheet" type="text/css">
<title><bean:message key="knowledgepro.admission.migrationcertificate"/></title>
<style type="text/css">
	.fontClass
	{
		font-weight: bold;
		font-family: Copperplate;
		font-size: 12pt;

	}
	.fontClassName
	{
		font-weight: bold;
		font-family: Baskerville old face;
		font-size: 12pt;

	}
	.heading1
	{
		font-weight: bold;
		font-family: Rockwell;
		font-size: 14pt;
		
	}
	.heading2
	{
		font-weight: bold;
		font-family: Lucida Sans;
		font-size: 12pt;
	}
	.heading4
	{
		font-family: Monotype Corsiva;
		font-size: 12pt;
	}
	.heading5
	{
		font-weight: bold;
		font-family: Rockwell;
		font-size: 12pt;
	}
	
	
</style>

</head>
<body>
<html:form action="/migrationCertificate">
<html:hidden property="formName" value="migrationCertificateForm" />
<html:hidden property="method" styleId="method" value="initMigrationCertificate"/>
<html:hidden property="pageType" value="2"/>
<logic:equal name="migrationCertificateForm" property="showRecord" value="YES">
<table width="100%" cellpadding="0" cellspacing="0" align="center">
      		<tr>
        		<td valign="top" class="news">
		        	<table width="100%" border="0" rules="none" cellpadding="3" cellspacing="3" align="center">
						<tr>
							<td width="100%" valign="middle">
								<table width="100%" align="center">
								<tr height="2px">
										<td>
										&nbsp;
										</td>
								</tr>
									<tr>
										<td><br>
										&nbsp;<br>
										</td>
									</tr>
									
									<tr>
										<td><br>
											&nbsp;<br>
										</td>
									</tr>
									<tr>
										<td>
											&nbsp;
										</td>
									</tr>
									<tr>
										<td align="left" valign="top">
											<font class="heading2">No.</font>
												&nbsp;
											<font class="heading5"><bean:write name="migrationCertificateForm" property="migrationCertificateNo"/></font>
										</td>
									</tr>
								</table>
         					</td>
         				</tr>
         					
    						<tr>
    									<td><br>
											&nbsp;
										</td>
    						</tr>
    						<tr>
										<td><br>
										&nbsp;
										</td>
									</tr>
    						    						
    						<tr height="40">
    									<td align="center">
			    							<font class="heading4">It is hereby stated that</font>
			    						</td>
			    			</tr>
			    			<tr>
										<td height="3">
										
										</td>
							</tr>
	    					<tr>
			    						<td align="center">
			    							<font class="fontClassName">
			    							<bean:write name="migrationCertificateForm" property="studentName"/></font>
			    						</td>
	    					</tr>
	    					<tr>
										<td height="3">
										
										</td>
							</tr>
			    					<tr>
			    						<td align="center">
			    							<font class="heading4">had been a student of this University studied in class of</font>
			    						</td>
			    					</tr>
			    					<tr>
										<td height="3">
										
										</td>
							</tr>
			    					<tr>
			    						<td align="center">
			    							<font class="fontClass">
			    							<bean:write name="migrationCertificateForm" property="studentCourse"/></font>
			    						</td>
			    					</tr>
			    					<tr>
										<td height="3">
										
										</td>
							</tr>
			    					<tr>
			    						<td align="center">
			    							<font class="heading4">with register number</font>
			    						</td>
			    					</tr>
			    					<tr>
										<td height="3">
										
										</td>
							</tr>
			    					<tr>
			    						<td align="center">
			    							<font class="fontClass">
			    							<bean:write name="migrationCertificateForm" property="studentRegNo"/></font> 
			    						</td>
			    					</tr>
			    					<tr>
										<td height="3">
										
										</td>
							</tr>
			    					<tr>
			    						<td align="center">
			     							<font class="heading4">completed/discontinued  his/her studies during the academic year</font>
			     						</td>
			     					</tr>
			     					<tr>
										<td height="3">
										
										</td>
							</tr>
			     					<tr>
			     						<td align="center">
			     							<font class="fontClass">
			     							<bean:write name="migrationCertificateForm" property="studentAcademicYearFrom"/>&nbsp;-&nbsp;<bean:write name="migrationCertificateForm" property="studentAcademicYearTo"/></font><font class="heading4">.</font>
			     								 
       									</td>
       								</tr>
       								<tr>
										<td height="3">
										
										</td>
							</tr>
       								<tr>
			    						<td align="center">
						    				<font class="heading4">The student has/has not been involved in any act of ragging or</font> 
			    						</td>
			    			</tr>
			    			<tr>
										<td height="3">
										
										</td>
							</tr>
							
			    					<tr>
			    						<td align="center">
			    							<font class="heading4">related acts directly or indirectly.</font>
			    						</td>
			    					</tr>
			    					<tr height="20">
										<td>
										&nbsp;
										</td>
							</tr>
			    												
			    					<tr>
			    						<td align="center">
			    							<font class="heading4">Christ University has no objection to continue his/her studies</font>
			    						</td>
			    					</tr>
			    					<tr>
										<td height="3">
										
										</td>
							</tr>
			    					<tr>
			    						<td align="center">
			    							<font class="heading4">in any other University.</font>
			    						</td>
			    					</tr>
			    					<tr height="80">
										<td>
										&nbsp;
										</td>
							</tr>
			    			
       					<tr>
       						<td colspan="2">
       							<table width="100%" align="center">
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
    					<tr>
    						<td width="100%" align="left">
    							<table width="100%" align="center">
    								<tr>
    									<td width="10%">
    										<font class="heading2">Date :</font>
    									</td>
    									<td width="90%">
    										<font class="heading5">
    										<bean:write name="migrationCertificateForm" property="migrationDate"/></font>	
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

</logic:equal>
<logic:equal name="migrationCertificateForm" property="showRecord" value="NO">
	<table width="100%" height="435px" align="center">
		<tr>
			<td align="center" valign="middle">
				No Records Found
			</td>
		</tr>
	</table>
</logic:equal>
<logic:equal name="migrationCertificateForm" property="showRecord" value="NOPREFIX">
	<table width="100%" height="435px" align="center">
		<tr>
			<td align="center" valign="middle">
				Please Enter Prefix Number in Student Certificate Number Master Screen.
			</td>
		</tr>
	</table>
</logic:equal>
</html:form>
</body>