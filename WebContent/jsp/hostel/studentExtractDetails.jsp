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
	document.getElementById("fromDate").value = "";
	document.getElementById("toDate").value = "";
	resetFieldAndErrMsgs();
}	
</script>
<link rel="stylesheet" href="css/calendar.css">
</head>
<html:form action="/studentExtract" >
<html:hidden property="formName" value="hostelStudentExtractForm" />
<html:hidden property="method" styleId="method" value="getStudentExtractDetails"/>
<html:hidden property="pageType" value="1"/>
<table width="99%" border="0">
  
  <tr>
    <td><span class="heading"><bean:message key="knowledgepro.hostel"/><span class="Bredcrumbs">&gt;&gt;<bean:message key="knowledgepro.hostel.student.extraction"/>   &gt;&gt;</span> </span></td>
  </tr>
  <tr>
    <td valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="heading_white" ><bean:message key="knowledgepro.hostel.student.extraction"/> </td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
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
        <td valign="top" background="images/Tright_3_3.gif" ></td>
      </tr>
      <tr>
        <td valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" ><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
            <tr>
              <td ><img src="images/01.gif" width="5" height="5" /></td>
              <td width="914" background="images/02.gif"></td>
              <td><img src="images/03.gif" width="5" height="5" /></td>
            </tr>
            <tr>
              <td width="5"  background="images/left.gif"></td>
              <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
                  <tr >
                    <td width="25%" height="25" class="row-odd" ><div align="right">* <bean:message key="knowledgepro.hostel.student.from.date"/></div></td>
                    <td width="25%" class="row-even" ><html:text name="hostelStudentExtractForm" property="fromDate" styleId="fromDate"	size="10" maxlength="10" />
                        <script language="JavaScript">
										new tcal ({
													// form name
														'formname': 'hostelStudentExtractForm',
													// input name
														'controlname': 'fromDate'
													});
						</script>
					</td>
                    <td width="25%" height="25" class="row-odd" ><div align="right">* <bean:message key="knowledgepro.hostel.student.to.date"/></div></td>
                    <td width="25%" class="row-even" ><html:text name="hostelStudentExtractForm" property="toDate" styleId="toDate"	size="10" maxlength="10" />
                        <script language="JavaScript">
								new tcal ({
											// form name
													'formname': 'hostelStudentExtractForm',
											// input name
													'controlname': 'toDate'
											});
						</script>   
                 <div align="right"></div></td>
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
        <td valign="top" background="images/Tright_3_3.gif" ></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td class="heading"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td width="47%" height="35"><div align="right">
                  <html:submit styleClass="formbutton"><bean:message key="knowledgepro.submit" /></html:submit>
              </div></td>
              <td width="2%"></td>
              <td width="51%"><html:button property="" styleClass="formbutton" onclick="resetMessages()"><bean:message key="knowledgepro.admin.reset"/></html:button></td>
            </tr>
        </table></td>
        <td valign="top" background="images/Tright_3_3.gif" ></td>
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


