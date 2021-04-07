<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
	<link rel="stylesheet" href="css/calendar.css">
	<script language="JavaScript" src="js/admission/admissionform.js"></script>
	<script language="JavaScript" src="js/ajax/AjaxUtil.js"></script>
	<script language="JavaScript" src="js/ajax/Ajax.js"></script>
<script type="text/javascript">
function cancelAction()
{
	 document.location.href = "viewRequisitions.do?method=initViewRequisitions";
}
function getRoomsOfHostel(id){
	getRoomsByHostel("roomTypeMap",id,"roomtype",updateHostelRooms);
	
}


function updateHostelRooms(req) {
	updateOptionsFromMap(req,"roomtype","- Select -");
}	
</script>

<html:form action="viewRequisitions" method="post">
<html:hidden property="method" styleId="method" value="approveRequisitionsStatus"/>
	<html:hidden property="formName" value="viewRequisitionsForm"/>
	<html:hidden property="pageType" value="3"/>
<table width="100%" border="0" cellpadding="0" cellspacing="0" >
	<tr>
		<td colspan="3"><span class="Bredcrumbs"> <bean:message key="knowledgepro.hostel"/><span class="Bredcrumbs">&gt;&gt;
		<bean:message key = "knowledgepro.hostel.viewRequisitions"/></span></span></td>	  
	</tr>
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td  background="images/Tcenter.gif" class="body" ><strong class="boxheader"><bean:message key = "knowledgepro.hostel.viewRequisitions"/> </strong></td>
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
                              <bean:write name="viewRequisitionsForm" property="rto.hostelName"/>
                              </td>
                              </tr>
                            <tr class="row-white">
                              <td width="18%"  height="24" class="row-odd"><bean:message key="knowledgepro.hostel.reqno.view"/>:</td>
                              <td width="32%" class="row-even"><bean:write name="viewRequisitionsForm" property="rto.requisitionNol"/></td>
                              <td width="25%" class="row-odd"><bean:message key="knowledgepro.hostel.appregstaffno"/>:</td>
                              <td width="25%" class="row-even">&nbsp;<bean:write name="viewRequisitionsForm" property="rto.applNoStaffID"/></td>
                            </tr>
                            <tr >
                              <td height="25" class="row-odd" ><bean:message key="knowledgepro.admin.name"/>:</td>
                              <td height="25" class="row-even" ><bean:write name="viewRequisitionsForm" property="rto.studentName"/></td>
                              <td class="row-odd" ><bean:message key="knowledgepro.admin.program"/>:</td>
                              <td class="row-even" ><bean:write name="viewRequisitionsForm" property="rto.program"/></td>
                            </tr>
                            <tr >
                              <td height="25" class="row-odd" ><p><bean:message key="knowledgepro.interview.DateofBirth"/></p></td>
                              <td class="row-even" ><bean:write name="viewRequisitionsForm" property="rto.dateofBirth"/></td>
                              <td class="row-odd" ><bean:message key="knowledgepro.admin.religion.report"/>:</td>
                              <td class="row-even" ><bean:write name="viewRequisitionsForm" property="rto.religion"/></td>
                            </tr>
                            <tr >
                              <td height="25" class="row-odd" ><bean:message key="knowledgepro.mobile.number.col"/></td>
                              <td class="row-even" ><bean:write name="viewRequisitionsForm" property="rto.mobileNo"/></td>
                              <td class="row-odd" ><bean:message key="admissionFormForm.emailId"/>:</td>
                              <td class="row-even" ><bean:write name="viewRequisitionsForm" property="rto.email"/></td>
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
                      <td width="14%" valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
                        <tr>
                          <td><img src="images/01.gif" width="5" height="5" /></td>
                          <td  background="images/02.gif"></td>
                          <td width="10" ><img src="images/03.gif" width="5" height="5" /></td>
                        </tr>
                        <tr>
                          <td width="5" height="28"  background="images/left.gif"></td>
                          <td width="100%"  height="28" valign="top">
                          <img src='<%=request.getContextPath() %>/RequisitionServlet'  height="150Px" width="150Px" alt="Image not found"/>
                          </td>
                          <td  background="images/right.gif" width="10" height="28"></td>
                        </tr>
                        <tr>
                          <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
                          <td background="images/05.gif"></td>
                          <td><img src="images/06.gif" /></td>
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
                            <td width="25%"  height="24" rowspan="4" class="row-odd"><div align="left"><bean:message key="knowledgepro.hostel.viewRequisitions.preferHostel"/>:</div></td>
                            <td width="25%" rowspan="4" class="row-even"><bean:write name="viewRequisitionsForm" property="rto.preferredHostel"/></td>
                            <td width="25%" rowspan="4" class="row-odd"><div align="left"><bean:message key="knowledgepro.hostel.viewRequisitions.preferRoomType"/>:</div></td>
                            <td width="25%" rowspan="4" class="row-even"><bean:write name="viewRequisitionsForm" property="rto.preferredRoomType"/></td>
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
                              <td width="57%" height="25" class="row-even"><bean:write name="viewRequisitionsForm" property="rto.paddressLine1"/></td>
                            </tr>
                            <tr class="row-white">
                              <td height="25" class="row-odd"><div align="right"><bean:message key="admissionForm.studentinfo.addrs2.label"/></div></td>
                              <td height="25" class="row-even"><bean:write name="viewRequisitionsForm" property="rto.paddressLine2"/></td>
                            </tr>
                            <tr class="row-white">
                              <td height="25" class="row-odd"><div align="right"><bean:message key="admissionForm.studentinfo.addrs1.country.label"/></div></td>
                              <td height="25" class="row-even"><bean:write name="viewRequisitionsForm" property="rto.country"/></td>
                            </tr>
                            <tr class="row-white">
                              <td height="25" class="row-odd"><div align="right"><bean:message key="admissionForm.studentinfo.addrs1.state.label"/></div></td>
                              <td height="25" class="row-even"><bean:write name="viewRequisitionsForm" property="rto.state"/></td>
                            </tr>
                            <tr class="row-white">
                              <td height="25" class="row-odd"><div align="right"><bean:message key="admissionForm.studentinfo.addrs1.city.label"/></div></td>
                              <td height="25" class="row-even"><bean:write name="viewRequisitionsForm" property="rto.city"/></td>
                            </tr>
                            <tr class="row-even">
                              <td height="25" class="row-odd"><div align="right"><bean:message key="knowledgepro.admission.zipCode"/></div></td>
                              <td height="25"><bean:write name="viewRequisitionsForm" property="rto.zip"/></td>
                            </tr>
                            <tr class="row-even">
                              <td height="25" class="row-odd"><div align="right"><bean:message key="knowledgepro.phone.number.col"/></div></td>
                              <td height="25"><bean:write name="viewRequisitionsForm" property="rto.phone"/></td>
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
                                <td width="57%" height="25" class="row-even"><bean:write name="viewRequisitionsForm" property="rto.gaddressLine1"/></td>
                              </tr>
                              <tr class="row-white">
                                <td height="25" class="row-odd"><div align="right"><bean:message key="admissionForm.studentinfo.addrs2.label"/></div></td>
                                <td height="25" class="row-even"><bean:write name="viewRequisitionsForm" property="rto.gaddressLine2"/></td>
                              </tr>
                              <tr class="row-white">
                                <td height="25" class="row-odd"><div align="right"><bean:message key="admissionForm.studentinfo.addrs1.country.label"/></div></td>
                                <td height="25" class="row-even"></td>
                              </tr>
                              <tr class="row-white">
                                <td height="25" class="row-odd"><div align="right"><bean:message key="admissionForm.studentinfo.addrs1.state.label"/></div></td>
                                <td height="25" class="row-even"></td>
                              </tr>
                              <tr class="row-white">
                                <td height="25" class="row-odd"><div align="right"><bean:message key="admissionForm.studentinfo.addrs1.city.label"/></div></td>
                                <td height="25" class="row-even"><bean:write name="viewRequisitionsForm" property="rto.gcity"/></td>
                              </tr>
                              <tr class="row-even">
                                <td height="25" class="row-odd"><div align="right"><bean:message key="knowledgepro.admission.zipCode"/></div></td>
                                <td height="25"><bean:write name="viewRequisitionsForm" property="rto.gzip"/></td>
                              </tr>
                              <tr class="row-even">
                                <td height="25" class="row-odd"><div align="right"><bean:message key="knowledgepro.phone.number.col"/></div></td>
                                <td height="25"><bean:write name="viewRequisitionsForm" property="rto.gphone"/></td>
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
                <tr >
                  <td height="25" colspan="2" valign="top" class="body" ><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                    <tr>
                      <td><img src="images/01.gif" width="5" height="5"></td>
                      <td  background="images/02.gif"></td>
                      <td width="10" ><img src="images/03.gif" width="5" height="5"></td>
                    </tr>
                    <tr>
                      <td width="0" height="28"  background="images/left.gif"></td>
                      <td width="100%" height="28" valign="top"><table width="100%" border="0" cellpadding="2" cellspacing="1">
                        <tr class="row-white">
                          <td class="row-odd"> The requisition status is                            </td>
                          <td class="row-even"><html:select property="status1"  styleId="status1" styleClass="combo">
	          		   		<html:option value="">Select</html:option>
	        			    <html:option value="Approved">Approved</html:option>
	        			    <html:option value="Pending">Pending</html:option>
	        			    <html:option value="Rejected">Rejected</html:option>
		           		</html:select>	</td>
                          <td class="row-odd">for Hostel </td>
                          <td class="row-even"><html:select
								property="hostelId1" styleClass="combo"
								styleId="hostelId1" onchange="getRoomsOfHostel(this.value)">
								<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
								<logic:notEmpty property="hostelList" name="viewRequisitionsForm">
	                    <html:optionsCollection property="hostelList" name="viewRequisitionsForm" label="name" value="id"/>
					</logic:notEmpty>
							</html:select></td>
                          <td class="row-odd">Room Type </td>
                          <td class="row-even"> <html:select property="roomtype"  styleId="roomtype" styleClass="combo">
          		   		<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
            		    	 	<c:if test="${roomTypeMap != null}">
            		    	 		<html:optionsCollection name="roomTypeMap" label="value" value="key"/>
            		    	 	</c:if>	 
	           		</html:select></td>
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
        <td valign="top" class="news"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="49%" height="35"><div align="right">
                <html:submit styleClass="formbutton">
								<bean:message key="knowledgepro.submit" />
							</html:submit>
            </div></td>
            <td width="3%"></td>
            <td width="48%"><html:button property="" styleClass="formbutton" onclick="cancelAction()">
										<bean:message key="knowledgepro.cancel" /></html:button></td>
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