<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms" %>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>

<%@page import="com.kp.cms.constants.CMSConstants"%><script type="text/javascript">
function addFeeAccount() {
	document.getElementById("method").value = "saveFeeAssignmentAccount";
	document.feeAssignmentForm.submit();	
}

function updateFeeAccount() {
	document.getElementById("method").value = "updateFeeAssignmentAccount";
	document.feeAssignmentForm.submit();
}

function backToFee(){
	document.location.href="FeeAssignment.do?method=initFeeAssignment";
}

function clearField(field){
	if(field.value == "0.0")
		field.value = "";
}
function checkForEmpty(field){
	if(field.value.length == 0)
		field.value="0.0";
	if(isNaN(field.value)) {
		field.value="0.0";
	}
}

var feeId;



</script>

<html:form action="/FeeAssignment">

<c:choose>
   	 <c:when test="${feeAccountoperation == 'edit'}">
   	 		<html:hidden property="method" styleId="method" value="updateFeeAssignmentAccount"/>
   	 </c:when>
  	 <c:otherwise>
   	 		<html:hidden property="method" styleId="method" value="saveFeeAssignmentAccount"/>
</c:otherwise>
</c:choose> 

<html:hidden property="formName" value="feeAssignmentForm"/>
<html:hidden property="pageType" value="1"/>
<input type="hidden" id="feeId" name="id" value="<bean:write name="feeAssignmentForm" property="id"/>"/>  <!-- usefull while edit and in reactivate-->
<div style="overflow: auto; width: 1100px;">
<table width="98%" border="0">
  <tr>
    <td><span class="heading"><bean:message key="knowledgepro.fee"/><span class="Bredcrumbs">&gt;&gt; <bean:message key="knowledgepro.fee.assignmentaccount"/> &gt;&gt;</span></span></td>
  </tr>
  <tr>
    <td>
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="10"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="body" ><div align="left"><strong class="boxheader"><bean:message key="knowledgepro.fee.assignmentaccount"/></strong></div></td>
        <td width="13" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      
      <tr>
        <td valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news">
        <div align="center">
        		  <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                    <tr>
               	    <td colspan="6" align="left">
               	    <div align="right" style="color:red"> <span class='MandatoryMark'> <bean:message key="knowledgepro.mandatoryfields"/></span></div>
               	    <div id="errorMessage">
                       <FONT color="red"><html:errors/></FONT>
                       <FONT color="green">
							<html:messages id="msg" property="messages" message="true">
									<c:out value="${msg}" escapeXml="false"></c:out><br>
							</html:messages>
					  </FONT>
					  </div>
               	    </td>
                   </tr>
                   <tr>
                      <td ><img src="images/01.gif" width="5" height="5" /></td>
                      <td width="914" background="images/02.gif"></td>
                      <td><img src="images/03.gif" width="5" height="5" /></td>
                    </tr>
                    <tr>
                      <td width="5"  background="images/left.gif"></td>
                      <td valign="top">
                                                 	 
                           	 		<table width="100%" cellspacing="1" cellpadding="2">
			                          <tr >
			                            <td width="21%" height="25" class="row-odd" ><div align="right"><bean:message key="knowledgepro.admin.program.type"/>:</div></td>
			                            <td width="10%" height="25" class="row-even" ><bean:write name="feeAssignmentForm" property="programTypeName"/></td>
			                            <td width="12%" height="25" class="row-odd" ><div align="right"><bean:message key="knowledgepro.admin.program.name"/>:</div></td>
			                            <td width="14%" height="25" class="row-even" ><bean:write name="feeAssignmentForm" property="programName"/></td>
			                            <td width="15%" height="25" class="row-odd" ><div align="right"><bean:message key="knowledgepro.admin.course"/>:</div></td>
			                            <td width="13%" height="25" class="row-even" ><bean:write name="feeAssignmentForm" property="courseName"/></td>
			                          </tr>
			                          <tr >
			                            <td width="21%" height="25" class="row-odd" ><div align="right"><bean:message key="knowledgepro.fee.academicyear"/>:</div></td>
			                            <td width="10%" height="25" class="row-even" ><bean:write name="feeAssignmentForm" property="academicYear"/></td>
			                            <td width="12%" height="25" class="row-odd" ><div align="right"><bean:message key="knowledgepro.fee.semister"/>:</div></td>
			                            <td width="14%" height="25" class="row-even" ><bean:write name="feeAssignmentForm" property="semister"/></td>
			                            <td width="15%" height="25" class="row-odd" ><div align="right"><bean:message key="knowledgepro.fee.aidedUnaided"/>:</div></td>
			                            <td width="13%" height="25" class="row-even" ><bean:write name="feeAssignmentForm" property="aidedUnAided"/></td>
			                          </tr>
			                      </table>
			                      <logic:equal value="true" property="isSemesterWise" name="feeAssignmentForm">
			                      <table width="100%" cellspacing="1" cellpadding="2">
			                      <tr>
			                      <td width="5%" height="25" class="row-odd" ><div align="right">Course Semester No:</div></td>
			                      <c:choose>
			                      <c:when test="${feeAccountoperation == 'edit'}">
			                            <td width="10%" height="25" class="row-even" ><html:select  property="sem"  name="feeAssignmentForm"  styleId="semester" disabled="true">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<html:option value="1">1</html:option>
										<html:option value="2">2</html:option>
										<html:option value="3">3</html:option>
										<html:option value="4">4</html:option>
										<html:option value="5">5</html:option>
										<html:option value="6">6</html:option>	
									</html:select></td></c:when>
									<c:otherwise>
									<td width="10%" height="25" class="row-even" ><html:select  property="sem"  name="feeAssignmentForm"  styleId="semester" disabled="false">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<html:option value="1">1</html:option>
										<html:option value="2">2</html:option>
										<html:option value="3">3</html:option>
										<html:option value="4">4</html:option>
										<html:option value="5">5</html:option>
										<html:option value="6">6</html:option>	
									</html:select></td>
									</c:otherwise>
									</c:choose>
			                      </tr>
			                      </table>
                                     </logic:equal>            
                      </td>
                      <td width="5" height="30"  background="images/right.gif"></td>
                    </tr>
                    <tr>
                      <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
                      <td background="images/05.gif"></td>
                      <td><img src="images/06.gif" /></td>
                    </tr>
                </table>
        </div>
        </td>
        <td width="13" valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
       
      <tr>
        <td height="122" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news">
        <div align="center">
              <table width="100%" height="156"  border="0" cellpadding="0" cellspacing="0">
                 <tr>
                   <td height="10" colspan="6" class="body" ></td>
                 </tr>
                 <tr>
                      <td ><img src="images/01.gif" width="5" height="5" /></td>
                      <td width="914" background="images/02.gif"></td>
                      <td><img src="images/03.gif" width="5" height="5" /></td>
                 </tr>
                 
                 <tr>
                   <td width="5"  background="images/left.gif"></td>
                   <td valign="top"  >
                   <c:set var="count" value="0"/>
                   <logic:iterate id="feeAccount" name="feeAssignmentForm" property="feeAccountsMap">
                       <c:set var="count" value="${count + 1 }"/>
                   </logic:iterate>
                                 
                   <table width="100%" cellspacing="1" cellpadding="2">
                      <c:set var="dataId" value="0"/>
                      <c:set var="globalcount" value="0"/>
	                  <%-- <nested:iterate id="admitedThrough" name="feeAssignmentForm" property="admitedThroughMap" indexId="admCount">
						 table header start
	                      <tr >
	                           <td height="25" class="row-even" width="15%"><div align="center" class="heading"><bean:message key="knowledgepro.fee.admissioncategory"/></div></td>
	                           <td colspan='<c:out value="${count}"/>' class="row-even" align="left"> <div align="left" class="heading"><bean:write name="feeAssignmentForm" property="admittedThrough"/></div></td>
	                          
						    
	                      </tr>
	                     <%-- table header finished --%>
	                     <nested:iterate id="feeGroupMap" name="feeAssignmentForm" property="feeHeadingsMap" indexId="groupCount">
							<tr >
	                           <td height="25" class="row-even" width="15%"><div align="center" class="heading">Fee Group</div></td>
	                           <td colspan='<c:out value="${count}"/>' class="row-even" align="left"> <div align="left" class="heading"><bean:write name="feeGroupMap" property="key"/></div></td>
	                      	</tr>
	                      	<tr>
		                       <td height="25" class="row-odd" width="15%"><div align="center"><bean:message key="knowledgepro.fee.feename"/></div></td>
	                    	   <logic:iterate id="feeAccount" name="feeAssignmentForm" property="feeAccountsMap"> 
		                     	  <td height="25" class="row-odd" ><div align="left"><bean:write name="feeAccount" property="value"/></div></td>
	                   		   </logic:iterate>  
		                    </tr>
	                     <nested:iterate id="feeHeading" name="feeGroupMap" property="value" indexId="headingCount">
		                     <tr >
		                           <td width="16%" height="25" class="row-even" width="30%"><div align="center"><nested:write name="feeHeading" property="value"/></div></td>
								   
								   <c:choose>
								        
								        <c:when test="${feeAccountoperation == 'edit'}">
								        	<!-- This will execute when operation is update -->	
										   <nested:iterate id="feeAccount" name="feeAssignmentForm" property="feeAccountsMap" indexId="accountCount">
						                       <td height="25" class="row-even" align="left"><div align="left">

						                       <c:set var="present" value="0"/>
						                         <!-- Here will add input type if it contain data if conditin matches -->
													
													<nested:iterate id="feeAssignment" name="feeAssignmentForm" property="feeAssignmentList" type="com.kp.cms.to.fee.FeeAccountAssignmentTO" indexId="assignmentCount">
						                             <c:set var="feeAccId"><bean:write name="feeAccount" property="key"/></c:set>	
										       		 <c:set var="feeHeadingId"><bean:write name="feeHeading" property="key"/></c:set>
										       		 <c:if test="${feeAssignment.feeAccountTo.id == feeAccId && feeAssignment.feeHeadingTo.id == feeHeadingId}">
						                             	<input type="hidden" name="feeAssignmentList[<c:out value="${globalcount}"/>].feeAccountId" value="<bean:write name='feeAccount' property='key'/>">
						          						<input type="hidden" name="feeAssignmentList[<c:out value="${globalcount}"/>].feeHeadingId" value="<bean:write name="feeHeading" property="key"/>">
						                             	
																
														<table width="100%" cellspacing="1" cellpadding="2">													
															<tr><td>
														<input type="text" name="feeAssignmentList[<c:out value="${globalcount}"/>].amount" value="<bean:write name='feeAssignment' property='amount'/>" onfocus="clearField(this)" onblur="checkForEmpty(this)" maxlength="10" onkeypress="return isDecimalNumberKey(this.value,event)" onkeyup="onlyTwoFractions(this,event)" size="5"/>
														</td>
														<%-- <td>
														
														<select name="feeAssignmentList[<c:out value="${globalcount}"/>].currencyId" id="feeAssignmentList[<c:out value="${globalcount}"/>].currencyId"  style="width:50px">
								                          
								                           <logic:notEmpty property="currencies" name="feeAssignmentForm">
																<option value=""><bean:message key="knowledgepro.admin.select"/></option>
																<%String selected=""; %>
																<logic:iterate id="option" property="currencies" name="feeAssignmentForm">
																		<logic:notEmpty property="currencyId" name="feeAssignment">
																		<logic:equal value='<%= feeAssignment.getCurrencyId() %>' name="option" property="id"><%selected="selected"; %></logic:equal>
																		<logic:notEqual value='<%= feeAssignment.getCurrencyId() %>' name="option" property="id"><%selected=""; %></logic:notEqual>
																		</logic:notEmpty>
																		<logic:empty property="currencyId" name="feeAssignment">
																		<logic:equal value='<%=String.valueOf(CMSConstants.KP_DEFAULT_CURRENCY_ID) %>' name="option" property="id"><%selected="selected"; %></logic:equal>
																		<logic:notEqual value='<%=String.valueOf(CMSConstants.KP_DEFAULT_CURRENCY_ID) %>' name="option" property="id"><%selected=""; %></logic:notEqual>
																		</logic:empty>
																		<option value='<bean:write name="option" property="id"/>' <%=selected %> ><bean:write name="option" property="name"/> </option>
																</logic:iterate>
								                           </logic:notEmpty>
								                         </select></td>
														

														<td>LIG:&nbsp;&nbsp;</td><td><input type="text" id='ligamount_<c:out value="${globalcount}"/>' name="feeAssignmentList[<c:out value="${globalcount}"/>].ligAmount" value="<bean:write name='feeAssignment' property='ligAmount'/>" onfocus="clearField(this)" onblur="checkForEmpty(this)" maxlength="10" onkeypress="return isDecimalNumberKey(this.value,event)" onkeyup="onlyTwoFractions(this,event)" size="5"/></td>
	                                                   <td></td> --%>
	                                                   </tr>
														<%-- <tr><td colspan="2"></td><td>Caste:&nbsp;&nbsp;</td><td><input type="text" id='casteamount_<c:out value="${globalcount}"/>' name="feeAssignmentList[<c:out value="${globalcount}"/>].casteAmount" value="<bean:write name='feeAssignment' property='casteAmount'/>" onfocus="clearField(this)" onblur="checkForEmpty(this)" maxlength="10" onkeypress="return isDecimalNumberKey(this.value,event)" onkeyup="onlyTwoFractions(this,event)" size="5"/></td>
	                                                   <td></td>
														</tr>
														 --%>
														 </table>

						                             	<c:set var="globalcount" value="${globalcount + 1}"/>
						                             	<c:set var="present" value="1"/>
						                            </c:if>
						                  		 </nested:iterate>


						                  		 <!-- If above iteration failed to add input here i am adding and amount will be 0 -->
						                  		 <c:if test="${present == 0}">
						                  		 	
						                             	<input type="hidden" name="feeAssignmentList[<c:out value="${globalcount}"/>].feeAccountId" value="<bean:write name='feeAccount' property='key'/>">
						          						<input type="hidden" name="feeAssignmentList[<c:out value="${globalcount}"/>].feeHeadingId" value="<bean:write name="feeHeading" property="key"/>">
						                             	

														
														<table width="100%" cellspacing="1" cellpadding="2">													
															<tr><td>
														<input type="text" name="feeAssignmentList[<c:out value="${globalcount}"/>].amount" value="0.0" onfocus="clearField(this)" onblur="checkForEmpty(this)" maxlength="10" onkeypress="return isDecimalNumberKey(this.value,event)" onkeyup="onlyTwoFractions(this,event)" size="5"/>
														</td>
														
														
														<%--<td>
														<select name="feeAssignmentList[<c:out value="${globalcount}"/>].currencyId" id="feeAssignmentList[<c:out value="${globalcount}"/>].currencyId"  style="width:50px">
								                          
								                           <logic:notEmpty property="currencies" name="feeAssignmentForm">
																<option value=""><bean:message key="knowledgepro.admin.select"/></option>
																<%String selected=""; %>
																<logic:iterate id="option" property="currencies" name="feeAssignmentForm">
																		
																		<logic:empty property="currencyId" name="feeAssignment">
																		<logic:equal value='<%=String.valueOf(CMSConstants.KP_DEFAULT_CURRENCY_ID) %>' name="option" property="id"><%selected="selected"; %></logic:equal>
																		<logic:notEqual value='<%=String.valueOf(CMSConstants.KP_DEFAULT_CURRENCY_ID) %>' name="option" property="id"><%selected=""; %></logic:notEqual>
																		</logic:empty>
																		<option value='<bean:write name="option" property="id"/>' <%=selected %> ><bean:write name="option" property="name"/> </option>
																</logic:iterate>
								                           </logic:notEmpty>
								                         </select></td>
									
														<td>LIG:&nbsp;&nbsp;</td><td><input type="text" id='ligamount_<c:out value="${globalcount}"/>' name="feeAssignmentList[<c:out value="${globalcount}"/>].ligAmount" value="<bean:write name='feeAssignment' property='ligAmount'/>" onfocus="clearField(this)" onblur="checkForEmpty(this)" maxlength="10" onkeypress="return isDecimalNumberKey(this.value,event)" onkeyup="onlyTwoFractions(this,event)" size="5"/></td>
	                                                   <td></td> --%>
	                                                   </tr>
														<%-- <tr><td colspan="2"></td><td>Caste:&nbsp;&nbsp;</td><td><input type="text" id='casteamount_<c:out value="${globalcount}"/>' name="feeAssignmentList[<c:out value="${globalcount}"/>].casteAmount" value="<bean:write name='feeAssignment' property='casteAmount'/>" onfocus="clearField(this)" onblur="checkForEmpty(this)" maxlength="10" onkeypress="return isDecimalNumberKey(this.value,event)" onkeyup="onlyTwoFractions(this,event)" size="5"/></td>
	                                                   <td></td>
														</tr>--%>
														</table>


						                             	<c:set var="globalcount" value="${globalcount + 1}"/>
						                             	<c:set var="present" value="1"/>






	
						                  		 </c:if>
						                  		 <c:set var="dataId" value="${dataId + 1 }"/>
						                       </div></td>
					                       </nested:iterate>
								        </c:when>
								        <c:otherwise>
								            <!-- This will execute when operation is insert -->
								         	<logic:iterate id="feeAccount" name="feeAssignmentForm" property="feeAccountsMap">	
						                       <td height="25" class="row-even" align="left"><div align="left">
						                         <nested:iterate id="feeAssignment" name="feeAssignmentForm" property="feeAssignmentList" type="com.kp.cms.to.fee.FeeAccountAssignmentTO" indexId="c">
						                             <c:if test="${c == dataId}"> <!-- this check i am doing for print only one text box out of n-->
						                             	<input type="hidden" name="feeAssignmentList[<c:out value="${c}"/>].feeAccountId" value="<bean:write name='feeAccount' property='key'/>">
						          					    <input type="hidden" name="feeAssignmentList[<c:out value="${c}"/>].feeHeadingId" value="<bean:write name="feeHeading" property="key"/>">
											            
														 <table width="100%" cellspacing="1" cellpadding="2">													
															<tr><td>
															<nested:text styleId="amount_<%=c %>" property="amount" onfocus="clearField(this)" onblur="checkForEmpty(this)" maxlength="10" onkeypress="return isDecimalNumberKey(this.value,event)" onkeyup="onlyTwoFractions(this,event)" size="5"/>
														</td>
														<%-- <td>
														<nested:select property="currencyId" style="width:50px">
															<option value=""><bean:message key="knowledgepro.admin.select"/></option>
															<%String selected=""; %>
																<logic:iterate id="option" property="currencies" name="feeAssignmentForm">
																		<logic:notEmpty property="currencyId" name="feeAssignment">
																		<logic:equal value='<%= feeAssignment.getCurrencyId() %>' name="option" property="id"><%selected="selected"; %></logic:equal>
																		<logic:notEqual value='<%= feeAssignment.getCurrencyId() %>' name="option" property="id"><%selected=""; %></logic:notEqual>
																		</logic:notEmpty>
																		<logic:empty property="currencyId" name="feeAssignment">
																		<logic:equal value='<%=String.valueOf(CMSConstants.KP_DEFAULT_CURRENCY_ID) %>' name="option" property="id"><%selected="selected"; %></logic:equal>
																		<logic:notEqual value='<%=String.valueOf(CMSConstants.KP_DEFAULT_CURRENCY_ID) %>' name="option" property="id"><%selected=""; %></logic:notEqual>
																		</logic:empty>
																		<option value='<bean:write name="option" property="id"/>' <%=selected %> ><bean:write name="option" property="name"/> </option>
																</logic:iterate>
								                         </nested:select></td>
									
														<%-- <td>LIG:&nbsp;&nbsp;</td><td><nested:text styleId='ligamount_<%=c %>' property="ligAmount"  onfocus="clearField(this)" onblur="checkForEmpty(this)" maxlength="10" onkeypress="return isDecimalNumberKey(this.value,event)" onkeyup="onlyTwoFractions(this,event)" size="5"/></td>
	                                                   <td></td>
	                                                   --%>
	                                                   </tr>
														
														<%--<tr><td colspan="2"></td><td>Caste:&nbsp;&nbsp;</td><td><nested:text styleId='casteamount_<%=c %>' property="casteAmount"  onfocus="clearField(this)" onblur="checkForEmpty(this)" maxlength="10" onkeypress="return isDecimalNumberKey(this.value,event)" onkeyup="onlyTwoFractions(this,event)" size="5" /></td>
	                                                   <td>
	                                                   </td>
														</tr> --%>
														</table>

										             </c:if>
						                  		 </nested:iterate>
						                         <c:set var="dataId" value="${dataId + 1 }"/>
						                       </div></td>
						                      
						                       
						                       
					                       </logic:iterate>
								        </c:otherwise>
								   </c:choose>
			                 </tr>
		                 </nested:iterate>
		               </nested:iterate>   
	              <%--  </nested:iterate>--%>
                   </table>
                   
                  </td>
                 <td width="5" align="right" background="images/right.gif"></td>
                 </tr>
                 <tr>
	                  <td>
	                  
	                  </td>
                 </tr>    
                                
                 <tr>
                   <td width="5"  background="images/left.gif"></td>
                   <td height="35" valign="top" class="body" >
                   <table width="100%" height="27"  border="0" cellpadding="0" cellspacing="0">
                     <tr><td style="height: 10px" align="center" colspan="3"></td></tr>
                     <tr>
                       <td width="45%"><div align="right">                       
                       <c:choose>
                           	 <c:when test="${feeAccountoperation == 'edit'}">
                           	 		<html:submit property="" styleClass="formbutton" value="Update Fee Account"></html:submit>
                           	 </c:when>
                           	 <c:otherwise>
                           	 		<html:submit property="" styleClass="formbutton" value="Add Fee Account"></html:submit>
                           	 </c:otherwise>
                        </c:choose> 
                       
                       </div></td>
                       <td width="2%"></td>
                       <td width="53%" height="45" align="left"><html:button property="" styleClass="formbutton" value="Cancel" onclick="backToFee()"></html:button></td>
                     </tr>                     
                     <tr><td style="height: 10px" align="center" colspan="3"></td></tr>                     
                   </table>
                   </td>
                   <td width="5" align="right" background="images/right.gif"></td>
                 </tr>
                 <tr>
                      <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
                      <td background="images/05.gif"></td>
                      <td><img src="images/06.gif" /></td>
                </tr>
                <tr>
                      <td colspan="3"> &nbsp;</td>
                      
                </tr>
              </table>
            </div>
            </td>
        <td width="13" valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
        <td width="100%" background="images/TcenterD.gif"></td>
        <td><img src="images/Tright_02.gif" width="9" height="29"></td>
      </tr>
    </table></td>
  </tr>
</table>
</div>
</html:form>
<script type="text/javascript">
var obj = document.getElementById("tempyear");
if(obj != null) {
	var year = document.getElementById("tempyear").value;
	if(year.length != 0) {
	 	document.getElementById("academicYear").value=year;
	}
}

</script>