<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<script type="text/javascript">
function resetFormFields(){	
	resetErrMsgs();
	document.getElementById("year").value = resetYear();
	document.getElementById("course").selectedIndex = 0;
}
</script>
<html:form action="/adminCertificateMarksCard">	
		<html:hidden property="method" styleId="method" value="getCandidates" />
		<html:hidden property="formName" value="certificateMarksCardForm"/>
		<html:hidden property="pageType" value="1" />
	<table width="100%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message key="knowledgepro.exam.exam"/>
			<span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.exam.consolidateMarks.displayName"/>
			 &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td valign="top">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> <bean:message key="knowledgepro.exam.consolidateMarks.displayName"/></strong></td>

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
									<td width="20%" class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.admin.course" />:</div>
									</td>
									<td width="30%" class="row-even">
							<html:select property="courseId" styleClass="comboLarge"
								styleId="course">
								<html:option value=""><bean:message
								key="knowledgepro.admin.select" /></html:option>
								<logic:notEmpty name="certificateMarksCardForm" property="courseList">
								<html:optionsCollection name="certificateMarksCardForm"
									property="courseList" label="name" value="id" />
									</logic:notEmpty>
							</html:select>
							</td>
									<td width="20%" height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>
										<bean:message key="knowledgepro.exam.reJoin.joiningBatch"/>:</div>
									</td>
									<td class="row-even" valign="top">
									<input type="hidden" id="tempyear" name="tempyear" value="<bean:write name="certificateMarksCardForm" property="year"/>"/>
									<html:select property="year" styleId="year" styleClass="combo">
										<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
										<cms:renderFutureYear normalYear="true"></cms:renderFutureYear>
									</html:select></td>																	
								</tr>
								<tr>
									<td width="24%" class="row-odd">
									<div align="right">
									<DIV align="right">Reg No From :</DIV>
									</div>
									</td>
									<td width="21%" class="row-even">
									<html:text property="regFrom" size="10"></html:text>
									</td>
									<td width="22%" class="row-odd">
									<div align="right">Reg No To:</div>
									</td>
									<td width="36%" class="row-even">
									<html:text property="regTo" size="10"></html:text>
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
						<tr><td style="height: 10px" align="center" colspan="3"></td></tr>
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
						<tr><td style="height: 10px" align="center" colspan="3"></td></tr>						
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
	var year = document.getElementById("tempyear").value;
	if (year.length != 0) {
		document.getElementById("year").value = year;
	}
	var print = "<c:out value='${certificateMarksCardForm.print}'/>";
	if(print.length != 0 && print == "true"){
		var url = "adminCertificateMarksCard.do?method=printMarksCard";
		myRef = window .open(url, "Marks Card", "left=20,top=20,width=900,height=900,toolbar=1,resizable=0,scrollbars=1");
		}
</script>