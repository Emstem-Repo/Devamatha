<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>

<html>
<head>
<style type="text/css">
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}

div.sdmenu {
	width: 165px;
	font-family: Verdana, sans-serif;
	font-size: 11px;
	padding-bottom: 10px;
	background: url(images/bottom1.gif) no-repeat  right bottom;
	color: #fff;
}
div.sdmenu div {
	background: url(images/title11.gif) repeat-x;
	overflow: hidden;
}
div.sdmenu div:first-child {
	background: url(images/toptitle.gif) no-repeat;
}
div.sdmenu div.collapsed {
	height: 25px;
}
div.sdmenu div span {
	display: block;
	padding: 5px 25px;
	font-weight: bold;
	color: white;
	background: url(images/expanded.gif) no-repeat 10px center;
	cursor: default;
	border-bottom: 1px solid #ddd;
}
div.sdmenu div.collapsed span {
	background-image: url(images/collapsed.gif);
}
div.sdmenu div a {
	padding: 5px 10px;
	background: #cbecfb ;
	display: block;
	border-bottom: 1px solid #ddd;
	color: #117097;
    font-family: Verdana,Arial,Helvetica,sans-serif;
    font-size: 11px;
    font-weight: normal
}
div.sdmenu div a.current {
	background : #cbecfb;
	color: #117097;
    font-family: Verdana,Arial,Helvetica,sans-serif;
    font-size: 11px;
    font-weight: normal
}
div.sdmenu div a:hover {
	background : #474848 url(images/linkarrow.gif) no-repeat right center;
	color: #fff;
	font-family: Verdana,Arial,Helvetica,sans-serif;
    font-size: 11px;
    font-weight: normal
}
div.selected {
	background-color: #CC3366;
}
</style>
<script type="text/javascript">


	var myMenu;
	window.onload = function() {
	myMenu = new SDMenu("my_menu");
	myMenu.init();
	myMenu.expandAll();
	};
	
	
	var myMenu = new SDMenu("my_menu"); // ID of the menu element
	//Default values...
	myMenu.speed = 3;                     // Menu sliding speed (1 - 5 recomended)
	myMenu.remember = true;               // Store menu states (expanded or collapsed) in cookie and restore later
	myMenu.oneSmOnly = false;             // One expanded submenu at a time
	myMenu.markCurrent = true;            // Mark current link / page (link.href == location.href)
	
	//myMenu.init();
	
	//Additional methods...
	var firstSubmenu = myMenu.submenus[0];
	myMenu.expandMenu(firstSubmenu);      // Expand a submenu
	myMenu.collapseMenu(firstSubmenu);    // Collapse a menu
	myMenu.toggleMenu(firstSubmenu);      // Expand if collapsed and collapse if expanded
	
	myMenu.expandAll();                   // Expand all submenus
	myMenu.collapseAll();  	           // Collapse all submenus

	myMenu.expandAll = function() {
		var oldOneSmOnly = this.oneSmOnly;
		this.oneSmOnly = false;
		for (var i = 0; i < this.submenus.length; i++)
			if (this.submenus[i].className == "collapsed")
				this.expandMenu(this.submenus[i]);
		this.oneSmOnly = oldOneSmOnly;
	};
</script>
</head>
<body>
	<table width="250" border="0" cellspacing="0" cellpadding="0">
   <tr>
    <td >&nbsp;</td>
   </tr>
    
        <tr>
             <td class="">
             
             <table border="0" cellspacing="0" cellpadding="0">
             <tr>
             <td>
              <div id="my_menu" class="sdmenu">
             	<div class="expand">
             	<span >Attendance</span>
		         <c:if test="${showAttendanceRep}">
	                 <c:choose>
		         <c:when test="${linkForCjc}">
	                  <a href="studentWiseAttendanceSummary.do?method=getIndividualStudentWiseAttendanceSummary" class="navmenu"><bean:message key="knowledgepro.attendance"/> </a>
	              </c:when>
	              <c:otherwise>
	                   <a href="studentWiseAttendanceSummary.do?method=getIndividualStudentWiseSubjectAndActivityAttendanceSummary" class="navmenu"><bean:message key="knowledgepro.attendance"/></a> 
	              </c:otherwise>
	                </c:choose>
	             </c:if>
	              
                  <c:if test="${linkForCjc}">
                 	<a href="studentWiseAttendanceSummary.do?method=getIndividualStudentWiseActivityAttendanceSummary" class="navmenu"><bean:message key="knowledgepro.attendance.activity.attendance"/></a>
                  </c:if>
                  <c:if test="${showLinks}">
 					 <a href="studentWiseAttendanceSummary.do?method=getStudentAbscentWithCocularLeave" class="navmenu">Absence Details </a>
	                </c:if>
	              <c:if test="${previousAttendance}">
	               <c:choose>
		         <c:when test="${linkForCjc}">
	                  <a href="studentWiseAttendanceSummary.do?method=initPreviousStudentAttendanceSummeryCjc" class="menuLink"><img src="images/smallArrow.gif" width="13" height="11" border="0" align="absmiddle" ><bean:message key="knowledgepro.attendance.previous"/> </a>
	              </c:when>
	              <c:otherwise>
	                   <a href="studentWiseAttendanceSummary.do?method=initPreviousStudentAttendanceSummeryChrist" class="menuLink"><img src="images/smallArrow.gif" width="13" height="11" border="0" align="absmiddle" ><bean:message key="knowledgepro.attendance.previous"/></a> 
	              </c:otherwise>
	                </c:choose>
	             </c:if>
	            </div>
	            <div class="expand">
             	<span>Downloads</span>
	             	<a href="studentWiseAttendanceSummary.do?method=getInternalMarkDetails" class="navmenu"><bean:message key="knowledgepro.studentlogin.sem.result"/></a> 
	                   
	          
                   <!-- Mary:- Alternative code for MarksCard -->
                    <c:if test="${linkForCjc==false}">
		                   <a href="StudentLoginAction.do?method=initMarksCard" class="navmenu">Marks Card</a>
		            </c:if>
 				<c:if test="${showLinks}">
                  <c:if test="${showHallTicket}">
	                 
	                    <c:if test="${isHallTicketBlockedStudent && hallTicketBlockReason!= null && hallTicketBlockReason!= ''}">
		                   <a href="StudentLoginAction.do?method=studentLoginHallTicketBlock&supHallTicket=false" class="navmenu" style="text-decoration: blink; color: red">Download HallTicket</a> 
	                    </c:if>
	                   <c:if test="${!isHallTicketBlockedStudent}">
							<c:choose>	
							<c:when test="${agreement == null}">                    
			                   	<a href="StudentLoginAction.do?method=getHallTicket" class="navmenu" style="text-decoration: blink;color: red">Download HallTicket</a>
		                   	</c:when>
		                   	<c:otherwise>
			                   <a href="StudentLoginAction.do?method=studentLoginHallTicketAgreement" class="navmenu" style="text-decoration: blink; color: red">Download HallTicket</a> 
		                   	</c:otherwise>
		                   	</c:choose>
	                   	</c:if>
		               
                  </c:if>
                  
               
                  
                   <logic:notEmpty name="supHallTicketList" scope="session">
                   		<logic:iterate id="to" name="supHallTicketList" scope="session" type="com.kp.cms.to.exam.ShowMarksCardTO">
                   			<c:if test="${to.showSupMC}">
			                
			                     <c:if test="${to.supMCBlockedStudent && to.supMCBlockReason!= null && to.supMCBlockReason!= ''}">
				                   	<a href="StudentLoginAction.do?method=studentLoginHallTicketBlock&supHallTicket=true&count=<%= to.getCnt()%>" class="navmenu" style="text-decoration: blink;color: red">Download Supplementary HallTicket for <%= to.getExamName()%></a>
			                    </c:if>
			                     <c:if test="${!to.supMCBlockedStudent}">
									<c:choose>	
									<c:when test="${to.supHallTicketagreement != null && to.supHallTicketagreement!=''}">                    
					                   	<a href="StudentLoginAction.do?method=studentLoginHallTicketSuplementaryAgreement&count=<%= to.getCnt()%>" class="navmenu" style="text-decoration: blink;color: red">Download Supplementary HallTicket <%= to.getExamName()%></a> 
				                   	</c:when>
				                   	<c:otherwise>
					                   	<a href="StudentLoginAction.do?method=getSuppHallTicket&count=<%= to.getCnt()%>" class="navmenu" style="text-decoration: blink;color: red">Download Supplementary HallTicket <%= to.getExamName()%></a> 
				                   	</c:otherwise>
				                   	</c:choose>
			                   	</c:if>
			               
                 		 </c:if>
                   		</logic:iterate>
                   </logic:notEmpty>
                   <c:if test="${showOverallReport}">
	                 <a href="CiaOverAllReport.do?method=getStudentOverAllMarksDetails" class="navmenu">CIA Overall</a> 
		              
	              </c:if>
	              </c:if>
	              <c:if test="${linkForCjc==false}">
                    <c:if test="${linkForConsolidateMarksCard==true}">
                    
						   <a href="StudentLoginAction.do?method=certificateMarksCard" class="navmenu">Print Consolidate Marks Card </a>
				    </c:if>
				 </c:if>
	             <% if(session.getAttribute("honoursLink").toString() != null && session.getAttribute("honoursLink").toString().equalsIgnoreCase("true")){ %>
	                 <a href="honoursCourseEntry.do?method=getHonoursCourse" class="navmenu">Apply For Honours Program </a> 
	                 <%} %>
	             </div>
            
                  <!--<a href="hostelStudentViewMessage.do?method=initHostelStudentViewMessage" class="navmenu">Student View Message </a>
                  -->
                  <c:if test="${showLinks}">
                  <div class="expand">
                   <span>Feedback</span>
                 		<a href="studentFeedBack.do?method=initStudentFeedback" class="navmenu"><bean:message key="knowledgepro.studentFeedback.link"/></a>
                  </div>
                 </c:if> 
	              
	              
	               <!--<c:if test="${showCertCourse}">
		             <a href="StudentCertificateCourse.do?method=getCertificateCourses" class="navmenu">Certificate Course </a> 
	               </c:if>-->
                 <c:if test="${linkForCertificateCourse}">
      				  	<div class="expand">
      				 	 <span>CertificateCourse</span>
      				   		<a href="NewStudentCertificateCourse.do?method=initCertificateCourseForStudentLogin" class="navmenuRed" style="text-decoration: blink">Apply for Certificate Course</a>
						</div>
				</c:if>
						
					<c:if test="${linkForCjc==false}">
						<div class="expand">
						<span>Apply Online</span>
							<a href="newSupplementaryImpApp.do?method=checkSupplementaryImpApplicationForStudentLogin" class="navmenu">Supplementary Improvement Application</a>
							 <a href="certificateRequest.do?method=initCertificateRequest" class="navmenu">Certificate Request</a>
							<a href="certificateRequest.do?method=getCertificateStatus" class="navmenu">Certificate Status</a>
							<c:if test="${hostelLinks==true}">
								<a href="hostelLeave.do?method=initStudentHostelLeave" class="navmenu">Hostel Leave Application</a>
								<a href="hostelLeave.do?method=viewStudentLeaves" class="navmenu">Hostel Leave Status</a>
							</c:if>
							<!--<c:if test="${linkForCjc==false}">
							<a href="examRegistrationDetails.do?method=initExamRegistrationDetails" class="navmenu" style="text-decoration: blink" >SAP Exam Registration </a>
							</c:if>
						    --></div>
					</c:if>
				
				<c:if test="${linkForPrintChallan}">
					<div class="expand">
      				  <span>Print Challan</span>
					
	                  <a href="StudentLoginAction.do?method=initFeeChallanPrint" class="navmenu">Print Fee Challan </a> 
	              	</div>
	              </c:if>
                  
	              <c:if test="${linkForCjc==false}">
		          <c:if test="${linkForOnlineReciepts}">
		          <div class="expand">
		          <span>Online Reciepts</span>
	                  <a href="StudentLoginAction.do?method=initOnlineRecieptsForStudentLogin" class="navmenu">Online Reciepts</a> 
	              </div>
	              </c:if>
	              </c:if>
				
				 <c:if test="${linkForCjc==false}">
					<div class="expand">
      				  <span>Data Bank</span>
		                   <a href="http://repository.christuniversity.in/" class="navmenu" target="_blank">Data Bank </a>
		            </div>
		         </c:if>
		         <c:if test="${studentFacultyFeedback}">
	             	 <div class="expand">
	             	   <span>Faculty Eval</span>
                 				<a href="EvaluationStudentFeedback.do?method=initEvaluationStudentFeedback" class="navmenu" style="text-decoration: blink">Faculty Evaluation</a>
                	 		<c:if test="${linkForCjc==false}">
                	 			<a href="http://conf.indiaeduservices.com/christ/login/index.php" class="navmenu" target="_blank"  style="text-decoration: blink">Holistic Education / Indian Constitution Exam</a>
                	 		 </c:if>
                	 </div>
		          </c:if> 
                  <c:if test="${linkForCjc==false}">
                  	 <div class="expand">
      				  <span>My Course</span>
                   			
	              		
		                <a href="http://courses.christuniversity.in/login/index.php" class="navmenu" target="_blank">My Course</a>
		               </div>
		          </c:if>
		         
                 </div>
               </td>
               </tr>
             </table>
            
             </td>
           
           </tr>
     
   </table>
</body>
</html>
