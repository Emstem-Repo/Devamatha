<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>

<html:html>
<head>
<title>:: CMS ::</title>
<script language="JavaScript" src="js/calendar_us.js"></script>

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
<script type="text/javascript">

function resetStudentReport()
{
	
	document.getElementById("startDateid").value = "";
	document.getElementById("endDateId").value = "";	
	document.getElementById("optional_1").checked=false;
	document.getElementById("optional_2").checked=false;
	document.getElementById("optional_3").checked=false;
	document.getElementById("appRegRollNoId").value ="";
	document.getElementById("year").value = resetYear();
	resetErrMsgs();
}


</script>

<body>
<html:form action="/studentCollectionReport" method="post">
<html:hidden property="method" styleId="method" value="submitStudentReport"/>
<html:hidden property="formName" styleId="formName" value="studentCollectionReportForm"/>
<html:hidden property="pageType" value="1"/>


<table width="98%" border="0" cellpadding="2" cellspacing="1">
  <tr>
    <td class="heading"><bean:message key="knowledgepro.reports"/><span class="Bredcrumbs">&gt;&gt;<span class="Bredcrumbs">Student Collection Report&gt;&gt;</span></span> </td>
  </tr>
  <tr>
    <td valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="5"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="body" ><strong class="boxheader">Student Collection Report</strong></td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td height="20" class="news"><div align="right" class="mandatoryfield"></div></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="45" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
	          <tr bgcolor="#FFFFFF">
				<div align="right"> <span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields" /></span></div>	
	            <td colspan="6" class="body" align="left">
	             <div id="errorMessage">
	            <FONT color="red"><html:errors/></FONT>
	             <FONT color="green">
						<html:messages id="msg" property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out><br>
						</html:messages>
	            </FONT>
	            </div>
	            </td>
	          </tr>
	          
	          

            <tr>
              <td ><img src="images/01.gif" width="5" height="5" /></td>
              <td width="914" background="images/02.gif"></td>
              <td><img src="images/03.gif" width="5" height="5" /></td>
            </tr>
          <tr>
            <td width="5"  background="images/left.gif"></td>
            <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
			
			<tr>
				<td height="25" class="row-odd" ><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.petticash.AppRegRollNumber"/>:</div></td>
				<td height="25" class="row-even" >
					<html:text name="studentCollectionReportForm" property="appRegRollNo" styleId="appRegRollNoId" size="10" maxlength="10"/>
				 
                    <html:radio name="studentCollectionReportForm" property="appNo" styleId="optional_1" value="1" /> App.No
                    <html:radio name="studentCollectionReportForm" property="appNo" styleId="optional_2" value="2" /> Roll No
                    <html:radio name="studentCollectionReportForm" property="appNo" styleId="optional_3" value="3"/> Reg.No
                    
				</td>
				
				<td width="25%" height="25" class="row-odd" ><div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.fee.academicyear" /></div></td>
                              <td width="35%" class="row-even">
									<div align="left"><span class="star"> <input
										type="hidden" id="yr" name="yr"
										value='<bean:write name="studentCollectionReportForm" property="academicYear"/>' />
									<html:select property="academicYear" styleClass="combo"
										styleId="year">
										<html:option value="">-<bean:message
												key="knowledgepro.select" />-</html:option>
										<cms:renderYear>
										</cms:renderYear>
									</html:select> </span></div>
					</td>
									
              <td width="5" height="30"  background="../images/right.gif"></td>
              
			</tr>
			<tr>
			
			
               <td height="25" class="row-odd" ><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.petticash.startdate" /></div></td>
               <td width="26%" height="25" class="row-even" >
					<html:text name="studentCollectionReportForm" property="startDate" styleId="startDateid" size="16" maxlength="16"/>
							<script
							language="JavaScript">
							new tcal( {
								// form name
								'formname' :'studentCollectionReportForm',
								// input name
								'controlname' :'startDate'
							});
						</script>

			   </td>

               <td height="25" class="row-odd" ><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.petticash.enddate" />:</div></td>
               <td width="26%" height="25" class="row-even" >
					<html:text name="studentCollectionReportForm" property="endDate" styleId="endDateId" size="16" maxlength="16"/>
							<script
							language="JavaScript">
							new tcal( {
								// form name
								'formname' :'studentCollectionReportForm',
								// input name
								'controlname' :'endDate'
							});
						</script>

			   </td>

			</tr>
			
            </table></td>
            
            <td width="5" height="30"  background="images/right.gif"></td>
          </tr>
          <tr>
            <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
            <td background="images/05.gif"></td>
            <td><img src="images/06.gif" /></td>
          </tr>
        </table></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="36" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news">
		
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="45%" height="35">
							<div align="right"><html:submit styleClass="formbutton"><bean:message key="knowledgepro.admin.search" />
							</html:submit></div>
							</td>
							<td width="2%"></td>
							<td width="53"><html:button property="" styleClass="formbutton" value="Reset" onclick="resetStudentReport()"></html:button></td>
						</tr>
					</table>
		</td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="26" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news">&nbsp;</td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
        <td width="931" background="images/TcenterD.gif"></td>
        <td><img src="images/Tright_02.gif" width="9" height="29"></td>
      </tr>
    </table></td>
  </tr>
</table>
</html:form>
</body>
</html:html>
<script type="text/javascript">
</script>