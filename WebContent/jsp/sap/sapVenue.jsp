<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>

<script type="text/javascript">

function addSapVenue() {
	document.getElementById("method").value = "addSapVenue";
	document.sapVenueForm.submit();
}
function editSapVenue(id) {
	document.getElementById("workLocId").selectedIndex = "workLocId";
	document.location.href = "SapVenue.do?method=editSapVenue&id="+ id;
}
function updateSapVenue() {
	document.getElementById("method").value = "updateSapVenue";
	document.sapVenueForm.submit();
}
function reActivate() {
	var name = document.getElementById("venueName").value;
	document.location.href = "SapVenue.do?method=reActivateSapVenue&venueName="+ name;
}
function deleteSapVenue(id){
	deleteConfirm = confirm("Are you sure you want to delete this entry?");
	if(deleteConfirm){
		document.location.href = "SapVenue.do?method=deleteSapVenue&id="+ id;
	}
}
function resetMessages(){
	document.getElementById("capacity").value = "";
	document.getElementById("venueName").value = "";
	document.getElementById("workLocId").selectedIndex = 0;
	resetErrMsgs();
}

</script>

<html:form action="/SapVenue" method="post">
<html:hidden property="formName" value="sapVenueForm" />
<html:hidden property="pageType" value="1" />
<html:hidden property="id" />
<c:choose>
		<c:when test="${venueOperation == 'edit'}">
			<html:hidden property="method" styleId="method" value="editSapVenue" />
		</c:when>
		<c:otherwise>
			<html:hidden property="method" styleId="method" value="addSapVenue" />
		</c:otherwise>
</c:choose>
<table width="100%" border="0">
		<tr>
		<td><span class="Bredcrumbs"><bean:message key="knowledgepro.sap" /> <span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.sap.venue" />&gt;&gt;</span></span></td>
		</tr>
		<tr>
		<td valign="top">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> <bean:message key="knowledgepro.sap.venue" /></strong></td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
			</tr>
			<tr>
					<td height="30" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<div align="right"><span class='MandatoryMark'> <bean:message key="knowledgepro.mandatoryfields" /></span></div>
					<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
					<FONT color="green"><html:messages id="msg" property="messages" message="true">
						<c:out value="${msg}" escapeXml="false"></c:out>
						<br>
					</html:messages> </FONT></div>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
			</tr>
			<tr>
				<td height="20" valign="top" background="images/Tright_03_03.gif">
					<div id="errorMessage"></div>
					</td>
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
									<td width="25%" height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>&nbsp;
									<bean:message key="employee.info.job.workLocationName" />:</div>
									</td>
									<td width="25%" class="row-even">
									<html:select property="workLocId" styleClass="comboLarge" styleId="workLocId">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<html:optionsCollection name="locationList" label="name" value="id" />
									</html:select>
									</td>
									<td width="25%" height="25" class="row-odd"></td>
									<td width="25%" height="25" class="row-even"></td>
							</tr>
							<tr>
									<td width="25%" height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>&nbsp;
									<bean:message key="knowledgepro.interview.Venue" /></div>
									</td>
									<td width="25%" class="row-even"><span class="star">
									<input type="hidden" name="vn" id="vn"
										value='<bean:write name="sapVenueForm" property="venueName"/>' />
									<html:text property="venueName" styleClass="TextBox" styleId="venueName"
										size="30" maxlength="100"></html:text> </span></td>
										
									<td width="25%" height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>&nbsp;
									<bean:message key="knowledgepro.exam.roomCapacity" />:</div>
									</td>
									<td width="25%" class="row-even"><span class="star">
									<input type="hidden" name="rc" id="rc"
										value='<bean:write name="sapVenueForm" property="capacity"/>' />
									<html:text property="capacity" styleClass="TextBox" styleId="capacity"
										size="10" maxlength="100" onkeypress="return isNumberKey(event)"></html:text> </span></td>
									
							</tr>
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
					<td height="20" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="49%" height="35">
							<div align="right"><c:choose>
								<c:when test="${venueOperation == 'edit'}">
									<html:button property="" styleClass="formbutton" value="Update"
										onclick="updateSapVenue()"></html:button>
								</c:when>
								<c:otherwise>
									<html:button property="" styleClass="formbutton" value="Submit"
										onclick="addSapVenue()"></html:button>
								</c:otherwise>
							</c:choose></div>
							</td>
							<td width="2%"></td>
							<td width="53%"><c:choose>
								<c:when test="${venueOperation == 'edit'}">
									<html:cancel value="Reset" styleClass="formbutton"></html:cancel>
								</c:when>
								<c:otherwise>
									<html:button property="" styleClass="formbutton" value="Reset"
										onclick="resetMessages()"></html:button>
								</c:otherwise>
							</c:choose>
							</td>
						</tr>
					</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td height="20" valign="top" background="images/Tright_03_03.gif"></td>
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
									<td height="25" class="row-odd">
									<div align="center"><bean:message key="knowledgepro.slno" /></div>
									</td>
									<td align="center" class="row-odd"><bean:message key="employee.info.job.workLocationName" /></td>
									<td align="center" class="row-odd">Venue</td>
									<td align="center" class="row-odd"><bean:message key="knowledgepro.exam.roomCapacity" /></td>
									<td class="row-odd"><div align="center"><bean:message key="knowledgepro.edit" />
									</div>
									</td>
									<td class="row-odd"><div align="center"><bean:message key="knowledgepro.delete" /></div>
									</td>
								</tr>
								<c:set var="temp" value="0" />
								<logic:notEmpty name="sapVenueForm"	property="venueList">
									<logic:iterate id="ven" name="sapVenueForm"
										property="venueList" indexId="count">
										<c:choose>
											<c:when test="${temp == 0}">
											<tr>
											<td width="5%" height="25" class="row-even">
											<div align="center"><c:out value="${count + 1}" /></div></td>
											<td align="left" width="25%" class="row-even">
											<bean:write	name="ven" property="workLoc" /></td>
											<td align="left" width="25%" class="row-even">
											<bean:write	name="ven" property="venueName" /></td>
											<td align="center" width="25%" class="row-even">
											<bean:write name="ven" property="capacity" /></td>
											<td align="center" width="5%" height="25" class="row-even">
											<div align="center"><img src="images/edit_icon.gif"
												width="16" height="18" style="cursor: pointer"
												onclick="editSapVenue('<bean:write name="ven" property="id" />')" /></div>
											</td>
											<td width="5%" height="25" class="row-even">
											<div align="center"><img src="images/delete_icon.gif"
												width="16" height="16" style="cursor: pointer"
												onclick="deleteSapVenue('<bean:write name="ven" property="id"/>')"></div>
											</td>
										</tr>
										<c:set var="temp" value="1" />
										</c:when>
											<c:otherwise>
												<tr>
											<td width="5%" height="25" class="row-white">
											<div align="center"><c:out value="${count + 1}" /></div></td>
											<td align="left" width="25%" class="row-white">
											<bean:write	name="ven" property="workLoc" /></td>
											<td align="left" width="25%" class="row-white">
											<bean:write	name="ven" property="venueName" /></td>
											<td align="center" width="25%" class="row-white">
											<bean:write name="ven" property="capacity" /></td>
											<td align="center" width="5%" height="25" class="row-white">
											<div align="center"><img src="images/edit_icon.gif"
												width="16" height="18" style="cursor: pointer"
												onclick="editSapVenue('<bean:write name="ven" property="id" />')" /></div>
											</td>
											<td width="5%" height="25" class="row-white">
											<div align="center"><img src="images/delete_icon.gif"
												width="16" height="16" style="cursor: pointer"
												onclick="deleteSapVenue('<bean:write name="ven" property="id"/>')"></div>
											</td>
										</tr>
										<c:set var="temp" value="0" />
											</c:otherwise>
										</c:choose>
									</logic:iterate>
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
					<td height="20" valign="top" background="images/Tright_03_03.gif"></td>
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
								