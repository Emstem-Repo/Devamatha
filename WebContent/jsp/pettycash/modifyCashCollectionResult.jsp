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
var amountId;
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

function getAmount(number) 
{

	document.location.href = "modifyCashCollection.do?method=getAmount&accountId="+number;
		
}
function preparefineList(){
	document.getElementById("method").value = "prepareFineList";	
	document.modifycashCollectionForm.submit();
}
function deleteFeeDetails(id,pcReceiptId) 
{

	deleteConfirm = confirm("Are you sure want to delete this entry?");
	if (deleteConfirm) {
		document.location.href = "modifyCashCollection.do?method=deleteReceiptNumber&accountId="+id
		+"&recNumber="+ pcReceiptId;
		//document.cashCollectionForm.submit();
	}
}
function updateCashCollection() {
	document.getElementById("method").value = "updateCashCollection";	
	
	}
function updateAndPrint()
{
	document.getElementById("method").value = "updateCashCollection";
	document.getElementById("flagId").value = "false";
	document.modifycashCollectionForm.submit();	
}

function getAmount1(accId,amtId) 
{
	amountId=amtId;
	alert("1111",amountId);
		var args ="method=getAmount&accoId="+accId;
		var url ="AjaxRequest.do";
		requestOperation(url,args,updateAmount);
}

function updateAmount(req) {
	 var pos;
	 var isfixed;
	var responseObj = req.responseText;
	   if(responseObj.indexOf('true')!= -1)
			   {
		pos = responseObj.indexOf('true');
		 isfixed = responseObj.substr(pos,pos+5);
		   alert("pos is"+pos);
		   alert("is fixed is"+isfixed);
		   }
	   else
	   {
		  pos = responseObj.indexOf('false');
		  isfixed = responseObj.substr(pos,pos+6);
		   alert("pos is"+pos);
		   alert("is fixed"+isfixed);
		   }
 var amount =responseObj.substring(0,pos);
 if(isfixed=='true')
 {
	 alert("in true");
	 document.getElementById(amountId).readonly='readonly';
 }
 

	if(document.getElementById(amountId))
	{
		document.getElementById(amountId).value=amount;
	}
}
function abc(){
	alert("Fixed amount.You cannot edit!");
}

</script>
</head>
<html:form action="modifyCashCollection">
<html:hidden property="method" styleId="method" value=""/>
<html:hidden property="flag" styleId="flagId" value="true"/>
<html:hidden property="pageType" value="2"/>
<html:hidden property="formName" value="modifycashCollectionForm" />
<table width="100%" border="0">
  <tr>
    <td><span class="heading">Petty Cash &gt;&gt; <bean:message key="knowledgepro.petticash.modifyCashCollection"/></span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="body" ><strong class="boxheader"><bean:message key="knowledgepro.petticash.modifyCashCollection"/></strong></td>
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
            <tr>
              	
                 <td width="22%" class="row-odd"><div align="right"><bean:message key="knowledgepro.petticash.recieptNo"/></div></td>
                <td width="22%" class="row-even" colspan="3"><bean:write name="modifycashCollectionForm" property="recNoResult"/></td>
             
                <td width="22%"  class="row-odd" ><div align="right"><bean:message key="knowledgepro.petticash.date"/></div></td>
                <td class="row-even" colspan="3"><bean:write name="modifycashCollectionForm" property="paidDate"/></td>
                  
                  <tr>
              	
                 <td width="22%" class="row-odd"><div align="right"><bean:message key="knowledgepro.petticash.time"/></div></td>
                <td class="row-even" colspan="3"><bean:write name="modifycashCollectionForm" property="hours"/> :
                  <bean:write name="modifycashCollectionForm" property="minutes"/></td>
             
              	
               <td width="23%" class="row-odd"><div align="right"><bean:message key="knowledgepro.petticash.AppRegRollNumber"/></div></td>
                <td class="row-even" colspan="3"><bean:write name="modifycashCollectionForm" property="appRegRollno"/>
                 <c:choose>
                <c:when test="${modifycashCollectionForm.appNo == 'appNo'}">
                 <html:radio name="modifycashCollectionForm" property="appNo" styleId="optional_1" value="true"/> App. No
				</c:when>
				<c:otherwise>
				<html:radio name="modifycashCollectionForm" property="appNo" styleId="optional_1" value="false" disabled="true" /> App. No
				</c:otherwise>
				
				</c:choose>
				<c:choose>
                 <c:when test="${modifycashCollectionForm.appNo =='rollNo'}">
                 <html:radio name="modifycashCollectionForm" property="appNo" styleId="optional_2" value="true"/> Roll No
				</c:when>
				<c:otherwise>
				<html:radio name="modifycashCollectionForm" property="appNo" styleId="optional_2" value="false" disabled="true"/> Roll No
				</c:otherwise>
				</c:choose>
				<c:choose>
                  <c:when test="${modifycashCollectionForm.appNo == 'regNo'}">
                  <html:radio name="modifycashCollectionForm" property="appNo" styleId="optional_3" value="true"/> Reg. No
                 </c:when>
                 <c:otherwise>
                  <html:radio name="modifycashCollectionForm" property="appNo" styleId="optional_3" value="false" disabled="true"/> Reg. No
                 </c:otherwise>
                 </c:choose>
                
                  </td>
                  
                  
              </tr>
              <tr>
              	 <td width="22%" class="row-odd"><div align="right"><bean:message key="knowledgepro.petticash.academicYear"/></div></td>
                  <td class="row-even" colspan="3"><bean:write name="modifycashCollectionForm" property="academicYear"/></td>
                 <td width="22%" class="row-odd"><div align="right"><bean:message key="knowledgepro.petticash.studentName"/></div></td>
                <td class="row-even"> <bean:write name="modifycashCollectionForm" property="nameOfStudent"/></td>
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
                    <td  height="25" colspan="2" class="row-odd" ><bean:message key="knowledgepro.petticash.accountCode"/></td>
                    <td  class="row-odd" align="left" >Amount:</td>
                    <td  class="row-odd" align="left" >Details:</td>
                    </tr>
                  <tr >
                    <td height="70" colspan="2" class="row-even" ><html:text property="fineType" name="modifycashCollectionForm" size="45"  styleId="searchItem"  onkeyup="itemSearch(this.value)"/>
                   <br>
					
                 	<nested:select property="accountId" styleId="accountCodeId" styleClass="body" size="4" onclick ="getAmount(this.value,'amount')" style="width:300px;">
						<logic:notEmpty name="modifycashCollectionForm" property="accNameWithCode">
						<nested:optionsCollection name="modifycashCollectionForm" property="accNameWithCode" label="nameWithCode"  value="id" styleClass="comboBig"/>
					</logic:notEmpty>
					</nested:select>
					
					
					</td>
                    <td  class="row-even">
                    <c:if test="${modifycashCollectionForm.isfixed==true}">
                    <html:text property="amount" name="modifycashCollectionForm" size="7" maxlength="7" styleId="amount" readonly="true" onclick="abc()"/>
                  	</c:if>
                  	<c:if test="${modifycashCollectionForm.isfixed!=true}">
                    <html:text property="amount" name="modifycashCollectionForm" size="7" maxlength="7" styleId="amount"/>
                  	</c:if>
                  	 </td>
                  	 <td class="row-even">
                  	 	<html:text property="details" name="modifycashCollectionForm" size="7" maxlength="15" styleId="details"/>
                  	 </td>
                    </tr>
                    <tr >
                    <td colspan="2" class="row-even" align="center"><html:button property="" styleClass="formbutton" value="Add" onclick="preparefineList()"/></td>
                   <td height="25" colspan="2" class="row-even" >&nbsp;</td>
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
                     <td height="25" class="row-odd" align="center">Account  Code</td>
                      <td   class="row-odd" align="center">Account Name</td>
                      <td   class="row-odd" align="center">Details</td>
                      <td  class="row-odd" align="center">Amount</td>
                      <td  class="row-odd" align="center">Delete</td>
                    </tr>
                    <tr >
                   										<c:set var="temp" value="0" />
										<logic:notEmpty name="modifycashCollectionForm" property="accountHeadListToDisplay">
											<logic:iterate id="details" name="modifycashCollectionForm" property="accountHeadListToDisplay" indexId="count">
												
												<c:choose>
													<c:when test="${temp == 0}">
														<tr>
															
															<td height="25" class="row-even"
																align="center"><bean:write name="details" property="accCode" /></td>
															<td class="row-even" align="center"><bean:write
																name="details" property="accName" /></td>
															<td class="row-even" align="center"><bean:write
																name="details" property="details" /></td>
															<td   class="row-even" align="center"><bean:write
																name="details" property="amount" /></td>
															
															<td width="14%" height="25" class="row-even">
															<div align="center"><img
																src="images/delete_icon.gif" width="16" height="16" style="cursor:pointer"
																onclick="deleteFeeDetails('<bean:write name="details" property="accid" />','<bean:write name="modifycashCollectionForm" property="recNumber" />')"></div>
															</td>
														</tr>
	
														<c:set var="temp" value="1" />
													</c:when>
													<c:otherwise>
														<tr>
															<td  height="25" class="row-even"
																align="center"><bean:write name="details" property="accCode" /></td>
															<td  class="row-even" align="center"><bean:write
																name="details" property="accName" /></td>
															<td class="row-even" align="center"><bean:write
																name="details" property="details" /></td>
															<td  class="row-even" align="center"><bean:write
																name="details" property="amount" /></td>
															
															<td width="14%" height="25" class="row-even">
															<div align="center"><img
																src="images/delete_icon.gif" width="16" height="16" style="cursor:pointer"
																onclick="deleteFeeDetails('<bean:write name="details" property="accid" />','<bean:write name="modifycashCollectionForm" property="recNumber" />')"></div>
															</td>
														</tr>
														
														<c:set var="temp" value="0" />
													</c:otherwise>
												</c:choose>
											</logic:iterate>
										</logic:notEmpty>
										
                    </tr>
                   
                   
                    <tr >
                    <td height="15" class="row-white" >&nbsp;</td>
                    <td height="30" class="row-white" align="right" ><div align="right"></div>Total</td>
                    <td colspan="2" class="row-white" ><html:text property="total" name="modifycashCollectionForm"  styleId="total" maxlength="10"/></td>
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
            <td width="49%" height="35" align="right"><html:submit property="" styleClass="formbutton" value="Update & Print" onclick="updateCashCollection()"/>
           </td>
           <td width="2%"/>
           <td width="49%" height="35" align="left"><html:button property="" styleClass="formbutton" value="Update" onclick="updateAndPrint()"/>
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