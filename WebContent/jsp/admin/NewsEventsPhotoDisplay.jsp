 <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
 <%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<link href="css/styles.css" rel="stylesheet" type="text/css">
<html:form action="/NewsEventsEntry" method="POST" enctype="multipart/form-data">
<html:hidden property="method" styleId="method" value="" />
<html:hidden property="formName" value="NewsEventsEntryForm" />
<table width="99%" border="0">  
  <tr>
    <td><span class="heading"><span class="Bredcrumbs"><bean:message key="knowledgepro.admin"/>  &gt;&gt; <bean:message key="knowledgepro.admin.mobNewsEventsDetails"/> 
&gt;&gt;</span></span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/st_Tright_03_01.gif" width="9" height="29"></td>
        <td colspan="2" background="images/st_Tcenter.gif" class="heading_white" ><bean:message key="knowledgepro.admin.mobNewsEventsDetailsEntry"/></td>
        <td width="10" ><img src="images/st_Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td colspan="2" class="heading"><div align="right"> </div>       
        <div id="errorMessage"><FONT color="red"><html:errors /></FONT>
					<FONT color="green"><html:messages id="msg"
						property="messages" message="true">
						<c:out value="${msg}" escapeXml="false"></c:out>
						<br>
					</html:messages> </FONT></div>       
        </td>
        <td valign="top" background="images/Tright_3_3.gif" ></td>
     </tr>
     <tr>
        <td valign="top" background="images/Tright_03_03.gif"></td>
        <td colspan="2" valign="top" ><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td ><img src="images/01.gif" width="5" height="5" /></td>
            <td width="914" background="images/02.gif"></td>
            <td><img src="images/03.gif" width="5" height="5" /></td>
          </tr>
          <tr>
            <td width="5"  background="images/left.gif"></td>
            <td valign="top">
	   	<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td ><img src="images/01.gif" width="5" height="5" /></td>
            <td width="914" background="images/02.gif"></td>
            <td><img src="images/03.gif" width="5" height="5" /></td>
          </tr> 
          <tr>
				<td width="5" background="images/left.gif"></td>
				<td width="100%" height="22" align="left" valign="top">
				<table width="100%" height="22" border="0" cellpadding="0">
					<tr class="row-white">
						<td width="50%" height="30" align="center" class="row-even"><img
												src='<%=request.getContextPath()%>/PhotoServlet'
												height="550Px" width="550Px" /></td>
					</tr>
				</table>
				</td>
				<td background="images/right.gif" width="5" height="22"></td>
		 </tr>
         
          <tr>
            <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
            <td background="images/05.gif"></td>
            <td><img src="images/06.gif" /></td>
          </tr>
        </table>
        <td width="5" height="30"  background="images/right.gif"></td>
          </tr>
          <tr>
            <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
            <td background="images/05.gif"></td>
            <td><img src="images/06.gif" /></td>
          </tr>
        </table></td>
        <td valign="top" background="images/Tright_3_3.gif" ></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td colspan="2" class="heading"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="49%" height="35" align="right">&nbsp;</td>
            <td width="2%" height="35" align="center"><html:button property="" styleClass="btnbg" value="Close" onclick="window.close()"/></td>
            <td width="49%" height="35" align="left"></td>
          </tr>
        </table></td>
        <td valign="top" background="images/Tright_3_3.gif" ></td>
      </tr>
      <tr>
        <td><img src="images/st_Tright_03_05.gif" width="9" height="29"></td>
        <td width="0" colspan="2" background="images/st_TcenterD.gif"></td>
        <td><img src="images/st_Tright_02.gif" width="9" height="29"></td>
      </tr>
    </table></td>
  </tr>
</table>
</html:form>