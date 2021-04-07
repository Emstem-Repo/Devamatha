<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@page import="com.itextpdf.text.log.SysoLogger"%>
<script src="tinymce/js/tinymce/tinymce.min.js"></script>
<script src="jquery/development-bundle/jquery-1.7.1.js"></script>
<script src="tinymce/js/tinymce/tinymce.min.js"></script>
<link rel="stylesheet" type="text/css" href="css/tooltipster.css" />
 <script type="text/javascript" src="js/jquery.tooltipster.js"></script>
<link rel="stylesheet" type="text/css" href="jquery.confirm/jquery.confirm/teacherAllotment.css" />
<link type="text/css"
	href="jquery/css/ui-lightness/jquery-ui-1.8.18.custom.css" rel="stylesheet" />
<link rel="stylesheet" type="text/css" href="css/styles.css"/>
	<link rel="stylesheet" type="text/css" href="css/sdmenu.css"/>
    <link rel='stylesheet' type='text/css' href="css/auditorium/start/jquery-ui-supportRequest.css" />
	<script type='text/javascript' src="js/auditorium/jquery-ui.min.js"></script>
	<link rel="stylesheet" type="text/css" href="jquery.confirm/jquery.confirm/jquery.confirm.css" />
    <script src="jquery.confirm/jquery.confirm/jquery.confirm.js"></script>
 <style type="text/css">
 .btn {
  background: #A0F24E;
  background-image: -webkit-linear-gradient(top, #F7FBDA, #DBE87A);
  background-image: -moz-linear-gradient(top, #F7FBDA, #DBE87A);
  background-image: -ms-linear-gradient(top, #F7FBDA, #DBE87A);
  background-image: -o-linear-gradient(top, #F7FBDA, #DBE87A);
  -webkit-border-radius: 15;
  -moz-border-radius: 15;
  border-radius: 15px;
  font-family: Arial;
  font-weight:bold;
  color: #171919;
  font-size: 12px;
  padding: 5px 10px 5px 10px;
  text-decoration: none;
}

.btn:hover {
  background: #D5DC98;
  background-image: -webkit-linear-gradient(top, #D5DC98, #F7FBDA);
  background-image: -moz-linear-gradient(top, #D5DC98, #F7FBDA);
  background-image: -ms-linear-gradient(top, #D5DC98, #F7FBDA);
  background-image: -o-linear-gradient(top, #D5DC98, #F7FBDA);
  text-decoration: none;
}
 </style>
 <style type="text/css">
	 body { font-size: 62.5%; }
</style>
<script type="text/javascript">
	 tinymce.init({selector: "textarea.editme"});
</script>
<script type="text/javascript">
$(document).ready(function(){
    $("#report1 .tdIMG").addClass("odd");
    $("#report1 .data").hide();
    
});
$(document).ready(function() {
    // Tooltip only Text
     $("#openDialog").hide();
    $('.dropt').tooltipster();
});
function syllabusEntry(subId){
	document.getElementById("method").value = "initSyllabusEntryForHodSecondPage";
	document.getElementById("subjectId").value = subId;
	document.syllabusEntryHodApprovalForm.submit();
}
function cancel(){
	document.location.href = "syllabusEntryHodApproval.do?method=checkIsSyllabusEntryOpenForHod";
}
function course(cnt){
	var a=document.getElementById("listSize").value;
	for (var i=0;i<a;i++){
		if(cnt==i){
			$("#courseId_"+i).show();
			$("#programId_"+i).hide();
			}else{
				$("#courseId_"+i).hide();
				$("#programId_"+i).hide();
				}
	}
	 
}
function program(cnt){
	var a=document.getElementById("listSize").value;
	for (var i=0;i<a;i++){
		if(cnt==i){
			$("#programId_"+i).show();
			 $("#courseId_"+i).hide();
			}else{
				$("#courseId_"+i).hide();
				$("#programId_"+i).hide();
				}
	}
	 
}
function approve(id){
	document.getElementById("method").value = "syllabusEntryHodApprove";
	document.getElementById("courseId").value = id;
	document.syllabusEntryHodApprovalForm.submit();
}
function preview(id,courseName){
	var year=document.getElementById("batchYear").value;
	url = "syllabusEntryHodApproval.do?method=hodPreview&courseId="+id+"&batchYear="+year+"&courseName="+courseName;
	myRef = window.open(url,"collectionLedger","left=20,top=20,width=800,height=500,toolbar=1,resizable=0,scrollbars=1");
}
function reject(id){
	 $("#openDialog").dialog({
	        resizable: false,
	        modal: true,
	        height: 200,
	        width: 500,
	        close: function() {
	    	$("#openDialog").dialog("destroy");
	    	$("#openDialog").hide();
			},
			buttons: {
				  Ok : function() {
						var reason=document.getElementById("tempRejectReason").value;
						  if(reason!=null && reason!=''){
							  document.getElementById("method").value = "rejectSyllabusEntry";
							  document.getElementById("courseId").value = id;
							  document.getElementById("rejectReason").value=reason;
							  document.syllabusEntryHodApprovalForm.submit();
				              $("#openDialog").dialog("close");
				              $("#openDialog").hide();
						  }else{
							  $.confirm({
		            				'message'	: 'Reject Reason is required',
		            				'buttons'	: {
		            					'Ok'	: {
		            						'class'	: 'blue',
		            						'action': function(){
		            							$.confirm.hide();
		            						}
		            					}
		            				}
		            			});	
							  }
			  },
			 Cancel : function() {
				  document.getElementById("tempRejectReason").value='';
			      $("#openDialog").dialog("close");
			      $("#openDialog").hide();
			   }
				               
			}
	    });
}
function unCheckSelectAll() {
	 var inputs = document.getElementsByTagName("input");
	 var inputObj;
	 var checkBoxOthersSelectedCount = 0;
	 var checkBoxOthersCount = 0;
	 for(var count1 = 0;count1<inputs.length;count1++) {
	          inputObj = inputs[count1];
	          var type = inputObj.getAttribute("type");
	            if (type == 'checkbox' && inputObj.id != "checkAll") {
	                  checkBoxOthersCount++;
	                  if(inputObj.checked) {
	                        checkBoxOthersSelectedCount++;
	                        inputObj.value="on";
	                  }else{
	                	  inputObj.value="off";	
	                      }   
	            }
	    }
	    if(checkBoxOthersCount != checkBoxOthersSelectedCount) {
	      document.getElementById("checkAll").checked = false;
	    } else {
	      document.getElementById("checkAll").checked = true;
	    }
	}
function saveProgramDetails(id,programId) {
	 document.getElementById("method").value = "saveProgramDetails";
	 document.getElementById("courseId").value = id;
	 document.getElementById("tempOpen").value = programId;
	 document.syllabusEntryHodApprovalForm.submit();
}
</script>
<html:form action="/syllabusEntryHodApproval" method="post">
<html:hidden property="formName" value="syllabusEntryHodApprovalForm" />
<html:hidden property="pageType" value="1" />
<html:hidden property="method" value="getsubjectAndStatus" styleId="method"/>
<html:hidden property="subjectId"  styleId="subjectId"/>
<html:hidden property="listSize" styleId="listSize"/>
<html:hidden property="courseId"  styleId="courseId"/>
<html:hidden property="rejectReason"  styleId="rejectReason"/>
<html:hidden property="tempOpen"  styleId="tempOpen"/>
<html:hidden property="batchYear" styleId="batchYear"/>
<table width="98%" border="0">
  <tr>
    <td><span class="heading"><bean:message key="knowledgepro.admin"/><span class="Bredcrumbs">&gt;&gt;Syllabus Entry HOD Approval&gt;&gt;</span></span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="10"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="body" ><div align="left"><strong class="boxheader">Syllabus Entry HOD Approval</strong></div></td>
        <td width="13" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="122" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><div align="center">
              <table width="100%" height="50"  border="0" cellpadding="0" cellspacing="0">
                 <tr>
               	    <td height="20" colspan="6" align="left">
               	    <div align="right" style="color:red"> <span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields"/></span></div>
               	    <div id="errorMessage"> &nbsp;
                       <FONT color="red"><html:errors/></FONT>
                       <FONT color="green">
							<html:messages id="msg" property="messages" message="true">
									<c:out value="${msg}" escapeXml="false"></c:out><br>
							</html:messages>
					  </FONT>
					  </div>
               	    </td>
                 </tr>
                 <logic:notEmpty name="syllabusEntryHodApprovalForm" property="list" >
                 <tr>
                   <td height="49" colspan="6" class="body" ><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                     <tr>
                       <td ><img src="images/01.gif" width="5" height="5" /></td>
                       <td width="914" background="images/02.gif"></td>
                       <td><img src="images/03.gif" width="5" height="5" /></td>
                     </tr>
                     <tr>
                       <td width="5"  background="images/left.gif"></td>
                       <td valign="top">
                       <table width="100%" cellspacing="1" cellpadding="2" id="report1">
                       <nested:iterate id="to" name="syllabusEntryHodApprovalForm" property="list" indexId="cnt">
                       <%
                       		String course="course('"+cnt+"')";
                       		String program="program('"+cnt+"')";
                       		String courseId="courseId_"+cnt;
                       		String programId="programId_"+cnt;
                       		String c=String.valueOf(cnt);
                       		
                       %>
                       <tr >
		                  	<td width="80%" class="tdIMG" >
								<div align="left" style="border: none; font-size: 13px; color: #006600"><a href="#" onclick="<%=course %>" style="text-decoration:none"><b><bean:write name="to" property="courseName"/> </b></a></div>
							</td>
							<td width="30%" class="tdIMG">
								<div align="left" style="border: none; font-size: 13px; color: #11EB11"><a href="#" onclick="<%=program %>" style="text-decoration:none"><b><bean:write name="to" property="programDetails"/> </b></a></div>
							</td>
		                 </tr>
		                 <tr class="data" id="<%=programId %>">
		                 	<td colspan="2">
		                 	<table width="100%">
		                 		<tr>
				                   <td height="49"  class="body" width="100%"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
				                     <tr>
				                       <td ><img src="images/01.gif" width="5" height="5" /></td>
				                       <td width="914" background="images/02.gif"></td>
				                       <td><img src="images/03.gif" width="5" height="5" /></td>
				                     </tr>
				                     <tr>
				                       <td width="5"  background="images/left.gif"></td>
				                       <td valign="top">
				                       <table width="100%" cellspacing="1" cellpadding="2">
							                <tr>
					                             <td width="25%" class="row-odd" ><div align="right">
					                             	<span class="Mandatory">*</span>Department Overview</div>
					                             </td>
					                              <td width="75%" height="25" class="row-even" >
					                            	 <nested:textarea property="programTo.departmentOverview" style="width: 100%" rows="5" cols="70"></nested:textarea>
					                             </td>
					                           </tr>
					                           <tr>
						                            <td width="25%" class="row-odd" ><div align="right">
						                             	<span class="Mandatory">*</span>Mission Statement</div>
						                             </td>
						                              <td width="75%" height="25" class="row-even" >
						                            	 <nested:textarea property="programTo.missionStatement" style="width: 100%" rows="5" cols="70"></nested:textarea>
						                             </td>
					                           </tr>
					                           <tr>
						                            <td width="25%" class="row-odd" ><div align="right">
						                             	<span class="Mandatory">*</span>Introduction To The Programme</div>
						                             </td>
						                              <td width="75%" height="25" class="row-even" >
						                            	 <nested:textarea property="programTo.introductionProgramme" style="width: 100%" rows="5" cols="70"></nested:textarea>
						                             </td>
					                           </tr>
					                           <tr>
						                            <td width="25%" class="row-odd" ><div align="right">
						                             	<span class="Mandatory">*</span>Programme Objective</div>
						                             </td>
						                              <td width="75%" height="25" class="row-even" >
						                            	 <nested:textarea property="programTo.programObjective"  style="width: 100%" rows="5" cols="70"></nested:textarea>
						                             </td>
					                           </tr>
					                           <tr>
								                   <td height="49"  class="body" width="100%" colspan="2"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
								                     <tr>
								                       <td ><img src="images/01.gif" width="5" height="5" /></td>
								                       <td width="914" background="images/02.gif"></td>
								                       <td><img src="images/03.gif" width="5" height="5" /></td>
								                     </tr>
								                     <tr>
								                       <td width="5"  background="images/left.gif"></td>
								                       <td valign="top">
								                       <table width="100%" cellspacing="1" cellpadding="2">
											                 <tr>
									                             <td class="row-odd"  width="100%" colspan="2"><div align="left">
									                             	<span class="Mandatory">*</span>Examination And Assesments</div>
									                             </td>
									                         </tr>
								                          	 <tr>
									                             <td width="100%" height="25" class="row-even" colspan="2">
									                             <nested:textarea property="programTo.examinationAndAssesments" styleClass="editme"  style="width:100%" ></nested:textarea>
									                             </td>
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
					                            <tr>
								                   <td height="49"  class="body" width="100%" colspan="2"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
								                     <tr>
								                       <td ><img src="images/01.gif" width="5" height="5" /></td>
								                       <td width="914" background="images/02.gif"></td>
								                       <td><img src="images/03.gif" width="5" height="5" /></td>
								                     </tr>
								                     <tr>
								                       <td width="5"  background="images/left.gif"></td>
								                       <td valign="top">
								                       <table width="100%" cellspacing="1" cellpadding="2">
											                 <tr>
									                             <td class="row-odd"  width="100%" colspan="2"><div align="left">
									                             	<span class="Mandatory">*</span>Assesment Pattern</div>
									                             </td>
									                         </tr>
								                          	 <tr>
									                             <td width="100%" height="25" class="row-even" colspan="2">
									                             <nested:textarea property="programTo.assesmentPattern" styleClass="editme"  style="width:100%" ></nested:textarea>
									                             </td>
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
									           <tr>
		                  							<td colspan="6">
		                  								<table width="100%">
		                  									<tr>
		                  										<td align="center">
		                  											<input type="button" class="btn" value="Save Program Details" onclick="saveProgramDetails('<bean:write name="to" property="courseId"/>','<%=c %>')"/>
					                  							</td>
		                  									</tr>
		                  								</table>
		                  							</td>
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
				           
		                 	</table>
		                 	
		                 	</td>
		                 </tr>
                       <nested:notEmpty name="to" property="syllabusEntryTos">
		                  <tr class="data" id="<%=courseId %>">
		                  	<td colspan="2">
		                  		<table width="100%">
		                  			<tr>
				                   <td height="49"  class="body" width="100%"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
				                     <tr>
				                       <td ><img src="images/01.gif" width="5" height="5" /></td>
				                       <td width="914" background="images/02.gif"></td>
				                       <td><img src="images/03.gif" width="5" height="5" /></td>
				                     </tr>
				                     <tr>
				                       <td width="5"  background="images/left.gif"></td>
				                       <td valign="top">
				                       <table width="100%" cellspacing="1" cellpadding="2">
					                  			<tr>
					                  				<td width="5%"  height="5%" class="row-odd" ><div align="center">Select</div></td>
					                  				<td width="5%"  height="5%" class="row-odd" ><div align="center"><bean:message key="admissionForm.detailmark.semester.label"/></div></td>
						               				<td width="20%" height="30%" class="row-odd" align="center" ><bean:message key="knowledgepro.admin.code"/></td>
						               				<td width="56%" height="30%" class="row-odd" align="center" ><bean:message key="knowledgepro.admin.template.candidate.name"/></td>
						               				<td width="19%" height="30%" class="row-odd" align="center" ><bean:message key="employee.info.job.achievement.status"/></td>
					                  			</tr>
				                  				<nested:iterate id="to1" property="syllabusEntryTos" indexId="count">
				                  				<%
				                  					String reason="viewReason('reason_"+count+"')";
				                  				    String id="reason_"+count;
				                  				%>
								                   	<c:choose>
															<c:when test="${count%2 == 0}">
																<tr class="row-even">
															</c:when>
															<c:otherwise>
																<tr class="row-white">
															</c:otherwise>
													</c:choose>
													<tr>
														<td  height="25" class="row-even" ><div align="center">
															<logic:equal value="Completed" property="status" name="to1">
																<nested:checkbox property="checked" onclick="unCheckSelectAll()"></nested:checkbox>
															</logic:equal>
														</div></td>
					                  					<td  height="25" class="row-even" ><div align="center"><bean:write name="to1" property="semester"/></div></td>
					                  					<td  height="25" class="row-even" align="left"><bean:write name="to1" property="subjectCode"/></td>
					                  					<logic:notEmpty name="to1" property="display">
					                  						<td  height="25" class="row-even" align="left"><a href="#" onclick="syllabusEntry('<bean:write name="to1" property="subjId"/>')"><span class="dropt" title="Click here for syllabus entry."><bean:write name="to1" property="subjectName"/></span></a></td>
					                  					</logic:notEmpty>
					                  					<logic:empty name="to1" property="display">
					                  						<td  height="25" class="row-even" align="left"><bean:write name="to1" property="subjectName"/></td>
					                  					</logic:empty>
					                  					<td  height="25" class="row-even" align="center">
					                  						<nested:equal value="Pending" property="status">
					                  							<b><bean:write name="to1" property="status"/></b>
					                  						</nested:equal>
					                  						<nested:equal value="In-progress" property="status">
					                  							<font color="#E036DA "><b><bean:write name="to1" property="status"/></b></font>
					                  						</nested:equal>
					                  						<nested:equal value="Rejected" property="status">
					                  							<font color="#1E9C83"><b><bean:write name="to1" property="status"/></b></font>
					                  						</nested:equal>
					                  						<nested:equal value="HOD Rejected" property="status">
					                  							<font color="#F20D42"><b><bean:write name="to1" property="status"/></b></font>
					                  						</nested:equal>
					                  						<nested:equal value="Approved By HOD" property="status">
					                  							<font color="#05A959"><b><bean:write name="to1" property="status"/></b></font>
					                  						</nested:equal>
					                  						<nested:equal value="Approved" property="status">
					                  							<font color="0DF24E"><b><bean:write name="to1" property="status"/></b></font>
					                  						</nested:equal>
					                  						<nested:equal value="Completed" property="status">
					                  							<font color="#200DF2"><b><bean:write name="to1" property="status"/></b></font>
					                  						</nested:equal>
					                  						
					                  					</td>
					                  				</tr>
			                  					</nested:iterate>
			                  						<tr>
			                  							<td colspan="6">
			                  								<table width="100%">
			                  									<tr>
			                  										<td align="right" width="45%">
			                  											<input type="button" class="btn" value="Approve" onclick="approve('<bean:write name="to" property="courseId"/>')"/>
						                  							</td>
						                  							<td align="left" width="5%">
						                  								<input type="button" class="btn" value="Reject" onclick="reject('<bean:write name="to" property="courseId"/>')"/>
						                  							</td>
						                  							<td>
						                  								<input type="button" class="btn" value="Preview" onclick="preview('<bean:write name="to" property="courseId"/>','<bean:write name="to" property="courseName"/>')"/>
						                  							</td>
			                  									</tr>
			                  								</table>
			                  							</td>
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
		                  		
		                  		</table>
		                  	</td>
               				
            			  </tr>
	                	</nested:notEmpty>
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
                 </logic:notEmpty>
                 <tr>
               		<td>
             				<div id="openDialog">
             					<div align="center">
						<table width="100%">
							<tr>
		                   <td height="49"  class="body"><table width="99%" border="0"  cellpadding="0" cellspacing="0">
		                     <tr>
		                       <td ><img src="images/01.gif" width="5" height="5" /></td>
		                       <td width="914" background="images/02.gif"></td>
		                       <td><img src="images/03.gif" width="5" height="5" /></td>
		                     </tr>
		                     <tr>
		                       <td width="5"  background="images/left.gif"></td>
		                       <td valign="top">
		                       <table width="99%" cellspacing="1" cellpadding="2">
					                 <tr>
										<td class="row-odd" height="25" width="25%"><span class="Mandatory">*</span>Reject Reason</td>
										<td class="row-even" height="25" width="70%">
											<html:textarea property="tempRejectReason" name="syllabusEntryHodApprovalForm" styleId="tempRejectReason" style="width: 100% " cols="50" rows="5"></html:textarea>
										</td>
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
						</table>
					</div>
					</div>
               		</td>
               	</tr>
                 <tr><td height="10"></td></tr>
                 <tr>
                   <td height="20" colspan="6" valign="top" class="body" >
                   <table width="100%" cellspacing="1" cellpadding="2"><tr ></tr>
                     <tr>
                       <td height="20" colspan="2" >   
		                <table width="100%"  border="0" cellspacing="0" cellpadding="0">
		                  <tr>
		                    <td height="20"><div align="center">
		                      		<html:button property="" styleClass="formbutton" value="Cancel" onclick="cancel()"></html:button>
		                    </div></td>
		                    </tr>
		                </table>
                        </td>
                     </tr>
                   </table></td>
                </tr>
                 <tr>
                   <td height="10" colspan="6" class="body" ></td>
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
   </table>
   </td>
 </tr>
</table>
<script type="text/javascript">
var programId=document.getElementById("tempOpen").value;
 if(programId!=null){
 	 $("#report1 .tdIMG").addClass("odd");
	 $("#report1 .data").show();
	 $("#programId_"+programId).show();
 }
</script>
</html:form>