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
<style type="text/css">
	@keyframes blink {
    0% {
        opacity: 1;
    }
    50% {
        opacity: 0;
    }
    100% {
        opacity: 1;
    }
}
.blinkImage {
    animation: blink 1s;
    animation-iteration-count: infinite;
}
</style>
<html:form action="/studentSupportRequest" >
	<html:hidden property="method" styleId="method" value="" />
	<html:hidden property="formName" value="loginform" />
	<html:hidden property="pageType" value="1" />
	
	<table border="0" width="600" cellspacing="0" cellpadding="0" align="center">
       <tr>
         <td colspan="2" class="nav">SAP Exam Resuls </td>
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
             
             </tr>
	             <tr class="row-white">
                   <td width="60%" class="studentrow-odd"><div align="right"><font size="3" color="black">Result for <bean:write name="loginform" property="sapExamDate"/> SAP exam:</font></div></td>
                   <td width="40%" colspan="2" class="studentrow-even">
                   <c:if test="${status!=''}">
                   <logic:equal value="true" name="loginform" property="statusIsPass">
                   	<font size="6" color="green"><bean:write name="loginform" property="status"/></font>
                   </logic:equal>
                   <logic:equal value="true" name="loginform" property="statusIsFail">
                   	<font size="6" color="red"><bean:write name="loginform" property="status"/></font>
                   </logic:equal>
                   <logic:equal value="true" name="loginform" property="statusIsIsOther">
                   	<font size="6" color="black"><bean:write name="loginform" property="status"/></font>
                   </logic:equal>
                   </c:if>
					
				   </td>
	            </tr>
        		
                <tr class="row-white">
                   <td colspan="2"><div align="center">
					 <html:button property="" value="Cancel" styleClass="btnbg" onclick="cancelAction()"></html:button>
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
