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
var nameEmpNo;
function searchStudent(){
	document.getElementById("method").value="searchStudentList";
    }
function addCompletionDetails() {
    document.getElementById("method").value="addCompletionDetails";
                        }
function resetField() {
    document.getElementById("title").value=document.getElementById("oldTitle").value;
    document.getElementById("discipline").value=document.getElementById("oldDiscipline").value;
    document.getElementById("vivaVoice").value=document.getElementById("oldVivaVoice").value;
          }
function cancel() {
	document.location.href = "PhdCompletionDetails.do?method=initPhdCompletionDetails";
	}
function deleteCompletionDetails(id) {
  	deleteConfirm = confirm("Are you sure you want to delete this entry?");
	if (deleteConfirm) {
		document.location.href = "PhdCompletionDetails.do?method=deleteCompletionDetails&id="+id;
	}
}
function reActivate() {
	document.location.href = "PhdCompletionDetails.do?method=reactivateCompletionDetails";
}
function imposeMaxLength(field, size) {
    if (field.value.length > size) {
        field.value = field.value.substring(249, size);
    }
}
</script>
<html:form action="/PhdCompletionDetails" method="post">
	<html:hidden property="method" styleId="method" value="" />
	<html:hidden property="formName" value="phdCompletionDetailsForm" />
	<html:hidden property="pageType" value="1" />
	
	<c:choose>
	<c:when test="${CompletionDetails == 'edit'}">
		<html:hidden property="method" styleId="method" value="updateCompletionDetails" />
	</c:when>
	<c:otherwise>
		<html:hidden property="method" styleId="method" value="addCompletionDetails" />
	</c:otherwise>
   </c:choose>
   
   <script language="JavaScript" src="calendar_us.js"></script>
	<link rel="stylesheet" href="calendar.css">
	<link href="../css/styles.css" rel="stylesheet" type="text/css">
	<table width="99%" border="0">
		<tr>
		<td><span class="Bredcrumbs">
		<bean:message key="knowledgepro.phd" /><span class="Bredcrumbs">&gt;&gt; <bean:message key="knowledgepro.phd.PhdCompletionDetails" /> &gt;&gt;</span></span></td>
		</tr>
		<tr>
		<td valign="top">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
					<td colspan="2" background="images/Tcenter.gif" class="heading_white"><bean:message key="knowledgepro.phd.PhdCompletionDetails" /></td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9" height="29"></td>
				</tr>
				<tr>
					<td height="30" valign="top" background="images/Tright_03_03.gif"></td>
					<td colspan="2" class="news"><div align="right"><FONT color="red"><span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields" /></span></FONT></div>
					<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
					<FONT color="green"> <html:messages id="msg" property="messages" message="true">
						<c:out value="${msg}" escapeXml="false"></c:out><br>
					</html:messages> </FONT></div></td>
					<td valign="top" colspan="2" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td height="30" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
					<tr>
							<td><img src="images/01.gif" width="5" height="5" /></td>
							<td width="914" background="images/02.gif"></td>
							<td><img src="images/03.gif" width="5" height="5" /></td>
						</tr>
						<tr>
							<td width="5" background="images/left.gif"></td>
							<td valign="top">
							<table width="100%" cellspacing="1" cellpadding="2">
						    <tr>
							<td height="25" class="row-odd" width="28%"><div align="right"><span class="Mandatory">*</span>&nbsp;
							<bean:message key="knowledgepro.hostel.allocation.regno"/>:</div></td>
							<td width="32%" height="25" class="row-even" ><label></label>
                            <span class="star">
                            <c:choose>
            		    <c:when test="${phdCompletionDetailsForm.studentName == null}">
              	   		<html:text property="registerNo" styleId="registerNo" size="20" maxlength="50" />
              		    </c:when>
              		    <c:otherwise>
                		<html:text property="registerNo" styleId="registerNo" size="20" maxlength="50" disabled="true"/>
              		    </c:otherwise>
              	     </c:choose>
                       </span></td>						
							</tr>
							</table>
							</td>
							<td width="5" height="29" background="images/right.gif"></td>
						</tr>
						<logic:notEmpty name="phdCompletionDetailsForm" property="studentDetails">	
                        <tr>
                        <td width="5" background="images/left.gif"></td>
						<td valign="top">
						 <table width="100%" cellspacing="1" cellpadding="2">
			             <tr>
			             <td height="25" class="row-odd" width="25%"><div align="right"><bean:message key="knowledgepro.fee.studentname"/>:</div></td>
			             <td width="32%" height="25" class="row-even" ><label></label>
                         <span class="star">
                         <bean:write name="phdCompletionDetailsForm" property="studentName"/>
                         </span></td>
			             </tr>
			              <tr>
			             <td height="25" class="row-odd" width="28%"><div align="right"><bean:message key="knowledgepro.admission.courseName"/>:</div></td>
			             <td width="32%" height="25" class="row-even" ><label></label>
                         <span class="star">
                         <bean:write name="phdCompletionDetailsForm" property="courseName"/>
                         </span></td>						
			             </tr>
			              <tr>
			             <td height="25" class="row-odd" width="25%"><div align="right"><bean:message key="knowledgepro.cancelattendance.batches"/>:</div></td>
			             <td width="32%" height="25" class="row-even" ><label></label>
                         <span class="star">
                         <bean:write name="phdCompletionDetailsForm" property="batch"/>
                         </span></td>						
			             </tr>
                         </table>
                         </td>
							<td width="5" height="29" background="images/right.gif"></td>
                        </tr>
                        </logic:notEmpty>
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
					<td height="36" valign="top" background="images/Tright_03_03.gif"></td>
					<td colspan="2" valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
				   <tr>
				   <td width="40%" height="35"><div align="center">
				   <html:submit property="" styleClass="formbutton" onclick="searchStudent()"><bean:message key="knowledgepro.admin.search"/></html:submit>
				   </div></td>
                 </tr>
			 </table>
			 </td>
					<td valign="top"  colspan="2" background="images/Tright_3_3.gif" class="news"></td>
					</tr>
					<tr>
					<td height="122" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news"><table width="100%" cellspacing="1" cellpadding="2">
        					<logic:notEmpty name="phdCompletionDetailsForm" property="studentDetails">
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
                  <tr>
                  <logic:iterate id="CME" name="phdCompletionDetailsForm" property="studentDetails" indexId="count">
									        <c:choose>
													<c:when test="${count%2 == 0}">
															<tr class="row-even">
													</c:when>
													<c:otherwise>
															<tr class="row-even">
													</c:otherwise>
					 						</c:choose>         
                    <td height="25" class="row-odd" align="right" rowspan="2" width="20%"><bean:message key="knowledgepro.employee.title"/>:
                  <td class="row-even" rowspan="2" width="27%">
						<input type="hidden" id="oldTitle" name="oldTitle" value='<bean:write property="title" name="phdCompletionDetailsForm"/>'/>
				<html:textarea name="phdCompletionDetailsForm" property="title" cols="3" rows="10" style="width: 230px; height: 90px;" styleId="title" onkeypress="return imposeMaxLength(this, 0);"></html:textarea></td>
					<td width="20%" height="25" class="row-odd" align="right" ><bean:message key="knowledgepro.phd.PhdCompletionDetails.vivavoice"/>:</td>
						<input type="hidden" id="oldVivaVoice" name="oldVivaVoice" value='<bean:write property="vivaVoice" name="phdCompletionDetailsForm"/>'/>
                    <td width="26%" height="25" class="bodytext"><div align="left">								
								<nested:text styleId="vivaVoice"
								property="vivaVoice" size="10" maxlength="10" />
										<script language="JavaScript">
							 new tcal( {
								// form name
								'formname' :'phdCompletionDetailsForm',
								// input name
								'controlname' :'vivaVoice'
							});
						</script></div>
						</td>
						<td class="row-odd" align="center">Delete</td>
						<tr>
					<td width="20%" height="25" class="row-odd" align="right"><bean:message key="knowledgepro.phd.PhdCompletionDetails.discipline"/>:</td>
					<td class="row-even">
					<input type="hidden" id="oldDiscipline" name="oldDiscipline" value='<bean:write property="discipline" name="phdCompletionDetailsForm"/>'/>
					<html:text name="phdCompletionDetailsForm" property="discipline" maxlength="150" size="30" styleId="discipline"/></td>
					<td class="row-odd" align=center>
					<img src="images/delete_icon.gif" width="16" height="16" style="cursor:pointer" onclick="deleteCompletionDetails('<bean:write name="CME" property="id"/>')">
					</td>
                 </tr></td>
                 </logic:iterate> </tr>
                  
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
          <tr>
            <td width="50%" height="35"><div align="center">
			<html:submit property="" styleClass="formbutton" onclick="addCompletionDetails()"><bean:message key="knowledgepro.submit" /></html:submit>&nbsp;&nbsp;&nbsp;
			<html:button property="" styleClass="formbutton" onclick="resetField()"><bean:message key="knowledgepro.admin.reset"/></html:button>&nbsp;&nbsp;&nbsp;
			<html:button property="" styleClass="formbutton" onclick="cancel()"><bean:message key="knowledgepro.phd.StudyAggrement.newEntry"/></html:button></div></td>
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