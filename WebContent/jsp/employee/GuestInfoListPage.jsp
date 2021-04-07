<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<script language="JavaScript" src="js/admission/admissionform.js"></script>
<script language="JavaScript" src="js/employee/guest.js"></script>

<script type="text/javascript">


var print = "<c:out value='${GuestFacultyInfoForm.printPage}'/>";
if(print.length != 0 && print == "true"){
	var url = "GuestFaculty.do?method=printGuest";
	myRef = window
			.open(url, "ViewGuest",
					"left=20,top=20,width=900,height=900,toolbar=1,resizable=0,scrollbars=1");	
	}
function viewGuest(id) {
	document.location.href = "GuestFaculty.do?method=getGuestDetails&selectedEmployeeId="+id;
}

</script>
<html:form action="/GuestFaculty" enctype="multipart/form-data" method="POST">
<html:hidden property="method" value="" styleId="method"/>
<html:hidden property="pageType" value="13"/>
<html:hidden property="formName" value="GuestFacultyInfoForm"/>
<html:hidden property="selectedEmployeeId" value="" />

	
<table width="98%" border="0">
   <tr>
			<td><span class="Bredcrumbs"><bean:message key="knowledgepro.admin.sec.EmployeeCategory" />
			<span class="Bredcrumbs">&gt;&gt; <bean:message key="knowledgepro.guest.faculty" /> &gt;&gt;</span></span></td>
		</tr>
  <tr>
    <td valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="10"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="body" ><div align="left"><strong class="boxheader"><bean:message key="knowledgepro.guest.faculty"/></strong></div></td>
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
                            <td height="25" class="row-odd" align="center"><div align="center"><bean:message key="knowledgepro.slno" /></div></td>
                             <td class="row-odd" align="center"><bean:message key="knowledgepro.admin.name"/></td>
                             <td height="25" class="row-odd" align="center"><bean:message key="knowledgepro.employee.department.col"/></td>
                            <td class="row-odd" align="center"><bean:message key="knowledgepro.employee.designation"/></td>
                            <td class="row-odd" align="center" width="10%"><bean:message key="knowledgepro.edit"/></td>
                            <td class="row-odd" align="center" width="10%"><bean:message key="knowledgepro.view.print"/></td>
                            <td class="row-odd" align="center" width="10%"><bean:message key="knowledgepro.hostel.photo"/></td>
                            
                                                     
                          </tr>
                          <c:set var="temp" value="0" />
							<nested:iterate name="GuestFacultyInfoForm" property="employeeToList" indexId="count">
								<c:choose>
								<c:when test="${temp == 0}">
                    <tr>
                            <td width="2%" height="39" class="row-even"><div align="center">
                            <c:out value="${count+1}" />
                            </div></td>
                             <input type="hidden" id="guestId" name="guestId" value="<nested:write property="id"/>"/>
                            
                            <td width="20%" class="row-even">&nbsp;<nested:write property="dummyFirstName"/></td>
                            <td width="20%" height="39" class="row-even">&nbsp;<nested:write property="dummyDepartmentName"/></td>
                            <td width="20%" class="row-even" >&nbsp;<nested:write property="dummyDesignationName"/></td>
                         
                           
                            <td width="3%" class="row-even" align="center"><img src="images/edit_icon.gif"
										width="16" height="18"  style="cursor:pointer" 
										onclick="getDetailForEdit('<nested:write property="id"/>','loadEmployeeInfo')"></td>
							<td class="row-even"><div align="center"><img src="images/View_icon.gif"
											width="16" height="18" style="cursor: pointer"
											onclick="viewGuest('<nested:write  property="id" />')" /></div>
										</td>
							<td width="3%" class="row-even" align="center">
                          	<nested:equal value="true" property="isPhoto">
												<img src="images/userIcon.gif"
										width="16" height="18"  style="cursor:pointer">
											</nested:equal></td>
                     </tr>
                          <c:set var="temp" value="1" />
							</c:when>
							<c:otherwise>
                     <tr >
                            <td  width="2%" height="25" class="row-white"><div align="center">
                           
                            <c:out value="${count+1}" />
                            </div></td>
                            <input type="hidden" id="guestId" name="guestId" value="<nested:write property="id"/>"/>
                          
                            <td width="20%" class="row-white">&nbsp;<nested:write property="dummyFirstName"/></td>
                             <td width="20%" height="39" class="row-white">&nbsp;<nested:write property="dummyDepartmentName"/></td>
                            
                            <td width="20%" class="row-white" >&nbsp;<nested:write property="dummyDesignationName"/></td>
                            
                            <td  width="3%" class="row-white" align="center"><img src="images/edit_icon.gif"
										width="16" height="18"  style="cursor:pointer" 
										onclick="getDetailForEdit('<nested:write property="id"/>','loadEmployeeInfo')"></td>
							<td class="row-white" ><div align="center"><img src="images/View_icon.gif"
											width="16" height="18" style="cursor: pointer"
											onclick="viewGuest('<nested:write  property="id" />')" /></div>
							</td>
							<td width="3%" class="row-white" align="center"><nested:equal value="true" property="isPhoto">
												<img src="images/userIcon.gif"
										width="16" height="18"  style="cursor:pointer">
											</nested:equal></td>
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
                      <html:submit styleClass="formbutton" property=""  onclick="cancelMe('InitEmpId')" >Close</html:submit>
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