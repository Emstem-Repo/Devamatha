<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<html:form action="/genSmartCardData" focus="programType">
	<html:hidden property="method" styleId="method" value="getEmployeeData" />	
	<html:hidden property="pageType" value="1" />
	<html:hidden property="formName" value="genSmartCardDataForm" />
	
	<table width="98%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message key="knowledgepro.admin.sec.EmployeeCategory" />
			<span class="Bredcrumbs">&gt;&gt; <bean:message key="knowledgepro.admission.gensmcdata" /> &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="10"><img src="images/Tright_03_01.gif"></td>
					<td width="100%" background="images/Tcenter.gif" class="body">
					<div align="left"><strong class="boxheader"><bean:message key="knowledgepro.admission.gensmcdata" /></strong></div>
					</td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
						<tr>
							<td colspan="6" align="left">
							<div align="right"><span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields" /></span></div>
							<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
							<FONT color="green"><html:messages id="msg"
								property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
								<br>
							</html:messages></FONT></div>
							</td>
						</tr>
						<tr>
							<td><img src="images/01.gif" width="5" height="5" /></td>
							<td width="914" background="images/02.gif"></td>
							<td><img src="images/03.gif" width="5" height="5" /></td>
						</tr>
						<tr>
							<td width="5" background="images/left.gif"></td>
							<td width="100%" valign="top">
							<table width="100%" height="30" border="0" cellpadding="0" cellspacing="1">
                  <tr class="row-white">
                    <td width="161" height="25" class="row-odd"><div align="right"><bean:message key="knowledgepro.usermanagement.userinfo.department"/>:</div></td>
                    <td width="164" class="row-even"> 
                <html:select styleId="departmentId"  property="departmentId" name="genSmartCardDataForm" styleClass="combo">
									<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
									<logic:notEmpty  name="genSmartCardDataForm" property="departmentMap">
									<html:optionsCollection name="genSmartCardDataForm" property="departmentMap" label="value" value="key"/></logic:notEmpty>	
				</html:select></td>
                    <td width="140" class="row-odd"><div align="right"><bean:message key="knowledgepro.employee.genSmartCardData.employeeId.from"/>:</div></td>
                    <td width="167" class="row-even"><html:text	property="empIdFrom" styleId="empIdFrom" styleClass="TextBox"
								size="20" maxlength="30" /></td>
                    <td width="127" class="row-odd"><div align="right"><bean:message key="knowledgepro.employee.genSmartCardData.employeeId.to"/>:</div></td>
                    <td width="160" class="row-even"><html:text	property="empIdTo" styleId="empIdTo" styleClass="TextBox"
								size="20" maxlength="30" /></td>
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
					<div align="center">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="46%" height="35">
							<div align="right"><html:submit styleClass="formbutton">
								Generate
							</html:submit></div>
							</td>
							<td width="2%"></td>
							<td width="52%" align="left"><html:button property=""
								styleClass="formbutton" onclick="resetFieldAndErrMsgs()">
								<bean:message key="knowledgepro.admin.reset" />
								</html:button> 
							</td>
						</tr>
					</table>
					</div>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				<tr>
					<td><img src="images/Tright_03_05.gif" height="29" width="9"></td>
					<td background="images/TcenterD.gif" width="100%"></td>
					<td><img src="images/Tright_02.gif" height="29" width="9"></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</html:form>
<script type="text/javascript">
var print = "<c:out value='${genSmartCardDataForm.print}'/>";
if(print!=null && print=='true'){
	var url = "printSmartCardData.do?method=getStreamInfo";
	myRef = window .open(url, "SmartCardData", "left=20,top=20,width=900,height=900,toolbar=1,resizable=0,scrollbars=1");
}
</script>