<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="ISO-8859-1" %>
<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<%@page import="java.util.Map,java.util.HashMap"%>
<link rel="stylesheet" type="text/css" href="css/styles.css"/>
<style type="text/css">
	body { font-family: Arial, Sans-serif; padding: 0 20px; }
	.field { width: 100%; float: left; margin: 0 0 20px; }
	.field input { margin: 0 0 0 20px; }
	h3 span { background: #444; color: #fff; padding: 3px; }
	pre { background: #f4f4f4; }

	/* Used for the Switch effect: */
	.cb-enable, .cb-disable, .cb-enable span, .cb-disable span { background: url(images/switch.gif) repeat-x;  float: left;}
	.cb-enable span, .cb-disable span { line-height: 30px; display: block; background-repeat: no-repeat; font-weight: bold; }
	.cb-enable span { background-position:  -90px; padding: 0 17px 0 17px;  }
	.cb-disable span { background-position: right -180px;padding: 0 10px; }
	.cb-disable.selected { background-position: 0 -30px; }
	.cb-disable.selected span { background-position: right -210px; color: #fff; }
	.cb-enable.selected { background-position: 0 -60px; }
	.cb-enable.selected span { background-position: left -150px; color: #fff; }
	.switch label { cursor: pointer; }
	</style>
	
	<script type="text/javascript">
	function setCheckedValues(count,value){
		var answer = value;
		document.getElementById("value_"+count).value = answer;
		for(j=1;j<=5;j++){
			var check = document.getElementById("answer_"+count+"_"+j).checked;
			if(check){
				 document.getElementById("label_"+count+"_"+j).style.color = 'white';
				 document.getElementById("label_"+count+"_"+j).style.background = '#8B0000';
			}else{
				document.getElementById("label_"+count+"_"+j).style.color = '#26466D';
				document.getElementById("label_"+count+"_"+j).style.background = 'white';
			}
		}
	}
	function submitDetails(){
		var flag;
		flag=0;
		var group_no;
		var grp;
		group_no=1;
		var count = 0;
		var temp = 0;
		var qno;
				qno=1;
				var total_questions = document.getElementById("tempTotalQuestions").value;
				while( qno<=total_questions)
				{
					var i=0;
					for(j=1;j<=5;j++){
						var check = document.getElementById("answer_"+temp+"_"+j).checked;
						if(check){
							break;
						}
							i++;
							if(i==5)
							{
								flag=1;
								}
						}
					temp++;
					if(temp == total_questions){
						break;
					}
				}
		if( flag==1 )
		 {
			 alert("Please give feedback for all questions.");
		 }else{
			 	
			 	document.getElementById("method").value="submitStudentsEvaluationDetails";
			 	document.evaluationTeacherFeedbackForm.submit();
		 }
		}
	function cancelAction() {
		closeConfirm = confirm("Are you sure you want to close the Teacher Evaluation?");
		if (closeConfirm == true) {
			document.location.href = "EvaluationTeacherFeedback.do?method=initEvaluationTeacherFeedback";
	}
	}
	function imposeMaxLength(evt, Object) {
		var keynum = (evt.which) ? evt.which : event.keyCode;
		if (keynum == 8) {
			return true;
		}
		var MaxLen = 499;
		if(Object.value.length <= MaxLen){
			return true;
		}else{
			return false;
		}
	}
	</script>
<html:form  action="/EvaluationTeacherFeedback">
<html:hidden property="formName" value="evaluationTeacherFeedbackForm"/>
<html:hidden property="pageType" value="5"/>
<html:hidden property="method" styleId="method" value="" />
<input type="hidden"  id="tempTotalQuestions" name="tempTotalQuestions" value="<bean:write name="evaluationTeacherFeedbackForm" property="totalQuestions"/>"/>
<input type="hidden"  id="submitSuccessfully"  value="<bean:write name="evaluationTeacherFeedbackForm" property="submitSuccessfully"/>"/>
<input type="hidden"  id="previousStudentName"  value="<bean:write name="evaluationTeacherFeedbackForm" property="previousStudentName"/>"/>
<input type="hidden"  id="lastStudent"  value="<bean:write name="evaluationTeacherFeedbackForm" property="lastStudent"/>"/>

<table width="100%" border="0" cellpadding="0" cellspacing="0">
<tr>
    <td colspan="3"><span class="Bredcrumbs">Teacher Evaluation <span class="Bredcrumbs">&gt;&gt;Teacher Evaluation Feedback </span> </span></td>
  </tr>
				<tr >
				
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader">Teacher Evaluation </strong></td>

					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr height="10%">
					<td height="20" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news"><div align="right"><span class='MandatoryMark'><bean:message
					key="knowledgepro.mandatoryfields" /></span></div>
					<div align="left"><FONT color="red" size="1">
						<bean:write name="evaluationTeacherFeedbackForm" property="errorMsg"/><br>
						</FONT></div>
					</td>
					
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" align="center" cellpadding="0"
						cellspacing="0">
						<tr>
						<td align="left" class="heading"><bean:write property="studentNo" name="evaluationTeacherFeedbackForm" /> of Students <bean:write property="totalStudents" name="evaluationTeacherFeedbackForm"/></td>
						</tr>
						<tr><td>
						<table width="476" border="0" align="center">
						  <tbody>
						    <tr >
						      <td class="heading" height="50" align="left" style="font-size: 14px;color:#8B0000;"><b>Student Name</b></td>
						      <td style="font-size: 14px;color:#8B0000;">:</td>
						      <td class="heading" style="font-size: 14px;color:#8B0000;"><bean:write property="nameOfStudent" name="evaluationTeacherFeedbackForm"/></td>
						    </tr>
						    <tr >
						      <td align="left" class="heading" height="50" style="font-size: 14px;color:#8B0000;"><b>Register No</b></td>
						      <td style="font-size: 14px;color:#8B0000;">:</td>
						      <td class="heading" style="font-size: 14px;color:#8B0000;"><bean:write property="regNo" name="evaluationTeacherFeedbackForm"/></td>
						    </tr>
						     <tr >
						      <td align="left" class="heading" height="50" style="font-size: 14px;color:#8B0000;"><b>Subject</b></td>
						      <td style="font-size: 14px;color:#8B0000;">:</td>
						      <td class="heading" style="font-size: 14px;color:#8B0000;"><bean:write property="subjectName" name="evaluationTeacherFeedbackForm"/></td>
						    </tr>
						    </tbody>
						</table>
						<br/><br/>
								<table align="center" border="0" style="color: #009">
								<tr>
								    <th class="heading" style="font-size: 12px;">1</th>
								    <th class="heading" style="font-size: 12px;">2</th>
								    <th class="heading" style="font-size: 12px;">3</th>
								    <th class="heading" style="font-size: 12px;">4</th>
								    <th class="heading" style="font-size: 12px;">5</th>
								</tr>
								<tr>
									<td width="160" align="center" class="heading" style="font-size: 12px;">Needs to improve</td>
								    <td width="160" align="center" class="heading" style="font-size: 12px;">Satisfactory</td>
								   	<td width="160" align="center" class="heading" style="font-size: 12px;">Good</td>
								   	<td width="160" align="center" class="heading" style="font-size: 12px;">Very Good</td>
								   	<td width="160" align="center" class="heading" style="font-size: 12px;">Excellent</td>    
								</tr>
								</table>
								<br/>
						</td></tr>
						<tr>
							<td>
								<table width="100%" border="1"  cellpadding="2" cellspacing="1" >
									<tr>
									  <logic:notEmpty name="evaluationTeacherFeedbackForm" property="evaTeacherQuestionsToList">
											<td width="50%" valign="top" bgcolor="#E3EBE5" >
											<c:set var="count1" value="0"></c:set>
												<nested:iterate id="qun" name="evaluationTeacherFeedbackForm" property="evaTeacherQuestionsToList" indexId="count"> 
															 <table  cellpadding="0" cellspacing="0" width="100%" bgcolor="#E3EBE5" >
															 <tr>
															<td width="3%" class="heading" align="left" style="font-size: 12px;"><c:out value="${count + 1}."/></td>
							                   				<td width="62%" class="heading" style="font-size: 12px;"> <nested:write property="question"/></td>
							                   				<td width="35%" align="center" style="font-size: 12px;" >
							                   				<fieldset style="align:center;">
							                   				<input type="hidden" name="evaFacultyQuestionsToList[<c:out value='${count}'/>].answer" id="value_<c:out value='${count}'/>"/>
							                   				<input type="radio" style="display:none;" id="answer_<c:out value='${count}'/>_1" name="evaTeacherQuestionsToList[<c:out value='${count}'/>].answer" value="1" onclick="setCheckedValues(<c:out value='${count}'/>,this.value)"/>
											                  <label id="label_<c:out value='${count}'/>_1" for="answer_<c:out value='${count}'/>_1" class="cb-enable" style="cursor:pointer; border: 1px solid #C1CDCD" ><span>1</span></label>
											                <input type="radio" style="display:none;" id="answer_<c:out value='${count}'/>_2" name="evaTeacherQuestionsToList[<c:out value='${count}'/>].answer" value="2" onclick="setCheckedValues(<c:out value='${count}'/>,this.value)"/>
											                  <label id="label_<c:out value='${count}'/>_2" for="answer_<c:out value='${count}'/>_2" class="cb-enable" style="cursor:pointer; border: 1px solid #C1CDCD"><span>2</span></label>
											                <input type="radio" style="display:none;" id="answer_<c:out value='${count}'/>_3" name="evaTeacherQuestionsToList[<c:out value='${count}'/>].answer" value="3" onclick="setCheckedValues(<c:out value='${count}'/>,this.value)"/>
											                  <label id="label_<c:out value='${count}'/>_3" for="answer_<c:out value='${count}'/>_3" class="cb-enable" style="cursor:pointer;border: 1px solid #C1CDCD"><span>3</span></label>
											                <input type="radio" style="display:none;" id="answer_<c:out value='${count}'/>_4" name="evaTeacherQuestionsToList[<c:out value='${count}'/>].answer" value="4" onclick="setCheckedValues(<c:out value='${count}'/>,this.value)"/>
											                  <label id="label_<c:out value='${count}'/>_4" for="answer_<c:out value='${count}'/>_4" class="cb-enable" style="cursor:pointer;border: 1px solid #C1CDCD"><span>4</span></label>
											                <input type="radio" style="display:none;" id="answer_<c:out value='${count}'/>_5" name="evaTeacherQuestionsToList[<c:out value='${count}'/>].answer" value="5" onclick="setCheckedValues(<c:out value='${count}'/>,this.value)"/>
											                  <label id="label_<c:out value='${count}'/>_5" for="answer_<c:out value='${count}'/>_5" class="cb-enable" style="cursor:pointer;border: 1px solid #C1CDCD"><span>5</span></label>
							                   				</fieldset>
							                   				</td>
															 </tr>  
															 </table>
												</nested:iterate>
											</td>
											</logic:notEmpty>
											
										</tr>
									<tr>
											<td>
										 <table  cellpadding="0" cellspacing="0" width="100%" bgcolor="#E3EBE5" >
										 <tr>
										 <td width="35%" class="heading" style="font-size: 12px;">Remarks, If any : </td>
										 <td width="65%"><html:textarea property="remarks" style="width: 83%" styleId="remarks" cols="80" rows="8" onkeypress="return imposeMaxLength(event,this)"></html:textarea> </td>
										 </tr>
										 </table>
											
											</td>
											</tr>
								</table>
							</td>	
						</tr>
						<tr>
			                   <td colspan="2" height="100"><div align="center">
								<html:button property="" value="Submit"  styleClass="formbutton" onclick="submitDetails()" />&nbsp; <html:button property="" value="Close" styleClass="formbutton" onclick="cancelAction()"></html:button>
								</div></td>
			                 </tr>
					</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
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
			<script>
			 
	var success = document.getElementById("submitSuccessfully").value;
	var empName =document.getElementById("previousStudentName").value; 
	if(success == 'true') {
		var a = confirm("Evaluation Submitted Successfully for \n\t\t"+empName+"\n\n  Do you want to continue with the next student ?");
		if(a == true){
			document.location.href = "EvaluationTeacherFeedback.do?method=startTeachersEvaluation";
		}else{
			document.location.href = "EvaluationTeacherFeedback.do?method=initEvaluationTeacherFeedback";
		}
	}
	var lastPeer1 =document.getElementById("lastStudent").value; 
	if(lastPeer1 == 'true'){
		var a = confirm("Evaluation Submitted Successfully for \n\t\t"+empName);
		if(a == true){
			document.location.href = "EvaluationTeacherFeedback.do?method=getStudentsForFeedback";
		}else{
			document.location.href = "EvaluationTeacherFeedback.do?method=initEvaluationTeacherFeedback";
		}
		
	}
</script>
</html:form>
