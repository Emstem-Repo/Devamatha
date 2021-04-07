<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">

<script type="text/javascript">

function getCourse(year) {
	getCourseByYear("courseMap", year, "courseId", updateClasses);		
}
function updateClasses(req) {
	updateOptionsFromMap(req, "courseId", "- Select -");
}

function save(){
	document.getElementById("method").value = "saveSemesterWiseCourse";
	document.semesterWiseCourseEntryForm.submit();
}

</script>

<html:form action="/SemesterWiseCourseEntry" method="post">

<html:hidden property="formName" value="semesterWiseCourseEntryForm" />
<html:hidden property="method" styleId="method" />
<html:hidden property="pageType" value="1"/>


	
<table width="98%" border="0">
  <tr>
    <td><span class="heading">Fee<span class="Bredcrumbs">&gt;&gt;Semester Wise Course Entry &gt;&gt;</span></span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="10"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="body" ><div align="left"><strong class="boxheader">Semester Wise Course Entry</strong></div></td>
        <td width="13" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="122" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><div align="center">
              <table width="100%" height="185"  border="0" cellpadding="0" cellspacing="0">
                 <tr>
               	    <td height="20" colspan="6" align="left">
               	    <div align="right" style="color:red"> <span class='MandatoryMark'>* Mandatory fields</span></div>
               	    <div id="err" style="color:red;font-family:arial;font-size:11px;"></div>
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
                    <td height="35" colspan="6" class="body" >
			        <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
	                     <tr>
	                       <td ><img src="images/01.gif" width="5" height="5" /></td>
	                       <td width="914" background="images/02.gif"></td>
	                       <td><img src="images/03.gif" width="5" height="5" /></td>
	                     </tr>
	                     <tr>
	                       <td width="5"  background="images/left.gif"></td>
	                       <td valign="top">
	                       <table width="100%" cellspacing="1" cellpadding="2">
	                       
	                             
					        	<tr>
			                       <td class="row-odd" width="25%"><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.fee.academicyear"/>:</div></td>
				        		   <td class="row-even" align="left" width="25%">
				                           <input type="hidden" id="tempyear" name="tempyear" value="<bean:write name="semesterWiseCourseEntryForm" property="academicYear"/>"/>
				                           <html:select property="academicYear" styleId="academicYear" name="semesterWiseCourseEntryForm" styleClass="combo">
		                       	   				 <html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
		                       	   				<cms:renderAcademicYear></cms:renderAcademicYear>
		                       			   </html:select>
				        			</td>
				                  <td class="row-odd" width="25%">
			                 		 <div id="classsdiv" align="right"><span class='MandatoryMark'>*</span>Course:</div>
					               </td>
					               <td class="row-even" width="25%">
					                  <html:select name="semesterWiseCourseEntryForm" styleId="courseId" property="courseId">
										<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
										<logic:notEmpty name="semesterWiseCourseEntryForm" property="courseMap">
											<html:optionsCollection property="courseMap" name="semesterWiseCourseEntryForm" label="value" value="key" />
										</logic:notEmpty>
									</html:select>
					                  
				                  </td>
				                </tr>
				                <tr>
									<td height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>&nbsp;is Semester Wise:</div>
									</td>
									<td height="25" class="row-even">
									 <html:radio name="semesterWiseCourseEntryForm" property="isSemesterWise" styleId="isSemesterWise" value="true" /> Yes 
					                <html:radio name="semesterWiseCourseEntryForm" property="isSemesterWise" styleId="isSemesterWisefalse" value="false" /> No
									
									</td>
				                </tr>
				                
	                       </table>
	                       </td>
	                       <td width="5" height="30"  background="images/right.gif"></td>
	                     </tr>
                     <tr>
                       <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
                       <td background="images/05.gif"></td>
                       <td><img src="images/06.gif" /></td>
                     </tr>
                   </table>
                   </td>
                 </tr>
                 <tr>
                   <td height="35" colspan="6" class="body" >
                   <table width="100%" height="27"  border="0" cellpadding="0" cellspacing="0">
                     <tr><td style="height: 10px" align="center" colspan="3"></td></tr>
                     <tr>
                       <td width="45%"><div align="right">
                       <html:submit styleClass="formbutton" value="Submit"  onclick="save()"></html:submit>
                       </div></td>
                       <td width="2%"></td>
                       <td width="53%" height="45" align="left">
                   	 		<html:button property="" styleClass="formbutton" value="Reset" onclick="resetFieldAndErrMsgs()"></html:button>
                       </td>
                     </tr>                     
                     <tr><td style="height: 10px" align="center" colspan="3"></td></tr>                     
                   </table>
                   </td>
                </tr>
              </table>
            </div></td>
        <td width="13" valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
        <td width="100%" background="images/TcenterD.gif"></td>
        <td><img src="images/Tright_02.gif" width="9" height="29"></td>
      </tr>
    </table></td>
  </tr>
</table>
</html:form>
<script type="text/javascript">
	var year = document.getElementById("tempyear").value;
	if (year.length != 0) {
		document.getElementById("academicYear").value = year;
	}
	document.getElementById("isSemesterWisefalse").checked = true;
</script>