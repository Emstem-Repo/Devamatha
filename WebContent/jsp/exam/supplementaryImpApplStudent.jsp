<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@page import="java.util.Map,java.util.HashMap"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>

<script language="JavaScript" src="js/ajax/AjaxUtil.js"></script>
<script language="JavaScript" src="js/ajax/Ajax.js"></script>
<script type="text/javascript">

function goToFirstPage() {
	document.location.href = "newSupplementaryImpApp.do?method=cancelSupplementaryImpApplication";
}
function cancelFirstPage() {
	document.location.href = "newSupplementaryImpApp.do?method=initSupplementaryImpApplication";
}
</script>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html:form action="/newSupplementaryImpApp">
	<html:hidden property="formName" value="newSupplementaryImpApplicationForm" />
	<html:hidden property="pageType" value="3" />
	<html:hidden property="method" styleId="method" value="addSupplementaryApplication" />

	<table width="100%" border="0">
		<tr>
			<td><span class="heading"><bean:message
				key="knowledgepro.exam.exam" /> <span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.exam.supplementaryApplication" />
			&gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> <bean:message
						key="knowledgepro.exam.supplementaryApplication" /></strong></td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td height="20" class="news">
					<div align="right" class="mandatoryfield"><bean:message
						key="knowledgepro.mandatoryfields" /></div>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
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
									<td width="33%" height="30" class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.exam.revaluationApplication.regNo" /></div>
									</td>
									<td width="19%" height="30" class="row-even"><bean:write
										name="newSupplementaryImpApplicationForm" property="suppTo.regNumber" /></td>
									<td width="24%" height="30" class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.exam.revaluationApplication.rollNo" /></div>
									</td>
									<td width="24%" height="30" class="row-even"><bean:write
										name="newSupplementaryImpApplicationForm" property="suppTo.rollNumber" /></td>
								</tr>
								<tr>
									<td height="26" class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.exam.revaluationApplication.course" /></div>
									</td>
									<td class="row-even"><bean:write
										name="newSupplementaryImpApplicationForm" property="suppTo.courseName" /></td>
									<td height="26" class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.exam.revaluationApplication.scheme" /></div>
									</td>
									<td class="row-even"><bean:write
										name="newSupplementaryImpApplicationForm" property="suppTo.semester_year_no" /></td>

								</tr>
								<tr>

									<td height="26" class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.exam.revaluationApplication.studentName" /></div>
									</td>
									<td class="row-even"><bean:write
										name="newSupplementaryImpApplicationForm" property="suppTo.studentName" /></td>
									<td height="26" class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.exam.supplementaryApplication.section" /></div>
									</td>
									<td class="row-even"><bean:write
										name="newSupplementaryImpApplicationForm" property="suppTo.section" /></td>
								</tr>

								<tr>
									<td class="row-odd" height="28">
									<div align="right"><bean:message
										key="knowledgepro.exam.supplementaryApplication.suppImp" /></div>
									</td>
									<td class="row-even"><bean:write
										name="newSupplementaryImpApplicationForm"
										property="supplementaryImprovement" /> </td>
									<td class="row-odd" height="28">
									<div align="right"><bean:message
										key="knowledgepro.exam.supplementaryApplication.chance" /></div>
									</td>
									<td class="row-even"></td>
								</tr>
							</table>
							</td>
							<td width="5" height="30" background="images/right.gif"></td>
						</tr>
						<tr>
							<td height="5"><img src="images/04.gif" width="5" height="5" /></td>
							<td background="images/05.gif"></td>
							<td><img src="images/06.gif" /></td>
						</tr>
					</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="47%" height="29">&nbsp;</td>
						</tr>
					</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td height="10" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
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
									<td height="25" rowspan="2" align="center" class="row-odd">Sl
									No.</td>
									<td rowspan="2" class="row-odd">Subject Code</td>
									<td rowspan="2" class="row-odd">Subject Name</td>
									<td colspan="2" class="row-odd">
									<div align="center">Failed</div>
									</td>

									<td rowspan="2" class="row-odd">Fees</td>
									<td colspan="2" class="row-odd">
									<div align="center">Appeared</div>
									</td>
								</tr>
								<tr>
									<td class="row-odd">Theory</td>
									<td class="row-odd">Practical</td>
									<td class="row-odd">Theory</td>
									<td class="row-odd">Practical</td>
								</tr>


								<nested:iterate property="suppTo.subjectList" indexId="count"  id = "subjectList">

									<%
										String dynamicStyle = "";
														if (count % 2 != 0) {
															dynamicStyle = "row-white";

														} else {
															dynamicStyle = "row-even";

														}
									%>
									<tr>
										<td class='<%=dynamicStyle%>'>
										<div align="center"><c:out value="${count + 1}" /></div>
										</td>
										<td class='<%=dynamicStyle%>'><nested:write
											property="subjectCode" /></td>
										<td class='<%=dynamicStyle%>'><nested:write
											property="subjectName" /></td>
										<td class='<%=dynamicStyle%>'>
										<%    String ft2 = "ft2_"+count; 
		                      						String ft1 = "ft1_"+count; 
		                     				 %> <nested:hidden styleId='<%=ft2%>'
											property="isFailedTheory" /> 
											<c:choose>
											<c:when test="${newSupplementaryImpApplicationForm.addOrEdit == 'add' || subjectList.isOverallTheoryFailed}">
												<input type="checkbox"
																	name="suppTo.subjectList[<c:out value='${count}'/>].failedTheory"
																	id="<%=ft1%>" />
											</c:when>
											<c:otherwise>
												<nested:checkbox styleId='<%=ft1%>' property="failedTheory" indexed="true"
													disabled="true" />
											</c:otherwise>
											</c:choose>
											 <script type="text/javascript">
										var vft1=document.getElementById("ft2_<c:out value='${count}'/>").value;
										if(vft1=="true"){
										document.getElementById("ft1_<c:out value='${count}'/>").checked =true ;
										}
											
										</script></td>
										<td class='<%=dynamicStyle%>'>
										<%    String fp2 = "hidden1_"+count; 
		                      						String fp1 = "check1_"+count; 
		                     				 %> <nested:hidden styleId='<%=fp2%>'
											property="isFailedPractical" /> 
											<c:choose>
											<c:when test="${newSupplementaryImpApplicationForm.addOrEdit == 'add' || subjectList.isOverallPracticalFailed}">
												<input type="checkbox"
																	name="suppTo.subjectList[<c:out value='${count}'/>].failedPractical"
																	id="<%=fp1%>" />
											</c:when>
											<c:otherwise>
												<nested:checkbox styleId='<%=fp1%>' property="failedPractical" disabled="true"/>
											</c:otherwise>
											
											</c:choose>
										<script type="text/javascript">
										var vfp=document.getElementById("hidden1_<c:out value='${count}'/>").value;
										if(vfp=="true"){
										document.getElementById("check1_<c:out value='${count}'/>").checked =true ;
										}
											
										</script></td>
										<td class='<%=dynamicStyle%>'>
										<% String control="control1_"+count;
										String disable="disable_"+count;
										%>
										<nested:hidden styleId='<%=control%>'
											property="controlDisable" />
											<nested:text property="fees" styleId='<%=disable%>'/>		
											</td>
												<td class='<%=dynamicStyle%>'>
															  <input
																	type="hidden"
																	name="subjectList[<c:out value='${count}'/>].tempChecked"
																	id="hidden_<c:out value='${count}'/>"
																	value="<nested:write name='subjectList' property='tempChecked'/>" />
																	<input
																	type="checkbox"
																	name="suppTo.subjectList[<c:out value='${count}'/>].isAppearedTheory"
																	id="<c:out value='${count}'/>" />
																	<script type="text/javascript">
																		var studentId = document.getElementById("hidden_<c:out value='${count}'/>").value;
																		if(studentId == "true") {
																			document.getElementById("<c:out value='${count}'/>").checked = true;
																		}		
																	</script>
															</td>
															
															
										<td class='<%=dynamicStyle%>'>
										  <input
												type="hidden"
												name="subjectList[<c:out value='${count}'/>].tempPracticalChecked"
												id="prhidden_<c:out value='${count}'/>"
												value="<nested:write name='subjectList' property='tempPracticalChecked'/>" />
												<input
												type="checkbox"
												name="suppTo.subjectList[<c:out value='${count}'/>].isAppearedPractical"
												id="pr_<c:out value='${count}'/>" />
												<script type="text/javascript">
													var pr = document.getElementById("prhidden_<c:out value='${count}'/>").value;
													if(pr == "true") {
														document.getElementById("pr_"+"<c:out value='${count}'/>").checked = true;
													}		
												</script>
										</td>			
									</tr>
								</nested:iterate>
							</table>
							</td>
							<td width="5" height="30" background="images/right.gif"></td>
						</tr>
						<tr>
							<td height="5"><img src="images/04.gif" width="5" height="5" /></td>
							<td background="images/05.gif"></td>
							<td><img src="images/06.gif" /></td>
						</tr>
					</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr><td style="height: 10px" align="center" colspan="3"></td></tr>
						<tr>
							<td width="47%" height="35">
							<div align="right">
							<input name="button2" type="submit"
								class="formbutton" value="Submit" /></div>
							</td>
							<td width="1%"></td>

							<td width="46%">
							<logic:equal value="add" name="newSupplementaryImpApplicationForm"
										property="addOrEdit">
							<input type="button" class="formbutton"
								value="Cancel" onclick="cancelFirstPage()"/>
							</logic:equal>
							<logic:notEqual value="add" name="newSupplementaryImpApplicationForm"
										property="addOrEdit">
							<input type="button" class="formbutton"
								value="Cancel" onclick="goToFirstPage()"/>
							</logic:notEqual>
							</td>
						</tr>						
						<tr><td style="height: 10px" align="center" colspan="3"></td></tr>						
					</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td height="10" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">&nbsp;</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
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