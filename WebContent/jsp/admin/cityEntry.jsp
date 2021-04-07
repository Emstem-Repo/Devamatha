<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>

<script type="text/javascript" >
	var stateId;
	function getStates(countryId) {
		if(countryId.length != 0) {
			var args = "method=getStatesByCountry&countryId="+countryId;
		  	var url ="AjaxRequest.do";
		  	// make an request to server passing URL need to be invoked and arguments.
			requestOperation(url,args,updateDistrict);
		} else {
			 var state = document.getElementById("state");
			 for (x1=state.options.length-1; x1>0; x1--)
			 {
				 state.options[x1]=null;
			 }
		}	
	}
	
	function updateDistrict(req) {
		updateOptionsFromMap(req,"state"," Select ");
		if(stateId != null && stateId.length != 0) {
			document.getElementById("stateId").value=stateId;
		} 
    }

    function editCity(cityId,cityName,countryId,sId) {
    	document.getElementById("method").value="updateCity";
	    //document.getElementById("stateId").value=stateId;
	    stateId = sId;
	    document.getElementById("countryId").value=countryId;
	    document.getElementById("cityId").value=cityId;
	    document.getElementById("cityName").value=cityName;
	    getStates(countryId);
	    //document.getElementById("stateId").value=stateId; 
	}    
    function deleteCity(cityId,cityName) {
		document.location.href="CityEntry.do?method=deleteCity&cityId="+cityId+"&cityName="+cityName;
	}

</script>

<html:form action="CityEntry">
<html:hidden property="method" styleId="method" value="addCity"/>
<html:hidden property="cityId" styleId="cityId" value=""/>
<html:hidden property="formName" value="countryStateCity"/>
<html:hidden property="pageType" value="3"/>
<table width="100%" border="0">
  <tr>
    <td><span class="heading"><html:link href="AdminHome.do" styleClass="Bredcrumbs"><bean:message key="knowledgepro.admin"/></html:link> <span class="Bredcrumbs">&gt;&gt; <bean:message key="knowledgepro.admin.cityentry"/> &gt;&gt;</span></span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="body" ><strong class="boxheader"> <bean:message key="knowledgepro.admin.cityentry"/></strong></td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="122" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="100%" cellspacing="1" cellpadding="2">
            <tr bgcolor="#FFFFFF">
              <td height="20" colspan="2">
 					<FONT color="red"><html:errors/></FONT>
                    <FONT color="green">
					<html:messages id="msg" property="messages" message="true">
							<c:out value="${msg}" escapeXml="false"></c:out><br>
					</html:messages>
					</FONT>
			 </td>
            </tr>
            <tr >
              <td width="13%" height="25" class="row-odd" ><div align="right"> <bean:message key="knowledgepro.admin.country"/> </div></td>
              <td width="19%" height="25" class="row-even" >
              <html:select property="countryId" styleClass="body" styleId="country" onchange="getStates(this.value)">
                   <html:option value=""> <bean:message key="knowledgepro.select"/> </html:option>
                   <html:optionsCollection name="countriesMap" label="value" value="key"/>
              </html:select>
              <span class="star"></span></td>
              <td width="22%" class="row-odd"><div align="right" ><bean:message key="knowledgepro.admin.state"/></div></td>
              <td width="16%" class="row-even"><span class="star">
              <html:select property="stateId" styleClass="body" styleId="state">
                    <html:option value=""> <bean:message key="knowledgepro.select"/> </html:option>
               </html:select>
              </span></td>
            </tr>
            <tr >
              <td height="25" class="row-odd" ><div align="right"><bean:message key="knowledgepro.admin.city.name"/></div></td>
              <td height="25" class="row-even" ><span class="star">
                <html:text property="cityName" styleClass="TextBox" styleId="cityName" size="16" maxlength="20"/>
              </span></td>
              <td class="row-odd"><div align="right"></div></td>
              <td class="row-even">&nbsp;</td>
            </tr>
            <tr>
              <td height="25" colspan="4" ><table width="100%"  border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td width="45%" height="35"><div align="right"><html:submit styleClass="button"><bean:message key="knowledgepro.submit"/></html:submit> </div></td>
                    <td width="2%"></td>
                    <td width="53%"><html:reset styleClass="button"><bean:message key="knowledgepro.cancel"/></html:reset></td>
                  </tr>
              </table></td>
            </tr>
            <tr>
              <td height="25" colspan="4" ><table width="100%" cellspacing="1" cellpadding="2">
                  <tr >
                    <td height="25" class="row-odd" ><div align="center"><bean:message key="knowledgepro.slno"/></div></td>
                    <td height="25" class="row-odd" ><bean:message key="knowledgepro.admin.countryname"/></td>
                    <td height="25" class="row-odd"><div align="left"><bean:message key="knowledgepro.admin.statename"/> </div></td>
                    <td class="row-odd"><bean:message key="knowledgepro.admin.city.cityname"/></td>
                    <td class="row-odd"><div align="center"><bean:message key="knowledgepro.edit"/> </div></td>
                    <td class="row-odd"><div align="center"><bean:message key="knowledgepro.delete"/></div></td>
                  </tr>
                  <c:set var="temp" value="0"/>
                  <logic:iterate name="cityList" id="city" type="com.kp.cms.to.admin.CityTO" indexId="count">
                  <c:choose>
                    <c:when test="${temp == 0}">
	                  <tr >
	                    <td width="7%" height="25" class="row-even"><div align="center"><c:out value="${count+1}"/></div></td>
	                    <td width="20%" height="25" class="row-even" ><bean:write name="city" property="stateTo.countryTo.name"/></td>
	                    <td width="26%" height="25" class="row-even" ><bean:write name="city" property="stateTo.name"/></td>
	                    <td width="29%" class="row-even" ><bean:write name="city" property="name"/></td>
	                    <td width="9%" height="25" class="row-even" ><div align="center"><img src="images/edit_icon.gif" width="16" height="18" onclick="editCity('<bean:write name="city" property="id"/>','<bean:write name="city" property="name"/>','<bean:write name="city" property="stateTo.countryTo.id"/>','<bean:write name="city" property="stateTo.id"/>')"></div></td>
	                    <td width="9%" height="25" class="row-even" ><div align="center"><img src="images/delete_icon.gif" width="16" height="16" onclick="deleteCity('<bean:write name="city" property="id"/>','<bean:write name="city" property="name"/>')"></div></td>
	                  </tr>
                    <c:set var="temp" value="1"/>
                    </c:when>
                    <c:otherwise>
                  <tr >
                    <td height="25" class="row-white"><div align="center"><c:out value="${count+1}"/></div></td>
                    <td height="25" class="row-white" ><bean:write name="city" property="stateTo.countryTo.name"/></td>
                    <td height="25" class="row-white" ><bean:write name="city" property="stateTo.name"/></td>
                    <td class="row-white" ><bean:write name="city" property="name"/></td>
                    <td height="25" class="row-white" ><div align="center"><img src="images/edit_icon.gif" width="16" height="18" onclick="editCity('<bean:write name="city" property="id"/>','<bean:write name="city" property="name"/>','<bean:write name="city" property="stateTo.countryTo.id"/>','<bean:write name="city" property="stateTo.id"/>')"></div></td>
                    <td height="25" class="row-white" ><div align="center"><img src="images/delete_icon.gif" width="16" height="16" onclick="deleteCity('<bean:write name="city" property="id"/>','<bean:write name="city" property="name"/>')"></div></td>
                  </tr>
                   <c:set var="temp" value="0"/>
                   </c:otherwise>
                  </c:choose>
                  </logic:iterate>
                  <tr>
                    <td height="25" colspan="6" >&nbsp;</td>
                  </tr>
              </table></td>
            </tr>
          </table>
            <div align="center"></div></td>
        <td width="10" valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="71" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news">&nbsp;</td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
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