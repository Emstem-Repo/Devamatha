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
		<tr>
		<td valign="top" background="images/Tright_03_03.gif"></td>
     	  <td valign="top" class="news">
     	  <table width="98%" border="0" align="center" cellpadding="0" cellspacing="0">
           <tr>
             <td width="100%"  valign="top"><table width="100%" border="0" cellpadding="4" cellspacing="1">
                 <tr class="row-white">
                   <td width="39%" class="studentrow-odd"><div align="right"><bean:message key="knowledgepro.fee.studentname"/></div></td>
                   <td width="61%" colspan="2" class="studentrow-even">
					<logic:notEmpty name="reqStatusForm" property="studentDetailsTOList">
                  	<bean:write name="reqStatusForm" property="studentDetailsTOList.studentName"/>
                  </logic:notEmpty>
					</td>
                 </tr>
                 <tr class="row-white">
                   <td width="39%" class="studentrow-odd"><div align="right"><bean:message key="knowledgepro.fee.applicationno"/></div></td>
                   <td width="61%" colspan="2" class="studentrow-even">
					 <logic:notEmpty name="reqStatusForm" property="studentDetailsTOList">
                            	<bean:write name="reqStatusForm" property="studentDetailsTOList.applicationNo"/>
                            </logic:notEmpty>
					</td>
                 </tr>
                 <tr class="row-white">
                   <td class="studentrow-odd"><div align="right"><bean:message key="knowledgepro.hostel.appliedDate"/></div></td>
                   <td colspan="2" class="studentrow-even">
                   	<logic:notEmpty name="reqStatusForm" property="studentDetailsTOList">
                            	<bean:write name="reqStatusForm" property="studentDetailsTOList.appliedDate"/>
                            </logic:notEmpty>
                   </td>
                 </tr>
                 <tr class="row-white">
                   <td width="39%" class="studentrow-odd"><div align="right"><bean:message key="knowledgepro.hostel.reqno"/></div></td>
                   <td width="61%" colspan="2" class="studentrow-even">
					 <logic:notEmpty name="reqStatusForm" property="studentDetailsTOList">
                            	<bean:write name="reqStatusForm" property="studentDetailsTOList.hostelReqNo"/>
                            </logic:notEmpty>
					</td>
                 </tr>
                 <tr class="row-white">
                   <td width="39%" class="studentrow-odd"><div align="right"><bean:message key="knowledgepro.hostel.name"/></div></td>
                   <td width="61%" colspan="2" class="studentrow-even">
					 <logic:notEmpty name="reqStatusForm" property="studentDetailsTOList">
                            	<bean:write name="reqStatusForm" property="studentDetailsTOList.hostelName"/>
                            </logic:notEmpty>
					</td>
                 </tr>
                 <tr class="row-white">
                   <td width="39%" class="studentrow-odd"><div align="right"><bean:message key="knowledgepro.hostel.reqroomtype"/></div></td>
                   <td width="61%" colspan="2" class="studentrow-even">
					  <logic:notEmpty name="reqStatusForm" property="studentDetailsTOList">
                            	<bean:write name="reqStatusForm" property="studentDetailsTOList.reqRoomType"/>
                            </logic:notEmpty>	
					</td>
                 </tr>
                  <tr class="row-white">
                   <td width="39%" class="studentrow-odd"><div align="right"><bean:message key="knowledgepro.hostel.approvedroomtype"/></div></td>
                   <td width="61%" colspan="2" class="studentrow-even">
					   <logic:notEmpty name="reqStatusForm" property="studentDetailsTOList">
                            	<bean:write name="reqStatusForm" property="studentDetailsTOList.approvedRoomType"/>
                            </logic:notEmpty>
					</td>
                 </tr>
                 <tr class="row-white">
                   <td width="39%" class="studentrow-odd"><div align="right"><bean:message key="knowledgepro.admission.status"/></div></td>
                   <td width="61%" colspan="2" class="studentrow-even">
					   <logic:notEmpty name="reqStatusForm" property="studentDetailsTOList">
                            	<bean:write name="reqStatusForm" property="studentDetailsTOList.status"/>
                            </logic:notEmpty>
					</td>
                 </tr>
                 <tr class="row-white">
                   <td height="35" colspan="3" class="row-white">
                   <table width="100%"  border="0" cellspacing="0" cellpadding="0">
                       <tr>
                         <td width="49%" height="35"><div align="right">
                         <input type="button" class="btnbg" value="Print" onclick="window.print()"/>
                         </div></td>
                         <td width="3%"></td>
                         <td width="48%"><input name="button" type="button" class="btnbg" value="Cancel" onclick="cancelAction()"/></td>
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