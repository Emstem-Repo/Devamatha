<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<script type="text/javascript">
function Proceed(){
	document.getElementById("method").value="InvigilatorAllotmentAvailableList";
	document.examInviligatorAllotmentForm.submit();
	
}
function cancel(){
	document.location.href = "InviligatorAllotment.do?method=initInvigilatorAllotment";
}
</script>
<html:form action="/InviligatorAllotment" enctype="multipart/form-data">
	<html:hidden property="pageType" value="1" />
	<html:hidden property="method" value="" styleId="method" />
	<html:hidden property="formName" value="examInviligatorAllotmentForm" />
	<table width="100%" border="0">
		<tr>
			<td><span class="heading"> <bean:message
				key="knowledgepro.exam.allotment" /><span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.exam.allotment.ExemptionList.for.exam" /> &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td valign="top">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> <bean:message key="knowledgepro.exam.allotment.ExemptionList.for.exam" /></strong></td>

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
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
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
									<td  width="4%"  height="25" class="row-odd">
									<div align="center"><bean:message key="knowledgepro.slno" /></div>
									</td>
									<td width="15%"  height="5%" class="row-odd" ><div align="center">Name</div></td>
									<td width="15%"  height="5%" class="row-odd" ><div align="center">Department</div></td>
									<td width="15%"  height="5%" class="row-odd" ><div align="center">Exemption Date</div></td>
									<td width="15%"  height="5%" class="row-odd" ><div align="center">Session</div></td>
                 				</tr>
                 				<logic:notEmpty name="examInviligatorAllotmentForm" property="datewiseExemptionList">
								<nested:iterate id="CME" name="examInviligatorAllotmentForm" property="datewiseExemptionList" indexId="count" type="com.kp.cms.to.examallotment.InvigilatorsDatewiseExemptionTO">
										<c:choose>
										<c:when test="${count%2 == 0}">
											<tr class="row-even">
										</c:when>
										<c:otherwise>
											<tr class="row-white">
										</c:otherwise>
									</c:choose>
                   				<tr>
                   					<td  height="25" class="row-even" align="center"><c:out value="${count + 1}" /></td>
                   					<td  height="25" class="row-even" align="left"><bean:write name="CME" property="name"/></td>
                   					<td  height="25" class="row-even" align="left"><bean:write name="CME" property="department"/></td>
                   					<td  height="25" class="row-even" align="left"><bean:write name="CME" property="date"/></td>
                   					<td  height="25" class="row-even" align="left"><bean:write name="CME" property="session"/></td>
                   					
                   				</tr>
                				</nested:iterate>
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
					<td height="35" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news" >
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="40%" height="35" align="center"></td>
							<td width="1%" height="35" align="right"></td>
							<td width="55%" height="35" align="left">
									<html:button property="" value="Proceed" styleClass="formbutton" onclick="Proceed()"></html:button>
									<html:button property="" value="Close" styleClass="formbutton" onclick="cancel()"></html:button>
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
