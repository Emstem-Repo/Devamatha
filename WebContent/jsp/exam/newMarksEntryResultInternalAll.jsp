<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@page import="java.util.Map,java.util.HashMap"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>

<head>
<script>
	function goToFirstPage(method) {
		document.location.href = "marksEntry.do?method="+method;
	}
	
	function goToPrintPage(method) {
		var url ="marksEntry.do?method="+method;
		
		myRef = window.open(url,"InternalMarksPrint","left=20,top=20,width=800,height=500,toolbar=1,resizable=0,scrollbars=1");
			
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
		if (keynum == 40 ) {
		var abc=count;
		var def=abc.substring(7);
		var ghi=abc.substring(5);
		var jkl=parseInt(ghi)+1;
		var mno="test_"+jkl+"_"+def;
		//please check whether the control is found ....then move to next
		eval(document.getElementById(mno)).focus();
		//pqr.focus();
		return true;
		}
	}

	function IsNumeric(sText)
	{
	   var ValidChars = "0123456789.";
	   var IsNumber=true;
	   var Char;
	   var v;
	   for (v = 0; v < sText.length && IsNumber == true; v++) 
	      { 
	      Char = sText.charAt(v); 
	      if (ValidChars.indexOf(Char) == -1) 
	         {
	         IsNumber = false;
	         }
	      }
	   return IsNumber;
	   
	   }
	   
	function totMarksT(count,count2) {
				
		var def=count;
		var count1=document.getElementById("internalexamSize").value;
		var ghi=count1;
		var tot=0;
		//please check whether the control is found ....then move to next
		for(var i=0;i<count1;i++){
			var abc='test_'+count+'_'+i;
			if(document.getElementById(abc)!=null && document.getElementById(abc).value!=null){
			var val=document.getElementById(abc).value;
			//alert('--val-'+val);
			if(val!=""){
				if(IsNumeric(val)){
					tot=tot+parseFloat(val);
					//alert('--if-'+tot);
					}
				else if(typeof val === 'string' || val instanceof String){
						if(tot==0){
						document.getElementById("t_"+def).innerHTML=val;
						}
						//alert('--else-'+tot);
					}
			}

			}
		}

		
		if(tot!=0){
			if(document.getElementById("programTypeId").value=='2'){
				tot=Math.ceil(tot);
			}
			
			document.getElementById("t_"+def).innerHTML=tot.toFixed(2);
		}
		
		
	}

	function totMarksP(count,count2) {
		
		var def=count;
		var count1=document.getElementById("internalexamSize").value;
		var ghi=count1;
		var tot=0;
		//please check whether the control is found ....then move to next
		for(var i=0;i<count1;i++){
			var abc='test_'+count+'_'+i;
			if(document.getElementById(abc)!=null && document.getElementById(abc).value!=null){
			var val=document.getElementById(abc).value;
			//alert('--val-'+val);
			if(val!=""){
				if(IsNumeric(val)){
					tot=tot+parseFloat(val);
					//alert('--if-'+tot);
					}
				else if(typeof val === 'string' || val instanceof String){
						if(tot!=0){
						document.getElementById("p_"+def).innerHTML=val;
						}
						//alert('--else-'+tot);
					}
			}
			}
		}

		
		if(tot!=0){
			if(document.getElementById("programTypeId").value=='2'){
			tot=Math.ceil(tot);
			}
			document.getElementById("p_"+def).innerHTML=tot.toFixed(2);
		}
		
		
	}
	

	
</script>

 <script language="javascript">
    document.onmousedown=disableclick;
    status="Right Click Disabled";
    function disableclick(event)
    {
      if(event.button==2)
       {
         alert(status);
         return false;    
       }
    }
    </script>

</head>

<html:form action="/marksEntry" method="POST" enctype="multipart/form-data">
	<html:hidden property="formName" value="newExamMarksEntryForm"	styleId="formName" />
	<html:hidden property="pageType" value="3" />
	<html:hidden property="method" styleId="method"	value="saveMarksForAllInternal" />
	<html:hidden property="internalexamSize" styleId="internalexamSize"	/>
	<html:hidden property="programTypeId" styleId="programTypeId"	/>
	
	<table width="99%" border="0">
		<tr>
			<td><span class="heading"><span class="Bredcrumbs">Exam	>> Teachers Internals Marks Entry&gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="heading_white">Teachers Internals Marks Entry - All Students Single Subject</td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>

					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="heading">
					<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
					<FONT color="green"> <html:messages id="msg"
						property="messages" message="true">
						<c:out value="${msg}" escapeXml="false"></c:out>
						<br>
					</html:messages> </FONT></div>
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
									<div align="right">Program :</div>
									</td>
									<td width="23%" height="25" class="row-even"><bean:write
										property="courseName" name="newExamMarksEntryForm"></bean:write></td>
									<td width="28%" class="row-odd">
									<div align="right">Semester :</div>
									</td>
									<td width="26%" class="row-even"><bean:write
										property="schemeNo" name="newExamMarksEntryForm"></bean:write></td>

								</tr>
								<tr>
									<td height="25" class="row-odd">
									<div align="right">Course Name:</div>
									</td>
									<td height="25" class="row-even"><bean:write
										property="subjectName" name="newExamMarksEntryForm"></bean:write></td>
									<td height="25" class="row-odd">
									<div align="right">Subject Type :</div>
									</td>
									<td height="25" class="row-even">
									<logic:equal value="1" property="subjectType" name="newExamMarksEntryForm">
									Theory
									</logic:equal>
									<logic:equal value="0" property="subjectType" name="newExamMarksEntryForm">
									Practical
									</logic:equal>
									<logic:equal value="11" property="subjectType" name="newExamMarksEntryForm">
									Both
									</logic:equal>
									</td>
								</tr>
								
							</table>
							</td>
							<td width="5" background="images/right.gif"></td>
						</tr>
					</table>
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
							<table width="100%" cellspacing="1" cellpadding="0">
								<tr>

									<td class="row-odd">
									<div align="center">Sl.No</div>
									</td>
									<td class="row-odd">Student Name</td>
									<td height="25" class="row-odd">Register No.</td>
									<td height="25" class="row-odd">Class No.</td>
									<logic:equal value="true" name="newExamMarksEntryForm" property="isExamMaxEntry">
										<td class="row-odd" align="center">Max Marks out of 10</td>
									</logic:equal>
									<logic:iterate  property="examList" name="newExamMarksEntryForm" id="examid">
									<td class="row-odd">
									<bean:write name="examid" property="examName"/>
									</td>
									</logic:iterate>
									<!-- <td height="25" class="row-odd">Total Theory</td> -->
									
									
									<%-- 
									<logic:equal value="0" property="subjectType" name="newExamMarksEntryForm">
									<logic:iterate  property="examList" name="newExamMarksEntryForm" id="examid">
									<td class="row-odd">
									<bean:write name="examid" property="examName"/>
									<br/>
									(Practical Marks)
									</td>
									</logic:iterate>
									<td height="25" class="row-odd">Total Practical</td>
									</logic:equal>
									--%>
									<logic:equal value="11" property="subjectType" name="newExamMarksEntryForm">
									<logic:iterate  property="examList" name="newExamMarksEntryForm" id="examid">
									<td class="row-odd">
									<bean:write name="examid" property="examName"/>
									<br/>
									(Theory Marks)
									</td>
									<td class="row-odd">
									<bean:write name="examid" property="examName"/>
									<br/>
									(Practical Marks)
									</td>
									</logic:iterate>
									<td height="25" class="row-odd">Total Theory</td>
									<td height="25" class="row-odd">Total Practical</td>
									</logic:equal>
									
									

								</tr>
								<logic:iterate name="newExamMarksEntryForm" property="studentMarksList" id="examStudentTO"	indexId="count">
								<c:choose>
								<c:when test="${count%2==0}">
								<tr class="row-even">
								</c:when>
								<c:otherwise>
								<tr class="row-white">
								</c:otherwise>
								</c:choose>
										<td height="35" width="5%">
										<div align="center"><c:out value="${count+1}"></c:out></div>
										</td>
										<td height="35" width="30%"><bean:write name="examStudentTO" property="studentName" /></td>
										<td height="35" width="15%"><bean:write	name="examStudentTO" property="registerNo" /></td>
										<td height="35" width="15%"><bean:write	name="examStudentTO" property="rollNo" /></td>
										<logic:equal value="true" name="newExamMarksEntryForm" property="isExamMaxEntry">
											<td height="35" width="15%" align="center"><bean:write	name="examStudentTO" property="maxMarksInternal" /></td>
										</logic:equal>
										<%String tott="t_"+count; %>
										<%String totp="p_"+count; %>
										<nested:iterate property="studentMarksList" id="examMarksEntryStudentTO" name="examStudentTO"	indexId="count1">
											
											<%String id="test_"+count+"_"+count1; %>
											
										<logic:equal value="true" property="isTheory" name="examMarksEntryStudentTO">
											<td height="35" width="10%">
											<%-- 
											<logic:equal value="true" property="isTheorySecured" name="examMarksEntryStudentTO">	
												<nested:text property="theoryMarks" maxlength="6" styleId= '<%=id %>'  onblur='<%="totMarksT("+count+","+count1+")"%>' disabled="true"/>
											</logic:equal>
											<logic:equal value="false" property="isTheorySecured" name="examMarksEntryStudentTO">
											</logic:equal>	
											--%>
											<c:choose>
											<c:when test="${newExamMarksEntryForm.oldMarksCheck==true || examMarksEntryStudentTO.allInternalDateExceeded!=true}">
											<nested:text name="newExamMarksEntryForm" property='<%="studentMarksList["+ count +"].studentMarksList["+ count1 +"].theoryMarks"%>' maxlength="6" styleId= '<%=id %>'  onkeyup='<%="totMarksT("+count+","+count1+")"%>' disabled="true"/>
										
											</c:when>
											
											<c:otherwise>
											<nested:text name="newExamMarksEntryForm" property='<%="studentMarksList["+ count +"].studentMarksList["+ count1 +"].theoryMarks"%>' maxlength="6" styleId= '<%=id %>'  onkeyup='<%="totMarksT("+count+","+count1+")"%>' />
										
											</c:otherwise>
											</c:choose>
											
											</td>
										</logic:equal>
										
										<logic:equal value="true" property="isPractical" name="examMarksEntryStudentTO">
										<td width="10%" height="35">
										
											<logic:equal value="true" property="isPracticalSecured" name="examMarksEntryStudentTO">	
												<nested:text property="practicalMarks" maxlength="6" styleId= '<%=id %>' onkeydown="movenext(this.name, event, this.id)" onblur="totMarksP(this.name, event, this.id)" disabled="true"/>
											</logic:equal>
											<logic:equal value="false" property="isPracticalSecured" name="examMarksEntryStudentTO">
											</logic:equal>
											
											<c:choose>
											<c:when test="${newExamMarksEntryForm.oldMarksCheck==true || examMarksEntryStudentTO.allInternalDateExceeded!=true}">
											<nested:text name="newExamMarksEntryForm" property='<%="studentMarksList["+ count +"].studentMarksList["+ count1 +"].practicalMarks"%>' maxlength="6" styleId= '<%=id %>'  onkeyup='<%="totMarksT("+count+","+count1+")"%>' disabled="true"/>
										
											</c:when>
											
											<c:otherwise>
											<nested:text name="newExamMarksEntryForm" property='<%="studentMarksList["+ count +"].studentMarksList["+ count1 +"].practicalMarks"%>' maxlength="6" styleId= '<%=id %>'  onkeyup='<%="totMarksT("+count+","+count1+")"%>' />
										
											</c:otherwise>
											</c:choose>
												
										</td>
										</logic:equal>
										
										</nested:iterate>
										
									<%-- 	<logic:equal value="1" property="subjectType" name="newExamMarksEntryForm">
										<td height="35" width="10%"><div id='<%=tott %>'><bean:write name="examStudentTO" property="totalInternalMarksT"/></div></td>
										</logic:equal>
										<logic:equal value="0" property="subjectType" name="newExamMarksEntryForm">
										<td height="35" width="10%"><div id='<%=totp %>'><bean:write name="examStudentTO" property="totalInternalMarksP" /></div></td>
										</logic:equal> --%>
										<%-- <logic:equal value="11" property="subjectType" name="newExamMarksEntryForm">
										<td height="35" width="10%"><div id='<%=tott %>'><bean:write name="examStudentTO" property="totalInternalMarksT" /></div></td>
										<td height="35" width="10%"><div id='<%=totp %>'><bean:write name="examStudentTO" property="totalInternalMarksP" /></div></td>
										</logic:equal> --%>
										
									</tr>
								
								</logic:iterate>
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
							<td width="45%" height="35">
							<div align="right"><input name="button2" type="submit"
								class="formbutton" value="Submit" /></div>
							</td>
							<td width="2%"></td>
							<td width="6%">
										<input type="button" class="formbutton"
													value="Exit" onclick="goToFirstPage('initExamMarksEntryForAllInternals')" />	
								</td>
							<td width="2%"></td>
							<td width="46%">
							<input type="button" class="formbutton"
													value="Print" onclick="goToPrintPage('getPrintMarksForAllInternals')" />
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
