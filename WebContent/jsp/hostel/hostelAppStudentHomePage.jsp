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
	function getRoomTypes(hostelId){
		document.location.href = "HostelApplication.do?method=showRequiredDataToApply&hostelId="
			+ hostelId;
	}
	function viewHostelDetails(hostelId){
		var url = "HostelApplication.do?method=viewHostelDetails&hostelId="+hostelId;
		window.open(url,'ViewTermsCondition','left=20,top=20,width=1000,height=1000,toolbar=1,resizable=0,scrollbars=1');	
	}
	function cancelAction() {
		document.location.href = "StudentLoginAction.do?method=returnHomePage";
	}
	</script>
<html:form action="/HostelApplication" >
	<html:hidden property="method" styleId="method" />
	<html:hidden property="formName" value="hostelApplicationForm" />
	<table border="0" width="600" cellspacing="0" cellpadding="0">
       <tr>
         <td colspan="2" class="nav">Hostel Application </td>
         <td width="220" class="tr">&nbsp;</td>
       </tr>
	<tr>&nbsp;&nbsp;</tr>
	<tr>
         <td width="13" class="le">&nbsp;</td>
         <td width="383">
		<table width="100%" border="0" cellpadding="0" cellspacing="0" >
     
     <logic:notEmpty name="hostelApplicationForm" property="hostelList">
     <nested:iterate name="hostelApplicationForm" id="hostel" property="hostelList" indexId="count">
     	<c:if test="${(count) % 2 == 0}">
		<tr>	
	<td valign="top" background="images/Tright_03_03.gif"></td>
       <td valign="top" class="news">
       <table width="98%" border="0" align="center" cellpadding="0" cellspacing="0">
           <tr>
             <td width="100%"  valign="top"><table width="100%" border="0" cellpadding="4" cellspacing="1">
                 <tr class="row-white">
                   <td width="39%" class="studentrow-odd"><div align="right"><bean:message key="knowledgepro.hostel.name.col"/></div></td>
                   <td width="61%" colspan="2" class="studentrow-even"><nested:write name="hostel" property="name"/></td>
                 </tr>
                 <tr class="row-white">
                   <td class="studentrow-odd"><div align="right"><bean:message key="knowledgepro.hostel.address.col"/></div></td>
                   <td colspan="2" class="studentrow-even">
                   	<nested:write name="hostel" property="addressLine1"/>
                   	<nested:write name="hostel" property="addressLine2"/>
                   </td>
                 </tr>
                 <tr class="row-white">
                   <td height="35" colspan="3" class="row-white"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
                       <tr>
                         <td width="49%" height="35"><div align="right">
                         <input type="button" value="View Details" class="btnbg" onclick="viewHostelDetails('<nested:write name="hostel" property="id" />')"/>
                         </div></td>
                         <td width="3%"></td>
                         <td width="48%"><input type="button" value="Apply" class="btnbg" onclick="getRoomTypes('<nested:write name="hostel" property="id" />')" /></td>
                       </tr>
                   </table></td>
                 </tr>
             </table></td>
           </tr>
           <tr>
             <td>&nbsp;</td>
           </tr>
       </table>
       </td>
       </c:if>
       <c:if test="${(count) % 2 != 0}">
       <td valign="top" class="news">
       <table width="98%" border="0" align="center" cellpadding="0" cellspacing="0">
           <tr>
             <td width="100%"  valign="top"><table width="100%" border="0" cellpadding="4" cellspacing="1">
                 <tr class="row-white">
                   <td width="39%" class="studentrow-odd"><div align="right"><bean:message key="knowledgepro.hostel.name.col"/></div></td>
                   <td width="61%" colspan="2" class="studentrow-even"><nested:write name="hostel" property="name"/></td>
                 </tr>
                 <tr class="row-white">
                   <td class="studentrow-odd"><div align="right"><bean:message key="knowledgepro.hostel.address.col"/></div></td>
                   <td colspan="2" class="studentrow-even">
                   	<nested:write name="hostel" property="addressLine1"/>
                   	<nested:write name="hostel" property="addressLine2"/>
				</td>
                 </tr>
                 <tr class="row-white">
                   <td height="35" colspan="3" class="row-white"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
                       <tr>
                         <td width="49%" height="35"><div align="right">
                            <input type="button" value="View Details" class="btnbg" onclick="viewHostelDetails('<nested:write name="hostel" property="id" />')"/>
                         </div></td>
                         <td width="3%"></td>
                         <td width="48%"><input type="button" value="Apply" class="btnbg" onclick="getRoomTypes('<nested:write name="hostel" property="id" />')" /></td>
                       </tr>
                   </table></td>
                 </tr>
             </table></td>
           </tr>
       </table>
       </td>
       	</tr>	
	</c:if>
     </nested:iterate>
     </logic:notEmpty>
     <c:if test="${(count) % 2 == 0}">
	<td>&nbsp;</td>
	</tr>
     </c:if>
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