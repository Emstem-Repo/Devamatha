<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@page import="com.kp.cms.constants.CMSConstants"%>
<header>
<div class="leftpanel">
	<div class="media profile-left">

		<div class="bs-example">
			<a class="pull-left profile-thumb"
				href="#"
				><img
				class="img-circle" src='<%=request.getContextPath()+"/PhotoServlet?"%>' alt=""
				onerror='onImgError(this);' onLoad='setDefaultImage(this);' onclick="goToHomePage()"></a>
		</div>
		<div class="media-body">
			<h4 class="media-heading">
				<c:out value="${username}" />
			</h4>
		</div>
	</div>
	<!-- media -->
	<h5 class="leftpanel-title">Navigation</h5>
	<ul class="nav nav-pills nav-stacked">
		<li class="parent"><a href=""><i
				class="fa fa-angle-double-right fa-lg"></i> <span>ATTENDANCE</span></a>
			<ul class="children">
				<c:if test="${showAttendanceRep}">
					<c:choose>
						<c:when test="${linkForCjc}">

							<li><a
								href="studentWiseAttendanceSummary.do?method=getIndividualStudentWiseAttendanceSummary">Attendance</a></li>
						</c:when>
						<c:otherwise>
							<li><a
								href="studentWiseAttendanceSummary.do?method=getIndividualStudentWiseSubjectAndActivityAttendanceSummary"><i
									class="fa fa-angle-double-right fa-lg"></i><span>
										Attendance</span></a> <a
								href="studentWiseAttendanceSummary.do?method=getIndividualSessionWiseSubjectAndActivityAttendanceSummary">
									<!--<i
						class="fa fa-angle-double-right fa-lg"></i><span>SESSION WISE ATTENDANCE</span>-->
							</a></li>
						</c:otherwise>
					</c:choose>
				</c:if>
				<c:if test="${linkForCjc}">
					<li><a
						href="studentWiseAttendanceSummary.do?method=getIndividualStudentWiseActivityAttendanceSummary">
					</a><span>Activity Attendance</span></li>
				</c:if>
				<c:if test="${showLinks}">
					<li><a
						href="studentWiseAttendanceSummary.do?method=getStudentAbscentWithCocularLeave"><i
							class="fa fa-angle-double-right fa-lg"></i><span>Absence 
								Details </span></a></li>
				</c:if>
				<li><a
					href="studentWiseAttendanceSummary.do?method=getTeacherNotTakenAttendanceSummary"><i
						class="fa fa-angle-double-right fa-lg"></i><span>Attendance
							Not Taken</span></a></li>
				<c:if test="${previousAttendance}">
					<c:choose>
						<c:when test="${linkForCjc}">
							<li><a
								href="studentWiseAttendanceSummary.do?method=initPreviousStudentAttendanceSummeryCjc"><i
									class="fa fa-angle-double-right fa-lg"></i><span><bean:message
											key="knowledgepro.attendance.previous" /></span></a></li>
						</c:when>
						<c:otherwise>
							<li><a
								href="studentWiseAttendanceSummary.do?method=initPreviousStudentAttendanceSummeryChrist"><i
									class="fa fa-angle-double-right fa-lg"></i><span><bean:message
											key="knowledgepro.attendance.previous" /></span></a></li>
						</c:otherwise>
					</c:choose>
				</c:if>

			</ul></li>
		<li class="parent"><a href=""><i
				class="fa fa-arrow-circle-o-down"></i> <span>DOWNLOADS</span></a>
			<ul class="children">
				<!-- <li><a
					href="studentWiseAttendanceSummary.do?method=getInternalMarkDetails"><i
						class="fa fa-certificate"></i> <span>INTERNAL EXAMINATION
							RESULT</span></a></li> -->
				<c:if test="${linkForCjc==false}">
					<li><a href="StudentLoginAction.do?method=initMarksCard"><i
							class="fa fa-certificate"></i><span>University Mark List</span></a></li>
				</c:if>
				<!-- <li><a href="downloadForms/exam_notificationug.pdf"><i
						class="fa fa-file-pdf-o"></i> <span>SUPPLEMENTARY/IMPROVEMENT
							EXAM NOTIFICATION </span></a></li>
				<li><a href="downloadForms/OPEN COURSES AND SYLLABUS.pdf"><i
						class="fa fa-file-pdf-o"></i> <span>FIFTH SEMESTER OPEN
							COURSES-LIST & SYLABUS</span></a></li>
				<li><a href="downloadForms/OPEN COURSE 2018 CLASS WISE.pdf"><i
						class="fa fa-file-pdf-o"></i> <span>OPEN COURSES ALLOTEMENT
							CLASS WISE DETAILS</span></a></li>
				<li><a href="downloadForms/OPEN COURSE 2018 COURSE WISE.pdf"><i
						class="fa fa-file-pdf-o"></i> <span>OPEN COURSES ALLOTEMENT
							LIST</span></a></li>
				<li><a href=""><i class="glyphicon glyphicon-bell"></i> <span>
							REGULAR EXAM NOTIFICATION</span></a></li> -->
				<c:if test="${showLinks}">
					<c:if test="${showHallTicket}">

						<c:if
							test="${isHallTicketBlockedStudent && hallTicketBlockReason!= null && hallTicketBlockReason!= ''}">
							<li><a
								href="StudentLoginAction.do?method=studentLoginHallTicketBlock&supHallTicket=false">DOWNLOAD
									HALLTICKET</a></li>
						</c:if>
						<c:if test="${!isHallTicketBlockedStudent}">
							<c:choose>
								<c:when test="${agreement == null}">

									<li><a href="StudentLoginAction.do?method=getHallTicket">DOWNLOAD
											HALLTICKET</a></li>
								</c:when>
								<c:otherwise>
									<li><a
										href="StudentLoginAction.do?method=studentLoginHallTicketAgreement">DOWNLOAD
											HALLTICKET</a></li>
								</c:otherwise>
							</c:choose>
						</c:if>
					</c:if>
					<logic:notEmpty name="supHallTicketList" scope="session">
						<logic:iterate id="to" name="supHallTicketList" scope="session"
							type="com.kp.cms.to.exam.ShowMarksCardTO">
							<c:if test="${to.showSupMC}">

								<c:if
									test="${to.supMCBlockedStudent && to.supMCBlockReason!= null && to.supMCBlockReason!= ''}">
									<li><a
										href="StudentLoginAction.do?method=studentLoginHallTicketBlock&supHallTicket=true&count=<%=to.getCnt()%>">DOWNLOAD
											SUPPLEMENTARY HALLTICKET FOR</a></li>
									<%=to.getExamName()%>
								</c:if>
								<c:if test="${!to.supMCBlockedStudent}">
									<c:choose>
										<c:when
											test="${to.supHallTicketagreement != null && to.supHallTicketagreement!=''}">
											<li><a
												href="StudentLoginAction.do?method=studentLoginHallTicketSuplementaryAgreement&count=<%=to.getCnt()%>">DOWNLOAD
													SUPPLEMENTARY HALLTICKET</a></li>
										</c:when>
										<c:otherwise>
											<li><a
												href="StudentLoginAction.do?method=getSuppHallTicket&count=<%=to.getCnt()%>">DOWNLOAD
													SUPPLEMENTARY HALLTICKET</a></li>
										</c:otherwise>
									</c:choose>
								</c:if>

							</c:if>
						</logic:iterate>
					</logic:notEmpty>
					<c:if test="${showOverallReport}">
						<li><a
							href="CiaOverAllReport.do?method=getStudentOverAllMarksDetails">CIA
								OVERALL</a></li>
					</c:if>
				</c:if>
				<c:if test="${linkForCjc==false}">
					<c:if test="${linkForConsolidateMarksCard==true}">

						<li><a
							href="StudentLoginAction.do?method=getConsolidatedMarksCard">PRINT
								CONSOLIDATE MARKS CARD</a></li>
					</c:if>
				</c:if>
				<%
					if (session.getAttribute("honoursLink").toString() != null
							&& session.getAttribute("honoursLink").toString().equalsIgnoreCase("true")) {
				%>



				<li><a href="honoursCourseEntry.do?method=getHonoursCourse">APPLY
						FOR HONOURS PROGRAM</a></li>
				<%
					}
				%>
				<!--  start by giri      -->
				<c:if test="${showCertCourse}">
					<%
						String method = "StudentLoginAction.do?method=certificateCourseStatus&studentId="
									+ session.getAttribute("studentId") + "&courseId=" + session.getAttribute("courseId");
					%>
					<!--  <a href="<%=method%>" class="menuLink">-->
					<li><a href="<%=method%>">Certificate Course Status</a></li>
				</c:if>
				<%-- <c:if test="${linkForCjc==false}">
					<li><a href="StudentLoginAction.do?method=initInternalMarks"><i
							class="fa fa-certificate"></i><span>INTERNAL MARK</span></a></li>
				</c:if> --%>
			</ul></li>
		<c:if test="${linkForCjc}">
			<li class="parent"><a href=""><i class="fa fa-edit"></i> <span>SAP</span></a>
				<ul class="children">
					<li><a href="studentFeedBack.do?method=initStudentFeedback">FEEDBACK</a></li>
				</ul></li>
		</c:if>

		<c:if test="${linkForCertificateCourse}">
			<li class="parent"><a href=""><i class="fa fa-edit"></i> <span>SAP</span></a>
				<ul class="children">

					<li><a href="">CERTIFICATE COURSE STATUS</a></li>
					<li><a
						href="NewStudentCertificateCourse.do?method=initCertificateCourseForStudentLogin">APPLY
							FOR CERTIFICATE COURSE</a></li>
				</ul></li>
		</c:if>
		<c:if test="${linkForCjc==false}">
			<c:if test="${sapResultLinks==true}">

				<li class="parent"><a href=""><i class="fa fa-edit"></i> <span>SAP</span></a>
					<ul class="children">

						<li><a href="StudentLoginAction.do?method=displaySAPpResuls">SAP</a></li>
						<li><a href="">SAP RESULTS </a></li>
					</ul></li>
			</c:if>



		</c:if>
		<!-- <li class="parent"><a href=""><i
				class="fa fa-angle-double-right fa-lg"></i> <span>TIME TABLE</span></a>
			<ul class="children">
				<li><a href="StudentLoginAction.do?method=getStudentTimeTable"><i
							class="fa fa-certificate"></i><span>Time Table</span></a></li>
				
			</ul></li> -->
			
			<li class="parent"><a href=""><i
				class="fa fa-angle-double-right fa-lg"></i> <span>STUDENT DETAILS</span></a>
			<ul class="children">
			<c:if test="${studentEdit==true}">
				<li><a href="StudentLoginAction.do?method=personalDataEdit"><i
							class="fa fa-certificate"></i><span>Edit Personal Detail </span></a></li>
							<li><a href="StudentLoginAction.do?method=editStudentPhotoInLogin"><i
							class="fa fa-certificate"></i><span>Change Student Photo </span></a></li>
			</c:if>
			
				
			</ul></li>
			<li class="parent"><a href=""><i
				class="fa fa-angle-double-right fa-lg"></i> <span>Disciplinary Action</span></a>
			<ul class="children">
				<li><a href="StudentLoginAction.do?method=disciplineAndAchievementView&type=Discipline action"><i
							class="fa fa-certificate"></i><span>View Disciplinary Action</span></a></li>
			</ul></li>
			<li class="parent"><a href=""><i
				class="fa fa-angle-double-right fa-lg"></i> <span>Achievements</span></a>
			<ul class="children">
				<li><a href="StudentLoginAction.do?method=disciplineAndAchievementView&type=Achievement"><i
							class="fa fa-certificate"></i><span>View Achievements</span></a></li>
				
			</ul></li>
			<li class="parent"><a href=""><i
				class="fa fa-angle-double-right fa-lg"></i> <span>Progress Card</span></a>
			<ul class="children">
				<li><a href="StudentLoginAction.do?method=viewProgressCard"><i
							class="fa fa-certificate"></i><span>View ProgresssCard</span></a></li>
			</ul></li>
			
		<%-- <c:if test="${linkForCjc==false}">

			<li class="parent"><a href=""><i class="fa fa-check"></i> <span>APPLY
						ONLINE</span></a>

				<ul class="children">
					<c:if test="${displayconvocation==true}">
						<li><a href="">APPLY ONLINE</a></li>
						<li><a
							href="newSupplementaryImpApp.do?method=initConvocationFeeForStudentLogin">CONVOCATION
								REGISTRATION</a></li>
					</c:if>
					<c:if test="${showRegApp}">
						<c:if test="${!isBlockedStudentRegularApp}">
							<li><a
								href="newSupplementaryImpApp.do?method=checkRegularApplicationForStudentLogin"><i
									class="fa fa-check"></i><span> EXAMINATION REGISTRATION</span></a></li>

						</c:if>
					</c:if>
					<c:if test="${showCourseApp}">
						<li><a
							href="OptionalCourseApplication.do?method=initOptionalCourseApplication">OPEN
								COURSE APPLICATION</a></li>
					</c:if>
					<c:if test="${showRevApp}">

						<li><a
							href="newSupplementaryImpApp.do?method=checkRevaluationApplicationForStudentLogin&isRevScrutinty=true&isphotoCopy= false"><i
								class="fa fa-check"></i><span>APPLICATION FOR REVALUTION
									/SCRUTINY</span></a></li>
					</c:if>
					<c:if test="${showPhotoApp}">
						<li><a
							href="newSupplementaryImpApp.do?method=checkRevaluationApplicationForStudentLogin&isRevScrutinty=false&isphotoCopy=true"><i
								class="fa fa-check"></i><span>APPLICATION FOR PHOTOCOPY</span></a></li>
					</c:if>
					<logic:notEmpty name="supRevalApplnList" scope="session">
						<logic:iterate id="to" name="supRevalApplnList" scope="session"
							type="com.kp.cms.to.exam.ShowMarksCardTO">

							<c:if test="${to.showSupRevalAppln}">
								<c:if test="${!to.supMCBlockedStudent}">

									<li><a
										href="newSupplementaryImpApp.do?method=checkRevaluationApplicationForSuppl&suppExamId=<%=to.getSupMCexamID()%>&suppClassId=<%=to.getSupMCClassId()%>&examName=<%=to.getExamName()%>&isRevScrutinty=true&isphotoCopy= false"><i
											class="fa fa-check"></i><span>APPLICATION FOR
												REVALUATION/SCRUTINY:</span></a></li>
								</c:if>
							</c:if>


						</logic:iterate>
					</logic:notEmpty>
					<logic:notEmpty name="supRevalApplnList" scope="session">
						<logic:iterate id="to" name="supRevalApplnList" scope="session"
							type="com.kp.cms.to.exam.ShowMarksCardTO">
							<c:if test="${to.showSupPhotoAppln}">
								<c:if test="${!to.supMCBlockedStudent}">
									<li><a
										href="newSupplementaryImpApp.do?method=checkRevaluationApplicationForSuppl&suppExamId=<%=to.getSupMCexamID()%>&suppClassId=<%=to.getSupMCClassId()%>&examName=<%=to.getExamName()%>&isRevScrutinty=false&isphotoCopy=true">APPLICATION
											FOR PHOTO COPY:</a></li>
								</c:if>
							</c:if>
						</logic:iterate>
					</logic:notEmpty>
					<c:if test="${isSupplyImpAppDisplay==true}">
						<%
							int i = 0;
						%>
						<logic:iterate id="examId" name="examIdList" scope="session">
							<%
								i++;
							%>
							<li><a
								href="newSupplementaryImpApp.do?method=checkSupplementaryImpApplicationForStudentLogin&examId=<%=examId%>"><i
									class="fa fa-check"></i><span>Supplementary Improvement
										Application</span></a></li>

						</logic:iterate>
					</c:if>


					<!--<li>	<a href="https://forms.gle/DQKyUtEg9Nb5Y7PJ9" target="_blank"><i
				class="fa fa-check"></i><span>CLUB REGISTRATION FORM</span></a></li>
			-->
					<!-- <li><a
						href="newSupplementaryImpApp.do?method=initMiscellaneousFeeForStudentLogin"><i
							class="fa fa-check"></i> <span>APPLICATION FOR
								MISCELLANEOUS FEE</span></a></li> -->
					<c:if test="${hostelLinks==true}">
						<li><a href="hostelLeave.do?method=initStudentHostelLeave">Hostel
								Leave Application</a></li>

						<li><a href="hostelLeave.do?method=viewStudentLeaves">Hostel
								Leave Status</a></li>
						<li><a
							href="MidSemRepeatExamApplication.do?method=initRepeatExamFeePayment">Mid
								Semester Repeat Exam Fees Payment</a></li>
					</c:if>
					<c:if test="${linkForRepeatExamsApplication==true}">
						<li><a
							href="MidSemRepeatExamApplication.do?method=initRepeatExamApplication">Mid
								Sem Repeat Exam Application Form</a></li>
					</c:if>
					<c:if test="${linkFordownloadHallTicket==true}">
						<li><a
							href="MidSemRepeatExamApplication.do?method=initRepeatExamFeePayment">Mid
								Sem Repeat Exam Download Hall Ticket</a></li>

					</c:if>
					<c:if test="${placementTraining==true}">
						<li><a href="https://forms.gle/h9pLeRg8YUoZGbtZ6"
							target="_balnk"><i class="fa fa-check"></i> <span>PLACEMENT
									TRANING PROGRAM</span></a></li>
					</c:if>
				</ul></li>
		</c:if> --%>
		<c:if test="${linkForPrintChallan}">

			<li class="parent"><a href=""><i class="fa fa-file-text"></i>
					<span>Print Challan</span></a>
				<ul class="children">
					<li><a href="StudentLoginAction.do?method=initFeeChallanPrint">Print
							Challan</a></li>
					<li><a href="">Print Fee Challan</a></li>


				</ul></li>
		</c:if>
		<c:if test="${linkForCjc==false}">
			<c:if test="${linkForOnlineReciepts}">

				<li class="parent"><a href=""><i class="fa fa-file-text"></i>
						<span>ONLINE RECIEPTS</span></a>
					<ul class="children">
						<li><a
							href="StudentLoginAction.do?method=initOnlineRecieptsForStudentLogin"><i
								class="fa fa-file-text-o"></i> <span>ONLINE RECIEPTS</span></a></li>
					</ul></li>
			</c:if>

		</c:if>

		<c:if test="${studentFacultyFeedback}">



			<li class="parent"><a href=""><i class="fa fa-file-text"></i>
					<span>Faculty Eval</span></a>
				<ul class="children">
					<li><a
						href="EvaluationStudentFeedback.do?method=initEvaluationStudentFeedback">Faculty
							Evaluation</a></li>
				</ul></li>
		</c:if>
		<%--
		<li class="parent"><a href=""><i class="fa fa-money"></i><span>FEE</span></a>
	<ul class="children">
		<li><a href="FeePayment.do?method=initFeePaymentStudentLogin"><i
			class="fa fa-inr"></i> <span>FEE PAYMENT</span></a></li>
	</ul>
	</li>
	 --%>

	</ul>

</div>

</header>

<script type="text/javascript">
function goToHomePage() {
	document.location.href = "StudentLoginAction.do?method=returnHomePage";
}
</script>