<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@page import="java.util.Map,java.util.HashMap"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@page import="com.kp.cms.utilities.CommonUtil"%>
<%@page import="com.kp.cms.constants.CMSConstants"%>
<%@page import="java.util.Date"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<link rel="stylesheet" href="jquery/development-bundle/demos/demos.css">
<script src="jquery/development-bundle/jquery-1.7.1.js"></script>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<LINK REL=StyleSheet HREF= "css/styles.css" TYPE="text/css">
<script type="text/javascript">
	function setFocus(){
		var Focus=document.getElementById("focusValue").value;
		var txtBox=document.getElementById("Focus").value;
		document.all('txtBox').focus();
		return false;

	}

	function cancelAction() {
		document.location.href = "empResearchPublicDetails.do?method=initResearchPublicDetails";
	}
	function seeRejectedData(name,empId)	{
		var url  = "empResearchPublicDetails.do?method=initResearchPublicRejectedDetails";
		myRef = window.open(url,"Rejected Details","left=20,top=20,width=800,height=300,toolbar=1,resizable=0,scrollbars=1");
}

	function seeApprovedData(name,empId)	{
			var url  = "empResPubPendApproval.do?method=seeApprovedData&selectedCategory="+name + "&selectedEmpoloyeeId="+ empId;
			myRef = window.open(url,"Approved Details","left=20,top=20,width=800,height=300,toolbar=1,resizable=0,scrollbars=1");
	}
	function seeApprovalPendingData(name,empId)	{
		var url  = "empResPubPendApproval.do?method=seeApprovalPendingData&selectedCategory="+name + "&selectedEmpoloyeeId="+ empId;
		myRef = window.open(url,"Approval Pending Details","left=20,top=20,width=800,height=300,toolbar=1,resizable=0,scrollbars=1");
}
	
	function saveEmpResPubDetails(name){
		charCountDisplay();
		var x=window.confirm("Please Verify Entered Details Before Submitting, Press 'Ok' if u want to submit without verifying, else press Cancel ");
		if(x)
		{
		document.getElementById("method").value="saveEmpResPubDetails";
		document.getElementById("submitName").value=name;
		document.EmpResPubDetailsForm.submit();
		}
	}

	function getOtherConferencePreseantation(valueSelected,count){
		other=document.getElementById("typeOfPgm_"+ count).value;
		if(other!=null && other!="" && other=="Other"){
			document.getElementById("other_"+count).style.display="block";
		}else{
			document.getElementById("other_"+count).style.display="none";
		}
	}
	function getOtherArticlePerodicals(valueSelected, count){
		other=document.getElementById("type_"+ count).value;
		 if(other!=null && other!="" && other=="Other"){
				document.getElementById("otherTextArticle_" + count).style.display="block";
		}else{
				document.getElementById("otherTextArticle_" + count).style.display="none";
		}
	}
	
	
    function validate(nameVal) {
        if(nameVal!=null && nameVal!=""){
        var pattern=/^[a-zA-Z0-9\s\[\]\.\-#,@()`~%&*()-_+=>,.<|\{}:;&!?'"]*$/;
        if (pattern.test(nameVal)) {
            return true;
        } else {
        	 alert("Special characters allowed are ([].\-#,@()`~%&*()-_+=>,.<|\{}:;&!?'). Please check and remove all other invalid special characters and Try Again");
        }
        }
    }


    function validateDate(Name)
    { 	
    		 var str=Date.parse(Name);
    		alert(str);

    }
    
	function submitEmployeeInfoAdd(method,mode){
		document.getElementById("method").value=method;
		document.getElementById("mode").value=mode;
		document.EmpResPubDetailsForm.submit();
	}
        $(document).ready(function(){
            $("#report1 .tdIMG").addClass("odd");
            $("#report1 .data").hide();
            $("#report1 tr:first-child").show();
            
            $("#report1 tr.odd").click(function(){
                $(this).next("tr").toggle();
                $(this).find(".arrow").toggleClass("up");
            });

            var focusField=document.getElementById("focusValue").value;
	    	 if(focusField != null){  
	 		 if(document.getElementById(focusField)!=null)   
	    	if(focusField == "OwnPhdMPilThesis")
	        { 
           $("#OwnPhdMPilThesis").toggle();
	        }
	    	else if(focusField == "PhdMPhilThesisGuided")
	        { 
	            $("#PhdMPhilThesisGuided").toggle();
	 	        }
	    	else if(focusField == "SeminarsOrganised")
	        { 
	            $("#SeminarsOrganised").toggle();
	 	        }
	    	else if(focusField == "CasesNotesWorking")
	        { 
	            $("#CasesNotesWorking").toggle();
	 	        }
	    	else if(focusField == "ConfPresent")
	        { 
	            $("#ConfPresent").toggle();
	 	        }
	    	else if(focusField == "InvitedTalk")
	        { 
	            $("#InvitedTalk").toggle();
	 	        }
	    	else if(focusField == "ArticlInPeriodicals")
	        { 
	            $("#ArticlInPeriodicals").toggle();
	 	        }
	    	else if(focusField == "FilmVideosDoc")
	        { 
	            $("#FilmVideosDoc").toggle();
	 	        }
	    	else if(focusField == "Blog")
	        { 
	            $("#Blog").toggle();
	 	        }
	    	else if(focusField == "ChapArticlBook")
	        { 
	            $("#ChapArticlBook").toggle();
	 	        }
	    	else if(focusField == "ArticleJournals")
	        { 
	            $("#ArticleJournals").toggle();
	 	        }
	    	else if(focusField == "BookMonographs")
	        { 
	            $("#BookMonographs").toggle();
	 	        }
	    	else if(focusField == "Research")
	        { 
	            $("#Research").toggle();
	 	        }
	    	  
	    	 else if(focusField == "SeminarsAttended")
		        { 
		            $("#SeminarsAttended").toggle();
		 	        }
	    	 else if(focusField == "WorkshopsAttended")
		        { 
		            $("#WorkshopsAttended").toggle();
		 	        }
	    	 else if(focusField == "AwardsAchievements")
		        { 
		            $("#AwardsAchievements").toggle();
		 	        }
	 		
		    	 }  
        });

        
        function maxlength(field, size) {
            if (field.value.length > size) {
                field.value = field.value.substring(0, size);
            }
        }
        function imposeMaxLength(field, size, length) {
            var fieldSize = field.value.length;
            if (fieldSize > size) {
                field.value = field.value.substring(length, size);
            }
        }
        function len_display(Object,MaxLen,element,vvId){
            if(MaxLen>=Object.value.length){
	        	document.getElementById(vvId).style.display="block";
	        
	            var len_remain = MaxLen-Object.value.length;
	            if(len_remain < MaxLen){
	            	document.getElementById(element).value=len_remain;
             	}
            }
        }

        function charCountDisplay(){
            if(document.getElementById("charCountResearchProject")!=null)
        	document.getElementById("charCountResearchProject").style.display="none";
        	if(document.getElementById("charCountBooksMonographs")!=null)
        	document.getElementById("charCountBooksMonographs").style.display="none";
        	if(document.getElementById("charCountArticleJournals")!=null)
        	document.getElementById("charCountArticleJournals").style.display="none";
        	if(document.getElementById("charCountChapterArticlBook")!=null)
        	document.getElementById("charCountChapterArticlBook").style.display="none";
        	if(document.getElementById("charCountBlog")!=null)
        	document.getElementById("charCountBlog").style.display="none";
        	if(document.getElementById("charCountFilmVideosDoc")!=null)
        	document.getElementById("charCountFilmVideosDoc").style.display="none";
        	if(document.getElementById("charCountConferencePresentation")!=null)
        	document.getElementById("charCountConferencePresentation").style.display="none";
        	if(document.getElementById("charCountInvitedTalk")!=null)
        	document.getElementById("charCountInvitedTalk").style.display="none";
        	if(document.getElementById("charCountCasesNotesWorking")!=null)
        	document.getElementById("charCountCasesNotesWorking").style.display="none";
        	if(document.getElementById("charCountSeminarsOrganized")!=null)
        	document.getElementById("charCountSeminarsOrganized").style.display="none";
        	if(document.getElementById("charCountPhdMPhilThesisGuided")!=null)
        	document.getElementById("charCountPhdMPhilThesisGuided").style.display="none";
        	if(document.getElementById("charCountOwnPhdMPilThesis")!=null)
        	document.getElementById("charCountOwnPhdMPilThesis").style.display="none";
        	if(document.getElementById("charCountAwards")!=null)
            	document.getElementById("charCountAwards").style.display="none";
        }
        </script>

<html:form action="/empResearchPublicDetails" method="post">
	<html:hidden property="formName" value="EmpResPubDetailsForm" />
	<html:hidden property="pageType" value="1" />
	<html:hidden property="method" value="" styleId="method" />
	<html:hidden property="mode" styleId="mode" value="" />
	<html:hidden property="submitName" styleId="submitName" value="" />
	<html:hidden property="selectedCategory" styleId="selectedCategory" value="" />
	<html:hidden property="focusValue" styleId="focusValue"/>
	<html:hidden property="verifyFlag" styleId="verifyFlag" />
	<table width="100%" border="0">
		<tr>
			<td><span class="Bredcrumbs">Research and Publications <span
				class="Bredcrumbs">&gt;&gt; Research and Publications
			&gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="10"><img src="images/Tright_03_01.gif"></td>
					<td width="100%" background="images/Tcenter.gif" class="body">
					<div align="left"><strong class="boxheader">Research
					and Publications</strong></div>
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
							<td colspan="6" align="left">
							<div align="right"><span class='MandatoryMark'><bean:message
								key="knowledgepro.mandatoryfields" /></span></div>
							<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
							<FONT color="red" size="1px"><bean:write name="EmpResPubDetailsForm" property="errorMessage"/></FONT>
							<FONT color="red" size="1px"><bean:write name="EmpResPubDetailsForm" property="errMsg"/></FONT>
							<FONT color="green"><html:messages id="msg"
								property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
								<br>
							</html:messages></FONT></div>
							</td>
						</tr>

						<tr>
							<td colspan="6">
							<table width="100%" cellspacing="1" cellpadding="2" id="report1">
							<tr class="studentrow-odd">
									<td colspan="2">
									<div align="center" style="border: none; font-size: 13px;"><b> Research And Publication</b></div>
									</td>
								 
								</tr>
								
								<tr class="heading">
									<td colspan="2">
									<div align="left" style="border:ridge 5px #d0e4fe; font-size: 11px;">Dear Faculty Member, Kindly fill the relevant details from the list given below.<br>
										1. If you have more than 1 item under each category, use 'Add more' and enter the details.<br>
										2. Click on the Category to get the details to be entered.<br>
										3. Click on the 'Arrow Button' near Category Name to close or collapse the category details<br>
										4. Click on 'Send for approval' from each section after the form is filled.</div>
									</td>
								</tr>
							<tr height="40" >
							<td align="center" ><input type="button" class="formbutton" value="View Approved Details" id="approvedData" onclick="seeApprovedData(true,<bean:write name="EmpResPubDetailsForm" property="employeeId"/>)"/>
							&nbsp;&nbsp;&nbsp;<input type="button" class="formbutton" value="Pending Approval List" id="approvalPendData" onclick="seeApprovalPendingData(true,<bean:write name="EmpResPubDetailsForm" property="employeeId"/>)"/>
							&nbsp;&nbsp;&nbsp;<input type="button" class="formbutton" value="View Rejected Details" id="rejectedData" onclick="seeRejectedData(<bean:write name="EmpResPubDetailsForm" property="employeeId"/>)"/>
							</td>
							</tr>
												
          <tr class="tdIMG" height="35px">
									<td>
									<div align="left" style="border: none; font-size: 13px; color: #006600"><b> <bean:message
										key="employee.research.project" /></b></div>
									</td>
								 <td align="right"> <div class="arrow"></div></td>
								</tr>
								<tr class="data" id="Research">
									<td>
									<table width="100%" cellspacing="1" cellpadding="2">
										<tr>
										<td colspan="4" align="right"><div align="right" id="charCountResearchProject"><input type="text" id="long_lenResearchProject" class="len" size="2" readonly="readonly" style="border: none; text-align: right; font-size: 11px; font-family: Verdana, Arial, Helvetica, sans-serif"> Characters Remaining</div></td>
										</tr>
										<tr class="row-odd">
											
											<logic:notEmpty property="empResearchProjectTO"
												name="EmpResPubDetailsForm">
												<nested:iterate property="empResearchProjectTO"
													name="EmpResPubDetailsForm" id="empResearchProject"
													indexId="count">
													<tr>
													<td><div style="border: none; font-size: 10px; color: #000000"><b>Sl No:- <c:out value="${count + 1}"/></b></div></td>
													<td><div style="border: none; font-size: 10px; color: #000000"><b>Submitted Date:- 
													<bean:write name="empResearchProject" property="entryCreatedate" /></b></div></td>
												
												<logic:equal value="true" name="empResearchProject" property="isRejected">
													<td><FONT color="red"><b>Rejected</b></FONT></td> 
													<td><div style="border: none; font-size: 10px; color: red"><b>Rejected Date:- 
													<bean:write name="empResearchProject" property="rejectDate" /></b></div></td>
													</logic:equal>
													</tr>
								
								   
								   <logic:equal value="false" name="empResearchProject" property="isResearchProject">
													
													<tr>
						
														<%
															String investigators = "investigators_" + count;
														%>
														<td width="20%" height="25" class="row-odd">
														<div align="left"><bean:message
															key="knowledgepro.employee.title" /></div>
														</td>
														<td height="20" class="row-even" colspan="3">
														
														<nested:textarea property="title" styleId="Restitle" cols="90" rows="1" 
															onkeypress="return imposeMaxLength(this, 0, 999);" 
															onchange="return imposeMaxLength(this, 0, 999);" 
															onkeyup="len_display(this,1000,'long_lenResearchProject','charCountResearchProject')" 
															onclick="len_display(this,1000,'long_lenResearchProject','charCountResearchProject')"
															></nested:textarea>
															</td>
												</tr>
												<tr>
														<td width="20%" class="row-odd">
														<div align="left"><bean:message
															key="knowledgepro.employee.abstract.objectives" /></div>
														</td>
														<td  height="20" class="row-even" colspan="3">
														<nested:textarea property="abstractObjectives" styleId="abstractObjectives" cols="90" rows="1" 
														onkeypress="return imposeMaxLength(this, 0, 1999);" onchange="return imposeMaxLength(this, 0, 1999);" 
														onkeyup="len_display(this,2000,'long_lenResearchProject','charCountResearchProject');" 
														onclick="len_display(this,2000,'long_lenResearchProject','charCountResearchProject');" 
														></nested:textarea>
															</td>
													</tr>
													<tr>
														<td width="20%" height="25" class="row-odd">
														<div align="left"><bean:message
															key="knowledgepro.employee.investigarors" /></div>
														</td>
														<td width="30%" class="row-even"><nested:text
															property="investigators" styleId="<%=investigators%>"
															size="40" maxlength="100" onkeypress="charCountDisplay()" ></nested:text></td>


														<td width="20%" class="row-odd">
														<div align="left"><bean:message
															key="knowledgepro.employee.sponsors" /></div>
														</td>
														<td width="30%"  height="25" class="row-even"><nested:text
															property="sponsors" size="40" maxlength="100" ></nested:text></td>

													</tr>
													<tr>
														<td width="20%" class="row-odd">
														<div align="left"><span class="Mandatory">*</span><bean:message
															key="knowledgepro.fee.academicyear" /></div>
														</td>
													
														<%String dynaYearId = "YOP" + count; %>
														
														<td width="16%" class="row-even">
														<input type="hidden" id="Year<c:out value='${count}'/>" value="<nested:write property='academicYear'/>"/>
														<nested:select property="academicYear" styleClass="comboLarge" styleId='<%=dynaYearId %>'>
															<option value=""><bean:message
																key="knowledgepro.admin.select" /></option>
															<cms:renderEmpResearchYear></cms:renderEmpResearchYear>
														</nested:select> 
														<script type="text/javascript">
																var yopid= document.getElementById("Year<c:out value='${count}'/>").value;
																document.getElementById("YOP<c:out value='${count}'/>").value = yopid;
															</script></td>
														
									<td width="20%" class="row-odd">
														<div align="left"><span class="Mandatory">*</span>Internal/External</div>
														</td>
														<td width="30%" height="25" class="row-even">
                								 			<nested:select property="internalExternal" styleId="internalExternal" styleClass="comboLarge">
  	   				 										<html:option value="">- Select -</html:option>
  	   														<html:option value="Internal">Internal</html:option>
								   							<html:option value="External">External</html:option>
   			   												</nested:select>
														</td>
													</tr>
													<tr>
													<td width="20%" class="row-odd">
														<div align="left"><span class="Mandatory">*</span>Amount Granted</div>
														</td>
														<td width="30%"  height="25" class="row-even"><nested:text
															property="amountGranted" size="40" maxlength="100" onkeypress="return isNumberKey(event)"></nested:text></td>
													</tr>
													</logic:equal>
									
												</nested:iterate>
											</logic:notEmpty>
										</tr>
										<tr>
										<td class="row-even" colspan="4"><div>
										<table width="100%" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td height="25" align="center">
											<logic:notEmpty property="empResearchProjectTO"
												name="EmpResPubDetailsForm">
								  			 <html:button property="" styleClass="formbutton" value="Send For Approval" onclick="saveEmpResPubDetails('ResearchProject')"></html:button>&nbsp;&nbsp;
											</logic:notEmpty>
											&nbsp;<html:submit
												property="" styleClass="formbutton" value="Add more"
												styleId="addMore"
												onclick="submitEmployeeInfoAdd('resetResearchProject','ExpAddMore'); return false;"></html:submit>
											<c:if
												test="${EmpResPubDetailsForm.empResearchProjectLength>0}">
												<html:submit property="" styleClass="formbutton"
													value="Remove" styleId="addMore"
													onclick="submitEmployeeInfoAdd('removeResearchProject','ExpAddMore'); return false;"></html:submit>
											</c:if></td>
										</tr>
										</table>
									</div></td>
											
											
											
										</tr>
										
										

									</table>

									</td>
								</tr>
<tr class="tdIMG" height="35px">
									<td >
									<div align="left" style="border: none; font-size: 13px; color: #006600"><b> <bean:message
										key="employee.book.monographs" /></b></div>
									</td>
									 <td align="right"> <div class="arrow"></div></td>
								</tr>
								<tr class="data" id="BookMonographs">
									<td>
									<table width="100%" cellspacing="1" cellpadding="1">
									<tr>
										<td colspan="4" align="right"><div align="right" id="charCountBooksMonographs"><input type="text" id="long_lenBooksMonographs" class="len" size="2" readonly="readonly" style="border: none; text-align: right; font-size: 11px; font-family: Verdana, Arial, Helvetica, sans-serif"> Characters Remaining</div></td>
										</tr>
										<tr class="row-odd">
											<logic:notEmpty property="empBooksMonographsTO"
												name="EmpResPubDetailsForm">
												<nested:iterate property="empBooksMonographsTO"
													name="EmpResPubDetailsForm" id="empBooksMonographs"
													indexId="count">
													<tr>
													<td><div style="border: none; font-size: 10px; color: #000000"><b>Sl No:- <c:out value="${count + 1}"/></b></div></td>
													<td><div style="border: none; font-size: 10px; color: #000000"><b>Submitted Date:- 
													<bean:write name="empBooksMonographs" property="entryCreatedate" /></b></div></td>
													
													<logic:equal value="true" name="empBooksMonographs" property="isRejected">
													<td><FONT color="red"><b>Rejected</b></FONT></td> 
													<td><div style="border: none; font-size: 10px; color: red"><b>Rejected Date:- 
													<bean:write name="empBooksMonographs" property="rejectDate" /></b></div></td>
													</logic:equal>
													
													</tr>
						
										
										<logic:equal value="false" name="empBooksMonographs" property="isBookMonographs">
												
													<tr>


														<td width="20%" height="25" class="row-odd">
														<div align="left"><bean:message
															key="knowledgepro.employee.title.of.Book" /></div>
														</td>
														<td width="30%" class="row-even"><nested:textarea
															property="title" cols="40" rows="2" 
														    onkeypress="return imposeMaxLength(this, 0, 999);"  onchange="return imposeMaxLength(this, 0, 999);"  
															onkeyup="len_display(this,1000,'long_lenBooksMonographs','charCountBooksMonographs')" ></nested:textarea></td>

														<td width="20%"height="25" class="row-odd">
														<div align="left"><bean:message
															key="knowledgepro.employee.author.name" /></div>
														</td>
														<%
															String authorName = "authorName_" + count;
														%>
														<td width="30%" class="row-even"><nested:text
															property="authorName" styleId="<%=authorName%>" size="40" 
															maxlength="100"  onkeypress="charCountDisplay()"></nested:text></td>

													</tr>
													<tr>

														<td width="20%" height="25" class="row-odd">
														<div align="left"><bean:message
															key="employee.info.language.title" /></div>
														</td>

														<td width="30%" class="row-even"><nested:text property="language"
															styleId="language" size="40" maxlength="50" ></nested:text></td>

														<td width="20%" class="row-odd">
														<div align="left"><bean:message
															key="knowledgepro.employee.total.pages" /></div>
														</td>
														<td width="30%" height="25" class="row-even"><nested:text
															property="totalPages" size="40" maxlength="10" onkeypress="return isNumberKey(event)" ></nested:text></td>

													</tr>
													<tr>
														<td width="20%" class="row-odd">
														<div align="left"><bean:message
															key="knowledgepro.employee.isbn" /></div>
														</td>
														<td width="30%" height="25" class="row-even"><nested:text
															property="isbn" size="40" maxlength="50" ></nested:text></td>

														<td width="20%" class="row-odd">
														<div align="left"><bean:message
															key="knowledgepro.employee.publishing.company" /></div>
														</td>
														<td width="30%" height="25" class="row-even"><nested:text
															property="companyInstitution" size="40" maxlength="250" ></nested:text></td>

													</tr>
													<tr>

														<td width="20%" class="row-odd">
														<div align="left"><bean:message
															key="knowledgepro.employee.place.publishing" /></div>
														</td>
														<td width="30%" height="25" class="row-even"><nested:text
															property="placePublication" size="40" maxlength="100" ></nested:text></td>

														<td width="20%" class="row-odd">
														<div align="left"><bean:message
															key="knowledgepro.employee.monthYear" /></div>
														</td>
														<td width="30%" height="25" class="row-even"><nested:text
															property="monthYear" size="40" maxlength="20" ></nested:text></td>

													</tr>
														<tr>

														<td width="20%" class="row-odd">
														<div align="left"><span class="Mandatory">*</span><bean:message
															key="knowledgepro.fee.academicyear" /></div>
														</td>
														<%String bmYearId = "YOP1" + count; %>
														
														<td width="16%" class="row-even"><c:set var="dyopid1"><%=bmYearId%></c:set>
														<nested:select property="academicYear" styleClass="comboLarge" styleId='<%=bmYearId %>'>
															<option value=""><bean:message
																key="knowledgepro.admin.select" /></option>
															<cms:renderEmpResearchYear></cms:renderEmpResearchYear>
														</nested:select> <script type="text/javascript">
																var yopid= '<nested:write property="academicYear"/>';
																document.getElementById("<c:out value='${dyopid1}'/>").value = yopid;
															</script></td>
														
														
														
														<!-- <td width="30%" height="25" class="row-even">
                								 		<nested:select property="academicYear" styleId="academicYear" styleClass="comboLarge">
  	   														<cms:renderAcademicYear></cms:renderAcademicYear>
   			   											</nested:select>
														</td>-->
														<td width="20%" class="row-odd">
														<div align="left"><span class="Mandatory">*</span>Sole Author/Co-Author</div>
														</td>
														<td width="30%" height="25" class="row-even">
                								 		<nested:select property="coAuthored" styleId="coAuthored" styleClass="comboLarge">
  	   				 										<html:option value="">- Select -</html:option>
  	   														<html:option value="Sole">Sole</html:option>
								   							<html:option value="Co-Author">Co-Author</html:option>
   			   											</nested:select>
														</td>
													</tr>
													</logic:equal>
												</nested:iterate>
											</logic:notEmpty>
										</tr>
								<tr>
										
										<td class="row-even" colspan="4"><div>
										<table width="100%" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td height="25" align="center">
											<logic:notEmpty property="empBooksMonographsTO"
												name="EmpResPubDetailsForm">
								  			 <html:button property="" styleClass="formbutton" value="Send For Approval" onclick="saveEmpResPubDetails('BooksMonographs')"></html:button>&nbsp;&nbsp;
											</logic:notEmpty>
											&nbsp;<html:submit
												property="" styleClass="formbutton" value="Add more"
												styleId="addMore"
												onclick="submitEmployeeInfoAdd('resetBookMonographs','ExpAddMore'); return false;"></html:submit>
											<c:if
												test="${EmpResPubDetailsForm.empBooksMonographsLength>0}">
												<html:submit property="" styleClass="formbutton"
													value="Remove" styleId="addMore"
													onclick="submitEmployeeInfoAdd('removeBookMonographs','ExpAddMore'); return false;"></html:submit>
											</c:if></td>
										</tr>
										</table>
									</div></td>
											
											
											
										</tr>
										
										

									</table>

									</td>
								</tr>

<tr class="tdIMG" height="35px">
									<td>
									<div align="left" style="border: none; font-size: 13px; color: #006600"><b> <bean:message
										key="employee.article.in.journal" /></b></div>
									</td>
								 <td align="right"> <div class="arrow"></div></td>
								</tr>
								<tr class="data" id="ArticleJournals">
									<td>
									<table width="100%" cellspacing="1" cellpadding="2">
										<tr>
										<td colspan="4" align="right"><div align="right" id="charCountArticleJournals"><input type="text" id="long_lenArticleJournals" class="len" size="2" readonly="readonly" style="border: none; text-align: right; font-size: 11px; font-family: Verdana, Arial, Helvetica, sans-serif"> Characters Remaining</div></td>
										</tr>
										<tr class="row-odd">
											<logic:notEmpty property="empArticleJournalsTO"
												name="EmpResPubDetailsForm">
												<nested:iterate property="empArticleJournalsTO"
													name="EmpResPubDetailsForm" id="empArticleJournals"
													indexId="count">
													<tr>
													<td><div style="border: none; font-size: 10px; color: #000000"><b>Sl No:- <c:out value="${count + 1}"/></b></div></td>
													<td><div style="border: none; font-size: 10px; color: #000000"><b>Submitted Date:- 
													<bean:write name="empArticleJournals" property="entryCreatedate" /></b></div></td>
												<logic:equal value="true" name="empArticleJournals" property="isRejected">
													<td><FONT color="red"><b>Rejected</b></FONT></td> 
													<td><div style="border: none; font-size: 10px; color: red"><b>Rejected Date:- 
													<bean:write name="empArticleJournals" property="rejectDate" /></b></div></td>
													</logic:equal>
													</tr>
						<logic:equal value="false" name="empArticleJournals" property="isArticleJournal">
												
													<tr>


														<td width="15%" height="25" class="row-odd">
														<div align="left"><bean:message
															key="knowledgepro.employee.title.of.article" /></div>
														</td>
														<td class="row-even" width="20%"><nested:textarea
															property="title" cols="40" rows="1"
															onclick="len_display(this,1000,'long_lenArticleJournals','charCountArticleJournals')"
															onkeypress="return imposeMaxLength(this, 0, 999);" 
															onchange="return imposeMaxLength(this, 0, 999);" 
															onkeyup="len_display(this,1000,'long_lenArticleJournals','charCountArticleJournals')" 
															></nested:textarea></td>
														<td width="15%" height="25" class="row-odd">
														<div align="left"><bean:message
															key="knowledgepro.employee.article.department.name" /></div>
														</td>
														<td class="row-even" width="20%">
															<nested:text property="departmentInstitution" styleId="departmentInstitution" size="40"
															maxlength="100" ></nested:text>
														</td>
												</tr>
												
											<tr>
														<td width="15%" height="25" class="row-odd">
														<div align="left"><bean:message
															key="knowledgepro.employee.author.name" /></div>
														</td>
														<td width="20%" class="row-even"><nested:text
															property="authorName" styleId="authorName" size="40"
															maxlength="100" onkeypress="charCountDisplay()" ></nested:text></td>
												
														<td width="15%" class="row-odd">
														<div align="left"><bean:message
															key="knowledgepro.employee.name.of.journal" /></div>
														</td>
														<td width="20%" height="25" class="row-even"><nested:text
															property="nameJournal" size="40" maxlength="100" ></nested:text></td>
												</tr>
												<tr>
														<td width="20%" height="25" class="row-odd">
														<div align="left"><bean:message
															key="knowledgepro.employee.impact.factor" /></div>
														</td>
														<td width="30%" class="row-even"><nested:text
															property="impactFactor" styleId="impactFactor" size="40"
															maxlength="100" ></nested:text></td>
												
														<td width="20%" class="row-odd">
														<div align="left"><bean:message
															key="knowledgepro.employee.url" /></div>
														</td>
														<td width="30%" height="25" class="row-even"><nested:text
															property="url" size="40" maxlength="200" ></nested:text></td>
                                             </tr>
                                             <tr>
														<td width="20%" height="25" class="row-odd">
														<div align="left"><bean:message
															key="knowledgepro.employee.publisher.name" /></div>
														</td>
														<td width="30%" class="row-even"><nested:text
															property="companyInstitution" styleId="companyInstitution" size="40"
															maxlength="250" ></nested:text></td>
												
														<td width="20%" class="row-odd">
														<div align="left"><bean:message
															key="knowledgepro.employee.publisherAddress" /></div>
														</td>
														<td width="30%" height="25" class="row-even"><nested:text
															property="placePublication" size="40" maxlength="100" ></nested:text></td>
                                             </tr>
												<tr>
														<td width="15%" height="25" class="row-odd">
														<div align="left"><bean:message
															key="employee.info.language.title" /></div>
														</td>
														<%
															String language = "language_" + count;
														%>
														<td width="20%" class="row-even"><nested:text property="language"
															styleId="<%=language%>" size="40" maxlength="50" ></nested:text></td>
															
															<td width="20%" class="row-odd">
														<div align="left">
														<bean:message key="knowledgepro.employee.date.monthYear.publication" /> </div>
											</td>
											<% String styleDatePublished = "datePublished_" + count; %>
																		
																
												<td  class="row-even"><div align="left">
				                  					<nested:text  property="datePublished" styleId="<%=styleDatePublished %>" size="20" maxlength="20" ></nested:text>
												</div>
												</td>
												</tr>		
													
														
													<tr>

														<td width="20%" class="row-odd">
														<div align="left"><bean:message
															key="knowledgepro.employee.volume.Number" /></div>
														</td>
														<td width="30%" height="25" class="row-even"><nested:text
															property="volumeNumber" size="40" maxlength="20"></nested:text></td>
													

														<td width="20%" class="row-odd">
														<div align="left"><bean:message
															key="knowledgepro.employee.issueNumber" /></div>
														</td>
														<td width="30%" height="25" class="row-even"><nested:text
															property="issueNumber" size="40" maxlength="20"></nested:text></td>
													</tr>
													<tr>
														<td width="20%" class="row-odd">
														<div align="left"><bean:message
															key="knowledgepro.employee.issn" /></div>
														</td>
														<td width="30%" height="25" class="row-even"><nested:text
															property="isbn" size="40" maxlength="50" ></nested:text></td>
														
														<td width="20%" class="row-odd">
														<div align="left"><bean:message
															key="knowledgepro.employee.doi" /></div>
														</td>
														<td width="30%" height="25" class="row-even"><nested:text
															property="doi" size="40" maxlength="100" ></nested:text></td>
														
									
													</tr>
													<tr>
														<td width="20%" class="row-odd">
														<div align="left"><bean:message
															key="knowledgepro.employee.pages.from" /></div>
														</td>
														<td width="30%" height="25" class="row-even"><nested:text
															property="pagesFrom" size="40" maxlength="10" onkeypress="return isNumberKey(event)"></nested:text> 
														</td>

														 <td width="20%" class="row-odd">
														<div align="left"><bean:message
															key="knowledgepro.employee.pages.to" /></div>
														</td>
														<td width="30%" height="25" class="row-even"><nested:text
															property="pagesTo" size="40" maxlength="10" onkeypress="return isNumberKey(event)"></nested:text></td>
												
														
													</tr>
													<tr>

														<td width="20%" class="row-odd">
														<div align="left"><span class="Mandatory">*</span><bean:message
															key="knowledgepro.fee.academicyear" /></div>
														</td>
														<%String ajYearId = "YOP2" + count; %>
														
														<td width="16%" class="row-even"><c:set var="dyopid1"><%=ajYearId%></c:set>
														<nested:select property="academicYear" styleClass="comboLarge" styleId='<%=ajYearId %>'>
															<option value=""><bean:message
																key="knowledgepro.admin.select" /></option>
															<cms:renderEmpResearchYear></cms:renderEmpResearchYear>
														</nested:select> <script type="text/javascript">
																var yopid= '<nested:write property="academicYear"/>';
																document.getElementById("<c:out value='${dyopid1}'/>").value = yopid;
															</script>
														</td>
														
														<td width="20%" class="row-odd">
														<div align="left"><span class="Mandatory">*</span><bean:message
															key="knowledgepro.employee.type" /></div>
														</td>
														<td width="30%" height="25" class="row-even">
														
														<input type="hidden" id="tempType" name="tempType" value="<bean:write name="empArticleJournals" property="type"/>"/>
                								 			<nested:select property="type" styleId="type" styleClass="comboLarge">
  	   				 										<html:option value="">- Select -</html:option>
  	   														<html:option value="National Non-Refereed">National Non-Refereed </html:option>
								   							<html:option value="Internaltional Non-Refereed">Internaltional Non-Refereed</html:option>
															<html:option value="National Refereed">National Refereed </html:option>
								   							<html:option value="Internaltional Refereed">Internaltional Refereed</html:option>
   			   												</nested:select>

														
														
														<!--<nested:text
															property="type" size="40" maxlength="10" onkeypress="return isNumberKey(event)"></nested:text>-->
															</td>
													</tr>
													
													</logic:equal>

												</nested:iterate>
											</logic:notEmpty>
										</tr>
										<tr>
										<td class="row-even" colspan="4"><div>
										<table width="100%" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td height="25" align="center">
											<logic:notEmpty property="empArticleJournalsTO"
												name="EmpResPubDetailsForm">
												
											
								  			 <html:button property="" styleClass="formbutton" value="Send For Approval" onclick="saveEmpResPubDetails('ArticleJournals')"></html:button>&nbsp;&nbsp;			
											</logic:notEmpty>
											&nbsp;<html:submit
												property="" styleClass="formbutton" value="Add more"
												styleId="addMore"
												onclick="submitEmployeeInfoAdd('resetArticleJournals','ExpAddMore'); return false;"></html:submit>
											<c:if
												test="${EmpResPubDetailsForm.empArticleJournalsLength>0}">
												<html:submit property="" styleClass="formbutton"
													value="Remove" styleId="addMore"
													onclick="submitEmployeeInfoAdd('removeArticleJournals','ExpAddMore'); return false;"></html:submit>
											</c:if></td>
										</tr>
										</table>
									</div></td>
											
											
											
										</tr>
										
										

									</table>

									</td>
								</tr>

					<tr class="tdIMG" height="35px">
									<td>
									<div align="left" style="border: none; font-size: 13px; color: #006600"><b> <bean:message
										key="employee.chapter.article.in.books" /></b></div>
									</td>
								 <td align="right"> <div class="arrow"></div></td>
								</tr>

								<tr class="data" id="ChapArticlBook">
									<td>
									<table width="100%" cellspacing="1" cellpadding="2">
										<tr>
										<td colspan="4" align="right"><div align="right" id="charCountChapterArticlBook"><input type="text" id="long_lenChapterArticlBook" class="len" size="2" readonly="readonly" style="border: none; text-align: right; font-size: 11px; font-family: Verdana, Arial, Helvetica, sans-serif"> Characters Remaining</div></td>
										</tr>
										<tr class="row-odd">
											<logic:notEmpty property="empChapterArticlBookTO"
												name="EmpResPubDetailsForm">
												<nested:iterate property="empChapterArticlBookTO"
													name="EmpResPubDetailsForm" id="empChapterArticlBook"
													indexId="count">
													<tr>
													<td><div style="border: none; font-size: 10px; color: #000000"><b>Sl No:- <c:out value="${count + 1}"/></b></div></td>
													<td><div style="border: none; font-size: 10px; color: #000000"><b>Submitted Date:- 
													<bean:write name="empChapterArticlBook" property="entryCreatedate" /></b></div></td>
													
													<logic:equal value="true" name="empChapterArticlBook" property="isRejected">
													<td><FONT color="red"><b>Rejected</b></FONT></td> 
													<td><div style="border: none; font-size: 10px; color: red"><b>Rejected Date:- 
													<bean:write name="empChapterArticlBook" property="rejectDate" /></b></div></td>
													</logic:equal>
													</tr>
                     <logic:equal value="false" name="empChapterArticlBook" property="isChapterArticleBook">
                     							
													<tr>
														<td width="20%" height="25" class="row-odd">
														<div align="left"><bean:message
															key="knowledgepro.employee.title.of.Book" /></div>
														</td>
														<td width="30%" class="row-even"><nested:textarea
															property="title" cols="40" rows="2" 
															onclick="len_display(this,1000,'long_lenChapterArticlBook','charCountChapterArticlBook')"
															onkeypress="return imposeMaxLength(this, 0, 999);" onchange="return imposeMaxLength(this, 0, 999);" 
															onkeyup="len_display(this,1000,'long_lenChapterArticlBook','charCountChapterArticlBook')" ></nested:textarea></td>

														<td width="20%" height="25" class="row-odd">
														<div align="left"><bean:message
															key="knowledgepro.employee.author.name" /></div>
														</td>
														<%
															String authorName = "authorName_" + count;
														%>
														<td width="30%" class="row-even"><nested:text
															property="authorName" styleId="<%=authorName%>" size="40"
															maxlength="100"  onkeypress="charCountDisplay()"></nested:text></td>
													</tr>
													<tr>
														<td width="20%" class="row-odd">
														<div align="left"><bean:message
															key="knowledgepro.employee.editor.name" /></div>
														</td>
														<td width="30%" height="25" class="row-even"><nested:text
															property="editorsName" size="40" maxlength="100" ></nested:text></td>

														<td width="20%" height="25" class="row-odd">
														<div align="left"><bean:message
															key="employee.info.language.title" /></div>
														</td>

														<td width="30%" class="row-even"><nested:text property="language"
															styleId="language" size="40" maxlength="50" ></nested:text></td>
													</tr>
													<tr>
														<td width="20%" class="row-odd">
														<div align="left"><bean:message
															key="knowledgepro.employee.monthYear" /></div>
														</td>
														<td width="30%" height="25" class="row-even"><nested:text
															property="monthYear" size="40" maxlength="20" ></nested:text></td>

													</tr>
													<tr>

														<td width="20%" class="row-odd">
														<div align="left"><bean:message
															key="knowledgepro.employee.pages.from" /></div>
														</td>
														<td width="30%" height="25" class="row-even"><nested:text
															property="pagesFrom" size="40" maxlength="10" onkeypress="return isNumberKey(event)"></nested:text></td>

														<td width="20%" class="row-odd">
														<div align="left"><bean:message
															key="knowledgepro.employee.pages.to" /></div>
														</td>
														<td width="30%" height="25" class="row-even"><nested:text
															property="pagesTo" size="40" maxlength="10" onkeypress="return isNumberKey(event)"></nested:text></td>
													</tr>
													<tr>
														<td width="20%" class="row-odd">
														<div align="left"><bean:message
															key="knowledgepro.employee.publishing.company" /></div>
														</td>
														<td width="30%" height="25" class="row-even"><nested:text
															property="companyInstitution" size="40" maxlength="250" ></nested:text></td>


														<td width="20%" class="row-odd">
														<div align="left"><bean:message
															key="knowledgepro.employee.place.publishing" /></div>
														</td>
														<td width="30%" height="25" class="row-even"><nested:text
															property="placePublication" size="40" maxlength="100" ></nested:text></td>

													</tr>
													<tr>
														<td width="20%" class="row-odd">
														<div align="left"><bean:message
															key="knowledgepro.employee.total.pages" /></div>
														</td>
														<td width="30%" height="25" class="row-even"><nested:text
															property="totalPages" size="40" maxlength="10"  onkeypress="return isNumberKey(event)"></nested:text></td>

														<td width="20%" class="row-odd">
														<div align="left"><bean:message
															key="knowledgepro.employee.isbn" /></div>
														</td>
														<td width="30%" height="25" class="row-even"><nested:text
															property="isbn" size="40" maxlength="50" ></nested:text></td>
													</tr>
													<tr>

														<td width="20%" class="row-odd">
														<div align="left"><bean:message
															key="knowledgepro.employee.title.chapter.article" /></div>
														</td>
														<td width="30%" class="row-even"><nested:textarea
															property="titleChapterArticle" cols="40" rows="1" 
															onclick="len_display(this,1000,'long_lenChapterArticlBook','charCountChapterArticlBook')"
															onkeypress="return imposeMaxLength(this, 0, 999);" onchange="return imposeMaxLength(this, 0, 999);" 
															onkeyup="len_display(this,1000,'long_lenChapterArticlBook','charCountChapterArticlBook')" ></nested:textarea></td>

														<td width="20%" class="row-odd">
														<div align="left"><bean:message
															key="knowledgepro.employee.doi"/> </div>
														</td>
														<td width="30%" height="25" class="row-even"><nested:text
															property="doi" size="40" maxlength="100" ></nested:text></td>
														
													</tr>
													<tr>

														<td width="20%" class="row-odd">
														<div align="left"><span class="Mandatory">*</span><bean:message
															key="knowledgepro.fee.academicyear" /></div>
														</td>
														<%String chPYearId = "YOP3" + count; %>
														
														<td width="16%" class="row-even"><c:set var="dyopid1"><%=chPYearId%></c:set>
														<nested:select property="academicYear" styleClass="comboLarge" styleId='<%=chPYearId %>'>
															<option value=""><bean:message
																key="knowledgepro.admin.select" /></option>
															<cms:renderEmpResearchYear></cms:renderEmpResearchYear>
														</nested:select> <script type="text/javascript">
																var yopid= '<nested:write property="academicYear"/>';
																document.getElementById("<c:out value='${dyopid1}'/>").value = yopid;
															</script>
														</td>
														<td width="20%" class="row-odd">
														<div align="left"><span class="Mandatory">*</span>Sole Author/Co-Author</div>
														</td>
														<td width="30%" height="25" class="row-even">
                								 			<nested:select property="coAuthored" styleId="coAuthored" styleClass="comboLarge">
  	   				 										<html:option value="">- Select -</html:option>
  	   														<html:option value="Sole">Sole</html:option>
								   							<html:option value="Co-Author">Co-Author</html:option>
   			   												</nested:select>
														</td>
													</tr>
													  </logic:equal>
												</nested:iterate>
											</logic:notEmpty>
										</tr>
										<tr>
										<td class="row-even" colspan="4"><div>
										<table width="100%" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td height="25" align="center">
											<logic:notEmpty property="empChapterArticlBookTO"
												name="EmpResPubDetailsForm">
													<html:button property="" styleClass="formbutton" value="Send For Approval" onclick="saveEmpResPubDetails('ChapterArticlBook')"></html:button>&nbsp;&nbsp;	
											</logic:notEmpty>
											&nbsp;<html:submit
												property="" styleClass="formbutton" value="Add more"
												styleId="addMore"
												onclick="submitEmployeeInfoAdd('resetChapArticlBook','ExpAddMore'); return false;"></html:submit>
											<c:if
												test="${EmpResPubDetailsForm.empChapterArticlBookLength>0}">
												<html:submit property="" styleClass="formbutton"
													value="Remove" styleId="addMore"
													onclick="submitEmployeeInfoAdd('removeChapArticlBook','ExpAddMore'); return false;"></html:submit>
											</c:if></td>
										</tr>
										</table>
									</div></td>
											
											
											
										</tr>
										
										

									</table>

									</td>
								</tr>




<tr class="tdIMG" height="35px">
									<td>
									<div align="left" style="border: none; font-size: 13px; color: #006600"><b> <bean:message
										key="employee.article.in.periodicals" /></b></div>
									</td>
 <td align="right"> <div class="arrow"></div></td>
								</tr>
								<tr class="data" id="ArticlInPeriodicals">
									<td>
									<table width="100%" cellspacing="1" cellpadding="2">
									<tr>
										<td colspan="4" align="right"><div align="right" id="charCountArticlInPeriodicals"><input type="text" id="long_lenArticlInPeriodicals" class="len" size="2" readonly="readonly" style="border: none; text-align: right; font-size: 11px; font-family: Verdana, Arial, Helvetica, sans-serif"> Characters Remaining</div></td>
										</tr>	
										<tr class="row-odd">
											<logic:notEmpty property="empArticlInPeriodicalsTO"
												name="EmpResPubDetailsForm">
												<nested:iterate property="empArticlInPeriodicalsTO"
													name="EmpResPubDetailsForm" id="empArticlInPeriodicals"
													indexId="count">
													<tr>
													<td><div style="border: none; font-size: 10px; color: #000000"><b>Sl No:- <c:out value="${count + 1}"/></b></div></td>
													<td><div style="border: none; font-size: 10px; color: #000000"><b>Submitted Date:- 
													<bean:write name="empArticlInPeriodicals" property="entryCreatedate" /></b></div></td>
													<logic:equal value="true" name="empArticlInPeriodicals" property="isRejected">
													<td><FONT color="red"><b>Rejected</b></FONT></td> 
													<td><div style="border: none; font-size: 10px; color: red"><b>Rejected Date:- 
													<bean:write name="empArticlInPeriodicals" property="rejectDate" /></b></div></td>
													</logic:equal>
													</tr>
									<logic:equal value="false" name="empArticlInPeriodicals" property="isArticleInPeriodicals">
													<tr>
														<td width="20%" height="25" class="row-odd">
														<div align="left"><bean:message
															key="knowledgepro.employee.title.of.article" /></div>
														</td>
														<td width="30%" class="row-even"><nested:textarea
															property="title" cols="40" rows="2"
															onclick="len_display(this,1000,'long_lenArticlInPeriodicals','charCountArticlInPeriodicals')"
															 onkeypress="return imposeMaxLength(this, 0, 999);" onchange="return imposeMaxLength(this, 0, 999);" 
															 onkeyup="len_display(this,1000,'long_lenArticlInPeriodicals','charCountArticlInPeriodicals')" ></nested:textarea></td>

														<td width="20%" height="25" class="row-odd">
														<div align="left"><bean:message
															key="knowledgepro.employee.author.name" /></div>
														</td>
														<%
															String authorName = "authorName_" + count;
														    int ct = count;
														%>
														<td width="30%" class="row-even"><nested:text
															property="authorName" styleId="<%=authorName%>" size="40"
															maxlength="100"  onclick="charCountDisplay()"></nested:text></td>

													</tr>
													<tr>
														<td width="20%" class="row-odd">
														<div align="left"><bean:message
															key="knowledgepro.employee.name.of.periodical" /></div>
														</td>
														<td width="30%" height="25" class="row-even"><nested:text
															property="namePeriodical" size="40" maxlength="100" ></nested:text></td>

														<td width="20%" height="25" class="row-odd">
														<div align="left"><bean:message
															key="employee.info.language.title" /></div>
														</td>

														<td width="30%" class="row-even"><nested:text property="language"
															styleId="language" size="40" maxlength="50" ></nested:text></td>
													</tr>
													<tr>
														<td width="20%" class="row-odd">
														<div align="left"><bean:message
															key="knowledgepro.employee.date.monthYear.publication" /> (dd/mm/yyyy)</div>
														</td>
														<%
															String styleDate3 = "dateMonthYear_" + count;
														%>
														<td width="30%" height="25" class="row-even"><span class="star"><nested:text
															property="dateMonthYear" styleId="<%=styleDate3%>"
															size="20" maxlength="20"></nested:text> <script
															language="JavaScript">
														new tcal( {
														// form name
														'formname' :'EmpResPubDetailsForm',
														// input name
														'controlname' :'<%=styleDate3%>'
														});
													</script></span></td>


														<td width="20%" class="row-odd">
														<div align="left"><bean:message
															key="knowledgepro.employee.volume.Number" /></div>
														</td>
														<td width="30%" height="25" class="row-even"><nested:text
															property="volumeNumber" size="40" maxlength="20"></nested:text></td>
													</tr>
													<tr>

														<td width="20%" class="row-odd">
														<div align="left"><bean:message
															key="knowledgepro.employee.issueNumber" /></div>
														</td>
														<td width="30%" height="25" class="row-even"><nested:text
															property="issueNumber" size="40" maxlength="20"></nested:text></td>

														<td width="20%" class="row-odd">
														<div align="left"><bean:message
															key="knowledgepro.employee.issn" /></div>
														</td>
														<td width="30%" height="25" class="row-even"><nested:text
															property="isbn" size="40" maxlength="50" ></nested:text></td>
													</tr>
													<tr>

														<td width="20%" class="row-odd">
														<div align="left"><bean:message
															key="knowledgepro.employee.pages.from" /></div>
														</td>
														<td width="30%" height="25" class="row-even"><nested:text
															property="pagesFrom" size="40" maxlength="10" onkeypress="return isNumberKey(event)"></nested:text></td>

														<td width="20%" class="row-odd">
														<div align="left"><bean:message
															key="knowledgepro.employee.pages.to" /></div>
														</td>
														<td width="30%" height="25" class="row-even"><nested:text
															property="pagesTo" size="40" maxlength="10" onkeypress="return isNumberKey(event)"></nested:text></td>
													</tr>
													<tr>

														<td width="20%" class="row-odd">
														<div align="left"><span class="Mandatory">*</span><bean:message
															key="knowledgepro.fee.academicyear" /></div>
														</td>
														<%String aPYearId = "YOP4" + count; %>
														
														<td width="16%" class="row-even"><c:set var="dyopid1"><%=aPYearId%></c:set>
														<nested:select property="academicYear" styleClass="comboLarge" styleId='<%=aPYearId %>'>
															<option value=""><bean:message
																key="knowledgepro.admin.select" /></option>
															<cms:renderEmpResearchYear></cms:renderEmpResearchYear>
														</nested:select> <script type="text/javascript">
																var yopid= '<nested:write property="academicYear"/>';
																document.getElementById("<c:out value='${dyopid1}'/>").value = yopid;
															</script>
														</td>
														<td width="20%" class="row-odd">
														<div align="left"><span class="Mandatory">*</span><bean:message
															key="knowledgepro.employee.type" /></div>
														</td>
														<td width="30%" height="25" class="row-even">
														
													
														<%
														String periodicalsType = "periodicalsType_" + count;
														String Type = "type_" + count;
														String FunctionShowHide="getOtherArticlePerodicals('"+Type+"','"+count+"');";	
														%>	
														<input type="hidden" id="<%=periodicalsType%>" name="PeriodicalsType" value="<bean:write name="empArticlInPeriodicals" property="type"/>"/>
                								 		<nested:select property="type" styleId="<%=Type%>" styleClass="comboLarge" onchange="<%=FunctionShowHide %>">
  	   				 										<html:option value="">- Select -</html:option>
  	   														<html:option value="Daily">Daily </html:option>
								   							<html:option value="Weekly">Weekly</html:option>
															<html:option value="Fortnightly">Fortnightly </html:option>
								   							<html:option value="Monthly">Monthly</html:option>
															<html:option value="Bimonthly">Bimonthly </html:option>
															<html:option value="Quarterly">Quarterly </html:option>
								   							<html:option value="Biannual">Biannual</html:option>
															<html:option value="Annual">Annual </html:option>
															<html:option value="Other">Other</html:option>
   			   											</nested:select>
   			   												
															
									<%
										String other1 = "otherTextArticle_" + count;
									%>
									     <div id="<%=other1%>">
									     	<nested:text property="otherTextArticle" size="40" maxlength="300" styleId="otherTextArticle" ></nested:text>
									     </div>		
									     
									     <script type="text/javascript">
												    		var valueSelected=document.getElementById("periodicalsType_<c:out value='${count}'/>").value;
											    				 if(valueSelected!=null && valueSelected!="" && valueSelected=="Other"){
												    					document.getElementById("otherTextArticle_<c:out value='${count}'/>").style.display="block";
												    			}else{
												    					document.getElementById("otherTextArticle_<c:out value='${count}'/>").style.display="none";
												    			}
												    		 </script>
												    		 </td>
													</tr>
													
								</logic:equal>
												</nested:iterate>
											</logic:notEmpty>
										</tr>
										<tr>
											<td class="row-even" colspan="4"><div>
										<table width="100%" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td height="25" align="center">
											<logic:notEmpty property="empArticlInPeriodicalsTO"
												name="EmpResPubDetailsForm">
											<html:button property="" styleClass="formbutton" value="Send For Approval" onclick="saveEmpResPubDetails('ArticlInPeriodicals')"></html:button>&nbsp;&nbsp;	
											</logic:notEmpty>
											&nbsp;<html:submit
												property="" styleClass="formbutton" value="Add more"
												styleId="addMore"
												onclick="submitEmployeeInfoAdd('resetArticlInPeriodicals','ExpAddMore'); return false;"></html:submit>
											<c:if
												test="${EmpResPubDetailsForm.empArticlPeriodicalsLength>0}">
												<html:submit property="" styleClass="formbutton"
													value="Remove" styleId="addMore"
													onclick="submitEmployeeInfoAdd('removeArticlInPeriodicals','ExpAddMore'); return false;"></html:submit>
											</c:if></td>
										</tr>
										</table>
									</div></td>
											
											
											
										</tr>
										
										

									</table>

									</td>
								</tr>
								
			<tr class="tdIMG" height="35px">
				   <td>
					<div align="left" style="border: none; font-size: 13px; color: #006600"><b> <bean:message	key="employee.conference.presentation" /></b></div>
					</td>
 						<td align="right"> <div class="arrow"></div></td>
						</tr>
						<tr class="data" id="ConfPresent">
					<td>
							<table width="100%" cellspacing="1" cellpadding="2">
								<tr>
										<td colspan="4" align="right"><div align="right" id="charCountConferencePresentation"><input type="text" id="long_lenConferencePresentation" class="len" size="2" readonly="readonly" style="border: none; text-align: right; font-size: 11px; font-family: Verdana, Arial, Helvetica, sans-serif"> Characters Remaining</div></td>
										</tr>
								<tr class="row-odd">
									<logic:notEmpty property="empConferencePresentationTO"
										name="EmpResPubDetailsForm">
										<nested:iterate property="empConferencePresentationTO"
											name="EmpResPubDetailsForm" id="empConferencePresentation" indexId="count">
											<tr>
													<td><div style="border: none; font-size: 10px; color: #000000"><b>Sl No:- <c:out value="${count + 1}"/></b></div></td>
													<td><div style="border: none; font-size: 10px; color: #000000"><b>Submitted Date:- 
													<bean:write name="empConferencePresentation" property="entryCreatedate" /></b></div></td>
													</tr>
					
							<logic:equal value="false" name="empConferencePresentation" property="isConferencePresentation">
											
											<tr>
												<td width="20%" height="25" class="row-odd">
												<div align="left"><bean:message
													key="knowledgepro.employee.title.of.article" /></div>
												</td>
												<td width="30%" class="row-even"><nested:textarea
													property="title" cols="40" rows="2"
													onclick="len_display(this,1000,'long_lenConferencePresentation','charCountConferencePresentation')"
													onkeypress="return imposeMaxLength(this, 0, 999);" onchange="return imposeMaxLength(this, 0, 999);" 
													onkeyup="len_display(this,1000,'long_lenConferencePresentation','charCountConferencePresentation')" ></nested:textarea></td>
													
													<td width="20%" height="25" class="row-odd">
												<div align="left"><bean:message
													key="KnowledgePro.employee.Names" /></div>
												</td>
												<%
											String nameTalksPresentation = "nameTalksPresentation_" + count;
											%>
												<td width="30%" class="row-even"><nested:text
													property="nameTalksPresentation" styleId="<%=nameTalksPresentation%>" size="40" maxlength="200" 
													 onclick="charCountDisplay()"></nested:text></td>
												
											</tr>
											<tr>
												<!-- <td width="20%" class="row-odd">
												<div align="left"><bean:message
													key="employee.institution.radio.tv" /></div>
												</td>
												<td width="30%" height="25" class="row-even"><nested:text
													property="companyInstitution" size="40" maxlength="250"></nested:text></td>-->
												
												<td width="20%" height="25" class="row-odd">
														<div align="left"><bean:message
															key="employee.info.language.title" /></div>
														</td>

														<td width="30%" class="row-even"><nested:text property="language"
															styleId="language" size="40" maxlength="50" ></nested:text></td>
												
												
												<td width="20%" height="25" class="row-odd">
												<div align="left"><bean:message
													key="employee.name.conference.seminar" /></div>
												</td>
												
												<td width="30%" class="row-even"><nested:text property="nameConferencesSeminar" 
												styleId="nameConferencesSeminar" size="40" maxlength="200" ></nested:text></td>
											</tr>
											<tr>
												<td width="20%" class="row-odd">
												<div align="left"><bean:message
													key="knowledgepro.employee.place" /></div>
												</td>
												<td width="30%" height="25" class="row-even"><nested:text
													property="placePublication" size="40" maxlength="100" ></nested:text></td>
											
											
												<td width="20%" class="row-odd">
												<div align="left"><bean:message
													key="knowledgepro.employee.monthYear" />(dd/mm/yyyy)</div>
												</td>
												<!-- <td width="30%" height="25" class="row-even"><nested:text
													property="monthYear" size="40" maxlength="20" ></nested:text></td>-->
											
												<% String styleDate23 = "monthYear_" + count;%>
												<td width="30%" class="row-even"><div align="left">
               								  	 <nested:text  property="monthYear" styleId="<%=styleDate23 %>" size="20" maxlength="20" ></nested:text>
                 							 		<script
														language="JavaScript">
														new tcal( {
														// form name
														'formname' :'EmpResPubDetailsForm',
														// input name
														'controlname' :'<%=styleDate23 %>'
														});
													</script></div>
                 							 	</td>
										</tr>
											<tr>


														<td width="20%" class="row-odd">
														<div align="left"><span class="Mandatory">*</span><bean:message
															key="knowledgepro.fee.academicyear" /></div>
														</td>
														<%String tpYearId = "YOP5" + count; %>
														
														<td width="16%" class="row-even"><c:set var="dyopid1"><%=tpYearId%></c:set>
														<nested:select property="academicYear" styleClass="comboLarge" styleId='<%=tpYearId %>'>
															<option value=""><bean:message
																key="knowledgepro.admin.select" /></option>
															<cms:renderEmpResearchYear></cms:renderEmpResearchYear>
														</nested:select> <script type="text/javascript">
																var yopid= '<nested:write property="academicYear"/>';
																document.getElementById("<c:out value='${dyopid1}'/>").value = yopid;
															</script>
														</td>
														<td width="20%" class="row-odd">
														<div align="left"><span class="Mandatory">*</span><bean:message
															key="knowledgepro.employee.level" /></div>
														</td>
														<td width="30%" height="25" class="row-even">
   			   											
   			   											<input type="hidden" id="ConfType" name="ConfType" value="<bean:write name="empConferencePresentation" property="type"/>"/>
                								 			<nested:select property="type" styleId="type" styleClass="comboLarge">
  	   				 										<html:option value="">- Select -</html:option>
  	   														<html:option value="Institutional">Institutional </html:option>
								   							<html:option value="Regional">Regional</html:option>
															<html:option value="National">National </html:option>
								   							<html:option value="International">International</html:option>
   			   												</nested:select>
														
														<!--<nested:text
															property="type" size="40" maxlength="10" onkeypress="return isNumberKey(event)"></nested:text>-->
															</td>
													</tr>
										
											<tr>

												<td width="20%" height="25" class="row-odd">
											      <div align="left"><bean:message
															key="knowledgepro.conference.abstracts" /></div>
														</td>

												<td class="row-even"><nested:textarea
													property="abstractDetails" cols="40" rows="2"
													onclick="len_display(this,2000,'long_lenConferencePresentation','charCountConferencePresentation')"
													 onkeypress="return imposeMaxLength(this, 0, 1999);" 
													onchange="return imposeMaxLength(this, 0, 1999);" 
													onkeyup="len_display(this,2000,'long_lenConferencePresentation','charCountConferencePresentation')" ></nested:textarea></td>
												
												<td width="20%" class="row-odd">
														<div align="left"><span class="Mandatory">*</span><bean:message
															key="employee.name.conference.type.pgm" /></div>
														</td>
														<td width="30%" height="25" class="row-even">
														
															<%
															String ConfTypePgm = "ConfTypePgm_" + count;
									    					String typeOfPgm = "typeOfPgm_"+count;
															String FunctionConfShowHide="getOtherConferencePreseantation('"+typeOfPgm+"','"+count+"');";	
															%>
														<input type="hidden" id="<%=ConfTypePgm%>" name="ConfTypePgm" value="<bean:write name="empConferencePresentation" property="typeOfPgm"/>"/>
                								 			<nested:select property="typeOfPgm" styleId="<%=typeOfPgm%>" styleClass="comboLarge" onchange="<%=FunctionConfShowHide%>">
  	   				 										<html:option value="">- Select -</html:option>
  	   														<html:option value="Seminar">Seminar</html:option>
								   							<html:option value="Conference">Conference</html:option>
															<html:option value="Symposium">Symposium</html:option>
															<html:option value="Other">Other</html:option>
   			   											</nested:select>
									<%
										String other = "other_" + count;
									   // String otherconf="otherTextConf_"+ count;
									%>
									     <div id="<%=other%>">
									     	<nested:text property="otherTextConf" size="40" maxlength="300"></nested:text>
									     </div>	
									    <script type="text/javascript">
												    		var valueSelected=document.getElementById("typeOfPgm_<c:out value='${count}'/>").value;
											    				 if(valueSelected!=null && valueSelected!="" && valueSelected=="Other"){
												    					document.getElementById("other_<c:out value='${count}'/>").style.display="block";
			 									    			}else{
												    					document.getElementById("other_<c:out value='${count}'/>").style.display="none";
												    			}
									 </script>	
									     </td>
											</tr>
											</logic:equal>
										</nested:iterate>
									</logic:notEmpty>
								</tr>
								 <tr>
			                         	<td class="row-even" colspan="4"><div>
										<table width="100%" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td height="25" align="center">
							<logic:notEmpty property="empConferencePresentationTO"
										name="EmpResPubDetailsForm">
												<html:button property="" styleClass="formbutton" value="Send For Approval" onclick="saveEmpResPubDetails('ConferencePresentation')"></html:button>&nbsp;&nbsp;
											
											
							</logic:notEmpty>&nbsp;<html:submit property="" styleClass="formbutton" value="Add more"  styleId="addMore" onclick="submitEmployeeInfoAdd('resetConfPresent','ExpAddMore'); return false;"></html:submit>
										<c:if test="${EmpResPubDetailsForm.empConfPresentLength>0}">
				                        		<html:submit property="" styleClass="formbutton" value="Remove"  styleId="addMore" onclick="submitEmployeeInfoAdd('removeConfPresent','ExpAddMore'); return false;"></html:submit>
				                        	</c:if></td>
										</tr>
										</table>
									</div></td>
											
											
											
										</tr>
										
										

									</table>

									</td>
								</tr>
								
		<tr class="tdIMG" height="35px">
	    <td>
		<div align="left" style="border: none; font-size: 13px; color: #006600"><b> <bean:message	key="employee.seminars.attended" /></b></div>
		</td>
 		<td align="right"> <div class="arrow"></div></td>
		</tr>
		<tr class="data" id="SeminarsAttended">
				<td>
							<table width="100%" cellspacing="1" cellpadding="2">
								<tr>
										<td colspan="4" align="right"><div align="right" id="charCountSeminarsAttended"><input type="text" id="long_lenSeminarsOrganized" class="len" size="2" readonly="readonly" style="border: none; text-align: right; font-size: 11px; font-family: Verdana, Arial, Helvetica, sans-serif"> Characters Remaining</div></td>
										</tr>
								<tr class="row-odd">
									<logic:notEmpty property="empConferenceSeminarsAttendedTO"
										name="EmpResPubDetailsForm">
										<nested:iterate property="empConferenceSeminarsAttendedTO"
											name="EmpResPubDetailsForm" id="empSeminarsAttended" indexId="count">
											<tr>
													<td><div style="border: none; font-size: 10px; color: #000000"><b>Sl No:- <c:out value="${count + 1}"/></b></div></td>
													<td><div style="border: none; font-size: 10px; color: #000000"><b>Submitted Date:- 
													<bean:write name="empSeminarsAttended" property="entryCreatedate" /></b></div></td>
													
													<logic:equal value="true" name="empSeminarsAttended" property="isRejected">
													<td><FONT color="red"><b>Rejected</b></FONT></td> 
													<td><div style="border: none; font-size: 10px; color: red"><b>Rejected Date:- 
													<bean:write name="empSeminarsAttended" property="rejectDate" /></b></div></td>
													</logic:equal>
													
													</tr>
					<logic:equal value="false" name="empSeminarsAttended" property="isSeminarAttended">
											
											
											<tr>
												<td width="20%" height="25" class="row-odd">
												<div align="left"><bean:message
													key="knowledgepro.pgm.organisedBy" /></div>
												</td>
													
												<td width="30%" class="row-even"><nested:text
													property="organisedBy" size="40" maxlength="200" ></nested:text></td>
												
												<td width="20%" height="25" class="row-odd">
												<div align="left"><bean:message
													key="knowledgepro.employee.Name.program" /></div>
												</td>
												<td width="30%" class="row-even"><nested:text
													property="nameOfPgm" size="40" maxlength="200"  ></nested:text></td>
											</tr>
											<tr>
												<td width="20%" height="25" class="row-odd">
												<div align="left"><bean:message
													key="knowledgepro.date.pgm" /> (dd/mm/yyyy)</div>
												</td>
														
														<%
															String styleDate12 = "dateOfPgm1_" + count;
														%>
														
												
												<td width="30%" class="row-even"><div align="left">
                  <nested:text  property="dateOfPgm" styleId="<%=styleDate12 %>" size="20" maxlength="20" ></nested:text>
                  <script
											language="JavaScript">
												new tcal( {
												// form name
												'formname' :'EmpResPubDetailsForm',
												// input name
												'controlname' :'<%=styleDate12 %>'
												});
					</script></div>
                  </td>
                       <td width="20%" height="25" class="row-odd">
														<div align="left"><bean:message
															key="knowledgepro.date.pgm.end" />(dd/mm/yyyy)</div>
														</td>
				<% String styleDate22 = "endOfPgm1_" + count;%>
				<td width="30%" class="row-even"><div align="left">
                  <nested:text  property="endOfPgm" styleId="<%=styleDate22 %>" size="20" maxlength="20" ></nested:text>
                  <script
											language="JavaScript">
												new tcal( {
												// form name
												'formname' :'EmpResPubDetailsForm',
												// input name
												'controlname' :'<%=styleDate22 %>'
												});
					</script></div>
                  </td>
												
												
											<!-- 	<td width="20%" height="25" class="row-odd">
														<div align="left"><bean:message
															key="knowledgepro.duration.pgm" /></div>
														</td>

														<td width="30%" class="row-even"><nested:text property="duration"
															styleId="duration" size="40" maxlength="10" ></nested:text></td>-->
											</tr>
												<tr>

														<td width="20%" class="row-odd">
														<div align="left"><span class="Mandatory">*</span><bean:message
															key="knowledgepro.fee.academicyear" /></div>
														</td>
															<%String semiYearId = "YOP6" + count; %>
														
														<td width="16%" class="row-even"><c:set var="dyopid1"><%=semiYearId%></c:set>
														<nested:select property="academicYear" styleClass="comboLarge" styleId='<%=semiYearId %>'>
															<option value=""><bean:message
																key="knowledgepro.admin.select" /></option>
															<cms:renderEmpResearchYear></cms:renderEmpResearchYear>
														</nested:select> <script type="text/javascript">
																var yopid= '<nested:write property="academicYear"/>';
																document.getElementById("<c:out value='${dyopid1}'/>").value = yopid;
															</script>
														</td>
														
														<td width="20%" class="row-odd">
														<div align="left"><span class="Mandatory">*</span><bean:message
															key="knowledgepro.employee.level" /></div>
														</td>
														<td width="30%" height="25" class="row-even">
														
														<input type="hidden" id="AttendedType" name="AttendedType" value="<bean:write name="empSeminarsAttended" property="type"/>"/>
                								 			<nested:select property="type" styleId="type" styleClass="comboLarge">
  	   				 										<html:option value="">- Select -</html:option>
  	   														<html:option value="Institutional">Institutional </html:option>
								   							<html:option value="Regional">Regional</html:option>
															<html:option value="National">National </html:option>
								   							<html:option value="International">International</html:option>
   			   												</nested:select>
															</td>
													</tr>
											
											
											</logic:equal>
				
				
										</nested:iterate>
									</logic:notEmpty>
								</tr>
								 <tr>
			                         	<td class="row-even" colspan="4"><div>
										<table width="100%" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td height="25" align="center">
											<logic:notEmpty property="empConferenceSeminarsAttendedTO"
										name="EmpResPubDetailsForm">
											<html:button property="" styleClass="formbutton" value="Send For Approval" onclick="saveEmpResPubDetails('SeminarsAttended')"></html:button>&nbsp;&nbsp;
											</logic:notEmpty>
											&nbsp;<html:submit property="" styleClass="formbutton" value="Add more"  styleId="addMore" onclick="submitEmployeeInfoAdd('resetSeminarsAttended','ExpAddMore'); return false;"></html:submit>
										<c:if test="${EmpResPubDetailsForm.empConfSeminarsAttendedLength>0}">
				                        		<html:submit property="" styleClass="formbutton" value="Remove"  styleId="addMore" onclick="submitEmployeeInfoAdd('removeSeminarsAttended','ExpAddMore'); return false;"></html:submit>
				                        	</c:if></td>
										</tr>
										</table>
									</div></td>
											
											
											
										</tr>
										
										

									</table>

									</td>
								</tr>
								
								
								
								
								
	<tr class="tdIMG" height="35px">
				   <td>
					<div align="left" style="border: none; font-size: 13px; color: #006600"><b> <bean:message key="employee.invited.talk" /></b></div>
					</td>
 						<td align="right"> <div class="arrow"></div></td>
						</tr>
						<tr class="data" id="InvitedTalk">
					<td>
							<table width="100%" cellspacing="1" cellpadding="2">
								<tr>
										<td colspan="4" align="right"><div align="right" id="charCountInvitedTalk"><input type="text" id="long_lenInvitedTalk" class="len" size="2" readonly="readonly" style="border: none; text-align: right; font-size: 11px; font-family: Verdana, Arial, Helvetica, sans-serif"> Characters Remaining</div></td>
										</tr>
								<tr class="row-odd">
									<logic:notEmpty property="empInvitedTalkTO"
										name="EmpResPubDetailsForm">
										<nested:iterate property="empInvitedTalkTO"
											name="EmpResPubDetailsForm" id="empInvitedTalk" indexId="count">
											<tr>
													<td><div style="border: none; font-size: 10px; color: #000000"><b>Sl No:- <c:out value="${count + 1}"/></b></div></td>
													<td><div style="border: none; font-size: 10px; color: #000000"><b>Submitted Date:- 
													<bean:write name="empInvitedTalk" property="entryCreatedate" /></b></div></td>
													
													<logic:equal value="true" name="empInvitedTalk" property="isRejected">
													<td><FONT color="red"><b>Rejected</b></FONT></td> 
													<td><div style="border: none; font-size: 10px; color: red"><b>Rejected Date:- 
													<bean:write name="empInvitedTalk" property="rejectDate" /></b></div></td>
													</logic:equal>
													</tr>
			<logic:equal value="false" name="empInvitedTalk" property="isInvitedTalk">
					<tr>
					<td width="20%" height="25" class="row-odd">
						<div align="left"><bean:message	key="knowledgepro.employee.title.invitedTalks" /></div>
					</td>
					<td width="30%" class="row-even" colspan="3">
					<nested:textarea
													property="title" cols="80" rows="1"
													onclick="len_display(this,1000,'long_lenInvitedTalk','charCountInvitedTalk')"
													onkeypress="return imposeMaxLength(this, 0, 999);" onchange="return imposeMaxLength(this, 0, 999);" 
													onkeyup="len_display(this,1000,'long_lenInvitedTalk','charCountInvitedTalk')"  ></nested:textarea></td>
													
													<!-- <td width="20%" height="25" class="row-odd">
												<div align="left"><bean:message
													key="KnowledgePro.employee.Names" /></div>
												</td>
												<%
											String nameTalksPresentation = "nameTalksPresentation_" + count;
											%>
												<td width="30%" class="row-even"><nested:text
													property="nameTalksPresentation" styleId="<%=nameTalksPresentation%>" size="40" maxlength="200"  onclick="charCountDisplay()"></nested:text></td>-->
												
											</tr>
											<tr>
												<td width="20%" class="row-odd">
												<div align="left"><bean:message
													key="employee.institution.radio.tv" /></div>
												</td>
												<td width="30%" height="25" class="row-even"><nested:text
													property="companyInstitution" size="40" maxlength="250" ></nested:text></td>
												
												<td width="20%" height="25" class="row-odd">
												<div align="left">Name Of Program</div>
												</td>
												
												<td width="30%" class="row-even" ><nested:text property="nameOfPgm" 
												styleId="nameOfPgm" size="40" maxlength="200"></nested:text></td>
												
												
												<!-- <td width="20%" height="25" class="row-odd">
												<div align="left"><bean:message
													key="employee.name.conference.seminar" /></div>
												</td>
												
												<td width="30%" class="row-even"><nested:text property="nameConferencesSeminar" 
												styleId="nameConferencesSeminar" size="30" maxlength="200"></nested:text></td>-->
											</tr>
											<tr>
												<td width="20%" class="row-odd">
												<div align="left"><bean:message
													key="knowledgepro.employee.place" /></div>
												</td>
												<td width="30%" height="25" class="row-even"><nested:text
													property="placePublication" size="40" maxlength="100" ></nested:text></td>
											
											
												<td width="20%" class="row-odd">
												<div align="left">Date (dd/mm/yyyy)</div>
												</td>
												<% String styleDate24 = "monthYear11_" + count;%>
														
												
										<td width="30%" class="row-even"><div align="left">
                 						 <nested:text  property="monthYear" styleId="<%=styleDate24 %>" size="20" maxlength="20" ></nested:text>
                  						<script
											language="JavaScript">
												new tcal( {
												// form name
												'formname' :'EmpResPubDetailsForm',
												// input name
												'controlname' :'<%=styleDate24 %>'
												});
									</script></div>
                  				</td>
											</tr>
											<tr>

														<td width="20%" class="row-odd">
														<div align="left"><span class="Mandatory">*</span><bean:message
															key="knowledgepro.fee.academicyear" /></div>
														</td>
														
													<%String invYearId = "YOP7" + count; %>
														
													<td width="16%" class="row-even"><c:set var="dyopid1"><%=invYearId%></c:set>
														<nested:select property="academicYear" styleClass="comboLarge" styleId='<%=invYearId %>'>
															<option value=""><bean:message
																key="knowledgepro.admin.select" /></option>
															<cms:renderEmpResearchYear></cms:renderEmpResearchYear>
														</nested:select> <script type="text/javascript">
																var yopid= '<nested:write property="academicYear"/>';
																document.getElementById("<c:out value='${dyopid1}'/>").value = yopid;
															</script>
													</td>
														<td width="20%" class="row-odd">
														<div align="left"><span class="Mandatory">*</span><bean:message
															key="knowledgepro.employee.level" /></div>
														</td>
														<td width="30%" height="25" class="row-even">
														
														<input type="hidden" id="InvitedTalkType" name="InvitedTalkType" value="<bean:write name="empInvitedTalk" property="type"/>"/>
                								 			<nested:select property="type" styleId="type" styleClass="comboLarge">
  	   				 										<html:option value="">- Select -</html:option>
  	   														<html:option value="Institutional">Institutional </html:option>
								   							<html:option value="Regional">Regional</html:option>
															<html:option value="National">National </html:option>
								   							<html:option value="International">International</html:option>
   			   												</nested:select>
														
														
														<!--<nested:text
															property="type" size="40" maxlength="10" onkeypress="return isNumberKey(event)"></nested:text>-->
															</td>
													</tr>
													<tr>
													<td width="20%" height="25" class="row-odd">
														<div align="left"><bean:message
															key="employee.info.language.title" /></div>
														</td>

														<td width="30%" class="row-even" colspan="2"><nested:text property="language"
															styleId="language" size="40" maxlength="50" ></nested:text></td>
													</tr>
											
											<!-- <tr>

														<td width="20%" height="25" class="row-odd">
														<div align="left"><bean:message
															key="employee.info.language.title" /></div>
														</td>

														<td width="30%" class="row-even"><nested:text property="language"
															styleId="language" size="30" maxlength="50"></nested:text></td>
											</tr>-->
											</logic:equal>
										</nested:iterate>
									</logic:notEmpty>
								</tr>
								 <tr>
			                         	<td class="row-even" colspan="4"><div>
										<table width="100%" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td height="25" align="center">
							<logic:notEmpty property="empInvitedTalkTO"	name="EmpResPubDetailsForm">
											
											<html:button property="" styleClass="formbutton" value="Send For Approval" onclick="saveEmpResPubDetails('InvitedTalk')"></html:button>&nbsp;&nbsp;
											
							</logic:notEmpty>
											&nbsp;<html:submit property="" styleClass="formbutton" value="Add more"  styleId="addMore" onclick="submitEmployeeInfoAdd('resetInvitedTalk','ExpAddMore'); return false;"></html:submit>
										<c:if test="${EmpResPubDetailsForm.empInvitedTalkLength>0}">
				                        		<html:submit property="" styleClass="formbutton" value="Remove"  styleId="addMore" onclick="submitEmployeeInfoAdd('removeInvitedTalk','ExpAddMore'); return false;"></html:submit>
				                        	</c:if></td>
										</tr>
										</table>
									</div></td>
											
											
											
										</tr>
										
										

									</table>

									</td>
								</tr>
	
				
	<tr class="tdIMG" height="35px">
	    <td>
		<div align="left" style="border: none; font-size: 13px; color: #006600"><b> <bean:message	key="employee.cases.note.working" /></b></div>
		</td>
 		<td align="right"> <div class="arrow"></div></td>
		</tr>
		<tr class="data" id="CasesNotesWorking">
				<td>
							<table width="100%" cellspacing="1" cellpadding="2">
								<tr>
										<td colspan="4" align="right"><div align="right" id="charCountCasesNotesWorking"><input type="text" id="long_lenCasesNotesWorking" class="len" size="2" readonly="readonly" style="border: none; text-align: right; font-size: 11px; font-family: Verdana, Arial, Helvetica, sans-serif"> Characters Remaining</div></td>
										</tr>
								<tr class="row-odd">
									<logic:notEmpty property="empCasesNotesWorkingTO"
										name="EmpResPubDetailsForm">
										<nested:iterate property="empCasesNotesWorkingTO"
											name="EmpResPubDetailsForm" id="empCasesNotesWorking" indexId="count">
											<tr>
													<td><div style="border: none; font-size: 10px; color: #000000"><b>Sl No:- <c:out value="${count + 1}"/></b></div></td>
													<td><div style="border: none; font-size: 10px; color: #000000"><b>Submitted Date:- 
													<bean:write name="empCasesNotesWorking" property="entryCreatedate" /></b></div></td>
													
													<logic:equal value="true" name="empCasesNotesWorking" property="isRejected">
													<td><FONT color="red"><b>Rejected</b></FONT></td> 
													<td><div style="border: none; font-size: 10px; color: red"><b>Rejected Date:- 
													<bean:write name="empCasesNotesWorking" property="rejectDate" /></b></div></td>
													</logic:equal>
													</tr>
					<logic:equal value="false" name="empCasesNotesWorking" property="isCasesNoteWorking">
											
											<tr>
												<td width="20%" height="25" class="row-odd">
												<div align="left"><bean:message
													key="knowledgepro.employee.author.name" /></div>
												</td>
													<%
													String authorName = "authorName_" + count;
														%>
												<td width="30%" class="row-even"><nested:text
													property="authorName" styleId="<%=authorName%>" size="40" maxlength="100" ></nested:text></td>
												
												<td width="20%" height="25" class="row-odd">
												<div align="left"><bean:message
													key="employee.case.note.working.option" /></div>
												</td>
												<td width="30%" class="row-even" align="left">
												<nested:radio property="caseNoteWorkPaper" value="Case">Case</nested:radio>
												<nested:radio property="caseNoteWorkPaper" value="Note" >Note</nested:radio>
												<nested:radio property="caseNoteWorkPaper" value="Working Paper" >Working Paper</nested:radio>
												</td> 
											</tr>
											<tr>
												<td width="20%" height="25" class="row-odd">
												<div align="left"><bean:message
													key="knowledgepro.employee.title.of.article" /></div>
												</td>
												<td width="30%" class="row-even"><nested:textarea
													property="title" cols="40" rows="1"
													onclick="len_display(this,1000,'long_lenCasesNotesWorking','charCountCasesNotesWorking')"
													onkeypress="return imposeMaxLength(this, 0, 999);" onchange="return imposeMaxLength(this, 0, 999);" 
													onkeyup="len_display(this,1000,'long_lenCasesNotesWorking','charCountCasesNotesWorking')" ></nested:textarea></td>
												
												<td width="20%" class="row-odd">
												<div align="left"><bean:message
													key="knowledgepro.employee.abstract.objectives" /></div>
												</td>
												<td width="30%" height="25" class="row-even">
												<nested:textarea property="abstractObjectives" cols="40" rows="1"
												onclick="len_display(this,2000,'long_lenCasesNotesWorking','charCountCasesNotesWorking')"
												onkeypress="return imposeMaxLength(this, 0, 1999);" onchange="return imposeMaxLength(this, 0, 1999);" 
												onkeyup="len_display(this,2000,'long_lenCasesNotesWorking','charCountCasesNotesWorking')" ></nested:textarea>
												</td>
											</tr>
											<tr>
												<td width="20%" height="25" class="row-odd">
												<div align="left"><bean:message
													key="employee.instution.sponsors.department" /></div>
												</td>
												
												<td width="30%" class="row-even"><nested:text
													property="sponsors" styleId="sponsors" size="40" maxlength="100"  onclick="charCountDisplay()"></nested:text></td>
												<td width="20%" class="row-odd">
														<div align="left"><span class="Mandatory">*</span><bean:message
															key="knowledgepro.fee.academicyear" /></div>
														</td>
															<%String caseYearId = "YOP22" + count; %>
														
													<td width="16%" class="row-even"><c:set var="dyopid1"><%=caseYearId%></c:set>
														<nested:select property="academicYear" styleClass="comboLarge" styleId='<%=caseYearId %>'>
															<option value=""><bean:message
																key="knowledgepro.admin.select" /></option>
															<cms:renderEmpResearchYear></cms:renderEmpResearchYear>
														</nested:select> <script type="text/javascript">
																var yopid= '<nested:write property="academicYear"/>';
																document.getElementById("<c:out value='${dyopid1}'/>").value = yopid;
															</script>
													</td>
											</tr>
							</logic:equal>
					
						</nested:iterate>
				</logic:notEmpty>
								</tr>
								 <tr>
			                         	<td class="row-even" colspan="4"><div>
										<table width="100%" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td height="25" align="center">
											<logic:notEmpty property="empCasesNotesWorkingTO"
										name="EmpResPubDetailsForm">
											<html:button property="" styleClass="formbutton" value="Send For Approval" onclick="saveEmpResPubDetails('CasesNotesWorking')"></html:button>&nbsp;&nbsp;
											</logic:notEmpty>
											&nbsp;<html:submit property="" styleClass="formbutton" value="Add more"  styleId="addMore" onclick="submitEmployeeInfoAdd('resetCasesNotesWorking','ExpAddMore'); return false;"></html:submit>
										<c:if test="${EmpResPubDetailsForm.empCasesNotesWorkingLength>0}">
				                        		<html:submit property="" styleClass="formbutton" value="Remove"  styleId="addMore" onclick="submitEmployeeInfoAdd('removeCasesNotesWorking','ExpAddMore'); return false;"></html:submit>
				                        	</c:if></td>
										</tr>
										</table>
									</div></td>
											
											
											
										</tr>
										
										

									</table>

									</td>
								</tr>
	
		<tr class="tdIMG" height="35px">
				   <td>
					<div align="left" style="border: none; font-size: 13px; color: #006600"><b> <bean:message	key="employee.phd.thesis.guided" /></b></div>
					</td>
 						<td align="right"> <div class="arrow"></div></td>
		</tr>
		<tr class="data" id="PhdMPhilThesisGuided">
					<td>
							<table width="100%" cellspacing="1" cellpadding="2">
							<tr>
										<td colspan="4" align="right"><div align="right" id="charCountPhdMPhilThesisGuided"><input type="text" id="long_lenPhdMPhilThesisGuided" class="len" size="2" readonly="readonly" style="border: none; text-align: right; font-size: 11px; font-family: Verdana, Arial, Helvetica, sans-serif"> Characters Remaining</div></td>
										</tr>
								<tr class="row-odd">
									<logic:notEmpty property="empPhdMPhilThesisGuidedTO"
										name="EmpResPubDetailsForm">
										<nested:iterate property="empPhdMPhilThesisGuidedTO"
											name="EmpResPubDetailsForm" id="empPhdMPhilThesisGuided" indexId="count">
											<tr>
													<td><div style="border: none; font-size: 10px; color: #000000"><b>Sl No:- <c:out value="${count + 1}"/></b></div></td>
													<td><div style="border: none; font-size: 10px; color: #000000"><b>Submitted Date:- 
													<bean:write name="empPhdMPhilThesisGuided" property="entryCreatedate" /></b></div></td>
													
													<logic:equal value="true" name="empPhdMPhilThesisGuided" property="isRejected">
													<td><FONT color="red"><b>Rejected</b></FONT></td> 
													<td><div style="border: none; font-size: 10px; color: red"><b>Rejected Date:- 
													<bean:write name="empPhdMPhilThesisGuided" property="rejectDate" /></b></div></td>
													</logic:equal>
													</tr>
					
					<logic:equal value="false" name="empPhdMPhilThesisGuided" property="isPhdMPhilThesisGuided">
											
											<tr>
											
											
												<td width="20%" height="25" class="row-odd">
												<div align="left"><bean:message
													key="knowledgepro.employee.title.of.thesis" /></div>
												</td>
												<td width="30%" class="row-even"><nested:textarea property="title" cols="40" rows="1"
													onclick="len_display(this,1000,'long_lenPhdMPhilThesisGuided','charCountPhdMPhilThesisGuided')"
													onkeypress="return imposeMaxLength(this, 0, 999);" onchange="return imposeMaxLength(this, 0, 999);"
													onkeyup="len_display(this,1000,'long_lenPhdMPhilThesisGuided','charCountPhdMPhilThesisGuided')" 
													 ></nested:textarea></td>
													
													<td width="20%" height="25" class="row-odd">
												<div align="left"><bean:message
													key="knowledgepro.admin.mail.subject" /></div>
												</td>
												<%
											String subject = "subject_" + count;
											%>
												<td width="30%" class="row-even"><nested:text
													property="subject" styleId="<%=subject%>" size="40" maxlength="100" onclick="charCountDisplay()" > </nested:text></td>
												
											</tr>
											<tr>
												<td width="20%" class="row-odd">
												<div align="left"><bean:message
													key="employee.institution.university" /></div>
												</td>
												<td width="30%" height="25" class="row-even"><nested:text
													property="companyInstitution" size="40" maxlength="250" ></nested:text></td>
												
												<td width="20%" height="25" class="row-odd">
												<div align="left"><bean:message
													key="employee.name.student" /></div>
												</td>
												
												<td width="30%" class="row-even"><nested:text
													property="nameStudent" styleId="nameStudent" size="40" maxlength="100" ></nested:text></td>
											</tr>
											<tr>
												<td width="20%" class="row-odd">
												<div align="left"><bean:message
													key="knowledgepro.employee.place" /></div>
												</td>
												<td width="30%" height="25" class="row-even"><nested:text
													property="place" size="40" maxlength="100" ></nested:text></td>
											
											
												<td width="20%" class="row-odd">
												<div align="left"><bean:message
													key="knowledgepro.employee.monthYear" /></div>
												</td>
												<td width="30%" height="25" class="row-even"><nested:text
													property="monthYear" size="40" maxlength="20" ></nested:text></td>
											</tr>
											<tr>

														<td width="20%" class="row-odd">
														<div align="left"><span class="Mandatory">*</span><bean:message
															key="knowledgepro.fee.academicyear" /></div>
														</td>
													<%String phdYearId = "YOP8" + count; %>
														
													<td width="16%" class="row-even"><c:set var="dyopid1"><%=phdYearId%></c:set>
														<nested:select property="academicYear" styleClass="comboLarge" styleId='<%=phdYearId %>'>
															<option value=""><bean:message
																key="knowledgepro.admin.select" /></option>
															<cms:renderEmpResearchYear></cms:renderEmpResearchYear>
														</nested:select> <script type="text/javascript">
																var yopid= '<nested:write property="academicYear"/>';
																document.getElementById("<c:out value='${dyopid1}'/>").value = yopid;
															</script>
													</td>
													
														<%--<td width="20%" class="row-odd">
														<div align="left"><span class="Mandatory">*</span>Guided/Adjudicated</div>
														</td>
														<td width="30%" height="25" class="row-even">
                								 			<nested:select property="guidedAbudjicated" styleId="guidedAbudjicated" styleClass="comboLarge">
  	   				 										<html:option value="">- Select -</html:option>
  	   														<html:option value="Guided">Guided </html:option>
								   							<html:option value="Adjudicated">Adjudicated</html:option>
   			   												</nested:select>
												</td> --%>
												<td width="20%" class="row-odd">
														<div align="left"><span class="Mandatory">*</span><bean:message
															key="knowledgepro.employee.type" /></div>
														</td>
												<td width="30%" height="25" class="row-even">
                								 			<nested:select property="type" styleId="type" styleClass="comboLarge">
  	   				 										<html:option value="">- Select -</html:option>
  	   														<html:option value="PhD">PhD </html:option>
								   							<html:option value="Mphil">Mphil</html:option>
   			   												</nested:select>
												</td>
											</tr>
											<%-- <tr>
												<td width="20%" class="row-odd">
														<div align="left"><span class="Mandatory">*</span><bean:message
															key="knowledgepro.employee.type" /></div>
														</td>
														<td width="30%" height="25" class="row-even">
                								 			<nested:select property="type" styleId="type" styleClass="comboLarge">
  	   				 										<html:option value="">- Select -</html:option>
  	   														<html:option value="PhD">PhD </html:option>
								   							<html:option value="Mphil">Mphil</html:option>
   			   												</nested:select>
												</td>
												</tr>--%>
											</logic:equal>
										</nested:iterate>
									</logic:notEmpty>
								</tr>
								 <tr>
			                         	<td class="row-even" colspan="4"><div>
										<table width="100%" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td height="25" align="center">
											<logic:notEmpty property="empPhdMPhilThesisGuidedTO"
										name="EmpResPubDetailsForm">
											<html:button property="" styleClass="formbutton" value="Send For Approval" onclick="saveEmpResPubDetails('PhdMPhilThesisGuided')"></html:button>&nbsp;&nbsp;
											</logic:notEmpty>
											&nbsp;<html:submit property="" styleClass="formbutton" value="Add more"  styleId="addMore" onclick="submitEmployeeInfoAdd('resetPhdMPhilThesisGuided','ExpAddMore'); return false;"></html:submit>
										<c:if test="${EmpResPubDetailsForm.empPhdThesisGuidedLength>0}">
				                        		<html:submit property="" styleClass="formbutton" value="Remove"  styleId="addMore" onclick="submitEmployeeInfoAdd('removePhdMPhilThesisGuided','ExpAddMore'); return false;"></html:submit>
				                        	</c:if></td>
										</tr>
										</table>
									</div></td>
											
											
											
										</tr>
										
										

									</table>

									</td>
								</tr>
								
		
		<tr class="tdIMG" height="35px">
			<td>
				<div align="left" style="border: none; font-size: 13px; color: #006600"><b> <bean:message
										key="employee.own.phd.mphil.thesis" /></b></div>
			</td>
 								<td align="right"> <div class="arrow"></div></td>
								</tr>
				
				<tr class="data" id="OwnPhdMPilThesis">
     				<td>
     				<table width="100%" cellspacing="1" cellpadding="2">
								<tr>
										<td colspan="4" align="right"><div align="right" id="charCountOwnPhdMPilThesis"><input type="text" id="long_lenOwnPhdMPilThesis" class="len" size="2" readonly="readonly" style="border: none; text-align: right; font-size: 11px; font-family: Verdana, Arial, Helvetica, sans-serif"> Characters Remaining</div></td>
										</tr>
								<tr class="row-odd">
									<logic:notEmpty property="empOwnPhdMPilThesisTO"
										name="EmpResPubDetailsForm">
										<nested:iterate property="empOwnPhdMPilThesisTO"
											name="EmpResPubDetailsForm" id="empOwnPhdMPilThesis" indexId="count">
											<tr>
													<td><div style="border: none; font-size: 10px; color: #000000"><b>Sl No:- <c:out value="${count + 1}"/></b></div></td>
													<td><div style="border: none; font-size: 10px; color: #000000"><b>Submitted Date:- 
													<bean:write name="empOwnPhdMPilThesis" property="entryCreatedate" /></b></div></td>
													
													<logic:equal value="true" name="empOwnPhdMPilThesis" property="isRejected">
													<td><FONT color="red"><b>Rejected</b></FONT></td> 
													<td><div style="border: none; font-size: 10px; color: red"><b>Rejected Date:- 
													<bean:write name="empOwnPhdMPilThesis" property="rejectDate" /></b></div></td>
													</logic:equal>
													</tr>
						
						<logic:equal value="false" name="empOwnPhdMPilThesis" property="isOwnPhdMphilThesis">
											<!-- <tr>
													
														<td width="20%" height="25" class="row-odd">
														<div align="left"><bean:message
															key="knowledgepro.employee.Rejection.Reason" /></div>
														</td>
														<td width="30%" class="row-even" colspan="3"><nested:text
															property="rejectReason" 
															size="100" maxlength="100" disabled="true"></nested:text></td>
													
													</tr>-->
											<tr>
											
											
												<td width="20%" height="25" class="row-odd">
												<div align="left"><bean:message
													key="knowledgepro.employee.title.of.thesis" /></div>
												</td>
												<td width="30%" class="row-even"><nested:textarea
													property="title" cols="40" rows="1"
													onclick="len_display(this,1000,'long_lenOwnPhdMPilThesis','charCountOwnPhdMPilThesis')"
													onkeypress="return imposeMaxLength(this, 0, 999);" onchange="return imposeMaxLength(this, 0, 999);" 
													onkeyup="len_display(this,1000,'long_lenOwnPhdMPilThesis','charCountOwnPhdMPilThesis')" ></nested:textarea></td>
													
													<td width="20%" height="25" class="row-odd">
												<div align="left"><bean:message
													key="knowledgepro.admin.mail.subject" /></div>
												</td>
												<%
											String subject = "subject_" + count;
											%>
												<td width="30%" class="row-even"><nested:text
													property="subject" styleId="<%=subject%>" size="40" maxlength="100" onclick="charCountDisplay()" ></nested:text></td>
												
											</tr>
											<tr>
												<td width="20%" class="row-odd">
												<div align="left"><bean:message
													key="employee.institution.university" /></div>
												</td>
												<td width="30%" height="25" class="row-even"><nested:text
													property="companyInstitution" size="40" maxlength="250" ></nested:text></td>
												
												<td width="20%" height="25" class="row-odd">
												<div align="left"><bean:message
													key="employee.name.guide" /></div>
												</td>
												
												<td width="30%" class="row-even"><nested:text
													property="nameGuide" styleId="nameGuide" size="40" maxlength="100" ></nested:text></td>
											</tr>
											<tr>
												<td width="20%" class="row-odd">
												<div align="left"><bean:message
													key="knowledgepro.employee.place" /></div>
												</td>
												<td width="30%" height="25" class="row-even"><nested:text
													property="place" size="40" maxlength="100" ></nested:text></td>
											
											
												<td width="20%" class="row-odd">
												<div align="left"><bean:message
													key="employee.month.year.defense" /></div>
												</td>
												<td width="30%" height="25" class="row-even"><nested:text
													property="monthYear" size="40" maxlength="20" ></nested:text></td>
											</tr>
												<tr>

														<td width="20%" class="row-odd">
														<div align="left"><span class="Mandatory">*</span><bean:message
															key="knowledgepro.fee.academicyear" /></div>
														</td>
														<%String mphilyearId = "YOP9" + count; %>
														
													<td width="16%" class="row-even"><c:set var="dyopid1"><%=mphilyearId%></c:set>
														<nested:select property="academicYear" styleClass="comboLarge" styleId='<%=mphilyearId %>'>
															<option value=""><bean:message
																key="knowledgepro.admin.select" /></option>
															<cms:renderEmpResearchYear></cms:renderEmpResearchYear>
														</nested:select> <script type="text/javascript">
																var yopid= '<nested:write property="academicYear"/>';
																document.getElementById("<c:out value='${dyopid1}'/>").value = yopid;
															</script>
													</td>
													<td width="20%" class="row-odd">
														<div align="left"><span class="Mandatory">*</span>Date of Submission (dd/mm/yyyy)</div>
														</td>
														<%
															String styleDateown = "submissionDate_" + count;
														%>
														<td width="30%" height="25" class="row-even"><span class="star"><nested:text
															property="submissionDate" styleId="<%=styleDateown%>"
															size="20" maxlength="20"></nested:text> <script
															language="JavaScript">
														new tcal( {
														// form name
														'formname' :'EmpResPubDetailsForm',
														// input name
														'controlname' :'<%=styleDateown%>'
															});
														</script></span></td>
											</tr>
											<tr>
												<td width="20%" class="row-odd">
														<div align="left"><span class="Mandatory">*</span><bean:message
															key="knowledgepro.employee.type" /></div>
														</td>
														<td width="30%" height="25" class="row-even">
                								 			<nested:select property="type" styleId="type" styleClass="comboLarge">
  	   				 										<html:option value="">- Select -</html:option>
  	   														<html:option value="PhD">PhD </html:option>
								   							<html:option value="Mphil">Mphil</html:option>
   			   												</nested:select>
															</td>
													</tr>
							</logic:equal>													
										</nested:iterate>
									</logic:notEmpty>
								</tr>
								 <tr>
			                         	
										<td class="row-even" colspan="4"><div>
										<table width="100%" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td height="25" align="center">
											<logic:notEmpty property="empOwnPhdMPilThesisTO"
										name="EmpResPubDetailsForm">
											<html:button property="" styleClass="formbutton" value="Send For Approval" onclick="saveEmpResPubDetails('OwnPhdMPilThesis')"></html:button>&nbsp;&nbsp;
											</logic:notEmpty>
											&nbsp;
											<html:submit property="" styleClass="formbutton" value="Add more"  styleId="addMore" onclick="submitEmployeeInfoAdd('resetOwnPhdMPilThesis','ExpAddMore'); return false;"></html:submit>
										<c:if test="${EmpResPubDetailsForm.empOwnPhdThesisLength>0}">
				                        		<html:submit property="" styleClass="formbutton" value="Remove"  styleId="addMore" onclick="submitEmployeeInfoAdd('removeOwnPhdMPilThesis','ExpAddMore'); return false;"></html:submit>
				                   </c:if></td>
										</tr>
										</table>
									</div></td>
							</tr>
					</table>
     		    </td>
			</tr>
			<tr class="studentrow-odd">
				<td colspan="2">
					<div align="center" style="border: none; font-size: 13px;"><b> Others </b></div>
			    </td>
								 
			</tr>
			
		<tr class="tdIMG" height="35px">
									<td>
									<div align="left" style="border: none; font-size: 13px; color: #006600"><b> <bean:message
										key="employee.blog" /></b></div>
									</td>
								 <td align="right"> <div class="arrow"></div></td>
								</tr>
								<tr class="data" id="Blog">
									<td>
									<table width="100%" cellspacing="1" cellpadding="2">
										<tr>
										<td colspan="4" align="right"><div align="right" id="charCountBlog"><input type="text" id="long_lenBlog" class="len" size="2" readonly="readonly" style="border: none; text-align: right; font-size: 11px; font-family: Verdana, Arial, Helvetica, sans-serif"> Characters Remaining</div></td>
										</tr>
										<tr class="row-odd">
											<logic:notEmpty property="empBlogTO"
												name="EmpResPubDetailsForm">
												<nested:iterate property="empBlogTO"
													name="EmpResPubDetailsForm" id="empBlog" indexId="count">
													<tr>
													<td><div style="border: none; font-size: 10px; color: #000000"><b>Sl No:- <c:out value="${count + 1}"/></b></div></td>
													<td><div style="border: none; font-size: 10px; color: #000000"><b>Submitted Date:- 
													
													<logic:equal value="true" name="empBlog" property="isRejected">
													<td><FONT color="red"><b>Rejected</b></FONT></td> 
													<td><div style="border: none; font-size: 10px; color: red"><b>Rejected Date:- 
													<bean:write name="empBlog" property="rejectDate" /></b></div></td>
													</logic:equal>
													</tr>
									 
									
									 <logic:equal value="false" name="empBlog" property="isBlog">
													
													<tr>

														<%
															String language = "language_" + count;
														%>
														<td width="20%" height="25" class="row-odd">
														<div align="left"><bean:message
															key="knowledgepro.employee.title" /></div>
														</td>
														<td width="30%" class="row-even"><nested:textarea
															property="title" cols="40" rows="2"
															onclick="len_display(this,1000,'long_lenBlog','charCountBlog')"
															onkeypress="return imposeMaxLength(this, 0, 999);"
															onchange="return imposeMaxLength(this, 0, 999);"
															 onkeyup="len_display(this,1000,'long_lenBlog','charCountBlog')" ></nested:textarea></td>
														<td width="20%" height="25" class="row-odd">
														<div align="left"><bean:message
															key="employee.info.language.title" /></div>
														</td>
														<td width="30%" class="row-even"><nested:text property="language"
															styleId="<%=language%>" size="40" maxlength="50" ></nested:text></td>
													</tr>
													<tr>
														<td width="20%" class="row-odd">
														<div align="left"><bean:message
															key="knowledgepro.employee.number.comments" /></div>
														</td>
														<td width="30%" height="25" class="row-even"><nested:text
															property="numberOfComments" size="40" maxlength="10" onkeypress="charCountDisplay()" ></nested:text></td>

														<td width="20%" class="row-odd">
														<div align="left"><bean:message
															key="knowledgepro.employee.monthYear" /></div>
														</td>
														<td width="30%" height="25" class="row-even"><nested:text
															property="monthYear" size="40" maxlength="20" ></nested:text></td>


													</tr>
													<tr>
														<td width="20%" class="row-odd">
														<div align="left"><bean:message
															key="knowledgepro.admin.selectedSubjects" /></div>
														</td>
														<td width="30%" height="25" class="row-even"><nested:text
															property="subject" size="40" maxlength="100" ></nested:text></td>

														<td width="20%" class="row-odd">
														<div align="left"><bean:message
															key="knowledgepro.employee.url" /></div>
														</td>
														<td width="30%" height="25" class="row-even"><nested:text
															property="url" size="40" maxlength="200"></nested:text></td>


													</tr>
													<tr>
														<td width="20%" class="row-odd">
														<div align="left"><span class="Mandatory">*</span><bean:message
															key="knowledgepro.fee.academicyear" /></div>
														</td>
														<%String blogyearId = "YOP10" + count; %>
														
													<td width="16%" class="row-even"><c:set var="dyopid1"><%=blogyearId%></c:set>
														<nested:select property="academicYear" styleClass="comboLarge" styleId='<%=blogyearId %>'>
															<option value=""><bean:message
																key="knowledgepro.admin.select" /></option>
															<cms:renderEmpResearchYear></cms:renderEmpResearchYear>
														</nested:select> <script type="text/javascript">
																var yopid= '<nested:write property="academicYear"/>';
																document.getElementById("<c:out value='${dyopid1}'/>").value = yopid;
															</script>
													</td>
														
												
													</tr>
									</logic:equal>

												</nested:iterate>
											</logic:notEmpty>
										</tr>
										<tr>
										<td class="row-even" colspan="4"><div>
										<table width="100%" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td height="25" align="center">
											<logic:notEmpty property="empBlogTO"
												name="EmpResPubDetailsForm">
											<html:button property="" styleClass="formbutton" value="Send For Approval" onclick="saveEmpResPubDetails('Blog')"></html:button>&nbsp;&nbsp;
											</logic:notEmpty>
											&nbsp;<html:submit
												property="" styleClass="formbutton" value="Add more"
												styleId="addMore"
												onclick="submitEmployeeInfoAdd('resetBlog','ExpAddMore'); return false;"></html:submit>
											<c:if test="${EmpResPubDetailsForm.empBlogLength>0}">
												<html:submit property="" styleClass="formbutton"
													value="Remove" styleId="addMore"
													onclick="submitEmployeeInfoAdd('removeBlog','ExpAddMore'); return false;"></html:submit>
											</c:if></td>
										</tr>
										</table>
									</div></td>
											
											
											
										</tr>
										
										

									</table>

									</td>
								</tr>

<tr class="tdIMG" height="35px">
									<td>
									<div align="left" style="border: none; font-size: 13px; color: #006600"><b> <bean:message
										key="employee.films" /></b></div>
									</td>
								 <td align="right"> <div class="arrow"></div></td>
								</tr>
								<tr class="data" id="FilmVideosDoc">
							<td>
							<table width="100%" cellspacing="1" cellpadding="2">
								<tr>
										<td colspan="4" align="right"><div align="right" id="charCountFilmVideosDoc"><input type="text" id="long_lenFilmVideosDoc" class="len" size="2" readonly="readonly" style="border: none; text-align: right; font-size: 11px; font-family: Verdana, Arial, Helvetica, sans-serif"> Characters Remaining</div></td>
										</tr>
								<tr class="row-odd">
									<logic:notEmpty property="empFilmVideosDocTO"
										name="EmpResPubDetailsForm">
										<nested:iterate property="empFilmVideosDocTO"
											name="EmpResPubDetailsForm" id="empFilmVideosDoc" indexId="count">
											<tr>
													<td><div style="border: none; font-size: 10px; color: #000000"><b>Sl No:- <c:out value="${count + 1}"/></b></div></td>
													<td><div style="border: none; font-size: 10px; color: #000000"><b>Submitted Date:- 
													<bean:write name="empFilmVideosDoc" property="entryCreatedate" /></b></div></td>
													
													<logic:equal value="true" name="empFilmVideosDoc" property="isRejected">
													<td><FONT color="red"><b>Rejected</b></FONT></td> 
													<td><div style="border: none; font-size: 10px; color: red"><b>Rejected Date:- 
													<bean:write name="empFilmVideosDoc" property="rejectDate" /></b></div></td>
													</logic:equal>
													</tr>
							
						
						<logic:equal value="false" name="empFilmVideosDoc" property="isFilmVideoDoc">
											
											<tr>
											
											
												<td width="20%" height="25" class="row-odd">
												<div align="left"><bean:message
													key="knowledgepro.employee.title" /></div>
												</td>
												<td width="30%" class="row-even"><nested:textarea
													property="title" cols="40" rows="2"
													onclick="len_display(this,1000,'long_lenFilmVideosDoc','charCountFilmVideosDoc')"
												    onkeypress="return imposeMaxLength(this, 0, 999);" 
												    onchange="return imposeMaxLength(this, 0, 999);"
												    onkeyup="len_display(this,1000,'long_lenFilmVideosDoc','charCountFilmVideosDoc')" ></nested:textarea></td>
													
													<td width="20%" height="25" class="row-odd">
												<div align="left"><bean:message
													key="knowledgepro.employee.subtitles" /></div>
												</td>
												
												<td width="30%" class="row-even"><nested:text
													property="subtitles" styleId="subtitles" size="40" maxlength="100" onkeypress="charCountDisplay()" ></nested:text></td>
												
											</tr>
											<tr>
												<td width="20%" class="row-odd">
												<div align="left"><bean:message
													key="knowledgepro.employee.genre" /></div>
												</td>
												<td width="30%" height="25" class="row-even"><nested:text
													property="genre" size="40" maxlength="100" ></nested:text></td>
												
												<td height="25" class="row-odd">
												<div align="left"><bean:message
													key="knowledgepro.employee.credits" /></div>
												</td>
												
												<td width="20%" class="row-even"><nested:text
													property="credits" styleId="credits" size="40" maxlength="100" ></nested:text></td>
											</tr>
											<tr>
												<td width="20%" class="row-odd">
												<div align="left"><bean:message
													key="knowledgepro.employee.running.time" /></div>
												</td>
												<td width="30%" class="row-even"><nested:text
													property="runningTime" styleId="runningTime" size="40" maxlength="10" ></nested:text></td>
											
												<td width="20%" class="row-odd">
												<div align="left"><bean:message
													key="knowledgepro.employee.disc.format" /></div>
												</td>
												<td width="30%" height="25" class="row-even"><nested:text
													property="discFormat" size="40" maxlength="10" ></nested:text></td>
											</tr>
											<tr>
												
												<td width="20%" class="row-odd">
												<div align="left"><bean:message	key="knowledgepro.employee.technical.format" /></div>
												</td>
												<td width="30%" height="25" class="row-even"><nested:text
													property="technicalFormat" size="40" maxlength="40" ></nested:text></td>
													
												<td width="20%" class="row-odd">
												<div align="left"><bean:message	key="knowledgepro.employee.audio.format" /></div>
												</td>
												<td width="30%" height="25" class="row-even"><nested:text
													property="audioFormat" size="40" maxlength="20" ></nested:text></td>	
											</tr>
											<tr>	
											
												<td width="20%" class="row-odd">
												<div align="left"><bean:message	key="knowledgepro.employee.aspect.ratio" /></div>
												</td>
												<td width="30%" height="25" class="row-even"><nested:text
													property="aspectRatio" size="40" maxlength="30" ></nested:text></td>
												
												<td width="20%" class="row-odd">
												<div align="left"><bean:message	key="knowledgepro.employee.producer" /></div>
												</td>
												<td width="30%" height="25" class="row-even"><nested:text
													property="producer" size="40" maxlength="100" ></nested:text></td>
											</tr>
											<tr>	
											
												<td width="20%" class="row-odd">
												<div align="left"><bean:message	key="knowledgepro.employee.copyright" /></div>
												</td>
												<td width="30%" height="25" class="row-even"><nested:text
													property="copyrights" size="40" maxlength="150" ></nested:text></td>
														<td width="20%" class="row-odd">
														<div align="left"><span class="Mandatory">*</span><bean:message
															key="knowledgepro.fee.academicyear" /></div>
														</td>
														
														<%String filmyearId = "YOP11" + count; %>
														
													<td width="16%" class="row-even"><c:set var="dyopid1"><%=filmyearId%></c:set>
														<nested:select property="academicYear" styleClass="comboLarge" styleId='<%=filmyearId %>'>
															<option value=""><bean:message
																key="knowledgepro.admin.select" /></option>
															<cms:renderEmpResearchYear></cms:renderEmpResearchYear>
														</nested:select> <script type="text/javascript">
																var yopid= '<nested:write property="academicYear"/>';
																document.getElementById("<c:out value='${dyopid1}'/>").value = yopid;
															</script>
													</td>
											</tr>
							</logic:equal>
										</nested:iterate>
									</logic:notEmpty>
								</tr>
								 <tr>
			                         	<td class="row-even" colspan="4"><div>
										<table width="100%" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td height="25" align="center">
											<logic:notEmpty property="empFilmVideosDocTO"
										name="EmpResPubDetailsForm">
											<html:button property="" styleClass="formbutton" value="Send For Approval" onclick="saveEmpResPubDetails('FilmVideosDoc')"></html:button>&nbsp;&nbsp;
											</logic:notEmpty>
											&nbsp;<html:submit property="" styleClass="formbutton" value="Add more"  styleId="addMore" onclick="submitEmployeeInfoAdd('resetFilmVideosDoc','ExpAddMore'); return false;"></html:submit>
										<c:if test="${EmpResPubDetailsForm.empFilmVideosDocLength>0}">
				                        		<html:submit property="" styleClass="formbutton" value="Remove"  styleId="addMore" onclick="submitEmployeeInfoAdd('removeFilmVideosDoc','ExpAddMore'); return false;"></html:submit>
				                        	</c:if></td>
										</tr>
										</table>
									</div></td>
											
											
											
										</tr>
										
										

									</table>

									</td>
								</tr>
	<tr class="tdIMG" height="35px">
	    <td>
		<div align="left" style="border: none; font-size: 13px; color: #006600"><b> <bean:message	key="employee.seminars.organised" /></b></div>
		</td>
 		<td align="right"> <div class="arrow"></div></td>
		</tr>
		<tr class="data" id="SeminarsOrganised">
				<td>
							<table width="100%" cellspacing="1" cellpadding="2">
								<tr>
										<td colspan="4" align="right"><div align="right" id="charCountSeminarsOrganized"><input type="text" id="long_lenSeminarsOrganized" class="len" size="2" readonly="readonly" style="border: none; text-align: right; font-size: 11px; font-family: Verdana, Arial, Helvetica, sans-serif"> Characters Remaining</div></td>
										</tr>
								<tr class="row-odd">
									<logic:notEmpty property="empSeminarsOrganizedTO"
										name="EmpResPubDetailsForm">
										<nested:iterate property="empSeminarsOrganizedTO"
											name="EmpResPubDetailsForm" id="empSeminarsOrganized" indexId="count">
											<tr>
													<td><div style="border: none; font-size: 10px; color: #000000"><b>Sl No:- <c:out value="${count + 1}"/></b></div></td>
													<td><div style="border: none; font-size: 10px; color: #000000"><b>Submitted Date:- 
													<bean:write name="empSeminarsOrganized" property="entryCreatedate" /></b></div></td>
													<logic:equal value="true" name="empSeminarsOrganized" property="isRejected">
													<td><FONT color="red"><b>Rejected</b></FONT></td> 
													<td><div style="border: none; font-size: 10px; color: red"><b>Rejected Date:- 
													<bean:write name="empSeminarsOrganized" property="rejectDate" /></b></div></td>
													</logic:equal>
													</tr>
					<logic:equal value="false" name="empSeminarsOrganized" property="isSeminarOrganized">
									
											<tr>
												<td width="20%" height="25" class="row-odd">
												<div align="left"><bean:message
													key="employee.name.organisers" /></div>
												</td>
													<%
													String nameOrganisers = "nameOrganisers_" + count;
														%>
												<td width="30%" class="row-even"><nested:text
													property="nameOrganisers" styleId="<%=nameOrganisers%>" size="40" maxlength="200" ></nested:text></td>
												
												<td width="20%" height="25" class="row-odd">
												<div align="left"><bean:message
													key="employee.name.conference.seminar" /></div>
												</td>
												<td width="30%" class="row-even"><nested:text
													property="nameConferencesSeminar" size="40" maxlength="200" ></nested:text></td>
											</tr>
											<tr>
												<td width="20%" height="25" class="row-odd">
												<div align="left"><bean:message
													key="employee.resourse.persons.details" /></div>
												</td>
												<td width="30%" class="row-even"><nested:text
													property="resoursePerson" size="40" maxlength="200" ></nested:text></td>
												
												<td width="20%" height="25" class="row-odd">
														<div align="left"><bean:message
															key="employee.info.language.title" /></div>
														</td>

														<td width="30%" class="row-even"><nested:text property="language"
															styleId="language" size="40" maxlength="50" ></nested:text></td>
												
												
												
											</tr>
											<tr>
											<td width="20%" class="row-odd">
												<div align="left"><bean:message
													key="knowledgepro.employee.place" /></div>
												</td>
												<td width="30%" height="25" class="row-even"><nested:text
													property="place" size="40" maxlength="100" ></nested:text></td>
											
											
												<td width="20%" class="row-odd">
														<div align="left">Date,Month and Year (dd/mm/yyyy)</div>
														</td>
														<%
															String styleDate4 = "dateMonthYear1_" + count;
														%>
														<td width="30%" height="25" class="row-even"><span class="star"><nested:text
															property="dateMonthYear" styleId="<%=styleDate4%>"
															size="20" maxlength="20"></nested:text> <script
															language="JavaScript">
														new tcal( {
														// form name
														'formname' :'EmpResPubDetailsForm',
														// input name
														'controlname' :'<%=styleDate4%>'
															});
														</script></span></td>
														
														

											</tr>
											<tr>
																							
												<td width="20%" height="25" class="row-odd">
												<div align="left"><bean:message
													key="employee.instution.sponsors.department" /></div>
												</td>
												
												<td width="30%" class="row-even"><nested:text
													property="sponsors" styleId="sponsors" size="40" maxlength="100" ></nested:text></td>
											<td width="20%" class="row-odd">
														<div align="left"><span class="Mandatory">*</span><bean:message
															key="knowledgepro.fee.academicyear" /></div>
														</td>
													<%String osyearId = "YOP12" + count; %>
														
													<td width="16%" class="row-even"><c:set var="dyopid1"><%=osyearId%></c:set>
														<nested:select property="academicYear" styleClass="comboLarge" styleId='<%=osyearId %>'>
															<option value=""><bean:message
																key="knowledgepro.admin.select" /></option>
															<cms:renderEmpResearchYear></cms:renderEmpResearchYear>
														</nested:select> <script type="text/javascript">
																var yopid= '<nested:write property="academicYear"/>';
																document.getElementById("<c:out value='${dyopid1}'/>").value = yopid;
															</script>
													</td>
											</tr>
											</logic:equal>
								
										</nested:iterate>
									</logic:notEmpty>
								</tr>
								 <tr>
			                         	<td class="row-even" colspan="4"><div>
										<table width="100%" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td height="25" align="center">
											<logic:notEmpty property="empSeminarsOrganizedTO"
										name="EmpResPubDetailsForm">
											<html:button property="" styleClass="formbutton" value="Send For Approval" onclick="saveEmpResPubDetails('SeminarsOrganized')"></html:button>&nbsp;&nbsp;
											</logic:notEmpty>
											&nbsp;<html:submit property="" styleClass="formbutton" value="Add more"  styleId="addMore" onclick="submitEmployeeInfoAdd('resetSeminarsOrganised','ExpAddMore'); return false;"></html:submit>
										<c:if test="${EmpResPubDetailsForm.empSeminarsOrganizedLength>0}">
				                        		<html:submit property="" styleClass="formbutton" value="Remove"  styleId="addMore" onclick="submitEmployeeInfoAdd('removeSeminarsOrganised','ExpAddMore'); return false;"></html:submit>
				                        	</c:if></td>
										</tr>
										</table>
									</div></td>
											
											
											
										</tr>
										
										

									</table>

									</td>
								</tr>
								
								
		<!-- -   -------------------------------new changes in jsp  -----------------------------       -->						
								
								
			
			
			
				<tr class="tdIMG" height="35px">
	    <td>
		<div align="left" style="border: none; font-size: 13px; color: #006600"><b> <bean:message	key="employee.workshops.trainings.attended" /></b></div>
		</td>
 		<td align="right"> <div class="arrow"></div></td>
		</tr>
		<tr class="data" id="WorkshopsAttended">
				<td>
							<table width="100%" cellspacing="1" cellpadding="2">
								<tr>
										<td colspan="4" align="right"><div align="right" id="charCountWorkshops"><input type="text" id="long_lenWorkshops" class="len" size="2" readonly="readonly" style="border: none; text-align: right; font-size: 11px; font-family: Verdana, Arial, Helvetica, sans-serif"> Characters Remaining</div></td>
										</tr>
								<tr class="row-odd">
									<logic:notEmpty property="empWorkshopsTO"
										name="EmpResPubDetailsForm">
										<nested:iterate property="empWorkshopsTO"
											name="EmpResPubDetailsForm" id="empWorkshopsAttended" indexId="count">
											<tr>
													<td><div style="border: none; font-size: 10px; color: #000000"><b>Sl No:- <c:out value="${count + 1}"/></b></div></td>
													<td><div style="border: none; font-size: 10px; color: #000000"><b>Submitted Date:- 
													<bean:write name="empWorkshopsAttended" property="entryCreatedate" /></b></div></td>
													<logic:equal value="true" name="empWorkshopsAttended" property="isRejected">
													<td><FONT color="red"><b>Rejected</b></FONT></td> 
													<td><div style="border: none; font-size: 10px; color: red"><b>Rejected Date:- 
													<bean:write name="empWorkshopsAttended" property="rejectDate" /></b></div></td>
													</logic:equal>
													</tr>
				
					<logic:equal value="false" name="empWorkshopsAttended" property="isWorkshopTraining">
										
											
											<tr>
												<td width="20%" height="25" class="row-odd">
												<div align="left"><bean:message
													key="knowledgepro.pgm.organisedBy" /></div>
												</td>
													
												<td width="30%" class="row-even"><nested:text
													property="organisedBy" size="40" maxlength="200" ></nested:text></td>
												
												<td width="20%" height="25" class="row-odd">
												<div align="left"><bean:message
													key="knowledgepro.employee.Name.program" /></div>
												</td>
												<td width="30%" class="row-even"><nested:text
													property="nameOfPgm" size="40" maxlength="200" ></nested:text></td>
											</tr>
											<tr>
												<td width="20%" height="25" class="row-odd">
												<div align="left"><bean:message
													key="knowledgepro.date.pgm" /> (dd/mm/yyyy)</div>
												</td>
												<%	String styleDate9 = "dateOfPgm_" + count;%>
														<td width="30%" height="25" class="row-even"><span class="star"><nested:text
															property="dateOfPgm" styleId="<%=styleDate9%>"
															size="20" maxlength="20"></nested:text> <script
															language="JavaScript">
														new tcal( {
														// form name
														'formname' :'EmpResPubDetailsForm',
														// input name
														'controlname' :'<%=styleDate9%>'
															});
														</script></span></td>
												
												
												<td width="20%" height="25" class="row-odd">
														<div align="left"><bean:message
															key="knowledgepro.duration.pgm" /></div>
														</td>

														<td width="30%" class="row-even"><nested:text property="duration"
															styleId="duration" size="40" maxlength="10"  ></nested:text></td>
											</tr>
											<tr>

														<td width="20%" class="row-odd">
														<div align="left"><span class="Mandatory">*</span><bean:message
															key="knowledgepro.fee.academicyear" /></div>
														</td>
													<%String wtyearId = "YOP13" + count; %>
														
													<td width="16%" class="row-even"><c:set var="dyopid1"><%=wtyearId%></c:set>
														<nested:select property="academicYear" styleClass="comboLarge" styleId='<%=wtyearId %>'>
															<option value=""><bean:message
																key="knowledgepro.admin.select" /></option>
															<cms:renderEmpResearchYear></cms:renderEmpResearchYear>
														</nested:select> <script type="text/javascript">
																var yopid= '<nested:write property="academicYear"/>';
																document.getElementById("<c:out value='${dyopid1}'/>").value = yopid;
															</script>
													</td>
														<td width="20%" class="row-odd">
														<div align="left"><span class="Mandatory">*</span><bean:message
															key="knowledgepro.employee.level" /></div>
														</td>
														<td width="30%" height="25" class="row-even">
														
														<input type="hidden" id="WorkshopsType" name="WorkshopsType" value="<bean:write name="empWorkshopsAttended" property="type"/>"/>
                								 			<nested:select property="type" styleId="type" styleClass="comboLarge">
  	   				 										<html:option value="">- Select -</html:option>
  	   														<html:option value="Institutional">Institutional </html:option>
								   							<html:option value="Regional">Regional</html:option>
															<html:option value="National">National </html:option>
								   							<html:option value="International">International</html:option>
   			   												</nested:select>
														
														
														<!--<nested:text
															property="type" size="40" maxlength="10" onkeypress="return isNumberKey(event)"></nested:text>-->
															</td>
													</tr>
											
											</logic:equal>
						
						
										</nested:iterate>
									</logic:notEmpty>
								</tr>
								 <tr>
			                         	<td class="row-even" colspan="4"><div>
										<table width="100%" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td height="25" align="center">
											<logic:notEmpty property="empWorkshopsTO"
										name="EmpResPubDetailsForm">
											<html:button property="" styleClass="formbutton" value="Send For Approval" onclick="saveEmpResPubDetails('WorkshopsAttended')"></html:button>&nbsp;&nbsp;
											</logic:notEmpty>
											&nbsp;<html:submit property="" styleClass="formbutton" value="Add more"  styleId="addMore" onclick="submitEmployeeInfoAdd('resetWorkshopsAttended','ExpAddMore'); return false;"></html:submit>
										<c:if test="${EmpResPubDetailsForm.empWorkshopsAttendedLength>0}">
				                        		<html:submit property="" styleClass="formbutton" value="Remove"  styleId="addMore" onclick="submitEmployeeInfoAdd('removeWorkshopsAttended','ExpAddMore'); return false;"></html:submit>
				                        	</c:if></td>
										</tr>
										</table>
									</div></td>
											
											
											
										</tr>
										
										

									</table>

									</td>
								</tr>
			<tr class="tdIMG" height="35px">
	    <td>
		<div align="left" style="border: none; font-size: 13px; color: #006600"><b> <bean:message	key="employee.research.awards.achiewemnets" /></b></div>
		</td>
 		<td align="right"> <div class="arrow"></div></td>
		</tr>
		<tr class="data" id="AwardsAchievements">
				<td>
							<table width="100%" cellspacing="1" cellpadding="2">
								<tr>
										<td colspan="4" align="right"><div align="right" id="charCountAwards"><input type="text" id="long_lenAwards" class="len" size="2" readonly="readonly" style="border: none; text-align: right; font-size: 11px; font-family: Verdana, Arial, Helvetica, sans-serif"> Characters Remaining</div></td>
										</tr>
								<tr class="row-odd">
									<logic:notEmpty property="empAwardsAchievementsOthersTO"
										name="EmpResPubDetailsForm">
										<nested:iterate property="empAwardsAchievementsOthersTO"
											name="EmpResPubDetailsForm" id="empAwardsAchievementsOthers" indexId="count">
											<tr>
													<td><div style="border: none; font-size: 10px; color: #000000"><b>Sl No:- <c:out value="${count + 1}"/></b></div></td>
													<td><div style="border: none; font-size: 10px; color: #000000"><b>Submitted Date:- 
													<bean:write name="empAwardsAchievementsOthers" property="entryCreatedate" /></b></div></td>
													
													<logic:equal value="true" name="empAwardsAchievementsOthers" property="isRejected">
													<td><FONT color="red"><b>Rejected</b></FONT></td> 
													<td><div style="border: none; font-size: 10px; color: red"><b>Rejected Date:- 
													<bean:write name="empAwardsAchievementsOthers" property="rejectDate" /></b></div></td>
													</logic:equal>
													</tr>
					<logic:equal value="false" name="empAwardsAchievementsOthers" property="isAwardsAchievementsOthers">
											<tr>
												<td width="20%" height="25" class="row-odd">
												<div align="left">Name of Awards/Achievements/Others</div>
												</td>
												<td width="30%" class="row-even"><nested:text
													property="name" size="40" maxlength="200" ></nested:text></td>
													
													<td width="20%" height="25" class="row-odd">
												<div align="left">Place</div>
												</td>
													
												<td width="30%" class="row-even"><nested:text
													property="place" size="40" maxlength="100" ></nested:text></td>
											</tr>
											<tr>
												<td width="20%" height="25" class="row-odd">
												<div align="left">Description</div>
												</td>
												<td width="30%" height="25" class="row-even" align="left"><nested:textarea property="description" cols="40" rows="2" onclick="len_display(this,500,'long_lenAwards','charCountAwards')"
															onkeypress="return imposeMaxLength(this, 0, 499);" 
															onchange="return imposeMaxLength(this, 0, 499);"
															onkeyup="len_display(this,500,'long_lenAwards','charCountAwards')" ></nested:textarea>
															</td>
												
												<td width="20%" height="25" class="row-odd">
														<div align="left">Month and Year</div>
														</td>

												<td width="30%" class="row-even"><nested:text property="monthYear"
															styleId="monthYear" size="40" maxlength="20" ></nested:text></td>
											</tr>
											
										
											
											
											<tr>

														<td width="20%" class="row-odd">
														<div align="left"><span class="Mandatory">*</span><bean:message
															key="knowledgepro.fee.academicyear" /></div>
														</td>
														<%String awdyearId = "YOP14" + count; %>
														
													<td width="16%" class="row-even"><c:set var="dyopid1"><%=awdyearId%></c:set>
														<nested:select property="academicYear" styleClass="comboLarge" styleId='<%=awdyearId %>'>
															<option value=""><bean:message
																key="knowledgepro.admin.select" /></option>
															<cms:renderEmpResearchYear></cms:renderEmpResearchYear>
														</nested:select> <script type="text/javascript">
																var yopid= '<nested:write property="academicYear"/>';
																document.getElementById("<c:out value='${dyopid1}'/>").value = yopid;
															</script>
													</td>
													<td width="20%" class="row-odd">
														<div align="left"><span class="Mandatory">*</span>Organisation /Institution Awarded</div>
														</td>
														<td width="30%" height="25" class="row-even">
														<nested:text property="organisationAwarded" size="40" maxlength="300"></nested:text>															</td>
													</tr>
											
											</logic:equal>
										</nested:iterate>
									</logic:notEmpty>
								</tr>
								 <tr>
			                         	<td class="row-even" colspan="4"><div>
										<table width="100%" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td height="25" align="center">
											<logic:notEmpty property="empAwardsAchievementsOthersTO"
										name="EmpResPubDetailsForm">
											<html:button property="" styleClass="formbutton" value="Send For Approval" onclick="saveEmpResPubDetails('AwardsAchievements')"></html:button>&nbsp;&nbsp;
											</logic:notEmpty>
											&nbsp;<html:submit property="" styleClass="formbutton" value="Add more"  styleId="addMore" onclick="submitEmployeeInfoAdd('resetAwardsAchievements','ExpAddMore'); return false;"></html:submit>
										<c:if test="${EmpResPubDetailsForm.empAwardsLength>0}">
				                        		<html:submit property="" styleClass="formbutton" value="Remove"  styleId="addMore" onclick="submitEmployeeInfoAdd('removeAwardsAchievements','ExpAddMore'); return false;"></html:submit>
				                        	</c:if></td>
										</tr>
										</table>
									</div></td>
										</tr>
									</table>
									</td>
								</tr>
		</table>
		</td>
	  </tr>
	</table>
					
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


if(document.getElementById("charCountResearchProject")!=null)
	document.getElementById("charCountResearchProject").style.display="none";
	if(document.getElementById("charCountBooksMonographs")!=null)
	document.getElementById("charCountBooksMonographs").style.display="none";
	if(document.getElementById("charCountArticleJournals")!=null)
	document.getElementById("charCountArticleJournals").style.display="none";
	if(document.getElementById("charCountChapterArticlBook")!=null)
	document.getElementById("charCountChapterArticlBook").style.display="none";
	if(document.getElementById("charCountBlog")!=null)
	document.getElementById("charCountBlog").style.display="none";
	if(document.getElementById("charCountFilmVideosDoc")!=null)
	document.getElementById("charCountFilmVideosDoc").style.display="none";
	if(document.getElementById("charCountConferencePresentation")!=null)
	document.getElementById("charCountConferencePresentation").style.display="none";
	if(document.getElementById("charCountInvitedTalk")!=null)
	document.getElementById("charCountInvitedTalk").style.display="none";
	if(document.getElementById("charCountCasesNotesWorking")!=null)
	document.getElementById("charCountCasesNotesWorking").style.display="none";
	if(document.getElementById("charCountSeminarsOrganized")!=null)
	document.getElementById("charCountSeminarsOrganized").style.display="none";
	if(document.getElementById("charCountPhdMPhilThesisGuided")!=null)
	document.getElementById("charCountPhdMPhilThesisGuided").style.display="none";
	if(document.getElementById("charCountOwnPhdMPilThesis")!=null)
	document.getElementById("charCountOwnPhdMPilThesis").style.display="none";
	if(document.getElementById("charCountWorkshops")!=null)
	document.getElementById("charCountWorkshops").style.display="none";
	if(document.getElementById("charCountSeminarsAttended")!=null)
	document.getElementById("charCountSeminarsAttended").style.display="none";
	if(document.getElementById("charCountAwards")!=null)
	document.getElementById("charCountAwards").style.display="none";
</script>