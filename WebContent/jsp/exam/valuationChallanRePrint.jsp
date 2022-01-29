<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@page import="java.util.Map,java.util.HashMap"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<script type="text/javascript">
	function cancelAction() {
		resetFieldAndErrMsgs();
	}
	function cancelPage() {
		document.location.href = "valuationChallan.do?method=initReprintChallan";
		
	}
	function otherValuatorsMap(displayOther){
		if(displayOther == null || displayOther==""){
			displayOther = document.getElementById("employeeId").value;
		}
		document.getElementById("employeeId").value=displayOther;
		if(displayOther != null && displayOther == "Other"){
			document.getElementById("displayOtherEmployee").style.display = "block";
			getExternalEmployeeList(displayOther,updateExternalEmployeeList);
		}else{
			document.getElementById("displayOtherEmployee").style.display = "none";
		}
		document.getElementById("otherEmpId").value="";
	}
	function updateExternalEmployeeList(req){
		updateExternalEmployeeMap(req,"otherEmpId","- Select -");
	}
	function printDetails(id){
		document.location.href = "valuationChallan.do?method=reprintChallan&remunerationId="+id;
	}
</SCRIPT>
<html:form action="/valuationChallan">
	<html:hidden property="formName" value="valuationChallanForm" styleId="formName" />
	<html:hidden property="method" styleId="method" value="getGeneratedChallanDetails" />
	<html:hidden property="pageType" value="1" styleId="pageType" />
	<table width="99%" border="0">
		<tr>
			<td><span class="heading"><span class="Bredcrumbs">Exam
			&gt;&gt; Exam Valuation &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="heading_white">Reprint Challan</td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td height="20" class="news">

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
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="heading">&nbsp;</td>
					<td valign="top" background="images/Tright_3_3.gif"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="heading">
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
									<div align="right"><span class="Mandatory">*</span>&nbsp; Valuator Name</div>
									</td>
									<td class="row-even">
									<html:select property="employeeId" styleClass="comboLarge" styleId="employeeId" onchange="otherValuatorsMap(this.value)">
										<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
										<html:optionsCollection name="evaluatorsMap" label="value" value="key" />
										<html:option value="Other">Other</html:option>
									</html:select>
									<a href="javascript:void(0)" onclick="otherValuatorsMap('Other')">External</a>
									</td>
									<td class="row-odd"><div align="right"><bean:message key="knowledgepro.fee.academicyear"/>:</div></td>
		                            <td class="row-even" align="left">
		                             <%String year="year"; %>
		                             <c:set var="dyopid"><%=year %></c:set>
		                            <html:select property="academicYear" name="valuationChallanForm" styleId="<%=year %>" styleClass="combo" onchange="getExamNameByYear(this.value)">
	                     	   					 <html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
	                     	   					 <cms:renderEmployeeYear></cms:renderEmployeeYear>
	                     			   		</html:select>
		                          
										<script type="text/javascript">
											var opid= '<nested:write property="academicYear"/>';
											if(opid!=0)
											document.getElementById("<c:out value='${dyopid}'/>").value = opid;
										</script>
		                              </td>
								</tr>
								<tr>
									<td height="25" class="row-odd">
									</td>
									<td class="row-even">
										<div id="displayOtherEmployee">
										<html:select property="otherEmpId" styleClass="comboLarge" styleId="otherEmpId">
										<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
										<html:optionsCollection name="OtherEvaluatorsMap" label="value" value="key" />
										</html:select>
										</div>									
									</td>
									<td class="row-odd"></td>
									<td class="row-even"></td>
								</tr>
								
							</table>
							</td>

							<td width="5" background="images/right.gif"></td>
						</tr>
						<tr>
							<td height="5"><img src="images/04.gif" width="5" height="5" /></td>
							<td background="images/05.gif"></td>
							<td><img src="images/06.gif" /></td>
						</tr>
					</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif"></td>

				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="heading">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="20%" height="35" align="center">
							
							<html:submit value="Search" styleClass="formbutton"></html:submit>
										&nbsp;&nbsp;
							<input type="button" class="formbutton" value="Reset" onclick="cancelAction()" />
							
							</td>
						</tr>
						
						<logic:notEmpty name="valuationChallanForm" property="generatedChallnList">
								<tr>
									<td class="heading"> Generated Challan Details</td>
								</tr>
								 <tr>
									<td height="45" colspan="4" ><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
						              <tr>
						                <td ><img src="images/01.gif" width="5" height="5" /></td>
						                <td width="914" background="images/02.gif"></td>
						                <td><img src="images/03.gif" width="5" height="5" /></td>
						              </tr>
						              <tr>
						                <td width="5"  background="images/left.gif"></td>
						                <td valign="top">
						         			<table width="100%" cellspacing="1" cellpadding="2">
										         <tr>
										         	 <td height="25" class="row-odd" ><div align="center"><bean:message key="knowledgepro.slno"/></div></td>
										             <td height="25" class="row-odd" align="center">Valuator Name</td>
										             <td height="25" class="row-odd" align="center">Date</td>
										             <td height="25" class="row-odd" align="center">Chalan Number</td>
										             <td height="25" class="row-odd" align="center">Print</td>
										         </tr>
										         <logic:iterate id="challan" name="valuationChallanForm" property="generatedChallnList" indexId="count">
										         	<c:choose>
														<c:when test="${count%2 == 0}">
															<tr class="row-even">
														</c:when>
														<c:otherwise>
															<tr class="row-white">
														</c:otherwise>
													</c:choose>
								                   		<td width="10%" height="25" class="row-even" ><div align="center"><c:out value="${count + 1}"/></div></td>
								                   		<td width="40%" height="25" class="row-even" align="center"><bean:write name="challan" property="employeeName"/></td>
								                   		<td width="20%" height="25" class="row-even" align="center"><bean:write name="challan" property="startDate"/></td>
								                   		<td width="20%" height="25" class="row-even" align="center"><bean:write name="challan" property="billNo"/></td>
								                   		<td width="17%" height="25" class="row-even" align="center">
															<img src="images/print-icon.png"
																width="16" height="18" style="cursor: pointer"
																onclick="printDetails(<bean:write name="challan" property="id"/>)">	
														</td>
										         </logic:iterate>
										    </table>
						                </td>
						                <td width="5" height="30"  background="images/right.gif"></td>
						              </tr>
						              <tr>
						                <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
						                <td background="images/05.gif"></td>
						                <td><img src="images/06.gif" /></td>
						              </tr>
						            </table></td>            
					    		</tr>
				    		</logic:notEmpty>
					</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif"></td>
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
var displayOption = document.getElementById("employeeId").value;
if(displayOption == null || displayOption==""){
	document.getElementById("displayOtherEmployee").style.display = "none";
}else{
	document.getElementById("displayOtherEmployee").style.display = "block";
}
var print = "<c:out value='${valuationChallanForm.printPage}'/>";
if(print.length != 0 && print == "true"){
	var url = "valuationChallan.do?method=printChallanPrint";
	myRef = window
			.open(url, "Challan",
					"left=20,top=20,width=900,height=900,toolbar=1,resizable=0,scrollbars=1");	
	}
</script>