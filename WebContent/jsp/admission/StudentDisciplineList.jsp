<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<script language="JavaScript" src="js/admission/admissionform.js"></script>
<script language="JavaScript" src="js/admission/studentdetails.js"></script>

<html:form action="/studentDiscipline">
	<html:hidden property="method" value="" />
	<html:hidden property="selectedAppNo" value="" />
	<html:hidden property="selectedYear" value="" />
	<html:hidden property="detailsView" value="false" />
	<html:hidden property="studentId" value="" />
	<html:hidden property="formName" value="studentEditForm" />
	<html:hidden property="pageType" value="2" />
<table width="98%" border="0">
  <tr>
    <td><span class="Bredcrumbs"><bean:message key="knowledgepro.admission" /><span class="Bredcrumbs">&gt;&gt; <bean:message key="admissionForm.studentedit.mainedit.label"/>&gt;&gt;</span></span></td>
  </tr>
  <tr>
    <td valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="10"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="body" ><div align="left"><strong class="boxheader"><bean:message key="admissionForm.studentedit.mainedit.label"/></strong></div></td>
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
                            <td class="row-odd"><bean:message key="knowledgepro.slno" /></td>
                            <td class="row-odd"><font style="font-size: 15px"><bean:message key="knowledgepro.admission.applicationnumber"/></font></td>
                            <td class="row-odd"><bean:message key="knowledgepro.admin.name"/></td>
                            <td class="row-odd"><font style="font-size: 15px"><bean:message key="knowledgepro.exam.course"/></font></td>
                            <td class="row-odd"><bean:message key="knowledgepro.attendanceentry.class"/></td>
                            <td class="row-odd"><bean:message key="knowledgepro.attendance.regno"/></td>
                            <td class="row-odd"><font style="font-size: 15px"><bean:message key="knowledgepro.exam.reJoin.rollNo"/></font></td>
                            <td class="row-odd"><font style="font-size: 15px"><bean:message key="knowledgepro.edit"/></font></td>
                            <td class="row-odd"><font style="font-size: 15px"><bean:message key="knowledgepro.view"/></font></td>
                          </tr>
                          <c:set var="temp" value="0" />
							<nested:iterate name="studentEditForm" property="studentTOList" indexId="count">
								<c:choose>
								<c:when test="${temp == 0}">
                          <tr>
                            <td width="5%" height="39" class="row-even"><div align="center">
                            <c:out value="${count+1}" />
                            </div></td>
                            <td width="13%" height="39" class="row-even">&nbsp;<nested:write property="applicationNo"/></td>
                            <td width="19%" class="row-even">&nbsp;<nested:write property="studentName"/></td>
                            <td width="10%" class="row-even">&nbsp;<nested:write property="courseName"/></td>
                            <td width="8%" class="row-even" >&nbsp;<nested:write property="className"/></td>
                            <td width="12%" class="row-even" >&nbsp;<nested:write property="registerNo"/></td>
                            <td width="12%" class="row-even" >&nbsp;<nested:write property="rollNo"/></td>
                            <td width="12%" class="row-even" align="center"><img src="images/edit_icon.gif"
										width="16" height="18"  style="cursor:pointer" 
										onclick="getDetailForEdit('<nested:write property="id"/>','<nested:write property="appliedYear"/>','StudentEditForDiscipline')"></td>
                            <td width="11%" class="row-even" align="center"><img src="images/View_icon.gif"
										width="16" height="18"  style="cursor:pointer" 
										onclick="getDetails('<nested:write property="id"/>','<nested:write property="appliedYear"/>','getStudentDetails')"></td>
                          </tr>
                          <c:set var="temp" value="1" />
							</c:when>
							<c:otherwise>
                          <tr >
                            <td height="25" class="row-white"><div align="center">
                           
                            <c:out value="${count+1}" />
                            </div></td>
                            <td height="39" class="row-white" >&nbsp;<nested:write property="applicationNo"/></td>
                            <td class="row-white" >&nbsp;<nested:write property="studentName"/></td>
                            <td width="10%" class="row-white">&nbsp;<nested:write property="courseName"/></td>
                            <td class="row-white" >&nbsp;<nested:write property="className"/></td>
                            <td class="row-white" >&nbsp;<nested:write property="registerNo"/></td>
                            <td width="12%" class="row-white" >&nbsp;<nested:write property="rollNo"/></td>
                            <td class="row-white" align="center"><img src="images/edit_icon.gif"
										width="16" height="18"  style="cursor:pointer" 
										onclick="getDetailForEdit('<nested:write property="studentId"/>','<nested:write property="appliedYear"/>','StudentEditForDiscipline')"></td>
                             <td class="row-white" align="center"><img src="images/View_icon.gif"
										width="16" height="18"  style="cursor:pointer" 
										onclick="getDetails('<nested:write property="applicationNo"/>','<nested:write property="appliedYear"/>','getStudentDetails')"></td>
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
                      <html:submit styleClass="formbutton" property=""  onclick="cancelMe('initStudentEditForDiscipline')" >Close</html:submit>
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