<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>

<SCRIPT type="text/javascript">
	function editLanguage(languageId, languageName) {
		
		document.getElementById("languageName").value = languageName;
		document.getElementById("languageId").value = languageId;
		document.getElementById("method").value = "editLanguage";
	}
	function deleteMeritSet(languageId) {
		deleteConfirm = confirm("Are you sure you want to delete this entry?")
		if (deleteConfirm) {		
		document.location.href = "ManageLanguage.do?method=deleteLanguage&languageId="
				+ languageId;
		}
	}
</SCRIPT>

<html:form action="/ManageLanguage" focus="languageName">
<html:hidden property="method" styleId="method" value="addLanguage" />
<html:hidden property="languageId" styleId="languageId"/>
<html:hidden property="formName" value="manageMeritSetForm"/>
<html:hidden property="pageType" value="1"/>

<table width="100%" border="0">
  <tr>
    <td><span class="heading"><bean:message key="knowledgepro.admin"/> <span class="Bredcrumbs">&gt;&gt; <bean:message key="knowledgepro.admin.languageentry"/> &gt;&gt;</span></span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="body" ><strong class="boxheader"> <bean:message key="knowledgepro.admin.languageentry"/></strong></td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="122" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="100%" cellspacing="1" cellpadding="2">
            <tr bgcolor="#FFFFFF">
              <td height="20" colspan="2">
              <FONT color="red"><html:errors /></FONT>
               <FONT color="green">
              <html:messages id="msg" property="actionMessages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
								<br>
			 </html:messages> </FONT>
              </td>
            </tr>
            <tr >
              <td width="13%" height="25" class="row-odd" ><div align="right"> <bean:message key="knowledgepro.admin.language"/>: </div></td>
              <td width="19%" height="25" class="row-even" ><span class="star">
               <html:text property="languageName" onblur="fCheckDateTime(this)"
								size="16" maxlength="20"></html:text> 
              </span></td>
            </tr>
            <tr>
              <td height="25" colspan="2" ><div align="center">
                  <table width="100%"  border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="46%" height="21"><div align="right"><html:submit styleClass="button"><bean:message key="knowledgepro.submit"/></html:submit>  </div></td>
                      <td width="2%"></td>
                      <td width="52%" height="45"><html:reset styleClass="button"><bean:message key="knowledgepro.cancel"/></html:reset></td>
                    </tr>
                  </table>
              </div></td>
            </tr>
            <tr>
              <td height="25" colspan="2" ><table width="100%" cellspacing="1" cellpadding="2">
                  <tr >
                    <td height="25" class="row-odd" ><div align="center"><bean:message key="knowledgepro.slno"/> </div></td>
                    <td height="25" class="row-odd" > <bean:message key="knowledgepro.admin.language"/></td>
                    <td class="row-odd"><div align="center"><bean:message key="knowledgepro.edit"/> </div></td>
                    <td class="row-odd"><div align="center"><bean:message key="knowledgepro.delete"/></div></td>
                  </tr>
                 
                 <c:set var="temp" value="0"/>
							<logic:iterate name="languageForm" property="languageList"
									id="id" indexId="count">
                               <c:choose>
								 <c:when test="${temp == 0}">
									<tr>
										<td width="7%" height="25" class="row-even">
										<div align="center"><c:out value="${count+1}"/></div>
										</td>
										<td width="75%" height="25" class="row-even"><bean:write
											name="id" property="languageName" /></td>
										<td width="10%" height="25" class="row-even">
                                        <div align="center"><img src="images/edit_icon.gif"
											width="16" height="18"
											onclick="editLanguage('<bean:write name="id" property="languageId" />','<bean:write name="id" property="languageName" />')"/>
										</div>
										<td width="8%" height="25" class="row-even">
                                       <div align="center"><img src="images/delete_icon.gif"
											width="16" height="16"
											onclick="deleteMeritSet('<bean:write name="id" property="languageId" />')" /></div>
										</td>                                   
									</tr>
								  <c:set var="temp" value="1"/>
                                </c:when>
									<c:otherwise>
										<tr>
											<td height="25" class="row-white">
											<div align="center"><c:out value="${count+1}"/></div>
											</td>

											<td height="25" class="row-white"><bean:write name="id"
												property="languageName" /></td>
											<td height="25" class="row-white">
										 <div align="center"><img src="images/edit_icon.gif"
											width="16" height="18"
											onclick="editLanguage('<bean:write name="id" property="languageId" />','<bean:write name="id" property="languageName" />')"/>
										</div>
											<td height="25" class="row-white">
											 <div align="center"><img src="images/delete_icon.gif"
											width="16" height="16"
											onclick="deleteMeritSet('<bean:write name="id" property="languageId" />')" /></div>
										</td>  
										</tr>
										<c:set var="temp" value="0" />
									</c:otherwise>
									</c:choose>
								</logic:iterate>             
                
                  <tr>
                    <td height="25" colspan="4" >&nbsp;</td>
                  </tr>
              </table></td>
            </tr>
        </table></td>
        <td width="10" valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      
      <tr>
        <td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
        <td width="0" background="images/Tcenter.gif"></td>
        <td><img src="images/Tright_02.gif" width="9" height="29"></td>
      </tr>
    </table></td>
  </tr>
</table>
</html:form>