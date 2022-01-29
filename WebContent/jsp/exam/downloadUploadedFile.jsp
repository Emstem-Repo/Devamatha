
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@page import="java.util.Map,java.util.HashMap"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<script language="JavaScript" src="js/ajax/AjaxUtil.js"></script>
<script language="JavaScript" src="js/ajax/Ajax.js"></script>
<title>:: CMS ::</title>
<script type="text/javascript">
function getDetailForDownload(formatNamejsp) {
	document.downloadUploadedFileForm.method.value="getStreamInfo";
	document.downloadUploadedFileForm.formatName.value=formatNamejsp;
	document.downloadUploadedFileForm.submit();
	myRef = window .open(url, "Download Files", "left=20,top=20,width=900,height=900,toolbar=1,resizable=0,scrollbars=1");
}
</script>

<html:form action="/printDownloadUploadedFile">

	<html:hidden property="method" styleId="method"	value="" />
	<html:hidden property="formName" value="downloadUploadedFileForm"  styleId="formName"/>
	<html:hidden property="formatName" value="" />
	<html:hidden property="pageType" value="1" />


<table width="100%" border="0">
  <tr>
    <td><span class="heading">Exam <span class="Bredcrumbs">&gt;&gt; Download Uploaded File &gt;&gt;</span></span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="body" ><strong class="boxheader"> Download Uploaded File</strong></td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="10" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
         
          
          
          
          <tr>
							<td height="45" colspan="4">
							<table width="98%" border="0" align="center" cellpadding="0"
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
									<tr >
					                    <td height="5%" class="row-odd" ><div align="center"><bean:message key="knowledgepro.slno"/></div></td>
					                     <td height="30%" class="row-odd" align="center" >File Name</td>
					                    <td height="30%" class="row-odd" align="center" >Download</td>
                 </tr>
									<logic:notEmpty name="downloadUploadedFileForm" property="downloadFiles">
									<logic:iterate id="download" name="downloadUploadedFileForm" property="downloadFiles" indexId="count">
										<tr>
										<td width="5%" height="25" class="row-even" ><div align="center"><c:out value="${count + 1}"/></div></td>
										<td  height="25" class="row-even" align=left><bean:write name="download" property="orgFileName"/></td>
										<td  height="25" class="row-even" align="left">
										<a href="#" onclick="getDetailForDownload('<bean:write name="download" property="fileName"/>')"><bean:message key="knowledgepro.reports.submit" /></a></td>
													</tr>
									</logic:iterate>
									</logic:notEmpty>
									</table>
									</td>
									<td width="5" height="30" background="images/right.gif"></td>
								</tr>
								<tr>
									<td height="5"><img src="images/04.gif" width="5"
										height="5" /></td>
									<td background="images/05.gif"></td>
									<td><img src="images/06.gif" /></td>
								</tr>
							</table>
							</td>
						</tr>
          
        </table></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
          <tr>
            
            <td width="1%"></td>
          
            <td width="1%"></td>
            
          </tr>
        </table> </td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
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
    </table></td>
  </tr>
</table>


</html:form>

