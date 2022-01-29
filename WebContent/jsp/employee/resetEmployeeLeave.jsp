<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>

<head>

</head>
<script type="text/javascript">
</script>
<html:form action="/resetLeaves" method="post">
<html:hidden property="pageType" value="1" />
<html:hidden property="formName" value="resetLeavesForm" />
<html:hidden property="method" styleId="method" value="resetLeavesOFEmployee" />	
<table width="100%" border="0">
  <tr>
    <td><span class="heading">Reset Leaves</span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="body" ><strong class="boxheader">Reset Leaves</strong></td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td height="20" class="news"><div align="right" class="mandatoryfield">*Mandatory fields</div>
         <div id="errorMessage"><FONT color="red"><html:errors /></FONT>
							<FONT color="green"><html:messages id="msg"
								property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
								<br>
							</html:messages></FONT></div>
        </td>
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
              <tr>
              <td width="20%"  class="row-odd" ><div align="right"> Month:</div></td>
                <td width="20%" class="row-even">
                     <html:select  property="month" styleId="month" styleClass="combo" > 
                     	<cms:renderMonths></cms:renderMonths>
					</html:select></td>
                <td width="20%"  class="row-odd" ><div align="right"> Year:</div></td>
                <td width="20%" class="row-even">
                     <html:select  property="academicYear" styleId="academicYear" styleClass="combo" > 
                     	<cms:renderYear></cms:renderYear>
					</html:select></td>
					<td class="row-odd">
					<div align="right"><span class="Mandatory">*</span><bean:message
						key="employee.info.job.emptype" /></div>
					</td>
					<td class="row-even"><html:select
						name="resetLeavesForm" property="empTypeId"
						styleClass="TextBox">
						<html:option value="">
							<bean:message key="knowledgepro.pettycash.Select" />
						</html:option>
						<logic:notEmpty name="resetLeavesForm" property="listEmployeeType">
						<html:optionsCollection name="resetLeavesForm"
							property="listEmployeeType" label="name" value="id" />
							</logic:notEmpty>
					</html:select></td>
              </tr>
              <tr >
              <td colspan="6" align="center"> 
              <html:submit value="Submit" styleClass="formbutton"></html:submit> &nbsp;
              <html:button property="" styleClass="formbutton" value="Reset" onclick="resetFieldAndErrMsgs()"></html:button>
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
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
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