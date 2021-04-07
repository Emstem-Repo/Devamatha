<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>

<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<SCRIPT type="text/javascript">
	checked = false;
	errorMessageDisplayed = false;
    function checkAll () {
        	if (checked == false) {
    			checked = true;
    		} else {
    			checked = false;
    		}
    		
    		for (var i=0;i<document.forms[0].elements.length;i++)
    		{	
    			var e=document.forms[0].elements[i];
    			if ((e.type=='checkbox'))
    			{
    				e.checked=checked;
    			}
    		}
    		setSelectedCandidatesCount();
    }

    function setSelectedCandidatesCount() {
    	var totalselectedCount = 0;
    	for (var i=0;i<document.forms[0].elements.length;i++)
    	{	
    		var e=document.forms[0].elements[i];
    		if ((e.type=='checkbox'))
    		{
    			if(e.name != "selectall") {
        			if(e.checked == true)
    				totalselectedCount = totalselectedCount +1;
    			}
    		}
    	}
    	if(document.getElementById("programWise").value == 0){
	    	 var maxIntake =  document.getElementById("maxIntake").value;
	    	 
	    	 if(totalselectedCount > maxIntake) {   	
	    	 	var errorMessage = "";
	   			errorMessage = errorMessage +" Number of selected candidates exceeds maximum intake of the course </br>";
	   			document.getElementById("err").innerHTML = errorMessage;
	   			errorMessageDisplayed = true;
	   	    } else {
	   	    	document.getElementById("err").innerHTML = "";
	   	    }
    	}    	
    }

    function getDetails(applicationNumber,appliedYear,courseId) {
       	var url  = "ApplicantDetails.do?method=selectPreference&applicationNumber="+applicationNumber+"&applicationYear="+appliedYear+"&courseId="+courseId;
    	myRef = window.open(url,"ViewApplicantDetails","left=20,top=20,width=800,height=600,toolbar=1,resizable=0,scrollbars=1");
   }

    function cancelAction() {
    	document.location.href = "FinalMeritList.do?method=cancelFinalMeritList";
    }

    function unCheckSelectAll(field) {

        if(field.checked == false) {
        	document.getElementById("selectall").checked = false;
        }

    }

</SCRIPT>
<html:form action="/FinalMeritListSearch" focus="selectall">

	<html:hidden property="method" styleId="method"
		value="submitSelectedList" />
	<html:hidden property="maxIntake" styleId="maxIntake" />
	<html:hidden property="programWise" styleId="programWise" />
<div style="overflow: auto; width: 800px;">
	<table width="98%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message
				key="knowledgepro.admission" /> <span class="Bredcrumbs">&gt;&gt;<bean:message
				key="knowledgepro.admission.finalmeritlist" /> <span
				class="Bredcrumbs">&gt;&gt;<bean:message
				key="knowledgepro.admission.finalmeritlistsearch" />&gt;&gt; </span></span></span></td>
		</tr>
		<tr>
			<td>
			<div style="overflow: auto; width: 914px; ">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="10"><img src="images/Tright_03_01.gif"></td>
					<td width="100%" background="images/Tcenter.gif" class="body">
					<div align="left"><strong class="boxheader"> <bean:message
						key="knowledgepro.admission.finalmeritlistsearch" /></strong></div>
					</td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" align="center" cellpadding="0"
						cellspacing="0">
						<tr>

							<td height="15" colspan="10" class="row-white">
							<div align="left">
							<div id="err" style="color: red;"></div>
							<div id="errorMessage"><FONT color="red">
							<c:if test="${ intakeExcessList != null && intakeExcessList != '' }">
								<logic:iterate id="excess" name="intakeExcessList" scope="request">
									<bean:write name="excess" />,	
								</logic:iterate>
							</c:if>
							<html:errors /></FONT>
							<FONT color="green"> <html:messages id="msg"
								property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
								<br>
							</html:messages> </FONT></div>
							</div>
							</td>
						</tr>
						<tr>
							<td><img src="images/01.gif" width="5" height="5" /></td>
							<td width="914" background="images/02.gif"></td>
							<td><img src="images/03.gif" width="5" height="5" /></td>
						</tr>
						<tr>
							<td width="5" background="images/left.gif"></td>
							<td width="100%" valign="top">
							<table width="99%" cellspacing="1" cellpadding="2">
								<tbody>
									<tr>
										<td colspan="14" class="row-white" height="15">
										<div align="right"><a
											href="FinalMeritListSearch.do?method=initSelectedList"
											styleClass="menuLink"> <bean:message
											key="knowledgepro.admission.selectedcandidatelist" /></a></div>
										</td>
									</tr>
									<tr>
										<td class="row-odd">&nbsp;</td>
										<td height="25" class="row-odd"><bean:message
											key="knowledgepro.admin.programtype" /></td>
										<td class="row-even"><bean:write
											name="finalMeritListForm" property="programTypeName" />
										&nbsp;</td>
										<td class="row-odd"><bean:message
											key="knowledgepro.admin.program" /></td>
										<td class="row-even"><bean:write
											name="finalMeritListForm" property="programName" />&nbsp;</td>
										<td class="row-odd"><bean:message
											key="knowledgepro.admin.course" /></td>
										<td class="row-even"><bean:write
											name="finalMeritListForm" property="courseName" /> &nbsp;</td>
										<td class="row-odd"><bean:message
											key="knowledgepro.admin.year" /></td>
										<td class="row-even"><bean:write
											name="finalMeritListForm" property="year" /> &nbsp;</td>
										<td class="row-odd">&nbsp;</td>
										<td class="row-odd">&nbsp;</td>
										<td class="row-odd">&nbsp;</td>
										<td class="row-odd">&nbsp;</td>
										<td class="row-odd">&nbsp;</td>
										<td class="row-odd">&nbsp;</td>
									</tr>
									<tr>
										<td height="25" class="row-odd">
										<div align="center"><bean:message
											key="knowledgepro.slno" /></div>
										</td>
										<td class="row-odd">
										<div align="center"><bean:message
											key="knowledgepro.admission.addtoselectedcandidatelist" /><br>
										<input type="checkbox" id="selectall" name="selectall"
											onclick="checkAll()" /></div>
										</td>
										<td height="25" class="row-odd">
										<div align="center"><bean:message
											key="knowledgepro.admission.applicationnumber" /></div>
										</td>
										<td class="row-odd">
										<div align="center"><bean:message
											key="knowledgepro.admin.name" /></div>
										</td>
										<td class="row-odd">
										<div align="center">Course</div>
										</td>
										<td class="row-odd">
										<div align="center"><bean:message
											key="knowledgepro.admin.dateofbirth" /></div>
										</td>
										<td class="row-odd">
										<div align="center"><bean:message
											key="knowledgepro.admin.gender" /></div>
										</td>
										<td width="9%" class="row-odd">
										<div align="center"><bean:message
											key="knowledgepro.admin.nationality" /></div>
										</td>
										<td class="row-odd">
										<div align="center"><bean:message
											key="knowledgepro.admin.religion" /></div>
										</td>
										<td class="row-odd">
										<div align="center">
										<div align="center"><bean:message
											key="knowladgepro.admin.subreligion" /></div>
										</div>
										</td>
										<td class="row-odd">
										<div align="center">
										<div align="center"><bean:message
											key="knowladgepro.admin.castecategory" /></div>
										</div>
										</td>

										<td class="row-odd">
										<div align="center">
										<div align="center"><bean:message
											key="knowledgepro.admin.studenttype" /></div>
										</div>
										</td>
										<td class="row-odd">
										<div align="center"><bean:message
											key="knowledgepro.admission.totalweightage" /></div>
										</td>
										<td class="row-odd">
										<div align="center"><bean:message
											key="knowledgepro.admin.pre.requisite" />
										</td>
										<td class="row-odd">
										<div align="center"><bean:message
											key="knowledgepro.admin.pre.requisite.mark" />
										</td>
										<td class="row-odd">
										<div align="center"><bean:message
											key="knowledgepro.admission.details" /></div>
										</td>
									</tr>

									<c:set var="temp" value="0" />
									<nested:iterate name="finalMeritListForm"
										property="finalMeritList" id="id" indexId="count">

										<c:set var="waiting">
											<nested:write name="id" property="inWaitingList" />
										</c:set>

										<c:choose>
											<c:when test="${waiting == 3}">
												<tr>
													<td class="row-waiting">
													<div align="center"><c:out value="${count+1}"></c:out>
													&nbsp;</div>
													</td>
													<td width="9%" class="row-waiting">
													<div align="center"><input type="checkbox" id="count"
														name="selectedCandidates"
														onclick="setSelectedCandidatesCount();unCheckSelectAll(this)"
														value='<nested:write name="id" property="finalMeritSetId"/>'>
													</td>
													<td width="10%" height="25" class="row-waiting">
													<div align="center">
													<nested:hidden name="id" property="maxIntakeCourseId" />
													<nested:write name="id"
														property="applicationId" />&nbsp;</div>
													</td>
													<td width="8%" class="row-waiting">
													<div align="center"><nested:write name="id"
														property="applicantName" /> &nbsp;</div>
													</td>
													<td width="8%" class="row-waiting">
													<div align="center"><nested:write name="id"
														property="selectedCrsName" /> &nbsp;</div>
													</td>
													<td width="10%" class="row-waiting">
													<div align="center"><nested:write name="id"
														property="applicantDOB" /> &nbsp;</div>
													</td>
														<td width="7%" class="row-waiting">
													<div align="center"><nested:write name="id"
														property="applicantGender" /> &nbsp;</div>
													</td>
													<td class="row-waiting">
													<div align="center"><nested:write name="id"
														property="applicantNationality" />&nbsp;</div>
													</td>
													<td width="9%" class="row-waiting">
													<div align="center"><nested:write name="id"
														property="applicantReligion" />&nbsp;</div>
													</td>
													<td width="10%" class="row-waiting">
													<div align="center"><nested:write name="id"
														property="applicantSubReligion" />&nbsp;</div>
													</td>
													<td width="11%" class="row-waiting">
													<div align="center"><nested:write name="id"
														property="applicantCasteCategory" />&nbsp;</div>
													</td>
													<td width="11%" class="row-waiting">
													<div align="center"><nested:write name="id"
														property="applicantResidentCategory" />&nbsp;</div>
													</td>
													<td width="9%" class="row-waiting">
													<div align="center"><nested:write name="id"
														property="applicantTotalWeightage" />&nbsp;</div>
													</td>
													<td width="9%" class="row-waiting">
													<div align="center"><nested:write name="id"
														property="preRequisiteType" />&nbsp;</div>
													</td>
													<td width="9%" class="row-waiting">
													<div align="center"><nested:write name="id"
														property="prerequisiteMarks" />&nbsp;</div>
													</td>
													<td width="7%" class="row-waiting">
													<div align="center"><a href="javascript:void(0)"
														onclick="getDetails('<nested:write name='id' property='applicationId'/>', '<bean:write name='finalMeritListForm' property='year'/>' ,'<bean:write name='finalMeritListForm' property='courseId'/>')"><bean:message
														key="knowledgepro.admission.details" /> </a></div>
													</td>
												</tr>
											</c:when>
											<c:otherwise>
												<c:choose>
													<c:when test="${temp == 0}">
														<tr>
															<td class="row-even">
															<div align="center"><c:out value="${count+1}"></c:out>
															&nbsp;</div>
															</td>
															<td width="9%" class="row-even">
															<div align="center"><input type="checkbox"
																id="count" name="selectedCandidates"
																onclick="setSelectedCandidatesCount();unCheckSelectAll(this)"
																value='<nested:write name="id" property="finalMeritSetId"/>'>
															</td>
															<td width="10%" height="25" class="row-even">
															<div align="center">
															
															<nested:hidden name="id" property="maxIntakeCourseId" />
															<nested:write name="id"
																property="applicationId" />&nbsp;</div>
															</td>
															<td width="8%" class="row-even">
															<div align="center"><nested:write name="id"
																property="applicantName" /> &nbsp;</div>
															</td>
															<td width="8%" class="row-even">
															<div align="center"><nested:write name="id"
																property="selectedCrsName" /> &nbsp;</div>
															</td>
															<td width="10%" class="row-even">
															<div align="center"><nested:write name="id"
																property="applicantDOB" /> &nbsp;</div>
															</td>
																<td width="7%" class="row-even">
													<div align="center"><nested:write name="id"
														property="applicantGender" /> &nbsp;</div>
													</td>
															<td class="row-even">
															<div align="center"><nested:write name="id"
																property="applicantNationality" />&nbsp;</div>
															</td>
															<td width="9%" class="row-even">
															<div align="center"><nested:write name="id"
																property="applicantReligion" />&nbsp;</div>
															</td>
															<td width="10%" class="row-even">
															<div align="center"><nested:write name="id"
																property="applicantSubReligion" />&nbsp;</div>
															</td>
															<td width="11%" class="row-even">
															<div align="center"><nested:write name="id"
																property="applicantCasteCategory" />&nbsp;</div>
															</td>
															<td width="11%" class="row-even">
															<div align="center"><nested:write name="id"
																property="applicantResidentCategory" />&nbsp;</div>
															</td>
															<td width="9%" class="row-even">
															<div align="center"><nested:write name="id"
																property="applicantTotalWeightage" />&nbsp;</div>
															</td>
															<td width="9%" class="row-even">
															<div align="center"><nested:write name="id"
																property="preRequisiteType" />&nbsp;</div>
															</td>
															<td width="9%" class="row-even">
															<div align="center"><nested:write name="id"
																property="prerequisiteMarks" />&nbsp;</div>
															</td>
															<td width="7%" class="row-even">
															<div align="center"><a href="javascript:void(0)"
																onclick="getDetails('<nested:write name='id' property='applicationId'/>', '<bean:write name='finalMeritListForm' property='year'/>' ,'<bean:write name='finalMeritListForm' property='courseId'/>')"><bean:message
																key="knowledgepro.admission.details" /> </a></div>
															</td>
														</tr>
														<c:set var="temp" value="1" />
													</c:when>
													<c:otherwise>
														<tr>
															<td class="row-white">
															<div align="center"><c:out value="${count+1}"></c:out>
															&nbsp;</div>
															</td>
															<td class="row-white">
															<div align="center"><input type="checkbox"
																name="selectedCandidates"
																onclick="setSelectedCandidatesCount();unCheckSelectAll(this)"
																value='<nested:write name="id" property="finalMeritSetId"/>'>
															</div>
															</td>
															<td height="25" class="row-white">
															<div align="center"><nested:write name="id"
																property="applicationId" />&nbsp;</div>
															</td>
															<td width="8%" class="row-white">
															<div align="center"><nested:write name="id"
																property="applicantName" />&nbsp;</div>
															</td>
															<td width="8%" class="row-white">
															<div align="center"><nested:write name="id"
																property="selectedCrsName" /> &nbsp;</div>
															</td>
															<td class="row-white">
															<div align="center"><nested:write name="id"
																property="applicantDOB" /> &nbsp;</div>
															</td>
																<td width="7%" class="row-white">
																<div align="center"><nested:write name="id"
																	property="applicantGender" /> &nbsp;</div>
																</td>
															<td class="row-white">
															<div align="center"><nested:write name="id"
																property="applicantNationality" />&nbsp;</div>
															</td>
															<td class="row-white">
															<div align="center"><nested:write name="id"
																property="applicantReligion" />&nbsp;</div>
															</td>
															<td class="row-white">
															<div align="center"><nested:write name="id"
																property="applicantSubReligion" />&nbsp;</div>
															</td>
															<td class="row-white">
															<div align="center"><nested:write name="id"
																property="applicantCasteCategory" />&nbsp;</div>
															</td>
															<td width="11%" class="row-white">
															<div align="center"><nested:write name="id"
																property="applicantResidentCategory" />&nbsp;</div>
															</td>
															<td class="row-white">
															<div align="center"><nested:write name="id"
																property="applicantTotalWeightage" />&nbsp;</div>
															</td>
															<td width="9%" class="row-white">
															<div align="center"><nested:write name="id"
																property="preRequisiteType" />&nbsp;</div>
															</td>
															<td width="9%" class="row-white">
															<div align="center"><nested:write name="id"
																property="prerequisiteMarks" />&nbsp;</div>
															</td>

															<td width="7%" class=row-white>
															<div align="center"><a href="javascript:void(0)"
																onclick="getDetails('<nested:write name='id' property='applicationId'/>', '<bean:write name='finalMeritListForm' property='year'/>' ,'<bean:write name='finalMeritListForm' property='courseId'/>')"><bean:message
																key="knowledgepro.admission.details" /> </a></div>
															</td>
														</tr>
														<c:set var="temp" value="0" />
													</c:otherwise>
												</c:choose>
											</c:otherwise>
										</c:choose>
									</nested:iterate>
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

					<div align="center">
					<table width="100%" height="54" border="0" cellpadding="1"
						cellspacing="2">
						<tr>
							<td width="100%" height="50" class="heading">
							<div align="center">
							<table width="100%" height="27" border="0" cellpadding="0"
								cellspacing="0">

								<tr>
									<td>
									<div align="center">
									<table width="100%" height="27" border="0" cellpadding="0"
										cellspacing="0">
										<tr>
											<td width="52%" height="45">
											<table width="100%" border="0" cellspacing="0"
												cellpadding="0">
												<tr>
													<td width="45%" height="35">
													<div align="right"><html:submit
														styleClass="formbutton">
														<bean:message key="knowledgepro.admin.add" />
													</html:submit></div>
													</td>
													<td width="2%"></td>
													<td width="53"><html:button property="cancel"
														onclick="cancelAction()" styleClass="formbutton">
														<bean:message key="knowledgepro.cancel" />
													</html:button></td>
												</tr>
											</table>
											</td>
										</tr>
									</table>
									</div>
									</td>
								</tr>
							</table>
							</div>
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
			</div>
			</td>
		</tr>
	</table></div>
</html:form>
