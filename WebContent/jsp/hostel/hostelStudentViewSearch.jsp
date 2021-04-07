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
	<script language="JavaScript" src="js/calendar_us.js"></script>
	<link rel="stylesheet" href="css/calendar.css"> 
<html:form action="hostelStudentViewMessage" method="post">
	<html:hidden property="method" styleId="method" value="getStudentViewMessageList"/>
	<html:hidden property="pageType" value="1"/>
	<html:hidden property="formName" value="hostelStudentViewMessageForm" />
	<table border="0" width="580" cellspacing="0" cellpadding="0">
    	<tr>
        	<td colspan="2" class="nav">Student View Messages</td>
         	<td width="220" class="tr">&nbsp;</td>
		</tr>
		<tr>
			<td width="13" class="le">&nbsp;</td>
			<td>
				<table width="70%" border="0" cellpadding="0" cellspacing="0">
					<div align="right" class="mandatoryfield">
	          			<div align="right" class="mandatoryfield"><FONT color="red"> <span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields"/></span></FONT></div>
	          			<div id="errorMessage" align="left">
	                    	<FONT color="red"><html:errors/></FONT>
	                       	<FONT color="green">
								<html:messages id="msg" property="messages" message="true">
									<c:out value="${msg}" escapeXml="false"></c:out><br>
								</html:messages>
						  	</FONT>
						</div>
	        		</div>
	        	</table>	
			</td>
			<td width="220" class="ri">&nbsp;</td>
		</tr>
		<tr>
        	<td width="13" class="le">&nbsp;</td>
         	<td width="383">
				<table width="100%" border="0" cellpadding="0" cellspacing="0" >
					<tr>
						<td valign="top" background="images/Tright_03_03.gif"></td>
     	  				<td valign="top" class="news">
     	  					<table width="98%" border="0" align="center" cellpadding="0" cellspacing="0">
           						<tr>
             						<td width="100%"  valign="top">
             							<table width="100%" border="0" cellpadding="4" cellspacing="1">
                 							<tr class="row-white">
                   								<td width="39%" class="studentrow-odd"><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.hostel.adminmessage.messageType"/>: </div></td>
                   								<td width="61%" colspan="2" class="studentrow-even">
													<html:select property="messageTypeId" name="hostelStudentViewMessageForm" styleClass="body" styleId="messageTypeId">
                        								<html:option value="">
                                    						<bean:message key="knowledgepro.select" />
                            							</html:option>
                            							<logic:notEmpty name="hostelStudentViewMessageForm" property="messageTypeMap">
                            								<html:optionsCollection name="hostelStudentViewMessageForm" property="messageTypeMap" label="value"  value="key" />
                            							</logic:notEmpty>
                   									</html:select>
												</td>
                 							</tr>
                 							<tr class="row-white">
                   								<td width="39%" class="studentrow-odd">
                   									<div align="right"><bean:message key="knowledgepro.hostel.adminmessage.fromDate"/>:</div>
                   								</td>	
                   								<td width="61%" colspan="2" class="studentrow-even">
													<html:text property="fromDate" name="hostelStudentViewMessageForm"  styleClass="TextBox" styleId="name" size="10" maxlength="10"/>
                        							<script language="JavaScript">
														new tcal ({
														// form name
														'formname': 'hostelStudentViewMessageForm',
														// input name
														'controlname': 'fromDate'
														});
													</script>
												</td>
                 							</tr>
                 							<tr class="row-white">
                   								<td class="studentrow-odd">
                   									<div align="right"><bean:message key="knowledgepro.hostel.adminmessage.toDate"/>:</div>
                   								</td>
                   								<td colspan="2" class="studentrow-even">
                   									<html:text property="toDate" name="hostelStudentViewMessageForm"  styleClass="TextBox" styleId="name" size="10" maxlength="10"/>
                       								<script language="JavaScript">
														new tcal ({
														// form name
														'formname': 'hostelStudentViewMessageForm',
														// input name
														'controlname': 'toDate'	
														});
													</script>
                   								</td>
                 							</tr>
                 							<tr class="row-white">
                   								<td height="35" colspan="3" class="row-white">
                   									<table width="100%"  border="0" cellspacing="0" cellpadding="0">
                       									<tr>
                         									<td width="49%" height="35">
                         										<div align="right">
                         											<html:submit styleClass="btnbg" styleId="button" ><bean:message key="knowledgepro.submit"/>
						 											</html:submit>	
                         										</div>
                         									</td>
                         									<td width="3%"></td>
                         									<td width="48%"><html:button property="" styleClass="btnbg" value="Reset" onclick="resetFieldAndErrMsgs()"></html:button></td>
                       									</tr>
                   									</table>
                   								</td>
                 							</tr>
             							</table>
           							</td>
           						</tr>
           						<tr>
             						<td>&nbsp;</td>
           						</tr>
       						</table>
       					</td>
       				</tr>	
   				</table>
			</td>
	  		<td width="210" class="ri">&nbsp;</td>
		</tr>
		<tr>
          <td class="bl">&nbsp;</td>
          <td class="bm">&nbsp;</td>
          <td class="br">&nbsp;</td>
        </tr>
	</table>
</html:form>