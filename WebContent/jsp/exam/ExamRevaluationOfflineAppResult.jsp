<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@page import="java.util.Map,java.util.HashMap"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<script language="JavaScript" src="js/ajax/AjaxUtil.js"></script>
<script language="JavaScript" src="js/ajax/Ajax.js"></script>
<script language="JavaScript" src="js/calendar_us.js"></script>
<script src="jquery/development-bundle/jquery-1.7.1.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<title>:: CMS ::</title>
<script>
function goToFirstPage() {
		document.location.href = "examRevaluationOfflineApp.do?method=initRevaluationOfflineApp";
	}
function checkDDdisplay(){
	var selects = document.getElementsByTagName("select");
    var select;
    var isValueAvailable=false;
    var amount=0;
    // iterating all the selects
    if(selects!=null){
	    for(var count=0;count<selects.length;count++) {
	        selectObj = selects[count];
	       	if(selectObj.value!=""){
	       		isValueAvailable=true;
	       		if(document.getElementById(selectObj.value)!=null)
		       		amount=amount+parseInt(document.getElementById(selectObj.value).value);
		    }
	    }        
    }	
    if(isValueAvailable){
		$("#ddDiv").show();
		$("#ddNote").show();
		$("#marksCardButton").show();
		document.getElementById("amount").value=amount;
    }else{
    	$("#ddDiv").hide();
    	$("#ddNote").hide();
    	$("#marksCardButton").hide();
    }

    $("#display").find("td:even").addClass("row-odd");
	$("#display").find("td:odd").addClass("row-even");
}

function submitMarksCard(){
	document.getElementById("method").value="submitMarksCard";
	document.examRevaluationOfflineAppForm.submit();
}
</script>
<html:form action="/examRevaluationOfflineApp">
	<html:hidden property="method" styleId="method"	value="submitMarksCard" />
	<html:hidden property="formName" value="examRevaluationOfflineAppForm" />
	<html:hidden property="pageType" value="2" />

<logic:notEmpty name="examRevaluationOfflineAppForm" property="revalationFeeMap">
		<nested:iterate id="fee" name="examRevaluationOfflineAppForm" property="revalationFeeMap">
			<input type="hidden" name='<bean:write name="fee" property="key"/>' id='<bean:write name="fee" property="key"/>' value='<bean:write name="fee" property="value"/>'>
		</nested:iterate>
	</logic:notEmpty>
<table width="100%" border="0">
  <tr>
    <td><span class="heading"><bean:message	key="knowledgepro.exam.exam" /> <span class="Bredcrumbs">&gt;&gt; <bean:message key="knowledgepro.exam.revaluation.offline.app" /> &gt;&gt;</span></span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="body" ><strong class="boxheader"> <bean:message key="knowledgepro.exam.revaluation.offline.app" /></strong></td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td height="20" class="news"><div align="right" class="mandatoryfield"><bean:message key="knowledgepro.mandatoryfields" /></div>
        <div id="errorMessage"><FONT color="red"><html:errors /></FONT>
							<FONT color="green"><html:messages id="msg"
								property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
								<br>
							</html:messages></FONT></div>
        </td>
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
				 <td width="15%" height="30" class="row-odd" ><div align="right"><bean:message key="knowledgepro.hostel.reservation.registerNo" /> :</div></td>
                  <td width="15%" height="30" class="row-even" ><bean:write name="examRevaluationOfflineAppForm" property="registerNo" /></td>              
                  <td width="15%" height="26" class="row-odd"><div align="right" ><bean:message	key="knowledgepro.fee.studentname" /> :</div></td>
                <td width="20%" class="row-even" ><bean:write name="examRevaluationOfflineAppForm" property="studentName" /></td>
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
        <td height="10" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news">&nbsp;</td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="10" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
         
          
          
          
          <tr>
							<td height="45" colspan="4">
							<table width="98%" border="0" align="center" cellpadding="0"
								cellspacing="0">
								<tr>
									<td><img src="images/01.gif" width="5" height="5" /></td>
									<td width="914" background="images/02.gif"></td>
									<td><img src="images/03.gif" width="5" height="5" /></td>
								</tr>
								<tr>
									<td width="5" background="images/left.gif"></td>
									<td valign="top">
									<table width="100%" cellspacing="1" cellpadding="2">
										<tr>
											<td width="10%" height="25" class="row-odd"><div align="center"><bean:message
												key="knowledgepro.attendance.classname" /></div></td>
											<td width="10%" height="25" class="row-odd" align="left"><bean:message
												key="knowledgepro.exam.UnvSubCode.subjectCode" /></td>
											<td width="40%" height="25" class="row-odd" align="left"><bean:message
												key="knowledgepro.exam.UnvSubCode.subjectName" /> </td>
											<td width="20%" height="25" align="left" class="row-odd">
											<bean:message key="knowledgepro.exam.revaluation.offline.applyFor" /> 
											</td>
										<td width="20%" height="25" class="row-odd" align="left"><bean:message
												key="knowledgepro.exam.revaluation.current.status.label" /> </td>
										</tr>
										
										
										
										<c:set var="temp" value="0" />

										<nested:iterate name="examRevaluationOfflineAppForm"
											property="revAppList" id="revAppList"
											type="com.kp.cms.to.exam.ExamRevaluationApplicationTO">
										<tr>
														<td  height="25" class="row-even"
															align=left><bean:write name="revAppList"
															property="className" /></td>
														<td height="25" class="row-even"
															align="left"><bean:write name="revAppList"
															property="subjectCode" /></td>
														<td  height="25" class="row-even"
															align="left"><bean:write name="revAppList"
															property="subjectName" /></td>
														<td  height="25" class="row-even"
															align="center">	
												<logic:equal value="true" name="revAppList" property="revaluationReq">
												<nested:select property="revType"  onchange="checkDDdisplay()" styleClass="combo">
															<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
															<html:optionsCollection name="examRevaluationOfflineAppForm" property="revalationFeeMap" label="key" value="key"/>
														</nested:select>
												</logic:equal>
														</td>
													<td  height="25" class="row-even"
															align="left"><logic:notEmpty name="revAppList" property="status">
													<bean:write name="revAppList"
															property="status" />
													</logic:notEmpty></td>
													<c:set var="temp" value="1" /></tr>	
											
										</nested:iterate>


									</table>
									</td>
									<td width="5" height="30" background="images/right.gif"></td>
								</tr>
								<tr>
									<td height="5"><img src="images/04.gif" width="5"
										height="5" /></td>
									<td background="images/05.gif"></td>
									<td><img src="images/06.gif" /></td>
								</tr>
							</table>
							</td>
						</tr>
          
        </table></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
						
							<td width="5" background="images/Tright_03_03.gif"></td>
							<td width="100%" valign="top">
							<div id="ddDiv">
								<table width="100%" cellspacing="1" cellpadding="2" class="row-white" id="display">
									<tr>
										<td>
										<span class="Mandatory">*</span>DD/Ref No.
										</td>
										<td>
											<html:text property="ddNo" name="examRevaluationOfflineAppForm" styleId="ddNo" maxlength="30" size="15"></html:text>
										</td>
										<td>
										<span class="Mandatory">*</span><bean:message key="knowledgepro.admission.date"/>
										</td>
										<td>
											<html:text property="ddDate" name="examRevaluationOfflineAppForm" styleId="ddDate"></html:text>
											<script language="JavaScript">
												new tcal( {
													// form name
													'formname' :'examRevaluationOfflineAppForm',
													// input name
													'controlname' :'ddDate'
												});
											</script>
											
										</td>
										<td>
											<span class="Mandatory">*</span><bean:message key="knowledgepro.admission.amount"/> INR:
										</td>
										<td>
											<html:text property="amount" name="examRevaluationOfflineAppForm" styleId="amount" readonly="true"></html:text>
										</td>
									</tr>
									<tr>
										
										<td>
											<bean:message key="knowledgepro.hostel.reservation.bankName"/>
										</td>
										<td>
											<html:text property="bankName" name="examRevaluationOfflineAppForm" styleId="bankName" maxlength="100"> </html:text>
										</td>
										<td>
											<bean:message key="knowledgepro.hostel.reservation.branchName"/>
										</td>
										<td>
											<html:text property="branchName" name="examRevaluationOfflineAppForm" styleId="branchName" maxlength="100"></html:text>
										</td>
										<td></td>
										<td></td>
									</tr>
								</table>							
							</div>
							</td>
							<td width="5" height="30" background="images/Tright_3_3.gif"></td>
						</tr>
						<tr>
							<td width="5" background="images/Tright_03_03.gif"></td>
							<td width="100%" valign="top">
							<div align="center">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr><td style="height: 10px" align="center" colspan="3"></td></tr>
								<tr>
									<td width="46%" height="35">
									<div align="right"><html:button property=""
										styleClass="formbutton" onclick="submitMarksCard()"
										styleId="marksCardButton">Submit </html:button></div>
									</td>
									<td width="2%"></td>
									<td width="52%" align="left"><html:button property=""
										styleClass="formbutton" value="Back" onclick="goToFirstPage()"></html:button>
									</td>
								</tr>
								<tr><td style="height: 10px" align="center" colspan="3"></td></tr>								
							</table>
							</div>
							</td>
							<td width="5" height="30" background="images/Tright_3_3.gif"></td>
					</tr>
				<tr>
        <td height="10" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news">&nbsp;</td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
        <td width="0" background="images/TcenterD.gif"></td>
        <td><img src="images/Tright_02.gif" width="9" height="29"></td>
      </tr>
    </table></td>
  </tr>
</table>


</html:form>
<script type="text/javascript">
checkDDdisplay();
</script>
