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
function getDetails(leaveid,commonid)
{
	document.getElementById("method").value="getLeaveStatusListByadmnApplId";
	document.getElementById("leaveId").value=leaveid;
	document.getElementById("commonId").value=commonid;
	document.hostelAdminMessageForm.submit();	
}
</script>
</head>
<html:form action="hostelAdmnMessage" method="post">
	<html:hidden property="formName" value="hostelAdminMessageForm" />
	<html:hidden property="pageType" value="1" />
	<html:hidden property="method" styleId="method" value="initHostelAdminMessage"/>	
	<html:hidden property="leaveId" styleId="leaveId" value=""/>
	<html:hidden property="commonId" styleId="commonId" value=""/>	
<table width="99%" border="0">
  
  <tr>
   
    <td><span class="heading"><bean:message key="knowledgepro.hostel.adminmessage.hostel"/> <span class="Bredcrumbs">&gt;&gt;<bean:message key="knowledgepro.hostel.adminmessage.adminmessage"/>&gt;&gt;</span></span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="heading_white" ><bean:message key="knowledgepro.hostel.adminmessage.adminmessage"/></td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news">&nbsp;</td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="40" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr bgcolor="#FFFFFF">
									<td height="20" colspan="4">
									<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
									<FONT color="green"><html:messages id="msg"
										property="messages" message="true">
										<c:out value="${msg}" escapeXml="false"></c:out>
										<br>
									</html:messages> </FONT></div>
									</td>
								</tr>
          <tr>
            <td ><img src="images/01.gif" width="5" height="5" /></td>
            <td width="914" background="images/02.gif"></td>
            <td><img src="images/03.gif" width="5" height="5" /></td>
          </tr>
       
          <tr>
              <td width="5"  background="images/left.gif"></td>
              <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
                  <tr >
                    <td height="25" class="row-odd" ><div align="center"><bean:message key="knowledgepro.slno"/> </div></td>
                    <td height="25" class="row-odd" ><bean:message key="knowledgepro.hostel.adminmessage.hostelname"/></td>
                    <td class="row-odd"><bean:message key="knowledgepro.hostel.adminmessage.messageType"/></td>
                    <td class="row-odd"><bean:message key="knowledgepro.hostel.adminmessage.roomNo"/> </td>
                     <td class="row-odd"><bean:message key="knowledgepro.hostel.adminmessage.fromName"/></td>
                     <td class="row-odd"><bean:message key="knowledgepro.hostel.adminmessage.id"/></td>
                    <td class="row-odd"><bean:message key="knowledgepro.hostel.adminmessage.sentDate"/></td>
                    <td class="row-odd"><bean:message key="knowledgepro.hostel.adminmessage.status"/></td>
                  </tr>
             
                 <logic:iterate id="adminMessageList" name="hostelAdminMessageForm" property="adminMessageTOList"  indexId="count">
		           <c:choose>
	               <c:when test="${temp == 0}">          
	               <tr >
                    <td width="5%" height="25" class="row-even"><div align="center"><c:out value="${count + 1}"/></div></td>
                    <td width="20%" height="25" class="row-even" ><bean:write name="adminMessageList" property="hostelName"/></td>
                    <td width="20%" height="25"  class="row-even" ><bean:write name="adminMessageList" property="messageType"/></td>
                    <td width="20%" height="25" class="row-even" ><bean:write name="adminMessageList" property="roomNo"/></td>
                    <td width="22%" height="25" class="row-even" ><bean:write name="adminMessageList" property="fromName" /></td>
                    <td width="19%" height="25" class="row-even" >
                    <A href="#" onclick='getDetails("<bean:write name='adminMessageList' property='leaveId'/>","<bean:write name='adminMessageList' property='commonId'/>")'><bean:write name="adminMessageList" property="commonId"/></A>
                    </td>
                    <td width="21%" height="25" class="row-even" ><bean:write  name="adminMessageList" property="sentDate"/></td>
                    <td width="13%" height="25" class="row-even" ><bean:write name="adminMessageList" property="status"/></td>
                  </tr>
                   <c:set var="temp" value="1"/>
                  </c:when>
                 
                  <c:otherwise>
                  <tr>
                    <td width="5%" height="25" class="row-white"><div align="center"><c:out value="${count + 1}"/></div></td>
                    <td width="20%" height="25" class="row-white" ><bean:write name="adminMessageList" property="hostelName"/></td>
                    <td width="20%" height="25" class="row-white" ><bean:write name="adminMessageList" property="messageType"/></td>
                    <td width="20%" height="25" class="row-white" ><bean:write name="adminMessageList" property="roomNo"/></td>
                     <td width="22%" height="25" class="row-white" ><bean:write name="adminMessageList" property="fromName"/></td>
                      <td width="19%" height="25" class="row-white" >
                      <A href="#" onclick='getDetails("<bean:write name='adminMessageList' property='leaveId'/>","<bean:write name='adminMessageList' property='commonId'/>")'><bean:write name="adminMessageList" property="commonId"/></A>
                      </td>
                       <td width="21%" height="25" class="row-white" ><bean:write  name="adminMessageList" property="sentDate"/></td>
                        <td width="13%" height="25" class="row-white" ><bean:write name="adminMessageList" property="status"/></td>
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
        </table>
        </td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td class="heading"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="45%" height="35">&nbsp;</td>
            <td width="0%"></td>
            <td width="55%">
            <input name="Submit" type="submit" class="formbutton" value="Back" /></td>
          </tr>
        </table></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
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