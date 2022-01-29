<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@page import="java.util.Map,java.util.HashMap"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<html>
<head>
<title>:: CMS ::</title>
<script type="text/javascript">
	function getSubjectsType(subjectNo) {
		if (subjectNo != '') {
			var args = "method=getSubjectsTypeBySubjectIdAndCollection&subjectId="
					+ subjectNo;
			var url = "AjaxRequest.do";
			requestOperation(url, args, updateClass);
		}
	}
	function updateClass(req) {
		updateOptionsFromMap(req, "subjectType", "- Select -");
		updateSubjectsTypeBySubjectId(req, "subjectType");
	}
	function getBatches() {
		subjectId = document.getElementById("subjectId").value;
		classId = document.getElementById("classId").value;
		getBatchesBySubject("subjectMap", subjectId, classId, "batchValues",
				updateBatches);
	}
	function updateBatches(req) {
		updateOptionsFromMapForNonDefaultSelection(req, "batchValues");
	}
	function getPeriodTo(classSchemaId) {
		getPeriodsByClassSchemewiseIds("periodMap",
				getArrayValues(classSchemaId), "periods", updatePeriodTo);
	}
	function updatePeriodTo(req) {
		updateOptionsFromMapForNonDefaultSelection(req, "periods");
	}
	function deleteDetails(id) {
		if (confirm("Are You Sure To Delete This Entry?"))
			document.location.href = "manageTimeTable.do?method=delete&id="
					+ id;
	}
</SCRIPT>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<script language="JavaScript" src="calendar_us.js"></script>
<link rel="stylesheet" href="calendar.css">

<link href="css/styles.css" rel="stylesheet" type="text/css">

</head>

<body>
<html:form action="/manageTimeTable.do">
	<html:hidden property="pageType" styleId="pageType" value="2" />
	<html:hidden property="formName" value="ManageTimeTableForm" />
	<html:hidden property="method" styleId="method" value="add" />
	<html:hidden property="classes" styleId="classId" />
	<table width="99%" border="0">

		<tr>
			<td><span class="heading"><span class="Bredcrumbs">&gt;&gt;
			Manage Time Table &gt;&gt;</span></span></td>
		</tr>

		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="heading_white">Manage
					Time Table</td>
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
							<td><img src="images/01.gif" width="5" height="5" /></td>

							<td width="914" background="images/02.gif"></td>
							<td><img src="images/03.gif" width="5" height="5" /></td>
						</tr>
						<tr>
							<td width="5" background="images/left.gif"></td>
							<td valign="top">
							<table width="100%" cellspacing="1" cellpadding="2">
								<tr>
									<td width="17%" height="25" class="row-odd">
									<div align="right">Academic Year:</div>
									</td>

									<td width="20%" class="row-even"><bean:write
										name="ManageTimeTableForm" property="academicYr" /></td>
									<td width="20%" class="row-odd">
									<div align="right">Class :</div>
									</td>
									<td width="20%" class="row-even"><bean:write
										name="ManageTimeTableForm" property="className" /></td>
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
					<td valign="top" background="images/Tright_3_3.gif"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="heading">&nbsp;</td>
					<td valign="top" background="images/Tright_3_3.gif"></td>
				</tr>
				<tr>

					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td height="25" class="heading">Subject Details:</td>
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
									<td width="32" height="25" class="row-odd">
									<div align="center">Sl.No</div>
									</td>

									<td class="row-odd"><span class="Mandatory">*</span>Teaching
									Staff</td>
									<td class="row-odd"><span class="Mandatory">*</span>Subject</td>
									<td class="row-odd"><span class="Mandatory">*</span>Subject
									Type</td>
									<td class="row-odd"><span class="Mandatory">*</span>Batch</td>
									<td class="row-odd"><span class="Mandatory">*</span>Day</td>
									<td class="row-odd"><span class="Mandatory">*</span>Period</td>

									<td class="row-odd">Room Code</td>
								</tr>
								<nested:iterate property="viewTo" indexId="count">
									<tr>
										<td height="25" class="row-even">
										<div align="center"><c:out value="${count+1}"></c:out></div>
										</td>
										<td width="88" class="row-even"><nested:select
											property="teachingStaffId" styleId="teachingStaffId">
											<html:option value="">Select</html:option>
											<nested:notEmpty property="teachingStaffList">
												<nested:optionsCollection property="teachingStaffList"
													value="key" label="value" />
											</nested:notEmpty>

										</nested:select></td>
										<td width="90" class="row-even"><nested:select
											property="subjectId"
											onchange="getBatches(),getSubjectsType(this.value)"
											styleId="subjectId">
											<html:option value="">Select</html:option>
											<nested:notEmpty property="subjectMap">
												<nested:optionsCollection property="subjectMap"
													label="value" value="key" />
											</nested:notEmpty>


										</nested:select></td>
										<td width="73" class="row-even"><nested:select
											property="subjectType" styleId="subjectType">
											<option value="">Select</option>
											<c:set var="evaluatorTypeMap"
												value="${baseActionForm.collectionMap['optionMap']}" />
											<c:if test="${evaluatorTypeMap != null}">
												<html:optionsCollection name="evaluatorTypeMap"
													label="value" value="key" />
											</c:if>
											<nested:notEmpty property="subjectTypeMap">
												<nested:optionsCollection property="subjectTypeMap"
													label="value" value="key" />
											</nested:notEmpty>
										</nested:select></td>

										<td width="73" class="row-even"><nested:select
											property="batchValues" styleId="batchValues" multiple="2"
											size="3" style="width: 90px;">
											<nested:notEmpty property="batchValues">
												<nested:optionsCollection property="batchValues"
													label="value" value="key" />
											</nested:notEmpty>

										</nested:select></td>
										<td width="90" class="row-even"><nested:select
											property="day" style="width: 90px;">

											<option value="">Select</option>
											<nested:notEmpty property="dayMap">
												<nested:optionsCollection property="dayMap" label="value"
													value="key" />
											</nested:notEmpty>
										</nested:select></td>
										<td width="68" class="row-even"><nested:select
											property="periods" styleId="periods" style="width: 90px;"
											multiple="yes" size="3">
											<nested:notEmpty property="periodsList">
												<nested:optionsCollection property="periodsList"
													label="value" value="key" />
											</nested:notEmpty>

										</nested:select></td>
										<td width="90" class="row-even"><nested:select
											property="roomCodeId" style="width: 90px;" styleId="rooms">
											<html:option value="">Select</html:option>
											<nested:notEmpty property="roomCodeList">
												<nested:optionsCollection property="roomCodeList"
													label="value" value="key" />
											</nested:notEmpty>

										</nested:select></td>

									</tr>
								</nested:iterate>
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
					<td valign="top" background="images/Tright_3_3.gif"></td>
				</tr>

				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="heading">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="49%" align="right"><input name="Submit2"
								type="submit" class="formbutton" value="Submit" /></td>
							<td width="2%" height="35" align="center">&nbsp;</td>
							<td width="49%" height="35" align="left"><input
								name="Submit2" type="button" class="formbutton" value="Reset" /></td>
						</tr>
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
									<td width="38" height="25" class="row-odd">
									<div align="center">Sl.No</div>
									</td>
									<td class="row-odd">Academic Year</td>
									<td class="row-odd">Class</td>
									<td class="row-odd">Day</td>
									<td width="56" class="row-odd">Delete</td>
								</tr>
								<nested:iterate property="bottomGrid" indexId="count">
									<c:choose>
										<c:when test="${count%2==0}">
											<tr class="row-even">
										</c:when>
										<c:otherwise>
											<tr class="row-odd">
										</c:otherwise>
									</c:choose>

									<td height="25">
									<div align="center"><c:out value="${count+1}"></c:out></div>
									</td>
									<td width="87"><nested:write property="academicYear" /></td>
									<td width="77"><nested:write property="className" /></td>
									<td width="79"><nested:write property="dayName" /></td>
									<td>
									<div align="center"><img src="images/delete_icon.gif"
										alt="CMS" width="16" height="16"
										onclick="deleteDetails('<nested:write property="id"/>')"></div>
									</td>
									</tr>
								</nested:iterate>
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
					<td valign="top" background="images/Tright_3_3.gif"></td>
				</tr>

				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="heading">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
					</table>
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
</body>
</html>

