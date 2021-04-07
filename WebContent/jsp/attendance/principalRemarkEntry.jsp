<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<script language="JavaScript" src="js/admission/studentdetails.js"></script>
<script language="JavaScript" src="js/admission/admissionform.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="css/styles.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
function imposeMaxLength(evt, Object) {
	var keynum = (evt.which) ? evt.which : event.keyCode;
	if (keynum == 8 || keynum == 37 || keynum == 39 || keynum == 9) {
		return true;
	}
	var MaxLen = 2000;
	return (Object.value.length < MaxLen);
}
</script>
<html:form action="/studentWiseAttendanceSummary" method="post">
	<html:hidden property="method" value="addPrincipalRemark" />
	<html:hidden property="formName" value="studentWiseAttendanceSummaryForm" />
	<html:hidden property="pageType" value="15" />
	

	<table width="100%" border="0">
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader">Remarks </strong></td>

					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>

				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" cellspacing="1" cellpadding="2">
					<%--check whether remark is editable by user or not --%>

						<tr>
						<logic:notEmpty property="staffRemarkList" name="studentWiseAttendanceSummaryForm" >				
							<tr>
							<td align="center"><span class="Bredcrumbs">Office Remarks</span></td>
							</tr>
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

									<td width="5%" height="25" class="row-odd" align="center">
										<div align="center"><bean:message key="knowledgepro.slno" /></div>
									</td>
									<td width="9%" height="25" class="row-odd" align="center"><bean:message key="knowledgepro.studentremark.remarktype.label"/></td>
									<td width="9%" class="row-odd" align="center"><bean:message key="knowledgepro.studentremark.occurance.label"/> </td>
									<td width="4%" class="row-odd" align="center">
										<div align="center"><bean:message key="knowledgepro.studentremark.commentstatus.label"/> </div>
									</td>
									<td width="7%" class="row-odd" align="center">
										<div align="center"><bean:message key="knowledgepro.studentremark.color.label"/> </div>
									</td>

									
								</tr>
								<%String dynastyle=""; %>
								<logic:iterate id="remarkTo" property="staffRemarkList" name="studentWiseAttendanceSummaryForm" indexId="count">
									<%	if(count%2==0)
											dynastyle="row-even";
										else
											dynastyle="row-odd";
										%>
									<tr>
										<td width="5%" height="25" class='<%=dynastyle %>' align="center">
										<div align="center"><%=count+1 %></div>
									</td>
									<td width="9%" height="25" class='<%=dynastyle %>' align="center"><bean:write property="remarkType" name="remarkTo"/> </td>
									<td width="9%" class='<%=dynastyle %>' align="center"><bean:write property="occurance" name="remarkTo"/></td>
									<td width="4%" class='<%=dynastyle %>' align="center">
										<div align="center">
											<logic:notEmpty property="comments" name="remarkTo">
												<table width="100%" cellspacing="0" cellpadding="0">
													<logic:iterate id="comment" property="comments" name="remarkTo">
														<tr><td class='<%=dynastyle %>' ><bean:write name="comment"/></td></tr>
													</logic:iterate>
												</table>
											</logic:notEmpty>
										</div>
									</td>
									<logic:equal value="true" property="colourPresent" name="remarkTo"><div style="border-width:1px "></div>
									<bean:define id="color" property="colourCode" name="remarkTo"></bean:define>
										<td width="7%" align="center" class='<%=dynastyle %>' valign="middle">
											<div align="center" style="width:50px;height:5px;background-color:<%=color %>;border: solid;border-width:1px;border-color: black">
												&nbsp;
											</div>
										</td>
									</logic:equal>
									<logic:equal value="false" property="colourPresent" name="remarkTo">
										<td width="7%" class='<%=dynastyle %>' align="center"></td>
									</logic:equal>
									</tr>
								</logic:iterate>
						
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
						</logic:notEmpty>		
						<tr>
						<td align="center"><span class="Bredcrumbs">Principal Remarks</span></td>
						</tr>

					<tr>
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
							<td height="25" colspan="4">
							<table width="100%" cellspacing="1" cellpadding="2">
							<tr>
								<td height="25" colspan="4">
								<table width="100%" cellspacing="1" cellpadding="2">
										<tr>
											<td width="15%" height="25" class="row-odd">
												<div align="right"><bean:message key="knowledgepro.studentremark.comment.label"/> </div>
												</td>
												<td width="45%" height="25" class="row-odd">
												<div align="left">
												<html:textarea
												property="principalRemarks" cols="80" rows="8" styleId="content"
												onkeypress="return imposeMaxLength(event,this)"></html:textarea>
												</div>
											</td>
										</tr>
									
								</table>
								</td>
							</tr>								
							</table>
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
						<td height="25" colspan="4">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="45%" height="35">
								<div align="center">
										<html:submit styleClass="formbutton" value="Submit" onclick="javascript:self.close();"></html:submit>
									</div>
								</td>
							</tr>
						</table>
						</td>
					</tr>

					</table>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>

				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td height="20" valign="top"></td>
					<td width="13" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				<tr>
					<td><img src="images/Tright_03_05.gif" width="9" height="29" /></td>
					<td width="100%" background="images/TcenterD.gif"></td>
					<td><img src="images/Tright_02.gif" width="9" height="29" /></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</html:form>
