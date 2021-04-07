<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>

<script type="text/javascript">
	function editAdmSubjectForRank(id,subjectname,stream,groupname) {
		document.getElementById("method").value = "editAdmSubjectForRank";
		document.getElementById("Id").value = id;
		document.getElementById("origsubjectname").value =subjectname;
		document.getElementById("origstream").value =stream;
		document.getElementById("origgroupname").value =groupname;
		document.getElementById("subjectname").value =subjectname;
		document.getElementById("stream").value =stream;
		document.getElementById("groupname").value =groupname;
		document.getElementById("submitbutton").value="UPDATE";
		resetErrMsgs();
	}

	function deleteAdmnSubjectForRank(Id, subName) {
		deleteConfirm = confirm("Are you sure you want to delete this entry?")
		if (deleteConfirm) {
			document.location.href = "AdmnSubjectForRankEntry.do?method=deleteAdmSubjectForRank&id="
					+ Id + "&subjectname=" + subName;
		}
	}
	function reActivate() {
		document.location.href = "AdmnSubjectForRankEntry.do?method=reActivateAdmSubjectForRank";
	}

	function resetValues() {
		document.location.href = "AdmnSubjectForRankEntry.do?method=initAdmSubjectForRank";
		
		resetErrMsgs();
	}
</script>


<html:form action="/AdmnSubjectForRankEntry">
	
	<html:hidden property="method" styleId="method" value="addAdmSubjectForRank" />
	<html:hidden property="id" styleId="Id" value="" name="addAdmSubjectForRank"/>
	<html:hidden property="origsubjectname" styleId="origsubjectname" value="" name="addAdmSubjectForRank"/>
	<html:hidden property="origstream" styleId="origstream" value="" name="addAdmSubjectForRank"/>
	<html:hidden property="origgroupname" styleId="origgroupname" value="" name="addAdmSubjectForRank"/>
	<html:hidden property="formName" value="admSubjectForRankForm" />
	<html:hidden property="pageType" value="1" />

	<table width="100%" border="0">
		<tr>

			<td><span class="Bredcrumbs"><bean:message key="knowledgepro.admission" /><span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.admission.subject.entry" /> &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td valign="top">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> <bean:message key="knowledgepro.admission.subject.entry" /></strong></td>

					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="20" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<div align="right"><FONT color="red"> <span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields"/></span></FONT></div>
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
									

									<td width="22%" class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.admission.subject.entry.name" /></div>
									</td>
									<td width="16%" class="row-even"><span class="star">
									<html:text property="subjectname" styleId="subjectname" styleClass="TextBox"
										size="16" maxlength="100" /> </span></td>
										
										
										<td width="13%" height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.admission.subject.entry.groupname" /></div>
									</td>
									<td width="19%" height="25" class="row-even">
									
									<html:select property="groupname" styleId="groupname">
                                        <html:option value="">select</html:option>
	                                    <html:option value="Commerce">Commerce</html:option>
	                                    <html:option value="Science">Science</html:option>
	                                    <html:option value="Humanities/Arts">Humanities/Arts</html:option>
	                                     <html:option value="Language">Language</html:option>
	                                      <html:option value="Vocational">Vocational</html:option>
	                                       <html:option value="Core">Core</html:option>
	                                        <html:option value="Complimentary">Complimentary</html:option>
	                                         <html:option value="Common">Common</html:option>
	                                     <html:option value="Open">Open</html:option>
                                    </html:select>
								</tr>
								<tr>
								<td width="13%" height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.admission.subject.entry.stream" /></div>
									</td>
									<td width="19%" height="25" class="row-even">
									
									<html:select property="stream" styleId="stream">
                                        <html:option value="">select</html:option>
	                                    <html:option value="DEG">DEG</html:option>
	                                    <html:option value="Class 12">Class 12</html:option>

	                                    
                                    </html:select>
								
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
							<td width="45%" height="35">
							<div align="right">
									<html:submit property="" styleClass="formbutton" value=""
										styleId="submitbutton">
								   </html:submit>
						   </div>
							</td>
							<td width="2%"></td>
							<td width="53%"><html:button property=""
								styleClass="formbutton" value="Reset" onclick="resetValues()"></html:button></td>
						</tr>

					</table>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
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
									<td height="25" class="row-odd" align="center">
									<div align="center"><bean:message key="knowledgepro.slno" /></div>
									</td>
									<td height="25" class="row-odd" align="center">SUBJECT NAME</td>

									<td class="row-odd" align="center">GROUP NAME</td>
									<td class="row-odd" align="center">STREAM</td>
									<td class="row-odd" align="center">
									<div align="center"><bean:message key="knowledgepro.edit" /></div>
									</td>
									<td class="row-odd" align="center">
									<div align="center"><bean:message
										key="knowledgepro.delete" /></div>
									</td>
								</tr>
								<logic:iterate id="list" name="admSubjectForRankForm" property="subjecttolist"
									indexId="count">
									
									<c:choose>
										<c:when test="${count%2 == 0}">
											<tr class="row-even">
										</c:when>
										<c:otherwise>
											<tr class="row-white">
										</c:otherwise>
									</c:choose>
		
									<td width="8%" height="25" align="center">
									<div align="center"><c:out value="${count + 1}" /></div>
									</td>
									<td width="34%" height="25" align="center"><bean:write
										name="list" property="subjectname" /></td>

									<td width="37%"  align="center"><bean:write
										name="list" property="groupname" /></td>
										
									<td width="37%"  align="center"><bean:write
										name="list" property="stream" /></td>
											
									<td width="11%" height="25"  align="center">
									<div align="center"><img src="images/edit_icon.gif"
										width="16" height="18" style="cursor:pointer"
										onclick="editAdmSubjectForRank('<bean:write name="list" property="id"/>','<bean:write name="list" property="subjectname"/>','<bean:write name="list" property="stream"/>','<bean:write name="list" property="groupname"/>')">
									</div>
									</td>
									<td width="10%" height="25" align="center">
									<div align="center"><img src="images/delete_icon.gif"
										width="16" height="16" style="cursor:pointer"
										onclick="deleteAdmnSubjectForRank('<bean:write name="list" property="id"/>','<bean:write name="list" property="subjectname"/>')">
									</div>
									</td>
								
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