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
    function exportExcel() {  
    	document.getElementById("mode").value = "excel";
    	document.getElementById("method").value = "initexportToExcel";
    	document.candidateSearchForm.submit();
    }

    function exportCsv() {
        document.getElementById("mode").value= "csv";
		document.getElementById("method").value = "initexportToCsv";
		document.candidateSearchForm.submit();
    }

    function getDetails(applicationNumber,appliedYear) {      
       	var url  = "ApplicantDetails.do?method=getApplicantDetails&applicationNumber="+applicationNumber+"&applicationYear="+appliedYear;
    	myRef = window.open(url,"ViewApplicantDetails","left=20,top=20,width=800,height=600,toolbar=1,resizable=0,scrollbars=1");
   }

    function cancelAction() {
    	document.location.href = "anyStageInAdmission.do?method=initCandidateSearch";
    }

</SCRIPT>
<html:form action="/anyStageInAdmission">

	<html:hidden property="method" styleId="method"
		value="" />
			<html:hidden property="pageType" value="1" />
	<html:hidden property="formName" value="anyStageInAdmissionForm" />
	<html:hidden property="mode" styleId="mode" value=""/>
	<table width="98%" border="0">
		<tr>
    		<td class="heading"><bean:message key="knowledgepro.reports"/><span class="Bredcrumbs">&gt;&gt;<bean:message key="knowledgepro.report.any.stage.admission.result"/> &gt;&gt;</span> </td>
  		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
			
				<tr>
					<td width="10"><img src="images/Tright_03_01.gif"></td>
					<td width="100%" background="images/Tcenter.gif" class="body">
					<div align="left"><strong class="boxheader"><bean:message key="knowledgepro.report.any.stage.admission.result"/></strong></div>
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
					<div style="overflow: auto; width: 914px; ">		
									<c:set var="temp" value="0" />
										<display:table export="true" name="sessionScope.studentSearch" requestURI="" defaultorder="descending" pagesize="10" style="width:100%">
										<display:setProperty name="export.excel.filename" value="students.xls"/>
										<display:setProperty name="export.xml" value="false" />
										<display:setProperty name="export.csv.filename" value="students.csv"/>
										<c:choose>
										<c:when test="${temp == 0}">											
											<display:column style=" padding-right: 40px;" property="applnNo" sortable="true" title="Application Number" class="row-even" headerClass="row-odd"/>
											<display:column style=" padding-right: 80px;" property="name" sortable="true" title="Name" class="row-even" headerClass="row-odd"/>
											<display:column style=" padding-right: 0px;" property="totalWeightage" sortable="true" title="Total Weightage" class="row-even" headerClass="row-odd"/>
											<display:column style=" padding-right: 10px;" property="dateOfBirth" sortable="true" title="Date of Birth" class="row-even" headerClass="row-odd"/>
											<display:column style=" padding-right: 10px;" property="email" sortable="true" title="Email" class="row-even" headerClass="row-odd"/>
											<display:column style=" padding-right: 0px;" property="gender" sortable="true" title="Gender" class="row-even" headerClass="row-odd"/>
											<display:column style=" padding-right: 0px;" property="bloodGroup" sortable="true" title="Blood Group" class="row-even" headerClass="row-odd"/>
											<display:column style=" padding-right: 0px;" property="challanNo" sortable="true" title="Challan Number" class="row-even" headerClass="row-odd"/>
											<display:column style=" padding-right: 0px;" property="obtainedMark" sortable="true" title="Marks Obtained" class="row-even" headerClass="row-odd"/>
											<display:column style=" padding-right: 0px;" property="totalmark" sortable="true" title="Total Mark" class="row-even" headerClass="row-odd"/>
											<display:column style=" padding-right: 0px;" property="percentage" sortable="true" title="Percentage" class="row-even" headerClass="row-odd"/>
											<display:column style=" padding-right: 0px;" property="status" sortable="true" title="Status" class="row-even" headerClass="row-odd"/>

												<c:set var="temp" value="1" />
										</c:when>
										<c:otherwise>
											<display:column style=" padding-right: 40px;" property="applnNo" sortable="true" title="Application Number" class="row-white" headerClass="row-odd"/>
											<display:column style=" padding-right: 80px;" property="name" sortable="true" title="Name" class="row-white" headerClass="row-odd"/>
											<display:column style=" padding-right: 0px;" property="totalWeightage" sortable="true" title="Total Weightage" class="row-white" headerClass="row-odd"/>
											<display:column style=" padding-right: 10px;" property="dateOfBirth" sortable="true" title="Date of Birth" class="row-white" headerClass="row-odd"/>
											<display:column style=" padding-right: 10px;" property="email" sortable="true" title="Email" class="row-white" headerClass="row-odd"/>
											<display:column style=" padding-right: 0px;" property="gender" sortable="true" title="Gender" class="row-white" headerClass="row-odd"/>
											<display:column style=" padding-right: 0px;" property="bloodGroup" sortable="true" title="Blood Group" class="row-white" headerClass="row-odd"/>
											<display:column style=" padding-right: 0px;" property="challanNo" sortable="true" title="Challan Number" class="row-white" headerClass="row-odd"/>
											<display:column style=" padding-right: 0px;" property="obtainedMark" sortable="true" title="Marks Obtained" class="row-white" headerClass="row-odd"/>
											<display:column style=" padding-right: 0px;" property="totalmark" sortable="true" title="Total Mark" class="row-white" headerClass="row-odd"/>
											<display:column style=" padding-right: 0px;" property="percentage" sortable="true" title="Percentage" class="row-white" headerClass="row-odd"/>
											<display:column style=" padding-right: 0px;" property="status" sortable="true" title="Status" class="row-white" headerClass="row-odd"/>

												<c:set var="temp" value="0" />
											</c:otherwise>
										</c:choose>										
										</display:table>
									</div></td>
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





</html:form>