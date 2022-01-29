<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<script type="text/javascript">

function deleteProgrammeNameEntry(id,name) {
	deleteConfirm = confirm("Are you sure you want to delete this entry?");
	if (deleteConfirm) {
		document.location.href = "programmeEntry.do?method=deleteProgrammeNameEntry&id="
				+ id+"&programmeName="+name;
	}
}

function editProgrmmeNameEntry(id) {
	document.getElementById("id").value=id;
	document.getElementById("dupId").value=id;
	document.programmeEntryForm.method.value="editProgrmmeNameEntry";
	document.programmeEntryForm.submit();
	}
function resetFormFields(){	
	document.getElementById("programmeName").value = null;
	document.getElementById("programmeCode").value = null;
	document.getElementById("startDate").value = null;
	document.getElementById("endDate").value = null;
	document.getElementById("inchargeName").value = null;
	document.getElementById("deptId").value = "";
	document.getElementById("extraCreditActivityType").value="";
	document.getElementById("submitbutton").value="Submit";
	resetErrMsgs();
	
}
function reActivate(){
	var id = document.getElementById("dupId").value;
	document.location.href = "programmeEntry.do?method=reActivateProgrammeNameEntry&dupId="+id;
}
function hidefield(id){
	document.getElementById("extraCreditActivityType").selected=true;
	var activityName=document.getElementById("extraCreditActivityType").value;
	if(activityName==1){
		document.location.href = "programmeEntry.do?method=initGetProgrameEntry&extraCreditActivityType="+id;
	}else{
		document.location.href = "programmeEntry.do?method=workAndInternshipMethod&extraCreditActivityType="+id;
	}
}

</script>
<html:form action="/programmeEntry">	

	<html:hidden property="formName" value="programmeEntryForm" />
    <html:hidden property="pageType" value="1" />
	<html:hidden property="id" styleId="id" />
	<html:hidden property="dupId" styleId="dupId"/>
	
	<c:choose>
		<c:when test="${department == 'edit'}">
		<html:hidden property="method" styleId="method" value="updateProgrammeNameEntry" />
		</c:when>
		<c:otherwise>
		<html:hidden property="method" styleId="method" value="addProgrammeNameEntry" />
		</c:otherwise>
		</c:choose>
		
	
	<table width="100%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message key="knowledgepro.admin"/>
			<span class="Bredcrumbs">&gt;&gt;
		Service Learning
			 &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td valign="top">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> Programme Name Entry</strong></td>

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
								<td width="16%" height="25" class="row-odd" ><div align="right"><span class="Mandatory">*</span>Activity Name:</div></td>
			                    <td width="16%" height="25" class="row-even" ><span class="star">
				                     <html:select property="extraCreditActivityType" styleId="extraCreditActivityType" styleClass="combo" onchange="hidefield(this.value)">
				                     <html:option value=""><bean:message key="knowledgepro.select"/></html:option>
				                     <html:optionsCollection property="activityTypeTos" label="activityName" value="activityTypeId"/>
				                     </html:select></span>
			                     </td>
							
							</tr>
							<tr>
							<td width="16%" height="25" class="row-odd">
							<div align="right"><span class="Mandatory">*</span>Programme Name:</div>
							</td>
							<td width="16%" height="25" class="row-even">
							<html:text
								property="programmeName" styleId="programmeName" styleClass="TextBox"
								size="20" maxlength="30" /><span class="star"></span></td>
								
								<td width="16%" height="25" class="row-odd">
							<div align="right"><span class="Mandatory">*</span>Programme Code:</div>
							</td>
							<td width="16%" height="25" class="row-even">
							<html:text
								property="programmeCode" styleId="programmeCode" styleClass="TextBox"
								size="20" maxlength="30" /><span class="star"></span></td>
							</tr>
							<tr>
							<td width="16%" height="25" class="row-odd">
							<div align="right"><span class="Mandatory">*</span>Start Date:</div>
							
							</td>
							<td width="16%" height="25" class="row-even">
							<html:text
								property="startDate" styleId="startDate" styleClass="TextBox" 
								size="20" maxlength="30" /><span class="star"></span> <script language="JavaScript">
												new tcal( {
													// form name
													'formname' :'programmeEntryForm',
													// input name
													'controlname' :'startDate'
												});
											</script>
								</td>
							<td width="16%" height="25" class="row-odd">
							<div align="right"><span class="Mandatory">*</span>End Date:</div>
							 
							</td>
							<td width="16%" height="25" class="row-even">
							<html:text
								property="endDate" styleId="endDate" styleClass="TextBox"
								size="20" maxlength="30" /><span class="star"></span>
								<script language="JavaScript">
												new tcal( {
													// form name
													'formname' :'programmeEntryForm',
													// input name
													'controlname' :'endDate'
												});
											</script>
								</td>
								</tr><tr>
								<td width="16%" height="25" class="row-odd">
							<div align="right"><span class="Mandatory">*</span>Incharge Name:</div>
							</td>
							<td width="16%" height="25" class="row-even">
							<html:text
								property="inchargeName" styleId="inchargeName" styleClass="TextBox"
								size="20" maxlength="30" /><span class="star"></span></td>
							
							
							
							
								<td width="16%" height="25" class="row-odd">
							<div align="right"><span class="Mandatory">*</span>Department/Club:</div>
							</td>
							<td width="16%" height="25" class="row-even"><label>
							<html:select property="deptId" styleClass="combo"
								styleId="deptId">
								<html:option value="">
									<bean:message key="knowledgepro.admin.select" />
								</html:option>
								<html:optionsCollection name="departmentNameEntryToList" label="departmentName"
									value="id" />
							</html:select> </label> <span class="star"></span></td>

							
							
							</tr>
										
							
							<tr>
							
							     <td width="50%" height="25" align="right" class="row-odd" colspan="2"><div align="right">
							     <span class="Mandatory">*</span>&nbsp;Max Hours(24 Hour Format) :
							     </div></td>
							     <td width="50%" class="row-even" align="left" height="25" colspan="2"><div align="left">
							       <html:text name="programmeEntryForm" property="startHours" styleClass="Timings" styleId="smtime" size="2"
							        maxlength="2" onfocus="clearField(this)" onblur="checkForEmpty(this), checkNumber(this)" onkeypress="return isNumberKey(event)"/> :
							       <html:text name="programmeEntryForm" property="startMins" styleClass="Timings" styleId="enmtime" size="2" maxlength="2" onfocus="clearField(this)" 
							       onblur="checkForEmpty(this), checkNumber(this)" onkeypress="return isNumberKey(event)"/>
							     </div></td>
     
                                     
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
							<c:choose>
							<c:when test="${department == 'edit'}">
									<html:submit property="" styleClass="formbutton" value="Update"
										styleId="submitbutton">
									</html:submit>
							</c:when>
							<c:otherwise>
								<html:submit property="" styleClass="formbutton" value="Submit"
										styleId="submitbutton">
									</html:submit>
							</c:otherwise>
							</c:choose>
								</div>
							</td>
							<td width="2%"></td>
							<td width="53%">
							<html:button property=""
							styleClass="formbutton" value="Reset"
							onclick="resetFormFields()"></html:button>
							</td>
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
											<td width="10%" class="row-odd">
											<div align="center">Programme Name</div>
											</td>
											<td width="10%" class="row-odd">
											<div align="center">Programme Code</div>
											</td>
											<td width="10%" class="row-odd">
											<div align="center">Activity Name</div>
											</td>
											<td width="10%" class="row-odd">
											<div align="center">Start Date</div>
											</td>
											<td width="10%" class="row-odd">
											<div align="center">End Date</div>
											</td>
											<td width="10%" class="row-odd">
											<div align="center">Incharge</div>
											</td>
											<td width="10%" class="row-odd">
											<div align="center">Department/Club</div>
											</td>
											<td width="10%" class="row-odd">
											<div align="center">Max Hrs</div>
											</td>
											
											<td width="9%" class="row-odd">
											<div align="center"><bean:message key="knowledgepro.edit" /></div>
											</td>
											<td width="8%" class="row-odd">
											<div align="center"><bean:message
										key="knowledgepro.delete" /></div>
											</td>
										</tr>
									<logic:iterate id="cList" property="programmeEntryToList" name="programmeEntryForm"
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
											<td align="center"><bean:write name="cList"
												property="programmeName" /></td>
												<td align="center"><bean:write name="cList"
												property="programmeCode" /></td>
												<td align="center"><bean:write name="cList"
												property="activityName" /></td>
												<td align="center"><bean:write name="cList"
												property="startDate" /></td>
												<td align="center"><bean:write name="cList"
												property="endDate" /></td>
												<td align="center"><bean:write name="cList"
												property="inchargeName" /></td>
												<td align="center"><bean:write name="cList"
												property="deptName" /></td>	
												<td align="center"><bean:write name="cList"
												property="maxHrs" /></td>
												
											
											<td height="25" align="center">
											<div align="center"><img src="images/edit_icon.gif"
													width="16" height="18"
													onclick="editProgrmmeNameEntry('<bean:write name="cList" property="id" />')" /></div>
											</td>
											<td height="25">
											<div align="center"><img src="images/delete_icon.gif"
													width="16" height="16"
													onclick="deleteProgrammeNameEntry('<bean:write name="cList" property="id" />','<bean:write name="cList" property="programmeName" />')" /></div>
											</td>
											</tr>
										</logic:iterate>
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
