<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>

<script type="text/javascript">
	function resetMessages() {
		document.getElementById("name").value = "";
		document.getElementById("appNoId").value = "";
		document.getElementById("regNoId").value = "";
		document.getElementById("staffIdId").value =" ";
		document.getElementById("rollNoId").value =" ";
		resetFieldAndErrMsgs();
	}	
		
</script>
<body>

<html:form action="/hostelCheckin" method="post" >
<html:hidden property="formName" value="hostelCheckinForm" />
<html:hidden property="method" styleId="method" value="getHostelAllocatedDetails"/>
<html:hidden property="pageType" value="1" />
	
	
	<table width="99%" border="0">
  
  <tr>
    <td><span class="heading"><bean:message key="knowledgepro.hostel"/><span class="Bredcrumbs">&gt;&gt; <bean:message key="knowledgepro.hostel.checkin"/>&gt;&gt;</span> </span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="heading_white" ><bean:message key="knowledgepro.hostel.checkin"/></td>
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
        <td class="heading">&nbsp;</td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td class="heading"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td ><img src="images/01.gif" width="5" height="5" /></td>
            <td width="914" background="images/02.gif"></td>
            <td><img src="images/03.gif" width="5" height="5" /></td>
          </tr>
          <tr>
            <td width="5"  background="images/left.gif"></td>
            <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
                <tr >
                  <td  class="row-odd" ><div align="right"><span class="Mandatory">* </span><bean:message key="knowledgepro.hostel.name"/></div></td>
                  <td  class="row-even" ><div align="left"><html:select property="hostelId" name="hostelCheckinForm" styleClass="combo" styleId="name">
										<html:option value="">-<bean:message key="knowledgepro.select" />-</html:option>
										<html:optionsCollection name="hostelList" label="name" value="id"/>
									</html:select></div></td>
				 <td  height="25" class="row-odd">
				<div align="right"><span class="Mandatory">*</span>
					<bean:message key="knowledgepro.fee.appliedyear"/>:</div>
				</td>
				<td class="row-even" valign="top"><html:select
					property="year" styleId="year"
					styleClass="combo">
					<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
					<cms:renderYear></cms:renderYear>
				</html:select></td>						
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
        <td class="heading">&nbsp;</td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td class="heading"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td ><img src="images/01.gif" width="5" height="5" /></td>
            <td width="914" background="images/02.gif"></td>
            <td><img src="images/03.gif" width="5" height="5" /></td>
          </tr>
          <tr>
            <td width="5"  background="images/left.gif"></td>
            <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
                <tr >
                <td width="12%" class="row-odd" ><div align="right"><strong><bean:message key="knowledgepro.hostel.checkin.applicationno"/></strong></div></td>
                <td width="8%" class="row-even" ><html:text property="appNo"
										styleId="appNoId" size="9" maxlength="9" /></td>
                <td width="2%" class="row-even" ><strong>OR</strong></td>
                <td width="10%" class="row-odd" ><div align="right"><strong><bean:message key="knowledgepro.hostel.checkin.regno"/></strong></div></td>
                <td width="8%" class="row-even" ><html:text property="regNo"
										styleId="regNoId" size="9" maxlength="9" /></td>
                <td width="6%" class="row-even" ><strong>OR</strong></td>
                <td width="12%" class="row-odd" ><div align="right"><strong><bean:message key="knowledgepro.hostel.checkin.staffId"/></strong></div></td>
                <td width="13%" class="row-even" ><html:text property="staffId"
										styleId="staffIdId" size="9" maxlength="9" /></td>
                <td width="3%" class="row-even" ><strong>OR</strong></td>
                <td width="7%" class="row-odd"><div align="right"><strong><bean:message key="knowledgepro.hostel.checkin.rollNo"/></strong></div></td>
                <td width="19%" class="row-even"><html:text property="rollNo"
										styleId="rollNoId" size="9" maxlength="9" /></td>
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
        <td height="26" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="38%" height="35">&nbsp;</td>
            <td width="10%"><html:submit styleClass="formbutton">
												<bean:message key="knowledgepro.submit" />
											</html:submit></td>
            <td width="8%"><html:button property="" styleClass="formbutton" onclick="resetMessages()">
												<bean:message key="knowledgepro.admin.reset"/>
											</html:button></td>
            <td width="44%">&nbsp;</td>
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
</body>
<script type="text/javascript">
</script>
