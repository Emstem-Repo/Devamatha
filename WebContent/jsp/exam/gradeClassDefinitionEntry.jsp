<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@page import="java.util.Map,java.util.HashMap"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>

<script type="text/javascript">
function isValidNumber(field) {
	if (isNaN(field.value)) {
		field.value = "";
	}
}
function AddMore()
{
	document.getElementById("method").value ="addMoreGradeClassMarks";
	document.gradeClassDefinitionEntryForm.submit();
	
}
function removeList()
{
	document.getElementById("method").value ="removeMoreGradeMarks";
	document.gradeClassDefinitionEntryForm.submit();
}
function resetAll()
{
	document.getElementById("method").value ="initGradeClassDefinitionEntry";
	document.gradeClassDefinitionEntryForm.submit();
}
function  AddGradeClassDefinition()
{
	document.getElementById("method").value ="addGradeClassDefinition";
	document.gradeClassDefinitionEntryForm.submit();
}

function getSchemMap()
{
	var year = document.getElementById("academicYear").value;
	
}
</script>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html:form action="/GradeClassDefinitionEntry" method="post">
	<html:hidden property="formName" value="gradeClassDefinitionEntryForm" />
	<html:hidden property="pageType" value="1" />
	<html:hidden property="method" value="" styleId="method" />

	<table width="100%" border="0">
		<tr>
			<td><span class="heading"><a href="main.html"
				class="Bredcrumbs"><bean:message key="knowledgepro.exam.exam" /></a>
			<span class="Bredcrumbs">&gt;&gt; <bean:message
				key="knowledgepro.exam.subjectDefinitionCourseWise" />&gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> <bean:message
						key="knowledgepro.exam.subjectDefinitionCourseWise" />  Grade / Class Definition </strong></td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td height="20" class="news">

					<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
					<FONT color="green"> <html:messages id="msg"
						property="messages" message="true">
						<c:out value="${msg}" escapeXml="false"></c:out>
						<br>
					</html:messages> </FONT></div>
					<div align="right" class="mandatoryfield">*Mandatory fields</div>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
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
								<td class="row-odd" width="25%"><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.fee.academicyear"/>:</div></td>
				        		   <td class="row-even" align="left" width="25%">
				                           <input type="hidden" id="tempyear" name="tempyear" value="<bean:write name="gradeClassDefinitionEntryForm" property="accYear"/>"/>
				                           <html:select property="accYear" styleId="academicYear" name="gradeClassDefinitionEntryForm" styleClass="combo" onchange="getSchemMap()">
		                       	   				 <html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
		                       	   				<cms:renderAcademicYear></cms:renderAcademicYear>
		                       			   </html:select>
				        			</td>
									<td width="10%" height="15" class="row-odd">
									<div align="right"><span class="Mandatory">*</span> <bean:message
										key="knowledgepro.exam.course" /> :</div>
									</td>


									<td width="17%" height="5" class="row-even"><nested:select
										property="selectedCourses" styleClass="body"
										multiple="multiple" size="8" styleId="id" style="width:500px">
										<nested:optionsCollection name="gradeClassDefinitionEntryForm"
											property="listExamCourseUtilTO" label="display" value="id" />
									</nested:select></td>
								</tr>
								
								<tr>
									<td width="10%" height="15" class="row-odd">
									<div align="right"><span class="Mandatory">*</span> <bean:message
										key="knowledgepro.fee.semister" /> :</div>
									</td>


									<td width="17%" height="5" class="row-even" colspan="3">
									<nested:select	property="schemeNumber" styleClass="combo"
										 styleId="schemNumber">	
										 <html:option value=""> - Select-</html:option>
										 <nested:optionsCollection property="schemeMap" label="value" value="key"/>
									</nested:select></td>
								
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
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">&nbsp;</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					
				 <nested:notEmpty property="gradeDefinitionList" name="gradeClassDefinitionEntryForm">
				  <!-- start add more -->
				  <nested:iterate property="gradeDefinitionList" name="gradeClassDefinitionEntryForm" indexId="count" id="gradeDefinitionList" type="com.kp.cms.to.exam.GradeClassDefinitionTO">
				  <br>
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
                       
									
								
									<td width="16%" height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>From %
									:</div>
									</td>
									<td width="16%" height="25" class="row-even">
									<nested:text
										property="fromPercentage" maxlength="10"
										styleId="fromPercentage" onblur="isValidNumber(this)" onkeypress="return isDecimalNumberKey(this.value,event)"/></td>
									<td width="16%" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>To %:</div>
									</td>
									<td width="16%" class="row-even">
									<nested:text
										property="toPercentage" maxlength="10"
										styleId="toPercentage" onblur="isValidNumber(this)" onkeypress="return isDecimalNumberKey(this.value,event)"/></td>
										
										<td width="16%" height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>Grade :</div>
									</td>
									<td height="25" width="16%" class="row-even">
									<nested:text
										property="grade" maxlength="10" styleId="grade"
										/></td>
									
								</tr>
                                <tr>
                       
									
								
									<td width="16%" height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>Grade Point
									:</div>
									</td>
									<td width="16%" height="25" class="row-even">
									<nested:text
										property="gradePoint" maxlength="10"
										styleId="gradePoint" /></td>
									<td width="16%" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>Result Class :</div>
									</td>
									<td width="16%" class="row-even">
									<nested:text
										property="resultClass" 
										styleId="resultClass" /></td>
										
										<td width="16%" height="25" class="row-odd">
									<div align="right">Interpretation :</div>
									</td>
									<td height="25" width="16%" class="row-even">
									<nested:text
										property="interpretation" maxlength="50" styleId="interpretation"
										/></td>
									
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
					
					</nested:iterate>
				 <!--  end add more -->
				  </nested:notEmpty> 
				  
					
					<div align="center"><html:button property=""  value="Add More" onclick="AddMore()" styleClass="formbutton"></html:button>
					<html:button property=""  value="Remove " onclick="removeList()" styleClass="formbutton"></html:button>
					</div>
					<br>
					<hr>
					</br>
					
					
					
					
					
					
					
					
					
					
					
					
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>

							<td width="49%" height="35" align="right">
							
							
							 
							 <html:button property="" value="Add Grade / Class Definition " onclick="AddGradeClassDefinition()" styleClass="formbutton"></html:button>
							
							
							</td>
							<td width="2%" align="center">&nbsp;</td>
							<td width="49%" align="left">
								<c:choose>
									<c:when test="${examAttOperation == 'edit'}">
										<html:cancel styleClass="formbutton" onclick="update()">
											Reset
										</html:cancel>
									</c:when>
									<c:otherwise>
										<input type="button" class="formbutton" value="Reset"
										onclick="resetAll()" />
									</c:otherwise>
								</c:choose>								
									
								</td>
						</tr>
					</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" align="center" cellpadding="0"
						cellspacing="0">
						<tr>
							<td height="45" colspan="4">
							<table width="98%" border="0" align="center" cellpadding="0"
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
											<td height="25" class="row-odd">
											<div align="center"><bean:message
												key="knowledgepro.slno" /></div>
											</td>
											<td width="30%" height="25" class="row-odd" align="left"><div align="center"><bean:message
												key="knowledgepro.exam.course" /></div></td>
											<td width="20%" height="25" class="row-odd" align="left"><div align="center"><bean:message
												key="knowledgepro.admin.grade.col" /></div></td>
											<td width="10%" height="25" class="row-odd" align="left"><div align="center"><bean:message
												key="knowledgepro.exam.fromPercentage" /> %</div></td>
											<td width="10%" height="25" class="row-odd" align="left"><div align="center"><bean:message
												key="knowledgepro.exam.toPercentage" /> %</div></td>
											<td width="10%" height="25" class="row-odd" align="left"><div align="center">Grade Point </div></td>
											<td width="20%" height="25" class="row-odd" align="left"><div align="center">Number Of Subjects</div></td>
											
										</tr>

										<logic:iterate id="GradeClassList" name="gradeClassDefinitionEntryForm" property="displayGradeDefinitionList"
											indexId="count">
									<tr>
										<c:choose>
											<c:when test="${count%2 == 0}">
												<tr class="row-even">
											</c:when>
											<c:otherwise>
												<tr class="row-white">
											</c:otherwise>
										</c:choose>
										<td height="25">
										<div align="center"><c:out value="${count + 1}" /></div>
										</td>
									
										<td align="center"><bean:write name="GradeClassList"
											property="courseName" /></td>
									
										<td align="center"><bean:write name="GradeClassList"
											property="grade" /></td>
										<td align="center"><bean:write name="GradeClassList"
											property="fromPercentage" /></td>
										<td align="center"><bean:write name="GradeClassList"
											property="toPercentage" /></td>
										<td align="center"><bean:write name="GradeClassList"
											property="gradePoint" /></td>
										<td align="center"><bean:write name="GradeClassList"
											property="numberOfSubject" /></td>
									</tr>
								</logic:iterate>

								
										
									
										


									</table>
									</td>
									<td width="5" height="30" background="images/right.gif"></td>
								</tr>
								<tr>
									<td height="5"><img src="images/04.gif" width="5"
										height="5" /></td>
									<td background="images/05.gif"></td>
									<td><img src="images/06.gif" /></td>
								</tr>
							</table>
							</td>
						</tr>
					</table>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
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
