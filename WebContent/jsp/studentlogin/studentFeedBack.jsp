<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<link rel="stylesheet" type="text/css" href="css/styles.css"/>
	<link rel="stylesheet" type="text/css" href="css/sdmenu.css"/>
    <script type="text/javascript" src="js/AC_RunActiveContent.js"></script>
    <script type="text/javascript" src="js/sdmenu/sdmenu.js"></script>
    <script type="text/javascript" src="js/ajax/Ajax.js"></script>
    <script type="text/javascript" src="js/ajax/AjaxUtil.js"></script>
    <script type="text/javascript" src="js/leftSwitchMenu.js"></script>
	<script type="text/javascript" src="js/common.js"></script>
	<script>
	function cancelAction() {
		document.location.href = "StudentLoginAction.do?method=returnHomePage";
	}
	</script>
<html:form action="/studentFeedBack" >
	<html:hidden property="method" styleId="method" value="addStudentFeedback" />
	<html:hidden property="formName" value="studentFeedBackForm" />
	<html:hidden property="pageType" value="1" />
	<table border="0" width="600" cellspacing="0" cellpadding="0">
       <tr>
         <td colspan="2" class="nav">Feedback </td>
         <td width="220" class="tr">&nbsp;</td>
       </tr>
	<tr>&nbsp;&nbsp;</tr>
	<tr>
         <td width="13" class="le">&nbsp;</td>
         <td width="383">
		<table width="100%" border="0" cellpadding="0" cellspacing="0" >
		<tr>
		<td valign="top" background="images/Tright_03_03.gif"></td>
     	  <td valign="top" class="news">
     	  <table width="98%" border="0" align="center" cellpadding="0" cellspacing="0">
           <tr>
             <td width="100%"  valign="top"><table width="100%" border="0" cellpadding="4" cellspacing="1">
             <tr >
             <td colspan="2">
             <div align="right"><span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields" /></span></div>
							<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
							<FONT color="green"><html:messages id="msg"
								property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
								<br>
							</html:messages></FONT></div>
             </td>
             </tr>
        		<tr class="row-white">
                   <td width="39%" class="studentrow-odd"><div align="right">*<bean:message key="knowledgepro.studentFeedback.label"/></div></td>
                   <td width="61%" colspan="2" class="studentrow-even">
					<html:textarea property="feedBack" name="studentFeedBackForm" styleId="feed" cols="30" rows="5" style="width:250px;"/>
					</td>
                 </tr>
                 <tr class="row-white">
                 <td width="39%" class="studentrow-odd"><div align="right">*<bean:message key="knowledgepro.studentEmail.label"/></div></td>
                 <td width="61%" class="studentrow-even">
                <html:text property="email" name="studentFeedBackForm" size="38"></html:text>
                 </td>
                 </tr>
                 <tr class="row-white">
                 <td width="39%" class="studentrow-odd"><div align="right">*<bean:message key="knowledgepro.studentMobileNo.label"/></div></td>
                 <td width="61%" class="studentrow-even">
                <html:text property="mobileNo" name="studentFeedBackForm"></html:text>
                 </td>
                 </tr>
                 <tr class="row-white">
                   <td colspan="2"><div align="center">
					<html:submit value="Submit" styleClass="btnbg" ></html:submit>	&nbsp; <html:button property="" value="Cancel" styleClass="btnbg" onclick="cancelAction()"></html:button>
					</div></td>
                 </tr>
             </table></td>
           </tr>
           <tr>
             <td>&nbsp;</td>
           </tr>
       </table>
       </td>
       	</tr>	
   </table>
		</td>
	  <td width="220" class="ri">&nbsp;</td>
       </tr>
		<tr>
          <td class="bl">&nbsp;</td>
          <td class="bm">&nbsp;</td>
          <td class="br">&nbsp;</td>
        </tr>
	</table>
</html:form>