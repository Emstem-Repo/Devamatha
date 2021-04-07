<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms" %>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<script type="text/javascript">
function addFeeAccount() {
	document.getElementById("method").value = "saveFeeAdditional";
	document.feeAdditionalForm.submit();	
}

function updateFeeAccount() {
	document.getElementById("method").value = "updateFeeAdditional";
	document.feeAdditionalForm.submit();
}

function backToFee(){
	document.location.href="FeeAdditional.do?method=initFeeAdditional";
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


</script>

<html:form action="/FeeAdditional">


<c:choose>
   	 <c:when test="${feeAccountoperation == 'edit'}">
   	 		<html:hidden property="method" styleId="method" value="updateFeeAdditional"/>
   	 </c:when>
  	 <c:otherwise>
   	 		<html:hidden property="method" styleId="method" value="saveFeeAdditional"/>
</c:otherwise>
</c:choose> 

<html:hidden property="formName" value="feeAdditionalForm"/>
<html:hidden property="pageType" value="2"/>
<input type="hidden" id="feeAdditionalId" name="id" value="<bean:write name="feeAdditionalForm" property="id"/>"/>  <!-- usefull while edit and in reactivate-->

<table width="98%" border="0">
  <tr>
    <td><span class="heading"><bean:message key="knowledgepro.fee"/> <span class="Bredcrumbs">&gt;&gt;<bean:message key="knowledgepro.feeadditional.feeadditional"/> &gt;&gt;</span></span></td>
  </tr>
  <tr>
    <td>
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="10"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="body" ><div align="left"><strong class="boxheader"><bean:message key="knowledgepro.feeadditional.feeadditional"/></strong></div></td>
        <td width="13" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      
      <tr>
        <td valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news">
        <div align="center">
        		  <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                    <tr>
               	    <td colspan="6" align="left">&nbsp;
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
	                            <td width="25%" height="25" class="row-odd" ><div align="right"><bean:message key="knowledgepro.fee.feegroup"/>:</div></td>
	                            <td width="25%" height="25" class="row-even" ><bean:write name="feeAdditionalForm" property="feeGroupName"/></td>
	                          </tr>
	                      </table>
                     
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
                      <td width="914" background="images/02.gif" height="5"></td>
                      <td><img src="images/03.gif" width="5" height="5" /></td>
                 </tr>
                 
                 <tr>
                   <td width="5"  background="images/left.gif"></td>
                   <td valign="top"  >
                   <c:set var="count" value="0"/>
                   <logic:iterate id="feeAccount" name="feeAdditionalForm" property="feeAccountsMap">
                       <c:set var="count" value="${count + 1 }"/>
                   </logic:iterate>
                                 
                   <table width="100%" cellspacing="1" cellpadding="2">
                      <c:set var="dataId" value="0"/>
                      <c:set var="globalcount" value="0"/>
	                     <tr >
		                       <td height="25" class="row-odd" width="30%"><div align="center"><bean:message key="knowledgepro.fee.feename"/></div></td>
	                    	   <logic:iterate id="feeAccount" name="feeAdditionalForm" property="feeAccountsMap"> 
		                     	  <td height="25" class="row-odd" ><div align="left"><bean:write name="feeAccount" property="value"/></div></td>
	                   		   </logic:iterate>  
	                     </tr>
	                     <logic:iterate id="feeHeading" name="feeAdditionalForm" property="feeHeadingsMap">
		                     <tr >
		                           <td width="16%" height="25" class="row-even" width="30%"><div align="center"><bean:write name="feeHeading" property="value"/></div></td>
								   <c:choose>
								        <c:when test="${feeAccountoperation == 'edit'}">
								        	<!-- This will execute when operation is update -->	
										   <logic:iterate id="feeAccount" name="feeAdditionalForm" property="feeAccountsMap">
						                       <td height="25" class="row-even" align="left"><div align="left">
						                       
						                       <c:set var="present" value="0"/>
						                         <!-- Here will add input type if it contain data if conditin matches -->
						                         <nested:iterate id="feeAssignment" name="feeAdditionalForm" property="feeAssignmentList" indexId="c">
						                             <c:set var="feeAccId"><bean:write name="feeAccount" property="key"/></c:set>	
										       		 <c:set var="feeHeadingId"><bean:write name="feeHeading" property="key"/></c:set>
										       		 <c:if test="${feeAssignment.feeAccountTo.id == feeAccId && feeAssignment.feeHeadingTo.id == feeHeadingId}">
						                               	<input type="hidden" name="feeAssignmentList[<c:out value="${globalcount}"/>].feeAccountId" value="<bean:write name='feeAccount' property='key'/>">
						          						<input type="hidden" name="feeAssignmentList[<c:out value="${globalcount}"/>].feeHeadingId" value="<bean:write name="feeHeading" property="key"/>">
						                             	<input type="text" name="feeAssignmentList[<c:out value="${globalcount}"/>].amount" value="<bean:write name='feeAssignment' property='amount'/>" onfocus="clearField(this)" onblur="checkForEmpty(this)" maxlength="10" onkeypress="return isDecimalNumberKey(this.value,event)" onkeyup="onlyTwoFractions(this,event)"/>
						                             	<c:set var="globalcount" value="${globalcount + 1}"/>
						                             	<c:set var="present" value="1"/>
						                            </c:if>
						                  		 </nested:iterate>
						                  		 <!-- If above iteration failed to add input here i am adding and amount will be 0 -->
						                  		 <c:if test="${present == 0}">
						                             	<input type="hidden" name="feeAssignmentList[<c:out value="${globalcount}"/>].feeAccountId" value="<bean:write name='feeAccount' property='key'/>">
						          						<input type="hidden" name="feeAssignmentList[<c:out value="${globalcount}"/>].feeHeadingId" value="<bean:write name="feeHeading" property="key"/>">
						                             	<input type="text" name="feeAssignmentList[<c:out value="${globalcount}"/>].amount" value="0.0" onfocus="clearField(this)" onblur="checkForEmpty(this)" maxlength="10" onkeypress="return isDecimalNumberKey(this.value,event)" onkeyup="onlyTwoFractions(this,event)"/>
						                             	<c:set var="globalcount" value="${globalcount + 1}"/>
						                             	<c:set var="present" value="1"/>
						                  		 </c:if>
						                  		 <c:set var="dataId" value="${dataId + 1 }"/>
						                       </div></td>
					                       </logic:iterate>
								        </c:when>
								        <c:otherwise>
								            <!-- This will execute when operation is insert -->
								         	<logic:iterate id="feeAccount" name="feeAdditionalForm" property="feeAccountsMap">	
						                       <td height="25" class="row-even" align="left"><div align="left">
						                         <nested:iterate id="feeAssignment" name="feeAdditionalForm" property="feeAssignmentList" indexId="c">
						                             <c:if test="${c == dataId}"> <!-- this check i am doing for print only one text box out of n-->
						                             	<input type="hidden" name="feeAssignmentList[<c:out value="${c}"/>].feeAccountId" value="<bean:write name='feeAccount' property='key'/>">
						          					    <input type="hidden" name="feeAssignmentList[<c:out value="${c}"/>].feeHeadingId" value="<bean:write name="feeHeading" property="key"/>">
											            <input type="text" id='amount_<c:out value="${c}"/>' name="feeAssignmentList[<c:out value="${c}"/>].amount" value="<bean:write name='feeAssignment' property='amount'/>" onfocus="clearField(this)" onblur="checkForEmpty(this)" maxlength="10" onkeypress="return isDecimalNumberKey(this.value,event)" onkeyup="onlyTwoFractions(this,event)"/>
										             </c:if>
						                  		 </nested:iterate>
						                         <c:set var="dataId" value="${dataId + 1 }"/>
						                       </div></td>
					                       </logic:iterate>
								        </c:otherwise>
								   </c:choose>
							 </tr>
		                 </logic:iterate> 
		                 <tr >
		                       <td height="5" class="row-odd" width="30%"></td>
	                    	   <logic:iterate id="feeAccount" name="feeAdditionalForm" property="feeAccountsMap"> 
		                     	  <td height="5" class="row-odd" ></td>
	                   		   </logic:iterate>  
	                     </tr>
                   </table>
                  </td>
                 <td width="5" align="right" background="images/right.gif"></td>
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
                           	 		<html:submit styleClass="formbutton" value="Update Additional Fee"></html:submit>
                           	 </c:when>
                           	 <c:otherwise>
                           	 		<html:submit styleClass="formbutton" value="Add Additional Fee"></html:submit>
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