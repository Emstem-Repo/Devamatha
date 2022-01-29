<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@ taglib uri="/WEB-INF/struts-tld/displaytag.tld" prefix="display" %>
<%@ page buffer = "500kb" %>

<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<link href="css/displaytag.css" rel="stylesheet" type="text/css">
<SCRIPT type="text/javascript">
    function exportExcel() {  
    	document.getElementById("mode").value = "excel";
    	document.getElementById("method").value = "initExportToExcel";
    	document.admMeritListForm.submit();
    }

    function cancelAction() {
    	document.location.href = "admMeritUpload.do?method=initAdmMeritListReport";
    }

</SCRIPT>
<html:form action="/admMeritUpload">

<html:hidden property="method" styleId="method" value="searchAdmMeritList"/>
<html:hidden property="formName" value="admMeritListForm"/>
	<html:hidden property="mode" styleId="mode" value=""/>
	<table width="98%" border="0">
		<tr>
    		<td class="heading">Data Migration<span class="Bredcrumbs">&gt;&gt;Admission MeritList Report&gt;&gt;</span> </td>
  		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
			
				<tr>
					<td width="10"><img src="images/Tright_03_01.gif"></td>
					<td width="100%" background="images/Tcenter.gif" class="body">
					<div align="left"><strong class="boxheader">Admission MeritList Report</strong></div>
					</td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" align="center" cellpadding="0"
						cellspacing="0">
						<tr>

							<td height="15" colspan="10" class="row-white">
							<div align="left"><FONT color="red"><html:errors /></FONT>
							<FONT color="green"> <html:messages id="msg"
								property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
								<br>
							</html:messages> </FONT></div>

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
					<div style="overflow: auto; width: 730px; ">		
									<c:set var="temp" value="0" />
										<display:table export="false" name="sessionScope.AdmMeritTO" requestURI="" defaultorder="descending" pagesize="10">
										<c:choose>
										
										<c:when test="${temp == 0}">		
											<display:column style="padding-right: 80px;" property="appNo" sortable="true" title="Appln No" class="row-even" headerClass="row-odd"/>
											<display:column style="padding-right: 80px;" property="name" sortable="true" title="Name" class="row-even" headerClass="row-odd"/>
											<display:column style="padding-right: 80px;" property="firstPreference" sortable="true" title="Class" class="row-even" headerClass="row-odd"/>
											<c:set var="temp" value="1" />
										</c:when>
										<c:otherwise>
											<display:column style="padding-right: 80px;" sortable="true" title="Appln No" property="appNo" class="row-white" headerClass="row-odd"></display:column>
											<display:column style="padding-right: 80px;" sortable="true" title="Name" property="name" class="row-white" headerClass="row-odd"></display:column>
											<display:column style="padding-right: 80px;" sortable="true" title="Class" property="firstPreference" class="row-white" headerClass="row-odd"></display:column>
											<c:set var="temp" value="0" />
											</c:otherwise>
										</c:choose>	
										
										</display:table>
										
									</div>	
									Export options:&nbsp;<a href="#" onclick="exportExcel()" >Excel</a>
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
					<table width="100%" height="54" border="0" cellpadding="1"
						cellspacing="2">
						<tr>
							<td width="100%" height="50" class="heading">
							<div align="center">
							<table width="100%" height="27" border="0" cellpadding="0"
								cellspacing="0">

								<tr>
									<td>
									<div align="center">
									<table width="100%" height="27" border="0" cellpadding="0"
										cellspacing="0">
										<tr>
											<td width="52%" height="45">
											<table width="100%" border="0" cellspacing="0"
												cellpadding="0">
												<tr>
													<td width="45%" height="35">
													<div align="right"></div>
													</td>
													<td width="2%"><html:button property="cancel"
														onclick="cancelAction()" styleClass="formbutton">
														<bean:message key="knowledgepro.cancel" />
													</html:button></td>
													<td width="53"></td>
												</tr>
											</table>
											</td>
										</tr>
									</table>
									</div>
									</td>
								</tr>
							</table>
							</div>
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
 
<logic:notEmpty name="admMeritListForm" property="downloadExcel">
<logic:notEmpty name="admMeritListForm" property="mode">
<bean:define id="downloadExcels" property="downloadExcel" name="admMeritListForm"></bean:define>
<bean:define id="modes" property="mode" name="admMeritListForm"></bean:define>

<logic:equal name="admMeritListForm" property="mode" value="excel">
<logic:equal name="admMeritListForm" property="downloadExcel" value="download">

<SCRIPT type="text/javascript">	
var url ="DownloadAdmMeritListResult.do";
myRef = window.open(url,"downloadReport","left=20,top=20,width=800,height=500,toolbar=1,resizable=0,scrollbars=1");
			
</SCRIPT>
</logic:equal>
</logic:equal>

</logic:notEmpty>
</logic:notEmpty>
</html:form>