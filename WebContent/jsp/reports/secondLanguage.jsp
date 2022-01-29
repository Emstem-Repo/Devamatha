<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<script type="text/javascript">
function getPrograms(programTypeId) {
	getProgramsByType("programMap",programTypeId,"program",updatePrograms);
	resetOption("course");
}

function updatePrograms(req) {
	updateOptionsFromMap(req,"program","- Select -");
}
function getCourses(programId) {
	getCoursesByProgram("courseMap", programId, "course", updateCourses);
}
function updateCourses(req) {
	updateOptionsFromMap(req, "course", "- Select -");
}
function resetAttReport()	{	
	document.getElementById("programtype").selectedIndex = 0;
	document.getElementById("program").selectedIndex = 0;
	document.getElementById("course").selectedIndex = 0;
	document.getElementById("semister").selectedIndex = 0;	
	document.getElementById("secondLanguage").selectedIndex = 0;
	document.getElementById("feesPaid").checked = false;
	document.getElementById("challanGenerated").checked = false;
	document.getElementById("all").checked = false;		
	document.getElementById("appNo").checked = false;
	document.getElementById("rollNo").checked = false;
	document.getElementById("regNo").checked = false;						
	resetErrMsgs();
	resetOption("program");
	resetOption("course");
}

function getNames(){
	document.getElementById("programTypeName").value = document
	.getElementById("programtype").options[document
	.getElementById("programtype").selectedIndex].text;
	
	document.getElementById("method").value="submitSecondLaguage";		
	document.secondLanguageForm.submit();
}
</script>
<html:form action="/SecondLanguage" method="post">
<html:hidden property="method" styleId="method"/>
<html:hidden property="pageType" value="1"/>
<html:hidden property="formName" value="secondLanguageForm" />
<html:hidden property="programTypeName" styleId="programTypeName" />
<table width="98%" border="0" cellpadding="2" cellspacing="1">
  <tr>
    <td class="heading"><bean:message key="knowledgepro.reports"/><span class="Bredcrumbs">&gt;&gt;<bean:message key="knowledgepro.reports.secondlanguage/admitted"/> &gt;&gt;</span> </td>
  </tr>
  <tr>
    <td valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="5"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="body" ><strong class="boxheader"><bean:message key="knowledgepro.reports.secondlanguage/admitted"/></strong></td>
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
				 <input type="hidden" name="programTId" id="programTId" value='<bean:write name="secondLanguageForm" property="programTypeId"/>'/>
                <td width="28%" height="25" class="row-even" >
                    <html:select property="programTypeId"  styleId="programtype" styleClass="comboLarge" onchange="getPrograms(this.value)">
                 			<html:option value=""><bean:message key="knowledgepro.admin.select"/> </html:option>
				    			<html:optionsCollection name="programTypeList" label="programTypeName" value="programTypeId"/>
	     			</html:select> 
                </td>
                <td width="19%" height="25" class="row-odd" ><div align="right"><bean:message key="knowledgepro.admin.prog" /></div></td>
                <td width="26%" height="25" class="row-even" >
			      <html:select property="programId"  styleId="program" styleClass="comboLarge" onchange="getCourses(this.value)">
          		   		<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
        			    	<c:if test="${secondLanguageForm.programTypeId != null && secondLanguageForm.programTypeId != ''}">
            					<c:set var="programMap" value="${baseActionForm.collectionMap['programMap']}"/>
            		    	 	<c:if test="${programMap != null}">
            		    	 		<html:optionsCollection name="programMap" label="value" value="key"/>
            		    	 	</c:if>	 
            		   		</c:if>
	           		</html:select>	   
				</td>
            </tr>
            <tr >
            <td height="25" class="row-odd" ><div align="right"><bean:message key="knowledgepro.admin.course.with.col" /></div></td>
                <td height="25" class="row-even" >
					<html:select property="courseId" styleClass="comboLarge" styleId="course">
					<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
					<c:if test="${courseMap != null}">
					<html:optionsCollection name="courseMap" label="value" value="key" />
					</c:if>
					</html:select>
				</td>           
                <td width="19%" height="25" class="row-odd" ><div align="right"><span class="Mandatory">*</span><bean:message key="curriculumSchemeForm.schemeId" />:</div></td>
                <td width="26%" height="25" class="row-even" >
                <input type="hidden"
								id="scheme" name="scheme"
								value='<bean:write name="secondLanguageForm" property="semister"/>' />
					<html:select property="semister" styleId="semister" styleClass="comboMedium">
	 				 	<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
				    	<cms:renderSchemeOrCourse></cms:renderSchemeOrCourse>
					</html:select>
				</td>
            </tr>
            <tr >
            <td height="25" class="row-odd" ><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.applicationform.secLang.label"/> </div></td>
                <td height="25" class="row-even" >
					<html:select property="secondLanguage" styleClass="comboLarge" styleId="secondLanguage">
					<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
					<c:if test="${secondLanguageMap != null}">
					<html:optionsCollection name="secondLanguageMap" label="value" value="key" />
					</c:if>
					</html:select>
				</td>           
                <td height="25" class="row-odd" align="right">
               	<bean:message key="knowledgepro.admission.ExportPhotos.admittedYear"/>
                </td>
                <td height="25" class="row-even">
		              <input type="hidden" id="tempyear" name="tempyear" value="<bean:write name="secondLanguageForm" property="year"/>" />
											<html:select name="secondLanguageForm" property="year" styleId="appliedYear" styleClass="combo">
												<html:option value=" "><bean:message key="knowledgepro.admin.select" /></html:option>
												<cms:renderYear></cms:renderYear>
											</html:select>
                </td>
            </tr>
            <tr>
            <td height="25" class="row-odd"><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.feereport.feereport.feestatus"/></div> </td>
            <td width="26%" height="25" class="row-even" ><html:radio styleId="feesPaid"
					property="feeStatus" value="1"><bean:message key="knowledgepro.reports.fees.paid"/> </html:radio>
					</td>
					 <td width="26%" height="25" class="row-even" >
					 <html:radio
					property="feeStatus" value="2" styleId="challanGenerated"><bean:message key="knowledgepro.reports.fees.challan.generated"/> </html:radio></td>
					 <td width="26%" height="25" class="row-even" >
					<html:radio styleId="all"
					property="feeStatus" value="3"><bean:message key="knowledgepro.reports.fees.all"/></html:radio>
				</td>
            </tr>
             <tr>
             <td height="25" class="row-odd"><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.hostel.serachby.col"/> </div> </td>
            <td width="26%" height="25" class="row-even" ><html:radio styleId="appNo"
					property="searchBy" value="1"><bean:message key="knowledgepro.interview.ApplicationNo"/> </html:radio>
					</td>
					 <td width="26%" height="25" class="row-even" > 
					<html:radio
					property="searchBy" value="2" styleId="rollNo"><bean:message key="knowledgepro.attendance.rollno"/> </html:radio></td>
					 <td width="26%" height="25" class="row-even" >
					<html:radio styleId="regNo"
					property="searchBy" value="3"><bean:message key="knowledgepro.attendance.regno"/> </html:radio>
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
var schemeId = document.getElementById("scheme").value;
if (schemeId != null && schemeId.length != 0) {
	document.getElementById("semister").value = schemeId;
}
var year = document.getElementById("tempyear").value;
if(year.length != 0) {
 	document.getElementById("appliedYear").value=year;
}
</script>