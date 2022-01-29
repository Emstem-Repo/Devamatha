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
	resetFieldAndErrMsgs();
}
function getFeeMainDetails(){
	document.getElementById("method").value = "getFeeMainDetails";
	document.feeMainDetailsReportForm.submit();
}
function gotoFeesDetails() {
	document.location.href = "feeMainDetailsReport.do?method=initFeeMainDetails";
}
</script>
<html:form action="/feeMainDetailsReport">	
	<html:hidden property="method" styleId="method"  value=""/>
	<html:hidden property="formName" value="feeMainDetailsReportForm" />
	<html:hidden property="pageType" value="1" />
	<table width="98%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message key="knowledgepro.pettycash" />
			<span class="Bredcrumbs">&gt;&gt; FeeMain Details &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="10"><img src="images/Tright_03_01.gif"></td>
					<td width="100%" background="images/Tcenter.gif" class="body">
					<div align="left"><strong class="boxheader">FeeMain Details</strong></div>
					</td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
						<tr>
							<td colspan="6" align="left">
							<div align="right"><span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields" /></span></div>
							<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
							<FONT color="green"><html:messages id="msg"
								property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
								<br>
							</html:messages></FONT></div>
							</td>
						</tr>
						<tr>
							<td><img src="images/01.gif" width="5" height="5" /></td>
							<td width="914" background="images/02.gif"></td>
							<td><img src="images/03.gif" width="5" height="5" /></td>
						</tr>
						<tr>
							<td width="5" background="images/left.gif"></td>
							<td width="100%" valign="top">
							<table width="100%" cellspacing="1" cellpadding="2">
							
            <tr>
				<td   class="row-odd" width="25%"><div align="right"> <span class="Mandatory">*</span><bean:message key="knowledgepro.exam.examDefinition.academicYear"/>:</div></td>
                <td  class="row-even" width="25%">
					<input type="hidden" id="tempYear" name="tempYear" value="<bean:write name="feeMainDetailsReportForm" property="academicYear"/>"/>
                    <html:select name="feeMainDetailsReportForm" property="academicYear" styleId="academicYear" styleClass="combo" > 
                    	<html:option value=" "><bean:message key="knowledgepro.admin.select" /></html:option>
                     	<cms:renderAdmBioDataAcademicYear></cms:renderAdmBioDataAcademicYear>
					</html:select>
				</td>                
			
           
            	<td   class="row-odd" width="25%"><div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message key="knowledgepro.feepays.startdate"/>:</div></td>
                <td  height="25" class="row-even" >
                	<table width="189" border="0" cellspacing="0" cellpadding="0">
                    	<tr>
                      		<td width="40">
                      			<html:text name="feeMainDetailsReportForm" property="startDate" styleId="startDate" size="10" maxlength="16"/> 
                      		</td>
                      		<td >
                      			<script language="JavaScript">
									new tcal ({
										// form name
										'formname': 'feeMainDetailsReportForm',
										// input name
										'controlname': 'startDate'
									});
								</script>
							</td>
                    	</tr>
                	</table>
                </td>
                </tr>
                <tr>
			    <td  class="row-odd"><div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message key="knowledgepro.feepays.enddate"/>:</div></td>
                <td class="row-even" width="25%">
                	<table width="188" border="0" cellspacing="0" cellpadding="0">
                  		<tr>
                    		<td width="40"><html:text name="feeMainDetailsReportForm" property="endDate" styleId="endDate" size="10" maxlength="16"/> </td>
                    		<td >
                    			<script language="JavaScript">
									new tcal ({
									// form name
									'formname': 'feeMainDetailsReportForm',
									// input name
									'controlname': 'endDate'
									});
								</script>
							</td>
                  		</tr>
                	</table>
                </td>
                 <td class="row-odd" width="25%"></td>
                  <td class="row-even" width="25%"></td>
			</tr>
		</table>
	</td>
	<td width="5" height="30" background="images/right.gif"></td>
</tr>
						<tr>
							<td height="5"><img src="images/04.gif" width="5" height="5" /></td>
							<td background="images/05.gif"></td>
							<td><img src="images/06.gif" /></td>
						</tr>
					</table>
					<div align="center">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="46%" height="35">
								<div align="right">
								<html:button property="" styleClass="formbutton" onclick="getFeeMainDetails()">Search</html:button>
							 	</div>
							</td>
							<td width="2%"></td>
							<td width="2%" align="left">
										<html:button property=""
											styleClass="formbutton" onclick="resetMessages()" styleId="reset">
											<bean:message key="knowledgepro.emp.exceptionDetails.reset" />
										</html:button>
										
							</td>
							<td width="2%"></td>
							<td  align="left">
							<html:button property="" styleClass="formbutton" onclick="gotoFeesDetails()">Back</html:button>
							</td>
						</tr>
					</table>
					</div>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
					<tr>
					<td height="26" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news"></td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>	
				<tr>
					<td><img src="images/Tright_03_05.gif" height="29" width="9"></td>
					<td background="images/TcenterD.gif" width="100%"></td>
					<td><img src="images/Tright_02.gif" height="29" width="9"></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</html:form>
<script type="text/javascript">
	var year = document.getElementById("tempyear").value;
	if (year.length != 0) {
		document.getElementById("academicYear").value = year;
	}
</script>