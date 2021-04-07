<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>

<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>

<head>
<title>:: CMS ::</title>
<script type="text/javascript" language="javascript">
	function deleteCourseDetails(id) {
		if (confirm("Are you sure to delete this Entry?"))
			document.location.href = "ExamPromotionCriteria.do?method=deleteExamPromotionCriteria&id="
					+ id;

	}
	function editCourseDetails(id) {
		document.location.href = "ExamPromotionCriteria.do?method=editExamPromotionCriteria&id="
				+ id;
		document.getElementById("submit").value = "Update";

	}

	function getScheme(courseId) {

		getSchemeNoByCourseId("schemeMap", courseId, "toScheme", updateToScheme);

	}
	function updateToScheme(req) {

		updateOptionsFromMap(req, "fromScheme", "- Select -");
		updateOptionsFromMap(req, "toScheme", "- Select -");
	}
	function getScheme1(toScheme) {

		b = document.getElementById("fromScheme").value;
		getSchemeValuesBySchemeId("schemesMap", b, "scheme", updateScheme,
				toScheme);

	}

	function updateScheme(req) {
		updateOptionsFromMapForNonDefaultSelection(req, "scheme");

	}
	function resetValues() {
		document.location.href = "ExamPromotionCriteria.do?method=initExamPromotionCriteria";
	}
</script>
</head>
<html:form action="/ExamPromotionCriteria.do" method="POST"
	enctype="multipart/form-data">
	<html:hidden property="formName" value="ExamPromotionCriteriaForm"
		styleId="formName" />
	<html:hidden property="pageType" value="1" styleId="pageType" />

	<c:choose>
		<c:when test="${operation != null}">
			<html:hidden property="id" />
			<html:hidden property="originalCourseId" />
			<html:hidden property="originalFromScheme" />
			<html:hidden property="originalToScheme" />
			<html:hidden property="originalScheme" />
			<html:hidden property="method" styleId="method"
				value="updateExamPromotionCriteria" />
		</c:when>
		<c:otherwise>
			<html:hidden property="method" styleId="method"
				value="addExamPromotionCriteria" />
		</c:otherwise>
	</c:choose>


	<table width="99%" border="0">

		<tr>
			<tr>
				<td><span class="Bredcrumbs"><bean:message
					key="knowledgepro.exam" /> <span class="Bredcrumbs">&gt;&gt;
				<bean:message key="knowledgepro.exam.ExamPromotionCriteria" />
				&gt;&gt;</span></span></td>
			</tr>


			<tr>
				<td>
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td width="9"><img src="images/Tright_03_01.gif" width="9"
							height="29"></td>
						<td background="images/Tcenter.gif" class="heading_white">
						Promotion Criteria</td>
						<td width="10"><img src="images/Tright_1_01.gif" width="9"
							height="29"></td>
					</tr>

					<tr>
						<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
						<td class="heading">
						<div align="right" class="mandatoryfield">*Mandatory fields</div>
						</td>
						<td valign="top" background="images/Tright_3_3.gif"></td>
					</tr>
					<tr>
						<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
						<td class="heading">

						<table width="100%" border="0" align="center" cellpadding="0"
							cellspacing="0">
							<tr>
								<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
								<FONT color="green"> <html:messages id="msg"
									property="messages" message="true">
									<c:out value="${msg}" escapeXml="false"></c:out>
									<br>
								</html:messages> </FONT></div>
							</tr>
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

										<td width="30%" height="25" valign="top" class="row-odd">
										<div align="right"><span class="Mandatory">*</span>Course:</div>
										</td>
										<td height="25" colspan="3" class="row-even">

										<table width="187" border="0">
											<tr>



												<td width="181"><html:select property="courseId"
													styleClass="body" styleId="courseId"
													onchange="getScheme(this.value)">

													<html:option value="-1">
														<bean:message key="knowledgepro.select" />
													</html:option>
													<html:optionsCollection property="courseMap"
														name="ExamPromotionCriteriaForm" label="value" value="key" />
												</html:select></td>


											</tr>


										</table>
									</tr>

									<tr>
										<td width="10%" class="row-odd">
										<div align="right"><span class="Mandatory">*</span>From
										Scheme:</div>
										</td>
										<td width="181" class="row-even"><html:select
											property="fromScheme" styleClass="body" styleId="fromScheme">
											<html:option value="-1">
												<bean:message key="knowledgepro.select" />
											</html:option>
											<c:choose>
												<c:when test="${operation != null }">
													<html:optionsCollection name="schemeMap" label="value"
														value="key" />

												</c:when>
												<c:otherwise>
													<c:if
														test="${ExamPromotionCriteriaForm.courseId != null && ExamPromotionCriteriaForm.courseId != ''}">
														<c:set var="schemeMap"
															value="${baseActionForm.collectionMap['schemeMap']}" />
														<c:if test="${schemeMap != null}">
															<html:optionsCollection name="schemeMap" label="value"
																value="key" />
														</c:if>
													</c:if>
												</c:otherwise>
											</c:choose>

										</html:select></td>
										<td width="10%" class="row-odd">
										<div align="right"><span class="Mandatory">*</span>To
										Scheme:</div>
										</td>
										<td width="181" class="row-even"><html:select
											name="ExamPromotionCriteriaForm" property="toScheme"
											styleId="toScheme" styleClass="combo"
											onchange="getScheme1(this.value)">
											<html:option value="-1">
												<bean:message key="knowledgepro.select" />
											</html:option>
											<c:choose>
												<c:when test="${operation != null }">



													<html:optionsCollection name="schemeMap" label="value"
														value="key" />

												</c:when>
												<c:otherwise>
													<c:if
														test="${ExamPromotionCriteriaForm.courseId != null && ExamPromotionCriteriaForm.courseId != ''}">
														<c:set var="schemeMap"
															value="${baseActionForm.collectionMap['schemeMap']}" />
														<c:if test="${schemeMap != null}">
															<html:optionsCollection name="schemeMap" label="value"
																value="key" />
														</c:if>
													</c:if>
												</c:otherwise>
											</c:choose>

										</html:select></td>
								</table>
								</td>
								<td width="5" background="images/right.gif"></td>
							</tr>
							<tr>
								<td height="5"><img src="images/04.gif" width="5"
									height="5" /></td>
								<td background="images/05.gif"></td>
								<td><img src="images/06.gif" /></td>

							</tr>
						</table>
						</td>
						<td valign="top" background="images/Tright_3_3.gif"></td>
					</tr>

					<tr>
						<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
						<td class="heading">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">

						</table>
						</td>
						<td valign="top" background="images/Tright_3_3.gif"></td>
					</tr>
					<tr>
						<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
						<td class="heading">
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

										<td width="25%" height="25" rowspan="2" class="row-odd">
										<div align="right"><span class="Mandatory">*</span>Scheme
										:</div>
										</td>
										<td width="25%" height="25" colspan="3" rowspan="2"
											class="row-even"><nested:select property="scheme"
											styleClass="body" multiple="multiple" size="8"
											styleId="scheme" style="width:200px">
											<c:choose>
												<c:when test="${operation != null }">

													<html:optionsCollection name="schemesMap" label="value"
														value="key" />
												</c:when>
												<c:otherwise>
													<c:if
														test="${ExamPromotionCriteriaForm.courseId != null && ExamPromotionCriteriaForm.courseId != ''}">
														<c:set var="schemesMap"
															value="${baseActionForm.collectionMap['schemesMap']}" />
														<c:if test="${schemesMap != null}">
															<html:optionsCollection name="schemesMap" label="value"
																value="key" />
														</c:if>
													</c:if>
												</c:otherwise>
											</c:choose>









										</nested:select></td>
										<td width="25%" height="53" class="row-odd">
										<div align="right">
										<div align="right"><html:radio property="maxBacklog"
											value="percentage"></html:radio> <span class="Mandatory">*</span>
										<bean:message
											key="knowledgepro.exam.Max.BacklogCountPercentage" /></div>
										</td>
										<td width="25%" class="row-even"><html:text
											property="backLogCountPercentage" styleClass="TextBox"
											styleId="BacklogCountPercentage" size="16" maxlength="4" /></td>
									</tr>
									<tr>
										<td class="row-odd">
										<div align="right">
										<div align="right"><html:radio property="maxBacklog"
											value="number"></html:radio> <span class="Mandatory">*</span>
										<bean:message key="knowledgepro.exam.Max.BacklogNumbers" /></div>
										</td>
										<td width="25%" class="row-even"><html:text
											property="backLogNumbers" styleClass="TextBox"
											styleId="BacklogNumbers" size="16" maxlength="4" /></td>
									</tr>

								</table>
								</td>
								<td width="5" background="images/right.gif"></td>
							</tr>
							<tr>

								<td height="5"><img src="images/04.gif" width="5"
									height="5" /></td>
								<td background="images/05.gif"></td>
								<td><img src="images/06.gif" /></td>
							</tr>
						</table>
						</td>
						<td valign="top" background="images/Tright_3_3.gif"></td>
					</tr>
					<tr>
						<td height="19" valign="top" background="images/Tright_03_03.gif"></td>

						<td class="heading">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr><td style="height: 10px" align="center" colspan="3"></td></tr>
							<tr>
								<td width="51%" height="35" align="right">
								<div align="right"><c:choose>
									<c:when test="${operation!=null}">
										<html:submit styleClass="formbutton" styleId="button">
											<bean:message key="knowledgepro.update" />
										</html:submit>
									</c:when>
									<c:otherwise>
										<html:submit styleClass="formbutton" styleId="button">
											<bean:message key="knowledgepro.admin.add" />
										</html:submit>&nbsp;&nbsp;&nbsp;&nbsp;
									</c:otherwise>
								</c:choose></div>
								</td>
								<td width="0%" height="35" align="center"><html:reset
									styleClass="formbutton" styleId="button"
									onclick="resetValues()">
										Reset
									</html:reset></td>
								<td width="49%" align="left">&nbsp;</td>
							</tr>							
							<tr><td style="height: 10px" align="center" colspan="3"></td></tr>							
						</table>
						</td>
						<td valign="top" background="images/Tright_3_3.gif"></td>
					</tr>

					<tr>
						<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
						<td class="heading">
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
										<td height="25" class="row-odd">
										<div align="center">Sl.No</div>
										</td>
										<td width="16%" height="25" class="row-odd">Course</td>
										<td class="row-odd">From Scheme</td>
										<td class="row-odd">To Scheme</td>

										<td class="row-odd">Scheme</td>
										<td class="row-odd">Max.Backlog Count Percentage</td>
										<td class="row-odd">Max. Backlog Numbers</td>
										<td class="row-odd">
										<div align="center">Edit</div>
										</td>
										<td class="row-odd">
										<div align="center">Delete</div>
										</td>
									</tr>


									<logic:iterate id="list" property="mainList"
										name="ExamPromotionCriteriaForm"
										type="com.kp.cms.to.exam.ExamPromotionCriteriaTO"
										indexId="count">
										<c:choose>
											<c:when test="${count%2 == 0}">
												<tr class="row-even">
											</c:when>
											<c:otherwise>
												<tr class="row-white">
											</c:otherwise>
										</c:choose>
										<td height="25" width="7%">
										<div align="center"><c:out value="${count+1}"></c:out></div>
										</td>
										<td height="25" width="15%" align="center"><bean:write
											property="courseName" name="list" /></td>
										<td width="15%" align="center"><bean:write
											property="fromScheme" name="list" /></td>
										<td width="15%" align="center"><bean:write
											property="toScheme" name="list" /></td>
										<td width="15%" align="center"><bean:write
											property="scheme" name="list" /></td>
										<td width="15%" align="center"><bean:write
											property="maxBackLogCountPrcntg" name="list" /></td>

										<td width="15%" align="center"><bean:write
											property="maxBackLogNumber" name="list" /></td>
										<td height="25">
										<div align="center"><img src="images/edit_icon.gif"
											height="18" style="cursor: pointer"
											onclick="editCourseDetails('<bean:write name="list" property="id"/>')">
										</div>
										</td>
										<td height="25">
										<div align="center"><img src="images/delete_icon.gif"
											width="16" height="16" style="cursor: pointer"
											onclick="deleteCourseDetails('<bean:write name="list" property="id"/>')"></div>
										</td>
										</tr>
									</logic:iterate>
									<!--<tr >
                  <td height="25" class="row-even"><div align="center">3</div></td>
                   <td height="25" class="row-even" >2008-09</td>
                  <td height="25" class="row-even" >PG - MBA  - Business Studies</td>
                   <td height="25" class="row-even" >Yes</td>
                   <td height="25" class="row-even" ><div align="center"><a href="grades3.html"><img src="images/edit_icon.gif" alt="" width="16" height="18" border="0"></a></div></td>
                  <td height="25" class="row-even" ><div align="center"><img src="images/delete_icon.gif" width="16" height="16"></div></td>
                </tr> -->
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
						<td valign="top" background="images/Tright_3_3.gif"></td>
					</tr>

					<tr>
						<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
						<td class="heading">&nbsp;</td>
						<td valign="top" background="images/Tright_3_3.gif"></td>
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
