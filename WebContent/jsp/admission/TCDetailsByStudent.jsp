<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<script src="jquery/development-bundle/jquery-1.7.1.js"></script>
<script src="js/AC_RunActiveContent.js" type="text/javascript"></script>
<link type="text/css" href="jquery/css/ui-lightness/jquery-ui-1.8.18.custom.css" rel="stylesheet" />
<script src="jquery/js/jquery-ui-1.10.0.custom.min.js" type="text/javascript"></script>
<script type='text/javascript' src="jquery/Zebra/javascript/zebra_dialog.js"></script>
<link rel="stylesheet" href="jquery/Zebra/css/default/zebra_dialog.css"  />
<link rel="stylesheet" type="text/css" href="jquery.confirm/jquery.confirm/jquery.confirm.css" />
<script src="jquery.confirm/jquery.confirm/jquery.confirm.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<script type="text/javascript">
	function cancelAction() {
		document.location.href = "tcDetails.do?method=getCandidates";
	}
	$(document).ready(function() {
		$('#submit_tc').click(function(e) {
		    var applied=  $('#applied').val();
		    if(applied == 'true'){
		    	$.confirm({
			    		'message'	: 'Do you want to alter TC Details?',
			    		'buttons'	: {
						    			'Ok'	: {
						    						'class'	: 'blue',
						    						'action': function() {
						    									$.confirm.hide();
						    									document.getElementById("method").value="updateStudentTCDetails";
										    					document.tCDetailsForm.submit();
						    								}
						    						},
						            	'Cancel':  {
						    						'class'	: 'gray',
						    						'action': function(){
						    									$.confirm.hide();
						    								}
						    						}
			    					}
			    });
			    return false;
			}
			else{
				document.getElementById("method").value="updateStudentTCDetails";
				document.tCDetailsForm.submit();
			}
		});
	});
		
</script>
<html:form action="/tcDetails" method="post">
	<html:hidden property="method" styleId="method" value=""/>
	<html:hidden property="formName" value="tCDetailsForm" />
	<html:hidden property="pageType" value="3" />
	<html:hidden property="applied" styleId="applied"/>
	<table width="99%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message
				key="knowledgepro.admission" /> <span class="Bredcrumbs">&gt;&gt;<bean:message
				key="knowledgepro.admission.tc.details.label" />&gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="heading_white"><bean:message
						key="knowledgepro.admission.tc.details.label" /></td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="news">
					<div align="right"><span class='MandatoryMark'><bean:message
						key="knowledgepro.mandatoryfields" /> </span></div>
					<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
					<FONT color="green"><html:messages id="msg"
						property="messages" message="true">
						<c:out value="${msg}" escapeXml="false"></c:out>
						<br>
					</html:messages> </FONT></div>
					</td>
					<td valign="top" background="images/Tright_3_3.gif"></td>
				</tr>
				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top">
					<table width="100%" border="0" align="center" cellpadding="0"
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
									<td height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.admission.student" /></div>
									</td>
									<td class="row-even">
									<html:text name="tCDetailsForm" property="tcDetailsTO.studentName" styleId="studentName" maxlength="50"/>									
									</td>
									<td  height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.admission.dob" /></div>
									</td>
									<td   class="row-even">
									<html:text name="tCDetailsForm" property="tcDetailsTO.dateOfBirth" styleId="dateOfBirth" size="10" maxlength="16"/>								
									<script
									language="JavaScript">
									new tcal( {
										// form name
										'formname' :'tCDetailsForm',
										// input name
										'controlname' :'tcDetailsTO.dateOfBirth'
									});
									</script>
									</td>
								</tr><tr>
									<td height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.interview.Year" /></div>
									</td>
									<td  class="row-even"><bean:write name="tCDetailsForm" property="academicYear"/></td>
									<td  height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.attendance.class.col" /></div>
									</td>
									<td   class="row-even"><bean:write name="tCDetailsForm" property="className"/></td>
								</tr>
								<tr>
									<td   height="25" class="row-odd">
									<div align="right"><span class="Mandatory"></span><bean:message
										key="knowledgepro.fee.rollno" /></div>
									</td>
									<td   class="row-even">
									<bean:write name="tCDetailsForm" property="rollNo"/>
									</td>
									<td class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.admission.tc.details.passed"/></div>
									</td>
									<td class="row-even">
									<nested:radio property="tcDetailsTO.passed" value='yes'>
										<bean:message key="knowledgepro.yes" />
									</nested:radio> 
									<nested:radio property="tcDetailsTO.passed" value='no'>
										<bean:message key="knowledgepro.no" />
									</nested:radio>									
									</td>
								</tr>
								<tr>
									<td   height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.admission.tc.details.dateofApplication" /></div>
									</td>
									<td   class="row-even">
									<html:text name="tCDetailsForm" property="tcDetailsTO.dateOfApplication" styleId="tcDetailsTO.dateOfApplication" size="10" maxlength="16"/>
									<script
									language="JavaScript">
									new tcal( {
										// form name
										'formname' :'tCDetailsForm',
										// input name
										'controlname' :'tcDetailsTO.dateOfApplication'
									});
									</script>
									</td>
									<td class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.admission.tc.details.feespaid"/></div>
									</td>
									<td class="row-even">
									<nested:radio property="tcDetailsTO.feePaid" value='yes'>
										<bean:message key="knowledgepro.yes" />
									</nested:radio> 
									<nested:radio property="tcDetailsTO.feePaid" value='no'>
										<bean:message key="knowledgepro.no" />
									</nested:radio>									
									</td>
								</tr>
								<tr>
									<td   height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.admission.tc.details.dateOfLeaving" /></div>
									</td>
									<td   class="row-even">
									<html:text name="tCDetailsForm" property="tcDetailsTO.dateOfLeaving" styleId="tcDetailsTO.dateOfLeaving" size="10" maxlength="16"/>
									<script
									language="JavaScript">
									new tcal( {
										// form name
										'formname' :'tCDetailsForm',
										// input name
										'controlname' :'tcDetailsTO.dateOfLeaving'
									});
									</script>
									</td>
									<td class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.admission.tc.details.reasonofLeaving"/></div>
									</td>
									<td class="row-even">
									<nested:select property="tcDetailsTO.reasonOfLeaving" name="tCDetailsForm">									
									<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>									
									<html:option value="Completed the Programme">Completed the Programme</html:option>
									<html:option value="As per Request">As per Request</html:option>
									</nested:select>									
																	
									</td>
								</tr>
								<tr>
									<td align="right" class="row-odd"><span class="Mandatory">*</span>TC Issuing date :</td>
									<td class="row-even">
										<html:text name="tCDetailsForm" property="tcDetailsTO.dateOfIssue" styleId="tcDetailsTO.dateOfIssue" size="10" readonly="true"/>
										<script language="JavaScript">
											new tcal( {
												// form name
												'formname' :'tCDetailsForm',
												// input name
												'controlname' :'tcDetailsTO.dateOfIssue'
											});
										</script>
									</td>
									
									<td class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.admission.tc.details.particular"/></div>
									</td>
									<td class="row-even">
									<nested:radio property="tcDetailsTO.scholarship" value='yes'>
										<bean:message key="knowledgepro.yes" />
									</nested:radio> 
									<nested:radio property="tcDetailsTO.scholarship" value='no'>
										<bean:message key="knowledgepro.no" />
									</nested:radio>									
									</td>
								</tr>
								<tr>
									<td height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.admin.academicyear"/></div>
									</td>
									<td  class="row-even">
									<input type="hidden" id="yr" name="yr" value='<bean:write name="tCDetailsForm" property="tcDetailsTO.year"/>' />
									<nested:select property="tcDetailsTO.year" styleId="tcDetailsTO.year" styleClass="comboSmall">
										<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
						              	<cms:renderYear normalYear="true"></cms:renderYear>
									</nested:select></td>
									
									<td   height="25" class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.admission.tc.details.characterandConduct" /></div>
									</td>
									<td   class="row-even">
									<html:select property="tcDetailsTO.characterId" styleId="tcDetailsTO.characterId"  styleClass="combo">
										<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
										<html:optionsCollection property="list" label="name" value="id" />
									</html:select>
									</td>
								</tr>
								<tr>
									<!--<td height="25" class="row-odd"><div align="right">
									<bean:message key="knowledgepro.admission.student.tcDetails.examYear.label" /></div></td>									
									<td height="25" class="row-even">
										<nested:text property="tcDetailsTO.examYear" styleId="examYear" maxlength="50"/>
									</td>
									
									<td height="25" class="row-odd"><div align="right">
									<bean:message key="knowledgepro.admission.student.tcDetails.examMonth.label" /></div></td>
									<td height="25" class="row-even">
										<nested:text property="tcDetailsTO.examMonth" styleId="examMonth" maxlength="50"/>
									</td>
								
								--></tr>
								<tr>
									<td height="25" class="row-odd">
										<div align="right"><span class="Mandatory">*</span><bean:message
											key="knowledgepro.admission.student.tcDetails.publicExaminationName.label" />
										</div>
									</td>
									<td height="25" class="row-even">
										<nested:text property="tcDetailsTO.publicExamName" styleId="publicExamName" maxlength="50"/>
									</td>
									<td class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.admission.student.tcDetails.showRegNo.label"/></div>
									</td>
									<td class="row-even">
									<nested:radio property="tcDetailsTO.showRegisterNo" value='yes'>
										<bean:message key="knowledgepro.yes" />
									</nested:radio> 
									<nested:radio property="tcDetailsTO.showRegisterNo" value='no'>
										<bean:message key="knowledgepro.no" />
									</nested:radio>									
									</td>
								</tr>
								<tr>
									<td height="25" class="row-odd">
										<div align="right"><span class="Mandatory">*</span><bean:message
											key="knowledgepro.admission.student.tcDetails.subjectPassed.label1" /></div>
									</td>
									<td height="25" class="row-even">
										<nested:textarea property="tcDetailsTO.subjectPassed" styleId="subjectPassed" cols="22" rows="2" style="width:200px;"/>
									</td>
									<td height="25" class="row-odd"><div align="right">Class of Leaving<br>(Please mention if it is different from above class)<br></br></div></td>
									<td height="25" class="row-even"><nested:text property="tcDetailsTO.classOfLeaving" styleId="classOfLeaving" maxlength="50"/></td>
								</tr>
								
								<tr>
									<td height="25" class="row-odd">
									<div align="right">
										TC Prefix and No
									</div>
									</td>
									<td height="25" class="row-even">
									<nested:text property="tcDetailsTO.tcNo" name="tCDetailsForm"></nested:text>
									</td>
									
									<td height="25" class="row-odd">
									<div align="right">
									Promotion-To-Next-Class
									</div>
									</td>
									<td height="25" class="row-even">
									<nested:select property="tcDetailsTO.promotionToNextClass" name="tCDetailsForm">									
									<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>									
									<html:option value="Programme Completed">Programme Completed</html:option>
									<html:option value="Programme not Completed">Programme not Completed</html:option>
									</nested:select>									
									</td>									
								</tr>	
								<tr>
									<td height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>Admission No:</div>
									</td>
									<td class="row-even">
									<html:text name="tCDetailsForm" property="tcDetailsTO.admissionNo" styleId="admissionNo" maxlength="50"/>									
									</td>	
									<td align="right" height="25" class="row-odd"><span class="Mandatory">*</span>Admission Date:</td>
									<td class="row-even">
										<html:text name="tCDetailsForm" property="tcDetailsTO.admissionDate" styleId="admissionDate" maxlength="50"/>
										<script language="JavaScript">
											new tcal( {
												// form name
												'formname' :'tCDetailsForm',
												// input name
												'controlname' :'tcDetailsTO.admissionDate'
											});
										</script>									
									</td>								
							</table>
							</td>
							<td width="5" height="30" background="images/right.gif"></td>
						</tr>
						<tr>
							<td height="5"><img src="images/04.gif" width="5" height="5" /></td>
							<td background="images/05.gif"></td>
							<td><img src="images/06.gif" /></td>
						</tr>
					</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif"></td>
				</tr>
				
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="heading">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td height="35" align="center">
							<html:submit styleClass="formbutton" styleId="submit_tc"></html:submit> &nbsp;&nbsp;
							&nbsp;
							<html:button property="" styleClass="formbutton" value="Cancel" onclick="cancelAction()"></html:button>
							</td>
						</tr>
					</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif"></td>
				</tr>
				<tr>
					<td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
					<td width="0" background="images/TcenterD.gif"></td>
					<td><img src="images/Tright_02.gif" width="9" height="29"></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</html:form>
<script type="text/javascript">
	var yearId = document.getElementById("yr").value;
	if (yearId != null && yearId.length != 0) {
		document.getElementById("tcDetailsTO.year").value = yearId;
	}
</script>