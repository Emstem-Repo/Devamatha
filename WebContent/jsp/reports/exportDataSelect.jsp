<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>

<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<script type="text/javascript">

checked = false;
function checkAll() {
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
			e.checked = checked;
		}
	}
	if(e.checked){
		 document.getElementById("selectall").checked = true;
	}
}


function unCheckSelectAll(field) {
	 
    if(field.checked == false) {
     document.getElementById("selectall").checked = false;
    }
    
}

function submitSelected() {
	document.getElementById("method").value= "selectedCandidateList";
}


function cancelAction() {
	resetErrMsgs();
	document.location.href = "exportDataSearch.do?method=initExportSearch";
}
</script>

<body>
<html:form action="/exportDataSearch" method="post">
	<html:hidden property="method" styleId="method" value="submitExportSearch" />
	<html:hidden property="pageType" value="0" />
	<html:hidden property="formName" value="dataSearchForm" />
	<table width="98%" border="0" cellpadding="2" cellspacing="1">
		<tr>
			<td class="heading"><bean:message key="knowledgepro.reports" /><span
				class="Bredcrumbs">&gt;&gt; <span class="Bredcrumbs"><bean:message key="knowledgepro.reports.senddataforidcard.label"/>
				&gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td valign="top">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="5"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"><bean:message key="knowledgepro.reports.senddataforidcard.label"/></strong></td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td height="20" class="news">
					<div align="right" class="mandatoryfield"></div>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td height="45" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" align="center" cellpadding="0"
						cellspacing="0">
						<tr bgcolor="#FFFFFF">
							<div align="right"><span class='MandatoryMark'><bean:message
								key="knowledgepro.mandatoryfields" /></span></div>
							<td colspan="6" class="body" align="left">
							<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
							<FONT color="green"> <html:messages id="msg"
								property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
								<br>
							</html:messages> </FONT></div>
							</td>
						</tr>

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
									<td height="25" width="25%" class="row-odd"><bean:message key="knowledgepro.admin.sec.ProgramType.label.col"/>:<bean:write name="dataSearchForm" property="programTypeName"/></td>
									<td height="25" width="25%" class="row-odd"><bean:message key="knowledgepro.admin.sec.Program.label.col"/><bean:write name="dataSearchForm" property="programName"/></td>
									<td height="25" width="20%" class="row-odd"><bean:message key="knowledgepro.interview.Year"/><bean:write name="dataSearchForm" property="academicYear"/></td>
									<td height="25" width="10%" class="row-odd">&nbsp;</td>
									<td height="25" width="5%" class="row-odd">&nbsp;</td>
									<td height="25" width="5%" class="row-odd">&nbsp;</td>
									<td height="25" width="10%" class="row-odd">&nbsp;</td>
								</tr>
								<tr>
				                    <td width="5%" class="row-odd" align="center" ><bean:message key="knowledgepro.slno"/></td>
				                    <td width="20%" class="row-odd" align="center" ><bean:message key="knowledgepro.hostel.allocation.regno"/></td>
				                    <td width="20%" class="row-odd" align="center"><bean:message key="knowledgepro.admin.course"/></td>
				                    <td width="15%" class="row-odd"><div align="center"><bean:message key="knowledgepro.fee.appno"/></div></td>
				                    <td width="25%" class="row-odd"><div align="center"><bean:message key="knowledgepro.fee.studentname"/></div></td>
				                    <td width="10%" class="row-odd" align="center"><bean:message key="knowledgepro.fee.semister"/></td>
				                    <td width="5%" class="row-odd"><div align="center"><bean:message key="knowledgepro.select.all.label"/></div>
				                   		<input type="checkbox" id="selectall" checked="checked" onclick= "checkAll()" />
				                    </td>
			                 	</tr>
			                 	
			                 	<logic:notEmpty name="dataSearchForm" property="candidateList">
					            <nested:iterate  name="dataSearchForm" id="searchTO" property="candidateList" indexId="count">
								<%
									String dynamicStyle="";				
									if(count%2!=0){
										dynamicStyle="row-white";
					
									}else{
										dynamicStyle="row-even";
									}
									String rowid="Student"+count;
								%>
								
						        <tr >
					              <td width="6%" align="center" class='<%= dynamicStyle %>'>
					              	<div align="center"><c:out value="${count + 1}"/></div>
					              </td>
					              <td align="center" class='<%= dynamicStyle %>' >
					              	<nested:write name="searchTO" property="regNo"/>
					              </td>
					              <td align="center" class='<%= dynamicStyle %>' >
					              	<nested:write name="searchTO" property="courseName"/>
					              </td>
					              <td align="center" class='<%= dynamicStyle %>' >
					              	<nested:write name="searchTO" property="applnNo"/>
					              </td>
					              <td align="center" class='<%= dynamicStyle %>' >
									<nested:write name="searchTO" property="name"/>
								  </td>
					              <td align="center" class='<%= dynamicStyle %>' >
					              		<nested:write name="searchTO" property="semesterNo"/>
					              </td>
					              <td align="center" class='<%= dynamicStyle %>'>
					              <div align="center">
					              	
					    <input type="hidden" name="candidateList[<c:out value='${count}'/>].checked" id="candidatehidden_<c:out value='${count}'/>"
													value="<nested:write name='searchTO' property='checked'/>" />
						<input type="checkbox" name="candidateList[<c:out value='${count}'/>].updatedChecked" id="<c:out value='${count}'/>" 
						    onclick="unCheckSelectAll(this)"/>
						
					        <script type="text/javascript">
							var studentId = document.getElementById("candidatehidden_<c:out value='${count}'/>").value;
								if(studentId == "true") {
									document.getElementById("<c:out value='${count}'/>").checked = true;
								}else{
									document.getElementById("selectall").checked = false;	
								}	
									
						</script>  			
					              				
								  </div>
					              </td>
								</tr>
					 
					 			</nested:iterate> 
					            </logic:notEmpty>

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
					<td height="36" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">

					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="36%" height="35">
								<div align="right">
									<html:submit styleClass="formbutton" onclick="submitSelected()"><bean:message key="knowledgepro.submit.exportdata.label"/></html:submit>
								</div>
							</td>
							<td width="2%"></td>
							<td width="30%">
								<html:button property="" styleClass="formbutton" onclick="cancelAction()"><bean:message key="knowledgepro.cancel"/></html:button>
							</td>
						</tr>
					</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td height="26" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">&nbsp;</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
					<td width="931" background="images/TcenterD.gif"></td>
					<td><img src="images/Tright_02.gif" width="9" height="29"></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
	
</html:form>