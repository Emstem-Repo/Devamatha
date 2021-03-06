<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@page import="java.util.Map,java.util.HashMap"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>


<link href="../css/styles.css" rel="stylesheet" type="text/css">



<html:form action="/ExamInternalMarksSupplementary">
	<html:hidden property="formName"
		value="ExamInternalMarksSupplementaryForm" />
	<html:hidden property="pageType" value="1" />
	<html:hidden property="method" styleId="method" value="add" />
	<html:hidden property="studentId"/>
	
	<table width="100%" border="0">
		<tr>
			<td><span class="heading"><a href="main.html"
				class="Bredcrumbs">Exams</a> <span class="Bredcrumbs">&gt;&gt;
			Internal Mark - Supplementary &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> Internal Mark - Supplementary</strong></td>

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

									<td width="31%" height="30" class="row-odd">
									<div align="right">Exam Name :</div>
									</td>
									<td height="30" class="row-even" colspan="4"><bean:write
										property="examName" name="ExamInternalMarksSupplementaryForm" />
									</td>
									
								</tr>
								<tr>
									<td height="26" class="row-odd">
									<div align="right">Course :</div>
									</td>
									<td width="26%" class="row-even"><bean:write
										property="courseName"
										name="ExamInternalMarksSupplementaryForm" /></td>

									<td width="18%" height="26" class="row-odd">
									<div align="right">Scheme :</div>
									</td>
									<td width="25%" class="row-even"><bean:write
										property="schemeId" name="ExamInternalMarksSupplementaryForm" /></td>

								</tr>
								<tr>
									<td class="row-odd">
									<div align="right">Register No.:</div>
									</td>
									<td class="row-even"><bean:write property="regNo"
										name="ExamInternalMarksSupplementaryForm" /></td>
									<td class="row-odd">
									<div align="right">Roll No.:</div>
									</td>

									<td class="row-even"><bean:write property="rollNo"
										name="ExamInternalMarksSupplementaryForm" /></td>
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
					<td height="10" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news"></td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
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
							<td valign="top">
							<table width="100%" cellspacing="1" cellpadding="0">
								<tr height="35">
									<td width="62"  class="row-odd">
									<div align="center">Sl.No</div>
									</td>
									<td class="row-odd">Subject Code</td>
									<td class="row-odd">Subject Name</td>
									<td class="row-odd">Theory Internal Marks</td>
									<td class="row-odd">Practical Internal Marks</td>
								</tr>
				
								<nested:iterate property="subjects" indexId="count">
									<c:choose>
										<c:when test="${count%2==0}">
											<tr class="row-even">
										</c:when>
										<c:otherwise>
											<tr class="row-white">
										</c:otherwise>
									</c:choose>
									<%
										boolean flag = true;
									%>
									<td width="62">
									<div align="center"><c:out value="${count+1}"></c:out></div>
									</td>
									<td width="122" ><nested:write
										property="subjectCode" /></td>
									<td width="122" ><nested:write
										property="subjectName" /></td>

									<nested:equal value="T" property="isTheoryPrac">
										<%
											flag = true;
										%>
									</nested:equal>
									<nested:equal value="B" property="isTheoryPrac">
										<%
											flag = true;
										%>
									</nested:equal>
									
									<td width="122" class="row-even"><nested:text
										property="theoryTotalSubInternalMarks" maxlength="3" onkeypress="return isNumberKey(event)"/></td>
									<%
											flag = true;
										%>
									<nested:equal value="B" property="isTheoryPrac">
										<%
											flag = false;
										%>
									</nested:equal>
									<nested:equal value="P" property="isTheoryPrac">
										<%
											flag = false;
										%>
									</nested:equal>
									<td width="122" ><nested:text
										property="practicalTotalSubInternalMarks"
										disabled="<%=flag%>" maxlength="3" onkeypress="return isNumberKey(event)"/></td>
									<nested:hidden property="id"/>
									<nested:hidden property="subjectId"/>
								</nested:iterate>

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
					<td valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr><td style="height: 10px" align="center" colspan="4"></td></tr>
						<tr>
							<td width="47%" height="35">
							<div align="right"><input name="submit" type="submit"
								class="formbutton" value="Submit" /></div>
							</td>

							<td width="1%"></td>

							<td width="1%"></td>
							<td width="46%"><input type="reset" class="formbutton"
								value="Reset" /></td>
						</tr>						
						<tr><td style="height: 10px" align="center" colspan="4"></td></tr>						
					</table>
					</td>
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