<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>

<%@page import="java.util.Map,java.util.HashMap"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<link href="styles.css" rel="stylesheet" type="text/css"/>
<SCRIPT type="text/javascript"><!--
	function searchStatusUpdate(){
		document.getElementById("method").value = "getStatusUpdateByAppNO";
		document.applicationStatusUpdateForm.submit();
	}
	function submitData(){
		//document.applicationStatusUpdateForm.method.value= "addApplicationStatusUpdate";
		document.getElementById("method").value = "addApplicationStatusUpdate";
		document.applicationStatusUpdateForm.submit();
	}
	function resetValues(){
		document.getElementById("applicationNo").value="";
		document.getElementById("applicationStatus").value="";
		document.getElementById("csvFile").value="";
		resetErrMsgs();
	}
	function cancelAction(){
		document.location.href= "applicationStatusUpdate.do?method=initApplicationStatusUpdate";
	}
	function getDocuments(year){
		getYearWiseDocuments(year,updateDocumentResult);
	}
	function updateDocumentResult(req){
		var responseObj = req.responseXML.documentElement;
		var childNodes = responseObj.childNodes;
		var items = responseObj.getElementsByTagName("option");
		var htm = "<table width='100%' cellspacing='1' cellpadding='2'>";
		htm = htm + "<tr>	<td height='25' class='row-odd' width='7%'>	<div align='center'>Sl.No</div>";
		htm = htm + "</td> <td height='25' class='row-odd' align='center' width='18%'>Application Number</td>";
		htm = htm + "<td class='row-odd' align='center' width='18%'>Application Status</td></tr>";
		var label, value;
		var count = 1;
		for ( var i = 0; i < items.length; i++) {
			label = items[i].getElementsByTagName("optionlabel")[0].firstChild.nodeValue;
			value = items[i].getElementsByTagName("optionvalue")[0].firstChild.nodeValue;

			htm = htm + "<tr>	<td height='25' class='row-even' width='7%'> <div align='center'> "+count;
			htm = htm + "</div> </td> <td height='25' class='row-even' align='center' width='18%'> "+value;
			htm = htm + "</td> <td class='row-even' align='center' width='20%'>"+label+"</td></tr>";
			count++;
		}
		htm = htm + "</table>";
		document.getElementById("replace").innerHTML=htm;
	}
</SCRIPT>

<html:form action="/applicationStatusUpdate" enctype="multipart/form-data">
	<html:hidden property="formName" value="applicationStatusUpdateForm" />
	<html:hidden property="pageType" value="1" />
	<html:hidden property="method" styleId="method" value="addApplicationStatusUpdate"/>
	<html:hidden property="origYear" styleId="origYear" name="applicationStatusUpdateForm"/>
	<table width="100%" border="0">
		<tr>
			<td><span class="heading"> <bean:message
				key="knowledgepro.admission" /> <span class="Bredcrumbs">&gt;&gt;
			Application Status Update &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader">Application Status Update</strong></td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="122" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" cellspacing="1" cellpadding="2">
						<tr bgcolor="#FFFFFF">
							<td height="20" colspan="4">
							<div align="right"><FONT color="red"> <span
								class='MandatoryMark'><bean:message
								key="knowledgepro.mandatoryfields" /></span></FONT></div>
							<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
							<FONT color="green"> <html:messages id="msg"
								property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
							</html:messages> </FONT></div>
							</td>
							
						</tr>
						<tr>
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
									<table width="100%" cellspacing="1" cellpadding="1">
										<tr><!--
										    <td height="25" class="row-odd" width="25%">
											<div align="right"> <bean:message
												key="knowledgepro.admin.year" />:</div>
											</td>
											  <td height="25" class="row-even" width="25%">
											<html:select property="year" styleClass="combo" styleId="year" name="applicationStatusUpdateForm" style="width: 90px;">
								               <html:option value="">
									           <bean:message key="knowledgepro.select" />-</html:option>
								<cms:renderAcademicYear></cms:renderAcademicYear>
							</html:select></td>-->
											<td height="25" class="row-odd" width="25%">
											<div align="right"><span class="Mandatory">*</span> <bean:message
												key="knowledgepro.admission.applicationnumber" />:</div>
											</td>
											<td height="25" class="row-even" width="25%"><span class="star">
											<html:text name="applicationStatusUpdateForm" 
												property="applicationNo" styleId="applicationNo"
												styleClass="TextBox" size="16" maxlength="50"/> </span><span
												class="star"></span></td>
											<td height="25" class="row-odd" width="25%"><bean:message key="knowledgepro.admin.year" />:</td>
											<td height="25" class="row-even" width="25%">
											<html:select property="year" styleId="appliedYear" onchange="getDocuments(this.value)"  styleClass="combo">
												<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
												<cms:renderAcademicYearForAdmission></cms:renderAcademicYearForAdmission>
											</html:select>
											</td>	
										</tr>
										<tr>
										
											<td height="25" class="row-odd" width="25%" colspan="1">
											<div align="right"><span class="Mandatory">*</span><bean:message
												key="knowledgepro.admission.applicationStatus.status" /> :</div>
											</td>
											<td class="row-even" width="25%" colspan="3" height="25">
											<html:select name="applicationStatusUpdateForm" property="applicationStatus" styleId="applicationStatus"  style="width: 1000px; height:21px;" styleClass="combo">
											    <html:option value="">
													<bean:message key="knowledgepro.admin.select" />
												</html:option>						
						                      <html:optionsCollection name="applicationStatusUpdateForm" property="applicationStatusMap" label="value" value="key" />
					                        </html:select></td>
										</tr>
										<tr>
										<td height="25" class="row-odd" colspan="4">
										<div align="center">OR</div>
										</td>
										</tr>
										<tr>
										  <td height="25" class="row-odd" width="25%" colspan="1">
											    <div align="right"><span class="Mandatory">*</span>Upload CSV File : </div>
										  </td>
										  <td class="row-even" width="50%" colspan="3"><label>
											<html:file property="csvFile" styleId="csvFile" size="15" maxlength="30" name="applicationStatusUpdateForm"/></label>
											</td>
										</tr>
									</table>
									</td>
									<td width="5" height="29" background="images/right.gif"></td>
								</tr>
								
								<tr>
									<td height="5"><img src="images/04.gif" width="5"
										height="5" /></td>
									<td background="images/05.gif"></td>
									<td><img src="images/06.gif" /></td>
								</tr>
							</table>
							</td>
							<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
						</tr>
						<tr>
							<td height="25" colspan="4">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td width="45%" height="35">
									<div align="right">
											<html:submit property="" styleClass="formbutton"
												value="Search" styleId="submitbutton" onclick="searchStatusUpdate()">
											</html:submit>
											<html:submit property="" styleClass="formbutton"
												value="Submit" styleId="submitbutton" onclick="submitData()">
											</html:submit>
									</div>
									</td>
									<td width="2%"><html:button property="" styleClass="formbutton" value="Reset" onclick="resetValues()"></html:button> </td>
                                    <td width="2%"><html:button property="" styleClass="formbutton" value="Cancel" onclick="cancelAction()"></html:button> </td> 
									  <td width="51%">
								</tr>
							</table>
							</td>
						</tr>
						<tr>
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
											<td height="25" colspan="4">
											<div id="replace">
											<table width="100%" cellspacing="1" cellpadding="2">
												<tr>
													<td height="25" class="row-odd" width="7%">
													<div align="center"><bean:message
														key="knowledgepro.admin.subject.subject.s1no" /></div>
													</td>
													<td height="25" class="row-odd" align="center" width="18%"><bean:message
														key="knowledgepro.admission.applicationnumber" /></td>
													<td class="row-odd" align="center" width="18%"><bean:message
														key="knowledgepro.admission.applicationStatus.status" /></td>
												</tr>
												<logic:notEmpty name="applicationStatusUpdateForm" property="statusUpdateTO">
												<logic:iterate name="applicationStatusUpdateForm"
													property="statusUpdateTO" id="status" indexId="count">
													
															<tr>
																<td height="25" class="row-even" width="7%">
																<div align="center"><c:out value="${count+1}" /></div>
																</td>
																<td height="25" class="row-even" align="center" width="18%"><bean:write
																	name="status" property="applicationNo" /></td>
																<td class="row-even" align="center" width="20%"><bean:write
																	name="status" property="applicationStatus" /></td>
															</tr>
												</logic:iterate>
												</logic:notEmpty>
											</table>
											</div>
											</td>
										</tr>
									</table>
									</td>
									<td width="5" height="29" background="images/right.gif"></td>
								</tr>

								<tr>
									<td height="5"><img src="images/04.gif" width="5"
										height="5" /></td>
									<td background="images/05.gif"></td>
									<td><img src="images/06.gif" /></td>
								</tr>
							</table>
							</td>
							<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
						</tr>

					</table>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td height="20" valign="top"></td>
					<td width="13" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				<tr>
					<td><img src="images/Tright_03_05.gif" width="9" height="29" /></td>
					<td width="100%" background="images/TcenterD.gif"></td>
					<td><img src="images/Tright_02.gif" width="9" height="29" /></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</html:form>
