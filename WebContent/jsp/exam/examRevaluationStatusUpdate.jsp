<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<script type="text/javascript">
function cancelAction(){
	document.location.href = "examRevaluationStatusUpdate.do?method=initRevaluationStatusUpdate";
}

function updateRevAppStatus(){
	document.getElementById("method").value="updateRevaluationStatus";
}
</script>
<html:form action="/examRevaluationStatusUpdate" >
	<html:hidden property="method" styleId="method" value="getStudentRevaluationDetails" />	
	<html:hidden property="formName" value="examRevaluationStatusUpdateForm" />
	<html:hidden property="pageType" value="1" />
	
	
	<table width="98%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message key="knowledgepro.exam" />
			<span class="Bredcrumbs">&gt;&gt; <bean:message key="knowledgepro.exam.revaluation.status.update" /> &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="10"><img src="images/Tright_03_01.gif"></td>
					<td width="100%" background="images/Tcenter.gif" class="body">
					<div align="left"><strong class="boxheader"><bean:message key="knowledgepro.exam.revaluation.status.update" /></strong></div>
					</td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
						<tr>
							<td colspan="6" align="left">
							<div align="right"><span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields" /></span></div>
							<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
							<FONT color="green"><html:messages id="msg"
								property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
								<br>
							</html:messages></FONT></div>
							</td>
						</tr>
						<tr>
							<td><img src="images/01.gif" width="5" height="5" /></td>
							<td width="914" background="images/02.gif"></td>
							<td><img src="images/03.gif" width="5" height="5" /></td>
						</tr>
						<tr>
							<td width="5" background="images/left.gif"></td>
							<td width="100%" valign="top">
							<table width="100%" height="30" border="0" cellpadding="0" cellspacing="1">
                  <tr class="row-white">
                    <td width="140" class="row-odd"><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.exam.reJoin.registerNo"/>:</div></td>
                    <td width="167" class="row-even"><html:text	property="registerNo" styleId="registerNo" styleClass="TextBox" size="20" maxlength="30" /></td>
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
					<div align="center">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr><td style="height: 10px" align="center" colspan="3"></td></tr>
						<tr>
							<td width="46%" height="35">
							<div align="right"><html:submit styleClass="formbutton">
								Search
							</html:submit></div>
							</td>
							<td width="2%"></td>
							<td width="52%" align="left"><html:button property=""
								styleClass="formbutton" onclick="resetFieldAndErrMsgs()">
								<bean:message key="knowledgepro.admin.reset" />
								</html:button> 
							</td>
						</tr>						
						<tr><td style="height: 10px" align="center" colspan="3"></td></tr>						
					</table>
					</div>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
		<!--  added the result table -->
				<logic:notEmpty name="examRevaluationStatusUpdateForm" property="revAppToList">
							<tr>
							<td height="25" colspan="6">
							<table width="100%" border="0" align="center" cellpadding="0"
								cellspacing="0">
								<tr>
									<td><img src="images/01.gif" width="5" height="5"></td>
									<td width="914" background="images/02.gif"></td>
									<td><img src="images/03.gif" width="5" height="5"></td>
								</tr>
								<tr>
									<td width="5" background="images/left.gif"></td>
									<td valign="top">
									<table width="100%" cellspacing="1" cellpadding="2">

										<tr>
											<td height="25" class="row-odd" align="center"><bean:message key="knowledgepro.exam.examDefinition.examName" /></td>
											<td class="row-odd" align="center"><bean:message key="knowledgepro.exam.UnvSubCode.subjectCode" /></td>
											<td class="row-odd" align="center"><bean:message key="knowledgepro.exam.UnvSubCode.subjectName" /></td>
											<td class="row-odd" align="center"><bean:message key="knowledgepro.exam.revaluation.current.status.label" /></td>	
											<td class="row-odd"><bean:message key="knowledgepro.exam.revaluation.new.status.label" /></td>
										</tr>
										
										<nested:iterate  name="examRevaluationStatusUpdateForm" property="revAppToList" indexId="count">
										<tr>
											<c:choose>
												<c:when test="${count%2 == 0}">
													<tr class="row-even">
												</c:when>
												<c:otherwise>
													<tr class="row-white">
												</c:otherwise>
											</c:choose>
															<td width="20%" class="row-even" align="center"><nested:write
																 property="examName" /></td>
															<td width="15%" height="25" class="row-even"
																align="center"><nested:write 
																 property="subjectCode" /></td>
															<td width="20%" class="row-even" align="center"><nested:write
																 property="subjectName" /></td>	
															<td width="20%" class="row-even" align="center"><nested:write
																 property="status" /></td>
															<td width="25%" class="row-even">
															<nested:textarea  property="newStatus" rows="2" cols="30"></nested:textarea>
															</td>
											</tr>	
										</nested:iterate>
									</table>
									<div align="center">
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td width="46%" height="35">
											<div align="right"><html:submit
											styleClass="formbutton" onclick="updateRevAppStatus()">
											Submit</html:submit></div>
											</td>
											<td width="2%"></td>
											<td width="53"><html:button property="cancel"
												onclick="cancelAction()" styleClass="formbutton">
												<bean:message key="knowledgepro.cancel" />
											</html:button></td>
										</tr>
									</table>
									</div>
									</td>
									<td width="5" height="30" background="images/right.gif"></td>
								</tr>
								<tr>
									<td height="5"><img src="images/04.gif" width="5"
										height="5"></td>
									<td background="images/05.gif"></td>
									<td><img src="images/06.gif"></td>
								</tr>
							</table>
								
							</td>
						</tr>				
				</logic:notEmpty>
				
				
				<tr>
					<td><img src="images/Tright_03_05.gif" height="29" width="9"></td>
					<td background="images/TcenterD.gif" width="100%"></td>
					<td><img src="images/Tright_02.gif" height="29" width="9"></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</html:form>
