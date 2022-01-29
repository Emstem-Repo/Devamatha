<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>


<head>
<title>:: CMS ::</title>
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
	document.getElementById("semister").selectedIndex = 0;
	
	resetErrMsgs();
}

function getNames(){

	document.getElementById("method").value="submitStudentsListReport";
		
}

</script>


<html:form action="/studentsList" method="post">
<html:hidden property="method" styleId="method" value="submitStudentsListReport"/>
<html:hidden property="pageType" value="1"/>
<html:hidden property="formName" value="listReportForm" />


<table width="98%" border="0" cellpadding="2" cellspacing="1">
  <tr>
    <td class="heading"><bean:message key="knowledgepro.reports"/><span class="Bredcrumbs">&gt;&gt;<bean:message key="knowledgepro.report.listof1styearstudents"/><span class="Bredcrumbs">&gt;&gt;</span> </td>
  </tr>
  <tr>
    <td valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="5"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="body" ><strong class="boxheader"><bean:message key="knowledgepro.report.listof1styearstudents"/><span class="Bredcrumbs"></strong></td>
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
				<div align="right"><span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields" /></span></div>	
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
                <td width="22%" height="25" class="row-odd" ><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.admin.program.type" /></div></td>
				 <input type="hidden" name="programTId" id="programTId" value='<bean:write name="listReportForm" property="programTypeId"/>'/>
                <td width="28%" height="25" class="row-even" >
                    <html:select property="programTypeId"  styleId="programtype" styleClass="comboLarge" onchange="getPrograms(this.value)">
                 			<html:option value=""><bean:message key="knowledgepro.admin.select"/> </html:option>
				    			<html:optionsCollection name="programTypeList" label="programTypeName" value="programTypeId"/>
	     			</html:select> 
                </td>
                <td width="19%" height="25" class="row-odd" ><div align="right"><bean:message key="knowledgepro.admin.prog" /></div></td>
                <td width="26%" height="25" class="row-even" >
			      <html:select property="programId"  styleId="program" styleClass="comboLarge">
          		   		<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
        			    	<c:if test="${listReportForm.programTypeId != null && listReportForm.programTypeId != ''}">
            					<c:set var="programMap" value="${baseActionForm.collectionMap['programMap']}"/>
            		    	 	<c:if test="${programMap != null}">
            		    	 		<html:optionsCollection name="programMap" label="value" value="key"/>
            		    	 	</c:if>	 
            		   		</c:if>
	           		</html:select>	   
				</td>
            </tr>
            <tr >
                <td height="25" class="row-odd" ><div align="right"><bean:message key="curriculumSchemeForm.schemeId" />:</div></td>
                <td height="25" class="row-even" >
					<html:select property="semister" styleId="semister" styleClass="comboMedium">
	 				 	<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
				    	<html:option value="1">1</html:option>
				    	<html:option value="2">2</html:option>
				    	<html:option value="3">3</html:option>
				    	<html:option value="4">4</html:option>
				    	<html:option value="5">5</html:option>
				    	<html:option value="6">6</html:option>
				    	<html:option value="7">7</html:option>
				    	<html:option value="8">8</html:option>
				    	<html:option value="9">9</html:option>
				    	<html:option value="10">10</html:option>
					</html:select>
				</td>
                <td height="25" class="row-odd" ></td>
				<td height="25" class="row-even" >&nbsp;</td>
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
						<div align="right"><html:submit styleClass="formbutton" onclick="getNames()">
							<bean:message key="knowledgepro.submit" /></html:submit>
						</div>
					</td>
					<td width="2%"></td>
					<td width="53">
						<html:button property="" styleClass="formbutton" onclick="resetAttReport()">
						<bean:message key="knowledgepro.admin.reset" /></html:button>
					</td>
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

<script type="text/javascript">
var programTypeId = document.getElementById("programTId").value;
if(programTypeId.length != 0){
	document.getElementById("programtype").value= programTypeId;
}

</script>