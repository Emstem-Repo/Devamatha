<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="http://java.fckeditor.net" prefix="FCK" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<script type="text/javascript">
function getPrograms(ProgramTypeId) {
	getProgramsByType("programMap", ProgramTypeId, "program", updatePrograms);
}

function updatePrograms(req) {
	updateOptionsFromMap(req, "program", "- Select -");
}

function getCourses(programId) {
	getCoursesByProgram("courseMap", programId, "course", updateCourses);
}
function updateCourses(req) {
	updateOptionsFromMap(req, "course", "- Select -");
}
function editTemplate(id) {
	document.getElementById("templateId").value = id;
	document.getElementById("method").value = "editInterviewTemplate";
	document.interviewTemplateForm.submit();
}

function updateTemplate() {
	document.getElementById("method").value = "updateInterviewTemplate";
	document.interviewTemplateForm.submit();
}

function addTemplate() {
	document.getElementById("method").value = "createInterviewTemplate";
	document.interviewTemplateForm.submit();
}
function viewTemplate(id) {
	var url = "interviewTemplate.do?method=viewTemplateDescription&templateId=" + id;
	myRef = window.open(url, "ViewTemplateDescription", "left=20,top=20,width=600,height=500,toolbar=1,resizable=0,scrollbars=1");
}
function deleteTemplate(id) {
	deleteConfirm = confirm("Are you sure you want to delete this entry?")
	if (deleteConfirm == true) {
		document.location.href = "interviewTemplate.do?method=deleteInterviewTemplate&templateId=" + id;
	}
}
function openHtml() {
	var url = "interviewTemplate.do?method=helpMenu";
	win2 = window.open(url, "Help", "width=800,height=800,scrollbars=yes"); 
}
function getinterviewType() {
	var year = document.getElementById("appliedYear").options[document.getElementById("appliedYear").selectedIndex].value;
	var courseId = document.getElementById("course").options[document.getElementById("course").selectedIndex].value;
	
		if(courseId.length >0) {
			getInterviewTypeByCourse("interviewMap",courseId,year,"interviewType",updateInterviewType);		
			document.getElementById("courseName").value =	document.getElementById("course").options[document.getElementById("course").selectedIndex].text	
		}
}
function updateInterviewType(req) {
	updateOptionsFromMap(req,"interviewType","- Select -");
}

function getinterviewSubrounds(interviewTypeId) {
	getInterviewSubroundsByInterviewtype("interviewSubroundsMap",interviewTypeId,"interviewSubrounds",updateInterviewSubrounds);	
	
}

function updateInterviewSubrounds(req) {
	updateOptionsFromMap(req,"interviewSubrounds","- Select -");
	var responseObj = req.responseXML.documentElement;
	var items = responseObj.getElementsByTagName("option");
	if(items.length>=1){
		document.getElementById("subroundCount").value = items.length;
		document.getElementById("subround").innerHTML = "<span class='Mandatory'>*</span>&nbsp;Subround:";
	} else{
		document.getElementById("subroundCount").value = 0;
		document.getElementById("subround").innerHTML = "Subround:";
	}			
}

</script>
<html:form action="/interviewTemplate" method="post">
<html:hidden property="method" styleId="method"	value="createGroupTemplate" />
<html:hidden property="templateId" styleId="templateId" />
<html:hidden property="formName" value="interviewTemplateForm" />
<html:hidden property="pageType" value="1" />
<table width="98%" border="0">
  <tr>
    <td><span class="heading"><bean:message key="knowledgepro.admin"/><span class="Bredcrumbs">&gt;&gt;Interview Template&gt;&gt;</span></span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="10"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="body" ><div align="left"><strong class="boxheader">Interview Template</strong></div></td>
        <td width="13" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="122" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><div align="center">
              <table width="100%" height="50"  border="0" cellpadding="0" cellspacing="0">
                 <tr>
               	    <td height="20" colspan="6" align="left">
               	    <div align="right" style="color:red"> <span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields"/></span></div>
               	    <div id="errorMessage"> &nbsp;
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
                   <td height="49" colspan="6" class="body" ><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
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
								<td width="17%" height="25" class="row-odd">
								<div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.admin.programtype" />:</div>
								</td>
								<td width="23%" height="25" class="row-even">
									<html:select property="programTypeId" styleClass="combo" styleId="programType" onchange="getPrograms(this.value)" >
										<html:option value=" "><bean:message key="knowledgepro.admin.select"/></html:option>
										<html:optionsCollection property="programTypeList" label="programTypeName" value="programTypeId"/>
									</html:select>
								</td>
								<td width="13%" class="row-odd">
								<div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.admin.program" />:</div>
								</td>
								<td width="17%" class="row-even">
								<html:select property="programId" styleClass="combo" styleId="program" onchange="getCourses(this.value)" >
									<html:option value=" "><bean:message key="knowledgepro.select" /></html:option>
									<c:choose>
										<c:when test="${operation == 'edit'}">
											<c:if test="${programMap != null}">
												<html:optionsCollection name="programMap" label="value" value="key" />
											</c:if>
										</c:when>
										<c:otherwise>
		                 			    	<c:if test="${interviewTemplateForm.programTypeId != null && interviewTemplateForm.programTypeId != ''}">
		                     					<c:set var="programMap" value="${baseActionForm.collectionMap['programMap']}"/>
		                     		    	 	<c:if test="${programMap != null}">
		                     		    	 		<html:optionsCollection name="programMap" label="value" value="key"/>
		                     		    	 	</c:if>	 
		                     		   		</c:if>
		                     		   	</c:otherwise>
				                   	</c:choose>	
								</html:select>
								</td>
								<td width="13%" class="row-odd">
								<div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.admin.course" />:</div>
								</td>
								<td width="17%" class="row-even">
								<html:select property="courseId" styleClass="combo" styleId="course" onchange="getinterviewType()">
									<html:option value=" "><bean:message key="knowledgepro.select" /></html:option>
										<c:choose>
											<c:when test="${operation == 'edit'}">
												<c:if test="${courseMap != null}">
													<html:optionsCollection name="courseMap" label="value" value="key" />
												</c:if>	
											</c:when>
											<c:otherwise>
												<c:if test="${interviewTemplateForm.programId != null && interviewTemplateForm.programId != ''}">
													<c:set var="coursesMap" value="${baseActionForm.collectionMap['courseMap']}" />
													<c:if test="${coursesMap != null}">
														<html:optionsCollection name="coursesMap" label="value" value="key" />
													</c:if>
												</c:if>
											</c:otherwise>
										</c:choose>
								</html:select>
								</td>
							</tr>
                           <tr>
                           	 <td height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message
										key="knowledgepro.admin.year" />:</div>
									</td>
									<td height="25" class="row-even"> 
									<input type="hidden" id="tempyear" name="tempyear"
										value="<bean:write name="interviewTemplateForm" property="appliedYear"/>" />
									<html:select property="appliedYear" styleId="appliedYear" onchange="getinterviewType()" styleClass="combo">
										<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
										<cms:renderYear></cms:renderYear>
									</html:select>
									</td>
									<td class="row-odd">
									<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message
										key="knowledgepro.admission.interviewType" />:</div>
									</td>
									<td class="row-even">
									<html:select property="interviewId" styleId="interviewType" onchange="getinterviewSubrounds(this.value)" styleClass="combo">
										<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
											<c:if test="${interviewMap != null}">
												<html:optionsCollection name="interviewMap" label="value"
													value="key" />
											</c:if>
									</html:select>
									</td>
									<td class="row-odd">
									<div align="right" id="subround">&nbsp;<bean:message
										key="knowledgepro.admin.interviewsubround.subround" />:</div>
									</td>
									<td class="row-even">
									<html:select property="interviewSubroundId" styleId="interviewSubrounds" styleClass="combo">
										<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
											<c:if test="${interviewSubroundsMap != null}">
												<html:optionsCollection name="interviewSubroundsMap" label="value" value="key" />
											</c:if>
									</html:select>
									</td>
                           </tr>
                           <tr>
                           <td class="row-odd" ><div align="right">
                             	<span class="Mandatory">*</span><bean:message key="knowledgepro.emailtemplate.templatename"/>:</div>
                             </td>
                             <td class="row-even">
                            	<div align="left">
                            	<!-- <input type="hidden" id="templatenameId" name="template" value='<bean:write name="interviewTemplateForm" property="templateName"/>' /> -->	
                            	<html:select property="templateName" styleClass="comboMediumLarge" styleId="templateNameId">
                            		<html:option value=" "><bean:message key="knowledgepro.admin.select"/></html:option>
                            		<html:option value="Personal Interview">Personal Interview</html:option>
								</html:select>
								<img src="images/questionMark.jpg" style="cursor:pointer" width="20" height="20"
								 onclick="openHtml()" title="Help">
								 </div>
							</td>
							<td class="row-odd"></td>
							<td class="row-even"></td>
							<td class="row-odd"></td>
							<td class="row-even"></td>
                           </tr>
                           <tr>
                             <td width="50%" height="25" class="row-odd" colspan="6">
								<FCK:editor instanceName="EditorDefault"  toolbarSet="Default">
									<jsp:attribute name="value">
										<c:out value="${interviewTemplateForm.templateDescription}" escapeXml="false"></c:out>
									</jsp:attribute>
								</FCK:editor>
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
                 </tr>
                 <tr>
                   <td height="20" colspan="6" valign="top" class="body" >
                   <table width="100%" cellspacing="1" cellpadding="2"><tr ></tr>
                     <tr>
                       <td height="20" colspan="2" >   
		                <table width="100%"  border="0" cellspacing="0" cellpadding="0">
		                  <tr>
		                    <td height="20"><div align="center">
		                    
		                     <c:choose>
		                     	<c:when test="${operation == 'edit'}">
		                     	 	<html:button property="" styleClass="formbutton" value="Update" onclick="updateTemplate()"></html:button>
		                      	</c:when>
		                      	<c:otherwise>
		                      		<html:button property="" styleClass="formbutton" value="Save" onclick="addTemplate()"></html:button>
		                      	</c:otherwise>
		                     </c:choose>	
		                    </div></td>
		                    </tr>
		                </table>
                        </td>
                     </tr>
                   </table></td>
                </tr>
                 <tr>
                   <td height="10" colspan="6" class="body" ></td>
                 </tr>
              </table>
            </div></td>
        <td width="13" valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <logic:notEmpty name="interviewTemplateForm" property="templateList">
      <tr>
        <td height="122" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><div align="center">
              <table width="100%" height="50"  border="0" cellpadding="0" cellspacing="0">
                 <tr>
                   <td height="49" colspan="6" class="body" ><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
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
                             <td width="10%" height="25" class="row-odd"><div align="center">
                             	<bean:message key="knowledgepro.slno"/></div>
                             </td>
                             <td width="15%" height="25" class="row-odd"><div align="center">
                             	<bean:message key="knowledgepro.admin.programtype" /></div>
                             </td>
                             <td width="15%" height="25" class="row-odd"><div align="center">
                             	<bean:message key="knowledgepro.admin.program" /></div>
                             </td>
                             <td width="15%" height="25" class="row-odd"><div align="center">
                             	<bean:message key="knowledgepro.admin.course" /></div>
                             </td>
                             <td width="15%" height="25" class="row-odd" align="center">
	                             <span class="star">
	                             	<bean:message key="knowledgepro.emailtemplate.templatename"/>
	                             </span>
	                         </td>
                             <td width="10%" height="25" class="row-odd" >
                             	<div align="center">
                             		<bean:message key="knowledgepro.view" />
                             	</div>
                             </td>
                             <td width="10%" height="25" class="row-odd" >
                             	<div align="center">
                             		<bean:message key="knowledgepro.edit" />
                             	</div>
                             </td>
                             <td width="10%" height="25" class="row-odd" >
                             	<div align="center">
                             		<bean:message key="knowledgepro.delete" />
                             	</div>
                             </td>
                           </tr>
                           <logic:iterate id="template" name="interviewTemplateForm" property="templateList" indexId="count">
							<c:choose>
								<c:when test="${count%2 == 0}">
									<tr class="row-even">
								</c:when>
								<c:otherwise>
									<tr class="row-white">
								</c:otherwise>
							</c:choose>
                           	<td height="25"><div align="center">
                             	<c:out value="${count + 1}"/> </div>
                             </td>
                             <td height="25"><div align="center">
	                             <bean:write name="template" property="programTypeName" />
	                           </div>
	                         </td>
	                         <td height="25"><div align="center">
	                             <bean:write name="template" property="programName"/>
	                           </div>
	                         </td>
	                         <td height="25"><div align="center">
	                             <bean:write name="template" property="courseName"/>
	                           </div>
	                         </td>
                             <td height="25"><div align="center">
	                             <bean:write name="template" property="templateName"/>
	                           </div>
	                         </td>
	                         <td height="25">
								<div align="center"><img src="images/View_icon.gif" style="cursor:pointer"
									width="16" height="18" onclick="viewTemplate('<bean:write name="template" property="id"/>')"></div>
							 </td>
							 <td height="25">
								<div align="center"><img src="images/edit_icon.gif" style="cursor:pointer"
									width="16" height="18" onclick="editTemplate('<bean:write name="template" property="id"/>')"></div>
							 </td>
							 <td height="25">
								<div align="center"><img src="images/delete_icon.gif" style="cursor:pointer"
								 width="16" height="16" onclick="deleteTemplate('<bean:write name="template" property="id"/>')"></div>
							 </td>
                            </tr>
                          </logic:iterate>
                      </table>
                      </td>
                      <td width="5" height="30"  background="images/right.gif"></td>
                    </tr>
                    <tr>
                      <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
                      <td background="images/05.gif"></td>
                      <td><img src="images/06.gif" /></td>
                    </tr>
                  </table></td>
                </tr>
                 <tr>
                  <td height="10" colspan="6" class="body" ></td>
                </tr>
             </table>
           </div></td>
       <td width="13" valign="top" background="images/Tright_3_3.gif" class="news"></td>
     </tr>
     </logic:notEmpty>
     <tr>
       <td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
       <td width="100%" background="images/TcenterD.gif"></td>
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
	document.getElementById("appliedYear").value = year;
}
</script>
