<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@page import="java.util.Map,java.util.HashMap"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<script type="text/javascript">
	
function cancelAction() {
	document.location.href = "valuationStatus.do?method=cancelForValuationStatus";
}

function viewValuationDetails(examId,subjectId,evaluatorId,courseId,subjectName){
	var url = "valuationStatus.do?method=viewValuationDetails&examId="+examId+"&subjectId="+subjectId+"&evaluatorTypeId="+evaluatorId+"&courseId="+courseId+"&subjectName="+subjectName;
	myRef = window
			.open(url, "ViewResume",
					"left=20,top=20,width=900,height=900,toolbar=1,resizable=0,scrollbars=1");	
}

function viewVerificationDetails(examId,subjectId,evaluatorId,courseId,subjectName){
	var url = "valuationStatus.do?method=viewVerificationDetails&examId="+examId+"&subjectId="+subjectId+"&evaluatorTypeId="+evaluatorId+"&courseId="+courseId+"&subjectName="+subjectName;
	myRef = window
			.open(url, "ViewResume",
					"left=20,top=20,width=900,height=900,toolbar=1,resizable=0,scrollbars=1");	
}

function changeValue(count,check){
	var checkedValue = document.getElementById("valuationStatus_"+count).checked;
	if(checkedValue){
		document.getElementById("valuationStatus_"+count).value = "on";
		document.getElementById("hidden_"+count).value = "on";
	}else{
		document.getElementById("valuationStatus_"+count).value = "off";
		document.getElementById("hidden_"+count).value = "off";
	}
}
function changeValue1(count,check){
	var checkedValue = document.getElementById("valuationStatus1_"+count).checked;
	if(checkedValue){
		document.getElementById("valuationStatus1_"+count).value = "on";
		document.getElementById("hidden1_"+count).value = "on";
	}else{
		document.getElementById("valuationStatus1_"+count).value = "off";
		document.getElementById("hidden1_"+count).value = "off";
	}
}
function selectAll(obj) {
    var value = obj.checked;
    var inputs = document.getElementsByTagName("input");
    var inputObj;
    var checkBoxselectedCount = 0;
    for(var count1 = 0;count1<inputs.length;count1++) {
          inputObj = inputs[count1];
          var type = inputObj.getAttribute("type");
          var styleId = inputObj.getAttribute("id");
            if (type == 'checkbox') {
                var styleName="";
                for(var j=0;j<=2000;j++){
                	styleName = "valuationStatus_"+j;
	                if(styleId == styleName){
	                	inputObj.checked = value;
	                    inputObj.value="on";
	                    if(value==true){
		                    document.getElementById("hidden_"+j).value="on";
	                    }else{
	                    	document.getElementById("hidden_"+j).value="off";
	                    }
	                }
                }
            }
    }
}
function selectAll1(obj) {
    var value = obj.checked;
    var inputs = document.getElementsByTagName("input");
    var inputObj;
    var checkBoxselectedCount = 0;
    for(var count1 = 0;count1<inputs.length;count1++) {
          inputObj = inputs[count1];
          var type = inputObj.getAttribute("type");
          var styleId = inputObj.getAttribute("id");
            if (type == 'checkbox') {
                var styleName="";
                for(var j=0;j<=2000;j++){
                	styleName = "valuationStatus1_"+j;
	                if(styleId == styleName){
	                	inputObj.checked = value;
	                    inputObj.value="on";
	                    if(value==true){
	                    	document.getElementById("hidden1_"+j).value="on";
	                    }else{
	                   		document.getElementById("hidden1_"+j).value="off";
	                    }
	                }
                }
            }
    }
}
function viewMismatchFounrDetails(subjectId,evaluatorId,courseId,subjectName){
	
	var url = "valuationStatus.do?method=viewMismatchDetails&subjectId="+subjectId+"&evaluatorTypeId="+evaluatorId+"&courseId="+courseId+"&subjectName="+subjectName;
	myRef = window
			.open(url, "ViewResume",
					"left=20,top=20,width=900,height=900,toolbar=1,resizable=0,scrollbars=1");
}
</SCRIPT>
<html:form action="/valuationStatus">
	<html:hidden property="formName" value="examValuationStatusForm" styleId="formName" />
	<html:hidden property="method" styleId="method" value="saveDetails" />
	<html:hidden property="pageType" value="2" styleId="pageType" />
	<input type="hidden" id="typeExam" name="type" value='<bean:write name="examValuationStatusForm" property="examType"/>' />
	<table width="99%" border="0">
		<tr>
			<td><span class="heading"><span class="Bredcrumbs">Exam
			&gt;&gt; Exam Valuation Status &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="heading_white">Exam Valuation Status</td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td height="20" class="news">

					<div align="right"><FONT color="red"> <span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields"/></span></FONT></div>
							<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
							<FONT color="green"> <html:messages id="msg"
								property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
								<br>
							</html:messages> </FONT></div>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>

				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="heading">&nbsp;</td>
					<td valign="top" background="images/Tright_3_3.gif"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="heading">
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

								 <tr >
				                    <td height="25" class="row-odd" ><div align="center"><bean:message key="knowledgepro.slno"/></div></td>
				                    <td height="25" class="row-odd" align="center" >Course</td>
				                    <td height="25" class="row-odd" align="center">Subject Code</td>
				                    <td height="25" class="row-odd" align="center">Subject Name</td>
				                    <td height="25" class="row-odd" align="center">Internal Subject</td>
				                    <td height="25" class="row-odd" align="center">Exam Date</td>
				                    <td height="25" class="row-odd" align="center">Issued For Valuation</td>
				                    <td height="25" class="row-odd" align="center">Valuator</td>
				                    <td height="25" class="row-odd" align="center">Valuation Completed</td>
				                    <td height="25" class="row-odd" align="center">Verification Completed</td>
				                    <td height="25" class="row-odd" align="center">Mismatch Found</td>
				                    <td height="25" class="row-odd" align="center">Valuation Process Completed</td>
				                    <td height="25" class="row-odd" align="center">Overall Process Completed</td>
				                  </tr>
				                   <tr >
				                    <td height="25" class="row-odd" ></td>
				                    <td height="25" class="row-odd" ></td>
				                    <td height="25" class="row-odd" ></td>
				                    <td height="25" class="row-odd" align="center" ></td>
				                    <td height="25" class="row-odd" align="center"></td>
				                    <td height="25" class="row-odd" align="center" ></td>
				                    <td height="25" class="row-odd" align="center"> Issued TO</td>
				                    <td height="25" class="row-odd" align="center"></td>
				                    <td height="25" class="row-odd" align="center"></td>
				                    <td height="25" class="row-odd" align="center"></td>
				                      <td height="25" class="row-odd" align="center"></td>
				                    <td height="25" class="row-odd" align="center">COE 
				                    	<div><input type="checkbox" id="checkAll" onclick="selectAll(this)"/>Select All</div>
				                    </td>
				                    <td height="25" class="row-odd" align="center">Exam Office
				                    	<div><input type="checkbox" id="checkAll" onclick="selectAll1(this)"/>Select All</div>
				                    </td>
				                  </tr>
				                <logic:notEmpty name="examValuationStatusForm" property="valuationStatus">
					                <nested:iterate id="to" name="examValuationStatusForm" property="valuationStatus" indexId="count" type="com.kp.cms.to.exam.ExamValuationStatusTO">
					               
					                   	<c:choose>
												<c:when test="${count%2 == 0}">
													<tr class="row-even">
												</c:when>
												<c:otherwise>
													<tr class="row-odd">
												</c:otherwise>
										</c:choose>
										<% if(!to.isCertificateCourse()) {%>
					                   		<td width="5%" height="25" class="row-even"><div align="center"><c:out value="${count + 1}"/></div></td>
					                   		<td width="18%" height="25" class="row-even"><bean:write name="to" property="courseCode"/></td>
					                   		<td width="5%" height="25" class="row-even"><bean:write name="to" property="subjectCode"/></td>
					                   		<td width="18%" height="25" class="row-even"><bean:write name="to" property="subjectName"/></td>
					                   		<td width="5%" height="25" class="row-even" align="center"><bean:write name="to" property="internalSubject"/></td>
					                   		<td width="5%" height="25" class="row-even" align="center"><bean:write name="to" property="examStratDate"/></td>
					                   		<td width="14%" height="25" class="row-even">
						                   		<logic:notEmpty name="to" property="evaluatorDetails">
						                   			<logic:iterate id="evaluator1" property="evaluatorDetails" name="to">
						                   			   <table>
					                   			   		 <tr>
												            <td width="15%" height="25" class="row-even"><bean:write name="evaluator1" property="employeeName"/></td>
											             </tr>
											           </table>
						                   			</logic:iterate>
						                   		</logic:notEmpty>
					                   		</td>
					                   		<td width="5%" class="row-even" align="center">
					                   		<logic:notEmpty name="to" property="statusTOs">
					                   			<logic:iterate id="status2" property="statusTOs" name="to" type="com.kp.cms.to.exam.ExamValuationStatusTO">
					                   			  <table>
					                   			   <tr>
											           	<td width="5%" class="row-even" align="center">
											           		<bean:write name="status2" property="evaluatorTypeId"/>
								                   		</td>
					                   			   </tr>
					                   			  </table>
					                   			</logic:iterate>
					                   		</logic:notEmpty>
					                   	</td>
					                   	<%}else{ %>
					                   	<td width="5%" height="25" class="row-even"><div align="center"><FONT color="#A52A2A"><c:out value="${count + 1}"/></FONT></div></td>
					                   		<td width="18%" height="25" class="row-even"><FONT color="#A52A2A"><bean:write name="to" property="courseCode"/></FONT></td>
					                   		<td width="5%" height="25" class="row-even"><FONT color="#A52A2A"><bean:write name="to" property="subjectCode"/></FONT></td>
					                   		<td width="18%" height="25" class="row-even"><FONT color="#A52A2A"><bean:write name="to" property="subjectName"/></FONT></td>
					                   		<td width="5%" height="25" class="row-even" align="center"><FONT color="#A52A2A"><bean:write name="to" property="internalSubject"/></FONT></td>
					                   		<td width="5%" height="25" class="row-even" align="center"><FONT color="#A52A2A"><bean:write name="to" property="examStratDate"/></FONT></td>
					                   		<td width="14%" height="25" class="row-even">
						                   		<logic:notEmpty name="to" property="evaluatorDetails">
						                   			<logic:iterate id="evaluator1" property="evaluatorDetails" name="to">
						                   			   <table>
					                   			   		 <tr>
												            <td width="15%" height="25" class="row-even"><FONT color="#A52A2A"><bean:write name="evaluator1" property="employeeName"/></FONT></td>
											             </tr>
											           </table>
						                   			</logic:iterate>
						                   		</logic:notEmpty>
					                   		</td>
					                   		<td width="5%" class="row-even" align="center">
					                   		<logic:notEmpty name="to" property="statusTOs">
					                   			<logic:iterate id="status2" property="statusTOs" name="to" type="com.kp.cms.to.exam.ExamValuationStatusTO">
					                   			  <table>
					                   			   <tr>
											           	<td width="5%" class="row-even" align="center">
											           	<FONT color="#A52A2A">	<bean:write name="status2" property="evaluatorTypeId"/></FONT>
								                   		</td>
					                   			   </tr>
					                   			  </table>
					                   			</logic:iterate>
					                   		</logic:notEmpty>
					                   	</td>
					                   	<%} %>
					                   		<td width="5%" class="row-even" align="center">
					                   		<logic:notEmpty name="to" property="statusTOs">
					                   			<logic:iterate id="status" property="statusTOs" name="to" type="com.kp.cms.to.exam.ExamValuationStatusTO">
					                   			  <table>
					                   			   <tr>
											           	<td width="5%" class="row-even" align="center">
											           	<%if(status.getValuationCompleted().equalsIgnoreCase("No")) {%>
									                   		<a href="javascript:void(0)" onclick="viewValuationDetails('<bean:write name="to" property="examId"/>','<bean:write name="to" property="subjectId"/>','<bean:write name="status" property="evaluatorTypeId"/>','<bean:write name="to" property="courseId"/>','<bean:write name="to" property="subjectName"/>')">
								                   				<bean:write name="status" property="valuationCompleted"/>
									                   		</a>
									                   <%}else if(status.getValuationCompleted().equalsIgnoreCase("Yes")){ %>
									                   			<bean:write name="status" property="valuationCompleted"/>
									                   <%} %>
								                   		</td>
					                   			   </tr>
					                   			  </table>
					                   			</logic:iterate>
					                   		</logic:notEmpty>
					                   	</td>
					                   	<td width="5%" class="row-even" align="center">
					                   		<logic:notEmpty name="to" property="statusTOs">
					                   			<logic:iterate id="status1" property="statusTOs" name="to" type="com.kp.cms.to.exam.ExamValuationStatusTO">
					                   			  <table>
					                   			   <tr>
											           	<td width="5%" class="row-even" align="center">
											           		<%if(status1.getVerificationCompleted().equalsIgnoreCase("No")) {%>
												           	<a href="javascript:void(0)" onclick="viewVerificationDetails('<bean:write name="to" property="examId"/>','<bean:write name="to" property="subjectId"/>','<bean:write name="status1" property="evaluatorTypeId"/>','<bean:write name="to" property="courseId"/>','<bean:write name="to" property="subjectName"/>')">
												           			<bean:write name="status1" property="verificationCompleted"/>
										                   	</a>
										                   	<%}else if(status1.getVerificationCompleted().equalsIgnoreCase("Yes")){ %>
										                   			<bean:write name="status1" property="verificationCompleted"/>
										                   	 <%} %>
											           	</td>
					                   			   </tr>
					                   			  </table>
					                   			</logic:iterate>
					                   		</logic:notEmpty>
					                   	</td>
					                   	<td width="5%" class="row-even" align="center">
					                   		<logic:notEmpty name="to" property="statusTOs">
					                   			<logic:iterate id="status2" property="statusTOs" name="to" type="com.kp.cms.to.exam.ExamValuationStatusTO">
					                   			  <table>
					                   			   <tr>
											           	<td width="5%" class="row-even" align="center">
											           		<%if(status2.getMisMatchFound().equalsIgnoreCase("Yes")) {%>
												           	<a href="javascript:void(0)" onclick="viewMismatchFounrDetails('<bean:write name="to" property="subjectId"/>','<bean:write name="status2" property="evaluatorTypeId"/>','<bean:write name="to" property="courseId"/>','<bean:write name="to" property="subjectName"/>')">
												           			<bean:write name="status2" property="misMatchFound"/>
										                   	</a>
										                   	<%}else if(status2.getMisMatchFound().equalsIgnoreCase("No")){ %>
										                   			<bean:write name="status2" property="misMatchFound"/>
										                   	 <%}%>
											           	</td>
					                   			   </tr>
					                   			  </table>
					                   			</logic:iterate>
					                   		</logic:notEmpty>
					                   	</td>
					                   		<td width="7%" class="row-even" align="center">
					                   		<input	type="hidden"	name="valuationStatus[<c:out value='${count}'/>].tempvaluationProcess"	id="hidden_<c:out value='${count}'/>"
																	value="<nested:write name='to' property='valuationProcess'/>" />
					                   		<input type="checkbox"	name="valuationStatus[<c:out value='${count}'/>].valuationProcess"	id="valuationStatus_<c:out value='${count}'/>" onclick="changeValue(<c:out value='${count}'/>,this.value)"/>
					                   		
					                   		
										<script type="text/javascript">
																		var studentId = document.getElementById("hidden_<c:out value='${count}'/>").value;
																		if(studentId == "on") {
																			document.getElementById("valuationStatus_<c:out value='${count}'/>").checked = true;
																		}		
																	</script>
					                   		</td>
					                   		<td width="7%" class="row-even" align="center">
					                   			<input	type="hidden"	name="valuationStatus[<c:out value='${count}'/>].tempoverallProcess"	id="hidden1_<c:out value='${count}'/>"
																	value="<nested:write name='to' property='overallProcess'/>" />
					                   			<input type="checkbox"	name="valuationStatus[<c:out value='${count}'/>].overallProcess" id="valuationStatus1_<c:out value='${count}'/>" onclick="changeValue1(<c:out value='${count}'/>,this.value)"/>
					                   			<script type="text/javascript">
																		var studentId = document.getElementById("hidden1_<c:out value='${count}'/>").value;
																		if(studentId == "on") {
																			document.getElementById("valuationStatus1_<c:out value='${count}'/>").checked = true;
																		}		
																	</script>
					                   		</td>
					                </nested:iterate>
				                </logic:notEmpty>
								
							</table>
							</td>

							<td width="5" background="images/right.gif"></td>
						</tr>
						<tr>
							<td height="5"><img src="images/04.gif" width="5" height="5" /></td>
							<td background="images/05.gif"></td>
							<td><img src="images/06.gif" /></td>
						</tr>
					</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif"></td>

				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="heading">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="20%" height="35" align="center">
							<html:submit property="" value="Submit" styleClass="formbutton"></html:submit>
							<input type="button" class="formbutton" value="Cancel" onclick="cancelAction()" />
							
							</td>
						</tr>
						
					</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif"></td>
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
</html:form>

