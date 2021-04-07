<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<script type="text/javascript">
	function cancelAction() {
		document.location.href = "studentWiseAttendanceSummary.do?method=initStudentWiseAttendanceSummaryAdmin";
	}

	function winOpen(reqId) {		
		var url = "studentWiseAttendanceSummary.do?method=getStudentAttendanceSummaryByAdmin&studentID=" + reqId;
		myRef = window.open(url,"StudentAttendanceDetails","left=20,top=20,width=700,height=800,toolbar=1,resizable=0,scrollbars=1");
	}
	function viewStudent(applicationNumber,appliedYear,courseId){
		var url  = "ApplicantDetails.do?method=getApplicantDetails&applicationNumber="+applicationNumber+"&applicationYear="+appliedYear+"&courseId="+courseId;
		myRef = window.open(url,"ViewApplicantDetails","left=20,top=20,width=800,height=600,toolbar=1,resizable=0,scrollbars=1");
	}
			
	</script>
<html:form action="/studentWiseAttendanceSummary" >
<html:hidden property="formName" value="studentWiseAttendanceSummaryForm"/>
<html:hidden property="pageType" value="1"/>	
<c:set var="temp" value="0"/>
<logic:notEmpty name="studentWiseAttendanceSummaryForm" property="studentTOList">
<table width="100%" border="0">
		<tr>
			<td><span class="Bredcrumbs">
			<span class="Bredcrumbs">Attendance &gt;&gt;
			Attendance check for a student 
			 &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td valign="top">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader">  Attendance check for a student </strong></td>

					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="35" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news"></td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				<tr>
					<td height="41" valign="top" background="images/Tright_03_03.gif"></td>
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
							<td height="25" class="row-odd">RollNo </td>
							<td class="row-odd">
							<div align="center">RegNo </div>
							</td>
							<td class="row-odd">
							<div align="center">Student Name</div>
							</td>
							<td class="row-odd">
							<div align="center">Attendance</div>
							</td>
							<td class="row-odd">
							<div align="center">Details</div>
							</td>
						</tr>
							
					<logic:iterate name="studentWiseAttendanceSummaryForm" property="studentTOList"
							id="id" indexId="count">
                             <c:choose>
						 <c:when test="${temp == 0}">
									<tr>
										<td width="10%" height="25" class="row-white" align="center"><bean:write
											name="id" property="rollNo" /></td>
										<td width="10%" height="25" class="row-white" align="center"><bean:write
											name="id" property="registerNo" /></td>
										<td width="10%" height="25" class="row-white" align="center"><bean:write
											name="id" property="studentName" /></td>
										<td width="10%" height="25" class="row-white" align="center"><A HREF="javascript:winOpen('<bean:write name="id" property="id" />');"> Attendance </A></td>
										<td width="10%" height="25" class="row-white" align="center"><A HREF="javascript:viewStudent('<bean:write name="id" property="applicationNo" />', 
										'<bean:write name="id" property="appliedYear" />',
										'<bean:write name="id" property="courseId" />');">  Details </A></td>
										</tr>
									<c:set var="temp" value="1"/>
                              </c:when>
							<c:otherwise>
								<tr>
										<td width="10%" height="25" class="row-even" align="center"><bean:write
											name="id" property="rollNo" /></td>
										<td width="10%" height="25" class="row-even" align="center"><bean:write
											name="id" property="registerNo" /></td>
										<td width="10%" height="25" class="row-even" align="center"><bean:write
											name="id" property="studentName" /></td>
										<td width="10%" height="25" class="row-even" align="center"><A HREF="javascript:winOpen('<bean:write name="id" property="id" />');"> Attendance </A></td>
										<td width="10%" height="25" class="row-even" align="center"><A HREF="javascript:viewStudent('<bean:write name="id" property="applicationNo" />', 
										'<bean:write name="id" property="appliedYear" />',
										'<bean:write name="id" property="courseId" />');">  Details </A></td>
								</tr>
								<c:set var="temp" value="0" />
							</c:otherwise>
							</c:choose>
						</logic:iterate> 								
						</table>
						</td>
						<td width="5" height="29" background="images/right.gif"></td>
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
					<td height="35" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="bottom" class="news">
					<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
						<tr>
							<td colspan="2" align="center"><html:button  property=""
									styleClass="formbutton" value="Back" onclick="cancelAction()" /></td>
						</tr>
					</table>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>

					<td valign="top" class="news">&nbsp;</td>
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
</logic:notEmpty>
</html:form>