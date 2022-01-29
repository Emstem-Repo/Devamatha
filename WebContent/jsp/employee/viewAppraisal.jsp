<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>

<script type="text/javascript">
	function cancelAction() {
		document.location.href = "viewAppraisal.do?method=initViewAppraisal";
	}
	function viewDetails(appraisalId) {
		document.location.href = "principalAppraisal.do?method=viewAppraisalDetails&appraiseId="+appraisalId;
	}
</script>
<html:form action="/principalAppraisal" method="post">
	<html:hidden property="method" styleId="method" value="initViewAppraisal" />
	<html:hidden property="formName" value="principalAppraisalForm" />
	<html:hidden property="pageType" value="1" />
	<table width="99%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message
				key="knowledgepro.employee" /> <span class="Bredcrumbs">&gt;&gt;<bean:message
				key="knowledgepro.employee.appraisals" />&gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
				
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
						
					<td background="images/Tcenter.gif" class="heading_white"><bean:message
						key="knowledgepro.employee.appraisal.details" /></td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
						
				</tr>
				
				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					
					<td valign="top">
					
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
								<tr class="row-odd">
								<td>Appraisal ID </td>
								<td>Department Name </td>
								<td>Faculty Name</td>
								
								<td>View</td>
								</tr>
<logic:notEmpty  property="appraisalTO" name="principalAppraisalForm">								
 <logic:iterate id="appraise" property="appraisalTO" name="principalAppraisalForm">
									<tr class="row-even"> 
										<%-- <td><c:out value="${count+1}"></c:out></td>--%>
										<td><bean:write name="appraise" property="appraisalId"></bean:write></td>
										<td><bean:write name="appraise" property="departmentName"/>  </td>
										<td><bean:write name="appraise" property="employeeName"/>  </td>
										
										
										 <td>
										 <img src="images/View_icon.gif"
													width="16" height="18"
													onclick="viewDetails('<bean:write name="appraise" property="appraisalId" />')" />
										 </td>
									</tr>
								</logic:iterate>
								</logic:notEmpty>
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
					<td valign="top" background="images/Tright_3_3.gif"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="heading">
					
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
