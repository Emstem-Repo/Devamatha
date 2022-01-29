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

<script language="JavaScript">

function resetFieldAndErrMsgs(){
	document.location.href = "PhdGuideDetails.do?method=initGuideDetails";
	}
function addGuideDetails() {
	    document.getElementById("method").value="addGuideDetails";
                            }
function editSynopsisDefense(id) {
	document.location.href = "PhdGuideDetails.do?method=editGuideDetails&id="+id;
	}
   function updateGuideDetails() {
	document.getElementById("method").value = "updateGuideDetails"; 
	}
   function deleteSynopsisDefense(id) {
	  	deleteConfirm = confirm("Are you sure you want to delete this entry?");
		if (deleteConfirm) {
			document.location.href = "PhdGuideDetails.do?method=deleteGuideDetails&id="+id;
		}
	}
   function reActivate() {
		document.location.href = "PhdGuideDetails.do?method=reactivateGuideDetails";
	}
</script>

<html:form action="/PhdGuideDetails" method="post">
	<html:hidden property="method" styleId="method" value="" />
	<html:hidden property="formName" value="phdGuideDetailsForm" />
	<html:hidden property="pageType" value="1" />
	<c:choose>
	<c:when test="${GuideDetails == 'edit'}">
		<html:hidden property="method" styleId="method" value="updateSynopsisDefense" />
	</c:when>
	<c:otherwise>
		<html:hidden property="method" styleId="method" value="addSynopsisDefense" />
	</c:otherwise>
   </c:choose>
	
	<script language="JavaScript" src="calendar_us.js"></script>
	<link rel="stylesheet" href="calendar.css">
	<link href="../css/styles.css" rel="stylesheet" type="text/css">
	<table width="99%" border="0">

		<tr>
			<td><span class="Bredcrumbs"> <bean:message key="knowledgepro.phd" /><span class="Bredcrumbs">&gt;&gt; <bean:message key="knowledgepro.phd.GuideDetails" /> &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td valign="top">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
					<td colspan="2" background="images/Tcenter.gif" class="heading_white"><bean:message key="knowledgepro.phd.GuideDetails" /></td>
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
        <td height="44" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td ><img src="images/01.gif" width="5" height="5" /></td>
            <td width="914" background="images/02.gif"></td>
            <td><img src="images/03.gif" width="5" height="5" /></td>
          </tr>
          <tr>
            <td width="5"  background="images/left.gif"></td>
            <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
              <tr >
                <td width="16%" height="25" class="row-odd" ><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.phd.guideDetails"/></div></td>
                <td width="34%" height="25" class="row-even" ><label></label>
                    <span class="star">
                    <html:text property="name" styleId="name" size="50" maxlength="100"/>
                  </span></td>
                <td width="16%" height="25" class="row-odd"><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.phd.empanelmentNo"/>:</div></td>
                <td width="34%" class="row-even"><div align="left"> <span class="star">
                    <html:text property="empanelmentNo" styleId="empanelmentNo" size="20" maxlength="10" onkeypress="return isNumberKey(event)"/>
                </span></div></td>
              </tr>
               <tr >
                <td width="16%" height="25" class="row-odd" ><div align="right"><bean:message key="admissionForm.phone.main.label"/></div></td>
                <td width="34%" height="25" class="row-even" ><label></label>
                    <span class="star">
                    <html:text property="phoneNo" styleId="phoneNo" size="20" maxlength="10" onkeypress="return isNumberKey(event)"/>
                  </span></td>
                <td width="16%" height="25" class="row-odd"><div align="right"><bean:message key="knowledgepro.employee.EmContact.mobile"/>:</div></td>
                <td width="34%" class="row-even"><div align="left"> <span class="star">
                    <html:text property="mobileNo" styleId="mobileNo" size="20" maxlength="10" onkeypress="return isNumberKey(event)"/>
                </span></div></td>
              </tr>
               <tr >
                <td width="16%" height="25" class="row-odd" ><div align="right"><bean:message key="admissionForm.studentinfo.addrs1.label"/></div></td>
                <td width="34%" height="25" class="row-even" ><label></label>
                    <span class="star">
                    <html:text property="addressLine1" styleId="addressLine1" size="70" maxlength="100"/>
                  </span></td>
                <td width="16%" height="25" class="row-odd"><div align="right"><bean:message key="admissionForm.studentinfo.addrs2.label"/></div></td>
                <td width="34%" class="row-even"><div align="left"> <span class="star">
                    <html:text property="addressLine2" styleId="addressLine2" size="70" maxlength="100"/>
                </span></div></td>
              </tr>
             <tr >
                <td width="16%" height="25" class="row-odd" ><div align="right"><bean:message key="admissionForm.studentinfo.addrs3.label"/></div></td>
                <td width="34%" height="25" class="row-even" ><label></label>
                    <span class="star">
                    <html:text property="addressLine3" styleId="addressLine3" size="70" maxlength="100"/>
                  </span></td>
                <td width="16%" height="25" class="row-odd"><div align="right"><bean:message key="admissionForm.studentinfo.addrs4.label"/></div></td>
                <td width="34%" class="row-even"><div align="left"> <span class="star">
                    <html:text property="addressLine4" styleId="addressLine4" size="70" maxlength="100"/>
                </span></div></td>
              </tr>
              <tr >
                <td width="16%" height="25" class="row-odd" ><div align="right"><bean:message key="knowledgepro.exam.email"/></div></td>
                <td width="34%" height="25" class="row-even" ><label></label>
                    <span class="star">
                    <html:text property="email" styleId="email" size="45" maxlength="50"/>
                  </span></td>
                <td width="16%" height="25" class="row-odd"><div align="right"><bean:message key="knowledgepro.exam.pin"/>:</div></td>
                <td width="34%" class="row-even"><div align="left"> <span class="star">
                    <html:text property="pinCode" styleId="pinCode" size="20" maxlength="6" onkeypress="return isNumberKey(event)"/>
                </span></div></td>
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
        <td valign="top" colspan="2" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
	 <tr>
        <td height="122" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="100%" cellspacing="1" cellpadding="2">
          <tr bgcolor="#FFFFFF">
            <td width="100%" height="20" colspan="4"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="45%" height="35"><div align="right">
                   <c:choose>
            		<c:when test="${GuideDetails == 'edit'}">
              	   		<html:submit property="" styleClass="formbutton" onclick="updateGuideDetails()"><bean:message key="knowledgepro.update"/></html:submit>
              		</c:when>
              		<c:otherwise>
                		<html:submit property="" styleClass="formbutton" onclick="addGuideDetails()"><bean:message key="knowledgepro.submit"/></html:submit>
              		</c:otherwise>
              	</c:choose>
                </div></td>
                <td width="2%"></td>
                <td width="53%">
                 <c:choose>
					<c:when test="${GuideDetails == 'edit'}">
					<html:button property="" styleClass="formbutton" onclick="resetFieldAndErrMsgs()"><bean:message key="knowledgepro.cancel"/></html:button>
					</c:when>
					<c:otherwise>
					<html:button property="" styleClass="formbutton" value="Reset" onclick="resetFieldAndErrMsgs()"></html:button>
					</c:otherwise>
				</c:choose>
				</td>
              </tr>
            </table></td>
          </tr>
           <logic:notEmpty name="phdGuideDetailsForm" property="guideDetails">
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
                    <td height="25" class="row-odd" ><div align="center"><bean:message key="knowledgepro.slno"/></div></td>
                     <td height="25" class="row-odd" align="center"><bean:message key="knowledgepro.phd.guideDetails"/></td>
                    <td height="25" class="row-odd" align="center" ><bean:message key="knowledgepro.phd.empanelmentNo"/></td>
                    <td height="25" class="row-odd" align="center"><bean:message key="admissionForm.phone.main.label"/></td>
                    <td height="25" class="row-odd" align="center"><bean:message key="admissionForm.mob.no.label"/></td>
                    <td height="25" class="row-odd" align="center"><bean:message key="admissionForm.studentinfo.addrs1.label"/></td>
                    <td height="25" class="row-odd" align="center"><bean:message key="knowledgepro.exam.email"/></td>
                    <td class="row-odd"><div align="center"><bean:message key="knowledgepro.edit"/></div></td>
                    <td class="row-odd"><div align="center"><bean:message key="knowledgepro.delete"/></div></td>
                  </tr>
                <c:set var="temp" value="0"/>
                <logic:iterate id="CME" name="phdGuideDetailsForm" property="guideDetails" indexId="count">
                <c:choose>
                   <c:when test="${temp == 0}">
                   	<tr>
                   		<td width="6%" height="25" class="row-even" ><div align="center"><c:out value="${count + 1}"/></div></td>
                   		<td width="15%" height="25" class="row-even" align="center"><bean:write name="CME" property="name"/></td>
                   		<td width="10%" height="25" class="row-even" align="center"><bean:write name="CME" property="empanelmentNo"/></td>
                   		<td width="15%" height="25" class="row-even" align="center"><bean:write name="CME" property="phoneNo"/></td>
                   		<td width="10%" height="25" class="row-even" align="center"><bean:write name="CME" property="mobileNo"/></td>
                   		<td width="20%" height="25" class="row-even" align="center"><bean:write name="CME" property="addressLine1"/></td>
                   		<td width="20%" height="25" class="row-even" align="center"><bean:write name="CME" property="email"/></td>
			            <td width="7%" height="25" class="row-even" ><div align="center">
			        		<img src="images/edit_icon.gif" width="16" height="18" style="cursor:pointer" onclick="editSynopsisDefense('<bean:write name="CME" property="id"/>')"></div></td>
                   		<td width="7%" height="25" class="row-even" ><div align="center">
                   			<img src="images/delete_icon.gif" width="16" height="16" style="cursor:pointer" onclick="deleteSynopsisDefense('<bean:write name="CME" property="id"/>')"></div></td>
					</tr>
                    <c:set var="temp" value="1"/>
                   	</c:when>
                    <c:otherwise>
		            <tr>
               			<td width="6%" height="25" class="row-white"><div align="center"><c:out value="${count + 1}"/></div></td>
               			<td width="15%" height="25" class="row-white" align="center"><bean:write name="CME" property="name"/></td>
               			<td width="10%" height="25" class="row-white" align="center"><bean:write name="CME" property="empanelmentNo"/></td>
               			<td width="15%" height="25" class="row-white" align="center"><bean:write name="CME" property="phoneNo"/></td>
               			<td width="10%" height="25" class="row-white" align="center"><bean:write name="CME" property="mobileNo"/></td>
               			<td width="20%" height="25" class="row-white" align="center"><bean:write name="CME" property="addressLine1"/></td>
               			<td width="20%" height="25" class="row-white" align="center"><bean:write name="CME" property="email"/></td>
               			<td width="7%" height="25" class="row-white" ><div align="center">
               				<img src="images/edit_icon.gif" width="16" height="18" style="cursor:pointer" onclick="editSynopsisDefense('<bean:write name="CME" property="id"/>')"></div></td>
               			<td width="7%" height="25" class="row-white" ><div align="center">
               				<img src="images/delete_icon.gif" width="16" height="16" style="cursor:pointer" onclick="deleteSynopsisDefense('<bean:write name="CME" property="id"/>')"></div></td>
               		</tr>
                    <c:set var="temp" value="0"/>
				  	</c:otherwise>
                  </c:choose>
                </logic:iterate>
                
                
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
