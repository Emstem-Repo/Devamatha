<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@page import="com.kp.cms.constants.CMSConstants"%>

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

function getCourses(programId) {
	getCoursesByProgram("coursesMap",programId,"course",updateCourses);
	
}

function updateCourses(req) {
	updateOptionsFromMap(req,"course","- Select -");
}

function submitForm()
{
	var admYear = document.getElementById("academicYear").value;
	var course = document.getElementById("course").value;
	var appNo = document.getElementById("applnnoID").value;
	
	var url = "AdmissionStatus.do?method=printAllotmentMemo&academicYear="+admYear+"&courseId="+course+"&applicationNo="+appNo;
	myRef = window.open(url,"AllotmentMemo","left=20,top=20,width=600,height=500,toolbar=2,resizable=0,scrollbars=1");
}

function resetForm()
{
	document.getElementById("academicYear").value="";
	document.getElementById("programtype").value="";
	document.getElementById("program").value="";
	document.getElementById("course").value="";
	document.getElementById("applnnoID").value=null;	
}
</script>
<html:form action="/AdmissionStatus" method="POST">
<html:hidden property="method" styleId="method" value=""/>
<html:hidden property="formName" value="admissionStatusForm" />
<html:hidden property="pageType" value="2"/>

<table width="98%" border="0" cellpadding="2" cellspacing="1">
  <tr>
    <td class="heading"><span class="Bredcrumbs"><bean:message key="knowledgepro.admission" />
			<span class="Bredcrumbs">&gt;&gt;<bean:message key="knowledgepro.admission.printAllotmentMemo"/> &gt;&gt;</span></span> </td>
  </tr>
  <tr>
    <td valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="5"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="body" ><strong class="boxheader"> <bean:message key="knowledgepro.admission.printAllotmentMemo"/></strong></td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td height="20" class="news"><div align="right" class="mandatoryfield"></div></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
	  
      <tr>
        <td height="40" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
          
          <tr><td>&nbsp;</td></tr>
	 <tr bgcolor="#FFFFFF">
				<div align="right"> <span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields"/></span></div>	
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
				<td height="25" class="row-odd" ><div align="right"><span class="Mandatory">*</span><bean:message key="admissionForm.studentedit.admyear.label" /></div></td>
				<td height="25" class="row-even" >
					<input type="hidden" id="tempyear" name="tempyear" value="<bean:write name="admissionStatusForm" property="academicYear"/>"/>
                 <html:select property="academicYear" styleId="academicYear" styleClass="combo">
  	   				 <html:option value="">- Select -</html:option>
  	   				<cms:renderAcademicYear></cms:renderAcademicYear>
   			   </html:select>
				</td>
				<td height="25" class="row-odd" ><div align="right"><bean:message key="knowledgepro.admission.programtype"/>:</div></td>
                <td height="25" class="row-even" >
                    <html:select property="programTypeId"  styleId="programtype" styleClass="combo" onchange="getPrograms(this.value)">
                 			<html:option value=""><bean:message key="knowledgepro.admin.select"/> </html:option>
				    			<html:optionsCollection name="admissionStatusForm" property="programTypeList" label="programTypeName" value="programTypeId"/>
	     			</html:select> 
                </td>
                </tr>
              <tr >
                
                <td height="25" class="row-odd" ><div align="right"><bean:message key="knowledgepro.admission.program"/>:</div></td>
                <td height="25" class="row-even" >
			      <html:select property="programId"  styleId="program" styleClass="combo" onchange="getCourses(this.value)">
          		   		<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
        			    	<c:if test="${admissionStatusForm.programTypeId != null && admissionStatusForm.programTypeId != ''}">
            					<c:set var="programMap" value="${baseActionForm.collectionMap['programMap']}"/>
            		    	 	<c:if test="${programMap != null}">
            		    	 		<html:optionsCollection name="programMap" label="value" value="key"/>
            		    	 	</c:if>	 
            		   		</c:if>
	           		</html:select>	   
				</td>
				<td height="25" class="row-odd" ><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.admission.course"/>:</div></td>
                <td class="row-even" >
             <html:select property="courseId"  styleId="course" styleClass="combo">
           				<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
         				   		<c:if test="${admissionStatusForm.programId != null && admissionStatusForm.programId != ''}">
             						<c:set var="courseMap" value="${baseActionForm.collectionMap['coursesMap']}"/>
             		    			<c:if test="${courseMap != null}">
             		    				<html:optionsCollection name="courseMap" label="value" value="key"/>
             		    			</c:if>	 
             		   			</c:if>
       			   </html:select>
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
        <td height="45" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0"> 
        </table></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="45" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">          

            <tr>
              <td ><img src="images/01.gif" width="5" height="5" /></td>
              <td width="914" background="images/02.gif"></td>
              <td><img src="images/03.gif" width="5" height="5" /></td>
            </tr>
            <tr>
              <td width="5"  background="images/left.gif"></td>
              <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
                <tr >
                  <td width="22%" height="25" class="row-odd" ><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.admission.appNo" /> </div></td>
                  <td width="24%" height="25" class="row-even" >					
					<html:text name="admissionStatusForm" property="applicationNo" styleClass="TextBox" styleId="applnnoID" size="12" maxlength="30" onkeypress="return isNumberKey(event)"/>
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
							<div align="right"><html:button property="" styleClass="formbutton" onclick="submitForm()">
								<bean:message key="knowledgepro.admission.printAllotmentMemo" />
							</html:button></div>
							</td>
							<td width="2%"></td>
							<td width="53"><html:button property="" styleClass="formbutton" value="Reset" onclick="resetForm()"></html:button></td>
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