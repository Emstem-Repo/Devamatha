<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<head>


<script type="text/javascript" language="javascript">
	function editElegSetUp(id, classID, examTypeID) {
		document.location.href = "ExamExamEligibilitySetUp.do?method=editExamEligibilitySetUp&id="
				+ id + "&classId=" + classID + "&examtypeId=" + examTypeID;
		document.getElementById("submit").value = "Update";

		//resetErrMsgs();
	}

	function deleteElegSetUp(id, className, examType) {
		deleteConfirm = confirm("Are you sure to delete class '" + className
				+ "' and exam Type '" + examType + "' this entry?");
		if (deleteConfirm) {
			document.location.href = "ExamExamEligibilitySetUp.do?method=deleteExamExamEligibilitySetUp&id="
					+ id;
		}
	}
	function reActivate(id) {
		document.location.href = "ExamExamEligibilitySetUp.do?method=reActivateEligibilitySetUp&id="
				+ id;
	}
	function moveoutid() {

		var mapFrom = document.getElementById('mapClass');
		var len = mapFrom.length;
		var mapTo = document.getElementById('selsubMap');

		if (mapTo.length == 0) {
			document.getElementById("moveIn").disabled = false;
		}
		for ( var j = 0; j < len; j++) {
			if (mapFrom[j].selected) {

				//listClasses.push(mapFrom[j].value);
				var tmp = mapFrom.options[j].text;
				var tmp1 = mapFrom.options[j].value;
				mapFrom.remove(j);
				len--;
				j--;
				if (j < 0) {
					document.getElementById("moveOut").disabled = true;
					document.getElementById("moveIn").disabled = false;
				}
				if (mapFrom.length <= 0)
					document.getElementById("moveOut").disabled = true;
				else
					document.getElementById("moveOut").disabled = false;
				var y = document.createElement('option');

				y.text = tmp;
				y.value = tmp1;
				y.setAttribute("class", "comboBig");
				try {
					mapTo.add(y, null);
				} catch (ex) {
					mapTo.add(y);
				}
			}
		}

	}

	function getClassValues() {
		var listClasses = new Array();
		var mapTo1 = document.getElementById('selsubMap');
		var len1 = mapTo1.length;
		for ( var k = 0; k < len1; k++) {
			listClasses.push(mapTo1[k].value);

		}
		document.getElementById("classValues").value = listClasses;

	}

	function moveinid() {
		var mapFrom = document.getElementById('mapClass');
		var mapTo = document.getElementById('selsubMap');
		var len = mapTo.length;

		for ( var j = 0; j < len; j++) {
			if (mapTo[j].selected) {
				var tmp = mapTo.options[j].text;
				var tmp1 = mapTo.options[j].value;
				mapTo.remove(j);
				len--;
				j--;
				if (j < 0) {
					document.getElementById("moveIn").disabled = true;
					document.getElementById("moveOut").disabled = false;
				}
				if (mapTo.length != 0) {
					document.getElementById("moveOut").disabled = false;
					document.getElementById("moveIn").disabled = false;
				} else
					document.getElementById("moveOut").disabled = false;
				var y = document.createElement('option');
				y.setAttribute("class", "comboBig");
				y.text = tmp;
				try {
					mapFrom.add(y, null);
				} catch (ex) {
					mapFrom.add(y);
				}
			}
		}

	}

	function reFreshValues() {
		resetErrMsgs();
		document.location.href = "ExamExamEligibilitySetUp.do?method=initExamEligibilitySetUpAction";

	}
</script>

</head>
<html:form action="/ExamExamEligibilitySetUp.do">

	<html:hidden property="formName" value="ExamExamEligibilitySetUpForm" />

	<html:hidden property="classValues" styleId="classValues" />

	<c:choose>
		<c:when
			test="${ExamEligibilitySetOperation != null && ExamEligibilitySetOperation == 'edit'}">
			<html:hidden property="method" styleId="method"
				value="updateExamEligibilitySetUp" />
			<html:hidden property="classValues" styleId="classValues" />
			<html:hidden property="examtypeId" styleId="examtypeId" />
			<html:hidden property="classId" styleId="classId" />
			<html:hidden property="eligibilitySetUpId"
				styleId="eligibilitySetUpId" />


		</c:when>
		<c:otherwise>
			<html:hidden property="method" styleId="method"
				value="addExamEligibilitySetUp" />
			<html:hidden property="pageType" value="1" />
		</c:otherwise>
	</c:choose>



	<table width="99%" border="0">

		<tr>
			<td><span class="heading"><span class="Bredcrumbs"><bean:message
				key="knowledgepro.exam.exam" /> &gt;&gt; <bean:message
				key="knowledgepro.exam.examEligibilitySetUp" /> &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="heading_white"><bean:message
						key="knowledgepro.exam.examEligibilitySetUp" /></td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="heading">
					<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
					<FONT color="green"> <html:messages id="msg"
						property="messages" message="true">
						<c:out value="${msg}" escapeXml="false"></c:out>
						<br>
					</html:messages> </FONT></div>
					<div align="right" class="mandatoryfield"><bean:message
						key="knowledgepro.mandatoryfields" /></div>
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
									<td width="35%" height="25" valign="top" class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.exam.examEligibilitySetUp.class" /> :</div>
									</td>
									<td width="65%" height="25" class="row-even">
									<table border="0">
										<tr>
											<td width="112"><label> <nested:select
												property="courseIdsFrom" styleClass="body"
												multiple="multiple" size="8" styleId="mapClass"
												style="width:200px">
												<nested:optionsCollection
													name="ExamExamEligibilitySetUpForm" property="mapClass"
													label="value" value="key" />
											</nested:select> </label></td>
											<td width="49"><c:choose>
												<c:when
													test="${ExamEligibilitySetOperation != null && ExamEligibilitySetOperation == 'edit'}">
													<table border="0">
														<tr><td style="height: 10px" align="center" colspan="1"></td></tr>
														<tr>
															<td><input type="button" onClick="moveoutid()" id="moveOut" value=">>" disabled="disabled"></td>
														</tr>
														<tr>
															<td><input type="button" value="&lt;&lt;" id="moveIn" onclick="moveinid()" disabled="disabled"/></td>
														</tr>
														<tr><td style="height: 10px" align="center" colspan="1"></td></tr>														
													</table>
												</c:when>
												<c:otherwise>
													<table border="0">
														<tr>
															<td><input type="button" onClick="moveoutid()" id="moveOut" value=">>"></td>
														</tr>
														<tr>
															<td><input type="button" value="&lt;&lt;" id="moveIn" onclick="moveinid()"/></td>
														</tr>
													</table>
												</c:otherwise>
											</c:choose></td>
											<td width="120"><label> <nested:select
												property="courseIdsTo" styleId="selsubMap" styleClass="body"
												multiple="multiple" size="8" style="width:200px;">
												<c:if
													test="${ExamExamEligibilitySetUpForm.mapSelectedClass!=null && ExamExamEligibilitySetUpForm.mapSelectedClass.size!=0}">
													<nested:optionsCollection
														name="ExamExamEligibilitySetUpForm"
														property="mapSelectedClass" label="value" value="key"
														styleClass="comboBig" />
												</c:if>
											</nested:select> </label></td>
										</tr>
									</table>
									</td>
								</tr>
								<tr>
									<td class="row-odd">
									<div align="right"><span class="mandatoryfield">*</span><bean:message
										key="knowledgepro.exam.examEligibilitySetUp.ExamType" />:</div>
									</td>
									<c:choose>
									
									<c:when test="${ExamEligibilitySetOperation != null && ExamEligibilitySetOperation == 'edit'}">
									
									<td class="row-even"><html:select property="examtypeId"
										styleClass="combo" styleId="examtypeId"
										name="ExamExamEligibilitySetUpForm" style="width:200px" disabled="true">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<logic:notEmpty name="ExamExamEligibilitySetUpForm"
											property="listExamtype">
											<html:optionsCollection property="listExamtype"
												name="ExamExamEligibilitySetUpForm" label="display"
												value="id" />
										</logic:notEmpty>
									</html:select></td>
								</c:when>
									<c:otherwise>
									<td class="row-even"><html:select property="examtypeId"
										styleClass="combo" styleId="examtypeId"
										name="ExamExamEligibilitySetUpForm" style="width:200px" >
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<logic:notEmpty name="ExamExamEligibilitySetUpForm"
											property="listExamtype">
											<html:optionsCollection property="listExamtype"
												name="ExamExamEligibilitySetUpForm" label="display"
												value="id" />
										</logic:notEmpty>
									</html:select></td>
									</c:otherwise>
									</c:choose>
									
								</tr>

							</table>
							</td>
							<td width="5" background="images/right.gif"></td>
						</tr>
						<tr>
							<td height="5"><img src="images/04.gif" width="5" height="5" /></td>
							<td background="images/05.gif"></td>
							<td><img src="images/06.gif" /></td>
						</tr>
					</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif"></td>
				</tr>
				<tr>
					<td height="25" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="heading"><bean:message
						key="knowledgepro.exam.examEligibilitySetUp.checkEligibility" /></td>
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
									<td width="20%" height="25" class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.exam.examEligibilitySetUp.noEligibilityCheck" />
									</div>
									</td>
									<td width="11%" height="25" class="row-even"><logic:equal
										name="ExamExamEligibilitySetUpForm"
										property="noEligibilityCheck" value="on">
										<html:checkbox property="noEligibilityCheck" value="on" />
									</logic:equal> <logic:equal name="ExamExamEligibilitySetUpForm"
										property="noEligibilityCheck" value="off">
										<html:checkbox property="noEligibilityCheck" />

									</logic:equal></td>
									<td width="20%" class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.exam.examEligibilitySetUp.examFees" /><br />
									</div>
									</td>
									<td width="10%" class="row-even"><logic:equal
										name="ExamExamEligibilitySetUpForm" property="examFees"
										value="on">
										<html:checkbox property="examFees" value="on" />
									</logic:equal> <logic:equal name="ExamExamEligibilitySetUpForm"
										property="examFees" value="off">
										<html:checkbox property="examFees" />

									</logic:equal></td>
									<td width="20%" class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.exam.examEligibilitySetUp.attendance" /></div>
									</td>


									<td width="13%" class="row-even"><logic:equal
										name="ExamExamEligibilitySetUpForm" property="attendance"
										value="on">
										<html:checkbox property="attendance" value="on" />
									</logic:equal> <logic:equal name="ExamExamEligibilitySetUpForm"
										property="attendance" value="off">
										<html:checkbox property="attendance" />

									</logic:equal></td>
								</tr>
								<tr>
									<td class="row-odd" height="25">
									<div align="right"><bean:message
										key="knowledgepro.exam.examEligibilitySetUp.courseFees" /></div>
									</td>
									<td class="row-even" colspan="5"><logic:equal
										name="ExamExamEligibilitySetUpForm" property="courseFees"
										value="on">
										<html:checkbox property="courseFees" value="on" />
									</logic:equal> <logic:equal name="ExamExamEligibilitySetUpForm"
										property="courseFees" value="off">
										<html:checkbox property="courseFees" />

									</logic:equal></td>
								</tr>
							</table>
							</td>
							<td width="5" background="images/right.gif"></td>
						</tr>
						<tr>
							<td height="5"><img src="images/04.gif" width="5" height="5" /></td>
							<td background="images/05.gif"></td>
							<td><img src="images/06.gif" /></td>
						</tr>
					</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif"></td>
				</tr>
				<tr>
					<td height="25" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="heading"><bean:message
						key="knowledgepro.exam.examEligibilitySetUp.additionalEligibility" /></td>
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
								<nested:iterate name="ExamExamEligibilitySetUpForm"
									property="listAdditionalEligibility"
									id="listAdditionalEligibility" indexId="eligibilityCount">
									<c:if test="${(eligibilityCount)%3 == 0}">
										<tr class="row-even">
									</c:if>

									<td width="20%" class="row-odd" align="right"><nested:write
										name="listAdditionalEligibility" property="display" /> :</td>



									<td width="10%" height="25" align="left" class="row-even">

									<c:if test="${listAdditionalEligibility.value == 1}">
										<input type="checkbox" name="additionalEligibility"
											value='<nested:write
										name="listAdditionalEligibility" property="id" />'
											checked="checked" />
									</c:if> <c:if test="${listAdditionalEligibility.value == 0}">
										<input type="checkbox" name="additionalEligibility"
											value='<nested:write
										name="listAdditionalEligibility" property="id" />' />
									</c:if></td>







								</nested:iterate>

								</tr>



							</table>














							</td>
							<td width="5" background="images/right.gif"></td>
						</tr>
						<tr>
							<td height="5"><img src="images/04.gif" width="5" height="5" /></td>
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
						<tr>
							<td width="49%" height="35" align="right"><c:choose>
								<c:when
									test="${ExamEligibilitySetOperation != null && ExamEligibilitySetOperation == 'edit'}">
									<input name="submit" type="submit" class="formbutton" value="Update" />

								</c:when>
								<c:otherwise>
									<input name="submit" type="submit" class="formbutton" value="Submit" onclick="getClassValues()"/>

								</c:otherwise>
							</c:choose></td>
							<td width="2%" height="35" align="center">&nbsp;</td>
							<td width="49%" align="left"><input type="Reset" class="formbutton" value="Cancel" onClick="reFreshValues()"/></td>
						</tr>
					</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif"></td>
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
									<td height="25" class="row-odd">
									<div align="center"><bean:message key="knowledgepro.slno" />
									</div>
									</td>
									<td height="25" class="row-odd"><bean:message
										key="knowledgepro.exam.examEligibilitySetUp.class" /></td>
									<td height="25" class="row-odd"><bean:message
										key="knowledgepro.exam.examEligibilitySetUp.ExamType" /></td>
									<td height="25" class="row-odd">
									<div align="center"><bean:message key="knowledgepro.edit" /></div>
									</td>
									<td height="25" class="row-odd">
									<div align="center"><bean:message
										key="knowledgepro.delete" /></div>
									</td>
								</tr>

								<c:set var="temp" value="0" />
								<logic:iterate name="ExamExamEligibilitySetUpForm"
									property="listExamEligibilitySetUp"
									id="listExamEligibilitySetUp"
									type="com.kp.cms.to.exam.ExamExamEligibilitySetUpTO"
									indexId="count">
									<c:choose>
										<c:when test="${temp == 0}">
											<tr>
												<td height="25" class="row-even">
												<div align="center"><c:out value="${count+1}" /></div>
												</td>
												<td height="25" class="row-even" align="left"><bean:write
													name="listExamEligibilitySetUp" property="className" /></td>
												<td height="25" class="row-even" align="left"><bean:write
													name="listExamEligibilitySetUp" property="examtypeName" /></td>
												<td width="11%" height="25" class="row-even">
												<div align="center"><img src="images/edit_icon.gif"
													height="18" style="cursor: pointer"
													onclick="editElegSetUp('<bean:write name="listExamEligibilitySetUp" property="id"/>','<bean:write name="listExamEligibilitySetUp" property="classId"/>','<bean:write name="listExamEligibilitySetUp" property="examTypeId"/>')"></div>
												</td>
												<td height="25" class="row-even">
												<div align="center"><img src="images/delete_icon.gif"
													width="16" height="16" style="cursor: pointer"
													onclick="deleteElegSetUp('<bean:write name="listExamEligibilitySetUp" property="id"/>','<bean:write name="listExamEligibilitySetUp" property="className"/>','<bean:write name="listExamEligibilitySetUp" property="examtypeName"/>')"></div>
												</td>
											</tr>
											<c:set var="temp" value="1" />
										</c:when>
										<c:otherwise>
											<tr>
												<td height="25" class="row-white">
												<div align="center"><c:out value="${count+1}" /></div>
												</td>
												<td height="25" class="row-white" align="left"><bean:write
													name="listExamEligibilitySetUp" property="className" /></td>
												<td height="25" class="row-white" align="left"><bean:write
													name="listExamEligibilitySetUp" property="examtypeName" /></td>
												<td height="25" class="row-white">
												<div align="center"><img src="images/edit_icon.gif"
													height="18" style="cursor: pointer"
													onclick="editElegSetUp('<bean:write name="listExamEligibilitySetUp" property="id"/>','<bean:write name="listExamEligibilitySetUp" property="classId"/>','<bean:write name="listExamEligibilitySetUp" property="examTypeId"/>')"></div>
												</td>
												<td height="25" class="row-white">
												<div align="center"><img src="images/delete_icon.gif"
													width="16" height="16" style="cursor: pointer"
													onclick="deleteElegSetUp('<bean:write name="listExamEligibilitySetUp" property="id"/>','<bean:write name="listExamEligibilitySetUp" property="className"/>','<bean:write name="listExamEligibilitySetUp" property="examtypeName"/>')"></div>
												</td>
											</tr>
											<c:set var="temp" value="0" />
										</c:otherwise>
									</c:choose>
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
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				<tr>
					<td height="25" valign="top" background="images/Tright_03_03.gif"></td>
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



