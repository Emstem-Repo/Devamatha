<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<script language="JavaScript" >
function resetMessages() {
	document.getElementById("toAM").checked =false;
	document.getElementById("fromAM").checked =false;
	document.getElementById("toAM1").checked =false;
	document.getElementById("fromAM1").checked =false;
	resetFieldAndErrMsgs();
}

function cancel()
{
	
	document.getElementById("method").value="initEmpApplyLeave";
	document.applyLeaveForm.submit();
}
</script>
<html:form action="/empApplyLeave">	
	<html:hidden property="method" styleId="method" value="startCancelLeave" />
	<html:hidden property="formName" value="applyLeaveForm" />
	<html:hidden property="leaveId" styleId="leaveId"/>
	<html:hidden property="pageType" value="3" />
	<table width="100%" border="0">
 		<tr>
			<td><span class="Bredcrumbs"><bean:message key="knowledgepro.admin.sec.EmployeeCategory" />
			<span class="Bredcrumbs">&gt;&gt; <bean:message key="knowledgepro.emp.applyLeave.display" /> &gt;&gt;</span></span></td>
		</tr>
  		<tr>
    		<td>
    			<table width="100%" border="0" cellpadding="0" cellspacing="0">
      				<tr>
        				<td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        				<td background="images/Tcenter.gif" class="body" ><strong class="boxheader"><bean:message key="knowledgepro.emp.applyLeave.display"/></strong></td>
        				<td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      				</tr>
				    <tr>
						<td height="20" valign="top" background="images/Tright_03_03.gif"></td>
						<td valign="top" class="news">
							<div align="right"><FONT color="red"> <span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields"/></span></FONT></div>
							<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
							<FONT color="green"> 
								<html:messages id="msg" property="messages" message="true">
									<c:out value="${msg}" escapeXml="false"></c:out>
									<br>
								</html:messages> </FONT></div>
						</td>
						<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
					</tr>
      				<tr>
        				<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        				<td valign="top" class="news">
        					<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
          						<tr>
            						<td ><img src="images/01.gif" width="5" height="5" /></td>
            						<td width="914" background="images/02.gif"></td>
            						<td><img src="images/03.gif" width="5" height="5" /></td>
          						</tr>
          						<tr>
            						<td width="5"  background="images/left.gif"></td>
            						<td valign="top">
            							<table width="100%" cellspacing="1" cellpadding="2">
            								<tr>
            									<td width="34%" class="row-even">
            										<table width="100%" cellspacing="1" cellpadding="2">
														<tr>
                											<td width="26%"  class="row-odd" >
                												<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message key="knowledgepro.attendance.activityattendence.leavetype"/>:</div>
                											</td>
                											<td class="row-even" colspan="3">
                												<html:select name="applyLeaveForm" property="leaveId" styleId="leaveId" styleClass="combo" disabled="true">
																	<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
																	<html:optionsCollection name="applyLeaveForm" property="allotedLeaveList" label="name" value="id" />
																</html:select> 
                								            </td>
                								            <td>&nbsp;</td>
              											</tr>
              											<tr>
                											<td width="26%"  class="row-odd" ><div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message key="knowledgepro.attendance.leavemodify.fromdate"/>:</div></td>
                											<td width="23%" height="25" class="row-even" >
                												<table width="189" border="0" cellspacing="0" cellpadding="0">
                    												<tr>
                      													<td width="60">
                      														<html:text name="applyLeaveForm"  disabled="true" property="fromDate" styleId="fromDate" size="10" maxlength="16"/> 
                      													</td>
  																    </tr>
                  												</table>
                  											</td>
                  										</tr>
                										<tr>
                											<td width="20%" class="row-odd"><div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message key="knowledgepro.attendance.leavemodify.todate"/>:</div></td>
                											<td class="row-even">
                												<table width="188" border="0" cellspacing="0" cellpadding="0">
                  													<tr>
                    													<td width="60"><html:text name="applyLeaveForm" disabled="true" property="toDate" styleId="toDate" size="10" maxlength="16"/> </td>
                    	                  							</tr>
                												</table>
                											</td>
              											</tr>
              											<tr>
                											<td width="26%"  class="row-odd" ><div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message key="knowledgepro.emp.applyLeave.cancelLeave.reason"/>:</div></td>
                											<td class="row-even" colspan="3"><span class="row-white">
                												<html:textarea property="cancelReason" name="applyLeaveForm" cols="28" rows="4" onkeypress="return imposeMaxLength(event,this)"></html:textarea>
                												</span>
                											</td>
                											<td>&nbsp;</td>
              											</tr>
            										</table>
            									</td>
                  							</tr>
            							</table>
            						</td>
            						<td width="5" height="30"  background="images/right.gif"></td>
          						</tr>
          						<tr>
            						<td height="5"><img src="images/04.gif" width="5" height="5" /></td>
            						<td background="images/05.gif"></td>
            						<td><img src="images/06.gif" /></td>
          						</tr>
        					</table>
        				</td>
        				<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      				</tr>
      				<tr>
        				<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        				<td class="heading">
        					<table width="100%"  border="0" cellspacing="0" cellpadding="0">
          						<tr>
									<td width="46%" height="35">
										<div align="right">
											<html:submit styleClass="formbutton">
												<bean:message key="knowledgepro.emp.applyLeave.cancelLeave" />
											</html:submit>
										</div>
									</td>
									<td width="2%"></td>
									<td width="52%" align="left">
										<html:button property="" styleClass="formbutton" value="Cancel" onclick="cancel()" styleId="reset">
										</html:button> 
									</td>
								</tr>
        					</table>
        				</td>
        				<td valign="top" background="images/Tright_3_3.gif" ></td>
      				</tr>
      				<tr>
        				<td height="10" valign="top" background="images/Tright_03_03.gif"></td>
        				<td valign="top" class="news">&nbsp;</td>
        				<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      				</tr>
      				<tr>
        				<td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
        				<td width="0" background="images/TcenterD.gif"></td>
        				<td><img src="images/Tright_02.gif" width="9" height="29"></td>
      				</tr>
    			</table>
    		</td>
  		</tr>
	</table>
</html:form>