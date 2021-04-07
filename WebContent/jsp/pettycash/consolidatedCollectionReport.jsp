<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<script type="text/javascript">
function getCode(accCode){
	document.getElementById("accountCode").value=accCode;
}
function itemSearch(searchValue){
	var sda = document.getElementById("accountId");
	var len = sda.length;
	var searchValueLen = searchValue.length;
	for(var m =0; m<len; m++){
		sda.options[m].selected = false;		
	}
	for(var j=0; j<len; j++)
	{
		for(var i=0; i<len; i++){
		if(((sda.options[i].text).substring(0, searchValueLen).toUpperCase())  == (searchValue.toUpperCase())){
			sda.options[i].selected = true;
			document.getElementById("accountCode").value=sda.options[i].value;
			break;
		}
		}
	}
}

function showDescription() {
	document.getElementById("dynaTable").style.display="block";
}
function hideDescription(){
	document.getElementById("dynaTable").style.display="none";
	
}
function hideStudentGroup() {
	document.getElementById("stPerGroup").style.display = "none";
	document.getElementById("otherName").style.display = "none";
}
function hideGroup() {
	document.getElementById("groupCode").style.display = "none";
	document.getElementById("groupId").style.display = "none";
}
function resetFormFields(){	
	resetErrMsgs();
	document.getElementById("programType").selectedIndex = 0;
	document.getElementById("startDate").value = "";
	document.getElementById("endDate").value = "";
	document.getElementById("account_1").checked=false;
	document.getElementById("account_2").checked=false;
	document.getElementById("currentUser").checked=false;
	document.getElementById("allUsers").checked=false;
	document.getElementById("otherUser").checked=false;
	document.getElementById("dynaTable").style.display="none";
	document.getElementById("stPerGroup").style.display = "none";
	document.getElementById("otherName").style.display = "none";
	document.getElementById("otherName").value=""
	document.getElementById("groupCode").style.display = "none";
	document.getElementById("groupId").style.display = "none";	
	document.getElementById("groupCode").value="";
	document.getElementById("accountCode").value="";
}
</script>
<html:form action="/consolidatedCollectionList">	
		<html:hidden property="method" styleId="method" value="searchStudentList" />
		<html:hidden property="formName" value="consolidatedCollectionReportForm"/>
		<html:hidden property="pageType" value="1" />
	<table width="100%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message key="knowledgepro.petticash"/>
			<span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.pettycash.consolidated.display"/>
			 &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td valign="top">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> <bean:message key="knowledgepro.pettycash.consolidated.display"/></strong></td>

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
									<div align="right">&nbsp;<bean:message
										key="knowledgepro.admin.programtype" />:</div>
									</td>
									<td width="25%" height="25" class="row-even"><label>
									<html:select property="programTypeId" styleId="programType" styleClass="combo">
										<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
										<html:optionsCollection property="programTypeList" label="programTypeName" value="programTypeId" />
									</html:select> 
									</label></td>
									<td width="25%" class="row-odd"></td>
									<td width="25%" class="row-even"></td>
							</tr>
							<tr>
								<td height="25" class="row-odd" width="25%">
								<html:radio name="consolidatedCollectionReportForm" property="account" value="true" styleId="account_1" onclick="showDescription(),hideGroup()">
								<bean:message key="knowledgepro.petticash.collectionLedger.AccountCode"/>
								</html:radio>
								</td>
								<td class="row-even" width="25%">
								<html:radio name="consolidatedCollectionReportForm" property="account" value="false" styleId="account_2" onclick="hideDescription(),showGroup()">
								<bean:message key="knowledgepro.petticash.collectionLedger.AccountGroup"/>
								</html:radio>
								</td>
								 <td height="25" class="row-odd" width="25%">
								 <div id="groupId"><span class="Mandatory">*</span>Account Group Code:</div>
								 </td>
						         <td height="25" class="row-even" width="25%">
						         <html:text name="consolidatedCollectionReportForm" property="groupCode"
									styleClass="body" styleId="groupCode" size="20" />
						         </td>
							</tr>
							<tr>
								<td colspan="4">
									<table width="100%" border="0" align="left" cellpadding="0" cellspacing="0" id="dynaTable">
						               <tr >
						               <td height="25" class="row-odd" width="25%">Account Number</td>
						                  <td class="row-white" width="25%">
						                  <nested:select property="accountNumber" styleId="accountId" styleClass="body" size="4" style="width:300px;" multiple="multiple">
						                  <logic:notEmpty name="consolidatedCollectionReportForm" property="accNolist">
											<nested:optionsCollection name="consolidatedCollectionReportForm" property="accNolist" label="accountNo" value="accountNo" styleClass="comboBig"/>
											</logic:notEmpty>
											</nested:select>
						                  </td>
						                  <td height="25" class="row-odd" width="25%"></td>
						                  <td height="25" class="row-even" width="25%"></td>
						                </tr>
									</table>
								</td>
							</tr>
							<tr>
								<td height="25" class="row-odd" width="20%">
								<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message key="knowledgepro.feepays.startdate" />:</div>
								</td>
								<td class="row-even" width="30%">
								<html:text name="consolidatedCollectionReportForm" property="startDate" styleId="startDate" size="10" maxlength="16"/>
									<script
									language="JavaScript">
									new tcal( {
										// form name
										'formname' :'consolidatedCollectionReportForm',
										// input name
										'controlname' :'startDate'
									});
									</script>
								</td>
								<td height="25" class="row-odd" width="20%">
								<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message key="knowledgepro.feepays.enddate" />:</div>
								</td>
								<td height="25" class="row-even" width="30%">
								<html:text name="consolidatedCollectionReportForm" property="endDate" styleId="endDate" size="11" maxlength="11"></html:text>
								<script language="JavaScript">
									new tcal( {
										// form name
										'formname' :'consolidatedCollectionReportForm',
										// input name
										'controlname' :'endDate'
									});
								</script>
								</td>
							</tr>
							<tr>
									<td width="20%" height="25" class="row-odd"><html:radio
									property="userType" value="false" styleId="currentUser"
									onclick="hideStudentGroup()">
									<bean:message key="knowledgepro.petticash.currentUser" />
									</html:radio></td>
									<td width="20%" height="25" class="row-even"><html:radio
									property="userType" value="true" styleId="allUsers"
									onclick="hideStudentGroup()">
									<bean:message key="knowledgepro.petticash.allUsers" />
									</html:radio></td>
									<td width="20%" height="25" class="row-odd"><html:radio
									property="userType" value="otherUser" styleId="otherUser"
									onclick="showStudentGroup()">
									<bean:message key="knowledgepro.petticash.otherUser" />
									</html:radio></td>
									<td class="row-even">
									<div id="stPerGroup"><span class="Mandatory">*</span>user
									Name:</div>
									<html:text name="consolidatedCollectionReportForm" property="otherName"
									styleClass="body" styleId="otherName" size="3" /></td>
									<tr>
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
									<html:submit property="" styleClass="formbutton" value="Submit"
										styleId="submitbutton">
									</html:submit>
								</div>
							</td>
							<td width="2%"></td>
							<td width="53%"><html:button property=""
							styleClass="formbutton" value="Reset"
							onclick="resetFormFields()"></html:button></td>
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
<script type="text/javascript">
	document.getElementById("dynaTable").style.display="none";	
	if(document.getElementById("account_1").checked){
		document.getElementById("dynaTable").style.display="block";
	}
	function showStudentGroup() {
		document.getElementById("stPerGroup").style.display = "block";
		document.getElementById("otherName").style.display = "block";
		}

		var stPerGroupp = document.getElementById("otherUser").checked;
		if(stPerGroupp == true){ 
		document.getElementById("otherName").style.display = "block"; 
		}else{
		document.getElementById("stPerGroup").style.display = "none";
		document.getElementById("otherName").style.display = "none";
		}
		function showGroup() {
			document.getElementById("groupCode").style.display = "block";
			document.getElementById("groupId").style.display = "block";
			}

			var stGroup = document.getElementById("account_2").checked;
			if(stGroup == true){ 
				document.getElementById("groupCode").style.display = "block";
				document.getElementById("groupId").style.display = "block";
			}else{
			document.getElementById("groupCode").style.display = "none";
			document.getElementById("groupId").style.display = "none";

			}	
</script>