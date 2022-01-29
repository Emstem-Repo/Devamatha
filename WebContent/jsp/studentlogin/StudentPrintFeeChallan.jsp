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
	function printChallan(financialYear,billNo){
		var url="StudentLoginAction.do?method=printFeeChallan&financialYear="+financialYear+"&billNo="+billNo;
		myRef = window.open(url,"challan_details","left=20,top=20,width=800,height=500,toolbar=1,resizable=0,scrollbars=1");
	}
	</script>
<html:form action="/StudentLoginAction" >
	<html:hidden property="method" styleId="method" value="printFeeChallan" />
	<html:hidden property="formName" value="loginform" />
	<html:hidden property="pageType" value="1" />
	<html:hidden property="billNo" styleId="billNo" name="loginform" />
	<html:hidden property="financialYear" styleId="financialYear" name="loginform" />
	<table border="0" width="600" cellspacing="0" cellpadding="0">
       <tr>
         <td colspan="2" class="nav">Print Fee Challan </td>
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
							<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
							<FONT color="green"><html:messages id="msg"
								property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
								<br>
							</html:messages></FONT></div>
             </td>
             </tr>
        		<tr class="row-white">
                   <td width="39%" class="heading" colspan="3"><div align="left"><bean:message key="knowledgepro.applicationform.challan.link"/></div></td>
                 </tr>
                 <tr class="row-white">
                 <td width="40%" class="studentrow-odd"><div align="center">Challan Printed Date</div></td>
                 <td width="30%" class="studentrow-odd"><div align="center"><bean:message key="admissionFormForm.challanNo.required"/></div></td>
                <td width="30%" class="studentrow-odd"><div align="center">Print Here</div></td>
                 </tr>
                 <logic:notEmpty name="loginform" property="feeToList">
                 <c:set var="temp" value="0" />
                 <nested:iterate name="loginform" property="feeToList" id="feeToList">
				<c:choose>
				<c:when test="${temp == 0}">
                 <tr class="row-white">
                 <td width="40%" class="studentrow-even"><div align="center"><bean:write name="feeToList"
															property="challenPrintedDate" /></div></td>
                 <td width="30%" class="studentrow-even"><div align="center"><bean:write name="feeToList"
															property="billNo" /></div></td>
                <td width="30%" class="studentrow-even"><div align="center"><a href="#" onclick="printChallan('<bean:write name="feeToList"
				property="financialYear" />','<bean:write name="feeToList"
															property="billNo" />')"><bean:message key="knowledgepro.applicationform.challan.link" />
					</a></div></td>
                <c:set var="temp" value="1" />
                 </tr>
                 </c:when>
				<c:otherwise>
					<tr class="row-white">
	                 <td width="40%" class="row-white"><div align="center"><bean:write name="feeToList"
															property="challenPrintedDate" /></div></td>
	                 <td width="30%" class="row-white"><div align="center"><bean:write name="feeToList"
															property="billNo" /></div></td>
	                <td width="30%" class="row-white"><div align="center"><a href="#" onclick="printChallan('<bean:write name="feeToList"
					property="financialYear" />','<bean:write name="feeToList"
						property="billNo" />')"><bean:message key="knowledgepro.applicationform.challan.link" />
					</a></div></td>
	                <c:set var="temp" value="1" />
	                 </tr>
                 <c:set var="temp" value="0" />
				</c:otherwise>
				</c:choose>
				</nested:iterate>
				</logic:notEmpty>
                 <tr class="row-white">
                   <td colspan="2"><div align="center">
					&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
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