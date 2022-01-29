<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@ taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>

<script>
	function editEntry(id, streamName) 
	{
		document.getElementById("id").value = id;
		document.getElementById("streamName").value = streamName;
		document.getElementById("origStreamName").value = streamName;
		document.getElementById("method").value = "updateConsolidatedSubjectStream";
		document.getElementById("submitbutton").value="Update";
	}
	function deleteEntry(id, streamName) 
	{
		deleteConfirm = confirm("Are you sure you want to delete this entry?");
		if (deleteConfirm) 
		{
			 document.location.href="ConsolidatedSubjectStream.do?method=deleteConsolidatedSubjectStream&id=" + id + "&streamName=" + streamName;
		}
	}
	function reActivate()
	{
		var dupId = document.getElementById("dupId").value;
		document.location.href = "ConsolidatedSubjectStream.do?method=reActivateConsolidatedSubjectStream&dupId=" + dupId;
	}
	function resetFormFields()
	{	
		document.getElementById("streamName").value = null;
		document.getElementById("submitbutton").value="Submit";
		document.getElementById("method").value="submitConsolidatedSubjectStream";
		resetErrMsgs();
	}
</script>

<html:form action="/ConsolidatedSubjectStream" method="POST">

	<html:hidden property="formName" value="consolidatedSubjectStreamForm"/>
	<html:hidden property="pageType" value="1"/>
	<html:hidden property="id" styleId="id"/>
	<html:hidden property="origStreamName" styleId="origStreamName"/>
	<html:hidden property="dupId" styleId="dupId"/>
	
	<c:choose>
		<c:when test="${admOperation != null && admOperation == 'edit'}">
			<html:hidden property="method" styleId="method" value="updateConsolidatedSubjectStream" />
		</c:when>
		<c:otherwise>
			<html:hidden property="method" styleId="method" value="submitConsolidatedSubjectStream" />
		</c:otherwise>
	</c:choose>
	
	<table width="100%" border="0">
		<tr>
			<td>
				<span class="Bredcrumbs">
					<bean:message key="knowledgepro.exam"/>
					&gt;&gt;
					<bean:message key="knowledgepro.consolidatedSubjectStream"/>
					&gt;&gt;
				</span>
			</td>
		</tr>
		<tr>
			<td valign="top">
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td width="9">
							<img src="images/Tright_03_01.gif" width="9" height="29">
						</td>
						<td background="images/Tcenter.gif" class="body">
							<strong class="boxheader"> 
								<bean:message key="knowledgepro.consolidatedSubjectStream"/>
							</strong>
						</td>
						<td width="10">
							<img src="images/Tright_1_01.gif" width="9" height="29">
						</td>
					</tr>
					<tr>
						<td height="20" valign="top" background="images/Tright_03_03.gif"></td>
						<td valign="top" class="news">
							<div align="right">
								<FONT color="red">
									 <span class='MandatoryMark'>
									 	<bean:message key="knowledgepro.mandatoryfields"/>
									 </span>
								</FONT>
							</div>
							<div id="errorMessage">
								<FONT color="red">
									<html:errors/>
								</FONT>
								<FONT color="green">
									 <html:messages id="msg" property="messages" message="true">
										<c:out value="${msg}" escapeXml="false"></c:out>
										<br>
									</html:messages>
							    </FONT>
						    </div>
						</td>
						<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
					</tr>
					<tr>
						<td height="41" valign="top" background="images/Tright_03_03.gif"></td>
						<td valign="top" class="news">
							<table width="100%"
								   border="0" 
								   align="center" 
								   cellpadding="0"
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
													<div align="right">
														<span class="Mandatory">
															*
														</span>
														<bean:message key="knowledgepro.consolidatedSubjectStream.streamName"/>:
													</div>
												</td>
												<td width="50%" height="25" class="row-even">
													<html:text property="streamName" 
															   styleId="streamName" 
							             					   size="20" 
															   maxlength="50"/>
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
						<td height="35" valign="top" background="images/Tright_03_03.gif"></td>
						<td valign="top" class="news">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td width="50%" height="35">
										<div align="right">
											<c:choose>
												<c:when test="${operation == 'edit'}">
													<input type="submit" class="formbutton" value="Update" id="submitbutton"/>
												</c:when>
												<c:otherwise>
													<input type="submit" class="formbutton" value="Submit" id="submitbutton"/>
												</c:otherwise>
											</c:choose>
										</div>
									</td>
									<td width="2%"></td>
									<td width="50%">
										<html:button property=""
													 styleClass="formbutton" 
													 value="Reset"
						 							 onclick="resetFormFields()">
										</html:button>
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td height="25" colspan="6">
							<table width="100%" 
								   border="0" 
								   align="center" 
								   cellpadding="0"
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
												<td width="10%" height="25" class="row-odd">
													<div align="center">
														<u><bean:message key="knowledgepro.slno" /></u>
													</div>
												</td>
												<td width="50%" class="row-odd">
													<div align="center">
														<u><bean:message key="knowledgepro.consolidatedSubjectStream.streamName" /></u>
													</div>
												</td>
												<td width="20%" height="25" class="row-odd">
													<div align="center">
														<u><bean:message key="knowledgepro.edit" /></u>
													</div>
												</td>
												<td width="20%" height="25" class="row-odd">
													<div align="center">
														<u><bean:message key="knowledgepro.delete" /></u>
													</div>
												</td>
											</tr>
											<c:if test="${subjectStreams != '' || subjectStreams != null}">
												<logic:iterate name="consolidatedSubjectStreamForm" 
															   property="subjectStreams" 
															   id="subjectStreams"
															   indexId="count">
											<tr>
											<c:choose>
												<c:when test="${count%2 == 0}">
													<tr class="row-even">
												</c:when>
												<c:otherwise>
													<tr class="row-white">
												</c:otherwise>
											</c:choose>
												<td width="10%" height="25">
													<div align="center">
														<c:out value="${count + 1}" />
													</div>
												</td>
												<td width="50%" align="center">
													<bean:write name="subjectStreams" property="streamName"/>
												</td>								

			
												<td width="20%" height="25" align="center">
													<div align="center">
														<img src="images/edit_icon.gif"
															 width="16" 
															 height="18"
        	        			 			 				 style="cursor: pointer;"
		 													 onclick="editEntry('<bean:write name="subjectStreams" property="id"/>',
    	           												                '<bean:write name="subjectStreams" property="streamName"/>')">
													</div>
												</td>
												<td width="20%" height="25">
													<div align="center">
														<img src="images/delete_icon.gif"
															 width="16" 
															 height="16"
                				 				 			 style="cursor: pointer;"
															 onclick="deleteEntry('<bean:write name="subjectStreams" property="id"/>',
    	           												                  '<bean:write name="subjectStreams" property="streamName"/>')">
													</div>
												</td>
											</tr>
												</logic:iterate>
											</c:if>
										</table>
									</td>
									<td width="5" height="30" background="images/right.gif"></td>
								</tr>
								<tr>
									<td height="5">
										<img src="images/04.gif" width="5" height="5"></td>
									<td background="images/05.gif"></td>
									<td><img src="images/06.gif"></td>
								</tr>
							</table>
						</td>
					</tr>
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
