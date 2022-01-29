<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<script type="text/javascript">
	function editCountry(countryId,countryName) {
	    document.getElementById("method").value="updateCountry";
	    document.getElementById("countryId").value=countryId;
	    document.getElementById("countryName").value=countryName;
	}

	function deleteCountry(countryId,countryName) {
		deleteConfirm = confirm("Are you sure you want to delete this entry?");
		if (deleteConfirm) {
			 document.location.href="CountryEntry.do?method=deleteCountry&countryId="+countryId+"&countryName="+countryName;
		}
       
	}	

</script>

<html:form action="/CountryEntry">

<html:hidden property="method" styleId="method" value="addCountry"/>
<html:hidden property="countryId" styleId="countryId"/>
<html:hidden property="formName" value="countryStateCity"/>
<html:hidden property="pageType" value="1"/>

<table width="100%" border="0">
  <tr>
    <td class="heading"><html:link href="AdminHome.do" styleClass="Bredcrumbs"><bean:message key="knowledgepro.admin"/></html:link> <span class="Bredcrumbs">&gt;&gt; <bean:message key="knowledgepro.admin.countryentry"/> &gt;&gt;</span> </td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="body" ><strong class="boxheader"><bean:message key="knowledgepro.admin.countryentry"/></strong></td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"> <div id="errorMessage">
	                       <FONT color="red"><html:errors/></FONT>
	                       <FONT color="green">
								<html:messages id="msg" property="messages" message="true">
										<c:out value="${msg}" escapeXml="false"></c:out><br>
								</html:messages>
						  </FONT>
		  </div></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
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
                <td width="45%" height="25" class="row-odd"><div align="right" ><bean:message key="knowledgepro.admin.country.name"/></div></td>
                <td width="55%" class="row-even"><span class="star">
                  <html:text property="countryName" styleClass="TextBox" styleId="countryName" size="16" maxlength="20"/>
                </span></td>
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
        <td valign="top" class="news"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
      <tr>
            <td width="45%" height="35"><div align="right">
            <html:submit  styleClass="formbutton" value="Submit"></html:submit>
            </div></td>
            <td width="2%"></td>
            <td width="53%">
            <html:button property="" styleClass="formbutton" value="Reset"></html:button>
      </tr>
        </table> </td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td ><img src="images/01.gif" width="5" height="5" /></td>
            <td width="914" background="images/02.gif"></td>
            <td><img src="images/03.gif" width="5" height="5" /></td>
          </tr>
          <tr>
            <td width="5"  background="images/left.gif"></td>
            <td valign="top">
            <table width="100%" cellspacing="1" cellpadding="2">
                  <tr >
                    <td height="25" class="row-odd" ><div align="center"><bean:message key="knowledgepro.slno"/> </div></td>
                    <td height="25" class="row-odd" ><bean:message key="knowledgepro.admin.country.countryname"/></td>
                    <td class="row-odd"><div align="center"><bean:message key="knowledgepro.edit"/> </div></td>
                    <td class="row-odd"><div align="center"><bean:message key="knowledgepro.delete"/></div></td>
                  </tr>
                  <c:set var="temp" value="0"/>
                  <logic:iterate name="countriesList" id="country" type="com.kp.cms.to.admin.CountryTO" indexId="count">
                  <c:choose>
                    <c:when test="${temp == 0}">
	                  <tr >
	                    <td width="9%" height="25" class="row-even"><div align="center"><c:out value="${count+1}"/></div></td>
	                    <td width="73%" height="25" class="row-even" ><bean:write name="country" property="name"/></td>
	                    <td width="10%" height="25" class="row-even" ><div align="center"><img src="images/edit_icon.gif" width="16" height="18" onclick="editCountry('<bean:write name="country" property="id"/>','<bean:write name="country" property="name"/>')"></div></td>
	                    <td width="8%" height="25" class="row-even" ><div align="center"><img src="images/delete_icon.gif" width="16" height="16" onclick="deleteCountry('<bean:write name="country" property="id"/>','<bean:write name="country" property="name"/>')"></div></td>
	                  </tr>
                    <c:set var="temp" value="1"/>
                    </c:when>
                    <c:otherwise>
	                  <tr >
	                    <td height="25" class="row-white"><div align="center"><c:out value="${count+1}"/></div></td>
	                    <td height="25" class="row-white" ><bean:write name="country" property="name"/></td>
	                    <td height="25" class="row-white" ><div align="center"><img src="images/edit_icon.gif" width="16" height="18" onclick="editCountry('<bean:write name="country" property="id"/>','<bean:write name="country" property="name"/>')"></div></td>
	                    <td height="25" class="row-white" ><div align="center"><img src="images/delete_icon.gif" width="16" height="16" onclick="deleteCountry('<bean:write name="country" property="id"/>','<bean:write name="country" property="name"/>')"></div></td>
	                  </tr>
                      <c:set var="temp" value="0"/>
				  </c:otherwise>
                  </c:choose>
                  </logic:iterate>
                  <tr>
                    <td height="25" colspan="4" >&nbsp;</td>
                  </tr>
              </table>
            </td>
            <td width="5" height="30"  background="images/right.gif"></td>
          </tr>
          <tr>
            <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
            <td background="images/05.gif"></td>
            <td><img src="images/06.gif" /></td>
          </tr>
        </table></td>
        <td width="10" valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="10" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news">&nbsp;</td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
        <td width="100%" background="images/TcenterD.gif"></td>
        <td><img src="images/Tright_02.gif" width="9" height="29"></td>
      </tr>
    </table></td>
  </tr>
</table>

</html:form>