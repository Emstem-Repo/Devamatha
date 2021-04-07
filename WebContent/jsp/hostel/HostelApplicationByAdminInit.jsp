<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<head>
<script type="text/javascript">
function resetFields()
{	
	document.getElementById("hostel").selectedIndex = 0;
	document.getElementById("applicationNo").value="";
	document.getElementById("registerNo").value="";
	document.getElementById("staffId").value="";
	document.getElementById("rollNo").value="";		
	resetErrMsgs();
}
</script>
</head>
<html:form action="/HostelApplicationByAdmin">
<html:hidden property="method" styleId="method" value="getHostelApplicationDetails"/>
<html:hidden property="formName" value="hostelApplicationByAdminForm" />
<html:hidden property="pageType" value="1" />

 <table width="99%" border="0">
  <tr>
    <td><span class="heading"><bean:message key="knowledgepro.hostel"/><span class="Bredcrumbs">&gt;&gt; 
    <bean:message key="knowledgepro.hostel.applicationByAdmin"/>&gt;&gt;</span> </span></td>
  </tr>
  <tr>
    <td valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="heading_white" ><bean:message key="knowledgepro.hostel.applicationByAdmin"/></td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news">&nbsp;</td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
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
            <td ><img src="images/01.gif" width="5" height="5" /></td>
            <td width="914" background="images/02.gif"></td>
            <td><img src="images/03.gif" width="5" height="5" /></td>
          </tr>
          <tr>
            <td width="5"  background="images/left.gif"></td>
            <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
                <tr >
                  <td  height="25" class="row-odd" ><div align="right"><span class="Mandatory">* </span>
                  <bean:message key="knowledgepro.hostel.entry.hostel.name" /></div></td>
                  <td height="25" class="row-even">
					<html:select property="hostelId" styleClass="TextBox" styleId="hostel" name="hostelApplicationByAdminForm">
						<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
						<logic:notEmpty name="hostelApplicationByAdminForm" property="hostelTOList">
						<html:optionsCollection property="hostelTOList" name="hostelApplicationByAdminForm" label="name" value="id" />
						</logic:notEmpty>
					</html:select>
				 </td>
				 <td class="row-odd">
					<div align="right"><span class="Mandatory">*</span>Academic
						Year :</div>
				</td>
				<td class="row-even"><html:select property="academicYr"  styleClass="TextBox" styleId="academicYr" name="hostelApplicationByAdminForm">
						<cms:renderYear></cms:renderYear>
				</html:select></td>	
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
        <td valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news">&nbsp;</td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="100%" cellspacing="1" cellpadding="2">
            <tr>
              <td colspan="4" ><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                  <tr>
                    <td ><img src="images/01.gif" width="5" height="5" /></td>
                    <td width="914" background="images/02.gif"></td>
                    <td><img src="images/03.gif" width="5" height="5" /></td>
                  </tr>
                  <tr>
                    <td width="5"  background="images/left.gif"></td>
                    <td align="center" valign="top"><table width="100%" cellspacing="1" cellpadding="2">
                        <tr >
                          <td width="13%" height="25" class="row-odd" ><div align="right"><bean:message key="knowledgepro.interview.ApplicationNo"/> </div></td>
                          <td width="18%" height="25" class="row-even" ><html:text property="applicationNo" styleId="applicationNo" size="20" maxlength="15"/></td>
                          <td width="3%" align="center" class="row-even" ><strong>OR</strong></td>
                          <td width="15%" height="25" class="row-odd" ><div align="right"><bean:message key="knowledgepro.hostel.reservation.registerNo"/> </div></td>
                          <td width="18%" height="25" class="row-even" >  <html:text property="registerNo" styleId="registerNo" size="20" maxlength="15"/></td>
                          <td width="3%" class="row-even" ><strong>OR</strong></td>
                          <td width="12%" class="row-odd" ><div align="right"><bean:message key="knowledgepro.hostel.reservation.staffId"/></div></td>
                          <td width="18%" class="row-even" ><html:text property="staffId" styleId="staffId"  size="20" maxlength="15"/></td>
						  <td width="3%" class="row-even" ><strong>OR</strong></td>
						  <td width="12%" class="row-odd" ><div align="right"><bean:message key="knowledgepro.pettycash.rollNo"/>:</div></td>
                          <td width="18%" class="row-even" ><html:text property="rollNo" styleId="rollNo"  size="20" maxlength="15"/></td>
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
            </tr>
        </table></td>
        <td width="10" valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="26" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td width="48%" height="35" align="right"><html:submit property="" styleClass="formbutton" value="Continue">
			 </html:submit></td>
              <td width="4%">&nbsp;</td>
              <td width="48%">
              <html:button property="" styleClass="formbutton" onclick="resetFields()">
				<bean:message key="knowledgepro.admin.reset" />
			 </html:button> </td>
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








