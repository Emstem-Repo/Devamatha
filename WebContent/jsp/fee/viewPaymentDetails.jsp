<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<script type="text/javascript">

function viewPayment() {
	document.location.href="FeeAssignment.do?method=initFeePaymentSearch";
}

function paymentSearch() {
	   document.location.href="FeePayment.do?method=initFeePaymentSearch";
}
</script>
<html:form action="/FeePayment">
<table width="98%" border="0">
  <tr>
    <td><span class="heading"><bean:message key="knowledgepro.fee"/> <span class="Bredcrumbs">&gt;&gt; <bean:message key="knowledgepro.fee.feedetails"/> &gt;&gt;</span></span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="10"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="body" ><div align="left"><strong class="boxheader"><bean:message key="knowledgepro.fee.feedetails"/></strong></div></td>
        <td width="13" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="122" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><div align="center">
              <table width="100%" height="156"  border="0" cellpadding="0" cellspacing="0">
                
                 <tr>
                   <td height="10" colspan="6" class="body" ></td>
                 </tr>
                 <tr>
                   <td height="35" colspan="6" valign="top" class="body" >
                   <table width="100%" cellspacing="1" cellpadding="2">

                     <tr >
                       <td width="26%" height="25" class="row-odd" ><div align="right"><bean:message key="knowledgepro.interview.ApplicationNo"/></div></td>
                       <td width="19%" height="25" class="row-even" ><div align="left"><bean:write name="feePaymentForm" property="applicationId"/></div></td>
                       <td width="26%" class="row-odd" ><div align="right"><bean:message key="knowledgepro.fee.regno"/>.</div></td>
                       <td width="29%" class="row-even" ><div align="left"><bean:write name="feePaymentForm" property="registrationNo"/></div></td>
                     </tr>
                     
                     <tr >
                       <td width="26%" height="25" class="row-odd" ><div align="right"><bean:message key="knowledgepro.admin.admittedthrough"/>. </div></td>
                       <td width="19%" height="25" class="row-even" ><div align="left"><bean:write property="admittedThroughName" name="feePaymentForm"/></div></td>
                       <td width="26%" class="row-odd" ><div align="right">&nbsp;</div></td>
                       <td width="29%" class="row-even" ><div align="left">&nbsp;</div></td>
                     </tr>
                     
                   </table>
                   </td>
                 </tr>
                 <logic:iterate id="semId" name="feePaymentForm" property="semSet" indexId="i">
				     <tr>	
				     	<td height="25" colspan="6" valign="top" class="body" >
				     			 <table width="100%" cellspacing="1" cellpadding="2">
				     			 <tr>
				     			 		<td height="25" class="row-even" width="30%" align="right"><div align="left" class="heading"><bean:message key="knowledgepro.fee.semister"/>:</div></td>
				     			 		<td height="25" class="row-even" width="75%" align="left"><div align="left" class="heading"><bean:write name="semId"/></div></td>
				     			 </tr>
				     			 </table>
				     	</td>
				     </tr>
			     
				     <logic:iterate id="fee" name="feePaymentForm" property="feesViewList" type="com.kp.cms.to.fee.FeeTO" indexId="feeCount">
					 <c:if test="${semId == fee.semister}">
						 <tr>
			                   <td height="35" colspan="6" valign="top" class="body" >
			                      
			                   <c:set var="count" value="0"/>
			                   <logic:iterate id="feeAccount" name="fee" property="feeAccountsMap">
			                       <c:set var="count" value="${count + 1 }"/>
			                   </logic:iterate>
			                   <table width="100%" cellspacing="1" cellpadding="2">
			                      <c:set var="dataId" value="0"/>
			                      <logic:iterate id="admitedThrough" name="fee" property="admitedThroughMap">
				                      
				                   	  <tr >
				                           <td height="25" class="row-even" width="30%"><div align="left" class="heading"><bean:message key="knowledgepro.admission.subjectGroup"/>:</div></td>
				                           <td colspan='<c:out value="${count}"/>' class="row-even" align="left"> <div align="left" class="heading"><bean:write name="fee" property="subjectGroup.name"/></div></td>
				                      </tr>
				                      <tr >
					                       <td height="25" class="row-odd" width="30%"><div align="center"><bean:message key="knowledgepro.fee.feename"/></div></td>
				                    	   <logic:iterate id="feeAccount" name="fee" property="feeAccountsMap"> 
					                     	  <td height="25" class="row-odd" ><div align="left"><bean:write name="feeAccount" property="value"/></div></td>
				                   		   </logic:iterate>  
				                      </tr>
				                      <logic:iterate id="feeApplicable" name="fee" property="feeApplicablesMap">
					                     <tr >
					                           <td width="16%" height="25" class="row-even" width="30%"><div align="left"><bean:write name="feeApplicable" property="value"/></div></td>
											   <logic:iterate id="feeAccount" name="fee" property="feeAccountsMap">
							                       <td height="25" class="row-even" align="left"><div align="left">
							                         <logic:iterate id="feeAssignment" name="fee" property="feeAccountAssignments" indexId="c">
							                             <c:set var="feeAccId"><bean:write name="feeAccount" property="key"/></c:set>	
											       		 <c:set var="applicableId"><bean:write name="feeApplicable" property="key"/></c:set>
											       		 <c:set var="admittedThId"><bean:write name="admitedThrough" property="key"/></c:set>
							                             <c:if test="${feeAssignment.feeAccountTO.id == feeAccId && feeAssignment.applicableTO.id == applicableId && feeAssignment.admittedThroughTO.id == admittedThId}">
							                             	<bean:write name='feeAssignment' property='amount'/>
											             </c:if>
							                  		 </logic:iterate>
							                         <c:set var="dataId" value="${dataId + 1 }"/>
							                       </div></td>
						                       </logic:iterate>
						                 </tr>
					                  </logic:iterate> 
					                  <tr>
					                  <td width="16%" height="25" class="row-even" width="30%"><div align="right">&nbsp;</div></td>
					                   <logic:iterate id="feeAccount" name="fee" property="feeAccountsMap">
					                 		<td height="25" class="row-even" align="left"><div align="left">
					                 		   &nbsp;
					                 		</div></td>
					                   </logic:iterate>
					                  </tr>
					                  <tr>
					                 	<td width="16%" height="25" class="row-even" width="30%"><div align="right"><bean:message key="knowledgepro.fee.grandtotal"/>:</div></td>
					                 	<td colspan='<c:out value="${count}"/>' class="row-even" align="left">
					                 	<nested:iterate id="feePayment" name="feePaymentForm" property="feePaymentList" indexId="feePaymentCount">
											<c:if test="${feePayment.feeTo.id == fee.id}">
											 <bean:write name='feePayment' property='totalAmount'/>
					                 	 	</c:if>     
		           						</nested:iterate>      
					                 	</td>
					                 </tr>  
				                  </logic:iterate>
				                  <tr >
				                       <td height="25" class="row-even" width="30%">&nbsp;</td>
				                       <td height="25" colspan='<c:out value="${count}"/>' class="row-even" ></td>
				                   	  </tr>
			                   </table>
			                 </td>
			            </tr>    
			           </c:if>  
	                </logic:iterate> 
           </logic:iterate>  
                <tr>
                <td height="35" colspan="6" valign="top" class="body" >
	                <table width="100%"  border="0" cellspacing="0" cellpadding="0">
	                  <tr><td style="height: 10px" align="center" colspan="3"></td></tr>
	                  <tr>
	                    <td width="45%" height="35"><div align="right">
	                        	<html:button property="" styleClass="formbutton" value="Back" onclick="paymentSearch()"></html:button>
	                    </div></td>
	                    <td width="2%"></td>
	                    <td width="53%">&nbsp;</td>
	                  </tr>	                  
	                  <tr><td style="height: 10px" align="center" colspan="3"></td></tr>	                  
	                </table>
				</td>
                 </tr>
                 <tr>
                   <td height="10" colspan="6" class="body" ></td>
                 </tr>
              </table>
            </div></td>
        <td width="13" valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
        <td width="100%" background="images/Tcenter.gif"></td>
        <td><img src="images/Tright_02.gif" width="9" height="29"></td>
      </tr>
    </table></td>
  </tr>
</table>
</html:form>