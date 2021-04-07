<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@page import="java.util.Map,java.util.HashMap"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>

<script src="jquery/js/jquery-1.9.0.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="jquery.confirm/jquery.confirm/freeFoodJqueryConfirm.css" />
  <script src="jquery.confirm/jquery.confirm/jquery.confirm.js"></script>

<title>:: CMS ::</title>
<script>
	function set(target) {
		document.getElementById("method").value = target;
	}
	function checkSecured() {
		if (document.getElementById("checkbox").checked)
			return false;
		else
			return true;
	}
	function goToFirstPage() {
		document.location.href = "moderationMarksEntry.do?method=initModeration";
	}

	function viewOldMarks(subjectId, studentId,count) {
		var url="studentMarksCorrection.do?method=viewOldMarks&studentId="+studentId+"&subjectId="+subjectId+"&count="+count;
		window.open(url,'Student Marks Correction');
	}
	function clearTheData(){
		document.getElementById("method").value = "getStudentDetails";
		document.moderationMarksEntryForm.submit();
	}			
</script>

<html:form action="/moderationMarksEntry" method="POST" enctype="multipart/form-data" focus="regNo">

	<html:hidden property="formName" value="moderationMarksEntryForm" styleId="formName" />
	<html:hidden property="pageType" value="2" styleId="pageType" />
	<html:hidden property="method" styleId="method"	value="getStudentDetails" />
	<input type="hidden"  id="verifyGracing" name="verifyGracing" value="<bean:write name="moderationMarksEntryForm" property="verifyGracing"/>"/>
	<input type="hidden"  id="subjectname" name="subjectname" value="<bean:write name="moderationMarksEntryForm" property="subjectname"/>"/>
	<table width="99%" border="0">
		<tr>
			<td><span class="heading"><span class="Bredcrumbs">Exam
			&gt;&gt; Moderation/Revaluation&gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>

					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="heading_white">Student
					Moderation/Revaluation - Single Student All Subjects</td>
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
					<div align="right" class="mandatoryfield"><bean:message
						key="knowledgepro.mandatoryfields" /></div>
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
							<table width="100%" cellspacing="0" cellpadding="0">
								<tr>
									<td height="25" width="10%" class="row-odd">
									<div align="right">Exam Name :&nbsp;&nbsp;</div>
									</td>
									<td height="25" width="40%" class="row-even">&nbsp;&nbsp;<bean:write
										name="moderationMarksEntryForm" property="examName" /></td>
									<td width="10%" class="row-odd">
									<div align="right">Scheme :&nbsp;&nbsp;</div>
									</td>
									<td width="40%" class="row-even">&nbsp;&nbsp;
									<bean:write name="moderationMarksEntryForm"
										property="schemeNo" /></td>
								</tr>
								<tr>
									<td height="25" width="10%" class="row-odd">
									<div align="right">Entry Type :&nbsp;&nbsp;</div>
									</td>
									<td height="25" width="40%" class="row-even">&nbsp;&nbsp;
									  <logic:equal value="R" name="moderationMarksEntryForm" property="entryType">REVALUATION</logic:equal>	
									  <logic:equal value="M" name="moderationMarksEntryForm" property="entryType">MODERATION</logic:equal>	
									  </td>
									  
									  <td height="25" width="10%" class="row-odd">
									<div align="right">Exam Type :&nbsp;&nbsp;</div>
									</td>
									<td height="25" width="40%" class="row-even">
									  <bean:write name="moderationMarksEntryForm" property="examType"/>
									  </td>
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
					<table width="50%" border="0" align="center" cellpadding="0"
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
									<td width="50%" height="25" class="row-odd">
									<div align="right">Register No. :&nbsp;&nbsp;</div>
									</td>
									<td width="50%" height="25" class="row-even">&nbsp;&nbsp;<html:text
										property="regNo" styleId="regNo" maxlength="10"
										styleClass="TextBox" size="10"/></td>
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
					<td class="heading">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr><td style="height: 10px" align="center" colspan="3"></td></tr>
						<tr>
							<td width="45%" height="35">
							<div align="right"><input  type="submit"
								class="formbutton" value="Search" onclick="set('getStudentDetails')" /></div>
							</td>
							<td width="2%"></td>
							<td width="53%"><input type="button" class="formbutton"
								value="Cancel" onclick="goToFirstPage()" /></td>
						</tr>						
						<tr><td style="height: 10px" align="center" colspan="3"></td></tr>						
					</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif"></td>
				</tr>
				<nested:notEmpty property="marksList">
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
									<td width="25%" height="25" class="row-odd">
									<div align="right">Student Name :&nbsp;&nbsp;</div>
									</td>
									<td width="25%" height="25" class="row-even">&nbsp;&nbsp;
									<bean:write name="moderationMarksEntryForm" property="studentName"/>
									</td>
									
									<td width="25%" height="25" class="row-odd">
									<div align="right">Class Name :&nbsp;&nbsp;</div>
									</td>
									<td width="25%" height="25" class="row-even">&nbsp;&nbsp;
									<bean:write name="moderationMarksEntryForm" property="className"/>
									</td>
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
										     
											<td height="25" width="20%" class="row-odd">Subject</td>
											<td align="center" class="row-odd" width="20%" colspan="2">
											<logic:equal value="R" name="moderationMarksEntryForm" property="entryType">Enter Revaluation</logic:equal>	
									        <logic:equal value="M" name="moderationMarksEntryForm" property="entryType">Enter Moderation</logic:equal>	
											</td>
											<td align="center" class="row-odd" colspan="2">Current Final Mark</td>
											<td align="center" class="row-odd" colspan="2">
											<logic:equal value="R" name="moderationMarksEntryForm" property="entryType">Mark Before Last Revaluation</logic:equal>	
									        <logic:equal value="M" name="moderationMarksEntryForm" property="entryType">Mark Before Last Moderation</logic:equal>	
											</td>
						                    <td class="row-odd">Comments</td>
										</tr>
										<tr>
											<td height="25" class="row-odd"></td>
											<td class="row-odd" >Theory</td>
											<td class="row-odd" >Practical</td>
											<td class="row-odd" >Theory</td>
											<td class="row-odd" >Practical</td>
											<td class="row-odd" >Theory</td>
											<td class="row-odd" >Practical</td>
											<td class="row-odd" ></td>
											
										</tr>
                                          <%
                                          boolean t =true ;
                                          boolean p =true;
                                          %>
										<nested:iterate property="marksList" indexId="count" id="list">
										<% 
                                          String td = "t"+count;
										  String pd = "p"+count;
										  String cd = "c"+count;
										%>
										  <c:choose>
										  		 <c:when test="${list.theoryPractical== 'T' || list.theoryPractical== 'B' }">
													  
													   <c:choose><c:when test="${list.theoryPractical== 'T'}">
													      	   <%
														   t=false;
														   %>
													   
													   </c:when>
													
													   <c:otherwise>
													    	   <%
														   t=false;
													    	   p=false;
														   %>
													   
													   </c:otherwise>
													   </c:choose>
												
										   		</c:when>
										   </c:choose>
										   <c:choose>
										   <c:when test="${list.theoryPractical== 'P' || list.theoryPractical== 'B' }">
										
										   
										   			<c:choose><c:when test="${list.theoryPractical== 'P'}">
												      	    <%
															 	p=false;
												      	        t=true;
															 %>
													   
													   </c:when>
													
													   <c:otherwise>
													    	   <%
														      t=false;
													    	   p=false;
														   %>
													   
													   </c:otherwise>
													   </c:choose>
										   
										   
										   </c:when>
										   </c:choose>
											<tr>
												<td width="23%" height="25" class="row-even"><bean:write name="list"
													property="subjectCode" /> - <bean:write name="list"
													property="subjectName" /></td>
												<td  class="row-even">
													<nested:text property="theoryMarks" maxlength="3" styleId="<%=td %>" disabled="<%=t%>"/>
												</td>
												<td  class="row-even">
													<nested:text property="practicalMarks" maxlength="3" styleId="<%=pd %>" disabled="<%=p%>"/>
												</td>
												<td align="center" class="row-even">
													<bean:write name="list"  property="currentTheoryMarks" />
												</td>
												<td align="center" class="row-even">
													<bean:write name="list"  property="currentPracticalMarks" />
												</td>
												<td align="center" class="row-even">
													<bean:write name="list"  property="prevoiusTheoryMarks" />
												</td>
												<td align="center" class="row-even">
													<bean:write name="list"  property="previousPracticalMarks" />
												</td>
												
												<td width="20%" align="center" class="row-even"><nested:textarea name="list" property="comments" styleId="<%=cd %>"></nested:textarea></td>
											</tr>
										</nested:iterate>
									</table>
									</td>
									<td width="5" background="images/right.gif"></td>
								</tr>
								<tr>
									<td height="5"><img src="images/04.gif" width="5"
										height="5" /></td>
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
							<td class="heading">&nbsp;</td>
							<td valign="top" background="images/Tright_3_3.gif"></td>
						</tr>
						
						<tr>
							<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
							<td class="heading">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr><td style="height: 10px" align="center" colspan="3"></td></tr>
								<tr>
									<td width="45%" height="35">
									<div align="right"><input name="button2" type="submit"
										class="formbutton" value="Submit" onclick="set('saveChangedMarks')"/></div>
									</td>
									<td width="2%"></td>
									<td width="53%"><input type="button" class="formbutton"
										value="Cancel" onclick="goToFirstPage()" /></td>
								</tr>								
								<tr><td style="height: 10px" align="center" colspan="3"></td></tr>								
							</table>
							</td>
							<td valign="top" background="images/Tright_3_3.gif"></td>
						</tr>
				</nested:notEmpty>
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
<script type="text/javascript">
var isVerifyGracing = document.getElementById("verifyGracing").value;
var subjectname= document.getElementById("subjectname").value;
if(isVerifyGracing=='true'){
	$.confirm({
		'message'	: 'Marks Has been Altered for this '+subjectname+'  gracing is not selected.Are you sure that entered marks is not for gracing',
		'buttons'	: {
			'Ok'	: {
				'class'	: 'blue',
				'action': function(){
					$.confirm.hide();
					document.getElementById("method").value="saveChangedMarksAfterVerifiedGracing";
					document.moderationMarksEntryForm.submit();
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