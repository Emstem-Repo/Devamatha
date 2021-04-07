<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<script type="text/javascript">
function editExamCenter(id){
	document.location.href = "ExamCenterEntry.do?method=editExamCenter&id="+ id;
}
function deleteExamCenter(id){
	deleteConfirm = confirm("Are you sure you want to delete this entry?")
	if (deleteConfirm) {
		document.location.href = "ExamCenterEntry.do?method=deleteExamCenter&id="+ id;
	}
}
function reActivate(){
	document.location.href = "ExamCenterEntry.do?method=reActivateCenter";
}
function resetFormFields(){	
	//document.getElementById("docTypeId").selectedIndex = 0;
	//document.getElementById("examName").value="";
	resetErrMsgs();
	
}

function getIsExamCentreSeatNoRequired(pgmId){
	var url = "ExamCenterEntry.do";
	var args = "method=getExamCentreSeatNoRequired&programId="+pgmId;
	requestOperationProgram(url, args, setMandatory);
}
function setMandatory(req){
	var responseObj = req.responseXML.documentElement;
	var value = responseObj.getElementsByTagName("value");
	if(value!=null){
		for ( var I = 0; I < value.length; I++) {
			if(value[I].firstChild!=null){
			var temp = value[I].firstChild.nodeValue;
			document.getElementById("isSeatNoValidationRequired").value=temp;
			if(temp=="true"){
				document.getElementById("hideMandatory").style.display="block";
				document.getElementById("hideMandatory1").style.display="block";
				document.getElementById("hideMandatory2").style.display="block";
				document.getElementById("hideMandatory3").style.display="block";
				
					}else
						{
						document.getElementById("hideMandatory").style.display="none";
						document.getElementById("hideMandatory1").style.display="none";
						document.getElementById("hideMandatory2").style.display="none";
						document.getElementById("hideMandatory3").style.display="none";
							}		}
	}
}
}
</script>
<html:form action="/ExamCenterEntry">	
	<c:choose>
		<c:when test="${centerOperation == 'Edit'}">
		<html:hidden property="method" styleId="method" value="updateExamCenter" />
		</c:when>
		<c:otherwise>
	<html:hidden property="method" styleId="method" value="addExamCenter" />
	</c:otherwise>
	</c:choose>
	
	<html:hidden property="formName" value="examCenterForm" />
	<html:hidden property="pageType" value="1" />
	<html:hidden property="isSeatNoValidationRequired" styleId="isSeatNoValidationRequired"/>
	<table width="100%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message key="knowledgepro.admission"/>
			<span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.admission.examCenter.displayName"/>
			 &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td valign="top">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> <bean:message key="knowledgepro.admission.examCenter.displayName"/> Entry</strong></td>

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
									<td width="25%" height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span> <bean:message key="knowledgepro.admin.program"/>:</div>
									</td>
									<td width="25%" colspan="2" class="row-even">
									<div align="left">
									<html:select property="programId" styleClass="combo" styleId="programId" name="examCenterForm" onchange="getIsExamCentreSeatNoRequired(this.value);">
									<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
									<logic:notEmpty name="examCenterForm" property="programList">
									<html:optionsCollection property="programList" name="examCenterForm" label="name" value="id" />
									</logic:notEmpty>
									</html:select>
									</div>
									</td>
									<td width="25%" height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span> <bean:message key="knowledgepro.admission.center.lable"/>:</div>
									</td>
									<td width="25%" colspan="2" class="row-even">
									<html:text property="center" name="examCenterForm" maxlength="50" styleId="centerId"></html:text>
									</td>
								</tr>
								<script type="text/javascript">
									var temp=document.getElementById("isSeatNoValidationRequired").value;
									if(temp){
										document.getElementById("hideMandatory").style.display="block";
										document.getElementById("hideMandatory1").style.display="block";
										document.getElementById("hideMandatory2").style.display="block";
										document.getElementById("hideMandatory3").style.display="block";
										
											}else
												{
												document.getElementById("hideMandatory").style.display="none";
												document.getElementById("hideMandatory1").style.display="none";
												document.getElementById("hideMandatory2").style.display="none";
												document.getElementById("hideMandatory3").style.display="none";
													}
									</script>
								
								<tr> 
									<td width="25%" height="25" class="row-odd">
									<div align="right"><div id="hideMandatory"><span class="Mandatory">*</span></div><bean:message key="knowledgepro.admission.seatNoFrom.lable"/>:</div>
									</td>
									<td width="25%" colspan="2" class="row-even">
									<html:text property="seatNoFrom" name="examCenterForm" maxlength="50" styleId="seatNoFromId"></html:text>
									</td>
									<td width="25%" height="25" class="row-odd">
									<div align="right"><div id="hideMandatory1"><span class="Mandatory">*</span></div><bean:message key="knowledgepro.admission.seatNoTo.lable"/>:</div>
									</td>
									<td width="25%" colspan="2" class="row-even">
									<html:text property="seatNoTo" name="examCenterForm" maxlength="50" styleId="seatNoToId"></html:text>
									</td>
								</tr>
								<tr> 
									<td width="25%" height="25" class="row-odd">
									<div align="right"><div id="hideMandatory2"><span class="Mandatory">*</span></div> <bean:message key="knowledgepro.admission.currentSeatNo.lable"/>:</div>
									</td>
									<td width="25%" colspan="2" class="row-even">
									<html:text property="currentSeatNo" name="examCenterForm" maxlength="50" styleId="currentSeatNoId"></html:text>
									</td>
									<td width="25%" height="25" class="row-odd">
									<div align="right"><div id="hideMandatory3"><span class="Mandatory">*</span></div><bean:message key="knowledgepro.admission.seatNoPrefix.lable"/>:</div>
									</td>
									<td width="25%" colspan="2" class="row-even">
									<html:text property="seatNoPrefix" name="examCenterForm" maxlength="50" styleId="seatNoPrefixId"></html:text>
									</td>
								</tr>
								<tr> 
									<td width="25%" height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span> <bean:message key="knowledgepro.admission.address1.lable"/>:</div>
									</td>
									<td width="25%" colspan="2" class="row-even">
									<html:text property="address1" name="examCenterForm" maxlength="50" styleId="address1Id"></html:text>
									</td>
									<td width="25%" height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span> <bean:message key="knowledgepro.admission.address2.lable"/>:</div>
									</td>
									<td width="25%" colspan="2" class="row-even">
									<html:text property="address2" name="examCenterForm" maxlength="50" styleId="address2Id"></html:text>
									</td>
								</tr>
								<tr> 
									<td width="25%" height="25" class="row-odd">
									<div align="right"><bean:message key="knowledgepro.admission.address3.lable"/>:</div>
									</td>
									<td width="25%" colspan="2" class="row-even">
									<html:text property="address3" name="examCenterForm" maxlength="50" styleId="address3Id"></html:text>
									</td>
									<td width="25%" height="25" class="row-odd">
									<div align="right"><bean:message key="knowledgepro.admission.address4.lable"/>:</div>
									</td>
									<td width="25%" colspan="2" class="row-even">
									<html:text property="address4" name="examCenterForm" maxlength="50" styleId="address4Id"></html:text>
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
									<c:choose>
							<c:when test="${centerOperation == 'Edit'}">
							<td width="45%" height="35">
							<div align="right">
									<html:submit property="" styleClass="formbutton" value="Update"
										styleId="submitbutton">
									</html:submit>
								</div>
							</td>
							<td width="2%"></td>
							<td width="53%"><html:cancel value="Reset" styleClass="formbutton" ></html:cancel></td>
							</c:when>
							<c:otherwise>
								<td width="45%" height="35">
							<div align="right">
									<html:submit property="" styleClass="formbutton" value="submit"
										styleId="submitbutton">
									</html:submit>
								</div>
							</td>
							<td width="2%"></td>
							<td width="53%"><html:button property="" styleClass="formbutton" value="Reset"
							onclick="resetFormFields()"></html:button></td>
							</c:otherwise>
							</c:choose>
						</tr>
					</table>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>	
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
											<td width="7%" height="25" class="row-odd">
											<div align="center"><bean:message key="knowledgepro.slno" /></div>
											</td>
											<td width="18%" class="row-odd">
											<div align="center"><bean:message key="knowledgepro.admin.program" /></div>
											</td>
											<td width="18%" class="row-odd">
											<div align="center"><bean:message key="knowledgepro.admission.center.lable" /></div>
											</td>
											<td width="10%" class="row-odd">
											<div align="center"><bean:message key="knowledgepro.admission.seatNoFrom.lable" /></div>
											</td>
											<td width="10%" class="row-odd">
											<div align="center"><bean:message key="knowledgepro.admission.seatNoTo.lable" /></div>
											</td>
											<td width="18%" class="row-odd">
											<div align="center"><bean:message key="knowledgepro.admission.seatNoPrefix.lable" /></div>
											</td>
											<td width="9%" class="row-odd">
											<div align="center"><bean:message key="knowledgepro.edit" /></div>
											</td>
											<td width="8%" class="row-odd">
											<div align="center"><bean:message key="knowledgepro.delete" /></div>
											</td>
										</tr>
										<logic:notEmpty name="examCenterForm" property="examCenterList">
										<logic:iterate id="eList" name="examCenterForm" property="examCenterList"
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
											<td height="25">
											<div align="center"><c:out value="${count + 1}" /></div>
											</td>
											<td align="center"><bean:write name="eList"
												property="programName" /></td>
											<td align="center"><bean:write name="eList"
												property="center" /></td>
											<td align="center"><bean:write name="eList"
												property="seatNoFrom" /></td>
											<td align="center"><bean:write name="eList"
												property="seatNoTo" /></td>
											<td align="center"><bean:write name="eList"
												property="seatNoPrefix" /></td>
											<td height="25" align="center">
											<div align="center"><img src="images/edit_icon.gif"
													width="16" height="18"
													onclick="editExamCenter('<bean:write name="eList" property="id" />')" /></div>
											</td>
											<td height="25">
											<div align="center"><img src="images/delete_icon.gif"
													width="16" height="16"
													onclick="deleteExamCenter('<bean:write name="eList" property="id" />')" /></div>
											</td>
											</tr>	
										</logic:iterate>
										</logic:notEmpty>
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
<script type="text/javascript">
			var temp=document.getElementById("isSeatNoValidationRequired").value;
			if(temp=="true"){
					document.getElementById("hideMandatory").style.display="block";
					document.getElementById("hideMandatory1").style.display="block";
					document.getElementById("hideMandatory2").style.display="block";
					document.getElementById("hideMandatory3").style.display="block";
						}else
							{
							document.getElementById("hideMandatory").style.display="none";
							document.getElementById("hideMandatory1").style.display="none";
							document.getElementById("hideMandatory2").style.display="none";
							document.getElementById("hideMandatory3").style.display="none";
								}
</script>
		    
