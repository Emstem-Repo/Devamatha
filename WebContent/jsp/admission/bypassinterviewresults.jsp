<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>

<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<SCRIPT type="text/javascript">
	checked = false;
    function checkAll () {
		if (checked == false) {
			checked = true;
		} else {
			checked = false;
		}
		
		for (var i=0;i<document.forms[0].elements.length;i++)
		{	
			var e=document.forms[0].elements[i];
			if ((e.type=='checkbox'))
			{
				e.checked=checked;
			}
		}
		
    }

    function getDetails(applicationNumber,appliedYear,courseId) {  
       	var url  = "ApplicantDetails.do?method=getApplicantDetails&applicationNumber="+applicationNumber+"&applicationYear="+appliedYear+"&courseId="+courseId;
    	myRef = window.open(url,"ViewApplicantDetails","left=20,top=20,width=800,height=600,toolbar=1,resizable=0,scrollbars=1");
   }

    function cancelAction() {
    	document.location.href = "BypassInterview.do?method=cancelBypassInterviewSearch";
    }
    function unCheckSelectAll(field) {

        if(field.checked == false) {
        	document.getElementById("selectall").checked = false;
        }

    }

</SCRIPT>
<html:form action="/BypassInterview" focus="selectall">

	<html:hidden property="method" styleId="method"
		value="submitSelectedBypassInterviewList" />
			<html:hidden property="pageType" value="1" />
	<html:hidden property="formName" value="studentSearchForm" />
	<table width="98%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message key="knowledgepro.admission" />
			<span class="Bredcrumbs">&gt;&gt;<bean:message
				key="knowledgepro.admission.bypassinterview" /> <span
				class="Bredcrumbs">&gt;&gt;<bean:message
				key="knowledgepro.admission.bypassinterviewresults" />&gt;&gt;</span></span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="10"><img src="images/Tright_03_01.gif"></td>
					<td width="100%" background="images/Tcenter.gif" class="body">
					<div align="left"><strong class="boxheader"> <bean:message
						key="knowledgepro.admission.bypassinterviewresults" /></strong></div>
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



							<table width="99%" cellspacing="1" cellpadding="2">
								<tbody>
									<tr>
										<td colspan="10" class="row-white" height="15">
										<div align="right"><a
											href="BypassInterview.do?method=getSelectedBypassInterviewList"
											styleClass="menuLink"> <bean:message
											key="knowledgepro.admission.selectedcandidatelist" /></a></div>
										</td>
									</tr>
									
									<tr>
										<td class="row-odd">
										<div align="center"><bean:message
											key="knowledgepro.admission.addtoselectedcandidatelist" /><br>
										<input type="checkbox" id="selectall" name="selectall" onclick="checkAll();" /></div>
										</td>
										<td height="25" class="row-odd">
										<div align="center"><bean:message
											key="knowledgepro.admission.applicationnumber" /></div>
										</td>
										<td class="row-odd"><div align="center"><bean:message
											key="knowledgepro.admin.name" /></div></td>
										<td class="row-odd">
										<div align="center"><bean:message
											key="knowledgepro.admin.dateofbirth" /></div>
										</td>		
										<td class="row-odd">
										<div align="center"><bean:message
											key="knowledgepro.admission.totalweightage" /></div>
										</td>
										<td class="row-odd">
										<div align="center"><bean:message
											key="knowledgepro.admission.details" /></div>
										</td>
									</tr>

									<c:set var="temp" value="0" />
										<nested:iterate name="studentSearchForm"
										property="studentSearch" id="id" indexId="count">
										<c:choose>

											<c:when test="${temp == 0}">
												<tr>
													<td width="9%" class="row-even">
													<div align="center"><input type="checkbox" id="count"
														name="selectedCandidates"
														onclick="unCheckSelectAll(this)"
														value='<nested:write name="id" property="admApplnId"/>'>
													</td>
													<td width="10%" height="25" class="row-even"><div align="center"><nested:write
														name="id" property="applicationId" />&nbsp;</div></td>
													<td width="10%" class="row-even"><div align="center"><nested:write
														name="id" property="applicantName" /> &nbsp;</div></td>
													<td width="10%" class="row-even"><div align="center"><nested:write
														name="id" property="applicantDOB" /> &nbsp;</div></td>												
													<td width="9%" class="row-even"><div align="center"><nested:write
														name="id" property="applicantTotalWeightage" />&nbsp;</div></td>
													<td width="7%" class="row-even"><div align="center"><a
														href="javascript:void(0)"
														onclick="getDetails('<nested:write name='id' property='applicationId'/>', '<bean:write name='studentSearchForm' property='year'/>', '<bean:write name='studentSearchForm' property='courseId'/>')"><bean:message
														key="knowledgepro.admission.details" /> </a></div></td>
												</tr>
												<c:set var="temp" value="1" />
											</c:when>
											<c:otherwise>
												<tr>
													<td class="row-white">
												<div align="center">
										<input type="checkbox" 
											name="selectedCandidates" 
											onclick="unCheckSelectAll(this)"
											value='<nested:write name="id" property="admApplnId"/>'>
	                                 										</div>
										</td>
														<td height="25" class="row-white"><div align="center"><nested:write name="id" property="applicationId"/>&nbsp;</div></td>
													<td class="row-white"><div align="center"><nested:write name="id"
														property="applicantName" />&nbsp;</div></td>
													<td class="row-white"><div align="center"><nested:write name="id"
														property="applicantDOB" /> &nbsp;</div></td>												
													<td class="row-white"><div align="center"><nested:write name="id"
														property="applicantTotalWeightage" />&nbsp;</div></td>
													<td width="7%" class="row-white"><div align="center"><a
														href="javascript:void(0)"
														onclick="getDetails('<nested:write name='id' property='applicationId'/>', '<bean:write name='studentSearchForm' property='year'/>','<bean:write name='studentSearchForm' property='courseId'/>' )"><bean:message
														key="knowledgepro.admission.details" /> </a></div></td>
												</tr>
												<c:set var="temp" value="0" />
											</c:otherwise>
										</c:choose>
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
													<div align="right"><html:submit
														styleClass="formbutton">
														<bean:message key="knowledgepro.admin.add" />
													</html:submit></div>
													</td>
													<td width="2%"></td>
													<td width="53"><html:button property="cancel"
														onclick="cancelAction()" styleClass="formbutton">
														<bean:message key="knowledgepro.cancel" />
													</html:button></td>
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
</html:form>
