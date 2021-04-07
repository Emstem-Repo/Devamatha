<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<link rel="stylesheet" href="css/calendar.css">
<script language="JavaScript" src="js/calendar_us.js"></script>
<SCRIPT type="text/javascript">

function cancel() {
		document.location.href = "interviewTimeChange.do?method=initInterviewSearch";
	}   
function getHistoryInfo(applnId){
	var url = "interviewTimeChange.do?method=getRescheduledInfo&appNo="+applnId;
	myRef = window
	.open(url, "Interview",
			"left=20,top=20,width=900,height=900,toolbar=1,resizable=0,scrollbars=1");
}
</SCRIPT>
<html:form action="interviewTimeChange" method="POST">
	<html:hidden property="method" value="interviewTimeUpdate" />
	<html:hidden property="formName" value="interviewTimeChangeForm" />
	<html:hidden property="pageType" value="1" />
<table width="98%" border="0">
  <tr>
    <td><span class="Bredcrumbs"><bean:message key="knowledgepro.admission" /><span class="Bredcrumbs">&gt;&gt; <bean:message key="knowledgepro.interview.updateschedule" /> &gt;&gt;</span></span></td>
  </tr>
  <tr>
    <td valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="10"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="body" ><div align="left"><strong class="boxheader"><bean:message key="knowledgepro.interview.updateschedule" /></strong></div></td>
        <td width="13" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="122" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><div align="center">
            <table width="100%" height="90"  border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td height="35" valign="top" class="body" ><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
					<tr>
							<td height="20" colspan="6" class="body" align="left">
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
                            <td height="25" class="row-odd"><div align="center">SI.No</div></td>
                            <td height="25" class="row-odd"><div align="center"><bean:message key="knowledgepro.admission.applicationnumber" /></div></td>
                            <td height="25" class="row-odd"><bean:message key="knowledgepro.interview.CandidateName" /></td>
                            <td class="row-odd"><bean:message key="knowledgepro.fee.appliedyear" /></td>
                            <td class="row-odd"><bean:message key="curriculumSchemeForm.course" /></td>
                            <td class="row-odd"><bean:message key="knowledgepro.interview.InterviewTypenocol" /></td>
                            <td class="row-odd"><bean:message key="knowledgepro.interview.totalrounds" /></td>
                            <td class="row-odd"><bean:message key="knowledgepro.interview.interviewdate" /></td>
                            <td class="row-odd"><bean:message key="knowledgepro.interview.updateschedule.time.inter" /></td>
                          </tr>
                          <c:set var="temp" value="0" />
                          <% int c=0; %>
							<nested:iterate name="interviewTimeChangeForm" property="selectedCandidates" indexId="count">
									<%
										String intDate = "intDate" + count;
										c=c+1;
									%>
								<c:choose>
								<c:when test="${temp == 0}">
                          <tr>
                            <td class="row-even">&nbsp;<%=c %></td>
                            <td width="5%" height="39" class="row-even"><div align="center">
                            <nested:write property="applicationNo"/>
                            </div></td>
                            <td width="13%" height="39" class="row-even">&nbsp;<nested:write property="applicantName"/></td>
                            <td width="10%" class="row-even">&nbsp;<nested:write property="appliedYear"/></td>
                            <td width="17%" class="row-even" ><span class="star">
								<nested:write property="courseName"/>
                            </span></td>
                            <td width="12%" class="row-even" ><span class="star">
								<nested:write property="interviewType"/>
                            </span></td>
                            <td width="10%" class="row-even">&nbsp;<a href="javascript:void(0)" onclick="getHistoryInfo(<nested:write property="applicationId"/>)"><nested:write property="totalRounds"/></a></td>
                            <td width="23%" class="row-even"><nested:text property="interviewDate" styleId='<%=intDate %>' size="10" maxlength="10"/>
                            <script language="JavaScript">							
											new tcal ({
												// form name
												'formname': 'interviewTimeChangeForm',
												// input name
												'controlname': '<%=intDate %>'
											});
									</script>
                            </td>
															 <nested:hidden property="timeID"></nested:hidden>
                            <td width="11%" class="row-even"><nested:text property="interviewTime"/></td>
                          </tr>
                          <c:set var="temp" value="1" />
							</c:when>
							<c:otherwise>
                          <tr >
                            <td class="row-white">&nbsp;<%=c %></td>
                            <td height="25" class="row-white"><div align="center">
                            <nested:write property="applicationNo"/>
                            </div></td>
                            <td height="25" class="row-white" >&nbsp;<nested:write property="applicantName"/></td>
                            <td class="row-white" >&nbsp;<nested:write property="appliedYear"/></td>
                            <td class="row-white" ><span class="star">
								<nested:write property="courseName"/>
                            </span></td>
                            <td class="row-white" ><span class="star">
								<nested:write property="interviewType"/>
                            </span></td>
                            <td class="row-white">&nbsp;<a href="javascript:void(0)" onclick="getHistoryInfo(<nested:write property="applicationId"/>)"><nested:write property="totalRounds"/></a></td>
                            <td class="row-white" ><nested:text property="interviewDate" styleId='<%=intDate %>' size="10" maxlength="10"/>
                            <script language="JavaScript">							
											new tcal ({
												// form name
												'formname': 'interviewTimeChangeForm',
												// input name
												'controlname': '<%=intDate %>'
											});
									</script></td>
													<nested:hidden property="timeID"></nested:hidden>
                             <td class="row-white"><nested:text property="interviewTime"/></td>
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
                      <td width="47%" height="35"><div align="right">
                      <html:submit styleClass="formbutton"><bean:message key="knowledgepro.update" /></html:submit>
                      </div></td>
                      <td width="1%"><html:button property="" styleClass="formbutton" onclick="javascript:cancel()">
										<bean:message key="knowledgepro.cancel" />
									</html:button>
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