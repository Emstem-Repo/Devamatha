<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<script type="text/javascript">
function saveRepaymentDetails(){
	document.cancelledAdmissionRepaymentForm.method.value="saveRepaymentDetails";
	document.cancelledAdmissionRepaymentForm.submit();
}
</script>
<html:form action="/cancelledAdmissionRepayment" >
	<html:hidden property="method" styleId="method" value="getStudentCanceledDetails" />	
	<html:hidden property="pageType" value="1" />
	<html:hidden property="formName" value="cancelledAdmissionRepaymentForm" />
	
	<table width="98%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message key="knowledgepro.fee" />
			<span class="Bredcrumbs">&gt;&gt; <bean:message key="knowledgepro.fee.cancelledAdmissionRepayment" /> &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="10"><img src="images/Tright_03_01.gif"></td>
					<td width="100%" background="images/Tcenter.gif" class="body">
					<div align="left"><strong class="boxheader"><bean:message key="knowledgepro.fee.cancelledAdmissionRepayment" /></strong></div>
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
                    		<td width="25%" height="25" class="row-odd"><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.template.student.regno"/></div></td>
                       		 <td width="20%" height="25" class="row-even" align="left"><span class="star">
                               <html:text property="registerNo" styleId="registerNo" name="cancelledAdmissionRepaymentForm" />
                             </span></td>      						
							<td width="10%" height="25" class="row-even" align="center">OR</td>
                             <td width="20%" height="25" class="row-odd"><div align="right" ><span class="Mandatory">*</span><bean:message key="knowledgepro.fee.appno"/>:</div></td>
                             <td width="25%" height="25" class="row-even" align="left">
                             <span class="star">
                               <html:text property="applnNo" styleId="applnNo" name="cancelledAdmissionRepaymentForm" />
                             </span></td>	
                            
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
						<tr><td style="height: 10px" align="center" colspan="3"></td></tr>
						<tr>
							<td width="46%" height="35">
							<div align="right"><html:submit styleClass="formbutton" value="Submit"></html:submit></div>
							</td>
							<td width="2%"></td>
							<td width="52%" align="left"><html:button property=""
								styleClass="formbutton" onclick="resetFieldAndErrMsgs()">
								<bean:message key="knowledgepro.admin.reset" />
								</html:button> 
							</td>
						</tr>						
						<tr><td style="height: 10px" align="center" colspan="3"></td></tr>						
					</table>
					</div>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				<logic:notEmpty name="cancelledAdmissionRepaymentForm" property="admApplnTo">	
				<tr>
        <td height="10" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
         
          <tr>
						<td height="45" colspan="4" class="heading">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Admission Canceled Details</td>
						</tr>	
          <tr>
							<td height="45" colspan="4">
							<table width="98%" border="0" align="center" cellpadding="0"
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
										<td width="20%" height="25" class="row-odd"><div align="center"><bean:message
												key="knowledgepro.fee.applicationno" /></div></td> 
										<td width="10%" height="25" class="row-odd"><div align="center"><bean:message
												key="knowledgepro.exam.blockUnblock.regNo" /></div></td> 
										<td width="20%" height="25" class="row-odd"><div align=""center"><bean:message
												key="knowledgepro.exam.UpdateExcludeWithheld.studentName" /></div></td> 
										<td width="20%" height="25" class="row-odd"><div align="center"><bean:message
												key="knowledgepro.admin.courseName" /></div></td> 
										<td width="10%" height="25" class="row-odd"><div align="center"><bean:message
												key="admissionform.studentinfo.cancelAdmission" /></div></td> 
										<td width="20%" height="25" class="row-odd"><div align="center"><bean:message
												key="knowledgepro.fee.cancelledAdmissionRepayment.reason" /></div></td> 
										</tr>
										<tr>
										<td width="20%" height="25" class="row-even" align="center">
											<bean:write name="cancelledAdmissionRepaymentForm" property="admApplnTo.applnNo" /></td>
										<td width="10%" height="25" class="row-even" align="center">
											<bean:write name="cancelledAdmissionRepaymentForm" property="admApplnTo.regNo" /></td>
										<td width="20%" height="25" class="row-even" align="center">
											<bean:write name="cancelledAdmissionRepaymentForm" property="admApplnTo.studentName" /></td>
										<td width="20%" height="25" class="row-even" align="center">
											<bean:write name="cancelledAdmissionRepaymentForm" property="admApplnTo.courseName" /></td>
										<td width="10%" height="25" class="row-even" align="center">
											<bean:write name="cancelledAdmissionRepaymentForm" property="admApplnTo.admissionCancelDate" /></td>
										<td width="20%" height="25" class="row-even" align="center">
											<bean:write name="cancelledAdmissionRepaymentForm" property="admApplnTo.admissionCancelRemarks" /></td>
										</tr>
									</table>
									</td>
									<td width="5" height="30" background="images/right.gif"></td>
								</tr>
								<tr>
									<td height="5"><img src="images/04.gif" width="5"
										height="5" /></td>
									<td background="images/05.gif"></td>
									<td><img src="images/06.gif" /></td>
								</tr>
							</table>
							</td>
						</tr>
						
		
          
        </table></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      
      <tr>
        <td height="10" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
         
          <tr>
						<td height="45" colspan="4" class="heading">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Admission Canceled Repayment Details Entry</td>
						</tr>	
          <tr>
							<td height="45" colspan="4">
							<table width="98%" border="0" align="center" cellpadding="0"
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
									
				<tr class="row-white">
                    		<td width="25%" height="25" class="row-odd"><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.fee.cancelledAdmissionRepayment.chequeNo"/></div></td>
                       		 <td width="20%" height="25" class="row-even" align="left"><span class="star">
                               <html:text property="chequeNo" styleId="chequeNo" name="cancelledAdmissionRepaymentForm" />
                             </span></td>      						
                             <td width="20%" height="25" class="row-odd"><div align="right" ><span class="Mandatory">*</span><bean:message key="knowledgepro.fee.cancelledAdmissionRepayment.cheque.date"/></div></td>
                             <td width="164" class="row-even"><html:text styleId="chequeDate" property="chequeDate"  styleClass="TextBox"/>
											<script	language="JavaScript">
												new tcal( {
													// form name
													'formname' :'cancelledAdmissionRepaymentForm',
													// input name
													'controlname' :'chequeDate'
												});
											</script>
		                           </td> 
                            
                  </tr>
				<tr class="row-white">
                    		<td width="25%" height="25" class="row-odd"><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.fee.cancelledAdmissionRepayment.cheque.issuedDate"/></div></td>
                       		 <td width="164" class="row-even"><html:text styleId="chequeIssuedDate" property="chequeIssuedDate"  styleClass="TextBox"/>
											<script	language="JavaScript">
												new tcal( {
													// form name
													'formname' :'cancelledAdmissionRepaymentForm',
													// input name
													'controlname' :'chequeIssuedDate'
												});
											</script>
		                           </td>      						
                             <td width="20%" height="25" class="row-odd"><div align="right" ><span class="Mandatory">*</span><bean:message key="knowledgepro.fee.cancelledAdmissionRepayment.repaid.amt"/></div></td>
                             <td width="25%" height="25" class="row-even" align="left">
                             <span class="star">
                               <html:text property="repaidAmt" styleId="repaidAmt" name="cancelledAdmissionRepaymentForm" />
                             </span></td>	
                            
                  </tr>
									</table>
									</td>
									<td width="5" height="30" background="images/right.gif"></td>
								</tr>
								<tr>
									<td height="5"><img src="images/04.gif" width="5"
										height="5" /></td>
									<td background="images/05.gif"></td>
									<td><img src="images/06.gif" /></td>
								</tr>
							</table>
							</td>
						</tr>
						
		
          
        </table>
        <div align="center">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="46%" height="35">
							<div align="center"><html:button property="" onclick="saveRepaymentDetails()"  styleClass="formbutton" value="Save"></html:button></div>
							</td>
						</tr>
					</table>
					</div>
        
        </td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      </logic:notEmpty>
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
