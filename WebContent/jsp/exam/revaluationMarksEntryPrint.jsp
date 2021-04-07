<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@page import="java.util.Map,java.util.HashMap"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<script>

	
	
</script>
<style>
.table-border{
    border: 1px solid black;
    border-collapse: collapse;
}
</style>

<html:form action="/marksEntry" method="POST"
	enctype="multipart/form-data">
	<html:hidden property="formName" value="newExamMarksEntryForm"
		styleId="formName" />
	<html:hidden property="pageType" value="3" />
	<html:hidden property="method" styleId="method" value="" />
		<table width="99%" border="0">

		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
				<td align="center" colspan="3">
		  			<img width="350" height="70"  src='<%= request.getRequestURL().toString().replace(request.getRequestURI().substring(0), request.getContextPath()).concat("/LogoServlet") %>' alt="Logo not available">
				</td>
				</tr>	
				<tr>
					<td >
					<table width="100%"  align="center" class="table-border" cellpadding="0"
						cellspacing="0">
				
						<tr>
							<td valign="top">
							<table width="100%" cellspacing="1" cellpadding="2">
								<tr>
									<td width="10%" height="25" class="row-odd">
									<div align="left">Exam Name :</div>
									</td>

									<td width="65%" height="25" class="row-even" ><bean:write
										property="examName" name="newExamMarksEntryForm"></bean:write></td>
											
										
								</tr>
								<tr><td width="10%" height="25" class="row-odd">
									<div align="left" align="left">Scheme :</div>
									</td>
											<td width="10%" height="25" class="row-even"><bean:write
										property="schemeNo" name="newExamMarksEntryForm"></bean:write></td>
				</tr>

								<tr>
									<td width="25%"  height="25" class="row-odd">
									<div align="left">Course :</div>
									</td>
									<td  height="25" class="row-even"><bean:write
										property="courseName" name="newExamMarksEntryForm"></bean:write></td>
									

								</tr>
								<tr>
									<td height="25" class="row-odd">
									<div align="left">Subject Name:</div>
									</td>
									<td height="25" class="row-even"><bean:write
										property="subjectName" name="newExamMarksEntryForm"></bean:write></td>
									
								</tr>
			
					
							</table>
							</td>
						</tr>
				
					</table>
					</td>
				</tr>
                <tr height="15"></tr>
				<tr>
					<td class="heading">
					<table width="100%" border="0" align="center" cellpadding="0"
						cellspacing="0">

						<tr>
							<td valign="top">
							<table width="100%" cellspacing="1" cellpadding="0" class="table-border" rules="all">
								<tr>
										     <td height="25" width="20%" align="center" class="row-odd" >Serial No</td>
											<td height="25" width="20%" align="center" class="row-odd" >Student Name</td>
											<td height="25" width="20%" align="center" class="row-odd" >Register No</td>
												
											<td align="center" class="row-odd" >
											Current Revaluation Marks
											</td>
											
											<logic:equal value="R" name="newExamMarksEntryForm" property="entryType">
											<td align="center" class="row-odd" width="20%" >
											Before Revaluation Marks
											</td></logic:equal>
													<logic:equal value="S" name="newExamMarksEntryForm" property="entryType">
											<td align="center" class="row-odd" width="20%" >
											Current Scrutiny
											</td></logic:equal>	
													<logic:equal value="CV" name="newExamMarksEntryForm" property="entryType">
											<td align="center" class="row-odd" width="20%" >
											 Current Challenge Valuation Marks
											</td></logic:equal>	
									
										</tr>
						
										<nested:iterate property="marksList" indexId="count111" id="list">
								
										  <c:choose>
										   <c:when test="${list.theoryPractical== 'T' }">
													<tr>
											<td height="25">
											<div align="center"><c:out value="${count111+1}" /></div>
											</td>
												<td width="23%" height="25" align="center" class="row-even"><bean:write name="list"
													property="studentName" /></td>

												<td width="23%" height="25" align="center" class="row-even"><bean:write
												name="list" property="regNo" /></td>

										
												<td align="center" class="row-even">
													<bean:write name="list"  property="prevoiusTheoryMarks" />
												</td>
										
									
												<td  class="row-even" align="center">
												    <bean:write name="list"  property="currentTheoryMarks" />
												</td>
										
												
											</tr>
										   </c:when>
										   </c:choose>
										   <c:choose>
										   <c:when test="${list.theoryPractical== 'P' }">
														<tr>
											<td height="25">
											<div align="center"><c:out value="${count111+1}" /></div>
											</td>
												<td width="23%" height="25" align="center" class="row-even"><bean:write name="list"
													property="studentName" /></td>

												<td width="23%" height="25" align="center" class="row-even"><bean:write
												name="list" property="regNo" /></td>

												<td align="center" class="row-even">
													<bean:write name="list"  property="previousPracticalMarks" />
												</td>
									
												<td  class="row-even" align="center">
													<bean:write name="list"  property="currentPracticalMarks" />
												</td>
												
											</tr>
										   </c:when>
										     <c:when test="${list.theoryPractical== 'B' }">
														<tr>
											<td height="25">
											<div align="center"><c:out value="${count111+1}" /></div>
											</td>
												<td width="23%" height="25" align="center" class="row-even"><bean:write name="list"
													property="studentName" /></td>

												<td width="23%" height="25" align="center" class="row-even"><bean:write
												name="list" property="regNo" /></td>

												<td align="center" class="row-even" align="center">
													<bean:write name="list"  property="previousPracticalMarks" />
												</td>
									
												<td  class="row-even" align="center">
													<bean:write name="list"  property="practicalMarks" />
												</td>
												
												
												<td align="center" class="row-even">
													<bean:write name="list"  property="prevoiusTheoryMarks" />
												</td>
										
									
												<td  class="row-even" align="center">
												    <bean:write name="list"  property="theoryMarks" />
												</td>
												
											</tr>
										   </c:when>
										   </c:choose>
						
										</nested:iterate>
								
								
								
								
								
								
								
							</table>
							</td>
						</tr>
				
					</table>
					</td>

				</tr>

			</table>
			</td>
		</tr>

	</table>
</html:form>
