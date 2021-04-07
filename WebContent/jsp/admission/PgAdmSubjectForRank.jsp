<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested" %>

<script type="text/javascript">

function getPrograms(programTypeId) {
	
	getProgramsByType("programMap",programTypeId,"selectedProgram",updatePrograms);
}

function updatePrograms(req) {
	
	updateOptionsFromMap(req,"selectedProgram","- Select -");
}

function getCourses(programId) {
	var program =  document.getElementById("selectedProgram");
	  var selectedArray = new Array();	  
	  var i;
	  var count = 0;
	  for (i=0; i<program.options.length; i++) {
	    if (program.options[i].selected) {
	      selectedArray[count] = program.options[i].value;
	      count++;
	    }
	  }
	  getCoursesByMultiplePrograms("coursesMap",selectedArray,"selectedCourse",updateCourses);
}

function updateCourses(req) {
	updateOptionsFromMapForMultiSelect(req,"selectedCourse");
}

function editAdmSubjectForRank(programtypeid,programid,courseid,eligcourseid,id) {
	//alert(programtypeid);
	//alert(programid);
	//alert(courseid);
	
	document.location.href = "PgAdmnSubjectForRankEntry.do?method=editAdmSubjectForRank&programTypeId="
		+ programtypeid+"&selectedProgram="+programid+"&selectedCourse="+courseid+"&selectedCourseId="+eligcourseid+"&id="+id;
	
}
	
function deleteAdmnSubjectForRank(Id, subName) {
		deleteConfirm = confirm("Are you sure you want to delete this entry?")
		if (deleteConfirm) {
			document.location.href = "PgAdmnSubjectForRankEntry.do?method=deleteAdmSubjectForRank&id="+Id+"&subjectname="+subName;
		}
}
	
function reActivate() {
		document.location.href = "PgAdmnSubjectForRankEntry.do?method=reActivateAdmSubjectForRank";
}

function resetValues() {
		document.location.href = "PgAdmnSubjectForRankEntry.do?method=initAdmSubjectForRank";
		resetErrMsgs();
}

</script>


<html:form action="/PgAdmnSubjectForRankEntry">
	
	<html:hidden property="origsubjectname" styleId="origsubjectname" value="" name="addAdmSubjectForRank"/>
	<html:hidden property="origstream" styleId="origstream" value="" name="addAdmSubjectForRank"/>
	<html:hidden property="origgroupname" styleId="origgroupname" value="" name="addAdmSubjectForRank"/>
	<html:hidden property="formName" value="pgadmSubjectForRankForm" />
	<html:hidden property="pageType" value="1" />
	<html:hidden property="id" styleId="id" />
	
	
	<c:choose>
		<c:when
			test="${Update == null || Update == 'valid' || Update == 'add'}">
			<html:hidden property="method" styleId="method" value="addAdmSubjectForRank" />
			
		</c:when>
		<c:otherwise>
			<html:hidden property="method" styleId="method" value="updateAdmSubjectForRank" />
			
		</c:otherwise>
	</c:choose>
	
<table width="100%" border="0">
		<tr>

			<td><span class="Bredcrumbs"><bean:message key="knowledgepro.admission" /><span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.admission.subject.entry" /> &gt;&gt;</span></span></td>
		</tr> 
		<tr>
			<td valign="top">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> <bean:message key="knowledgepro.admission.subject.entry" /></strong></td>

					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="20" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<div align="right"><FONT color="red"> <span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields"/></span></FONT></div>
					<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
					<FONT color="green"> <html:messages id="msg"
						property="messages" message="true">
						<c:out value="${msg}" escapeXml="false"></c:out>
						<br>
					</html:messages> </FONT></div>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td height="41" valign="top" background="images/Tright_03_03.gif"></td>

					<td valign="top" class="news">
					<table width="100%" border="0" align="center" cellpadding="0"
						cellspacing="0">
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
						<td class="row-odd" valign="top" >
									<div align="right"><span class="Mandatory">*</span>
									<bean:message key="knowledgepro.admission.programtype"/>:</div></td>
                   					 <td height="25" class="row-even" >
                   					<html:select property="programTypeId"  styleId="programtype" styleClass="comboLarge" onchange="getPrograms(this.value)">
                 					<html:option value=""><bean:message key="knowledgepro.admin.select"/> </html:option>
				    				<html:optionsCollection name="pgadmSubjectForRankForm" property="programTypeList" label="programTypeName" value="programTypeId"/>
	     							</html:select> 
               						 </td>
									
									
									<td class="row-odd" valign="top">
									<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message
										key="knowledgepro.admin.program" /></div>
									</td>
									<td  class="row-even" valign="top"> 
									<html:select property="selectedProgram" styleId="selectedProgram" styleClass="comboLarge"   onchange="getCourses(this.value)" >
																		
										<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
        			    				<c:if test="${pgadmSubjectForRankForm.programTypeId != null && pgadmSubjectForRankForm.programTypeId != ''}">
            		    	 			 <logic:notEmpty name="pgadmSubjectForRankForm" property="programMap">
            		    	 						<html:optionsCollection name="pgadmSubjectForRankForm" property="programMap" label="value" value="key" styleClass="comboBig"/>
            		    	 			</logic:notEmpty>		
            		   					</c:if>
										
									</html:select>
									</td>
									</tr>
									<tr>
									<td class="row-odd" valign="top">
									<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message key="knowledgepro.admin.course.with.col"/></div>
									</td>
									<td  class="row-even" valign="top">                    
									<nested:select property="selectedCourse" styleClass="comboLarge"   styleId="selectedCourse" >
									<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
        			    				
										<logic:notEmpty name="pgadmSubjectForRankForm" property="courseMap">
											<nested:optionsCollection name="pgadmSubjectForRankForm" property="courseMap" label="value" value="key" styleClass="comboBig"/>
										</logic:notEmpty>
									</nested:select>
								</td>
								
									

									<td width="22%" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>Eligible Ug Courses</div>
									</td>
									<td width="16%" class="row-even">
										
									<html:select property="selectedCourseId" styleClass="comboLarge"   styleId="selectedCourseid" >
										<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
        			    			
											<html:optionsCollection name="pgadmSubjectForRankForm" property="ugcourseList" label="name" value="id" styleClass="comboBig"/>
										
									</html:select>
									
									
										
										</td>
										
								
								</tr>
									

							</table>
							</td>
							<td width="5" height="29" background="images/right.gif"></td>
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

					<td height="35" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="45%" height="35">
							<div align="right">
							
							<c:choose>
										<c:when test="${Update != null && Update == 'Update'}">
											<html:submit property="" styleClass="formbutton"
												value="Update" styleId="submitbutton">
											</html:submit>
										</c:when>
										<c:otherwise>
											<html:submit property="" styleClass="formbutton"
												value="Submit" styleId="submitbutton">
											</html:submit>
										</c:otherwise>
							</c:choose>
							
									
						   </div>
							</td>
							<td width="2%"></td>
							<td width="53%"><html:button property=""
								styleClass="formbutton" value="Reset" onclick="resetValues()"></html:button></td>
						</tr>

					</table>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" align="center" cellpadding="0"
						cellspacing="0">
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
									<td height="25" class="row-odd" align="center">
									<div align="center"><bean:message key="knowledgepro.slno" /></div>
									</td>
									<td height="25" class="row-odd" align="center">PROGRAM</td>

									<td class="row-odd" align="center">PG COURSE</td>
									<td class="row-odd" align="center">ELIGIBLE COURSES</td>
									<td class="row-odd" align="center">
									<div align="center"><bean:message key="knowledgepro.edit" /></div>
									</td>
									<td class="row-odd" align="center">
									<div align="center"><bean:message
										key="knowledgepro.delete" /></div>
									</td>
								</tr>
								<logic:iterate id="list" name="pgadmSubjectForRankForm" property="subjecttolist"
									indexId="count">
									
									<c:choose>
										<c:when test="${count%2 == 0}">
											<tr class="row-even">
										</c:when>
										<c:otherwise>
											<tr class="row-white">
										</c:otherwise>
									</c:choose>
		
									<td width="8%" height="25" align="center">
									<div align="center"><c:out value="${count + 1}" /></div>
									</td>
									<td width="34%" height="25" align="center"><bean:write
										name="list" property="programTO.name" /></td>

									<td width="37%"  align="center"><bean:write
										name="list" property="courseTO.name" /></td>
										
									<td width="37%"  align="center"><bean:write
										name="list" property="uGCoursesTO.name" /></td>
											
									<td width="11%" height="25"  align="center">
									<div align="center"><img src="images/edit_icon.gif"
										width="16" height="18" style="cursor:pointer"
										onclick="editAdmSubjectForRank('<bean:write name="list" property="programTypeTO.programTypeId"/>','<bean:write name="list" property="programTO.id"/>','<bean:write name="list" property="courseTO.id"/>','<bean:write name="list" property="uGCoursesTO.id"/>','<bean:write name="list" property="id"/>')">
									</div>
									</td>
									<td width="10%" height="25" align="center">
									<div align="center"><img src="images/delete_icon.gif"
										width="16" height="16" style="cursor:pointer"
										onclick="deleteAdmnSubjectForRank('<bean:write name="list" property="id"/>','<bean:write name="list" property="uGCoursesTO.name"/>')">
									</div>
									</td>
								
								</logic:iterate>
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
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>

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