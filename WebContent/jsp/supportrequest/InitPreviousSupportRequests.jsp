<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<%@page import="com.itextpdf.text.log.SysoLogger"%><link rel="stylesheet" type="text/css" href="css/styles.css"/>
	<script>
		function cancel() {
			window.close();
		}
</script>
<html:form action="/studentSupportRequest" focus="name">
	<html:hidden property="method" styleId="method" value="searchTheSupportRequestList" />
	<html:hidden property="formName" value="studentSupportRequestForm" />
	<html:hidden property="pageType" value="1" />
	<table width="100%" border="0">
		<tr>
			<td><span class="heading"> Support Request<span class="Bredcrumbs">&gt;&gt;
			Previous Support Request Details&gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td valign="top">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> Previous Support Request Details</strong></td>

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
									<td width="25%" height="25" class="row-odd">
									<div align="right"><span class="Mandatory"></span> ID/Register No:</div>
									</td>
									<td width="25%" class="row-even"> 
										<bean:write name="studentSupportRequestForm" property="regOrUserId"/>
                					</td>
                    				<td class="row-odd" width="25%">
										<div align="right"><span class="Mandatory"></span>
											Name:
										</div>
									</td>
									<td  class="row-even" width="25%">
										<bean:write name="studentSupportRequestForm" property="previousName"/>
									</td>
								</tr>
								<tr>
									<td width="25%" height="25" class="row-odd">
									<div align="right"><span class="Mandatory"></span>  Class/Department:</div>
									</td>
									<td width="25%" class="row-even"> 
										<bean:write name="studentSupportRequestForm" property="classOrDepartment"/>
                					</td>
                    				<td class="row-odd" width="25%">
									</td>
									<td  class="row-even" width="25%">
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
					<logic:notEmpty name="studentSupportRequestForm" property="list">
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
				       				<td width="5%"  height="25" class="row-odd" ><div align="center"><bean:message key="knowledgepro.slno"/></div></td>
				       				<td width="5%" height="25" class="row-odd"><div align="center">Request ID</div></td>
				       				<td width="5%" height="25" class="row-odd"><div align="center">Date</div></td>
				       				<td width="5%" height="25" class="row-odd"><div align="center">Issue Raised By</div></td>
				       				<td width="10%" height="25" class="row-odd"><div align="center">Campus</div></td>
				       				<td width="35%" height="25" class="row-odd"><div align="center">Issue</div></td>
				       				<td width="20%" class="row-odd"><div align="center">Remarks</div></td>
				       				<td width="width="10%" height="25" class="row-odd"><div align="center">Category</div></td>
				       				<td width="5%" class="row-odd"><div align="center">Status</div></td>
				             	</tr>
								<nested:iterate id="CME" name="studentSupportRequestForm" property="previousList" indexId="count" type="com.kp.cms.to.admin.StudentSupportRequestTo">
			  						<c:choose>
										<c:when test="${count%2 == 0}">
											<tr class="row-even">
										</c:when>
										<c:otherwise>
											<tr class="row-white">
										</c:otherwise>
									</c:choose>
			           					<td  height="25" ><div align="center"><c:out value="${count + 1}"/></div></td>
			           					<td  height="25" ><div align="center"><bean:write name="CME" property="requestId"/></div></td>
			           					<td  height="25" ><div align="center"><bean:write name="CME" property="dateOfSubmssion"/></div></td>
			           					<td  height="25" ><div align="center"><bean:write name="CME" property="issueRaisedBy"/></div></td>
			           					<td  height="25" ><div align="left"><bean:write name="CME" property="campus"/></div></td>
			           					<td  height="25" ><div align="left"><bean:write name="CME" property="description"/></div></td>
			           					<td  height="25" ><div align="left"><bean:write name="CME" property="remarks"/></div></td>
			           					<td  height="25"><div align="center"><bean:write name="CME" property="categoryName"/></div></td>
			           					<td  height="25"><div align="center"><bean:write name="CME" property="status"/></div></td>
			    				</nested:iterate>
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
				</logic:notEmpty>
				<tr>

					<td height="35" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news" >
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="40%" height="35" align="center"></td>
							<td width="50%" height="35" align="left">
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
