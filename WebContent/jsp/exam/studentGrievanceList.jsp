<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<script type="text/javascript" >

function cancelMe(method){
	   
	document.grievanceStudentForm.method.value=method;
	document.grievanceStudentForm.submit();
}
function submitForm(grivNo) {	
	//alert("grivNo is  "+grivNo);
	document.location.href="GrievanceStudent.do?method=getGrievanceStudentdetails&grievanceNo="+grivNo;
}
</script>

<html:form action="/GrievanceStudent">
	<html:hidden property="formName" value="grievanceStudentForm" />
	<html:hidden property="method" styleId="method" value="" />
	
<table width="98%" border="0">
  <tr>
    <td><span class="Bredcrumbs">exam<span class="Bredcrumbs">&gt;&gt; Grievance Student List&gt;&gt;</span></span></td>
  </tr>
  <tr>
    <td valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="10"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="body" ><div align="left"><strong class="boxheader">Grievance Student List</strong></div></td>
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
                            <td height="25" class="row-odd">Grievance No</td>
                            <td class="row-odd">Register No</td>
                            <td class="row-odd">Name</td>
                            <td class="row-odd">Semester</td>
                            <td class="row-odd">Grievance</td>
                           
                          </tr>
                          <c:set var="temp" value="0" />
                          <logic:iterate id="slist" property="studentList" name="grievanceStudentForm"
											 indexId="count">
											<tr>
											<c:choose>
												<c:when test="${count%2 == 0}">
													<tr class="row-even">
												</c:when>
												<c:otherwise>
													<tr class="row-white">
												</c:otherwise>
											</c:choose>
											<td height="25" width="10%">
											<div align="center"><c:out value="${count + 1}" /></div>
											</td>
											 <td width="13%" height="39" >&nbsp;<bean:write name="slist"
												property="grievanceNo" /></td>
                            <td width="19%" >&nbsp;<bean:write property="registerNo" name="slist"/></td>
                            <td width="8%" >&nbsp;<bean:write property="studentName" name="slist"/></td>
                            <td width="12%">&nbsp;<bean:write property="semister"  name="slist" /></td>
                            <td width="12%"  >&nbsp;
                            <%--       <html:button property="" styleClass="formbutton" value="Details"
													onclick="submitForm('<bean:write name="slist" property="grievanceNo" />')"></html:button>--%>
                      <img src="images/detail_icon.jpg"
													width="100" height="30"
													onclick="submitForm('<bean:write name="slist" property="grievanceNo" />')" alt="img not found" />
													
													
													</td>
											</tr>
										</logic:iterate>
                          
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
                      <html:submit styleClass="formbutton" property=""  onclick="cancelMe('initGrievanceStudent')" >Close</html:submit>
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