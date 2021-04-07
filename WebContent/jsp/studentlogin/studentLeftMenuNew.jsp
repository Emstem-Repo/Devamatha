<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>

<html>
	<head>
		<style type="text/css">
			body
			{
				background-color:#FFF;
			}
			
			div.sdmenu {
				width: 185px;
				background-color:#FFF;
				font-family: Arial, sans-serif;
				font-size: 10px;
				padding-bottom: 10px;
				background: url(bottom.gif) no-repeat  right bottom;
				color: #000;
			}
			div.sdmenu div {
				/*background: url(title.gif) repeat-x;*/
				overflow: hidden;
				 border: 1px solid #0B7ED1;
			   background: -webkit-gradient(linear, left top, left bottom, from(#D5EAFA), to(#6EA7D1));
			   background: -webkit-linear-gradient(top, #D5EAFA, #6EA7D1);
			   background: -moz-linear-gradient(top, #D5EAFA, #6EA7D1);
			   background: -ms-linear-gradient(top, #D5EAFA, #6EA7D1);
			   background: -o-linear-gradient(top, #D5EAFA, #6EA7D1);
			   background-color: #6EA7D1;
			  
			   -webkit-border-radius: 2px;
			   -moz-border-radius: 2px;
			   border-radius: 2px;
			   -webkit-border-radius: 3px;
			   -moz-border-radius: 3px;
			   border-radius: 3px;
			   text-decoration: none;
			  -webkit-transition: .1s;
			   -moz-transition: .1s;
			   -o-transition: .1s;
			   cursor: pointer;
			   margin-bottom:5px;
			}
			
			div.sdmenu div:first-child {
				/*background: url(toptitle.gif) no-repeat;*/
				 border: 1px solid #666363;
			    border: 1px solid #0B7ED1;
			   background: -webkit-gradient(linear, left top, left bottom, from(#D5EAFA), to(#6EA7D1));
			   background: -webkit-linear-gradient(top, #D5EAFA, #6EA7D1);
			   background: -moz-linear-gradient(top, #D5EAFA, #6EA7D1);
			   background: -ms-linear-gradient(top, #D5EAFA, #6EA7D1);
			   background: -o-linear-gradient(top, #D5EAFA, #6EA7D1);
			   background-color: #6EA7D1;
			  
			   -webkit-border-radius: 2px;
			   -moz-border-radius: 2px;
			   border-radius: 2px;
			   -webkit-border-radius: 3px;
			   -moz-border-radius: 3px;
			   border-radius: 3px;
			   text-decoration: none;
			   margin-bottom:2px;
			}
			div.sdmenu div.collapsed {
				height: 25px;
			}
			div.sdmenu div span {
				display: block;
				padding: 5px 25px;
				font-size: 13px;
				font-weight: bold;
				color: #000;
				background: url(images/expanded.gif) no-repeat 10px center;
				cursor: default;
				
				border-bottom: 1px solid #ddd;
			}
			div.sdmenu div.collapsed span {
				background-image: url(images/collapsed.gif);
			}
			div.sdmenu div a {
				font-size: 12px;
				padding: 5px 10px;
				background: #eee;
				display: block;
				border-bottom: 1px solid #666;
				color: #000;
				text-decoration: none;
			}
			div.sdmenu div a.current {
				background : #ccc;
				font-size: 12px;
			}
			div.sdmenu div a:hover {
				background : url(images/linkarrow.gif) no-repeat right center ;
				color: #020ffe;
				
				border: 1px solid #C7C7C7;
			 	 border: 1px solid #77BFF2;
			   background: -webkit-gradient(linear, left top, left bottom, from(#F2F7FC), to(#C9E1F7));
			   background: -webkit-linear-gradient(top, #F2F7FC, #C9E1F7);
			   background: -moz-linear-gradient(top, #F2F7FC, #C9E1F7);
			   background: -ms-linear-gradient(top, #F2F7FC, #C9E1F7);
			   background: -o-linear-gradient(top, #F2F7FC, #C9E1F7);
			   background-color: #C9E1F7;
			  
			 
			   -webkit-border-radius: 2px;
			   -moz-border-radius: 2px;
			   border-radius: 2px;
			   
			  
			   
			   -webkit-transition: 0.5s;
			   -moz-transition: 0.5s;
			   -o-transition: 0.5s;
			  	 cursor: pointer;
				text-decoration: none;
			}
			div.selected {
				background-color: #74aad3;
				font-size: 12px;
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
	
		<style type="text/css">
			@keyframes blink {
			    0% {
			        opacity: 1;
			    }
			    50% {
			        opacity: 0;
			    }
			    100% {
			        opacity: 1;
			    }
			}
			.blinkImage {
			    animation: blink 1s;
			    animation-iteration-count: infinite;
			}
		</style>
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
								<div style="float: left" id="my_menu" class="sdmenu">
									<div class="collapsed">
										
										<span>Attendance</span>
			         					
			         					<c:if test="${showAttendanceRep}">
		                 					<c:choose>
			         							<c:when test="${linkForCjc}">
		                  							<a href="studentWiseAttendanceSummary.do?method=getIndividualStudentWiseAttendanceSummary" class="menuLink"><img src="images/smallArrow.gif" width="13" height="11" border="0"><bean:message key="knowledgepro.attendance"/> </a>
		              							</c:when>
			              						<c:otherwise>	              
			              							<!-- <a href="studentWiseAttendanceSummary.do?method=getIndividualSessionWiseSubjectAndActivityAttendanceSummary" class="menuLink"><img src="images/smallArrow.gif" width="13" height="11" border="0">SessionWise Attendance</a> --> 
			              							<a href="studentWiseAttendanceSummary.do?method=getIndividualStudentWiseSubjectAndActivityAttendanceSummary" class="menuLink"><img src="images/smallArrow.gif" width="13" height="11" border="0"><bean:message key="knowledgepro.attendance"/></a> 
			              						</c:otherwise>
		                					</c:choose>
		             					</c:if>	              
	                  					
	                  					<c:if test="${linkForCjc}">
	                 						<a href="studentWiseAttendanceSummary.do?method=getIndividualStudentWiseActivityAttendanceSummary" class="menuLink"><img src="images/smallArrow.gif" width="13" height="11" border="0"><bean:message key="knowledgepro.attendance.activity.attendance"/></a>
	                  					</c:if>
	                  					
	                  					<c:if test="${showLinks}">
	 					 					<a href="studentWiseAttendanceSummary.do?method=getStudentAbscentWithCocularLeave" class="menuLink"><img src="images/smallArrow.gif" width="13" height="11" border="0">Absence Details </a>
		                				</c:if>
		              					
		              					<c:if test="${previousAttendance}">
		                					<c:choose>
			           							<c:when test="${linkForCjc}">
		                  							<a href="studentWiseAttendanceSummary.do?method=initPreviousStudentAttendanceSummeryCjc" class="menuLink"><img src="images/smallArrow.gif" width="13" height="11" border="0"><bean:message key="knowledgepro.attendance.previous"/> </a>
		               							</c:when>
								                <c:otherwise>
								                    <a href="studentWiseAttendanceSummary.do?method=initPreviousStudentAttendanceSummeryChrist" class="menuLink"><img src="images/smallArrow.gif" width="13" height="11" border="0"><bean:message key="knowledgepro.attendance.previous"/></a> 
								                </c:otherwise>
		                					</c:choose>
		             					</c:if>
		             
							            <%-- <a href="ExtraCocurricularLeaveEntry.do?method=initExtraCocurricularLeaveEntry" class="menuLink"><img src="images/smallArrow.gif" width="13" height="11" border="0"><bean:message key="knowledgepro.attendance.studentLogin.extra.curricular.leave.entry"/></a> --%> 
							          		
							          	<a href="studentWiseAttendanceSummary.do?method=getTeacherNotTakenAttendanceSummary" class="menuLink"><img src="images/smallArrow.gif" width="13" height="11" border="0">Teacher Attendance Not Taken</a> 
		            	            
						            </div>
		            
		            				<div class="collapsed">
	             	
	             						<span>Downloads</span>
		             					
		             					<!-- <a href="studentWiseAttendanceSummary.do?method=getInternalMarkDetails" class="menuLink"><img src="images/smallArrow.gif" width="13" height="11" border="0"><bean:message key="knowledgepro.studentlogin.sem.result"/></a> -->
		             					
		             					<!--<a href="StudentLoginAction.do?method=initServiceLearningActivity" class="menuLink"><img src="images/smallArrow.gif" width="13" height="11" border="0">Activity </a> 
	               						<a href="StudentLoginAction.do?method=serviceLearningActivityRecord" class="menuLink"><img src="images/smallArrow.gif" width="13" height="11" border="0">Service Learning Record </a> 
		                
						                --><c:if test="${showConsolidateMarksCard}">       
											<a href="StudentLoginAction.do?method=initInternalGrievance" class="menuLink"><img src="images/smallArrow.gif" width="13" height="11" border="0">Internal Exam Grievance Form</a>
					                  	</c:if>
	                    				
	                    				<%--Mary:- Alternative code for MarksCard  --%>
					                    <c:if test="${linkForCjc==false}">
											<a href="StudentLoginAction.do?method=initMarksCard" class="menuLink"><img src="images/smallArrow.gif" width="13" height="11" border="0">Marks Card</a>
							            </c:if>
							            
							            <c:if test="${showGievanceForm}">
											<a href="StudentLoginAction.do?method=initGrievance" class="menuLink"><img src="images/smallArrow.gif" width="13" height="11" border="0">Exam Grievance Form</a>
							            </c:if>
			            
							            <c:if test="${showGievanceForm}">
											<a href="StudentLoginAction.do?method=initGrievanceStatus" class="menuLink"><img src="images/smallArrow.gif" width="13" height="11" border="0">Grievance Status</a>
							            </c:if>
			             
			             				<c:if test="${showConsolidateMarksCard}">
			                    			<a href="StudentLoginAction.do?method=certificateMarksCardNew" class="menuLink"><img src="images/smallArrow.gif" width="13" height="11" border="0">Consolidate Marks Card</a>
			            				</c:if>
	 				
	 									<c:if test="${showLinks}">                  						
	                  						<c:if test="${showHallTicket}">	                 
							                    <c:if test="${isHallTicketBlockedStudent && hallTicketBlockReason!= null && hallTicketBlockReason!= ''}">
								                   <a href="StudentLoginAction.do?method=studentLoginHallTicketBlock&supHallTicket=false" class="menuLink" style="text-decoration: blink; color: red"><img src="images/smallArrow.gif" width="13" height="11" border="0">Download HallTicket</a> 
							                    </c:if>
							                    
							                    <c:if test="${!isHallTicketBlockedStudent}">
													<c:choose>	
														<c:when test="${agreement == null}">                    
									                   		<a href="StudentLoginAction.do?method=getHallTicket" class="menuLink" style="text-decoration: blink;color: red"><img src="images/smallArrow.gif" width="13" height="11" border="0">Download HallTicket</a>
								                   		</c:when>
								                   		<c:otherwise>
									                   		<a href="StudentLoginAction.do?method=studentLoginHallTicketAgreement" class="menuLink" style="text-decoration: blink; color: red"><img src="images/smallArrow.gif" width="13" height="11" border="0">Download HallTicket</a> 
								                   		</c:otherwise>
								                   	</c:choose>
							                   	 </c:if>                  						
	                  						</c:if>
	                  
						                    <logic:notEmpty name="supHallTicketList" scope="session">
												<logic:iterate id="to" name="supHallTicketList" scope="session" type="com.kp.cms.to.exam.ShowMarksCardTO">
						                   			
						                   			<c:if test="${to.showSupMC}">								                
														<c:if test="${to.supMCBlockedStudent && to.supMCBlockReason!= null && to.supMCBlockReason!= ''}">
										                   	<a href="StudentLoginAction.do?method=studentLoginHallTicketBlock&supHallTicket=true&count=<%= to.getCnt()%>" class="menuLink" style="text-decoration: blink;color: red"><img src="images/smallArrow.gif" width="13" height="11" border="0">Download Supplementary HallTicket </a>
									                    </c:if>
									                    <c:if test="${!to.supMCBlockedStudent}">
															<c:choose>	
																<c:when test="${to.supHallTicketagreement != null && to.supHallTicketagreement!=''}">                    
											                   		<a href="StudentLoginAction.do?method=studentLoginHallTicketSuplementaryAgreement&count=<%= to.getCnt()%>" class="menuLink" style="text-decoration: blink;color: red"><img src="images/smallArrow.gif" width="13" height="11" border="0">Download Supplementary HallTicket </a> 
										                   		</c:when>
										                   		<c:otherwise>
											                   		<a href="StudentLoginAction.do?method=getSuppHallTicket&count=<%= to.getCnt()%>" class="menuLink" style="text-decoration: blink;color: red"><img src="images/smallArrow.gif" width="13" height="11" border="0">Download Supplementary HallTicket </a> 
										                   		</c:otherwise>
										                   	</c:choose>
									                   	</c:if>								               
						                 		 	</c:if>
						                 		 	
						                   		</logic:iterate>
											</logic:notEmpty>
	                   
	                   						<logic:notEmpty name="supRevalApplnList" scope="session">
	                   							<logic:iterate id="to" name="supRevalApplnList" scope="session" type="com.kp.cms.to.exam.ShowMarksCardTO">
	
													<c:if test="${to.showSupRevalAppln}">
														<c:if test="${!to.supMCBlockedStudent}">
															<c:if test="${programType=='UG'}">
																<a href="newSupplementaryImpApp.do?method=checkRevaluationApplicationForSuppl&suppExamId=<%=to.getSupMCexamID()%>&suppClassId=<%=to.getSupMCClassId()%> &examName=<%=to.getExamName()%>" style="text-decoration: blink;color: red" class="menuLink"><img src="images/smallArrow.gif" width="13" height="11" border="0">
																Application for Revaluation/Scrutiny:<bean:write property="examName" name="to" /></a>
															</c:if>
																	
															<c:if test="${programType=='PG'}">
																<a href="newSupplementaryImpApp.do?method=checkRevaluationApplicationForSuppl&suppExamId=<%=to.getSupMCexamID()%>&suppClassId=<%=to.getSupMCClassId()%>&examName=<%=to.getExamName()%>" 
																   style="text-decoration: blink;color: red" class="menuLink"><img src="images/smallArrow.gif" width="13" height="11" border="0">
																   Application for Challenge Valuation:<bean:write property="examName" name="to" /> </a>
															</c:if>												
														 </c:if>
													</c:if>
					
	                   							</logic:iterate>
	                   						</logic:notEmpty>
	                   
						                   	<c:if test="${showOverallReport}">
							                	<a href="CiaOverAllReport.do?method=getStudentOverAllMarksDetails" class="menuLink"><img src="images/smallArrow.gif" width="13" height="11" border="0">CIA Overall</a> 							              
							              	</c:if>
		              					</c:if>
		              					
		              					<c:if test="${linkForCjc==false}">
	                    					<c:if test="${linkForConsolidateMarksCard==true}">
							   					<a href="StudentLoginAction.do?method=certificateMarksCard" class="menuLink"><img src="images/smallArrow.gif" width="13" height="11" border="0">Print Consolidate Marks Card </a>
					    					</c:if>
					 					</c:if>
		             					
		             					<% 
		             						if(session.getAttribute("honoursLink").toString() != null && 
		             						   session.getAttribute("honoursLink").toString().equalsIgnoreCase("true")) { 
		             					%>
		                 						
		                 						<a href="honoursCourseEntry.do?method=getHonoursCourse" class="menuLink"><img src="images/smallArrow.gif" width="13" height="11" border="0">Apply For Honours Program </a> 
		                 				<%} %>
		                				
		                				<%-- start by giri--%>             
		                 				<c:if test="${showCertCourse}">
					                 		<%
					                 			String method="StudentLoginAction.do?method=certificateCourseStatus&studentId="+session.getAttribute("studentId")+"&courseId="+session.getAttribute("courseId");
					                 		%>
	         								<a href="<%=method %>" class="menuLink"><img src="images/smallArrow.gif" width="13" height="11" border="0">Certificate Course Status &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src="images/supportRequestnew_icon1.png" width="25" height="15" class="blinkImage"></a>
	       	 							</c:if>
	       	 			
	       	 			 				<c:if test="${linkForCjc==false}">
											<!--<a href="StudentLoginAction.do?method=initInternalMarks" class="menuLink"><img src="images/smallArrow.gif" width="13" height="11" border="0">Internal Mark</a>-->
			            				</c:if>
			            				
		               					<%--end by giri --%>             
		             				</div>
	            					
	            					
	            					<div class="collapsed">
	             	
	             						<span>Student Details</span>
	            							
	            							<c:if test="${studentEdit==true}">
											<a href="StudentLoginAction.do?method=personalDataEdit"  class="menuLink"><img src="images/smallArrow.gif" width="13" height="11" border="0">Edit Student Detail </a>
							            </c:if>
	            							<c:if test="${studentEdit==true}">
											<a href="StudentLoginAction.do?method=editStudentPhotoInLogin"  class="menuLink"><img src="images/smallArrow.gif" width="13" height="11" border="0">Edit Student Photo </a>
							            </c:if>
	            					</div>
	            					
	            					<div class="collapsed">
	            					     <span>Time Table</span>
	            					        <a href="StudentLoginAction.do?method=getStudentTimeTable" class="menuLink"><img src="images/smallArrow.gif" width="13" height="11" border="0">Time Table </a>
	            					</div>
	                  				<!--<a href="hostelStudentViewMessage.do?method=initHostelStudentViewMessage" class="menuLink">Student View Message </a>-->
	                   
	                   				<c:if test="${linkForCjc}">
	                  					<div class="collapsed">
	                   						<span>Feedback</span>
	                    						<a href="studentFeedBack.do?method=initStudentFeedback" class="menuLink"><img src="images/smallArrow.gif" width="13" height="11" border="0"><bean:message key="knowledgepro.studentFeedback.link"/></a>
	                 					</div>
	                 				</c:if> 
						              
						            <!--<c:if test="${linkForCjc==false}">
					                  		<div class="collapsed">
					                   			<span>Feedback</span>
					                    		<span>Support</span>
					                 				<a href="studentFeedBack.do?method=initStudentFeedback" class="menuLink"><img src="images/smallArrow.gif" width="13" height="11" border="0"><bean:message key="knowledgepro.studentFeedback.link"/></a>
					                 				<a href="studentSupportRequest.do?method=initStudentSupportRequest" class="menuLink"><img src="images/smallArrow.gif" width="13" height="11" border="0">Support Request </a>
					                 		</div>
					                 	</c:if>-->
					                <!--<c:if test="${showCertCourse}">
							            	<a href="StudentCertificateCourse.do?method=getCertificateCourses" class="menuLink">Certificate Course </a> 
						               	</c:if>-->
						               
	                 				<c:if test="${linkForCertificateCourse}">
	      				  				<div class="collapsed">
	      				 	 				<span>CertificateCourse</span>
	      				   						<a href="NewStudentCertificateCourse.do?method=initCertificateCourseForStudentLogin" class="navmenuRed" style="text-decoration: blink"><img src="images/smallArrow.gif" width="13" height="11" border="0">Apply for Certificate Course</a>
										</div>
									</c:if>
									
									<c:if test="${linkForCjc==false}">
										<c:if test="${sapResultLinks==true}">
											<div class="collapsed">
												<span>SAP</span>
													<!-- <a href="https://www.indiaeduservices.com/controlcenter/login.php" class="menuLink" target="_blank" ><img src="images/smallArrow.gif" width="13" height="11" border="0">SAP Exam</a>-->							
	                    			 				<a href="StudentLoginAction.do?method=displaySAPpResuls"  class="menuLink" ><img src="images/smallArrow.gif" width="13" height="11" border="0">SAP Results <img src="images/supportRequestnew_icon1.png" width="30" height="11" class="blinkImage"></a>
											</div>
										</c:if>
									</c:if>
							
									<%-- <c:if test="${linkForCjc==false}">
										<div class="collapsed">
											<span>Apply Online</span>						
											<c:if test="${showRevApp}">
												<c:if test="${programType=='UG'}">
													<a href="newSupplementaryImpApp.do?method=checkRevaluationApplicationForStudentLogin" style="text-decoration: blink;color: red" class="menuLink"><img src="images/smallArrow.gif" width="13" height="11" border="0">
													Application for Revaluation/Scrutiny</a>
												</c:if>
												
												<c:if test="${programType=='PG'}">
													<a href="newSupplementaryImpApp.do?method=checkRevaluationApplicationForStudentLogin" style="text-decoration: blink;color: red" class="menuLink"><img src="images/smallArrow.gif" width="13" height="11" border="0">						
													Application for Challenge Valuation </a>
												</c:if>
											</c:if>
												
											<c:if test="${(showRegApp ==true && isBlockedStudentRegularApp==false) || (isSupplyImpAppDisplay==true) || (isInternalRedoImpAppDisplay==true)}">
												<c:if test="${showRegApp}">
													<c:if test="${!isBlockedStudentRegularApp}">
														<a href="newSupplementaryImpApp.do?method=checkRegularApplicationForStudentLogin" style="text-decoration: blink;color: red" class="menuLink"><img src="images/smallArrow.gif" width="13" height="11" border="0">Examination Registration</a>
													</c:if>
												</c:if>						
						
												<c:if test="${isSupplyImpAppDisplay==true}">
													<%int i=0 ;%>
													<logic:iterate id="examId" name="examIdList" scope="session">
														<%i++; %>
														<a href="newSupplementaryImpApp.do?method=checkSupplementaryImpApplicationForStudentLogin&examId=<%=examId %>" class="menuLink"><img src="images/smallArrow.gif" width="13" height="11" border="0">Supplementary Improvement Application <%=i %></a>
														
														<!--<a href="certificateRequest.do?method=initCertificateRequest" class="menuLink"><img src="images/smallArrow.gif" width="13" height="11" border="0">Certificate Request</a>
															<a href="certificateRequest.do?method=getCertificateStatus" class="menuLink"><img src="images/smallArrow.gif" width="13" height="11" border="0">Certificate Status</a>
														-->
													</logic:iterate>
												</c:if>
						
												<c:if test="${isInternalRedoImpAppDisplay==true}">
													<%int i=0 ;%>
													<logic:iterate id="examId" name="internalRedoExamIds" scope="session">
														<%i++; %>
														<a href="newSupplementaryImpApp.do?method=checkInternalRedoImpApplicationForStudentLogin&examId=<%=examId %>" class="menuLink"><img src="images/smallArrow.gif" width="13" height="11" border="0">Internal Redo Application <%=i %></a>
													</logic:iterate>
												</c:if>
						
												<c:if test="${hostelLinks==true}">
													<a href="hostelLeave.do?method=initStudentHostelLeave" class="menuLink"><img src="images/smallArrow.gif" width="13" height="11" border="0">Hostel Leave Application</a>
													<a href="hostelLeave.do?method=viewStudentLeaves" class="menuLink"><img src="images/smallArrow.gif" width="13" height="11" border="0">Hostel Leave Status</a>
												</c:if>
												
												<c:if test="${linkForRepeatExamsFeePayment==true}">
	                           						<a href="MidSemRepeatExamApplication.do?method=initRepeatExamFeePayment" class="menuLink" style="color: red"><img src="images/smallArrow.gif" width="13" height="11" border="0">Mid Semester Repeat Exam Fees Payment</a>
				             					</c:if>
	                           
	                           					<c:if test="${linkForRepeatExamsApplication==true}">
	                           						<a href="MidSemRepeatExamApplication.do?method=initRepeatExamApplication" class="menuLink" style="color: red"><img src="images/smallArrow.gif" width="13" height="11" border="0">Mid Sem Repeat Exam Application Form</a>
				              					</c:if>
					            
				            					<c:if test="${linkFordownloadHallTicket==true}">
	                           						<a href="MidSemRepeatExamApplication.do?method=initRepeatExamFeePayment" class="menuLink" style="color: red"><img src="images/smallArrow.gif" width="13" height="11" border="0">Mid Sem Repeat Exam Download Hall Ticket</a>
				             					</c:if>
							
												<!--<c:if test="${linkForCjc==false}">
													<a href="examRegistrationDetails.do?method=initExamRegistrationDetails" class="menuLink" style="text-decoration: blink"> <img src="images/smallArrow.gif" width="13" height="11" border="0">SAP Exam Registration </a>
												</c:if>
												<c:if test="${linkForHolisticExam}">
											   		<a href="onlineExamSuppApplication.do?method=initOnlineSuppExam" class="navmenu"><img src="images/smallArrow.gif" width="13" height="11" border="0" align="absmiddle">HED/EVS Supplementary Application</a>
											  	</c:if>-->
											  	
	            					        	
	            							
											</c:if>
						    			</div>
								    </c:if> --%>
	
									<c:if test="${linkForPrintChallan}">
										<div class="collapsed">
	      				  					<span>Print Challan</span>					
		                  					<a href="StudentLoginAction.do?method=initFeeChallanPrint" class="menuLink"><img src="images/smallArrow.gif" width="13" height="11" border="0">Print Fee Challan </a> 
		              					</div>
		              				</c:if>
	                  
		              				<c:if test="${linkForCjc==false}">
			          					<c:if test="${linkForOnlineReciepts}">
			          						<div class="collapsed">
			          							<span>Online Reciepts</span>
		                  						<a href="StudentLoginAction.do?method=initOnlineRecieptsForStudentLogin" class="menuLink"><img src="images/smallArrow.gif" width="13" height="11" border="0">Online Reciepts</a> 
		              						</div>
		              					</c:if>
		              				</c:if>
					
									<!--
										<c:if test="${linkForCjc==false}">
											<div class="collapsed">
					      				  		<span>Data Bank</span>
							                   		<a href="http://repository.christuniversity.in/" class="menuLink" target="_blank"><img src="images/smallArrow.gif" width="13" height="11" border="0">Data Bank </a>
							            	</div>
							         	</c:if>
							        -->
			         
							        <c:if test="${studentFacultyFeedback}">
						            	<div class="collapsed">
											<span>Faculty Eval</span>
											<a href="EvaluationStudentFeedback.do?method=initEvaluationStudentFeedback" class="menuLink" style="text-decoration: blink"><img src="images/smallArrow.gif" width="13" height="11" border="0">Faculty Evaluation</a>
				                	 		<!--
				                	 			<c:if test="${linkForCjc==false}">
				                	 				<a href="http://courses.christuniversity.in/login/index.php" class="menuLink" target="_blank"  style="text-decoration: blink"><img src="images/smallArrow.gif" width="13" height="11" border="0">HED/EVS Exam</a>
				                	 		 	</c:if>
				                			 -->
					                	</div>
							          </c:if> 
							          
					                  <!--<c:if test="${linkForCjc==false}">
					                  	 <div class="collapsed">
					      				  <span>My Course</span>
					                   			
						              		
							                <a href="http://courses.christuniversity.in/login/index.php" class="menuLink" target="_blank"><img src="images/smallArrow.gif" width="13" height="11" border="0">My Course</a>
							               </div>
							          </c:if>
							         
					                 -->
	                 
	                 			</div>
	               			</td>
	               		</tr>
	             	</table>            
				</td>           
			</tr>     
		</table>
	</body>
</html>