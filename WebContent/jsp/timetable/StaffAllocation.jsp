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
function resetValues() {
	document.location.href = "staffAllocation.do?method=initStaffAllocation";
}
function getClasses(academicYear)
{
	getClassesByYear("className",academicYear,"className",updateClasses);
}

function updateClasses(req) {
	var responseObj = req.responseXML.documentElement;
	var destination = document.getElementById("className");
	for (x1=destination.options.length-1; x1>=0; x1--) {
		destination.options[x1]=null;
	}
	var items1 = responseObj.getElementsByTagName("option");
	destination.options[0]=new Option("Select","");
	for (var j = 1 ; j < items1.length ; j++) {
		 label = items1[j].getElementsByTagName("optionlabel")[0].firstChild.nodeValue;
	     value = items1[j].getElementsByTagName("optionvalue")[0].firstChild.nodeValue;
	     destination.options[j] = new Option(label,value);
	 }
}
function getSubjects(classSchemewiseId) {
	getSubjectsByClass("subjectMap", classSchemewiseId, "allSubjectPreference",
			updateSubjects);
}
function updateSubjects(req) {
	updateOptionsFromMapForNonDefaultSelection(req, "allSubjectPreference", "");
}
function getSubjectList()
{
	var object=document.getElementById("selectedSubjectPreferences");
	selected=new Array();
	for ( var i = 0; i < object.options.length; i++) {
		selected.push(object.options[i].value);
	}
	document.getElementById("classValues").value=selected;
}
function deleteDetails(id) {
	if(confirm("Are You Sure To Delete This Entry?"))
	document.location.href = "staffAllocation.do?method=delete&id=" + id;
}
</SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="js/selectbox.js"></SCRIPT>
<link href="css/styles.css" rel="stylesheet" type="text/css">

</head>

<body>
<html:form action="/staffAllocation.do">
	<html:hidden property="pageType" styleId="pageType" value="1" />
	<html:hidden property="formName" value="StaffAllocationForm" />
	<html:hidden property="method" styleId="method" value="add" />
	<html:hidden property="classValues" styleId="classValues" />

	<table width="99%" border="0">

		<tr>
			<td><span class="heading"><span class="Bredcrumbs">&gt;&gt;
			Staff Allocation &gt;&gt;</span></span></td>

		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="heading_white">Staff
					Allocation</td>
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
							<td width="5" background="images/left.gif"></td>
							<td valign="top">
							<table width="100%" cellspacing="1" cellpadding="2">
								<tr>
									<td width="17%" height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>Teaching Staff :</div>
									</td>

									<td width="20%" class="row-even"><html:select
										property="teachingStaff" styleClass="combo"
										styleId="teachingStaff" name="StaffAllocationForm"
										style="width:200px">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<logic:notEmpty property="teachingStaffList"
											name="StaffAllocationForm">
											<html:optionsCollection property="teachingStaffList"
												value="id" label="display" />
										</logic:notEmpty>
									</html:select></td>
									<td width="20%" class="row-odd">&nbsp;</td>

									<td width="20%" class="row-even">&nbsp;</td>
								</tr>
								<tr>
									<td height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>Academic Year :</div>
									</td>
									<td class="row-even"><html:select property="academicYr"
										onchange="getClasses(this.value)">
										<cms:renderYear />
									</html:select></td>
									<td class="row-odd">
									<div align="right"><span class="Mandatory">*</span>Class :</div>
									</td>

									<td class="row-even"><html:select property="className"
										styleId="className" onchange="getSubjects(this.value)">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<logic:notEmpty property="listOfClasses" name="StaffAllocationForm">
										<html:optionsCollection property="listOfClasses" label="value" value="key"/>
										 </logic:notEmpty>
									</html:select></td>
								</tr>
								<tr>
									<td height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>Subject Preference :</div>
									</td>
									<td width="78" valign="top" class="row-even"><html:select
										property="allSubjectPreference" styleId="allSubjectPreference"
										style="width: 200px"
										ondblclick="moveSelectedOptions(this.form['allSubjectPreference'],this.form['selectedSubjectPreferences'],false)"
										multiple="2" size="5">
										<c:if
											test="${StaffAllocationForm.className != null && StaffAllocationForm.className != ''}">
											<c:set var="subjectMap"
												value="${baseActionForm.collectionMap['subjectMap']}" />
											<c:if test="${subjectMap != null}">
												<html:optionsCollection name="subjectMap" label="value"
													value="key" />
											</c:if>
										</c:if>
									</html:select></td>
									<td width="207" align=CENTER valign=MIDDLE class="row-odd"><a
										href="#" class="button"
										onClick="moveSelectedOptions(document.forms[0]['allSubjectPreference'],document.forms[0]['selectedSubjectPreferences'],false);return false;">&gt;&gt;</a><br>
									<br>

									<br>
									<a href="#" class="button"
										onClick="moveSelectedOptions(document.forms[0]['selectedSubjectPreferences'],document.forms[0]['allSubjectPreference'],false); return false;">&lt;&lt;</a><br>
									<br>
									</td>
									<td width="59" valign="top" class="row-even"><html:select
										property="selectedSubjectPreferences"
										styleId="selectedSubjectPreferences" style="width: 200px"
										ondblclick="moveSelectedOptions(this.form['selectedSubjectPreferences'],this.form['allSubjectPreference'],false)"
										multiple="2" size="5"></html:select></td>

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
					<td class="heading">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="49%" align="right"><input name="Submit2"
								type="submit" class="formbutton" value="Submit"
								onclick="getSubjectList()" /></td>

							<td width="2%" height="35" align="center">&nbsp;</td>
							<td width="49%" height="35" align="left"><input
								name="Submit2" type="reset" class="formbutton" value="Reset" onclick="resetValues()"/></td>
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

									<td width="32" height="25" class="row-odd">
									<div align="center">Sl.No</div>
									</td>
									<td class="row-odd">Teaching Staff</td>
									<td class="row-odd">Academic Year</td>
									<td class="row-odd">Class</td>
									<td class="row-odd">Subject Preference</td>
									<td width="43" class="row-odd">Delete</td>
								</tr>
								<nested:iterate property="bottomGrid" indexId="count">
									<c:choose>
										<c:when test="${count%2 == 0}">
											<tr class="row-even">
										</c:when>
										<c:otherwise>
											<tr class="row-white">
										</c:otherwise>
									</c:choose>
									<td height="25">
									<div align="center"><c:out value="${count+1}"></c:out></div>
									</td>
									<td width="90"><nested:write property="teachingStaff" /></td>
									<td width="84"><nested:write property="academicYear" /></td>
									<td width="87"><nested:write property="className" /></td>
									<td width="79"><nested:write property="subjectPreference" />
									</td>
									<td>
									<div align="center"><img src="images/delete_icon.gif"
										alt="CMS" width="16" height="16" onclick="deleteDetails('<nested:write property="id"/>')"></div>
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
</body>
</html>

