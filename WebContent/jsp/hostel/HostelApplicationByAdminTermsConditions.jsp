<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-tld/displaytag.tld" prefix="display"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">

<head>
<script type="text/javascript">

</script>
</head>
<table width="99%" border="0">
  
  <tr>
    <td><span class="heading"><bean:message key="knowledgepro.hostel.hlApplicationByAdmin.termsConditions"/></span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="heading_white" ><bean:message key="knowledgepro.hostel.hlApplicationByAdmin.termsConditions"/></td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
     <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td class="heading"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">

	        <tr>
				<td colspan="6" align="left">
				<!--<div align="right"><span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields"/></span></div>
				--><div id="errorMessage"><FONT color="red"><html:errors/></FONT>
				<FONT color="green"><html:messages id="msg"
					property="messages" message="true">
					<c:out value="${msg}" escapeXml="false"></c:out>
					<br>
				</html:messages></FONT></div>
			    </td>
	       </tr> 
          &nbsp;&nbsp;
          <tr>
            <td ><img src="images/01.gif" width="5" height="5" /></td>
            <td width="914" background="images/02.gif"></td>
            <td><img src="images/03.gif" width="5" height="5" /></td>
          </tr>
          
         <tr>
            <td width="5"  background="images/left.gif"></td>
             <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
                <tr >
 
                  <bean:write name="hostelApplicationByAdminForm" property="hostelTO.termsConditions"/></div></td>
                  
               </tr>
            </table></td>
            <td width="5"  background="images/left.gif"></td>
            
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
      
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td class="heading"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td ><img src="images/01.gif" width="5" height="5" /></td>
            <td width="914" background="images/02.gif"></td>
            <td><img src="images/03.gif" width="5" height="5" /></td>
          </tr>
          <tr>
            <td width="5"  background="images/left.gif"></td>
            <td valign="top"><table width="50%" align="center" cellpadding="2" cellspacing="1">
              
            </table></td>
            <!--<td width="5" height="30"  background="images/right.gif"></td>
          --></tr>
          <tr>
            <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
            <td background="images/05.gif"></td>
            <td><img src="images/06.gif" /></td>
          </tr>
        </table></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
  
       <tr>
         <td width="5"  background="images/left.gif"></td>
         <td height="35" valign="top" class="body" >
         <table width="100%" height="27"  border="0" cellpadding="0" cellspacing="0">
           <tr>
             <td colspan="3" align="center"><div align="center">
             <input type="button" class="formbutton" value="Close" onclick="javascript:self.close();"/>
             
             </div></td>
            
           </tr>
         </table>
         </td>
         <td width="5" align="right" background="images/right.gif"></td>
        </tr>
       <tr>
        <td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
        <td width="0" background="images/TcenterD.gif"></td>
        <td><img src="images/Tright_02.gif" width="9" height="29"></td>
      </tr>
    </table></td>
  </tr>
</table>


 
