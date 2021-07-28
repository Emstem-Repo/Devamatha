<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@page import="java.util.Map,java.util.HashMap"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested" %>

<%@page import="java.util.ArrayList"%><script src="jquery/development-bundle/jquery-1.7.1.js"></script>
<link rel="stylesheet" type="text/css" href="jquery.confirm/jquery.confirm/jquery.confirm.css" />
    <script src="jquery.confirm/jquery.confirm/jquery.confirm.js"></script>
<script type="text/javascript">
var c=0;
function getActivityBatches(activity,count){
	c=count;
	var classes =  document.getElementById("classId").value;
	var selectedClasses = new Array();
	selectedClasses[0] = classes;
	getBatchesByActivity1("subjectMap",activity,selectedClasses,c,updateBatches);
}


function getBatches(subjectId,count) {
	c=count;
	var classes =  document.getElementById("classId").value;
	var selectedClasses = new Array();
	selectedClasses[0] = classes;
	getBatchesBySubject1("subjectMap",subjectId,selectedClasses,count,updateBatches);
}
function updateBatches(req) {
	updateOptionsFromMap(req,c,"- Select -");
}
function addMore(){
	document.getElementById("method").value="addMoreToPeriodDetail";
}
function deleteTimeTable(count){
	
	document.location.href="timeTableForClass.do?method=removeFromPeriodDetail&deleteCount="+count;
}
function cancelAction(){
	document.location.href="timeTableForClass.do?method=goToMainPage";
}
function getActivityByAttendanceType(value,count) {
	c=count;
	getMandatoryFieldsByAttendanceType("activityMap",value,updateMandatory);
}
function updateMandatory(req) {
	updateOptionsFromMap(req,"activity_"+c,"- Select -");	
}
var dep=0;
function searchDeptWise(deptId,count){
	dep=count;
	var destination = document.getElementById("teacher_"+dep);
	for (x1 = destination.options.length; x1 >=0; x1--) {
	destination.options[x1] = null;
	} 
	getTearchersByDepartmentWise(deptId,updateTeachersMapByDep);
}
function updateTeachersMapByDep(req){
	updateTeachersFromMap(req,"teacher_"+dep);
}

function getAttendanceTypeid(subjectId,count) {
	c=count;
	//alert(c);
	getAttendanceTypeIdBySubject(subjectId,updateAttendanceTypeid);
}

function updateAttendanceTypeid(req) {
	var responseObj = req.responseXML.documentElement;
	var value = responseObj.getElementsByTagName("option");
	if(value!=null){
		for ( var I = 0; I < value.length; I++) {
			if(value[I].firstChild!=null){
			var temp = value[I].firstChild.nodeValue;
			//alert(c);
			document.getElementById("attendanceTypeId_"+c).value = temp;
			//alert(temp);
			}
		}
		
	}
	
}

	
</script>
<html:form action="/timeTableForClass" method="post">
	<html:hidden property="method" styleId="method" value="addTimeTableForaPeriod" />
	<html:hidden property="formName" value="timeTableForClassForm" />
	<html:hidden property="pageType" value="1" />
	<html:hidden name="timeTableForClassForm" property="classId" styleId="classId"/>
	<html:hidden name="timeTableForClassForm" property="displayWarning" styleId="displayWarning"/>
	<html:hidden name="timeTableForClassForm" property="displayWarning1" styleId="displayWarning1"/>
	<table width="100%" border="0">
	  <tr>
	   <td><span class="Bredcrumbs"><bean:message key="knowledgepro.timetable.module" /> <span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.timetable.for.class"/> &gt;&gt;</span></span></td>
	  </tr>
	
	  <tr>
	    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
	      <tr>
	        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
	        <td background="images/Tcenter.gif" class="heading_white" ><bean:message key = "knowledgepro.timetable.for.class"/></td>
	        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
	      </tr>
	      <tr>
	
	        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
	        <td valign="top" class="news">
				<div align="right"><FONT color="red"> <span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields"/></span></FONT></div>
					<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
					<FONT color="green"> <html:messages id="msg"
						property="messages" message="true">
						<c:out value="${msg}" escapeXml="false"></c:out>
					<br>
				</html:messages> </FONT></div></td>
	        <td valign="top" background="images/Tright_3_3.gif" ></td>
	      </tr>
	      
	      
	      <tr>
	        <td valign="top" background="images/Tright_03_03.gif"></td>
	        <td class="heading">
	        <table width="100%"  border="0" cellspacing="0" cellpadding="0">
	        	<tr height="25">
	        		<td class="row-odd" align="right"><font style="font-size: 11px">Class:</font></td>
	        		<td class="row-even" align="center"><bean:write name="timeTableForClassForm" property="className"/>  </td>
	        		<td class="row-odd" align="right"><font style="font-size: 11px">Period:</font></td>
	        		<td class="row-even" align="center"><bean:write name="timeTableForClassForm" property="periodName"/>  </td>
	        		<td class="row-odd" align="right"><font style="font-size: 11px">Week:</font></td>
	        		<td class="row-even" align="center"><bean:write name="timeTableForClassForm" property="week"/>  </td>
	        	</tr>
	        </table>
	        </td>
	        <td valign="top" background="images/Tright_3_3.gif" ></td>
	        </tr>
	      <tr>
	        <td valign="top" background="images/Tright_03_03.gif"></td>
	        <td class="heading">
	        &nbsp;
	        </td>
	        <td valign="top" background="images/Tright_3_3.gif" ></td>
	        </tr>
	      
	      <tr>
	        <td valign="top" background="images/Tright_03_03.gif"></td>
	        <td class="heading"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
	          <tr>
	            <td align="center"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
	
	              <tr>
	                <td ><img src="images/01.gif" width="5" height="5" /></td>
	                <td width="914" background="images/02.gif"></td>
	                <td><img src="images/03.gif" width="5" height="5" /></td>
	              </tr>
	              <tr>
	                <td width="5"  background="images/left.gif"></td>
	                <td valign="top">
	                <table width="100%" cellspacing="1" cellpadding="2">
	                	<tr class="row-odd">
		                    <td width="15%" align="center"><font style="font-size: 11px"><bean:message key="knowledgepro.hostel.hostelerdatabase.roomno"/></font> </td>
		                    <td width="40%" align="center"><font style="font-size: 11px"><bean:message key="knowledgepro.admin.selectedSubjects"/></font> </td>
		                    <td width="11%" align="center" style="display: none;">Attendance Type </td>
		                    <td style="display: none;"><bean:message key="knowledgepro.attendance.activityattendence.activitytype"/> </td>
		                    <td style="display: block;"><bean:message key="knowledgepro.cancelattendance.batches"/> </td>
		                    <td width="36%" align="center"><font style="font-size: 11px"><bean:message key="knowledgepro.attn.teacherclass.teacher.name"/></font> </td>
		                    <c:if test="${timeTableForClassForm.deleteSubject}">
		                    <td width="5%" align="center"><font style="font-size: 11px"><bean:message key="knowledgepro.delete"/></font> </td>
		                    </c:if>
	                    </tr>
	                    
	                    <logic:notEmpty name="timeTableForClassForm" property="subjectList">
	                    	<nested:iterate id="to" name="timeTableForClassForm" property="subjectList" indexId="count">
	                    	
	                    	<c:if test="${to.isActive}">
	                    	
	                    	
	                    	
	                    	<%--
	                    	<c:out value="${users}"/>
	                    	<bean:write property="userId" name="timeTableForClassForm"/>
	                    	<c:if test="${users==timeTableForClassForm.userId}">
								--%>
								<tr class="row-even">
				                    <td align="center"><nested:text size="15"  property="roomNo"></nested:text> </td>
				                    <td>
				                    <%
				                    
				                    String batch="getBatches(this.value,"+count+")"; 
				                    String activitybatch="getActivityBatches(this.value,"+count+")"; 
				                    String activity="getActivityByAttendanceType(this.value,"+count+")";
				                    String dep="searchDeptWise(this.value,"+count+")";
				                    String activityId="activity_"+count; 
				                    String teacherId="teacher_"+count; 
				                    String attendanceTypeId="attendanceTypeId_"+count; 
				                    String attendanceType="getAttendanceTypeid(this.value,"+count+")";
				                    String userId="userId_"+count;
				                    %>
				                    
				                    
				                    
				                     <c:choose>
                  					<c:when test="${timeTableForClassForm.isStaff}">
                    				 <c:choose>
                  					<c:when test="${timeTableForClassForm.deleteSubject}">
									<bean:define id="perioduserId" property="userId" name="to"></bean:define>
									<bean:define id="loginuserId" property="userId" name="timeTableForClassForm"></bean:define>
									
				                    
				                    <c:forEach var="item" items="${perioduserId}">
				                    <c:choose>
                  					<c:when test="${item eq loginuserId}">
                  					<nested:select style="width:405px" property="subjectId" styleClass="combo" onchange="<%=attendanceType %>">
				                    	<html:option value="">select</html:option>
				                    	<html:optionsCollection name="timeTableForClassForm" property="subjectMap" label="value" value="key"/>
				                    </nested:select>
									</c:when>
                    				<c:otherwise>
                    				<nested:select style="width:405px" property="subjectId" styleClass="combo" onchange="<%=attendanceType %>" disabled="true">
				                    	<html:option value="">select</html:option>
				                    	<html:optionsCollection name="timeTableForClassForm" property="subjectMap" label="value" value="key"/>
				                    </nested:select>
                    				</c:otherwise>
                    				</c:choose>
									</c:forEach>
                    				</c:when>
                    				<c:otherwise>
                    				
                    				<nested:select style="width:405px" property="subjectId" styleClass="combo" onchange="<%=attendanceType %>">
				                    	<html:option value="">select</html:option>
				                    	<html:optionsCollection name="timeTableForClassForm" property="subjectMap" label="value" value="key"/>
				                    </nested:select>
				                    
                    				</c:otherwise>
                    				</c:choose>
				                  	</c:when>
				                  	
									<c:otherwise>
									
									
									<nested:select style="width:405px" property="subjectId" styleId="course" styleClass="combo" onchange="<%=attendanceType %>">
				                    	<html:option value="">select</html:option>
				                    	<html:optionsCollection name="timeTableForClassForm" property="subjectMap" label="value" value="key"/>
				                    </nested:select>
				                    
									
									
									</c:otherwise>
									</c:choose>
				                    
				                    
				                    
				                    
				                    
				                    
				                    
				                    
				                    
				                    
				                    
				                     </td>
				                    <td style="display: none;"><nested:select  property="attendanceTypeId" styleId="<%=attendanceTypeId %>" styleClass="combo" onchange="<%=activity %>">
				                    	<html:option value="">select</html:option>
				                    	<logic:notEmpty name="timeTableForClassForm" property="attTypeMap">
				                    	<html:optionsCollection name="timeTableForClassForm" property="attTypeMap" label="value" value="key"/>
				                    	</logic:notEmpty>
				                    </nested:select> </td>
				                    <td style="display: none;"><nested:select  property="activityId" styleId="<%=activityId %>" styleClass="combo" onchange="<%=activitybatch %>">
				                    	<html:option value="">select</html:option>
				                    	<logic:notEmpty name="to" property="activity">
				                    	<html:optionsCollection name="to" property="activity" label="value" value="key"/>
				                    	</logic:notEmpty>
				                    </nested:select> </td>
				                    <td style="display: block;"><nested:select  property="batchId" styleId="<%=String.valueOf(count) %>" styleClass="combo">
				                    	<html:option value="">select</html:option>
				                    	<logic:notEmpty name="to" property="batchs">
				                    	<html:optionsCollection name="to" property="batchs" label="value" value="key"/>
				                    	</logic:notEmpty>
				                    </nested:select> </td>
				                    
				                    <c:choose>
                  					<c:when test="${timeTableForClassForm.isStaff == 'false'}">
                  
				                    
				                   
				                    <td>
				                    
				                    <table>
				                    <tr><td>
				                    <nested:select style="width:200px" property="depId" styleId="depId" styleClass="combo" onchange="<%=dep %>">
										<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
											<logic:notEmpty name="timeTableForClassForm" property="departmentMap">
												<html:optionsCollection name="timeTableForClassForm" property="departmentMap" label="value" value="key" />
											</logic:notEmpty>
									</nested:select>
				                    </td> </tr>
				                    <tr>
				                    
				                    <td>
				                  
                 	 			 <nested:select  property="userId" style="width:330px;height:80px" styleId="<%=teacherId %>" multiple="multiple">
					                    	<logic:notEmpty name="timeTableForClassForm" property="teachersMap">
					                    	<html:optionsCollection name="to" property="teachersMap" label="value" value="key"/>
					                    	</logic:notEmpty>
				                    </nested:select>
				                    
				                     </td> 
				                    
				                    </tr>
				                    </table>
				                   
				                    
				                     </td> 
				                    
				                   
				                     </c:when>
                 				 <c:otherwise> 
                 			
                 			
                 			 <c:choose>
                  					<c:when test="${empty to.userId}">
                  
                 			
                 			<td style="display: none;">
				                  
                 	 			 <nested:select  property="userId" style="width:330px;height:80px" styleId="<%=teacherId %>" multiple="multiple">
					                    	<logic:notEmpty name="timeTableForClassForm" property="teachersMap">
					                    	<html:optionsCollection name="to" property="teachersMap" label="value" value="key"/>
					                    	</logic:notEmpty>
				                    </nested:select>
				                    
				                </td> 
				                
                 			
                 				  <td align="center">
				                     
				                    
				                  <font style="font-size: 12px">  <bean:write name="timeTableForClassForm" property="empName"/></font>
				                    <html:hidden name="timeTableForClassForm" property="userId" styleId="<%=userId%>"/>
                  					
                  	
              						<script type="text/javascript">
              						//alert('<%=teacherId %>'+'-----'+document.getElementById('<%=userId %>').value);
									document.getElementById('<%=teacherId %>').value=document.getElementById('<%=userId %>').value;
									
									</script>
                  	
				                     </td>
                 			
                 			
                 			 	 
				                  </c:when>
				                  
				                  <c:otherwise>
				                  <c:forEach items="${to.userId}" var="users">
				                  
				                  
				                  <td style="display: none;">
				                  
                 	 			 <nested:select  property="userId" style="width:330px;height:80px" styleId="<%=teacherId %>" multiple="multiple">
					                    	<logic:notEmpty name="timeTableForClassForm" property="teachersMap">
					                    	<html:optionsCollection name="to" property="teachersMap" label="value" value="key"/>
					                    	</logic:notEmpty>
				                    </nested:select>
				                    
				                </td> 
                 			
                 				  <td align="center">
				                     
				                    
				                   <font style="font-size: 12px"> <nested:write  property="userName"/></font>
                  					<input type="hidden"  id="<%=activityId %>" value='${users};' />
                  	
              						<script type="text/javascript">
									$(document).ready(function(){
										
										$(course).change(function(){
											var subele=document.getElementById("course");		
											var year = 2020;
											var subId=subele.options[subele.selectedIndex].value;
											
											var destination1 = document.getElementById('<%=teacherId %>');
											for (x1=destination1.options.length-1; x1>=0; x1--) {
												destination1.options[x1]=null;
											}
												getTeachersForSubjects(subId,year,updateTeachers);
												/* ajax end */
										});
										function updateTeachers(req) {
											var responseObj = req.responseXML.documentElement;
											
											
											var destination1 = document.getElementById('<%=teacherId %>');
											
											var items1 = responseObj.getElementsByTagName("option");
											if(items1.length == 0) {
												var destination4 = document.getElementById('<%=teacherId %>');
												for (x1=destination4.options.length-1; x1>=0; x1--) {
													destination4	.options[x1]=null;
												}
											}	
											for (var k = 0 ; k < items1.length ; k++) {
										        label = items1[k].getElementsByTagName("optionlabel")[0].firstChild.nodeValue;
											     value = items1[k].getElementsByTagName("optionvalue")[0].firstChild.nodeValue;
											     destination1.options[k] = new Option(label,value);
											 }
										} 
									});

									
									</script>
                  	
				                     </td>
				                 
				                  
				                  
				                   </c:forEach>
				                   
				                  </c:otherwise>
				                  
				                  </c:choose>
				                  
				                   	
                 	 			</c:otherwise>
                 				 </c:choose>
				                     
				                    <c:choose>
                  					<c:when test="${timeTableForClassForm.isStaff}">
                    
									<c:if test="${timeTableForClassForm.deleteSubject}"> 
									<bean:define id="perioduserId" property="userId" name="to"></bean:define>
									<bean:define id="loginuserId" property="userId" name="timeTableForClassForm"></bean:define>
									
				                    <td height="25">
				                    <c:forEach var="item" items="${perioduserId}">
				                    <c:if test="${item eq loginuserId}">
									<div align="center" class="delete"><img src="images/delete_icon.gif" width="16" height="16"
										onclick="deleteTimeTable('<bean:write name="to" property="deleteCount" />')" />
									</div>
									</c:if>	
										<logic:equal value="129" property="roleId" name="timeTableForClassForm">
				                    
									<div align="center" class="delete"><img src="images/delete_icon.gif" width="16" height="16"
										onclick="deleteTimeTable('<bean:write name="to" property="deleteCount" />')" />
									</div>
													
									</logic:equal>
									</c:forEach>
											
									</td>
									</c:if>
									
                    
				                  	</c:when>
									<c:otherwise>
									
									
									<c:if test="${timeTableForClassForm.deleteSubject}"> 
				                    <td height="25">
				                    
									<div align="center" class="delete"><img src="images/delete_icon.gif" width="16" height="16"
										onclick="deleteTimeTable('<bean:write name="to" property="deleteCount" />')" />
									</div>
													
									</td>
									</c:if>
								
									
									
									</c:otherwise>
									</c:choose>
									
			                    </tr>
			                    <%--
			                    </c:if>
			                    --%>
			                   
			                    </c:if>			                    	
	                    	</nested:iterate>
	                    </logic:notEmpty>
	                  </table>
	                  </td>
	                <td width="5" height="30"  background="images/right.gif"></td>
	              </tr>
	
	              <tr>
	                <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
	                <td background="images/05.gif"></td>
	                <td><img src="images/06.gif" /></td>
	              </tr>
	            </table></td>
	          </tr>
	          <tr>
			<td>
			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
			<tr >
            <td style="height: 10px;" align="center" colspan="2">
            
           		
            </td>
          </tr>
				<tr>
	            <td width="45%" height="35"><div align="right">
					<html:submit value="Submit" styleClass="formbutton"></html:submit>
					
				</div>
				</td>
				<td width="55%"> &nbsp; <html:submit value="Add More" styleClass="formbutton" onclick="addMore()"></html:submit>
				&nbsp; 
				<logic:equal value="false" name="timeTableForClassForm" property="deleteBackButton">
				<html:button property="" styleClass="formbutton" value="Back" onclick="cancelAction()"></html:button>
				</logic:equal>
				</td>
			</tr>
				</table>
			</td>
	          </tr>

	        </table></td>
	        <td valign="top" background="images/Tright_3_3.gif" ></td>
	      </tr>
	      <tr>
	        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
	        <td class="heading"></td>      
	        <td valign="top" background="images/Tright_3_3.gif" ></td>
	      </tr>
	      <tr>
	        <td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
	        <td width="0" background="images/TcenterD.gif"></td>
	        <td><img src="images/Tright_02.gif" width="9" height="29"></td>
	      </tr>
	    </table></td>
	  </tr>
	</table>
</html:form>
<script type="text/javascript">
var displayWarning=document.getElementById("displayWarning").value;
if(displayWarning!=""){
	$.confirm({
		'message'	: displayWarning+'<br> Are you sure you want to assign this Teacher. ?',
		'buttons'	: {
			'Ok'	: {
				'class'	: 'blue',
				'action': function(){
					$.confirm.hide();
					document.location.href = "timeTableForClass.do?method=addTimeTableForaPeriodAfterConfirmation";
				}
			},
    'Cancel'	:  {
				'class'	: 'gray',
				'action': function(){
					$.confirm.hide();
				}
			}
		}
	});
}
var displayWarning1=document.getElementById("displayWarning1").value;
if(displayWarning1!=""){
	$.confirm({
		'message'	: displayWarning1+'<br> Do you want to assign this Teacher. ?',
		'buttons'	: {
			'Ok'	: {
				'class'	: 'blue',
				'action': function(){
					$.confirm.hide();
					document.location.href = "timeTableForClass.do?method=addTimeTableForaPeriodAfterConfirmation";
				}
			},
    'Cancel'	:  {
				'class'	: 'gray',
				'action': function(){
					$.confirm.hide();
				}
			}
		}
	});
}
</script>