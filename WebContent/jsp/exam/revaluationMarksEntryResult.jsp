<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@page import="java.util.Map,java.util.HashMap"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<script>
	function goToFirstPage(method) {
		document.location.href = "marksEntry.do?method="+method;
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
		var ghi=abc.substring(5);
		var jkl=parseInt(ghi)+1;
		var mno="test_"+jkl;
		//please check whether the control is found ....then move to next
		eval(document.getElementById(mno)).focus();
		//pqr.focus();
		return true;
		}
	}

	function printRevaluationMarksEntry(){
		var url = "marksEntry.do?method=printRevaluationMarksEntry";
		myRef = window
				.open(url, "Revaluation Marks Entry",
						"left=20,top=20,width=900,height=900,toolbar=1,resizable=0,scrollbars=1");

	}
	
</script>

<html:form action="/marksEntry" method="POST" enctype="multipart/form-data">
	<html:hidden property="formName" value="newExamMarksEntryForm"	styleId="formName" />
	<html:hidden property="pageType" value="3" />
	<html:hidden property="method" styleId="method"	value="saveRevaluationMarks" />
	<table width="99%" border="0">
		<tr>
			<td><span class="heading"><span class="Bredcrumbs">Exam	>>Revaluation Exam Marks Entry&gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="heading_white">Student Revaluation
					Marks Entry - All Students Single Subject</td>
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
									<td width="15%" height="25" class="row-odd">
									<div align="right">Exam Name :</div>
									</td>

									<td width="45%" height="25" class="row-even" ><bean:write
										property="examName" name="newExamMarksEntryForm"></bean:write></td>
									
									<td width="15%" class="row-odd">
									<div align="right">Entered Max Marks :</div>
									</td>
									<td width="25%" class="row-even"><bean:write
										property="enteredMaxMarks" name="newExamMarksEntryForm"></bean:write></td>
										
								</tr>
								<tr>
									<td  height="25" class="row-odd">
									<div align="right">Course :</div>
									</td>
									<td  height="25" class="row-even"><bean:write
										property="courseName" name="newExamMarksEntryForm"></bean:write></td>
									<td  class="row-odd">
									<div align="right">Scheme :</div>
									</td>
									<td  class="row-even"><bean:write
										property="schemeNo" name="newExamMarksEntryForm"></bean:write></td>

								</tr>
								<tr>
									<td height="25" class="row-odd">
									<div align="right">Subject Name:</div>
									</td>
									<td height="25" class="row-even"><bean:write
										property="subjectName" name="newExamMarksEntryForm"></bean:write></td>
									<td height="25" class="row-odd">
									<div align="right">Subject Type :</div>
									</td>
									<td height="25" class="row-even"><bean:write
										property="subjectType" name="newExamMarksEntryForm"></bean:write></td>
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
										     
											<td height="25" width="20%" align="center" class="row-odd" rowspan="2">Student Name</td>
											<td height="25" width="20%" align="center" class="row-odd" >Register No</td>
											<logic:equal value="R" name="newExamMarksEntryForm" property="entryType">
											<td align="center" class="row-odd" width="20%" colspan="2">
											 Revaluation Marks
											</td></logic:equal>
													<logic:equal value="S" name="newExamMarksEntryForm" property="entryType">
											<td align="center" class="row-odd" width="20%" colspan="2">
											 Scrutiny
											</td></logic:equal>	
													<logic:equal value="CV" name="newExamMarksEntryForm" property="entryType">
											<td align="center" class="row-odd" width="20%" colspan="2">
											 Challenge Valuation Marks
											</td></logic:equal>	
												
											<td align="center" class="row-odd" colspan="2">
											Current Marks Entry
											</td>
											<td align="center" class="row-odd" colspan="2">Before Marks Entry</td>
										
						                    <td height="25" width="20%" align="center" class="row-odd" rowspan="2">Comments</td>
										</tr>
										<tr>
										<td height="25" class="row-odd"></td>
											<td class="row-odd" >Theory</td>
											<td class="row-odd" >Practical</td>
											<td class="row-odd" >Theory</td>
											<td class="row-odd" >Practical</td>
											<td class="row-odd" >Theory</td>
											<td class="row-odd" >Practical</td>
										
											
										</tr>
								
								                  <%
                                          boolean t =true ;
                                          boolean p =true;
                                          %>
										<nested:iterate property="marksList" indexId="count111" id="list">
										<% 
                                          String td = "t"+count111;
										  String pd = "p"+count111;
										  String cd = "c"+count111;
										%>
										  <c:choose>
										   <c:when test="${list.theoryPractical== 'T' || list.theoryPractical== 'B' }">
										   <%
										   t=false;
										   %>
										   </c:when>
										   </c:choose>
										   <c:choose>
										   <c:when test="${list.theoryPractical== 'P' || list.theoryPractical== 'B' }">
										   <%
										   p=false;
										   %>
										   </c:when>
										   </c:choose>
											<tr>
												<td width="23%" height="25" align="center" class="row-even"><bean:write name="list"
													property="studentName" /></td>

												<td width="23%" height="25" align="center" class="row-even"><bean:write
												name="list" property="regNo" /></td>

												<td  class="row-even">
													<nested:text property="theoryMarks" maxlength="3" styleId="<%=td %>" disabled="<%=t%>"/>
												</td>
												<td  class="row-even">
													<nested:text property="practicalMarks" maxlength="3" styleId="<%=pd %>" disabled="<%=p%>"/>
												</td>
										
												<td align="center" class="row-even">
													<bean:write name="list"  property="prevoiusTheoryMarks" />
												</td>
												<td align="center" class="row-even">
													<bean:write name="list"  property="previousPracticalMarks" />
												</td>
												
														<td align="center" class="row-even">
													<bean:write name="list"  property="currentTheoryMarks" />
												</td>
												<td align="center" class="row-even">
													<bean:write name="list"  property="currentPracticalMarks" />
												</td>
												
												<td width="20%" align="center" class="row-even"><nested:textarea name="list" property="comments" styleId="<%=cd %>"></nested:textarea></td>
											</tr>
										</nested:iterate>
								
								
								
								
								
								
								
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
						<tr><td style="height: 10px" align="center" colspan="5"></td></tr>
						<tr>
							<td width="45%" height="35">
							<div align="right"><input name="button2" type="submit"
								class="formbutton" value="Submit" /></div>
							</td>
							<td width="2%"></td>
							<td width="6%">
							<c:choose>
				
									<c:when test="${newExamMarksEntryForm.regular==true }">
										<input type="button" class="formbutton"
													value="Cancel" onclick="goToFirstPage('initRevaluationExamMarksEntry')" />	
									</c:when>
									<c:otherwise>
											<input type="button" class="formbutton"
													value="Cancel" onclick="goToFirstPage('initRevaluationExamMarksEntry')" />	
									</c:otherwise>
							</c:choose>
							
								
								</td>
							<td width="2%"></td>
								<td width="6%" height="35" align="left">
							<div align="right"><input type="button" 
								class="formbutton" value="Print" onclick="printRevaluationMarksEntry();" /></div>
							</td>
							<td width="45%"></td>
						</tr>						
						<tr><td style="height: 10px" align="center" colspan="5"></td></tr>						
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

<script type="text/javascript">
var print = "<c:out value = '${newExamMarksEntryForm.print}' />";
if(print.length!=0 && print=='true'){
	var url = "marksEntry.do?method=printRevaluationMarksEntry";
	myRef = window
			.open(url, "Revaluation Marks Entry",
					"left=20,top=20,width=900,height=900,toolbar=1,resizable=0,scrollbars=1");
	window.print();
}
	

</script>
