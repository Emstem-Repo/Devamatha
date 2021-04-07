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

function deleteDepartmentNameEntry(id,name) {
	deleteConfirm = confirm("Are you sure you want to delete this entry?");
	if (deleteConfirm) {
		document.location.href = "departmentNameEntry.do?method=deleteDepartmentNameEntry&id="
				+ id+"&departmentName="+name;
	}
}

function editDepartmentNameEntry(id) {
	document.getElementById("id").value=id;
	document.getElementById("dupId").value=id;
	document.departmentNameEntryForm.method.value="editDepartmentNameEntry";
	document.departmentNameEntryForm.submit();
	}
function resetFormFields(){	
	document.getElementById("name").value = null;
	
}
function reActivate(){
	var id = document.getElementById("dupId").value;
	document.location.href = "departmentNameEntry.do?method=reActivateDepartmentNameEntry&dupId="+id;
}


</script>
<html:form action="/serviceLearningMarksEntry">	

	<html:hidden property="formName" value="serviceLearningMarksEntryForm" />
	<html:hidden property="method" styleId="method" value="searchServiceLearningActivity" />
    
	
	<c:choose>
		<c:when test="${department == 'edit'}">
		<html:hidden property="method" styleId="method" value="updateDepartmentNameEntry" />
		</c:when>
		<c:otherwise>
		<html:hidden property="method" styleId="method" value="addHostelNameEntry" />
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
						class="boxheader"> Service Learning Marks Entry</strong></td>

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
							<td width="16%" height="25" class="row-odd">
							<div align="right"><span class="Mandatory">*</span>Date:</div>
							</td>
							<td width="16%" height="25" class="row-even">
						 	<html:text
								property="startDate" styleId="startDate" styleClass="TextBox"
								size="20" maxlength="30" /><span class="star"></span>
								<script language="JavaScript">
												new tcal( {
													// form name
													'formname' :'serviceLearningMarksEntryForm',
													// input name
													'controlname' :'startDate'
												});
											</script></td>
								<td width="16%" height="25" class="row-odd">
							<div align="right"><span class="Mandatory">*</span>Programme Name:</div>
							</td>
							<td width="16%" height="25" class="row-even"> <label>
							<html:select property="programId" styleId="programId" onchange="getPrograme(this.value)">
											<html:option value=""><bean:message key="knowledgepro.select"/></html:option>
												<logic:notEmpty property="programmeEntryToList" name="serviceLearningMarksEntryForm">
											  		<html:optionsCollection property="programmeEntryToList" label="programmeName" value="id" />
											   </logic:notEmpty>
										 </html:select> 
						
							 </label> <span class="star"></span>
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
							<td width="45%" height="35">
							<div align="right">
							<c:choose>
							<c:when test="${operation == 'edit'}">
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
