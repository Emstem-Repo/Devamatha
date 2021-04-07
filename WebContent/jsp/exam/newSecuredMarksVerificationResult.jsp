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
function refreshThePage(){
	document.getElementById("method").value="getCandidates";
	document.newSecuredMarksVerficationForm.submit();
}
function resetValue() {
	document.location.href = "securedMarksVerification.do?method=initSecuredMarksVerification";
}
	var latestId;
	var c = 0;
	var idq;
	var dif=false;
	function check(countId)
	{
		var t = new Array();
		t = countId.split("_");
		if(document.getElementById("registerNo_"+t[1]).value.length>0)
		{
			dif=true;
			return true;
		}
		else
		{
			dif=false;
			return false;
		}
	}

	function getStudentDetails(registerNo, id) {
		var isDuplicate=false;
		document.getElementById("div_"+id).innerHTML ="";
		var dupCou=0;
		if (registerNo.length != 0) {
		for ( var int = 0; int <10; int++) {
			if(int!=id){
				var regNo=document.getElementById("registerNo_"+int).value;
				if(regNo!=null && regNo!='' && regNo.length!=0){
					if(registerNo==regNo){
						isDuplicate=true;
						dupCou=int+1;
					}
				}
			}
		}
		if(isDuplicate){
			document.getElementById("div_"+id).innerHTML = "Duplicate Reg No at Line No:"+dupCou;
			eval(document.getElementById("registerNo_"+id)).focus();
		}else{
			var url = "securedMarksVerification.do";
			c=id;
			var args = "method=checkStudentDetails&regNo="+registerNo;
			requestOperation(url, args, updateRegNo);
			}
		}
	}
	function updateRegNo(req) {
		var responseObj = req.responseXML.documentElement;
		var value = responseObj.getElementsByTagName("value");
		var isMsg=false;
		if(value!=null){
			for ( var I = 0; I < value.length; I++) {
				if(value[I].firstChild!=null){
				var temp = value[I].firstChild.nodeValue;
				document.getElementById("div_"+c).innerHTML = temp;
				eval(document.getElementById("registerNo_"+c)).focus();
				isMsg=true;
				}
			}
			}
				if(isMsg!=true){
					var regMsg=responseObj.getElementsByTagName("regMsg");
					if(regMsg!=null){
						for ( var I = 0; I < regMsg.length; I++) {
							if(regMsg[I].firstChild!=null){
							var temp = regMsg[I].firstChild.nodeValue;
							document.getElementById("regDiv_"+c).innerHTML = temp;
							}
						}
						}
					var items = responseObj.getElementsByTagName("fields");
				for (var i = 0 ; i < items.length ; i++) {
					if(items[i]!=null){
			         var detailId = items[i].getElementsByTagName("detailId")[0].firstChild.nodeValue;
				     var studentId = items[i].getElementsByTagName("studentId")[0].firstChild.nodeValue;
				     var classId = items[i].getElementsByTagName("classId")[0].firstChild.nodeValue;
				     var courseId = items[i].getElementsByTagName("courseId")[0].firstChild.nodeValue;
				     var schemeNo = items[i].getElementsByTagName("schemeNo")[0].firstChild.nodeValue;
				     var year = items[i].getElementsByTagName("year")[0].firstChild.nodeValue;
				     document.getElementById("detailId_"+c).value = detailId;
				     document.getElementById("studentId_"+c).value = studentId;
				     document.getElementById("classId_"+c).value = classId;
				     document.getElementById("courseId_"+c).value = courseId;
				     document.getElementById("schemeNO_"+c).value = schemeNo;
				     document.getElementById("year_"+c).value = year;
					 var subType=document.getElementById("subjectType").value;
					 if(subType=='T' || subType=='t'){
						 document.getElementById("marks_"+c).value =items[i].getElementsByTagName("tMarks")[0].firstChild.nodeValue;
					 }
					 if(subType=='P' || subType=='p'){
						 document.getElementById("marks_"+c).value =items[i].getElementsByTagName("pMarks")[0].firstChild.nodeValue;
					 }
				     var mistake = items[i].getElementsByTagName("mistake")[0].firstChild.nodeValue;
				     document.getElementById("dupMistake_"+c).value = mistake;
					 if(mistake!=null && mistake!='' && mistake=='on'){
				    	 document.getElementById("mistake_"+c).checked = true;
					 }
					}
				 }
			}
		
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
			var ghi=abc.substring(11);
			var jkl=parseInt(ghi)+1;
			var mno="registerNo_"+jkl;
			latestId=mno;
			idq=jkl;
			chk=false;
			eval(document.getElementById(mno)).focus();
			return false;
		}else{
			var abc=count;
			var ghi=abc.substring(11);
			var jkl=parseInt(ghi)+1;
			var mno="registerNo_"+jkl;
			latestId=mno;
			idq=jkl;
			chk=true;
			return false;
		}
	}

	function movenextMark(val, e, count) {
		
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
			var mno="registerNo_"+jkl;
			if(mno == "registerNo_10"){
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

	function submitForm(){
		var size = parseInt(document.getElementById("registerNoCount").value);
		var pos = -1;
		for ( var count = 0; count <= size - 1; count++) {
			var regNo = document.getElementById("registerNo_" + count).value;
			if(regNo == null || trim(regNo) == ""){
				pos = count;
				break;
			}			
		}		
		if(pos < 0){
			if(!checkErrorDiv()){
				document.newSecuredMarksVerficationForm.submit();
			}else{
				document.getElementById("formbutton").style.display="block";
				}	
		}else{
			if(!checkErrorDiv()){
				submitConfirm = confirm("You have not entered all 10 numbers. Are you sure the entry is correct");
				if (submitConfirm) {
					document.newSecuredMarksVerficationForm.submit();
				}
				else{
					document.getElementById("registerNo_"+pos).focus();
				}
			}else{
				document.getElementById("errorMessage").innerHTML="Please Clear the Validations";
				document.getElementById("formbutton").style.display="block";	
			}
		}
	}
	function beforeSubmit(){
		document.getElementById("formbutton").style.display="none";
		setTimeout("submitForm()", 1500);
	}
	var d1;
	function getMarksDifference(marks,element)	{
		var url = "securedMarksEntry.do";
		d1=element;
		var regVal=document.getElementById("div_"+ d1).innerHTML;
		if(regVal!=null && regVal!='' && regVal.length>0){
			document.getElementById("marks_"+d1).value="";
			eval(document.getElementById("registerNo_"+d1)).focus();
		}else{
			document.getElementById("marksDiv_"+ d1).innerHTML = "";
			if(document.getElementById("evalu_"+d1)!=null){
				document.getElementById("evalu_"+d1).innerHTML="";
			}
			var	registerNo=document.getElementById("registerNo_"+element).value;
			var	studentId=document.getElementById("studentId_"+element).value;
			var	course=document.getElementById("courseId_"+element).value;
	        var schemeNo=document.getElementById("schemeNO_"+c).value;
	        var year=document.getElementById("year_"+c).value;
	        
			if (registerNo.length != 0 && marks.length !=0) {
				var args = "method=validateStudentMarks&examMark="+marks+"&studentId="+studentId+"&course="+course+"&year="+year+"&schemeNo="+schemeNo;
				requestOperationProgram(url, args, updateMarksDifference);
			}
			else
			{
				document.getElementById("marksDiv_"+ d1).value = "";
			}
		}
	}
	function updateMarksDifference(req)
	{
		dif=false;
		var responseObj = req.responseXML.documentElement;
		var value = responseObj.getElementsByTagName("value");
		if(value!=null){
			for ( var I = 0; I < value.length; I++) {
				if(value[I].firstChild!=null && value[I].firstChild!='' && value[I].firstChild.length!=0){
					var temp = value[I].firstChild.nodeValue;
					document.getElementById("marksDiv_"+d1).innerHTML = temp;
					eval(document.getElementById("marks_"+d1)).focus();
				}
			}
		}
		if(document.getElementById("evaluatorId")!=null){
			var evalId=document.getElementById("evaluatorId").value;
			if(evalId!=''){
				var ev = responseObj.getElementsByTagName("evalu");
				if(ev!=null){
					for ( var I = 0; I < ev.length; I++) {
						if(ev[I].firstChild!=null && ev[I].firstChild!='' && ev[I].firstChild.length!=0){
							var temp = ev[I].firstChild.nodeValue;
							document.getElementById("evalu_"+d1).innerHTML = temp;
						}
					}
				}
			}
		}
	}
	function checkErrorDiv(){
		var isDuplicate=false;
		for ( var int = 0; int <10; int++) {
			var regNo=document.getElementById("div_"+int).innerHTML;
			if(regNo!=null && regNo!='' && regNo.length!=0){
					isDuplicate=true;
			}
			var marksDiv=document.getElementById("marksDiv_"+int).innerHTML;
			if(marksDiv!=null && marksDiv!='' && marksDiv.length!=0){
					isDuplicate=true;
			}
		}
		return isDuplicate;
	}

	function disableMarks(count){
		if (document.getElementById("mistake_"+count).checked) {
			document.getElementById("marks_"+count).disabled = false;
		} else {
			document.getElementById("marks_"+count).disabled = true;
		}
	}
</SCRIPT>
<html:form action="/securedMarksVerification" styleId="secureForm" focus="registerNo_0">
	<html:hidden property="formName" value="newSecuredMarksVerficationForm"
		styleId="formName" />
	<html:hidden property="method" styleId="method" value="saveMarks" />
	<html:hidden property="pageType" value="1" styleId="pageType" />

	<table width="99%" border="0">

		<tr>
			<td><span class="heading"><span class="Bredcrumbs">Exam
			>> Secured Marks Verification &gt;&gt;</span></span></td>
		</tr>

		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="heading_white">Student
					Marks Entry - Single Student Single Subject</td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="heading">
					<div align="right"><FONT color="red"> <span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields"/></span></FONT></div>
							<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
							<FONT color="green"> <html:messages id="msg"
								property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
								<br>
							</html:messages> </FONT></div>
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
										name="newSecuredMarksVerficationForm" property="subjectCode" /></td>


									<td width="28%" height="25" class="row-odd">
									<div align="right">Subject Name :</div>
									</td>
									<td class="row-even" width="23%" height="25"><bean:write
										name="newSecuredMarksVerficationForm" property="subjectName" /></td>
								</tr>
								<tr>
									<td width="23%" height="25" class="row-odd">
									<div align="right">Subject Type :</div>
									</td>
									<td class="row-even" width="23%" height="25"><bean:write
										name="newSecuredMarksVerficationForm" property="subjectType" /></td>

									<td width="28%" height="25" class="row-odd">
									<div align="right">Exam Name :</div>
									</td>
									<td class="row-even" width="23%" height="25"><bean:write
										name="newSecuredMarksVerficationForm" property="examName" /></td></tr>
									<tr>
										<td height="25" class="row-odd">
										<div align="right">Evaluator Type :</div>
										</td>
										<td class="row-even" width="23%" height="25">&nbsp;
											<bean:write name="newSecuredMarksVerficationForm" property="evaluatorType"/>
										</td>
										<td height="25" class="row-odd">
										<div align="right">Answer Script Type :</div>
										</td>
										<td class="row-even" width="23%" height="25"><bean:write
											name="newSecuredMarksVerficationForm" property="answerScriptType" /></td>
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
										<td height="25" class="row-odd">Register No.</td>
									<logic:equal value="T" name="newSecuredMarksVerficationForm"
										property="subjectType">
										<td class="row-odd">Theory Marks</td>
									</logic:equal>
									<logic:equal value="P" name="newSecuredMarksVerficationForm"
										property="subjectType">
										<td class="row-odd">Practical Marks</td>
									</logic:equal>
									<logic:notEmpty property="evaluatorType"
										name="newSecuredMarksVerficationForm">
										<td class="row-odd">Previous Evaluator Marks</td>
									</logic:notEmpty>
									<td class="row-odd">Mistake</td>
									<td class="row-odd">Re test</td>
								</tr>
								<nested:hidden property="subjectType" name="newSecuredMarksVerficationForm" styleId="subjectType" />
								<% int registerNoCount = 0; %>
								<nested:iterate property="mainList" indexId="count">
									<c:choose>
										<c:when test="${count%2 == 0}">
											<tr class="row-even">
										</c:when>
										<c:otherwise>
											<tr class="row-white">
										</c:otherwise>
									</c:choose>
										<% 
											String onClickMethod="getStudentDetails(this.value,"+count+")"; 
											String divId="div_"+count;
											String regDivId="regDiv_"+count;
											String marksMethod="getMarksDifference(this.value,"+count+")";
											String registerStyleId="registerNo_"+count;
											String marksId="marks_"+count;
											String marksDivId="marksDiv_"+count;
											String detailId="detailId_"+count;
											String studentId="studentId_"+count;
											String classId="classId_"+count;
											String courseId="courseId_"+count;
											String schemeNO="schemeNO_"+count;
											String year="year_"+count;
											String dupMistake="dupMistake_"+count;
										%>
										<nested:hidden property="id" styleId="<%=detailId %>"></nested:hidden>
										<nested:hidden property="studentId" styleId="<%=studentId %>"></nested:hidden>
										<nested:hidden property="classId" styleId="<%=classId %>"></nested:hidden>
										<nested:hidden property="courseId" styleId="<%=courseId %>"></nested:hidden>
										<nested:hidden property="schemeNo" styleId="<%=schemeNO %>"></nested:hidden>
										<nested:hidden property="year" styleId="<%=year %>"></nested:hidden>
										<nested:hidden property="dupMistake" styleId="<%=dupMistake %>"></nested:hidden>
										
										<td width="10%" height="25" align="center"><%=count+1%> </td>
										<td width="25%" height="25">
										<logic:equal value="yes" name="newSecuredMarksVerficationForm" property="checkBox">
											<nested:password property="registerNo" size="15" onkeydown="movenext(this.name, event, this.id)"
												onchange='<%=onClickMethod%>' styleId="<%=registerStyleId %>"></nested:password><div id="<%=divId %>"></div>
												<div id="<%=regDivId %>"></div>
										</logic:equal>
										<logic:equal value="no" name="newSecuredMarksVerficationForm" property="checkBox">
											<nested:text property="registerNo" size="15" onkeydown="movenext(this.name, event, this.id)"
												onchange='<%=onClickMethod%>' styleId="<%=registerStyleId %>"></nested:text><div id="<%=divId %>"></div>
												<div id="<%=regDivId %>"></div>
										</logic:equal>
										</td>
										<nested:equal value="T" name="newSecuredMarksVerficationForm" property="subjectType">
											<td height="25">
												<nested:text property="theoryMarks" styleId='<%=marksId%>' 
													size="15" maxlength="4" onkeydown="movenextMark(this.name, event, this.id)"
													  onkeypress="return check(this.id)" disabled="true"/><div id="<%=marksDivId %>"></div>
											</td>
										</nested:equal>
										<nested:equal value="P" name="newSecuredMarksVerficationForm" property="subjectType">
											<td height="25">
												<nested:text property="practicalMarks" styleId='<%=marksId%>' 
													size="15" maxlength="4" onkeydown="movenextMark(this.name, event, this.id)"
													  onkeypress="return check(this.id)" disabled="true"/><div id="<%=marksDivId %>"></div>
											</td>
										</nested:equal>
										<logic:notEmpty property="evaluatorType"
										name="newSecuredMarksVerficationForm">
										<td><div id='<%="evalu_" + count%>'></div> </td>
										</logic:notEmpty>
										<td width="12%" align="center" >
										<div align="center"><nested:checkbox styleId='<%="mistake_" + count%>'  property="mistake"/></div>
										</td>
										<td width="11%" align="center" >
										<div align="center"><nested:checkbox styleId='<%="retest_" + count%>' property="retest" disabled="true"  indexed="true"/>
										<nested:hidden styleId='<%="retestHidden_" + count%>' property="retest" />
										</div>
										</td>
									</tr>
									<%registerNoCount = registerNoCount + 1; %>
								</nested:iterate>
							</table>
							</td>
							<td width="5" background="images/right.gif"></td>
							<td>
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
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>

					<td class="heading">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr><td style="height: 10px" align="center" colspan="5"></td></tr>
						<tr>
							<td width="40%" height="35">
							<div align="right">
							<html:button property=""
								onmousedown="beforeSubmit()"  styleClass="formbutton" value="Submit" styleId="formbutton"></html:button>
							</div>
							</td>
							<td width="1%"></td>
							<td width="7%"><input type="button" class="formbutton"
								value="Cancel" onclick="resetValue()" /></td>
							<td width="1%"></td>
							<td width="52%">&nbsp;</td>
						</tr>						
						<tr><td style="height: 10px" align="center" colspan="5"></td></tr>						
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