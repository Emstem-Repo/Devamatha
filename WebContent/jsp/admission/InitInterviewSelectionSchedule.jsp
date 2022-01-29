<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>

<script type="text/javascript" src="js/jquery.js"></script>
<script src="jquery/js/jquery-1.9.0.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="jquery.confirm/jquery.confirm/teacherAllotment.css" />
    <script src="jquery.confirm/jquery.confirm/jquery.confirm.js"></script>
    <link type="text/css"
	href="jquery/css/ui-lightness/jquery-ui-1.8.18.custom.css" rel="stylesheet" />
<script src="jquery/js/jquery-ui-1.10.0.custom.min.js" type="text/javascript"></script>
<style type="text/css">
body {
	margin-left: 0px;
	margin-top: 5px;
	margin-right: 0px;
	margin-bottom: 0px;
}
</style>
<style type="text/css">
.ui-datepicker {
	background: #6B8E23;
	border: 1px solid #555;
	color: #6B8E23;
	font-family: Arial;
	font-size: 11px;
	margin-left: 10px
}
</style>
<script type="text/javascript">
	function editDetails(id){
		document.location.href = "interviewSelectionSchedule.do?method=edit&id="+ id;
	}
	function deleteDetails(id) {
		deleteConfirm = confirm("Are you sure you want to delete this entry?");
		if (deleteConfirm == true) {
			document.location.href = "interviewSelectionSchedule.do?method=delete&id="+ id;
		}
	}
	function cancel(){
		document.location.href = "LoginAction.do?method=loginAction";
	}
	function cancelHome(){
		document.location.href = "interviewSelectionSchedule.do?method=initInterviewSelectionSchedule";
	}
	/*function program(id){
		getProgramByYear("programMap", id, "programId", updatePrograms);
		ListByAcademicYear(id);
		}
	function updatePrograms(req){
		updateOptionsFromMap(req,"programId","- Select -");
		}	*/
	function ListByAcademicYear(year){
		document.getElementById("hDetails").innerHTML = "";
		var url = "interviewSelectionSchedule.do";
		var args = "method=getInterviewSelectionScheduleRecords&academicYear="+year;
		requestOperationProgram(url, args, displayList);
		}
	function displayList(req){
		var responseObj = req.responseXML.documentElement;
		var TList = responseObj.getElementsByTagName("TList");
		var htm="<tr><td height='19' valign='top' background='images/Tright_03_03.gif'></td><td valign='top' class='news'>";
		htm=htm+"<table width='100%' border='0' align='center' cellpadding='0' 	cellspacing='0'><tr>";
		htm=htm+"<td><img src='images/01.gif' width='5' height='5'/></td><td width='914' background='images/02.gif'></td>";
		htm=htm+"<td><img src='images/03.gif' width='5' height='5'/></td></tr><tr><td width='5' background='images/left.gif'></td>";
		htm=htm+"<td valign='top'><table width='100%' cellspacing='1' cellpadding='2'><tr>";
		htm=htm+"<td width='5%'  height='25%' class='row-odd' ><div align='center'><bean:message key='knowledgepro.slno'/></div></td>";
		htm=htm+"<td width='10%'  height='25' class='row-odd' ><div align='center'><bean:message key='knowledgepro.admin.year'/></div></td>";
		htm=htm+"<td width='15%'  height='25' class='row-odd' ><div align='center'><bean:message key='knowledgepro.admin.program'/></div></td>";
		htm=htm+"<td width='10%'  height='25' class='row-odd' ><div align='center'><bean:message key='knowledgepro.admission.selection.process.date'/></div></td>";
		htm=htm+"<td width='10%'  height='25' class='row-odd' ><div align='center'><bean:message key='knowledgepro.auditorium.venue.name'/></div></td>";
		htm=htm+"<td width='10%'  height='25' class='row-odd' ><div align='center'><bean:message key='knowledgepro.admission.cut.off.date'/></div></td>";
		htm=htm+"<td width='10%'  height='25' class='row-odd' ><div align='center'><bean:message key='knowledgepro.admission.max.no.of.seats.online'/></div></td>";
		htm=htm+"<td width='10%'  height='25' class='row-odd' ><div align='center'><bean:message key='knowledgepro.admission.max.no.of.seats.offline'/></div></td>";
		htm=htm+"<td width='10%'  height='25' class='row-odd' ><div align='center'><bean:message key='knowledgepro.admission.appln.interview.total.applied'/></div></td>";
		htm=htm+"<td width='5%'  height='25' class='row-odd' ><div align='center'><bean:message key='knowledgepro.edit'/></div></td>";
		htm=htm+"<td width='5%'  height='25' class='row-odd' ><div align='center'><bean:message key='knowledgepro.delete'/></div></td></tr>";
     				
		 for ( var I = 0; I < TList.length; I++) {
				if(TList[I]!=null){
					var year = TList[I].getElementsByTagName("year")[0].firstChild.nodeValue;
					var program= TList[I].getElementsByTagName("program")[0].firstChild.nodeValue;
					var date = TList[I].getElementsByTagName("date")[0].firstChild.nodeValue;
					var id = TList[I].getElementsByTagName("id")[0].firstChild.nodeValue;
					var venue = TList[I].getElementsByTagName("venue")[0].firstChild.nodeValue;
					var cutOffDate = TList[I].getElementsByTagName("cutOffDate")[0].firstChild.nodeValue;
					var maxSeatsOnline = TList[I].getElementsByTagName("maxSeatsOnline")[0].firstChild.nodeValue;
					var maxSeatsOffline = TList[I].getElementsByTagName("maxSeatsOffline")[0].firstChild.nodeValue;
					var totalAppliedStudents = TList[I].getElementsByTagName("totalAppliedStudents")[0].firstChild.nodeValue;
					htm=htm+"<tr><td  height='25' class='row-even' align='center'>"+(parseInt(I)+1)+"</td>";
					htm=htm+"<td  height='25' class='row-even' align='center'>"+year+"</td>";
					htm=htm+"<td  height='25' class='row-even' align='left'>"+program+"</td>";
					htm=htm+"<td  height='25' class='row-even' align='center'>"+date+"</td>";
					htm=htm+"<td  height='25' class='row-even' align='center'>"+venue+"</td>";
					htm=htm+"<td  height='25' class='row-even' align='center'>"+cutOffDate+"</td>";
					htm=htm+"<td  height='25' class='row-even' align='center'>"+maxSeatsOnline+"</td>";
					htm=htm+"<td  height='25' class='row-even' align='center'>"+maxSeatsOffline+"</td>";
					htm=htm+"<td  height='25' class='row-even' align='center'>"+totalAppliedStudents+"</td>";
					htm=htm+"<td  height='25' class='row-even' align='center'><div align='center'>";
					htm=htm+"<img src='images/edit_icon.gif' height='18' style='cursor:pointer'"; 
					htm=htm+"onclick=";
					htm=htm+"editDetails("+parseInt(id)+")";
					htm=htm+"></div></td>";
					htm=htm+"<td  height='25' class='row-even' align='center'><div align='center'>";
					htm=htm+"<img src='images/delete_icon.gif' width='16' height='18' style='cursor:pointer'"; 
					htm=htm+"onclick=";
					htm=htm+"deleteDetails("+parseInt(id)+")";
					htm=htm+"></div></td></tr>";
				}
			}
			htm=htm+"</table></td><td width='5' height='30' background='images/right.gif'></td>";
			htm=htm+"</tr><tr><td height='5'><img src='images/04.gif' width='5' height='5' /></td>";
			htm=htm+"<td background='images/05.gif'></td><td><img src='images/06.gif'/></td></tr>";
			htm=htm+"</table></td><td valign='top' background='images/Tright_3_3.gif' class='news'></td></tr>";
		 document.getElementById("hDetails").innerHTML = htm;
		}
	function venues(programId){
		document.getElementById("method").value = "venues";
		document.getElementById("flag").value = "add";
		document.interviewSelectionScheduleForm.submit();
		}
	function venues1(programId){
		document.getElementById("method").value = "venues";
		document.getElementById("flag").value = "edit";
		document.interviewSelectionScheduleForm.submit();
		}
	function addMoreVenues(){
		document.getElementById("method").value = "addMoreVenues";
		document.getElementById("flag").value = "add";
		document.interviewSelectionScheduleForm.submit();
	}
	function addMoreTimes(){
		document.getElementById("method").value = "addMoreTimes";
		document.getElementById("flag").value = "add";
		document.interviewSelectionScheduleForm.submit();
	}
	function removeMoreVenues(){
		document.getElementById("method").value = "removeMoreVenues";
		document.getElementById("flag").value = "add";
		document.interviewSelectionScheduleForm.submit();
	}
	function removeMoreTimes(){
		document.getElementById("method").value = "removeMoreTimes";
		document.getElementById("flag").value = "add";
		document.interviewSelectionScheduleForm.submit();
	}
	function addMoreVenues1(){
		document.getElementById("method").value = "addMoreVenues";
		document.getElementById("flag").value = "edit";
		document.interviewSelectionScheduleForm.submit();
	}
	function addMoreTimes1(){
		document.getElementById("method").value = "addMoreTimes";
		document.getElementById("flag").value = "edit";
		document.interviewSelectionScheduleForm.submit();
	}
	function removeMoreVenues1(){
		document.getElementById("method").value = "removeMoreVenues";
		document.getElementById("flag").value = "edit";
		document.interviewSelectionScheduleForm.submit();
	}
	function removeMoreTimes1(){
		document.getElementById("method").value = "removeMoreTimes";
		document.getElementById("flag").value = "edit";
		document.interviewSelectionScheduleForm.submit();
	}
	function add(){
		document.getElementById("method").value = "addInterviewSelectionSchedule";
		document.interviewSelectionScheduleForm.submit();
		}
	
	function update()
	{
			var dateFlag = document.getElementById("dateChangedFlag").value ;
			if(dateFlag.trim()!=null && dateFlag.trim()!=""){
				if(dateFlag=='true'){
					$.confirm({
						'message'	: 'Interview Card has already been generated for the date. The Process will assign the modified dates to interview Card and intimate the students via SMS and Mail. Press Ok if u want to run the process, else press Cancel',
						'buttons'	: {
							'Ok'	: {
								'class'	: 'blue',
								'action': function(){
									$.confirm.hide();
									document.getElementById("method").value = "updateInterviewSelectionSchedule";
									document.interviewSelectionScheduleForm.submit();
								}
							},
					  'Cancel'	:  {
								'class'	: 'gray',
								'action': function(){
									$.confirm.hide();
								}
							}
						}
					});
				}else{
					document.getElementById("method").value = "updateInterviewSelectionSchedule";
					document.interviewSelectionScheduleForm.submit();
					}
			}else{
				document.getElementById("method").value = "updateInterviewSelectionSchedule";
				document.interviewSelectionScheduleForm.submit();
			}
		}
		
	function resetMessages() {
		 resetFieldAndErrMsgs();
	}
	function count(cnt){
		var totalCount=0;
		var value=document.getElementById("timeListSize").value;
		for(i=0;i<parseInt(value);i++){
			var val=document.getElementById("max_"+i).value;
			if(val!=null && val!=""){
				totalCount=parseInt(totalCount)+parseInt(val);
				}
		}
		document.getElementById("totalCount").value=totalCount;
	}
</script>
<html:form action="/interviewSelectionSchedule">
<html:hidden property="method" value="addInterviewSelectionSchedule" styleId="method"/>
<html:hidden property="formName" value="interviewSelectionScheduleForm" />
<html:hidden property="pageType" value="1" />
<html:hidden property="flag" value="" styleId="flag"/>
<html:hidden property="timeListSize" styleId="timeListSize"/>
<html:hidden property="venueFocus" styleId="venueFocus" />
<html:hidden property="timeFocus" styleId="timeFocus" />
<html:hidden property="dateChangedFlag" styleId="dateChangedFlag"/>
	<table width="100%" border="0">
		<tr>
			<td><span class="heading"> Admission <span class="Bredcrumbs">&gt;&gt;
			Interview Selection Schedule &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td valign="top">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> Interview Selection Schedule</strong></td>

					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="20" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<div align="right"><FONT color="red"> <span
						class='MandatoryMark'><bean:message
						key="knowledgepro.mandatoryfields" /></span></FONT></div>
					<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
					<FONT color="green"> <html:messages id="msg"
						property="messages" message="true">
						<c:out value="${msg}" escapeXml="false"></c:out>
						<br>
					</html:messages> </FONT></div>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td height="41" valign="top" background="images/Tright_03_03.gif"></td>

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
							<td valign="top">
							<table width="100%" cellspacing="1" cellpadding="2">
								<tr>
									<td class="row-odd" width="25%">
										<div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.admin.year"/>:</div>
									</td>
									<td class="row-even" width="25%">
									<input type="hidden" id="tempyear" value="<bean:write name="interviewSelectionScheduleForm" property="academicYear"/>" /> 
										<html:select property="academicYear" styleClass="combo"	styleId="year" name="interviewSelectionScheduleForm" onchange="ListByAcademicYear(this.value)">
											<html:option value=""> <bean:message key="knowledgepro.admin.select" />	</html:option>
											<cms:renderAcademicYearForAdmission></cms:renderAcademicYearForAdmission>
										</html:select>
                					</td>
									<td width="25%" height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span> <bean:message key="knowledgepro.admin.program"/>:</div>
									</td>
									<td width="25%" class="row-even"> 
										<c:choose>
											<c:when test="${admOperation != null && admOperation == 'edit'}">
												<html:select property="programId" styleId="programId" styleClass="comboMediumBig" onchange="venues1(this.value)">
												<html:option value="">--Select--</html:option>
                    						<c:choose>
				             					<c:when test="${programMap != null}">
				             					<html:optionsCollection name="programMap" label="value" value="key" />
												</c:when>
												<c:otherwise>
		                   							<logic:notEmpty property="programMap" name="interviewSelectionScheduleForm">
							   							<html:optionsCollection property="programMap" label="value" value="key"/>
							   						</logic:notEmpty>
					   							</c:otherwise>
					   						</c:choose>
					   					</html:select>
											</c:when>
											<c:otherwise>
												<html:select property="programId" styleId="programId" styleClass="comboMediumBig" onchange="venues(this.value)">
												<html:option value="">--Select--</html:option>
                    						<c:choose>
				             					<c:when test="${programMap != null}">
				             					<html:optionsCollection name="programMap" label="value" value="key" />
												</c:when>
												<c:otherwise>
		                   							<logic:notEmpty property="programMap" name="interviewSelectionScheduleForm">
							   							<html:optionsCollection property="programMap" label="value" value="key"/>
							   						</logic:notEmpty>
					   							</c:otherwise>
					   						</c:choose>
					   					</html:select>
											</c:otherwise>
										</c:choose>
                					</td>
								</tr>
								<tr>
									<td class="row-odd" width="25%">
										<div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.admission.selection.process.date"/>:</div>
									</td>
									<td class="row-even" width="25%"> 
                    					<html:text  property="selectionProcessDate" styleId="selectionProcessDate" size="10" maxlength="16"/>
											<script language="JavaScript">
	   													$(function(){
								 					var pickerOpts = {
								        			dateFormat:"dd/mm/yy"
								       				};  
								  					$.datepicker.setDefaults(
								   					$.extend($.datepicker.regional[""])
								  					);
								  				$("#selectionProcessDate").datepicker(pickerOpts);
													});
	                                   		</script>	
                					</td>
									<td class="row-odd" width="25%" >
										<div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.admission.cut.off.date"/>:</div>
									</td>
                					<td class="row-even" align="left" width="25%" >
                						<html:text  property="cutOffDate" styleId="cutOffDate" size="10" maxlength="16"/>
											<script language="JavaScript">
	   													$(function(){
								 					var pickerOpts = {
								        			dateFormat:"dd/mm/yy"
								       				};  
								  					$.datepicker.setDefaults(
								   					$.extend($.datepicker.regional[""])
								  					);
								  				$("#cutOffDate").datepicker(pickerOpts);
													});
	                                   		</script>
									</td>
                				</tr>
                				<tr>
                					<td class="row-odd" width="25%" align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.admission.max.no.of.seats.online"/>:
									</td>
									<td class="row-even" width="25%" >
										<html:text property="maxNumOfSeatsOnline"	styleId="maxNumOfSeatsOnline" size="5" maxlength="5" onkeypress="return isNumberKey(event)"/>&nbsp;
									</td>
									<td class="row-odd" width="25%" >
										<div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.admission.max.no.of.seats.offline"/>:</div>
									</td>
                					<td class="row-even" align="left" width="25%" >
                						<html:text property="maxNumOfSeatsOffline"	styleId="maxNumOfSeatsOffline" size="5" maxlength="5" onkeypress="return isNumberKey(event)"/>&nbsp;
									</td>
                				</tr>
                				
                				<tr>
                					<td class="row-odd" width="25%" align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.interview.Venue"/>
									</td>
									<td class="row-even" width="25%" >
										<nested:select property="venueId" styleId="venueId">
	                  						<html:option value="">--Select--</html:option>
	                  						<nested:notEmpty property="venueMap">
				   							<nested:optionsCollection property="venueMap" label="value" value="key"/>
				   						</nested:notEmpty>
				   						</nested:select>
									</td>
									<td class="row-odd" width="25%" >
									</td>
                					<td class="row-even"  width="25%" >
									</td>
                				</tr>
                				
							</table>
							</td>
							<td width="5" height="29" background="images/right.gif"></td>
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
				<logic:equal value="venue" name="admn" scope="request">
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
							<td valign="top">
							<table width="100%" cellspacing="1" cellpadding="2">
								<tr>
									<!-- <td width="30%" align="center"><b>Venues</b></td> -->
									<td  align="center"><b>Time and No.of Candidates  (Time in 24 hours format)</b></td>
								</tr>
							<!--
							//start 1
								--><tr>
									<!--  <td width="30%">
										<table>
											<tr>
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
														<td valign="top">
														<table width="100%" cellspacing="1" cellpadding="2">
														<logic:notEmpty name="interviewSelectionScheduleForm" property="venueList">
															<nested:iterate id="CME" name="interviewSelectionScheduleForm" property="venueList" indexId="cnt">
																<%
							                						String venueId="venue_"+cnt;
							                					%>
																<tr>
																	<td class="row-odd" width="50%" align="right"><span class="Mandatory">*</span>
																		<bean:write name="CME" property="venueName"/>:
																	</td>
																	<td class="row-even" width="50%" >
																		<nested:select property="venueId" styleId="<%=venueId %>">
							                    						<html:option value="">--Select--</html:option>
							                    						<nested:notEmpty property="venueMap">
												   							<nested:optionsCollection property="venueMap" label="value" value="key"/>
												   						</nested:notEmpty>
												   						</nested:select>
																	</td>
																</tr>
															</nested:iterate>
														</logic:notEmpty>
														<tr>
															<td  height="25" align="center" colspan="6">
															<table>
																<tr>
																	<c:choose>
																		<c:when test="${admOperation != null && admOperation == 'edit'}">
																			<td width="=49%" align="center">
																				<html:button property="" value="Add More" styleClass="formbutton" onclick="addMoreVenues1()"></html:button>
																			</td>
																			<td width="1%"></td>
											                   					<logic:equal value="true" property="venueFlag" name="interviewSelectionScheduleForm">
												                   					<td  align="left" width="45%">
												                   						<html:button property="" value="Remove" styleClass="formbutton" onclick="removeMoreVenues1()"></html:button>
												                   					</td>
									                   							</logic:equal>
																		</c:when>
																		<c:otherwise>
																			<td width="=49%" align="center">
																				<html:button property="" value="Add More" styleClass="formbutton" onclick="addMoreVenues()"></html:button>
																			</td>
																			<td width="1%"></td>
											                   					<logic:equal value="true" property="venueFlag" name="interviewSelectionScheduleForm">
												                   					<td  align="left" width="45%">
												                   						<html:button property="" value="Remove" styleClass="formbutton" onclick="removeMoreVenues()"></html:button>
												                   					</td>
									                   							</logic:equal>
																		</c:otherwise>
																	</c:choose>
																</tr>
															</table>
						                   					</td>
						                   				</tr>								
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
												<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
											</tr>
										</table>
									</td> -->
									<td >
										<table>
											<tr>
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
														<td valign="top">
														<table width="100%" cellspacing="1" cellpadding="2">
														<!-- start 3-->
														<% boolean disable=false;%>
															<logic:equal value="true" name="interviewSelectionScheduleForm" property="isCardgenetated">
															<% disable=true;%>
															</logic:equal>
															<logic:notEmpty name="interviewSelectionScheduleForm" property="timeList">
															<nested:iterate id="CME" name="interviewSelectionScheduleForm" property="timeList" indexId="cnt">
																<%
							                						String timeId="time_"+cnt;
																	String method="count("+cnt+")";
																	String styleId="max_"+cnt;
							                					%>
																<tr>
																	<td class="row-odd" width="15%" align="right"><span class="Mandatory">*</span>
																		<bean:write name="CME" property="timeTemplate"/>:
																	</td>
																	<td class="row-even" width="15%" >
																	<logic:notEmpty name="CME" property="hours">
																		<nested:select property="hours" styleId="<%=timeId %>" disabled='<%=disable%>'>
							                    						<nested:notEmpty property="hoursMap">
												   							<nested:optionsCollection property="hoursMap" label="value" value="key"/>
												   						</nested:notEmpty>
												   						</nested:select>:
												   						</logic:notEmpty>
												   						<logic:empty name="CME" property="hours">
																		<nested:select property="hours" >
							                    						<nested:notEmpty property="hoursMap">
												   							<nested:optionsCollection property="hoursMap" label="value" value="key"/>
												   						</nested:notEmpty>
												   						</nested:select>:
												   						</logic:empty>
												   						<logic:notEmpty name="CME" property="mins">
												   						<nested:select property="mins" disabled='<%=disable%>'>
							                    						<nested:notEmpty property="minsMap">
												   							<nested:optionsCollection property="minsMap" label="value" value="key"/>
												   						</nested:notEmpty>
												   						</nested:select>
												   						</logic:notEmpty>
												   						<logic:empty name="CME" property="mins">
												   						<nested:select property="mins" >
							                    						<nested:notEmpty property="minsMap">
												   							<nested:optionsCollection property="minsMap" label="value" value="key"/>
												   						</nested:notEmpty>
												   						</nested:select>
												   						</logic:empty>
												   						
																	</td>
																	<td class="row-odd" width="15%" align="right"><span class="Mandatory">*</span>
																		<bean:write name="CME" property="timeHenceTemplate"/>:
																	</td>
																	<td class="row-even" width="15%" >
																	<logic:notEmpty name="CME" property="henceHours">
																		<nested:select property="henceHours" styleId="<%=timeId %>" disabled='<%=disable%>'>
							                    						<nested:notEmpty property="hoursMap">
												   							<nested:optionsCollection property="hoursMap" label="value" value="key"/>
												   						</nested:notEmpty>
												   						</nested:select>:
												   					</logic:notEmpty >
																	<logic:empty name="CME" property="henceHours">
																		<nested:select property="henceHours" >
							                    						<nested:notEmpty property="hoursMap">
												   							<nested:optionsCollection property="hoursMap" label="value" value="key"/>
												   						</nested:notEmpty>
												   						</nested:select>:
												   					</logic:empty >
												   					<logic:notEmpty name="CME" property="henceMins">
												   						<nested:select property="henceMins" disabled='<%=disable%>'>
							                    						<nested:notEmpty property="minsMap">
												   							<nested:optionsCollection property="minsMap" label="value" value="key"/>
												   						</nested:notEmpty>
												   						</nested:select>
												   					</logic:notEmpty >
												   					<logic:empty name="CME" property="henceMins">
												   						<nested:select property="henceMins" >
							                    						<nested:notEmpty property="minsMap">
												   							<nested:optionsCollection property="minsMap" label="value" value="key"/>
												   						</nested:notEmpty>
												   						</nested:select>
												   					</logic:empty >
																	</td>
																	<td class="row-odd" width="10%" align="right"><span class="Mandatory">*</span>
																		<bean:write name="CME" property="candidatesTemplate"/>:
																	</td>
																	<td class="row-even" width="20%">
																		<nested:text  property="maxCandidates" size="5" maxlength="5" onkeypress="return isNumberKey(event)" styleId="<%=styleId %>" onchange="<%=method %>"/>
																	</td>
																</tr>
															</nested:iterate>
														</logic:notEmpty>
														<tr>
						                   					<td class="row-odd" width="15%"></td>
						                   					<td class="row-even" width="15%" ></td>
						                   					<td class="row-odd" width="15%"></td>
						                   					<td class="row-even" width="15%" ></td>
						                   					<td class="row-odd" width="10%" align="right">Total:</td>
						                   					<td class="row-even" width="20%" >
						                   						<html:text property="totalCount" styleId="totalCount" disabled="true" size="8" name="interviewSelectionScheduleForm"></html:text>
						                   					</td>
						                   				</tr>
														<tr>
															<td  height="25" align="center" colspan="6">
															<table>
																<tr>
																	<c:choose>
																		<c:when test="${admOperation != null && admOperation == 'edit'}">
																			<td width="49%" align="center">
																				<html:button property="" value="Add More" styleClass="formbutton" onclick="addMoreTimes1()"></html:button>
																			</td>
																			<td width="1%"></td>
											                   					<logic:equal value="true" property="timeFlag" name="interviewSelectionScheduleForm">
												                   					<td  align="left" width="45%">
												                   						<html:button property="" value="Remove" styleClass="formbutton" onclick="removeMoreTimes1()"></html:button>
												                   					</td>
									                   							</logic:equal>
																		</c:when>
																		<c:otherwise>
																			<td width="=49%" align="center">
																				<html:button property="" value="Add More" styleClass="formbutton" onclick="addMoreTimes()"></html:button>
																			</td>
																			<td width="1%"></td>
											                   					<logic:equal value="true" property="timeFlag" name="interviewSelectionScheduleForm">
												                   					<td  align="left" width="45%">
												                   						<html:button property="" value="Remove" styleClass="formbutton" onclick="removeMoreTimes()"></html:button>
												                   					</td>
									                   							</logic:equal>
																		</c:otherwise>
																	</c:choose>
																	
																</tr>
															</table>
						                   					</td>
						                   				</tr>
														<!-- end 3-->
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
												<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
											</tr>
										</table>
									</td>
								</tr>
								<!--
								//end 1
							--></table>
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
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				</logic:equal>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news" >
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
						<c:choose>
								<c:when test="${admOperation != null && admOperation == 'edit'}">
									<td width="40%" height="35" align="center"></td>
									<td width="5%" height="35" align="center">
										<html:button property="" styleClass="formbutton" value="Update" styleId="submitbutton" onclick="update()"></html:button>
									</td>
									<td width="50%" height="35" align="left">
										<html:button property="" value="Cancel" styleId="editReset" styleClass="formbutton" onclick="cancelHome()"></html:button>
									</td>
								</c:when>
								<c:otherwise>
									<td width="40%" height="35" align="center"></td>
									<td width="5%" height="35" align="center">
										<html:button property="" styleClass="formbutton" value="Submit"	styleId="submitbutton" onclick="add()"></html:button>
									</td>
									<td width="5%" height="35" align="left">
										<html:button property="" value="Reset" styleClass="formbutton" onclick="resetMessages()"></html:button>
									</td>
									<td width="50%" height="35" align="left">
										<html:button property="" value="Cancel" styleClass="formbutton" onclick="cancel()"></html:button>
									</td>
								</c:otherwise>
						</c:choose>
						</tr>

					</table>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				<tr>
					<td width="100%" id="hDetails" colspan="6"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>

					<td valign="top" class="news">&nbsp;</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
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
<script type="text/javascript">
var year = document.getElementById("tempyear").value;
if(year.length != 0) {
 	document.getElementById("year").value=year;
 	ListByAcademicYear(year);
}
hook=false;
</script>
</html:form>
