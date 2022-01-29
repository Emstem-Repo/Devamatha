<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@page import="java.util.Map,java.util.HashMap"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<script language="JavaScript" src="js/calendar.js"></script>
<link href="css/styles.css" rel="stylesheet" type="text/css">
<script language="JavaScript">

function addSettings() {
	    document.getElementById("method").value="addPhdSettings";
                            }

   function editSettings(id) {
		document.location.href = "PhdSettings.do?method=editPhdSettings&id="+ id;
	}

	function updateSettings() {
		document.getElementById("method").value = "updatePhdSettings";
	}

	function deleteSettings(id) {
		  	deleteConfirm = confirm("Are you sure you want to delete this entry?");
			if (deleteConfirm) {
				document.location.href = "PhdSettings.do?method=deletePhdSettings&id="+id;
			}
		}
	
	function resetField() {
		document.location.href = "PhdSettings.do?method=initPhdSettings";
         }
	function cancel() {
		document.location.href = "PhdSettings.do?method=initPhdSettings";
		}
			
	function reActivate() {
			document.location.href = "PhdSettings.do?method=reactivatePhdSettings";
		}
</script>

<html:form action="/PhdSettings" >
	<html:hidden property="method" styleId="method" value="" />
	<html:hidden property="formName" value="phdSettingForm" />
	<html:hidden property="pageType" value="1" />
	<c:choose>
	<c:when test="${PhdSettings == 'edit'}">
		<html:hidden property="method" styleId="method" value="updatePhdSettings" />
	</c:when>
	<c:otherwise>
		<html:hidden property="method" styleId="method" value="addPhdSettings" />
	</c:otherwise>
   </c:choose>
	
	<script language="JavaScript" src="calendar_us.js"></script>
	<link rel="stylesheet" href="calendar.css">
	<link href="../css/styles.css" rel="stylesheet" type="text/css">
	<table width="99%" border="0">

		<tr>
			<td><span class="Bredcrumbs"> <bean:message key="knowledgepro.phd" /><span class="Bredcrumbs">&gt;&gt; <bean:message key="knowledgepro.phd.PhdSettings" /> &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td valign="top">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
					<td colspan="2" background="images/Tcenter.gif" class="heading_white"><bean:message key="knowledgepro.phd.PhdSettings" /></td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9" height="29"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td colspan="2" class="news"><div align="right"><FONT color="red"><span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields" /></span></FONT></div>
					<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
					<FONT color="green"> <html:messages id="msg" property="messages" message="true">
						<c:out value="${msg}" escapeXml="false"></c:out><br>
					</html:messages> </FONT></div></td>
					<td valign="top" colspan="2" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td height="41" valign="top" background="images/Tright_03_03.gif"></td>

					<td valign="top" class="news">
					<table width="100%" border="0" align="center" cellpadding="0"
						cellspacing="0">
						<tr>
							<td><img src="images/01.gif" width="5" height="5" /></td>
							<td width="914" background="images/02.gif"></td>
							<td><img src="images/03.gif" width="5" height="5" /></td>
						</tr>
						<tr>
						<td width="5" height="29" background="images/left.gif"></td>
						<td valign="top">
					   <table width="100%">
						    <tr>
								<td width="30%" height="25" class="row-odd">
								<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message key="knowledgepro.phd.beforedate" />:</div>
								</td>
								  <td width="20%" class="row-even"><div align="left"> <span class="star">
                                  <html:text property="reminderMailBefore" styleId="reminderMailBefore" size="20" maxlength="3" onkeypress="return isNumberKey(event)"/>
                                  </span></div></td>
								<td width="30%" height="25" class="row-odd">
								<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message key="knowledgepro.phd.afterdate" />:</div>
								</td>
								  <td width="20%" class="row-even"><div align="left"> <span class="star">
                                 <html:text property="dueMailAfter" styleId="dueMailAfter" size="20" maxlength="3" onkeypress="return isNumberKey(event)"/>
                                 </span></div></td>
							</tr>
							    <tr>
								<td width="30%" height="25" class="row-odd">
								<div align="right"><bean:message key="KnowledgePro.phd.setting.maxguides.assigned" />:</div>
								</td>
								  <td width="20%" class="row-even"><div align="left"> <span class="star">
                                  <html:text property="maxGuidesAssign" styleId="maxGuidesAssign" size="20" maxlength="9" onkeypress="return isNumberKey(event)"/>
                                  </span></div></td>
								<td width="30%" height="25" class="row-odd"></td>
								<td width="20%" class="row-even"></td>
							</tr>
						<tr>
                         <td height="25" colspan="4">
						<div align="center">
						<c:choose>
						<c:when test="${PhdSettings != null && PhdSettings == 'edit'}">
						<html:submit property="" styleClass="formbutton" onclick="updateSettings()"><bean:message key="knowledgepro.update" /></html:submit>&nbsp;&nbsp;&nbsp;
						<html:button property="" styleClass="formbutton" onclick="cancel()"><bean:message key="knowledgepro.cancel" /></html:button>
						</c:when>
						<c:otherwise>
						<html:submit property="" styleClass="formbutton" onclick="addSettings()"><bean:message key="knowledgepro.submit" /></html:submit>&nbsp;&nbsp;&nbsp;
						<html:button property="" styleClass="formbutton" onclick="resetField()"><bean:message key="knowledgepro.admin.reset" /></html:button>
						</c:otherwise>
						</c:choose></div>
						</td>
                       </tr>
							</table>
							</td>
							<td width="5" height="29" background="images/right.gif"></td>
						</tr>
				          <tr>
							<td height="5"><img src="images/04.gif" width="5" height="5" /></td>
							<td background="images/05.gif"></td>
							<td><img src="images/06.gif" /></td>
						</tr>
						
						</table>
					</td>
					<td valign="top" colspan="2" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
		 <tr>
        <td height="122" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="100%" cellspacing="1" cellpadding="2">
        <logic:notEmpty name="phdSettingForm" property="settingDetails">
          <tr>
            <td height="45" colspan="4" ><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
              <tr>
                <td ><img src="images/01.gif" width="5" height="5" /></td>
                <td width="914" background="images/02.gif"></td>
                <td><img src="images/03.gif" width="5" height="5" /></td>
              </tr>
              <tr>
                <td width="5"  background="images/left.gif"></td>
                <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
                  <tr >
                    <td width="10%" height="25" class="row-odd" align="center"><div align="center"><bean:message key="knowledgepro.slno" /></div></td>
                    <td width="20%" height="25" class="row-odd" align="center" ><bean:message key="knowledgepro.phd.beforedate"/></td>
                    <td width="25%" height="25" class="row-odd" align="center"><bean:message key="knowledgepro.phd.afterdate"/></td>
                    <td width="25%" height="25" class="row-odd" align="center"><bean:message key="KnowledgePro.phd.setting.maxguides.assigned"/></td>
                    <td width="10%" height="25" class="row-odd" align="center"><div align="center"><bean:message key="knowledgepro.edit" /></div></td>
					<td width="10%" height="25" class="row-odd" align="center"><div align="center"><bean:message key="knowledgepro.delete" /></div></td>
                  </tr>
                
                   	<tr>
                   	<logic:iterate id="CME" name="phdSettingForm" property="settingDetails" indexId="count">
                   		 <c:choose>
                   <c:when test="${temp == 0}">
                   	<tr>
                   	<td height="20" class="row-even" align="center"><div align="center"><c:out value="${count+1}" /></div></td>
                    <td height="20" class="row-even" align="center"><bean:write name="CME" property="reminderMailBefore" /></td>
					<td height="20" class="row-even" align="center"><bean:write name="CME" property="dueMailAfter" /></td>
					<td height="20" class="row-even" align="center"><bean:write name="CME" property="maxGuidesAssign" /></td>
					<td height="20" class="row-even" ><div align="center">
					<img src="images/edit_icon.gif" width="16" height="18" style="cursor: pointer" onclick="editSettings('<bean:write name="CME" property="id"/>')"></div></td>
                   	 <td height="20" class="row-even" ><div align="center">
                   	<img src="images/delete_icon.gif" width="16" height="16" style="cursor:pointer" onclick="deleteSettings('<bean:write name="CME" property="id"/>')"></div></td>
					</tr>
                    <c:set var="temp" value="1"/>
                   	</c:when>
                    <c:otherwise>
		            <tr>
               		<td height="20" class="row-white" align="center"><div align="center"><c:out value="${count+1}" /></div></td>
                    <td height="20" class="row-white" align="center"><bean:write name="CME" property="reminderMailBefore" /></td>
					<td height="20" class="row-white" align="center"><bean:write name="CME" property="dueMailAfter" /></td>
					<td height="20" class="row-white" align="center"><bean:write name="CME" property="maxGuidesAssign" /></td>
					<td height="20" class="row-white" ><div align="center">
					<img src="images/edit_icon.gif" width="16" height="18" style="cursor: pointer" onclick="editSettings('<bean:write name="CME" property="id"/>')"></div></td>
                   	 <td height="20" class="row-white" ><div align="center">
                   	<img src="images/delete_icon.gif" width="16" height="16" style="cursor:pointer" onclick="deleteSettings('<bean:write name="CME" property="id"/>')"></div></td>
               		</tr>
                    <c:set var="temp" value="0"/>
				  	</c:otherwise>
                  </c:choose>
                </logic:iterate>
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
          </tr>
          </logic:notEmpty>
        </table></td>
        <td width="10" valign="top" colspan="2" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
				<tr>
					<td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
					<td colspan="2" background="images/TcenterD.gif"></td>

					<td><img src="images/Tright_02.gif" width="9" height="29"></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>

</html:form>
