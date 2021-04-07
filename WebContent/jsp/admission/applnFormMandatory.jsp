<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested" %>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<script type="text/javascript">
function updatMandatoryField(){
	document.getElementById("method").value = "updateMandatoryField";
	document.applnFormMandatory.submit();
}
function resetField(){
	document.getElementById("method").value = "initApplnFormMandatory";
	document.applnFormMandatory.submit();
	}
</script>
<html:form action="applnMandatory" method="post">
	<html:hidden property="formName" value="applnFormMandatory" />
	<html:hidden property="method" styleId="method" value=""/>
	<html:hidden property="pageType" value="1" />	
	<table width="100%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message key="knowledgepro.admin"/>
			<span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.admin.application.form.mandatory.field"/>
			 &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td valign="top">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> <bean:message key="knowledgepro.admin.application.form.mandatory.field"/> Entry</strong></td>

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
					<table width="100%" cellspacing="1" cellpadding="2">
								<tr>
									<td height="25" class="row-odd" align="center">
									<div align="center"><bean:message key="knowledgepro.slno" /></div>
									</td>
									<td height="25" class="row-odd" align="center"><bean:message key="knowledgepro.admin.application.form.mandatory.field.name" /></td>

									<td class="row-odd" align="center"><bean:message key="knowledgepro.admin.application.form.mandatory.field.yes" /></td>
								</tr>
								<logic:notEmpty name="applnFormMandatory" property="list">
										<nested:iterate id="list" name="applnFormMandatory" property="list"
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
											<td align="center"><bean:write name="list"
												property="displayName" /></td>
											<td align="center">
											<input type="hidden" id="needToProduce_mc_<c:out value='${count}'/>" name="NMC1_<c:out value='${count}'/>" value="<nested:write name='list' property='validate'/>"/>
											<input type="radio" id="sc_<c:out value='${count}'/>" name="list[<c:out value='${count}'/>].validate" value="yes"   />Yes 
											<input type="radio" id="sc1_<c:out value='${count}'/>" name="list[<c:out value='${count}'/>].validate" value="no"  />No
											<script type="text/javascript">
												var needToProduceMCId = "needToProduce_mc_<c:out value='${count}'/>";
												var needToProduceMC = document.getElementById(needToProduceMCId).value;
												if(needToProduceMC == "yes") {
								                        document.getElementById("sc_<c:out value='${count}'/>").checked = true;
												}	else{
													 document.getElementById("sc1_<c:out value='${count}'/>").checked = true;
													}
											</script>
											
											</td>
											
											</tr>	
										</nested:iterate>
										</logic:notEmpty>
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
								<html:button property="method" styleClass="formbutton"
												value="Submit" onclick="updatMandatoryField()"></html:button>
									
							</c:otherwise>
							</c:choose>
								</div>
							</td>
							<td width="2%"></td>
							<td width="53%">
							<html:button property=""
							styleClass="formbutton" value="Reset"
							onclick="resetField()"></html:button>
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
