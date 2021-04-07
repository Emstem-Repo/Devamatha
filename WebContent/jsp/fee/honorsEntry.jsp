<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<script language="JavaScript" >
function resetMessages() {
	document.getElementById("method").value = "initHonorsEntry";
	document.honorsEntryForm.submit();
}

function getCourses(programId) {
	getCoursesByProgram("coursesMap",programId,"courseId",updateCourses);
	resetOption("semister");
}

function updateCourses(req) {
	updateOptionsFromMap(req,"courseId","- Select -");
}

function resetCoursesChilds() {
	resetOption("semister");
}

function getSemistersByCourse(courseId) {
	var academicYear = document.getElementById("academicYear").value;
	getSemistersOnYearAndCourse("semistersMap",courseId,"semister",academicYear,updateSemisters);
}

function getSemisters(year) {
	var courseId = document.getElementById("courseId").value;
	getSemistersOnYearAndCourse("semistersMap",courseId,"semister",year,updateSemisters);
}

function updateSemisters(req){
	updateOptionsFromMap(req,"semister","- Select -");
}
function reActivate(id) {
	document.location.href = "HonorsEntry.do?method=reActivateHonorsEntry&id="+id;
}
</script>
<html:form action="/HonorsEntry">	
	<html:hidden property="method" styleId="method" value="updateHonorsEntry" />
	<html:hidden property="formName" value="honorsEntryForm" />
	<html:hidden property="pageType" value="1" />
	<table width="100%" border="0">
 		<tr>
			<td><span class="Bredcrumbs"><bean:message key="knowledgepro.fee" />
			<span class="Bredcrumbs">&gt;&gt; <bean:message key="knowledgepro.fee.honors.entry" /> &gt;&gt;</span></span></td>
		</tr>
  		<tr>
    		<td>
    			<table width="100%" border="0" cellpadding="0" cellspacing="0">
      				<tr>
        				<td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        				<td background="images/Tcenter.gif" class="body" ><strong class="boxheader"><bean:message key="knowledgepro.fee.honors.entry"/></strong></td>
        				<td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      				</tr>
				    <tr>
						<td height="20" valign="top" background="images/Tright_03_03.gif"></td>
						<td valign="top" class="news">
							<div align="right"><FONT color="red"> <span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields"/></span></FONT></div>
							<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
							<FONT color="green"> 
								<html:messages id="msg" property="messages" message="true">
									<c:out value="${msg}" escapeXml="false"></c:out>
									<br>
								</html:messages> </FONT></div>
						</td>
						<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
					</tr>
      				<tr>
        				<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        				<td valign="top" class="news">
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
            									<td width="34%" class="row-even">
            										<table width="100%" cellspacing="1" cellpadding="2">
														<tr>
                											<td width="26%"  class="row-odd" >
                												<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message key="knowledgepro.admin.program"/>:</div>
                											</td>
                											<td class="row-even">
                												<html:select name="honorsEntryForm" property="programId" styleId="programId" styleClass="combo" onchange="getCourses(this.value)">
																	<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
																	<html:optionsCollection property="programList" label="name" value="id"/>
																</html:select> 
                								            </td>
                											<td width="26%"  class="row-odd" >
                												<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message key="knowledgepro.admin.course"/>:</div>
                											</td>
                											<td class="row-even">
                												<html:select name="honorsEntryForm" property="courseId" styleId="courseId" styleClass="combo" onchange="getSemistersByCourse(this.value),resetCoursesChilds()">
																	<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
	                            		    							<c:if test="${courseMap != null}">
	                            		    								<html:optionsCollection name="courseMap" label="value" value="key"/>
	                            		    							</c:if>	 
																</html:select> 
                								            </td>
              											</tr>
              											<tr>
                											<td class="row-odd"><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.fee.admittedYear"/>:</div></td>
		                           							<td class="row-even" align="left">
		                           								<input type="hidden" id="tempyear" name="tempyear" value="<bean:write name="honorsEntryForm" property="academicYear"/>"/>
		                           								<html:select property="academicYear" styleId="academicYear" styleClass="combo" onchange="getSemisters(this.value)">
                       	   				 							<html:option value="">- Select -</html:option>
                       	   				 							<cms:renderYear></cms:renderYear>
                       			   								</html:select>
		                           							</td>
                											<td width="26%"  class="row-odd" >
                												<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message key="admissionForm.detailmark.semester.label"/>:</div>
                											</td>
                											<td class="row-even">
                												<html:select name="honorsEntryForm" property="semister" styleId="semister" styleClass="combo">
																	<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
                            		    								<c:if test="${semistersMap != null}">
                            		    									<html:optionsCollection name="semistersMap" label="value" value="key"/>
                            		    								</c:if>	 
																</html:select> 
                								            </td>
              											</tr>
              											<tr>
                											<td width="26%"  class="row-odd" ><div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message key="knowledgepro.exam.reJoin.registerNo"/>:</div></td>
                											<td class="row-even" colspan="3">
                												 <html:text name="honorsEntryForm" property="regNo" styleId="regNo" size="16" maxlength="16"/>
                								            </td>
                								            <td>&nbsp;</td>
                  										</tr>
                  										<tr>
															<td height="25" colspan="3" class="row-even">
																<div align="center">
																<html:radio name="honorsEntryForm" property="add" styleId="addId" value="Add"><font size="-3">Add</font></html:radio>
																&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
																<html:radio name="honorsEntryForm" property="add" styleId="deleteId" value="Delete"><font size="-3">Delete</font></html:radio>
																</div>
															</td>
														</tr>
            										</table>
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
        				<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      				</tr>
      				<tr>
        				<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        				<td class="heading">
        					<table width="100%"  border="0" cellspacing="0" cellpadding="0">
          						<tr>
									<td width="46%" height="35">
										<div align="right">
											<html:submit styleClass="formbutton">
												<bean:message key="knowledgepro.submit" />
											</html:submit>
										</div>
									</td>
									<td width="2%"></td>
									<td width="52%" align="left">
										<html:button property="" styleClass="formbutton" value="Reset" onclick="resetMessages();" styleId="reset">
										</html:button> 
									</td>
								</tr>
        					</table>
        				</td>
        				<td valign="top" background="images/Tright_3_3.gif" ></td>
      				</tr>
      				<tr>
        				<td height="10" valign="top" background="images/Tright_03_03.gif"></td>
        				<td valign="top" class="news">&nbsp;</td>
        				<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      				</tr>
      				<tr>
        				<td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
        				<td width="0" background="images/TcenterD.gif"></td>
        				<td><img src="images/Tright_02.gif" width="9" height="29"></td>
      				</tr>
    			</table>
    		</td>
  		</tr>
	</table>
</html:form>

<script type="text/javascript">
	var year = document.getElementById("tempyear").value;
	if (year.length != 0) {
		document.getElementById("academicYear").value = year;
	}
</script>