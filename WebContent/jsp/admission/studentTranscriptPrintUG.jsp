<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic" %>
<%@page import="com.kp.cms.constants.CMSConstants"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" type="text/css" href="css/styles.css"/>
<link rel="stylesheet" href="css/calendar.css">

<head>
<link href="css/styles.css" rel="stylesheet" type="text/css">
<title>Student Transcript Print</title>
<style type="text/css">
	.heading1
	{
		font-family: Rockwell;
		font-size: 10pt;
	}
	.heading2
	{
		font-family: Lucida Sans;
		font-size: 10pt;
	}
	.heading3
	{
		font-weight: bold;
		font-family: Rockwell;
		font-size: 12pt;
	}
	
	
</style>

</head>
<html:form action="/studentTranscriptPrint" >
	<html:hidden property="method" styleId="method" value="printTranscript" />	
	<html:hidden property="pageType" value="2" />
	<html:hidden property="formName" value="studentTranscriptPrintForm" />
	
	<logic:notEmpty name="studentTranscriptPrintForm" property="semesterList">
				
		<logic:iterate id="MainMap" name="studentTranscriptPrintForm" property="semesterList">
			
				
				
				<c:if test="${count>0}">
		<p style="page-break-after:always;"> </p>
					<p style="page-break-after:always;"> </p>
		</c:if>
				<table width="100%" cellspacing="1" cellpadding="2" class="row-white">
							
								<tr>
									<td><br>
										&nbsp;<br>
									</td>
								</tr>
								<tr>
									<td><br>
										&nbsp;<br>
									</td>
								</tr>
								<tr>
									<td><br>
										&nbsp;<br>
									</td>
								</tr>
								
								<tr>
									<td align="left" valign="top">
										No.
											&nbsp;
										<bean:write name="studentTranscriptPrintForm" property="transcriptNo"/>
									</td>
								</tr>
								<tr>
									<td align="left" valign="top">
										<bean:write name="studentTranscriptPrintForm" property="transcriptDate"/>
									</td>
								</tr>
							<tr height="21px">
								<td align="center" class="heading3"> <u>TRANSCRIPT</u> </td>
								
							</tr>
							<tr>
							
								<td colspan="2">
									<table width="100%">
										<logic:equal property="key" value="1" name="MainMap">
										<tr>
											<td class="score">
											This is to certify that <B><bean:write name="studentTranscriptPrintForm" property="studentName"/></B> was a student 
											of this Institution enrolled in the</td>
										</tr>
										<tr>
											<td class="score"><B><bean:write name="studentTranscriptPrintForm" property="studentCourse"/></B> Under Graduation Course</td>
										</tr>
										<tr>
											<td class="score">during the academic years <b><bean:write name="studentTranscriptPrintForm" property="studentAcademicYearFrom"/> - <bean:write name="studentTranscriptPrintForm" property="studentAcademicYearTo"/></b> having Registration No. <B><bean:write name="studentTranscriptPrintForm" property="regNo"/></B>.</td>
										</tr>
										<tr>
											<td class="score">The following are the marks secured in each Semester at the Examinations:</td>
										</tr>
										<tr>
									<c:set var="monthyearcount" scope="session" value="${0}"/>
									<logic:iterate id="mto" name="MainMap" property="value" type="com.kp.cms.to.admission.StudentTranscriptPrintTO">
										<c:if test="${monthyearcount == '0' }">
										<tr>
											<td class="score" align="left"><B><bean:write name="MainMap" property="key"/>
											 <bean:write name="mto" property="schemeName"/></B></td>
											
											<td class="score" align="right">
											<bean:write name="mto" property="monthYear"/></td>
										</tr>
										<c:set var="monthyearcount" scope="session" value="${1}"/>
										</c:if>
									</logic:iterate>
								</tr>
								</logic:equal>
								<logic:notEqual property="key" value="1" name="MainMap">
								<tr>
									<td class="score" align="left">Name: <B><bean:write name="studentTranscriptPrintForm" property="studentName"/></B></td>
									<td class="score" align="right">Reg. No.: <B><bean:write name="studentTranscriptPrintForm" property="regNo"/></B></td>
								</tr>
									<c:set var="monthyearcount" scope="session" value="${0}"/>
									<logic:iterate id="mto" name="MainMap" property="value" type="com.kp.cms.to.admission.StudentTranscriptPrintTO">
										<c:if test="${monthyearcount == '0' }">
										<tr>
											<td class="score" align="left"><B><bean:write name="MainMap" property="key"/>
											 <bean:write name="mto" property="schemeName"/></B></td>
											
											<td class="score" align="right">
											<bean:write name="mto" property="monthYear"/></td>
										</tr>
										<c:set var="monthyearcount" scope="session" value="${1}"/>
										</c:if>
									</logic:iterate>
								</logic:notEqual>
							</table>
						</td>
					</tr>
							
							
							<tr>
								<td colspan="2">
									<table width="100%" style="border: 1px solid black; " rules="all">
										<tr height="21px">

						                        <td rowspan="2" class="score" align="center" width="3%">
						                                    SI NO
						                        </td>
						                        <td rowspan="2" class="score" align="center" width="30%">
						                                    SUBJECTS
						                        </td>
						                        <td rowspan="2" class="score" align="center" width="10%">
						                                    TYPE
						                        </td>
						                        <td colspan="2" align="center" class="row-print" width="15%">
						                                    CIA
						                        </td>
						                        <td colspan="2" align="center" class="row-print" width="15%">
						                                    ATT
						                        </td>
						                        <td colspan="3" align="center" class="row-print" width="17%">
						                                    ESE
						                        </td>
						                        <td  colspan="2" align="center" class="row-print" width="15%">
						                                    TOTAL
						                        </td>
						                        <td rowspan="2" align="center" class="score" width="6%">
						                                    CREDITS
						                        </td>
						                        <td rowspan="2" align="center" class="score" width="4%">
						                                    GRADE
						                        </td>
						            </tr>
						            <tr height="21px">
						                        <td align="center" class="score">
						                                    MAX MARKS
						                        </td>
						                        <td align="center" class="score">
						                                    MARKS AWARDED
						                        </td>
						                        <td align="center" class="score">
						                                    MAX MARKS
						                        </td>
						                        <td align="center" class="score">
						                                    MARKS AWARDED
						                        </td>
						                        <td align="center" class="score">
						                                    MAX MARKS
						                        </td>
						                        <td align="center" class="score">
						                                    MIN MARKS
						                        </td>
						                        <td align="center" class="score">
						                                    MARKS AWARDED
						                        </td>
						                        <td align="center" class="score">
						                                    MAX MARKS
						                        </td>
						                        <td align="center" class="score">
						                                    MARKS AWARDED
						                        </td>               
						            </tr>
						            
						            <c:set var="slnocount" scope="session" value="${0}"/>
						            <c:set var="slnocount1" scope="session" value="${0}"/>
						            <c:set var="slnocount2" scope="session" value="${0}"/>
						            <logic:iterate id="mto" name="MainMap" property="value">
						            
									<tr height="21px">
										<logic:notEqual name="mto" property="section" value="Add On Course">
											<logic:notEqual name="mto" property="section" value="  Part-1">
												<logic:notEqual name="mto" property="section" value="  Part-2">
												
													<td align="center" class="score">
													<logic:notEqual name="mto" property="subjectOrder" value="0" >
														<bean:write name="mto" property="subjectOrder"/>
													</logic:notEqual></td>
													
												</logic:notEqual>
											</logic:notEqual>
										</logic:notEqual>
										<logic:equal name="mto" property="section" value="  Part-1">
											<c:if test="${slnocount == '0' }">
												<td></td>
												<td colspan="13" class="row-print">Part - I</td>
												<c:set var="slnocount" scope="session" value="${1}"/>
												<tr></tr>
											</c:if>
											<td align="center" class="score">
											<logic:notEqual name="mto" property="subjectOrder" value="0" >
												<bean:write name="mto" property="subjectOrder"/>
											</logic:notEqual></td>
										</logic:equal>
										<logic:equal name="mto" property="section" value="  Part-2">
											<c:if test="${slnocount1 == '0' }">
												<td></td>
												<td colspan="13" class="row-print">Part - II</td>
												<c:set var="slnocount1" scope="session" value="${1}"/>
												<tr></tr>
											</c:if>
											<td align="center" class="score">
											<logic:notEqual name="mto" property="subjectOrder" value="0" >
											<bean:write name="mto" property="subjectOrder"/>
											</logic:notEqual>	</td>										
										</logic:equal>
										<logic:equal name="mto" property="section" value="Add On Course">
											<c:if test="${slnocount2 == '0' }">
												<td></td>
												<td colspan="13" class="row-print">Add On Course</td>
												<c:set var="slnocount2" scope="session" value="${1}"/>
												<tr></tr>
											</c:if>
											<td align="center" class="score">
											<logic:notEqual name="mto" property="subjectOrder" value="0" >
											<bean:write name="mto" property="subjectOrder"/>
											</logic:notEqual></td>								
										</logic:equal>
									
										<td class="score">
										<table width="100%" cellpadding="0" cellspacing="0">
										<tr>
											<td align="left" class="score">
											<bean:write name="mto" property="subjectName"/></td>
										</tr>
										</table></td>
										<td align="center" class="row-print">
											<logic:equal name="mto" property="dontShowSubType" value="false"><bean:write name="mto" property="subType"/></logic:equal>
											<logic:equal name="mto" property="dontShowSubType" value="true"></logic:equal></td>
										<td align="center" class="score">
											<logic:equal name="mto" property="subType" value="Theory"><logic:equal name="mto" property="showOnlyGrade" value="false"><logic:notEmpty name="mto" property="theCiaMaxMarks"><bean:write name="mto" property="theCiaMaxMarks"/></logic:notEmpty><logic:empty name="mto" property="theCiaMaxMarks">-</logic:empty></logic:equal><logic:equal name="mto" property="showOnlyGrade" value="true">-</logic:equal></logic:equal>
											<logic:equal name="mto" property="subType" value="Practical"><logic:equal name="mto" property="showOnlyGrade" value="false"><logic:notEmpty name="mto" property="praCiaMaxMarks"><bean:write name="mto" property="praCiaMaxMarks"/></logic:notEmpty><logic:empty name="mto" property="praCiaMaxMarks">-</logic:empty></logic:equal><logic:equal name="mto" property="showOnlyGrade" value="true">-</logic:equal></logic:equal>
										</td>
										<td align="center" class="score">
											<logic:equal name="mto" property="subType" value="Theory"><logic:equal name="mto" property="showOnlyGrade" value="false"><logic:notEmpty name="mto" property="theCiaMarksAwarded"><bean:write name="mto" property="theCiaMarksAwarded"/></logic:notEmpty><logic:empty name="mto" property="theCiaMarksAwarded">-</logic:empty></logic:equal><logic:equal name="mto" property="showOnlyGrade" value="true">-</logic:equal></logic:equal>
											<logic:equal name="mto" property="subType" value="Practical"><logic:equal name="mto" property="showOnlyGrade" value="false"><logic:notEmpty name="mto" property="praCiaMarksAwarded"><bean:write name="mto" property="praCiaMarksAwarded"/></logic:notEmpty><logic:empty name="mto" property="praCiaMarksAwarded">-</logic:empty></logic:equal><logic:equal name="mto" property="showOnlyGrade" value="true">-</logic:equal></logic:equal>
										</td>
										<td align="center" class="score">
											<logic:notEmpty name="mto" property="attMaxMarks"><logic:equal name="mto" property="showOnlyGrade" value="false"><bean:write name="mto" property="attMaxMarks"/></logic:equal><logic:equal name="mto" property="showOnlyGrade" value="true">-</logic:equal></logic:notEmpty><logic:empty name="mto" property="attMaxMarks">-</logic:empty>
											
										</td>
										<td align="center" class="score">
											<logic:equal name="mto" property="subType" value="Theory"><logic:equal name="mto" property="showOnlyGrade" value="false"><logic:notEmpty name="mto" property="theAttMarksAwarded"><bean:write name="mto" property="theAttMarksAwarded"/></logic:notEmpty><logic:empty name="mto" property="theAttMarksAwarded">-</logic:empty></logic:equal><logic:equal name="mto" property="showOnlyGrade" value="true">-</logic:equal></logic:equal>
											<logic:equal name="mto" property="subType" value="Practical"><logic:equal name="mto" property="showOnlyGrade" value="false"><logic:notEmpty name="mto" property="praAttMarksAwarded"><bean:write name="mto" property="praAttMarksAwarded"/></logic:notEmpty><logic:empty name="mto" property="praAttMarksAwarded">-</logic:empty></logic:equal><logic:equal name="mto" property="showOnlyGrade" value="true">-</logic:equal></logic:equal>
										</td>
										<td align="center" class="score">
											<logic:equal name="mto" property="subType" value="Theory"><logic:equal name="mto" property="showOnlyGrade" value="false"><logic:notEmpty name="mto" property="theEndSemMaxMarks"><bean:write name="mto" property="theEndSemMaxMarks"/></logic:notEmpty><logic:empty name="mto" property="theEndSemMaxMarks">-</logic:empty></logic:equal><logic:equal name="mto" property="showOnlyGrade" value="true">-</logic:equal></logic:equal>
											<logic:equal name="mto" property="subType" value="Practical"><logic:equal name="mto" property="showOnlyGrade" value="false"><logic:notEmpty name="mto" property="praEndSemMaxMarks"><bean:write name="mto" property="praEndSemMaxMarks"/></logic:notEmpty><logic:empty name="mto" property="praEndSemMaxMarks">-</logic:empty></logic:equal><logic:equal name="mto" property="showOnlyGrade" value="true">-</logic:equal></logic:equal>
										</td>
										<td align="center" class="score">
											<logic:equal name="mto" property="subType" value="Theory"><logic:equal name="mto" property="showOnlyGrade" value="false"><logic:notEmpty name="mto" property="theEndSemMinMarks"><bean:write name="mto" property="theEndSemMinMarks"/></logic:notEmpty><logic:empty name="mto" property="theEndSemMinMarks">-</logic:empty></logic:equal><logic:equal name="mto" property="showOnlyGrade" value="true">-</logic:equal></logic:equal>
											<logic:equal name="mto" property="subType" value="Practical"><logic:equal name="mto" property="showOnlyGrade" value="false"><logic:notEmpty name="mto" property="praEndSemMinMarks"><bean:write name="mto" property="praEndSemMinMarks"/></logic:notEmpty><logic:empty name="mto" property="praEndSemMinMarks">-</logic:empty></logic:equal><logic:equal name="mto" property="showOnlyGrade" value="true">-</logic:equal></logic:equal>
										</td>
										<td align="center" class="score">
											<logic:equal name="mto" property="subType" value="Theory"><logic:equal name="mto" property="showOnlyGrade" value="false"><logic:notEmpty name="mto" property="theEndSemMarksAwarded"><bean:write name="mto" property="theEndSemMarksAwarded"/></logic:notEmpty><logic:empty name="mto" property="theEndSemMarksAwarded">-</logic:empty></logic:equal><logic:equal name="mto" property="showOnlyGrade" value="true">-</logic:equal></logic:equal>
											<logic:equal name="mto" property="subType" value="Practical"><logic:equal name="mto" property="showOnlyGrade" value="false"><logic:notEmpty name="mto" property="praEndSemMarksAwarded"><bean:write name="mto" property="praEndSemMarksAwarded"/></logic:notEmpty><logic:empty name="mto" property="praEndSemMarksAwarded">-</logic:empty></logic:equal><logic:equal name="mto" property="showOnlyGrade" value="true">-</logic:equal></logic:equal>
										</td>
										<td align="center" class="score">
											<logic:equal name="mto" property="subType" value="Theory"><logic:equal name="mto" property="showOnlyGrade" value="false"><logic:notEmpty name="mto" property="theTotMaxMarks"><b><bean:write name="mto" property="theTotMaxMarks"/></b></logic:notEmpty><logic:empty name="mto" property="theTotMaxMarks">-</logic:empty></logic:equal><logic:equal name="mto" property="showOnlyGrade" value="true">-</logic:equal></logic:equal>
											<logic:equal name="mto" property="subType" value="Practical"><logic:equal name="mto" property="showOnlyGrade" value="false"><logic:notEmpty name="mto" property="praTotMaxMarks"><b><bean:write name="mto" property="praTotMaxMarks"/></b></logic:notEmpty><logic:empty name="mto" property="praTotMaxMarks">-</logic:empty></logic:equal><logic:equal name="mto" property="showOnlyGrade" value="true">-</logic:equal></logic:equal>
										</td>
										<td align="center" class="score">
											<logic:equal name="mto" property="subType" value="Theory"><logic:equal name="mto" property="showOnlyGrade" value="false"><logic:notEmpty name="mto" property="theTotMarksAwardedDisplay"><b><bean:write name="mto" property="theTotMarksAwardedDisplay"/></b><logic:equal name="mto" property="showSuppl" value="true">*</logic:equal></logic:notEmpty><logic:empty name="mto" property="theTotMarksAwardedDisplay">-</logic:empty></logic:equal><logic:equal name="mto" property="showOnlyGrade" value="true">-</logic:equal></logic:equal>
											<logic:equal name="mto" property="subType" value="Practical"><logic:equal name="mto" property="showOnlyGrade" value="false"><logic:notEmpty name="mto" property="praTotMarksAwardedDisplay"><b><bean:write name="mto" property="praTotMarksAwardedDisplay"/></b><logic:equal name="mto" property="showSuppl" value="true">*</logic:equal></logic:notEmpty><logic:empty name="mto" property="praTotMarksAwardedDisplay">-</logic:empty></logic:equal><logic:equal name="mto" property="showOnlyGrade" value="true">-</logic:equal></logic:equal>
										</td>
										<td align="center" class="score">
											<logic:equal name="mto" property="subType" value="Theory"><logic:notEmpty name="mto" property="theCredit"><bean:write name="mto" property="theCredit"/></logic:notEmpty><logic:empty name="mto" property="theCredit">-</logic:empty></logic:equal>
											<logic:equal name="mto" property="subType" value="Practical"><logic:notEmpty name="mto" property="praCredit"><bean:write name="mto" property="praCredit"/></logic:notEmpty><logic:empty name="mto" property="praCredit">-</logic:empty></logic:equal>
										</td>
										<td align="center" class="score">
											<logic:notEmpty name="mto" property="grade"><bean:write name="mto" property="grade"/></logic:notEmpty><logic:empty name="mto" property="grade">-</logic:empty>
										</td>
												
											
									</tr>
									</logic:iterate>		
										
										<tr height="21px">
											<td align="center" class="score"></td>
											<td align="left" class="score">
											<b>Total Marks :</b></td>
											<td align="center" class="score"></td>
											<td align="center" class="score"></td>
											<td align="center" class="score"></td>
											<td align="center" class="score"></td>
											<td align="center" class="score"></td>
											<td align="center" class="score"></td>
											<td align="center" class="score"></td>
											<td align="center" class="score"></td>
											<td align="center" class="score">
											<b><bean:write name="mto" property="totalMaxMarks"/></b></td>
											<td align="center" class="score">
											<b><bean:write name="mto" property="totalMaxMarksAwarded"/></b></td>
											<td align="center" class="score"></td>
											<td align="center" class="score"></td>
										</tr>
										<tr height="40">
										<td colspan="14">
											<table width="94%">
											<tr>
											<td colspan="6" align="left" width="60%" class="score">Total (In Words) :&nbsp;<b><bean:write name="mto" property="totalMarksInWords"/> only</b></td>
											<td colspan="6" align="right" width="50%" class="score"><b><bean:write name="mto" property="percentage"/>%</b></td>
											</tr>
											<tr>
											<td colspan="5" align="left" width="35%" class="score">Result :&nbsp;<b><bean:write name="mto" property="result"/></b></td>
											<td colspan="5" align="left" width="30%" class="score">Total Credits Awarded :&nbsp;<b><bean:write name="mto" property="totalCreditsAwarded"/></b></td>
											<td colspan="5" align="left" width="30%" class="score">Grade Point Average :&nbsp;<b><bean:write name="mto" property="totalGradePoint"/></b></td>
											</tr>
											<tr>
												<td>
													&nbsp;
												</td>
											</tr>
											
											</table>
										</td></tr>
								
									</table>
								</td>
							</tr>
							
							<tr height="21px">
								<td colspan="2"  class="score">
									<logic:notEmpty name="studentTranscriptPrintForm" property="description">
										<c:out value="${studentTranscriptPrintForm.description}" escapeXml="false"></c:out>
									</logic:notEmpty>
								</td>
							</tr>
							<tr>
							
							<logic:equal name="mto" property="showSupplDisplay" value="true">
								<tr>
									<td colspan="2" class="score">
									* Denotes more than one attempt
									</td>
								</tr>
							</logic:equal>
							</tr>
							<tr>
								<td>
									&nbsp;<br>
								</td>
							</tr>
							<tr>
								<td>
									&nbsp;<br>
								</td>
							</tr>
							<tr>
								<td>
									&nbsp;<br>
								</td>
							</tr>
							<tr>
								<td>
									&nbsp;<br>
								</td>
							</tr>
							<tr>
							<td colspan="2" align="left">
								<b>Pro Vice Chancellor</b>
							</td>
							</tr>
							</table>
							
							</logic:iterate>
				
				
			</logic:notEmpty>
			<logic:empty name="studentTranscriptPrintForm" property="semesterList">
				<table width="100%" height="435px" align="center">
				<tr>
					<td align="center" valign="middle">
						No Records Found
					</td>
				</tr>
				</table>
			</logic:empty>

<script type="text/javascript">
	window.print();
</script>
				
</html:form>