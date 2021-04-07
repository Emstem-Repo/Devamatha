<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<head>
<title>:: CMS ::</title>
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
</style>
<script type="text/javascript">
function dleteAccountHead(id)
{
	
	var deleteConfirm=confirm("Are you Sure you want to delete this entry?");
	if(deleteConfirm)
	{
		//document.location.href="pettyCashAccHeads.do?method=deletePettyCashAccountHead&id="+ id;
		document.getElementById("method").value="deletePettyCashAccountHead";
		document.getElementById("id").value=id;
		document.pettyCashAccHeadForm.submit();
	}
}

function editAccountHead(id)
{
		//document.location.href="pettyCashAccHeads.do?method=editPettyCashAccountHead&id="+ id;
	document.getElementById("method").value="editPettyCashAccountHead";
	document.getElementById("id").value=id;
	document.pettyCashAccHeadForm.submit();
}

function updateAccountHead(id)
{
		//document.location.href="pettyCashAccHeads.do?method=editPettyCashAccountHead&id="+ id;
	document.getElementById("method").value="manageAccountHead";
	document.getElementById("id").value=id;
	document.pettyCashAccHeadForm.submit();
}
function cancel(){
	document.location.href = "pettyCashAccHeads.do?method=initAccountHeadGroup";
}
</script>
</head>
	<html:form action="pettyCashAccHeads" method="POST">
	<html:hidden property="formName" value="pettyCashAccHeadForm" styleId="formName" />
	<html:hidden property="pageType" value="1" styleId="pageType" />
	<html:hidden property="method" styleId="method" value="manageAccountHead" />
	<html:hidden property="id"  styleId="id" value=""/>
<table width="98%" border="0">
  <tr>
    <td><span class="heading"><bean:message key="knowledgepro.pettycash"/> <span class="Bredcrumbs">&gt;&gt;<bean:message key="knowledgepro.pettycash"/>Account Heads&gt;&gt;</span></span></td>
  </tr>
  <tr>
    <td valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="10"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="body" ><div align="left"><strong class="boxheader"><bean:message key="knowledgepro.pettycash.accheads"/></strong></div></td>
        <td width="13" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><div align="right" class="mandatoryfield">
          <div align="right" class="mandatoryfield"><FONT color="red"> <span class='MandatoryMark'>* Mandatory Fields</span></FONT></div>
          <div id="errorMessage" align="left">
                       <FONT color="red"><html:errors/></FONT>
                       <FONT color="green">
							<html:messages id="msg" property="messages" message="true">
									<c:out value="${msg}" escapeXml="false"></c:out><br>
							</html:messages>
					  </FONT>
			</div>
        </div></td>
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
                    <td width="29%" height="25" class="row-odd" ><div align="right"><span class="mandatoryfield">*</span><bean:message key="knowledgepro.pettycash.accheads.accCode"/>:</div></td>
                    <td width="24%" height="25" class="row-even" > <html:text name="pettyCashAccHeadForm"  property="accCode" styleClass="TextBox" styleId="name" size="16" maxlength="20"/></td>
                    <td width="22%" class="row-odd"><div align="right"><span class="mandatoryfield">*</span><bean:message key="knowledgepro.pettycash.accheads.accname"/>:</div></td>
                     <td width="25%" class="row-even"> <html:text  name="pettyCashAccHeadForm"  property="accName" styleClass="TextBox" styleId="name" size="16" maxlength="45"/></td>
                  </tr>
                  <tr >
                    <td width="29%" height="25" class="row-odd" ><div align="right"><span class="mandatoryfield">*</span><bean:message key="knowledgepro.pettycash.accheads.bankAccNo"/>: </div></td>
                    <td width="24%" height="25" class="row-even" >
                    <html:select property="pcBankAccNumberId" name="pettyCashAccHeadForm"
												styleClass="body" styleId="accountid">
                                  <html:option value="" >
                                    <bean:message key="knowledgepro.select" />
                                  </html:option>  
                                  <logic:notEmpty name="pettyCashAccHeadForm" property="pcAccountNumberTOList">
                                  <html:optionsCollection name="pettyCashAccHeadForm" label="bankAccountNo" property="pcAccountNumberTOList"
													value="id" />
													</logic:notEmpty>
                    </html:select>
                    </td>
                     <td class="row-odd"><div align="right"><bean:message key="knowledgepro.pettycash.accheads.amount"/>:</div></td>
                     <td class="row-even"> <html:text property="amount" styleClass="TextBox" styleId="name" size="16" maxlength="8"/></td>
                  </tr>
                  <tr>
                    <td width="29%" height="25" class="row-odd" ><div align="right"><bean:message key="knowledgepro.pettycash.accheads.fixedamount"/>: </div></td>
                    <td width="24%" height="25" class="row-even" ><html:checkbox property="isFixedAmount"/> </td>
                     <td class="row-odd"><div align="right"><bean:message key="knowledgepro.pettycash.accheads.group"/>:</div></td>
                     <td class="row-even">
                     
                     <html:select property="pcAccHeadGroupCodeId" name="pettyCashAccHeadForm"
												styleClass="body" styleId="groupcodeid">
                                  <html:option value="">
                                    <bean:message key="knowledgepro.select" />
                                  </html:option>
                                  <logic:notEmpty name="pettyCashAccHeadForm" property="pcAccountHeadGroupCodeTOList">
                                  <html:optionsCollection name="pettyCashAccHeadForm" property="pcAccountHeadGroupCodeTOList" label="groupName" value="id" />
                                  </logic:notEmpty>
                      </html:select>
                     
                     
                     </td>
                    </tr>
                    <tr>
                    <td width="29%" height="25" class="row-odd" ><div align="right"><bean:message key="knowledgepro.admin.program"/>: </div></td>
                    <td width="25%" height="25" class="row-even"><span	class="star"> 
                     <html:select property="programName" styleClass="comboLarge" styleId="programName"><html:option value="">
                    <bean:message key="knowledgepro.select" /></html:option>
				   <html:optionsCollection name="programList" label="name" value="id" /></html:select>
				   </span></td>
				   <td class="row-odd">
				   <td class="row-even">
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
        <td valign="top" class="news"><form name="formbutton" method="post" action="">
            <table width="100%"  border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="45%" height="27"><div align="right">
                     <c:choose>
							<c:when test="${task=='Update'}">
				<input type="button"  class="formbutton" value="Update" onclick='updateAccountHead("<bean:write name='pettyCashAccHeadForm' property='id'/>")'>
							</c:when>
							<c:otherwise>
							<html:submit styleClass="formbutton"
										styleId="button"><bean:message key="knowledgepro.admin.add"/>
							</html:submit>					
							</c:otherwise>
							</c:choose> 
                </div></td>
                <td width="2%"></td>
                <td width="53%">
                <c:choose>
                           	 <c:when test="${task=='Update'}">
                           	 		<html:button property="" value="Reset" styleClass="formbutton" ></html:button>
                           	 </c:when>
                           	 <c:otherwise>
                           	 		<html:button property="" styleClass="formbutton" value="Reset" onclick="resetFieldAndErrMsgs()" ></html:button>
                           	 </c:otherwise>
                  </c:choose> 
                </td>
              </tr>
            </table>
        </form></td>
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
                    <td height="25" class="row-odd" ><div align="center"><bean:message key="knowledgepro.pettycash.accheads.slno"/> </div></td>
                    <td height="25" class="row-odd" ><div align="center"><bean:message key="knowledgepro.pettycash.accheads.accCode"/></div></td>
                    <td height="25" class="row-odd"><div align="center"><bean:message key="knowledgepro.pettycash.accheads.accname"/></div></td>
                    <td height="25" class="row-odd"><div align="center"><bean:message key="knowledgepro.pettycash.accheads.bankAccNo"/></div></td>
                    <td height="25" class="row-odd"><div align="center"><bean:message key="knowledgepro.pettycash.accheads.amount"/></div></td>
                    <td height="25" class="row-odd"><div align="center"><bean:message key="knowledgepro.pettycash.accheads.fixedamount"/></div></td>
                    <td height="25" class="row-odd"><div align="center"><bean:message key="knowledgepro.pettycash.accheads.group"/></div></td>
                    <td height="25" class="row-odd"><div align="center"><bean:message key="knowledgepro.admin.program"/></div></td>
                    <td height="25" class="row-odd"><div align="center"><bean:message key="knowledgepro.edit"/></div></td>
                    <td height="25" class="row-odd"><div align="center"><bean:message key="knowledgepro.delete"/></div></td>
                  </tr>
                <logic:notEmpty name="pettyCashAccHeadForm" property="pcAccountHeadTOList">
                  <logic:iterate id="accountHeads" name="pettyCashAccHeadForm" property="pcAccountHeadTOList"  indexId="count"> 
                   <c:choose>
	               <c:when test="${temp == 0}">                 
	           <tr>
				<td width="5%" height="25" class="row-even"><div align="center"><c:out value="${count + 1}"/></div></td>
				 <td width="8%" height="25" class="row-even" ><div align="center"><bean:write name="accountHeads" property="accCode"/></div></td>
				 <td width="19%" height="25" class="row-even" ><div align="center"><bean:write name="accountHeads" property="accName"/></div></td>
				 <td width="8%" height="25" class="row-even" ><div align="center"><bean:write name="accountHeads" property="bankAccNo"/></div></td>
				 <td width="8%" height="25" class="row-even" ><div align="center"><bean:write name="accountHeads" property="amount"/></div></td>
				 <td width="7%" height="25" class="row-even" ><div align="center"><bean:write name="accountHeads" property="isActives"/></div></td>
				 <td width="17%" height="25" class="row-even" ><div align="center"><bean:write name="accountHeads" property="groupName"/></div></td>
				 <td width="18%" height="25" class="row-even" ><div align="center"><bean:write name="accountHeads" property="programName"/></div></td>
				 <td width="5%" height="25" class="row-even" ><div align="center"><img src="images/edit_icon.gif" width="16" style="cursor:pointer" height="18" onclick="editAccountHead('<bean:write name="accountHeads" property="id"/>')"></div></td>
				 <td width="5%" height="25" class="row-even" ><div align="center"><img src="images/delete_icon.gif" width="16" style="cursor:pointer" height="16" onclick="dleteAccountHead('<bean:write name="accountHeads" property="id"/>')"></div></td>
				 </tr>
				 <c:set var="temp" value="1"/>
				 </c:when>
				 <c:otherwise>
				 <tr>
				 <td width="5%" height="25" class="row-white"><div align="center"><c:out value="${count + 1}"/></div></td>
				 <td width="8%" height="25" class="row-white" ><div align="center"><bean:write name="accountHeads" property="accCode"/></div></td>
				 <td width="19%" height="25" class="row-white" ><div align="center"><bean:write name="accountHeads" property="accName"/></div></td>
				 <td width="8%" height="25" class="row-white" ><div align="center"><bean:write name="accountHeads" property="bankAccNo"/></div></td>
				 <td width="8%" height="25" class="row-white" ><div align="center"><bean:write name="accountHeads" property="amount"/></div></td>
				 <td width="7%" height="25" class="row-white" ><div align="center"><bean:write name="accountHeads" property="isActives"/></div></td>
				 <td width="17%" height="25" class="row-white" ><div align="center"><bean:write name="accountHeads" property="groupName"/></div></td>
				 <td width="18%" height="25" class="row-white" ><div align="center"><bean:write name="accountHeads" property="programName"/></div></td>
				 <td width="5%" height="25" class="row-white" ><div align="center"><img src="images/edit_icon.gif" width="16" style="cursor:pointer" height="18" onclick="editAccountHead('<bean:write name="accountHeads" property="id"/>')"></div></td>
				 <td width="5%" height="25" class="row-white" ><div align="center"><img src="images/delete_icon.gif" width="16" style="cursor:pointer" height="16" onclick="dleteAccountHead('<bean:write name="accountHeads" property="id"/>')"></div></td>
				 </tr>
				  <c:set var="temp" value="0"/>
				 </c:otherwise>
				 </c:choose>
	   		 </logic:iterate>
               </logic:notEmpty>
                  
       		

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
        <td valign="top" class="news">&nbsp;</td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
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
