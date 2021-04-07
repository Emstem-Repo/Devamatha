<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<html:html>
<head>
<title><bean:message key="knowledgepro.title"/> </title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

<script language="JavaScript" src="js/calendar_us.js"></script>
<script language="JavaScript" src="js/admission/prcadmissionform.js"></script>
<link rel="stylesheet" href="css/calendar.css">


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
</style></head>

<body>
<html:form action="/presidanceadmissionFormSubmit" enctype="multipart/form-data">
<html:hidden property="method" value=""/>
<html:hidden property="pageType" value="5"/>
<html:hidden property="formName" value="admissionFormForm"/>
<table width="98%" border="0">
  
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="10"><img src="images/Tright_03_01.gif" ></td>
        <td width="100%" background="images/Tcenter.gif" class="body" ><div align="left"><span class="boxheader"><bean:message key="knowledgepro.admission"/> &gt;&gt; <bean:message key="knowledgepro.applicationform"/> &gt;&gt;</span></div></td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td valign="top" background="images/Tright_03_03.gif"></td>
        <td height="46" class="heading"><img src="images/Upload_tab.jpg" width="664" height="33" border="0">
                  </td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
 		<tr>
        <td height="20" valign="top" background="images/Tright_03_03.gif"></td>
        <td class="heading"><div align="right"> <span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields"/> </span></div><div id="errorMessage"><html:errors/></div></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td ><img src="images/01.gif" width="5" height="5" /></td>
            <td width="914" background="images/02.gif"></td>
            <td><img src="images/03.gif" width="5" height="5" /></td>
          </tr>
          <tr>
            <td width="5"  background="images/left.gif"></td>
            <td valign="top"><table width="100%" height="90" border="0" cellpadding="0" cellspacing="1">
			
           <nested:iterate  name="admissionFormForm" property="uploadDocs" indexId="count">
				<tr>
            <td width="410" height="25"  class="row-odd" ><div align="right"><nested:write property="name"/>:</div></td>
            <td height="25" class="row-even"><label>
			
               <nested:file property="document" maxlength="30"></nested:file>
            </label></td>
          </tr>
           </nested:iterate>
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
        <td valign="top" class="news"><div align="center">
              <table width="100%" height="27"  border="0" cellpadding="1" cellspacing="2">
                <tr>
                  <td><table width="100%"  border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="48%" height="21"><div align="right"><html:button property="" onclick="submitAdmissionForm('submitAdmissionFormAttachments')" styleClass="formbutton" value="Continue"></html:button> </div></td>
                      <td width="1%"><div align="center"></div></td>
                      <td width="51%" height="45"><div align="left"><html:button property=""  styleClass="formbutton" value="Reset" onclick="submitAdmissionForm('initAttachMentPage')"></html:button></div></td>
                    </tr>
                  </table></td>
                </tr>
              </table>
            </div></td>
        <td width="10" valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td><img src="images/Tright_03_05.gif" ></td>
        <td  background="images/TcenterD.gif"></td>
        <td><img src="images/Tright_02.gif" width="9" height="29"></td>
      </tr>
    </table></td>
  </tr>
</table>
</html:form>
</html:html>
