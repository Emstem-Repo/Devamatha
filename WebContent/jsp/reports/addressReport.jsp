<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
	<link rel="stylesheet" href="css/calendar.css">
	<script language="JavaScript" src="js/admission/admissionform.js"></script>
<script type="text/javascript">
function getPrograms(programTypeId) {
	getProgramsByType("programMap",programTypeId,"program",updatePrograms);
}

function updatePrograms(req) {
	updateOptionsFromMap(req,"program","- Select -");
}


function resetAttReport()	{
	document.getElementById("programtype").selectedIndex = 0;
	document.getElementById("program").selectedIndex = 0;
document.getElementById("startRegisterid").value="";
document.getElementById("endRegisterid").value="";
document.getElementById("wname").checked="";
document.getElementById("fname").checked="";
document.getElementById("Parents Address").value="Parents Address";
resetOption("program");
	resetErrMsgs();
}


</script>

<html:form action="addressReport" method="post">
<html:hidden property="method" styleId="method" value="submitAddressReport"/>
	<html:hidden property="formName" value="addressReportForm"/>
	<html:hidden property="pageType" value="1"/>
<table width="98%" border="0" cellpadding="2" cellspacing="1">
  <tr>
    <td class="heading"><bean:message key="knowledgepro.reports"/><span class="Bredcrumbs">&gt;&gt;<span class="Bredcrumbs"><bean:message key="knowledgepro.reports.student.AddressReport"/>&gt;&gt;</span></span> </td>
  </tr>
  <tr>
    <td valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="5"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="body" ><strong class="boxheader"> <bean:message key="knowledgepro.reports.students.Address"/></strong></td>
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
              <tr >
                <td width="22%" height="25" class="row-odd" ><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.admin.program.type" /></div></td>
				     <td width="28%" height="25" class="row-even" >
                    <html:select property="programTypeId"  styleId="programtype" styleClass="combo" onchange="getPrograms(this.value)">
                 			<html:option value=""><bean:message key="knowledgepro.admin.select"/> </html:option>
				    			<html:optionsCollection name="programTypeList" label="programTypeName" value="programTypeId"/>
	     			</html:select> 
                </td>
                <td width="19%" height="25" class="row-odd" ><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.admin.prog" /></div></td>
                <td width="26%" height="25" class="row-even" >
			      <html:select property="programId"  styleId="program" styleClass="combo">
          		   		<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
        			    	<c:if test="${addressReportForm.programTypeId != null && addressReportForm.programTypeId != ''}">
            					<c:set var="programMap" value="${baseActionForm.collectionMap['programMap']}"/>
            		    	 	<c:if test="${programMap != null}">
            		    	 		<html:optionsCollection name="programMap" label="value" value="key"/>
            		    	 	</c:if>	 
            		   		</c:if>
	           		</html:select>	   
				</td>
              </tr>
              <tr >
                <td height="25" class="row-odd" ><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.reports.students.AddressType" /></div></td>
                <td class="row-even" >
             <html:select property="addressType"  styleId="program" styleClass="comboLarge">
          		   		<html:option value="Parents Address">Parents Address</html:option>
        			    <html:option value="Current Address">Current Address</html:option>
        			    <html:option value="Permanent Address">Permanent Address</html:option>
	           		</html:select>	
				</td>
                <td height="25" class="row-odd" ><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.admitted.Year" /></div></td>
                <td height="25" colspan="4" class="row-even" >
				<input type="hidden" id="tempyear" name="tempyear" value="<bean:write name="addressReportForm" property="academicYear"/>"/>
                 <html:select property="academicYear" styleId="academicYear" styleClass="combo" onchange="getSemisters(this.value)">
  	   				 <html:option value="">- Select -</html:option>
  	   				 <cms:renderYear></cms:renderYear>
   			   </html:select>
                </td>

              </tr>             	
					<tr>
					<td height="25" class="row-odd">
					<div align="right"><bean:message key="knowledgepro.registernofrom"/>:</div>
					</td>
					<td width="26%" height="25" class="row-even"><html:text
						property="startRegisterNo" styleId="startRegisterid" 
						maxlength="16" /></td>

					<td height="25" class="row-odd">
					<div align="right"><bean:message key="knowledgepro.registernoto"/>:</div>
					</td>
					<td width="26%" height="25" class="row-even"><html:text
						property="endRegisterNo" styleId="endRegisterid" 
						maxlength="16" /></td>
				</tr>
								
			<tr>
             	<td height="25" class="row-odd">
				<div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.reports.students.Genarate" /></div>
				</td>
				<td width="26%" height="25" class="row-even" >
					<html:radio styleId="wname" property="withName" value="false"><bean:message key="knowledgepro.reports.students.WithStudent" /></html:radio>
				</td>
				<td width="26%" height="25" class="row-even" > 
					<html:radio property="withName" value="true" styleId="fname"><bean:message key="knowledgepro.reports.students.WithFather" /></html:radio>
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
							<div align="right"><html:submit styleClass="formbutton">
								<bean:message key="knowledgepro.admin.search" />
							</html:submit></div>
							</td>
							<td width="2%"></td>
							<td width="53"><html:button property="" styleClass="formbutton" value="Reset" onclick="resetAttReport()"></html:button></td>
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
