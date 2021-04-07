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
<html:form action="/studentreqstatus" >
	<html:hidden property="method" styleId="method" value="initViewStudentDetails" />
	<html:hidden property="formName" value="reqStatusForm" />
	<table border="0" width="600" cellspacing="0" cellpadding="0">
       <tr>
         <td colspan="2" class="nav">Requistion Status </td>
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
        		<tr class="row-white">
                   <td width="39%" class="studentrow-odd"><div align="right">Requistion No</div></td>
                   <td width="61%" colspan="2" class="studentrow-even">
					<html:select property="hlId" styleClass="comboLarge" styleId="hlId">
								<html:optionsCollection  name="reqStatusForm" property="list" label="name" value="id" />
							</html:select>
					</td>
                 </tr>
                 <tr class="row-white">
                   <td colspan="2"><div align="center">
					<html:submit value="Submit" styleClass="formbutton"></html:submit>	
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