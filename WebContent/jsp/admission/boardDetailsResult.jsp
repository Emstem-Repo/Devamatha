<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>

<script type="text/javascript">
	function cancelAction() {
		document.location.href = "boardDetails.do?method=initBoardDetails";
	}
</script>
<html:form action="/boardDetails" method="post">
	<html:hidden property="method" styleId="method" value="updateBoardDetails" />
	<html:hidden property="formName" value="boardDetailsForm" />
	<html:hidden property="pageType" value="1" />
	<table width="99%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message
				key="knowledgepro.admission" /> <span class="Bredcrumbs">&gt;&gt;<bean:message
				key="knowledgepro.admission.boardDetails.label" />&gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="heading_white"><bean:message
						key="knowledgepro.admission.boardDetails.label" /></td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="news">
					<div align="right"><span class='MandatoryMark'><bean:message
						key="knowledgepro.mandatoryfields" /> </span></div>
					<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
					<FONT color="green"><html:messages id="msg"
						property="messages" message="true">
						<c:out value="${msg}" escapeXml="false"></c:out>
						<br>
					</html:messages> </FONT></div>
					</td>
					<td valign="top" background="images/Tright_3_3.gif"></td>
				</tr>
				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top">
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
									<td height="25" class="row-odd" width="25%">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.interview.Year" /></div>
									</td>
									<td  class="row-even" width="25%"><bean:write name="boardDetailsForm" property="year"/></td>
									
									<td height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.admission.boardDetails.termyear" />:</div>
									</td>
									<td   class="row-even">
									<bean:write name="boardDetailsForm" property="years"/>
									</td>
								</tr>
								<tr>
								
	 							   <td height="25" class="row-odd" width="25%">
									<div align="right"><bean:message
										key="knowledgepro.admin.program" />:</div>
									</td>
									<td   class="row-even" width="25%">
									<c:if test="${boardDetailsForm.programName!=null && boardDetailsForm.programName!='Select-'}">
									<bean:write name="boardDetailsForm" property="programName"/></c:if></td>
								
								    <td  height="25" class="row-odd" width="25%">
									<div align="right"><bean:message
										key="knowledgepro.attendance.class.col" /></div>
									</td>
									<td   class="row-even" width="25%">
									<c:if test="${boardDetailsForm.className!=null && boardDetailsForm.className!='Select-'}">
									<bean:write name="boardDetailsForm" property="className"/></c:if></td>
									
								</tr>
								<tr>
								    <td  height="25" class="row-odd" width="25%">
									<div align="right"><bean:message
										key="knowledgepro.admin.courses.report" />:</div>
									</td>
									<td   class="row-even" width="25%">
									<c:if test="${boardDetailsForm.courseName!=null && boardDetailsForm.courseName!='Select-'}">
									<bean:write name="boardDetailsForm" property="courseName"/></c:if></td>
									
									<td   height="25" class="row-odd" width="25%">
									<div align="right"><span class="Mandatory"></span><bean:message
										key="knowledgepro.hostel.reservation.registerNo" /></div>
									</td>
									
									<td   class="row-even" width="25%">
									<c:if test="${boardDetailsForm.registerNo!=null}">
									<bean:write name="boardDetailsForm" property="registerNo"/>
									</c:if></td>
									<td class="row-odd" width="25%"></td>
									<td class="row-even" width="25%"></td>
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
					<td valign="top" background="images/Tright_3_3.gif"></td>
				</tr>
				
				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top">
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
								<tr class="row-odd">
								<td>SI.No </td>
								<td>Student Name </td>
								<td>Register No</td>
								<td>Father Name</td>
								<td>Mother Name</td>
								<td>Exam Register No </td>
								<td>Student No </td>
								</tr>
								<nested:notEmpty name="boardDetailsForm" property="boardList">
								<nested:iterate id="bdForm" name="boardDetailsForm" property="boardList" indexId="count">
									<tr class="row-even"> 
										<td width="5%"><c:out value="${count+1}"></c:out></td>
										<td width="18%"><bean:write name="bdForm" property="studentName"/>  </td>
										<td width="8%"><bean:write name="bdForm" property="registerNo"/>  </td>
										<td width="14%"><bean:write name="bdForm" property="fatherName"/>  </td>
										<td width="14%"><bean:write name="bdForm" property="motherName"/>  </td>
										<td width="20%"><nested:text property="examRegNo" maxlength="15" size="20"/>  </td>
										<td width="20%"><nested:text property="studentNo" maxlength="15" size="20"/>  </td>
									</tr>
								</nested:iterate>
								</nested:notEmpty>
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
					<td valign="top" background="images/Tright_3_3.gif"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="heading">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td height="35" align="center">
							<html:submit styleClass="formbutton"></html:submit> &nbsp;&nbsp;
							<html:cancel styleClass="formbutton">
												<bean:message key="knowledgepro.admin.reset" />
											</html:cancel>&nbsp;&nbsp;
							
							<html:button property="" styleClass="formbutton" value="Cancel" onclick="cancelAction()"></html:button>
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




