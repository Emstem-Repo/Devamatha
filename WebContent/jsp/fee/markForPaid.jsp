<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">


<script type="text/javascript">
function markAsPaid(){
	if(validateCheckBox()) {
	document.getElementById("method").value = "markAsPaid";
	document.feePaidForm.submit();
	}
}

function validateCheckBox() {
	var inputs = document.getElementsByTagName("input");
    var inputObj;
    var checkBoxselectedCount = 0;
    for(var count1 = 0;count1<inputs.length;count1++) {
	    inputObj = inputs[count1];
	    var type = inputObj.getAttribute("type");
	   	if (type == 'checkbox') {
	   		if(inputObj.checked){
	   			checkBoxselectedCount++;
		   	}
		}
    }

    if(checkBoxselectedCount == 0) {
        document.getElementById("err").innerHTML = "Please select at least one record.";
        document.getElementById("errorMessage").innerHTML = "";
    	return false;
    }    
    else { 
        return true;
    }    
            
}
function selectAll(obj) {
	value = obj.checked;
	var inputs = document.getElementsByTagName("input");
    var inputObj;
    var checkBoxselectedCount = 0;
    for(var count1 = 0;count1<inputs.length;count1++) {
	    inputObj = inputs[count1];
	    var type = inputObj.getAttribute("type");
	   	if (type == 'checkbox') {
	   		inputObj.checked = value;
		}
    }
}

function unCheckSelectAll() {
	var inputs = document.getElementsByTagName("input");
    var inputObj;
    var checkBoxOthersSelectedCount = 0;
    var checkBoxOthersCount = 0;
    for(var count1 = 0;count1<inputs.length;count1++) {
	    inputObj = inputs[count1];
	    var type = inputObj.getAttribute("type");
	   	if (type == 'checkbox' && inputObj.id != "checkAll") {
	   		checkBoxOthersCount++;
	   		if(inputObj.checked) {
	   			checkBoxOthersSelectedCount++;
	   		}	
		}
    }
    if(checkBoxOthersCount != checkBoxOthersSelectedCount) {
    	document.getElementById("checkAll").checked = false;
    } else {
    	document.getElementById("checkAll").checked = true;
    }        
}

function clearField(field) {
	if (field.value == "0.0000"){
		field.value = "";
	}
	if(field.value == "0"){
		field.value = "";
	}
}
function checkForEmpty(field) {
	if (field.value.length == 0)
		field.value = "0.0000";
	if (isNaN(field.value)) {
		field.value = "";
	}
}
</script>

<html:form action="/FeePaid">

<html:hidden property="method" styleId="method" value="feePaidSearch"/>
<html:hidden property="formName" value="feePaidForm"/>
<html:hidden property="pageType" value="1"/>

<table width="98%" border="0">
  <tr>
    <td><span class="heading"><bean:message key="knowledgepro.fee"/><span class="Bredcrumbs">&gt;&gt;<bean:message key="knowledgepro.feepays.feepay"/> &gt;&gt;</span></span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="10"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="body" ><div align="left"><strong class="boxheader"><bean:message key="knowledgepro.feepays.feepay"/></strong></div></td>
        <td width="13" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="122" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><div align="center">
              <table width="100%" height="185"  border="0" cellpadding="0" cellspacing="0">
                 <tr>
               	    <td height="20" colspan="6" align="left">
               	    <div align="right" style="color:red"> <span class='MandatoryMark'> <bean:message key="knowledgepro.mandatoryfields"/></span></div>
               	    <div id="err" style="color:red;font-family:arial;font-size:11px;"></div>
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
                    <td height="35" colspan="6" class="body" >
			        <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
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
								<td valign="top" class="row-odd"><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.feepays.startdate"/>:</div></td>
		                           <td class="row-even" align="left">
		                           		<html:text styleId="startDate" property="startDate" readonly="true" styleClass="TextBox"/>
											<script	language="JavaScript">
												new tcal( {
													// form name
													'formname' :'feePaidForm',
													// input name
													'controlname' :'startDate'
												});
											</script>
		                           </td>
		                           <td height="25" valign="top" class="row-odd" ><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.feepays.enddate"/>:</div></td>
		                           <td height="25" class="row-even" align="left">
		                            		<html:text styleId="endDate" property="endDate" readonly="true" styleClass="TextBox"/>
											<script	language="JavaScript">
												new tcal( {
													// form name
													'formname' :'feePaidForm',
													// input name
													'controlname' :'endDate'
												});
											</script>
                                   <br></td>
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
                   </td>
                 </tr>
                 <tr>
                   <td height="35" colspan="6" class="body" >
                   <table width="100%" height="27"  border="0" cellpadding="0" cellspacing="0">
                     <tr>
                       <td width="45%"><div align="right">
	              	 		<html:submit styleClass="formbutton" value="Search"></html:submit>
                       </div></td>
                       <td width="2%"></td>
                       <td width="53%" height="45" align="left">
                   	 		<html:button property="" styleClass="formbutton" value="Reset" onclick="resetFieldAndErrMsgs()"></html:button>
                       </td>
                     </tr>
                   </table>
                   </td>
                </tr>
                <tr>
                   <td height="35" colspan="6" >
                    <logic:notEmpty name="feePaidForm" property="feePaymentList">
					<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
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
							<td class="row-odd"><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.admission.date"/></div></td>
	                           <td class="row-even" align="left" colspan="9">
	                           		<html:text styleId="startDate" property="paidDate" readonly="true" styleClass="TextBox"/>
										<script	language="JavaScript">
											new tcal( {
												// form name
												'formname' :'feePaidForm',
												// input name
												'controlname' :'paidDate'
											});
										</script>
	                           </td>
							</tr>
	                       <tr >
	                           <td width="5%" height="25" class="row-odd"><div align="center"><input type="checkbox" id="checkAll" onclick="selectAll(this)">  Paid</div></td>
		                       <td width="5%" class="row-odd" ><div align="center"><bean:message key="knowledgepro.slno"/></div></td>
		                       <td width="12%" class="row-odd" ><div align="center"><bean:message key="knowledgepro.feepays.appregno"/></div></td>
		                       <td width="12%" class="row-odd" ><div align="center">Class</div></td>
		                       <td width="12%" class="row-odd" ><div align="center"><bean:message key="knowledgepro.admin.name"/></div></td>
		                       <td width="10%" class="row-odd" ><div align="center"><bean:message key="knowledgepro.feepays.billno"/></div></td>
		                       <td width="10%" class="row-odd" ><div align="center"><bean:message key="knowledgepro.feepays.totalamount"/></div></td>
		                       <td width="10%" class="row-odd"><div align="center"><bean:message key="knowledgepro.feepays.challandate"/></div></td><!--
		                       <td width="10%" class="row-odd"><div align="center"><bean:message key="knowledgepro.fee.paid.conversion.rate"/></div></td>

	                       --></tr>
	                       <c:set var="temp" value="0"/>
	                       <nested:iterate name="feePaidForm" property="feePaymentList" type="com.kp.cms.to.fee.FeePaymentTO" indexId="count">
		                       <c:choose>
	                           	 <c:when test="${temp == 0}">
	                           		<tr><nested:hidden property="id"></nested:hidden>
									   <td height="25" class="row-even" ><div align="center"><nested:checkbox styleId="<c:out value='${count}'/>" property="paymentVerified" onclick="unCheckSelectAll()"/></div></td>
				                       <td class="row-even"><div align="center"><c:out value="${count + 1}"/></div></td>
				                       <td class="row-even" ><div align="left"><nested:write  property="registrationNo"/></div></td>
				                       <td class="row-even" ><div align="left"><nested:write  property="className"/></div></td>
				                       <td class="row-even" ><div align="left"><nested:write  property="studentName"/></div></td>
				                       <td class="row-even" ><div align="left"><nested:write  property="billNo"/></div></td>
				                       <td class="row-even" ><div align="left"><nested:write  property="totalFeePaid"/> &nbsp; &nbsp; <nested:write  property="currencyCode"/></div></td>
				                       <td class="row-even" ><div align="center"><nested:write  property="challenPrintedDate"/></div></td>
				                    </tr>
	                      		   <c:set var="temp" value="1"/>
	                   		 	</c:when>
	                    	    <c:otherwise>
			                    <tr><nested:hidden property="id"></nested:hidden> 
			             			   <td height="25" class="row-white" ><div align="center"><nested:checkbox styleId="<c:out value='${count}'/>" property="paymentVerified" onclick="unCheckSelectAll()"/></div></td>
			             			   <td class="row-white"><div align="center"><c:out value="${count + 1}"/></div></td>
				                       <td class="row-white" ><div align="left"><nested:write  property="registrationNo"/></div></td>
				                       <td class="row-white" ><div align="left"><nested:write  property="className"/></div></td>
				                       <td class="row-white" ><div align="left"><nested:write  property="studentName"/></div></td>
				                       <td class="row-white" ><div align="left"><nested:write  property="billNo"/></div></td>
				                       <td class="row-white" ><div align="left"><nested:write  property="totalFeePaid"/></div></td>
				                       <td class="row-white" ><div align="center"><nested:write  property="challenPrintedDate"/></div></td>
				                </tr>
	                    		 <c:set var="temp" value="0"/>
					  	       </c:otherwise>
	                        </c:choose>
	                      </nested:iterate>
	                      <tr>
						   <td height="2" class="row-even" ><div align="center"></div></td>
	                       <td class="row-even"><div align="center"></div></td>
	                       <td class="row-even" ><div align="center"></div></td>
	                       <td class="row-even" ><div align="center"></div></td>
	                       <td class="row-even" ><div align="center"></div></td>
	                       <td class="row-even" ><div align="center"></div></td>
	                       <td class="row-even" ><div align="center"></div></td>
	                       <td class="row-even" ><div align="center"></div></td>
	                     </tr>
	                     <tr>
						   <td height="25" colspan="8" class="row-white" ><div align="center">
						   <html:button property="" styleClass="formbutton" value="Submit"  onclick="markAsPaid()"></html:button>
						   </div></td>
	                      
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
                    </logic:notEmpty>
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
        <td width="100%" background="images/TcenterD.gif"></td>
        <td><img src="images/Tright_02.gif" width="9" height="29"></td>
      </tr>
    </table></td>
  </tr>
</table>


</html:form>
