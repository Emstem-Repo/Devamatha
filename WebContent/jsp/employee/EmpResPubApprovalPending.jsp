<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@page import="java.util.Map,java.util.HashMap"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<link rel="stylesheet" href="jquery/development-bundle/demos/demos.css">
<script src="jquery/development-bundle/jquery-1.7.1.js"></script>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<LINK REL=StyleSheet HREF= "css/styles.css" TYPE="text/css">
<script type="text/javascript">
	function cancelAction() {
		document.location.href = "empResPubPendApproval.do?method=initEmpResPendList";
	}

	function saveEmpResPubDetails(id,name){
		var x=window.confirm("Are you sure, you want to approve the details. Press 'Ok' if u want to Approve, else press Cancel ");
		if(x)
		{
		document.getElementById("method").value="saveEmpResPubDetails";
		document.getElementById("selectedId").value=id;
		document.getElementById("submitName").value=name;
		document.EmpResPubPendApprovalForm.submit();
		}
	}
	function RejectAction(id,name){
		var x=window.confirm("Are you sure, you want to 'Reject' the details. Press 'Ok' if u want to Reject, else press Cancel ");
		if(x)
		{
			RejectReason(id,name);
		}
	}
	function RejectReason(id,name){
		 var reason=prompt("Please enter the reason for Rejection:");
		if(reason!=null && reason!="")
		{
			document.getElementById("selectedId").value=id;
			document.getElementById("rejectReason").value=reason;
			document.getElementById("isReject").value="true";
			document.getElementById("method").value="saveEmpResPubDetails";
			document.getElementById("submitName").value=name;
			document.EmpResPubPendApprovalForm.submit();
		}else
		{
			RejectAction(id,name);
		}
		
	}

		 function setButtonDisplay(){
		        var data= document.getElementById("selectedCategory").value;
		        if(data=="mainScreen")
		        {
		        	
		        	document.getElementById("showbuttons").style.display =block;
		        	document.getElementById("buttons").style.display =none;

		        }
		        else if(data=="" || data==null)
		        {
		        	
		        	document.getElementById("showbuttons").style.display =none;
		        	document.getElementById("buttons").style.display =block;
		        	
		        }
		        }

		 function getOtherConferencePreseantation(valueSelected,count){
				alert(valueSelected);
				alert(count);
				alert(document.getElementById("typeOfPgm_"+ count).value);
				other=document.getElementById("typeOfPgm_"+ count).value;
				if(other!=null && other!="" && other=="Other"){
					document.getElementById("other_"+count).style.display="block";
				}else{
					document.getElementById("other_"+count).style.display="none";
				}
			}
			function getOtherArticlePerodicals(valueSelected, count){
				alert(valueSelected);
				alert(count);
				alert(document.getElementById("type_"+ count).value);
				other=document.getElementById("type_"+ count).value;
				 if(other!=null && other!="" && other=="Other"){
						document.getElementById("otherTextArticle_" + count).style.display="block";
				}else{
						document.getElementById("otherTextArticle_" + count).style.display="none";
				}
			}

		

	function submitEmployeeInfoAdd(method,mode){
		document.getElementById("method").value=method;
		document.getElementById("mode").value=mode;
		document.EmpResPubPendApprovalForm.submit();
	}
        $(document).ready(function(){
            $("#report1 .tdIMG").addClass("odd");
            $("#report1 .data").show();
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
	    	}    
       });
    </script>

<body onload="setButtonDisplay()">
<html:form action="/empResPubPendApproval" method="post">
	<html:hidden property="formName" value="EmpResPubPendApprovalForm" />
	<html:hidden property="pageType" value="1" />
	<html:hidden property="method" value="" styleId="method" />
	<html:hidden property="mode" styleId="mode" value="" />
	<html:hidden property="submitName" styleId="submitName" value="" />
	<html:hidden property="focusValue" styleId="focusValue"/>
	<html:hidden property="rejectReason" styleId="rejectReason" value=""/>
	<html:hidden property="isReject" styleId="isReject" value=""/>
	<html:hidden property="selectedId" styleId="selectedId" value=""/>
	<table width="100%" border="0">
		<tr>
			<td><span class="Bredcrumbs">Research and Publications-Pending Approval <span
				class="Bredcrumbs">&gt;&gt; Research and Publications-Pending Approval
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
									<div align="left" style="border:ridge 5px #d0e4fe; font-size: 11px;">
									1. Please find the Research and publication submitted by the faculty.<br>
									2. Click on the Category to view the respective details and click on the approve button.<br>
									3. Kindly approve the details submitted. Any corrections can be incorporated.<br>
									4. Once approved, the message and mail will be sent to the faculty.<br>
									3. Click on the 'Arrow Button' near Category Name to close or collapse the category details
									</div>
									</td>
								</tr>
							<tr>
									<td colspan="2">
									<table width="100%" height="22" border="0" cellpadding="0">
							<tr class="row-even" style="border:ridge 5px #d0e4fe; ">
											<td width="25%" height="30" align="center" class="row-even" style="border:ridge 5px #d0e4fe;"><img
												src='<%=request.getContextPath()%>/ResearchPhotoServlet'
												height="100Px" width="100Px" /></td>
								
							    
								<td class="row-even" width="50%">
								<table width="100%" height="100%" border="0" cellpadding="0">
							<tr style="border:ridge 5px #d0e4fe; ">
								<td class="row-odd" style="border:ridge 5px #d0e4fe;">
									 <bean:message key="knowledgepro.admin.name"/></td>
								<td width="25%" align="left" class="row-even" style="border:ridge 5px #d0e4fe;"><bean:write name="EmpResPubPendApprovalForm" property="empName" />
									 
								</td>
							</tr>
							<tr style="border:ridge 5px #d0e4fe; ">
								<td width="25%"  align="left" class="row-odd" style="border:ridge 5px #d0e4fe;">
									 <bean:message key="knowledgepro.admin.employeeid"/></td>
						        <td width="25%" align="left"  class="row-even" style="border:ridge 5px #d0e4fe;"><bean:write name="EmpResPubPendApprovalForm" property="fingerprintId" /></td>
							</tr>
							<tr style="border:ridge 5px #d0e4fe; ">
									
								<td width="25%" align="left" class="row-odd" style="border:ridge 5px #d0e4fe;"><bean:message key="knowledgepro.employee.Department"/></td>
										 <td width="25%"  align="left" class="row-even" style="border:ridge 5px #d0e4fe;"><bean:write name="EmpResPubPendApprovalForm" property="empDepartment" /> 
									  
									</td>
							</tr>
									</table>
									</td>
								</tr>
								</table>
								</td>
									
								</tr>
							
								<tr class="tdIMG" height="35px">
									<td>
									<div align="left" style="border: none; font-size: 13px; color: #006600"><b> <bean:message
										key="employee.research.project" /></b></div>
									</td>
								 <td align="right"> <div class="arrow"></div></td>
								</tr>
								<tr class="data" id="ResearchResearch">
									<td>
									<table width="100%" cellspacing="1" cellpadding="2">
										<tr class="row-odd">
										<%int slnocount = 0; %>
											<logic:notEmpty property="empResearchProjectTO"
												name="EmpResPubPendApprovalForm">
												<nested:iterate property="empResearchProjectTO"
													name="EmpResPubPendApprovalForm" id="empResearchProject"
													indexId="count">
								<logic:equal value="false" name="empResearchProject" property="isApproved">
													 <%slnocount = slnocount + 1; %>
													<tr>
													<td><div style="border: none; font-size: 10px; color: #000000"><b>Sl No:- <%= slnocount %></b></div></td>
													<td><div style="border: none; font-size: 10px; color: #000000"><b>Submitted Date:- 
													<bean:write name="empResearchProject" property="entryCreatedate" /></b></div></td>
													</tr>
													<tr>
														<%
															String investigators = "investigators_" + count;
														%>
														<td width="20%" height="25" class="row-odd">
														<div align="left"><bean:message
															key="knowledgepro.employee.title" /></div>
														</td>
														<td height="20" class="row-even" colspan="3"><nested:textarea
															property="title" cols="90" rows="1"
															onkeypress="maxlength(this, 499);"></nested:textarea></td>
												</tr>
												<tr>
														<td width="20%" class="row-odd">
														<div align="left"><bean:message
															key="knowledgepro.employee.abstract.objectives" /></div>
														</td>
														<td  height="20" class="row-even" colspan="3"><nested:textarea
															property="abstractObjectives" cols="90" rows="1"
															onkeypress="maxlength(this, 1999);"></nested:textarea></td>
													</tr>
													<tr>
														<td width="20%" height="25" class="row-odd">
														<div align="left"><bean:message
															key="knowledgepro.employee.investigarors" /></div>
														</td>
														<td width="30%" class="row-even"><nested:text
															property="investigators" styleId="<%=investigators%>"
															size="40" maxlength="100"></nested:text></td>


														<td width="20%" class="row-odd">
														<div align="left"><bean:message
															key="knowledgepro.employee.sponsors" /></div>
														</td>
														<td width="30%"  height="25" class="row-even"><nested:text
															property="sponsors" size="40" maxlength="100"></nested:text></td>

													</tr>
													<tr>

														<td width="20%" class="row-odd">
														<div align="left"><bean:message
															key="knowledgepro.fee.academicyear" /></div>
														</td>
														<td width="30%" height="25" class="row-even">
															<%String resYearId="res"+count; %>
															<c:set var="resid"><%=resYearId %></c:set>
                								 			<nested:select property="academicYear" styleId='<%=resYearId %>' styleClass="comboLarge">
  	   				 										<html:option value="">- Select -</html:option>
  	   														<cms:renderEmployeeYear></cms:renderEmployeeYear>
   			   												</nested:select>
   			   												<script type="text/javascript">
																var opid= '<nested:write property="academicYear"/>';
																if(opid!=0)
																document.getElementById("<c:out value='${resid}'/>").value = opid;
															</script>
														</td>
														<td width="20%" class="row-odd">
														<div align="left">Internal/External</div>
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
														<div align="left">Amount Granted</div>
														</td>
														<td width="30%"  height="25" class="row-even"><nested:text
															property="amountGranted" size="40" maxlength="100" onkeypress="return isNumberKey(event)"></nested:text></td>
													
														<td width="20%" height="25" class="row-odd">
														<div align="left"><bean:message
															key="knowledgepro.employee.approver.comment" /></div>
														</td>
														<td width="30%" class="row-even" colspan="2"><nested:text
															property="approverComment" 
															size="40" maxlength="100"></nested:text></td>
													</tr>
								</logic:equal>
								<logic:equal value="false" property="selectedCategory" name="EmpResPubPendApprovalForm">
									<tr>	
									  <td class="row-even" colspan="4">
										<table width="100%" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td height="25" align="center">
											<input type="button" style="formbutton" value="Approve" onclick="saveEmpResPubDetails('<nested:write property="id" name="empResearchProject"/>','ResearchProject')"/>&nbsp;&nbsp;
											&nbsp;&nbsp;&nbsp;<input type="button" style="formbutton"  value="Reject" onclick="RejectAction('<nested:write property="id" name="empResearchProject"/>','ResearchProject')"/>
											</td>
										</tr>
										</table>
									   </td>
									</tr>
								</logic:equal>
								</nested:iterate>
								</logic:notEmpty>	
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
										<tr class="row-odd">
											<logic:notEmpty property="empBooksMonographsTO"
												name="EmpResPubPendApprovalForm">
												<%int slnocount1 = 0; %>
												<nested:iterate property="empBooksMonographsTO"
													name="EmpResPubPendApprovalForm" id="empBooksMonographs"
													indexId="count">
													
									<logic:equal value="false" name="empBooksMonographs" property="isApproved" >
													<tr>
													 <%slnocount1 = slnocount1 + 1; %>
													<td><div style="border: none; font-size: 10px; color: #000000"><b>Sl No:- <%= slnocount1 %></b></div></td>
													<td><div style="border: none; font-size: 10px; color: #000000"><b>Submitted Date:- 
													<bean:write name="empBooksMonographs" property="entryCreatedate" /></b></div></td>
													</tr>
						
													
													<tr>


														<td width="20%" height="25" class="row-odd">
														<div align="left"><bean:message
															key="knowledgepro.employee.title.of.Book" /></div>
														</td>
														<td width="30%" class="row-even"><nested:textarea
															property="title" cols="40" rows="2"
															onkeypress="maxlength(this, 499);"></nested:textarea></td>

														<td width="20%"height="25" class="row-odd">
														<div align="left"><bean:message
															key="knowledgepro.employee.author.name" /></div>
														</td>
														<%
															String authorName = "authorName_" + count;
														%>
														<td width="30%" class="row-even"><nested:text
															property="authorName" styleId="<%=authorName%>" size="30"
															maxlength="100"></nested:text></td>

													</tr>
													<tr>

														<td width="20%" height="25" class="row-odd">
														<div align="left"><bean:message
															key="employee.info.language.title" /></div>
														</td>

														<td width="30%" class="row-even"><nested:text property="language"
															styleId="language" size="40" maxlength="50"></nested:text></td>

														<td width="20%" class="row-odd">
														<div align="left"><bean:message
															key="knowledgepro.employee.total.pages" /></div>
														</td>
														<td width="30%" height="25" class="row-even"><nested:text
															property="totalPages" size="40" maxlength="10"></nested:text></td>

													</tr>
													<tr>
														<td width="20%" class="row-odd">
														<div align="left"><bean:message
															key="knowledgepro.employee.isbn" /></div>
														</td>
														<td width="30%" height="25" class="row-even"><nested:text
															property="isbn" size="40" maxlength="50"></nested:text></td>

														<td width="20%" class="row-odd">
														<div align="left"><bean:message
															key="knowledgepro.employee.publishing.company" /></div>
														</td>
														<td width="30%" height="25" class="row-even"><nested:text
															property="companyInstitution" size="40" maxlength="250"></nested:text></td>

													</tr>
													<tr>

														<td width="20%" class="row-odd">
														<div align="left"><bean:message
															key="knowledgepro.employee.place.publishing" /></div>
														</td>
														<td width="30%" height="25" class="row-even"><nested:text
															property="placePublication" size="40" maxlength="100"></nested:text></td>

														<td width="20%" class="row-odd">
														<div align="left"><bean:message
															key="knowledgepro.employee.monthYear" /></div>
														</td>
														<td width="30%" height="25" class="row-even"><nested:text
															property="monthYear" size="40" maxlength="20"></nested:text></td>

													</tr>
													<tr>

														<td width="20%" class="row-odd">
														<div align="left"><bean:message
															key="knowledgepro.fee.academicyear" /></div>
														</td>
														<td width="30%" height="25" class="row-even">
															<%String bkYearId="bk"+count; %>
															<c:set var="bkid"><%=bkYearId %></c:set>
                								 			<nested:select property="academicYear" styleId='<%=bkYearId %>' styleClass="comboLarge">
  	   				 										<html:option value="">- Select -</html:option>
  	   														<cms:renderEmployeeYear></cms:renderEmployeeYear>
   			   												</nested:select>
   			   												<script type="text/javascript">
																var opid= '<nested:write property="academicYear"/>';
																if(opid!=0)
																document.getElementById("<c:out value='${bkid}'/>").value = opid;
															</script>
														</td>
														<td width="20%" class="row-odd">
														<div align="left">Sole Author/Co-Author</div>
														</td>
														<td width="30%" height="25" class="row-even">
                								 			<nested:select property="coAuthored" styleId="coAuthored" styleClass="comboLarge">
  	   				 										<html:option value="">- Select -</html:option>
  	   														<html:option value="Sole">Sole</html:option>
								   							<html:option value="Co-Author">Co-Author</html:option>
   			   												</nested:select>
														</td>
													</tr>
													<tr>
														<td width="20%" height="25" class="row-odd">
														<div align="left"><bean:message
															key="knowledgepro.employee.approver.comment" /></div>
														</td>
														<td width="30%" class="row-even" colspan="2"><nested:text
															property="approverComment" 
															size="40" maxlength="100"></nested:text></td>
													</tr>
								</logic:equal>
								<logic:equal value="false" property="selectedCategory" name="EmpResPubPendApprovalForm">
									<tr>	
									  <td class="row-even" colspan="4">
										<table width="100%" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td height="25" align="center">
											<input type="button" style="formbutton" value="Approve" onclick="saveEmpResPubDetails('<nested:write property="id" name="empBooksMonographs"/>','BooksMonographs')"/>&nbsp;&nbsp;
											&nbsp;&nbsp;&nbsp;<input type="button" style="formbutton"  value="Reject" onclick="RejectAction('<nested:write property="id" name="empBooksMonographs"/>','BooksMonographs')"/>
											</td>
										</tr>
										</table>
									   </td>
									</tr>
								</logic:equal>	
							</nested:iterate>
									<!--<logic:equal value="false" property="selectedCategory" name="EmpResPubPendApprovalForm">
										<tr>	
										<td class="row-even" colspan="4">
										<table width="100%" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td height="25" align="center">
											<html:button property="" styleClass="formbutton" value="Approve" onclick="saveEmpResPubDetails('BooksMonographs')"></html:button>&nbsp;&nbsp;
											&nbsp;&nbsp;&nbsp;<html:button property="" styleClass="formbutton" value="Reject" onclick="RejectAction('BooksMonographs')"></html:button>
											</td>
										</tr>
										
									</table>

									</td>
								</tr>
								</logic:equal>-->
								</logic:notEmpty>
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
										<tr class="row-odd">
											<logic:notEmpty property="empArticleJournalsTO"
												name="EmpResPubPendApprovalForm">
												<%int slnocount2 = 0; %>
												<nested:iterate property="empArticleJournalsTO"
													name="EmpResPubPendApprovalForm" id="empArticleJournals"
													indexId="count">
						<logic:equal value="false" name="empArticleJournals" property="isApproved">
										 <%slnocount2 = slnocount2 + 1; %>
													<tr>
													<td><div style="border: none; font-size: 10px; color: #000000"><b>Sl No:- <%= slnocount2 %></b></div></td>
													<td><div style="border: none; font-size: 10px; color: #000000"><b>Submitted Date:- 
													<bean:write name="empArticleJournals" property="entryCreatedate" /></b></div></td>
													</tr>
													
													<tr>
														<td width="20%" height="25" class="row-odd">
														<div align="left">Title</div>
														</td>
														<td class="row-even"><nested:textarea
															property="title" cols="40" rows="1"
															onkeypress="maxlength(this, 499);"></nested:textarea></td>
														<td width="20%" height="25" class="row-odd">
														<div align="left"><bean:message
															key="knowledgepro.employee.article.department.name" /></div>
														</td>
													<td class="row-even" >
															<nested:text property="departmentInstitution" styleId="departmentInstitution" size="40"
															maxlength="100"></nested:text>
													</td>
												</tr><tr>
														<td width="15%" height="25" class="row-odd">
														<div align="left"><bean:message
															key="knowledgepro.employee.author.name" /></div>
														</td>
														<td width="20%" class="row-even"><nested:text
															property="authorName" styleId="authorName" size="40"
															maxlength="100"></nested:text></td>
												
														<td width="15%" class="row-odd">
														<div align="left"><bean:message
															key="knowledgepro.employee.name.of.journal" /></div>
														</td>
														<td width="20%" height="25" class="row-even"><nested:text
															property="nameJournal" size="40" maxlength="100"></nested:text></td>
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
															property="placePublication" size="40" maxlength="100"></nested:text></td>
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
															styleId="<%=language%>" size="40" maxlength="50"></nested:text></td>
															
														<td width="20%" class="row-odd">
														<div align="left">
														<bean:message key="knowledgepro.employee.date.monthYear.publication" /></div>
											</td>
											<% String styleDatePublished = "datePublished_" + count; %>
																		
																
												<td  class="row-even"><div align="left">
				                  					<nested:text  property="datePublished" styleId="<%=styleDatePublished %>" maxlength="20" ></nested:text>
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
															property="isbn" size="40" maxlength="50"></nested:text></td>
														
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
															property="pagesFrom" size="40" maxlength="10"></nested:text> 
														</td>

														 <td width="20%" class="row-odd">
														<div align="left"><bean:message
															key="knowledgepro.employee.pages.to" /></div>
														</td>
														<td width="30%" height="25" class="row-even"><nested:text
															property="pagesTo" size="40" maxlength="10"></nested:text></td>
												
														
													</tr>
													<tr>

														<td width="20%" class="row-odd">
														<div align="left"><bean:message
															key="knowledgepro.fee.academicyear" /></div>
														</td>
														<td width="30%" height="25" class="row-even">
															<%String ajYearId="aj"+count; %>
															<c:set var="ajid"><%=ajYearId %></c:set>
                								 			<nested:select property="academicYear" styleId='<%=ajYearId %>' styleClass="comboLarge">
  	   				 										<html:option value="">- Select -</html:option>
  	   														<cms:renderEmployeeYear></cms:renderEmployeeYear>
   			   												</nested:select>
   			   												<script type="text/javascript">
																var opid= '<nested:write property="academicYear"/>';
																if(opid!=0)
																document.getElementById("<c:out value='${ajid}'/>").value = opid;
															</script>
														</td>
														<td width="20%" class="row-odd">
														<div align="left"><bean:message
															key="knowledgepro.employee.type" /></div>
														</td>
														<td width="30%" height="25" class="row-even">
														
														<input type="hidden" id="JournalsType" name="JournalsType" value="<bean:write name="empArticleJournals" property="type"/>"/>
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
													<tr>
														<td width="20%" height="25" class="row-odd">
														<div align="left"><bean:message
															key="knowledgepro.employee.approver.comment" /></div>
														</td>
														<td width="30%" class="row-even" colspan="2"><nested:text
															property="approverComment" 
															size="40" maxlength="100"></nested:text></td>
													</tr>
									</logic:equal>
									<logic:equal value="false" property="selectedCategory" name="EmpResPubPendApprovalForm">
									<tr>	
									  <td class="row-even" colspan="4">
										<table width="100%" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td height="25" align="center">
											<input type="button" style="formbutton" value="Approve" onclick="saveEmpResPubDetails('<nested:write property="id" name="empArticleJournals"/>','ArticleJournals')"/>&nbsp;&nbsp;
											&nbsp;&nbsp;&nbsp;<input type="button" style="formbutton"  value="Reject" onclick="RejectAction('<nested:write property="id" name="empArticleJournals"/>','ArticleJournals')"/>
											</td>
										</tr>
										</table>
									   </td>
									</tr>
								</logic:equal>
									</nested:iterate>
										
								</logic:notEmpty>
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
										<tr class="row-odd">
											<logic:notEmpty property="empChapterArticlBookTO"
												name="EmpResPubPendApprovalForm">
												<%int slnocount3 = 0; %>
												<nested:iterate property="empChapterArticlBookTO"
													name="EmpResPubPendApprovalForm" id="empChapterArticlBook"
													indexId="count">
								<logic:equal value="false" name="empChapterArticlBook" property="isApproved">
													 <%slnocount3 = slnocount3 + 1; %>
													<tr>
													<td><div style="border: none; font-size: 10px; color: #000000"><b>Sl No:- <%= slnocount3 %></b></div></td>
													<td><div style="border: none; font-size: 10px; color: #000000"><b>Submitted Date:- 
													<bean:write name="empChapterArticlBook" property="entryCreatedate" /></b></div></td>
													</tr>
													
													<tr>
														<td width="20%" height="25" class="row-odd">
														<div align="left"><bean:message
															key="knowledgepro.employee.title.of.Book" /></div>
														</td>
														<td width="30%" class="row-even"><nested:textarea
															property="title" cols="40" rows="2"
															onkeypress="maxlength(this, 499);" ></nested:textarea></td>

														<td width="20%" height="25" class="row-odd">
														<div align="left"><bean:message
															key="knowledgepro.employee.author.name" /></div>
														</td>
														<%
															String authorName = "authorName_" + count;
														%>
														<td width="30%" class="row-even"><nested:text
															property="authorName" styleId="<%=authorName%>" size="40"
															maxlength="100" ></nested:text></td>

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
															property="pagesFrom" size="40" maxlength="10" ></nested:text></td>

														<td width="20%" class="row-odd">
														<div align="left"><bean:message
															key="knowledgepro.employee.pages.to" /></div>
														</td>
														<td width="30%" height="25" class="row-even"><nested:text
															property="pagesTo" size="40" maxlength="10" ></nested:text></td>
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
															property="totalPages" size="40" maxlength="10" ></nested:text></td>

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
															property="titleChapterArticle" cols="40" rows="2"
															onkeypress="maxlength(this, 299);" ></nested:textarea></td>

														<td width="20%" class="row-odd">
														<div align="left"><bean:message
															key="knowledgepro.employee.doi"/></div>
														</td>

														<td width="30%" height="25" class="row-even"><nested:text
															property="doi" size="40" maxlength="100" ></nested:text></td>
													</tr>
													<tr>

														<td width="20%" class="row-odd">
														<div align="left"><bean:message
															key="knowledgepro.fee.academicyear" /></div>
														</td>
														<td width="30%" height="25" class="row-even">
															<%String chpYearId="chp"+count; %>
															<c:set var="chpid"><%=chpYearId %></c:set>
                								 			<nested:select property="academicYear" styleId='<%=chpYearId %>' styleClass="comboLarge">
  	   				 										<html:option value="">- Select -</html:option>
  	   														<cms:renderEmployeeYear></cms:renderEmployeeYear>
   			   												</nested:select>
   			   												<script type="text/javascript">
																var opid= '<nested:write property="academicYear"/>';
																if(opid!=0)
																document.getElementById("<c:out value='${chpid}'/>").value = opid;
															</script>
														</td>
														<td width="20%" class="row-odd">
														<div align="left">Sole Author/Co-Author</div>
														</td>
														<td width="30%" height="25" class="row-even">
                								 			<nested:select property="coAuthored" styleId="coAuthored" styleClass="comboLarge">
  	   				 										<html:option value="">- Select -</html:option>
  	   														<html:option value="Sole">Sole</html:option>
								   							<html:option value="Co-Author">Co-Author</html:option>
   			   												</nested:select>
														</td>
													</tr>
													<tr>
														<td width="20%" height="25" class="row-odd">
														<div align="left"><bean:message
															key="knowledgepro.employee.approver.comment" /></div>
														</td>
														<td width="30%" class="row-even" colspan="2"><nested:text
															property="approverComment" 
															size="40" maxlength="100"></nested:text></td>
													</tr>
                              </logic:equal>
                             <logic:equal value="false" property="selectedCategory" name="EmpResPubPendApprovalForm">
                              <tr>
								<td class="row-even" colspan="4">
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
									  <tr>
											<td height="25" align="center">
											<input type="button" style="formbutton" value="Approve" onclick="saveEmpResPubDetails('<nested:write property="id" name="empChapterArticlBook"/>','ChapterArticlBook')"/>&nbsp;&nbsp;
											&nbsp;&nbsp;&nbsp;<input type="button" style="formbutton" value="Reject" onclick="RejectAction('<nested:write property="id" name="empChapterArticlBook"/>','ChapterArticlBook')"/>
											</td>
										</tr>
									</table>
									</td>
								</tr>
						</logic:equal>		
						</nested:iterate>
									
						</logic:notEmpty>
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
										<tr class="row-odd">
											<logic:notEmpty property="empArticlInPeriodicalsTO"
												name="EmpResPubPendApprovalForm">
												<%int slnocount6 = 0; %>
												<nested:iterate property="empArticlInPeriodicalsTO"
													name="EmpResPubPendApprovalForm" id="empArticlInPeriodicals"
													indexId="count">
									<logic:equal value="false" name="empArticlInPeriodicals" property="isApproved">
													 <%slnocount6 = slnocount6 + 1; %>
													<tr>
													<td><div style="border: none; font-size: 10px; color: #000000"><b>Sl No:- <%= slnocount6 %></b></div></td>
													<td><div style="border: none; font-size: 10px; color: #000000"><b>Submitted Date:- 
													<bean:write name="empArticlInPeriodicals" property="entryCreatedate" /></b></div></td>
													</tr>
							
													<tr>


														<td width="20%" height="25" class="row-odd">
														<div align="left"><bean:message
															key="knowledgepro.employee.title.of.article" /></div>
														</td>
														<td width="30%" class="row-even"><nested:textarea
															property="title" cols="40" rows="2"
															onkeypress="maxlength(this, 499);"></nested:textarea></td>

														<td width="20%" height="25" class="row-odd">
														<div align="left"><bean:message
															key="knowledgepro.employee.author.name" /></div>
														</td>
														<%
															String authorName = "authorName_" + count;
														%>
														<td width="30%" class="row-even"><nested:text
															property="authorName" styleId="<%=authorName%>" size="40"
															maxlength="100"></nested:text></td>

													</tr>
													<tr>
														<td width="20%" class="row-odd">
														<div align="left"><bean:message
															key="knowledgepro.employee.name.of.periodical" /></div>
														</td>
														<td width="30%" height="25" class="row-even"><nested:text
															property="namePeriodical" size="40" maxlength="100"></nested:text></td>

														<td width="20%" height="25" class="row-odd">
														<div align="left"><bean:message
															key="employee.info.language.title" /></div>
														</td>

														<td width="30%" class="row-even"><nested:text property="language"
															styleId="language" size="40" maxlength="50"></nested:text></td>
													</tr>
													<tr>
														<td width="20%" class="row-odd">
														<div align="left"><bean:message
															key="knowledgepro.employee.date.monthYear.publication" /></div>
														</td>
														<%
															String styleDate1 = "dateMonthYear_" + count;
														%>
														<td width="30%" height="25" class="row-even"><span class="star"><nested:text
															property="dateMonthYear" styleId="<%=styleDate1%>"
															size="40" maxlength="20"></nested:text> <script
															language="JavaScript">
														new tcal( {
														// form name
														'formname' :'EmpResPubPendApprovalForm',
														// input name
														'controlname' :'<%=styleDate1%>'
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
															property="isbn" size="40" maxlength="50"></nested:text></td>
													</tr>
													<tr>

														<td width="20%" class="row-odd">
														<div align="left"><bean:message
															key="knowledgepro.employee.pages.from" /></div>
														</td>
														<td width="30%" height="25" class="row-even"><nested:text
															property="pagesFrom" size="40" maxlength="10"></nested:text></td>

														<td width="20%" class="row-odd">
														<div align="left"><bean:message
															key="knowledgepro.employee.pages.to" /></div>
														</td>
														<td width="30%" height="25" class="row-even"><nested:text
															property="pagesTo" size="40" maxlength="10"></nested:text></td>
													</tr>
													<tr>

														<td width="20%" class="row-odd">
														<div align="left"><bean:message
															key="knowledgepro.fee.academicyear" /></div>
														</td>
														<td width="30%" height="25" class="row-even">
															<%String apYearId="ap"+count; %>
															<c:set var="apid"><%=apYearId %></c:set>
                								 			<nested:select property="academicYear" styleId='<%=apYearId %>' styleClass="comboLarge">
  	   				 										<html:option value="">- Select -</html:option>
  	   														<cms:renderEmployeeYear></cms:renderEmployeeYear>
   			   												</nested:select>
   			   												<script type="text/javascript">
																var opid= '<nested:write property="academicYear"/>';
																if(opid!=0)
																document.getElementById("<c:out value='${apid}'/>").value = opid;
															</script>
														</td>
														<td width="20%" class="row-odd">
														<div align="left"><bean:message
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
													<tr>
														<td width="20%" height="25" class="row-odd">
														<div align="left"><bean:message
															key="knowledgepro.employee.approver.comment" /></div>
														</td>
														<td width="30%" class="row-even" colspan="2"><nested:text
															property="approverComment" 
															size="40" maxlength="100"></nested:text></td>
													</tr>
													
								</logic:equal>
							<logic:equal value="false" property="selectedCategory" name="EmpResPubPendApprovalForm">
							<tr>
								<td class="row-even" colspan="4">
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
									  <tr>
											<td height="25" align="center">
											<input type="button" style="formbutton" value="Approve" onclick="saveEmpResPubDetails('<nested:write property="id" name="empArticlInPeriodicals"/>','ArticlInPeriodicals')"/>&nbsp;&nbsp;
											&nbsp;&nbsp;&nbsp;<input type="button" style="formbutton" value="Reject" onclick="RejectAction('<nested:write property="id" name="empArticlInPeriodicals"/>','ArticlInPeriodicals')"/>
											</td>
										</tr>
									</table>
									</td>
								</tr>
							</logic:equal>
							</nested:iterate>
							</logic:notEmpty>
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
								<tr class="row-odd">
									<logic:notEmpty property="empConferencePresentationTO"
										name="EmpResPubPendApprovalForm">
										<%int slnocount7 = 0; %>
										<nested:iterate property="empConferencePresentationTO"
											name="EmpResPubPendApprovalForm" id="empConferencePresentation" indexId="count">
								<logic:equal value="false" name="empConferencePresentation" property="isApproved">
											<%slnocount7 = slnocount7 + 1; %>
											<tr>
													<td><div style="border: none; font-size: 10px; color: #000000"><b>Sl No:- <%= slnocount7 %></b></div></td>
													<td><div style="border: none; font-size: 10px; color: #000000"><b>Submitted Date:- 
													<bean:write name="empConferencePresentation" property="entryCreatedate" /></b></div></td>
													</tr>
					
											
											<tr>
											
											
												<td width="20%" height="25" class="row-odd">
												<div align="left"><bean:message
													key="knowledgepro.employee.title.of.article" /></div>
												</td>
												<td width="30%" class="row-even"><nested:textarea
													property="title" cols="40" rows="2"
													onkeypress="maxlength(this, 499);"></nested:textarea></td>
													
													<td width="20%" height="25" class="row-odd">
												<div align="left"><bean:message
													key="KnowledgePro.employee.Names" /></div>
												</td>
												<%
											String nameTalksPresentation = "nameTalksPresentation_" + count;
											%>
												<td width="30%" class="row-even"><nested:text
													property="nameTalksPresentation" styleId="<%=nameTalksPresentation%>" size="40" maxlength="200"></nested:text></td>
												
											</tr>
											<tr>
												<td width="20%" height="25" class="row-odd">
											      <div align="left"><bean:message
															key="knowledgepro.conference.abstracts" /></div>
														</td>
												<td class="row-even" ><nested:textarea
													property="abstractDetails" cols="40" rows="2"
													onkeypress="maxlength(this, 499);"></nested:textarea></td>
													
												<td width="20%" height="25" class="row-odd">
												<div align="left"><bean:message
													key="employee.name.conference.seminar" /></div>
												</td>
												
												<td width="30%" class="row-even"><nested:text property="nameConferencesSeminar" 
												styleId="nameConferencesSeminar" size="40" maxlength="200"></nested:text></td>
											</tr>
											<tr>
												<td width="20%" class="row-odd">
												<div align="left"><bean:message
													key="knowledgepro.employee.place" /></div>
												</td>
												<td width="30%" height="25" class="row-even"><nested:text
													property="placePublication" size="40" maxlength="100"></nested:text></td>
											
											
												<td width="20%" class="row-odd">
												<div align="left">Date (dd/mm/yyyy)</div>
												</td>
												<% String styleDate23 = "monthYear_" + count;%>
												<td width="30%" class="row-even"><div align="left">
               								  	 <nested:text  property="monthYear" styleId="<%=styleDate23 %>" size="20" maxlength="20" ></nested:text>
                 							 		<script
														language="JavaScript">
														new tcal( {
														// form name
														'formname' :'EmpResPubPendApprovalForm',
														// input name
														'controlname' :'<%=styleDate23 %>'
														});
													</script></div>
                 							 	</td>
												<!-- <td width="30%" height="25" class="row-even"><nested:text
													property="monthYear" size="40" maxlength="20"></nested:text></td>-->
											</tr>
											<tr>

														<td width="20%" class="row-odd">
														<div align="left"><bean:message
															key="knowledgepro.fee.academicyear" /></div>
														</td>
														<td width="30%" height="25" class="row-even">
															<%String cpYearId="cp"+count; %>
															<c:set var="cpid"><%=cpYearId %></c:set>
                								 			<nested:select property="academicYear" styleId='<%=cpYearId %>' styleClass="comboLarge">
  	   				 										<html:option value="">- Select -</html:option>
  	   														<cms:renderEmployeeYear></cms:renderEmployeeYear>
   			   												</nested:select>
   			   												<script type="text/javascript">
																var opid= '<nested:write property="academicYear"/>';
																if(opid!=0)
																document.getElementById("<c:out value='${cpid}'/>").value = opid;
															</script>
														</td>
														<td width="20%" class="row-odd">
														<div align="left"><bean:message
															key="knowledgepro.employee.level" /></div>
														</td>
														<td width="30%" height="25" class="row-even">
														
														<input type="hidden" id="tempType" name="tempType" value="<bean:write name="empConferencePresentation" property="type"/>"/>
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

														<td width="30%" class="row-even"><nested:text property="language"
															styleId="language" size="40" maxlength="50"></nested:text></td>
												
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
									    String otherconf="otherTextConf_"+ count;
									%>
									     <div id="<%=other%>">
									     	<nested:text styleId="<%=otherconf%>" property="otherTextConf" size="40" maxlength="300"></nested:text>
									     </div>	
									    <script type="text/javascript">
												    		var valueSelected=document.getElementById("typeOfPgm_<c:out value='${count}'/>").value;
											    				 if(valueSelected!=null && valueSelected!="" && valueSelected=="Other"){
												    					document.getElementById("otherTextConf_<c:out value='${count}'/>").style.display="block";
												    			}else{
												    					document.getElementById("otherTextConf_<c:out value='${count}'/>").style.display="none";
												    			}
									 </script>	
									     </td>
   			   										
   			   										
   			   														
													
											</tr>
											<tr>			<td width="20%" height="25" class="row-odd">
														<div align="left"><bean:message
															key="knowledgepro.employee.approver.comment" /></div>
														</td>
														<td width="30%" class="row-even" colspan="2"><nested:text
															property="approverComment" 
															size="40" maxlength="100"></nested:text></td>
													
											</tr>
											
					</logic:equal>
					<logic:equal value="false" property="selectedCategory" name="EmpResPubPendApprovalForm">
					<tr>
								<td class="row-even" colspan="4">
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
									  <tr>
											<td height="25" align="center">
											<input type="button" style="formbutton" value="Approve" onclick="saveEmpResPubDetails('<nested:write property="id" name="empConferencePresentation"/>','ConferencePresentation')"/>&nbsp;&nbsp;
											&nbsp;&nbsp;&nbsp;<input type="button" style="formbutton" value="Reject" onclick="RejectAction('<nested:write property="id" name="empConferencePresentation"/>','ConferencePresentation')"/>
											</td>
										</tr>
									</table>
									</td>
								</tr>
					</logic:equal>										
				</nested:iterate>
    		</logic:notEmpty>
								</tr>
								 
					</table>

			</td>
	</tr>
	
	<tr class="tdIMG" height="35px">
									<td>
									<div align="left" style="border: none; font-size: 13px; color: #006600"><b> <bean:message
										key="employee.seminars.attended" /></b></div>
									</td>
								 <td align="right"> <div class="arrow"></div></td>
								</tr>
								<tr class="data" id="SeminarsAttended">
							<td>
							<table width="100%" cellspacing="1" cellpadding="2">
								<tr class="row-odd">
									<logic:notEmpty property="empConferenceSeminarsAttendedTO"
										name="EmpResPubPendApprovalForm">
										<%int slnocount5 = 0; %>
										<nested:iterate property="empConferenceSeminarsAttendedTO"
											name="EmpResPubPendApprovalForm" id="empConferenceSeminarsAttended" indexId="count">
							<logic:equal value="false" name="empConferenceSeminarsAttended" property="isApproved">
											 <%slnocount5 = slnocount5 + 1; %>
											<tr>
													<td><div style="border: none; font-size: 10px; color: #000000"><b>Sl No:- <%= slnocount5 %></b></div></td>
													<td><div style="border: none; font-size: 10px; color: #000000"><b>Submitted Date:- 
													<bean:write name="empConferenceSeminarsAttended" property="entryCreatedate" /></b></div></td>
													</tr>
							
											
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
													key="knowledgepro.date.pgm" /></div>
												</td>
														
														<%
															String styleDate12 = "dateOfPgm1_" + count;
														%>
														
												
												<td width="30%" class="row-even"><div align="left">
                  <nested:text  property="dateOfPgm" styleId="<%=styleDate12 %>" maxlength="20" ></nested:text>
                  <script
											language="JavaScript">
												new tcal( {
												// form name
												'formname' :'EmpResPubPendApprovalForm',
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
												'formname' :'EmpResPubPendApprovalForm',
												// input name
												'controlname' :'<%=styleDate22 %>'
												});
					</script></div>
                  </td>
											</tr>
											<tr>

														<td width="20%" class="row-odd">
														<div align="left"><bean:message
															key="knowledgepro.fee.academicyear" /></div>
														</td>
														
														<td width="30%" height="25" class="row-even">
															<%String csYearId="cs"+count; %>
															<c:set var="csid"><%=csYearId %></c:set>
                								 			<nested:select property="academicYear" styleId='<%=csYearId %>' styleClass="comboLarge">
  	   				 										<html:option value="">- Select -</html:option>
  	   														<cms:renderEmployeeYear></cms:renderEmployeeYear>
   			   												</nested:select>
   			   												<script type="text/javascript">
																var opid= '<nested:write property="academicYear"/>';
																if(opid!=0)
																document.getElementById("<c:out value='${csid}'/>").value = opid;
															</script>
														</td>
														
														
														<td width="20%" class="row-odd">
														<div align="left"><bean:message
															key="knowledgepro.employee.level" /></div>
														</td>
														<td width="30%" height="25" class="row-even">
														
														<input type="hidden" id="AttendedType" name="AttendedType" value="<bean:write name="empConferenceSeminarsAttended" property="type"/>"/>
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
															key="knowledgepro.employee.approver.comment" /></div>
														</td>
														<td width="30%" class="row-even" colspan="2"><nested:text
															property="approverComment" 
															size="40" maxlength="100"></nested:text></td>
											</tr>
							</logic:equal>
						<logic:equal value="false" property="selectedCategory" name="EmpResPubPendApprovalForm">
						<tr>
								<td class="row-even" colspan="4">
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
									  <tr>
											<td height="25" align="center">
											<input type="button" style="formbutton" value="Approve" onclick="saveEmpResPubDetails('<nested:write property="id" name="empConferenceSeminarsAttended"/>','SeminarsAttended')"/>&nbsp;&nbsp;
											&nbsp;&nbsp;&nbsp;<input type="button" style="formbutton" value="Reject" onclick="RejectAction('<nested:write property="id" name="empConferenceSeminarsAttended"/>','SeminarsAttended')"/>
											</td>
										</tr>
									</table>
									</td>
								</tr>	
								</logic:equal>
					</nested:iterate>
				</logic:notEmpty>
			</tr>
		</table>
	</td>
 </tr>
	
	<tr class="tdIMG" height="35px">
				   <td>
					<div align="left" style="border: none; font-size: 13px; color: #006600"><b> <bean:message	key="employee.invited.talk" /></b></div>
					</td>
 						<td align="right"> <div class="arrow"></div></td>
						</tr>
						<tr class="data" id="InvitedTalk">
					<td>
							<table width="100%" cellspacing="1" cellpadding="2">
								<tr class="row-odd">
									<logic:notEmpty property="empInvitedTalkTO"
										name="EmpResPubPendApprovalForm">
										<%int slnocount7 = 0; %>
										<nested:iterate property="empInvitedTalkTO"
											name="EmpResPubPendApprovalForm" id="empInvitedTalk" indexId="count">
								<logic:equal value="false" name="empInvitedTalk" property="isApproved">
											<%slnocount7 = slnocount7 + 1; %>
											<tr>
													<td><div style="border: none; font-size: 10px; color: #000000"><b>Sl No:- <%= slnocount7 %></b></div></td>
													<td><div style="border: none; font-size: 10px; color: #000000"><b>Submitted Date:- 
													<bean:write name="empInvitedTalk" property="entryCreatedate" /></b></div></td>
													</tr>
					
											
											<tr>
												<td width="20%" height="25" class="row-odd">
												<div align="left"><bean:message
													key="knowledgepro.employee.title.invitedTalks" /></div>
												</td>
												<td width="30%" class="row-even" colspan="3"><nested:textarea
													property="title" cols="80" rows="1"
													onkeypress="maxlength(this, 499);"></nested:textarea></td>
													
													<!-- <td width="20%" height="25" class="row-odd">
												<div align="left"><bean:message
													key="KnowledgePro.employee.Names" /></div>
												</td>
												<%
											String nameTalksPresentation = "nameTalksPresentation_" + count;
											%>
												<td width="30%" class="row-even"><nested:text
													property="nameTalksPresentation" styleId="<%=nameTalksPresentation%>" size="40" maxlength="200">
													</nested:text></td>-->
												
											</tr>
											<tr>
												<td width="20%" class="row-odd">
												<div align="left"><bean:message
													key="employee.institution.radio.tv" /></div>
												</td>
												<td width="30%" height="25" class="row-even"><nested:text
													property="companyInstitution" size="40" maxlength="250"></nested:text></td>
												
												<td width="20%" height="25" class="row-odd">
												<div align="left">Name Of Program</div>
												</td>
												
												<td width="30%" class="row-even" ><nested:text property="nameOfPgm" 
												styleId="nameOfPgm" size="40" maxlength="200"></nested:text></td>
												
												<!--  <td width="20%" height="25" class="row-odd">
												<div align="left"><bean:message
													key="employee.name.conference.seminar" /></div>
												</td>
												
												<td width="30%" class="row-even"><nested:text property="nameConferencesSeminar" 
												styleId="nameConferencesSeminar" size="40" maxlength="200"></nested:text></td>-->
											</tr>
											<tr>
												<td width="20%" class="row-odd">
												<div align="left"><bean:message
													key="knowledgepro.employee.place" /></div>
												</td>
												<td width="30%" height="25" class="row-even"><nested:text
													property="placePublication" size="40" maxlength="100"></nested:text></td>
											
											
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
												'formname' :'EmpResPubPendApprovalForm',
												// input name
												'controlname' :'<%=styleDate24 %>'
												});
									</script></div>
                  				</td>
											</tr>
											<tr>

														<td width="20%" class="row-odd">
														<div align="left"><bean:message
															key="knowledgepro.fee.academicyear" /></div>
														</td>
														<td width="30%" height="25" class="row-even">
															<%String invYearId="inv"+count; %>
															<c:set var="invid"><%=invYearId %></c:set>
                								 			<nested:select property="academicYear" styleId='<%=invYearId %>' styleClass="comboLarge">
  	   				 										<html:option value="">- Select -</html:option>
  	   														<cms:renderEmployeeYear></cms:renderEmployeeYear>
   			   												</nested:select>
   			   												<script type="text/javascript">
																var opid= '<nested:write property="academicYear"/>';
																if(opid!=0)
																document.getElementById("<c:out value='${invid}'/>").value = opid;
															</script>
														</td>
														<td width="20%" class="row-odd">
														<div align="left"><bean:message
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

														<td width="30%" class="row-even"><nested:text property="language"
															styleId="language" size="40" maxlength="50"></nested:text></td>
															
													
														<td width="20%" height="25" class="row-odd">
														<div align="left"><bean:message
															key="knowledgepro.employee.approver.comment" /></div>
														</td>
														<td width="30%" class="row-even" colspan="2"><nested:text
															property="approverComment" 
															size="40" maxlength="100"></nested:text></td>
													
											</tr>
					</logic:equal>
				<logic:equal value="false" property="selectedCategory" name="EmpResPubPendApprovalForm">
				<tr>
					<td class="row-even" colspan="4">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
							    <tr>
									<td height="25" align="center">
									<input type="button" style="formbutton" value="Approve" onclick="saveEmpResPubDetails('<nested:write property="id" name="empInvitedTalk"/>','InvitedTalk')"/>&nbsp;&nbsp;
									&nbsp;&nbsp;&nbsp;<input type="button" style="formbutton" value="Reject" onclick="RejectAction('<nested:write property="id" name="empInvitedTalk"/>','InvitedTalk')"/>
									</td>
									</tr>
						</table>
					</td>
				</tr>	
				</logic:equal>										
			</nested:iterate>
		</logic:notEmpty>
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
								<tr class="row-odd">
									<logic:notEmpty property="empCasesNotesWorkingTO"
										name="EmpResPubPendApprovalForm">
										<%int slnocount8 = 0; %>
										<nested:iterate property="empCasesNotesWorkingTO"
											name="EmpResPubPendApprovalForm" id="empCasesNotesWorking" indexId="count">
									<logic:equal value="false" name="empCasesNotesWorking" property="isApproved">
											<%slnocount8 = slnocount8 + 1; %>
											<tr>
													<td><div style="border: none; font-size: 10px; color: #000000"><b>Sl No:- <%= slnocount8 %></b></div></td>
													<td><div style="border: none; font-size: 10px; color: #000000"><b>Submitted Date:- 
													<bean:write name="empCasesNotesWorking" property="entryCreatedate" /></b></div></td>
													</tr>
								
											
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
													property="title" cols="40" rows="2"
													onkeypress="maxlength(this, 499);"></nested:textarea></td>
												
												<td width="20%" class="row-odd">
												<div align="left"><bean:message
													key="knowledgepro.employee.abstract.objectives" /></div>
												</td>
												<td width="30%" height="25" class="row-even">
												<nested:textarea property="abstractObjectives" cols="40" rows="2"
													onkeypress="maxlength(this, 298);"></nested:textarea>
												</td>
											</tr>
											<tr>
												<td width="20%" height="25" class="row-odd">
												<div align="left"><bean:message
													key="employee.instution.sponsors.department" /></div>
												</td>
												
												<td width="30%" class="row-even"><nested:text
													property="sponsors" styleId="sponsors" size="40" maxlength="100"></nested:text></td>
												<td width="20%" class="row-odd">
														<div align="left"><bean:message
															key="knowledgepro.fee.academicyear" /></div>
														</td>
														<td width="30%" height="25" class="row-even">
															<%String caseYearId="case"+count; %>
															<c:set var="caseid"><%=caseYearId %></c:set>
                								 			<nested:select property="academicYear" styleId='<%=caseYearId %>' styleClass="comboLarge">
  	   				 										<html:option value="">- Select -</html:option>
  	   														<cms:renderEmployeeYear></cms:renderEmployeeYear>
   			   												</nested:select>
   			   												<script type="text/javascript">
																var opid= '<nested:write property="academicYear"/>';
																if(opid!=0)
																document.getElementById("<c:out value='${caseid}'/>").value = opid;
															</script>
														</td>
										</tr>
										<tr>
														<td width="20%" height="25" class="row-odd">
														<div align="left"><bean:message
															key="knowledgepro.employee.approver.comment" /></div>
														</td>
														<td width="30%" class="row-even" colspan="2"><nested:text
															property="approverComment" 
															size="40" maxlength="100"></nested:text></td>
													
											</tr>
							</logic:equal>
							<logic:equal value="false" property="selectedCategory" name="EmpResPubPendApprovalForm">
							<tr>
					<td class="row-even" colspan="4">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
							    <tr>
									<td height="25" align="center">
									<input type="button" style="formbutton" value="Approve" onclick="saveEmpResPubDetails('<nested:write property="id" name="empCasesNotesWorking"/>','CasesNotesWorking')"/>&nbsp;&nbsp;
									&nbsp;&nbsp;&nbsp;<input type="button" style="formbutton" value="Reject" onclick="RejectAction('<nested:write property="id" name="empCasesNotesWorking"/>','CasesNotesWorking')"/>
									</td>
									</tr>
						</table>
					</td>
				</tr>	
				</logic:equal>
						</nested:iterate>
				
			</logic:notEmpty>
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
								<tr class="row-odd">
									<logic:notEmpty property="empPhdMPhilThesisGuidedTO"
										name="EmpResPubPendApprovalForm">
										<%int slnocount10 = 0; %>
										<nested:iterate property="empPhdMPhilThesisGuidedTO"
											name="EmpResPubPendApprovalForm" id="empPhdMPhilThesisGuided" indexId="count">
										<logic:equal value="false" name="empPhdMPhilThesisGuided" property="isApproved">
											<%slnocount10 = slnocount10 + 1; %>
											<tr>
													<td><div style="border: none; font-size: 10px; color: #000000"><b>Sl No:- <%= slnocount10 %></b></div></td>
													<td><div style="border: none; font-size: 10px; color: #000000"><b>Submitted Date:- 
													<bean:write name="empPhdMPhilThesisGuided" property="entryCreatedate" /></b></div></td>
													</tr>
					
											<tr>
											
											
												<td width="20%" height="25" class="row-odd">
												<div align="left"><bean:message
													key="knowledgepro.employee.title.of.thesis" /></div>
												</td>
												<td width="30%" class="row-even"><nested:textarea
													property="title" cols="40" rows="2"
													onkeypress="maxlength(this, 499);"></nested:textarea></td>
													
													<td width="20%" height="25" class="row-odd">
												<div align="left"><bean:message
													key="knowledgepro.admin.mail.subject" /></div>
												</td>
												<%
											String subject = "subject_" + count;
											%>
												<td width="30%" class="row-even"><nested:text
													property="subject" styleId="<%=subject%>" size="40" maxlength="100"></nested:text></td>
												
											</tr>
											<tr>
												<td width="20%" class="row-odd">
												<div align="left"><bean:message
													key="employee.institution.university" /></div>
												</td>
												<td width="30%" height="25" class="row-even"><nested:text
													property="companyInstitution" size="40" maxlength="250"></nested:text></td>
												
												<td width="20%" height="25" class="row-odd">
												<div align="left"><bean:message
													key="employee.name.student" /></div>
												</td>
												
												<td width="30%" class="row-even"><nested:text
													property="nameStudent" styleId="nameStudent" size="40" maxlength="100"></nested:text></td>
											</tr>
											<tr>
												<td width="20%" class="row-odd">
												<div align="left"><bean:message
													key="knowledgepro.employee.place" /></div>
												</td>
												<td width="30%" height="25" class="row-even"><nested:text
													property="place" size="40" maxlength="100"></nested:text></td>
											
											
												<td width="20%" class="row-odd">
												<div align="left"><bean:message
													key="knowledgepro.employee.monthYear" /></div>
												</td>
												<td width="30%" height="25" class="row-even"><nested:text
													property="monthYear" size="40" maxlength="20"></nested:text></td>
											</tr>
										<tr>

														<td width="20%" class="row-odd">
														<div align="left"><bean:message
															key="knowledgepro.fee.academicyear" /></div>
														</td>
														<td width="30%" height="25" class="row-even">
															<%String phYearId="ph"+count; %>
															<c:set var="phid"><%=phYearId %></c:set>
                								 			<nested:select property="academicYear" styleId='<%=phYearId %>' styleClass="comboLarge">
  	   				 										<html:option value="">- Select -</html:option>
  	   														<cms:renderEmployeeYear></cms:renderEmployeeYear>
   			   												</nested:select>
   			   												<script type="text/javascript">
																var opid= '<nested:write property="academicYear"/>';
																if(opid!=0)
																document.getElementById("<c:out value='${phid}'/>").value = opid;
															</script>
														</td>
														<%-- <td width="20%" class="row-odd">
														<div align="left">Guided/Adjudicated</div>
														</td>
														<td width="30%" height="25" class="row-even">
                								 			<nested:select property="guidedAbudjicated" styleId="guidedAbudjicated" styleClass="comboLarge">
  	   				 										<html:option value="">- Select -</html:option>
  	   														<html:option value="Guided">Guided </html:option>
								   							<html:option value="Adjudicated">Adjudicated</html:option>
   			   												</nested:select>
												</td>--%>
														<td width="20%" class="row-odd">
														<div align="left"><bean:message
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
											<tr>
												<%--<td width="20%" class="row-odd">
														<div align="left"><bean:message
															key="knowledgepro.employee.type" /></div>
														</td>
														<td width="30%" height="25" class="row-even">
                								 			<nested:select property="type" styleId="type" styleClass="comboLarge">
  	   				 										<html:option value="">- Select -</html:option>
  	   														<html:option value="PhD">PhD </html:option>
								   							<html:option value="Mphil">Mphil</html:option>
   			   												</nested:select>
												</td>--%>
														<td width="20%" height="25" class="row-odd">
														<div align="left"><bean:message
															key="knowledgepro.employee.approver.comment" /></div>
														</td>
														<td width="30%" class="row-even" colspan="2"><nested:text
															property="approverComment" 
															size="40" maxlength="100"></nested:text></td>
													</tr>
						</logic:equal>	
						<logic:equal value="false" property="selectedCategory" name="EmpResPubPendApprovalForm">
						<tr>
										<td class="row-even" colspan="4">
										<table width="100%" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td height="25" align="center">
											<input type="button" style="formbutton" value="Approve" onclick="saveEmpResPubDetails('<nested:write property="id" name="empPhdMPhilThesisGuided"/>','PhdMPhilThesisGuided')"/>&nbsp;&nbsp;
											&nbsp;&nbsp;&nbsp;<input type="button" style="formbutton" value="Reject" onclick="RejectAction('<nested:write property="id" name="empPhdMPhilThesisGuided"/>','PhdMPhilThesisGuided')"/>
											</td>
										</tr>
										
									</table>

									</td>
								</tr>	
								</logic:equal>									
										</nested:iterate>
						
				</logic:notEmpty>
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
								<tr class="row-odd">
									<logic:notEmpty property="empOwnPhdMPilThesisTO"
										name="EmpResPubPendApprovalForm">
										<%int slnocount11 = 0; %>
										<nested:iterate property="empOwnPhdMPilThesisTO"
											name="EmpResPubPendApprovalForm" id="empOwnPhdMPilThesis" indexId="count">
								<logic:equal value="false" name="empOwnPhdMPilThesis" property="isApproved">
								<%slnocount11 = slnocount11 + 1; %>
											<tr>
													<td><div style="border: none; font-size: 10px; color: #000000"><b>Sl No:- <%= slnocount11 %></b></div></td>
													<td><div style="border: none; font-size: 10px; color: #000000"><b>Submitted Date:- 
													<bean:write name="empOwnPhdMPilThesis" property="entryCreatedate" /></b></div></td>
													</tr>
				
											
											<tr>
											
											
												<td width="20%" height="25" class="row-odd">
												<div align="left"><bean:message
													key="knowledgepro.employee.title.of.thesis" /></div>
												</td>
												<td width="30%" class="row-even"><nested:textarea
													property="title" cols="40" rows="2"
													onkeypress="maxlength(this, 499);"></nested:textarea></td>
													
													<td width="20%" height="25" class="row-odd">
												<div align="left"><bean:message
													key="knowledgepro.admin.mail.subject" /></div>
												</td>
												<%
											String subject = "subject_" + count;
											%>
												<td width="30%" class="row-even"><nested:text
													property="subject" styleId="<%=subject%>" size="40" maxlength="100"></nested:text></td>
												
											</tr>
											<tr>
												<td width="20%" class="row-odd">
												<div align="left"><bean:message
													key="employee.institution.university" /></div>
												</td>
												<td width="30%" height="25" class="row-even"><nested:text
													property="companyInstitution" size="40" maxlength="250"></nested:text></td>
												
												<td width="20%" height="25" class="row-odd">
												<div align="left"><bean:message
													key="employee.name.guide" /></div>
												</td>
												
												<td width="30%" class="row-even"><nested:text
													property="nameGuide" styleId="nameGuide" size="40" maxlength="100"></nested:text></td>
											</tr>
											<tr>
												<td width="20%" class="row-odd">
												<div align="left"><bean:message
													key="knowledgepro.employee.place" /></div>
												</td>
												<td width="30%" height="25" class="row-even"><nested:text
													property="place" size="40" maxlength="100"></nested:text></td>
											
											
												<td width="20%" class="row-odd">
												<div align="left"><bean:message
													key="employee.month.year.defense" /></div>
												</td>
												<td width="30%" height="25" class="row-even"><nested:text
													property="monthYear" size="40" maxlength="20"></nested:text></td>
											</tr>
											
											<tr>

														<td width="20%" class="row-odd">
														<div align="left"><bean:message
															key="knowledgepro.fee.academicyear" /></div>
														</td>
														<td width="30%" height="25" class="row-even">
															<%String ownYearId="own"+count; %>
															<c:set var="ownid"><%=ownYearId %></c:set>
                								 			<nested:select property="academicYear" styleId='<%=ownYearId %>' styleClass="comboLarge">
  	   				 										<html:option value="">- Select -</html:option>
  	   														<cms:renderEmployeeYear></cms:renderEmployeeYear>
   			   												</nested:select>
   			   												<script type="text/javascript">
																var opid= '<nested:write property="academicYear"/>';
																if(opid!=0)
																document.getElementById("<c:out value='${ownid}'/>").value = opid;
															</script>
														</td>
														<td width="20%" class="row-odd">
														<div align="left">Date of Submission (dd/mm/yyyy)</div>
														</td>
														<%
															String styleDateown = "dateMonthYear1_" + count;
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
														<div align="left"><bean:message
															key="knowledgepro.employee.type" /></div>
														</td>
														<td width="30%" height="25" class="row-even">
                								 			<nested:select property="type" styleId="type" styleClass="comboLarge">
  	   				 										<html:option value="">- Select -</html:option>
  	   														<html:option value="PhD">PhD </html:option>
								   							<html:option value="Mphil">Mphil</html:option>
   			   												</nested:select>
												</td>
										
												<td width="20%" height="25" class="row-odd">
														<div align="left"><bean:message
															key="knowledgepro.employee.approver.comment" /></div>
												</td>
												<td width="30%" class="row-even" colspan="2"><nested:text
															property="approverComment" 
															size="40" maxlength="100"></nested:text></td>
													</tr>
							</logic:equal>
						<logic:equal value="false" property="selectedCategory" name="EmpResPubPendApprovalForm">
								<tr>
										<td class="row-even" colspan="4">
										<table width="100%" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td height="25" align="center">
											<input type="button" style="formbutton" value="Approve" onclick="saveEmpResPubDetails('<nested:write property="id" name="empOwnPhdMPilThesis"/>','OwnPhdMPilThesis')"/>&nbsp;&nbsp;
											&nbsp;&nbsp;&nbsp;<input type="button" style="formbutton" value="Reject" onclick="RejectAction('<nested:write property="id" name="empOwnPhdMPilThesis"/>','OwnPhdMPilThesis')"/>
											</td>
										</tr>
										
									</table>

									</td>
								</tr>	
						</logic:equal>							
										</nested:iterate>
							
		</logic:notEmpty>
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
										<tr class="row-odd">
											<logic:notEmpty property="empBlogTO"
												name="EmpResPubPendApprovalForm">
												<%int slnocount4 = 0; %>
												<nested:iterate property="empBlogTO"
													name="EmpResPubPendApprovalForm" id="empBlog" indexId="count">
								 <logic:equal value="false" name="empBlog" property="isApproved">
								 					 <%slnocount4 = slnocount4 + 1; %>
													<tr>
													<td><div style="border: none; font-size: 10px; color: #000000"><b>Sl No:- <%= slnocount4 %></b></div></td>
													<td><div style="border: none; font-size: 10px; color: #000000"><b>Submitted Date:- 
													<bean:write name="empBlog" property="entryCreatedate" /></b></div></td>
													</tr>
									
													
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
															onkeypress="maxlength(this, 499);"></nested:textarea></td>
														<td width="20%" height="25" class="row-odd">
														<div align="left"><bean:message
															key="employee.info.language.title" /></div>
														</td>
														<td width="30%" class="row-even"><nested:text property="language"
															styleId="<%=language%>" size="40" maxlength="50"></nested:text></td>
													</tr>
													<tr>
														<td width="20%" class="row-odd">
														<div align="left"><bean:message
															key="knowledgepro.employee.number.comments" /></div>
														</td>
														<td width="30%" height="25" class="row-even"><nested:text
															property="numberOfComments" size="40" maxlength="10"></nested:text></td>

														<td width="20%" class="row-odd">
														<div align="left"><bean:message
															key="knowledgepro.employee.monthYear" /></div>
														</td>
														<td width="30%" height="25" class="row-even"><nested:text
															property="monthYear" size="40" maxlength="20"></nested:text></td>


													</tr>
													<tr>
														<td width="20%" class="row-odd">
														<div align="left"><bean:message
															key="knowledgepro.admin.selectedSubjects" /></div>
														</td>
														<td width="30%" height="25" class="row-even"><nested:text
															property="subject" size="40" maxlength="100"></nested:text></td>

														<td width="20%" class="row-odd">
														<div align="left"><bean:message
															key="knowledgepro.employee.url" /></div>
														</td>
														<td width="30%" height="25" class="row-even"><nested:text
															property="url" size="40" maxlength="200"></nested:text></td>


													</tr>
													<tr>
														<td width="20%" class="row-odd">
														<div align="left"><bean:message
															key="knowledgepro.fee.academicyear" /></div>
														</td>
														<td width="30%" height="25" class="row-even">
															<%String blYearId="bl"+count; %>
															<c:set var="blid"><%=blYearId %></c:set>
                								 			<nested:select property="academicYear" styleId='<%=blYearId %>' styleClass="comboLarge">
  	   				 										<html:option value="">- Select -</html:option>
  	   														<cms:renderEmployeeYear></cms:renderEmployeeYear>
   			   												</nested:select>
   			   												<script type="text/javascript">
																var opid= '<nested:write property="academicYear"/>';
																if(opid!=0)
																document.getElementById("<c:out value='${blid}'/>").value = opid;
															</script>
														</td>
												
														<td width="20%" height="25" class="row-odd">
														<div align="left"><bean:message
															key="knowledgepro.employee.approver.comment" /></div>
														</td>
														<td width="30%" class="row-even" colspan="2"><nested:text
															property="approverComment" 
															size="40" maxlength="100"></nested:text></td>
													</tr>
									</logic:equal>
								<logic:equal value="false" property="selectedCategory" name="EmpResPubPendApprovalForm">
									<tr>
										<td class="row-even" colspan="4">
										<table width="100%" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td height="25" align="center">
											<input type="button" style="formbutton" value="Approve" onclick="saveEmpResPubDetails('<nested:write property="id" name="empBlog"/>','Blog')"/>&nbsp;&nbsp;
											&nbsp;&nbsp;&nbsp;<input type="button" style="formbutton" value="Reject" onclick="RejectAction('<nested:write property="id" name="empBlog"/>','Blog')"/>
											</td>
										</tr>
										
									</table>

									</td>
								</tr>
							</logic:equal>

						</nested:iterate>
							</logic:notEmpty>
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
								<tr class="row-odd">
									<logic:notEmpty property="empFilmVideosDocTO"
										name="EmpResPubPendApprovalForm">
										<%int slnocount5 = 0; %>
										<nested:iterate property="empFilmVideosDocTO"
											name="EmpResPubPendApprovalForm" id="empFilmVideosDoc" indexId="count">
							<logic:equal value="false" name="empFilmVideosDoc" property="isApproved">
											 <%slnocount5 = slnocount5 + 1; %>
											<tr>
													<td><div style="border: none; font-size: 10px; color: #000000"><b>Sl No:- <%= slnocount5 %></b></div></td>
													<td><div style="border: none; font-size: 10px; color: #000000"><b>Submitted Date:- 
													<bean:write name="empFilmVideosDoc" property="entryCreatedate" /></b></div></td>
													</tr>
							
											
											<tr>
											
											
												<td width="20%" height="25" class="row-odd">
												<div align="left"><bean:message
													key="knowledgepro.employee.title" /></div>
												</td>
												<td width="30%" class="row-even"><nested:textarea
													property="title" cols="40" rows="2"
													onkeypress="maxlength(this, 499);"></nested:textarea></td>
													
													<td width="20%" height="25" class="row-odd">
												<div align="left"><bean:message
													key="knowledgepro.employee.subtitles" /></div>
												</td>
												
												<td width="30%" class="row-even"><nested:text
													property="subtitles" styleId="subtitles" size="40" maxlength="100"></nested:text></td>
												
											</tr>
											<tr>
												<td width="20%" class="row-odd">
												<div align="left"><bean:message
													key="knowledgepro.employee.genre" /></div>
												</td>
												<td width="30%" height="25" class="row-even"><nested:text
													property="genre" size="40" maxlength="100"></nested:text></td>
												
												<td height="25" class="row-odd">
												<div align="left"><bean:message
													key="knowledgepro.employee.credits" /></div>
												</td>
												
												<td width="20%" class="row-even"><nested:text
													property="credits" styleId="credits" size="40" maxlength="100"></nested:text></td>
											</tr>
											<tr>
												<td width="20%" class="row-odd">
												<div align="left"><bean:message
													key="knowledgepro.employee.running.time" /></div>
												</td>
												<td width="30%" class="row-even"><nested:text
													property="runningTime" styleId="runningTime" size="40" maxlength="10"></nested:text></td>
											
												<td width="20%" class="row-odd">
												<div align="left"><bean:message
													key="knowledgepro.employee.disc.format" /></div>
												</td>
												<td width="30%" height="25" class="row-even"><nested:text
													property="discFormat" size="40" maxlength="10"></nested:text></td>
											</tr>
											<tr>
												
												<td width="20%" class="row-odd">
												<div align="left"><bean:message	key="knowledgepro.employee.technical.format" /></div>
												</td>
												<td width="30%" height="25" class="row-even"><nested:text
													property="technicalFormat" size="40" maxlength="40"></nested:text></td>
													
												<td width="20%" class="row-odd">
												<div align="left"><bean:message	key="knowledgepro.employee.audio.format" /></div>
												</td>
												<td width="30%" height="25" class="row-even"><nested:text
													property="audioFormat" size="40" maxlength="20"></nested:text></td>	
											</tr>
											<tr>	
											
												<td width="20%" class="row-odd">
												<div align="left"><bean:message	key="knowledgepro.employee.aspect.ratio" /></div>
												</td>
												<td width="30%" height="25" class="row-even"><nested:text
													property="aspectRatio" size="40" maxlength="30"></nested:text></td>
												
												<td width="20%" class="row-odd">
												<div align="left"><bean:message	key="knowledgepro.employee.producer" /></div>
												</td>
												<td width="30%" height="25" class="row-even"><nested:text
													property="producer" size="40" maxlength="100"></nested:text></td>
											</tr>
											<tr>	
											
												<td width="20%" class="row-odd">
												<div align="left"><bean:message	key="knowledgepro.employee.copyright" /></div>
												</td>
												<td width="30%" height="25" class="row-even"><nested:text
													property="copyrights" size="40" maxlength="150"></nested:text></td>
												<td width="20%" class="row-odd">
														<div align="left"><bean:message
															key="knowledgepro.fee.academicyear" /></div>
														</td>
														<td width="30%" height="25" class="row-even">
															<%String fYearId="f"+count; %>
															<c:set var="fid"><%=fYearId %></c:set>
                								 			<nested:select property="academicYear" styleId='<%=fYearId %>' styleClass="comboLarge">
  	   				 										<html:option value="">- Select -</html:option>
  	   														<cms:renderEmployeeYear></cms:renderEmployeeYear>
   			   												</nested:select>
   			   												<script type="text/javascript">
																var opid= '<nested:write property="academicYear"/>';
																if(opid!=0)
																document.getElementById("<c:out value='${fid}'/>").value = opid;
															</script>
														</td>
											</tr>
											<tr>	
													
										<td width="20%" height="25" class="row-odd">
														<div align="left"><bean:message
															key="knowledgepro.employee.approver.comment" /></div>
												</td>
											<td width="30%" class="row-even" colspan="2"><nested:text
															property="approverComment" 
												size="40" maxlength="100"></nested:text></td>
													
											</tr>
							</logic:equal>
						<logic:equal value="false" property="selectedCategory" name="EmpResPubPendApprovalForm">
								<tr>
										<td class="row-even" colspan="4">
										<table width="100%" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td height="25" align="center">
											<input type="button" style="formbutton" value="Approve" onclick="saveEmpResPubDetails('<nested:write property="id" name="empFilmVideosDoc"/>','FilmVideosDoc')"/>&nbsp;&nbsp;
											&nbsp;&nbsp;&nbsp;<input type="button" style="formbutton" value="Reject" onclick="RejectAction('<nested:write property="id" name="empFilmVideosDoc"/>','FilmVideosDoc')"/>
											</td>
										</tr>
									</table>
									</td>
								</tr>
							</logic:equal>
						</nested:iterate>
							
							</logic:notEmpty>
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
								<tr class="row-odd">
									<logic:notEmpty property="empSeminarsOrganizedTO"
										name="EmpResPubPendApprovalForm">
										<%int slnocount9 = 0; %>
										<nested:iterate property="empSeminarsOrganizedTO"
											name="EmpResPubPendApprovalForm" id="empSeminarsOrganized" indexId="count">
										<logic:equal value="false" name="empSeminarsOrganized" property="isApproved">
											<%slnocount9 = slnocount9 + 1; %>
											<tr>
													<td><div style="border: none; font-size: 10px; color: #000000"><b>Sl No:- <%= slnocount9 %></b></div></td>
													<td><div style="border: none; font-size: 10px; color: #000000"><b>Submitted Date:- 
													<bean:write name="empSeminarsOrganized" property="entryCreatedate" /></b></div></td>
													</tr>
				
											<tr>
												<td width="20%" height="25" class="row-odd">
												<div align="left"><bean:message
													key="employee.name.organisers" /></div>
												</td>
													<%
													String nameOrganisers = "nameOrganisers_" + count;
														%>
												<td width="30%" class="row-even"><nested:text
													property="nameOrganisers" styleId="<%=nameOrganisers%>" size="30" maxlength="200"></nested:text></td>
												
												<td width="20%" height="25" class="row-odd">
												<div align="left"><bean:message
													key="employee.name.conference.seminar" /></div>
												</td>
												<td width="30%" class="row-even"><nested:text
													property="nameConferencesSeminar" size="40" maxlength="200"></nested:text></td>
											</tr>
											<tr>
												<td width="20%" height="25" class="row-odd">
												<div align="left"><bean:message
													key="employee.resourse.persons.details" /></div>
												</td>
												<td width="30%" class="row-even"><nested:text
													property="resoursePerson" size="40" maxlength="200"></nested:text></td>
												
												<td width="20%" height="25" class="row-odd">
														<div align="left"><bean:message
															key="employee.info.language.title" /></div>
														</td>

														<td width="30%" class="row-even"><nested:text property="language"
															styleId="language" size="40" maxlength="50"></nested:text></td>
												
												
												
											</tr>
											<tr>
											<td width="20%" class="row-odd">
												<div align="left"><bean:message
													key="knowledgepro.employee.place" /></div>
												</td>
												<td width="30%" height="25" class="row-even"><nested:text
													property="place" size="40" maxlength="100"></nested:text></td>
											
											
												<td width="20%" class="row-odd">
														<div align="left">Date,Month and Year (dd/mm/yyyy)</div>
														</td>
														<%
															String styleDate1 = "dateMonthYear_" + count;
														%>
														<td width="30%" height="25" class="row-even"><span class="star"><nested:text
															property="dateMonthYear" styleId="<%=styleDate1%>"
															size="40" maxlength="20"></nested:text> <script
															language="JavaScript">
														new tcal( {
														// form name
														'formname' :'EmpResPubPendApprovalForm',
														// input name
														'controlname' :'<%=styleDate1%>'
															});
														</script></span></td>

											</tr>
											<tr>
																							
												<td width="20%" height="25" class="row-odd">
												<div align="left"><bean:message
													key="employee.instution.sponsors.department" /></div>
												</td>
												
												<td width="30%" class="row-even"><nested:text
													property="sponsors" styleId="sponsors" size="40" maxlength="100"></nested:text></td>
												<td width="20%" class="row-odd">
														<div align="left"><bean:message
															key="knowledgepro.fee.academicyear" /></div>
														</td>
														<td width="30%" height="25" class="row-even">
															<%String soYearId="so"+count; %>
															<c:set var="soid"><%=soYearId %></c:set>
                								 			<nested:select property="academicYear" styleId='<%=soYearId %>' styleClass="comboLarge">
  	   				 										<html:option value="">- Select -</html:option>
  	   														<cms:renderEmployeeYear></cms:renderEmployeeYear>
   			   												</nested:select>
   			   												<script type="text/javascript">
																var opid= '<nested:write property="academicYear"/>';
																if(opid!=0)
																document.getElementById("<c:out value='${soid}'/>").value = opid;
															</script>
														</td>
											</tr>
											<tr>	
												<td width="20%" height="25" class="row-odd">
														<div align="left"><bean:message
															key="knowledgepro.employee.approver.comment" /></div>
														</td>
														<td width="30%" class="row-even" colspan="2"><nested:text
															property="approverComment" 
															size="40" maxlength="100"></nested:text></td>
													
											</tr>
											</logic:equal>
							<logic:equal value="false" property="selectedCategory" name="EmpResPubPendApprovalForm">
								<tr>
										<td class="row-even" colspan="4">
										<table width="100%" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td height="25" align="center">
											<input type="button" style="formbutton" value="Approve" onclick="saveEmpResPubDetails('<nested:write property="id" name="empSeminarsOrganized"/>','SeminarsOrganized')"/>&nbsp;&nbsp;
											&nbsp;&nbsp;&nbsp;<input type="button" style="formbutton" value="Reject" onclick="RejectAction('<nested:write property="id" name="empSeminarsOrganized"/>','SeminarsOrganized')"/>
											</td>
										</tr>
									</table>
									</td>
								</tr>
							</logic:equal>
					</nested:iterate>
						</logic:notEmpty>
								</tr>
								 
					</table>
			</td>
		</tr>						
			
		
			<tr class="tdIMG" height="35px">
									<td>
									<div align="left" style="border: none; font-size: 13px; color: #006600"><b> <bean:message
										key="employee.workshops.trainings.attended" /></b></div>
									</td>
								 <td align="right"> <div class="arrow"></div></td>
								</tr>
								<tr class="data" id="WorkshopsAttended">
							<td>
							<table width="100%" cellspacing="1" cellpadding="2">
								<tr class="row-odd">
									<logic:notEmpty property="empWorkshopsTO"
										name="EmpResPubPendApprovalForm">
										<%int slnocount5 = 0; %>
										<nested:iterate property="empWorkshopsTO"
											name="EmpResPubPendApprovalForm" id="empWorkshops" indexId="count">
							<logic:equal value="false" name="empWorkshops" property="isApproved">
											 <%slnocount5 = slnocount5 + 1; %>
											<tr>
													<td><div style="border: none; font-size: 10px; color: #000000"><b>Sl No:- <%= slnocount5 %></b></div></td>
													<td><div style="border: none; font-size: 10px; color: #000000"><b>Submitted Date:- 
													<bean:write name="empWorkshops" property="entryCreatedate" /></b></div></td>
													</tr>
							
											
																
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
													key="knowledgepro.date.pgm" /></div>
												</td>
														
														<%
															String styleDate11 = "dateOfPgm_" + count;
														%>
														
												
												<td width="30%" class="row-even"><div align="left">
                  <nested:text  property="dateOfPgm" styleId="<%=styleDate11 %>" maxlength="20" ></nested:text>
                  <script
											language="JavaScript">
												new tcal( {
												// form name
												'formname' :'EmpResPubPendApprovalForm',
												// input name
												'controlname' :'<%=styleDate11 %>'
												});
					</script></div>
                  </td>
												
												
												<td width="20%" height="25" class="row-odd">
														<div align="left"><bean:message
															key="knowledgepro.duration.pgm" /></div>
														</td>

														<td width="30%" class="row-even"><nested:text property="duration"
															styleId="duration" size="40" maxlength="50" ></nested:text></td>
											</tr>
											<tr>

														<td width="20%" class="row-odd">
														<div align="left"><bean:message
															key="knowledgepro.fee.academicyear" /></div>
														</td>
														<td width="30%" height="25" class="row-even">
															<%String wkYearId="wk"+count; %>
															<c:set var="wkid"><%=wkYearId %></c:set>
                								 			<nested:select property="academicYear" styleId='<%=wkYearId %>' styleClass="comboLarge">
  	   				 										<html:option value="">- Select -</html:option>
  	   														<cms:renderEmployeeYear></cms:renderEmployeeYear>
   			   												</nested:select>
   			   												<script type="text/javascript">
																var opid= '<nested:write property="academicYear"/>';
																if(opid!=0)
																document.getElementById("<c:out value='${wkid}'/>").value = opid;
															</script>
														</td>
														
														<td width="20%" class="row-odd">
														<div align="left"><bean:message
															key="knowledgepro.employee.level" /></div>
														</td>
														<td width="30%" height="25" class="row-even">
														
														<input type="hidden" id="WorkshopsType" name="WorkshopsType" value="<bean:write name="empWorkshops" property="type"/>"/>
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
															key="knowledgepro.employee.approver.comment" /></div>
														</td>
														<td width="30%" class="row-even" colspan="2"><nested:text
															property="approverComment" 
															size="40" maxlength="100"></nested:text></td>
											</tr>
							</logic:equal>
						<logic:equal value="false" property="selectedCategory" name="EmpResPubPendApprovalForm">
							<tr>
										<td class="row-even" colspan="4">
										<table width="100%" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td height="25" align="center">
											<input type="button" style="formbutton" value="Approve" onclick="saveEmpResPubDetails('<nested:write property="id" name="empWorkshops"/>','WorkshopsAttended')"/>&nbsp;&nbsp;
											&nbsp;&nbsp;&nbsp;<input type="button" style="formbutton" value="Reject" onclick="RejectAction('<nested:write property="id" name="empWorkshops"/>','WorkshopsAttended')"/>
											</td>
										</tr>
									</table>
									</td>
								</tr>
							</logic:equal>
										</nested:iterate>
							</logic:notEmpty>
								</tr>
								 
					</table>

					</td>
			</tr>	
			
					<tr class="tdIMG" height="35px">
									<td>
									<div align="left" style="border: none; font-size: 13px; color: #006600"><b> <bean:message
										key="employee.research.awards.achiewemnets" /></b></div>
									</td>
								 <td align="right"> <div class="arrow"></div></td>
								</tr>
								<tr class="data" id="AwardsAchievements">
							<td>
							<table width="100%" cellspacing="1" cellpadding="2">
								<tr class="row-odd">
									<logic:notEmpty property="empAwardsAchievementsOthersTO"
										name="EmpResPubPendApprovalForm">
										<%int slnocount5 = 0; %>
										<nested:iterate property="empAwardsAchievementsOthersTO"
											name="EmpResPubPendApprovalForm" id="empAwardsAchievementsOthers" indexId="count">
							<logic:equal value="false" name="empAwardsAchievementsOthers" property="isApproved">
											 <%slnocount5 = slnocount5 + 1; %>
											<tr>
													<td><div style="border: none; font-size: 10px; color: #000000"><b>Sl No:- <%= slnocount5 %></b></div></td>
													<td><div style="border: none; font-size: 10px; color: #000000"><b>Submitted Date:- 
													<bean:write name="empAwardsAchievementsOthers" property="entryCreatedate" /></b></div></td>
													</tr>
							
											
																
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
												<td width="30%" height="25" class="row-even" align="left"><nested:textarea
															property="description" cols="40" rows="1"
															onkeypress="maxlength(this, 499);"></nested:textarea></td>
												
												<td width="20%" height="25" class="row-odd">
														<div align="left">Month and Year</div>
														</td>

												<td width="30%" class="row-even"><nested:text property="monthYear"
															styleId="monthYear" size="40" maxlength="20"  ></nested:text></td>
											</tr>
											<tr>
														<td width="20%" class="row-odd">
														<div align="left"><bean:message
															key="knowledgepro.fee.academicyear" /></div>
														</td>
														<td width="30%" height="25" class="row-even">
														<%String AwardsYearId="Awards"+count; %>
														<c:set var="Awardsid"><%=AwardsYearId %></c:set>
                								 			<nested:select property="academicYear" styleId='<%=AwardsYearId %>' styleClass="comboLarge">
  	   				 										<html:option value="">- Select -</html:option>
  	   														<cms:renderEmployeeYear></cms:renderEmployeeYear>
   			   												</nested:select>
   			   												<script type="text/javascript">
																var opid= '<nested:write property="academicYear"/>';
																if(opid!=0)
																document.getElementById("<c:out value='${Awardsid}'/>").value = opid;
															</script>
														</td>
														<td width="20%" height="25" class="row-odd">
														<div align="left">Institution/Organization Awarded</div>
														</td>

												<td width="30%" class="row-even"><nested:text property="organisationAwarded"
															styleId="organisationAwarded" size="40" maxlength="300"  ></nested:text></td>
									</tr>
									<tr>	
														
													
											<td width="20%" height="25" class="row-odd">
														<div align="left"><bean:message
															key="knowledgepro.employee.approver.comment" /></div>
														</td>
														<td width="30%" class="row-even" colspan="2"><nested:text
															property="approverComment" 
															size="40" maxlength="100"></nested:text></td>
											</tr>
							</logic:equal>
						<logic:equal value="false" property="selectedCategory" name="EmpResPubPendApprovalForm">
						<tr>
										<td class="row-even" colspan="4">
										<table width="100%" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td height="25" align="center">
											<input type="button" style="formbutton" value="Approve" onclick="saveEmpResPubDetails('<nested:write property="id" name="empAwardsAchievementsOthers"/>','AwardsAchievements')"/>&nbsp;&nbsp;
											&nbsp;&nbsp;&nbsp;<input type="button" style="formbutton" value="Reject" onclick="RejectAction('<nested:write property="id" name="empAwardsAchievementsOthers"/>','AwardsAchievements')"/>
											</td>
										</tr>
									</table>
									</td>
								</tr>
							</logic:equal>
										</nested:iterate>
						
							</logic:notEmpty>
								</tr>
								 
					</table>

					</td>
			</tr>		
						
						
						
						
						
							</table>
							</td>
				</tr>
					</table>
					<logic:equal value="false" property="selectedCategory" name="EmpResPubPendApprovalForm">
					<div align="center" id="buttons">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td colspan="3" height="35" align="center"><div align="center">
							<html:button property="" styleClass="formbutton" value="Cancel" onclick="cancelAction()"></html:button></div>
							</td>
						</tr>
					</table>
					</div>
					</logic:equal>
					<logic:equal value="true" property="selectedCategory" name="EmpResPubPendApprovalForm">
					<div id="showbuttons" align="center">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td colspan="3" height="35" width="50" align="center">
					<html:button property="" styleClass="formbutton" value="Close" onclick="javascript:self.close();"></html:button>
						</td>
						</tr>
					</table>
					</div>
					</logic:equal>
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
</body>
