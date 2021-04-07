<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tld/displaytag.tld" prefix="display"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<script language="javaScript" type="text/javascript">
function reDirectBack(){
	document.location.href = "ApplicationReceived.do?method=initApplicationReceived";
}
</script>
<html:form action="/ApplicationReceived" method="post">
<html:hidden property="method" styleId="method" />
<html:hidden property="formName" value="applicationReceivedForm" />
<table width="98%" border="0">
	<tr>
	<td><span class="heading"><bean:message key="knowledgepro.reports"/> <span
				class="Bredcrumbs">&gt;&gt;<bean:message key="knowledgepro.reports.application.received.report"/>&gt;&gt;</span></span></td>
	</tr>
	<tr>
		<td valign="top">
		<table width="98%" border="0" cellpadding="0" cellspacing="0">
			<tr>
			<td width="10"><img src="images/Tright_03_01.gif" width="9"
					height="29"></td>
			<td width="100%" background="images/Tcenter.gif" class="body">
			<div align="left"><strong class="boxheader"><bean:write name="applicationReceivedForm" property="receivedApplication"/></strong></div>
			</td>
			<td width="10"><img src="images/Tright_1_01.gif" width="9"
				height="29"></td>
			</tr>
			<tr>
				<td height="8" valign="top" background="images/Tright_03_03.gif"></td>
				<td height="8" valign="top"></td>
				<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
			</tr>
			<tr>
				<td height="8" valign="top" background="images/Tright_03_03.gif"></td>
				<td height="8" valign="top">
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
							<tr bgcolor="#FFFFFF">
							<td width="5"  background="images/left.gif"></td>
              
								<td valign="top">       
		<div style="overflow: auto; width: 914px; ">
		<c:set var="temp" value="0" />  
			<display:table export="true" uid="due" name="sessionScope.applicationList" requestURI="" defaultorder="ascending" pagesize="20" style="width:100%">
			<display:setProperty name="export.excel.filename" value="receivedApp.xls"/>
			<display:setProperty name="export.xml" value="false" />
			<display:setProperty name="export.csv.filename" value="receivedApp.csv"/>
				<display:column media="html" style="padding-right: 80px;" sortable="true" title="Application No." property="applicationNo" class="row-even" headerClass="row-odd"></display:column>
				<display:column media="html" style="padding-right: 80px;" sortable="true" title="Name" property="name" class="row-even" headerClass="row-odd"></display:column>
				<display:column media="html" style="padding-right: 80px;" sortable="true" title="First Preference" property="firstPreference" class="row-even" headerClass="row-odd"></display:column>
				<display:column media="html" style="padding-right: 80px;" sortable="true" title="Second Preference" property="secondPreference" class="row-even" headerClass="row-odd"></display:column>
				<display:column media="html" style="padding-right: 80px;" sortable="true" title="Third Preference" property="thirdPreference" class="row-even" headerClass="row-odd"></display:column>
			</display:table>
		</div>	
			</td>
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
					<tr>
						<td height="25" colspan="6">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td align="center" width="100%" height="35">
								<div align="center"><input type="button"
									class="formbutton" value="Close" onclick="reDirectBack()" /></div>
								</td>
							</tr>
						</table>
					</tr>
				</table>
				</td>
				<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
			</tr>
			<tr>
				<td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
				<td background="images/TcenterD.gif"></td>
				<td><img src="images/Tright_02.gif" width="9" height="29"></td>
			</tr>
		</table>
		</td>
	</tr>
</table>
</html:form>