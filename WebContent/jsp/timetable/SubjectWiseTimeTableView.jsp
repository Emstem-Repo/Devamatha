<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">

<script type="text/javascript">

function getSemistersByCourse(courseId) {
	var academicYear = document.getElementById("academicYr").value;
	getSemistersOnYearAndCourseScheme("semisterMap", courseId, "termNo",
			academicYear, updateSemisters);
}

function getSubjectsOnCourseTermYear(termNo){
	var academicYear = document.getElementById("academicYr").value;
	var courseId=document.getElementById("course").value;
	getSubjectsByCourseTermYear("subjectMap",courseId,academicYear,termNo,"subject",updateSubjects);
}

function updateSubjects(req)
{
	updateOptionsFromMap(req, "subject", "- Select -");
}


function resetFields() {
	document.getElementById("course").selectedIndex = 0;
	document.getElementById("termNo").selectedIndex = 0;
	document.getElementById("sectionName").value = "";
	document.getElementById("className").value = "";
	resetErrMsgs();
}
function updateSemisters(req) {
	updateOptionsFromMap(req, "termNo", "- Select -");
}

</script>

<html:form action="/subjectWiseTimeTableView" method="post">

<html:hidden property="formName" value="SubjectWiseTimeTableViewForm" />
<html:hidden property="method" styleId="method" value="fetchData"/>
<html:hidden property="pageType" value="1"/>


	
<table width="98%" border="0">
  <tr>
    <td><span class="heading"><bean:message key="knowledgepro.viewTimeTable"/><span class="Bredcrumbs">&gt;&gt;<bean:message key="knowledgepro.viewTimeTable.subjectWise"/> &gt;&gt;</span></span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="10"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="body" ><div align="left"><strong class="boxheader"><bean:message key="knowledgepro.viewTimeTable.subjectWise"/></strong></div></td>
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
	                       		<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
									<tr>
										<td><img src="images/01.gif" width="5" height="5" /></td>
			
										<td width="914" background="images/02.gif"></td>
										<td><img src="images/03.gif" width="5" height="5" /></td>
									</tr>
									<tr>
										<td width="5" background="images/left.gif"></td>
										<td valign="top">
										<table width="100%" cellspacing="1" cellpadding="2">
											<tr>
												<td   height="25" class="row-odd">
												<div align="right"><span class="Mandatory">*</span>Course
												:</div>
												</td>
												<td  class="row-even"><html:select
													property="course" styleClass="combo" name="SubjectWiseTimeTableViewForm"
													styleId="course"
													style="width:200px">
													<html:option value="">
														<bean:message key="knowledgepro.admin.select" />
													</html:option>	
														<logic:notEmpty name="SubjectWiseTimeTableViewForm" property="courseList">
															<html:optionsCollection property="courseList"
																name="SubjectWiseTimeTableViewForm" label="name" value="id" />
														</logic:notEmpty>
												</html:select></td>
												
												<td   class="row-odd">
												<div align="right"><span class="Mandatory">*</span>Academic
												Year :</div>
												</td>
			
												<td    class="row-even"><html:select 
													property="academicYr"
													styleId="academicYr">
													<cms:renderYear></cms:renderYear>
												</html:select></td>
											</tr>
											<tr>
			
												<td class="row-odd">
													<div align="right"><span class="Mandatory">*</span><bean:message
														key="knowledgepro.fee.semister" />:</div>
												</td>
												<td class="row-even"><html:select property="termNo"
													styleId="termNo" name="SubjectWiseTimeTableViewForm" styleClass="combo" onchange="getSubjectsOnCourseTermYear(this.value);">
													<html:option value="">
														<bean:message key="knowledgepro.admin.select" />
													</html:option>
													<cms:renderSchemeOrCourse></cms:renderSchemeOrCourse>
													</html:select>
												</td>
												<td   class="row-odd">
												<div align="right"><span class="Mandatory">*</span>Subject :</div>
												</td>
			
												<td   class="row-even"><html:select 
													property="subject" styleId="subject" styleClass="combo">
													<html:option value="">
														<bean:message key="knowledgepro.admin.select" />
														<c:if test="${subjectMap != null}">
														<html:optionsCollection name="subjectMap" label="value" value="key" />
													</c:if>
													</html:option>
												</html:select></td>
											</tr>
			
			
										</table>
			
										</td>
										<td width="5" height="30" background="images/right.gif"></td>
			
									</tr>
									<tr>
										<td height="5"><img src="images/04.gif" width="5" height="5" /></td>
										<td background="images/05.gif"></td>
										<td><img src="images/06.gif" /></td>
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
                     <tr>
                       <td width="45%"><div align="right">
                       <html:submit styleClass="formbutton" value="Search"></html:submit>
                       </div></td>
                       <td width="2%"></td>
                       <td width="53%" height="45" align="left">
                   	 		<html:button property="" styleClass="formbutton" value="Reset" onclick="resetFieldAndErrMsgs()"></html:button>
                       </td>
                     </tr>
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
</script>