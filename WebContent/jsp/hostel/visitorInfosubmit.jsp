<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<script language="JavaScript" >
function resetMessages() {
	resetFieldAndErrMsgs();
	}
</script>
<html:form action="/hostelVisitorInfo" enctype="multipart/form-data">
<html:hidden property="method" styleId="method" value="submitDetails" />	
	<html:hidden property="pageType" value="3" />
	<html:hidden property="formName" value="visitorInfoForm" />
<table width="98%" border="0">
   <tr>
    <td><span class="heading"><a href="#" class="Bredcrumbs"><bean:message key="knowledgepro.hostel"/></a> <span class="Bredcrumbs">&gt;&gt; <bean:message key="knowledgepro.hostel.visitor.display"/>&gt;&gt;</span></span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
       <tr>
        <td width="9"><img src="images/Tright_03_01.gif" ></td>
        <td width="100%" background="images/Tcenter.gif" class="body" ><div align="left" class="heading_white"><bean:message key="knowledgepro.hostel.visitor.display"/></div></td>
        <td width="11" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td valign="top" background="images/Tright_03_03.gif"></td>
        <td height="10" class="heading">&nbsp;</td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
		<td height="20" valign="top" background="images/Tright_03_03.gif"></td>
		<td valign="top" class="news">
		<div align="right"><FONT color="red"> <span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields"/></span></FONT></div>
		<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
		<FONT color="green"> <html:messages id="msg"
			property="messages" message="true">
			<c:out value="${msg}" escapeXml="false"></c:out>
			<br>
		</html:messages> </FONT></div>
		</td>
		<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
	</tr>
      <tr>
        <td height="17" valign="top" background="images/Tright_03_03.gif"></td>
        <td height="17" valign="top" class="heading"><table width="100%" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td ><img src="images/01.gif" width="5" height="5"></td>
            <td width="914" background="images/02.gif"></td>
            <td><img src="images/03.gif" width="5" height="5"></td>
          </tr>
          <tr>
            <td width="5"  background="images/left.gif"></td>
            <td width="100%" height="29" valign="top"><table width="100%" cellspacing="1" cellpadding="2">
                <tr >
                  <td width="20%" height="25" class="row-odd" > <div align="right"><span class="Mandatory">*</span><bean:message key="employee.info.firstName"/></div></td>
                  <td width="30%" class="row-even" >
                  <html:text name="visitorInfoForm" property="firstName" styleId="firstName" styleClass="TextBox" size="20" maxlength="20" />
                  </td>
                  <td width="21%" class="row-odd" ><div align="right"><span class="Mandatory">*</span>
                  <bean:message key="employee.info.lastName"/></div>
                  </td>
                  <td width="29%" class="row-even">
                  <html:text name="visitorInfoForm" property="lastName" styleId="lastName" styleClass="TextBox" size="20" maxlength="20" />                  
                  </td>
                </tr>
                <tr >
                  <td height="25" class="row-odd" ><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.hostel.visitor.id"/></div></td>
                  <td class="row-even" >
                  <html:text name="visitorInfoForm" property="visitorId" styleId="visitorId" styleClass="TextBox" size="20" maxlength="20" />                   
                  </td>
                  <td class="row-odd" ><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.relationship.col"/></div></td>
                  <td class="row-even" >
                  <html:text name="visitorInfoForm" property="relationShip" styleId="relationShip" styleClass="TextBox" size="20" maxlength="20" />                  
                  </td>
                </tr>
                <tr>
                <td height="25" class="row-odd" >
                <div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.upload.image"/></div>
                </td>
                <td class="row-even" height="25" colspan="3">
                <html:file property="photoFile"></html:file>
                </td>
                </tr>
                
            </table></td>
            <td  background="images/right.gif" width="10" height="29"></td>
          </tr>
          <tr>
            <td height="5"><img src="images/04.gif" width="5" height="5"></td>
            <td background="images/05.gif"></td>
            <td><img src="images/06.gif" ></td>
          </tr>
        </table></td>
        <td height="17" valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="24" valign="top" background="images/Tright_03_03.gif"></td>
        <td height="17" class="heading"><bean:message key="knowledgepro.hostel.visitor.visitDetails"/></td>
        <td height="17" valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="17" valign="top" background="images/Tright_03_03.gif"></td>
        <td height="17" valign="top" class="heading"><table width="100%%" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td ><img src="images/01.gif" width="5" height="5"></td>
            <td width="914" background="images/02.gif"></td>
            <td><img src="images/03.gif" width="5" height="5"></td>
          </tr>
          <tr>
            <td width="5"  background="images/left.gif"></td>
            <td width="100%" height="29" valign="top"><table width="100%" cellspacing="1" cellpadding="2">
                <tr >
                  <td height="25" class="row-odd" ><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.hostel.vistorinfo.Indate"/></div></td>
                  <td width="35%" class="row-even" >
                  <html:text name="visitorInfoForm" property="dateIn" styleId="dateIn" size="10" maxlength="16"/>
							<script
							language="JavaScript">
							new tcal( {
								// form name
								'formname' :'visitorInfoForm',
								// input name
								'controlname' :'dateIn'
							});
						</script>
                  </td>
                  <td width="16%" class="row-odd" ><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.hostel.vistorinfo.outdate"/></div></td>
                  <td width="23%" class="row-even" >
                  <html:text name="visitorInfoForm" property="dateOut" styleId="dateOut" size="10" maxlength="16"/>
							<script
							language="JavaScript">
							new tcal( {
								// form name
								'formname' :'visitorInfoForm',
								// input name
								'controlname' :'dateOut'
							});
						</script>
                  </td>
                </tr>
              </table></td>
            <td  background="images/right.gif" width="10" height="29"></td>
          </tr>
          <tr>
            <td  background="images/left.gif"></td>
            <td height="4" valign="top"></td>
            <td  background="images/right.gif" height="4"></td>
          </tr>
          <tr>
            <td height="5"><img src="images/04.gif" width="5" height="5"></td>
            <td background="images/05.gif"></td>
            <td><img src="images/06.gif" ></td>
          </tr>
        </table></td>
        <td height="17" valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news">
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="46%" height="35">
							<div align="right"><html:submit styleClass="formbutton">
								<bean:message key="knowledgepro.submit"/>
							</html:submit></div>
							</td>
							<td width="2%"></td>
							<td width="52%" align="left"><html:button property=""
								styleClass="formbutton" onclick="resetMessages()">
								<bean:message key="knowledgepro.admin.reset" />
								</html:button> 
							</td>
						</tr>
					</table>
        </td>
        <td width="11" valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td><img src="images/Tright_03_05.gif" ></td>
        <td width="100%"  background="images/TcenterD.gif"></td>
        <td><img src="images/Tright_02.gif" width="9" height="29"></td>
      </tr>
    </table></td>
  </tr>
</table>
</html:form>