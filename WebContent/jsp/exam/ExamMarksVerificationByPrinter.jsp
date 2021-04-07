<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@page import="java.util.Map,java.util.HashMap"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>




<SCRIPT>
nextfield = "box1"; // name of first box on page
netscape = "";
ver = navigator.appVersion; len = ver.length;
for(iln = 0; iln < len; iln++) if (ver.charAt(iln) == "(") break;
netscape = (ver.charAt(iln+1).toUpperCase() != "C");

function keyDown(DnEvents) { // handles keypress
k = (netscape) ? DnEvents.which : window.event.keyCode;
if (k == 13) {
if (nextfield == 'box20')
	{
	return true;
	} 
else {
	return false;
      }
   }
}
document.onkeydown = keyDown; 
if (netscape) document.captureEvents(Event.KEYDOWN|Event.KEYUP);




	function resetValue() {
		
		document.location.href = "ExamSecuredMarksVerification.do?method=initExamSecuredMarksVerificationByPrinter";
	}
	
	
var c=0;
	function getDecryptRegNo(registerNo,id,type) {
		var url = "AjaxRequest.do";
		c=id;
		var evaluatorId = "";
		examId=document.getElementById("examId").value;
		subjectId=document.getElementById("subject").value;
		if(document.getElementById("evaluatorType") != null){
			evaluatorId=document.getElementById("evaluatorType").value;
		}
		answerScriptId=document.getElementById("answerScriptType").value;
		if (registerNo.length != 0) {
			var args = "method=getDecryptRegNo&appRegRollno=" + registerNo+"&examName="+examId+"&subjectId="+subjectId+"&evaluatorId="+evaluatorId+"&answerScriptId="+answerScriptId+"&type="+type;
					requestOperation(url, args, updateRegNo);
		}
		
		
	}
	function getPacketNo()
	{
		var packetNo=document.getElementById("packetNo").value;
		if(packetNo==null || packetNo=="")
		{
			alert("Please Enter packet No");
			document.getElementById('packetNo').focus();
		}
		
	}
	
	function getDecryptRegNo1(registerNo,id,type) {
		
		var size = parseInt(document.getElementById("registerNoCount").value);
		var pos = -1;
		var duplicate = false;
		for ( var count = 0; count <= size - 1; count++) {
			var regNo = document.getElementById("regNo_" + count).value;
			if(regNo != null || trim(regNo) != ""){
				if(trim(regNo) == registerNo && parseInt(id) != count){
					document.getElementById("display_" + id).innerHTML = "Duplicate Register Number";
					document.getElementById("theoryMarks_" + id).value = "";
					document.getElementById("practicalMarks_" + id).value = "";
					document.getElementById("theoryMarksHidden_" + id).value = "";
					document.getElementById("practicalMarksHidden_" + id).value = "";
					duplicate = true;
					break;
				}
			}
			if(regNo == null || trim(regNo) == ""){
				pos = count;
				break;
			}			
		}
		if(!duplicate){
			var url = "AjaxRequest.do";
			var evaluatorId = "";
			c=id;
			examId=document.getElementById("examId").value;
			subjectId=document.getElementById("subject").value;
			if(document.getElementById("evaluatorType") != null){
				evaluatorId=document.getElementById("evaluatorType").value;
			}
			answerScriptId=document.getElementById("answerScriptType").value;
			var examType=document.getElementById("examType").value;
			var schemeNo=document.getElementById("schemeNo").value;
			var subjectType=document.getElementById("subjectType").value;
			
			if (registerNo.length != 0) {
				var args = "method=getDecryptRegNo1&appRegRollno=" + registerNo+"&examName="+examId+"&subjectId="+subjectId+"&evaluatorId="+evaluatorId+"&answerScriptId="+answerScriptId+"&type="+type+"&examType="+examType+"&schemeNo="+schemeNo+"&subjectType="+subjectType;
						requestOperation(url, args, updateRegNo);
			}
			document.getElementById("theoryMarks_"+id).focus();
		}else{
			document.getElementById("regNo_"+id).focus();	
		}
	}
	
	function moveToNext(registerNo,id){
		alert("--Count--"+id);
		document.getElementById("regNo_"+id).focus();
	}
	function moveToNext1(){
		//alert("--Count--"+id);
		//document.getElementById("regNo_"+1).focus();
	}
	function updateRegNo(req) {
		updateDecryptRegNo(req, c);
	}
	function donotEnter() {
		return true;
		//if (document.getElementById("boxCheck").value == "true") {
		//	return false;
		//} else {
		//		return true;
		//}
	}
	function getMarksByRegNo() {
		var registerNo = document.getElementById("registerNo").value;

		if (registerNo != '') {
			var args = "method=getMarksByRegNo&marksForReg=" + registerNo;
			var url = "AjaxRequest.do";
			requestOperation(url, args, updateClass);
		}
	}
	function updateClass(req) {
		updateMarksByRegNO(req, "registerNo");
	}
	
	function movenext(val, e, count) {
		
		var keynum;
		var keychar;
		var numcheck;
		
		if (window.event) // IE
		{
			keynum = e.keyCode;
		} else if (e.which) // Netscape/Firefox/Opera
		{
			keynum = e.which;
		}
		if (keynum == 13) {
			var abc=count;
			var ghi=abc.substring(6);
			var jkl=parseInt(ghi)+1;
			var mno="regNo_"+jkl;
			if(mno == "regNo_10"){
				document.getElementById('formbutton').focus();
			}
			else
			{	
				eval(document.getElementById(mno)).focus();
			}	
			return false;
		}
			
	}
	function limitEnd () {
		if (window.event) // IE
		{
			keynum = e.keyCode;
		} else if (e.which) // Netscape/Firefox/Opera
		{
			keynum = e.which;
		}
		if (keynum == 13) {
			alert("hi");
		}
	}
	
	function movenextMark(val, e, count) {
		//alert("MoveNextMark"+count);
		var keynum;
		var keychar;
		var numcheck;
		var t = new Array();
		t = count.split("_");
		//alert(t[1]);
		//document.getElementById("printIcon_"+t[1]).disabled = false;
		for(var i=0; i<10; i++){
			if(t[1] == i){
				document.getElementById("printIcon_"+t[1]).disabled = false;
			}else{
				document.getElementById("printIcon_"+i).disabled = true;
			}
		}
		
		if (window.event) // IE
		{
			keynum = e.keyCode;
		} else if (e.which) // Netscape/Firefox/Opera
		{
			keynum = e.which;
		}
		if (keynum == 13) {
			var abc=count;
			var ghi=abc.substring(12);
			var jkl=parseInt(ghi)+1;
			var mno="regNo_"+jkl;
			var jkl1=parseInt(ghi)-1;
			var mno1="regNo_"+jkl1;
			var printing = 0;
			if(document.getElementById("theoryMarks_"+ghi).value != null && 
					document.getElementById("theoryMarks_"+ghi).value.length > 3){
				document.getElementById("theoryMarks_"+ghi).focus();
				document.getElementById("marksError_" + ghi).innerHTML = "Enter Valid Mark.";
				printing = 1;
				return false;
			}else{
				document.getElementById("marksError_" + ghi).innerHTML = "";
			}
			if(document.getElementById("regNo_"+ghi).value != null && printing != 1){
				var registerNo = document.getElementById("regNo_"+ghi).value;
				var url = "ExamSecuredMarksVerification.do?method=printRS&registerNo="+registerNo;
				myRef = window.open(url,"TC","left=20,top=20,width=600,height=500,toolbar=2,resizable=0,scrollbars=1");
			}
			if(jkl == "10"){
				submitConfirm = confirm("You have entered all 10 numbers. Please Click the Submit Button.");
				if (submitConfirm) {
					//alert(mno);
					document.ExamSecuredMarksVerificationForm.submit();
				}
				else{
					//document.getElementById("registerNo_"+pos).focus();
				}
				if(dif){
					getMarksDifference(document.getElementById(latestId).value,idq);
				}
				document.getElementById('formbutton').focus();
			}else{
				eval(document.getElementById(mno)).focus();
			}
			chk=false;
			idq=ghi;
			return false;
		}else{
			var abc=count;
			var ghi=abc.substring(12);
			chk=true;
			idq=ghi;
			return false;
		}

	
	}
	 var p =0;
	function upDatePrint(req){
		updatePrinter(req, p);
	}
	
	function rePrint(val, e, count) {
		var t = new Array();
		t = count.split("_");
		var abc=count;
		var ghi=abc.substring(12);
		var jkl=parseInt(ghi)+1;
		var mno="regNo_"+jkl;
		var schemeNo=document.getElementById("schemeNo").value;
		if(document.getElementById("regNo_"+t[1]).value != null){
			var registerNo = document.getElementById("regNo_"+t[1]).value;
			var url = "ExamSecuredMarksVerification.do?method=printRS&registerNo="+registerNo+"&schemeNo="+schemeNo;
			myRef = window.open(url,"TC","left=20,top=20,width=600,height=500,toolbar=2,resizable=0,scrollbars=1");
		}
		
	} 
	function check(countId)
	{
		var t = new Array();
		t = countId.split("_");
		if(document.getElementById("regNo_"+t[1]).value.length>0)
		{
			return true;
		}
		else
		{
			return false;
		}
	
	}

	function submitForm(){
		
		var size = parseInt(document.getElementById("registerNoCount").value);
		var pos = -1;
		var printing = 0;
		for ( var count = 0; count <= size - 1; count++) {
			var regNo = document.getElementById("regNo_" + count).value;
			if(regNo == null || trim(regNo) == ""){
				pos = count;
				break;
			}			
		}
		var jkl=parseInt(pos)-1;
		if(document.getElementById("regNo_"+0).value == ""){
			document.getElementById("regNo_"+0).focus();
			document.getElementById("marksError_"+0).innerHTML = "Enter Valid Reg No.";
			printing = 1;
			return false;
		}else{
			document.getElementById("marksError_"+0).innerHTML = "";
		}
		if(document.getElementById("theoryMarks_"+jkl) != null && document.getElementById("theoryMarks_"+jkl).value == ""){
			document.getElementById("theoryMarks_"+jkl).focus();
			document.getElementById("marksError_"+jkl).innerHTML = "Enter Valid Mark.";
			printing = 1;
			return false;
		}else{
			if(document.getElementById("marksError_"+jkl) != null){
				document.getElementById("marksError_"+jkl).innerHTML = "";
			}
		}

		if(pos < 0 && printing == 1){
			document.ExamSecuredMarksVerificationForm.submit();	
		}else{
			submitConfirm = confirm("You have not entered all 10 numbers. Are you sure the entry is correct");
			if (submitConfirm) {
				document.ExamSecuredMarksVerificationForm.submit();
			}
			else{
				document.getElementById("regNo_"+pos).focus();
			}
		}
		//document.ExamSecuredMarksVerificationForm.submit();
	}

	function resetStudentList()
	{
		for(var i=0;i<10;i++)
		{
			document.getElementById("regNo_"+i).value="";
			document.getElementById("theoryMarks_"+i).value="";
			document.getElementById("practicalMarks_"+i).value="";
			document.getElementById("display_"+i).innerHTML="";
		}
		document.getElementById("error").innerHTML="";		
	}	
	function clearPacket()
	{
		var packetNo=document.getElementById("packetNo").value;
		if(packetNo!=null){
			document.getElementById("packetNo").value="";
		}	
	}
	
</SCRIPT>
<body onload=clearPacket()></body>
<html:form action="/ExamSecuredMarksVerification.do" focus="packetNo" >
	<html:hidden property="formName"
		value="ExamSecuredMarksVerificationForm" styleId="formName" />
	<html:hidden property="method" styleId="method" value="onSubmit1" />

	<html:hidden property="pageType" value="2" styleId="pageType" />

	<html:hidden property="studentId"/>
	<html:hidden property="subjectCode" />
	<html:hidden property="subject" styleId="subject" />
	<html:hidden property="examId" styleId="examId" />
	<html:hidden property="subjectType" styleId="subjectType"/>
	<html:hidden property="subjectName" />
	<html:hidden property="examName" />
	<html:hidden property="evaluatorTypeId" styleId="evaluatorId" />
	<html:hidden property="answerScriptTypeId" styleId="answerScriptId" />
	<html:hidden property="boxCheck" styleId="boxcheck" />
	<html:hidden property="checkBox" styleId="checkBox" />
	<html:hidden property="schemeNo" styleId="schemeNo" />
	<html:hidden property="examType" styleId="examType" />

	<table width="99%" border="0">

		<tr>
			<td><span class="heading"><span class="Bredcrumbs">Exam
			>> Exam Verification Marks Entry &gt;&gt;</span></span></td>
		</tr>

		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="heading_white">Student
					Marks Entry - Single Student Single Subject </td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>

					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="heading"><FONT color="red">
					<div id="error"> <html:errors></html:errors></div>
					</FONT>
					<FONT color="green"> <html:messages id="msg"
						property="messages" message="true">
						<c:out value="${msg}" escapeXml="false"></c:out>
						<br>
					</html:messages> </FONT>
					<div align="right" class="mandatoryfield">*Mandatory fields</div>
					</td>
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
								<tr>
									<td width="23%" height="25" class="row-odd">
									<div align="right">Subject Code :</div>
									</td>
									<td class="row-even" width="23%" height="25"><bean:write
										name="ExamSecuredMarksVerificationForm" property="subjectCode" /></td>


									<td width="28%" height="25" class="row-odd">
									<div align="right">Subject Name :</div>
									</td>
									<td class="row-even" width="23%" height="25"><bean:write
										name="ExamSecuredMarksVerificationForm" property="subjectName" /></td>
								</tr>
								<tr>
									<td width="23%" height="25" class="row-odd">
									<div align="right">Subject Type :</div>
									</td>
									<td class="row-even" width="23%" height="25"><bean:write
										name="ExamSecuredMarksVerificationForm" property="subjectType" /></td>

									<td width="28%" height="25" class="row-odd">
									<div align="right">Exam Name :</div>
									</td>
									<td class="row-even" width="23%" height="25"><bean:write
										name="ExamSecuredMarksVerificationForm" property="examName" /></td>
								</tr>		
									<tr>
										<td height="25" class="row-odd">
										<div align="right">Evaluator Type :</div>
										</td>
										<td class="row-even" width="23%" height="25">
										<c:if test="${ExamSecuredMarksVerificationForm.evaluatorType!= null && ExamSecuredMarksVerificationForm.evaluatorType!= ''}">
										
											<logic:notEmpty name="ExamSecuredMarksVerificationForm" property="listEvaluatorType">
												<html:select property="evaluatorType" styleClass="combo" styleId="evaluatorType" name="ExamSecuredMarksVerificationForm" style="width:120px" onchange="resetStudentList();">
													<html:optionsCollection name="ExamSecuredMarksVerificationForm" property="listEvaluatorType" label="value" value="key" />
												</html:select>
											</logic:notEmpty>
											<logic:empty name="ExamSecuredMarksVerificationForm" property="listEvaluatorType">
												<html:hidden property="evaluatorType" styleId="evaluatorType" value=""/>
											</logic:empty>
										</c:if>
										</td>
										<td height="25" class="row-odd">
										<div align="right">Answer Script Type :</div>
										</td>
										<td class="row-even" width="23%" height="25">
										<logic:notEmpty name="ExamSecuredMarksVerificationForm" property="listAnswerScriptType">
											<html:select  property="answerScriptType" styleClass="combo" styleId="answerScriptType" name="ExamSecuredMarksVerificationForm" style="width:120px" onchange="resetStudentList();">
												<html:optionsCollection property="listAnswerScriptType" name="ExamSecuredMarksVerificationForm" label="value" value="key" />
											</html:select>	
										</logic:notEmpty>
										<logic:empty name="ExamSecuredMarksVerificationForm" property="listAnswerScriptType">
												<html:hidden property="answerScriptType" styleId="answerScriptType" value=""/>
										</logic:empty>
										</td>
									</tr>
									<tr>
									<td class="row-odd" align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.ExamSecuredMarks.Packet.No"/>:</td>
								<td class="row-even" colspan="3"><html:text	property="packetNo" styleId="packetNo" size="15" maxlength="20" /></td>
									</tr>
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
							<table width="100%" cellspacing="1" cellpadding="0">
								<tr>
									<td height="25" class="row-odd">Sl No.</td>
									<logic:equal value="true" property="regNoOrRollNumber"
										name="ExamSecuredMarksVerificationForm">
										<td height="25" class="row-odd">Register No.</td>
									</logic:equal>
									<logic:equal value="false" property="regNoOrRollNumber"
										name="ExamSecuredMarksVerificationForm">
										<td height="25" class="row-odd">Roll No.</td>
									</logic:equal>
									<td class="row-odd">Marks</td>
									<td class="row-odd">Re Print</td>
									<td class="row-odd"></td>
									<td class="row-odd"></td>
									
									
								</tr>
								<% int registerNoCount = 0; %>
								<nested:iterate property="listSingleStudents" indexId="count">
									<tr><td style="height: 10px" align="center" colspan="7"></td></tr>
									<tr> 
									<td width="5%" height="25" class="row-even" align="center">
									<%=count + 1%>
									</td>
									<%String id = "regNo_"+count; 
									String studentId="studentId_"+count;%>
										<c:if
											test="${ExamSecuredMarksVerificationForm.boxCheck == true}">
											<td width="27%" height="25" class="row-even">
											<%
											String s3 = "getDecryptRegNo1(this.value, "+ count.toString() + ",'password')";
											%> <nested:password property="regNo" onblur='<%=s3%>' onfocus='<%="nextfield =box"+(count+1)+";"%>' onclick="getPacketNo()"
												size="15" onkeypress="return donotEnter()" onkeydown="movenext(this.name, event, this.id)" styleId='<%=id %>' /><strong
												id='<%="display_" + count%>'></strong>
												<nested:hidden property="studentId"
													styleId='<%=studentId %>' />
												</td>
										</c:if>
										<c:if
											test="${ExamSecuredMarksVerificationForm.boxCheck ==false}">
											<td width="27%" height="25" class="row-even">
											<%
												String s3 = "getDecryptRegNo1(this.value, "
																		+ count.toString() + ",'text')";
											%> <nested:text property="regNo" size="15" onblur='<%=s3%>' styleId='<%=id %>' onkeydown="movenext(this.name, event, this.id)" />
											<strong id='<%="display_" + count%>'></strong>
											
											
											</td>
										</c:if>


										<td width="18%" class="row-even">
										<%
											String s4 = "";
											%>
											<STRONG>
											
											<nested:text
											property="theoryMarks" styleId='<%="theoryMarks_" + count%>'
											size="3" maxlength="4" disabled="false" onkeydown="movenextMark(this.name, event, this.id)"
												onblur='' onkeypress=" return check(this.id)"   />
												<nested:hidden property="studentName" styleId='<%="studentName_"+ count %>'></nested:hidden>
												</STRONG>
										<FONT color="red">
											<strong id='<%="marksError_" + count%>'></strong></FONT>
												
										</td>
										<td width="18%" class="row-even" align="center">
											<input type="button" class="formbutton"
													value="Reprint" onclick="rePrint(this.name, event, this.id)" id="printIcon_<%=count %>" disabled="disabled"/>
											
										</td>
										
										<td width="12%" align="center" class="row-even">
										
										</td>
										<td width="11%" align="center" class="row-even">
										</td>
									</tr>									
									<tr><td style="height: 10px" align="center" colspan="7"></td></tr>									
									<%registerNoCount = registerNoCount + 1; %>
								</nested:iterate>
							</table>
							</td>
							<td width="5" background="images/right.gif">
							<input
								type="hidden" name="registerNoCount" id="registerNoCount"
								value='<%=registerNoCount%>' />
							</td>
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
					<td class="heading">&nbsp;</td>
					<td valign="top" background="images/Tright_3_3.gif"></td>
				</tr>
				<!-- <tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>

					<td class="heading">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
								<td class="row-odd" align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.ExamSecuredMarks.Packet.No"/>:</td>
								<td class="row-even"><html:text	property="packetNo" styleId="packetNo" size="15" maxlength="20" /></td>
						</tr>
					</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif"></td>
				</tr>-->
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="heading">&nbsp;</td>
					<td valign="top" background="images/Tright_3_3.gif"></td>
				</tr>
				
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>

					<td class="heading">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="40%" height="35">
							<div align="right">
							<html:button property="" onclick="submitForm()" styleClass="formbutton" styleId="formbutton" value="Submit"></html:button>
							</div>
							</td>
							<td width="1%"></td>
							<td width="7%"><input type="button" class="formbutton"
								value="Cancel" onclick="resetValue()" /></td>
							<td width="1%"></td>
							<td width="52%">&nbsp;</td>

						</tr>
					</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="heading">&nbsp;</td>
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
<script>
hook=false;
</script>