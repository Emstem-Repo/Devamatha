<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@page import="java.util.Map,java.util.HashMap"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<link rel="stylesheet" href="jquery/css/ui-lightness/jquery-ui-1.8.18.custom.css">
<script language="JavaScript" src="js/ajax/AjaxUtil.js"></script>
<script language="JavaScript" src="js/ajax/Ajax.js"></script>
<script type="text/javascript">

function cancelAction(){
	document.location.href="StudentLoginAction.do?method=returnHomePage";
}
function printApplication() {
	var url ="newSupplementaryImpApp.do?method=showPrintDetails";
	myRef = window.open(url,"certificateCourse","left=20,top=20,width=800,height=500,toolbar=1,resizable=0,scrollbars=1");
}
function printChallanApplication() {
	var url ="newSupplementaryImpApp.do?method=showPrintChallanForRegular";	
	myRef = window.open(url,"certificateCourse","left=20,top=20,width=800,height=500,toolbar=1,resizable=0,scrollbars=1");
}

</script>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html:form action="/newSupplementaryImpApp">
	<html:hidden property="formName" value="newSupplementaryImpApplicationForm" />
	<html:hidden property="pageType" value="3" />
	<html:hidden property="method" styleId="method" value="calculateAmountForInternalRedo" />

	<table width="100%" border="0">
		<tr>
			<td><span class="heading"><bean:message
				key="knowledgepro.exam.exam" /> <span class="Bredcrumbs">&gt;&gt;
			Internal Redo Application
			&gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td width="9"><img src="images/st_Tright_03_01.gif" width="9" height="29"></td>
						<td background="images/st_Tcenter.gif" class="body">
							<strong class="boxheader">
								Internal Redo Application
							</strong>
						</td>
						<td width="10"><img src="images/st_Tright_1_01.gif" width="9" height="29"></td>
					</tr>
					<tr>
						<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
						<td height="20" class="news">
							<div align="right" class="mandatoryfield">
								<bean:message key="knowledgepro.mandatoryfields" />
							</div>
						</td>
						<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
					</tr>
					<tr>
						<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
						<td height="20">
							<table width="100%">
								<tr>
									<td align="left">
										<div id="errorMessages" class="ui-widget">
											<div class="ui-state-error ui-corner-all" style="padding: 0 .7em;">
												<p>
													<span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
													<strong>Alert:</strong>
													<span id="err"><html:errors/></span>
												</p>
											</div>
										</div>			
										<div id="messages">
											<div class="display-info">
												<span id="msg">
													<html:messages id="message" property="messages" message="true">
														<c:out value="${message}" escapeXml="false"></c:out>
														<br>
													</html:messages>
												</span>
											</div>
										</div>
										<script type="text/javascript">
											if(document.getElementById("message")==null ||  document.getElementById("message").innerHTML==''){
												document.getElementById("messages").style.display="none";
											}
											if(document.getElementById("err").innerHTML==''){
												document.getElementById("errorMessages").style.display="none";
											}
										</script>										
									</td>
								</tr>
							</table>
						</td>
						<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
					</tr>
					
					<tr>
						<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
						<td valign="top" class="news">
							<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
								<tr>
									<td><img src="images/01.gif" width="5" height="5" /></td>
									<td width="914" background="images/02.gif"></td>
									<td><img src="images/03.gif" width="5" height="5" /></td>
								</tr>
								<tr>
									<td width="5" background="images/left.gif"></td>
									<td valign="top">									
										<table width="100%" cellspacing="1" cellpadding="2">
											<nested:equal value="false" name="newSupplementaryImpApplicationForm" property="feesNotConfigured">
												<nested:equal value="true" name="newSupplementaryImpApplicationForm" property="intRedoAppAvailable">
													<nested:notEmpty name="newSupplementaryImpApplicationForm" property="mainList">
														<tr>
															<td width="85%" valign="top">
																<table width="100%" cellspacing="1" cellpadding="2">
																	<tr>
																		<td width="15%" height="60" class="studentrow-odd">
																			<div align="right"><bean:message key="knowledgepro.exam.revaluationApplication.regNo" /></div>
																		</td>
																		<td width="35%" height="60" class="studentrow-even">
																			<nested:write name="newSupplementaryImpApplicationForm" property="registerNo" />
																		</td>
																		<td height="60" class="studentrow-odd" width="15%">
																			<div align="right"><bean:message key="knowledgepro.exam.revaluationApplication.studentName" /></div>
																		</td>
																		<td class="studentrow-even" height="60" width="35%">
																			<nested:write name="newSupplementaryImpApplicationForm" property="nameOfStudent" />
																		</td>
																	</tr>
																	<tr>
																		<td class="studentrow-odd" height="60">
																			<div align="right"><bean:message key="knowledgepro.exam.blockUnblock.class" /></div>
																		</td>
																		<td class="studentrow-even" height="60">
																			<nested:write name="newSupplementaryImpApplicationForm" property="className" /></td>
																		<td class="studentrow-odd" height="60">
																			<div align="right"><bean:message key="knowledgepro.exam.blockUnblock.examName"/></div>
																		</td>
																		<td class="studentrow-even" height="60">
																			<nested:write name="newSupplementaryImpApplicationForm" property="examName" />
																		</td>						
																	</tr>
																</table>
															</td>
															<td width="15%">
																<img src='<%=request.getContextPath()%>/PhotoServlet' height="130" width="150" align="right"/>
															</td>																												
													</nested:notEmpty>
												</nested:equal>
											</nested:equal>								
										</table>
									</td>
									<td width="5" height="30" background="images/right.gif"></td>
								</tr>									
								<tr>
									<td width="5" background="images/left.gif"></td>
									<td valign="top">
										<table width="100%" cellspacing="1" cellpadding="2">
											<nested:equal value="false" name="newSupplementaryImpApplicationForm" property="intRedoAppAvailable">
												Application is not available
											</nested:equal>
											<nested:equal value="true" name="newSupplementaryImpApplicationForm" property="feesNotConfigured">
												Application is not available Please Contact: 
											</nested:equal>
											<nested:equal value="false" name="newSupplementaryImpApplicationForm" property="feesNotConfigured">
												<nested:equal value="true" name="newSupplementaryImpApplicationForm" property="intRedoAppAvailable">
													<nested:notEmpty name="newSupplementaryImpApplicationForm" property="mainList">
														<nested:iterate id="eto" name="newSupplementaryImpApplicationForm" property="mainList" indexId="count2">
															<nested:notEmpty property="examList" >
																<nested:iterate id="cto"  property="examList" indexId="count1">
																	<tr><td colspan="4">&nbsp;</td></tr>
																	<tr>
																		<td colspan="4" align="center">
																			<p>
																				<nested:equal value="true" name="newSupplementaryImpApplicationForm" property="isSupplPaper">
																					NOTE:The failed subjects are displayed below. Kindly tick the subject which you wish to apply for Internal Redo.
																				</nested:equal>																				
																			</p>
																		</td>
																 	</tr>
																	<nested:equal value="false" property="extended" name="newSupplementaryImpApplicationForm">
																		<tr class="heading">
																			<td colspan="4" align="center">
																				<p>The Application will be available till: <bean:write name="eto" property="examDate"/> </p>
																			</td>
																		</tr>
																	</nested:equal>
																	<nested:equal value="true" property="extended" name="newSupplementaryImpApplicationForm">
																		<tr class="heading">
																			<td colspan="4" align="center">
																				<p>The Application is extended with fine till: <bean:write name="eto" property="extendedDate"/>&nbsp;</p>
																			</td>
																		</tr>
																	</nested:equal>																	
																	<tr>
																		<td colspan="4" align="center">
																			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
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
																								<td height="25" rowspan="2" align="center" class="studentrow-odd">Sl No.</td>
																								<td rowspan="2" class="studentrow-odd">Subject Code</td>
																								<td rowspan="2" class="studentrow-odd">Subject Name</td>
																								<td rowspan="2" class="studentrow-odd">
																									<div align="center">CIA Marks</div>
																								</td>
																								<td colspan="2" class="studentrow-odd">
																									<div align="center">Apply For</div>
																								</td>																								
																							</tr>
																							<tr>																								
																								<td class="studentrow-odd">
																									<div align="center">Theory</div>
																								</td>
																								<td class="studentrow-odd">
																									<div align="center">Practical</div>
																								</td>
																							</tr>
																							<nested:iterate  property="toList" indexId="count">
															
																								<%
																									String dynamicStyle = "";
																									if (count % 2 != 0) {
																										dynamicStyle = "row-white";															
																									}
																									else {
																										dynamicStyle = "studentrow-even";															
																									}
																								%>
																								<tr>
																									<td class='<%=dynamicStyle%>'>
																										<div align="center"><c:out value="${count + 1}" /></div>
																									</td>
																									<td class='<%=dynamicStyle%>'><nested:write property="subjectCode" /></td>
																									<td class='<%=dynamicStyle%>'><nested:write property="subjectName" /></td>																								
																									<td class='<%=dynamicStyle%>'>
																										<div align="center"><nested:write property="ciaMark"/></div>
																									</td>
																									<nested:equal value="true" property="isSupplementary">																																																		
																										<%
																											String control="control1_"+count;
																											String disable="disable_"+count;
																										%>
																										<td class='<%=dynamicStyle%>'>
																											<nested:equal value="false" property="tempChecked">
																												<nested:equal value="true" property="isFailedTheory">
																													<div align="center">
																														<input type="hidden"
																															   name="mainList[<c:out value='${count2}'/>].examList[<c:out value='${count1}'/>].toList[<c:out value='${count}'/>].tempChecked"
																															   id="hidden_<c:out value='${count2}'/>_<c:out value='${count1}'/>_<c:out value='${count}'/>"
																															   value="<nested:write property='tempChecked'/>" />
																														<input type="checkbox"
																															   name="mainList[<c:out value='${count2}'/>].examList[<c:out value='${count1}'/>].toList[<c:out value='${count}'/>].isCIAAppearedTheory"
																															   id="<c:out value='${count2}'/>_<c:out value='${count1}'/>_<c:out value='${count}'/>" />
																														<script type="text/javascript">
																															var studentId = document.getElementById("hidden_<c:out value='${count2}'/>_<c:out value='${count1}'/>_<c:out value='${count}'/>").value;
																															if(studentId == "true") {
																																document.getElementById("<c:out value='${count2}'/>_<c:out value='${count1}'/>_<c:out value='${count}'/>").checked = true;
																															}		
																														</script>
																													</div>																													
																												</nested:equal>
																											</nested:equal>
																											<nested:equal value="true" property="tempChecked"><div align="center">Applied</div></nested:equal>
																										</td>
																										<td class='<%=dynamicStyle%>'>
																											<nested:equal value="false" property="tempPracticalChecked">
																												<nested:equal value="true" property="isFailedPractical">
																													<div align="center">
																														<input type="hidden"
																															   name="mainList[<c:out value='${count2}'/>].examList[<c:out value='${count1}'/>].toList[<c:out value='${count}'/>].tempPracticalChecked"
																															   id="prhidden_<c:out value='${count}'/>"
																															   value="<nested:write property='tempPracticalChecked'/>" />
																														<input type="checkbox"
																															   name="mainList[<c:out value='${count2}'/>].examList[<c:out value='${count1}'/>].toList[<c:out value='${count}'/>].isCIAAppearedPractical"
																															   id="pr_<c:out value='${count}'/>" />
																														<script type="text/javascript">
																															var pr = document.getElementById("prhidden_<c:out value='${count}'/>").value;
																															if(pr == "true") {
																																document.getElementById("pr_"+"<c:out value='${count}'/>").checked = true;
																															}		
																														</script>
																													</div>																													
																												</nested:equal>
																											</nested:equal>
																											<nested:equal value="true" property="tempPracticalChecked"><div align="center">Applied</div></nested:equal>
																										</td>
																										<nested:hidden styleId='<%=control%>' property="controlDisable" />
																									</nested:equal>																								
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
																	</tr>
																</nested:iterate>
															</nested:notEmpty>
														</nested:iterate>
													</nested:notEmpty>
												</nested:equal>
											</nested:equal>
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
						<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
						<td valign="top" class="news">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<nested:equal value="true" property="extended" name="newSupplementaryImpApplicationForm">
									<tr>
										<td width="47%" height="35" colspan="4">
											<div align="center" style="font-weight: bold;font-size: 12px;color: red"><B>Late submission fee: <bean:write name="newSupplementaryImpApplicationForm" property="fineFees"/></B></div>
										</td>
									</tr>
								</nested:equal>										
								<tr>						
									<nested:equal value="false" name="newSupplementaryImpApplicationForm" property="feesNotConfigured">
										<nested:equal value="true" name="newSupplementaryImpApplicationForm" property="intRedoAppAvailable">
											<nested:equal value="true" name="newSupplementaryImpApplicationForm" property="displayButton">
												<logic:equal value="false" name="newSupplementaryImpApplicationForm" property="challanDisplay">
													<td width="33%" height="35">
														<div align="right">
															<nested:submit styleClass="btnbg" value="Submit"></nested:submit>
														</div>
													</td>															
												</logic:equal>
											</nested:equal>
										</nested:equal>
									</nested:equal>									
									<td width="1%"></td>									
									<logic:equal value="true" name="newSupplementaryImpApplicationForm" property="challanDisplay">
										<td align="right" width="33%">
											<html:button property=""
													 styleClass="btnbg" value="Print"
													 onclick="printApplication()"></html:button>
											<html:button property=""
														 styleClass="btnbg" value="Print Challan"
														 onclick="printChallanApplication()"></html:button>
										</td>											
									</logic:equal>
									<td width="1%"></td>
									<td width="33%">
										<html:button property=""
													 styleClass="btnbg" value="Close"
													 onclick="cancelAction()"></html:button>
									</td>
								</tr>
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
						<td><img src="images/st_Tright_03_05.gif" width="9" height="29"></td>
						<td width="0" background="images/st_TcenterD.gif"></td>
						<td><img src="images/st_Tright_02.gif" width="9" height="29"></td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</html:form>
<script type="text/javascript">
	$(".display-info").fadeOut(800).fadeIn(800).fadeOut(400).fadeIn(400).fadeOut(400).fadeIn(400);
	var print = "<c:out value='${newSupplementaryImpApplicationForm.printSupplementary}'/>";
	if(print.length != 0 && print == "true") {
		var url ="newSupplementaryImpApp.do?method=showPrintDetails";
		myRef = window.open(url,"certificateCourse","left=20,top=20,width=800,height=500,toolbar=1,resizable=0,scrollbars=1");
	}
</script>