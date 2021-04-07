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
function resetFieldAndErrMsgs(){
	document.location.href = "PhdStudyAggrement.do?method=initPhdStudyAggrement";
	}
function addStudyAggrement() {
	    document.getElementById("method").value="addStudyAggrement";
                            }
function resetField() {
    document.getElementById("guide").value="";
    document.getElementById("guideEmpaneNo").value="";
    document.getElementById("coGuide").value="";
    document.getElementById("coGuideEmpaneNo").value="";
    document.getElementById(document.getElementById("signedOn").value).value="";
          }
function cancel() {
	document.location.href = "PhdStudyAgreement.do?method=initPhdStudyAgreement";
	}

   function deleteStudyAggrement(id) {
	  	deleteConfirm = confirm("Are you sure you want to delete this entry?");
		if (deleteConfirm) {
			document.location.href = "PhdStudyAgreement.do?method=deleteStudyAggrement&id="+id;
		}
	}
   function reActivate() {
		document.location.href = "PhdStudyAgreement.do?method=reactivateStudyAggrement";
	}
	
   function getEmpGuideNo(guideName,nameId) 
   {
	nameEmpNo=nameId;
  	var args ="method=getEmpanelmentNoByguideName&guideName="+guideName;
  	var url ="AjaxRequest.do";
  	requestOperation(url,args,updateEmpanelmentNo);	
     }
   function updateEmpanelmentNo(req) {
	     var pos;
		 var responseObj = req.responseText;
		 var empNo =responseObj.substring(0,pos);
		if(document.getElementById(nameEmpNo))
		{
			document.getElementById(nameEmpNo).value=empNo;
		}
	}
</script>

<html:form action="/PhdStudyAgreement" method="post">
	<html:hidden property="method" styleId="method" value="" />
	<html:hidden property="formName" value="phdStudyAggrementForm" />
	<html:hidden property="pageType" value="1" />
	<c:choose>
	<c:when test="${StudyAggrement == 'edit'}">
		<html:hidden property="method" styleId="method" value="updateStudyAggrement" />
	</c:when>
	<c:otherwise>
		<html:hidden property="method" styleId="method" value="addStudyAggrement" />
	</c:otherwise>
   </c:choose>
	
	<script language="JavaScript" src="calendar_us.js"></script>
	<link rel="stylesheet" href="calendar.css">
	<link href="../css/styles.css" rel="stylesheet" type="text/css">
	<table width="99%" border="0">

		<tr>
			<td><span class="Bredcrumbs"> <bean:message key="knowledgepro.phd" /><span class="Bredcrumbs">&gt;&gt; <bean:message key="knowledgepro.phd.PhdStudyAggrement" /> &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td valign="top">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
					<td colspan="2" background="images/Tcenter.gif" class="heading_white"><bean:message key="knowledgepro.phd.PhdStudyAggrement" /></td>
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
							<td width="5" background="images/left.gif"></td>
							<td valign="top">
							<table width="100%" cellspacing="1" cellpadding="2">
						    <tr>
							<td height="25" class="row-odd" width="25%"><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.hostel.allocation.regno"/></div></td>
							<td width="32%" height="25" class="row-even" ><label></label>
                            <span class="star">
                            <c:choose>
            		    <c:when test="${phdStudyAggrementForm.studentName == null}">
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
				<logic:notEmpty name="phdStudyAggrementForm" property="studentDetails">	
                        <tr>
                        <td width="5" background="images/left.gif"></td>
						<td valign="top">
						 <table width="100%" cellspacing="1" cellpadding="2">
			             <tr>
			             <td height="25" class="row-odd" width="25%"><div align="right"><bean:message key="knowledgepro.fee.studentname"/></div></td>
			             <td width="32%" height="25" class="row-even" ><label></label>
                         <span class="star">
                         <bean:write name="phdStudyAggrementForm" property="studentName"/>
                         </span></td>						
			             </tr>
			              <tr>
			             <td height="25" class="row-odd" width="25%"><div align="right"><bean:message key="knowledgepro.admission.courseName"/></div></td>
			             <td width="32%" height="25" class="row-even" ><label></label>
                         <span class="star">
                         <bean:write name="phdStudyAggrementForm" property="courseName"/>
                         </span></td>						
			             </tr>
			              <tr>
			             <td height="25" class="row-odd" width="25%"><div align="right"><bean:message key="knowledgepro.cancelattendance.batches"/></div></td>
			             <td width="32%" height="25" class="row-even" ><label></label>
                         <span class="star">
                         <bean:write name="phdStudyAggrementForm" property="batch"/>
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
                   <td width="45%" height="35"><div align="center">
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
        <logic:notEmpty name="phdStudyAggrementForm" property="studentDetails">
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
                    <td height="25" class="row-odd" align="center" ><bean:message key="knowledgepro.phd.Guide"/></td>
                    <td height="25" class="row-odd" align="center"><bean:message key="knowledgepro.phd.empanelmentNo"/></td>
                    <td height="25" class="row-odd" align="center"><bean:message key="knowledgepro.phd.CoGuide"/></td>
                    <td height="25" class="row-odd" align="center"><bean:message key="knowledgepro.phd.empanelmentNo"/></td>
                    <td height="25" class="row-odd" align="center"><bean:message key="knowledgepro.phd.signedOn"/></td>
                    <td class="row-odd"><div align="center"><bean:message key="knowledgepro.delete"/></div></td>
                  </tr>
                
                   	<tr>
                   	<logic:iterate id="CME" name="phdStudyAggrementForm" property="studentDetails" indexId="count">
                   		                             <%
										             String styleDate1 = "datePick" + count;
													 String styleDate2 = "datePicker" + count;
									                 %>
													<c:choose>
														<c:when test="${count%2 == 0}">
															<tr class="row-even">
														</c:when>
														<c:otherwise>
															<tr class="row-even">
														</c:otherwise>
					 								</c:choose>
                   		<td width="19%" height="25" class="white"><span	class="star"> 
				        <html:select property="guideId" styleClass="comboLarge" styleId="guide" onchange="getEmpGuideNo(this.value,'guideEmpaneNo')"><html:option value=""><bean:message key="knowledgepro.admin.select" />
				        </html:option><html:optionsCollection name="GuideDetails" label="name" value="id" /></html:select></span></td>
                   		 <td class="row-even"><html:text name="phdStudyAggrementForm" property="guideEmpaneNo" maxlength="30" size="30" styleId="guideEmpaneNo"/></td>
                   		<td width="19%" height="25" class="white"><span	class="star"> 
				        <html:select property="coGuideId" styleClass="comboLarge" styleId="coGuide" onchange="getEmpGuideNo(this.value,'coGuideEmpaneNo')"><html:option value=""><bean:message key="knowledgepro.admin.select" />
				        </html:option><html:optionsCollection name="GuideDetails" label="name" value="id" /></html:select></span></td>
                        <td class="row-even"><html:text name="phdStudyAggrementForm" property="coGuideEmpaneNo" maxlength="30" size="30" styleId="coGuideEmpaneNo"/></td>
                   		<td width="19%" height="25" class="bodytext"><div align="center">									
								<nested:text styleId='<%=styleDate1%>'
								property="signedOn" size="10" maxlength="10" />
										<script language="JavaScript">
							 new tcal( {
								// form name
								'formname' :'phdStudyAggrementForm',
								// input name
								'controlname' :'<%=styleDate1%>'
							});
						</script></div>
						</td>
                   		<td width="5%" height="25" class="row-even" ><div align="center">
                   			<img src="images/delete_icon.gif" width="16" height="16" style="cursor:pointer" onclick="deleteStudyAggrement('<bean:write name="CME" property="id"/>')"></div></td>
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
            <tr>
            <td width="45%" height="35"><div align="center">
			<html:submit property="" styleClass="formbutton" onclick="addStudyAggrement()"><bean:message key="knowledgepro.submit" /></html:submit>&nbsp;&nbsp;&nbsp;
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
