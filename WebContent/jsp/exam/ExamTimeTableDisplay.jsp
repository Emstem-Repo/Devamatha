<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@page import="java.util.Map,java.util.HashMap"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<head>
<script>
function editExamDetails(id,year,joinYear) {
	document.location.href = "ExamTimeTable.do?method=edit&id=" + id+"&year="+year +"&joiningBatch="+joinYear;
}
	function closeScreen(){
		document.location.href = "ExamTimeTable.do?method=initExamTimeTable";
	}
</script>
</head>
<html:form action="/ExamTimeTable.do" method="POST"
	enctype="multipart/form-data">
	<html:hidden property="formName" value="ExamTimeTableForm"
		styleId="formName" />
	<html:hidden property="pageType" value="2" styleId="pageType" />
	<html:hidden property="examNameId" />
	<html:hidden property="examTypeId" />
	<table width="99%" border="0">

		<tr>
			<td><span class="Bredcrumbs"><bean:message
				key="knowledgepro.exam" /> <span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.exam.ExamTimeTable" /> &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="heading_white">Exam
					Time Table</td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="heading">&nbsp;</td>
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
									<td width="50%" height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span> <bean:message
										key="knowledgepro.exam.ExamName" /> <span class="star"></span></div>
									</td>
									<td width="50%" height="25" class="row-even"><bean:write
										property="examName" name="ExamTimeTableForm"></bean:write></td>
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
					<td class="heading">&nbsp;</td>
					<td valign="top" background="images/Tright_3_3.gif"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="heading">
					<table width="100%" border="0" align="center" cellpadding="0"
						cellspacing="0">
						<tr>
							<td><img src="images/01.gif" width="5" height="5"></td>
							<td width="914" background="images/02.gif"></td>
							<td><img src="images/03.gif" width="5" height="5"></td>
						</tr>
						<tr>
							<td width="5" background="images/left.gif"></td>
							<td height="54" valign="top">
							<table width="100%" cellspacing="1" cellpadding="2">
								<tr>
									<td width="53" height="25" class="row-odd">
									<div align="center">Sl.No</div>
									</td>
									<td class="row-odd">Exam Type</td>
									<td class="row-odd">Academic Year</td>
									<td class="row-odd">Program</td>
									<td class="row-odd">Course</td>
									<td class="row-odd">Scheme</td>
									<td class="row-odd">Status</td>
									<td width="67" class="row-odd">Edit</td>
								</tr>
								<logic:iterate id="details" name="ExamTimeTableForm"
									type="com.kp.cms.to.exam.ExamTimeTableTO" property="mainList"
									indexId="count">
									<c:choose>
										<c:when test="${count%2==0}">
											<tr class="row-even">
										</c:when>
										<c:otherwise>
											<tr class="row-white">
										</c:otherwise>
									</c:choose>
									<td width="53" height="25">
									<div align="center"><c:out value="${count+1}"></c:out></div>
									</td>
									<td width="136"><bean:write property="examType"
										name="details" /></td>
									<td width="161"><bean:write property="batch"
										name="details" /></td>
									<td width="113"><bean:write property="program"
										name="details" /></td>
									<td width="117"><bean:write property="course"
										name="details" /></td>
									<td width="139"><bean:write property="scheme"
										name="details" /></td>
									<td width="120"><span class="grnheading"><bean:write
										property="status" name="details" /></span></td>
									<td align="center"><img src="images/edit_icon.gif"
										height="18" style="cursor: pointer"
										onclick="editExamDetails('<bean:write name="details" property="id"/>','<bean:write name="details" property="batch"/>','<bean:write name="details" property="joiningBatch"/>')"></td>
									</tr>
								</logic:iterate>
							</table>
							</td>
							<td background="images/right.gif" width="5" height="54"></td>
						</tr>
						<tr>
							<td height="5"><img src="images/04.gif" width="5" height="5"></td>
							<td background="images/05.gif"></td>
							<td><img src="images/06.gif"></td>
						</tr>
					</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif"></td>
				</tr>
				<tr>
						<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
							<td class="heading">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr><td style="height: 10px" align="center" colspan="1"></td></tr>
								<tr>
									<td width="51%" height="45" align="center"><html:button
										property="" styleClass="formbutton"
										onclick="closeScreen()">
										<bean:message key="knowledgepro.close" />
									</html:button></td>
								</tr>								
								<tr><td style="height: 10px" align="center" colspan="1"></td></tr>								
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