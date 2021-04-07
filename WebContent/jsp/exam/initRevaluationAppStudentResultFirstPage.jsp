<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
	
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@page import="java.util.Map,java.util.HashMap"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<link rel="stylesheet" href="jquery/css/ui-lightness/jquery-ui-1.8.18.custom.css">
 <script src="jquery/development-bundle/jquery-1.7.1.js"></script>
 <script src="http://code.jquery.com/jquery-latest.js"></script>
<script language="JavaScript" src="js/ajax/AjaxUtil.js"></script>
<script language="JavaScript" src="js/ajax/Ajax.js"></script>
<script type="text/javascript">
var jq=$.noConflict();

function forRevaluation(){
	//document.getElementById("isRevaluation").value = "";
	document.getElementById("isScrutiny").value = "";
	document.getElementById("isAnswerScrptCopy").value = "";
	document.getElementById("method").value = "getDataToFormForRevaluationChoose";
	document.newSupplementaryImpApplicationForm.submit();
	
}


function showButton(){
	if(document.getElementById("checkbox_id").value=='on')
	document.getElementById("buttonStyleId").disabled = false;
	else
		document.getElementById("buttonStyleId").disabled = true;

}
function getData(){
	document.getElementById("method").value = "getDataToFormForRevaluationChoose";
	document.newSupplementaryImpApplicationForm.submit();
}

function forAnswerScrptCopy(){
	document.getElementById("isRevaluation").value = "";
	document.getElementById("isScrutiny").value = "";
	//document.getElementById("isAnswerScrptCopy").value = "";
	document.getElementById("method").value = "getDataToFormForRevaluationChoose";
	document.newSupplementaryImpApplicationForm.submit();
}

function showRevInstructions(){

	document.getElementById("showCheckBoxButton").style.display = "block";
	document.getElementById("revaluationInstructionId").style.display = "block";
	document.getElementById("scrutinyInstrId").style.display = "none";
	document.getElementById("challengeValuationInstructionId").style.display = "none";
	
	
}
function showScrutinyInstructions(){
	document.getElementById("showCheckBoxButton").style.display = "block";
	document.getElementById("scrutinyInstrId").style.display = "block";
	document.getElementById("revaluationInstructionId").style.display = "none";
	document.getElementById("challengeValuationInstructionId").style.display = "none";
}

function showChallengeRevaluationInstructions(){
	document.getElementById("showCheckBoxButton").style.display = "block";
	document.getElementById("challengeValuationInstructionId").style.display = "block";
	
}

</script>


<%
	String dynaMandate = "";
%>
	<%
		dynaMandate = "<span class='Mandatory'>*</span>";
	%>
<html:form action="/newSupplementaryImpApp">
	<html:hidden property="formName" value="newSupplementaryImpApplicationForm" />
	<html:hidden property="pageType" value="3" />
	<html:hidden property="method" styleId="method" value="addRevaluationApplicationForStudentLogin" />

	<table width="100%" border="0">
		<tr>
			<td><span class="heading"><bean:message
				key="knowledgepro.exam.exam" /> <span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.exam.revaluation.application" />
			&gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/st_Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/st_Tcenter.gif" class="body"><strong
						class="boxheader"> <bean:message
						key="knowledgepro.exam.revaluation.application" /></strong></td>
					<td width="10"><img src="images/st_Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td height="20" class="news">
					<div align="right" class="mandatoryfield"><bean:message
						key="knowledgepro.mandatoryfields" /></div>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td height="20">
					<table width="100%">
					<tr>
							<td align="left">
							<div id="errorMessages" class="ui-widget">
							<div class="ui-state-error ui-corner-all" style="padding: 0 .7em;">
							<p>
							<span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
							<strong>Alert:</strong>
							<span id="err"><html:errors/></span>
							
							</p>
							
							</div>
							</div>

							<div id="messages"><div class="display-info">
							<span id="msg"><html:messages id="message" property="messages" message="true"><c:out value="${message}" escapeXml="false"></c:out><br></html:messages></span>
							</div>
							</div>
							<script type="text/javascript">
								if(document.getElementById("msg")==null ||  document.getElementById("msg").innerHTML==''){
									document.getElementById("messages").style.display="none";
									}
								if(document.getElementById("err").innerHTML==''){
									document.getElementById("errorMessages").style.display="none";
									}
							</script>
							
							</td>
						</tr>
					</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" align="center" cellpadding="0"
						cellspacing="0">
					<tr>
						<td><img src="images/01.gif" width="5" height="5" /></td>
						<td width="914" background="images/02.gif"></td>
						<td><img src="images/03.gif" width="5" height="5" /></td>
					</tr>
					<tr>
						<td width="5" background="images/left.gif"></td>
						<td valign="top" align="center">
							
						<table width="90%" cellspacing="1" cellpadding="2">
						<tr>
					<logic:equal value="UG" name="newSupplementaryImpApplicationForm" property="programTypeName">
						
						<td colspan="5" style="text-align: center; font-size: 13px;" class="studentrow-even">
						<b>  APPLICATION FOR THE REVALUATION/SCRUTINY OF <bean:write name="newSupplementaryImpApplicationForm" property="examName" /> </b>
						</td></logic:equal>
						<logic:equal value="PG" name="newSupplementaryImpApplicationForm" property="programTypeName">
						
						<td colspan="5" style="text-align: center; font-size: 13px;" class="studentrow-even">
						<b> APPLICATION FOR THE CHALLENGE VALUATION OF <bean:write name="newSupplementaryImpApplicationForm" property="examName" /> </b>
						</td></logic:equal>	
						</tr>
						<tr>
									<td height="50px" width="25%" align="center" class="pay">
									<table>



										<tr>
											<td height="50px" width="25%"  class="pay">
											<table>

												<tr>
												<logic:equal value="UG" name="newSupplementaryImpApplicationForm" property="programTypeName">

													<td height="26" width="35%" class="studentrow-even">
													Purpose of the application (Mark tick on whichever is
													applicable)</td>
													
														<td width="30%"><html:radio styleId="isRevaluation"
															property="isRevaluation" value="revaluation"
															onclick="showRevInstructions('rev')">Revaluation</html:radio>

														</td>

														<td width="30%"><html:radio styleId="isScrutiny"
															property="isRevaluation" value="scrutiny"
															onclick="showScrutinyInstructions('scrutiny')">Srcutiny</html:radio>

														</td>
													</logic:equal>
										
													
													
												</tr>

                                     <logic:equal value="UG" name="newSupplementaryImpApplicationForm" property="programTypeName">
												<tr>
													<td colspan="3">
													<table width="100%" border="0" cellpadding="0"
														align="center" >


														<tr class="row-odd" id="revaluationInstructionId"
															style="display: none;">
															<td colspan="3">
															
																<div align="center" style="font-size: 12px"><b>
															RULES FOR REVALUATION  </b></div>
															
															<table>
															
															<tr >
														
															
															<td>	
															
															<ol>
													
															
															<li>Candidates are
															eligible for revaluation of the answer papers of their
															theory examinations only. Select the papers carefully in
															which you wish to seek revaluation. No second application
															for Additional papers shall be accepted and summarily
															rejected.
															</li>
															
															<li> Candidates should apply for revaluation
															within 10 days of the date of uploading of results in
															college website.</li>
															
															<li>Application Submitted on behalf of
															the candidate, and incomplete application will be
															rejected summarily without any further reference.</li>
						
												
															<li>Applications which
															are late and not in the prescribed form and which are
															found defective in any respect will not be entertained
															and will be summarily rejected without notice.</li>
												
															
															</ol>
															<p align="center">PLEASE NOTE</p>
															<ol><li>In revaluation
															a different examiner will value the paper. Better of the
															two marks will be the final mark.</li>
															<li>The fee shall be
															refunded if thereis a difference of 15% or more in the
															score.</li>
															<li>It may be noted that the College can never set
															in any case a time limit for the issue of the results of
															revaluation. However the college will take necessary
															steps to provide the results of the revaluation at the
															earliest. The College will also therefore, not be liable
															for loss of any kind sustained by candidates concerned on
															account of the delay, if any, in issuing the results of
															revaluation.</li>
															
															</ol>
															
															</td>
															
															</tr>
															</table>

															</td>
														</tr>



														<tr class="row-odd" style="display: none;"
															id="scrutinyInstrId">

															<td colspan="3">
														<div align="center" style="font-size: 12px"><b>
															RULES FOR SCRUTINY </b></div>

															<ol>
																<li>Candidates are eligible to apply for scrutiny
																of their answer books of theory papers only.</li>

																<li>Application for Scrutiny should be submitted within 10 days of the
																date of uploading of results of college website.</li>
																<li>Every application for scrutiny should be
																submitted in the prescribed form along with the
																prescribed fee mentioned below.</li>
																<li>Rs. 250 per paper to be deposited along with
																the application fee of Rs. 25 at Federal Bank,Thevara.</li>

																<li>The applications received late or not in the
																prescribed form or defective in any respect will not be
																entertained and will be summarily rejected without any
																notice.</li>

																<li>The scope of scrutiny is to give an opportunity
																to the candidate to identify their valued answer scripts
																of examination and to check the correctness of the
																addition of marks awarded to the various answers. No
																dispute regarding marks already awarded for answers
																shall be entertained at the scrutiny.</li>

																<li>Separate application forms shall be submitted
																for each semester/year of examination, as the case may
																be.</li>

															</ol>


															</td>
														</tr>

														<tr>
															<td colspan="2" height="20" class="heading"></td>
														</tr>
													</table>
													</td>
												</tr></logic:equal>
												
												
												<logic:equal value="PG" name="newSupplementaryImpApplicationForm" property="programTypeName">
												<tr>
													<td colspan="3">
													<table width="100%" border="0" cellpadding="0"
														align="center" >


														<tr class="row-odd" id="challengeValuationInstructionId"
															style="display: block">
															<td colspan="3">
															
																<div align="center" style="font-size: 12px"><b>
															RULES FOR CHALLENGE VALUATION  </b></div>
															
															<table>
															
															<tr >
														
															
															<td>	
															
															<ol>
																			<li>PG Candidates are eligible for Challenge
																			valuation of the answer papers of their theory
																			examinations only. Select the papers carefully in
																			which you seek Challenge valuation.</li>
																			<li>Candidates should apply for challenge
																			valuation within the stipulated date notified in
																			college website/Notice Board.</li>
																			<li>Every application for challenge valuation
																			should be submitted in the prescribed form duly
																			signed by the candidate himself/ herself and not by
																			anyone else on his/her behalf.</li>
																			<li>The Candidate is required to produce a
																			photocopy of his/her Hall Ticket and Statement of
																			marks for verification of Roll No., marks etc., at
																			the time of submission of Application Form for
																			challenge valuation.</li>
																			<li>Applications which are late and not in the
																			prescribed form and which are found defective in any
																			respect will not be entertained and will be summarily
																			rejected without notice.</li>
																			<li>Entries made by the candidate be got
																			verified by the concerned Department HoD of the
																			College.</li>
																		</ol>
																		<p align="center">PLEASE NOTE</p>
																		
																<ol>
																			<li>When you apply for challenge valuation, you
																			surrender your original performance and will now
																			accept the revised performance.</li>
																			<li>In challenge valuation, two different
																			examiners will value the paper. Better of the two
																			marks will be the final mark.</li>
																			<li>The fee shall be refunded if there is a
																			difference of 15% or more in the score.</li>
																			<li>It may be noted that the College can never
																			set in any case a time limit for the issue of the
																			results of challenge valuation. However the college
																			will take necessary steps to provide the results of
																			the challenge valuation at the earliest. The College
																			will also therefore, not be liable for loss of any
																			kind sustained by candidates concerned on account of
																			the delay, if any, in issuing the results of
																			challenge valuation.</li>
																		</ol>
															
															
															</td>
															
															</tr>
															
											
															
															</table>

															</td>
														</tr>
																				<tr id="showCheckBoxButton" style="display: block">
													<td height="100%" width="100%" colspan="3"><input
														type="checkbox" name="checkbox_name" id="checkbox_id"
														onclick="showButton();">I agree the above terms and conditions
												 </td>

												</tr>



														<tr>
															<td colspan="2" height="20" class="heading"></td>
														</tr>
													</table>
													</td>
												</tr></logic:equal>
							
											 
											 
												
												

												<tr id="showCheckBoxButton" style="display: none">
													<td height="100%" width="100%" colspan="3"><input
														type="checkbox" name="checkbox_name" id="checkbox_id"
														onclick="showButton();">I agree the above terms and conditions
												 </td>

												</tr>

											</table>
											</td>

										</tr>





									</table>

									</td>


								</tr>





							</table>
							
							
							</td>
							<td width="5" height="30" background="images/right.gif"></td>
							</tr>
							
						
							
							<tr  >
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					
					
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td align="center" colspan="3" height="35"><html:submit
								styleClass="btnbg" styleId="buttonStyleId" value="Submit" disabled="true" onclick="getData()"></html:submit></td>
						</tr>
					</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>	
							
						
						<tr>
							<td height="5"><img src="images/04.gif" width="5" height="5" /></td>
							<td background="images/05.gif"></td>
							<td><img src="images/06.gif" /></td>
						</tr>
					</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>




				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="47%" height="29">&nbsp; </td>
						</tr>
					</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				
				<tr>
					<td height="10" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">&nbsp;</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td><img src="images/st_Tright_03_05.gif" width="9" height="29"></td>
					<td width="0" background="images/st_TcenterD.gif"></td>
					<td><img src="images/st_Tright_02.gif" width="9" height="29"></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</html:form>
