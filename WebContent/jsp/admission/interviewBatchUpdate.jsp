<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<SCRIPT type="text/javascript">
	function getDetails(applicationNumber,appliedYear,courseId) {
		var url  = "ApplicantDetails.do?method=getApplicantDetails&applicationNumber="+applicationNumber+"&applicationYear="+appliedYear+"&courseId="+courseId;
    	myRef = window.open(url,"ViewApplicantDetails","left=20,top=20,width=800,height=600,toolbar=1,resizable=0,scrollbars=1");
   }
	function cancel() {
		document.location.href = "InterviewBatchEntry.do?method=initStudentSearch";
	}
	function imposeMaxLength(evt, Object) {
		var keynum = (evt.which) ? evt.which : event.keyCode;
		if (keynum == 8 || keynum == 37 || keynum == 39 || keynum == 9) {
			return true;
		}
		var MaxLen = 100;
		return (Object.value.length < MaxLen);
	}   
</SCRIPT>
<html:form action="/InterviewBatchEntry">
	<html:hidden property="method" value="interviewResultBatchUpdate" />
	<html:hidden property="formName" value="interviewBatchEntryForm" />
	<html:hidden property="pageType" value="1" />
<table width="98%" border="0">
  <tr>
    <td><span class="Bredcrumbs"><bean:message key="knowledgepro.admission" /><span class="Bredcrumbs">&gt;&gt; <bean:message key="knowledgepro.admission.batchupdate" /> &gt;&gt;</span></span></td>
  </tr>
  <tr>
    <td valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="10"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="body" ><div align="left"><strong class="boxheader"><bean:message key="knowledgepro.admission.batchupdate" /></strong></div></td>
        <td width="13" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="122" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><div align="center">
            <table width="100%" height="90"  border="0" cellpadding="0" cellspacing="0">
            <tr>
							<td colspan="6" align="left">
							<div align="right"><span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields" /></span></div>
							<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
							<FONT color="green"><html:messages id="msg"
								property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
								<br>
							</html:messages></FONT></div>
							</td>
						</tr>
              <tr>
                <td height="35" valign="top" class="body" >&nbsp;</td>
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
                      <td valign="top">
                      <div style="overflow: auto; width: 914px; ">
                      <table width="100%" cellspacing="1" cellpadding="2">
                          <tr>
                            <td height="25" class="row-odd"><div align="center"><bean:message key="knowledgepro.slno" /></div></td>
                            <td height="25" class="row-odd"><bean:message key="knowledgepro.admission.applicationnumber"/></td>
                            <td class="row-odd"><bean:message key="knowledgepro.admin.name"/></td>
                            <td class="row-odd"><bean:message key="knowledgepro.admission.status" /></td>
                            <c:set var="gradeCount" value="${interviewBatchEntryForm.interviewersPerPanel}"></c:set>
                            <c:forEach var="i" begin="1" end="${gradeCount}" step="1" varStatus="status">
                            <td class="row-odd"><bean:message key="knowledgepro.admission.gradeObtained" /></td>
                            </c:forEach>
                            <td class="row-odd"><bean:message key="knowledgepro.admission.comment" /></td>
                            <td class="row-odd"><bean:message key="knowledgepro.admission.details"/></td>
                          </tr>
                          <c:set var="temp" value="0" />
							<nested:iterate name="interviewBatchEntryForm" property="selectedCandidates" indexId="count">
								<c:choose>
								<c:when test="${temp == 0}">
                          <tr>
                            <td width="5%" height="39" class="row-even"><div align="center">
                            <nested:hidden property="id"></nested:hidden>
                            <nested:hidden property="interviewSubroundId"></nested:hidden>
                            <c:out value="${count+1}" />
                            </div></td>
                            <td width="13%" height="39" class="row-even">&nbsp;<nested:write property="applicationNo"/></td>
                            <td width="19%" class="row-even">&nbsp;<nested:write property="applicantName"/></td>
                            <td width="17%" class="row-even" ><span class="star">
                              <nested:select property="interviewStatusId" styleId="interviewStatusId" styleClass="combo">
								<nested:optionsCollection property="interviewStatus"  name="interviewBatchEntryForm" label="value" value="key" />
							  </nested:select>
                            </span></td>
                            <nested:iterate property="interviewResultDetail">
                            <td width="12%" class="row-even">
                            <span class="star">
                              <nested:select property="gradeObtainedId" styleId="gradeObtainedId" styleClass="combo">
								<option value="0"><bean:message key="knowledgepro.admin.select"/></option>
								<nested:optionsCollection property="grades" name="interviewBatchEntryForm" label="value" value="key" />
							  </nested:select>
                            </span>
                            </td>
                            </nested:iterate>
                            <td width="23%" class="row-even"><nested:textarea property="comments" cols="18" rows="2" styleId="content" onkeypress="return imposeMaxLength(event,this)"></nested:textarea></td>
                            <td width="11%" class="row-even"><a href="javascript:void(0)" onclick="getDetails('<nested:write property="applicationNo"/>','<nested:write property="appliedYear"/>','<nested:write property="courseId"/>')"><bean:message key="knowledgepro.admission.details"/></a></td>
                          </tr>
                          <c:set var="temp" value="1" />
							</c:when>
							<c:otherwise>
                          <tr>
                            <td height="25" class="row-white"><div align="center">
                            <nested:hidden property="id"></nested:hidden>
                            <nested:hidden property="interviewSubroundId"></nested:hidden>
                            <c:out value="${count+1}" />
                            </div></td>
                            <td height="25" class="row-white" >&nbsp;<nested:write property="applicationNo"/></td>
                            <td class="row-white" >&nbsp;<nested:write property="applicantName"/></td>
                            <td class="row-white" ><span class="star">
                              <nested:select property="interviewStatusId" styleId="interviewStatusId" styleClass="combo">
								<nested:optionsCollection property="interviewStatus" name="interviewBatchEntryForm" label="value" value="key" />
							  </nested:select>
                            </span></td>
                            <nested:iterate property="interviewResultDetail">
                            <td class="row-white" ><span class="star">
                              <nested:select property="gradeObtainedId" styleId="gradeObtainedId" styleClass="combo">
								<option value="0"><bean:message key="knowledgepro.admin.select"/></option>
								<nested:optionsCollection property="grades" name="interviewBatchEntryForm" label="value" value="key" />
							  </nested:select>
                            </span></td>
                            </nested:iterate>
                            <td class="row-white" ><span class="row-even"><nested:textarea property="comments" cols="18" rows="2" styleId="content" onkeypress="return imposeMaxLength(event,this)"></nested:textarea></span></td>
                             <td class="row-white"><a href="javascript:void(0)" onclick="getDetails('<nested:write property="applicationNo"/>','<nested:write property="appliedYear"/>','<nested:write property="courseId"/>')"><bean:message key="knowledgepro.admission.details"/></a></td>
                          </tr>
                          <c:set var="temp" value="0" />
						</c:otherwise>
						</c:choose>
						</nested:iterate>
                      </table>
                      </div>
                      </td>
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
                      <td width="52%" height="35"><div align="right">
                      <html:submit styleClass="formbutton"><bean:message key="knowledgepro.admission.batchupdate" /></html:submit>
                      </div></td>
					  <td width="2%"></td>
                      <td width="46%" align="left"><html:button property="" styleClass="formbutton" onclick="javascript:cancel()">
										<bean:message key="knowledgepro.cancel" />
									</html:button>
					  </td>
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