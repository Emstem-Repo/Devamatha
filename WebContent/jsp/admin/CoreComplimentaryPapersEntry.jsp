<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<script type="text/javascript">
function editCoreComp(corecompId, coreSubject,complementary1Subject,complementary2Subject,courseId) {
	document.getElementById("method").value = "updateCoreComplimentaryPapers";
	document.getElementById("corecompId").value = corecompId;
	document.getElementById("courseId").value = courseId;
	document.getElementById("orgcourseId").value = courseId;
	document.getElementById("orgcorecompId").value = corecompId;
	document.getElementById("coreSubject").value = coreSubject;
	document.getElementById("complementary1Subject").value = complementary1Subject;
	document.getElementById("complementary2Subject").value = complementary2Subject;
	document.getElementById("orgcoresubject").value = coreSubject;
	document.getElementById("orgcomp1subject").value = complementary1Subject;
	document.getElementById("orgcomp2subject").value = complementary2Subject;
	document.getElementById("submitbutton").value="Update";
}
function deleteCoreComp(corecompId,coreSubject,courseId) {
	deleteConfirm = confirm("Are you sure you want to delete this entry?");
	if (deleteConfirm) {
		document.location.href = "CoreComplimentaryPaperEntry.do?method=deleteCoreComplimentaryPapers&corecompId="
				+ corecompId+"&coreSubject="+coreSubject+"&courseId="+courseId;
	}
}
function reActivate(){
	document.location.href = "CoreComplimentaryPaperEntry.do?method=reActivateCoreComplimentaryPapers";
}
function resetFormFields(){	
	document.getElementById("courseId").value = "";
	document.getElementById("coreSubject").value =null;
	document.getElementById("complementary1Subject").value =null;
	document.getElementById("complementary2Subject").value =null;
	resetErrMsgs();
	if (document.getElementById("method").value == "updateCoreComplimentaryPapers") {
		document.getElementById("corecompId").value = document.getElementById("orgcorecompId").value;
		document.getElementById("coreSubject").value = document.getElementById("orgcoresubject").value;
		document.getElementById("complementary1Subject").value = document.getElementById("orgcomp1subject").value;
		document.getElementById("complementary2Subject").value = document.getElementById("orgcomp2subject").value;
	}
}
</script>
<html:form action="/CoreComplimentaryPaperEntry">	
		<c:choose>
		<c:when test="${operation == 'edit'}">
		<html:hidden property="method" styleId="method" value="updateCoreComplimentaryPapers" />
		</c:when>
		<c:otherwise>
		<html:hidden property="method" styleId="method" value="addCoreComplimentaryPapers" />
		</c:otherwise>
		</c:choose>
	<html:hidden property="corecompId" styleId="corecompId" />
	<html:hidden property="orgcourseId" styleId="orgcourseId" />
	<html:hidden property="orgcorecompId" styleId="orgcorecompId" />
	<html:hidden property="orgcoresubject" styleId="orgcoresubject" />
	<html:hidden property="orgcomp1subject" styleId="orgcomp1subject" />
	<html:hidden property="orgcomp2subject" styleId="orgcomp2subject" />
	<html:hidden property="formName" value="CoreComplimentaryPaperForm" />
	<html:hidden property="pageType" value="1" />
	<table width="100%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message key="knowledgepro.admin"/>
			<span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.admin.corecomplimentary"/>
			 &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td valign="top">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> <bean:message key="knowledgepro.admin.corecomplimentary"/> Entry</strong></td>

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
								
									<td width="16%" height="25" class="row-odd" colspan="2">
										<div align="right"><span class="Mandatory">*</span>Course:</div>
									</td>
									<td width="16%" height="25" class="row-even"><label>
									<html:select property="courseId" styleClass="combo"
										styleId="courseId">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<html:optionsCollection name="CourseList" label="name"
											value="id" />
									</html:select> </label> <span class="star"></span></td>
								</tr>
								<tr>
									<td width="16%" height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>Core Subject 1:</div>
									</td>
									<td width="16%" height="25" class="row-even">
										<html:text
											property="coreSubject" styleId="coreSubject" styleClass="TextBox"
											size="20" maxlength="100" />
									</td>
									<td width="16%" height="25" class="row-odd">
										<div align="right">Complementary Subject 1:</div>
									</td>
									<td width="16%" height="25" class="row-even">
										<html:text
											property="complementary1Subject" styleId="complementary1Subject" styleClass="TextBox"
											size="20" maxlength="100" />
									</td>	
								</tr>
								<tr>
									<td width="16%" height="25" class="row-odd">
									<div align="right">Core Subject 2:</div>
									</td>
									<td width="16%" height="25" class="row-even">
										<html:text
											property="coreSubject2" styleId="coreSubject2" styleClass="TextBox"
											size="20" maxlength="100" /><span class="star"></span>
									</td>									
									<td width="16%" height="25" class="row-odd">
										<div align="right">Complementary Subject 2:</div>
									</td>									
									<td width="16%" height="25" class="row-even">
										<html:text 
											property="complementary2Subject" styleId="complementary2Subject" styleClass="TextBox"
											size="20" maxlength="100" />
									</td>	
								</tr>
								<tr>
									<td width="16%" height="25" class="row-odd">
										<div align="right">Core Subject 3:</div>
									</td>
									<td width="16%" height="25" class="row-even">
										<html:text
											property="coreSubject3" styleId="coreSubject3" styleClass="TextBox"
											size="20" maxlength="100" />
									</td>									
									<td width="16%" height="25" class="row-odd">
										<div align="right">Complementary Subject 3:</div>
									</td>									
									<td width="16%" height="25" class="row-even">
										<html:text 
											property="complementary3Subject" styleId="complementary3Subject" styleClass="TextBox"
											size="20" maxlength="100" />
									</td>	
								</tr>
								<tr>
									<td width="16%" height="25" class="row-odd" colspan="2">
									<div align="right">Open Subject :</div>
									</td>
									<td width="16%" height="25" class="row-even">
										<html:text
											property="openSubject" styleId="openSubject" styleClass="TextBox"
											size="20" maxlength="100" />
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
							<c:when test="${operation == 'edit'}">
									<html:submit property="" styleClass="formbutton" value="Update"
										styleId="submitbutton">
									</html:submit>
							</c:when>
							<c:otherwise>
								<html:submit property="" styleClass="formbutton" value="Submit"
										styleId="submitbutton">
								</html:submit>
							</c:otherwise>
							</c:choose>
								</div>
							</td>
							<td width="2%"></td>
							<td width="53%">
							<html:button property=""
							styleClass="formbutton" value="Reset"
							onclick="resetFormFields()"></html:button>
							</td>
						</tr>
					</table>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>	
				<tr>
							<td height="25" colspan="6">
							<table width="100%" border="0" align="center" cellpadding="0"
								cellspacing="0">
								<tr>
									<td><img src="images/01.gif" width="5" height="5"></td>
									<td width="914" background="images/02.gif"></td>
									<td><img src="images/03.gif" width="5" height="5"></td>
								</tr>
								<tr>
									<td width="5" background="images/left.gif"></td>
									<td valign="top">
									<table width="100%" cellspacing="1" cellpadding="2">

										<tr>
											<td align="center" width="5%" height="25" class="row-odd"><bean:message key="knowledgepro.slno" /></td>
											<td align="center" width="10%" class="row-odd"><bean:message key="knowledgepro.admin.coursename" /></td>
											<td align="center" width="10%" class="row-odd">Core Subject 1</td>
											<td align="center" width="10%" class="row-odd">Core Subject 2</td>
											<td align="center" width="10%" class="row-odd">Core Subject 3</td>
											<td align="center" width="10%" class="row-odd">Complementary Subject 1</td>
											<td align="center" width="10%" class="row-odd">Complementary Subject 2</td>
											<td align="center" width="10%" class="row-odd">Complementary Subject 3</td>
											<td align="center" width="10%" class="row-odd">Open Subject</td>
											<td align="center" width="9%" class="row-odd"><bean:message key="knowledgepro.edit" /></td>
											<td align="center" width="8%" class="row-odd"><bean:message key="knowledgepro.delete" /></td>
										</tr>
										<logic:iterate id="cList" property="corecompList" name="CoreComplimentaryPaperForm" indexId="count">
											<c:choose>
												<c:when test="${count%2 == 0}">
													<tr class="row-even">
												</c:when>
												<c:otherwise>
													<tr class="row-white">
												</c:otherwise>
											</c:choose>
											<td align="center" height="25"><c:out value="${count + 1}" /></td>
											<td align="center"><bean:write name="cList" property="courseto.name" /></td>
											<td align="center"><bean:write name="cList" property="coreSubject" /></td>	
											<td align="center"><bean:write name="cList" property="coreSubject2" /></td>
											<td align="center"><bean:write name="cList" property="coreSubject3" /></td>
											<td align="center"><bean:write name="cList" property="complementary1Subject" /></td>
											<td align="center"><bean:write name="cList" property="complementary2Subject" /></td>	
											<td align="center"><bean:write name="cList" property="complementary3Subject" /></td>
											<td align="center"><bean:write name="cList" property="openSubject" /></td>
											<td align="center">
												<img src="images/edit_icon.gif"
													width="16" height="18"
													onclick="editCoreComp('<bean:write name="cList" property="corecompId" />','<bean:write name="cList" property="coreSubject" />','<bean:write name="cList" property="complementary1Subject" />','<bean:write name="cList" property="complementary2Subject" />','<bean:write name="cList" property="courseto.id" />')" />
											</td>
											<td align="center">
												<img src="images/delete_icon.gif"
													width="16" height="16"
													onclick="deleteCoreComp('<bean:write name="cList" property="corecompId" />','<bean:write name="cList" property="coreSubject" />','<bean:write name="cList" property="courseto.id" />')" />
											</td>
										</logic:iterate>
									</table>
									</td>
									<td width="5" height="30" background="images/right.gif"></td>
								</tr>
								<tr>
									<td height="5"><img src="images/04.gif" width="5"
										height="5"></td>
									<td background="images/05.gif"></td>
									<td><img src="images/06.gif"></td>
								</tr>
							</table>
							</td>
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
