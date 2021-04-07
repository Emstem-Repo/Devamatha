<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<head>
<script type="text/javascript">
function resetStudentListReport(){
	document.getElementById("classId").selectedIndex = 0;
	document.getElementById("columnHeading").value = "";
	resetErrMsgs();
}
function getClasses(year) {
	getClassesByYear("classMap", year, "classId", updateClasses);
}
function updateClasses(req) {
	updateOptionsFromMap(req, "classId", "- Select -");
}

function submitReport() {
	document.getElementById("className").value = document
			.getElementById("classId").options[document
			.getElementById("classId").selectedIndex].text;
	document.studentListForSignatureForm.submit();
}
</script>
</head>
<body>
<html:form action="/StudListForSign" method="post">
<html:hidden property="method" styleId="method" value="SearchStudentListForSignature"/>
<html:hidden property="formName" value="studentListForSignatureForm" />
<html:hidden property="pageType" value="1"/>
<html:hidden property="className" styleId="className"/>
<table width="98%" border="0" cellpadding="2" cellspacing="1">
   <tr>
	<td><span class="Bredcrumbs"><bean:message
		key="knowledgepro.reports" /> <span class="Bredcrumbs">&gt;&gt; <bean:message key="knowledgepro.report.stud.report.for.sign"/>
		&gt;&gt;</span></span></td>
  </tr>
  <tr>
    <td valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="5"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="body" ><strong class="boxheader"><bean:message key="knowledgepro.report.stud.report.for.sign"/></strong></td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
       <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td height="20" class="news"><div align="right" class="mandatoryfield"></div></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="45" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news">
        <table width="100%" align="center" border="0" cellpadding="0" cellspacing="0">
	         <tr bgcolor="#FFFFFF">
				<td height="20" colspan="2">
				<div align="right"><FONT color="red"> <span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields"/></span></FONT></div>
				<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
				<FONT color="green"> <html:messages id="msg"
					property="messages" message="true">
					<c:out value="${msg}" escapeXml="false"></c:out>
					<br>
				</html:messages> </FONT></div>
				</td>
			</tr>

            <tr>
              <td><img src="images/01.gif" width="5" height="5" /></td>
              <td width="914" background="images/02.gif"></td>
              <td><img src="images/03.gif" width="5" height="5" /></td>
            </tr>

          <tr>
            <td width="5" background="images/left.gif"></td>
            <td width="100%" valign="top">
            <table width="100%" cellspacing="1" cellpadding="2">
			<tr>
               <td height="25" class="row-odd" ><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.fee.academicyear.col" /></div></td>
	             <td height="25" class="row-even" >
	                 <html:select property="year" styleId="academicYear" styleClass="combo" onchange="getClasses(this.value)">
	  	   				 <html:option value="">- Select -</html:option>
	  	   				 <cms:renderYear></cms:renderYear>
	   			   </html:select>
                </td>
				<td height="25" class="row-odd" ><div align="right">&nbsp;</div></td>
	             <td height="25" class="row-even" >
	                &nbsp;
                </td>
              </tr>
              <tr>
                <td width="25%" height="25" class="row-odd" ><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.attendance.class.col" /></div></td>
				 <td width="25%" height="25" class="row-even" >
				 	<html:select property="classId"  styleId="classId" styleClass="combo" >
                 			<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
							<c:if test="${classMap!= null && classMap!= ''}">
			    				<html:optionsCollection name="classMap" label="value" value="key"/>
							</c:if>
	     			</html:select> 
                </td>
				<td height="25" class="row-odd" ><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.report.stud.report.for.sign.column.heading" /></div></td>
	             <td height="25" class="row-even" >
	                <html:text property="columnHeading" styleId="columnHeading" styleClass="text" size = "20" maxlength="30"></html:text>
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
					<div align="right"><html:button property=""  styleClass="formbutton" onclick="submitReport()">
						<bean:message key="knowledgepro.admin.search" /> </html:button>
					</div>
					</td>
					<td width="2%"></td>
					<td width="53"><html:button property="" styleClass="formbutton" value="Reset" onclick="resetStudentListReport()"></html:button></td>
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