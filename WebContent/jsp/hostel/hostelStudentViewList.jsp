<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<head>
	<title>:: CMS ::</title>
	<link rel="stylesheet" type="text/css" href="css/styles.css"/>
	<link rel="stylesheet" type="text/css" href="css/sdmenu.css"/>
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
	<script type="text/javascript">
		function getLeaveId(id)
		{
			document.getElementById("method").value="getStudentLeaveStatus";
			document.getElementById("leaveId").value=id;
			document.hostelStudentViewMessageForm.submit();	
		}
	</script>
</head>
<html:form action="hostelStudentViewMessage" method="post">
	<html:hidden property="method" styleId="method" value="initHostelStudentViewMessage"/>
	<html:hidden property="pageType" value="1"/>
	<html:hidden property="formName" value="hostelStudentViewMessageForm" />	
	<html:hidden property="leaveId" styleId="leaveId" value=""/>
	<table border="0" width="580" cellspacing="0" cellpadding="0">
    	<tr>
        	<td colspan="2" class="nav"><bean:message key="knowledgepro.hostel.studentviewmessage"/></td>
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
    						<table width="100%" border="0" cellpadding="0" cellspacing="0">
      							<tr>
   		                			<td height="25" class="studentrow-odd" ><div align="center"><bean:message key="knowledgepro.pettycash.accheads.slno"/></div></td>
        		        			<td height="25" class="studentrow-odd" ><bean:message key="knowledgepro.hostel.studentviewmessage.sentby"/></td>
                					<td class="studentrow-odd" ><bean:message key="knowledgepro.hostel.adminmessage.messageType"/></td>
                					<td height="25" class="studentrow-odd"><div align="left"><bean:message key="knowledgepro.hostel.studentviewmessage.sentdate"/></div></td>
                					<td class="studentrow-odd"><bean:message key="knowledgepro.admission.status"/></td>
                				</tr>

				                <logic:iterate id="leaveTypeList" name="hostelStudentViewMessageForm" property="leaveTypeTOList"  indexId="count">
              						<tr>
                  						<td width="7%" height="25" class="studentrow-even"><div align="center"><c:out value="${count + 1}"/></div></td>
                  						<td width="26%" height="25" class="studentrow-even" ><p><bean:write name="leaveTypeList" property="sentBy"/></p></td>
                  						<td width="23%" height="25" class="studentrow-even" ><bean:write name="leaveTypeList" property="messageTypeName"/></td>
                 							<td width="26%" height="25" class="studentrow-even" >
                 								<div align="left">
                  								<A href="#" onclick='getLeaveId("<bean:write name='leaveTypeList' property='leaveId'/>")'><bean:write name="leaveTypeList" property="sentDate"/></A>
                  							</div>
                  						</td>
                   						<td width="18%"  height="25" class="studentrow-even" ><bean:write name="leaveTypeList" property="statusName" /></td>
               						</tr>
	               				</logic:iterate>
	               				<tr>
	               					<td colspan="5">
	               						<table width="100%"  border="0" cellspacing="0" cellpadding="0">
          									<tr>
            									<td width="45%" height="35">&nbsp;</td>
            									<td width="0%"></td>
            									<td width="55%"><input name="Submit" type="submit" class="btnbg" value="Cancel" /></td>
          									</tr>
        								</table>     
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
