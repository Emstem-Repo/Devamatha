<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>


<html:html>
<head>

 <script src="jquery/development-bundle/jquery-1.7.1.js"></script>
 <script src="http://code.jquery.com/jquery-latest.js"></script>
 

<title><bean:message key="knowledgepro.title"/> </title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

<script language="JavaScript" src="js/calendar_us.js"></script>
<script language="JavaScript" src="js/admission/admissionform.js"></script>
	<link rel="stylesheet" href="css/calendar.css">

<link href="css/styles.css" rel="stylesheet" type="text/css">

<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 5px;
	margin-right: 0px;
	margin-bottom: 0px;
}
-->
</style></head>
<script type="text/javascript">
var jq=$.noConflict();

function payOnline(){
	document.admissionFormForm.method.value="redirectToPGI";
	document.admissionFormForm.submit();
}

jq(document).ready(function(){
	var options = {};
	if(jq('#SBIRadio').is(':checked')){
		 jq(".SBI").show();
		 jq(".DD").hide();
		 jq(".OnlinePayment").hide();
	}
	else if(jq('#DDRadio').is(':checked')){
		 jq(".DD").show();
		 jq(".SBI").hide();
		 jq(".OnlinePayment").hide();
	}
	else if(jq('#onlinePayRadio').is(':checked')){
		jq(".OnlinePayment").show();
		jq(".SBI").hide();
		jq(".DD").hide();
	}
	else{
	jq(".SBI").hide();
	jq(".DD").hide();
	jq(".OnlinePayment").hide();
	}
	jq("#SBIRadio").click(function(){
		 jq(".SBI").show(2000);
		 jq(".DD").hide();
		 jq(".OnlinePayment").hide();
	  });

	jq("#DDRadio").click(function(){
		     jq(".SBI").hide();
			 jq(".DD").show(2000);
			 jq(".OnlinePayment").hide();
		  });

	jq("#onlinePayRadio").click(function(){
		 jq(".SBI").hide();
		 jq(".DD").hide();
		 jq(".OnlinePayment").show(2000);
		  });

	  
	});




function validate(evt) {
	  var theEvent = evt || window.event;
	  var key = theEvent.keyCode || theEvent.which;
	  key = String.fromCharCode( key );
	  var regex = /[0-9]|\./;
	  if( !regex.test(key) ) {
		  if (theEvent.keyCode!=8){
	    theEvent.returnValue = false;
	    if(theEvent.preventDefault) theEvent.preventDefault();
		  }
	  }
	}

</script>
<body>
<html:form action="/admissionFormSubmit" method="POST">
<html:hidden property="method" value=""/>
<html:hidden property="pageType" value="1"/>
<html:hidden property="formName" value="admissionFormForm"/>
<%
	String submitjsmethod=null;
%>
<logic:notEmpty name="transactionstatus" scope="request">
	<%
		submitjsmethod="#";
	%>
</logic:notEmpty>
<logic:empty name="transactionstatus" scope="request">
	<%
		submitjsmethod="submitAdmissionForm('submitApplicationFormInfo')";
	%>
</logic:empty>
<%String dynaMandate=""; %>
 <logic:equal value="true" property="onlineApply" name="admissionFormForm">
	<%dynaMandate="<span class='Mandatory'>*</span>"; %>
</logic:equal>
		
<table width="98%" border="0">
  
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" ></td>
        <td width="935" colspan="2" background="images/Tcenter.gif" class="body" ><div align="left"><span class="boxheader"><bean:message key="knowledgepro.admission"/> &gt;&gt; <bean:message key="knowledgepro.applicationform"/> &gt;&gt;</span></div></td>
        <td width="9" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td valign="top" background="images/Tright_03_03.gif"></td>
        <td height="46" colspan="2" class="heading">
			<logic:equal value="false" property="singlePageAppln" name="admissionFormForm">
			<img src="images/Application_tab.jpg" width="664" height="33" border="0">
			</logic:equal>
			
         <div align="right"> <span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields"/></span></div></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      
	<tr><td valign="top" background="images/Tright_03_03.gif"></td>
	<td colspan="2">
					
				<div id="errorMessage">
      						<html:errors/><FONT color="green">
							<html:messages id="msg" property="messages" message="true">
									<c:out value="${msg}" escapeXml="false"></c:out><br>
							</html:messages>
					  </FONT>
					</div>
				</td>
	<td valign="top" background="images/Tright_3_3.gif" class="news"></td></tr>
      
	<tr>
        <td height="24" valign="top" background="images/Tright_03_03.gif"></td>
        <td height="24" colspan="2" class="heading">&nbsp;&nbsp;<bean:message key="admissionForm.applicationfeeinfo.main.label"/></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
	      <tr>
        <td height="52" valign="top" background="images/Tright_03_03.gif"></td>
        <td colspan="2" valign="top" class="news"><table width="99%" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td ><img src="images/01.gif" width="5" height="5"></td>
            <td width="914" background="images/02.gif"></td>
            <td><img src="images/03.gif" width="5" height="5"></td>
          </tr>
          <tr>
          <td width="5"  background="images/left.gif"></td>
            <td width="100%" height="25" valign="top"><table width="100%" height="25" border="0" cellpadding="0" cellspacing="1">
              <tr class="row-white" >
                <td width="16%" height="20" class="row-odd"><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.admission.programtype"/>:</div></td>
                <td width="16%" height="20" class="row-even">
                <input type="hidden" id="programType" name="programType" value='<bean:write name="admissionFormForm" property="programTypeId"/>'/>
				
                <bean:write name="admissionFormForm" property="progTypeName"/>
				
				</td>
                <td width="16%" height="20" class="row-odd"><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.admission.program"/>:</div></td>
                <td width="16%" height="20" class="row-even">
				
                <bean:write name="admissionFormForm" property="programName"/>
				</td>
                <td width="16%" class="row-odd"><div align="right"><span class="Mandatory">*</span><bean:message key="admissionForm.edit.firstpref.label"/> </div></td>
                <td width="16%" class="row-even">
				
                <bean:write name="admissionFormForm" property="courseName"/>

				</td>
              </tr>
              
            </table></td>
            <td  background="images/right.gif" width="5" height="5"></td>
          </tr>
          <tr>
            <td height="5"><img src="images/04.gif" width="5" height="5"></td>
            <td background="images/05.gif"></td>
            <td><img src="images/06.gif" ></td>
          </tr>
        </table></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
     <tr>
      <td valign="top" background="images/Tright_03_03.gif"></td>
      <td valign="top" colspan="2" class="heading" align="center">Application Fee Payment Option:</td>
      <td width="9" valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>  
      <tr>
      <td valign="top" background="images/Tright_03_03.gif"></td>
      <td height="10" colspan="2">&nbsp; </td>
      <td width="9" valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      
      
       <tr>
            <td valign="top" background="images/Tright_03_03.gif"></td>
           <td colspan="2" valign="top" class="news">&nbsp;
           </td>
            <td width="9" valign="top" background="images/Tright_3_3.gif" class="news"></td>
       </tr>
       
       
       <tr>
        <td height="52" valign="top" background="images/Tright_03_03.gif"></td>
        <td colspan="2" valign="top" class="news"><table width="60%" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td ><img src="images/01.gif" width="5" height="5"></td>
            <td width="914" background="images/02.gif"></td>
            <td><img src="images/03.gif" width="5" height="5"></td>
          </tr>
          <tr>
          <td width="5"  background="images/left.gif"></td>
            <td width="100%" height="25" ><table width="100%" height="25" border="0" cellpadding="0" cellspacing="1">
            <tr class="row-even">
            <%-- 
            <logic:equal value="true" name="admissionFormForm" property="indianCandidate">
            --%>
            <td width="100%" class="heading" id="SBI" align="center"><html:radio styleId="SBIRadio" property="selectedFeePayment" value="CHALLAN">Challan</html:radio></td>
           <%-- 
            </logic:equal>
            --%>
             <logic:equal value="true" name="admissionFormForm" property="indianCandidate">
           
             <%-- 
              <td width="50%" class="heading" id="DD">
            <html:radio styleId="DDRadio" property="selectedFeePayment" value="DD">Demand Draft</html:radio>
            </td>
            
            --%>
             </logic:equal>
            
            <%--    
            <td  width="50%" class="heading" id="OnlinePayment"><html:radio styleId="onlinePayRadio" property="selectedFeePayment" value="OnlinePayment">Credit Card/Debit Card/NetBanking</html:radio></td>
            
             --%>
            </tr>
            </table></td>
            <td  background="images/right.gif" width="5" height="5"></td>
          </tr>
          <tr>
            <td height="5"><img src="images/04.gif" width="5" height="5"></td>
            <td background="images/05.gif"></td>
            <td><img src="images/06.gif" ></td>
          </tr>
        </table></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      
       <tr>
            <td valign="top" background="images/Tright_03_03.gif"></td>
           <td colspan="2" valign="top" class="news">&nbsp;
           </td>
            <td width="9" valign="top" background="images/Tright_3_3.gif" class="news"></td>
       </tr>
  <tr class="SBI">
        <td height="52" valign="top" background="images/Tright_03_03.gif"></td>
        <td colspan="2" valign="top" class="news"><table width="60%" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td ><img src="images/01.gif" width="5" height="5"></td>
            <td width="914" background="images/02.gif"></td>
            <td><img src="images/03.gif" width="5" height="5"></td>
          </tr>
          <tr>
            <td width="5"  background="images/left.gif"></td>
            <td width="100%" height="75" valign="top"><table width="100%" height="75" border="0" cellpadding="0" cellspacing="1">            
              <tr class="row-even">
                <%--<td height="20" class="row-odd">
                
                <div align="right"><%=dynaMandate%><bean:message key="admissionForm.application.challan.label"/></div>
                 
                </td>
                <td height="20" class="row-even">
                 <%--
                <html:text property="challanNo" size="15" maxlength="30" ></html:text>
               
                </td> --%>
                <td height="20" class="row-odd"><div align="right"><%=dynaMandate%>Journal No.</div></td>
                <td class="row-white"><html:text property="journalNo" size="15" maxlength="30"></html:text></td>
                <td height="20" class="row-odd"><div align="right"><%=dynaMandate%><bean:message key="admissionForm.application.date.label"/><bean:message key="admissionForm.application.dateformat.label"/></div></td>
                <td height="20" class="row-even"><html:text property="applicationDate" styleId="applicationdate" size="12" maxlength="15"></html:text><script language="JavaScript">
							new tcal( {
								// form name
								'formname' :'admissionFormForm',
								// input name
								'controlname' :'applicationdate'
							});
						</script></td>
               </tr>
              <tr class="row-even">
                <td height="20" class="row-odd"><div align="right"><%=dynaMandate%><bean:message key="admissionForm.application.amount.label"/></div></td>
                <td height="20" class="row-even">
                <logic:empty name="admissionFormForm" property="applicationAmount">
                <html:text property="applicationAmount"  size="15" maxlength="8" onkeypress='validate(event)'> </html:text>
                </logic:empty>
                <logic:notEmpty name="admissionFormForm" property="applicationAmount">
                 <html:text property="applicationAmount" disabled="false" size="15" maxlength="8" onkeypress='validate(event)'> </html:text>
                </logic:notEmpty>
                </td>
               
               
                <td height="20" class="row-odd"><div align="right"><bean:message key="knowledgepro.hostel.reservation.branchName"/></div></td>
                <td height="20" class="row-even"><html:text property="bankBranch"  size="15" maxlength="20"></html:text></td>
                
              </tr>
           </table></td>
            <td  background="images/right.gif" width="5" height="5"></td>
          </tr>
          <tr>
            <td height="5"><img src="images/04.gif" width="5" height="5"></td>
            <td background="images/05.gif"></td>
            <td><img src="images/06.gif" ></td>
          </tr>
        </table></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>   
      <tr class="SBI">
        <td valign="top" background="images/Tright_03_03.gif"></td>
        <td colspan="2" valign="top" class="news"><div align="center">
              <table width="60%" height="27"  border="0" cellpadding="1" cellspacing="2">
                <tr>
                  <td><table width="100%"  border="0" cellspacing="0" cellpadding="0"><tr>
                    <td width="48%" height="21"><div align="right"><html:button property="" onclick='<%=submitjsmethod %>' styleClass="formbutton" value="Continue"></html:button> </div></td>
                      <td width="2%"></td>
                      <td width="50%" height="21"><div align="left"><html:button property=""  styleClass="formbutton" value="Reset" onclick="resetApplicationDetails()"></html:button></div></td>
                    </tr>
                  </table></td>
                </tr>
              </table>
            </div></td>
        <td width="9" valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>

<tr class = "DD">
        <td height="52" valign="top" background="images/Tright_03_03.gif"></td>
        <td colspan="2" valign="top" class="news"><table width="60%" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td ><img src="images/01.gif" width="5" height="5"></td>
            <td width="914" background="images/02.gif"></td>
            <td><img src="images/03.gif" width="5" height="5"></td>
          </tr>
          <tr>
            <td width="5"  background="images/left.gif"></td>
            <td width="100%" height="75" valign="top"><table width="100%" height="75" border="0" cellpadding="0" cellspacing="1">
              <tr class="row-even">
                <td height="20" class="row-odd"><div align="right"><%=dynaMandate%>DD.No:</div></td>
                <td height="20" class="row-even"><html:text property="ddNo" size="15" maxlength="30"></html:text></td>
                <td height="20" class="row-odd"><div align="right"><%=dynaMandate%><bean:message key="knowledgepro.employee.place" />(State):</div></td>
                <td class="row-white"><html:text property="ddDrawnOn" size="15" maxlength="30"></html:text></td>
                <td height="20" class="row-odd"><div align="right"><%=dynaMandate%><bean:message key="knowledgepro.admin.template.Date"/><bean:message key="admissionForm.application.dateformat.label"/></div></td>
                <td height="20" class="row-even"><html:text property="ddDate" styleId="ddDate" size="12" maxlength="15"></html:text><script language="JavaScript">
							new tcal( {
								// form name
								'formname' :'admissionFormForm',
								// input name
								'controlname' :'ddDate'
							});
						</script></td>
              </tr>
              <tr class="row-even">
                <td height="20" class="row-odd"><div align="right"><%=dynaMandate%><bean:message key="admissionForm.application.amount.label"/></div></td>
                <td height="20" class="row-even">
                 <logic:empty name="admissionFormForm" property="ddAmount" >
                <html:text property="ddAmount"  size="10" maxlength="8" onkeypress='validate(event)'> </html:text>
                </logic:empty> 
                <logic:notEmpty name="admissionFormForm" property="ddAmount">
                 <html:text property="ddAmount" disabled="false" size="10" maxlength="8" onkeypress='validate(event)'> </html:text>
                </logic:notEmpty>&nbsp;&nbsp;
               
               
               <%--
                <nested:select name="admissionFormForm" property="internationalApplnFeeCurrencyId" styleId='internationalApplnFeeCurrencyId' styleClass="comboMedium" style="max-width:10%;" disabled="false" >
												<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
								              	<html:optionsCollection name="admissionFormForm" property="currencyList" label="name" value="id"/>
				</nested:select>&nbsp;&nbsp;
                OR Enter Equivalent INR&nbsp;&nbsp;
                <html:text property="equivalentApplnFeeINR" name="admissionFormForm" size="10" maxlength="10" onkeypress="return isDecimalNumberKey(this.value,event)"></html:text>
                
               --%>
               
               
               </td>
                <td height="20" class="row-odd"><div align="right"><bean:message key="knowledgepro.hostel.reservation.branchName"/></div></td>
                <td height="20" class="row-even"><html:text property="ddBankCode"  size="15" maxlength="20"></html:text></td>
                <td class="row-odd"><div align="right"><%=dynaMandate%>Issuing Bank</div></td>
                <td height="20"><html:text property="ddIssuingBank"  size="15" maxlength="20"></html:text></td>
              </tr>
            </table></td>
            <td  background="images/right.gif" width="5" height="5"></td>
          </tr>
          <tr>
            <td height="5"><img src="images/04.gif" width="5" height="5"></td>
            <td background="images/05.gif"></td>
            <td><img src="images/06.gif" ></td>
          </tr>
        </table></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr class="DD">
        <td valign="top" background="images/Tright_03_03.gif"></td>
        <td colspan="2" valign="top" class="news"><div align="center">
              <table width="60%" height="27"  border="0" cellpadding="1" cellspacing="2">
                <tr>
                  <td><table width="100%"  border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="48%" height="21"><div align="right"><html:button property="" onclick='<%=submitjsmethod %>' styleClass="formbutton" value="Continue"></html:button> </div></td>
                      <td width="2%"></td>
                      <td width="50%" height="21"><div align="left"><html:button property=""  styleClass="formbutton" value="Reset" onclick="resetApplicationDetails()"></html:button></div></td>
                    </tr>
                  </table></td>
                </tr>
              </table>
            </div></td>
        <td width="9" valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      
      <tr class="OnlinePayment">
        <td height="52" valign="top" background="images/Tright_03_03.gif"></td>
        <td colspan="2" valign="top" class="news"><table width="60%" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td ><img src="images/01.gif" width="5" height="5"></td>
            <td width="914" background="images/02.gif"></td>
            <td><img src="images/03.gif" width="5" height="5"></td>
          </tr>
          <tr>
          
          <td width="5"  background="images/left.gif"></td>
            <td width="100%" height="25" ><table width="100%" height="25" border="0" cellpadding="0" cellspacing="1">
            <logic:equal value="false" name="admissionFormForm" property="paymentSuccess">
            <tr class="row-even">
                <td width="40%" height="20" class="row-odd"><div align="right"><%=dynaMandate%><bean:message key="admissionForm.application.amount.label"/></div></td>
                <td width="60%"  height="20" class="row-even">
                
                <logic:empty name="admissionFormForm" property="applicationAmount">
                <html:text property="applicationAmount1"  size="15" maxlength="8" onkeypress='validate(event)'> </html:text>
                </logic:empty>
                <logic:notEmpty name="admissionFormForm" property="applicationAmount">
                 <html:text property="applicationAmount1" disabled="false" size="15" maxlength="8" onkeypress='validate(event)'> </html:text>
                </logic:notEmpty>
                
                <%-- 
                <logic:equal value="true" name="admissionFormForm" property="indianCandidate">
                <logic:empty name="admissionFormForm" property="applicationAmount">
                <html:text property="applicationAmount"  size="15" maxlength="8"> </html:text>
                </logic:empty>
                <logic:notEmpty name="admissionFormForm" property="applicationAmount">
                 <html:text property="applicationAmount"  size="15" maxlength="8"> </html:text>
                </logic:notEmpty>
                </logic:equal>
                --%>
                <%-- 
                <logic:equal value="false" name="admissionFormForm" property="indianCandidate">
                <logic:empty name="admissionFormForm" property="ddAmount">
                <html:text property="ddAmount"  size="10" maxlength="8"> </html:text>
                </logic:empty>
                <logic:notEmpty name="admissionFormForm" property="ddAmount">
                 <html:text property="ddAmount"  size="10" maxlength="8"> </html:text>
                </logic:notEmpty>&nbsp;&nbsp;
                <nested:select name="admissionFormForm" property="internationalApplnFeeCurrencyId" styleId='internationalApplnFeeCurrencyId' styleClass="comboMedium" style="max-width:10%;" disabled="false" >
												<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
								              	<html:optionsCollection name="admissionFormForm" property="currencyList" label="name" value="id"/>
				</nested:select>&nbsp;&nbsp;
                
                 Equivalent INR&nbsp;&nbsp;
                <html:text property="equivalentCalcApplnFeeINR" name="admissionFormForm" disabled="false" size="10" maxlength="10" onkeypress="return isDecimalNumberKey(this.value,event)"></html:text>
                </logic:equal>
                
                --%>
                
               </td></tr>
            <tr class="row-odd">
            <td colspan="6" width="100%" class="heading" align="center">
            <p>You have chosen to pay online. You will be redirected to an external site for payment. Please click on confirm.<br/>
             NOTE: Your credit card statement will show the transaction as coming from PAY U MONEY.<br/>
             Please quote your Transaction ID for any queries relating to this request. </p></td>
            </tr>
            <tr class="row-odd">
            	<td colspan="6" width="100%" class="heading" align="center">
		            <p>
		            	Candidate should submit the application form within 24 hours of remitting the application fee online, <br/>
		            	else the candidate will have to pay again and fill the application form. The initial amount will be <br/>
		            	refunded within 7 working days.
		            </p>
	            </td>
            </tr>
            
            </logic:equal>
           <logic:equal value="true" name="admissionFormForm" property="paymentSuccess">
            <tr>
            <td colspan="6" height="25" class="row-odd"><div align="center"><b>Your earlier online transaction for application fee was successful. Below are the details of your transaction</b> </div></td>
            </tr>
            <tr height="5px"></tr>
            <tr>
            <td width="30%" height="21" class="row-odd"><div align="right">Transaction Amount:</div></td>
                      <td width="10%" height="21" class="row-even"><div align="left">
                      <bean:write name="admissionFormForm" property="txnAmt" />
                      </div></td>
                      <td width="15%" height="21" class="row-odd"><div align="right">Transaction Ref No:</div></td>
                      <td width="15%" height="21" class="row-even"><div align="left">
                      <bean:write name="admissionFormForm" property="txnRefNo" />
                      </div></td>
                      <td width="15%" height="21" class="row-odd"><div align="right">Transaction Date:</div></td>
                      <td width="15%" height="21" class="row-even"><div align="left">
                      <bean:write name="admissionFormForm" property="txnDate" />
                      </div></td>
            </tr>
            </logic:equal>
            </table></td>
            <td  background="images/right.gif" width="5" height="5"></td>
          </tr>
          <tr>
            <td height="5"><img src="images/04.gif" width="5" height="5"></td>
            <td background="images/05.gif"></td>
            <td><img src="images/06.gif" ></td>
          </tr>
        </table></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      
     <tr class="OnlinePayment">
        <td valign="top" background="images/Tright_03_03.gif"></td>
        <td colspan="2" valign="top" class="news"><div align="center">
              <table width="60%" height="27"  border="0" cellpadding="1" cellspacing="2">
                <tr>
                  <td><table width="100%"  border="0" cellspacing="0" cellpadding="0">
                    <tr>
                    <logic:equal value="false" name="admissionFormForm" property="paymentSuccess">
                      <td width="48%" height="21"><div align="center"><html:button property="" onclick="payOnline()" styleClass="formbutton" value="Pay Online"></html:button></div></td>
                      </logic:equal>
                       <logic:equal value="true" name="admissionFormForm" property="paymentSuccess">
                      <td width="48%" height="21"><div align="center"><html:button property=""  styleClass="formbutton" value="Continue to fill Application" onclick='<%=submitjsmethod %>'></html:button></div></td>
                      </logic:equal>
                    </tr>
                  </table></td>
                </tr>
              </table>
            </div></td>
        <td width="9" valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>   
	 
      
      
       
     
     
      <tr>
        <td valign="top" background="images/Tright_03_03.gif"></td>
        <td colspan="2" valign="top" class="news">&nbsp;</td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
      <td><img src="images/Tright_03_05.gif" height="29" width="9"></td>
        <td colspan="2" background="images/TcenterD.gif" width="100%"></td>
        <td><img src="images/Tright_02.gif" height="29" width="9"></td>        
      </tr>
    </table></td>
  </tr>
</table>
</html:form>
</body>

</html:html>


<script type="text/javascript">
if(document.getElementById("programType")!=null && document.getElementById("programTypeId")!=null){
var programTypeId = document.getElementById("programType").value;
if(programTypeId != null && programTypeId.length != 0) {
	document.getElementById("programTypeId").value = programTypeId;
}
document.getElementById("DDRadio").checked = true;


}	
</script>