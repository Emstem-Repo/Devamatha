<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@page import="java.util.Map,java.util.HashMap"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<script src="jquery/development-bundle/jquery-1.7.1.js"></script>
<script src="js/AC_RunActiveContent.js" type="text/javascript"></script>
<link type="text/css" href="jquery/css/ui-lightness/jquery-ui-1.8.18.custom.css" rel="stylesheet" />
<script src="jquery/js/jquery-ui-1.10.0.custom.min.js" type="text/javascript"></script>
<script type="text/javascript" src="js/ajax/Ajax.js"></script>
<script type="text/javascript" src="js/ajax/AjaxUtil.js"></script>
<script type='text/javascript' src="jquery/Zebra/javascript/zebra_dialog.js"></script>
<link rel="stylesheet" href="jquery/Zebra/css/default/zebra_dialog.css"  />
<link rel="stylesheet" type="text/css" href="jquery.confirm/jquery.confirm/jquery.confirm.css" />
<script src="jquery.confirm/jquery.confirm/jquery.confirm.js"></script>
<style>
	@keyframes blink {
	    0% {
		opacity: 1;
	    }
	    49% {
		opacity: 1;
	    }
	    50% {
		opacity: 0;
	    }
	    100% {
		opacity: 0;
	    }
	}
	
</style>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">  
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>  
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>  
  
  
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootbox.js/4.4.0/bootbox.min.js">  
</script> 
<script type="text/javascript">
function cancelAction(){
	document.location.href="StudentLoginAction.do?method=returnHomePage";
		}

function printApplication()
{
	var url ="newSupplementaryImpApp.do?method=showPrintDetails";
	myRef = window.open(url,"certificateCourse","left=20,top=20,width=800,height=500,toolbar=1,resizable=0,scrollbars=1");
}

function printChallanApplication()
{
	var url ="newSupplementaryImpApp.do?method=showPrintChallanForRegular";
	myRef = window.open(url,"certificateCourse","left=20,top=20,width=800,height=500,toolbar=1,resizable=0,scrollbars=1");
}

function onlinePay(){
	document.getElementById("method").value = "redirectToPGIExamSuppl";
	document.newSupplementaryImpApplicationForm.submit();
		}


function submitApplication(){

	
	bootbox.confirm({
	    title: "Message :",
	    className: 'my-modal',
		 size: "small",
		 message: "Please Confirm the subjects selected for improvement/supplementary exams before submission.No modifications is Possible after submission of the Application Form."
		,
		 buttons: {
	        confirm: {
	            label: 'Submit',
	            className: 'btn-success'
	        },
	        cancel: {
	            label: 'Cancel',
	            className: 'btn-danger'
	        }
	    },
		 callback: function(result) {
			
    if (result) {
    	 document.getElementById("method").value="addSupplementaryApplicationForStudentLogin";
		 document.newSupplementaryImpApplicationForm.submit();
    } else {
        console.log("User declined dialog");
    }
}
});


}
</script>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html:form action="/newSupplementaryImpApp">
	<html:hidden property="formName" value="newSupplementaryImpApplicationForm" />
	<html:hidden property="pageType" value="3" />
	<html:hidden property="method" styleId="method" value="calculateAmount" />

	<div class="pageheader">
                        <div class="media">
                            <div class="Container">
                                <ul class="breadcrumb">
                                    <li><a href="#"><i class="glyphicon glyphicon-log-in"></i></a></li>
                                    <li><a href="#">Exam</a></li>
                                    <li>SupplementaryApplication</li>
                                </ul>
                                
                            </div>
                        </div><!-- media -->
                    </div><!-- pageheader -->
                      <div class="panel-body">
                        <div class="panel panel-primary">
                         <div class="panel-heading">
                          <h1 class="panel-title">SupplementaryApplication</h1>
                         </div>
     
                         </div>
                          
                    
                        
                    <div class="form-group">
                    <div align="right"><span style="color:red;">* Mandatory Fields</span>
                    </div>
                    </div>
                    <div class="container">
					<div class="panel-body" style="padding-top: 0px;">
				<div class="container">
							<div>
							<div>
								<span style="color: red; animation: blink 3s; animation-iteration-count: infinite;">
								<img alt="" src="images/exclamatory_mark.jpg" width="25" height="25">
								<b>
										Please handover the printout of Supplementary/Improvement submission in the pareeksha bhavan to enable the completion of the process.If the process is incomplete contact Pareeksha Bhavan digital section with transaction details.
									</span>	
								</b>
							</div>
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
						</div>
							</div>
							</div>
							</div>
							<nested:equal value="false" name="newSupplementaryImpApplicationForm" property="feesNotConfigured">
							<nested:equal value="true" name="newSupplementaryImpApplicationForm" property="suppImpAppAvailable">
								<nested:notEmpty name="newSupplementaryImpApplicationForm" property="mainList">
								<div class="col-sm-12" style="border-style: solid;border-color: #0a0a0a57;">
										<div class="form-group">
										<div class="col-sm-12">								
									<div class="col-sm-2">
									<bean:message
										key="knowledgepro.exam.revaluationApplication.regNo" /></div>
									<div class="col-sm-4"><nested:write name="newSupplementaryImpApplicationForm"
										 property="registerNo" /></div>
									<div class="col-sm-2">
									<bean:message
										key="knowledgepro.exam.revaluationApplication.studentName" /></div>
									<div class="col-sm-4">
									<nested:write name="newSupplementaryImpApplicationForm"
										 property="nameOfStudent" />
							      	</div>
							      	</div>
							      	</div>
							      	<div class="form-group">
							      <div class="col-sm-12">	
									<div class="col-sm-2">
									<bean:message
										key="knowledgepro.exam.revaluationApplication.course" /></div>
									<div class="col-sm-4"><nested:write name="newSupplementaryImpApplicationForm"
										 property="courseName" /></div>
										 </div>
										 </div>
										 <div class="form-group">
										  <div class="col-sm-12">	
								<div class="col-sm-2">
									<bean:message
										key="knowledgepro.supplementary.theory.fees" /></div>
									<div class="col-sm-4">
										<bean:write name="newSupplementaryImpApplicationForm" property="theoryFees"/>
										</div>
									<div class="col-sm-2">
								<bean:message
										key="knowledgepro.supplementary.practical.fees" /></div>
									<div class="col-sm-4">
									<bean:write name="newSupplementaryImpApplicationForm" property="practicalFees"/>
									</div>
									</div>
									</div>
									</div>
								</nested:notEmpty></nested:equal></nested:equal>
							
							</div>
							<nested:equal value="false" name="newSupplementaryImpApplicationForm" property="suppImpAppAvailable">
								Application is not available
							</nested:equal>
							<nested:equal value="true" name="newSupplementaryImpApplicationForm" property="feesNotConfigured">
								Application is not available Please Contact: 
							</nested:equal>
							<nested:equal value="false" name="newSupplementaryImpApplicationForm" property="feesNotConfigured">
							<nested:equal value="true" name="newSupplementaryImpApplicationForm" property="suppImpAppAvailable">
								<nested:notEmpty name="newSupplementaryImpApplicationForm" property="mainList">
									<nested:iterate id="eto" name="newSupplementaryImpApplicationForm" property="mainList" indexId="count2">
										<nested:notEmpty property="examList" >
											<nested:iterate id="cto"  property="examList" indexId="count1">
											<tr><td colspan="4"> &nbsp; </td> </tr>
												<div class="col-sm-12" style="color:red;"align="center"> <p>
											<nested:equal value="true" name="cto" property="supplementary">
											NOTE:The failed subjects are displayed below. Kindly tick the subject which you wish to apply for supplementary/improvement</nested:equal>
											<nested:equal value="true" name="cto" property="improvement">
											NOTE:Kindly tick [Apply for] for the subject which you wish to apply for improvement
											</nested:equal>
											</p>
											 </div>
									<nested:equal value="false" property="extended" name="newSupplementaryImpApplicationForm">
												<div class="col-sm-12" style="color:blue;"align="center"> <p>
											The Application will be available till: <bean:write name="eto" property="examDate"/> </p>
											 </div>
									</nested:equal>
									<nested:equal value="true" property="extended" name="newSupplementaryImpApplicationForm">
											<div class="col-sm-12" style="color:blue;" align="center"> <p>
											The Application is extended with fine till: <bean:write name="eto" property="extendedDate"/>&nbsp; </p>
											</div>
									</nested:equal>
									&nbsp;
								<div class="col-sm-12">
								<div class="col-sm-2" style="color:blue;">
								Class:
							</div>
								<div class="col-sm-4" style="color:blue;"><nested:write property="className"/> </div>
									<div class="col-sm-2" style="color:blue;">Exam : </div>
								<div class="col-sm-4" style="color:blue;"><bean:write name="eto" property="examName"/> </div>
								</div>
								<tr>
									<td colspan="4" align="center">
									<table width="90%" border="0" align="center" cellpadding="0" cellspacing="0">
						<tr>
							<td valign="top">
							<div class="table-responsive">
				      <table class="table table-bordered table-striped" width="100%" >
								<tr>
									<td height="25" rowspan="2" align="center" class="studentrow-odd">Sl
									No.</td>
									<td rowspan="2" class="studentrow-odd">Subject Code</td>
									<td rowspan="2" class="studentrow-odd">Subject Name</td>
									<nested:equal value="true" name="cto" property="supplementary"><td colspan="2" class="studentrow-odd">
									<div align="center">Failed</div>
									</td></nested:equal>
									<td colspan="2" class="studentrow-odd">
									<div align="center">Apply For</div>
									</td>
									<!--<td  class="studentrow-odd" rowspan="2">
									<div align="center">Is CIA</div>
									</td>
									-->
								</tr>
								<tr>
									<nested:equal value="true" name="cto" property="supplementary"><td class="studentrow-odd">Theory</td>
									<td class="studentrow-odd">Practical</td></nested:equal>
									<td class="studentrow-odd">Theory</td>
									<td class="studentrow-odd">Practical</td>
								</tr>

 <%
		 int total=0;
		 int subTotal=0;
		 int theotyTotal=0;
		 %>
								<nested:iterate  property="toList" indexId="count">

									<%
										String dynamicStyle = "";
														if (count % 2 != 0) {
															dynamicStyle = "row-white";

														} else {
															dynamicStyle = "studentrow-even";

														}
									%>
									<tr>
										<td class='<%=dynamicStyle%>'>
										<div align="center"><c:out value="${count + 1}" /></div>
										</td>
										<td class='<%=dynamicStyle%>'><nested:write
											property="subjectCode" /></td>
										<td class='<%=dynamicStyle%>'><nested:write
											property="subjectName" /></td>
											
										<%-- raghu chnged down
										<nested:equal value="true" name="cto" property="supplementary">
										--%>
										
										<nested:equal value="true" property="isSupplementary">
										<td class='<%=dynamicStyle%>'>
										<%    String ft2 = "ft2_"+count2+"_"+count1+"_"+count; 
                      					 	  String ft1 = "ft1_"+count2+"_"+count1+"_"+count; %>
                      					 	  
		                     				 <nested:hidden styleId='<%=ft2%>' property="isFailedTheory" />
		                     				 <span id="sp_<c:out value='${count2}'/>_<c:out value='${count1}'/>_<c:out value='${count}'/>">
											 <nested:checkbox styleId='<%=ft1%>' property="failedTheory" indexed="true" disabled="true" />
											 </span>
											 <script type="text/javascript">
												var vft1=document.getElementById("ft2_<c:out value='${count2}'/>_<c:out value='${count1}'/>_<c:out value='${count}'/>").value;
												if(vft1=="true"){
												document.getElementById("ft1_<c:out value='${count2}'/>_<c:out value='${count1}'/>_<c:out value='${count}'/>").checked =true ;
												document.getElementById("sp_<c:out value='${count2}'/>_<c:out value='${count1}'/>_<c:out value='${count}'/>").style.background="red";
												}
       										</script>
										</td>
										<td class='<%=dynamicStyle%>'>
										<%    String fp2 = "hidden1_"+count2+"_"+count1+"_"+count; 
                      						  String fp1 = "check1_"+count2+"_"+count1+"_"+count; %>
		                     				 <nested:hidden styleId='<%=fp2%>' property="isFailedPractical" />
		                     				 <span id="pr_<c:out value='${count2}'/>_<c:out value='${count1}'/>_<c:out value='${count}'/>">
											<nested:checkbox styleId='<%=fp1%>' property="failedPractical" indexed="true" disabled="true"/>
											</span>
										<script type="text/javascript">
										var vfp=document.getElementById("hidden1_<c:out value='${count2}'/>_<c:out value='${count1}'/>_<c:out value='${count}'/>").value;
										if(vfp=="true"){
										document.getElementById("check1_<c:out value='${count2}'/>_<c:out value='${count1}'/>_<c:out value='${count}'/>").checked =true ;
										document.getElementById("pr_<c:out value='${count2}'/>_<c:out value='${count1}'/>_<c:out value='${count}'/>").style.background="red";
										}
										</script>
										</td>
										
										<% String control="control1_"+count;
										String disable="disable_"+count;
										%>
										
												<td class='<%=dynamicStyle%>'>
												<nested:equal value="false" property="tempChecked">
													<nested:equal value="true" property="isFailedTheory">
																<input
																	type="hidden"
																	name="mainList[<c:out value='${count2}'/>].examList[<c:out value='${count1}'/>].toList[<c:out value='${count}'/>].tempChecked"
																	id="hidden_<c:out value='${count2}'/>_<c:out value='${count1}'/>_<c:out value='${count}'/>"
																	value="<nested:write property='tempChecked'/>" />
																	<input
																	type="checkbox"
																	name="mainList[<c:out value='${count2}'/>].examList[<c:out value='${count1}'/>].toList[<c:out value='${count}'/>].isAppearedTheory"
																	id="<c:out value='${count2}'/>_<c:out value='${count1}'/>_<c:out value='${count}'/>"/>
																	<script type="text/javascript">
																		var studentId = document.getElementById("hidden_<c:out value='${count2}'/>_<c:out value='${count1}'/>_<c:out value='${count}'/>").value;
																		if(studentId == "true") {
																			document.getElementById("<c:out value='${count2}'/>_<c:out value='${count1}'/>_<c:out value='${count}'/>").checked = true;
																		}		
																	</script>
													</nested:equal>
												</nested:equal>
												<nested:equal value="true" property="tempChecked">Applied</nested:equal>
															</td>
										<td class='<%=dynamicStyle%>'>
										<nested:equal value="false" property="tempPracticalChecked">
										<nested:equal value="true" property="isFailedPractical">

										<input
												type="hidden"
												name="mainList[<c:out value='${count2}'/>].examList[<c:out value='${count1}'/>].toList[<c:out value='${count}'/>].tempPracticalChecked"
												id="prhidden_<c:out value='${count}'/>"
												value="<nested:write property='tempPracticalChecked'/>" />
												<input
												type="checkbox"
												name="mainList[<c:out value='${count2}'/>].examList[<c:out value='${count1}'/>].toList[<c:out value='${count}'/>].isAppearedPractical"
												id="pr_<c:out value='${count}'/>" />
												<script type="text/javascript">
													var pr = document.getElementById("prhidden_<c:out value='${count}'/>").value;
													if(pr == "true") {
														document.getElementById("pr_"+"<c:out value='${count}'/>").checked = true;
													}		
												</script>
												</nested:equal>
												</nested:equal>
												<nested:equal value="true" property="tempPracticalChecked">Applied</nested:equal>
										</td>
										<nested:hidden styleId='<%=control%>'
											property="controlDisable" />
										</nested:equal>
										
										<%-- raghu chnged down
										<nested:equal value="true" name="cto" property="improvement">
										--%>
										
										<nested:equal value="true" property="isImprovement">
											<% String control="control1_"+count;
										String disable="disable_"+count;
										%>
										
										
												
												
										<nested:equal value="true" name="newSupplementaryImpApplicationForm" property="supplementary">
												
												
												<td class='<%=dynamicStyle%>'>
										<%    String ft2 = "ft2_"+count2+"_"+count1+"_"+count; 
                      					 	  String ft1 = "ft1_"+count2+"_"+count1+"_"+count; %>
                      					 	  
		                     				 <nested:hidden styleId='<%=ft2%>' property="isFailedTheory" />
		                     				 <span id="sp_<c:out value='${count2}'/>_<c:out value='${count1}'/>_<c:out value='${count}'/>">
											 <nested:checkbox styleId='<%=ft1%>' property="failedTheory" indexed="true" disabled="true" />
											 </span>
											 <script type="text/javascript">
												var vft1=document.getElementById("ft2_<c:out value='${count2}'/>_<c:out value='${count1}'/>_<c:out value='${count}'/>").value;
												if(vft1=="true"){
												document.getElementById("ft1_<c:out value='${count2}'/>_<c:out value='${count1}'/>_<c:out value='${count}'/>").checked =true ;
												document.getElementById("sp_<c:out value='${count2}'/>_<c:out value='${count1}'/>_<c:out value='${count}'/>").style.background="red";
												}
       										</script>
										</td>
										<td class='<%=dynamicStyle%>'>
										<%    String fp2 = "hidden1_"+count2+"_"+count1+"_"+count; 
                      						  String fp1 = "check1_"+count2+"_"+count1+"_"+count; %>
		                     				 <nested:hidden styleId='<%=fp2%>' property="isFailedPractical" />
		                     				 <span id="pr_<c:out value='${count2}'/>_<c:out value='${count1}'/>_<c:out value='${count}'/>">
											<nested:checkbox styleId='<%=fp1%>' property="failedPractical" indexed="true" disabled="true"/>
											</span>
										<script type="text/javascript">
										var vfp=document.getElementById("hidden1_<c:out value='${count2}'/>_<c:out value='${count1}'/>_<c:out value='${count}'/>").value;
										if(vfp=="true"){
										document.getElementById("check1_<c:out value='${count2}'/>_<c:out value='${count1}'/>_<c:out value='${count}'/>").checked =true ;
										document.getElementById("pr_<c:out value='${count2}'/>_<c:out value='${count1}'/>_<c:out value='${count}'/>").style.background="red";
										}
										</script>
										</td>
												
										</nested:equal>
												
												
												
												
												<td class='<%=dynamicStyle%>'>
												<nested:equal value="false" property="tempChecked">
																						<nested:equal value="true" property="theory">
												<input
																	type="hidden"
																	name="mainList[<c:out value='${count2}'/>].examList[<c:out value='${count1}'/>].toList[<c:out value='${count}'/>].tempChecked"
																	id="hidden_<c:out value='${count2}'/>_<c:out value='${count1}'/>_<c:out value='${count}'/>"
																	value="<nested:write property='tempChecked'/>" />
														<%--azr disabling checkbox --%>
														<nested:equal value="true" name="newSupplementaryImpApplicationForm" property="subjectFailed">
																	<nested:equal value="true" property="isSupplementary">
																		<input
																		type="checkbox"
																		name="mainList[<c:out value='${count2}'/>].examList[<c:out value='${count1}'/>].toList[<c:out value='${count}'/>].isAppearedTheory"
																		id="<c:out value='${count2}'/>_<c:out value='${count1}'/>_<c:out value='${count}'/>" />
																	</nested:equal>
																	<nested:equal value="false" property="isSupplementary">
																		<input
																			type="checkbox"
																			name="mainList[<c:out value='${count2}'/>].examList[<c:out value='${count1}'/>].toList[<c:out value='${count}'/>].isAppearedTheory"
																			id="<c:out value='${count2}'/>_<c:out value='${count1}'/>_<c:out value='${count}'/>" disabled="disabled"/>
																	</nested:equal>
															</nested:equal>
															<nested:equal value="false" name="newSupplementaryImpApplicationForm" property="subjectFailed">
																<nested:equal value="false" property="isSupplementary">
																		<input
																			type="checkbox"
																			name="mainList[<c:out value='${count2}'/>].examList[<c:out value='${count1}'/>].toList[<c:out value='${count}'/>].isAppearedTheory"
																			id="<c:out value='${count2}'/>_<c:out value='${count1}'/>_<c:out value='${count}'/>"/>
																	</nested:equal>
															</nested:equal>
														<%--azr end --%>
																	<script type="text/javascript">
																		var studentId = document.getElementById("hidden_<c:out value='${count2}'/>_<c:out value='${count1}'/>_<c:out value='${count}'/>").value;
																		if(studentId == "true") {
																			document.getElementById("<c:out value='${count2}'/>_<c:out value='${count1}'/>_<c:out value='${count}'/>").checked = true;
																		}		
																	</script>
																	</nested:equal>
												</nested:equal>
												<nested:equal value="true" property="tempChecked">Applied</nested:equal>
															</td>
										<td class='<%=dynamicStyle%>'>
										<nested:equal value="false" property="tempPracticalChecked">
										<nested:equal value="true" property="practical">

										<input
												type="hidden"
												name="mainList[<c:out value='${count2}'/>].examList[<c:out value='${count1}'/>].toList[<c:out value='${count}'/>].tempPracticalChecked"
												id="prhidden_<c:out value='${count}'/>"
												value="<nested:write property='tempPracticalChecked'/>" />
												<input
												type="checkbox"
												name="mainList[<c:out value='${count2}'/>].examList[<c:out value='${count1}'/>].toList[<c:out value='${count}'/>].isAppearedPractical"
												id="pr_<c:out value='${count}'/>" />
												<script type="text/javascript">
													var pr = document.getElementById("prhidden_<c:out value='${count}'/>").value;
													if(pr == "true") {
														document.getElementById("pr_"+"<c:out value='${count}'/>").checked = true;
														
													}		
												</script>
												</nested:equal>
												</nested:equal>
												<nested:equal value="true" property="tempPracticalChecked">Applied</nested:equal>
										</td>
										<nested:hidden styleId='<%=control%>'
											property="controlDisable" />
										</nested:equal>
										
										<!-- Start CIA Exam -->
										
										<%--
										<td  class='<%=dynamicStyle%>' align="center"><div align="center">
										
										<nested:equal value="false" property="isESE">
										<nested:equal value="false" property="tempCIAExamChecked">
										

										<input
												type="hidden"
												name="mainList[<c:out value='${count2}'/>].examList[<c:out value='${count1}'/>].toList[<c:out value='${count}'/>].tempCIAExamChecked"
												id="CIAhidden_<c:out value='${count}'/>"
												value="<nested:write property='tempCIAExamChecked'/>" />
												<input
												type="checkbox"
												name="mainList[<c:out value='${count2}'/>].examList[<c:out value='${count1}'/>].toList[<c:out value='${count}'/>].ciaExam"
												id="CIA_<c:out value='${count}'/>" />
												<script type="text/javascript">
													var cia = document.getElementById("CIAhidden_<c:out value='${count}'/>").value;
													if(cia == "true") {
														document.getElementById("CIA_"+"<c:out value='${count}'/>").checked = true;
														
													}		
												</script>
												
												</nested:equal>
												</nested:equal>
												<nested:equal value="true" property="isESE">No</nested:equal>
												<nested:equal value="true" property="tempCIAExamChecked">Yes</nested:equal>
												
										
										</div>
										</td>
										
										--%>
										
										<!-- END CIA Exam -->
									</tr>
								</nested:iterate>
							</table>
							</div>
							</td>
						</tr>
					</table>
					<%theotyTotal=150*subTotal; %>
		<% total=theotyTotal+100+150+20+25;
		session.setAttribute("totalAmount",total);%>
											</nested:iterate>
											
										</nested:notEmpty>
									</nested:iterate>
								</nested:notEmpty>
							</nested:equal></nested:equal><!--
							</table>
							</td>
						</tr>
					</table>
					</td>
				</tr>
				--><tr>
					<td valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="47%" height="29">&nbsp;</td>
						</tr>
					</table>
					</td>
				</tr>
				<tr>
					<td valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<nested:equal value="true" property="extended" name="newSupplementaryImpApplicationForm">
						<tr>
						<td width="47%" height="35" colspan="3">
						<div align="center" style="font-weight: bold;font-size: 12px;color: red"><B>Late submission fee: <bean:write name="newSupplementaryImpApplicationForm" property="fineFees"/></B>
						
						</div>
						</td>
						</tr>
					</nested:equal>
						
						<tr>
							<td  height="35" colspan="3">
							<div align="center" id="buttons">
							<nested:equal value="true" name="newSupplementaryImpApplicationForm" property="suppImpAppAvailable">
							<nested:equal value="false" name="newSupplementaryImpApplicationForm" property="isAttendanceShortage">
							<nested:equal value="false" name="newSupplementaryImpApplicationForm" property="displayButton">
							
							<html:button property=""
								styleClass="btn btn-success" value="Continue "
								onclick="submitApplication()"></html:button>
								<%-- 
							<html:button property=""
								styleClass="btnbg" value="Print Challan" 
								onclick="printChallanApplication()"></html:button>
								&nbsp; &nbsp; 
							--%> 
							
							</nested:equal>
							</nested:equal>
							</nested:equal>
							
							<nested:equal value="true" name="newSupplementaryImpApplicationForm" property="displayButton">
							
								<html:button property=""
								styleClass="btn btn-success" value="Pay Online"
								onclick="onlinePay()"></html:button>
								
							</nested:equal>
							
							<nested:equal value="true" name="newSupplementaryImpApplicationForm" property="displayButton">
							<nested:equal value="true" name="newSupplementaryImpApplicationForm" property="printSupplementary">
								<html:button property=""
								styleClass="btn btn-success" value="Print Application"
								onclick="printApplication()"></html:button>
							</nested:equal>
							</nested:equal>
							<html:button property=""
								styleClass="btn btn-danger" value="Close"
								onclick="cancelAction()"></html:button>
							
							
							</div>
							</td>
						</tr>
					</table>
					</td>
				</tr>
				<tr>
					<td valign="top" class="news">&nbsp;</td>
				</tr>
				<tr>
				</tr>
			<!--</table>
			</td>
		</tr>
	</table>
--></html:form>
<script type="text/javascript">
$(".display-info").fadeOut(800).fadeIn(800).fadeOut(400).fadeIn(400).fadeOut(400).fadeIn(400);
var print = "<c:out value='${newSupplementaryImpApplicationForm.printSupplementary}'/>";
if(print.length != 0 && print == "true") {
	var url ="newSupplementaryImpApp.do?method=showPrintDetails";
	myRef = window.open(url,"certificateCourse","left=20,top=20,width=800,height=500,toolbar=1,resizable=0,scrollbars=1");
}
document.getElementById("buttons").style.display="block";
</script>