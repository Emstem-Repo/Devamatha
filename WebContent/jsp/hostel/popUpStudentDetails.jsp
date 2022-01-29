<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
	<link rel="stylesheet" href="css/calendar.css">
	<link rel="stylesheet" href="css/styles.css">
	<script language="JavaScript" src="js/admission/admissionform.js"></script>
	<script language="JavaScript" src="js/ajax/AjaxUtil.js"></script>
	<script language="JavaScript" src="js/ajax/Ajax.js"></script>
<script type="text/javascript">

</script>
<html:form action="/HostelerDatabase" method="post">
<html:hidden property="method" value="gettHostelerDatabase"/>
<html:hidden property="formName" value="hostelerDatabaseForm" />

		<table width="100%" border="0" cellpadding="0" cellspacing="0" >
	     <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td  background="images/Tcenter.gif" class="body" ><strong class="boxheader">Hosteler Details </strong></td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="10" valign="top" background="images/Tright_03_03.gif"></td>
        <td height="10" valign="top"></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
							<td colspan="6" align="left">
							<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
							<FONT color="green"><html:messages id="msg"
								property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
								<br>
							</html:messages></FONT></div>
							</td>
						</tr>		
      <tr>
        <td height="20" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="100%" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td><img src="images/01.gif" width="5" height="5"></td>
            <td width="100%" background="images/02.gif"></td>
            <td width="11" ><img src="images/03.gif" width="5" height="5"></td>
          </tr>
          <tr>
            <td width="10" height="0"  background="images/left.gif"></td>
            <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
                <tr >
                  <td height="25" colspan="2" class="row-white" ><div align="center"><span class="heading"><bean:message key="knowledgepro.applicationform"/><br>
                  </span></div></td>
                </tr>
                
                <tr >
                  <td height="25" colspan="2" class="row-white" ><table width="100%" border="0" cellpadding="0" cellspacing="0">
                    <tr>
                      <td width="86%" valign="top"><table width="99%" border="0" cellpadding="0" cellspacing="0">
                        <tr>
                          <td><img src="images/01.gif" width="5" height="5"></td>
                          <td  background="images/02.gif"></td>
                          <td width="10" ><img src="images/03.gif" width="5" height="5"></td>
                        </tr>
                        <tr>
                          <td width="5" height="28"  background="images/left.gif"></td>
                          <td width="100%"  height="28" valign="top"><table width="100%" height="129" border="0" cellpadding="2" cellspacing="1">
                            <tr class="row-white">
                              <td  height="24" class="row-odd">	<bean:message key="knowledgepro.hostel.name.col"/></td>
                              <td colspan="3" class="row-even">
                              <bean:write name="studentDetails" property="hostelName"/>
                              </td>
                              </tr>
                            <tr class="row-white">
                              <td width="18%"  height="24" class="row-odd"><bean:message key="knowledgepro.hostel.reqno.view"/>:</td>
                              <td width="32%" class="row-even"><bean:write name="studentDetails" property="requisitionNol"/></td>
                              <td width="25%" class="row-odd"><bean:message key="knowledgepro.hostel.appregstaffno"/>:</td>
                              <td width="25%" class="row-even">&nbsp;<bean:write name="studentDetails" property="applNoStaffID"/></td>
                            </tr>
                            <tr >
                              <td height="25" class="row-odd" ><bean:message key="knowledgepro.admin.name"/>:</td>
                              <td height="25" class="row-even" ><bean:write name="studentDetails" property="studentName"/></td>
                              <td class="row-odd" ><bean:message key="knowledgepro.admin.program"/>:</td>
                              <td class="row-even" ><bean:write name="studentDetails" property="program"/></td>
                            </tr>
                            <tr >
                              <td class="row-odd" ><bean:message key="knowledgepro.admin.religion.report"/>:</td>
                              <td class="row-even" ><bean:write name="studentDetails" property="religion"/></td>
                              <td height="25" class="row-odd" ><bean:message key="knowledgepro.admin.course"/>:</td>
                              <td height="25" class="row-even" ><bean:write name="studentDetails" property="courseName"/></td>
                            </tr>
                           
                            <tr >
                              <td height="25" class="row-odd" ><p><bean:message key="knowledgepro.interview.DateofBirth"/></p></td>
                              <td class="row-even" ><bean:write name="studentDetails" property="dateofBirth"/></td>
                                 <td class="row-odd" ><bean:message key="knowledgepro.attendance.activityattendence.class"/>:</td>
                              <td class="row-even" ><bean:write name="studentDetails" property="className"/></td>
                            </tr>
                            <tr >
                              <td height="25" class="row-odd" ><bean:message key="knowledgepro.mobile.number.col"/></td>
                              <td class="row-even" ><bean:write name="studentDetails" property="mobileNo"/></td>
                              <td class="row-odd" ><bean:message key="admissionFormForm.emailId"/>:</td>
                              <td class="row-even" ><bean:write name="studentDetails" property="email"/></td>
                            </tr>
                          </table></td>
                          <td  background="images/right.gif" width="10" height="28"></td>
                        </tr>
                        <tr>
                          <td height="5"><img src="images/04.gif" width="5" height="5"></td>
                          <td background="images/05.gif"></td>
                          <td><img src="images/06.gif" ></td>
                        </tr>
                      </table></td>
                   
                    </tr>
                  </table></td>
                </tr>
                <tr >
                  <td height="25" colspan="2" class="row-white" ><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                      <tr>
                        <td><img src="images/01.gif" width="5" height="5"></td>
                        <td  background="images/02.gif"></td>
                        <td width="10" ><img src="images/03.gif" width="5" height="5"></td>
                      </tr>
                      <tr>
                        <td width="0" height="28"  background="images/left.gif"></td>
                        <td width="100%" height="28" valign="top"><table width="100%" height="26" border="0" cellpadding="2" cellspacing="1">
                          <tr class="row-white">
                            <td width="25%"  height="24" rowspan="4" class="row-odd"><div align="left"><bean:message key="knowledgepro.hostel.name.col"/>:</div></td>
                            <td width="25%" rowspan="4" class="row-even"><bean:write name="studentDetails" property="hostelName"/></td>
                            <td width="25%" rowspan="4" class="row-odd"><div align="left"><bean:message key="knowledgepro.hoste.roomtype.col"/></div></td>
                            <td width="25%" rowspan="4" class="row-even"><bean:write name="studentDetails" property="roomType"/></td>
                          </tr>
                          <tr class="row-white"></tr>
                          <tr class="row-white"></tr>
                        </table></td>
                        <td  background="images/right.gif" width="10" height="28"></td>
                      </tr>
                      <tr>
                        <td height="5"><img src="images/04.gif" width="5" height="5"></td>
                        <td background="images/05.gif"></td>
                        <td><img src="images/06.gif" ></td>
                      </tr>
                  </table></td>
                </tr>
                <tr >
                  <td width="49%" height="17" valign="top" class="Bredcrumbs" >Name and Address of the Parent/Guardian</td>
                  <td width="51%" valign="top" class="Bredcrumbs" >Name and Address of the Local Guardian</td>
                </tr>
                <tr >
                  <td height="197" valign="top" class="row-white" ><table width="98%" border="0" cellpadding="0" cellspacing="0">
                      <tr>
                        <td ><img src="images/01.gif" width="5" height="5"></td>
                        <td background="images/02.gif"></td>
                        <td><img src="images/03.gif" width="5" height="5"></td>
                      </tr>
                      <tr>
                        <td width="5"  background="images/left.gif"></td>
                        <td height="91" valign="top"><table width="100%"  border="0" cellpadding="0" cellspacing="1">
                            <tr class="row-white">
                              <td width="43%" height="25" class="row-odd"><div align="right"><bean:message key="admissionForm.studentinfo.addrs1.label"/></div></td>
                              <td width="57%" height="25" class="row-even"><bean:write name="studentDetails" property="paddressLine1"/></td>
                            </tr>
                            <tr class="row-white">
                              <td height="25" class="row-odd"><div align="right"><bean:message key="admissionForm.studentinfo.addrs2.label"/></div></td>
                              <td height="25" class="row-even"><bean:write name="studentDetails" property="paddressLine2"/></td>
                            </tr>
                            <tr class="row-white">
                              <td height="25" class="row-odd"><div align="right"><bean:message key="admissionForm.studentinfo.addrs1.country.label"/></div></td>
                              <td height="25" class="row-even"><bean:write name="studentDetails" property="country"/></td>
                            </tr>
                            <tr class="row-white">
                              <td height="25" class="row-odd"><div align="right"><bean:message key="admissionForm.studentinfo.addrs1.state.label"/></div></td>
                              <td height="25" class="row-even"><bean:write name="studentDetails" property="state"/></td>
                            </tr>
                            <tr class="row-white">
                              <td height="25" class="row-odd"><div align="right"><bean:message key="admissionForm.studentinfo.addrs1.city.label"/></div></td>
                              <td height="25" class="row-even"><bean:write name="studentDetails" property="city"/></td>
                            </tr>
                            <tr class="row-even">
                              <td height="25" class="row-odd"><div align="right"><bean:message key="knowledgepro.admission.zipCode"/></div></td>
                              <td height="25"><bean:write name="studentDetails" property="zip"/></td>
                            </tr>
                            <tr class="row-even">
                              <td height="25" class="row-odd"><div align="right"><bean:message key="knowledgepro.phone.number.col"/></div></td>
                              <td height="25"><bean:write name="studentDetails" property="phone"/></td>
                            </tr>
                            
                        </table></td>
                        <td  background="images/right.gif" width="5" height="5"></td>
                      </tr>
                      <tr>
                        <td height="5"><img src="images/04.gif" width="5" height="5"></td>
                        <td background="images/05.gif"></td>
                        <td><img src="images/06.gif" ></td>
                      </tr>
                  </table></td>
                  <td valign="top" class="row-white" ><div align="right">
                      <table width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
                        <tr>
                          <td ><img src="images/01.gif" width="5" height="5"></td>
                          <td width="914" background="images/02.gif"></td>
                          <td><img src="images/03.gif" width="5" height="5"></td>
                        </tr>
                        <tr>
                          <td width="5"  background="images/left.gif"></td>
                          <td width="100%" height="91" valign="top"><table width="100%"  border="0" cellpadding="0" cellspacing="1">
                              <tr class="row-white">
                                <td width="43%" height="25" class="row-odd"><div align="right"><bean:message key="admissionForm.studentinfo.addrs1.label"/></div></td>
                                <td width="57%" height="25" class="row-even"><bean:write name="studentDetails" property="gaddressLine1"/></td>
                              </tr>
                              <tr class="row-white">
                                <td height="25" class="row-odd"><div align="right"><bean:message key="admissionForm.studentinfo.addrs2.label"/></div></td>
                                <td height="25" class="row-even"><bean:write name="studentDetails" property="gaddressLine2"/></td>
                              </tr>
                              <tr class="row-white">
                                <td height="25" class="row-odd"><div align="right"><bean:message key="admissionForm.studentinfo.addrs1.country.label"/></div></td>
                                <td height="25" class="row-even"><bean:write name="studentDetails" property="gcountry"/></td>
                              </tr>
                              <tr class="row-white">
                                <td height="25" class="row-odd"><div align="right"><bean:message key="admissionForm.studentinfo.addrs1.state.label"/></div></td>
                                <td height="25" class="row-even"><bean:write name="studentDetails" property="gstate"/></td>
                              </tr>
                              <tr class="row-white">
                                <td height="25" class="row-odd"><div align="right"><bean:message key="admissionForm.studentinfo.addrs1.city.label"/></div></td>
                                <td height="25" class="row-even"><bean:write name="studentDetails" property="gcity"/></td>
                              </tr>
                              <tr class="row-even">
                                <td height="25" class="row-odd"><div align="right"><bean:message key="knowledgepro.admission.zipCode"/></div></td>
                                <td height="25"><bean:write name="studentDetails" property="gzip"/></td>
                              </tr>
                              <tr class="row-even">
                                <td height="25" class="row-odd"><div align="right"><bean:message key="knowledgepro.phone.number.col"/></div></td>
                                <td height="25"><bean:write name="studentDetails" property="gphone"/></td>
                              </tr>
                              
                          </table></td>
                          <td  background="images/right.gif" width="5" height="5"></td>
                        </tr>
                        <tr>
                          <td height="5"><img src="images/04.gif" width="5" height="5"></td>
                          <td background="images/05.gif"></td>
                          <td><img src="images/06.gif" ></td>
                        </tr>
                      </table>
                  </div></td>
                </tr>
                               
            </table></td>
            <td  background="images/right.gif" width="11" height="3"></td>
          </tr>
          <tr>
            <td height="5"><img src="images/04.gif" width="5" height="5"></td>
            <td background="images/05.gif"></td>
            <td><img src="images/06.gif" ></td>
          </tr>
        </table></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
            <tr>
        <td height="20" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news">&nbsp;</td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
        <td background="images/TcenterD.gif"></td>
        <td><img src="images/Tright_02.gif" width="9" height="29"></td>
      </tr>
    </table>
</html:form>