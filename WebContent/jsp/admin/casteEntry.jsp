<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<script type="text/javascript">
function editCaste(casteId, casteName,isFeeExcemption,religionid) {
	document.getElementById("method").value = "updateCaste";
	document.getElementById("casteId").value = casteId;
	document.getElementById("religionId").value = religionid;
	document.getElementById("orgreligionId").value = religionid;
	document.getElementById("origCasteId").value = casteId;
	document.getElementById("casteName").value = casteName;
	document.getElementById("origCasteName").value = casteName;
	if(isFeeExcemption == "yes") {
            document.getElementById("feeExemption_1").checked = true;
            document.getElementById("origisFeeExcemption").value="yes";
	}	
	if(isFeeExcemption == "no") {
        document.getElementById("feeExemption_2").checked = true;
        document.getElementById("origisFeeExcemption").value="no";
	}
	document.getElementById("submitbutton").value="Update";
}
function deleteCaste(casteId,casteName) {
	deleteConfirm = confirm("Are you sure you want to delete this entry?")
	if (deleteConfirm) {
		document.location.href = "CasteEntry.do?method=deleteCaste&casteId="
				+ casteId+"&casteName="+casteName;
	}
}
function reActivate(){
	document.location.href = "CasteEntry.do?method=reActivateCaste";
}
function resetFormFields(){	
	document.getElementById("casteName").value = null;
	document.getElementById("religionId").value ="";
	document.getElementById("feeExemption_1").checked=false;
	document.getElementById("feeExemption_2").checked=false;
	resetErrMsgs();
	if (document.getElementById("method").value == "updateCaste") {
		var isFeeExcemption=document.getElementById("origisFeeExcemption").value;
		if(isFeeExcemption == "yes") {
            document.getElementById("feeExemption_1").checked = true;
		}	
		if(isFeeExcemption == "no") {
      	  document.getElementById("feeExemption_2").checked = true;
		}
		document.getElementById("casteId").value = document.getElementById("origCasteId").value;
		document.getElementById("casteName").value = document.getElementById("origCasteName").value;
	}
}
</script>
<html:form action="/CasteEntry">	
		<c:choose>
		<c:when test="${operation == 'edit'}">
		<html:hidden property="method" styleId="method" value="updateCaste" />
		</c:when>
		<c:otherwise>
		<html:hidden property="method" styleId="method" value="addCaste" />
		</c:otherwise>
		</c:choose>
	<html:hidden property="casteId" styleId="casteId" />
	<html:hidden property="orgreligionId" styleId="orgreligionId" />
	<html:hidden property="formName" value="casteForm" />
	<html:hidden property="pageType" value="1" />
	<html:hidden property="origCasteId"	styleId="origCasteId" />
	<html:hidden property="origCasteName" styleId="origCasteName" />
	<html:hidden property="origisFeeExcemption"	styleId="origisFeeExcemption" />
	<table width="100%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message key="knowledgepro.admin"/>
			<span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.admin.caste"/>
			 &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td valign="top">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> <bean:message key="knowledgepro.admin.caste"/> Entry</strong></td>

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
							<div align="right"><span class="Mandatory">*</span>Religion:</div>
							</td>
							<td width="16%" height="25" class="row-even"><label>
							<html:select property="religionId" styleClass="combo"
								styleId="religionId">
								<html:option value="">
									<bean:message key="knowledgepro.admin.select" />
								</html:option>
								<html:optionsCollection name="religionList" label="religionName"
									value="religionId" />
							</html:select> </label> <span class="star"></span></td>
							
							
							
							<td width="16%" height="25" class="row-odd">
							<div align="right"><span class="Mandatory">*</span><bean:message
								key="knowledgepro.admin.castename" />:</div>
							</td>
							<td width="16%" height="25" class="row-even">
							<html:text
								property="casteName" styleId="casteName" styleClass="TextBox"
								size="20" maxlength="30" /><span class="star"></span></td>
							<td width="22%" class="row-odd">
							<div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.admin.caste.FeeExemption"/>?</div>
							</td>
							<td width="16%" class="row-even">
							 <input type="radio" name="feeExemption" id="feeExemption_1" value="yes"/> <bean:message key="knowledgepro.yes"/>
                   			 <input type="radio" name="feeExemption" id="feeExemption_2" value="no"/> <bean:message key="knowledgepro.no"/>
							<script type="text/javascript">
								var feeExemption = "<bean:write name='casteForm' property='feeExemption'/>";
								if(feeExemption == "yes") {
				                        document.getElementById("feeExemption_1").checked = true;
								}	
								if(feeExemption == "no") {
			                        document.getElementById("feeExemption_2").checked = true;
							}
							</script>
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
											<td width="29%" class="row-odd">
											<div align="center"><bean:message
											key="knowledgepro.admin.castename" /></div>
											</td>
											<td width="29%" class="row-odd">
											<div align="center">Religion</div>
											</td>
											<td width="18%" class="row-odd">
											<div align="center"><bean:message
											key="knowledgepro.admin.caste.FeeExemption" /></div>
											</td>
											<td width="9%" class="row-odd">
											<div align="center"><bean:message key="knowledgepro.edit" /></div>
											</td>
											<td width="8%" class="row-odd">
											<div align="center"><bean:message
										key="knowledgepro.delete" /></div>
											</td>
										</tr>
										<logic:iterate id="cList" property="casteList" name="casteForm"
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
												property="casteName" /></td>
											<td align="center"><bean:write name="cList"
												property="religionto.religionName" /></td>	
												
											<td align="center"><bean:write name="cList"
												property="isFeeExcemption" /></td>
											<td height="25" align="center">
											<div align="center"><img src="images/edit_icon.gif"
													width="16" height="18"
													onclick="editCaste('<bean:write name="cList" property="casteId" />','<bean:write name="cList" property="casteName" />','<bean:write name="cList" property="isFeeExcemption" />','<bean:write name="cList" property="religionto.religionId" />')" /></div>
											</td>
											<td height="25">
											<div align="center"><img src="images/delete_icon.gif"
													width="16" height="16"
													onclick="deleteCaste('<bean:write name="cList" property="casteId" />','<bean:write name="cList" property="casteName" />')" /></div>
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
