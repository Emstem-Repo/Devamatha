<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<script language="JavaScript" src="js/admission/admissionform.js"></script>
<script language="JavaScript" src="js/newsEventsDetails.js"></script>
<script type="text/javascript">
function getDetailForEdit(newsId,method) {
	
	document.NewsEventsEntryForm.method.value=method;
	document.NewsEventsEntryForm.selectedNewsEventsId.value=newsId;
	document.NewsEventsEntryForm.submit();
}


function cancelMe(method){
	document.NewsEventsEntryForm.method.value=method;
	document.NewsEventsEntryForm.submit();
}

function Cancel() {
	document.location.href = "LoginAction.do?method=loginAction";
}
</script>
<html:form action="/NewsEventsEntry" method="POST" enctype="multipart/form-data">
<html:hidden property="selectedNewsEventsId" styleId="selectedNewsEventsId"/>
<html:hidden property="method" styleId="method" value="" />
<html:hidden property="formName" value="NewsEventsEntryForm" />
<html:hidden property="pageType" value="7" />
<html:hidden property="selectedEmployeeId" value="" />
<html:hidden property="screen"/>
	
<table width="98%" border="0">
   <tr>
    <td><span class="Bredcrumbs"><bean:message key="knowledgepro.admin"/> <span class="Bredcrumbs">&gt;&gt; <bean:message key="knowledgepro.admin.mobNewsEventsDetails"/> &gt;&gt;</span> </span></td>
  </tr>
  <tr>
    <td valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="10"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="body" ><div align="left"><strong class="boxheader"><bean:message key="knowledgepro.admin.mobNewsEventsDetails"/></strong></div></td>
        <td width="13" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
	
      <tr>
        <td height="122" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><div align="center">
            <table width="100%" height="90"  border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td height="35" valign="top" class="body" ><tr bgcolor="#FFFFFF">
		<td height="20" colspan="2">
		<div align="right"><FONT color="red"> </FONT></div>
		<div id="errorMessage" align="left"><FONT color="red"><html:errors /></FONT>
		<FONT color="green"> <html:messages id="msg"
			property="messages" message="true">
			<c:out value="${msg}" escapeXml="false"></c:out>
			<br>
		</html:messages> </FONT></div>
		</td>
	</tr></td>
              </tr>
              <tr>
                <td height="35" valign="top" class="body" ><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                    <tr>
                      <td ><img src="images/01.gif" width="5" height="5" /></td>
                      <td width="914" background="images/02.gif"></td>
                      <td><img src="images/03.gif" width="5" height="5" /></td>
                    </tr>
                    <tr>
                      <td width="5"  background="images/left.gif"></td>
                      <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
                          <tr >
                            <td height="25" class="row-odd"><div align="center"><bean:message key="knowledgepro.slno" /></div></td>
                             <td class="row-odd"><bean:message key="knowledgepro.admin.mobNewsEventsDetailsTitle"/></td>
                             <td height="25" class="row-odd"><bean:message key="knowledgepro.admin.mobNewsEventsDetailsDateFrom"/></td>
                            <td class="row-odd"><bean:message key="knowledgepro.admin.mobNewsEventsDetailsDateTo"/></td>
                            <td class="row-odd"><bean:message key="knowledgepro.admin.mobNewsEventsDetailsCategory"/></td>
                             <td height="25"  class="row-odd" align="left" ><bean:message key="knowledgepro.pgm.organisedBy"/></td>
                            <td class="row-odd"><bean:message key="knowledgepro.edit"/></td>
                          </tr>
                          <c:set var="temp" value="0" />
							<nested:iterate id="NEC1" name="NewsEventsEntryForm" property="newsList" indexId="count">
								<c:choose>
								<c:when test="${temp == 0}">
                    <tr>
                            <td width="2%" height="39" class="row-even"><div align="center">
                            <c:out value="${count+1}" />
                            </div></td>
                             <input type="hidden" id="newsId" name="newsId" value="<nested:write property="id"/>"/>
                            
                            <td width="20%" class="row-even">&nbsp;<nested:write property="eventTitle"/></td>
                            <td width="20%" height="39" class="row-even">&nbsp;<nested:write property="dateFrom"/></td>
                             <td width="3%" class="row-even">&nbsp;<nested:write property="dateTo"/></td>
                            <td width="20%" class="row-even" >&nbsp;<nested:write property="category"/></td>
                         	 <logic:equal value="Course" property="orgBy" name="NEC1">
                   			<td width="20%" height="25" class="row-even" align="left"><bean:write name="NEC1" property="courseName"/></td>
                   			</logic:equal>
                   			<logic:equal value="Department" property="orgBy" name="NEC1"> 
                   			<td width="20%" height="25" class="row-even" align="left"><bean:write name="NEC1" property="departmentName"/></td>
                   			</logic:equal>
                   			<logic:equal value="Deanery" property="orgBy" name="NEC1">
                   			<td width="20%" height="25" class="row-even" align="left"><bean:write name="NEC1" property="streamName"/></td>
                   			</logic:equal>
                   			<logic:equal value="Special Centers" property="orgBy" name="NEC1">
                   			<td width="20%" height="25" class="row-even" align="left"><bean:write name="NEC1" property="splCentreName"/></td>
                   			</logic:equal>
              
                           
                            <td width="3%" class="row-even" align="center"><img src="images/edit_icon.gif"
										width="16" height="18"  style="cursor:pointer" 
										onclick="getDetailForEdit('<nested:write property="id"/>','loadPreApproval')"></td>
							
                     </tr>
                          <c:set var="temp" value="1" />
							</c:when>
							<c:otherwise>
                     <tr >
                            <td  width="2%" height="25" class="row-white"><div align="center">
                           
                            <c:out value="${count+1}" />
                            </div></td>
                            <input type="hidden" id="newsId" name="newsId" value="<nested:write property="id"/>"/>
                          
                            <td width="20%" class="row-white">&nbsp;<nested:write property="eventTitle"/></td>
                             <td width="20%" height="39" class="row-white">&nbsp;<nested:write property="dateFrom"/></td>
                             <td width="3%" class="row-white">&nbsp;<nested:write property="dateTo"/></td>
                            <td width="20%" class="row-white" >&nbsp;<nested:write property="category"/></td>
                             <logic:equal value="Course" property="orgBy" name="NEC1">
                   			<td width="20%" height="25" class="row-white" align="left"><bean:write name="NEC1" property="courseName"/></td>
                   			</logic:equal>
                   			<logic:equal value="Department" property="orgBy" name="NEC1"> 
                   			<td width="20%" height="25" class="row-white" align="left"><bean:write name="NEC1" property="departmentName"/></td>
                   			</logic:equal>
                   			<logic:equal value="Deanery" property="orgBy" name="NEC1">
                   			<td width="20%" height="25" class="row-white" align="left"><bean:write name="NEC1" property="streamName"/></td>
                   			</logic:equal>
                   			<logic:equal value="Special Centers" property="orgBy" name="NEC1">
                   			<td width="20%" height="25" class="row-white" align="left"><bean:write name="NEC1" property="splCentreName"/></td>
                   			</logic:equal>
              
                            
                            <td  width="3%" class="row-white" align="center"><img src="images/edit_icon.gif"
										width="16" height="18"  style="cursor:pointer" 
										onclick="getDetailForEdit('<nested:write property="id"/>','loadPreApproval')"></td>
							
                      </tr>
                          <c:set var="temp" value="0" />
						</c:otherwise>
						</c:choose>
						</nested:iterate>
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
              <tr>
                <td height="10" class="body" ><table width="100%"  border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="47%" ></td>
                      <td width="1%" height="35"><div align="center">
                     <!--  <html:submit styleClass="formbutton" property=""  onclick="cancelMe('initNewsEventsSearch')" >Close</html:submit>
                 -->     
                      <html:button styleClass="formbutton" property=""  onclick="Cancel()" >Close</html:button>
                      </div>
					  </td>
                      <td width="52%"></td>
                    </tr>
                </table></td>
              </tr>
              <tr>
                <td height="10" class="body" ></td>
              </tr>
            </table>
        </div></td>
        <td width="13" valign="top" background="images/Tright_3_3.gif" class="news"></td>
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
<script type="text/javascript">
hook=false;
</script>