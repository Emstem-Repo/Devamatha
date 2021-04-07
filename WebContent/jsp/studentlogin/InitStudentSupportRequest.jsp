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
	function imposeMaxLength1(field, size) {
	    if (field.value.length > size) {
	        field.value = field.value.substring(0, size);
	    }
	}
	function len_display(Object,MaxLen,element){
	    var len_remain = MaxLen+Object.value.length;
	   if(len_remain <=500){
	    document.getElementById(element).value=len_remain; }
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
	<html:hidden property="method" styleId="method" value="addStudentRequest" />
	<html:hidden property="formName" value="studentSupportRequestForm" />
	<html:hidden property="pageType" value="1" />
	<table>
		<tr><td height="5"></td></tr>
		<tr>
		<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<img src="images/support RequestForStudentIcon.png" width="20" height="20" class="blinkImage"></td><td style="color: #FF4000;font-size: small;"> Please select a category and enter the problem description in 500 characters
		</td>
		</tr>
	</table>
	<table border="0" width="600" cellspacing="0" cellpadding="0" align="center">
       <tr>
         <td colspan="2" class="nav">Support Request </td>
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
							<FONT color="green" size="2"><html:messages id="msg"
								property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
								<br>
							</html:messages></FONT></div>
             </td>
             </tr>
	             <tr class="row-white">
                   <td width="39%" class="studentrow-odd"><div align="right"><span class="Mandatory">*</span><bean:message key="aknowledgepro.hostel.fineCategory.name"/></div></td>
                   <td width="61%" colspan="2" class="studentrow-even">
					<html:select property="categoryId" styleId="categoryId" style="width:250px;">
   						<html:option value="">--Select--</html:option>
       						<logic:notEmpty property="categoryMap" name="studentSupportRequestForm">
								<html:optionsCollection property="categoryMap" label="value" value="key"/>
							</logic:notEmpty>
   					</html:select>
				   </td>
	            </tr>
        		<tr class="row-white">
                   <td width="39%" class="studentrow-odd"><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.employee.holidays.description"/></div></td>
					<td width="61%">
						<table>
							<tr>
								<td width="100%">
									<html:textarea property="description" style="width: 100%" styleId="description" cols="30" rows="5" onkeypress="return imposeMaxLength1(this, 499);" onkeyup="len_display(this,0,'long_len')"></html:textarea>
								</td>
							</tr>
							<tr>
								<td width="100%">
									<input type="text" id="long_len" value="0" class="len" size="2" readonly="readonly" style="border: none; text-align: right; font-size: 11px; font-family: Verdana, Arial, Helvetica, sans-serif; color: red">/500 Characters
								</td>
							</tr>
						</table>
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
	<logic:notEmpty name="studentSupportRequestForm" property="list">
			<table width="100%" border="0" align="center" cellpadding="0"cellspacing="0">
				<tr>
					<td><img src="images/st_01.gif" width="5" height="5" /></td>
					<td width="914" background="images/st_02.gif"></td>
		
					<td><img src="images/st_03.gif" width="5" height="5" /></td>
				</tr>
				<tr>
					<td width="5" background="images/st_left.gif"></td>
					<td valign="top">
					<table width="100%" cellspacing="1" cellpadding="2">
						<tr >
		       				<td width="5%"  height="25" class="studentrow-odd" ><div align="center"><bean:message key="knowledgepro.slno"/></div></td>
		       				<td width="5%" height="25" class="studentrow-odd"><div align="center">Request ID</div></td>
		       				<td width="5%" height="25" class="studentrow-odd"><div align="center">Date Of Submission</div></td>
		       				<td width="20%" height="25" class="studentrow-odd"><div align="center"><bean:message key="aknowledgepro.hostel.fineCategory.name"/></div></td>
		       				<td width="40%" height="25" class="studentrow-odd"><div align="center"><bean:message key="knowledgepro.employee.holidays.description"/></div></td>
		       				<td width="5%" class="studentrow-odd"><div align="center">Status</div></td>
		       				<td width="20%" class="studentrow-odd"><div align="center">Remarks</div></td>
		             	</tr>
						<logic:iterate id="CME" name="studentSupportRequestForm" property="list" indexId="count">
		  					<c:choose>
		     					<c:when test="${temp == 0}">
		           				<tr>
		           					<td  height="25" class="row-white" ><div align="center"><c:out value="${count + 1}"/></div></td>
		           					<td  height="25" class="row-white"><div align="center"><bean:write name="CME" property="requestId"/></div></td>
		           					<td  height="25" class="row-white"><div align="center"><bean:write name="CME" property="dateOfSubmssion"/></div></td>
		           					<td  height="25" class="row-white"><div align="center"><bean:write name="CME" property="categoryName"/></div></td>
		           					<td  height="25" class="row-white"><bean:write name="CME" property="description"/></td>
		           					<td  height="25" class="row-white"><div align="center"><bean:write name="CME" property="status"/></div></td>
		           					<td  height="25" class="row-white"><div align="left"><bean:write name="CME" property="remarks"/></div></td>
		           				</tr>
		           				<c:set var="temp" value="1"/>
		          						</c:when>
		           				<c:otherwise>
		          				<tr>
		      						<td  height="25" class="studentrow-even"><div align="center"><c:out value="${count + 1}"/></div></td>
		      						<td  height="25" class="studentrow-even"><div align="center"><bean:write name="CME" property="requestId"/></div></td>
		      						<td  height="25" class="studentrow-even"><div align="center"><bean:write name="CME" property="dateOfSubmssion"/></div></td>
		      						<td  height="25" class="studentrow-even"><div align="center"><bean:write name="CME" property="categoryName"/></div></td>
		      						<td  height="25" class="studentrow-even"><bean:write name="CME" property="description"/></td>
		      						<td  height="25" class="studentrow-even"><div align="center"><bean:write name="CME" property="status"/></div></td>
		      						<td  height="25" class="studentrow-even"><div align="left"><bean:write name="CME" property="remarks"/></div></td>
		           				</tr>
		                		<c:set var="temp" value="0"/>
		  					</c:otherwise>
		      				</c:choose>
		    				</logic:iterate>
					</table>
					</td>
					<td width="5" height="30" background="images/st_right.gif"></td>
				</tr>
				<tr>
					<td height="5"><img src="images/st_04.gif" width="5" height="5" /></td>
					<td background="images/st_05.gif"></td>
					<td><img src="images/st_06.gif" /></td>
				</tr>
			</table>
		</logic:notEmpty>
</html:form>
