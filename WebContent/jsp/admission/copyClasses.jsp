<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<script type="text/javascript">
checked = false;
function checkAll () {
	if (checked == false) {
		checked = true;
	} else {
		checked = false;
	}

	for (var i=0;i<document.forms[0].elements.length;i++)
	{	
		var e=document.forms[0].elements[i];
		if ((e.type=='checkbox'))
		{
			e.checked=checked;
		}
	}
}
function unCheckSelectAll(field) {

    if(field.checked == false) {
    	document.getElementById("selectall").checked = false;
    }

}
function setSelectedCandidatesCount() {
	var totalselectedCount = 0;
	for (var i=0;i<document.forms[0].elements.length;i++)
	{	
		var e=document.forms[0].elements[i];
		if ((e.type=='checkbox'))
		{
			if(e.name != "selectall") {
    			if(e.checked == true)
				totalselectedCount = totalselectedCount +1;
			}
		}
	}
}
function searchDetails(){		
	document.getElementById("method").value="searchCopyClasses";
}

function resetMessages() {	
	resetErrMsgs();
}	
function getClasses(year) {
	document.getElementById("fromYearId").value = year;
}
function copySelected(){
	document.getElementById("method").value="copySelectedClasses";
}
function cancelAction() {
	document.location.href = "copyClasses.do?method=initCopyClasses";
}
</script>
<html:form action="/copyClasses" method="post">
	<html:hidden property="method" styleId="method" />
	<html:hidden property="formName" value="copyClassesForm" />
	<html:hidden property="pageType" value="1" />
	<html:hidden property="id" />
	<table width="100%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message key="knowledgepro.admission"/>
			<span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.admission.copyClasses"/>
			 &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td valign="top">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> <bean:message key="knowledgepro.admission.copyClasses"/> Entry</strong></td>

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
									<td width="20%" height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.admission.copyClasses.fromYear" /> :</div>
									</td>
									<td width="30%" class="row-even"><input type="hidden"
										id="yr" name="yr"
										value='<bean:write name="copyClassesForm" property="fromYear"/>' />
									<html:select property="fromYear"
										styleId="fromYearId" styleClass="combo" onchange="getClasses(this.value)">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<cms:renderYear></cms:renderYear>
									</html:select></td>
									<td width="20%" height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.admission.copyClasses.toYear" /> :</div>
									</td>
									<td width="30%" class="row-even"><input type="hidden"
										id="toyrId" name="toyr"
										value='<bean:write name="copyClassesForm" property="toYear"/>' />
									<html:select property="toYear"
										styleId="toYearId" styleClass="combo">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<cms:renderYear></cms:renderYear>
									</html:select></td>
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
						<tr><td style="height: 10px" align="center" colspan="3"></td></tr>
						<tr>
									<td width="45%" height="35">
									<div align="right">
											<html:submit property="" styleClass="formbutton"
												onclick="searchDetails()">
												<bean:message key="knowledgepro.admin.search" />
											</html:submit>
									</div>
									</td>
									<td width="2%"></td>
									<td width="53%">
											<html:button property="" styleClass="formbutton"
												value="Reset" onclick="resetMessages()">
												<bean:message key="knowledgepro.admin.reset" />
											</html:button>
									</td>
								</tr>
					<tr><td style="height: 10px" align="center" colspan="3"></td></tr>								
					</table>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				<logic:notEmpty name="copyClassesForm" property="classesList">	
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
											<td height="25" class="row-odd">
											<div align="center"><bean:message
												key="knowledgepro.slno" /></div>
											</td>
											<td class="row-odd">
												<div align="center"><bean:message
													key="knowledgepro.admission.addtoselectedclasseslist" /><br>
											<input type="checkbox" id="selectall" name="selectall"
												onclick="checkAll()" /></div>
											</td>
											<td height="25" class="row-odd" align="center"><bean:message
												key="knowledgepro.admission.className" /></td>
											<td height="25" class="row-odd" align="center"><bean:message
												key="knowledgepro.admission.courseName" /></td>
											<td height="25" class="row-odd" align="center"><bean:message
												key="knowledgepro.admission.semesterNo" /></td>	
											<td height="25" class="row-odd" align="center"><bean:message
												key="knowledgepro.admission.sectionName" /></td>	
										</tr>
										
										<nested:iterate id="cList" name="copyClassesForm" property="classesList"
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
											<td width="6%" height="25">
															<div align="center"><c:out value="${count + 1}" /></div>
															</td>
															<td width="9%">
																<div align="center">
																<nested:checkbox  property="classesSelId" styleId="selectedId" onclick="setSelectedCandidatesCount();unCheckSelectAll(this)"></nested:checkbox>
																</div>
															</td>
															<td width="20%" align="center"><bean:write
																name="cList" property="className" /></td>
															<td width="41%" height="25" align="center"><bean:write name="cList"
																property="courseTo.name" /></td>
															<td width="15%" align="center"><bean:write
																name="cList" property="termNo" /></td>	
															<td width="15%" align="center"><bean:write
																name="cList" property="sectionName" /></td>
											</tr>	
										</nested:iterate>
										
									</table>
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
								<div align="center">
									<table width="100%" height="54" border="0" cellpadding="1"
										cellspacing="2">
										<tr>
											<td width="100%" height="50" class="heading">
											<div align="center">
											<table width="100%" height="27" border="0" cellpadding="0"
												cellspacing="0">
				
												<tr>
													<td>
													<div align="center">
													<table width="100%" height="27" border="0" cellpadding="0"
														cellspacing="0">
														<tr>
															<td width="52%" height="45">
															<table width="100%" border="0" cellspacing="0"
																cellpadding="0">
																<tr>
																	<td width="45%" height="35">
																	<div align="right"><html:submit
																		styleClass="formbutton" onclick="copySelected()">
																		<bean:message key="knowledgepro.admission.copy" />
																	</html:submit></div>
																	</td>
																	<td width="2%"></td>
																	<td width="53"><html:button property="cancel"
																		onclick="cancelAction()" styleClass="formbutton">
																		<bean:message key="knowledgepro.cancel" />
																	</html:button></td>
																</tr>
															</table>
															</td>
														</tr>
													</table>
													</div>
													</td>
												</tr>
											</table>
											</div>
											</td>
										</tr>
									</table>
								</div>
							</td>
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
<script type="text/javascript">
	var yearId = document.getElementById("yr").value;
	if (yearId != null && yearId.length != 0) {
		document.getElementById("fromYearId").value = yearId;
	}
	var yearId = document.getElementById("toyrId").value;
	if (yearId != null && yearId.length != 0) {
		document.getElementById("toYearId").value = yearId;
	}
	
</script>
