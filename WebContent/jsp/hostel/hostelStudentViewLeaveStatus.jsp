<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<head>
	<title>:: CMS ::</title>
	<script language="JavaScript" src="js/calendar_us.js"></script>
	<link href="css/styles.css" rel="stylesheet" type="text/css">
	<link rel="stylesheet" href="css/calendar.css">
	<script type="text/javascript" src="js/AC_RunActiveContent.js"></script>
    <script type="text/javascript" src="js/sdmenu/sdmenu.js"></script>
    <script type="text/javascript" src="js/ajax/Ajax.js"></script>
    <script type="text/javascript" src="js/ajax/AjaxUtil.js"></script>
    <script type="text/javascript" src="js/leftSwitchMenu.js"></script>
	<script type="text/javascript" src="js/common.js"></script>
	<style type="text/css">
	<!--
		body {
			margin-left: 0px;
			margin-top: 5px;
			margin-right: 0px;
			margin-bottom: 0px;
		}
	-->
	</style>
</head>
<html:form action="hostelStudentViewMessage" method="post">
	<html:hidden property="method" styleId="method" value="getStudentViewMessageList"/>
	<html:hidden property="formName" value="hostelStudentViewMessageForm" />	
	<table border="0" width="580" cellspacing="0" cellpadding="0">
		<tr>
  			<td colspan="2" class="nav"><bean:message key="knowledgepro.attendanceentry.leavestatus"/></td>
  			<td width="220" class="tr">&nbsp;</td>
  		</tr>
  		<tr>
  			<td width="13" class="le">&nbsp;</td>
  			<td width="383">&nbsp;</td>
  			<td width="210" class="ri">&nbsp;</td>
  		</tr>
  		<tr>
    		<td width="13" class="le">&nbsp;</td>
         	<td width="383">
  				<table width="100%" border="0" cellpadding="0" cellspacing="0" >
					<tr>
						<td valign="top" background="images/Tright_03_03.gif"></td>
     	  				<td valign="top" class="news">
  							<table width="100%" cellspacing="1" cellpadding="2">
                				<tr >
                  					<td width="18%" height="25" class="studentrow-odd" ><div align="right"> <bean:message key="knowledgepro.hostel.adminmessage.id"/>: </div></td>
                  					<td width="32%" height="25" class="studentrow-even" ><label></label>
                    					<span class="star">
                      						<bean:write name="hostelStudentViewMessageForm" property="leaveTypeTo.leaveId" />
                      					</span>
                      				</td>
                  					<td width="17%" height="25" class="studentrow-odd"><div align="right"><bean:message key="knowledgepro.hostel.adminmessage.type"/>:</div></td>
                  					<td width="33%" class="studentrow-even"><div align="left"><bean:write name="hostelStudentViewMessageForm" property="leaveTypeTo.messageTypeName" /></div></td>
                				</tr>
                				<tr >
                  					<td height="25" class="studentrow-odd" ><div align="right"><bean:message key="knowledgepro.hostel.adminmessage.startDate"/> :</div></td>
                  					<td class="studentrow-even" >  <bean:write name="hostelStudentViewMessageForm" property="leaveTypeTo.startDate" /></td>
                  					<td height="25" class="studentrow-odd" ><div align="right"><bean:message key="knowledgepro.hostel.adminmessage.enddate"/>:</div></td>
                  					<td class="studentrow-even" >  <bean:write name="hostelStudentViewMessageForm" property="leaveTypeTo.endDate" /> </td>
                				</tr>
				                <tr >
                					<td height="25" class="studentrow-odd" ><div align="right"><bean:message key="knowledgepro.hostel.adminmessage.reasons"/>:</div></td>
                  					<td class="studentrow-even" ><bean:write name="hostelStudentViewMessageForm" property="leaveTypeTo.reasons"/></td>
                  					<td height="25" class="studentrow-odd" >&nbsp;</td>
                  					<td class="studentrow-even" >&nbsp;</td>
                				</tr>
            				</table>
            				<table width="100%" cellspacing="1" cellpadding="2">
            					<tr>
        							<td class="heading">Approval details</td>
      							</tr>
            				</table>
            				<table width="100%" height="53" border="0" cellpadding="0" cellspacing="1">
				                <tr class="row-white">
				                	<td width="182" height="25" class="studentrow-odd"><div align="right"><bean:message key="knowledgepro.hostel.adminmessage.status"/>:</div></td>
				                  	<td width="282" height="25" class="studentrow-even"><bean:write name="hostelStudentViewMessageForm" property="leaveTypeTo.statusName" /></td>
				                  	<td width="159" height="25" class="studentrow-odd"><div align="right"><bean:message key="knowledgepro.hostel.adminmessage.approvalBy"/>:</div></td>
				                  	<td width="305" height="25" class="studentrow-even"><bean:write name="hostelStudentViewMessageForm" property="leaveTypeTo.approvedBy" /></td>
				                </tr>
				                <tr class="studentrow-even">
				                	<td height="25" class="studentrow-odd"><div align="right"><bean:message key="knowledgepro.hostel.adminmessage.approvedDate"/>:</div></td>
				                  	<td height="25" class="studentrow-even">
				                    	<bean:write name="hostelStudentViewMessageForm" property="leaveTypeTo.approvedDate" />
				                    </td>
				                  	<td height="25" class="studentrow-odd"><div align="right"><bean:message key="knowledgepro.hostel.adminmessage.remarks"/>:</div></td>
				                  	<td height="25" class="studentrow-even"><bean:write name="hostelStudentViewMessageForm" property="leaveTypeTo.remarks" /></td>
				                </tr>
				            </table>
				            <table width="100%"  border="0" cellspacing="0" cellpadding="0">
          						<tr>
            						<td width="45%" height="35">&nbsp;</td>
            						<td width="0%"></td>
            						<td width="55%">
             							<html:submit styleClass="btnbg">
											<bean:message key="knowledgepro.close" />
										</html:submit>  
           							</td>
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