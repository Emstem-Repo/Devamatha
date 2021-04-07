<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<script type="text/javascript">

function resetFields() {
		document.getElementById("method").value="initChangeSubject";
		document.changeSubjectForm.submit();
}
function saveData() {
		document.getElementById("method").value="saveSelectedSubjectData";
		document.changeSubjectForm.submit();
}

function selectAll(obj) {
	    var value = obj.checked;
	    var inputs = document.getElementsByTagName("input");
	    var inputObj;
	    var checkBoxselectedCount = 0;
	    for(var count1 = 0;count1<inputs.length;count1++) {
	          inputObj = inputs[count1];
	          var type = inputObj.getAttribute("type");
	            if (type == 'checkbox') {
	                  inputObj.checked = value;
	                  inputObj.value="on";
	            }
	    }
}

function unCheckSelectAll() {
	    var inputs = document.getElementsByTagName("input");
	    var inputObj;
	    var checkBoxOthersSelectedCount = 0;
	    var checkBoxOthersCount = 0;
	    for(var count1 = 0;count1<inputs.length;count1++) {
	          inputObj = inputs[count1];
	          var type = inputObj.getAttribute("type");
	            if (type == 'checkbox' && inputObj.id != "checkAll") {
	                  checkBoxOthersCount++;
	                  if(inputObj.checked) {
	                        checkBoxOthersSelectedCount++;
	                        inputObj.value="on";
	                  }else{
	                	  inputObj.value="off";	
	                      }   
	            }
	    }
	    if(checkBoxOthersCount != checkBoxOthersSelectedCount) {
	      document.getElementById("checkAll").checked = false;
	    } else {
	      document.getElementById("checkAll").checked = true;
	    }
}
</script>
<html:form action="/changeSubject" method="POST">	
	<html:hidden property="method" styleId="method" value="getClassDetails" />
	<html:hidden property="formName" value="changeSubjectForm" />
	<html:hidden property="pageType" value="1" />
	<table width="100%" border="0">
	<tr>
		<td><span class="Bredcrumbs"><bean:message key="knowledgepro.exam"/>
		<span class="Bredcrumbs">&gt;&gt;
		Change Subject
	 	&gt;&gt;</span></span></td>
	</tr>
	<tr>
		<td valign="top">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> Change Subject</strong></td>

					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="20" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<div align="right"><FONT color="red"> <span class='MandatoryMark'> <bean:message key="knowledgepro.mandatoryfields"/></span></FONT></div>
					<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
					<FONT color="green"> <html:messages id="msg" property="messages" message="true">
						<c:out value="${msg}" escapeXml="false"></c:out>
						<br>
					</html:messages> </FONT></div>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
						<td height="41" valign="top" background="images/Tright_03_03.gif"></td>
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
									<% boolean disable1=false;%>
										<logic:equal value="true" name="changeSubjectForm" property="flag">
										<% disable1=true;%>
										</logic:equal>
									<tr>
										<td height="25" class="row-odd">
										<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message key="knowledgepro.attendance.activityattendence.fromdate.required1" />:</div>
										</td>
										<td class="row-even">
										<html:text name="changeSubjectForm" property="fromDate" styleId="fromDate" size="10" maxlength="16" disabled='<%=disable1%>'/>
										<script language="JavaScript">
											new tcal( {
												// form name
												'formname' :'changeSubjectForm',
												// input name
												'controlname' :'fromDate'
												});
										</script>
										</td>
										<td height="25" class="row-odd">
											<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message key="knowledgepro.attendance.activityattendence.todate.required1" />:</div>
										</td>
									<td class="row-even">
									<html:text name="changeSubjectForm" property="toDate" styleId="toDate" size="10" maxlength="16" disabled='<%=disable1%>'/>
									<script language="JavaScript">
										new tcal( {
										// form name
										'formname' :'changeSubjectForm',
										// input name
										'controlname' :'toDate'
											});
									</script>
									</td>
									</tr>
									<tr><td height="25" width="25%" class="row-odd">
										<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.admisn.subject.code" /></div>
										</td>
										<td  height="25" class="row-even"><html:text
											property="subjectCode" styleId="subjectCode" maxlength="50"
											styleClass="TextBox" size="20" disabled='<%=disable1%>'/></td>
										<td height="25" class="row-even" colspan=2></td>
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
										<html:submit property="" styleClass="formbutton" value="Search"
											styleId="submitbutton">
										</html:submit>
										</div>
									</td>
									<td width="2%"></td>
									<td width="53%"><html:button property=""  styleClass="formbutton" value="Reset"
									onclick="resetFields()"></html:button></td>
								</tr>								
								<tr><td style="height: 10px" align="center" colspan="3"></td></tr>								
							</table>
						</td>
						<td width="10" valign="top" background="images/Tright_3_3.gif"
							class="news"></td>
					</tr>
				<logic:notEmpty property="classList1" name="changeSubjectForm">		
					<tr>
						<td height="35" valign="top" background="images/Tright_03_03.gif"></td>
	          			<td>
	          		<table width="100%" border="0" cellspacing="0" cellpadding="0">
	          				 <tr>
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
											<tr>
												<td class="row-even" width="10%">
												 <div align="right"><bean:message key="knowledgepro.admin.detailsubject.subjectname"/> :</div>
												</td>
											<td width="25%" height="25" class="row-even" colspan="3">&nbsp;&nbsp;&nbsp;&nbsp;<bean:write name="changeSubjectForm" property="subjectName" />
												</td>
											</tr>
											<tr>
												<td class="row-odd">
												<div align="center"><bean:message key="knowledgepro.slno" /></div>
												</td>
												<td align="center" height="25" class="row-odd">
												<input type="checkbox" id="checkAll" onclick="selectAll(this)"/> select
												</td>
												<td align="center" height="25" class="row-odd">
												<div align="center">Date</div>
												</td>
												<td align="center" height="25" class="row-odd">
												<div align="center">Class</div>
												</td>
											</tr>
												<nested:iterate id="to" property="classList1" name="changeSubjectForm" indexId="count">
													<c:choose>
														<c:when test="${count%2 == 0}">
															<tr class="row-even">
														</c:when>
														<c:otherwise>
															<tr class="row-white">
														</c:otherwise>
													</c:choose>
													<td height="25">
													<div align="center"><c:out value="${count + 1}" /></div>
													</td>
													<td width="10%" height="25">
													<div align="center">
													<nested:checkbox property="checked1"  onclick="unCheckSelectAll()"> </nested:checkbox>
													</div>
													</td>
													<td align="center" width="20%" height="25"><nested:write  name="to"
														 property="dates" /></td>
													<td align="center" width="50%" height="25"><nested:write  name="to"  
														 property="className" /></td>
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
	          		</table>
	          	</td>
	          	<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
	          </tr>
          			<tr>
	          			       <td height="20" valign="top" background="images/Tright_03_03.gif"></td>
	          			       <td>
	          			       <table width="100%" border="0" cellspacing="0" cellpadding="0">
	          			       <tr>
								<td valign="top" class="news">
								<table width="100%" border="0" align="center" cellpadding="0"
									cellspacing="0">
									<tr><td>&nbsp;&nbsp;</td></tr>
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
									<td height="25" class="row-odd" width="5%">
									<div align="left"><span class="Mandatory">*</span>&nbsp;Change to Subject</div>
									</td>
									<td height="25" class="row-even" width="30%" colspan="3"><html:select
										name="changeSubjectForm" property="changedSubject" styleClass="comboExtraLarge" styleId="changedSubject" >
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<logic:notEmpty name="changeSubjectForm" property="subjectList">
											<html:optionsCollection property="subjectList"
												name="changeSubjectForm" label="name" value="id" />
										</logic:notEmpty>
									</html:select></td>
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
								</tr>
								<tr>
									<td valign="top" class="news">
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
										<tr><td style="height: 10px" align="center" colspan="3"></td></tr>
										<tr>
											<td width="45%" height="35">
												<div align="right">
													<html:button property="" styleClass="formbutton" value="Submit"
														styleId="submitbutton" onclick="saveData()">
													</html:button>
												</div>
											</td>
											<td width="2%"></td>
											<td width="53%"><html:button property=""  styleClass="formbutton" value="Cancel"
											onclick="resetFields()"></html:button></td>
										</tr>										
										<tr><td style="height: 10px" align="center" colspan="3"></td></tr>										
									</table>
									</td>
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
