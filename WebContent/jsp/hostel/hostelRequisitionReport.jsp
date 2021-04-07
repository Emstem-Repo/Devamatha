<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>

<head>
<link href="css/styles.css" rel="stylesheet" type="text/css">
<script language="JavaScript" src="js/calendar_us.js"></script>
<script type="text/javascript" language="JavaScript">
function resetMessages() {
	document.getElementById("name").value = "";
	document.getElementById("appliedDate").value = "";
	resetFieldAndErrMsgs();
}	
	
</script>
<link rel="stylesheet" href="css/calendar.css">
</head>
<html:form action="/hostelReqReport" >
<html:hidden property="formName" value="hostelReqReportForm" />
<html:hidden property="method" styleId="method" value="getReqReportDetails"/>
<html:hidden property="pageType" value="1" />
<table width="99%" border="0">
<tr>
    <td><span class="heading"><a href="#" class="Bredcrumbs"><bean:message key="knowledgepro.hostel"/></a> <span class="Bredcrumbs">&gt;&gt; <bean:message key="knowledgepro.hostel.req.report"/> &gt;&gt;</span></span></td>
  </tr>
  <tr>
    <td valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" ></td>
        <td colspan="2" background="images/Tcenter.gif" class="body" ><div align="left" class="heading_white"> <bean:message key="knowledgepro.hostel.req.report"/></div></td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="20" valign="top" background="images/Tright_03_03.gif"></td>
         <td valign="top" class="news">
								<div align="right"><span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields" /></span></div>
								<div id="errorMessage">
									<FONT color="red"><html:errors /></FONT>
									<FONT color="green">
										<html:messages id="msg" property="messages" message="true">
											<c:out value="${msg}" escapeXml="false"></c:out><br>
										</html:messages>
									</FONT>
								</div>
							</td>
        <td height="20" valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      
      <tr>
        <td valign="top" background="images/Tright_03_03.gif"></td>
        <td colspan="2" valign="top" class="heading"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td ><img src="images/01.gif" width="5" height="5" /></td>
            <td width="914" background="images/02.gif"></td>
            <td><img src="images/03.gif" width="5" height="5" /></td>
          </tr>
          <tr>
            <td width="5"  background="images/left.gif"></td>
            <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
                <tr >
                  <td width="25%" class="row-odd" ><div align="right"><span class="Mandatory">* </span><bean:message key="knowledgepro.hostel.name"/> </div></td>
                  <td width="25%" class="row-even" ><html:select property="hostelId" name="hostelReqReportForm" styleClass="combo" styleId="name">
										<html:option value="">-<bean:message key="knowledgepro.select" />-</html:option>
										<html:optionsCollection name="hostelList" label="name" value="id"/>
									</html:select></td>
                  <td width="25%" class="row-odd" ><div align="right"><span class="Mandatory">* </span><bean:message key="knowledgepro.hostel.date" /> </div></td>
              <td width="25%" class="row-even" ><html:text name="hostelReqReportForm" property="appliedDate" styleId="appliedDate"	size="10" maxlength="10" />
                              <script language="JavaScript" type="text/javascript">
									new tcal ({
											// form name
											'formname': 'hostelReqReportForm',
											// input name
											'controlname': 'appliedDate'
										});
							</script></td>
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
        <td colspan="2" valign="top" class="heading">&nbsp;</td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td valign="top" background="images/Tright_03_03.gif"></td>
        <td colspan="2" valign="top" class="news"><div align="center">
            <table width="100%" height="27"  border="0" cellpadding="1" cellspacing="2">
              <tr>
                <td><table width="100%"  border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td width="48%" height="35" align="right"><html:submit styleClass="formbutton">
												<bean:message key="knowledgepro.submit" />
											</html:submit></td>
                    <td width="2%">&nbsp;</td>
                    <td width="48%"><html:button property="" styleClass="formbutton" onclick="resetMessages()">
												<bean:message key="knowledgepro.admin.reset"/>
											</html:button></td>
                  </tr>
                </table></td>
              </tr>
            </table>
        </div></td>
        <td width="10" valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td><img src="images/Tright_03_05.gif" ></td>
        <td colspan="2"  background="images/TcenterD.gif"></td>
        <td><img src="images/Tright_02.gif" width="9" height="29"></td>
      </tr>
    </table></td>
  </tr>
</table>
</html:form>
