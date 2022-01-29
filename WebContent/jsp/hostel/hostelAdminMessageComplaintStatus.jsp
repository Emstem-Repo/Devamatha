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

</script>
</head>
<html:form action="hostelAdmnMessage" method="post">
<html:hidden property="method" styleId="method" value="manageLeaveStatus"/>
<html:hidden property="id" styleId="id" value=""/>
<html:hidden property="pageType" value="4"/>
<html:hidden property="formName" value="hostelAdminMessageForm" />	
<input type="hidden" name="complaintId" id="complaintId" value='<bean:write name="complaintTypeTo" property="complaintId"/>' />
<table width="99%" border="0">
  
  <tr>
  <td><span class="heading"><bean:message key="knowledgepro.hostel.adminmessage.hostel"/> <span class="Bredcrumbs">&gt;&gt;<bean:message key="knowledgepro.hostel.complaintstatus"/>&gt;&gt;</span></span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="heading_white" ><bean:message key="knowledgepro.hostel.complaintstatus"/> </td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news">
         <div id="errorMessage" align="left">
                       <FONT color="red"><html:errors/></FONT>
                       <FONT color="green">
							<html:messages id="msg" property="messages" message="true">
									<c:out value="${msg}" escapeXml="false"></c:out><br>
							</html:messages>
					  </FONT>
		</div>
        
        </td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="96" valign="top" background="images/Tright_03_03.gif"></td>
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
                  <td width="18%" height="25" class="row-odd" ><div align="right"><bean:message key="knowledgepro.hostel.adminmessage.id"/>: </div></td>
                  <td width="32%" height="25" class="row-even" ><label></label>
                    <span class="star"><bean:write name="complaintTypeTo" property="commonId" />
                     </span></td>
                  <td width="17%" height="25" class="row-odd"><div align="right"><bean:message key="knowledgepro.hostel.adminmessage.type"/>:</div></td>
                  <td width="33%" class="row-even"><div align="left"><bean:write name="complaintTypeTo" property="messageTypeName" /></div></td>
                </tr>
                <tr >
                  <td height="25" class="row-odd" ><div align="right"><bean:message key="knowledgepro.hostel.complaints.title"/>:</div></td>
                  <td class="row-even" >
                  <div align="left">
                  <bean:write name="complaintTypeTo" property="title" />
                  </div>
                  </td>
                  <td height="25" class="row-odd" ><div align="right"><bean:message key="knowledgepro.hostel.actiontaken"/>:</div></td>
                  <td class="row-even" >
                  <div align="left">
                 <html:textarea name="complaintTypeTo" property="actionTaken" styleId="actionTaken"  styleClass="TextBox"  cols="30" rows="5"></html:textarea>
                 </div>
                  </td>
                </tr>
                
                <tr >
                  <td height="25" class="row-odd" ><div align="right"><bean:message key="employee.info.job.achievement.desc"/>:</div></td>
                  <td class="row-even" ><bean:write name="complaintTypeTo" property="desc" /></td>
                  <td height="25" class="row-odd" ><div align="right"><bean:message key="knowledgepro.hostel.complaints.complaintType"/>:</div></td>
                  <td class="row-even" ><bean:write name="complaintTypeTo" property="complaintTypeName" /></td>
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
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td class="heading"><bean:message key="knowledgepro.hostel.adminmessage.approvalDetails"/></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="44" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td ><img src="images/01.gif" width="5" height="5"></td>
            <td width="914" background="images/02.gif"></td>
            <td><img src="images/03.gif" width="5" height="5"></td>
          </tr>
          <tr>
            <td width="5"  background="images/left.gif"></td>
            <td height="54" valign="top"><table width="100%" height="53" border="0" cellpadding="0" cellspacing="1">
                <tr class="row-white">
                  <td width="182" height="25" class="row-odd"><div align="right"><bean:message key="knowledgepro.hostel.adminmessages.complaint.status"/>:</div></td>
                  <td width="282" height="25" class="row-even">
                  	
                  <html:select property="statusId" name="hostelAdminMessageForm"
												styleClass="body" styleId="statusId">
                                  <html:option value="">
                                    <bean:message key="knowledgepro.select" />
                                  </html:option>
                                  <logic:notEmpty name="hostelAdminMessageForm" property="statusList">
                             <html:optionsCollection name="hostelAdminMessageForm"  label="statusType" property="statusList" value="id"/>
                                  </logic:notEmpty>
                   </html:select>                
                    </td>
                  <td width="159" height="25" class="row-odd"><div align="right"><bean:message key="knowledgepro.hostel.adminmessage.approvalBy"/>:</div></td>
                  <td width="305" height="25"><bean:write name="complaintTypeTo" property="approvedBy" /></td>
                  </tr>
                <tr class="row-even">
                  <td height="25" class="row-odd"><div align="right"><bean:message key="knowledgepro.hostel.adminmessage.approvedDate"/>:</div></td>
                  <td height="25" class="row-white">
                  <html:text property="approvedDate" name="complaintTypeTo"  styleClass="TextBox" styleId="approvedDate" size="10" maxlength="10"/>
                  <script language="JavaScript">
					new tcal ({
					// form name
					'formname': 'hostelAdminMessageForm',
					// input name
					'controlname': 'approvedDate'
					});</script>
                  </td>
                  </tr>

            </table></td>
            <td  background="images/right.gif" width="5" height="54"></td>
          </tr>
          <tr>
            <td height="5"><img src="images/04.gif" width="5" height="5"></td>
            <td background="images/05.gif"></td>
            <td><img src="images/06.gif" ></td>
          </tr>
        </table></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      
      <tr>
        <td height="38" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="45%" height="35"><div align="right">
            <html:submit styleClass="formbutton">
								<bean:message key="knowledgepro.submit" />	
							</html:submit>        
            </div></td>
            <td width="2%"></td>
            <td width="53%">
            <html:cancel value="Cancel" styleClass="formbutton" ></html:cancel>
           </td>
          </tr>
        </table></td>
        <td width="10" valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
        <td width="0" background="images/TcenterD.gif"></td>
        <td><img src="images/Tright_02.gif" width="9" height="29"></td>
      </tr>
    </table></td>
  </tr>
</table>
</html:form>



