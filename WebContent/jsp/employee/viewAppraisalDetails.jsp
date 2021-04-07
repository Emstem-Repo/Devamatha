<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<script language="JavaScript" src="js/admission/studentdetails.js"></script>
<SCRIPT type="text/javascript">
	function downloadFile(documentId) {
		document.location.href = "DocumentDownloadAction.do?documentId="
				+ documentId;
	}
	function getSemesterMarkDetails(qualId) {
		var url  = "studentEdit.do?method=viewSemesterMarkPage&editcountID="+qualId;
    	myRef = window.open(url,"ViewSemesterMarkDetails","left=20,top=20,width=800,height=300,toolbar=1,resizable=0,scrollbars=1");
   }
	function getDetailsMark(qualId) {
		var url  = "studentEdit.do?method=viewDetailMarkPage&editcountID="+qualId;
    	myRef = window.open(url,"ViewDetailsMark","left=20,top=20,width=800,height=600,toolbar=1,resizable=0,scrollbars=1");
   }
	function detailLateralSubmit()
	{
		var url  = "studentEdit.do?method=viewLateralEntryPage";
    	myRef = window.open(url,"ViewLateralDetails","left=20,top=20,width=800,height=600,toolbar=1,resizable=0,scrollbars=1");
	}
	function detailTransferSubmit()
	{
		var url  = "studentEdit.do?method=viewTransferEntryPage";
    	myRef = window.open(url,"ViewTransferDetails","left=20,top=20,width=800,height=600,toolbar=1,resizable=0,scrollbars=1");
	}
	function goToView(){
		document.location.href="principalAppraisal.do?method=initViewAppraisal";
	}
</SCRIPT>

<script language="JavaScript" src="js/calendar_us.js"></script>
	<link rel="stylesheet" href="css/calendar.css">
<script language="JavaScript" src="js/admission/admissionform.js"></script>
<style type="text/css">
input.colorbox{
background-color:#306EFF;
color:white;

}
</style>
<html:form action="/principalAppraisal" method="POST">
	<html:hidden property="method" value="viewAppraisalDetails" />
	<html:hidden property="formName" value="principalAppraisalForm" />
	<html:hidden property="pageType" value="3" />
	
	 
	<%--<logic:notEmpty  property="appraisalTO" name="principalAppraisalForm">
	
	<bean:write name="appraisalTO" property="detailsId"/><br></br>
	<bean:write name="appraisalTO" property="attributeName"/>
		
	</logic:notEmpty>--%>
	
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
						<span class="Bredcrumbs"><bean:message key="knowledgepro.employee.appraisal.attribute"></bean:message></span><br></br>
							<td><img src="images/01.gif" width="5" height="5" /></td>
							<td width="914" background="images/02.gif"></td>
							<td><img src="images/03.gif" width="5" height="5" /></td>
						</tr>
						
						<tr>
							<td width="5" background="images/left.gif"></td>
							<td valign="top">
							<table width="100%" cellspacing="1" cellpadding="2">
							
							
								
								<tr><td class="row-odd" width="5">Attribute Names </td>
								<td class="row-even" width="5">
								<logic:notEmpty property="attriButeList" name="principalAppraisalForm">
							<logic:iterate id="attribute" name="principalAppraisalForm" property="attriButeList">
							
								<bean:write name="attribute" property="name"/>&nbsp;&nbsp;
								</logic:iterate></logic:notEmpty>
								</td></tr>
								
<%-- <logic:notEmpty  property="appraiseTO" name="principalAppraisalForm">								
 
									<tr class="row-even"> 
										 <td><c:out value="${count+1}"></c:out></td>
										<td><bean:write name="appraiseTO" property="attributeId"></bean:write></td>
										<td><bean:write name="appraiseTO" property="attributeName"/>  </td>
										<td><bean:write name="appraiseTO" property="isEmployee"/>  </td>
										<td><bean:write name="appraiseTO" property="attributeCreatedDate"/></td>
										
										
										 
									</tr>
								
								</logic:notEmpty> --%>
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
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top">
					<table width="100%" border="0" align="center" cellpadding="0"
						cellspacing="0">
						<tr>
						    <span class="Bredcrumbs"><bean:message key="knowledgepro.employee.appraisal"></bean:message></span><br></br>
							<td><img src="images/01.gif" width="5" height="5" /></td>
							<td width="914" background="images/02.gif"></td>
							<td><img src="images/03.gif" width="5" height="5" /></td>
						</tr>
						
						<tr>
							<td width="5" background="images/left.gif"></td>
							<td valign="top">
							<table width="100%" cellspacing="1" cellpadding="2">
								<tr class="row-odd">
								<td>Faculty Name</td>
								<td>Department Name</td>
								<td>Recommendation</td>
								<td>Created Date</td>
								
								</tr>
<logic:notEmpty  property="appraiseTO" name="principalAppraisalForm">								
 
									<tr class="row-even"> 
										<%-- <td><c:out value="${count+1}"></c:out></td>--%>
										
								<td>
								<bean:write name="appraiseTO" property="employeeName"/>
								</td>		
							<td><bean:write name="appraiseTO" property="departmentName"/>  </td>
								<td><bean:write name="appraiseTO" property="recomand"/>  </td>		
								<td>
								<bean:write name="appraiseTO" property="appraiseCreatedDate"/>
								</td>		
										 
									</tr>
							
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
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top">
					<table width="100%" border="0" align="center" cellpadding="0"
						cellspacing="0">
						<tr>
						    <span class="Bredcrumbs"><bean:message key="knowledgepro.employee.appraisal.details"></bean:message></span><br></br>
							<td><img src="images/01.gif" width="5" height="5" /></td>
							<td width="914" background="images/02.gif"></td>
							<td><img src="images/03.gif" width="5" height="5" /></td>
						</tr>
						
						<tr>
							<td width="5" background="images/left.gif"></td>
							<td valign="top">
							<table width="100%" cellspacing="1" cellpadding="2">
								<tr class="row-odd">
								<td>Attribute Name</td>
								<td> Appraisal value </td>
								
								
								
								</tr>
 <logic:notEmpty  property="appraisalDetails" name="principalAppraisalForm">								
 <logic:iterate id="appraise" property="appraisalDetails" name="principalAppraisalForm">
									<tr class="row-even"> 
										 <%-- <td><c:out value="${count+1}"></c:out></td>--%>
										<td><bean:write name="appraise" property="empAttribute.name"></bean:write></td>
										<td><bean:write name="appraise" property="attributeValue"/>  </td>
										
										 
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
						<tr></tr>
					<tr>
		              <td colspan="2"><div align="center">
                      <input type="button" value="Back" class="colorbox" onclick="goToView()"></input>
                      </div>
					  </td>
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