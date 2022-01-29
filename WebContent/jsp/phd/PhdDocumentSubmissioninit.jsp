<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@page import="java.util.Map,java.util.HashMap"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<script language="JavaScript" src="js/calendar.js"></script>
<link href="css/styles.css" rel="stylesheet" type="text/css">
<script language="JavaScript">
function searchStudent(){
	document.getElementById("method").value="searchStudentList";
	document.phdDocumentSubmissionForm.submit();
    }

function resetPhdDocumentDetails(){
	document.getElementById("method").value="searchStudentList";
	document.phdDocumentSubmissionForm.submit();
	}
function addPhdDocument() {
	    document.getElementById("method").value="addPhdDocumentSubmission";
	    document.phdDocumentSubmissionForm.submit();
                            }
function AddMoreGuide() {
    document.getElementById("method").value="AddMoreGuide";
    document.phdDocumentSubmissionForm.submit();
                        }

function closePhdDocument() {
	document.location.href = "PhdDocumentSubmissionInit.do?method=initPhdDocumentSubmission";
	}

function addResearchPublication(){
	document.getElementById("method").value="addResearchPublication";
	document.phdDocumentSubmissionForm.submit();
}
function addConference(){
	document.getElementById("method").value="addConference";
	document.phdDocumentSubmissionForm.submit();
}
function AddFinalVivaList(){
	document.getElementById("method").value="AddFinalVivaList";
	document.phdDocumentSubmissionForm.submit();
}
function AddSynopisToList(){
	document.getElementById("method").value="AddSynopisToList";
	document.phdDocumentSubmissionForm.submit();
}
function deletesynopsisList(id){
	document.getElementById("id").value=id;
	document.getElementById("method").value="deletesynopsisList";
	document.phdDocumentSubmissionForm.submit();
}
function deleteVivaList(id){
	document.getElementById("id").value=id;
	document.getElementById("method").value="deleteVivaList";
	document.phdDocumentSubmissionForm.submit();
}
function deleteResearchPublication(researchPublication,issn,id){
	document.getElementById("id").value=id;
	document.getElementById("tempResearch").value=researchPublication;
	document.getElementById("tempIssn").value=issn;
	document.getElementById("method").value="deleteResearchPublication";
	document.phdDocumentSubmissionForm.submit();
}
function deleteConference(nameProgram,organised,id){
	document.getElementById("id").value=id;
	document.getElementById("tempNameProgram").value=nameProgram;
	document.getElementById("tempOrganBy").value=organised;
	document.getElementById("method").value="deleteConference";
	document.phdDocumentSubmissionForm.submit();
}
function getCurrentDate(count) {
	var currentDate = new Date();
	var day = currentDate.getDate();
	var month = currentDate.getMonth() + 1 ;
	var year = currentDate.getFullYear() ;
    var date=(day + "/" + month + "/" + year);
	var check=document.getElementById("submittedDate_"+count).value;
	if(check!=null && check!=""){
	   document.getElementById("submittedDate_"+count).value=null;
	}else{
		document.getElementById("submittedDate_"+count).value=date;
	}
}
function setInternalGuide(value){
	 var args ="method=setInternalGuide&type="+value;
	  var url = "AjaxRequest.do";
	  requestOperation(url, args, updateGuideInternal);
}
function updateGuideInternal(req) {
	   updateOptionsFromMap(req, "guideId", "- Select -");
   }
function setInternalCoGuide(value){
	 var args ="method=setInternalGuide&type="+value;
	  var url = "AjaxRequest.do";
	  requestOperation(url, args, updateCoguideInternal);
}
function updateCoguideInternal(req) {
	   updateOptionsFromMap(req, "coGuideId", "- Select -");
   }
function setPanelInternalGuide(value){
	 var args ="method=setInternalGuide&type="+value;
	  var url = "AjaxRequest.do";
	  requestOperation(url, args, updatePanelGuideInternal);
}
function updatePanelGuideInternal(req) {
	   updateOptionsFromMap(req, "synopsis", "- Select -");
}
function setFinalInternalGuide(value){
	 var args ="method=setInternalGuide&type="+value;
	  var url = "AjaxRequest.do";
	  requestOperation(url, args, updateFinalGuideInternal);
}
function updateFinalGuideInternal(req) {
	   updateOptionsFromMap(req, "finalViva", "- Select -");
}

function imposeMaxLength(field,size) {
    if (field.value.length > size) {
        field.value = field.value.substring(0, size);
    }
}


//to display the text areas length 
function len_displays(field,size){
	 if (field.value.length > size) {
	        field.value = field.value.substring(0, size);
	    }
}

// to display the text areas length 
function len_display(field,size){
	 if (field.value.length > size) {
	        field.value = field.value.substring(0, size);
	    }
}
</script>

<html:form action="/PhdDocumentSubmissionInit" method="post">
	<html:hidden property="method" styleId="method" value="" />
	<html:hidden property="id" styleId="id" value="" />
	<html:hidden property="tempResearch" styleId="tempResearch" value="" />
	<html:hidden property="tempIssn" styleId="tempIssn" value="" />
	<html:hidden property="tempOrganBy" styleId="tempOrganBy" value="" />
	<html:hidden property="tempNameProgram" styleId="tempNameProgram" value="" />
	<html:hidden property="formName" value="phdDocumentSubmissionForm" />
	<html:hidden property="focusValue" styleId="focusValue"/>
	<html:hidden property="pageType" value="1" />

	<script language="JavaScript" src="calendar_us.js"></script>
	<link rel="stylesheet" href="calendar.css">
	<link href="../css/styles.css" rel="stylesheet" type="text/css">
	<table width="99%" border="0">

		<tr>
			<td><span class="Bredcrumbs"> <bean:message key="knowledgepro.phd" /><span class="Bredcrumbs">&gt;&gt; <bean:message key="knowledgepro.phd.studentInfo" /> &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td valign="top">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
					<td colspan="2" background="images/Tcenter.gif" class="heading_white"><bean:message key="knowledgepro.phd.studentInfo" /></td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9" height="29"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td colspan="2" class="news"><div align="right"><FONT color="red"><span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields" /></span></FONT></div>
					<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
					<FONT color="green"> <html:messages id="msg" property="messages" message="true">
						<c:out value="${msg}" escapeXml="false"></c:out><br>
					</html:messages> </FONT></div></td>
					<td valign="top" colspan="2" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>

					<td  height="145" valign="top" class="news">
					<table width="100%" border="0" align="center" cellpadding="0"
						cellspacing="0">
						<tr>
							<td><img src="images/01.gif" width="5" height="5" /></td>
							<td width="100%" background="images/02.gif"></td>
							<td><img src="images/03.gif" width="5" height="5" /></td>
						</tr>
						<tr>
							<td width="5" background="images/left.gif"></td>
							<td valign="top">
							<table width="100%" cellspacing="1" cellpadding="2">
						    <tr>
							<td height="25" class="row-odd" width="25%"><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.hostel.allocation.regno"/></div></td>
							<td width="32%" height="25" class="row-even" ><label></label>
                            <span class="star">
                            <c:choose>
            		    <c:when test="${phdDocumentSubmissionForm.studentName == null}">
              	   		<html:text property="registerNo" styleId="registerNo" size="20" maxlength="50" />
              		    </c:when>
              		    <c:otherwise>
                		<html:text property="registerNo" styleId="registerNo" size="20" maxlength="50" disabled="true"/>
              		    </c:otherwise>
              	     </c:choose>
                       </span></td>						
							</tr>
							</table>
							</td>
							<td width="5" height="29" background="images/right.gif"></td>
						</tr>
				<logic:notEmpty name="phdDocumentSubmissionForm" property="studentDetails">	
                        <tr>
                        <td width="5" background="images/left.gif"></td>
						<td valign="top">
						 <table width="100%" cellspacing="1" cellpadding="2">
			             <tr>
			             <td height="25" class="row-odd" width="25%"><div align="right"><bean:message key="knowledgepro.fee.studentname"/></div></td>
			             <td width="32%" height="25" class="row-even" ><label></label>
                         <span class="star">
                         <bean:write name="phdDocumentSubmissionForm" property="studentName"/>
                         </span></td>						
			             </tr>
			              <tr>
			             <td height="25" class="row-odd" width="25%"><div align="right"><bean:message key="knowldgepro.exam.upload.marks.Course"/></div></td>
			             <td width="32%" height="25" class="row-even" ><label></label>
                         <span class="star">
                         <bean:write name="phdDocumentSubmissionForm" property="courseName"/>
                         </span></td>						
			             </tr>
			              <tr>
			             <td height="25" class="row-odd" width="25%"><div align="right"><bean:message key="knowledgepro.cancelattendance.batches"/></div></td>
			             <td width="32%" height="25" class="row-even" ><label></label>
                         <span class="star">
                         <bean:write name="phdDocumentSubmissionForm" property="batch"/>
                         </span></td>						
			             </tr>
                         </table>
                         </td>
							<td width="5" height="29" background="images/right.gif"></td>
                        </tr>
                        </logic:notEmpty>
						<tr>
							<td height="5"><img src="images/04.gif" width="5" height="5" /></td>
							<td background="images/05.gif"></td>
							<td><img src="images/06.gif" /></td>
						</tr>
					</table>
					</td>
					<td valign="top" colspan="2" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<logic:empty name="phdDocumentSubmissionForm" property="studentDetails">
				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td colspan="2" valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
				   <tr>
                   <td width="45%" height="35"><div align="center">
                 	<html:submit property="" styleClass="formbutton" onclick="searchStudent()"><bean:message key="knowledgepro.admin.search"/></html:submit>
                </div></td>
                 </tr>
			 </table>
			 </td>
			<td valign="top"  colspan="2" background="images/Tright_3_3.gif" class="news"></td>
			</tr>
			</logic:empty>
			
		
			<logic:notEmpty name="phdDocumentSubmissionForm" property="studentDetails">
				   <tr>
				   <td height="10" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						   <tr>
					<td colspan="2" class="heading" align="left">
						<bean:message key="knowledgepro.phd.aggrement.details"/>
					</td>
					</tr>
					</table>
					</td>
					<td valign="top" colspan="2" background="images/Tright_3_3.gif" class="news"></td>
				  </tr>	
	           
		 <tr>
        <td height="90" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="100%" cellspacing="1" cellpadding="2">
          <tr>
            <td height="45" colspan="4" ><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
              <tr>
                <td ><img src="images/01.gif" width="5" height="5" /></td>
                <td width="100%" background="images/02.gif"></td>
                <td><img src="images/03.gif" width="5" height="5" /></td>
              </tr>
              <tr>
                <td width="5"  background="images/left.gif"></td>
                <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
                  <tr >
                    <td width="40%" height="25" class="row-odd" align="center" ><bean:message key="knowledgepro.phd.Guide"/></td>
                    <td width="40%" height="25" class="row-odd" align="center"><bean:message key="knowledgepro.phd.CoGuide"/></td>
                    <td width="20%" height="25" class="row-odd" align="center"><bean:message key="knowledgepro.phd.signedOn"/></td>
                  </tr>
                
                   	<tr>
                   	<logic:iterate id="CME" name="phdDocumentSubmissionForm" property="studentDetails" indexId="count">
                   		                            
													<c:choose>
														<c:when test="${count%2 == 0}">
															<tr class="row-even">
														</c:when>
														<c:otherwise>
															<tr class="row-even">
														</c:otherwise>
					 								</c:choose>
                   		<td height="25" class="white">
                   		<span	class="star">Internal Guide:
				        <html:radio property="guideDisplay" name="phdDocumentSubmissionForm" value="true" styleId="fixed" onchange="setInternalGuide(this.value)">Yes</html:radio>
					    <html:radio property="guideDisplay"  name="phdDocumentSubmissionForm" value="false" styleId="fixed" onchange="setInternalGuide(this.value)">No</html:radio></span>&nbsp;&nbsp;
                   		<span	class="star"> 
				        <html:select property="guideId" styleClass="comboLarge" styleId="guideId"><html:option value=""><bean:message key="knowledgepro.admin.select" />
				        </html:option><html:optionsCollection name="GuideDetails" label="name" value="id" /></html:select></span>
					    </td>
                   		<td height="25" class="white">
                   		<span class="star">Internal Guide:
				        <html:radio property="coGuideDisplay" name="phdDocumentSubmissionForm" value="true" styleId="fixed" onchange="setInternalCoGuide(this.value)">Yes</html:radio>
					    <html:radio property="coGuideDisplay"  name="phdDocumentSubmissionForm" value="false" styleId="fixed" onchange="setInternalCoGuide(this.value)">No</html:radio></span>&nbsp;&nbsp;
                   		<span class="star"> 
				        <html:select property="coGuideId" styleClass="comboLarge" styleId="coGuideId" ><html:option value=""><bean:message key="knowledgepro.admin.select" />
				        </html:option><html:optionsCollection name="CoGuideDetails" label="name" value="id" /></html:select></span>
				      </td>
						<td class="row-even"><html:text
									name="phdDocumentSubmissionForm" property="signedOn"
									styleId="signedOn" size="16" maxlength="16" /> <script
									language="JavaScript">
									new tcal( {
									// form name
									'formname' :'phdDocumentSubmissionForm',
									// input name
									'controlname' :'signedOn'
									});
									</script></td>
                </logic:iterate>
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
          </tr>
        </table></td>
        <td width="10" valign="top" colspan="2" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      </logic:notEmpty>
      
    <logic:notEmpty name="phdDocumentSubmissionForm" property="studentDetailsList">
                	<tr>
					<td height="10" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
					<td colspan="2" class="heading" align="left">
						<bean:message key="knowledgepro.phd.document.details"/>
					</td>
					</tr>
					</table>
					</td>
					<td width="10" valign="top" colspan="2" background="images/Tright_3_3.gif" class="news"></td>
				</tr>	
	         
       <tr>
        <td height="115" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="100%" cellspacing="1" cellpadding="2">
         
          <tr>
            <td height="45" colspan="4" ><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
              <tr>
                <td ><img src="images/01.gif" width="5" height="5" /></td>
                <td width="100%" background="images/02.gif"></td>
                <td><img src="images/03.gif" width="5" height="5" /></td>
              </tr>
              <tr>
                <td width="5"  background="images/left.gif"></td>
                <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
                  <tr >
                    <td height="25" class="row-odd" ><div align="center"><bean:message key="knowledgepro.slno"/></div></td>
                     <td height="25" class="row-odd" align="center"><bean:message key="knowledgepro.phd.document.name"/></td>
                    <td height="25" class="row-odd" align="center" ><bean:message key="knowledgepro.phd.Submission.Schedule.assigndate"/></td>
                    <td height="25" class="row-odd" align="center"><bean:message key="knowledgepro.phd.isreminder.mailrequired"/></td>
                    <td height="25" class="row-odd" align="center"><bean:message key="knowledgepro.phd.guide.feerequired"/></td>
                    <td height="25" class="row-odd" align="center"><bean:message key="knowledgepro.phd.can.submit.online"/></td>
                    <td height="25" class="row-odd" align="center"><bean:message key="knowledgepro.admin.ducuments.submitted"/></td>
                    <td height="25" class="row-odd" align="center"><div align="center"><bean:message key="knowledgepro.phd.Guide.submitted_date" /></div></td> 
                  </tr>
                <c:set var="temp" value="0"/>
                <nested:iterate id="CME" name="phdDocumentSubmissionForm" property="studentDetailsList" indexId="count">
                              
					   	<tr>
					                           	 <c:choose>
														<c:when test="${count%2 == 0}">
															<tr class="row-even">
														</c:when>
														<c:otherwise>
															<tr class="white">
														</c:otherwise>
					 								</c:choose>
                   		<td width="6%" height="20" ><div align="center"><c:out value="${count + 1}"/></div></td>
                   		<td width="15%" height="20"  align="left"><bean:write name="CME" property="documentName"/></td>
                   		<td width="10%" height="20"  align="center"><bean:write name="CME" property="assignDate"/></td>
                   		<td width="15%" height="20"  align="center"><bean:write name="CME" property="isReminderMail"/></td>
                   		<td width="10%" height="20"  align="center"><bean:write name="CME" property="guidesFee"/></td>
                   		<td width="20%" height="20"  align="center"><bean:write name="CME" property="canSubmitOnline"/></td>
                   		<td width="10%" height="20"  align="center">
						<input type="hidden" name="studentDetailsList[<c:out value='${count}'/>].tempChecked"	id="hidden_<c:out value='${count}'/>"
						value="<nested:write name='CME' property='tempChecked'/>" />
														
						<input type="checkbox" name="studentDetailsList[<c:out value='${count}'/>].checked" id="<c:out value='${count}'/>" onchange="getCurrentDate('<c:out value='${count}'/>')"/>
						
						<script type="text/javascript">
						var studentId = document.getElementById("hidden_<c:out value='${count}'/>").value;
						if(studentId == "on") {
						document.getElementById("<c:out value='${count}'/>").checked = true;
						}		
						</script>
						</td>
						<%String submittedDate="submittedDate_"+String.valueOf(count);%>
					    <td height="20" ><div align="center">									
								<nested:text styleId='<%=submittedDate%>'
								property="submittedDate"  size="10" maxlength="10" />
										<script language="JavaScript">
							 new tcal( {
								// form name
								'formname' :'phdDocumentSubmissionForm',
								// input name
								'controlname' :'<%=submittedDate%>'
							});
						</script></div>
					</td>
					</tr>
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
        </table></td>
        <td width="10" valign="top" colspan="2" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      </logic:notEmpty>
      
      <logic:notEmpty property="studentDetails" name="phdDocumentSubmissionForm">      
                  <tr>
					<td height="10" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0"> <tr>
					<td colspan="2" class="heading" align="left"><bean:message key="KnowledgePro.phd.research.publication"/>
					</td>
					</tr>
					</table>
					</td>
					<td width="10" valign="top" colspan="2" background="images/Tright_3_3.gif" class="news"></td>
				  </tr>	
         <tr>
            <td height="160" valign="top" background="images/Tright_03_03.gif"></td>
            <td valign="top" class="news"><table width="100%" cellspacing="1" cellpadding="2">
              <tr>
            <td height="45" colspan="4" ><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
              <tr>
                <td ><img src="images/01.gif" width="5" height="5" /></td>
                <td width="100%" background="images/02.gif"></td>
                <td><img src="images/03.gif" width="5" height="5" /></td>
              </tr>
              <tr>
                <td width="5"  background="images/left.gif"></td>
								<td valign="top">
									<table width="100%" cellspacing="1" cellpadding="2">
									
			                         <html:hidden property="researchDescriptionLength" name="phdDocumentSubmissionForm" styleId="researchDescriptionLength"/>
			                              <logic:notEmpty property="researchDescription" name="phdDocumentSubmissionForm">  
                                         <% int des=0;%>       
			                               <nested:iterate property="researchDescription" name="phdDocumentSubmissionForm" id="experience" indexId="count">
			                                <% des=des+1;%> 
									   <tr>
									    <%String presearchid="presearchid_"+String.valueOf(count);%>
                                        <td class="row-odd" width="12%" height="25" align="right"> 
											<bean:message key="KnowledgePro.phd.research.publication" /> :
									  	</td>
										<td height="25" width="12%" class="row-even"><span	class="star"> 
				                         <nested:select property="researchPublication" styleClass="comboLarge" styleId="<%=presearchid%>" >
				                         <html:option value=""><bean:message key="knowledgepro.admin.select" />
				                          </html:option><html:optionsCollection name="ResearchPublications" label="name" value="id" /></nested:select></span>
				                        </td>
										<%String issn="issn_"+String.valueOf(count);%>
										<td class="row-odd" width="11%" align="right"> 
											<bean:message key="knowledgepro.employee.issn"/> :
										</td>
										<td width="12%" height="25" align="left"  class="row-even" >
								               <nested:text property="issn" styleId="<%=issn%>" maxlength="48"></nested:text>
										</td>
					                 
										<%String issueNumber="issueNumber_"+String.valueOf(count);%>
										<td class="row-odd" width="14%" align="right"> 
											<bean:message key="knowledgepro.employee.issueNumber"/> :
										</td>
										<td width="13%" height="25" align="left"  class="row-even"  >
								               <nested:text property="issueNumber" styleId="<%=issueNumber%>" onkeypress="return isNumberKey(event)"  maxlength="18"></nested:text>
										</td>
										<%String level="level_"+String.valueOf(count);%>
										<td class="row-odd" width="12%" align="right"> 
											<bean:message key="knowledgepro.employee.level"/> :
										</td>
										<td width="14%" height="25" align="left" class="row-even" >
								         <span class="star">
					                     	<nested:select property="level" styleId="level" styleClass="combo" >
							                  <html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
							                  <html:option value="National"></html:option>
							                   <html:option value="InterNational"></html:option>
						                       </nested:select></span>
										</td>
 									   </tr>
 									   
 									  <tr>
									  	<%String nameJournal="nameJournal_"+String.valueOf(count);%>
										<td class="row-odd" width="12%" align="right"> 
											<bean:message key="knowledgepro.phd.name.of.journal"/> :
										</td>
										<td width="12%" height="25" align="left"  class="row-even" >
								               <nested:text property="nameJournal" styleId="<%=nameJournal%>" maxlength="48"></nested:text>
										</td>
										<%String title="title_"+String.valueOf(count);%>
										<td class="row-odd" width="11%" align="right"> 
											<bean:message key="knowledgepro.employee.title"/> :
										</td>
										<td width="12%" height="25" align="left"  class="row-even" >
								               <nested:text property="title" styleId="<%=title%>" maxlength="48"></nested:text>
										</td>
										<%String datePhd="datePhd_"+String.valueOf(count);%>
										<td class="row-odd" width="14%" align="right"> 
											<bean:message key="knowledgepro.phd.date.month.year.publication"/> :
										</td>
										<td width="13%" height="25" align="left" class="row-even">
								               <nested:text property="datePhd" styleId="<%=datePhd%>" maxlength="18" ></nested:text>
										</td>
										<%String volumeNo="volumeNo_"+String.valueOf(count);%>
										<td class="row-odd" width="12%" align="right"> 
											<bean:message key="knowledgepro.employee.volume.Number"/> :
										</td>
										<td width="14%" height="25" align="left" class="row-even">
								               <nested:text property="volumeNo" styleId="<%=volumeNo%>" onkeypress="return isNumberKey(event)" maxlength="18"></nested:text>
										</td>
 									   </tr>
 									    <tr>
									  	<%String description="description_"+String.valueOf(count);%>
										<td class="row-odd" width="12%" align="right"> 
											<bean:message key="knowledgepro.phd.anyother.Details"/> :
										</td>
										 <% if(des>1) {%>
										<td height="25" align="left"  class="row-even" colspan="6">
								              <nested:textarea property="description" styleClass="Textarea" cols="100" styleId="<%=description%>" onkeypress="return imposeMaxLength(this,999);" onchange="len_displays(this,999)"></nested:textarea>
										</td>
										 <%} else {%>
										 <td height="25" align="left"  class="row-even" colspan="7">
								              <nested:textarea property="description" styleClass="Textarea" cols="100" styleId="<%=description%>" onkeypress="return imposeMaxLength(this,999);" onchange="len_displays(this,999)"></nested:textarea>
										</td>
										 <% }%>
										<% if(des>1) {%>
										<td width="5%" height="25" align="center" class="row-even">
										<img src="images/removeIcon.gif" width="81" height="22" style="cursor: pointer" onclick="deleteResearchPublication('<bean:write name="experience" property="researchPublication"/>','<bean:write name="experience" property="issn"/>','<bean:write name="experience" property="id"/>')">
 									   </td>
 									   <%} %>
 									   </tr>
 									   <tr><td  height="20" class="row-even" align="center" colspan="8"></td></tr>
			                     </nested:iterate>
			                         <tr>
			                         	<td  class="row-even" align="center" colspan="8">
			                           <html:submit property="" styleClass="formbutton" value="Add more"  onclick="addResearchPublication(); return false;"></html:submit>
										</td> 
									</tr>
					 				</logic:notEmpty>
									</table>
								</td>
								<td width="5" height="30"  background="images/right.gif"></td>
							</tr>
							<tr>
								<td height="5"><img src="images/04.gif" width="5" height="5" /></td>
								<td background="images/05.gif"></td>
								<td><img src="images/06.gif" /></td>
							</tr>
						</table>
						</td>
						</tr>
                    </table></td>
                <td width="10" valign="top" colspan="2" background="images/Tright_3_3.gif" class="news"></td>
             </tr>
               </logic:notEmpty>
               
               
               
               
               
               
               
             <logic:notEmpty property="studentDetails" name="phdDocumentSubmissionForm">      
                  <tr>
					<td height="10" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0"> <tr>
					<td colspan="2" class="heading" align="left"><bean:message key="KnowledgePro.phd.research.Conference"/>
					</td>
					</tr>
					</table>
					</td>
					<td width="10" valign="top" colspan="2" background="images/Tright_3_3.gif" class="news"></td>
				  </tr>	
         <tr>
            <td height="160" valign="top" background="images/Tright_03_03.gif"></td>
            <td valign="top" class="news"><table width="100%" cellspacing="1" cellpadding="2">
              <tr>
            <td height="45" colspan="4" ><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
              <tr>
                <td ><img src="images/01.gif" width="5" height="5" /></td>
                <td width="100%" background="images/02.gif"></td>
                <td><img src="images/03.gif" width="5" height="5" /></td>
              </tr>
              <tr>
                <td width="5"  background="images/left.gif"></td>
								<td valign="top">
									<table width="100%" cellspacing="1" cellpadding="2">
									<% int con=0; %>
			                         <html:hidden property="conferenceLength" name="phdDocumentSubmissionForm" styleId="conferenceLength"/>
			                              <logic:notEmpty property="conferenceList" name="phdDocumentSubmissionForm">          
			                               <nested:iterate property="conferenceList" name="phdDocumentSubmissionForm" id="conference" indexId="count">
			                               <% con=con+1; %>
									   <tr>
										<%String participated="participated_"+String.valueOf(count);%>
										<%String participated1="participated1_"+String.valueOf(count);%>
										<td class="row-even" colspan="2" align="center">
										 <bean:message key="KnowledgePro.phd.name.participated" />
									     <nested:radio property="participated" styleId="<%=participated%>" value="Participated"></nested:radio>
									     <bean:message key="KnowledgePro.phd.name.Paper.Presented" />
									     <nested:radio property="participated" styleId="<%=participated1%>" value="Paper Presented"></nested:radio>
										</td>
					                 
										<%String organizedBy="organizedBy_"+String.valueOf(count);%>
										<td class="row-odd" width="17%" align="right"> 
											<bean:message key="knowledgepro.pgm.organisedBy"/> :
										</td>
										<td width="17%" height="25" align="left" class="row-even">
								               <nested:text property="organizedBy" styleId="<%=organizedBy%>" maxlength="48"></nested:text>
										</td>
										<%String nameProgram="nameProgram_"+String.valueOf(count);%>
										<td class="row-odd" width="16%" align="right"> 
											<bean:message key="KnowledgePro.phd.name.of.program"/> :
										</td>
										<td width="16%" height="25" align="left" class="row-even">
								               <nested:text property="nameProgram" styleId="<%=nameProgram%>" maxlength="48"></nested:text>
										</td>
 									   </tr>
 									   
 									  <tr>
									  	<%String dateFrom="dateFrom_"+String.valueOf(count);%>
										<td class="row-odd" width="17%" align="right"> 
											<bean:message key="knowledgepro.admin.mobNewsEventsDetailsDateFrom"/> :
										</td>
										<td  width="17%" height="25" class="row-even"><div align="left">									
								             <nested:text styleId='<%=dateFrom%>'
								              property="dateFrom"  size="10" maxlength="10" />
										   <script language="JavaScript">
							                 new tcal( {
							             	// form name
							              	'formname' :'phdDocumentSubmissionForm',
								           // input name
								            'controlname' :'<%=dateFrom%>'
							                });
					                  	</script></div>
					                   </td>
										<%String dateTo="dateTo_"+String.valueOf(count);%>
										<td class="row-odd" width="17%" align="right"> 
											<bean:message key="knowledgepro.admin.mobNewsEventsDetailsDateTo"/> :
										</td>
										<td  width="17%" height="25" class="row-even"><div align="left">									
								             <nested:text styleId='<%=dateTo%>'
								              property="dateTo"  size="10" maxlength="10" />
										   <script language="JavaScript">
							                 new tcal( {
							             	// form name
							              	'formname' :'phdDocumentSubmissionForm',
								           // input name
								            'controlname' :'<%=dateTo%>'
							                });
					                  	</script></div>
					                   </td>
										<%String level="level_"+String.valueOf(count);%>
										<td class="row-odd" width="16%" align="right"> 
											<bean:message key="knowledgepro.employee.level"/> :
										</td>
										<td width="16%" height="25" align="left" class="row-even">
								         <span class="star">
					                     	<nested:select property="level" styleId="level" styleClass="combo" >
							                  <html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
							                  <html:option value="Institutional"></html:option>
							                  <html:option value="Regional"></html:option>
							                  <html:option value="National"></html:option>
							                   <html:option value="InterNational"></html:option>
						                       </nested:select></span>
										</td>
 									   </tr>
 									   <tr><% if(con>1) { %>
 									   <td height="20" class="row-even" colspan="5"></td>
 									   <td height="20" class="row-even" align="center">
										<img src="images/removeIcon.gif" width="81" height="22" style="cursor: pointer" onclick="deleteConference('<bean:write name="conference" property="nameProgram"/>','<bean:write name="conference" property="organizedBy"/>','<bean:write name="conference" property="id"/>')">
 									   </td><% } else { %>
 									    <td height="20" class="row-even" align="center" colspan="6"></td>
 									   <%} %>
 									   </tr>
			                     </nested:iterate>
			                         <tr>
			                         	<td  class="row-even" align="center" colspan="10">
			                           <html:submit property="" styleClass="formbutton" value="Add more"  onclick="addConference(); return false;"></html:submit>
										</td> 
									</tr>
					 				</logic:notEmpty>
									</table>
								</td>
								<td width="5" height="30"  background="images/right.gif"></td>
							</tr>
							<tr>
								<td height="5"><img src="images/04.gif" width="5" height="5" /></td>
								<td background="images/05.gif"></td>
								<td><img src="images/06.gif" /></td>
							</tr>
						</table>
						</td>
						</tr>
                    </table></td>
                <td width="10" valign="top" colspan="2" background="images/Tright_3_3.gif" class="news"></td>
             </tr>
               </logic:notEmpty>
               
               
               
               
               
    
            <logic:notEmpty name="phdDocumentSubmissionForm" property="studentDetails">
                  <tr>
					<td height="10" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						   <tr>
					<td  class="heading" align="left">
						<bean:message key="KnowledgePro.phd.panelmember.detatils"/>
					</td>
					</tr>
					</table>
					</td>
					<td width="10" valign="top" colspan="2" background="images/Tright_3_3.gif" class="news"></td>
				  </tr>	
      
                   <tr>
                   <td height="122" valign="top" background="images/Tright_03_03.gif"></td>
						<td valign="top" class="news" colspan="3">
						 <table width="50%" border="0" align="left" cellpadding="0"	cellspacing="0">
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
										    <td width="33%" height="25" class="row-even"  colspan="6">
										    <span	class="star"> Internal :
										    <html:radio property="panelGuideDisplay" name="phdDocumentSubmissionForm" value="true" styleId="fixed" onchange="setPanelInternalGuide(this.value)">Yes</html:radio>
					                        <html:radio property="panelGuideDisplay"  name="phdDocumentSubmissionForm" value="false" styleId="fixed" onchange="setPanelInternalGuide(this.value)">No</html:radio></span>&nbsp;&nbsp;&nbsp;&nbsp;
										    <bean:message key="knowledgepro.phd.PhdSynopsisDefense" /> :
										    </td>
				                            <td width="17%" height="25" class="white"  colspan="1"><span	class="star"> 
				                            <html:select property="synopsis" styleClass="comboLarge" styleId="synopsis"><html:option value=""><bean:message key="knowledgepro.admin.select" />
				                            </html:option><html:optionsCollection name="PanelGuideDetails" label="name" value="id" /></html:select></span>
				                            <html:submit property="" styleClass="formbutton" onclick="AddSynopisToList()">Add</html:submit></td>
								 	    </tr>
									<logic:present name="phdDocumentSubmissionForm" property="synopsisList">
									      <tr>
											<td width="15%" class="row-odd" align="center"><div align="center"><bean:message key="knowledgepro.admin.name" /></div></td>
											<td width="15%" class="row-odd" align="center"><div align="center"><bean:message key="knowledgepro.select"/></div></td>
											<td height="5%" class="row-odd" align="center"> <div align="center"><bean:message key="knowledgepro.delete" /></div></td>
										  </tr>
										  <tr>
											<nested:iterate id="synopsis1" property="synopsisList"
												name="phdDocumentSubmissionForm" indexId="count" type="com.kp.cms.to.phd.PhdStudentPanelMemberTO">
													<c:choose>
													<c:when test="${count%2 == 0}">
													<tr class="row-even">
													</c:when>
													<c:otherwise>
															<tr class="row-white">
														</c:otherwise>
					 								</c:choose>
											        <td><div align="left"><nested:write property="synopsisName" name="synopsis1" /></div></td>
								                	<td  align="center">
								 	              <input type="hidden" name="synopsisList[<c:out value='${count}'/>].tempChecked1"	id="hidden1_<c:out value='${count}'/>"
																value="<nested:write name='synopsis1' property='tempChecked1'/>" />
														<input type="checkbox" name="synopsisList[<c:out value='${count}'/>].checked1" id="hiden1_<c:out value='${count}'/>" />
																<script type="text/javascript">
																var studentId = document.getElementById("hidden1_<c:out value='${count}'/>").value;
																if(studentId == "on") {
																document.getElementById("hiden1_<c:out value='${count}'/>").checked = true;
																		}		
														</script>
													</td>
												<td  align="center"><div align="center">
													<img src="images/delete_icon.gif" width="16" height="16" style="cursor: pointer" onclick="deletesynopsisList('<bean:write name="synopsis1" property="id"/>')">
											      </div>
											      </td>
										</nested:iterate>
										</tr> 
								</logic:present>
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
						
						<table width="50%" border="0" align="left" cellpadding="0" cellspacing="0">
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
										   <td width="30%" height="25" class="row-even" colspan="4">
										    <span	class="star"> Internal :
										    <html:radio property="finalGuideDisplay" name="phdDocumentSubmissionForm" value="true" styleId="fixed" onchange="setFinalInternalGuide(this.value)">Yes</html:radio>
					                        <html:radio property="finalGuideDisplay"  name="phdDocumentSubmissionForm" value="false" styleId="fixed" onchange="setFinalInternalGuide(this.value)">No</html:radio></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										    <bean:message key="KnowledgePro.phd.panelmember.viva" /> :
										    </td>
				                            <td width="20%" height="25" class="white" colspan="1"><span	class="star"> 
				                            <html:select property="finalViva" styleClass="comboLarge" styleId="finalViva"><html:option value=""><bean:message key="knowledgepro.admin.select" />
				                             </html:option><html:optionsCollection name="VivaGuideDetails" label="name" value="id" /></html:select></span>
				                             <html:submit property="" styleClass="formbutton" onclick="AddFinalVivaList()">Add</html:submit></td>
									    </tr>
									    <logic:present name="phdDocumentSubmissionForm" property="finalVivaList">
									      <tr>
											<td width="15%" class="row-odd" align="center"><div align="center"><bean:message key="knowledgepro.admin.name" /></div></td>
											<td width="15%" class="row-odd" align="center"><div align="center"><bean:message key="knowledgepro.select"/></div></td>
											<td height="5%" class="row-odd" align="center"> <div align="center"><bean:message key="knowledgepro.delete" /></div></td>
										  </tr>
										  <tr>
											<nested:iterate id="finalViva1" property="finalVivaList"
												name="phdDocumentSubmissionForm" indexId="count" type="com.kp.cms.to.phd.PhdStudentPanelMemberTO">
													<c:choose>
													<c:when test="${count%2 == 0}">
													<tr class="row-even">
													</c:when>
													<c:otherwise>
															<tr class="row-white">
														</c:otherwise>
					 								</c:choose>
											       <td><div align="left"><nested:write property="vivaName" name="finalViva1" /></div></td>
								                	<td  align="center">
								 	                <input type="hidden" name="finalVivaList[<c:out value='${count}'/>].tempChecked2"	id="hidden2_<c:out value='${count}'/>"
																value="<nested:write name='finalViva1' property='tempChecked2'/>" />
														
														<input type="checkbox" name="finalVivaList[<c:out value='${count}'/>].checked2" id="hiden2_<c:out value='${count}'/>" />
																<script type="text/javascript">
																var studentId = document.getElementById("hidden2_<c:out value='${count}'/>").value;
																if(studentId == "on") {
																document.getElementById("hiden2_<c:out value='${count}'/>").checked = true;
																		}		
														</script>
													</td>
												<td  align="center"><div align="center">
													<img src="images/delete_icon.gif" width="16" height="16" style="cursor: pointer" onclick="deleteVivaList('<bean:write name="finalViva1" property="id"/>')">
											      </div>
											      </td>
										</nested:iterate>
										</tr> 
										</logic:present>
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
						<td width="10" valign="top" colspan="2" background="images/Tright_3_3.gif" class="news"></td>
					</tr>
                 </logic:notEmpty>
      
      
      
      
       <logic:notEmpty property="studentDetails" name="phdDocumentSubmissionForm">      
                  <tr>
					<td height="10" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
				   <tr>
					<td colspan="2" class="heading" align="left">
					<bean:message key="KnowledgePro.phd.complition.details"/>
					</td>
					</tr>
					</table>
					</td>
					<td width="10" valign="top" colspan="2" background="images/Tright_3_3.gif" class="news"></td>
				  </tr>	
                    <tr>
                    <td height="122" valign="top" background="images/Tright_03_03.gif"></td>
						<td valign="top" class="news" colspan="3">
						<table width="100%" border="0" align="center" cellpadding="0"
							cellspacing="0">
							<tr>
								<td><img src="images/01.gif" width="5" height="5" /></td>
								<td width="100%" background="images/02.gif"></td>
								<td><img src="images/03.gif" width="5" height="5" /></td>
							</tr>
							<tr>
								<td width="5" background="images/left.gif"></td>
								<td valign="top">
									<table width="100%" cellspacing="1" cellpadding="2">
								    <tr>
										<td class="row-odd" width="15%" height="25" align="left"> 
											<bean:message key="KnowledgePro.phd.Viva.voce" />
									  		</td>
										
										<td class="row-odd" width="65%" align="left"> 
											<bean:message key="KnowledgePro.phd.title"/>
										</td>
										<td class="row-odd" width="20%" align="left"> 
											<bean:message key="knowledgepro.phd.PhdCompletionDetails.discipline"/>
										</td>
									</tr>
									<tr>
									<td class="row-even"><html:text
									name="phdDocumentSubmissionForm" property="vivaDate"
									styleId="vivaDate" size="16" maxlength="16" /> <script
									language="JavaScript">
									new tcal( {
									// form name
									'formname' :'phdDocumentSubmissionForm',
									// input name
									'controlname' :'vivaDate'
									});
									</script></td>
									<td class="row-even" align="left"><nested:textarea property="vivaTitle" styleClass="Textarea" cols="80" styleId="vivaTitle" onkeypress="return imposeMaxLength(this,249);" onchange="len_display(this,249)"></nested:textarea></td>
						           <td class="row-even">
								 	 <html:select property="vivaDiscipline" styleClass="comboLarge" styleId="vivaDiscipline">
								      <html:option value=""><bean:message key="knowledgepro.select"/></html:option>
								   	<logic:notEmpty property="guideShipMap" name="phdDocumentSubmissionForm">
								   	<html:optionsCollection property="guideShipMap" label="value" value="key"/>
								    </logic:notEmpty>
							        </html:select> 
								    </td>
									
									</tr>
			                        </table>
								</td>
								<td width="5" height="100" background="images/right.gif"></td>
							</tr>
							<tr>
								<td height="5"><img src="images/04.gif" width="5" height="5" /></td>
								<td background="images/05.gif"></td>
								<td><img src="images/06.gif" /></td>
							</tr>
						</table>
						</td>
						<td width="10" height="50" valign="top" colspan="2" background="images/Tright_3_3.gif" class="news"></td>
					</tr>
           </logic:notEmpty>
     
       <logic:notEmpty property="studentDetails" name="phdDocumentSubmissionForm">   
                    <tr>
                        <td valign="top" background="images/Tright_03_03.gif"></td>
							<td height="50" align="center" colspan="6"> 
                		    <html:button property="" styleClass="formbutton" value="Submit" onclick="addPhdDocument()"></html:button>&nbsp;&nbsp;
							<html:button property="" styleClass="formbutton" value="Close" onclick="closePhdDocument()"></html:button>	
							</td>
						<td height="50" valign="top" colspan="8" background="images/Tright_3_3.gif" class="news"></td>
                    </tr>
      
          </logic:notEmpty>
      
				<tr>
					<td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
					<td colspan="2" background="images/TcenterD.gif"></td>

					<td><img src="images/Tright_02.gif" width="9" height="29"></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
     
</html:form>
 <script type="text/javascript">
			var focusField=document.getElementById("focusValue").value;
		    if(focusField !=null && focusField!=''){  
		            document.getElementById(focusField).focus();
			}
		</script>