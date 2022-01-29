<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>

<script type="text/javascript"><!--
function editCaste(educstreamId,name) 
{
	document.getElementById("method").value = "updateEducStream";
	document.getElementById("name").value=name;
	document.getElementById("orgName").value=name;
	document.getElementById("educstreamId").value=educstreamId;
	document.getElementById("submitbutton").value="Update";
}	
function resetFormFields()
{
	document.getElementById("name").value=null;
	resetErrMsgs();
	if (document.getElementById("method").value == "updateCaste") 
	{
		document.getElementById("name").value = document.getElementById("orgName").value;
	}
}

function reActivate()
{
	document.location.href="educationstream.do?method=reactivateEducStream";
}

function deleteCaste(educstreamId)
{
	deleteConfirm=confirm("Are you sure you want to delete this entry?")
	if(deleteConfirm)
	{
		document.location.href="educationstream.do?method=deleteEducStream&educstreamId="+educstreamId;
	}
}
</script>

<html:form action="/educationstream.do" focus="name">
	<c:choose>
		<c:when test="${operation == 'edit'}">
		<html:hidden property="method" styleId="method" value="updateEducStream" />
		</c:when>
		<c:otherwise>
		<html:hidden property="method" styleId="method" value="addEducStream" />
		</c:otherwise>
		</c:choose>

	<html:hidden property="educstreamId" styleId="educstreamId" />
	<html:hidden property="orgName" styleId="orgName" />	
	<html:hidden property="formName" value="educationstreamform" />
	<html:hidden property="pageType" value="1" />
	
	<table width="100%" border="0">
		<tr>

			<td><span class="Bredcrumbs"> <bean:message
				key="knowledgepro.admin" /> <span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.admin.educationstream.report" /> &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td valign="top">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> <bean:message
						key="knowledgepro.admin.educationstream" /> Entry</strong></td>

					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="20" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<div align="right"><FONT color="red"> <span
						class='MandatoryMark'><bean:message
						key="knowledgepro.mandatoryfields" /></span></FONT></div>
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
									<td width="46%" height="25"class="row-odd">
									<div align="right"><span class="Mandatory">*</span>Education Stream::</div>
									</td>
									<td width="54%" height="25" class="row-even"><span
										class="star"> <html:text property="educstreamName"
										styleClass="TextBox" styleId="name" size="15" maxlength="11" />
										 <span class="star"></span></td>
									
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
										key="knowledgepro.admin.educationstream.name" /></div>
									</td>
									<td width="9%" class="row-odd">
									<div align="center"><bean:message key="knowledgepro.edit" /></div>
									</td>
									<td width="8%" class="row-odd">
									<div align="center"><bean:message
										key="knowledgepro.delete" /></div>
									</td>
								</tr>
								<logic:iterate id="cList" property="educationStreamList"
									name="educationstreamform" indexId="count">
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
											property="name" /></td>
											
										<td height="25" align="center">
										<div align="center"><img src="images/edit_icon.gif"
											width="16" height="18"onclick="editCaste('<bean:write name="cList" property="id" />','<bean:write name="cList" property="name" />')"
											 /></div>
										</td>
										<td height="25">
										<div align="center"><img src="images/delete_icon.gif"
											width="16" height="16" onclick="deleteCaste('<bean:write name="cList" property="id"/>' )" /></div>
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
