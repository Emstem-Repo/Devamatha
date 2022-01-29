<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>

<html:html>
<head>
<title><bean:message key="knowledgepro.title"/> </title>
<script language="JavaScript" src="js/calendar_us.js"></script>
<script language="JavaScript" src="js/admission/interviewprocess.js"></script>
<link href="css/styles.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="css/calendar.css">

<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 5px;
	margin-right: 0px;
	margin-bottom: 0px;
}
-->
</style>
</head>

<body>
<html:form action="ddStatus" method="post">
<html:hidden property="method" styleId="method" value="updateChallanUploadProcessForExam"/>
<html:hidden property="formName" value="dDStatusForm" />
<html:hidden property="pageType" value="6"/>




<table width="100%" border="0">
		<tr>
			<td><span class="heading"> <bean:message
				key="knowledgepro.admin" /> <span class="Bredcrumbs">&gt;&gt;
			Challan Upload Process On Date Wise &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> Challan Upload Process On Date Wise</strong></td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="122" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" cellspacing="1" cellpadding="2">
						<tr bgcolor="#FFFFFF">
							<td height="20" colspan="4">
							<div align="right"><FONT color="red"> <span
								class='MandatoryMark'><bean:message
								key="knowledgepro.mandatoryfields" /></span></FONT></div>
							<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
							<FONT color="green"> <html:messages id="msg"
								property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
							</html:messages> </FONT>
							</div>
							</td>
						</tr>


						
						
						
			<tr>
							<td valign="top" class="news">
							<table width="100%" border="0" align="center" cellpadding="0"
								cellspacing="0">
								<tr>
									<td><img src="images/01.gif" width="5" height="5" /></td>
									<td width="914" background="images/02.gif"></td>
									<td><img src="images/03.gif" width="5" height="5" /></td>
								</tr>
									
						
			 <tr>
            <td width="5"  background="images/left.gif"></td>
            <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
              <tr ><!--
                <td height="25" class="row-odd" ><div align="right"><span class="Mandatory">*</span><bean:message key="admissionForm.studentedit.admyear.label" /></div></td>
				<td height="25" class="row-even" >
					<input type="hidden" id="tempyear" name="tempyear" value="<bean:write name="dDStatusForm" property="academicYear"/>"/>
                 <html:select property="academicYear" styleId="academicYear" styleClass="combo" >
  	   				 <html:option value="">- Select -</html:option>
  	   				<cms:renderAcademicYear></cms:renderAcademicYear>
   			   </html:select>
				</td>
                --><td width="15%" class="row-odd" width="25%">
											<div align="right"><span class="Mandatory">*</span>&nbsp;Date:</div></td>
											<td width="16%" class="row-even">
												<html:text readonly="true" name="dDStatusForm" property="recievedChallanDate" styleId="recievedChallanDate" size="10" maxlength="16"/>
												<script
												language="JavaScript">
												new tcal( {
													// form name
													'formname' :'dDStatusForm',
													// input name
													'controlname' :'recievedChallanDate'
												});
											</script>
											</td>


										</tr>
										
										<tr bordercolor="">

											<td valign="top" colspan="1">
											<table width="100%" cellspacing="1" cellpadding="2">
												<tr>

													<td height="25" colspan="1" class="row-odd" align="right">
													Is Revaluation ?:</td>


													<td height="25" colspan="1" class="row-even">
													<div align="Center"><html:radio
														property="flagType" styleId="isRevaluation"
														value="isRevaluation">Yes</html:radio>

											</div>
													</td>
												</tr>
												
														<tr>

													<td height="25" colspan="1" class="row-odd" align="right">
													Is Scrutiny ?:</td>


													<td height="25" colspan="1" class="row-even">
													<div align="Center"><html:radio
														property="flagType" styleId="isScrutiny"
														value="isScrutiny">Yes</html:radio>

													
											</div>
													</td>
												</tr>
												
														<tr>

													<td height="25" colspan="1" class="row-odd" align="right">
													Is Challenge Valuation ?:</td>


													<td height="25" colspan="1" class="row-even">
													<div align="Center"><html:radio
														property="flagType" styleId="isChallengeValuation"
														value="isChallengeValuation">Yes</html:radio>
													
										</div>
													</td>
												</tr>


											</table>
											</td>
										</tr>
										
										
										
             
             
            </table></td>
            <td width="5" height="30"  background="images/right.gif"></td>
          </tr>
						
			<tr>
									<td height="5"><img src="images/04.gif" width="5"
										height="5" /></td>
									<td background="images/05.gif"></td>
									<td><img src="images/06.gif" /></td>
								</tr>
							</table>
							</td>
							<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
						</tr>			
						
						
						
						
						
						
						<tr>
							<td height="25" colspan="4">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td  height="35">
									<div align="center">
									<html:submit styleClass="formbutton" value="Submit"  ></html:submit>
									</div>
									</td>
									
								</tr>
							</table>
							</td>
						</tr>
						
						
						
					</table>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				<!--<tr>
							<td height="25" colspan="6">
							<table width="100%" border="0" align="center" cellpadding="0"
								cellspacing="0">
								<tr>
									<td><img src="images/01.gif" width="5" height="5"></td>
									<td width="914" background="images/02.gif"></td>
									<td><img src="images/03.gif" width="5" height="5"></td>
								</tr>
								<tr>
									<td height="5"><img src="images/04.gif" width="5"
										height="5"></td>
									<td background="images/05.gif"></td>
									<td><img src="images/06.gif"></td>
								</tr>
							</table>
							</td>
						</tr>
				--><tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td height="20" valign="top"></td>
					<td width="13" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				<tr>
					<td><img src="images/Tright_03_05.gif" width="9" height="29" /></td>
					<td width="100%" background="images/TcenterD.gif"></td>
					<td><img src="images/Tright_02.gif" width="9" height="29" /></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>





</html:form>
</body>
</html:html>

<script type="text/javascript">
var year = document.getElementById("tempyear").value;
if(year.length != 0) {
 	document.getElementById("academicYear").value=year;
}
</script>
