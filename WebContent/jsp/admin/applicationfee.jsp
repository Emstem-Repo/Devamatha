<%@ taglib prefix="bean" uri="/WEB-INF/struts-tld/struts-bean.tld"%>
<%@ taglib prefix="logic" uri="/WEB-INF/struts-tld/struts-logic.tld"%>
<%@ taglib prefix="html" uri="/WEB-INF/struts-tld/struts-html.tld"%>
<%@ taglib prefix="c" uri="/WEB-INF/struts-tld/c.tld"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<script type="text/javascript"><!--
function editCaste(appfeeId,year, subReligionId, amount,programType) 
{
	document.getElementById("method").value = "updateCaste";
	document.getElementById("AccademicYear").value = year;
	document.getElementById("category").value = subReligionId;	
	document.getElementById("amount").value=amount;
	document.getElementById("programTypeId").value=programType;
	document.getElementById("orgProgramTypeId").value=programType;
	document.getElementById("orgYear").value = year;
	document.getElementById("orgSubReligionId").value = subReligionId;	
	document.getElementById("orgAmount").value=amount;
	document.getElementById("appfeeId").value=appfeeId;
	document.getElementById("submitbutton").value="Update";
}	
function resetFormFields()
{
	document.getElementById("AccademicYear").value = "";
	document.getElementById("category").value = "";	
	document.getElementById("amount").value=null;
	resetErrMsgs();
	if (document.getElementById("method").value == "updateCaste") 
	{
		document.getElementById("amount").value = document.getElementById("orgAmount").value;
		document.getElementById("category").value = document.getElementById("orgSubReligionId").value;
		document.getElementById("AccademicYear").value = "";
	}
}

function reActivate()
{
	document.location.href="applicationFee.do?method=reactivateAppFee";
}

function deleteCaste(appfeeId)
{
	deleteConfirm=confirm("Are you sure you want to delete this entry?")
	if(deleteConfirm)
	{
		document.location.href="applicationFee.do?method=deleteCaste&appfeeId="+appfeeId;
	}
}
</script>
<html:form action="/applicationFee">
	<c:choose>
		<c:when test="${operation == 'edit'}">
		<html:hidden property="method" styleId="method" value="updateCaste" />
		</c:when>
		<c:otherwise>
		<html:hidden property="method" styleId="method" value="addCaste" />
		</c:otherwise>
		</c:choose>
	<html:hidden property="appfeeId" styleId="appfeeId" />
	<html:hidden property="orgSubReligionId" styleId="orgSubReligionId" />
	<html:hidden property="orgYear" styleId="orgYear" />
	<html:hidden property="orgProgramTypeId" styleId="orgProgramTypeId" />
	<html:hidden property="orgAmount" styleId="orgAmount" />	
	<html:hidden property="formName" value="applfeeForm" />
	<html:hidden property="pageType" value="1" />
	<table width="100%" border="0">
		<tr>
			<td><span class="Bredcrumbs"> <bean:message
				key="knowledgepro.admin" /> <span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.admin.caste" /> &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td valign="top">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> <bean:message
						key="knowledgepro.admin.caste" /> Entry</strong></td>

					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="20" valign="top" background="images/Tright_03_03.gif">
					<td valign="top" class="news">
					<div align="right"><FONT color="red"><span
						class="MandatoryMark"><bean:message
						key="knowledgepro.mandatoryfields" /></span></FONT></div>
					<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
					<FONT color="green"><html:messages id="msg"
						property="messages" message="true">
						<c:out value="${msg}" escapeXml="false"></c:out>
						</br>
					</html:messages></FONT></div>
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
									<div align="right"><span class="Mandatory">*</span>Academic
									Year:</div>
									</td>
									<td width="16%" height="25" class="row-even"><label>
									<html:select property="year" styleClass="combo" name="applfeeForm"
										styleId="AccademicYear">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
											<cms:renderAcademicYear></cms:renderAcademicYear>
										</html:option>
									</html:select> </label><span class="star"></span></td>

									<td width="16%" height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>
									Category:</div>
									</td>
									<td width="16%" height="25" class="row-even"><html:select
										name="applfeeForm" property="subReligionId" styleClass="combo" styleId="category">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<html:optionsCollection name="subrelegionList" label="name"
											value="id" />
									</html:select> <span class="star"></span></td>
									</tr>
									<tr>
									<td width="16%" height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>
									Program Type:</div>
									</td>
									<td width="16%" height="25" class="row-even">
									<html:select property="programTypeId" name="applfeeForm" styleClass="combo" 
									styleId="programTypeId">
									
									<html:option value="">
									<bean:message key="knowledgepro.admin.select" />
									</html:option>
									<html:optionsCollection name="programtypeList" label="programTypeName" 
									value="programTypeId"/>
									</html:select>
									<span class="star"></span></td>
									<td width="22%" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>Amount:</div>
									</td>
									<td width="16%" height="25" class="row-even"><label>
									<html:text property="amount" name="applfeeForm" styleClass="TextBox"
										styleId="amount" size="20" maxlength="30"
										onkeypress='return IsNumeric(event)'>
									</html:text> <span class="star"></span><span id="error" style="color:Red; display:none">input digits(0-9)</span> </td>
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
										key="knowledgepro.admin.accademicyear" /></div>
									</td>
									<td width="29%" class="row-odd">
									<div align="center">Category</div>
									</td>
									<td width="18%" class="row-odd">
									<div align="center">ProgramType</div>
									</td>
									<td width="18%" class="row-odd">
									<div align="center"><bean:message
										key="knowledgepro.admin.Amount" /></div>
									</td>
									<td width="9%" class="row-odd">
									<div align="center"><bean:message key="knowledgepro.edit" /></div>
									</td>
									<td width="8%" class="row-odd">
									<div align="center"><bean:message
										key="knowledgepro.delete" /></div>
									</td>
								</tr>
								<logic:iterate id="cList" property="appfeeList"
									name="applfeeForm" indexId="count">
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
											property="year" /></td>
											
										<td align="center"><bean:write name="cList"
											property="religionSectionTO.name" /></td>
											
										<td align="center"><bean:write name="cList" 
										property="programType.programTypeName"/>
										</td>

										<td align="center"><bean:write name="cList"
											property="amount" /></td>
										<td height="25" align="center">
										<div align="center"><img src="images/edit_icon.gif"
											width="16" height="18"onclick="editCaste('<bean:write name="cList" property="appfeeId" />','<bean:write name="cList" property="year" />','<bean:write name="cList" property="religionSectionTO.id" />','<bean:write name="cList" property="amount" />','<bean:write name="cList" property="programType.programTypeId" />')"
											 /></div>
										</td>
										<td height="25">
										<div align="center"><img src="images/delete_icon.gif"
											width="16" height="16" onclick="deleteCaste('<bean:write name="cList" property="appfeeId"/>' )" /></div>
										</td>
									</tr>
								</logic:iterate>
							</table>
							</td>
							<td width="5" height="30" background="images/right.gif"></td>
						</tr>
						<tr>
							<td height="5"><img src="images/04.gif" width="5" height="5"></td>
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
	</tr>
</html:form>
<script>
 function IsNumeric(e)
 {
	 var unicode=e.charCode? e.charCode : e.keyCode
			    if (unicode!=8)
				{ 
			        if (unicode<48||unicode>57)
			            return false
			    }
    
    }
 </script>
