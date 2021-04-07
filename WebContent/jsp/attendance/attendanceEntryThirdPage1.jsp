<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<script type="text/javascript">
function submitData() {
	document.attendanceEntryForm.submit();
}
function backPage() {
	document.location.href="AttendanceEntry.do?method=loadAttendanceEntrySecondPage";
}

function backToFirstPage() {
	document.location.href="AttendanceEntry.do?method=loadAttendanceEntryFirstPage";
}
function validateCheckBox() {
	var inputs = document.getElementsByTagName("input");
    var inputObj;
    var checkBoxselectedCount = 0;
    for(var count1 = 0;count1<inputs.length;count1++) {
	    inputObj = inputs[count1];
	    var type = inputObj.getAttribute("type");
	   	if (type == 'checkbox') {
	   		if(inputObj.checked){
	   			checkBoxselectedCount++;
		   	}
		}
    }
        document.getElementById("err").innerHTML = "Number of Absentees is:"+checkBoxselectedCount;
            
}

function showInfo(teachername,className,subjectName){

	var tab1 = '<p>Class &nbsp; &nbsp;:&nbsp;'+className+'<br>Teacher:&nbsp;'+teachername+'<br>Subject&nbsp;:&nbsp;'+subjectName+'</p>';
	new Messi(''+tab1+'', {title: '', modal: true, titleClass: 'success', buttons: [{id: 0, label: 'Close', val: 'X'}]});

}

</script>

<style> 
label input {
  display: none;/* <-- hide the default checkbox */
}
label span {/* <-- style the artificial checkbox */
  height: 10px;
  width: 10px;
  border: 1px solid grey;
  display: inline-block;
  
}
.table-striped > tbody > tr:nth-child(2n+1) > td {
    background-color: #03a9f440;
}
td{

padding: 30.5px;
padding-bottom: 2.13em;
border-spacing:5em; 
margin-bottom:5em; 
}
tr{
height: 86px;

}

</style>
<html:form action="/AttendanceEntry" method="post">
<html:hidden property="formName" value="attendanceEntryForm" />
<html:hidden property="method" styleId="method" value="saveAttendance"/>
<html:hidden property="pageType" value="1"/>

<div class="panel-body">
                        <div class="panel panel-primary">
                         <div class="panel-heading">
                         <h1 class="panel-title">Attendance Entry</h1>
                         
                         </div>
     
                         </div>

       
        <div class="form-group">
                 <div class="col-sm-10">
     
        <div align="right"  style="color:red"><span class='MandatoryMark'><bean:message
					key="knowledgepro.mandatoryfields" /></span></div>
					<div class="col-sm-12" style="color:blue;">
			<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
				<FONT color="green"><html:messages id="msg" property="messages" message="true">
					<c:out value="${msg}" escapeXml="false"></c:out><br>
					</html:messages> </FONT></div>
		Mark only absentees 			
		  </div>
     </div>
     </div>
       <div class="form-group">
                 <div class="col-sm-12">
        <div class="col-sm-12"  style="border-style: solid;border-color: #29953b">
               <div class="form-group">
                 <div class="col-sm-12">
                  		 <div class="col-sm-2">
                  		 <div id="teacherdiv" ><bean:message key="knowledgepro.attendanceentry.teacher"/>:</div>
                  </div>
                  <div class="col-sm-2">
						<bean:write name="attendanceEntryForm" property="attendanceTeacher"/>
                  </div>
                     <div class="col-sm-2"><bean:message key="knowledgepro.attendanceentry.date"/>:</div>
                  <div class="col-sm-2">
                 
                      	<bean:write name="attendanceEntryForm" property="attendancedate"/>
                    </div>
                   <div class="col-sm-2"><bean:message key="knowledgepro.fee.academicyear"/>:</div>
		    <div class="col-sm-2">       
		                    <bean:write name="attendanceEntryForm" property="acaYear"/>       
		        	</div>
               </div>
                </div>
                   <div class="form-group">
                 <div class="col-sm-12">
               
                 <div class="col-sm-2">
                	 	 <div id="classsdiv" ><bean:message key="knowledgepro.attendanceentry.class"/>:</div>
                  </div>
                   <div class="col-sm-2">
                  	 
                  	 <c:out value="${attendanceEntryForm.attendanceClass}"/> 
                 </div>
                  <div class="col-sm-2">
                
                    	<div id="subjectdiv"><bean:message key="knowledgepro.attendanceentry.subject"/>:</div>
                  </div>
                  <div class="col-sm-2">
                    	<bean:write name="attendanceEntryForm" property="attendanceSubject"/> 
                </div>
                  <div class="col-sm-2">
                  <bean:message key="knowledgepro.attendanceentry.type"/>:</div>
                  <div class="col-sm-2">
                  	<bean:write name="attendanceEntryForm" property="attenType"/>
                   </div>
               </div>
               </div>
                  
               
                <div class="form-group">
                 <div class="col-sm-12">
                  <div class="col-sm-2">
                 
                  	    <div id="perioddiv" ><bean:message key="knowledgepro.attendanceentry.period"/>:</div>
                   </div>
                  <div class="col-sm-2">
						<bean:write name="attendanceEntryForm" property="attendancePeriod"/>
                  </div>
                 <div class="col-sm-2">
                <%-- <bean:message key="knowledgepro.attendanceentry.hoursheld"/>: --%></div>
                 <div class="col-sm-2">
                 <%-- 	<bean:write name="attendanceEntryForm" property="hoursHeld" /> --%>
                  </div>
             
              <div class="col-sm-2" style="display: none;">
                <div class="col-sm-2">
                    	<div id="activitydiv" ><bean:message key="knowledgepro.attendanceentry.activitytype"/>:</div>
                 </div>
                  <div class="col-sm-2">
                  	<bean:write name="attendanceEntryForm" property="attendanceActivity"/>
                 </div>
         
     </div>
     
     </div>
     </div>
     </div>
     </div>
     </div>         
         <div class="form-group">
                 <div class="col-sm-12">
         <div class="col-sm-8" style=" color:blue;">Click on the cell to view details</div>
        <div class="col-sm-4">
         <label ><span style="background-color: green;"></span><input type='checkbox' disabled="disabled"></label> is for Present
        &nbsp; &nbsp;
        <label ><span style="background-color: red;"></span><input type='checkbox' disabled="disabled"></label> is for Absent
       &nbsp; &nbsp;
     
       <label ><span style="background-color: #EEE8AA;"></span><input type='checkbox' disabled="disabled"></label> <b>is not Marked</b>
       &nbsp; &nbsp;
        <br/>
        <br/>
        </div>
     </div>
     </div>
     		<logic:equal value="true" property="isMobileAccess" name="attendanceEntryForm">
      			<div class="col-sm-12"  style="border-style: solid;border-color: #29953b;">
     				<div class="col-sm-12">
                   		<div class="table-responsive">
                  			<table class="table table-bordered " width="100%">
                      			<tr style="background-color: #618685; color:white;">
                       				<td align="center">
										<div align="center" style="width:35px;"><bean:message key="knowledgepro.slno" /></div>
					 				</td>
                        			<td align="center" style="width:395px; "><bean:message key="knowledgepro.attendance.studentname"/></td>
                        			<td align="center" style="width:158px;">Roll No</td>
			                        <logic:notEmpty name="attendanceEntryForm" property="periodsList">
	            						<c:forEach items="${attendanceEntryForm.periodsList}" var="periods">
		            						<c:if test="${periods==attendanceEntryForm.periodName}">
												<td  width="" align="center"><c:out value="${periods}"/></td>
											</c:if>
	            						</c:forEach>
	            					</logic:notEmpty>
                      			</tr>
                      			<tr>
                      				<td colspan="3">
                      					<div class="col-sm-12">
                    						<div class="table-responsive">
                  								<table class="table table-bordered table-striped" width="100%" >
                      								<nested:iterate id="student" property="studentList" name="attendanceEntryForm" indexId="count2">
					   									<c:choose>
															<c:when test="${count2%2 == 0}">
																<tr style="background-color:#03a9f440;">
															</c:when>
															<c:otherwise>
																<tr>
															</c:otherwise>
					 									</c:choose>
					 									<td align="center" ><c:out value="${count2+1}"/></td>
                        								<td align="center" ><nested:write name="student" property="studentName"/>
                        									<div>
                        										<br>
																<c:forEach items="${student.periodTOs}" var="per">
																	<bean:write name="per" property="periodName"/>
																	<c:choose>
	                													<c:when test="${per.hoursTaken == 'true' }">
									  										<c:choose>
									             								<c:when test="${per.tempChecked == 'true'}">
									               									<label>
									               										<span style="background-color: green;"></span>
									               										<input type='checkbox' disabled="disabled">
									               									</label>
									               								</c:when>
																                <c:otherwise>
																                     <label ><span style="background-color: red;"></span><input type='checkbox' disabled="disabled"></label>
																                </c:otherwise>
									                  						</c:choose>
	               														</c:when>
		               													<c:otherwise>
			               													<label>
			               														<span style="background-color: #EEE8AA"></span>
			               														<input type='checkbox' disabled="disabled">
			               													</label>
			               												</c:otherwise>
		               												</c:choose>
				            									</c:forEach>
															</div>
                        								</td>
								                        <%-- <td align="center">
                        										<nested:write name="student" property="registerNo"/>
                        									</td> --%>
                        									<td  align="center">
                        										<nested:write name="student" property="rollNo"/>
                       										 </td>
                        							</nested:iterate>
                       							</table>
                      						</div>
                      					</div>
                      				</td>
                     				<logic:notEmpty name="attendanceEntryForm" property="studentsList">
	            						<nested:iterate id="lists" name="attendanceEntryForm" property="studentsList" indexId="count">
	            							<c:if test="${count==attendanceEntryForm.periodCount}">
                      							<td>
                      								<div class="col-sm-12">
                      									<div class="table-responsive">
										                  	<table class="table table-bordered table-striped" width="100%" >
										                      	<nested:iterate id="student"  name="lists" indexId="count1">
										                      		<c:if test="${student.isCurrent=='true'}">
	                       												<c:choose>
																			<c:when test="${count1%2 == 0}">
																				<tr style="background-color:#03a9f440;">
																			</c:when>
																			<c:otherwise>
																				<tr>
																			</c:otherwise>
																		</c:choose>
	                        											<c:choose>
												                       		<c:when test="${student.isTaken == true }">
												                       			<c:choose>
												                       				<c:when test="${student.tempChecked == true}">
												                       					<td align="center" onclick="showInfo('<nested:write name="student" property="teacherName"/>','<nested:write name="student" property="className"/>','<nested:write name="student" property="subjectName"/>');">
												                      						<label><span style="background-color: green;"></span><input type='checkbox' disabled="disabled"></label>
												                       					</td>
												                        			</c:when>
													                        		<c:otherwise>
													                        			<td align="center" onclick="showInfo('<nested:write name="student" property="teacherName"/>','<nested:write name="student" property="className"/>','<nested:write name="student" property="subjectName"/>');">
												                       						<label><span style="background-color: red;"></span><input type='checkbox' disabled="disabled"></label>
												                        				</td>
												                        			</c:otherwise>
												                        		</c:choose>
												                      		</c:when>
		                        											<c:otherwise>
				                         										<c:choose>
				                       												<c:when test="${student.isCurrent == true }">
				                         												<td align="center">
																	                        <input
																								type="hidden"
																								name="studentsList[<c:out value='${count}'/>][<c:out value='${count1}'/>].tempChecked"
																								id="hidden_<c:out value='${count}'/>_<c:out value='${count1}'/>"
																								value="<nested:write name='student' property='tempChecked'/>" />
																	                        <c:choose>
																		                        <c:when test="${student.coCurricularLeavePresent == true || student.studentLeave == true}">
																		                        <input
																									type="checkbox"
																									name="studentsList[<c:out value='${count}'/>][<c:out value='${count1}'/>].checked"
																									id="<c:out value='${count}'/>_<c:out value='${count1}'/>" disabled="disabled" />
																		                        </c:when>
																		                        <c:otherwise>
																		                        	<input
																									type="checkbox"
																									name="studentsList[<c:out value='${count}'/>][<c:out value='${count1}'/>].checked"
																									id="<c:out value='${count}'/>_<c:out value='${count1}'/>" onclick="validateCheckBox()"/>
																		                        </c:otherwise>
																	                        </c:choose>
																							<script type="text/javascript">
																								var studentId = document.getElementById("hidden_<c:out value='${count}'/>_<c:out value='${count1}'/>").value;
																								if(studentId == "true") {
																									document.getElementById("<c:out value='${count}'/>_<c:out value='${count1}'/>").checked = true;
																								}		
																							</script>
								   														</td>
								   													</c:when>
			                        											</c:choose>
			                       											</c:otherwise>
			                        									</c:choose>
			                     									</c:if>
			                      								</nested:iterate>
			                      							</table>
			                      						</div>
			                      					</div>
			                      				</td>
			                      			</c:if>
			                     		</nested:iterate>
				            		</logic:notEmpty>
								</tr>
							</table> 
						</div>
     				</div>
     			</div>
      		</logic:equal>
      <logic:equal value="false" property="isMobileAccess" name="attendanceEntryForm">
      <div class="col-sm-12"  style="border-style: solid;border-color: #29953b;">
     <div class="col-sm-12">
                   <div class="table-responsive">
                  <table class="table table-bordered " width="100%">
                      <tr  style="background-color: #618685; color:white;">
                       <td  align="center" style="width:107px;">
						<div align="center"><bean:message key="knowledgepro.slno" />
					</div>
					 </td>
                        <td align="center" style="width:517px; "><bean:message key="knowledgepro.attendance.studentname"/></td>
                        <!-- <td  align="center" style="width:230px;">
                        		Reg No
                        </td> -->
                        <td   width="150" style="width:87px;">
                        		Roll No
                        </td>
                       <logic:notEmpty name="attendanceEntryForm" property="periodsList">
	            		<c:forEach items="${attendanceEntryForm.periodsList}" var="periods">
	            		<c:if test="${periods==attendanceEntryForm.periodName}">
											<td  width="" align="center"><c:out value="${periods}"/></td>
										</c:if>
	            		</c:forEach>
	            		</logic:notEmpty>
                      </tr>
                      <tr>
                      <td colspan="3">
                      <div class="col-sm-12">
                    <div class="table-responsive">
                  <table class="table table-bordered table-striped" width="100%" >
                      <nested:iterate id="student" property="studentList" name="attendanceEntryForm" indexId="count2">
					   <c:choose>
								<c:when test="${count2%2 == 0}">
									<tr style="background-color:#03a9f440;">
								</c:when>
									<c:otherwise>
									<tr>
								</c:otherwise>
					 </c:choose>
					 <td  align="center" ><c:out value="${count2+1}"/></td>
                        <td  align="center" ><nested:write name="student" property="studentName"/>
                        <div>
                        									<br>
															<c:forEach items="${student.periodTOs}" var="per">
																<bean:write name="per" property="periodName"/>
																<c:choose>
                													<c:when test="${per.hoursTaken == 'true' }">
								  										<c:choose>
								             								<c:when test="${per.tempChecked == 'true'}">
								               									<label>
								               										<span style="background-color: green;"></span>
								               										<input type='checkbox' disabled="disabled">
								               									</label>
								               								</c:when>
															                <c:otherwise>
															                     <label ><span style="background-color: red;"></span><input type='checkbox' disabled="disabled"></label>
															                </c:otherwise>
								                  						</c:choose>
               														</c:when>
	               													<c:otherwise>
		               													<label>
		               														<span style="background-color: #EEE8AA"></span>
		               														<input type='checkbox' disabled="disabled">
		               													</label>
		               												</c:otherwise>
		               											</c:choose>
				            								</c:forEach>
														</div>
                        </td>
                        <%-- <td align="center">
                        		<nested:write name="student" property="registerNo"/>
                        </td> --%>
                        <td  align="center">
                        		<nested:write name="student" property="rollNo"/>
                        </td>
                     </nested:iterate>
                       </table>
                      </div>
                      </div>
                      </td>
                     <logic:notEmpty name="attendanceEntryForm" property="studentsList">
	            		<nested:iterate id="lists" name="attendanceEntryForm" property="studentsList" indexId="count">
	            			<c:if test="${count==attendanceEntryForm.periodCount}">
                      <td>
                      <div class="col-sm-12">
                      <div class="table-responsive">
                  <table class="table table-bordered table-striped" width="100%" >
                      <nested:iterate id="student"  name="lists" indexId="count1">
                      <c:if test="${student.isCurrent=='true'}">
                       <c:choose>
								<c:when test="${count1%2 == 0}">
									<tr style="background-color:#03a9f440;">
								</c:when>
									<c:otherwise>
									<tr>
								</c:otherwise>
					 </c:choose>
                        <c:choose>
                       <c:when test="${student.isTaken == true }">
                       <c:choose>
                       <c:when test="${student.tempChecked == true}">
                       <td align="center" onclick="showInfo('<nested:write name="student" property="teacherName"/>','<nested:write name="student" property="className"/>','<nested:write name="student" property="subjectName"/>');">
                      <label ><span style="background-color: green;"></span><input type='checkbox' disabled="disabled"></label>
                      <%-- 	<img width="15" height="15" title="" onclick="showInfo('<nested:write name="student" property="teacherName"/>','<nested:write name="student" property="className"/>','<nested:write name="student" property="subjectName"/>');" style="cursor:pointer" src="images/questionMark.jpg">--%>
                       </td>
                        </c:when>
                        <c:otherwise>
                        <td align="center" onclick="showInfo('<nested:write name="student" property="teacherName"/>','<nested:write name="student" property="className"/>','<nested:write name="student" property="subjectName"/>');">
                       <label ><span style="background-color: red;"></span><input type='checkbox' disabled="disabled"></label>
                       <%-- <img width="15" height="15" title=""  onclick="showInfo('<nested:write name="student" property="teacherName"/>','<nested:write name="student" property="className"/>','<nested:write name="student" property="subjectName"/>');" style="cursor:pointer" src="images/questionMark.jpg">--%>
                        </td>
                        </c:otherwise>
                        </c:choose>
                      </c:when>
                        <c:otherwise>
                         <c:choose>
                       <c:when test="${student.isCurrent == true }">
                         <td align="center">
                        <input
							type="hidden"
							name="studentsList[<c:out value='${count}'/>][<c:out value='${count1}'/>].tempChecked"
							id="hidden_<c:out value='${count}'/>_<c:out value='${count1}'/>"
							value="<nested:write name='student' property='tempChecked'/>" />
                        <c:choose>
                        <c:when test="${student.coCurricularLeavePresent == true || student.studentLeave == true}">
                        <input
							type="checkbox"
							name="studentsList[<c:out value='${count}'/>][<c:out value='${count1}'/>].checked"
							id="<c:out value='${count}'/>_<c:out value='${count1}'/>" disabled="disabled" />
                        </c:when>
                        <c:otherwise>
                        	<input
							type="checkbox"
							name="studentsList[<c:out value='${count}'/>][<c:out value='${count1}'/>].checked"
							id="<c:out value='${count}'/>_<c:out value='${count1}'/>" onclick="validateCheckBox()"/>
                        </c:otherwise>
                        </c:choose>
						<script type="text/javascript">
							var studentId = document.getElementById("hidden_<c:out value='${count}'/>_<c:out value='${count1}'/>").value;
							if(studentId == "true") {
								document.getElementById("<c:out value='${count}'/>_<c:out value='${count1}'/>").checked = true;
							}		
						</script>
					   </td>
					   </c:when>
                        </c:choose>
                       </c:otherwise>
                        </c:choose>
                     </c:if>
                      </nested:iterate>
                      </table>
                      </div>
                      </div>
                      </td>
                      </c:if>
                     </nested:iterate>
	            		</logic:notEmpty>
                          </tr>
                  </table> 
                  </div>
      </div>
      </div>
      </logic:equal>
    </div>
            <logic:equal value="2" name="attendanceEntryForm" property="attendanceTypeId"> 
           <div class="form-group">
           <div class="row ">
            <div class="col-sm-12 text-center offset-sm-3">
             <table class="table table-bordered table-striped" style="width: 50%;margin-left: 25%;" >
            	<logic:iterate id="teacher" name="attendanceEntryForm" property="attCoTeachers">
            	<tr style="height: 20%">
            	<td>
					<bean:write name="teacher" property="value"/>
				</td>
				<td>	
					<input type="checkbox" name="coAtteachers" value='<bean:write name="teacher" property="key"/>' />
				</td>			
				</logic:iterate>
				</table>
				</div>
            </div>
            </div>
             </logic:equal> 
            
            
          <div class="form-group">
            <div class="col-sm-12 text-center">
           <div class="col-sm-4"> <div id="err"style="color:blue;"></div></div>
            <div class="col-sm-4">
            <html:button property="" styleClass="btn btn-primary" value="Submit" onclick="submitData()"></html:button>
            &nbsp;
            <c:choose>
            <c:when test="${attendanceEntryForm.isSecondPage == true}">
            	<html:button property="" styleClass="btn btn-danger" value="Cancel" onclick="backPage()"></html:button>
            </c:when>
            <c:otherwise>
            	<html:button property="" styleClass="btn btn-danger" value="Cancel" onclick="backToFirstPage()"></html:button>
            </c:otherwise>
            </c:choose>
            </div>
            </div>
         </div>       
</html:form>

<script language="JavaScript" >
	var inputs = document.getElementsByTagName("input");
    var inputObj;
    var checkBoxselectedCount = 0;
    for(var count1 = 0;count1<inputs.length;count1++) {
	    inputObj = inputs[count1];
	    var type = inputObj.getAttribute("type");
	   	if (type == 'checkbox') {
	   		if(inputObj.checked){
	   			checkBoxselectedCount++;
		   	}
		}
    }
        document.getElementById("err").innerHTML = "Number of Absentees is:"+checkBoxselectedCount;
</script>

