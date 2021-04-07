<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<script type="text/javascript">
	function editDetails(id){
		document.location.href = "examinationSessions.do?method=edit&id="+ id;
	}
	function deleteDetails(id) {
		deleteConfirm = confirm("Are you sure you want to delete this entry?");
		if (deleteConfirm == true) {
			document.location.href = "examinationSessions.do?method=delete&id="+ id;
		}
	}
	function cancel(){
		document.location.href = "LoginAction.do?method=loginAction";
	}
	function cancelHome(){
		document.location.href = "examinationSessions.do?method=initExaminationSessions";
	}
	function resetMessages(){
		 resetFieldAndErrMsgs();
		}
</script>
<html:form action="/examinationSessions">
<html:hidden property="formName" value="examinationSessionsForm" />
<html:hidden property="pageType" value="1" />
<c:choose>
	<c:when test="${admOperation != null && admOperation == 'edit'}">
		<html:hidden property="method" value="update"/>
	</c:when>
	<c:otherwise>
		<html:hidden property="method" value="addExaminationSessions"/>
	</c:otherwise>
</c:choose>
<table width="100%" border="0">
		<tr>
			<td><span class="heading"> Exam Allotment <span class="Bredcrumbs">&gt;&gt;
			Examination Sessions &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td valign="top">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> Examination Sessions</strong></td>

					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="20" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<div align="right"><FONT color="red"> <span
						class='MandatoryMark'><bean:message
						key="knowledgepro.mandatoryfields" /></span></FONT></div>
					<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
					<FONT color="green"> <html:messages id="msg"
						property="messages" message="true">
						<c:out value="${msg}" escapeXml="false"></c:out>
						<br>
					</html:messages> </FONT></div>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td height="41" valign="top" background="images/Tright_03_03.gif"></td>

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
									<td class="row-odd" width="25%">
										<div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.admin.session"/>:</div>
									</td>
									<td class="row-even" width="25%">
										<html:text property="session" styleId="sessionId" size="5" maxlength="5"></html:text> 
                					</td>
									<td width="25%" height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span> <bean:message key="knowledgepro.exam.allotment.order.no"/>:</div>
									</td>
									<td width="25%" class="row-even"> 
										<html:text property="orderNo" styleId="orderNoId" size="2" maxlength="2" onkeypress="return isNumberKey(event)"></html:text>
                					</td>
								</tr>
								<tr>
									<td class="row-odd" width="25%">
										<div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.exam.allotment.session.description"/>:</div>
									</td>
									<td class="row-even" width="25%"> 
                    					<html:text  property="sessionDesc" styleId="sessionDesc" size="30" maxlength="50"/>
                					</td>
									<td class="row-odd" width="25%" >
										<div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.interview.Timings"/>:</div>
									</td>
                					<td class="row-even" align="left" width="25%" >
                						<html:text  property="timings" styleId="timings" size="10" maxlength="20"/>
									</td>
                				</tr>
							</table>
							</td>
							<td width="5" height="29" background="images/right.gif"></td>
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
					<td valign="top" class="news" >
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
						<c:choose>
								<c:when test="${admOperation != null && admOperation == 'edit'}">
									<td width="40%" height="35" align="center"></td>
									<td width="5%" height="35" align="center">
										<html:submit property="" styleClass="formbutton" value="Update" styleId="submitbutton"></html:submit>
									</td>
									<td width="50%" height="35" align="left">
										<html:button property="" value="Cancel" styleId="editReset" styleClass="formbutton" onclick="cancelHome()"></html:button>
									</td>
								</c:when>
								<c:otherwise>
									<td width="40%" height="35" align="center"></td>
									<td width="7%" height="35" align="center">
										<html:submit property="" styleClass="formbutton" value="Submit"	styleId="submitbutton"></html:submit>
									</td>
									<td width="5%" height="35" align="left">
										<html:button property="" value="Reset" styleClass="formbutton" onclick="resetMessages()"></html:button>
									</td>
									<td width="50%" height="35" align="left">
										<html:button property="" value="Cancel" styleClass="formbutton" onclick="cancel()"></html:button>
									</td>
								</c:otherwise>
						</c:choose>
						</tr>

					</table>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				<logic:notEmpty  name="examinationSessionsForm" property="list">
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
								<tr >
                    				<td width="5"  height="5%" class="row-odd" ><div align="center"><bean:message key="knowledgepro.slno"/></div></td>
                    				<td width="15" height="30%" class="row-odd" align="center" ><bean:message key="knowledgepro.admin.session"/></td>
                    				<td width="15" height="30%" class="row-odd" align="center" ><bean:message key="knowledgepro.exam.allotment.order.no"/></td>
                    				<td width="15" height="30%" class="row-odd" align="center" ><bean:message key="knowledgepro.exam.allotment.session.description"/></td>
                    				<td width="15" height="30%" class="row-odd" align="center" ><bean:message key="knowledgepro.interview.Timings"/></td>
                    				<td width="5" height="25" class="row-odd" align="center" ><bean:message key="knowledgepro.edit"/></td>
                    				<td width="5" class="row-odd"><div align="center"><bean:message key="knowledgepro.delete"/></div></td>
                 				</tr>
								<logic:iterate id="CME" name="examinationSessionsForm" property="list" indexId="count">
                					<c:choose>
										<c:when test="${count%2 == 0}">
											<tr class="row-even">
										</c:when>
										<c:otherwise>
											<tr class="row-white">
										</c:otherwise>
									</c:choose>
                   				<tr>
                   					<td  height="25" class="row-even" ><div align="center"><c:out value="${count + 1}"/></div></td>
                   					<td  height="25" class="row-even" align="center"><bean:write name="CME" property="session"/></td>
                   					<td  height="25" class="row-even" align="center"><bean:write name="CME" property="orderNo"/></td>
                   					<td  height="25" class="row-even" align="center"><bean:write name="CME" property="sessionDesc"/></td>
                   					<td  height="25" class="row-even" align="center"><bean:write name="CME" property="timings"/></td>
                   					<td  height="25" class="row-even" align="center"> <div align="center"><img src="images/edit_icon.gif"
						 					height="18" style="cursor:pointer" onclick="editDetails('<bean:write name="CME" property="id"/>')"> </div> </td>
                   					<td  height="25" class="row-even" ><div align="center">
                   						<img src="images/delete_icon.gif" width="16" height="16" style="cursor:pointer" onclick="deleteDetails('<bean:write name="CME" property="id"/>')"></div></td>
                   				</tr>
                				</logic:iterate>
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
				</logic:notEmpty>
				
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>

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
