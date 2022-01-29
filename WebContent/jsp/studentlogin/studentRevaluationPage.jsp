<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested" %>
<%@page import="com.kp.cms.constants.CMSConstants"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<script src="jquery/development-bundle/jquery-1.7.1.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<script language="JavaScript" >
function goToHomePage() {
	document.location.href = "StudentLoginAction.do?method=backToMarksCard";
}
function resetErrorMsgs() {	
	resetFieldAndErrMsgs();
}
function submitMarksCard(){
	//document.getElementById("method").value="submitMarksCard";
	document.getElementById("method").value="calculateAmount";
	document.loginform.submit();
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

    $("#display").find("td:even").addClass("studentrow-odd");
	$("#display").find("td:odd").addClass("studentrow-even");
}
</script>
<html:form action="/StudentLoginAction" >
	<html:hidden property="method" styleId="method" value="printMarksCard" />	
	<html:hidden property="pageType" value="2" />
	<html:hidden property="formName" value="loginform" />
	<html:hidden property="checkDD" value="loginform" />
	<logic:notEmpty name="loginform" property="revalationFeeMap">
		<nested:iterate id="fee" name="loginform" property="revalationFeeMap">
			<input type="hidden" name='<bean:write name="fee" property="key"/>' id='<bean:write name="fee" property="key"/>' value='<bean:write name="fee" property="value"/>'>
		</nested:iterate>
	</logic:notEmpty>
	
	<table width="98%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message key="knowledgepro.admin.studentlogin" />
			<span class="Bredcrumbs">&gt;&gt; <bean:message key="knowledgepro.exam.publishHM.MarksCard" /> &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="10"><img src="images/st_Tright_03_01.gif"></td>
					<td width="100%" background="images/st_Tcenter.gif" class="body">
					<div align="left"><strong class="boxheader"><bean:message key="knowledgepro.exam.publishHM.MarksCard" /></strong></div>
					</td>
					<td width="10"><img src="images/st_Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
						
						<tr>
							<td><img src="images/st_01.gif" width="5" height="5" /></td>
							<td width="914" background="images/st_02.gif"></td>
							<td><img src="images/st_03.gif" width="5" height="5" /></td>
						</tr>
						<tr>
							<td width="5" background="images/st_left.gif"></td>
							<td width="100%" valign="top">
							<table width="100%">
							<tr>
							<td  align="left">
							<div align="right"><span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields" /></span></div>
							<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
							<FONT color="green"><html:messages id="msg"
								property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
								<br>
							</html:messages></FONT></div>
							</td>
						</tr>
							</table>
							</td>
							<td width="5" height="30" background="images/st_right.gif"></td>
						</tr>
						<tr>
							<td width="5" background="images/st_left.gif"></td>
							<td width="100%" valign="top">
							<table width="100%" cellspacing="1" cellpadding="2" class="row-white">
							<tr>
								<td colspan="2">
									<table width="100%">
									<tr>
										<td class="studentrow-odd">Degree</td>
										<td class="studentrow-odd">:</td>
										<td class="studentrow-even"><bean:write name="loginform" property="marksCardTo.courseName"/></td>
										<td class="studentrow-odd"> Date</td>
										<td class="studentrow-odd">:</td>
										<td class="studentrow-even"><bean:write name="loginform" property="marksCardTo.date"/></td>
									</tr>
									<tr>
										<td class="studentrow-odd"><bean:write name="loginform" property="marksCardTo.semType"/></td>
										<td class="studentrow-odd">:</td>
										<td class="studentrow-even"><bean:write name="loginform" property="marksCardTo.semNo"/></td>
										<td class="studentrow-odd"> Month & Year of Examination</td>
										<td class="studentrow-odd"> :</td>
										<td class="studentrow-even"><bean:write name="loginform" property="marksCardTo.monthYear"/></td>
									</tr>
									<tr>
										<td class="studentrow-odd">Name Of Candidate</td>
										<td class="studentrow-odd">:</td>
										<td class="studentrow-even"><bean:write name="loginform" property="marksCardTo.studentName"/></td>
										<td class="studentrow-odd"> Register No</td>
										<td class="studentrow-odd">:</td>
										<td class="studentrow-even"><bean:write name="loginform" property="marksCardTo.regNo"/></td>
									</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td colspan="2">
									<table width="100%" style="border: 1px solid black; " rules="all">
										<tr height="21px" class="studentrow-odd">

						                        <td   align="center" width="3%">
						                                    SI NO
						                        </td>
						                        <td   align="center" width="25%">
						                                    SUBJECT
						                        </td>
						                        <td   align="center" width="5%">
						                                    TYPE
						                        </td>
						                        <td  align="center" >
						                                    Apply For
						                        </td>
						                        <td  align="center" >
						                                    STATUS
						                        </td>
						            </tr>
						            <logic:notEmpty name="loginform" property="marksCardTo.mainList">
										<nested:iterate id="to" name="loginform" property="marksCardTo.mainList" indexId="count">
										<c:if test="${to.theory!=null && to.theory==true && to.displaySubject==true}">
											<tr height="21px" class="studentrow-even">
												<td align="center" > <c:out value="${count+1}" /> </td>
												<td   > <bean:write name="to" property="name"/> </td>
												<td align="center" > <bean:write name="to" property="type"/> </td>
												<td align="center" > 
												<logic:equal value="true" name="loginform" property="checkRevaluation">
												<logic:equal value="true" name="to" property="revaluationReq">
												<nested:select property="revType"  onchange="checkDDdisplay()" styleClass="combo">
															<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
															<html:optionsCollection name="loginform" property="revalationFeeMap" label="key" value="key"/>
														</nested:select>
												</logic:equal>
												</logic:equal>
												
												 </td>
												<td >
													<bean:write name="to" property="status"/>
												</td>
											</tr>
											
											</c:if>
										</nested:iterate></logic:notEmpty>
										<tr class="studentrow-even">
											<td colspan="4" align="right">
												<span class="Mandatory">*</span><b><bean:message key="knowledgepro.admission.amount"/> INR:</b>
											</td>
											<td colspan="1" align="left">
												<html:text property="amount" name="loginform" styleId="amount" readonly="true"></html:text>
											</td>
											</tr>
										
									</table>
								</td>
							</tr>
							</table>
							</td>
							<td width="5" height="30" background="images/st_right.gif"></td>
						</tr>
						
						
						<!-- <tr>
						
							<td width="5" background="images/st_left.gif"></td>
							<td width="100%" valign="top">
							<div id="ddDiv">
								<table width="100%" cellspacing="1" cellpadding="2" class="row-white" id="display">
									<tr>
										<td>
										<span class="Mandatory">*</span><bean:message key="knowledgepro.exam.ddNo"/>
										</td>
										<td>
											<html:text property="ddNo" name="loginform" styleId="ddNo" maxlength="30" size="15"></html:text>
										</td>
										<td>
										<span class="Mandatory">*</span><bean:message key="knowledgepro.admission.date"/>
										</td>
										<td>
											<html:text property="ddDate" name="loginform" styleId="ddDate"></html:text>
											<script language="JavaScript">
												new tcal( {
													// form name
													'formname' :'loginform',
													// input name
													'controlname' :'ddDate'
												});
											</script>
											
										</td>
										<td>
											<span class="Mandatory">*</span><bean:message key="knowledgepro.admission.amount"/> INR:
										</td>
										<td>
											<html:text property="amount" name="loginform" styleId="amount" readonly="true"></html:text>
										</td>
									</tr>
									<tr>
										
										<td>
											<span class="Mandatory">*</span><bean:message key="knowledgepro.hostel.reservation.bankName"/>
										</td>
										<td>
											<html:text property="bankName" name="loginform" styleId="bankName" maxlength="100"> </html:text>
										</td>
										<td>
											<span class="Mandatory">*</span><bean:message key="knowledgepro.hostel.reservation.branchName"/>
										</td>
										<td>
											<html:text property="branchName" name="loginform" styleId="branchName" maxlength="100"></html:text>
										</td>
										<td></td>
										<td></td>
									</tr>
								</table>							
							</div>
							</td>
							<td width="5" height="30" background="images/st_right.gif"></td>
						</tr>
						
						<tr>
						
							<td width="5" background="images/st_left.gif"></td>
							<td width="100%" valign="top">
							<div id="ddNote">
								<table width="100%" cellspacing="1" cellpadding="2" class="row-white">
									<tr>
										<td class="heading">
										Note: 

Please take the Demand Draft for the above mentioned amount, in favor of "Christ University, Bangalore" payable at "Bangalore".<br>
Send the Demand Draft to: Office of Examination, Christ University, Hosur Road, Bangalore - 560029, Karnataka, India<br/>
The process of Revaluation/Re-totaling will be done only after the application is submitted online and the Demand Draft is received with a copy of marks card.
				
										
										
										</td>
									</tr>
								</table>							
							</div>
							</td>
							<td width="5" height="30" background="images/st_right.gif"></td>
						</tr>-->
						<tr>
							<td height="5"><img src="images/st_04.gif" width="5" height="5" /></td>
							<td background="images/st_05.gif"></td>
							<td><img src="images/st_06.gif" /></td>
						</tr>
					</table>
					<div align="center">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="46%" height="35" align="right">
							<!-- <div align="right">
							<html:button property="" styleClass="formbutton" onclick="submitMarksCard()" styleId="marksCardButton">Submit </html:button>
							</div>-->
							
							<html:button property="" styleClass="btnbg" value="Proceed with Smart Card Payment"
							onclick="submitMarksCard()"></html:button>
							</td>
							<td width="2%"></td>
							<td width="52%" align="left"><html:button property=""  styleClass="btnbg" value="Back" onclick="goToHomePage()"></html:button> 
							</td>
						</tr>
					</table>
					</div>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				<tr>
					<td><img src="images/Tright_03_05.gif" height="29" width="9"></td>
					<td background="images/TcenterD.gif" width="100%"></td>
					<td><img src="images/Tright_02.gif" height="29" width="9"></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</html:form>
<script type="text/javascript">
checkDDdisplay();
</script>