<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-tld/displaytag.tld" prefix="display"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">

<head>
<script type="text/javascript">
function resetFields(){	
	document.getElementById("date").value="";
	document.getElementById("timeHr").value="00";
	document.getElementById("timeMin").value="00";	
	document.getElementById("amount").value="";
    document.getElementById("description").value="";	
	resetErrMsgs();
}
function deleteDamageEntry(hlDamageId) {
	deleteConfirm =confirm("Are you sure to delete this entry?");
	if(deleteConfirm)
	document.location.href = "HostelDamageEntry.do?method=deleteHostelDamageEntry&hlDamageId="+hlDamageId;
}
function goToHostelDamageInitPage(){
	document.location.href = "HostelDamageEntry.do?method=initHostelDamageEntry";
}
function clearField(field){
	if(field.value == "00")
		field.value = "";
}
function checkForEmpty(field){
	if(field.value.length == 0)
		field.value="00";
}
</script>
</head>
<html:form action="/HostelDamageEntry" method="POST">
<html:hidden property="method" styleId="method" value="addHostelDamageEntry"/>
<html:hidden property="formName" value="hostelDamageForm" />
<html:hidden property="pageType" value="2" />

<table width="99%" border="0">
  
  <tr>
    <td><span class="heading"><bean:message key="knowledgepro.hostel"/><span class="Bredcrumbs">&gt;&gt; <bean:message key="knowledgepro.hostel.damageEntry"/> &gt;&gt;</span> </span></td>
  </tr>
  <tr>
    <td valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="heading_white" > <bean:message key="knowledgepro.hostel.damageEntry"/></td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news">&nbsp;</td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
          
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
            <td ><img src="images/01.gif" width="5" height="5" /></td>
            <td width="914" background="images/02.gif"></td>
            <td><img src="images/03.gif" width="5" height="5" /></td>
          </tr>
          <tr>
            <td width="5"  background="images/left.gif"></td>
            <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
                <tr >
                  <td width="25%" height="25" class="row-odd" ><div align="right"><bean:message key="knowledgepro.hostel.entry.hostel.name"/></div></td>
                  <td width="23%" height="25" class="row-even" ><bean:write name="hostelDamageForm" property="hostelDamageTO.hostelName"/></td>
                  <td width="27%" class="row-odd" ><div align="right"><bean:message key="knowledgepro.hostel.hostelerdatabase.name"/></div></td>
                  <td width="25%" class="row-even" ><bean:write name="hostelDamageForm" property="hostelDamageTO.studentOrStaffDisplay"/></td>
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
        <td valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news">&nbsp;</td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="94" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="100%" cellspacing="1" cellpadding="2">
            <tr>
              <td colspan="4" ><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                  <tr>
                    <td ><img src="images/01.gif" width="5" height="5" /></td>
                    <td width="914" background="images/02.gif"></td>
                    <td><img src="images/03.gif" width="5" height="5" /></td>
                  </tr>
                  <tr>
                    <td width="5"  background="images/left.gif"></td>
                    <td align="center" valign="top">
					<table width="100%" cellspacing="1" cellpadding="2">
                        <tr >
                          <td width="15%" height="25" class="row-odd"><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.inventory.salvageitem.date.label"/></div></td>
                          <td colspan="2" class="row-even">
                          <html:text name="hostelDamageForm" property="date" styleId="date" size="10" maxlength="16"/>
							  <script
								language="JavaScript">
								new tcal( {
									// form name
									'formname' :'hostelDamageForm',
									// input name
									'controlname' :'date'
								});
							  </script>
                          </td>
                          <td width="11%" class="row-odd"><div align="right"><bean:message key="knowledgepro.petticash.time"/></div></td>
                          <td class="row-even"><html:text property="timeHours"  styleId="timeHr" value="00" onfocus="clearField(this)"  onblur="checkForEmpty(this)" onkeypress="return isNumberKey(event)" size="2" maxlength="2"/>
                            :
                            <html:text property="timeMins" styleId="timeMin" onfocus="clearField(this)" value="00" onblur="checkForEmpty(this)" onkeypress="return isNumberKey(event)"  size="2" maxlength="2"/></td>
                          <td width="14%" class="row-odd"><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.petticash.amount"/></div></td>
                          <td width="18%" class="row-even"><html:text property="amount" styleId="amount" size="20" maxlength="20"/></td>
                        </tr>
                        <tr >
                          <td height="25" valign="top" class="row-odd" ><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.admin.desc.with.col"/></div></td>
                          <td width="16%" height="25" class="row-even" ><html:textarea property="description" styleId="description" cols="15" rows="2"/></td>
                          <td height="25" colspan="5" class="row-even" >&nbsp;</td>
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
              </table></td>
            </tr>
        </table></td>
        <td width="10" valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="26" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td width="48%" height="35" align="right">
              <html:submit property="" styleClass="formbutton" value="Submit"></html:submit></td>
              <td width="4%">&nbsp;</td>
              <td width="48%">
              <html:button property="" styleClass="formbutton" onclick="resetFields()">
				<bean:message key="knowledgepro.admin.reset" />
			  </html:button> &nbsp;&nbsp;&nbsp;
              <html:button property="" styleClass="formbutton" onclick="goToHostelDamageInitPage()">
				<bean:message key="knowledgepro.cancel" />
			  </html:button></td>
              </td>
              </tr>
        </table></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="26" valign="top" background="images/Tright_03_03.gif"></td>
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
                  <td height="25" class="row-odd" ><div align="center"><bean:message key="knowledgepro.slno"/></div></td>
                  <td class="row-odd" ><bean:message key="knowledgepro.application.registerNo.staff"/></td>
                  <td class="row-odd" ><bean:message key="knowledgepro.hostel.damageEntry.date"/> &amp; <bean:message key="knowledgepro.petticash.time"/></td>
                  <td class="row-odd" ><bean:message key="knowledgepro.admission.amt"/></td>
                  <td class="row-odd" ><bean:message key="employee.info.job.achievement.desc"/></td>
                  <td class="row-odd" >BillNo</td>
                  <td class="row-odd" >Status</td>
                  <td class="row-odd"><div align="center"><bean:message key="knowledgepro.delete"/></div></td>
                </tr>
				<logic:notEmpty name="hostelDamageForm" property="hlDamageTOList">	
				<logic:iterate id="hlDamageTO" name="hostelDamageForm" property="hlDamageTOList" indexId="count">
                <tr>
                  <td width="7%" height="25" class="row-even"><div align="center"><c:out value="${count + 1}"/></td>
                  <td width="18%" class="row-even" ><bean:write name="hlDamageTO" property="applnNoRegisterStaffIdRollNo"/> </td>
                  <td width="15%" class="row-even" ><bean:write name="hlDamageTO" property="dateAndTimeDisplay"/></td>
                  <td width="8%" class="row-even" ><bean:write name="hlDamageTO" property="amount"/></td> 
                  <td width="33%" class="row-even" ><bean:write name="hlDamageTO" property="description"/></td>
                  <td width="33%" class="row-even" ><bean:write name="hlDamageTO" property="billNo"/></td>
                  <td width="33%" class="row-even" ><bean:write name="hlDamageTO" property="status"/></td>
                  <td width="8%" class="row-even" ><div align="center"><img src="images/delete_icon.gif" width="16" height="16"  style="cursor:pointer" onclick="deleteDamageEntry('<bean:write name="hlDamageTO" property="id"/>')" /></div></td>
                </tr>
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
        <td height="26" valign="top" background="images/Tright_03_03.gif"></td>
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