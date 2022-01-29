<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="ISO-8859-1" %>
<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<%@page import="java.util.Map,java.util.HashMap"%>
<%@page import="com.kp.cms.constants.CMSConstants"%>
<link rel="stylesheet" type="text/css" href="css/styles.css"/>
	<link rel="stylesheet" type="text/css" href="css/sdmenu.css"/>
    <script type="text/javascript" src="js/AC_RunActiveContent.js"></script>
    <script type="text/javascript" src="js/sdmenu/sdmenu.js"></script>
    <script type="text/javascript" src="js/ajax/Ajax.js"></script>
    <script type="text/javascript" src="js/ajax/AjaxUtil.js"></script>
    <script type="text/javascript" src="js/leftSwitchMenu.js"></script>
	
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
				var remarks = document.getElementById("remarks").value;
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
		 }else if(remarks==null){
			 alert("Please give remarks");
		 }
		 else{
			 	
			 	document.getElementById("method").value="submitFacultyEvaluationDetails";
			 	document.evaluationStudentFeedbackForm.submit();
		 }
		}
	function cancelAction() {
		closeConfirm = confirm("Are you sure you want to close the Faculty Evaluation?\n Already Submitted feedback will not be saved");
		if (closeConfirm == true) {
		document.location.href = "StudentLoginAction.do?method=returnHomePage";
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
	function imposeMaxLength1(field, size) {
	    if (field.value.length > size) {
	        field.value = field.value.substring(0, size);
	    }
	}
		
	// to display the text areas length 
	function len_display(Object,MaxLen,element){
	    var len_remain = MaxLen+Object.value.length;
	   if(len_remain <=500){
	    document.getElementById(element).value=len_remain; }
	}
	function len_display1(Object,MaxLen,element){
	    var len_remain = MaxLen+Object.value.length;
	   if(len_remain <=500){
	    document.getElementById(element).value=len_remain; }
	}
	function len_display2(Object,MaxLen,element){
	    var len_remain = MaxLen+Object.value.length;
	   if(len_remain <=500){
	    document.getElementById(element).value=len_remain; }
	}
	function submitDetailsForCJC(){
	var flag;
	flag=0;
	var group_no;
	var grp;
	group_no=1;
	var count = 0;
	var temp = 0;
	var qno;
			qno=1;
			var remarks = document.getElementById("remarks");
			var total_questions = document.getElementById("tempTotalQuestions").value;
			while( qno<=total_questions)
			{
				var i=0;
				for(j=1;j<=7;j++){
					var check = document.getElementById("answer_"+temp+"_"+j).checked;
					if(check){
						break;
					}
						i++;
						if(i==7)
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
	 }else if(remarks==null){
		 alert("Please give remarks");
	 }else{
		 	
		 	document.getElementById("method").value="submitFacultyEvaluationDetails";
		 	document.evaluationStudentFeedbackForm.submit();
	 }

	}
	function setCheckedValuesForCJC(count,value){
		var answer = value;
		document.getElementById("value_"+count).value = answer;
		for(j=1;j<=7;j++){
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
	</script>
<html:form  action="/EvaluationStudentFeedback">
<html:hidden property="formName" value="evaluationStudentFeedbackForm"/>
<html:hidden property="pageType" value="1"/>
<html:hidden property="method" styleId="method" value="" />
<input type="hidden"  id="tempTotalQuestions" name="tempTotalQuestions" value="<bean:write name="evaluationStudentFeedbackForm" property="totalQuestions"/>"/>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr >
				
					<td width="9"><img src="images/st_Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/st_Tcenter.gif" class="body"><strong
						class="boxheader">Faculty Evaluation</strong></td>

					<td width="10"><img src="images/st_Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr height="10%">
					<td height="20" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news"><div align="right"><span class='MandatoryMark'><bean:message
					key="knowledgepro.mandatoryfields" /></span></div>
					</td>
					
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" align="center" cellpadding="0"
						cellspacing="0">
						<tr>
						<td align="left" class="heading"><bean:write property="subjectNo" name="evaluationStudentFeedbackForm" /> of Subjects <bean:write property="totalSubjects" name="evaluationStudentFeedbackForm"/></td>
						</tr>
						<tr><td>
						<table width="476" border="0" align="center">
						  <tbody>
						    <tr >
						      <td class="heading" height="50" align="left" style="font-size: 14px;color:#8B0000;"><b>Teacher Name</b></td>
						      <td style="font-size: 14px;color:#8B0000;">:</td>
						      <td class="heading" style="font-size: 14px;color:#8B0000;"><bean:write property="teacherName" name="evaluationStudentFeedbackForm"/></td>
						    </tr>
						    <tr >
						      <td align="left" class="heading" style="font-size: 14px;color:#8B0000;"><b>Subject</b></td>
						      <td style="font-size: 14px;color:#8B0000;">:</td>
						      <td class="heading" style="font-size: 14px;color:#8B0000;"><bean:write property="subjectName" name="evaluationStudentFeedbackForm"/></td>
						    </tr>
						    </tbody>
						</table>
						<br/><br/>
						<% if(CMSConstants.LINK_FOR_CJC){ %>
									<table align="center" border="0" style="color: #009">
								
								<tr>
								    <th class="heading" style="font-size: 12px;">1</th>
								    <th class="heading" style="font-size: 12px;">7</th>
								</tr>
								<tr>
									<td width="160" align="center" class="heading" style="font-size: 12px;">Lowest</td>
								    <td width="160" align="center" class="heading" style="font-size: 12px;">Highest</td>
								</tr>
								</table>
							<%}else{ %> 
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
												<%} %>
								
								<br/>
						</td></tr>
						<tr>
							<td>
								<table width="100%" border="1"  cellpadding="2" cellspacing="1" >
									<tr>
									  <logic:notEmpty name="evaluationStudentFeedbackForm" property="questionListTo">
											<td width="50%" valign="top" bgcolor="#E3EBE5" >
											<c:set var="count1" value="0"></c:set>
												<nested:iterate id="qun" name="evaluationStudentFeedbackForm" property="questionListTo" indexId="count"> 
															 <table  cellpadding="0" cellspacing="0" width="100%" bgcolor="#E3EBE5" >
															 <tr>
															<td width="3%" class="heading" align="left" style="font-size: 12px;"><c:out value="${count + 1}."/></td>
							                   				<td width="62%" class="heading" style="font-size: 12px;"> <bean:write name="qun" property="question"/></td>
							                   				<td width="35%" align="center" style="font-size: 12px;" >
							                   				<fieldset style="align:center;">
							                   				<% if(CMSConstants.LINK_FOR_CJC){ %>
							                   				<input type="hidden" name="questionListTo[<c:out value='${count}'/>].checked" id="value_<c:out value='${count}'/>"/>
							                   				<input type="radio" style="display:none;" id="answer_<c:out value='${count}'/>_1" name="questionListTo[<c:out value='${count}'/>].checked" value="1" onclick="setCheckedValuesForCJC(<c:out value='${count}'/>,this.value)"/>
											                   <label id="label_<c:out value='${count}'/>_1" for="answer_<c:out value='${count}'/>_1" class="cb-enable" style="cursor:pointer; border: 1px solid #C1CDCD" ><span>1</span></label>
											                <input type="radio" style="display:none;" id="answer_<c:out value='${count}'/>_2" name="questionListTo[<c:out value='${count}'/>].checked" value="2" onclick="setCheckedValuesForCJC(<c:out value='${count}'/>,this.value)" />
											                   <label id="label_<c:out value='${count}'/>_2" for="answer_<c:out value='${count}'/>_2" class="cb-enable" style="cursor:pointer; border: 1px solid #C1CDCD"><span>2</span></label>&nbsp;&nbsp;&nbsp;
											                <input type="radio" style="display:none;" id="answer_<c:out value='${count}'/>_3" name="questionListTo[<c:out value='${count}'/>].checked" value="3" onclick="setCheckedValuesForCJC(<c:out value='${count}'/>,this.value)"/>
											                   <label id="label_<c:out value='${count}'/>_3" for="answer_<c:out value='${count}'/>_3" class="cb-enable" style="cursor:pointer;border: 1px solid #C1CDCD"><span>3</span></label>&nbsp;&nbsp;&nbsp;
											                <input type="radio" style="display:none;" id="answer_<c:out value='${count}'/>_4" name="questionListTo[<c:out value='${count}'/>].checked" value="4" onclick="setCheckedValuesForCJC(<c:out value='${count}'/>,this.value)"/>
											                   <label id="label_<c:out value='${count}'/>_4" for="answer_<c:out value='${count}'/>_4" class="cb-enable" style="cursor:pointer;border: 1px solid #C1CDCD"><span>4</span></label>&nbsp;&nbsp;&nbsp;
											                <input type="radio" style="display:none;" id="answer_<c:out value='${count}'/>_5" name="questionListTo[<c:out value='${count}'/>].checked" value="5" onclick="setCheckedValuesForCJC(<c:out value='${count}'/>,this.value)"/>
											                   <label id="label_<c:out value='${count}'/>_5" for="answer_<c:out value='${count}'/>_5" class="cb-enable" style="cursor:pointer;border: 1px solid #C1CDCD"><span>5</span></label>&nbsp;&nbsp;&nbsp;
											                <input type="radio" style="display:none;" id="answer_<c:out value='${count}'/>_6" name="questionListTo[<c:out value='${count}'/>].checked" value="6" onclick="setCheckedValuesForCJC(<c:out value='${count}'/>,this.value)"/>
											                   <label id="label_<c:out value='${count}'/>_6" for="answer_<c:out value='${count}'/>_6" class="cb-enable" style="cursor:pointer;border: 1px solid #C1CDCD"><span>6</span></label>
											                <input type="radio" style="display:none;" id="answer_<c:out value='${count}'/>_7" name="questionListTo[<c:out value='${count}'/>].checked" value="7" onclick="setCheckedValuesForCJC(<c:out value='${count}'/>,this.value)"/>
											                   <label id="label_<c:out value='${count}'/>_7" for="answer_<c:out value='${count}'/>_7" class="cb-enable" style="cursor:pointer;border: 1px solid #C1CDCD"><span>7</span></label>
							                   				<%}else{ %>
							                   				<input type="hidden" name="questionListTo[<c:out value='${count}'/>].checked" id="value_<c:out value='${count}'/>"/>
							                   				<input type="radio" style="display:none;" id="answer_<c:out value='${count}'/>_1" name="questionListTo[<c:out value='${count}'/>].checked" value="1" onclick="setCheckedValues(<c:out value='${count}'/>,this.value)"/>
											                  <label id="label_<c:out value='${count}'/>_1" for="answer_<c:out value='${count}'/>_1" class="cb-enable" style="cursor:pointer; border: 1px solid #C1CDCD" ><span>1</span></label>
											                <input type="radio" style="display:none;" id="answer_<c:out value='${count}'/>_2" name="questionListTo[<c:out value='${count}'/>].checked" value="2" onclick="setCheckedValues(<c:out value='${count}'/>,this.value)" />
											                   <label id="label_<c:out value='${count}'/>_2" for="answer_<c:out value='${count}'/>_2" class="cb-enable" style="cursor:pointer; border: 1px solid #C1CDCD"><span>2</span></label>&nbsp;&nbsp;&nbsp;
											                <input type="radio" style="display:none;" id="answer_<c:out value='${count}'/>_3" name="questionListTo[<c:out value='${count}'/>].checked" value="3" onclick="setCheckedValues(<c:out value='${count}'/>,this.value)"/>
											                    <label id="label_<c:out value='${count}'/>_3" for="answer_<c:out value='${count}'/>_3" class="cb-enable" style="cursor:pointer;border: 1px solid #C1CDCD"><span>3</span></label>&nbsp;&nbsp;&nbsp;
											                <input type="radio" style="display:none;" id="answer_<c:out value='${count}'/>_4" name="questionListTo[<c:out value='${count}'/>].checked" value="4" onclick="setCheckedValues(<c:out value='${count}'/>,this.value)"/>
											                   <label id="label_<c:out value='${count}'/>_4" for="answer_<c:out value='${count}'/>_4" class="cb-enable" style="cursor:pointer;border: 1px solid #C1CDCD"><span>4</span></label>&nbsp;&nbsp;&nbsp;
											                <input type="radio" style="display:none;" id="answer_<c:out value='${count}'/>_5" name="questionListTo[<c:out value='${count}'/>].checked" value="5" onclick="setCheckedValues(<c:out value='${count}'/>,this.value)"/>
											                   <label id="label_<c:out value='${count}'/>_5" for="answer_<c:out value='${count}'/>_5" class="cb-enable" style="cursor:pointer;border: 1px solid #C1CDCD"><span>5</span></label>
							                   				<%} %>
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
										<% if(CMSConstants.LINK_FOR_CJC){ %>
											<table  cellpadding="0" cellspacing="0" width="100%" bgcolor="#E3EBE5" >
										 <tr>
										 <td width="35%" class="heading" style="font-size: 12px;">What do you like best about your teacher? </td>
										 <td width="65%"><html:textarea property="additionalInfo" style="width: 83%" styleId="remarks" cols="80" rows="8" onkeypress="return imposeMaxLength1(this, 499);" onkeyup="len_display(this,0,'long_len')"></html:textarea>
										 <input type="text" id="long_len" value="0" class="len" size="2" readonly="readonly" style="border: none; background-color: #E3EBE5; text-align: right; font-size: 11px; font-family: Verdana, Arial, Helvetica, sans-serif">/500 Characters</td>
										 </tr>
										 <tr>
										 <td width="35%" class="heading" style="font-size: 12px;">Any other comments : </td>
										 <td width="65%"><html:textarea property="remarks" style="width: 83%" styleId="remarks" cols="80" rows="8" onkeypress="return imposeMaxLength1(this, 499);" onkeyup="len_display1(this,0,'long_len1')"></html:textarea>
										<input type="text" id="long_len1" value="0" class="len1" size="2" readonly="readonly" style="border: none; background-color: #E3EBE5; text-align: right; font-size: 11px; font-family: Verdana, Arial, Helvetica, sans-serif">/500 Characters</td>
										 </tr>
										 </table>
											
										<%}else { %>
										 <table  cellpadding="0" cellspacing="0" width="100%" bgcolor="#E3EBE5" >
										 <tr>
										 <td width="35%" class="heading" style="font-size: 12px;">Remarks, If any : </td>
										 <td width="65%"><html:textarea property="remarks" style="width: 83%" styleId="remarks" cols="80" rows="8" onkeypress="return imposeMaxLength1(this, 499);" onkeyup="len_display2(this,0,'long_len2')"></html:textarea>
										 <input type="text" id="long_len2" value="0" class="len2" size="2" readonly="readonly" style="border: none; background-color: #E3EBE5; text-align: right; font-size: 11px; font-family: Verdana, Arial, Helvetica, sans-serif">/500 Characters</td>
										 </tr>
										 </table>
											<%} %>
											</td>
											</tr>
								</table>
							</td>	
						</tr>
						<tr>
			                   <td colspan="2" height="100"><div align="center">
			                   <% if(CMSConstants.LINK_FOR_CJC){ %>
			                   	<html:button property="" value="Next" styleClass="classname" onclick="submitDetailsForCJC()" />&nbsp; <html:button property="" value="Close" styleClass="classname" onclick="cancelAction()"></html:button>
			                   <%}else{ %>
								<html:button property="" value="Next" styleClass="classname" onclick="submitDetails()" />&nbsp; <html:button property="" value="Close" styleClass="classname" onclick="cancelAction()"></html:button>
								<%} %>
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
					<td><img src="images/st_Tright_03_05.gif" width="9" height="29"></td>
					<td width="0" background="images/st_TcenterD.gif"></td>
					<td><img src="images/st_Tright_02.gif" width="9" height="29"></td>
				</tr>
			</table>
</html:form>
