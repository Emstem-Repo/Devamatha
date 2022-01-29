<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>

<head>
<script type="text/javascript">
function resetFields()
{	
	document.getElementById("hostel").selectedIndex = 0;
	document.getElementById("applicationNo").value="";
	document.getElementById("registerNo").value="";
	document.getElementById("staffId").value="";
	document.getElementById("rollNo").value="";
	//document.getElementById("fee").value="";
	//document.getElementById("fine").value="";		
	resetErrMsgs();
}
</script>
</head>
<html:form action="/HostelPaymentSlip">
<html:hidden property="method" styleId="method" value="getBillNumbers"/>
<html:hidden property="formName" value="hostelPaymentSlipForm" />
<html:hidden property="pageType" value="1" />

<table width="99%" border="0">
  
  <tr>
    <td><span class="heading"><bean:message key="knowledgepro.hostel"/> <span class="Bredcrumbs">&gt;&gt; <bean:message key="knowledgepro.hostel.paymentSlip"/>&gt;&gt;</span> </span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="heading_white" ><bean:message key="knowledgepro.hostel.paymentSlip"/></td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
            
      </tr>
      
           
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td class="heading"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
          
          <tr>
		<td colspan="6" align="left">
		<div align="right"><span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields"/></span></div>
		<div id="errorMessage"><FONT color="red"><html:errors/></FONT>
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
                  <td width="16%" class="row-odd" ><div align="right"><span class="Mandatory">* </span>
                  <bean:message key="knowledgepro.hostel.entry.hostel.name" /> </div></td>
                  <td width="41%" class="row-even" ><html:select property="hostelId" styleClass="TextBox" styleId="hostel" name="hostelPaymentSlipForm">
						<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
						<logic:notEmpty name="hostelPaymentSlipForm" property="hostelTOList">
						<html:optionsCollection property="hostelTOList" name="hostelPaymentSlipForm" label="name" value="id" />
						</logic:notEmpty>
					</html:select></td>

               <td width="44%" height="25" class="row-even"> 
                 <html:radio name="hostelPaymentSlipForm" styleId="fee" property="searchBy" value="1"><bean:message key="knowledgepro.hostel.paymentSlip.fee"/> </html:radio>
				<html:radio name="hostelPaymentSlipForm" styleId="fine" property="searchBy" value="2"><bean:message key="knowledgepro.hostel.paymentSlip.fine"/> </html:radio>
               </td>
                  <!--<td width="7%" class="row-odd" ><bean:message key="knowledgepro.hostel.paymentSlip.fee"/></td>
                  <td width="12%" class="row-even" ><input type="radio" name="radio" id="radio" value="radio"></td>
                  <td width="8%" class="row-odd" ><bean:message key="knowledgepro.hostel.paymentSlip.fine"/></td>
                  <td width="16%" class="row-even" ><input type="radio" name="radio" id="radio2" value="radio"></td>
                --></tr>
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
        <td class="heading">&nbsp;</td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="20" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="100%" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td><img src="images/01.gif" width="5" height="5"></td>
            <td  background="images/02.gif"></td>
            <td width="10" ><img src="images/03.gif" width="5" height="5"></td>
          </tr>
          <tr>
            <td width="5" background="images/left.gif"></td>
            <td width="100%"  valign="top"><table width="100%" cellspacing="1" cellpadding="2">
              <tr >
                <td width="12%" class="row-odd" ><div align="right"><strong><bean:message key="knowledgepro.interview.ApplicationNo"/></strong></div></td>
                <td width="8%" class="row-even" ><html:text property="applicationNo" styleId="applicationNo" size="20" maxlength="15"/></td>
                <td width="2%" class="row-even" ><strong>OR</strong></td>
                <td width="10%" class="row-odd" ><div align="right"><strong><bean:message key="knowledgepro.hostel.reservation.registerNo"/></strong></div></td>
                <td width="8%" class="row-even" ><html:text property="registerNo" styleId="registerNo" size="20" maxlength="15"/></td>
                <td width="6%" class="row-even" ><strong>OR</strong></td>
                <td width="12%" class="row-odd" ><div align="right"><bean:message key="knowledgepro.hostel.reservation.staffId"/></div></td>
                <td width="13%" class="row-even" ><html:text property="staffId" styleId="staffId"  size="20" maxlength="15"/></td>
                <td width="3%" class="row-even" ><strong>OR</strong></td>
                <td width="7%" class="row-odd"><div align="right"><bean:message key="knowledgepro.pettycash.rollNo"/>:</div></td>
                <td width="19%" class="row-even"><html:text property="rollNo" styleId="rollNo"  size="20" maxlength="15"/></td>
              </tr>
            </table></td>
            <td  background="images/right.gif" width="10" height="28"></td>
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
        <td height="26" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="38%" height="35" align="right"><html:submit property="" styleClass="formbutton" value="Continue">
			 </html:submit></td>
            <td width="4%">&nbsp;</td>
              <td width="48%">
              <html:button property="" styleClass="formbutton" onclick="resetFields()">
				<bean:message key="knowledgepro.admin.reset" />
			 </html:button></td>
          </tr>
        </table></td>
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

