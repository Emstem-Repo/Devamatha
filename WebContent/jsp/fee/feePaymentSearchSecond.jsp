<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms" %>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<script type="text/javascript">

function checkData() {
	var obj2= document.getElementById("semister").selectedIndex;
	var obj3= document.getElementById("feeGroupId1").selectedIndex;
	document.getElementById("semeterSelectedIndex").value=obj2;
	document.getElementById("optionalFeeSelectedIndex").value=obj3;
	document.getElementById("admittedThroughName").value = document
	.getElementById("admittedThroughId").options[document
	.getElementById("admittedThroughId").selectedIndex].text;
	document.feePaymentForm.submit();
}

function goBack() {
	document.location.href="FeePayment.do?method=initFeePaymentSearch";
}
function getDat() {
	var finYear = document.getElementById("financialYearId").value;
	document.getElementById("year").value = finYear;
}
</script>

<html:form action="/FeePayment">
<html:hidden property="method" styleId="method" value="displayFeePaymentDetails"/>
<html:hidden property="formName" value="feePaymentForm"/>
<html:hidden property="pageType" value="2"/>
<html:hidden property="semeterSelectedIndex" styleId="semeterSelectedIndex"/>
<html:hidden property="optionalFeeSelectedIndex" styleId="optionalFeeSelectedIndex"/>
<input type="hidden" name="ligExemption" value='<bean:write	name="feePaymentForm"	property="ligExemption" />' />
<input type="hidden" name="casteExemption" value='<bean:write name="feePaymentForm"	property="casteExemption" />' />
<input type="hidden" name="registrationNo" value='<bean:write	name="feePaymentForm"	property="registrationNo" />' />
<input type="hidden" name="rollNumber" value='<bean:write name="feePaymentForm"	property="rollNumber" />' />
<input type="hidden" name="studentId" value='<bean:write	name="feePaymentForm"	property="studentId" />' />
<html:hidden property="admittedThroughName" styleId="admittedThroughName"/>
<html:hidden property="financialYearId" styleId="financialYearId"/>
<table width="98%" border="0">
  <tr>
    <td><span class="heading"><bean:message key="knowledgepro.fee"/> <span class="Bredcrumbs">&gt;&gt; <bean:message key="knowledgepro.fee.feepayment"/> &gt;&gt;</span></span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="10"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="body" ><div align="left"><strong class="boxheader"><bean:message key="knowledgepro.fee.feepayment"/> </strong></div></td>
        <td width="13" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="122" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><div align="center">
              <table width="100%" height="104"  border="0" cellpadding="0" cellspacing="0">
                
                 <tr>
                   <td height="10" align="left">
                   <div align="right" style="color:red"> <span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields"/></span></div>
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
                   <td height="49" class="body" ><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                     <tr>
                       <td ><img src="images/01.gif" width="5" height="5" /></td>
                       <td width="914" background="images/02.gif"></td>
                       <td><img src="images/03.gif" width="5" height="5" /></td>
                     </tr>
                     <tr>
                       <td width="5"  background="images/left.gif"></td>
                       <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
                           <tr >
                             <td width="25%" height="25" class="row-odd" ><div align="right"><span class="Mandatory">*</span>Year: </div></td>
                             <td width="22%" height="25" class="row-even" align="left">
                            <!--  <html:select property="selectedSems" size="2" multiple="true" styleId="semister" style="width:120px" onclick="getDat()">
                       	   	    	<html:optionsCollection name="feePaymentForm" property="semMap" label="value" value="key"/>
                            </html:select> -->
                            
							<html:select property="selectedSems" styleId="semister" styleClass="combo">
               	   				 <html:option value="">- Select -</html:option>
               	   				 <html:option value="1">1</html:option>
               	   				 <html:option value="2">2</html:option>
               	   				 <html:option value="3">3</html:option>
                       	   					
                       	 	</html:select>


                              
                             </td>
                             <td width="25%" height="25" class="row-odd"><div align="right"><span class="Mandatory">*</span>Financial Year:</div></td>
                             <td width="22%" height="25" class="row-even">
                             	<input type="hidden" id="tempyear" name="tempyear" value="<bean:write name="feePaymentForm" property="year"/>"/>
                             	<html:select property="financialYearId" styleClass="combo" styleId="year" >
								<html:option value="">-<bean:message key="knowledgepro.select" />-</html:option>
									<cms:renderFinancialForFeePaymenYear></cms:renderFinancialForFeePaymenYear>
								</html:select>  
                             </td>
                           </tr>
                            <tr >
                            
                             <td width="19%" class="row-odd" ><div align="right"><bean:message key="knowledgepro.fee.optionalfeegroup"/>:</div></td>
                             <td width="34%" class="row-even" align="left">
                            <html:select property="selectedfeeOptionalGroup" size="2" multiple="true" styleId="feeGroupId1" style="width:170px;height:100px">
                         			<html:optionsCollection name="feePaymentForm" property="feeOptionalGroupMap" label="value" value="key"/>
                       		</html:select>
                             </td>
                             <td width="19%" class="row-odd" ><div align="right">Apply Fees Exemption</div></td>
                             <td width="34%" class="row-even" align="left">
                     		 	<input type="radio" name="isFeeExemption" id="isFeeExemption_1" value="true" /> <bean:message key="knowledgepro.yes"/>									
				                <input type="radio" name="isFeeExemption" id="isFeeExemption_2" value="false" checked="checked"/> <bean:message key="knowledgepro.no"/>
                             </td>
                             
                           </tr>
							<tr class="row-white">
                            <td width="212" height="25" class="row-odd"><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.admin.admitted.through"/></div></td>
                            <td width="236" height="25" class="row-even" align="left">
                            <logic:notEmpty name = "feePaymentForm" property="admittedThroughList">
								<html:select property="admittedThrough" styleId="admittedThroughId" styleClass="combo">
									<html:option value="">-Select-</html:option>
									<html:optionsCollection property="admittedThroughList" label="name" value="id"/>
										
								</html:select>
								</logic:notEmpty>
							</td>
							<td class="row-odd">
						<div align="right">Cast Category:</div>
						</td>
						<td class="row-even"><html:select property="casteId" styleClass="combo">
									<html:option value="0">- Select -</html:option>
									<html:optionsCollection property="casteList" name="feePaymentForm" label="casteName" value="casteId"/>
							</html:select></td>
							</tr>
						<tr class="row-white">
						<td height="20" class="row-odd" width="40%"><div align="right"><bean:message key="knowledgepro.applicationform.secLang.label"/></div></td>
	                      <td height="20" class="row-even" align="left">
	                      
	                      <html:select property="secondLanguage" styleClass="body" styleId="secondLanguage">
								<html:option value="">
									<bean:message key="knowledgepro.admin.select" />
								</html:option>
								<html:optionsCollection property="secondLanguageList"
									label="value" value="value" />
							</html:select>
						 </td>
						</tr>
                       </table></td>
                       <td width="5" height="30"  background="images/right.gif"></td>
                     </tr>
                     <tr>
                       <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
                       <td background="images/05.gif"></td>
                       <td><img src="images/06.gif" /></td>
                     </tr>
                   </table></td>
                 </tr>
                 <tr>
                   <td height="35" valign="top" class="body" ><table width="100%" cellspacing="1" cellpadding="2"><tr ></tr>
                     <tr>
                       <td height="37" colspan="2" >   
		                <table width="100%"  border="0" cellspacing="0" cellpadding="0">
		                  <tr><td style="height: 10px" align="center" colspan="1"></td></tr>
		                  <tr>
		                    <td height="27"><div align="center">
		                      <html:button property="" styleClass="formbutton" value="Next" onclick="checkData()"></html:button>
		                      &nbsp;&nbsp;&nbsp; <html:button property="" styleClass="formbutton" value="Go Back" onclick="goBack()"></html:button>
		                    </div></td>
		                    </tr>		                  
		                    <tr><td style="height: 10px" align="center" colspan="1"></td></tr>		                    
		                </table>
                       </td>
                     </tr>
                   </table></td>
                </tr>
                 <tr>
                   <td height="10" class="body" ></td>
                 </tr>
              </table>
            </div></td>
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
var error = "<c:out value='${feePaymentForm.error}'/>";
if(error.length == 0) {
	setTimeout("resetData()",100);
}
function resetData() {
}
var year = document.getElementById("tempyear").value;
if(year.length != 0) {
 	document.getElementById("admissionYear").value=year;
} else if(year.length ==0 && error == "true"){
	document.getElementById("admissionYear").selectedIndex = 0;
}

</script>