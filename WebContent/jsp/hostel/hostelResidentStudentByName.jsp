<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<script type="text/javascript">
function viewOnStudentId(hostelApplnId){
	document.location.href = "ResidentStudentInfo.do?method=getStudentByName&hostelApplnId="+hostelApplnId;
}
</script>
<html:form action="/ResidentStudentInfo">	
		<html:hidden property="method" styleId="method" value="" />
		<html:hidden property="formName" value="residentStudentInfoForm"/>
	<table width="100%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message key="knowledgepro.hostel"/>
			<span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.hostel.residentStudentInfo"/>
			 &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td valign="top">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> <bean:message key="knowledgepro.hostel.residentStudentInfo"/></strong></td>

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
							<td class="row-odd">
							<bean:message key="knowledgepro.hostel.hostel.entry.name"/>
							</td>
							<td class="row-odd">
							<bean:message key="knowledgepro.roomtype"/>
							</td>
							<td class="row-odd">
							<bean:message key="knowledgepro.fee.studentname"/>
							</td>
							<td class="row-odd">
							<bean:message key="knowledgepro.view"/>
							</td>
							</tr>
							<logic:notEmpty name="residentStudentInfoForm" property="list">
							<nested:iterate id="rto" name="residentStudentInfoForm" property="list">
							<tr>
							<td class="row-even">
							<bean:write name="rto" property="hostelType"/>
							</td>
							<td class="row-even">
							<bean:write name="rto" property="roomType"/>
							</td>
							<td class="row-even">
							<bean:write name="rto" property="studentName"/>
							</td>
							<td class="row-even">
							<div align="center">
							<img src="images/View_icon.gif"
							alt="View" width="24" height="21" border="0"
							style="cursor: hand"
							onclick="viewOnStudentId('<bean:write name="rto" property="hostelApplnId"/>')"></div>
							</td>
							</tr>
							</nested:iterate>
							</logic:notEmpty>
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
