<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>

<head>
<title>:: CMS ::</title>
<script language="JavaScript" src="js/calendar_us.js"></script>
<script language="JavaScript" src="js/admission/interviewprocess.js"></script>
<link href="css/styles.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="css/calendar.css">
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 5px;
	margin-right: 0px;
	margin-bottom: 0px;
}
-->
</style>
<script type="text/javascript">




var destId;

function getName(number,nameId) 
{
	destId=nameId;	
	var appRegRollno=document.getElementById("appRegRollno").value;
	var academicYear = document.getElementById("financialYearId").value;
	if(appRegRollno!='' && number!='')
	{
		var args ="method=getStudentName&appRegRollno="+appRegRollno+"&optionNo="+number+"&academicYear="+academicYear;
		var url ="AjaxRequest.do";
		requestOperation(url,args,updateStudentName);	
	}
	else
	{
        alert("Please enter App/Reg or Roll No");
		document.getElementById("optional_1").checked="";
		document.getElementById("optional_2").checked="";
		document.getElementById("optional_3").checked="";
		document.getElementById(destId).value ='';
		document.getElementById("appRegRollno").value='';
	}	
}
function getNameByApp(nameId) 
{
	destId=nameId;	
	var appRegRollno=document.getElementById("appRegRollno").value;
	var academicYear = document.getElementById("financialYearId").value;
	if(appRegRollno!=''&& document.getElementById("optional_3").checked)
	{
		var args ="method=getStudentName&appRegRollno="+appRegRollno+"&optionNo="+3+"&academicYear="+academicYear;
		var url ="AjaxRequest.do";
		requestOperation(url,args,updateStudentName);	
	}
}
function updateStudentName(req) {
	var responseObj = req.responseText;
if(responseObj.length =='12')
{
	alert("This not a valid Roll/Reg/App number");
	document.getElementById("optional_1").checked="";
	document.getElementById("optional_2").checked="";
	document.getElementById("optional_3").checked=true;
	document.getElementById("appRegRollno").value='';
}
	if(document.getElementById(destId))
	{
		document.getElementById(destId).value=responseObj;
	}
}
var amountId;
var amount;
var isfixed;
function getAmount1(accId,amtId) 
{
	amountId=amtId;
	
		var args ="method=getAmount&accoId="+accId;
		var url ="AjaxRequest.do";
		requestOperation(url,args,updateAmount);
}

function updateAmount(req) {
	 var pos;
	
	var responseObj = req.responseText;
	   if(responseObj.indexOf('true')!= -1)
			   {
		pos = responseObj.indexOf('true');
		 isfixed = responseObj.substr(pos,pos+5);
		   }
	   else
	   {
		  pos = responseObj.indexOf('false');
		  isfixed = responseObj.substr(pos,pos+6);
		   }

	amount =responseObj.substring(0,pos);

	
	
	if(document.getElementById(amountId))
	{
		document.getElementById("dynaAmount").innerHTML="<input type='text' name='amount' size='7' maxlength='7' value="+amount+" id='amountid' onclick='abc()'>";
	}
}

function abc()
{
	
	if(isfixed.length == 6)
	{
	alert("Fixed amount.You cannot edit!");
	document.getElementById("amountid").readOnly = true;
		}
	
}
function itemSearch(searchValue){
	
	var sda = document.getElementById("accountCodeId");
	var len = sda.length;
	var searchValueLen = searchValue.length;
	for(var m =0; m<len; m++){
		sda.options[m].selected = false;		
	}
	for(var j=0; j<len; j++)
	{
		for(var i=0; i<len; i++){
			if(((sda.options[i].text).substring(0, searchValueLen).toUpperCase())  == (searchValue.toUpperCase())){
				sda.options[i].selected = true;
				break;
			}
		}
	}
}

function deleteFeeDetails(id) {
	deleteConfirm = confirm("Are you sure want to delete this entry?");
	if (deleteConfirm) {
		document.location.href = "CashCollection.do?method=deleteReceiptNumber&accId="+id;
	}
}
	function showEmpty(){		
		document.getElementById("optional_1").checked="";
		document.getElementById("optional_2").checked="";
		document.getElementById("optional_3").checked="";
		}
				
	function preparefineList(){
		document.getElementById("method").value = "prepareFineList";	
		document.cashCollectionForm.submit();
	}

	function saveAndPrintCashCollection() {
	document.getElementById("method").value = "saveCashCollection";	
	document.getElementById("flagId").value = "false";
	document.cashCollectionForm.submit();	
	}
	function saveCashCollection() 
	{
	document.getElementById("method").value = "saveCashCollection";	
	}	
	function onChangeYear(){
		var number=document.getElementById("optional_1").value;
		number=document.getElementById("optional_2").value;
		number=document.getElementById("optional_3").value;
		var appRegRollno=document.getElementById("appRegRollno").value;
		var academicYear = document.getElementById("financialYearId").value;
		if(appRegRollno!='' && number!='')
		{
			var args ="method=getStudentName&appRegRollno="+appRegRollno+"&optionNo="+number+"&academicYear="+academicYear;
			var url ="AjaxRequest.do";
			requestOperation(url,args,updateStudentName);	
		}
	}
</script>
</head>
<html:form action="CashCollectionNew">
<html:hidden property="method" styleId="method" value=""/>
<html:hidden property="flag" styleId="flagId" value="true"/>
<html:hidden property="pageType" value="1"/>
<html:hidden property="formName" value="cashCollectionForm" />
<table width="100%" border="0">
   <tr>
    <td><span class="heading"><span class="Bredcrumbs">Petty Cash &gt;&gt; <bean:message key="knowledgepro.petticash.cashCollection"/>&gt;&gt;</span></span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="heading_white" ><bean:message key="knowledgepro.petticash.cashCollection"/></td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><div align="right"><span class='MandatoryMark'><bean:message
					key="knowledgepro.mandatoryfields" /></span></div>
			<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
				<FONT color="green"><html:messages id="msg" property="messages" message="true">
					<c:out value="${msg}" escapeXml="false"></c:out><br>
					</html:messages> </FONT></div></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td ><img src="images/01.gif" width="5" height="5" /></td>
            <td width="914" background="images/02.gif"></td>
            <td><img src="images/03.gif" width="5" height="5" /></td>
          </tr>
          <tr>
            <td width="5"  background="images/left.gif"></td>
            <td valign="top"><table width="100%" cellspacing="1" cellpadding="2"><tr><td width="34%" class="row-even"><table width="100%" cellspacing="1" cellpadding="2">
            <tr><!--
              	
                 <td width="23%" height="25" class="row-odd" ><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.petticash.recieptNo"/></div></td>
                  <td class="row-even" colspan="3">
                  <html:text name="cashCollectionForm" property="number" styleId="numberId" size="16" maxlength="20" readonly="true"></html:text>
                  
                  </td>
             
                 -->
                 <td width="22%" class="row-odd" ><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.petticash.date"/></div></td>
                  <td width="30%" class="row-even" ><table width="82" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                   
					  <td width="60"><html:text name="cashCollectionForm" property="paidDate" styleId="paidId" size="10" maxlength="12" readonly="true"/></td>
                      <td width="40"><script language="JavaScript">
							new tcal ({
								// form name
								'formname': 'cashCollectionForm',
								// input name
								'controlname': 'paidDate'
							});</script>
					  </td>
					  
                    </tr>
                  </table></td>
                   <td width="22%" class="row-odd"><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.petticash.time"/></div></td>
                <td class="row-even" colspan="3"><html:text name="cashCollectionForm" property="hour" styleId="hourId" size="6" maxlength="6" readonly="true"/> :
                  <html:text name="cashCollectionForm" property="minutes" styleId="minutesId" size="6" maxlength="6" readonly="true"/></td>
                </tr>
              	<tr>
              	
                 <td width="23%" class="row-odd" colspan="2"><div align="right"><bean:message key="knowledgepro.petticash.AppRegRollNumber"/></div></td>
                <td class="row-even" colspan="5"><html:text name="cashCollectionForm" property="appRegRollno" styleId="appRegRollno" size="9" maxlength="9" onchange="getNameByApp('Stname')"/> 
                 <html:radio name="cashCollectionForm" property="appNo" styleId="optional_1" value="1" onclick="getName(this.value,'Stname')"/> App. No
                 <html:radio name="cashCollectionForm" property="appNo" styleId="optional_2" value="2" onclick="getName(this.value,'Stname')" /> Roll No
                  <html:radio name="cashCollectionForm" property="appNo" styleId="optional_3" value="3" onchange="getName(this.value,'Stname')"/> Reg. No
                  </td>
              </tr>
             
              <tr>
              <td width="23%" class="row-odd"><div align="right"><span class="Mandatory">*</span>Name:</div></td>
                <td class="row-even">
               <html:text name="cashCollectionForm" property="name" maxlength="30" size="30" styleId="Stname"/>
                </td>
              	 <td width="22%" class="row-odd"><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.fee.academicyear"/></div></td>
                  <td class="row-even" colspan="3">
                  <input type="hidden" id="tempyear" name="tempyear" value="<bean:write name="cashCollectionForm" property="finYearId"/>" />
                  <html:select name="cashCollectionForm" property="finYearId" styleId="financialYearId" style="width:165px" onchange="onChangeYear()">
						<html:option value=" "><bean:message key="knowledgepro.admin.select"/></html:option>
						<cms:renderYear></cms:renderYear>
				  </html:select>
              </tr>             
                           
            </table></td>
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
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
        <tr>
        <td height="10" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news">&nbsp;</td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      
      
      
     
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
            <tr>
              <td ><img src="images/01.gif" width="5" height="5" /></td>
              <td width="914" background="images/02.gif"></td>
              <td><img src="images/03.gif" width="5" height="5" /></td>
            </tr>
            
            <tr>
              <td width="5"  background="images/left.gif"></td>
              <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
                  <tr >
                    <td  height="25" colspan="2" class="row-odd" width="50%"><bean:message key="knowledgepro.petticash.accountCode"/></td>
                    <td colspan="2" class="row-odd" align="left" width="50%">Amount:</td>
                    </tr>
                  <tr >
                    <td height="25" colspan="2" class="row-even" width="50%"><html:text property="fineType" name="cashCollectionForm" styleId="searchItem" size="45" onkeyup="itemSearch(this.value)"/>
                   <br>
                 	<nested:select property="accountId" styleId="accountCodeId" styleClass="body" size="4" onclick ="getAmount1(this.value,'amountid')" style="width:300px;">
						<logic:notEmpty name="cashCollectionForm" property="accNameWithCode">
						<nested:optionsCollection name="cashCollectionForm" property="accNameWithCode" label="nameWithCode" value="id" styleClass="comboBig"/>
					</logic:notEmpty>
					</nested:select>
					</td>
                    <td height="25" colspan="2" class="row-even" align="left" width="50%">
						<div id="dynaAmount">
                    <html:text property="amount" name="cashCollectionForm" styleId="amountid" size="7" maxlength="7"/>
						</div>
                  	 </td>
                    </tr>
                    <tr >
                     <td colspan="4" class="row-even" align="center"><html:button property="" styleClass="formbutton" value="Add" onclick="preparefineList()"/></td>
                   
                    </tr>
                    
                     <tr >
                      <td height="25" colspan="2" class="row-white" >&nbsp;</td>
                      <td colspan="2" class="row-white" >&nbsp;</td>
                    </tr>
                    </table>
                
                     </td>
                      <td width="5"  background="images/right.gif"></td>
                     </tr>
                     <tr>
            		  <td width="5"  background="images/left.gif"></td>
             		 <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
             		  <tr >
             		  <td width="29%" height="25" class="row-odd" >Account  Code</td>
                      <td width="31%" class="row-odd" >Account Name</td>
                      <td width="26%" class="row-odd">Amount</td>
                      <td width="14%" class="row-odd">Delete</td>
                    </tr>
            					<c:set var="temp" value="0" />
										
					<logic:notEmpty name="cashCollectionForm" property="accountList">
											<logic:iterate id="details" name="cashCollectionForm" property="accountList" indexId="count">
												
												<c:choose>
													<c:when test="${temp == 0}">
														<tr>
															
															<td width="29%" height="25" class="row-even"
																align="center"><bean:write name="details" property="accountCode" /></td>
															<td width="31%" class="row-even" align="center"><bean:write
																name="details" property="accName" /></td>
															<td width="26%" class="row-even" align="center"><bean:write
																name="details" property="amount" /></td>
															
															<td width="14%" height="25" class="row-even">
															<div align="center"><img
																src="images/delete_icon.gif" width="16" height="16" style="cursor:pointer"
																onclick="deleteFeeDetails('<bean:write name="details" property="id"/>')"></div>
															</td>
														</tr>
	
														<c:set var="temp" value="1" />
													</c:when>
													<c:otherwise>
														<tr>
															
															<td width="29%" height="25" class="row-even"
																align="center"><bean:write name="details" property="accountCode" /></td>
															<td width="31%" class="row-even" align="center"><bean:write
																name="details" property="accName" /></td>
															<td width="26%" class="row-even" align="center"><bean:write
																name="details" property="amount" /></td>
															
															<td width="14%" height="25" class="row-even">
															<div align="center"><img
																src="images/delete_icon.gif" width="16" height="16" style="cursor:pointer"
																onclick="deleteFeeDetails('<bean:write name="details" property="id"/>')"></div>
															</td>
														</tr>
														
														<c:set var="temp" value="0" />
													</c:otherwise>
												</c:choose>
											</logic:iterate>
										</logic:notEmpty>				
										
										
                    <tr >
                    <td height="15" class="row-white" >&nbsp;</td>
                    <td height="30" class="row-white" align="right"><div align="right">Total</div></td>
                    <td colspan="2" class="row-white" ><html:text property="total" name="cashCollectionForm"  styleId="total" maxlength="10"/></td>
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
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
       <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td class="heading"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
        <tr>
            <td width="49%" height="35" align="right"><html:submit property="" styleClass="formbutton" value="Save & Print" onclick="saveCashCollection()"/>
           </td>
           <td width="2%"/>
           <td width="49%" height="35" align="left"><html:button property="" styleClass="formbutton" value="Save" onclick="saveAndPrintCashCollection()"/>
           </td>
            <td width="2%" height="35" align="center"> </td>
            <td width="49%" height="35" align="left">&nbsp;</td>
          </tr>
          
        </table></td>
        <td valign="top" background="images/Tright_3_3.gif" ></td>
      </tr>
      <tr>
        <td height="10" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news">&nbsp;</td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      
      <tr>
        <td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
        <td width="0" background="images/TcenterD.gif"></td>
        <td><img src="images/Tright_02.gif" width="9" height="29"></td>
      </tr>
    </table></td>
  </tr>
</table>
</html:form> 
<script type="text/javascript">
var print = "<c:out value='${cashCollectionForm.printReceipt}'/>";
if(print.length != 0 && print == "true") {
	var url ="CashCollection.do?method=printReceiptAfterSave";
	myRef = window.open(url,"challan_details","left=20,top=20,width=800,height=500,toolbar=1,resizable=0,scrollbars=1");
	
}
var year = document.getElementById("tempyear").value;
if (year.length != 0) {
	document.getElementById("financialYearId").value = year;
}
</script>