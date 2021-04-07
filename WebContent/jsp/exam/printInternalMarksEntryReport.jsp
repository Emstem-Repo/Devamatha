<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<html:form action="/newCIAMarksEntry" >
	<html:hidden property="pageType" value="5" />
	<html:hidden property="formName" value="newInternalMarksEntryForm" />
	<table width="98%" border="0">
		<tr>
		  <td width="100%">
					<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
						<tr>
							<td width="100%" valign="top">
							<table width="100%" cellspacing="1" cellpadding="2" class="row-white">
								<tr>
								  <td width="100%" align="center"><font style="font-weight: bolder;" size="5">CHRIST UNIVERSITY</font></td>
								</tr>
								<tr height="2"></tr>
						        <tr><td width="100%">
						        <table width="100%">
						        <tr>
						        <logic:notEmpty name="newInternalMarksEntryForm" property="batchName">
						        <td width="15%" align="right" height="25" style="font-weight: bolder;font-size: 2">Batch</td>
						        <td width="3%" style="font-weight: bolder;font-size: 2">:</td>
						        <td width="45%" align="left" height="25" style="font-weight: bolder;font-size: 2"><bean:write
										property="batchName" name="newInternalMarksEntryForm"></bean:write>&nbsp;(<bean:write
										property="classNames" name="newInternalMarksEntryForm"></bean:write>)</td>
						        </logic:notEmpty>
						        <logic:empty name="newInternalMarksEntryForm" property="batchName">
									<td width="15%" height="25">
									<div align="left" style="font-weight: bolder;font-size: 2">Class Name </div>
									</td>
									<td width="3%" style="font-weight: bolder;font-size: 2">:</td>
									<td width="45%" align="left" height="25" style="font-weight: bolder;font-size: 2"><bean:write
										property="className" name="newInternalMarksEntryForm"></bean:write></td>
									</logic:empty>
								 <td width="10%" height="25" align="left" style="font-weight: bolder;font-size: 2">Teacher </td>
								 <td width="2%" style="font-weight: bolder;font-size: 2">:</td>
								 <td width="25%" align="left" height="25" style="font-weight: bolder;font-size: 2"> <%=session.getAttribute("username").toString().toUpperCase() %></td>	
						        </tr>
						        <tr>
						        <td width="15%" height="25" align="left" style="font-weight: bolder;font-size: 2">Score List for </td>
						        <td width="3%" style="font-weight: bolder;font-size: 4">:</td>
						        <td width="45%" align="left" height="25" style="font-weight: bolder;font-size: 2"><bean:write property="examName" name="newInternalMarksEntryForm"></bean:write></td>
								<td width="10%" height="25" align="left" style="font-weight: bolder;font-size: 2">Mobile </td>
								<td width="2%" style="font-weight: bolder;font-size: 2">:</td>
								<td width="25%" height="25" align="left" style="font-weight: bolder;font-size: 2"><bean:write property="teacherMobileNo" name="newInternalMarksEntryForm"></bean:write></td>		
						        </tr>
						        <tr>
						        <td width="15%" height="25" align="left" style="font-weight: bolder;font-size: 4">Subject </td>
						        <td width="3%" style="font-weight: bolder;font-size: 4">:</td>
						        <td width="45%" height="25" align="left" style="font-weight: bolder;font-size: 4"><bean:write property="subjectName" name="newInternalMarksEntryForm"></bean:write>
						        (<bean:write property="subjectCode" name="newInternalMarksEntryForm"></bean:write>)</td>
								<td width="10%" headers="25" align="left" style="font-weight: bolder;font-size: 4">Date </td>
								<td width="2%" style="font-weight: bolder;font-size: 4">:</td>
								<td width="25%" height="25" align="left" style="font-weight: bolder;font-size: 4"><bean:write property="currentDate" name="newInternalMarksEntryForm"></bean:write></td>		
						        </tr>
						        </table>
						        </td></tr>
							    <tr height="10"></tr>
							    <tr>
							    <td width="100%"><table width="100%" rules="all" frame="box">
							    <tr height="2"></tr>
							    <tr>
									<td>
									<div align="center" style="font-weight: bold;font-size: 3" >Sl.No</div>
									</td>
									<td style="font-weight: bold;font-size: 3" >Student Name</td>
									<td height="25" style="font-weight: bold;font-size: 3" >Register No.</td>
									<logic:equal value="1" property="subjectType" name="newInternalMarksEntryForm">
									<td style="font-weight: bold;font-size: 3" >Theory Marks </td>
									</logic:equal>
									<logic:equal value="0" property="subjectType" name="newInternalMarksEntryForm">
										<td style="font-weight: bold;font-size: 3" >Practical Marks  </td>
									</logic:equal>
									<logic:equal value="11" property="subjectType" name="newInternalMarksEntryForm">
										<td style="font-weight: bold;font-size: 3" >Theory Marks   </td>
									</logic:equal>
								</tr>
								<tr height="2"></tr>
								<logic:notEmpty  property="studentList" name="newInternalMarksEntryForm">
								<nested:iterate property="studentList" id="examMarksEntryStudentTO" name="newInternalMarksEntryForm" indexId="count">
									<tr>
										<td width="10%" style="font-size: 80%;">
										<div align="center"><c:out value="${count+1}"></c:out></div>
										</td>
										<td width="40%" style="font-size: 80%;"><nested:write
											property="name" /></td>
										<td width="10%" align="center" style="font-size:  80%;"><nested:write
											property="registerNo" /></td>
										<logic:equal value="1" property="subjectType" name="newInternalMarksEntryForm">
											<logic:equal value="true" property="isTheory" name="examMarksEntryStudentTO">
												<td width="10%" align="center" style="font-size:  80%;">
												    <bean:write property="theoryMarks" name="examMarksEntryStudentTO"></bean:write>	
												</td>
											</logic:equal>
										</logic:equal>
										<logic:equal value="0" property="subjectType" name="newInternalMarksEntryForm">
											<logic:equal value="true" property="isPractical" name="examMarksEntryStudentTO">
												<td width="10%" align="center" style="font-size:80%;">
													 <bean:write property="practicalMarks" name="examMarksEntryStudentTO"></bean:write>	
												</td>
											</logic:equal>
										</logic:equal>
									</tr>
								</nested:iterate>
								</logic:notEmpty>
							    </table></td></tr>
						   </table>
							</td>
						</tr>
						<tr height="40"></tr>
						<tr><td width="100%" align="center" style="font-size: 5 ;font-weight: bolder;">Signature</td></tr>
						<tr height="40"></tr>
					</table>
			</td>
		</tr>
	</table>
</html:form>
<script type="text/javascript">window.print();</script>