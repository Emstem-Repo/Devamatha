<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@page import="java.util.Map,java.util.HashMap"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@page import="com.kp.cms.constants.CMSConstants"%>
<html>
<head>

<style>
    @media print {
            tr.page-break  { display: block; page-break-before: always; }
        } 
     
 	
}  
 </style>
    
 <script language="javascript">
    document.onmousedown=disableclick;
    status="Right Click Disabled";
    function disableclick(event)
    {
      if(event.button==2)
       {
         alert(status);
         return false;    
       }
    }
    </script>

</head>

<html:form action="/marksEntry" method="POST" enctype="multipart/form-data">
	<html:hidden property="formName" value="newExamMarksEntryForm"	styleId="formName" />
	<table width="99%" border="0">
		
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				
				
				<tr>
					
					<td class="heading">
					<table width="100%" border="0" align="center" cellpadding="0"
						cellspacing="0">
						
						<tr>
							
							<td valign="top">
							<table width="100%" cellspacing="1" cellpadding="2">
								
								<tr>
									<td align="center" colspan="4">
									<img width="500" height="80"  src='<%= request.getRequestURL().toString().replace(request.getRequestURI().substring(0), request.getContextPath()).concat("/LogoServlet") %>' alt="Logo not available">
									</td>

								</tr>
								
								<tr>
									<td width="10%" height="25" class="row-odd">
									<div align="left">Program :</div>
									</td>
									<td width="50%" height="25" class="row-even"><bean:write
										property="courseName" name="newExamMarksEntryForm"></bean:write></td>
									<td width="30%" class="row-odd">
									<div align="right">Semester :</div>
									</td>
									<td width="10%" class="row-even"><bean:write
										property="schemeNo" name="newExamMarksEntryForm"></bean:write></td>

								</tr>
								<tr>
									<td width="10%" height="25" class="row-odd">
									<div align="left">Course Name:</div>
									</td>
									<td width="50%" height="25" class="row-even"><bean:write
										property="subjectName" name="newExamMarksEntryForm"></bean:write></td>
									<td width="30%" height="25" class="row-odd">
									<div align="right">Subject Type :</div>
									</td>
									<td height="25" width="10%" class="row-even">
									<logic:equal value="1" property="subjectType" name="newExamMarksEntryForm">
									Theory
									</logic:equal>
									<logic:equal value="0" property="subjectType" name="newExamMarksEntryForm">
									Practical
									</logic:equal>
									<logic:equal value="11" property="subjectType" name="newExamMarksEntryForm">
									Both
									</logic:equal>
									</td>
								</tr>
								
							</table>
							</td>
							
						</tr>
					</table>
					</td>
					
				</tr>
				
				<tr> <td colspan="3" height="30"></td></tr>
				
				
				
				<tr>
					
					<td class="heading">
					
					<table width="100%" border="0" align="center" cellpadding="0"
						cellspacing="0">

						
						<tr>
							
							<td valign="top">
							
							<table style="page-break-before: always; page-break-after: always;" width="100%" border="1" cellspacing="1" cellpadding="0">
								<tr style="page-break-inside: avoid;">

									<td height="35" class="row-odd">
									<div >Sl.No</div>
									</td>
									<td height="35" class="row-odd" >Student Name</td>
									<td height="35" class="row-odd" align="center">Reg No.</td>
									<td height="35" class="row-odd" align="center">Class No.</td>
<!-- 									<td height="35" class="row-odd" align="center">Attendance Mark</td> -->
									
									<logic:iterate  property="examList" name="newExamMarksEntryForm" id="examid">
									<td height="35" class="row-odd" align="center">
									<bean:write name="examid" property="examName"/>
									</td>
									</logic:iterate>
<!-- 									<td height="35" class="row-odd" align="center">Total Marks</td> -->
									<td height="35" class="row-odd" align="center">Sign</td>
									
									<logic:equal value="11" property="subjectType" name="newExamMarksEntryForm">
									<logic:iterate  property="examList" name="newExamMarksEntryForm" id="examid">
									<td align="center" class="row-odd">
									<bean:write name="examid" property="examName"/>
									<br/>
									(Theory Marks)
									</td>
									<td align="center" class="row-odd">
									<bean:write name="examid" property="examName"/>
									<br/>
									(Practical Marks)
									</td>
									</logic:iterate>
									<td height="35" align="center" class="row-odd">Total Theory</td>
									<td height="35" align="center" class="row-odd">Total Practical</td>
									</logic:equal>
									
									

								</tr>
								<logic:iterate name="newExamMarksEntryForm" property="studentMarksList" id="examStudentTO"	indexId="count">
								
								<tr style="page-break-inside: avoid;" class="row-white">
								
										<td height="35">
										<div align="center"><c:out value="${count+1}"></c:out></div>
										</td>
										<td height="35"  ><bean:write name="examStudentTO" property="studentName" /></td>
										<td height="35" align="center" ><bean:write	name="examStudentTO" property="registerNo" /></td>
										<td height="35"  align="center" ><bean:write	name="examStudentTO" property="rollNo" /></td>
										<%-- <td height="35"  align="center">
											<logic:notEmpty property="studentAttMarksList" name="examStudentTO">
												<nested:iterate property="studentAttMarksList" id="examMarksEntryStudentTO" name="examStudentTO"	indexId="count1">
													<nested:write  property="attendanceMark" name="examMarksEntryStudentTO"/>
												</nested:iterate>
											</logic:notEmpty>
										</td> --%>
										<c:if test="${examStudentTO.maxMarksInternal!=null}">
											<logic:equal value="true" name="newExamMarksEntryForm" property="isExamMaxEntry">
												<td height="35"  align="center"><bean:write	name="examStudentTO" property="maxMarksInternal" /></td>
											</logic:equal>
										</c:if>
										<%String tott="t_"+count; %>
										<%String totp="p_"+count; %>
										<nested:iterate property="studentMarksList" id="examMarksEntryStudentTO" name="examStudentTO"	indexId="count1">
											
											<%String id="test_"+count+"_"+count1; %>
										<logic:equal value="false" name="examMarksEntryStudentTO" property="isConversion">	
											<logic:equal value="true" property="isTheory" name="examMarksEntryStudentTO">
												<td height="35"  align="center">
												
												
													<nested:write  property="theoryMarks" />
												
												</td>
											</logic:equal>
											
											<logic:equal value="true" property="isPractical" name="examMarksEntryStudentTO">
											<td  height="35" align="center">
											
													<nested:write  property="practicalMarks"  />
											</td>
											</logic:equal>
										</logic:equal>
										</nested:iterate>
										<logic:equal value="1" property="subjectType" name="newExamMarksEntryForm">
<%-- 										<td height="35"><div align="center" id='<%=tott %>'><bean:write name="examStudentTO" property="totalInternalMarksT"/></div></td> --%>
										</logic:equal>
										<logic:equal value="0" property="subjectType" name="newExamMarksEntryForm">
										<td height="35"><div align="center" id='<%=totp %>'><bean:write name="examStudentTO" property="totalInternalMarksP" /></div></td>
										</logic:equal>
										<logic:equal value="11" property="subjectType" name="newExamMarksEntryForm">
										<td height="35" ><div align="center" id='<%=tott %>'><bean:write name="examStudentTO" property="totalInternalMarksT" /></div></td>
										<td height="35" ><div align="center" id='<%=totp %>'><bean:write name="examStudentTO" property="totalInternalMarksP" /></div></td>
										</logic:equal>
										
										<td height="35" >
										<div align="center"></div>
										</td>
											
									</tr>
								
								</logic:iterate>
							</table>
							
							</td>
							
						</tr>
						
					</table>
					</td>

					
				</tr>
		
				<tr height="25"></tr>
				<tr height="25"></tr>
				<tr><td colspan="8"><table width="100%"><tr>
									<td  width="40%" align="left">
									Signature of Teacher
									</td>

									<td  align="center" width="40%" >
								Signature of HOD
									</td>
		                           <td   align="right" width="40%" >Date:<bean:write name= "newExamMarksEntryForm" property="date"></bean:write></td>  </tr></table></td>
		                         

								</tr>


				
			</table>
			</td>
		</tr>

	</table>
</html:form>
</html>
<script type="text/javascript"> window.print();</script>
